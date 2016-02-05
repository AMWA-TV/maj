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
 * $Log: IdentificationCriteriaNoIdSearchImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:35  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.union.IdentificationCriteriaType;


/**
 * <p>Implementation of a criteria for matching an {@linkplain tv.amwa.maj.model.Identification identification}
 * that matches nothing.</p>
 *
 *
 */
public class IdentificationCriteriaNoIdSearchImpl 
	extends IdentificationCriteriaImpl
	implements tv.amwa.maj.union.IdentificationCriteriaNoIdSearch,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 2149914413733298761L;

	/** 
	 * <p>Create a new criteria that matches no identifications.</p>
	 */
	public IdentificationCriteriaNoIdSearchImpl() {
		super(IdentificationCriteriaType.NoIdSearch);
	}

	@Override
	public boolean equals(Object o) {

		if (o == null) return false;
		return o instanceof tv.amwa.maj.union.IdentificationCriteriaNoIdSearch;
	}
	
	/**
	 * <p>Pseudo-XML representation of this identification criteria. No corresponding XML schema or DTD is defined.
	 * For example:</p>
	 * 
	 * <pre>
	 * &lt;IdentificationCriteria /&gt;
	 * </pre>
	 * 
	 * @return String representation of an identification criteria that matches nothing.
	 */
	@Override
	public String toString() {
	
		return "<IdentificationCriteria />";
	}

	@Override
	public IdentificationCriteriaNoIdSearchImpl clone() 
		throws CloneNotSupportedException {

		return (IdentificationCriteriaNoIdSearchImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return 0;
	}
}
