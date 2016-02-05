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
 * $Log: TypeDefinitionCharacterImpl.java,v $
 * Revision 1.5  2011/07/27 17:36:56  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.4  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/26 11:50:11  vizigoth
 * Completed common method testing.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/03/19 16:13:53  vizigoth
 * Added methods for writing bytes and calculating lengths.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.4  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.3  2008/01/14 20:55:19  vizigoth
 * Change to type category enumeration element names.
 *
 * Revision 1.2  2007/12/04 09:45:45  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:13:22  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.TypeDefinitionCharacter;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements the definition of a property type that has a value of a single 2-byte character.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0223, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TypeDefinitionCharacter",
		  description = "The TypeDefinitionCharacter class defines a property type that has a value of a single 2-byte character.",
		  symbol = "TypeDefinitionCharacter")
public final class TypeDefinitionCharacterImpl 
	extends 
		SingletonTypeDefinitionImpl 
	implements 
		TypeDefinitionCharacter,
		Serializable {

	/** <p></p> */
	private static final long serialVersionUID = -7854052728814156013L;
	
	protected TypeDefinitionCharacterImpl() { }
	
	/**
	 * <p>Creates and initializes a new type definition for characters, which defines a property type that 
	 * has a value of a single 2-byte character like Java's <code>char</code> type.</p>
	 * 
	 * <p>Creating new and unregistered type definitions is not recommended as this may cause
	 * interoperability issues with other systems. The official registry of type definitions 
	 * is available from SMPTE at <a href="http://www.smpte-ra.org/mdd/">SMPTE Metadata Registries 
	 * And Related Items</a>. The full range of data types documented in the AAF 1.1 object 
	 * specification can be accessed by name and identification using either
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(String)} or
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(tv.amwa.maj.record.AUID)}
	 * respectively.</p>
	 * 
	 * @param identification AUID used to identify this type.
	 * @param typeName Friendly name used to identify this type.
	 * 
	 * @throws NullPointerException The identification is <code>null</code>.
	 */
	public TypeDefinitionCharacterImpl(
			AUID identification,
			@AAFString String typeName)
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new character type definition using a null identification value.");
		
		setIdentification(identification);
		setName(typeName);
	}
	
	public final static class CharacterValue 
		extends PropertyValueImpl
		implements PropertyValue {
		
		private Character value;
		private final TypeDefinitionCharacterImpl type;
		
		private CharacterValue(
				TypeDefinitionCharacterImpl type,
				Character value) {
			
			this.type = type;
			this.value = value;
		}

		public tv.amwa.maj.meta.TypeDefinition getType() {

			return type;
		}

		public boolean isDefinedType() {

			return true;
		}
		
		public Character getValue() {
			return value;
		}
		
		private void setValue(Character value) {
			this.value = value;
		}
	}
	
	public tv.amwa.maj.industry.PropertyValue createValueFromCharacter(
			char character) {

		return new CharacterValue(this, character);
	}

	public char getCharacter(
			tv.amwa.maj.industry.PropertyValue characterProperty)
		throws NullPointerException, 
			IllegalPropertyValueException {

		if (characterProperty == null)
			throw new NullPointerException("Cannot extract a character value from a null property value.");
		if (!(equals(characterProperty.getType())))
			throw new IllegalPropertyValueException("The given character property does not match this kind of character type definition.");
		
		return (Character) characterProperty.getValue();
	}

	public void setCharacter(
			tv.amwa.maj.industry.PropertyValue characterProperty, 
			char character)
		throws NullPointerException,
			IllegalPropertyValueException {

		if (characterProperty == null)
			throw new NullPointerException("Cannot set a character value into a null property value.");
		if (!(equals(characterProperty.getType())))
			throw new IllegalPropertyValueException("The given character property does not match this kind of character type definition.");
	
		((CharacterValue) characterProperty).setValue(character);
	}
	
	public TypeCategory getTypeCategory() {
		
		return TypeCategory.Character;
	}
	
	@Override
	public PropertyValue createValue(
			Object javaValue)
		throws ClassCastException {

		if (javaValue == null)
			throw new ClassCastException("Cannot cast a null value to a character type property.");
		
		if (javaValue instanceof ByteBuffer)
			return new CharacterValue(this, ((ByteBuffer) javaValue).getChar());
		
		if (javaValue instanceof CharacterValue) 
			return new CharacterValue(this, ((CharacterValue) javaValue).getValue());
		
		if (!(javaValue instanceof Character))
			throw new ClassCastException("Cannot create a character type property value with a non java.lang.Character value.");
		
		return new CharacterValue(this, (Character) javaValue);
	}

	@Override
	public PropertyValue createFromBytes(
			ByteBuffer buffer) 
		throws NullPointerException,
			EndOfDataException {

		super.createFromBytes(buffer);
		
		if (buffer.remaining() < 2)
			throw new EndOfDataException("Insufficient data in the given buffer to read a character value.");
		
		return new CharacterValue(this, buffer.getChar());
	}
	
	@Override
	public List<PropertyValue> writeAsBytes(
			PropertyValue value,
			ByteBuffer buffer)
		throws NullPointerException,
			IllegalPropertyValueException,
			InsufficientSpaceException {
		
		super.writeAsBytes(value, buffer);
		
		if (buffer.remaining() < 2)
			throw new InsufficientSpaceException("Not enough space remaining in the given buffer to write a character value.");
		
		buffer.putChar(((CharacterValue) value).getValue());
		
		return null;
	}
	
	@Override
	public long lengthAsBytes(
			PropertyValue value)
		throws NullPointerException,
			IllegalPropertyValueException {
		
		super.lengthAsBytes(value);
		
		return 2;
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		Element typeElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "TypeDefinitionCharacter");
		
		super.appendMetadictXML(typeElement, namespace, prefix);
	}
	
	public TypeDefinitionCharacter clone() {
		
		return (TypeDefinitionCharacter) super.clone();
	}
	
}
