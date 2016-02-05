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
 * $Log: DateStructTest.java,v $
 * Revision 1.2  2011/01/20 17:40:50  vizigoth
 * Fixed up all record tests to the point where they all pass.
 *
 * Revision 1.1  2011/01/20 11:15:09  vizigoth
 * Change of package name from embeddable to record.impl.
 *
 * Revision 1.2  2009/12/18 17:56:02  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2007/11/13 22:16:44  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:32  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.Ignore;


public class DateStructTest {

	@Test public void setAndGetDate1()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl((byte) 2, (byte) 11, (short) 1972);

		assertEquals((byte) 2, testDate.getDay());
		assertEquals((byte) 11, testDate.getMonth());
		assertEquals((short) 1972, testDate.getYear());
	}

	@Test public void setAndGetDate2()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl((byte) 29, (byte) 2, (short) 2008);

		assertEquals((byte) 29, testDate.getDay());
		assertEquals((byte) 2, testDate.getMonth());
		assertEquals((short) 2008, testDate.getYear());
	}

	@Test public void setAndGetDay()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl();
		testDate.setDay((byte) 2);
		assertEquals((byte) 2, testDate.getDay());
	}

	@Test public void setAndGetMonth()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl();
		testDate.setMonth((byte) 11);
		assertEquals((byte) 11, testDate.getMonth());
	}

	@Test public void setAndGetYear()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl();
		testDate.setYear((short) 1972);
		assertEquals((short) 1972, testDate.getYear());
	}

	@Test(expected=IllegalArgumentException.class)
	public void badDayHigh()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl();
		testDate.setDay((byte) 32);
	}

	@Test(expected=IllegalArgumentException.class)
	public void badDayLow()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl();
		testDate.setDay((byte) 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void badMonthHigh()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl();
		testDate.setMonth((byte) 13);
	}

	@Test(expected=IllegalArgumentException.class)
	public void badMonthLow()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl();
		testDate.setMonth((byte) 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void badDate1()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl();
		testDate.setDate((byte) 29, (byte) 2, (short) 2007);
	}

  // TODO fix this test
	@Ignore @Test(expected=IllegalArgumentException.class)
	public void badDate2()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl();
		testDate.setDay((byte) 29);
		testDate.setMonth((byte) 2);
		testDate.setYear((short) 2007);
	}

	@Test public void beforeChrist()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl((byte) 1, (byte) 1, (short) 1970);
		testDate.setYear((short) -9999);
		assertEquals((short) -9999, testDate.getYear());

		assertEquals("-9999-01-01+0000", testDate.toString());
	}

	@Test public void yearDot1()
		throws IllegalArgumentException {

		DateStructImpl testDate = new DateStructImpl((byte) 1, (byte) 1, (short) 1970);
		testDate.setYear((short) 0);
		assertEquals((short) 0, testDate.getYear());

		assertEquals("0000-01-01+0000", testDate.toString());
	}

	@Test public void yearDot2()
		throws IllegalArgumentException {

		GregorianCalendar testCalendar =
			new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		testCalendar.set(Calendar.ERA, GregorianCalendar.BC);
		testCalendar.set(1, 0, 1);

		DateStructImpl testDate = new DateStructImpl(testCalendar);
		assertEquals((short) 0, testDate.getYear());

		assertEquals("0000-01-01+0000", testDate.toString());
	}

	@Test public void stringFormatter() {

		GregorianCalendar testCalendar =
			new GregorianCalendar(TimeZone.getTimeZone("Australia/Adelaide"));
		testCalendar.clear();
		testCalendar.set(Calendar.YEAR, 1972);
		testCalendar.set(Calendar.MONTH, 10);
		testCalendar.set(Calendar.DAY_OF_MONTH, 2);

		DateStructImpl testDate = new DateStructImpl(testCalendar);
		assertEquals("1972-11-02+0930", testDate.toString());
	}

	@Test public void epoch() {

		GregorianCalendar testCalendar =
			new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		testCalendar.clear();
		Date epochValue = new Date(0l);
		testCalendar.setTime(epochValue);

		DateStructImpl testDate = new DateStructImpl(testCalendar);
		assertEquals((byte) 1, testDate.getDay());
		assertEquals((byte) 1, testDate.getMonth());
		assertEquals((short) 1970, testDate.getYear());
	}

	@Test public void equalDates() {

		DateStructImpl testDate = new DateStructImpl();
		assertEquals(false, testDate.equals(null));
		assertEquals(false, testDate.equals(new Object()));

		DateStructImpl epoch = new DateStructImpl((byte) 1, (byte) 1, (short) 1970);
		assertEquals(false, testDate.equals(epoch));

		DateStructImpl theSame = new DateStructImpl(testDate.getDay(), testDate.getMonth(),
				testDate.getYear());
		assertEquals(true, testDate.equals(theSame));

		Calendar testCalendar = theSame.getDateValue();
		testCalendar.clear(Calendar.ZONE_OFFSET);
		testCalendar.set(Calendar.ZONE_OFFSET, -8 * 60 * 60 * 1000);
		theSame.setDateValue(testCalendar);
		assertEquals(true, testDate.equals(theSame));
	}

	@Test public void stringToDateStruct()
		throws java.text.ParseException, IllegalArgumentException, NullPointerException {

		DateStructImpl testDate = DateStructImpl.parseFactory("1972-11-02");
		DateStructImpl direct = new DateStructImpl((byte) 2, (byte) 11, (short) 1972);
		assertEquals(true, direct.equals(testDate));
	}

	@Test public void stringToDateStructMinimum()
		throws java.text.ParseException, IllegalArgumentException, NullPointerException {

		DateStructImpl testDate = DateStructImpl.parseFactory("0001-01-01");
		DateStructImpl direct = new DateStructImpl((byte) 1, (byte) 1, (short) 1);
		assertEquals(true, direct.equals(testDate));
	}

	@Test(expected=java.text.ParseException.class)
	public void stringToDateEmpty()
		throws java.text.ParseException, IllegalArgumentException, NullPointerException {

		@SuppressWarnings("unused")
		DateStructImpl testDate = DateStructImpl.parseFactory("");
	}

	@Test(expected=java.text.ParseException.class)
	public void stringToDateBadValue1()
		throws java.text.ParseException, IllegalArgumentException, NullPointerException {

		@SuppressWarnings("unused")
		DateStructImpl testDate = DateStructImpl.parseFactory("2007-02-29");
	}

	@Test(expected=java.text.ParseException.class)
	public void stringToDateBadValue2()
		throws java.text.ParseException, IllegalArgumentException, NullPointerException {

		@SuppressWarnings("unused")
		DateStructImpl testDate = DateStructImpl.parseFactory("2007-13-03");
	}

	@Test(expected=java.text.ParseException.class)
	public void stringToDateBadValue3()
		throws java.text.ParseException, IllegalArgumentException, NullPointerException {

		@SuppressWarnings("unused")
		DateStructImpl testDate = DateStructImpl.parseFactory("2007-11-31");
	}

	@Test(expected=IllegalArgumentException.class)
	public void stringToDateBadValue4()
		throws java.text.ParseException, IllegalArgumentException, NullPointerException {

		@SuppressWarnings("unused")
		DateStructImpl testDate = DateStructImpl.parseFactory("65537-12-03");
	}



	@Test(expected=NullPointerException.class)
	public void creationFromNull() {

		@SuppressWarnings("unused")
		DateStructImpl testDate = new DateStructImpl(null);
	}

	@Test(expected=NullPointerException.class)
	public void settingFromNull() {

		DateStructImpl testDate = new DateStructImpl();
		testDate.setDateValue(null);
	}

	@Test(expected=NullPointerException.class)
	public void toDateStructWithNullString()
		throws java.text.ParseException, IllegalArgumentException, NullPointerException{

		@SuppressWarnings("unused")
		DateStructImpl testDate = DateStructImpl.parseFactory(null);
	}
}
