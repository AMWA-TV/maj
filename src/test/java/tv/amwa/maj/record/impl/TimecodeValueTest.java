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
 * $Log: TimecodeValueTest.java,v $
 * Revision 1.2  2011/01/20 17:40:50  vizigoth
 * Fixed up all record tests to the point where they all pass.
 *
 * Revision 1.1  2011/01/20 11:15:09  vizigoth
 * Change of package name from embeddable to record.impl.
 *
 * Revision 1.4  2009/12/18 17:56:02  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.3  2009/03/30 09:17:55  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/10/15 09:46:53  vizigoth
 * Improvements alongside documentation improvements.
 *
 * Revision 1.1  2007/11/13 22:16:42  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:32  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.record.TimecodeValue;
import tv.amwa.maj.record.impl.TimecodeValueImpl;


import java.text.ParseException;
import java.util.*;

public class TimecodeValueTest {

	static final TimecodeValueImpl zero = 
		new TimecodeValueImpl(false, 0l, (short) 25);
	static final String zeroString = "00:00:00:00";
	
	static final TimecodeValueImpl random1 = // 00:30:47;03 with drop
		new TimecodeValueImpl(true, 55359l, (short) 30);
	static final String random1String = "00:30:47;03";
	static final String random1Real = "00:30:47.14";
	
	static final TimecodeValueImpl random2 = // 01:23:45;06
		new TimecodeValueImpl(true, 150606l, (short) 30);
	static final String random2String = "01:23:45;06";
	static final String random2Real = "01:23:45.22";
	
	static final TimecodeValueImpl biggest =
		new TimecodeValueImpl(false, Long.MAX_VALUE, (short) 24);
	static final HashMap<Integer, String> dropTestMap = new HashMap<Integer, String>();
	
	static {
		dropTestMap.put(0, 							"00:00:00;00");
		dropTestMap.put(1, 							"00:00:00;01");
		dropTestMap.put(2, 							"00:00:00;02");
		dropTestMap.put((30 * 60) - 1, 				"00:00:59;29");
		dropTestMap.put((30 * 60), 					"00:01:00;02");
		dropTestMap.put((30 * 60) + 1, 				"00:01:00;03");
		dropTestMap.put((2 * 30 * 60 - 2) - 1, 		"00:01:59;29");
		dropTestMap.put((2 * 30 * 60 - 2), 			"00:02:00;02");
		dropTestMap.put((2 * 30 * 60 - 2) + 1, 		"00:02:00;03");
		dropTestMap.put((30 * 60 + 28) - 1, 		"00:01:00;29");
		dropTestMap.put((30 * 60 + 28), 			"00:01:01;00");
		dropTestMap.put((30 * 60 + 28) + 1, 		"00:01:01;01");
		dropTestMap.put((10 * 30 * 60 - 18) - 1, 	"00:09:59;29");
		dropTestMap.put((10 * 30 * 60 - 18), 		"00:10:00;00");
		dropTestMap.put((10 * 30 * 60 - 18) + 1, 	"00:10:00;01");
		dropTestMap.put((60 * 30 * 60 - 108) - 1, 	"00:59:59;29");
		dropTestMap.put((60 * 30 * 60 - 108), 		"01:00:00;00");
		dropTestMap.put((60 * 30 * 60 - 108) + 1, 	"01:00:00;01");

	}
	
	@Test public void createFromValues() {
		
		TimecodeValueImpl testTimecode = 
			new TimecodeValueImpl(true, 123l, (short) 25);
		assertEquals(true, testTimecode.getDropFrame());
		assertEquals(123l, testTimecode.getStartTimecode());
		assertEquals((short) 25, testTimecode.getFramesPerSecond());	
	}

	@Test public void equalTimecodesNull() {
		
		assertEquals(false, zero.equals(null));
	}
	
	@Test public void equalTimecodesObject() {
		
		assertEquals(false, zero.equals(new Object()));
	}
	
	@Test public void equalTimecodesSame() {
		
		assertEquals(true, zero.equals(zero));
	}
	
	@Test public void equalTimecodesRandom() {
		
		assertEquals(false, zero.equals(random1));
	}

	@Test public void equalTimecodesRandoCopy() {
		TimecodeValueImpl randomCopy = new TimecodeValueImpl(
				random1.getDropFrame(), 
				random1.getStartTimecode(),
				random1.getFramesPerSecond());
		
		assertEquals(true, randomCopy.equals(random1));
		assertEquals(true, random1.equals(randomCopy));
		
		randomCopy.setDropFrame(false);
		assertEquals(false, random1.equals(randomCopy));
		
		randomCopy.setDropFrame(true);
		randomCopy.setFramesPerSecond((short) 29);
		assertEquals(false, random1.equals(randomCopy));
	}

	@Test public void compareTimecodesZeroRandom() {

		assertEquals(-1, zero.compareTo(random1));
		assertEquals(0, zero.compareTo(zero));
		assertEquals(0, random1.compareTo(random1));
		assertEquals(1, random1.compareTo(zero));
	}
	
	@Test public void compareTimecodesRandomPlusOne() {
		
		TimecodeValueImpl randomPlusOne = new TimecodeValueImpl(
				random1.getDropFrame(),
				random1.getStartTimecode() + 1,
				random1.getFramesPerSecond());
		assertEquals(-1, random1.compareTo(randomPlusOne));
		assertEquals(1, randomPlusOne.compareTo(random1));
	}

	@Test(expected=NullPointerException.class)
	public void compareToNull() {
		
		assertEquals(1, zero.compareTo(null));
	}
	
	@Test public void cloneTimecode() {
		
		TimecodeValue clone = random1.clone();
		
		assertFalse(clone == random1);
		
		assertEquals(random1.getDropFrame(), clone.getDropFrame());
		assertEquals(random1.getFramesPerSecond(), clone.getFramesPerSecond());
		assertEquals(random1.getStartTimecode(), clone.getStartTimecode());
		
		assertEquals(true, clone.equals(random1));
		assertEquals(true, random1.equals(clone));
		
		assertEquals(0, clone.compareTo(random1));
		assertEquals(0, random1.compareTo(clone));
	}

	@Test public void timecodeToStringRandom1() {
		
		String random1String = random1.toString();
		assertEquals("00:30:47;03", random1String);
	}
	
	@Test public void timecodeToStringRandom2() {
		
		String random2String = random2.toString();
		assertEquals("01:23:45;06", random2String);
	}
	
	@Test public void timecodeToStringZeroString() {
		
		String zeroString = zero.toString();
		assertEquals("00:00:00:00", zeroString);
	}
		
	@Test public void timecodeToStringBiggest() {
	
		String biggestString = biggest.toString();
		assertEquals("106751991167300:30:25:07", biggestString);
	}

	@Test public void timecodeToTimeStruct() {
		
		tv.amwa.maj.record.TimeStruct zeroTime =
			zero.convertToRealTime();
		// System.out.println(zeroTime.toString());
		assertTrue(zeroTime.toString().startsWith("00:00:00"));
		
		tv.amwa.maj.record.TimeStruct random1Time =
			random1.convertToRealTime();
		assertEquals(true, random1Time.toString().startsWith(random1Real));
		
		tv.amwa.maj.record.TimeStruct random2Time =
			random2.convertToRealTime();
		assertEquals(true, random2Time.toString().startsWith(random2Real));
	}

	@Test(expected=NumberFormatException.class) 
	public void timecodeToTimeStructTooBig() {
		
		@SuppressWarnings("unused")
		tv.amwa.maj.record.TimeStruct biggestTime = 
			biggest.convertToRealTime();
	}

	@Test public void dropCalculationBoundaries() {
		
		for ( int key : dropTestMap.keySet() ) {
			TimecodeValueImpl testValue = new TimecodeValueImpl(true, (long) key, (short) 30);
			// System.out.println(dropTestMap.get(key) + " " + testValue.toString());
			assertEquals(dropTestMap.get(key), testValue.toString());
		}
	}

	// Not a valid test ... even drop frame timecode drifts off by 86.4ms per day.
//	@Test public void compareHourTimecodes() {
//		
//		TimecodeValueImpl hourNonDrop = new TimecodeValueImpl(false, (long) 3600 * 25, (short) 25);
//		TimecodeValueImpl hourDrop = 
//			new TimecodeValueImpl(true, (long) ((30 * 60 * 60) - 108), (short) 30);
//		
//		assertEquals(0, hourNonDrop.compareTo(hourDrop));
//		assertEquals(0, hourDrop.compareTo(hourNonDrop));
//	}

	// Not a valid test ... even drop frame timecode drifts off by 86.4ms per day.
//	@Test public void compareHourTimecodesIncrementing() {
//		
//		for ( int u = 0 ; u < 10000 ; u += 100) {
//			
//			TimecodeValueImpl hourNonDrop = 
//				new TimecodeValueImpl(false, (long) u * 3600 * 25, (short) 25);
//			TimecodeValueImpl hourDrop = 
//				new TimecodeValueImpl(true, (long) u * ((30 * 60 * 60) - 108), (short) 30);
//			
//			assertEquals(0, hourNonDrop.compareTo(hourDrop));
//			assertEquals(0, hourDrop.compareTo(hourNonDrop));
//
//			// System.out.println(hourNonDrop.getStartOffset() + ", " + hourDrop.getStartOffset());
//		}
//	}
	
	@Ignore @Test public void toXML() { // FIXME make this test run on Windows
		
		String asXML = XMLBuilder.toXMLNonMetadata(random1);
		// System.out.println(asXML);
		
		String expectedEnding = "<aaf:TimecodeValue xmlns:aaf=\"http://www.smpte-ra.org/schemas/2001-2/2007/aaf\">\n" +
				"  <aaf:StartTimecode>55359</aaf:StartTimecode>\n" +
				"  <aaf:FramesPerSecond>30</aaf:FramesPerSecond>\n" +
				"  <aaf:DropFrame>true</aaf:DropFrame>\n" +
				"</aaf:TimecodeValue>";
		assertTrue(asXML.contains(expectedEnding));
	}
	
	@Test public void createTimecodeFromRealTimeZero() {
		
		TimecodeValueImpl fromRealTime = new TimecodeValueImpl((short) 25, (short) 0, (short) 0, (short) 0, (short) 0, false);
		assertTrue(fromRealTime.equals(zero));
	}
	
	@Test public void createTimecodeFromRealTimeRandom1() {
		
		TimecodeValueImpl fromRandom1 = new TimecodeValueImpl(
				random1.getFramesPerSecond() , (short) 0, (short) 30, (short) 47, (short) 3, true);
		assertTrue(fromRandom1.equals(random1));
	}
	
	@Test public void createTimecodeFromRealTimeRandom2() {
		
		TimecodeValueImpl fromRandom2 = new TimecodeValueImpl(
				random2.getFramesPerSecond() , (short) 1, (short) 23, (short) 45, (short) 6, true);
		assertTrue(fromRandom2.equals(random2));	
	}
	
	// No longer supporting frame rates over 60fps. Added framePair .0 and .1 postfix notation instead.
//	@Test public void createTimecodeFromRealTimeNonDrop() {
//		
//		TimecodeValueImpl nonDrop = new TimecodeValueImpl(
//				(short) 300, (short) 12, (short) 34, (short) 56, (short) 135, false);
//		assertEquals("12:34:56:135", nonDrop.toString());
//	}
	
	@Test public void createTimecodeFromRealTimeWrapAround() {
		
		TimecodeValueImpl time1 = new TimecodeValueImpl((short) 25, (short) 0, (short) 0, (short) 63, (short) 0, false);
		TimecodeValueImpl time2 = new TimecodeValueImpl((short) 25, (short) 0, (short) 1, (short) 3, (short) 0, false);
		TimecodeValueImpl time3 = new TimecodeValueImpl((short) 25, (short) 0, (short) 1, (short) 04, (short) -25, false);
		
		assertTrue(time1.equals(time2));
		assertTrue(time2.equals(time3));
		assertTrue(time3.equals(time1));
	}
	
	@Test public void parseTimecodeValueZero() 
		throws NullPointerException, ParseException {
		
		TimecodeValue testValue = TimecodeValueImpl.parseTimecode(zeroString, (short) 25);
		assertTrue(zero.equals(testValue));
	}
	
	@Test public void parseTimecodeValueZeroWrongFPS() 
		throws NullPointerException, ParseException {
		
		TimecodeValue testValue = TimecodeValueImpl.parseTimecode(zeroString, (short) 24);
		assertFalse(zero.equals(testValue));
	}

	@Test public void parseTimecodeValueZeroImpliedFPS() 
		throws NullPointerException, ParseException {
		
		TimecodeValue testValue = TimecodeValueImpl.parseTimecode(zeroString);
		assertTrue(zero.equals(testValue));
	}

	@Test public void parseTimecodeValueRandom1() 
		throws NullPointerException, ParseException {
		
		TimecodeValue testValue = TimecodeValueImpl.parseTimecode(random1String, (short) 30);
		assertTrue(random1.equals(testValue));
	}
	
	@Test public void parseTimecodeValueRandom1ImpliedFPS() 
		throws NullPointerException, ParseException {
		
		TimecodeValue testValue = TimecodeValueImpl.parseTimecode(random1String);
		assertTrue(random1.equals(testValue));
	}

	@Test public void parseTimecodeValueRandom2() 
		throws NullPointerException, ParseException {
		
		TimecodeValue testValue = TimecodeValueImpl.parseTimecode(random2String, (short) 30);
		assertTrue(random2.equals(testValue));	
	}

	@Test public void parseTimecodeValueAsDocs() 
		throws NullPointerException, ParseException {
		
		TimecodeValue test1 = TimecodeValueImpl.parseTimecode("01:02:03:04", (short) 100);
		TimecodeValue test2 = TimecodeValueImpl.parseTimecode("1:2:003:4", (short) 100);
		
		assertTrue(test1.equals(test2));
		assertEquals(0, test1.compareTo(test2));
	}

	@Test(expected=ParseException.class)
	public void parseTimecodeValueTooShort() 
		throws NullPointerException, ParseException {
		
		TimecodeValueImpl.parseTimecode("12:13:14", (short) 1);
	}
	
	@Test(expected=ParseException.class) 
	public void parseTimecodeValueBadDigit() 
		throws NullPointerException, ParseException {
		
		TimecodeValueImpl.parseTimecode("12:13:14;1a5", (short) 40);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void parseTimecodeValueNegativeFPS() 
		throws NullPointerException, ParseException {
		
		TimecodeValueImpl.parseTimecode(zeroString, (short) -1);
	}

	@Test public void timecodeHashcode() {
		
		int random1Hash = random1.hashCode();
		assertEquals(2022640, random1Hash);
	}
	
	@Test public void timecodeHashcodeFPS() {
		
		TimecodeValue cloned = random1.clone();
		cloned.setFramesPerSecond((short) 24);
		assertNotSame(random1.hashCode(), cloned.hashCode());
	}

	@Test public void timecodeHashcodeDrop() {
		
		TimecodeValue cloned = random1.clone();
		cloned.setDropFrame(false);
		assertNotSame(random1.hashCode(), cloned.hashCode());
	}

	@Test public void testCalculateDurationSame() {
		
		TimecodeValue endValue = random1.clone();
		TimecodeValue result = TimecodeValueImpl.calculateDuration(random1, endValue);
		assertFalse(zero.equals(result));
		assertTrue((new TimecodeValueImpl(random1.getDropFrame(), 0l, random1.getFramesPerSecond())).equals(result));
	}
	
	@Test public void testCalculateDurationDifferent() 
		throws NullPointerException, ParseException {
		
		TimecodeValue startValue = TimecodeValueImpl.parseTimecode("09:59:55:00");
		TimecodeValue endValue = TimecodeValueImpl.parseTimecode("10:09:08:07");
		TimecodeValueImpl result = TimecodeValueImpl.calculateDuration(startValue, endValue);
		
		assertEquals("00:09:13:07", result.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCalculateDurationFpsDifferent() 
		throws NullPointerException, ParseException {
		
		TimecodeValue startValue = TimecodeValueImpl.parseTimecode("09:59:55:00", (short) 30);
		TimecodeValue endValue = TimecodeValueImpl.parseTimecode("10:09:08:07", (short) 29);
		TimecodeValueImpl result = TimecodeValueImpl.calculateDuration(startValue, endValue);
		
		assertEquals("00:09:13:07", result.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCalculateDurationDropDifferent() 
		throws NullPointerException, IllegalArgumentException, ParseException {
		
		TimecodeValue startValue = TimecodeValueImpl.parseTimecode("09:59:55:00", (short) 30, true);
		TimecodeValue endValue = TimecodeValueImpl.parseTimecode("10:09:08:07", (short) 30, false);
		TimecodeValueImpl result = TimecodeValueImpl.calculateDuration(startValue, endValue);
		
		assertEquals("00:09:13:07", result.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCalculateDurationLessThan() 
		throws NullPointerException, IllegalArgumentException, ParseException {
		
		TimecodeValue endValue = TimecodeValueImpl.parseTimecode("09:59:55:00", (short) 30, true);
		TimecodeValue startValue = TimecodeValueImpl.parseTimecode("10:09:08:07", (short) 30, false);
		TimecodeValueImpl result = TimecodeValueImpl.calculateDuration(startValue, endValue);
		
		assertEquals("00:09:13:07", result.toString());
	}


	@Test public void testCalculateEndDifference() 
		throws NullPointerException, ParseException {
		
		TimecodeValue startValue = TimecodeValueImpl.parseTimecode("09:59:55:00");
		TimecodeValue duration = TimecodeValueImpl.parseTimecode("00:09:13:07");
		TimecodeValueImpl result = TimecodeValueImpl.calculateEndTimecode(startValue, duration);
		
		assertEquals("10:09:08:07", result.toString());
	}
	
	@Test public void testCalculateEndSame() 
		throws NullPointerException, ParseException {
		
		TimecodeValue startValue = TimecodeValueImpl.parseTimecode("09:59:55:00");
		TimecodeValue duration = TimecodeValueImpl.parseTimecode("00:00:00:00");
		TimecodeValueImpl result = TimecodeValueImpl.calculateEndTimecode(startValue, duration);
		
		assertTrue(startValue.equals(result));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCalculateEndFpsDifferent() 
		throws NullPointerException, ParseException {
		
		TimecodeValue startValue = TimecodeValueImpl.parseTimecode("09:59:55:00", (short) 30);
		TimecodeValue duration = TimecodeValueImpl.parseTimecode("00:09:13:07", (short) 29);
		@SuppressWarnings("unused")
		TimecodeValueImpl result = TimecodeValueImpl.calculateEndTimecode(startValue, duration);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCalculateEndDropDifferent() 
		throws NullPointerException, IllegalArgumentException, ParseException {
		
		TimecodeValue startValue = TimecodeValueImpl.parseTimecode("09:59:55:00", (short) 30, true);
		TimecodeValue duration = TimecodeValueImpl.parseTimecode("00:09:13:07", (short) 29, false);
		@SuppressWarnings("unused")
		TimecodeValueImpl result = TimecodeValueImpl.calculateEndTimecode(startValue, duration);
	}

	// Tests of .0 and .1 postfix notation
	@Test
	public void testParsePostfixNotation50fps() 
		throws ParseException {
		
		TimecodeValue parsedValue = TimecodeValueImpl.parseTimecode("00:00:00:00.0");
		assertEquals((short) 50, parsedValue.getFramesPerSecond());
		assertEquals(false, parsedValue.getDropFrame());
		assertEquals(0l, parsedValue.getStartTimecode());
		
		parsedValue = TimecodeValueImpl.parseTimecode("00:00:00:00.1");
		assertEquals((short) 50, parsedValue.getFramesPerSecond());
		assertEquals(false, parsedValue.getDropFrame());
		assertEquals(1, parsedValue.getStartTimecode());
	}
	
	@Test
	public void testParsePostfixNotation60fps() 
		throws ParseException {
		
		TimecodeValue parsedValue = TimecodeValueImpl.parseTimecode("00:00:00;00.0");
		assertEquals((short) 60, parsedValue.getFramesPerSecond());
		assertEquals(true, parsedValue.getDropFrame());
		assertEquals(0l, parsedValue.getStartTimecode());
		
		parsedValue = TimecodeValueImpl.parseTimecode("00:00:00;00.1");
		assertEquals((short) 60, parsedValue.getFramesPerSecond());
		assertEquals(true, parsedValue.getDropFrame());
		assertEquals(1, parsedValue.getStartTimecode());
	}

	@Test
	public void test50fpsToString() {
		
		TimecodeValue testValue = new TimecodeValueImpl(false, 0l, (short) 50);
		assertEquals("00:00:00:00.0", testValue.toString());
		
		testValue = new TimecodeValueImpl(false, 1l, (short) 50);
		assertEquals("00:00:00:00.1", testValue.toString());
		
		testValue = new TimecodeValueImpl(false, 2l, (short) 50);
		assertEquals("00:00:00:01.0", testValue.toString());
	}
	
	@Test
	public void test60fpsToString() {
		
		TimecodeValue testValue = new TimecodeValueImpl(true, 0l, (short) 60);
		assertEquals("00:00:00;00.0", testValue.toString());
		
		testValue = new TimecodeValueImpl(true, 1l, (short) 60);
		assertEquals("00:00:00;00.1", testValue.toString());
		
		testValue = new TimecodeValueImpl(true, 2l, (short) 60);
		assertEquals("00:00:00;01.0", testValue.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFramePairOutOfRange() 
		throws ParseException, IllegalArgumentException {
		
		TimecodeValueImpl.parseTimecode("00:00:00;00.2");
	}
	
	@Test
	public void test50fpsRoundTrip() 
		throws ParseException, IllegalArgumentException {
		
		String testString = "10:11:12:13.1";
		TimecodeValue testValue = TimecodeValueImpl.parseTimecode(testString);
		assertEquals(testString, testValue.toString());
	}

	@Test
	public void test60fpsRoundTrip() 
		throws ParseException, IllegalArgumentException {
		
		String testString = "10:11:12;13.1";
		TimecodeValue testValue = TimecodeValueImpl.parseTimecode(testString);
		assertEquals(testString, testValue.toString());
	}

	@Test
	public void testDropFrameBoundaryCases60fps() {
		
		assertEquals("00:19:59;29.0", (new TimecodeValueImpl(true,71926, (short) 60)).toString());
		assertEquals("00:19:59;29.1", (new TimecodeValueImpl(true, 71927, (short) 60)).toString());
		assertEquals("00:20:00;00.0", (new TimecodeValueImpl(true, 71928, (short) 60)).toString());
		assertEquals("00:20:00;00.1", (new TimecodeValueImpl(true, 71929, (short) 60)).toString());
		
		assertEquals("00:18:59;29.0", (new TimecodeValueImpl(true, 68330, (short) 60)).toString());
		assertEquals("00:18:59;29.1", (new TimecodeValueImpl(true, 68331, (short) 60)).toString());
		assertEquals("00:19:00;02.0", (new TimecodeValueImpl(true, 68332, (short) 60)).toString());
		assertEquals("00:19:00;02.1", (new TimecodeValueImpl(true, 68333, (short) 60)).toString());
		assertEquals("00:19:00;03.0", (new TimecodeValueImpl(true, 68334, (short) 60)).toString());
	}
}
