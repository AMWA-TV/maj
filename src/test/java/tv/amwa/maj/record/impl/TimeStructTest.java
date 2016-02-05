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
 * $Log: TimeStructTest.java,v $
 * Revision 1.2  2011/01/20 17:40:50  vizigoth
 * Fixed up all record tests to the point where they all pass.
 *
 * Revision 1.1  2011/01/20 11:15:09  vizigoth
 * Change of package name from embeddable to record.impl.
 *
 * Revision 1.2  2009/12/18 17:56:02  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2007/11/13 22:16:41  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:32  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import tv.amwa.maj.record.impl.TimeStructImpl;



public class TimeStructTest {

	@Test public void timeStructToString() {
		
		TimeStructImpl testTime = new TimeStructImpl((byte) 12, (byte) 9, (byte) 53, (byte) 47);
		assertEquals(true, testTime.toString().startsWith("12:09:53.188"));
	}

	@Test public void creationFromNow() {
		
		@SuppressWarnings("unused")
		TimeStructImpl testTime = new TimeStructImpl();	
	}
	
	@Test public void getAndSetFraction() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setFraction((byte) 72);
		assertEquals((byte) 72, testTime.getFraction());
		
		Calendar testCalendar = testTime.getTimeValue();
		assertEquals(288, testCalendar.get(Calendar.MILLISECOND));
	}

	@Test public void getAndSetHour() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setHour((byte) 23);
		assertEquals((byte) 23, testTime.getHour());
		
		Calendar testCalendar = testTime.getTimeValue();
		assertEquals(23, testCalendar.get(Calendar.HOUR_OF_DAY));
	}
	
	@Test public void getAndSetMinute() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setMinute((byte) 59);
		assertEquals((byte) 59, testTime.getMinute());
		
		Calendar testCalendar = testTime.getTimeValue();
		assertEquals(59, testCalendar.get(Calendar.MINUTE));
	}
	
	@Test public void getAndSetSecond() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setSecond((byte) 34);
		assertEquals((byte) 34, testTime.getSecond());
		
		Calendar testCalendar = testTime.getTimeValue();
		assertEquals(34, testCalendar.get(Calendar.SECOND));
	}

	@Test public void constructTimeFromParts() {
		TimeStructImpl testTime = new TimeStructImpl((byte) 1, (byte) 2, (byte) 3, (byte) 4);
		assertEquals((byte) 1, testTime.getHour());
		assertEquals((byte) 2, testTime.getMinute());
		assertEquals((byte) 3, testTime.getSecond());
		assertEquals((byte) 4, testTime.getFraction());
		
		Calendar testCalendar = testTime.getTimeValue();
		assertEquals(16, testCalendar.get(Calendar.MILLISECOND));
	}

	@Test public void timeMinimum() {
		TimeStructImpl testTime = new TimeStructImpl((byte) 0, (byte) 0, (byte) 0, (byte) 0);
		assertEquals((byte) 0, testTime.getHour());
		assertEquals((byte) 0, testTime.getMinute());
		assertEquals((byte) 0, testTime.getSecond());
		assertEquals((byte) 0, testTime.getFraction());
	}
	
	@Test public void timeMaximum() {
		TimeStructImpl testTime = new TimeStructImpl((byte) 23, (byte) 59, (byte) 59, (byte) 99);
		assertEquals((byte) 23, testTime.getHour());
		assertEquals((byte) 59, testTime.getMinute());
		assertEquals((byte) 59, testTime.getSecond());
		assertEquals((byte) 99, testTime.getFraction());
	}
		
	@Test(expected=IllegalArgumentException.class) 
	public void badHourLow() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setHour((byte) -1);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void badHourHigh() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setHour((byte) 24);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void midnight24() {

		@SuppressWarnings("unused")
		TimeStructImpl testTime = new TimeStructImpl((byte) 24, (byte) 0, (byte) 0, (byte) 0);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void badMinuteLow() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setMinute((byte) -1);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void badMinuteHigh() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setMinute((byte) 60);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void badSecondLow() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setSecond((byte) -1);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void badSecondHigh() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setSecond((byte) 60);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void badFractionLow() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setFraction((byte) -1);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void badFractionHigh() {
		TimeStructImpl testTime = new TimeStructImpl();
		testTime.setFraction((byte) 250);
	}

	@Test public void timeEquals() {
		
		TimeStructImpl testTime1 = new TimeStructImpl((byte) 1, (byte) 2, (byte) 3, (byte) 4);
		TimeStructImpl testTime2 = new TimeStructImpl((byte) 1, (byte) 2, (byte) 3, (byte) 4);
		
		assertEquals(false, testTime1.equals(null));
		assertEquals(false, testTime2.equals(new Object()));
		assertEquals(true, testTime1.equals(testTime2));
		
		testTime2.setHour((byte) 5);
		assertEquals(false, testTime1.equals(testTime2));
	}
	
	@Test(expected=NullPointerException.class)
	public void creationFromNull() {
		
		@SuppressWarnings("unused")
		TimeStructImpl testTime = new TimeStructImpl(null);
	}

	@Test public void stringToTime() 
		throws java.text.ParseException, NullPointerException {
		TimeStructImpl testTime = TimeStructImpl.parseFactory("03:33:57.239+0930");

		// Values will become UTC
		assertEquals((byte) 18, testTime.getHour());
		assertEquals((byte) 3, testTime.getMinute());
		assertEquals((byte) 57, testTime.getSecond());
		assertEquals((byte) 59, testTime.getFraction());

		//Printed value will be according to local time zone
		//System.out.println(testTime.toString());
	}
	
	@Test(expected=java.text.ParseException.class)
	public void badStringToTime() 
		throws java.text.ParseException, NullPointerException {
		
		@SuppressWarnings("unused")
		TimeStructImpl testTime = TimeStructImpl.parseFactory("03-33:57.239+0930");
	}
	
	@Test(expected=NullPointerException.class)
	public void stringToTimeNull()  
	throws java.text.ParseException, NullPointerException {
		
		@SuppressWarnings("unused")
		TimeStructImpl testTime = TimeStructImpl.parseFactory(null);
	}

	@Test(expected=java.text.ParseException.class)
	public void stringToTimeEmpty()  
	throws java.text.ParseException, NullPointerException, IllegalArgumentException {
		
		@SuppressWarnings("unused")
		TimeStructImpl testTime = TimeStructImpl.parseFactory("");
	}

	@Test public void constructFromCalendar() {

		GregorianCalendar testCalendar = new GregorianCalendar();
		testCalendar.clear();
		testCalendar.set(Calendar.HOUR_OF_DAY, 21);
		testCalendar.set(Calendar.MINUTE, 45);
		testCalendar.set(Calendar.SECOND, 17);
		testCalendar.set(Calendar.MILLISECOND, 970);
		
		TimeStructImpl testTime = new TimeStructImpl(testCalendar);
		assertEquals((byte) 21, testTime.getHour());
		assertEquals((byte) 45, testTime.getMinute());
		assertEquals((byte) 17, testTime.getSecond());
		assertEquals((byte) 242, testTime.getFraction());
	}
	
	@Test public void hashcodeGeneration() {
		
		TimeStructImpl testTime = new TimeStructImpl((byte) 1, (byte) 2, (byte) 3, (byte) 4);
		int hash = testTime.hashCode();
		String hashString = Integer.toHexString(hash);
		assertEquals("1020304", hashString);
	}

	@Test public void getDateValue() {
		
		TimeStructImpl testTime = new TimeStructImpl((byte) 18, (byte) 2, (byte) 3, (byte) 4);
		Calendar testCalendar = testTime.getTimeValue();
		
		assertEquals(18, testCalendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(2, testCalendar.get(Calendar.MINUTE));
		assertEquals(3, testCalendar.get(Calendar.SECOND));
		assertEquals(16, testCalendar.get(Calendar.MILLISECOND));
	}
	
	@Test
	public final void testSetFractionBigger() {
		
		TimeStructImpl testTime = new TimeStructImpl((byte) 18, (byte) 2, (byte) 3, (byte) 4);
		testTime.setFraction((byte) 129);
		
		assertEquals((byte) 129, testTime.getFraction());
	}
}
