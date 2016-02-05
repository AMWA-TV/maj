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
 * $Log: TypeDefinitionEnumeration.java,v $
 * Revision 1.9  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2010/12/15 18:51:45  vizigoth
 * Added facility to map from ordinal element values to enumeration type values.
 *
 * Revision 1.4  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:40:07  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:17  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.misctype.AAFString;

// TODO no set value method in this interface?

/**
 * <p>Specifies the definition of a property type that can have one of a set of 
 * integer values. Every element in the set has both an integer value and a string label. The 
 * enumeration type is equivalent to the Java {@link java.lang.Enum} type.</p></p>
 * 
 *
 *
 * @see tv.amwa.maj.enumeration.TypeCategory#Enum
 * @see java.lang.Enum
 */

public interface TypeDefinitionEnumeration 
	extends TypeDefinition {

	/**
	 * <p>Creates and returns an enumeration property value of this enumeration
	 * type using the given enumeration element name.</p>
	 * 
	 * @param name Name of an enumeration element to create a property value
	 * for.
	 * @return Newly created property value element set to the given
	 * element name.
	 * 
	 * @throws NullPointerException The given element name is <code>null</code>.
	 * @throws InvalidParameterException The given name is not in the
	 * list of element names for this enumeration type.
	 * 
	 * @see #createValueFromOrdinal(Number)
	 */
	public PropertyValue createValueFromName(
			@AAFString String name) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Creates and returns an enumeration property value of this enumeration type
	 * using the given enumeration element value.</p>
	 * 
	 * @param ordinal Element value to create a property value for.
	 * @return Newly created property value element set to the given
	 * element value.
	 * 
	 * @throws NullPointerException The given element value is <code>null</code>.
	 * @throws InvalidParameterException The given name is not in the
	 * list of element values for this enumeration type.
	 * 
	 * @see #createValueFromName(String)
	 */
	public PropertyValue createValueFromOrdinal(
			Number ordinal)
		throws NullPointerException,
			InvalidParameterException;
	
	/**
	 * <p>Returns the {@linkplain TypeDefinition type definition} of the integer
	 * values that are found in this enumeration. For data types created using the MAJ API, this 
	 * is always an {@link tv.amwa.maj.integer.Int32}.</p>
	 * 
	 * @return Type definition of the integer values that are found in 
	 * this enumeration.
	 * 
	 * @see TypeDefinitionInteger
	 */
	public TypeDefinition getElementType();

	/**
	 * <p>Returns the integer value associated with the enumeration
	 * element at the given index in the list of elements of this enumeration type 
	 * definition.</p>
	 * 
	 * @param index 0-based index of the integer value to retrieve from the list of elements of
	 * this enumeration type definition.
	 * @return Integer value of the element at this given index into
	 * the enumeration type definition.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the
	 * acceptable range for the enumeration type definition.
	 */
	public @UInt64 long getElementValue(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the name of the enumeration element at the given index into the
	 * list of elements of this enumeration type definition.</p>
	 * 
	 * @param index 0-based index of the name to retrieve from the list of elements 
	 * of this enumeration type definition.
	 * @return Name of the enumeration element at the given index in the list of
	 * elements of this enumeration type definition.
	 */
	public @AAFString String getElementName(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the number of enumeration elements contained in the list of elements
	 * of this enumeration type definition.</p>
	 * 
	 * @return Number of enumeration elements contained in this
	 * enumeration type definition.
	 */
	public @UInt32 int countElements();

	/**
	 * <p>Returns the name associated with the 
	 * given enumeration property value as defined by this enumeration
	 * type definition.</p>
	 * 
	 * @param value Enumeration property value to find the name of.
	 * @return Name associated with the given enumeration property
	 * definition according to this enumeration type definition.
	 * 
	 * @throws NullPointerException The given property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not 
	 * a value of this enumeration property type.
	 */
	public @AAFString String getNameFromValue(
			PropertyValue value) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Returns the name element with the given integer
	 * in this enumeration type definition.</p>
	 * 
	 * @param value Integer value associated with an enumeration
	 * element.
	 * @return Name of the element with the given integer
	 * in this enumeration type definition.
	 * 
	 * @throws IllegalPropertyValueException The given value is not associated
	 * with an enumeration element in this enumeration type definition.
	 */
	public @AAFString String getNameFromInteger(
			@Int64 long value) 
		throws IllegalPropertyValueException;

	/**
	 * <p>Returns the integer value associated with the given enumeration
	 * property value as defined by this enumeration type definition.</p>
	 * 
	 * @param enumerationProperty Enumeration property value
	 * to find the associated integer for.
	 * @return Integer value associated with the given property value
	 * as defined by this enumeration type definition. 
	 * 
	 * @throws NullPointerException The given enumeration property is 
	 * <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not
	 * defined by the enumeration type definition.
	 */
	public @Int64 long getIntegerValue(
			PropertyValue enumerationProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Returns the {@link java.lang.Enum} Java type associated with 
	 * this enumeration type definition.</p>
	 *
	 * @return Java enumeration associated with this type definition.
	 */
	public Class<Enum<?>> getEnumeration();
	
	/**
	 * <p>Create a cloned copy of this enumeration type definition.</p>
	 *
	 * @return Cloned copy of this enumeration type definition.
	 */
	public TypeDefinitionEnumeration clone();
}
