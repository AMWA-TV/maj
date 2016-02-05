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
 * $Log: TimeStructImpl.java,v $
 * Revision 1.6  2011/07/27 17:21:29  vizigoth
 * Fractions of seconds now measured in 1/250ths.
 *
 * Revision 1.5  2011/01/20 17:41:19  vizigoth
 * Fixed up all meta tests to the point where they all pass.
 *
 * Revision 1.4  2011/01/19 11:36:06  vizigoth
 * Unified approach to working with timezones for parsing time structs and time stamps.
 *
 * Revision 1.3  2011/01/19 11:32:17  vizigoth
 * Added better parsing of values that compensates for timezone differences.
 *
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:56:03  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/15 09:45:54  vizigoth
 * Edited documentation to release standard.
 *
 * Revision 1.1  2007/11/13 22:14:35  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:26  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.record.TimeStamp;
import tv.amwa.maj.record.TimeStruct;
import tv.amwa.maj.util.Utilities;

// TODO tests

/** 
 * <p>Implementation of the time component of date and time values specified according to 
 * Coordinated Universal Time (UTC), including hour, minute, second and 1/250th of a second. 
 * The time structure is itself a component of a {@linkplain TimeStamp timestamp}.</p>
 *  
 * <p>This implementation is backed by a {@link java.util.Calendar}.</p>
 * 
 * @see DateStructImpl
 * @see TimeStamp
 * @see tv.amwa.maj.industry.TypeDefinitions#TimeStruct
 * 
 *
 *
 */

public final class TimeStructImpl 
	implements TimeStruct, 
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7172172051477239720L;

	/** Internal representation of the time. */
	private Calendar timeValue;
	
	/** Date formatter, for parsing and writing UTC-style times. */
	private final static SimpleDateFormat formatter = 
		new SimpleDateFormat("HH:mm:ss.SSSZ");
	
	static {
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
	}
	
    /** 
     * <p>Create a time value from all of its component parts. </p>
     * 
	 * @param hour Hour component of the time value, in the inclusive range 0 to 23.
	 * @param minute Minute component of the time value, in the inclusive range 0 to 59.
	 * @param second Second component of the time value, normally in the inclusive range 0 to 59.
	 * @param fraction 1/250th of a second component of the time value, in the inclusive range
	 * 0&nbsp;to&nbsp;249.
	 * 
	 * @throws IllegalArgumentException One or more of the parameters is out of range.
	 */
	public TimeStructImpl(
			byte hour, 
			byte minute, 
			byte second, 
			byte fraction) 
		throws IllegalArgumentException {

		timeValue = new GregorianCalendar();
		timeValue.setTimeZone(formatter.getTimeZone());
		timeValue.setLenient(false);
		
		setTime(hour, minute, second, fraction);
	}

    /** 
     * <p>Create a time value from  its hour, minute and second component parts. The
     * fraction of a second is set to 0.</p>
     * 
	 * @param hour Hour component of the time value, in the inclusive range 0 to 23.
	 * @param minute Minute component of the time value, in the inclusive range 0 to 59.
	 * @param second Second component of the time value, normally in the inclusive range 0 to 59.
	 * 
	 * @throws IllegalArgumentException One or more of the parameters is out of range.
	 */
	public TimeStructImpl(
			byte hour, 
			byte minute, 
			byte second) 
		throws IllegalArgumentException {

		timeValue = new GregorianCalendar();
		timeValue.setLenient(false);

		setTime(hour, minute, second, (byte) 0);
	}
	
	/**
	 * <p>Create a new time structure from the given calendar value.</p>
	 * 
	 * @param timeItem Calendar item to use to create a new time structure.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws IllegalArgumentException The given calendar value contains out-of-range
	 * values.
	 */
	public TimeStructImpl(
			Calendar timeItem) 
		throws NullPointerException, 
			IllegalArgumentException {
		
		setTimeValue(timeItem);
	}
	
	/**
	 * <p>Create a new time structure set to the current time.</p>
	 */
	public TimeStructImpl() {
		
		timeValue = new GregorianCalendar();
		timeValue.setLenient(false);
	}

	public final byte getFraction() {
		return (byte) (timeValue.get(Calendar.MILLISECOND) / 4);
	}

	public final void setFraction(
			byte fraction) 
		throws IllegalArgumentException {

//		if ((fraction < 0) || (fraction > 99)) 
//			throw new IllegalArgumentException("The fraction of a second value must be in the range 0 to 99.");
//		
		timeValue.clear(Calendar.MILLISECOND);
		int positiveFraction = (fraction >= 0) ? fraction : 256 + fraction;
		timeValue.set(Calendar.MILLISECOND, positiveFraction * 4);
		
		// Test value
		timeValue.getTime();
	}

	public final byte getHour() {
		return (byte) timeValue.get(Calendar.HOUR_OF_DAY);
	}

	public final void setHour(byte hour) 
		throws IllegalArgumentException {

		timeValue.clear(Calendar.HOUR_OF_DAY);
		timeValue.set(Calendar.HOUR_OF_DAY, (int) hour);

		// Test value
		timeValue.getTime();
	}

	public final byte getMinute() {
		return (byte) timeValue.get(Calendar.MINUTE);
	}

	public final void setMinute(byte minute) 
		throws IllegalArgumentException {

		timeValue.clear(Calendar.MINUTE);
		timeValue.set(Calendar.MINUTE, (int) minute);
		
		// Test value
		timeValue.getTime();
	}

	public final byte getSecond() {
		return (byte) timeValue.get(Calendar.SECOND);
	}

	public final void setSecond(byte second) 
		throws IllegalArgumentException {
		
		timeValue.clear(Calendar.SECOND);
		timeValue.set(Calendar.SECOND, (int) second);
		
		// Test newly set value
		timeValue.getTime();
	}

	public final void setTime(byte hour, byte minute, byte second)
			throws IllegalArgumentException {

		setTime(hour, minute, second, (byte) 0);
	}

	public final void setTime(byte hour, byte minute, byte second, byte fraction)
			throws IllegalArgumentException {

		timeValue.clear();
		timeValue.set(Calendar.HOUR_OF_DAY, (int) hour);
		timeValue.set(Calendar.MINUTE, (int) minute);
		timeValue.set(Calendar.SECOND, (int) second);
		timeValue.set(Calendar.MILLISECOND, ((int) fraction) * 4);
		
		// Test newly set value
		timeValue.getTime();
	}    

	/**
	 * <p>Sets the value of the time structure from the given calendar value.</p>
	 * 
	 * @param timeItem Calendar value to set for this time structure.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws IllegalArgumentException The given calendar value contains one or more fields
	 * that are out of range.
	 */
	public final void setTimeValue(Calendar timeItem) 
		throws NullPointerException, IllegalArgumentException {
		
		if (timeItem == null)
			throw new NullPointerException("Cannot set time value from a null value.");
		
		timeItem.setLenient(false);

		// Test time value is valid
		timeItem.getTime();

		timeValue = timeItem;
	}
	
	/**
	 * <p>Returns a copy of the calendar value used to represent the time value.</p>
	 * 
	 * @return Copy of the calendar value used to represent this time structure.
	 */
	public final Calendar getTimeValue() {
		return (Calendar) timeValue.clone();
	}

	/** 
	 * <p>Formats a UTC-style string representation of the time structure's value, including the 
	 * timezone offset. The format used is "<code>HH:mm:ss.SSSZ</code>", where:</p>
	 * 
	 * <ul>
	 *  <li>"HH" is the two digit hour of the day in 24-hour clock;</li>
	 *  <li>"mm" is the two digit minute;</li>
	 *  <li>"ss" is the two digit second;</li>
	 *  <li>"SSS" is the three digital millisecond value;</li>
	 *  <li>"Z" is the timezone offset from UTC, measure in positive or negative hours 
	 *  and minutes, e.g. "+0000" for GMT and "-0800" for PST.</li>
	 * </ul>
	 * 
	 * @return String representation of the time value according to the local time zone.
	 * 
	 * @see java.lang.Object#toString()
	 * @see java.text.SimpleDateFormat
	 */
	public final String toString() {
		
		return formatter.format(timeValue.getTime());
	}
	
	/** 
	 * <p>Tests to see if the value of two time structures are equal. Two time values are
	 * equal if and only each of their hour, minute, second and fraction of a second values
	 * are equal respectively.</p>
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public final boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof TimeStruct)) return false;
		
		TimeStruct testTime = 
			(TimeStruct) o;
		if (getHour() != testTime.getHour()) return false;
		if (getMinute() != testTime.getMinute()) return false;
		if (getSecond() != testTime.getSecond()) return false;
		if (getFraction() != testTime.getFraction()) return false;
		
		return true;
	}
	
	public final int hashCode() {
		return (getHour() << 24) | (getMinute() << 16 ) | 
			(getSecond() << 8) | getFraction(); 
	}

	public final TimeStruct clone() {
		
		try {
			TimeStructImpl cloned = (TimeStructImpl) super.clone();
			cloned.setTimeValue(getTimeValue());
			return cloned;
		}
		catch (CloneNotSupportedException cnse) {
			
			cnse.printStackTrace();
			return null;
		}
	}
	
	/**
	 * <p>Creates a new time value from a UTC-style string. The expected format of the
	 * string is "<code>HH:mm:ss.SSSZ</code>", where:
	 * 
	 * <ul>
	 *  <li>"HH" is the two digit hour of the day in 24-hour clock;</li>
	 *  <li>"mm" is the two digit minute;</li>
	 *  <li>"ss" is the two digit second;</li>
	 *  <li>"SSS" is the three digital millisecond value;</li>
	 *  <li>"Z" is the timezone offset from UTC, measure in positive or negative hours 
	 *  and minutes, e.g. "+0000" for GMT and "-0800" for PST.</li>
	 * </ul></p>
	 * 
	 * <p>Note that if a timezone value different from UTC ("+0000"),
	 * the time value will be converted to its UTC equivalent.</p>
	 * 
	 * @param time UTC-style string time value.
	 * 
	 * @return Time structure representing the given string time value.
	 * 
	 * @throws ParseException The given value could not be converted into a time structure value.
	 * @throws NullPointerException Argument is null.
	 */
	public final static TimeStructImpl parseFactory(
			String time) 
		throws ParseException, 
			NullPointerException {
		
		if (time == null)
			throw new NullPointerException("Cannot create time value from a null string.");
		
		int lastDot = time.lastIndexOf('.');
//		Need to be more lenient if fraction of second is not present
//		if ((lastDot == -1) || ((lastDot + 3) >= stamp.length()))
//			throw new ParseException("Timestamp is not in the expected format.", lastDot);
		
		if ((lastDot > 0) && (lastDot < (time.length() - 3)) && 
				(!(Character.isDigit(time.charAt(lastDot + 3)))))
			time = time.substring(0, lastDot + 3) + "0" + time.substring(lastDot + 3);
		
		if (time.endsWith("Z"))
			time = time.substring(0, time.length() - 1) + "+0000";
		
		if ((lastDot == -1) && (time.length() > 5))
			time = time.substring(0, time.length() - 5) + ".000" + time.substring(time.length() - 5);
		
		formatter.setLenient(false);
		Date parsedDate = formatter.parse(time);
		
		GregorianCalendar calenderValue = new GregorianCalendar();
		calenderValue.clear();
		calenderValue.setTimeZone(formatter.getTimeZone());
		calenderValue.setTime(parsedDate);
		
		return new TimeStructImpl(calenderValue);
	}

	// TODO documentation on these special methods
	public final static void generateEmbeddableORM(
			Node parent,
			String namespace,
			String prefix) {
		
		Element embeddable = XMLBuilder.createChild(parent, namespace, prefix, "embeddable");
		XMLBuilder.setAttribute(embeddable, namespace, prefix, "class", TimeStructImpl.class.getCanonicalName());
		XMLBuilder.setAttribute(embeddable, namespace, prefix, "access", "FIELD");
		
		Element embeddableAttributes = XMLBuilder.createChild(embeddable, namespace, prefix, "attributes");
		
		Element basic = XMLBuilder.createChild(embeddableAttributes, namespace, prefix, "basic");
		XMLBuilder.setAttribute(basic, namespace, prefix, "name", "timeValue");
		XMLBuilder.appendElement(basic, namespace, prefix, "temporal", "TIME");
	}
	
	public final static void generateEmbeddedORM(
			Node parent,
			String ownerName,
			String namespace,
			String prefix) {
		
		Element embedded = XMLBuilder.createChild(parent, namespace, prefix, "embedded");
		XMLBuilder.setAttribute(embedded, namespace, prefix, "name", Utilities.lowerFirstLetter(ownerName));
		
		Element attributeOverride = XMLBuilder.createChild(embedded, namespace, prefix, "attribute-override");
		XMLBuilder.setAttribute(attributeOverride, namespace, prefix, "name", "timeValue");
		Element column = XMLBuilder.createChild(attributeOverride, namespace, prefix, "column");
		XMLBuilder.setAttribute(column, namespace, prefix, "name", ownerName);
	}
}
