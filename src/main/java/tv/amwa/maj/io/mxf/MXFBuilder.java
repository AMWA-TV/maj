/*
 * Copyright 2016 Richard Cartwright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tv.amwa.maj.io.mxf;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.io.mxf.impl.DeltaEntryImpl;
import tv.amwa.maj.io.mxf.impl.IndexEntryImpl;
import tv.amwa.maj.io.mxf.impl.IndexTableSegmentImpl;
import tv.amwa.maj.io.mxf.impl.MXFFileImpl;
import tv.amwa.maj.io.mxf.impl.PartitionPackImpl;
import tv.amwa.maj.io.mxf.impl.PrimerPackImpl;
import tv.amwa.maj.io.mxf.impl.RandomIndexPackImpl;
import tv.amwa.maj.io.mxf.impl.ResolutionEntry;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionObjectReference;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.impl.ClassDefinitionImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionObjectReferenceImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionRecordImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionSetImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionVariableArrayImpl;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>Collection of static methods that provide the ability to build {@linkplain MXFFile MXF files}
 * from in memory data models (e.g. AAF) and vice versa. Reading or writing an MXF file is boiled
 * down to calling these methods in the correct order.</p>
 *
 * <p>In this builder, the following capability is provided for reading and writing KLV triplets:</p>
 *
 * <ul>
 *  <li>The ability to read and write keys with {@link #readKey(ByteBuffer)} and {@link #writeKey(UL, ByteBuffer)};</li>
 *  <li>The ability to read and write BER encoded length values with {@link #readBERLength(ByteBuffer)} and
 *  {@link #writeBERLength(long, int, ByteBuffer)};</li>
 *  <li>Two kinds of value encoding are supported:
 *   <ul>
 *    <li>Reading and writing {@linkplain FixedLengthPack fixed length packs} is supported with
 *   	{@link #readFixedLengthPack(AUID, ByteBuffer)}, {@link #writeFixedLengthPack(FixedLengthPack, ByteBuffer)}
 *   	and {@link #lengthOfFixedLengthPack(FixedLengthPack)};</li>
 *    <li>Reading and writing local sets with 2-byte keys and 2-byte values is supported with
 *      {@link #readLocalSet(UL, ByteBuffer, PrimerPack, Map, List)},
 *      {@link #writeLocalSet(MetadataObject, ByteBuffer, PrimerPack, List)} and
 *      {@link #lengthOfLocalSet(MetadataObject)}.</li>
 *   </ul></li>
 * </ul>
 *
 * <p>When reading an MXF file, all methods report an error on bad data and try to skip over it and
 * move on.</p>
 *
 *
 *
 * @see MXFFile
 *
 */
public class MXFBuilder {

	private MXFBuilder() { }

	private static boolean mxfRegistration = false;

	public final static synchronized void registerMXF() {

		if (mxfRegistration) return;

		MediaEngine.initializeAAF();
		PartitionPackImpl.initializePackClasses();
		Warehouse.lookForClass(PrimerPackImpl.class);
		Warehouse.lookForClass(RandomIndexPackImpl.class);
		Warehouse.lookForClass(IndexTableSegmentImpl.class);
		
		TypeDefinitionRecordImpl.registerInterfaceMapping(DeltaEntry.class, DeltaEntryImpl.class);
		TypeDefinitionRecordImpl.registerInterfaceMapping(IndexEntry.class, IndexEntryImpl.class);
		
		Warehouse.registerTypes(TypeDefinitions.class, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX);

		mxfRegistration = true;
	}

	private static Set<String> propertyIgnoreList =
		new HashSet<String>();

	/**
	 * <p>Add a property to the set of properties ignored by this builder. When reading an
	 * MXF file containing consisting of local sets that have known issues, it is useful to
	 * be able to ignore certain properties by skipping over them and not producing an
	 * error. This method reduces noise rather than changing the ability of the builder
	 * to read a file.</p>
	 *
	 * <p>No check is made against any known metadata set to see if the property exists. The
	 * property will be ignored on the basis of its name and name only.</p>
	 *
	 * @param ignoreMe Name of a property to ignore.
	 *
	 * @throws NullPointerException Cannot set a property to ignore with a <code>null</code>
	 * string.
	 */
	public final static void ignoreProperty(
			String ignoreMe)
		throws NullPointerException {

		if (ignoreMe == null)
			throw new NullPointerException("Cannot set a property name to ignore with a null value.");

		propertyIgnoreList.add(ignoreMe);
	}

	/**
	 * <p>Clear the set of ignored properties, restoring the builder to the state where it
	 * will attempt to raad everything.</p>
	 */
	public final static void clearIgnoredProperties() {

		propertyIgnoreList.clear();
	}

	/**
	 * <p>Read data from a {@linkplain FixedLengthPack fixed length pack} that starts with
	 * the provided key and is contained in the given buffer.</p>
	 *
	 * <p>The methods reads the buffer to
	 * create a the {@linkplain MetadataObject metadata object} corresponding to the key. The key
	 * is the {@linkplain ClassDefinition#getAUID() identification} of the {@linkplain ClassDefinition class}
	 * defining the metadata object. To be able to instantiate the metadata object, it must have been
	 * registered with the {@linkplain tv.amwa.maj.industry.Warehouse warehouse} first. As the order
	 * of a fixed length pack is used to determine which property is which in the buffer, the implementation
	 * of the metadata object must also implement {@linkplain FixedLengthPack fixed length pack}.</p>
	 *
	 * <p>The key is provided to the method separately as it must have been read from the buffer to
	 * determine that the next item in the buffer is a fixed length pack. The buffer should contain
	 * have remaining to read the correct number of bytes as has been determined by reading the length of the pack
	 * before calling this method. Although known as a fixed length
	 * pack, it is possible for the last property of the pack to have variable length, as for the
	 * {@linkplain PartitionPack#getEssenceContainers() essence containers} of a
	 * {@linkplain PartitionPack partition pack}.</p>
	 *
	 * @param key Identification of the metadata object represented by the fixed length pack.
	 * @param buffer Buffer containing a serialization of the value of the pack. When passed
	 * into the method, the remaining number of bytes to read is equal to the length of the
	 * pack taken from the KLV coding.
	 * @return Metadata object created by reading the fixed length pack.
	 *
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code> or
	 * the key is not of a known type.
	 * @throws EndOfDataException Reached the end of the buffer before al the properties of
	 * the fixed length pack have been read.
	 * @throws ClassCastException The metadata object registered in the warehosue with the given
	 * key is not a fixed length pack.
	 *
	 * @see #writeFixedLengthPack(FixedLengthPack, ByteBuffer)
	 * @see #lengthOfFixedLengthPack(FixedLengthPack)
	 * @see PartitionPack
	 * @see PrimerPack
	 * @see FixedLengthPack
	 * @see FixedLengthPack#getPackOrder()
	 */
	public final static FixedLengthPack readFixedLengthPack(
			AUID key,
			ByteBuffer buffer)
		throws NullPointerException,
			EndOfDataException,
			ClassCastException {

		if (buffer == null)
			throw new NullPointerException("Cannot read a fixed length pack from a null buffer.");
		if (key == null)
			throw new NullPointerException("Cannot read a fixed length pack with a null key.");

		ClassDefinition fixedLengthClass = null;
		FixedLengthPack fixedLengthValue = null;

		try{
			fixedLengthClass = Warehouse.lookForClass(key);
			fixedLengthValue = (FixedLengthPack) fixedLengthClass.createInstance();
		}
		catch (ClassCastException cce) {
			throw new ClassCastException("The given key does not correspond to a metadata object that can be serialized as a fixed length pack. The key resolved to type " + fixedLengthClass.getName() + ".");
		}
		catch (NullPointerException npe) {
			throw new NullPointerException("The given key of " + key.toString() + " is now known in the warehouse.");
		}

		for ( String propertyName : fixedLengthValue.getPackOrder()) {

			try {
				PropertyDefinition property = fixedLengthClass.lookupPropertyDefinition(propertyName);
				TypeDefinition propertyType = property.getTypeDefinition();
				PropertyValue propertyValue = propertyType.createFromBytes(buffer);
				property.setPropertyValue(fixedLengthValue, propertyValue);
			}
			catch (BadParameterException bpe) {
				continue;
			}
			catch (EndOfDataException eode) {
				throw new EndOfDataException("Prematurely reached the end of the data buffer when reading a fixed length buffer for type " + fixedLengthClass.getName() + ".");
			}
		}

		return fixedLengthValue;
	}

	/**
	 * <p>Write the value of the given {@linkplain FixedLengthPack fixed length pack} to the given byte buffer.
	 * The key and length of the pack must have been written to the buffer prior to calling this method.</p>
	 *
	 * <p>The buffer is expected to have sufficient capacity remaining. To determine how much capacity this
	 * is before calling this method, call {@link #lengthOfFixedLengthPack(FixedLengthPack)}.</p>
	 *
	 * <p>The properties of the fixed length pack are written to the buffer in the
	 * {@linkplain FixedLengthPack#getPackOrder() specified order}.</p>
	 *
	 * @param fixedLengthPack Data to write to the buffer.
	 * @param buffer Buffer to be written into.
	 *
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws InsufficientSpaceException Not enough space is available write all the data into the buffer.
	 * Note that this exception will be thrown part way through an attempt to write to the buffer.
	 *
	 * @see #readFixedLengthPack(AUID, ByteBuffer)
	 * @see #lengthOfFixedLengthPack(FixedLengthPack)
	 * @see PartitionPack
	 * @see PrimerPack
	 * @see FixedLengthPack
	 * @see FixedLengthPack#getPackOrder()
	 */
	public final static void writeFixedLengthPack(
			FixedLengthPack fixedLengthPack,
			ByteBuffer buffer)
		throws NullPointerException,
			InsufficientSpaceException {

		if (fixedLengthPack == null)
			throw new NullPointerException("Cannot write a fixed length pack from a null value.");
		if (buffer == null)
			throw new NullPointerException("Cannot write a fixed length pack to a null buffer.");

		// FIXME this is bad ... primer writes its own key, whereas others expect key and length to have been written
		if (fixedLengthPack instanceof PrimerPack) {
			PrimerPackImpl.writeAsBytes((PrimerPack) fixedLengthPack, buffer);
			return;
		}

		ClassDefinition fixedLengthClass = Warehouse.lookForClass(fixedLengthPack.getClass());

		for ( String propertyName : fixedLengthPack.getPackOrder() ) {

			try {
				PropertyDefinition property = fixedLengthClass.lookupPropertyDefinition(propertyName);
				TypeDefinition propertyType = property.getTypeDefinition();
				PropertyValue propertyValue = property.getPropertyValue(fixedLengthPack);
				propertyType.writeAsBytes(propertyValue, buffer);
			}
			catch (IllegalPropertyValueException ipve) {
				throw new InternalError("Unexpected type problem dealing wih property " + propertyName +
						" of class " + fixedLengthClass.getName() + " when writing bytes for a fixed length pack.");
			}
			catch (BadParameterException bpe) {
				throw new InternalError("Unexpected failure to resolve property " + propertyName +
						" of class " + fixedLengthClass.getName() + " when writing bytes for a fixed length pack.");
			}
		}
	}

	/**
	 * <p>Calculates the number of bytes that will be used to encode the value of the given
	 * {@linkplain FixedLengthPack fixed length pack}, exclusive of its key and length representation in
	 * the byte stream. The length calculation can be used as the encoded length and to create a buffer with
	 * at least enough capacity to represent the fixed length pack ready for
	 * {@linkplain #writeFixedLengthPack(FixedLengthPack, ByteBuffer) writing}.</p>
	 *
	 * @param fixedLengthPack Fixed length back to calculate the encoded length of.
	 * @return Number of bytes required to encode the given data.
	 *
	 * @throws NullPointerException Cannot calculate a length from a <code>null</code> value.
	 *
	 * @see #writeFixedLengthPack(FixedLengthPack, ByteBuffer)
	 * @see #readFixedLengthPack(AUID, ByteBuffer)
	 */
	public final static long lengthOfFixedLengthPack(
			FixedLengthPack fixedLengthPack)
		throws NullPointerException {

		if (fixedLengthPack == null)
			throw new NullPointerException("Cannot calculate the length in bytes of a null fixed length pack.");

		// FIXME does give incorrect value for primer packs

		ClassDefinition fixedLengthClass = Warehouse.lookForClass(fixedLengthPack.getClass());
		long length = 0l;

		for ( String propertyName : fixedLengthPack.getPackOrder() ) {

			try {
				PropertyDefinition property = fixedLengthClass.lookupPropertyDefinition(propertyName);
				TypeDefinition propertyType = property.getTypeDefinition();
				PropertyValue propertyValue = property.getPropertyValue(fixedLengthPack);
				length += propertyType.lengthAsBytes(propertyValue);
			}
			catch (IllegalPropertyValueException e) {
				throw new InternalError("Unexpected type problem dealing wih property " + propertyName +
						" of class " + fixedLengthClass.getName() + " when calculating the length of a fixed length pack.");
			}
			catch (BadParameterException bpe) {
				throw new InternalError("Unexpected failure to resolve property " + propertyName +
						" of class " + fixedLengthClass.getName() + " when calaulating the length of a fixed length pack.");
			}
		}

		return length;
	}

	/**
	 * <p>Create a new instance of a {@linkplain tv.amwa.maj.industry.MetadataObject metadata object}
	 * from the given buffer that has an encoding of the value as a local set.
	 * {@linkplain HeaderMetadata Header metadata} is encoded as a sequence of local sets, where each
	 * local set may have a variable length depending on its value.</p>
	 *
	 * <p>The key will have been read from the stream already and must correspond to the identification
	 * of a class registered with the {@linkplain tv.amwa.maj.industry.Warehouse warehouse}. The length
	 * will have been read from the stream and used to set the number of bytes available to read from the
	 * given buffer.<p>
	 *
	 * <p>A local set encoding is a sequence of tag-length-value triplets of each property, where the
	 * tags are two bytes long. The keys are {@linkplain PrimerPack#lookupUID(short) resolved} to full
	 * 16-byte keys using the given primer pack that has been read from the same
	 * {@linkplain HeaderMetadata header metadata}.</p>
	 *
	 * <p>As a local set often has references to other local sets in the same sequence that makes
	 * up {@linkplain HeaderMetadata header metadata}, the local set must allow for the resolution of
	 * the references after the creation of all of the metadata objects. The local set will add its
	 * identification to the <em>reference table</em> and request any subsequent <em>resolutions</em> required
	 * to fully complete the value.</p>
	 *
	 * <p>This method skips over the following kinds of data, returning <code>null</code>:</p>
	 *
	 * <ul>
	 *  <li>Any local set with a key that is not known in the {@linkplain tv.amwa.maj.industry.Warehouse warehouse};</li>
	 *  <li>Any representation of a {@linkplain tv.amwa.maj.meta.MetaDictionary meta dictionary}, such as that
	 *  sometimes found in an MXF OP-Atom file;</li>
	 *  <li>A local set that is a kind of {@linkplain tv.amwa.maj.meta.MetaDefinition meta definition},
	 *  although a warning message will be generated to say if the meta definition is now known in the
	 *  {@linkplain tv.amwa.maj.industry.Warehouse warehouse};</li>
	 *  <li>Any properties on the {@linkplain #ignoreProperty(String) properties to be ignored list};</li>
	 *  <li>Any properties that are not known for the class identified by the given key.</li>
	 * </ul>
	 *
	 * @param key Identification of the local set.
	 * @param buffer Buffer containing the encoding of the local set.
	 * @param primerPack Mapping of 2-byte tags to full 16-byte property identifications.
	 * @param referenceTable Mapping of local set instance identifications to values being created as
	 * header metadata is read in sequence.
	 * @param resolutions Requests for subsequent resolutions required to complete the value of
	 * the returned metadata object.
	 * @return Metadata object of the {@linkplain tv.amwa.maj.meta.ClassDefinition class} with
	 * the given identifier with most of its properties set according to the local set encoding
	 * in the given buffer.
	 *
	 * @throws Exception A problem occurred parsing the local set and so the value should be
	 * skipped over.
	 *
	 * @see #writeLocalSet(MetadataObject, ByteBuffer, PrimerPack, List)
	 * @see #writeLocalSet(PropertyValue, ByteBuffer, PrimerPack, List)
	 * @see #lengthOfLocalSet(MetadataObject)
	 * @see HeaderMetadata#getPreface()
	 * @see ResolutionEntry#resolve(Map)
	 */
	public final static MetadataObject readLocalSet(
			UL key,
			ByteBuffer buffer,
			PrimerPack primerPack,
			Map<AUIDImpl, MetadataObject> referenceTable,
			List<ResolutionEntry> resolutions)
		throws Exception { // TODO ruggidise all of this

		int preserveLimit = buffer.limit();

		ClassDefinition localSetClass = ClassDefinitionImpl.forAUID(key);

		if (localSetClass == null) {
			System.err.println("Unable to find a local implementation of class with id " + key.toString() +
					". Skipping " + buffer.remaining() + " bytes.");
			buffer.position(preserveLimit);
			return null;
		}

		// TODO add meta dictionary support
		if (localSetClass.getName().equals("MetaDictionary")) {
			System.err.println("This version of MAJ does not support processing meta dictionaries in MXF files.");
			buffer.position(preserveLimit);
			return null;
		}

		MetadataObject localSetValue = localSetClass.createInstance();

		if (localSetValue == null) {
			System.err.println("Unable to create an instance of class " + localSetClass.getName() + " implemented by " +
					localSetClass.getJavaImplementation().getName() + ". Skipping.");
			buffer.position(preserveLimit);
			return null;
		}

		if (localSetValue instanceof MetaDefinition) {
			System.err.println("Skipping meta definition " + localSetClass.getName() + ".");
			buffer.position(preserveLimit);
			return null;
		}

		while (buffer.hasRemaining()) {

			PropertyDefinition property = null;
			AUID propertyKey = null;
			short tag = buffer.getShort();
			int length = 0;

			try {
				if (primerPack != null) {
					propertyKey = primerPack.lookupUID(tag);
				}
				else {
					property = localSetClass.lookupPropertyDefinition(tag);
					propertyKey = property.getAUID();
				}

				short shortLength = buffer.getShort();
				length = (shortLength >= 0) ? shortLength : 65536 - shortLength;

				if (propertyKey == null)
					throw new BadParameterException("Unable to resolve tag in primer pack.");

				if (propertyKey.equals(MXFConstants.InstanceUID)) {
					byte[] instanceKey = new byte[16];
					buffer.get(instanceKey);
//					if (localSetValue instanceof tv.amwa.maj.model.Package)
//						System.out.println("Adding " + localSetClass.getName() + " package with key " + new AUIDImpl(instanceKey));
					referenceTable.put(new AUIDImpl(instanceKey), localSetValue);
					continue;
				}

				if (property == null)
					property = localSetClass.lookupPropertyDefinition(propertyKey);
			}
			catch (BadParameterException bpe) {
				if (propertyKey != null)
					System.err.println("Unable to resolve tag " + Integer.toHexString(tag) + " and key " + propertyKey.toString() +
							" for class " + localSetClass.getName() + ". Skipping.");
				else
					System.err.println("Unable to resolve tag " + Integer.toHexString(tag) +
							" for class " + localSetClass.getName() + ". Skipping.");

				buffer.limit(preserveLimit);
				buffer.position(buffer.position() + length);
				continue;
			}

//			System.out.println(property.getName());

//			if (property.getName().equals("Delta Entry Array"))
//				System.out.println("Found it!");

//			if (localSetValue instanceof MetaDefinition) {
//				String defName = ((MetaDefinition) localSetValue).getName();
//				if (defName != null)
//					System.out.println(defName + "." + property.getName());
//			}

			if (propertyIgnoreList.contains(property.getName())) {
				System.err.println("Ignoring property " + property.getMemberOf().getName() + "." + property.getName() + ".");
				buffer.limit(preserveLimit);
				buffer.position(buffer.position() + length);
				continue;
			}

			TypeDefinition propertyType = property.getTypeDefinition();
			buffer.limit(buffer.position() + length);

			PropertyValue propertyValue = null;
			try {
				propertyValue = propertyType.createFromBytes(buffer);
				if (propertyValue == null)
					throw new NullPointerException("Unexpected null property value created when parsing bytes.");
			}
			catch (Exception e) {
				System.err.println(e.getClass().getName() + " thrown when parsing value of " + property.getMemberOf().getName() + "." + property.getName() + ": " + e.getMessage());
				buffer.position(buffer.limit());
				buffer.limit(preserveLimit);
				continue;
			}

			switch (propertyType.getTypeCategory()) {

			case StrongObjRef:
				resolutions.add(new ResolutionEntry(property, localSetValue, propertyValue));
				break;
			case WeakObjRef:
				resolutions.add(0, new ResolutionEntry(property, localSetValue, propertyValue));
				break;
			case Set:
				if (((TypeDefinitionSetImpl) propertyType).getElementType() instanceof TypeDefinitionObjectReference)
					resolutions.add(new ResolutionEntry(property, localSetValue, propertyValue));
				else
					property.setPropertyValue(localSetValue, propertyValue);
				break;
			case VariableArray:
				if (((TypeDefinitionVariableArrayImpl) propertyType).getType() instanceof TypeDefinitionObjectReference)
					resolutions.add(new ResolutionEntry(property, localSetValue, propertyValue));
				else
					property.setPropertyValue(localSetValue, propertyValue);
				break;

			default:
				property.setPropertyValue(localSetValue, propertyValue);
				break;
			}

			buffer.limit(preserveLimit);
		}

		if (localSetValue instanceof MetaDefinition) {
			testMetaDictionaryEntry((MetaDefinition) localSetValue);
			return null;
		}

//		System.out.println(localSetValue.toString());
		if (localSetValue instanceof WeakReferenceTarget)
			WeakReference.registerTarget((WeakReferenceTarget) localSetValue);

		return localSetValue;
	}

	/**
	 * <p>Writes the given metadata object to the given buffer encoded as a local set, excluding
	 * its key and length that will have already been written. A new random
	 * {@linkplain MXFConstants#InstanceUID instance ID} will be created to identify the metadata object.
	 * As such, this method is suitable to write the top of a hierarchy of objects. If the object is
	 * referenced from another object in the same {@linkplain HeaderMetadata header metadata},
	 * write the reference and value to the local set with
	 * {@link #writeLocalSet(PropertyValue, ByteBuffer, PrimerPack, List)}.</p>
	 *
	 * <p>The buffer must have sufficient capacity and this can be ensured by calling
	 * {@link #lengthOfLocalSet(MetadataObject)} first.</p>
	 *
	 * <p>Any {@linkplain tv.amwa.maj.meta.TypeDefinitionStrongObjectReference strong references} made
	 * by this metadata object to another one must result in the referenced value being added to the
	 * list of <em>forward references</em>. The caller must ensure that all forward references get added
	 * to the list.</p>
	 *
	 * @param toWrite Metadata object to write as a local set value.
	 * @param buffer Buffer to write the local set data into.
	 * @param primerPack Lookup table of tag-to-key mappings in use for this header metadata.
	 * @param forwardReferences List of forward references that have yet to be written into the
	 * current set of header metadata.
	 * @return Number of local set bytes written. If the number is less than expected, this suggests that
	 * some of the properties could not be written into the set.
	 *
	 * @throws NullPointerException One or more of the arguments is/are <code>null</code>.
	 * @throws InsufficientSpaceException The given buffer does not have enough capacity to receive
	 * the bytes required to represent the local set.
	 *
	 * @see #readLocalSet(UL, ByteBuffer, PrimerPack, Map, List)
	 * @see #lengthOfLocalSet(MetadataObject)
	 */
	public final static long writeLocalSet(
			MetadataObject toWrite,
			ByteBuffer buffer,
			PrimerPack primerPack,
			List<PropertyValue> forwardReferences)
		throws NullPointerException,
			InsufficientSpaceException {

		if (toWrite == null)
			throw new NullPointerException("Cannot encode a null metadata object as a local set value.");

		AUID instanceID = Forge.randomAUID();
		// buffer.put(instanceID.getAUIDValue());

		return writeToLocalSet(toWrite, instanceID, buffer, primerPack, forwardReferences);
	}

	/**
	 * <p>Writes the given {@link TypeDefinitionStrongObjectReference strongly referenced}
	 * value to the given buffer encoded as a local set, excluding
	 * its key and length that will have already been written. The local identifier of the
	 * strong reference will be used created to identify the metadata object that is contained
	 * in the value. As such, this method is suitable to write mid- and bottom tier objects in
	 * the object hierarchy. If the object is not referenced from another object in the same
	 * {@linkplain HeaderMetadata header metadata}, write the the local set with
	 * {@link #writeLocalSet(MetadataObject, ByteBuffer, PrimerPack, List)}.</p>
	 *
	 * <p>The buffer must have sufficient capacity and this can be ensured by calling
	 * {@link #lengthOfLocalSet(MetadataObject)} first.</p>
	 *
	 * <p>Any {@linkplain tv.amwa.maj.meta.TypeDefinitionStrongObjectReference strong references} made
	 * by this metadata object to another one must result in the referenced value being added to the
	 * list of <em>forward references</em>. The caller must ensure that all forward references get added
	 * to the list.</p>
	 *
	 * @param referencedValue Strongly referenced value to write.
	 * @param buffer Buffer to write the local set data into.
	 * @param primerPack Lookup table of tag-to-key mappings in use for this header metadata.
	 * @param forwardReferences List of forward references that have yet to be written into the
	 * current set of header metadata.
	 * @return Number of local set bytes written. If the number is less than expected, this suggests that
	 * some of the properties could not be written into the set.
	 *
	 * @throws NullPointerException One or more of the arguments is/are <code>null</code>.
	 * @throws InsufficientSpaceException The given buffer does not have enough capacity to receive
	 * the bytes required to represent the local set.
	 *
	 * @see #readLocalSet(UL, ByteBuffer, PrimerPack, Map, List)
	 * @see #lengthOfLocalSet(MetadataObject)
	 */
	public final static long writeLocalSet(
			PropertyValue referencedValue,
			ByteBuffer buffer,
			PrimerPack primerPack,
			List<PropertyValue> forwardReferences)
		throws NullPointerException,
			InsufficientSpaceException {

		if (referencedValue == null)
			throw new NullPointerException("Cannot encode a null property value object as a local set value.");
		if ((referencedValue.getType() == null) ||
				(!(referencedValue.getType() instanceof TypeDefinitionStrongObjectReference)))
			throw new NullPointerException("The given referenced property has a null type or is not a strong reference type. Type provided was " + referencedValue.getType().getName() + ".");
		if ((referencedValue.getValue() == null) ||
				(!(referencedValue.getValue() instanceof MetadataObject)))
			throw new NullPointerException("The given referenced proeprty has a null value or references a inappropriate type of value.");

		MetadataObject toWrite = (MetadataObject) referencedValue.getValue();
		AUID instanceID = TypeDefinitionObjectReferenceImpl.getLocalReference(referencedValue);

		return writeToLocalSet(toWrite, instanceID, buffer, primerPack, forwardReferences);
	}

	private final static long writeToLocalSet(
			MetadataObject toWrite,
			AUID instanceID,
			ByteBuffer buffer,
			PrimerPack primerPack,
			List<PropertyValue> forwardReferences)
		throws NullPointerException,
			InsufficientSpaceException {

		if (buffer == null)
			throw new NullPointerException("Cannot write an encoded local set to a null buffer.");
		if (forwardReferences == null)
			throw new NullPointerException("The list of forward references provided is null and a metadata object might need to be added.");

		ClassDefinition classOfMetadata = MediaEngine.getClassDefinition(toWrite);
		SortedMap<? extends PropertyDefinition,? extends PropertyValue> properties = classOfMetadata.getProperties(toWrite);

		AUID classAUID = classOfMetadata.getAUID();
		writeKey((UL) classAUID, buffer);

		long lengthOfSetBody = lengthOfLocalSet(toWrite);
		MXFBuilder.writeBERLength(lengthOfSetBody, 4, buffer);

		buffer.putShort(MXFConstants.InstanceTag);
		buffer.putShort((short) 16);
		buffer.put(instanceID.getAUIDValue());

		long bytesWritten = 20l;

		for ( PropertyDefinition property : properties.keySet() ) {

			if (property.getAUID().equals(CommonConstants.ObjectClassID)) continue;

			PropertyValue value = properties.get(property);
			Short localTag = primerPack.lookupLocalTag(property);
			if (localTag == null)
				continue;

			long propertyLength = value.getType().lengthAsBytes(value);
			if (propertyLength > 65535) {
				System.err.println("Cannot write a property value longer than 65535 bytes for property " +
						property.getMemberOf().getName() + "." + property.getName() + ".");
				continue;
			}

			buffer.putShort(localTag);
			buffer.putShort((short) propertyLength);
			bytesWritten += 4l;

			List<PropertyValue> childReferences = value.getType().writeAsBytes(value, buffer);
			if (childReferences != null)
				forwardReferences.addAll(childReferences);
			bytesWritten += propertyLength;
		}

		return bytesWritten;
	}

	/**
	 * <p>Calculates the length in bytes of a local set value for the given metadata object, excluding the
	 * sets initial key and value. The value returned can be used to allocate sufficient capacity in a buffer to encode the value and
	 * to write the length part of a KLV triplet representing the value.</p>
	 *
	 * @param toWrite Metadata object to calculate the length of.
	 * @return Length of the given metadata object in bytes when encoded as a local set.
	 *
	 * @throws NullPointerException Cannot calculate a length for a <code>null</code> value.
	 *
	 * @see #writeBERLength(long, int, ByteBuffer)
	 */
	public static final @UInt64 long lengthOfLocalSet(
			MetadataObject toWrite)
		throws NullPointerException {

		if (toWrite == null)
			throw new NullPointerException("Cannot calculate the length as a local set value for a null metadata object.");

		ClassDefinition classOfMetadata = MediaEngine.getClassDefinition(toWrite);
		SortedMap<? extends PropertyDefinition,? extends PropertyValue> properties = classOfMetadata.getProperties(toWrite);

		long localSetLength = 20l; // Length of the instance ID tag, length and value

		for ( PropertyDefinition property : properties.keySet() ) {

			if (property.getAUID().equals(CommonConstants.ObjectClassID)) continue;
			localSetLength += 4; // Make space for the tag and length
			PropertyValue value = properties.get(property);
			localSetLength += value.getType().lengthAsBytes(value);
		}

		return localSetLength;
	}

	/**
	 * <p>Determines if the given key represents KLV fill data that can be safely skipped over.</p>
	 *
	 * @param key Key to test.
	 * @return Does the given key represent KLV fill data?
	 *
	 * @see #skipKLVFill(ByteBuffer)
	 */
	public final static boolean isKLVFill(
			UL key) {

		if (key == null) return false;

		if (key.getData1() != MXFConstants.KLVFill.getData1()) return false;
		if (key.getData2() != MXFConstants.KLVFill.getData2()) return false;
		if (key.getData3() != MXFConstants.KLVFill.getData3()) return false;

		byte[] firstPartTest = key.getData4();
		byte[] firstPartShouldBe = MXFConstants.KLVFill.getData4();

		for (int u = 0 ; u < 7 ; u++ )
			if (firstPartTest[u] != firstPartShouldBe[u]) return false;

		return true;
	}

	private final static void testMetaDictionaryEntry(
			MetaDefinition metaDefinition) {

		if (metaDefinition instanceof ClassDefinition) {
			ClassDefinition checkForClass =
				ClassDefinitionImpl.forAUID(metaDefinition.getAUID());
			if (checkForClass == null)
				System.err.println("Warning: A class with name " + metaDefinition.getName() + " is not known to this MAJ runtime.");
			return;
		}

		if (metaDefinition instanceof PropertyDefinition) {

			if (!ClassDefinitionImpl.isKnownProperty((PropertyDefinition) metaDefinition))
				System.err.println("Warning: A property with name " + metaDefinition.getName() + " is not known to this MAJ runtime.");

			return;
		}

		if (metaDefinition instanceof TypeDefinition) {

			TypeDefinition checkForType =
				Warehouse.lookForType(metaDefinition.getAUID());

			if (checkForType == null)
				System.err.println("Warning A type with name " + metaDefinition.getName() + " is not knowmn to this MAJ runtime.");
			return;
		}

		return;
	}

	/**
	 * <p>Skips over KLV fill information at the current position for an MXF file.</p>
	 *
	 * @param mxfFile File with KLV fill data to skip at the current position.
	 * @return Number of bytes skipped.
	 *
	 * @see #skipKLVFill(ByteBuffer)
	 * @see #isKLVFill(UL)
	 */
	public static final long skipKLVFill(
			MXFFile mxfFile) {

		// TODO push methods from implementation into interface
		if (!(mxfFile instanceof MXFFileImpl)) return 0l;

		MXFFileImpl mxfFileImpl = (MXFFileImpl) mxfFile;
		long startPosition = mxfFileImpl.tell();
		UL key = mxfFileImpl.readKey();
		if (!isKLVFill(key)) {
			mxfFileImpl.seek(startPosition);
			return 0;
		}

		long length = mxfFileImpl.readBER();
		mxfFileImpl.read((int) length);

		return mxfFileImpl.tell() - startPosition;
	}

	/**
	 * <p>Skip over KLV fill data at the current position for a buffer, moving the position to the first byte
	 * beyond the fill data, which may be the end of the buffer.</p>
	 *
	 * @param buffer Buffer with KLV fill data to skip at the current position.
	 * @return Number of bytes skipped over.
	 *
	 * @see #skipKLVFill(MXFFile)
	 * @see #isKLVFill(UL)
	 */
	public static final long skipKLVFill(
			ByteBuffer buffer) {

		int startPosition = buffer.position();
		int preserveLimit = buffer.limit();
		UL key = readKey(buffer);
		if (!isKLVFill(key)) {
			buffer.position(startPosition);
			return 0l;
		}

		long length = readBERLength(buffer);
		if (length < buffer.remaining())
			buffer.position((int) (buffer.position() + length));
		else
			buffer.position(preserveLimit);

		return (buffer.position() - startPosition);
	}

	/**
	 * <p>Reads a basic encoding rules (BER) length from the given buffer at the current
	 * position. For more details on BER lengths, see <a href="http://en.wikipedia.org/wiki/KLV">the
	 * wikipedia description of KLV data encoding</a>.</p>
	 *
	 * @param buffer Buffer to read the BER length from.
	 * @return Decoded length value.
	 *
	 * @throws NullPointerException The given buffer is <code>null</code>.
	 * @throws BufferUnderflowException Insufficient bytes remaining in the buffer to read
	 * the length.
	 *
	 * @see #writeBERLength(long, int, ByteBuffer)
	 */
	public final static long readBERLength(
			ByteBuffer buffer)
		throws NullPointerException,
			BufferUnderflowException {

		if (buffer == null)
			throw new NullPointerException("Cannot read a BER length from a null buffer.");

		byte first = 0;
		try {
			first = buffer.get();
		}
		catch (BufferUnderflowException bue) {
			System.err.println("Incomplete BER length in buffer at 0x" + Long.toHexString(buffer.position()));
			return -1l;
		}

		if (first >= 0) // top bit set not set
			return (long) first;

		int berTailLength = (int) (first & 0x7f);
		byte[] lengthData = new byte[berTailLength];

		if (buffer.remaining() < berTailLength) {
			System.err.println("Incomplete BER length in buffer at 0x" + Long.toHexString(buffer.position()));
			return -1l;
		}

		long lengthValue = 0l;
		buffer.get(lengthData);

		for ( int u = 0 ; u < lengthData.length ; u++ )
			lengthValue = (lengthValue << 8) |
				(((lengthData[u]) >= 0) ? lengthData[u] : 256 + lengthData[u]);

		return lengthValue;
	}

	/**
	 * <p>Encodes the given length value using the basic encoding rules (BER) for lengths and writes
	 * it to the given buffer. For more details on BER lengths, see <a href="http://en.wikipedia.org/wiki/KLV">the
	 * wikipedia description of KLV data encoding</a>.</p>
	 *
	 * @param length Length value to encode.
	 * @param encodedBytes Number of bytes to use to encode the value.
	 * @param buffer Buffer to write the encoded BER value to.
	 *
	 * @throws NullPointerException The given buffer is <code>null</code>.
	 * @throws BufferOverflowException Insufficient bytes remaining in the buffer to write the given length
	 * value.
	 * @throws IllegalArgumentException The length value is negative or will not fit in the number
	 * of encoded bytes specified.
	 */
	public final static void writeBERLength(
			long length,
			int encodedBytes,
			ByteBuffer buffer)
		throws NullPointerException,
			BufferOverflowException,
			IllegalArgumentException {

		if (buffer == null)
			throw new NullPointerException("Cannot write a BER length to a null buffer.");

		if (length < 0)
			throw new IllegalArgumentException("Cannot write a negative length value.");

		if ((encodedBytes < 1) || (encodedBytes > 9))
			throw new IllegalArgumentException("The number of encoded bytes must be between 1 and 9.");

		if (encodedBytes == 1) {
			if (length > 127)
				throw new IllegalArgumentException("The number of encoded bytes is 1 but the length is greater than 127.");

			buffer.put((byte) length);
			return;
		}

		encodedBytes--;
		long maxValue = 2l << (encodedBytes * 8);
		if (length >= maxValue)
			throw new IllegalArgumentException("The given length of " + length + " is greater than the maximum value for the given number of bytes to encode at " + maxValue + ".");

		// Write the length byte
		buffer.put((byte) (0x80 + (byte) encodedBytes));

		for ( int x = (encodedBytes - 1) ; x >= 0 ; x-- ) {
			long mask = 0xff << (x * 8);
			buffer.put((byte) ((length & mask) >> (x * 8)));
		}
	}

	/**
	 * <p>Read a 16-byte universal label from the specified byte buffer.</p>
	 *
	 * @param keyData Buffer containing a key at the current position.
	 * @return Key as a universal label or <code>null</code> if insufficient data was available or
	 * the key is invalid.
	 *
	 * @see #writeKey(UL, ByteBuffer)
	 */
	public final static UL readKey(
			ByteBuffer keyData) {

		if (keyData.limit() < 16)
			return null;

		byte[] keySwap = new byte[16];
		keyData.get(keySwap, 8, 8);
		keyData.get(keySwap, 0, 8);

		return new AUIDImpl(keySwap);
	}

	/**
	 * <p>Write the given universal label key to the given buffer.</p>
	 *
	 * @param key Key to be written.
	 * @param buffer Buffer to write the key to.
	 *
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws BufferOverflowException Insufficient capacity remaining in the given buffer
	 * to write a 16-byte key to.
	 *
	 * @see #readKey(ByteBuffer)
	 */
	public final static void writeKey(
			UL key,
			ByteBuffer buffer)
		throws NullPointerException,
			BufferOverflowException {

		if (buffer == null)
			throw new NullPointerException("Cannot write a key into a null buffer.");
		if (key == null)
			throw new NullPointerException("Cannot write a null key into a buffer.");

		if (buffer.remaining() < 16)
			throw new BufferOverflowException();

		byte[] keyBytes = key.getUniversalLabel();

		// Fix up class identifiers
		if ((keyBytes[4] == 0x02) && (keyBytes[5] == 0x06) && (keyBytes[6] == 0x01))
			keyBytes[5] = 0x53;
		buffer.put(keyBytes);
	}

	/**
	 * <p>Calculate the minimum number of bytes required to carry the given preface as header
	 * metadata in an MXF files.</p>
	 *
	 * @param preface Header metadata to calculate the size for.
	 * @return Minimum number of bytes required to encode the header metadata and associated
	 * primer pack.
	 *
	 * @throws NullPointerException Cannot calculate the size for a <code>null</code> preface.
	 *
	 * @see Partition#writeHeaderMetadata(Preface, long)
	 */
	public final static long calculateMinimumHeaderMetadataSize(
			Preface preface)
		throws NullPointerException {

		if (preface == null)
			throw new NullPointerException("Cannot calculate the size of the header metadata using a null preface.");

		PrimerPack primerPack = new PrimerPackImpl();
		primerPack.addLocalTagEntry(MXFConstants.InstanceTag, MXFConstants.InstanceUID);
		long lengthOfAllSets = PrimerPackImpl.addPropertiesForObject(primerPack, preface);

		return lengthOfAllSets + PrimerPackImpl.lengthAsBytes(primerPack);
	}
}
