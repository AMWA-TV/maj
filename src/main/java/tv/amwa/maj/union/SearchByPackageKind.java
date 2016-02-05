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
 * $Log: SearchByPackageKind.java,v $
 * Revision 1.2  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/14 16:07:40  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:00  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.enumeration.PackageKind;

/**
 * <p>Specifies a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} defined by a 
 * {@linkplain tv.amwa.maj.enumeration.PackageKind kind of package}.</p>
 * 
 * @see tv.amwa.maj.enumeration.PackageKind 
 * 
 *
 *
 */
public interface SearchByPackageKind 
	extends SearchCriteria {

	/**
	 * <p>Returns the kind of package of the search criteria.</p>
	 *
	 * @return Kind of package of the search criteria.
	 */
	public PackageKind getKind();

	/**
	 * <p>Sets the kind of package of the search criteria.</p>
	 *
	 * @param kind Kind of package of the search criteria.
	 * 
	 * @throws NullPointerException The given package kind is <code>null</code>.
	 */
	public void setKind(
			PackageKind kind)
		throws NullPointerException;
	
}
