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
 * $Log: TIFFDescriptorImpl.java,v $
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
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
 * Revision 1.1  2007/11/13 22:09:38  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.misctype.DataValue;
import tv.amwa.maj.misctype.JPEGTableIDType;
import tv.amwa.maj.model.ContainerDefinition;
import tv.amwa.maj.model.TIFFDescriptor;


/** 
 * <p>Implements the description of a file of video essence formatted according to the 
 * Tagged Image File Format (TIFF) specification.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2b00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TIFFDescriptor",
		  description = "The TIFFDescriptor class specifies that a File SourcePackage is associated with video essence formatted according to the TIFF specification.",
		  symbol = "TIFFDescriptor")
public class TIFFDescriptorImpl
	extends 
		AAFFileDescriptorImpl
	implements 
		TIFFDescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 258831607340293352L;
	
	private static final int JT_NULL = 0; // TODO find out what this should be! 

	private boolean isUniform;
	private boolean isContiguous;
	private Integer leadingLines = null;
	private Integer trailingLines = null;
	private @JPEGTableIDType Integer jpegTableID = JT_NULL;
	private byte[] tiffSummary;
	
	public TIFFDescriptorImpl() { }

	/**
	 * <p>Creates and initializes a new TIFF descritpor, which specifies that a 
	 * {@link SourcePackageImpl file source package} is associated with video essence formatted according 
	 * to the TIFF specification.</p>
	 *
	 * @param containerFormat Container format of the TIFF data.
	 * @param isUniform Does the data have the same number of rows per strip throughout?
	 * @param isContiguous Is the data stored in contiguous bytes?
	 * @param summary A copy of the TIFF IFD, without the sample data.
	 * 
	 * @throws NullPointerException The container format and/or summary arguments are <code>null</code>
	 * and both are required.
	 */
	public TIFFDescriptorImpl(
			ContainerDefinition containerFormat,
			@Bool boolean isUniform,
			@Bool boolean isContiguous,
			@DataValue byte[] summary)
		throws NullPointerException {
		
		if (summary == null)
			throw new NullPointerException("Cannot create a new TIFF descriptor using a null summary.");
		
		setContainerFormat(containerFormat);
		setIsUniform(isUniform);
		setIsContiguous(isContiguous);
		setTIFFSummary(summary);
	}

	@MediaProperty(uuid1 = 0x06080201, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "IsContiguous",
			typeName = "Boolean",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3702,
			symbol = "IsContiguous")
	public boolean getIsContiguous() {

		return isContiguous;
	}

	@MediaPropertySetter("IsContiguous")
	public void setIsContiguous(
			boolean isContiguous) {

		this.isContiguous = isContiguous;
	}
	
	public final static boolean initializeIsContiguous() {
		
		return false;
	}

	@MediaProperty(uuid1 = 0x05020103, uuid2 = (short) 0x0101, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "IsUniform",
			typeName = "Boolean",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3701,
			symbol = "IsUniform")
	public boolean getIsUniform() {
		
		return isUniform;
	}

	@MediaPropertySetter("IsUniform")
	public void setIsUniform(
			boolean isUniform) {

		this.isUniform = isUniform;
	}
	
	public final static boolean initializeIsUniform() {
		
		return false;
	}

	@MediaProperty(uuid1 = 0x05020103, uuid2 = (short) 0x0102, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "JPEGTableID",
			typeName = "JPEGTableIDType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3705,
			symbol = "JPEGTableID")
	public int getJPEGTableID() 
		throws PropertyNotPresentException {

		if (jpegTableID == null)
			throw new PropertyNotPresentException("The optional JPEG table id property is not present in TIFF descriptor.");

		return jpegTableID;
	}

	@MediaPropertySetter("JPEGTableID")
	public void setJPEGTableID(
			Integer jpegTableID) {

		this.jpegTableID = jpegTableID;
	}

	@MediaProperty(uuid1 = 0x04010302, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "LeadingLines",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3703,
			symbol = "LeadingLines")
	public int getLeadingLines() {

		if (leadingLines == null)
			return LEADINGLINES_DEFAULT;
		else
			return leadingLines;
	}

	@MediaPropertySetter("LeadingLines")
	public void setLeadingLines(
			Integer leadingLines) {
		
		this.leadingLines = leadingLines;
	}

	@MediaProperty(uuid1 = 0x03030302, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TIFFSummary", 
			aliases = { "Summary", "TIFFDescriptorSummary" },
			typeName = "DataValue",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3706,
			symbol = "TIFFSummary")
	public byte[] getTIFFSummary() {

		return tiffSummary.clone();
	}

	@MediaPropertySetter("TIFFSummary")
	public void setTIFFSummary(
			byte[] tiffSummary)
		throws NullPointerException {

		if (tiffSummary == null)
			throw new NullPointerException("Cannot set the summary of a TIFF descriptor to a null value.");
		
		this.tiffSummary = tiffSummary.clone();
	}

	public int getSummaryBufferSize() {

		return tiffSummary.length;
	}
	
	public final static byte[] initializeTIFFSummary() {
		
		return new byte[0];
	}

	@MediaProperty(uuid1 = 0x04010302, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "TrailingLines",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3704,
			symbol = "TrailingLines")
	public int getTrailingLines() {

		if (trailingLines == null)
			return TRAILINGLINES_DEFAULT;
		else
			return trailingLines;
	}

	@MediaPropertySetter("TrailingLines")
	public void setTrailingLines(
			Integer trailingLines) {

		this.trailingLines = trailingLines;
	}

	public TIFFDescriptor clone() {
		
		return (TIFFDescriptor) super.clone();
	}
}
