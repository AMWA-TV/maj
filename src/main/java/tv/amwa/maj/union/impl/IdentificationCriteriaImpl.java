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
 * $Log: IdentificationCriteriaImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/12/18 17:56:01  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:28  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.union.IdentificationCriteriaType;


/** 
 * <p>Implementation of a criteria for matching an {@linkplain tv.amwa.maj.model.Identification identification}.</p>
 * 
 *
 */
public abstract class IdentificationCriteriaImpl 
	implements tv.amwa.maj.union.IdentificationCriteria,
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3890056988635607404L;
	
	private IdentificationCriteriaType idType;
	
	/** 
	 * <p>Create an IdentificationCriteria of the specified type.</p>
	 * 
	 * @param idType Type of this identification criteria.
	 */
	IdentificationCriteriaImpl(
			IdentificationCriteriaType idType) {

		this.idType = idType;
	}
	
	public IdentificationCriteriaType getIdentificationCriteriaType() {
		return idType;
	}	
}
