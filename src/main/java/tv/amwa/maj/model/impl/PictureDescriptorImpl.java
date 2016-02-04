/*
 * Copyright 2016 Advanced Media Workflow Assocation
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
 * $Log: PictureDescriptorImpl.java,v $
 * Revision 1.5  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.4  2011/07/27 17:32:40  vizigoth
 * Changed incorrect video line map size from an exception to a warning.
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
 * Revision 1.4  2010/12/15 19:07:21  vizigoth
 * Added aliase to be closer to what MXFDump produces.
 *
 * Revision 1.3  2010/05/20 18:52:14  vizigoth
 * Adding support for Avid extensions.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2007/12/04 09:25:01  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:09:07  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tv.amwa.maj.enumeration.AlphaTransparencyType;
import tv.amwa.maj.enumeration.FieldNumber;
import tv.amwa.maj.enumeration.LayoutType;
import tv.amwa.maj.enumeration.SignalStandardType;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.avid.AvidConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.model.PictureDescriptor;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.record.impl.RationalImpl;

/** 
 * <p>Implements the description of video content data formatted either using RGBA or
 * luminance/chrominance formatting.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2700,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "PictureDescriptor",
		  aliases = { "DigitalImageDescriptor" },
		  description = "The DigitalImageDescriptor class specifies that a File SourcePackage is associated with video essence that is formatted either using RGBA or luminance/chrominance formatting.",
		  symbol = "PictureDescriptor",
		  isConcrete = false)
public class PictureDescriptorImpl
	extends 
		AAFFileDescriptorImpl
	implements 
		PictureDescriptor,
		tv.amwa.maj.extensions.avid.PictureDescriptor,
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5920151389391521249L;
	
	private AUID pictureCompression = null; // Default value is uncompressed == null
	private int storedWidth;
	private int storedHeight;
	private Integer storedF2Offset = null;
	
	private int sampledHeight = 0;
	private int sampledWidth = 0;
	private int sampledXOffset = 0;
	private int sampledYOffset = 0;
	private boolean sampledValuesPresent = false;
	
	private int displayHeight = 0;
	private int displayWidth = 0;
	private int displayXOffset = 0;
	private int displayYOffset = 0;
	private boolean displayValuesPresent = false;
	private Integer displayF2Offset = null;
	
	private Byte activeFormatDescriptor = null; // Default value is "unspecified" == null
	private AlphaTransparencyType alphaTransparency = null;
	private AUID codingEquations = null; // Default value is "unspecified" == null
	private AUID colorPrimaries = null; // Default value is "unspecified" == null
	private FieldNumber fieldDominance = null; // Null represents 'optional - not present'
	private Integer imageStartOffset = null;
	private Integer imageEndOffset = null;
	private LayoutType frameLayout;
	private Integer imageAlignmentFactor = null;
	private Rational imageAspectRatio;
	private SignalStandardType signalStandard = null; // Default value is "unspecified" == null
	private AUID transferCharacteristic = null; // Default value is "unspecified" == null
	private int[] videoLineMap;
	
	@MediaProperty(uuid1 = 0x04010302, uuid2 = (short) 0x0900, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "ActiveFormatDescriptor",
			typeName = "UInt8",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3218,
			symbol = "ActiveFormatDescriptor")
	public byte getActiveFormatDescriptor() 
		throws PropertyNotPresentException {

		if (activeFormatDescriptor == null)
			throw new PropertyNotPresentException("The optional active format descriptor is not present in this picture descriptor.");
		
		return activeFormatDescriptor;
	}

	@MediaPropertySetter("ActiveFormatDescriptor")
	public void setActiveFormatDescriptor(
			Byte activeFormatDescriptor) 
		throws IllegalArgumentException {
		
		if (activeFormatDescriptor == null) {
			this.activeFormatDescriptor = null;
			return;
		}
		
		if ((activeFormatDescriptor < 0) || (activeFormatDescriptor > 15))
			throw new IllegalArgumentException("The active format descriptor is a 4-bit value and so cannot lie outside the range 0 to 15.");
		else
			this.activeFormatDescriptor = activeFormatDescriptor;
	}

	@MediaProperty(uuid1 = 0x05200102, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "AlphaTransparency",
			typeName = "AlphaTransparencyType", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x320F,
			symbol = "AlphaTransparency")
	public AlphaTransparencyType getAlphaTransparency() {

		if (alphaTransparency == null)
			return ALPHATRANSPARENCY_DEFAULT;
		else
			return alphaTransparency;
	}

	@MediaPropertySetter("AlphaTransparency")
	public void setAlphaTransparency(
			AlphaTransparencyType alphaTransparency) {
		
		this.alphaTransparency = alphaTransparency;
	}

	@MediaProperty(uuid1 = 0x04010201, uuid2 = (short) 0x0103, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "CodingEquations",
			typeName = "CodingEquationsType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x321A,
			symbol = "CodingEquations")
	public AUID getCodingEquations() 
		throws PropertyNotPresentException {

		if (codingEquations == null)
			throw new PropertyNotPresentException("The optional coding equations property is not present in this RGBA descriptor.");
		
		return codingEquations.clone();
	}

	@MediaPropertySetter("CodingEquations")
	public void setCodingEquations(
			tv.amwa.maj.record.AUID codingEquations) {

		if (codingEquations == null)
			this.codingEquations = null;
		else
			this.codingEquations = codingEquations.clone();
	}

	@MediaProperty(uuid1 = 0x04010201, uuid2 = (short) 0x0106, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x09},
			definedName = "ColorPrimaries",
			typeName = "ColorPrimariesType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3219,
			symbol = "ColorPrimaries")
	public AUID getColorPrimaries() 
		throws PropertyNotPresentException {

		if (colorPrimaries == null)
			throw new PropertyNotPresentException("The optional color primaries type is not present in this picture descriptor.");
		
		return colorPrimaries.clone();
	}

	@MediaPropertySetter("ColorPrimaries")
	public void setColorPrimaries(
			tv.amwa.maj.record.AUID colorPrimaries) {
		
		if (colorPrimaries == null)
			this.colorPrimaries = null;
		else
			this.colorPrimaries = colorPrimaries.clone();
	}

	@MediaProperty(uuid1 = 0x04010601, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PictureCompression",
			aliases = { "Compression", "DigitalImageDescriptorCompression", "PictureEssenceCoding" },
			typeName = "AUID", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3201,
			symbol = "PictureCompression")
	public AUID getPictureCompression() 
		throws PropertyNotPresentException {

		if (pictureCompression == null)
			throw new PropertyNotPresentException("The optional compression property is not present in this picture descriptor.");
		
		return pictureCompression.clone();
	}

	@MediaPropertySetter("PictureCompression")
	public void setPictureCompression(
			tv.amwa.maj.record.AUID pictureCompression) {
		
		if (pictureCompression == null)
			this.pictureCompression = null;
		else
			this.pictureCompression = pictureCompression.clone();
	}

	@MediaProperty(uuid1 = 0x04010302, uuid2 = (short) 0x0700, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "DisplayF2Offset",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3217,
			symbol = "DisplayF2Offset")
	public int getDisplayF2Offset() {

		if (displayF2Offset == null)
			return DISPLAYF2OFFSET_DEFAULT;
		else
			return displayF2Offset;
	}

	@MediaPropertySetter("DisplayF2Offset")
	public void setDisplayF2Offset(
			Integer displayF2Offset) 
		throws IllegalArgumentException {

		if (displayF2Offset == null) {
			this.displayF2Offset = null;
			return;
		}
		
		if ((displayF2Offset != 0) && (displayF2Offset != 1))
			throw new IllegalArgumentException("The given display field 2 offset is not one of the valid values of 0 and 1.");
		
		this.displayF2Offset = displayF2Offset;
	}

	public int[] getDisplayView() {

		if (displayValuesPresent == false)
			return new int[] { storedHeight, storedWidth, 0, 0 };
		else
			return new int[] { displayHeight, displayWidth, displayXOffset, displayYOffset };
	}

	public void setDisplayView(
			int displayHeight,
			int displayWidth,
			int displayXOffset,
			int displayYOffset)
		throws IllegalPropertyValueException {

		if ((displayXOffset < 0) || (displayXOffset >= storedWidth))
			throw new IllegalPropertyValueException("The given display x offset is outside the acceptable range for the stored width of this picture descriptor.");
		if ((displayYOffset < 0) || (displayYOffset >= storedHeight))
			throw new IllegalPropertyValueException("The given display y offset is outside the acceptable range for the stored height of this picture descriptor.");
		if ((displayHeight < 0) || (displayHeight > (storedHeight - displayYOffset)))
			throw new IllegalPropertyValueException("The given display height is outside the available range for the given offsets.");
		if ((displayWidth < 0) || (displayWidth > (storedWidth - displayXOffset)))
			throw new IllegalPropertyValueException("The given display width is outside the available range for the given offsets.");
	
		this.displayHeight = displayHeight;
		this.displayWidth = displayWidth;
		this.displayXOffset = displayXOffset;
		this.displayYOffset = displayYOffset;
		this.displayValuesPresent = true;
	}

	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x0b00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "DisplayHeight",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3208,
			symbol = "DisplayHeight")
	public int getDisplayHeight() {
		
		if (displayValuesPresent == false)
			return storedHeight;
		else
			return displayHeight;
	}
	
	@MediaPropertySetter("DisplayHeight")
	public void setDisplayHeight(
			int displayHeight) 
		throws IllegalArgumentException {
		
		if (displayHeight < 0)
			throw new IllegalArgumentException("The display height of a picture descriptor cannot be negative.");
		
		this.displayHeight = displayHeight;
		this.displayValuesPresent = true;
	}

	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x0c00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "DisplayWidth",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3209,
			symbol = "DisplayWidth")
	public int getDisplayWidth() {
		
		if (displayValuesPresent == false)
			return storedWidth;
		else
			return displayWidth;
	}
	
	@MediaPropertySetter("DisplayWidth")
	public void setDisplayWidth(
			int displayWidth) 
		throws IllegalArgumentException {
		
		if (displayWidth < 0)
			throw new IllegalArgumentException("Cannot set the display width of this picture descriptor to a negative value.");
		
		this.displayWidth = displayWidth;
		this.displayValuesPresent = true;
	}

	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x0d00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "DisplayXOffset",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x320A,
			symbol = "DisplayXOffset")
	public int getDisplayXOffset() {
		
		if (displayValuesPresent == false)
			return 0;
		else
			return displayXOffset;
	}
	
	@MediaPropertySetter("DisplayXOffset")
	public void setDisplayXOffset(
			int displayXOffset) {
		
		this.displayXOffset = displayXOffset;
		this.displayValuesPresent = true;
	}

	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x0e00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "DisplayYOffset",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x320B,
			symbol = "DisplayYOffset")
	public int getDisplayYOffset() {
		
		if (displayValuesPresent == false)
			return 0;
		else
			return displayYOffset;
	}
	
	@MediaPropertySetter("DisplayYOffset")
	public void setDisplayYOffset(
			int displayYOffset) {
		
		this.displayYOffset = displayYOffset;
		this.displayValuesPresent = true;
	}

	public boolean areDisplayValuesPresent() {
		
		return displayValuesPresent;
	}

	@MediaProperty(uuid1 = 0x04010301, uuid2 = (short) 0x0600, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FieldDominance",
			typeName = "FieldNumber",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3212,
			symbol = "FieldDominance")
	public FieldNumber getFieldDominance() 
		throws PropertyNotPresentException {
		
		if (fieldDominance == null)
			throw new PropertyNotPresentException("The optional field dominance property is not present in this picture descriptor.");
		
		return fieldDominance;
	}

	@MediaPropertySetter("FieldDominance")
	public void setFieldDominance(
			FieldNumber fieldDominance) {

		this.fieldDominance = fieldDominance;
	}

	@MediaProperty(uuid1 = 0x04180103, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ImageEndOffset",
			aliases = { "FieldEndOffset" },
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3214,
			symbol = "ImageEndOffset")
	public int getImageEndOffset() {

		if (imageEndOffset == null)
			return IMAGEENDOFFSET_DEFAULT;
		else
			return imageEndOffset;
	}

	@MediaPropertySetter("ImageEndOffset")
	public void setImageEndOffset(
			Integer imageEndOffset) 
		throws IllegalArgumentException {
		
		if (imageEndOffset == null) {
			this.imageEndOffset = null;
			return;
		}
		
		if (imageEndOffset < 0) 
			throw new IllegalArgumentException("Cannot set the image end offset value to a negative value for this picture descriptor.");
		
		// TODO Check upper bound?
		
		this.imageEndOffset = imageEndOffset;
	}

	@MediaProperty(uuid1 = 0x04180102, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ImageStartOffset",
			aliases = { "FieldStartOffset" },
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3213,
			symbol = "ImageStartOffset")
	public int getImageStartOffset() {

		if (imageStartOffset == null)
			return IMAGESTARTOFFSET_DEFAULT;
		else
			return imageStartOffset;
	}

	@MediaPropertySetter("ImageStartOffset")
	public void setImageStartOffset(
			Integer imageStartOffset) 
		throws IllegalArgumentException {
		
		if (imageStartOffset == null) {
			this.imageStartOffset = null;
			return;
		}
		
		if (imageStartOffset < 0)
			throw new IllegalArgumentException("Cannot set the image end offset to a negative value for this picture descriptor.");
		
		// TODO Check upper bound and/or overlap with field end offset etc.?
		
		this.imageStartOffset = imageStartOffset;
	}

	@MediaProperty(uuid1 = 0x04010301, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "FrameLayout",
			typeName = "LayoutType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x320C,
			symbol = "FrameLayout")
	public LayoutType getFrameLayout() {

		return frameLayout;
	}

	@MediaPropertySetter("FrameLayout")
	public void setFrameLayout(
			LayoutType frameLayout) 
		throws NullPointerException {
		
		if (frameLayout == null)
			throw new NullPointerException("Cannot set the frame layout of this picture descriptor using a null value.");
		
		this.frameLayout = frameLayout;
	}
	
	public final static LayoutType initializeFrameLayout() {
		
		return LayoutType.FullFrame;
	}

	@MediaProperty(uuid1 = 0x04180101, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ImageAlignmentFactor",
			aliases = { "ImageAlignmentOffset" },
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3211,
			symbol = "ImageAlignmentFactor")
	public int getImageAlignmentFactor() {

		if (imageAlignmentFactor == null)
			return IMAGEALIGNMENTFACTOR_DEFAULT;
		else
			return imageAlignmentFactor;
	}

	@MediaPropertySetter("ImageAlignmentFactor")
	public void setImageAlignmentFactor(
			Integer imageAlignmentFactor) 
		throws IllegalArgumentException {

		if (imageAlignmentFactor == null) {
			this.imageAlignmentFactor = null;
			return;
		}
		
		if (imageAlignmentFactor < 0)
			throw new IllegalArgumentException("Cannot set the image alignment factor to a negative value for this picture descriptor.");

		// TODO any properties to check here?
		
		this.imageAlignmentFactor = imageAlignmentFactor;
	}

	@MediaProperty(uuid1 = 0x04010101, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "ImageAspectRatio",
			aliases = { "AspectRatio" },
			typeName = "Rational",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x320E,
			symbol = "ImageAspectRatio")
	public Rational getImageAspectRatio() {

		return imageAspectRatio.clone();
	}

	@MediaPropertySetter("ImageAspectRatio")
	public void setImageAspectRatio(
			Rational imageAspectRatio)
		throws NullPointerException {

		if (imageAspectRatio == null)
			throw new NullPointerException("Cannot set the image aspect ratio of this picture descriptor to a null value.");
		
		this.imageAspectRatio = imageAspectRatio.clone();
	}
	
	public final static Rational initializeImageAspectRatio() {
		
		return new RationalImpl(1, 1);
	}

	public int[] getSampledView() {

		if (sampledValuesPresent == false)
			return new int[] { storedHeight, storedWidth, 0, 0 };
		else
			return new int[] { sampledHeight, sampledWidth, sampledXOffset, sampledYOffset };
	}

	public void setSampledView(
			int sampledHeight,
			int sampledWidth,
			int sampledXOffset,
			int sampledYOffset)
		throws IllegalPropertyValueException {

		if ((sampledXOffset < 0) || (sampledXOffset >= storedWidth))
			throw new IllegalArgumentException("The given sampled x offset is outside the acceptable range for the stored width of this picture descriptor.");
		if ((sampledYOffset < 0) || (sampledYOffset >= storedHeight))
			throw new IllegalArgumentException("The given sampled y offset is outside the acceptable range for the stored height of this picture descriptor.");
		
		if ((sampledHeight < 0) || (sampledHeight > (storedHeight - sampledYOffset)))
			throw new IllegalPropertyValueException("The given sampled height is outside the available range for the given offsets.");
		if ((sampledWidth < 0) || (sampledWidth > (storedWidth - sampledXOffset)))
			throw new IllegalArgumentException("The given sampled width is outside the available range for the given offsets.");

		this.sampledHeight = sampledHeight;
		this.sampledWidth = sampledWidth;
		this.sampledXOffset = sampledXOffset;
		this.sampledYOffset = sampledYOffset;
		this.sampledValuesPresent = true;
	}

	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x0700, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "SampledHeight",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3204,
			symbol = "SampledHeight")
	public int getSampledHeight() {
		
		if (sampledValuesPresent == false) 
			return storedHeight;
		else
			return sampledHeight;
	}
	
	@MediaPropertySetter("SampledHeight")
	public void setSampledHeight(
			int sampledHeight) 
		throws IllegalArgumentException {
		
		if (sampledHeight < 0)
			throw new IllegalArgumentException("Cannot set the sampled height for this picture descriptor to a negative value.");
		
		this.sampledHeight = sampledHeight;
		this.sampledValuesPresent = true;
	}

	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x0800, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "SampledWidth",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3205,
			symbol = "SampledWidth")
	public int getSampledWidth() {
		
		if (sampledValuesPresent == false)
			return storedWidth;
		else
			return sampledWidth;
	}

	@MediaPropertySetter("SampledWidth")
	public void setSampledWidth(
			int sampledWidth) 
		throws IllegalArgumentException {
		
		if (sampledWidth < 0)
			throw new IllegalArgumentException("Cannot set the sampled width of this picture descriptor to a negative value.");
		
		this.sampledWidth = sampledWidth;
		this.sampledValuesPresent = true;
	}

	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x0900, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "SampledXOffset",
			typeName = "Int32", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3206,
			symbol = "SampledXOffset")
	public int getSampledXOffset() {
		
		if (sampledValuesPresent == false)
			return 0;
		else
			return sampledXOffset;
	}
	
	@MediaPropertySetter("SampledXOffset")
	public void setSampledXOffset(
			int sampledXOffset) {
		
		this.sampledXOffset = sampledXOffset;
		this.sampledValuesPresent = true;
	}

	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x0a00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "SampledYOffset",
			typeName = "Int32", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3207,
			symbol = "SampledYOffset")
	public int getSampledYOffset() {
		
		if (sampledValuesPresent == false)
			return 0;
		else
			return sampledYOffset;
	}
	
	@MediaPropertySetter("SampledYOffset")
	public void setSampledYOffset(
			int sampledYOffset) {
		
		this.sampledYOffset = sampledYOffset;
		this.sampledValuesPresent = true;
	}

	public boolean areSampledValuesPresent() {
		
		return sampledValuesPresent;
	}

	@MediaProperty(uuid1 = 0x04050113, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "SignalStandard",
			typeName = "SignalStandardType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3215,
			symbol = "SignalStandard")
	public SignalStandardType getSignalStandard() 
		throws PropertyNotPresentException {

		if (signalStandard == null)
			throw new PropertyNotPresentException("The optional signal standard property is not present in this picture descriptor.");
		
		return signalStandard;
	}

	@MediaPropertySetter("SignalStandard")
	public void setSignalStandard(
			SignalStandardType signalStandard) {

		this.signalStandard = signalStandard;
	}

	@MediaProperty(uuid1 = 0x04010302, uuid2 = (short) 0x0800, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "StoredF2Offset",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3216,
			symbol = "StoredF2Offset")
	public int getStoredF2Offset() {

		if (storedF2Offset == null)
			return STOREDF2OFFSET_DEFAULT;
		else
			return storedF2Offset;
	}

	@MediaPropertySetter("StoredF2Offset")
	public void setStoredF2Offset(
			Integer storedF2Offset) 
		throws IllegalArgumentException {

		if (storedF2Offset == null) {
			this.storedF2Offset = null;
			return;
		}
		
		if ((storedF2Offset != 0) && (storedF2Offset != -1))
			throw new IllegalArgumentException("The given stored field 2 offset is not one of the valid values of 0 and -1.");
		
		this.storedF2Offset = storedF2Offset;
	}

	public int[] getStoredView() {

		return new int[] { storedHeight, storedWidth };
	}

	public void setStoredView(
			int storedHeight,
			int storedWidth) 
		throws IllegalArgumentException {

		setStoredHeight(storedHeight);
		setStoredWidth(storedWidth);
	}

	@MediaProperty(uuid1 = 0x04010502, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "StoredHeight",
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3202,
			symbol = "StoredHeight")
	public int getStoredHeight() {
		
		return storedHeight;
	}

	@MediaPropertySetter("StoredHeight")
	public void setStoredHeight(
			int storedHeight) 
		throws IllegalArgumentException {
		
		if (storedHeight < 0) 
			throw new IllegalArgumentException("The given stored height is negative, which is not permitted.");
		
		this.storedHeight = storedHeight;
	}
	
	public final static int initializeStoredHeight() {
		
		return 0;
	}

	@MediaProperty(uuid1 = 0x04010502, uuid2 = (short) 0x0200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "StoredWidth",
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3203,
			symbol = "StoredWidth")
	public int getStoredWidth() {
		
		return storedWidth;
	}

	@MediaPropertySetter("StoredWidth")
	public void setStoredWidth(
			int storedWidth) 
		throws IllegalArgumentException {
		
		if (storedWidth < 0)
			throw new IllegalArgumentException("The given stored width is negative, which is not permitted.");
		
		this.storedWidth = storedWidth;
	}
	
	public final static int initializeStoredWidth() {
		
		return 0;
	}

	@MediaProperty(uuid1 = 0x04010201, uuid2 = (short) 0x0101, uuid3 = (short) 0x0200,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TransferCharacteristic",
			aliases = { "Gamma" },
			typeName = "TransferCharacteristicType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3210,
			symbol = "TransferCharacteristic")
	public AUID getTransferCharacteristic() 
		throws PropertyNotPresentException {

		if (transferCharacteristic == null)
			throw new PropertyNotPresentException("The optional transfer characteristic type is not present in this picture descriptor.");
		
		return transferCharacteristic.clone();
	}

	@MediaPropertySetter("TransferCharacteristic")
	public void setTransferCharacteristic(
			tv.amwa.maj.record.AUID transferCharacteristic) {
		
		if (transferCharacteristic == null)
			this.transferCharacteristic = null;
		else
			this.transferCharacteristic = transferCharacteristic.clone();
	}

	@MediaProperty(uuid1 = 0x04010302, uuid2 = (short) 0x0500, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "VideoLineMap",
			typeName = "Int32Array",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x320D,
			symbol = "VideoLineMap")
	public int[] getVideoLineMap() {

		return videoLineMap.clone();
	}

	public void setVideoLineMap(
			int[] videoLineMap)
		throws NullPointerException,
			IllegalArgumentException {

		if (videoLineMap == null)
			throw new NullPointerException("Cannot set the video line map of this picture descriptor using a null value.");
		if ((videoLineMap.length == 0) || (videoLineMap.length > 2))
			throw new IllegalArgumentException("The size of the video line map must be 1 or 2 for this picture descriptor.");

		this.videoLineMap = videoLineMap.clone();
	}
	
	@MediaPropertySetter("VideoLineMap")
	public void setVideoLineMap(
			List<Integer> videoLineMap) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (videoLineMap == null)
			throw new NullPointerException("Cannot set the video line map of this picture descriptor using a null value.");
		if ((videoLineMap.size() == 0) || (videoLineMap.size() > 2))
			System.err.println("The size of the video line map should be 1 or 2 for this picture descriptor and was set to length " + videoLineMap.size() + " from a stream.");
	
		this.videoLineMap = new int[videoLineMap.size()];
		
		for ( int u = 0 ; u < this.videoLineMap.length ; u++ ) {
			
			if (videoLineMap.get(u) == null)
				throw new NullPointerException("Cannot set the video line map with null integer values.");
			this.videoLineMap[u] = videoLineMap.get(u);
		}
	}
	
	public final static List<Integer> initializeVideoLineMap() {
		
		List<Integer> initialList = new ArrayList<Integer>(1);
		initialList.add(0);
		return initialList;
	}

	@MediaPropertyCount("VideoLineMap")
	public int getVideoLineMapSize() {

		return videoLineMap.length;
	}

	public PictureDescriptor clone() {
		
		return (PictureDescriptor) super.clone();
	}
	
	// AVID extension properties - start

	private @Int32 Integer dataOffset = null;
	
	@MediaProperty(uuid1 = 0xbfde81e4, uuid2 = (short) 0xbcc8, uuid3 = (short) 0x4abd,
			uuid4 = { (byte) 0xa8, 0x0e, 0x21, 0x4d, (byte) 0xc0, (byte) 0xf1, 0x46, (byte) 0x84 },
			definedName = "DataOffset",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "DataOffset",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public @Int32 int getDataOffset()
		throws PropertyNotPresentException {
		
		if (dataOffset == null)
			throw new NullPointerException("The optional data offset property is not present for this picture descriptor.");
		
		return dataOffset;
	}
	
	@MediaPropertySetter("DataOffset")
	public void setDataOffset(
			@Int32 Integer dataOffset) {
		
		this.dataOffset = dataOffset;
	}
	
	private @UInt16 Short frameIndexByteOrder = null;
	
	@MediaProperty(uuid1 = 0xb57e925d, uuid2 = (short) 0x170d, uuid3 = (short) 0x11d4,
			uuid4 = { (byte) 0xa0, (byte) 0x8f, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb },
			definedName = "FrameIndexByteOrder",
			typeName = "UInt16",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "FrameIndexByteOrder",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public @UInt16 short getFrameIndexByteOrder()
		throws PropertyNotPresentException {
		
		if (frameIndexByteOrder == null)
			throw new NullPointerException("The optional frame index byte order property is not present for this picture descriptor.");
		
		return frameIndexByteOrder;
	}
	
	@MediaPropertySetter("FrameIndexByteOrder")
	public void setFrameIndexByteOrder(
			@UInt16 Short frameIndexByteOrder) 
		throws IllegalArgumentException {
		
		if (frameIndexByteOrder == null) {
			this.frameIndexByteOrder = null;
			return;
		}
		
		if (frameIndexByteOrder < 0)
			throw new IllegalArgumentException("Cannot set the frame index byte order with a negative value.");
		
		this.frameIndexByteOrder = frameIndexByteOrder;
	}
	
	private @Int32 Integer frameSampleSize = null;
	
	@MediaProperty(uuid1 = 0xce2aca50, uuid2 = (short) 0x51ab, uuid3 = (short) 0x11d3,
			uuid4 = { (byte) 0xa0, 0x24, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb },
			definedName = "FrameSampleSize",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "FrameSampleSize",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public @Int32 int getFrameSampleSize()
		throws PropertyNotPresentException {
		
		if (frameSampleSize == null)
			throw new NullPointerException("The optional frame sample size property is not present for this picture descriptor.");
		
		return frameSampleSize;
	}
	
	@MediaPropertySetter("FrameSampleSize")
	public void setFrameSampleSize(
			@Int32 Integer frameSampleSize) {
		
		this.frameSampleSize = frameSampleSize;
	}
	
	private @Int32 Integer imageSize = null;
	
	@MediaProperty(uuid1 = 0xce2aca4f, uuid2 = (short) 0x51ab, uuid3 = (short) 0x11d3,
			uuid4 = { (byte) 0xa0, 0x24, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb },
			definedName = "ImageSize",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "ImageSize",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public @Int32 int getImageSize()
		throws PropertyNotPresentException {
		
		if (imageSize == null)
			throw new NullPointerException("The optional image size property is not present for this picture descriptor.");
		
		return imageSize;
	}
	
	@MediaPropertySetter("ImageSize")
	public void setImageSize(
			@Int32 Integer imageSize) {
		
		this.imageSize = imageSize;
	}
	
	private @Int32 Integer resolutionID = null;
	
	@MediaProperty(uuid1 = 0xce2aca4d, uuid2 = (short) 0x51ab, uuid3 = (short) 0x11d3,
			uuid4 = { (byte) 0xa0, 0x24, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb },
			definedName = "ResolutionID",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "ResolutionID",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public @Int32 int getResolutionID()
		throws PropertyNotPresentException {
		
		if (resolutionID == null)
			throw new NullPointerException("The optional resolution ID property is not present for this picture descriptor.");
		
		return resolutionID;
	}
	
	@MediaPropertySetter("ResolutionID")
	public void setResolutionID(
			@Int32 Integer resolutionID) {
		
		this.resolutionID = resolutionID;
	}
	
	// AVID extension properties - end

	public String getCodingEquationsString() {
		
		return AUIDImpl.toPersistentForm(codingEquations);
	}
	
	public void setCodingEquationsString(
			String codingEquations) {
		
		this.codingEquations = AUIDImpl.fromPersistentForm(codingEquations);
	}
	
	public String getColorPrimariesString() {
		
		return AUIDImpl.toPersistentForm(colorPrimaries);
	}
	
	public void setColorPrimariesString(
			String colorPrimaries) {
		
		this.colorPrimaries = AUIDImpl.fromPersistentForm(colorPrimaries);
	}

	public String getImageAspectRatioString() {
		
		return RationalImpl.toPersistentForm(imageAspectRatio);
	}
	
	public void setImageAspectRatioString(
			String imageAspectRatio) {
		
		this.imageAspectRatio = RationalImpl.fromPersistentForm(imageAspectRatio);
	}
	
	public String getTransferCharacteristicString() {
		
		return AUIDImpl.toPersistentForm(transferCharacteristic);
	}
	
	public void setTransferCharacteristicString(
			String transferCharacteristic) {
		
		this.transferCharacteristic = AUIDImpl.fromPersistentForm(transferCharacteristic);
	}
	
	public String getPictureCompressionString() {
		
		return AUIDImpl.toPersistentForm(pictureCompression);
	}
	
	public void setPictureCompressionString(
			String pictureCompressionString) {
		
		this.pictureCompression = AUIDImpl.fromPersistentForm(pictureCompressionString);
	}
}
