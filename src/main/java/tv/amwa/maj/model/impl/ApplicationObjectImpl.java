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

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.model.ApplicationObject;
import tv.amwa.maj.record.AUID;

@MediaClass(uuid1 = 0x0d010101, uuid2 = (short) 0x0101, uuid3 = (short) 0x6600,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		definedName = "ApplicationObject",
		symbol = "ApplicationObject",
		description = "Common properties of application plugin objects and " +
				"application referenced objects. These classes provide the " +
				"mechanism to decorate interchange objects with application-specific " +
				"extension metadata.")
public abstract class ApplicationObjectImpl
	extends
		InterchangeObjectImpl
	implements
		ApplicationObject,
		Cloneable,
		Serializable,
		XMLSerializable {

	private static final long serialVersionUID = -6841492239125321529L;

	private WeakReference<ClassDefinition> baseClass = null;
	private Map<AUID, PropertyValue> extensionProperties =
		Collections.synchronizedMap(new HashMap<AUID, PropertyValue>());

	public ApplicationObjectImpl() { }

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x010B, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C},
			definedName = "BaseClass",
			typeName = "ClassDefinitionWeakReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "BaseClass",
			description = "Class of the immediate superclass that this object extends.")
	public ClassDefinition getBaseClass()
		throws PropertyNotPresentException {

		if (baseClass == null)
			throw new PropertyNotPresentException("The optional base class property is not present for this application object.");

		return baseClass.getTarget();
	}

	@MediaPropertySetter("BaseClass")
	public void setBaseClass(
			ClassDefinition baseClass) {

		if (baseClass == null) {
			this.baseClass = null;
			return;
		}

		this.baseClass = new WeakReference<ClassDefinition>(baseClass);
	}

	public Set<AUID> getExtensionPropertyIDs() {

		return extensionProperties.keySet();
	}

	public void clearExtensionProperties() {

		extensionProperties.clear();
	}

	public int countExtensionProperties() {

		return extensionProperties.size();
	}

	public PropertyValue getExtensionProperty(
			AUID propertyID)
		throws NullPointerException,
			PropertyNotPresentException,
			IllegalArgumentException {

		if (propertyID == null)
			throw new NullPointerException("Cannot retrieve an extension property using a null id.");
		if (extensionProperties.size() == 0)
			throw new PropertyNotPresentException("The optional extension properties set is not present for this application object.");
		if (!extensionProperties.containsKey(propertyID)) {
			if (getApplicationScheme().containsMetaDefinition(propertyID)) {
				PropertyDefinition property = Warehouse.lookForProperty(propertyID);
				if (property != null)
					throw new PropertyNotPresentException("The optional property " + property.getMemberOf().getName() + "." +
							property.getName() + " is not present for this application object.");
				else
					throw new PropertyNotPresentException("The optional property with identifier " + propertyID.toString() +
							" is not present for this applicaiton object.");
			}
			else {
				throw new IllegalArgumentException("The given property identifier is not part of extension scheme " +
						getApplicationScheme().getSchemeURI() + ".");
			}
		}

		return extensionProperties.get(propertyID);
	}

	public PropertyValue getExtensionProperty(
			String propertyName)
		throws NullPointerException,
			PropertyNotPresentException,
			IllegalArgumentException {

		if (propertyName == null)
			throw new NullPointerException("Cannot retrieve an extension property value using a null value.");
		AUID propertyKey = getApplicationScheme().lookupMetaDefinitionIdentifier(propertyName);
		if (propertyKey == null)
			throw new IllegalArgumentException("The given property name " + propertyName + " is not known in the associated " +
					"application scheme.");

		return getExtensionProperty(propertyKey);
	}

	public boolean isExtensionPropertyPresent(
			AUID propertyID)
		throws NullPointerException,
			IllegalArgumentException {

		if (propertyID == null)
			throw new NullPointerException("Cannot check for an extension property using a null value.");

		if (extensionProperties.containsKey(propertyID)) return true;

		if (getApplicationScheme().containsMetaDefinition(propertyID)) return false;

		throw new IllegalArgumentException("The given property identifier does not match one of the associated extension scheme.");
	}

	public boolean isExtensionPropertyPresent(
			String propertyName)
		throws NullPointerException,
			IllegalArgumentException {

		if (propertyName == null)
			throw new NullPointerException("Cannot check for an extension property usning a null value.");

		AUID propertyKey = getApplicationScheme().lookupMetaDefinitionIdentifier(propertyName);
		if (propertyKey == null)
			throw new IllegalArgumentException("The given property name does not match one of the associated extension scheme.");

		return isExtensionPropertyPresent(propertyKey);
	}

	public void putExtensionProperty(
			AUID propertyID,
			PropertyValue propertyValue)
		throws IllegalArgumentException,
			NullPointerException,
			IllegalPropertyValueException {

		if (propertyID == null)
			throw new NullPointerException("Cannot put an extension property value using a null property identifier.");
		if (propertyValue == null)
			throw new NullPointerException("Cannot put an extension property value using a null property value.");

		if (!getApplicationScheme().containsMetaDefinition(propertyID))
			throw new IllegalArgumentException("The given property identifier does not match one of the associated extension scheme.");
		MetaDefinition metaDefinition = getApplicationScheme().lookupMetaDefinition(propertyID);
		if (!(metaDefinition instanceof PropertyDefinition))
			throw new IllegalArgumentException("The given property identifier is not for a property definition.");

		PropertyDefinition propertyDefinition = (PropertyDefinition) metaDefinition;
		if (!propertyDefinition.getTypeDefinition().equals(propertyValue.getType()))
			throw new IllegalPropertyValueException("The type of the property value " + propertyValue.getType().getName() +
					" does not match the type of the associated property " + propertyDefinition.getTypeDefinition().getName() + ".");

		extensionProperties.put(propertyID, propertyValue);
	}

	public void putExtensionProperty(
			String propertyName,
			PropertyValue propertyValue)
		throws IllegalArgumentException,
			NullPointerException,
			IllegalPropertyValueException {

		if (propertyName == null)
			throw new NullPointerException("Cannot put an extension property value using a null property name.");

		AUID propertyKey = getApplicationScheme().lookupMetaDefinitionIdentifier(propertyName);
		if (propertyKey == null)
			throw new IllegalArgumentException("The given property name does not match one of the associated extension scheme.");

		putExtensionProperty(propertyKey, propertyValue);
	}

	public abstract ExtensionScheme getApplicationScheme();

	public SortedMap<? extends PropertyDefinition, ? extends PropertyValue> getProperties() {

		SortedMap<PropertyDefinition, PropertyValue> properties =
			new TreeMap<PropertyDefinition, PropertyValue>();

		ExtensionScheme applicationScheme = getApplicationScheme();
		for ( AUID propertyKey : extensionProperties.keySet() ) {
			MetaDefinition likelyPropertyDefinition = applicationScheme.lookupMetaDefinition(propertyKey);
			if ((likelyPropertyDefinition != null) &&
					(likelyPropertyDefinition instanceof PropertyDefinition))
				properties.put(
						(PropertyDefinition) likelyPropertyDefinition,
						(PropertyValue) extensionProperties.get(propertyKey));
		}

		return properties;
	}


	public void appendXMLChildren(
			Node parent) {

		ExtensionScheme applicationScheme = getApplicationScheme();
		for ( AUID propertyKey : getExtensionPropertyIDs() ) {

			PropertyValue value = getExtensionProperty(propertyKey);
			XMLBuilder.appendValue((Element) parent, applicationScheme.getSchemeURI(), applicationScheme.getPreferredPrefix(),
					applicationScheme.lookupMetaDefinition(propertyKey).getSymbol(), value);
		}
	}


	public ApplicationObject clone() {

		return (ApplicationObject) super.clone();
	}
}
