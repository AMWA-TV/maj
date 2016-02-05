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
 * $Log: UInt8Array.java,v $
 * Revision 1.5  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/12/12 12:51:44  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.1  2007/12/03 12:38:07  vizigoth
 * Added new labels to complete coverage of built-in types in the AAF specification.
 *
 *
 */

package tv.amwa.maj.integer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** 
 * <p>Labels an array of 8-bit unsigned integer values. This annotation is used
 * to show that a Java <code>byte[]</code> is being used to represent an AAF 
 * <code>UInt8Array</code> type value.</p>
 * 
 * @see UInt8
 * @see tv.amwa.maj.industry.TypeDefinitions#UInt8Array
 * @see tv.amwa.maj.industry.TypeDefinitions#UInt8Array8
 * @see tv.amwa.maj.industry.TypeDefinitions#UInt8Array12
 * @see tv.amwa.maj.industry.TypeDefinitions#DataValue
 * 
 *
*/
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface UInt8Array { }
