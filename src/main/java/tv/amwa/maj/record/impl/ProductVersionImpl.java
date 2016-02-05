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
 * $Log: ProductVersionImpl.java,v $
 * Revision 1.3  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/04/13 07:09:16  vizigoth
 * Found product versions with patch levels greater than 32767. Removed negative value check.
 *
 * Revision 1.5  2010/03/19 09:36:53  vizigoth
 * Minor fix to build type pattern.
 *
 * Revision 1.4  2010/02/10 23:58:59  vizigoth
 * Added new static methods for serialization of these types to bytes: lengthAsBuffer, writeToBuffer.
 *
 * Revision 1.3  2010/01/19 14:38:35  vizigoth
 * Moved parsing of record type from byte buffers done in a non standard way to creatFromBuffer factory methods in the implementation itself.
 *
 * Revision 1.2  2009/12/18 17:56:03  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.7  2009/03/30 09:05:03  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.6  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.5  2008/10/15 09:45:54  vizigoth
 * Edited documentation to release standard.
 *
 * Revision 1.4  2008/09/01 12:12:57  vizigoth
 * Testing the commit mechanism after a break.
 *
 * Revision 1.3  2008/03/07 08:08:11  vizigoth
 * Edited comments to release standard.
 *
 * Revision 1.2  2008/01/15 12:27:04  vizigoth
 * Updated due to refactoring of element names in the ProductReleaseType enumeration.
 *
 * Revision 1.1  2007/11/13 22:14:38  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.enumeration.ProductReleaseType;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.io.xml.LocalHandler;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.record.ProductVersion;

/** 
 * <p>Implementation of a version number for an application, represented with four release
 * levels and its {@linkplain tv.amwa.maj.enumeration.ProductReleaseType product release type}.
 * The four release levels specify the major, minor, tertiary and patch level of a product.</p>
 * 
 * <p>A persistent representation of a product version may be represented by the following
 * columns in a database:</p>
 * 
 * <pre>
 *     'ProductVersion_BuildType' int(11) DEFAULT NULL,
 *     'ProductVersion_Major' smallint(6) NOT NULL,
 *     'ProductVersion_Minor' smallint(6) NOT NULL,
 *     'ProductVersion_Tertiary' smallint(6) NOT NULL,
 *     'ProductVersion_PatchLevel' smallint(6) NOT NULL
 * </pre>
 *
 *
 * 
 * @see tv.amwa.maj.enumeration.ProductReleaseType
 * @see VersionTypeImpl
 * @see tv.amwa.maj.industry.TypeDefinitions#ProductVersionType
 * @see tv.amwa.maj.model.impl.IdentificationImpl#getApplicationVersion()
 * @see tv.amwa.maj.model.impl.IdentificationImpl#getRefImplVersion()
 */
public final class ProductVersionImpl 
	implements ProductVersion,
		Serializable,
		XMLSerializable,
		Cloneable,
		CommonConstants {

    /**  */
	private static final long serialVersionUID = -7836591873835174007L;
	
	/** Major version number. */
	@UInt16 private short major;
    /** Minor version number. */
	@UInt16 private short minor;
	/** Tertiary version number. */
	@UInt16 private short tertiary;
	/** Application patch level. */
	@UInt16 private short patchLevel;
	/** Application's product release type. */
	private ProductReleaseType buildType;

	ProductVersionImpl() { }
	
    /**
     * <p>Create a product version value that represents the version of an application.</p>
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
	 */
	public ProductVersionImpl(
			@UInt16 short major, 
			@UInt16 short minor, 
			@UInt16 short tertiary, 
			@UInt16 short patchLevel, 
			ProductReleaseType type) 
		throws NullPointerException,
			IllegalArgumentException {
		
		// Database special value to represent null, used in Identification
		if ((major == -1) && (minor == -1) && (tertiary == -1) && (patchLevel == -1)
				&& (type == null)) {
			this.buildType = null;
			return;
		}
		
		setMajor(major);
		setMinor(minor);
		setTertiary(tertiary);
		setPatchLevel(patchLevel);
		setBuildType(type);
	}
	
	public final @UInt16 short getMajor() {
		return major;
	}

	public final void setMajor(
			@UInt16 short major) 
		throws IllegalArgumentException {
		
		if (major < 0)
			throw new IllegalArgumentException("Cannot set the major part for this product version to a negative value.");
		
		this.major = major;
	}

	public final @UInt16 short getMinor() {
		return minor;
	}

	public final void setMinor(
			@UInt16 short minor) 
		throws IllegalArgumentException {
		
		if (minor < 0)
			throw new IllegalArgumentException("Cannot set the minor part for this product version to a negative value.");
		
		this.minor = minor;
	}

	public final @UInt16 short getPatchLevel() {
		return patchLevel;
	}

	public final void setPatchLevel(
			@UInt16 short patchLevel) 
		throws IllegalArgumentException {

		// Don't be too precious. Some patch levels go higher than 32767!
//		if (patchLevel < 0)
//			throw new IllegalArgumentException("Cannot set the patch level for this product version to a negative number.");
		
		this.patchLevel = patchLevel;
	}

	public final @UInt16 short getTertiary() {
		return tertiary;
	}

	public final void setTertiary(
			@UInt16 short tertiary) 
		throws IllegalArgumentException {
		
		if (tertiary < 0)
			throw new IllegalArgumentException("Cannot set the tertiary part of this product version to a negative number.");
		
		this.tertiary = tertiary;
	}

	public final ProductReleaseType getBuildType() {
		return buildType;
	}

	public final void setBuildType(
			ProductReleaseType buildType) 
		throws NullPointerException {

		if (buildType == null)
			throw new NullPointerException("Cannot set the product release type of this product version with a null value.");

		this.buildType = buildType;
	}

	public final boolean equals(Object o) {

		if (this == o) return true;
		if (o == null) return false;
		if (!(o instanceof ProductVersion)) return false;
		
		ProductVersion testVersion = 
			(ProductVersion) o;
		
		if (testVersion.getMajor() != major) return false;
		if (testVersion.getMinor() != minor) return false;
		if (testVersion.getTertiary() != tertiary) return false;
		if (testVersion.getPatchLevel() != patchLevel) return false;
		if (testVersion.getBuildType() != buildType) return false;
		
		return true;
	}
	
	/**
	 * <p>Creates a pseudo-XML representation of the current value of this product version. 
	 * The value will be formatted in a similar way to the following example:</p>
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
	 * @return Pseudo-XML representation of this product version. 
	 */
	public final String toString() {

		return XMLBuilder.toXMLNonMetadata(this);
	}

	private static final Pattern majorPattern = 
		Pattern.compile("<\\w*\\:?Major\\>([\\d]{1,5})\\<\\/\\w*\\:?Major\\>");
	private static final Pattern minorPattern =
		Pattern.compile("<\\w*\\:?Minor\\>([\\d]{1,5})\\<\\/\\w*\\:?Minor\\>");
	private static final Pattern tertiaryPattern =
		Pattern.compile("<\\w*\\:?Tertiary\\>([\\d]{1,5})\\<\\/\\w*\\:?Tertiary\\>");
	private static final Pattern patchLevelPattern =
		Pattern.compile("<\\w*\\:?PatchLevel\\>([\\d]{1,5})\\<\\/\\w*\\:?PatchLevel\\>");
	private static final Pattern buildTypePattern =
		Pattern.compile("<\\w*\\:?BuildType\\>(\\w*)\\<\\/\\w*\\:?BuildType\\>");
	
	/**
	 * <p>Creates an instance of this product version class by parsing the given pseudo-XML version. This method will 
	 * create an instance from the result of calling the {@link #toString()} method. The 
	 * pseudo-XML format is illustrated below:</p>
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
	 * @param versionString Pseudo-XML string to convert into a product version instance.
	 * @return Product version created from the given string.
	 * 
	 * @throws NullPointerException The given version string is <code>null</code>.
	 * @throws NumberFormatException Parse error creating a numerical value (major, minor, tertiary, patch level) 
	 * or the given build type does not match the tokens of the {@linkplain ProductReleaseType product
	 * release type} enumeration.
	 */
	public final static ProductVersionImpl parseFactory(
			String versionString) 
		throws NullPointerException,
			ParseException {
		
		if (versionString == null)
			throw new NullPointerException("Cannot create a product version instance from a null string value.");
		
		Matcher matcher = majorPattern.matcher(versionString);
		
		short major = (short) 0;
		short minor = (short) 0;
		short tertiary = (short) 0;
		short patchLevel = (short) 0;

		try {
			if (matcher.find()) 
				major = Short.parseShort(matcher.group(1));
	
			matcher = minorPattern.matcher(versionString);
			
			if (matcher.find()) 
				minor = Short.parseShort(matcher.group(1));
			
			matcher = tertiaryPattern.matcher(versionString);
			
			if (matcher.find()) 
				tertiary = Short.parseShort(matcher.group(1));
			
			matcher = patchLevelPattern.matcher(versionString);
	
			if (matcher.find()) 
				patchLevel = (short) Integer.parseInt(matcher.group(1));
		}
		catch (NumberFormatException nfe) {
			throw new ParseException("A given numeric value for one of major, minor, tertiary or patch level values could not be parsed.", 0);
		}
		
		matcher = buildTypePattern.matcher(versionString);

		ProductReleaseType releaseType = ProductReleaseType.Unknown;
		
		try {
			if (matcher.find()) {
				String enumToken = matcher.group(1);
				if (enumToken.startsWith("Version"))
					enumToken = enumToken.substring(7);
				releaseType = ProductReleaseType.valueOf(enumToken);
			}
		}
		catch (IllegalArgumentException iae) {
			throw new ParseException("Could not parse the given value patch level to a known enumeration value.", 0);
		}
		
		return new ProductVersionImpl(major, minor, tertiary, patchLevel, releaseType);
	}
	
	public final ProductVersion clone() {

		try {
			return (ProductVersion) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// This class implements cloneable and so this should not happen
			cnse.printStackTrace();		
			return null;		
		}

	}

	public final int hashCode() {

		return major ^ (minor << 16) ^ (tertiary << 8) ^ (patchLevel << 24) ^ buildType.hashCode();
	}

	public static final String PRODUCTVERSION_TAG = "ProductVersion";
	
	public final void appendXMLChildren(
			Node parent) {
		
		appendXMLChildren(parent, PRODUCTVERSION_TAG);
	}


	/**
	 * <p>Additional version of {@link #appendXMLChildren(Node)} that allows the tag name for the 
	 * version number tag to be altered.</p>
	 *
	 * @param parent Parent node to append this product version to as an XML child. 
	 * @param alternativeTag Name of the tag to use to represent the product version.
	 */
	public final void appendXMLChildren(
			Node parent,
			String alternativeTag) {
		
		Node productVersionElement;
		
		if (parent instanceof DocumentFragment) 
			productVersionElement = 
				XMLBuilder.createChild(parent, AAF_XML_NAMESPACE, AAF_XML_PREFIX, alternativeTag);
		else
			productVersionElement = parent;
		
		XMLBuilder.appendElement(productVersionElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, "Major", major);
		XMLBuilder.appendElement(productVersionElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, "Minor", minor);
		XMLBuilder.appendElement(productVersionElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, "Tertiary", tertiary);
		XMLBuilder.appendElement(productVersionElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, "PatchLevel", (patchLevel >= 0) ? patchLevel : 65536 + patchLevel);
		XMLBuilder.appendElement(productVersionElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, "BuildType", "Version" + buildType.name());	
	}
	
	/**
	 * <p>XML parser event handler for converting product version elements into product version
	 * values.</p>
	 *  
	 *
	 *
	 */
	public static class XMLHandler 
		extends LocalHandler
		implements ContentHandler {

		public final static String elementName = "ProductVersion";
		public final static String[] dataElementNames = 
			new String[] { "major", "minor", "tertiary", "patchLevel", "type" };

		public Object createResult() throws Exception {
			
			String releaseTypeValue = getElementData("type");
			if (releaseTypeValue.startsWith("Version"))
				releaseTypeValue = releaseTypeValue.substring(7);
			
			return new ProductVersionImpl(
						Short.parseShort(getElementData("major")),
						Short.parseShort(getElementData("minor")),
						Short.parseShort(getElementData("tertiary")),
						Short.parseShort(getElementData("patchLevel")),
						ProductReleaseType.valueOf(releaseTypeValue));
		}
	}

	public String getComment() {

		return null;
	}

	// TODO documentation
	public final static ProductVersion createFromBuffer(
			ByteBuffer buffer) 
		throws NullPointerException,
			EndOfDataException {
		
		if (buffer == null)
			throw new NullPointerException("Cannot create a product version from a null value rather than a buffer.");
		
		if ((buffer.remaining() < 9) || (buffer.remaining() > 10))
			throw new EndOfDataException("Cannot create a product version from a buffer with less than 9 bytes or more than 10 bytes remaining.");

		ProductVersion createdVersion = new ProductVersionImpl();
		createdVersion.setMajor(buffer.getShort());
		createdVersion.setMinor(buffer.getShort());
		createdVersion.setTertiary(buffer.getShort());
		createdVersion.setPatchLevel(buffer.getShort());
		if (buffer.remaining() == 2) buffer.get();
		try {
			createdVersion.setBuildType((ProductReleaseType) 
				TypeDefinitions.ProductReleaseType.createFromBytes(buffer).getValue());
		}
		catch (EndOfDataException eode) {
			throw new IllegalArgumentException("Unexpectedly short product version value in binary data.");
		}
		
		return createdVersion;
	}
	
	public final static long lengthAsBuffer(
			ProductVersion value) {
		
		return 10;
	}

	public final static void writeToBuffer(
			ProductVersion version,
			ByteBuffer buffer)
		throws NullPointerException,
			InsufficientSpaceException {
		
		if (version == null)
			throw new NullPointerException("Cannot write a null product version to a buffer.");
		if (buffer == null)
			throw new NullPointerException("Cannot write a product version to a null buffer.");
		
		if (buffer.remaining() < 10)
			throw new InsufficientSpaceException("Insufficient space in the given buffer to write a product version value.");
		
		buffer.putShort(version.getMajor());
		buffer.putShort(version.getMinor());
		buffer.putShort(version.getTertiary());
		buffer.putShort(version.getPatchLevel());
		buffer.put((byte) 0);
		buffer.put((byte) version.getBuildType().value());
	}
	
	public final static String toPersistentForm(
			ProductVersion productVersion) {
		
		if (productVersion == null) return null;
		return productVersion.toString();
	}
	
	public final static ProductVersion fromPersistentForm(
			String productVersion) {
		
		if (productVersion == null) return null;
		try {
			return parseFactory(productVersion);
		}
		catch (ParseException pe) {
			return null;
		}
	}
}
