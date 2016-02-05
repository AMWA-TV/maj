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
 *
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface Bool { }

