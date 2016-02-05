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
 * $Log: SearchByMediaCriteria.java,v $
 * Revision 1.5  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/14 16:07:35  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:12  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.enumeration.CriteriaType;

/**
 * <p>Specifies a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} defined by media 
 * criteria. This can be used to search for a version of media that is the best fidelity, smallest representation etc. 
 * as defined by a {@linkplain tv.amwa.maj.enumeration.CriteriaType criteria type}.</p>
 * 
 * @see tv.amwa.maj.enumeration.CriteriaType
 * 
 *
 */
public interface SearchByMediaCriteria 
	extends SearchCriteria {
	
	/**
	 * <p>Returns the media criteria search type.</p>
	 *
	 * @return Media criteria search type.
	 */
	public CriteriaType getMediaCriteria();

	/**
	 * <p>Sets the media criteria search type.</p>
	 *
	 * @param mediaCriteria Media criteria search type.
	 * 
	 * @throws NullPointerException The given media criteria is <code>null</code>.
	 */
	public void setMediaCriteria(
			CriteriaType mediaCriteria) 
		throws NullPointerException;
}
