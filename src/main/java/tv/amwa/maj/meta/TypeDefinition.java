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
 * $Log: TypeDefinition.java,v $
 * Revision 1.8  2011/02/14 22:32:50  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.7  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.6  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.9  2010/05/14 18:31:13  vizigoth
 * Provided mechanism for a difference between structured storage byte writing and KLV byte writing.
 *
 * Revision 1.8  2010/03/19 16:19:20  vizigoth
 * Added general resolve references method.
 *
 * Revision 1.7  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.6  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2009/02/24 18:47:50  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2008/02/08 11:27:16  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:11  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.model.Parameter;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Specifies the definition of a property type.</p>
 * 
 * <p>The methods of type definitions allow property values of the given type
 * to be manipulated. The interfaces provided in the MAJ API allow property
 * values to be converted to and from equivalent Java types. Taking the 
 * {@linkplain TypeDefinitionCharacter character type definition} as an example:</p>
 * 
 * <ul>
 *  <li>The {@link TypeDefinitionCharacter#createValueFromCharacter(char) createValueFromChar(char)} 
 *  converts a value of the Java <code>char</code> primitive type into a new AAF {@linkplain PropertyValue 
 *  property value} with type definition {@link TypeDefinitionCharacter}. The {@link #createValue(Object)}
 *  method will do the same for a {@link java.lang.Character} value.</li>
 *  <li>The {@link TypeDefinitionCharacter#getCharacter(PropertyValue) char getCharacter(PropertyValue)} method
 *  retrieves a value of Java <code>char</code> primitive type from an AAF {@linkplain PropertyValue 
 *  property value} of the correct type.</p>
 *  <li>The {@link TypeDefinitionCharacter#setCharacter(PropertyValue, char) setCharacter(PropertyValue, char)}
 *  method sets an existing character type AAF {@linkplain PropertyValue property value} to the given value of the 
 *  Java <code>char</code> primitive type.</li>
 * </ul>
 * 
 * <p>All other data types have similar methods.</p>
 * 
 *
 * 
 * @see PropertyDefinition#getTypeDefinition()
 * @see PropertyValue#getType()
 * @see Parameter#getTypeDefinition()
 * @see tv.amwa.maj.industry.Warehouse#lookForType(String)
 * @see tv.amwa.maj.industry.Warehouse#lookForType(AUID)
 * @see tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionWeakReferenceVector
 * @see tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionStrongReferenceSet 
 */

public interface TypeDefinition 
	extends MetaDefinition {

	/**
	 * <p>Returns the type category to which this type definition belongs, which
	 * corresponds to its sub interface. For example, {@linkplain tv.amwa.maj.enumeration.TypeCategory#Int}
	 * is returned for a {@link TypeDefinitionInteger}.</p>
	 * 
	 * @return Type category to which the type definition belongs.
	 * 
	 * @see tv.amwa.maj.enumeration.TypeCategory
	 */
	public TypeCategory getTypeCategory();

	// This method is not implemented in the com-api reference implementation so
	// removed from the MAJ API
	
	/**
	 * <p>Returns the type definition through which values of this type may be 
	 * accessed if the client wishes to access the value as as raw data.</p>
	 * 
	 * @return Type definition through which values of this type may be 
	 * accessed if the client wishes to access the value as as raw data.
	 */
	// public TypeDefinition rawAccessType();
	
	/**
	 * <p>Create a {@linkplain PropertyValue property value} of this type from the 
	 * given Java value. Each kind of type definition will do its best to convert the given 
	 * value to a property value of its type.</p>
	 *
	 * @param javaValue Java value to use to create a new property value from.
	 * @return New property value of this type with a value corresponding to the 
	 * given Java value.
	 * 
	 * @throws ClassCastException The given java value could not be cast to a 
	 * property value of this type.
	 */
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException;
	
	/**
	 * <p>Create a value of this type by reading the bytes in the given byte buffer.
	 * The buffer is assumed to have exactly the number of bytes that represent the
	 * value. For example, the length of a string value will be determined by the 
	 * length (remaining bytes) in the buffer and not by reading a length value
	 * from the buffer.</p>
	 * 
	 * <p>This method is able to read values written by the 
	 * {@link #writeAsBytes(PropertyValue, ByteBuffer)} method.</p>
	 * 
	 * @param buffer Buffer to create a value from.
	 * @return Value of this type created from the given byte array.
	 * 
	 * @throws NullPointerException Cannot read values from a <code>null</code> buffer.
	 * @throws EndOfDataException Insufficient bytes remaining in the given buffer to create 
	 * a value of this type.
	 * 
	 * @see #writeAsBytes(PropertyValue, ByteBuffer)
	 * @see #lengthAsBytes(PropertyValue)
	 */
	public PropertyValue createFromBytes(
			ByteBuffer buffer)
		throws NullPointerException,
			EndOfDataException;
		
	/**
	 * <p>Write the given property value of this type to the given byte stream. This version is most
	 * appropriate for writing KLV streams (MXF files).</p>
	 * 
	 * <p>The format used to serialize the value with be compatible with the 
	 * {@link #createFromBytes(ByteBuffer)} method. The number of bytes written to 
	 * the byte buffer will be the same as returned by {@link #lengthAsBytes(PropertyValue)}.</p>
	 * 
	 * @param value Value to write to the given buffer.
	 * @param buffer Buffer to write the value to.
	 * @return List of any strongly referenced property values referenced from the new structure
	 * of bytes. Elements of this list will require separate serialization to the stream if 
	 * the reference if to be resolved.
	 * 
	 * @throws NullPointerException Cannot write from a <code>null</code> property value and/or
	 * to a <code>null</code> value.
	 * @throws IllegalPropertyValueException The given property value is not of this type.
	 * @throws InsufficientSpaceException Insufficient space to write the given value into the
	 * given buffer.
	 * 
	 * @see #writeAsStructuredStorageBytes(PropertyValue, ByteBuffer)
	 * @see #createFromBytes(ByteBuffer)
	 * @see #lengthAsBytes(PropertyValue)
	 */
	public List<PropertyValue> writeAsBytes(
			PropertyValue value,
			ByteBuffer buffer)
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException;
	
	/**
	 * <p>Write the given property value of this type to the given byte stream that is to be written to a
	 * structured storage format file.</p>
	 * 
	 * <p>The number of bytes written to 
	 * the byte buffer will be the same as returned by {@link #lengthAsBytes(PropertyValue)}.</p>
	 * 
	 * @param value Value to write to the given buffer.
	 * @param buffer Buffer to write the value to.
	 * @return List of any strongly referenced property values referenced from the new structure
	 * of bytes. Elements of this list will require separate serialization to the stream if 
	 * the reference if to be resolved.
	 * 
	 * @throws NullPointerException Cannot write from a <code>null</code> property value and/or
	 * to a <code>null</code> value.
	 * @throws IllegalPropertyValueException The given property value is not of this type.
	 * @throws InsufficientSpaceException Insufficient space to write the given value into the
	 * given buffer.
	 * 
	 * @see #writeAsBytes(PropertyValue, ByteBuffer)
	 * @see #lengthAsBytes(PropertyValue)
	 */
	public List<PropertyValue> writeAsStructuredStorageBytes(
			PropertyValue value,
			ByteBuffer buffer) 
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException;
	
	/**
	 * <p>Returns the number of bytes that the given value of this type is serialized as a
	 * sequence of bytes.</p>
	 * 
	 * <p>The number returned by this method is the same as the number of bytes written
	 * to a byte buffer by the {@link #writeAsBytes(PropertyValue, ByteBuffer)} method.</p>
	 * 
	 * @param value Value to find the length of when serialized to bytes.
	 * @return Number of bytes used to represent the given value when serialized.
	 * 
	 * @throws NullPointerException Cannot compute the serialized length of a <code>null</code> value.
	 * @throws IllegalPropertyValueException The given property value is not of this type.
	 * 
	 * @see #writeAsBytes(PropertyValue, ByteBuffer)
	 * @see #createFromBytes(ByteBuffer)
	 */
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException;
	
	/**
	 * <p>Resolve any unresolved object references using the given reference map. Unresolved object
	 * references occur when a value has been read from a file but not linked to its strong or weak
	 * values. This method only effects types that are references or contain references in collections, 
	 * which are:</p>
	 * 
	 * <ul>
	 *  <li>{@link TypeDefinitionWeakObjectReference};</li>
	 *  <li>{@link TypeDefinitionStrongObjectReference};</li>
	 *  <li>{@link TypeDefinitionVariableArray} with elements of one of the object reference types;</li>
	 *  <li>{@link TypeDefinitionSet} with elements of one of the object reference types.</li>
	 * </ul>
	 * 
	 * @param value Value to resolve references for.
	 * @param referenceMap Map of local identifications to values to be used for resolution.
	 * @return Have all references been resolved?
	 * 
	 * @throws NullPointerException The value or reference map is/are <code>null</code>. 
	 * @throws IllegalPropertyValueException The given property value is not of this type.
	 */
	public boolean resolveReferences(
			PropertyValue value,
			Map<AUID, MetadataObject> referenceMap)
		throws NullPointerException,
			IllegalPropertyValueException;
	
	/**
	 * <p>On the given metadata object, set the specified property to the given value. The property
	 * must be of this type.</p>
	 * 
	 * @param metadataObject Object to set the value on.
	 * @param property Property of the object to set.
	 * @param value Value of this type to set the property value to.
	 * 
	 * @throws IllegalArgumentException The given property value is not of this type.
	 * @throws IllegalAccessException Insufficient privileges to set properties on the given
	 * metadata object.
	 * @throws InvocationTargetException Problem encountered while calling the set method
	 * associated with the property for the given object.
	 * 
	 * @see #getPropertyValue(MetadataObject, PropertyDefinition)
	 */
	public abstract void setPropertyValue(
			MetadataObject metadataObject,
			PropertyDefinition property,
			PropertyValue value)
		throws IllegalArgumentException, 
			IllegalAccessException, 
			InvocationTargetException;
	
	/**
	 * <p>Get the specified property's value from the given object. The property must be of
	 * this type.</p> 
	 * 
	 * @param metadataObject Object to retrieve the property value from.
	 * @param property Property of this type to extract the value for.
	 * @return Current value of the given property for the object.
	 * 
	 * @throws IllegalArgumentException The given property value is not of this type.
	 * @throws IllegalAccessException Insufficient privileges to get properties on the given
	 * metadata object.
	 * @throws InvocationTargetException Problem encountered while calling the get method
	 * associated with the property for the given object.
	 * 
	 * @see #setPropertyValue(MetadataObject, PropertyDefinition, PropertyValue)
	 */
	public PropertyValue getPropertyValue(
			MetadataObject metadataObject,
			PropertyDefinition property) 
		throws IllegalArgumentException, 
			IllegalAccessException, 
			InvocationTargetException;
	
	/**
	 * <p>Create a cloned copy of this type definition.</p>
	 *
	 * @return Cloned copy of this type definition.
	 */
	public TypeDefinition clone();
}
