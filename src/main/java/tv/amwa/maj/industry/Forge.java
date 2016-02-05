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
 * $Log: Forge.java,v $
 * Revision 1.7  2011/10/07 19:46:00  vizigoth
 * Added generate ID using material part only. Creates a D-Cinema style UMID.
 *
 * Revision 1.6  2011/07/27 12:25:07  vizigoth
 * Comments updated as fractions of seconds now measured in 1/250ths.
 *
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/19 17:12:10  vizigoth
 * Allow initialization to use virtual methods when appropriate, e.g. for Track/EventTrack.
 *
 * Revision 1.3  2011/01/19 11:41:15  vizigoth
 * Fixes and additions to issues found when writing tests.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/05 13:08:07  vizigoth
 * Created new forge for making record and union type values.
 *
 */

package tv.amwa.maj.industry;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.enumeration.EdgeType;
import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.enumeration.MaterialType;
import tv.amwa.maj.enumeration.ProductReleaseType;
import tv.amwa.maj.enumeration.RGBAComponentKind;
import tv.amwa.maj.exception.GenerationMethodNotSupportedException;
import tv.amwa.maj.exception.IllegalPropertyException;
import tv.amwa.maj.integer.Int16;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int8;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.misctype.FrameOffset;
import tv.amwa.maj.misctype.PackageIDType;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.DateStruct;
import tv.amwa.maj.record.EdgeCodeValue;
import tv.amwa.maj.record.InstanceNumberGeneration;
import tv.amwa.maj.record.MaterialNumberGeneration;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.ProductVersion;
import tv.amwa.maj.record.RGBAComponent;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.TimeStamp;
import tv.amwa.maj.record.TimeStruct;
import tv.amwa.maj.record.TimecodeValue;
import tv.amwa.maj.record.VersionType;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.record.impl.DateStructImpl;
import tv.amwa.maj.record.impl.EdgeCodeValueImpl;
import tv.amwa.maj.record.impl.PackageIDImpl;
import tv.amwa.maj.record.impl.ProductVersionImpl;
import tv.amwa.maj.record.impl.RGBAComponentImpl;
import tv.amwa.maj.record.impl.RationalImpl;
import tv.amwa.maj.record.impl.TimeStampImpl;
import tv.amwa.maj.record.impl.TimeStructImpl;
import tv.amwa.maj.record.impl.TimecodeValueImpl;
import tv.amwa.maj.record.impl.VersionTypeImpl;
import tv.amwa.maj.union.SourceReferenceValue;
import tv.amwa.maj.union.impl.SourceReferenceValueImpl;
import tv.amwa.maj.util.Utilities;

/**
 * <p>Stamps out values of the model, record and union types, acting as a factory for these
 * values and objects. All methods are static and reference underlying methods, produce value to
 * the MAJ interface using static methods in the underlying implementation.</p>
 * 
 * <p>To create new instances of media classes, whether AAF classes or extensions classes,
 * use one of the following make methods:</p>
 * 
 * <ul>
 *  <li>{@link #make(Class, Object...)} - Make an object that implements the given interface
 *  using the provided list of parameters.</li>
 *  <li>{@link #make(Class, boolean, Object...)} - Similar to the previous method, except you
 *  can define how strict you want MAJ to be in checking whether all the required parameters
 *  are provided.</li>
 *  <li>{@link #makeAAF(String, Object...)} - Make an instance of a defined AAF baseline
 *  class using its name, initialized using the list of paramters provided.</li>
 *  <li>{@link #makeByName(String, String, Object...)} - Make an instance of a class from
 *  a given namespace of the specified class name, initialized using the list of parameters
 *  provided.</li>
 *  <li>{@link #makeByName(String, String, boolean, Object...)} - As above, specifying how
 *  strict you want MAJ to be in checking whether all the required parameters are 
 *  provided.</li<
 * </ul>
 * 
 * <p>All of the above methods take lists of name and value pairs and are very open to how
 * the values are provided. For example, a {@linkplain tv.amwa.maj.model.DataDefinition data 
 * definition} can be provided as an instance of a class implementing the data definition 
 * interface, an {@linkplain tv.amwa.maj.record.AUID AUID} identifying the definition or
 * simply by its name, e.g. <em>Picture</em>.</p> 
 * 
 * <p>The record types supported by this factory class are: {@link AUID}, {@link DateStruct}, 
 * {@link EdgeCodeValue}, {@link PackageID}, {@link ProductVersion}, {@link Rational},
 * {@link RGBAComponent}, {@link TimecodeValue}, {@link TimeStamp}, {@link TimeStruct},
 * {@link VersionType}.</p>
 * 
 * <p>The union types, compound-type values for passing or returning a single value 
 * to and from a MAJ method, are: {@link SourceReferenceValue}.</p>
 * 
 * <p>Most types have a method that allows a string representation to be parsed and
 * transformed into a value of the given type: {@link #parseAUID(String)}, {@link #parseDate(String)}, 
 * {@link #parseEdgeCode(String)}, {@link #parsePackageID(String)}, {@link #parseProductVersion(String)},
 * {@link #parseRational(String)}, {@link #parseRGBAComponent(String)}, {@link #parseTimecode(String)}&nbsp;/
 * {@link #parseTimecode(String, short, boolean)}, {@link #parseTimeStamp(String)}, 
 * {@link #parseTime(String)}, {@link #parseVersion(String)}.</p>
 * 
 * <p>Methods for creating values of a given type are listed below: {@link SourceReferenceValue}.</p>
 * 
 * <ul>
 *  <li>{@link AUID} - Make a value from the known component parts with {@link #makeAUID(byte[])}
 *  and {@link #makeAUID(int, short, short, byte[])}. Alternatively, use UUID-generation methods
 *  to make type 1, 3 and 4 UUIDs with {@link #timebasedAUID()}&nbsp;/ {@link #timebasedAUID(byte[])}, 
 *  {@link #namebasedAUID(byte[])}&nbsp;/ {@link #namebasedAUID(String)}
 *  and {@link #randomAUID()}. The nil AUID can be made using {@link #nilAUID()}.</li>
 *  
 *  <li>{@link DateStruct} - Make a date from its component parts with {@link #makeDate(byte, byte, short)}
 *  or from an existing Java Calendar value with {@link #makeDate(Calendar)}. For today's date, use
 *  {@link #todaysDate()}.</p>
 *  
 *  <li>{@link EdgeCodeValue} - Make an edge code value from its component parts with 
 *  {@link #makeEdgeCode(long, FilmType, EdgeType)} and with its optional header using 
 *  {@link #makeEdgeCode(long, FilmType, EdgeType, byte[])} or 
 *  {@link #makeEdgeCode(long, FilmType, EdgeType, String)}. For a default value, use 
 *  {@link #makeEdgeCode()}.</li>
 *  
 *  <li>{@link PackageID} - Make a package identifier value from its component parts
 *  with {@link #makePackageID(byte[])} and {@link #makePackageID(byte[], byte, byte, byte, byte, AUID)}.
 *  Generate package identifiers according to specified generation strategies with 
 *  {@link #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration)}, 
 *  {@link #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, AUID)} and
 *  {@link #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, byte[])}. 
 *  Create a D-Cinema UMID with {@link #dCinemaUMID()}, a random UMID with {@link #randomUMID()} or 
 *  a zero UMID with {@link #zeroPackageID()}.</li>
 *  
 *  <li>{@link ProductVersion} - Make a product version value from its constituent parts with
 *  {@link #makeProductVersion(short, short, short, short, ProductReleaseType)}. Alternatively,
 *  make a zero <em>default</em> value using {@link #zeroProductVersion()}.</li>
 *  
 *  <li>{@link Rational} - Make a rational value from its numerator and denominator with
 *  {@link #makeRational(int, int)} or a zero valued rational with {@link #zeroRational()}.</li>
 *  
 *  <li>{@link RGBAComponent} - Make a RGBA component value from its size and code components with
 *  {@link #makeRGBAComponent(RGBAComponentKind, byte)} or a zero <em>default</em> value with
 *  {@link #zeroRGBAComponent()}.</li>
 *  
 *  <li>{@link TimecodeValue} - Make a timecode value from its components with
 *  {@link #makeTimecode(long, short, boolean)}. Alternatively, calculate a timecode value
 *  from a SMPTE-style set of values with {@link #calculateTimecode(short, short, short, short, short, boolean)}
 *  or from other timecode values with {@link #calculateDuration(TimecodeValue, TimecodeValue)} and
 *  {@link #calculateEndTimecode(TimecodeValue, TimecodeValue)}.</li>
 *  
 *  <li>{@link TimeStamp} - Make a time stamp value from its component parts with 
 *  {@link #makeTimeStamp(DateStruct, TimeStruct)} and 
 *  {@link #makeTimeStamp(short, byte, byte, byte, byte, byte, byte)}. Alternatively, make
 *  a time stamp from an existing {@link java.util.Calendar} value with
 *  {@link #makeTimeStamp(Calendar)} or a time stamp for now with {@link #now()}.</li>
 *  
 *  <li>{@link TimeStruct} - Make a time value (with no date part) from its component parts with
 *  {@link #makeTime(byte, byte, byte)} and {@link #makeTime(byte, byte, byte, byte)}. Alternatively,
 *  make a time value from an existing {@link java.util.Calendar} value with
 *  {@link #makeTime(Calendar)} or representing the current time with {@link #timeNow()}.</li>
 *  
 *  <li>{@link VersionType} - Make a version type value from its component parts with 
 *  {@link #makeVersion(byte, byte)} or a zero version type value with {@link #zeroVersion()}.</li>
 * </ul>
 * 
 *
 *
 */

public final class Forge {

	private Forge() { }
	
	// AUID
	
    /**
     * <p>Make a new AUID from a URN representation, as specified in section&nbsp;3 of
     * <a href="http://www.faqs.org/rfcs/rfc4122.html" alt="RFC 4122">rfc 4122</a> or
     * the commonly used URN representation of Universal Labels (starting with 
     * "<code>urn:x-ul:</code>" or "<code>urn:smpte:ul:</code>"). The method accepts 
     * hexadecimal digits in upper or lower case.</p>
     * 
     * <p>Example values are:</p>
     * 
     * <ul>
     *  <ul><code>urn:uuid:B302CABE-7AAB-41C4-A072-F0E96C160D7D</code></ul>
     *  <ul><code>urn:uuid:00000000-0000-0000-0000-000000000000</code></ul>
     *  <ul><code>urn:smpte:ul:0602b34.010101ff.42464141.000d4d4f</code></ul>
     * </ul>
     * 
     * @param auidAsString String value to use to create a new AUID.
     * @return Newly created AUID value corresponding to the given string value.
     * 
     * @throws NullPointerException AUID string value argument is <code>null</code>.
     * @throws NumberFormatException The given string is not recognised as an AUID value, 
     * either because it is of the wrong length, has its separators in the wrong place, 
     * uses the wrong separators or contains non-hexadecimal values. 
     * 
     * @see AUID#toString()
     * @see #makeAUID(byte[])
     */
	public final static AUID parseAUID(
			String auidAsString)
		throws NullPointerException,
			ParseException {
		
		try {
			return AUIDImpl.parseFactory(auidAsString);
		}
		catch (NumberFormatException nfe) {
			throw new ParseException("Parse error when trying to read an AUID value: " + nfe.getMessage(), 0);
		}
	}

	/**
	 * <p>Generates a new UUID as AUID generated according to the time and location
	 * based method, type&nbsp;1. The node identifier used for this method can be set by calling 
	 * {@link #setLocalNodeID(byte[])}.</p>
	 * 
	 * <p>It is possible to call this method repeatedly, even though Java's time resolution is 
	 * to the nearest millisecond. An extra counter is implemented by MAJ in this method to provide 
	 * an additional sequence of values to simulate
	 * the generation of time values measured in 100-nano second chunks as required by the
	 * UUID specification. The clock sequence part of the value is generated using a secure 
	 * random number generator.</p>
	 *
	 * @return Time and location based UUID as an AUID.
	 * 
	 * @see #setLocalNodeID(byte[])
	 * @see #randomAUID()
	 * @see #namebasedAUID(byte[])
	 */
	public final static AUID timebasedAUID() {
		
		return AUIDImpl.timebasedAUID();
	}
	
	/**
	 * <p>Set the local node ID to use in the generation of {@linkplain #timebasedAUID() time-based UUIDs as AUIDs}. 
	 * This method is provided to allow the default host id generated in the instantiation of this
	 * class to be overridden. As the actual host identifier (Ethernet address) of a system is not
	 * available through the Java 1.5 APIs, the default value is generated from the IP address 
	 * and domain name of this system. Using this method, the actual host identifier of the
	 * system can be set, or any other 6-byte unique identifier for the host.</p>
	 * 
	 * <p>If you are using Java 1.6, the {@link tv.amwa.maj.util.Utilities#getLocalHostID() Utilities.getLocalHostID()}
	 * method is available to return an Ethernet address for one of the local interfaces. Note
	 * that the value returned may change as interfaces are brought up and down, so this method
	 * should be good enough for the repeated generation of time-based UUIDs but not as a general 
	 * and reliable system identifier.</p>
	 *
	 * @param localNodeID A guaranteed to be unique identifier for the node on which this
	 * class is loaded. The value will be padded or truncated to 6-bytes.
	 * 
	 * @throws NullPointerException The given byte array is <code>null</code>.
	 * 
	 * @see #timebasedAUID()
	 * @see tv.amwa.maj.util.Utilities#createLocalHostID(int)
	 * @see tv.amwa.maj.util.Utilities#getLocalHostID()
	 */
	public static final void setLocalNodeID(
			byte[] localNodeID) 
		throws NullPointerException {
		
		if (localNodeID == null)
			throw new NullPointerException("The given local host ID value is null and so cannot be used.");
		
		AUIDImpl.setLocalNodeID(localNodeID);
	}
	
	/**
	 * <p>Generates a new UUID as an AUID from the current time and a location provided
	 * by the given node identifier. The node identifier must be a byte array containing 
	 * at least 6 elements.</p>
	 * 
	 * <p>It is possible to call this method repeatedly, even though Java's time resolution is 
	 * to the nearest millisecond. An extra counter is implemented by MAJ in this method to provide 
	 * an additional sequence of values to simulate
	 * the generation of time values measured in 100-nano second chunks as required by the
	 * UUID specification. The clock sequence part of the value is generated using a secure 
	 * random number generator.</p>
	 *
	 * @param nodeID Unique identifier for this computational node, which must contain at least
	 * 6&nbsp;bytes.
	 * @return Time and location based UUID as an AUID.
	 * 
	 * @throws NullPointerException The node ID byte array argument is <code>null</code>.
	 * @throws IllegalArgumentException The node identifier must contain at least 6 bytes.
	 * 
	 * @see #timebasedAUID()
	 * @see #namebasedAUID(byte[])
	 * @see #randomAUID()
	 */
	public final static AUID timebasedAUID(
			byte[] nodeID) 
		throws NullPointerException,
			IllegalArgumentException {
		
		return AUIDImpl.timebasedAUID(nodeID);
	}

	/**
	 * <p>Generates a new UUID as an AUID generated with the name-based method, type&nbsp;3. This
	 * method is based on {@link java.util.UUID#nameUUIDFromBytes(byte[])} that uses an
	 * MD5 hashing algorithm.</p>
	 *
	 * @param nameData Name data to use to create a name-based UUID.
	 * @return Name-based UUID as an AUID.
	 * 
	 * @throws NullPointerException The name data provided is <code>null</code>.
	 * 
	 * @see java.util.UUID#nameUUIDFromBytes(byte[])
	 * @see #namebasedAUID(String)
	 * @see #timebasedAUID()
	 * @see #randomAUID()
	 */
	public final static AUID namebasedAUID(
			byte[] nameData) 
		throws NullPointerException {
	
		return AUIDImpl.namebasedAUID(nameData);
	}
	
	/**
	 * <p>Generates a new UUID as an AUID generated with the name-based method, type&nbsp;3, from a 
	 * Java {@linkplain java.lang.String string} value. This
	 * method is based on {@link java.util.UUID#nameUUIDFromBytes(byte[])} that uses an
	 * MD5 hashing algorithm.</p>
	 *
	 * @param nameData Name data to use to create a name-based UUID as a string.
	 * @return Name-based UUID as an AUID.
	 * 
	 * @throws NullPointerException The name data provided is <code>null</code>.
	 * 
	 * @see java.util.UUID#nameUUIDFromBytes(byte[])
	 * @see java.lang.String#getBytes()
	 * @see #namebasedAUID(byte[])
	 * @see #timebasedAUID()
	 * @see #randomAUID()
	 */
	public final static AUID namebasedAUID(
			String nameData) 
		throws NullPointerException {
		
		if (nameData == null)
			throw new NullPointerException("Cannot make a name-type AUID value using null name data.");
		
		return AUIDImpl.namebasedAUID(nameData.getBytes());
	}
	
	/**
	 * <p>Generates a new AUID using pseudo-random number generation, type&nbsp;4. This method uses
	 * the <code>java.security.SecureRandom</code> number generator, seeded from values
	 * in the local environment obscured with a mixing function.</p>
	 * 
	 * @return Random generation of a new UUID as an AUID. 
	 * 
	 * @see tv.amwa.maj.util.Utilities#seedRandomMaker()
	 * @see #timebasedAUID()
	 * @see #namebasedAUID(byte[])
	 */
	public final static AUID randomAUID() {
		
		return AUIDImpl.randomAUID();
	}
	

	/**
	 * <p>Make a new AUID value from a 16-byte array representing its internal value. The given
	 * array is copied to make the internal representation used here safe from subsequent changes to
	 * the passed array. If the array length is less than or greater than 16, the array is padded with 
	 * 0's or truncated to contain exactly 16 bytes.</p>
	 *
	 * <p>If a SMPTE Universal Label is detected, starting with bytes <code>0x06</code>,
	 * <code>0x0e</code>, <code>0x2b</code> and <code>0x34</code>, then the first eight and
	 * last eight bytes are swapped over to make a valid AUID.</p>
	 * 
	 * @param bytes Byte array to use to set the value of the new AUID.
	 * 
	 * @throws NullPointerException The given array of bytes is <code>null</code>.
	 * 
	 * @see #makeAUID(int, short, short, byte[])
	 * @see #parseAUID(String)
	 */
	public final static AUID makeAUID(
			byte[] bytes) 
		throws NullPointerException {
		
		return new AUIDImpl(bytes);
	}

	/**
     * <p>Makes a new AUID value from its constituent parts.</p>
     * 
	 * @param data1 Integer value ... the first 4 bytes, also known as the "<code>time_low</code>"
	 * part.
	 * @param data2 Short value ... the next 2 bytes, also known as the "<code>time_mid</code>"
	 * part.
	 * @param data3 Short value ... the next 2 bytes, also known as the 
	 * "<code>time_mid_and_version</code>" part.
	 * @param data4 Array of 8 bytes ... the final 8 bytes, containing the variant, clock sequence
	 * and node parts. The value will be truncated or padded if more or less than 8 bytes are 
	 * provided respectively.
	 * 
	 * @throws NullPointerException The given data4 value byte array is <code>null</code>.
	 * 
	 * @see #makeAUID(byte[])
	 * @see #parseAUID(String)
	 */	
	public final static AUID makeAUID(
			int data1,
			short data2,
			short data3,
			byte[] data4)
		throws NullPointerException {
		
		return new AUIDImpl(data1, data2, data3, data4);
	}
	
	/**
	 * <p>Makes the nil AUID that represents the nil UUID, a special value where every byte element
	 * is set to zero.</p>
	 * 
	 * @return The nil AUID.
	 */
	public final static AUID nilAUID() {
		
		return new AUIDImpl(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
	}
	
	// DateStruct
	
	/**
	 * <p>Make a new date structure value from a string representation of a date. The date
	 * should be represented as the UTC portion of a date in the format 
	 * "<code>yyyy-mm-dd</code>". This method always parses values generated by the 
	 * date structure's {@link DateStruct#toString()} method.</p>
	 * 
	 * <p>Note that this method does not support the specification of timezone or 
	 * dates earlier than 1AD.</p>
	 * 
	 * <p>For example, Tuesday 4th January 2011 is represented as "<code>2011-01-04</code>".</p>
	 * 
	 * @param dateAsString Date value represented as a string.
	 * @return Newly created date structure taken from the given string.
	 * 
	 * @throws NullPointerException The given string representation of a date is <code>null</code>. 
	 * @throws ParseException Given string value cannot be parsed into a date structure as it
	 * is in the wrong format or is not a valid date.
	 * @throws IllegalArgumentException After successful parsing, the given date is not an 
	 * acceptable value due to the year value being out of range.
	 * 
	 * @see tv.amwa.maj.record.DateStruct#toString()
	 * @see #parseTime(String)
	 * @see #parseTimeStamp(String)
	 */
	public final static DateStruct parseDate(
			String dateAsString)
		throws NullPointerException,
			ParseException,
			IllegalArgumentException {
		
		return DateStructImpl.parseFactory(dateAsString);
	}

	/**
	 * <p>Make a date structure representing the current date.</p>
	 * 
	 * @return Date structure representing the current date.
	 * 
	 * @see #now()
	 * @see #timeNow()
	 */
	public final static DateStruct todaysDate() {
		
		return new DateStructImpl();
	}
	
	/** 
	 * <p>Create a new date value using the given year, 1-based month and day of 
	 * the month values.</p> 
	 * 
	 * @param day Day component of the date.
	 * @param month Month component of the date, with 1 for January, 2 for February etc..
	 * @param year Year component of the date.
	 * 
	 * @throws IllegalArgumentException One or more of the arguments has a value outside of
	 * an acceptable range.
	 * 
	 * @see DateStruct#getDay()
	 * @see DateStruct#getMonth()
	 * @see DateStruct#getYear()
	 * @see DateStruct#setDate(byte, byte, short)
	 */
	public final static DateStruct makeDate(
			@UInt8 byte day,
			@UInt8 byte month,
			@Int16 short year) 
		throws IllegalArgumentException {
		
		return new DateStructImpl(day, month, year);
	}
	
	/**
     * <p>Make a new date structure from a {@link java.util.Calendar} value.</p>
     * 
     * <p>Note that the newly created date structure may not store the timezone and other
     * locale information encoded in the original calendar value.</p>
     * 
     * @param dateAsCalendar Calendar value to use to extract a day, month and year from.
     * 
     * @throws NullPointerException The given calendar value is <code>null</code>.
     * 
     * @see DateStruct#getDateValue()
     */
	public final static DateStruct makeDate(
			Calendar dateAsCalendar) 
		throws NullPointerException {
		
		return new DateStructImpl(dateAsCalendar);
	}
	
	// Edgecode Value
	
	/**
	 * <p>Creates an edge code value from a pseudo-XML representation, including all those
	 * generated by {@link EdgeCodeValue#toString()}. Edge code values are intended to match those generated
	 * for the AAF XML schema.</p>
	 * 
	 * <p>An example edge code representation that would be parsed by this method is:</p>
	 * 
	 * <pre>
	 *     &lt;EdgecodeValue&gt;
	 *       &lt;EdgeCodeStart&gt;321&lt;/EdgeCodeStart&gt;
	 *       &lt;FilmKind&gt;Ft35MM&lt;/FilmKind&gt;
	 *       &lt;CodeFormat&gt;EtKeycode&lt;/CodeFormat&gt;
	 *       &lt;!--Header as text: 'abcdefgh'--&gt;
	 *       &lt;EdgeCodeHeader&gt;6162636465666768&lt;/EdgeCodeHeader&gt;
	 *     &lt;/EdgecodeValue&gt;
	 * </pre>
	 * 
	 * <p>Note that any unrecognised values will result in the setting of a default value rather
	 * than an error. Parse exceptions are only thrown when the validity of a value can only be
	 * determined beyond matching a simple regular expression.</p>
	 * 
	 * @param edgeCodeAsString Pseudo-XML representation of an edge code value.
	 * @return Edge code value with values with component values parsed from the given string.
	 * 
	 * @throws NullPointerException The given string value is <code>null</code>.
	 * @throws ParseException A problem occurred when trying to decode the given string.
	 * 
	 * @see EdgeCodeValue#toString()
	 */
	public final static EdgeCodeValue parseEdgeCode(
			String edgeCodeAsString)
		throws NullPointerException,
			ParseException {
		
		return EdgeCodeValueImpl.parseFactory(edgeCodeAsString);
	}

	/**
	 * <p>Makes a default film edge code value, with start frame 0, a null film kind,
	 * null edge type and no header.</p>
	 * 
	 * @return Default film edge code value.
	 */
	public final static EdgeCodeValue makeEdgeCode() {
		
		return new EdgeCodeValueImpl();
	}
	
    /**
     * <p>Makes a new film edge code value from its component parts.</p> 
     * 
	 * @param startFrame Specifies the edge code at the beginning of the corresponding segment.
	 * @param filmKind Specifies the type of film.
	 * @param codeFormat Specifies the edge code format.
	 * 
	 * @throws NullPointerException One or more of the arguments is/are <code>null</code>.
	 */
	public final static EdgeCodeValue makeEdgeCode(
			@PositionType long startFrame, 
			FilmType filmKind, 
			EdgeType codeFormat) 
		throws NullPointerException {
		
		return new EdgeCodeValueImpl(startFrame, filmKind, codeFormat);
	}

    /**
     * <p>Makes a new film edge code value from its component parts, including the optional header
     * as a byte array.</p> 
     * 
	 * @param startFrame Specifies the edge code at the beginning of the corresponding segment.
	 * @param filmKind Specifies the type of film.
	 * @param codeFormat Specifies the edge code format.
	 * @param header Text prefix that identifies the film.
	 * 
	 * @throws NullPointerException One or more of the arguments is/are <code>null</code>.
	 */
	public final static EdgeCodeValue makeEdgeCode(
			@PositionType long startFrame, 
			FilmType filmKind, 
			EdgeType codeFormat,
			byte[] header) 
		throws NullPointerException {
		
		EdgeCodeValue value = new EdgeCodeValueImpl(startFrame, filmKind, codeFormat);
		value.setEdgeCodeHeader(header);
		return value;
	}

    /**
     * <p>Makes a new film edge code value from its component parts, including the optional header
     * as a string.</p> 
     * 
	 * @param startFrame Specifies the edge code at the beginning of the corresponding segment.
	 * @param filmKind Specifies the type of film.
	 * @param codeFormat Specifies the edge code format.
	 * @param header Text prefix that identifies the film.
	 * 
	 * @throws NullPointerException One or more of the arguments is/are <code>null</code>.
	 */
	public final static EdgeCodeValue makeEdgeCode(
			@PositionType long startFrame, 
			FilmType filmKind, 
			EdgeType codeFormat,
			String header) 
		throws NullPointerException {
		
		EdgeCodeValue value = new EdgeCodeValueImpl(startFrame, filmKind, codeFormat);
		value.setEdgeCodeHeader(header);
		return value;
	}

	// PackageID
	
	/**
	 * <p>Parse a package identifier formatted as a URN-style UMID string and convert it into a newly 
	 * instantiated package id with an equivalent value. The format of the expected value 
	 * is the same as that generated by the {@link PackageID#toString()} method.
	 * 
	 * <p>For example:</p>
	 *
	 * <p><center><code>urn:smpte:umid:060a2b34.01010101.01010f13.1300b347.53b933d9.18245095.ca82322e.c0a801ba</code></center></p>
	 *
	 * <p>Hexadecimal digits used to express the UMID value may be a mixture of upper or lower case letters.</p>
	 * 
	 * <p>Note that this method also works with the old style of package ID URN, for example:</p>
	 * 
	 * <p><center><code>urn:x-umid:060a2b340101010101010f13-13-00b347-53b933d918245095ca82322ec0a801ba</code></center></p>
	 *
	 * @param packageIDAsString Package identifier value formatted as a URN-style UMID string.
	 * @return Package identifier value created from the given URN-style UMID value.
	 * 
	 * @throws NullPointerException The package identifier string value is <code>null</code>.
	 * @throws ParseException The given URN value is the wrong length, a different kind or
	 * URN, has path separators in the wrong place or contains digits other than hexadecimal digits
	 * where hexadecimal digits are expected.
	 * 
	 * @see PackageID#toString()
	 */
	public final static PackageID parsePackageID(
			String packageIDAsString)
		throws NullPointerException,
			ParseException {
		
		return PackageIDImpl.parseFactory(packageIDAsString);
	}
	
	/**
	 * <p>Makes the zero package identifier that consists of every bit set to zero. This
	 * value has special significance for {@linkplain #originalSource() original source references} 
	 * where it is used to indicate the original source, the end of this source reference chain.</p>
	 * 
	 * @return The zero package ID with all values set to zero.
	 * 
	 * @see #makePackageID(byte[])
	 * @see #makePackageID(byte[], byte, byte, byte, byte, AUID)
	 */
	public final static PackageID zeroPackageID() {
		
		return PackageIDImpl.getZeroPackageID();
	}
	
	/**
	 * <p>Make a new package identifier from the specified parameters. The label and material values are copied to
	 * ensure that subsequent external changes do not effect the newly created package identifier.</p>
	 * 
	 * <p>A default universal label to set the package identifier to be a SMPTE UMID is available as constant 
	 * {@link PackageID#BaseUniversalLabel}.</p>
	 * 
	 * @param univeralLabel Universal label component of the package identifier.
	 * @param length Length of the following data.
	 * @param instanceHigh Most significant part of the instance number component of the package identifier.
	 * @param instanceMid Second most significant part the instance number component of the package identifier.
	 * @param instanceLow Least significant part of the instance number component of the package identifier.
	 * @param materialNumber Unique identifier for the identified material, the material number component of the
	 * package identifier.
	 * 
	 * @throws NullPointerException One or both of the universal label or material number numbers are 
	 * <code>null</code>.
	 * 
	 * @see #zeroPackageID()
	 * @see #makePackageID(byte[])
	 * @see PackageID#BaseUniversalLabel
	 */
	public final static PackageID makePackageID(
			byte[] univeralLabel, 
			@UInt8 byte length, 
			@UInt8 byte instanceHigh, 
			@UInt8 byte instanceMid, 
			@UInt8 byte instanceLow, 
			AUID materialNumber) 
		throws NullPointerException {
		
		return new PackageIDImpl(univeralLabel, length, 
				instanceHigh, instanceMid, instanceLow, materialNumber);
	}
	
	/**
	 * <p>Make a new package identifier from the given byte array of elements. The array should be 
	 * 32&nbsp;bytes in length and in the form of a UMID. If the array is too short or too long, it will
	 * be padded with zeros or truncated to make it 32&nbsp;bytes. The passed array value is cloned to avoid
	 * external changes to the array affecting this value.</p>
	 * 
	 * <p>If the bytes of the material number part appear to be an unswapped universal label value,
	 * these will be swapped to an AUID-appropriate internal representation.</p>
	 * 
	 * @param bytes Byte array representing the elements of a package identifier.
	 * @return Package identifier created from the given array of bytes.
	 * 
	 * @throws NullPointerException The given array of byte elements is <code>null</code>.
	 * 
	 * @see #zeroPackageID()
	 * @see #makePackageID(byte[], byte, byte, byte, byte, AUID)
	 */
	public final static PackageID makePackageID(
			byte[] bytes)
		throws NullPointerException {
		
		return new PackageIDImpl(bytes);
	}
	
	/**
	 * <p>Generate a new package identifier appropriate for use in D-Cinema packaging 
	 * using the given material number using the material number 
	 * generation method. As for {@linkplain #dCinemaUMID() completely random D-Cinema UMIDs}, the material type is
	 * {@linkplain tv.amwa.maj.enumeration.MaterialType#NotIdentified not identified} and the instance
	 * number generation is {@linkplain tv.amwa.maj.record.InstanceNumberGeneration#NotDefined not defined}.</p>
	 * 
	 * @param materialNumber Material number part of the new package identifier.
	 * @return Package identifier appropriate for use in D-Cinema packaging.
	 * 
	 * @throws NullPointerException Cannot use a <code>null</code> material number to generate a package identifier.
	 * 
	 * @see #dCinemaUMID()
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration)
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, AUID)
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, byte[])
	 */
	public final static PackageID generatePackageID(
			AUID materialNumber)
		throws NullPointerException {
		
		if (materialNumber == null)
			throw new NullPointerException("Cannot generate a package identifier from a null material number.");
		
		return PackageIDImpl.umidFactory(
				tv.amwa.maj.enumeration.MaterialType.NotIdentified, 
			    tv.amwa.maj.record.MaterialNumberGeneration.UUID_UL, 
			    tv.amwa.maj.record.InstanceNumberGeneration.NotDefined, 
			    materialNumber.getAUIDValue());
	}
	
	/**
	 * <p>Generate a new package identifier according to the given material type, instance number
	 * generation method and material number generation method. The material type and generation
	 * methods are as described in SMPTE&nbsp;330M.</p>
	 * 
	 * <p>The supported material number generation methods are as follows:</p>
	 * 
	 * <ul>
	 *  
	 *  <li>{@link MaterialNumberGeneration#SMPTE} - Creates a material number from the system clock,
	 *  current date, a two byte random number and a 6&nbsp;byte system identifier. This approach is documented in 
	 *  section&nbsp;A.1 of SMPTE&nbsp;330M and should ensure a high degree of identifier uniqueness. The 
	 *  system identifier must be provided as the first 6&nbsp;bytes of the <em>extra data</em> parameter. Java&nbsp;1.5 does not 
	 *  provide a means to extract the ethernet address of the system on which a virtual machine is running 
	 *  but a locally administered value that is a combination of bytes taken from the system's IP address 
	 *  and domain name should be sufficient. Just such a value is provided by calling 
	 *  {@link tv.amwa.maj.util.Utilities#createLocalHostID(int) Utilities.createLocalHostID(6)}.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#UUID_UL} - Generates a material number from the UUID or universal
	 *  label provided as the 16&nbsp;bytes passed as the extra data parameter. The method assumes that the byte 
	 *  reordering documented in section&nbsp;A.2 of SMPTE&nbsp;330M has already taken place. No validation
	 *  of the UUID or universal label is carried out. In fact, this method is implemented here as a 
	 *  <em>dumb</em> pass through of the given value directly to the 16-bytes of the material number of 
	 *  the new package identifier.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#Masked} - Generates a material number according to the SMPTE
	 *  method and then uses an MD5 function on the value to mask the details, as described in section A.3
	 *  of SMPTE&nbsp;330M. This masking is normally done for security reasons so as to hide details of creation times 
	 *  and systems identification. Once masked, the time and host id values cannot be easily recovered and
	 *  the masking function ensures the same degree of uniqueness as the 
	 *  {@linkplain MaterialNumberGeneration#SMPTE SMPTE method}.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#IEEE1394} - Generates a material number according to the IEEE&nbsp;1394
	 *  network method, as described in section A.4 of SMPTE&nbsp;330M. The first 8&nbsp;bytes are generated 
	 *  from the system clock, current date and a random number. The last 8&nbsp;bytes are the device node identifier that
	 *  must be provided as the <em>extra bytes</em> parameter. The device node of the
	 *  system on which the virtual machine is running is not available in the core Java API version&nbsp;1.5. A locally
	 *  derived host identifier created from the system's IP address and domain name can be generated by calling
	 *  {@link Utilities#createLocalHostID(int) Utilities.createLocalHostID(8)}.</p>  
	 *  </li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#NotDefined} - A pseudo random number is generated for the
	 *  128-bits of data representing the material number, using the {@link java.security.SecureRandom}
	 *  class.</li>
	 *  
	 * </ul>
	 * 
	 * <p>The supported instance generation methods are documented in more detail in 
	 * {@link tv.amwa.maj.record.PackageID#nextInstance()}, with the instance number 
	 * initialized as follows:</p>
	 * 
	 * <ul>
	 *  <li>{@link InstanceNumberGeneration#LocalRegistration} - All three instance bytes are set to
	 *  zero.</li>
	 *  <li>{@link  InstanceNumberGeneration#CopyAndPseudoRandom16Bit} - The instance high byte is
	 *  set to 0 and the other two bytes are set to random values.</li>
	 *  <li>{@link InstanceNumberGeneration#PseudoRandom24Bit} - The instance high, middle and low 
	 *  values are set using 24 randomly generated bits.</li>
	 *  <li>{@link InstanceNumberGeneration#LiveStream} - Not supported and throws a 
	 *  {@link GenerationMethodNotSupportedException}.</li>
	 *  <li>{@link InstanceNumberGeneration#NotDefined} - Same as PseudoRandom24Bit.</li>
	 * </ul>
	 * 
	 * <p>The chosen type of instance number generated will define the behaviour of subsequent
	 * calls to {@link PackageID#nextInstance()} for the new package identifier.</p>
	 * 
	 * @param materialType Type of material represented by the new package identifier.
	 * @param materialNumberGeneration Method to use to generate the material number of the package identifier.
	 * @param instanceNumberGeneration Method to use to generate the first and next instance
	 * numbers for the package identifier.
	 * @return Newly manufactured package identifier, which is a SMPTE UMID according to the given specification.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and all are
	 * required.
	 * @throws GenerationMethodNotSupportedException The given material or instance number generation
	 * method is not supported.
	 * 
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, AUID)
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, byte[])
	 * @see #generatePackageID(AUID)
	 * @see #dCinemaUMID()
	 */
	public final static PackageID generatePackageID(
			MaterialType materialType,
			InstanceNumberGeneration instanceNumberGeneration,
			MaterialNumberGeneration materialNumberGeneration) 
		throws NullPointerException,
			GenerationMethodNotSupportedException {
		
		return PackageIDImpl.umidFactory(materialType, materialNumberGeneration, 
				instanceNumberGeneration, new byte[] { });
	}
	
	/**
	 * <p>Generate a new package identifier according to the given material type, instance number
	 * generation method and material number generation method. The material type and generation
	 * methods are as described in SMPTE&nbsp;330M.</p>
	 * 
	 * <p>The supported material number generation methods are as follows:</p>
	 * 
	 * <ul>
	 *  
	 *  <li>{@link MaterialNumberGeneration#SMPTE} - Creates a material number from the system clock,
	 *  current date, a two byte random number and a 6&nbsp;byte system identifier. This approach is documented in 
	 *  section&nbsp;A.1 of SMPTE&nbsp;330M and should ensure a high degree of identifier uniqueness. The 
	 *  system identifier must be provided as the first 6&nbsp;bytes of the <em>extra data</em> parameter. Java&nbsp;1.5 does not 
	 *  provide a means to extract the ethernet address of the system on which a virtual machine is running 
	 *  but a locally administered value that is a combination of bytes taken from the system's IP address 
	 *  and domain name should be sufficient. Just such a value is provided by calling 
	 *  {@link tv.amwa.maj.util.Utilities#createLocalHostID(int) Utilities.createLocalHostID(6)}.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#UUID_UL} - Generates a material number from the UUID or universal
	 *  label provided as the 16&nbsp;bytes passed as the extra data parameter. The method assumes that the byte 
	 *  reordering documented in section&nbsp;A.2 of SMPTE&nbsp;330M has already taken place. No validation
	 *  of the UUID or universal label is carried out. In fact, this method is implemented here as a 
	 *  <em>dumb</em> pass through of the given value directly to the 16-bytes of the material number of 
	 *  the new package identifier.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#Masked} - Generates a material number according to the SMPTE
	 *  method and then uses an MD5 function on the value to mask the details, as described in section A.3
	 *  of SMPTE&nbsp;330M. This masking is normally done for security reasons so as to hide details of creation times 
	 *  and systems identification. Once masked, the time and host id values cannot be easily recovered and
	 *  the masking function ensures the same degree of uniqueness as the 
	 *  {@linkplain MaterialNumberGeneration#SMPTE SMPTE method}.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#IEEE1394} - Generates a material number according to the IEEE&nbsp;1394
	 *  network method, as described in section A.4 of SMPTE&nbsp;330M. The first 8&nbsp;bytes are generated 
	 *  from the system clock, current date and a random number. The last 8&nbsp;bytes are the device node identifier that
	 *  must be provided as the <em>extra bytes</em> parameter. The device node of the
	 *  system on which the virtual machine is running is not available in the core Java API version&nbsp;1.5. A locally
	 *  derived host identifier created from the system's IP address and domain name can be generated by calling
	 *  {@link Utilities#createLocalHostID(int) Utilities.createLocalHostID(8)}.</p>  
	 *  </li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#NotDefined} - A pseudo random number is generated for the
	 *  128-bits of data representing the material number, using the {@link java.security.SecureRandom}
	 *  class.</li>
	 *  
	 * </ul>
	 * 
	 * <p>The supported instance generation methods are documented in more detail in 
	 * {@link tv.amwa.maj.record.PackageID#nextInstance()}, with the instance number 
	 * initialized as follows:</p>
	 * 
	 * <ul>
	 *  <li>{@link InstanceNumberGeneration#LocalRegistration} - All three instance bytes are set to
	 *  zero.</li>
	 *  <li>{@link  InstanceNumberGeneration#CopyAndPseudoRandom16Bit} - The instance high byte is
	 *  set to 0 and the other two bytes are set to random values.</li>
	 *  <li>{@link InstanceNumberGeneration#PseudoRandom24Bit} - The instance high, middle and low 
	 *  values are set using 24 randomly generated bits.</li>
	 *  <li>{@link InstanceNumberGeneration#LiveStream} - Not supported and throws a 
	 *  {@link GenerationMethodNotSupportedException}.</li>
	 *  <li>{@link InstanceNumberGeneration#NotDefined} - Same as PseudoRandom24Bit.</li>
	 * </ul>
	 * 
	 * <p>The chosen type of instance number generated will define the behaviour of subsequent
	 * calls to {@link PackageID#nextInstance()} for the new package identifier.</p>
	 * 
	 * @param materialType Type of material represented by the new package identifier.
	 * @param materialNumberGeneration Method to use to generate the material number of the package identifier.
	 * @param instanceNumberGeneration Method to use to generate the first and next instance
	 * numbers for the package identifier.
	 * @param extraData Additional data required by the material number generation method.
	 * @return Newly manufactured package identifier, which is a SMPTE UMID according to the given specification.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and all are
	 * required.
	 * @throws GenerationMethodNotSupportedException The given material or instance number generation
	 * method is not supported.
	 * 
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration)
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, AUID)
	 * @see #generatePackageID(AUID)
	 * @see #dCinemaUMID()
	 */
	public final static PackageID generatePackageID(
			MaterialType materialType,
			InstanceNumberGeneration instanceNumberGeneration,
			MaterialNumberGeneration materialNumberGeneration,
			byte[] extraData) 
		throws NullPointerException,
			GenerationMethodNotSupportedException {
		
		return PackageIDImpl.umidFactory(materialType, materialNumberGeneration, 
				instanceNumberGeneration, extraData);
	}

	/**
	 * <p>Generate a new package identifier according to the given material type, instance number
	 * generation method and material number generation method. The material type and generation
	 * methods are as described in SMPTE&nbsp;330M.</p>
	 * 
	 * <p>The supported material number generation methods are as follows:</p>
	 * 
	 * <ul>
	 *  
	 *  <li>{@link MaterialNumberGeneration#SMPTE} - Creates a material number from the system clock,
	 *  current date, a two byte random number and a 6&nbsp;byte system identifier. This approach is documented in 
	 *  section&nbsp;A.1 of SMPTE&nbsp;330M and should ensure a high degree of identifier uniqueness. The 
	 *  system identifier must be provided as the first 6&nbsp;bytes of the <em>extra data</em> parameter. Java&nbsp;1.5 does not 
	 *  provide a means to extract the ethernet address of the system on which a virtual machine is running 
	 *  but a locally administered value that is a combination of bytes taken from the system's IP address 
	 *  and domain name should be sufficient. Just such a value is provided by calling 
	 *  {@link tv.amwa.maj.util.Utilities#createLocalHostID(int) Utilities.createLocalHostID(6)}.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#UUID_UL} - Generates a material number from the UUID or universal
	 *  label provided as the 16&nbsp;bytes passed as the extra data parameter. The method assumes that the byte 
	 *  reordering documented in section&nbsp;A.2 of SMPTE&nbsp;330M has already taken place. No validation
	 *  of the UUID or universal label is carried out. In fact, this method is implemented here as a 
	 *  <em>dumb</em> pass through of the given value directly to the 16-bytes of the material number of 
	 *  the new package identifier.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#Masked} - Generates a material number according to the SMPTE
	 *  method and then uses an MD5 function on the value to mask the details, as described in section A.3
	 *  of SMPTE&nbsp;330M. This masking is normally done for security reasons so as to hide details of creation times 
	 *  and systems identification. Once masked, the time and host id values cannot be easily recovered and
	 *  the masking function ensures the same degree of uniqueness as the 
	 *  {@linkplain MaterialNumberGeneration#SMPTE SMPTE method}.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#IEEE1394} - Generates a material number according to the IEEE&nbsp;1394
	 *  network method, as described in section A.4 of SMPTE&nbsp;330M. The first 8&nbsp;bytes are generated 
	 *  from the system clock, current date and a random number. The last 8&nbsp;bytes are the device node identifier that
	 *  must be provided as the <em>extra bytes</em> parameter. The device node of the
	 *  system on which the virtual machine is running is not available in the core Java API version&nbsp;1.5. A locally
	 *  derived host identifier created from the system's IP address and domain name can be generated by calling
	 *  {@link Utilities#createLocalHostID(int) Utilities.createLocalHostID(8)}.</p>  
	 *  </li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#NotDefined} - A pseudo random number is generated for the
	 *  128-bits of data representing the material number, using the {@link java.security.SecureRandom}
	 *  class.</li>
	 *  
	 * </ul>
	 * 
	 * <p>The supported instance generation methods are documented in more detail in 
	 * {@link tv.amwa.maj.record.PackageID#nextInstance()}, with the instance number initialized as follows:</p>
	 * 
	 * <ul>
	 *  <li>{@link InstanceNumberGeneration#LocalRegistration} - All three instance bytes are set to
	 *  zero.</li>
	 *  <li>{@link  InstanceNumberGeneration#CopyAndPseudoRandom16Bit} - The instance high byte is
	 *  set to 0 and the other two bytes are set to random values.</li>
	 *  <li>{@link InstanceNumberGeneration#PseudoRandom24Bit} - The instance high, middle and low 
	 *  values are set using 24 randomly generated bits.</li>
	 *  <li>{@link InstanceNumberGeneration#LiveStream} - Not supported and throws a 
	 *  {@link GenerationMethodNotSupportedException}.</li>
	 *  <li>{@link InstanceNumberGeneration#NotDefined} - Same as PseudoRandom24Bit.</li>
	 * </ul>
	 * 
	 * <p>The chosen type of instance number generated will define the behaviour of subsequent
	 * calls to {@link PackageID#nextInstance()} for the new package identifier.</p>
	 * 
	 * @param materialType Type of material represented by the new package identifier.
	 * @param materialNumberGeneration Method to use to generate the material number of the package identifier.
	 * @param instanceNumberGeneration Method to use to generate the first and next instance
	 * numbers for the package identifier.
	 * @param materialNumber Material number to pass through to the last 16&nbsp;bytes of the package identifier
	 * where appropriate.
	 * @return Newly manufactured package identifier, which is a SMPTE UMID according to the given specification.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and all are
	 * required.
	 * @throws GenerationMethodNotSupportedException The given material or instance number generation
	 * method is not supported.
	 * 
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration)
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, byte[])
	 * @see #generatePackageID(AUID)
	 * @see #dCinemaUMID()
	 */
	public final static PackageID generatePackageID(
			MaterialType materialType,
			InstanceNumberGeneration instanceNumberGeneration,
			MaterialNumberGeneration materialNumberGeneration,
			AUID materialNumber) 
		throws NullPointerException,
			GenerationMethodNotSupportedException {
		
		return PackageIDImpl.umidFactory(materialType, materialNumberGeneration, 
				instanceNumberGeneration, materialNumber.getAUIDValue());
	}
	
	/**
	 * <p>Generates a package identifier appropriate for use in D-Cinema packaging, as defined in 
	 * section 6.3.1 of SMPTE&nbsp;429-3-2007. This is a value with no material
	 * type defined, no instance number and a random UUID used for the material
	 * number.</p>
	 * 
	 * <p>For example:</p>
	 * 
	 * <p><center><code>urn:smpte:umid:0602b34.01010105.01010f20.13000000.9ADB7935.22904D96.86656A99.78529D78</code></center></p>
	 *
	 * @return Package identifier appropriate for use in D-Cinema packaging.
	 * 
	 * @see #generatePackageID(AUID)
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration)
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, AUID)
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, byte[])
	 * @see #randomUMID()
	 */
	public final static PackageID dCinemaUMID() {
		
		return PackageIDImpl.umidFactory(
				tv.amwa.maj.enumeration.MaterialType.NotIdentified, 
			    tv.amwa.maj.record.MaterialNumberGeneration.UUID_UL, 
			    tv.amwa.maj.record.InstanceNumberGeneration.NotDefined, 
			    randomAUID().getAUIDValue());
	}
	
	/**
	 * <p>Generates a UMID with a random material number. A random UMID generated by this forge
	 * is actually the same as a {@linkplain #dCinemaUMID() D-Cinema UMID}.</p>
	 * 
	 * @return Package identifier with a random material number.
	 * 
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration)
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, AUID)
	 * @see #generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, byte[])
	 * @see #dCinemaUMID()
	 */
	public final static PackageID randomUMID() {
		
		return dCinemaUMID(); 
	}
	
	// Product version
	
	/**
	 * <p>Makes an instance of this product version class by parsing the given pseudo-XML version. 
	 * This method will create an instance from the result of calling the {@link ProductVersion#toString()} 
	 * method. The pseudo-XML format is illustrated below:</p>
	 * 
	 * <pre>
	 *     &lt;ProductVersion&gt;
	 *       &lt;Major&gt;0&lt;/Major&gt;
	 *       &lt;Minor&gt;3&lt;/Minor&gt;
	 *       &lt;Tertiary&gt;1&lt;/Tertiary&gt;
	 *       &lt;PatchLevel&gt;2&lt;/PatchLevel&gt;
	 *       &lt;BuildType&gt;VersionDebug&lt;/BuildType&gt;
	 *     &lt;/ProductVersion&gt;
	 * </pre>
	 * 
	 * <p>If any tags are missing, default values are used and the method returns successfully.
	 * This method ignores any namespace prefix found in an element name.</p>
	 * 
	 * @param versionAsAString Pseudo-XML string to convert into a product version instance.
	 * @return Product version created from the given string.
	 * 
	 * @throws NullPointerException The given version string is <code>null</code>.
	 * @throws ParseException Parse error creating a numerical value (major, minor, tertiary, patch level) 
	 * or the given build type does not match the tokens of the {@linkplain ProductReleaseType product
	 * release type} enumeration.
	 */
	public final static ProductVersion parseProductVersion(
			String versionAsAString)
		throws NullPointerException,
			ParseException {
		
		return ProductVersionImpl.parseFactory(versionAsAString);
	}
	
    /**
     * <p>Make a product version value that represents the version of an application.</p>
     * 
	 * @param major Application's major version number.
	 * @param minor Application's minor version number.
	 * @param tertiary Application's tertiary version number.
	 * @param patchLevel Application's patch level.
	 * @param type Application's product release type.
	 * 
	 * @throws NullPointerException The product release type argument is <code>null</code>.
	 * @throws IllegalArgumentException One of more of the major, minor, tertiary or patch level
	 * values is negative. 
	 * 
	 * @see #zeroProductVersion()
	 */
	public final static ProductVersion makeProductVersion(
			@UInt16 short major, 
			@UInt16 short minor, 
			@UInt16 short tertiary, 
			@UInt16 short patchLevel, 
			ProductReleaseType type)
		throws NullPointerException,
			IllegalArgumentException {
		
		return new ProductVersionImpl(major, minor, tertiary, patchLevel, type);
	}
	
	/**
	 * <p>Make a zero product version value, with all numerical values set to zero 
	 * and the release type set to {@linkplain ProductReleaseType#Unknown unknown}.</p>
	 * 
	 * @return Zero product version value.
	 * 
	 * @see #makeProductVersion(short, short, short, short, ProductReleaseType)
	 */
	public final static ProductVersion zeroProductVersion() {
		
		return new ProductVersionImpl(
				(short) 0, (short) 0, (short) 0, (short) 0, ProductReleaseType.Unknown);
	}
	
	// Rational
	
	/**
	 * <p>Make a rational value by parsing a string representation.
	 * The string representation is expected to take the form of the numerator, followed
	 * by a slash character ('<code>/</code>'), followed by the denominator, e.g. 
	 * "<code>-123/4567</code>". This format is as created by {@link Rational#toString()}.</p>
	 * 
	 * @param rationalAsString String to use to create a rational value.
	 * @return Corresponding rational value.
	 * 
	 * @throws NullPointerException The rational as string argument is <code>null</code>.
	 * @throws PraseException The string is not formatted as a rational number 
	 * representation.
	 * 
	 * @see Rational#toString()
	 */
	public final static Rational parseRational(
			String rationalAsString) 
		throws NullPointerException,
			ParseException {
		
		return RationalImpl.parseFactory(rationalAsString);
	}
	
	/**
	 * <p>Make a rational value from its numerator and denominator.</p>
	 * 
	 * @param numerator 
	 * @param denominator
	 * @return Rational value as specified by the arguments.
	 * 
	 * @see #zeroRational()
	 */
	public final static Rational makeRational(
			@Int32 int numerator, 
			@Int32 int denominator) {
		
		return new RationalImpl(numerator, denominator);
	}
	
	/**
	 * <p>Make a rational value of zero, with numerator of&nbsp;0 and denominator 
	 * of&nbsp;1.</p>
	 * 
	 * @return Zero rational value.
	 * 
	 * @see #makeRational(int, int)
	 */
	public final static Rational zeroRational() {
		
		return new RationalImpl();
	}
	
	// RGBAComponent
	
	/**
	 * <p>Make a new element of an {@linkplain TypeDefinitions#RGBALayout RGBA layout array} 
	 * by parsing the given pseudo-XML
	 * representation. This method will create an instance from the result of calling the 
	 * {@link RGBAComponent#toString()} method. The pseudo-XML format is illustrated below:</p>
	 * 
	 * <pre>
	 *   &lt;RGBAComponent&gt;
	 *     &lt;Code&gt;CompRed&lt;/Code&gt;
	 *     &lt;ComponentSize&gt;8&lt;/ComponentSize&gt;
	 *   &lt;/RGBAComponent&gt;
	 * </pre>
	 * 
	 * <p>If any tags are missing, default values are used and the method returns successfully.
	 * This method ignores any namespace prefix found in an element name.</p>
	 * 
	 * @param rgbaComponentAsString Pseudo-XML string to convert into a RGBA component instance.
	 * @return Newly created RGBA component instance.
	 * 
	 * @throws NullPointerException The given psuedo-XML representation is <code>null</code>.
	 * @throws ParseException The component size could not be parsed into a byte value or the 
	 * given component code was not recognized as an element of the 
	 * {@linkplain RGBAComponentKind RGBA component kind enumeration}.
	 * 
	 * @see RGBAComponent#toString()
	 * @see TypeDefinitions#RGBAComponent
	 * @see TypeDefinitions#RGBALayout
	 */
	public final static RGBAComponent parseRGBAComponent(
			String rgbaComponentAsString) 
		throws NullPointerException,
			ParseException {
		
		return RGBAComponentImpl.parseFactory(rgbaComponentAsString);
	}
	
	/**
     * <p>Make an element of an {@linkplain TypeDefinitions#RGBALayout RGBA layout array}.</p>
	 * 
	 * @param code Code for the component.
	 * @param size Size of the component in bits.
	 * 
	 * @throws NullPointerException The code argument is <code>null</code>.
	 * @throws IllegalArgumentException The size argument is negative.
	 * 
	 * @see #zeroRGBAComponent()
	 * @see TypeDefinitions#RGBALayout
	 */
	public final static RGBAComponent makeRGBAComponent(
			RGBAComponentKind code, 
			@UInt8 byte size) 
		throws NullPointerException,
			IllegalArgumentException {
		
		return new RGBAComponentImpl(code, size);
	}
	
	/**
	 * <p>Make an element of an {@linkplain TypeDefinitions#RGBALayout RGBA layout array}
	 * with a {@link RGBAComponentKind#Null} code and a size of <code>0</code>.</p>
	 * 
	 * @see #makeRGBAComponent(RGBAComponentKind, byte)
	 * @see TypeDefinitions#RGBALayout
	 */
	public final static RGBAComponent zeroRGBAComponent() {
		
		return new RGBAComponentImpl();
	}
	
	// Timecode value
	
	/**
	 * <p>Makes a SMPTE-style string representation of a timecode and creates a MAJ API timecode value,
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
	 * @param timecodeAsAString String representation of a timecode value.
	 * @return A timecode value created by parsing the given textual representation.
	 * 
	 * @throws NullPointerException The given string representation of a timecode is actually <code>null</code>.
	 * @throws ParseException It is not possible to parse the given value to a MAJ API timecode value.
	 * 
	 * @see TimecodeValue#toString()
	 * @see #parseTimecode(String, short, boolean)
	 */	
	public final static TimecodeValue parseTimecode(
			String timecodeAsAString) 
		throws NullPointerException,
			ParseException {
		
		return TimecodeValueImpl.parseTimecode(timecodeAsAString);
	}
	
	/**
	 * <p>Makes a SMPTE-style string representation of a timecode and creates a MAJ API timecode value, 
	 * with all parameters specified. The method will try and parse both drop and non-drop values, working with both SMPTE-12M values
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
	 * @param timecodeAsAString String representation of a timecode value.
	 * @param explicitFramesPerSecond Explicit value for the frames per second of this timecode value.
	 * @param explicitDropFrameIndicator Explicit indicator as to whether this timecode value requires
	 * allowance for drop frames.
	 * @return A timecode value created by parsing the given textual representation and explicit values.
	 * 
	 * @throws NullPointerException The given string representation of a timecode is actually <code>null</code>.
	 * @throws ParseException It is not possible to parse the given value to a MAJ API timecode value.
	 * @throws IllegalArgumentException The given frames per second value cannot be negative or zero.
	 * 
	 * @see TimecodeValue#toString()
	 * @see #parseTimecode(String)
	 */	
	public final static TimecodeValue parseTimecode(
			String timecodeAsAString,
			@UInt16 short explicitFramesPerSecond,
			boolean explicitDropFrameIndicator)
		throws NullPointerException,
			ParseException,
			IllegalArgumentException {
		
		return TimecodeValueImpl.parseTimecode(timecodeAsAString, 
				explicitFramesPerSecond, explicitDropFrameIndicator);
	}
			
	/**
	 * <p>Make a timecode value from its actual component parts.</p>
	 * 
	 * @param startFrame Specifies the timecode as the number of frames offset from
	 * the start of the video or audio.
	 * @param fps Frames per second of the videotape or audio tape.
	 * @param drop Indicates whether the timecode is drop (<code>true</code> value) or nondrop 
	 * (<code>false</code> value).
	 * 
	 * @throws IllegalArgumentException The frames per second value is not a positive value. 
	 * 
	 * @see #calculateTimecode(short, short, short, short, short, boolean)
	 * @see #zeroTimecode()
	 */
	public final static TimecodeValue makeTimecode(
			@FrameOffset long startFrame, 
			@UInt16 short fps,			
			boolean drop) 
		throws IllegalArgumentException {
		
		return new TimecodeValueImpl(drop, startFrame, fps);
	}
	
	/** 
	 * <p>For lower frame rates (up to 30), calculate a new timecode value from the given hours, minutes, 
	 * seconds and frames according to the given number of frames per second and whether the timecode value 
	 * requires drop frame calculations. Internally, these values are converted to a frame offset value.</p>
	 * 
	 * <p>This method does not apply strict boundaries. Negative values and values greater than the
	 * acceptable bounds when displaying a timecode will be accepted and mapped to an appropriate offset.
	 * For example, the following three calls to this method create equivalent timecode values:</p>
	 * 
	 * <pre>
	 * Forge.calculateTimecode(0, 0, 63,   0, 25, false);
	 * Forge.calculateTimecode(0, 1, 03,   0, 25, false);
	 * Forge.calculateTimecode(0, 1, 04, -25, 25, false);
	 * </pre>
	 *
	 * @param hours Number of hours since the start of an item represented by this timecode.
	 * @param minutes Number of minutes in the hour of an item represented by this timecode.
	 * @param seconds Number of seconds in the minute of an item represented by this timecode.
	 * @param frames Number of frames through the second of an item represented by this timecode.
	 * @param fps Number of frames per second for this timecode value.
	 * @param drop Does this timecode require drop frame calculation?
	 * 
	 * @throws IllegalArgumentException The number of frames per second is not a positive (greater than zero)
	 * number.
	 * 
	 * @see #makeTimecode(long, short, boolean)
	 * @see #calculateTimecode(short, short, short, short, short, short, boolean)
	 * @see #zeroTimecode()
	 */
	public final static TimecodeValue calculateTimecode(
			short hours,
			short minutes,
			short seconds,
			short frames,
			@UInt16 short fps, 
			boolean drop) 
		throws IllegalArgumentException {
		
		return new TimecodeValueImpl(fps, hours, minutes, seconds, frames, drop);
	}
	
	/** 
	 * <p>For higher frame rates (greater than 30), calculate a new timecode value from the given hours, 
	 * minutes, seconds, frames and frame pair according to the
	 * given number of frames per second and whether the timecode value requires drop frame calculations.
	 * Internally, these values are converted to a frame offset value.</p>
	 * 
	 * <p>This method does not apply strict boundaries. Negative values and values greater than the
	 * acceptable bounds when displaying a timecode will be accepted and mapped to an appropriate offset.</p>
	 * 
	 * <p>Some examples:</p>
	 * 
	 * <pre>
	 * Forge.calculateTimecode(0, 0, 63,   0, 0, 50, false);
	 * Forge.calculateTimecode(0, 1, 03,   0, 1, 60, true);
	 * Forge.calculateTimecode(0, 1, 04, -25, 0, 60, true);
	 * </pre>
	 *
	 * @param hours Number of hours since the start of an item represented by this timecode.
	 * @param minutes Number of minutes in the hour of an item represented by this timecode.
	 * @param seconds Number of seconds in the minute of an item represented by this timecode.
	 * @param frames Number of frames through the second of an item represented by this timecode.
	 * @param framePair For frame rates higher than 30fps, which sub-frame is being referenced. This is 
	 * normally 0 for the first and 1 for the second.
	 * @param fps Number of frames per second for this timecode value.
	 * @param drop Does this timecode require drop frame calculation?
	 * 
	 * @throws IllegalArgumentException The number of frames per second is not a positive (greater than zero)
	 * number.
	 * 
	 * @see #makeTimecode(long, short, boolean)
	 * @see #calculateTimecode(short, short, short, short, short, boolean)
	 * @see #zeroTimecode()
	 */
	public final static TimecodeValue calculateTimecode(
			short hours,
			short minutes,
			short seconds,
			short frames,
			short framePair,
			@UInt16 short fps, 
			boolean drop) 
		throws IllegalArgumentException {
		
		return new TimecodeValueImpl(fps, hours, minutes, seconds, frames, framePair, drop);
	}

	/**
	 * <p>Make a zero timecode value, with a zero start frame, zero frames per second
	 * and a zero drop frame.</p>
	 * 
	 * @return Zero timecode value.
	 * 
	 * @see #makeTimecode(long, short, boolean)
	 * @see #calculateTimecode(short, short, short, short, short, boolean)
	 */
	// TODO test toString on one of these!
	public final static TimecodeValue zeroTimecode() {
		
		return new TimecodeValueImpl();
	}
	
	/**
	 * <p>Calculate the duration of a piece of media from its start timecode and end timecode. The value returned
	 * is a timecode representing the duration. Both values must be specified with the same number of frames per
	 * second and matching drop timecode specifications. The end value must be the same or greater than the 
	 * start value.</p>
	 * 
	 * <p>To calculate the number of frames of an item of media, call {@link TimecodeValue#getStartTimecode()} 
	 * on the value returned by this method.</p>
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
	public final static TimecodeValue calculateDuration(
			TimecodeValue startValue,
			TimecodeValue endValue) 
	throws NullPointerException,
		IllegalArgumentException {
		
		return TimecodeValueImpl.calculateDuration(startValue, endValue);
	}
	
	/**
	 * <p>Calculate the end timecode for a segment of media from its start timecode and a timecode 
	 * value representing its duration. Both values must be specified with the same number of frames per
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
	public final static TimecodeValue calculateEndTimecode(
			TimecodeValue startValue,
			TimecodeValue duration) 
		throws NullPointerException,
			IllegalArgumentException {
		
		return TimecodeValueImpl.calculateEndTimecode(startValue, duration);
	}
	
	// TimeStamp
	
	/**
	 * <p>Make a time stamp value from a string that is formatted according to the UTC standards. If
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
	 * number of milliseconds can be expressed by 100ths of a second.</p>
	 * 
	 * <p>Values in this format can be created using {@link TimeStamp#toString()}.</p>
	 *
	 * @param timeStampAsAString String representation of a time stamp value.
	 * @return Newly created MAJ time stamp value from given string value.
	 * 
	 * @throws ParseException A problem with the given string prevents it from being converted
	 * to a time stamp value, or the resulting value in unacceptable.
	 * @throws NullPointerException The given time stamp as a string value is <code>null</code>.
	 * 
	 * @see TimeStamp#toString()
	 * @see #parseTime(String)
	 * @see #parseDate(String)
	 */
	public final static TimeStamp parseTimeStamp(
			String timeStampAsAString) 
		throws NullPointerException,
			ParseException {
		
		return TimeStampImpl.parseFactory(timeStampAsAString);
	}
	
	/**
	 * <p>Make a time stamp using the components of the given date and time values.
	 * This method is strict in its interpretation of date and time values.</p>
	 * 
	 * @param date Date component of the time stamp to create.
	 * @param time Time component of the time stamp to create.
	 * @return Timestamp created from the given date and time values.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>.
	 * @throws IllegalArgumentException The resulting time stamp value would not be valid.
	 * 
	 * @see #makeTimeStamp(Calendar)
	 * @see #makeTimeStamp(short, byte, byte, byte, byte, byte, byte)
	 * @see #now()
	 */
	public final static TimeStamp makeTimeStamp(
			DateStruct date,
			TimeStruct time) 
		throws NullPointerException,
			IllegalArgumentException {
		
		return new TimeStampImpl(date, time);
	}
	
	/**
	 * <p>Make a time stampe using all of the components of the date and time parts.
	 * This method is strict in its interpretation of date and time values.</p>
	 * 
	 * @param year Year component of the time stamp.
	 * @param month Month component of the time stamp, 1-based, with 1 for January, 2 for Febrary etc..
	 * @param day Day of the month component of the time stamp.
	 * @param hour Hour component of the time stamp.
	 * @param minute Minute component of the time stamp.
	 * @param second Second component of the time stamp.
	 * @param fraction Fraction of a second component of the time stamp, measured in 1/250ths of a second.
	 * @return Timestamp created from its component parts.
	 * 
	 * @throws IllegalArgumentException The resulting time stamp value would not be valid.
	 * 
	 * @see #makeTimeStamp(DateStruct, TimeStruct)
	 * @see #makeTimeStamp(Calendar)
	 * @see #now()
	 */
	public final static TimeStamp makeTimeStamp(
			@Int16 short year,
			@UInt8 byte month,
			@UInt8 byte day,
			@UInt8 byte hour, 
			@UInt8 byte minute, 
			@UInt8 byte second, 
			@UInt8 byte fraction)
		throws IllegalArgumentException {
		
		return new TimeStampImpl(
				makeDate(day, month, year), 
				makeTime(hour, minute, second, fraction));
	}
	
	/** 
	 * <p>Make a time stamp using a {@link java.util.Calendar} value.
	 * This method is strict in its interpretation of date and time values.</p>
	 * 
	 * <p>Note that all fields of the value will be preserved within the time stamp value and
	 * available on a call to {@link tv.amwa.maj.record.TimeStamp#getTimeStamp()}.</p>
	 *
	 * @param timeStampAsCalendar Calendar value to use to set the value of this time stamp.
	 * 
	 * @throws NullPointerException The given calendar value is <code>null</code>.
	 * @throws IllegalArgumentException The given calendar value is not a valid date or time.
	 * 
	 * @see #makeTimeStamp(DateStruct, TimeStruct)
	 * @see #makeTimeStamp(short, byte, byte, byte, byte, byte, byte)
	 * @see #now()
	 */
	public final static TimeStamp makeTimeStamp(
			Calendar timeStampAsCalendar) 
		throws NullPointerException,
			IllegalArgumentException {
		
		return new TimeStampImpl(timeStampAsCalendar);
	}
	
	/**
	 * <p>Make a timestamp representing the date and time now.</p>
	 * 
	 * @return Timestamp for the current date and time.
	 * 
	 * @see #todaysDate()
	 * @see #timeNow()
	 */
	public final static TimeStamp now() {
		
		return new TimeStampImpl();
	}
	
	// Time struct
	
	/**
	 * <p>Makes a new time value from a UTC-style string. The expected format of the
	 * string is "<code>HH:mm:ss.SSSZ</code>", where:</p>
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
	 * <p>Note that if a timezone value different from UTC ("+0000"),
	 * the time value will be converted to its UTC equivalent.</p>
	 * 
	 * @param timeAsAString UTC-style string time value.
	 * 
	 * @return Time structure representing the given string time value.
	 * 
	 * @throws NullPointerException Provided time as a string value is <code>null</code>.
	 * @throws ParseException The given value could not be converted into a time structure value.
	 * 
	 * @see TimeStruct#toString()
	 * @see #parseTimeStamp(String)
	 * @see #parseDate(String)
	 */
	public final static TimeStruct parseTime(
			String timeAsAString)
		throws NullPointerException,
			ParseException {
		
		return TimeStructImpl.parseFactory(timeAsAString);
	}
	
    /** 
     * <p>Make a time value from  its hour, minute and second component parts. The
     * fraction of a second is set to&nbsp;0.</p>
     * 
	 * @param hour Hour component of the time value, in the inclusive range 0 to 23.
	 * @param minute Minute component of the time value, in the inclusive range 0 to 59.
	 * @param second Second component of the time value, normally in the inclusive range 0 to 59.
	 * 
	 * @throws IllegalArgumentException One or more of the parameters is out of range.
	 * 
	 * @see #makeTime(Calendar)
	 * @see #makeTime(byte, byte, byte, byte)
	 * @see #timeNow()
	 */
	public final static TimeStruct makeTime(
			byte hour, 
			byte minute, 
			byte second) 
		throws IllegalArgumentException {
		
		return new TimeStructImpl(hour, minute, second);
	}
	
	/** 
     * <p>Make a time value from all of its component parts.</p>
     * 
	 * @param hour Hour component of the time value, in the inclusive range 0 to 23.
	 * @param minute Minute component of the time value, in the inclusive range 0 to 59.
	 * @param second Second component of the time value, normally in the inclusive range 0 to 59.
	 * @param fraction 1/250th of a second component of the time value, in the inclusive range
	 * 0&nbsp;to&nbsp;249.
	 * 
	 * @throws IllegalArgumentException One or more of the parameters is out of range.
	 * 
	 * @see #makeTime(Calendar)
	 * @see #makeTime(byte, byte, byte)
	 * @see #timeNow()
	 */
	public final static TimeStruct makeTime(
			byte hour, 
			byte minute, 
			byte second, 
			byte fraction) 
		throws IllegalArgumentException {
		
		return new TimeStructImpl(hour, minute, second, fraction);
	}
	
	/**
	 * <p>Make a new time structure from the given {@linkplain java.util.Calendar calendar} value.</p>
	 * 
	 * @param timeAsCalendar Calendar item to use to create a new time structure.
	 * 
	 * @throws NullPointerException The given calendar value for the time is <code>null</code>.
	 * @throws IllegalArgumentException The given calendar value contains out-of-range
	 * values.
	 * 
	 * @see #makeTime(byte, byte, byte)
	 * @see #makeTime(byte, byte, byte, byte)
	 * @see #timeNow()
	 */
	public final static TimeStruct makeTime(
			Calendar timeAsCalendar) 
		throws NullPointerException,
			IllegalArgumentException {
		
		return new TimeStructImpl(timeAsCalendar);
	}
	
	/**
	 * <p>Makes a time only value representing the time now.</p>
	 * 
	 * <p>To make a time stamp value representing both today's date and the time now,
	 * use {@link #now()}.</p>
	 * 
	 * @return The time now.
	 * 
	 * @see #todaysDate()
	 * @see #now()
	 */
	public final static TimeStruct timeNow() {
		
		return new TimeStructImpl();
	}
	
	// Version type
	
	/**
	 * <p>Make a version type value by parsing the given string representation. The value 
	 * must contain a&nbsp;'<code>.</code>' to be valid. The major and minor 
	 * parts of the version numbers, before and after the dot respectively, must range between
	 * 0&nbsp;and&nbsp;255.</p>
	 * 
	 * <p>This method will parse values created by {@link VersionType#toString()}.</p>
	 * 
	 * @param versionAsAString String representation of a version number.
	 * @return Version number value.
	 * 
	 * @throws NullPointerException The given version number string is <code>null</code>.
	 * @throws ParseException The given version number string causes parse or number range errors
	 * that prevent it from being converted into a version number value.
	 * 
	 * @see VersionType#toString()
	 */
	public final static VersionType parseVersion(
			String versionAsAString)
		throws NullPointerException,
			ParseException {
		
		return VersionTypeImpl.parseFactory(versionAsAString);
	}
	
	/**
	 * <p>Make a version type value from its component major and minor values.</p>
	 * 
	 * @param major Major part of the version number.
	 * @param minor Minor part of the version number.
	 * @return Version number created from the given components.
	 * 
	 * @see #zeroVersion()
	 */
	public final static VersionType makeVersion(
			@Int8 byte major, 
			@Int8 byte minor) {
		
		return new VersionTypeImpl(major, minor);
	}
	
	/**
	 * <p>Make a zero version number, where both major and minor parts are zero.</p>
	 * 
	 * @return  Zero version number.
	 * 
	 * @see #makeVersion(byte, byte)
	 */
	public final static VersionType zeroVersion() {
		
		return new VersionTypeImpl();
	}
	
	// Source reference value
	
	/**
	 * <p>Makes a new source reference that indicates the reference is to essence
	 * that is the original source. The source package identifier will be the 
	 * {@linkplain tv.amwa.maj.industry.Forge#zeroPackageID() zero package identifier}, 
	 * the source track identifier will be zero and so will the start position.</p>
	 *
	 * @return Source reference to original source.
	 * 
	 * @see tv.amwa.maj.union.SourceReferenceValue#isOriginalSource()
	 * @see tv.amwa.maj.record.PackageID#isZero()
	 * @see #inContextReference(int)
	 * @see #inContextReference(int, Long)
	 */
	public final static SourceReferenceValue originalSource() {
		
		return SourceReferenceValueImpl.originalSource();
	}
	
	/**
	 * <p>Makes a new source reference to static material in a track within the same context
	 * as the package in which the reference is specified. This method version omits the start
	 * position property&nbsp;- use {@link #inContextReference(int, Long)} to set a start
	 * position value for timeline- or event-based material.</p>
	 *
	 * @param sourceTrackID Track to reference within the same package that this reference is defined in.
	 * @return Contextual source reference.
	 * 
	 * @throws IllegalArgumentException The given source track id is a negative value.
	 * 
	 * @see tv.amwa.maj.model.StaticTrack
	 * @see tv.amwa.maj.union.SourceReferenceValue#isContextual()
	 * @see #originalSource()
	 * @see #inContextReference(int, Long)
	 */
	public final static SourceReferenceValue inContextReference(
			@TrackID int sourceTrackID) 
		throws IllegalArgumentException {
		
		return SourceReferenceValueImpl.inContextReference(sourceTrackID);
	}
	
	/**
	 * <p>Makes a new source reference to timeline- or event-based material in 
	 * a track within the same context as the package in which the reference is specified. Use the
	 * {@link #inContextReference(int)} to create a reference to static material.</p>
	 *
	 * @param sourceTrackID Track to reference within the same package that this reference is defined in.
	 * @param startPosition Specifies the offset from the origin of the referenced package's track in edit 
	 * units determined by the associated source clip object's context.
	 * 
	 * @return Contextual source reference.
	 * 
	 * @throws IllegalArgumentException The source track id cannot be set to a negative value.
	 * 
	 * @see tv.amwa.maj.model.TimelineTrack
	 * @see tv.amwa.maj.model.EventTrack
	 * @see tv.amwa.maj.union.SourceReferenceValue#isContextual()
	 * @see #inContextReference(int)
	 * @see #originalSource()
	 */
	public final static SourceReferenceValue inContextReference(
			@TrackID int sourceTrackID,
			@PositionType Long startPosition) 
		throws IllegalArgumentException {
		
		return SourceReferenceValueImpl.inContextReference(sourceTrackID, startPosition);
	}

    /**
     * <p>Make a new source reference value that contains a source package identifier, source track identifier and
     * start position. If the given source package identifier is the {@linkplain #zeroPackageID() zero package id}, 
     * values for source track id and start position are set to zero to comply with source reference constraints.</p>
     * 
	 * @param sourcePackageID Identifies the {@linkplain tv.amwa.maj.model.Package package} that is the target
	 * of the new reference. If the property is set to the {@linkplain #zeroPackageID() zero package id}, 
	 * it means that the package owning the source reference describes the original source. If this optional property 
	 * is omitted by setting its value to <code>null</code>, the given {@linkplain tv.amwa.maj.model.Track track} 
	 * refers to another track within the same package as the reference.
	 * @param sourceTrackID Specifies the track id of a track within the specified 
	 * package. If the source package id is the {@linkplain #zeroPackageID() zero package id}, then the source 
	 * track id shall have a 0&nbsp;value.
	 * @param startPosition Specifies the position offset from the origin of the referenced package's
	 * track in edit units determined by the associated 
	 * {@linkplain tv.amwa.maj.model.SourceClip source clip's} context. If the source package id 
	 * is the {@linkplain #zeroPackageID() zero package id}, then the start position shall have a 0&nbsp;value. 
	 * Set the value to <code>null</code> to omit this optional property in the case that the
	 * new reference is to {@linkplain tv.amwa.maj.model.StaticTrack static material}.
	 * 
	 * @throws IllegalArgumentException The source track id cannot be set to a negative value.
	 * 
	 * @see tv.amwa.maj.record.PackageID#isZero()
	 * @see #zeroPackageID()
	 */
	public final static SourceReferenceValue makeReference(
			@PackageIDType PackageID sourcePackageID,
			@TrackID int sourceTrackID,
			@PositionType Long startPosition) 
		throws IllegalArgumentException {
		
		return new SourceReferenceValueImpl(sourcePackageID, sourceTrackID, startPosition);
	}
	
	// Make model objects
	
	/**
	 * <p>Create new instances of metadata objects using the definition of its primary interface
	 * and its initial property values. Property values must be provided for all required properties 
	 * and may also be provided for optional values. This is with the exception of required
	 * sets and arrays that are initialized to empty and will require at least one element to be
	 * added to become initialized.</p>
	 * 
	 * <p>The type of object to create is provided by its defining Java interface or class. The
	 * property values are provided by a list of property identifier and value pairs, where each identifier
	 * is followed by its value. The property identifier can by a name, {@linkplain AUID} or 
	 * {@linkplain PropertyDefinition property definition}. The values are either instances of the
	 * {@link PropertyValue} interface or Java values compatible with the type definition, as
	 * defined in the <a href="package-summary.html#typeMappingTable">type mapping table</a>.</p>
	 * 
	 * <p>Here is an example of how to use this method:</p>
	 * 
	 * <pre>
	 *     	TimelineTrack videoTrack = MediaEngine.make(
	 *          TimelineTrack.class,
	 *          "TrackID", 1, 
	 *          "EditRate", "25/1", 
	 *          "Origin", 0l,           
	 *          "TrackSegment", videoSequence);
	 * </pre>
	 * 
	 * <p>In the example, an instance of the timeline track type is created. Its track identifier is initialized
	 * to&nbsp;1, edit rate to&nbsp;25/1 using a string representation of the value, origin to&nbsp;0 and track to a variable
	 * called <code>videoSequence</code>.</p>
	 * 
	 * @param <T> Type of value to make and return.
	 * @param type Java class representing the type to return.
	 * @param strict Should the method be strict about required properties or use defaults provided by
	 * the implementation?
	 * @param properties List of pairs of property identifiers and property values to use to initialize the
	 * newly made value.
	 * @return New instance of the specified type initialized with the given property values.
	 * 
	 * @throws NullPointerException Cannot make a new value for a null class specification.
	 * @throws IllegalArgumentException The list of properties must be a list of property identifier
	 * and property value pairs, ie. divisible by&nbsp;2, or a summary of other errors encountered during
	 * object creation.
	 * 
	 * @see #make(Class, Object...)
	 * @see #makeByName(String, String, boolean, Object...)
	 * @see #makeByName(String, String, Object...)
	 * @see #makeAAF(String, Object...)
	 * @see ClassDefinition#createInstance()
	 */
	@SuppressWarnings("unchecked")
	public final static <T extends MetadataObject> T make(
			Class<T> type,
			boolean strict,
			Object... properties) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (type == null)
			throw new NullPointerException("Cannot make a new value for a null class specification.");
		
		ClassDefinition typeClass = Warehouse.lookForClass(type);
		MetadataObject instance = typeClass.createInstance();
		
		if (instance == null)
			throw new IllegalArgumentException("Unable to create an instance of class " + typeClass.getName() + ".");
		
		if ((properties.length % 2) != 0 )
			throw new IllegalArgumentException("The list of properties and their values must be a list of pairs.");
		
		List<Exception> exceptions = new Vector<Exception>();
		Set<PropertyDefinition> valuesSet = new HashSet<PropertyDefinition>();
		
		if (instance instanceof InterchangeObject) {
			try {
				valuesSet.add(typeClass.lookupPropertyDefinition("ObjectClass"));
			}
			catch (Exception e) { }
		}
		
		for ( int u = 0 ; u < properties.length ; u += 2 ) {
			
			Object property = properties[u];
			Object value = properties[u + 1];
			
			if (property == null) {
				exceptions.add(new NullPointerException("Cannot set a property indentified by a null value."));
				continue;
			}
			
			PropertyDefinition propertyDefinition = null;
			
			try {
				if (property instanceof PropertyDefinition) {
					propertyDefinition = (PropertyDefinition) property;
					typeClass.lookupPropertyDefinition(propertyDefinition.getAUID());
				}
				else if (property instanceof String) {
					try {
						propertyDefinition = typeClass.lookupPropertyDefinition(
								tv.amwa.maj.record.impl.AUIDImpl.parseFactory((String) property));
					}
					catch (Exception e) {
						propertyDefinition = typeClass.lookupPropertyDefinition((String) property);
					}
				}
				else if (property instanceof AUID) {
					propertyDefinition = typeClass.lookupPropertyDefinition((AUID) property);
				}
			}
			catch (Exception e) {
				exceptions.add(new Exception(e.getClass().getName() + " thrown when finding property definition " + property.toString() + ".",
						e));
				continue;
			}
			
			if (propertyDefinition == null) {
				exceptions.add(new IllegalPropertyException("Class " + typeClass.getName() + 
						" does not define property " + property.toString() + "."));
				continue;
			}
			
			valuesSet.add(propertyDefinition);
			
			try {
				propertyDefinition.setPropertyValue(
					instance, 
					propertyDefinition.getTypeDefinition().createValue(value));
			}
			catch (Exception e) {
				exceptions.add(new Exception(e.getClass().getName() + " thrown when setting property " + propertyDefinition.getName() + ".",
						e));
			}
		}
		
		for (PropertyDefinition requiredTest : typeClass.getAllPropertyDefinitions()) 
			if (requiredTest.getIsOptional() == false)
				if (!valuesSet.contains(requiredTest)) {
					
					if (!strict) {
						try {
							Method initMethod = 
								requiredTest.getMemberOf().getJavaImplementation().getMethod(
									"initialize" + requiredTest.getName().replace(" ", ""));
							
							Object value = initMethod.invoke(instance);
							requiredTest.setPropertyValue(
									instance, 
									requiredTest.getTypeDefinition().createValue(value));
						}
						catch (Exception e) { 
							// TODO consider removing this output - included for development
							System.err.println("Info: Possible unsafe setting of property " + 
									requiredTest.getMemberOf().getName() + "." + requiredTest.getName() + 
									" due to a " + e.getClass().getName() + ": " + e.getMessage());
							// Not being strict, so allow to continue
						}
						
						continue;
					}
					
					switch (requiredTest.getTypeDefinition().getTypeCategory()) {
					
					case Set: // Expect developer to add in, even if required and non-empty properties
					case FixedArray:
					case VariableArray:
						break;
					default:
						exceptions.add(new Exception("Required property " + requiredTest.getName() + " has not been provided with a value."));
					}
				}
					
		
		if (exceptions.size() > 0) {
			StringBuffer exceptionDescription = new StringBuffer();
			if (exceptions.size() == 1)
				exceptionDescription.append("Error setting property values for class ");
			else
				exceptionDescription.append("Errors setting property values for class ");
			exceptionDescription.append(typeClass.getName());
			exceptionDescription.append(':');
			
			for (Exception e : exceptions ) {
				exceptionDescription.append('\n');
				exceptionDescription.append(" - ");
				exceptionDescription.append(e.getMessage());
				
				if (e.getCause() == null) continue;
				
				exceptionDescription.append(' ');
				exceptionDescription.append(e.getCause().getMessage());
			}
			
			throw new IllegalArgumentException(exceptionDescription.toString());
		}
		
		if (instance instanceof WeakReferenceTarget)
			WeakReference.registerTarget((WeakReferenceTarget) instance);
		
		return (T) instance;
	}

	/**
	 * <p>Create new instances of metadata objects using the definition of its primary interface
	 * and its initial property values without being strict about required properties. See the
	 * description of {@link #make(Class, boolean, Object...)} for more details.</p>
	 * 
	 * @param <T> Type of value to make and return.
	 * @param type Java class representing the type to return.
	 * @param properties List of pairs of property identifiers and property values to use to initialize the
	 * newly made value.
	 * @return New instance of the specified type initialized with the given property values.
	 * 
	 * @throws NullPointerException Cannot make a new value for a null class specification.
	 * @throws IllegalArgumentException The list of properties must be a list of property identifier
	 * and property value pairs, ie. divisible by&nbsp;2, or a summary of other errors encountered during
	 * object creation.
	 * 
	 * @see #make(Class, boolean, Object...)
	 * @see #makeByName(String, String, Object...)
	 * @see ClassDefinition#createInstance()
	 */
	public final static <T extends MetadataObject> T make(
			Class<T> type,
			Object... properties) 
		throws NullPointerException,
			IllegalArgumentException {
		
		return make(type, false, properties);
	}

	/**
	 * <p>Create new instances of metadata objects using a fully qualified XML namespace name for
	 * the type and its initial property values. Property values must be provided for all required properties 
	 * and may also be provided for optional values. This is with the exception of required
	 * sets and arrays that are initialized to empty and will require at least one element to be
	 * added to become initialized.</p>
	 * 
	 * <p>The type of object to create is provided by its defining Java interface or class. The
	 * property values are provided by a list of property identifier and value pairs, where each identifier
	 * is followed by its value. The property identifier can by a name, {@linkplain AUID} or 
	 * {@linkplain PropertyDefinition property definition}. The values are either instances of the
	 * {@link PropertyValue} interface or Java values compatible with the type definition, as
	 * defined in the <a href="package-summary.html#typeMappingTable">type mapping table</a>.</p>
	 * 
	 * <p>Here is an example of how to use this method:</p>
	 * 
	 * <pre>
	 *     	TimelineTrack videoTrack = MediaEngine.make(
	 *          "http://www.smpte-ra.org/schemas/2001-2/2007/aaf", "TimelineTrack",
	 *          "TrackID", 1, 
	 *          "EditRate", "25/1", 
	 *          "Origin", 0l,           
	 *          "TrackSegment", videoSequence);
	 * </pre>
	 * 
	 * <p>In the example, an instance of the timeline track type is created. Its track identifier is initialized
	 * to&nbsp;1, edit rate to&nbsp;25/1 using a string representation of the value, origin to&nbsp;0 and track to a variable
	 * called <code>videoSequence</code>. Note that the default AAF XML namespace is set as static string
	 * {@link CommonConstants#AAF_XML_NAMESPACE}.</p>
	 * 
	 * @param <T> Type of value to make and return.
	 * @param namespace Namespace in which the type for the new instance is defined.
	 * @param typeName Name of the type of instance to create within the given namespace.
	 * @param strict Should the method be strict about required properties or use defaults provided by
	 * the implementation?
	 * @param properties List of pairs of property identifiers and property values to use to initialize the
	 * newly made value.
	 * @return New instance of the specified type initialized with the given property values.
	 * 
	 * @throws NullPointerException Cannot make a new value for a null class specification.
	 * @throws IllegalArgumentException The list of properties must be a list of property identifier
	 * and property value pairs, ie. divisible by&nbsp;2, or a summary of other errors encountered during
	 * object creation.
	 * 
	 * @see #make(Class, Object...)
	 * @see ClassDefinition#createInstance()
	 * @see MediaClass#namespace()
	 * @see CommonConstants#AAF_XML_NAMESPACE
	 */
	@SuppressWarnings("unchecked")
	public final static <T extends MetadataObject> T makeByName(
			String namespace,
			String typeName,
			boolean strict,
			Object... properties)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (namespace == null)
			throw new NullPointerException("Cannot make a new instance with a null namespace.");
		
		if (typeName == null)
			throw new NullPointerException("Cannot make a new instance from a null name.");		
		
		String fullName = "{" + namespace + "}" + typeName;
		
		return make(
				(Class<T>) Warehouse.lookForClass(fullName).getJavaImplementation(),
				strict,
				properties);
	}

	/**
	 * <p>Create new instances of metadata objects using a fully qualified XML namespace name for
	 * the type and its initial property values, and also without being strict about required properties.
	 * For more details about this method, see the documentation for the {@link #makeByName(String, String, boolean, Object...)}
	 * method.</p>
	 * 
	 * @param <T> Type of value to make and return.
	 * @param namespace Namespace in which the type for the new instance is defined.
	 * @param typeName Name of the type of instance to create within the given namespace.
	 * @param properties List of pairs of property identifiers and property values to use to initialize the
	 * newly made value.
	 * @return New instance of the specified type initialized with the given property values.
	 * 
	 * @throws NullPointerException Cannot make a new value for a null class specification.
	 * @throws IllegalArgumentException The list of properties must be a list of property identifier
	 * and property value pairs, ie. divisible by&nbsp;2, or a summary of other errors encountered during
	 * object creation.
	 * 
	 * @see #make(Class, Object...)
	 * @see #make(Class, boolean, Object...)
	 * @see #makeByName(String, String, boolean, Object...)
	 * @see #makeAAF(String, Object...)
	 * @see ClassDefinition#createInstance()
	 * @see MediaClass#namespace()
	 * @see CommonConstants#AAF_XML_NAMESPACE
	 */
	public final static <T extends MetadataObject> T makeByName(
			String namespace,
			String typeName,
			Object... properties)
		throws NullPointerException,
			IllegalArgumentException {
		
		return Forge.<T>makeByName(namespace, typeName, false, properties);
	}

	/**
	 * <p>Create new instances of AAF baseline metadata objects using the name of
	 * the type and its initial property values, and also without being strict about required properties.</p>
	 * 
	 * <p>For more details about this method, see the documentation for the {@link #makeByName(String, String, boolean, Object...)}
	 * method. This method is the same as calling that method with the namespace set to the 
	 * {@linkplain CommonConstants#AAF_XML_NAMESPACE AAF namespace}.</p>
	 * 
	 * @param <T> Type of value to make and return.
	 * @param typeName Name of the AAF baseline type of instance to create.
	 * @param properties List of pairs of property identifiers and property values to use to initialize the
	 * newly made value.
	 * @return New instance of the specified type initialized with the given property values.
	 * 
	 * @throws NullPointerException Cannot make a new value for a null class specification.
	 * @throws IllegalArgumentException The list of properties must be a list of property identifier
	 * and property value pairs, ie. divisible by&nbsp;2, or a summary of other errors encountered during
	 * object creation.
	 * 
	 * @see #make(Class, Object...)
	 * @see #make(Class, boolean, Object...)
	 * @see #makeByName(String, String, boolean, Object...)
	 * @see #makeByName(String, String, Object...)
	 * @see ClassDefinition#createInstance()
	 * @see MediaClass#namespace()
	 * @see CommonConstants#AAF_XML_NAMESPACE
	 */
	public final static <T extends MetadataObject> T makeAAF(
			String typeName,
			Object... properties)
		throws NullPointerException,
			IllegalArgumentException {
		
		MediaEngine.initializeAAF();
		return Forge.<T>makeByName(CommonConstants.AAF_XML_NAMESPACE, typeName, false, properties);
	}
	
}
