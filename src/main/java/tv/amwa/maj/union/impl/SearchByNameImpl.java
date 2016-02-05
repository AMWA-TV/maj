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
 * $Log: SearchByNameImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:50  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.union.SearchTag;

// TODO tests

/** 
 * <p>Implementation of a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} defined by 
 * package name.</p>
 * 
 * @see tv.amwa.maj.model.Package#getPackageName()
 * 
 *
 */
public class SearchByNameImpl 
	extends SearchCriteriaImpl
	implements tv.amwa.maj.union.SearchByName,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 1784079678995544295L;
	
	/** <p>Name defining this search criteria.</p> */
	private String name;
	
	/** 
	 * <p>Create a search criteria defined by the given package name.</p>
	 * 
	 * @param name Package name defining the new search criteria.
	 * 
	 * @throws NullPointerException The given name value is <code>null</code>.
	 */
	public SearchByNameImpl(
			String name) 
		throws NullPointerException {
		
		super(SearchTag.ByName);
		setName(name);
	}

	public String getName() {

		return name;
	}

	public void setName(
			String name) 
		throws NullPointerException {

		if (name == null)
			throw new NullPointerException("The given name to define this search criteria is null.");
		
		this.name = name;
	}

	@Override
	public boolean equals(
			Object o) {
		
		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof tv.amwa.maj.union.SearchByName)) return false;
		
		return name.equals(((tv.amwa.maj.union.SearchByName) o).getName());
	}

	/**
	 * <p>Create a pseudo-XML representation of this search criteria defined by package name. No XML
	 * schema or DTD defines this element. For example:</p>
	 * 
	 * <pre>
	 * &lt;SearchByName name="Watchdog series 27 episode 3"/&gt;
	 * </pre>
	 * 
	 * @return XML representation of this search by AUID criteria.
	 */
	@Override
	public String toString() {
		
		return "<SearchByName name=\"" + name + "\"/>";
	}

	@Override
	public SearchByNameImpl clone() 
		throws CloneNotSupportedException {
		
		return (SearchByNameImpl) super.clone();
	}

	@Override
	public int hashCode() {
		
		return name.hashCode();
	}
}
