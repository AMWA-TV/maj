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
 * $Log: TypeDefinitionIndirect.java,v $
 * Revision 1.12  2011/02/14 22:32:50  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.11  2011/01/20 15:52:28  vizigoth
 * Removed warning messages.
 *
 * Revision 1.10  2011/01/20 15:51:37  vizigoth
 * Fixed up all meta tests to the point where they all pass.
 *
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
 * Revision 1.2  2007/12/04 09:40:08  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import java.io.IOException;
import java.io.NotSerializableException;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.TypeNotFoundException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.model.TaggedValue;



/**
 * <p>Specifies the definition of property values whose type is specified in
 * each instance.</p>
 * 
 * <p>One way to think of property values defined by the indirect
 * property definition is as: <em>a property value with a value that
 * is a property value.</em> One application of a value of indirect type is
 * a {@linkplain TaggedValue tagged value} that is used to represent a user
 * comment. Most comments are {@linkplain TypeDefinitionString strings} but 
 * a comment may take the form of an {@linkplain TypeDefinitionInteger integer}
 * value.</p>
 * 
 * <p>Note that the MAJ API provides a means to serialize data values to and from byte
 * arrays using Java object serialization. This is an internal mechanism that
 * is exposed to the application through methods such as {@link #getActualData(PropertyValue)}.
 * The binary format created by the MAJ API is not compatible with that of other
 * AAF implementations.</p>
 * 
 *
 *
 * @see tv.amwa.maj.enumeration.TypeCategory#Indirect
 * @see tv.amwa.maj.model.TaggedValue
 * @see tv.amwa.maj.model.ConstantValue
 * @see tv.amwa.maj.model.ControlPoint
 * @see tv.amwa.maj.industry.TypeDefinitions#Indirect
 * @see tv.amwa.maj.industry.PropertyValue
 */

public interface TypeDefinitionIndirect 
	extends TypeDefinition {

	/**
	 * <p>Creates and returns an indirect property value that contains 
	 * the given <em>actual</em> property value.</p>
	 * 
	 * @param actualValue Actual value to embed into the new indirect
	 * property value.
	 * @return Newly created indirect property value embedding the given
	 * actual property value.
	 * 
	 * @throws NullPointerException The given original property value is <code>null</code>.
	 * @throws NotSerializableException For creating opaque type values, the 
	 * given property value cannot be serialized to a stream of opaque bytes.
	 * 
	 * @see #createValueFromActualData(TypeDefinition, byte[])
	 * @see #getActualValue(PropertyValue)
	 */
	public PropertyValue createValueFromActualValue(
			PropertyValue actualValue) 
		throws NullPointerException,
			NotSerializableException;

	/**
	 * <p>Creates and returns an indirect property value from the given 
	 * type definition and serialized data.</p>
	 * 
	 * @param actualType Type of the actual data to embed within the
	 * new indirect property value.
	 * @param initialData Data to use to create the new indirect property
	 * value of the given type.
	 * @return New indirect property value created from the given type
	 * and data.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>.
	 * @throws EndOfDataException Insufficient data in the given array.
	 * @throws ClassCastException The type of the data does not match the 
	 * given type definition.
	 * 
	 * @see #createValueFromActualValue(PropertyValue)
	 */
	public PropertyValue createValueFromActualData(
			TypeDefinition actualType,
			@DataBuffer byte[] initialData)
		throws NullPointerException,
			EndOfDataException,
			ClassCastException;

	/**
	 * <p>Returns the actual property value embedded within the given indirect 
	 * property value.</p>
	 * 
	 * @param indirectProperty Property value containing the embedded actual 
	 * value to return.
	 * @return Actual value from within the given indirect property value.
	 * 
	 * @throws NullPointerException The given indirect property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not
	 * defined by an indirect type definition.
	 * @throws TypeNotFoundException For opaque properties, the type attached
	 * to the value cannot be resolved.
	 */
	public PropertyValue getActualValue(
			PropertyValue indirectProperty) 
		throws NullPointerException,
			IllegalPropertyValueException,
			TypeNotFoundException;

	/** 
	 * <p>Returns the size of the data held by the actual property value
	 * embedded within the given indirect property value.</p>
	 * 
	 * @param indirectProperty Property value containing an actual value.
	 * @return Size of the data represented by the actual property value.
	 * 
	 * @throws NullPointerException The given indirect property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not
	 * defined by an indirect type definition.
	 * @throws IOException An error occurred white trying to write the 
	 * property's value to a byte array.
	 * 
	 * @see #getActualData(PropertyValue)
	 */
	public @UInt32 int getActualSize(
			PropertyValue indirectProperty) 
		throws NullPointerException,
			IllegalPropertyValueException,
			IOException;

	/**
	 * <p>Returns the type definition of the property value embedded within 
	 * the given property value. This is effectively {@link #getActualValue(PropertyValue)}
	 * followed by {@link PropertyValue#getType()}.</p>
	 * 
	 * @param indirectProperty Property value containing an actual property
	 * value to determine the type of.
	 * @return Type definition of the actual property value in the given
	 * indirect property value.
	 * 
	 * @throws NullPointerException The given indirect property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not
	 * defined by an indirect type definition.
	 * @throws TypeNotFoundException For opaque type definitions, a type
	 * matching the values key cannot be found.
	 * 
	 * @see #getActualValue(PropertyValue)
	 * @see PropertyValue#getType()
	 */
	public TypeDefinition getActualType(
			PropertyValue indirectProperty)
		throws NullPointerException,
			IllegalPropertyValueException,
			TypeNotFoundException;

	/**
	 * <p>Returns a copy of the actual data contained in the indirect 
	 * property value.</p>
	 * 
	 * @param indirectProperty Property value containing an actual value.
	 * @return Serialized contents of the actual property value.
	 *  
	 * @throws NullPointerException The given indirect property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not
	 * defined by an indirect type definition.
	 * @throws IOException An error occurred white trying to write the 
	 * property's value to a byte array.
	 */
	public byte[] getActualData(
			PropertyValue indirectProperty) 
		throws NullPointerException,
			IllegalPropertyValueException,
			IOException;
	
	/**
	 * <p>Create a cloned copy of this indirect type definition.</p>
	 *
	 * @return Cloned copy of this indirect type definition.
	 */
	public TypeDefinitionIndirect clone();
}
