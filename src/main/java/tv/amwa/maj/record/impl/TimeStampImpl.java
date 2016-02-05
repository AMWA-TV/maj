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
 * $Log: TimeStampImpl.java,v $
 * Revision 1.5  2011/07/27 17:21:29  vizigoth
 * Fractions of seconds now measured in 1/250ths.
 *
 * Revision 1.4  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/20 17:41:19  vizigoth
 * Fixed up all meta tests to the point where they all pass.
 *
 * Revision 1.2  2011/01/19 11:36:06  vizigoth
 * Unified approach to working with timezones for parsing time structs and time stamps.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/11/08 15:27:05  vizigoth
 * Fixes required to deal with issues discovered parsing time values when outside of the UK.
 *
 * Revision 1.2  2009/12/18 17:56:03  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
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
 * Revision 1.1  2007/11/13 22:14:35  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:26  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

// TimeStamp is definately called TimeStamp in AAF

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.record.DateStruct;
import tv.amwa.maj.record.TimeStamp;
import tv.amwa.maj.record.TimeStruct;
import tv.amwa.maj.util.Utilities;


/** 
 * <p>Implementation of a date and time in UTC (Coordinated Universal Time). The value is made
 * up of a {@linkplain DateStructImpl date} and {@linkplain TimeStructImpl time} structure.</p>
 * 
 * <p>The internal representation of this value is a {@link java.util.Calendar}
 * value.</p>
 * 
 * <p>A time stamp structure is represented in the database by a column of type date:</p>
 *
 * <pre>
 *     `Date` date
 * </pre>
 *
 *
 *
 * @see tv.amwa.maj.record.TimeStamp
 * @see tv.amwa.maj.record.impl.DateStructImpl
 * @see tv.amwa.maj.record.impl.TimeStructImpl
 * @see tv.amwa.maj.industry.TypeDefinitions#TimeStamp
 * @see java.util.Calendar
 */
public final class TimeStampImpl 
	implements 
		TimeStamp, 
		Serializable,
		XMLSerializable,
		Cloneable {

	private static final long serialVersionUID = 1822101767409444066L;

	/** Authoritative internal representation of the time stamp value. */
	private Calendar timestamp;
	/** Quick access, transient time structure that shares the same internal calendar value. */
	private TimeStructImpl timeValue;
	/** Quick access, transient date structure that shared the same internal calendar value. */
	private DateStructImpl dateValue;

	/** UTC date formatter used to creating and reading strings. */
	private final static SimpleDateFormat formatter =
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	static {
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
	}
		
	/**
	 * <p>Create a new time stamp value to represent the time now.</p>
	 *
	 */
	public TimeStampImpl() {
		
		timestamp = new GregorianCalendar();
		timeValue = new tv.amwa.maj.record.impl.TimeStructImpl(timestamp);
		dateValue = new tv.amwa.maj.record.impl.DateStructImpl(timestamp);
	}
	
	/**
	 * <p>Create a new time stamp from the given calendar value.</p>
	 *
	 * @param calendarItem Calendar value to use to create a new time stamp.
	 * 
	 * @throws IllegalArgumentException The resulting time stamp value would not be valid.
	 * @throws NullPointerException Argument is <code>null</code>.
	 */
	public TimeStampImpl(
			Calendar calendarItem) 
		throws IllegalArgumentException, 
			NullPointerException {
		
		timeValue = new TimeStructImpl();
		dateValue = new DateStructImpl();
		
		setTimeStamp(calendarItem);
	}
	
	/**
	 * <p>Create a new time stamp from the given date and time structures.</p>
	 *
	 * @param date Date to set for the new time stamp.
	 * @param time Time to set for the new time stamp.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>.
	 * @throws IllegalArgumentException The resulting time stamp value would not be valid.
	 */
	public TimeStampImpl(
			DateStruct date,
			TimeStruct time) 
		throws NullPointerException, 
			IllegalArgumentException {

		this();
		setStamp(date, time);
	}
	
	public final DateStruct getDate() {

		syncValues();
		return dateValue.clone();
	}

	public final void setDate(
			DateStruct date) 
		throws NullPointerException, 
			IllegalArgumentException {

		setDateInternal(date);
	}

	/**
	 * <p>Internal method for setting the date part of this time stamp without
	 * validating the value. This method was introduced to reduce code duplication.</p>
	 *
	 * @param date Date value to use to set the internal calendar value.
	 * 
	 * @throws NullPointerException Argument is <code>null</code>.
	 */
	private void setDateInternal(
			DateStruct date) 
		throws NullPointerException {

		if (date == null)
			throw new NullPointerException("Cannot set date of time stamp from a null value.");
		
		timestamp.clear(Calendar.DATE);
		timestamp.set(Calendar.DATE, (int) date.getDay());
		
		timestamp.clear(Calendar.MONTH);
		timestamp.set(Calendar.MONTH, ((int) date.getMonth()) - 1);
		
		// Need to adjust for era here
		timestamp.clear(Calendar.YEAR);
		timestamp.set(Calendar.YEAR, (int) date.getYear());
		
		syncValues();
	}

	public final TimeStruct getTime() {
		
		syncValues();
		return timeValue.clone();
	}

	public final void setTime(
			TimeStruct time) 
		throws NullPointerException, 
			IllegalArgumentException {

		setTimeInternal(time);	
	}
	
	/**
	 * <p>Internal method for setting the time part of this time stamp without
	 * validating the value. This method was introduced to reduce code duplication.</p>
	 *
	 * @param time Time value to use to set the internal calendar value.
	 * 
	 * @throws NullPointerException Argument is <code>null</code>.
	 */
	private void setTimeInternal(
			TimeStruct time) 
		throws NullPointerException {
		
		if (time == null)
			throw new NullPointerException("Cannot set time of time stamp from a null value.");
		
		timestamp.clear(Calendar.HOUR_OF_DAY);
		timestamp.set(Calendar.HOUR_OF_DAY, (int) time.getHour());
		
		timestamp.clear(Calendar.MINUTE);
		timestamp.set(Calendar.MINUTE, (int) time.getMinute());
		
		timestamp.clear(Calendar.SECOND);
		timestamp.set(Calendar.SECOND, (int) time.getSecond());
		
		timestamp.clear(Calendar.MILLISECOND);
		int fraction = (int) time.getFraction();
		fraction = (fraction >= 0) ? fraction : 256 + fraction;
		timestamp.set(Calendar.MILLISECOND, fraction * 4);		
		
		syncValues();
	}

	public final void setStamp(
			DateStruct date,
			TimeStruct time) 
		throws NullPointerException, 
			IllegalArgumentException {

		setTimeInternal(time);
		setDateInternal(date);

		// Test once that the combined date and time is valid.
		timestamp.getTime();
	}

	public final Calendar getTimeStamp() {
		
		return (Calendar) timestamp.clone();
	}

	/** 
	 * <p>Set the value of this time stamp using a {@link java.util.Calendar} value.
	 * This method is strict in its interpretation of date and time values.</p>
	 * 
	 * <p>Note that all fields of the value will be preserved within the time stamp value and
	 * available on a call to {@link #getTimeStamp()}.</p>
	 *
	 * @param timestampItem Calendar value to use to set the value of this time stamp.
	 * 
	 * @throws NullPointerException Argument is <code>null</code>.
	 * @throws IllegalArgumentException The given calendar value is not a valid date or time.
	 */
	public final void setTimeStamp(
			Calendar timestampItem) 
		throws NullPointerException, 
			IllegalArgumentException {
		
		if (timestampItem == null)
			throw new NullPointerException("Cannot set the value of a time stamp from null.");
		
		timestampItem.setLenient(false);
		
		// Test this is an acceptable timestamp
		timestampItem.getTime();
		
		timestamp = Calendar.getInstance();
		timestamp.setTime(timestampItem.getTime()); // Copy the passed in value to prevent side effects
		timestamp.setTimeZone(timestampItem.getTimeZone());
		
		syncValues();
	}

	/**
	 * <p>Ensure that the separate date and time values are in sync with the persisted {@link java.util.Calendar}
	 * value.</p>
	 */
	private void syncValues() {
		
		dateValue.setDateValue(timestamp);
		timeValue.setTimeValue(timestamp);
	}
	
	/** 
	 * <p>Formats the string according ISO 8601 and as a UTC value.</p>
	 * 
	 * <p>The format of the string is "<code>yyyy-MM-dd'T'HH:mm:ss.SSZ</code>", where:
	 * 
	 * <ul>
	 *  <li>yyyy - Year part of the time stamp value.</li>
	 *  <li>MM - Month part of the time stamp value, in the range 1 to 12.</li>
	 *  <li>dd - Day of the month part of the time stamp value, in the range 1 to 31.</li>
	 *  <li>HH - Hour of the day of the time stamp value, specified in 24-hour clock in 
	 *  the range 0 to 23.</li>
	 *  <li>mm - Minute part of the time stamp value, in the range 0 to 59.</li>
	 *  <li>ss - Second part of the time stamp value, normally in the range 0 to 59.</li>
	 *  <li>SS - hundreths of a second part of the time stamp value, in the range .00 to .99.</li>
	 *  <li>Z - The letter "Z" to indicate that this value is shown in UTC.</li>
	 * </ul></p>
	 * 
	 * @see java.lang.Object#toString()
	 * @see java.text.SimpleDateFormat
	 */
	public final String toString() {
		
		String formatted = formatter.format(timestamp.getTime());
		if (formatted.endsWith("+0000"))
			formatted = formatted.substring(0, formatted.length() - 6) + "Z";
		return formatted;
	}
	
	public final int hashCode() {
		return timestamp.hashCode();
	}

	/**
	 * <p>Create a time stamp from a string that is formatted according to the UTC standards. If
	 * the value is given with a timezone part, the internal representation will be 
	 * converted to UTC "<code>+0000</code>", otherwise kwown as "<code>Z</code>".</p>
	 * 
	 * <p>One acceptable format of the string is "<code>yyyy-MM-dd'T'HH:mm:ss.SSSZ</code>", where:</p>
	 * 
	 * <ul>
	 *  <li>yyyy - Year part of the time stamp value.</li>
	 *  <li>MM - Month part of the time stamp value, in the range 1 to 12.</li>
	 *  <li>dd - Day of the month part of the time stamp value, in the range 1 to 31.</li>
	 *  <li>HH - Hour of the day of the time stamp value, specified in 24-hour clock in 
	 *  the range 0 to 23.</li>
	 *  <li>mm - Minute part of the time stamp value, in the range 0 to 59.</li>
	 *  <li>ss - Second part of the time stamp value, normally in the range 0 to 59.</li>
	 *  <li>SSS - millisecond part of the time stamp value, in the range .000 to .999.</li>
	 *  <li>Z - Timezone part of the time stamp value, measured as a positive or negative
	 *  hours and minutes offset from UTC, e.g. "-0800" for Pacific Standard Time.</li>
	 * </ul>
	 * 
	 * <p>Alternatively, a "<code>Z</code>" may be used in place of "<code>+0000</code>". The
	 * number of milliseconds can be expressed by 1/250ths of a second.</p>
	 *
	 * @param stamp String representation of a time stamp value.
	 * @return Newly created MAJ time stamp value from given string value.
	 * 
	 * @throws ParseException A problem with the given string prevents it from being converted
	 * to a time stamp value, or the resulting value in unacceptable.
	 * @throws NullPointerException Argument is <code>null</code>.
	 */
	public final static TimeStampImpl parseFactory(
			String stamp) 
		throws ParseException, 
			NullPointerException {
		
		if (stamp == null)
			throw new NullPointerException("Cannot create a time stamp from a null value.");
		
		if (stamp.length() < 20)
			throw new ParseException("Timestamp values does not have a sufficient number of characters.", 0);
		
		int lastDot = stamp.lastIndexOf('.');
//		Need to be more lenient if fraction of second is not present
//		if ((lastDot == -1) || ((lastDot + 3) >= stamp.length()))
//			throw new ParseException("Timestamp is not in the expected format.", lastDot);
		
		if ((lastDot > 0) && (lastDot < (stamp.length() - 3)) && 
				(!(Character.isDigit(stamp.charAt(lastDot + 3)))))
			stamp = stamp.substring(0, lastDot + 3) + "0" + stamp.substring(lastDot + 3);
		
		if (stamp.endsWith("Z"))
			stamp = stamp.substring(0, stamp.length() - 1) + "+0000";
		
		if ((lastDot == -1) && (stamp.length() > 5))
			stamp = stamp.substring(0, stamp.length() - 5) + ".000" + stamp.substring(stamp.length() - 5);
		
//		System.out.println("***: " + stamp);
		
		formatter.setLenient(false);
		Date parsedDate = formatter.parse(stamp);
		
		Calendar calendarValue = new GregorianCalendar();
		calendarValue.clear();
		calendarValue.setTimeZone(formatter.getTimeZone());
		calendarValue.setTime(parsedDate);
		
		return new TimeStampImpl(calendarValue);
	}

	/** 
	 * <p>Checks for the equality of UTC values only and not the exact equality of
	 * the underlying calendar value representation.</p>
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public final boolean equals(
			Object o) {
		
		if (o == null) return false;
		if (!(o instanceof TimeStamp)) return false;
		
		TimeStamp testStamp = 
			(TimeStamp) o;
		
		if (!getDate().equals(testStamp.getDate())) return false;
		if (!getTime().equals(testStamp.getTime())) return false;
		
		return true;
	}

	public final TimeStamp clone() {
		
		try {
			TimeStampImpl cloned = (TimeStampImpl) super.clone();
			cloned.setTimeStamp(getTimeStamp());
			return cloned;
		} 
		catch (CloneNotSupportedException cnse) {

			cnse.printStackTrace();
			return null;
		}
	}	

	public final void appendXMLChildren(
		Node parent) {

		Document document = parent.getOwnerDocument();
		Text textTimestamp = document.createTextNode(toString());
		parent.appendChild(textTimestamp);
	}

	public String getComment() {

		return null;
	}
	
	// TODO documentation on these special methods
	public final static void generateEmbeddableORM(
			Node parent,
			String namespace,
			String prefix) {
		
		Element embeddable = XMLBuilder.createChild(parent, namespace, prefix, "embeddable");
		XMLBuilder.setAttribute(embeddable, namespace, prefix, "class", TimeStampImpl.class.getCanonicalName());
		XMLBuilder.setAttribute(embeddable, namespace, prefix, "access", "FIELD");
		
		Element embeddableAttributes = XMLBuilder.createChild(embeddable, namespace, prefix, "attributes");
		
		Element basic = XMLBuilder.createChild(embeddableAttributes, namespace, prefix, "basic");
		XMLBuilder.setAttribute(basic, namespace, prefix, "name", "timestamp");
		XMLBuilder.appendElement(basic, namespace, prefix, "temporal", "TIMESTAMP");
		
		Element transientOne = XMLBuilder.createChild(embeddableAttributes, namespace, prefix, "transient");
		XMLBuilder.setAttribute(transientOne, namespace, prefix, "name", "timeValue");
		
		Element transientTwo = XMLBuilder.createChild(embeddableAttributes, namespace, prefix, "transient");
		XMLBuilder.setAttribute(transientTwo, namespace, prefix, "name", "dateValue");

	}
	
	public final static void generateEmbeddedORM(
			Node parent,
			String ownerName,
			String namespace,
			String prefix) {
		
		Element embedded = XMLBuilder.createChild(parent, namespace, prefix, "embedded");
		XMLBuilder.setAttribute(embedded, namespace, prefix, "name", Utilities.lowerFirstLetter(ownerName));
		
		Element attributeOverride = XMLBuilder.createChild(embedded, namespace, prefix, "attribute-override");
		XMLBuilder.setAttribute(attributeOverride, namespace, prefix, "name", "timestamp");
		Element column = XMLBuilder.createChild(attributeOverride, namespace, prefix, "column");
		XMLBuilder.setAttribute(column, namespace, prefix, "name", ownerName);
	}
	
	public final static String MYSQL_COLUMN_DEFINITION = 
		"CHAR(23) CHARACTER SET ascii COLLATE ascii_general_ci";
	
	public final static String toPersistentForm(
			TimeStamp timeStamp) {
		
		if (timeStamp == null) return null;
		return timeStamp.toString();
	}
	
	public final static TimeStamp fromPersistentForm(
			String timeStamp) {
		
		if (timeStamp == null) return null;
		try {
			return parseFactory(timeStamp);
		}
		catch (ParseException pe) {
			return null;
		}
	}
}
