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
 * $Log: RGBALayout.java,v $
 * Revision 1.5  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2008/02/08 12:57:45  vizigoth
 * Consistent referal to zero rather than null/nil mob id and isOriginal/Contextual methods added to the interface.
 *
 * Revision 1.3  2007/12/12 12:52:27  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.2  2007/12/03 12:39:22  vizigoth
 * Minor comment updates.
 *
 * Revision 1.1  2007/12/03 11:58:17  vizigoth
 * Added new labels to complete coverage of built-in types in the AAF specification.
 *
 */

package tv.amwa.maj.misctype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** 
 * <p>Labels an array of {@link tv.amwa.maj.record.RGBAComponent} values that represent 
 * the order and size of the components specifying the value of a pixel.</p>
 * 
 * @see CompArray
 * @see tv.amwa.maj.model.RGBADescriptor
 * 
 *
*/
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface RGBALayout { }
