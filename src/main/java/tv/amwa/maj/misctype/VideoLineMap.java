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
 * $Log: VideoLineMap.java,v $
 * Revision 1.5  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2007/12/12 12:52:27  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.2  2007/11/23 15:04:16  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:07:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.misctype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** 
 * <p>Labels an integer array (Java <code>int[]</code>) with 2 elements used to represent a 
 * video line map property. The video line map property is defined for  
 * {@linkplain tv.amwa.maj.model.PictureDescriptor digital image descriptor} objects.</p>
 * 
 * <p>The video line map property is necessary for images that are derived from or will be converted 
 * to video (television) signals. For each field, it describes the mapping, relative to the sampled 
 * view in the digital essence, of the digital image lines to the analogue signal lines.</p>
 *  
 * <p>The video line map specifies the relationship between the scan lines in the analogue signal and 
 * the beginning of the digitized fields. The analogue lines are expressed in scan line numbers that 
 * are appropriate for the signal format. For example, a typical PAL two-field mapping might be 
 * {20,332}, where scan line 20 corresponds to the first line of field 1, and scan line 332 corresponds 
 * to the first line of field 2. Notice that the numbers are based on the whole frame, not on offset 
 * from the top of each field, which would be {20,20}.</p>
 *  
 * <p>A value of 0 is allowed only when computer-generated essence has to be treated differently. If 
 * the digital essence was computer generated (RGB), the values can be either {0,1} (even field first) 
 * or {1,0} (odd field first).</p>
 * 
 * <p>
 * Original C name: <code>aafVideoLineMap_t</code><br>
 * C base type: <code>aafInt32[2]</code><br>
 * Java base type: <code>int[]</code>
 * </p>
 * 
 * @see tv.amwa.maj.model.PictureDescriptor
 * 
 *
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface VideoLineMap { }

