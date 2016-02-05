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
 * $Log: UInt64.java,v $
 * Revision 1.5  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2007/12/12 12:51:44  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.3  2007/11/23 15:07:16  vizigoth
 * Minor documentation edits.
 *
 * Revision 1.2  2007/11/22 12:38:01  vizigoth
 * Edited javadoc comments to release standard.
 *
 */

package tv.amwa.maj.integer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** 
 * <p>Labels an unsigned 64-bit integer value. This annotation is used
 * to show that a Java signed <code>long</code> value is being used to represent an 
 * AAF unsigned <code>UInt64</code> type value. See the <a href="package-summary.html#AAFIntegersInJava">
 * discussion on the representation of AAF integer types in Java</a> for issues involved in managing 
 * unsigned AAF integer values in Java.</p>
 * 
 * <p>
 * Original C type name: <code>aafUInt64</code><br>
 * C base type: <code>unsigned hyper</code><br>
 * Java base type: <code>long</code>
 * </p>
 * 
 * @see UInt64Array
 * @see tv.amwa.maj.industry.TypeDefinitions#UInt64
 * 
 *
*/
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface UInt64 { }

