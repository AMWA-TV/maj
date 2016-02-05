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

package tv.amwa.maj.io.mxf.impl;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyContains;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.io.mxf.FixedLengthPack;
import tv.amwa.maj.io.mxf.HeaderMetadata;
import tv.amwa.maj.io.mxf.LocalTagEntry;
import tv.amwa.maj.io.mxf.MXFBuilder;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.MXFFactory;
import tv.amwa.maj.io.mxf.MXFFile;
import tv.amwa.maj.io.mxf.PrimerPack;
import tv.amwa.maj.io.mxf.TypeDefinitions;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0105, uuid3 = 0x0100,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01 },
		definedName = "PrimerPack",
		description = "Table of local 2-byte tags mapped to defined identifiers.",
		symbol = "PrimerPack",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX)
public class PrimerPackImpl
	implements
		MetadataObject,
		FixedLengthPack,
		PrimerPack {

	public final static String[] packOrder = { "LocalTagEntry Batch" };

	private Map<Short, LocalTagEntry> localTagBatch =
		Collections.synchronizedMap(new HashMap<Short, LocalTagEntry>(100));
	private Map<AUID, Short> reverseMap =
		Collections.synchronizedMap(new HashMap<AUID, Short>());

	private short localCounter = 0;

	private long primerPackPadding = 0;

	static {
		Warehouse.registerTypes(TypeDefinitions.class, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX);
		Warehouse.lookForClass(LocalTagEntryImpl.class);
		Warehouse.lookForClass(PrimerPackImpl.class);
	}

	public PrimerPackImpl() {

	}

	void setPrimerPackPadding(
			long primerPackPadding) {

		this.primerPackPadding = primerPackPadding;
	}

	/**
	 * <p>Create a cloned copy of this PrimerPackImpl.</p>
	 *
	 * @return Cloned copy of this PrimerPackImpl.
	 */
	@MediaProperty(uuid1 = 0x06010107, uuid2 = 0x1500, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 },
			definedName = "LocalTagEntry Batch",
			aliases = { "Local Tag Entries" },
			typeName = "LocalTagEntryBatch",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "LocalTagEntryBatch")
	public Set<LocalTagEntry> getLocalTagEntryBatch() {

		return new HashSet<LocalTagEntry>(localTagBatch.values());
	}

	/**
	 * <p>Create a cloned copy of this PrimerPackImpl.</p>
	 *
	 * @return Cloned copy of this PrimerPackImpl.
	 */
	@MediaSetAdd("LocalTagEntry Batch")
	public void addLocalTagEntry(
			LocalTagEntry localTagEntry)
		throws NullPointerException {

		if (localTagEntry == null)
			throw new NullPointerException("Cannot add a new local tag entry to this batch with a null value.");

		localTagBatch.put(localTagEntry.getLocalTag(), localTagEntry);
		reverseMap.put(localTagEntry.getUID(), localTagEntry.getLocalTag());
	}

	/**
	 * <p>Create a cloned copy of this PrimerPackImpl.</p>
	 *
	 * @return Cloned copy of this PrimerPackImpl.
	 */
	public void addLocalTagEntry(
			@UInt16 short localTag, // Allow negative for dynamic tags > 0x8000
			tv.amwa.maj.record.AUID uid) {

		addLocalTagEntry(new LocalTagEntryImpl(localTag, uid));
		reverseMap.put(uid, localTag);
	}

	/**
	 * <p>Create a cloned copy of this PrimerPackImpl.</p>
	 *
	 * @return Cloned copy of this PrimerPackImpl.
	 */
	@MediaPropertyCount("LocalTagEntry Batch")
	public int countLocalTagEntries() {

		return localTagBatch.size();
	}

	/**
	 * <p>Create a cloned copy of this PrimerPackImpl.</p>
	 *
	 * @return Cloned copy of this PrimerPackImpl.
	 */
	@MediaPropertyClear("LocalTagEntry Batch")
	public void clearLocalTagEntries() {

		localTagBatch.clear();
	}

	public AUID lookupUID(
			@UInt16 short localTag) {

		if (!(localTagBatch.containsKey(localTag))) {
			System.err.println("Could not find tag 0x" + Integer.toHexString(localTag) + " in this primer pack.");
			return null;
		}
		return localTagBatch.get(localTag).getUID();
	}

	/**
	 * <p>Create a cloned copy of this PrimerPackImpl.</p>
	 *
	 * @return Cloned copy of this PrimerPackImpl.
	 */
	@MediaPropertyContains("LocalTagEntry Batch")
	public boolean isLocalTagEntryPresent(
			LocalTagEntry localTagEntry) {

		return localTagBatch.containsKey(localTagEntry.getLocalTag());
	}

	public String[] getPackOrder() {

		return packOrder;
	}

	public String toString() {

		return XMLBuilder.toXML(this);
	}

	public synchronized short getNextLocalCounter() {

		// TODO add roll over capability
		localCounter--;
		return localCounter;
	}

	public void addLocalTagEntry(
			PropertyDefinition propertyDefinition)
		throws NullPointerException {

		if (propertyDefinition == null)
			throw new NullPointerException("Cannot add a null property definition to a primer pack.");
		AUID uid = propertyDefinition.getAUID();
		if(!reverseMap.containsKey(uid))
		{
			short localTag = propertyDefinition.getLocalIdentification();
			if (localTag <= 0)
				localTag = getNextLocalCounter();
			addLocalTagEntry(localTag, propertyDefinition.getAUID());
		}
	}

	public Short lookupLocalTag(
			AUID uid)
		throws NullPointerException {

		if (uid == null)
			throw new NullPointerException("Cannot look up a property reference to find a local tag from a null value.");

		Short localTag = reverseMap.get(uid);
		if (localTag == null) {
			System.err.println("Cannot map the given identifier for a property " + uid.toString() + " to a tag.");
			return null;
		}
		else
			return localTag;
	}

	public Short lookupLocalTag(
			PropertyDefinition propertyDefinition)
		throws NullPointerException {

		if (propertyDefinition == null)
			throw new NullPointerException("Cannot look up a property to find its local tag using a null value.");

		AUID propertyID = propertyDefinition.getAUID();

//		if (!(reverseMap.containsKey(propertyID)))
//			addLocalTagEntry(propertyDefinition);

		Short localTag = reverseMap.get(propertyID);
		if (localTag == null) {
			System.err.println("Cannot map the given property " + propertyDefinition.getMemberOf().getName() + "." +
					propertyDefinition.getName() + " to a tag.");
			return null;
		}
		else
			return localTag;
	}

	public PrimerPack clone() {

		try {
			return (PrimerPack) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Implements cloneable so should never happen
			throw new InternalError(cnse.getMessage());
		}
	}

	public final static PrimerPack createFromBytes(
			ByteBuffer buffer)
		throws EndOfDataException {

		UL key = MXFBuilder.readKey(buffer);
		long length = MXFBuilder.readBERLength(buffer);
		int preserveLimit = buffer.limit();
		PrimerPack primerPack = null;

		try {
			buffer.limit((int) (buffer.position() + length));
			primerPack = (PrimerPack) MXFBuilder.readFixedLengthPack((AUIDImpl) key, buffer);
		}
		finally {
			buffer.limit(preserveLimit);
		}

		return primerPack;
	}

	public final static int lengthAsBytes(
			PrimerPack primerPack)
		throws NullPointerException {

		if (primerPack == null)
			throw new NullPointerException("Cannot calculate the byte length of a null primer pack.");

		return 16 + 4 + 8 + 18 * primerPack.countLocalTagEntries();
	}

	public final static void writeAsBytes(
			PrimerPack primerPack,
			ByteBuffer buffer)
		throws NullPointerException,
			InsufficientSpaceException {

		if (primerPack == null)
			throw new NullPointerException("Cannot write a null primer pack to a buffer.");
		if (buffer == null)
			throw new NullPointerException("Cannot write a primer pack into a null buffer.");

		if (buffer.remaining() < lengthAsBytes(primerPack))
			throw new InsufficientSpaceException("Insufficient space remaining to write primer pack into given buffer.");

		UL key = (UL) Forge.makeAUID(0x0d010201, (short) 0x0105, (short) 0x0100,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01} );
		MXFBuilder.writeKey(key, buffer);
		MXFBuilder.writeBERLength(18 * primerPack.countLocalTagEntries() + 8, 4, buffer);

		buffer.putInt(primerPack.countLocalTagEntries());
		buffer.putInt(18);

		for ( LocalTagEntry entry : primerPack.getLocalTagEntryBatch() ) {
			buffer.putShort(entry.getLocalTag());
			MXFBuilder.writeKey((UL) entry.getUID(), buffer);
		}
	}

	public final static PrimerPack createFromPreface(
			Preface preface)
		throws NullPointerException {

		if (preface == null)
			throw new NullPointerException("Cannot create a primer pack from a null preface.");

		PrimerPack pack = new PrimerPackImpl();

		pack.addLocalTagEntry(MXFConstants.InstanceTag, MXFConstants.InstanceUID);
		addPropertiesForObject(pack, preface);
		return pack;
	}

	public final static long addPropertiesForObject(
			PrimerPack primerPack,
			MetadataObject mdObject)
		throws NullPointerException {

		if (primerPack == null)
			throw new NullPointerException("Cannot add local tag entries to a null primer pack.");
		if (mdObject == null)
			throw new NullPointerException("Cannot add local tag entries to a primer pack using a null metadata object.");

		ClassDefinition objectsClass = MediaEngine.getClassDefinition(mdObject);
		long setLength = MXFBuilder.lengthOfLocalSet(mdObject) + 20l; // Bytes in value plus initial class ID and 4-byte length

		for ( PropertyDefinition propertyDefinition : objectsClass.getAllPropertyDefinitions() ) {

			PropertyValue propertyValue = null;
			try {
				if (propertyDefinition.getIsOptional())
					propertyDefinition.getPropertyValue(mdObject);
			}
			catch (IllegalArgumentException iae) {
				continue;
			}
			catch (PropertyNotPresentException pnpe) {
				continue;
			}

			primerPack.addLocalTagEntry(propertyDefinition);

			TypeDefinition propertyType = propertyDefinition.getTypeDefinition();

			switch (propertyType.getTypeCategory()) {

			case StrongObjRef:
				if (propertyValue == null)
					propertyValue = propertyDefinition.getPropertyValue(mdObject);
				MetadataObject stronglyReferencedObject = (MetadataObject) propertyValue.getValue();
				setLength += addPropertiesForObject(primerPack, stronglyReferencedObject);
				break;
			case Set:
				if (propertyValue == null)
					propertyValue = propertyDefinition.getPropertyValue(mdObject);
				TypeDefinition setElementType = ((TypeDefinitionSet) propertyType).getElementType();

				if (setElementType instanceof TypeDefinitionStrongObjectReference) {
					Set<?> setValues = (Set<?>) propertyValue.getValue();
					for ( Object value : setValues )
						setLength += addPropertiesForObject(primerPack, (MetadataObject) value);
				}
				break;
			case VariableArray:
				if (propertyValue == null)
					propertyValue = propertyDefinition.getPropertyValue(mdObject);
				TypeDefinition arrayElementType = ((TypeDefinitionVariableArray) propertyType).getType();

				if (arrayElementType instanceof TypeDefinitionStrongObjectReference) {
					List<?> arrayValues = (List<?>) propertyValue.getValue();
					for ( Object value : arrayValues )
						setLength += addPropertiesForObject(primerPack, (MetadataObject) value);
				}
				break;
			default:
				break;
			}
		}

		//Add local tags defined in the index packs
		PrimerPack indexPrimerPack = new IndexPrimerPackImpl();
		for(LocalTagEntry tagEntry : indexPrimerPack.getLocalTagEntryBatch()){
			primerPack.addLocalTagEntry(tagEntry);
		}

		return setLength;
	}

	public final static void main(
			String args[]) {

		if (args.length < 1) {
			System.err.println("Please provide the name of an MXF file to reflect.");
			System.exit(1);
		}

		String fileName = args[0];

		MXFFile mxfFile = MXFFactory.readPartitions(fileName);

		HeaderMetadata fromTheFooter = null;
//		for ( int x = 0 ; x < 100 ; x++ ) {
			long startTime = System.currentTimeMillis();
			fromTheFooter = mxfFile.getFooterPartition().readHeaderMetadata();
			long endTime = System.currentTimeMillis();
			System.out.println("INFO: Reading MXF File header data took " + (endTime - startTime) + "ms.");
//		}
		System.out.println(fromTheFooter.getPrimerPack().toString());
		//System.out.println(fromTheFooter.getPreface().toString());

//		System.out.println("Header partition has an index: " + mxfFile.getHeaderPartition().hasIndexTable());
//		IndexTable headerIndex = mxfFile.getHeaderPartition().readIndexTable();
//		System.out.println(headerIndex.toString());
//
//		System.out.println("Footer partition has an index: " + mxfFile.getFooterPartition().hasIndexTable());
//		IndexTable footerIndex = mxfFile.getFooterPartition().readIndexTable();
//		System.out.println(footerIndex.toString());
//
//		for ( int x = 0 ; x < 10 ; x++ )
//			System.out.println("Edit unit " + x + " has offset " + Integer.toHexString((int) (0x8000 + footerIndex.streamOffset(x, 2))));
//
		mxfFile.close();

		PrimerPack generatedPrimer = createFromPreface(fromTheFooter.getPreface());
		System.out.println(generatedPrimer.toString());
	}
}
