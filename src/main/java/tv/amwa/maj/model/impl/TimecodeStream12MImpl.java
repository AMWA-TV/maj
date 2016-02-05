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
 * $Log: TimecodeStream12MImpl.java,v $
 * Revision 1.2  2011/01/24 14:01:40  vizigoth
 * Completed annotation and definition auto test generation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/07/14 13:34:35  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
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
 * Revision 1.1  2007/11/13 22:09:43  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import tv.amwa.maj.enumeration.TCSource;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.model.TimecodeStream12M;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.TimecodeValue;
import tv.amwa.maj.record.impl.TimecodeValueImpl;

import java.io.Serializable;
import java.nio.ByteBuffer;

/** 
 * <p>Implements a representation of a stream of timecode data in the SMPTE&nbsp;12M format.</p>
 * 
 * <p>THE COMMENTS FOR THIS CLASS ARE INCOMPLETE.</p>
 * 
 * <p>The byte stream is laid out as a sequence of bits numbered 0-79 with bit 0 being
 * the first bit. The data is stored as a 10-byte array, and bit 0 is the MSB of the 
 * first byte.</p>
 * 
 * <p>The bit definitions are as follows:</p>
 * 
 * <table>
 * <tr><td align="right"><strong>bits</strong></td><td><strong>description</strong></td><td><strong>extraction expression</strong></td></tr>
 * <tr><td align="right">0-3</td><td>units of frames</td><td><code>(foo[bits0_7] >> 4) & 0x0F</code></td></tr>
 * <tr><td align="right">4-7</td><td>first binary group</td><td><code>foo[bits0_7] & 0x0F</code></td></tr>
 * <tr><td align="right">8-9</td><td>tens of frames</td><td><code>(foo[bits8_15] >> 6) & 0x03</code></td></tr>
 * <tr><td align="right">10</td><td>drop frame</td><td><code>(foo[bits8_15] >> 5) & 0x01</code></td></tr>
 * <tr><td align="right">11</td><td>color frame</td><td><code>(foo[bits8_15] >> 4) & 0x01</code></td></tr>
 * <tr><td align="right">12-15</td><td>second binary group</td><td><code>foo[bits8_15] & 0x0F</code></td></tr>
 * <tr><td align="right">16-19</td><td>units of seconds</td><td><code>(foo[bits16_23] >> 4) & 0x0F</code></td></tr>
 * <tr><td align="right">20-23</td><td>third binary group</td><td><code>foo[bits16_23] & 0x0F</code></td></tr>
 * <tr><td align="right">24-26</td><td>tens of seconds</td><td><code>(foo[bits24_31] >> 5) & 0x07</code></td></tr>
 * <tr><td align="right">27</td><td>bi-phase mark phase correction bit</td><td><code>(foo[bits24_31] >> 7) & 0x01</code></td></tr>
 * <tr><td align="right">28-31</td><td>fourth binary group</td><td><code>foo[bits24_31] & 0x0F</code></td></tr>
 * <tr><td align="right">32-35</td><td>units of minutes</td><td><code>(foo[bits32_39] >> 4) & 0x0F</code></td></tr>
 * <tr><td align="right">36-39</td><td>fifth binary group</td><td><code>foo[bits32_39] & 0x0F</code></td></tr>
 * <tr><td align="right">40-42</td><td>tens of minutes</td><td><code>(foo[bits40_47] >> 5) & 0x07</code></td></tr>
 * <tr><td align="right">43</td><td>binary group flag bit</td><td><code>(foo[bits40_47] >> 4) & 0x01</code></td></tr>
 * <tr><td align="right">44-47</td><td>sixth binary group</td><td><code>foo[bits40_47] & 0x0F</code></td></tr>
 * <tr><td align="right">48-51</td><td>units of hours</td><td><code>(foo[bits48_55] >> 4) & 0x0F</code></td></tr>
 * <tr><td align="right">52-55</td><td>seventh binary group</td><td><code>foo[bits48_55] & 0x0F</code></td></tr>
 * <tr><td align="right">56-57</td><td>tens of hours</td><td><code>(foo[bits56_63] >> 6) & 0x03</code></td></tr>
 * <tr><td align="right">58</td><td>unassigned address bit</td><td><code>(foo[bits56_63] >> 5) & 0x01</code></td></tr>
 * <tr><td align="right">59</td><td>binary group flag bit</td><td><code>(foo[bits56_63] >> 4) & 0x01</code></td></tr>
 * <tr><td align="right">60-63</td><td>eighth binary group</td><td><code>foo[bits56_63] & 0x0F</code></td></tr>
 * <tr><td align="right">64-71</td><td>synch word (fixed <code>0x3F</code>)</td></tr>
 * <tr><td align="right">72-79</td><td>synch word (fixed <code>0xFD</code>)</td></tr>
 * </table>
 *
 * <p>Timecode streams are useful to store user bits that were specified in the timecode on the videotape.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1600,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TimecodeStream12M",
		  description = "The TimecodeStream12M class specifies a stream of timecode data in the SMPTE 12M format.",
		  symbol = "TimecodeStream12M")
public class TimecodeStream12MImpl
	extends TimecodeStreamImpl
	implements 
		TimecodeStream12M,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 3370849531070799041L;

	private static final int streamSampleSize = 10;
	private static final int userDataSize = 4;
	
	private final static  int bits0_7 = 0;
	private final static  int bits8_15 = 1;
	private final static  int bits16_23 = 2;
	private final static  int bits24_31 = 3;
	private final static  int bits32_39 = 4;
	private final static  int bits40_47 = 5;
	private final static  int bits48_55 = 6;
	private final static  int bits56_63 = 7;
	private final static  int bits64_71 = 8;
	private final static  int bits72_79 = 9;
	
	private Boolean includeSync = null;

	public TimecodeStream12MImpl() { }

	/**
	 * <p>Creates and initializes a new SMPTE 12M-compliant timecode stream, which specifies a 
	 * stream of timecode data in the SMPTE 12M format.</p>
	 *
	 * @param sampleRate Sample rate of the timecode data contained in the source property.
	 * @param source The timecode data.
	 * @param sourceType Kind of timecode data.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and all
	 * are required.
	 */
	public TimecodeStream12MImpl(
			Rational sampleRate,
			@DataBuffer Stream source,
			TCSource sourceType)
		throws NullPointerException,
			IllegalArgumentException {
		
		setComponentDataDefinition(DataDefinitionImpl.forIdentification(DataDefinitionImpl.Timecode));
		
		if (sampleRate == null)
			throw new NullPointerException("Cannot set the sample rate for the 12M-compliant timecode stream using a null value.");
		if (source == null)
			throw new NullPointerException("Cannot set the source for this 12M-compliant timecode stream using a null value.");
		if (sourceType == null)
			throw new NullPointerException("Cannot set the source type for this 12M-compliant timecode stream using a null value.");
		
		setTimecodeStreamSampleRate(sampleRate);
		setTimecodeStreamData(source);
		setTimecodeSource(sourceType);
	}

    @MediaProperty(uuid1 = 0x04040101, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "IncludeSync",
			typeName = "Boolean",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1701,
			symbol = "IncludeSync")
	public boolean getIncludeSync() {

		if (includeSync == false)
			return INCLUDESYNC_DEFAULT;
		else
			return includeSync;
	}

	@MediaPropertySetter("IncludeSync")
	public void setIncludeSync(
			Boolean includeSync) {

		this.includeSync = includeSync;
	}

	public final static boolean initializeIncludeSync() {
		
		return false;
	}
	
	@Override
	public int getSampleSize() {

		return streamSampleSize;
	}

	@Override
	public int getUserDataLength() {

		return userDataSize;
	}

	@Override
	public ByteBuffer packTimecode(
			TimecodeValue timecode,
			ByteBuffer packedBuffer) 
		throws NullPointerException,
			IllegalArgumentException {

		if (timecode == null)
			throw new NullPointerException("Cannot pack a null timecode value into a 12M-compliant timecode sample.");
		if (packedBuffer == null)
			throw new NullPointerException("Cannot pack a timecode value into a null timecode sample.");
		
		byte[] packedBytes = packedBuffer.array();
		
		if (packedBytes.length != streamSampleSize)
			throw new IllegalArgumentException("The given timecode sample buffer is the wrong length (" + streamSampleSize + ") for a 12M-compliant timecode stream.");
		
		byte[] withAddedBits = packedBytes.clone();
		TimecodeValueImpl.TimeComponents timeComponents = 
			(new TimecodeValueImpl(timecode.getDropFrame(), 
					timecode.getStartTimecode(), timecode.getFramesPerSecond())).convertToActualTimeValues();
		
		withAddedBits[bits0_7] = (byte) ((packedBytes[bits0_7] & 0xF0)	| 
				(((timeComponents.frameInSecond % 10) << 4) & 0xF0));
		withAddedBits[bits8_15] = (byte) ((packedBytes[bits8_15] & 0x3F) | 
				(((timeComponents.frameInSecond / 10) << 6) & 0xC0));
		withAddedBits[bits16_23] = (byte) ((packedBytes[bits16_23] & 0x0F) |
				(((timeComponents.seconds % 10) << 4) & 0xF0));
		withAddedBits[bits24_31] = (byte) ((packedBytes[bits24_31] & 0x1F) | 
				(((timeComponents.seconds / 10) << 5) & 0xE0));
		withAddedBits[bits32_39] = (byte) ((packedBytes[bits32_39] & 0x0F) | 
				(((timeComponents.minutes % 10) << 4) & 0xF0));
		withAddedBits[bits40_47] = (byte) ((packedBytes[bits40_47] & 0x1F) | 
				(((timeComponents.minutes / 10) << 5) & 0xE0));
		withAddedBits[bits48_55] = (byte) ((packedBytes[bits48_55] & 0x0F) | 
				(((timeComponents.hours % 10) << 4) & 0xF0));
		withAddedBits[bits56_63] = (byte) ((packedBytes[bits56_63] & 0x3F) | 
				(((timeComponents.hours / 10) << 6) & 0xC0));
		withAddedBits[bits8_15] = (byte) ((packedBytes[bits8_15] & 0xDF) | 
				(timecode.getDropFrame() ? 0x20 : 0x00));

		withAddedBits[bits64_71] = (byte) 0x3F; // synch "Word" 1
		withAddedBits[bits72_79] = (byte) 0xFD; // synch "Word" 2
		
		return ByteBuffer.wrap(withAddedBits);
	}

	@Override
	public ByteBuffer packUserBits(
			ByteBuffer userBitsBuffer,
			ByteBuffer packedBuffer) 
		throws NullPointerException,
			IllegalArgumentException {

		if (userBitsBuffer == null)
			throw new NullPointerException("Cannot pack user bits into a timecode sample using a null userbits value.");
		if (packedBuffer == null)
			throw new NullPointerException("Cannot pack user bits into a null timecode sample.");
		
		byte[] userBits = userBitsBuffer.array();
		byte[] packedBytes = packedBuffer.array();
		
		if (userBits.length != userDataSize)
			throw new IllegalArgumentException("The given array of user bits is not of the required length (" + userDataSize + ") for this 12M-compliant timecode stream.");
		if (packedBytes.length != streamSampleSize)
			throw new IllegalArgumentException("The given array of packed bytes is not of the required length (" + streamSampleSize + ") for this 12M-compliant timecode stream.");
		
		byte[] withAddedBits = packedBytes.clone();
		
		withAddedBits[bits0_7]   = (byte) ((packedBytes[bits0_7] & 0xF0)	| ((userBits[0] >>> 4) & 0x0F));
		withAddedBits[bits8_15]  = (byte) ((packedBytes[bits8_15] & 0xF0)  | (userBits[0] & 0x0F));
		withAddedBits[bits16_23] = (byte) ((packedBytes[bits16_23] & 0xF0) | ((userBits[1] >>> 4) & 0x0F));
		withAddedBits[bits24_31] = (byte) ((packedBytes[bits24_31] & 0xF0) | (userBits[1] & 0x0F));
		withAddedBits[bits32_39] = (byte) ((packedBytes[bits32_39] & 0xF0) | ((userBits[2] >>> 4) & 0x0F));
		withAddedBits[bits40_47] = (byte) ((packedBytes[bits40_47] & 0xF0) | (userBits[2] & 0x0F));
		withAddedBits[bits48_55] = (byte) ((packedBytes[bits48_55] & 0xF0) | ((userBits[3] >>> 4) & 0x0F));
		withAddedBits[bits56_63] = (byte) ((packedBytes[bits56_63] & 0xF0) | (userBits[3] & 0x0F));
		
		withAddedBits[bits64_71] = (byte) 0x3F; // synch "Word" 1
		withAddedBits[bits72_79] = (byte) 0xFD; // synch "Word" 2
		
		return ByteBuffer.wrap(withAddedBits);
	}

	@Override
	public TimecodeValue unpackTimecode(
			ByteBuffer timecodeBuffer,
			short fps) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (timecodeBuffer == null)
			throw new NullPointerException("The given buffer cannot be coverted to a timecode value because it is null.");
		
		byte[] buffer = timecodeBuffer.array();
		if ((buffer.length < streamSampleSize))
			throw new IllegalArgumentException("The given buffer cannot be converted to a timecode value because is is too short.");
		if (fps <= 0)
			throw new IllegalArgumentException("The given timecode value is not a postive number.");
		
		short hours = (short) ((((buffer[bits56_63] >>> 6) & 0x03) * 10) + ((buffer[bits48_55] >>> 4) & 0x0F));
		short minutes = (short) ((((buffer[bits40_47] >>> 5) & 0x07) * 10) + ((buffer[bits32_39] >>> 4) & 0x0F));
		short seconds = (short) ((((buffer[bits24_31] >>> 5) & 0x07) * 10) + ((buffer[bits16_23] >>> 4) & 0x0F));
		short frames = (short) ((((buffer[bits8_15] >>> 6) & 0x03) * 10) + ((buffer[bits0_7] >>> 4) & 0x0F));
		boolean drop = (((buffer[bits8_15] >>> 5) & 0x01) == 1);
		
		return new TimecodeValueImpl(fps, hours, minutes, seconds, frames, drop);
	}

	@Override
	public ByteBuffer unpackUserBits(
			ByteBuffer buffer) 
		throws NullPointerException,
			IllegalArgumentException {

		if (buffer == null)
			throw new NullPointerException("Cannot unpack user bits from a null buffer.");
		
		byte[] localBuffer = buffer.array();
		if (localBuffer.length < streamSampleSize)
			throw new IllegalArgumentException("The given buffer is too short to allow user bits to be unpacked.");
		
		byte[] userBits = new byte[] {
			(byte) (((localBuffer[bits0_7] & 0x0F) << 4) | (localBuffer[bits8_15] & 0x0F)),
			(byte) (((localBuffer[bits16_23] & 0x0F) << 4) | (localBuffer[bits24_31] & 0x0F)),
			(byte) (((localBuffer[bits32_39] & 0x0F) << 4) | (localBuffer[bits40_47] & 0x0F)),
			(byte) (((localBuffer[bits48_55] & 0x0F) << 4) | (localBuffer[bits56_63] & 0x0F))
		};
		
		return ByteBuffer.wrap(userBits);
	}

	public TimecodeStream12M clone() {
		
		return (TimecodeStream12M) super.clone();
	}
	
}
