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
 * $Log: DescriptiveFrameworkImpl.java,v $
 * Revision 1.3  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.2  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/07/14 13:34:35  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
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
 * Revision 1.1  2007/11/13 22:09:37  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.model.DescriptiveFramework;
import tv.amwa.maj.record.AUID;

/** 
 * <p>Implements a framework for descriptive metadata. Sub-interfaces of this interface are taken
 * from vocabularies of descriptive metadata, such as DMS-1, known as <em>descriptive
 * metadata schemes</em>.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010401, uuid2 = 0x0000, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "DescriptiveFramework",
		  description = "The DescriptiveFramework class specifies descriptive metadata.",
		  symbol = "DescriptiveFramework",
		  isConcrete = false)
public class DescriptiveFrameworkImpl
	extends 
		InterchangeObjectImpl
	implements 
		DescriptiveFramework,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 685052129244179561L;

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0c00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C},
			definedName = "LinkedDescriptiveFrameworkPluginID",
			aliases = { "LinkedDescriptiveFrameworkPlug-InID" },
			typeName = "AUID",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "LinkedDescriptiveFrameworkPluginID",
			description = "Descriptive marker that strongly references this descriptive framework instance.")
	public AUID getLinkedDescriptiveFrameworkPluginID()
		throws PropertyNotPresentException {
		
		// TODO
		return null;
	}

	@MediaPropertySetter("LinkedDescriptiveFrameworkPluginID")
	public void setLinkedDescriptiveFrameworkPluginID(
			AUID linkedDescriptiveFrameworkPluginID) {
		
		// TODO
	}
	
	/** 
	 * <p>All descriptive frameworks must be cloneable to allow them to be used as strong references within
	 * {@link DescriptiveMarkerImpl descriptive markers}.</p>
	 * 
	 * @see java.lang.Object#clone()
	 */
	public DescriptiveFramework clone() {

		return (DescriptiveFramework) super.clone();

	}
}
