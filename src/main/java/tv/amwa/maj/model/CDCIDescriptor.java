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
 * $Log: CDCIDescriptor.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/28 12:50:35  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2008/01/27 11:07:23  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:09  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.ColorSitingType;
import tv.amwa.maj.integer.Int16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.Bool;


/**
 * <p>Specifies the description of a file of video essence formatted with one luminance component and two 
 * color-difference components, as specified according to the
 * <a href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF object specification v1.1</a>. This format
 * is also known as "YCbCr".</p>
 * 
 * <p>Note that the default values for the optional {@linkplain #getWhiteRefLevel() white reference level} and 
 * {@linkplain #getColorRange() color range} properties depend on the required {@linkplain #getComponentDepth() component
 * width} property. Therefore, the default values of these properties should be computed at runtime.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.constant.CodecConstant#CDCI
 */

public interface CDCIDescriptor
	extends PictureDescriptor {

	/** 
	 * <p>Default value for the vertical subsampling property, which is&nbsp;{@value #VERTICALSUBSAMPLING_DEFAULT}.</p> 
	 * 
	 * @see #getVerticalSubsampling()
	 * @see #setVerticalSubsampling(Integer) 
	 */
	public final static int VERTICALSUBSAMPLING_DEFAULT = 1;
	
	/** 
	 * <p>Default value for the alpha sample depth property, which is&nbsp;{@value #ALPHASAMPLEDEPTH_DEFAULT}.</p> 
	 * 
	 * @see #getAlphaSampleDepth()
	 * @see #setAlphaSampleDepth(Integer)
	 */
	public final static int ALPHASAMPLEDEPTH_DEFAULT = 0;
	
	/** 
	 * <p>Default value for the padding bits property, which is&nbsp;{@value #PADDINGBITS_DEFAULT}.</p> 
	 * 
	 * @see #getPaddingBits()
	 * @see #setPaddingBits(Short)
	 */
	public final static short PADDINGBITS_DEFAULT = (short) 0;
	
	/** 
	 * <p>Default value for the color siting property, which is 
	 * {@link ColorSitingType#CoSiting}.</p> 
	 * 
	 * @see #getColorSiting()
	 * @see #setColorSiting(ColorSitingType)
	 */
	public final static ColorSitingType COLORSITING_DEFAULT = ColorSitingType.CoSiting;
	
	/** 
	 * <p>Default value for the black reference level property which is&nbsp;{@value #BLACKREFLEVEL_DEFAULT}.</p> 
	 * 
	 * @see #getBlackRefLevel()
	 * @see #setBlackRefLevel(Integer)
	 */
	public final static int BLACKREFLEVEL_DEFAULT = 0;
	
	// White reference level default is dependent on component width property.
	
	// Color range is dependent on component width property.
	
	/** 
	 * <p>Default value for the reversed byte order property, which 
	 * is&nbsp;<code>{@value #REVERSEDBYTEORDER_DEFAULT}</code>.</p> 
	 * 
	 * @see #getReversedByteOrder()
	 * @see #setReversedByteOrder(Boolean)
	 */
	public final static boolean REVERSEDBYTEORDER_DEFAULT = false;
	
	/**
	 * <p>Sets the component depth property of this CDCI descriptor, which 
	 * specifies the number of bits used to store each component.  
	 * Typical values can be&nbsp;8, 10, 12, 14, or&nbsp;16, but others are 
	 * permitted by the MAJ API implementation.  Each component in a 
	 * sample is packed contiguously; the sample is filled with the number 
	 * of bits specified by the optional {@linkplain #getPaddingBits() padding bits property}.  
	 * If the padding bits property is omitted, samples are packed
	 * contiguously.</p>
	 * 
	 * @param componentDepth Component width for the CDCI descriptor.
	 * 
	 * @throws IllegalArgumentException The given component width is negative.
	 * 
	 * @see #getPaddingBits()
	 */
	public void setComponentDepth(
			@UInt32 int componentDepth)
		throws IllegalArgumentException; 

	/**
	 * <p>Returns the component width property of this CDCI descriptor, which
	 * specifies the number of bits used to store each component.  
	 * Typical values can be 8, 10, 12, 14, or 16, but others are 
	 * permitted by the MAJ API implementation.  Each component in a 
	 * sample is packed contiguously; the sample is filled with the number 
	 * of bits specified by the optional {@linkplain #getPaddingBits() padding bits property}.  
	 * If the padding bits property is omitted, samples are packed
	 * contiguously.</p>
	 * 
	 * @return Component width for the CDCI descriptor.
	 * 
	 * @see #getPaddingBits()
	 */
	public @UInt32 int getComponentDepth();

	/**
	 * <p>Sets the horizontal subsampling property for this CDCI descriptor, which
	 * specifies the ratio of luminance sampling to chrominance sampling in 
	 * the horizontal direction.</p>
	 * 
	 * <p>For 4:2:2 video, the value is&nbsp;2, which means that there are twice as
	 * many luminance values as there are color-difference values.
	 * Another typical value is &nbsp;1; however other values are permitted by
	 * the MAJ API implementation.</p>
	 * 
	 * @param horizontalSubsampling Horizontal subsampling of the CDCI descriptor.
	 */
	public void setHorizontalSubsampling(
			@UInt32 int horizontalSubsampling);

	/**
	 * <p>Returns the horizontal subsampling property for this CDCI descriptor, which 
	 * specifies the ratio of luminance sampling to chrominance sampling in 
	 * the horizontal direction.</p>
	 * 
	 * <p>For 4:2:2 video, the value is&nbsp;2, which means that there are twice as
	 * many luminance values as there are color-difference values.
	 * Another typical value is&nbsp;1; however other values are permitted by
	 * the MAJ API implementation.</p>
	 * 
	 * @return Horizontal subsampling of the CDCI descriptor.
	 */
	public @UInt32 int getHorizontalSubsampling();

	/**
	 * <p>Sets the vertical subsampling property of the CDCI descriptor, which
	 * specifies the ratio of luminance sampling to chrominance sampling 
	 * in the vertical direction.</p>
	 * 
	 * <p>For 4:2:2 video, the value is&nbsp;2, which means that there are twice as
	 * many luminance values as there are color-difference values. Another 
	 * typical value is&nbsp;1; however other values are permitted by the MAJ API 
	 * implementation.</p>
	 * 
	 * <p>To omit this optional property, call ths method with <code>null</code>.</p>
	 * 
	 * @param verticalSubsampling Vertical subsampling of the CDCI descriptor.
	 * 
	 * @throws IllegalArgumentException The given vertical subsampling property is negative.
	 * 
	 * @see #VERTICALSUBSAMPLING_DEFAULT
	 */
	public void setVerticalSubsampling(
			@UInt32 Integer verticalSubsampling)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the vertical subsampling property of the CDCI descriptor, which
	 * specifies the ratio of luminance sampling to chrominance sampling 
	 * in the vertical direction.</p>
	 * 
	 * <p>For 4:2:2 video, the value is&nbsp;2, which means that there are twice as
	 * many luminance values as there are color-difference values. Another 
	 * typical value is 1; however other values are permitted by the MAJ API 
	 * implementation.</p>
	 * 
	 * <p>The default value of this optional property will be returned if it is 
	 * not present.</p>
	 * 
	 * @return Vertical subsampling of the CDCI descriptor.
	 * 
	 * @see #VERTICALSUBSAMPLING_DEFAULT
	 */
	public @UInt32 int getVerticalSubsampling();

	/**
	 * <p>Sets the color siting property of the CDCI descriptor, which specifies how
	 * to compute subsampled chrominance values.</p>
	 * 
	 * <p>Valid values are:</p>
	 * 
	 * <ul>
	 *  <li>{@link ColorSitingType#CoSiting} - To calculate subsampled pixels, take the
	 *  preceding's pixels color value, discard the other color values and cosite the 
	 *  color with the first luminance value.</li>
	 *  
	 *  <li>{@link ColorSitingType#Averaging} - To calculate subsampled pixels, take the
	 *  average of the two adjacent pixels' color values and site the color in the 
	 *  center of the luminance pixels.</li>
	 *  
	 *  <li>{@link ColorSitingType#ThreeTap} - To calculate subsampled pixels, take 
	 *  25&nbsp;percent of the the previous pixel's color value, 50&nbsp;percent of the first 
	 *  value and 25&nbsp;percent of the second value.  For the first value in a row, use 
	 *  75&nbsp;percent of that value since there is no previous value.  The 
	 *  <code>ThreeTap</code> value is only meaningful when the horizontal subsampling
	 *  property has a value of&nbsp;2.</li>
	 *  
	 * </ul>
	 * 
	 * <p>The color siting property is optional and has a default value of 
	 * {@link ColorSitingType#CoSiting}. Calling this method with <code>null</code> to
	 * omit this property.</p>
	 * 
	 * @param colorSiting Color siting for the CDCI descriptor.
	 * 
	 * @see #COLORSITING_DEFAULT
	 */
	public void setColorSiting(
			ColorSitingType colorSiting);

	/**
	 * <p>Returns the color siting property of the CDCI descriptor, which specifies how
	 * to compute subsampled chrominance values.</p>
	 * 
	 * <p>Valid values are:</p>
	 * 
	 * <ul>
	 *  <li>{@link ColorSitingType#CoSiting} - To calculate subsampled pixels, take the
	 *  preceding's pixels color value, discard the other color values and cosite the 
	 *  color with the first luminance value.</li>
	 *  
	 *  <li>{@link ColorSitingType#Averaging} - To calculate subsampled pixels, take the
	 *  average of the two adjacent pixels' color values and site the color in the 
	 *  center of the luminance pixels.</li>
	 *  
	 *  <li>{@link ColorSitingType#ThreeTap} - To calculate subsampled pixels, take 
	 *  25&nbsp;percent of the the previous pixel's color value, 50&nbsp;percent of the first 
	 *  value and 25&nbsp;percent of the second value.  For the first value in a row, use 
	 *  75&nbsp;percent of that value since there is no previous value.  The 
	 *  <code>ThreeTap</code> value is only meaningful when the horizontal subsampling
	 *  property has a value of&nbsp;2.</li>
	 *  
	 * </ul>
	 * 
	 * <p>The color siting property is optional and has a default value of 
	 * {@link ColorSitingType#CoSiting}. The default value will be returned if the
	 * property is not present.</p>
	 * 
	 * @return Color siting for the CDCI descriptor.
	 * 
	 * @see #COLORSITING_DEFAULT
	 */
	public ColorSitingType getColorSiting();

	/**
	 * <p>Sets the black reference level property for this CDCI descriptor, which 
	 * specifies the digital luminance component value associated with black.</p>
	 * 
	 * <p>For CCIR-601/2, the value is&nbsp;16 for 8-bit video and&nbsp;64 for 10-bit video.  
	 * For YUV, the value is&nbsp;0.  These are typical values; other values will not 
	 * be disallowed by the MAJ API implementation.</p>
	 * 
	 * <p>The same value is used in CDCI and RGBA when standard colorspace 
	 * conversion is used.</p>
	 * 
	 * <p>This property is optional and has a default value of&nbsp;{@value #BLACKREFLEVEL_DEFAULT}. 
	 * To omit the property, call this method with <code>null</code>.</p>
	 * 
	 * @param blackRefLevel Black reference level of the CDCI descriptor.
	 * 
	 * @throws IllegalArgumentException The given black reference level is negative.
	 * 
	 * @see #BLACKREFLEVEL_DEFAULT
	 */
	public void setBlackRefLevel(
			@UInt32 Integer blackRefLevel)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the black reference level property for this CDCI descriptor, which
	 * specifies the digital luminance component value associated with black.</p>
	 * 
	 * <p>For CCIR-601/2, the value is&nbsp;16 for 8-bit video and&nbsp;64 for 10-bit video.  
	 * For YUV, the value is&nbsp;0.  These are typical values; other values will not 
	 * be disallowed by the MAJ API implementation.</p>
	 * 
	 * <p>The same value is used in CDCI and RGBA when standard colorspace 
	 * conversion is used.</p>
	 * 
	 * <p>If the property is omitted, its default value will be returned.</p>
	 * 
	 * @return Black reference level of the CDCI descriptor.
	 * 
	 * @see #BLACKREFLEVEL_DEFAULT
	 */
	public @UInt32 int getBlackRefLevel();

	/**
	 * <p>Sets the white reference level property of this CDCI descriptor, which
	 * specifies the digital luminance component component value associated 
	 * with white.</p>
	 * 
	 * <p>For CCIR-601/2, the value is&nbsp;235 for 8-bit video and&nbsp;940 for 
	 * 10-bit video.  For YUV, the value is&nbsp;255 for 8-bit video 
	 * and&nbsp;1023 for 10-bit video.  These are typical values; other values 
	 * will not be disallowed by the MAJ API implementation.</p>
	 * 
	 * <p>This property is optional.  The default value is the maximum
	 * unsigned integer value for the component size. To omit this optional property,
	 * call this method with <code>null</code>.</p>
	 * 
	 * @param whiteRefLevel White reference level for the CDCI descriptor.
	 * 
	 * @throws IllegalArgumentException Cannot set the white reference level of 
	 * this CDCI descriptor to a negative value.
	 * 
	 * @see #getComponentDepth()
	 */
	public void setWhiteRefLevel(
			@UInt32 Integer whiteRefLevel)
		throws IllegalArgumentException;

	/**
	 * <p>Sets the white reference level property of this CDCI descriptor, which
	 * specifies the digital luminance component component value associated 
	 * with white.</p>
	 * 
	 * <p>For CCIR-601/2, the value is&nbsp;235 for 8-bit video and&nbsp;940 for 
	 * 10-bit video.  For YUV, the value is&nbsp;255 for 8-bit video 
	 * and &nbsp;1023 for 10-bit video.  These are typical values; other values 
	 * will not be disallowed by the MAJ API implementation.</p>
	 * 
	 * <p>If the property is not present, its default value will be returned.
	 * The default value is the maximum unsigned integer value for the component 
	 * size.</p>
	 * 
	 * @return White reference level for the CDCI descriptor.
	 * 
	 * @see #getComponentDepth()
	 */
	public @UInt32 int getWhiteRefLevel();
	
	/**
	 * <p>Sets the color range property for this CDCI descriptor, which
	 * specifies the range of allowable digital chrominance component values.  
	 * Chrominance values are unsigned and the range is centered on&nbsp;128 for 
	 * 8-bit video and&nbsp;512 for 10-bit video. This value is used for both 
	 * chrominance components.</p>
	 * 
	 * <p>For CCIR-601/2, the range is&nbsp;225 for 8-bit video and&nbsp;897 for
	 * 10-bit video.  For YUV, the range is&nbsp;255 for 8-bit video 
	 * and&nbsp;1023 for 10-bit video.  hese are typical values; other values will
	 * not be disallowed by the MAJ API implementation.</p>
	 * 
	 * <p>This property is optional. The default value is the maximum
	 * unsigned integer value for the component size. To omit this optional
	 * property, call this method with <code>null</code>.</p>
	 * 
	 * @param colorRange Color range for the CDCI descriptor.
	 * 
	 * @throws IllegalArgumentException Cannot set the color range property of
	 * this CDCI descriptor to a negative value.
	 * 
	 * @see #getComponentDepth()
	 */
	public void setColorRange(
			@UInt32 Integer colorRange);

	/**
	 * <p>Returns the color range property for this CDCI descriptor, which 
	 * specifies the range of allowable digital chrominance component values.  
	 * Chrominance values are unsigned and the range is centered on&nbsp;128 for 
	 * 8-bit video and&nbsp;512 for 10-bit video. This value is used for both 
	 * chrominance components.</p>
	 * 
	 * <p>For CCIR-601/2, the range is&nbsp;225 for 8-bit video and&nbsp;897 for
	 * 10-bit video.  For YUV, the range is&nbsp;255 for 8-bit video 
	 * and&nbsp;1023 for 10-bit video.  These are typical values; other values will
	 * not be disallowed by the MAJ API implementation.</p>
	 * 
	 * <p>If this optional property is not present, the default value is 
	 * returned. The default value is the maximum unsigned integer value for 
	 * the component size.</p>
	 * 
	 * @return Color range for the CDCI descriptor.
	 * 
	 * @see #getComponentDepth()
	 */
	public @UInt32 int getColorRange();

	/**
	 * <p>Sets the padding bits property of this CDCI descriptor, which
	 * specifies the number of bits padded to each pixel.</p>
	 * 
	 * <p>This is an optional property with a default value of {@value #PADDINGBITS_DEFAULT}. To
	 * omit this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param paddingBits Padding bits of the CDCI descriptor.
	 * 
	 * @see #PADDINGBITS_DEFAULT
	 */
	public void setPaddingBits(
			@Int16 Short paddingBits);

	/**
	 * <p>Returns the padding bits property of the CDCI descriptor, which
	 * specifies the number of bits padded to each pixel.</p>
	 * 
	 * <p>If this optional property is not present, its default value is returned.</p>
	 * 
	 * @return Padding bits property of the CDCI descriptor.
	 * 
	 * @see #PADDINGBITS_DEFAULT
	 */
	public @Int16 short getPaddingBits();

	/**
	 * <p>Sets the alpha sample depth property of the CDCI descriptor, which
	 * specifies the number of bits used to store the alpha 
	 * component.</p>
	 * 
	 * <p>This property is optional and has a default value of {@value #ALPHASAMPLEDEPTH_DEFAULT}. To omit 
	 * this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param alphaSampleDepth Alpha sample depth property of the 
	 * CDCI descriptor.
	 * 
	 * @throws IllegalArgumentException The giv en alpha sampling width value is negative.
	 * 
	 * @see #ALPHASAMPLEDEPTH_DEFAULT
	 */
	public void setAlphaSampleDepth(
			@UInt32 Integer alphaSampleDepth)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the alpha sample depth property of the CDCI descriptor, which
	 * specifies the number of bits used to store the alpha 
	 * component.</p>
	 * 
	 * <p>If this optional property is not present, its default value is returned.</p>
	 * 
	 * @return Alpha sample depth property of the CDCI descriptor.
	 * 
	 * @see #ALPHASAMPLEDEPTH_DEFAULT
	 */
	public @UInt32 int getAlphaSampleDepth();

	/**
	 * <p>Sets the reversed byte order property of the CDCI descriptor, which
	 * specifies whether the luma and chroma sample order is reversed relative to
	 * the order defined by ITU-R&nbsp;BT.601. The value is <code>false</code> if the sample order
	 * conforms conforms to ITU-R&nbsp;BT.601, or <code>true</code> if the order is reversed (i.e. the 
	 * luma sample precedes the chroma).<p>
	 * 
	 * <p>This property is optional and has a default value of <code>{@value #REVERSEDBYTEORDER_DEFAULT}</code>. 
	 * To omit this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param reversedByteOrder Is the byte order reversed?
	 * 
	 * @see #REVERSEDBYTEORDER_DEFAULT
	 * @see tv.amwa.maj.enumeration.ColorSitingType#Rec601
	 */
	public void setReversedByteOrder(
			@Bool Boolean reversedByteOrder);

	/**
	 * <p>Sets the reversed byte order property of the CDCI descriptor, which
	 * specifies whether the luma and chroma sample order is reversed relative to
	 * the order defined by ITU-R&nbsp;BT.601. The value is <code>false</code> if the sample order
	 * conforms conforms to ITU-R&nbsp;BT.601, or <code>true</code> if the order is reversed (i.e. the 
	 * luma sample precedes the chroma).<p>
	 * 
	 * <p>If this optional property is not present, its default value is returned.</p>
	 * 
	 * @return Is the byte order reversed?
	 * 
	 * @see #REVERSEDBYTEORDER_DEFAULT
	 * @see tv.amwa.maj.enumeration.ColorSitingType#Rec601
	 */
	public @Bool boolean getReversedByteOrder();
	
	/**
	 * <p>Create a cloned copy of this CDCI descriptor.</p>
	 *
	 * @return Cloned copy of this CDCI descriptor.
	 */
	public CDCIDescriptor clone();
}
