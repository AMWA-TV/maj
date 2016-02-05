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
 * $Log: TypeDefinitionObjectReferenceImpl.java,v $
 * Revision 1.5  2011/07/27 17:39:27  vizigoth
 * Only add strong references to the list of property values required to be written, not weak references.
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
 * Revision 1.3  2010/03/19 16:13:53  vizigoth
 * Added methods for writing bytes and calculating lengths.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.3  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.2  2007/12/04 09:45:45  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:13:34  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.HiddenClass;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.io.mxf.FixedLengthPack;
import tv.amwa.maj.io.mxf.MXFBuilder;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.TypeDefinitionObjectReference;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;


/** 
 * <p>Implements the definition of a property type that defines an object 
 * relationship.</p>
 *
 *
 *
 */
@HiddenClass
public abstract class TypeDefinitionObjectReferenceImpl 
	extends 
		SingletonTypeDefinitionImpl
	implements 
		tv.amwa.maj.meta.TypeDefinitionObjectReference,
		XMLSerializable,
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9088016822594218143L;
	
	private WeakReference<ClassDefinition> referencedType;
	
	public static class ObjectReferenceValue
		extends PropertyValueImpl
		implements PropertyValue {

		private TypeDefinitionObjectReferenceImpl type;
		private Object value;
		private AUID localReference;
		
		ObjectReferenceValue(
				TypeDefinitionObjectReferenceImpl type,
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
		
		void setValue(
				Object value) {
			
			this.value = value;
			if (value instanceof WeakReferenceTarget)
				localReference = ((WeakReferenceTarget) value).getAUID();
			else
				localReference = AUIDImpl.randomAUID();
		}
		
		/** 
		 * <p>Method included so that methods of {@link java.util.Set} will work as 
		 * expected.</p>
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object o) {
			
			// TODO test this with null
			if (o instanceof ObjectReferenceValue)
				return value.equals(((ObjectReferenceValue) o).getValue());
			else
				return value.equals(o);
		}
		
		/** 
		 * <p>Method included so that methods of {@link java.util.Set} will work as 
		 * expected.</p>
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			
			if (value == null) {
				System.err.println("Asked for a null hashcode.");
				return 0;
			}
			else
				return value.hashCode();
		}
		
		public AUID getLocalReference() {
			
			return localReference;
		}
	}
	
	public static class UnresolvedReferenceValue
		extends PropertyValueImpl
		implements PropertyValue {

		private TypeDefinitionObjectReferenceImpl type;
		private AUID value;

		UnresolvedReferenceValue(
				TypeDefinitionObjectReferenceImpl type,
				AUID reference) {

			this.type = type;
			setValue(reference);
		}

		public tv.amwa.maj.meta.TypeDefinition getType() {

			return type;
		}

		public AUID getValue() {

			return value;
		}

		public boolean isDefinedType() {

			return true;
		}

		private void setValue(
				AUID value) {

			this.value = value;
		}

		/** 
		 * <p>Method included so that methods of {@link java.util.Set} will work as 
		 * expected.</p>
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object o) {

			// TODO test this with null
			if (o instanceof UnresolvedReferenceValue)
				return value.equals(((UnresolvedReferenceValue) o).getValue());
			else
				return value.equals(o);
		}

		/** 
		 * <p>Method included so that methods of {@link java.util.Set} will work as 
		 * expected.</p>
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {

			return value.hashCode();
		}
	}

	public tv.amwa.maj.industry.PropertyValue createValue(
			Object object)
		throws ClassCastException {

		if (object == null)
			return new ObjectReferenceValue(this, null);
		
		if (object instanceof UnresolvedReferenceValue)
			return (UnresolvedReferenceValue) object;
		
		try {
			if (!(referencedType.getTarget().getJavaImplementation().isInstance(object)))
				throw new ClassCastException("Cannot cast the given object to a value that can be referenced from this object reference type definition.");
		} catch (PropertyNotPresentException e) {
			throw new ClassCastException("The referenced type implementation does not have an associated Java implementation so it is not possible to check if the given object is compatable.");
		}

		return new ObjectReferenceValue(this, object);
	}

	public Object getObject(
			tv.amwa.maj.industry.PropertyValue propertyValue)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (propertyValue == null)
			throw new NullPointerException("Cannot return the referenced value of a null object reference property value.");
		if (!(equals(propertyValue.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		
		return propertyValue.getValue();
	}

	public ClassDefinition getObjectType() {

		return referencedType.getTarget();
	}
	
	void setReferencedType(
			ClassDefinition referencedType) {
		
		if (referencedType == null)
			throw new NullPointerException("Cannot set the referenced type of a value of an object reference type definition to a null value.");
		
		this.referencedType = new WeakReference<ClassDefinition>(referencedType);
	}

	public void setObject(
			PropertyValue propertyValue,
			Object object)
		throws NullPointerException,
			IllegalPropertyValueException,
			ClassCastException {

		if (propertyValue == null)
			throw new NullPointerException("Cannot set the referenced value of a null object reference property value.");
		if (!(equals(propertyValue.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		
		if (object == null) {
			((ObjectReferenceValue) propertyValue).setValue(null);
			return;
		}
		
		// FIXME shoud be more forgiving and use interfaces ... and elsewhere in the API
		try {
			if (!(referencedType.getTarget().getJavaImplementation().isInstance(object)))
				throw new ClassCastException("Cannot use the given value as the target of an object reference because it cannot be cast to the referenced type of this object refernce type definition.");
		} catch (PropertyNotPresentException e) {
			throw new ClassCastException("The referenced type implementation does not have an associated Java implementation so it is not possible to check if the given object is compatable.");
		}
		
		((ObjectReferenceValue) propertyValue).setValue(object);
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
		throws NullPointerException,
			EndOfDataException {

		super.createFromBytes(buffer);
		
		if (FixedLengthPack.class.isAssignableFrom(getObjectType().getJavaImplementation())) {
			
			return createValue(MXFBuilder.readFixedLengthPack(getObjectType().getAUID(), buffer));
		}
		
		if (buffer.remaining() < 16)
			throw new EndOfDataException("Cannot create a reference from a buffer containing less than 16 bytes.");
		
		if (buffer.remaining() == 21) { // From AAF
			buffer.position(buffer.position() + 5);
			int data1 = buffer.getInt();
			short data2 = buffer.getShort();
			short data3 = buffer.getShort();
			byte[] data4 = new byte[8];
			buffer.get(data4);
			return new UnresolvedReferenceValue(this, new AUIDImpl(data1, data2, data3, data4));
		}
		
		byte[] refBytes = new byte[16];
		buffer.get(refBytes);
		return new UnresolvedReferenceValue(this, new AUIDImpl(refBytes));
	}
	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {

		super.lengthAsBytes(value);
		
		return 16l;
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
			throw new InsufficientSpaceException("Insufficient space in buffer to write a 16 byte reference.");
		
		AUID reference = getLocalReference(value);
		
		if (reference.isUniversalLabel()) 
			buffer.put(((UL) reference).getUniversalLabel());
		else
			buffer.put(reference.getAUIDValue());
		
		List<PropertyValue> singleReference = new Vector<PropertyValue>(1);
		if (this instanceof TypeDefinitionStrongObjectReference) 
			singleReference.add(value);
		return singleReference;
	}
	
	public final static AUID getLocalReference(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		if (value == null)
			throw new NullPointerException("Cannot find the local reference for a null property value.");
		
		if (!((value instanceof ObjectReferenceValue) || 
			(value instanceof UnresolvedReferenceValue)))
			throw new IllegalPropertyValueException("Cannot resolve a local reference from the wrong kind  of property value: " + 
					value.getClass().getName());
		
		AUID reference = null;
		if (value instanceof UnresolvedReferenceValue) 
			reference = ((UnresolvedReferenceValue) value).getValue();
		else
			reference = ((ObjectReferenceValue) value).getLocalReference();

		return reference;
	}
	
	public TypeDefinitionObjectReference clone() {
		
		return (TypeDefinitionObjectReference) super.clone();
	}
}
