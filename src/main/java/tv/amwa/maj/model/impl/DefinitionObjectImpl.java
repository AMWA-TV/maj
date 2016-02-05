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
 * $Log: DefinitionObjectImpl.java,v $
 * Revision 1.6  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/24 14:01:40  vizigoth
 * Completed annotation and definition auto test generation.
 *
 * Revision 1.3  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.2  2011/01/18 09:13:54  vizigoth
 * Fixes after writing Warehouse unit tests.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
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
 * Revision 1.1  2007/11/13 22:09:46  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.model.DefinitionObject;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

/** 
 * <p>Implements a definition to be referenced.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1a00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "DefinitionObject",
		  description = "The DefinitionObject class defines an item to be referenced.",
		  symbol = "DefinitionObject",
		  isConcrete = false)
public class DefinitionObjectImpl
	extends InterchangeObjectImpl
	implements DefinitionObject,
			DefinitionType,
			WeakReferenceTarget,
			Serializable,
			Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6442990440375118888L;
	
	private AUID definitionObjectIdentification;
	private String definitionObjectName;
	private String definitionObjectDescription = null;

	/** <p>Used to create human readable weak references to definitions in XML input/output.</p> */
	private String localizedUID = null;
	
	@MediaProperty(uuid1 = 0x01011503, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "DefinitionObjectIdentification",
			aliases = { "AUID", "Identification" },
			typeName = "AUID",
			optional = false,
			uniqueIdentifier = true,
			pid = 0x1B01,
			symbol = "DefinitionObjectIdentification")
	public AUID getAUID() {

		return definitionObjectIdentification.clone();
	}

	@MediaPropertySetter("DefinitionObjectIdentification")
	public void setAUID(
			AUID identification) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot set the identification of this definition to a null value.");
		
		this.definitionObjectIdentification = identification.clone();
		
	}
	
	public final static AUID initializeDefinitionObjectIdentification() {
		
		return AUIDImpl.randomAUID();
	}
	
	@MediaProperty(uuid1 = 0x01070102, uuid2 = (short) 0x0301, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "DefinitionObjectName",
			aliases = { "Name" },
			typeName = "UTF16String",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1B02,
			symbol = "DefinitionObjectName")
	public String getName() {

		return definitionObjectName;
	}
	
	@MediaPropertySetter("DefinitionObjectName")
	public void setName(
			String name)
		throws NullPointerException {

			if (name == null)
				throw new NullPointerException("Cannot set the name of a definition from a null string.");
			
			this.definitionObjectName = name;
	}

	private static int nameCounter = 1;
	
	public final static String initializeDefinitionObjectName() {
		
		return "DefaultDefinitionName" + (nameCounter++);
	}
	
	@MediaProperty(uuid1 = 0x03020301, uuid2 = (short) 0x0201, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "DefinitionObjectDescription",
			aliases = { "Description" },
			typeName = "UTF16String", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1B03,
			symbol = "DefinitionObjectDescription")
	public String getDescription() 
		throws PropertyNotPresentException {
		
		if (definitionObjectDescription == null) 
			throw new PropertyNotPresentException("The optional description of the definition is not present.");

		return definitionObjectDescription;
	}

	@MediaPropertySetter("DefinitionObjectDescription")
	public void setDescription(
			String description) {

		this.definitionObjectDescription = description;
	}

	public String getLocalizedUID() {
		
		if (localizedUID == null)
			return definitionObjectIdentification.toString();
		else
			return localizedUID;
	}
	
	public void setLocalizedUID(
			String localizedUID) {
		
		localizedUID.replaceAll("\\W", "_");
		this.localizedUID = localizedUID;
	}
	
	public DefinitionObject clone() {
		
		return (DefinitionObject) super.clone();
	}
	
	public String getWeakTargetReference() {
		
		return definitionObjectIdentification.toString();
	}
	
	public String getDefinitionObjectIdentificationString() {
		
		return AUIDImpl.toPersistentForm(definitionObjectIdentification);
	}
	
	public void setDefinitionObjectIdentificationString(
			String definitionObjectIdentification) {
		
		this.definitionObjectIdentification = 
			AUIDImpl.fromPersistentForm(definitionObjectIdentification);
	}
}
