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

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.PropertyWrapperDefinition;

@MediaClass(uuid1 = 0x0D010101, uuid2 = (short) 0x0227, uuid3 = (short) 0x0000,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		definedName = "PropertyWrapperDefinition",
		symbol = "PropertyWrapperDefinition",
		description = "Globally unique alias for a property that was originally defined to be used in another " +
				"class. This allows the property to be used unambiguously in more than one class.")
public class PropertyWrapperDefinitionImpl
	extends
		PropertyDefinitionImpl
	implements
		PropertyWrapperDefinition,
		Cloneable,
		Serializable {

	private static final long serialVersionUID = 6863107583083476678L;

	public PropertyWrapperDefinitionImpl() { }

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x2000, uuid3 = (short) 0x0000,
		uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0d },
		definedName = "OriginalProperty",
		typeName = "PropertyDefinitionWeakReference",
		optional = false,
		uniqueIdentifier = false,
		pid = 0x0029,
		symbol = "OriginalProperty",
		description = "Original definition of the reused property.")
	public PropertyDefinition getOriginalProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@MediaPropertySetter("OriginalProperty")
	public void setOriginalProperty(
			PropertyDefinition propertyDefinition)
		throws NullPointerException,
			IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	public PropertyWrapperDefinition clone() {

		// TODO
		return null;
	}
}
