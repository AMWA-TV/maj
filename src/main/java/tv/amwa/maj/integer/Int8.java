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
 * $Log: Int8.java,v $
 * Revision 1.6  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.5  2007/12/12 12:51:44  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.4  2007/12/03 12:38:06  vizigoth
 * Added new labels to complete coverage of built-in types in the AAF specification.
 *
 * Revision 1.3  2007/11/23 15:07:16  vizigoth
 * Minor documentation edits.
 *
 * Revision 1.2  2007/11/22 12:38:01  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:15:22  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.integer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** <p>Labels an 8-bit 2's complement integer value. This annotation is used
 * to show that a Java <code>byte</code> value is being used to represent an AAF 
 * <code>Int8</code> type value.</p></p>
 * 
 * <p>
 * Original C type name: <code>aafInt8</code><br>
 * C base type: <code>signed char</code><br>
 * Java base type: <code>byte</code>
 * </p>
 * 
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#Int8
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface Int8 { }

