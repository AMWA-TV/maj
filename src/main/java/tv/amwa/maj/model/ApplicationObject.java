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

package tv.amwa.maj.model;

import java.util.Set;
import java.util.SortedMap;

import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.record.AUID;

/**
 * <p>Common properties of {@linkplain ApplicationPluginObject application plugin objects} and
 * {@linkplain ApplicationReferencedObject application referenced objects}. These classes provide the
 * mechanism to decorate {@linkplain InterchangeObject interchange objects} with application-specific
 * extension metadata.</p>
 *
 *
 *
 * @see tv.amwa.maj.meta.ExtensionScheme
 */
public abstract interface ApplicationObject
	extends InterchangeObject {

	/**
	 * <p>Returns the class identifier of the immediate superclass that this object extends.
	 * This is an optional property.</p>
	 *
	 * @return Class identifier of the immediate superclass that this object extends.
	 *
	 * @throws PropertyNotPresentException The optional base class property is not present for
	 * this application object.
	 *
	 * @see InterchangeObject#getObjectClass()
	 */
	public ClassDefinition getBaseClass()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the class identifier of the immediate superclass that this object extends. Set
	 * this optional property to <code>null</code> to omit it.</p>
	 *
	 * @param baseClass Class identifier of the immediate superclass that this object extends.
	 *
	 * @see InterchangeObject#setObjectClass(ClassDefinition)
	 */
	public void setBaseClass(
			ClassDefinition baseClass);
//
//	/**
//	 * <p>Sets the extension scheme used to define properties, types and classes of this application
//	 * object. The extension scheme is used to seed methods if the interface that use strings rather
//	 * than AUIDs.</p>
//	 *
//	 * <p>This value is persisted within this object.</p>
//	 *
//	 * @param extensionScheme Extension scheme in use for this application object.
//	 */
//	public void setApplicationScheme(
//			ExtensionScheme extensionScheme);

	/**
	 * <p>Put an extension property value into this application object by property identifier, either
	 * creating the property the property and making it present, or updating its value. The property
	 * should be within the context of the defined extension scheme.</p>
	 *
	 * <p>Note that the property value cannot itself be <code>null</code> but the value it wraps
	 * may be <code>null</code>, depending on the type and property definition. Property values
	 * can be created with the {@link TypeDefinition#createValue(Object)} method.</p>
	 *
	 * @param propertyID Identifier of the property to add or update.
	 * @param propertyValue Value of the property to update.
	 *
	 * @throws IllegalArgumentException The given property identifier is not found within an appropriate
	 * context.
	 * @throws NullPointerException One or both of the property identifier or value is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value does not match the type defined for
	 * the property.
	 *
	 * @see #putExtensionProperty(String, PropertyValue)
	 * @see #getExtensionProperty(AUID)
	 */
	public void putExtensionProperty(
			AUID propertyID,
			PropertyValue propertyValue)
		throws IllegalArgumentException,
			NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Put an extension property value into this application object by property name, either
	 * creating the property the property and making it present, or updating its value. The property
	 * should be within the context of the defined extension scheme.</p>
	 *
	 * <p>Note that the property value cannot itself be <code>null</code> but the value it wraps
	 * may be <code>null</code>, depending on the type and property definition. Property values
	 * can be created with the {@link TypeDefinition#createValue(Object)} method.</p>
	 *
	 * @param propertyName Identifier of the property to add or update.
	 * @param propertyValue Value of the property to update.
	 *
	 * @throws IllegalArgumentException The given property identifier is not known in the extension scheme.
	 * @throws NullPointerException One or both of the property identifier or value is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value does not match the type defined for
	 * the property.
	 *
	 * @see #putExtensionProperty(AUID, PropertyValue)
	 * @see #getExtensionProperty(String)
	 */
	public void putExtensionProperty(
			String propertyName,
			PropertyValue propertyValue)
		throws IllegalArgumentException,
			NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Get the value of an extension property by its property identifier.</p>
	 *
	 * @param propertyID Identifier of the extension property to retrieve the value of.
	 * @return Value of the extension property with the given identifier.
	 *
	 * @throws NullPointerException Cannot retrieve an extension property using a <code>null</code>
	 * identifier.
	 * @throws PropertyNotPresentException The given property identifier is not present for
	 * this application object.
	 * @throws IllegalArgumentException The given property identifier is not known in the extension
	 * scheme.
	 *
	 * @see #getExtensionProperty(String)
	 * @see #putExtensionProperty(String, PropertyValue)
	 */
	public PropertyValue getExtensionProperty(
			AUID propertyID)
		throws NullPointerException,
			PropertyNotPresentException,
			IllegalArgumentException;

	/**
	 * <p>Get the value of an extension property by its property name.</p>
	 *
	 * @param propertyName Name of the extension property to retrieve the value of.
	 * @return Value of the extension property with the given identifier.
	 *
	 * @throws NullPointerException Cannot retrieve an extension property using a <code>null</code>
	 * name.
	 * @throws PropertyNotPresentException The given property name is not present for
	 * this application object.
	 * @throws IllegalArgumentException The given property identifier is not known in the extension
	 * scheme.
	 *
	 * @see #getExtensionProperty(AUID)
	 * @see #putExtensionProperty(String, PropertyValue)
	 */
	public PropertyValue getExtensionProperty(
			String propertyName)
		throws NullPointerException,
			PropertyNotPresentException,
			IllegalArgumentException;

	/**
	 * <p>Determines whether the given property identifier is for a present property of the
	 * application object. A property is present when it exists in the extension properties
	 * and its value is not <code>null</code>.</p>
	 *
	 * @param propertyID Property identifier to check if the property is present.
	 * @return Is the property present for this application object?
	 *
	 * @throws NullPointerException Cannot check for the presence of a property using a <code>null</code>
	 * identifier.
	 * @throws IllegalArgumentException The given property identifier does not match one
	 * known in the associated extension scheme.
	 *
	 * @see #isExtensionPropertyPresent(String)
	 * @see #getExtensionProperty(AUID)
	 */
	public boolean isExtensionPropertyPresent(
			AUID propertyID)
		throws NullPointerException,
			IllegalArgumentException;

	/**
	 * <p>Determines whether the given property name is for a present property of the
	 * application object. A property is present when it exists in the extension properties
	 * and its value is not <code>null</code>.</p>
	 *
	 * @param propertyName Property identifier to check if the property is present.
	 * @return Is the property present for this application object?
	 *
	 * @throws NullPointerException Cannot check for the presence of a property using a <code>null</code>
	 * identifier.
	 * @throws IllegalArgumentException The given property identifier does not match one
	 * known in the associated extension scheme.
	 *
	 * @see #isExtensionPropertyPresent(AUID)
	 * @see #getExtensionProperty(String)
	 */
	public boolean isExtensionPropertyPresent(
			String propertyName)
		throws NullPointerException,
			IllegalArgumentException;

	/**
	 * <p>Returns the number of extension properties that are present for this application
	 * object.</p>
	 *
	 * @return Number of extension properties that are present for this application
	 * object.
	 */
	public int countExtensionProperties();

	/**
	 * <p>Clear all extension properties from this application object, effectively omitting
	 * them all.</p>
	 */
	public void clearExtensionProperties();

	/**
	 * <p>Returns the set of extension property identifiers for present properties of this
	 * application object. This set can be used to iterate through all of the property
	 * values with {@link #getExtensionProperty(AUID)}.</p>
	 *
	 * @return Set of property identifiers for present properties of this application
	 * object.
	 */
	public Set<AUID> getExtensionPropertyIDs();

	/**
	 * <p>Creates a map of all present {@linkplain tv.amwa.maj.meta.PropertyDefinition property definitions}
	 * of this application object and their current values.</p>
	 *
	 * @return All present property definitions of this object mapped to their values.
	 */
	public SortedMap<? extends PropertyDefinition, ? extends PropertyValue> getProperties();

	/**
	 * <p>Create a cloned copy of this application object.</p>
	 *
	 * @return Cloned copy of this application object.
	 */
	public ApplicationObject clone();

}
