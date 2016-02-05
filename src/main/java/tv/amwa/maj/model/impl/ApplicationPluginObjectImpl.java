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

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.model.ApplicationPluginObject;
import tv.amwa.maj.record.AUID;

@MediaClass(uuid1 = 0x0d010101, uuid2 = (short) 0x0101, uuid3 = (short) 0x6100,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		definedName = "ApplicationPluginObject",
		symbol = "ApplicationPluginObject",
		description = "Enables application-specific extensions to be associated " +
				"with any interchange object.")
public class ApplicationPluginObjectImpl
	extends
		ApplicationObjectImpl
	implements
		ApplicationPluginObject,
		Cloneable,
		Serializable,
		WeakReferenceTarget {

	private static final long serialVersionUID = -6136644711475711832L;

	private AUID applicationPluginInstanceID;
	private String applicationEnvironmentID = null;
	private WeakReference<ExtensionScheme> applicationScheme;

	public ApplicationPluginObjectImpl() { }

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0d00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C },
			definedName = "ApplicationPluginInstanceID",
			aliases = { "ApplicationPlug-InInstanceID" },
			typeName = "AUID",
			optional = false,
			uniqueIdentifier = true,
			pid = 0,
			symbol = "ApplicationPluginInstanceID",
			description = "Immutable identifier of this application metadata plug-in instance.")
	public AUID getApplicationPluginInstanceID() {

		return applicationPluginInstanceID.clone();
	}

	@MediaPropertySetter("ApplicationPluginInstanceID")
	public void setApplicationPluginInstanceID(
			AUID applicationPluginInstanceID)
		throws NullPointerException {

		if (applicationPluginInstanceID == null)
			throw new NullPointerException("Cannot set the application plugin instance identifier using a null value.");

		this.applicationPluginInstanceID = applicationPluginInstanceID.clone();
		WeakReference.registerTarget(this);
	}

	public AUID getAUID() {

		return getApplicationPluginInstanceID();
	}

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0f00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C},
			definedName = "ApplicationEnvironmentID",
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "ApplicationEnvironmentID",
			description = "Application environment identifier, an Uniform " +
					"Resource Identifier (RFC 3986) that identifies the application to which the information in this plugin " +
					"object applies.")
	public String getApplicationEnvironmentID()
			throws PropertyNotPresentException {

		if (applicationEnvironmentID == null)
			throw new PropertyNotPresentException("The optional application environment ID property is not present for this application plugin object.");

		return applicationEnvironmentID;
	}

	@MediaPropertySetter("ApplicationEnvironmentID")
	public void setApplicationEnvironmentID(
			String applicationEnvironmentID) {

		this.applicationEnvironmentID = applicationEnvironmentID;
	}

	@MediaProperty(uuid1 = 0x04060803, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C},
			definedName = "ApplicationScheme",
			typeName = "ExtensionSchemeWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "ApplicationScheme",
			description = "Application metadata scheme contained in this plugin object.")
	public ExtensionScheme getApplicationScheme() {

		return applicationScheme.getTarget();
	}

	@MediaPropertySetter("ApplicationScheme")
	public void setApplicationScheme(
			ExtensionScheme applicationScheme)
		throws NullPointerException {

		if (applicationScheme == null)
			throw new NullPointerException("Unable to set the application scheme of this application plugin using a null value.");

		this.applicationScheme = new WeakReference<ExtensionScheme>(applicationScheme);
	}

	public String getWeakTargetReference() {

		return applicationPluginInstanceID.toString();
	}

	public ApplicationPluginObject clone() {

		return (ApplicationPluginObject) super.clone();
	}

}
