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
 * $Log: LengthType.java,v $
 * Revision 1.8  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.7  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/01/14 20:53:12  vizigoth
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
 */

package tv.amwa.maj.misctype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** 
 * <p>Labels a Java <code>long</code> value used to represent the length of a 
 * {@linkplain tv.amwa.maj.model.Component component}. The length is measured in the
 * edit units of the component.</p>
 * 
 * <p>
 * Original C name: <code>aafLength_t</code><br>
 * C base type: <code>aafInt64</code><br>
 * Java base type: <code>long</code>
 * </p>
 * 
 * @see tv.amwa.maj.model.Component#getComponentLength()
 * @see tv.amwa.maj.industry.Forge#calculateDuration(tv.amwa.maj.record.TimecodeValue, tv.amwa.maj.record.TimecodeValue)
 * @see tv.amwa.maj.industry.TypeDefinitions#LengthType
 * 
 *
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface LengthType { }

