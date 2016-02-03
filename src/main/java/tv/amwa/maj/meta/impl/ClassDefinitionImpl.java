/* 
 **********************************************************************
 *
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: ClassDefinitionImpl.java,v $
 * Revision 1.7  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.6  2011/07/27 17:36:16  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.5  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/19 21:37:53  vizigoth
 * Added property initialization code.
 *
 * Revision 1.3  2011/01/18 09:12:31  vizigoth
 * Added some comments.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/11/18 10:49:53  vizigoth
 * Added support for dynamic meta dictionaries and type name mapping for legacy meta dictionary compatibility.
 *
 * Revision 1.5  2010/07/19 16:07:10  seanhowes
 * update so that mxf metadictionary not put into aff
 *
 * Revision 1.4  2010/05/19 22:23:57  vizigoth
 * Adding support for Avid extensions.
 *
 * Revision 1.3  2010/03/19 16:17:20  vizigoth
 * Added support for hidden classes and changed parent class to a proper weak reference.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.10  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.9  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.8  2009/02/06 17:02:24  vizigoth
 * Alterations to creatInstance.
 *
 * Revision 1.7  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.6  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.5  2008/01/27 11:10:07  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.4  2007/12/04 13:04:53  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.3  2007/12/04 09:58:18  vizigoth
 * Added global properties lookup table for AAF structured storage file reading.
 *
 * Revision 1.2  2007/12/04 09:45:17  vizigoth
 * Added local id to property mapping table.
 *
 * Revision 1.1  2007/11/13 22:13:28  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.AlreadyUniquelyIdentifiedException;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.ObjectAlreadyAttachedException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.EmitXMLClassIDAs;
import tv.amwa.maj.industry.HiddenClass;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyContains;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.io.aaf.AAFConstants;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;


/** 
 * <p>Implementation of the definition of an AAF class.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#ClassDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ClassDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ClassDefinitionStrongReferenceSet
 */
@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0201, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "ClassDefinition",
		  description = "The ClassDefinition class is used to programatically represent the class hierarchy defined in the AAF object specification.",
		  symbol = "ClassDefinition")	
public final class ClassDefinitionImpl 
	extends 
		MetaDefinitionImpl 
	implements 
		ClassDefinition, 
		Serializable,
		XMLSerializable,
		WeakReferenceTarget,
		CommonConstants,
		Cloneable {

	/**  */
	private static final long serialVersionUID = 3707298326242517329L;
		
//	private final static Set<AUID> knownPropertyDefinitions =
//		Collections.synchronizedSet(new HashSet<AUID>());
	
	private boolean concrete = true;
	private WeakReference<ClassDefinition> parentClass = null;
	private Map<AUID, PropertyDefinition> properties = 
		Collections.synchronizedMap(new HashMap<AUID, PropertyDefinition>());
	private Map<String, PropertyDefinition> propertiesByName =
		Collections.synchronizedMap(new HashMap<String, PropertyDefinition>());
	private Map<Short, PropertyDefinition> propertiesByTag =
		Collections.synchronizedMap(new HashMap<Short, PropertyDefinition>());
	private Set<PropertyDefinition> allDefinitions = null;
	private PropertyDefinition unqiueIdentifier  = null;
	private Class<?> javaImplementation = null;
	private EmitXMLClassIDAs emitXMLClassId = EmitXMLClassIDAs.Parent;
	
	private static Map<String, String> classNameMap = null;
	
	public final static void initalizeClassNameMap() {
		if (AAFConstants.MAP_SS_CLASS_NAMES) {
		
			int classMapSize = AAFConstants.classNameAliases.length / 2;
			classNameMap = 
				Collections.synchronizedMap(new HashMap<String, String>(classMapSize));
			for ( int x = 0 ; x < classMapSize ; x++ )
				classNameMap.put(AAFConstants.classNameAliases[x * 2], 
						AAFConstants.classNameAliases[x * 2 + 1]);
		}
	}

	
	protected ClassDefinitionImpl() { }
	
	/** 
	 * <p>Creates and initializes a class definition object that inherits from the
	 * given parent class.  If the is concrete property is set to <code>true</code>, objects of 
	 * this class can be instantiated; otherwise, it will be illegal to 
	 * instantiate such objects.</p>
	 * 
	 * <p>When creating the class implementation, it is advisable to set the Java
	 * class implementation of the given definition using 
	 * {@link ClassDefinitionImpl#setJavaImplementation(Class)}.</p>
	 * 
	 * @param identification Identification of the new class definition.
	 * @param className Friendly name of the new class definition.
	 * @param parentClass Specifies the parent of the class being defined from
	 * which this class inherits. If set to <code>null</code>, the new definition is 
	 * assumed to be for a root class.
	 * @param isConcrete Can objects of this class be instantiated?
	 * 
	 * @throws NullPointerException The identification argument is <code>null</code>.
	 */
	public ClassDefinitionImpl(
			AUID identification,
			String className,
			ClassDefinition parentClass, 
			boolean isConcrete) 
		throws NullPointerException {

		if (identification == null)
			throw new NullPointerException("Cannot create a new class definition with a null class identifier.");
		
		setIdentification(identification);
		setName(className);
		if (parentClass == null)
			setParent(this);
		else
			setParent(parentClass);
		setIsConcrete(isConcrete);
		
		Warehouse.register(this);
	}

	/**
	 * <p>For any given Java class, this method finds the corresponding AAF class definition or 
	 * creates it if it does not yet exists within the current Java virtual machine. The creation
	 * of class definitions is done by lazy evaluation as required using Java reflection and annotations. 
	 * All generated values are cached in a local, static hashtable so that once generated, the work
	 * is not repeated.</p>
	 * 
	 * <p>The values returned by this method are only as good as the annotations provided with 
	 * the sourcecode, as labelled using {@link MediaClass} and {@link MediaProperty}. If an {@link MediaClass}
	 * annotation is not present in the given class, an {@link IllegalArgumentException} is thrown. Any
	 * problems found with the {@link MediaProperty} annotations, such as two properties having the same
	 * name, will also result in an {@link IllegalArgumentException}.</p>
	 *
	 * @param aafClass Java class to find the AAF class definition of.
	 * @return AAF class definition associated with the given Java class. 
	 * 
	 * @throws NullPointerException The given Java class is <code>null</code>.
	 * @throws IllegalArgumentException The given Java class is not annotated with {@link MediaClass} or 
	 * the {@link MediaProperty} annotations contain errors.
	 * 
	 * @see tv.amwa.maj.industry.ClassDefinitionWarehouse
	 */
	public final static ClassDefinition forClass(
			Class<?> aafClass) 
		throws NullPointerException,
			IllegalArgumentException {
		
		ClassDefinitionImpl classDef = new ClassDefinitionImpl();
		classDef.setJavaImplementation(aafClass);
		
		MediaClass classMetadata = aafClass.getAnnotation(MediaClass.class);
		if (classMetadata == null) {
			HiddenClass hiddenClass = aafClass.getAnnotation(HiddenClass.class);
			if (hiddenClass == null)
				throw new IllegalArgumentException("The class " + aafClass.getName() + " is not annotated as a media class or a hidden class.");
			return Warehouse.lookForClass(aafClass.getSuperclass());
		}
		
		AUID classID = 
			new AUIDImpl(classMetadata.uuid1(), (short) classMetadata.uuid2(), 
					 (short) classMetadata.uuid3(), classMetadata.uuid4());
		
		ClassDefinition parent;
		if (aafClass.getSuperclass().equals(Object.class))
			parent = classDef;
		else 
			parent = Warehouse.lookForClass(aafClass.getSuperclass());
		
		classDef.setIdentification(classID);
		classDef.setParent(parent);
		classDef.setName(classMetadata.definedName()); 
		
		boolean isConcrete = (Modifier.isAbstract(aafClass.getModifiers()) == false);
		if (isConcrete) 
			isConcrete = classMetadata.isConcrete();
		classDef.setIsConcrete(isConcrete);
		classDef.setDescription(classMetadata.description());
		classDef.setSymbol(classMetadata.symbol());
		classDef.setAliases(classMetadata.aliases());
		classDef.setEmitXMLClassIDAs(classMetadata.emitXMLClassID());
		
		if (classMetadata.namespace().length() > 0)
			classDef.setNamespace(classMetadata.namespace());
		
		if (classMetadata.prefix().length() > 0)
			classDef.setPrefix(classMetadata.prefix());

		int propertyWeight = Integer.MIN_VALUE + classDef.getHierarchyDepth() * 1000;
		
		for ( Method propertyFinder : aafClass.getDeclaredMethods() ) {
			MediaProperty property = propertyFinder.getAnnotation(MediaProperty.class);
			if (property != null) {
				
				AUIDImpl propertyID = new AUIDImpl(property.uuid1(), (short) property.uuid2(), 
							               (short) property.uuid3(), property.uuid4());
				
				try {			
					PropertyDefinitionImpl justRegistered = (PropertyDefinitionImpl) classDef.registerNewPropertyDefinition(
						propertyID, 
						property.definedName(), 
						property.aliases(),
						property.symbol(),
						property.typeName(),
						property.optional(), 
						property.uniqueIdentifier(),
						(short) property.pid());
					justRegistered.setAnnotatedGetter(propertyFinder);
					
					if (property.namespace().length() > 0)
						justRegistered.setNamespace(property.namespace());
					if (property.prefix().length() > 0)
						justRegistered.setPrefix(property.prefix());
					justRegistered.setDescription(property.description());
					justRegistered.setIsXMLCDATA(property.isXMLCDATA());
					justRegistered.setIsXMLAttribute(property.isXMLAttribute());
					justRegistered.setFlattenXML(property.flattenXML());
					
					if (property.uniqueIdentifier())
						classDef.setUniqueIdentifier(justRegistered);
					
					if (property.weight() == 0) 
						justRegistered.setWeight(propertyWeight);
					else	
						justRegistered.setWeight(property.weight());
					propertyWeight++;
				}
				catch (AlreadyUniquelyIdentifiedException auie) {
					throw new IllegalArgumentException("The class contains two AAF property annotations claiming to be its unique identifier, one of which is '" + property.definedName() + "'.");
				}
				catch (ObjectAlreadyAttachedException bpe) {
					throw new IllegalArgumentException("The class contains two AAF property annotations with the same identification with defined name '" + property.definedName() + "'.");
				}
			}
		}
		
		Warehouse.register(classDef);
		
		return classDef;
	}

	private int getHierarchyDepth() {

		int depth = 0;
		ClassDefinition target = this;
		ClassDefinition parent = target.getParent();
		while (!target.equals(target.getParent())) {
			depth++;
			target = parent;
			parent = target.getParent();
		}

		return depth;
	}
	
	@MediaPropertyCount("Properties")
	public int countPropertyDefinitions() {

		return properties.size();
	}

	@MediaPropertyClear("Properties")
	public void clearPropertyDefinitions() {
		
		properties.clear();
		propertiesByName.clear();
		propertiesByTag.clear();
	}
	
	public MetadataObject createInstance() { 

		try {
			if (javaImplementation != null)
				return (MetadataObject) javaImplementation.newInstance();
		}
		catch (Exception iae) {
			return null;
		}
		
		return null;
	}

	public String getJavaClassName() 
		throws PropertyNotPresentException {
		
		if (javaImplementation == null)
			throw new PropertyNotPresentException("The optional java implementation property is not present in this class definition.");
		
		return javaImplementation.getCanonicalName();
	}
	
	public Class<?> getJavaImplementation() 
		throws PropertyNotPresentException {
		
		if (javaImplementation == null)
			throw new PropertyNotPresentException("The optional java class implementation that can be associated with this AAF class definition is not present.");
		
		return javaImplementation;
	}
	
	public void setJavaImplementation(
			Class<?> javaImplementation) {

		this.javaImplementation = javaImplementation;
	}
	
	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ParentClass",
			typeName = "ClassDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0008,
			symbol = "ParentClass")
	public ClassDefinition getParent() {

		return parentClass.getTarget();
	}

	@MediaPropertySetter("ParentClass")
	public void setParent(
			ClassDefinition parent) 
		throws NullPointerException {
		
		if (parent == null) 
			throw new NullPointerException("Cannot set a parent class for this class definition to a null value. For a root class, use itself as a parent.");

		this.parentClass = new WeakReference<ClassDefinition>(parent);
	}
	
	public ClassDefinition initializeParentClass() {
		
		return this;
	}
	
	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Properties",
			typeName = "PropertyDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0009,
			symbol = "Properties")
	public Set<PropertyDefinition> getPropertyDefinitions() {

		return new HashSet<PropertyDefinition>(properties.values());
	}

	void setPropertyDefinitions(
			Set<? extends PropertyDefinition> properties) 
		throws NullPointerException {
	
		this.properties = Collections.synchronizedMap(new HashMap<AUID, PropertyDefinition>());
		this.propertiesByName = Collections.synchronizedMap(new HashMap<String, PropertyDefinition>());
		this.propertiesByTag = Collections.synchronizedMap(new HashMap<Short, PropertyDefinition>());
		
		for ( tv.amwa.maj.meta.PropertyDefinition property : properties )
			if (property != null) {

				this.properties.put(property.getAUID(), property);
				this.propertiesByName.put(property.getName(), property);
				this.propertiesByName.put(property.getSymbol(), property);
				putAliases(property);
				try {
					if (property.getLocalIdentification() != (short) 0) 
						this.propertiesByTag.put(property.getLocalIdentification(), property);
					Warehouse.register(property);
				}
				catch (PropertyNotPresentException pnpe) { }
			}
	}
	
	@MediaSetAdd("Properties")
	public void addPropertyDefinition(
			PropertyDefinition propertyDefinition) 
		throws NullPointerException {
		
		if (propertyDefinition == null)
			throw new NullPointerException("Cannot add a null property definition to this class.");
		
		propertyDefinition.setMemberOf(this);
		properties.put(propertyDefinition.getAUID(), propertyDefinition);
		propertiesByName.put(propertyDefinition.getName(), propertyDefinition);
		propertiesByName.put(propertyDefinition.getSymbol(), propertyDefinition);
		putAliases(propertyDefinition);
		if (propertyDefinition.getLocalIdentification() != 0)
			propertiesByTag.put(propertyDefinition.getLocalIdentification(), propertyDefinition);
	}
	
	@MediaPropertyContains("Properties")
	public boolean containsPropertyDefinition(
			PropertyDefinition propertyDefinition) 
		throws NullPointerException {
		
		if (propertyDefinition == null)
			throw new NullPointerException("Cannot test to see if a null value is contained in the properties of this class definition.");
		
		return properties.containsKey(propertyDefinition.getAUID());
	}
	
	@MediaPropertyRemove("Properties")
	public void removePropertyDefinition(
			PropertyDefinition propertyDefinition) 
		throws NullPointerException {
		
		if (propertyDefinition == null)
			throw new NullPointerException("Cannot remove a null property definition from the properties of this class definition.");
		
		PropertyDefinition propertyImplementation = properties.remove(propertyDefinition.getAUID());
		propertiesByName.remove(propertyDefinition.getName());
		propertiesByName.remove(propertyImplementation.getSymbol());
		propertiesByTag.remove(propertyDefinition.getLocalIdentification());
	}
	
	public PropertyDefinition getUniqueIdentifierProperty() {

		if (this == parentClass.getTarget()) 
			return unqiueIdentifier;
		
		if (unqiueIdentifier == null)
			return parentClass.getTarget().getUniqueIdentifierProperty();
		else
			return unqiueIdentifier;
	}

	public boolean isConcrete() {

		return concrete;
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "IsConcrete",
			typeName = "Boolean",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x000A,
			symbol = "IsConcrete")
	public boolean getIsConcrete() {
		
		return concrete;
	}
	
	@MediaPropertySetter("IsConcrete")
	public void setIsConcrete(
			boolean concrete) {
		
		this.concrete = concrete;
	}
	
	public final static boolean initializeIsConcrete() {
		
		return true;
	}
	
	public boolean isRoot() {

		return equals(parentClass.getTarget());
	}

	public boolean isUniquelyIdentified() {

		return (getUniqueIdentifierProperty() != null);
	}

	public PropertyDefinition lookupPropertyDefinition(
			AUID propertyID)
		throws NullPointerException, 
			BadParameterException {

		if (propertyID == null)
			throw new NullPointerException("Cannot lookup a property definition with a null id.");
		
		if (!(properties.containsKey(propertyID))) {
			if (parentClass == null) System.err.println("*-*-*: " + getName());
			if (parentClass.getTarget() != this)
				return parentClass.getTarget().lookupPropertyDefinition(propertyID);
			else
				throw new BadParameterException("Cannot find the given property registered with this class definition.");
		}
		
		return properties.get(propertyID);
	} 

	public PropertyDefinition lookupPropertyDefinition(
			String propertyName) 
		throws NullPointerException,
			BadParameterException {
		
		if (propertyName == null)
			throw new NullPointerException("Cannot lookup a property definition with a null name.");
		
		if (!(propertiesByName.containsKey(propertyName))) {
			if (parentClass.getTarget() != this)
				return parentClass.getTarget().lookupPropertyDefinition(propertyName);
			else
				throw new BadParameterException("Cannot find the given property by name registered with this class definition.");
		}
			
		return propertiesByName.get(propertyName);
	}
	
	public PropertyDefinition lookupPropertyDefinition(
			short propertyTag) 
		throws BadParameterException {
		
		if (!(propertiesByTag.containsKey(propertyTag))) {
			if ((parentClass != null) && (parentClass.getTarget() != this))
				return parentClass.getTarget().lookupPropertyDefinition(propertyTag);
			else
				throw new BadParameterException("Cannot find the given property by local identification tag registered with this class definition.");
		}
		
		return propertiesByTag.get(propertyTag);
	}
	
	public final static PropertyDefinition globalPropertyIDLookup(
			AUID propertyID) {
		
		return Warehouse.lookForProperty(propertyID);
	}
	
	public PropertyDefinition registerNewPropertyDefinition(
			AUID identification, 
			String name,
			String[] aliases,
			String symbol,
			String typeName, 
			boolean isOptional,
			boolean isUniqueIdentifier,
			Short pid)
		throws NullPointerException,
			AlreadyUniquelyIdentifiedException,
			ObjectAlreadyAttachedException {

		if (properties.containsKey(identification))
			throw new ObjectAlreadyAttachedException("Cannot register a property with the same identity as one that is already registered.");
		
		PropertyDefinitionImpl creation = new PropertyDefinitionImpl(identification, name, typeName, this, isOptional);
		creation.setSymbol(symbol);
		creation.setLocalIdentification(pid);
		creation.setAliases(aliases);
		
		if ((isUniqueIdentifier == true) && (isUniquelyIdentified()))
			throw new AlreadyUniquelyIdentifiedException("Cannot add another property to the set of already uniquely identified properties that is also a unique indentifier.");
	
		creation.setIsUniqueIdentifier(isUniqueIdentifier);
		properties.put(creation.getAUID(), creation);
		propertiesByName.put(creation.getName(), creation);
		propertiesByName.put(creation.getSymbol(), creation);
		putAliases(creation);
		if ((pid != null) && (pid != (short) 0)) 
			propertiesByTag.put(creation.getLocalIdentification(), creation);
		Warehouse.register(creation);	
		
		return creation;
	}

	/**
	 * <p>Put property definitions into the property name lookup table only if they do not overwrite
	 * the defined name or symbol name of another property.</p>
	 * 
	 * @param severalNames The property definition to check.
	 */
	private void putAliases(
			PropertyDefinition severalNames) {
		
		if (severalNames == null) return;
		if (severalNames.getAliases() == null) return;
		
		for ( String alias : severalNames.getAliases() ) {
			if (propertiesByName.containsKey(alias)) {
				PropertyDefinition checkBeforeRemoving = propertiesByName.get(alias);
				if ((alias.equals(checkBeforeRemoving.getName())) ||
						(alias.equals(checkBeforeRemoving.getSymbol()))){
					System.err.println("Warning: Cannot use alias " + alias + " for property " + severalNames.getMemberOf().getName() + "." +
							severalNames.getName() + " because it clashes with another property name or symbol in the same class.");
					continue;
				}
					
			}
			
			propertiesByName.put(alias, severalNames);
		}
	}
	
	public PropertyDefinition registerOptionalPropertyDefinition(
			AUID identification, 
			String name,
			String[] aliases,
			String symbol,
			String typeName) 
		throws NullPointerException,
			ObjectAlreadyAttachedException {

		try {
			return registerNewPropertyDefinition(identification, name, aliases, 
					symbol, typeName, true, false, null);
		}
		catch (AlreadyUniquelyIdentifiedException auie) {
			// Should not happen as forcing uniquely identified to false
			return null;
		}
	}
	
	/** 
	 * <p>The symbol name for class definitions is derived from the canonical name of the
	 * associated Java class. This is to ensure that the one and only authorative source
	 * of class definition information is derived from actual Java class implementations. The
	 * symbol name is used as the main way of finding class definitions when XML is
	 * deserialized.</p>
	 * 
	 * @see tv.amwa.maj.meta.impl.MetaDefinitionImpl#getSymbolName()
	 */
	@Override
	String getSymbolName() {
		
		return javaImplementation.getCanonicalName().replace('.', '_');
	}
	
	public String getComment() {
		
		if (javaImplementation == null)
			return "No known implementing class. Symbol: " + getSymbol();
		return "Java implementing class: " + javaImplementation.getCanonicalName() +
			" Symbol: " + getSymbol();
	}
		
	public final static ClassDefinition forAUID(
			AUID identification) {
				
		return Warehouse.lookForClass(identification);
	}

	public synchronized Set<PropertyDefinition> getAllPropertyDefinitions() {
		
		if (allDefinitions != null) return allDefinitions;
		
		allDefinitions = new HashSet<PropertyDefinition>(getPropertyDefinitions());
		allDefinitions.addAll(getParent().getAllPropertyDefinitions());
		
		return allDefinitions;
	}
	
	public int countProperties(
			MetadataObject metadataObject) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (metadataObject == null)
			throw new NullPointerException("Cannot count properties for a null metadata object.");

		if (!metadataObject.getClass().equals(javaImplementation))
			throw new IllegalArgumentException("The given object is not an instance of this defined class.");
		
		int propertyPresentCounter = 0;
		
		for ( PropertyDefinition propertyDefinition : getAllPropertyDefinitions() ) {
			if (propertyDefinition.getIsOptional()) {
				if (propertyDefinition.isPropertyPresent(metadataObject)) propertyPresentCounter++;
			}
			else
				propertyPresentCounter++; // Always count required properties			
		}
		
		return propertyPresentCounter;
	}

	public final static boolean isKnownProperty(
			PropertyDefinition property) {
		
		return Warehouse.isKnownProperty(property);
	}
	
	public SortedMap<? extends tv.amwa.maj.meta.PropertyDefinition, ? extends PropertyValue> getProperties(
			MetadataObject metadataObject) {

		Set<PropertyDefinition> allPropertyDefinitions = getAllPropertyDefinitions();
		SortedMap<PropertyDefinition, PropertyValue> propertyValues =
			new TreeMap<PropertyDefinition, PropertyValue>();
		
		for ( PropertyDefinition propertyDefinition : allPropertyDefinitions ) {
				
			try {
				propertyValues.put(
						propertyDefinition, 
						propertyDefinition.getPropertyValue(metadataObject));
			}
			catch (Exception e) {
				// If you can't get the value, don't add it to the map ... print warning for non optionals
				
				if ((!propertyDefinition.getIsOptional()) &&
						(!(e.getCause() instanceof BadPropertyException))) {
					System.err.println("Could not retrieve a value for required property " + getName() + "." + propertyDefinition.getName() + 
							" because of a " + e.getClass().getName() + ": " + e.getMessage());
					e.printStackTrace(System.err);
				}
			}		
		}
		
		return propertyValues;
	}
	
	@Override
	public String getNamespace() {
		
		if (namespace == null) {
			if (parentClass.getTarget() == this)
				return "unknown";
			else
				return parentClass.getTarget().getNamespace();
		}

		return namespace;
	}
	
	@Override
	public String getPrefix() {
		
		if (prefix == null) {
			if (parentClass.getTarget() == this)
				return "unknown";
			else
				return parentClass.getTarget().getPrefix();
		}

		return prefix;
	}

	public EmitXMLClassIDAs getEmitXMLClassIDAs() {
		
		if (isRoot()) 
			return (emitXMLClassId == EmitXMLClassIDAs.Parent) ? EmitXMLClassIDAs.Element : emitXMLClassId;
		
		if (emitXMLClassId == EmitXMLClassIDAs.Parent) {
			return getParent().getEmitXMLClassIDAs();
			// TODO consider optimisation by caching locally
		}
		
		return emitXMLClassId;
		
		// TODO test this
	}
	
	void setEmitXMLClassIDAs(
			EmitXMLClassIDAs emitAs) {
		
		this.emitXMLClassId = emitAs;
	}
	
	public String getWeakTargetReference() {
		
		return getIdentification().toString();
	}
	
	void setUniqueIdentifier(
			PropertyDefinition uniqueIdentifier) {
		
		this.unqiueIdentifier = uniqueIdentifier;
	}
	
	public PropertyValue getUniqueIdentifierValue(
			MetadataObject uniquelyIdentifiedObject) 
		throws NullPointerException,
			IllegalArgumentException {
		
		return getUniqueIdentifierProperty().getPropertyValue(uniquelyIdentifiedObject);
	}
	
	public final static int countClassDefinitions() {
		
		return Warehouse.countClassDefinitions();
	}
	
	public final static Collection<ClassDefinition> getClassDefinitions() {

		return Warehouse.getClassDefinitions();  
	}

	@Override
	public String nameToAAFName(
			String name) {
		
		if (classNameMap != null) {
			String mappedName = classNameMap.get(name);
			return (mappedName != null) ? mappedName : name;
		}
		
		return name;
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element classElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "ClassDefinition");
		super.appendMetadictXML(classElement, namespace, prefix);
		
		if (!isRoot())
			XMLBuilder.appendElement(classElement, namespace, prefix, 
					"ParentClass", parentClass.getTarget().getName());
			
		XMLBuilder.appendElement(classElement, namespace, prefix, 
				"IsConcrete", isConcrete());
	}
	
	public ClassDefinition clone() {
		
		return (ClassDefinition) super.clone();
	}
}
