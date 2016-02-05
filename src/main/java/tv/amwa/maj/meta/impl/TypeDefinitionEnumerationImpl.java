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
 * $Log: TypeDefinitionEnumerationImpl.java,v $
 * Revision 1.9  2011/07/27 17:36:56  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.8  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.7  2011/01/26 11:50:11  vizigoth
 * Completed common method testing.
 *
 * Revision 1.6  2011/01/21 12:37:23  vizigoth
 * Final fixes to work with lenient initialization of properties.
 *
 * Revision 1.5  2011/01/20 15:52:54  vizigoth
 * Fixed up all meta tests to the point where they all pass.
 *
 * Revision 1.4  2011/01/19 21:37:53  vizigoth
 * Added property initialization code.
 *
 * Revision 1.3  2011/01/18 09:13:20  vizigoth
 * Fixed spelling errors in comments.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2011/01/04 09:57:30  vizigoth
 * Improved error message.
 *
 * Revision 1.5  2010/12/15 18:51:11  vizigoth
 * Added facility to map from ordinal element values to enumeration type values.
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
 * Revision 1.3  2008/01/14 20:55:20  vizigoth
 * Change to type category enumeration element names.
 *
 * Revision 1.2  2007/12/04 09:45:45  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:13:16  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:22  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionEnumeration;
import tv.amwa.maj.meta.TypeDefinitionInteger;
import tv.amwa.maj.misctype.AAFString;


/** 
 * <p>Implements the definition of a property type that can have one of a set of integer values.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0207, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionEnumeration",
		  description = "The TypeDefinitionEnumeration class defines a property type that can have one of a set of integer values.",
		  symbol = "TypeDefinitionEnumeration")
public final class TypeDefinitionEnumerationImpl 
	extends 
		SingletonTypeDefinitionImpl 
	implements 
		TypeDefinitionEnumeration,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 5104190354150925255L;
	
	private Class<Enum<?>> elements;
	private MediaEnumerationValue[] elementArray;
	private Map<String, MediaEnumerationValue> nameMap = 
		Collections.synchronizedMap(new HashMap<String, MediaEnumerationValue>());
	private Map<Long, MediaEnumerationValue> valueMap =
		Collections.synchronizedMap(new HashMap<Long, MediaEnumerationValue>());
	private WeakReference<TypeDefinition> elementType;
	
	protected TypeDefinitionEnumerationImpl() { }
	
	/**
	 * <p>Creates and initializes an enumeration type definition, which defines a 
	 * property type that can have one of a set of integer values. The enumeration is
	 * initialized with its identity and its enumeration elements defined by a 
	 * <code>java.lang.Enum</code> class (<code>Class&lt;Enum&gt;</code>).</p>
	 * 
	 * <p>The interface {@link tv.amwa.maj.industry.MediaEnumerationValue} must
	 * be implemented by the Java class defining the enumeration. For any enumeration value,
	 * this interface provides a method 
	 * {@link tv.amwa.maj.industry.MediaEnumerationValue#value() value()} that is
	 * used to find the <code>Int64</code> value associated with the enumeration token in 
	 * the AAF object specification and associated registries. Note, however, that Java
	 * enumeration values are backed by Java <code>int</code> values that are actually
	 * <code>Int32</code> AAF values.</p>
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
	 * @param identification Unique identifier to be used to identify this type.
	 * @param typeName Display name for the enumeration type definition.	 
	 * @param enumeration Java enumeration represented by this type definition.
	 * 
	 * @throws NullPointerException One or more of the arguments is null,
	 * or one or more of the elements of the names array is null.
	 * @throws IllegalArgumentException The class provided to describe this
	 * enumeration is not a Java enumeration or does not implement the 
	 * {@link MediaEnumerationValue} interface.
	 */
	@SuppressWarnings("unchecked")
	public TypeDefinitionEnumerationImpl(
			tv.amwa.maj.record.AUID identification,
			@AAFString String typeName,
			Class<?> enumeration,
			tv.amwa.maj.meta.TypeDefinition elementType)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new enumeration type definition with a null identification.");
		if (enumeration == null)
			throw new NullPointerException("Cannot create a new enumeration type definition with a null Java enumeration.");
		if (enumeration.isEnum() == false)
			throw new IllegalArgumentException("The class provided to describe this enumeration is not a Java enumeration.");
		
		setIdentification(identification);
		setName(typeName);
		setElementType(elementType);
		setElements((Class<Enum<?>>) enumeration);
	}
	
	public static class EnumerationValue 
		extends PropertyValueImpl
		implements PropertyValue{

		private TypeDefinitionEnumerationImpl type;
		private MediaEnumerationValue value;
		
		private EnumerationValue(
				TypeDefinitionEnumerationImpl type,
				MediaEnumerationValue value) {
			
			this.type = type;
			setValue(value);
		}
		
		public tv.amwa.maj.meta.TypeDefinition getType() {

			return type;
		}

		public MediaEnumerationValue getValue() {

			return value;
		}

		public boolean isDefinedType() {
			
			return true;
		}
		
		private void setValue(
				MediaEnumerationValue value) {
			
			this.value = value;
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
	 * <p></p>
	 *
	 * @param enumeration
	 * @throws NullPointerException
	 */
	void setElements(
			Class<Enum<?>> enumeration) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (enumeration == null)
			throw new NullPointerException("Cannot set the elements for this type definiton using a null enumeration class.");
		
		this.elements = enumeration;
		
		Enum<?>[] constants = enumeration.getEnumConstants();
		this.elementArray = new MediaEnumerationValue[constants.length];
		int elementCounter = 0;
		try {
			for ( Enum<?> item : constants ) {
				MediaEnumerationValue itemValue = (MediaEnumerationValue) item;
				nameMap.put(itemValue.name(), itemValue);
				nameMap.put(itemValue.symbol(), itemValue); // Support symbol name lookup
				valueMap.put(itemValue.value(), itemValue); // Support ordinal lookup
				this.elementArray[elementCounter++] = itemValue;
			}
		}
		catch (ClassCastException cce) {
			throw new IllegalArgumentException("Java enumerations representing AAF enumerations must implement the AAFEnumerationValue interface.");
		}
	}
	
	public int countElements() {

		return elementArray.length;
	}

	public tv.amwa.maj.industry.PropertyValue createValueFromName(
			String name)
		throws NullPointerException, 
			InvalidParameterException {

		if (name == null)
			throw new NullPointerException("Cannot create a new enumeration value from a null name.");
		if (!(nameMap.containsKey(name)))
			throw new InvalidParameterException("An enumeration value of the given name of " + name + " is not present in this enumeration type definition.");
		
		return new EnumerationValue(this, nameMap.get(name));
	}

	public tv.amwa.maj.industry.PropertyValue createValueFromOrdinal(
			Number ordinal) 
		throws NullPointerException,
			InvalidParameterException {
		
		if (ordinal == null)
			throw new NullPointerException("Cannot create a new enumeration value from a null ordinal value.");
		long ordinalLong = ordinal.longValue();
		if (!((TypeDefinitionInteger) elementType.getTarget()).isSigned()) {
			switch (((TypeDefinitionInteger) elementType.getTarget()).getSize()) {
			
			case TypeDefinitionInteger.BYTE:
				ordinalLong = (ordinalLong < 0l) ? 256l + ordinalLong : ordinalLong;
				break;
			case TypeDefinitionInteger.SHORT:
				ordinalLong = (ordinalLong < 0l) ? 65536l + ordinalLong : ordinalLong;
				break;
			case TypeDefinitionInteger.INT:
				ordinalLong = (ordinalLong < 0l) ? 4294967296l + ordinalLong : ordinalLong;
				break;
			case TypeDefinitionInteger.LONG:
				// TODO this is a broken, although unlikely to happen.
				ordinalLong = (ordinalLong < 0l) ? 0l : ordinalLong;
				break;
			}
		}
		if (!(valueMap.containsKey(ordinalLong)))
			throw new InvalidParameterException("An enumeration value with the given ordinal value of " + ordinal.toString() + " is not present in this enumeration.");
	
		return new EnumerationValue(this, valueMap.get(ordinalLong));
	}
	
	public String getElementName(
			int index) 
		throws IndexOutOfBoundsException {

		try {
			return elementArray[index].name();
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The index into the array of elements of this enumeration type definition is outside the acceptable range.");
		}
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0b00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ElementType",
			aliases = { "EnumElementType" },
			typeName = "TypeDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0014,
			symbol = "ElementType")
	public TypeDefinition getElementType() {

		return elementType.getTarget();
	}

	/**
	 * <p></p>
	 *
	 * @param elementType
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	@MediaPropertySetter("ElementType")
	public void setElementType(
			TypeDefinition elementType)
		throws NullPointerException, 
			IllegalArgumentException {
		
		if (elementType == null)
			throw new NullPointerException("Cannot set the element type of this enumeration type definition with a null reference.");
		if (!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionInteger))
			throw new IllegalArgumentException("Cannot set the element type this enumeration to a non integer type.");
	
		this.elementType = new WeakReference<TypeDefinition>(elementType);
	}
	
	public final static TypeDefinition initializeElementType() {
		
		return TypeDefinitions.UInt8;
	}
	
	@MediaProperty(uuid1 = 0x03010203, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ElementNames",
			aliases = { "EnumElementNames" },
			typeName = "UTF16StringArray",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0015,
			symbol = "ElementNames")
	public String[] getElementNames() {

		String[] names = new String[elementArray.length];
		for ( int x = 0 ; x < names.length ; x++ )
			names[x] = elementArray[x].symbol();
		
		return names;
	}
	
	public final static String[] initializeElementNames() {
		
		return new String[0];
	}
	
	// Deliberately has no function. Manipulate using Java enumerations instead.
	@MediaPropertyClear("ElementNames")
	public void clearElementNames() { }

	// Deliberately has no function. Manipulate using Java enumerations instead.
	@MediaListAppend("ElementNames")
	public void appendElementName(
			String elementName) { }
	
	@MediaProperty(uuid1 = 0x03010203, uuid2 = (short) 0x0500, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ElementValues",
			aliases = { "EnumElementValues" },
			typeName = "Int64Array",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0016,
			symbol = "ElementValues")
	public long[] getElementValues() {
		
		long[] values = new long[elementArray.length];
		for ( int x = 0 ; x < values.length ; x++ )
			values[x] = ((MediaEnumerationValue) elementArray[x]).value();
		
		return values;
	}
	
	public final static long[] initializeElementValues() {
		
		return new long[0];
	}

	// Deliberately has no function. Manipulate using Java enumerations instead.
	@MediaPropertyClear("ElementValues")
	public void clearElementValues() { }
	
	// Deliberately has no function. Manipulate using Java enumerations instead.
	@MediaListAppend("ElementValues")
	public void appendElementValue(
			long elementValue) { }
	
	public long getElementValue(
			int index) 
		throws IndexOutOfBoundsException {

		try {
			return (long) ((MediaEnumerationValue) elementArray[index]).value();
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The index into the array of elements of this enumeration type definition is outside the acceptable range.");
		}
	}

	public Class<Enum<?>> getEnumeration() {

		return elements;
	}

	public long getIntegerValue(
			tv.amwa.maj.industry.PropertyValue enumerationProperty)
		throws NullPointerException, 
			IllegalPropertyValueException {

		if (enumerationProperty == null)
			throw new NullPointerException("Cannot extract the integer value from a null enumeration property value.");
		if (!(equals(enumerationProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this enumeration type definition.");
		
		return ((MediaEnumerationValue) ((EnumerationValue) enumerationProperty).getValue()).value();
	}

	public String getNameFromInteger(
			long value) 
		throws IllegalArgumentException {
		
		if (!(valueMap.containsKey(value)))
			throw new IllegalArgumentException("This enumeration type definition does not contain an element with the given value.");
		
		return valueMap.get(value).name();
	}

	public String getNameFromValue(
			tv.amwa.maj.industry.PropertyValue enumerationProperty)
		throws NullPointerException, 
			IllegalPropertyValueException {

		if (enumerationProperty == null)
			throw new NullPointerException("Cannot extract the element name from a null enumeration property value.");
		if (!(equals(enumerationProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this enumeration type definition.");
		
		return ((EnumerationValue) enumerationProperty).getValue().name();
	}

	public String getSymbolFromValue(
			tv.amwa.maj.industry.PropertyValue enumerationProperty)
	throws NullPointerException, 
		IllegalPropertyValueException {
		
		if (enumerationProperty == null)
			throw new NullPointerException("Cannot extract the element symbol from a null enumeration property value.");
		if (!(equals(enumerationProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this enumeration type definition.");
		
		return ((EnumerationValue) enumerationProperty).getValue().symbol();
	}
	
	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		if (javaValue == null)
			throw new ClassCastException("Cannot create a new enumeration property value from a null value.");
		
		if (javaValue instanceof PropertyValue) {
			PropertyValue propertyValue = (PropertyValue) javaValue;
			if (propertyValue.getType().equals(this))
				return new EnumerationValue(this, (MediaEnumerationValue) propertyValue.getValue());
		}
		
		if (javaValue instanceof MediaEnumerationValue) {
			for ( Enum<?> constants : elements.getEnumConstants() )
				if (((Enum<?>) javaValue).equals(constants)) 
					return new EnumerationValue(this, (MediaEnumerationValue) javaValue);
		}
		
		try {
			if (javaValue instanceof String) {
				try { javaValue = Long.parseLong((String) javaValue); }
				catch (NumberFormatException nfe) {
					return createValueFromName((String) javaValue);
				}
			}
			
			if (javaValue instanceof Number) 
				return createValueFromOrdinal((Number) javaValue);
			
			if (javaValue instanceof Boolean)
				return createValueFromName(((Boolean) javaValue).booleanValue() ? "True" : "False");
		} 
		catch (NullPointerException e) {
			throw new ClassCastException("Cannot create a new enumeration property value from a null value.");
		} 
		catch (InvalidParameterException e) {
			throw new ClassCastException("Cannot create a new enumeration property value due to an illegal parameter exception: " + e.getMessage());
		} 
		catch (IllegalArgumentException e) {
			throw new ClassCastException("Unexpetedly, this enumeration type definition does not equal this enumeration type definition!");
		}
		
		if (javaValue instanceof MediaEnumerationValue) {
			MediaEnumerationValue enumerationValue = (MediaEnumerationValue) javaValue;
			if ((nameMap.containsKey(enumerationValue.name()) &&
					valueMap.containsKey(enumerationValue.value())))
				return new EnumerationValue(this, (MediaEnumerationValue) javaValue);
		}
		
		throw new ClassCastException("Cannot create a new enumeration property value from the given Java object.");
	}		

	@Override
	public TypeCategory getTypeCategory() {

		return TypeCategory.Enum;
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
		throws NullPointerException,
			EndOfDataException {
		
		super.createFromBytes(buffer);

		PropertyValue valueFromBuffer = elementType.getTarget().createFromBytes(buffer);
		
		return createValue(valueFromBuffer.getValue());
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
		PropertyValue elementValue = 
			elementTypeResolved.createValue(((EnumerationValue) value).getValue().value());
		
		if (elementTypeResolved.lengthAsBytes(elementValue) > buffer.remaining())
			throw new InsufficientSpaceException("Not enough space remaining in the given buffer to write an emumeration value with element type " +
					elementTypeResolved.getName() + ".");
		
		elementTypeResolved.writeAsBytes(elementValue, buffer);
		
		return null;
	}
	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.lengthAsBytes(value);
		
		TypeDefinition elementTypeResolved = elementType.getTarget();
		
		if (elementTypeResolved instanceof TypeDefinitionInteger)
			return ((TypeDefinitionInteger) elementTypeResolved).getSize();
		
		PropertyValue elementValue = 
			elementTypeResolved.createValue(((EnumerationValue) value).getValue().value());
		return elementTypeResolved.lengthAsBytes(elementValue);
	}
	
	@Override
	public void setPropertyValue(
			MetadataObject metadataObject,
			PropertyDefinition property, 
			PropertyValue value) 
		throws IllegalArgumentException, 
			IllegalAccessException, 
			InvocationTargetException {
		
		SingletonMethodBag methods = (SingletonMethodBag) ((PropertyDefinitionImpl) property).getMethodBag();
		
		if (value.getValue() instanceof tv.amwa.maj.enumeration.Boolean) {
			
			methods.set(metadataObject, 
					(((tv.amwa.maj.enumeration.Boolean) value.getValue()) == tv.amwa.maj.enumeration.Boolean.True));
		}
		else
			methods.set(metadataObject, value.getValue());
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionEnumeration");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
		
		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"ElementType", elementType.getTarget().getName());

		Element elements = XMLBuilder.createChild(typeElement, namespace, prefix, 
				"Elements");
		
		for ( MediaEnumerationValue enumValue : elementArray ) {
			XMLBuilder.appendElement(elements, namespace, prefix, 
					"Name", enumValue.name());
			XMLBuilder.appendElement(elements, namespace, prefix, 
					"Value", enumValue.value());
		}
		
	}
	
	public TypeDefinitionEnumeration clone() {
		
		return (TypeDefinitionEnumeration) super.clone();
	}
}
