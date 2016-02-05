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
 * $Log: GenerationCore.java,v $
 * Revision 1.6  2011/10/05 17:14:31  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.5  2011/07/27 17:11:56  vizigoth
 * Made the generation of meta dictionary XML namespace aware.
 *
 * Revision 1.4  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/25 14:19:04  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.2  2011/01/24 14:01:14  vizigoth
 * Completed annotation and definition auto test generation.
 *
 * Revision 1.1  2011/01/21 17:35:21  vizigoth
 * Refactor to extract common generation code and initial authoring of code to generate JUnit tests.
 *
 */

package tv.amwa.maj.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.impl.TypeDefinitionRecordImpl;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>Shared utilities for parsing meta dictionary XML files and building generators, such as 
 * the {@linkplain AutoGeneration extension generator} and the {@linkplain TestGeneration test
 * generator}.</p>
 * 
 *
 *
 */
public class GenerationCore {

	protected static Map<String, ClassData> classList = new HashMap<String, ClassData>();
	protected static Map<String, PropertyData> propertyList = new HashMap<String, PropertyData>();
	protected static Map<String, TypeData> typeList = new HashMap<String, TypeData>();
	protected static Map<String, String> weakReferenceTargets = new HashMap<String, String>();
	public static final int LINE_WIDTH = 80;
	static final String[] NoImports = new String[0];

	protected abstract static class MetaData {
			
			String name = "";
			String symbol = "";
			String description = "";
			AUID identification = null;
	
			public MetaData() { } 
			
			public String toString() {
				
				return name;
			}
			
			String[] getImports() {
				
				return NoImports;
			}
		}

	protected static class ClassData 
			extends MetaData {
			
			String parentClass = "";
			boolean isConcrete = true;
			
			public ClassData() { }

			public boolean isInterchangeable() {

				if (this.name.equals("InterchangeObject"))
					return true;
				if (isRoot())
					return false;
				return classList.get(parentClass).isInterchangeable();
			};
			
			public boolean isRoot() {
				
				if ((this.parentClass == null) || (this.parentClass == "") || (this.parentClass.equals(name)))
					return true;
				else
					return false;
			}
		}

	protected static class PropertyData 
			extends MetaData {
			
			String memberOf = "";
			String type = "";
			boolean isOptional = true;
			boolean isUniqueIdentifier = false;
			short localIdentification = 0;
			
			public PropertyData() { }
		}

	protected static abstract class TypeData 
			extends MetaData {
			
			public TypeData() { }
			
			abstract String getJavaGetName();
			
			abstract String getJavaSetName(
					boolean optional);
			
			abstract TypeCategory getTypeCategory();
			
			boolean getThrowsIllegal() {
				
				return false;
			}
			
			String getAnnotation() {
				
				return null;
			}
			
			void appendTypeDescription(
					StringIndenter building) {
				
				building.append("        Forge.makeAUID(" + "" +
						"0x" + padHexTo8(identification.getData1()) + ", " + 
						"(short) 0x" + padHexTo4(identification.getData2()) + ", " + 
						"(short) 0x" + padHexTo4(identification.getData3()) + ",");
				building.append("                new byte[] { " + bytesToText(identification.getData4()) + " }),");
			}
		}

	protected static class TypeDataCharacter 
			extends TypeData {
			
			public TypeDataCharacter() { }
			
			String getJavaGetName() {
				
				 return "char";
			}
			
			String getJavaSetName(
					boolean optional) {
				
				return optional ? "Character" : "char";
			}
			
			TypeCategory getTypeCategory() {
				
				return TypeCategory.Character;
			}
			
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionCharacter");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionCharacterImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", a character type definition.</p>", LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionCharacter " + name + " = new TypeDefinitionCharacterImpl(");
				super.appendTypeDescription(building);
				building.appendNL("        \"" + name + "\");");
			}
		}

	protected static class TypeDataEnumeration
		 	extends TypeData {
			
			String elementType;
			List<String> names = new Vector<String>();
			List<String> values = new Vector<String>();
			
			public TypeDataEnumeration() { }
	
			@Override
			String getJavaGetName() {
			
				return (name.equals("Boolean")) ? "boolean" : name;
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
				
				if (name.equals("Boolean")) 
					return optional ? "Boolean" : "boolean";
				
				return name;
			}
	
			@Override
			TypeCategory getTypeCategory() {
				
				return TypeCategory.Enum;
			}
			
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionEnumeration");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionEnumerationImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", an enumeration type definition.</p>", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@see " + name, LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionEnumeration " + name + " = new TypeDefinitionEnumerationImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
				building.append("        " + name + ".class,");
				
				if (typeList.containsKey(elementType))
					building.appendNL("        " + elementType + ");");
				else // Assume in the baseline
					building.appendNL("        tv.amwa.maj.industry.TypeDefinitions." + elementType + ");");
			}
	
		}

	protected static class TypeDataExtendibleEnumeration 
			extends TypeData {
			
			public TypeDataExtendibleEnumeration() { }
	
			@Override
			String getJavaGetName() {
	
				return "AUID";
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
	
				return "AUID";
			}
	
			@Override
			TypeCategory getTypeCategory() {
				
				return TypeCategory.ExtEnum;
			}
			
			@Override
			boolean getThrowsIllegal() {
				
				return true;
			}
			
			@Override
			public String[] getImports() {
				
				return new String[] { "tv.amwa.maj.record.AUID" };
			}
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionExtendibleEnumerationImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", an extendible enumeration type definition.</p>", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@see " + name, LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionExtendibleEnumeration " + name + 
						" = new TypeDefinitionExtendibleEnumerationImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
				building.appendNL("        \"" + symbol + "\");");
			}
			
		}

	protected static class TypeDataFixedArray 
			extends TypeData {
			
			public TypeDataFixedArray() { }
			
			public int elementCount;
			public String elementType;
			
			@Override
			String getJavaGetName() {
				
				return typeByName(elementType).getJavaGetName() + "[]";
			}
			
			@Override
			String getJavaSetName(
					boolean optional) {
				
				return typeByName(elementType).getJavaSetName(optional) + "[]";
			}
			
			@Override
			TypeCategory getTypeCategory() {
				
				return TypeCategory.FixedArray;
			}
			
			@Override
			public String getAnnotation() {
				
				return typeByName(elementType).getAnnotation();
			}
			
			@Override
			public String[] getImports() {
				
				return typeByName(elementType).getImports();
			}
			
			@Override
			boolean getThrowsIllegal() {
				
				return typeByName(elementType).getThrowsIllegal();
			}
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionFixedArray");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionFixedArrayImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", a fixed array with " +
							elementCount + " elements of " + camelToWords(elementType) + ".</p>", LINE_WIDTH);
				building.blankComment();
				if (typeList.containsKey(elementType)) 
					building.wrapComment("@see #" + elementType, LINE_WIDTH);
				else
					building.wrapComment("@see tv.amwa.maj.industry.TypeDefintions#" + elementType, LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionFixedArray " + name + 
						" = new TypeDefinitionFixedArrayImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
			
				if (typeList.containsKey(elementType)) 
					building.append("        " + elementType + ",");
				else
					building.append("        tv.amwa.maj.industry.TypeDefinitions." + elementType + ",");
				
				building.appendNL("        " + elementCount + ");");
			}
	
			
		}

	protected static class TypeDataIndirect
			extends TypeData {
			
			public TypeDataIndirect() { }
			
			@Override
			String getJavaGetName() {
	
				return "PropertyValue";
			}
	
			@Override
			String getJavaSetName(boolean optional) {
	
				return "PropertyValue";
			}
	
			@Override
			TypeCategory getTypeCategory() {
	
				return TypeCategory.Indirect;
			}	
			
			static final String[] propertyValueImports = 
				new String[] { "tv.amwa.maj.industry.PropertyValue" };
			
			@Override
			public String[] getImports() {
				
				return propertyValueImports;
			}
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionIndirect");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionIndirectImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", an indirect type definition.</p>", LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionIndirect " + name + 
						" = new TypeDefinitionIndirectImpl(");
				super.appendTypeDescription(building);
				building.appendNL("        \"" + name + "\");");
			}
	
		}

	protected static class TypeDataInteger
			extends TypeData {
			
			int size;
			boolean isSigned;
			
			public TypeDataInteger() { }
	
			@Override
			String getJavaGetName() {
				
				switch (size) {
				
				case 1:
					return "byte";
				case 2:
					return "short";
				case 4:
					return "int";
				case 8:
					return "long";
				default:
					return "int";	// Is this a good default?
				}
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
				
				switch (size) {
				
				case 1:
					return optional ? "Byte" : "byte";
				case 2:
					return optional ? "Short" : "short";
				case 4:
					return optional ? "Integer" : "integer";
				case 8:
					return optional ? "Long" : "long";
				default:
					return optional ? "Integer" : "int";
				}
			}
	
			@Override
			TypeCategory getTypeCategory() {
			
				return TypeCategory.Int;
			}
			
			@Override
			boolean getThrowsIllegal() {
				
				return !isSigned;
			}
			
			@Override
			String getAnnotation() {
				
				switch (size) {
				case 1:
				case 2:
				case 4:
				case 8:
					return (isSigned ? "" : "U") + "Int" + (size * 8);
	
				default:
					return null;
				}
				
			}
			
			@Override
			public String[] getImports() {
				
				switch (size) {
				case 1:
				case 2:
				case 4:
				case 8:
					return new String[] {
							"tv.amwa.maj.integer." + getAnnotation() };
					
				default:
					return NoImports;
				}
			}
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionInteger");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionIntegerImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", an integer type definition.</p>", LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionInteger " + name + " = new TypeDefinitionIntegerImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
				building.append("        (byte) " + size + ",");
				building.appendNL("        " + isSigned + ");");
			}
	
		}

	protected static class TypeDataOpaque
			extends TypeData {
			
			public TypeDataOpaque() { }
	
			@Override
			String getJavaGetName() {
				
				return "ByteBuffer";
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
	
				return "ByteBuffer";
			}
	
			@Override
			TypeCategory getTypeCategory() {
	
				return TypeCategory.Opaque;
			}
			
			static final String[] bufferImport = new String[] {
				"java.nio.ByteBuffer" };
			
			@Override
			public String[] getImports() {
				
				return bufferImport;
			}
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionOpaque");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionOpaqueImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", an opaque type definition.</p>", LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionOpaque " + name + 
						" = new TypeDefinitionOpaqueImpl(");
				super.appendTypeDescription(building);
				building.appendNL("        \"" + name + "\");");
			}
		}

	protected static class TypeDataRecord
			extends TypeData {
			
			List<String> memberNames = new Vector<String>();
			List<String> memberTypes = new Vector<String>();
			
			public TypeDataRecord() { }
	
			@Override
			String getJavaGetName() {
				
				TypeDefinitionRecordImpl externalType = 
					(TypeDefinitionRecordImpl) Warehouse.lookForType(name);
				if (externalType != null)
					return externalType.getSpecification().getSimpleName();
				else
					return name;
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
	
				return getJavaGetName();
			}
	
			@Override
			TypeCategory getTypeCategory() {
				
				return TypeCategory.Record;
			}
			
			@Override
			public String[] getImports() {
				
				try {
					TypeDefinitionRecordImpl externalType = 
						(TypeDefinitionRecordImpl) Warehouse.lookForType(name);
				
					if (externalType != null)
						return new String[] { 
							externalType.getSpecification().getCanonicalName() };
				}
				catch (ClassCastException cce) { }
				
				return NoImports;
			}
	
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionRecord");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionRecordImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", a record type definition.</p>", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@see " + name, LINE_WIDTH);
				building.wrapComment("@see Factory#parse" + name + "(String)", LINE_WIDTH);
				// TODO add in argument-based factory constructor
				building.endComment();
				
				building.append("public final static TypeDefinitionRecord " + name + 
						" = new TypeDefinitionRecordImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
				building.append("        " + building.getPackageName() + "." + name + ".MEMBER_NAMES,");
				building.append("        " + building.getPackageName() + "." + name + ".MEMBER_TYPES,");
				building.appendNL("        " + name + ".class);");
			}
	
		}

	protected static class TypeDataRename
			extends TypeData {
			
			String renamedType;
			
			public TypeDataRename() { }
	
			@Override
			String getJavaGetName() {
			
				return typeByName(renamedType).getJavaGetName();
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
				
				return typeByName(renamedType).getJavaSetName(optional);
			}
	
			@Override
			TypeCategory getTypeCategory() {
				
				return TypeCategory.Rename;
			}
			
			@Override
			String getAnnotation() {
				
				return renamedType;
			}
			
			@Override
			String[] getImports() {
				
				return typeByName(renamedType).getImports();
			}
			
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionRename");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionRenameImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", an alias for type definition " + 
							camelToWords(renamedType) + ".</p>", LINE_WIDTH);
				building.blankComment();
				if (typeList.containsKey(renamedType))
					building.wrapComment("@see #" + renamedType, LINE_WIDTH);
				else
					building.wrapComment("@see tv.amwa.maj.industry.TypeDefinitions#" + renamedType, LINE_WIDTH);			
				building.endComment();
				
				building.append("public final static TypeDefinitionRename " + name + 
						" = new TypeDefinitionRenameImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
				
				if (typeList.containsKey(renamedType))
					building.appendNL("        " + renamedType + ");");
				else
					building.appendNL("        tv.amwa.maj.industry.TypeDefinitions." + renamedType + ");");
			}
		}

	protected static class TypeDataSet
			extends TypeData {
			
			String elementType;
			
			public TypeDataSet() { }
	
			@Override
			String getJavaGetName() {
	
				return typeByName(elementType).getJavaGetName();
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
	
				return typeByName(elementType).getJavaSetName(optional);
			}
	
			@Override
			TypeCategory getTypeCategory() {
	
				return TypeCategory.Set;
			}
			
			@Override
			String[] getImports() {
				
				return typeByName(elementType).getImports();
			}
			
			boolean getThrowsIllegal() {
				
				return typeByName(elementType).getThrowsIllegal();
			}
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionSet");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionSetImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", a set type definition with " +
							camelToWords(elementType) + " elements.</p>", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@see tv.amwa.maj.industry.StrongReferenceSet", LINE_WIDTH);
				building.wrapComment("@see tv.amwa.maj.industry.WeakReferenceSet", LINE_WIDTH);
				if (typeList.containsKey(elementType))
					building.wrapComment("@see #" + elementType, LINE_WIDTH);
				else
					building.wrapComment("@see tv.amwa.maj.industry.TypeDefinitions#" + elementType, LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionSet " + name + 
						" = new TypeDefinitionSetImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
				if (typeList.containsKey(elementType))
					building.appendNL("        " + elementType + ");");
				else
					building.appendNL("        tv.amwa.maj.industry.TypeDefinitions." + elementType + ");");
			}
		}

	protected static class TypeDataStream
			extends TypeData {
			
			public TypeDataStream() { }
	
			@Override
			String getJavaGetName() {
	
				if (name.equals("Stream"))
					return "Stream";
				else
					return "byte[]";
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
	
				return getJavaGetName();
			}
	
			@Override
			TypeCategory getTypeCategory() {
	
				return TypeCategory.Stream;
			}
			
			@Override 
			public String getAnnotation() {
				
				if (name.equals("Stream"))
					return "DataBuffer";
				else
					return "DataValue";
			}
			
			@Override
			public String[] getImports() {
				
				if (name.equals("Stream"))
					return new String[] {
						"tv.amwa.maj.industry.Stream" };
				else
					return new String[] {
						"tv.amwa.maj.misctype.DataValue" };
				
			}
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionStream");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionStreamImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", a stream type definition.</p>", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@see tv.amwa.maj.industry.Stream", LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionStream " + name + 
						" = new TypeDefinitionStreamImpl(");
				super.appendTypeDescription(building);
				building.appendNL("        \"" + name + "\");");
			}
		}

	protected static class TypeDataString
			extends TypeData {
			
			String elementType;
			
			public TypeDataString() { }
	
			@Override
			String getJavaGetName() {
				
				return "String";
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
				
				return "String";
			}
	
			@Override
			TypeCategory getTypeCategory() {
				
				return TypeCategory.String;
			}
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionString");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionStringImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", a string type definition.</p>", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@see tv.amwa.maj.industry.TypeDefinitions#UTF16String", LINE_WIDTH);
				if (typeList.containsKey(elementType))
					building.wrapComment("@see #" + elementType, LINE_WIDTH);
				else
					building.wrapComment("@see tv.amwa.maj.industry.TypeDefinitions#" + elementType, LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionString " + name + 
						" = new TypeDefinitionStringImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
				
				if (typeList.containsKey(elementType))
					building.appendNL("        " + elementType + ");");
				else
					building.appendNL("        tv.amwa.maj.industry.TypeDefinitions." + elementType + ");");
			}
	
		}

	protected static class TypeDataStrongObjectReference	
			extends TypeData {
		
			String referencedType;
		
			public TypeDataStrongObjectReference() { }
	
			@Override
			String getJavaGetName() {
			
				return Warehouse.javaClassAlias(referencedType);
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
			
				return Warehouse.javaClassAlias(referencedType);
			}
	
			@Override
			TypeCategory getTypeCategory() {
			
				return TypeCategory.StrongObjRef;
			}
	
			@Override
			public String[] getImports() {
				
				try {
					Class<?> referencedClass = Warehouse.lookForClass(referencedType).getJavaImplementation();
					if (referencedClass == null) return NoImports;
						
					for ( Class<?> iface : referencedClass.getInterfaces() ) {
						if (iface.getName().contains(referencedType)) 
							return new String[] { iface.getCanonicalName() };
					}
				}
				catch (ClassCastException cce) { }
				
				return NoImports;
			}
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionStrongObjectReference");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionStrongObjectReferenceImpl");
				building.addImport("tv.amwa.maj.industry.Warehouse");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", a strong object reference to " +
							aOrAn(camelToWords(referencedType)) + ".</p>", LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionStrongObjectReference " + name + 
						" = new TypeDefinitionStrongObjectReferenceImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
				if (classList.containsKey(referencedType)) {
					building.addImport(building.getPackageName() + ".impl." + referencedType + "Impl");
					building.appendNL("        Warehouse.lookForClass(" + referencedType + "Impl.class) );");
				}
				else {
					ClassDefinition baselineClass = Warehouse.lookForClass(referencedType);
					building.appendNL("        Warehouse.lookForClass(" + 
							baselineClass.getJavaImplementation().getCanonicalName() + ".class) );");
				}
			}
	
		}

	protected static class TypeDataVariableArray
			extends TypeData {
			
			String elementType;
			
			public TypeDataVariableArray() { }
	
			@Override
			String getJavaGetName() {
				
				return typeByName(elementType).getJavaGetName();
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
	
				return typeByName(elementType).getJavaSetName(optional);
			}
	
			@Override
			TypeCategory getTypeCategory() {
				
				return TypeCategory.VariableArray;
			}
			
			@Override
			public String[] getImports() {
				
				return typeByName(elementType).getImports();
			}
			
			@Override
			boolean getThrowsIllegal() {
				
				return typeByName(elementType).getThrowsIllegal();
			}
		
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionVariableArray");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionVariableArrayImpl");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", a variable array type definition with " +
							camelToWords(elementType) + " elements.</p>", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@see tv.amwa.maj.industry.StrongReferenceVector", LINE_WIDTH);
				building.wrapComment("@see tv.amwa.maj.industry.WeakReferenceVector", LINE_WIDTH);
				if (typeList.containsKey(elementType))
					building.wrapComment("@see #" + elementType, LINE_WIDTH);
				else
					building.wrapComment("@see tv.amwa.maj.industry.TypeDefinitions#" + elementType, LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionVariableArray " + name + 
						" = new TypeDefinitionVariableArrayImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
				if (typeList.containsKey(elementType))
					building.appendNL("        " + elementType + ");");
				else
					building.appendNL("        tv.amwa.maj.industry.TypeDefinitions." + elementType + ");");
			}
	
		}

	protected static class TypeDataWeakObjectReference
			extends TypeData {
			
			String referencedType;
			List<String> targetSet = new Vector<String>();
			
			public TypeDataWeakObjectReference() { }
	
			@Override
			String getJavaGetName() {
				
				return referencedType;
			}
	
			@Override
			String getJavaSetName(
					boolean optional) {
	
				return referencedType;
			}
	
			@Override
			TypeCategory getTypeCategory() {
				
				return TypeCategory.WeakObjRef;
			}
			
			@Override
			String[] getImports() {
				
				try {
					Class<?> referencedClass = Warehouse.lookForClass(referencedType).getJavaImplementation();
					if (referencedClass == null) return NoImports;
					
					for ( Class<?> iface : referencedClass.getInterfaces() ) {
						if (iface.getName().contains(referencedType)) 
							return new String[] { iface.getCanonicalName() };
					}
				}
				catch (ClassCastException cce) { }
	
				return NoImports;
			}
			
			@Override
			void appendTypeDescription(
					StringIndenter building) {
				
				building.addImport("tv.amwa.maj.meta.TypeDefinitionWeakObjectReference");
				building.addImport("tv.amwa.maj.meta.impl.TypeDefinitionWeakObjectReferenceImpl");
				building.addImport("tv.amwa.maj.industry.Warehouse");
				
				building.startJavadoc();
				if ((description != null) && (description.length() > 0))
					building.wrapComment("<p>" + description + "</p>", LINE_WIDTH);
				else
					building.wrapComment("<p>" + upperFirstLetter(camelToWords(name)) + ", a weak object reference to " +
							aOrAn(camelToWords(referencedType)) + ".</p>", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@see tv.amwa.maj.industry.WeakReference", LINE_WIDTH);
				building.wrapComment("@see tv.amwa.maj.industry.WeakReferenceTarget", LINE_WIDTH);
				building.endComment();
				
				building.append("public final static TypeDefinitionWeakObjectReference " + name + 
						" = new TypeDefinitionWeakObjectReferenceImpl(");
				super.appendTypeDescription(building);
				building.append("        \"" + name + "\",");
				if (classList.containsKey(referencedType)) {
					building.addImport(building.getPackageName() + ".impl." + referencedType + "Impl");
					building.append("        Warehouse.lookForClass(" + referencedType + "Impl.class),");
				}
				else {
					ClassDefinition baselineClass = Warehouse.lookForClass(referencedType);
					building.append("        Warehouse.lookForClass(" + 
							baselineClass.getJavaImplementation().getCanonicalName() + ".class),");
				}
				
				building.appendNL("        new AUID[] { } );");
			}
		}

	protected static TypeData typeByName(String name) {
		
		if (name.startsWith("urn:smpte:ul")) name = name.toLowerCase();
		
		if (typeList.containsKey(name))
			return typeList.get(name);
		
		TypeDefinition widerType = Warehouse.lookForType(name);
		
		if (widerType == null)
			return null;
		
		DocumentFragment fragment = XMLBuilder.createDocumentFragment();
		widerType.appendMetadictXML(fragment, "", "");
		return processTypeDefinition((Element) fragment.getFirstChild());
	}

	protected static final boolean writeFile(File directory, String name,
			String content) {
			
		File fileToWrite = new File(directory, name);
		FileWriter writer = null;

		try {
			writer = new FileWriter(fileToWrite);
			writer.append(content);
			writer.flush();
		}
		catch (IOException ioe) {
			System.err.println("Unable to write to file " + fileToWrite.getAbsolutePath() + ".");
			return false;
		}
		finally {
			if (writer != null) {
				try { writer.close(); } catch (IOException junk) { }
			}
		}

		return true;
	}

	protected static DictionaryContext processRoot(Node node) {
		
		if (node.getNodeName().equals("DataModel"))
			node = getFirstChildElement(node);
	
		DictionaryContext context = new DictionaryContext();
		
		// Mixed mode of extensions and baseline not supported
		if (node.getNodeName().equals("Baseline"))
			context.baseline = true;
		else
			if (!node.getNodeName().equals("Extension"))
				return null;
		
		context.schemeURI = getChildNodeByName("Symbolspace", (Element) node);
		if ((context.schemeURI == null) || (context.schemeURI.length() == 0))
			context.schemeURI = getChildNodeByName("SchemeURI", (Element) node);
		
		String schemeIDAsString = getChildNodeByName("Identification", (Element) node);
		if ((schemeIDAsString == null) || (schemeIDAsString.length() == 0))
			schemeIDAsString = getChildNodeByName("SchemeID", (Element) node);
		context.schemeID = AUIDImpl.parseFactory(schemeIDAsString);
		
		context.extensionDescription = getChildNodeByName("Description", (Element) node);
		if ((context.extensionDescription == null) || (context.extensionDescription.length() == 0))
			context.extensionDescription = getChildNodeByName("ExtensionDescription", (Element) node);
		
		context.preferredPrefix = getChildNodeByName("PreferredPrefix", (Element) node);
		
		NodeList childNodes = null;
		
		for ( int x = 0 ; x < node.getChildNodes().getLength() ; x++ ) {
			Node nodeToCheck = node.getChildNodes().item(x);
			if (nodeToCheck.getNodeName().equals("Definitions")) {
				childNodes = nodeToCheck.getChildNodes();
				break;
			}
		}
		
		if (childNodes == null) return context;
		
		for ( int x = 0 ; x < childNodes.getLength() ; x++ ) {
			if (!(childNodes.item(x) instanceof Element)) continue;
		
			Element child = (Element) childNodes.item(x);
			if (child.getNodeName().equals("ClassDefinition")) {
				processClassDefinition(child);
				continue;
			}
			
			if (child.getNodeName().equals("PropertyDefinition")) {
				processPropertyDefinition(child);
				continue;
			}
			
			if (child.getNodeName().startsWith("TypeDefinition")) {
				TypeData typeData = processTypeDefinition(child);
				typeList.put(typeData.name, typeData);
				typeList.put(typeData.symbol, typeData);
				typeList.put(typeData.identification.toString(), typeData);
				continue;
			}
			
			if (child.getNodeName().equals("ExtendibleEnumerationElement")) {
				processExtendibleEnumerationElement(child);
				continue;
			}
		}
	
		return context;
	}

	static TypeData childToTypeData(Element child) {
		
		TypeData typeData = null;
		
		if (child.getNodeName().contains("Character")) {
			typeData = new TypeDataCharacter();
				
			return typeData;
		}
		
		if (child.getNodeName().contains("nEnumeration")) {
			typeData = new TypeDataEnumeration();
	
	   		((TypeDataEnumeration) typeData).elementType =
				getChildNodeByName("ElementType", child);
			
			NodeList children = child.getChildNodes();
			for ( int x = 0 ; x < children.getLength() ; x++ ) {
				if ((children.item(x) instanceof Element) && (children.item(x).getNodeName().equals("Elements"))) {
					Element elements = (Element) children.item(x);
					
					NodeList elementsNodes = elements.getChildNodes();
					for ( int y = 0 ; y < elementsNodes.getLength() ; y++ ) {
						
						if ((elementsNodes.item(y) instanceof Element) &&
								(elementsNodes.item(y).getNodeName().equals("Name"))) {
	
							((TypeDataEnumeration) typeData).names.add(elementsNodes.item(y).getTextContent());
							continue;
						}
						
						if ((elementsNodes.item(y) instanceof Element) &&
								(elementsNodes.item(y).getNodeName().equals("Value"))) {
							((TypeDataEnumeration) typeData).values.add(elementsNodes.item(y).getTextContent());
							continue;
						}
					}
					
					break;
				}
			}
			
			return typeData;
		}
		
		if (child.getNodeName().contains("ExtendibleEnumeration")) {
			typeData = new TypeDataExtendibleEnumeration();
				
			return typeData;
		}
		
		if (child.getNodeName().contains("FixedArray")) {
			typeData = new TypeDataFixedArray();
			((TypeDataFixedArray) typeData).elementCount =
				Integer.parseInt(getChildNodeByName("ElementCount", child));
			((TypeDataFixedArray) typeData).elementType = 
				getChildNodeByName("ElementType", child);
			
			return typeData;
		}
		
		if (child.getNodeName().contains("Indirect")) {
			typeData = new TypeDataIndirect();
			
			return typeData;
		}    			
		
		if (child.getNodeName().contains("Integer")) {
			typeData = new TypeDataInteger();
			
			((TypeDataInteger) typeData).size = 
				Integer.parseInt(getChildNodeByName("Size", child));
			((TypeDataInteger) typeData).isSigned = 
				Boolean.parseBoolean(getChildNodeByName("IsSigned", child));
			
			return typeData;
		}
		
		if (child.getNodeName().contains("Opaque")) {
			typeData = new TypeDataOpaque();
			    		
			return typeData;
		}
		
		if (child.getNodeName().contains("Record")) {
			typeData = new TypeDataRecord();
			
			NodeList children = child.getChildNodes();
			for ( int x = 0 ; x < children.getLength() ; x++ ) {
				if ((children.item(x) instanceof Element) && (children.item(x).getNodeName().equals("Members"))) {
					Element members = (Element) children.item(x);
					
					NodeList elementsNodes = members.getChildNodes();
					for ( int y = 0 ; y < elementsNodes.getLength() ; y++ ) {
						
						if ((elementsNodes.item(y) instanceof Element) &&
								(elementsNodes.item(y).getNodeName().equals("Name"))) {
							((TypeDataRecord) typeData).memberNames.add(elementsNodes.item(y).getTextContent());
							continue;
						}
						
						if ((elementsNodes.item(y) instanceof Element) &&
								(elementsNodes.item(y).getNodeName().equals("Type"))) {
							((TypeDataRecord) typeData).memberTypes.add(elementsNodes.item(y).getTextContent());
							continue;
						}
					}
					
					break;
				}
			}
			
			return typeData;
		}
		
		if (child.getNodeName().contains("Rename")) {
			typeData = new TypeDataRename();
			
			((TypeDataRename) typeData).renamedType = getChildNodeByName("RenamedType", child);
			
			return typeData;
		}
		
		if (child.getNodeName().contains("Set")) {
			typeData = new TypeDataSet();
			
			((TypeDataSet) typeData).elementType = getChildNodeByName("ElementType", child);
			
			return typeData;
		}
		
		if (child.getNodeName().contains("Stream")) {
			typeData = new TypeDataStream();
				
			return typeData;
		}
	
		if (child.getNodeName().contains("String")) {
			typeData = new TypeDataString();
			
			((TypeDataString) typeData).elementType = getChildNodeByName("ElementType", child);
			
			return typeData;
		}
		
		if (child.getNodeName().contains("StrongObjectReference")) {
			typeData = new TypeDataStrongObjectReference();
			
			((TypeDataStrongObjectReference) typeData).referencedType = 
				getChildNodeByName("ReferencedType", child);
			
			return typeData;
		}
		
		if (child.getNodeName().contains("VariableArray")) {
			typeData = new TypeDataVariableArray();
			
			((TypeDataVariableArray) typeData).elementType = 
				getChildNodeByName("ElementType", child);
			
			return typeData;
		}
		
		if (child.getNodeName().contains("WeakObjectReference")) {
			typeData = new TypeDataWeakObjectReference();
			
			((TypeDataWeakObjectReference) typeData).referencedType =
				getChildNodeByName("ReferencedType", child);
			
			return typeData;
		}
	
		System.err.println("Unable to process a type definition with element name " + child.getNodeName() + ".");
		return null;
	}

	static TypeData processTypeDefinition(Element child) {
		
		TypeData typeData = childToTypeData(child);
		
		if (typeData == null) return null;
		
		setMetaProperties(child, typeData);
		
		return typeData;
	}

	static void processExtendibleEnumerationElement(Element child) {
		
		String extendibleEnumerationName = getChildNodeByName("ElementOf", child);
		String elementName = getChildNodeByName("Name", child);
		AUID elementValue = AUIDImpl.parseFactory(getChildNodeByName("Value", child));
		
		TypeDataExtendibleEnumeration enumByName = 
			(TypeDataExtendibleEnumeration) typeByName(extendibleEnumerationName);
		
		Warehouse.registerExtendibleEnumerationElement(enumByName.symbol, elementName, elementValue);
	}

	static void processPropertyDefinition(Element child) {
		
		PropertyData propertyData = new PropertyData();
		setMetaProperties(child, propertyData);
		
		propertyData.memberOf = getChildNodeByName("MemberOf", child);
		propertyData.isOptional = Boolean.parseBoolean(getChildNodeByName("IsOptional", child));
		propertyData.type = getChildNodeByName("Type", child);
		
		String localIdentificationString = getChildNodeByName("LocalIdentification", child);
		if (localIdentificationString.startsWith("0x")) {
			propertyData.localIdentification = (short) Integer.parseInt(localIdentificationString.substring(2), 16);
		}
		else
			propertyData.localIdentification = Short.parseShort(localIdentificationString);
		
		String unique = getChildNodeByName("IsUniqueIdentifier", child);
		if (unique != null) {
			propertyData.isUniqueIdentifier = Boolean.parseBoolean(unique);
			if (propertyData.type.equals("AUID"))
				weakReferenceTargets.put(propertyData.memberOf, propertyData.name);
		}
			
		propertyList.put(propertyData.memberOf + "_" + propertyData.name, propertyData);
	}

	static void processClassDefinition(Element child) {
	
		ClassData classData = new ClassData();
		setMetaProperties(child, classData);
		
		classData.parentClass = getChildNodeByName("ParentClass", child);
		classData.isConcrete = Boolean.parseBoolean(getChildNodeByName("IsConcrete", child));
	
		classList.put(classData.name, classData);
	}

	static void setMetaProperties(Element parent, MetaData metaData) {
		
		metaData.name = getChildNodeByName("Name", parent);
		metaData.description = getChildNodeByName("Description", parent);
		metaData.symbol = getChildNodeByName("Symbol", parent);
		metaData.identification = AUIDImpl.parseFactory(getChildNodeByName("Identification", parent));
	}

	static String getChildNodeByName(String childName, Element parent) {
		
		NodeList nodes = parent.getChildNodes();
		String result = null;
		for ( int x = 0 ; x < nodes.getLength() ; x++ ) {
			
			if (!(nodes.item(x) instanceof Element))
				continue;
			
			String localChildName = childName;
			if (nodes.item(x).getPrefix() != null)
				localChildName = nodes.item(x).getPrefix() + ":" + localChildName;
			
			if (nodes.item(x).getNodeName().equals(localChildName)) {
				NodeList children = nodes.item(x).getChildNodes();
				if (children.getLength() > 0)
					result = children.item(0).getNodeValue().trim();
				else
					result = "";
				break;
			}	
	
		}
		
		if ((result != null) && (result.length() > 0)) {
			StringTokenizer tokenizer = new StringTokenizer(result);
			result = tokenizer.nextToken();
			while (tokenizer.hasMoreTokens()) {
				result += " " + tokenizer.nextToken();
			}
		}
		return result;
	}
	
	static Node getFirstChildElement(Node parent) {
		
		NodeList nodes = parent.getChildNodes();
		for ( int x = 0 ; x < nodes.getLength() ; x++ )
			if (nodes.item(x) instanceof Element)
				return nodes.item(x);
		
		return null;
	}

	protected static class StringIndenter {
			
			public final static int INDENT_SIZE = 4;
			
			private StringBuffer buffer = new StringBuffer();
			private int indent = 0;
			private SortedSet<String> imports = new TreeSet<String>();
			private String packageName = null;
			
			public StringIndenter() { }
	
			public void addImport(
					String canonicalName) {
				
//				System.out.println("Canonical name = " + canonicalName + " package = " + packageName +
//						" lastDot = " + canonicalName.lastIndexOf('.') + " packageNameLength = " + packageName.length());
				
				if ((canonicalName != null) && (canonicalName.length() > 0))
//						(!(canonicalName.startsWith(packageName)) && 
//								(canonicalName.lastIndexOf('.') == packageName.length())))
					imports.add(canonicalName);
			}
	
			public void addImport(
					String[] imports) {
				
				for ( String imported : imports )
					addImport(imported);
			}
	
			public void setPackageName(
					String packageName) {
				
				this.packageName = packageName;
			}
			
			public String getPackageName() {
				
				return packageName;
			}
			
			private void indent() {
				
				for ( int x = 0 ; x < indent ; x++ )
					buffer.append(' ');
			}
			
			public void append(
					String line) {
				
				indent();
				buffer.append(line);
				buffer.append('\n');
			}
	
			public void appendNL(
					String line) {
				
				append(line);
				buffer.append('\n');
			}
			
			public void increment(
					String line) {
				
				indent += INDENT_SIZE;
				append(line);
			}
			
			public void incrementNL(
					String line) {
				
				indent += INDENT_SIZE;
				appendNL(line);
			}
			
			public void decrement(
					String line) {
				
				indent -= INDENT_SIZE;
				append(line);
			}
			
			public void decrementNL(
					String line) {
				
				indent -= INDENT_SIZE;
				appendNL(line);
			}
			
			public void reset(
					String line) {
				
				indent = 0;
				append(line);
			}
			
			public void resetNL(
					String line) {
				
				indent = 0;
				appendNL(line);
			}
			
			public void wrapComment(
					String comment,
					int lineWidth) {
				
				StringTokenizer tokenizer = new StringTokenizer(comment);
				
				StringBuffer currentLine = new StringBuffer(lineWidth + 10);
				currentLine.append(" *");
				
				while (tokenizer.hasMoreElements()) {
					String token = tokenizer.nextToken();
					currentLine.append(" ");
					currentLine.append(token);
					if ((currentLine.length() > lineWidth) && (tokenizer.hasMoreElements())) {
						append(currentLine.toString());
						currentLine = new StringBuffer(lineWidth + 10);
						currentLine.append(" *");
					}
				}
				
				append(currentLine.toString());
			}
			
			public void startJavadoc() {
				
				append("/**");
			}
			
			public void blankComment() {
				
				append(" *");
			}
			
			public void endComment() {
				
				append(" */");
			}
			
			public void backWrite(
					String previousLineEnding) {
				
				buffer.insert(buffer.length() - 1, previousLineEnding);
			}
			
			public void addThrowsClause(
					List<String> exceptions,
					boolean isInterface) {
				
				if ((exceptions == null) || (exceptions.size() == 0)) {
					backWrite(isInterface ? ";\n" : " {\n");
					if (!isInterface) indent += INDENT_SIZE;
					return;
				}
				
				indent();
				buffer.append("    throws " + exceptions.get(0));
				
				for ( int x = 1 ; x < exceptions.size() ; x++ ) {
					buffer.append(",\n");
					indent();
					buffer.append("        " + exceptions.get(x));
				}
				
				buffer.append(isInterface ? ";\n\n" : " {\n\n");	
				if (!isInterface) indent += INDENT_SIZE;
			}
			
			public String toString() {
				
				if (imports.size() > 0)
					buffer.insert(0, '\n');
				
				List<String> orderedImports = new Vector<String>(imports);
				
				for ( int x = orderedImports.size() - 1 ; x >= 0 ; x-- )
					buffer.insert(0, "import " + orderedImports.get(x) + ";\n");
				
				if ((packageName != null) && (packageName.length() > 0))
					buffer.insert(0, "package " + packageName + ";\n\n");
				
				return buffer.toString();
			}
		}

	protected static class DictionaryContext {
			
			AUID schemeID;
			String schemeURI;
			String preferredPrefix;
			String extensionDescription;
			String basePackageName;
			
			String factoryName;
			
			File rootDir;
			File interfaceDir;
			File implementationDir;
			File testModelDir;
			File testMetaDir;
			
			boolean baseline;
			
			public DictionaryContext() { }
		}

	protected static final String lowerFirstLetter(
			String changeMe) {
		
		if (Character.isUpperCase(changeMe.charAt(0))) {
				StringBuffer replacement = new StringBuffer(changeMe);
				replacement.setCharAt(0, Character.toLowerCase(changeMe.charAt(0)));
				return replacement.toString();
		}
		
		return changeMe;
	}

	protected static final String upperFirstLetter(String changeMe) {
		
		if (Character.isLowerCase(changeMe.charAt(0))) {
			StringBuffer replacement = new StringBuffer(changeMe);
			replacement.setCharAt(0, Character.toTitleCase(changeMe.charAt(0)));
			return replacement.toString();
		}
		
		return changeMe;
	}

	protected static final String firstSentence(String changeMe) {
		
		int firstDot = -1;
		
		for ( int x = 1 ; x < changeMe.length() ; x++ ) {
			if ((changeMe.charAt(x - 1) == '.') && 
					(Character.isWhitespace(changeMe.charAt(x)))) {
				firstDot = x - 1;
				break;
			}
		}
		
		if ((firstDot == -1) && (changeMe.charAt(changeMe.length() - 1) == '.'))
			return changeMe;
		
		return changeMe.substring(0, firstDot + 1);
	}

	protected static final String camelToWords(String changeMe) {
		
		List<String> words = new Vector<String>();
		if ((changeMe == null) || (changeMe.length() == 0))
			return "";
		
		StringBuffer currentWord = new StringBuffer(); 
		currentWord.append(Character.toLowerCase(changeMe.charAt(0)));
		
		for ( int x = 1 ; x < changeMe.length() ; x++ ) {
			
			char currentChar = changeMe.charAt(x);
			
			if (Character.isUpperCase(currentChar)) {
				words.add(currentWord.toString());
				currentWord = new StringBuffer();
				currentWord.append(Character.toLowerCase(currentChar));
				continue;
			}
			
			currentWord.append(currentChar);
		}
		
		if (currentWord.length() > 0) 
			words.add(currentWord.toString());
		
		StringBuffer result = new StringBuffer(words.get(0));
		for ( int x = 1 ; x < words.size() ; x++ ) {
			result.append(' ');
			result.append(words.get(x));
		}
		
		return result.toString();
	}

	protected static String makeSingular(String changeMe) {
		
		if ((changeMe.endsWith("s")) || (changeMe.endsWith("S")))
			return changeMe.substring(0, changeMe.length() - 1);
		
		return changeMe;
	}

	protected static String aOrAn(String noun) {
		
		if ((noun == null) || (noun.length() == 0))
			return "a";
		
		switch (noun.charAt(0)) {
		
		case 'a':
		case 'A':
		case 'e':
		case 'E':
		case 'i':
		case 'I':
		case 'o':
		case 'O':
		case 'u':
		case 'U':
			return "an " + noun;
		default:
			return "a " + noun;
		} 
	}

	protected static String padHexTo8(int value) {
		
		StringBuffer buffer = new StringBuffer(Integer.toHexString(value));
		
		int zerosToPad = 8 - buffer.length();
		for ( int x = 0 ; x < zerosToPad ; x++ )
			buffer.insert(0, '0');
		
		return buffer.toString();
	}

	protected static String padHexTo4(short value) {
		
		StringBuffer buffer = new StringBuffer(Integer.toHexString(value));
		if (buffer.length() > 4)
			buffer.delete(0, buffer.length() - 4);
		
		int zerosToPad = 4 - buffer.length();
		for ( int x = 0 ; x < zerosToPad ; x++ )
			buffer.insert(0, '0');
		
		return buffer.toString();		
	}

	protected static String bytesToText(byte[] value) {
				
		StringBuffer buffer = new StringBuffer();
		
		for ( int x = 0 ; x < value.length ; x++ ) {
			
			int current = (value[x] >= 0) ? value[x] : 256 + value[x];
			if (current > 0x7f) buffer.append("(byte) ");
			buffer.append("0x");
			if (current < 0x10) buffer.append('0');
			buffer.append(Integer.toHexString(current));
			
			if (x < (value.length - 1)) buffer.append(", ");
		}
		
		return buffer.toString();
	}

	protected static final boolean isNullableType(TypeData type) {
		
		switch (type.getTypeCategory()) {
		
		case Int:
			return false;
		case Enum:
			return type.name.equals("Boolean") ? false : true;
		case Character:
			return false;
		default:
			return true;
		}
	}

	public static final boolean makeDirectories(DictionaryContext context) {
		
		boolean success = true;
		
		context.rootDir = new File(".");
		
		if (!context.rootDir.isDirectory()) return false;
		if (!context.rootDir.canWrite()) return false;
		
		File srcRootDir = new File(context.rootDir, "src");
		File testRootDir = new File(context.rootDir, "test");
		
		if (!srcRootDir.exists()) success &= srcRootDir.mkdir();
		if (!testRootDir.exists()) success &= testRootDir.mkdir();
		
		if (context.baseline) {
			context.testModelDir = makePackageDir(testRootDir, context.basePackageName + ".model.impl");
			context.testMetaDir = makePackageDir(testRootDir, context.basePackageName + ".meta.impl");
			
			if ((context.testModelDir == null) || (context.testMetaDir == null)) return false;
		}
		else {
			context.interfaceDir = makePackageDir(srcRootDir, context.basePackageName);
		
			context.implementationDir = makePackageDir(srcRootDir, context.basePackageName + ".impl");
			context.testModelDir = makePackageDir(testRootDir, context.basePackageName + ".impl");
			
			if ((context.interfaceDir == null) || (context.implementationDir == null) ||
					(context.testModelDir == null)) 
				return false;
		}

		return success;
	}

	final static File makePackageDir(
			File rootDir,
			String packageName) {
		
		StringTokenizer splitPackage = new StringTokenizer(packageName, ".");
		File parent = rootDir;
		File child = parent;
		
		boolean success = true;
		
		while (splitPackage.hasMoreElements()) {
			String pathPart = (String) splitPackage.nextElement();
			
			child = new File(parent, pathPart);
			
			if (!child.exists()) success &= child.mkdir();
			
			parent = child;
		}
		
		if (!success) return null;
	
		return child;
	}
}
