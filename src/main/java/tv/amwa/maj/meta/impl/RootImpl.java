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
import java.util.Set;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyContains;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.meta.Root;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.record.AUID;

@MediaClass(uuid1 = 0x0D010101, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		definedName = "Root",
		symbol = "Root",
		description = "Represents the logical association of the definitions of the extensions used in " +
				"a file with the structural and descriptive metadata in the file.",
		namespace = CommonConstants.AAF_XML_NAMESPACE,
		prefix = CommonConstants.AAF_XML_PREFIX)
public class RootImpl
	implements
		Root,
		Cloneable,
		Serializable,
		MetadataObject {

	private static final long serialVersionUID = 5497260644740222373L;

	public RootImpl() { }

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1700, uuid3 = (short) 0x0000,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0a},
		definedName = "RootPreface",
		aliases = { "Preface" },
		typeName = "PrefaceStrongReference",
		optional = false,
		uniqueIdentifier = false,
		pid = 0x0002,
		symbol = "RootPreface",
		description = "Preface of the file.")
	public Preface getRootPreface() {
		// TODO Auto-generated method stub
		return null;
	}

	@MediaPropertySetter("RootPreface")
	public void setRootPreface(
			Preface preface)
		throws NullPointerException {
		// TODO Auto-generated method stub

	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1a00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0d },
			definedName = "RootExtensions",
			aliases = { "RootExtensionSchemes" },
			typeName = "ExtensionSchemeStrongReferenceSet",
			optional = false, // TODO confirm this - seems counter intuitive
			uniqueIdentifier = false,
			pid = 0x0023,
			symbol = "RootExtensions",
			description = "Set of extension schemes that are " +
					"used for this file. The extension schemes represent meta definitions " +
					"that are not contained in the AAF baseline.")
	public Set<ExtensionScheme> getRootExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@MediaSetAdd("RootExtensions")
	public void addRootExtension(
			ExtensionScheme extensionScheme) {
		// TODO Auto-generated method stub

	}

	@MediaPropertyClear("RootExtensions")
	public void clearRootExtensions() {
		// TODO Auto-generated method stub

	}

	@MediaPropertyContains("RootExtensions")
	public boolean containsRootExtension(
			ExtensionScheme extensionScheme)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsRootExtension(
			AUID extensionSchemeID)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsRootExtension(
			String schemeURI)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	@MediaPropertyCount("RootExtensions")
	public int countRootExtensions() {
		// TODO Auto-generated method stub
		return 0;
	}

	@MediaPropertyRemove("RootExtensions")
	public boolean removeRootExtension(
			ExtensionScheme extensionScheme)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeRootExtension(
			AUID extensionScheme)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1900, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0a },
			definedName = "RootFormatVersion",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0022,
			symbol = "RootFormatVersion",
			description = "Simple integer version number of meta model. If present, this " +
					"optional property shall be 12h (18) or greater.")
	public @UInt32 int getRootFormatVersion()
		throws PropertyNotPresentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@MediaPropertySetter("RootFormatVersion")
	public void setRootFormatVersion(
			@UInt32 Integer rootFormatVerison)
		throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	public Root clone() {

		// TODO
		return null;
	}
}
