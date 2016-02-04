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
 * $Log: CDCIDescriptorImpl.java,v $
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
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
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:28  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.ColorSitingType;
import tv.amwa.maj.enumeration.LayoutType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.avid.AvidConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.misctype.VideoLineMap;
import tv.amwa.maj.model.CDCIDescriptor;


/** 
 * <p>Implements the description of a file of video essence formatted with one luminance component and two 
 * color-difference components, as specified according to the
 * <a href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF object specification v1.1</a>. This format
 * is also known as "YCbCr".</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2800,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "CDCIDescriptor",
		  description = "The CDCIDescriptor class specifies that a file SourcePackage is associated with video essence formatted with one luminance component and two color-difference components as specified in this document.",
		  symbol = "CDCIDescriptor")
public class CDCIDescriptorImpl
	extends PictureDescriptorImpl
	implements CDCIDescriptor,
		tv.amwa.maj.extensions.avid.CDCIDescriptor,
		Serializable,
		XMLSerializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 710646891409730144L;

	private Integer alphaSampleDepth = null;
	private Integer blackRefLevel = null;
	private ColorSitingType colorSiting = null;
	private Integer colorRange = null;
	private int componentDepth;
	private int horizontalSubsampling;
	private Short paddingBits = null;
	private Boolean reversedByteOrder = null;
	private Integer verticalSubsampling = null;
	private Integer whiteRefLevel = null;

	private int maximumLevel = 0;
	
	/** Default constructor is not public to avoid unset required fields. */
	public CDCIDescriptorImpl() { }

	/**
	 * <p>Creates and initializes a new CDCI descriptor, which specifies that a 
	 * {@link SourcePackageImpl file source package} is associated with video essence that is formatted 
	 * with one luminance component and two color-difference components. This format is commonly 
	 * known as "YCbCr".</p>
	 * 
	 * @param containerFormat Container mechanism used to store the essence.
	 * @param storedHeight Number of pixels in vertical dimension of the stored view.
	 * @param storedWidth Number of pixels in horizontal dimension of the stored view.
	 * @param frameLayout Describes whether all data for a complete sample is in one 
	 * frame or is split into more than one field.
	 * @param videoLineMap The scan line in the analog source that corresponds to the 
	 * beginning of each digitized field. For single-field video, there is 1 value in 
	 * the array; for interlaced video, there are 2 values in the array.
	 * @param imageAspectRatio Describes the ratio between the horizontal size and the vertical 
	 * size in the intended final image.
	 * @param horizontalSubsampling Ratio of luminance sampling to chrominance sampling in the
	 * horizontal direction. For 4:2:2 video, the value is 2, which means that there are twice as 
	 * many luminance values as there are color-difference values. Legal values are <code>1</code>, 
	 * <code>2</code> and <code>4</code>.
	 * @param componentDepth Number of bits used to store each component. Can have a value of 
	 * <code>8</code>, <code>10</code>, or <code>16</code>. Each component in a sample is 
	 * packed contiguously; the sample is filled with the number of bits specified by the optional 
	 * padding bits property. If the padding bits property is omitted, samples are packed contiguously.	 
	 * 
	 * @throws NullPointerException One or more of arguments is null.
	 * @throws IllegalArgumentException The given values are not of the permintted ranges.
	 */
	public CDCIDescriptorImpl(
			tv.amwa.maj.model.ContainerDefinition containerFormat,
			@UInt32 int storedHeight,
			@UInt32 int storedWidth,
			LayoutType frameLayout,
			@VideoLineMap int[] videoLineMap,
			tv.amwa.maj.record.Rational imageAspectRatio,
			@UInt32 int horizontalSubsampling,
			@UInt32 int componentDepth)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (frameLayout == null)
			throw new NullPointerException("Cannot create a CDCI descriptor using a null frame layout value.");
		if (videoLineMap == null)
			throw new NullPointerException("Cannot create a CDCI descriptor using a null video line map value.");
		if (imageAspectRatio == null)
			throw new NullPointerException("Cannot create a CDCI descriptor using a null image aspect ratio.");

		setContainerFormat(containerFormat);
		setStoredView(storedHeight, storedWidth);
		setFrameLayout(frameLayout);
		setVideoLineMap(videoLineMap);
		setImageAspectRatio(imageAspectRatio);
		setHorizontalSubsampling(horizontalSubsampling);
		setComponentDepth(componentDepth);
		
		// Set to default values, which are based on component width
		setWhiteRefLevel(null);
		setColorRange(null);
	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0700, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "AlphaSampleDepth",
			aliases = { "AlphaSamplingWidth" },
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3309,
			symbol = "AlphaSampleDepth")
	public int getAlphaSampleDepth() {

		if (alphaSampleDepth == null)
			return ALPHASAMPLEDEPTH_DEFAULT;
		else
			return alphaSampleDepth;
	}

	@MediaPropertySetter("AlphaSampleDepth")
	public void setAlphaSampleDepth(
			Integer alphaSampleDepth) 
		throws IllegalArgumentException {

		if (alphaSampleDepth == null) {
			this.alphaSampleDepth = null;
			return;
		}
		
		if (alphaSampleDepth < 0)
			throw new IllegalArgumentException("Cannot set a negative alpha sampling width value for this CDCI descriptor.");

		this.alphaSampleDepth = alphaSampleDepth;
	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "BlackRefLevel",
			aliases = { "BlackReferenceLevel" },
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3304,
			symbol = "BlackRefLevel")
	public int getBlackRefLevel() {

		if (blackRefLevel == null) 
			return BLACKREFLEVEL_DEFAULT;
		else
			return blackRefLevel;
	}

	public void setBlackRefLevel(
			Integer blackRefLevel) 
		throws IllegalArgumentException {

		if (blackRefLevel == null) {
			this.blackRefLevel = null;
			return;
		}
		
		if ((blackRefLevel < 0) || (blackRefLevel > maximumLevel))
			throw new IllegalArgumentException("Cannot set the black reference level of this CDCI descriptor to a negative value or greater than the maximum range for the component size.");
		
		this.blackRefLevel = blackRefLevel;
	}

	@MediaPropertySetter("BlackRefLevel")
	public void setBlackRefLevelFromStream(
			Integer blackRefLevel) 
		throws IllegalArgumentException {

		if (blackRefLevel == null) {
			this.blackRefLevel = null;
			return;
		}
		
		if (blackRefLevel < 0)
			throw new IllegalArgumentException("Cannot set the black reference level of this CDCI descriptor to a negative value.");
		
		this.blackRefLevel = blackRefLevel;
	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0500, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ColorRange",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3306,
			symbol = "ColorRange")
	public int getColorRange() {

		if (colorRange == null)
			return maximumLevel;
		else
			return colorRange;
	}

	public void setColorRange(
			Integer colorRange) 
		throws IllegalArgumentException {

		if (colorRange == null) {
			this.colorRange = null;
			return;
		}
		
		if ((colorRange < 0) || (colorRange > maximumLevel))
			throw new IllegalArgumentException("Cannot set the color range of this CDCI descriptor to a negative value or a value greater than that allowable for the component size.");
		
		this.colorRange = colorRange;
	}
	
	@MediaPropertySetter("ColorRange")
	public void setColorRangeFromStream(
			Integer colorRange) 
		throws IllegalArgumentException {

		if (colorRange == null) {
			this.colorRange = null;
			return;
		}
		
		if (colorRange < 0)
			throw new IllegalArgumentException("Cannot set the color range of this CDCI descriptor to a negative value.");
		
		this.colorRange = colorRange;
	}

	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x0600, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "ColorSiting",
			typeName = "ColorSitingType", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3303,
			symbol = "ColorSiting")
	public ColorSitingType getColorSiting() {

		if (colorSiting == null)
			return COLORSITING_DEFAULT;
		else
			return colorSiting;
	}

	@MediaPropertySetter("ColorSiting")
	public void setColorSiting(
			ColorSitingType colorSiting) {

		this.colorSiting = colorSiting;
	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0a00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ComponentDepth",
			aliases = { "ComponentWidth" },
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3301,
			symbol = "ComponentDepth")
	public int getComponentDepth() {

		return componentDepth;
	}

	@MediaPropertySetter("ComponentDepth")
	public void setComponentDepth(
			int componentDepth) 
		throws IllegalArgumentException {

		if (componentDepth < 0)
			throw new IllegalArgumentException("Cannot set the component depth of this CDCI descriptor to a negative value.");
		
		this.componentDepth = componentDepth;
		calculateMaximumLevel();
	}
	
	public final static int initializeComponentDepth() {
		
		return 0;
	}

	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x0500, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "HorizontalSubsampling",
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3302,
			symbol = "HorizontalSubsampling")
	public int getHorizontalSubsampling() {

		return horizontalSubsampling;
	}

	@MediaPropertySetter("HorizontalSubsampling")
	public void setHorizontalSubsampling(
			int horizontalSubsampling) 
		throws IllegalArgumentException {

		if (horizontalSubsampling < 0)
			throw new IllegalArgumentException("Cannot set the horizontal subsampling value of this CDCI descriptor with a negative value.");
		
		this.horizontalSubsampling = horizontalSubsampling;
	}

	public final static int initializeHorizontalSubsampling() {
		
		return 0;
	}
	
	@MediaProperty(uuid1 = 0x04180104, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PaddingBits",
			typeName = "Int16",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3307,
			symbol = "PaddingBits")
	public short getPaddingBits() {
		
		if (paddingBits == null)
			return PADDINGBITS_DEFAULT;
		else
			return paddingBits;
	}

	@MediaPropertySetter("PaddingBits")
	public void setPaddingBits(
			Short paddingBits) {

		this.paddingBits = paddingBits;
	}

	@MediaProperty(uuid1 = 0x03010201, uuid2 = (short) 0x0a00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "ReversedByteOrder",
			typeName = "Boolean",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x330B,
			symbol = "ReversedByteOrder")
	public boolean getReversedByteOrder() {

		if (reversedByteOrder == null)
			return REVERSEDBYTEORDER_DEFAULT;
		else
			return reversedByteOrder;
	}

	@MediaPropertySetter("ReversedByteOrder")
	public void setReversedByteOrder(
			Boolean reversedByteOrder) {

		this.reversedByteOrder = reversedByteOrder;
	}
	
	@MediaProperty(uuid1 = 0x04010501, uuid2 = (short) 0x1000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "VerticalSubsampling",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3308,
			symbol = "VerticalSubsampling")
	public int getVerticalSubsampling() {

		if (verticalSubsampling == null)
			return VERTICALSUBSAMPLING_DEFAULT;
		else
			return verticalSubsampling;
	}

	@MediaPropertySetter("VerticalSubsampling")
	public void setVerticalSubsampling(
			Integer verticalSubsampling) 
		throws IllegalArgumentException {

		if (verticalSubsampling == null) {
			this.verticalSubsampling = null;
			return;
		}
		
		if (verticalSubsampling < 0)
			throw new IllegalArgumentException("Cannot set the vertical subsampling value of thsi CDCI descriptor to a negative value.");

		this.verticalSubsampling = verticalSubsampling;
	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "WhiteRefLevel",
			aliases = { "WhiteReferenceLevel" },
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3305,
			symbol = "WhiteRefLevel")
	public int getWhiteRefLevel() {

		if (whiteRefLevel == null)
			return maximumLevel;
		else
			return whiteRefLevel;
	}

	public void setWhiteRefLevel(
			Integer whiteRefLevel) 
		throws IllegalArgumentException {

		if (whiteRefLevel == null) {
			this.whiteRefLevel = null;
			return;
		}
		
		if ((whiteRefLevel < 0) || (whiteRefLevel > maximumLevel))
			throw new IllegalArgumentException("Cannot set the white reference level of this CDCI descriptor to a negative value or greater than the maximum range for the component size.");
		
		this.whiteRefLevel = whiteRefLevel;
	}
	
	@MediaPropertySetter("WhiteRefLevel")
	public void setWhiteRefLevelFromStream(
			Integer whiteRefLevel) 
		throws IllegalArgumentException {

		if (whiteRefLevel == null) {
			this.whiteRefLevel = null;
			return;
		}
		
		if (whiteRefLevel < 0)
			throw new IllegalArgumentException("Cannot set the white reference level of this CDCI descriptor to a negative value.");
		
		this.whiteRefLevel = whiteRefLevel;
	}
	
	/**
	 * <p>Work out the maximum possible level for any component value, which is the default 
	 * value for the white reference level and color range properties.</p>
	 *
	 */
	private void calculateMaximumLevel() {
		
		this.maximumLevel = (1 << componentDepth) - 1; // 2^componentWidth - 1
	}
	
	public CDCIDescriptor clone() {
		
		return (CDCIDescriptor) super.clone();
	}

	// AVID extension properties - start

	private @Int64 Long offsetToFrameIndexes64 = null;
	
	@MediaProperty(uuid1 = 0x298eb260, uuid2 = (short) 0x30b6, uuid3 = (short) 0x4e30,
			uuid4 = { (byte) 0x8c, (byte) 0x90, (byte) 0xcf, 0x63, (byte) 0xaa, 0x79, 0x3c, 0x34 },
			definedName = "OffsetToFrameIndexes64",
			typeName = "Int64",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "OffsetToFrameIndexes64",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public @Int64 long getOffsetToFrameIndexes64()
		throws PropertyNotPresentException {
		
		if (offsetToFrameIndexes64 == null)
			throw new NullPointerException("The optional offset to frame indexes 64 property is not present for this CDCI descriptor.");
		
		return offsetToFrameIndexes64;
	}
	
	@MediaPropertySetter("OffsetToFrameIndexes64")
	public void setOffsetToFrameIndexes64(
			@Int64 Long offsetToFrameIndexes64) {
		
		this.offsetToFrameIndexes64 = offsetToFrameIndexes64;
	}
	
	// AVID extension properties - end
}
