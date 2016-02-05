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
 * $Log: PictureDescriptor.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/01/27 11:07:24  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:29:23  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:21  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.AlphaTransparencyType;
import tv.amwa.maj.enumeration.FieldNumber;
import tv.amwa.maj.enumeration.LayoutType;
import tv.amwa.maj.enumeration.SignalStandardType;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.misctype.VideoLineMap;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;


/**
 * <p>Specifies the description of video content data formatted either using RGBA or
 * luminance/chrominance formatting.</p>
 * 
 * <p>The geometry properties accessed by 
 * {@link #setStoredView(int, int) set}/{@link #getStoredView()},
 * {@link #setSampledView(int, int, int, int) set}/{@link #getSampledView()} 
 * and
 * {@link #setDisplayView(int, int, int, int) set}/{@link #getDisplayView()}
 * describe the dimensions and meaning of the stored pixels in the 
 * image.  The geometry describes the pixels of an uncompressed image.
 * Consequently, the geometry properties are independent of the 
 * compression and subsampling of that data.</p>
 *  
 * <p>Three separate geometries&nbsp;- <em>stored</em>, <em>sampled</em>, and 
 * <em>display views</em>&nbsp;- are used to define a set of different views on uncompressed digital
 * data. All views are constrained to rectangular regions, which means
 * that storage and sampling has to be rectangular.</p>
 * 
 * <p>The relationships among the views can be shown by the following
 * rectangles, representing areas of a video image:</p>
 * 
 * <pre>
      +------------------+
      |                  |
      |   Stored View    |
      |                  |           +--------------+
      +------------------+ <-------- |              |
      |                  |  Sample   | Analog Video |
      |   Sampled View   |  Process  |    Source    |
      |                  |           | Information  |
      | +--------------+ |           |              |
      | |              | |           |              |
      | | Display View | |           |              |
      | |              | |           |              |
      | +--------------+ |           |              |
      |                  |           |              |
      +------------------+ <-------- |              |
                                     +--------------+

   </pre>
 *
 * <p>The stored view is the entire data region corresponding to a single
 * uncompressed frame or field of the image, and is defined by its
 * horizontal and vertical dimension properties. The stored view may
 * include data that is not derived from, and would not usually be
 * translated back to, analog data.</p>
 * 
 * <p>The sampled view is defined to be the rectangular dimensions in
 * pixels corresponding to the digital data derived from an analog or
 * digital source. These pixels reside within the rectangle defined by
 * the stored view. This would include the image and auxiliary
 * information included in the analog or digital source. For the
 * capture of video signals, the mapping of these views to the
 * original signal is determined by the {@linkplain #getVideoLineMap() video line map} property.</p>
 * 
 * <p>The display view is the rectangular size in pixels corresponding to
 * the viewable area. These pixels contain image data suitable for
 * scaling, display, warping, and other image processing. The display
 * view offsets are relative to the stored view, not to the sampled
 * view.</p>
 * 
 * <p>Although typically the display view is a subset of the sampled
 * view, it is possible that the viewable area may not be a subset of
 * the sampled data. It may overlap or even encapsulate the sampled
 * data. For example, a subset of the input image might be centered in
 * a computer-generated blue screen for use in a chroma key effect. In
 * this case the viewable pixels on disk would contain more than the
 * sampled image.</p>
 * 
 * <p>Each of these data views will have a width and height value. Both
 * the sampled view and the display view also have offsets relative to
 * the top left corner of the stored view.</p>
 * 
 *
 *
 */

public interface PictureDescriptor 
	extends AAFFileDescriptor { 
	
	/** 
	 * <p>Value representing no compression, which is that the compression property is 
	 * omitted (<code>null</code>).</p>
	 * 
	 * @see #getPictureCompression()
	 * @see #setPictureCompression(AUID)
	 */
	public final static AUID Uncompressed = null;
	
	/** 
	 * <p>Default value for the optional stored f2 offset property, which is 
	 * {@value #STOREDF2OFFSET_DEFAULT}.</p> 
	 * 
	 * @see #getStoredF2Offset()
	 * @see #setStoredF2Offset(Integer)
	 */
	public final static int STOREDF2OFFSET_DEFAULT = 0;
	
	/** 
	 * <p>Default value for the optional display f2 offset property, which is
	 * {@value #DISPLAYF2OFFSET_DEFAULT}.</p> 
	 * 
	 * @see #getDisplayF2Offset()
	 * @see #setDisplayF2Offset(Integer)
	 */
	public final static int DISPLAYF2OFFSET_DEFAULT = 0;
	
	/** 
	 * <p>Default value for the optional alpha transparency property, which is 
	 * {@link tv.amwa.maj.enumeration.AlphaTransparencyType#MinValueTransparent}.</p> 
	 *
	 * @see #getAlphaTransparency()
	 * @see #setAlphaTransparency(AlphaTransparencyType)
	 */
	public final static AlphaTransparencyType ALPHATRANSPARENCY_DEFAULT = 
										AlphaTransparencyType.MinValueTransparent;
	
	/** 
	 * <p>Default value for the image alignment factor property, which is 
	 * {@value #IMAGEALIGNMENTFACTOR_DEFAULT}.</p> 
	 * 
	 * @see #getImageAlignmentFactor()
	 * @see #setImageAlignmentFactor(Integer)
	 */
	public final static int IMAGEALIGNMENTFACTOR_DEFAULT = 1;
	
	/** 
	 * <p>Default value for the image start offset property, which is
	 * {@value #IMAGESTARTOFFSET_DEFAULT}.</p> 
	 * 
	 * @see #getImageStartOffset()
	 * @see #setImageStartOffset(Integer)
	 */
	public final static int IMAGESTARTOFFSET_DEFAULT = 0;
	
	/** 
	 * <p>Default value for the image end offset property, which is
	 * {@value #IMAGEENDOFFSET_DEFAULT}.</p> 
	 * 
	 * @see #getImageEndOffset()
	 * @see #setImageEndOffset(Integer)
	 */
	public final static int IMAGEENDOFFSET_DEFAULT = 0;
	
	/**
	 * <p>Sets the kind of compression and format of the compression
	 * information of the video essence data described by this picture descriptor. If there 
	 * is no compression, set this optional property to {@link #Uncompressed} or <code>null</code>.</p>
	 * 
	 * @param pictureCompression Kind of compression and format of the compression
	 * information of the video essence data, or {@link #Uncompressed} if the 
	 * image is uncompressed.
	 * 
	 * @see #Uncompressed
	 */
	public void setPictureCompression(
			AUID pictureCompression);

	/**
	 * <p>Returns the kind of compression and format of compression
	 * information of the video essence data described by this picture descriptor. If 
	 * the image data is uncompressed, a {@link PropertyNotPresentException} will be thrown.</p>
	 * 
	 * @return Kind of compression and format of compression
	 * information of the video essence data.
	 * 
	 * @throws PropertyNotPresentException The optional compression property is not
	 * present in this picture descriptor, indicating that the described image
	 * essence data is uncompressed.
	 */
	public AUID getPictureCompression()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the dimension of the stored view of this picture descriptor.  Typically,
	 * this includes leading blank video lines, any VITC lines, as well as the active
	 * picture area.</p>
	 * 
	 * @param storedHeight Number of pixels in vertical dimension of stored view.
	 * @param storedWidth Number of pixels in horizontal dimension of stored view.
	 * 
	 * @throws IllegalArgumentException One or both of the given stored width and height
	 * properties is/are negative, which is not permitted.
	 */
	public void setStoredView(
			@UInt32 int storedHeight,
			@UInt32 int storedWidth)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the dimension of the stored view of this picture descriptor.  Typically,
	 * this includes leading blank video lines, any VITC lines, as well as the active
	 * picture area.</p>
	 * 
	 * <p>The value returned is an array of two integer values. The first 
	 * element of the array (index&nbsp;0) contains the stored height and the 
	 * second element of the array (index&nbsp;1) contains the stored width.</p>
	 * 
	 * @return Dimension of the stored view.
	 * 
	 * @see #getStoredHeight()
	 * @see #getStoredWidth()
	 */
	public int[] getStoredView();
	
	/**
	 * <p>Returns the height component of the dimension of the complete stored view. Typically,
	 * this includes leading blank video lines, any VITC lines, as well as the active
	 * picture area.</p>
	 * 
	 * @return Height component of the complete stored view.
	 * 
	 * @see #getStoredView()
	 */
	public int getStoredHeight();
	
	/**
	 * <p>Returns the width component of the dimension of the complete stored view.</p>
	 * 
	 * @return Width component of the complete stored view.
	 * 
	 * @see #getStoredView()
	 */
	public int getStoredWidth();

	/**
	 * <p>Sets the dimensions of the sampled view of this picture descriptor.  Typically,
	 * this includes any VITC lines as well as the active picture area, but excludes
	 * leading blank video lines.  The offset is specified relative to
	 * the rectangle specified by the {@linkplain #getStoredView() stored view}.</p>
	 * 
	 * <p>All properties are optional and are set as follows:</p>
	 * 
	 * <ul>
	 *  <li><code>sampledHeight</code> - The default value is the stored height, so use
	 *  {@link #getStoredView() getStoredView()[0]} to set the default value.</li>
	 *  <li><code>sampledWidth</code> - The default value is the stored width, so use
	 *  {@link #getStoredView() getStoredView()[1]} to set the default value.</li>
	 *  <li><code>displayXOffset</code> - The default value is&nbsp;0, so use&nbsp;0
	 *  to select the default.</li>
	 *  <li><code>displayYOffset</code> - The default value is&nbsp;0, so use&nbsp;0
	 *  to select the default.</li>
	 * </ul></p>
	 * 
	 * <p>The sampled view values are either all present or all omitted. Calling this
	 * method will mean sampled view values are present. To find out whether the sampled
	 * view properties are present, call {@link #areSampledValuesPresent()}.</p>
	 * 
	 * @param sampledHeight Number of pixels in vertical dimension of sampled view.
	 * @param sampledWidth Number of pixels in horizontal dimension of sampled view.
	 * @param sampledXOffset Number of pixels from top left corner of the stored view. 
	 * @param sampledYOffset Number of pixels from top left corner of the stored view. 
	 * 
	 * @throws IllegalPropertyValueException The area specified by the sampled width
	 * and sampled X offset is outside the stored view, or the area 
	 * specified by the sampled height and sampled Y offset is 
	 * outside the stored view. 
	 */
	public void setSampledView(
			@UInt32 int sampledHeight,
			@UInt32 int sampledWidth,
			@Int32 int sampledXOffset,
			@Int32 int sampledYOffset) 
		throws IllegalPropertyValueException;

	/**
	 * <p>Returns the dimensions of the sampled view of this picture descriptor. Typically,
	 * this includes any VITC lines as well as the active picture area, but excludes
	 * leading blank video lines.  The offset is specified relative to
	 * the rectangle specified by the {@linkplain #getStoredView() stored view}.</p>
	 * 
	 * <p>The value returned is an array of four integer values. The index
	 * of the values correspond to:</p>
	 * 
	 * <ul>
	 *  <li><code>0</code> - sampled height - Number of pixels in vertical 
	 *  dimension of sampled view.</li>
	 *  <li><code>1</code> - sampled width - Number of pixels in horizontal 
	 *  dimension of sampled view.</li>
	 *  <li><code>2</code> - sampled X offset - Number of pixels from top 
	 *  left corner of stored view.</li>
	 *  <li><code>3</code> - sampled Y offset - Number of pixels from top 
	 *  left corner of stored view.</li>
	 * </ul>
	 * 
	 * <p>The sampled view properties are either all present or all omitted. If the 
	 * properties are omitted, the default value is returned which is an area
	 * equivalent to the stored view. To find out whether the sampled view
	 * properties are present, call {@link #areSampledValuesPresent()}.</p> 
	 * 
	 * @return Dimensions of the sampled view.
	 * 
	 * @see #getSampledHeight()
	 * @see #getSampledWidth()
	 * @see #getSampledXOffset()
	 * @see #getSampledYOffset()
	 */
	public int[] getSampledView();
	
	/**
	 * <p>Returns the height component of the sampled view of this picture descriptor. Typically,
	 * this includes any VITC lines as well as the active picture area, but excludes
	 * leading blank video lines.</p>
	 * 
	 * @return Height component of the sampled view.
	 * 
	 * @see #getSampledView()
	 */
	public int getSampledHeight();
	
	/**
	 * <p>Returns the width component of the sampled view of this picture descriptor.</p>
	 * 
	 * @return Width component of the sampled view.
	 * 
	 * @see #getSampledView()
	 */
	public int getSampledWidth();
	
	/**
	 * <p>Returns the X offset component to the sampled view of this picture descriptor. 
	 * The offset is specified relative to the rectangle specified by the 
	 * {@linkplain #getStoredView() stored view}.</p>
	 * 
	 * @return X offset component to the sampled view.
	 * 
	 * @see #getSampledView()
	 * @see #getStoredView()
	 */
	public int getSampledXOffset();
	
	/**
	 * <p>Returns the Y offset component to the sampled view of this picture descriptor. 
	 * The offset is specified relative to the rectangle specified by the 
	 * {@linkplain #getStoredView() stored view}.</p>
	 * 
	 * @return Y offset component to the sampled view.
	 * 
	 * @see #getSampledView()
	 * @see #getStoredView()
	 */
	public int getSampledYOffset();

	/**
	 * <p>Sets the dimension of display view of this picture descriptor.  Typically,
	 * this includes the active picture area, but excludes leading blank video lines
	 * and any VITC lines.  The offset is specified relative to the
	 * rectangle specified by the {@linkplain #getStoredView() stored view}.</p>
	 * 
	 * <p>All properties are optional and are set as follows:</p>
	 * 
	 * <ul>
	 *  <li><code>displayHeight</code> - The default value is the stored height, so use
	 *  {@link #getStoredView() getStoredView()[0]} to set the default value.</li>
	 *  <li><code>displayWidth</code> - The default value is the stored width, so use
	 *  {@link #getStoredView() getStoredView()[1]} to set the default value.</li>
	 *  <li><code>displayXOffset</code> - The default value is&nbsp;0, so use&nbsp;0
	 *  to select the default.</li>
	 *  <li><code>displayYOffset</code> - The default value is&nbsp;0, so use&nbsp;0
	 *  to select the default.</li>
	 * </ul></p>
	 * 
	 * <p>The display view values are either all present or all omitted. Calling this
	 * method successfully will mean display view values are present. To find out whether the display
	 * view properties are present, call {@link #areDisplayValuesPresent()}.</p>
	 * 
	 * @param displayHeight Number of pixels in vertical dimension of display view.
	 * @param displayWidth Number of pixels in vertical dimension of display view.
	 * @param displayXOffset Number of pixels from the top-left corner of the stored view.
	 * @param displayYOffset Number pixels from the top-left corner of the stored view.
	 * 
	 * @throws IllegalPropertyValueException The area specified by display width
	 * and display X offset properties is outside the stored view, or the area specified 
	 * by display height and display Y offset properties is outside the stored view.
	 */
	public void setDisplayView(
			@UInt32 int displayHeight,
			@UInt32 int displayWidth,
			@Int32 int displayXOffset,
			@Int32 int displayYOffset) 
		throws IllegalPropertyValueException;

	/**
	 * <p>Returns the dimensions of the display view of this picture descriptor. 
	 * Typically, this includes the active picture area, but excludes leading blank video lines
	 * and any VITC lines.  The offset is specified relative to the
	 * rectangle specified by the {@linkplain #getStoredView() stored view}.</p>
	 * 
	 * <p>The value returned is an array of four integer values. The index
	 * of the values correspond to:</p>
	 * 
	 * <ul>
	 *  <li><code>0</code> - display height - Number of pixels in vertical 
	 *  dimension of display view.</li>
	 *  <li><code>1</code> - display width - Number of pixels in horizontal 
	 *  dimension of display view.</li>
	 *  <li><code>2</code> - display X offset - Number of pixels from top 
	 *  left corner of stored view.</li>
	 *  <li><code>3</code> - display Y offset - Number of pixels from top 
	 *  left corner of stored view.</li>
	 * </ul></p>
	 * 
	 * <p>The display view properties are either all present or all omitted. If the 
	 * properties are omitted, the default value is returned which is an area
	 * equivalent to the stored view. To find out whether the display view
	 * properties are present, call {@link #areDisplayValuesPresent()}.</p> 

	 * @return Dimensions of the display view.
	 * 
	 * @see #getDisplayHeight()
	 * @see #getDisplayWidth()
	 * @see #getDisplayXOffset()
	 * @see #getDisplayYOffset()
	 */
	public int[] getDisplayView();
	
	/**
	 * <p>Returns the height component of the display view of the picture descriptor. 
	 * Typically, this includes the active picture area, but excludes leading blank video lines
	 * and any VITC lines.</p>
	 * 
	 * @return Height component of the display view.
	 * 
	 * @see #getDisplayView()
	 */
	public int getDisplayHeight();
	
	/**
	 * <p>Returns the width component of the display view of the picture descriptor.</p>
	 * 
	 * @return Width component of the display view.
	 * 
	 * @see #getDisplayView()
	 */
	public int getDisplayWidth();
	
	/**
	 * <p>Returns the X offset of the display view of the picture descriptor. The offset 
	 * is specified relative to the rectangle specified by the 
	 * {@linkplain #getStoredView() stored view}.</p>
	 * 
	 * @return X offset of the display view.
	 * 
	 * @see #getDisplayView()
	 * @see #getStoredView()
	 */
	public int getDisplayXOffset();
	
	/**
	 * <p>Returns the Y offset of the display view of the picture descriptor. The offset 
	 * is specified relative to the rectangle specified by the 
	 * {@linkplain #getStoredView() stored view}.</p>
	 * 
	 * @return Y offset of the display view.
	 * 
	 * @see #getDisplayView()
	 * @see #getStoredView()
	 */
	public int getDisplayYOffset();

	/**
	 * <p>Sets the frame layout for this picture descriptor, which
	 * describes whether all data for a complete sample is in one 
	 * frame or is split into more than one field.</p>
	 * 
	 * @param frameLayout Frame layout for the picture descriptor.
	 * 
	 * @throws NullPointerException The given frame layout is <code>null</code>.
	 */
	public void setFrameLayout(
			LayoutType frameLayout)
		throws NullPointerException;

	/**
	 * <p>Returns the frame layout for this picture descriptor, which
	 * describes whether all data for a complete sample is 
	 * in one frame or is split into more than one field.</p>
	 * 
	 * @return Frame layout for the picture descriptor.
	 */
	public LayoutType getFrameLayout();

	/**
	 * <p>Sets the video line map property of this picture descriptor, which
	 * specifies the scan line in the analog source that 
	 * corresponds to the beginning of each digitized field. For single-field 
	 * video, there is&nbsp;1 value in the array.  For interleaved video, there are&nbsp;2 
	 * values in the array.</p>
	 * 
	 * @param videoLineMap Video line map for the picture descriptor.
	 * 
	 * @throws NullPointerException The given video line map is <code>null</code>.
	 * @throws IllegalArgumentException The size of the video line map must be&nbsp;1
	 * or&nbsp;2 for this picture descriptor.
	 */
	public void setVideoLineMap(
			@VideoLineMap int[] videoLineMap) 
		throws NullPointerException;

	/**
	 * <p>Returns the video line map property of this picture descriptor, which
	 * specifies the scan line in the analog source that 
	 * corresponds to the beginning of each digitized field. For single-field 
	 * video, there is&nbsp;1 value in the array. For interleaved video, there are&nbsp;2 
	 * values in the array.</p>
	 * 
	 * @return Video line map property of this picture descriptor.
	 */
	public @VideoLineMap int[] getVideoLineMap();

	/**
	 * <p>Returns the number of elements in the video line map property 
	 * array of this picture descriptor.</p>
	 * 
	 * @return Number of elements in the video line map property 
	 * array.
	 * 
	 * @see #getVideoLineMap()
	 */
	public @UInt32 int getVideoLineMapSize();

	/**
	 * <p>Sets the image aspect ratio property of this picture descriptor, 
	 * which describes the ratio between the horizontal size and the vertical 
	 * size in the intended final image.</p>
	 * 
	 * @param imageAspectRatio Ratio between horizontal and vertical size of the 
	 * intended final image.
	 * 
	 * @throws NullPointerException The given image aspect ratio is <code>null</code>.
	 */
	public void setImageAspectRatio(
			Rational imageAspectRatio) 
		throws NullPointerException;

	/**
	 * <p>Gets the image aspect ratio property of this picture descriptor,
	 * which describes the ratio between the horizontal size and the vertical 
	 * size in the intended final image.</p>
	 * 
	 * @return Ratio between horizontal and vertical size of the intended final
	 * image.
	 */
	public Rational getImageAspectRatio();

	/**
	 * <p>Sets the alpha transparency property of this picture descriptor, which
	 * specifies whether the minimum alpha value or the maximum
	 * alpha value indicates transparency. To omit this optional property, call this
	 * method with <code>null</code>.</p>
	 * 
	 * @param alphaTransparency Alpha transparency orientation for this picture
	 * descriptor.
	 * 
	 * @see #ALPHATRANSPARENCY_DEFAULT
	 */
	public void setAlphaTransparency(
			AlphaTransparencyType alphaTransparency)
		throws NullPointerException;

	/**
	 * <p>Returns the alpha transparency property of this picture descriptor, which
	 * specifies whether the minimum alpha value or the maximum
	 * alpha value indicates transparency. This is an optional property and if the value
	 * is omitted, the default value of {@linkplain AlphaTransparencyType#MinValueTransparent}
	 * is returned.</p>
	 * 
	 * @return Alpha transparency orientation for this picture descriptor.
	 * 
	 * @see #ALPHATRANSPARENCY_DEFAULT
	 */
	public AlphaTransparencyType getAlphaTransparency();

	/**
	 * <p>Sets the image alignment factor property of this picture
	 * descriptor which specifies the alignment when storing the digital essence.</p>
	 * 
	 * <p>For example, a value of&nbsp;16 means that the image is stored on 16-byte 
	 * boundaries.  The starting point for a field will always be a multiple of 
	 * 16&nbsp;bytes. If the field does not end on a 16-byte boundary, it is padded
	 * out to the next 16-byte boundary.</p>
	 * 
	 * <p>Set this optional property to <code>null</code> to omit the property. The 
	 * default value of this property is&nbsp;{@value #IMAGEALIGNMENTFACTOR_DEFAULT}.</p>
	 * 
	 * @param imageAlignmentFactor Image alignment factor of this picture
	 * descriptor.
	 * 
	 * @throws IllegalArgumentException Cannot set the image alignment factor to
	 * a negative value for this picture descriptor.
	 */
	public void setImageAlignmentFactor(
			@UInt32 Integer imageAlignmentFactor)
		throws IllegalArgumentException;

	/**
	 * <p>Sets the image alignment factor property of this picture
	 * descriptor, which specifies the alignment when storing the digital essence.</p> 
	 * 
	 * <p>For example, a value of&nbsp;16 means that the image is stored on 16-byte 
	 * boundaries.  The starting point for a field will always be a multiple of 
	 * 16&nbsp;bytes. If the field does not end on a 16-byte boundary, it is padded
	 * out to the next 16-byte boundary.</p>
	 * 
	 * <p>This property is optional and the default value of {@value #IMAGEALIGNMENTFACTOR_DEFAULT}
	 * will be returned if it is currently omitted.</p>
	 * 
	 * @return Image alignment factor of this picture descriptor.
	 */
	public @UInt32 int getImageAlignmentFactor();

	/**
	 * <p>Sets the opto-electronic transfer characteristic of this digital 
	 * image descriptor.</p>
	 * 
	 * <p>The default value for the transfer characteristic property is 
	 * <em>unspecified</em>, which is represented by this property being omitted.
	 * If this method is called with a <code>null</code> value, the
	 * this optional property is omitted.</p>
	 * 
	 * @param transferCharacteristic Opto-electronic transfer characteristic of 
	 * the picture descriptor.
	 * 
	 * @see tv.amwa.maj.constant.TransferCharacteristicType
	 * @see tv.amwa.maj.industry.TypeDefinitions#TransferCharacteristicType
	 */
	public void setTransferCharacteristic(
			AUID transferCharacteristic);

	/**
	 * <p>Returns the opto-electronic transfer characteristic of this digital 
	 * image descriptor. If this optional property is omitted, the opto-electronic
	 * characteristic is <em>unspecified</em>.</p>
	 * 
	 * @return Opto-electronic transfer characteristic of this digital 
	 * image descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional opto-electronic characteristic
	 * of this picture descriptor is not present and so is <em>unspecified</em>.
	 * 
	 * @see tv.amwa.maj.constant.TransferCharacteristicType
	 * @see tv.amwa.maj.industry.TypeDefinitions#TransferCharacteristicType
	 */
	public AUID getTransferCharacteristic()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the coding equations property of this picture descriptor, which
	 * specifies the coding equations to convert RGB image components to component 
	 * color difference image components.</p>
	 * 
	 * <p>The default value for this optional property is <em>unspecified</em>,
	 * which is represented by omitting this property. Setting the optional  
	 * coding equations property to <code>null</code> will result in the property 
	 * being omitted.</p>
	 * 
	 * @param codingEquations Coding equations property of this picture 
	 * descriptor.
	 * 
	 * @see tv.amwa.maj.constant.CodingEquationsType
	 * @see tv.amwa.maj.industry.TypeDefinitions#ColorPrimariesType
	 */
	public void setCodingEquations(
			AUID codingEquations);

	/**
	 * <p>Returns the coding equations property of this picture descriptor,
	 * which specifies the coding equations to convert RGB image
	 * components to component color difference image components.</p>
	 * 
	 * <p>The default value for this optional property is <em>unspecified</em>
	 * which is represented by this property being omitted.</p> 
	 * 
	 * @return Coding equations property of this picture descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional coding equations property
	 * is not present for this picture descriptor, indicating that it is 
	 * <em>unspecified</em>.
	 * 
	 * @see tv.amwa.maj.constant.CodingEquationsType
	 * @see tv.amwa.maj.industry.TypeDefinitions#ColorPrimariesType
	 */
	public AUID getCodingEquations()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the color primaries property for this picture descriptor.</p> 
	 * 
	 * <p>The default value for this property is <em>unspecified</em>, which is 
	 * represented by omitting this property. Set this optional property to
	 * <code>null</code> to omit it.</p>
	 * 
	 * @param colorPrimaries Color primaries for the digiral image descriptor.
	 * 
	 * @see tv.amwa.maj.constant.ColorPrimariesType
	 * @see tv.amwa.maj.industry.TypeDefinitions#ColorPrimariesType
	 */
	public void setColorPrimaries(
			AUID colorPrimaries);

	/**
	 * <p>Returns the color primaries property for the picture descriptor.</p>
	 * 
	 * <p>This is an optional property and if the property is omitted it is assumed
	 * to be <em>unspecified</em>.</p>
	 * 
	 * @return Color primaries for this picture descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional color primaries property is not
	 * present for this picture descriptor, indicating that it is <em>unspecified</em>.
	 * 
	 * @see tv.amwa.maj.constant.ColorPrimariesType
	 * @see tv.amwa.maj.industry.TypeDefinitions#ColorPrimariesType
	 */
	public AUID getColorPrimaries()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the image start offset property of this picture descriptor, which
	 * specifies the number of unused bytes at the start of 
	 * each video field.</p>
	 * 
	 * <p>Set this optional property to <code>null</code> to omit its value. The default
	 * value of this property is {@value #IMAGESTARTOFFSET_DEFAULT}.</p>
	 * 
	 * @param imageStartOffset Image start offset of this picture descriptor.
	 * 
	 * @throws IllegalArgumentException The given image start offset is negative.
	 * 
	 * @see #IMAGESTARTOFFSET_DEFAULT
	 */
	public void setImageStartOffset(
			@UInt32 Integer imageStartOffset);

	/**
	 * <p>Returns the image start offset property of this picture descriptor, which
	 * specifies the number of unused bytes at the start of 
	 * each video field.</p>
	 * 
	 * <p>If this property value is omitted, its default value of {@value #IMAGESTARTOFFSET_DEFAULT}
	 * will be returned.</p>
	 * 
	 * @return Image start offset of this picture descriptor.
	 * 
	 * @see #IMAGESTARTOFFSET_DEFAULT
	 */
	public @UInt32 int getImageStartOffset();

	/**
	 * <p>Sets the image end offset property of this picture descriptor, which
	 * specifies the number of unused bytes at the end of each 
	 * video field.</p>
	 * 
	 * <p>Set this optional property to <code>null</code> to omit its value. The default value
	 * of this property is {@value #IMAGEENDOFFSET_DEFAULT}.</p>
	 * 
	 * @param imageEndOffset Image end offset property of this picture
	 * descriptor.
	 * 
	 * @throws IllegalArgumentException Cannot set the image end offset to a negative
	 * value for this picture descriptor.
	 * 
	 * @see #IMAGEENDOFFSET_DEFAULT
	 */
	public void setImageEndOffset(
			@UInt32 Integer imageEndOffset)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the field end offset property of this picture descriptor, which
	 * specifies the number of unused bytes at the end of each 
	 * video field.</p>
	 * 
	 * <p>If this property value is omitted, its default value of {@link #IMAGEENDOFFSET_DEFAULT}
	 * will be returned.</p>
	 * 
	 * @return Field end offset property of this picture descriptor.
	 * 
	 * @see #IMAGEENDOFFSET_DEFAULT
	 */
	public @UInt32 int getImageEndOffset();

	/**
	 * <p>Sets the field dominance property of this picture descriptor, which
	 * specifies whether field&nbsp;1 or field&nbsp;2 is dominant in images composed 
	 * of two interlaced fields.</p>
	 * 
	 * <p>This is an optional property with no default value. Call this method with
	 * <code>null</code> to omit this property.
	 * 
	 * @param fieldDominance Field dominance of this picture descriptor.
	 */
	public void setFieldDominance(
			FieldNumber fieldDominance);

	/**
	 * <p>Returns the field dominance property of this picture descriptor, which
	 * specifies whether field&nbsp;1 or field&nbsp;2 is dominant in images composed 
	 * of two interlaced fields. This is an optional property with no default value.</p>
	 * 
	 * @return Field dominance of this picture descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional field dominance property is
	 * not present for this picture descriptor.
	 */
	public FieldNumber getFieldDominance()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the display field&nbsp;2 offset property of this picture
	 * descriptor. This value specifies a topness adjustment for the 
	 * displayed picture. Valid values are&nbsp;0 or&nbsp;1.</p>
	 * 
	 * <p>Set this optional property to <code>null</code> to omit its value. The
	 * default value of this property is {@value #DISPLAYF2OFFSET_DEFAULT}.</p>
	 * 
	 * @param displayF2Offset Display field&nbsp;2 offset property of this 
	 * picture descriptor.
	 * 
	 * @throws IllegalArgumentException The given display field&nbsp;2 offset is not one 
	 * of the valid values of&nbsp;0 or&nbsp;1.
	 * 
	 * @see #DISPLAYF2OFFSET_DEFAULT
	 */
	public void setDisplayF2Offset(
			@Int32 Integer displayF2Offset)
		throws IllegalArgumentException;
	/**
	 * <p>Returns the display field&nbsp;2 offset property of this picture
	 * descriptor. This value specifies a topness adjustment for the 
	 * displayed picture. Valid values are&nbsp;0 or&nbsp;1.</p>
	 * 
	 * <p>If this property value is omitted, its default value of {@value #DISPLAYF2OFFSET_DEFAULT}
	 * will be returned.</p>
	 * 
	 * @return Display field&nbsp;2 offset property of this picture 
	 * descriptor.
	 * 
	 * @see #DISPLAYF2OFFSET_DEFAULT
	 */
	public @Int32 int getDisplayF2Offset();


	/**
	 * <p>Sets the stored field&nbsp;2 offset property of this picture
	 * descriptor, which specifies a topness adjustment for the 
	 * stored picture. Valid values are&nbsp;0 or&nbsp;-1.</p>
	 * 
	 * <p>Set this optional property to <code>null</code> to omit its value. The
	 * default value of this property is {@value #STOREDF2OFFSET_DEFAULT}.</p>
	 * 
	 * @param storedF2Offset Stored field&nbsp;2 offset property of this digital 
	 * image descriptor.
	 * 
	 * @throws IllegalArgumentException The given stored field&nbsp;2 offset is not one 
	 * of the valid values of&nbsp;0 or&nbsp;-1.
	 * 
	 * @see #STOREDF2OFFSET_DEFAULT
	 */
	public void setStoredF2Offset(
			@Int32 Integer storedF2Offset)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the stored field&nbsp;2 offset property of this picture
	 * descriptor, which specifies a topness adjustment for the 
	 * stored picture. Valid values are&nbsp;0 or&nbsp;-1.</p>
	 * 
	 * <p>This is an optional property and if it is omitted, the default value
	 * of {@value #STOREDF2OFFSET_DEFAULT} will be returned.</p>
	 * 
	 * @return Stored field&nbsp;2 offset property of this digital 
	 * image descriptor.
	 * 
	 * @see #STOREDF2OFFSET_DEFAULT
	 */
	public @Int32 int getStoredF2Offset();

	/**
	 * <p>Sets the active format descriptor property for the picture 
	 * descriptor, which specifies the desired framing of the content 
	 * within the displayed image (4:3&nbsp;in&nbsp;16:9 etc.). The value exactly matches 
	 * the "active_format" element defined by ETSI&nbsp;TS&nbsp;102&nbsp;154.</p>
	 * 
	 * <p>To omit this optional property, call this method with <code>null</code>.
	 * When the property is not present, it is considered to be the same as its default
	 * value of "<em>unspecified</em>".</p>
	 * 
	 * @param activeFormatDescriptor Active format descriptor property for this
	 * picture descriptor.
	 * 
	 * @throws IllegalArgumentException The active format descriptor is a 4-bit value
	 * and cannot lie outside the range&nbsp;0 to&nbsp;15.
	 */
	public void setActiveFormatDescriptor(
			@UInt8 Byte activeFormatDescriptor)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the active format descriptor property for the picture 
	 * descriptor, which specifies the desired framing of the content 
	 * within the displayed image (4:3&nbsp;in&nbsp;16:9 etc.). The value exactly matches 
	 * the active_format element defined by ETSI&nbsp;TS&nbsp;102&nbsp;154.</p>
	 * 
	 * <p>This optional property is set to the default value of "<em>unspecified</em>"
	 * by omitting its value.</p>
	 * 
	 * @return Active format descriptor property for the picture 
	 * descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional active format descriptor property
	 * is not present in this picture descriptor.
	 */
	public @UInt8 byte getActiveFormatDescriptor()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the signal standard property of this picture descriptor, which
	 * specifies the underlying signal standard used to define the raster.</p>
	 * 
	 * <p>The default value for signal standard property is <em>unspecified</em>, which 
	 * is represented by omitting this property. Set this optional property to <code>null</code>
	 * to omit it.</p>
	 * 
	 * @param signalStandard Signal standard property of this picture
	 * descriptor.
	 */
	public void setSignalStandard(
			SignalStandardType signalStandard);

	/**
	 * <p>Returns the signal standard property of this picture descriptor.
	 * This property specifies the underlying signal standard used to define 
	 * the raster.</p>
	 * 
	 * <p>The default value for this property is <em>unspecified</em>, which is
	 * represented by omitting this property.</p>
	 * 
	 * @see SignalStandardType
	 * 
	 * @return Signal standard property of this picture descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional signal standard property is
	 * not present for this picture descriptor, indicating that it is 
	 * <em>unspecified</em>.
	 */
	public SignalStandardType getSignalStandard()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns whether this picture descriptor contains a display view. A value
	 * of <code>true</code> indicates that display height, width, x-offset and y-offset values are present.</p>
	 *
	 * @return Does this picture descriptor contain display size and offset information?
	 * 
	 * @see #getDisplayView()
	 * @see #setDisplayView(int, int, int, int)
	 */
	public boolean areDisplayValuesPresent();
	
	/**
	 * <p>Returns whether this picture descriptor contains a sample area values dimensions and
	 * offsets. A value of <code>true</code> indicates that sampled height, width, x-offset and y-offset 
	 * values are present.</p>
	 *
	 * @return Does this picture descriptor contain sample area values and dimensions?
	 * 
	 * @see #getSampledView()
	 * @see #setSampledView(int, int, int, int)
	 */
	public boolean areSampledValuesPresent();
	
	/**
	 * <p>Create a cloned copy of this picture descriptor.</p>
	 *
	 * @return Cloned copy of this picture descriptor.
	 */
	public PictureDescriptor clone();
}
