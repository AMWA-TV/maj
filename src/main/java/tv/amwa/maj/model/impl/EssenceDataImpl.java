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

/*
 * $Log: EssenceDataImpl.java,v $
 * Revision 1.4  2011/07/27 17:31:02  vizigoth
 * Added bodySID and indexSID optional properties and made the essence stream optional for easier working with MXF files.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/11/08 15:33:15  vizigoth
 * Version with IO exceptions on stream manipulation methods.
 *
 * Revision 1.2  2010/07/14 13:34:35  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2008/01/27 11:14:42  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.1  2007/11/13 22:09:23  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.model.impl;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.NotImplementedException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.model.EssenceAccess;
import tv.amwa.maj.model.EssenceData;
import tv.amwa.maj.model.SourcePackage;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.impl.PackageIDImpl;

/** 
 * <p>Implements an essence container. The methods can be used to modify 
 * essence data objects that contain the actual essence data (excluding WAVE) when it is contained 
 * within an {@linkplain tv.amwa.maj.model.AAFFile AAF file}.  Normally, the client application 
 * would access the essence through the {@linkplain EssenceAccess essence access}
 * interface, which handles the work of finding and (de)compressing the data.
 * However, in rare cases direct access to the data is required, the {@linkplain #read(int) read}
 * and {@linkplain #write(ByteBuffer) write} methods of this interface are provided.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDataStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDataStrongReferenceSet
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2300,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "EssenceData",
		  description = "The EssenceData class contains essence.",
		  symbol = "EssenceData")
public class EssenceDataImpl
	extends InterchangeObjectImpl
	implements EssenceData,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 5290980225454498543L;
	
	private PackageID linkedPackageID;
	private Stream essenceStream = null;
	private Stream sampleIndex = null; // optional property
	private Integer indexSID = null;
	private Integer bodySID = null;
	private transient SourcePackage filePackage = null;
	
	public EssenceDataImpl() { }

	/**
	 * <p>Creates and initializes a new essence data object that contains essence. 
	 * This method requires a weak reference to the corresponding metadata 
	 * {@linkplain tv.amwa.maj.model.SourcePackage source package} of the essence data itself.</p>
	 * 
	 * @param sourcePackage Identifies the source package that describes the essence.
	 * @param data The essence data.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 */
	public EssenceDataImpl(
			tv.amwa.maj.model.SourcePackage sourcePackage,
			Stream data) 
		throws NullPointerException {
		
		if (sourcePackage == null)
			throw new NullPointerException("Cannot create a new item of essence data witn a null file package.");
		if (data == null)
			throw new NullPointerException("Cannot create a new item of essence data with null data.");
		
		setFilePackage(sourcePackage);
		setEssenceStream(data);
	}

    @MediaProperty(uuid1 = 0x06010106, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "LinkedPackageID",
			aliases = { "MobID", "EssenceDataMobID" },
			typeName = "PackageIDType", 
			optional = false,
			uniqueIdentifier = true,
			pid = 0x2701,
			symbol = "LinkedPackageID")
	public PackageID getLinkedPackageID() {

		return linkedPackageID.clone();
	}

	@MediaPropertySetter("LinkedPackageID")
	public void setLinkedPackageID(
			PackageID linkedPackageID) { 
		
		this.linkedPackageID = linkedPackageID.clone();
	}
	
	public final static PackageID initializeLinkedPackageID() {
		
		return PackageImpl.initializePackageID();
	}

	public SourcePackage getFilePackage() {

		// TODO add a file package lookup here?
		return filePackage;
	}

	public void setFilePackage(
		SourcePackage packageToSet)
			throws NullPointerException {

		if (packageToSet == null)
			throw new NullPointerException("Cannot create a new item of essence data witn a null file package.");

		this.filePackage = packageToSet;
			
		setLinkedPackageID(filePackage.getPackageID());
	}

	public long getPosition() throws IOException {

		return essenceStream.getPosition();
	}

	public long getSampleIndexPosition() 
		throws PropertyNotPresentException, IOException {

		if (sampleIndex == null)
			throw new PropertyNotPresentException("The optional sample index property is not present for this essence data.");
		
		return sampleIndex.getPosition();
	}

	public long getSampleIndexSize() 
		throws PropertyNotPresentException,
			IOException {

		if (sampleIndex == null)
			throw new PropertyNotPresentException("The optional sample index property is not present for this essence data.");		
		
		return sampleIndex.getLength();
	}

	public void setSampleIndexSize(
			@LengthType long sampleIndexSize)
		throws IllegalArgumentException, 
			NotImplementedException {
		
		if (sampleIndexSize == 0l) { // make the property optional
			sampleIndex = null;
			return;
		}
		
		// TODO implement in memory sample index buffers by extending Stream
		if (sampleIndex == null) {
			throw new NotImplementedException("Extending a not present sample index is not supported in this release of MAJ.");
		}
		
		sampleIndex.setLimit(sampleIndexSize);
	}

	public long getSize() 
		throws IOException {

		return essenceStream.getLength();
	}

	public ByteBuffer read(
			int bytes) 
		throws IOException, 
			EndOfDataException {

		return essenceStream.read(bytes);
	}

	public ByteBuffer readSampleIndex(
			int size) 
		throws PropertyNotPresentException,
			IOException, 
			EndOfDataException {

		if (sampleIndex == null)
			throw new PropertyNotPresentException("The optional sample index property is not present for this essence data.");
		
		return sampleIndex.read(size);
	}

	public void setPosition(
			long offset) 
		throws IllegalArgumentException, 
			IOException {

		essenceStream.setPosition(offset);
	}

	public void setSampleIndexPosition(
			@PositionType long offset) 
		throws PropertyNotPresentException, 
			IllegalArgumentException, 
			IOException {
		
		if (sampleIndex == null)
			throw new PropertyNotPresentException("The optional sample index property is not present for this essence data.");

		sampleIndex.setPosition(offset);
	}

	public int write(
			ByteBuffer buffer) 
		throws EndOfDataException, 
			IOException {

		return essenceStream.write(buffer);
	}

	public int writeSampleIndex(
			ByteBuffer buffer) 
		throws PropertyNotPresentException, EndOfDataException, IOException {
		
		if (sampleIndex == null)
			throw new PropertyNotPresentException("The optional sample index property is not present in for this essence data.");

		return sampleIndex.write(buffer);
	}
	
	/*
	 * Although essence stream is a required property in AAF, it is often not present in MXF sets as its
	 * representation is through the generic container. This property has been made optional in MAJ
	 * to support MXF applications that use bodySID and indexSID properties instead.
	 */
	@MediaProperty(uuid1 = 0x04070200, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EssenceStream",
			aliases = { "Data" },
			typeName = "Stream", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2702,
			symbol = "EssenceStream")
	public Stream getEssenceStream() 
		throws PropertyNotPresentException { 
		
		if (essenceStream == null)
			throw new PropertyNotPresentException("The optional essence data is not present, indicating that this is an MXF application using body stream identifiers instead.");
		
		return essenceStream.clone(); 
	}
	
	@MediaPropertySetter("EssenceStream")
	public void setEssenceStream(
			Stream data) { 
		
		if (data == null) {
			essenceStream = null;
			return;
		}
		
		this.essenceStream = data.clone(); 
	}
	
//	public final static Stream initializeEssenceStream() {
//		
//		return new MemoryResidentStream(0);
//	}
	
	@MediaProperty(uuid1 = 0x06010102, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "SampleIndex",
			typeName = "Stream",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2B01,
			symbol = "SampleIndex")
	public Stream getSampleIndex() 
		throws PropertyNotPresentException { 
		
		if (sampleIndex == null)
			throw new PropertyNotPresentException("Sample index data is not present for this essence data.");
		
		return sampleIndex.clone(); 
	}
	
	@MediaPropertySetter("SampleIndex")
	public void setSampleIndex(
			Stream sampleIndex) { 
		
		if (sampleIndex == null) {
			this.sampleIndex = null;
			return;
		}
		
		this.sampleIndex = sampleIndex.clone(); 
	}

	/** 
	 * <p>Cloned essence data contains the same file package and data, which are not themselves cloned.</p>
	 * 
	 * @see java.lang.Object#clone()
	 */
	public EssenceData clone() {
		
		return (EssenceData) super.clone();
	}

	public String getLinkedPackageIDString() {
		
		return PackageIDImpl.toPersistentForm(linkedPackageID);
	}
	
	public void setLinkedPackageIDString(
			String linkedPackageID) {
		
		this.linkedPackageID = PackageIDImpl.fromPersistentForm(linkedPackageID);
	}
	
	@MediaProperty(uuid1 = 0x01030405, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04},
			definedName = "IndexSID",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3f06,
			symbol = "IndexSID")
	public @UInt32 int getIndexSID() 
		throws PropertyNotPresentException {
		
		if (indexSID == null)
			throw new PropertyNotPresentException("The optional index stream identifier property is not present for this essence data.");
		
		return indexSID;
	}
	
	@MediaPropertySetter("IndexSID")
	public void setIndexSID(
			@UInt32 Integer indexSID)
		throws IllegalArgumentException {
		
		if (indexSID == null) {
			this.indexSID = null;
			return;
		}
		
		if (indexSID < 0)
			throw new IllegalArgumentException("Cannot set the index stream identifier to a negative value.");
		
		this.indexSID = indexSID;
	}
	
	@MediaProperty(uuid1 = 0x01030404, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04},
			definedName = "BodySID",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3f07,
			symbol = "BodySID")
	public @UInt32 int getBodySID()
		throws PropertyNotPresentException {
		
		if (bodySID == null)
			throw new PropertyNotPresentException("The optional body stream identifier property is not present for this essence data.");
		
		return bodySID;
	}
	
	@MediaPropertySetter("BodySID")
	public void setBodySID(
			@UInt32 Integer bodySID) 
		throws IllegalArgumentException {
		
		if (bodySID == null) {
			this.bodySID = null;
			return;
		}
		
		if (bodySID < 0)
			throw new IllegalArgumentException("Cannot set the body stream identifier to a negative value.");
		
		this.bodySID = bodySID;
	}
}
