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
 * $Log: MetadataObject.java,v $
 * Revision 1.4  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.3  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/02/06 17:03:20  vizigoth
 * Added new super-interface "MetadataObject".
 *
 * 
 */

package tv.amwa.maj.industry;

import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.model.InterchangeObject;

/**
 * <p>Specifies any object holding metadata that could be defined by the SMPTE registration
 * authority and serialised to an AAF file, MXF file etc.. Any class that implements this
 * interface should also be annotated as a {@linkplain MediaClass media class}.</p>
 * 
 * <p>In the MAJ API implementation of the AAF classes, all {@linkplain InterchangeObject interchange 
 * objects} and {@linkplain MetaDefinition meta definitions} are metadata objects.</p>
 * 
 *
 *
 * @see Forge#make(Class, Object...)
 * @see MediaClass
 */
public interface MetadataObject {

}
