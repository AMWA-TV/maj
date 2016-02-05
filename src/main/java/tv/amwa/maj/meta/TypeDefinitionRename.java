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
 * $Log: TypeDefinitionRename.java,v $
 * Revision 1.9  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:40:07  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.industry.PropertyValue;


/**
 * <p>Specifies the definition a property type that has the same structure and representation 
 * as its underlying type but has a different meaning.</p>
 * 
 * <p>An example of a rename type is the {@linkplain tv.amwa.maj.industry.TypeDefinitions#PositionType
 * position type}, which is used as another name for a 64-bit signed integer value 
 * ({@link tv.amwa.maj.integer.Int64 Int64}).</p>
 * 
 *
 *
 * @see tv.amwa.maj.enumeration.TypeCategory#Rename
 */

public interface TypeDefinitionRename
	extends TypeDefinition {

	/**
	 * <p>Returns the type definition of the base type for which
	 * this rename type definition is an alias. The base type is also 
	 * known as the renamed type.</p>
	 * 
	 * @return Base type for this rename type definition.
	 */
	public TypeDefinition getBaseType();

	/**
	 * <p>Returns a property value of the base type, unwrapping the the given 
	 * property value that is defined by this rename type definition.</p>
	 * 
	 * <p>Note that the value returned may contain a reference to the
	 * same internal value as the given property value and this data is not
	 * necessarily a new copy.</p>
	 * 
	 * @param propertyValue Property value defined by the rename type 
	 * definition.
	 * @return Equivalent property value defined by the {@linkplain #getBaseType()
	 * base type} definition.
	 * 
	 * @throws NullPointerException The given property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not 
	 * compatible with this rename type definition.
	 * 
	 * @see #getBaseType()
	 */
	public PropertyValue getBaseValue(
			PropertyValue propertyValue) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Returns a property value defined by this rename type definition 
	 * containing, wrapping the given property value. The given 
	 * property value shall be of the same type as the underlying base 
	 * type of the rename type definition, as returned by 
	 * {@link #getBaseType()}.</p> 
	 * 
	 * <p>Note that the value returned may contain a reference to the
	 * same internal value as the given property value and this data is not
	 * necessarily a new copy.</p>
	 * 
	 * @param propertyValue Property value of the base type to create a
	 * new property value of this rename type from.
	 * @return New property value created from the given property value
	 * of this type.
	 * 
	 * @throws NullPointerException The given property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not compatible
	 * with the base type of this rename type definition.
	 */
	public PropertyValue createValueFromBaseValue(
			PropertyValue propertyValue) 
		throws NullPointerException, 
			IllegalPropertyValueException;
	
	/**
	 * <p>Create a cloned copy of this rename type definition.</p>
	 *
	 * @return Cloned copy of this rename type definition.
	 */
	public TypeDefinitionRename clone();
}
