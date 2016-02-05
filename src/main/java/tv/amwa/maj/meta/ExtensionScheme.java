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

package tv.amwa.maj.meta;

import java.util.Set;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.model.DescriptiveFramework;
import tv.amwa.maj.record.AUID;

/**
 * <p>Groups together a collection of {@linkplain MetaDefinition meta definitions} for an
 * extension to a file and provides a globally unique identifier for the collection.</p>
 *
 * <p>An MXF file may contain any number of Extension Scheme objects.</p>
 *
 * <p>An extension scheme may be used as part of an application metadata plugin or
 * {@linkplain DescriptiveFramework descriptive framework}.</p>
 *
 *
 *
 * @see MetaDefinition
 * @see tv.amwa.maj.model.ApplicationPluginObject
 * @see tv.amwa.maj.model.DescriptiveFramework
 * @see Root#getRootExtensions()
 */
public interface ExtensionScheme
	extends MetadataObject,
		WeakReferenceTarget {

	/**
	 * <p>Returns the globally unique identification of the extension scheme.</p>
	 *
	 * @return Globally unique identification of the extensions scheme.
	 *
	 * @see #getSchemeURI()
	 * @see tv.amwa.maj.model.ApplicationPluginObject#getApplicationScheme()
	 * @see tv.amwa.maj.model.Preface#getApplicationSchemes()
	 * @see tv.amwa.maj.model.Preface#getDescriptiveSchemes()
	 */
	public AUID getSchemeID();

	/**
	 * <p>Sets the globally unique identification of the extensions scheme. An extension
	 * scheme may be used as part of an application metadata plugin or
	 * {@linkplain DescriptiveFramework descriptive framework}.</p>
	 *
	 * @param schemeID Globally unique identification of the extensions scheme.
	 *
	 * @throws NullPointerException Cannot set the scheme identifier using a <code>null</code>
	 * value.
	 *
	 * @see #setSchemeURI(String)
	 * @see tv.amwa.maj.model.ApplicationPluginObject#setApplicationScheme(AUID)
	 * @see tv.amwa.maj.model.Preface#getApplicationSchemes()
	 * @see tv.amwa.maj.model.Preface#getDescriptiveSchemes()
	 */
	public void setSchemeID(
			AUID schemeID)
		throws NullPointerException;

	/**
	 * <p>Returns the namespace URI for the extension scheme.</p>
	 *
	 * @return Namespace URI for the extension scheme.
	 *
	 * @see #getSchemeID()
	 * @see MetaDefinition#getNamespace()
	 * @see MediaClass#namespace()
	 * @see MediaProperty#namespace()
	 */
	public String getSchemeURI();

	/**
	 * <p>Sets the namespace URI for the extension scheme.</p>
	 *
	 * @param schemeURI Namespace URI for the extension scheme.
	 *
	 * @throws NullPointerException Cannot set the scheme identifier using a <code>null</code>
	 * value.
	 *
	 * @see #setSchemeID(AUID)
	 * @see MetaDefinition#setNamespace(String)
	 * @see MediaClass#namespace()
	 * @see MediaProperty#namespace()
	 */
	public void setSchemeURI(
			String schemeURI)
		throws NullPointerException;

	/**
	 * <p>Returns the preferred namespace tag when SMPTE Reg-XML encoding is used. This is an
	 * optional property.</p>
	 *
	 * @return Preferred namespace tag when SMPTE Reg-XML encoding is used.
	 *
	 * @throws PropertyNotPresentException The optional preferred prefix property is
	 * not present for this extension scheme.
	 *
	 * @see #getSchemeURI()
	 * @see MetaDefinition#getPrefix()
	 * @see tv.amwa.maj.industry.MediaClass#prefix()
	 * @see tv.amwa.maj.industry.MediaProperty#prefix()
	 */
	public String getPreferredPrefix()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the preferred namespace tag when SMPTE Reg-XML encoding is used. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 *
	 * @param preferredPrefix Preferred namespace tag when SMPTE Reg-XML encoding is used.
	 *
	 * @see #setSchemeURI(String)
	 * @see tv.amwa.maj.industry.MediaClass#prefix()
	 * @see tv.amwa.maj.industry.MediaProperty#prefix()
	 */
	public void setPreferredPrefix(
			String preferredPrefix);

	/**
	 * <p>Returns the description of the extension scheme. This is an optional property.</p>
	 *
	 * @return Description of the extension scheme.
	 *
	 * @throws PropertyNotPresentException The optional description property of the extension scheme is
	 * not present.
	 */
	public String getExtensionDescription()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the description of the extension scheme. Set this optional property to <code>null</code>
	 * to omit it.</p>
	 *
	 * @param extensionDescription Description of the extension scheme.
	 */
	public void setExtensionDescription(
			String extensionDescription);

	/**
	 * <p>Returns the set of {@linkplain MetaDefinition meta definitions} that form the extensions scheme.
	 * The meta definitions are the {@linkplain ClassDefinition class definitions},
	 * {@linkplain PropertyDefinition property definitions} and {@linkplain TypeDefinition type definitions}
	 * that define extensions classes and properties for use in:</p>
	 *
	 * <ul>
	 *  <li>{@linkplain tv.amwa.maj.model.DescriptiveFramework descriptive frameworks};</li>
	 *  <li>{@linkplain tv.amwa.maj.model.DescriptiveObject descriptive objects};</li>
	 *  <li>{@linkplain tv.amwa.maj.model.ApplicationPluginObject application plugin objects};</li>
	 *  <li>{@linkplain tv.amwa.maj.model.ApplicationReferencedObject application referenced objects}.</li>
	 * </ul>
	 *
	 * <p>This is an optional property, although omitting it and defining an empty scheme would not make
	 * much sense.</p>
	 *
	 * @return Set of {@linkplain MetaDefinition meta definitions} that form the extensions scheme.
	 *
	 * @throws PropertyNotPresentException The optional meta definitions property is not present for this
	 * extension scheme.
	 */
	public Set<MetaDefinition> getMetaDefinitions()
		throws PropertyNotPresentException;

	/**
	 * <p>Add a {@linkplain MetaDefinition meta definition} to the set of definitions that form this extension
	 * scheme.</p>
	 *
	 * @param metaDefinition Meta definition to add to the scheme.
	 *
	 * @throws NullPointerException Cannot use a <code>null</code> value to add to the set of meta
	 * definitions.
	 *
	 * @see #getMetaDefinitions()
	 */
	public void addMetaDefinition(
			MetaDefinition metaDefinition)
		throws NullPointerException;

	/**
	 * <p>Lookup a {@linkplain MetaDefinition meta definition} within this extension scheme by its identifier.</p>
	 *
	 * @param metaDefinitionID Identifier of the meta definition to find in the extension scheme.
	 * @return Meta definition with the given identifier, or <code>null</code> if no matching meta definition could be
	 * found.
	 *
	 * @throws NullPointerException Cannot look for a meta definition using a <code>null</code> value.
	 *
	 * @see #lookupMetaDefinition(String)
	 * @see #lookupMetaDefinitionIdentifier(String)
	 * @see #getMetaDefinitions()
	 */
	public MetaDefinition lookupMetaDefinition(
			AUID metaDefinitionID)
		throws NullPointerException;

	/**
	 * <p>Lookup a {@linkplain MetaDefinition meta definition} within this extension scheme by its name.</p>
	 *
	 * @param metaDefinitionName Name of the meta definition to find in the extension scheme.
	 * @return  Meta definition with the given name, or <code>null</code> if no matching meta definition could be
	 * found.
	 *
	 * @throws NullPointerException Cannot look for a meta definition using a <code>null</code> value.
	 *
	 * @see #lookupMetaDefinition(AUID)
	 * @see #lookupMetaDefinitionIdentifier(String)
	 * @see #getMetaDefinitions()
	 */
	public MetaDefinition lookupMetaDefinition(
			String metaDefinitionName)
		throws NullPointerException;

	/**
	 * <p>Lookup the identifier of a {@linkplain MetaDefinition meta definition} in this extension scheme from
	 * its name.</p>
	 *
	 * @param metaDefinitionName Name of the meta definition to find in the extension scheme.
	 * @return Identifier of the named meta definition.
	 *
	 * @throws NullPointerException Cannot look for a meta definition using a <code>null</code> value.
	 *
	 * @see #lookupMetaDefinition(AUID)
	 * @see #lookupMetaDefinition(String)
	 * @see #getMetaDefinitions()
	 */
	public AUID lookupMetaDefinitionIdentifier(
			String metaDefinitionName)
		throws NullPointerException;

	/**
	 * <p>Determines whether the given {@linkplain MetaDefinition meta definition} is present within those that make up this
	 * extension scheme.</p>
	 *
	 * @param metaDefinition Meta definition to check for.
	 * @return Is the meta definition defined by this extension scheme?
	 *
	 * @throws NullPointerException Cannot use a <code>null</code> value to check for a meta definition.
	 *
	 * @see #containsMetaDefinition(AUID)
	 * @see #containsMetaDefinition(String)
	 * @see #getMetaDefinitions()
	 */
	public boolean containsMetaDefinition(
			MetaDefinition metaDefinition)
		throws NullPointerException;

	/**
	 * <p>Determines whether the given identifier is for a {@linkplain MetaDefinition meta definition} that is present
	 * within those that make up this extension scheme.</p>
	 *
	 * @param metaDefinition Identifier of the meta definition to check for.
	 * @return Is the identified meta definition defined by this extension scheme?
	 *
	 * @throws NullPointerException Cannot use a <code>null</code> value to check for a meta definition.
	 *
	 * @see #containsMetaDefinition(MetaDefinition)
	 * @see #containsMetaDefinition(String)
	 * @see #getMetaDefinitions()
	 */
	public boolean containsMetaDefinition(
			AUID metaDefinitionID)
		throws NullPointerException;

	/**
	 * <p>Determines whether the given name is for a {@linkplain MetaDefinition meta definition} that is present
	 * within those that make up this extension scheme.</p>
	 *
	 * @param metaDefinition Name of the meta definition to check for.
	 * @return Is the named meta definition defined by this extension scheme?
	 *
	 * @throws NullPointerException Cannot use a <code>null</code> value to check for a meta definition.
	 *
	 * @see #containsMetaDefinition(MetaDefinition)
	 * @see #containsMetaDefinition(AUID)
	 * @see #getMetaDefinitions()
	 */
	public boolean containsMetaDefinition(
			String metaDefinitionName)
		throws NullPointerException;

	/**
	 * <p>Clear all the {@linkplain MetaDefinition meta definitions} from this extensions scheme, omitting this optional property.</p>
	 *
	 * @see #getMetaDefinitions()
	 */
	public void clearMetaDefinitions();

	/**
	 * <p>Count the number of {@linkplain MetaDefinition meta definitions} in this extensions scheme.</p>
	 *
	 * @return Number of meta definitions in this extensions scheme.
	 *
	 * @see #getMetaDefinitions()
	 */
	public int countMetaDefinitions();

	/**
	 * <p>Remove the given {@linkplain MetaDefinition meta definition} from those that make up this
	 * extension scheme.</p>
	 *
	 * @param metaDefinition Meta definition to remove.
	 * @return Was the meta definition successfully removed from the set? Returns <code>false</code> if
	 * the meta definition was not part of this extension scheme.
	 *
	 * @throws NullPointerException Cannot remove a meta definition using a <code>null</code> value.
	 *
	 * @see #removeMetaDefinition(AUID)
	 * @see #getMetaDefinitions()
	 */
	public boolean removeMetaDefinition(
			MetaDefinition metaDefinition)
		throws NullPointerException;

	/**
	 * <p>Remove the identified {@linkplain MetaDefinition meta definition} from those that make up this
	 * extension scheme.</p>
	 *
	 * @param metaDefinition Identifier of the meta definition to remove.
	 * @return Was the meta definition successfully removed from the set? Returns <code>false</code> if
	 * the meta definition was not part of this extension scheme.
	 *
	 * @throws NullPointerException Cannot remove a meta definition using a <code>null</code> value.
	 *
	 * @see #removeMetaDefinition(MetaDefinition)
	 * @see #getMetaDefinitions()
	 */
	public boolean removeMetaDefinition(
			AUID metaDefinitionID)
		throws NullPointerException;

	/**
	 * <p>Create a cloned copy of this extension scheme.</p>
	 *
	 * @return Cloned copy of this extension scheme.
	 */
	public ExtensionScheme clone();
}
