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
 * $Log: AutoGeneration.java,v $
 * Revision 1.16  2011/10/07 19:40:27  vizigoth
 * Stop cloning strong references.
 *
 * Revision 1.15  2011/10/05 17:14:31  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.14  2011/07/27 17:11:21  vizigoth
 * Minor update to comment on move of meta dictionary generator.
 *
 * Revision 1.13  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.12  2011/01/21 17:35:21  vizigoth
 * Refactor to extract common generation code and initial authoring of code to generate JUnit tests.
 *
 * Revision 1.11  2011/01/13 17:44:31  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.10  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.9  2010/12/15 19:09:41  vizigoth
 * Be a bit less private to allow for future extension.
 *
 * Revision 1.8  2010/11/08 16:44:04  vizigoth
 * Fixed problem found when no eumeration elements are defined.
 *
 * Revision 1.7  2010/03/05 14:46:53  vizigoth
 * Minor fixes, including making sure factory initialization happens once only and that the property keys are unique in propertyList.
 *
 * Revision 1.6  2010/03/05 09:35:42  vizigoth
 * Minor fixes to add imports for strong and weak references to implementations and fix description formatting.
 *
 * Revision 1.5  2010/03/04 13:21:45  vizigoth
 * First complete auto generation that produces code that compiles OK.
 *
 */


package tv.amwa.maj.util;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.record.AUID;

/*
 * TODO When registering extension elements for an existing extendible enumeration, a constants class
 * needs to be generated and then the elements added using the factory.
 * 
 * Problem noted on BBC fabric project, file FabricCoreFramework_2.xml
 */

/**
 * <p>Generate Java classes, interfaces and tests from meta dictionary extension descriptions. This
 * command line utility generates a number of Java files from an XML meta dictionary description that can
 * be compiled and used with the {@linkplain tv.amwa.maj.industry.MediaEngine media engine} and
 * {@linkplain tv.amwa.maj.industry.Forge forge} to create, serialize and deserialize values of the 
 * defined types.</p>
 * 
 * <p>The typical usage is described in the following steps:</p>
 * 
 * <ol>
 *  <li><p>Describe a meta dictionary in XML according to the registered data XML meta dictionary format. The
 *  namespace is:</p><p><center><code>http://www.smpte-ra.org/schemas/2001-1/2007/metadict</code></center></p>
 *  <p>(See the file <code>AutoFramework.xml</code> in the MAJ distribution for an example, or generate 
 *  some examples using the {@linkplain tv.amwa.maj.io.xml.MetaDictionaryGenerator#generateMetaDictionary(java.util.Collection, java.util.Collection, AUID, String, String, String, AUID, String, String, String, boolean) media engine's
 *  meta dictionary generator}.)</p></li>
 *  
 *  <li><p>Run this java class as an application, providing a package name and the meta dictionary XML file:</p>
 *  <p><code>&nbsp;&nbsp;&nbsp;&nbsp;java tv.amwa.maj.util.AutoGeneration&nbsp;</code>&lt;<em>package_name</em>&gt;<code>&nbsp;</code>&lt;<em>meta_dictionary_file</em>&gt;</p></li>
 * 
 * 	<li>This generates a folder called <code>src</code> and a folder called <code>test</code> in the current
 *  working directory. These new folders contain the generated Java files according to the convention for turning a 
 *  Java package name into a directory hierarchy. (Better configuration of the location will be provided in a future 
 *  versions. Tests are not yet generated.)</li>
 *  
 *  <li>Optionally, patch the Java files. An automatic approach like this can never produce the most efficient
 *  code and may not work in all instances. It is recommended that you use an automatic patch mechanism as part
 *  of any build process.</li>
 *  
 *  <li><p>Compile the generated Java files according to your local build strategy. For example:</p>
 *   <ul>
 *    <li><code>cd src/com/portability4media/framework</code></li>
 *    <li><code>javac -classpath $MAJ_JAR -d $COMPILE_TARGET *.java impl/*.java</code></li>
 *   </ul>
 *   <p>Note that this code has been tested with Java&nbsp;6.</p></li>
 *  
 *  <li><p>Initialize the compiled types and classes in your application. A factory is generated as part 
 *  of the auto generation to enable you to safely initalize all the required components in the correct order.
 *  For example:</p>
 *  <center>&lt;<em>package_name</em>&gt;<code>.Factory.initialize();</code></center>
 *  <p>Remember to {@linkplain tv.amwa.maj.industry.MediaEngine#initializeAAF() initialize the AAF baseline classes} 
 *  first if these are required.</p></li>
 *  
 *  <li><p>Create instances of the classes described in the meta dictionary. Either use the generic 
 *  factory methods in the {@linkplain tv.amwa.maj.industry.MediaEngine media engine} or the <code>make</code>
 *  method provided in the newly created factory. For example, for a class with no required properties:</p>
 *  <p><code>&nbsp;&nbsp;&nbsp;&nbsp;</code>&lt;<em>class_name</em>&gt;<code> firstInstance =</code><br>
 *  <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</code>&lt;<em>package_name</em>&gt;<code>.Factory.make("</code>&lt;<em>class_name</em>&gt;<code>");</code></p></li>
 *  
 * </ol>
 * 
 * <p>Below is a brief summary of what this class does when it runs:</p>
 * 
 * <ul>
 *  <li>All class definitions create a Java interface and a class that implements that interface. Any inheritance
 *  described in the parent class specification is followed to create (abstract) Java classes and extension
 *  relationships.</li>
 *  
 *  <li>All classes are cloneable and appropriate clone methods are added to the interfaces and 
 *  implementations in such a way that an application does not have to perform a cast.</li>
 *  
 *  <li>All classes can be tested for equality, can generate a hash code and write themselves out as a string.
 *  The string representation is according to the SMPTE 2001-1 registered data XML format.</li>
 *  
 *  <li>All implementations have {@linkplain tv.amwa.maj.industry.MediaClass media class} and 
 *  {@linkplain tv.amwa.maj.industry.MediaProperty media property} annotations, amongst others, added so
 *  that the MAJ {@linkplain tv.amwa.maj.industry.MediaEngine media engine} can be used to manage instances of
 *  the extension class. At runtime, definitions of the classes can be accessed through the 
 *  {@linkplain tv.amwa.maj.industry.Warehouse warehouse}.</li>
 *  
 *  <li>All property definitions for a class are added to the interface for that class. This is normally 
 *  achieved by a pair of get and set methods. Properties of set and variable array type have append, add, remove,
 *  contains etc. methods generated to manage their elements.<li>

 *  <li><p>All type definitions result in a static instance to represent them in an interface called 
 *  <Code>TypeDefinitions</code>. The following additional files are generated from these definitions:</p>
 *  
 *   <ul>
 *    <li>All {@linkplain tv.amwa.maj.meta.TypeDefinitionEnumeration enumeration types} result in the generation 
 *    of a Java <code>enum</code>. This allows an application developer to manipulate enumeration values in the 
 *    standard Java way.</li>
 *    <li>All {@linkplain tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration extendible extendible enumeration types}
 *    result in a Java interface containing static constants representing each extendible enumeration element. This
 *    can be used by an application developer to use names to set enumeration values. These interface files can
 *    be used to seed values into the {@linkplain tv.amwa.maj.industry.Warehouse warehouse}.<li>
 *    <li><p>All {@linkplain tv.amwa.maj.meta.TypeDefinitionRecord record types} result in the generation of 
 *    an interface and a class that is an implementation of that interface. The interface has get and set 
 *    methods for each member and the implementation provides basic equals, hash code, clone and to string methods.</p>
 *    <p>The string representation provides an XML representation of the value. A static <em>parse</em> method to read a 
 *    value represented that way and turn it back into a value of the record type is also provided.</p></li>
 *    <li>Although no additional file is generated, classes that have the potential to be 
 *    {@linkplain tv.amwa.maj.industry.WeakReferenceTarget weak reference targets} have suitable code
 *    injected so that they can be referenced and dereferenced at runtime.</p>
 *   </ul>
 *   
 *   <p>At runtime, definitions of the types can be accessed through the 
 *   {@linkplain tv.amwa.maj.industry.Warehouse warehouse}.</p></li>
 *   
 *   <li>A file of common constants is created, including the symbol space for the extension meta dictionary.</li>
 *   
 *   <li><p>A factory class is generated with convenience methods for initializing and creating instances of the
 *   defined classes and types. The process of initialization includes:</p>
 *   
 *    <ul>
 *     <li>Registering all the extension classes with the {@linkplain tv.amwa.maj.industry.Warehouse warehouse}.</li>
 *     <li>Registering all of the extendible enumeration values with the 
 *     {@linkplain tv.amwa.maj.industry.Warehouse warehouse}.</li>
 *     <li>Registering the implementing classes for all record type values.</li>
 *     <li>Registering all the extension type definitions with the
 *     {@linkplain tv.amwa.maj.industry.Warehouse warehouse}.</li>
 *    </ul>
 *    
 *    <p>The factory also contains methods to create and parse values of the extension record types.</p></li>
 *  <ul>
 * 
 *
 *
 */
public class AutoGeneration 
	extends GenerationCore {
	
	static {
		MediaEngine.initializeAAF();
	}
	
	public final static void main(
			String[] args) {

		if (args.length < 2) {
			System.out.println("Usage: java tv.amwa.maj.util.AutoGeneration <base_package_name> <metadictionary_XML_file>");
			System.exit(1);
		}
		
		DictionaryContext context = null;
		
		try{
			File file = new File(args[1]);
			if (file.exists()){
				DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = fact.newDocumentBuilder();
				Document doc = builder.parse(args[1]);
				Node node = doc.getDocumentElement();
				context = processRoot(node);
			}
			else{
				System.out.println("File not found!");
			}	
		}
		catch(Exception e){
			System.err.println(e.getClass().getName() + " thrown: " + e.getMessage());
			e.printStackTrace();
		}
		
		context.basePackageName = args[0];
		
		if (makeDirectories(context) == false) {
			System.out.println("Unable to create the required directory structure.");
			System.exit(1);
		}
		
		boolean success = true;
		
		success &= writeFile(context.interfaceDir, "Constants.java", generateConstants(context));
		
		for ( ClassData classData : classList.values() ) {
			success &= writeFile(
					context.interfaceDir, 
					classData.name + ".java", 
					generateInterface(context.basePackageName, classData));
			success &= writeFile(
					context.implementationDir,
					classData.name + "Impl.java",
					generateImplementation(context.basePackageName, classData));
		}
		
		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataEnumeration) 
				success &= writeFile(
						context.interfaceDir, 
						typeData.name + ".java", 
						generateEnumeration(context.basePackageName, (TypeDataEnumeration) typeData));

		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataExtendibleEnumeration) 
				success &= writeFile(
						context.interfaceDir, 
						typeData.name + ".java", 
						generateExtendibleEnumeration(context.basePackageName, typeData.name, typeData.symbol));
		
		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataRecord) {
				success &= writeFile(
						context.interfaceDir,
						typeData.name + ".java",
						generateRecordInterface(context.basePackageName, (TypeDataRecord) typeData));
				success &= writeFile(
						context.implementationDir,
						typeData.name + "Impl.java",
						generateRecordImplementation(context.basePackageName, (TypeDataRecord) typeData));
			}

		success &= writeFile(
				context.interfaceDir,
				"TypeDefinitions.java",
				generateTypeDefinitions(context.basePackageName));
		
		if ((context.preferredPrefix != null) && (context.preferredPrefix.length() > 0))
			context.factoryName = upperFirstLetter(context.preferredPrefix) + "Factory";
		else
			context.factoryName = "Factory";
		
		success &= writeFile(
				context.interfaceDir,
				context.factoryName + ".java",
				generateFactory(context));
		
		if (success)
			System.out.println("Successfully generated all files for package " + context.basePackageName + ".");
		else
			System.out.println("Problem encountered generating files for package " + context.basePackageName + ".");
	}

	static String generateInterface(
			String packageName,
			ClassData classData) {
		
		StringIndenter building = new StringIndenter();
		
		building.setPackageName(packageName);
		
		building.startJavadoc();
		building.wrapComment("<p>" + classData.description + "</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@author Auto generation.", LINE_WIDTH);
		building.endComment();
		
		building.addImport("tv.amwa.maj.industry.MediaEntity");
		
		building.append("public interface " + classData.name);
		building.increment("extends");
		building.append("    MediaEntity,"); // Everything is a media entity
		if (weakReferenceTargets.containsKey(classData.name)) {
			building.append("    WeakReferenceTarget,");
			building.addImport("tv.amwa.maj.industry.WeakReferenceTarget");
		}
		if ((classData.parentClass == null) || (classData.parentClass.length() == 0))
			building.appendNL("    Cloneable {");
		else {
			building.append("    Cloneable,");
			
			if (classList.containsKey(classData.parentClass)) {
				building.appendNL("    " + classData.parentClass + " {");
			}
			else {
				ClassDefinition widerClass = Warehouse.lookForClass(classData.parentClass);
				Class<?>[] interfaces = widerClass.getJavaImplementation().getInterfaces();
				
				// TODO check weirder edge cases where Java name is different e.g. Timecode and TimecodeComponent
				for (Class<?> iface : interfaces) {
					if (iface.getName().contains(classData.parentClass)) {
						building.appendNL("    " + classData.parentClass + " {");
						building.addImport(iface.getCanonicalName());
						break;
					}
				}
			}
		}
		
		for ( PropertyData property : propertyList.values() )
			if (property.memberOf.equals(classData.name)) {
				addInterfaceGetter(building, property);
				addInterfaceSetter(building, property);
			}
				
		building.startJavadoc();
		building.wrapComment("<p>Create a cloned copy of this " + camelToWords(classData.name) + ".</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@return Cloned copy of this " + camelToWords(classData.name) + ".", LINE_WIDTH);
		building.endComment();
		building.appendNL("public " + classData.name + " clone();");
		
		building.reset("}");
		
		return building.toString();
	}

	static void addInterfaceGetter(
			StringIndenter building,
			PropertyData property) {
		
		TypeData typeData = typeByName(property.type);
		building.addImport(typeData.getImports());
		
		String annotation = typeData.getAnnotation();
		if (annotation == null) 
			annotation = "";
		else
			annotation = "@" + annotation + " ";
		
		building.startJavadoc();
		building.wrapComment("<p>Returns the " + lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		if (property.isOptional) {
			building.wrapComment("<p>This is an optional property.</p>", LINE_WIDTH);
			building.blankComment();
		}
		building.wrapComment("@return " + firstSentence(property.description), LINE_WIDTH);
		if (property.isOptional) {
			building.append(" *");
			building.wrapComment("@throws PropertyNotPresentException The optional " + camelToWords(property.name) + 
					" property is not present for this " + camelToWords(property.memberOf) + ".", LINE_WIDTH);
		}
		building.endComment();
		
		switch (typeData.getTypeCategory()) {
		
		case VariableArray:
			building.append("public List<? extends " + typeByName(property.type).getJavaGetName() + 
					"> get" + property.name + "()" + (property.isOptional ? "" : ";\n"));
			building.addImport("java.util.List");
			break;
			
		case FixedArray:
			building.append("public " + annotation + typeByName(property.type).getJavaGetName() + 
					"[] get" + property.name + "()" + (property.isOptional ? "" : ";\n"));
			break;
			
		case Set:
			building.append("public Set<? extends " + typeByName(property.type).getJavaGetName() + 
					"> get" + property.name + "()" + (property.isOptional ? "" : ";\n"));
			building.addImport("java.util.Set");
			break;
			
		default:
			building.append("public " + annotation + typeByName(property.type).getJavaGetName() + 
					" get" + property.name + "()" + (property.isOptional ? "" : ";\n"));
			break;
		}
	
		if (property.isOptional) {
			building.appendNL("    throws PropertyNotPresentException;");
			building.addImport("tv.amwa.maj.exception.PropertyNotPresentException");
		}		

	}
	
	static void addInterfaceSetter(
			StringIndenter building,
			PropertyData property) {
		
		TypeData typeData = typeByName(property.type);
		building.addImport(typeData.getImports());
		
		String annotation = typeData.getAnnotation();
		if (annotation == null) 
			annotation = "";
		else
			annotation = "@" + annotation + " ";

		switch (typeData.getTypeCategory()) {
					
		case Set:
			setTypeInterfaceMethods(building, property, typeData, annotation);
			break;
		case VariableArray:
			variableArrayInterfaceMethods(building, property, typeData, annotation);
			break;
		case FixedArray:
			fixedArrayInterfaceMethods(building, property, typeData, annotation);
			break;
		default:
			defaultInterfaceSetter(building, property, typeData, annotation);
			break;
		}
	
	}
	
	private static void defaultInterfaceSetter(
			StringIndenter building,
			PropertyData property,
			TypeData typeData,
			String annotation) {
		
		building.startJavadoc();
		building.wrapComment("<p>Sets the " + lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		if (property.isOptional) {
			building.wrapComment("<p>Set this optional property to <code>null</code> to omit it.</p>", LINE_WIDTH);
			building.blankComment();
		}
		building.wrapComment("@param " + lowerFirstLetter(property.name) + " " + firstSentence(property.description), LINE_WIDTH);
		if ((!property.isOptional) && (isNullableType(typeData))) {
			building.blankComment();
			building.wrapComment("@throws NullPointerException Cannot set the required " + camelToWords(property.name) + 
					" with a <code>null</code> value.", LINE_WIDTH);
		}
		if (typeData.getThrowsIllegal()) {
			if (property.isOptional) building.blankComment();
			building.wrapComment("@throws IllegalArgumentException Cannot set the " + camelToWords(property.name) + 
					" property with the given value.", LINE_WIDTH);
		}
		building.endComment();

		building.append("public void set" + property.name + "(");
		building.append("        " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(property.name) + ")");

		List<String> exceptional = new Vector<String>();
		if ((!property.isOptional) && (isNullableType(typeData)))
			exceptional.add("NullPointerException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");
		
		building.addThrowsClause(exceptional, true);

	}
	
	private static void setTypeInterfaceMethods(
			StringIndenter building,
			PropertyData property,
			TypeData typeData,
			String annotation) {
		
		building.startJavadoc();
		building.wrapComment("<p>Add " + aOrAn(camelToWords(typeData.getJavaGetName())) + " to the set of " + 
				camelToWords(property.name) + " for this " + camelToWords(property.memberOf) + ". This is a " +
				lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		if (property.isOptional) {
			building.wrapComment("<p>This is an optional property. Adding a value when it is omitted will make it present.</p>", 
					LINE_WIDTH);
			building.blankComment();
		}
		building.wrapComment("@param " + lowerFirstLetter(makeSingular(property.name)) + " " +
				"Element to add to the set of " + camelToWords(property.name) + ".", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws NullPointerException Cannot add a null " + camelToWords(typeData.getJavaGetName()) +
				" to the set of " + camelToWords(property.name) + ".", LINE_WIDTH);
		if (typeData.getThrowsIllegal())
			building.wrapComment("@throws IllegalArgumentException The given value cannot be set as an element of the set of " +
					camelToWords(property.name) + ".", LINE_WIDTH);
		building.endComment();
		
		building.append("public void add" + makeSingular(property.name) + "(");
		building.append("         " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(makeSingular(property.name)) + ")");
		
		List<String> exceptional = new Vector<String>();
		exceptional.add("NullPointerException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");

		building.addThrowsClause(exceptional, true);
		
		building.startJavadoc();
		building.wrapComment("<p>Test to see if the given " + camelToWords(typeData.getJavaGetName()) +
				" is contained in this set of " + camelToWords(property.name) + ". Testing for containment in the " +
				lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@param " + lowerFirstLetter(makeSingular(property.name)) + " The " +
				camelToWords(typeData.getJavaGetName()) + " to test for.", LINE_WIDTH);
		building.wrapComment("@return Is the given " + camelToWords(typeData.getJavaGetName()) +
				" present in the set of " + camelToWords(property.name) + "?", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws NullPointerException Cannot check for containment using a <code>null</code> value.", 
				LINE_WIDTH);
		building.endComment();
		
		building.append("public boolean contains" + makeSingular(property.name) + "(");
		building.append("        " + typeData.getJavaGetName() + " " + lowerFirstLetter(makeSingular(property.name)) + ")");
		
		exceptional.clear();
		exceptional.add("NullPointerException");
		
		building.addThrowsClause(exceptional, true);

		building.startJavadoc();
		building.wrapComment("<p>Remove a value equal to the given " + camelToWords(typeData.getJavaGetName()) +
				" from the set of " + camelToWords(property.name) + ". Element will be removed from the " +
				lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		if (!property.isOptional) {
			building.wrapComment("<p>This property is required. If the last element is removed, you should add in a new element to satisfy the required constraint.</p>", LINE_WIDTH);
			building.blankComment();
		}
		building.wrapComment("@param " + lowerFirstLetter(makeSingular(property.name)) + " The " +
				camelToWords(typeData.getJavaGetName()) + " to remove from the set.", LINE_WIDTH);
		building.wrapComment("@return Has the given element been removed from the set of " + 
				camelToWords(property.name) + "?", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws NullPointerException Cannot remove a <code>null</code> value from a set.", LINE_WIDTH);
		building.endComment();
		
		building.append("public boolean remove" + makeSingular(property.name) + "(");
		building.append("        " + typeData.getJavaGetName() + " " + lowerFirstLetter(makeSingular(property.name)) + ")");

		exceptional.clear();
		exceptional.add("NullPointerException");
		
		building.addThrowsClause(exceptional, true);
		
		sharedInterfaceMethods(building, property, typeData, annotation);
	}
	
	private static void variableArrayInterfaceMethods(
			StringIndenter building,
			PropertyData property,
			TypeData typeData,
			String annotation) {

		// Append
		
		building.startJavadoc();
		building.wrapComment("<p>Append " + aOrAn(camelToWords(typeData.getJavaGetName())) + " to the list of " +
				camelToWords(property.name) +". The element is appended to the " + lowerFirstLetter(property.description) + "</p>", 
				LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@param " + lowerFirstLetter(makeSingular(property.name)) + " The " +
				camelToWords(typeData.getJavaGetName()) + " to append to the list of " + 
				camelToWords(property.name) +".", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws NullPointerException Cannot append a <code>null</code> to the list of " +
				camelToWords(property.name) +".", LINE_WIDTH);
		if (typeData.getThrowsIllegal()) 
			building.wrapComment("@throws IllegalArgumentException The given value is outside the acceptable range for this property.",
					LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see #prepend" + makeSingular(property.name) + "(" + typeData.getJavaGetName() + ")", LINE_WIDTH);
		building.wrapComment("@see #insert"+ makeSingular(property.name) + "(int, " + typeData.getJavaGetName() + ")", LINE_WIDTH);
		building.endComment();
		
		building.append("public void append" + makeSingular(property.name) + "(");
		building.append("        " + annotation + typeData.getJavaGetName() + " " + 
				lowerFirstLetter(makeSingular(property.name)) + ")");
		
		List<String> exceptional = new Vector<String>();
		exceptional.add("NullPointerException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");
		building.addThrowsClause(exceptional, true);
		
		// Prepend
		
		building.startJavadoc();
		building.wrapComment("<p>Prepend " + aOrAn(camelToWords(typeData.getJavaGetName())) + " to the list of " +
				camelToWords(property.name) +". The element is prepended to the " + lowerFirstLetter(property.description) + "</p>", 
				LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@param " + lowerFirstLetter(makeSingular(property.name)) + " The " +
				camelToWords(typeData.getJavaGetName()) + " to prepend to the list of " + 
				camelToWords(property.name) +".", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws NullPointerException Cannot prepend a <code>null</code> to the list of " +
				camelToWords(property.name) +".", LINE_WIDTH);
		if (typeData.getThrowsIllegal()) 
			building.wrapComment("@throws IllegalArgumentException The given value is outside the acceptable range for this property.",
					LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see #append" + makeSingular(property.name) + "(" + typeData.getJavaGetName() + ")", LINE_WIDTH);
		building.wrapComment("@see #insert"+ makeSingular(property.name) + "(int, " + typeData.getJavaGetName() + ")", LINE_WIDTH);
		building.endComment();
		
		building.append("public void prepend" + makeSingular(property.name) + "(");
		building.append("        " + annotation + typeData.getJavaGetName() + " " + 
				lowerFirstLetter(makeSingular(property.name)) + ")");
		
		exceptional.clear();
		exceptional.add("NullPointerException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");
		building.addThrowsClause(exceptional, true);
		
		// Insert
		
		building.startJavadoc();
		building.wrapComment("<p>Insert " + aOrAn(camelToWords(typeData.getJavaGetName())) + " into the list of " +
				camelToWords(property.name) + " at the given index. The element is prepended to the " + 
				lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@param index Index in the list of " + camelToWords(property.name) + 
				" where the element is to be inserted.", LINE_WIDTH);
		building.wrapComment("@param " + lowerFirstLetter(makeSingular(property.name)) + " The " +
				camelToWords(typeData.getJavaGetName()) + " to insert into the list of " + 
				camelToWords(property.name) +".", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws NullPointerException Cannot insert a <code>null</code> to the list of " +
				camelToWords(property.name) +".", LINE_WIDTH);
		building.wrapComment("@throws IndexOutOfBoundsException The given index is outside the acceptable range for the current list of " +
				camelToWords(property.name) + ".", LINE_WIDTH);
		if (typeData.getThrowsIllegal()) 
			building.wrapComment("@throws IllegalArgumentException The given value is outside the acceptable range for this property.",
					LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see #append" + makeSingular(property.name) + "(" + typeData.getJavaGetName() + ")", LINE_WIDTH);
		building.wrapComment("@see #prepend"+ makeSingular(property.name) + "(" + typeData.getJavaGetName() + ")", LINE_WIDTH);
		building.endComment();
		
		building.append("public void insert" + makeSingular(property.name) + "At(");
		building.append("        int index,");
		building.append("        " + annotation + typeData.getJavaGetName() + " " + 
				lowerFirstLetter(makeSingular(property.name)) + ")");
		
		exceptional.clear();
		exceptional.add("NullPointerException");
		exceptional.add("IndexOutOfBoundsException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");
		building.addThrowsClause(exceptional, true);
		
		// Contains
		
		building.startJavadoc();
		building.wrapComment("<p>Determines whether the given " + camelToWords(makeSingular(property.name)) + 
				" is contained in the list of " + camelToWords(property.name) + ". This is the list of " +
				lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@param " + lowerFirstLetter(makeSingular(property.name)) + " The " + 
				camelToWords(makeSingular(property.name)) + " to test for in the list of " +
				camelToWords(property.name) + ".", LINE_WIDTH);
		building.wrapComment("@return Is the given " + camelToWords(makeSingular(property.name)) + 
				" contained in the list of " + camelToWords(property.name) + "?" , LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws NullPointerException Cannot check for the containment of a <code>null</code> value.", 
				LINE_WIDTH);
		building.endComment();
		
		building.append("public boolean contains" + makeSingular(property.name) + "(");
		building.append("        " + annotation + typeData.getJavaGetName() + " " + 
				lowerFirstLetter(makeSingular(property.name)) + ")");
		
		exceptional.clear();
		exceptional.add("NullPointerException");
		building.addThrowsClause(exceptional, true);
		
		// GetAt
		
		building.startJavadoc();
		building.wrapComment("<p>Get the " + camelToWords(makeSingular(property.name)) + " at the given index into the list of " +
				camelToWords(property.name) + ". This is the " + lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@param index Index of the element to retrieve from the list of " + camelToWords(property.name) + 
				".", LINE_WIDTH);
		building.wrapComment("@return The " + camelToWords(makeSingular(property.name)) + " at the given index.", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws IndexOutOfBoundsException The given index is outside the acceptable range for the current list of " +
				camelToWords(property.name) + ".", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see #get" + property.name + "()", LINE_WIDTH);
		building.endComment();
		
		building.append("public " + annotation + typeData.getJavaGetName() + " get" + makeSingular(property.name) + "At(");
		building.append("        int index)");
		
		exceptional.clear();
		exceptional.add("IndexOutOfBoundsException");
		building.addThrowsClause(exceptional, true);
		
		// Remove
		
		building.startJavadoc();
		building.wrapComment("<p>Remove the " + camelToWords(makeSingular(property.name)) + " at the given index into the list of " +
				camelToWords(property.name) + ". This is the " + lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment(); 
		building.wrapComment("@param index Index of the element to remove from the list of " + camelToWords(property.name) +
				".", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws IndexOutOfBoundsException The given index is outside the acceptable range for the current list of " +
				camelToWords(property.name) + ".", LINE_WIDTH);
		building.endComment();
		
		building.append("public void remove" + makeSingular(property.name) + "At(");
		building.append("        int index)");
		
		exceptional.clear();
		exceptional.add("IndexOutOfBoundsException");
		building.addThrowsClause(exceptional, true);
		
		sharedInterfaceMethods(building, property, typeData, annotation);
	}
	
	private static void fixedArrayInterfaceMethods(
			StringIndenter building,
			PropertyData property,
			TypeData typeData,
			String annotation) {
		
		building.startJavadoc();
		building.wrapComment("<p>Sets the " + lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		if (property.isOptional) {
			building.wrapComment("<p>Set this optional property to <code>null</code> to omit it.</p>", LINE_WIDTH);
			building.blankComment();
		}
		building.wrapComment("@param " + lowerFirstLetter(property.name) + " " + firstSentence(property.description), LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws NullPointerException Cannot set the required " + camelToWords(property.name) + 
				" with a <code>null</code> value or one of the elements of the given array is <code>null</code>.", LINE_WIDTH);
		if (typeData.getThrowsIllegal()) {
			if (property.isOptional) building.blankComment();
			building.wrapComment("@throws IllegalArgumentException Cannot set the " + camelToWords(property.name) + 
					" property with the given value.", LINE_WIDTH);
		}
		building.endComment();

		building.append("public void set" + property.name + "(");
		building.append("        " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(property.name) + "[])");

		List<String> exceptional = new Vector<String>();
		exceptional.add("NullPointerException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");
		
		building.addThrowsClause(exceptional, true);
	}
	
	private static void sharedInterfaceMethods(	
			StringIndenter building,
			PropertyData property,
			TypeData typeData,
			String annotation) {
	
		building.startJavadoc();
		building.wrapComment("<p>Clears the set of " + camelToWords(property.name) + ", which is a " + 
				lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		if (!property.isOptional) 
			building.wrapComment("<p>This is a required property. Elements should be added back in directly after calling this method to satisfy the required constraint.</p>", LINE_WIDTH);
		else
			building.wrapComment("<p>Calling this method will omit this optional property.</p>", LINE_WIDTH);
		building.endComment();
		building.appendNL("public void clear" + property.name + "();");
		
		building.startJavadoc();
		building.wrapComment("<p>Counts the number of elements in the set of " + camelToWords(property.name) + ". This set is a " +
				lowerFirstLetter(property.description) + "</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@return Number of elements in the set of " + camelToWords(property.name) + ".", LINE_WIDTH);
		building.endComment();
		building.appendNL("public int count" + property.name + "();");
	}
	
	static String generateImplementation(
			String basePackageName,
			ClassData classData) {
		
		StringIndenter building = new StringIndenter();
		building.setPackageName(basePackageName + ".impl");
		
		building.startJavadoc();
		building.wrapComment("<p>" + classData.description + " Implementation.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@author Auto generation.", LINE_WIDTH);
		building.endComment();
		
		building.addImport("tv.amwa.maj.industry.MediaClass");
		building.addImport(basePackageName + ".Constants");
		building.append("@MediaClass(");
		building.increment("definedName = \"" + classData.name + "\",");
		building.append("uuid1 = 0x" + padHexTo8(classData.identification.getData1()) + 
				", uuid2 = (short) 0x" + padHexTo4(classData.identification.getData2()) + 
				", uuid3 = (short) 0x" + padHexTo4(classData.identification.getData3()) + ",");
		building.append("uuid4 = { " + bytesToText(classData.identification.getData4()) + " },");
		if (classData.description != null) 
			building.append("description = \"" + classData.description.replace("\"", "\\\"") + "\",");
		else
			building.append("description = \"\",");			
		building.append("namespace = Constants.XML_NAMESPACE,");
		building.append("prefix = Constants.XML_PREFIX,");
		building.append("symbol = \"" + classData.symbol + "\")");
		
		building.reset("public " + (classData.isConcrete ?  "" : "abstract ") + "class " + classData.name + "Impl");
		if ((classData.parentClass != null) && (classData.parentClass.length() > 0)) {
			building.append("    extends");
			
			if (classList.containsKey(classData.parentClass)) 
				building.append("        " + classData.parentClass + "Impl");
			else {
				ClassDefinition widerClass = Warehouse.lookForClass(classData.parentClass);
				building.append("        " + classData.parentClass + "Impl");
				building.addImport(widerClass.getJavaImplementation().getCanonicalName());
			}
		}
		
		building.addImport(basePackageName + "." + classData.name);
		building.addImport("tv.amwa.maj.io.xml.XMLSerializable");
		building.addImport("tv.amwa.maj.constant.CommonConstants");
		building.addImport("java.io.Serializable");
		
		building.increment("implements");
		building.append("    " + classData.name + ",");
		building.append("    CommonConstants,");
		building.append("    Serializable,");
		building.append("    XMLSerializable,");
		if (weakReferenceTargets.containsKey(classData.name)) {
			building.addImport("tv.amwa.maj.industry.WeakReferenceTarget");
			building.append("    WeakReferenceTarget,");
		}
		building.appendNL("    Cloneable {");
		
		java.security.MessageDigest messageDigest;
		try {
			messageDigest = java.security.MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new InternalError("Unable to find MD5 checksum algorithm.");
		}
		
		messageDigest.update(classData.identification.getAUIDValue());
		for ( PropertyData property : propertyList.values() )
			if (property.memberOf.equals(classData.name))
				messageDigest.update(property.identification.getAUIDValue());
		
		byte[] uidBytes = messageDigest.digest();
		long uid = ((long) uidBytes[0]) << 56 ^ ((long) uidBytes[1]) << 48 ^ ((long) uidBytes[2]) << 40 ^ ((long) uidBytes[3]) << 32 ^
				((long) uidBytes[4]) << 24 ^ ((long) uidBytes[5]) << 16 ^ ((long) uidBytes[6]) << 8 ^ ((long) uidBytes[7]); 
		
		building.appendNL("private static final long serialVersionUID = " + uid + "l;");
		
		for ( PropertyData property : propertyList.values() ) {
			if (property.memberOf.equals(classData.name)) {
				
				TypeData typeData = typeByName(property.type);
				building.addImport(typeData.getImports());
				
				String annotation = typeData.getAnnotation();
				if (annotation == null) 
					annotation = "";
				else
					annotation = "@" + annotation + " ";

				switch (typeData.getTypeCategory()) {
				
				case WeakObjRef:
					building.append("private WeakReference<" + typeData.getJavaGetName() + "> " +
							lowerFirstLetter(property.name) + ";");
					
					building.addImport("tv.amwa.maj.industry.WeakReference");
					
					String weakRefType = ((TypeDataWeakObjectReference) typeData).referencedType;
					if (classList.containsKey(weakRefType))
						building.addImport(basePackageName + "." + weakRefType);
					else
						building.addImport(typeData.getImports());
					break;
					
				case Set:
					TypeDataSet setType = (TypeDataSet) typeData;
					TypeData setElementType = typeByName(setType.elementType);
					
					building.addImport("java.util.Set");
					
					switch (setElementType.getTypeCategory()) {
				
					case WeakObjRef:
						building.append("private WeakReferenceSet<" + typeData.getJavaGetName() + "> " +
								lowerFirstLetter(property.name) + " =");
						building.append("        new WeakReferenceSet<" + typeData.getJavaGetName() + ">();");
						building.addImport("tv.amwa.maj.industry.WeakReferenceSet");
						
						if (classList.containsKey(((TypeDataWeakObjectReference) setElementType).referencedType))
							building.addImport(basePackageName + "." + typeData.getJavaGetName());
						else
							building.addImport(setElementType.getImports());
						break; 
					default:
						building.append("private Set<" + typeData.getJavaSetName(true) + "> " +
								lowerFirstLetter(property.name) + " =");
						building.append("        Collections.synchronizedSet(new HashSet<" + typeData.getJavaSetName(true) + ">());");
						building.addImport("tv.amwa.maj.industry.StrongReferenceSet");
						building.addImport("java.util.Collections");
						building.addImport("java.util.HashSet");
						
						if ((setElementType instanceof TypeDataStrongObjectReference) 
								&& (classList.containsKey(((TypeDataStrongObjectReference) setElementType).referencedType)))
							building.addImport(basePackageName + "." + typeData.getJavaSetName(true));
						else
							building.addImport(setElementType.getImports());

						break;
					}
					break;
					
				case VariableArray:
					TypeDataVariableArray varialbeArrayType = (TypeDataVariableArray) typeData;
					TypeData arrayElementType = typeByName(varialbeArrayType.elementType);

					switch (arrayElementType.getTypeCategory()) {
					
					case WeakObjRef:
						building.append("private WeakReferenceVector<" + typeData.getJavaGetName() + "> " +
								lowerFirstLetter(property.name) + " =");
						building.append("        new WeakReferenceVector<" + typeData.getJavaGetName() + ">();");
						building.addImport("tv.amwa.maj.industry.WeakReferenceVector");
						
						if (classList.containsKey(((TypeDataWeakObjectReference) arrayElementType).referencedType))
							building.addImport(basePackageName + "." + typeData.getJavaGetName());
						else
							building.addImport(arrayElementType.getImports());
						break; 
						
					default:
						building.append("private List<" + typeData.getJavaSetName(true) + "> " +
								lowerFirstLetter(property.name) + " =");
						building.append("        Collections.synchronizedList(new Vector<" + typeData.getJavaSetName(true) + ">());");
						building.addImport("tv.amwa.maj.industry.StrongReferenceVector");
						building.addImport("java.util.Collections");
						building.addImport("java.util.List");
						building.addImport("java.util.Vector");

						if ((arrayElementType instanceof TypeDataStrongObjectReference)
								&& (classList.containsKey(((TypeDataStrongObjectReference) arrayElementType).referencedType)))
							building.addImport(basePackageName + "." + typeData.getJavaSetName(true));
						else
							building.addImport(arrayElementType.getImports());

						break;
					}
					break;
				
				case StrongObjRef:
					building.append("private " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
							lowerFirstLetter(property.name) + ";");

					String strongRefType = ((TypeDataStrongObjectReference) typeData).referencedType;
					if (classList.containsKey(strongRefType))
						building.addImport(basePackageName + "." + strongRefType);
					else
						building.addImport(typeData.getImports());
					break;
					
				case Record:
				case Enum:
					if (typeList.containsKey(typeData.name))
						building.addImport(basePackageName + "." + typeData.name);
					// No break ... continuing on to add the field
					
				default:
					building.append("private " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
							lowerFirstLetter(property.name) + ";");
					break;
				}
			}
		}
		
		building.append("");
		
		building.appendNL("public " + classData.name + "Impl() { }");
		
		for ( PropertyData property : propertyList.values() ) {
			
			if (property.memberOf.equals(classData.name)) {
				addImplementationGetter(building, property);
				addImplementationSetter(building, property);
			}
		}
		
		if (weakReferenceTargets.containsKey(classData.name)) {
			
			building.appendNL("public AUID getAUID() {");
			
			building.increment("return get" + weakReferenceTargets.get(classData.name) + "();");
			building.decrementNL("}");
			
			building.appendNL("public String getWeakTargetReference() {");
			
			building.increment("return getAUID().toString();");
			building.decrementNL("}");			
		}
		
		if ((classData.parentClass == null) || (classData.parentClass.length() == 0))
			addRootClassMethods(building, classData);
		else {
			building.appendNL("public " + classData.name + " clone() {");
			building.increment("return (" + classData.name + ") super.clone();");
			building.decrementNL("}");
		}
		building.reset("}");
		return building.toString();
	}
	
	static void addImplementationGetter(
			StringIndenter building,
			PropertyData property) {
		
		TypeData typeData = typeByName(property.type);
		building.addImport(typeData.getImports());
		
		String annotation = typeData.getAnnotation();
		if (annotation == null) 
			annotation = "";
		else
			annotation = "@" + annotation + " ";
		
		building.addImport("tv.amwa.maj.industry.MediaProperty");
		building.append("@MediaProperty(");
		building.increment("definedName = \"" + property.name + "\",");
		building.append("uuid1 = 0x" + padHexTo8(property.identification.getData1()) + 
				", uuid2 = (short) 0x" + padHexTo4(property.identification.getData2()) + 
				", uuid3 = (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
		building.append("uuid4 = { " + bytesToText(property.identification.getData4()) + " },");
		building.append("typeName = \"" + property.type + "\",");
		building.append("optional = " + property.isOptional + ",");
		building.append("uniqueIdentifier = " + property.isUniqueIdentifier + ",");
		building.append("pid = 0x" + padHexTo4(property.localIdentification) + ",");
		building.append("description = \"" + property.description.replace("\"", "\\\"") + "\",");
		building.append("symbol = \"" + property.symbol + "\")");
		
		List<String> exceptional = new Vector<String>();
		
		switch(typeData.getTypeCategory()) {
		
		case VariableArray:
			building.decrement("public List<" + typeData.getJavaGetName() + "> get" + 
					property.name + "()");
			
			exceptional.clear();
			if (property.isOptional) {
				exceptional.add("PropertyNotPresentException");
				building.addImport("tv.amwa.maj.exception.PropertyNotPresentException");
			}
			building.addThrowsClause(exceptional, false);

			if (property.isOptional) {
				building.append("if (count" + property.name + "() == 0)");
				building.appendNL("    throw new PropertyNotPresentException(\"No " + camelToWords(makeSingular(property.name)) +
						" elements are present for the list of " + camelToWords(property.name) + ".\");");
				if (typeByName(((TypeDataVariableArray) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
					building.append("return " + lowerFirstLetter(property.name) + ".getOptionalList();");
				else {
					building.addImport("tv.amwa.maj.industry.StrongReferenceVector");
					building.append("return StrongReferenceVector.getOptionalList(" + lowerFirstLetter(property.name) + ");");
				}
			}
			else
				if (typeByName(((TypeDataVariableArray) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
					building.append("return " + lowerFirstLetter(property.name) + ".getRequiredList();");
				else {
					building.addImport("tv.amwa.maj.industry.StrongReferenceVector");
					building.append("return StrongReferenceVector.getRequiredList(" + lowerFirstLetter(property.name) + ");");
				}			
			break;
		
		case Set:
			building.decrement("public Set<" + typeData.getJavaGetName() + "> get" + 
					property.name + "()");
			
			exceptional.clear();
			if (property.isOptional) {
				exceptional.add("PropertyNotPresentException");
				building.addImport("tv.amwa.maj.exception.PropertyNotPresentException");
			}
			building.addThrowsClause(exceptional, false);

			if (property.isOptional) {
				building.append("if (count" + property.name + "() == 0)");
				building.appendNL("    throw new PropertyNotPresentException(\"No " + camelToWords(makeSingular(property.name)) +
						" elements are present for the set of " + camelToWords(property.name) + ".\");");
				if (typeByName(((TypeDataSet) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
					building.append("return " + lowerFirstLetter(property.name) + ".getOptionalSet();");
				else {
					building.addImport("tv.amwa.maj.industry.StrongReferenceSet");
					building.append("return StrongReferenceSet.getOptionalSet(" + lowerFirstLetter(property.name) + ");");
				}
			}
			else {
				if (typeByName(((TypeDataSet) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
					building.append("return " + lowerFirstLetter(property.name) + ".getRequiredSet();");
				else {
					building.addImport("tv.amwa.maj.industry.StrongReferenceSet");
					building.append("return StrongReferenceSet.getRequiredSet(" + lowerFirstLetter(property.name) + ");");
				}
			}
			break;
			
			// TODO do something better with extendible enumerations. Check the IDs and add name methods.
			
		default:
			building.decrement("public " + annotation + typeData.getJavaGetName() + " get" + 
					property.name + "()");
			
			exceptional.clear();
			if (property.isOptional) {
				exceptional.add("PropertyNotPresentException");
				building.addImport("tv.amwa.maj.exception.PropertyNotPresentException");
			}
			building.addThrowsClause(exceptional, false);
			
			if (property.isOptional) {
				building.append("if (" + lowerFirstLetter(property.name) + " == null)");
				building.appendNL("    throw new PropertyNotPresentException(\"The optional " + camelToWords(property.name) +
						" property is not present for this " + camelToWords(property.memberOf) + ".\");");
			}
			
			if ((typeData.getTypeCategory() == TypeCategory.StrongObjRef) ||
					(typeData.getTypeCategory() == TypeCategory.Record))
				building.append("return " + lowerFirstLetter(property.name) + ";");
			else if (typeData.getTypeCategory() == TypeCategory.WeakObjRef)
				building.append("return " + lowerFirstLetter(property.name) + ".getTarget();");
			else
				building.append("return " + lowerFirstLetter(property.name) + ";");
			
			break;
		}
		
		building.decrementNL("}");
	}
	
	static void addImplementationSetter(
			StringIndenter building,
			PropertyData property) { 
		
		TypeData typeData = typeByName(property.type);
		building.addImport(typeData.getImports());
		
		String annotation = typeData.getAnnotation();
		if (annotation == null) 
			annotation = "";
		else
			annotation = "@" + annotation + " ";
		
		switch (typeData.getTypeCategory()) {
		
		case Set:
			setTypeImplementationMethods(building, property, typeData, annotation);
			break;
			
		case VariableArray:
			variableArrayImplementationMethods(building, property, typeData, annotation);
			break;
			
		default:
			defaultImplementationSetter(building, property, typeData, annotation);
			break;			
			
		}

	}
	
	static void defaultImplementationSetter(
			StringIndenter building,
			PropertyData property,
			TypeData typeData,
			String annotation) {
		
		building.addImport("tv.amwa.maj.industry.MediaPropertySetter");
		
		building.append("@MediaPropertySetter(\"" + property.name + "\")");
		building.append("public void set" + property.name + "(");
		building.append("        " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(property.name) + ")");
		
		List<String> exceptional = new Vector<String>();
		if ((!property.isOptional) && (isNullableType(typeData))) 
			exceptional.add("NullPointerException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");
		building.addThrowsClause(exceptional, false);
		
		if (property.isOptional) {
			building.append("if (" + lowerFirstLetter(property.name) + " == null) {");
			building.increment("this." + lowerFirstLetter(property.name) + " = null;");
			building.append("return;");
			building.decrementNL("}");
		}
		else {
			if (isNullableType(typeData)) {
			
				building.append("if (" + lowerFirstLetter(property.name) + " == null)");
				building.appendNL("    throw new NullPointerException(\"Cannot set the required " +
						camelToWords(property.name) + " with a null value.\");");
			}
		}
		
		if ((typeData.getTypeCategory() == TypeCategory.StrongObjRef) ||
				(typeData.getTypeCategory() == TypeCategory.Record))
			building.append("this." + lowerFirstLetter(property.name) + " = " + 
					lowerFirstLetter(property.name) + ";");
		else if (typeData.getTypeCategory() == TypeCategory.WeakObjRef)
			building.append("this." + lowerFirstLetter(property.name) + " = new WeakReference<" +
					typeData.getJavaGetName() + ">(" + lowerFirstLetter(property.name) + ");");
		else
			building.append("this." + lowerFirstLetter(property.name) + " = " +
					lowerFirstLetter(property.name) + ";");
		
		building.decrementNL("}");
	}
	
	private static void setTypeImplementationMethods(
			StringIndenter building,
			PropertyData property,
			TypeData typeData,
			String annotation) {
		
		List<String> exceptional = new Vector<String>();
		
		// Add
		
		building.addImport("tv.amwa.maj.industry.MediaSetAdd");
		
		building.append("@MediaSetAdd(\"" + property.name + "\")");
		building.append("public void add" + makeSingular(property.name) + "(");
		building.append("         " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(makeSingular(property.name)) + ")");

		exceptional.clear();
		exceptional.add("NullPointerException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");
		building.addThrowsClause(exceptional, false);
		
		building.append("if (" + lowerFirstLetter(makeSingular(property.name)) + " == null)");
		building.appendNL("    throw new NullPointerException(\"Cannot add a null value to the set of " + 
				camelToWords(property.name) + ".\");");
		
		if (typeByName(((TypeDataSet) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
			building.append("this." + lowerFirstLetter(property.name) + ".add(" + 
					lowerFirstLetter(makeSingular(property.name)) + ");");
		else
			building.append("StrongReferenceSet.add(this." + lowerFirstLetter(property.name) + ", " +
					lowerFirstLetter(makeSingular(property.name)) + ");");
		
		building.decrementNL("}");
		
		// Contains
		
		building.addImport("tv.amwa.maj.industry.MediaPropertyContains");
		
		building.append("@MediaPropertyContains(\"" + property.name + "\")");
		building.append("public boolean contains" + makeSingular(property.name) + "(");
		building.append("         " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(makeSingular(property.name)) + ")");

		exceptional.clear();
		exceptional.add("NullPointerException");
		building.addThrowsClause(exceptional, false);
		
		building.append("if (" + lowerFirstLetter(makeSingular(property.name)) + " == null)");
		building.appendNL("    throw new NullPointerException(\"Cannot check for containment in the set of " + 
				camelToWords(property.name) + " with a null value.\");");

		if (typeByName(((TypeDataSet) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
			building.append("return this." + lowerFirstLetter(property.name) + ".contains(" + 
					lowerFirstLetter(makeSingular(property.name)) + ");");
		else
			building.append("return StrongReferenceSet.contains(this." + lowerFirstLetter(property.name) + ", " +
					lowerFirstLetter(makeSingular(property.name)) + ");");
		
		building.decrementNL("}");

		// Remove
		
		building.addImport("tv.amwa.maj.industry.MediaPropertyRemove");
		
		building.append("@MediaPropertyRemove(\"" + property.name + "\")");
		building.append("public boolean remove" + makeSingular(property.name) + "(");
		building.append("         " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(makeSingular(property.name)) + ")");

		exceptional.clear();
		exceptional.add("NullPointerException");
		building.addThrowsClause(exceptional, false);
		
		building.append("if (" + lowerFirstLetter(makeSingular(property.name)) + " == null)");
		building.appendNL("    throw new NullPointerException(\"Cannot remove from the set of " + 
				camelToWords(property.name) + " using a null value.\");");
		if (typeByName(((TypeDataSet) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
			building.append("return this." + lowerFirstLetter(property.name) + ".remove(" + 
					lowerFirstLetter(makeSingular(property.name)) + ");");
		else
			building.append("return StrongReferenceSet.remove(this." + lowerFirstLetter(property.name) + ", " +
					lowerFirstLetter(makeSingular(property.name)) + ");");
		
		building.decrementNL("}");
		
		sharedImplementationMethods(building, property, typeData, annotation);
	}
	
	private static void variableArrayImplementationMethods(
			StringIndenter building,
			PropertyData property,
			TypeData typeData,
			String annotation) {
		
		List<String> exceptional = new Vector<String>();
		
		// Append
		
		building.addImport("tv.amwa.maj.industry.MediaListAppend");
		
		building.append("@MediaListAppend(\"" + property.name + "\")");
		building.append("public void append" + makeSingular(property.name) + "(");
		building.append("         " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(makeSingular(property.name)) + ")");

		exceptional.clear();
		exceptional.add("NullPointerException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");
		building.addThrowsClause(exceptional, false);
		
		building.append("if (" + lowerFirstLetter(makeSingular(property.name)) + " == null)");
		building.appendNL("    throw new NullPointerException(\"Cannot append a null value to the list of " + 
				camelToWords(property.name) + ".\");");
		// TODO throws illegal
		if (typeByName(((TypeDataVariableArray) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
			building.append("this." + lowerFirstLetter(property.name) + ".append(" + 
					lowerFirstLetter(makeSingular(property.name)) + ");");
		else
			building.append("StrongReferenceVector.append(this." + lowerFirstLetter(property.name) + ", " +
					lowerFirstLetter(makeSingular(property.name)) + ");");
		
		building.decrementNL("}");
		
		// Prepend
		
		building.addImport("tv.amwa.maj.industry.MediaListPrepend");
		
		building.append("@MediaListPrepend(\"" + property.name + "\")");
		building.append("public void prepend" + makeSingular(property.name) + "(");
		building.append("        " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(makeSingular(property.name)) + ")");
		
		exceptional.clear();
		exceptional.add("NullPointerException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");
		building.addThrowsClause(exceptional, false);
		
		building.append("if (" + lowerFirstLetter(makeSingular(property.name)) + " == null)");
		building.appendNL("    throw new NullPointerException(\"Cannot prepend a null value to the list of " +
				camelToWords(property.name) + ".\");");
		// TODO throws illegal
		if (typeByName(((TypeDataVariableArray) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
			building.append("this." + lowerFirstLetter(property.name) + ".prepend(" + 
					lowerFirstLetter(makeSingular(property.name)) + ");");
		else
			building.append("StrongReferenceVector.prepend(this." + lowerFirstLetter(property.name) + ", " +
					lowerFirstLetter(makeSingular(property.name)) + ");");
		
		building.decrementNL("}");
		
		// InsertAt
		
		building.addImport("tv.amwa.maj.industry.MediaListInsertAt");
		
		building.append("@MediaListInsertAt(\"" + property.name + "\")");
		building.append("public void insert" + makeSingular(property.name) + "At(");
		building.append("        int index,");
		building.append("        " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(makeSingular(property.name)) + ")");
		
		exceptional.clear();
		exceptional.add("NullPointerException");
		exceptional.add("IndexOutOfBoundsException");
		if (typeData.getThrowsIllegal()) exceptional.add("IllegalArgumentException");
		building.addThrowsClause(exceptional, false);
		
		building.append("if (" + lowerFirstLetter(makeSingular(property.name)) + " == null)");
		building.appendNL("    throw new NullPointerException(\"Cannot insert a null value into the list of " +
				camelToWords(property.name) + ".\");");
		// TODO throws illegal
		
		// Index out of bounds pushed into generic methods
		
		if (typeByName(((TypeDataVariableArray) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
			building.append("this." + lowerFirstLetter(property.name) + ".insert(index, " + 
					lowerFirstLetter(makeSingular(property.name)) + ");");
		else
			building.append("StrongReferenceVector.insert(this." + lowerFirstLetter(property.name) + ", index, " +
					lowerFirstLetter(makeSingular(property.name)) + ");");
		
		building.decrementNL("}");
		
		// GetAt
		
		building.addImport("tv.amwa.maj.industry.MediaListGetAt");
		
		building.append("@MediaListGetAt(\"" + property.name + "\")");
		building.append("public " + annotation + typeData.getJavaSetName(property.isOptional) + " get" + 
				makeSingular(property.name) + "At(");
		building.append("        int index)");

		exceptional.clear();
		exceptional.add("IndexOutOfBoundsException");
		building.addThrowsClause(exceptional, false);

		// Index out of bounds pushed into generic methods
		
		if (typeByName(((TypeDataVariableArray) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
			building.append("return this." + lowerFirstLetter(property.name) + ".getAt(index)");
		else
			building.append("return StrongReferenceVector.getAt(this." + lowerFirstLetter(property.name) + ", index);");
		
		building.decrementNL("}");
		
		// RemoveAt
		
		building.addImport("tv.amwa.maj.industry.MediaListRemoveAt");
	
		building.append("@MediaListRemoveAt(\"" + property.name + "\")");
		building.append("public void remove" + makeSingular(property.name) + "At(");
		building.append("        int index)");
		
		exceptional.clear();
		exceptional.add("IndexOutOfBoundsException");
		building.addThrowsClause(exceptional, false);

		// Index out of bounds pushed into generic methods
		
		if (typeByName(((TypeDataVariableArray) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
			building.append("this." + lowerFirstLetter(property.name) + ".removeAt(index)");
		else
			building.append("StrongReferenceVector.remove(this." + lowerFirstLetter(property.name) + ", index);");
		
		building.decrementNL("}");

		// Remove item
		
		building.addImport("tv.amwa.maj.industry.MediaPropertyRemove");
		
		building.append("@MediaPropertyRemove(\"" + property.name + "\")");
		building.append("public boolean remove" + makeSingular(property.name) + "(");
		building.append("        " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(makeSingular(property.name)) + ")");
		
		exceptional.clear();
		exceptional.add("NullPointerException");
		building.addThrowsClause(exceptional, false);
		
		building.append("if (" + lowerFirstLetter(makeSingular(property.name)) + " == null)");
		building.appendNL("    throw new NullPointerException(\"Cannot remove a null value from the list of " +
				camelToWords(property.name) + ".\");");
		
		if (typeByName(((TypeDataVariableArray) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
			building.append("return this." + lowerFirstLetter(property.name) + ".remove(" +
					lowerFirstLetter(makeSingular(property.name)) + ");");
		else
			building.append("return StrongReferenceVector.remove(this." + lowerFirstLetter(property.name) + ", " +
					lowerFirstLetter(makeSingular(property.name)) + ");");
		
		building.decrementNL("}");
		
		// Contains
		
		building.addImport("tv.amwa.maj.industry.MediaPropertyContains");
		
		building.append("@MediaPropertyContains(\"" + property.name + "\")");
		building.append("public boolean contains" + makeSingular(property.name) + "(");
		building.append("        " + annotation + typeData.getJavaSetName(property.isOptional) + " " +
				lowerFirstLetter(makeSingular(property.name)) + ")");
		
		exceptional.clear();
		exceptional.add("NullPointerException");
		building.addThrowsClause(exceptional, false);
		
		building.append("if (" + lowerFirstLetter(makeSingular(property.name)) + " == null)");
		building.appendNL("    throw new NullPointerException(\"Cannot test for containment in the list of " +
				camelToWords(property.name) + " using a null value.\");");
		
		if (typeByName(((TypeDataVariableArray) typeData).elementType).getTypeCategory() == TypeCategory.WeakObjRef) 
			building.append("return this." + lowerFirstLetter(property.name) + ".contains(" +
					lowerFirstLetter(makeSingular(property.name)) + ");");
		else
			building.append("return StrongReferenceVector.contains(this." + lowerFirstLetter(property.name) + ", " +
					lowerFirstLetter(makeSingular(property.name)) + ");");
		
		building.decrementNL("}");
		
		sharedImplementationMethods(building, property, typeData, annotation);
	}

	private static void sharedImplementationMethods(
			StringIndenter building,
			PropertyData property,
			TypeData typeData,
			String annotation) {
	
		building.addImport("tv.amwa.maj.industry.MediaPropertyClear");
		
		building.append("@MediaPropertyClear(\"" + property.name + "\")");
		building.appendNL("public void clear" + property.name + "() {");
		building.increment(lowerFirstLetter(property.name) + ".clear();");
		building.decrementNL("}");
		
		building.addImport("tv.amwa.maj.industry.MediaPropertyCount");
		
		building.append("@MediaPropertyCount(\"" + property.name + "\")");
		building.appendNL("public int count" + property.name + "() {");
		
		TypeCategory targetCategory = null;
		if (typeData instanceof TypeDataSet)
			targetCategory = typeByName(((TypeDataSet) typeData).elementType).getTypeCategory();
		else
			targetCategory = typeByName(((TypeDataVariableArray) typeData).elementType).getTypeCategory();
		
		if (targetCategory == TypeCategory.WeakObjRef)
			building.increment("return " + lowerFirstLetter(property.name) + ".count();");
		else
			building.increment("return " + lowerFirstLetter(property.name) + ".size();");
		
		building.decrementNL("}");
	}
	
	private static void addRootClassMethods(
			StringIndenter building,
			ClassData classData) {
		
		// Override java.lang.Object
		
		building.addImport("tv.amwa.maj.industry.MediaEngine");
		
		building.append("@Override");
		building.append("public boolean equals(");
		building.incrementNL("    Object o) {");
		building.append("return MediaEngine.equals(this, o);");
		building.decrementNL("}");
		
		building.append("@Override");
		building.appendNL("public int hashCode() {");
		building.increment("return MediaEngine.hashCode(this);");
		building.decrementNL("}");
		
		building.append("@Override");
		building.appendNL("public String toString() {");
		building.increment("return MediaEngine.toString(this);");
		building.decrementNL("}");
		
		building.appendNL("public " + classData.name + " clone() {");
		building.increment("try {");
		building.increment("return (" + classData.name + ") super.clone();");
		building.decrement("}");
		building.append("catch (CloneNotSupportedException cnse) {");
		building.increment("// Should never happen as all classes implement Cloneable");
		building.append("throw new InternalError(cnse.getMessage());");
		building.decrement("}");
		building.decrementNL("}");
		
		building.append("public void appendXMLChildren(");
		building.appendNL("        org.w3c.dom.Node node) { }");
		
		building.appendNL("public String getComment() {");
		building.increment("return null;");
		building.decrementNL("}");
		
		building.append("private long persistentID = 0l;");
		building.append("@SuppressWarnings(\"unused\")");
		building.appendNL("private int persistentIndex = 0;");
		
		building.appendNL("public long getPersistentID() {");
		building.increment("return persistentID;");
		building.decrementNL("}");
		
		building.append("public void setPersistentIndex(");
		building.incrementNL("    int index) {");
		building.append("this.persistentIndex = index;");
		building.decrementNL("}");
	}
	
	static final String generateTypeDefinitions(
			String basePackageName) {
		
		StringIndenter building = new StringIndenter();
		building.setPackageName(basePackageName);

		building.addImport("tv.amwa.maj.industry.Forge");
		
		building.startJavadoc();
		building.wrapComment("<p>Extension type definitions defined for this package.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see tv.amwa.maj.meta.TypeDefinition", LINE_WIDTH);
		building.wrapComment("@see tv.amwa.maj.industry.Warehouse", LINE_WIDTH);
		building.wrapComment("@see tv.amwa.maj.industry.TypeDefinitions", LINE_WIDTH);
		building.endComment();
		
		building.append("public interface TypeDefinitions {");
		building.increment("");
		
		Set<AUID> alreadyDone = new HashSet<AUID>();
		
		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataCharacter) appendAndCheck(typeData, building, alreadyDone);
		
		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataInteger) appendAndCheck(typeData, building, alreadyDone);
		
		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataEnumeration) appendAndCheck(typeData, building, alreadyDone);

		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataExtendibleEnumeration) appendAndCheck(typeData, building, alreadyDone);
		
		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataIndirect) appendAndCheck(typeData, building, alreadyDone);

		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataOpaque) appendAndCheck(typeData, building, alreadyDone);

		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataStream) appendAndCheck(typeData, building, alreadyDone);
		
		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataString) appendAndCheck(typeData, building, alreadyDone);

		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataRename) appendAndCheck(typeData, building, alreadyDone);

		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataWeakObjectReference) appendAndCheck(typeData, building, alreadyDone);

		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataStrongObjectReference) appendAndCheck(typeData, building, alreadyDone);

		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataFixedArray) appendAndCheck(typeData, building, alreadyDone);
		
		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataRecord) appendAndCheck(typeData, building, alreadyDone);

		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataSet) appendAndCheck(typeData, building, alreadyDone);
		
		for ( TypeData typeData : typeList.values() )
			if (typeData instanceof TypeDataVariableArray) appendAndCheck(typeData, building, alreadyDone);

		building.reset("}");
		
		return building.toString();
	}
	
	static final void appendAndCheck(
			TypeData typeData,
			StringIndenter building,
			Set<AUID> alreadyWritten) {
		
		if (alreadyWritten.contains(typeData.identification)) return;
		typeData.appendTypeDescription(building);
		alreadyWritten.add(typeData.identification);
	}
	
	static final String generateEnumeration(
			String basePackageName,
			TypeDataEnumeration enumeration) {
		
		StringIndenter building = new StringIndenter();
		building.setPackageName(basePackageName);
		
		building.addImport("tv.amwa.maj.industry.MediaEnumerationValue");
		building.addImport("tv.amwa.maj.integer.Int64");
		
		building.startJavadoc();
		if ((enumeration.description != null) && (enumeration.description.length() > 0))
			building.wrapComment("<p>" + enumeration.description + "</p>", LINE_WIDTH);
		else
			building.wrapComment("<p>" + upperFirstLetter(camelToWords(enumeration.name)) + " enumeration.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see TypeDefinitions#" + enumeration.name, LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@author Auto generation", LINE_WIDTH);
		building.endComment();
		
		building.append("public enum " + enumeration.name);
		building.incrementNL("implements MediaEnumerationValue {");
		
		for ( int x = 0 ; x < enumeration.names.size() ; x++ ) {
			building.append("/** <p>" + upperFirstLetter(camelToWords(enumeration.names.get(x))) + " element.</p> */");
			building.append(enumeration.names.get(x) + " (" + enumeration.values.get(x) + "l),"); 
		}
		
		building.appendNL(";");
		
		building.appendNL("private final long value;");
		
		building.appendNL(enumeration.name + "(long value) { this.value = value; }");
		
		building.appendNL("@Int64 public long value() { return value; }");
		
		building.appendNL("public String symbol() {");
		building.increment("return \"" + enumeration.name + "_\" + name();");
		
		building.decrementNL("}");
		
		building.reset("}");
		
		return building.toString();
	}
	
	static final String generateExtendibleEnumeration(
			String basePackageName,
			String extendibleEnumerationName,
			String extendibleEnumerationSymbol) {
		
		StringIndenter building = new StringIndenter();
		building.setPackageName(basePackageName);

		building.addImport("tv.amwa.maj.industry.ExtendibleEnumerationItem");
		building.addImport("tv.amwa.maj.record.AUID");
		building.addImport("tv.amwa.maj.industry.Forge");
		
		building.startJavadoc();
		building.wrapComment("<p>Constants for extendible enumeration " + camelToWords(extendibleEnumerationName) + ".</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see TypeDefinitions#" + extendibleEnumerationName, LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@author Auto generation", LINE_WIDTH);
		building.endComment();
		
		building.append("public interface " + extendibleEnumerationName + " {");
		building.increment("");
		
		SortedMap<String, AUID> enumeration = 
			Warehouse.lookupExtendibleEnumeration(extendibleEnumerationSymbol);
		
		if (enumeration != null)
			for ( String elementName : enumeration.keySet() ) {
				
				building.startJavadoc();
				building.wrapComment("<p>Element " + camelToWords(elementName) + " of extendible enumeration " +
						camelToWords(extendibleEnumerationName) + ".</p>", LINE_WIDTH);
				building.endComment();
				
				AUID elementValue = enumeration.get(elementName);
				building.append("@ExtendibleEnumerationItem(target = \"" + extendibleEnumerationSymbol + "\")");
				building.append("public final static AUID " + elementName + " = ");
				building.append("        Forge.makeAUID(" +
						"0x" + padHexTo8(elementValue.getData1()) + ", " +
						"(short) 0x" + padHexTo4(elementValue.getData2()) + ", " +
						"(short) 0x" + padHexTo4(elementValue.getData3()) + ",");
				building.appendNL("                new byte[] { " + bytesToText(elementValue.getData4()) + " } );");
			}
		
		building.reset("}");
		
		return building.toString();
	}
	
	static final String generateRecordInterface(
			String basePackageName,
			TypeDataRecord recordType) {
		
		StringIndenter building = new StringIndenter();
		building.setPackageName(basePackageName);

		building.startJavadoc();
		if ((recordType.description != null) && (recordType.description.length() > 0))
			building.wrapComment("<p>" + recordType.description + " Interface.</p>", LINE_WIDTH);
		else
			building.wrapComment("<p>Interface to values of the " + camelToWords(recordType.name) + 
					" record type.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@author Auto generation", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see TypeDefinitions#" + recordType.name, LINE_WIDTH);
		building.endComment();
		
		building.append("public interface " + recordType.name);
		building.incrementNL("    extends Cloneable {");
		
		building.startJavadoc();
		building.wrapComment("<p>Names of the members of a " + camelToWords(recordType.name) +
				" value. The indexes of this array correspond with the indexes of the {@linkplain #MEMBER_TYPES member types array}.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see #MEMBER_TYPES", LINE_WIDTH);
		building.endComment();
		
		building.append("public final static String[] MEMBER_NAMES = new String[] {");
		for ( int x = 0 ; x < recordType.memberNames.size() ; x++ ) 
			building.append("    \"" + recordType.memberNames.get(x) + "\",");
		building.appendNL("};");
		
		building.startJavadoc();
		building.wrapComment("<p>Types of the members of a " + camelToWords(recordType.name) +
				" value. The indexes of this array correspond with the indexes of the {@linkplain #MEMBER_NAMES member names array}.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see #MEMBER_NAMES", LINE_WIDTH);
		building.endComment();

		building.addImport("tv.amwa.maj.meta.TypeDefinition");
		building.append("public final static TypeDefinition[] MEMBER_TYPES = new TypeDefinition[] {");
		for ( int x = 0 ; x < recordType.memberTypes.size() ; x++ ) {
			String memberTypeName = recordType.memberTypes.get(x);
			
			if (typeList.containsKey(memberTypeName))
				building.append("    TypeDefinitions." + recordType.memberTypes.get(x) + ",");
			else
				building.append("    tv.amwa.maj.industry.TypeDefinitions." + recordType.memberTypes.get(x) + ",");
		}
		building.appendNL("};");
		
		List<String> exceptional = new Vector<String>();
		
		for ( int x = 0 ; x < recordType.memberNames.size() ; x++ ) {
			
			TypeData elementType = typeByName(recordType.memberTypes.get(x));
			String annotation = elementType.getAnnotation();
			if (annotation == null) 
				annotation = "";
			else
				annotation = "@" + annotation + " ";
			building.addImport(elementType.getImports());
			
			building.startJavadoc();
			building.wrapComment("<p>Returns the " + lowerFirstLetter(camelToWords(recordType.memberNames.get(x))) + 
					" member of record type " + camelToWords(recordType.name) + ".</p>", LINE_WIDTH);
			building.blankComment();
			building.wrapComment("@return " + upperFirstLetter(camelToWords(recordType.memberNames.get(x))) + ".", LINE_WIDTH);
			if (elementType instanceof TypeDataExtendibleEnumeration) {
				building.blankComment();
				building.wrapComment("@see #get" + upperFirstLetter(recordType.memberNames.get(x)) + 
						"Name()", LINE_WIDTH);
			}
			building.endComment();
			
			building.appendNL("public " + annotation + elementType.getJavaGetName() + 
					((elementType instanceof TypeDataFixedArray) ? "[]" : "") + " get" +
					upperFirstLetter(recordType.memberNames.get(x)) + "();");
			
			building.startJavadoc();
			building.wrapComment("<p>Sets the " + lowerFirstLetter(camelToWords(recordType.memberNames.get(x))) + 
					" member of record type " + camelToWords(recordType.name) + ".</p>", LINE_WIDTH);
			building.blankComment();
			building.wrapComment("@param " + lowerFirstLetter(recordType.memberNames.get(x)) + " " +
					upperFirstLetter(camelToWords(recordType.memberNames.get(x))) + ".", LINE_WIDTH);
			if ((!(elementType instanceof TypeDataInteger)) || (elementType.getThrowsIllegal()))
				building.blankComment();
			if (!(elementType instanceof TypeDataInteger))
				building.wrapComment("@throws NullPointerException Cannot set the member value with a <code>null</code>.", LINE_WIDTH);
			if (elementType.getThrowsIllegal())
				building.wrapComment("@throws IllegalArgumentException The given value is illegal for the required type.", LINE_WIDTH);
			if (elementType instanceof TypeDataExtendibleEnumeration) {
				building.blankComment();
				building.wrapComment("@see #set" + upperFirstLetter(recordType.memberNames.get(x)) + 
						"(String)", LINE_WIDTH);
			}
			building.endComment();
			
			building.append("public void set" + upperFirstLetter(recordType.memberNames.get(x)) + "(");
			building.append("        " + annotation + elementType.getJavaSetName(false) + 
					((elementType instanceof TypeDataFixedArray) ? "[]" : "") + " " +
					lowerFirstLetter(recordType.memberNames.get(x)) + ")");
			exceptional.clear();
			if (!(elementType instanceof TypeDataInteger))
				exceptional.add("NullPointerException");
			if (elementType.getThrowsIllegal())
				exceptional.add("IllegalArgumentException");
			building.addThrowsClause(exceptional, true);
			
			if (elementType instanceof TypeDataExtendibleEnumeration) {

				building.startJavadoc();
				building.wrapComment("<p>Returns the " + lowerFirstLetter(camelToWords(recordType.memberNames.get(x))) + 
						" member of record type " + camelToWords(recordType.name) + " by its name.</p>", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@return " + upperFirstLetter(camelToWords(recordType.memberNames.get(x))) + ".", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@see #get" + upperFirstLetter(recordType.memberNames.get(x)) + "()", LINE_WIDTH);
				building.endComment();
				
				building.appendNL("public String get" + 
						upperFirstLetter(recordType.memberNames.get(x)) + "Name();");
				
				building.startJavadoc();
				building.wrapComment("<p>Sets the " + lowerFirstLetter(camelToWords(recordType.memberNames.get(x))) + 
						" member of record type " + camelToWords(recordType.name) + " by its name.</p>", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@param " + lowerFirstLetter(recordType.memberNames.get(x)) + " " +
						upperFirstLetter(camelToWords(recordType.memberNames.get(x))) + ".", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@throws NullPointerException Cannot set the value with a <code>null</code>.", LINE_WIDTH);
				building.wrapComment("@throws IlleggalArgumentException The given name is not known for this extendible enumeration type.", LINE_WIDTH);
				building.blankComment();
				building.wrapComment("@see #set" + upperFirstLetter(recordType.memberNames.get(x)) + "(tv.amwa.maj.record.AUID)", LINE_WIDTH);
				building.endComment();
				
				building.append("public void set" + upperFirstLetter(recordType.memberNames.get(x)) + "(");
				building.append("        String " + lowerFirstLetter(recordType.memberNames.get(x)) + ")");
				exceptional.clear();
				exceptional.add("NullPointerException");
				exceptional.add("IllegalArgumentException");
				building.addThrowsClause(exceptional, true);
			}
		}
		
		building.startJavadoc();
		building.wrapComment("<p>Create a cloned copy of this " + camelToWords(recordType.name) + 
				" value.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@return Cloned copy of this " + camelToWords(recordType.name) + " value.", LINE_WIDTH);
		building.endComment();
		
		building.appendNL("public " + recordType.name + " clone();");
		
		building.reset("}");
		
		return building.toString();
	}
	
	static final String generateRecordImplementation(
			String basePackageName,
			TypeDataRecord recordType) {
		
		StringIndenter building = new StringIndenter();
		building.setPackageName(basePackageName + ".impl");

		building.startJavadoc();
		if ((recordType.description != null) && (recordType.description.length() > 0))
			building.wrapComment("<p>" + recordType.description + " Implmentation.</p>", LINE_WIDTH);
		else
			building.wrapComment("<p>Implementation for values of the " + camelToWords(recordType.name) + 
					" record type.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@author Auto generation", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see TypeDefinitions#" + recordType.name, LINE_WIDTH);
		building.endComment();
		
		building.addImport(basePackageName + "." + recordType.name);
		building.addImport(basePackageName + ".Constants");
		building.addImport("java.io.Serializable");
		building.addImport("tv.amwa.maj.io.xml.XMLSerializable;");
		
		building.append("public class " + recordType.name + "Impl");
		building.increment("implements");
		building.increment(recordType.name + ",");
		building.append("Serializable,");
		building.append("XMLSerializable,");
		building.append("Constants,");
		building.append("Cloneable {");
		
		building.decrement("");
		
		for ( int x = 0 ; x < recordType.memberNames.size() ; x++ ) {
		
			TypeData elementType = typeByName(recordType.memberTypes.get(x));
			String annotation = elementType.getAnnotation();
			if (annotation == null) 
				annotation = "";
			else
				annotation = "@" + annotation + " ";
			building.addImport(elementType.getImports());

			if ((typeList.containsKey(elementType.name)) &&
					((elementType.getTypeCategory() == TypeCategory.Enum) ||
					(elementType.getTypeCategory() == TypeCategory.Record)))
				building.addImport(basePackageName + "." + elementType.name);
			
			building.append("private " + annotation + elementType.getJavaSetName(false) + 
					((elementType instanceof TypeDataFixedArray) ? "[]" : "") + " " +
					lowerFirstLetter(recordType.memberNames.get(x)) + ";");
		}
		
		building.append("");
		
		building.appendNL("public " + recordType.name + "Impl() { }");
		
		// Needs to go in the factory ... type definition defined to the interface
//		building.append("static {");
//		building.increment("tv.amwa.maj.meta.TypeDefinitionRecordImpl.registerInterfaceMapping(");
//		building.append("        " + basePackageName + "." + recordType.name + ".class,");
//		building.append("        " + basePackageName + ".impl." + recordType.name + "Impl.class);");
//		building.decrement("}");

		List<String> exceptional = new Vector<String>();
		
		// TODO do something better with extendible enumerations. Check the IDs and add name methods.
		
		for ( int x = 0 ; x < recordType.memberNames.size() ; x++ ) {
			
			TypeData elementType = typeByName(recordType.memberTypes.get(x));
			String annotation = elementType.getAnnotation();
			if (annotation == null) 
				annotation = "";
			else
				annotation = "@" + annotation + " ";
			building.addImport(elementType.getImports());
						
			building.appendNL("public " + annotation + elementType.getJavaGetName() + 
					((elementType instanceof TypeDataFixedArray) ? "[]" : "") + " get" +
					upperFirstLetter(recordType.memberNames.get(x)) + "() {");
			building.increment("return this." + lowerFirstLetter(recordType.memberNames.get(x)) + ";");
			building.decrementNL("}");
			
			building.append("public void set" + upperFirstLetter(recordType.memberNames.get(x)) + "(");
			building.append("        " + annotation + elementType.getJavaSetName(false) + 
					((elementType instanceof TypeDataFixedArray) ? "[]" : "") + " " +
					lowerFirstLetter(recordType.memberNames.get(x)) + ")");
			exceptional.clear();
			if (!(elementType instanceof TypeDataInteger))
				exceptional.add("NullPointerException");
			if (elementType.getThrowsIllegal())
				exceptional.add("IllegalArgumentException");
			building.addThrowsClause(exceptional, false);

			if (!(elementType instanceof TypeDataInteger)) {

				building.append("if (" + lowerFirstLetter(recordType.memberNames.get(x)) + " == null)");
				building.appendNL("    throw new NullPointerException(\"Cannot set the " + 
						camelToWords(recordType.memberNames.get(x)) + " member of a " + 
						camelToWords(recordType.name) + " value with a null value.\");");
			}
			// TODO IllegalArgument
			
			building.append("this." + lowerFirstLetter(recordType.memberNames.get(x)) + " = " +
					lowerFirstLetter(recordType.memberNames.get(x)) + ";");
			
			building.decrementNL("}");
			
			if (elementType instanceof TypeDataExtendibleEnumeration) {
				
				building.appendNL("public String get" + 
						upperFirstLetter(recordType.memberNames.get(x)) + "Name() {");
				
				building.increment("try {");
				if (typeList.containsKey(elementType.name)) 
					building.increment("return " + basePackageName + ".TypeDefinitions." + elementType.name + 
							".getNameFromAUID(" + lowerFirstLetter(recordType.memberNames.get(x)) + ");");
				else
					building.increment("return tv.amwa.maj.industry.TypeDefinitions." + elementType.name + 
							".getNameFromAUID(" + lowerFirstLetter(recordType.memberNames.get(x)) + ");");
				building.decrement("}");
				building.addImport("tv.amwa.maj.exception.BadParameterException");
				building.append("catch (BadParameterException bpe) {");
				building.increment("System.err.println(\"A stored identifier is not registered for extendible enumeration " + 
						camelToWords(elementType.name) + ".\");");
				building.append("return null;");
				building.decrement("}");
				building.decrementNL("}");
				
				building.append("public void set" + upperFirstLetter(recordType.memberNames.get(x)) + "(");
				building.append("        String " + lowerFirstLetter(recordType.memberNames.get(x)) + ")");
				exceptional.clear();
				exceptional.add("NullPointerException");
				exceptional.add("IllegalArgumentException");
				building.addThrowsClause(exceptional, false);
				
				building.append("if (" + lowerFirstLetter(recordType.memberNames.get(x)) + " == null)");
				building.appendNL("    throw new NullPointerException(\"Cannot set the " + 
						camelToWords(recordType.memberNames.get(x)) + " member of a " + 
						camelToWords(recordType.name) + " value with a null value.\");");

				building.append("try {");
				if (typeList.containsKey(elementType.name)) 
					building.increment("set" + upperFirstLetter(recordType.memberNames.get(x)) + "(" + 
							basePackageName + ".TypeDefinitions." + elementType.name + 
							".getAUIDFromName(" + lowerFirstLetter(recordType.memberNames.get(x)) + "));");
				else
					building.increment("set" + upperFirstLetter(recordType.memberNames.get(x)) + "(" + 
							"tv.amwa.maj.industry.TypeDefinitions." + elementType.name + 
							".getAUIDFromName(" + lowerFirstLetter(recordType.memberNames.get(x)) + "));");
				building.decrement("}");
				building.addImport("tv.amwa.maj.exception.BadParameterException");
				building.append("catch (BadParameterException bpe) {");
				building.increment("throw new IllegalArgumentException(\"The given element name is not recognised for extendible enumeration " +
						camelToWords(elementType.name) + ".\");");
				building.decrement("}");
				building.decrementNL("}");
			}
		}
		
		// Clone
		
		building.appendNL("public " + recordType.name + " clone() {");
		
		building.increment("try {");
		building.increment("return (" + recordType.name + ") super.clone();");
		building.decrement("}");
		building.append("catch (CloneNotSupportedException cnse) {");
		building.increment("// Implements Cloneable so should never happen");
		building.append("throw new InternalError(cnse.getMessage());");
		building.decrement("}");
		building.decrementNL("}");
		
		// Hashcode
		
		building.appendNL("public int hashCode() {");
		
		building.incrementNL("int hashcode = 0;");
		
		for ( int x = 0 ; x < recordType.memberNames.size() ; x++ ) {
			TypeData elementType = typeByName(recordType.memberTypes.get(x));
			String memberName = lowerFirstLetter(recordType.memberNames.get(x));
			
			switch (elementType.getTypeCategory()) {
			
			case Int:
				building.append("hashcode ^= (int) " + memberName + ";");
				break;
			case Record:
			case Enum:
			case ExtEnum:
				building.append("hashcode ^= " + memberName + ".hashCode();");
				break;
			case FixedArray:
				building.addImport("java.util.Arrays");
				building.append("hashcode ^= Arrays.hashCode(" + memberName + ");");
			default:
				break;
			}
		}
		
		building.append("");
		
		building.append("return hashcode;");
		building.decrementNL("}");
		
		// Equals
		building.append("public boolean equals(");
		building.appendNL("        Object o) {");
		
		building.increment("if (o == null) return false;");
		building.append("if (this == o) return true;");
		building.appendNL("if (!(o instanceof " + recordType.name + ")) return false;");
		
		building.appendNL(recordType.name + " testValue = (" + recordType.name + ") o;");
		
		for ( int x = 0 ; x < recordType.memberNames.size() ; x++ ) {
			TypeData elementType = typeByName(recordType.memberTypes.get(x));
			String memberName = lowerFirstLetter(recordType.memberNames.get(x));
			
			switch (elementType.getTypeCategory()) {
			
			case Int:
			case Enum:
				building.append("if (" + memberName + " != testValue.get" + upperFirstLetter(memberName) + "())");
				building.append("    return false;");
				break;
			case Record:
			case ExtEnum:
				building.append("if (!(" + memberName + ".equals(testValue.get" + upperFirstLetter(memberName) + "())))");
				building.append("    return false;");
				break;
			case FixedArray:
				building.addImport("java.util.Arrays");
				building.append("if (!(Arrays.equals(" + memberName + ", testValue.get" + upperFirstLetter(memberName) + "())))");
				building.append("    return false;");				
			default:
				break;
			}
		}
		
		building.append("");
		building.append("return true;");
		building.decrementNL("}");
		
		building.addImport("org.w3c.dom.Node");
		building.addImport("org.w3c.dom.DocumentFragment");
		building.addImport("tv.amwa.maj.io.xml.XMLBuilder");
		
		building.append("public void appendXMLChildren(");
		building.appendNL("        Node parent) {");
		
		String elementName = lowerFirstLetter(recordType.name) + "Value";
		building.incrementNL("Node " + elementName + ";");
		
		building.append("if (parent instanceof DocumentFragment)");
		building.append("    " + elementName + " = XMLBuilder.createChild(parent, XML_NAMESPACE, XML_PREFIX, \"" + recordType.name + "\");");
		building.append("else");
		building.appendNL("    " + elementName + " = parent;");
		
		for ( int x = 0 ; x < recordType.memberNames.size() ; x++ ) {
			TypeData elementType = typeByName(recordType.memberTypes.get(x));
			String memberName = lowerFirstLetter(recordType.memberNames.get(x));
			
			switch (elementType.getTypeCategory()) {
			
			case Int:
				building.append("XMLBuilder.appendElement(" + elementName + ", XML_NAMESPACE, XML_PREFIX,");
				building.appendNL("        \"" + upperFirstLetter(memberName) + "\", " + memberName + ");");
				break;
			case Enum:
				building.append("XMLBuilder.appendElement(" + elementName + ", XML_NAMESPACE, XML_PREFIX,");
				building.appendNL("        \"" + upperFirstLetter(memberName) + "\", " + memberName + ".symbol());");
				break;
			case Record:
			case ExtEnum:
				building.append("XMLBuilder.appendElement(" + elementName + ", XML_NAMESPACE, XML_PREFIX,");
				building.appendNL("        \"" + upperFirstLetter(memberName) + "\", " + memberName + ".toString());");
				break;
			case FixedArray:
				building.append("Node " + memberName + "Node = XMLBuilder.createChild(" + elementName + 
						", XML_NAMESPACE, XML_PREFIX, \"" + memberName + "\");");
				building.append("for ( " + elementType.getJavaGetName() + " value : " + memberName + ")");
				building.append("    XMLBuilder.appendElement(" + memberName + "Node, XML_NAMESPACE, XML_PREFIX,");
				building.appendNL("            \"" + ((TypeDataFixedArray) elementType).elementType + "\", value);");
				break;
			default:
				break;
			}
		}
		
		building.decrementNL("}");
		
		building.appendNL("public String getComment() {");
		building.increment("return null;");
		building.decrementNL("}");
		
		building.appendNL("public String toString() {");
		building.increment("return XMLBuilder.toXMLNonMetadata(this);");
		building.decrementNL("}");
		
		building.addImport("java.util.regex.Pattern");
		building.addImport("java.util.regex.Matcher");
		
		for (int x = 0 ; x < recordType.memberNames.size() ; x++ ) {
			TypeData elementType = typeByName(recordType.memberTypes.get(x));
			String memberName = lowerFirstLetter(recordType.memberNames.get(x));
			
			building.append("private final static Pattern " + memberName + "Pattern = ");
			if (elementType instanceof TypeDataFixedArray) {
				building.append("    Pattern.compile(\"<\\\\w*\\\\:?" + upperFirstLetter(memberName) + "\\\\>\" +");
				building.append("            \"(<\\\\w*\\\\:?" + ((TypeDataFixedArray) elementType).elementType + "\\\\>\\\\S*" +
						"<\\\\/\\\\w*\\\\:?" + ((TypeDataFixedArray) elementType).elementType + "\\\\>)*\" +");
				building.append("            \"<\\\\/\\\\w*\\\\:?" + upperFirstLetter(memberName) + "\\\\>\");");
			}
			else {
				building.append("    Pattern.compile(\"<\\\\w*\\\\:?" + upperFirstLetter(memberName) + "\\\\>(\\\\S*)" +
						"<\\\\/\\\\w*\\\\:?" + upperFirstLetter(memberName) + "\\\\>\");");
			}
		}
		
		building.addImport("java.text.ParseException");
		
		building.append("");
		building.append("// TODO best to replace this with something more type specific");
		building.append("public final static " + recordType.name + " parseFactory(");
		building.append("        String valueAsString)");
		building.append("    throws NullPointerException,");
		building.appendNL("        ParseException {");
		
		building.increment("if (valueAsString == null)");
		building.appendNL("    throw new NullPointerException(\"Cannot create a " + camelToWords(recordType.name) +
				" value from a null string value.\");");
		
		building.append("Matcher matcher;");
		building.appendNL(recordType.name + " value = new " + recordType.name + "Impl();");
		

		for (int x = 0 ; x < recordType.memberNames.size() ; x++ ) {
			TypeData elementType = typeByName(recordType.memberTypes.get(x));
			String memberName = lowerFirstLetter(recordType.memberNames.get(x));
			
			building.append("try {");
			building.increment("matcher = " + memberName + "Pattern.matcher(valueAsString);");
			
			if (elementType instanceof TypeDataFixedArray) {
				TypeDataFixedArray fixedType = (TypeDataFixedArray) elementType;
				TypeData fixedElementType = typeByName(fixedType.elementType);
				
				building.appendNL(elementType.getJavaSetName(false) + "[] " + memberName + "Value = new " +
						elementType.getJavaSetName(false) + "[" + fixedType.elementCount + "];");
				building.append("for ( int x = 0 ; x < matcher.groupCount() ; x++ ) {");
				building.increment("String elementValue = matcher.group(x);");
				building.append("elementValue = elementValue.substring(elementValue.indexOf(\'>\') + 1);");
				building.append("elementValue = elementValue.substring(0, elementValue.indexOf(\'<\'));");
				if (typeList.containsKey(fixedType.elementType)) 
					building.append(memberName + "Value[x] = (" + fixedElementType.getJavaSetName(true) + ") " + 
							basePackageName + ".TypeDefinitions." + fixedElementType.name + 
							".createValue(elementValue).getValue();");
				else
					building.append(memberName + "Value[x] = (" + fixedElementType.getJavaSetName(true) + ") " + 
							"tv.amwa.maj.industry.TypeDefinitions." + fixedElementType.name + 
							".createValue(elementValue).getValue();");
				building.decrement("}");
			}
			else {
				building.append("if (matcher.find())");
				building.append("    value.set" + upperFirstLetter(memberName) + "(");
				if (typeList.containsKey(elementType.name)) 
					building.append("            (" + elementType.getJavaSetName(true) + ") " + 
							basePackageName + ".TypeDefinitions." + elementType.name + 
					".createValue(matcher.group(1)).getValue());");
				else
					building.append("            (" + elementType.getJavaSetName(true) + ") " + 
							"tv.amwa.maj.industry.TypeDefinitions." + elementType.name + 
					".createValue(matcher.group(1)).getValue());");
				building.append("else");
				building.append("    throw new ParseException(\"Unable to find and parse a value for member " +
						camelToWords(memberName) + " of type " + camelToWords(recordType.name) + ".\", 0);");
			}

			building.decrement("}");
			building.append("catch (ClassCastException cce) {");
			building.increment("throw new ParseException(\"Unable to parse a value for member " +
					camelToWords(memberName) + " of type " + camelToWords(recordType.name) + ".\", 0);");
			building.decrementNL("}");
		}

		building.append("return value;");
		
		building.decrementNL("}");
		
		building.reset("}");

		return building.toString();
	}
	
	static final String generateConstants(
			DictionaryContext context) {
		
		StringIndenter building = new StringIndenter();
		building.setPackageName(context.basePackageName);
		
		building.startJavadoc();
		building.wrapComment("<p>Constant values, including the XML namespace, for package <code>" +
				context.basePackageName + "</code>.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@author Auto generation", LINE_WIDTH);
		building.endComment();
		
		building.append("public interface Constants {");
		building.increment("");
		
		building.startJavadoc();
		building.wrapComment("<p>Namespace for all symbols of the extension namespace.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see #XML_PREFIX", LINE_WIDTH);
		building.endComment();
		
		building.append("public final static String XML_NAMESPACE =");
		building.appendNL("    \"" + context.schemeURI + "\";");
		
		building.startJavadoc();
		building.wrapComment("<p>Prefix for all symbols of the extension namespace.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see #XML_NAMESPACE", LINE_WIDTH);
		building.endComment();

		building.appendNL("public final static String XML_PREFIX = \"" + context.preferredPrefix + "\";");
		
		building.addImport("tv.amwa.maj.record.AUID");
		building.addImport("tv.amwa.maj.industry.Forge");
		
		building.startJavadoc();
		building.wrapComment("<p>Identification of the extension namespace.</p>", LINE_WIDTH);
		building.endComment();
		
		building.append("public final static AUID EXTENSION_ID = Forge.makeAUID(");
		building.append("        0x" + padHexTo8(context.schemeID.getData1()) + ", " +
				"(short) 0x" + padHexTo4(context.schemeID.getData2()) + ", " +
				"(short) 0x" + padHexTo4(context.schemeID.getData3()) + ",");
		building.appendNL("        new byte[] { " + bytesToText(context.schemeID.getData4()) + " } );");
		
		building.reset("}");
		
		return building.toString();
	}
	
	static final String generateFactory(
			DictionaryContext context) {

		StringIndenter building = new StringIndenter();
		building.setPackageName(context.basePackageName);

		building.startJavadoc();
		building.wrapComment("<p>Factory for all types and classes of symbolespace <code>" + context.schemeURI + 
				"</code>. Contains methods to initialize the MAJ media engine and warehouses with the types " + 
				"of this symbol space.</p>", LINE_WIDTH);
		if ((context.extensionDescription != null) && (context.extensionDescription.length() > 0)) {
			building.blankComment();
			building.wrapComment("<p>" + context.extensionDescription + "</p>", LINE_WIDTH);
		}
		building.blankComment();
		building.wrapComment("@author Auto generation", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see TypeDefinitions", LINE_WIDTH);
		building.wrapComment("@see tv.amwa.maj.industry.MediaEngine", LINE_WIDTH);
		building.wrapComment("@see tv.amwa.maj.industry.Warehouse", LINE_WIDTH);
		building.endComment();
		
		building.append("public class " + context.factoryName + "{");
		building.increment("");
		
		building.appendNL("private static boolean initialized = false;");
		
		building.startJavadoc();
		building.wrapComment("<p>List of all the implementing classes defined for this symbol space.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see #initialize()", LINE_WIDTH);
		building.endComment();
		
		building.append("public final static Class<?>[] CLASSES = new Class<?>[] {");
		for ( String className : classList.keySet() ) {
			building.append("        " + context.basePackageName + ".impl." + className + "Impl.class,");
		}
		building.appendNL("};");
		
		building.startJavadoc();
		building.wrapComment("<p>Initialize all the types of this symbol space and make them available through " +
				"the MAJ {@linkplain tv.amwa.maj.industry.MediaEngine media engine}. The media engine and associated " +
				"APIs can then make, serialize and deserialize values to all supported XML formats, binary formats and " +
				"persistance providers.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see tv.amwa.maj.industry.MediaEngine#initializeAAF()", LINE_WIDTH);
		building.wrapComment("@see #CLASSES", LINE_WIDTH);
		building.endComment();
		
		building.append("public final static void initialize() {");
		building.increment("");
		
		building.addImport("tv.amwa.maj.industry.Warehouse");
		
		building.appendNL("if (initialized) return;");
		
		building.append("// Register all of the symbolspace classes");
		building.append("for ( Class<?> extensionClass : CLASSES )");
		building.appendNL("    Warehouse.lookForClass(extensionClass);");

		building.append("// Register all of the initial extendible enumeration values");		
		for ( TypeData typeData : typeList.values() ) {

			if (typeData instanceof TypeDataExtendibleEnumeration) {				
				building.append("try {");
				building.append("    Warehouse.registerExtendibleEnumerationElements(" + 
						typeData.name + ".class);");
				building.append("}");
				building.append("catch (Exception e) {");
				building.append("    System.err.println(\"Unable to register items for extendible enumeration " + 
						typeData.name + ".\");");
				building.appendNL("}");
			}
		}
				
		building.append("// Register all of the extension type definitions");
		building.appendNL("Warehouse.registerTypes(TypeDefinitions.class, Constants.XML_NAMESPACE, Constants.XML_PREFIX);");
		
		List<String> recordRegistrations = new Vector<String>();
		for ( TypeData typeData : typeList.values() ) {
			if (typeData instanceof TypeDataRecord) 
				recordRegistrations.add(typeData.name);
		}		
		
		if (recordRegistrations.size() > 0) {
			building.addImport("tv.amwa.maj.meta.TypeDefinitionRecordImpl");
			building.append("// Set up all record type mappings from interface to implementation");
		
			for ( String registration : recordRegistrations ) {
				building.append("TypeDefinitionRecordImpl.registerInterfaceMapping(");
				building.append("        " + context.basePackageName + "." + registration + ".class,");
				building.append("        " + context.basePackageName + ".impl." + registration + "Impl.class);");
			}
			
			building.append("");
		}
		
		building.appendNL("initialized = true;");
		
		building.decrementNL("}");
		
		building.addImport("tv.amwa.maj.industry.MetadataObject");
		building.addImport("tv.amwa.maj.industry.Forge");
		
		building.startJavadoc();
		building.wrapComment("<p>Create a new instance of a class defined in this symbol space from its name " +
				"and initial property values.</p>", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@param className Name of the class in this symbol space to create.", LINE_WIDTH);
		building.wrapComment("@param properties List of property identifier and value pairs to use to make a value " +
				"of the required type.", LINE_WIDTH);
		building.wrapComment("@return Newly created value of the named class.", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@throws NullPointerException Cannot create a new instance from a <code>null</code> name " +
				"or <code>null</code> property specifications.", LINE_WIDTH);
		building.wrapComment("@throws IllegalArgumentException Unable to use the given properties to create a value of " +
				"the name class or the named class does not exist.", LINE_WIDTH);
		building.blankComment();
		building.wrapComment("@see tv.amwa.maj.industry.Forge#makeByName(String, String, Object...)", LINE_WIDTH);
		building.endComment();
		
		building.append("@SuppressWarnings(\"unchecked\")");
		building.append("public final static <T extends MetadataObject> T make(");
		building.increment("    String className,");
		building.append("    Object... properties)");
		building.append("throws NullPointerException,");
		building.appendNL("    IllegalArgumentException {");
		
		building.append("return (T) Forge.makeByName(Constants.XML_NAMESPACE, className, properties);");
		building.decrementNL("}");
		
		for ( String recordTypeName : recordRegistrations ) {
			
			TypeDataRecord recordType = (TypeDataRecord) typeList.get(recordTypeName);
			
			building.addImport("java.text.ParseException");
			
			building.startJavadoc();
			building.wrapComment("<p>Parse a string to create a value of " + camelToWords(recordTypeName) + " type.</p>", 
					LINE_WIDTH);
			building.blankComment();
			building.wrapComment("@param valueAsString Value formatted as a string, often in a pseudo-XML format.", LINE_WIDTH);
			building.wrapComment("@return New " + camelToWords(recordTypeName) + " value created from the given string.", 
					LINE_WIDTH);
			building.blankComment();
			building.wrapComment("@throws NullPointerException The given string value is <code>null</code>.", LINE_WIDTH);
			building.wrapComment("@throws ParseException Error parsing the given string to create a " + 
					camelToWords(recordTypeName) + " value.", LINE_WIDTH);
			building.endComment();
			
			building.append("public final static " + recordTypeName + " parse" + recordTypeName + "(");
			building.increment("    String valueAsString)");
			building.append("throws NullPointerException,");
			building.appendNL("    ParseException {");
			
			building.append("return " + context.basePackageName + ".impl." + recordTypeName + 
					"Impl.parseFactory(valueAsString);");
			building.decrementNL("}");
			
			building.startJavadoc();
			building.wrapComment("<p>Create a " + camelToWords(recordTypeName) + " value from the member element values.</p>", 
					LINE_WIDTH);
			building.wrapComment("@return Newly created " + camelToWords(recordTypeName) + " value.", LINE_WIDTH);
			building.blankComment();
			building.wrapComment("@throws NullPointerException One or more of the member element values is <code>null</code>.", 
					LINE_WIDTH);
			building.wrapComment("@throws IllegalArgumentException One or more of the member element values is illegal for " +
					"its type.", LINE_WIDTH);
			building.blankComment();
			building.wrapComment("@see #parse" + upperFirstLetter(recordTypeName) + "(String)", LINE_WIDTH);
			building.endComment();
			
			building.append("public final static " + recordTypeName + " create" + recordTypeName + "(");

			for ( int x = 0 ; x < recordType.memberNames.size() ; x++ ) {
				String memberName = recordType.memberNames.get(x);
				TypeData memberType = typeByName(recordType.memberTypes.get(x));
				
				building.addImport(memberType.getImports());
				
				building.append("        " + memberType.getJavaSetName(false) + 
						((memberType instanceof TypeDataFixedArray) ? "[] " : " ") + 
						lowerFirstLetter(memberName) +
						((x == recordType.memberNames.size() - 1) ? ")" : ","));
			}
			building.increment("throws NullPointerException,");
			building.appendNL("    IllegalArgumentException {");
			
			building.append(recordTypeName + " " + lowerFirstLetter(recordTypeName) + "Value =");
			building.appendNL("         new " + context.basePackageName + ".impl." + recordTypeName + "Impl();");
			
			for ( int x = 0 ; x < recordType.memberNames.size() ; x++ ) {
				String memberName = recordType.memberNames.get(x);

				building.append(lowerFirstLetter(recordTypeName) + "Value.set" + upperFirstLetter(memberName) + 
						"(" + memberName + ");");
			}
			building.append("");
			
			building.append("return " + lowerFirstLetter(recordTypeName) + "Value;");
			building.decrement("}");
			
		}
		
		building.reset("}");
		
		return building.toString();
	}
}
