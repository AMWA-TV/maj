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
 * $Log: ParameterDefinitionImpl.java,v $
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.3  2011/01/18 09:13:55  vizigoth
 * Fixes after writing Warehouse unit tests.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/06/18 16:49:50  vizigoth
 * Fixed registerParameterDefinition to use interfaces rather than implementations.
 *
 * Revision 1.2  2010/03/19 09:40:57  vizigoth
 * Added support for lazy weak reference resolutions through forAUID method.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2008/01/15 12:33:15  vizigoth
 * Added the unexpectedly missing XMLHandler.
 *
 * Revision 1.1  2007/11/13 22:09:38  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import tv.amwa.maj.constant.ParameterConstant;
import tv.amwa.maj.constant.ParameterDescription;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.ParameterDefinition;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements the definition of a kind of {@linkplain tv.amwa.maj.model.Parameter parameter} for 
 * an effect.</p>
 *
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionWeakReferenceSet
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1d00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "ParameterDefinition",
		  description = "The ParameterDefinition class defines a kind of Parameter for an effect.",
		  symbol = "ParameterDefinition")
public class ParameterDefinitionImpl
	extends 
		DefinitionObjectImpl
	implements 
		ParameterDefinition,
		Serializable,
		ParameterConstant,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 5172982416984376588L;
	
	private WeakReference<TypeDefinition> parameterType;
	private String parameterDisplayUnits = null;

	private final static Map<String, ParameterDefinition> definitionsByName =
		new HashMap<String, ParameterDefinition>();
	private final static Map<AUID, ParameterDefinition> definitionsById =
		new HashMap<AUID, ParameterDefinition>();
	
	static {
		registerParametersFromClass(ParameterConstant.class);
	}
	
	public final static int registerParametersFromClass(
			Class<?> classWithParameters) 
		throws NullPointerException {
		
		if (classWithParameters == null)
			throw new NullPointerException("Cannot register parameter definitions using a null class.");
		
		int registered = 0;
		Field[] fields = classWithParameters.getFields();
		for ( Field field : fields ) {
				
			int modifiers = field.getModifiers();
			if ((!Modifier.isPublic(modifiers)) && (!Modifier.isStatic(modifiers))) continue;
				
			try {
				Object value = field.get(null);
				if (!(value instanceof tv.amwa.maj.record.AUID)) continue;

				if (!(field.isAnnotationPresent(ParameterDescription.class))) continue;
				
				ParameterDescription parameterMetadata = 
					field.getAnnotation(ParameterDescription.class);
				
				TypeDefinition parameterType = 
					Warehouse.lookForType(parameterMetadata.typeName());		

				ParameterDefinitionImpl definedParameter = new ParameterDefinitionImpl(
						(tv.amwa.maj.record.AUID) value,
						field.getName(),
						parameterType);
				
				if (parameterMetadata.description().length() > 0)
					definedParameter.setDescription(parameterMetadata.description());
				if (parameterMetadata.displayUnits().length() > 0)
					definedParameter.setParameterDisplayUnits(parameterMetadata.displayUnits());
		
				// System.out.println(definedParameter.toString());
				
				registerParameterDefinition(definedParameter);
			
				for ( String alias : parameterMetadata.aliases() )
					definitionsByName.put(alias, definedParameter);
				
				registered++;
			}
			catch (IllegalAccessException iae) { /* Parameter definition was not meant to be. */ }
		}

		return registered;
	}
	
	public final static boolean registerParameterDefinition(
			ParameterDefinition parameterDefinition) 
		throws NullPointerException {
		
		if (parameterDefinition == null)
			throw new NullPointerException("Cannot register a new parameter definition using a null value.");
		
		boolean alreadyContained = definitionsById.containsKey(parameterDefinition.getAUID());
		
		definitionsByName.put(parameterDefinition.getName(), parameterDefinition);
		definitionsByName.put("ParameterDef_" + parameterDefinition.getName(), parameterDefinition);
		definitionsById.put(parameterDefinition.getAUID(), parameterDefinition);		

		return alreadyContained;
	}
	
	public final static ParameterDefinition forName(
			String parameterName) 
		throws NullPointerException {
		
		if (parameterName == null)
			throw new NullPointerException("Cannot retrieve a parameter definition with a null name.");
		
		return definitionsByName.get(parameterName);
	}
	
	public final static ParameterDefinition forIdentification(
			tv.amwa.maj.record.AUID identification) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot retrieve a parameter definition with a null identification.");
		
		return definitionsById.get(identification);
	}
	
	public final static ParameterDefinition forAUID(
			AUID identification)
		throws NullPointerException {
		
		return forIdentification(identification);
	}
	
	public final static Collection<String> inventory() {
		
		SortedSet<String> inventory = new TreeSet<String>();
		for ( AUID definitionID : definitionsById.keySet()) {
			inventory.add(definitionsById.get(definitionID).getName());
		}
		
		return inventory;
	}
	
	public final static int count() {
		
		return definitionsById.size();
	}
	
	public ParameterDefinitionImpl() {
	}

	/**
	 * <p>Creates and initializes a new parameter definition, which defines a kind of parameter for
	 * an effect.</p>
	 *
	 * @param identification Uniquely identifies the parameter definition.
	 * @param name Display name of the parameter definition.
	 * @param type Data type of the parameter.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and they
	 * are all required properties in this implementation.
	 */
	public ParameterDefinitionImpl(
			AUID identification,
			@AAFString String name,
			TypeDefinition type)
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a parameter definition with a null id.");
		if (type == null)
			throw new NullPointerException("Cannot create a parameter definition with a null type definition.");
		
		setAUID(identification);
		setName(name);
		setParameterType(type);
	}
	
	@MediaProperty(uuid1 = 0x0530050b, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ParameterDisplayUnits",
			aliases = { "DisplayUnits" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1F03,
			symbol = "ParameterDisplayUnits")
	public String getParameterDisplayUnits() 
		throws PropertyNotPresentException {

		if (parameterDisplayUnits == null)
			throw new PropertyNotPresentException("The optional display units property is not present in this parameter definition.");
		
		return parameterDisplayUnits;
	}

	@MediaPropertySetter("ParameterDisplayUnits")
	public void setParameterDisplayUnits(
			String displayUnits) {

		this.parameterDisplayUnits = displayUnits;
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0106, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ParameterType",
			aliases = { "Type", "ParameterDefinitionType" },
			typeName = "TypeDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1F01,
			symbol = "ParameterType")
	public TypeDefinition getParameterType() {

		return parameterType.getTarget();
	}

	@MediaPropertySetter("ParameterType")
	public void setParameterType(
			tv.amwa.maj.meta.TypeDefinition parameterType) 
		throws NullPointerException {
		
		if (parameterType == null)
			throw new NullPointerException("Cannot set the type of a parameter definition to a null value.");
		
		this.parameterType = new WeakReference<TypeDefinition>(parameterType);
	}
	
	public final static TypeDefinition initializeParameterType() {
		
		return TypeDefinitions.Int32;
	}
	
	public ParameterDefinition clone() {
		
		return (ParameterDefinition) super.clone();
	}
}
