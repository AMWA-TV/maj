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
 * $Log: TaggedValueDefinitionImpl.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2007/12/04 13:04:49  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:55  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tv.amwa.maj.exception.NoMoreObjectsException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.TaggedValueDefinition;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements the documentation of {@linkplain tv.amwa.maj.model.TaggedValue tagged values} used in a file.
 * The {@linkplain tv.amwa.maj.model.DefinitionObject#getName() name of the definition} is the same
 * as the name of the tagged value it documents. The {@linkplain tv.amwa.maj.model.DefinitionObject#getDescription() 
 * description of the definition} provides the documentation.</p>
 *
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueDefinitionStrongReferenceSet
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x4c00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TaggedValueDefinition",
		  description = "The TaggedValueDefinition class documents the TaggedValue objects used in the file.",
		  symbol = "TaggedValueDefinition")
public class TaggedValueDefinitionImpl
	extends 
		DefinitionObjectImpl
	implements 
		TaggedValueDefinition,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 6631196590993410617L;
	
	private transient Set<PropertyDefinition> parentProperties = null;

	public TaggedValueDefinitionImpl() { }

	/**
	 * <p>Creates and initialize all the fields of a tagged value definition, which documents 
	 * the {@link TaggedValueImpl tagged value} objects used in the file.</p>
	 * 
	 * @param identification Unique identifier for the new tagged value definition object.
	 * @param name Display name of the new tagged value definition object. 
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 */
	public TaggedValueDefinitionImpl(
			AUID identification,
			@AAFString String name) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a tagged value definition with a null id.");
		
		setAUID(identification);
		setName(name);
	}

	public void addParentProperty(
			PropertyDefinition parentProperty)
		throws NullPointerException {

		if (parentProperty == null)
			throw new NullPointerException("Cannot add a null value to the set of parent properties.");
		
		if (parentProperties == null)
			parentProperties = Collections.synchronizedSet(new HashSet<PropertyDefinition>());
		
		parentProperties.add(parentProperty);
	}

	public int countParentProperties() 
		throws PropertyNotPresentException {

		if (parentProperties == null)
			throw new PropertyNotPresentException("The optional parent properties property is not present for this tagged value definition.");
		
		return parentProperties.size();
	}

	public Set<PropertyDefinition> getParentProperties() 
		throws PropertyNotPresentException {
		
		if (parentProperties == null)
			throw new PropertyNotPresentException("The optional parent properties property is not present for this tagged value definition.");

		return new HashSet<PropertyDefinition>(parentProperties);
	}	

	public void removeParentProperty(
			tv.amwa.maj.meta.PropertyDefinition parentProperty)
		throws NullPointerException,
			NoMoreObjectsException,
			PropertyNotPresentException {

		if (parentProperty == null)
			throw new NullPointerException("Cannot remove a parent property from this tagged value definition using a null value.");
		
		if (parentProperties == null)
			throw new PropertyNotPresentException("The optional parent properties property is not present for this tagged value definition.");		
		
		if (!(parentProperties.contains(parentProperty)))
			throw new NoMoreObjectsException("The given parent property is not contained within the set of parent properties of this tagged value definition.");
	
		parentProperties.remove(parentProperty);
		
		if (parentProperties.size() == 0) parentProperties = null;
	}

	public TaggedValueDefinition clone() {
		
		return (TaggedValueDefinition) super.clone();
	}
}
