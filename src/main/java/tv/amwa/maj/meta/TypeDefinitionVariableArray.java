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
 * $Log: TypeDefinitionVariableArray.java,v $
 * Revision 1.9  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:40:23  vizigoth
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
 * Revision 1.2  2007/12/04 09:40:06  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:28  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import java.util.List;

import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.Component;
import tv.amwa.maj.model.ControlPoint;
import tv.amwa.maj.model.DefinitionObject;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.model.OperationDefinition;



/**
 * <p>Specifies the definition of a property type that has a varying number of values 
 * of the underlying type. The order of the values is meaningful.</p>
 * 
 * <p>Some examples of the underlying type are as follows:</p>
 * 
 * <ul>
 *  <li>Another type definition, such as:
 *   <ul>
 *    <li>{@link tv.amwa.maj.integer.Int64} values with array type 
 *    {@link tv.amwa.maj.industry.TypeDefinitions#Int64Array Int64Array};</li>
 *    <li>{@link tv.amwa.maj.misctype.AAFString} values with array type
 *    {@link tv.amwa.maj.industry.TypeDefinitions#UTF16StringArray StringArray};</li>
 *    <li>{@link tv.amwa.maj.enumeration.ChannelStatusModeType} enumeration values with 
 *    array type {@link tv.amwa.maj.industry.TypeDefinitions#ChannelStatusModeArray 
 *    ChannelStatusModeArray};
 *    <li>{@link tv.amwa.maj.record.AUID} record values with array type
 *    {@link tv.amwa.maj.industry.TypeDefinitions#AUIDArray AUIDArray}.</li>
 *   </ul></li>
 *  <li>A {@linkplain TypeDefinitionStrongObjectReference strong object reference}
 *  to a subclass of {@linkplain InterchangeObject interchange object}, with the exception
 *  of {@linkplain DefinitionObject definition objects}, such as:
 *   <ul>
 *    <li>References to {@linkplain Component components} with array type
 *    {@link tv.amwa.maj.industry.TypeDefinitions#ComponentStrongReferenceVector 
 *    ComponentStrongReferenceVector};</li>
 *    <li>References to {@linkplain ControlPoint control points} with array type
 *    {@link tv.amwa.maj.industry.TypeDefinitions#ControlPointStrongReferenceVector
 *    ControlPointStrongReferenceVector}.</li>
 *   </ul>
 *   Arrays of this type are known as <em>strong reference vectors</em>. The control point example
 *   above is called "StrongReferenceVector of ControlPoint" in the AAF object specification and
 *   may also be represented as "StrongReferenceVector&lt;ControlPoint&gt;".</li>
 *  <li>A {@linkplain TypeDefinitionWeakObjectReference weak object reference} to
 *  a subclass of {@linkplain DefinitionObject definition object} or {@linkplain MetaDefinition
 *  meta definition}, such as:
 *   <ul>
 *    <li>References to {@linkplain TypeDefinition type definitions} with array type
 *    {@link tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionWeakReferenceVector
 *    TypeDefinitionWeakObjectReference};</li>
 *    <li>References to {@linkplain OperationDefinition operation definitions} with array
 *    type {@link tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionWeakReferenceVector
 *    OperationDefinitionWeakReference}.</li>
 *   </ul>
 *   Arrays of this type are known as <em>weak reference vectors</em>. The operation definition example
 *   above is called "WeakReferenceVector of OperationDefinition" in the AAF object specification and
 *   may also be represented as "WeakReferenceVector&lt;OperationDefinition&gt;".</li>
 * </ul>
 * 
 * <p>Type names as shown in the examples above can be used to retrieve variable array type
 * definitions with a call to {@link tv.amwa.maj.industry.Warehouse#lookForType(String)}.</p>
 * 
 *
 *
 * @see tv.amwa.maj.enumeration.TypeCategory#VariableArray
 * @see TypeDefinitionFixedArray
 * @see TypeDefinitionSet
 */

public interface TypeDefinitionVariableArray 
	extends TypeDefinition {
	
	/**
	 * <p>Returns the type of elements of this variable size 
	 * array type definition.</p>
	 * 
	 * @return Type of elements of this variable size array type definition.
	 */
	public TypeDefinition getType();

	/**
	 * <p>Returns the number of elements in the given array, which is of a type defined
	 * by this variable size type definition. This method will 
	 * return&nbsp;0 if the array is <code>null</code> or empty.</p>
	 * 
	 * @param arrayProperty Property value containing the array to find the
	 * size of.
	 * @return Number of elements in the given array.
	 * 
	 * @throws NullPointerException The given array property is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 */
	public @UInt32 int getCount(
			PropertyValue arrayProperty)
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Append the given element to the end of the given array, which is
	 * of a type defined by this variable array type definition.</p>
	 * 
	 * @param arrayProperty Array to extend with the given element.
	 * @param element Element to use to extend the given array.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 * @throws BadTypeException The given element is not of a type that is
	 * compatible with this type of array.
	 * 
	 * @see #prependElement(PropertyValue, PropertyValue)
	 * @see #insertElement(PropertyValue, int, PropertyValue)
	 */
	public void appendElement(
			PropertyValue arrayProperty,
			PropertyValue element) 
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException;

	/**
	 * <p>Creates a property value containing an empty variable size
	 * array, which is an array with no elements in it yet.</p>
	 * 
	 * @return Property value containing an empty variable size
	 * array.
	 * 
	 * @see #createValueFromArray(Object[])
	 * @see #createValueFromList(List)
	 * @see #createValueFromValues(PropertyValue[])
	 */
	public PropertyValue createEmptyValue();

	/**
	 * <p>Creates a property value containing a
	 * variable size array from the given array of property values.</p>
	 * 
	 * @param elementValues Array of property values to use to create a
	 * variable size array.
	 * @return Property value containing the newly created variable size
	 * array.
	 * 
	 * @throws NullPointerException The given array of element values is <code>null</code> 
	 * or one or more of its elements is/are <code>null</code>.
	 * @throws BadTypeException One of more of the elements of the given
	 * array of property values has a type that is not compatible with
	 * this array type.
	 * 
	 * @see #createEmptyValue()
	 * @see #createValueFromList(List)
	 * @see #createValueFromArray(Object[])
	 */
	public PropertyValue createValueFromValues(
			PropertyValue[] elementValues) 
		throws NullPointerException,
			BadTypeException;
	
	/**
	 * <p>Create a property value containing a variable 
	 * size array from the given array of Java objects. Passing a <code>null</code>
	 * value to this method will create a variable size array property of this
	 * type containing a <code>null</code> value, which may be used to indicate
	 * that a property is not present.</p>
	 * 
	 * @param initialData Array of Java objects to use as the initial data for
	 * the variable size array.
	 * @return Property value containing a variable size array created from
	 * the given array of Java objects.
	 * 
	 * @throws ClassCastException It is not possible to cast one or more of the
	 * given elements of the Java array to the underlying element type of this 
	 * type definition.
	 * 
	 * @see #createEmptyValue()
	 * @see #createValueFromList(List)
	 * @see #createValueFromValues(PropertyValue[])
	 * @see #setArray(PropertyValue, Object[])
	 */
	public PropertyValue createValueFromArray(
			Object[] initialData) 
		throws ClassCastException;
	
	/**
	 * <p>Creates a property value containing a variable
	 * size array from the given list of Java objects. Passing a <code>null</code>
	 * value to this method will create a variable size array property of this
	 * type containing a <code>null</code> value, which may be used to indicate
	 * that a property is not present.</p>
	 *
	 * @param initialData List of Java objects to use as the initial data for
	 * the variable size array.
	 * @return  Property value containing a variable size array created from
	 * the given array of Java objects.
	 * 
	 * @throws ClassCastException One or more of the elements of the given list does 
	 * not match the underlying element type of this variable size array type definition.
	 * 
	 * @see #createValueFromArray(Object[])
	 * @see #setArray(PropertyValue, List)
	 */
	public PropertyValue createValueFromList(
			List<Object> initialData)
		throws ClassCastException;
	
	/**
	 * <p>Returns the property value from the given array at the given index.</p>
	 * 
	 * @param arrayProperty Property containing an array to return a value
	 * from.
	 * @param index Index into the given array for the value to return.
	 * @return Property value from the given array at the given index. 
	 * 
	 * @throws NullPointerException The given array property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 * @throws IndexOutOfBoundsException The given index is outside the
	 * acceptable range for the current size of the given array.
	 * 
	 * @see #getArray(PropertyValue)
	 * @see PropertyValue#getValue()
	 */
	public PropertyValue getElementValue(
			PropertyValue arrayProperty,
			@UInt32 int index) 
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException;
												
	/**
	 * <p>Returns an array of Java objects containing the values of the given 
	 * array.</p>
	 * 
	 * @param arrayProperty Property containing the array to convert to
	 * an array of Java objects.
	 * @return Array of Java objects created from the values of the given
	 * array.
	 * 
	 * @throws NullPointerException The given array property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 * 
	 * @see #getElements(PropertyValue)
	 * @see #getElementValue(PropertyValue, int)
	 */
	public Object[] getArray(
			PropertyValue arrayProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	// TODO should you be able to set an element to null?
	
	/**
	 * <p>Sets an element of the given array at the given
	 * index with the given value, replacing the value that is currently
	 * stored.</p>
	 * 
	 * @param arrayProperty Property value containing the array to be modified.
	 * @param index Index into the given array of the element to be replaced
	 * by the given value.
	 * @param element Value to set at the given index in the given array.
	 * 
	 * @throws NullPointerException The given array property value and/or the given element
	 * is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 * @throws IndexOutOfBoundsException The given index is outside the 
	 * acceptable range for the current size of the given array.
	 * @throws BadTypeException The given element type is not compatible with the
	 * element type of this array.
	 * 
	 * @see #insertElement(PropertyValue, int, PropertyValue)
	 */
	public void setElementAt(
			PropertyValue arrayProperty,
			@UInt32 int index,
			PropertyValue element) 
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException,
			BadTypeException;

	/**
	 * <p>Sets the elements of a given array from the given array
	 * of Java objects. The {@linkplain TypeDefinition type definition}
	 * returned by {@link #getType()} must be able to transform each
	 * of the objects in the given Java array into the appropriate
	 * type, otherwise a {@link IllegalPropertyValueException} will be thrown.</p>
	 * 
	 * @param arrayProperty Property containing the array to have its
	 * data set.
	 * @param data List of Java objects to set as the elements of the
	 * given array.
	 * 
	 * @throws NullPointerException The given array property argument is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 * @throws ClassCastException It is not possible to cast one or
	 * more of the given Java objects to a type compatible with this type
	 * definition.
	 * 
	 * @see TypeDefinition#createValue(Object)
	 * @see #setArray(PropertyValue, List)
	 */
	public void setArray(
			PropertyValue arrayProperty,
			Object[] data) 
		throws NullPointerException,
			IllegalPropertyValueException,
			ClassCastException;

	/**
	 * <p>Sets the elements of a given array from the given list
	 * of Java objects. The {@linkplain TypeDefinition type definition}
	 * returned by {@link #getType()} must be able to transform each
	 * of the objects in the given Java array into the appropriate
	 * type, otherwise a {@link IllegalPropertyValueException} will be thrown.</p>
	 * 
	 * @param arrayProperty Property containing the array to have its
	 * data set.
	 * @param data Array of Java objects to set as the elements of the
	 * given array.
	 * 
	 * @throws NullPointerException The given array property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 * @throws ClassCastException It is not possible to cast one or
	 * more of the given Java objects to a type compatible with this type
	 * definition.
	 * 
	 * @see TypeDefinition#createValue(Object)
	 * @see #setArray(PropertyValue, Object[])
	 */
	public void setArray(
			PropertyValue arrayProperty,
			List<Object> data) 
		throws NullPointerException,
			IllegalPropertyValueException,
			ClassCastException;
	/**
	 * <p>Returns a copy of the internal list of all the elements in the given array.</p>
	 * 
	 * @param arrayProperty Property containing the array to create an iterator
	 * for.
	 * @return List containing the elements of the given variable size array.
	 * 
	 * @throws NullPointerException The given array property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 * 
	 * @see #getArray(PropertyValue)
	 */
	public List<PropertyValue> getElements(
			PropertyValue arrayProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Prepends a given element to the beginning of the given 
	 * array.</p>
	 * 
	 * @param arrayProperty Property value of the array to be extended.
	 * @param element Element to add to the beginning of the given array.
	 * 
	 * @throws NullPointerException The given array property value and/or the given element
	 * value is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 * @throws BadTypeException The given element is not of the correct type
	 * to be inserted into this array.
	 * 
	 * @see #appendElement(PropertyValue, PropertyValue)
	 * @see #insertElement(PropertyValue, int, PropertyValue)
	 */
	public void prependElement(
			PropertyValue arrayProperty,
			PropertyValue element) 
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException;

	/**
	 * <p>Removes an element from the given array at the given zero-based
	 * index. On successful element removal, the indices elements beyond the given index
	 * will reduced by one.</p>
	 * 
	 * @param arrayProperty Property value of the array to be modified.
	 * @param index 0-based index of the element to be removed from the given array.
	 * 
	 * @throws NullPointerException The given array property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 * @throws IndexOutOfBoundsException The given index is outside the 
	 * acceptable range for the current size of the given array.
	 */
	public void removeElement(
			PropertyValue arrayProperty,
			@UInt32 int index) 
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException;

	/**
	 * <p>Inserts the value of the given element into the given array property.
	 * The index is zero-based and must be less than the value returned by 
	 * {@link #getCount(PropertyValue)}.  The property value must be of the same type as 
	 * returned by {@link #getType()}. Elements at the given index and above will have their
	 * indices increased by one to accommodate the new element.</p>
	 * 
	 * @param arrayProperty Property value corresponding to an array.
	 * @param index 0-based index at which to insert the given element into the given
	 * array. 
	 * @param element Element to insert into the given array at the given index.
	 * 
	 * @throws NullPointerException The given array property value and/or the given element
	 * is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value type does not match
	 * this variable size array property definition.
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable 
	 * range for the current size of the given array.
	 * @throws BadTypeException The given element is of the wrong type to
	 * be inserted into the array.
	 * 
	 * @see #appendElement(PropertyValue, PropertyValue)
	 * @see #prependElement(PropertyValue, PropertyValue)
	 * @see #setElementAt(PropertyValue, int, PropertyValue)
	 */
	public void insertElement(
			PropertyValue arrayProperty,
			@UInt32 int index,
			PropertyValue element) 
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException,
			BadTypeException;
	
	/**
	 * <p>Create a cloned copy of this variable array type definition.</p>
	 *
	 * @return Cloned copy of this variable array type definition.
	 */
	public TypeDefinitionVariableArray clone();
}
