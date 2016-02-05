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
 * $Log: HiddenClass.java,v $
 * Revision 1.3  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.2  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2010/03/19 09:50:37  vizigoth
 * New annotation for classes that are not SMPTE registered but are useful for Java.
 *
 */

package tv.amwa.maj.industry;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tv.amwa.maj.meta.TypeDefinitionObjectReference;

/**
 * <p>Labels a class introduced into a Java class hierarchy for convenience, between {@linkplain MediaClass media
 * classes}, but not part of a registered hierarchy. Such a class contains shared methods for its children but
 * does not have any {@linkplain MediaProperty media properties} exposed.</p>
 * 
 *
 * 
 * @see TypeDefinitionObjectReference
 * @see Warehouse#lookForClass(Class)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HiddenClass {

}
