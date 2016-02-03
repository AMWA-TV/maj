/* 
 **********************************************************************
 *
 * $Id: MediaPropertyCount.java,v 1.3 2011/02/14 22:32:49 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: MediaPropertyCount.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
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
* <p>Labels a method as one used to count the number of elements in AAF-type {@linkplain TypeDefinitionVariableArray variable
* length arrays} or {@linkplain tv.amwa.maj.meta.TypeDefinitionSet sets} of the given {@linkplain PropertyDefinition property} 
* name. The labelled method should have a signature similar to:</p>
* 
* <p><code>&nbsp;&nbsp;&nbsp;&nbsp;public&nbsp;int&nbsp;count</code>&lt;<em>propertyName</em>&gt;<code>()</code></p>
* 
* @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
* 
* @see TypeDefinitionVariableArray
* @see tv.amwa.maj.meta.TypeDefinitionSet
* @see tv.amwa.maj.model.Preface#countDescriptiveSchemes()
* @see tv.amwa.maj.model.BWFImportDescriptor#countUnknownBWFChunks()
*/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MediaPropertyCount {

	/** Property name for the array or set to be counted. */
	String value();
}
