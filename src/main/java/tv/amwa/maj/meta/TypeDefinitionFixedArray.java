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
 * $Log: TypeDefinitionFixedArray.java,v $
 * Revision 1.9  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:40:06  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:41  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import java.util.List;

import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.UInt32;



/**
 * <p>Specifies the definition of a property type that has a fixed number of values 
 * of the underlying type. The order of the values is meaningful.</p>
 * 
 *
 *
 * @see tv.amwa.maj.enumeration.TypeCategory#FixedArray
 * @see TypeDefinitionVariableArray
 * @see TypeDefinitionSet
 */

public interface TypeDefinitionFixedArray 
	extends TypeDefinition {

	/**
	 * <p>Returns the underlying type of that defines the type of each element of the array
	 * defined by this fixed array type definition.</p>
	 * 
	 * @return Element type property of this fixed array type definition.
	 */
	public TypeDefinition getType();

	/**
	 * <p>Returns the number of elements of an array defined by
	 * this fixed array type definition.</p>
	 * 
	 * @return Number of elements of an array defined by this fixed 
	 * array type definition.
	 */
	public @UInt32 int getCount();

	/**
	 * <p>Creates and returns a fixed array property value from the 
	 * given array of property values.</p>
	 * 
	 * @param elementValues Property value array to use to initialize the new
	 * fixed array property value.
	 * @return Newly created fixed array property value with values copied
	 * from the given property value array.
	 * 
	 * @throws NullPointerException The given array of element values is <code>null</code> or
	 * one or more of its elements is <code>null</code>.
	 * @throws BadParameterException The given array of property values is 
	 * of a different size to that required by the fixed array type definition, 
	 * as returned by {@link #getCount()}.
	 * @throws BadTypeException One or more of the elements of the given
	 * property value array cannot be converted to values of the underlying type 
	 * of the fixed type array definition, as returned by {@link #getType()}.
	 */
	public PropertyValue createValueFromValues(
			PropertyValue[] elementValues) 
		throws NullPointerException,
			BadParameterException,
			BadTypeException;
	
	/**
	 * <p>Creates and returns a new fixed array property value from
	 * the given Java array. The elements of the Java array are converted
	 * to property values of the underlying type of the fixed array
	 * type definition, as returned by {@link #getType()} and using a
	 * call to {@link TypeDefinition#createValue(Object)}.</p>
	 * 
	 * @param initialData Java array of data to use to initialize the new
	 * fixed array property value.
	 * @return Newly created fixed array property value with values converted
	 * from the given Java array.
	 * 
	 * @throws NullPointerException The argument is <code>null</code>, or one or more of 
	 * the elements of the given Java array are <code>null</code>.
	 * @throws BadParameterException The given Java array is of a different
	 * size to that required by the fixed array type definition, as returned
	 * by {@link #getCount()}.
	 * @throws BadTypeException One or more of the elements of the given
	 * Java array cannot be converted to values of the underlying type of the
	 * fixed type array definition, as returned by {@link #getType()}.
	 * 
	 * @see TypeDefinition#createValue(Object)
	 * @see #getArray(PropertyValue)
	 * @see #setArray(PropertyValue, Object[])
	 */
	public PropertyValue createValueFromArray(
			Object[] initialData) 
		throws NullPointerException,
			BadParameterException,
			BadTypeException;

	/**
	 * <p>Returns the member property value at the given index through the given fixed array
	 * property value.</p>
	 * 
	 * @param arrayProperty Fixed array property value to retrieve a 
	 * a member property value from.
	 * @param index 0-based index into the given fixed array property value
	 * to use to locate the required member property value.
	 * @return Member property value at the given index in the given
	 * fixed array property value.
	 * 
	 * @throws NullPointerException The given fixed array property value is <code>null</code>.
	 * @throws IndexOutOfBoundsException The given index is outside
	 * the acceptable range for an array defined by this fixed array
	 * type definition.
	 * @throws IllegalPropertyValueException The fixed array property value is not
	 * defined by this fixed array type definition.
	 */
	public PropertyValue getElementValue(
			PropertyValue arrayProperty,
			@UInt32 int index) 
		throws NullPointerException,
			IndexOutOfBoundsException,
			IllegalPropertyValueException;
	
	/**
	 * <p>Returns a Java array created from the elements of the
	 * given fixed array property value. The array is created with 
	 * a call to {@link PropertyValue#getValue()} on each of the 
	 * members of the given array.</p>
	 * 
	 * @param arrayProperty Fixed array property value to create a
	 * fixed array property value from.
	 * @return Java array containing elements with values matching the
	 * values of the elements of the given fixed array property value.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws IllegalPropertyValueException The fixed array property value is not
	 * defined by the fixed array type definition.
	 * 
	 * @see #createValueFromArray(Object[])
	 * @see #getElements(PropertyValue)
	 */
	public Object[] getArray(
			PropertyValue arrayProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Sets the value of a member of the given fixed array property
	 * value at the given index using the given value.</p>
	 * 
	 * @param arrayProperty Fixed array property value for which a member
	 * is to be set.
	 * @param index 0-based index of the member value to change.
	 * @param memberProperty Replacement value for the member of the given
	 * array at the given index. 
	 * 
	 * @throws NullPointerException One or both of the array and/or member property
	 * values is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The fixed array property value is not
	 * defined by this fixed array type definition.
	 * @throws IndexOutOfBoundsException The index is outside the acceptable
	 * range for arrays of this fixed array type definition.
	 * @throws BadTypeException The given member property definition is 
	 * not compatible with the underlying type of the fixed array type
	 * definition, as returned by {@link #getType()}.
	 * 
	 * @see #getElements(PropertyValue)
	 */
	public void setElementValue(
			PropertyValue arrayProperty,
			@UInt32 int index,
			PropertyValue memberProperty) 
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException,
			BadTypeException;

	/**
	 * <p>Sets the value of the given fixed array property value using
	 * the given Java array, setting the elements of the fixed array
	 * to be values given by the elements of the Java array. Both arrays
	 * must the same length and it must be possible to convert the 
	 * values of the Java array to the underlying type of the fixed
	 * array type definition, as returned by {@link #getType()} and using
	 * {@link TypeDefinition#createValue(Object)}.</p>
	 * 
	 * @param arrayProperty Fixed array property value to have the value
	 * of all of its elements set.
	 * @param data Java array containing elements to be used to set the
	 * values of the elements of the given fixed array property value.
	 * 
	 * @throws NullPointerException One or more of the arguments is null,
	 * or one or more of the elements of the given Java array is null.
	 * @throws IllegalPropertyValueException The fixed array property value is not
	 * defined by the fixed array type definition.
	 * @throws BadParameterException The given Java array is a different
	 * size to that defined by the fixed array type definition, as returned
	 * by {@link #getCount()}.
	 * @throws BadTypeException The Java array contains one or more
	 * objects that cannot be converted to elements of the underlying type
	 * of this fixed array type definition, as returned by {@link #getType()}.
	 * 
	 * @see #createValueFromArray(Object[])
	 * @see TypeDefinition#createValue(Object)
	 */
	public void setArray(
			PropertyValue arrayProperty,
			Object[] data) 
		throws NullPointerException,
			IllegalPropertyValueException,
			BadParameterException,
			BadTypeException;

	/**
	 * <p>Returns a list over all the property values of the
	 * given fixed array property value.</p>
	 * 
	 * @param arrayProperty Fixed array property value to create
	 * a list from.
	 * @return List of the member elements of the given fixed array
	 * property value.
	 * 
	 * @throws NullPointerException The given fixed array property 
	 * value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is
	 * not defined by this fixed array type definition.
	 * 
	 * @see #getArray(PropertyValue)
	 * @see #getElementValue(PropertyValue, int)
	 */
	public List<PropertyValue> getElements(
			PropertyValue arrayProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;
	
	/**
	 * <p>Create a cloned copy of this fixed array type definition.</p>
	 *
	 * @return Cloned copy of this fixed array type definition.
	 */
	public TypeDefinitionFixedArray clone();
}
