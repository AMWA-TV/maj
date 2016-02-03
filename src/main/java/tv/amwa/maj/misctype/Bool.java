/* 
 **********************************************************************
 *
 * $Id: Bool.java,v 1.4 2011/02/14 22:33:03 vizigoth Exp $
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
 * $Log: Bool.java,v $
 * Revision 1.4  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2007/12/12 12:52:27  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.2  2007/11/23 15:04:16  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:08:05  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.misctype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** 
 * <p>Label for a Java boolean value used to represent an AAF Boolean value. Java and
 * AAF both define a boolean to have values of either <code>true</code> or <code>false</code>.
 * The C-based AAF SDK uses an enumeration to represent boolean values,
 * based on <code>aafInt32</code>, with:</p>
 * 
 * <ul>
 *  <li><code>false</code> represented by <code>0</code> and <code>kAAFFalse</code>;</li>
 *  <li><code>true</code> represented by <code>1</code> and <code>kAAFTrue</code>.</li>
 * </ul>
 * 
 * <p>
 * Original C name: <code>aafBool</code><br>
 * C base type: enumeration <code>aafBoolean_t</code><br>
 * Java base type: <code>boolean</code>
 * </p>
 * 
 * @see tv.amwa.maj.enumeration.Boolean
 * @see tv.amwa.maj.industry.TypeDefinitions#Boolean
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface Bool { }

