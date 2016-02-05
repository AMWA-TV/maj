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
 * $Log: MediaEngine.java,v $
 * Revision 1.20  2011/10/05 17:14:25  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.19  2011/07/27 12:21:50  vizigoth
 * Commented out AAF initialized in output.
 *
 * Revision 1.18  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.17  2011/01/26 11:50:57  vizigoth
 * Completed common method testing.
 *
 * Revision 1.16  2011/01/21 17:40:35  vizigoth
 * Moved initialization of AAF extendible enumerations and types into the media engine and added public access to the class name aliases.
 *
 * Revision 1.15  2011/01/21 09:51:19  vizigoth
 * Completed writing tests for media engine.
 *
 * Revision 1.14  2011/01/20 22:18:08  vizigoth
 * Improved tests of the media engine.
 *
 * Revision 1.13  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.12  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.11  2010/12/15 19:00:06  vizigoth
 * Moving JPA generation to be separate from the media engine.
 *
 * Revision 1.10  2010/11/08 16:05:57  vizigoth
 * Stream support changes.
 *
 * Revision 1.9  2010/07/14 13:34:38  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
 *
 * Revision 1.8  2010/05/20 18:53:23  vizigoth
 * Adding support for Avid extensions means checking whether a property is an extension property or baseline.
 *
 * Revision 1.7  2010/04/13 07:20:53  vizigoth
 * Ensured that the makeByName methods are correctly called using generics. Thanks to Leo Simons for pointing this out.
 *
 * Revision 1.6  2010/03/19 16:18:23  vizigoth
 * Fixed bug in hashcode generation for uniquely identified classes.
 *
 * Revision 1.5  2010/03/19 09:51:53  vizigoth
 * Added mechanism to determine if a meta definition is in the AAF baseline.
 *
 * Revision 1.4  2010/03/01 15:15:39  vizigoth
 * Added a generic table for weak reference resolution. Helps with auto generated weak reference targets.
 *
 * Revision 1.3  2010/02/10 23:53:23  vizigoth
 * Provided easier ways to make classes with an option to be less strict about checking for required properties. Now checks for initialize_PropertyName_ static methods in implementing classes.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 */

package tv.amwa.maj.industry;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.IllegalPropertyException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionFixedArray;
import tv.amwa.maj.meta.TypeDefinitionInteger;
import tv.amwa.maj.meta.TypeDefinitionRecord;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.record.AUID;

/**
 * <p>An engine to allow the manipulation of {@linkplain tv.amwa.maj.industry.MetadataObject metadata
 * objects}. The engine provides a means to be able to:</p>
 * 
 * <ul> 
 *  <li>{@linkplain #getPropertyValue(MetadataObject, String) get} and {@linkplain #setPropertyValue(MetadataObject, String, Object) set}
 *  property values by name and with a variety of different value representations;</li>
 *  <li>test if two objects are {@linkplain #equals(MetadataObject, Object) equal}, both shallow and 
 *  {@linkplain #deepEquals(MetadataObject, Object) deep};</li>
 *  <li>generate a {@linkplain #hashCode(MetadataObject) hash code};</li>
 *  <li>create a {@linkplain #toString(MetadataObject) string representation}.</li>
 * </ul>
 * 
 * <p>The utilities of this class depend on the given metadata objects being instances of Java classes that provide
 * {@link MediaClass &#64;MediaClass}, {@link MediaProperty &#64;MediaProperty} and associated annotations. To
 * create an extension to the specified classes or to implement your own media data model, use these annotations.</p>
 * 
 * <p>To read, write or manipulate Advanced Authoring Format (AAF) data, including AAF, MXF or Reg-XML
 * files, call {@link #initializeAAF()} first. Values of these types can then be made using the 
 * make methods of the {@linkplain Forge forge}: {@link Forge#make(Class, Object...)}, 
 * {@link Forge#make(Class, boolean, Object...)}, {@link Forge#makeAAF(String, Object...)} etc..</p>
 * 
 * <p>All the methods of this class are static.</p>
 * 
 *
 * 
 * @see Forge
 * @see MediaClass
 * @see MediaProperty
 * @see tv.amwa.maj.meta.ClassDefinition
 *
 */
public final class MediaEngine 
	implements
		CommonConstants {

	
	private static boolean aafInitialized = false;
	
	private static Set<AUID> baselineIDs = new HashSet<AUID>();
	
	private static StreamResolver resolver = new LocalFileResolver();
	
	// Class only contains static methods
	private MediaEngine() { }

	/**
	 * <p>Initializes the {@linkplain Warehouse warehouse} with all
	 * the class definitions of the {@linkplain AAFSpecifiedClasses AAF specified classes}, along with
	 * all associated {@linkplain tv.amwa.maj.meta.TypeDefinition types}. Call this
	 * before any code that needs to create, read, write or otherwise manipulate AAF data, including AAF
	 * files, MXF files and Reg XML documents.</p>
	 * 
	 * <p>Note that the first call to this method may take same time as a few hundred classes have to be 
	 * loaded by the Java virtual machine and registered with this engine.</p>
	 * 
	 * @see AAFSpecifiedClasses
	 */
	public final static void initializeAAF() {
		
		if (aafInitialized) return;
		
//		long startTime = System.currentTimeMillis();
		for ( Class<?> aafClass : AAFSpecifiedClasses.interchangeable )
			Warehouse.lookForClass(aafClass);
		for ( Class<?> aafClass : AAFSpecifiedClasses.meta )
			Warehouse.lookForClass(aafClass);
		
		for ( Class<?> typeClass : Warehouse.typeDefinitionClasses)
			Warehouse.registerTypes(typeClass, CommonConstants.AAF_XML_NAMESPACE, CommonConstants.AAF_XML_PREFIX);
		
		try {
			for ( Class<?> extEnum : Warehouse.extensibleEnumerationClasses ) 
				Warehouse.registerExtendibleEnumerationElements(extEnum);
		}
		catch (Exception e) { 
			System.err.println("Unexpected exception in static initilizer for ExtensibleEnumerationFactory.");
			e.printStackTrace(System.err);
			System.exit(1);
		}

		buildBaselineClasses();
		
//		long endTime = System.currentTimeMillis();
		aafInitialized = true;
		
		// System.out.println("INFO: AAF classes registered in " + (endTime - startTime) + "ms.");
	}
	
	private final static void buildBaselineClasses() {
		
		for ( Class<?> baselineClass : AAFSpecifiedClasses.interchangeable )
			addBaselineIDs(Warehouse.lookForClass(baselineClass));
		for ( Class<?> baselineClass : AAFSpecifiedClasses.abstractInterchangeable )
			addBaselineIDs(Warehouse.lookForClass(baselineClass));
		for ( Class<?> baselineClass : AAFSpecifiedClasses.meta )
			addBaselineIDs(Warehouse.lookForClass(baselineClass));
		for ( Class<?> baselineClass : AAFSpecifiedClasses.abstractMeta )
			addBaselineIDs(Warehouse.lookForClass(baselineClass));
		
		addBaselineTypeDefinitions();
	}
	
	private final static void addBaselineIDs(
			ClassDefinition aafClass) {
		
		baselineIDs.add(aafClass.getAUID());
		for ( PropertyDefinition property : aafClass.getPropertyDefinitions()) {
			if (property.getNamespace().equals(AAF_XML_NAMESPACE))
					baselineIDs.add(property.getAUID());
		}

	}
	
	private final static void addBaselineTypeDefinitions() {
		
		for ( Field field : tv.amwa.maj.industry.TypeDefinitions.class.getDeclaredFields() ) {
			
			try {
				TypeDefinition type = (TypeDefinition) field.get(null);
				baselineIDs.add(type.getAUID());
			}
			catch (IllegalAccessException iae) { }
		}
	}
	
	/**
	 * <p>Determines if the given {@linkplain tv.amwa.maj.meta.ClassDefinition class}, 
	 * {@linkplain tv.amwa.maj.meta.PropertyDefinition property} or 
	 * {@linkplain TypeDefinition type} definition that is part of the AAF baseline set.
	 * All baseline types are registered by calling {@link #initializeAAF()}.
	 * If it is not part of the baseline set, the definition is considered as an 
	 * <em>extension</em>.</p>
	 * 
	 * <p>Note that this method only returns a correct result after calling 
	 * {@link #initializeAAF()}.</p>
	 * 
	 * @param metaDefinition Meta definition to test to see if it is part of the AAF baseline. 
	 * @return Is the given definition part of the AAF baseline set (returns <code>true</code>)
	 * or an extension (returns <code>false</code>)?
	 * 
	 * @throws NullPointerException The given meta definition is <code>null</code>.
	 * 
	 * @see #isBaseline(AUID)
	 * @see MetaDefinition
	 */
	public final static boolean isBaseline(
			MetaDefinition metaDefinition) {
		
		if (metaDefinition == null)
			throw new NullPointerException("Cannot determine if a definition is baseline using a null value.");
		
		try {
			return baselineIDs.contains(metaDefinition.getAUID());
		}
		catch (NullPointerException npe) {
			return false;
		}
	}
	
	/**
	 * <p>Determines if the given identifier identifies a {@linkplain tv.amwa.maj.meta.ClassDefinition class}, 
	 * {@linkplain tv.amwa.maj.meta.PropertyDefinition property} or 
	 * {@linkplain TypeDefinition type} definition that is part of the AAF baseline set.
	 * All baseline types are registered by calling {@link #initializeAAF()}.
	 * If it is not part of the baseline set, the definition is considered as an 
	 * <em>extension</em>.</p>
	 * 
	 * <p>Note that this method only returns a correct result after calling 
	 * {@link #initializeAAF()}.</p>
	 * 
	 * @param metaDefinitionID Meta definition to test to see if it is part of the AAF baseline. 
	 * @return Is the given definition part of the AAF baseline set (returns <code>true</code>)
	 * or an extension (returns <code>false</code>)?
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * 
	 * @see #isBaseline(MetaDefinition)
	 * @see MetaDefinition
	 */	
	public final static boolean isBaseline(
			AUID metaDefinitionID) 
		throws NullPointerException {
		
		if (metaDefinitionID == null) 
			throw new NullPointerException("Cannot check whether a class is baseline using a null identifier.");
		
		return baselineIDs.contains(metaDefinitionID);
	}
	
	/**
	 * <p>Test to see if the first metadata object is equal to the second object, accepting equality of 
	 * unique identifiers where available. Equality is defined by the following rules:</p>
	 * 
	 * <ul>
	 *  <li>Two <code>null</code> values are always equal.</li>
	 *  <li>If only one of the values is <code>null</code>, the values are not equal.</li>
	 *  <li>For non-null values, the {@linkplain ClassDefinition class definitions} of the values 
	 *  are the same or one is an ancestor of the other in the class hierarchy.</li>
	 *  <li>For non-null values and for values that both have a unique identifier property, the value of that 
	 *  identifier property is equal in both objects.</li>
	 *  <li>For non-null values with no unique identifiers:
	 *  <ul>
	 *   <li>every required property must be equal
	 *  according to the property value's {@linkplain Object#equals(Object) equals} method;</li>
	 *   <li>The same optional property values must be present and of equal value according to the
	 *   property value's {@linkplain Object#equals(Object) equals} method.</li>
	 *  </ul></li>
	 * </ul>
	 * 
	 * <p>For {@linkplain MediaClass media classes}, developer's can use this method to provide an 
	 * equality test in their own classes without having to write their own code. An example of how 
	 * to do this is:</p>
	 * 
	 * <pre>
	 *     &#64;Override
	 *     public boolean equals(
	 *             Object o) {
	 *     
	 *         return MetaEngine.equals(this, o);
	 *     }
	 * </pre>
	 * 
	 * <p>The equality test of this method is compatible with the media engines {@linkplain #hashCode()
	 * hash code} method.</p>
	 * 
	 * @param first First object to test the equality of.
	 * @param second Second object to test the equality of.
	 * 
	 * @return Are the two objects of equals value?
	 * 
	 * @see #deepEquals(MetadataObject, Object)
	 * @see Object#equals(Object)
	 */
	public static boolean equals(
			MetadataObject first,
			Object second) {
		
		if (!basicEqualityChecks(first, second)) return false;
		
		if (first == second) return true;
		
		ClassDefinition firstClass = Warehouse.lookForClass(first.getClass());
		ClassDefinition secondClass = Warehouse.lookForClass(second.getClass());
		
		// If uniquely identified, test for the equality of the identifiers
		if (firstClass.isUniquelyIdentified() && secondClass.isUniquelyIdentified()) {
			return firstClass.getUniqueIdentifierValue(first).getValue().equals(
					secondClass.getUniqueIdentifierValue((MetadataObject) second).getValue());
		}
		
		return propertyByPropertyEquality(firstClass, first, secondClass, (MetadataObject) second);
		
	}
	
	private final static boolean basicEqualityChecks(
			MetadataObject first,
			Object second) {
		
		if (first == null) return (second == null);
		
		// If here, first is not null
		if (second == null) return false;
		
		// If the two objects are the same, they are equal!
		if (first == second) return true;
		
		// Comparison is based on comparing metadata object properties
		if (!(second instanceof MetadataObject)) return false;
		
		// One of the objects must by in the type hierarchy of the other
		if ((!first.getClass().isInstance(second)) && (!second.getClass().isInstance(first)))
			return false;
		
		return true;
	}
	
	private final static boolean propertyByPropertyEquality(
			ClassDefinition firstClass,
			MetadataObject first,
			ClassDefinition secondClass,
			MetadataObject second) {
		
		SortedMap<? extends PropertyDefinition, ? extends PropertyValue> firstValues =
			firstClass.getProperties(first);
		SortedMap<? extends PropertyDefinition, ? extends PropertyValue> secondValues =
			secondClass.getProperties((MetadataObject) second);
		
		// The sets must contain the same number of present properties to be equals
		if (firstValues.size() != secondValues.size()) return false;
		
		// Iterate through each property checking for that all the same properties are present
		for ( PropertyDefinition firstProperty : firstValues.keySet() ) {
			
			// All properties present in the first must be present in the second.
			if (!secondValues.containsKey(firstProperty)) return false;
		}
		
		// Iterate through each property checking for equality
		for ( PropertyDefinition firstProperty : firstValues.keySet() ) {
			
			PropertyValue firstValue = firstValues.get(firstProperty);
			PropertyValue secondValue = secondValues.get(firstProperty);
//			if (firstProperty.getName().equals("ByteOrder"))
//				System.out.println("Checking " + firstProperty.getName());
			if (!firstValue.equals(secondValue)) return false;
		}
		
		return true;
	}

	/**
	 * <p>Test to see if a metadata object is equal to another object by comparing the properties
	 * of each object. Equality is defined by the following rules:</p>
	 * 
	 * <ul>
	 *  <li>Two <code>null</code> values are always equal.</li>
	 *  <li>If only one of the values is <code>null</code>, the values are not equal.</li>
	 *  <li>For non-null values, the {@linkplain ClassDefinition class definitions} of the values 
	 *  are the same or one is an ancestor of the other in the class hierarchy.</li>
	 *  <li>For non-null values:
	 *  <ul>
	 *   <li>every required property must be equal
	 *  according to the property value's {@linkplain Object#equals(Object) equals} method;</li>
	 *   <li>The same optional property values must be present and of equal value according to the
	 *   property value's {@linkplain Object#equals(Object) equals} method.</li>
	 *  </ul></li>
	 * </ul>
	 * 
	 * <p>Note that deep equals is only one level deep. Any comparison of child values that have
	 * unique identifiers will use those identifiers.</p>
	 * 
	 * <p>For {@linkplain MediaClass media classes}, developer's can use this method to provide an 
	 * equality test in their own classes without having to write their own code. An example of how 
	 * to do this is:</p>
	 * 
	 * <pre>
	 *     public boolean deepEquals(
	 *             Object o) {
	 *     
	 *         return MetaEngine.equals(this, o);
	 *     }
	 * </pre>
	 * 
	 * @param first First object to test the equality of.
	 * @param second Second object to test the equality of.
	 * @return Are the two objects of equals value?
	 * 
	 * @see #equals(MetadataObject, Object)
	 * @see Object#equals(Object)
	 */
	public final static boolean deepEquals(
			MetadataObject first,
			Object second) {

		if (!basicEqualityChecks(first, second)) return false;

		ClassDefinition firstClass = Warehouse.lookForClass(first.getClass());
		ClassDefinition secondClass = Warehouse.lookForClass(second.getClass());

		return propertyByPropertyEquality(firstClass, first, secondClass, (MetadataObject) second);
	} 
	
	/**
	 * <p>Create a hash code value for a metadata object using its present properties.</p>
	 * 
	 * <p>If two objects have equal value, they should have the same hash code. Objects that
	 * are not equal values should have different hash codes but may occasionally clash.</p>
	 * 
	 * <p>Calling this method as a delegate within a {@linkplain MetadataObject metadata object's} 
	 * {@linkplain Object#hashCode() hashCode()} method, overriding the default, will 
	 * enable efficient storage and reference to metadata objects in {@linkplain HashSet hash
	 * sets} and {@linkplain HashMap hash maps}. By using the media metadata associated with
	 * a class to create the hash code, a developer does not have to implement their own
	 * hash code calculation. An example of using this method is:</p>
	 * 
	 * <pre>
	 *     &#64;Override
	 *     public int hashCode() {
	 *     
	 *         MediaEngine.hashCode(this);
	 *     }
	 * </pre>
	 * 
	 * <p>Note that <code>null</code> values produce a&nbsp;<code>0</code> hash code rather than throwing an 
	 * exception.
	 * 
	 * @param value Hashcode value for a metadata object using its present properties.
	 * 
	 * @return Hashcode value for a metadata object using its present properties.
	 * 
	 * @see Object#hashCode()
	 */
	public final static int hashCode(
			MetadataObject value) {
	
		if (value == null) return 0;
		
		ClassDefinition metaClass = Warehouse.lookForClass(value.getClass());
		
		if (metaClass.isUniquelyIdentified()) {
			PropertyDefinition uniqueIdentifierProperty = metaClass.getUniqueIdentifierProperty();
			PropertyValue valueUniqueID = uniqueIdentifierProperty.getPropertyValue(value);
			return valueUniqueID.getValue().hashCode();
		}
		
		SortedMap<? extends PropertyDefinition, ? extends PropertyValue> propertyValues = 
			metaClass.getProperties(value);
		
//		for ( PropertyDefinition propDef : propertyValues.keySet() )
//			System.out.println(propDef.getName() + " = " + propertyValues.get(propDef).hashCode());
		
		int hashCode = 0;
		for ( PropertyValue propertyValue : propertyValues.values() ) 
			hashCode ^= propertyValue.hashCode();
		
		return hashCode;
	}
	
	/* TODO post release 1.0 of MAJ
	public final static MetadataObject deepClone(
			MetadataObject metadataObject) {
		
		return null;
	} */
	
	/**
	 * <p>Returns the value of the named property from the given metadata object.</p>
	 * 
	 * <p>The type of the value that is returned will depend on the data type of the 
	 * property. See the <a href="package-summary.html#typeMappingTable">type mapping table</a>
	 * in the package documentation to determine the expected type of the return
	 * value.</p>
	 * 
	 * @param <T> Default Java type for the property to retrieve.
	 * @param target Metadata object instance to retrieve a property value from.
	 * @param propertyName Name of the property value to retrieve.
	 * @return Unwrapped value of the property.
	 * 
	 * @throws NullPointerException Cannot get a property value from a <code>null</code> value.
	 * @throws IllegalPropertyException The given property name does not match a property
	 * defined for the class of the given metadata object.
	 * @throws ClassCastException Class cast exception when trying to cast the
	 * returned value to the required type.
	 * @throws PropertyNotPresentException An optional property is not present for
	 * the given metadata object.
	 * 
	 * @see #getWrappedPropertyValue(MetadataObject, String)
	 * @see #setPropertyValue(MetadataObject, String, Object)
	 * @see PropertyDefinition#getPropertyValue(MetadataObject)
	 */
	@SuppressWarnings("unchecked")
	public final static <T> T getPropertyValue(
			MetadataObject target,
			String propertyName) 
		throws NullPointerException,
			IllegalPropertyException,
			ClassCastException,
			PropertyNotPresentException {
		
		try {
			PropertyValue candidateValue = getWrappedPropertyValue(target, propertyName);
			if (candidateValue.getType().equals(TypeDefinitions.Boolean)) {
				tv.amwa.maj.enumeration.Boolean boolValue = (tv.amwa.maj.enumeration.Boolean) candidateValue.getValue();
				if (boolValue == tv.amwa.maj.enumeration.Boolean.True)
					return (T) java.lang.Boolean.valueOf(true);
				else
					return (T) java.lang.Boolean.valueOf(false);
			}
			if (candidateValue.getValue() instanceof Collection<?>) {

				TypeDefinition elementType = null;
				switch (candidateValue.getType().getTypeCategory()) {
				
				case VariableArray:
					elementType = ((TypeDefinitionVariableArray) candidateValue.getType()).getType();
					break;
				case Set:
					elementType = ((TypeDefinitionSet) candidateValue.getType()).getElementType();
					if (elementType instanceof TypeDefinitionInteger)
						return (T) candidateValue.getValue();
					if (elementType instanceof TypeDefinitionRecord)
						return (T) candidateValue.getValue();
					break;
				case FixedArray:
					elementType = ((TypeDefinitionFixedArray) candidateValue.getType()).getType();
					break;
				default:
					return (T) candidateValue.getValue();
				}
				
				Collection<?> candidateCollection = (Collection<?>) candidateValue.getValue();
				switch (elementType.getTypeCategory()) {
				
				case Int:
					switch (((TypeDefinitionInteger) elementType).getSize()) {
					case 1:
						byte[] bytes = new byte[candidateCollection.size()];
						int x1 = 0;
						for ( Object byteElement : candidateCollection ) bytes[x1++] = (Byte) byteElement;
						return (T) bytes;
					case 2:
						short[] shorts = new short[candidateCollection.size()];
						int x2 = 0;
						for ( Object shortElement : candidateCollection ) shorts[x2++] = (Short) shortElement;
						return (T) shorts;
					case 4:
						int[] ints = new int[candidateCollection.size()];
						int x3 = 0;
						for ( Object intElement : candidateCollection ) ints[x3++] = (Integer) intElement;
						return (T) ints;
					default:
					case 8:
						long[] longs = new long[candidateCollection.size()];
						int x4 = 0;
						for ( Object longElement : candidateCollection ) longs[x4++] = (Long) longElement;
						return (T) longs;
					}
				case Enum:
				case Record:
					return (T) candidateCollection.toArray();
				default:
					return (T) candidateValue.getValue();
				}
			}
			return (T) candidateValue.getValue();
		}
		catch (ClassCastException cce) {
			throw new ClassCastException("Class cast exception when trying to convert property " + propertyName + 
					" to a value of the required type: " + cce.getMessage());
		}
	}
	
	/**
	 * <p>Returns the value of the named property from the given metadata object, wrapped
	 * up with its type in a {@linkplain PropertyValue property value}.</p>
	 * 
	 * @param target Metadata object instance to retrieve a property value from.
	 * @param propertyName Name of the property value to retrieve.
	 * @return Wrapped value of the property.
	 * 
	 * @throws NullPointerException Cannot get a property value from a <code>null</code> value.
	 * @throws IllegalPropertyException Cannot get a property value from the given 
	 * metadata object using a <code>null</code> value.
	 * @throws PropertyNotPresentException An optional property is not present for
	 * the given metadata object.
	 * 
	 * @see #getPropertyValue(MetadataObject, String)
	 * @see #setPropertyValue(MetadataObject, String, PropertyValue)
	 */
	public final static PropertyValue getWrappedPropertyValue(
			MetadataObject target,
			String propertyName) 
		throws NullPointerException,
			IllegalPropertyException,
			PropertyNotPresentException {

		if (target == null)
			throw new NullPointerException("Cannot get a property value from a null target value.");
		
		if (propertyName == null)
			throw new NullPointerException("Cannot get a property value from the given metadata object using a null property name.");
		
		ClassDefinition targetClass = Warehouse.lookForClass(target.getClass());
		try {
			PropertyDefinition targetProperty = targetClass.lookupPropertyDefinition(propertyName);
			return targetProperty.getPropertyValue(target);
		}
		catch (BadParameterException bpe) {
			throw new IllegalPropertyException("The given property name does not match that of a known property for class " + 
					targetClass.getName() + ".");
		}
	}

	/**
	 * <p>Returns the {@linkplain ClassDefinition class definition} that defines the type of
	 * the given metadata object.</p>
	 * 
	 * @param target Object to retrieve the class definition for.
	 * @return Class definition defining the type of the given object.
	 * 
	 * @throws NullPointerException Cannot retrieve a class definition for a <code>null</code>
	 * value.
	 * @throws IllegalArgumentException The given object is not annotated as a media class.
	 * 
	 * @see #getPropertyDefinition(MetadataObject, String)
	 * @see MediaClass
	 * @see ClassDefinition
	 * @see InterchangeObject#getObjectClass()
	 */
	public final static ClassDefinition getClassDefinition(
			MetadataObject target) 
		throws NullPointerException,
			IllegalArgumentException {
		
//		if (target instanceof ApplicationObject)
//			System.out.println(((ApplicationObject) target).getBaseClass());
		return Warehouse.lookForClass(target.getClass());
	}
	
	/**
	 * <p>Returns the {@linkplain PropertyDefinition property definition} of the named property from given metadata
	 * object.</p>
	 * 
	 * @param target Object to retrieve the property definition for.
	 * @param propertyName Name of the property.
	 * @return Property definition of the given name in the class defining the type of the given metadata object.
	 * 
	 * @throws NullPointerException Cannot retrieve a property definition from a <code>null</code> object or
	 * with a <code>null</code> name.
	 * @throws IllegalPropertyException The given metadata object does not have a property of the given name.
	 * @throws IllegalArgumentException The given metadata object is not annotated as a media class.
	 * 
	 * @see #getClassDefinition(MetadataObject)
	 * @see ClassDefinition#lookupPropertyDefinition(String)
	 * @see MediaProperty
	 * @see PropertyDefinition
	 * @see #getPropertyValue(MetadataObject, String)
	 */
	public final static PropertyDefinition getPropertyDefinition(
			MetadataObject target,
			String propertyName) 
		throws NullPointerException,
			IllegalPropertyException,
			IllegalArgumentException {
		
		if (target == null)
			throw new NullPointerException("Cannot get a property definition from a null value.");
		
		if (propertyName == null)
			throw new NullPointerException("Cannot get a property definition from the given metadata object using a null value.");
		
		ClassDefinition targetClass = Warehouse.lookForClass(target.getClass());
		try {
			return targetClass.lookupPropertyDefinition(propertyName);
		}
		catch (BadParameterException bpe) {
			throw new IllegalPropertyException("The given property name does not match that of a known property for class " + 
					targetClass.getName() + ".");
		}
	}

	/**
	 * <p>Sets the value of the named property from the given metadata object to the given value.
	 * The type of the value to set must match that of the {@linkplain PropertyDefinition#getTypeDefinition() property's 
	 * type definition} as shown in the <a href="package-summary.html#typeMappingTable">type mapping table</a>.</p>
	 * 
	 * <p>All values will be set through the relevant set method of the metadata object, meaning that any semantic
	 * checks implemented in that set method will be carried out.</p>
	 * 
	 * <p>Note that it is possible to set a <code>null</code> value to omit an optional property. Setting a 
	 * <code>null</code> value for a required property will result in an exception.</p>
	 * 
	 * @param target Metadata object to set the value of a property for.
	 * @param propertyName Name of the property value to set.
	 * @param propertyValue Value to set for the property.
	 * 
	 * @throws NullPointerException Cannot set a property value on a <code>null</code> value or find a property
	 * using a <code>null</code> name.
	 * @throws IllegalPropertyException The given metadata object does not have a property of the given name.
	 * @throws IllegalArgumentException An error occurred when trying to convert the value to the required type
	 * or when calling the underlying classes set method.
	 * 
	 * @see #setPropertyValue(MetadataObject, String, PropertyValue)
	 * @see #getPropertyValue(MetadataObject, String)
	 * @see #getPropertyDefinition(MetadataObject, String)
	 * @see PropertyDefinition#setPropertyValue(MetadataObject, PropertyValue)
	 * @see TypeDefinition#createValue(Object)
	 */
	public final static void setPropertyValue(
			MetadataObject target,
			String propertyName,
			Object propertyValue) 
		throws NullPointerException,
		 	IllegalPropertyException,
		 	IllegalArgumentException {
		
		PropertyDefinition targetDefinition = getPropertyDefinition(target, propertyName);
		targetDefinition.setPropertyValue(
				target, 
				targetDefinition.getTypeDefinition().createValue(propertyValue));
	}
	
	/**
	 * <p>Sets the value of the named property from the given metadata object to the given 
	 * wrapped {@linkplain PropertyValue property value}.</p>
	 * 
	 * <p>All values will be set through the relevant set method of the metadata object, meaning that any semantic
	 * checks implemented in that set method will be carried out.</p>
	 * 
	 * <p>Note that it is possible to set a <code>null</code> value to omit an optional property. Setting a 
	 * <code>null</code> value for a required property will result in an exception.</p>
	 * 
	 * @param target Metadata object to set the value of a property for.
	 * @param propertyName Name of the property value to set.
	 * @param propertyValue Value to set for the property.
	 * 
	 * @throws NullPointerException Cannot set a property value on a <code>null</code> value or find a property
	 * using a <code>null</code> name.
	 * @throws IllegalPropertyException The given metadata object does not have a property of the given name.
	 * @throws IllegalArgumentException An error occurred when trying to convert the value to the required type
	 * or when calling the underlying classes set method.
	 * 
	 * @see #setPropertyValue(MetadataObject, String, Object)
	 * @see #getWrappedPropertyValue(MetadataObject, String)
	 * @see #getPropertyDefinition(MetadataObject, String)
	 * @see PropertyDefinition#setPropertyValue(MetadataObject, PropertyValue)
	 * @see TypeDefinition#createValue(Object)
	 */
	public final static void setPropertyValue(
			MetadataObject target,
			String propertyName,
			PropertyValue propertyValue) 
		throws NullPointerException,
			IllegalPropertyException,
			IllegalArgumentException {
		
		PropertyDefinition targetDefinition = getPropertyDefinition(target, propertyName);
		targetDefinition.setPropertyValue(target, propertyValue); 
	}

	/**
	 * <p>Checks if the given metadata object is initialized. An initialized object has non-null values for
	 * all of its required properties that can be retrieved without causing an exception.</p>
	 * 
	 * <p>If an object is initialized, it should be safe to write it out to serialize it to a file, persistent store
	 * or over a network connection. It is recommended that this method is called before any such serialization.</p>
	 * 
	 * <p>This method requires initialized required arrays or sets to have at least one element.</p> 
	 * 
	 * <p>Any object instantiated using the {@link ClassDefinition#createInstance()} method is likely to 
	 * start in an uninitialized state. Most instances created with one of the 
	 * {@linkplain Forge#make(Class, Object...) make methods} of the media engine will required the 
	 * caller to have provided enough information to safely initialize the object, with the 
	 * exception of arrays and lists.</p>
	 * 
	 * @param unknownState Metadata object that may or may not be initialized.
	 * @return Is the given metadata object initialized?
	 * 
	 * @throws NullPointerException Cannot check if an object is initialized using a <code>null</code> value.
	 * @throws IllegalArgumentException The given metadata object is not of a type annotated as a media class.
	 * 
	 * @see Forge#make(Class, Object...)
	 * @see Forge#makeByName(String, String, Object...)
	 * @see PropertyNotPresentException
	 */
//	public final static boolean isInitialized(
//			MetadataObject unknownState) 
//		throws NullPointerException,
//			IllegalArgumentException {
//		
//		// TODO post release 1.0 of MAJ
//		
//		return false;
//	}

	/**
	 * <p>Create a string representation of the given metadata object. The string representation
	 * will be in an XML format similar to the one used for SMPTE Reg-XML. The symbol names of
	 * the object's defining class and the properties will be used as XML element names.</p>
	 * 
	 * <p>Developer's can use this as a delegate method in a method that overrides
	 * {@link Object#toString() the default to string method} in any class. This means they can 
	 * get a XML form of a value at any time and this is often useful in debugging. An example of how 
	 * to do this is:</p>
	 * 
	 * <pre>
	 *     &#64;Override
	 *     public String toString() {
	 *     
	 *         return MetaEngine.toString(this);
	 *     }
	 * </pre>
	 * 
	 * @param metadataOject Metadata object to create a string representation of.
	 * @return String representation of the given metadata object.
	 * 
	 * @see Object#toString()
	 * @see XMLBuilder#toXML(MetadataObject)
	 */
	public final static String toString(
			MetadataObject metadataOject) {

		return XMLBuilder.toXML(metadataOject);
	}

	/**
	 * <p>Create new instances of metadata objects using the definition of its primary interface
	 * and its initial property values. Property values must be provided for all required properties 
	 * and may also be provided for optional values. This is with the exception of required
	 * sets and arrays that are initialized to empty and will require at least one element to be
	 * added to become initialized.</p>
	 * 
	 * <p>The type of object to create is provided by its defining Java interface or class. The
	 * property values are provided by a list of property identifier and value pairs, where each identifier
	 * is followed by its value. The property identifier can by a name, {@linkplain tv.amwa.maj.record.AUID} or 
	 * {@linkplain tv.amwa.maj.meta.PropertyDefinition property definition}. The values are either instances of the
	 * {@link PropertyValue} interface or Java values compatible with the type definition, as
	 * defined in the <a href="package-summary.html#typeMappingTable">type mapping table</a>.</p>
	 * 
	 * <p>Here is an example of how to use this method:</p>
	 * 
	 * <pre>
	 *     	TimelineTrack videoTrack = MediaEngine.make(
	 *          TimelineTrack.class,
	 *          "TrackID", 1, 
	 *          "EditRate", "25/1", 
	 *          "Origin", 0l,           
	 *          "TrackSegment", videoSequence);
	 * </pre>
	 * 
	 * <p>In the example, an instance of the timeline track type is created. Its track identifier is initialized
	 * to&nbsp;1, edit rate to&nbsp;25/1 using a string representation of the value, origin to&nbsp;0 and track to a variable
	 * called <code>videoSequence</code>.</p>
	 * 
	 * @param <T> Type of value to make and return.
	 * @param type Java class representing the type to return.
	 * @param strict Should the method be strict about required properties or use defaults provided by
	 * the implementation?
	 * @param properties List of pairs of property identifiers and property values to use to initialize the
	 * newly made value.
	 * @return New instance of the specified type initialized with the given property values.
	 * 
	 * @throws NullPointerException Cannot make a new value for a null class specification.
	 * @throws IllegalArgumentException The list of properties must be a list of property identifier
	 * and property value pairs, ie. divisible by&nbsp;2, or a summary of other errors encountered during
	 * object creation.
	 * 
	 * @see #make(Class, Object...)
	 * @see #makeByName(String, String, boolean, Object...)
	 * @see #makeByName(String, String, Object...)
	 * @see #makeAAF(String, Object...)
	 * @see ClassDefinition#createInstance()
	 * @deprecated Use {@link Forge#make(Class, boolean, Object...)} instead
	 */
	public final static <T extends MetadataObject> T make(
			Class<T> type,
			boolean strict,
			Object... properties) 
		throws NullPointerException,
			IllegalArgumentException {
				
		return Forge.make(type, strict, properties);
	}
	
	/**
	 * <p>Create new instances of metadata objects using the definition of its primary interface
	 * and its initial property values without being strict about required properties. See the
	 * description of {@link #make(Class, boolean, Object...)} for more details.</p>
	 * 
	 * @param <T> Type of value to make and return.
	 * @param type Java class representing the type to return.
	 * @param properties List of pairs of property identifiers and property values to use to initialize the
	 * newly made value.
	 * @return New instance of the specified type initialized with the given property values.
	 * 
	 * @throws NullPointerException Cannot make a new value for a null class specification.
	 * @throws IllegalArgumentException The list of properties must be a list of property identifier
	 * and property value pairs, ie. divisible by&nbsp;2, or a summary of other errors encountered during
	 * object creation.
	 * 
	 * @see #make(Class, boolean, Object...)
	 * @see #makeByName(String, String, Object...)
	 * @see ClassDefinition#createInstance()
	 * @deprecated Use {@link Forge#make(Class, Object...)} instead
	 */
	public final static <T extends MetadataObject> T make(
			Class<T> type,
			Object... properties) 
		throws NullPointerException,
			IllegalArgumentException {
				
		return Forge.make(type, properties);
	}

	/**
	 * <p>Create new instances of metadata objects using a fully qualified XML namespace name for
	 * the type and its initial property values. Property values must be provided for all required properties 
	 * and may also be provided for optional values. This is with the exception of required
	 * sets and arrays that are initialized to empty and will require at least one element to be
	 * added to become initialized.</p>
	 * 
	 * <p>The type of object to create is provided by its defining Java interface or class. The
	 * property values are provided by a list of property identifier and value pairs, where each identifier
	 * is followed by its value. The property identifier can by a name, {@linkplain AUID} or 
	 * {@linkplain PropertyDefinition property definition}. The values are either instances of the
	 * {@link PropertyValue} interface or Java values compatible with the type definition, as
	 * defined in the <a href="package-summary.html#typeMappingTable">type mapping table</a>.</p>
	 * 
	 * <p>Here is an example of how to use this method:</p>
	 * 
	 * <pre>
	 *     	TimelineTrack videoTrack = MediaEngine.make(
	 *          "http://www.smpte-ra.org/schemas/2001-2/2007/aaf", "TimelineTrack",
	 *          "TrackID", 1, 
	 *          "EditRate", "25/1", 
	 *          "Origin", 0l,           
	 *          "TrackSegment", videoSequence);
	 * </pre>
	 * 
	 * <p>In the example, an instance of the timeline track type is created. Its track identifier is initialized
	 * to&nbsp;1, edit rate to&nbsp;25/1 using a string representation of the value, origin to&nbsp;0 and track to a variable
	 * called <code>videoSequence</code>. Note that the default AAF XML namespace is set as static string
	 * {@link CommonConstants#AAF_XML_NAMESPACE}.</p>
	 * 
	 * @param <T> Type of value to make and return.
	 * @param namespace Namespace in which the type for the new instance is defined.
	 * @param typeName Name of the type of instance to create within the given namespace.
	 * @param strict Should the method be strict about required properties or use defaults provided by
	 * the implementation?
	 * @param properties List of pairs of property identifiers and property values to use to initialize the
	 * newly made value.
	 * @return New instance of the specified type initialized with the given property values.
	 * 
	 * @throws NullPointerException Cannot make a new value for a null class specification.
	 * @throws IllegalArgumentException The list of properties must be a list of property identifier
	 * and property value pairs, ie. divisible by&nbsp;2, or a summary of other errors encountered during
	 * object creation.
	 * 
	 * @see #make(Class, Object...)
	 * @see ClassDefinition#createInstance()
	 * @see MediaClass#namespace()
	 * @see CommonConstants#AAF_XML_NAMESPACE
	 * @deprecated Use {@link Forge#makeByName(String,String,boolean,Object...)} instead
	 */
	public final static <T extends MetadataObject> T makeByName(
			String namespace,
			String typeName,
			boolean strict,
			Object... properties)
		throws NullPointerException,
			IllegalArgumentException {
				
		return Forge.makeByName(namespace, typeName, strict, properties);
	}
	
	/**
	 * <p>Create new instances of metadata objects using a fully qualified XML namespace name for
	 * the type and its initial property values, and also without being strict about required properties.
	 * For more details about this method, see the documentation for the {@link #makeByName(String, String, boolean, Object...)}
	 * method.</p>
	 * 
	 * @param <T> Type of value to make and return.
	 * @param namespace Namespace in which the type for the new instance is defined.
	 * @param typeName Name of the type of instance to create within the given namespace.
	 * @param properties List of pairs of property identifiers and property values to use to initialize the
	 * newly made value.
	 * @return New instance of the specified type initialized with the given property values.
	 * 
	 * @throws NullPointerException Cannot make a new value for a null class specification.
	 * @throws IllegalArgumentException The list of properties must be a list of property identifier
	 * and property value pairs, ie. divisible by&nbsp;2, or a summary of other errors encountered during
	 * object creation.
	 * 
	 * @see #make(Class, Object...)
	 * @see #make(Class, boolean, Object...)
	 * @see #makeByName(String, String, boolean, Object...)
	 * @see #makeAAF(String, Object...)
	 * @see ClassDefinition#createInstance()
	 * @see MediaClass#namespace()
	 * @see CommonConstants#AAF_XML_NAMESPACE
	 * @deprecated Use {@link Forge#makeByName(String,String,Object...)} instead
	 */
	public final static <T extends MetadataObject> T makeByName(
			String namespace,
			String typeName,
			Object... properties)
		throws NullPointerException,
			IllegalArgumentException {
				
		return Forge.makeByName(namespace, typeName, properties);
	}
	
	/**
	 * <p>Create new instances of AAF baseline metadata objects using the name of
	 * the type and its initial property values, and also without being strict about required properties.</p>
	 * 
	 * <p>For more details about this method, see the documentation for the {@link #makeByName(String, String, boolean, Object...)}
	 * method. This method is the same as calling that method with the namespace set to the 
	 * {@linkplain CommonConstants#AAF_XML_NAMESPACE AAF namespace}.</p>
	 * 
	 * @param <T> Type of value to make and return.
	 * @param typeName Name of the AAF baseline type of instance to create.
	 * @param properties List of pairs of property identifiers and property values to use to initialize the
	 * newly made value.
	 * @return New instance of the specified type initialized with the given property values.
	 * 
	 * @throws NullPointerException Cannot make a new value for a null class specification.
	 * @throws IllegalArgumentException The list of properties must be a list of property identifier
	 * and property value pairs, ie. divisible by&nbsp;2, or a summary of other errors encountered during
	 * object creation.
	 * 
	 * @see #make(Class, Object...)
	 * @see #make(Class, boolean, Object...)
	 * @see #makeByName(String, String, boolean, Object...)
	 * @see #makeByName(String, String, Object...)
	 * @see ClassDefinition#createInstance()
	 * @see MediaClass#namespace()
	 * @see CommonConstants#AAF_XML_NAMESPACE
	 * @deprecated Use {@link Forge#makeAAF(String,Object...)} instead
	 */
	public final static <T extends MetadataObject> T makeAAF(
			String typeName,
			Object... properties)
		throws NullPointerException,
			IllegalArgumentException {
		
		return Forge.makeAAF(typeName, properties);
	}
	
	/**
	 * <p>Checks whether a property of the given name is present or omitted for the given metadata object.
	 * Optional properties may be present or omitted, whereas required (non-optional) properties must
	 * always be present.</p>
	 * 
	 * @param target Metadata object to check for the presence of a property.
	 * @param propertyName Name of the property to check for.
	 * @return Is the property present for the given object (<code>true</code>) of omitted (<code>false</code>)?
	 * 
	 * @throws NullPointerException One of both of the metadata object and/or property name is/are <code>null</code>.
	 * @throws IllegalPropertyException The given property name is not known as a property of the given
	 * metadata object.
	 * 
	 * @see #omitOptionalProperty(MetadataObject, String)
	 * @see #getPropertyValue(MetadataObject, String)
	 * @see PropertyDefinition#getIsOptional()
	 * @see tv.amwa.maj.meta.PropertyDefinition#isPropertyPresent(MetadataObject)
	 */
	public final static boolean isPropertyPresent(
			MetadataObject target,
			String propertyName) 
		throws NullPointerException,
			IllegalPropertyException {
		
		if (target == null)
			throw new NullPointerException("Cannot check for for the presence of a property using a null value.");
		
		if (propertyName == null)
			throw new NullPointerException("Cannot get a property value from the given metadata object using a null property name.");
		
		ClassDefinition targetClass = Warehouse.lookForClass(target.getClass());
		try {
			PropertyDefinition targetProperty = targetClass.lookupPropertyDefinition(propertyName);
			return targetProperty.isPropertyPresent(target);
		}
		catch (BadParameterException bpe) {
			throw new IllegalPropertyException("The given property name " + propertyName + " does not match that of a known property for class " + 
					targetClass.getName() + ".");
		}
	}
	
	/**
	 * <p>Omits an optional property of the given name from the given metadata object if it is currently present.
	 * If the property is already omitted, this method does nothing. Required (non-optional) properties cannot be 
	 * omitted.</p>
	 * 
	 * @param target Metadata object instance to omit the property from.
	 * @param propertyName Name of the proeprty to omit.
	 * 
	 * @throws NullPointerException One or both of the obect and/or property name is/are <code>null</code>.
	 * @throws IllegalPropertyException A property with the given name is not a known property of the given metadata
	 * object or the property is not optional.
	 * @throws IllegalArgumentException An error occurred when trying to omit the property.
	 * 
	 * @see #isPropertyPresent(MetadataObject, String)
	 * @see #getPropertyValue(MetadataObject, String)
	 * @see PropertyNotPresentException
	 * @see tv.amwa.maj.meta.PropertyDefinition#omitOptionalProperty(MetadataObject)
	 */
	public final static void omitOptionalProperty(
			MetadataObject target,
			String propertyName) 
		throws NullPointerException,
			IllegalPropertyException,
			IllegalArgumentException {
		
		if (target == null)
			throw new NullPointerException("Cannot omit a property using a null value.");
		
		if (propertyName == null)
			throw new NullPointerException("Cannot omit a property from the given metadata object using a null property name.");
		
		ClassDefinition targetClass = Warehouse.lookForClass(target.getClass());
		try {
			PropertyDefinition targetProperty = targetClass.lookupPropertyDefinition(propertyName);
			targetProperty.omitOptionalProperty(target);
		}
		catch (BadParameterException bpe) {
			throw new IllegalPropertyException("The given property name " + propertyName + " does not match that of a known property for class " + 
					targetClass.getName() + ".");
		}	
	}
	
	// TODO set and array methods post MAJ 1.0 release
	
	/**
	 * <p>Resolve the given URI to a {@linkplain Stream stream} that can be used to access 
	 * the data at that URI according to resolver policy. The URI may be location and
	 * file-type specific, or it may be a canonical identifier for which a specific location 
	 * has already been {@linkplain #registerMapping(URI, URI) registered}.
	 * If a canonical identifier, the resolver uses a local policy to determine which
	 * location to return as a stream.</p> 
	 * 
	 * @param streamReference Identifier for the stream to resolve.
	 * @return Stream that can be used to read and write data, or <code>null</code> if
	 * the given URI cannot be resolved.
	 * 
	 * @throws NullPointerException Cannot resolve a <code>null</code> URI.
	 * 
	 * @see #resolveStream(URI)
	 * @see #getStreamResolver()
	 * @see StreamResolver#resolveStream(URI)
	 * @see java.net.URI#create(String)
	 */
	public final static Stream resolveStream(
			URI streamReference)
		throws NullPointerException{
		
		return resolver.resolveStream(streamReference);
	}

	/**
	 * <p>Resolve the given URI to a {@linkplain Stream stream} that can be used to access
	 * the data at the URI in a form that matches the given HTTP-like accept criteria. The accept criteria
	 * provide a list of MIME types and quality parameters that indicate types of data
	 * acceptable to the caller, in order of preference. The resolver may choose to use the
	 * accept criteria when selecting a stream to access, according to local resolver policy.
	 * The format of an HTTP accept criteria is defined to be the same as an HTTP accept header,
	 * which can be found in 
	 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html">section 14 of 
	 * HTTP&nbsp;1.1</a>.</p>
	 * 
	 * <p>The calling client should look at the URI of the stream returned using 
	 * {@linkplain Stream#getStreamURI()} to see if their accept request has been 
	 * observed. Note that two calls to this method with the same identifier may
	 * resolve to different streams according to the policy of the local resolved,
	 * for example to handle network routing failures.</p>
	 * 
	 * @param streamReference Identifier for the stream to resolve.
	 * @param accept Hint for the resolver as to the acceptable kinds of stream for the 
	 * calling client. An accept header must be provided and should be set to "<code>*&#x2f;*</code>"
	 * to get the same behaviour as for {@link #resolveStream(URI)}.
	 * @return Stream that can be used to read and write data, or <code>null</code> if
	 * the given URI cannot be resolved.
	 * 
	 * @see NullPointerException Cannot resolve a <code>null</code> URI and/or deal with a <code>null</code>
	 * accept header.
	 * @see IllegalArgumentException The given accept header is not valid.
	 * 
	 * @see #resolveStream(URI)
	 * @see #makeSpecific(URI, String)
	 * @see StreamResolver#resolveStream(URI, String)
	 * @see java.net.URI#create(String)
	 */
	public final static Stream resolveStream(
			URI streamReference,
			String accept)
		throws NullPointerException,
			IllegalArgumentException {
		
		return resolver.resolveStream(streamReference, accept);
	}
	
	/**
	 * <p>Make a canonical URI both local and file-type specific by using the 
	 * {@linkplain #registerMapping(URI, URI) registered mappings} of the resolver and 
	 * the HTTP-like accept criteria provided. This method is similar to {@link #resolveStream(URI, String)}
	 * except that it does not carry out the final step of resolving the provided URI to a stream.
	 * Instead, it provides the specific URI that would be used to provide access to the stream.</p>
	 * 
	 * <p>URIs that are already specific should be passed straight through.</p>
	 *  
	 * @param streamReference Identifier for the stream to resolve.
	 * @param accept Hint for the resolver as to the acceptable kinds of stream for the 
	 * calling client.
	 * @return Location and file-type specific URI for accessing the stream.
	 * 
	 * @see NullPointerException Cannot resolve a <code>null</code> URI and/or deal with a <code>null</code>
	 * accept header.
	 * @see IllegalArgumentException The given accept header is not valid.
	 * 
	 * @see #resolveStream(URI, String)
	 * @see StreamResolver#makeSpecific(URI, String)
	 * @see java.net.URI#create(String)
	 */
	public final static URI makeSpecific(
			URI streamReference,
			String accept)
		throws NullPointerException,
			IllegalArgumentException {
		
		return resolver.makeSpecific(streamReference, accept);
	}
	
	/**
	 * <p>Register a mapping between the canonical form of a URI for a stream and
	 * a location and file-type specific form. This mapping may be used by future
	 * resolutions from identifier to streams made by this resolver.</p>
	 * 
	 * @param canonicalForm Canonical representation of an identifier for a stream.
	 * @param location One possible location and file-type specific identifier for 
	 * the stream.
	 * 
	 * @throws NullPointerException One or both is the arguments is/are <code>null</code>.
	 * @throws IllegalArgumentException The given canonical identifier is location 
	 * specific and/or the given location identifier is not specific enough.
	 * 
	 * @see #resolveStream(URI)
	 * @see #resolveStream(URI, String)
	 * @see #makeSpecific(URI, String)
	 * @see StreamResolver#registerMapping(URI, URI)
	 * @see java.net.URI#create(String)
	 */
	public final static void registerMapping(
			URI canonicalForm,
			URI location)
		throws NullPointerException,
			IllegalArgumentException {
		
		resolver.registerMapping(canonicalForm, location);
	}
	
	/**
	 * <p>Remove a canonical identifier or a specific location identifier as a possible 
	 * stream resolution source. If the location is canonical, all location-specific
	 * identifiers for that identifier may also be removed.</p>
	 * 
	 * @param identifier Identifier to remove from this resolved.
	 * @return Has the identifier been successfully removed? Note that <code>false</code> is
	 * returned if the identifier is not known to this resolver.
	 * 
	 * @throws NullPointerException Cannot remove a <code>null</code> identifier.
	 * 
	 * @see StreamResolver#removeLocation(URI)
	 * @see #changeStreamResolver(StreamResolver)
	 */
	public final static boolean removeLocation(
			URI identifier)
		throws NullPointerException {
		
		return resolver.removeLocation(identifier);
	}
	
	/**
	 * <p>Returns the stream resolver currently in use by this media engine for resolving
	 * stream idenfiers into {@linkplain Stream stream} values. The default resolver
	 * is the {@linkplain LocalFileResolver local file resolver}.</p>
	 * 
	 * @return Stream resolver in use by this media engine.
	 * 
	 * @see #changeStreamResolver(StreamResolver)
	 * @see #resolveStream(URI)
	 */
	public final static StreamResolver getStreamResolver() {
		
		return resolver;
	}
	
	/**
	 * <p>Change the stream resolver currently in use by this media engine. The first
	 * call to this method will replace the default {@linkplain LocalFileResolver local
	 * file resolver}.</p>
	 * 
	 * <p>Care should be taken as mappings are not preserved between the current 
	 * stream resolver and the replacement stream resolver.</p>
	 * 
	 * @param newResolver Resolver to use instead of the current resolver.
	 * 
	 * @throws NullPointerException Cannot set a <code>null</code> stream resolver.
	 * 
	 * @see #getStreamResolver()
	 */
	public final static void changeStreamResolver(
			StreamResolver newResolver) 
		throws NullPointerException {
		
		if (resolver == null) 
			throw new NullPointerException("Cannot set a null stream resolver.");
		
		resolver = newResolver;
	}
}
