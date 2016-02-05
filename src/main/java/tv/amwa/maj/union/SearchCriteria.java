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
 * $Log: SearchCriteria.java,v $
 * Revision 1.5  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/14 16:07:33  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:04  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;


/**
 * <p>Specifies a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} 
 * specified in a number of different ways.</p>
 * 
 * @see tv.amwa.maj.model.ContentStorage#getPackages(SearchCriteria)
 * @see tv.amwa.maj.model.Preface#getPackages(SearchCriteria)
 * @see SearchTag
 * 
 *
 */

public abstract interface SearchCriteria {

	/**
	 * <p>Returns the {@linkplain SearchTag tag} that defines the kind of search 
	 * criteria.</p>
	 *
	 * @return Tag defining the kind of search criteria.
	 */
	public SearchTag getSearchTag();
}
