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
 * $Log: TypeDefinitionIntegerImpl.java,v $
 * Revision 1.7  2011/07/27 17:39:47  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.6  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.5  2011/01/26 11:50:11  vizigoth
 * Completed common method testing.
 *
 * Revision 1.4  2011/01/25 14:17:55  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.3  2011/01/19 21:37:53  vizigoth
 * Added property initialization code.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/12/15 18:50:43  vizigoth
 * Moved constant values for integer size into interface.
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
 * Revision 1.3  2008/01/14 20:55:18  vizigoth
 * Change to type category enumeration element names.
 *
 * Revision 1.2  2007/12/04 09:45:45  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:13:14  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:22  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.BadSizeException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.TypeDefinitionInteger;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements the definition of a property type that is an integer with the specified number of bytes.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0204, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionInteger",
		  description = "The TypeDefinitionInteger class defines a property type that is an integer with the specified number of bytes.",
		  symbol = "TypeDefinitionInteger")
public final class TypeDefinitionIntegerImpl 
	extends 
		SingletonTypeDefinitionImpl 
	implements 
		TypeDefinitionInteger,
		Serializable,
		CommonConstants,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -9133561212939801341L;
	
	private byte size;
	private boolean isSigned;
	
	protected TypeDefinitionIntegerImpl() { }

	/**
	 * <p>Creates and initializes the integer type definition, which defines a property 
	 * type that is a signed or an unsigned integer with the specified number of bytes.</p>
	 * 
	 * <p>The implementation of this method may only allow certain values
	 * for the <code>intSize</code>. It <em>will</em> allow at least 1, 2, 4, and 8-byte
	 * integers; some implementations may allow more than that.</p>
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
	 * @param identification AUID to use to identify this type.
	 * @param typeName Friendly name for the integer type definition.
	 * @param intSize Number of bytes used to represent a value.
	 * @param isSigned Is the integer type signed?
	 * 
	 * @throws NullPointerException The identification argument is <code>null</code>.
	 * @throws IllegalArgumentException The implementation does not support the given number of
	 * bytes per integer value.
	 */
	public TypeDefinitionIntegerImpl(
			AUID identification,
			@AAFString String typeName,
			@UInt8 byte intSize,
			@Bool boolean isSigned) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new integer type definition for a null identification.");
		
		setIdentification(identification);
		setName(typeName);
		setSize(intSize);
		setIsSigned(isSigned);
	}
	
	public static abstract class BaseIntegerValue
		extends PropertyValueImpl
		implements PropertyValue {
		
		private TypeDefinitionIntegerImpl type;
		
		public TypeDefinitionIntegerImpl getType() {
			return type;
		}

		protected void setType(
				TypeDefinitionIntegerImpl type) {
			
			this.type = type;
		}

		public boolean isDefinedType() {
			
			return true;
		}
	}
	
	public static class ByteValue
		extends BaseIntegerValue
		implements PropertyValue {

		private Byte value;
		
		private ByteValue(
				TypeDefinitionIntegerImpl type,
				Byte value) 
			throws IllegalArgumentException {
			
			setType(type);
			setValue(value);
		}

		public Byte getValue() {

			return value;
		}
		
		private void setValue(
				Byte value) 
			throws IllegalArgumentException {
			
			this.value = value;
		}
	}
	
	public static class ShortValue
		extends BaseIntegerValue
		implements PropertyValue {

		private Short value;
		
		public ShortValue(
				TypeDefinitionIntegerImpl type,
				Short value) 
			throws IllegalArgumentException {
			
			setType(type);
			setValue(value);
		}
		
		public Short getValue() {

			return value;
		}

		public void setValue(
				short value) 
			throws IllegalArgumentException {
			
			this.value = value;
		}
	}

	public static class IntegerValue
		extends BaseIntegerValue
		implements PropertyValue {

		private Integer value;
		
		private IntegerValue(
				TypeDefinitionIntegerImpl type,
				Integer value) 
			throws IllegalArgumentException {
			
			setType(type);
			setValue(value);
		}
		
		private void setValue(
				Integer value) 
			throws IllegalArgumentException {

			this.value = value;
		}

		public Integer getValue() {

			return value;
		}
	}
	
	public static class LongValue
		extends BaseIntegerValue
		implements PropertyValue {

		private Long value;
		
		private LongValue(
				TypeDefinitionIntegerImpl type,
				Long value) 
			throws IllegalArgumentException {
			
			setType(type);
			setValue(value);
		}
		
		public Long getValue() {

			return value;
		}
		
		private void setValue(
				Long value) 
			throws IllegalArgumentException {
			
			if ((getType().getIsSigned() == false) && (value < 0l))
				throw new IllegalArgumentException("Cannot set a negative value for an unsigned UInt64/long type.");
		
			this.value = value;
		}
	}

	public tv.amwa.maj.industry.PropertyValue createValueFromNumber(
			Number value)
		throws NullPointerException,
			IllegalArgumentException,
			BadSizeException {
		
		if (value == null)
			throw new NullPointerException("Cannot create an integer property value using a null value.");
		
		// System.out.println("value: " + value.longValue() + " minValue: " + minValue() + " maxValue: " + maxValue()); 
		
		if ((value.longValue() < minValue()) || (value.longValue() > maxValue()))
			throw new IllegalArgumentException("The given number is outside the acceptable range for a " + (isSigned ? "signed" : "unsigned") + " integer value of " + size + " byte(s).");
		
		switch (size) {
		
		case BYTE: 
			
			return new ByteValue(this, value.byteValue());
		case SHORT:
			return new ShortValue(this, value.shortValue());
		case INT:
			return new IntegerValue(this, value.intValue());
		case LONG:
			return new LongValue(this, value.longValue());
		default:
			throw new BadSizeException("Unexpectedly, the size in bytes of the representation of an integer type is not supported in this implementation.");
		}
	}

	/**
	 * <p>Returns the maximum value for an integer value of the size defined by this integer type
	 * definition when represented in Java.</p>
	 *
	 * @return Maximum value for an integer value of the size defined by this integer type
	 * definition when represented in Java.
	 */
	private long maxValue() {
		
		if (isSigned) {
			switch (size) {
			case BYTE: return (long) Byte.MAX_VALUE;
			case INT: return (long) Integer.MAX_VALUE;
			case SHORT: return (long) Short.MAX_VALUE;
			default:
			case LONG: return (long) Long.MAX_VALUE;
			}
		}
		else {
			switch (size) {
			case BYTE: return (long) 1<<8 - 1;
			case INT: return (long) 1<<32 - 1;
			case SHORT: return (long) 1<<16 - 1;
			default:
			case LONG: return (long) Long.MAX_VALUE;	
			}
		}
	}
	
	/**
	 * <p>Returns the minimum value for an integer value of the size defined by this integer
	 * type definition when represented in Java.</p>
	 *
	 * @return Minimum value for an integer value of the size defined by this integer
	 * type definition when represented in Java.
	 */
	private long minValue() {
		
		// if (isSigned == false) return 0l;
		
		switch (size) {
		case BYTE: return (long) Byte.MIN_VALUE;
		case INT: return (long) Integer.MIN_VALUE;
		case SHORT: return (long) Short.MIN_VALUE;
		default:
		case LONG: return (long) Long.MIN_VALUE;
		}
	}
	
	public Number getInteger(
			tv.amwa.maj.industry.PropertyValue integerProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (integerProperty == null)
			throw new NullPointerException("Cannot extract an integer value from a null integer property.");
		if (!(equals(integerProperty.getType())))
			throw new IllegalPropertyValueException("The given integer property does not match this kind of integer type definition.");
		
		return (Number) integerProperty.getValue();
	}

	@MediaProperty(uuid1 = 0x03010203, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Size",
			typeName = "UInt8",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x000F,
			symbol = "Size")
	public byte getSize() {

		return size;
	}

	@MediaPropertySetter("Size")
	public void setSize(byte intSize) 
		throws IllegalArgumentException {
		
		if ((intSize != BYTE) && (intSize != SHORT) && (intSize != INT) && (intSize != LONG))
			throw new IllegalArgumentException("This implementation does not support the given number of bytes per integer value.");

		this.size = intSize;
	}
	
	public final static byte initializeSize() {
		
		return 1;
	}
	
	public boolean isSigned() {

		return isSigned;
	}

	@MediaProperty(uuid1 = 0x03010203, uuid2 = (short) 0x0200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "IsSigned",
			typeName = "Boolean",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0010,
			symbol = "IsSigned")
	public boolean getIsSigned() {
		
		return isSigned;
	}
	
	@MediaPropertySetter("IsSigned")
	public void setIsSigned(
			boolean isSigned) {
		
		this.isSigned = isSigned;
	}
	
	public final static boolean initializeIsSigned() {
		
		return true;
	}
	
	public void setInteger(
			tv.amwa.maj.industry.PropertyValue integerProperty,
			Number value)
		throws NullPointerException,
			BadSizeException,
			IllegalPropertyValueException,
			IllegalArgumentException {

		if (integerProperty == null)
			throw new NullPointerException("Cannot set the integer value of a null property.");
		if (value == null)
			throw new NullPointerException("Cannot set the integer value of the given property using a null value.");
		if (!(equals(integerProperty.getType())))
			throw new IllegalPropertyValueException("The given integer property does not match this integer type definition.");
		
		if ((value.longValue() < minValue()) || (value.longValue() > maxValue()))
			throw new IllegalArgumentException("The given number is outside the acceptable range for a " + (isSigned ? "signed" : "unsigned") + " integer value of " + size + " bytes.");
		
		switch (size) {
		
		case BYTE:
			((ByteValue) integerProperty).setValue(value.byteValue());
			return;
		case SHORT:
			((ShortValue) integerProperty).setValue(value.shortValue());
			return;
		case INT:
			((IntegerValue) integerProperty).setValue(value.intValue());
			return;
		case LONG:
			((LongValue) integerProperty).setValue(value.longValue());
			return;
		default:
			throw new IllegalArgumentException("Unexpectedly, the size in bytes of the representation of an integer type is not supported in this implementation.");
		}
	}
	
	public TypeCategory getTypeCategory() {
		
		return TypeCategory.Int;
	}

	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {
		
		if (javaValue == null)
			throw new ClassCastException("Cannot cast a null value to an integer property value.");
		
		if (javaValue instanceof ByteOrder)
			javaValue = Short.valueOf(((ByteOrder) javaValue).getAAFByteOrderCode());
		
		if (javaValue instanceof String) {
			
			try {
				String stringValue = (String) javaValue;
				javaValue = Long.valueOf(stringValue);
			} 
			catch (NumberFormatException e) {
				throw new ClassCastException("The given string does not represent an appropriate " + getName() + " value.");
			}
		}
		
		if (javaValue instanceof PropertyValue) {
			PropertyValue value = (PropertyValue) javaValue;
			try {
				return createValueFromNumber((Number) value.getValue());
			} 
			catch (Exception e) {
				throw new ClassCastException("Problem creating an integer property value from the given property value: " + e.getMessage());
			}
		}
		
		if (!(javaValue instanceof Number))
			throw new ClassCastException("Cannot cast a non java.lang.Number value to an integer property value.");

		try {
			return createValueFromNumber((Number) javaValue);
		}
		catch (Exception bse) {
			throw new ClassCastException("Exception thrown when creating an integer value: " + 
					bse.getClass().getName() + ": " + bse.getMessage());
		}
	}
	
	@Override
	public final boolean deepEquals(Object o) {
		
		if (super.deepEquals(o) == false) return false;
		
		if (!(o instanceof tv.amwa.maj.meta.TypeDefinitionInteger)) return false;
		
		tv.amwa.maj.meta.TypeDefinitionInteger testDefinition =
			(tv.amwa.maj.meta.TypeDefinitionInteger) o;
		
		if (testDefinition.getSize() != size) return false;
		if (testDefinition.isSigned() != isSigned) return false;
		
		return true;
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
		throws NullPointerException,
			EndOfDataException {

		super.createFromBytes(buffer);
		
		if (buffer.remaining() < size)
			throw new EndOfDataException("Not enough bytes in the given buffer to read a " + getName() + " value.");
		
		switch (size) {
		
		case BYTE:
			return createValueFromNumber(buffer.get());
		case SHORT:
			return createValueFromNumber(buffer.getShort());
		case INT:
			return createValueFromNumber(buffer.getInt());
		case LONG:
			return createValueFromNumber(buffer.getLong());
		default:
			return null;
		}
	}
	
	@Override
	public List<PropertyValue> writeAsBytes(
			PropertyValue value,
			ByteBuffer buffer) 
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		super.writeAsBytes(value, buffer);
		
		if (buffer.remaining() < size)
			throw new InsufficientSpaceException("Insufficient space remaining in the given buffer to write a " + getName() + " value.");
		
		switch (size) {
		
		case BYTE:
			buffer.put(((ByteValue) value).getValue());	
			return null;
		case SHORT:
			buffer.putShort(((ShortValue) value).getValue());
			return null;
		case INT:
			buffer.putInt(((IntegerValue) value).getValue());
			return null;
		case LONG:
			buffer.putLong(((LongValue) value).getValue());
			return null;
		default:
			return null;	
		}
	}
	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.lengthAsBytes(value);
		
		return (long) size;
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionInteger");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
		
		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"Size", size);
		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"IsSigned", isSigned);
	}
	
	public TypeDefinitionInteger clone() {
		
		return (TypeDefinitionInteger) super.clone();
	}
}
