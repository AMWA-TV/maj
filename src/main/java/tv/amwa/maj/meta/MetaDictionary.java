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
 * $Log: MetaDictionary.java,v $
 * Revision 1.3  2011/02/14 22:32:50  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/11/18 10:45:53  vizigoth
 * Added support for dynamic meta dictionaries and type name mapping for legacy meta dictionary compatibility.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 */

package tv.amwa.maj.meta;

import java.util.Set;

import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.exception.NotImplementedException;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.model.AAFFile;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.record.AUID;

/**
 * <p>Specifies a container for {@linkplain MetaDefinition meta definitions}. Every {@linkplain AAFFile AAF file} 
 * has a dictionary within its {@linkplain tv.amwa.maj.model.Preface preface} and also contains a representation of a 
 * {@linkplain MetaDictionary meta dictionary}.</p>
 * 
 * <p>In MAJ, the meta dictionary is primarily represented by the {@linkplain tv.amwa.maj.industry.Warehouse warehouse}. 
 * This keeps a per-virtual machine record of all meta definitions that have been registered. MAJ meta dictionaries can 
 * be <em>static</em> or <em>dynamic</em>:</p>
 * 
 * <ul>
 *  <li>A <em>static meta dictionary</em> contains all meta definitions known within the current warehouses. 
 *  Static is the default state for the meta dictionary and can be explicitly set using 
 *  {@link #makeStatic()}.</li>
 *  <li>A <em>dynamic meta dictionary</em> contains only the meta definitions required for a given 
 *  {@linkplain tv.amwa.maj.model.Preface preface} at the time the preface is provided. To turn the meta 
 *  dictionary into a dynamic one, call {@link #makeDynamic(tv.amwa.maj.model.Preface)}.</li>
 * </ul>
 * 
 *
 * 
 * @see tv.amwa.maj.model.Dictionary
 * @see MetaDefinition
 * @see tv.amwa.maj.industry.Warehouse#lookForClass(String)
 * @see tv.amwa.maj.industry.Warehouse#lookForType(String)
 * @see tv.amwa.maj.model.Preface
 */
public interface MetaDictionary 
	extends 
		MetadataObject, 
		Cloneable {

	/**
	 * <p>Creates a single uninitialized AAF object of the class associated
	 * with a specified {@linkplain tv.amwa.maj.model.InterchangeObject interchange 
	 * object} class identifier.</p>
	 * 
	 * <p>This method is a factory for creating objects found within the body of an AAF
	 * file. In MAJ, the equivalent functionality is provided through the factory
	 * provided on the {@linkplain tv.amwa.maj.industry.MediaEngine media engine}.</p> 
	 * 
	 * @param identification Class identifier (id) of the stored object. This is the
	 * corresponding SMPTE identifier (as a GUID) for all predefined built-in 
	 * classes or registered extension classes.
	 * @return Object that is an instance of the specified class and implements 
	 * the {@linkplain tv.amwa.maj.model.InterchangeObject interchange object} interface.
	 * 
	 * @throws NullPointerException Cannot create an instance from a <code>null</code>
	 * value.
	 * @throws IllegalArgumentException The given identifier is not known or is not
	 * for an object of interchange type.
	 * 
	 * @deprecated Use the {@link Forge#make(Class, Object...)} or equivalent
	 * instead.
	 */
	public InterchangeObject createInstance(
			AUID identification) 
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Creates a single uninitialized AAF {@linkplain MetaDefinition meta definition}
	 * (class or type) associated with a specified class identifier. </p>
	 * 
	 * <p>This method is a factory for creating objects used to describe the meta dictionary 
	 * of an AAF file. In MAJ, the equivalent functionality is provided through the factory
	 * provided on the {@linkplain tv.amwa.maj.industry.MediaEngine media engine}.</p> 

	 * 
	 * @param identification Identifier (id) of a class or type definition. This is
	 * the corresponding SMPTE identifier (as a GUID) for all predefined 
	 * built-in definitions or registered extensions.
	 * @return Instantiated metaclass.
	 * 
	 * @throws NullPointerException Cannot create a meta instance for a <code>null</code> 
	 * value.
	 * @throws IllegalArgumentException The given identifier is not known or is not for
	 * a meta definition.
	 * 
	 * @deprecated Use the {@link Forge#make(Class, Object...)} or equivalent
	 * instead.
	 */
	public MetaDefinition createMetaInstance(
			AUID identification) 
		throws NotImplementedException;

	/**
	 * <p>Add a class definition to those contained in this dictionary.</p>
	 * 
	 * @param classDefinition Class definition to add.
	 * 
	 * @throws InvalidParameterException A class definition with the same identifier
	 * is already contained in this dictionary.
	 * @throws NullPointerException The given class definition is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.industry.Warehouse#lookForClass(Class)
	 * @see tv.amwa.maj.industry.Warehouse#lookForClass(String)
	 */
	public void registerClassDefinition(
			ClassDefinition classDefinition) 
		throws InvalidParameterException,
			NullPointerException;

	/**
	 * <p>Returns the class definition with the given identifier that is contained in this dictionary.</p>
	 * 
	 * @param identification Unique identifier for a class contained in this dictionary.
	 * @return Class definition with the given identification that is contained in this dictionary.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws InvalidParameterException The given class identifier does not match a class
	 * definition stored in this dictionary.
	 * 
	 * @see Warehouse#lookForClass(Class)
	 * @see tv.amwa.maj.industry.MediaClass
	 */
	public ClassDefinition lookupClassDefinition(
			AUID identification) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns the set of all class definitions in this dictionary.</p>
	 * 
	 * @return Shallow copy of the set of all class definitions contained in this dictionary.
	 */
	public Set<? extends ClassDefinition> getClassDefinitions();

	/**
	 * <p>Returns the number of class definitions contained in this dictionary.</p>
	 * 
	 * @return Number of class definitions contained in this dictionary.
	 */
	public @UInt32 int countClassDefinitions();

	/**
	 * <p>Creates a forward class reference that is used to allow a class definition to
	 * be referenced before it has had its full definition stored into this dictionary.</p>
	 * 
	 * @param classId Unique class identifier for the forward reference.
	 * 
	 * @throws InvalidParameterException The given ID represents an existing 
	 * forward class reference or a class definition that has already been 
	 * successfully registered.
	 */
	public void createForwardClassReference(
			AUID classId) 
		throws InvalidParameterException;

	/**
	 * <p>Returns <code>true</code> if the given class identification is a 
	 * forward reference registered with this dictionary.</p>
	 * 
	 * @param classId Unique class identifier.
	 * @return Has the class got a forward reference in this dictionary?
	 * 
	 * @throws InvalidParameterException The given ID is not recognized as a 
	 * class definition id.
	 */
	public @Bool boolean hasForwardClassReference(
			AUID classId) 
		throws InvalidParameterException;

	/**
	 * <p>Add a type definition to those contained in this dictionary.</p>
	 * 
	 * @param typeDefinition Type definition to add to this dictionary.
	 * 
	 * @throws NullPointerException The given type definition is <code>null</code>.
	 * @throws InvalidParameterException The given type has an id that matches
	 * the id of a type definition already contained in the dictionary.
	 * 
	 * @see tv.amwa.maj.industry.Warehouse#lookForType(String)
	 * @see tv.amwa.maj.industry.Warehouse#lookForType(AUID)
	 * @see tv.amwa.maj.industry.Warehouse#registerTypes(Class, String, String)
	 * @see tv.amwa.maj.industry.Warehouse#register(TypeDefinition, String, String)
	 */
	public void registerTypeDefinition(
			TypeDefinition typeDefinition) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Return the type definition with the given identifier that is contained within this dictionary.</p>
	 * 
	 * @param identification Unique identifier for the type definition contained in this dictionary.
	 * @return Type definition in this dictionary with the given type id.
	 * 
	 * @throws NullPointerException The given type identifier is <code>null</code>.
	 * @throws InvalidParameterException The given identifier does not match that of a type
	 * definition contained in this dictionary. 
	 */
	public TypeDefinition lookupTypeDefinition(
			AUID identification) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns the set of all type definitions contained within this dictionary.</p>
	 * 
	 * @return Shallow copy of the set of type definitions contained in this dictionary.
	 */
	public Set<? extends TypeDefinition> getTypeDefinitions();

	/**
	 * <p>Returns the number of type definitions contained in the dictionary.</p>
	 * 
	 * @return Number of type definitions in this dictionary.
	 */
	public @UInt32 int countTypeDefinitions();

	// TODO work on these methods to do with opaque type definitions!
	
	/**
	 * <p>Add the opaque type definition to the dictionary.</p>
	 * 
	 * <p>An opaque type definition defines a property type that has a 
	 * value whose type is specified in each instance.</p>
	 * 
	 * @param typeDef Type definition object.
	 * @throws NullPointerException Argument is null.
	 * @throws InvalidParameterException The given type has already 
	 * been registered.
	 */
	public void registerOpaqueTypeDefinition(
			TypeDefinition typeDef) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Return the opaque type definition with the given id.</p>
	 * 
	 * @param typeId Unique type ID.
	 * @return Corresponding type definition.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws InvalidParameterException The given ID is not recognized as 
	 * a type definition ID.
	 */
	public TypeDefinition lookupOpaqueTypeDefinition(
			AUID typeId) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Return the set of all registered opaque type definitions
	 * in the dictionary.</p>
	 * 
	 * @return Iterator over the set of all registered opaque type definitions.
	 */
	public Set<? extends TypeDefinition> getOpaqueTypeDefinitions();

	/**
	 * <p>Returns the number of opaque type definitions in the dictionary.</p>
	 * 
	 * @return Number of opaque type definitions.
	 */
	public @UInt32 int countOpaqueTypeDefinitions();
	
	/**
	 * <p>Is this meta dictionary dynamic? A dynamic meta dictionary represents only the 
	 * meta definitions required to represent a given preface at a specific point in time.
	 * Otherwise, the meta dictionary is static and represents all meta definitions known
	 * to the current virtual machine.</p>
	 * 
	 * @return Is this meta dictionary dynamic?
	 * 
	 * @see #makeStatic()
	 * @see #makeDynamic(Preface)
	 */
	public boolean getIsDynamic();
	
	/**
	 * <p>Turn this meta dictionary from static to dynamic and/or update the meta definitions
	 * to represent those required to represent the given preface.</p>
	 * 
	 * @param preface Preface to compute the meta dictionary for.
	 * @throws NullPointerException Cannot make a dynamic meta dictionary from a <code>null</code> 
	 * value.
	 * 
	 * @see #getIsDynamic()
	 * @see #makeStatic()
	 */
	public void makeDynamic(
			Preface preface)
		throws NullPointerException;
	
	/**
	 * <p>Turn this meta dictionary into a static meta dictionary, which is the default state.
	 * In this state, the meta dictionary holds all {@linkplain MetaDefinition meta definitions}
	 * known to the current virtual machine.</p>
	 * 
	 * @see #getIsDynamic()
	 * @see #makeDynamic(Preface)
	 */
	public void makeStatic();
	
	/**
	 * <p>Create a cloned copy of this meta dictionary.</p>
	 * 
	 * @return Cloned copy of this meta dictionary.
	 */
	public MetaDictionary clone();

}
