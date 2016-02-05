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
 * $Log: TypeDefinitionSetImpl.java,v $
 * Revision 1.7  2011/07/27 17:41:46  vizigoth
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
 * Revision 1.7  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.6  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.5  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.4  2008/01/14 20:55:20  vizigoth
 * Change to type category enumeration element names.
 *
 * Revision 1.3  2007/12/04 13:04:53  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.2  2007/12/04 09:45:45  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:13:13  vizigoth
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.BadTypeException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyContains;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionObjectReference;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;

// TODO consider what happens when one or more of the methods is not defined

/** 
 * <p>Implements the definition of a property type that has a collection of object references to 
 * uniquely identified objects. The order of the objects has no meaning.</p>
 *
 *
 *
 * @see MediaSetAdd
 * @see MediaPropertyRemove
 * @see MediaPropertyCount
 * @see MediaPropertyContains
 * @see MediaPropertyClear
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x020a, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionSet",
		  description = "The TypeDefinitionSet class defines a property type that has a collection of object references to uniquely identified objects. The order of the objects has no meaning.",
		  symbol = "TypeDefinitionSet")
public final class TypeDefinitionSetImpl 
	extends 
		TypeDefinitionImpl 
	implements 
		TypeDefinitionSet, 
		Serializable,
		Cloneable {
	
	/** <p></p> */
	private static final long serialVersionUID = 7032285999083401616L;

	public static class SetMethodBag
		extends MethodBag {

		private Method add = null;
		private Method count = null;
		private Method contains = null;
		private Method remove = null;
		private Method clear = null;
		private Method setter = null;

		public SetMethodBag(
				Method getter,
				Method[] candidateMethods,
				String propertyName) {

			super(getter, candidateMethods, propertyName);

			for ( Method candidate : candidateMethods ) {

				MediaSetAdd addCandidate = candidate.getAnnotation(MediaSetAdd.class);
				if (addCandidate != null) {
					if (addCandidate.value().equals(propertyName)) {
						add = candidate;
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

				MediaPropertyContains containsCandidate = candidate.getAnnotation(MediaPropertyContains.class);
				if (containsCandidate != null) {
					if (containsCandidate.value().equals(propertyName)) {
						contains = candidate;
						continue;
					}
				}

				MediaPropertyRemove removeCandidate = candidate.getAnnotation(MediaPropertyRemove.class);
				if (removeCandidate != null) {
					if (removeCandidate.value().equals(propertyName)) {
						remove = candidate;
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

				MediaPropertySetter setterCandidate = candidate.getAnnotation(MediaPropertySetter.class);
				if (setterCandidate != null) {
					if (setterCandidate.value().equals(propertyName)) {
						setter = candidate;
						continue;
					}
				}

			}	
		}

		public void add(
				MetadataObject metadataObject,
				Object value) 
		throws IllegalArgumentException, 
			IllegalAccessException, 
			InvocationTargetException {

			add.invoke(metadataObject, value);
		}

		public int count(
				MetadataObject metadataObject) 
		throws IllegalArgumentException, 
		IllegalAccessException, 
		InvocationTargetException {

			return (Integer) count.invoke(metadataObject);
		}

		public void contains(
				MetadataObject metadataObject,
				Object value) 
		throws IllegalArgumentException, 
		IllegalAccessException, 
		InvocationTargetException {

			contains.invoke(metadataObject, value);
		}

		public void remove(
				MetadataObject metadataObject,
				Object value) 
		throws IllegalArgumentException, 
			IllegalAccessException, 
			InvocationTargetException {

			remove.invoke(metadataObject, value);
		}

		public void clear(
				MetadataObject metadataObject) 
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {
			
			if (clear != null)
				clear.invoke(metadataObject);
		}
			
		public void set(
				MetadataObject metadataObject,
				Set<Object> values)
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {

			setter.invoke(metadataObject, values);
		}
		
		public boolean hasSetter() {
			
			return (setter != null);
		}

		public String getAddName() {
			
			if (add == null) return null;
			return add.getName();
		}

		public String getCountName() {
			
			if (count == null) return null;
			return count.getName();
		}
		
		public String getClearName() {
			
			if (clear == null) return null;
			return clear.getName();
		}
		
		public String getContainsName() {
			
			if (contains == null) return null;
			return contains.getName();
		}
		
		public String getRemoveName() {
			
			if (remove == null) return null;
			return remove.getName();
		}
	}

	private WeakReference<TypeDefinition> setElementType;
	
	protected TypeDefinitionSetImpl() { }
	
	/**
	 * <p>Creates and initializes the set type definition, which defines a property type 
	 * that has a collection of object references to uniquely identified objects. The order of 
	 * the objects has no meaning.</p>
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
	 * @param identification AUID to be used to identify this type of set.
	 * @param typeName Friendly name for this type definition.
	 * @param elementType Type definition of each element contained in this set.
	 * 
	 * @throws NullPointerException The identification and/or element type arguments is/are
	 * <code>null</code>.
	 */
	public TypeDefinitionSetImpl(
			AUID identification,
			@AAFString String typeName,
			TypeDefinition elementType) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new set type definition with a null identification.");
		if (elementType == null)
			throw new NullPointerException("Cannot create a new set type definition with a null element type.");

		setIdentification(identification);
		setName(typeName);
		setElementType(elementType);
	}

	public static class SetValue
		extends PropertyValueImpl
		implements PropertyValue {

		private TypeDefinitionSetImpl type;
		private Set<Object> value;
		
		private SetValue(
				TypeDefinitionSetImpl type,
				Set<Object> value) {
			
			this.type = type;
			setValue(value);
		}
		
		public tv.amwa.maj.meta.TypeDefinition getType() {

			return type;
		}

		public Set<Object> getValue() {
			
			return value;
		}
		
		public boolean isDefinedType() {

			return true;
		}
		
		private void setValue(
				Set<Object> value) {
			
			this.value = value;
		}
	}
	
	public void addElement(
			tv.amwa.maj.industry.PropertyValue setPropertyValue,
			tv.amwa.maj.industry.PropertyValue elementPropertyValue)
		throws NullPointerException, 
			IllegalPropertyValueException {

		if (setPropertyValue == null)
			throw new NullPointerException("Cannot add an element to a null set property value.");
		if (!(equals(setPropertyValue.getType())))
			throw new IllegalPropertyValueException("The type of the given set property value does not match this set type definition.");
		if (elementPropertyValue == null)
			throw new NullPointerException("Cannot add a null element property value to a set.");
		if (!(setElementType.equals(elementPropertyValue.getType())))
			throw new IllegalPropertyValueException("The type of the given element property value does not match the element type of this set type definition.");
		
		if (elementPropertyValue instanceof 
				TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue)
			((SetValue) setPropertyValue).value.add(elementPropertyValue);
		else
			((SetValue) setPropertyValue).value.add(elementPropertyValue.getValue());
	}

	public boolean containsElement(
			tv.amwa.maj.industry.PropertyValue setPropertyValue,
			tv.amwa.maj.industry.PropertyValue elementPropertyValue)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (setPropertyValue == null)
			throw new NullPointerException("Cannot check containment in a null set property value.");
		if (!(equals(setPropertyValue.getType())))
			throw new IllegalPropertyValueException("The type of the given set property value does not match this set type definition.");
		if (elementPropertyValue == null)
			throw new NullPointerException("Cannot check to see whether a null element property value is contained in a set.");
		if (!(setElementType.equals(elementPropertyValue.getType())))
			return false;
		
		return ((SetValue) setPropertyValue).getValue().contains(elementPropertyValue.getValue());
	}

	public int getCount(
			tv.amwa.maj.industry.PropertyValue setPropertyValue) 
		throws NullPointerException,
			IllegalPropertyValueException {

		if (setPropertyValue == null)
			throw new NullPointerException("Cannot count the elements in a null set property value.");
		if (!(equals(setPropertyValue.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this set type definition.");
		
		Set<?> setValue = ((SetValue) setPropertyValue).getValue();

		if (setValue == null)
			return -1;
		else
			return setValue.size();
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0e00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "SetElementType",
			aliases = { "ElementType" },
			typeName = "TypeDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x001A,
			symbol = "SetElementType")
	public TypeDefinition getElementType() {

		return setElementType.getTarget();
	}

	@MediaPropertySetter("SetElementType")
	public void setElementType(
			TypeDefinition elementType) 
		throws NullPointerException {
		
		if (elementType == null)
			throw new NullPointerException("Cannot set the element type of this set type definition to null.");
		
		this.setElementType = new WeakReference<TypeDefinition>(elementType);
	}

	public final static TypeDefinition initializeSetElementType() {
		
		return TypeDefinitions.UTF16String;
	}
	
	public Set<tv.amwa.maj.industry.PropertyValue> getElements(
			tv.amwa.maj.industry.PropertyValue setPropertyValue) 
		throws NullPointerException,
			IllegalPropertyValueException {

		if (setPropertyValue == null)
			throw new NullPointerException("Cannot extract the elements of a null set property value.");
		if (!(equals(setPropertyValue.getType())))
			throw new IllegalPropertyValueException("The type of the given property does not match this set property value.");
		
		Set<Object> setValue = ((SetValue) setPropertyValue).getValue();
		Set<PropertyValue> elementValues;

		if (setValue != null) {
			elementValues = new HashSet<PropertyValue>(setValue.size());
			for ( Object element : setValue )
					elementValues.add(setElementType.getTarget().createValue(element));
		}
		else
			elementValues = new HashSet<PropertyValue>();
		
		return elementValues;
	}

	public void removeElement(
			tv.amwa.maj.industry.PropertyValue setPropertyValue,
			tv.amwa.maj.industry.PropertyValue elementPropertyValue)
		throws NullPointerException,
			IllegalPropertyValueException,
			BadTypeException,
			ObjectNotFoundException {

		if (setPropertyValue == null)
			throw new NullPointerException("Cannot remove an element from a null set property value.");
		if (!(equals(setPropertyValue.getType())))
			throw new IllegalPropertyValueException("The type of the given property does not match this set property value.");
		if (elementPropertyValue == null)
			throw new NullPointerException("Cannot remove an element from a set property value using a null element property value.");
		if (!(setElementType.equals(elementPropertyValue.getType())))
			throw new BadTypeException("The type of the given element property value does not match the element type of this set type definition.");
		
		Set<Object> setValue = ((SetValue) setPropertyValue).getValue();
		if ((setValue == null) ||
				(!(setValue.contains(elementPropertyValue.getValue()))))
			throw new ObjectNotFoundException("The given set property value does not contain the given element to remove.");
		
		setValue.remove(elementPropertyValue.getValue());
	}

	public PropertyValue createEmptySet() {
		
		return new SetValue(
				this, 
				Collections.synchronizedSet(new HashSet<Object>()));
	}

	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		if (javaValue == null)
			throw new ClassCastException("Cannot create a null set. Use createEmptySet() to create an empty set for use in optional properties.");
		
		Set<Object> creation = null;
		
		if (javaValue instanceof PropertyValue) {
			if (!(((PropertyValue) javaValue).getType().equals(setElementType)))
				throw new ClassCastException("Cannot cast the given property value to the value of a singleton set as its value does not match the element type of this type definition.");
			else {
				creation = Collections.synchronizedSet(new HashSet<Object>());
				creation.add(((PropertyValue) javaValue).getValue());
			}
		}
		else if (!(javaValue.getClass().isArray()) &&
				(!(javaValue instanceof Collection<?>))) {
			
			setElementType.getTarget().createValue(javaValue);
			creation = Collections.synchronizedSet(new HashSet<Object>());
			creation.add(javaValue);
		}
		
		if (javaValue.getClass().isArray()) {
			List<Object> objectList = new Vector<Object>(Array.getLength(javaValue));
			for ( int u = 0 ; u < Array.getLength(javaValue) ; u++ )
				objectList.add(Array.get(javaValue, u));
			javaValue = objectList;
		}
		
		if (javaValue instanceof Collection<?>) {
			
			Collection<?> javaCollection = (Collection<?>) javaValue;
			creation = Collections.synchronizedSet(new HashSet<Object>());
			
			for ( Object element : javaCollection ) {
				
				if (element instanceof PropertyValue) {
					if (!(((PropertyValue) element).getType().equals(setElementType)))
						throw new ClassCastException("An element of the given collection to create a new set with has a type that does not match the property type of this set type definition.");
					creation.add(((PropertyValue) element).getValue());
				}
				else {
					setElementType.getTarget().createValue(element);
					creation.add(element);
				}
			}
		}
			
		if (creation != null)
			return new SetValue(this, creation);
		else
			throw new ClassCastException("Cannot cast the given Java value to a set property value.");
	}

	@Override
	public TypeCategory getTypeCategory() {

		return TypeCategory.Set;
	}

	@Override
	MethodBag makeMethodBag(
			Method getter, 
			Method[] candidateMethods,
			String propertyName) {

		return new SetMethodBag(getter, candidateMethods, propertyName);
	}

	@Override
	public void setPropertyValue(
			MetadataObject metadataObject,
			PropertyDefinition property, 
			PropertyValue value)
		throws IllegalArgumentException, 
			IllegalAccessException,
			InvocationTargetException {

		SetMethodBag methods = (SetMethodBag) ((PropertyDefinitionImpl) property).getMethodBag();
		methods.clear(metadataObject);
		
		if (methods.hasSetter()) {
			methods.set(metadataObject, ((SetValue) value).getValue());
			return;
		}
		
		for ( PropertyValue nextElement : getElements(value) )
			methods.add(metadataObject, nextElement.getValue());
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
		throws EndOfDataException {

		PropertyValue setValue = createEmptySet();
		
		try {
			int setCount = buffer.getInt();
			@SuppressWarnings("unused") // have to read size even if it is not used
			int elementSize = buffer.getInt();

			for ( int u = 0 ; u < setCount ; u++ ) {
				PropertyValue elementValue = setElementType.getTarget().createFromBytes(buffer);
				addElement(setValue, elementValue);
			}
		}
		catch (BufferUnderflowException bue) {
			throw new EndOfDataException("Buffer too small to write a set value. Buffer underflow exception: " + 
					bue.getMessage());
		}
		
		return setValue;
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
			throw new InsufficientSpaceException("Insufficient space to write the given set value to the given buffer.");
		
		int count = getCount(value);
		buffer.putInt(count);
		
		if (count == 0) { // Cannot work out length of elements for empty set ... default to 16
			buffer.putInt(16);
			return null;
		}
		
		PropertyValue sampleElement = null;
		for ( PropertyValue temporaryElement : getElements(value) ) {
			sampleElement = temporaryElement;
			break;
		}
		
		buffer.putInt((int) sampleElement.getType().lengthAsBytes(sampleElement));
		
		List<PropertyValue> setStrongReferences = new Vector<PropertyValue>(count);
		for ( PropertyValue elementValue : getElements(value)) {
			elementValue.getType().writeAsBytes(elementValue, buffer);
			if (elementValue.getType() instanceof TypeDefinitionStrongObjectReference)
				setStrongReferences.add(elementValue);
		}
		
		return setStrongReferences;
	}
	
	@Override
	public boolean resolveReferences(
			PropertyValue value,
			Map<AUID, MetadataObject> referenceMap) 
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.resolveReferences(value, referenceMap);
		
		boolean allResolved = true;
		
		Set<Object> setValue = ((SetValue) value).getValue();
		List<Object> elements = new Vector<Object>(setValue);
		
		for ( Object element : elements ) {
			
			if (element instanceof TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) {
				
				TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue unresolvedReference = 
					(TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) element;
				if (referenceMap.containsKey(unresolvedReference.getValue())) {
					setValue.remove(element);
					setValue.add(referenceMap.get(unresolvedReference.getValue()));
				}
				else {
					System.err.println("Unable to resolve reference " + unresolvedReference.getValue().toString() +
							" for a set.");
					allResolved = false;
				}
			}
		}
		
		return allResolved;
	}
	
	public String nameToAAFName(
			String name) {
		
		if (getElementType() instanceof TypeDefinitionObjectReference)
			return "kAAFTypeID_" + super.nameToAAFName(name);
		return super.nameToAAFName(name);
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionSet");
		
		super.appendMetadictXML(typeElement, namespace, prefix);

		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"ElementType", setElementType.getTarget().getName());
	}
	
	public TypeDefinitionSet clone() {
		
		return (TypeDefinitionSet) super.clone();
	}
}
