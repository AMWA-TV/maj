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

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.record.AUID;

/**
 * <p>Enables application-specific extensions to be associated
 * with any {@linkplain InterchangeObject interchange object}.</p>
 *
 *
 *
 * @see InterchangeObject#getApplicationPlugins()
 *
 */
public interface ApplicationPluginObject
	extends ApplicationObject,
		WeakReferenceTarget,
		XMLSerializable {

	/**
	 * <p>Returns the immutable identifier of this application metadata plug-in instance.</p>
	 *
	 * @return Immutable identifier of this application metadata plug-in instance.
	 */
	public AUID getApplicationPluginInstanceID();

	/**
	 * <p>Sets the immutable identifier of this application metadata plug-in instance.</p>
	 *
	 * @param applicationPluginInstanceID Immutable identifier of this application metadata
	 * plug-in instance.
	 *
	 * @throws NullPointerException Cannot set the identifier of this application plugin object
	 * using a <code>null</code> value.
	 */
	public void setApplicationPluginInstanceID(
			AUID applicationPluginInstanceID)
		throws NullPointerException;

	/**
	 * <p>Returns the application metadata scheme contained in this plugin
	 * object. Application metadata schemes are represented by
	 * {@linkplain tv.amwa.maj.meta.ExtensionScheme extension schemes}.</p>
	 *
	 * @return Application metadata scheme contained in this plugin
	 * object.
	 *
	 * @see ApplicationObject#setExensionScheme(tv.amwa.maj.meta.ExtensionScheme)
	 * @see tv.amwa.maj.meta.ExtensionScheme#getSchemeID()
	 * @see Preface#getApplicationSchemes()
	 * @see tv.amwa.maj.meta.Root#getRootExtensions()
	 */
	public ExtensionScheme getApplicationScheme();

	/**
	 * <p>Sets the application metadata scheme contained in this plugin
	 * object. Application metadata schemes are represented by
	 * {@linkplain tv.amwa.maj.meta.ExtensionScheme extension schemes}.</p>
	 *
	 * @param applicationScheme Identifier of the application metadata scheme contained in this plugin
	 * object.
	 *
	 * @throws NullPointerException Cannot set the application scheme using a <code>null</code> value.
	 *
	 * @see ApplicationObject#setExensionScheme(tv.amwa.maj.meta.ExtensionScheme)
	 * @see tv.amwa.maj.meta.ExtensionScheme#getSchemeID()
	 * @see Preface#getApplicationSchemes()
	 */
	public void setApplicationScheme(
			ExtensionScheme applicationScheme)
		throws NullPointerException;

	/**
	 * <p>Returns the application environment identifier, an <a href="http://www.ietf.org/rfc/rfc3986.txt">Uniform
	 * Resource Identifier (RFC 3986)</a> that identifies the application to which the information in this plugin
	 * object applies. This is an optional property.</p>
	 *
	 * @return Application environment identifier.
	 *
	 * @throws PropertyNotPresentException The optional application environment identifier property
	 * is not present for this application plugin.
	 */
	public String getApplicationEnvironmentID()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the application environment identifier, an <a href="http://www.ietf.org/rfc/rfc3986.txt">Uniform
	 * Resource Identifier (RFC 3986)</a> that identifies the application to which the information in this plugin
	 * object applies. Set this optional property to <code>null</code> to omit it.</p>
	 *
	 * @param applicationEnvironmentID Application environment identifier.
	 */
	public void setApplicationEnvironmentID(
			String applicationEnvironmentID);

	/**
	 * <p>Create a cloned copy of this ApplicationPluginObject.</p>
	 *
	 * @return Cloned copy of this ApplicationPluginObject.
	 */
	public ApplicationPluginObject clone();
}
