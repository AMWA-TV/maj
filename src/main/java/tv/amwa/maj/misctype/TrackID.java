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
 * $Log: TrackID.java,v $
 * Revision 1.2  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/03/30 09:04:51  vizigoth
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
 */

package tv.amwa.maj.misctype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


/** <p>Labels an integer (Java <code>int</code>) value used to uniquely identify a 
 * {@linkplain tv.amwa.maj.model.Track track} within a {@linkplain tv.amwa.maj.model.Package 
 * package}. This identifier is known as a <em>track id</em>.</p>
 * 
 * <p>
 * Original C name: <code>aafSlotID_t</code><br>
 * C base type: <code>aafUInt32</code><br>
 * Java base type: <code>int</code>
 * </p>
 * 
 * @see tv.amwa.maj.model.Track
 * @see tv.amwa.maj.model.Package
 * @see tv.amwa.maj.union.SourceReferenceValue
 * 
 *
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface TrackID { }

