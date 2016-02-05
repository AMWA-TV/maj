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
 * $Log: MPEGVideoDescriptorImpl.java,v $
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
 * Revision 1.1  2007/11/13 22:09:54  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.ContentScanningType;
import tv.amwa.maj.enumeration.LayoutType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.misctype.VideoLineMap;
import tv.amwa.maj.model.MPEGVideoDescriptor;

/** 
 * <p>Implements the description of picture essence that is encoded according to the 
 * MPEG specifications.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x5100,
		  uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "MPEGVideoDescriptor",
		  description = "Describes essence that is encoded according to the MPEG specifications.",
		  symbol = "MPEGVideoDescriptor")
public class MPEGVideoDescriptorImpl
	extends 
		CDCIDescriptorImpl
	implements 
		MPEGVideoDescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -7207964203762950920L;
	
	private Boolean singleSequence = null;
	private Boolean constantBPictureCount = null;
	private ContentScanningType codedContentScanning = null;
	private Boolean lowDelay = null;
	private Boolean closedGOP = null;
	private Boolean identicalGOP = null;
	private @UInt16 Short maxGOP = null;
	private @UInt16 Short maxBPictureCount = null;
	private @UInt32 Integer bitRate = null;
	private @UInt8 Byte profileAndLevel = null;
	
	public MPEGVideoDescriptorImpl() { }
	
	/**
	 * <p>Creates and initializes a new MPEG video descriptor descriptor, which specifies that a 
	 * {@link SourcePackageImpl file source package} is associated with video essence that is represented and
	 * coded according to the MPEG specifications.</p>
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
	 * @param componentWidth Number of bits used to store each component. Can have a value of 
	 * <code>8</code>, <code>10</code>, or <code>16</code>. Each component in a sample is 
	 * packed contiguously; the sample is filled with the number of bits specified by the optional 
	 * padding bits property. If the padding bits property is omitted, samples are packed contiguously.	 
	 * 
	 * @throws NullPointerException One or more of arguments is <code>null</code>.
	 * @throws IllegalArgumentException The given values are not within the permintted ranges.
	 */
	public MPEGVideoDescriptorImpl(
			tv.amwa.maj.model.ContainerDefinition containerFormat,
			@UInt32 int storedHeight,
			@UInt32 int storedWidth,
			LayoutType frameLayout,
			@VideoLineMap int[] videoLineMap,
			tv.amwa.maj.record.Rational imageAspectRatio,
			@UInt32 int horizontalSubsampling,
			@UInt32 int componentWidth)
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
		setComponentDepth(componentWidth);
	}
	
	@MediaProperty(uuid1 = 0x04010602, uuid2 = 0x010B, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "BitRate",
			     typeName = "UInt32",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x0000,
			     symbol = "BitRate")
	public int getBitRate()
			throws PropertyNotPresentException {

		if (bitRate == null)
			throw new PropertyNotPresentException("The optional bit rate property is not present in this MPEG video descriptor.");
		
		return bitRate;
	}

	@MediaPropertySetter("BitRate")
	public void setBitRate(
			Integer bitRate)
		throws IllegalArgumentException {
		
		if ((bitRate != null) && (bitRate < 0))
				throw new IllegalArgumentException("Cannot set the bit rate to a negative value.");
		this.bitRate = bitRate;
	}

	@MediaProperty(uuid1 = 0x04010602, uuid2 = 0x0106, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "ClosedGOP",
			     typeName = "Boolean",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x0000,
			     symbol = "ClosedGOP")
	public boolean getClosedGOP()
			throws PropertyNotPresentException {

		if (closedGOP == null)
			throw new PropertyNotPresentException("The optional closed GOP property is not present in this MPEG video descriptor.");
		
		return closedGOP;
	}

	@MediaPropertySetter("ClosedGOP")
	public void setClosedGOP(
			Boolean closedGOP) {
		
		this.closedGOP = closedGOP;
	}

	@MediaProperty(uuid1 = 0x04010602, uuid2 = 0x0104, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "CodedContentScanning",
			     typeName = "ContentScanningType",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x0000,
			     symbol = "CodedContentScanning")
	public ContentScanningType getCodedContentScanning()
			throws PropertyNotPresentException {

		if (codedContentScanning == null)
			throw new PropertyNotPresentException("The optional coded content scanning property is not present in this MPEG video descriptor.");
		
		return codedContentScanning;
	}

	@MediaPropertySetter("CodedContentScanning")
	public void setCodedContentScanning(
			ContentScanningType codedContentScanning) {

		this.codedContentScanning = codedContentScanning;
	}

	@MediaProperty(uuid1 = 0x04010602, uuid2 = 0x0103, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "ConstantBPictureCount",
			     typeName = "Boolean",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x0000,
			     symbol = "ConstantBPictureCount")
	public boolean getConstantBPictureCount()
			throws PropertyNotPresentException {
		
		if (constantBPictureCount == null)
			throw new PropertyNotPresentException("The optional constant B picture count property is not present in this MPEG video descriptor.");
		
		return constantBPictureCount;
	}

	@MediaPropertySetter("ConstantBPictureCount")
	public void setConstantBPictureCount(
			Boolean constanBPictureCount) {
		
		this.constantBPictureCount = constanBPictureCount;
	}

	@MediaProperty(uuid1 = 0x04010602, uuid2 = 0x0107, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "IdenticalGOP",
			     typeName = "Boolean",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x0000,
			     symbol = "IdenticalGOP")
	public boolean getIdenticalGOP()
			throws PropertyNotPresentException {

		if (identicalGOP == null)
			throw new PropertyNotPresentException("The optional identifical GOP property is not present in this MPEG video descriptor.");
		
		return identicalGOP;
	}

	@MediaPropertySetter("IdenticalGOP")
	public void setIdenticalGOP(
			Boolean identicalGOP) {

		this.identicalGOP = identicalGOP;
	}

	@MediaProperty(uuid1 = 0x04010602, uuid2 = 0x0105, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "LowDelay",
			     typeName = "Boolean",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x0000,
			     symbol = "LowDelay")
	public boolean getLowDelay()
			throws PropertyNotPresentException {

		if (lowDelay == null)
			throw new PropertyNotPresentException("The optional low delay property is not present in this MPEG video descriptor.");
		
		return lowDelay;
	}

	@MediaPropertySetter("LowDelay")
	public void setLowDelay(
			Boolean lowDelay) {

		this.lowDelay = lowDelay;
	}

	@MediaProperty(uuid1 = 0x04010602, uuid2 = 0x0109, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "MaxBPictureCount",
			     typeName = "UInt16",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x0000,
			     symbol = "MaxBPictureCount")
	public short getMaxBPictureCount()
			throws PropertyNotPresentException {

		if (maxBPictureCount == null)
			throw new PropertyNotPresentException("The optional maximum B picture count property is not present in this MPEG video descriptor.");

		return maxBPictureCount;
	}

	@MediaPropertySetter("MaxBPictureCount")
	public void setMaxBPictureCount(
			Short maxBPictureCount)
		throws IllegalArgumentException {

		if ((maxBPictureCount != null) && (maxBPictureCount < 0))
			throw new IllegalArgumentException("The maximum B picture count cannot be a negative value.");
		
		this.maxBPictureCount = maxBPictureCount;
	}

	@MediaProperty(uuid1 = 0x04010602, uuid2 = 0x0108, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "MaxGOP",
			     typeName = "UInt16",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x0000,
			     symbol = "MaxGOP")
	public short getMaxGOP()
			throws PropertyNotPresentException {

		if (maxGOP == null)
			throw new PropertyNotPresentException("The optional max GOP property is not present in this MPEG video descriptor.");
		
		return maxGOP;
	}

	@MediaPropertySetter("MaxGOP")
	public void setMaxGOP(
			Short maxGOP)
		throws IllegalArgumentException {

		if ((maxGOP != null) && (maxGOP < 0))
			throw new IllegalArgumentException("The max GOP property cannot be set to a negative value.");

		this.maxGOP = maxGOP;
	}

	@MediaProperty(uuid1 = 0x04010602, uuid2 = 0x010A, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "ProfileAndLevel",
			     typeName = "UInt8",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x0000,
			     symbol = "ProfileAndLevel")
	public byte getProfileAndLevel()
			throws PropertyNotPresentException {

		if (profileAndLevel == null)
			throw new PropertyNotPresentException("The optional profile and level property is not present in this MPEG video descriptor.");
		
		return profileAndLevel;
	}

	@MediaPropertySetter("ProfileAndLevel")
	public void setProfileAndLevel(
			Byte profileAndLevel) {

		this.profileAndLevel = profileAndLevel;
	}

	@MediaProperty(uuid1 = 0x04010602, uuid2 = 0x0102, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "SingleSequence",
			     typeName = "Boolean",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x0000,
			     symbol = "SingleSequence")
	public boolean getSingleSequence()
			throws PropertyNotPresentException {

		if (singleSequence == null)
			throw new PropertyNotPresentException("The optional single sequence property is not present in this MPEG video descriptor.");
		
		return singleSequence;
	}

	@MediaPropertySetter("SingleSequence")
	public void setSingleSequence(
			Boolean singleSequence) {

		this.singleSequence = singleSequence;
	}

	@Override
	public MPEGVideoDescriptor clone() {
		
		return (MPEGVideoDescriptor) super.clone();
	}
}
