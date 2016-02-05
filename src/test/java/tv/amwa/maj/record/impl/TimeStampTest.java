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
 * $Log: TimeStampTest.java,v $
 * Revision 1.2  2011/01/20 17:40:50  vizigoth
 * Fixed up all record tests to the point where they all pass.
 *
 * Revision 1.1  2011/01/20 11:15:09  vizigoth
 * Change of package name from embeddable to record.impl.
 *
 * Revision 1.3  2009/12/18 17:56:02  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.2  2008/10/15 09:46:53  vizigoth
 * Improvements alongside documentation improvements.
 *
 * Revision 1.1  2007/11/13 22:16:36  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:31  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

import tv.amwa.maj.record.TimeStamp;
import tv.amwa.maj.record.impl.DateStructImpl;
import tv.amwa.maj.record.impl.TimeStampImpl;
import tv.amwa.maj.record.impl.TimeStructImpl;


public class TimeStampTest {

	@Test public void constructFromNow() {
		
		TimeStampImpl testStamp = new TimeStampImpl();

		checkInSync(testStamp);
	}

	private void checkInSync(
			TimeStampImpl testStamp) {
		
		assertTrue( 
				testStamp.getTimeStamp().equals(
						((DateStructImpl) testStamp.getDate()).getDateValue()));
		assertTrue(
				testStamp.getTimeStamp().equals(
						((TimeStructImpl) testStamp.getTime()).getTimeValue()));
	}
	
	@Test public void setAndGetDate() {
		
		TimeStampImpl testStamp = new TimeStampImpl();
		testStamp.setDate(new DateStructImpl((byte) 2, (byte) 11, (short) 1972));

		tv.amwa.maj.record.DateStruct testDate = testStamp.getDate();
		assertEquals((byte) 2, testDate.getDay());
		assertEquals((byte) 11, testDate.getMonth());
		assertEquals((short) 1972, testDate.getYear());

		checkInSync(testStamp);
	}

	@Test public void setAndGetTime() {

		TimeStampImpl testStamp = new TimeStampImpl();
		testStamp.setTime(new TimeStructImpl((byte) 1, (byte) 2, (byte) 3, (byte) 4));
		
		tv.amwa.maj.record.TimeStruct testTime = testStamp.getTime();
		assertEquals((byte) 1, testTime.getHour());
		assertEquals((byte) 2, testTime.getMinute());
		assertEquals((byte) 3, testTime.getSecond());
		assertEquals((byte) 4, testTime.getFraction());

		checkInSync(testStamp);
	}

	@Test public void setAndGetTimeAndDate() {
		
		TimeStampImpl testStamp = new TimeStampImpl();
		testStamp.setStamp(
				new DateStructImpl((byte) 10, (byte) 11, (short) 1972), 
				new TimeStructImpl((byte) 1, (byte) 2, (byte) 3, (byte) 4));
			
		tv.amwa.maj.record.DateStruct testDate = testStamp.getDate();
		assertEquals((byte) 10, testDate.getDay());
		assertEquals((byte) 11, testDate.getMonth());
		assertEquals((short) 1972, testDate.getYear());
		
		tv.amwa.maj.record.TimeStruct testTime = testStamp.getTime();
		assertEquals((byte) 1, testTime.getHour());
		assertEquals((byte) 2, testTime.getMinute());
		assertEquals((byte) 3, testTime.getSecond());
		assertEquals((byte) 4, testTime.getFraction());		

		checkInSync(testStamp);
	}

	@Test public void constructFromDateTime() {
		
		TimeStampImpl testStamp = new TimeStampImpl(
				new DateStructImpl((byte) 10, (byte) 11, (short) 1972), 
				new TimeStructImpl((byte) 1, (byte) 2, (byte) 3, (byte) 4));
				
		tv.amwa.maj.record.DateStruct testDate = testStamp.getDate();
		assertEquals((byte) 10, testDate.getDay());
		assertEquals((byte) 11, testDate.getMonth());
		assertEquals((short) 1972, testDate.getYear());

		tv.amwa.maj.record.TimeStruct testTime = testStamp.getTime();
		assertEquals((byte) 1, testTime.getHour());
		assertEquals((byte) 2, testTime.getMinute());
		assertEquals((byte) 3, testTime.getSecond());
		assertEquals((byte) 4, testTime.getFraction());		

		checkInSync(testStamp);
}

	@Test public void constructFromCalendar() {
		
		GregorianCalendar testCalendar = new GregorianCalendar();
		testCalendar.clear();
		
		testCalendar.set(Calendar.YEAR, 1972);
		testCalendar.set(Calendar.MONTH, 10);
		testCalendar.set(Calendar.DAY_OF_MONTH, 10);
		testCalendar.set(Calendar.HOUR_OF_DAY, 1);
		testCalendar.set(Calendar.MINUTE, 2);
		testCalendar.set(Calendar.SECOND, 3);
		testCalendar.set(Calendar.MILLISECOND, 16);
		
		TimeStampImpl testStamp = new TimeStampImpl(testCalendar);
		
		tv.amwa.maj.record.DateStruct testDate = testStamp.getDate();
		assertEquals((byte) 10, testDate.getDay());
		assertEquals((byte) 11, testDate.getMonth());
		assertEquals((short) 1972, testDate.getYear());

		tv.amwa.maj.record.TimeStruct testTime = testStamp.getTime();
		assertEquals((byte) 1, testTime.getHour());
		assertEquals((byte) 2, testTime.getMinute());
		assertEquals((byte) 3, testTime.getSecond());
		assertEquals((byte) 4, testTime.getFraction());			

		checkInSync(testStamp);
	}

	@Test(expected=NullPointerException.class)
	public void constructFromNull() {
	
		@SuppressWarnings("unused")
		TimeStampImpl testStamp = new TimeStampImpl(null);
	}

	@Test(expected=NullPointerException.class)
	public void constructFromNulls1() {
		
		@SuppressWarnings("unused")
		TimeStampImpl testStamp = new TimeStampImpl(null, new TimeStructImpl());		
	}

	@Test(expected=NullPointerException.class)
	public void constructFromNulls2() {
		
		@SuppressWarnings("unused")
		TimeStampImpl testStamp = new TimeStampImpl(new DateStructImpl(), null);
	}

	@Test(expected=NullPointerException.class)
	public void constructFromNulls3() {
		
		@SuppressWarnings("unused")
		TimeStampImpl testStamp = new TimeStampImpl(null, null);		
	}

	@Test public void equalStamps() {
	
		TimeStampImpl testStamp1 = new TimeStampImpl();
		testStamp1.setStamp(
				new DateStructImpl((byte) 10, (byte) 11, (short) 1972), 
				new TimeStructImpl((byte) 1, (byte) 2, (byte) 3, (byte) 4));
		TimeStampImpl testStamp2 = new TimeStampImpl();
		testStamp2.setStamp(
				new DateStructImpl((byte) 10, (byte) 11, (short) 1972), 
				new TimeStructImpl((byte) 1, (byte) 2, (byte) 3, (byte) 4));
		
		assertEquals(false, testStamp1.equals(null));
		assertEquals(false, testStamp1.equals(new Object()));
		assertEquals(true, testStamp1.equals(testStamp2));
		assertEquals(true, testStamp2.equals(testStamp1));
		assertEquals(false, testStamp1.equals(new TimeStampImpl()));


		checkInSync(testStamp1);
		checkInSync(testStamp2);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void badDate1() {
		
		TimeStampImpl testStamp = new TimeStampImpl();
		testStamp.setStamp(
				new DateStructImpl((byte) 29, (byte) 2, (short) 2007), 
				new TimeStructImpl((byte) 1, (byte) 2, (byte) 3, (byte) 4));	
	}

	// Fixed test with timezone as it was only working in the UK!
	// TODO: Check for summer and winter timezone changes
	@Test public void timeStampFromString() 
		throws java.text.ParseException {
		
		TimeStampImpl testStamp = TimeStampImpl.parseFactory("1972-11-02T23:05:59.234+0200");
		
		assertEquals((short) 1972, testStamp.getDate().getYear());
		assertEquals((byte) 11, testStamp.getDate().getMonth());
		assertEquals((byte) 2, testStamp.getDate().getDay());
		
		TimeZone timezone = TimeZone.getDefault();
		int offset = (timezone.getRawOffset() / 3600000);
		
		assertEquals((byte) 21 + offset, testStamp.getTime().getHour());
		assertEquals((byte) 5, testStamp.getTime().getMinute());
		assertEquals((byte) 59, testStamp.getTime().getSecond());
		assertEquals((byte) 58, testStamp.getTime().getFraction());

		checkInSync(testStamp);
	}

	// Fixed test with timezone as it was only working in the UK!
	// TODO: Check for summer and winter timezone changes
	@Test public void timeStampFromStringZ() 
	throws java.text.ParseException {

		TimeStampImpl testStamp = TimeStampImpl.parseFactory("1972-11-02T23:05:59.23Z");

		assertEquals((short) 1972, testStamp.getDate().getYear());
		assertEquals((byte) 11, testStamp.getDate().getMonth());
		assertEquals((byte) 2, testStamp.getDate().getDay());

		TimeZone timezone = TimeZone.getDefault();
		int offset = (timezone.getRawOffset() / 3600000);
		
		assertEquals((byte) 23 + offset, testStamp.getTime().getHour());
		assertEquals((byte) 5, testStamp.getTime().getMinute());
		assertEquals((byte) 59, testStamp.getTime().getSecond());
		assertEquals((byte) 57, testStamp.getTime().getFraction());

		checkInSync(testStamp);
	}
	
	@Test
	public void timeStampFromStringNoFraction() 
		throws java.text.ParseException {
		
		TimeStampImpl testStamp = TimeStampImpl.parseFactory("2010-07-28T08:47:07Z");
		assertEquals("2010-07-28T08:47:07.00Z", testStamp.toString());
	}
	
	@Test(expected=NullPointerException.class)
	public void timeStampFromNullString() 
		throws java.text.ParseException {
		
		@SuppressWarnings("unused")
		TimeStampImpl testStamp = TimeStampImpl.parseFactory(null);
	}

	@Test(expected=java.text.ParseException.class)
	public void timeStampFromEmptyString() 
		throws java.text.ParseException {
		
		TimeStampImpl.parseFactory("");
	}

	@Test(expected=java.text.ParseException.class)
	public void timeStampFromJunk() 
		throws java.text.ParseException {
		
		TimeStampImpl.parseFactory("What a load of junk this is that should not parse.");
		TimeStampImpl.parseFactory("What a load of junk this is that should not pars.e");
		TimeStampImpl.parseFactory("What a load of junk this is that should not par.se");
		TimeStampImpl.parseFactory("What a load of junk this is that should not pa.rse");
		TimeStampImpl.parseFactory("What a load of junk this is that should not p.arse");
	}

	
	@Test(expected=java.text.ParseException.class)
	public void timeStampFromBadString() 
		throws java.text.ParseException {
		
		@SuppressWarnings("unused")
		TimeStampImpl testStamp = TimeStampImpl.parseFactory("1972-11-02T23:61:59.234+0200");
	}
	
	@Test public void stampToString() 
		throws java.text.ParseException {
		
		TimeStampImpl testStamp1 = TimeStampImpl.parseFactory("1972-11-02T23:05:59.234+0000");
		assertEquals("1972-11-02T23:05:59.23Z", testStamp1.toString());	
	}

	@Test public void cloneTest() 
		throws NullPointerException, ParseException {
		
		TimeStampImpl testStamp1 = TimeStampImpl.parseFactory("1972-11-02T23:05:59.234+0000");
		assertEquals((short) 1972, testStamp1.getDate().getYear());
		TimeStamp testStamp2 = testStamp1.clone();

		assertTrue(testStamp1.equals(testStamp2));
	}
	
	@Test public void cloneTestNotSame() 
		throws NullPointerException, ParseException {

		TimeStampImpl testStamp1 = TimeStampImpl.parseFactory("1972-11-02T23:05:59.234+0000");
		assertEquals((short) 1972, testStamp1.getDate().getYear());
		TimeStamp testStamp2 = testStamp1.clone();
		assertFalse(testStamp1 == testStamp2);
	}
	
	@Test public void setTimeStampExternalModification() 
		throws NullPointerException, ParseException {
		
		TimeStampImpl testStamp1 = TimeStampImpl.parseFactory("1972-11-02T23:05:59.234+0000");
		Calendar internalStamp = testStamp1.getTimeStamp();
		TimeStampImpl testStamp2 = TimeStampImpl.parseFactory("2007-11-02T23:05:59.234+0000");
		testStamp2.setTimeStamp(internalStamp);
		internalStamp.set(Calendar.YEAR, 2006);
		
		assertEquals((short) 1972, testStamp2.getDate().getYear());
	}

}

