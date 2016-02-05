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
 * $Log: WAVEPCMDescriptor.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/11/08 16:03:23  vizigoth
 * Version with IO exceptions on stream manipulation methods.
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
 * Revision 1.3  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2008/02/08 11:27:18  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:18  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.io.IOException;
import java.nio.ByteBuffer;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.TimeStamp;

/**
 * <p>Specifies the description of a file {@linkplain SourcePackage source package} that is associated with 
 * audio essence formatted according to the BWF file format.</p>
 * 
 * <p>See section&nbsp;13.2 of the <a href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF
 * object specification v1.1</a> for details of mapping BWF file metadata to the AAF object model.</a>
 * 
 *
 * 
 * @see tv.amwa.maj.constant.CodecConstant#PCM
 * @see AES3PCMDescriptor
 */

public interface WAVEPCMDescriptor 
	extends SoundDescriptor {
	
	// TODO pull the definition of the UL for SMPTE 320M-B in here or in as a constant
	
	/**
	 * <p>Sets the number of bytes used to store one sample of all channels
	 * of data associated with this PCM descriptor. This property is required.</p>
	 * 
	 * @param blockAlign The number of bytes used to store one sample of all 
	 * channels.
	 * 
	 * @throws IllegalArgumentException The given block align value is negative.
	 */
	public void setBlockAlign(
			@UInt16 short blockAlign);

	/**
	 * <p>Returns the number of bytes used to store one sample of all channels
	 * of data associated with this PCM descriptor. This property is required.</p>
	 * 
	 * @return Number of bytes used to store one sample of all channels.
	 */
	public @UInt16 short getBlockAlign();
	
	/**
	 * <p>Sets the frame number of the beginning of the essence data
	 * within a five-frame sequence for this PCM descriptor. This property 
	 * is optional and zero-based. Set to <code>null</code> omit it.</p>
	 * 
	 * @param offset Frame number of the beginning of the essence data
	 * within a five-frame sequence.
	 * 
	 * @throws IllegalArgumentException The given sequence offset value is
	 * negative.
	 */
	public void setSequenceOffset(
			@UInt8 Byte offset)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the frame number of the beginning of the essence data
	 * within a five-frame sequence for this PCM descriptor. This property 
	 * is optional and zero-based.</p>
	 * 
	 * @return Frame number of the beginning of the essence data
	 * within a five-frame sequence.
	 * 
	 * @throws PropertyNotPresentException This optional property is not
	 * present in this PCM descriptor.
	 */
	public @UInt8 byte getSequenceOffset()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the average bytes per second of the essence stream described
	 * by this PCM descriptor. This property is required.</p>
	 *  
	 * @param bps Average bytes per second of the essence stream.
	 * 
	 * @throws IllegalArgumentException The given average bytes per second
	 * value is negative.
	 */
	public void setAverageBytesPerSecond(
			@UInt32 int bps)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the average bytes per second of the essence stream described
	 * by this PCM descriptor.</p>
	 * 
	 * @return Average bytes per second of the essence stream described
	 * by this PCM descriptor.
	 */
	public @UInt32 int getAverageBytesPerSecond();

	/**
	 * <p>Sets the channel assignment scheme of this PCM descriptor. An
	 * example of this would be an {@linkplain tv.amwa.maj.record.AUID AUID} 
	 * representing "SMPTE&nbsp;320M-B". This
	 * property is optional and set by default to the identifier for 
	 * "SMPTE 320M-B". Calling this method with <code>null</code> will
	 * restore the default value.</p>
	 * 
	 * @param channelAssignment Channel assignment to use for this PCM descriptor.
	 */
	public void setChannelAssignment(
			AUID channelAssignment);

	/**
	 * <p>Returns the channel assignment scheme for this PCM descriptor. An
	 * example of this would be an AUID representing "SMPTE 320M-B". This
	 * property is optional and has a default value of "SMPTE 320M-B".</p>
	 * 
	 * @return Channel assignment scheme for the PCM descriptor.
	 */
	public AUID getChannelAssignment();

	/**
	 * <p>Returns <code>true</code> if all the following optional properties are
	 * set for this PCM descriptor:</p>
	 * 
	 * <ul>
	 *  <li>PeakEnvelopeVersion - see {@link #setPeakEnvelopeVersion(Integer)}</li>
	 *  <li>PeakEnvelopeFormat - see {@link #setPeakEnvelopeFormat(Integer)}</li>
	 *  <li>PointsPerPeakValue - see {@link #setPointsPerPeakValue(Integer)}</li>
	 *  <li>PeakEnvelopeBlockSize - see {@link #setPeakEnvelopeBlockSize(Integer)}</li>
	 *  <li>PeakChannels - see {@link #setPeakChannelCount(Integer)}</li>
	 *  <li>PeakFrames - see {@link #setPeakChannelCount(Integer)}</li>
	 *  <li>PeakOfPeaksPosition - see {@link #setPeakOfPeaksPosition(Long)}</li>
	 *  <li>PeakEnvelopeTimestamp - see {@link #setPeakEnvelopeTimestamp(TimeStamp)}</li>
	 *  <li>PeakEnvelopeData - see {@link #setPeakEnvelopeDataPosition(long)},
	 *  {@link #getPeakEnvelopeDataSize()}, {@link #writePeakEnvelopeData(ByteBuffer)} and
	 *  {@link #readPeakEnvelopeData(int)}.</li>
	 * </ul>
	 * 
	 * @return Are all the optional peak envelope properties present in this PCM
	 * descriptor?
	 */
	public @Bool boolean areAllPeakEnvelopePropertiesPresent();
	
	/**
	 * <p>Sets the version information of the peak envelope data of this PCM 
	 * descriptor. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param version Version information of the peak envelope data of this PCM 
	 * descriptor.
	 * 
	 * @throws IllegalArgumentException Cannot set the peak envelope version
	 * property of this PCM descriptor to a negative value.
	 */
	public void setPeakEnvelopeVersion(
			@UInt32 Integer version)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the version information of the peak envelope data of this PCM
	 * descriptor. This property is optional.</p>
	 * 
	 * @return Version information of the peak envelope data of this PCM
	 * descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional peak envelope version property is not
	 * present in this PCM descriptor.
	 */
	public @UInt32 int getPeakEnvelopeVersion()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the format of the peak point for this PCM descriptor.
	 * Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param format Format of the peak point for this PCM descriptor.
	 * 
	 * @throws IllegalArgumentException The given peak envelope format is
	 * negative.
	 */
	public void setPeakEnvelopeFormat(
			@UInt32 Integer format)
		throws IllegalArgumentException; 

	/**
	 * <p>Returns the format of the peak point for this PCM descriptor.
	 * This property is optional.</p>
	 * 
	 * @return Format of the peak point for this PCM descriptor.
	 * @throws PropertyNotPresentException The optional peak envelope format property is not
	 * present in this PCM descriptor.
	 */
	public @UInt32 int getPeakEnvelopeFormat()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the number of peak points per peak value for this PCM
	 * descriptor. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param pointCount Number of peak points per peak value for this PCM
	 * descriptor.
	 * 
	 * @throws IllegalArgumentException The given number of peak points per peak
	 * value is negative.
	 */
	public void setPointsPerPeakValue(
			@UInt32 Integer pointCount)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of peak points per peak value for this PCM
	 * descriptor. This value is optional.</p>
	 * 
	 * @return Number of peak points per peak value for this PCM descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional points per peak value property is not
	 * present in this PCM descriptor.
	 */
	public @UInt32 int getPointsPerPeakValue()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the number of audio samples used to generate each peak 
	 * frame for this PCM descriptor. Set this optional property to <code>null</code>
	 * to omit it.</p>
	 * 
	 * @param blockSize Number of audio samples used to generate each peak 
	 * frame for this PCM descriptor.
	 * 
	 * @throws IllegalArgumentException The given peak envelope block size is negative.
	 */
	public void setPeakEnvelopeBlockSize(
			@UInt32 Integer blockSize)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of audio samples used to generate each peak 
	 * frame for this PCM descriptor. This property is optional.</p>
	 * 
	 * @return Number of audio samples used to generate each peak 
	 * frame for this PCM descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional peak envelope block size property is not
	 * present in this PCM descriptor.
	 */
	public @UInt32 int getPeakEnvelopeBlockSize()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the number of peak channels for this PCM descriptor. Set this optional
	 * property of <code>null</code> to omit it.</p>
	 * 
	 * @param channelCount Number of peak channels for this PCM descriptor.
	 * 
	 * @throws IllegalArgumentException Cannot set the peak channels property
	 * of this PCM descriptor to a negative value.
	 */
	public void setPeakChannelCount(
			@UInt32 Integer channelCount)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of peak channels for this PCM descriptor. This
	 * property is optional.</p>
	 * 
	 * @return Number of peak channels for this PCM descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional peak channels property is not
	 * present in this PCM descriptor.
	 */
	public @UInt32 int getPeakChannelCount()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the number of peak frames for this PCM descriptor. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 * 
	 * @param frameCount Number of peak frames for this PCM descriptor.
	 * 
	 * @throws IllegalArgumentException The given peak frames count is negative.
	 */
	public void setPeakFrameCount(
			@UInt32 Integer frameCount)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of peak frames for this PCM descriptor. This
	 * property is optional.</p>
	 * 
	 * @return Number of peak frames for this PCM descriptor.
	 * 
	 * @throws PropertyNotPresentException This optional peak of peaks property is not
	 * present in this PCM descriptor.
	 */
	public @UInt32 int getPeakFrameCount()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the offset to the first audio sample whose absolute
	 * value is the maximum value of the entire audio file described by
	 * this PCM descriptor. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param position Peak of peaks position in the described audio essence.
	 */
	public void setPeakOfPeaksPosition(
			@PositionType Long position);

	/**
	 * <p>Returns the offset to the first audio sample whose absolute
	 * value is the maximum value of the entire audio file represented by
	 * this PCM descriptor. This property is optional.</p>
	 * 
	 * @return Peak of peaks position in the described audio essence.

	 * @throws PropertyNotPresentException The optional peak of peaks position property 
	 * is not present in this PCM descriptor.
	 */
	public @PositionType long getPeakOfPeaksPosition()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the {@linkplain tv.amwa.maj.record.TimeStamp time stamp} for the date and time of
	 * the creation of the peak data for the PCM descriptor. Set this optional property to
	 * <code>null</code> to omit it.</p>
	 * 
	 * @param timeStamp Time stamp of the creation of the peak data for the 
	 * PCM descriptor.
	 * 
	 * @see tv.amwa.maj.industry.Forge#makeTimeStamp(java.util.Calendar)
	 * @see tv.amwa.maj.industry.Forge#parseTimeStamp(String)
	 * @see #areAllPeakEnvelopePropertiesPresent()
	 */
	public void setPeakEnvelopeTimestamp(
			TimeStamp timeStamp);

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.record.TimeStamp time stamp} for the date and time
	 * of the creation of this peak data for this PCM descriptor. This property is optional.</p>
	 * 
	 * @return Time stamp of the creation of the peak data for this PCM descriptor.
	 * 
	 * @throws PropertyNotPresentException This optional property is not
	 * present in this PCM descriptor.
	 * 
	 * @see #areAllPeakEnvelopePropertiesPresent()
	 * @see tv.amwa.maj.record.TimeStamp#getTimeStamp()
	 */
	public TimeStamp getPeakEnvelopeTimestamp()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the stream containing the peak envelope data for this PCM descriptor.
	 * This is an optional property.</p>
	 * 
	 * @return Stream containing the peak envelope data.
	 * 
	 * @throws PropertyNotPresentException The optional peak envelope data 
	 * property is not present for this WAVE PCM descriptor.
	 * 
	 * @see #getPeakEnvelopeDataPosition()
	 * @see #readPeakEnvelopeData(int)
	 * @see #writePeakEnvelopeData(ByteBuffer)
	 * @see #setPeakEnvelopeDataPosition(long)
	 */
	public Stream getPeakEnvelopeData()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the offset from the beginning of the peak envelope data
	 * for this PCM descriptor. This property is optional and is used to define
	 * the point at which peak envelope data is {@linkplain #readPeakEnvelopeData(int) 
	 * read} or {@linkplain #writePeakEnvelopeData(ByteBuffer) written}.</p>
	 * 
	 * @param position Offset from the beginning of the peak envelope data
	 * for this PCM descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional peak envelope data 
	 * property is not present for this WAVE PCM descriptor.
	 * @throws IOException Error occurred when trying to set the position for the 
	 * underlying stream.
	 * @throws IllegalArgumentException The given position is outside the 
	 * acceptable range for the current stream.
	 * 
	 * @see #getPeakEnvelopeData()
	 * @see #getPeakEnvelopeDataPosition()
	 * @see #readPeakEnvelopeData(int)
	 * @see #writePeakEnvelopeData(ByteBuffer)
	 */
	public void setPeakEnvelopeDataPosition(
			@PositionType long position) 
		throws PropertyNotPresentException,
			IllegalArgumentException, 
			IOException;

	/**
	 * <p>Returns the offset from the beginning of the peak envelope data
	 * for this PCM descriptor. This transient property is used to define
	 * the point at which peak envelope data is {@link #readPeakEnvelopeData(int) 
	 * read} or {@link #writePeakEnvelopeData(ByteBuffer) written}.</p></p>
	 * 
	 * @return Offset from the beginning of the peak envelope data
	 * for this PCM descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional peak envelope data 
	 * property is not present for this WAVE PCM descriptor.
	 * @throws IOException Error occurred when trying to get the position from
	 * the underlying stream.
	 * 
	 * @see #getPeakEnvelopeData()
	 * @see #setPeakEnvelopeDataPosition(long)
	 * @see #readPeakEnvelopeData(int)
	 * @see #writePeakEnvelopeData(ByteBuffer)
	 */
	public @PositionType long getPeakEnvelopeDataPosition() 
		throws PropertyNotPresentException,
			IOException;

	/**
	 * <p>Returns the size of the peak envelope data of this PCM
	 * descriptor. This peak envelope data property is optional. The value returned
	 * can be useful when working out how many bytes to 
	 * {@link #readPeakEnvelopeData(int) read} from the stream of peak 
	 * envelope data.</p>
	 * 
	 * @return Size of the peak envelope data of this PCM descriptor.
	 *
	 * @throws PropertyNotPresentException The optional peak envelope data property is not
	 * present in this PCM descriptor.
	 * @throws IOException Cannot access the underlying stream to determine the size.
	 * 
	 * @see #getPeakEnvelopeData()
	 */
	public @LengthType long getPeakEnvelopeDataSize()
		throws PropertyNotPresentException, 
			IOException;
	
	/**
	 * <p>Write the given bytes to the peak envelope data stream of the
	 * PCM descriptor at the current position, as determined by calling
	 * {@link #getPeakEnvelopeDataPosition()}.</p>
	 * 
	 * @param bytes Data to write to the peak envelope data stream of this
	 * PCM descriptor.
	 * @return Number of bytes written.
	 * 
	 * @throws PropertyNotPresentException The optional peak envelope data property is not present in 
	 * this PCM descriptor and so cannot be written to.
	 * @throws IOException Error occurred when trying to write to the underlying
	 * stream.
	 * @throws EndOfDataException Cannot write beyond the end of the stream.
	 * 
	 * @see #getPeakEnvelopeData()
	 */
	public @UInt32 int writePeakEnvelopeData(
			@DataBuffer ByteBuffer bytes) 
		throws PropertyNotPresentException, 
			EndOfDataException, 
			IOException;

	/**
	 * <p>Read the given number of bytes of data from the peak envelope 
	 * data stream of this PCM descriptor at the {@link #getPeakEnvelopeDataPosition()} 
	 * current position.</p>
	 * 
	 * @param bytes Number of bytes to read.
	 * @return The given number of bytes of data from the peak envelope 
	 * data stream of this PCM descriptor.
	 * 
	 * @throws EndOfDataException Attempt to read beyond the end of the 
	 * peak envelope data.
	 * @throws PropertyNotPresentException The optional peak envelope data property is not
	 * present in this PCM descriptor.
	 * @throws IOException Error occurred when trying to read data from the underlying
	 * stream.
	 */
	public @DataBuffer ByteBuffer readPeakEnvelopeData(
			@UInt32 int bytes) 
		throws EndOfDataException,
			PropertyNotPresentException, 
			IOException;
	
	/**
	 * <p>Set the peak envelope data stream of this PCM descriptor. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 * 
	 * @param peakEnvelopeData Peak envelope data stream of this PCM descriptor.
	 * 
	 * @see #readPeakEnvelopeData(int)
	 * @see #writePeakEnvelopeData(ByteBuffer)
	 * @see #getPeakEnvelopeDataPosition()
	 * @see #getPeakEnvelopeDataSize()
	 * @see #setPeakEnvelopeDataPosition(long)
	 */
	public void setPeakEnvelopeData(
			Stream peakEnvelopeData);

	/**
	 * <p>Create a cloned copy of this WAVE PCM sound descriptor.</p>
	 * 
	 * @return Cloned copy of this WAVE PCM sound descriptor.
	 */
	public WAVEPCMDescriptor clone();
	
}
