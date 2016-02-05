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
 * $Log: TypeDefinitionExtendibleEnumerationImpl.java,v $
 * Revision 1.8  2011/08/15 09:03:23  vizigoth
 * Read past problematic operation category in some Quantel AAF files.
 *
 * Revision 1.7  2011/07/27 17:42:46  vizigoth
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
 * Revision 1.3  2011/01/21 12:37:23  vizigoth
 * Final fixes to work with lenient initialization of properties.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.8  2010/11/18 10:49:53  vizigoth
 * Added support for dynamic meta dictionaries and type name mapping for legacy meta dictionary compatibility.
 *
 * Revision 1.7  2010/11/08 16:38:40  vizigoth
 * Added specific code to correctly write extendible enumeration elements into structured storage files.
 *
 * Revision 1.6  2010/06/18 16:56:07  vizigoth
 * Minor improvement to error message.
 *
 * Revision 1.5  2010/04/13 07:22:46  vizigoth
 * Improved byte stream reading to support little endian byte streams.
 *
 * Revision 1.4  2010/03/19 16:13:53  vizigoth
 * Added methods for writing bytes and calculating lengths.
 *
 * Revision 1.3  2010/03/01 11:09:00  vizigoth
 * Fixed interaction with ExtendibleEnumerationWareshouse to make this type truly dynamic and avoid replication between two different sets of static collections.
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
 * Revision 1.1  2007/11/13 22:13:27  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.DuplicateException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

/** 
 * <p>Implements the definition of a property type that can have one of an extendible set of AUID values.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0220, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionExtendibleEnumeration",
		  description = "The TypeDefinitionExtendibleEnumeration class defines a property type that can have one of an extendible set of AUID values.",
		  symbol = "TypeDefinitionExtendibleEnumeration")
public final class TypeDefinitionExtendibleEnumerationImpl 
	extends 
		SingletonTypeDefinitionImpl
	implements 
		tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration, 
		Serializable,
		Cloneable {
	
	static final long serialVersionUID = -3191040594861355572L;
	
	protected TypeDefinitionExtendibleEnumerationImpl() {	}

	/**
	 * <p>Creates and initializes the new extensible enumeration type definition, which 
	 * defines a property type that can have one of an extendible set of AUID values. The 
	 * initial set of (possibly empty) elements must be provided as a map from element name to
	 * AUID. Further enumeration elements can then be specified using the 
	 * {@link TypeDefinitionExtendibleEnumerationImpl#appendElement(AUIDImpl, String) appendElement(AUID, String)} 
	 * method.</p>
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
	 * @param typeName Friendly name of the type definition.
	 * @param symbolPrefix Name of the type used as a prefix when serialising values to text,
	 * e.g. "<code><em>TransferCharacteristic</em>_ITU709</code>".
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>.
	 * @throws DuplicateException The given representation of an extendible enumeration
	 * contains duplicate AUID values.
	 * 
	 * @see tv.amwa.maj.industry.ExtendibleEnumerationWarehouse
	 * @see tv.amwa.maj.industry.ExtendibleEnumerationItem
	 */
	public TypeDefinitionExtendibleEnumerationImpl(
			AUID identification,
			@AAFString String typeName,
			String symbolPrefix) 
		throws NullPointerException,
			DuplicateException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new extendible enumeration type definition with a null identification.");
		
		setIdentification(identification);
		setName(typeName);
		this.symbol= symbolPrefix;
		
	}
	
	public static class ExtendibleEnumerationValue 
		extends PropertyValueImpl
		implements PropertyValue {
		
		private TypeDefinitionExtendibleEnumerationImpl type;
		private String valueName;
		private AUID valueId;
		
		private ExtendibleEnumerationValue(
				TypeDefinitionExtendibleEnumerationImpl type,
				String valueName,
				AUID valueId) {
			
			this.type = type;
			setValue(valueName, valueId);
		}

		private void setValue(
				String valueName,
				AUID valueId) {

			this.valueName = valueName;
			this.valueId = valueId.clone();
		}

		public tv.amwa.maj.meta.TypeDefinition getType() {

			return type;
		}

		public AUID getValue() {

			return valueId;
		}

		public String getName() {
			
			return valueName;
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
	
	@MediaProperty(uuid1 = 0x03010203, uuid2 = (short) 0x0700, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ExtendibleEnumerationElementNames",
			aliases = { "ElementNames", "ExtEnumElementNames" },
			typeName = "UTF16StringArray",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x001F,
			symbol = "ExtendibleEnumerationElementNames")
	public String[] getElementNames() {
		
		SortedMap<String, AUID> enumeration = Warehouse.lookupExtendibleEnumeration(symbol);
		
		String[] names = new String[enumeration.size()];
		int index = 0;
		for ( String name : enumeration.keySet() ) 
			names[index++] = symbol + "_" + name;
		
		return names;
	}
	
	public final static List<String> initializeExtendibleEnumerationElementNames() {
		
		return new ArrayList<String>();
	}
	
	// Deliberate dummy method ... use Warehouse methods instead
	@MediaPropertyClear("ExtendibleEnumerationElementNames")
	public void clearExtendibleEnumerationElementNames() { }
	
	// Deliberate dummy method ... use Warehouse methods instead
	@MediaListAppend("ExtendibleEnumerationElementNames")
	public void appendExtendibleEnumerationElementName(
			String elementName) { }
	
	@MediaProperty(uuid1 = 0x03010203, uuid2 = (short) 0x0800, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ExtendibleEnumerationElementValues",
			aliases = { "ElementValues", "ExtEnumElementValues" },
			typeName = "AUIDArray",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0020,
			symbol = "ExtendibleEnumerationElementValues")
	public AUID[] getElementValues() {
		
		SortedMap<String, AUID> enumeration = Warehouse.lookupExtendibleEnumeration(symbol);
		
		AUID[] values = new AUID[enumeration.size()];
		int index = 0;
		for ( String name : enumeration.keySet() )
			values[index++] = enumeration.get(name);
		
		return values;
	}

	public final static List<AUID> initializeExtendibleEnumerationElementValues() {
		
		return new ArrayList<AUID>();
	}

	// Deliberate dummy method ... use Warehouse methods instead
	@MediaPropertyClear("ExtendibleEnumerationElementValues")
	public void clearExtendibleEnumerationElementValues() { }
	
	// Deliberate dummy method ... use Warehouse methods instead
	@MediaListAppend("ExtendibleEnumerationElementValues")
	public void appendExtendibleEnumerationElementValue(
			AUID extendibleEnumerationElementValue) { }
	
	public void appendElement(
			AUID value, 
			String name)
		throws NullPointerException, 
			DuplicateException {

		if (value == null)
			throw new NullPointerException("Cannot append an enumeration element to this extendible enumeration with a null identifying AUID.");
		if (name == null)
			throw new NullPointerException("Cannot append an enumeration element to this extendible enumeration with a null name.");

		SortedMap<String, AUID> enumeration = Warehouse.lookupExtendibleEnumeration(symbol);
		if (enumeration.containsKey(name))
			throw new DuplicateException("The given name is already the name of an element of the " + symbol + " extendible enumeration.");
		if (enumeration.containsValue(value))
			throw new DuplicateException("The given value is already the value of an element of the " + symbol + " extendible enumeration.");

		String testName = Warehouse.extendibleEnumerationName(value);
		if ((testName != null) && (!testName.equals(symbol)))
			throw new DuplicateException("The given value is alerady an element value of another extendible enumeration.");
		
		Warehouse.registerExtendibleEnumerationElement(symbol, name, value);
	}

	public int countElements() {

		SortedMap<String, AUID> enumeration = Warehouse.lookupExtendibleEnumeration(symbol);

		return enumeration.size();
	}

	public PropertyValue createValueFromName(
			String name)
		throws NullPointerException, 
			InvalidParameterException {

		if (name == null)
			throw new NullPointerException("Cannot create an extendible enumeration property value from a null name.");
		
		if (name.startsWith(symbol)) {
			int truncateBy = symbol.length();
			if (name.charAt(truncateBy) == '_') truncateBy++;
			name = name.substring(truncateBy);
			if (Character.isDigit(name.charAt(0))) name = "_" + name;
		}

		SortedMap<String, AUID> enumeration = Warehouse.lookupExtendibleEnumeration(symbol);
		
		if (!(enumeration.containsKey(name)))
			throw new InvalidParameterException("The given element name does not match the name of an element of this extendible enumeration.");
		
		return new ExtendibleEnumerationValue(this, name, enumeration.get(name));
	}

	public AUID getAUIDValue(
			tv.amwa.maj.industry.PropertyValue enumerationProperty)
		throws NullPointerException, 
			IllegalPropertyValueException {

		if (enumerationProperty == null)
			throw new NullPointerException("Cannot extract the identification from a null extendible enumeration property value.");
		if (!(equals(enumerationProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this extendible enumeration type definition.");

		return ((ExtendibleEnumerationValue) enumerationProperty).getValue();
	}

	public String getElementName(
			int index) 
		throws IndexOutOfBoundsException {
		
		SortedMap<String, AUID> enumeration = Warehouse.lookupExtendibleEnumeration(symbol);

		if ((index < 0) || (index >= enumeration.size()))
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for this extensible enumeration type definition.");
		
		int counter = 0;
		for ( String elementName : enumeration.keySet() ) {
			if (counter == index) return elementName;
			counter++;
		}
		
		throw new IndexOutOfBoundsException("Unexpectedly, the given index is outside the acceptable range for this extensible enumeration type definition.");
	}

	public AUID getElementValue(
			int index) 
		throws IndexOutOfBoundsException {

		SortedMap<String, AUID> enumeration = Warehouse.lookupExtendibleEnumeration(symbol);
		
		if ((index < 0) || (index >= enumeration.size()))
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for this extensible enumeration type definition.");
		
		int counter = 0;
		for ( String elementName : enumeration.keySet() ) {
			if (counter == index) return enumeration.get(elementName);
			counter++;
		}
		
		throw new IndexOutOfBoundsException("Unexpectedly, the given index is outside the acceptable range for this extensible enumeration type definition.");		
	}

	public AUID getAUIDFromName(
			String name)
		throws NullPointerException,
			BadParameterException {
		
		if (name == null)
			throw new NullPointerException("Cannot retrieve the AUID corresponding to a null name.");
		
		SortedMap<String, AUID> enumeration = Warehouse.lookupExtendibleEnumeration(symbol);
		
		if (!(enumeration.containsKey(name))) {
			if (name.contains(symbol + "_")) {
				try {
					name = name.substring(symbol.length() + 1);
				}
				catch (IndexOutOfBoundsException iobe) {
					throw new BadParameterException("The given AUID does not correspond to a known enumeration element for this extensible enumeration definition.");
				}
				if (!(enumeration.containsKey(name)))
					throw new BadParameterException("The given AUID does not correspond to a known enumeration element for this extensible enumeration definition.");
			}
			else
				throw new BadParameterException("The given AUID does not correspond to a known enumeration element for this extensible enumeration definition.");
		}
		
		return enumeration.get(name).clone();
	}
	
	SortedMap<String,AUID> getElements() {

		return Warehouse.lookupExtendibleEnumeration(symbol);
	}
	
	public String getNameFromAUID(
			AUID value) 
		throws NullPointerException,
			BadParameterException {

		if (value == null)
			throw new NullPointerException("Cannot retrieve the name of an extendible enumeration element using a null identity.");

		String enumerationName = Warehouse.extendibleEnumerationName(value);
		
		if ((enumerationName == null) || (!(enumerationName.equals(symbol))))
			throw new BadParameterException("This extendible enumeration type definition does not contain an element with the given identity.");

		return Warehouse.extendibleEnumerationElementName(value);
	}

	public String getNameFromValue(
			tv.amwa.maj.industry.PropertyValue enumerationProperty)
		throws NullPointerException, 
			IllegalPropertyValueException {

		if (enumerationProperty == null)
			throw new NullPointerException("Cannot extract the name from a null extendible enumeration property value.");
		if (!(equals(enumerationProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this extendible enumeration type definition.");

		return ((ExtendibleEnumerationValue) enumerationProperty).getName();
	}
	
	public String getSymbolFromValue(
			tv.amwa.maj.industry.PropertyValue enumerationProperty) 
		throws NullPointerException, 
			IllegalPropertyValueException {
		
		String name = getNameFromValue(enumerationProperty);
		if (name.charAt(0) == '_') name = name.substring(1);
		
		if (symbol.length() > 0)
			return symbol+ "_" + name;
		else
			return name;
	}

	public void setAUIDValue(
			PropertyValue enumerationProperty, 
			AUID value)
		throws NullPointerException, 
			IllegalPropertyValueException,
			BadParameterException {

		if (enumerationProperty == null)
			throw new NullPointerException("Cannot set the value of a null extendible enumeration property.");
		if (value == null)
			throw new NullPointerException("Cannot set the value of a null extendible enumeration property with a null identity value.");
		if (!(equals(enumerationProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this extendible enumeration type definition.");
	
		String elementName = getNameFromAUID(value);
		
		((ExtendibleEnumerationValue) enumerationProperty).setValue(
				elementName, 
				value);
	}

	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		if (javaValue == null)
			throw new ClassCastException("Cannot create a new extendible enumeration property value by casting from null.");
		
		if (javaValue instanceof PropertyValue) {
			
			PropertyValue propertyValue = (PropertyValue) javaValue;
			if (propertyValue.getType().equals(this)) {
				ExtendibleEnumerationValue extensiblePropertyValue = 
					(ExtendibleEnumerationValue) propertyValue;
				return new ExtendibleEnumerationValue(
						this, 
						extensiblePropertyValue.getName(), 
						extensiblePropertyValue.getValue());
			}
		}
		
		try {
			if (javaValue instanceof String)
				return createValueFromName((String) javaValue);
		} 
		catch (InvalidParameterException e) {
			throw new ClassCastException("Cannot create a new extendible enumeration property value from the given name as it does not match the name of an enumeration element.");
		}
		
		try {
			if (javaValue instanceof AUID) {

				String elementName = getNameFromAUID((AUID) javaValue);
			
				return new ExtendibleEnumerationValue(
						this,
						elementName,
						(AUID) javaValue);
			}
		}
		catch (BadParameterException bpe) {
			
			// Read past incorrect metadictionary entries in Quantel AAF files
			if (getName().equals("OperationCategoryType")) {
				return new ExtendibleEnumerationValue(this, "Effect", (AUID) javaValue);
			}
			
			throw new ClassCastException("Cannot create a new extendible enumeration property value from the given AUID identity as it does not match the identity of an enumeration element of type "
				+ getName() + ".");
		}
		
		throw new ClassCastException("Cannot create a new extendible enumeration property value from the given value.");
	}

	@Override
	public TypeCategory getTypeCategory() {
		
		return TypeCategory.ExtEnum;
	}

//	static class ExtendibleEnumerationItem 
//		extends MetaDefinitionImpl {
//
//		private AUID elementOf;
//		
//		/** <p></p> */
//		private static final long serialVersionUID = -4129507679224529497L;
//
//		public ExtendibleEnumerationItem(
//				AUID value,
//				String name,
//				String elementOf) 
//			throws NullPointerException {
//	
//			if (value == null)
//				throw new NullPointerException("Cannot create a new extendible enumeration item with a null value (identification).");
//			if (name == null)
//				throw new NullPointerException("Cannot create a new extendible enumeration item with a null name.");
//			if (elementOf == null)
//				throw new NullPointerException("Cannot create a new extendible enumeration iten with a null element of reference.");
//			
//			setIdentification(value);
//			setName(name);
//			
//			this.elementOf = AUIDImpl.parseFactory(elementOf.trim().substring(0, 45));
//		}
//		
//		AUID getElementOf() {
//			
//			return elementOf;
//		}
//	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
	 	throws NullPointerException,
	 		EndOfDataException {

		super.createFromBytes(buffer);
		
		if (buffer.remaining() < 16)
			throw new EndOfDataException("Not enough bytes available in the provided buffer to create a extendible enumeration value.");

		AUID enumKey = AUIDImpl.createFromBuffer(buffer);
		// System.err.println("Key for enumeration " + getName() + ": " + enumKey.toString());
		return createValue(enumKey);
	}
	
	@Override
	public List<PropertyValue> writeAsBytes(
			PropertyValue value,
			ByteBuffer buffer) 
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		super.writeAsBytes(value, buffer);
		
		if (buffer.remaining() < 16)
			throw new InsufficientSpaceException("Not enough space remaining in the given buffer to write an extendible emumeration value.");
		
		AUID keyValue = ((ExtendibleEnumerationValue) value).getValue();
		if (keyValue.isUniversalLabel())
			buffer.put(((UL) keyValue).getUniversalLabel());
		else
			buffer.put(keyValue.getAUIDValue());
		
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
		
		if (buffer.remaining() < 16)
			throw new InsufficientSpaceException("Not enough space remaining in the given buffer to write an extendible emumeration value.");
		
		AUID keyValue = ((ExtendibleEnumerationValue) value).getValue();
//		if (keyValue.isUniversalLabel())
//			buffer.put(((UL) keyValue).getUniversalLabel());
//		else
			buffer.put(keyValue.getAUIDValue());
		
		return null;
	}

	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.lengthAsBytes(value);
		
		return 16;
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionExtendibleEnumeration");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
		
		appendMetadictElements(metadict, namespace, prefix);
	}

	public void appendMetadictElements(
			Node metadict,
			String namespace,
			String prefix) {
		
		SortedMap<String, AUID> enumeration = Warehouse.lookupExtendibleEnumeration(symbol);
		
		for ( String elementName : enumeration.keySet() ) {
			
			Element elementElement = XMLBuilder.createChild(metadict, namespace, 
					prefix, "ExtendibleEnumerationElement");
			XMLBuilder.appendElement(elementElement, namespace, prefix, 
					"ElementOf", getIdentification().toString());
			XMLBuilder.appendElement(elementElement, namespace, prefix, 
					"Name", elementName);
			XMLBuilder.appendElement(elementElement, namespace, prefix, 
					"Value", enumeration.get(elementName).toString());
		}
	}
	
	public TypeDefinitionExtendibleEnumeration clone() {
		
		return (TypeDefinitionExtendibleEnumeration) super.clone();
	}
}
