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
 * $Log: TapeDescriptorImpl.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/05/20 18:52:14  vizigoth
 * Adding support for Avid extensions.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:49  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.TapeCaseType;
import tv.amwa.maj.enumeration.TapeFormatType;
import tv.amwa.maj.enumeration.VideoSignalType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.avid.AvidConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.model.TapeDescriptor;


/** 
 * <p>Implements the description of audio tape or video tape media. A 
 * {@linkplain tv.amwa.maj.model.SourcePackage source package} described by a tape descriptor 
 * is known as a <em>tape source package</em>.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2e00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TapeDescriptor",
		  description = "The TapeDescriptor class describes audio tape or video tape media.",
		  symbol = "TapeDescriptor")
public class TapeDescriptorImpl
	extends 
		EssenceDescriptorImpl
	implements 
		TapeDescriptor,
		tv.amwa.maj.extensions.avid.TapeDescriptor,
		Serializable,
		Cloneable {
	
	/** <p></p> */
	private static final long serialVersionUID = -5061283625147621078L;
	
	private TapeCaseType tapeFormFactor = null;
	private VideoSignalType videoSignal = null;
	private TapeFormatType tapeFormat = null;
	private Integer tapeCapacity = null;
	private String tapeManufacturer = null;
	private String tapeFormulation = null;
	private String tapeBatchNumber = null;
	private String tapeStock = null;
	
	/**
	 * <p>Creates and initializes a new tape descriptor, which describes audio tape or video 
	 * tape media.</p>
	 * 
	 */
	public TapeDescriptorImpl() {
	}

	@MediaProperty(uuid1 = 0x04010401, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "VideoSignal",
			typeName = "VideoSignalType", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3A02,
			symbol = "VideoSignal")
	public VideoSignalType getSignalType() 
		throws PropertyNotPresentException {

		if (videoSignal == null)
			throw new PropertyNotPresentException("The optional video signal type is not present in this tape descriptor.");
		
		return videoSignal;
	}

	@MediaPropertySetter("VideoSignal")
	public void setSignalType(
			VideoSignalType videoSignal) {
		
		this.videoSignal = videoSignal;
	}

	@MediaProperty(uuid1 = 0x04100101, uuid2 = (short) 0x0101, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TapeFormFactor",
			aliases = { "FormFactor" },
			typeName = "TapeCaseType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3A01,
			symbol = "TapeFormFactor")
	public TapeCaseType getTapeFormFactor() 
		throws PropertyNotPresentException {

		if (tapeFormFactor == null)
			throw new PropertyNotPresentException("The optional tape form factor property is not present for this tape descriptor.");
		
		return tapeFormFactor;
	}

	@MediaPropertySetter("TapeFormFactor")
	public void setTapeFormFactor(
			TapeCaseType tapeFormFactor) {

		this.tapeFormFactor = tapeFormFactor;
	}

	@MediaProperty(uuid1 = 0x0d010101, uuid2 = (short) 0x0101, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TapeFormat",
			typeName = "TapeFormatType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3A03,
			symbol = "TapeFormat")
	public TapeFormatType getTapeFormat() 
		throws PropertyNotPresentException {

		if (tapeFormat == null)
			throw new PropertyNotPresentException("The optional tape format property is not present for this tape descriptor");
		
		return tapeFormat;
	}
	
	@MediaPropertySetter("TapeFormat")
	public void setTapeFormat(
			TapeFormatType tapeFormat) {

		this.tapeFormat = tapeFormat;
	}

	@MediaProperty(uuid1 = 0x04100101, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TapeCapacity", 
			aliases = { "Length", "TapeDescriptorLength" },
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3A04,
			symbol = "TapeCapacity")
	public int getTapeCapacity() 
		throws PropertyNotPresentException {

		if (tapeCapacity == null)
			throw new PropertyNotPresentException("The optional tape capacity property is not present in this tape descriptor.");

		return tapeCapacity;
	}

	@MediaPropertySetter("TapeCapacity")
	public void setTapeCapacity(
			Integer tapeCapacity) 
		throws IllegalArgumentException {

		if (tapeCapacity == null) {
			this.tapeCapacity = null;
			return;
		}
		
		if (tapeCapacity < 0)
			throw new IllegalArgumentException("Cannot set the length for this tape descriptor to a negative value.");
		
		this.tapeCapacity = tapeCapacity;
	}

	@MediaProperty(uuid1 = 0x04100101, uuid2 = (short) 0x0401, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TapeManufacturer",
			aliases = { "ManufcaturerID", "TapeDescriptorManufacturerID" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3A05,
			symbol = "TapeManufacturer")
	public String getTapeManufacturer() 
		throws PropertyNotPresentException {

		if (tapeManufacturer == null)
			throw new PropertyNotPresentException("The optional tape manufacturer property is not present in this tape descriptor.");

		return tapeManufacturer;
	}

	@MediaPropertySetter("TapeManufacturer")
	public void setTapeManufacturer(
			String tapeManufacturer) {

		this.tapeManufacturer = tapeManufacturer;
	}

	@MediaProperty(uuid1 = 0x04100101, uuid2 = (short) 0x0201, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TapeFormulation",
			aliases = { "Model", "TapeDescriptorModel" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3A06,
			symbol = "TapeFormulation")
	public String getTapeFormuulation() 
		throws PropertyNotPresentException {

		if (tapeFormulation == null)
			throw new PropertyNotPresentException("The optional tape formulation property is not present in this tape descriptor.");
		
		return tapeFormulation;
	}

	@MediaPropertySetter("TapeFormulation")
	public void setTapeFormulation(
			String tapeFormulation) {

		this.tapeFormulation = tapeFormulation;
	}

	@MediaProperty(uuid1 = 0x04100101, uuid2 = (short) 0x0601, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TapeBatchNumber",
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3A07,
			symbol = "TapeBatchNumber")
	public String getTapeBatchNumber()
			throws PropertyNotPresentException {

		if (tapeBatchNumber == null)
			throw new PropertyNotPresentException("The optional tape batch number property is not present in this tape descriptor.");
		
		return tapeBatchNumber;
	}

	@MediaPropertySetter("TapeBatchNumber")
	public void setTapeBatchNumber(
			String tapeBatchNumber) {

		this.tapeBatchNumber = tapeBatchNumber;
	}

	@MediaProperty(uuid1 = 0x04100101, uuid2 = (short) 0x0501, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TapeStock",
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3A08,
			symbol = "TapeStock")
	public String getTapeStock()
			throws PropertyNotPresentException {

		if (tapeStock == null)
			throw new PropertyNotPresentException("The optional tape stock property is not present for this tape descriptor.");

		return tapeStock;
	}

	@MediaPropertySetter("TapeStock")
	public void setTapeStock(
			String tapeStock) {

		this.tapeStock = tapeStock;
	}

	public TapeDescriptor clone() {
		
		return (TapeDescriptor) super.clone();
	}
	
	// AVID extension properties - start
	
	private @Int32 Integer colorFrame = null;
	
	@MediaProperty(uuid1 = 0x9548b03a, uuid2 = (short) 0x15fb, uuid3 = (short) 0x11d4,
			uuid4 = { (byte) 0xa0, (byte) 0x8f, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb },
			definedName = "ColorFrame",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "ColorFrame",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public @Int32 int getColorFrame()
		throws PropertyNotPresentException {
		
		if (colorFrame == null)
			throw new NullPointerException("The optional color frame property is not present for this tape descriptor.");
		
		return colorFrame;
	}
	
	@MediaPropertySetter("ColorFrame")
	public void setColorFrame(
			@Int32 Integer colorFrame) {
		
		this.colorFrame = colorFrame;
	}
	
	// AVID extension properties - end
}
