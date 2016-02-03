/* 
 **********************************************************************
 *
 * $Id: TypeDefinitionObjectReference.java,v 1.8 2011/01/13 17:44:26 vizigoth Exp $
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
 * $Log: TypeDefinitionObjectReference.java,v $
 * Revision 1.8  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.7  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:40:06  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:24  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.industry.PropertyValue;

/**
 * <p>Specifies the definition of a property type that defines an object 
 * relationship.</p>
 * 
 * <p>THE COMMENTS FOR THIS CLASS ARE INCOMPLETE.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see TypeDefinitionStrongObjectReference
 * @see TypeDefinitionWeakObjectReference
 *
 */

public abstract interface TypeDefinitionObjectReference 
	extends TypeDefinition {

	/**
	 * <p>Returns the {@linkplain ClassDefinition class definition} representing 
	 * the class of objects to which this object reference type definition can 
	 * refer. The returned definition is the least-derived type that it
	 * is permissible to represent, in other words the type definition can
	 * represent objects of the given class and any of its sub-classes.</p>
	 * 
	 * @return Class definition representing the class of objects to which 
	 * property values of the object reference type can refer.
	 */
	public ClassDefinition getObjectType();

	/**
	 * <p>Creates and returns a new object reference property value
	 * to reference the given object.</p>
	 * 
	 * @param object Object to use to create an object reference
	 * property value.
	 * @return Newly created object reference property value that
	 * references the given object.
	 * 
	 * @throws NullPointerException The given object is <code>null</code>.
	 * @throws ClassCastException The given object cannot be cast 
	 * to one compatible with the permissible classes, as returned
	 * by {@link #getObjectType()}.
	 * 
	 * @see #setObject(PropertyValue, Object)
	 */
	public PropertyValue createValue(
			Object object) 
		throws NullPointerException,
			ClassCastException;

	/**
	 * <p>Returns the object referenced by the given object
	 * reference property value.</p>
	 * 
	 * @param propertyValue Property value form which the value is to
	 * be read.
	 * 
	 * @throws NullPointerException The given property value containing
	 * a reference is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is
	 * not defined by this object reference type.
	 */
	public Object getObject(
			PropertyValue propertyValue) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Sets the given object as the target of the given object 
	 * reference property value.</p>
	 *  
	 * @param propertyValue Property value containing the object
	 * reference to be set.
	 * @param object Target of the object reference to set for the
	 * given property value.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws IllegalPropertyValueException The given property value is
	 * not defined by this object reference type.
	 * @throws ClassCastException The given object cannot be cast 
	 * to one compatible with the permissible classes, as returned
	 * by {@link #getObjectType()}.
	 * 
	 * @see #createValue(Object)
	 */
	public void setObject(
			PropertyValue propertyValue,
			Object object) 
		throws NullPointerException,
			IllegalPropertyValueException,
			ClassCastException;
	
	/**
	 * <p>Create a cloned copy of this object reference type definition.</p>
	 *
	 * @return Cloned copy of this object reference type definition.
	 */
	public TypeDefinitionObjectReference clone();
}
