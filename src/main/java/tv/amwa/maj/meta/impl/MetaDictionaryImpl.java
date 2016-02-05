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
 * $Log: MetaDictionaryImpl.java,v $
 * Revision 1.7  2011/11/04 09:54:16  vizigoth
 * Updates to enable writing AAF files from objects with AMP metadata.
 *
 * Revision 1.6  2011/10/07 19:43:51  vizigoth
 * Fix to allow meta dictionaries to be dynamically created when application objects are present.
 *
 * Revision 1.5  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.4  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
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
 * Revision 1.3  2010/11/18 10:49:53  vizigoth
 * Added support for dynamic meta dictionaries and type name mapping for legacy meta dictionary compatibility.
 *
 * Revision 1.2  2010/03/19 16:14:44  vizigoth
 * Fixed incorrect keys on property definitions.
 *
 * Revision 1.1  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.industry.AAFSpecifiedClasses;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.aaf.AAFConstants;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.meta.MetaDictionary;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionEnumeration;
import tv.amwa.maj.meta.TypeDefinitionFixedArray;
import tv.amwa.maj.meta.TypeDefinitionObjectReference;
import tv.amwa.maj.meta.TypeDefinitionRecord;
import tv.amwa.maj.meta.TypeDefinitionRename;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionString;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.meta.impl.TypeDefinitionIndirectImpl.IndirectValue;
import tv.amwa.maj.meta.impl.TypeDefinitionSetImpl.SetValue;
import tv.amwa.maj.meta.impl.TypeDefinitionVariableArrayImpl.VariableArrayValue;
import tv.amwa.maj.model.ApplicationObject;
import tv.amwa.maj.model.ApplicationPluginObject;
import tv.amwa.maj.model.ApplicationReferencedObject;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.record.AUID;

/**
 * <p>Contains definitions of meta classes and types.</p>
 * 
 *
 *
 */
@MediaClass(definedName = "MetaDictionary",
		uuid1 = 0x0D010101, uuid2 = 0x0225, uuid3 = 0x0000,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01 },
		description = "The meta dictionary class is deprecated and replaced by extension schemes and the baseline dictionary.",
		symbol = "MetaDictionary",
		namespace = CommonConstants.AAF_XML_NAMESPACE,
		prefix = CommonConstants.AAF_XML_PREFIX)
public class MetaDictionaryImpl 
	implements 
		MetaDictionary,
		MetadataObject, 
		Cloneable,
		Serializable {

	private boolean dynamic = false;
	private Set<ClassDefinition> classDefinitions = null;
	private Set<TypeDefinition> typeDefinitions = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MetaDictionaryImpl() { }
	
	public int countClassDefinitions() {

		return tv.amwa.maj.meta.impl.ClassDefinitionImpl.countClassDefinitions();
	}

	public int countOpaqueTypeDefinitions() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int countTypeDefinitions() {

		return (dynamic) ? typeDefinitions.size() : Warehouse.countTypeDefinitions();
	}

	public void createForwardClassReference(
			AUID classId)
			throws InvalidParameterException {
		// TODO Auto-generated method stub

	}

	@Deprecated
	public InterchangeObject createInstance(
			AUID identification)
		throws NullPointerException,
			IllegalArgumentException {

		ClassDefinition classToCreate = Warehouse.lookForClass(identification);
		
		if (classToCreate == null)
			throw new IllegalArgumentException("The given class identifier does not match a class known to this meta dictionary.");
		
		if ((dynamic) && (!classDefinitions.contains(classToCreate)))
			throw new IllegalArgumentException("The given class identifier exists but is not in this dynamic meta dictionary.");
		
		MetadataObject created = classToCreate.createInstance();
		
		if (!(created instanceof InterchangeObject))
			throw new IllegalArgumentException("The created class is not an interchange object.");
		
		return (InterchangeObject) created;
	}

	@Deprecated
	public MetaDefinition createMetaInstance(
			AUID identification)
		throws NullPointerException,
			IllegalArgumentException {
	
		ClassDefinition classToCreate = Warehouse.lookForClass(identification);
	
		if (classToCreate == null)
			throw new IllegalArgumentException("The given class identifier does not match a class known to this meta dictionary.");
	
		if ((dynamic) && (!classDefinitions.contains(classToCreate)))
			throw new IllegalArgumentException("The given class identifier exists but is not in this dynamic meta dictionary.");
	
		MetadataObject created = classToCreate.createInstance();
	
		if (!(created instanceof MetaDefinition))
			throw new IllegalArgumentException("The created class is not a meta definition.");
	
		return (MetaDefinition) created;
	}

	@MediaProperty(definedName = "ClassDefinitions",
			uuid1 = 0x06010107, uuid2 = 0x0700, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 },
			symbol = "ClassDefinitions",
			typeName = "ClassDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 3)
	public Set<? extends ClassDefinition> getClassDefinitions() {
		
		if (!dynamic)
			return new HashSet<ClassDefinition>(tv.amwa.maj.meta.impl.ClassDefinitionImpl.getClassDefinitions());
		return classDefinitions;
	}

	public Set<? extends TypeDefinition> getOpaqueTypeDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@MediaProperty(definedName = "TypeDefinitions",
			uuid1 = 0x06010107, uuid2 = 0x0800, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 },
			symbol = "TypeDefinitions",
			typeName = "TypeDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 4)
	public Set<? extends TypeDefinition> getTypeDefinitions() {
		
		if (!dynamic)
			return new HashSet<TypeDefinition>(Warehouse.getTypeDefinitions());
		return typeDefinitions;
	}

	public boolean hasForwardClassReference(AUID classId)
			throws InvalidParameterException {
		// TODO Auto-generated method stub
		return false;
	}

	public ClassDefinition lookupClassDefinition(
			AUID identification)
		throws NullPointerException, 
			InvalidParameterException {

		if (identification == null)
			throw new NullPointerException("Cannot lookup a class definition with a null value.");
		
		ClassDefinition classDefinition = tv.amwa.maj.meta.impl.ClassDefinitionImpl.forAUID(identification);
		
		if (classDefinition != null) return classDefinition;
		
		throw new InvalidParameterException("The given identifier does not match that of a known class definition.");
	}

	public TypeDefinition lookupOpaqueTypeDefinition(
			AUID typeId)
		throws NullPointerException, 
			InvalidParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeDefinition lookupTypeDefinition(
			AUID identification)
		throws NullPointerException, 
			InvalidParameterException {

		if (identification == null)
			throw new NullPointerException("Cannot lookup a type definition with a null value.");
		
		TypeDefinition typeDefinition = Warehouse.lookForType(identification);
		
		if (typeDefinition != null) return typeDefinition;
		
		throw new InvalidParameterException("The given identifier does not match that of a known type definition.");
	}

	public void registerClassDefinition(
			ClassDefinition classDefinition)
		throws NullPointerException,
			InvalidParameterException {
		
		if (classDefinition == null)
			throw new NullPointerException("Cannot register a class definition using a null value.");

		if (tv.amwa.maj.meta.impl.ClassDefinitionImpl.forAUID(classDefinition.getAUID()) != null)
			throw new InvalidParameterException("The given class definition is already registered in the meta dictionary.");
		
		Warehouse.lookForClass(classDefinition.getJavaImplementation());
	}

	public void registerOpaqueTypeDefinition(
			TypeDefinition typeDef)
		throws NullPointerException, 
			InvalidParameterException {
		// TODO

	}

	public void registerTypeDefinition(
			TypeDefinition typeDefinition)
		throws NullPointerException, 
			InvalidParameterException {
		
		if (typeDefinition == null)
			throw new NullPointerException("Cannot register a class definition using a null value.");

		if (Warehouse.lookForType(typeDefinition.getAUID()) != null)
			throw new InvalidParameterException("The given type definition is already registered in the meta dictionary.");

		Warehouse.register(typeDefinition, typeDefinition.getNamespace(), typeDefinition.getPrefix());
	}
	
	public void makeDynamic(
			Preface preface) 
		throws NullPointerException {
		
		if (preface == null)
			throw new NullPointerException("Cannot use a null preface value to make this meta dictionary dynamic.");
		
		classDefinitions = Collections.synchronizedSet(new HashSet<ClassDefinition>());
		typeDefinitions = Collections.synchronizedSet(new HashSet<TypeDefinition>());
		
		for ( Class<?> metaClass : AAFSpecifiedClasses.abstractMeta )
			classDefinitions.add(Warehouse.lookForClass(metaClass));
		
		for ( Class<?> metaClass : AAFSpecifiedClasses.existingMeta )
			classDefinitions.add(Warehouse.lookForClass(metaClass));
		
		addMetaDefinitions(preface);
		// typeDefinitions.add(tv.amwa.maj.industry.TypeDefinitions.UInt8Array12);
		
		typeDefinitions.remove(TypeDefinitions.ApplicationPluginObjectStrongReference);
		typeDefinitions.remove(TypeDefinitions.ApplicationPluginObjectStrongReferenceSet);
		
		dynamic = true;
	}
	
	private void addMetaDefinitions(
			MetadataObject mdObject) {
		
		if (mdObject == null) return;
		
		SortedMap<? extends PropertyDefinition,? extends PropertyValue> values = null;
		
		if (mdObject instanceof ApplicationObject) {
			ApplicationObject appObject = (ApplicationObject) mdObject;
			addClassHierarchy(appObject.getObjectClass());
			values = ((ApplicationObject) mdObject).getProperties();
			for ( PropertyDefinition property : values.keySet() ) {
//				System.out.println("*+*: Processing meta property definition :" + property.getName());
				typeDefinitions.add(property.getTypeDefinition());
				
				ClassDefinition memberOf = property.getMemberOf();
				if (memberOf != null) {
					if (!classDefinitions.contains(memberOf))
						classDefinitions.add(memberOf.clone());
					
					for ( ClassDefinition toBeWrittenMemberOf : classDefinitions ) {
						if (toBeWrittenMemberOf.getAUID().equals(memberOf.getAUID())) {
		
							try {
								toBeWrittenMemberOf.lookupPropertyDefinition(property.getAUID());
							}
							catch (BadParameterException bpe) {
//								System.out.println("*+*: Adding property " + property.getName() + " to class " + toBeWrittenMemberOf.getName());
								((ClassDefinitionImpl) toBeWrittenMemberOf).addPropertyDefinition(property);
							}
						}
					}
				}
					
			}
		}
		else {
			ClassDefinition targetClass = MediaEngine.getClassDefinition(mdObject);
			addClassHierarchy(targetClass);
		
			for ( PropertyDefinition property : targetClass.getAllPropertyDefinitions() ) {
				TypeDefinition propertyType = property.getTypeDefinition();
				if (propertyType == null) 
					System.err.println("When making a dynamic preface, adding null type for property '" + targetClass.getName() + ":" +
							property.getName() + "'.");
				typeDefinitions.add(propertyType);
			}
			values = targetClass.getProperties(mdObject);
		}
		
		for ( PropertyDefinition property : values.keySet() ) {
			
			if (property.getAUID().equals(AAFConstants.ObjectClassID)) continue;				
			
			TypeDefinition type = property.getTypeDefinition();
			if (type == null) 
				System.err.println("When making a dynamic preface, adding null type for property '" + property.getName() + "'.");
			typeDefinitions.add(type);
			PropertyValue value = null;
			
			switch (type.getTypeCategory()) {
			
			case WeakObjRef:
			case StrongObjRef:
				value = values.get(property);
				// TODO check this is the correct condition
				if (!(value.getValue() instanceof ClassDefinition))
					addMetaDefinitions((MetadataObject) value.getValue());
				break;
			case VariableArray:
				TypeDefinitionVariableArray arrayType = (TypeDefinitionVariableArray) type;
				TypeDefinition arrayRefType = arrayType.getType();
				if (arrayRefType == null) System.err.println("When making a dynamic preface, adding null sub type for array ref property '" + property.getName() + "'.");
				typeDefinitions.add(arrayRefType);
				
				value = values.get(property);
				List<Object> list = ((VariableArrayValue) value).getValue();
				
				for ( Object listItem : list )
					if (listItem instanceof MetadataObject)
						addMetaDefinitions((MetadataObject) listItem);
				break;
			case Set:
				TypeDefinitionSet setType = (TypeDefinitionSet) type;
				TypeDefinition setRefType = setType.getElementType();
				typeDefinitions.add(setRefType);
				if (setRefType == null) System.err.println("When making a dynamic preface, adding null sub type for set ref property '" + property.getName() + "'.");
				value = values.get(property);
				Set<Object> elements = ((SetValue) value).getValue();
				
				for (Object element : elements )
					if (element instanceof MetadataObject)
						addMetaDefinitions((MetadataObject) element);
				break;
			case FixedArray:
				TypeDefinition fixedRefType = ((TypeDefinitionFixedArray) type).getType();
				if (fixedRefType == null) System.err.println("When making a dynamic preface, adding null sub type for fixed ref property '" + property.getName() + "'.");
				typeDefinitions.add(fixedRefType);
				break;
			case Indirect:
				value = values.get(property);
				PropertyValue indirectValue = ((IndirectValue) value).getValue();
				TypeDefinition indirectType = indirectValue.getType();
				if (indirectType == null) System.err.println("When making a dynamic preface, adding null sub type for indirect property '" + property.getName() + "'.");
				typeDefinitions.add(indirectType);
				if (indirectValue.getValue() instanceof MetadataObject)
					addMetaDefinitions((MetadataObject) indirectValue.getValue());
				break;

			default:
				break;
			
			}
		}
	}
	
	private void addClassHierarchy(
			ClassDefinition baseClass) {
		
		if (baseClass.equals(Warehouse.lookForClass(ApplicationPluginObject.class)) || 
				(baseClass.equals(Warehouse.lookForClass(ApplicationReferencedObject.class))))
			return;
		
		if (classDefinitions.contains(baseClass)) return;
		
		classDefinitions.add(baseClass.clone());
		if (!baseClass.isRoot())
			addClassHierarchy(baseClass.getParent());
		
		for ( PropertyDefinition property : baseClass.getPropertyDefinitions() ) {
			
			TypeDefinition type = property.getTypeDefinition();
			if (type != null)
				addType(type);
		}
	}
	
	private void addType(
			TypeDefinition type) {
		
		if (typeDefinitions.contains(type)) return;
		
		typeDefinitions.add(type);
		
		switch (type.getTypeCategory()) {
		
		case WeakObjRef:
		case StrongObjRef:
			addClassHierarchy(((TypeDefinitionObjectReference) type).getObjectType());
			break;
		case Rename:
			addType(((TypeDefinitionRename) type).getBaseType());
			break;
		case Enum:
			addType(((TypeDefinitionEnumeration) type).getElementType());
			break;
		case FixedArray:
			addType(((TypeDefinitionFixedArray) type).getType());
			break;
		case Record:
			TypeDefinitionRecord recordType = (TypeDefinitionRecord) type;
			for ( int x = 0 ; x < recordType.getCount() ; x++ )
				addType(recordType.getMemberType(x));
			break;
		case Set:
			addType(((TypeDefinitionSet) type).getElementType());
			break;
		case VariableArray:
			addType(((TypeDefinitionVariableArray) type).getType());
			break;
		case String:
			addType(((TypeDefinitionString) type).getElementType());
			break;
		default:
			break;
		}
	
	}
	
	public void makeStatic() {
		
		dynamic = false;
		classDefinitions = null;
		typeDefinitions = null;
	}

	public boolean getIsDynamic() {
		
		return dynamic;
	}
	
	public String toString() {
		
		return MediaEngine.toString(this);
	}
	
	public int hashCode() {
		
		return MediaEngine.hashCode(this);
	}
	
	public boolean equals(
			Object o) {
		
		return MediaEngine.equals(this, o);
	}
	
	public MetaDictionary clone() {
		
		try {
			return (MetaDictionary) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Should never get here
			throw new InternalError(cnse.getMessage());
		}
	}
	
}
