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
 * $Log: TypeDefinitionStreamImpl.java,v $
 * Revision 1.5  2011/07/27 17:41:46  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.4  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/26 11:50:11  vizigoth
 * Completed common method testing.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/11/08 16:39:50  vizigoth
 * Major rewrite to support new stream interface.
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
 * Revision 1.1  2007/11/13 22:13:18  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta.impl;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.DataSizeException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MemoryResidentStream;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.TypeDefinitionStream;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.util.Utilities;

/** 
 * <p>Implements the definition of a property type that is stored in a stream and has a 
 * value that consists of a varying number of the bytes.</p>
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x020c, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionStream",
		  description = "The TypeDefinitionStream class defines a property type that is stored in a stream and has a value that consists of a varying number of the bytes.",
		  symbol = "TypeDefinitionStream")
public final class TypeDefinitionStreamImpl 
	extends 
		SingletonTypeDefinitionImpl 
	implements 
		TypeDefinitionStream, 
		Serializable,
		Cloneable {
	
	/** <p></p> */
	private static final long serialVersionUID = -7778646178736728937L;
	
	protected TypeDefinitionStreamImpl() { }
	
	/**
	 * <p>Creates and initializes a new type definition for a streams, which defines a property type 
	 * that is stored in a stream and has a value that consists of a varying number of the bytes. The 
	 * order of the bytes is meaningful.</p>
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
	 * @param identification AUID to be used to identify this stream type.
	 * @param typeName Friendly name for this type definition.
	 * 
	 * @throws NullPointerException The identification argument is <code>null</code>.
	 */
	public TypeDefinitionStreamImpl(
			tv.amwa.maj.record.AUID identification,
			@AAFString String typeName)
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new stream type definition with a null identification.");
		
		setIdentification(identification);
		setName(typeName);
	}
	
	public static class StreamValue
		extends PropertyValueImpl
		implements PropertyValue {

		private TypeDefinitionStreamImpl type;
		private Stream value;
		private ByteOrder byteOrder = null;

		private StreamValue(
				TypeDefinitionStreamImpl type,
				Stream value) {

			this.type = type;
			setValue(value);
			byteOrder = null;
		}

		private StreamValue(
				TypeDefinitionStreamImpl type,
				Stream value,
				ByteOrder order) {

			this.type = type;
			setValue(value);
			byteOrder = order;
		}
		
		public TypeDefinitionImpl getType() {

			return type;
		}

		public Stream getValue() {

			return value;
		}

		public boolean isDefinedType() {

			return true;
		}

		private void setValue(
				Stream value) {

			this.value = value;
		}	
		
		public ByteOrder getByteOrder() {
			
			return byteOrder;
		}
	}
	
	public void append(
			tv.amwa.maj.industry.PropertyValue streamProperty,
			byte[] data)
		throws NullPointerException,
			IllegalPropertyValueException,
			DataSizeException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot append to a null stream property value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		if (data == null)
			throw new NullPointerException("Cannot append null data to a stream property value.");
		
		appendToStream(streamProperty, data);
	}
	
	private void appendToStream(
			PropertyValue streamProperty,
			byte[] data) 
		throws DataSizeException {
		
		Stream stream = (Stream) streamProperty.getValue();
		try {
			stream.write(ByteBuffer.wrap(data));
		}
		catch (Exception e) {
			throw new DataSizeException("Could not write data to the underlying stream because of a " + e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public void appendElements(
			tv.amwa.maj.industry.PropertyValue streamProperty,
			tv.amwa.maj.meta.TypeDefinition elementType,
			tv.amwa.maj.industry.PropertyValue[] data)
			throws NullPointerException,
				IllegalPropertyValueException,
				BadTypeException,
				DataSizeException {

		// TODO
		throw new UnsupportedOperationException("This operation is not supported in the current version of the API.");
	}

	public void clearStoredByteOrder(
			tv.amwa.maj.industry.PropertyValue streamProperty)
			throws NullPointerException,
				IllegalPropertyValueException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot change the stored byte order from a null stream property value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");

		((StreamValue) streamProperty.getValue()).byteOrder = null;
	}

	public long getPosition(
			tv.amwa.maj.industry.PropertyValue streamProperty)
		throws NullPointerException,
			IllegalPropertyValueException, 
			IOException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot find the current position of a null stream property value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		
		return ((StreamValue) streamProperty).getValue().getPosition();
	}

	public long getSize(
			tv.amwa.maj.industry.PropertyValue streamProperty)
		throws NullPointerException,
			IllegalPropertyValueException,
			IOException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot find the size of a null stream property value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");

		return ((StreamValue) streamProperty).getValue().getLength();
	}

	public ByteOrder getStoredByteOrder(
			tv.amwa.maj.industry.PropertyValue streamProperty)
			throws NullPointerException,
				IllegalPropertyValueException,
				PropertyNotPresentException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot retrieve the stored byte order from a null stream property value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");

		ByteOrder storedByteOrder = ((StreamValue) streamProperty.getValue()).byteOrder;
		if (storedByteOrder == null)
			throw new PropertyNotPresentException("The optional byte order is not stored with the stream value.");
		
		return storedByteOrder;
	}

	public boolean hasStoredByteOrder(
			tv.amwa.maj.industry.PropertyValue streamProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot retrieve the stored byte order from a null stream property value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		
		ByteOrder storedByteOrder = ((StreamValue) streamProperty.getValue()).byteOrder;
		return (storedByteOrder != null);
	}

	public byte[] read(
			tv.amwa.maj.industry.PropertyValue streamProperty,
			int dataSize)
		throws NullPointerException,
			IllegalArgumentException,
			EndOfDataException,
			IllegalPropertyValueException, 
			IOException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot read data from a null stream property value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");		
		if (dataSize < 0)
			throw new IllegalArgumentException("Cannot read a negative number of bytes from a stream property value.");		
		
		Stream stream = ((StreamValue) streamProperty).getValue();
		ByteBuffer buffer = stream.read(dataSize);
		return buffer.array();
	}

	public tv.amwa.maj.industry.PropertyValue[] readElements(
			tv.amwa.maj.industry.PropertyValue streamProperty,
			tv.amwa.maj.meta.TypeDefinition elementType,
			int numElements)
			throws NullPointerException,
				IllegalPropertyValueException,
				BadTypeException,
				EndOfDataException {

		// TODO
		throw new UnsupportedOperationException("This operation is not supported in the current version of the API.");
	}

	public void setPosition(
			tv.amwa.maj.industry.PropertyValue streamProperty,
			long newPosition)
		throws NullPointerException,
			IllegalArgumentException,
			IllegalPropertyValueException, 
			IOException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot set the buffer position for a null stream property value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");

		((StreamValue) streamProperty).getValue().setPosition(newPosition);
	}

	public void setSize(
			tv.amwa.maj.industry.PropertyValue streamProperty,
			long newSize)
		throws NullPointerException,
			IllegalArgumentException,
			IllegalPropertyValueException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot set the size of a null stream property value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		if (newSize < 0)
			throw new IllegalArgumentException("Cannot set the size of the given stream property value to a negative value.");
		
		if (streamProperty instanceof StreamValue) 
			throw new IllegalArgumentException("Cannot set the size for a stream.");
		
		// TODO not sure set size should be supported here?
		throw new IllegalArgumentException("This version of MAJ does not support setting the size of streams.");
	}

	public void setStoredByteOrder(
			tv.amwa.maj.industry.PropertyValue streamProperty,
			ByteOrder byteOrder)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot set the stored byte order of a null stream value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		
		((StreamValue) streamProperty.getValue()).byteOrder = byteOrder;
	}

	public void write(
			tv.amwa.maj.industry.PropertyValue streamProperty,
			byte[] data)
		throws NullPointerException,
			IllegalPropertyValueException,
			EndOfDataException, 
			IOException {

		if (streamProperty == null)
			throw new NullPointerException("Cannot write to a null stream value.");
		if (!(equals(streamProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		
		Stream stream = ((StreamValue) streamProperty).getValue();
		stream.write(ByteBuffer.wrap(data));
	}

	public void writeElements(
			tv.amwa.maj.industry.PropertyValue streamProperty,
			tv.amwa.maj.meta.TypeDefinition elementType,
			tv.amwa.maj.industry.PropertyValue[] data)
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException,
			EndOfDataException {

		// TODO
		throw new UnsupportedOperationException("This operation is not supported in the current version of the API.");
	}

	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		if (javaValue == null)
			throw new ClassCastException("Cannot create a new stream property value from a null value.");
		
		if (javaValue instanceof Stream)
			return new StreamValue(this, (Stream) javaValue);
		
		if (javaValue instanceof byte[]) {
			ByteBuffer wrappedBytes = ByteBuffer.wrap((byte[]) javaValue);
			MemoryResidentStream wrappedStream = new MemoryResidentStream(wrappedBytes);
			return new StreamValue(
					this, 
					wrappedStream,
					(wrappedBytes.order() == java.nio.ByteOrder.BIG_ENDIAN) ? ByteOrder.Big : ByteOrder.Little);
		}

		if (javaValue instanceof ByteBuffer) {
			ByteBuffer wrappedBytes = (ByteBuffer) javaValue;
			MemoryResidentStream wrappedStream = new MemoryResidentStream(wrappedBytes);
			return new StreamValue(
					this, 
					wrappedStream,
					(wrappedBytes.order() == java.nio.ByteOrder.BIG_ENDIAN) ? ByteOrder.Big : ByteOrder.Little);			
		}
		
		if (javaValue instanceof String) { // Assume a hex string representation
			try {
				byte[] bytes = Utilities.hexStringToBytes((String) javaValue);
				ByteBuffer wrappedBytes = ByteBuffer.wrap(bytes);
				MemoryResidentStream wrappedStream = new MemoryResidentStream(wrappedBytes);
				return new StreamValue(
						this, 
						wrappedStream,
						(wrappedBytes.order() == java.nio.ByteOrder.BIG_ENDIAN) ? ByteOrder.Big : ByteOrder.Little);
			}
			catch (Exception e) {
				throw new ClassCastException("Cannot convert the given hex string to a stream property value.");
			}
		}
		
		throw new ClassCastException("Cannot cast the given value to the value of this stream data type.");
	}

	@Override
	public TypeCategory getTypeCategory() {

		return TypeCategory.Stream;
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) {

		return createValue(buffer);
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionStream");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
	}
	
	public TypeDefinitionStream clone() {
		
		return (TypeDefinitionStream) super.clone();
	}
}
