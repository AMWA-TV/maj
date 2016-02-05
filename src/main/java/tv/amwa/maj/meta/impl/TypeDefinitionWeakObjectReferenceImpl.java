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
 * $Log: TypeDefinitionWeakObjectReferenceImpl.java,v $
 * Revision 1.7  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.6  2011/07/27 17:41:46  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.5  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/21 09:52:44  vizigoth
 * Completed writing tests for media engine.
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
 * Revision 1.2  2008/01/14 20:55:21  vizigoth
 * Change to type category enumeration element names.
 *
 * Revision 1.1  2007/11/13 22:13:33  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.aaf.AAFConstants;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinitionWeakObjectReference;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements the definition of a property type that defines an object relationship where the target 
 * of the weak reference is referenced by the object with the property with the TypeDefinitionWeakObjectReference 
 * type.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0206, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionWeakObjectReference",
		  description = "The TypeDefinitionWeakObjectReference class defines a property type that defines an object relationship where the target of the weak reference is referenced by the object with the property with the TypeDefinitionWeakObjectReference type.",
		  symbol = "TypeDefinitionWeakObjectReference")
public final class TypeDefinitionWeakObjectReferenceImpl 
	extends 
		TypeDefinitionObjectReferenceImpl 
	implements 
		TypeDefinitionWeakObjectReference,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 1790614459668387476L;
	
	private AUID[] targetSet;
	
	protected TypeDefinitionWeakObjectReferenceImpl() { }
	
	/**
	 * <p>Creates and initializes a weak object reference type definition, which defines a property 
	 * type that defines an object relationship where the target of the weak reference is referenced
	 * by the object with the property. Only objects that define a unique identification 
	 * ({@link tv.amwa.maj.record.AUID AUID} or 
	 * {@link tv.amwa.maj.record.PackageID package id}) can be the targets of weak object 
	 * references. An object can be the target of one or more than one weak references.</p>
	 * 
	 * <p>The <code>targetSet</code> parameter points to an array of 
	 * property definition AUIDs that indicate the location of the property 
	 * containing the target of the weak reference.  The AUIDs indicate the 
	 * containment path to the target property, starting at the root {@link 
	 * Preface preface} object.</p>  
	 * 
	 * <p>For example, if the containment hierarchy is:<br>
	 * <center><code>Preface->A->B->C</code></center><br>
	 * then the first {@link tv.amwa.maj.record.AUID AUID} corresponds to 
	 * the preface's property that contains object A; the second 
	 * {@link tv.amwa.maj.record.AUID AUID} corresponds to object A's
	 * property which contains object B; and the third 
	 * {@link tv.amwa.maj.record.AUID AUID} corresponds
	 * to object B's property C that contans the target of this weak reference.  
	 * If any intermediate property is a vector or set, <strong>all</strong>
	 * objects in that vector or set are searched for the target.</p>
	 * 
	 * <p>Note that the final {@link tv.amwa.maj.record.AUID AUID} <strong>must</strong> 
	 * correspond to a property that is currently set.</p>
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
	 * @param objectType Class definition of objects that the reference is permitted
	 * to reference. The referenced object may also belong to a subclass of the 
	 * referenced class.
	 * @param targetSet List of property definition ids that indicate where 
	 * the target of the reference is to be found.
	 * 
	 * @throws NullPointerException One or more of the identification, object type or target
	 * set arguments is </code>null</code> and all are required.
	 */
	public TypeDefinitionWeakObjectReferenceImpl(
			AUID identification,
			@AAFString String typeName,
			ClassDefinition objectType,
			AUID[] targetSet) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a weak object reference type definition using a null identification.");
		if (objectType == null)
			throw new NullPointerException("Cannot create a weak object reference type definition using a null object type.");
		if (targetSet == null)
			throw new NullPointerException("Cannot create a weak object reference type definition using a null target set.");
		
		setIdentification(identification);
		setName(typeName);
		setReferencedType(objectType);
		setTargetSet(targetSet);
	}
	
	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0a00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "WeakReferencedType",
			aliases = { "ReferencedType" },
			typeName = "ClassDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0012,
			symbol = "WeakReferencedType")
	public ClassDefinition getReferencedType() {
		
		return getObjectType();
	}

	@MediaPropertySetter("WeakReferencedType")
	public void setReferencedType(
			ClassDefinition referencedType) {
		
		super.setReferencedType(referencedType);
	}
	
	public final static ClassDefinition initializeWeakReferencedType() {
		
		return Warehouse.lookForClass(DataDefinition.class);
	}
	
	@MediaProperty(uuid1 = 0x03010203, uuid2 = (short) 0x0b00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TargetSet", // TODO should this be TargetList or is KXS wrong
			aliases = { "ReferenceTargetSet", "TargetList" },
			typeName = "AUIDArray",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0013,
			symbol = "TargetSet")
	public AUID[] getTargetSet() {
		
		return targetSet.clone();
	}

	@MediaPropertySetter("TargetSet")
	public void setTargetSet(
			AUID[] targetSet) 
		throws NullPointerException {
		
		if (targetSet == null)
			throw new NullPointerException("Cannot set the target set of a weak object reference to a null value.");
		
		this.targetSet = new AUID[targetSet.length];
		for ( int x = 0 ; x < targetSet.length ; x++ )
			this.targetSet[x] = targetSet[x].clone();
	}

	public final static AUID[] initializeTargetSet() {
		
		return new AUID[0];
	}
	
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {
		
		if (javaValue == null)
			return new TypeDefinitionObjectReferenceImpl.ObjectReferenceValue(this, null);
		
		if (javaValue instanceof UnresolvedReferenceValue)
			return (UnresolvedReferenceValue) javaValue;
		
		if (javaValue instanceof String) {
			try {
				Method forName = getReferencedType().getJavaImplementation().getMethod(
						"forName", String.class);
				javaValue = forName.invoke(null, (String) javaValue);
			}
			catch (Exception e) { /* Do nothing and keep going. */ }
		}
		
		if (javaValue instanceof AUID)
			return new UnresolvedReferenceValue(this, (AUID) javaValue);
		
		return super.createValue(javaValue);
	}
	
	@Override
	public TypeCategory getTypeCategory() {

		return TypeCategory.WeakObjRef;
	}

	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionWeakObjectReference");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
		
		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"ReferencedType", getReferencedType().getName());
		Element targets = XMLBuilder.createChild(typeElement, namespace, prefix, 
				"TargetSet");
		for ( AUID id : targetSet ) {
			PropertyDefinition targetProperty = ClassDefinitionImpl.globalPropertyIDLookup(id);
			if (targetProperty != null) {
				XMLBuilder.appendElement(targets, namespace, prefix, 
						"MetaDefRef", targetProperty.getName());
			}
			
			if (id.equals(AAFConstants.RootMetaDictionaryProperty))
				XMLBuilder.appendElement(targets, namespace, prefix, 
						"MetaDefRef", "MetaDictionary");
			
			if (id.equals(AAFConstants.RootPrefaceProperty))
				XMLBuilder.appendElement(targets, namespace, prefix, 
						"MetaDefRef", "Preface");	
		}
	}
	
	public TypeDefinitionWeakObjectReference clone() {
		
		return (TypeDefinitionWeakObjectReference) super.clone();
	}
}
