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
 * $Log: HTMLClip.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/27 11:07:36  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:45  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

// Documentation in AAFSpecDR4.pdf 1.0 so I'm guessing this is old and dusty.

import tv.amwa.maj.exception.PropertyNotPresentException;

/**
 *
 * <p>Specifies a reference to HTML text essence.</p>
 * 
 * <p>Typically, an HTML clip is in a {@linkplain StaticTrack static track} and defines a section of 
 * HTML text that is associated with the essence data in a parallel {@linkplain TimelineTrack timeline
 * track}. The length of the HTML clip defines the extent of the association with the parallel track in
 * the same {@linkplain Package package}.</p>
 * 
 * <p>The {@linkplain #getBeginAnchor() begin anchor} and {@linkplain #getEndAnchor() end anchor} properties 
 * specify the HTML tags that delineate the start and end of
 * the referenced text. The begin anchor tag shall precede the end anchor tag. If the begin anchor and end
 * anchor properties are omitted, the HTML clip references all the HTML text in the essence data object.</p>
 *
 *
 * 
 * @see HTMLDescriptor
 */
public interface HTMLClip
	extends TextClip {

	/**
	 * <p>Sets the begin anchor property of this HTML clip, which delineates the anchor that is the start of 
	 * the reference's text. Set this optional property to <code>null</code> to omit it.</p>
	 *
	 * @param beginAnchor Begin anchor property of this HTML clip.
	 */
	public void setBeginAnchor(
			String beginAnchor);

	/**
	 * <p>Returns the begin anchor property of this HTML clip, which delineates the anchor that is the start 
	 * of the reference's text. This is an optional property.</p>
	 *
	 * @return Begin anchor property of this HTML clip.
	 * 
	 * @throws PropertyNotPresentException The optional begin anchor property is not
	 * present in this HTML clip.
	 */
	public String getBeginAnchor()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the end anchor property of this HTML clip, which delineates the anchor that is the end of the 
	 * reference's text. Set this optional property to <code>null</code> to omit it.</p>
	 *
	 * @param endAnchor End anchor property of this HTML clip.
	 */
	public void setEndAnchor(
			String endAnchor);

	/**
	 * <p>Returns the end anchor property of this HTML clip, which delineates the anchor that is the end of the 
	 * reference's text. This is an optional property.</p>
	 *
	 * @return End anchor property of this HTML clip.
	 * 
	 * @throws PropertyNotPresentException The optional end anchor property is not
	 * present in this HTML clip.
	 */
	public String getEndAnchor()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this HTML clip.</p>
	 *
	 * @return Cloned copy of this HTML clip.
	 */
	public HTMLClip clone();
}
