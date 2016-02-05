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
 * $Log: TypeDefinitionStrongObjectReference.java,v $
 * Revision 1.8  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.7  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:30  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import tv.amwa.maj.model.NetworkLocator;


/**
 * <p>Specifies the definition of a property type that defines an object relationship 
 * where the target of the strong reference is owned by the object with the property with the
 * strong object reference type. An object can be the target of only one strong reference within
 * an AAF file.</p>
 * 
 * <p>This property type is known as a <em>strong reference</em>. For example, a reference
 * to a {@linkplain NetworkLocator network locator} of this type is called a "StrongReference
 * to NetworkLoctor" in the AAF object specification and may also be represented as
 * "StrongReference&lt;NetworkLocator&gt;". Both these styles of type name can be used
 * to return a strong reference type definition from the factory function 
 * {@link tv.amwa.maj.industry.Warehouse#lookForType(String)}.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.enumeration.TypeCategory#StrongObjRef
 * @see TypeDefinitionWeakObjectReference
 */
public interface TypeDefinitionStrongObjectReference 
	extends TypeDefinitionObjectReference {

	/**
	 * <p>Create a cloned copy of this strong object reference type definition.</p>
	 *
	 * @return Cloned copy of this strong object reference type definition.
	 */
	public TypeDefinitionStrongObjectReference clone();
}
