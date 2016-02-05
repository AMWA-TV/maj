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
 * $Log: SearchByPackageKindImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:46  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.PackageKind;
import tv.amwa.maj.union.SearchTag;

// TODO tests

/** 
 * <p>Implementaiton of a earch criteria for matching {@linkplain tv.amwa.maj.model.Package packages} defined by a 
 * {@linkplain tv.amwa.maj.enumeration.PackageKind kind of package}.</p>
 * 
 *
 */
public class SearchByPackageKindImpl 
	extends SearchCriteriaImpl
	implements tv.amwa.maj.union.SearchByPackageKind,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = -3750826008773216547L;
	
	/** <p>Kind of package to search for.</p> */
	private PackageKind kind;
	
	/** 
	 * <p>Create a search criteria defined by package kind.</p>
	 * 
	 * @param packagekind Kind of package to search for.
	 * 
	 * @throws NullPointerException The given package kind is <code>null</code>.
	 */
	public SearchByPackageKindImpl(
			PackageKind packagekind) {
		
		super(SearchTag.ByPackageKind);
		setKind(packagekind);
	}

	public PackageKind getKind() {
		
		return kind;
	}

	public void setKind(
			PackageKind kind) 
		throws NullPointerException {
		
		if (kind == null)
			throw new NullPointerException("The given package kind for this search by package kind criteria is null.");
		
		this.kind = kind;
	}

	@Override
	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof tv.amwa.maj.union.SearchByPackageKind)) return false;
		
		return kind == ((tv.amwa.maj.union.SearchByPackageKind) o).getKind();
	}
	
	/**
	 * <p>Create a pseudo-XML representation of this search by package kind criteria. No XML
	 * schema or DTD defines this element. For example:</p>
	 * 
	 * <pre>
	 * &lt;SearchByPackageKind kind="TapePackage"/&gt;
	 * </pre>
	 * 
	 * @return XML representation of this search by package kind criteria.
	 */
	@Override
	public String toString() {

		return "<SearchByPackageKind kind=\"" + kind.name() + "\"/>";
	}

	@Override
	public SearchByPackageKindImpl clone() 
		throws CloneNotSupportedException {
		
		return (SearchByPackageKindImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return kind.hashCode();
	}
	
}
