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
 * $Log: Warehouse.java,v $
 * Revision 1.8  2011/10/05 17:14:25  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.7  2011/07/27 12:25:44  vizigoth
 * Fixed import warning messages.
 *
 * Revision 1.6  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.5  2011/01/24 14:02:24  vizigoth
 * Made property global lookup table public.
 *
 * Revision 1.4  2011/01/21 17:40:34  vizigoth
 * Moved initialization of AAF extendible enumerations and types into the media engine and added public access to the class name aliases.
 *
 * Revision 1.3  2011/01/19 11:42:36  vizigoth
 * Fixes to issues found when writing tests.
 *
 * Revision 1.2  2011/01/18 09:11:50  vizigoth
 * Fixes after writing unit tests.
 *
 * Revision 1.1  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 */

package tv.amwa.maj.industry;

import static tv.amwa.maj.io.mxf.MXFConstants.RP210_NAMESPACE;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import tv.amwa.maj.constant.CodingEquationsType;
import tv.amwa.maj.constant.ColorPrimariesType;
import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.constant.OperationCategoryType;
import tv.amwa.maj.constant.PluginCategoryType;
import tv.amwa.maj.constant.TransferCharacteristicType;
import tv.amwa.maj.constant.UsageType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionObjectReference;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.meta.impl.ExtensionSchemeImpl;
import tv.amwa.maj.meta.impl.MetaDefinitionImpl;
import tv.amwa.maj.model.ApplicationObject;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.DefinitionObject;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>Warehouse with a dynamic inventory of {@linkplain ClassDefinition classes}, 
 * {@linkplain TypeDefinition types}, {@linkplain DefinitionObject definitions} and 
 * extendible enumeration items. The warehouse is the place to come looking for any
 * {@linkplain WeakReference weak referenced} item, by name or identification, known
 * to the virtual machine containing the class. Also, new items can be added to the 
 * inventory of the warehouse at runtime, enabling dynamic and loosely-coupled
 * use of the API.</p>
 * 
 * <p>The warehouse is not the place to come to make new values or instantiate objects, or
 * to manipulate and test values. Use the {@linkplain Forge forge} to make new things
 * and the {@linkplain MediaEngine media engine} to change them.</p>
 * 
 * <p>To find something in the inventory of the warehouse, use:</p>
 * 
 * <ul>
 *  <li><strong>{@linkplain ClassDefinition media classes}</strong> - Look for a class definition by 
 *  its Java interface, implementing class, name, Java class name or identification with 
 *  {@link #lookForClass(Class)}&nbsp;/ {@link #lookForClass(String)}&nbsp;/ 
 *  {@link #lookForClass(AUID)}. Note that {@linkplain tv.amwa.maj.meta.PropertyDefinition property 
 *  definitions} are accessible as part of the class that they are a member of.</li>
 *  
 *  <li><strong>{@linkplain TypeDefinitions media types}</strong> - Look for a type definition by
 *  its name or identification with {@link #lookForType(String)}&nbsp;/ 
 *  {@link #lookForType(AUID)}.</li>
 *  
 *  <li><strong>{@linkplain DefinitionObject definitions}</strong> - Look for a definition by 
 *  its Java interface/implementation or name and the definition name or identification
 *  with {@link #lookup(Class, String)}&nbsp;/ {@link #lookup(Class, AUID)}&nbsp;/ 
 *  {@link #lookup(String, String)}&nbsp;/ {@link #lookup(String, AUID)}.</li> 
 *  
 *  <li><strong>{@linkplain ExtendibleEnumerationItem extendible enumerations}</strong> - Look for an
 *  extendible enumeration by its name with {@link #lookupExtendibleEnumeration(String)} or
 *  use other methods to query details of the elements of an extendible enumeration: 
 *  {@link #extendibleEnumerationName(AUID)}, {@link #extendibleEnumerationElementName(AUID)},
 *  {@link #extendibleEnumerationElementValue(String)}, {@link #extendibleEnumerationFullName(AUID)}.</li>
 * </ul>
 * 
 * <p>To register to items onto the inventory of the warehouse, use:</p>
 * 
 * <ul>
 * 
 *  <li><strong>{@linkplain ClassDefinition media classes}</strong> - The {@link #lookForClass(Class)}
 *  and {@link #lookForClass(String)} methods will automatically register an unknown class specified
 *  using an appropriately annotated Java class.</li>
 *  
 *  <li><strong>{@linkplain TypeDefinitions media types}</strong> - Use {@link #register(TypeDefinition, String, String)}
 *  or add a collection of type definitions from static values of a class using 
 *  {@link #registerTypes(Class, String, String)}.</li>
 *  
 *  <li><strong>{@linkplain DefinitionObject definitions}</strong> - Use 
 *  {@link #register(DefinitionObject)}.</li> 
 *  
 *  <li><strong>{@linkplain ExtendibleEnumerationItem extendible enumerations}</strong> - Add new 
 *  extendible enumerations element by element using 
 *  {@link #registerExtendibleEnumerationElement(String, String, AUID)} or from static values of a 
 *  class with {@linkplain ExtendibleEnumerationItem extendible enumeration item annotations} 
 *  using {@link #registerExtendibleEnumerationElements(Class)}.</li>
 *  
 * </ul>
 * 
 * <p>To query the current inventory of the warehouse, lists and counts of the various names and types
 * of item are provided:</p>
 * 
 * <ul>
 * 
 *  <li><strong>{@linkplain ClassDefinition media classes}</strong> - {@link #getClassInventory()}
 *  and {@link #countClassDefinitions()}.</li>
 *  
 *  <li><strong>{@linkplain TypeDefinitions media types}</strong> - {@link #getTypeInventory()},
 *  {@link #getTypeDefinitions()} and {@link #countTypeDefinitions()}.</li>
 *  
 *  <li><strong>{@linkplain DefinitionObject definitions}</strong> - Generic methods 
 *  {@link #count(Class)} and {@link #inventory(Class)}.</li> 
 *  
 *  <li><strong>{@linkplain ExtendibleEnumerationItem extendible enumerations}</strong> - 
 *  {@link #getExtendibleEnumerationInventory()} and {@link #countExtendibleEnumerations()}.</li>

 * </ul>
 * 
 *
 *
 * @see TypeDefinitions
 * @see tv.amwa.maj.model.Dictionary
 * @see tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration
 * @see ExtendibleEnumerationItem
 * @see MediaClass
 * @see tv.amwa.maj.meta.ClassDefinition
 * @see tv.amwa.maj.meta.TypeDefinition
 */
public final class Warehouse {
	
	// This is the authoritative list of class definitions known to this MAJ instance
	private static final Map<AUID, ClassDefinition> idToClass =
		Collections.synchronizedMap(new HashMap<AUID, ClassDefinition>());
	private static Map<Class<?>, ClassDefinition> knownClasses = 
		Collections.synchronizedMap(new HashMap<Class<?>, ClassDefinition>());
	private static Map<String, ClassDefinition> nameToClass =
		Collections.synchronizedMap(new HashMap<String, ClassDefinition>());
	private static HashMap<String, String> classAliases = new HashMap<String, String>();

	private static final Map<AUID, PropertyDefinition> globalPropertyIdTable =
		Collections.synchronizedMap(new HashMap<AUID, PropertyDefinition>());
	
	private static Map<String, TypeDefinition> knownTypes = 
		Collections.synchronizedMap(new HashMap<String, TypeDefinition>());
	private static Map<AUID, TypeDefinition> knownTypesByID =
		Collections.synchronizedMap(new HashMap<AUID, TypeDefinition>());
	static final Class<?>[] typeDefinitionClasses = new Class<?>[] {
			TypeDefinitions.class
	};
	
	private static Map<String, TreeMap<String, AUID>> extensibleEnumerations = 
			new HashMap<String, TreeMap<String, AUID>>();
	private static Map<AUID, String> idToEnumeration =
			new HashMap<AUID, String>();
	private static Map<AUID, String> idToElementName =
			new HashMap<AUID, String>();
	final static Class<?>[] extensibleEnumerationClasses = new Class<?>[] {
			OperationCategoryType.class,
			PluginCategoryType.class,
			TransferCharacteristicType.class,
			UsageType.class,
			ColorPrimariesType.class,
			CodingEquationsType.class
	};
	
	static class ExtensionSchemeRecord {
		
		public ExtensionSchemeRecord(
				ExtensionScheme extensionScheme) {
			
			schemeID = extensionScheme.getSchemeID();
			schemeURI = extensionScheme.getSchemeURI();
			try {
				preferredPrefix = extensionScheme.getPreferredPrefix();
			}
			catch (PropertyNotPresentException pnpe) { }
			try {
				extensionDescription = extensionScheme.getExtensionDescription();
			}
			catch (PropertyNotPresentException pnpe) { }
		}
		
		AUID schemeID;
		String schemeURI;
		String preferredPrefix = null;
		String extensionDescription = null;
		
		public int hashCode() {
			
			return schemeID.hashCode();
		}
	}
	
	private static Map<AUID, ExtensionSchemeRecord> extensionSchemes =
		Collections.synchronizedMap(new HashMap<AUID, ExtensionSchemeRecord>());
	
	public final static void clear() {
		knownClasses.clear();
		nameToClass.clear();
		knownTypes.clear();
		knownTypesByID.clear();
		extensibleEnumerations.clear();
		idToEnumeration.clear();
		idToElementName.clear();
		idToClass.clear();
		globalPropertyIdTable.clear();
	}
	
	static {
		classAliases.put("Timecode", "TimecodeSegment");
		classAliases.put("Edgecode", "EdgeCodeSegment");
		classAliases.put("EdgeCode", "EdgeCodeSegment");
		classAliases.put("File", "AAFFile");
		classAliases.put("FileDescriptor", "AAFFileDescriptor");
		classAliases.put("SourceReference", "SourceReferenceSegment");
		classAliases.put("AAFObject", "InterchangeObject");
		classAliases.put("Object", "InterchangeObject");
	}

	private Warehouse() {}

	/**
	 * <p>For any given Java class, this method finds the corresponding media class definition or 
	 * creates it if it does not yet exist within the current Java virtual machine. The creation
	 * of class definitions is done by lazy evaluation as required using Java reflection and annotations. 
	 * All generated values are stored in the warehouse inventory, a static hashtable, so that once 
	 * generated the work is not repeated.</p>
	 * 
	 * <p>The values returned by this method are only as good as the annotations provided with 
	 * the sourcecode, as labelled using {@link tv.amwa.maj.industry.MediaClass} and {@link tv.amwa.maj.industry.MediaProperty}. 
	 * If an {@link tv.amwa.maj.industry.MediaClass} annotation is not present in the given class, an {@link IllegalArgumentException} is thrown. Any
	 * problems found with the {@link tv.amwa.maj.industry.MediaProperty} annotations, such as two properties having the same
	 * name, will also result in an {@link IllegalArgumentException}.</p>
	 *
	 * @param mediaClass Java class to find the media class definition of.
	 * @return Media class definition associated with the given Java class. 
	 * 
	 * @throws NullPointerException The given Java class is <code>null</code>.
	 * @throws IllegalArgumentException The given Java class is not annotated with {@link MediaClass} or 
	 * the {@link MediaProperty} annotations contain errors.
	 * 
	 * @see tv.amwa.maj.industry.MediaClass
	 * @see tv.amwa.maj.industry.MediaProperty
	 * @see MediaEngine#initializeAAF()
	 * @see MediaEngine#getClassDefinition(MetadataObject)
	 */
	public final static ClassDefinition lookForClass(
			Class<?> mediaClass) 
		throws NullPointerException,
			IllegalArgumentException {
	
		if (mediaClass == null)
			throw new NullPointerException("Cannot make a class definition from a null value.");
		
		if ((!mediaClass.isInterface()) && (mediaClass.getCanonicalName().contains("$"))) 
			mediaClass = mediaClass.getSuperclass();
		
		if (knownClasses.containsKey(mediaClass))
			return knownClasses.get(mediaClass);
		
		// If this is the first time the interface has been seen, try and find the class by name
		if (mediaClass.isInterface()) {
			
			return lookForClass(mediaClass.getName());
		}
		
		ClassDefinition classDef = tv.amwa.maj.meta.impl.ClassDefinitionImpl.forClass(mediaClass);
		
		// Note: Class def is registered in ClassDefinitionImpl
		
		knownClasses.put(mediaClass, classDef);
		
		for ( Class<?> implementing : mediaClass.getInterfaces() )
			if (!implementing.getCanonicalName().startsWith("java"))
				knownClasses.put(implementing, classDef);
				
		return classDef;
	}

	public final static void register(
			ClassDefinition classDefinition)
		throws NullPointerException {
		
		if (classDefinition == null)
			throw new NullPointerException("Cannot register a class definition using a null value.");
		
		idToClass.put(classDefinition.getAUID(), classDefinition);
		
		nameToClass.put(classDefinition.getName(), classDefinition);
		
		try {
			if (classDefinition.getJavaImplementation() != null) {
				if (!(ApplicationObject.class.isAssignableFrom(classDefinition.getJavaImplementation())))
					nameToClass.put(classDefinition.getJavaImplementation().getCanonicalName(), classDefinition);
			}
			nameToClass.put(classDefinition.getSymbol(), classDefinition);
			if ((classDefinition.getNamespace() != null) && (classDefinition.getNamespace().length() > 0)) {
				nameToClass.put("{" + classDefinition.getNamespace() + "}" + classDefinition.getSymbol(), classDefinition);
				nameToClass.put("{" + classDefinition.getNamespace() + "}" + classDefinition.getName(), classDefinition);
			}
			putAliases(classDefinition);
		} catch (PropertyNotPresentException e) {
			// No worry.
		}
	}
	
	/**
	 * <p>Put class definitions into the class name lookup table only if they do not overwrite
	 * the defined name or symbol name of another class.</p>
	 * 
	 * @param severalNames The property definition to check.
	 */
	private static void putAliases(
			ClassDefinition severalNames) {
		
		if (severalNames == null) return;
		if (severalNames.getAliases() == null) return;
		
		for ( String alias : severalNames.getAliases() ) {
			if (nameToClass.containsKey(alias)) {
				ClassDefinition checkBeforeRemoving = nameToClass.get(alias);
				if ((alias.equals(checkBeforeRemoving.getName())) ||
						(alias.equals(checkBeforeRemoving.getSymbol()))) {
					System.err.println("Warning: Cannot use alias " + alias + " for class " + 
							severalNames.getName() + " because it clashes with another property name or symbol.");
					continue;
				}
					
			}
			
			classAliases.put(alias, "{" + severalNames.getNamespace() + "}" + severalNames.getSymbol());
			classAliases.put("{" + severalNames.getNamespace() + "}" + alias, "{" + severalNames.getNamespace() + "}" + severalNames.getSymbol());
		}
	}

	/**
	 * <p>Finds and returns a class definition for the given media class name. Firstly, the search takes place using 
	 * the class name specified in the AAF object specification and then by using a Java class name.</p>
	 * 
	 * <p>The search order used by this method is:</p>
	 * 
	 * <ol>
	 *  <li>Is the name an alias used in Java for an AAF class name in the specification? In which case, 
	 *  substitute the given Java-like name with the AAF alias. Look for the class in the current cache and, 
	 *  if present, return it.</li>
	 *  <li>If the given class name already contains a path separator, use it directly for a call to
	 *  {@link java.lang.Class#forName(String)} followed by 
	 *  {@link #lookForClass(Class)} with the result. If the given class name represents an interface,
	 *  an attempt is made to find the implementation by naming convention. Return the value.</li>
	 *  <li>If the class name contains no path separator character, prepend MAJ API package names to the
	 *  front of it and call {@link java.lang.Class#forName(String)} followed by 
	 *  {@link #lookForClass(Class)} with the result. Return the value.</li>
	 * </ol>
	 * 
	 * <p>Once the class has been located and linked into the running virtual machine, the {@link #lookForClass(Class)}
	 * method is called.</p>
	 * 
	 * @param name Name of the class definition to find, specified using a name from the AAF object specification,
	 * a MAJ API class name or a fully qualified java class name.
	 * @return Class definition for the given name. 
	 * 
	 * @throws NullPointerException The given class name is <code>null</code>.
	 * @throws IllegalArgumentException The given class name could not be resolved to an AAF class.
	 * 
	 * @see java.lang.Class#forName(java.lang.String)
	 */
	public final static ClassDefinition lookForClass(
			String name) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (name == null)
			throw new NullPointerException("Cannot retrieve a class definition from a null name.");
		
		if (classAliases.containsKey(name)) name = classAliases.get(name);
		ClassDefinition fromName = nameToClass.get(name);

		if (fromName != null)
			return fromName;
		
		Class<?> guess;
		String guessedName = name;
		
		if (name.indexOf('.') >= 0) {
			try {
				guess = Class.forName(guessedName);
				if (guess.isInterface()) {
					int lastDot = name.lastIndexOf('.');
					guessedName = name.substring(0, lastDot) + ".impl" + 
							name.substring(lastDot) + "Impl";
					guess = Class.forName(guessedName);
				}
					
				return lookForClass(guess);
			}
			catch (Exception e) {
				throw new IllegalArgumentException("Unable to find a media class with full pathname " + name + ".", e);
			}
		}

		try {
			guessedName = "tv.amwa.maj.model.impl." + name + "Impl";
			guess = Class.forName(guessedName);
			if (!guessedName.equals(name)) 
				return lookForClass(guess);
			else
				throw new IllegalArgumentException("Unable to find a media class called " + name);
		}
		catch (ClassNotFoundException cnfe) {
			try {
				guessedName = name;
				guessedName = "tv.amwa.maj.meta.impl." + name + "Impl";
				guess = Class.forName(guessedName);
				return lookForClass(guess);
			}
			catch (Exception e) { 
				throw new IllegalArgumentException("Unable to find a media class called " + name + ": " + e.getClass().getName() + ": " + e.getMessage());
			}
		}
		catch (Exception e) { 
			throw new IllegalArgumentException("Unable to find a media class caled " + name + ": "+ e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * <p>Returns a collection of all classes known in this warehouse, by name.</p>
	 * 
	 * <p>Note this methods excludes some classes registered only for the mechanics of reading
	 * and writing MXF files.</p>
	 * 
	 * @return Collection of all classes known in this warehouse, by name.
	 * 
	 * @see #countClassDefinitions()
	 */
	public final static Collection<String> getClassInventory() {
		
		SortedSet<String> classNames = new TreeSet<String>();
		for ( ClassDefinition currentClass : getClassDefinitions() )
			classNames.add(currentClass.getName());
		
		return classNames;
	}

	public final static Collection<ClassDefinition> getClassDefinitions() {
		List<ClassDefinition> classDefintions = new ArrayList<ClassDefinition>();
		
		for(ClassDefinition classDefinition: idToClass.values()) {
			// Excluding the MXF incidental stuff
			if(!classDefinition.getNamespace().equals(RP210_NAMESPACE)) {
				classDefintions.add(classDefinition);
			}
		}
		return classDefintions;  
	}

	/**
	 * <p>Count of the number of different kinds of class in this warehouse.</p>
	 * 
	 * <p>Note that this method includes a count of classes registered for the mechanics
	 * of reading and writing MXF files. To exclude these from the count, use:</p>
	 * 
	 * <center>{@link #getClassInventory()}<code>.size()</code></center>
	 * 
	 * @return Count of the number of different kinds of class in this warehouse.
	 * 
	 * @see #getClassInventory()
	 */
	public final static int countClassDefinitions() {
		
		return idToClass.size();
	}

	/**
	 * <p>Looks up and returns the {@linkplain ClassDefinition class definition} in this warehouse
	 * with the given identification. If no class definition with the given identification can be
	 * found, a value of <code>null</code> is returned.</p>
	 * 
	 * @param identification Identifier for the class.
	 * @return Instance of class definition representing the class.
	 * 
	 * @throws NullPointerException Cannot resolve a registered class definition using a <code>null</code>
	 *  value.
	 */
	public final static ClassDefinition lookForClass(
			AUID identification) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot resolve a registered class definition using a null value.");
		
		byte[] classIdBytes = identification.getAUIDValue();
		if (classIdBytes[13] == 0x53) { // If a local set ID, convert to a class or element instance key
			classIdBytes[13] = 0x06;
			identification = new AUIDImpl(classIdBytes);
		}
		
		return idToClass.get(identification);
	}
	
	/**
	 * <p>Looks up a {@linkplain tv.amwa.maj.model.DefinitionObject definition} by its type and its name 
	 * in the inventory of the warehouse. The definition only has to be known to the Java local machine and 
	 * is not necessarily stored in an AAF {@linkplain tv.amwa.maj.model.Dictionary dictionary}.</p>
	 * 
	 * <p>For example, to find the correct kind of {@linkplain DataDefinition data definition} for a picture,
	 * call:</p>
	 * 
	 * <p>{@code DataDefinition pictureData = Warehouse.lookup(DataDefinition.class, "Picture");}</p> 
	 * 
	 * @param <T> All kinds of {@linkplain DefinitionObject definition objects}.
	 * @param definitionType Specific kind of definition required.
	 * @param definitionName Name of the definition of the given kind to find.
	 * @return Definition corresponding to the given type and name, or <code>null</code> if the 
	 * definition name is not known for the given type.
	 * 
	 * @throws NullPointerException One or both of the given definition type or definition name 
	 * is/are <code>null</code>.
	 * 
	 * @see #lookup(Class, AUID)
	 * @see #lookup(String, AUID)
	 * @see #lookup(String, String)
	 * @see WeakReference
	 * @see tv.amwa.maj.model.Dictionary
	 */
	@SuppressWarnings("unchecked")
	public final static <T extends DefinitionObject> T lookup(
			Class<T> definitionType,
			String definitionName) 
		throws NullPointerException {
		
		if (definitionType == null)
			throw new NullPointerException("Cannot lookup a definition from a null definition type.");
		if (definitionName == null)
			throw new NullPointerException("Cannot lookup a definition from a null name.");
		
		if (definitionType.isInterface()) {
			ClassDefinition correspondingClass = lookForClass(definitionType);
			definitionType = (Class<T>) correspondingClass.getJavaImplementation();
		}
		
		try {
			Method forName = definitionType.getMethod("forName", String.class);
			return (T) forName.invoke(null, definitionName);
		}
		catch (Exception e) { 
			return null;
		}
	}
	
	/**
	 * <p>Looks up a {@linkplain tv.amwa.maj.model.DefinitionObject definition} by its type and its identification 
	 * in the inventory of the warehouse. The definition only has to be known to the Java local machine and 
	 * is not necessarily stored in an AAF {@linkplain tv.amwa.maj.model.Dictionary dictionary}.</p>
	 * 
	 * <p>For example, to find the correct kind of {@linkplain DataDefinition data definition} for a picture,
	 * call:</p>
	 * 
	 * <pre>{@code 
	 * DataDefinition pictureData = Warehouse.lookup(
	 *         tv.amwa.maj.model.DataDefinition.class, 
	 *         Forge.parseAUID("urn:smpte:ul:060x0e2b34.04010101.01030202.01000000"));}</pre> 
	 * 
	 * @param <T> All kinds of {@linkplain DefinitionObject definition objects}.
	 * @param definitionType Specific kind of definition required.
	 * @param definitionID Identification of the definition of the given kind to find.
	 * @return Definition corresponding to the given type and identification, or <code>null</code> if the 
	 * definition name is not known for the given type.
	 * 
	 * @throws NullPointerException One or both of the given definition type or definition name 
	 * is/are <code>null</code>.
	 * 
	 * @see #lookup(Class, String)
	 * @see #lookup(String, AUID)
	 * @see #lookup(String, String)
	 * @see WeakReference
	 * @see tv.amwa.maj.model.Dictionary
	 */
	@SuppressWarnings("unchecked")
	public final static <T extends DefinitionObject> T lookup(
			Class<T> definitionType,
			AUID definitionID) 
		throws NullPointerException {
		
		if (definitionType == null)
			throw new NullPointerException("Cannot lookup a definition from a null definition type.");
		if (definitionID== null)
			throw new NullPointerException("Cannot lookup a definition from a null identification.");
		
		if (definitionType.isInterface()) {
			ClassDefinition correspondingClass = lookForClass(definitionType);
			definitionType = (Class<T>) correspondingClass.getJavaImplementation();
		}
		
		try {
			Method forName = definitionType.getMethod("forIdentification", AUID.class);
			return (T) forName.invoke(null, definitionID);
		}
		catch (Exception e) { 
			return null;
		}
	}
	
	/**
	 * <p>Looks up a {@linkplain tv.amwa.maj.model.DefinitionObject definition} by its type name and its name 
	 * in the inventory of the warehouse. The definition only has to be known to the Java local machine and 
	 * is not necessarily stored in an AAF {@linkplain tv.amwa.maj.model.Dictionary dictionary}.</p>
	 * 
	 * <p>For example, to find the correct kind of {@linkplain DataDefinition data definition} for a picture,
	 * call:</p>
	 * 
	 * <p>{@code DataDefinition pictureData = Warehouse.lookup("DataDefinition", "Picture");}</p> 
	 * 
	 * @param <T> All kinds of {@linkplain DefinitionObject definition objects}.
	 * @param definitionTypeName Name of the specific kind of definition required.
	 * @param definitionName Name of the definition of the given kind to find.
	 * @return Definition corresponding to the given type name and name, or <code>null</code> if the 
	 * definition name is not known for the given type.
	 * 
	 * @throws NullPointerException One or both of the given definition type or definition name 
	 * is/are <code>null</code>.
	 * @throws IllegalArgumentException The given type name does not match one knwon in the class inventory
	 * or is not a kind of {@linkplain DefinitionObject definition}.
	 * 
	 * @see #lookup(Class, AUID)
	 * @see #lookup(String, AUID)
	 * @see #lookup(Class, String)
	 * @see WeakReference
	 * @see tv.amwa.maj.model.Dictionary
	 */
	@SuppressWarnings("unchecked")
	public final static <T extends DefinitionObject> T lookup(
			String definitionTypeName,
			String definitionName) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (definitionTypeName == null)
			throw new NullPointerException("Cannot lookup a definition using a null definition type name.");
		if (definitionName == null)
			throw new NullPointerException("Cannot lookup a definition using a null definition name.");
		
		Class<?> correspondingClass = lookForClass(definitionTypeName).getJavaImplementation();
//		if (DefinitionObject.class.isAssignableFrom(correspondingClass))
		if (correspondingClass.isAssignableFrom(DefinitionObject.class))
			throw new IllegalArgumentException("The given class name does not map to a definition.");
		
		return lookup(
				(Class<T>) correspondingClass,
				definitionName);
	}
	
	/**
	 * <p>Looks up a {@linkplain tv.amwa.maj.model.DefinitionObject definition} by its type name and its 
	 * identification in the inventory of the warehouse. The definition only has to be known to the Java 
	 * local machine and is not necessarily stored in an AAF {@linkplain tv.amwa.maj.model.Dictionary dictionary}.</p>
	 * 
	 * <p>For example, to find the correct kind of {@linkplain DataDefinition data definition} for a picture,
	 * call:</p>
	 * 
	 * <pre>{@code 
	 * DataDefinition pictureData = Warehouse.lookup(
	 *         "DataDefinition", 
	 *         Forge.parseAUID("urn:smpte:ul:060x0e2b34.04010101.01030202.01000000"));}</pre> 
	 * 
	 * @param <T> All kinds of {@linkplain DefinitionObject definition objects}.
	 * @param definitionTypeName Name of the specific kind of definition required.
	 * @param definitionID Identification of the definition of the given kind to find.
	 * @return Definition corresponding to the given type name and identification, or <code>null</code> if the 
	 * definition name is not known for the given type.
	 * 
	 * @throws NullPointerException One or both of the given definition type name or definition identification 
	 * is/are <code>null</code>.
	 * 
	 * @see #lookup(Class, String)
	 * @see #lookup(Class, AUID)
	 * @see #lookup(String, String)
	 * @see WeakReference
	 * @see tv.amwa.maj.model.Dictionary
	 */
	@SuppressWarnings("unchecked")
	public final static <T extends DefinitionObject> T lookup(
			String definitionTypeName,
			AUID definitionID) 
		throws NullPointerException,
			IllegalArgumentException {

		if (definitionTypeName == null)
			throw new NullPointerException("Cannot lookup a definition using a null definition type name.");
		if (definitionID == null)
			throw new NullPointerException("Cannot lookup a definition using a null definition name.");
		
		Class<?> correspondingClass = lookForClass(definitionTypeName).getJavaImplementation();
		if (correspondingClass.isAssignableFrom(DefinitionObject.class))
			throw new IllegalArgumentException("The given class name does not map to a definition.");
		
		return lookup(
				(Class<T>) correspondingClass, 
				definitionID);
	}

	/**
	 * <p>Registers the given {@linkplain DefinitionObject definition} with the inventory of this 
	 * warehouse.</p>
	 * 
	 * @param definition Definition to be placed onto the warehouse inventory.
	 * @return <code>true</code> if the registration is successful, <code>false</code> if the
	 * definition is already registered and <code>null</code> if an error occurred and the 
	 * registration failed.
	 * 
	 * @throws NullPointerException The given definition is <code>null</code>.
	 * 
	 * @see #lookup(Class, String)
	 * @see #lookup(Class, AUID)
	 */
	public final static Boolean register(
			DefinitionObject definition) 
		throws NullPointerException {
		
		if (definition == null)
			throw new NullPointerException("Cannot register a new definition using a null value.");
		
		ClassDefinition definitionClassDefinition = lookForClass(definition.getClass());
		Class<?> definitionImplementation = definitionClassDefinition.getJavaImplementation();
		
		String registrationName = "register" + definitionClassDefinition.getName();
		Boolean alreadyRegistered = null;
		try {
			Method forName = null;
			for ( Class<?> interfaceToCheck : definitionImplementation.getInterfaces() ) {
				try {	
					forName = definitionImplementation.getMethod(registrationName, interfaceToCheck);
				}
				catch (NoSuchMethodException nsme) { }
				if (forName != null) break;
			}
			alreadyRegistered = (Boolean) forName.invoke(null, definition);
		}
		catch (Exception e) {
			return null;
		}
		
		return !alreadyRegistered;
	}
	
	/**
	 * <p>Counts the number of definitions in the inventory of the given definition type.</p>
	 * 
	 * @param <T> All kinds of {@linkplain DefinitionObject definition types}.
	 * @param definitionType Definition type to count the number of known definitions for.
	 * @return Number of definitions in the inventory, or zero if the definition type is not
	 * known.
	 * 
	 * @throws NullPointerException The given definition type is <code>null</code>.
	 * 
	 * @see #countClassDefinitions()
	 * @see #countTypeDefinitions()
	 * @see #countExtendibleEnumerations()
	 * @see #inventory(Class)
	 */
	@SuppressWarnings("unchecked")
	public final static <T extends DefinitionObject> int count(
			Class<T> definitionType)
		throws NullPointerException {
		
		if (definitionType == null)
			throw new NullPointerException("Cannot count definitions from a null definition type.");

		if (definitionType.isInterface()) {
			ClassDefinition correspondingClass = lookForClass(definitionType);
			definitionType = (Class<T>) correspondingClass.getJavaImplementation();
		}
		
		try {
			Method forName = definitionType.getMethod("count");
			return (Integer) forName.invoke(null);
		}
		catch (Exception e) { 
			return 0;
		}		
	}
	
	/**
	 * <p>Returns a collection of the names of the definitions of the given definition type.</p>
	 * 
	 * @param <T> All kinds of {@linkplain DefinitionObject definition types}.
	 * @param definitionType Definition type to list the elements of.
	 * @return List of the names of the definitions of the given type, which is empty is the type is known
	 * and no definitions are registered and <code>null</code> if the type is not known.
	 * 
	 * @throws NullPointerException The given definition type is <code>null</code>.
	 * 
	 * @see #getTypeInventory()
	 * @see #getClassInventory()
	 * @see #getExtendibleEnumerationInventory()
	 * @see #count(Class)
	 */
	@SuppressWarnings("unchecked")
	public final static <T extends DefinitionObject> Collection<String> inventory(
			Class<T> definitionType)
		throws NullPointerException {

		if (definitionType == null)
			throw new NullPointerException("Cannot lookup a definition from a null definition type.");

		if (definitionType.isInterface()) {
			ClassDefinition correspondingClass = lookForClass(definitionType);
			definitionType = (Class<T>) correspondingClass.getJavaImplementation();
		}
		
		try {
			Method forName = definitionType.getMethod("inventory");
			return (Collection<String>) forName.invoke(null);
		}
		catch (Exception e) { 
			return null;
		}		
	}
	
	/**
	 * <p>Looks through the given class for public static fields of {@link TypeDefinition type definitions}
	 * and adds these to the warehouse inventory.</p>
	 * 
	 * <p>This method picks out static fields in the given class and adds them to the cache of known
	 * types in this class. Here is an example of the declaration of a {@linkplain tv.amwa.maj.integer.UInt8 UInt8}
	 * value:</p>
	 * 
	 * <pre>
	 *   public final static TypeDefinitionInteger UInt8 = new TypeDefinitionInteger(
	 *     new AUID(0x01010100, (short) 0x0000, (short) 0x0000,
	 *              new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 } ),
	 *     "UInt8", (byte) 1, false);
	 * </pre>
	 *
	 * @param typeClass Class to check for static type definition instances.
	 * @param namespace Namespace in which the type definition is defined.
	 * @param prefix Preferred prefix for symbols in the given namespace.
	 * 
	 * @throws NullPointerException The given class is <code>null</code>.
	 * 
	 * @see #register(TypeDefinition, String, String)
	 * @see #lookForType(AUID)
	 * @see #lookForType(String)
	 * @see tv.amwa.maj.meta.MetaDefinition#getNamespace()
	 * @see tv.amwa.maj.meta.MetaDefinition#getPrefix()
	 */
	public final static void registerTypes(
			Class<?> typeClass,
			String namespace,
			String prefix) 
		throws NullPointerException {
		
		if (typeClass == null)
			throw new NullPointerException("Cannot add type definitions from a null class.");
		if (namespace == null)
			throw new NullPointerException("Cannot add type definitions to the warehouse with a null namespace.");
		if (prefix == null)
			throw new NullPointerException("Cannot add type definitions to the warehouse with a null prefix.");
		
		for ( Field field : typeClass.getFields() ) {
		
			// System.out.println(field.getName());
			
			int modifiers = field.getModifiers();
			if ((!Modifier.isPublic(modifiers)) || (!Modifier.isStatic(modifiers))) continue;
			
			try {
				Object value = field.get(null);
				if (value instanceof TypeDefinition) {
	
					TypeDefinition foundType = (TypeDefinition) value;
					register(foundType, namespace, prefix);
				}
			}
			catch (IllegalAccessException iae) { /* If you can't access it, don't add it! */ }
		}
	
		/* for ( String typeName : knownTypes.keySet() )
			if (typeName.contains("SourceReference")) System.out.println(typeName); */
	}

	/**
	 * <p>Finds the type definition for the given name from the warehouse inventory. Type definition names are
	 * the same as used in the AAF specification, reference implementation meta dictionary or SMPTE 
	 * type dictionary, for example:</p>
	 * 
	 * <ul>
	 *  <li><code>UInt32</code> is a unique name;</li>
	 *  <li><code>Int8Array</code> is a unique name;</li>
	 *  <li><code>TypeDefinitionWeakReferenceVector</code> returns the same type as:
	 *   <ul>
	 *    <li><code>WeakReferenceVector of TypeDefinition</code> and</li>
	 *    <li><code>WeakReferenceVector&lt;TypeDefinition&gt;</code>;</li>
	 *   </ul></li>
	 *  <li><code>CompositionPackageStrongReference</code> returns the same type as:
	 *   <ul>
	 *    <li><code>StrongReference to CompositionPackage</code> and</li>
	 *    <li><code>StrongReference&lt;CompositionPackage&gt;</code></li>
	 *   </ul></li>
	 * </ul>
	 *
	 * <p>If a corresponding type cannot be found, this method returns <code>null</code>.</p>
	 * 
	 * @param typeName Name of the type definition to check for.
	 * @return Type definition corresponding to the given name.
	 * 
	 * @throws NullPointerException The given type name is <code>null</code>.
	 * 
	 * @see #lookForClass(AUID)
	 * @see #getTypeInventory()
	 */
	public final static TypeDefinition lookForType(
			String typeName) 
		throws NullPointerException {
		
		if (typeName == null)
			throw new NullPointerException("Cannot find a type definition with a null name value.");
		
		if (!knownTypes.containsKey(typeName))
			registerTypes(TypeDefinitions.class, CommonConstants.AAF_XML_NAMESPACE, CommonConstants.AAF_XML_PREFIX);
		return knownTypes.get(typeName);
	}

	/**
	 * <p>Finds the type definition with the given {@linkplain tv.amwa.maj.record.AUID AUID} identification
	 * from the warehouse inventory. If a type definition matching the given identification cannot be found, 
	 * <code>null</code> is returned.</p>
	 *
	 * @param identification AUID to use to lookup a type definition.
	 * @return Type definition matching the identification.
	 * 
	 * @throws NullPointerException The given type identifier is <code>null</code>.
	 * 
	 * @see #lookForType(String)
	 * @see #getTypeInventory()
	 */
	public final static TypeDefinition lookForType(
			AUID identification) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot find a type definition with a null identification value.");
		
		return knownTypesByID.get(identification);
	}

	/**
	 * <p>Count of the number of different type definitions in this warehouse.</p>
	 * 
	 * @return Number of different type definitions in this warehouse.
	 * 
	 * @see #getTypeDefinitions()
	 * @see #getTypeInventory()
	 * @see #countClassDefinitions()
	 */
	public static int countTypeDefinitions() {
	
		return knownTypesByID.size();
	}

	/**
	 * <p>Add or replace a given type definition in this warehouse.</p>
	 * 
	 * @param typeDefinition Type definition to add to the warehouse.
	 * @param namespace Namespace in which the type definition is defined.
	 * @param prefix Preferred prefix for symbols in the given namespace.
	 * 
	 * @throws NullPointerException Cannot add a type to the warehouse 
	 * using a <code>null</code> value.
	 * 
	 * @see #registerTypes(Class, String, String)
	 * @see tv.amwa.maj.meta.MetaDefinition#getNamespace()
	 * @see tv.amwa.maj.meta.MetaDefinition#getPrefix()
	 */
	public final static void register(
			TypeDefinition typeDefinition,
			String namespace,
			String prefix) 
		throws NullPointerException {
		
		if (typeDefinition == null)
			throw new NullPointerException("Cannot add a type to the warehouse using a null value.");
		if (namespace == null)
			throw new NullPointerException("Cannot add a type to the warehouse using a null value.");
		if (prefix == null)
			throw new NullPointerException("Cannot add a type to the warehouse using a null value.");
		
		typeDefinition.setNamespace(namespace);
		((MetaDefinitionImpl) typeDefinition).setPrefix(prefix);
		String namespaceWithBrackets = "{" + namespace + "}";
		
		knownTypes.put(typeDefinition.getName(), typeDefinition);
		knownTypes.put(namespaceWithBrackets + typeDefinition.getName(), typeDefinition);
		knownTypesByID.put(typeDefinition.getAUID(), typeDefinition);
		
		String targetTypeName = null;
		String angleName = null;
		String ofToName = null;
		
		switch (typeDefinition.getTypeCategory()) {
		
		case StrongObjRef:
		case WeakObjRef:
			targetTypeName = ((TypeDefinitionObjectReference) typeDefinition).getObjectType().getName();
			angleName = "StrongReference<" + targetTypeName + ">";
			ofToName = "StrongReference to " + targetTypeName;
			knownTypes.put(angleName, typeDefinition);
			knownTypes.put(ofToName, typeDefinition);
			knownTypes.put(namespaceWithBrackets + angleName, typeDefinition);
			knownTypes.put(namespaceWithBrackets + ofToName, typeDefinition);
			break;
			
		case Set:
			
			if (typeDefinition.getName().endsWith("WeakReferenceSet")) {
	
				TypeDefinitionObjectReference referenceType =
					(TypeDefinitionObjectReference) 
						((TypeDefinitionSet) typeDefinition).getElementType();
				targetTypeName = referenceType.getObjectType().getName();
				
				angleName = "WeakReferenceSet<" + targetTypeName + ">";
				ofToName = "WeakReferenceSet of " + targetTypeName;
				knownTypes.put(angleName, typeDefinition);
				knownTypes.put(ofToName, typeDefinition);
				knownTypes.put(namespaceWithBrackets + angleName, typeDefinition);
				knownTypes.put(namespaceWithBrackets + ofToName, typeDefinition);
				break;
			}
			
			if (typeDefinition.getName().endsWith("StrongReferenceSet")) {
				
				TypeDefinitionObjectReference referenceType =
					(TypeDefinitionObjectReference) 
						((TypeDefinitionSet) typeDefinition).getElementType();
				targetTypeName = referenceType.getObjectType().getName();

				angleName = "StrongReferenceSet<" + targetTypeName + ">";
				ofToName = "StrongReferenceSet of " + targetTypeName;
				knownTypes.put(angleName, typeDefinition);
				knownTypes.put(ofToName, typeDefinition);
				knownTypes.put(namespaceWithBrackets + angleName, typeDefinition);
				knownTypes.put(namespaceWithBrackets + ofToName, typeDefinition);
				break;
			}
			break;
			
		case VariableArray:
			
			if (typeDefinition.getName().endsWith("StrongReferenceVector")) {
				
				TypeDefinitionObjectReference referenceType =
					(TypeDefinitionObjectReference) 
						((TypeDefinitionVariableArray) typeDefinition).getType();
				targetTypeName = referenceType.getObjectType().getName();
				
				angleName = "StrongReferenceVector<" + targetTypeName + ">";
				ofToName = "StrongReferenceVector of " + targetTypeName;
				
				knownTypes.put(angleName, typeDefinition);
				knownTypes.put(ofToName, typeDefinition);
				knownTypes.put(namespaceWithBrackets + angleName, typeDefinition);
				knownTypes.put(namespaceWithBrackets + ofToName, typeDefinition);
				break;
			}
			
			if (typeDefinition.getName().endsWith("WeakReferenceVector")) {
				
				TypeDefinitionObjectReference referenceType =
					(TypeDefinitionObjectReference) 
						((TypeDefinitionVariableArray) typeDefinition).getType();
				targetTypeName = referenceType.getObjectType().getName();

				angleName = "WeakReferenceVector<" + targetTypeName + ">";
				ofToName = "WeakReferenceVector of " + targetTypeName;
				
				knownTypes.put(angleName, typeDefinition);
				knownTypes.put(ofToName, typeDefinition);
				knownTypes.put(namespaceWithBrackets + angleName, typeDefinition);
				knownTypes.put(namespaceWithBrackets + ofToName, typeDefinition);
				break;
			}

			break;
		default:
			break;
		}

	}

	/**
	 * <p>Returns a collection of all {@linkplain tv.amwa.maj.meta.TypeDefinition types} in the 
	 * inventory of this warehouse, by name.</p>
	 * 
	 * @return Collection of all types in the inventory of this warehouse, by name.
	 * 
	 * @see #getClassInventory()
	 * @see #getExtendibleEnumerationInventory()
	 * @see #getTypeDefinitions()
	 * @see #countTypeDefinitions()
	 */
	public final static Collection<String> getTypeInventory() {
		
		SortedSet<String> inventory = new TreeSet<String>();
		for ( AUID typeID : knownTypesByID.keySet() )
			inventory.add(knownTypesByID.get(typeID).getName());
		
		return inventory;
	}
	
	/**
	 * <p>Returns a collection containing all the different kinds of type definitions in this
	 * warehouse.</p>
	 * 
	 * @return Collection of all different kinds of type definitions in this
	 * warehouse.
	 * 
	 * @see #countTypeDefinitions()
	 * @see #getTypeInventory()
	 */
	public final static Collection<TypeDefinition> getTypeDefinitions() {
		
		return knownTypesByID.values();
	}

	/**
	 * <p>Scan the given class for {@linkplain tv.amwa.maj.record.AUID AUID} values annotated
	 * as the definitions of {@linkplain ExtendibleEnumerationItem extendible enumeration values}.
	 * Any values find are added to the inventory of the warehouse for use by applications sharing
	 * the same Java virtual machine.</p>
	 * 
	 * <p>To create an extendible enumeration element in MAJ, you can write an interface with 
	 * a set of static {@linkplain tv.amwa.maj.record.AUID AUID} values and annotate these as
	 * follows:</p>
	 * 
	 * <pre>
	 * {@code @ExtendibleEnumerationItem(target = "OperationCategory")
	 *  public final static AUID Effect = Forge.makeAUID(
	 *          0x0d010102, (short) 0x0101, (short) 0x0100,
	 *          new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 }); }
	 * </pre>
	 * 
	 * @param extendibleEnumerationClass Class or interface containing extendible enumeration
	 * element definitions.
	 * @return The number of elements added by this operation. A value of zero may indicate
	 * an underlying problem.
	 * 
	 * @throws NullPointerException The given class to add extendible enumeration values from is 
	 * <code>null</code>.
	 * @throws IllegalArgumentException Unable to access the annotations of the given class.
	 * 
	 * @see ExtendibleEnumerationItem
	 * @see tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration
	 * @see tv.amwa.maj.constant.OperationCategoryType
	 * @see #registerExtendibleEnumerationElement(String, String, AUID)
	 * @see #lookupExtendibleEnumeration(String)
	 */
	public final static int registerExtendibleEnumerationElements(
			Class<?> extendibleEnumerationClass) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (extendibleEnumerationClass == null)
			throw new NullPointerException("Cannot add extensible enumeration items from a null class value.");
		
		Field[] fields = extendibleEnumerationClass.getFields();
		
		int count = 0;
		
		try {
			for ( Field field : fields ) {
				ExtendibleEnumerationItem item = field.getAnnotation(ExtendibleEnumerationItem.class);
				if (item == null) continue;
				
				registerExtendibleEnumerationElement(item.target(), field.getName(), (AUID) field.get(null));
				count++;
			}
		}
		catch (IllegalAccessException iae) {
			throw new IllegalArgumentException("When trying to read extendible enueratuin values, the fields of the given class are not accessible: " +
					iae.getMessage(), iae);
		}
		
		return count;
	}

	/**
	 * <p>Add an element to an {@linkplain tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration extendible 
	 * enumeration} in the inventory of this warehouse. The element is added by the name of the extendible
	 * enumeration to which it is to be added, the element's name and the element's identification.</p>
	 * 
	 * <p>The value will overwrite previous values known in the inventory. The behaviour will be to
	 * degrade gracefully, with the possibility of two names resolving to one identification.</p>
	 * 
	 * @param extendibleEnumerationName Name of the extendible enumeration to which the element is to be
	 * added. If no such enumeration is known, one will be created.
	 * @param elementName Name of the new element to add to the inventory.
	 * @param elementValue Identification of the new element to add to the inventory.
	 * @return Is the element replacing an element that is already present, either by key or value? A value
	 * of <code>true</code> indicates a replacement and may result in an undesirable side effect.
	 * 
	 * @throws NullPointerException One or more of the arguments in <code>null</code>.
	 * @throws IllegalArgumentException The given element value is already in use for a different 
	 * extendible enumeration.
	 * 
	 * @see tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration
	 * @see #registerExtendibleEnumerationElements(Class)
	 * @see ExtendibleEnumerationItem
	 */
	public final static boolean registerExtendibleEnumerationElement(
			String extendibleEnumerationName,
			String elementName,
			AUID elementValue) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (extendibleEnumerationName == null)
			throw new NullPointerException("Cannot register an element with a null name for an extendbiel enumeration.");
		if (elementName == null)
			throw new NullPointerException("Cannot register an element with a null element name.");
		if (elementValue == null)
			throw new NullPointerException("Cannot register an element with a null element value.");
		
		if (idToEnumeration.containsKey(elementValue)) {
			if (!idToEnumeration.get(elementValue).equals(extendibleEnumerationName))
				throw new IllegalArgumentException("Cannot register an extendible enumeration identifier in use by existing extendible enumeration " +
						idToEnumeration.get(elementValue) + ".");
		}
		
		if (!extensibleEnumerations.containsKey(extendibleEnumerationName))
			extensibleEnumerations.put(
					extendibleEnumerationName, new TreeMap<String, AUID>());
		
		TreeMap<String, AUID> expanding = 
			extensibleEnumerations.get(extendibleEnumerationName);
	
		boolean replacement = (idToElementName.containsKey(elementValue)) ||
			(expanding.containsKey(elementName));
		
		expanding.put(elementName, elementValue);

		idToEnumeration.put(elementValue, extendibleEnumerationName);
		idToElementName.put(elementValue, elementName);
		
		return replacement;
	}

	/**
	 * <p>Returns a sorted map representation of an extendible enumeration from the name of
	 * an extendible enumeration in the inventory of this warehouse.</p>
	 * 
	 * @param extendibleEnumerationName Name of an extendible enumeration that should be in the inventory of this
	 * warehouse.
	 * @return Sorted map of element names to element values for this extendible enumeration,
	 * or <code>null</code> if no such extendible enumeration is found in the inventory.
	 * 
	 * @throws NullPointerException The given extendible enumeration name is <code>null</code>.
	 * 
	 * @see #registerExtendibleEnumerationElements(Class)
	 * @see #registerExtendibleEnumerationElement(String, String, AUID)
	 * @see #extendibleEnumerationElementName(AUID)
	 * @see #extendibleEnumerationFullName(AUID)
	 * @see #extendibleEnumerationElementValue(String)
	 * @see #extendibleEnumerationName(AUID)
	 */
	public final static SortedMap<String, tv.amwa.maj.record.AUID> lookupExtendibleEnumeration(
			String extendibleEnumerationName) 
		throws NullPointerException {
		
		if (extendibleEnumerationName == null)
			throw new NullPointerException("Cannot look for an extendible enumeration using a null name.");
		
		if ((!extensibleEnumerations.containsKey(extendibleEnumerationName)) &&
			(extendibleEnumerationName.endsWith("Type")))
			return extensibleEnumerations.get(
					extendibleEnumerationName.substring(0, extendibleEnumerationName.length() - 4));
		
		return extensibleEnumerations.get(extendibleEnumerationName);
	}

	/**
	 * <p>Looks up the full name of an enumeration element in the warehouse inventory by its 
	 * element value identifications. The name is of the form:</p>
	 * 
	 * <center>&lt;<em>enumeration_name</em><code>&gt;_&lt;</code><em>element_name</em>&gt;</center>
	 * 
	 * <p>This form is commonly used by XML representation of AAF and can be converted back
	 * to an element value using {@link #extendibleEnumerationElementValue(String)}.</p>
	 * 
	 * @param elementValue Value of the extendible enumeration full name to lookup in the inventory.
	 * @return Full name of the enumeration element represented by the given element value, or 
	 * <code>null</code> if the element value is not in the inventory.
	 * 
	 * @throws NullPointerException The given element value is <code>null</code>.
	 * 
	 * @see #extendibleEnumerationElementValue(String)
	 * @see #lookupExtendibleEnumeration(String)
	 */
	public final static String extendibleEnumerationFullName(
			AUID elementValue) 
		throws NullPointerException {

		if (elementValue == null)
			throw new NullPointerException("Cannot lookup the full name of an element using a null element value.");
		
		String enumerationName = idToEnumeration.get(elementValue);
		String enumerationElementName = idToElementName.get(elementValue);
		
		if ((enumerationName == null) || (enumerationElementName == null))
			return null;
		
		if (!(enumerationElementName.charAt(0) == '_'))
			return enumerationName + "_" + enumerationElementName;
		else
			return enumerationName + enumerationElementName;
	}
	
	/**
	 * <p>Looks up the full name of an enumeration element value in the warehouse inventory by
	 * its full name, including enumeration name and element name. The name should be of the
	 * form:</p>
	 * 
	 * <center>&lt;<em>enumeration_name</em><code>&gt;_&lt;</code><em>element_name</em>&gt;</center>
	 * 
	 * <p>This full name form is commonly use by XML representations of AAF and can be converted
	 * back to a string form using {@link #extendibleEnumerationFullName(AUID)}.</p>
	 * 
	 * @param fullElementName Full name of the element value to look up in the inventory.
	 * @return Element value identification for the given full name, or <code>null</code> if
	 * the name is not known in the inventory.
	 * 
	 * @throws NullPointerException The given full element name is <code>null</code>.
	 * 
	 * @see #extendibleEnumerationElementValue(String)
	 * @see #lookupExtendibleEnumeration(String)
	 */
	public final static AUID extendibleEnumerationElementValue(
			String fullElementName) 
		throws NullPointerException {
		
		if (fullElementName == null)
			throw new NullPointerException("Cannot lookup an extendible enumeration element value using a null full element name.");
		
		int underscoreIndex = fullElementName.indexOf('_');
		
		if (underscoreIndex == -1) return null;
		
		String enumerationName = fullElementName.substring(0, underscoreIndex);
		String itemName = fullElementName.substring(underscoreIndex + 1);
		
		if (Character.isDigit(itemName.charAt(0)))
			itemName = "_" + itemName;
		
		return extensibleEnumerations.get(enumerationName).get(itemName);
	}

	/**
	 * <p>Returns the enumeration name of an extendible enumeration item from its element value. The
	 * element name part is not included.</p>
	 * 
	 * @param elementValue Element value identification to find the corresponding enumeration name
	 * for.
	 * @return Extendible enumeration name for the given element value, or <code>null</code> if no element of the
	 * given element identification is known in the inventory.
	 * 
	 * @throws NullPointerException Cannot find an extendible enumeration name from a <code>null</code> element value.
	 * 
	 * @see #extendibleEnumerationFullName(AUID)
	 * @see #extendibleEnumerationElementName(AUID)
	 */
	public final static String extendibleEnumerationName(
			AUID elementValue) 
		throws NullPointerException {
		
		if (elementValue == null)
			throw new NullPointerException("Cannot find an extendible enumeration name from a null element value.");
		
		return idToEnumeration.get(elementValue);
	}

	/**
	 * <p>Returns the element name of an extendible enumeration item from its element value. The
	 * enumeration name part is not included.</p>
	 * 
	 * @param elementValue Element value identification to find the corresponding element name
	 * for.
	 * @return Element name for the given element value, or <code>null</code> if no element of the
	 * given element identification is known in the inventory.
	 * 
	 * @throws NullPointerException Cannot find an element name from a <code>null</code> element value.
	 * 
	 * @see #extendibleEnumerationName(AUID)
	 * @see #extendibleEnumerationFullName(AUID)
	 */
	public final static String extendibleEnumerationElementName(
			AUID elementValue) 
		throws NullPointerException {
		
		if (elementValue == null)
			throw new NullPointerException("Cannot find an extendible enumeration element name from a null element value.");
		
		return idToElementName.get(elementValue);
	}
	
	/**
	 * <p>Returns a collection of all extendible enumerations known in the inventory for this
	 * warehouse, by name.</p>
	 * 
	 * @return Collection of all extendible enumerations known in the inventory for this
	 * warehouse, by name.
	 * 
	 * @see #getClassInventory()
	 * @see #getTypeInventory()
	 * @see #lookupExtendibleEnumeration(String)
	 * @see #countExtendibleEnumerations()
	 */
	public final static Collection<String> getExtendibleEnumerationInventory() {
		
		return new TreeSet<String>(extensibleEnumerations.keySet());
	}
	
	/**
	 * <p>Returns the number of extendible enumerations known in the inventory of this 
	 * warehouse.</p>
	 * 
	 * @return Number of extendible enumerations known in the inventory of this 
	 * warehouse.
	 * 
	 * @see #getExtendibleEnumerationInventory()
	 * @see #countClassDefinitions()
	 * @see #countTypeDefinitions()
	 */
	public final static int countExtendibleEnumerations() {
		
		return extensibleEnumerations.size();
	}
	
	/**
	 * <p>Transform an AAF class name into its Java equivalent name. For example, the 
	 * AAF class called <em>FileDescriptor</em> is known as 
	 * <em>{@linkplain tv.amwa.maj.model.AAFFileDescriptor AAFFileDescriptor}</em> in
	 * Java. Most names are the same and passed through this method unchanged, as 
	 * are <code>null</code> values.</p>
	 * 
	 * @param aafName Name of the class to check for an alias of.
	 * @return Corresponding name as used in Java.
	 * 
	 * @see tv.amwa.maj.meta.MetaDefinition#getName()
	 */
	public final static String javaClassAlias(
			String aafName) {
		
		if (classAliases.containsKey(aafName))
			return classAliases.get(aafName);
		
		else return aafName;
	}
	
	/**
	 * <p>Look for a {@linkplain tv.amwa.maj.meta.PropertyDefinition property} in the warehouse
	 * by its unique identifier.</p>
	 * 
	 * <p>To look for a property by its name or local identification number, use 
	 * {@link ClassDefinition#lookupPropertyDefinition(String)} and 
	 * {@link ClassDefinition#lookupPropertyDefinition(short)} respectively.</p>
	 * 
	 * @param propertyID Unique identifier of a property to check for in the warehouse.
	 * @return Property with the given unique identifier, or <code>null</code> if the property
	 * cannot be found.
	 * @throws NullPointerException Cannot look for a property using a <code>null</code> identifier.
	 * 
	 * @see ClassDefinition#lookupPropertyDefinition(AUID)
	 * @see #lookForClass(AUID)
	 * @see #lookForClass(Class)
	 * @see #lookForClass(String)
	 */
	public final static PropertyDefinition lookForProperty(
			AUID propertyID) 
		throws NullPointerException {
		
		if (propertyID == null)
			throw new NullPointerException("Cannot look for a property using a null identifier.");
		
		return globalPropertyIdTable.get(propertyID);
	}
	
	public final static void register(
			PropertyDefinition propertyDefinition)
		throws NullPointerException {
		
		if (propertyDefinition == null)
			throw new NullPointerException("Cannot register a property definition using a null value.");
		
		globalPropertyIdTable.put(propertyDefinition.getAUID(), propertyDefinition);
	}
	
	public final static boolean isKnownProperty(
			PropertyDefinition property) {
			
		return (globalPropertyIdTable.containsKey(property.getAUID()));
	}
	
	public final static void register(
			ExtensionScheme extensionScheme) 
		throws NullPointerException {
		
		if (extensionScheme == null)
			throw new NullPointerException("Cannot register an extension scheme using a null value.");
		
		ExtensionSchemeRecord extensionSchemeRecord = new ExtensionSchemeRecord(extensionScheme);
		
		if ( extensionScheme.countMetaDefinitions() > 0) {
			for ( MetaDefinition metaDefinition : extensionScheme.getMetaDefinitions() ) {
				if (metaDefinition instanceof ClassDefinition) {
					if (!idToClass.containsKey(metaDefinition.getAUID())) 
						register((ClassDefinition) metaDefinition);
					continue;
				}
				if (metaDefinition instanceof PropertyDefinition) {
					if (!globalPropertyIdTable.containsKey(metaDefinition.getAUID())) 
						register((PropertyDefinition) metaDefinition);
					continue;
				}
				if (metaDefinition instanceof TypeDefinition) {
					if (!knownTypesByID.containsKey(metaDefinition.getAUID()))
						register((TypeDefinition) metaDefinition, extensionScheme.getSchemeURI(), 
								extensionScheme.getPreferredPrefix());
					continue;
				}
			}
		}
		
		extensionSchemes.put(extensionSchemeRecord.schemeID, extensionSchemeRecord);
		WeakReference.registerTarget(extensionScheme);
	}
	
	public final static ExtensionScheme lookupExtensionScheme(
			AUID extensionSchemeID)
		throws NullPointerException {
		
		if (extensionSchemeID == null)
			throw new NullPointerException("Cannot look for an extension scheme using a null scheme ID.");
		
		if (!extensionSchemes.containsKey(extensionSchemeID)) return null;
		ExtensionSchemeRecord record = extensionSchemes.get(extensionSchemeID);
		
		ExtensionScheme scheme = new ExtensionSchemeImpl();
		scheme.setSchemeID(record.schemeID);
		scheme.setSchemeURI(record.schemeURI);
		scheme.setPreferredPrefix(record.preferredPrefix);
		scheme.setExtensionDescription(record.extensionDescription);
		
		for ( ClassDefinition classDefinition : idToClass.values() )
			if (classDefinition.getNamespace().equals(record.schemeURI))
				scheme.addMetaDefinition(classDefinition);
		for ( TypeDefinition typeDefinition : knownTypesByID.values())
			if (typeDefinition.getNamespace().equals(record.schemeURI))
				scheme.addMetaDefinition(typeDefinition);
		for ( PropertyDefinition propertyDefinition : globalPropertyIdTable.values())
			if (propertyDefinition.getNamespace().equals(record.schemeURI))
				scheme.addMetaDefinition(propertyDefinition);
		
		// TODO something about extendible enumerations?
		return scheme;
	}

	public final static ExtensionScheme lookupExtensionScheme(
			String extensionSchemeIdentifier) 
		throws NullPointerException {
		
		if (extensionSchemeIdentifier == null)
			throw new NullPointerException("Cannot look for an extension scheme using a null scheme identifier.");
		
		AUID schemeToFind = null;
		for ( ExtensionSchemeRecord record : extensionSchemes.values() ) {
			if ((extensionSchemeIdentifier.equals(record.schemeURI)) ||
					(extensionSchemeIdentifier.equals(record.preferredPrefix)) ||
					(record.schemeID.toString().toLowerCase().contains(extensionSchemeIdentifier.toLowerCase()))) {
				schemeToFind = record.schemeID;
				break;
			}
		}
		
		if (schemeToFind != null)
			return lookupExtensionScheme(schemeToFind);
		else
			return null;
	}
}
