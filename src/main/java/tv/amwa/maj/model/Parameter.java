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
 * $Log: Parameter.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/02/08 11:27:26  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:34  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.record.AUID;

/**
 * <p>Specifies an effect control value. Effect control values are parameters to operations
 * specified by {@linkplain OperationGroup operation groups}.</p>
 * 
 * <p>An example of a parameter value would be the
 * <em>level</em> parameter of a video dissolve, which has control
 * points with a value of zero (0&nbsp;percent B&nbsp;material) at the
 * start, to one (100&nbsp;percent B&nbsp;material) at the end.  The data
 * value will actually be stored in either a {@linkplain ConstantValue constant value} or 
 * one of the {@linkplain ControlPoint control points} inside of a
 * {@linkplain VaryingValue varying value}.</p>
 * 
 *
 *
 * @see ParameterDefinition
 * @see tv.amwa.maj.constant.ParameterConstant
 * @see OperationGroup#addParameter(Parameter)
 * @see OperationDefinition#addParameterDefinition(ParameterDefinition)
 * @see Dictionary#getParameterDefinitions()
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterStrongReferenceVector
 */

public abstract interface Parameter 
	extends InterchangeObject {

	/**
	 * <p>Returns the {@linkplain ParameterDefinition parameter definition} of 
	 * this operation parameter.</p>
	 * 
	 * @return Parameter definition of this operation parameter.
	 * 
	 * @see #getParameterDefinitionReference()
	 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
	 */
	public ParameterDefinition getParameterDefinition();

	/**
	 * <p>Returns the identifier for the {@linkplain ParameterDefinition parameter definition} of
	 * this operation parameter.</p>
	 *
	 * @return Identifier of the parameter definition of this operation parameter.
	 * 
	 * @see #getParameterDefinition()
	 * @see DefinitionObject#getAUID()
	 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, AUID)
	 */
	public AUID getParameterDefinitionReference();
	
	/**
	 * <p>Returns the {@linkplain TypeDefinition type definition} of the data 
	 * value of this parameter. It is often an integer or rational value and
	 * the value may vary with time.</p>
	 * 
	 * <p>Note that the type definition is stored in the base class because 
	 * it should be the same for all {@linkplain ControlPoint control points} inside 
	 * of a {@linkplain VaryingValue varying value}.</p>
	 * 
	 * @return Type definition of the data value inside the parameter.
	 * 
	 * @see #getParameterDefinition()
	 * @see ParameterDefinition#getParameterType()
	 */
	public TypeDefinition getTypeDefinition();
	
	/**
	 * <p>Create a cloned copy of this parameter.</p>
	 *
	 * @return Cloned copy of this parameter.
	 */
	public Parameter clone();
}
