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
 * $Log: TypeDefinitionImpl.java,v $
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
 * Revision 1.5  2010/11/18 10:49:53  vizigoth
 * Added support for dynamic meta dictionaries and type name mapping for legacy meta dictionary compatibility.
 *
 * Revision 1.4  2010/05/14 18:30:28  vizigoth
 * Provided mechanism for a difference between structured storage byte writing and KLV byte writing.
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
 * Revision 1.4  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.2  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.1  2007/11/13 22:13:36  vizigoth
 * Public release of MAJ API.
 */

/**
 * 
 */
package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.io.aaf.AAFConstants;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.record.AUID;


// TODO add externalisation to match reference implementation (if required?)
// TODO comments
// TODO play around with whether property values need to be public

/** 
 * <p>Implements the definition of a property type.</p>
 * 
 * @see tv.amwa.maj.industry.TypeDefinitionWarehouse
 *
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionWeakReferenceVector
 *
 */
@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0203, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinition",
		  description = "The TypeDefinition class defines a property type.",
		  symbol = "TypeDefinition")
public abstract class TypeDefinitionImpl 
	extends 
		MetaDefinitionImpl 
	implements 
		TypeDefinition,
		Serializable,
		Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 963203215988237810L;

	private static Map<String, String> typeNameMap = null;
	
	public final static void initalizeTypeNameMap() {
		if (AAFConstants.MAP_SS_TYPE_NAMES) {
		
			int typeMapSize = AAFConstants.typeNameAliases.length / 2;
			typeNameMap = 
				Collections.synchronizedMap(new HashMap<String, String>(typeMapSize));
			for ( int x = 0 ; x < typeMapSize ; x++ )
				typeNameMap.put(AAFConstants.typeNameAliases[x * 2], 
						AAFConstants.typeNameAliases[x * 2 + 1]);
		}
	}

	public static abstract class PropertyValueImpl
		implements PropertyValue {
		
		public boolean equals(
				Object o) {
			
			if (o == null) return false;
			if (o == this) return true;
			if (!(o instanceof PropertyValue)) return false;
			
			PropertyValue testValue = (PropertyValue) o;
			
			if (!(getType().equals(testValue.getType()))) return false;
			if ((getValue() == null) && (testValue.getValue() == null)) return true;
			if (!(getValue().equals(testValue.getValue()))) return false;
			return true;
		}
		
		public int hashCode() {
			
			if (getValue() == null)
				return getType().hashCode();
			return getType().hashCode() ^ getValue().hashCode();
		}
		
		public String toString() {
			
			if (getValue() == null) return "null";
			return getValue().toString();
		}
	}
	
	public abstract TypeCategory getTypeCategory();

	/**
	 * <p>Create a property value from the given Java object. If the type of
	 * the object is not compatible with this type definition, a {@link ClassCastException}
	 * is thrown.</p>
	 *
	 * @param javaValue Java object to use to create a property value of this type.
	 * @return Property value representing the given value.
	 * 
	 * @throws ClassCastException The given object cannot be cast to a property value with
	 * this type definition.
	 */
	public abstract PropertyValue createValue(
			Object javaValue)
		throws ClassCastException;
	
//	static class TypeDefinitionUnresolved
//		extends TypeDefinitionImpl {
//
//		/** <p></p> */
//		private static final long serialVersionUID = -3770433558768358121L;
//		
//		private AUIDImpl identification;
//		private String symbolName;
//		
//		TypeDefinitionUnresolved(
//				String idAndSymbol) 
//			throws IllegalArgumentException,
//				IndexOutOfBoundsException,
//				NumberFormatException {
//			
//			idAndSymbol = idAndSymbol.trim();
//			identification = AUIDImpl.parseFactory(idAndSymbol.substring(0, 45));
//			symbolName = idAndSymbol.substring(46);
//		}
//		
//		@Override
//		public PropertyValue createValue(
//				Object javaValue)
//				throws ClassCastException {
//
//			return null;
//		}
//
//		@Override
//		public TypeCategory getTypeCategory() {
//
//			return null;
//		}
//		
//		public AUIDImpl getIdentification() {
//			
//			return identification;
//		}
//		
//		public String getSymbolName() {
//			
//			return symbolName;
//		}
//		
//		public String toString() {
//			return "Unresolved type reference: " + identification.toString() + " " + symbolName;
//		}
//
//		@Override
//		PropertyValue getPropertyValue(MetadataObject metadataObject,
//				PropertyDefinitionImpl property) {
//
//			return null;
//		}
//
//		@Override
//		void setPropertyValue(MetadataObject metadataObject,
//				PropertyDefinitionImpl property, PropertyValue value) {
//			
//		}
//
//		@Override
//		MethodBag makeMethodBag(Method getter, Method[] candidateMethods,
//				String propertyName) {
//
//			System.err.println("Method bag creation called before types are fully resolved.");
//			return null;
//		}
//
//		@Override
//		public PropertyValue createFromBytes(ByteBuffer buffer) {
//			
//			return null;
//		}
//	}

	public abstract void setPropertyValue(
			MetadataObject metadataObject,
			PropertyDefinition property,
			PropertyValue value)
		throws IllegalArgumentException, 
			IllegalAccessException, 
			InvocationTargetException;
	
	public PropertyValue getPropertyValue(
			MetadataObject metadataObject,
			PropertyDefinition property) 
		throws IllegalArgumentException, 
			IllegalAccessException, 
			InvocationTargetException {

		/* try { */
			MethodBag methods = ((PropertyDefinitionImpl) property).getMethodBag();
			Object baseValue = methods.get(metadataObject);
			return createValue(baseValue);
		/* }
		catch (InvocationTargetException ite) {
			
			System.err.println(ite.getCause().getClass().getName() + ": " + ite.getCause().getMessage());
			throw ite;
		} */
	}
	
	static MethodBag makeMethodBagForType(
			TypeDefinition type,
			Method getter,
			Method[] candidateMethods,
			String propertyName) {
		
		return ((TypeDefinitionImpl) type).makeMethodBag(getter, candidateMethods, propertyName);
	}
	
	abstract MethodBag makeMethodBag(
			Method getter,
			Method[] candidateMethods,
			String propertyName);
	
	public PropertyValue createFromBytes(
			ByteBuffer buffer)
		throws NullPointerException,
			EndOfDataException {
		
		if (buffer == null)
			throw new NullPointerException("Cannot create a value from a null byte buffer.");
		
		return null;
	}
	
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
		
		return null;
	}
	
	public List<PropertyValue> writeAsStructuredStorageBytes(			
			PropertyValue value,
			ByteBuffer buffer) 
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		return writeAsBytes(value, buffer);
	}
	
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
	
		if (value == null)
			throw new NullPointerException("Cannot provide the length in bytes of a null value.");
		if (!value.getType().equals(this))
			throw new IllegalPropertyValueException("The given property value to find the serialized length of is not the " +
					"same as this type. " + value.getType().getName() + " != " + getName() + ".");
		
		return 0l;
	}
	
	public boolean resolveReferences(
			PropertyValue value,
			Map<AUID, MetadataObject> referenceMap) 
		throws NullPointerException,
			IllegalPropertyValueException { 
		
		if (value == null)
			throw new NullPointerException("Cannot resolve references for a null property.");
		if (referenceMap == null)
			throw new NullPointerException("Cannot resolve references using a null reference map.");
		if (!value.getType().equals(this))		
			throw new IllegalPropertyValueException("The given property value for property resolution is not the same as this type. " +
					value.getType().getName() + " != " + getName() + ".");

		return true;
	}

	@Override
	public String nameToAAFName(
			String name) {
		
		if (typeNameMap != null) {
			String mappedName =typeNameMap.get(name);
			return (mappedName != null) ? mappedName : name;
		}
		
		return name;
	}
	
	@Override
	public String getPrefix() {
		
		if (prefix == null)
			return "unknown";
		else return prefix;
	}
	
	@Override
	public String getNamespace() {
		
		if (namespace == null)
			return "unknown";
		else return namespace;
	}
	
	public TypeDefinition clone() {
		
		return (TypeDefinition) super.clone();
	}
	
}
