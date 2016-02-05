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
 * $Log: SearchByAUIDImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:27  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.record.AUID;
import tv.amwa.maj.union.SearchTag;

// TODO tests

/** 
 * <p>Implementation of a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} defined by an 
 * {@linkplain tv.amwa.maj.record.AUID AUID} value. The kind of search represented by this 
 * criteria must be set using method {@link #setTag(SearchTag)} or through the constructor.</p>
 * 
 * @see tv.amwa.maj.model.ContentStorage#getPackages(tv.amwa.maj.union.SearchCriteria)
 * @see tv.amwa.maj.model.Preface#getPackages(tv.amwa.maj.union.SearchCriteria)
 * 
 *
 */
public class SearchByAUIDImpl 
	extends SearchCriteriaImpl
	implements tv.amwa.maj.union.SearchByAUID,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 8151301802260779701L;
	
	/** <p>AAF class identifier to search for.</p> */
	private AUID identification;

	/**
	 * <p>Create a search criteria defined by an AUID and the kind of item that the
	 * item identifies.</p>
	 * 
	 * @param tag Kind of item that the AUID identifies, indicating the kind of match
	 * performed by a search using this criteria.
	 * @param identification Identifier defining this search criteria.
	 * 
	 * @throws NullPointerException One or both of the given tag or identification values 
	 * is <code>null</code>.
	 */
	public SearchByAUIDImpl(
			SearchTag tag, 
			AUID identification) 
		throws NullPointerException,
			IllegalArgumentException {
		
		setAUID(identification);
		setTag(tag);
	}

	public AUID getAUID() {
		
		return identification;
	}

	public void setAUID(
			AUID identification)
		throws NullPointerException {

		if (identification == null)
			throw new NullPointerException("The given identification for this AUID search criteria is null.");
		
		this.identification = identification.clone();  
	}
	
	public void setTag(
			SearchTag tag)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (tag == null)
			throw new NullPointerException("Cannot set the type of a search by AUID criteria with a null search tag.");
		
		if ((tag == SearchTag.ByMediaCrit) || (tag == SearchTag.ByPackageID) ||
				(tag == SearchTag.ByPackageKind) || (tag == SearchTag.ByName) ||
				(tag == SearchTag.NoSearch))
			throw new IllegalArgumentException("Search criteria tag is not compatible with searching using AUIDs.");

		setSearchTag(tag);
	}
	
	@Override
	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof tv.amwa.maj.union.SearchByAUID)) return false;
		
		tv.amwa.maj.union.SearchByAUID testCriteria = (tv.amwa.maj.union.SearchByAUID) o;
		
		if (testCriteria.getSearchTag() != getSearchTag()) return false;
		if (!(identification.equals(testCriteria.getAUID()))) return false;
		
		return true;
	}
	
	/**
	 * <p>Create a pseudo-XML representation of this search by AUID criteria. No XML
	 * schema or DTD defines this element. For example:</p>
	 * 
	 * <pre>
	 * &lt;SearchByDataDef identification="urn:x-ul:060e2b34.0401.0101.01030202.01000000"/&gt;
	 * </pre>
	 * 
	 * @return XML representation of this search by AUID criteria.
	 */
	@Override
	public String toString() {

		return "<" + getSearchTag().name() + " identification=\"" + identification.toString() + "\"/>";
	}

	@Override
	public SearchByAUIDImpl clone() 
		throws CloneNotSupportedException {

		return (SearchByAUIDImpl) super.clone();
	}

	@Override
	public int hashCode() {
		
		return identification.hashCode() ^ getSearchTag().hashCode();
	}	
}
