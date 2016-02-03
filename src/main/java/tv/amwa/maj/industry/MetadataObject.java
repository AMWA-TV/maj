/* 
 **********************************************************************
 *
 * $Id: MetadataObject.java,v 1.4 2011/01/13 17:44:26 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
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
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 * @see Forge#make(Class, Object...)
 * @see MediaClass
 */
public interface MetadataObject {

}
