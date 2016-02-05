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
 * $Log: OperationalPatternDescription.java,v $
 * Revision 1.2  2008/01/23 14:22:47  vizigoth
 * Fixed name of OperationalProtocolConstant to OperationalPatternConstant.
 *
 * Revision 1.1  2008/01/23 14:20:49  vizigoth
 * Added operational pattern constants and descriptions.
 *
 */

package tv.amwa.maj.constant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Labels an {@linkplain tv.amwa.maj.record.AUID AUID} that is a unqiue identifier for an
 * operational pattern. An operational pattern constraints levels of file complexity.</p>
 * 
 *
 * 
 * @see OperationalPatternConstant
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OperationalPatternDescription {

	/**
	 * <p>A brief description of the operational pattern.</p>
	 */
	String description() default "";
	
	/**
	 * <p>A list of alternative names that can be used as aliases to describe the operational pattern.</p>
	 */
	String[] aliases() default { };
	

}
