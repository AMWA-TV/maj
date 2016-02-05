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
 * $Log: TypeDefinitionSet.java,v $
 * Revision 1.10  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.9  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:40:08  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:28  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

/*
 * All methods to do with keys removed as this implementation uses a java.util.HashSet
 * as its backing for set values.
 */

import java.util.Set;

import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.Bool;


/**
 * <p>Specifies the definition of a property type that has a collection of object references 
 * to uniquely identified objects. The order of the objects has no meaning.</p>
 * 
 * <p>Note that the element type for a set must be a {@linkplain TypeDefinitionObjectReference reference
 * type}. For example and as a result, it is not possible to define a set of integer values.</p>
 * 
 *
 *
 * @see tv.amwa.maj.enumeration.TypeCategory#Set
 * @see TypeDefinitionFixedArray
 * @see TypeDefinitionVariableArray
 */

public interface TypeDefinitionSet 
	extends TypeDefinition {

	/**
	 * <p>Returns the type of elements referenced from the defined set.</p>
	 * 
	 * @return Type of elements referenced from the defined set.
	 * 
	 * @see TypeDefinitionObjectReference#getObjectType()
	 */
	public TypeDefinition getElementType();

	/**
	 * <p>Adds the given element to the given set, which is represented by the given
	 * property value of this set type definition.</p>
	 * 
	 * @param setPropertyValue Set to add the given element to.
	 * @param elementPropertyValue Element to add to the given set.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The type of the given property value
	 * does not match this set type definition.
	 * @throws BadTypeException The type of the given element property value
	 * does not match the element type of this set type definition.
	 */
	public void addElement(
			PropertyValue setPropertyValue,
			PropertyValue elementPropertyValue) 
		throws NullPointerException, 
			IllegalPropertyValueException,
			BadTypeException;

	/**
	 * <p>Removes the given element from the given set, which is represented by the given
	 * property value of this set type definition.</p>
	 * 
	 * @param setPropertyValue Set to remove the given element from.
	 * @param elementPropertyValue Element to remove from the given set.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The type of the given property value
	 * does not match this set type definition.
	 * @throws BadTypeException The type of the given element for removal does
	 * not match the element type of this set type definition.
	 * @throws ObjectNotFoundException The given element to remove cannot
	 * be found in the given set property value.

	 */
	public void removeElement(
			PropertyValue setPropertyValue,
			PropertyValue elementPropertyValue) 
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException,
			ObjectNotFoundException;

	/**
	 * <p>Returns <code>true</code> if the given element is contained in
	 * the given set; otherwise <code>false</code>. The set to test membership for is 
	 * represented by the given property value of this set type definition.</p>
	 * 
	 * @param setPropertyValue Set to check for the given element.
	 * @param elementPropertyValue Element to check for in the given set.
	 * @return Is the given element contained in the given set?
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The type of the given property value does
	 * not match this set type definition.
	 */
	public @Bool boolean containsElement(
			PropertyValue setPropertyValue,
			PropertyValue elementPropertyValue) 
		throws NullPointerException, IllegalPropertyValueException;

	/**
	 * <p>Returns the number of elements in the given set, or&nbsp;-1
	 * if the set is <code>null</code>. The set to count the members for is 
	 * represented by the given property value of this set type definition.</p>
	 * 
	 * @param setPropertyValue Property value containing a set.
	 * @return Number of elements in the given set.
	 * 
	 * @throws NullPointerException The given set property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The type of the given property value
	 * does not match this set type definition.
	 */
	public @UInt32 int getCount(
			PropertyValue setPropertyValue) 
		throws NullPointerException, 
			IllegalPropertyValueException;

	/**
	 * <p>Returns a copy of the set of all the elements in the given set property 
	 * value.</p>
	 * 
	 * @param setPropertyValue Property value to read and returns the elements
	 * for.
	 * @return Shallow copy of the value of the given set property value.
	 * 
	 * @throws NullPointerException The given set property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The type of the given argument does not
	 * match this set type definition.
	 */
	public Set<PropertyValue> getElements(
			PropertyValue setPropertyValue) 
		throws NullPointerException,
			IllegalPropertyValueException;
	
	/**
	 * <p>Create a property value with an empty set, ready to hold property values
	 * of this type definitions {@linkplain #getElementType() element type}. Call
	 * {@link #addElement(PropertyValue, PropertyValue)} to add elements to this
	 * set.</p>
	 *
	 * @return New property value containing an empty set.
	 */
	public PropertyValue createEmptySet();
	
	/**
	 * <p>Create a cloned copy of this set type definition.</p>
	 *
	 * @return Cloned copy of this set type definition.
	 */
	public TypeDefinitionSet clone();
	
}
