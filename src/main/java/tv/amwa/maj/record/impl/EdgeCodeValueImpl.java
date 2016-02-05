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
 * $Log: EdgeCodeValueImpl.java,v $
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/12/15 18:56:23  vizigoth
 * Moved character to byte parsing methods into common utility functions.
 *
 * Revision 1.1  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:05:03  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/03/07 08:08:10  vizigoth
 * Edited comments to release standard.
 *
 * Revision 1.2  2008/01/18 16:30:57  vizigoth
 * Changed header property of an edgecode value to optional.
 *
 * Revision 1.1  2007/11/13 22:14:41  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.enumeration.EdgeType;
import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.misctype.EdgecodeHeader;
import tv.amwa.maj.misctype.FrameOffset;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.EdgeCodeValue;
import tv.amwa.maj.util.Utilities;

/** 
 * <p>Implementation of a value that represents film edge code information. The properties of an 
 * edgecode are specified by the AAF {@linkplain tv.amwa.maj.model.impl.EdgeCodeSegmentImpl edgecode class}.</p>
 * 
 * <p>The internal representation of the header is as a byte array. Conversion to and from Java String values
 * can take place using {@link #setHeaderFromString(String)} and {@link #getHeaderAsString()}.</p>
 * 
 * <p>An edgecode value can be represented in a database by the following columns:</p>
 * 
 * <pre>
 *     `EdgeCodeFormat` int(11) NOT NULL,
 *     `EdgeCodeFileFormat` int(11) NOT NULL,
 *     `EdgeCodeStart` bigint(20) NOT NULL,
 *     `EdgeCodeHeader` tinyblob
 * </pre>
 * 
 * @see tv.amwa.maj.model.impl.EdgeCodeSegmentImpl
 * @see tv.amwa.maj.misctype.EdgecodeHeader
 * 
 *
 *
 */
public final class EdgeCodeValueImpl 
	implements EdgeCodeValue,
		Serializable,
		XMLSerializable,
		Cloneable,
		CommonConstants {

    /**  */
	private static final long serialVersionUID = 4175102659219151076L;
	/** Specifies the edge code at the beginning of the corresponding segment. */
	@FrameOffset private long edgeCodeStart;
	/** Specifies the type of film. */
	private FilmType edgeCodeFilmFormat;
	/** Specifies the edge code format. */
    private EdgeType edgeCodeFormat;
    /** Specifies the text prefix that identifies the film. Typically, this is a text 
     *  string of no more than 8 7-bit ISO characters. */
    @EdgecodeHeader private byte[] edgeCodeHeader = null;
    
    /** <p>Template used when making XML versions of header values.</p> */
    private static final char[] headerTemplate = new char[] {
    	'0', '0', '0', '0', '0', '0', '0', '0',
    	'0', '0', '0', '0', '0', '0', '0', '0'
    };
    
	/** <p>Byte to character value map used for efficient generation of XML versions of headers.</p> */
	private static final char[] hexCharMap = new char[] {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};

    
    /**
     * <p>Creates and initializes a new edgecode value. </p> 
     * 
	 * @param startFrame Specifies the edge code at the beginning of the corresponding segment.
	 * @param filmKind Specifies the type of film.
	 * @param codeFormat Specifies the edge code format.
	 * 
	 * @throws NullPointerException One or more of the arguments is/are <code>null</code>.
	 */
	public EdgeCodeValueImpl(
			@PositionType long startFrame, 
			FilmType filmKind, 
			EdgeType codeFormat) 
		throws NullPointerException {

		if (filmKind == null)
			throw new NullPointerException("Cannot create a new edgecode value with a null film kind.");
		if (codeFormat == null)
			throw new NullPointerException("Cannot create a new edgecode value with a null code format.");
		
		this.edgeCodeStart = startFrame;
		this.edgeCodeFilmFormat = filmKind;
		this.edgeCodeFormat = codeFormat;
	}

	/**
	 * <p>Create a default edgecode value. The newly created edgecode value has the following 
	 * initial value:</p>
	 * 
	 * <pre>
	 *     &lt;EdgecodeValue&gt;
	 *       &lt;EdgeCodeStart&gt;0&lt;/EdgeCodeStart&gt;
	 *       &lt;FilmKind&gt;FtNull&lt;/FilmKind&gt;
	 *       &lt;CodeFormat&gt;EtNull&lt;/CodeFormat&gt;
	 *       &lt;!-- Header property is omitted --&gt;
	 *     &lt;/EdgecodeValue&gt;
	 * </pre>
	 *
	 */
	public EdgeCodeValueImpl() {
		edgeCodeStart = 0l;
		edgeCodeFilmFormat = FilmType.FtNull;
		edgeCodeFormat = EdgeType.Null;
		edgeCodeHeader = null;
	}

	public EdgeType getEdgeCodeFormat() {
		return edgeCodeFormat;
	}

	public void setEdgeCodeFormat(
			EdgeType edgeCodeFormat) 
		throws NullPointerException {
		
		if (edgeCodeFormat == null)
			throw new NullPointerException("Cannot set the code format of an edgecode value with a null value.");
		
		this.edgeCodeFormat = edgeCodeFormat;
	}

	public FilmType getEdgeCodeFilmFormat() {
		return edgeCodeFilmFormat;
	}

	public void setEdgeCodeFilmFormat(
			FilmType edgeCodeFilmFormat) 
		throws NullPointerException {
		
		if (edgeCodeFilmFormat == null)
			throw new NullPointerException("Cannot set the film kind of an edgecode value with a null value.");
		
		this.edgeCodeFilmFormat = edgeCodeFilmFormat;
	}

	/** 
	 * @see #getHeaderAsString()
	 */
	public @EdgecodeHeader byte[] getEdgeCodeHeader() 
		throws PropertyNotPresentException {
		
		if (edgeCodeHeader == null)
			throw new PropertyNotPresentException("The optional header property is not present in this edgecode value.");
		
		return edgeCodeHeader.clone();
	}

	/**
	 * @see #setHeaderFromString(String)
	 */
	public void setEdgeCodeHeader(
			@EdgecodeHeader byte[] edgeCodeHeader) {
		
		if (edgeCodeHeader == null) {
			this.edgeCodeHeader = null;
			return;
		}
			
		this.edgeCodeHeader = edgeCodeHeader.clone();
	}
	
	public String getEdgeCodeHeaderAsString() 
		throws PropertyNotPresentException {

		if (edgeCodeHeader == null)
			throw new PropertyNotPresentException("The optional header property is not present for this edge code value.");
		
		String headerAsString = new String(edgeCodeHeader);
		if (headerAsString.endsWith("\u0000"))
			return headerAsString.substring(0, headerAsString.length() - 1);
		else
			return headerAsString;
	}	

	public void setEdgeCodeHeader(
			String header) {
		
		if (header == null) {
			this.edgeCodeHeader = null;
			return;
		}
		
		this.edgeCodeHeader = header.getBytes();
	}

	public long getEdgeCodeStart() {
		
		return edgeCodeStart;
	}

	public void setEdgeCodeStart(
			long edgeCodeStart) {
	
		this.edgeCodeStart = edgeCodeStart;
	}	

	/** 
	 * <p>Two edgecode values are equals if and only if each of their properties are equals.</p>
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		
		if (o == null) return false;
		if (!(o instanceof EdgeCodeValue)) return false;
		
		EdgeCodeValue testEdgecode = 
			(EdgeCodeValue) o;
		
		if (testEdgecode.getEdgeCodeFormat() != edgeCodeFormat) return false;
		if (testEdgecode.getEdgeCodeFilmFormat() != edgeCodeFilmFormat) return false;
		if (testEdgecode.getEdgeCodeStart() != edgeCodeStart) return false;
		
		return Arrays.equals(testEdgecode.getEdgeCodeHeader(), edgeCodeHeader);
	}

	/** 
	 * <p>Formats this edgecode value in a pseudo-XML representation that is compatible with 
	 * the AAF XML schema. For example:</p>
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
	 * @return Pseudo-XML representation of this edgecode value.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		return XMLBuilder.toXMLNonMetadata(this);
	}

	private static final Pattern edgeCodeStartPattern = 
		Pattern.compile("<\\w*\\:?EdgeCodeStart\\>\\-?([\\d]{1,20})\\<\\/\\w*\\:?EdgeCodeStart\\>");
	private static final Pattern filmKindPattern =
		Pattern.compile("<\\w*\\:?FilmKind\\>(\\w*)\\<\\/\\w*\\:?FilmKind\\>");
	private static final Pattern codeFormatPattern =
		Pattern.compile("<\\w*\\:?CodeFormat\\>(\\w*)\\<\\/\\w*\\:?CodeFormat\\>");
	private static final Pattern headerPattern =
		Pattern.compile("<\\w*\\:?EdgeCodeHeader\\>([0-9a-fA-F]*)\\<\\/\\w*\\:?EdgeCodeHeader\\>");
	
	/**
	 * <p>Creates an edge code value from a pseudo-XML representation, including all those
	 * generated by {@link #toString()}. Edge code values are intended to match those generated
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
	 * @see #toString()
	 */
	public final static EdgeCodeValue parseFactory(
			String edgeCodeAsString)
		throws NullPointerException,
			ParseException {
		
		if (edgeCodeAsString == null)
			throw new NullPointerException("Cannot create a new edge code value from a null string.");
		
		Matcher matcher = edgeCodeStartPattern.matcher(edgeCodeAsString);
		
		long start = 0l;
		if (matcher.find())
			start = Long.parseLong(matcher.group(1));
		
		matcher = filmKindPattern.matcher(edgeCodeAsString);
		
		FilmType kind = FilmType.FtNull;
		try {
			if (matcher.find()) {
				String enumValue = matcher.group(1);
				kind = FilmType.valueOf(enumValue);
			}
		}
		catch (IllegalArgumentException iae) { 
			throw new ParseException("Unrecognised film kind when parsing an edge code value.", 0);
		}
		
		matcher = codeFormatPattern.matcher(edgeCodeAsString);
		
		EdgeType format = EdgeType.Null;
		try {
			if (matcher.find()) {
				String enumValue = matcher.group(1);
				if (enumValue.startsWith("Et"))
					enumValue = enumValue.substring(2);
				format = EdgeType.valueOf(enumValue);	
			}
		}
		catch (IllegalArgumentException iae) { 
			throw new ParseException("Unrecognised code format value when parsing an edge code value.", 0);
		}
		
		EdgeCodeValue value = new EdgeCodeValueImpl(start, kind, format);
		
		matcher = headerPattern.matcher(edgeCodeAsString);
		
		try {
			if (matcher.find())
				value.setEdgeCodeHeader(
						EdgeCodeValueImpl.convertHexCodingToHeader(matcher.group(1)));
		}
		catch (NumberFormatException nfe) {
			throw new ParseException("Unable to convert edge code header to a valid value.", 0);
		}
		
		return value;
	}
	
	public EdgeCodeValue clone() {
		
		try {
			EdgeCodeValueImpl cloned = (EdgeCodeValueImpl) super.clone();
			cloned.setEdgeCodeHeader(edgeCodeHeader);
			return cloned;
		}
		catch (CloneNotSupportedException cnse) {
			cnse.printStackTrace();
			return null;
		}
	}
	
	public int hashCode() {
		
		return (new Long(edgeCodeStart).hashCode()) ^
			(edgeCodeFilmFormat.ordinal() << 16 + edgeCodeFormat.ordinal()) ^
			edgeCodeHeader.hashCode();
	}

	public void appendXMLChildren(
			Node parent) {

		XMLBuilder.appendComment(parent, "EdgecodeValue represents a structure and is not a defined AAF XML element.");
		
		Element edgecodeElement = XMLBuilder.createChild(parent, AAF_XML_NAMESPACE, AAF_XML_PREFIX, "EdgecodeValue");
		appendXMLGrandchildren(edgecodeElement);
	}
	
	public final static String START_TAG = "EdgeCodeStart";
	public final static String FILMKIND_TAG = "FilmKind";
	public final static String CODEFORMAT_TAG = "CodeFormat";
	public final static String HEADER_TAG = "EdgeCodeHeader";
	
    /**
     * <p>Appends the sub-elements of an XML representation of this value to the given XML node, without
     * the enclosing <code>EdgecodeValue</code> tag. This allows the elements of an edgecode to be 
     * appended to the XML representation of an {@linkplain tv.amwa.maj.model.EdgeCodeSegment edgecode 
     * segment}.</p>
     * 
     * @param edgecodeElement XML node to append start time, film kind, code format and header elements
     * to, which represent this value.
     */
    public void appendXMLGrandchildren(
    		Node edgecodeElement) {
    	
		XMLBuilder.appendElement(edgecodeElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, START_TAG, edgeCodeStart);
		XMLBuilder.appendElement(edgecodeElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, FILMKIND_TAG, edgeCodeFilmFormat.name());
		XMLBuilder.appendElement(edgecodeElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, CODEFORMAT_TAG, "Et" + edgeCodeFormat.name());
		
		if (edgeCodeHeader == null) return;
		
		XMLBuilder.appendComment(edgecodeElement, "Header as text: '" + getEdgeCodeHeaderAsString() + "'");
		XMLBuilder.appendElement(edgecodeElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, HEADER_TAG, new String(convertHeaderToHexCoding(edgeCodeHeader)));
    }

    public final static String convertHeaderToHexCoding(
    		byte[] edgeCodeHeader) {
    	
    	if (edgeCodeHeader == null) return new String();
    	
		char[] headerChars = headerTemplate.clone();
		int headerIndex = 0;
		for ( int x = 0 ; x < edgeCodeHeader.length ; x++ ) {
			headerChars[headerIndex++] = hexCharMap[(edgeCodeHeader[x] >>> 4) & 15];
			headerChars[headerIndex++] = hexCharMap[edgeCodeHeader[x] & 15];
			if (headerIndex > headerChars.length) break;
		}
		
		return new String(headerChars);
    }

    public final static byte[] convertHexCodingToHeader(
    		String hexCode) 
    	throws NumberFormatException {
    	
    	if (hexCode == null) return null;
    	if (hexCode.length() != 16) return null;
    	char[] hexCodeChars = hexCode.toCharArray();
    	
    	byte[] header = new byte[8];
    	header[0] = Utilities.twoCharsToByte(hexCodeChars[0], hexCodeChars[1]);
    	header[1] = Utilities.twoCharsToByte(hexCodeChars[2], hexCodeChars[3]);
    	header[2] = Utilities.twoCharsToByte(hexCodeChars[4], hexCodeChars[5]);
    	header[3] = Utilities.twoCharsToByte(hexCodeChars[6], hexCodeChars[7]);
    	header[4] = Utilities.twoCharsToByte(hexCodeChars[8], hexCodeChars[9]);
    	header[5] = Utilities.twoCharsToByte(hexCodeChars[10], hexCodeChars[11]);
    	header[6] = Utilities.twoCharsToByte(hexCodeChars[12], hexCodeChars[13]);
    	header[7] = Utilities.twoCharsToByte(hexCodeChars[14], hexCodeChars[15]);
    	
    	return header;
   }
    
	public String getComment() {

		return null;
	}

}
