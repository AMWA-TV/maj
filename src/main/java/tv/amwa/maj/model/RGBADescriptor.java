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
 * $Log: RGBADescriptor.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/02/28 12:50:35  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.4  2008/02/08 11:27:26  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.3  2008/01/14 21:06:48  vizigoth
 * Minor change due to refactoring of element names in RGBAComponentKind.
 *
 * Revision 1.2  2007/12/04 09:35:36  vizigoth
 * Changed CompArray to RGBALayout annotation.
 *
 * Revision 1.1  2007/11/13 22:08:42  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.RGBAComponentKind;
import tv.amwa.maj.enumeration.ScanningDirectionType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.RGBALayout;
import tv.amwa.maj.record.RGBAComponent;


/**
 * <p>Specifies the description of a file of video essence formatted with three color components 
 * or with three color components and an alpha component.</p>
 * 
 * <p>In addition to the parameters inherited from {@linkplain PictureDescriptor
 * digital image descriptor}, this interface adds pixel layout (order of R, B, G, 
 * alpha), pixel structure (sizes of the various components), palette, palette layout,
 * and palette structure.</p>
 * 
 *
 *
 * @see SourcePackage#getEssenceDescriptor()
 * @see tv.amwa.maj.constant.CodecConstant#RGBA
 * @see tv.amwa.maj.misctype.RGBALayout
 */

public interface RGBADescriptor 
	extends PictureDescriptor {

	/** 
	 * <p>Default value for the component minimum reference level property, which is 
	 * {@value #COMPONENTMINREF_DEFAULT}.</p> 
	 * 
	 * @see #getComponentMinRef()
	 * @see #setComponentMinRef(Integer)
	 */
	public final static int COMPONENTMINREF_DEFAULT = 0;

	/** 
	 * <p>Default value for the component maximum reference level property, which is
	 * {@value #COMPONENTMAXREF_DEFAULT}.</p> 
	 * 
	 * @see #getComponentMaxRef()
	 * @see #setComponentMaxRef(Integer)
	 */
	public final static int COMPONENTMAXREF_DEFAULT = 255;
	
	/** 
	 * <p>Default value for the alpha minimum reference level property, which is
	 * {@value #ALPHAMINREF_DEFAULT}.</p> 
	 * 
	 * @see #getAlphaMinRef()
	 * @see #setAlphaMinRef(Integer)
	 */
	public final static int ALPHAMINREF_DEFAULT = 0;
	
	/** 
	 * <p>Default value for the alpha maximum reference level property, which is
	 * {@value #ALPHAMAXREF_DEFAULT}.</p> 
	 * 
	 * @see #getAlphaMaxRef()
	 * @see #setAlphaMaxRef(Integer)
	 */
	public final static int ALPHAMAXREF_DEFAULT = 255;
	
	/** 
	 * <p>Default value for the scanning direction property, which is 
	 * {@link ScanningDirectionType#LeftToRightTopToBottom}.</p> 
	 * 
	 * @see #getScanningDirection()
	 * @see #setScanningDirection(ScanningDirectionType)
	 */
	public final static ScanningDirectionType SCANNINGDIRECTION_DEFAULT =
		ScanningDirectionType.LeftToRightTopToBottom;
	
	/**
	 * <p>Sets the layout and structure of the components in a single pixel of data
	 * represented by the RGBA descriptor. The 
	 * layout array contains {@linkplain tv.amwa.maj.record.RGBAComponent} items. Each item defines
	 * a size in bits for a component and the {@linkplain tv.amwa.maj.enumeration.RGBAComponentKind 
	 * kind of component} (red, green etc.).</p> 
	 * 
	 * <p>If you set less than 8 entries, the remaining entries will be padded with 
	 * {@link tv.amwa.maj.enumeration.RGBAComponentKind#None} and a size of zero.</p>
	 * 
	 * @param pixelLayoutArray Array of RGBA components, indicating the order and size
	 * of components in the data represented by the RGBA descriptor.
	 * 
	 * @throws NullPointerException The given array of RGBA components is <code>null</code> or
	 * one or more of the elements of the array are <code>null</code>.
	 *  
	 * @see tv.amwa.maj.record.RGBAComponent
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind
	 */
	public void setPixelLayout(
			@RGBALayout RGBAComponent[] pixelLayoutArray) 
		throws NullPointerException;

	/**
	 * <p>Returns the number of components per pixel in data represented by
	 * this RGBA descriptor.</p>
	 *  
	 * @return Number of components per pixel in data represented by this RGBA
	 * descriptor.
	 */
	public @UInt32 int countPixelLayoutElements();

	/**
	 * <p>Returns the layout and structure of the components in a single pixel of data
	 * represented by the RGBA descriptor. The 
	 * layout array contains {@linkplain tv.amwa.maj.record.RGBAComponent} items. Each item defines
	 * a size in bits for a component and the {@linkplain tv.amwa.maj.enumeration.RGBAComponentKind 
	 * kind of component} (red, green etc.).</p>
	 * 
	 * @return Array of RGBA components, indicating the order and size
	 * of components in the data represented by the RGBA descriptor. 
	 */
	public @RGBALayout RGBAComponent[] getPixelLayout();


	// TODO add tests for omit failures
	
	/**
	 * <p>Sets the palette property of the RGBA descriptor, which is an array of color values that 
	 * are used to specify an image. The palette must be 
	 * in the form specified by the {@linkplain #getPaletteLayout() palette layout}.</p>
	 * 
	 * <p>To omit this optional property, set this value to <code>null</code>. 
	 * If the layout of this RGBA descriptor requires a palette, an 
	 * {@link IllegalArgumentException} will be thrown if an attempt is made to
	 * omit the palette property.</p>
	 * 
	 * @param palette Palette of the RGBA descriptor.
	 * 
	 * @throws IllegalArgumentException Cannot omit the palette for an RGBA descriptor that
	 * requires a palette.
	 * 
	 * @see #getPixelLayout()
	 * @see #getPaletteLayout()
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Palette
	 */
	public void setPalette(
			byte[] palette)
		throws IllegalArgumentException; 

	/**
	 * <p>Returns the palette property of the RGBA descriptor, which is an array of color values that 
	 * are used to specify an image. The palette is  
	 * in the form specified by the {@linkplain #getPaletteLayout() palette layout}. This property is 
	 * optional.</p>
	 * 
	 * @return Palette of the RGBA descriptor.
	 * @throws PropertyNotPresentException The optional palette property is not present
	 * in this RGBA descriptor.
	 * 
	 * @see #getPixelLayout()
	 * @see #getPaletteLayout()
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Palette
	 */
	public byte[] getPalette() 
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of components per pixel in the palette of this
	 * RGBA descriptor.</p>
	 * 
	 * @return Number of component per pixel in the palette of this RGBA 
	 * descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional palette layout property is
	 * not present in this RGBA descriptor.
	 * 
	 * @see #getPaletteLayout()
	 */
	public @UInt32 int countPaletteLayoutElements()
		throws PropertyNotPresentException;


	// TODO add tests for omit failures
	
	/**
	 * <p>Sets the layout and structure of the components of a single entry
	 * for the palette of the RGBA descriptor. The layout array contains 
	 * {@linkplain tv.amwa.maj.record.RGBAComponent} items. Each item defines a size in bits for a 
	 * component and the {@linkplain tv.amwa.maj.enumeration.RGBAComponentKind kind of component}
	 * (red, green etc.).</p>
	 * 
	 * <p>If you set less than&nbsp;8 entries, the remaining entries will be padded with 
	 * {@link tv.amwa.maj.enumeration.RGBAComponentKind#None} and a size of zero.</p>
	 * 
	 * <p>To omit this optional property, call this method with <code>null</code>. If 
	 * the RGBA descriptor requires a palette and an attempt is made to omit the property,
	 * an {@link IllegalArgumentException} will be thrown.</p>
	 * 
	 * @param paletteLayoutArray Array of RGBA components, indicating the order and size
	 * of components in the palette of the RGBA descriptor.
	 * 
	 * @throws IllegalArgumentException Cannot omit the palette layout for an RGBA descriptor that
	 * requires a palette, or the given layout refers to a palette.
	 * 
	 * @see #getPalette()
	 * @see tv.amwa.maj.record.RGBAComponent
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Palette
	 */
	public void setPaletteLayout(
			@RGBALayout RGBAComponent[] paletteLayoutArray)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the layout and structure of the components of a single entry
	 * for the palette of the RGBA descriptor. The layout array contains 
	 * {@linkplain tv.amwa.maj.record.RGBAComponent} items. Each item defines a size in bits for a 
	 * component and the {@linkplain tv.amwa.maj.enumeration.RGBAComponentKind kind of component}
	 * (red, green etc.).</p>
	 * 
	 * @return Array of RGBA components, indicating the order and size
	 * of components in the palette of the RGBA descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional palette layout property is not
	 * present for this RGBA descriptor.
	 * 
	 * @see #getPalette()
	 * @see tv.amwa.maj.record.RGBAComponent
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Palette
	 */
	public @RGBALayout RGBAComponent[] getPaletteLayout() 
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the maximum value for RGB components of this RGBA descriptor,
	 * for example 239 or&nbsp;959 (8 or 10&nbsp;bits).</p>
	 * 
	 * <p>The default value for this optional property is {@value #COMPONENTMAXREF_DEFAULT}. To
	 * omit this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param componentMaxRef Maximum value for RGB components of this RGBA descriptor.
	 * 
	 * @throws IllegalArgumentException The given component maximum reference level is negative.
	 * 
	 * @see #COMPONENTMAXREF_DEFAULT
	 * @see #setComponentMinRef(Integer)
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Red
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Green
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Blue
	 */
	public void setComponentMaxRef(
			@UInt32 Integer componentMaxRef)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the maximum value for RGB components of this RGBA descriptor,
	 * for example 239 or&nbsp;959 (8 or 10&nbsp;bits).</p>
	 * 
	 * <p>If this optional property is not present, its default value of
	 * {@value #COMPONENTMAXREF_DEFAULT} is returned.</p>
	 * 
	 * @return Maximum value for RGB components of this RGBA descriptor.
	 * 
	 * @see #COMPONENTMAXREF_DEFAULT
	 * @see #getComponentMinRef()
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Red
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Green
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Blue
	 */
	public @UInt32 int getComponentMaxRef();

	/**
	 * <p>Sets the minimum value for RGB components of the RGBA descriptor, for example
	 * 16 or&nbsp;64 (8 or 10&nbsp;bits).</p>
	 * 
	 * <p>The default value for this optional property is {@value #COMPONENTMINREF_DEFAULT}. 
	 * To omit this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param componentMinRef Minimum value for RGB components of the RGBA
	 * descriptor.
	 * 
	 * @throws IllegalArgumentException The given component minimum reference level is negative.
	 * 
	 * @see #COMPONENTMINREF_DEFAULT
	 * @see #setComponentMaxRef(Integer)
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Red
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Green
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Blue
	 */
	public void setComponentMinRef(
			@UInt32 Integer componentMinRef)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the minimum value for RGB components of the RGBA descriptor, for example
	 * 16 or&nbsp;64 (8 or 10&nbsp;bits).</p>
	 * 
	 * <p>If this optional property is not present, its default value of 
	 * {@value #COMPONENTMINREF_DEFAULT} is returned.</p>
	 * 
	 * @return Minimum value for RGB components.
	 * 
	 * @see #COMPONENTMINREF_DEFAULT
	 * @see #getComponentMaxRef()
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Red
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Green
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind#Blue
	 */
	public @UInt32 int getComponentMinRef();

	/**
	 * <p>Sets the maximum alpha value of the RGBA descriptor, for example
	 * 239 or&nbsp;959 (8 or 10&nbsp;bits).</p>
	 * 
	 * <p>The default value for this optional property is {@value #ALPHAMAXREF_DEFAULT}. 
	 * To omit this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param alphaMaxRef Maximum alpha value of this RGBA descriptor.
	 * 
	 * @throws IllegalArgumentException The given maximum alpha reference level is negative.
	 * 
	 * @see #ALPHAMAXREF_DEFAULT
	 * @see #setAlphaMinRef(Integer)
	 * @see PictureDescriptor#getAlphaTransparency()
	 * @see RGBAComponentKind#Alpha
	 */
	public void setAlphaMaxRef(
			@UInt32 Integer alphaMaxRef)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the maximum alpha value of this RGBA descriptor, for example
	 * 239 or&nbsp;959 (8 or 10&nbsp;bits).</p>
	 * 
	 * <p>If this optional property is not present, the default value of 
	 * {@value #ALPHAMAXREF_DEFAULT} is returned.</p>
	 * 
	 * @return Maximum alpha value of this RGBA descriptor.
	 * 
	 * @see #ALPHAMAXREF_DEFAULT
	 * @see #getAlphaMinRef()
	 * @see PictureDescriptor#getAlphaTransparency()
	 * @see RGBAComponentKind#Alpha
	 */
	public @UInt32 int getAlphaMaxRef();

	/**
	 * <p>Sets the minimum alpha value of this RGBA descriptor, for example
	 * 16 or&nbsp;64 (8 or 10&nbsp;bits).</p>
	 * 
	 * <p>The default value for this property is {@value #ALPHAMINREF_DEFAULT}. To omit
	 * this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param alphaMinRef Minimum alpha value of this RGBA descriptor.
	 * 
	 * @see #ALPHAMINREF_DEFAULT
	 * @see #setAlphaMaxRef(Integer)
	 * @see PictureDescriptor#getAlphaTransparency()
	 * @see RGBAComponentKind#Alpha
	 */
	public void setAlphaMinRef(
			@UInt32 Integer alphaMinRef);

	/**
	 * <p>Returns the minimum alpha value of the RGBA descriptor, for example
	 * 16 or&nbsp;64 (8 or 10&nbsp;bits).</p>
	 * 
	 * <p>If this optional property is not present, the default value of
	 * {@value #ALPHAMINREF_DEFAULT} is returned.</p>
	 * 
	 * @return Minimum alpha value of this RGBA descriptor.
	 * 
	 * @see #ALPHAMINREF_DEFAULT
	 * @see #getAlphaMaxRef()
	 * @see PictureDescriptor#getAlphaTransparency()
	 * @see RGBAComponentKind#Alpha
	 */
	public @UInt32 int getAlphaMinRef();

	/**
	 * <p>Sets the scanning direction of this
	 * RGBA descriptor, which specifies the scanning direction of the image. The value
	 * exactly matches the equivalent property in SMPTE&nbsp;268M.</p>
	 * 
	 * <p>This property is optional and has a default value of 
	 * {@link tv.amwa.maj.enumeration.ScanningDirectionType#LeftToRightTopToBottom}. To omit 
	 * this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param scanningDirection Scanning direction of this RGBA descriptor.
	 * 
	 * @see #SCANNINGDIRECTION_DEFAULT
	 * @see tv.amwa.maj.enumeration.ScanningDirectionType
	 */
	public void setScanningDirection(
			ScanningDirectionType scanningDirection);

	/**
	 * <p>Returns the scanning direction of this
	 * RGBA descriptor, which specifies the scanning direction of the image. The value
	 * exactly matches the equivalent property in SMPTE&nbsp;268M.</p>
	 * 
	 * <p>This property is optional and has a default value of 
	 * {@link tv.amwa.maj.enumeration.ScanningDirectionType#LeftToRightTopToBottom}. If the 
	 * optional property is not present, the default value is returned.</p>
	 * 
	 * @return Scanning direction of this RGBA descriptor.
	 * 
	 * @see #SCANNINGDIRECTION_DEFAULT
	 * @see tv.amwa.maj.enumeration.ScanningDirectionType
	 */
	public ScanningDirectionType getScanningDirection();
	
	/**
	 * <p>Create a cloned copy of this RGBA descriptor.</p>
	 *
	 * @return Cloned copy of this RGBA descriptor.
	 */
	public RGBADescriptor clone();
}
