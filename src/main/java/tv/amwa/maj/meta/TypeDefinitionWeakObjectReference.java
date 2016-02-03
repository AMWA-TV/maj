/* 
 **********************************************************************
 *
 * $Id: TypeDefinitionWeakObjectReference.java,v 1.8 2011/01/13 17:44:26 vizigoth Exp $
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
 * $Log: TypeDefinitionWeakObjectReference.java,v $
 * Revision 1.8  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.7  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2010/04/16 15:24:29  vizigoth
 * Provide interface access to the target set of the type definition. Required for AAF file writing.
 *
 * Revision 1.4  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:20  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.record.AUID;


/**
 * <p>Specifies the definition of a property type that 
 * defines an object relationship where the target of the weak reference 
 * is referenced by the object with the property with the 
 * weak object reference type. Only objects that define a unique 
 * identification ({@linkplain tv.amwa.maj.record.AUID AUID} or 
 * {@linkplain tv.amwa.maj.record.PackageID PackageID}) can be the targets of weak 
 * object references. In an AAF file, an object can be the target of one or more than one weak 
 * references.</p>
 * 
 * <p>This property type is known as a <em>weak reference</em>. For example, a reference
 * to a {@linkplain DataDefinition data definition} of this type is called a "WeakReference
 * to DataDefinition" in the AAF object specification and may also be represented as
 * "WeakReference&lt;DataDefinition&gt;". Both these styles of type name can be used
 * to return a strong reference type definition from the factory function 
 * {@link tv.amwa.maj.industry.Warehouse#lookForType(String)}.</p>
 * 
 * <p>Note that the <em>target list</em> property of a weak object reference type definition is
 * not exposed through this interface. This property will be maintained
 * automatically by the MAJ API.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see tv.amwa.maj.enumeration.TypeCategory#WeakObjRef
 * @see TypeDefinitionWeakObjectReference
 */

public interface TypeDefinitionWeakObjectReference 
	extends TypeDefinitionObjectReference {

	/**
	 * <p>Create a cloned copy of this weak object reference type definition.</p>
	 *
	 * @return Cloned copy of this weak object reference type definition.
	 */
	public TypeDefinitionWeakObjectReference clone();
	
	/**
	 * <p>Returns the target set of the reference type that provides the path 
	 * from the root of an AAF file to a string referenced set than can be used
	 * to resolve a weak reference of this type.</p>
	 * 
	 * @return Property path for resolving properties of this reference kind. 
	 */
	public AUID[] getTargetSet();

}
