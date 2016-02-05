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
 * $Log: TypeDefinitionRenameImpl.java,v $
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
 * Revision 1.1  2007/11/13 22:13:26  vizigoth
 * Public release of MAJ API.
 */

/**
 * 
 */
package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.EndOfDataException;
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
import tv.amwa.maj.meta.TypeDefinitionRename;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;

/** 
 * <p>Implements the definition of a property type that has the same structure and representation as 
 * its underlying type but has a different meaning.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x020e, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionRename",
		  description = "The TypeDefinitionRename class defines a property type that has the same structure and representation as its underlying type but has a different meaning.",
		  symbol = "TypeDefinitionRename")
public final class TypeDefinitionRenameImpl 
	extends 
		SingletonTypeDefinitionImpl // TODO check this
	implements 
		TypeDefinitionRename,
		Serializable,
		Cloneable {
	
	/** <p></p> */
	private static final long serialVersionUID = -6856048217810442695L;
	
	private WeakReference<TypeDefinition> renamedType;
	
	protected TypeDefinitionRenameImpl() { }
	
	/**
	 * <p>Creates and initializes a renamed type definition, which defines a property 
	 * type that has the same structure and representation as its underlying type but has 
	 * a different meaning.</p>
	 * 
	 * @param identification AUID to be used to identify this type.
	 * @param typeName Friendly name of the rename type definition.
	 * @param baseType Underlying base type this rename type definition is an
	 * alias for.
	 * 
	 * @throws NullPointerException The identification and/or the base type arguments 
	 * is/are <code>null</code>.
	 */
	public TypeDefinitionRenameImpl(
			AUID identification,
			@AAFString String typeName,
			TypeDefinition baseType) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new rename type definition using a null identification.");
		if (baseType == null)
			throw new NullPointerException("Cannot create a new rename type definition using a null renamed type value.");
		
		setIdentification(identification);
		setName(typeName);
		setRenamedType(baseType);
	}
	
	public static class RenamedValue
		extends PropertyValueImpl
		implements PropertyValue {

		private TypeDefinitionRenameImpl type;
		private Object value;
		
		private RenamedValue(
				TypeDefinitionRenameImpl type,
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
	
	public tv.amwa.maj.industry.PropertyValue createValueFromBaseValue(
			tv.amwa.maj.industry.PropertyValue propertyValue) 
		throws NullPointerException, 
			IllegalPropertyValueException {
		
		if (propertyValue == null)
			throw new NullPointerException("Cannot create a new renamed type property value from a null base property value.");
		if (!(renamedType.equals(propertyValue.getType())))
			throw new IllegalPropertyValueException("The given property value is not compatable with the base type of this rename type definition.");
		
		return new RenamedValue(this, propertyValue.getValue());
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "RenamedType",
			typeName = "TypeDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x001E,
			symbol = "RenamedType")
	public TypeDefinition getBaseType() {

		return renamedType.getTarget();
	}

	@MediaPropertySetter("RenamedType")
	public void setRenamedType(
			TypeDefinition renamedType) 
		throws NullPointerException {
		
		if (renamedType == null)
			throw new NullPointerException("Cannot set the renamed type of a rename type definition to null.");
		
		// TODO what happens with unresolved types here?
		this.renamedType = new WeakReference<TypeDefinition>(renamedType);
	}
	
	public final static TypeDefinition initializeRenamedType() {
		
		return TypeDefinitions.UTF16String;
	}
	
	public tv.amwa.maj.industry.PropertyValue getBaseValue(
			tv.amwa.maj.industry.PropertyValue propertyValue)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (propertyValue == null)
			throw new NullPointerException("Cannot extract the actual value from a null renamed type property value.");
		if (!(equals(propertyValue.getType())))
			throw new IllegalPropertyValueException("The type of the given property value does not match this type definition.");
		
		Object renamedValue = ((RenamedValue) propertyValue).getValue();
		
		if (renamedValue == null) return null;
		
		return renamedType.getTarget().createValue(renamedValue);
	}

	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		PropertyValue renamedTypeValue = renamedType.getTarget().createValue(javaValue);
		return new RenamedValue(this, renamedTypeValue.getValue());
	}

	@Override
	public TypeCategory getTypeCategory() {

		return TypeCategory.Rename;
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
		throws EndOfDataException {

		return createValueFromBaseValue(renamedType.getTarget().createFromBytes(buffer));
	}

	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.lengthAsBytes(value);
		
		PropertyValue baseValue = getBaseValue(value);
		return baseValue.getType().lengthAsBytes(baseValue);
	}
	
	@Override
	public List<PropertyValue> writeAsBytes(
			PropertyValue value,
			ByteBuffer buffer) 
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		super.writeAsBytes(value, buffer);
		
		PropertyValue baseValue = getBaseValue(value);
		baseValue.getType().writeAsBytes(baseValue, buffer);
		
		return null;
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
				prefix, "TypeDefinitionRename");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
		
		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"RenamedType", renamedType.getTarget().getName());
	}
	
	public TypeDefinitionRename clone() {
		
		return (TypeDefinitionRename) super.clone();
	}
	
}
