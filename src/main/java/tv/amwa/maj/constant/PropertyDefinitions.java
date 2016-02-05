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
 * $Log: PropertyDefinitions.java,v $
 * Revision 1.5  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/12/06 15:59:28  vizigoth
 * Removed most property IDs to ensure they are managed in one place, locally to their implementing class, from now on. Root property definitions only now.
 *
 * Revision 1.1  2007/11/13 22:12:39  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;
import tv.amwa.maj.industry.MediaProperty;

/** 
 * <p>Implement this interface to access unique identifiers for property definitions not available elsewhere
 * in the MAJ API through the {@link tv.amwa.maj.industry.MediaProperty} annotation. The property constants
 * in this interface are those used to define the root properties of an AAF file. These are used for
 * specifying the starting {@linkplain tv.amwa.maj.meta.TypeDefinitionStrongObjectReference strong reference}
 * in the target list of a {@linkplain tv.amwa.maj.meta.TypeDefinitionWeakObjectReference weak reference}.</p>
 * 
 * <p>Note that this interface used to contain all property definitions. In the interests of easy
 * ongoing maintenance, the details of a property are now only defined within its class definition.
 * This should enable each AAF class to be managed as if a loosely-coupled component.</p>
 *
 *
 *
 */

public interface PropertyDefinitions {

	/**
	 * <p>Special property defining the meta dictionary object of an AAF file.</p>
	 */
	@MediaProperty(uuid1 = 0x0d010301, uuid2 = (short) 0x0101, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MetaDictionary",
			typeName = "StrongReference to MetaDictionary", 
			optional = false,
			uniqueIdentifier = false,
			symbol = "MetaDictionary")
	public final static AUID Root_MetaDictionary = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0101, (short) 0x0100,
			new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02});
 
	/**
	 * <p>Special property defining the preface (header) object of an AAF file.</p>
	 */
	@MediaProperty(uuid1 = 0x0d010301, uuid2 = (short) 0x0102, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Preface",
			typeName = "PrefaceStrongReference",
			optional = false,
			uniqueIdentifier = false,
			symbol = "RootPreface") // TODO confirm this
	public final static AUID Root_Preface = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0102, (short) 0x0100,
			new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02});

	/*
	 * Other property definitions have been removed to promote ongoing management of property
	 * metadata with implementing classes.
	 */
}
