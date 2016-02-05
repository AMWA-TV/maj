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
 * $Log: DefinitionCriteriaByClassImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:51  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.union.DefinitionCriteriaType;


/** 
 * <p>Implementation of a criteria for matching a {@linkplain tv.amwa.maj.model.DefinitionObject definition} determined 
 * by reference to the unique identifier of a {@linkplain tv.amwa.maj.meta.ClassDefinition class definition}.</p>
 * 
 *
 */
public class DefinitionCriteriaByClassImpl 
	extends DefinitionCriteriaImpl
	implements tv.amwa.maj.union.DefinitionCriteriaByClass,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 4669571340682694185L;
	
	/** <p>Class ID of associated definition criteria.</p> */
	private AUIDImpl classId;
	
	/**
	 * <p>Create a criteria for matching a {@linkplain tv.amwa.maj.model.DefinitionObject definition} 
	 * from the unique identification of a class.</p>
	 * 
	 * @param classId Unique identifier of a class providing this definition criteria.
	 * 
	 * @throws NullPointerException The given class identifier is <code>null</code>.
	 */
	public DefinitionCriteriaByClassImpl(
			tv.amwa.maj.record.AUID classId) 
		throws NullPointerException {
		
		super(DefinitionCriteriaType.ByClass);
		setClassId(classId);
	}

	public AUIDImpl getClassId() {
		
		return classId;
	}

	public void setClassId(
			tv.amwa.maj.record.AUID classId) 
		throws NullPointerException {
		
		if (classId == null)
			throw new NullPointerException("Cannot set a definition criteria with a null class id.");
		
		if (classId instanceof AUIDImpl)
			this.classId = (AUIDImpl) classId;
		else
			this.classId = 
				new AUIDImpl(classId.getData1(), classId.getData2(), classId.getData3(), classId.getData4());
	}

	@Override
	public boolean equals(
			Object o) {
		
		if (o == null) return false;
		if (!(o instanceof tv.amwa.maj.union.DefinitionCriteriaByClass)) return false;
		
		return classId.equals(((tv.amwa.maj.union.DefinitionCriteriaByClass) o).getClassId());
	}
	
	/**
	 * <p>Creates a pseudo-XML representation of this definition criteria. No XML schema or
	 * DTD is defined. For example:</p>
	 * 
	 * <pre>
	 * &lt;DefinitionCriteria classId="urn:x-ul:060e2b34.0206.0101.0d010101.0101.1f00"/&gt;
	 * </pre>
	 */
	@Override
	public String toString() {

		return "<DefinitionCriteria classId=\"" + classId.toString() + "\"/>";
	}

	@Override
	public DefinitionCriteriaByClassImpl clone() 
		throws CloneNotSupportedException {

		DefinitionCriteriaByClassImpl cloned = (DefinitionCriteriaByClassImpl) super.clone();
		cloned.setClassId(classId);
		return cloned;
	}

	@Override
	public int hashCode() {

		return classId.hashCode();
	}
}
