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
 * $Log: TaggedValueDefinition.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/08 11:27:21  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2007/12/04 13:04:52  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:56  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.Set;

import tv.amwa.maj.exception.NoMoreObjectsException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.PropertyDefinition;



/**
 * <p>Specifies the documentation of {@linkplain TaggedValue tagged values} used in a file.
 * The {@linkplain DefinitionObject#getName() name of the definition} is the same
 * as the name of the tagged value it documents. The {@linkplain DefinitionObject#getDescription() 
 * description of the definition} provides the documentation.</p>
 * 
 * <p>The extended optional <em>parent properties</em> property is available to assist applications
 * but is not a built-in property that is persistent in files.</p>
 * 
 *
 *
 * @see Dictionary#getTaggedValueDefinitions()
 * @see DefinitionObject#setDescription(String)
 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueDefinitionStrongReferenceSet
 */

public interface TaggedValueDefinition 
	extends DefinitionObject {

	/**
	 * <p>Add a parent property to the list of parent properties of this tagged
	 * value definition, which specify which properties specify tagged values of
	 * this kind. For example, {@linkplain Package#getPackageUserComments() package user comments} or
	 * {@linkplain Component#getComponentAttributes() component attributes}. </p>
	 * 
	 * <p>This is an extended optional property. If this 
	 * property is omitted, a successful call to this method will make it present.</p>
	 * 
	 * @param parentProperty Parent property to add to the list of parent properties
	 * of the tagged value definition.
	 * 
	 * @throws NullPointerException The given parent property is <code>null</code>.
	 */
	public void addParentProperty(
			PropertyDefinition parentProperty) 
		throws NullPointerException;

	/**
	 * <p>Returns the set of parent property definitions of this tagged value definition,
	 * which specify which properties specify tagged values of
	 * this kind. For example, {@linkplain Package#getPackageUserComments() package user comments} or
	 * {@linkplain Component#getComponentAttributes() component attributes}. This is an
	 * extended optional property.</p>
	 * 
	 * @return Shallow copy of the set of parent property definitions of this tagged
	 * value definition.
	 * 
	 * @throws PropertyNotPresentException The optional parent properties property
	 * is not present in this tagged value definition.
	 */
	public Set<? extends PropertyDefinition> getParentProperties()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of parent properties of this tagged value definition, 
	 * which specify which properties specify tagged values of
	 * this kind. For example, {@linkplain Package#getPackageUserComments() package user comments} or
	 * {@linkplain Component#getComponentAttributes() component attributes}. This is an
	 * extended optional property.</p>
	 * 
	 * @return Number of parent properties of this tagged value definition.
	 * 
	 * @throws PropertyNotPresentException The optional parent properties property
	 * is not present in this tagged value definition.
	 */
	public @UInt32 int countParentProperties()
		throws PropertyNotPresentException;

	/**
	 * <p>Removes the given parent {@linkplain PropertyDefinition property}
	 * from the list of parent properties of this tagged value definition,
	 * which specify which properties specify tagged values of
	 * this type. For example, {@linkplain Package#getPackageUserComments() package user comments} or
	 * {@linkplain Component#getComponentAttributes() component attributes}. This is an
	 * extended optional property. Removing the last parent property from the
	 * set will omit the property.</p>
	 * 
	 * @param parentProperty Property definition to remove from the list of parent
	 * properties of this tagged value definition.
	 * 
	 * @throws NullPointerException The given parent property to remove is not present 
	 * in the set of parent properties of this tagged value definition.
	 * @throws NoMoreObjectsException The property definition is not in the set
	 * of parent property definitions of this tagged value definition.
	 * @throws PropertyNotPresentException The optional parent properties property
	 * is not present in this tagged value definition.
	 */
	public void removeParentProperty(
			PropertyDefinition parentProperty) 
		throws NullPointerException,
			NoMoreObjectsException,
			PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this tagged value definition.</p>
	 *
	 * @return Cloned copy of this tagged value definition.
	 */
	public TaggedValueDefinition clone();
}