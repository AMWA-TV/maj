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
 * $Log: TimecodeStreamImpl.java,v $
 * Revision 1.4  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
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
 * Revision 1.3  2010/11/08 15:48:54  vizigoth
 * Version with IO exceptions on stream manipulation methods.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
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
 * Revision 1.2  2008/01/15 12:34:56  vizigoth
 * Updated due to refactoring of element names in TCSource enumeration.
 *
 * Revision 1.1  2007/11/13 22:09:10  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

import tv.amwa.maj.enumeration.TCSource;
import tv.amwa.maj.exception.BadSampleOffsetException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.PositionOutOfRangeException;
import tv.amwa.maj.exception.TimecodeNotFoundException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MemoryResidentStream;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.model.TimecodeStream;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.TimecodeValue;
import tv.amwa.maj.record.impl.RationalImpl;
import tv.amwa.maj.record.impl.TimecodeValueImpl;


/** 
 * <p>Implements a representation of a stream of timecode data.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1500,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TimecodeStream",
		  description = "The TimecodeStream class specifies a stream of timecode data.",
		  symbol = "TimecodeStream")
		  // TODO consider whether this class can be made concrete and set abstract in metadata?
public abstract class TimecodeStreamImpl
	extends 
		SegmentImpl
	implements 
		TimecodeStream,
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3448173192584470825L;
	
	private Rational timecodeStreamSampleRate;
	private Stream timecodeStreamData;
	private TCSource timecodeSource;
	
	@MediaProperty(uuid1 = 0x04040101, uuid2 = (short) 0x0201, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TimecodeStreamSampleRate",
			aliases = { "SampleRate" },
			typeName = "Rational",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1601,
			symbol = "TimecodeStreamSampleRate")
	public Rational getTimecodeStreamSampleRate() {

		return timecodeStreamSampleRate.clone();
	}

	@MediaPropertySetter("TimecodeStreamSampleRate")
	public void setTimecodeStreamSampleRate(
			tv.amwa.maj.record.Rational timecodeStreamSampleRate) 
		throws NullPointerException {

		if (timecodeStreamSampleRate == null)
			throw new NullPointerException("Cannot set the sample rate of this timecode stream using a null value.");
		
		this.timecodeStreamSampleRate = timecodeStreamSampleRate.clone();
	}

	public final static Rational initializeTimecodeStreamSampleRate() {
		
		return new RationalImpl(0, 1);
	}
	
	public abstract int getSampleSize();

	@MediaProperty(uuid1 = 0x04070300, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TimecodeStreamData",
			aliases = { "Source" },
			typeName = "Stream",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1602,
			symbol = "TimecodeStreamData")
	public Stream getTimecodeStreamData() {

		return timecodeStreamData.clone();
	}

	@MediaPropertySetter("TimecodeStreamData")
	public void setTimecodeStreamData(
			Stream timecodeStreamData)
		throws NullPointerException {

		if (timecodeStreamData == null)
			throw new NullPointerException("Cannot set the value of the source property of this timecode stream using a null value.");
		
		this.timecodeStreamData = timecodeStreamData.clone();
	}

	public final static Stream initializeTimecodeStreamData() {
		
		return new MemoryResidentStream(0);
	}
	
	public long getSourceBufferLength() 
		throws IOException {

		return timecodeStreamData.getLength();
	}

	@MediaProperty(uuid1 = 0x04040201, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "TimecodeSource",
			aliases = { "SourceType" },
			typeName = "TCSource",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1603,
			symbol = "TimecodeSource")
	public TCSource getTimecodeSource() {

		return timecodeSource;
	}

	@MediaPropertySetter("TimecodeSource")
	public void setTimecodeSource(
			TCSource timecodeSource) 
		throws NullPointerException {

		if (timecodeSource == null)
			throw new NullPointerException("Cannot set the timecode source type of this timecode stream to a null value.");
		
		this.timecodeSource = timecodeSource;
	}

	public final static TCSource initializeTimecodeSource() {
		
		return TCSource.TimecodeVITC;
	}
	
	public ByteBuffer getUserDataAtPosition(
			long position) 
		throws PositionOutOfRangeException, 
			IOException  {

		try {
			int sampleSize = getSampleSize();
			setPosition(sampleSize * position);
			ByteBuffer packedBuffer = read(sampleSize);
			ByteBuffer buffer = unpackUserBits(packedBuffer);
			
			return buffer;
		}
		catch (IllegalArgumentException iae) {
			throw new PositionOutOfRangeException("The given timecode position is outside the range of the timecode stream.");
		}
		catch (EndOfDataException eode) {
			throw new PositionOutOfRangeException("End of data reached when trying to read the requested sampled.");
		}
	}

	public void setUserDataAtPosition(
			long position,
			ByteBuffer buffer)
		throws NullPointerException,
			PositionOutOfRangeException,
			IOException {

		try {
			int sampleSize = getSampleSize();
			setPosition(position * sampleSize);
			ByteBuffer packedBuffer = read(sampleSize);
			setPosition(position * sampleSize);
			packedBuffer = packUserBits(buffer, packedBuffer);
			write(packedBuffer);
		}
		catch (IllegalArgumentException iae) {
			throw new PositionOutOfRangeException("The given timecode position is outside the range of the timecode stream.");
		}
		catch (EndOfDataException eode) {
			throw new PositionOutOfRangeException("End of data reached when trying to read the requested sampled.");
		}
	}

	public abstract int getUserDataLength();

	public TimecodeValue getPositionTimecode(
			long position)
		throws PositionOutOfRangeException,
			IOException {
	
		Rational rate = getTimecodeStreamSampleRate();
		double floatRate = ((double) rate.getNumerator()) / ((double) rate.getDenominator());
		
		short fps;
		if ((floatRate >= 29.96) || (floatRate <= 30.00))
			fps = (short) 30;
		else
			fps = (short) floatRate;
		
		TimecodeValue positionTimecode = new TimecodeValueImpl();
		positionTimecode.setFramesPerSecond(fps);
		
		try {
			int sampleSize = getSampleSize();
			setPosition(position * sampleSize);
			ByteBuffer buffer = read(sampleSize);
			positionTimecode = unpackTimecode(buffer, fps);
			
			return positionTimecode;
		}
		catch (IllegalArgumentException iae) {
			throw new PositionOutOfRangeException("The given timecode position is outside the range of the timecode stream.");
		}
		catch (EndOfDataException eode) {
			throw new PositionOutOfRangeException("End of data reached when trying to read the requested sampled.");
		}
		
	}

	public void setPositionTimecode(
			long position,
			TimecodeValue timecode)
		throws NullPointerException,
			PositionOutOfRangeException,
			IOException {

		if (timecode == null)
			throw new NullPointerException("Cannot write bytes to the timecode stream using a null timecode value.");
		
		try {
			int sampleSize = getSampleSize();
			setPosition(position * sampleSize);
			ByteBuffer packedBuffer = read(sampleSize);
			setPosition(position * sampleSize);
			packedBuffer = packTimecode(timecode, packedBuffer);
			write(packedBuffer);
		}
		catch (IllegalArgumentException iae) {
			throw new PositionOutOfRangeException("The given timecode position is outside the range of the timecode stream.");
		}
		catch (EndOfDataException eode) {
			throw new PositionOutOfRangeException("End of data reached when trying to read the requested sampled.");
		}
		
	}

	public TimecodeValue segmentOffsetToTC(
			long offset) 
		throws IllegalArgumentException {
		
		try {
			return getPositionTimecode(offset);
		}
		catch (PositionOutOfRangeException poore) {
			throw new IllegalArgumentException(poore.getMessage());
		}
		catch (IOException ioe) {
			throw new IllegalArgumentException("IO error when tryign to access the underlying stream.");
		}
	}

	public long segmentTCToOffset(
			TimecodeValue timecode,
			Rational editRate) // TODO check edit rate parameter ... it gets overwritten in the C code!
		throws NullPointerException,
			TimecodeNotFoundException,
			BadSampleOffsetException {
		
		if (timecode == null)
			throw new NullPointerException("Cannot calculate segment timecode offset for this timecode stream using a null timecode value.");
		if (editRate == null)
			throw new NullPointerException("Cannot calculate segment timecode offset for this timecode stream using a null edit rate value.");
		
		long increment = timecode.getFramesPerSecond() * 10l;
		
		editRate = getTimecodeStreamSampleRate();
		TimecodeValue baseTimecode = null;
		
		try {
			try {
				baseTimecode = getPositionTimecode(0l);  
			}
			catch (PositionOutOfRangeException pore) { 
				/* Position of 0 is always acceptable. */
			}
	
			long testOffset = timecode.getStartTimecode() - baseTimecode.getStartTimecode();
	
			long numSamples = timecodeStreamData.getLength() / getSampleSize();
			TimecodeValue testTimecode = new TimecodeValueImpl();
	
			// TODO check that the getPositionTimecode() method sets the startOffset, otherwise ...
			// testTimecode.setStartOffset(baseTimecode.getStartOffset()); here or after getPositionTimecode()?
			
			if ((testOffset >= 0) && (testOffset < numSamples)) {
	
				try {
					testTimecode = getPositionTimecode(testOffset);
				}
				catch (PositionOutOfRangeException poore) { 
					poore.printStackTrace();
					/* Already within known bounds - should not happen. */
				}
				
				if (testTimecode.equals(timecode)) 
					return testOffset;
			}
			else {
				/*
				 * See if times a monotonically increasing anywhere near here. See if we can
				 * compute offset from the error in reaching here. 
				 */
				testOffset += timecode.getStartTimecode() - testTimecode.getStartTimecode();
				
				if (testOffset >= 0) {
	
					try {
						testTimecode = getPositionTimecode(testOffset);
					}
					catch (PositionOutOfRangeException poore) { 
						poore.printStackTrace();
						/* Already within known bounds - should not happen. */
					}
	
					if (testTimecode.equals(timecode))
						return testOffset;
				}
			}
			
			/* Start at zero and skip forward at increments until a timecode is found within 
			 * (increment) of the goal.
			 */
			testOffset = increment;
			long scanStart = 0l;
			
			if (testOffset > numSamples)
				testOffset = 0; // It's short, so skip the scan phase
			else {
				for ( ; testOffset < numSamples ; testOffset += increment ) {
	
					try {
						testTimecode = getPositionTimecode(testOffset);
					}
					catch (PositionOutOfRangeException poore) { 
						poore.printStackTrace();
						/* Already within known bounds - should not happen. */
					}
	
					int error = (int) (timecode.getStartTimecode() - testTimecode.getStartTimecode());
					
					if ((error >= -increment) && (error <= increment)) {
						if (error < 0) 
							testOffset -= increment;
						scanStart = testOffset; // Need to get a running start
						break;
					}
					
					if (testOffset >= numSamples) { // Reached the end of the scan so set start near the end.
						testOffset -= increment; // Need to get a running start
						scanStart = testOffset;
					}
				}
			}
			
			for ( ; testOffset < numSamples ; testOffset++ ) { // Scan from start start to the end
				
				try {
					testTimecode = getPositionTimecode(testOffset);
				}
				catch (PositionOutOfRangeException poore) { 
					poore.printStackTrace();
					/* Already within known bounds - should not happen. */
				}
				
				if (testTimecode.equals(timecode)) 
					return testOffset;
			}
			
			for ( testOffset = 0 ; testOffset < scanStart ; testOffset++ ) { // Scan from 0 to scan start
				
				try {
					testTimecode = getPositionTimecode(testOffset);
				}
				catch (PositionOutOfRangeException poore) { 
					poore.printStackTrace();
					/* Already within known bounds - should not happen. */
				}
				
				if (testTimecode.equals(timecode))
					return testOffset;
			}
		}
		catch (IOException ioe) { 
			throw new TimecodeNotFoundException("Could not find a matching timecode due to a IO error: " + ioe.getMessage());
		} // If you couldn't open the stream, the timecode was not found
		
		throw new TimecodeNotFoundException("Could not find a matching timecode within this timecode stream.");
	}
	
	/**
	 * <p>Unpack a timecode value from the given buffer for the given number of frames per second.</p>
	 *
	 * @param buffer Data buffer containing bytes representing one timecode value.
	 * @param fps Frames per second of the current timecode stream.
	 * @return Deserialized timecode value from the given data buffer. 
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws IllegalArgumentException The given buffer does not contain sufficient data to unpack a timecode
	 * value or the frame per second value is not a positive value.
	 */
	public abstract TimecodeValue unpackTimecode(
			ByteBuffer buffer,
			short fps)
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Pack a given timecode value into a give byte array. Any user bits in the given packed 
	 * buffer are preserved.</p>
	 *
	 * @param timecode Timecode value to serialize.
	 * @param packedBuffer Array of bytes into which the timecode should be packed.
	 * @return Serialized version of the given timecode value.
	 * 
	 * @throws NullPointerException One or more of the arguments is null.
	 * @throws IllegalArgumentException The given array of packed bytes is the wrong length to allow
	 * the packing of a timecode value.
	 */
	public abstract ByteBuffer packTimecode(
			TimecodeValue timecode,
			ByteBuffer packedBuffer)
		throws NullPointerException,
			IllegalArgumentException;
	
	
	/**
	 * <p>Unpack user data from the given sample of the timecode stream.</p>
	 *
	 * @param buffer Buffer to use to unpack user data from.
	 * @return User data contained within the given sample.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws IllegalArgumentException The given data buffer is too short to enable user bits to be 
	 * extracted.
	 */
	public abstract ByteBuffer unpackUserBits(
			ByteBuffer buffer)
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Pack user data from the given byte array into the given packed buffer.</p>
	 *
	 * @param userBits User bits to set for a timecode value.
	 * @param packedBuffer Buffer to pack user bits into.
	 * @return Buffer with the given user bits set.
	 * 
	 * @throws NullPointerException One or more of the arguments is null.
	 * @throws IllegalArgumentException The packed buffer or array of user bits are of
	 * the wrong length.
	 */
	public abstract ByteBuffer packUserBits(
			ByteBuffer userBits,
			ByteBuffer packedBuffer)
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Returns the current position for reading or writing the timecode stream source.</p>
	 *
	 * @return Current position for reading of writing the timecode stream source.
	 * @throws IOException Error thrown when trying to get position from the underlying stream.
	 */
	long getPosition() throws IOException {
		
		return timecodeStreamData.getPosition();
	}
	
	/**
	 * <p>Sets the current position for reading of writing the timecode stream source.</p>
	 *
	 * @param offset Current position for reading of writing the timecode stream source.
	 * @throws IOException Error occurred when trying to set the position for the underlying stream.
	 * @throws IllegalArgumentException The given position offset is outside the acceptable range 
	 * for this timecode stream.
	 */
	private void setPosition(
			long offset) 
		throws IllegalArgumentException, 
			IOException {
		
		timecodeStreamData.setPosition(offset);
	}
	
	/**
	 * <p>Read the given number of bytes from the timecode stream at the current position and return
	 * these as a byte array. If the read will result in reading beyond the end of the current timecode
	 * stream, all the remaining bytes between the current position and the end of the stream are 
	 * returned.</p>
	 *
	 * @param bytes Number of bytes to read from the timecode stream.
	 * @return The given number of bytes read from the current position.
	 * 
	 * @throws IOException Error occurred when trying to read data from the underlying stream.	
	 * @throws EndOfDataException Cannot read data beyond the end of the stream.
	 */
	private ByteBuffer read(
			long bytes) 
		throws EndOfDataException, 
			IOException {
		
		return timecodeStreamData.read((int) bytes);
	}
	
	/**
	 * <p>Write the given array of bytes to the timecode stream at the current position. If the
	 * length of the given array of bytes added to the current position exceeds the length of the
	 * source buffer, only the initial bytes from the given array are written up until the end of 
	 * the buffer.</p>
	 *
	 * @param buffer Buffer to data to write to the timecode stream.
	 * @return Number of bytes actually written.
	 * @throws IOException Error occurred when trying to write data to the underlying stream.
	 * @throws EndOfDataException Cannot write data beyond the end of the stream.
	 */
	private int write(
			ByteBuffer buffer) 
		throws EndOfDataException, 
			IOException {
		
		return timecodeStreamData.write(buffer);
	}

	public TimecodeStream clone() {
		
		return (TimecodeStream) super.clone();
	}
	
	public String getTimecodeStreamSampleRateString() {
		
		return RationalImpl.toPersistentForm(timecodeStreamSampleRate);
	}
	
	public void setTimecodeStreamSampleRateString(
			String timecodeStreamSampleRate) {
		
		this.timecodeStreamSampleRate = RationalImpl.fromPersistentForm(timecodeStreamSampleRate);
	}
}
