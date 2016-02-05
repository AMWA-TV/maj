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
 * $Log: ParameterDefinition.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/02/08 11:27:17  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:11  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.misctype.AAFString;

/**
 * <p>Specifies the definition of a kind of {@linkplain Parameter parameter} required to control 
 * an effect.</p>
 * 
 *
 *
 * @see Parameter
 * @see tv.amwa.maj.constant.ParameterConstant
 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
 * @see Dictionary#getParameterDefinitions()
 * @see OperationDefinition#getOperationParametersDefined()
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionWeakReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionStrongReferenceSet
 */

public interface ParameterDefinition 
	extends DefinitionObject {

	/**
	 * <p>Returns the {@linkplain TypeDefinition type definition} attached to
	 * this parameter definition, which specifies the data type of the parameter.</p>
	 * 
	 * @return Type definition attached to this parameter definition.
	 */
	public TypeDefinition getParameterType();

	/**
	 * <p>Sets the display units of this parameter definition, which is a displayable string 
	 * identifying the units in which the parameter is measured. For example: "%&nbsp;of 
	 * picture width". Set this optional property to <code>null</code> to omit
	 * it.</p>
	 * 
	 * @param displayUnits Display units of this parameter definition.
	 */
	public void setParameterDisplayUnits(
			@AAFString String displayUnits);

	/**
	 * <p>Returns the display unit property of this parameter definition, which is a displayable 
	 * string identifying the units in which the parameter is measured. 
	 * For example: "%&nbsp;of picture width". This property is optional.</p>
	 * 
	 * @return Display unit property of the parameter definition.
	 * 
	 * @throws PropertyNotPresentException The optional display units property
	 * is not present in this parameter definition.
	 */
	public @AAFString String getParameterDisplayUnits()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this parameter definition.</p>
	 *
	 * @return Cloned copy of this parameter definition.
	 */
	public ParameterDefinition clone();
}
