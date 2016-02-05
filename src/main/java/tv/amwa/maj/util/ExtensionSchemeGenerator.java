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

package tv.amwa.maj.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.ExtensionScheme;
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
import tv.amwa.maj.meta.impl.ClassDefinitionImpl;

// TODO comments
// TODO tests
public class ExtensionSchemeGenerator
	extends GenerationCore {

	public ExtensionSchemeGenerator() { }

	public final static ExtensionScheme generateExtensionScheme(
			Reader reader)
		throws IOException {

		DictionaryContext context = null;

		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fact.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(reader));
			Node node = doc.getDocumentElement();
			context = processRoot(node);
		}
		catch (Exception e) {
			throw new IOException("Unable to read a meta dictionary due to a " + e.getClass().getName() + ": " +
					e.getMessage(), e);
		}

		ExtensionScheme extensionScheme = Forge.make(ExtensionScheme.class,
				"SchemeID", context.schemeID,
				"SchemeURI", context.schemeURI);

		if (context.preferredPrefix != null)
			extensionScheme.setPreferredPrefix(context.preferredPrefix);
		if (context.extensionDescription != null)
			extensionScheme.setExtensionDescription(context.extensionDescription);

		for ( ClassData classData : classList.values() ) {

			ClassDefinition classDefinition = Forge.make(ClassDefinition.class,
					"MetaDefinitionIdentification", classData.identification,
					"MetaDefinitionName", classData.name,
					"ParentClass", Warehouse.lookForClass(classData.parentClass),
					"IsConcrete", classData.isConcrete);
			classDefinition.setSymbol(classData.symbol);
			if (classData.description != null)
				classDefinition.setDescription(classData.description);
			classDefinition.setJavaImplementation(classDefinition.getParent().getJavaImplementation());
			classDefinition.setNamespace(context.schemeURI);
			classDefinition.setPrefix(context.preferredPrefix);
			Warehouse.register(classDefinition);
			extensionScheme.addMetaDefinition(classDefinition);
		}

		List<TypeData> orderedTypes = new ArrayList<TypeData>(typeList.size());

		Collection<TypeData> typeValues = typeList.values();

		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataInteger) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataEnumeration) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataCharacter) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataString) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataExtendibleEnumeration) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataIndirect) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataOpaque) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataStream) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataFixedArray) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataRecord) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataWeakObjectReference) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues)
			if (typeData instanceof TypeDataStrongObjectReference) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataSet) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataVariableArray) orderedTypes.add(typeData);
		for ( TypeData typeData : typeValues )
			if (typeData instanceof TypeDataRename) orderedTypes.add(typeData);

		for ( TypeData typeData : orderedTypes ) {

			TypeDefinition typeDefinition = null;

			switch (typeData.getTypeCategory()) {

			case Character:
				typeDefinition = Forge.make(TypeDefinitionCharacter.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name);
				break;
			case Enum:
				typeDefinition = Forge.make(TypeDefinitionEnumeration.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name,
						"ElementType", Warehouse.lookForType(((TypeDataEnumeration) typeData).elementType),
						"ElementNames", ((TypeDataEnumeration) typeData).names,
						"ElementValues", ((TypeDataEnumeration) typeData).values);
				break;
			case ExtEnum:
				typeDefinition = Forge.make(TypeDefinitionExtendibleEnumeration.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name);
				break;
			case FixedArray:
				typeDefinition = Forge.make(TypeDefinitionFixedArray.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name,
						"ElementCount", ((TypeDataFixedArray) typeData).elementCount,
						"ElementType", Warehouse.lookForType(((TypeDataFixedArray) typeData).elementType));
				break;
			case Indirect:
				typeDefinition = Forge.make(TypeDefinitionIndirect.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name);
				break;
			case Int:
				typeDefinition = Forge.make(TypeDefinitionInteger.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name,
						"Size", ((TypeDataInteger) typeData).size,
						"IsSigned", ((TypeDataInteger) typeData).isSigned);
				break;
			case Opaque:
				typeDefinition = Forge.make(TypeDefinitionOpaque.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name);
				break;
			case Record:
				TypeDataRecord recordType = (TypeDataRecord) typeData;
				List<TypeDefinition> memberTypes = new ArrayList<TypeDefinition>(recordType.memberTypes.size());
				for ( String memberName : recordType.memberTypes )
					memberTypes.add(Warehouse.lookForType(memberName));

				typeDefinition = Forge.make(TypeDefinitionRecord.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name,
						"MemberNames", recordType.memberNames,
						"MemberTypes", memberTypes);
				break;
			case Rename:
				typeDefinition = Forge.make(TypeDefinitionRename.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name,
						"RenamedType", Warehouse.lookForType(((TypeDataRename) typeData).renamedType));
				break;
			case Set:
				typeDefinition = Forge.make(TypeDefinitionSet.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name,
						"ElementType", Warehouse.lookForType(((TypeDataSet) typeData).elementType));
				break;
			case Stream:
				typeDefinition = Forge.make(TypeDefinitionStream.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name);
				break;
			case String:
				typeDefinition = Forge.make(TypeDefinitionString.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name,
						"StringElementType", Warehouse.lookForType(((TypeDataString) typeData).elementType));
				break;
			case StrongObjRef:
				typeDefinition = Forge.make(TypeDefinitionStrongObjectReference.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name,
						"ReferencedType", Warehouse.lookForClass(((TypeDataStrongObjectReference) typeData).referencedType));
				break;
			case WeakObjRef:
				typeDefinition = Forge.make(TypeDefinitionWeakObjectReference.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name,
						"ReferencedType", Warehouse.lookForClass(((TypeDataWeakObjectReference) typeData).referencedType),
						"TargetList", ((TypeDataWeakObjectReference) typeData).targetSet);

			case VariableArray:
				typeDefinition = Forge.make(TypeDefinitionVariableArray.class,
						"MetaDefinitionIdentification", typeData.identification,
						"MetaDefinitionName", typeData.name,
						"VariableArrayElementType", Warehouse.lookForType(((TypeDataVariableArray) typeData).elementType));
				break;
			default:
				break;
			}

			if (typeData.description != null)
				typeDefinition.setDescription(typeData.description);
			if (typeData.symbol != null)
				typeDefinition.setSymbol(typeData.symbol);
			Warehouse.register(typeDefinition, context.schemeURI, context.preferredPrefix);
			extensionScheme.addMetaDefinition(typeDefinition);
		}

		for ( PropertyData propertyData : propertyList.values() ) {
			PropertyDefinition propertyDefinition = Forge.make(PropertyDefinition.class,
					"MetaDefinitionIdentification", propertyData.identification,
					"MetaDefinitionName", propertyData.name,
					"MemberOf", Warehouse.lookForClass(propertyData.memberOf),
					"IsOptional", propertyData.isOptional,
					"IsUniqueIdentifier", propertyData.isUniqueIdentifier,
					"PropertyType", typeByName(propertyData.type).identification,
					"LocalIdentification", (propertyData.localIdentification > 0) ? propertyData.localIdentification : (short) 0);
			if (propertyData.description != null)
				propertyDefinition.setDescription(propertyData.description);
			propertyDefinition.setNamespace(context.schemeURI);
			propertyDefinition.setPrefix(context.preferredPrefix);
			propertyDefinition.setSymbol(propertyData.symbol);
			Warehouse.register(propertyDefinition);
			extensionScheme.addMetaDefinition(propertyDefinition);
		}

		return extensionScheme;
	}

	public final static void main(
			String[] args)
		throws Exception {

		if (args.length < 1)
			System.exit(1);

		MediaEngine.initializeAAF();

		FileReader fileReader = new FileReader(args[0]);
		ExtensionScheme scheme = generateExtensionScheme(fileReader);
		System.out.println(scheme.toString());

		System.out.println(Warehouse.lookForClass("PolyFileDescriptor").toString());
		System.out.println(Warehouse.lookForProperty(Forge.parseAUID("urn:uuid:45e12b0b-ac1d-43ae-9db4-36f065afc3eb")).toString());
		System.out.println(Warehouse.lookForType("Operation Group Strong Reference Variable Array").toString());

		ClassDefinition polyClass = Warehouse.lookForClass("PolyFileDescriptor");
		MetadataObject testing = ((ClassDefinitionImpl) polyClass).createInstance();
		System.out.println(testing.getClass().getCanonicalName());
		System.out.println(testing.toString());
	}
}
