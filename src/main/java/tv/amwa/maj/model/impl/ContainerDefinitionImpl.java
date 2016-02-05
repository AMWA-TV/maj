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
 * $Log: ContainerDefinitionImpl.java,v $
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
 * Revision 1.2  2010/03/19 09:40:57  vizigoth
 * Added support for lazy weak reference resolutions through forAUID method.
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
 * Revision 1.1  2007/11/13 22:09:48  vizigoth
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

import tv.amwa.maj.constant.ContainerConstant;
import tv.amwa.maj.constant.ContainerDescription;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.model.AAFFile;
import tv.amwa.maj.model.ContainerDefinition;
import tv.amwa.maj.model.EssenceData;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements a definition for the mechanism used to store {@linkplain EssenceData essence data}. A container can 
 * be either a kind of file, such as an {@linkplain AAFFile AAF file}, or it can be another mechanism for storing essence 
 * data.</p>
 *
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionStrongReferenceSet
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "ContainerDefinition",
		  description = "The ContainerDefinition class specifies the mechanism used to store essence data.",
		  symbol = "ContainerDefinition")
public class ContainerDefinitionImpl
	extends DefinitionObjectImpl
	implements ContainerDefinition,
		Serializable,
		ContainerConstant,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 1398838773463122930L;
	
	private Boolean essenceIsIndentified = null;
	
	private final static Map<String, ContainerDefinition> definitionsByName = 
		new HashMap<String, ContainerDefinition>(200);
	private final static Map<AUID, ContainerDefinition> definitionsById =
		new HashMap<AUID, ContainerDefinition>(100);
	
	static {
		registerContainersFromClass(ContainerConstant.class);
	}
	
	public final static int registerContainersFromClass(
			Class<?> classWithContainers) 
		throws NullPointerException {
		
		if (classWithContainers == null)
			throw new NullPointerException("Cannot register parameter definitions using a null class.");
		
		int registered = 0;
		Field[] fields = classWithContainers.getFields();
		for ( Field field : fields ) {
				
			int modifiers = field.getModifiers();
			if ((!Modifier.isPublic(modifiers)) && (!Modifier.isStatic(modifiers))) continue;
				
			try {
				Object value = field.get(null);
				if (!(value instanceof tv.amwa.maj.record.AUID)) continue;

				if (!(field.isAnnotationPresent(ContainerDescription.class))) continue;
				
				ContainerDescription containerMetadata = 
					field.getAnnotation(ContainerDescription.class);	

				ContainerDefinitionImpl definedContainer = new ContainerDefinitionImpl(
						(tv.amwa.maj.record.AUID) value,
						field.getName());
				
				if (containerMetadata.description().length() > 0)
					definedContainer.setDescription(containerMetadata.description());
		
				// System.out.println(definedContainer.toString());
				
				registerContainerDefinition(definedContainer);
			
				for ( String alias : containerMetadata.aliases() )
					definitionsByName.put(alias, definedContainer);
				
				registered++;
			}
			catch (IllegalAccessException iae) { /* Parameter definition was not meant to be. */ }
		}

		return registered;
	}
	
	public final static boolean registerContainerDefinition(
			ContainerDefinition definedContainer) 
		throws NullPointerException {
		
		if (definedContainer == null)
			throw new NullPointerException("Cannot register a new container definition using a null value.");
		
		boolean alreadyContained = definitionsById.containsKey(definedContainer.getAUID());
		
		definitionsByName.put(definedContainer.getName(), definedContainer);
		definitionsByName.put("ContainerDef_" + definedContainer.getName(), definedContainer);
		definitionsById.put(definedContainer.getAUID(), definedContainer);		

		return alreadyContained;
	}
	
	public final static ContainerDefinition forName(
			String containerName) 
		throws NullPointerException {
		
		if (containerName == null)
			throw new NullPointerException("Cannot retrieve a container definition with a null name.");
		
		return definitionsByName.get(containerName);
	}
	
	public final static ContainerDefinition forIdentification(
			tv.amwa.maj.record.AUID identification) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot retrieve a container definition with a null identification.");
		
		return definitionsById.get(identification);
	}

	public final static ContainerDefinition forAUID(
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
	
	public ContainerDefinitionImpl() { }

	/**
	 * <p>Creates and initializes a container definition, which specifies the mechanism 
	 * used to store essence data. A container can be either a kind of file, such as an AAF file,
	 * or it can be another mechanism for storing essence data. The essence is idenfified flag is 
	 * initially set to <code>false</code>. Call 
	 * {@link ContainerDefinitionImpl#setEssenceIsIdentified(Boolean) setEssenceIsIdentified()} to
	 * change this.</p>
	 * 
	 * @param identification Unique identifier for the new container definition.
	 * @param name Display name for new container definition.
	 * 
	 * @throws NullPointerException Identification argument is <code>null</code>.
	 */
	public ContainerDefinitionImpl (
			AUID identification,
			String name)
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a container definition with a null identifier.");

		setAUID(identification);
		setName(name);
	}

	public boolean essenceIsIdentified() 
		throws PropertyNotPresentException {

		return getEssenceIsIdentified();
	}

	@MediaProperty(uuid1 = 0x03010201, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "EssenceIsIdentified",
			typeName = "Boolean",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2401,
			symbol = "EssenceIsIdentified")
	public boolean getEssenceIsIdentified() 
		throws PropertyNotPresentException {

		if (essenceIsIndentified == null)
			throw new PropertyNotPresentException("The optional essence is identified property is not present in this container definition.");

		return essenceIsIndentified;
	}

	@MediaPropertySetter("EssenceIsIdentified")
	public void setEssenceIsIdentified(
			Boolean essenceIsIdentified) {

		this.essenceIsIndentified = essenceIsIdentified;
	}
	
	public ContainerDefinition clone()  {
		
		return (ContainerDefinition) super.clone();
	}
	
	@Override
	public String getWeakTargetReference() {
		
		if (getName().startsWith("ContainerDef_")) 
			return getName();
		else
			return "ContainerDef_" + getName();
	}
}
