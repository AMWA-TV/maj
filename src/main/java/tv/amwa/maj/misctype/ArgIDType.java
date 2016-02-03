/* 
 **********************************************************************
 *
 * $Id: ArgIDType.java,v 1.5 2011/01/04 10:42:32 vizigoth Exp $
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
 * $Log: ArgIDType.java,v $
 * Revision 1.5  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2007/12/12 12:52:27  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.3  2007/12/03 11:58:19  vizigoth
 * Added new labels to complete coverage of built-in types in the AAF specification.
 *
 * Revision 1.2  2007/11/23 15:04:16  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:07:58  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.misctype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

// TODO check the documentation link

/** 
 * <p>Labels an {@link tv.amwa.maj.record.AUID AUID} that defines an operation parameter. Possible
 * values for operation parameters are defined in interface {@link tv.amwa.maj.constant.ParameterConstant 
 * ParameterConstant} and can be used as a parameter to method
 * {@link tv.amwa.maj.model.OperationGroup#lookupParameter(tv.amwa.maj.record.AUID)}.</p>
 * 
 * <p>
 * Original C name: <code>aafArgIDType_t</code><br>
 * C base type: <code>_aafUID_t</code><br>
 * Java base type: {@link tv.amwa.maj.record.AUID}
 * </p>
 * 
 * @see tv.amwa.maj.model.OperationGroup
 * @see tv.amwa.maj.constant.ParameterConstant
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
*/
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface ArgIDType { }

