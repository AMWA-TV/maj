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
 * $Log: SearchByName.java,v $
 * Revision 1.5  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/14 16:07:25  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:12  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;


/**
 * <p>Specifies a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} defined by 
 * package name.</p>
 * 
 * @see tv.amwa.maj.model.Package#getPackageName()
 * 
 *
 */
public interface SearchByName 
	extends SearchCriteria {

	/**
	 * <p>Returns the name that defines the search criteria.</p>
	 *
	 * @return Name defining the search criteria.
	 */
	public String getName();

	/**
	 * <p>Sets the name the defines the search criteria.</p>
	 *
	 * @param name Name defining the search criteria.
	 * 
	 * @throws NullPointerException The given name is <code>null</code>.
	 */
	public void setName(
			String name) 
		throws NullPointerException;
}
