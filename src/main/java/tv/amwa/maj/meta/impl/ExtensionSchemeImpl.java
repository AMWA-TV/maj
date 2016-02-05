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

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyContains;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.record.AUID;

@MediaClass(uuid1 = 0x0D010101, uuid2 = (short) 0x0226, uuid3 = (short) 0x0000,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01 },
		definedName = "ExtensionScheme",
		symbol = "ExtensionScheme",
		description = "Groups together a collection of meta definitions for an " +
				"extension to a file and provides a globally unique identifier for the collection.",
		namespace = CommonConstants.AAF_XML_NAMESPACE,
		prefix = CommonConstants.AAF_XML_PREFIX)
public class ExtensionSchemeImpl
	implements
		ExtensionScheme,
		Cloneable,
		Serializable,
		MetadataObject,
		WeakReferenceTarget {

	private static final long serialVersionUID = 8353597427037923656L;

	private AUID schemeID;
	private String schemeURI;
	private String preferredPrefix = null;
	private String extensionDescription = null;
	private Map<AUID, MetaDefinition> metaDefinitions =
		Collections.synchronizedMap(new HashMap<AUID, MetaDefinition>());
	private Map<String, AUID> metaDefinitionNames =
		Collections.synchronizedMap(new HashMap<String, AUID>());

	public ExtensionSchemeImpl() { }

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1b00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0d },
			definedName = "SchemeID",
			aliases = { "ExtensionSchemeID" },
			typeName = "AUID",
			optional = false,
			uniqueIdentifier = true,
			pid = 0x0024,
			symbol = "SchemeID",
			description = "Globally unique identification of the extension scheme.")
	public AUID getSchemeID() {

		return schemeID.clone();
	}

	@MediaPropertySetter("SchemeID")
	public void setSchemeID(
			AUID schemeID)
		throws NullPointerException {

		if (schemeID == null)
			throw new NullPointerException("Cannot set the scheme identifier using a null value.");

		this.schemeID = schemeID.clone();
	}

	public AUID getAUID() {

		return schemeID.clone();
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1c00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0d },
			definedName = "SchemeURI",
			aliases = { "ExtensionSchemeURI", "SymbolSpaceURI", "ExtensionSymbolSpaceURI" },
			typeName = "UTF16String",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0025,
			symbol = "SchemeURI",
			description = "Namespace URI for the extension scheme.")
	public String getSchemeURI() {

		return schemeURI;
	}

	@MediaPropertySetter("SchemeURI")
	public void setSchemeURI(
			String schemeURI)
		throws NullPointerException {

		if (schemeURI == null)
			throw new NullPointerException("Cannot set the scheme URI using a null value.");

		this.schemeURI = schemeURI;
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1d00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0d },
			definedName = "PreferredPrefix",
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0026,
			symbol = "PreferredPrefix",
			description = "Preferred namespace tag when SMPTE Reg-XML encoding is used.")
	public String getPreferredPrefix()
		throws PropertyNotPresentException {

		if (preferredPrefix == null)
			throw new PropertyNotPresentException("The optional preferred prefix property is not present for this extension scheme.");

		return preferredPrefix;
	}

	@MediaPropertySetter("PreferredPrefix")
	public void setPreferredPrefix(
			String preferredPrefix) {

		this.preferredPrefix = preferredPrefix;
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1e00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0d },
			definedName = "ExtensionDescription",
			aliases = { "Description" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0027,
			symbol = "ExtensionDescription",
			description = "Description of the extension scheme.")
	public String getExtensionDescription()
		throws PropertyNotPresentException {

		if (extensionDescription == null)
			throw new PropertyNotPresentException("The optional extension description property is not present for this extension scheme.");

		return extensionDescription;
	}

	@MediaPropertySetter("ExtensionDescription")
	public void setExtensionDescription(
			String extensionDescription) {

		this.extensionDescription = extensionDescription;
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1f00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0d},
			definedName = "MetaDefinitions",
			typeName = "MetaDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0028,
			symbol = "MetaDefinitions",
			description = "Set of meta definitions that form the extensions scheme. " +
					"The meta definitions are the class definitions, property definitions and type definitions} " +
					"that define extensions classes and properties for use in descriptive frameworks, descriptive objects, " +
					"application plugin objects and application referenced objects.")
	public Set<MetaDefinition> getMetaDefinitions()
			throws PropertyNotPresentException {

		if (metaDefinitions.size() == 0)
			throw new NullPointerException("The optional meta definitions property is not present for this extension scheme.");

		return new HashSet<MetaDefinition>(metaDefinitions.values());
	}

	@MediaSetAdd("MetaDefinitions")
	public void addMetaDefinition(
			MetaDefinition metaDefinition)
		throws NullPointerException {

		if (metaDefinition == null)
			throw new NullPointerException("Cannot add a null meta definition to the set of meta definitions.");

		metaDefinitions.put(metaDefinition.getAUID(), metaDefinition);
		metaDefinitionNames.put(metaDefinition.getName(), metaDefinition.getAUID());
	}

	@MediaPropertyClear("MetaDefinitions")
	public void clearMetaDefinitions() {

		metaDefinitions.clear();
	}

	@MediaPropertyContains("MetaDefinitions")
	public boolean containsMetaDefinition(
			MetaDefinition metaDefinition)
		throws NullPointerException {

		if (metaDefinition == null)
			throw new NullPointerException("Cannot check for a meta definition using a null value.");

		return metaDefinitions.containsKey(metaDefinition.getAUID());
	}

	public boolean containsMetaDefinition(
			AUID metaDefinitionID)
		throws NullPointerException {

		if (metaDefinitionID == null)
			throw new NullPointerException("Cannot check for a meta definition using a null identifier.");

		return metaDefinitions.containsKey(metaDefinitionID);
	}

	public boolean containsMetaDefinition(
			String metaDefinitionName)
		throws NullPointerException {

		if (metaDefinitionName == null)
			throw new NullPointerException("Cannot check for a meta definition using a null name.");

		return metaDefinitionNames.containsKey(metaDefinitionName);
	}

	public MetaDefinition lookupMetaDefinition(
			AUID metaDefinitionID)
		throws NullPointerException {

		if (metaDefinitionID == null)
			throw new NullPointerException("Cannot lookup a meta definition using a null identifier.");

		return metaDefinitions.get(metaDefinitionID);
	}

	public MetaDefinition lookupMetaDefinition(
			String metaDefinitionName)
		throws NullPointerException {

		if (metaDefinitionName == null)
			throw new NullPointerException("Cannot lookup a meta definition using a null name.");

		if (!metaDefinitionNames.containsKey(metaDefinitionName)) return null;
		return metaDefinitions.get(metaDefinitionNames.get(metaDefinitionName));
	}

	public AUID lookupMetaDefinitionIdentifier(
			String metaDefinitionName)
		throws NullPointerException {

		if (metaDefinitionName == null)
			throw new NullPointerException("Cannot lookup a meta definition using a null name.");

		return metaDefinitionNames.get(metaDefinitionName).clone();
	}

	@MediaPropertyCount("MetaDefinitions")
	public int countMetaDefinitions() {

		return metaDefinitions.size();
	}

	@MediaPropertyRemove("MetaDefinitions")
	public boolean removeMetaDefinition(
			MetaDefinition metaDefinition)
		throws NullPointerException {

		if (metaDefinition == null)
			throw new NullPointerException("Cannot remove a meta definition using a null value.");

		return (metaDefinitions.remove(metaDefinition.getAUID()) != null);
	}

	public boolean removeMetaDefinition(
			AUID metaDefinitionID)
		throws NullPointerException {

		if (metaDefinitionID == null)
			throw new NullPointerException("Cannot remove a meta definition using a null value.");

		return (metaDefinitions.remove(metaDefinitionID) != null);
	}

	public String getWeakTargetReference() {

		return schemeID.toString();
	}

	public int hashCode() {

		return MediaEngine.hashCode(this);
	}

	public String toString() {

		return MediaEngine.toString(this);
	}

	public boolean equals(
			Object o) {

		return MediaEngine.equals(this, o);
	}

	public boolean deepEquals(
			Object o) {

		return MediaEngine.deepEquals(this, o);
	}

	public ExtensionScheme clone() {

		try {
			return (ExtensionScheme) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Should never get here
			throw new InternalError(cnse.getMessage());
		}
	}
}
