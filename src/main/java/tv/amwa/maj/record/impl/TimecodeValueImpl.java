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
 * $Log: TimecodeValueImpl.java,v $
 * Revision 1.5  2011/11/04 09:54:16  vizigoth
 * Updates to enable writing AAF files from objects with AMP metadata.
 *
 * Revision 1.4  2011/07/27 17:21:59  vizigoth
 * Fractions of seconds now measured in 1/250ths.
 *
 * Revision 1.3  2011/01/19 11:31:15  vizigoth
 * Fixes due issues found during Forge tests, including making the default value a fps of 1 rather than 0.
 *
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:03  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/15 09:45:54  vizigoth
 * Edited documentation to release standard.
 *
 * Revision 1.1  2007/11/13 22:14:40  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.record.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.misctype.FrameOffset;
import tv.amwa.maj.record.TimeStruct;
import tv.amwa.maj.record.TimecodeValue;

/** 
 * <p>Implementation of a value that represents video tape or audio tape timecode information. The timecode
 * interface is capable of representing timecode values specified by SMPTE&nbsp;12M.</p>
 * 
 * <p>Representation of timecode values based on a frame offset from the start of
 * an item of content. This class also models the number of frames per second associated
 * with a timecode value and whether the timecode requires the calculation of drop 
 * frames.</p>
 * 
 * <p>All methods that operate on the timecode value, such as 
 * {@link #compareTo(tv.amwa.maj.record.TimecodeValue)}, {@link #toString()} and
 * {@link #convertToRealTime()} respect the number of frames-per-second and drop frame
 * values. See the method descriptions for more details.</p>
 * 
 * <p>This implementation can support high frame rate television. In this case, timecode values
 * will format in a way that is not compatible with SMPTE&nbsp;12M. For example, a timecode value of 
 * <code>00:00:21:135</code> represents the 135th frame of the frame 21&nbsp;seconds into a 300&nbsp;
 * fps item, frame&nbsp;6435.</p>
 *
 *
 * 
 * @see tv.amwa.maj.model.TimecodeSegment#getTimecode()
 * @see tv.amwa.maj.model.TimecodeSegment
 */
public final class TimecodeValueImpl 
	implements TimecodeValue, 
		Serializable, 
		XMLSerializable,
		Comparable<TimecodeValue>,
		Cloneable,
		CommonConstants {

    /**  */
	private static final long serialVersionUID = 1296780910250948724L;

	/** Indicates whether the timecode is drop (True value) or nondrop (False value). */
	private boolean dropFrame;

	/** Specifies the timecode at the beginning of a segment. */
	@FrameOffset private long startTimecode;

	/** Frames per second of the videotape or audio tape. */
	@UInt16 private short framesPerSecond;

	/**
	 * <p>Create a timecode value.</p>
	 * 
	 * @param drop Indicates whether the timecode is drop (<code>true</code> value) or nondrop 
	 * (<code>false</code> value).
	 * @param startFrame Specifies the timecode as the number of frames offset from
	 * the start of the video or audio.
	 * @param fps Frames per second of the videotape or audio tape.
	 * 
	 * @throws IllegalArgumentException The frames per second value is not a positive value. 
	 */
	public TimecodeValueImpl(
			boolean drop, 
			@FrameOffset long startFrame, 
			@UInt16 short fps) 
		throws IllegalArgumentException {
		
		if (fps <= 0)
			throw new IllegalArgumentException("The frames per second value must be a positive number.");
		
		this.dropFrame = drop;
		this.startTimecode = startFrame;
		this.framesPerSecond = fps;
	}

	/**
	 * <p>Create a default timecode value. Default values are non-drop, start frame set to&nbsp;0 and
	 * frame rate of&nbsp;1 frames per second.</p>
	 *
	 */
	public TimecodeValueImpl() {
		dropFrame = false;
		startTimecode = 0;
		framesPerSecond = 1;
	}
	
	/**
	 *
	 * <p>For a specific frames per second value, instances of this class represent the number of frames
	 * in an hour, minute of second for both drop and non-drop values.</p>
	 *
	 */
	// Tested indirectly by public methods.
	private static class FrameTable {
		
		final int dropFpMin;
		final int dropFpMin10;
		final int dropFpHour;
		final int fpMinute;
		final int fpHour;
		
		/**
		 * <p>Create a frame table for the given number of frames per second, carrying out the number 
		 * of frames calculations on construction.</p>
		 *
		 * @param fps Number of frames per second to use to calculate the values of the new frame table.
		 */
		FrameTable(short fps) {
		
			short scaledFps = (fps > 30) ? ((short) (fps / 2)) : fps;
			
			dropFpMin = (60 * scaledFps) - 2;
			dropFpMin10 = (10 * dropFpMin + 2);
			dropFpHour = 6 * dropFpMin10;

			fpMinute = 60 * scaledFps;
			fpHour = 60 * fpMinute;
		}
	}
	
	/** 
	 * <p>Create a new timecode value from the given hours, minutes, seconds and frames according to the
	 * given number of frames per second and whether the timecode value requires drop frame calculations.
	 * Internally, these values are converted to an offset value.</p>
	 * 
	 * <p>For frames per second values above 30, the value of the frame pair part of the timecode will
	 * be set to zero. To set the frame pair value (VITC Field Flag Bit), use 
	 * {@link #TimecodeValueImpl(short, short, short, short, short, short, boolean)}.</p>
	 * 
	 * <p>This method does not apply strict boundaries. Negative values and values greater than the
	 * acceptable bounds when displaying a timecode will be accepted and mapped to an appropriate offset.
	 * For example, the following three calls to this method create equivalent timecode values:</p>
	 * 
	 * <pre>
	 * time1 = new TimecodeValueImpl(25, 0, 0, 63,   0, false);
	 * time2 = new TimecodeValueImpl(25, 0, 1, 03,   0, false);
	 * time3 = new TimecodeValueImpl(25, 0, 1, 04, -25, false);
	 * </pre>
	 *
	 * @param fps Number of frames per second for this timecode value.
	 * @param hours Number of hours since the start of an item represented by this timecode.
	 * @param minutes Number of minutes in the hour of an item represented by this timecode.
	 * @param seconds Number of seconds in the minute of an item represented by this timecode.
	 * @param frames Number of frames through the second of an item represented by this timecode.
	 * @param drop Does this timecode require drop frame calculation?
	 * 
	 * @throws IllegalArgumentException The number of frames per second is not a positive (greater than zero)
	 * number.
	 */
	public TimecodeValueImpl(
			@UInt16 short fps, 
			short hours,
			short minutes,
			short seconds,
			short frames,
			boolean drop) 
		throws IllegalArgumentException {

		this(fps, hours, minutes, seconds, frames, (short) 0, drop);
	}
	
	/** 
	 * <p>Create a new timecode value from the given hours, minutes, seconds, frames and frame pair according to the
	 * given number of frames per second and whether the timecode value requires drop frame calculations.
	 * Internally, these values are converted to an offset value.</p>
	 * 
	 * <p>This method does not apply strict boundaries. Negative values and values greater than the
	 * acceptable bounds when displaying a timecode will be accepted and mapped to an appropriate offset.
	 * For example, the following three calls to this method create equivalent timecode values:</p>
	 * 
	 * <pre>
	 * time1 = new TimecodeValueImpl(25, 0, 0, 63,   0, 0, false);
	 * time2 = new TimecodeValueImpl(25, 0, 1, 03,   0, 1, false);
	 * time3 = new TimecodeValueImpl(25, 0, 1, 04, -25, 1, false);
	 * </pre>
	 *
	 * @param fps Number of frames per second for this timecode value.
	 * @param hours Number of hours since the start of an item represented by this timecode.
	 * @param minutes Number of minutes in the hour of an item represented by this timecode.
	 * @param seconds Number of seconds in the minute of an item represented by this timecode.
	 * @param frames Number of frames through the second of an item represented by this timecode.
	 * @param framePair For frames per second greater than 30, is this the first (.0) or second (.1) in a pair?
	 * @param drop Does this timecode require drop frame calculation?
	 * 
	 * @throws IllegalArgumentException The number of frames per second is not a positive (greater than zero)
	 * number.
	 */	
	public TimecodeValueImpl(
			@UInt16 short fps, 
			short hours,
			short minutes,
			short seconds,
			short frames,
			short framePair,
			boolean drop) {

		if (fps <= 0)
			throw new IllegalArgumentException("The frames per second value for a timecode must be a positive number.");
		if ((framePair < 0) || (framePair > 1))
			throw new IllegalArgumentException("The frame pair value for a timecode must be either 0 or 1.");
		
		FrameTable frameTable = new FrameTable(fps);
		long value;
		
		if (drop == true) {
			value = (hours * frameTable.dropFpHour);
			value += ((minutes / 10) * frameTable.dropFpMin10);
			value += (minutes % 10) * frameTable.dropFpMin;
		}
		else {
			value = hours * frameTable.fpHour;
			value += minutes * frameTable.fpMinute;
		}
		
		value += seconds * ((fps > 30) ? fps / 2 : fps);
		value += frames;
		
		value = (fps > 30) ? value * 2 + framePair : value;
		
		setDropFrame(drop);
		setFramesPerSecond(fps);
		setStartTimecode(value);
	}
	
	public boolean getDropFrame() {
		return dropFrame;
	}

	public void setDropFrame(boolean drop) {
		this.dropFrame = drop;
	}

	public short getFramesPerSecond() {
		return framesPerSecond;
	}

	public void setFramesPerSecond(short framesPerSecond) {
		this.framesPerSecond = framesPerSecond;
	}

	public long getStartTimecode() {
		return startTimecode;
	}

	public void setStartTimecode(long startFrame) {
		this.startTimecode = startFrame;
	}
	
	public int hashCode() {
		
		return ((Long) startTimecode).hashCode() ^ (framesPerSecond << 16) ^ ((Boolean) dropFrame).hashCode();
	}


	/**
	 * <p>Check to see if another timecode value is equal to this one. Two timecode values are equal if
	 * they have the same start frame, number of frames per second and consistent drop frame or non-drop
	 * frame values.</p>
	 * 
	 * <p>To check to see if two timecode values are equal in time, use 
	 * {@link #compareTo(tv.amwa.maj.record.TimecodeValue)}.</p>
	 * 
	 * @param o Object to test for equality with this one.
	 * 
	 * @see java.lang.Object#equals(Object)
	 */
	public final boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof TimecodeValue)) return false;

		TimecodeValue testValue =
			(TimecodeValue) o;
		
		if (dropFrame != testValue.getDropFrame()) return false;
		if (framesPerSecond != testValue.getFramesPerSecond()) return false;
		if (startTimecode != testValue.getStartTimecode()) return false;
		
		return true;
	}

	/**
	 * <p>Chronologically compare this timecode value to another one based on real time, 
	 * which is calculated to take account of frames per second and drop frame values.</p>
	 *
	 * @param o Timecode value to compare this value to.
	 * @return A value of&nbsp;-1 if this timecode value is less than the given value,
	 * 0&nbsp;if they are equal and&nbsp;1 if this one is greater than the
	 * one given.
	 * 
	 * @throws NullPointerException The given timecode value to compare to this one is <code>null</code>.
	 */
	
	public final int compareTo(
			TimecodeValue o) 
		throws NullPointerException {
		
		if (o == null)
			throw new NullPointerException("Cannot compare timecode value with null.");
		
		if (equals(o)) return 0;
		
		long thisInMillis = timeToThousandths(this);
		TimecodeValueImpl testTimecode = new TimecodeValueImpl(
				o.getDropFrame(),
				o.getStartTimecode(),
				o.getFramesPerSecond());
		long testInMillis = timeToThousandths(testTimecode);
		
		if (thisInMillis < testInMillis) return -1;
		if (thisInMillis > testInMillis) return 1;
		return 0;
 	}
	
	/**
	 * <p>Convert timecode values to their equivalent value in real milliseconds. Drop
	 * values are calculated based on 1001/1000 factor of frames per second.</p>
	 *
	 * @param timecode Timecode value to convert to real milliseconds.
	 * @return Number of actual milliseconds represented by the timecode value.
	 */
	private long timeToThousandths(TimecodeValueImpl timecode) {
		
		if (timecode.getDropFrame() == false)
			return (long) (timecode.getStartTimecode() * 
					((double) 1000 / (double) timecode.getFramesPerSecond()));
		else {
			switch (timecode.getFramesPerSecond()) {
			case 24: 
				return ((timecode.getStartTimecode() * 1001000l) / 24000l);
			case 30:
				return ((timecode.getStartTimecode() * 1001000l) / 30000l);
			case 48:
				return ((timecode.getStartTimecode() * 1001000l) / 48000l);
			case 60:
				return ((timecode.getStartTimecode() * 1001000l) / 60000l);
			default:
				return ((timecode.getStartTimecode() * 1001000l) / (timecode.getFramesPerSecond() * 1000l));	
			}
		}
	}
	
	/** 
	 * <p>Formats a timecode value representation in accordance with the SMPTE and EBU specifications.
	 * Specifically:</p>
	 * 
	 * <ul>
	 *  <li>EBU TS N12-1999: "Time-and-control codes for television recording". 1999.</li>
	 *  <li>SMPTE 0012-2008: "SMPTE standard for television, audio and film - time 
	 *  and control code". February 2008. (Character format from SMPTE 0258-2004).</li>
	 * </ul>
	 * 
	 * <p>The format of the returned value is "<code>hh:mm:ss;ff(.p)?</code>" for timecodes with drop
	 * frames and "<code>hh:mm:ss:ff(.p)?</code>" for timecodes with no drop frame. The components of the 
	 * value of the timecode are:</p>
	 * 
	 * <ul>
	 *  <li><code>hh</code> is the number of hours</li>
	 *  <li><code>mm</code> is the number of minutes;</li>
	 *  <li><code>ss</code> is the number of seconds;</li>
	 *  <li><code>ff</code> is the number of frames through this second;</li>.
	 *  <li><code>(.p)?</code> for frame rates over 30, whether the frame is the first (<code>.0</code>) or second 
	 *  (<code>.1</code>) of the pair.</li> 
	 * </ul>
	 * 
	 * @return String representation of this timecode value.
	 */
	public String toString() {
		
		TimeComponents realTime = convertToActualTimeValues();

		StringBuffer sb = new StringBuffer(14);
		
		String value = Long.toString(realTime.hours);
		if (value.length() == 1) sb.append('0');
		sb.append(value);

		sb.append(':');
		
		value = Integer.toString(realTime.minutes);
		if (value.length() == 1) sb.append('0');
		sb.append(value);

		sb.append(':');
		
		value = Integer.toString(realTime.seconds);
		if (value.length() == 1) sb.append('0');
		sb.append(value);

		if (dropFrame == true)
			sb.append(';');
		else
			sb.append(':');

		// Using actual frame numbers rather than fractions of a second here.
		value = Integer.toString(realTime.frameInSecond);
				
		if (value.length() == 1) sb.append('0');
		sb.append(value);
		
		if (realTime.framePair != null) {
			sb.append('.');
			sb.append(realTime.framePair);
		}
		
		return sb.toString();
	}
	
	/**
	 * <p>Converts the timecode value to a real time, taking account of the frames 
	 * per second and drop frame values. The value returned is a 
	 * {@link tv.amwa.maj.record.impl.TimeStructImpl} value, containing the
	 * hours, minutes, seconds and fractions of a second represented by the
	 * frame offset value returned by {@link #getStartTimecode()}.</p>
	 *
	 * @return A time value representing the real time offset of the timecode value.
	 * 
	 * @throws NumberFormatException The timecode value is larger than the range it is 
	 * possible to represent with a time structure value.
	 */
	public TimeStruct convertToRealTime() 
		throws NumberFormatException {

		long thousandths = timeToThousandths(this);
		long hours = thousandths / 3600000l;

		if (hours > 23l)
			throw new NumberFormatException("Timecode value is too large to convert to a time structure value.");
		
		thousandths = thousandths % 3600000l;
		int minutes = (int) (thousandths / 60000l);
		thousandths = thousandths % 60000;
		int seconds = (int) (thousandths / 1000l);
		int fraction = ((int) (thousandths % 1000l)) / 4;
		
		return new TimeStructImpl(
				(byte) hours, (byte) minutes, (byte) seconds, (byte) fraction);
	}

	/**
	 * <p>Parses a SMPTE-style string representation of a timecode and creates a MAJ API timecode value, 
	 * with all parameters specified.
	 * The method will try and parse both drop and non-drop values, working with both SMPTE-12M values
	 * and the additional frame pair value for higher frame rate television.</p>
	 * 
	 * <p>The format of a timecode parsed by this method is:</p>
	 * 
	 * <center>&lt;<em>hour</em>&gt;<code>:</code>&lt;<em>minute</em>&gt;<code>:</code>&lt;<em>second</em>&gt;<code>:</code>&lt;<em>frame</em>&gt;(<code>.</code>&lt;<em>framePair</em>&gt;)?</center>
	 *    
	 * <p>The <em>hour</em>, <em>minute</em>, <em>second</em> and <em>frame</em> values should all be Java short
	 * values. The <em>framePair</em> value should be either 0 or 1. The number of digits is not significant, although values are 
	 * limited to being {@link tv.amwa.maj.integer.Int16}. For example, the values <code>"01:02:03:04"</code> is parsed the same as <code>"1:2:003:4".</p>
	 *  
	 * @param timecodeText String representation of a timecode value.
	 * @param fps Number of frames per second this timecode is measured against.
	 * @param drop Whether this timecode has drop values or not?
	 * @return A timecode value created by parsing the given textual representation.
	 * 
	 * @throws NullPointerException The given timecode string is <code>null</code>.
	 * @throws ParseException It is not possible to parse the given value to a MAJ API timecode value.
	 * @throws IllegalArgumentException The given frames per second value is a negative value.
	 */
	public final static TimecodeValue parseTimecode(
			String timecodeText, 
			@UInt16 short fps,
			boolean drop) 
		throws NullPointerException,
			ParseException,
			IllegalArgumentException {
		
		if (timecodeText == null)
			throw new NullPointerException("The given timecode value as a string is null.");
		
		if (fps < 0)
			throw new IllegalArgumentException("When parsing a timecode value, the given frames per second value is negative.");
		
		StringTokenizer timecodeTokens = new StringTokenizer(timecodeText, ":;.,");
		
		short hours, minutes, seconds, frames, framePair;

		try {
			hours = Short.parseShort(timecodeTokens.nextToken());
			minutes = Short.parseShort(timecodeTokens.nextToken());
			seconds = Short.parseShort(timecodeTokens.nextToken());
			frames = Short.parseShort(timecodeTokens.nextToken());
		}
		catch (NumberFormatException nfe) {
			throw new ParseException("Parsing one of the values in the given string causes a number format exception:" +
					nfe.getMessage(), 0);
		}
		catch (NoSuchElementException nsee) {
			throw new ParseException("The given value is not formatted as a valid timecode value.", 0);
		}
		
		try {
			framePair = Short.parseShort(timecodeTokens.nextToken());
		}
		catch (NumberFormatException nfe) {
			throw new ParseException("Parsing the frane pair value of the given string causes a number format exception:" +
					nfe.getMessage(), 0);
		}
		catch (NoSuchElementException nsee) {
			framePair = 0;
		}
				
		return new TimecodeValueImpl(
				fps, hours, minutes, seconds, frames, framePair, drop);
	}
	
	/**
	 * <p>Parses a SMPTE-style string representation of a timecode and creates a MAJ API timecode value,
	 * working out the drop frame value from the given timcode text.
	 * The method will try and parse both drop and non-drop values, working with both SMPTE-12M values
	 * and a broader definition of timecodes as required for high frame rate television. This method
	 * assumes any value with a "<code>;</code>" in it is a drop-frame value; otherwise the value is
	 * assumed to be a non-drop timecode.</p>
	 * 
	 * <p>String timecode representations are parsed as follows:</p>
	 * 
	 * <ul>
	 *  <li>The format of a non-drop timecode specified by frame is:<br>
	 * 
	 *   <center>&lt;<em>hour</em>&gt;<code>:</code>&lt;<em>minute</em>&gt;<code>:</code>&lt;<em>second</em>&gt;<code>:</code>&lt;<em>frame</em>&gt;(<code>.</code>&lt;<em>framePair</em>&gt;)?</center>
	 *  </li>
	 *  
	 *  <li>The format of a drop timecode specified by frame is:<br>
	 * 
	 *   <center>&lt;<em>hour</em>&gt;<code>:</code>&lt;<em>minute</em>&gt;<code>:</code>&lt;<em>second</em>&gt;<code>;</code>&lt;<em>frame</em>&gt;(<code>.</code>&lt;<em>framePair</em>&gt;)?</center>
	 *  </li>
	 * </ul>
	 * 
	 * <p>The <em>hour</em>, <em>minute</em>, <em>second</em> and <em>frame</em> values should all be Java short
	 * values. The <em>framePair</em> value should be either 0 or 1. The number of digits is not significant, although values are 
	 * limited to being {@link tv.amwa.maj.integer.Int16}. For example, the values <code>"01:02:03:04"</code> is parsed the same as <code>"1:2:003:4".</p>
	 *  
	 * @param timecodeText String representation of a timecode value.
	 * @param fps Number of frames per second this timecode is measured against.
	 * @return A timecode value created by parsing the given textual representation.
	 * 
	 * @throws NullPointerException The given timecode string is <code>null</code>.
	 * @throws ParseException It is not possible to parse the given value to a MAJ API timecode value or
	 * the given frames per second value is a negative value.
	 */	
	public final static TimecodeValue parseTimecode(
			String timecodeText,
			@UInt16 short fps) 
		throws NullPointerException,
			ParseException {
		
		if (timecodeText == null)
			throw new NullPointerException("The given timecode value as a string is null.");
		
		if (timecodeText.contains(";"))
			return parseTimecode(timecodeText, fps, true);
		else
			return parseTimecode(timecodeText, fps, false);
	}
	
	/**
	 * <p>Parses a SMPTE-style string representation of a timecode and creates a MAJ API timecode value,
	 * implying default values for the frames per second and drop frame parameters from the timecode text.
	 * The method will try and parse both drop and non-drop values, working with both SMPTE-12M values
	 * and a broader definition of timecodes as required for high frame rate television. This method
	 * assumes any value with a "<code>;</code>" in it is a drop-frame value at 30&nbsp;fps or 60&nbsp;fps,
	 * otherwise the value is assumed to be a non-drop timecode at 25&nbsp;fps or 50&nbsp;fps. The higher 
	 * frame rates of 60&nbsp;fps; and 50&nbsp;fps if the value ends with a frame pair value of <code>.0</code>
	 * or <code>.1</code>.</p>
	 * 
	 * <p>String timecode representations are parsed as follows:</p>
	 * 
	 * <ul>
	 *  <li>The format of a non-drop timecode specified by frame is:<br>
	 * 
	 *   <center>&lt;<em>hour</em>&gt;<code>:</code>&lt;<em>minute</em>&gt;<code>:</code>&lt;<em>second</em>&gt;<code>:</code>&lt;<em>frame</em>&gt;(<code>.</code>&lt;<em>framePair</em>&gt;)?</center>
	 *  </li>
	 *  
	 *  <li>The format of a drop timecode specified by frame is:<br>
	 * 
	 *   <center>&lt;<em>hour</em>&gt;<code>:</code>&lt;<em>minute</em>&gt;<code>:</code>&lt;<em>second</em>&gt;<code>;</code>&lt;<em>frame</em>&gt;(<code>.</code>&lt;<em>framePair</em>&gt;)?</center>
	 *  </li>
	 * </ul>
	 * 
	 * <p>The <em>hour</em>, <em>minute</em>, <em>second</em> and <em>frame</em> values should all be Java short
	 * values. The <em>framePair</em> value should be either 0 or 1. The number of digits is not significant, although values are 
	 * limited to being {@link tv.amwa.maj.integer.Int16}. For example, the values <code>"01:02:03:04"</code> is parsed the same as <code>"1:2:003:4".</p>
	 * 
	 * @param timecodeText String representation of a timecode value.
	 * @return A timecode value created by parsing the given textual representation.
	 * 
	 * @throws NullPointerException The given timecode string is <code>null</code>.
	 * @throws ParseException It is not possible to parse the given value to a MAJ API timecode value.
	 */	
	public final static TimecodeValue parseTimecode(
			String timecodeText) 
		throws NullPointerException,
			ParseException {
		
		if (timecodeText == null)
			throw new NullPointerException("The given timecode value as a string is null.");
		
		if ((timecodeText.contains(";")) || (timecodeText.contains(","))) {
			if (timecodeText.endsWith(".0") || (timecodeText.endsWith(".1")))
				return parseTimecode(timecodeText, (short) 60, true);
			else
				return parseTimecode(timecodeText, (short) 30, true);
		}
		else {
			if (timecodeText.endsWith(".0") || (timecodeText.endsWith(".1")))
				return parseTimecode(timecodeText, (short) 50, false);
			else
				return parseTimecode(timecodeText, (short) 25, false);
		}
	}
	
	/**
	 * <p>Component parts of a timecode value, represented as hours, minutes, seconds and frames in a second.</p>
	 *
	 *
	 *
	 */
	public class TimeComponents {
		
		/** Frame-pair number, either zero or one for 60fps or null if not present. */
		public Integer framePair = null;
		/** Frame through the second component. */
		public int frameInSecond;
		/** Second component of the timecode. */
		public int seconds;
		/** Minute component of the timecode. */
		public int minutes;
		/** Hour component of the timecode. */
		public long hours;
	}
	
	/**
	 * <p>Converts this timecode into a {@link TimeComponents} value. This method will
	 * carry out drop frame calculations if required.</p>
	 *
	 * @return Componentized representation of this timecode value.
	 */
	public TimeComponents convertToActualTimeValues() {
		
		TimeComponents internal = new TimeComponents();
		
		if (dropFrame == false) {
			long baseTimecode = (framesPerSecond > 30) ? startTimecode / 2 : startTimecode;
			short baseFps = (framesPerSecond > 30) ? (short) (framesPerSecond / 2) : framesPerSecond;
			internal.frameInSecond = (int) (baseTimecode % baseFps);
			long totalSeconds = baseTimecode / baseFps;
			internal.seconds = ((int) totalSeconds % 60);
			long totalMinutes = totalSeconds / 60;
			internal.minutes = (int) totalMinutes % 60;
			internal.hours = totalMinutes / 60;

			internal.framePair = (framesPerSecond > 30) ? ((int) (startTimecode % 2)) : null;
			
			return internal;
		}
		long baseTimecode = (framesPerSecond > 30) ? startTimecode / 2 : startTimecode;
		internal.hours = baseTimecode / ((30 * 60 * 60) - 108);
		int remainingFrames = (int) (baseTimecode % ((30 * 60 * 60) - 108));
		int majorMinutes = remainingFrames / ((30 * 60 * 10) - 18);
		remainingFrames = remainingFrames % ((30 * 60 * 10) - 18);
		
		if (remainingFrames < (30 * 60)) {
			internal.minutes = majorMinutes * 10;
			internal.seconds = remainingFrames / 30;
			internal.frameInSecond = remainingFrames % 30;
		}
		else {
			remainingFrames = remainingFrames - (30 * 60);
			internal.minutes = majorMinutes * 10 + remainingFrames / ((30 * 60) - 2) + 1;
			remainingFrames = remainingFrames % ((30 * 60) - 2);
			if (remainingFrames < 28) { // Only the first second of a minute is short
				internal.seconds = 0;
				internal.frameInSecond = remainingFrames + 2;
			}
			else {
				remainingFrames += 2;
				internal.seconds = (remainingFrames / 30); // No 0 or 1 value.
				internal.frameInSecond = remainingFrames % 30;
			}
		}
		internal.framePair = (framesPerSecond > 30) ? ((int) (startTimecode % 2)) : null;
		return internal;
	}
	
	public final TimecodeValue clone() {
		
		try {
			return (TimecodeValue) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// This class implements cloneable so this should not happen.
			cnse.printStackTrace();
			return null;
		}
	}

	public final static String TIMECODEVALUE_TAG = "TimecodeValue";
	public final static String STARTTIMECODE_TAG = "StartTimecode";
	public final static String FRAMESPERSECOND_TAG = "FramesPerSecond";
	public final static String DROPFRAME_TAG = "DropFrame";
	
	public void appendXMLChildren(
			Node parent) {

		XMLBuilder.appendComment(parent, "TimecodeValue represents a structure and is not a defined AAF XML element.");
		Element timecodeElement = 
			XMLBuilder.createChild(parent, AAF_XML_NAMESPACE, AAF_XML_PREFIX, TIMECODEVALUE_TAG);
		
		appendXMLGrandchildren(timecodeElement);
	}
	
	/**
	 * <p>Append just the child elements specifying the value of this timecode to the given node.</p>
	 * 
	 * @param timecodeElement Element to append timecode specifiers to.
	 */
	public void appendXMLGrandchildren(
			Node timecodeElement) {
		
		XMLBuilder.appendElement(timecodeElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, STARTTIMECODE_TAG, startTimecode);
		XMLBuilder.appendElement(timecodeElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, FRAMESPERSECOND_TAG, framesPerSecond);
		XMLBuilder.appendElement(timecodeElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, DROPFRAME_TAG, dropFrame);		
	}
	
	/**
	 * <p>Calculate the duration of a piece of media from its start timecode and end timecode. The value returned
	 * is a timecode representing the duration. Both values must be specified with the same number of frames per
	 * second and matching drop timecode specifications. The end value must be the same or greater than the 
	 * start value.</p>
	 * 
	 * <p>To calculate the number of frames of an item of media, call {@link #getStartTimecode()} on the value 
	 * returned by this method.</p>
	 * 
	 * @param startValue Start timecode at the beginning of the segment of media, sometimes known as 
	 * SOM (<em>Start Of Message</em>).
	 * @param endValue End timecode at the end of the segment of media, sometimes known as EOM (<em>End Of
	 * Message</em>).
	 * @return Duration timecode value calculated as the difference between the end value and the start value.
	 * 
	 * @throws NullPointerException One or both of the start and/or end timecodes is/are <code>null</code>.
	 * @throws IllegalArgumentException It was not possible to calculate the duration as the timecode values
	 * are incompatible or the start value is after the end value.
	 * 
	 * @see tv.amwa.maj.misctype.LengthType
	 * @see #calculateEndTimecode(tv.amwa.maj.record.TimecodeValue, tv.amwa.maj.record.TimecodeValue)
	 */
	public final static TimecodeValueImpl calculateDuration(
			TimecodeValue startValue,
			TimecodeValue endValue) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (startValue == null)
			throw new NullPointerException("Cannot calculate a duration from a null start value.");
		
		if (endValue == null)
			throw new NullPointerException("Cannot calculate a duration from a null end value.");
		
		if (startValue.getFramesPerSecond() != endValue.getFramesPerSecond())
			throw new IllegalArgumentException("Start and end timecode values must have the same number of frames per second.");
		
		if (startValue.getDropFrame() != endValue.getDropFrame())
			throw new IllegalArgumentException("Start and end timecode values must have matching drop or non-drop specifications.");
		
		if (startValue.getStartTimecode() > endValue.getStartTimecode())
			throw new IllegalArgumentException("Start timecode value must be before the end timecode value.");
		
		return new TimecodeValueImpl(
				startValue.getDropFrame(), 
				endValue.getStartTimecode() - startValue.getStartTimecode(),
				startValue.getFramesPerSecond());
		
	}
	
	/**
	 * <p>Calculate the end timecode for a segment of media from its start timecode and a timecode value representing
	 * its duration. Both values must be specified with the same number of frames per
	 * second and matching drop timecode specifications.</p>
	 * 
	 * @param startValue Start timecode at the beginning of a segment of media.
	 * @param duration Duration of the media specified as a timecode value.
	 * @return End timecode value calculated by adding the duration timecode's length to the start timecode.
	 * 
	 * @throws NullPointerException One or both of the start and/or duration values is/are <code>null</code>.
	 * @throws IllegalArgumentException It was not possible to calculate the end timecode as the timecode values
	 * are incompatible.
	 * 
	 * @see #calculateDuration(tv.amwa.maj.record.TimecodeValue, tv.amwa.maj.record.TimecodeValue)
	 */
	public final static TimecodeValueImpl calculateEndTimecode(
			TimecodeValue startValue,
			TimecodeValue duration) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (startValue == null)
			throw new NullPointerException("Cannot calculate an end timecode from a null start value.");
		
		if (duration == null)
			throw new NullPointerException("Cannot calculate an end timecode from a null duration.");
		
		if (startValue.getFramesPerSecond() != duration.getFramesPerSecond())
			throw new IllegalArgumentException("Start and duration timecode values must have the same number of frames per second.");
		
		if (startValue.getDropFrame() != duration.getDropFrame())
			throw new IllegalArgumentException("Start and duration timecode values must have matching drop or non-drop specifications.");		
		
		return new TimecodeValueImpl(
				startValue.getDropFrame(),
				startValue.getStartTimecode() + duration.getStartTimecode(),
				startValue.getFramesPerSecond());
	}

	public String getComment() {

		return null;
	}
}