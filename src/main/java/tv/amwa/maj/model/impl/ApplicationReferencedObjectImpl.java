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

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.model.ApplicationPluginObject;
import tv.amwa.maj.model.ApplicationReferencedObject;
import tv.amwa.maj.record.AUID;

@MediaClass(uuid1 = 0x0d010101, uuid2 = (short) 0x0101, uuid3 = (short) 0x6200,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		definedName = "ApplicationReferencedObject",
		symbol = "ApplicationReferencedObject",
		description = "Representation of any object connected by strong reference to an " +
				"application plugin object, whether directly or indirectly.")
public class ApplicationReferencedObjectImpl
	extends
		ApplicationObjectImpl
	implements
		ApplicationReferencedObject,
		Cloneable,
		Serializable {

	private static final long serialVersionUID = -3612154664539896315L;

	private WeakReference<ApplicationPluginObject> linkedApplicationPlugin = null;

	public ApplicationReferencedObjectImpl() { }

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0b00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C },
			definedName = "LinkedApplicationPluginInstanceID",
			typeName = "AUID",
			optional = false,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "LinkedApplicationPluginInstanceID",
			description = "Identifier of the application plugin object that strongly references this application referenced object.")
	public AUID getLinkedApplicationPluginInstanceID() {

		return linkedApplicationPlugin.getIdentifier();
	}

	@MediaPropertySetter("LinkedApplicationPluginInstanceID")
	public void setLinkedApplicationPluginInstanceID(
			AUID linkedApplicationPluginInstanceID)
		throws NullPointerException {

		if (linkedApplicationPluginInstanceID == null)
			throw new NullPointerException("Cannot set the application plugin object identifier using a null value.");

		this.linkedApplicationPlugin = new WeakReference<ApplicationPluginObject>(
				ApplicationPluginObject.class, linkedApplicationPluginInstanceID);

	}

	public void setLinkedApplicationPluginInstanceID(
			ApplicationPluginObject linkedApplicationPluginInstance)
		throws NullPointerException {

		if (linkedApplicationPluginInstance == null)
			throw new NullPointerException("Cannot set the application plugin object identifier using a null value.");

		WeakReference.registerTarget(linkedApplicationPluginInstance);
		this.linkedApplicationPlugin = new WeakReference<ApplicationPluginObject>(linkedApplicationPluginInstance);
	}

	public ExtensionScheme getApplicationScheme() {

		return linkedApplicationPlugin.getTarget().getApplicationScheme();
	}

	public ApplicationReferencedObject clone() {

		return (ApplicationReferencedObject) super.clone();
	}
}
