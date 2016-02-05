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
 * $Log: VersionTypeImpl.java,v $
 * Revision 1.3  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/06/18 09:35:19  vizigoth
 * Version was wrongly based on UInt8 instead of Int8. Fixed to match metadictionary.
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
 * Revision 1.1  2007/11/13 22:14:39  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:26  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import java.io.Serializable;
import java.text.ParseException;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.integer.Int8;
import tv.amwa.maj.record.VersionType;

/** 
 * <p>Implements a 2-byte version number, consisting of a major and minor component.</p>
 * 
 * <p>A persistent representation of a product version may be represented by the following columns in a database:</p>
 * 
 * <pre>
 *     'VersionType_major' tinyint(4) NOT NULL, 
 *     'VersionType_minor' tinyint(4) NOT NULL
 * </pre>
 *
 * @see ProductVersionImpl
 * @see tv.amwa.maj.model.Preface#getFormatVersion()
 * @see tv.amwa.maj.model.PluginDefinition
 *
 *
 *
 */

public final class VersionTypeImpl 
	implements VersionType, 
		Serializable,
		Comparable<VersionType>,
		Cloneable,
		CommonConstants {

	/**  */
	private static final long serialVersionUID = 1648329801097298111L;

	/** Major version number. */
	@Int8 private byte major;

	/** Minor version number. */
	@Int8 private byte minor;

	/**
	 * <p>Create a version number from its major and minor component.</p>
	 * 
	 * @param major Major component of version number.
	 * @param minor Minor component of version number.
	 */
	public VersionTypeImpl(@Int8 byte major, @Int8 byte minor) {
		this.major = major;
		this.minor = minor;
	}

	/**
	 * <p>Create a new version number, set to "<code>0.0</code>".</p>
	 *
	 */
	public VersionTypeImpl() {
		major = (byte) 0;
		minor = (byte) 0;
	}

	public final byte getMajor() {
		return major;
	}

	public final void setMajor(byte major) {
		this.major = major;
	}

	public final byte getMinor() {
		return minor;
	}

	public void setMinor(byte minor) {
		this.minor = minor;
	}	

	/** 
	 * <p>Formats the version number as a string representation. This format is:</p>
	 * 
	 * <p><center>&lt;<em>major</em>&gt;<code>.</code>&lt;<em>minor</em>&gt;</center></p>
	 * 
	 * <p>Note that "<code>2.10</code>" is a later version than "<code>2.9</code>".</p>
	 * 
	 * @see java.lang.Object#toString()
	 */
	public final String toString() {
		
		return format(this);
	}

	/**
	 * <p>Parse a string representation of a version number and create a value of this
	 * class. The value must contain a&nbsp;'<code>.</code>' to be valid. The major and minor 
	 * parts of the version numbers, before and after the dot respectively, must range between
	 * 0&nbsp;and&nbsp;255.</p>
	 * 
	 * @param versionAsString String representation of a version number.
	 * @return Version number value.
	 * 
	 * @throws NullPointerException The given version number string is <code>null</code>.
	 * @throws ParseException The given version number string causes parse or number range errors
	 * that prevent it from being converted into a version number value.
	 */
	public final static VersionTypeImpl parseFactory(
			String versionAsString) 
		throws NullPointerException,
			ParseException {
		
		if (versionAsString == null)
			throw new NullPointerException("Cannot convert a null string into a value of the version type.");
		
		int dotIndex = versionAsString.indexOf('.');
		if (dotIndex == -1)
			throw new ParseException("The given version value does not contain a major/minor separator dot.", 0);
		
		String majorString = versionAsString.substring(0, dotIndex);
		String minorString = versionAsString.substring(dotIndex + 1);
		
		try {
			int major = Integer.parseInt(majorString);
			if ((major < Byte.MIN_VALUE) || (major > Byte.MAX_VALUE))
				throw new NumberFormatException("The major part of the version number must be between " + Byte.MIN_VALUE + 
						" and " + Byte.MAX_VALUE + ".");
			
			int minor = Integer.parseInt(minorString);
			if ((minor < Byte.MIN_VALUE) || (minor > Byte.MAX_VALUE))
				throw new NumberFormatException("The minor part of the version number must be between " + Byte.MIN_VALUE + 
						" and " + Byte.MAX_VALUE + ".");
			
			return new VersionTypeImpl((byte) major, (byte) minor);
		}
		catch (NumberFormatException nfe) {
			throw new ParseException("Unable to parse the major or minor parts of a version type value: " + 
					nfe.getMessage(), 0);
		}
	}
	
	/*
	 * <p>Convert a Java byte value to a string representation of an {@link tv.amwa.maj.integer.UInt8} value.</p>
	 *
	 * @param number Number to convert into a string.
	 * @return String representation of the given number.
	 */
//	private final static String uInt8ToString(@UInt8 byte number) {
//
//		return Integer.toString((number >= 0) ? number : 256 + number);
//	}

	public final int hashCode() {
		
		return major * 1024 + minor;
	}

	/**
	 * <p>Compare this version number to the given version number.</p>
	 *
	 * @param o Version number to compare with this one.
	 * @return A value of <code>-1<code> if this version number is less than the given one, 
	 * <code>1</code> if this version is greater and <code>0</code> if the two version
	 * numbers and equal.
	 * 
	 * @throws NullPointerException The given value is <code>null</code>.
	 * 
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public final int compareTo(
			VersionType o) 
		throws NullPointerException {

		if (o == null)
			throw new NullPointerException("Cannot compare this version number to a null value.");
		
//		int majorThis = (major >= 0) ? (int) major : 256 + major;
//		int minorThis = (minor >= 0) ? (int) minor : 256 + minor;
		int majorThis = (int) major;
		int minorThis = (int) minor;

		byte majorToTest = o.getMajor();
		byte minorToTest = o.getMinor();

//		int majorTest = (majorToTest >= 0) ? (int) majorToTest : 256 + majorToTest;
//		int minorTest = (minorToTest >= 0) ? (int) minorToTest : 256 + minorToTest;
		int majorTest = (int) majorToTest;
		int minorTest = (int) minorToTest;
		
		if (majorThis < majorTest) return -1;
		if (majorThis > majorTest) return 1;
		
		if (minorThis < minorTest) return -1;
		if (minorThis > minorTest) return 1;
		
		return 0;
	}

	public final boolean equals(Object o) {
		
		if (o == null) return false;
		if (!(o instanceof VersionType)) return false;
		
		VersionType testVersion = 
			(VersionType) o;

		if (major != testVersion.getMajor()) return false;
		if (minor != testVersion.getMinor()) return false;
		
		return true;
	}

	public final VersionType clone() {
		
		try {
			return (VersionType) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// This class supports cloning
			return null;
		}
	}

	// Serialization to XML changed ... now just a string "\-?\d{1,3}\.\-?\d{1,3}"
	/* public final void appendXMLChildren(
			Node parent) {

		XMLBuilder.appendElement(
				parent, 
				AAF_XML_NAMESPACE,
				AAF_XML_PREFIX,
				"Version", 
				format(this));
	} */

	/**
	 * <p>Format a version number as a string. </p>
	 *
	 * @param version Version number value to format as a string.
	 * @return Version number formatted as a string.
	 * 
	 * @throws NullPointerException Version value is <code>null</code>.
	 */
	public final static String format(
			VersionTypeImpl version) 
		throws NullPointerException {
		
		if (version == null)
			throw new NullPointerException("Cannot format a null version number value.");
		
		StringBuffer formatted = new StringBuffer(10);
//		formatted.append(uInt8ToString(version.getMajor()));
		formatted.append(version.getMajor());
		formatted.append('.');
//		formatted.append(uInt8ToString(version.getMinor()));
		formatted.append(version.getMinor());
		
		return formatted.toString();
	}

	public String getEventComment() {

		return null;
	}
	
	public final static String MYSQL_COLUMN_DEFINITION = 
		"VARCHAR(14) CHARACTER SET ascii COLLATE ascii_general_ci";
	
	public final static String toPersistentForm(
			VersionType version) {

		if (version == null) return null;
		return version.toString();
	}
	
	public final static VersionType fromPersistentForm(
			String version) {
		
		if (version == null) return null;
		try {
			return parseFactory(version);
		}
		catch (ParseException pe) {
			// TODO add log messages when this go wrong
			return null;
		}
	}
}
	
