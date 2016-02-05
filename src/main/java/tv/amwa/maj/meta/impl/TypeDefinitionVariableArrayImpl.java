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
 * $Log: TypeDefinitionVariableArrayImpl.java,v $
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
 * Revision 1.6  2010/11/18 10:49:53  vizigoth
 * Added support for dynamic meta dictionaries and type name mapping for legacy meta dictionary compatibility.
 *
 * Revision 1.5  2010/06/18 16:57:23  vizigoth
 * Improvements to setting primitive arrays types and ByteBuffer values.
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
 * Revision 1.1  2007/11/13 22:13:20  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
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
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionObjectReference;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.util.Utilities;


/** 
 * <p>Implements the definition of a property type that has a varying number of values of 
 * the underlying type. The order of the values is meaningful.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0209, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionVariableArray",
		  description = "The TypeDefinitionVariableArray class defines a property type that has a varying number of values of the underlying type. The order of the values is meaningful.",
		  symbol = "TypeDefinitionVariableArray")
public final class TypeDefinitionVariableArrayImpl 
	extends 
		TypeDefinitionImpl 
	implements 
		TypeDefinitionVariableArray,
		Serializable,
		XMLSerializable,
		Cloneable {

	public static class VariableArrayMethodBag
		extends MethodBag {
		
		private Method append = null;
		private Method prepend = null;
		private Method count = null;
		private Method insertAt = null;
		private Method removeAt = null;
		private Method getAt = null;
		private Method setter = null;
		private Method clear = null;
		
		public VariableArrayMethodBag(
				Method getter,
				Method[] candidateMethods,
				String propertyName) {
			
			super(getter, candidateMethods, propertyName);
			
			for ( Method candidate : candidateMethods ) {
				
				MediaListAppend appendCandidate = candidate.getAnnotation(MediaListAppend.class);
				if (appendCandidate != null) {
					if (appendCandidate.value().equals(propertyName)) {
						append = candidate;
						continue;
					}
				}
				
				MediaListPrepend prependCandidate = candidate.getAnnotation(MediaListPrepend.class);
				if (prependCandidate != null) {
					if (prependCandidate.value().equals(propertyName)) {
						prepend = candidate;
						continue;
					}
				}

				MediaPropertyCount countCandidate = candidate.getAnnotation(MediaPropertyCount.class);
				if (countCandidate != null) {
					if (countCandidate.value().equals(propertyName)) {
						count = candidate;
						continue;
					}
				}

				MediaListInsertAt insertAtCandidate = candidate.getAnnotation(MediaListInsertAt.class);
				if (insertAtCandidate != null) {
					if (insertAtCandidate.value().equals(propertyName)) {
						insertAt = candidate;
						continue;
					}
				}

				MediaListRemoveAt removeAtCandidate = candidate.getAnnotation(MediaListRemoveAt.class);
				if (removeAtCandidate != null) {
					if (removeAtCandidate.value().equals(propertyName)) {
						removeAt = candidate;
						continue;
					}
				}

				MediaListGetAt getAtCandidate = candidate.getAnnotation(MediaListGetAt.class);
				if (getAtCandidate != null) {
					if (getAtCandidate.value().equals(propertyName)) {
						getAt = candidate;
						continue;
					}
				}
				
				MediaPropertySetter setterCandidate = candidate.getAnnotation(MediaPropertySetter.class);
				if (setterCandidate != null) {
					if (setterCandidate.value().equals(propertyName)) {
						setter = candidate;
						continue;
					}
				}
				
				MediaPropertyClear clearCandidate = candidate.getAnnotation(MediaPropertyClear.class);
				if (clearCandidate != null) {
					if (clearCandidate.value().equals(propertyName)) {
						clear = candidate;
						continue;
					}
				}
			}
		}

		public void append(
				MetadataObject metadataObject,
				Object value) 
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {
			
			append.invoke(metadataObject, value);
		}

		public void prepend(
				MetadataObject metadataObject,
				Object value) 
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {
			
			prepend.invoke(metadataObject, value);
		}

		public int count(
				MetadataObject metadataObject) 
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {
			
			return (Integer) count.invoke(metadataObject);
		}

		public void insertAt(
				MetadataObject metadataObject,
				int index,
				Object value) 
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {
			
			insertAt.invoke(metadataObject, index, value);
		}

		public void removeAt(
				MetadataObject metadataObject,
				int index) 
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {
			
			removeAt.invoke(metadataObject, index);
		}

		public Object getAt(
				MetadataObject metadataObject,
				int index) 
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {
			
			return getAt.invoke(metadataObject, index);
		}

		public boolean hasSetter() {
			
			return (setter != null);
		}
		
		public void set(
				MetadataObject metadataObject,
				Object value) 
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {

			Class<?> parameterType = setter.getParameterTypes()[0];
			Class<?> componentType = parameterType.getComponentType();
			if ((parameterType.isArray()) && (value instanceof List<?>)) {
				
				if (componentType.isPrimitive()) {
					
					if (componentType.equals(Byte.TYPE)) {
						List<?> byteList = (List<?>) value;
						byte[] byteValues = new byte[byteList.size()];
						for ( int x = 0 ; x < byteValues.length ; x++ )
							byteValues[x] = (Byte) byteList.get(x);
						setter.invoke(metadataObject, byteValues);
						return;
					}

					if (componentType.equals(Short.TYPE)) {
						List<?> shortList = (List<?>) value;
						short[] shortValues = new short[shortList.size()];
						for ( int x = 0 ; x < shortValues.length ; x++ )
							shortValues[x] = (Short) shortList.get(x);
						setter.invoke(metadataObject, shortValues);
						return;
					}

					if (componentType.equals(Integer.TYPE)) {
						List<?> intList = (List<?>) value;
						int[] intValues = new int[intList.size()];
						for ( int x = 0 ; x < intValues.length ; x++ )
							intValues[x] = (Integer) intList.get(x);
						setter.invoke(metadataObject, intValues);
						return;
					}

					if (componentType.equals(Long.TYPE)) {
						List<?> longList = (List<?>) value;
						long[] longValues = new long[longList.size()];
						for ( int x = 0 ; x < longValues.length ; x++ )
							longValues[x] = (Long) longList.get(x);
						setter.invoke(metadataObject, longValues);
						return;
					}
					
					if (componentType.equals(Boolean.TYPE)) {
						List<?> booleanList = (List<?>) value;
						boolean[] booleanValues = new boolean[booleanList.size()];
						for ( int x = 0 ; x < booleanValues.length ; x++ )
							booleanValues[x] = (Boolean) booleanList.get(x);
						setter.invoke(metadataObject, booleanValues);
						return;
					}
					
					if (componentType.equals(Character.TYPE)) {
						List<?> charList = (List<?>) value;
						char[] charValues = new char[charList.size()];
						for ( int x = 0 ; x < charValues.length ; x++ )
							charValues[x] = (Character) charList.get(x);
						setter.invoke(metadataObject, charValues);
						return;
					}

					if (componentType.equals(Float.TYPE)) {
						List<?> floatList = (List<?>) value;
						float[] floatValues = new float[floatList.size()];
						for ( int x = 0 ; x < floatValues.length ; x++ )
							floatValues[x] = (Byte) floatList.get(x);
						setter.invoke(metadataObject, floatValues);
						return;
					}

					if (componentType.equals(Double.TYPE)) {
						List<?> doubleList = (List<?>) value;
						double[] doubleValues = new double[doubleList.size()];
						for ( int x = 0 ; x < doubleValues.length ; x++ )
							doubleValues[x] = (Byte) doubleList.get(x);
						setter.invoke(metadataObject, doubleValues);
						return;
					}

				}
					
				value = ((Collection<?>) value).toArray(
						(Object[]) Array.newInstance(componentType, 0));
			}
			
			if (parameterType.equals(ByteBuffer.class) && (value instanceof List<?>)) {
				List<?> byteList = (List<?>) value;
				byte[] byteValues = new byte[byteList.size()];
				for ( int x = 0 ; x < byteValues.length ; x++ )
					byteValues[x] = (Byte) byteList.get(x);
				setter.invoke(metadataObject, ByteBuffer.wrap(byteValues));
				return;
			}
			
			setter.invoke(metadataObject, value);
		}
		
		public void clear(
				MetadataObject metadataObject) 
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {
			
			if (clear == null) 
				System.err.println("Unable to call clear method for varaible array property " + getPropertyName() + ".");
			else
				clear.invoke(metadataObject);
		}
		
		public String getAppendName() {
			
			if (append == null) return null;
			return append.getName();
		}
		
		public String getClearName() {
			
			if (clear == null) return null;
			return clear.getName();
		}
		
		public String getCountName() {
			
			if (count == null) return null;
			return count.getName();
		}
		
		public String getPrependName() {
			
			if (prepend == null) return null;
			return prepend.getName();
		}
		                             
		public String getInsertAtName() {
			
			if (insertAt == null) return null;
			return insertAt.getName();
		}
		
		public String getRemoveAtName() {
			
			if (removeAt == null) return null;
			return removeAt.getName();
		}
		
		public String getGetAtName() {
			
			if (getAt == null) return null;
			return getAt.getName();
		}
		
		public String getSetterName() {
			
			if (setter == null) return null;
			return setter.getName();
		}	
	}
	
	/** <p></p> */
	private static final long serialVersionUID = 8075771349769610773L;

	private WeakReference<TypeDefinition> variableArrayElementType;
	
	protected TypeDefinitionVariableArrayImpl() { }
	
	/**
	 * <p>Creates and initializes the varying size array type definition, defines a property 
	 * type that has a varying number of values of the underlying type. The order of the 
	 * values is meaningful.</p>
	 * 
	 * <p>Note that it is only possible to use certain types as the 
	 * element type and an {@link IllegalArgumentException} is thrown if the
	 * given type is not permitted. The permissible types are:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionInteger TypeDefinitionInteger}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionRecord TypeDefinitionRecord}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionEnumeration TypeDefinitionEnumeration}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration TypeDefinitionExtendibleEnumeration}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionFixedArray TypeDefinitionFixedArray}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionObjectReference TypeDefinitionObjectReference}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionString TypeDefinitionString}</li>
	 * </ul>
	 * 
	 * <p>Creating new and unregistered type definitions is not recommended as this may cause
	 * interoperability issues with other systems. The official registry of type definitions 
	 * is available from SMPTE at <a href="http://www.smpte-ra.org/mdd/">SMPTE Metadata Registries 
	 * And Related Items</a>. The full range of data types documented in the AAF 1.1 object 
	 * specification can be accessed by name and identification using either
	 * {@link tv.amwa.maj.industry.Warehouse#lookForTypeString)} or
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(tv.amwa.maj.record.AUID)}
	 * respectively.</p>
	 * 
	 * @param identification AUID to be used to identify this type.
	 * @param typeName Friendly name of the type definition.
	 * @param elementType Type of each element to be contained in the array.
	 * 
	 * @throws NullPointerException The identification and/or element type arguments is/are 
	 * <code>null</code>.
	 * @throws IllegalArgumentException The given element type is not permitted
	 * for elements of a varying size array.
	 */
	public TypeDefinitionVariableArrayImpl(
			AUID identification,
			@AAFString String typeName,
			TypeDefinition elementType)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new variable size array type definition with a null identification.");
		if (elementType == null)
			throw new NullPointerException("Cannot create a new variable size array type definition with a null element type.");
		
		setIdentification(identification);
		setName(typeName);
		
		setElementType(elementType);
	}
	
	public static class VariableArrayValue
		extends PropertyValueImpl
		implements PropertyValue {
		
		private TypeDefinitionVariableArrayImpl type;
		private List<Object> value;
		
		private VariableArrayValue(
				TypeDefinitionVariableArrayImpl type,
				List<Object> propertyValue) {
			
			this.type = type;
			setValue(propertyValue);
		}
		
		public TypeDefinitionImpl getType() {

			return type;
		}
		
		public List<Object> getValue() {

			return value;
		}
		
		public boolean isDefinedType() {

			return true;
		}
		
		private void setValue(
				List<Object> value) {
			
			this.value = value;
		}
	}
	
	public void appendElement(
			tv.amwa.maj.industry.PropertyValue arrayProperty,
			tv.amwa.maj.industry.PropertyValue element)
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot append to null variable size array value.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
		if (element == null)
			throw new NullPointerException("Cannot append a null element to a variable size array value.");
		
		if (!(variableArrayElementType.equals(element.getType())))
			throw new BadTypeException("The element type of the variable size array value does not match the type of the element to append.");
		
		Object value = (element instanceof 
				TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) ? element : element.getValue();
		
		if (arrayProperty.getValue() == null) {
			
			List<Object> values = Collections.synchronizedList(new Vector<Object>(1));
			values.add(value);
			((VariableArrayValue) arrayProperty).setValue(values);
		}
		else
			((VariableArrayValue) arrayProperty).getValue().add(value);
	}

	public PropertyValue createEmptyValue() {

		return new VariableArrayValue(
				this,
				Collections.synchronizedList(new Vector<Object>()));
	}

	public PropertyValue createValueFromArray(
			Object[] initialData)
		throws ClassCastException {

		if (initialData == null)
			return new VariableArrayValue(this, null);
		
		List<Object> values = Collections.synchronizedList(new Vector<Object>());
		for ( int x = 0 ; x < initialData.length ; x++ ) {
			
			variableArrayElementType.getTarget().createValue(initialData[x]);
			values.add(initialData[x]);
		}
		
		return new VariableArrayValue(this, values);
	}

	public PropertyValue createValueFromList(
			List<Object> initialData) {
		
		if (initialData == null)
			return new VariableArrayValue(this, null);

		for ( Object element : initialData ) 
			variableArrayElementType.getTarget().createValue(element);
		
		List<Object> values = 
			Collections.synchronizedList(new Vector<Object>(initialData));

		return new VariableArrayValue(this, values);

	}
	
	public tv.amwa.maj.industry.PropertyValue createValueFromValues(
			tv.amwa.maj.industry.PropertyValue[] elementValues)
		throws BadTypeException {

		if (elementValues == null)
			return new VariableArrayValue(this, null);
		
		for ( PropertyValue value : elementValues )
			if (!(variableArrayElementType.equals(value.getType())))
				throw new BadTypeException("At least one of the elements of the given property values array does not match the element type of this variable size array type definition.");
		
		List<Object> values = Collections.synchronizedList(new Vector<Object>());
		for ( int x = 0 ; x < elementValues.length ; x++ )
			values.add(elementValues[x].getValue());
		
		return new VariableArrayValue(this, values);
	}

	public int getCount(
			tv.amwa.maj.industry.PropertyValue arrayProperty) 
		throws NullPointerException,
			IllegalPropertyValueException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot count the number of elements in a null variable size array property value.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
		
		List<Object> values = ((VariableArrayValue) arrayProperty).getValue();
		if (values == null)
			return 0;
		else
			return values.size();
	}

	public tv.amwa.maj.industry.PropertyValue getElementValue(
			tv.amwa.maj.industry.PropertyValue arrayProperty,
			int index)
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException,
			ClassCastException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot retrieve an element from a null variable size array.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
	
		List<Object> variableArray = ((VariableArrayValue) arrayProperty).getValue();
		if (variableArray == null)
			throw new IndexOutOfBoundsException("The variable array value is representing a not present value (null).");
		try {
			if (TypeDefinitions.UTF16StringArray.equals(this))
				return TypeDefinitions.UTF16String.createValue(variableArray.get(index));
			return variableArrayElementType.getTarget().createValue(variableArray.get(index));
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the given variable size array.");
		}
	}

	public List<PropertyValue> getElements(
			tv.amwa.maj.industry.PropertyValue arrayProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot retrieve the elements of a null variable size array.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
		
		List<Object> arrayValue = ((VariableArrayValue) arrayProperty).getValue();
		
		List<PropertyValue> elementValues;
		
		if (arrayValue != null) {
			elementValues =  new Vector<PropertyValue>(arrayValue.size());
			for ( Object value : arrayValue ) 
				if (TypeDefinitions.UTF16StringArray.equals(this))
					elementValues.add(TypeDefinitions.UTF16String.createValue(value));
				else
					elementValues.add(variableArrayElementType.getTarget().createValue(value));
		}
		else
			elementValues = new Vector<PropertyValue>();
		
		return elementValues;
	}
	
	public Object[] getArray(
			tv.amwa.maj.industry.PropertyValue arrayProperty)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot retrieve the elements of a null variable size array.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
		
		List<Object> valueList = ((VariableArrayValue) arrayProperty).getValue();
		
		return valueList.toArray();
	}
	
	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0d00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "VariableArrayElementType",
			aliases = { "ElementType" },
			typeName = "TypeDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0019,
			symbol = "VariableArrayElementType")
	public TypeDefinition getType() {

		return variableArrayElementType.getTarget();
	}

	/**
	 * <p>Set the underlying element type for this variable size array type definition. All elements
	 * of a value of this type must be of the given element type.</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionInteger TypeDefinitionInteger}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionRecord TypeDefinitionRecord}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionEnumeration TypeDefinitionEnumeration}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration TypeDefinitionExtendibleEnumeration}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionFixedArray TypeDefinitionFixedArray}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionObjectReference TypeDefinitionObjectReference}</li>
	 *  <li>{@link tv.amwa.maj.meta.TypeDefinitionString TypeDefinitionString}</li>
	 * </ul>
	 *
	 * @param elementType Underlying element type for this variable size array type definition.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws IllegalArgumentException Cannot set the element type of this variable size array type definition to the given 
	 * element type.
	 */
	@MediaPropertySetter("VariableArrayElementType")
	public void setElementType(
			TypeDefinition elementType) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (elementType == null)
			throw new NullPointerException("Cannot set the element type of this variable size array type definition using a null value.");
		
//		if (elementType instanceof TypeDefinitionUnresolved) {
//			this.variableArrayElementType = (TypeDefinitionUnresolved) elementType;
//			return;
//		}
		
		if ((!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionInteger)) &&
				(!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionRecord)) &&
				(!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionEnumeration)) &&
				(!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration)) &&
				(!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionFixedArray)) &&
				(!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionObjectReference)) &&
				(!(elementType instanceof tv.amwa.maj.meta.TypeDefinitionCharacter)))
			throw new IllegalArgumentException("Cannot set the element type of this variable size array type definition to the given element type.");
		
		this.variableArrayElementType = new WeakReference<TypeDefinition>(elementType);
	}
	
	public final static TypeDefinition initializeVariableArrayElementType() {
		
		return TypeDefinitions.Int32;
	}
	
	public void insertElement(
			tv.amwa.maj.industry.PropertyValue arrayProperty,
			int index,
			tv.amwa.maj.industry.PropertyValue element)
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException,
			BadTypeException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot insert an element into a null variable size array.");
		if (element == null)
			throw new NullPointerException("Cannot insert a null propery value into a variable size array.");
		if (arrayProperty.getValue() == null)
			throw new NullPointerException("Cannot insert a value into a null-valued variable sized array. Use appendElement() or prependElement() instead.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
		if (!(variableArrayElementType.equals(element.getType())))
			throw new BadTypeException("The given element cannot be inserted into the variable size array because its type does not match the element type.");
		
		Object value = (element instanceof 
				TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) ? element : element.getValue();
		
		try {
			((VariableArrayValue) arrayProperty).getValue().add(index, value);
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index for element insertion is outside the acceptable range for the given variable size array.");
		}
	}

	public void prependElement(
			tv.amwa.maj.industry.PropertyValue arrayProperty,
			tv.amwa.maj.industry.PropertyValue element)
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot insert an element into a null variable size array.");
		if (element == null)
			throw new NullPointerException("Cannot prepend a null propery value into a variable size array.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
		if (!(variableArrayElementType.equals(element.getType())))
			throw new BadTypeException("The given element cannot be prepended into the variable size array because its type does not match the element type.");

		Object value = (element instanceof 
				TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) ? element : element.getValue();
		
		if (arrayProperty.getValue() == null) {
			
			List<Object> values = Collections.synchronizedList(new Vector<Object>(1));
			values.add(value);
			((VariableArrayValue) arrayProperty).setValue(values);
		}
		else
			((VariableArrayValue) arrayProperty).getValue().add(0, value);
	}

	public void removeElement(
			tv.amwa.maj.industry.PropertyValue arrayProperty,
			int index)
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot remove an element from a null variable size array.");
		if (arrayProperty.getValue() == null)
			throw new NullPointerException("Cannot remove an element from a null-valued variable size array.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
		
		try {
			((VariableArrayValue) arrayProperty).getValue().remove(index);
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the given variable size array.");
		}
	}

	public void setElementAt(
			tv.amwa.maj.industry.PropertyValue arrayProperty,
			int index,
			tv.amwa.maj.industry.PropertyValue element)
		throws NullPointerException,
			IllegalPropertyValueException,
			IndexOutOfBoundsException,
			BadTypeException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot replace an element from a null variable size array.");
		if (element == null)
			throw new NullPointerException("Cannot replace a value in a variable size array with a null propery value.");
		if (arrayProperty.getValue() == null)
			throw new NullPointerException("Cannot set an element at the given index in a null-valued variable sized array property value.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
		if (!(variableArrayElementType.equals(element.getType())))
			throw new BadTypeException("The given property value to use to replace an existing property if of a type that does not match the underlying element type of this variable size array type definition.");
		
		Object value = (element instanceof 
				TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) ? element : element.getValue();
		
		try {
			((VariableArrayValue) arrayProperty).getValue().set(index, value);
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the given variable size array.");
		}
	}
	
	public void setArray(
			tv.amwa.maj.industry.PropertyValue arrayProperty,
			Object[] data)
		throws NullPointerException,
			IllegalPropertyValueException,
			ClassCastException {

		if (arrayProperty == null)
			throw new NullPointerException("Cannot set the value of a null variable size array.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
		
		if (data == null) 
			((VariableArrayValue) arrayProperty).setValue(null);
		else
			((VariableArrayValue) arrayProperty).setValue(
					((VariableArrayValue) createValue(data)).getValue());
	}

	public void setArray(
			tv.amwa.maj.industry.PropertyValue arrayProperty,
			List<Object> data)
		throws NullPointerException,
			IllegalPropertyValueException,
			ClassCastException {
		
		if (arrayProperty == null)
			throw new NullPointerException("Cannot set the value of a null variable size array.");
		if (!(equals(arrayProperty.getType())))
			throw new IllegalPropertyValueException("The given property value type does not match this variable size array property definition.");
		
		if (data == null)
			((VariableArrayValue) arrayProperty).setValue(null);
		else
			((VariableArrayValue) arrayProperty).setValue(
					((VariableArrayValue) createValue(data)).getValue());		
	}

	// TODO test this all for weak reference vectors
	
	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		if (javaValue == null)
			return new VariableArrayValue(this, null);

		if ((getName().equals("DataValue")) && (javaValue instanceof String)) 
			javaValue = Utilities.hexStringToBytes((String) javaValue);
		
		List<Object> values = Collections.synchronizedList(new Vector<Object>());
		
		if (javaValue.getClass().isArray()) {
			List<Object> objectList = new Vector<Object>(Array.getLength(javaValue));
			for ( int u = 0 ; u < Array.getLength(javaValue) ; u++ )
				objectList.add(Array.get(javaValue, u));
			javaValue = objectList;
		}
		
		if (javaValue instanceof Collection<?>) {
			for ( Object item : (Collection<?>) javaValue ) {
				
				if (item instanceof PropertyValue) {
					if (!(((PropertyValue) item).getType().equals(variableArrayElementType)))
						throw new ClassCastException("An element of the given collection to create a new variable array with has a type that does not match the property type of this variable array type definition.");
					values.add(((PropertyValue) item).getValue());
				}
				else {
					// Deal with special case UTF16StringArray where base type is character.
					if (TypeDefinitions.UTF16StringArray.equals(this))
						TypeDefinitions.UTF16String.createValue(item);
					else
						variableArrayElementType.getTarget().createValue(item);

					values.add(item);
				}
			}
		}
		else {
			if (javaValue instanceof PropertyValue) {
				if (!(((PropertyValue) javaValue).getType().equals(variableArrayElementType)))
					throw new ClassCastException("The given property value to make a new variable array with has a type that does not match the property type of this variable array type definition.");
				values.add(((PropertyValue) javaValue).getValue());
			}
			else {
				variableArrayElementType.getTarget().createValue(javaValue);
				values.add(javaValue);
			}
		}
		
		return new VariableArrayValue(this, values);
	}

	@Override
	public TypeCategory getTypeCategory() {

		return TypeCategory.VariableArray;
	}

	@Override
	MethodBag makeMethodBag(Method getter, Method[] candidateMethods,
			String propertyName) {

		return new VariableArrayMethodBag(getter, candidateMethods, propertyName);
	}

	@Override
	public void setPropertyValue(
			MetadataObject metadataObject,
			PropertyDefinition property, 
			PropertyValue value)
		throws IllegalArgumentException, 
			IllegalAccessException,
			InvocationTargetException {

		VariableArrayMethodBag methods = (VariableArrayMethodBag) ((PropertyDefinitionImpl) property).getMethodBag();
		
		if (methods.hasSetter()) {
			
			methods.set(metadataObject, value.getValue());
			return;
		}
		
		methods.clear(metadataObject);
		
		for ( Object nextElement : ((VariableArrayValue) value).getValue() )
			methods.append(metadataObject, nextElement);
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
		throws EndOfDataException {

		PropertyValue arrayValue = createEmptyValue();
		
		try {
			int setCount = buffer.getInt();
			int elementSize = buffer.getInt();

			if (this.equals(TypeDefinitions.UInt8Array)) {
				if (elementSize > 1) {
					setCount = elementSize;
					elementSize = 1;
				}
			}
			
			for ( int u = 0 ; u < setCount ; u++ ) {
				int preservedLimit = buffer.limit();
				try {
					buffer.limit(buffer.position() + elementSize);
					PropertyValue elementValue = variableArrayElementType.getTarget().createFromBytes(buffer);
					appendElement(arrayValue, elementValue);
				}
				finally {
					buffer.limit(preservedLimit);
				}
			}
		}
		catch (BufferUnderflowException bue) {
			throw new EndOfDataException("Reached the end of the buffer before a variable array was written. Buffer underflow exception: " +
					bue.getMessage());
		}

		return arrayValue;
	}
	
	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.lengthAsBytes(value);
		
		long length = 8;
		if (getCount(value) == 0) return length;
		
		PropertyValue sampleElement = null;
		for ( PropertyValue temporaryElement : getElements(value) ) {
			sampleElement = temporaryElement;
			break;
		}
		
		length += sampleElement.getType().lengthAsBytes(sampleElement) * getCount(value);			
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

		if (buffer.remaining() < lengthAsBytes(value)) 
			throw new InsufficientSpaceException("Insufficient space to write the given variable array value to the given buffer.");
		
		int count = getCount(value);
		buffer.putInt(count);
		
		if (count == 0) { // Cannot work out length of elements for empty array ... default to 16
			buffer.putInt(16);
			return null;
		}
		
		PropertyValue sampleElement = null;
		for ( PropertyValue temporaryElement : getElements(value) ) {
			sampleElement = temporaryElement;
			break;
		}
		
		buffer.putInt((int) sampleElement.getType().lengthAsBytes(sampleElement));
		
		List<PropertyValue> arrayStrongReferences = new Vector<PropertyValue>(count);
		for ( PropertyValue elementValue : getElements(value)) {
			elementValue.getType().writeAsBytes(elementValue, buffer);
			if (elementValue.getType() instanceof TypeDefinitionStrongObjectReference)
				arrayStrongReferences.add(elementValue);
		}
		
		return arrayStrongReferences;
	}
	
	@Override
	public boolean resolveReferences(
			PropertyValue value,
			Map<AUID, MetadataObject> referenceMap) 
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.resolveReferences(value, referenceMap);
		
		boolean allResolved = true;
		
		List<Object> setValue = ((VariableArrayValue) value).getValue();
		List<Object> elements = new Vector<Object>(setValue);
		
		for ( int x = 0 ; x < elements.size() ; x++ ) {
			
			if (elements.get(x) instanceof TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) {
				
				TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue unresolvedReference = 
					(TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) elements.get(x);
				if (referenceMap.containsKey(unresolvedReference.getValue())) {
					setValue.remove(x);
					setValue.add(x, referenceMap.get(unresolvedReference.getValue()));
				}
				else {
					System.err.println("Unable to resolve reference " + unresolvedReference.getValue().toString() +
							" for a varialbe array.");
					allResolved = false;
				}
			}
		}
		
		return allResolved;
	}
	
	@Override
	public String nameToAAFName(
			String name) {
		
		if (getType() instanceof TypeDefinitionObjectReference)
			return "kAAFTypeID_" + super.nameToAAFName(name);
		// TODO is appending AAF here a bodge or a rule?
		return "aaf" + super.nameToAAFName(name);
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionVariableArray");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
		
		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"ElementType", variableArrayElementType.getTarget().getName());
	}
	
	public TypeDefinitionVariableArray clone() {
		
		return (TypeDefinitionVariableArray) super.clone();
	}
}
