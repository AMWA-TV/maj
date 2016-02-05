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
 * $Log: MetaDictionaryGenerator.java,v $
 * Revision 1.2  2011/10/05 17:14:32  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.1  2011/07/27 17:07:21  vizigoth
 * Additions and alterations coincidence with editing the Reg-XML document part 1.
 *
 */

package tv.amwa.maj.io.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.industry.AAFSpecifiedClasses;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionCharacter;
import tv.amwa.maj.meta.TypeDefinitionEnumeration;
import tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration;
import tv.amwa.maj.meta.TypeDefinitionFixedArray;
import tv.amwa.maj.meta.TypeDefinitionIndirect;
import tv.amwa.maj.meta.TypeDefinitionInteger;
import tv.amwa.maj.meta.TypeDefinitionOpaque;
import tv.amwa.maj.meta.TypeDefinitionRecord;
import tv.amwa.maj.meta.TypeDefinitionRename;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionStream;
import tv.amwa.maj.meta.TypeDefinitionString;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.meta.TypeDefinitionWeakObjectReference;
import tv.amwa.maj.record.AUID;

// TODO comments
// TODO tests

/**
 * <p>Generate a Reg-XML compliant meta dictionary for either the baseline AAF model or an extension
 * data model.</p>
 * 
 *
 * 
 * @see XSDGenerator
 */
public class MetaDictionaryGenerator 
	implements CommonConstants {

	public final static DocumentFragment generateBaselineDictionary() {
		
		MediaEngine.initializeAAF();
		Collection<ClassDefinition> baselineClasses = makeBaselineClasses();
		
		DocumentFragment baselineFragment = generateMetaDictionary(
				baselineClasses, 
				Forge.makeAUID(0x060E2B34, (short) 0x0101, (short) 0x0101,
						new byte[] { 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01 }), 
				AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, 
				"AAF baseline scheme", 
				AAF_METADICT_NAMESPACE,
				AAF_METADICT_PREFIX,
				AAFElement.AAF_TAG,
				"Preface");

		return baselineFragment;
	}
	
	public final static String generateBaselineDictionaryAsAString() {
		
		return XMLBuilder.transformNodeToString(generateBaselineDictionary());
	}
	
	// TODO rewrite this
	/**
	 * <p>Generates an XML meta dictionary description from the given set of 
	 * baseline class definitions and extension class definitions. Each class
	 * definition is searched to find the required property definitions and 
	 * type definitions. Only required meta definitions for the given classes
	 * are included.</p>
	 * 
	 * <p>The extension element will only include meta definitions that are not
	 * already covered in the baseline set. The include baseline property 
	 * will include the both the baseline and extensions in the result if 
	 * set to <code>true</code> (AAF-style) or only the extensions if
	 * set to <code>false</code> (MXF- and Registered Data XML-style).</p>
	 * 
	 * <p>Symbol space and description properties are optional for baseline and
	 * extension sections. If no extensions are present, extension identification
	 * and extension symbol space parameters are not required.</p> 
	 * 
	 * @param baselineClasses Collection of baseline classes.
	 * @param extensionClasses Collection of extensions.
	 * @param baselineIdentification Identification for the baseline meta dictionary.
	 * @param baselineSymbolSpace Namespace for the symbols defined in the baseline meta dictionary.
	 * @param baselinePreferredPrefix An optional preferred prefix to use for elements in the baseline
	 * symbol space.
	 * @param baselineDescription An optional description of the baseline meta dictionary.
	 * @param extensionIdentification Extension classes to use to generate the extension section of the
	 * meta dictionary.
	 * @param extensionSymbolSpace Namespace for the symbols defined in the extension meta dictionary.
	 * @param extensionPreferredPrefix An optional preferred prefix to use for elements in the extension
	 * symbol space.
	 * @param extensionDescription An optional description of the extension meta dictionary.
	 * @param includeBaseline Should the baseline be assumed or included in the meta dictionary?
	 * @return XML representation of a meta dictionary for the given classes.
	 * 
	 * @throws NullPointerException One or more of the required properties is <code>null</code>.
	 * 
	 * @see CommonConstants#AAF_METADICT_NAMESPACE
	 * @see AAFSpecifiedClasses
	 */
	public final static DocumentFragment generateMetaDictionary(
			Collection<ClassDefinition> classes,
			AUID schemeID,
			String schemeURI,
			String preferredPrefix,
			String description,
			String localNamespace,
			String localPrefix) 
		throws NullPointerException {
	
		return generateMetaDictionary(classes, schemeID, schemeURI, preferredPrefix, description, 
				localNamespace, localPrefix, null, null);
	}
	
	public final static DocumentFragment generateMetaDictionary(
			Collection<ClassDefinition> classes,
			AUID schemeID,
			String schemeURI,
			String preferredPrefix,
			String description,
			String localNamespace,
			String localPrefix,
			String rootElement,
			String rootObject) 
		throws NullPointerException {
	
		if (classes == null)
			throw new NullPointerException("Cannot generate a meta dictionary from a null collection of classes.");
		
		if (schemeID == null)
			throw new NullPointerException("Cannot generate a meta dictionary with a null scheme identification.");
		
		if (schemeURI == null)
			throw new NullPointerException("Cannot generate a meta dictionary with a null scheme URI.");
		
		if (((rootElement == null) && (rootObject != null)) ||
				((rootElement != null) && (rootObject == null)))
			throw new NullPointerException("Both root element and root object properties must be provided or both be null.");
		
		List<ClassDefinition> classList = new ArrayList<ClassDefinition>(classes.size() * 2);
		List<PropertyDefinition> propertyList = new ArrayList<PropertyDefinition>(classes.size() * 20);
		
		List<TypeDefinitionInteger> integerList = new ArrayList<TypeDefinitionInteger>();
		List<TypeDefinitionEnumeration> enumerationList = new ArrayList<TypeDefinitionEnumeration>();
		List<TypeDefinitionCharacter> characterList = new ArrayList<TypeDefinitionCharacter>();
		List<TypeDefinitionString> stringList = new ArrayList<TypeDefinitionString>();
		List<TypeDefinitionExtendibleEnumeration> extEnumList = new ArrayList<TypeDefinitionExtendibleEnumeration>();
		List<TypeDefinitionIndirect> indirectList = new ArrayList<TypeDefinitionIndirect>();
		List<TypeDefinitionOpaque> opaqueList = new ArrayList<TypeDefinitionOpaque>();
		List<TypeDefinitionStream> streamList = new ArrayList<TypeDefinitionStream>();
		List<TypeDefinitionWeakObjectReference> weakRefList = new ArrayList<TypeDefinitionWeakObjectReference>();
		List<TypeDefinitionStrongObjectReference> strongRefList = new ArrayList<TypeDefinitionStrongObjectReference>();
		List<TypeDefinitionRecord> recordList = new ArrayList<TypeDefinitionRecord>();
		List<TypeDefinitionFixedArray> fixedArrayList = new ArrayList<TypeDefinitionFixedArray>();
		List<TypeDefinitionSet> setList = new ArrayList<TypeDefinitionSet>();
		List<TypeDefinitionVariableArray> variableArrayList = new ArrayList<TypeDefinitionVariableArray>();
		List<TypeDefinitionRename> renameList = new ArrayList<TypeDefinitionRename>();
		
		Set<MetaDefinition> baseline = new HashSet<MetaDefinition>();
		// TODO defending against bad behavior of extension properties in core classes - could do better?
		boolean aafMode = false;
		if ((rootElement != null) && (rootElement.equals(AAFElement.AAF_TAG))) aafMode = true;
		
		for ( ClassDefinition classItem : classes ) 
			addPropertiesAndTypes(classItem, baseline, classList, propertyList, aafMode);
		
		boolean allTypesChecked = false;
		while (allTypesChecked == false) {
	
			Set<MetaDefinition> newForBaseline = new HashSet<MetaDefinition>();
			allTypesChecked = true;
			
			for ( MetaDefinition metaDefinition : baseline ) {
	
				if (!(metaDefinition instanceof TypeDefinition)) continue;
				TypeDefinition typeDefinition = (TypeDefinition) metaDefinition;
	
				switch (typeDefinition.getTypeCategory()) {
	
				case Int:
					if (!integerList.contains(typeDefinition))
						integerList.add((TypeDefinitionInteger) typeDefinition);
					break;
				case Enum:
					if (!enumerationList.contains(typeDefinition))
						enumerationList.add((TypeDefinitionEnumeration) typeDefinition);
					
					TypeDefinitionEnumeration enumerationType = (TypeDefinitionEnumeration) typeDefinition;
					if (!baseline.contains(enumerationType.getElementType())) {
						newForBaseline.add(enumerationType.getElementType());
						allTypesChecked = false;
					}
					break;
				case Character:
					if (!characterList.contains(typeDefinition))
						characterList.add((TypeDefinitionCharacter) typeDefinition);
					break;
				case String:
					if (!stringList.contains(typeDefinition))
						stringList.add((TypeDefinitionString) typeDefinition);
					
					TypeDefinitionString stringType = (TypeDefinitionString) typeDefinition;
					if (!baseline.contains(stringType.getElementType())) {
						newForBaseline.add(stringType.getElementType());
						allTypesChecked = false;
					}
					break;
				case ExtEnum:
					if (!extEnumList.contains(typeDefinition))
						extEnumList.add((TypeDefinitionExtendibleEnumeration) typeDefinition);
					break;
				case Indirect:
					if (!indirectList.contains(typeDefinition))
						indirectList.add((TypeDefinitionIndirect) typeDefinition);
					break;
				case Opaque:
					if (!opaqueList.contains(typeDefinition))
						opaqueList.add((TypeDefinitionOpaque) typeDefinition);
					break;
				case Stream:
					if (!streamList.contains(typeDefinition))
						streamList.add((TypeDefinitionStream) typeDefinition);
					break;
				case Record:
					if (!recordList.contains(typeDefinition))
						recordList.add((TypeDefinitionRecord) typeDefinition);
					
					TypeDefinitionRecord recordType = (TypeDefinitionRecord) typeDefinition;
					for ( int u = 0 ; u < recordType.getCount() ; u++ )
						if (!baseline.contains(recordType.getMemberType(u))) {
							newForBaseline.add(recordType.getMemberType(u));
							allTypesChecked = false;
						}
					break;
				case WeakObjRef:
					if (!weakRefList.contains(typeDefinition))
						weakRefList.add((TypeDefinitionWeakObjectReference) typeDefinition);
					
					TypeDefinitionWeakObjectReference weakRefType = (TypeDefinitionWeakObjectReference) typeDefinition;
					if (!classList.contains(weakRefType.getObjectType())) {
						addPropertiesAndTypes(weakRefType.getObjectType(), newForBaseline, classList, propertyList, aafMode);
						allTypesChecked = false;
					}
					break;
				case StrongObjRef:
					if (!strongRefList.contains(typeDefinition))
						strongRefList.add((TypeDefinitionStrongObjectReference) typeDefinition);
					
					TypeDefinitionStrongObjectReference strongRefType = (TypeDefinitionStrongObjectReference) typeDefinition;
					if (!classList.contains(strongRefType.getObjectType())) {
						addPropertiesAndTypes(strongRefType.getObjectType(), newForBaseline, classList, propertyList, aafMode);
						allTypesChecked = false;
					}
					break;
				case FixedArray:
					if (!fixedArrayList.contains(typeDefinition))
						fixedArrayList.add((TypeDefinitionFixedArray) typeDefinition);
					
					TypeDefinitionFixedArray fixedArrayType = (TypeDefinitionFixedArray) typeDefinition;
					if (!baseline.contains(fixedArrayType.getType())) {
						newForBaseline.add(fixedArrayType.getType());
						allTypesChecked = false;
					}
					break;
				case Set:
					if (!setList.contains(typeDefinition))
						setList.add((TypeDefinitionSet) typeDefinition);
					
					TypeDefinitionSet setType = (TypeDefinitionSet) typeDefinition;
					if (!baseline.contains(setType.getElementType())) {
						newForBaseline.add(setType.getElementType());
						allTypesChecked = false;
					}
					break;
				case VariableArray:
					if (!variableArrayList.contains(typeDefinition))
						variableArrayList.add((TypeDefinitionVariableArray) typeDefinition);
					
					TypeDefinitionVariableArray variableArrayType = (TypeDefinitionVariableArray) typeDefinition;
					if (!baseline.contains(variableArrayType.getType())) {
						newForBaseline.add(variableArrayType.getType());
						allTypesChecked = false;
					}
					break;
				case Rename:
					if (!renameList.contains(typeDefinition))
						renameList.add((TypeDefinitionRename) typeDefinition);
					
					TypeDefinitionRename renameType = (TypeDefinitionRename) typeDefinition;
					if (!baseline.contains(renameType.getBaseType())) {
						newForBaseline.add(renameType.getBaseType());
						allTypesChecked = false;
					}
					break;
				default:
					break;
				}
			} // For all meta definitions in the baseline.
			
			baseline.addAll(newForBaseline);
		} // While allTypesChecked == false
		 
		DocumentFragment metadictFragment = XMLBuilder.createDocumentFragment();
		Element schemeElement = null;
		
		metadictFragment.appendChild(metadictFragment.getOwnerDocument().createComment(
				"Meta Dictionary generated by the media authoring with Java API (MAJ API"));
		
		if (rootElement == null) { // this is an extension
			schemeElement = XMLBuilder.createChild(metadictFragment, localNamespace, 
					localPrefix, "Extension");
		}
		else { // this is a baseline
			schemeElement = XMLBuilder.createChild(metadictFragment, localNamespace, localPrefix, "Baseline");
			XMLBuilder.setAttribute(schemeElement, "", "", "rootElement", rootElement);
			XMLBuilder.setAttribute(schemeElement, "", "", "rootObject", rootObject);
		}
		
		XMLBuilder.appendElement(schemeElement, localNamespace, localPrefix, 
				"SchemeID",schemeID.toString());
		XMLBuilder.appendElement(schemeElement, localNamespace, localPrefix, 
				"SchemeURI", schemeURI);
		if (preferredPrefix != null)
			XMLBuilder.appendElement(schemeElement, localNamespace, localPrefix, 
					"PreferredPrefix", preferredPrefix);
		if (description != null)
			XMLBuilder.appendElement(schemeElement, localNamespace, localPrefix, 
					"Description", description);
		
		Element definitions = XMLBuilder.createChild(schemeElement, localNamespace, 
				localPrefix, "MetaDefinitions");
		
		for ( ClassDefinition classItem : classList )
			classItem.appendMetadictXML(definitions, localNamespace, localPrefix);
		
		for ( PropertyDefinition propertyItem : propertyList )
			propertyItem.appendMetadictXML(definitions, localNamespace, localPrefix);
		
		for ( TypeDefinitionInteger integerType : integerList )
			integerType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionEnumeration enumerationType : enumerationList )
			enumerationType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionCharacter characterType : characterList )
			characterType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionString stringType : stringList )
			stringType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionExtendibleEnumeration extEnumType : extEnumList ) 
			extEnumType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionIndirect indirectType : indirectList )
			indirectType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionOpaque opaqueType : opaqueList )
			opaqueType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionStream streamType : streamList )
			streamType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionFixedArray fixedArrayType : fixedArrayList )
			fixedArrayType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionRecord recordType : recordList )
			recordType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionWeakObjectReference weakRefType : weakRefList )
			weakRefType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionStrongObjectReference strongRefType : strongRefList)
			strongRefType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionSet setType : setList )
			setType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionVariableArray variableArrayType : variableArrayList )
			variableArrayType.appendMetadictXML(definitions, localNamespace, localPrefix);
		for ( TypeDefinitionRename renameType : renameList )
			renameType.appendMetadictXML(definitions, localNamespace, localPrefix);
				
		return metadictFragment;
	}

	public final static String generateMetaDictionaryAsAString(
			Collection<ClassDefinition> classes,
			AUID schemeID,
			String schemeURI,
			String preferredPrefix,
			String description,
			String localNamespace,
			String localPrefix,
			String rootElement,
			String rootObject) 
		throws NullPointerException {
		
		return XMLBuilder.transformNodeToString(
				generateMetaDictionary(classes, schemeID, schemeURI, preferredPrefix, description, 
						localNamespace, localPrefix, rootElement, rootObject));
	}
	
	public final static String generateMetaDictionaryAsAString(			
			Collection<ClassDefinition> classes,
			AUID schemeID,
			String schemeURI,
			String preferredPrefix,
			String description,
			String localNamespace,
			String localPrefix)
		throws NullPointerException {
		
		return XMLBuilder.transformNodeToString(
				generateMetaDictionary(classes, schemeID, schemeURI, preferredPrefix, description, localNamespace, localPrefix));
	}

	final static void addPropertiesAndTypes(
			ClassDefinition classItem,
			Set<MetaDefinition> baseline,
			List<ClassDefinition> classList,
			List<PropertyDefinition> propertyList,
			boolean aafMode) {
		
		baseline.add(classItem);
		
		if (!classList.contains(classItem)) {
			
			if (!classItem.isRoot()) 
				addPropertiesAndTypes(classItem.getParent(), baseline, classList, propertyList, aafMode);
			
			classList.add(classItem);
		
			for ( PropertyDefinition propertyItem : classItem.getPropertyDefinitions() ) {
			
				if ((aafMode) && (!MediaEngine.isBaseline(propertyItem)))
					continue;
				
				baseline.add(propertyItem);
				if (!propertyList.contains(propertyItem)) {
						propertyList.add(propertyItem);
				}
				TypeDefinition propertyType = propertyItem.getTypeDefinition();
				baseline.add(propertyType);
			}
		}
	}
	
	final static Collection<ClassDefinition> makeBaselineClasses() {
		
		List<ClassDefinition> baselineClasses = new ArrayList<ClassDefinition>(
				AAFSpecifiedClasses.abstractInterchangeable.length + 
				AAFSpecifiedClasses.interchangeable.length +
				AAFSpecifiedClasses.abstractMeta.length + 
				AAFSpecifiedClasses.meta.length);
		
		for ( Class<?> abstractInterchangeable : AAFSpecifiedClasses.abstractInterchangeable )
			baselineClasses.add(Warehouse.lookForClass(abstractInterchangeable));
		for ( Class<?> interchangeable : AAFSpecifiedClasses.interchangeable )
			baselineClasses.add(Warehouse.lookForClass(interchangeable));
		for ( Class<?> abstactMeta : AAFSpecifiedClasses.abstractMeta )
			baselineClasses.add(Warehouse.lookForClass(abstactMeta));
		for ( Class<?> meta : AAFSpecifiedClasses.meta )
			baselineClasses.add(Warehouse.lookForClass(meta));

		return baselineClasses;
	}
	
	public final static void main(
			String args[]) {

		System.out.println(generateBaselineDictionaryAsAString());
	}
}
