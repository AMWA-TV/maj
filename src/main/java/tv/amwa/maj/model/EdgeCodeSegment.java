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
 * $Log: EdgeCodeSegment.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/02/08 11:27:18  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2008/01/27 11:07:34  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:25  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.EdgeType;
import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.EdgecodeHeader;
import tv.amwa.maj.misctype.FrameOffset;
import tv.amwa.maj.record.EdgeCodeValue;

// Renamed EdgecodeSegment so that it does not get confused with EdgecodeValue.

/**
 * <p>Specifies the storage of film edge code information.</p>
 * 
 * <p>See the <a href="package-summary.html#namingConflicts">section on naming conflicts in the package 
 * documentation</a>.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.record.EdgeCodeValue
 * @see tv.amwa.maj.industry.Forge#makeEdgeCode()
 * @see tv.amwa.maj.industry.Forge#makeEdgeCode(long, tv.amwa.maj.enumeration.FilmType, tv.amwa.maj.enumeration.EdgeType)
 * @see tv.amwa.maj.industry.Forge#makeEdgeCode(long, tv.amwa.maj.enumeration.FilmType, tv.amwa.maj.enumeration.EdgeType, byte[])
 * @see tv.amwa.maj.industry.Forge#makeEdgeCode(long, tv.amwa.maj.enumeration.FilmType, tv.amwa.maj.enumeration.EdgeType, String)
 */

public interface EdgeCodeSegment 
	extends Segment {

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.record.EdgeCodeValue edgecode value} stored by this 
	 * edgecode segment.</p>
	 * 
	 * @return Edgecode value of this edgecode segment.
	 * 
	 * @see #getEdgeCodeFormat()
	 * @see #getEdgeCodeFilmFormat()
	 * @see #getEdgeCodeStart()
	 * @see #getEdgeCodeHeader()
	 * @see #getEdgeCodeHeaderAsString()
	 */
	public EdgeCodeValue getEdgecode();

	/**
	 * <p>Returns the format of the edge code.</p>
	 *
	 * @return Format of the edge code.
	 * 
	 * @see #getEdgecode()
	 * @see EdgeType
	 */
	public EdgeType getEdgeCodeFormat();

	/**
	 * <p>Returns the film format of the edge code.</p>
	 *
	 * @return Film format for this edge code.
	 * 
	 * @see #getEdgecode()
	 * @see FilmType
	 */
	public FilmType getEdgeCodeFilmFormat();

	/**
	 * <p>Returns the text prefix that identifies the film. Typically, this is a text string of no 
	 * more than 8&nbsp;7-bit ISO characters. This is an optional property.</p>
	 *
	 * @return Text prefix that identifies the film.
	 * 
	 * @throws PropertyNotPresentException The optional header property is not present for this edgecode
	 * value.
	 * 
	 * @see #getEdgecode()
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
	 * @see #getEdgecode()
	 * @see #getEdgeCodeHeader()
	 */
	public String getEdgeCodeHeaderAsString()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the edge code at the beginning of the corresponding segment.</p>
	 *
	 * @return Edge code the the beginning of the corresponding segment.
	 * 
	 * @see #getEdgecode()
	 */
	public @FrameOffset long getEdgeCodeStart();
	
	/**
	 * <p>Create a cloned copy of this edge code segment.</p>
	 *
	 * @return Cloned copy of this edge code segment.
	 */
	public EdgeCodeSegment clone();
}
