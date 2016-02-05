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
 * $Log: TypeDefinitionExtendibleEnumeration.java,v $
 * Revision 1.9  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/03/19 09:45:53  vizigoth
 * Minor comment fix following refactoring to move ExtendibleEnumerationItem to industry.
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
 * Revision 1.1  2007/11/13 22:08:31  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.DuplicateException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;


/**
 * <p>Specifies the definition of a property type that can have one of an extendible set
 * of {@linkplain tv.amwa.maj.record.AUID AUID} values. Each value is an enumeration element
 * with its own name. The elements are specified as an array of identifiers and an array of names,
 * where a single element is defined by the identifier and name and the same index of the
 * arrays.</p>
 * 
 * <p>The {@linkplain tv.amwa.maj.industry.Warehouse MAJ warehouse} keeps a runtime inventory of
 * elements for extendible enumeration types. A quick way to fill the inventory is to define a
 * class containing static {@linkplain tv.amwa.maj.record.AUID AUID} values an annotating these 
 * as {@linkplain tv.amwa.maj.industry.ExtendibleEnumerationItem extendible enumeration items}.</p>
 * 
 *
 *
 * @see tv.amwa.maj.enumeration.TypeCategory#ExtEnum
 * @see tv.amwa.maj.industry.Warehouse#registerExtendibleEnumerationElements(Class)
 * @see tv.amwa.maj.industry.Warehouse#registerExtendibleEnumerationElement(String, String, AUID)
 * @see tv.amwa.maj.industry.Warehouse#lookupExtendibleEnumeration(String)
 * @see tv.amwa.maj.industry.ExtendibleEnumerationItem
 */

public interface TypeDefinitionExtendibleEnumeration 
	extends TypeDefinition {

	/**
	 * <p>Creates a property value defined by this extendible enumeration
	 * type definition using the given name. The name is checked
	 * to see that it is in the list of names defining elements of this
	 * enumeration.</p>
	 * 
	 * @param name Name to use to create a new extendible enumeration
	 * property value.
	 * @return New extendible enumeration property value corresponding
	 * to the given name.
	 * 
	 * @throws NullPointerException The given name is <code>null</code>.
	 * @throws InvalidParameterException The given name does not match an element
	 * of this extendible enumeration type definition.
	 * 
	 * @see #setAUIDValue(PropertyValue, AUID)
	 */
	public PropertyValue createValueFromName(
			@AAFString String name) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns the number of enumeration elements of this 
	 * extendible enumeration type definition.</p>
	 * 
	 * @return Number of enumeration elements of this 
	 * extendible enumeration type definition.
	 */
	public @UInt32 int countElements();

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.record.AUID AUID} value for the enumeration element
	 * at the given index of in the list of elements of this extendible enumeration type definition.</p>
	 * 
	 * @param index Index of the required {@linkplain tv.amwa.maj.record.AUID AUID} value.
	 * @return AUID value for the enumeration element at the 
	 * given index of the list of elements of this extendible enumeration type definition.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside
	 * the acceptable range for the list of elements of this extendible enumeration type
	 * definition.
	 */
	public AUID getElementValue(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the name of the enumeration element
	 * at the given index in the list of elements of this extendible enumeration type 
	 * definition.</p>
	 * 
	 * @param index Index of the required name.
	 * @return Name for the enumeration element at the 
	 * given index in the list of elements of this extendible enumeration type definition.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside
	 * the acceptable range for this extendible enumeration type
	 * definition.
	 */
	public @AAFString String getElementName(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the name associated with the given
	 * property value, as defined by this
	 * extendible enumeration type definition.</p>
	 * 
	 * @param enumerationProperty Property value to lookup the name of.
	 * @return Name of the enumeration element of the given extendible 
	 * enumeration property value.
	 * 
	 * @throws NullPointerException The given extendible enumeration property value is 
	 * <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not
	 * defined by this extendible enumeration type definition.
	 */
	public @AAFString String getNameFromValue(
			PropertyValue enumerationProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Returns the name of the enumeration 
	 * element associated with the given AUID value, as defined by
	 * this extendible enumeration type definition.</p>
	 * 
	 * @param elementValue Value to use to lookup the element name.
	 * @return Name associated with the given
	 * value in this extendible enumeration type definition.
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * @throws BadParameterException The given value is not found
	 * in the list of values of this extendible enumeration type
	 * definition.
	 * 
	 * @see #getAUIDFromName(String)
	 */
	public @AAFString String getNameFromAUID(
			AUID elementValue) 
		throws NullPointerException,
			BadParameterException;

	/**
	 * <p>Returns the identifier of the enumeration element associated
	 * with the given name, as defined by this extendible enumeration type
	 * definition.</p>
	 * 
	 * @param elementName Name to use to lookup the element identifier.
	 * @return Identifier associated with the given name in this 
	 * extendible enumeration type definition.
	 * 
	 * @throws NullPointerException The given element name is <code>null</code>.
	 * @throws BadParameterException The given element name is not known 
	 * for in the list of values of this extendible enumeration type
	 * definition.
	 * 
	 * @see #getNameFromAUID(AUID)
	 */
	public AUID getAUIDFromName(
			@AAFString String elementName)
		throws NullPointerException,
			BadParameterException;
	
	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.record.AUID AUID} value associated with the given
	 * extendible enumeration property value according to this extendible 
	 * enumeration type definition.</p>
	 * 
	 * @param enumerationProperty Extensible enumeration property value
	 * to find the associated AUID value for.
	 * @return AUID associated with the given property value by
	 * this extendible enumeration type definition.
	 * 
	 * @throws NullPointerException The given extendible enumeration property value
	 * is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not 
	 * defined by this extendible enumeration type definition.
	 * 
	 * @see #getNameFromAUID(AUID)
	 * @see #getAUIDFromName(String)
	 */
	public AUID getAUIDValue(
			PropertyValue enumerationProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Sets the value of the given extendible enumeration property
	 * value to the given {@linkplain tv.amwa.maj.record.AUID AUID} that identifies
	 * an element of this extendible enumeration type definition.</p>
	 * 
	 * @param enumerationProperty Extensible enumeration property value
	 * to change.
	 * @param value New value to set the given property value to.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not 
	 * defined by this extendible enumeration type definition.
	 * @throws BadParameterException The given enumeration value identifier does not identifiy
	 * an element of this extendible enumeration type.
	 * 
	 * @see #getNameFromAUID(AUID)
	 * @see #getAUIDFromName(String)
	 * @see #createValueFromName(String)
	 */
	public void setAUIDValue(
			PropertyValue enumerationProperty,
			AUID value) 
		throws NullPointerException,
			IllegalPropertyValueException, 
			BadParameterException;

	/**
	 * <p>Appends the given element, a name and {@linkplain tv.amwa.maj.record.AUID AUID} pair, to 
	 * this extendible enumeration type definition.</p>
	 * 
	 * @param value AUID for the new enumeration element to append.
	 * @param name Name for the new enumeration element to append.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * @throws DuplicateException Appending the given enumeration element
	 * would introduce a duplicate value and/or duplicate name.
	 */
	public void appendElement(
			AUID value,
			@AAFString String name) 
		throws NullPointerException,
			DuplicateException;
	
	/**
	 * <p>Create a cloned copy of this extendible enumeration type definition.</p>
	 *
	 * @return Cloned copy of this extendible enumeration type definition.
	 */
	public TypeDefinitionExtendibleEnumeration clone();
	
}
