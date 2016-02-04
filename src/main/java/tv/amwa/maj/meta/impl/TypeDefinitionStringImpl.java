/*
 * Copyright 2016 Advanced Media Workflow Assocation
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
 * $Log: TypeDefinitionStringImpl.java,v $
 * Revision 1.6  2011/07/27 17:41:46  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.5  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/26 11:50:11  vizigoth
 * Completed common method testing.
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
 * Revision 1.4  2010/03/19 16:13:53  vizigoth
 * Added methods for writing bytes and calculating lengths.
 *
 * Revision 1.3  2010/01/19 14:42:21  vizigoth
 * String values in byte buffers that are padded with null characters (\u0000) at the end are now truncated to only contain the characters in use.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.7  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.6  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.5  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.4  2008/01/14 20:55:21  vizigoth
 * Change to type category enumeration element names.
 *
 * Revision 1.3  2007/12/04 09:45:45  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.2  2007/11/15 12:52:48  vizigoth
 * Edits to ensure source can make rough and ready javadoc.
 *
 * Revision 1.1  2007/11/13 22:13:28  vizigoth
 * Public release of MAJ API.
 */

/**
 * 
 */
package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.BadSizeException;
import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionCharacter;
import tv.amwa.maj.meta.TypeDefinitionInteger;
import tv.amwa.maj.meta.TypeDefinitionString;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements the definition of a property type that consists,
 * effectively, of an array of the underlying character or integer type. In the
 * MAJ API, all strings are represented as {@linkplain java.lang.String Java strings}.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x020b, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionString",
		  description = "The TypeDefinitionString class defines a property type that consists of a zero-terminated array of the underlying character or integer type.",
		  symbol = "TypeDefinitionString")
public final class TypeDefinitionStringImpl 
	extends 
		SingletonTypeDefinitionImpl 
	implements 
		TypeDefinitionString,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -362492942547562578L;
	
	private WeakReference<TypeDefinition> elementType;
	private transient Charset charSet = Charset.forName("UTF-16");
	
	protected TypeDefinitionStringImpl() { }
	
	/**
	 * <p>Creates and initializes the string type definition with its identity and the
	 * underlying data representation. Acceptable underlying type definitions 
	 * are:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionInteger TypeDefinitionInteger}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionCharacter TypeDefinitionCharacter}</li>
	 * </ul>
	 * 
	 * <p>It is anticipated that most Java versions of this interface will be
	 * choose {@link TypeDefinitionCharacterImpl} and will use Java string handling
	 * in their implementation. However, by using a call to {@link #setCharacterSet(String)}
	 * it is possible to define mappings between different character sets to and 
	 * from Java strings.</p>
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
	 * @param identification AUID to be used to identify this type.
	 * @param typeName Friendly name for the string data type.
	 * @param typeDefinition Type of each element to be contained in the array.
	 * 
	 * @throws NullPointerException The identification and/or type definition arguments is/are
	 * <code>null</code>.
	 * @throws IllegalArgumentException The given type is not an acceptable underlying
	 * type for a string type definition.
	 */
	public TypeDefinitionStringImpl(
			AUID identification,
			@AAFString String typeName,
			TypeDefinition typeDefinition) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new string type definition with a null identification.");
		if (typeDefinition == null)
			throw new NullPointerException("Cannot create a new string type definition with a null element type.");
		
		setIdentification(identification);
		setName(typeName);
		setElementType(typeDefinition);
	}
	
	/** 
	 * <p>Base representation for all property values representing string values.</p>
	 *
	 */
	public static abstract class BaseStringValue
		extends PropertyValueImpl
		implements PropertyValue {
		
		private TypeDefinitionStringImpl type;
		
		protected void setType(
				TypeDefinitionStringImpl type) {
			
			this.type = type;
		}

		public TypeDefinitionStringImpl getType() {
			
			return type;
		}
		
		public boolean isDefinedType() {
			
			return true;
		}
		
		public boolean equals(Object o) {
			
			if (o == null) return false;
			if (this == o) return true;
			if (o instanceof PropertyValue)
				return getValue().equals(((PropertyValue) o).getValue());
			else
				return getValue().equals(o);
		}
	}
	
	/** 
	 * <p>Property value for strings with an underlying element type of 
	 * {@link TypeDefinitionCharacterImpl}. These are backed in Java by a 
	 * {@link java.lang.String} value.</p>
	 *
	 */
	public static class CharacterStringValue
		extends BaseStringValue
		implements PropertyValue {

		private String value;
		
		/**
		 * <p>Create a new character-backed string property value.</p>
		 *
		 * @param type Type definition for the property value.
		 * @param value Value of the property.
		 */
		private CharacterStringValue(
				TypeDefinitionStringImpl type,
				String value) {
			
			setType(type);
			setValue(value);
		}
		
		/** 
		 * @see tv.amwa.maj.industry.PropertyValue#getValue()
		 */
		public String getValue() {
			
			return value;
		}
		
		/**
		 * <p>Set the value of this character-backed string property value.</p>
		 *
		 * @param value Value of this character-backed string property value.
		 */
		private void setValue(
				String value) {
			
			this.value = value;
		}
	}
	
	/** 
	 * <p>Property value for strings with an underlying element type of 
	 * {@link TypeDefinitionIntegerImpl}. These are backed in Java by a 
	 * byte array value.</p>
	 *
	 */
	public static class ByteArrayStringValue
		extends BaseStringValue
		implements PropertyValue {

		private byte[] value;
		private ByteOrder order;
		
		/**
		 * <p>Create a new byte-array-backed string property value.</p>
		 *
		 * @param type Type definition for this property value.
		 * @param value Value of the property.
		 * @param order Byte order used to encode the data as a byte array.
		 */
		private ByteArrayStringValue(
				TypeDefinitionStringImpl type,
				byte[] value,
				ByteOrder order) {
			
			setType(type);
			setValue(value);
			
			this.order = order;
		}
		
		/** 
		 * @see tv.amwa.maj.industry.PropertyValue#getValue()
		 */
		public byte[] getValue() {

			return value;
		}
		
		/**
		 * <p>Set the value of this byte-array-backed string property value.</p>
		 *
		 * @param value Value of this byte-array-backed string property value.
		 */
		private void setValue(
				byte[] value) {
			
			this.value = value;
		}

		/**
		 * <p>Returns the byte order of the encoded string data.</p>
		 *
		 * @return Byte order of the encoded string data.
		 */
		public ByteOrder getByteOrder() {

			return order;
		}
	}
	
	public void appendElements(
			tv.amwa.maj.industry.PropertyValue stringProperty,
			tv.amwa.maj.industry.PropertyValue[] elements)
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException, 
			BadSizeException {

		if (stringProperty == null)
			throw new NullPointerException("Cannot append to the value of a null string property.");
		
		if (!(equals(stringProperty.getType())))
			throw new IllegalPropertyValueException("Cannot append to a string property value with a type does not match the type of this type definition.");

		if (elements == null) return;
		
		for ( PropertyValue property : elements ) {
			if (!(elementType.equals(property.getType())))
				throw new BadTypeException("The given array of property values contains at least one element of a type that does not match the underlying element type of this string type definition.");
		}
		
		if (elementType.getTarget() instanceof TypeDefinitionCharacter) {
			StringBuffer backToStringLike = new StringBuffer();
			for ( PropertyValue property : elements ) 
				backToStringLike.append((Character) property.getValue());
			appendString(stringProperty, backToStringLike.toString());
			return;
		}
		
		TypeDefinitionInteger integerType = (TypeDefinitionInteger) elementType.getTarget();
		
		switch (integerType.getSize()) {
		
		case TypeDefinitionIntegerImpl.BYTE:
			ByteBuffer bytes = ByteBuffer.allocate(elements.length);
			bytes.order(((ByteArrayStringValue) stringProperty).getByteOrder());
			for ( int x = 0 ; x < elements.length ; x++ )
				bytes.put((Byte) elements[x].getValue());
			appendElements(stringProperty, bytes);
			break;
			
		case TypeDefinitionIntegerImpl.SHORT:
			ShortBuffer shorts = ShortBuffer.allocate(elements.length);
			for ( int x = 0 ; x < elements.length ; x++ )
				shorts.put((Byte) elements[x].getValue());
			appendElements(stringProperty, shorts);
			break;
			
		case TypeDefinitionIntegerImpl.INT:
			IntBuffer ints = IntBuffer.allocate(elements.length);
			for ( int x = 0 ; x < elements.length ; x++ )
				ints.put((Integer) elements[x].getValue());
			appendElements(stringProperty, ints);
			break;
			
		case TypeDefinitionIntegerImpl.LONG:
			LongBuffer longs = LongBuffer.allocate(elements.length);
			for ( int x = 0 ; x < elements.length ; x++ )
				longs.put((Long) elements[x].getValue());
			appendElements(stringProperty, longs);
			break;
			
		default:
			throw new BadSizeException("Unexpextedly, the number of bytes per integer of the underlying element type of this string type definition is not supported by this implementation.");
		}
	}

	public void appendElements(
			tv.amwa.maj.industry.PropertyValue stringProperty,
			Buffer elements)
		throws NullPointerException,
			IllegalArgumentException,
			IllegalPropertyValueException {

		if (stringProperty == null)
			throw new NullPointerException("Cannot append to the value of a null string property.");
		
		if (!(equals(stringProperty.getType())))
			throw new IllegalPropertyValueException("Cannot append to a string property value with a type that does not match this type definition.");
		
		if (elements == null) return;
		if (stringProperty.getValue() == null) {
			setString(stringProperty, elements);
			return;
		}
		
		ByteBuffer buffer = convertBuffer(elements);
		
		if (elementType.getTarget() instanceof TypeDefinitionCharacter) {
			CharacterStringValue existing = (CharacterStringValue) stringProperty;
			StringBuffer extended = new StringBuffer(existing.getValue());
			extended.append(charSet.decode(buffer));
			existing.setValue(extended.toString());
			return;
		}
		
		ByteBuffer extensionBuffer = convertBuffer(elements);
		ByteArrayStringValue byteArrayValue = (ByteArrayStringValue) stringProperty;
		ByteBuffer existingBuffer = ByteBuffer.wrap(byteArrayValue.getValue());
		existingBuffer.order(byteArrayValue.getByteOrder());
		
		ByteBuffer extendedBuffer = 
			ByteBuffer.allocate(existingBuffer.capacity() + existingBuffer.capacity());
		extendedBuffer.put(existingBuffer);
		extendedBuffer.put(extensionBuffer);
		
		byteArrayValue.setValue(extendedBuffer.array());
	}

	public int getCount(
			tv.amwa.maj.industry.PropertyValue stringProperty)
		throws NullPointerException, 
			IllegalPropertyValueException {

		String countMe = getString(stringProperty);
		if (countMe == null)
			return 0;
		else
			return getString(stringProperty).length();
	}

	public Buffer getElements(
			tv.amwa.maj.industry.PropertyValue stringProperty)
		throws NullPointerException, 
			IllegalPropertyValueException,
			BadSizeException {

		if (stringProperty == null)
			throw new NullPointerException("Cannot retrieve the value of a null string property.");
		if (!(equals(stringProperty.getType())))
			throw new IllegalPropertyValueException("Cannot retrieve the value of a string property with a different type definition to this one.");
		
		if (stringProperty.getValue() == null) return null;
		
		if (elementType.getTarget() instanceof TypeDefinitionCharacter) {
			String stringValue = (String) stringProperty.getValue();
			return CharBuffer.wrap(stringValue).asReadOnlyBuffer();
		}
		
		ByteArrayStringValue byteStringValue = (ByteArrayStringValue) stringProperty;
		ByteBuffer bytes = ByteBuffer.wrap(byteStringValue.getValue());
		bytes.order(byteStringValue.getByteOrder());
		
		switch (((TypeDefinitionInteger) elementType.getTarget()).getSize()) {
			
		case TypeDefinitionIntegerImpl.BYTE:
			return bytes.asReadOnlyBuffer();
		case TypeDefinitionIntegerImpl.SHORT:
			return bytes.asShortBuffer().asReadOnlyBuffer();
		case TypeDefinitionIntegerImpl.INT:
			return bytes.asIntBuffer().asReadOnlyBuffer();
		case TypeDefinitionIntegerImpl.LONG:
			return bytes.asLongBuffer().asReadOnlyBuffer();
		default:
			throw new BadSizeException("Unexpextedly, the number of bytes per integer of the underlying element type of this string type definition is not supported by this implementation.");
		}
	}

	public String getString(
			tv.amwa.maj.industry.PropertyValue stringProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (stringProperty == null)
			throw new NullPointerException("Cannot extract a Java string value from a null string property.");
		if (!(equals(stringProperty.getType())))
			throw new IllegalPropertyValueException("Cannot extract a Java string value from a string with a different type definition to this one.");
		
		if (elementType.getTarget() instanceof TypeDefinitionCharacterImpl) 
			return ((CharacterStringValue) stringProperty).getValue();
		
		ByteArrayStringValue byteArrayValue = (ByteArrayStringValue) stringProperty;
		ByteBuffer bytes = ByteBuffer.wrap(byteArrayValue.getValue());
		bytes.order(byteArrayValue.getByteOrder());
		
		return charSet.decode(bytes).toString();
	}

	public tv.amwa.maj.industry.PropertyValue[] getPropertyValues(
			tv.amwa.maj.industry.PropertyValue stringProperty)
		throws NullPointerException,
			IllegalPropertyValueException,
			BadSizeException {

		if (stringProperty == null)
			throw new NullPointerException("Cannot extract a Java string value from a null string property.");
		if (!(equals(stringProperty.getType())))
			throw new IllegalPropertyValueException("Cannot extract a Java string value from a string with a different type definition to this one.");
		
		if (stringProperty.getValue() == null)
			return null;
		
		if (elementType.getTarget() instanceof TypeDefinitionCharacter) {
			TypeDefinitionCharacter characterType = (TypeDefinitionCharacter) elementType.getTarget();
			String stringValue = ((CharacterStringValue) stringProperty).getValue();
			PropertyValue[] stringAsArray = new PropertyValue[stringValue.length()];
			for ( int x = 0 ; x < stringAsArray.length ; x++ )
				stringAsArray[x] = characterType.createValueFromCharacter(stringValue.charAt(x));
			return stringAsArray;
		}
		
		TypeDefinitionInteger integerType = (TypeDefinitionInteger) elementType.getTarget();
		ByteArrayStringValue byteArrayValue = (ByteArrayStringValue) stringProperty;
		ByteBuffer bytes = ByteBuffer.wrap(byteArrayValue.getValue());
		bytes.order(byteArrayValue.getByteOrder());
		
		PropertyValue[] propertyArray;
		
		switch (integerType.getSize()) {
		
		case TypeDefinitionIntegerImpl.BYTE:
			propertyArray = new PropertyValue[bytes.capacity()];
			for ( int x = 0 ; x < propertyArray.length ; x++ )
				propertyArray[x] = integerType.createValue(bytes.get());
			return propertyArray;
		case TypeDefinitionIntegerImpl.SHORT:
			ShortBuffer shorts = bytes.asShortBuffer();
			propertyArray = new PropertyValue[shorts.capacity()];
			for ( int x = 0 ; x < propertyArray.length ; x++ )
				propertyArray[x] = integerType.createValue(shorts.get());
			return propertyArray;
		case TypeDefinitionIntegerImpl.INT:
			IntBuffer ints = bytes.asIntBuffer();
			propertyArray = new PropertyValue[ints.capacity()];
			for ( int x = 0 ; x < propertyArray.length ; x++ )
				propertyArray[x] = integerType.createValue(ints.get());
			return propertyArray;
		case TypeDefinitionIntegerImpl.LONG:
			LongBuffer longs = bytes.asLongBuffer();
			propertyArray = new PropertyValue[longs.capacity()];
			for ( int x = 0 ; x < propertyArray.length ; x++) 
				propertyArray[x] = integerType.createValue(longs.get());
			return propertyArray;
		default:
			throw new BadSizeException("Unexpextedly, the number of bytes per integer of the underlying element type of this string type definition is not supported by this implementation.");
		}
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0f00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "StringElementType",
			aliases = { "ElementType" },
			typeName = "TypeDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x001B,
			symbol = "StringElementType")
	public TypeDefinition getElementType() {

		return elementType.getTarget();
	}

	/**
	 * <p>Set the underlying array element type of this string type definition. The element type 
	 * must either be a {@link tv.amwa.maj.meta.TypeDefinitionInteger integer type definition} or
	 * a {@link tv.amwa.maj.meta.TypeDefinitionCharacter character type definition},
	 * otherwise a {@link IllegalArgumentException} will be thrown.</p>
	 *
	 * @param type Underlying array element type of this string type definition.  
	 * @throws NullPointerException Argument is null.
	 * @throws IllegalArgumentException The given type definition is not of integer or character
	 * type.
	 */
	@MediaPropertySetter("StringElementType")
	public void setElementType(
			TypeDefinition type) 
		throws NullPointerException, 
			IllegalArgumentException {
		
		if (type == null)
			throw new NullPointerException("Cannot set the element type for this type definition to a null value.");
		
		if ((!(type instanceof TypeDefinitionCharacter)) && 
				(!(type instanceof TypeDefinitionInteger)))
			throw new IllegalArgumentException("Cannot create a new string type definition with an underlying element type other than character or integer.");

		this.elementType = new WeakReference<TypeDefinition>(type);
	}
	
	public final static TypeDefinition initializeStringElementType() {
		
		return TypeDefinitions.Character;
	}
	
	public void appendString(
			PropertyValue stringProperty,
			java.lang.String extension)
		throws NullPointerException, 
			IllegalPropertyValueException {

		if (stringProperty == null)
			throw new NullPointerException("Cannot append to the value of a null string property.");
		
		if (!(equals(stringProperty.getType())))
			throw new IllegalPropertyValueException("Cannot append to a string property value with a type that does not match this type definition.");
		
		if (extension == null) return;
		if (stringProperty.getValue() == null) {
			setString(stringProperty, extension);
			return;
		}
		
		if (elementType.getTarget() instanceof TypeDefinitionCharacter) {
			String existing = (String) stringProperty.getValue();
			((CharacterStringValue) stringProperty).setValue(existing + extension);
			return;
		}
		
		ByteBuffer bytes = charSet.encode(extension);
		appendElements(stringProperty, bytes);
	}

	public PropertyValue createValueFromString(
			String initData) {

		if (elementType.getTarget() instanceof TypeDefinitionCharacter)
			return new CharacterStringValue(this, initData);
		
		// Must be backed by a byte buffer.
		if (initData == null)
			return new ByteArrayStringValue(this, null, ByteOrder.BIG_ENDIAN);
		
		byte[] stringData;
		stringData = charSet.encode(initData).array();
		return new ByteArrayStringValue(this, stringData, ByteOrder.nativeOrder());
	}

	public PropertyValue createValueFromString(
			Buffer initData) 
		throws IllegalArgumentException {

		ByteBuffer buffer = convertBuffer(initData);
		
		if (elementType.getTarget() instanceof TypeDefinitionCharacter) {
			if (buffer == null) 
				return new CharacterStringValue(this, null);
			String stringData = charSet.decode(buffer).toString();
			return new CharacterStringValue(this, stringData);
		}
			
		// Must be byte array backed if we are here
		if (buffer == null)
			return new ByteArrayStringValue(this, null, ByteOrder.nativeOrder());

		return new ByteArrayStringValue(this, buffer.array(), buffer.order());
	}

	/**
	 * <p>Convert the given data buffer into a {@link java.nio.ByteBuffer byte buffer}, 
	 * carrying out any byte order conversions required.</p>
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
	 * @param buffer Buffer of data to convert into byte buffer.
	 * @return Given data buffer converted to a byte buffer.
	 * 
	 * @throws IllegalArgumentException The given buffer must be of an appropriate type for 
	 * conversion to the underlying element type for this string type definition.
	 */
	private ByteBuffer convertBuffer(
			Buffer buffer) 
		throws IllegalArgumentException {
		
		if (buffer == null) return null;
		
		if (buffer instanceof ByteBuffer)
			return (ByteBuffer) buffer.position(0);

		ByteBuffer convertedBytes;
		
		if (buffer instanceof ShortBuffer) {
			ShortBuffer shorts = (ShortBuffer) buffer;
			convertedBytes = ByteBuffer.allocate(shorts.capacity() * 2);
			convertedBytes.order(shorts.order());
			for ( ; shorts.hasRemaining() ; )
				convertedBytes.putShort(shorts.get());
			convertedBytes.position(0);
			return convertedBytes;
		}
		
		if (buffer instanceof IntBuffer) {
			IntBuffer ints = (IntBuffer) buffer;
			convertedBytes = ByteBuffer.allocate(ints.capacity() * 4);
			convertedBytes.order(ints.order());
			for ( ; ints.hasRemaining() ; )
				convertedBytes.putInt(ints.get());
			convertedBytes.position(0);
			return convertedBytes;
		}
		
		if (buffer instanceof LongBuffer) {
			LongBuffer longs = (LongBuffer) buffer;
			convertedBytes = ByteBuffer.allocate(longs.capacity() * 8);
			convertedBytes.order(longs.order());
			for ( ; longs.hasRemaining() ; )
				convertedBytes.putLong(longs.get());
			convertedBytes.position(0);
			return convertedBytes;
		}
		
		if (buffer instanceof CharBuffer) {
			CharBuffer chars = (CharBuffer) buffer;
			convertedBytes = ByteBuffer.allocate(chars.capacity() * 2);
			convertedBytes.order(chars.order());
			for ( ; chars.hasRemaining() ; )
				convertedBytes.putChar(chars.get());
			convertedBytes.position(0);
			return convertedBytes;
		}

		throw new IllegalArgumentException("The given buffer must be of an appropriate type for conversion to the underlying element type for this string type definition.");
		
	}
	
	public java.lang.String getCharacterSet() {

		return charSet.toString();
	}

	public void setCharacterSet(
			java.lang.String charSet)
		throws NullPointerException,
			IllegalCharsetNameException {

		if (charSet == null)
			throw new NullPointerException("Cannot set the character set for this string type definition using a null character set name.");
		
		try {
			this.charSet = Charset.forName(charSet);
		}
		catch (IllegalCharsetNameException uce) {
			throw new IllegalCharsetNameException("The requested character set is not supported on this platform for this string type definition.");
		}
		
	}

	public void setString(
			PropertyValue stringProperty,
			String data)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (stringProperty == null)
			throw new NullPointerException("Cannot set the value of a null string property.");
		
		if (!(equals(stringProperty.getType())))
			throw new IllegalPropertyValueException("The given property value does not match this string type definition.");

		
		if (elementType.getTarget() instanceof TypeDefinitionCharacter) {
			((CharacterStringValue) stringProperty).setValue(data);
			return;
		}
			
		ByteArrayStringValue byteStringValue = (ByteArrayStringValue) stringProperty; 
		
		if (data == null) {
			byteStringValue.setValue(null);
		}
		else {
			ByteBuffer buffer = charSet.encode(data);
			byteStringValue.setValue(buffer.array());
		}
	}

	public void setString(
			PropertyValue stringProperty,
			Buffer data)
		throws NullPointerException, 
			IllegalPropertyValueException,
			IllegalArgumentException {
		
		if (stringProperty == null)
			throw new NullPointerException("Cannot append to the value of a null string property.");
		
		if (!(equals(stringProperty.getType())))
			throw new IllegalPropertyValueException("Cannot append to the value of the given string property as its type does not match this type definition.");
		
		if (data == null) {
			if (stringProperty instanceof CharacterStringValue)
				((CharacterStringValue) stringProperty).setValue(null);
			else
				((ByteArrayStringValue) stringProperty).setValue(null);
			return;
		}	
		
		ByteBuffer buffer = convertBuffer(data);
		
		if (elementType.getTarget() instanceof TypeDefinitionCharacter) {
			String stringValue = charSet.decode(buffer).toString();
			((CharacterStringValue) stringProperty).setValue(stringValue);
			return;
		}
	
		ByteArrayStringValue existing = (ByteArrayStringValue) stringProperty;
		buffer.order(existing.getByteOrder());
		((ByteArrayStringValue) stringProperty).setValue(buffer.array());
	}

	public void appendString(
			PropertyValue stringProperty,
			Buffer extension)
		throws NullPointerException, 
			IllegalArgumentException, 
			IllegalPropertyValueException {

		appendElements(stringProperty, extension);
	}	

	public TypeCategory getTypeCategory() {
		
		return TypeCategory.String;
	}

	
	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		if (javaValue instanceof CharSequence)
			return createValueFromString(((CharSequence) javaValue).toString());

		try {
			if (javaValue instanceof Buffer)
				return createValueFromString((Buffer) javaValue);
		} 
		catch (IllegalArgumentException e) {
			throw new ClassCastException("Illegal argument exception thrown when creating a string property value: " + e.getMessage());
		}
		
		if (javaValue instanceof byte[])
			return createValueFromString(ByteBuffer.wrap((byte[]) javaValue));

		throw new ClassCastException("Cannot create a new string property value from the given java value.");
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) {

		// TODO make this truly UTF-16 rather than 2-byte
		char[] characters = new char[buffer.remaining() / 2];
		for ( int u = 0 ; u < characters.length ; u++ )
			characters[u] = buffer.getChar();
		
		String checkForNulls = new String(characters);
		int firstNull = checkForNulls.indexOf('\u0000');
		if (firstNull != -1)
			checkForNulls = checkForNulls.substring(0, firstNull);
		
		return createValue(checkForNulls);
	}
	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.lengthAsBytes(value);
		
		return (2 * (((String) value.getValue())).length() + 2);
	}
	
	@Override
	public List<PropertyValue> writeAsBytes(
			PropertyValue value,
			ByteBuffer buffer) 
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		super.writeAsBytes(value, buffer);
		
		String toWrite = (String) value.getValue();
		if (buffer.remaining() < (2 * toWrite.length() + 2))
			throw new InsufficientSpaceException("Not enough remaining space in the given buffer to write a string value of length " + 
					toWrite.length() + ".");
		
		char[] characters = toWrite.toCharArray();
		for ( char c : characters)
			buffer.putChar(c);
		buffer.putChar('\u0000');
		
		return null;
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionString");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
		
		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"ElementType", elementType.getTarget().getName());
	}
	
	public TypeDefinitionString clone() {
		
		return (TypeDefinitionString) super.clone();
	}
}
