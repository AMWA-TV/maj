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
 * $Log: TestGeneration.java,v $
 * Revision 1.8  2011/10/05 17:14:31  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.7  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/26 11:48:41  vizigoth
 * Completed common method testing.
 *
 * Revision 1.5  2011/01/25 21:45:03  vizigoth
 * Added common method tests.
 *
 * Revision 1.4  2011/01/25 14:19:04  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.3  2011/01/24 14:01:14  vizigoth
 * Completed annotation and definition auto test generation.
 *
 * Revision 1.2  2011/01/21 22:41:08  vizigoth
 * Added annotation and meta definition tests.
 *
 * Revision 1.1  2011/01/21 17:35:21  vizigoth
 * Refactor to extract common generation code and initial authoring of code to generate JUnit tests.
 *
 */

package tv.amwa.maj.util;

import java.io.File;
import java.io.NotSerializableException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.meta.impl.PropertyDefinitionImpl;
import tv.amwa.maj.meta.impl.SingletonTypeDefinitionImpl.SingletonMethodBag;
import tv.amwa.maj.meta.impl.TypeDefinitionSetImpl.SetMethodBag;
import tv.amwa.maj.meta.impl.TypeDefinitionVariableArrayImpl.VariableArrayMethodBag;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.TimeStamp;

/**
 * <p>Generates JUnit tests from a meta dictionary file, designed to exercise all aspects of a Java
 * implementation of the elements defined in that dictionary.</p>
 * 
 *
 *
 */
public class TestGeneration 
	extends GenerationCore {

	public final static void main(
			String[] args) {
		
		if (args.length < 2) {
			System.out.println("Usage: java tv.amwa.maj.util.TestGeneration <base_package_name> <metadictionary_XML_file>");
			System.exit(1);
		}
		
		DictionaryContext context = null;
		
		long startTime = System.currentTimeMillis();
		System.out.println("Starting test generation for package " + args[0]);
		
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

		long dictDoneTime = System.currentTimeMillis();
		System.out.println("Dictionary " + args[1] + " read in " + (dictDoneTime - startTime) + "ms.");
		
		context.basePackageName = args[0];
		
		if (makeDirectories(context) == false) {
			System.out.println("Unable to create the required directory structure.");
			System.exit(1);
		}
		
		long foldersCreatedTime = System.currentTimeMillis();
		System.out.println("Folders created. Starting to generate tests from meta dictionary only for each class.");
		
		boolean success = true;
		
		for ( ClassData classData : classList.values() ) {
		
			if ((context.baseline) && (!classData.isInterchangeable()))
				success &= writeFile(
						context.testMetaDir,
						Warehouse.javaClassAlias(classData.name) + "MetaTest.java",
						generateMetaTest(context.basePackageName + ".meta", classData, context));
			else
				success &= writeFile(
						context.testModelDir,
						Warehouse.javaClassAlias(classData.name) + "MetaTest.java",
						generateMetaTest(context.basePackageName + ".model", classData, context));
		}
		
		long metaTestEnd = System.currentTimeMillis();
		System.out.println("All meta dictionary tests generated in " + (metaTestEnd - foldersCreatedTime) + "ms. Starting value tests.");
		
		Warehouse.clear();
		MediaEngine.initializeAAF();
		if (!context.basePackageName.equals("tv.amwa.maj")) {
			try {
				Class<?> extensionFactory = 
					Class.forName(context.basePackageName + "." + upperFirstLetter(context.preferredPrefix) + "Factory");
				Method initializeMethod = extensionFactory.getMethod("initialize");
				initializeMethod.invoke(null);
			}
			catch (Exception e) {
				System.out.println("Unable to initialize extension due to a " + e.getClass().getName() + ": " +
						e.getMessage());
				System.exit(1);
			}
		}

		for ( ClassData classData : classList.values() ) {
			
			if ((context.baseline) && (!classData.isInterchangeable()))
				success &= writeFile(
						context.testMetaDir,
						Warehouse.javaClassAlias(classData.name) + "ValueTest.java",
						generateValueTest(context.basePackageName + ".meta", classData, context));
			else
				success &= writeFile(
						context.testModelDir,
						Warehouse.javaClassAlias(classData.name) + "ValueTest.java",
						generateValueTest(context.basePackageName + ".model", classData, context));
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("All value tests generated in " + (endTime - metaTestEnd) + "ms.");
		if (success)
			System.out.println("Successfully generated all files for package " + context.basePackageName + 
					" in total time " + (endTime - startTime) + "ms.");
		else
			System.out.println("Problem encountered generating files for package " + context.basePackageName + ".");
	}
	
	// Tests generated before the registration of the types
	static String generateMetaTest(
			String packageName,
			ClassData classData,
			DictionaryContext context) {
		
		StringIndenter building = new StringIndenter();
		
		building.setPackageName(packageName + ".impl");
		
		building.addImport("static org.junit.Assert.*");
		building.addImport("org.junit.Test");
		building.addImport("org.junit.BeforeClass");
		building.addImport("org.junit.Before");
		building.addImport("org.junit.After");
		
		building.append("public class " + Warehouse.javaClassAlias(classData.name) + "MetaTest {");
		
		String modelInterface = Warehouse.javaClassAlias(classData.name);
		String testValue = lowerFirstLetter(modelInterface) + "Value";
		String testClass = lowerFirstLetter(modelInterface) + "Class";
		String testImpl = lowerFirstLetter(modelInterface) + "Impl";
		
		building.addImport(packageName + "." + modelInterface);
		
		building.increment("");
		
		if (classData.isConcrete) {
			building.append(modelInterface + " " + testValue + " = null;");
		}
		
		building.addImport("tv.amwa.maj.meta.ClassDefinition");
		building.append("ClassDefinition " + testClass + " = null;");
		
		building.addImport("tv.amwa.maj.industry.MetadataObject");
		building.appendNL("Class<? extends MetadataObject> " + testImpl + " = null;");
		
		building.append("@BeforeClass");
		building.appendNL("public final static void beforeClass() {");
		
		building.addImport("tv.amwa.maj.industry.MediaEngine");
		building.increment("MediaEngine.initializeAAF();");
		building.decrementNL("}");
		
//		building.append("@SuppressWarnings(\"unchecked\")");
		building.append("@Before");
		building.append("public final void setUp() {");

		building.increment("");
		
		if (classData.isConcrete) {
			building.addImport("tv.amwa.maj.industry.Forge");
			building.append(testValue + " = Forge.make(" + modelInterface + ".class);");
		}
		building.addImport("tv.amwa.maj.industry.Warehouse");
		building.append(testClass + " = Warehouse.lookForClass(" + modelInterface + ".class);");
		building.append(testImpl + " = ");
		building.append("        " + packageName + ".impl." + Warehouse.javaClassAlias(classData.name) + "Impl.class;");
		building.decrementNL("}");
		
		building.append("@After");
		building.append("public final void tearDown() {");
		building.increment("");
		if (classData.isConcrete)
			building.append(testValue + " = null;");
		building.append(testClass + " = null;");
		building.append(testImpl + " = null;");
		building.decrementNL("}");

		building.append("@Test");
		building.append("public final void testAllInitialized() {");
		
		building.increment("");
		if (classData.isConcrete) 
			building.append("assertNotNull(" + testValue + ");");
		building.append("assertNotNull(" + testClass + ");");
		building.append("assertNotNull(" + testImpl + ");");
		building.decrementNL("}");
		
		addAnnotationTests(building, classData, testImpl);
		addMetaDefinitionTests(building, classData, testClass, testImpl, modelInterface, context);
		
		for ( PropertyData property : propertyList.values() ) 
			if (property.memberOf.equals(classData.name)) {
				addPropertyAnnotationTests(building, property, testImpl);	
				addPropertyDefinitionTests(building, classData, property, testClass, testImpl, context);
			}
	
		addCompleteInstance(building, classData, modelInterface, "Meta", "");
		
		if (classData.isConcrete) {

			addCommonMethodTests(building, classData, testValue, modelInterface, context);
		}
		
		building.reset("}");
		
		return building.toString();
	}
	
	// Tests generated after warehouse is initialized
	static String generateValueTest(
			String packageName,
			ClassData classData,
			DictionaryContext context) {
	
		StringIndenter building = new StringIndenter();
		
		building.setPackageName(packageName + ".impl");
		
		building.addImport("static org.junit.Assert.*");
		building.addImport("org.junit.Test");
		building.addImport("org.junit.BeforeClass");
		building.addImport("org.junit.Before");
		building.addImport("org.junit.After");
		
		building.append("public class " + Warehouse.javaClassAlias(classData.name) + "ValueTest {");
		
		String modelInterface = Warehouse.javaClassAlias(classData.name);
		String testValue = lowerFirstLetter(modelInterface) + "Value";
		String testClass = lowerFirstLetter(modelInterface) + "Class";
		String testImpl = lowerFirstLetter(modelInterface) + "Impl";
		
		building.addImport(packageName + "." + modelInterface);
		
		building.increment("");
		
		if (classData.isConcrete) {
			building.append(modelInterface + " " + testValue + " = null;");
		}
		
		building.addImport("tv.amwa.maj.meta.ClassDefinition");
		building.append("ClassDefinition " + testClass + " = null;");
		
		building.addImport("tv.amwa.maj.industry.MetadataObject");
		building.appendNL("Class<? extends MetadataObject> " + testImpl + " = null;");
		
		building.append("@BeforeClass");
		building.appendNL("public final static void beforeClass() {");
		
		building.addImport("tv.amwa.maj.industry.MediaEngine");
		building.increment("MediaEngine.initializeAAF();");
		building.decrementNL("}");
		
	//	building.append("@SuppressWarnings(\"unchecked\")");
		building.append("@Before");
		building.append("public final void setUp() {");
	
		building.increment("");
		
		if (classData.isConcrete) {
			building.addImport("tv.amwa.maj.industry.Forge");
			building.append(testValue + " = Forge.make(" + modelInterface + ".class);");
		}
		building.addImport("tv.amwa.maj.industry.Warehouse");
		building.append(testClass + " = Warehouse.lookForClass(" + modelInterface + ".class);");
		building.append(testImpl + " = ");
		building.append("        " + packageName + ".impl." + Warehouse.javaClassAlias(classData.name) + "Impl.class;");
		building.decrementNL("}");
		
		building.append("@After");
		building.append("public final void tearDown() {");
		building.increment("");
		if (classData.isConcrete)
			building.append(testValue + " = null;");
		building.append(testClass + " = null;");
		building.append(testImpl + " = null;");
		building.decrementNL("}");
	
		building.append("@Test");
		building.append("public final void testAllInitialized() {");
		
		building.increment("");
		if (classData.isConcrete) 
			building.append("assertNotNull(" + testValue + ");");
		building.append("assertNotNull(" + testClass + ");");
		building.append("assertNotNull(" + testImpl + ");");
		building.decrementNL("}");
		
		addCompleteInstance(building, classData, modelInterface, "Value", "1");
		addCompleteInstance(building, classData, modelInterface, "Value", "2");
		
		if (classData.isConcrete) {

			for ( PropertyData property : getAllProperties(classData) ) {
				try {			
					addPropertyValueTests(building, property, testValue, modelInterface);
				}
				catch (BadParameterException bpe) {
 					System.out.println("Unable to generate test for property " + property.name + " due to a bad parameter exception: " + bpe.getMessage());
				}
			}
		}
		
		building.reset("}");
		
		return building.toString();
	}
	
	static void addAnnotationTests(
			StringIndenter building,
			ClassData classData,
			String testImpl) {

		building.addImport("tv.amwa.maj.industry.MediaClass");
	    building.append("@Test");
	    building.append("public final void testClassNameAnnotation() {");
	    building.increment("");	
	    building.append("MediaClass classAnnotation = " + testImpl + ".getAnnotation(MediaClass.class);");
	    building.append("assertNotNull(classAnnotation);");
	    building.append("assertEquals(\"" + classData.name + "\", classAnnotation.definedName());");
	    building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testClassSymbolAnnotation() {");
	    building.increment("");		    	
	    building.append("MediaClass classAnnotation = " + testImpl + ".getAnnotation(MediaClass.class);");
	    building.append("assertNotNull(classAnnotation);");
	    building.append("assertEquals(\"" + classData.symbol + "\", classAnnotation.symbol());");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testClassIdentificationAnnotations() {");
	    building.increment("");		    	
	    building.append("MediaClass classAnnotation = " + testImpl + ".getAnnotation(MediaClass.class);");
	    building.append("assertNotNull(classAnnotation);");
	    building.append("assertEquals(0x" + padHexTo8(classData.identification.getData1()) +", classAnnotation.uuid1());");
	    building.append("assertEquals(0x" + padHexTo4(classData.identification.getData2()) +", classAnnotation.uuid2());");
	    building.append("assertEquals(0x" + padHexTo4(classData.identification.getData3()) +", classAnnotation.uuid3());");
	    building.addImport("java.util.Arrays");
	    building.append("assertTrue(Arrays.equals(");
	    building.append("			new byte[] { " + bytesToText(classData.identification.getData4()) + " },"); 
	    building.append("			classAnnotation.uuid4()));");
		building.decrementNL("}");
	    
		building.addImport("java.lang.reflect.Modifier");
	    building.append("@Test");
	    building.append("public final void testClassImplementationAbstraction() {");
	    building.increment("");
	    if (classData.isConcrete)
	    	building.append("assertFalse(Modifier.isAbstract(" + testImpl + ".getModifiers()));");
	    else
	    	building.append("assertTrue(Modifier.isAbstract(" + testImpl + ".getModifiers()));");
		building.decrementNL("}");
	    
	    if ((classData.parentClass != null) && (classData.parentClass.length() > 0) && 
	    		(!classData.parentClass.equals(classData.name))) {
	    	building.append("@Test");
	    	building.append("public final void testClassImplementationParent() {");
	    	building.increment("");

	    	building.append("assertEquals(\"" + Warehouse.javaClassAlias(classData.parentClass) +
	    			"Impl\", " + testImpl + ".getSuperclass().getSimpleName());");
	    	building.decrementNL("}");
	    }
	}
	
	static void addMetaDefinitionTests(
			StringIndenter building, 
			ClassData classData,
			String testClass,
			String testImpl,
			String modelInterface,
			DictionaryContext context) {
		
	    building.append("@Test");
	    building.append("public final void testClassDefinitionName() {");
	    building.increment("");	
	    building.append("assertEquals(\"" + classData.name + "\", " + testClass + ".getName());");
	    building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testClassDefinitionSymbol() {");
	    building.increment("");	
	    	
	    building.append("assertEquals(\"" + classData.symbol + "\", " + testClass + ".getSymbol());");
	    building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testClassDefinitionIdentification() {");
	    building.increment("");	
	    building.append("assertEquals(0x" + padHexTo8(classData.identification.getData1()) + ", " + 
	    		testClass + ".getAUID().getData1());");
	    building.append("assertEquals(0x" + padHexTo4(classData.identification.getData2()) + ", " + 
	    		testClass + ".getAUID().getData2());");
	    building.append("assertEquals(0x" + padHexTo4(classData.identification.getData3()) + ", " + 
	    		testClass + ".getAUID().getData3());");
	    building.append("assertTrue(Arrays.equals(");
	    building.append("        new byte[] { " + bytesToText(classData.identification.getData4()) + " },");
	    building.append("    	 " + testClass + ".getAUID().getData4()));");
	    building.decrementNL("}");
	    
	    if ((classData.parentClass != null) && (classData.parentClass.length() > 0) && 
	    		(!classData.parentClass.equals(classData.name))) {
	    	
	    	building.append("@Test");
	    	building.append("public final void testClassDefinitionParentClass() {");
	    	building.increment("");	
	    	building.append("ClassDefinition expectedParent = Warehouse.lookForClass(" +
	    			Warehouse.javaClassAlias(classData.parentClass) + "Impl.class);");
	    	building.append("assertTrue(expectedParent.equals(" + testClass + ".getParent()));");
	    	building.decrementNL("}");
	    }
	    
	    building.append("@Test");
	    building.append("public final void testClassDefinitionSymbolSpace() {");
	    building.increment("");	
	    building.append("assertEquals(\"" + context.schemeURI + "\" ," + testClass + ".getNamespace());");
	    building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testClassDefinitionPreferredPrefix() {");
	    building.increment("");	
	    building.append("assertEquals(\"" + context.preferredPrefix + "\", " + testClass + ".getPrefix());");
	    building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testClassDefinitionIsRoot() {");
	    building.increment("");	
	    if (!classData.isRoot()) {
	    	building.append("assertFalse(" + testClass + ".isRoot());");
	    }
	    else {
	    	building.append("assertTrue(" + testClass + ".isRoot());");
	    }
	    building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testClassDefinitionIsConcrete() {");
	    building.increment("");	
	    if (classData.isConcrete)
	    	building.append("assertTrue(" + testClass + ".isConcrete());");
	    else
	    	building.append("assertFalse(" + testClass + ".isConcrete());");
	    building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testClassDefinitionJavaImplementation() {");
	    building.increment("");	
	    building.append("assertTrue(" + testImpl + ".equals(" + testClass + ".getJavaImplementation()));");
	    building.decrementNL("}");
	    
	    if (classData.isConcrete) {
	    	building.append("@Test");
	    	building.append("public final void testClassDefinitionCreateInstance() {");
	    	building.increment("");	
	    	building.append(modelInterface + " testValue = (" + modelInterface + ") " +
	    			testClass + ".createInstance();");
	    	building.append("assertNotNull(testValue);");
	    	building.decrementNL("}");
	    }
	    
	    building.append("@Test");
	    building.append("public final void testClassDefinitionUniqueIdentification() {");
	    building.increment("");	
	    PropertyData uniqueIdentifier = getUniqueIdentifier(classData);
	    if (uniqueIdentifier != null) {
	    	building.append("assertTrue(" + testClass + ".isUniquelyIdentified());");
	    	building.addImport("tv.amwa.maj.meta.PropertyDefinition");
	    	building.append("PropertyDefinition uniqueProperty = " + testClass + ".getUniqueIdentifierProperty();");
	    	building.append("assertNotNull(uniqueProperty);");
	    	building.append("assertEquals(\"" + uniqueIdentifier.name + "\", uniqueProperty.getName());");
	    }
	    else {
	    	building.append("assertFalse(" + testClass + ".isUniquelyIdentified());");
	    	building.append("assertNull(" + testClass + ".getUniqueIdentifierProperty());");
	    }
	    
	    building.decrementNL("}");
		
	}

	static void addPropertyAnnotationTests(
			StringIndenter building,
			PropertyData property,
			String testImpl) {
		
		building.addImport("tv.amwa.maj.industry.Forge");
		
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyAnnotationExists() {");
	    building.increment("");	
	    building.addImport("java.lang.reflect.Method");	
	    building.addImport("tv.amwa.maj.util.TestUtilities");
	    building.append("Method method1 = TestUtilities.propertyGetMethod(" + testImpl + ", \"" +
	    		property.name + "\");");
	    building.append("Method method2 = TestUtilities.propertyGetMethod(" + testImpl + ",");  
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertNotNull(method1);");
	    building.append("assertNotNull(method2);");
	    building.append("assertTrue(method1.equals(method2));");
		building.decrementNL("}");  
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyAnnotationName() {");
	    building.increment("");	
	    building.addImport("tv.amwa.maj.industry.MediaProperty");
	    building.append("MediaProperty property = TestUtilities.propertyAnnotation(" + testImpl + ",");  
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertNotNull(property);");
	    building.append("assertEquals(\"" + property.name + "\", property.definedName());");
		building.decrementNL("}");  
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyAnnotationIdentification() {");
	    building.increment("");	
	    building.append("MediaProperty property = TestUtilities.propertyAnnotation(" + testImpl + ", \"" +
	    		property.name + "\");");
	    building.append("assertNotNull(property);");
	    building.append("assertEquals(0x" + padHexTo8(property.identification.getData1()) + ", property.uuid1());");
	    building.append("assertEquals(0x" + padHexTo4(property.identification.getData2()) + ", property.uuid2());");
	    building.append("assertEquals(0x" + padHexTo4(property.identification.getData3()) + ", property.uuid3());");
	    building.append("assertTrue(Arrays.equals(");
	    building.append("        new byte[] { " + bytesToText(property.identification.getData4()) + " },");
	    building.append("         property.uuid4()));");
		building.decrementNL("}");  
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyAnnotationSymbol() {");
	    building.increment("");	
	    building.append("MediaProperty property = TestUtilities.propertyAnnotation(" + testImpl + ",");  
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertEquals(\"" + property.symbol + "\", property.symbol());");
		building.decrementNL("}");  
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyAnnotationOptional() {");
	    building.increment("");	
	    building.append("MediaProperty property = TestUtilities.propertyAnnotation(" + testImpl + ",");  
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    if (property.isOptional)
	    	building.append("assertTrue(property.optional());");
	    else
	    	building.append("assertFalse(property.optional());");
		building.decrementNL("}");  

	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyAnnotationUnique() {");
	    building.increment("");	
	    building.append("MediaProperty property = TestUtilities.propertyAnnotation(" + testImpl + ",");  
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    if (property.isUniqueIdentifier)
	    	building.append("assertTrue(property.uniqueIdentifier());");
	    else
	    	building.append("assertFalse(property.uniqueIdentifier());");
		building.decrementNL("}");  
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyAnnotationPID() {");
	    building.increment("");
	    building.append("MediaProperty property = TestUtilities.propertyAnnotation(" + testImpl + ",");  
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertEquals(0x" + padHexTo4(property.localIdentification) + ", property.pid());" +
	    		" // pid = " + property.localIdentification);
		building.decrementNL("}");  
	 
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyAnnotationTypeName() {");
	    building.increment("");	
	    building.append("MediaProperty property = TestUtilities.propertyAnnotation(" + testImpl + ",");  
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertEquals(\"" + property.type + "\", property.typeName());");
		building.decrementNL("}");  

	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyAnnotationCheckType() {");
	    building.increment("");	
	    building.append("MediaProperty property = TestUtilities.propertyAnnotation(" + testImpl + ",");  
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertNotNull(Warehouse.lookForType(property.typeName()));");
	    building.decrementNL("}");  
	}

	static void addPropertyDefinitionTests(
			StringIndenter building,
			ClassData classData, 
			PropertyData property, 
			String testClass,
			String testImpl, 
			DictionaryContext context) {
	
		building.addImport("tv.amwa.maj.exception.BadParameterException");
		building.addImport("tv.amwa.maj.industry.Forge");
		
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionExistsByName()"); 
	    building.append("throws NullPointerException, BadParameterException {");
	    building.increment("");	
	    building.append("assertNotNull(" + testClass + ".lookupPropertyDefinition(\"" + property.name + "\"));");
		building.decrementNL("}");
	    
		if (property.localIdentification != 0) {
		    building.append("@Test");
		    building.append("public final void test" + property.name + "PropertyDefinitionExistsByPID()"); 
		    building.append("    throws NullPointerException, BadParameterException {");
			building.increment("");	
		    building.append("assertNotNull(" + testClass + ".lookupPropertyDefinition((short) 0x" + 
		    		padHexTo4(property.localIdentification) + "));");
			building.decrementNL("}");
		}
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionExistsByIdentification()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
		building.increment("");	
	    building.append("assertNotNull(" + testClass + ".lookupPropertyDefinition(");
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " })));");
		building.decrementNL("}");

	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionExistsTheSame()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
		building.increment("");
		building.addImport("tv.amwa.maj.meta.PropertyDefinition");
	    building.append("PropertyDefinition testValue1 = " +
	    		testClass + ".lookupPropertyDefinition(\"" + property.name + "\");");
		if (property.localIdentification != 0) 
			building.append("PropertyDefinition testValue2 = " +
					testClass + ".lookupPropertyDefinition((short) 0x" + 
	    					padHexTo4(property.localIdentification) + ");");
	    building.append("PropertyDefinition testValue3 = " + testClass + ".lookupPropertyDefinition(");
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
		if (property.localIdentification != 0) 
			building.append("assertTrue(testValue1.equals(testValue2));");
	    building.append("assertTrue(testValue1.equals(testValue3));");
		if (property.localIdentification != 0) 
			building.append("assertTrue(testValue2.equals(testValue3));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionInGlobalTable()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
	    building.increment("");	
	    	
	    building.append("PropertyDefinition testValue1 = " + testClass + ".lookupPropertyDefinition(");
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertNotNull(testValue1);");
	    building.addImport("tv.amwa.maj.industry.Warehouse");
	    building.append("PropertyDefinition testValue2 = Warehouse.lookForProperty(");
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertNotNull(testValue2);");
	    building.append("assertTrue(testValue1.equals(testValue1));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionName()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
		building.increment("");	
	    building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(");
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertEquals(\"" + property.name + "\", property.getName());");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionSymbol()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
		building.increment("");	
	    building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(");
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertEquals(\"" + property.symbol + "\", property.getSymbol());");
		building.decrementNL("}");

	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionIdentification()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
	    building.increment("");	
		building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(\"" +
				property.name + "\");");
	    building.append("assertTrue(property.getAUID().equals(");
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " })));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionOptional()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
		building.increment("");	
	    building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(");
	    building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData2()) + 
	    		", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
	    building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    if (property.isOptional)
	    	building.append("assertTrue(property.getIsOptional());");
	    else
	    	building.append("assertFalse(property.getIsOptional());");
		building.decrementNL("}");

	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionUnique()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
		building.increment("");	
		building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(");
		building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
				", (short) 0x" + padHexTo4(property.identification.getData2()) + 
		    	", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
		building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    if (property.isUniqueIdentifier)
	    	building.append("assertTrue(property.getIsUniqueIdentifier());");
	    else
	    	building.append("assertFalse(property.getIsUniqueIdentifier());");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionPID()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
		building.increment("");	
		building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(");
		building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
				", (short) 0x" + padHexTo4(property.identification.getData2()) + 
		    	", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
		building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertEquals(0x" + padHexTo4(property.localIdentification) + 
	    		", property.getLocalIdentification());");
		building.decrementNL("}");
	   
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionType()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
		building.increment("");	
		building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(");
		building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
				", (short) 0x" + padHexTo4(property.identification.getData2()) + 
		    	", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
		building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
		building.addImport("tv.amwa.maj.meta.TypeDefinition");
	    building.append("TypeDefinition testType = Warehouse.lookForType(\"" + property.type + "\");");
	    building.append("assertNotNull(property.getTypeDefinition());");
	    building.append("assertTrue(testType.equals(property.getTypeDefinition()));");
		building.decrementNL("}");

	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionTypeID()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
	    building.increment("");	
		building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(");
		building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
				", (short) 0x" + padHexTo4(property.identification.getData2()) + 
		    	", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
		building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("TypeDefinition testType = Warehouse.lookForType(\"" + property.type + "\");");
	    building.append("assertNotNull(property.getPropertyType());");
	    building.append("assertTrue(testType.getAUID().equals(property.getPropertyType()));");
		building.decrementNL("}");

	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionMemberOf()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
		building.increment("");	
		building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(");
		building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
				", (short) 0x" + padHexTo4(property.identification.getData2()) + 
		    	", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
		building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertNotNull(property.getMemberOf());");
	    building.append("assertTrue(" + testClass + ".equals(property.getMemberOf()));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionSymbolSpace()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
	    building.increment("");	
		building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(");
		building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
				", (short) 0x" + padHexTo4(property.identification.getData2()) + 
		    	", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
		building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
	    building.append("assertEquals(\"" + context.schemeURI + "\", property.getNamespace());");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void test" + property.name + "PropertyDefinitionPrefix()"); 
	    building.append("    throws NullPointerException, BadParameterException {");
		building.increment("");	
		building.append("PropertyDefinition property = " + testClass + ".lookupPropertyDefinition(");
		building.append("        Forge.makeAUID(0x" + padHexTo8(property.identification.getData1()) + 
				", (short) 0x" + padHexTo4(property.identification.getData2()) + 
		    	", (short) 0x" + padHexTo4(property.identification.getData3()) + ",");
		building.append("                new byte[] { " + bytesToText(property.identification.getData4()) + " }));");
		building.append("assertEquals(\"" + context.preferredPrefix + "\", property.getPrefix());");
		building.decrementNL("}");
	}

	static List<String> defaultValues = Arrays.asList(new String[] {
		"UserDataMode", "AuxBitsMode", "BlockStartOffset", "ChannelStatusMode", "FixedUserData", "FixedChannelStatusData",
		"ChannelAssignment", "Authentication", "SoftwareOnly", "Accelerator", "DescribedTrackIDs", 
		"DescriptiveClipDescribedTrackIDs", "Emphasis", "IsTimeWarp", "ObjectModelVersion", 
		"KLVDataType", "LeadingLines", "TrailingLines", "JPEGTableID",
		"ComponentMaxRef", "ComponentMinRef", "AlphaMaxRef", "AlphaMinRef", "ScanningDirection", "ImageStartOffset",
		"ImageAlignmentFactor", "ImageEndOffset", "AlphaTransparency", "DisplayF2Offset", "StoredF2Offset",
		"AlphaSampleDepth", "ColorSiting", "VerticalSubsampling", "BlackRefLevel", "ColorRange", 
		"WhiteRefLevel", "ReversedByteOrder", "PaddingBits"
	});
	static List<String> conditionalValues = Arrays.asList(new String[] {
		"ComponentLength", 
		"SampledHeight", "SampledWidth", "SampledXOffset", "SampledYOffset", 
		"DisplayHeight", "DisplayWidth", "DisplayXOffset", "DisplayYOffset",
		"WhiteRefLevel", "SubDescriptors", "DescriptiveFrameworkObject", "ImplementedClass",
		"ColorRange", "BlackRefLevel"
	});
	
	static void addPropertyValueTests(
			StringIndenter building,
			PropertyData property,
			String testValue,
			String modelInterface) 
		throws BadParameterException {
		
		List<String> defaultAndConditional = new ArrayList<String>(defaultValues.size() + conditionalValues.size());
		defaultAndConditional.addAll(defaultValues);
		defaultAndConditional.addAll(conditionalValues);
		
		PropertyDefinitionImpl propertyDefinition = 
			(PropertyDefinitionImpl) Warehouse.lookForClass(property.memberOf).lookupPropertyDefinition(property.name);
		
		if ((property.isOptional) && ((!building.getPackageName().startsWith("tv.amwa.maj")) ||  
				((building.getPackageName().startsWith("tv.amwa.maj")) && (!defaultAndConditional.contains(property.name))))) {
			building.addImport("tv.amwa.maj.exception.PropertyNotPresentException");
		    building.append("@Test(expected=PropertyNotPresentException.class)");
		    building.append("public final void test" + property.name + "SimpleOptionalDirect() {");
		    building.increment("");
		    building.append(testValue + "." + propertyDefinition.getMethodBag().getGetterName() + "();");
		    building.decrementNL("}");
		    
		    building.append("@Test(expected=PropertyNotPresentException.class)");
		    building.append("public final void test" + property.name + "SimpleOptionalEngine() {");
		    building.increment("");
		    building.append("MediaEngine.getPropertyValue(" + testValue + ", \"" + property.name + "\");");
		    building.decrementNL("}");
		    
		    building.append("@Test");
		    building.append("public final void test" + property.name + "SimpleOptionalCheck() {");
		    building.increment("");
		    building.append("assertFalse(MediaEngine.isPropertyPresent(" + testValue + ", \"" + property.name + "\"));");
		    building.decrementNL("}");
		    
		    building.append("@Test");
		    building.append("public final void test" + property.name + "CompleteOptionalDirect() {");
		    building.increment("");
		    building.append("assertNotNull(makeComplete" + modelInterface + "1()." + propertyDefinition.getMethodBag().getGetterName() + "());");
		    building.decrementNL("}");

		    building.addImport("tv.amwa.maj.industry.PropertyValue");
		    building.append("@Test");
		    building.append("public final void test" + property.name + "CompleteOptionalEngineWrapped() {");
		    building.increment("");
		    building.append("PropertyValue testValue =");
		    building.append("    MediaEngine.getWrappedPropertyValue(makeComplete" + modelInterface + "1(), \"" + property.name + "\");");
		    building.append("assertNotNull(testValue);");
		    building.append("assertNotNull(testValue.getType());");
		    building.append("assertNotNull(testValue.getValue());");
		    building.append("assertEquals(\"" + property.type + "\", testValue.getType().getName());");
		    building.decrementNL("}");

		    building.append("@Test");
		    building.append("public final void test" + property.name + "CompleteOptionalEngine() {");
		    building.increment("");
		    building.append("assertNotNull(MediaEngine.getPropertyValue(makeComplete" + modelInterface + "1(), \"" + property.name + "\"));");
		    building.decrementNL("}");

		    building.append("@Test");
		    building.append("public final void test" + property.name + "CompleteOptionalCheck() {");
		    building.increment("");
		    building.append("assertTrue(MediaEngine.isPropertyPresent(makeComplete" + modelInterface + "1(), \"" + property.name + "\"));");
		    building.decrementNL("}");
		}
		else {
			if (!conditionalValues.contains(property.name)) {
			    building.append("@Test");
			    building.append("public final void test" + property.name + "SimplePresentDirect() {");
			    building.increment("");
			    building.append("assertNotNull(" + testValue + "." + propertyDefinition.getMethodBag().getGetterName() + "());");
			    building.decrementNL("}");
			    
			    building.append("@Test");
			    building.append("public final void test" + property.name + "SimplePresentEngine() {");
			    building.increment("");
			    building.append("assertNotNull(MediaEngine.getPropertyValue(" + testValue + ", \"" + property.name + "\"));");
			    building.decrementNL("}");
			
			    building.append("@Test");
			    building.append("public final void test" + property.name + "SimplePresentCheck() {");
			    building.increment("");
			    building.append("assertTrue(MediaEngine.isPropertyPresent(" + testValue + ", \"" + property.name + "\"));");
			    building.decrementNL("}");
			    
			    building.append("@Test");
			    building.append("public final void test" + property.name + "CompletePresentDirect() {");
			    building.increment("");
			    building.append("assertNotNull(makeComplete" + modelInterface + "1()." + propertyDefinition.getMethodBag().getGetterName() + "());");
			    building.decrementNL("}");
			    
			    building.addImport("tv.amwa.maj.industry.PropertyValue");
			    building.append("@Test");
			    building.append("public final void test" + property.name + "CompletePresentEngineWrapped() {");
			    building.increment("");
			    building.append("PropertyValue testValue =");
			    building.append("    MediaEngine.getWrappedPropertyValue(makeComplete" + modelInterface + "1(), \"" + property.name + "\");");
			    building.append("assertNotNull(testValue);");
			    building.append("assertNotNull(testValue.getType());");
			    building.append("assertNotNull(testValue.getValue());");
			    building.append("assertEquals(\"" + property.type + "\", testValue.getType().getName());");
			    building.decrementNL("}");
			    
			    building.append("@Test");
			    building.append("public final void test" + property.name + "CompletePresentEngine() {");
			    building.increment("");
			    building.append("assertNotNull(MediaEngine.getPropertyValue(makeComplete" + modelInterface + "1(), \"" + property.name + "\"));");
			    building.decrementNL("}");
			    
			    building.append("@Test");
			    building.append("public final void test" + property.name + "CompletePresentCheck() {");
			    building.increment("");
			    building.append("assertTrue(MediaEngine.isPropertyPresent(makeComplete" + modelInterface + "1(), \"" + property.name + "\"));");
			    building.decrementNL("}");
			}
		}  

		if ((!property.name.equals("SubDescriptors")) && (!property.name.equals("DescriptiveFrameworkObject"))) {
		    building.append("@Test");
		    building.append("public final void testGet" + property.name + "Same() {");
		    building.increment("");
		    building.append("Object testValue1 = makeComplete" + modelInterface + "1()." + propertyDefinition.getMethodBag().getGetterName() + "();");
		    building.append("assertNotNull(testValue1);");
		    building.append("Object testValue2 =");
		    building.append("        MediaEngine.getPropertyValue(makeComplete" + modelInterface + "1(), \"" + property.name + "\");");
		    building.append("assertNotNull(testValue2);");	
		    building.addImport("tv.amwa.maj.util.TestUtilities");
		    building.append("assertTrue(TestUtilities.objectEqualityTest(testValue1, testValue2));");
		    building.decrementNL("}");  
		   
		    if ((property.isOptional) && 
		    		(! ((modelInterface.equals("Preface")) && 
		    				(property.name.equals("LinkedGenerationID"))) ) ) {
			    building.append("@Test");
			    building.append("public final void test" + property.name + "CompleteOptionalCanOmit() {");
			    building.increment("");
			    building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			    building.append("assertTrue(MediaEngine.isPropertyPresent(testValue, \"" + property.name + "\"));");
			    building.append("MediaEngine.omitOptionalProperty(testValue, \"" + property.name + "\");");
			    if (!defaultAndConditional.contains(property.name)) 
			    	building.append("assertFalse(MediaEngine.isPropertyPresent(testValue, \"" + property.name + "\"));");
			    building.decrementNL("}");
		    }
		}		

		if ((!property.name.equals("ObjectClass")) && (!property.name.equals("SubDescriptors")) &&
				(!property.name.equals("DescriptiveFrameworkObject"))) {
			
			if (! ((modelInterface.equals("Preface")) && (property.name.equals("LinkedGenerationID"))) ) {
			    building.append("@Test");
			    building.append("public final void test" + property.name + "SetAndGetEngine() {");
			    building.increment("");	
			    building.append(modelInterface + " testValue1 = makeComplete" + modelInterface + "1();");
			    building.append(modelInterface + " testValue2 = makeComplete" + modelInterface + "2();");
			    building.append("");	
//		    building.append("Object testProperty1 = MediaEngine.getPropertyValue(testValue1, \"" + property.name + "\");");
			    building.append("Object testProperty2 = MediaEngine.getPropertyValue(testValue2, \"" + property.name + "\");");
//		    building.append("assertFalse(testProperty1.equals(testProperty2));");
			    building.append("");
			    building.append("MediaEngine.setPropertyValue(testValue1, \"" + property.name + "\", testProperty2);");
			    building.append("Object testProperty1 = MediaEngine.getPropertyValue(testValue1, \"" + property.name + "\");");
			    building.addImport("tv.amwa.maj.util.TestUtilities");
			    building.append("assertTrue(TestUtilities.objectEqualityTest(testProperty1, testProperty2));");
			    building.decrementNL("}");
			}

		    TypeData typeData = typeByName(property.type);
		    building.addImport(typeData.getImports());	    
		    switch (typeData.getTypeCategory()) {
		    case VariableArray:
		    	addPropertyVariableArrayTests(building, property, typeData, testValue, modelInterface, propertyDefinition);
		    	break;
		    case Set:
		    	addPropertySetTests(building, property, typeData, testValue, modelInterface, propertyDefinition);
		    	break;
		    	// TODO add stream-specific tests here
		    default:
		    	addPropertyDefaultTests(building, property, typeData, testValue, modelInterface, propertyDefinition);
		    	break;
		    }
		}
		
	}
	
	static List<String> initializeOnly = Arrays.asList(new String[] {
			"DefinitionObjectIdentification", "MetaDefinitionIdentification", "Tag",
			"ElementType", "GenerationID", "ToolkitVersion", "ElementCount", "StringElementType",
			"SetElementType", "ParentClass", "IsConcrete", "Interpolation", "LocalIdentification",
			"IsUniqueIdentifier", "PropertyType", "IsOptional", "ObjectModelVersion", "Dictionaries",
			"ReferencedType", "RenamedType", "VariableArrayElementType", "ParameterType",
			"FixedArrayElementType", "WeakReferencedType", "LinkedPackageID", "ChunkLength",
			"ContentStorageObject", "FormatVersion", "ByteOrder", "ApplicationVersionString",
			"ApplicationProductID", "ApplicationPlatform", "FileModificationDate",
			"ApplicationSupplierName", "ApplicationName", "ParameterDefinitionReference"
		});
	// TODO add tests for setting these with the union types
	static List<String> unionTypeParameters = Arrays.asList(new String[] {
			"DefaultFadeLength", "DefaultFadeEditUnit", "DefaultFadeType", "FadeInLength",
			"FadeOutLength", "FadeInType", "FadeOutType", "StoredWidth", "StoredHeight",
			"FramesPerSecond", "DropFrame", "StartTimecode", "EdgeCodeFormat", 
			"EdgeCodeFilmFormat", "EdgeCodeStart", 
	});
	
	static void addPropertyDefaultTests(
			StringIndenter building,
			PropertyData property, 
			TypeData typeData,
			String testValue, 
			String modelInterface,
			PropertyDefinitionImpl propertyDefinition) {

		if (building.getPackageName().startsWith("tv.amwa.maj")) {
			if (conditionalValues.contains(property.name)) return;
			if (initializeOnly.contains(property.name)) return;
			if (unionTypeParameters.contains(property.name)) return;
			if ((property.memberOf.equals("TypeDefinitionInteger")) &&
					((property.name.equals("Size")) || (property.name.equals("IsSigned")))) return;
			if ((property.memberOf.equals("SourceClip")) &&
					(property.name.equals("StartPosition"))) return;
		}
		
		SingletonMethodBag methods = (SingletonMethodBag) propertyDefinition.getMethodBag();

		Class<?> iface = findInterface(building.getPackageName(), property);
		if (iface == null) return;
		
		Method setter = findMethodForName(iface, methods.getSetterName());
		if (setter == null) {
			System.out.println("Annotated set method " + methods.getSetterName() + "() for property " + property.memberOf + "." + property.name +
					" is not available in interface " + iface.getCanonicalName() + ".");
			setter = findMethodForName(iface, "set" + property.name);
		}
		if (setter == null) {
			System.out.println("    Alternative method set" + property.name+ "() is also not available.");
			for ( String alias : propertyDefinition.getAliases() ) {
				setter = findMethodForName(iface, "set" + alias);
				if (setter != null) {
					System.out.println("    Trying to set " + property.name + " with alias name " + setter.getName() + ".");
					break;
				}
			}
		}
		if (setter == null) {
			System.out.println("    No public interface for setting property " + property.name + " has been found.");
			return;
		}	
		
		if ((typeData.getTypeCategory() == TypeCategory.Enum) &&
				(building.getPackageName().startsWith("tv.amwa.maj")) &&
				(!typeData.name.equals("Boolean")))
			building.addImport("tv.amwa.maj.enumeration." + typeData.name);
		
		if (!((modelInterface.equals("Preface")) && (property.name.equals("LinkedGenerationID"))) ) {
			building.append("@Test");
		    building.append("public final void test" + property.name + "SetAndGetDirect() {");
		    building.increment("");
		    building.append(modelInterface + " testValue1 = makeComplete" + modelInterface + "1();");
		    building.append(modelInterface + " testValue2 = makeComplete" + modelInterface + "2();");
		    building.append("");
		    building.addImport(typeData.getImports());
		    building.append(typeData.getJavaSetName(true) + " testProperty2 = testValue2." +
		    		methods.getGetterName() + "();");
	    	building.append("testValue1." + setter.getName() + "(testProperty2);");
		    building.append(typeData.getJavaSetName(true) + " testProperty1 = testValue1." +
		    		methods.getGetterName() + "();");
		    building.addImport("tv.amwa.maj.util.TestUtilities");
	    	building.append("assertTrue(TestUtilities.objectEqualityTest(testProperty1, testProperty2));");
	    	building.decrementNL("}");
	    	
	    	building.addImport("tv.amwa.maj.exception.PropertyNotPresentException");
	    	if (!setter.getParameterTypes()[0].isPrimitive()) {
		    		if (property.isOptional)
		    			building.append("@Test");
		    		else
		    			building.append("@Test(expected=NullPointerException.class)");
		    	building.append("public final void test" + property.name + "DirectNull() {");
		    	building.increment("");
		    	building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
		    	building.append("try {");
		    	building.increment("testValue." + methods.getGetterName() + "();");
		    	building.decrement("}");
		    	building.append("catch (PropertyNotPresentException pnpe) {");
		    	building.increment("fail(\"Property " + property.name + " should be present for a complete " + modelInterface + ".\");");
		    	building.decrement("}");
		    	building.append("testValue." + setter.getName() + "(null);");
		    	if (!defaultValues.contains(property.name)) {
		    		building.append("try {");
		    	   	building.increment("testValue." + methods.getGetterName() + "();");
		           	building.append("fail(\"Property " + property.name + " should be omitted after a null set call for " + modelInterface + ".\");");
		            building.decrement("}");
		        	building.append("catch (PropertyNotPresentException pnpe) {");
		        	building.append("}");
		    	}
		    	building.decrementNL("}");
	    	}
	    }
	}
	
	static Class<?> findInterface(
			String packageName,
			PropertyData property) {
		
		Class<?> iface = null;
		
		try {
			if (packageName.startsWith("tv.amwa.maj")) {
				if (classList.get(property.memberOf).isInterchangeable())
					iface = Class.forName("tv.amwa.maj.model." + Warehouse.javaClassAlias(property.memberOf));
				else
					iface = Class.forName("tv.amwa.maj.meta." + Warehouse.javaClassAlias(property.memberOf));
			}
			else
				iface = Class.forName(packageName + "." + property.memberOf);
		}
		catch (Exception e) { 
			System.out.println("Unabled to generate property default test for " + property.memberOf + "." + property.name +
					" due to a " + e.getClass().getName() + ": " + e.getMessage());
			return null; 
		}
		return iface;
	}

	static Method findMethodForName(
			Class<?> iface, 
			String name) {

		Method[] methods = iface.getMethods();
		for ( Method method : methods) 
			if (method.getName().equals(name)) return method;
		
		return null;
	}

	static void addPropertySetTests(
			StringIndenter building,
			PropertyData property, 
			TypeData typeData,
			String testValue, 
			String modelInterface,
			PropertyDefinitionImpl propertyDefinition) {
		
		SetMethodBag methods = (SetMethodBag) propertyDefinition.getMethodBag();
		
		Class<?> iface = findInterface(building.getPackageName(), property);
		if (iface == null) return;

		TypeData elementType = typeByName(((TypeDataSet) typeData).elementType); 
		System.out.println("*** SET *** " + typeData.name + " with elements of type " + elementType.name + ".");
		
		Method add = findMethodForName(iface, methods.getAddName());
		if (add == null) {
			System.out.println("No public add method for set " + property.memberOf + "." + property.name + " in interface " + iface.getCanonicalName() + ".");
			return;
		}
		
		Method count = findMethodForName(iface, methods.getCountName());
		if (count == null) {
			System.out.println("No public count method for set " + property.memberOf + "." + property.name + " in interface " + iface.getCanonicalName() + ".");
		}
 			
		building.append("@Test");
		building.append("public final void test" + property.name + "DirectAdd() {");
		building.increment("");
		building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
		if (count != null)
			building.append("assertEquals(1, testValue." + count.getName() + "());");
		switch (elementType.getTypeCategory()) {
		case StrongObjRef:
		case WeakObjRef:
			building.addImport(elementType.getImports());
			building.append(elementType.getJavaSetName(true) + " testElement = " +
					elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
					elementType.getJavaSetName(true) + "2();");
			break;
		case Record:
			if (elementType.name.equals("AUID")) {
				building.addImport("tv.amwa.maj.record.AUID");
				building.append("AUID testElement = Forge.randomAUID();");
				break;
			}
			System.out.println("Need to find a way to test set elements for " + typeData.name + ".");
			building.decrement("}");
			return;
		case Int:
			// TODO better support other types other than UInt32
			building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
			break;
		default:
			System.out.println("Need to find a way to test set elements for " + typeData.name + ".");
			building.decrement("}");
			return;
		}
		building.append("testValue." + add.getName() + "(testElement);");
		if (count != null)
			building.append("assertEquals(2, testValue." + count.getName() + "());");
		building.decrementNL("}");
		
		if (!add.getParameterTypes()[0].isPrimitive()) {
			building.append("@Test(expected=NullPointerException.class)");
			building.append("public final void test" + property.name + "DirectAddNull() {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			building.append("testValue." + add.getName() + "(null);");
			building.decrementNL("}");
		}
		
		Method clear = findMethodForName(iface, methods.getClearName());
		if (clear == null) {
			if (property.isOptional == true)
				System.out.println("No public clear method for set " + property.memberOf + "." +
						property.name + " in interface " + iface.getCanonicalName());
		}
		else {
			building.append("@Test");
			building.append("public final void test" + property.name + "SetClear() {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			building.append("testValue." + clear.getName() + "();");
			if (count != null)
				building.append("assertEquals(0, testValue." + count.getName() + "());");
			building.decrementNL("}");
			
			if ((property.isOptional) && (!property.name.equals("DescriptiveClipDescribedTrackIDs")))
				building.append("@Test(expected=PropertyNotPresentException.class)");
			else
				building.append("@Test");
			building.append("public final void test" + property.name + "SetClearChanged() {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			building.append("Object testSet = testValue." + methods.getGetterName() + "();");
			building.append("assertNotNull(testSet);");
			building.append("testValue." + clear.getName() + "();");
			building.append("assertFalse(testValue == testValue." + methods.getGetterName() + "());");
			building.decrementNL("}");
		}

		Method contains = findMethodForName(iface, methods.getContainsName());
		if (contains == null) {
			System.out.println("No public contains methods for " + property.memberOf + "." + property.name + 
					" in interface " + iface.getCanonicalName());
		}
		else {
			building.append("@Test");
			building.append("public final void test" + property.name + "DirectContains() {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			building.append("Object testElement = null;");
			building.append("for ( Object setTest : testValue." + methods.getGetterName() + "() ) testElement = setTest;");
			building.append("assertTrue(testValue." + methods.getContainsName() + 
					"((" + typeData.getJavaSetName(true) + ") testElement));");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			building.decrementNL("}");
		}
		
		Method remove = findMethodForName(iface, methods.getRemoveName());
		if (remove == null) {
			System.out.println("No public remove method for property " + property.memberOf + "." + property.name + 
					" for interface " + iface.getCanonicalName());
		}
		else {
			building.append("@Test");
			// TODO don't let removing essence data cause an exception
			building.append("public final void test" + property.name + "DirectRemove() throws Exception {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			building.append("Object testElement = null;");
			building.append("for ( Object fromSet : testValue." + methods.getGetterName() + "() ) testElement = fromSet;");
			if (contains != null)
				building.append("assertTrue(testValue." + methods.getContainsName() + 
						"((" + typeData.getJavaSetName(true) + ") testElement));");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			building.append("testValue." + methods.getRemoveName() + 
					"((" + typeData.getJavaSetName(true) + ") testElement);");
			if (count != null)
				building.append("assertEquals(0, testValue." + count.getName() + "());");
			if (contains != null) {
				building.append("assertFalse(testValue." + methods.getContainsName() + 
						"((" + typeData.getJavaSetName(true) + ") testElement));");					
			}
			building.decrementNL("}");
		}

	}

	static void addPropertyVariableArrayTests(
			StringIndenter building,
			PropertyData property, 
			TypeData typeData,
			String testValue, 
			String modelInterface,
			PropertyDefinitionImpl propertyDefinition) {

		VariableArrayMethodBag methods = (VariableArrayMethodBag) propertyDefinition.getMethodBag();
		
		Class<?> iface = findInterface(building.getPackageName(), property);
		if (iface == null) return;

		TypeData elementType = typeByName(((TypeDataVariableArray) typeData).elementType); 
		System.out.println("*** VARIABLE ARRAY *** " + typeData.name + " with elements of type " + elementType.name + ".");
		
		Method append = findMethodForName(iface, methods.getAppendName());
		if (append == null) {
			System.out.println("No public append method for array " + property.memberOf + "." + property.name + " in interface " + iface.getCanonicalName() + ".");
			return;
		}
		
		Method count = findMethodForName(iface, methods.getCountName());
		if (count == null) {
			System.out.println("No public count method for array " + property.memberOf + "." + property.name + " in interface " + iface.getCanonicalName() + ".");
		}
 			
		building.append("@Test");
		building.append("public final void test" + property.name + "DirectAppend() throws Exception {");
		building.increment("");
		building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
		if (count != null)
			building.append("assertEquals(1, testValue." + count.getName() + "());");
		switch (elementType.getTypeCategory()) {
		case StrongObjRef:
		case WeakObjRef:
			building.addImport(elementType.getImports());
			building.append(elementType.getJavaSetName(true) + " testElement = " +
					elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
					elementType.getJavaSetName(true) + "2();");
			building.append("testValue." + append.getName() + "(testElement);");
			if (count != null)
				building.append("assertEquals(2, testValue." + count.getName() + "());");
			break;
		case Record:
			if (elementType.name.equals("AUID")) {
				building.addImport("tv.amwa.maj.record.AUID");
				building.append("AUID testElement = Forge.randomAUID();");
				building.append("testValue." + append.getName() + "(testElement);");
				if (count != null)
					building.append("assertEquals(2, testValue." + count.getName() + "());");
				break;
			}
			System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
			break;
		case Int:
			// TODO better support other types other than UInt32
			building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
			building.append("testValue." + append.getName() + "(testElement);");
			if (count != null)
				building.append("assertEquals(2, testValue." + count.getName() + "());");
			break;
		default:
			System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
			break;
		}
		building.decrementNL("}");

		if (!append.getParameterTypes()[0].isPrimitive()) {
			building.append("@Test(expected=NullPointerException.class)");
			building.append("public final void test" + property.name + "DirectAppendNull() throws Exception {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			building.append("testValue." + append.getName() + "(null);");
			building.decrement("}");
		}
		
		Method clear = findMethodForName(iface, methods.getClearName());
		if (clear == null) {
			if (property.isOptional == true)
				System.out.println("No public clear method for array " + property.memberOf + "." +
						property.name + " in interface " + iface.getCanonicalName());
		}
		else {
			building.append("@Test");
			building.append("public final void test" + property.name + "ArrayClear() {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			building.append("testValue." + clear.getName() + "();");
			if (count != null)
				building.append("assertEquals(0, testValue." + count.getName() + "());");
			building.decrementNL("}");
			
			if (property.isOptional) {
				building.append("@Test");
				building.append("public final void test" + property.name + "ArrayClearNotPresent() {");
				building.increment("");
				building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
				building.append("Object testSet = testValue." + methods.getGetterName() + "();");
				building.append("assertNotNull(testSet);");
				building.append("testValue." + clear.getName() + "();");
				building.append("try {");
				building.increment("testValue." + methods.getGetterName() + "();");
				building.append("fail(\"Should have thrown property not present.\");");
				building.decrement("}");
				building.append("catch (PropertyNotPresentException pnpe) { }");
				building.decrementNL("}");
			}
		}

		Method prepend = findMethodForName(iface, methods.getPrependName());
		if (prepend == null) {
			System.out.println("No public prepend method for array " + property.memberOf + "." + property.name + 
					" in interface " + iface.getCanonicalName());
		}
		else {
			building.append("@Test");
			building.append("public final void test" + property.name + "DirectPrepend() throws Exception {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			switch (elementType.getTypeCategory()) {
			case StrongObjRef:
			case WeakObjRef:
				building.addImport(elementType.getImports());
				building.append(elementType.getJavaSetName(true) + " testElement = " +
						elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
						elementType.getJavaSetName(true) + "2();");
				building.append("testValue." + prepend.getName() + "(testElement);");
				if (count != null)
					building.append("assertEquals(2, testValue." + count.getName() + "());");
				break;
			case Record:
				if (elementType.name.equals("AUID")) {
					building.addImport("tv.amwa.maj.record.AUID");
					building.append("AUID testElement = Forge.randomAUID();");
					building.append("testValue." + prepend.getName() + "(testElement);");
					if (count != null)
						building.append("assertEquals(2, testValue." + count.getName() + "());");
					break;
				}
				System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
				break;
			case Int:
				// TODO better support other types other than UInt32
				building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
				building.append("testValue." + prepend.getName() + "(testElement);");
				if (count != null)
					building.append("assertEquals(2, testValue." + count.getName() + "());");
				break;
			default:
				System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
				break;
			}
			building.decrementNL("}");

			if (!prepend.getParameterTypes()[0].isPrimitive()) {
				building.append("@Test(expected=NullPointerException.class)");
				building.append("public final void test" + property.name + "DirectPrependNull() throws Exception {");
				building.increment("");
				building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
				building.append("testValue." + prepend.getName() + "(null);");
				building.decrement("}");
			}
		}

		Method insertAt = findMethodForName(iface, methods.getInsertAtName());
		if (insertAt == null) {
			System.out.println("No public insert-at method for array " + property.memberOf + "." + property.name + 
					" in interface " + iface.getCanonicalName());
		}
		else {
			building.append("@Test");
			building.append("public final void test" + property.name + "DirectInsertAtBegin() throws Exception {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			switch (elementType.getTypeCategory()) {
			case StrongObjRef:
			case WeakObjRef:
				building.addImport(elementType.getImports());
				building.append(elementType.getJavaSetName(true) + " testElement = " +
						elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
						elementType.getJavaSetName(true) + "2();");
				building.append("testValue." + insertAt.getName() + "(0, testElement);");
				if (count != null)
					building.append("assertEquals(2, testValue." + count.getName() + "());");
				break;
			case Record:
				if (elementType.name.equals("AUID")) {
					building.addImport("tv.amwa.maj.record.AUID");
					building.append("AUID testElement = Forge.randomAUID();");
					building.append("testValue." + insertAt.getName() + "(0, testElement);");
					if (count != null)
						building.append("assertEquals(2, testValue." + count.getName() + "());");
					break;
				}
				System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
				break;
			case Int:
				// TODO better support other types other than UInt32
				building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
				building.append("testValue." + insertAt.getName() + "(0, testElement);");
				if (count != null)
					building.append("assertEquals(2, testValue." + count.getName() + "());");
				break;
			default:
				System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
				break;
			}
			building.decrementNL("}");
		
			building.append("@Test");
			building.append("public final void test" + property.name + "DirectInsertAtEnd() throws Exception {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			switch (elementType.getTypeCategory()) {
			case StrongObjRef:
			case WeakObjRef:
				building.addImport(elementType.getImports());
				building.append(elementType.getJavaSetName(true) + " testElement = " +
						elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
						elementType.getJavaSetName(true) + "2();");
				building.append("testValue." + insertAt.getName() + "(1, testElement);");
				if (count != null)
					building.append("assertEquals(2, testValue." + count.getName() + "());");
				break;
			case Record:
				if (elementType.name.equals("AUID")) {
					building.addImport("tv.amwa.maj.record.AUID");
					building.append("AUID testElement = Forge.randomAUID();");
					building.append("testValue." + insertAt.getName() + "(1, testElement);");
					if (count != null)
						building.append("assertEquals(2, testValue." + count.getName() + "());");
					break;
				}
				System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
				break;
			case Int:
				// TODO better support other types other than UInt32
				building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
				building.append("testValue." + insertAt.getName() + "(1, testElement);");
				if (count != null)
					building.append("assertEquals(2, testValue." + count.getName() + "());");
				break;
			default:
				System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
				break;
			}
			building.decrementNL("}");
		
			building.append("@Test(expected=IndexOutOfBoundsException.class)");
			building.append("public final void test" + property.name + "InsertAtBadIndexBig() throws Exception {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			switch (elementType.getTypeCategory()) {
			case StrongObjRef:
			case WeakObjRef:
				building.addImport(elementType.getImports());
				building.append(elementType.getJavaSetName(true) + " testElement = " +
						elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
						elementType.getJavaSetName(true) + "2();");
				building.append("testValue." + insertAt.getName() + "(2, testElement);");
				break;
			case Record:
				if (elementType.name.equals("AUID")) {
					building.addImport("tv.amwa.maj.record.AUID");
					building.append("AUID testElement = Forge.randomAUID();");
					building.append("testValue." + insertAt.getName() + "(2, testElement);");
					break;
				}
				System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
				break;
			case Int:
				// TODO better support other types other than UInt32
				building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
				building.append("testValue." + insertAt.getName() + "(2, testElement);");
				break;
			default:
				System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
				break;
			}
			building.decrementNL("}");

			building.append("@Test(expected=IndexOutOfBoundsException.class)");
			building.append("public final void test" + property.name + "InsertAtBadIndexSmall() throws Exception {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			switch (elementType.getTypeCategory()) {
			case StrongObjRef:
			case WeakObjRef:
				building.addImport(elementType.getImports());
				building.append(elementType.getJavaSetName(true) + " testElement = " +
						elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
						elementType.getJavaSetName(true) + "2();");
				building.append("testValue." + insertAt.getName() + "(-1, testElement);");
				break;
			case Record:
				if (elementType.name.equals("AUID")) {
					building.addImport("tv.amwa.maj.record.AUID");
					building.append("AUID testElement = Forge.randomAUID();");
					building.append("testValue." + insertAt.getName() + "(-1, testElement);");
					break;
				}
				System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
				break;
			case Int:
				// TODO better support other types other than UInt32
				building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
				building.append("testValue." + insertAt.getName() + "(-1, testElement);");
				break;
			default:
				System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
				break;
			}
			building.decrementNL("}");
			
			if (!insertAt.getParameterTypes()[1].isPrimitive()) {
				building.append("@Test(expected=NullPointerException.class)");
				building.append("public final void test" + property.name + "DirectInsertAtNull2() throws Exception {");
				building.increment("");
				building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
				building.append("testValue." + insertAt.getName() + "(0, null);");
				building.decrement("}");
			}
		}
		
		Method getAt = findMethodForName(iface, methods.getGetAtName());
		if (getAt == null) {
			System.out.println("No public get-at methods for array " + property.memberOf + "." + property.name + 
					" in interface " + iface.getCanonicalName());
		}
		else {
			building.append("@Test");
			building.append("public final void test" + property.name + "DirectGetAtAppend() throws Exception {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			if (count != null)
				building.append("assertEquals(1, testValue." + count.getName() + "());");
			switch (elementType.getTypeCategory()) {
			case StrongObjRef:
			case WeakObjRef:
				building.addImport(elementType.getImports());
				building.append(elementType.getJavaSetName(true) + " testElement = " +
						elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
						elementType.getJavaSetName(true) + "2();");
				building.append("testValue." + append.getName() + "(testElement);");
				if (count != null)
					building.append("assertEquals(2, testValue." + count.getName() + "());");
				building.append("assertTrue(testElement.equals(testValue." + getAt.getName() + "(1)));");
				break;
			case Record:
				if (elementType.name.equals("AUID")) {
					building.addImport("tv.amwa.maj.record.AUID");
					building.append("AUID testElement = Forge.randomAUID();");
					building.append("testValue." + append.getName() + "(testElement);");
					if (count != null)
						building.append("assertEquals(2, testValue." + count.getName() + "());");
					building.append("assertTrue(testElement.equals(testValue." + getAt.getName() + "(1)));");
					break;
				}
				System.out.println("Need to find a way to test set elements for " + typeData.name + ".");
				break;
			case Int:
				// TODO better support other types other than UInt32
				building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
				building.append("testValue." + append.getName() + "(testElement);");
				if (count != null)
					building.append("assertEquals(2, testValue." + count.getName() + "());");
				building.append("assertTrue(testElement == testValue." + getAt.getName() + "(1)));");
				break;
			default:
				System.out.println("Need to find a way to test set elements for " + typeData.name + ".");
				return;
			}
			building.decrementNL("}");
			
			if (prepend != null) {
				building.append("@Test");
				building.append("public final void test" + property.name + "GetAtPrepend() throws Exception {");
				building.increment("");
				building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
				if (count != null)
					building.append("assertEquals(1, testValue." + count.getName() + "());");
				switch (elementType.getTypeCategory()) {
				case StrongObjRef:
				case WeakObjRef:
					building.addImport(elementType.getImports());
					building.append(elementType.getJavaSetName(true) + " testElement = " +
							elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
							elementType.getJavaSetName(true) + "2();");
					building.append("testValue." + prepend.getName() + "(testElement);");
					if (count != null)
						building.append("assertEquals(2, testValue." + count.getName() + "());");
					building.append("assertTrue(testElement.equals(testValue." + getAt.getName() + "(0)));");
					break;
				case Record:
					if (elementType.name.equals("AUID")) {
						building.addImport("tv.amwa.maj.record.AUID");
						building.append("AUID testElement = Forge.randomAUID();");
						building.append("testValue." + prepend.getName() + "(testElement);");
						if (count != null)
							building.append("assertEquals(2, testValue." + count.getName() + "());");
						building.append("assertTrue(testElement.equals(testValue." + getAt.getName() + "(0)));");
						break;
					}
					System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
					break;
				case Int:
					// TODO better support other types other than UInt32
					building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
					building.append("testValue." + prepend.getName() + "(testElement);");
					if (count != null)
						building.append("assertEquals(2, testValue." + count.getName() + "());");
					building.append("assertTrue(testElement == testValue" + getAt.getName() + "(0)));");
					break;
				default:
					System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
					break;
				}
				building.decrementNL("}");
			}
			
			if (insertAt != null) {
				building.append("@Test");
				building.append("public final void test" + property.name + "GetAtInsertAtBegin() throws Exception {");
				building.increment("");
				building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
				if (count != null)
					building.append("assertEquals(1, testValue." + count.getName() + "());");
				switch (elementType.getTypeCategory()) {
				case StrongObjRef:
				case WeakObjRef:
					building.addImport(elementType.getImports());
					building.append(elementType.getJavaSetName(true) + " testElement = " +
							elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
							elementType.getJavaSetName(true) + "2();");
					building.append("testValue." + insertAt.getName() + "(0, testElement);");
					if (count != null)
						building.append("assertEquals(2, testValue." + count.getName() + "());");
					building.append("assertTrue(testElement.equals(testValue." + getAt.getName() + "(0)));");
					break;
				case Record:
					if (elementType.name.equals("AUID")) {
						building.addImport("tv.amwa.maj.record.AUID");
						building.append("AUID testElement = Forge.randomAUID();");
						building.append("testValue." + insertAt.getName() + "(0, testElement);");
						if (count != null)
							building.append("assertEquals(2, testValue." + count.getName() + "());");
						building.append("assertTrue(testElement.equals(testValue." + getAt.getName() + "(0)));");
						break;
					}
					System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
					break;
				case Int:
					// TODO better support other types other than UInt32
					building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
					building.append("testValue." + insertAt.getName() + "(0, testElement);");
					if (count != null)
						building.append("assertEquals(2, testValue." + count.getName() + "());");
					building.append("assertTrue(testElement == testValue." + getAt.getName() + "(0)));");
					break;
				default:
					System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
					break;
				}
				building.decrementNL("}");
			
				building.append("@Test");
				building.append("public final void test" + property.name + "GetAtInsertAtEnd() throws Exception {");
				building.increment("");
				building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
				if (count != null)
					building.append("assertEquals(1, testValue." + count.getName() + "());");
				switch (elementType.getTypeCategory()) {
				case StrongObjRef:
				case WeakObjRef:
					building.addImport(elementType.getImports());
					building.append(elementType.getJavaSetName(true) + " testElement = " +
							elementType.getJavaSetName(true) + "ValueTest.makeComplete" + 
							elementType.getJavaSetName(true) + "2();");
					building.append("testValue." + insertAt.getName() + "(1, testElement);");
					if (count != null)
						building.append("assertEquals(2, testValue." + count.getName() + "());");
					building.append("assertTrue(testElement.equals(testValue." + getAt.getName() + "(1)));");					
					break;
				case Record:
					if (elementType.name.equals("AUID")) {
						building.addImport("tv.amwa.maj.record.AUID");
						building.append("AUID testElement = Forge.randomAUID();");
						building.append("testValue." + insertAt.getName() + "(1, testElement);");
						if (count != null)
							building.append("assertEquals(2, testValue." + count.getName() + "());");
						building.append("assertTrue(testElement.equals(testValue." + getAt.getName() + "(1)));");					
						break;
					}
					System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
					break;
				case Int:
					// TODO better support other types other than UInt32
					building.append("int testElement = " + ((int) (Math.random() * Integer.MAX_VALUE)) + ";");
					building.append("testValue." + insertAt.getName() + "(1, testElement);");
					if (count != null)
						building.append("assertEquals(2, testValue." + count.getName() + "());");
					building.append("assertTrue(testElement.equals(testValue." + getAt.getName() + "(1)));");					
					break;
				default:
					System.out.println("Need to find a way to test array elements for " + typeData.name + ".");
					break;
				}
				building.decrementNL("}");
			}
					
			building.append("@Test(expected=IndexOutOfBoundsException.class)");
			building.append("public final void test" + property.name + "GetAtBadIndexBig() {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			building.append("testValue." + getAt.getName() + "(1);");
			building.decrementNL("}");

			building.append("@Test(expected=IndexOutOfBoundsException.class)");
			building.append("public final void test" + property.name + "GetAtBadIndexSmall() {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			building.append("testValue." + getAt.getName() + "(-1);");
			building.decrementNL("}");
		}
	
		Method removeAt = findMethodForName(iface, methods.getRemoveAtName());
		if (removeAt == null) {
			System.out.println("No public remove-at method for array " + property.name + "." + property.memberOf + 
					" in interface " + iface.getCanonicalName());
		}
		else {
			if (!((modelInterface.equals("NestedScope")) && (property.name.equals("NestedScopeTracks"))) ) {
				building.append("@Test");
				building.append("public final void test" + property.name + "RemoveAt() {");
				building.increment("");
				building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
				if (count != null)
					building.append("assertEquals(1, testValue." + count.getName() + "());");
				building.append("testValue." + removeAt.getName() + "(0);");
				if (count != null)
					building.append("assertEquals(0, testValue." + count.getName() + "());");
				building.decrementNL("}");
			}
			
			building.append("@Test(expected=IndexOutOfBoundsException.class)");
			building.append("public final void test" + property.name + "RemoveAtBig() {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			building.append("testValue." + removeAt.getName() + "(1);");
			building.decrementNL("}");
			
			building.append("@Test(expected=IndexOutOfBoundsException.class)");
			building.append("public final void test" + property.name + "RemoveAtSmall() {");
			building.increment("");
			building.append(modelInterface + " testValue = makeComplete" + modelInterface + "1();");
			building.append("testValue." + removeAt.getName() + "(-1);");
			building.decrementNL("}");
		}
	}

	static void addCommonMethodTests(
			StringIndenter building, 
			ClassData classData,
			String testValue,
			String modelInterface,
			DictionaryContext context) {
		
		// Equals
		
	    building.append("@Test");
	    building.append("public final void testEqualsItselfSimple() {");
	    building.increment("");	
	    building.append("assertTrue(" + testValue + ".equals(" + testValue + "));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testEqualsItselfComplete() {");
		building.increment("");	
	    building.append(modelInterface + " testValue = makeComplete" + modelInterface + "();");
	    building.append("assertTrue(testValue.equals(testValue));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testEqualsSameSimple() {");
		building.increment("");	
	    building.append(modelInterface + " testValue = " + testValue + ".clone();");
	    building.append("assertTrue(testValue.equals(" + testValue + "));");
	    building.append("assertTrue(" + testValue + ".equals(testValue));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testEqualsSameComplete() {");
		building.increment("");	
	    	
	    building.append(modelInterface + " testValue1 = makeComplete" + modelInterface + "();");
	    building.append(modelInterface + " testValue2 = makeComplete" + modelInterface + "();");
	    building.append("assertTrue(testValue1.equals(testValue2));");
	    building.append("assertTrue(testValue2.equals(testValue1));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testEqualsNotSame() {");
		building.increment("");		    	
	    building.append(modelInterface + " testValue = makeComplete" + modelInterface + "();");
	    building.append("assertFalse(testValue.equals(" + testValue + "));");
	    building.append("assertFalse(" + testValue + ".equals(testValue));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testEqualsNull() {");
		building.increment("");	
	    building.append("assertFalse(" + testValue + ".equals(null));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testEqualsBadObject() {");
		building.increment("");	
	    building.append("assertFalse(" + testValue + ".equals(new Object()));");
	    building.decrementNL("}");

	    // DeepEquals
	    
	    building.append("@Test");
	    building.append("public final void testDeepEqualsItselfSimple() {");
	    building.increment("");	
	    building.append("assertTrue(" + testValue + ".deepEquals(" + testValue + "));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testDeepEqualsItselfComplete() {");
		building.increment("");	
	    building.append(modelInterface + " testValue = makeComplete" + modelInterface + "();");
	    building.append("assertTrue(testValue.deepEquals(testValue));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testDeepEqualsSameSimple() {");
		building.increment("");	
	    building.append(modelInterface + " testValue = " + testValue + ".clone();");
	    building.append("assertTrue(testValue.deepEquals(" + testValue + "));");
	    building.append("assertTrue(" + testValue + ".deepEquals(testValue));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testDeepEqualsSameComplete() {");
		building.increment("");	
	    	
	    building.append(modelInterface + " testValue1 = makeComplete" + modelInterface + "();");
	    building.append(modelInterface + " testValue2 = makeComplete" + modelInterface + "();");
	    building.append("assertTrue(testValue1.deepEquals(testValue2));");
	    building.append("assertTrue(testValue2.deepEquals(testValue1));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testDeepEqualsNotSame() {");
		building.increment("");		    	
	    building.append(modelInterface + " testValue = makeComplete" + modelInterface + "();");
	    building.append("assertFalse(testValue.deepEquals(" + testValue + "));");
	    building.append("assertFalse(" + testValue + ".deepEquals(testValue));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testDeepEqualsNull() {");
		building.increment("");	
	    building.append("assertFalse(" + testValue + ".deepEquals(null));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testDeepEqualsBadObject() {");
		building.increment("");	
	    building.append("assertFalse(" + testValue + ".deepEquals(new Object()));");
	    building.decrementNL("}");

	    // Clone
	    building.append("@Test");
	    building.append("public final void testCloneSimple() {");
	    building.increment("");	
	    building.append(modelInterface + " testValue = " + testValue + ".clone();");
	    building.append("assertNotNull(testValue);");
	    building.append("assertTrue(testValue.equals(" + testValue + "));");
	    building.append("assertTrue(" + testValue + ".equals(testValue));");
	    building.append("assertFalse(" + testValue + " == testValue);");
	    building.decrementNL("}");

	    building.append("@Test");
	    building.append("public final void testCloneComplete() {");
	    building.increment("");	
	    building.append(modelInterface + " testValue1 = makeComplete" + modelInterface + "();");
	    building.append(modelInterface + " testValue2 = makeComplete" + modelInterface + "();");
	    building.append("assertNotNull(testValue1);");
	    building.append("assertNotNull(testValue2);");
	    building.append("assertTrue(testValue1.equals(testValue2));");
	    building.append("assertTrue(testValue2.equals(testValue1));");
	    building.append("assertFalse(testValue1 == testValue2);");
	    building.decrementNL("}");
	    
	    // Hashcode
	    
	    building.append("@Test");
	    building.append("public final void testHashCodeSimpleSame() {");
	    building.increment("");	
	    building.append("assertEquals(" + testValue + ".hashCode(), " + testValue +  
					".hashCode());");	    	
		building.decrementNL("}");
	 
	    building.append("@Test");
	    building.append("public final void testHashCodeSimpleCloned() {");
	    building.increment("");	
	    building.append("assertEquals(" + testValue + ".hashCode(), " + testValue +  
					".clone().hashCode());");	    	
		building.decrementNL("}");

		building.append("@Test");
	    building.append("public final void testHashCodeComplete() {");
		building.increment("");	
	    building.append(modelInterface + " testValue1 = makeComplete" + modelInterface + "();");
	    building.append(modelInterface + " testValue2 = makeComplete" + modelInterface + "();");
	    building.append("assertEquals(testValue1.hashCode(), testValue2.hashCode());");
		building.decrementNL("}");

	    building.append("@Test");
	    building.append("public final void testHashCodeDifferent() {");
		building.increment("");	
	    building.append("assertFalse(" + testValue + ".hashCode() == makeComplete" + modelInterface + "().hashCode());");
		building.decrementNL("}");
	    
		// To String
		
		building.append("@Test");
	    building.append("public final void testToStringSimple() {");
	    building.increment("");	
	    building.append("String testValue = " + testValue + ".toString();");
	    building.append("assertNotNull(testValue);");
	    building.append("assertTrue(testValue.contains(\"<" + context.preferredPrefix + ":" + classData.symbol + "\"));");
	    building.append("assertTrue(testValue.contains(\"</" + context.preferredPrefix + ":" + classData.symbol + ">\"));");
	    for ( PropertyData property : getAllProperties(classData) )
	    	if (!property.isOptional) {  		
	    		building.append("");
	    		building.append("assertTrue(testValue.contains(\"<" + context.preferredPrefix + ":" + property.symbol + "\"));");
	    		if ((!property.type.equals("DataValue")) && (!property.type.equals("Opaque")) &&
	    				(!property.type.equals("Stream")))
	    			building.append("assertTrue(testValue.contains(\"</" + context.preferredPrefix + ":" + property.symbol + ">\"));");
	    	}	    		
		building.decrementNL("}");

		building.addImport("java.io.IOException");
		building.addImport("org.xml.sax.SAXException");
		building.addImport("tv.amwa.maj.io.xml.XMLBuilder");
	    building.append("@Test");
	    building.append("public final void testToStringSimpleValidate() ");
	    building.append("	throws NullPointerException, SAXException, IOException {");
		building.increment("");	
	    	
		building.append("XMLBuilder.validate(\"test/metadictionary/aaf.xsd\", " + testValue + ".toString());");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testToStringComplete() {");
		building.increment("");	
	    building.append("String testValue = makeComplete" + modelInterface + "().toString();");
	    building.append("assertNotNull(testValue);");
	    building.append("assertTrue(testValue.contains(\"<" + context.preferredPrefix + ":" + classData.symbol + "\"));");
	    building.append("assertTrue(testValue.contains(\"</" + context.preferredPrefix + ":" + classData.symbol + ">\"));");
	    for ( PropertyData property : getAllProperties(classData) ) {
	    	if ((!property.name.equals("SubDescriptors")) && 
	    			(!property.name.equals("DescriptiveFrameworkObject"))) {
		    	building.append("");
		    	building.append("assertTrue(testValue.contains(\"<" + context.preferredPrefix + ":" + property.symbol + "\"));");
		        if (!property.type.equals("Stream"))
		        	building.append("assertTrue(testValue.contains(\"</" + context.preferredPrefix + ":" + property.symbol + ">\"));");
	    	}
	    }	    		
	    building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testToStringCompleteValidate() ");
	    building.append("    throws NullPointerException, SAXException, IOException {");
		building.increment("");	
	    building.append("XMLBuilder.validate(\"test/metadictionary/aaf.xsd\", makeComplete" + modelInterface + "().toString());");
	    building.decrementNL("}");

	    building.append("@Test");
	    building.append("public final void testToStringSimpleRoundTrip()");
	    building.append("	throws NullPointerException, SAXException {");
		building.increment("");	
	    building.append("String testString = XMLBuilder.toXML(" + testValue + ");");
	    building.append("assertNotNull(testString);");
	    building.append(modelInterface + " testValue = (" + modelInterface + 
	    		") XMLBuilder.createFromXMLString(testString);");
	    building.append("assertNotNull(testValue);");
	    building.append("assertTrue(testValue.equals(" + testValue + "));");
	    building.append("assertTrue(" + testValue + ".equals(testValue));");
		building.decrementNL("}");
	    
	    building.append("@Test");
	    building.append("public final void testToStringCompleteRoundTrip()");
	    building.append("    throws NullPointerException, SAXException {");
		building.increment("");	
	    building.append(modelInterface + " testValue1 = makeComplete" + modelInterface + "();");
	    building.append("String testString = XMLBuilder.toXML(testValue1);");
	    building.append("assertNotNull(testString);");
	    building.append(modelInterface + " testValue2 = (" + modelInterface + ") XMLBuilder.createFromXMLString(testString);");
	    building.append("assertNotNull(testValue2);");
	    building.append("assertTrue(testValue1.equals(testValue2));");
	    building.append("assertTrue(testValue2.equals(testValue1));");
	    building.decrementNL("}");
	}
	
	static List<String> getClassHierarchy(
			ClassData classData) {
		
		List<String> hierarchy = null;
		if (classData.isRoot())
			hierarchy = new ArrayList<String>(10);
		else
			hierarchy = getClassHierarchy(classList.get(classData.parentClass));
		hierarchy.add(classData.name);
		return hierarchy;
	}
	
	static Set<PropertyData> getAllProperties(
			ClassData classData) {
		
		Set<PropertyData> allProperties = new HashSet<PropertyData>(20);
		List<String> hierarchy = getClassHierarchy(classData);
		for ( PropertyData property : propertyList.values())
			if (hierarchy.contains(property.memberOf))
				allProperties.add(property);		
		return allProperties;
	}
	
	static PropertyData getUniqueIdentifier(
			ClassData classData) {
		
		for ( PropertyData property : getAllProperties(classData ) )
			if (property.isUniqueIdentifier)
				return property;
		return null;
	}
	
	static void addCompleteInstance(
			StringIndenter building,
			ClassData classData,
			String modelInterface,
			String testType,
			String nameExtra) {
		
		building.append("public final static " + modelInterface + " makeComplete" + modelInterface + nameExtra + "() {");
		building.increment("");
		
		if (classData.isConcrete) {
			building.append(modelInterface + " completeValue = Forge.make(" + modelInterface + ".class,");
			
			List<PropertyData> allProperties = new ArrayList<PropertyData>(getAllProperties(classData));
			int y = -1;
			for ( int x = 0 ; x < allProperties.size() ; x++ )
				if (allProperties.get(x).name.equals("ObjectClass")) { y = x; break; }
			if (y >= 0) allProperties.remove(y);
				
 			for ( int x = 0 ; x < allProperties.size() ; x++ ) {
				
				TypeData type = typeByName(allProperties.get(x).type);

				String propertyName = allProperties.get(x).name;
				
				if (building.getPackageName().startsWith("tv.amwa.maj")) {
					if (propertyName.equals("ParameterDefinitionReference")) {
						building.addImport("tv.amwa.maj.model.ParameterDefinition");
						building.append("        \"" + propertyName + "\", " +
								"Warehouse.lookup(ParameterDefinition.class, \"Level\").getAUID()" + ((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if (propertyName.equals("InputSegment")) {
						building.append("        \"" + propertyName + "\", " +
								"SourceClip" + testType + "Test.makeCompleteSourceClip" + nameExtra + "()" + ((x < (allProperties.size() - 1)) ? "," : ""));	
						continue;
					}
					if (propertyName.equals("DegradeTo")) {
						building.append("        \"" + propertyName + "\", " +
								"Warehouse.lookup(OperationDefinition.class, \"VideoDissolve\")" + ((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if (propertyName.equals("ControlPointTime")) {
						building.append("        \"" + propertyName + "\", " +
								"Forge.makeRational(" + ((int) (Math.random() * Integer.MAX_VALUE)) + 
								", Integer.MAX_VALUE)" + ((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if (propertyName.equals("ActiveFormatDescriptor")) {
						building.append("        \"" + propertyName + "\", " +
								((int) (Math.random() * 15)) + ((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if (propertyName.equals("FileDescriptors")) {
						building.addImport("tv.amwa.maj.model.AAFFileDescriptor");
						building.append("        \"" + propertyName + "\", " +
								"new AAFFileDescriptor[] { SoundDescriptor" + testType + "Test.makeCompleteSoundDescriptor" + nameExtra + "() }" +
								((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if (((propertyName.equals("Value")) && (modelInterface.equals("ConstantValue"))) || 
							((propertyName.equals("ControlPointValue")) && (modelInterface.equals("ControlPoint")))) {
						building.addImport("tv.amwa.maj.industry.TypeDefinitions");
						building.append("        \"" + propertyName + "\", " +
								"TypeDefinitions.Rational.createValue(Forge.makeRational(" + ((int) (Math.random() * Integer.MAX_VALUE)) + 
								", Integer.MAX_VALUE))" + ((x < (allProperties.size() - 1)) ? "," : ""));
						continue;						
					}
					if ((propertyName.equals("ByteOrder")) && (modelInterface.equals("Preface"))) {
						building.addImport("tv.amwa.maj.enumeration.ByteOrder");
						if (Math.random() < 0.5)
							building.append("        \"" + propertyName + "\", ByteOrder.Big" +
									((x < (allProperties.size() - 1)) ? "," : ""));
						else
							building.append("        \"" + propertyName + "\", ByteOrder.Little" +
									((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if ((propertyName.equals("TrackSegment")) && (modelInterface.equals("EventTrack"))) {
						building.append("        \"" + propertyName + "\", " +
								"CommentMarker" + testType + "Test.makeCompleteCommentMarker" + nameExtra + "()" +
								((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if ((propertyName.equals("ComponentObjects")) && (modelInterface.equals("Sequence"))) {
						building.append("        \"" + propertyName + "\", " +
								"Filler" + testType + "Test.makeCompleteFiller" + nameExtra + "()" +
								((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if ((propertyName.equals("ChannelCount")) && (modelInterface.equals("AES3PCMDescriptor"))) {
						building.append("        \"" + propertyName + "\", 1" +
								((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if (((propertyName.equals("FixedChannelStatusData")) ||
							(propertyName.equals("FixedUserData"))) && (modelInterface.equals("AES3PCMDescriptor"))) {
						building.append("        \"" + propertyName + "\", new byte[24]" +
								((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if (propertyName.equals("DisplayF2Offset")) {
						if (Math.random() < 0.5)
							building.append("        \"" + propertyName + "\", 0" +
									((x < (allProperties.size() - 1)) ? "," : ""));
						else
							building.append("        \"" + propertyName + "\", 1" +
									((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if (propertyName.equals("StoredF2Offset")) {
						if (Math.random() < 0.5)
							building.append("        \"" + propertyName + "\", 0" +
									((x < (allProperties.size() - 1)) ? "," : ""));
						else
							building.append("        \"" + propertyName + "\", -1" +
									((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}
					if ((propertyName.equals("StillFrame")) && (modelInterface.equals("EssenceGroup"))) {
						building.addImport("tv.amwa.maj.model.SourceClip");
						building.append("        \"" + propertyName + "\", " +
								"Forge.make(SourceClip.class, \"ComponentLength\", 1, \"ComponentDataDefinition\", DataDefinition" + testType + "Test.makeCompleteDataDefinition" + nameExtra + "())" +
								((x < (allProperties.size() - 1)) ? "," : ""));
						continue;
					}

				}
				building.append("        \"" + propertyName + "\", " + getTypeValue(type, modelInterface, building, testType, nameExtra) + 
						((x < (allProperties.size() - 1)) ? "," : ""));				
			}
			
			building.append(");");
			if (getClassHierarchy(classData).contains("DefinitionObject"))
				building.append("Warehouse.register(completeValue);");
			building.append("return completeValue;");
		}
		else {
			ClassData concreteSubClass = findConcreteSubClass(classData);
			if (concreteSubClass != null)
				building.append("return " + concreteSubClass.name + "" + testType + "Test.makeComplete" + 
						Warehouse.javaClassAlias(concreteSubClass.name) + nameExtra + "();");
			else
				building.append("return null;");
		}

		building.decrementNL("}");
		
		if (classData.isConcrete) {
			building.append("@Test");
			building.append("public final void testComplete" + classData.name + "Instanciates" + nameExtra + "() {");
			building.increment("");
			building.append("assertNotNull(makeComplete" + Warehouse.javaClassAlias(classData.name) + nameExtra + "());");
			building.decrementNL("}");	
		}
	}
	
	static ClassData findConcreteSubClass(
			ClassData parent) {
		
		for ( ClassData candidateChild : classList.values() ) {
			if ((candidateChild.parentClass != null) && 
					(candidateChild.parentClass.equals(parent.name)) &&
					(candidateChild.isConcrete))
				return candidateChild;
		}
		
		for ( ClassData candidateChild : classList.values() ) {
			if ((candidateChild.parentClass != null) && 
					(candidateChild.parentClass.equals(parent.name)))
				return findConcreteSubClass(candidateChild);
		}
		
		return null;
	}
	
	static String getTypeValue(
			TypeData type,
			String modelInterface,
			StringIndenter building,
			String testType,
			String nameExtra) {
		
		switch (type.getTypeCategory()) {

		case Int:
			TypeDataInteger integerType = (TypeDataInteger) type;
			switch (integerType.size) {
			case 1:
				if (integerType.isSigned)
					return Integer.toString((int) ((Math.random() - 0.5) * Byte.MAX_VALUE * 2));
				else
					return Integer.toString((int) (Math.random() * Byte.MAX_VALUE));
			case 2:
				if (integerType.isSigned)
					return Integer.toString((int) ((Math.random() - 0.5) * Short.MAX_VALUE * 2));
				else
					return Integer.toString((int) (Math.random() * Short.MAX_VALUE));
			case 4:
				if (integerType.isSigned)
					return Integer.toString((int) ((Math.random() - 0.5) * Integer.MAX_VALUE * 2));
				else
					return Integer.toString((int) (Math.random() * Integer.MAX_VALUE));
			case 8:
//				if (integerType.isSigned)
//					return Long.toString((int) ((Math.random() - 0.5) * Long.MAX_VALUE * 2)) + "l";
//				else
					return Long.toString((long) (Math.random() * Long.MAX_VALUE)) + "l";
			default:
				return "42";
			}
		case String:
			return "\"" + modelInterface + "-test-string-" + Integer.toString((int) (Math.random() * Integer.MAX_VALUE)) + "\"";
		case Enum:
			TypeDataEnumeration enumType = (TypeDataEnumeration) type;
			int index = (int) (Math.random() * enumType.names.size());
			return "\"" + enumType.names.get(index) + "\"";
		case ExtEnum:
			TypeDataExtendibleEnumeration extEnumType = (TypeDataExtendibleEnumeration) type;
			SortedMap<String, AUID> elements = Warehouse.lookupExtendibleEnumeration(extEnumType.name);
			String[] names = elements.keySet().toArray(new String[elements.size()]);
			int extIndex = (int) (Math.random() * names.length);
			return "\"" + names[extIndex] + "\"";
		case WeakObjRef:
			TypeDataWeakObjectReference weakType = (TypeDataWeakObjectReference) type;
			String weakReferenceName = Warehouse.javaClassAlias(weakType.referencedType);
			if (weakReferenceName.equals("ClassDefinition"))
				return "Warehouse.lookForClass(\"SoundDescriptor\")";
			if (weakReferenceName.startsWith("TypeDefinition"))
				return "Warehouse.lookForType(\"Int32\")";
			return weakReferenceName + "" + testType + "Test.makeComplete" + weakReferenceName + nameExtra + "()";
		case StrongObjRef:
			TypeDataStrongObjectReference strongType = (TypeDataStrongObjectReference) type;
			String strongReferenceName = Warehouse.javaClassAlias(strongType.referencedType);
			return strongReferenceName + "" + testType + "Test.makeComplete" + strongReferenceName + nameExtra + "()";
		case Record:
			if (type.name.equals("AUID")) {
				AUID randomID = Forge.randomAUID();
				return "Forge.makeAUID(0x" + padHexTo8(randomID.getData1()) + 
						", (short) 0x" + padHexTo4(randomID.getData2()) + 
						", (short) 0x" + padHexTo4(randomID.getData3()) + "," + "\n" +
						"                        new byte[] { " + bytesToText(randomID.getData4()) + " } )";
			}
			if (type.name.equals("Rational")) {
				return "Forge.makeRational(" + ((int) (Math.random() * Integer.MAX_VALUE)) + "," +
						((int) (Math.random() * Integer.MAX_VALUE)) + ")";
			}
			if (type.name.equals("PackageIDType")) {
				PackageID randomID = Forge.randomUMID();
				return "Forge.makePackageID(new byte[] { " + bytesToText(randomID.getUniversalLabel()) + " },\n" +
						"                        (byte) " + randomID.getLength() + ", (byte) " + randomID.getInstanceHigh() + ", (byte) " +
							randomID.getInstanceMid() + ", (byte) " + randomID.getInstanceLow() + ",\n" +
						"                        Forge.makeAUID(new byte[] { " + bytesToText(randomID.getMaterial().getAUIDValue()) + " }))";
			}
			if (type.name.equals("TimeStamp")) {
				TimeStamp now = Forge.now();
				return "Forge.makeTimeStamp((short) " + now.getDate().getYear() + 
						", (byte) " + now.getDate().getMonth() + 
						", (byte) " + now.getDate().getDay() + 
						", (byte) " + now.getTime().getHour() +
						", (byte) " + ((int) (Math.random() * 60)) + 
						", (byte) " + ((int) (Math.random() * 60)) + 
						", (byte) " + ((int) (Math.random() * 100)) + ")";
			}
			if (type.name.equals("VersionType")) {
				return "Forge.makeVersion(" + 
					"(byte) " + ((int) (Math.random() * 127)) + ", " +
					"(byte) " + ((int) (Math.random() * 127)) + ")";
			}

			return "\"\"";	
		case Rename:
			TypeDataRename renamedType = (TypeDataRename) type;
			return getTypeValue(typeByName(renamedType.renamedType), modelInterface, building, testType, nameExtra);
		case VariableArray:
			TypeDataVariableArray arrayType = (TypeDataVariableArray) type;
			TypeData subElementType = typeByName(arrayType.elementType);

			switch (subElementType.getTypeCategory()) {

			case StrongObjRef:
				TypeDataStrongObjectReference subRefType = (TypeDataStrongObjectReference) subElementType;
				workOutImport(building, subRefType.referencedType);
				return "new " + Warehouse.javaClassAlias(subRefType.referencedType) + 
				"[] { " + getTypeValue(typeByName(arrayType.elementType), modelInterface, building, testType, nameExtra) +
				" }";
			case WeakObjRef:
				TypeDataWeakObjectReference subWeakType = (TypeDataWeakObjectReference) subElementType;
				workOutImport(building, subWeakType.referencedType);
				return "new " + Warehouse.javaClassAlias(subWeakType.referencedType) + 
						"[] { " + getTypeValue(typeByName(arrayType.elementType), modelInterface, building, testType, nameExtra) +
						" }";
			case Int:
				TypeDataInteger intType = (TypeDataInteger) subElementType;
				switch (intType.size) {
				
				case 1:
					AUID randomBytes = Forge.randomAUID();
					if (intType.isSigned)
						return "new byte[] { " + bytesToText(randomBytes.getData4()) + " }";
					else
						return "new byte[] { " + bytesToText(makePositive(randomBytes.getData4())) + " }";
				case 2:
					return "new short[] { (short) 42 }";
				case 4:
					return "new int[] { 42 }";
				case 8:
					return "new long[] { 42l }";
				default:
					return "\"\"";
				}
			case Enum:
				TypeDataEnumeration arrayEnumType = (TypeDataEnumeration) subElementType;
				building.addImport("tv.amwa.maj.enumeration." + arrayEnumType.name);
				return "new " + arrayEnumType.name + "[] { " + 
						arrayEnumType.name + "." + enumName(arrayEnumType.names.get(
								((int) (Math.random() * arrayEnumType.names.size())))) +
						" }";
			default:
				return "\"\"";
			}
		case Stream:
			building.addImport("tv.amwa.maj.industry.MemoryResidentStream");
			return "new MemoryResidentStream(0)";
		case Set:
			TypeDataSet setType = (TypeDataSet) type;
			TypeData setElementType = typeByName(setType.elementType);

			switch (setElementType.getTypeCategory()) {

			case StrongObjRef:
				TypeDataStrongObjectReference setSubRefType = (TypeDataStrongObjectReference) setElementType;
				workOutImport(building, setSubRefType.referencedType);
				return "new " + Warehouse.javaClassAlias(setSubRefType.referencedType) + 
						"[] { " + getTypeValue(typeByName(setType.elementType), modelInterface, building, testType, nameExtra) +
						" }";
			case WeakObjRef:
				TypeDataWeakObjectReference setSubWeakType = (TypeDataWeakObjectReference) setElementType;
				workOutImport(building, setSubWeakType.referencedType);
				return "new " + Warehouse.javaClassAlias(setSubWeakType.referencedType) + 
						"[] { " + getTypeValue(typeByName(setType.elementType), modelInterface, building, testType, nameExtra) +
						" }";
			case Int:
				TypeDataInteger setIntType = (TypeDataInteger) setElementType;
				switch (setIntType.size) {
				
				case 1:
					AUID randomBytes = Forge.randomAUID();
					return "new byte[] { " + bytesToText(randomBytes.getData4()) + " }";
				case 2:
					return "new short[] { (short) 42 }";
				case 4:
					return "new int[] { 42 }";
				case 8:
					return "new long[] { 42l }";
				default:
					return "\"\"";
				}
			case Record:
				TypeDataRecord setRecordType = (TypeDataRecord) setElementType;
				building.addImport("tv.amwa.maj.record." + setRecordType.name);
				return "new " + setRecordType.name + "[] { " + getTypeValue(setRecordType, modelInterface, building, testType, nameExtra) +
						"}";
			default:
				return "\"\"";
			}
		case Opaque:
			try {
				PropertyValue opaqueValue = TypeDefinitions.Opaque.createValueFromActualValue(
						TypeDefinitions.Int32.createValue(42));
				ByteBuffer internalBytes = (ByteBuffer) opaqueValue.getValue();
				return "new byte[] { " + bytesToText(internalBytes.array()) + " }";
			}
			catch (NotSerializableException nse) {
				return "\"\"";
			}
		case Indirect:
			building.addImport("tv.amwa.maj.industry.TypeDefinitions");
			return "TypeDefinitions.Indirect.createValue(Integer.valueOf(42))";
		case FixedArray:
			if (type.name.equals("RGBALayout")) {
				building.addImport("tv.amwa.maj.record.RGBAComponent");
				return "new RGBAComponent[] { Forge.zeroRGBAComponent(), Forge.zeroRGBAComponent(), Forge.zeroRGBAComponent(), Forge.zeroRGBAComponent(), Forge.zeroRGBAComponent(), Forge.zeroRGBAComponent(), Forge.zeroRGBAComponent(), Forge.zeroRGBAComponent() }";
			}
			return "\"\"";
			
		default:
			return "\"\"";
		}
	}	

	private static byte[] makePositive(
			byte[] data4) {

		for ( int x = 0 ; x < data4.length ; x++ ) 
			if (data4[x] < 0) data4[x] = (byte) -(data4[x]);
		
		return data4;
 	}

	static void workOutImport(
			StringIndenter building,
			String className) {
		
		if (building.getPackageName().startsWith("tv.amwa.maj")) {
			if (classList.get(className).isInterchangeable())
				building.addImport("tv.amwa.maj.model." + Warehouse.javaClassAlias(className));
			else
				building.addImport("tv.amwa.maj.meta." + Warehouse.javaClassAlias(className));
		}
		else
			building.addImport(building.getPackageName() + "." + className);
	}	
	
	private static String enumName(
			String withUnderscore) {
		
		int underscore = withUnderscore.indexOf('_');
		if ((underscore >= 0) && (underscore < (withUnderscore.length() - 1))) {
			String enumToken = withUnderscore.substring(underscore + 1);
			// TODO better mapping to Java names e.g. Ft35mm
			if (Character.isDigit(enumToken.charAt(0)))
				return "_" + enumToken;
			else
				return enumToken;
		}
		return withUnderscore;
	}
}
