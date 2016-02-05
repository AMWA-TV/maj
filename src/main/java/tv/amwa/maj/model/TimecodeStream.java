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
 * $Log: TimecodeStream.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/11/08 16:01:53  vizigoth
 * Version with IO exceptions on stream manipulation methods.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/08 11:27:26  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2008/01/14 21:08:26  vizigoth
 * Minor update due to refactoring of element names in TCSource enumeration.
 *
 * Revision 1.1  2007/11/13 22:08:16  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.io.IOException;
import java.nio.ByteBuffer;

import tv.amwa.maj.enumeration.TCSource;
import tv.amwa.maj.exception.PositionOutOfRangeException;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.TimecodeValue;


/**
 * <p>Specifies a stream of timecode data.</p>
 * 
 * <p>In contrast to a timecode stream, a {@linkplain TimecodeSegment timecode} specifies 
 * timecode by specifying the single starting timecode value; other timecode values 
 * are calculated from the starting timecode and the time offset.</p>
 * 
 *
 * 
 * @see Segment#segmentOffsetToTC(long)
 * @see Segment#segmentTCToOffset(TimecodeValue, Rational)
 */

public abstract interface TimecodeStream
	extends Segment {

	
	/** 
	 * <p>Returns the {@linkplain tv.amwa.maj.record.TimecodeValue timecode value} for a 
	 * given frame of this timecode stream. The frame index must be within the 
	 * length of the stream.</p>
	 * 
	 * @param position Frame to find the timecode value for from the timecode
	 * stream.
	 * @return Timecode value at the given frame from the timecode stream.
	 * 
	 * @throws PositionOutOfRangeException Frame index is outside the length
	 * of this timecode stream.
	 * @throws IOException Error occurred when trying to access data from the 
	 * underlying stream.
	 */
	public TimecodeValue getPositionTimecode(
			@PositionType long position) 
		throws PositionOutOfRangeException, 
			IOException;

	/**
	 * <p>Sets the {@linkplain tv.amwa.maj.record.TimecodeValue timecode value} for a 
	 * given frame of this timecode stream. The frame index must be within the 
	 * length of the stream.</p>
	 * 
	 * @param position Zero-based offset to set the timecode value at.
	 * @param timecode Timecode value to set at the given frame for the timecode
	 * stream.
	 * 
	 * @throws NullPointerException The given timecode value is <code>null</code>.
	 * @throws PositionOutOfRangeException Frame index is outside the length
	 * of the stream.
	 * @throws IOException Error occurred when trying to write data to the underlying
	 * stream.
	 */
	public void setPositionTimecode(
			@PositionType long position,
			TimecodeValue timecode) 
		throws NullPointerException,
			PositionOutOfRangeException, 
			IOException;

	/**
	 * <p>Returns the length of user data for one frame of this timecode
	 * stream.</p>
	 * 
	 * @return Length of user data for one frame of this timecode
	 * stream.
	 * 
	 * @see #getUserDataAtPosition(long)
	 */
	public @Int32 int getUserDataLength();

	/**
	 * <p>Returns the user data (user-bits) for a particular frame of
	 * this timecode stream.</p>
	 * 
	 * @param position Zero-based index to the frame to get the user bits
	 * from.
	 * @return User-bits from the timecode data for this given frame.
	 * 
	 * @throws PositionOutOfRangeException The given user data is outside the
	 * acceptable range for this timecode stream.
	 * @throws IOException Error occurred when trying to read from the underlying
	 * stream.
	 * @throws PositionOutOfRangeException The given user data position is out
	 * of range for the timecode stream.
	 * 
	 * @see #getUserDataLength()
	 */
	public @DataBuffer ByteBuffer getUserDataAtPosition(
			@PositionType long position)
		throws PositionOutOfRangeException,
			IOException;
	
	/**
	 * <p>Sets the user data (userbits) for a particular frame of the
	 * timecode stream.</p>
	 * 
	 * @param position Zero-based offset to the userbits to set.
	 * @param buffer User data for the specified frame.
	 * 
	 * @throws NullPointerException The given user data buffer is <code>null</code>.
	 * @throws PositionOutOfRangeException The given user data is outside the
	 * acceptable range for this timecode stream.
	 * @throws IOException Error occurred when trying to access date from the underlying 
	 * stream.
	 */
	public void setUserDataAtPosition(
			@PositionType long position,
			@DataBuffer ByteBuffer buffer) 
		throws NullPointerException,
			PositionOutOfRangeException, 
			IOException;

	/**
	 * <p>Sets the sample rate of this timecode stream, which specifies the sample rate 
	 * of the timecode data contained in the {@linkplain #getTimecodeStreamData() source property}.</p>
	 * 
	 * @param sampleRate Sample rate of this timecode stream.
	 */
	public void setTimecodeStreamSampleRate(
			Rational sampleRate);

	/**
	 * <p>Returns the sample rate of the timecode stream, which specifies the sample rate 
	 * of the timecode data contained in the {@linkplain #getTimecodeStreamData() source property}.</p>
	 * 
	 * @return Sample rate of this timecode stream.
	 */
	public Rational getTimecodeStreamSampleRate();

	/**
	 * <p>Sets the kind of timecode of this timecode stream, which is one of 
	 * {@linkplain tv.amwa.maj.enumeration.TCSource#TimecodeLTC LTC} or 
	 * {@linkplain tv.amwa.maj.enumeration.TCSource#TimecodeVITC VITC}.</p>
	 * 
	 * @param sourceType Kind of timecode of this timecode stream.
	 */
	public void setTimecodeSource(
			TCSource sourceType);

	/**
	 * <p>Returns the kind of timecode of this timecode stream, which is one of 
	 * {@linkplain tv.amwa.maj.enumeration.TCSource#TimecodeLTC LTC} or 
	 * {@linkplain tv.amwa.maj.enumeration.TCSource#TimecodeVITC VITC}.</p>
	 * 
	 * @return Kind of timecode of this timecode stream.
	 */
	public TCSource getTimecodeSource();

	/**
	 * <p>Returns the size of one sample of the timecode data of this
	 * timecode stream, measured in bytes.</p>
	 * 
	 * @return Size of one sample of the timecode data of this timecode
	 * stream.
	 */
	public @UInt32 int getSampleSize();

	/**
	 * <p>Returns the data buffer containing the stream of timecode data of this
	 * timecode stream.</p>
	 * 
	 * @return Stream of timecode data stored in this timecode stream.
	 * 
	 * @see #getTimecodeStreamSampleRate()
	 */
	public @DataBuffer Stream getTimecodeStreamData();

	/**
	 * <p>Returns the length of the timecode stream buffer contained
	 * within the timecode stream object. This is the size of the buffer 
	 * and not the number of timecode entries in the stream.</p>
	 * 
	 * @return Length of the timecode stream buffer contained
	 * within the timecode stream object.
	 * @throws IOException Cannot access the underlying stream to determine the length.
	 * 
	 * @see #getTimecodeStreamData()
	 */
	public @UInt32 long getSourceBufferLength() 
		throws IOException;

	/**
	 * <p>Sets the data buffer containing the stream of timecode data of this
	 * timecode stream.</p>
	 * 
	 * @param timecodeStreamData Stream of timecode data to store in this timecode stream.
	 * 
	 * @throws NullPointerException The given timecode stream source property
	 * is <code>null</code>.
	 * 
	 * @see #setTimecodeStreamSampleRate(Rational)
	 */
	public void setTimecodeStreamData(
			@DataBuffer Stream timecodeStreamData) 
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this timecode stream.</p>
	 *
	 * @return Cloned copy of this timecode stream.
	 */
	public TimecodeStream clone();
}
