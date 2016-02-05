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
 * $Log: AAFString.java,v $
 * Revision 1.7  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/01/18 16:18:52  vizigoth
 * Minor comment improvement.
 *
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
 * <p>Labels a string of <a href="http://www.unicode.org/">Unicode</a> characters. 
 * Java strings and AAF strings both use 2-byte Unicode characters. Although the AAF name for
 * this type is <code>String</code>, the annotation <code>AAFString</code> is used here
 * to avoid confusion with the core Java class {@link java.lang.String}.</p>
 * 
 * <p>
 * Original C type name: <code>aafString_t</code><br>
 * C base type: <code>aafCharacter *</code><br>
 * Java base type: {@link java.lang.String}
 * </p>
 * 
 * @see tv.amwa.maj.meta.TypeDefinitionString
 * @see tv.amwa.maj.industry.TypeDefinitions#UTF16String
 * 
 *
*/
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface AAFString { }

