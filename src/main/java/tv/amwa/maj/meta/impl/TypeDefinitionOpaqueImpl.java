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
 * $Log: TypeDefinitionOpaqueImpl.java,v $
 * Revision 1.6  2011/07/27 17:41:46  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.5  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/26 11:50:11  vizigoth
 * Completed common method testing.
 *
 * Revision 1.3  2011/01/20 15:52:54  vizigoth
 * Fixed up all meta tests to the point where they all pass.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/06/16 14:55:48  vizigoth
 * Fixes to UTF16StringArray handling and other minor issues required for AAF file writing.
 *
 * Revision 1.3  2010/03/19 16:13:53  vizigoth
 * Added methods for writing bytes and calculating lengths.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.4  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.3  2008/01/14 20:55:19  vizigoth
 * Change to type category enumeration element names.
 *
 * Revision 1.2  2007/12/04 09:45:45  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:13:24  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.meta.impl;

// Opaque values work with type IDs yet to be resolved whereas in indirect
//       values they are resolved? - YES

import java.io.NotSerializableException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.exception.TypeNotFoundException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.MetaDictionary;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionOpaque;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;


/** 
 * <p>Implements the definition of a property type that has a value whose type is specified in each 
 * instance.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0222, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionOpaque",
		  description = "The TypeDefinitionOpaque class defines a property type that has a value whose type is specified in each instance.",
		  symbol = "TypeDefinitionOpaque")
public final class TypeDefinitionOpaqueImpl 
	extends 
		TypeDefinitionIndirectImpl 
	implements 
		TypeDefinitionOpaque, 
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -8999815866650774149L;
	
	private transient MetaDictionary resolutionDictionary = null;
	
	protected TypeDefinitionOpaqueImpl() { }
	
	/**
	 * <p>Creates and initializes a new opaque type definition, which defines a property type that 
	 * has a value whose type is specified in each instance.</p>
	 *
	 * <p>Creating new and unregistered type definitions is not recommended as this may cause
	 * interoperability issues with other systems. The official registry of type definitions 
	 * is available from SMPTE at <a href="http://www.smpte-ra.org/mdd/">SMPTE Metadata Registries 
	 * And Related Items</a>. The full range of data types documented in the AAF 1.1 object 
	 * specification can be accessed by name and identification using either
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(String)} or
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(tv.amwa.maj.record.AUID)}
	 * respectively.</p>
	 * 
	 * @param identification AUID used to identify this type.
	 * @param typeName Friendly name for the opaque type definition.
	 * 
	 * @throws NullPointerException The identification argument is <code>null</code>.
	 */
	public TypeDefinitionOpaqueImpl(
			tv.amwa.maj.record.AUID identification,
			@AAFString String typeName)
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new opaque type definition with a null indentification.");
		
		setIdentification(identification);
		setName(typeName);
	}
	
	public static class OpaqueValue
		extends PropertyValueImpl
		implements PropertyValue {

		private TypeDefinitionOpaqueImpl type;
		private ByteBuffer value; // First 16 bytes assumed to be AUID
		
		private OpaqueValue(
				TypeDefinitionOpaqueImpl type,
				ByteBuffer value) {
			
			this.type = type;
			setValue(value);
		}
		
		public TypeDefinitionImpl getType() {

			return type;
		}

		public ByteBuffer getValue() {

			return value.duplicate();
		}

		public boolean isDefinedType() {

			return true;
		}
		
		private void setValue(
				ByteBuffer value) {
			
			this.value = value.duplicate();
		}
		
		public boolean equals(
				Object o) {
			
			if (o == null) return false;
			if (o == this) return true;
			if (!(o instanceof PropertyValue)) return false;
			
			PropertyValue testValue = (PropertyValue) o;
			
			if (!(getType().equals(testValue.getType()))) return false;
			if ((getValue() == null) && (testValue.getValue() == null)) return true;
			
			return Arrays.equals(value.array(), ((OpaqueValue) testValue).getValue().array());
		}

	}
	
	public final tv.amwa.maj.industry.PropertyValue createValueFromHandle(
			ByteBuffer initData)
		throws NullPointerException,
			IllegalArgumentException {

		if (initData == null)
			throw new NullPointerException("Cannot create a new opaque value from null initial data.");
		
		if (initData.limit() < 16)
			throw new IllegalArgumentException("Cannot create a new opaque value from a handle of less than 16 bytes.");

		return new OpaqueValue(this, initData);
	}

	public final AUIDImpl getActualTypeID(
			tv.amwa.maj.industry.PropertyValue opaqueProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (opaqueProperty == null)
			throw new NullPointerException("Cannot retrieve a type id from a null property value.");
		if (!(equals(opaqueProperty.getType())))
			throw new IllegalPropertyValueException("The given property value does not match this opaque type definition.");
		
		byte[] auidValue = new byte[16];
		ByteBuffer keyAndValue = ((OpaqueValue) opaqueProperty).getValue();
		keyAndValue.rewind();
		keyAndValue.get(auidValue);
		
		return new AUIDImpl(auidValue);
	}

	public final ByteBuffer getHandle(
			tv.amwa.maj.industry.PropertyValue opaqueProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (opaqueProperty == null)
			throw new NullPointerException("Cannot retrieve the handle from a null opaque property.");
		if (!(equals(opaqueProperty.getType())))
			throw new IllegalPropertyValueException("The given property value is of a type that does not match this opaque type definition.");
		
		return ((OpaqueValue) opaqueProperty).getValue();
	}

	public final void setHandle(
			tv.amwa.maj.industry.PropertyValue opaqueProperty,
			ByteBuffer handle)
		throws NullPointerException,
			IllegalPropertyValueException,
			IllegalArgumentException {

		if (opaqueProperty == null)
			throw new NullPointerException("Cannot set a handle for a null property value.");
		if (handle == null)
			throw new NullPointerException("Cannot set the handle of an opaque property value using a null value.");
		if (!(equals(opaqueProperty.getType())))
			throw new IllegalPropertyValueException("The given property value is of a type that does not match this opaque type definition.");
		if (handle.limit() < 16)
			throw new IllegalArgumentException("Cannot use a handle with less than 16 bytes to the value of an opaque property.");
			
		((OpaqueValue) opaqueProperty).setValue(handle);
	}

	public final tv.amwa.maj.industry.PropertyValue createValueFromActualData(
			tv.amwa.maj.record.AUID actualTypeId,
			byte[] initData) 
		throws NullPointerException {
		
		return createValueFromActualData(actualTypeId, initData, ByteOrder.Big);
		
	}
		
	public final tv.amwa.maj.industry.PropertyValue createValueFromActualData(
			tv.amwa.maj.record.AUID actualTypeId,
			byte[] initData,
			ByteOrder byteOrder) 
		throws NullPointerException {

		if (actualTypeId == null)
			throw new NullPointerException("Cannot create an opaque property with a null actual type id.");
		if (initData == null)
			throw new NullPointerException("Cannot create an opaque property with a null initial data value.");
		if (byteOrder == null)
			throw new NullPointerException("Cannot create an opaque property with an unspecified byte order.");
		
		ByteBuffer handle = ByteBuffer.allocate(initData.length + 16);

		if (byteOrder == ByteOrder.Little)
			handle.order(java.nio.ByteOrder.LITTLE_ENDIAN);
		else
			handle.order(java.nio.ByteOrder.BIG_ENDIAN);
		
		handle.put(actualTypeId.getAUIDValue());
		handle.put(initData);
		handle.rewind();
		return new OpaqueValue(this, handle);
	}
	
	@Override
	public final tv.amwa.maj.industry.PropertyValue createValueFromActualData(
			tv.amwa.maj.meta.TypeDefinition type,
			byte[] initData) 
		throws NullPointerException {
		
		if (type == null)
			throw new NullPointerException("Cannot create an opaque property with a null type definition.");
		
		return createValueFromActualData(type.getAUID(), initData);
	}
	
	@Override
	public final byte[] getActualData(
			tv.amwa.maj.industry.PropertyValue opaqueProperty)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		if (opaqueProperty == null)
			throw new NullPointerException("Cannot retrieve the actual data from a null opaque property.");
		if (!(equals(opaqueProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this opaque type definition.");
		
		ByteBuffer handle = ((OpaqueValue) opaqueProperty).getValue();
		byte[] actualData = new byte[handle.limit() - 16];
		handle.position(16);
		handle.get(actualData);
		
		return actualData;
	}
	
	@Override
	public final int getActualSize(
			tv.amwa.maj.industry.PropertyValue opaqueProperty)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		if (opaqueProperty == null)
			throw new NullPointerException("Cannot retrieve the actual data length from a null opaque property.");
		if (!(equals(opaqueProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this opaque type definition.");

		return ((OpaqueValue) opaqueProperty).getValue().limit() - 16;
	}
	
	public final void setDictionary(
			MetaDictionary dictionary) 
		throws NullPointerException {
		
		if (dictionary == null)
			throw new NullPointerException("Cannot set the dictionary to use for type resolution from a null value.");
		
		resolutionDictionary = dictionary;
	}
	
	@Override
	public final TypeDefinition getActualType(
			tv.amwa.maj.industry.PropertyValue opaqueProperty) 
		throws NullPointerException,
			IllegalPropertyValueException,
			TypeNotFoundException {
		
		if (opaqueProperty == null)
			throw new NullPointerException("Cannot retrieve the actual type of a null opaque property.");
		if (!(equals(opaqueProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property does not match this opaque type definition.");
		
		AUID actualTypeId = getActualTypeID(opaqueProperty);
		TypeDefinition actualType = 
			Warehouse.lookForType(actualTypeId);
		try {
			if ((actualType == null) && (resolutionDictionary != null))
				actualType = resolutionDictionary.lookupOpaqueTypeDefinition(actualTypeId);
		} catch (ClassCastException e) {
			actualType = null;
		} catch (InvalidParameterException e) {
			actualType = null;
		}
		
		if (actualType == null)
			throw new TypeNotFoundException("A type matching the handle of this type definition has not been found.");
		
		return actualType;
	}
	
	@Override
	public final tv.amwa.maj.industry.PropertyValue getActualValue(
			tv.amwa.maj.industry.PropertyValue opaqueProperty) 
		throws NullPointerException,
			IllegalPropertyValueException,
			TypeNotFoundException,
			ClassCastException {
				
		TypeDefinition actualType = getActualType(opaqueProperty);
		byte[] bytes = getActualData(opaqueProperty);

		return actualType.createFromBytes(ByteBuffer.wrap(bytes));
	}
	
	@Override
	public final tv.amwa.maj.industry.PropertyValue createValueFromActualValue(
			tv.amwa.maj.industry.PropertyValue propertyValue) 
		throws NullPointerException,
			NotSerializableException {
		
		if (propertyValue == null)
			throw new NullPointerException("Cannot create a new opaque property value from a null value.");

		PropertyValue indirectValue = TypeDefinitions.Indirect.createValue(propertyValue);
		ByteBuffer opaqueBuffer = ByteBuffer.allocate((int) indirectValue.getType().lengthAsBytes(indirectValue));
		try {
			indirectValue.getType().writeAsBytes(indirectValue, opaqueBuffer);
		} catch (IllegalPropertyValueException e) {
			e.printStackTrace();
			throw new InternalError("Should never get here.");
		} catch (InsufficientSpaceException e) {
			e.printStackTrace();
			throw new InternalError("Should never get here.");
		}

		opaqueBuffer.rewind();
		return new OpaqueValue(this, opaqueBuffer);
	}
	
	@Override
	public final tv.amwa.maj.industry.PropertyValue createValue(
			Object javaValue) 
		throws ClassCastException {
		
		if (javaValue == null)
			throw new ClassCastException("Cannot create a new opaque value from a null value.");
		
		if (javaValue instanceof byte[])
			javaValue = ByteBuffer.wrap((byte[]) javaValue);
			
		if (javaValue instanceof ByteBuffer) {
			
			try {
				return createValueFromHandle((ByteBuffer) javaValue);
			} catch (RuntimeException e) {
				throw new ClassCastException("Cannot create an opaque property from the given byte array as it has insufficient bytes.");
			}
		}
		
		if (javaValue instanceof tv.amwa.maj.industry.PropertyValue) {
			try {
				return createValueFromActualValue((PropertyValue) javaValue);
			} catch (NotSerializableException e) {
				throw new ClassCastException("The given property value cannot be serialized to a byte stream to make an opaque property value.");
			} catch (NullPointerException e) {
				throw new ClassCastException("Unexpected null pointer exception: " + e.getMessage());
			}
		}
		
		throw new ClassCastException("Cannot cast the given java object to an opaque property value.");
	}
	
	public final TypeCategory getTypeCategory() {
		
		return TypeCategory.Opaque;
	}
	
	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
	 	throws NullPointerException,
	 		EndOfDataException {

		if (buffer == null)
			throw new NullPointerException("Cannot create a value from a null byte buffer.");

		return createValueFromHandle(buffer);
	}
	
	@Override
	public List<PropertyValue> writeAsBytes(
			PropertyValue value,
			ByteBuffer buffer) 
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		if (value == null)
			throw new NullPointerException("Cannot write a null property value to a byte stream.");
		if (buffer == null)
			throw new NullPointerException("Cannot write a value to a null byte buffer.");
		if (!value.getType().equals(this))
			throw new IllegalPropertyValueException("The given property value to write a bytes is not the same as this type. " +
					value.getType().getName() + " != " + getName() + ".");
		
		ByteBuffer valueBuffer = ((OpaqueValue) value).getValue();
		int preservePosition = valueBuffer.position();
		valueBuffer.rewind();
		
		if (valueBuffer.remaining() > buffer.remaining()) {
			valueBuffer.position(preservePosition);
			throw new InsufficientSpaceException("Cannot write the given source opaque value into the given target buffer as the target is too small.");
		}
		
		buffer.put(valueBuffer);
		valueBuffer.position(preservePosition);
		
		return null;
	}
	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (value == null)
			throw new NullPointerException("Cannot provide the length in bytes of a null value.");
		if (!value.getType().equals(this))
			throw new IllegalPropertyValueException("The given property value to find the serialized length of is not the " +
					"same as this type. " + value.getType().getName() + " != " + getName() + ".");
		
		// Respecting any limit that has been set
		return ((OpaqueValue) value).getValue().limit(); 
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionOpaque");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
	}
	
	public TypeDefinitionOpaque clone() {
		
		return (TypeDefinitionOpaque) super.clone();
	}
}
