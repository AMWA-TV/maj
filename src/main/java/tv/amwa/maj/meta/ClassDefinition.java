/*
 * Copyright 2016 Advanced Media Workflow Assocation
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
 * $Log: ClassDefinition.java,v $
 * Revision 1.14  2011/02/14 22:32:50  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.13  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.12  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.10  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.9  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.8  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.7  2009/02/24 18:47:54  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.6  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.5  2008/02/28 12:50:33  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.4  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.3  2008/01/27 11:07:21  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 13:04:50  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:42  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import java.util.Set;
import java.util.SortedMap;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.AlreadyUniquelyIdentifiedException;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.ObjectAlreadyAttachedException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.EmitXMLClassIDAs;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.model.Dictionary;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.record.AUID;



/**
 * <p>Specifies the definition of an AAF class. The definition can be used to extend the class hierarchy defined in the 
 * <a href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF object specification v1.1</a> 
 * by specifying a new class or by defining additional optional properties for a defined class. The 
 * operations on a class definition include: managing the position of the class within the class 
 * hierarchy; accessing {@linkplain PropertyDefinition property definitions} associated with the class.</p>
 * 
 * <p>With the MAJ API, a Java class can be used to specify a class definition by using the {@link tv.amwa.maj.industry.MediaClass}
 * and {@link tv.amwa.maj.industry.MediaProperty} annotations. In this way, AAF class specification data and
 * the MAJ API implementation methods can be managed alongside one another.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.industry.MediaClass
 * @see tv.amwa.maj.industry.Warehouse#lookForClass(Class)
 * @see tv.amwa.maj.industry.Warehouse#lookForClass(String)
 * @see tv.amwa.maj.industry.Warehouse#lookForClass(AUID)
 * @see tv.amwa.maj.industry.TypeDefinitions#ClassDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ClassDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ClassDefinitionStrongReferenceSet
 * @see tv.amwa.maj.model.InterchangeObject#getObjectClass()
 */
public interface ClassDefinition 
	extends MetaDefinition,
		WeakReferenceTarget {

	/** 
	 * <p>Returns the AAF property definitions attached directly to the 
	 * current class.<p>
	 * 
	 * @return Shallow copy of the set of {@linkplain PropertyDefinition 
	 * property definitions} that define the properties for this
	 * class.
	 * 
	 * @see #getAllPropertyDefinitions()
	 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionStrongReferenceSet
	 */
	public Set<? extends PropertyDefinition> getPropertyDefinitions();
	
	/** 
	 * <p>Returns all AAF property definitions attached directly to the 
	 * current class and its {@linkplain #getParent() ancestors}.<p>
	 * 
	 * @return The set of {@linkplain PropertyDefinition 
	 * property definitions}  that define the properties for this
	 * class and all of its ancestors.
	 * 
	 * @see #getPropertyDefinitions()
	 * @see #getProperties(MetadataObject)
	 */
	public Set<PropertyDefinition> getAllPropertyDefinitions();
	
	
	/**
	 * <p>Returns number of property definitions in this class.</p>
	 * 
	 * @return Number of properties contained in this class definition.
	 */
	public @UInt32 int countPropertyDefinitions();

	/** 
	 * <p>Creates a new property definition and registers it in this class 
	 * definition. The new property definition is returned.</p>
	 * 
	 * <p>Note that it is illegal to add mandatory properties to an existing 
	 * (registered) class.  This method will allow adding
	 * either optional or mandatory properties to a class, but they must
	 * be added to a class which has not yet been registered in the 
	 * {@linkplain Dictionary dictionary}.  If this class has already been registered, it is 
	 * possible to add optional properties, but not through this
	 * method.  Optional properties added to an existing (registered)
	 * class are not supported dynamically in the MAJ API.</p>
	 * 
	 * @param identification AUID to be used to identify this property.
	 * @param name Name of the new property. 
	 * @param aliases A set of additional names that can be used to identify the property.
	 * @param symbol Registered data symbol for the property.
	 * @param typeName Name of the type of value represented by the new property.
	 * @param isOptional True if new property is to be optional.
	 * @param isUniqueIdentifier True if new property is to be the unique 
	 * identifier of the class.
	 * @param pid Short tag used to identify the property, or <code>null</code> if it is not known.
	 * @return The newly created property definition.
	 * 
	 * @throws NullPointerException One of the name or type definition arguments is null.
	 * @throws ObjectAlreadyAttachedException A property with the given identifier has already been registered.
	 * @throws AlreadyUniquelyIdentifiedException This class already has 
	 * a unique identifier property.
	 * 
	 * @see MetaDictionary#getClassDefinitions()
	 * @see PropertyDefinition
	 * @see #registerOptionalPropertyDefinition(AUID, String, String[], String, String)
	 */
	public PropertyDefinition registerNewPropertyDefinition(
			AUID identification,
			@AAFString String name,
			String[] aliases,
			@AAFString String symbol,
			String typeName,
			boolean isOptional,
			boolean isUniqueIdentifier,
			Short pid) 
		throws NullPointerException,
			ObjectAlreadyAttachedException, 
			AlreadyUniquelyIdentifiedException;

	/** 
	 * <p>Creates a new property definition and registers it in this class
	 * definition. The new property definition is returned.</p>
	 * 
	 * <p>Note that it is illegal to add mandatory properties to an already
	 * existing (registered) class.  It is assumed that this property is 
	 * being added to a class which is already registered.  If so, it 
	 * must be optional and this method will declare it so.  If it is 
	 * wished to add a mandatory property, that may be done through the
	 * {@link #registerNewPropertyDefinition(AUID, String, String[], String, String, boolean, boolean, Short)
	 * registerNewPropertyDefinition()} method, but that must be called on 
	 * a class which is not yet registered.</p>
	 * 
	 * @param identification AUID to be used to identify this property.
	 * @param name Name of the new property. 
	 * @param aliases A set of additional names that can be used to identify the property.
	 * @param symbol Registered data symbol for the property.
	 * @param typeName Name of the type of value represented by the new property.
	 * @return Newly created property definition.
	 * 
	 * @throws NullPointerException Name or typeDef argument is null.
	 * @throws ObjectAlreadyAttachedException The given property identifier has already been registered.
	 * 
	 * @see #registerNewPropertyDefinition(AUID, String, String[], String, String, boolean, boolean, Short)
	 */
	public PropertyDefinition registerOptionalPropertyDefinition(
			AUID identification,
			@AAFString String name,
			String[] aliases,
			@AAFString String symbol,
			String typeName) 
		throws NullPointerException, 
			ObjectAlreadyAttachedException;

	/**
	 * <p>Looks up and returns the {@linkplain PropertyDefinition property definition} corresponding to 
	 * the given {@linkplain tv.amwa.maj.record.AUID AUID} in the set of properties defined for this
	 * class.</p>
	 * 
	 * @param propertyId AUID identifying the property to look up.
	 * @return Property definition with the given identifier.
	 * 
	 * @throws NullPointerException The given property identifier is <code>null</code>. 
	 * @throws BadParameterException The given property identifier is not registered as a property definition for
	 * this class.
	 * 
	 * @see #lookupPropertyDefinition(String)
	 * @see #lookupPropertyDefinition(short)
	 * @see #getAllPropertyDefinitions()
	 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionWeakReference
	 */
	public PropertyDefinition lookupPropertyDefinition(
			AUID propertyId) 
		throws NullPointerException, 
			BadParameterException;
	
	/**
	 * <p>Looks up and returns the {@linkplain PropertyDefinition property definition} corresponding to 
	 * the given name in the set of all properties defined for this class. The name may be either the property's
	 * {@linkplain tv.amwa.maj.industry.MediaProperty#definedName() defined name}, 
	 * {@linkplain tv.amwa.maj.industry.MediaProperty#symbol() symbol name} or one of its
	 * {@linkplain tv.amwa.maj.industry.MediaProperty#aliases() aliases}.</p>
	 * 
	 * @param propertyName String identifying the property to look up.
	 * @return Property definition with the given identifier.
	 * 
	 * @throws NullPointerException The given property identifier is <code>null</code>. 
	 * @throws BadParameterException The given property identifier is not registered as a property definition for
	 * this class.
	 * 
	 * @see #lookupPropertyDefinition(AUID)
	 * @see #lookupPropertyDefinition(String)
	 * @see #getAllPropertyDefinitions()
	 * 
	 */
	public PropertyDefinition lookupPropertyDefinition(
			String propertyName) 
		throws NullPointerException, 
			BadParameterException;

	/**
	 * <p>Looks up and returns the {@linkplain PropertyDefinition property definition} corresponding to 
	 * the given short tag in the set of all properties defined for this class.</p>
	 * 
	 * <p>Note that not all properties have defined tags, also known as <em>local identification</em>, and 
	 * tags can be generated automatically when required.</p>
	 * 
	 * @param propertyTag Short identifier for the property to look up.
	 * @return Property definition with the given identifier.
	 * 
	 * @throws NullPointerException The given property identifier is <code>null</code>. 
	 * @throws BadParameterException The given property identifier is not registered as a property definition for
	 * this class.
	 * 
	 * @see #lookupPropertyDefinition(AUID)
	 * @see #lookupPropertyDefinition(String)
	 * @see #getAllPropertyDefinitions()
	 * 
	 */
	public PropertyDefinition lookupPropertyDefinition(
			short propertyTag) 
		throws NullPointerException, 
			BadParameterException;
	
	/**
	 * <p>Gets the Parent class for this class definition.  If there is no parent then
	 * <code>null</code> is returned.  The only classes which have no parent will
	 * be {@link InterchangeObject} and {@link MetaDefinition}. These classes return
	 * themselves as their own parents to indicate that they are the root of the
	 * type hierarchy.</p>
	 * 
	 * @return Parent class of this class definition, this class if it is the root of
	 * the class hierarchy or <code>null</code> if no parent class has been defined.
	 */
	public ClassDefinition getParent();

	/**
	 * <p>Returns <code>true</code> if the defined class can be instantiated; returns <code>false</code> 
	 * otherwise. If the class is not concrete, 
	 * then it is abstract and implemented by an abstract Java class in the MAJ API. 
	 * Any object in an AAF file that belongs to an 
	 * abstract class shall also belong to a concrete subclass of the 
	 * abstract class.</p>
	 * 
	 * @return Can the defined class be instantiated?
	 */
	public @Bool boolean isConcrete();

	/** 
	 * <p>Returns <code>true</code> if the defined class is the base of the inheritance
	 * hierarchy; returns <code>false</code> otherwise. </p>
	 * 
	 * @return Is this a root (base) class?
	 */
	public @Bool boolean isRoot();

	/**
	 * <p>Returns <code>true</code> if the defined class is uniquely identified, which means that it
	 * has a single property that is a unique identifier for any given instance.</p>
	 * 
	 * @return Is this a uniquely identified class?
	 * 
	 * @see #getUniqueIdentifierProperty()
	 * @see PropertyDefinition#getIsUniqueIdentifier()
	 * @see #getUniqueIdentifierValue(MetadataObject)
	 */
	public @Bool boolean isUniquelyIdentified();
	
	/**
	 * <p>Returns the property definition corresponding to this class'
	 * unique identifier.</p>
	 * 
	 * @return The unique identifier of this class definition, or 
	 * <code>null</code> if the class does not have a unique identifier
	 * property definition.
	 * 
	 * @see #isUniquelyIdentified()
	 * @see PropertyDefinition#getIsUniqueIdentifier()
	 * @see #getUniqueIdentifierValue(MetadataObject)
	 */
	public PropertyDefinition getUniqueIdentifierProperty();

	/** 
	 * <p>Creates an instance of this class and returns it, or <code>null</code> if the instantiation
	 * failed. This method is not the recommended way of creating objects in the MAJ API as the values 
	 * of the class will not be initialized to sensible values. To create implementations of the objects safely,
	 * use the make methods of the {@linkplain MediaEngine media engine} instead.</p>
	 * 
	 * <p>This method is provided for implementations of file reading mechanisms that need to create 
	 * uninitialized objects before setting all required properties from the input stream.</p>
	 * 
	 * @return An instance object defined by this class definition, or <code>null</code> if the 
	 * instantiation failed.
	 * 
	 * @see Forge#make(Class, Object...)
	 */
	public MetadataObject createInstance();

	/** 
	 * <p>Returns the Java class implementation associated with the defined class.</p>
	 *
	 * @return Java class implementation associated with this AAF class definition.
	 * 
	 * @throws PropertyNotPresentException The optional java implementation property is not present
	 * in this AAF class definition.
	 * 
	 * @see tv.amwa.maj.industry.MediaClass
	 * @see tv.amwa.maj.industry.Warehouse#lookForClass(Class)
	 */
	public Class<?> getJavaImplementation()
		throws PropertyNotPresentException;
	
	/** 
	 * <p>Sets the Java class implementation associated with the defined class.</p>
	 *
	 * @param javaImplementation Java class implementation associated with this AAF class definition.
	 * 
	 * @see tv.amwa.maj.industry.MediaClass
	 * @see tv.amwa.maj.industry.Warehouse#lookForClass(Class)
	 */
	public void setJavaImplementation(
			Class<?> javaImplementation);
	
	/**
	 * <p>Returns the set of properties of the given {@linkplain MetadataObject object}, 
	 * where each property contains its {@linkplain PropertyDefinition property definition}
	 * and current value. The set contains both required properties and optional properties
	 * that are currently present.</p>
	 * 
	 * @param metadataObject Object of this class to retrieve the properties for. 
	 * @return Iterator over the set of properties of this interchange object.
	 * 
	 * @throws NullPointerException The metadata object is <code>null</code>.
	 * @throws IllegalArgumentException The metadata object is not an instance of 
	 * this class.
	 */
	public SortedMap<? extends PropertyDefinition, ? extends PropertyValue> getProperties(
			MetadataObject metadataObject)
		throws NullPointerException,
			IllegalArgumentException;

	/**
	 * <p>Returns the number of properties currently present in the
	 * given metadata object, including all required properties.</p>
	 * 
	 * @param metadataObject Object of this class to count the number of present
	 * properties.
	 * @return Number of properties present for the given metadata object.
	 * 
	 * @throws NullPointerException The metadata object is <code>null</code>.
	 * @throws IllegalArgumentException The metadata object is not an instance of 
	 * this class.
	 */
	public @UInt32 int countProperties(
			MetadataObject metadataObject)
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Returns the symbol name for this class, which is the same as its tag name
	 * in registered data XML. Symbol names provide a namespace with unique names
	 * for all SMPTE registered class names and property names.</p>
	 * 
	 * @return Symbol name of this class.
	 */
	public @AAFString String getSymbol();
	
	/**
	 * <p>Returns the current value of the unique identifier for a value of a type
	 * defined by this class definition. The class must have a 
	 * {@linkplain #getUniqueIdentifierProperty() unique identifier} for this
	 * method to by successful.</p>
	 * 
	 * @param metadataObject Object to find the unique identifier for.
	 * @return Value of the unique identifier.
	 * 
	 * @throws NullPointerException Cannot find the unique identifier for a 
	 * <code>null</code> value.
	 * @throws IllegalArgumentException The given object does not have a unique
	 * identifier property.
	 * 
	 * @see #isUniquelyIdentified()
	 * @see #getUniqueIdentifierProperty()
	 */
	public PropertyValue getUniqueIdentifierValue(
			MetadataObject metadataObject)
		throws NullPointerException,
			IllegalArgumentException;

	/**
	 * <p>Determines how the identifier of the class should be serialized to XML.
	 * For more details, see the description of the {@linkplain EmitXMLClassIDAs emit 
	 * XML class ID as} enumeration.</p>
	 * 
	 * @return How should the identifier of the class should be serialized to XML?
	 * 
	 * @see MediaClass#emitXMLClassID()
	 * @see CommonConstants#XMLClassIDAsAttributeName
	 */
	public EmitXMLClassIDAs getEmitXMLClassIDAs();
	
	/**
	 * <p>Create a cloned copy of this class definition.</p>
	 *
	 * @return Cloned copy of this class definition.
	 */
	public ClassDefinition clone();

}
