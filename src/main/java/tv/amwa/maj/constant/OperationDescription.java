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
 * $Log: OperationDescription.java,v $
 * Revision 1.5  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.4  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2007/12/12 12:29:53  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:37  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * <p>Labels an {@linkplain tv.amwa.maj.record.AUID AUID} that is a unique identifier for 
 * an {@linkplain tv.amwa.maj.model.OperationDefinition operation}. The additional metadata provided by this
 * annotation can be used in combination with the identifier to create an
 * {@linkplain tv.amwa.maj.model.OperationDefinition operation definition}. </p>
 * 
 * <p>See the <a href="package-summary.html#managingDefinitions">description of managing definitions</a> 
 * in the package summary for more details of how to use these constants and dynamically extend the range 
 * of supported operations.</p>
 * 
 * @see OperationConstant
 * @see tv.amwa.maj.model.OperationDefinition
 * 
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OperationDescription {

	/**
	 * <p>A brief description of the operation.</p>
	 */
	String description() default "";
	
	/**
	 * <p>A list of alternative names that can be used as aliases for the operation.</p>
	 */
	String[] aliases() default { };
	
	/**
	 * <p>Name of the {@linkplain tv.amwa.maj.model.DataDefinition data definition} for the 
	 * kind of data that is produced by the operation.</p>
	 * 
	 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
	 */
	String dataDefinition();
	
	/**
	 * <p>Name of the {@linkplain OperationCategoryType operation category type} that specifies
	 * the kind of operation.</p> 
	 */
	String operationCategory() default "";
	
	/**
	 * <p>Specifies the number of input segments to this operation. A value of -1 indicates that 
	 * the effect can have any number of input segments.</p>
	 */
	int numberInputs();
	
	/**
	 * <p>Specifies whether the output of the operation differs from the length of the input segment(s).</p>
	 */
	boolean isTimeWarp() default false;
	
	/**
	 * <p>Provides a list of operation names for operations that an application can substitute for the
	 * defined operation if it cannot process it.</p>
	 */
	String[] degradeTo() default { };
	
	/**
	 * <p>Specifies the array index (1-based) of the input segment which is the primary input. Set to
	 * {@link Integer#MIN_VALUE} to indicate that the bypass segment optional property is not present.</p>
	 */
	int bypass() default Integer.MIN_VALUE;
	
	/**
	 * <p>Specifies a list of names of {@linkplain tv.amwa.maj.model.ParameterDefinition parameters} 
	 * for the operation.</p>
	 */
	String[] parametersDefined() default { };
}
