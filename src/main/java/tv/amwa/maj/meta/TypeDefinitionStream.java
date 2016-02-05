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
 * $Log: TypeDefinitionStream.java,v $
 * Revision 1.10  2011/02/14 22:32:50  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.9  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2010/11/08 16:02:45  vizigoth
 * Version with IO exceptions on stream manipulation methods.
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
 * Revision 1.1  2007/11/13 22:08:56  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import java.io.IOException;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.DataSizeException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.misctype.DataBuffer;


/**
 * <p>Specifies the definition of a property type that is stored in a stream and has 
 * a value that consists of a varying number of bytes. The order of the bytes is 
 * meaningful.</p>
 * 
 * <p>Note that all reads and writes advance the current position of a stream.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.enumeration.TypeCategory#Stream
 * @see tv.amwa.maj.misctype.DataBuffer
 * @see tv.amwa.maj.model.EssenceData#getEssenceStream()
 * @see tv.amwa.maj.industry.TypeDefinitions#Stream
 * @see tv.amwa.maj.industry.Stream
 */

public interface TypeDefinitionStream 
	extends TypeDefinition {

	/**
	 * <p>Returns the number of bytes in the given stream property
	 * value, or&nbsp;-1 if the stream is <code>null</code>.</p>
	 * 
	 * @param streamProperty Stream property value containing the
	 * stream to determine the length of.
	 * @return Number of bytes in the stream of the given stream
	 * property value.
	 * 
	 * @throws NullPointerException The given stream property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not
	 * defined by this stream type definition.
	 * @throws IOException Cannot access the underlying stream to determine the size.
	 */
	public @Int64 long getSize(
			PropertyValue streamProperty) 
		throws NullPointerException,
			IllegalPropertyValueException, 
			IOException;

	/**
	 * <p>Sets the number of bytes contained in the given stream
	 * property value to the given new size. If the new size is smaller
	 * than the current size, as returned by {@link #getSize(PropertyValue)},
	 * then the data will be truncated. If the size is larger, the beginning
	 * section of the stream value will be the same as the existing stream.</p>
	 * 
	 * @param streamProperty Stream property value that is to have
	 * a new size.
	 * @param newSize New size, measured in bytes, for the given
	 * stream property value.
	 * 
	 * @throws NullPointerException The given stream property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not
	 * defined by this stream type definition.
	 */
	public void setSize(
			PropertyValue streamProperty,
			@Int64 long newSize) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Returns the current position within the given stream property
	 * value.</p>
	 * 
	 * @param streamProperty Stream property value to find the current position of.
	 * @return Current position in the given stream property value.
	 * 
	 * @throws NullPointerException The given stream property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 * @throws IOException Error occurred when trying to access the underlying stream.
	 */
	public @Int64 long getPosition(
			PropertyValue streamProperty) 
		throws NullPointerException,
			IllegalPropertyValueException, 
			IOException;

	/**
	 * <p>Sets the position of the given stream property value to 
	 * that of the given value.</p>
	 * 
	 * @param streamProperty Stream property value to have its 
	 * position set.
	 * @param newPosition New position to set for the given stream
	 * property value.
	 * 
	 * @throws NullPointerException The given stream property value is <code>null</code>.
	 * @throws IllegalArgumentException The given position is outside 
	 * the acceptable range for the given stream property.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 * @throws IOException Error occurred when trying to access the underlying stream.
	 */
	public void setPosition(
			PropertyValue streamProperty,
			@Int64 long newPosition) 
		throws NullPointerException,
			IllegalPropertyValueException, 
			IllegalArgumentException, 
			IOException;
	
	/**
	 * <p>Copies the data from the current position in the given
	 * stream property value into a data buffer, which is of the given
	 * size. The data buffer is a byte array that is returned by the
	 * method.</p>
	 * 
	 * @param streamProperty Stream property array to copy data from.
	 * @param dataSize Size of the data buffer to create and return.
	 * @return Data buffer containing data copied from the given stream
	 * property at its current position and of the given size.
	 * 
	 * @throws NullPointerException The given stream property value is <code>null</code>.
	 * @throws IllegalArgumentException Cannot read a negative number of bytes 
	 * from a stream property value.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 * @throws EndOfDataException Cannot read the requested amount of data as not 
	 * enough bytes remain in the given stream property value at its current position.
	 * @throws IOException Error occurred when trying to access the underlying stream.
	 */
	public @DataBuffer byte[] read(
			PropertyValue streamProperty,
			@UInt32 int dataSize) 
		throws NullPointerException,
			IllegalArgumentException, 
			IllegalPropertyValueException, 
			EndOfDataException, 
			IOException;

	/**
	 * <p>Write the given data buffer, an array of bytes, into the given
	 * stream property value and its current position. The number of 
	 * bytes written is the same as the size of the given data buffer.</p>
	 * 
	 * @param streamProperty Stream property value to have data written into 
	 * at its current position.
	 * @param data Data to write into the given stream property value at its
	 * current position.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 * @throws EndOfDataException The write operation will exceed the current
	 * size of the stream.
	 * @throws IOException Error occurred when trying to access the underlying stream.
	 */
	public void write(
			PropertyValue streamProperty,
			byte[] data) 
		throws NullPointerException,
			IllegalPropertyValueException,
			EndOfDataException, 
			IOException;

	/**
	 * <p>Extend the given stream property value using the data
	 * from the given data buffer, an array of bytes. The size 
	 * of the stream will increase by the size of the given 
	 * data buffer.</p>
	 * 
	 * @param streamProperty Stream property value to extend  
	 * the given data buffer.
	 * @param data Data buffer to use to extend the given stream
	 * property value.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 * @throws DataSizeException Not enough space was available to extend
	 * the given stream property value by the given number of bytes. 
	 */
	public void append(
			PropertyValue streamProperty,
			byte[] data) 
		throws NullPointerException,
			IllegalPropertyValueException,
			DataSizeException;

	/**
	 * <p>Returns <code>true</code> if the given property value 
	 * stream has a stored byte order; otherwise <code>false</code>.</p>
	 * 
	 * @param streamProperty Stream property value to test. 
	 * @return Does the given stream property value have as stored
	 * property value?
	 * 
	 * @throws NullPointerException The given stream property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 * 
	 * @see #getStoredByteOrder(PropertyValue)
	 */
	public @Bool boolean hasStoredByteOrder(
			PropertyValue streamProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Returns the stored byte order of the given stream property 
	 * value.</p>
	 * 
	 * @param streamProperty Stream property value to find the byte order
	 * for.
	 * @return Byte order for the given stream property value.
	 * 
	 * @throws NullPointerException The given stream proeprty value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 * @throws PropertyNotPresentException The byte order is not stored
	 * in the given property value. 
	 * 
	 * @see #hasStoredByteOrder(PropertyValue)
	 */
	public ByteOrder getStoredByteOrder(
			PropertyValue streamProperty) 
		throws NullPointerException,
			IllegalPropertyValueException,
			PropertyNotPresentException;
	
	/**
	 * <p>Sets the stored byte order of the given stream property value to the
	 * given byte order. The stream should be empty before calling this method
	 * to avoid a mixture of byte orders within the stream.</p>
	 *  
	 * @param streamProperty Stream property value to set the stored
	 * byte order for.
	 * @param byteOrder Byte order to set for the given stream property value.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 */
	public void setStoredByteOrder(
			PropertyValue streamProperty,
			ByteOrder byteOrder) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Clear the stored byte order of the given stream property
	 * value. The stream should be empty before calling this method
	 * to avoid a mixture of byte orders within the stream.</p>
	 * 
	 * @param streamProperty Stream property value to clear the stored
	 * property value of.
	 * 
	 * @throws NullPointerException The given stream property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 */
	public void clearStoredByteOrder(
			PropertyValue streamProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Reads the given number of elements from the given stream property
	 * value of the given element type and returns them as an array of property
	 * values. The read takes place at the current position for the given 
	 * stream property value and advances the position by the number of bytes
	 * read.</p>
	 * 
	 * <p>If the {@link #writeElements(PropertyValue, TypeDefinition, PropertyValue[])}
	 * method has been used to write an array of property values to a stream
	 * property value at a given position, a read from the same position with this
	 * method must create an array containing the same values.</p>
	 * 
	 * @param streamProperty Stream property value to read elements from.
	 * @param elementType Type of elements to read from the given stream 
	 * property value.
	 * @param numElements Number of elements to read from the given stream
	 * property value. 
	 * @return Array of elements read from the given stream property value.
	 * 
	 * @throws NullPointerException One or both of the stream property or element
	 * type arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 * @throws BadTypeException The values in the data stream do not match
	 * the given element type.
	 * @throws EndOfDataException Reading the number of requested elements
	 * will result in a read beyond the end of the given stream property
	 * value.
	 * 
	 * @see #writeElements(PropertyValue, TypeDefinition, PropertyValue[])
	 */
	public PropertyValue[] readElements(
			PropertyValue streamProperty,
			TypeDefinition elementType,
			@UInt32 int numElements) 
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException,
			EndOfDataException;

	/**
	 * <p>Writes the elements of the given array of property values 
	 * of the given element type into the given stream property value
	 * at its current position.</p>
	 * 
	 * @param streamProperty Stream property value into which elements
	 * are to be written.
	 * @param elementType Type of elements to be written to the given
	 * stream property value.
	 * @param data Elements to write to the given stream property value
	 * at its current position.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>,
	 * or one or more of the data elements is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 * @throws BadTypeException One or more of the elements of the data
	 * array are not defined by the given element type.
	 * @throws EndOfDataException The end of the stream has been reached
	 * before all data values could be written. The stream is returned
	 * to its previous state. 
	 * 
	 * @see #readElements(PropertyValue, TypeDefinition, int)
	 */
	public void writeElements(
			PropertyValue streamProperty,
			TypeDefinition elementType,
			PropertyValue[] data) 
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException,
			EndOfDataException;

	/**
	 * <p>Append the elements of the given property value array 
	 * onto the end of the given stream property value. All the 
	 * elements of the given property value array must by of the
	 * given type.</p>
	 * 
	 * @param streamProperty Stream property value to append elements
	 * to the end of.
	 * @param elementType Type of the elements of data to append. 
	 * @param data Property values of the elements to append onto
	 * the given stream property value.
	 * 
	 * @throws NullPointerException One or more of the arguments is
	 * <code>null</code>, or one or more of the given data values in <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is 
	 * not defined by this stream type definition.
	 * @throws BadTypeException One or more of the elements of the data
	 * array are not defined by the given element type.
	 * @throws DataSizeException The operation failed because not enough
	 * space was available to extend the stream.
	 */
	public void appendElements(
			PropertyValue streamProperty,
			TypeDefinition elementType,
			PropertyValue[] data) 
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException,
			DataSizeException;
	
	/**
	 * <p>Create a cloned copy of this stream type definition.</p>
	 *
	 * @return Cloned copy of this stream type definition.
	 */
	public TypeDefinitionStream clone();
}
