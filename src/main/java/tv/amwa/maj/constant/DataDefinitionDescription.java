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
 * $Log: DataDefinitionDescription.java,v $
 * Revision 1.6  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.5  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2008/02/08 11:28:05  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2007/12/12 12:29:55  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:45  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * <p>Labels an {@linkplain tv.amwa.maj.record.AUID AUID} that is a unique identifier for a data definition
 * that specifies the kind of data that can be stored in a {@linkplain tv.amwa.maj.model.Component component}. 
 * The additional metadata provided by this annotation can be used in combination with the identifier
 * to create a {@linkplain tv.amwa.maj.model.DataDefinition data definition}.</p>
 * 
 * <p>See the <a href="package-summary.html#managingDefinitions">description of managing definitions</a> 
 * in the package summary for more details of how to use these constants and dynamically extend the range 
 * of supported data definitions.</p>
 * 
 * @see DataDefinitionConstant
 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, tv.amwa.maj.record.AUID)
 * 
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataDefinitionDescription {

	/**
	 * <p>A brief description of the kind of data specified.</p>
	 */
	String description() default "";
	
	/**
	 * <p>A list of alternative names that can be used as aliases for the kind of data specified.</p>
	 */
	String[] aliases() default { };
}
