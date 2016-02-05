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
 * $Log: InterpolationDefinitionImpl.java,v $
 * Revision 1.4  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
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
 * Revision 1.3  2010/03/19 09:40:57  vizigoth
 * Added support for lazy weak reference resolutions through forAUID method.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:45  vizigoth
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

import tv.amwa.maj.constant.InterpolationConstant;
import tv.amwa.maj.constant.InterpolationDescription;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.InterpolationDefinition;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements a definition for the mechanism used to calculate the values produced by a
 * {@linkplain tv.amwa.maj.model.VaryingValue varying value} using the specified 
 * {@linkplain tv.amwa.maj.model.ControlPoint control points}.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#InterpolationDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#InterpolationDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#InterpolationDefinitionStrongReferenceSet
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2100,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "InterpolationDefinition",
		  description = "The InterpolationDefinition class specifies the mechanism used to calculate the values produced by a VaryingValue using the specified ControlPoints.",
		  symbol = "InterpolationDefinition")
public class InterpolationDefinitionImpl
	extends DefinitionObjectImpl
		implements
			InterpolationDefinition,
			Serializable,
			InterpolationConstant,
			Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -959360620943627125L;

	private static final Map<String, InterpolationDefinition> definitionsByName =
		new HashMap<String, InterpolationDefinition>(20);
	private static final Map<AUID, InterpolationDefinition> definitionsById =
		new HashMap<AUID, InterpolationDefinition>(10);
	
	static {		
		registerInterpolationsFromClass(InterpolationConstant.class);
	}
	
	public final static int registerInterpolationsFromClass(
			Class<?> classWithInterpolations) 
		throws NullPointerException {
		
		if (classWithInterpolations == null)
			throw new NullPointerException("Cannot register parameter definitions using a null class.");
		
		int registered = 0;
		Field[] fields = classWithInterpolations.getFields();
		for ( Field field : fields ) {
				
			int modifiers = field.getModifiers();
			if ((!Modifier.isPublic(modifiers)) && (!Modifier.isStatic(modifiers))) continue;
				
			try {
				Object value = field.get(null);
				if (!(value instanceof tv.amwa.maj.record.AUID)) continue;

				if (!(field.isAnnotationPresent(InterpolationDescription.class))) continue;
				
				InterpolationDescription interpolationMetadata = 
					field.getAnnotation(InterpolationDescription.class);	

				InterpolationDefinitionImpl definedInterpolation = new InterpolationDefinitionImpl(
						(tv.amwa.maj.record.AUID) value,
						field.getName());
				
				if (interpolationMetadata.description().length() > 0)
					definedInterpolation.setDescription(interpolationMetadata.description());
		
				// System.out.println(definedInterpolation.toString());
				
				registerInterpolationDefinition(definedInterpolation);
				
				for ( String alias : interpolationMetadata.aliases() )
					definitionsByName.put(alias, definedInterpolation);
				
				registered++;
			}
			catch (IllegalAccessException iae) { /* Parameter definition was not meant to be. */ }
		}

		return registered;
	}
	
	public final static boolean registerInterpolationDefinition(
			InterpolationDefinition definedInterpolation) 
		throws NullPointerException {
		
		if (definedInterpolation == null)
			throw new NullPointerException("Cannot register a new interpolation definition using a null value.");
		
		boolean alreadyContained = definitionsById.containsKey(definedInterpolation.getAUID());
		
		definitionsByName.put(definedInterpolation.getName(), definedInterpolation);
		definitionsByName.put("InterpolationDef_" + definedInterpolation.getName(), definedInterpolation);
		definitionsById.put(definedInterpolation.getAUID(), definedInterpolation);		

		return alreadyContained;
	}
	
	public final static InterpolationDefinition forName(
			String interpolationName) 
		throws NullPointerException {
		
		if (interpolationName == null)
			throw new NullPointerException("Cannot retrieve an interpolation definition with a null name.");
		
		return definitionsByName.get(interpolationName);
	}
	
	public final static InterpolationDefinition forIdentification(
			tv.amwa.maj.record.AUID identification) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot retrieve a parameter definition with a null identification.");
		
		return definitionsById.get(identification);
	}

	public final static InterpolationDefinition forAUID(
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
	
	public InterpolationDefinitionImpl() { }

	/**
	 * <p>Creates and initalizes a new interpolation definition, which specifies the mechanism used 
	 * to calculate the values produced by a {@link VaryingValueImpl varying value} using the specified 
	 * {@link ControlPointImpl control points}.</p>
	 *
	 * @param identification Unique identifier for this interpolation.
	 * @param name Display name for the interpolation.
	 * 
	 * @throws NullPointerException One or both of the arguments is <code>null</code>.
	 */
	public InterpolationDefinitionImpl(
			AUID identification,
			@AAFString String name)
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create an interpolation definition with a null id.");
		
		setAUID(identification);
		setName(name);
	}

	public InterpolationDefinition clone() {
		
		return (InterpolationDefinition) super.clone();
	}

	@Override
	public String getLocalizedUID() {
		
		if (getName().startsWith("InterpolationDef_")) 
			return getName();
		else
			return "InterpolationDef_" + getName();
	} 
}
