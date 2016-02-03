/* 
 **********************************************************************
 *
 * $Id: AUIDArray.java,v 1.5 2009/05/14 16:15:35 vizigoth Exp $
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
 * $Log: AUIDArray.java,v $
 * Revision 1.5  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2007/12/12 12:52:27  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.2  2007/12/03 12:39:26  vizigoth
 * Minor comment updates.
 *
 * Revision 1.1  2007/12/03 11:58:20  vizigoth
 * Added new labels to complete coverage of built-in types in the AAF specification.
 *
 */

package tv.amwa.maj.misctype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** 
 * <p>Labels an array of 16-byte unique identifiers. 
 * This annotation is used to show that a Java {@link tv.amwa.maj.record.AUID AUID}<code>[]</code> is 
 * being used to represent an AAF <code>AUIDArray</code> type value.</p>
 * 
 * @see tv.amwa.maj.record.AUID
 * @see tv.amwa.maj.industry.TypeDefinitions#AUIDArray
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
*/
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface AUIDArray { }
