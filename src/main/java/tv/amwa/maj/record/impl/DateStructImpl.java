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
 * $Log: DateStructImpl.java,v $
 * Revision 1.3  2011/01/20 17:41:19  vizigoth
 * Fixed up all meta tests to the point where they all pass.
 *
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/06/02 13:28:46  vizigoth
 * Fixed problem where setting the date in year, month and then day order on the 29th Feb would have caused an error.
 *
 * Revision 1.3  2010/06/02 12:11:12  vizigoth
 * Fixed problem where setting the month before the day on the 31st of the month throws an exception.
 *
 * Revision 1.2  2009/12/18 17:56:03  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/10/15 09:45:54  vizigoth
 * Edited documentation to release standard.
 *
 * Revision 1.3  2008/03/07 08:08:11  vizigoth
 * Edited comments to release standard.
 *
 * Revision 1.2  2008/01/14 20:41:27  vizigoth
 * Fixed incorrect use of BadParameterException.
 *
 * Revision 1.1  2007/11/13 22:14:37  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.integer.Int16;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.record.DateStruct;
import tv.amwa.maj.util.Utilities;

// TODO tests for 29th Feb and 31st of month as the default value ... dependency on the order values are set

/**
 * <p>Implementation of the date component of {@linkplain TimeStampImpl timestamp values} that are specified
 * according to Coordinated Universal Time (UTC). AAF dates represent dates with year, month and day
 * of the month components.</p>
 *
 * <p>As specified, AAF dates do not represent the timezone associated with a date and in this
 * sense are not fully compatible with UTC dates. Internally, this class uses the
 * {@link java.util.Calendar} class with its support for internationalization so
 * timezone is represented when provided through methods such as {@link #getDateValue()}.
 * Also, this class restricts the acceptable range of values to acceptable values, which
 * includes checking for leap years and unacceptable dates such as 31st February.</p>
 *
 * <p>A date structure is represented in the database by a column of type date:</p>
 *
 * <pre>
 *     `Date` date
 * </pre>
 *
 * @see TimeStampImpl
 * @see TimeStructImpl
 * @see tv.amwa.maj.industry.TypeDefinitions#DateStruct
 *
 *
 *
 */

public final class DateStructImpl
	implements DateStruct,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = -20078426390114363L;

	/** Gregorian calendar value providing date storage. */
	private Calendar dateValue;

	/** Variable required to see if the day has been set yet, required to determine whether date
	 * validation should be carried out. */
	private boolean dayHasBeenSet = false;

	/** Variable required to see if the month has been set yet, required to determine whether date
	 * validation should be carried out. */
	private boolean monthHasBeenSet = false;

	/** Date formatter, for parsing and writing UTC-style dates. */
	private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * <p>Create a new date value using the given year, month and day of the month values.</p>
	 *
	 * @param day Day component of the date.
	 * @param month Month component of the date.
	 * @param year Year component of the date.
	 *
	 * @throws IllegalArgumentException One or more of the arguments has a value outside of
	 * an acceptable range.
	 */
    public DateStructImpl(
    		@UInt8 byte day,
    		@UInt8 byte month,
    		@Int16 short year)
    	throws IllegalArgumentException {

    	dateValue = new GregorianCalendar();
    	dateValue.setLenient(false);

    	setDate(day, month, year);
	}

    public void setDate(
    		@UInt8 byte day,
    		@UInt8 byte month,
    		@Int16 short year)
    	throws IllegalArgumentException {

    	dateValue.clear();
    	dateValue.set((int) year, ((int) month) - 1, (int) day);

    	dayHasBeenSet = true;
    	monthHasBeenSet = true;

		// Force test of the validity of the new date.
    	dateValue.getTime();
    }

    /**
     * <p>Create a new date structure from a {@link java.util.Calendar} value.</p>
     *
     * <p>Note that the newly created date structure may not store the timezone and other
     * locale information encoded in the original calendar value.</p>
     *
     * @param dateItem Calendar value to use to extract a day, month and year from.
     *
     * @throws NullPointerException Argument is null.
     */
    public DateStructImpl(
    		Calendar dateItem)
    	throws NullPointerException,
    		IllegalArgumentException {

    	setDateValue(dateItem);
    }

    /**
     * <p>Create a new date structure for the current date.</p>
     */
    public DateStructImpl() {
    	dateValue = new GregorianCalendar();
    	dateValue.setLenient(false);
    }

	public final @UInt8 byte getDay() {
		return (byte) dateValue.get(Calendar.DAY_OF_MONTH);
	}

	public final void setDay(
				@UInt8 byte day)
		throws IllegalArgumentException {

		if ((day < 1) || (day > 31))
			throw new IllegalArgumentException("The day of the month must be in the range 1 to 31.");

		dateValue.clear(Calendar.DAY_OF_MONTH);
		dateValue.set(Calendar.DAY_OF_MONTH, day);

		dayHasBeenSet = true;

		// Force test of the validity of the new date.
		if (monthHasBeenSet)
			dateValue.getTime();
	}

	public final @UInt8 byte getMonth() {
		return (byte) (dateValue.get(Calendar.MONTH) + 1);
	}

	public final void setMonth(
			@UInt8 byte month)
		throws IllegalArgumentException {

		if ((month < 1) || (month > 12))
			throw new IllegalArgumentException("The month must lie in the range 1 to 12.");

		dateValue.clear(Calendar.MONTH);
		dateValue.set(Calendar.MONTH, ((int) month) - 1);

		monthHasBeenSet = true;

		// Force test of the validity of the new date.
		if (dayHasBeenSet)
			dateValue.getTime();
	}

	public final @Int16 short getYear() {

		int year = dateValue.get(Calendar.YEAR);
		if (dateValue.get(Calendar.ERA) == GregorianCalendar.BC)
			year = 1 - year;
		return (short) year;
	}


	public final void setYear(
			@Int16 short year)
		throws IllegalArgumentException {

		// Uncomment to test what happens on 29th Feb in a leap year
//		dateValue.set(Calendar.DAY_OF_MONTH, 29);
//		dateValue.set(Calendar.MONTH, Calendar.FEBRUARY);
//		dateValue.set(Calendar.YEAR, 2008);

		int dayOfMonth = dateValue.get(Calendar.DAY_OF_MONTH);
		int month = dateValue.get(Calendar.MONTH);

		dateValue.clear(Calendar.YEAR);
		if (year <= 0) {
			year = (short) (1 - year);
			dateValue.set(Calendar.ERA, GregorianCalendar.BC);
		}
		dateValue.set(Calendar.YEAR, year);


	  dateValue.getTime();
	}

	/**
	 * <p>Returns a copy of the {@link java.util.Calendar} value representing the
	 * day given by this date.</p>
	 *
	 * @return Java Calendar representation of this date structure, to the current default
	 * timezone and locale settings.
	 */
	public final Calendar getDateValue() {

		return (Calendar) dateValue.clone();
	}

	/**
	 * <p>Set the {@link java.util.Calendar} value used internally to represent this
	 * date value.</p>
	 *
	 * @param dateItem Calendar value to use to set the date.
	 * @throws NullPointerException The given calendar value is <code>null</code>.
	 */
	public final void setDateValue(
			Calendar dateItem)
		throws NullPointerException,
			IllegalArgumentException {

    	if (dateItem == null)
    		throw new NullPointerException("Cannot create a date structure from a null value.");
		dateItem.setLenient(false);

  		// Force test of the validity of the new date.
    	dateItem.getTime();

    	dateValue = (Calendar) dateItem.clone();
    }

	/**
	 * <p>Formats the date value as a UTC date-only value. The format is
	 * "<code>yyyy-mm-dd</code>", the year followed by the month followed by
	 * the day of the month. Note that locale and timezone are not available
	 * in a date structure.</p>
	 *
	 * @return Date value formatted as a UTC-style string without a timezone.
	 */
	public final String toString() {

		StringBuffer sb = new StringBuffer();

		String value = Short.toString(getYear());
		if (value.charAt(0) == '-') {
			sb.append('-');
			value = value.substring(1);
		}

		for ( int u = value.length() ; u < 4 ; u++ )
			sb.append('0');
		sb.append(value);

		sb.append('-');

		value = Byte.toString(getMonth());
		if (value.length() == 1)
			sb.append('0');
		sb.append(value);

		sb.append('-');

		value = Byte.toString(getDay());
		if (value.length() == 1)
			sb.append('0');
		sb.append(value);

		int offset = dateValue.get(Calendar.ZONE_OFFSET) / (60 * 1000);
		int hourOffset = offset / 60;
		int minuteOffset = offset % 60;

		value = Integer.toString(hourOffset);
		if (value.charAt(0) == '-') {
			sb.append('-');
			value = value.substring(1);
		}
		else
			sb.append('+');
		if (value.length() == 1)
			sb.append('0');
		sb.append(value);

		value = Integer.toString(minuteOffset);
		if (value.length() == 1)
			sb.append('0');
		sb.append(value);

		return sb.toString();
	}

	/**
	 * <p>Create a new date structure value from a string representation of a date. The date
	 * should be represented as the UTC portion of a date in the format
	 * "<code>yyyy-mm-dd</code>".</p>
	 *
	 * <p>Note that this method does not support the specification of timezone or
	 * dates earlier than 1AD.</p>
	 *
	 * @param date Date value represented as a string.
	 * @return Newly created date structure taken from the given string.
	 *
	 * @throws ParseException Given string value cannot be parsed into a date structure as it
	 * is in the wrong format.
	 * @throws NullPointerException The given string representation of a date is <code>null</code>.
	 * @throws IllegalArgumentException After successful parsing, the given date is not an
	 * acceptable value.
	 */

	public final static DateStructImpl parseFactory(
			String date)
		throws java.text.ParseException,
			NullPointerException,
			IllegalArgumentException {

		if (date == null)
			throw new NullPointerException("Cannot create a date value from a null value.");

		formatter.setLenient(false);
		Date parsedDate = formatter.parse(date);

		Calendar calendar = new GregorianCalendar();
		calendar.clear();
		calendar.setTime(parsedDate);

		if (calendar.get(Calendar.YEAR) <= Short.MIN_VALUE)
			throw new IllegalArgumentException("Year cannot be less than the minimum value for a Java short.");
		if (calendar.get(Calendar.YEAR) > Short.MAX_VALUE)
			throw new IllegalArgumentException("Year cannot be greater than the maximum value for a Java short.");

		return new DateStructImpl(calendar);
	}

	/**
	 * <p>Tests to see if this date structure has an equal value to the date structure
	 * passed in. Comparison is done based on year, month and day of month values only to
	 * remain compatible with the fields actually represented by AAF.</p>
	 */
	public final boolean equals(
			Object o) {

		if (o == null) return false;
		if (!(o instanceof DateStruct)) return false;

		DateStruct testDate =
			(DateStruct) o;

		if (getDay() != testDate.getDay()) return false;
		if (getMonth() != testDate.getMonth()) return false;
		if (getYear() != testDate.getYear()) return false;

		return true;
	}

	public final int hashCode() {
		return getYear() + 10000 * getMonth() + 1000000 * getDay();
	}

	public final DateStruct clone() {

		try {
			DateStructImpl cloned = (DateStructImpl) super.clone();
			cloned.setDateValue(dateValue);
			return cloned;
		}
		catch (CloneNotSupportedException cnse) {
			cnse.printStackTrace();
			return null;
		}
	}

	// TODO documentation on these special methods
	public final static void generateEmbeddableORM(
			Node parent,
			String namespace,
			String prefix) {

		Element embeddable = XMLBuilder.createChild(parent, namespace, prefix, "embeddable");
		XMLBuilder.setAttribute(embeddable, namespace, prefix, "class", DateStructImpl.class.getCanonicalName());
		XMLBuilder.setAttribute(embeddable, namespace, prefix, "access", "FIELD");

		Element embeddableAttributes = XMLBuilder.createChild(embeddable, namespace, prefix, "attributes");

		Element basic = XMLBuilder.createChild(embeddableAttributes, namespace, prefix, "basic");
		XMLBuilder.setAttribute(basic, namespace, prefix, "name", "dateValue");
		XMLBuilder.appendElement(basic, namespace, prefix, "temporal", "DATE");
	}

	public final static void generateEmbeddedORM(
			Node parent,
			String ownerName,
			String namespace,
			String prefix) {

		Element embedded = XMLBuilder.createChild(parent, namespace, prefix, "embedded");
		XMLBuilder.setAttribute(embedded, namespace, prefix, "name", Utilities.lowerFirstLetter(ownerName));

		Element attributeOverride = XMLBuilder.createChild(embedded, namespace, prefix, "attribute-override");
		XMLBuilder.setAttribute(attributeOverride, namespace, prefix, "name", "dateValue");
		Element column = XMLBuilder.createChild(attributeOverride, namespace, prefix, "column");
		XMLBuilder.setAttribute(column, namespace, prefix, "name", ownerName);
	}
}
