/* 
 **********************************************************************
 *
 * $Id: MXFLength.java,v 1.2 2011/01/04 10:43:58 vizigoth Exp $
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
 * Matt Beard, Metaglue Corporation
 *
 **********************************************************************
 */

/*
 * $Log: MXFLength.java,v $
 * Revision 1.2  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/02/13 14:27:29  vizigoth
 * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
 *
 */

package tv.amwa.maj.io.mxf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.model.Component;

/**
 * <p>Labels a value that represents the length of an item in an MXF file, measured in
 * bytes. Values are {@linkplain Int64 64&nbsp;bit signed integers} represented as 
 * Java long values.</p>
 * 
 * <p>Note that this is different from a {@link LengthType} which is a value that
 * represents the length of an {@linkplain Component AAF component} measured in edit
 * units rather than the number of bytes taken up by an item in an AAF file.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see tv.amwa.maj.integer.Int64
 * @see tv.amwa.maj.misctype.LengthType
 * @see MXFPosition
 *
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface MXFLength {

}