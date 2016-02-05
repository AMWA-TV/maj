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
 * $Log: FrameOffset.java,v $
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2007/12/12 12:52:27  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.2  2007/11/23 15:04:16  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:08:03  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.misctype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** 
 * <p>Labels a Java <code>long</code> value as an offset into time-varying essence, 
 * measured as an offset and counted in frames. The start frame of 
 * {@linkplain tv.amwa.maj.record.EdgeCodeValue edgecode} and 
 * {@linkplain tv.amwa.maj.record.TimecodeValue timecode} values are measured as 
 * frame offsets.</p>
 * 
 * <p>
 * Original C name: <code>aafFrameOffset_t</code><br>
 * C base type: <code>aafInt64</code><br>
 * Java base type: <code>long</code>
 * </p>
 * 
 * @see tv.amwa.maj.record.EdgeCodeValue
 * @see tv.amwa.maj.record.TimecodeValue
 * 
 *
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface FrameOffset { }

