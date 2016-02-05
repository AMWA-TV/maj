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
 * $Log: TypeDefinitionCharacter.java,v $
 * Revision 1.9  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:40:07  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:18  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.industry.PropertyValue;

/**
 * <p>Specifies the definition of a property type that has a value of a single 2-byte character.
 * The AAF character type is equivalent to the Java <code>char</code> primitive type and a value
 * of {@link java.lang.Character}.</p>
 * 
 *
 *
 * @see tv.amwa.maj.enumeration.TypeCategory#Character 
 * @see java.lang.Character
 */

public interface TypeDefinitionCharacter 
	extends TypeDefinition {

	/**
	 * <p>Create a new {@linkplain PropertyValue property value} of character type from the given
	 * single character.</p>
	 * 
	 * @param character Character to use to create a new property value.
	 * 
	 * @return New property value with a character value matching the given
	 * character.
	 * 
	 * @see #setCharacter(PropertyValue, char)
	 */
	public PropertyValue createValueFromCharacter(
			char character);

	/**
	 * <p>Returns the character that is the value of the given character
	 * {@linkplain PropertyValue property value}.</p>
	 * 
	 * @param characterProperty Property value containing the character to
	 * return.
	 * @return Value of the given property value.
	 * 
	 * @throws NullPointerException The given property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not a 
	 * character property value.
	 * 
	 * @see PropertyValue#getType()
	 */
	public char getCharacter(
			PropertyValue characterProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Sets the given character as the value of the given character
	 * {@linkplain PropertyValue property value}.</p>
	 * 
	 * @param characterProperty Property value of character type to set
	 * the character for.
	 * @param character Character to set as the value of the given property
	 * value.
	 * 
	 * @throws NullPointerException The given character property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not a 
	 * a character property value.
	 * 
	 * @see #createValueFromCharacter(char)
	 */
	public void setCharacter(
			PropertyValue characterProperty,
			char character) 
		throws NullPointerException,
			IllegalPropertyValueException;
	
	/**
	 * <p>Create a cloned copy of this character type definition.</p>
	 *
	 * @return Cloned copy of this character type definition.
	 */
	public TypeDefinitionCharacter clone();
}
