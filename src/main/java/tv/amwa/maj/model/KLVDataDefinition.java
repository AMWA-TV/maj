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
 * $Log: KLVDataDefinition.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/28 12:50:33  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2008/01/27 11:07:39  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:19  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionOpaque;

/* import java.util.Set;

import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.integer.UInt32; */

// TODO ask what the purpose of the add parent property methods is?

// Removed parent property methods as not implemented in reference implementation

/**
 * <p>Specifies the documentation for the {@linkplain KLVData KLV data} objects 
 * used in a file.</p>
 * 
 * <p>The {@linkplain DefinitionObject#getAUID() identification property} of the 
 * {@linkplain DefinitionObject definition object} super-class shall identify a particular 
 * {@linkplain tv.amwa.maj.record.AUID AUID} that is used for the key of instances of the 
 * {@linkplain KLVData KLV data} being documented.</p>
 * 
 *
 * 
 * @see Dictionary#getKLVDataDefinitions()
 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataDefinitionStrongReferenceSet
 */

public interface KLVDataDefinition 
	extends DefinitionObject {
	
	/**
	 * <p>Add a parent property definition to the KLV data definition.</p>
	 * 
	 * @param parentProperty Parent property definition to add.
	 * @throws NullPointerException Argument is null.
	 */
	/* public void addParentProperty(
			PropertyDefinition parentProperty) 
		throws NullPointerException; */

	/**
	 * <p>Returns a set of property definitions associated with the KLV
	 * data definition.</p>
	 * 
	 * @return Iterator over the set of property definitions of the KLV
	 * data definition.
	 */
	/* public Set<PropertyDefinition> getParentProperties(); */

	/**
	 * <p>Returns the total number of property definitions of the KLV data
	 * definition.</p>
	 * 
	 * @return Number of property definitions of the KLV data
	 * definition.
	 */
	/* public @UInt32 int countParentProperties(); */

	/**
	 * <p>Removes the given property definition from the KLV data definition.</p>
	 * 
	 * @param parentProperty Property to remove from the KLV data definition.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws ObjectNotFoundException The property definition is not contained
	 * within the KLV data definition.
	 */
	/* public void removeParentProperty(
			PropertyDefinition parentProperty) 
		throws NullPointerException,
			ObjectNotFoundException; */

	/**
	 * <p>Returns the KLV data type property for this KLV data definition, which 
	 * specifies the type of the value of the documented {@linkplain KLVData KLV data}.</p>
	 * 
	 * <p>If this optional property is omitted, the default value of a reference to
	 * the type named "{@linkplain tv.amwa.maj.industry.TypeDefinitions#UInt8Array VariableArray of UInt8}" 
	 * is returned.</p> 
	 * 
	 * @return The type definition referenced by this KLV data definition.
	 * 
	 * @see TypeDefinitionOpaque
	 */
	public TypeDefinition getKLVDataType();

	/**
	 * <p>Sets the KLV data type property of this KLV data definition, which 
	 * specifies the type of the value of the documented {@linkplain KLVData KLV data}.</p>
	 * 
	 * <p>To omit this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param typeDefinition Referenced type definition that is the KLV data type property of 
	 * this KLV data definition.
	 */
	public void setKLVDataType(
			TypeDefinition typeDefinition);
	
	/**
	 * <p>Create a cloned copy of this KLV data definition.</p>
	 *
	 * @return Cloned copy of this KLV data definition.
	 */
	public KLVDataDefinition clone();
}

