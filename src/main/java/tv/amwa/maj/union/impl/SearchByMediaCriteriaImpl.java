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
 * $Log: SearchByMediaCriteriaImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:45  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.CriteriaType;
import tv.amwa.maj.union.SearchTag;

// TODO tests

/** 
 * <p>Implementation of a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} defined by media 
 * criteria. This can be used to search for a version of media that is the best fidelity, smallest representation etc. 
 * as defined by a {@linkplain tv.amwa.maj.enumeration.CriteriaType criteria type}.</p>
 * 
 * @see tv.amwa.maj.enumeration.CriteriaType
 * 
 *
 */
public class SearchByMediaCriteriaImpl 
	extends SearchCriteriaImpl 
	implements tv.amwa.maj.union.SearchByMediaCriteria,
		Serializable,
		Cloneable {
	
	private static final long serialVersionUID = -5063897798486293586L;
	
	/** <p>Media criteria type to search for.</p> */
	private CriteriaType mediaCriteria;
	
	/**
	 * <p>Create a search criteria defined by the given media criteria type.</p>
	 * 
	 * @param mediaCriteria Media criteria type to search for.
	 * 
	 * @throws NullPointerException The given media criteria type is <code>null</code>.
	 */
	public SearchByMediaCriteriaImpl(
			CriteriaType mediaCriteria) 
		throws NullPointerException {
		
		super(SearchTag.ByMediaCrit);
		setMediaCriteria(mediaCriteria);
	}

	public CriteriaType getMediaCriteria() {
		
		return mediaCriteria;
	}

	public void setMediaCriteria(
			CriteriaType mediaCriteria) {
		
		if (mediaCriteria == null)
			throw new NullPointerException("The given media criteria to use to define this search criteria is null.");
		
		this.mediaCriteria = mediaCriteria;
	}

	@Override
	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof tv.amwa.maj.union.SearchByMediaCriteria)) return false;
		
		return mediaCriteria == ((tv.amwa.maj.union.SearchByMediaCriteria) o).getMediaCriteria();
	}
	
	/**
	 * <p>Create a pseudo-XML representation of this search by media criteria. No XML
	 * schema or DTD defines this element. For example:</p>
	 * 
	 * <pre>
	 * &lt;SearchByMediaCrit criteria="FastestRepresentation"/&gt;
	 * </pre>
	 * 
	 * @return XML representation of this search by media criteria.
	 */
	@Override
	public String toString() {

		return "<SearchByMediaCrit criteria=\"" + mediaCriteria.name() + "\"/>";
	}

	@Override
	public SearchByMediaCriteriaImpl clone() 
		throws CloneNotSupportedException {

		return (SearchByMediaCriteriaImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return mediaCriteria.hashCode();
	}
}
