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
 * $Log: UInt8.java,v $
 * Revision 1.6  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
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
 * <p>Labels an unsigned 8-bit integer value. This annotation is used
 * to show that a Java signed <code>byte</code> value is being used to represent an 
 * AAF unsigned <code>UInt8</code> type value. See the <a href="package-summary.html#AAFIntegersInJava">
 * discussion on the representation of AAF integer types in Java</a> for issues involved in managing 
 * unsigned AAF integer values in Java.</p>
 * 
 * <p>
 * Original C type name: <code>aafUInt8</code><br>
 * C base type: <code>unsigned char</code><br>
 * Java base type: <code>byte</code>
 * </p>
 * 
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#UInt8
 * @see tv.amwa.maj.industry.TypeDefinitions#UInt8Array
 * @see tv.amwa.maj.industry.TypeDefinitions#UInt8Array8
 * @see tv.amwa.maj.industry.TypeDefinitions#UInt8Array12
 * @see UInt8Array
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface UInt8 { }

