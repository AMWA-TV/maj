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
 * $Log: DefinitionCriteriaNoSearchImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:26  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.union.DefinitionCriteriaType;


/** 
 * <p>Implementation of a criteria for matching a {@linkplain tv.amwa.maj.model.DefinitionObject definition} that
 * matches nothing.</p>
 * 
 *
 */

public class DefinitionCriteriaNoSearchImpl 
	extends DefinitionCriteriaImpl 
	implements tv.amwa.maj.union.DefinitionCriteriaNoSearch,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = -1435146082856437153L;

	/**
	 * <p>Create a definition criteria that matches nothing.</p>
	 */
	public DefinitionCriteriaNoSearchImpl() {
		
		super(DefinitionCriteriaType.NoSearch);
	}
	
	@Override
	public boolean equals(
			Object o) {

		if (o == null) return false;
		return o instanceof tv.amwa.maj.union.DefinitionCriteriaNoSearch;
	}
	
	/**
	 * <p>Creates a pseudo-XML representation of this definition criteria. No XML schema or
	 * DTD is defined. For example:</p>
	 * 
	 * <pre>
	 * &lt;DefinitionCriteria /&gt;
	 * </pre>
	 */
	@Override
	public String toString() {
		
		return "<DefinitionCriteria />";
	}

	@Override
	public DefinitionCriteriaNoSearchImpl clone() 
		throws CloneNotSupportedException {

		return (DefinitionCriteriaNoSearchImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return 0;
	}
}
