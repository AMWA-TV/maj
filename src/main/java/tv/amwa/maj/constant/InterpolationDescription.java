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
 * $Log: InterpolationDescription.java,v $
 * Revision 1.3  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/12/12 12:29:53  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:38  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/** 
 * <p>Labels an {@linkplain tv.amwa.maj.record.AUID AUID} that is a unique identifier for a kind of interpolation
 * function that specifies the mechanism used to calculate the values produced by a 
 * {@linkplain tv.amwa.maj.model.VaryingValue varying value}. The additional metadata provided by this
 * annotation can be used in combination with the identifier to create a 
 * {@linkplain tv.amwa.maj.model.InterpolationDefinition interpolation definition}.</p>
 * 
 * <p>See the <a href="package-summary.html#managingDefinitions">description of managing definitions</a> 
 * in the package summary for more details of how to use these constants and dynamically extend the range 
 * of supported interpolation definitions.</p>
 * 
 * @see InterpolationConstant
 * 
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InterpolationDescription {

	/**
	 * <p>A brief description of the interpolation function.</p>
	 */
	String description() default "";
	
	/**
	 * <p>A list of alternative names that can be used as aliases for an interpolation function.</p>
	 */
	String[] aliases() default { };	
}
