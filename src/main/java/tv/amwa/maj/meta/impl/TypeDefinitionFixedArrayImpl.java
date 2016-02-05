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
 * $Log: TypeDefinitionFixedArrayImpl.java,v $
 * Revision 1.6  2011/07/27 17:39:47  vizigoth
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
 * Revision 1.4  2010/11/18 10:49:53  vizigoth
 * Added support for dynamic meta dictionaries and type name mapping for legacy meta dictionary compatibility.
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
 * Revision 1.3  2008/01/14 20:55:21  vizigoth
 * Change to type category enumeration element names.
 *
 * Revision 1.2  2007/12/04 09:45:45  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:13:17  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionFixedArray;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements the definition of a property type that has a fixed number of values of the underlying type. 
 * The order of the values is meaningful.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0208, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionFixedArray",
		  description = "The TypeDefinitionFixedArray class defines a property type that has a fixed number of values of the underlying type. The order of the values is meaningful.",
		  symbol = "TypeDefinitionFixedArray")
public final class TypeDefinitionFixedArrayImpl 
	extends 
		SingletonTypeDefinitionImpl 
	implements 
		TypeDefinitionFixedArray,
		Serializable,
		Cloneable {
	
	/** <p></p> */
	private static final long serialVersionUID = 2221149495149931508L;

	private int elementCount;
	private WeakReference<TypeDefinition> elementType;
	
	protected TypeDefinitionFixedArrayImpl() { }
	
	/**
	 * <p>Creates and initializes a new fixed array type definition, which defines a property 
	 * type that has a fixed number of values of the underlying type. The order of the values 
	 * is meaningful.</p>
	 * 
	 * <p>The following types are allowed as the underlying type for 
	 * fixed array type definitions:</p>
	 * 
	 * <ul>
	 * 	<li>{@link tv.amwa.maj.meta.TypeDefinitionInteger TypeDefinitionInteger}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionRecord TypeDefinitionRecord}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionEnumeration TypeDefinitionEnumeration}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration TypeDefinitionExtendibleEnumeration}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionFixedArray TypeDefinitionFixedArray}</li>
	 * </ul>
	 * 
	 * <p>If the element type is not one of the above kinds, an {@link IllegalArgumentException} is
	 * thrown.</p>
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
	 * @param elementType Type of each element to be contained in an array defined
	 * by the fixed array type definition.
	 * @param numberElements Number of elements of this kind of array.
	 * @param typeName Friendly name of the fixed array type definition.
	 * 
	 * @throws NullPointerException One or more of the arguments is null.
	 * @throws IllegalArgumentException The given underlying type is not compatible
	 * with the types supported by fixed size arrays.
	 */
	public TypeDefinitionFixedArrayImpl(
			AUID identification,
			@AAFString String typeName,
			TypeDefinition elementType,
			@UInt32 int numberElements) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new fixed array type definition using a null identification value.");
		if (elementType == null)
			throw new NullPointerException("Cannot create a new fixed array type definition using a null element type.");
		if (numberElements < 0)
			throw new IllegalArgumentException("Cannot create a new fixed array type definition with a negative number of elements.");
		
		setIdentification(identification);
		setName(typeName);
		setElementType(elementType);
		setElementCount(numberElements);
	}
	
	public static class FixedArrayValue
		extends PropertyValueImpl
		implements PropertyValue {

		private TypeDefinitionFixedArrayImpl type;
		private Object[] value;
		
		private FixedArrayValue(
				TypeDefinitionFixedArrayImpl type,
				Object[] value) {
			
			this.type = type;
			this.value = value;
		}
		
		public tv.amwa.maj.meta.TypeDefinition getType() {

			return type;
		}

		public Object[] getValue() {

			return value;
		}

		public boolean isDefinedType() {

			return true;
		}
		
		private void setValue(
				Object[] value) {
			
			this.value = value;
		}
		
		public boolean equals(
				Object o) {
			
			if (o == null) return false;
			if (o == this) return true;
			if (!(o instanceof PropertyValue)) return false;
			
			PropertyValue testValue = (PropertyValue) o;
			if (!(type.equals(testValue.getType()))) return false;
			if ((value == null) && (testValue.getValue() == null)) return true;
			return Arrays.equals(value, ((FixedArrayValue) testValue).getValue());
		}
		
		public int hashCode() {
			
			if (value == null)
				return type.hashCode();
			else
				return type.hashCode() ^ Arrays.hashCode(value);
		}
	}

	public tv.amwa.maj.industry.PropertyValue createValueFromValues(
			tv.amwa.maj.industry.PropertyValue[] elementValues)
		throws NullPointerException, 
			BadParameterException,
			BadTypeException {

		if (elementValues == null)
			throw new NullPointerException("Cannot create a new fixed size array property value from a null array of element values.");
		
		if (elementValues.length != getCount())
			throw new BadParameterException("Cannot create a new fixed size array property value from an array of elements of a different length.");
		
		for ( PropertyValue value : elementValues ) 
			if (!(elementType.getTarget().equals(value.getType())))
				throw new BadTypeException("Cannot create a new fixed size array property value when at least one member of the given element values array is of a type that is not compatable with the element type of this type definition.");
		
		Object[] values = new Object[elementCount];
		
		for ( int u = 0 ; u < elementCount ; u++ ) 
			values[u] = elementValues[u].getValue();
		
		return new FixedArrayValue(this, values);
	}

	@MediaProperty(uuid1 = 0x03010203, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ElementCount",
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0018,
			symbol = "ElementCount")
	public int getCount() {

		return elementCount;
	}

	/**
	 * <p>Set the element count of this fixed array type definition.</p>
	 *
	 * @param elementCount Element count of this fixed array type definition.
	 * 
	 * @throws IllegalArgumentException Cannot set the element count of a fixed 
	 * array type definition to a negative value.
	 */
	@MediaPropertySetter("ElementCount")
	public void setElementCount(int elementCount)
		throws IllegalArgumentException {
		
		if (elementCount < 0)
			throw new IllegalArgumentException("Cannot set the element count of a fixed array type definition to a negative value.");

		this.elementCount = elementCount;
	}
	
	public final static int initializeElementCount() {
		
		return 2;
	}
	
	public tv.amwa.maj.industry.PropertyValue getElementValue(
			tv.amwa.maj.industry.PropertyValue arrayProperty, 
			int index)
		throws NullPointerException,
			IndexOutOfBoundsException,
			IllegalPropertyValueException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot retrieve an element from a null fixed size array property.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given fixed array property type does not match this fixed size array property.");
		
		try {
			Object[] values = ((FixedArrayValue) arrayProperty).getValue();

			return elementType.getTarget().createValue(values[index]);
 		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for this fixed sized array, which has a length of " + getCount() + ".");
		}
	}

	public List<tv.amwa.maj.industry.PropertyValue> getElements(
			tv.amwa.maj.industry.PropertyValue arrayProperty)
		throws NullPointerException, 
			IllegalPropertyValueException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot retrieve elements from a null fixed size array property.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given fixed array property type does not match this fixed size array property.");
		
		List<tv.amwa.maj.industry.PropertyValue> valueList = 
			new Vector<tv.amwa.maj.industry.PropertyValue>();
		
		Object[] values = ((FixedArrayValue) arrayProperty).getValue();
		for ( Object value : values) 
			valueList.add(elementType.getTarget().createValue(value));
		
		return valueList;
	}

	public Object[] getArray(
			tv.amwa.maj.industry.PropertyValue arrayProperty)
		throws NullPointerException, 
			IllegalPropertyValueException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot retrieve elements from a null fixed size array property.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given fixed array property type does not match this fixed size array property.");
		
		Object[] values = ((FixedArrayValue) arrayProperty).getValue();
		
		return values.clone();
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0c00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FixedArrayElementType",
			aliases = { "ElementType" },
			typeName = "TypeDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0017,
			symbol = "FixedArrayElementType")
	public TypeDefinition getType() {

		return elementType.getTarget();
	}

	/**
	 * <p>Sets the element type for all elements of the array defined by this fixed array 
	 * type definition.</p>
	 * 
	 * <p>The element type of a fixed array type definition must be one of the following kinds:</p>
	 * 
	 * <ul>
	 * 	<li>{@link tv.amwa.maj.meta.TypeDefinitionInteger TypeDefinitionInteger}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionRecord TypeDefinitionRecord}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionEnumeration TypeDefinitionEnumeration}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration TypeDefinitionExtendibleEnumeration}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionFixedArray TypeDefinitionFixedArray}</li>
	 * </ul>
	 * 
	 * <p>If the element type is not one of the above kinds, an {@link IllegalArgumentException} is
	 * thrown.</p>
	 *
	 * @param elementType Element type for this fixed array type definition.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws IllegalArgumentException The given element type is not acceptable for a fixed array 
	 * type definition.
	 */
	@MediaPropertySetter("FixedArrayElementType")
	public void setElementType(
			tv.amwa.maj.meta.TypeDefinition elementType)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (elementType == null)
			throw new NullPointerException("Cannot set the element type of this fixed array type definition with a null element type.");
		
		if ((!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionInteger)) &&
				(!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionRecord)) &&
				(!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionEnumeration)) &&
				(!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration)) &&
				(!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionFixedArray)))
			throw new IllegalArgumentException("The given element type is not acceptable for a fixed array type definition.");
		
		this.elementType = new WeakReference<TypeDefinition>(elementType);
	}

	public final static TypeDefinition initializeFixedArrayElementType() {
		
		return TypeDefinitions.Int32;
	}
	
	public void setElementValue(
			tv.amwa.maj.industry.PropertyValue arrayProperty, 
			int index,
			tv.amwa.maj.industry.PropertyValue memberProperty) 
	throws NullPointerException,
		IllegalPropertyValueException, 
		IndexOutOfBoundsException, 
		BadTypeException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot set an element in a null fixed size array property.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given fixed array property type does not match this fixed size array property.");
		if (!(elementType.getTarget().equals(memberProperty.getType())))
			throw new BadTypeException("The given property to set in the fixed sized array is not of a compatable type for the element typee of this fixed array type definition.");
		
		try {
			Object[] values = ((FixedArrayValue) arrayProperty).getValue();
			values[index] = memberProperty.getValue();
			// No need to set the value again as we are accessing the underlying array.
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for a fixed size array of " + getCount() + " elements.");
		}
	}

	public void setArray(
			tv.amwa.maj.industry.PropertyValue arrayProperty, 
			Object[] data)
		throws NullPointerException, 
			IllegalPropertyValueException,
			BadParameterException, 
			BadTypeException,
			ClassCastException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot set the value of a null fixed size array property value.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property to set in the fixed sized array is not of a compatable type for the element typee of this fixed array type definition.");
		if (data == null)
			throw new NullPointerException("Cannot set the value of a fixed size array value from a null object array.");
		if (data.length != getCount())
			throw new BadParameterException("Cannot set the value of a fixed size array value from an array of objects of a different length to that of this fixed size array definition.");

		try {
			for ( int x = 0 ; x < elementCount ; x++ )
				elementType.getTarget().createValue(data[x]);
			((FixedArrayValue) arrayProperty).setValue(data.clone());
		}
		catch (ClassCastException cce) {
			throw new BadTypeException("Class cast exception thrown when trying to set array elements on a fixed size array property value: " + cce.getMessage());
		}		
	}

	public PropertyValue createValueFromArray(
			Object[] initData)
		throws NullPointerException,
			BadParameterException,
			BadTypeException {

		if (initData == null)
			throw new NullPointerException("Cannot create a new fixed size array value from a null object array.");
		if (initData.length != getCount())
			throw new BadParameterException("Cannot create a new fixed size array value from an array of objects of a different length to that of this fixed size array definition.");
		
		try {
			for ( int x = 0 ; x < initData.length ; x++ )
				elementType.getTarget().createValue(initData[x]);
			return new FixedArrayValue(this, initData.clone());
		}
		catch (ClassCastException cce) {
			throw new BadTypeException("Class cast exception thrown when trying to add values to a new fixed size array property value: " + cce.getMessage());
		}
	}
	
	public TypeCategory getTypeCategory() {
		
		return TypeCategory.FixedArray;
	}

	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		if (javaValue == null)
			throw new ClassCastException("Cannot cast a null value to a fixed size array property value.");
		
		Object[] values;
		
		if (javaValue instanceof Collection<?>)
			javaValue = ((Collection<?>) javaValue).toArray();
		
		if (javaValue.getClass().isArray()) {
			Object[] javaArray = (Object[]) javaValue;

			if (javaArray.length != getCount())
				throw new ClassCastException("Cannot cast the given value to a fixed size array property as the given array has " + javaArray.length + " elements and exactly " + getCount() + " are required.");
			
			values = new Object[javaArray.length];
			for ( int x = 0 ; x < values.length ; x++ ) {

				Object value;
				if (javaArray[x] instanceof PropertyValue) {
					if (!((PropertyValue) javaArray[x]).getType().equals(elementType.getTarget()))
						throw new ClassCastException("Cannot cast a property value element of the given Java array at index " + x + " to a new fixed array as its type does not match the element type of this fixed size array.");
					value = ((PropertyValue) javaArray[x]).getValue();
				}
				else {
					elementType.getTarget().createValue(javaArray[x]);
					value = javaArray[x];
				}

				values[x] = value;
			}
		}
		else {
			if (getCount() == 1) {
				if (javaValue instanceof PropertyValue) {
					
					PropertyValue propertyValue = (PropertyValue) javaValue;
					if (!(propertyValue.getType().equals(elementType.getTarget())))
						throw new ClassCastException("Cannot cast the given property value to a value of this fixed size array type as its type does not match the element type of this fixed size array type definition.");
					values = new Object[] { propertyValue.getValue() };
				}
				else {
					elementType.getTarget().createValue(javaValue);
					values = new Object[] { javaValue };
				}
			}
			else
				throw new ClassCastException("Cannot cast the given value to a fixed size array property value as the fixed array must have " + elementCount + " elements.");
		}
		
		return new FixedArrayValue(this, values);
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
		throws NullPointerException,
			EndOfDataException {
		
		super.createFromBytes(buffer);

		PropertyValue[] values = new PropertyValue[elementCount];
		
		for ( int u = 0 ; u < elementCount ; u++) 
			values[u] = elementType.getTarget().createFromBytes(buffer);
		
		return createValue(values);
	}
	
	@Override
	public List<PropertyValue> writeAsBytes(
			PropertyValue value,
			ByteBuffer buffer)
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		super.writeAsBytes(value, buffer);
		
		TypeDefinition elementTypeResolved = elementType.getTarget();
		if ((elementTypeResolved.lengthAsBytes(getElementValue(value, 0)) * elementCount) > buffer.remaining())
			throw new InsufficientSpaceException("Insuficient space in the given buffer to write a value of " + getName() + " type.");
		
		for ( int u = 0 ; u < elementCount ; u++) 
			elementTypeResolved.writeAsBytes(getElementValue(value, u), buffer);
		
		return null;
	}
	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.lengthAsBytes(value);
		
		long elementLength = elementType.getTarget().lengthAsBytes(getElementValue(value, 0));
		return elementCount * elementLength;
	}
	
	public String nameToAAFName(
			String name) {
		
		return "aaf" + super.nameToAAFName(name);
	}

	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionFixedArray");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
		
		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"ElementCount", elementCount);
		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"ElementType", elementType.getTarget().getName());
	}
	
	public TypeDefinitionFixedArray clone() {
		
		return (TypeDefinitionFixedArray) super.clone();
	}	
}
