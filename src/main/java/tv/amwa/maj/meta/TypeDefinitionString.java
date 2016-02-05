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

// TODO tidy up the method names in this interface - elements, buffers, strings etc.

/*
 * $Log: TypeDefinitionString.java,v $
 * Revision 1.11  2011/02/14 22:32:50  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.10  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.9  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:40:07  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:50  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import java.nio.Buffer;
import java.nio.charset.UnsupportedCharsetException;

import tv.amwa.maj.exception.BadSizeException;
import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.UInt32;

/** 
 * <p>Specifies the definition of a property type that consists,
 * effectively, of an array of the underlying character or integer type. In the
 * MAJ API, all strings are represented as {@linkplain java.lang.String Java strings}.</p>
 * 
 * <p>String type definitions have an underlying element type that can be
 * found be calling {@link #getElementType()}. The element type can be either
 * {@linkplain TypeDefinitionCharacter a character type} or {@linkplain TypeDefinitionInteger
 * an integer type}. The methods of this class allow the manipulation of string property 
 * values in a way that hides the underlying value type.</p> 
 * 
 * <p>For any conversion between arrays of integer
 * values and Java strings or vice versa, the character set returned by 
 * {@link #getCharacterSet()} is used. This character set can also be set using
 * to a user-defined value with {@link #setCharacterSet(String)} and the defined 
 * byte order of any buffer is respected. The default value
 * for this property is "<code>UTF-16</code>", which is the same as the default
 * character set for the Java platform. </p>
 * 
 * <p>Due to the object-oriented representation of arrays in Java including a defined
 * length, sequences of data are not expected to be terminated with null bytes.</p>
 * 
 * <p>Property values containing <code>null</code> as the value for a string are
 * acceptable.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.enumeration.TypeCategory#String
 * @see java.lang.String
 * @see TypeDefinitionCharacter
 * @see tv.amwa.maj.industry.TypeDefinitions#UTF16String
 * @see tv.amwa.maj.misctype.AAFString
 */
public interface TypeDefinitionString 
	extends TypeDefinition {

	/**
	 * <p>Returns the underlying AAF type used to represent characters
	 * of a string of this string type definition.</p>
	 * 
	 * @return Underlying type used to represent characters
	 * of a string of this string type definition.
	 */
	public TypeDefinition getElementType();

	/**
	 * <p>Returns the length of the string contained in the given 
	 * string property value, measured in the number of characters.</p>
	 * 
	 * @param stringProperty Property value containing a string to determine
	 * the length of.
	 * @return Number of characters in the given string.
	 * 
	 * @throws NullPointerException The given string property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given string property value type does not match
	 * this string type definition.
	 * 
	 * @see java.lang.String#length()
	 */
	public @UInt32 int getCount(
			PropertyValue stringProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Create a new string type property value consistent with
	 * this type definition from the given Java string. Conversion to 
	 * the underlying character type takes place according to the 
	 * character set returned by {@link #getCharacterSet()}. Setting
	 * a <code>null</code> value will result in the return of a property
	 * value with its internal string representation set to 
	 * <code>null</code>.</p>
	 * 
	 * @param initialData Initial string data to create a new string
	 * property value.
	 * @return Newly created string property value from the given
	 * Java string.
	 * 
	 * @see #createValueFromString(Buffer)
	 */
	public PropertyValue createValueFromString(
			String initialData);
	
	/** 
	 * <p>Create a new string type property value consistent with
	 * this type definition from the given data buffer. Any character
	 * set conversion required takes place using the 
	 * character set returned by {@link #getCharacterSet()}. Setting
	 * a <code>null</code> value will result in the return of a property
	 * value with its internal string representation set to 
	 * <code>null</code>.</p>
	 * 
	 * <p>The buffer of data must be one of the following types:</p>
	 * 
	 * <ul>
	 *  <li>{@link java.nio.ByteBuffer}</li>
	 *  <li>{@link java.nio.ShortBuffer}</li>
	 *  <li>{@link java.nio.IntBuffer}</li>
	 *  <li>{@link java.nio.LongBuffer}</li>
	 *  <li>{@link java.nio.CharBuffer}</li>
	 * </ul>
	 * 
	 * <p>Any other buffer types will result in an {@link IllegalArgumentException}.</p>
	 *
	 * @param initialData Initial string data to use to create a new string
	 * property value.
	 * @return Newly created string property value from the given buffer.
	 * 
	 * @throws IllegalArgumentException The given buffer must be of an appropriate type for 
	 * conversion to the underlying element type for this string type definition.
	 * 
	 * @see #createValueFromString(Buffer)
	 */
	public PropertyValue createValueFromString(
			Buffer initialData)
		throws IllegalArgumentException;
	
	/**
	 * <p>Set the value of the string contained in the given 
	 * string property value to the given Java
	 * string value. Any character set conversion required takes place using the 
	 * character set returned by {@link #getCharacterSet()}. Setting
	 * a <code>null</code> value will result in the return of a property
	 * value with its internal string representation set to 
	 * <code>null</code>.</p>
	 * 
	 * @param stringProperty Property value to have its string value set.
	 * @param data Value to set for the string type property value, represented 
	 * as a Java String.
	 * 
	 * @throws NullPointerException The given string property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given string property type does not match
	 * this string type definition.
	 * 
	 * @see #getString(PropertyValue)
	 * @see #setString(PropertyValue, Buffer)
	 */
	public void setString(
			PropertyValue stringProperty,
			String data) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Set the value of the string contained in the given 
	 * string property value to the given data buffer. 
	 * Any character set conversion required takes place using the 
	 * character set returned by {@link #getCharacterSet()}. Setting
	 * a <code>null</code> value will result in the return of a property
	 * value with its internal string representation set to 
	 * <code>null</code>.</p>
	 * 
	 * <p>The buffer of data must be one of the following types:</p>
	 * 
	 * <ul>
	 *  <li>{@link java.nio.ByteBuffer}</li>
	 *  <li>{@link java.nio.ShortBuffer}</li>
	 *  <li>{@link java.nio.IntBuffer}</li>
	 *  <li>{@link java.nio.LongBuffer}</li>
	 *  <li>{@link java.nio.CharBuffer}</li>
	 * </ul>
	 * 
	 * <p>Any other buffer types will result in an {@link IllegalArgumentException}.</p>
	 *
	 * @param stringProperty Property value to have its string value set.
	 * @param data Value to set for the string type property value, represented
	 * as a data buffer.
	 * 
	 * @throws NullPointerException The given string property is <code>null</code>.
	 * @throws IllegalPropertyValueException The given string property type does not match
	 * this string type definition.
	 * @throws IllegalArgumentException The given buffer must be of an appropriate type for 
	 * conversion to the underlying element type for this string type definition.
	 * 
	 * @see #getElements(PropertyValue)
	 * @see #setString(PropertyValue, String)
	 */
	public void setString(
			PropertyValue stringProperty,
			Buffer data)
		throws NullPointerException, 
			IllegalPropertyValueException, 
			IllegalArgumentException;
	
	/**
	 * <p>Append to the string value of the given property value using
	 * the array of property value elements. Each
	 * element of the given array must match the type definition returned
	 * by {@link #getElementType()}.</p>
	 * 
	 * @param stringProperty Property value containing a string to be
	 * extended.
	 * @param elements Array of property value elements to use to extend
	 * the given string.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>, or
	 * one or more of the values of the given array is <code>null</code>.
	 * @throws BadTypeException One or more of the elements of the given array
	 * of property values is of an incompatible type. 
	 * @throws IllegalPropertyValueException The given string property value is not
	 * of this string type definition.
	 * @throws BadSizeException The number of bytes per integer of the underlying element 
	 * type of this string type definition is not supported by this implementation.
	 * 
	 * @see #appendElements(PropertyValue, Buffer)
	 * @see #appendString(PropertyValue, String)
	 */
	public void appendElements(
			PropertyValue stringProperty,
			PropertyValue[] elements) 
		throws NullPointerException,
			BadTypeException, 
			IllegalPropertyValueException, 
			BadSizeException;
	
	
	/**
	 * <p>Append to the string value of the given property value using
	 * a data buffer. Any character set conversion required takes place using the 
	 * character set returned by {@link #getCharacterSet()}. An attempt to
	 * pass in a <code>null</code> buffer will result in no change to the property
	 * value and no error.</p>
	 * 
	 * <p>The buffer of data must be one of the following types:</p>
	 * 
	 * <ul>
	 *  <li>{@link java.nio.ByteBuffer}</li>
	 *  <li>{@link java.nio.ShortBuffer}</li>
	 *  <li>{@link java.nio.IntBuffer}</li>
	 *  <li>{@link java.nio.LongBuffer}</li>
	 *  <li>{@link java.nio.CharBuffer}</li>
	 * </ul>
	 * 
	 * <p>Any other buffer types will result in an {@link IllegalArgumentException}.</p>
	 * 
	 * <p>This method has the same behaviour as {@link #appendString(PropertyValue, Buffer)}.</p>
	 * 
	 * @param stringProperty Property value containing a string value
	 * to append the given elements to.
	 * @param elements Buffer containing values to be appended to to
	 * the given property value.
	 * 
	 * @throws NullPointerException The given string property argument is <code>null</code>.
	 * @throws IllegalArgumentException The given buffer must be of an appropriate type 
	 * for conversion to the underlying element type for this string type definition.
	 * @throws IllegalPropertyValueException The given string property type does not match this
	 * type definition.
	 * 
	 * @see #appendElements(PropertyValue, PropertyValue[])
	 * @see #appendString(PropertyValue, String)
	 */
	public void appendElements(
			PropertyValue stringProperty,
			Buffer elements) 
		throws NullPointerException, 
			IllegalArgumentException, 
			IllegalPropertyValueException;
	
	/**
	 * <p>Appends the given Java String to the string of the given
	 * property value. Any character set conversion required takes place using the 
	 * character set returned by {@link #getCharacterSet()}. An attempt to
	 * pass a <code>null</code> string will result in no change to the property
	 * value and no error.</p>
	 * 
	 * @param stringProperty Property value containing the string to extend.
	 * @param extension String to append to the end of the given property
	 * value.
	 * 
	 * @throws NullPointerException The given string property argument is <code>null</code>.
	 * @throws IllegalPropertyValueException The given string property value type does not match
	 * this string type definition.
	 * 
	 * @see #appendString(PropertyValue, Buffer)
	 * @see #appendElements(PropertyValue, PropertyValue[])
	 */
	public void appendString(
			PropertyValue stringProperty,
			String extension) 
		throws NullPointerException, 
			IllegalPropertyValueException;
	
	/**
	 * <p>Appends the given data buffer to the string of the given
	 * property value. Any character set conversion required takes place using the 
	 * character set returned by {@link #getCharacterSet()}. An attempt to
	 * pass a <code>null</code> buffer will result in no change to the property
	 * value and no error.</p>
	 * 
	 * <p>The buffer of data must be one of the following types:</p>
	 * 
	 * <ul>
	 *  <li>{@link java.nio.ByteBuffer}</li>
	 *  <li>{@link java.nio.ShortBuffer}</li>
	 *  <li>{@link java.nio.IntBuffer}</li>
	 *  <li>{@link java.nio.LongBuffer}</li>
	 *  <li>{@link java.nio.CharBuffer}</li>
	 * </ul>
	 * 
	 * <p>Any other buffer types will result in an {@link IllegalArgumentException}.</p>
	 * 
	 * <p>This method has the same behaviour as {@link #appendElements(PropertyValue, Buffer)}.</p>
	 *
	 * @param stringProperty Property value containing the string to extend.
	 * @param extension Buffer of string data to use to extend the given property value.
	 * 
	 * @throws NullPointerException The given string property value is <code>null</code>.
	 * @throws IllegalArgumentException The given buffer must be of an appropriate type for 
	 * conversion to the underlying element type for this string type definition.
	 * @throws IllegalPropertyValueException The given string property value type does not match
	 * this string type definition.
	 * 
	 * @see #appendElements(PropertyValue, PropertyValue[])
	 * @see #appendString(PropertyValue, String)
	 */
	public void appendString(
			PropertyValue stringProperty,
			Buffer extension)
		throws NullPointerException, 
			IllegalArgumentException, 
			IllegalPropertyValueException;

	/**
	 * <p>Returns the value of the given string property as a data buffer
	 * consistent with the element type of this string type definition. 
	 * Any character set conversion required takes place using the 
	 * character set returned by {@link #getCharacterSet()}. If the internal
	 * representation of the string property value is <code>null</code> then
	 * this method will return <code>null</code>.</p>
	 * 
	 * <p>Note that a <code>null</code> character will not be inserted as the last
	 * byte of the buffer.</p>
	 *  
	 * @param stringProperty Property value containing the string to convert.
	 * @return Data buffer representation of the given value.
	 * 
	 * @throws NullPointerException The given string property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given string property type does not match
	 * this string property value.
	 * @throws BadSizeException The number of bytes per integer of the underlying element type of this 
	 * string type definition is not supported by this implementation.
	 * 
	 * @see #setString(PropertyValue, Buffer)
	 * @see #getString(PropertyValue)
	 */
	public Buffer getElements(
			PropertyValue stringProperty) 
		throws NullPointerException, 
			IllegalPropertyValueException, 
			BadSizeException;
	
	/**
	 * <p>Returns the value of the given string property as an array of
	 * property values of a compatible type to the
	 * string type definition, as returned by {@link #getElementType()}.</p>
	 *  
	 * @param stringProperty Property value containing the string to convert.
	 * @return Java String representation of the given value.
	 * 
	 * @throws NullPointerException The given string property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The type of the given string property value does not match this
	 * string type definition.
	 * @throws BadSizeException The number of bytes per integer of the underlying element type of this 
	 * string type definition is not supported by this implementation.
	 * 
	 * @see #appendElements(PropertyValue, PropertyValue[])
	 */
	public PropertyValue[] getPropertyValues(
			PropertyValue stringProperty) 
		throws NullPointerException, 
			IllegalPropertyValueException, 
			BadSizeException;

	/**
	 * <p>Returns the value of the given string property as a Java string.</p>
	 *  
	 * @param stringProperty Property value containing the string to convert.
	 * @return Java String representation of the given value, which could be a
	 * <code>null</code> string.
	 * 
	 * @throws NullPointerException The given string property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given string property is not of 
	 * this string type definition.
	 */
	public String getString(
			PropertyValue stringProperty) 
		throws NullPointerException, 
			IllegalPropertyValueException;
	
	/**
	 * <p>Returns the character set that will be used for conversion between a Java
	 * string and this string type definition. If a character
	 * set has not been provided by a user then the value returned will be the default 
	 * Java character set of "<code>UTF-16</code>".</p>
	 *
	 * @return Character set used for conversion between this string type and Java strings.
	 * 
	 * @see java.nio.charset.Charset
	 */
	public String getCharacterSet();
	
	/**
	 * <p>Set the character set that will be used for conversion between a Java string
	 * and this string type definition. For information on valid character set names, 
	 * see the {@linkplain java.nio.charset.Charset relevant Java API documentation}.</p>
	 *
	 * @param charSet Character set used for conversion between this string type and Java strings.
	 * 
	 * @throws NullPointerException The given character set name is <code>null</code>.
	 * @throws UnsupportedCharsetException The named character set is not supported in the
	 * underlying implementation of Java.
	 * 
	 * @see java.nio.charset.Charset
	 */
	public void setCharacterSet(
			String charSet)
		throws NullPointerException, 
			UnsupportedCharsetException;
	
	/**
	 * <p>Create a cloned copy of this string type definition.</p>
	 *
	 * @return Cloned copy of this string type definition.
	 */
	public TypeDefinitionString clone();
}
