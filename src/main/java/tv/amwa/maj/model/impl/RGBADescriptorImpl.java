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
 * $Log: RGBADescriptorImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/03/19 16:20:06  vizigoth
 * Added a TODO.
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
 * Revision 1.2  2008/01/14 21:13:14  vizigoth
 * Minor change due to refactoring of element names in RGBAComponentKind.
 *
 * Revision 1.1  2007/11/13 22:09:45  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import tv.amwa.maj.enumeration.LayoutType;
import tv.amwa.maj.enumeration.RGBAComponentKind;
import tv.amwa.maj.enumeration.ScanningDirectionType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.DataValue;
import tv.amwa.maj.misctype.VideoLineMap;
import tv.amwa.maj.model.ContainerDefinition;
import tv.amwa.maj.model.RGBADescriptor;
import tv.amwa.maj.record.RGBAComponent;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.RGBAComponentImpl;


/** 
 * <p>Implements the description of a file of video essence formatted with three color components 
 * or with three color components and an alpha component.</p>
 *
 *
 *
 */ 

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2900,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "RGBADescriptor",
		  description = "The RGBADescriptor class specifies that a File SourcePackage is associated with video essence formatted with three color component or with three color components and an alpha component as specified in this document.",
		  symbol = "RGBADescriptor")
public class RGBADescriptorImpl
	extends 
		PictureDescriptorImpl
	implements 
		RGBADescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 5533596704038036966L;

	private Integer alphaMaxRef = null;
	private Integer alphaMinRef = null;
	private Integer componentMaxRef = null;
	private Integer componentMinRef = null;

	private RGBAComponent[] pixelLayout;
	private byte[] palette = null;
	private RGBAComponent[] paletteLayout = null;
	private ScanningDirectionType scanningDirection = null;
	
	public RGBADescriptorImpl() { }

	/**
	 * <p>Creates and initalizes a new RGBA descriptor without a palette. An RGBA descriptor 
	 * specifies that a {@link SourcePackageImpl file source package} is associated with video essence formatted 
	 * with three color components or with three color components and an alpha component.</p>
	 *
	 * @param containerFormat Container mechanism used to store the essence.
	 * @param storedHeight Number of pixels in vertical dimension of stored view.
	 * @param storedWidth Number of pixels in horizontal dimension of stored view.
	 * @param frameLayout Describes whether all data for a complete sample is in one 
	 * frame or is split into more than one field.
	 * @param videoLineMap The scan line in the analog source that corresponds to the 
	 * beginning of each digitized field. For single-field video, there is 1 value in 
	 * the array; for interlaced video, there are 2 values in the array.
	 * @param imageAspectRatio Describes the ratio between the horizontal size and the vertical 
	 * size in the intended final image.
	 * @param pixelLayout Order and size of the components within the pixel.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>.
	 * @throws IllegalArgumentException The given values are outside acceptable bounds or
	 * the pixel layout requires a palette and a palette is not provided. In this case, use 
	 * {@link #RGBADescriptor(tv.amwa.maj.model.ContainerDefinition, int, int, LayoutType, int[], tv.amwa.maj.record.Rational, tv.amwa.maj.record.RGBAComponent[], byte[], tv.amwa.maj.record.RGBAComponent[]) RGBADescriptor(..., byte[], RGBAComponent[])}
	 * instead.
	 */
	public RGBADescriptorImpl(
			ContainerDefinition containerFormat,
			@UInt32 int storedHeight,
			@UInt32 int storedWidth,
			LayoutType frameLayout,
			@VideoLineMap int[] videoLineMap,
			Rational imageAspectRatio,
			RGBAComponent[] pixelLayout)
		throws NullPointerException,
			IllegalArgumentException {
		
		// Allow container format to be null in case it is not specified in older files
		if (frameLayout == null)
			throw new NullPointerException("Cannot create a new RGBA descriptor with a null frame layout value.");
		if (videoLineMap == null)
			throw new NullPointerException("Cannot create a new RGBA descriptor with a null video line map value.");
		if (imageAspectRatio == null)
			throw new NullPointerException("Cannot create a new RGBA descriptor with a null image aspect ration value.");
		if (pixelLayout == null)
			throw new NullPointerException("Cannot create a new RGBA descriptor with a null pixel layout descriptor.");
		
		setContainerFormat(containerFormat);
		setStoredView(storedHeight, storedWidth);
		setFrameLayout(frameLayout);
		setVideoLineMap(videoLineMap);
		setImageAspectRatio(imageAspectRatio);
		
		if (paletteRequired(pixelLayout) == true)
			throw new IllegalArgumentException("Cannot create a new RGBA descriptor requiring a palette without a palette and palette layout.");
		setPixelLayout(pixelLayout);
	}

	/**
	 * <p>Creates and initalizes a new RGBA descriptor that includes a palette. An RGBA descriptor 
	 * specifies that a {@link SourcePackageImpl file source package} is associated with video essence formatted 
	 * with three color components or with three color components and an alpha component.</p> 
	 * 
	 * <p>Note that this method can be used to create a descriptor for image data 
	 * that does not have a palette, although this will result in the storage of 
	 * redundant data within the descriptor. If not palette is required, use
	 * {@link #RGBADescriptor(tv.amwa.maj.model.ContainerDefinition, int, int, LayoutType, int[], tv.amwa.maj.record.Rational, tv.amwa.maj.record.RGBAComponent[])}
	 * instead.</p>
	 *
	 * @param containerFormat Container mechanism used to store the essence.
	 * @param storedHeight Number of pixels in vertical dimension of stored view.
	 * @param storedWidth Number of pixels in horizontal dimension of stored view.
	 * @param frameLayout Describes whether all data for a complete sample is in one 
	 * frame or is split into more than one field.
	 * @param videoLineMap The scan line in the analog source that corresponds to the 
	 * beginning of each digitized field. For single-field video, there is 1 value in 
	 * the array; for interlaced video, there are 2 values in the array.
	 * @param imageAspectRatio Describes the ratio between the horizontal size and the vertical 
	 * size in the intended final image.
	 * @param pixelLayout Order and size of the components within the pixel.
	 * @param palette Palette to include with this digital image descriptor.
	 * @param paletteLayout Layout of the values within the palette.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>.
	 * @throws IllegalArgumentException The given values are outside acceptable bounds or
	 * the pixel layout requires a palette and an adequate palette is not provided.
	 */
	public RGBADescriptorImpl(
			ContainerDefinition containerFormat,
			@UInt32 int storedHeight,
			@UInt32 int storedWidth,
			LayoutType frameLayout,
			@VideoLineMap int[] videoLineMap,
			Rational imageAspectRatio,
			RGBAComponent[] pixelLayout,
			@DataValue byte[] palette,
			RGBAComponent[] paletteLayout)
		throws NullPointerException,
			IllegalArgumentException {
		
		// Allow container format to be null in case it is not specified in older files
		if (frameLayout == null)
			throw new NullPointerException("Cannot create a new RGBA descriptor with a null frame layout value.");
		if (videoLineMap == null)
			throw new NullPointerException("Cannot create a new RGBA descriptor with a null video line map value.");
		if (imageAspectRatio == null)
			throw new NullPointerException("Cannot create a new RGBA descriptor with a null image aspect ration value.");
		if (pixelLayout == null)
			throw new NullPointerException("Cannot create a new RGBA descriptor with a null pixel layout descriptor.");
		
		setContainerFormat(containerFormat);
		setStoredView(storedHeight, storedWidth);
		setFrameLayout(frameLayout);
		setVideoLineMap(videoLineMap);
		setImageAspectRatio(imageAspectRatio);
		
		// Must set the palette before the pixel layout.
		setPalette(palette);
		setPaletteLayout(paletteLayout);
		
		setPixelLayout(pixelLayout);

	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0d00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "AlphaMaxRef",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3408,
			symbol = "AlphaMaxRef")
	public int getAlphaMaxRef() {

		if (alphaMaxRef == null)
			return ALPHAMAXREF_DEFAULT;
		else
			return alphaMaxRef;
	}

	@MediaPropertySetter("AlphaMaxRef")
	public void setAlphaMaxRef(
			Integer alphaMaxRef) 
		throws IllegalArgumentException {
	
		if (alphaMaxRef == null) {
			this.alphaMaxRef = null;
			return;
		}
			
		if (alphaMaxRef < 0)
			throw new IllegalArgumentException("Cannot set the alpha maximum reference level for this RGBA descriptor to a negative value");

		// TODO consider range checking?
		
		this.alphaMaxRef = alphaMaxRef;
	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0e00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "AlphaMinRef",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3409,
			symbol = "AlphaMinRef")
	public int getAlphaMinRef() {

		if (alphaMinRef == null)
			return ALPHAMINREF_DEFAULT;
		else
			return alphaMinRef;
	}

	@MediaPropertySetter("AlphaMinRef")
	public void setAlphaMinRef(
			Integer alphaMinRef) 
		throws IllegalArgumentException {
		
		if (alphaMinRef == null) {
			this.alphaMinRef = null;
			return;
		}
		
		if (alphaMinRef < 0)
			throw new IllegalArgumentException("Cannot set the alpha minimum reference level for this RGBA descriptor to a negative value.");
	
		this.alphaMinRef = alphaMinRef;
	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0b00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "ComponentMaxRef",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3406,
			symbol = "ComponentMaxRef")
	public int getComponentMaxRef() {

		if (componentMaxRef == null)
			return COMPONENTMAXREF_DEFAULT;
		else
			return componentMaxRef;
	}

	@MediaPropertySetter("ComponentMaxRef")
	public void setComponentMaxRef(
			Integer componentMaxRef) 
		throws IllegalArgumentException {
		
		if (componentMaxRef == null) {
			this.componentMaxRef = null;
			return;
		}
		
		if (componentMaxRef < 0)
			throw new IllegalArgumentException("Cannot set the component minimum refererence level for this RGBA descriptor using a negative value.");
		
		// TODO consider range checking?
		
		this.componentMaxRef = componentMaxRef;
	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0c00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "ComponentMinRef",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3407,
			symbol = "ComponentMinRef")
	public int getComponentMinRef() {

		if (componentMinRef == null)
			return COMPONENTMINREF_DEFAULT;
		else
			return componentMinRef;
	}

	@MediaPropertySetter("ComponentMinRef")
	public void setComponentMinRef(
			Integer componentMinRef) 
		throws IllegalArgumentException {

		if (componentMinRef == null) {
			this.componentMinRef = null;
			return;
		}
		
		if (componentMinRef < 0)
			throw new IllegalArgumentException("Cannot set the component minimum reference level for thie RGBA descriptor using a negative value.");
		
		this.componentMinRef = componentMinRef;
	}
	
	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0800, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Palette",
			typeName = "DataValue",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3403,
			symbol = "Palette")
	public byte[] getPalette() 
		throws PropertyNotPresentException {

		if (palette == null)
			throw new PropertyNotPresentException("The optional pallette property is not present for this RGBA descriptor.");
		
		return palette.clone();
	}

	public void setPalette(
			byte[] palette) 
		throws IllegalArgumentException {

		if ((palette == null) && (paletteRequired(pixelLayout) == true))
			throw new IllegalArgumentException("Cannot unset the palette for an RGBA descriptor which requires palette values.");
		
		if (palette == null) {
			this.palette = null;
			return;
		}
		
		this.palette = palette.clone();
	}
	
	// TODO check if this method is called correctly
	@MediaPropertySetter("Palette")
	public void setPaletteFromStream(
			ByteBuffer palette) {
		
		if (palette == null) {
			this.palette = null;
			return;
		}

		this.palette = new byte[palette.limit()];
		palette.rewind();
		palette.get(this.palette);
	}

	@MediaPropertyClear("Palette")
	public void omitPalette() 
		throws IllegalArgumentException {
		
		if (paletteRequired(pixelLayout) == true)
			throw new IllegalArgumentException("Cannot unset the palette for an RGBA descriptor which requires palette values.");
		
		palette = null;
	}
	
	public int countPaletteLayoutElements() {

		return countLayoutElements(paletteLayout);
	}

	public int getPaletteSize() {

		return palette.length;
	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0900, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PaletteLayout",
			typeName = "RGBALayout",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3404,
			symbol = "PaletteLayout")
	public RGBAComponent[] getPaletteLayout() 
		throws PropertyNotPresentException {

		if (paletteLayout == null)
			throw new PropertyNotPresentException("The optional palette layout property is not present for this RGBA descriptor.");
		
		return paletteLayout;
	}

	public void setPaletteLayout(
			RGBAComponent[] paletteLayoutArray) 
		throws IllegalArgumentException {

		if ((paletteLayoutArray == null) && (paletteRequired(pixelLayout) == true))
			throw new IllegalArgumentException("Cannot remove the palette layout for a RGBA descriptor that requires palette values.");
		
		if (paletteRequired(paletteLayoutArray))
			throw new IllegalArgumentException("Cannot set a pallette layout for an RGBA descriptor with components that reference palette.");
		
		if (paletteLayoutArray == null) {
			this.paletteLayout = null;
			return;
		}

		this.paletteLayout = new RGBAComponent[paletteLayoutArray.length];
		for ( int x = 0 ; x < paletteLayout.length ; x++) 
			if (paletteLayoutArray[x] != null)
				paletteLayout[x] = paletteLayoutArray[x].clone();
	}
	
	@MediaPropertySetter("PaletteLayout")
	public void setPaletteLayoutFromStream(
			Object[] paletteLayoutArray) {
		
		if (paletteLayoutArray == null) {
			this.paletteLayout = null;
			return;
		}
		
		this.paletteLayout = new RGBAComponentImpl[paletteLayoutArray.length];
		for ( int x = 0 ; x < paletteLayout.length ; x++) 
			if (paletteLayoutArray[x] != null)
				paletteLayout[x] = ((RGBAComponent) paletteLayoutArray[x]).clone();
	}

	@MediaProperty(uuid1 = 0x04010503, uuid2 = (short) 0x0600, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PixelLayout",
			typeName = "RGBALayout",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3401,
			symbol = "PixelLayout")
	public RGBAComponent[] getPixelLayout() {
	
		return pixelLayout.clone();
	}

	public void setPixelLayout(
			tv.amwa.maj.record.RGBAComponent[] pixelLayoutArray)
		throws NullPointerException,
			IllegalArgumentException {

		if (pixelLayoutArray == null)
			throw new NullPointerException("Cannot set the pixel layout of this RGBA descriptor with a null value.");
		
		if (validatePixelLayout(pixelLayoutArray) == false)
			throw new IllegalArgumentException("The given pixel array is not valid as it contains a mix of palette and RGB data.");
		
		if ((paletteRequired(pixelLayoutArray) == true) && 
				((palette == null) || (paletteLayout == null)))
			throw new IllegalArgumentException("Palette and palette layout properties must be set for pixel layouts containing palette entries.");
		
		pixelLayout = new RGBAComponent[pixelLayoutArray.length];
		for ( int x = 0 ; x < pixelLayout.length ; x++ )
			if (pixelLayoutArray[x] != null)
				pixelLayout[x] = pixelLayoutArray[x].clone();
	}
	
	@MediaPropertySetter("PixelLayout")
	public void setPixelLayoutFromStream(
			Object[] pixelLayoutArray) 
		throws NullPointerException,
			IllegalArgumentException,
			ClassCastException {
	
		if (pixelLayoutArray == null)
			throw new NullPointerException("Cannot set the pixel layout of this RGBA descriptor with a null value.");

		pixelLayout = new RGBAComponent[pixelLayoutArray.length];
		for ( int x = 0 ; x < pixelLayout.length ; x++ )
			if (pixelLayoutArray[x] != null)
				pixelLayout[x] = ((RGBAComponent) pixelLayoutArray[x]).clone();

		if (validatePixelLayout(pixelLayout) == false)
			throw new IllegalArgumentException("The given pixel array is not valid as it contains a mix of palette and RGB data.");	
	}
	
	public final static RGBAComponent[] initializePixelLayout() {
		
		RGBAComponent[] initialElements = new RGBAComponent[8];
		for ( int x = 0 ; x < 8 ; x++ )
			initialElements[x] = new RGBAComponentImpl(RGBAComponentKind.Null, (byte) 0);
		
		return initialElements;
	}

	public int countPixelLayoutElements() {

		return countLayoutElements(pixelLayout);
	}

	@MediaProperty(uuid1 = 0x04010404, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "ScanningDirection",
			typeName = "ScanningDirectionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3405,
			symbol = "ScanningDirection")
	public ScanningDirectionType getScanningDirection() {

		if (scanningDirection == null)
			return SCANNINGDIRECTION_DEFAULT;
		else
			return scanningDirection;
	}

	@MediaPropertySetter("ScanningDirection")
	public void setScanningDirection(
			ScanningDirectionType scanningDirection) {

		if (scanningDirection == null)
			this.scanningDirection = ScanningDirectionType.LeftToRightTopToBottom;
		else
			this.scanningDirection = scanningDirection;
	}
	
	/**
	 * <p>Checks the given pixel layout to see if a palette is required for this RGBA descriptor. This
	 * is the case when one of the components of the pixel layout references palette values.</p>
	 *
	 * @param Pixel layout to check for palette values.
	 * @return Does the given RGBA descriptor required a palette?
	 */
	private static boolean paletteRequired(
			tv.amwa.maj.record.RGBAComponent[] pixelLayout) {
		
		if (pixelLayout == null) return false;
		
		for ( tv.amwa.maj.record.RGBAComponent component : pixelLayout ) {
			if (component.getCode() == RGBAComponentKind.Palette) return true;
		}
		
		return false;
	}
	
	/**
	 * <p>Validates the given pixel layout. Invalid pixel layouts are ones that contain both palette
	 * references and RGB values.</p>
	 *
	 * @param pixelLayout Pixel layout to check?
	 * @return Is the given pixel layout valid?
	 */
	private static boolean validatePixelLayout(
			tv.amwa.maj.record.RGBAComponent[] pixelLayout) {
		
		if (pixelLayout == null) return false;
		
		boolean paletteFound = false;
		boolean rgbFound = false;
		
		for ( int x = 0 ; x < pixelLayout.length ; x++ ) {
			
			switch (pixelLayout[x].getCode()) {
			
			case Palette:
				paletteFound = true;
				if (rgbFound == true) return false;
				break;
			case Red:
			case Green:
			case Blue:
				rgbFound = true;
				if (paletteFound == true) return false;
				break;
			default:
				break;
			}
		}

		return true;
	}
	
	/**
	 * <p>Counts the number of useful components in the given layout.</p>
	 *
	 * @param layout Layout to determine the number of useful components for.
	 * @return Number of useful components in the given layout.
	 */
	private static int countLayoutElements(
			tv.amwa.maj.record.RGBAComponent[] layout) {
		
		int componentCount = 0;
		for (tv.amwa.maj.record.RGBAComponent component : layout) 
			
			switch (component.getCode()) {
			
			case None:
			case Fill:
			case Null:
				break;
			default:
				componentCount++;
				break;
			}
			
		return componentCount;
	}

	public RGBADescriptor clone() {
		
		return (RGBADescriptor) super.clone();
	}

	public List<String> getPixelLayoutStringList() {
		
		return layoutToList(pixelLayout);
	}
	
	public void setPixelLayoutStringList(
			List<String> pixelLayout) {
		
		this.pixelLayout = layoutFromList(pixelLayout);
	}

	public List<String> getPaletteLayoutStringList() {
		
		return layoutToList(paletteLayout);
	}
	
	public void setPaletteLayoutStringList(
			List<String> paletteLayout) {
		
		this.paletteLayout = layoutFromList(paletteLayout);
	}

	private final static List<String> layoutToList(
			RGBAComponent[] layout) {
		
		if (layout == null) return null;
		
		List<String> layoutList = new ArrayList<String>(layout.length);
		for ( int x = 0 ; x < layout.length ; x++ )
			layoutList.add(RGBAComponentImpl.toPersistentForm(layout[x]));
		return layoutList;
	}
	
	private final static RGBAComponent[] layoutFromList(
			List<String> layoutList) {
		
		if (layoutList == null) return null;
		
		RGBAComponent[] layout = new RGBAComponent[layoutList.size()];
		for ( int x = 0 ; x < layout.length ; x++ )
			layout[x] = RGBAComponentImpl.fromPersistentForm(layoutList.get(x));
		return layout;
	}
}
