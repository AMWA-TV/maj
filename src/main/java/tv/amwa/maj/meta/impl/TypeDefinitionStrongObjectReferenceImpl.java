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
 * $Log: TypeDefinitionStrongObjectReferenceImpl.java,v $
 * Revision 1.5  2011/07/27 17:41:46  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
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
 * Revision 1.1  2007/11/13 22:13:27  vizigoth
 * Public release of MAJ API.
 */


package tv.amwa.maj.meta.impl;

import java.io.Serializable;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.NetworkLocator;

// TODO ask Phil for a better description of this data type ... one you can read!

/**
 * <p>Implements the definition of a property type that defines an object relationship where the target of the strong 
 * reference is owned by the object with a property of the TypeDefinitionStrongObjectReference type.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0205, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionStrongObjectReference",
		  description = "The TypeDefinitionStrongObjectReference class defines a property type that defines an object relationship where the target of the strong reference is owned by the object with the property with the TypeDefinitionStrongObjectReference type.",
		  symbol = "TypeDefinitionStrongObjectReference")
public final class TypeDefinitionStrongObjectReferenceImpl 
	extends 
		TypeDefinitionObjectReferenceImpl 
	implements 
		TypeDefinitionStrongObjectReference,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 7393102643260496960L;
	
	protected TypeDefinitionStrongObjectReferenceImpl() { }
	
	/**
	 * <p>Creates and initializes the strong object reference type definition, which defines 
	 * a property type that defines an object relationship where the target of the strong reference 
	 * is owned by the object with the property. An object can be the target of only one strong 
	 * reference.</p>
	 * 
	 * <p>Creating new and unregistered type definitions is not recommended as this may cause
	 * interoperability issues with other systems. The official registry of type definitions 
	 * is available from SMPTE at <a href="http://www.smpte-ra.org/mdd/">SMPTE Metadata Registries 
	 * And Related Items</a>. The full range of data types documented in the AAF 1.1 object 
	 * specification can be accessed by name and identification using either
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(String)} or
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(tv.amwa.maj.record.AUID)}
	 * respectively.</p>

	 * @param identification AUID to be used to identify this type.
	 * @param typeName Friendly name of the type definition.
	 * @param referencedType Class definition of objects that it is permissable
	 * for this type of object reference to reference.
	 * 
	 * @throws NullPointerException The identification and/or referenced type is/are
	 * <code>null</code>.
	 */
	public TypeDefinitionStrongObjectReferenceImpl(
			tv.amwa.maj.record.AUID identification,
			@AAFString String typeName,
			tv.amwa.maj.meta.ClassDefinition referencedType)
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new strong object reference type definition with a null identification.");
		if (referencedType == null)
			throw new NullPointerException("Cannot create a new string object reference type definition with a null referenced type value.");
		
		setIdentification(identification);
		setName(typeName);
		setReferencedType(referencedType);
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0900, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ReferencedType",
			aliases = { "StrongReferencedType" },
			typeName = "ClassDefinitionWeakReference", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0011,
			symbol = "ReferencedType")
	public final ClassDefinition getReferencedType() {
		
		return getObjectType();
	}
	
	@MediaPropertySetter("ReferencedType")
	public void setReferencedType(
			ClassDefinition referencedType) {
		
		super.setReferencedType(referencedType);
	}

	public final static ClassDefinition initializeReferencedType() {
		
		return Warehouse.lookForClass(NetworkLocator.class);
	}
	
	@Override
	public final TypeCategory getTypeCategory() {

		return TypeCategory.StrongObjRef;
	}
	
	// TODO Problem changing UnresolvedReferenceValue into ObjectReferenceValue
//	@Override
//	public boolean resolveReferences(
//			PropertyValue value,
//			Map<AUID, MetadataObject> referenceMap)
//		throws NullPointerException,
//			IllegalPropertyValueException {
//		
//		super.resolveReferences(value, referenceMap);
//		
//		if (value.getValue() instanceof UnresolvedReferenceValue) {
//			UnresolvedReferenceValue unresolvedReference = (UnresolvedReferenceValue) value.getValue();
//			if (referenceMap.containsKey(unresolvedReference.getValue())) {
//				((ObjectReferenceValue) value).setValue(referenceMap.get(unresolvedReference.getValue()));
//				return true;
//			}
//			return false;
//		}
//		
//		return true;
//	}
	
	@Override
	public String nameToAAFName(
			String name) {
		
		return "kAAFTypeID_" + super.nameToAAFName(name);
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionStrongObjectReference");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
		
		XMLBuilder.appendElement(typeElement, namespace, prefix, 
				"ReferencedType", getReferencedType().getName());
	}
	
	public TypeDefinitionStrongObjectReference clone() {
		
		return (TypeDefinitionStrongObjectReference) super.clone();
	}
}
