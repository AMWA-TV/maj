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
 * $Log: TypeDefinitionIndirectImpl.java,v $
 * Revision 1.7  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.6  2011/07/27 17:39:47  vizigoth
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
 * Revision 1.5  2010/06/16 14:55:48  vizigoth
 * Fixes to UTF16StringArray handling and other minor issues required for AAF file writing.
 *
 * Revision 1.4  2010/04/13 07:23:26  vizigoth
 * Improved byte stream reading to support little endian byte streams.
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
 * Revision 1.3  2008/01/14 20:55:20  vizigoth
 * Change to type category enumeration element names.
 *
 * Revision 1.2  2007/12/04 09:45:45  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:13:35  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:23  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.meta.impl;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.TypeNotFoundException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionIndirect;
import tv.amwa.maj.meta.impl.TypeDefinitionOpaqueImpl.OpaqueValue;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.AUIDImpl;


/** 
 * 
 * <p>Implementation of property values whose type is specified in each instance.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0221, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionIndirect",
		  description = "The TypeDefinitionIndirect class defines a property type that has a value whose type is specified in each instance.",
		  symbol = "TypeDefinitionIndirect")
public class TypeDefinitionIndirectImpl 
	extends 
		SingletonTypeDefinitionImpl 
	implements 
		TypeDefinitionIndirect, 
		Serializable {

	/** <p></p> */
	private static final long serialVersionUID = -3618444671349385648L;
	
	protected TypeDefinitionIndirectImpl() { }
	
	/**
	 * <p>Creates and initializes a new indirect type definition, which defines a property type 
	 * that has a value whose type is specified in each instance.</p>
	 *
	 * <p>Creating new and unregistered type definitions is not recommended as this may cause
	 * interoperability issues with other systems. The official registry of type definitions 
	 * is available from SMPTE at <a href="http://www.smpte-ra.org/mdd/">SMPTE Metadata Registries 
	 * And Related Items</a>. The full range of data types documented in the AAF 1.1 object 
	 * specification can be accessed by name and identification using either
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(String)} or
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(tv.amwa.maj.record.AUID)}
	 * respectively.</p>

	 * @param identification AUID to be used to identify this type.
	 * @param typeName Friendly name of the new indirect type definition.
	 * 
	 * @throws NullPointerException The identification argument is <code>null</code>.
	 */
	public TypeDefinitionIndirectImpl(
			AUID identification,
			@AAFString String typeName)
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new indirect type definition with a null identification.");
		
		setIdentification(identification);
		setName(typeName);
	}
	
	public static class IndirectValue
		extends PropertyValueImpl
		implements PropertyValue {

		private TypeDefinitionIndirectImpl type;
		private PropertyValue value;
		
		private IndirectValue(
				TypeDefinitionIndirectImpl type,
				PropertyValue value) {
			
			this.type = type;
			setValue(value);
		}
		
		public tv.amwa.maj.meta.TypeDefinition getType() {

			return type;
		}

		public PropertyValue getValue() {

			return value;
		}

		public boolean isDefinedType() {

			return true;
		}
		
		void setValue(
				PropertyValue value) {
			
			this.value = value;
		}
		
	}
	
	/** 
	 * @see tv.amwa.maj.meta.TypeDefinitionIndirect#createValueFromActualData(tv.amwa.maj.meta.TypeDefinition, byte[])
	 */
	public tv.amwa.maj.industry.PropertyValue createValueFromActualData(
			TypeDefinition actualType,
			byte[] initData)
		throws NullPointerException,
			EndOfDataException,
			ClassCastException {

		if (actualType == null)
			throw new NullPointerException("Cannot create a new indirect property value with a null actual type value.");
		if (initData == null) // Might need to find a way to relax this.
			throw new NullPointerException("Cannot create a new indirect property value with null initialization data.");
		
		PropertyValue indirectValue = actualType.createFromBytes(ByteBuffer.wrap(initData));
		
		return new IndirectValue(this, indirectValue);
	}
	
	/**
	 * @see tv.amwa.maj.meta.TypeDefinitionIndirect#createValueFromActualValue(tv.amwa.maj.industry.PropertyValue)
	 */
	public tv.amwa.maj.industry.PropertyValue createValueFromActualValue(
			tv.amwa.maj.industry.PropertyValue indirectProperty)
			throws NullPointerException, 
				NotSerializableException {

		if (indirectProperty == null)
			throw new NullPointerException("Cannot create a new indirect property definition from a null value.");
		
		return new IndirectValue(this, indirectProperty);
	}

	/**
	 * @see tv.amwa.maj.meta.TypeDefinitionIndirect#getActualData(tv.amwa.maj.industry.PropertyValue)
	 */
	public byte[] getActualData(
			tv.amwa.maj.industry.PropertyValue indirectProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (indirectProperty == null)
			throw new NullPointerException("Cannot retrieve the actual data for a null indirect property value.");
		if (!(equals(indirectProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		
		PropertyValue internalValue = (PropertyValue) indirectProperty.getValue();
		ByteBuffer buffer = ByteBuffer.allocate((int) internalValue.getType().lengthAsBytes(internalValue));
		try {
			internalValue.getType().writeAsBytes(internalValue, buffer);
		} catch (InsufficientSpaceException e) {
			e.printStackTrace();
			throw new InternalError("Should never get here.");
		}
		
		return buffer.array(); 
	}

	/**
	 * @see tv.amwa.maj.meta.TypeDefinitionIndirect#getActualSize(tv.amwa.maj.industry.PropertyValue)
	 */
	public int getActualSize(
			tv.amwa.maj.industry.PropertyValue indirectProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (indirectProperty == null)
			throw new NullPointerException("Cannot retrieve the actual data size for a null indirect property value.");
		if (!(equals(indirectProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		
		PropertyValue internalValue = (PropertyValue) indirectProperty.getValue();
		return (int) internalValue.getType().lengthAsBytes(internalValue);
	}

	/**
	 * @see tv.amwa.maj.meta.TypeDefinitionIndirect#getActualType(tv.amwa.maj.industry.PropertyValue)
	 */
	public tv.amwa.maj.meta.TypeDefinition getActualType(
			tv.amwa.maj.industry.PropertyValue indirectProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (indirectProperty == null)
			throw new NullPointerException("Cannot find the actual type of a null indirect property value.");
		if (!(equals(indirectProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property does not match this type definition.");
		
		PropertyValue actualValue = ((IndirectValue) indirectProperty).getValue();
		if (actualValue == null)
			throw new TypeNotFoundException("The actual type of the indirect property value could not be found.");
		
		return actualValue.getType();
	}

	/**
	 * @see tv.amwa.maj.meta.TypeDefinitionIndirect#getActualValue(tv.amwa.maj.industry.PropertyValue)
	 */
	public tv.amwa.maj.industry.PropertyValue getActualValue(
			tv.amwa.maj.industry.PropertyValue indirectProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (indirectProperty == null)
			throw new NullPointerException("Cannot find the actual type of a null indirect property value.");
		if (!(equals(indirectProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property does not match this type definition.");
		
		PropertyValue actualValue = ((IndirectValue) indirectProperty).getValue();
		return actualValue;
	}

	/** 
	 * @see tv.amwa.maj.meta.impl.TypeDefinitionImpl#createValue(java.lang.Object)
	 */
	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		if (javaValue instanceof PropertyValue)
			return new IndirectValue(this, (PropertyValue) javaValue);
		
		if (javaValue instanceof String)
			return new IndirectValue(this, TypeDefinitions.UTF16String.createValue(javaValue));
		
		if (javaValue instanceof Integer)
			return new IndirectValue(this, TypeDefinitions.Int32.createValue(javaValue));
		
		if (javaValue instanceof Byte)
			return new IndirectValue(this, TypeDefinitions.Int8.createValue(javaValue));
		
		if (javaValue instanceof Short)
			return new IndirectValue(this, TypeDefinitions.Int16.createValue(javaValue));
		
		if (javaValue instanceof Long)
			return new IndirectValue(this, TypeDefinitions.Int64.createValue(javaValue));
		
		if (javaValue instanceof MediaEnumerationValue) {
			TypeDefinition guessedEnumType = Warehouse.lookForType(javaValue.getClass().getSimpleName());
			if (guessedEnumType != null)
				return new IndirectValue(this, guessedEnumType.createValue(javaValue));
		}

		if (javaValue instanceof Rational) 
			return new IndirectValue(this, TypeDefinitions.Rational.createValue((Rational) javaValue));
		
		// Other types could be added here
		
		throw new ClassCastException("Cannot create a new indirect property value from the given object.");
	}

	/** 
	 * @see tv.amwa.maj.meta.impl.TypeDefinitionImpl#getTypeCategory()
	 */
	@Override
	public TypeCategory getTypeCategory() {

		return TypeCategory.Indirect;
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
		throws NullPointerException,
			EndOfDataException {
		
		super.createFromBytes(buffer);
		
		if (buffer.remaining() < 16)
			throw new EndOfDataException("Not enough bytes to read a 16-byte key for an indirect value.");

		AUID typeID = AUIDImpl.createFromBuffer(buffer);
		
		TypeDefinition type = Warehouse.lookForType(typeID);
		PropertyValue indirectValue = type.createFromBytes(buffer);
		
		return createValue(indirectValue);
	}
	
	@Override
	public List<PropertyValue> writeAsBytes(
			PropertyValue value,
			ByteBuffer buffer) 
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		super.writeAsBytes(value, buffer);
		
		if (buffer.remaining() < lengthAsBytes(value)) 
			throw new InsufficientSpaceException("Insufficient space in the given buffer to write a key and an indirect value.");
		
		PropertyValue indirectValue = ((IndirectValue) value).getValue();
		AUID typeKey = indirectValue.getType().getAUID();
		if (typeKey.isUniversalLabel())
			buffer.put(((UL) typeKey).getUniversalLabel());
		else
			buffer.put(typeKey.getAUIDValue());
		
		indirectValue.getType().writeAsBytes(indirectValue, buffer);
		
		return null;
	}
	
	@Override
	public List<PropertyValue> writeAsStructuredStorageBytes(
			PropertyValue value,
			ByteBuffer buffer) 
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		super.writeAsBytes(value, buffer);
		
		if (buffer.remaining() < lengthAsBytes(value)) 
			throw new InsufficientSpaceException("Insufficient space in the given buffer to write a key and an indirect value.");
		
		PropertyValue indirectValue = ((IndirectValue) value).getValue();
		AUID typeKey = indirectValue.getType().getAUID();
//		if (typeKey.isUniversalLabel())
//			buffer.put(((UL) typeKey).getUniversalLabel());
//		else
			buffer.put(typeKey.getAUIDValue());
		
		indirectValue.getType().writeAsBytes(indirectValue, buffer);
		
		return null;
	}
	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.lengthAsBytes(value);
		
		if (value instanceof OpaqueValue) return 0l;
		
		PropertyValue indirectValue = (PropertyValue) value.getValue();
		return indirectValue.getType().lengthAsBytes(indirectValue) + 16l;
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = null;
		
		if ( metadict instanceof Element ) {
			Element passedElement = (Element) metadict;
			if (passedElement.getNodeName().startsWith("TypeDefinition"))
				typeElement = passedElement;
			else
				typeElement = XMLBuilder.createChild(metadict, namespace, 
						prefix, "TypeDefinitionIndirect");
		}
		
		if ( metadict instanceof DocumentFragment )
			typeElement = XMLBuilder.createChild(metadict, namespace, 
					prefix, "TypeDefinitionIndirect");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
	}
	
	public TypeDefinitionIndirect clone() {
		
		return (TypeDefinitionIndirect) super.clone();
	}
}
