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
 * $Log: IdentificationCriteriaByIdGenerationImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:34  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.record.AUID;
import tv.amwa.maj.union.IdentificationCriteriaType;


/** 
 * <p>Implementation of a criteria for matching an {@linkplain tv.amwa.maj.model.Identification identification}
 * by its generation id.</p>
 *
 *
 */
public class IdentificationCriteriaByIdGenerationImpl 
	extends IdentificationCriteriaImpl
	implements tv.amwa.maj.union.IdentificationCriteriaByIdGeneration,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 6709112346767175225L;
	
	/** <p>Generation id defining this indentification criteria.</p> */
	private AUID generationID;
	
	/** 
	 * <p>Create a new identification criteria with a generation ID.</p>
	 * 
	 * @param generationID Generation identifier to match with this identification criteria.
	 * 
	 * @throws NullPointerException The given generation id is <code>null</code>.
	 */
	public IdentificationCriteriaByIdGenerationImpl(
			AUID generationID) 
		throws NullPointerException {
		
		super(IdentificationCriteriaType.ByIdGeneration);
		setGenerationID(generationID);
	}

	public AUID getGenerationID() {

		return generationID;
	}

	public void setGenerationID(
			tv.amwa.maj.record.AUID generationID) 
		throws NullPointerException {

		if (generationID == null)
			throw new NullPointerException("Cannot set the generation id of an identification criteria with a null value.");
		
		this.generationID = generationID.clone();
	}

	@Override
	public boolean equals(Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof tv.amwa.maj.union.IdentificationCriteriaByIdGeneration)) return false;
		
		return generationID.equals(((tv.amwa.maj.union.IdentificationCriteriaByIdGeneration) o).getGenerationID());
	}
	
	/**
	 * <p>Pseudo-XML representation of this identification criteria. No corresponding XML schema or DTD is defined.
	 * For example:</p>
	 * 
	 * <pre>
	 * &lt;IdentificationCriteria generationId="urn:uuid:7ab65789-dfed-7891-f6b7-a8d67892"/&gt;
	 * </pre>
	 * 
	 * @return String representation of an identification criteria defined by generation id.
	 */
	@Override
	public String toString() {

		return "<IdentificationCriteria generationId=\"" + generationID.toString() + "\"/>";
	}

	@Override
	public IdentificationCriteriaByIdGenerationImpl clone()
		throws CloneNotSupportedException {

		return (IdentificationCriteriaByIdGenerationImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return generationID.hashCode();
	}
}
