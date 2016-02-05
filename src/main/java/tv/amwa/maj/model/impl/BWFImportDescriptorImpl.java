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
 * $Log: BWFImportDescriptorImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/25 14:18:28  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
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
 * Revision 1.2  2007/12/04 13:04:47  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:41  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tv.amwa.maj.exception.ObjectAlreadyAttachedException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.BWFImportDescriptor;
import tv.amwa.maj.model.RIFFChunk;


/** 
 * <p>Implements the description of audio essence that is formatted according the the 
 * <a href="http://www.ebu.ch/en/technical/publications/userguides/bwf_user_guide.php">Broadcast Wave Format</a>
 * specifications, which is a file essence source that is not directly manipulated by an AAF application.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x5000,
		  uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "BWFImportDescriptor",
		  description = "Specifies audio essence represented according to the Broadcast Wave specifications.",
		  symbol = "BWFImportDescriptor")
public class BWFImportDescriptorImpl
	extends ImportDescriptorImpl
	implements 
		BWFImportDescriptor,
		Serializable,
		XMLSerializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -4831327473408052331L;

	private Integer qltyFileSecurityReport = null;
	private Integer qltyFileSecurityWave = null;
	private String bextCodingHistory = null;
	private String qltyBasicData = null;
	private String qltyStartOfModulation = null;
	private String qltyQualityEvent = null;
	private String qltyEndOfModulation = null;
	private String qltyQualityParameter = null;
	private String qltyOperatorComment = null;
	private String qltyCueSheet = null;
	
	private List<RIFFChunk> unknownBWFChunks = 
		Collections.synchronizedList(new ArrayList<RIFFChunk>());
	
	/**
	 * <p>Creates and initializes a new BWF import descriptor, which specifies audio data
	 * essence according to the Broadcast Wave specifications.</p>
	 *
	 */
	public BWFImportDescriptorImpl() { }

	@MediaProperty(uuid1 = 0x04020502, uuid2 = 0x0201, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "QltyBasicData",
			     typeName = "UTF16String",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D22,
			     symbol = "QltyBasicData")
	public String getBasicData()
			throws PropertyNotPresentException {

		if (qltyBasicData == null)
			throw new PropertyNotPresentException("The optional basic data property is not present in this BWF import descriptor.");
		
		return qltyBasicData;
	}

	@MediaPropertySetter("QltyBasicData")
	public void setBasicData(
			String basicData) {

		this.qltyBasicData = basicData;
	}
	
	@MediaProperty(uuid1 = 0x04020502, uuid2 = 0x0101, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "BextCodingHistory",
			     typeName = "UTF16String",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D21,
			     symbol = "BextCodingHistory")
	public String getCodingHistory()
			throws PropertyNotPresentException {

		if (bextCodingHistory == null)
			throw new PropertyNotPresentException("The optional coding history property is not present in this BWF import descriptor.");
		
		return bextCodingHistory;
	}

	@MediaPropertySetter("BextCodingHistory")
	public void setCodingHistory(
			String codingHistory) {

		this.bextCodingHistory = codingHistory;
	}
	
	@MediaProperty(uuid1 = 0x04020502, uuid2 = 0x0801, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "QltyCueSheet",
			     typeName = "UTF16String",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D28,
			     symbol = "QltyCueSheet")
	public String getCueSheet()
			throws PropertyNotPresentException {

		if (qltyCueSheet == null)
			throw new PropertyNotPresentException("The optional cue sheet property is not present in this BWF import descriptor.");
		
		return qltyCueSheet;
	}

	@MediaPropertySetter("QltyCueSheet")
	public void setCueSheet(
			String cueSheet) {

		this.qltyCueSheet = cueSheet;
	}
	
	@MediaProperty(uuid1 = 0x04020502, uuid2 = 0x0501, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "QltyEndOfModulation",
			     typeName = "UTF16String",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D25,
			     symbol = "QltyEndOfModulation")
	public String getEndOfModulation()
			throws PropertyNotPresentException {

		if (qltyEndOfModulation == null)
			throw new PropertyNotPresentException("The optional end of modulation property is not present in this BWF descriptor.");
		
		return this.qltyEndOfModulation;
	}

	@MediaPropertySetter("QltyEndOfModulation")
	public void setEndOfModulation(
			String endOfModulation) {

		this.qltyEndOfModulation = endOfModulation;
	}
	
	@MediaProperty(uuid1 = 0x04020302, uuid2 = 0x0500, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "QltyFileSecurityReport",
			     typeName = "UInt32",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D15,
			     symbol = "QltyFileSecurityReport")
	public int getFileSecurityReport()
			throws PropertyNotPresentException {

		if (qltyFileSecurityReport == null)
			throw new PropertyNotPresentException("The optional file security report value is not present this BWF import descriptor.");
		
		return qltyFileSecurityReport;
	}

	@MediaPropertySetter("QltyFileSecurityReport")
	public void setFileSecurityReport(
			Integer fileSecurityReport) {

		this.qltyFileSecurityReport = fileSecurityReport;
	}

	@MediaProperty(uuid1 = 0x04020302, uuid2 = 0x0600, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "QltyFileSecurityWave",
			     typeName = "UInt32",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D16,
			     symbol = "QltyFileSecurityWave")
	public int getFileSecurityWave()
			throws PropertyNotPresentException {

		if (qltyFileSecurityWave == null)
			throw new PropertyNotPresentException("The optional file security wave property is not present in this BWF import descriptor.");

		return qltyFileSecurityWave;
	}

	@MediaPropertySetter("QltyFileSecurityWave")
	public void setFileSecurityWave(
			Integer fileSecurityWave) {

		this.qltyFileSecurityWave = fileSecurityWave;
	}
	
	@MediaProperty(uuid1 = 0x04020502, uuid2 = 0x0701, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "QltyOperatorComment",
			     typeName = "UTF16String",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D27,
			     symbol = "QltyOperatorComment")
	public String getOperatorComment()
			throws PropertyNotPresentException {

		if (qltyOperatorComment == null)
			throw new PropertyNotPresentException("The optional operator comment property is not present in this BWF import descriptor.");
		
		return qltyOperatorComment;
	}

	@MediaPropertySetter("QltyOperatorComment")
	public void setOperatorComment(
			String operatorComment) {

		this.qltyOperatorComment = operatorComment;
	}
	
	@MediaProperty(uuid1 = 0x04020502, uuid2 = 0x0401, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "QltyQualityEvent",
			     typeName = "UTF16String",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D24,
			     symbol = "QltyQualityEvent")
	public String getQualityEvent()
			throws PropertyNotPresentException {

		if (qltyQualityEvent == null)
			throw new PropertyNotPresentException("The optional quality event property is not present in this BWF import descriptor.");
		
		return qltyQualityEvent;
	}

	@MediaPropertySetter("QltyQualityEvent")
	public void setQualityEvent(
			String qualityEvent) {

		this.qltyQualityEvent = qualityEvent;
	}
	
	@MediaProperty(uuid1 = 0x04020502, uuid2 = 0x0601, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "QltyQualityParameter",
			     typeName = "UTF16String",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D26,
			     symbol = "QltyQualityParameter")
	public String getQualityParameter()
			throws PropertyNotPresentException {

		if (qltyQualityParameter == null)
			throw new PropertyNotPresentException("The optional quality parameter is not present in this MWF import descriptor.");
		
		return qltyQualityParameter;
	}
	
	@MediaPropertySetter("QltyQualityParameter")
	public void setQualityParameter(
			String qualityParameter) {

		this.qltyQualityParameter = qualityParameter;
	}
	
	@MediaProperty(uuid1 = 0x04020502, uuid2 = 0x0301, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "QltyStartOfModulation",
			     typeName = "UTF16String",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D23,
			     symbol = "QltyStartOfModulation")
	public String getStartOfModulation()
			throws PropertyNotPresentException {

		if (qltyStartOfModulation == null)
			throw new PropertyNotPresentException("The optional start of modulation property is not present in this BWF import descriptor.");
		
		return qltyStartOfModulation;
	}

	@MediaPropertySetter("QltyStartOfModulation")
	public void setStartOfModulation(
			String startOfModulation) {

		this.qltyStartOfModulation = startOfModulation;
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = 0x060F, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x08},
			     definedName = "UnknownBWFChunks",
			     typeName = "RIFFChunkStrongReferenceVector",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D33,
			     symbol = "UnknownBWFChunks")
	public List<RIFFChunk> getUnknownBWFChunks() 
		throws PropertyNotPresentException {

		if (unknownBWFChunks.size() == 0)
			throw new PropertyNotPresentException("The optional list of unknown BWF chunks is not present for this BWF import descripto.");
		
		return StrongReferenceVector.getOptionalList(unknownBWFChunks);
	}
	
	@MediaListAppend("UnknownBWFChunks")
	public void appendUnknownBWFChunk(
			RIFFChunk chunk)
		throws NullPointerException,
			ObjectAlreadyAttachedException {

		if (chunk == null)
			throw new NullPointerException("Cannot append a null value to the list of unknown BWF chunks of this BWF import descriptor.");
		if (unknownBWFChunks.contains(chunk))
			throw new ObjectAlreadyAttachedException("The given RIFF chunk is already in the list of BWF chunks of this BWF import descriptor.");
		
		StrongReferenceVector.append(unknownBWFChunks, chunk);
	}

	@MediaPropertyCount("UnknownBWFChunks")
	public int countUnknownBWFChunks() {

		return unknownBWFChunks.size();
	}
	
	@MediaPropertyClear("UnknownBWFChunks")
	public void clearUnknownBWFChunks() {
		
		unknownBWFChunks = Collections.synchronizedList(new ArrayList<RIFFChunk>());
	}

	@MediaListGetAt("UnknownBWFChunks")
	public RIFFChunk getUnknownBWFChunkAt(
			int index)
		throws IndexOutOfBoundsException {
		
		return StrongReferenceVector.getAt(unknownBWFChunks, index);
	}

	@MediaListInsertAt("UnknownBWFChunks")
	public void insertUnknownBWFChunkAt(
			int index,
			RIFFChunk chunk)
		throws NullPointerException,
			IndexOutOfBoundsException,
			ObjectAlreadyAttachedException {

		if (chunk == null)
			throw new NullPointerException("Cannot insert a null value into the list of unknown BWF chunks of this BWF import descriptor.");
		if (unknownBWFChunks.contains(chunk))
			throw new ObjectAlreadyAttachedException("The given chunk is already contained in the list of unknown BWF chunks of this BWF import descriptor.");
		
		StrongReferenceVector.insert(unknownBWFChunks, index, chunk);
	}

	@MediaListPrepend("UnknownBWFChunks")
	public void prependUnknownBWFChunk(
			RIFFChunk chunk)
		throws NullPointerException,
			ObjectAlreadyAttachedException {

		if (chunk == null)
			throw new NullPointerException("Cannot prepend a null value to the list of unknown BWF chunks of this BWF import descriptor.");
		if (unknownBWFChunks.contains(chunk))
			throw new ObjectAlreadyAttachedException("The given chunk is already contained within the list of unknown BWF chunks of this BWF import descriptor.");

		StrongReferenceVector.prepend(unknownBWFChunks, chunk);
	}

	@MediaListRemoveAt("UnknownBWFChunks")
	public void removeUnknownBWFChunkAt(
			int index)
		throws IndexOutOfBoundsException {

		StrongReferenceVector.remove(unknownBWFChunks, index);
	}

	@Override
	public BWFImportDescriptor clone() {
		
		return (BWFImportDescriptor) super.clone();
	}
}
