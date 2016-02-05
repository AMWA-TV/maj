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
 * $Log: MediaListGetAt.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 */

package tv.amwa.maj.industry;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;

/**
* <p>Labels a method as one used to get an element at an index into an {@linkplain TypeDefinitionVariableArray AAF-type variable
* length array} of the given {@linkplain PropertyDefinition property} name. The labelled method should
* have a signature similar to:</p>
* 
* <p><code>&nbsp;&nbsp;&nbsp;&nbsp;public&nbsp;</code>&lt;<em>arrayElementType</em>&gt;<code>&nbsp;get</code>&lt;<em>propertyName</em>&gt;<code>At(</code><br>
* <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;int&nbsp;index)</code></p>
* 
*
* 
* @see TypeDefinitionVariableArray
* @see tv.amwa.maj.model.BWFImportDescriptor#getUnknownBWFChunkAt(int)
*/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MediaListGetAt {

	/** Property name this method retrieves elements for. */
	String value();
}
