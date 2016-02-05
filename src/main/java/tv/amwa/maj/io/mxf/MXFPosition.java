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
 * $Log: MXFPosition.java,v $
 * Revision 1.3  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/01/19 14:44:23  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
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
import tv.amwa.maj.io.mxf.impl.MXFFileImpl;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.model.Component;

/**
 * <p>Labels a value that represents a position in an MXF file. Values are 
 * {@linkplain Int64 64&nbsp;bit signed integers} represented as Java long values.</p>
 * 
 * <p>Note that this is different from a {@link PositionType} which is a value that
 * represents the offset into an {@linkplain Component AAF component} measured in edit units 
 * rather than a position in a file measured in bytes.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.integer.Int64
 * @see tv.amwa.maj.misctype.PositionType
 * @see MXFLength
 * @see MXFFileImpl#tell()
 *
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface MXFPosition {

}
