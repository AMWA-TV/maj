/* 
 **********************************************************************
 *
 * $Id: TypeDefinitionRecordImpl.java,v 1.6 2011/07/27 17:41:46 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: TypeDefinitionRecordImpl.java,v $
 * Revision 1.6  2011/07/27 17:41:46  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.5  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/26 11:50:11  vizigoth
 * Completed common method testing.
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
 * Revision 1.6  2010/05/14 18:30:28  vizigoth
 * Provided mechanism for a difference between structured storage byte writing and KLV byte writing.
 *
 * Revision 1.5  2010/04/13 07:24:29  vizigoth
 * Better support for reporting Invocation Target Exceptions when creating values from lots of properties.
 *
 * Revision 1.4  2010/03/19 16:13:53  vizigoth
 * Added methods for writing bytes and calculating lengths.
 *
 * Revision 1.3  2010/01/19 14:38:59  vizigoth
 * Moved parsing of record type from byte buffers done in a non standard way to creatFromBuffer factory methods in the implementation itself.
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
 * Revision 1.1  2007/11/13 22:13:29  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionRecord;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements the definition of a property type that consists of an ordered set of fields, where each 
 * field has a name and type.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x020d, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionRecord",
		  description = "The TypeDefinitionRecord class defines a property type that consists of an ordered set of fields, where each field has a name and type.",
		  symbol = "TypeDefinitionRecord")
public final class TypeDefinitionRecordImpl 
	extends 
		SingletonTypeDefinitionImpl 
	implements 
		TypeDefinitionRecord,
		Serializable,
		Cloneable {
	
	/** <p></p> */
	private static final long serialVersionUID = -2858335381258759227L;

	/** Map from interface specifications to MAJ implementations. Used for createValueFromValues(). */
	private final static Map<Class<?>, Class<?>> majImplementations =
		new HashMap<Class<?>, Class<?>>(10);
	
	static {
		registerInterfaceMapping(tv.amwa.maj.record.Rational.class, tv.amwa.maj.record.impl.RationalImpl.class);
		registerInterfaceMapping(tv.amwa.maj.record.AUID.class, tv.amwa.maj.record.impl.AUIDImpl.class);
		registerInterfaceMapping(tv.amwa.maj.record.PackageID.class, tv.amwa.maj.record.impl.PackageIDImpl.class);
		registerInterfaceMapping(tv.amwa.maj.record.ProductVersion.class, tv.amwa.maj.record.impl.ProductVersionImpl.class);
		registerInterfaceMapping(tv.amwa.maj.record.VersionType.class, tv.amwa.maj.record.impl.VersionTypeImpl.class);
		registerInterfaceMapping(tv.amwa.maj.record.RGBAComponent.class, tv.amwa.maj.record.impl.RGBAComponentImpl.class);
		registerInterfaceMapping(tv.amwa.maj.record.DateStruct.class, tv.amwa.maj.record.impl.DateStructImpl.class);
		registerInterfaceMapping(tv.amwa.maj.record.TimeStruct.class, tv.amwa.maj.record.impl.TimeStructImpl.class);
		registerInterfaceMapping(tv.amwa.maj.record.TimeStamp.class, tv.amwa.maj.record.impl.TimeStampImpl.class);
	}
	
	public final static void registerInterfaceMapping(
			Class<?> recordTypeInterface,
			Class<?> recordTypeImplementation) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if ((recordTypeImplementation == null) || (recordTypeInterface == null))
			throw new NullPointerException("Cannot register record type interface or implementation with a null value.");
		
		if (!recordTypeInterface.isInterface())
			throw new IllegalArgumentException("The record type interface must be an interface.");
		
		if (recordTypeImplementation.isInterface())
			throw new IllegalArgumentException("The recond type implementation must be a member class.");
		
		if (!recordTypeInterface.isAssignableFrom(recordTypeImplementation))
			throw new IllegalArgumentException("The given record type implementation does not implement the given interface.");
		
		majImplementations.put(recordTypeInterface, recordTypeImplementation);
	}
	
	private String[] memberNames;
	private Map<String, TypeDefinition> memberTypes;
	private Class<?> specification;
	private Class<?> implementation;
	
	protected TypeDefinitionRecordImpl() { }
	
	/**
	 * <p>Creates and initializes this record type definition, which defines a property 
	 * type that consists of an ordered set of fields, where each field has a name and type.</p>
	 * 
	 * <p>It is only possible to use the following types as members of a record:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionInteger TypeDefinitionInteger}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionRecord TypeDefinitionRecord}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionEnumeration TypeDefinitionEnumeration}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration TypeDefinitionExtendibleEnumeration}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionFixedArray TypeDefinitionFixedArray}</li>
	 * </ul>
	 * 
	 * <p>If a type definition of a kind other than one listed above is used, an 
	 * {@link IllegalArgumentException} will be thrown.</p>
	 * 
	 * <p>Record type definitions provide a documented mechanism for extended the AAF meta model.
	 * However, care must be taken as any user-defined record types may not be interoperable with
	 * other systems if the record is not registered. The official registry of type definitions 
	 * is available from SMPTE at <a href="http://www.smpte-ra.org/mdd/">SMPTE Metadata Registries 
	 * And Related Items</a>. The full range of data types documented in the AAF 1.1 object 
	 * specification can be accessed by name and identification using either
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(String)} or
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(tv.amwa.maj.record.AUID)}
	 * respectively.</p>
	 * 
	 * @param identification AUID to be used to identify this type.
	 * @param typeName Friendly name for the record type definition.
	 * @param names Names of the element of the record in the order of the records.
	 * @param elements Elements of the records represented as a map from name to type.
	 * @param specification A Java interface specifying implementations of instances of this type.
	 * 
	 * @throws NullPointerException One or more of the identification, names or elements 
	 * arguments is <code>null</code>, or one or more of the elements of the given arrays is 
	 * <code>null</code>.
	 * @throws IllegalArgumentException Thress possible causes: One or more of the types contained in 
	 * the member type array is not a permissable type for a record; a 
	 * member type name is duplicated in the given array of member names; the given Java specification
	 * is not an interface. 
	 */
	public TypeDefinitionRecordImpl(
			  AUID identification,
			  @AAFString String typeName,
			  @AAFString String[] names,
			  Map<String, TypeDefinition> elements,
			  Class<?> specification) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new record type definition with a null identifier.");
		if (names == null)
			throw new NullPointerException("Cannot create a new record type definition with a null member names array.");
		if (elements == null)
			throw new NullPointerException("Cannpt create a new record type definition with a null member name to member type map.");
		if (specification == null)
			throw new NullPointerException("Cannot create a new record type definition with a null Java specification.");
		
		setIdentification(identification);
		setName(typeName);
		setMembers(names, elements);
		setSpecification(specification);
	}
	
	/**
	 * <p>Version of the constructor that can be used in static initializers.</p>
	 *
	 * @param identification AUID to be used to identify this type.
	 * @param typeName Friendly name for the record type definition.
	 * @param names Names of the element of the record in the order of the records.
	 * @param types Elements of the records represented as a map from name to type mapped to the 
	 * names by array index.
	 * @param specification A Java interface specifying implementations of instances of this type.
	 * 
	 * @throws NullPointerException One or more of the arguments is null.
	 * @throws IndexOutOfBoundsException The arrays are of different lengths.
	 * 
	 * @see #TypeDefinitionRecord(tv.amwa.maj.record.AUID, String, String[], Map)
	 */
	public TypeDefinitionRecordImpl(
			  tv.amwa.maj.record.AUID identification,
			  @AAFString String typeName,
			  @AAFString String[] names,
			  tv.amwa.maj.meta.TypeDefinition[] types,
			  Class<?> specification)
		throws NullPointerException,
			IndexOutOfBoundsException,
			IllegalArgumentException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new record type definition with a null identifier.");
		if (names == null)
			throw new NullPointerException("Cannot create a new record type definition with a null member names array.");
		if (types == null)
			throw new NullPointerException("Cannpt create a new record type definition with a null types array.");
		if (specification == null)
			throw new NullPointerException("Cannot create a new record type definition with a null Java specification.");

		setIdentification(identification);
		setName(typeName);
		setMembers(names, types);
		setSpecification(specification);
	}
	
	public final static class RecordValue 
		extends PropertyValueImpl
		implements PropertyValue {

		private TypeDefinitionRecordImpl type;
		private Object value;
		
		private RecordValue(
				TypeDefinitionRecordImpl type,
				Object value) {
			
			this.type = type;
			setValue(value);
		}
		
		public tv.amwa.maj.meta.TypeDefinition getType() {

			return type;
		}

		public Object getValue() {

			return value;
		}

		public boolean isDefinedType() {

			return true;
		}
		
		private void setValue(
				Object value) {
			
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
	
	@MediaProperty(uuid1 = 0x03010203, uuid2 = (short) 0x0600, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MemberNames",
			typeName = "UTF16StringArray",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x001D,
			symbol = "MemberNames")
	public final String[] getMemberNames() {
		
		return memberNames.clone();
	}

	public final static String[] initializeMemberNames() {
		
		return new String[0];
	}
	
	// Deliberately empty - use methods that link the record type to Java classes instead
	@MediaPropertyClear("MemberNames")
	public void clearMemberNames() { }

	// Deliberately empty - use methods that link the record type to Java classes instead
	@MediaListAppend("MemberNames")
	public void appendMemberName(
			String memberName) { }
	
	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MemberTypes",
			typeName = "TypeDefinitionWeakReferenceVector",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x001C,
			symbol = "MemberTypes")
	public final List<TypeDefinition> getMemberTypes() {
		
		List<tv.amwa.maj.meta.TypeDefinition> types = 
			new Vector<tv.amwa.maj.meta.TypeDefinition>();
		
		for ( int x = 0 ; x < memberNames.length ; x++ )
			types.add(memberTypes.get(memberNames[x]));
		
		return types;
	}

	public final static List<TypeDefinition> initializeMemberTypes() {
		
		return new ArrayList<TypeDefinition>();
	}
	
	// Deliberately empty - use methods that link the record type to Java classes instead
	@MediaPropertyClear("MemberTypes")
	public void clearMemberTypes() { }

	// Deliberately empty - use methods that link the record type to Java classes instead
	@MediaListAppend("MemberTypes")
	public void appendMemberName(
			TypeDefinition memberType) { }
	
	void setMembers(
			String[] memberNames,
			Map<String, tv.amwa.maj.meta.TypeDefinition> memberTypes) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (memberNames == null)
			throw new NullPointerException("Cannot set the member names of this record type definition with a null string array.");
		if (memberTypes == null)
			throw new NullPointerException("Cannot set the member types of this record type definition with a null name to type map.");
		
		for ( String memberName : memberNames ) {
			if (memberName == null)
				throw new NullPointerException("Cannot set the member names of this record type definition with a null member name.");
			if (!(memberTypes.containsKey(memberName)))
				throw new IllegalArgumentException("Could not set the members of this record type definition as a type definition for member " + memberName + " could not be found in the given map.");
		}
			
		for ( int x = 0 ; x < memberNames.length ; x++ ) 
			for ( int y = x + 1 ; y < memberNames.length ; y++ )
				if (memberNames[x].equals(memberNames[y]))
					throw new IllegalArgumentException("Cannot set the member names of this record using an array containing duplicate names.");
		
		for ( tv.amwa.maj.meta.TypeDefinition type : memberTypes.values() ) {
			
			// if (type instanceof TypeDefinitionUnresolved) continue;
			
			switch (type.getTypeCategory()) {
			
			case Int:
			case Record:
			case Enum:
			case ExtEnum:
			case FixedArray:
				break;
			default:
				throw new IllegalArgumentException("Cannot set the member types of this record using a type definition that is not permitted for records.");
			}
		}
		
		this.memberNames = memberNames;
		this.memberTypes = 
			new HashMap<String, tv.amwa.maj.meta.TypeDefinition>(memberTypes.size());
		
		for ( String name : memberTypes.keySet() ) {
			this.memberTypes.put(name, memberTypes.get(name));
		}
	}
	
	void setMembers(
			String[] memberNames,
			tv.amwa.maj.meta.TypeDefinition[] memberTypeArray) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (memberNames == null)
			throw new NullPointerException("Cannot set the member names of this record type definition using a null string array.");
		if (memberTypeArray == null)
			throw new NullPointerException("Cannot set the member types of this record type definition using a null array of types.");
		if (memberNames.length != memberTypeArray.length)
			throw new IllegalArgumentException("Cannot set the member types of this record type definition with name and type arrays of different lengths.");
		
		Map<String, tv.amwa.maj.meta.TypeDefinition> memberTypes = 
			new HashMap<String, tv.amwa.maj.meta.TypeDefinition>();
		
		for ( int x = 0 ; x < memberNames.length ; x++ ) {
			if (memberNames[x] == null)
				throw new NullPointerException("Cannot set the member names of this record type definition with a null member name at index " + x + ".");
			if (memberTypeArray[x] == null)
				throw new NullPointerException("Cannot set the member types of this record type definition with a null member type at index " + x + ".");
			memberTypes.put(memberNames[x], memberTypeArray[x]);
		}
		
		setMembers(memberNames, memberTypes);
	}
	
	void setSpecification(
			Class<?> specification) 
		throws IllegalArgumentException {
		
		if (!specification.isInterface())
			throw new IllegalArgumentException("The specification of an implementing object of this type is not an interface.");
		
		this.specification = specification;
		this.implementation = majImplementations.get(specification);
	}
	
	public Class<?> getSpecification() {
		
		return specification;
	}
	
	public Class<?> getImplementation() {
		
		return majImplementations.get(specification);
	}
	
	public final tv.amwa.maj.industry.PropertyValue createValueFromObject(
			Object initialData)
		throws BadTypeException {

		if (initialData == null)
			return new RecordValue(this, null);
		
		if (!specification.isInstance(initialData))
			throw new BadTypeException("The given initial data for a new record value does not implement the specification " + specification.getCanonicalName() + ".");
		
		return new RecordValue(this, initialData);
	}

	public final tv.amwa.maj.industry.PropertyValue createValueFromValues(
			tv.amwa.maj.industry.PropertyValue[] memberValues)
		throws NullPointerException,
			IllegalArgumentException,
			BadTypeException {

		if (memberValues == null)
			throw new NullPointerException("Cannot create a new record property value from a null array of member values.");
		if (memberValues.length != memberNames.length)
			throw new IllegalArgumentException("Cannot create a new record from the given array of member values as the wrong length.");
		
		try {
			Object baseValue = majImplementations.get(specification).newInstance();
			RecordValue recordProperty = new RecordValue(this, baseValue);

			for ( int x = 0 ; x < memberValues.length ; x++ ) {
				if (!(memberTypes.get(memberNames[x]).equals(memberValues[x].getType())))
					throw new BadTypeException("The type of the element at index " + x + " in the array of member values does not match the required type for this record type definition.");
				setValue(recordProperty, memberNames[x], memberValues[x]);
			}

			return recordProperty;
		}
		catch (Exception e) {
			if (e instanceof BadTypeException) 
				throw (BadTypeException) e;
			throw new BadTypeException("Cannot create an instance of the underling record property type due to a " + e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public final int getCount() {

		return memberNames.length;
	}

	public final Object getObject(
			tv.amwa.maj.industry.PropertyValue recordProperty)
		throws NullPointerException,
			BadTypeException,
			IllegalPropertyValueException {

		if (recordProperty == null)
			throw new NullPointerException("Cannot create an object instance representing a null record property value.");
		if (!(equals(recordProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property does not match this record type definition.");

		return recordProperty.getValue();
	}

	public final String getMemberName(
			int index)
		throws IndexOutOfBoundsException {

		try {
			return memberNames[index];
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the array of member names of this record type definition.");
		}
	}

	public final tv.amwa.maj.meta.TypeDefinition getMemberType(
			int index)
		throws IndexOutOfBoundsException {

		try {
			return memberTypes.get(memberNames[index]);
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the arrays of members of this record type definition.");
		}
	}

	public final tv.amwa.maj.industry.PropertyValue getValue(
			tv.amwa.maj.industry.PropertyValue recordProperty,
			int index)
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException {
		
		String memberName;
		try {
			memberName = memberNames[index];
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the array of names of this record type definition.");
		}
		
		return getValue(recordProperty, memberName);
	}

	public final tv.amwa.maj.industry.PropertyValue getValue(
			tv.amwa.maj.industry.PropertyValue recordProperty,
			String memberName)
		throws NullPointerException,
			IllegalPropertyValueException,
			IllegalArgumentException {

		if (recordProperty == null)
			throw new NullPointerException("Cannot retrieve a property value from a null record property value.");
		if (!(equals(recordProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property does not match this record property value.");
		if (memberName == null)
			throw new NullPointerException("Cannot retrieve a property value using a null member name.");
		if (!(memberTypes.containsKey(memberName)))
			throw new IllegalArgumentException("The given argument name does not match the name of a record member for this record type definition.");
		
		// This method expects the record property value to implement a method called get<memberName>
		
		if (!specification.isInstance(recordProperty.getValue()))
			throw new IllegalPropertyValueException("The implementing class of the record property value does not match the type specification of: " + specification.getCanonicalName());
		
		Class<?> valueImplementation = recordProperty.getValue().getClass();
		Method[] methods = valueImplementation.getMethods();
		String expectedName = "get" + memberName;
		
		for ( Method method : methods ) {
			
			if (method.getName().equalsIgnoreCase(expectedName)) {
				
				try {
					Object baseValue = method.invoke(recordProperty.getValue());
					return memberTypes.get(memberName).createValue(baseValue);
				}
				catch (Exception e) {
					throw new IllegalPropertyValueException("Unable to retrieve property value for " + memberName + " due to a " + e.getClass().getName() + ": " + e.getMessage());
				}
			}
		}
		
		throw new IllegalPropertyValueException("The class " + valueImplementation.getCanonicalName() + " does not have a method to retrieve value " + memberName + ".");
	}

	public final void setObject(
			tv.amwa.maj.industry.PropertyValue recordProperty,
			Object data)
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException {
		
		if (recordProperty == null)
			throw new NullPointerException("Cannot set the value of a null record property value.");
		if (!(equals(recordProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this record type definition.");
		
		if (data == null) {
			((RecordValue) recordProperty).setValue(null);
			return;
		}
			
		if (!specification.isInstance(data))
			throw new BadTypeException("The given data to set a record property value does not implement the required specification.");
		
		((RecordValue) recordProperty).setValue(data);
	}

	public final void setValue(
			tv.amwa.maj.industry.PropertyValue recordProperty,
			int index,
			tv.amwa.maj.industry.PropertyValue memberProperty)
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException,
			BadTypeException {

		try {
			String memberName = memberNames[index];
			setValue(recordProperty, memberName, memberProperty);
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for this type definition.");
		}
	}

	public final void setValue(
			tv.amwa.maj.industry.PropertyValue recordProperty,
			String memberName,
			tv.amwa.maj.industry.PropertyValue memberProperty)
		throws NullPointerException,
			IllegalPropertyValueException,
			IllegalArgumentException,
			BadTypeException {

		if (recordProperty == null)
			throw new NullPointerException("Cannot set the value of a null record property value.");
		if (!(equals(recordProperty.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this record type definition.");
		if (memberName == null)
			throw new NullPointerException("Cannot set the value of a record with a null member name.");
		if (!(memberTypes.containsKey(memberName)))
			throw new IllegalArgumentException("Cannot set the value of a record with a member name that is not known for this record type definition.");
		if (memberProperty == null)
			throw new NullPointerException("Cannot set the value of a record member using a null value.");
		
		if (!(memberTypes.get(memberName).equals(memberProperty.getType())))
			throw new BadTypeException("The type of the given member property does not match the type of the member property at the given index.");
		
		if (recordProperty.getValue() == null)
			throw new NullPointerException("Cannot set a member value of a null internal record proeprty value.");
		
		if (!specification.isInstance(recordProperty.getValue())) 
			throw new IllegalPropertyValueException("The internal implementation of the record proeprty value does not match the specification of this record type.");
		
		Class<?> valueImplementation = recordProperty.getValue().getClass();
		Method[] methods = valueImplementation.getMethods();
		String expectedMethod = "set" + memberName;
		
		for ( Method method : methods ) {
			
			if (method.getName().equalsIgnoreCase(expectedMethod)) {
				
				try {
//					System.out.println("Method: " + method.getName() + " on " + recordProperty.toString() + " with " +
//							memberProperty.getValue().toString());
					method.invoke(recordProperty.getValue(), memberProperty.getValue());
					return;
				}
				catch (InvocationTargetException ite) {
					throw new IllegalPropertyValueException("Unable to set the value of " + memberName + " due to a InvocationTargetException: " + ite.getTargetException().getMessage());
				}
				catch (Exception e) {
					throw new IllegalPropertyValueException("Unable to set the value of " + memberName + " due to a " + e.getClass().getName() + ": " + e.getMessage());
				}
			}
		}
		
		throw new IllegalPropertyValueException("Unable to find a setter method for " + memberName + " in value implementation " + valueImplementation.getCanonicalName() + ".");
	}

	@Override
	public final PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		if (javaValue == null)
			throw new ClassCastException("Cannot create a record propery value from a null value.");
		
		try {
			if (javaValue instanceof PropertyValue[])
				return createValueFromValues((PropertyValue[]) javaValue);
			if (javaValue instanceof String)
				return createValueFromString((String) javaValue);
			return createValueFromObject(javaValue);
		} 
		catch (NullPointerException e) {
			throw new ClassCastException("Cannot create a record propery value from a null value.");
		} 
		catch (IllegalPropertyValueException e) {
			throw new ClassCastException("Illegal value exception thrown when trying to create a record property value: " + e.getMessage());
		} 
		catch (BadTypeException e) {
			throw new ClassCastException("Bad type exception thrown when trying to create a record property value: " + e.getMessage());
		}
	}

	/**
	 * <p>Create a record type value of this type from a string representation.</p>
	 * 
	 * <p>This method expects
	 * the Java implementing class of the record type to implement a static method <code>parseFactory(String)</code>
	 * that can create an instance of the represented type from a string. This value is then passed into
	 * {@link #createValueFromObject(Object)}.</p>
	 * 
	 * @param content String representation of the record value.
	 * @return Newly created property value based on the given string.
	 * 
	 * @throws ClassCastException It was not possible to parse the string a create a value of this record type.
	 * 
	 * @see #createValue(Object)
	 * @see #createValueFromObject(Object)
	 */
	public final PropertyValue createValueFromString(
			String content) 
		throws ClassCastException {
		
		try {
			Class<?> implementation = majImplementations.get(specification);
			Method stringFactory = implementation.getMethod("parseFactory", String.class);
			return createValueFromObject(stringFactory.invoke(null, content));
		}
		catch (Exception e) {
			throw new ClassCastException("Cannot create a " + getName() + " record value from the given string '" + content + "' due to a " +
					e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	@Override
	public final TypeCategory getTypeCategory() {

		return TypeCategory.Record;
	}
		
	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
		throws NullPointerException,
			EndOfDataException {
		
		super.createFromBytes(buffer);
		
		if (implementation != null) {
			try {
				Method bufferCreateMethod = implementation.getMethod("createFromBuffer", ByteBuffer.class);
			
				return new RecordValue(this, bufferCreateMethod.invoke(null, buffer));
			}
			catch (NoSuchMethodException nme) {
				// Keep going
			}
			catch (InvocationTargetException ite) {
				
				Throwable cause = ite.getCause();
				if (cause instanceof EndOfDataException)
					throw (EndOfDataException) cause;
				if (cause instanceof NullPointerException)
					throw (NullPointerException) cause;
				if (cause instanceof RuntimeException)
					throw (RuntimeException) cause;
				throw new RuntimeException("Unexpected " + cause.getClass().getName() + " thrown when reading a record property value from a buffer: " + cause.getMessage());
			}
			catch (IllegalAccessException iae) {
				throw new RuntimeException("Illegal access exception thrown when reading a " + getName() + " value: " + iae.getMessage());
			}
		}
		
		PropertyValue[] values = new PropertyValue[memberNames.length];
		
		for ( int u = 0 ; u < memberNames.length ; u++ ) {
			
			String memberName = memberNames[u];
			TypeDefinition memberType = memberTypes.get(memberName);
			values[u] = memberType.createFromBytes(buffer);
		}
		
		return createValueFromValues(values);
	}
	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.lengthAsBytes(value);
		
		if (implementation != null) {
			try {
				Method bufferLengthMethod = 
					implementation.getMethod("lengthAsBuffer", getSpecification());
			
				return (Long) bufferLengthMethod.invoke(null, value.getValue());
			}
			catch (NoSuchMethodException nme) {
				// Keep going
			}
			catch (InvocationTargetException ite) {
				
				Throwable cause = ite.getCause();
				if (cause instanceof NullPointerException)
					throw (NullPointerException) cause;
				if (cause instanceof IllegalPropertyValueException)
					throw (IllegalPropertyValueException) cause;
				if (cause instanceof RuntimeException)
					throw (RuntimeException) cause;
				throw new RuntimeException("Unexpected " + cause.getClass().getName() + " thrown when calculating the length of a record property value: " + cause.getMessage());
			}
			catch (IllegalAccessException iae) {
				throw new RuntimeException("Illegal access exception thrown when calculating the length of a " + getName() + " value: " + iae.getMessage());
			}
		}
		
		long length = 0;
		
		for ( int u = 0 ; u < memberNames.length ; u++ ) {
			
			String memberName = memberNames[u];
			TypeDefinition memberType = memberTypes.get(memberName);
			length += memberType.lengthAsBytes(getValue(value, u));
		}

		return length;
	}
	
	@Override
	public List<PropertyValue> writeAsBytes(
			PropertyValue value,
			ByteBuffer buffer)
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		super.writeAsBytes(value, buffer);
		
		if (implementation != null) {
			try {
				Method bufferWriteMethod = 
					implementation.getMethod("writeToBuffer", 
							getSpecification(),
							ByteBuffer.class);
			
				bufferWriteMethod.invoke(null, value.getValue(), buffer);
				return null;
			}
			catch (NoSuchMethodException nme) {
				// Keep going
			}
			catch (InvocationTargetException ite) {
				
				Throwable cause = ite.getCause();
				if (cause instanceof NullPointerException)
					throw (NullPointerException) cause;
				if (cause instanceof IllegalPropertyValueException)
					throw (IllegalPropertyValueException) cause;
				if (cause instanceof InsufficientSpaceException)
					throw (InsufficientSpaceException) cause;
				if (cause instanceof RuntimeException)
					throw (RuntimeException) cause;
				throw new RuntimeException("Unexpected " + cause.getClass().getName() + " thrown when writing a record property value to a buffer: " + cause.getMessage());
			}
			catch (IllegalAccessException iae) {
				throw new RuntimeException("Illegal access exception thrown when writing a " + getName() + " value: " + iae.getMessage());
			}
		}

		long length = lengthAsBytes(value);

		if (buffer.remaining() < length)
			throw new InsufficientSpaceException("The given buffer is too small to write the given record value in to.");
		
		for ( int u = 0 ; u < memberNames.length ; u++ ) {
			
			String memberName = memberNames[u];
			TypeDefinition memberType = memberTypes.get(memberName);
			memberType.writeAsBytes(getValue(value, u), buffer);
		}
		
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
		
		if (implementation != null) {
			try {
				Method bufferWriteMethod = null;
				try {
					bufferWriteMethod = implementation.getMethod("writeToStructuredStorageBuffer", 
							getSpecification(),
							ByteBuffer.class);
				}
				catch (NoSuchMethodException nmess) {
					bufferWriteMethod = implementation.getMethod("writeToBuffer", 
							getSpecification(),
							ByteBuffer.class);
				}

				bufferWriteMethod.invoke(null, value.getValue(), buffer);
				return null;
			}
			catch (NoSuchMethodException nme) {
				// Keep going
			}
			catch (InvocationTargetException ite) {
				
				Throwable cause = ite.getCause();
				if (cause instanceof NullPointerException)
					throw (NullPointerException) cause;
				if (cause instanceof IllegalPropertyValueException)
					throw (IllegalPropertyValueException) cause;
				if (cause instanceof InsufficientSpaceException)
					throw (InsufficientSpaceException) cause;
				if (cause instanceof RuntimeException)
					throw (RuntimeException) cause;
				throw new RuntimeException("Unexpected " + cause.getClass().getName() + " thrown when writing a record property value to a buffer: " + cause.getMessage());
			}
			catch (IllegalAccessException iae) {
				throw new RuntimeException("Illegal access exception thrown when writing a " + getName() + " value: " + iae.getMessage());
			}
		}

		long length = lengthAsBytes(value);

		if (buffer.remaining() < length)
			throw new InsufficientSpaceException("The given buffer is too small to write the given record value in to.");
		
		for ( int u = 0 ; u < memberNames.length ; u++ ) {
			
			String memberName = memberNames[u];
			TypeDefinition memberType = memberTypes.get(memberName);
			memberType.writeAsBytes(getValue(value, u), buffer);
		}
		
		return null;
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionRecord");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
		
		Element members = XMLBuilder.createChild(typeElement, namespace, prefix, 
				"Members");
		
		for ( String memberName : memberNames ) {
			XMLBuilder.appendElement(members, namespace, prefix, 
					"Name", memberName);
			XMLBuilder.appendElement(members, namespace, prefix, 
					"Type", memberTypes.get(memberName).getName());
		}
	}
	
	public TypeDefinitionRecord clone() {
		
		return (TypeDefinitionRecord) super.clone();
	}
}
