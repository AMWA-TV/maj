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
 * $Log: UInt32.java,v $
 * Revision 1.8  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.7  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
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
 *
 */

package tv.amwa.maj.integer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** 
 * <p>Labels an unsigned 32-bit integer value. This annotation is used
 * to show that a Java signed <code>int</code> value is being used to represent an 
 * AAF unsigned <code>UInt32</code> type value. See the <a href="package-summary.html#AAFIntegersInJava">
 * discussion on the representation of AAF integer types in Java</a> for issues involved in managing 
 * unsigned AAF integer values in Java.</p>
 * 
 * <p>
 * Original C type name: <code>aafUInt32</code><br>
 * C base type: <code>unsigned int</code><br>
 * Java base type: <code>int</code>
 * </p>
 * 
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#UInt32
 * @see tv.amwa.maj.industry.TypeDefinitions#UInt32Array
 * 
 * @see UInt32Array
*/
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface UInt32 { }

