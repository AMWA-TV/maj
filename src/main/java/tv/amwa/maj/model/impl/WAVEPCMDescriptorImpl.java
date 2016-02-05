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
 * $Log: WAVEPCMDescriptorImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/12/15 19:08:40  vizigoth
 * Better handling of channel assignment default values.
 *
 * Revision 1.3  2010/11/08 15:49:46  vizigoth
 * Version with IO exceptions on stream manipulation methods.
 *
 * Revision 1.2  2010/05/20 18:52:14  vizigoth
 * Adding support for Avid extensions.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:52  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.avid.AvidConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.WAVEPCMDescriptor;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.TimeStamp;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.record.impl.TimeStampImpl;


/** 
 * <p>Implements the description of a file {@linkplain tv.amwa.maj.model.SourcePackage source package} that is associated with 
 * audio essence formatted according to the BWF file format.</p>
 *
 *
 * 
 * @see BWFImportDescriptorImpl
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x4800,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "WAVEPCMDescriptor",
		  aliases = { "PCMDescritpor" },
		  description = "The WAVEPCMDescriptor class specifies that a File SourcePackage is associated with audio essence formatted according to the BWF File Format.",
		  symbol = "WAVEPCMDescriptor")
public class WAVEPCMDescriptorImpl
	extends 
		SoundDescriptorImpl
	implements 
		WAVEPCMDescriptor,
		tv.amwa.maj.extensions.avid.WAVEPCMDescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -7505177867499530833L;
	
	// SMPTE 320M has been withdrawn and channel assignment is now an optional property
	// private static final AUIDImpl smpte320M_B = new AUIDImpl(); 

	private short blockAlign;
	private Byte sequenceOffset = null;
	private int averageBytesPerSecond;
	private AUID channelAssignment = null; 
	private Integer peakEnvelopeVersion = null;
	private Integer peakEnvelopeFormat = null;
	private Integer pointsPerPeakValue = null;
	private Integer peakEnvelopeBlockSize = null;
	private Integer peakChannels = null;
	private Integer peakFrames = null;
	private Long peakOfPeaksPosition = null;
	private TimeStamp peakEnvelopeTimestamp = null;
	private Stream peakEnvelopeData = null;
	
	public WAVEPCMDescriptorImpl() { }

	/**
	 * <p>Creates and initializes a new PCM sound descriptor, which is associated with audio essence 
	 * formatted according to the 
	 * <a href="http://www.ebu.ch/en/technical/publications/userguides/bwf_user_guide.php">BWF File 
	 * Format</a>.</p>
	 *
	 * @param containerFormat Identifies the container mechanism used to store the essence.
	 * @param audioSamplingRate Sampling rate of the audio essence.
	 * @param channels Number of audio channels.
	 * @param quantizationBits Number of quantization bits.
	 * @param blockAlign Sample block alignment.
	 * @param averageBPS Average bytes per second.
	 * 
	 * @throws NullPointerException The container format or sampling rate properties are
	 * <code>null</code>.
	 * @throws IllegalArgumentException Cannot set the channels, quantization bits, block align
	 * or average bits per second values to negative values. 
	 */
	public WAVEPCMDescriptorImpl(
			tv.amwa.maj.model.ContainerDefinition containerFormat,
			tv.amwa.maj.record.Rational audioSamplingRate,
			@UInt32 int channels,
			@UInt32 int quantizationBits,
			@UInt16 short blockAlign,
			@UInt32 int averageBPS)
		throws NullPointerException,
			IllegalArgumentException {
		
		super(containerFormat, audioSamplingRate, channels, quantizationBits);
		
		if (blockAlign < (short) 0)
			throw new IllegalArgumentException("Cannot create a new PCM descriptor with a negative block align value.");
		if (averageBPS < 0)
			throw new IllegalArgumentException("Cannot create a new PCM descriptor with a negative average bits per second value.");
		
		setBlockAlign(blockAlign);
		setAverageBytesPerSecond(averageBPS);
	}
	
	public boolean areAllPeakEnvelopePropertiesPresent() {

		return ((peakEnvelopeVersion != null) &&
				(peakEnvelopeFormat != null) &&
				(pointsPerPeakValue != null) &&
				(peakEnvelopeBlockSize != null) &&
				(peakChannels != null) &&
				(peakFrames != null) &&
				(peakOfPeaksPosition != null) &&
				(peakEnvelopeTimestamp != null) &&
				(peakEnvelopeData != null));
	}

	@MediaProperty(uuid1 = 0x04020303, uuid2 = (short) 0x0500, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "AverageBytesPerSecond",
			aliases = { "AverageBPS" },
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3D09,
			symbol = "AverageBytesPerSecond")
	public int getAverageBytesPerSecond() {

		return averageBytesPerSecond;
	}

	@MediaPropertySetter("AverageBytesPerSecond")
	public void setAverageBytesPerSecond(
			int bps) 
		throws IllegalArgumentException {

		if (bps < 0)
			throw new IllegalArgumentException("Cannot set the average bits per second value to a negative value for this PCM descriptor.");

		averageBytesPerSecond = bps;
	}
	
	public final static int initializeAverageBytesPerSecond() {
		
		return 1;
	}

	@MediaProperty(uuid1 = 0x04020302, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "BlockAlign",
			typeName = "UInt16",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3D0A,
			symbol = "BlockAlign")
	public short getBlockAlign() {

		return blockAlign;
	}

	@MediaPropertySetter("BlockAlign")
	public void setBlockAlign(
			short blockAlign) 
		throws IllegalArgumentException {

			// TODO block align values may be greater than 2^15?
		
			if (blockAlign < (short) 0)
				throw new IllegalArgumentException("Cannot set the block alignment for this PCM descriptor to a negative value.");
			
			this.blockAlign = blockAlign;
	}

	public final static short initializeBlockAlign() {
		
		return 0;
	}
	
	@MediaProperty(uuid1 = 0x04020101, uuid2 = (short) 0x0500, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "ChannelAssignment",
			typeName = "AUID",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D32,
			symbol = "ChannelAssignment")
	public AUID getChannelAssignment() 
		throws PropertyNotPresentException {

		if (channelAssignment == null)
			throw new PropertyNotPresentException("The optional channel assignment property is not present in this PCM descriptor.");

		return channelAssignment.clone();
	}

	@MediaPropertySetter("ChannelAssignment")
	public void setChannelAssignment(
			AUID channelAssignment) {
		
		if (channelAssignment == null) {
			this.channelAssignment = null;
			return;
		}

		this.channelAssignment = channelAssignment.clone();
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0a00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "PeakChannels",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D2D,
			symbol = "PeakChannels")
	public int getPeakChannelCount()
			throws PropertyNotPresentException {

		if (peakChannels == null)
			throw new PropertyNotPresentException("The optional peak channel count property is not present in this PCM descriptor.");
		
		return peakChannels;
	}

	@MediaPropertySetter("PeakChannels")
	public void setPeakChannelCount(
			Integer channelCount) 
		throws IllegalArgumentException {
		
		if (channelCount == null) {
			this.peakChannels = null;
			return;
		}
		
		if (channelCount < 0)
			throw new IllegalArgumentException("Cannot set the peak channels property of a PCM descriptor to a negative value.");

		this.peakChannels = channelCount;
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0900, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "PeakEnvelopeBlockSize",
			typeName = "UInt32", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D2C,
			symbol = "PeakEnvelopeBlockSize")
	public int getPeakEnvelopeBlockSize()
			throws PropertyNotPresentException {

		if (peakEnvelopeBlockSize == null)
			throw new PropertyNotPresentException("The optional peak envelope block size property is not present in this PCM descriptor.");
		
		return peakEnvelopeBlockSize;
	}

	@MediaPropertySetter("PeakEnvelopeBlockSize")
	public void setPeakEnvelopeBlockSize(
			Integer blockSize) 
		throws IllegalArgumentException {

		if (blockSize == null) {
			this.peakEnvelopeBlockSize = null;
			return;
		}
		
		if (blockSize < 0)
			throw new IllegalArgumentException("Cannot set the peak envelope box size of this PCM descriptor to a negative value.");
		
		this.peakEnvelopeBlockSize = blockSize;
	
	}

	public long getPeakEnvelopeDataPosition() 
		throws PropertyNotPresentException,
			IOException {

		if (peakEnvelopeData == null)
			throw new PropertyNotPresentException("The optional peak envelope data property is not present for this WAVE PCM descriptor.");
		
		return peakEnvelopeData.getPosition();
	}

	public void setPeakEnvelopeDataPosition(
			long position) 
		throws PropertyNotPresentException,
			IllegalArgumentException, 
			IOException {

		if (peakEnvelopeData == null)
			throw new PropertyNotPresentException("The optional peak envelope data property is not present for this WAVE PCM descriptor.");
		
		this.peakEnvelopeData.setPosition(position);
	}

	public long getPeakEnvelopeDataSize()
			throws PropertyNotPresentException,
				IOException {

		if (peakEnvelopeData == null)
			throw new PropertyNotPresentException("The optional peak envelope data property is not present in this PCM descriptor.");
		
		return peakEnvelopeData.getLength();
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0700, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "PeakEnvelopeFormat",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D2A,
			symbol = "PeakEnvelopeFormat")
	public int getPeakEnvelopeFormat()
			throws PropertyNotPresentException {

		if (peakEnvelopeFormat == null)
			throw new PropertyNotPresentException("The optional peak envelope format is not present in this PCM descriptor.");
		
		return peakEnvelopeFormat;
	}

	@MediaPropertySetter("PeakEnvelopeFormat")
	public void setPeakEnvelopeFormat(
			Integer format)
		throws IllegalArgumentException {
		
		if (format == null) {
			this.peakEnvelopeFormat = null;
			return;
		}
		
		if (format < 0)
			throw new IllegalArgumentException("Cannot set the peak envelope format property of this PCM descriptor to a negative value.");
	
		this.peakEnvelopeFormat = format;
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0d00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "PeakEnvelopeTimestamp",
			typeName = "TimeStamp",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D30,
			symbol = "PeakEnvelopeTimestamp")
	public TimeStamp getPeakEnvelopeTimestamp()
			throws PropertyNotPresentException {

		if (peakEnvelopeTimestamp == null)
			throw new PropertyNotPresentException("The optional peak envelope timestamp property is not present in this PCM descritpor.");
		
		return peakEnvelopeTimestamp.clone();
	}

	@MediaPropertySetter("PeakEnvelopeTimestamp")
	public void setPeakEnvelopeTimestamp(
			TimeStamp timeStamp) {
		
		if (timeStamp == null) {
			this.peakEnvelopeTimestamp = null;
			return;
		}
		
		this.peakEnvelopeTimestamp = timeStamp.clone();
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0600, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "PeakEnvelopeVersion",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D29,
			symbol = "PeakEnvelopeVersion")
	public int getPeakEnvelopeVersion()
			throws PropertyNotPresentException {
		
		if (peakEnvelopeVersion == null)
			throw new PropertyNotPresentException("The optional peak envelope version property is not present in this PCM descriptor.");

		return peakEnvelopeVersion;
	}

	@MediaPropertySetter("PeakEnvelopeVersion")
	public void setPeakEnvelopeVersion(
			Integer version) 
		throws IllegalArgumentException {
		
		if (version == null) {
			this.peakEnvelopeVersion = null;
			return;
		}
		
		if (version < 0)
			throw new IllegalArgumentException("Cannot set the peak envelope version property of this PCM descriptor to a negative value.");

		this.peakEnvelopeVersion = version;
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0b00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "PeakFrames",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D2E,
			symbol = "PeakFrames")
	public int getPeakFrameCount()
			throws PropertyNotPresentException {

		if (peakFrames == null)
			throw new PropertyNotPresentException("The optional peak frames property is not present in this PCM descriptor.");
		
		return peakFrames;
	}

	@MediaPropertySetter("PeakFrames")
	public void setPeakFrameCount(
			Integer frameCount) 
		throws IllegalArgumentException {

		if (frameCount == null) {
			this.peakFrames = null;
			return;
		}
		
		if (frameCount < 0)
			throw new IllegalArgumentException("Cannot set the peak frames count property of this PCM descriptor to a negative value.");
		
		this.peakFrames = frameCount;
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0c00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "PeakOfPeaksPosition",
			typeName = "PositionType", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D2F,
			symbol = "PeakOfPeaksPosition")
	public long getPeakOfPeaksPosition()
			throws PropertyNotPresentException {

		if (peakOfPeaksPosition == null)
			throw new PropertyNotPresentException("The optional peak of peaks position property is not present in this PCM descriptor.");
		
		return peakOfPeaksPosition;
	}

	@MediaPropertySetter("PeakOfPeaksPosition")
	public void setPeakOfPeaksPosition(
			Long position) {

		this.peakOfPeaksPosition = position;
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0800, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "PointsPerPeakValue",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D2B,
			symbol = "PointsPerPeakValue")
	public int getPointsPerPeakValue()
			throws PropertyNotPresentException {

		if (pointsPerPeakValue == null)
			throw new PropertyNotPresentException("The optional points per peak property is not present in this PCM descriptor.");
		
		return pointsPerPeakValue;
	}

	@MediaPropertySetter("PointsPerPeakValue")
	public void setPointsPerPeakValue(
			Integer pointCount) 
		throws IllegalArgumentException {

		if (pointCount == null) {
			this.pointsPerPeakValue = null;
			return;
		}
		
		if (pointCount < 0)
			throw new IllegalArgumentException("Cannot set the points per peak property of this PCM descriptor to a negative value.");

		this.pointsPerPeakValue = pointCount;
	}

	@MediaProperty(uuid1 = 0x04020302, uuid2 = (short) 0x0200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "SequenceOffset",
			typeName = "UInt8",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D0B,
			symbol = "SequenceOffset")
	public byte getSequenceOffset()
			throws PropertyNotPresentException {

		if (sequenceOffset == null)
			throw new PropertyNotPresentException("The optional sequence offset property is not present in this PCM descritpor.");
		
		return sequenceOffset;
	}

	@MediaPropertySetter("SequenceOffset")
	public void setSequenceOffset(
			Byte offset) 
		throws IllegalArgumentException {

		if (offset == null) {
			this.sequenceOffset = null;
			return;
		}
		
		if (offset < 0)
			throw new IllegalArgumentException("Cannot set the sequence offset of this PCM descriptor to a negative value.");

		this.sequenceOffset = offset;
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0e00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "PeakEnvelopeData",
			typeName = "Stream",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D31,
			symbol = "PeakEnvelopeData")
	public Stream getPeakEnvelopeData() 
		throws PropertyNotPresentException {
		
		if (peakEnvelopeData == null)
			throw new PropertyNotPresentException("The optional peak envelope data property is not present in this PCM descriptor.");
		
		return peakEnvelopeData.clone();
	}

	@MediaPropertySetter("PeakEnvelopeData")
	public void setPeakEnvelopeData(
			Stream peakEnvelopeData) {
		
		if (peakEnvelopeData == null)
			this.peakEnvelopeData = null;
		else
			this.peakEnvelopeData = peakEnvelopeData.clone();
	}

	public ByteBuffer readPeakEnvelopeData(
			int bytes)
		throws EndOfDataException,
			PropertyNotPresentException, 
			IOException {

		if (peakEnvelopeData == null)
			throw new PropertyNotPresentException("The optional peak envelope data property is not present for this WAVE PCM descriptor.");
		
		return peakEnvelopeData.read(bytes);
	}

	public int writePeakEnvelopeData(
			ByteBuffer bytes)
		throws PropertyNotPresentException,
			EndOfDataException, 
			IOException {

		if (peakEnvelopeData == null)
			throw new PropertyNotPresentException("The optional peak envelope data property is not present for this WAVE PCM descriptor.");		
		
		return peakEnvelopeData.write(bytes);
	}

	public WAVEPCMDescriptor clone() {
		
		return (WAVEPCMDescriptor) super.clone();

	}
	
	// AVID extension properties - start
	
	private @Int32 Integer dataOffset = null;
	
	@MediaProperty(uuid1 = 0xbb3fabdd, uuid2 = (short) 0xfcc0, uuid3 = (short) 0x43a8,
			uuid4 = { (byte) 0x97, 0x59, (byte) 0xc7, 0x27, 0x77, 0x1f, (byte) 0xcc, 0x4a },
			definedName = "DataOffset",
			typeName = "Int32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "DataOffset2",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public @Int32 int getDataOffset()
		throws PropertyNotPresentException {
		
		if (dataOffset == null)
			throw new NullPointerException("The optional data offset property is not present for this WAVE PCM descriptor.");
		
		return dataOffset;
	}
	
	@MediaPropertySetter("DataOffset")
	public void setDataOffset(
			@Int32 Integer dataOffset) {
		
		this.dataOffset = dataOffset;
	}

	// AVID extension properties - end
	
	public String getChannelAssignmentString() {
		
		return AUIDImpl.toPersistentForm(channelAssignment);
	}
	
	public void setChannelAssignmentString(
			String channelAssignment) {
		
		this.channelAssignment = AUIDImpl.fromPersistentForm(channelAssignment);
	}
	
	public String getPeakEnvelopeTimestampString() {
		
		return TimeStampImpl.toPersistentForm(peakEnvelopeTimestamp);
	}
	
	public void setPeakEnvelopeTimestampString(
			String peakEnvelopeTimestampString) {
		
		this.peakEnvelopeTimestamp = TimeStampImpl.fromPersistentForm(peakEnvelopeTimestampString);
	}
}
