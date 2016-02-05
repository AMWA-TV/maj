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
 * $Log: DescriptiveObjectImpl.java,v $
 * Revision 1.3  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.2  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
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
 * Revision 1.3  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:47  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.model.DescriptiveObject;
import tv.amwa.maj.record.AUID;

/** 
 * <p>Implements an item of descriptive metadata in a {@linkplain tv.amwa.maj.model.DescriptiveFramework
 * descriptive framework}.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#DescriptiveObjectStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#DescriptiveObjectStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#DescriptiveObjectStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0D010400, uuid2 = 0x0000, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "DescriptiveObject",
		  description = "Extended by objects that represent descriptive metadata.",
		  symbol = "DescriptiveObject",
		  isConcrete = false)
public class DescriptiveObjectImpl
	extends 
		InterchangeObjectImpl
	implements 
		DescriptiveObject,
		Serializable {

	private static final long serialVersionUID = -1382041109822618231L;

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x1100, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C },
			definedName = "LinkedDescriptiveObjectPluginID",
			aliases = {  "LinkedDescriptiveObjectPlug-InID"},
			typeName = "AUID",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "LinkedDescriptiveObjectPluginID",
			description = "Descriptive marker that indirectly " + 
					"strongly references this descriptive object instance.")
	public AUID getLinkedDescriptiveObjectPluginID()
		throws PropertyNotPresentException {
		
		// TODO
		return null;
	}

	@MediaPropertySetter("LinkedDescriptiveObjectPluginID")
	public void setLinkedDescriptiveObjectPluginID(
			AUID linkedDescriptiveObjectPluginID) {
		
		// TODO
	}

	public DescriptiveObject clone() {
		
		return (DescriptiveObject) super.clone();
	}
	
}
