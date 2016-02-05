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
 * $Log: ParameterImpl.java,v $
 * Revision 1.4  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/06/18 16:50:44  vizigoth
 * Minor spelling error fix in null pointer exception message.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:39  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.constant.ParameterConstant;
import tv.amwa.maj.exception.ParameterNotFoundException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.model.Parameter;
import tv.amwa.maj.model.ParameterDefinition;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;


/** 
 * <p>Implements an effect control value. Effect control values are parameters to operations
 * specified by {@linkplain tv.amwa.maj.model.OperationGroup operation groups}.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3c00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Parameter",
		  description = "The Parameter class specifies an effect control value.",
		  symbol = "Parameter",
		  isConcrete = false)
public class ParameterImpl
	extends InterchangeObjectImpl
	implements Parameter,
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5979416040720344072L;
	
	private AUID parameterDefinitionReference;
	
	public ParameterDefinition getParameterDefinition() {

		return ParameterDefinitionImpl.forIdentification(parameterDefinitionReference).clone();
	}

	public void setParameterDefinitionReference(
			AUID parameterDefinitionReference) 
		throws NullPointerException,
			ParameterNotFoundException {
		
		if (parameterDefinitionReference == null)
			throw new NullPointerException("Cannot set the parameter definition reference from a null value.");
		
		ParameterDefinition parameterDefinition = 
			ParameterDefinitionImpl.forIdentification(parameterDefinitionReference);
		
		if (parameterDefinition == null)
			throw new ParameterNotFoundException("The given parameter identifier does not map to a known parameter.");
		
		this.parameterDefinitionReference = parameterDefinitionReference.clone();
	}

	@MediaPropertySetter("ParameterDefinitionReference")
	public void setParameterDefinitionReferenceFromStream(
			AUID parameterDefinitionReference)
		throws NullPointerException {
		
		if (parameterDefinitionReference == null)
			throw new NullPointerException("Cannot set the parameter definition reference from a null value.");
		
		this.parameterDefinitionReference = parameterDefinitionReference.clone();	
	}
	
	public void setParameterDefinition(
			ParameterDefinition parameterDefinition) 
		throws NullPointerException{
		
		if (parameterDefinition == null)
			throw new NullPointerException("Cannot set the parameter type for this parameter with a null definition.");

		this.parameterDefinitionReference = parameterDefinition.getAUID().clone();
	}
	
	public final static AUID initializeParameterDefinitionReference() {
		
		return ParameterConstant.Level;
	}
	
	/**
	 * <p>Returns the unique identifier for the parameter definition of this parameter.</p>
	 *
	 * @return Unique identifier for the parameter definition of this parameter.
	 */
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0104, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ParameterDefinitionReference",
			aliases = { "Definition" },
			typeName = "AUID",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4C01,
			symbol = "ParameterDefinitionReference")
	public AUID getParameterDefinitionReference() {
		
		return parameterDefinitionReference.clone();
	}
	
	public TypeDefinition getTypeDefinition() {

		return ParameterDefinitionImpl.forIdentification(parameterDefinitionReference).getParameterType();
	}

	public Parameter clone() {
		
		return (Parameter) super.clone();
	}
	
	public String getComment() {

		return "local parameter persistent id is " + getPersistentID() + 
			" and the parameter is named " + 
			ParameterDefinitionImpl.forIdentification(parameterDefinitionReference).getName();
	}
	
	public String getParameterDefinitionReferenceString() {
		
		return AUIDImpl.toPersistentForm(parameterDefinitionReference);
	}
	
	public void setParameterDefinitionReferenceString(
			String parameterDefinitionReference) {
		
		this.parameterDefinitionReference = AUIDImpl.fromPersistentForm(parameterDefinitionReference);
	}
}
