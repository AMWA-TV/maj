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
 * $Log: CodecDescription.java,v $
 * Revision 1.4  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/12/12 12:29:54  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:44  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Labels an {@linkplain tv.amwa.maj.record.AUID AUID} that is a unique identifier for a codec. 
 * The additional metadata provided by this annotation can be used in combination with the identifier
 * to create a {@linkplain tv.amwa.maj.model.CodecDefinition codec definition}.</p>
 * 
 * <p>See the <a href="package-summary.html#managingDefinitions">description of managing definitions</a> 
 * in the package summary for more details of how to use these constants and dynamically extend the range 
 * of supported codecs.</p>
 * 
 * @see CodecConstant
 * 
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CodecDescription {
	
	/**
	 * <p>A brief description of the codec.</p>
	 */
	String description() default "";
	
	/**
	 * <p>A list of alternative names that can be used as aliases to describe the codec.</p>
	 */
	String[] aliases() default { };
	
	/**
	 * <p>Name of the kind of {@linkplain tv.amwa.maj.model.AAFFileDescriptor file descriptor} that 
	 * identifies the kind of essence the codec processes. The name should correspond to the name
	 * of a {@linkplain tv.amwa.maj.meta.ClassDefinition class definition}.</p>
	 * 
	 * @see tv.amwa.maj.meta.ClassDefinition
	 * @see tv.amwa.maj.model.AAFFileDescriptor
	 * @see tv.amwa.maj.model.WAVEPCMDescriptor
	 */
	String fileDescriptorClass();
	
	/**
	 * <p>List of the names of {@linkplain DataDefinitionConstant data definitions} of the essence 
	 * formats that the codec processes.</p>
	 * 
	 * @see DataDefinitionConstant
	 */
	String[] dataDefinitions();
}
