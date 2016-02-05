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
 * $Log: TypeDefinitionRecord.java,v $
 * Revision 1.10  2011/02/14 22:32:50  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.9  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:40:07  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:25  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.AAFString;

/**
 * <p>Specifies the definition of a property type that consists of an ordered set 
 * of fields, where each field has a name and type.</p>
 * 
 * <p>A number of built-in record type definitions are supported in the MAJ API by
 * interfaces specified in the <a href="../record/package-summary.html">record package</a>.
 * Record type definitions provide a mechanism for extending the range of data types 
 * supported in AAF. In the MAJ API, any interface that has a set and get method for
 * each of the fields of a record can be used to represent a value of that type.</p>
 * 
 * <p>As an example, consider a <em>person</em> record type consisting of a first name, 
 * surname and age, the following record fields would be defined:</p>
 * 
 * <ul>
 *  <li>field 1: "firstName" of type {@linkplain TypeDefinitionString string};</li>
 *  <li>field 2: "surname" of type {@linkplain TypeDefinitionString string};</li>
 *  <li>field 3: "age" of type {@linkplain TypeDefinitionInteger integer}, in this case a 
 *  {@linkplain tv.amwa.maj.integer.UInt16}.</li>
 * </ul>
 * 
 * <p>Using a call to {@link #createValueFromObject(Object)}, a record property value of person 
 * type can be created from any class that implements the following Java interface:</p>
 * 
 * <pre>
 * public interface Person {
 *     public String getFirstName();
 *     public void setFirstName(String firstName);
 *     public String getSurname();
 *     public void setSurname();
 *     public &#64;UInt16 short getAge();
 *     public void setAge(&#64;UInt16 short age);
 * }
 * </pre>
 * 
 * <p>The interface must be set when the class is instantiated and can be queried with the 
 * {@link #getSpecification()} method. If an implementation is avaialble, this can be queried
 * with {@link #getImplementation()}.</p>
 * 
 *
 *
 * @see tv.amwa.maj.enumeration.TypeCategory#Record
 */

public interface TypeDefinitionRecord 
	extends TypeDefinition {
	  
	/**
	 * <p>Returns the {@linkplain TypeDefinition type} of the field 
	 * of a record defined by this record type definition at the 
	 * given index into the list of fields.</p>
	 * 
	 * @param index 0-based index into the record type definition's fields.
	 * @return Field type at the given index.
	 * 
	 * @throws IndexOutOfBoundsException The index is outside the acceptable
	 * range for the list of fields of this record type definition.
	 */
	public TypeDefinition getMemberType(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the name of the field of a record defined by
	 * this record type definition at the given index into the list of fields.</p>
	 * 
	 * @param index 0-based index into the record type definition's list of fields.
	 * @return Field name at the given index.
	 * 
	 * @throws IndexOutOfBoundsException The index is outside the
	 * acceptable range for the number of fields of this record type
	 * definition.
	 */
	
	public @AAFString String getMemberName(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Creates and returns a new record property value from the 
	 * given array of {@linkplain PropertyValue property values}, matching
	 * the elements of the given array to the fields of a new record value by index
	 * into the list of fields of this record type definition.</p>
	 *   
	 * @param fieldValues Array of property values to set for the 
	 * new record property value.
	 * @return Newly created record property value from the given 
	 * array of property values.
	 * 
	 * @throws NullPointerException The given array of field values is <code>null</code>
	 * or one or more of the elements of the array is <code>null</code>.
	 * @throws IllegalArgumentException The given array is of a different
	 * size from that defined by the record type definition and returned
	 * by {@link #getCount()}.
	 * @throws BadTypeException One or more of the given property values
	 * is of a type that does not match the one defined for the field at the equivalent 
	 * index in the list of fields of this record type definition.
	 */
	public PropertyValue createValueFromValues(
			PropertyValue[] fieldValues) 
		throws NullPointerException,
			IllegalArgumentException,
			BadTypeException;

	/**
	 * <p>Creates and returns a new record property value that is initialized using
	 * the given Java object. The Java reflection API is used to discover
	 * if the fields of the record, matched by name, are readable 
	 * from the given object, either as public fields or as public <code>get...</code>
	 * methods. The type of each field in the Java object must be compatible
	 * with the type definition defined for that field in this record type definition.</p>
	 * 
	 * <p>Note that the object may define more fields than the
	 * {@linkplain #getCount() number of elements} of the record. If this is the
	 * case, the record property value will contain less information
	 * than the given object instance. Be aware that a future
	 * call to {@link #getObject(PropertyValue)} with a record
	 * property created by this method may result in a different 
	 * Java object value.</p>
	 * 
	 * @param initialData Object to use to set the values of the a new
	 * record property value. A <code>null</code> value is permitted.
	 * @return Newly created record property value, created using the
	 * the current value of the given object.
	 * 
	 * @throws BadTypeException The type of the object 
	 * cannot be converted to the interface specification
	 * defined in this record type definition.
	 * 
	 * @see java.lang.Class#getFields()
	 * @see java.lang.Class#getMethods()
	 * @see #createValueFromValues(PropertyValue[])
	 */
	public PropertyValue createValueFromObject(
			Object initialData) 
		throws BadTypeException;

	/**
	 * <p>Returns the property value of one of the fields of the 
	 * given record property value, where the value to return is determined
	 * by an index into the list of fields of this record type definition.</p>
	 * 
	 * @param recordProperty Property value containing a record that is
	 * to have a value returned.
	 * @param index 0-based index into the array of fields of this
	 * record type definition that is used to determine the field of the
	 * given record property value to return.
	 * @return Member of the given record property value selected using
	 * the given index.
	 *  
	 * @throws NullPointerException The given record property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The type of the given property value does
	 * not match this record type definition.
	 * @throws IndexOutOfBoundsException The given index is not within
	 * the acceptable range for this record type definition.
	 * 
	 * @see #getValue(PropertyValue, String)
	 */
	public PropertyValue getValue(
			PropertyValue recordProperty,
			@UInt32 int index) 
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException;

	/**
	 * <p>Returns the property value of one of the fields of the given
	 * record property value, where the value to return corresponds to
	 * the given field name.</p>
	 *
	 * @param recordProperty Property value containing a record that is
	 * to have a value returned.
	 * @param fieldName Name of the field value to retrieve.
	 * @return Property value containing the requested field value.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are 
	 * <code>null</code>.
	 * @throws IllegalPropertyValueException The type of the given property value does
	 * not match this record type definition.
	 * @throws IllegalArgumentException The given field name does not identify a
	 * field of this record type definition.
	 */
	public tv.amwa.maj.industry.PropertyValue getValue(
			tv.amwa.maj.industry.PropertyValue recordProperty,
			String fieldName)
		throws NullPointerException,
			IllegalPropertyValueException,
			IllegalArgumentException;
	
	/**
	 * <p>Returns the object representing the record value. Note that the internal value
	 * is permitted to be <code>null</code>.</p>
	 * 
	 * <p>This method is very similar to {@link PropertyValue#getValue() recordProperty.getValue()}
	 * except that it checks the type definition matches this one first.</p>
	 * 
	 * @param recordProperty Property value containing a record to extract
	 * the field values from.
	 * @return New Java object with its values set by the fields of the 
	 * given record property value.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws BadTypeException The types of the field properties of the 
	 * record type definition cannot be converted to the types of the fields
	 * of an instance of the given class.
	 * @throws IllegalPropertyValueException The given property
	 * value is not defined by a compatible record type definition.
	 * 
	 * @see #createValueFromObject(Object)
	 * @see #setObject(PropertyValue, Object)
	 * @see tv.amwa.maj.industry.PropertyValue#getValue()
	 */
	public Object getObject(
			PropertyValue recordProperty) 
		throws NullPointerException,
			BadTypeException,
			IllegalPropertyValueException;

	/**
	 * <p>Sets the value of a field of the given record property 
	 * value, determined by index into the list of fields of its record type definition. 
	 * The field property must be of a type that is compatible with the type at the given 
	 * index, as returned by {@link #getMemberType(int)} for the same index.</p>  
	 * 
	 * @param recordProperty Property value containing a record that
	 * is to have one of its fields set.
	 * @param index 0-based index into the record type definition defining the
	 * given record property value. This determines the name and required
	 * type of the record field.
	 * @param fieldProperty Value to set as a field of the given record
	 * property value.
	 * 
	 * @throws NullPointerException One or both of the property arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The type of the given property definition
	 * does not match this record type definition.
	 * @throws IndexOutOfBoundsException The given index is outside the 
	 * range of the list of fields of this record type definition.
	 * @throws BadTypeException The type of the given field property value
	 * does not match that defined at the given index in the record
	 * type definition.
	 * 
	 * @see #getValue(PropertyValue, int)
	 * @see #setValue(PropertyValue, String, PropertyValue)
	 */
	public void setValue(
			PropertyValue recordProperty,
			@UInt32 int index,
			PropertyValue fieldProperty) 
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException,
			BadTypeException;
	
	/**
	 * <p>Sets the value of a field of the given record property 
	 * value, determined by field name, to be the given field 
	 * property value. The field property must be 
	 * of a type that is compatible with the type of the field
	 * specified in the list of fields of this property.</p>
	 *
	 * @param recordProperty Property value containing a record that
	 * is to have one of its fields set.
	 * @param fieldName Name of the field to set the value for within the given 
	 * record property value.
	 * @param fieldProperty Value to set the field to.
	 * 
	 * @throws NullPointerException One or more or the arguments is/are
	 * <code>null</code> and all are required.
	 * @throws IllegalPropertyValueException The type of the given record property definition
	 * does not match this record type definition.
	 * @throws IllegalArgumentException The field name is not known as a
	 * field of this record type definition.
	 * @throws BadTypeException The type of the given field property value
	 * does not match that defined for the field with given name in this record 
	 * type definition.
	 * 
	 * @see #getValue(PropertyValue, String)
	 * @see #setValue(PropertyValue, int, PropertyValue)
	 */
	public void setValue(
			tv.amwa.maj.industry.PropertyValue recordProperty,
			String fieldName,
			tv.amwa.maj.industry.PropertyValue fieldProperty)
		throws NullPointerException,
			IllegalPropertyValueException,
			IllegalArgumentException,
			BadTypeException;
	
	/**
	 * <p>Sets the value of the given record property value using
	 * the given Java object. The Java reflection API is used to discover
	 * if the fields of the record are readable from the
	 * given object, either as public fields or public <code>get...</code>
	 * methods. The types of the fields of the given object must be compatible
	 * with the type definitions of the matching fields in this record type
	 * definition.</p>
	 * 
	 * <p>Note that the object may define more fields than the
	 * {@linkplain #getCount() number of elements} of the record. If this is the
	 * case, the record property value will contain less information
	 * than the given object. Be aware that a subsequent
	 * call to {@link #getObject(PropertyValue)} with a record
	 * property value created by this method may result in a different 
	 * object value.</p>
	 * 
	 * @param recordProperty Property value containing a record that is
	 * to have its values set.
	 * @param data A Java object that is used
	 * to set the fields of the given record property value.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given object does not provide
	 * values for one or more of the record's values, or the given property
	 * value is not defined by a compatible record type definition.
	 * @throws BadTypeException One or more of the field types of the
	 * given object do not match or cannot be converted to the type of 
	 * their equivalent property in this record type definition.
	 * 
	 * @see #createValueFromObject(Object)
	 * @see #getObject(PropertyValue)
	 */
	public void setObject(
			PropertyValue recordProperty,
			Object data) 
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException;

	/**
	 * <p>Returns the number of fields stored in a record defined
	 * by this record type definition.</p>
	 * 
	 * @return Number of fields stored in a record defined
	 * by this record type definition.
	 */
	public @UInt32 int getCount();
	
	/**
	 * <p>Returns the interface that specifies acceptable implementations
	 * of record values of this type.</p>
	 * 
	 * @return Interface specifying Java implementations of values of
	 * this type.
	 * 
	 * @see #getImplementation()
	 */
	public Class<?> getSpecification();
	
	/**
	 * <p>Returns an implementation of the specified interface if available;
	 * otherwise <code>null</code>.</p>
	 * 
	 * @return Implementation of the specified interface.
	 * 
	 * @see #getSpecification()
	 */
	public Class<?> getImplementation();
	
	/**
	 * <p>Create a cloned copy of this record type definition.</p>
	 *
	 * @return Cloned copy of this record type definition.
	 */
	public TypeDefinitionRecord clone();
}
