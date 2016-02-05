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
 * $Log: Filler.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2008/01/27 11:07:29  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:17  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

/**
 * <p>Specifies an unspecified value for the duration of a {@linkplain Component component}.</p>
 * 
 * <p>If a filler segment is played, applications can choose any appropriate blank essence to play. 
 * Typically, a video filler segment would be played as a black section, and an audio filler segment 
 * would be played as a silent section.</p>
 * 
 *
 * 
 * @see Component#getComponentLength()
 */

public interface Filler 
	extends Segment {

	/**
	 * <p>Create a cloned copy of this filler.</p>
	 *
	 * @return Cloned copy of this filler.
	 */
	public Filler clone();
}
