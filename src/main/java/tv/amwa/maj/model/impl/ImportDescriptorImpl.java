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
 * $Log: ImportDescriptorImpl.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
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
 * Revision 1.1  2007/11/13 22:09:25  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.model.ImportDescriptor;


/** 
 * <p>Implements the description of a file essence source that is not directly manipulated 
 * by an AAF application.</p>
 * 
 * <p>Typically, an import {@linkplain tv.amwa.maj.model.SourcePackage source package} is the source of a file 
 * {@linkplain tv.amwa.maj.model.SourcePackage source package}, where an application had imported a file by 
 * application-specific means.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x4a00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "ImportDescriptor",
		  description = "The ImportDescriptor class describes a file essence source that is not directly manipulated by an AAF application.",
		  symbol = "ImportDescriptor")
public class ImportDescriptorImpl
	extends
		PhysicalDescriptorImpl
	implements 
		ImportDescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -3495776601175521397L;
	
	/**
	 * <p>Creates and initializes a new import descriptor, which represents a file essence source 
	 * that is not directly manipulated by an AAF application.</p>
	 */
	public ImportDescriptorImpl() { }

	public ImportDescriptor clone() {
		
		return (ImportDescriptor) super.clone();
	}
}
