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
 * $Log: CompSizeArray.java,v $
 * Revision 1.3  2007/12/12 12:52:27  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.2  2007/11/23 15:04:16  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:08:04  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.misctype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** 
 * <p>Labels an array in which the elements represent the number of bits reserved 
 * for each component of an array of {@link tv.amwa.maj.record.RGBAComponent 
 * RGBAComponents}. The array is terminated by a 0 byte and has a maximum of 8 
 * elements (including the terminating byte).</p>
 * 
 * <p>Note that this kind of array is used by the JPEG codec plugin that is not
 * supported in this version of the API.</p>
 * 
 * <p>
 * Original C name: <code>aafCompSizeArray_t</code><br>
 * C base type: <code>aafUInt8 *</code><br>
 * Java base type: <code>byte[]</code>
 * </p>
 * 
 *
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface CompSizeArray { }

