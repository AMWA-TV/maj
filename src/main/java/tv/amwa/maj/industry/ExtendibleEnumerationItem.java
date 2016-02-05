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
 * $Log: ExtendibleEnumerationItem.java,v $
 * Revision 1.3  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.2  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2010/03/02 17:29:31  vizigoth
 * Part of the MAJ industry, so moved to the appropriate package.
 *
 * Revision 1.4  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/01/08 17:01:52  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.2  2007/12/12 12:50:52  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.1  2007/11/13 22:14:15  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.industry;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Labels an {@linkplain tv.amwa.maj.record.AUID AUID} that represents an element
 * of an {@linkplain tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration extendible
 * enumeration}. These annotations are used by the {@linkplain tv.amwa.maj.industry.Warehouse
 * warehouse} to manage a dynamic inventory of extendible enumerations at runtime.</p>
 * 
 * <p>Using this annotation, it is possible for any AUID to become an element of an existing of new
 * extendible enumeration at runtime. As long as the AUID is in the current classpath, add a set of elements
 * from a class with {@link tv.amwa.maj.industry.Warehouse#registerExtendibleEnumerationElements(Class)}. The
 * element value should be annotated as shown for the <em>color primaries</em> element shown below:</p>
 * 
 * <pre>
 *     &#64;ExtendibleEnumerationItem(target = "ColorPrimaries")
 *     public final static AUID SMPTE170M = Forge.makeAUID(
 *             0x04010101, (short) 0x0301, (short) 0x0000,
 *             new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x06 });
 * </pre>
 * 
 * <p>Note that the new elements are registered within the local Java virtual machine only.</p>
 * 
 * @see tv.amwa.maj.industry.Warehouse#registerExtendibleEnumerationElements(Class)
 * @see tv.amwa.maj.industry.Warehouse#lookupExtendibleEnumeration(String)
 * @see tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration
 * @see tv.amwa.maj.constant.ColorPrimariesType
 *
 *
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExtendibleEnumerationItem {

	/**
	 * <p>Name of the extendible enumeration that this labelled constant is an element for.
	 * When used to create an element of an extendible enumeration, the name of the labelled
	 * AUID constant is used as the name of the element.</p>
	 * 
	 * @see tv.amwa.maj.industry.Warehouse#registerExtendibleEnumerationElements(Class)
	 */
	String target();
}
