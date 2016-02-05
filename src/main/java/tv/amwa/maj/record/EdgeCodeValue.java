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
 * $Log: EdgeCodeValue.java,v $
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.3  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/01/18 16:11:42  vizigoth
 * Changed header property of an edgecode value to optional.
 *
 * Revision 1.4  2008/01/14 20:52:23  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.3  2008/01/10 17:18:43  vizigoth
 * Minor comment improvement.
 *
 * Revision 1.2  2007/12/14 15:01:49  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:13:01  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record;

import tv.amwa.maj.enumeration.EdgeType;
import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.EdgecodeHeader;
import tv.amwa.maj.misctype.FrameOffset;

// Renamed to EdgecodeValue so that it does not get confused with EdgecodeSegment.

/** 
 * <p>Specifies a value that represents film edge code information. A film edge code
 * is described by a start frame, its edge code format, the film format and an optional header.</p>
 * 
 * @see tv.amwa.maj.model.EdgeCodeSegment
 * 
 *
 */

public interface EdgeCodeValue { 
	
	/**
	 * <p>Returns the format of the edge code.</p>
	 *
	 * @return Format of the edge code.
	 * 
	 * @see EdgeType
	 */
	public EdgeType getEdgeCodeFormat();

	/**
	 * <p>Sets the format of the edge code.</p>
	 *
	 * @param codeFormat Format of the edge code.
	 * 
	 * @throws NullPointerException The given edge code format is <code>null</code>.
	 */
	public void setEdgeCodeFormat(
			EdgeType codeFormat)
		throws NullPointerException;

	/**
	 * <p>Returns the film format of the edge code.</p>
	 *
	 * @return Film format for this edge code.
	 * 
	 * @see FilmType
	 */
	public FilmType getEdgeCodeFilmFormat();

	/**
	 * <p>Sets the film format for this edge code.</p>
	 *
	 * @param filmKind Film format for this edge code.
	 * 
	 * @throws NullPointerException The given film type for the edge code is <code>null</code>.
	 */
	public void setEdgeCodeFilmFormat(
			FilmType filmKind)
		throws NullPointerException;

	/**
	 * <p>Returns the text prefix that identifies the film. Typically, this is a text string of no 
	 * more than 8&nbsp;7-bit ISO characters. This is an optional property.</p>
	 *
	 * @return Text prefix that identifies the film.
	 * 
	 * @throws PropertyNotPresentException The optional header property is not present for this edgecode
	 * value.
	 * 
	 * @see #getEdgeCodeHeaderAsString()
	 */
	public @EdgecodeHeader byte[] getEdgeCodeHeader()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the text prefix the identifies the film as a string value. Typically, this is a 
	 * text string of no more than 8&nbsp;7-bit ISO characters. This is an optional property.</p>
	 * 
	 * @return Text prefix that identifies the film as a string.
	 * @throws PropertyNotPresentException The optional header property is not present for this edgecode
	 * value.
	 * 
	 * @see #getEdgeCodeHeader()
	 * @see #setEdgeCodeHeader(String)
	 */
	public String getEdgeCodeHeaderAsString()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the text prefix that identifies the film. Typically, this is a text string of no 
	 * more than 8 7-bit ISO characters. Set the value to <code>null</code> to omit this
	 * optional property.</p>
	 * 
	 * <p>To convert a {@link java.lang.String} to an array of bytes, use 
	 * {@link java.lang.String#getBytes(String)}.</p>
	 *
	 * @param header Text prefix that identifies the film.
	 * 
	 * @see #setEdgeCodeHeader(String)
	 */
	public void setEdgeCodeHeader(
			@EdgecodeHeader byte[] header);

	/**
	 * <p>Set the value of the edge code header from the given string. Conversion to bytes takes
	 * place using the platforms default character set, according to 
	 * {@link java.lang.String#String(byte[])}.</p>
	 *
	 * @param header String representation of the edge code header.
	 * 
	 * @see #setEdgeCodeHeader(byte[])
	 */
	public void setEdgeCodeHeader(
			String header) 
		throws NullPointerException;
	
	/**
	 * <p>Returns the edge code at the beginning of the corresponding segment.</p>
	 *
	 * @return Edge code the the beginning of the corresponding segment.
	 */
	public @FrameOffset long getEdgeCodeStart();

	/**
	 * <p>Sets the edge code at the beginning of the corresponding segment.</p>
	 *
	 * @param startFrame Edge code the the beginning of the corresponding segment.
	 */
	public void setEdgeCodeStart(
			@FrameOffset long startFrame);
	
	/**
	 * <p>Create a cloned copy of this edgecode value.</p>
	 * 
	 * @return Cloned copy of this edgecode value.
	 */
	public EdgeCodeValue clone();
	
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
	public String toString();
}
