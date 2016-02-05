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
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.record.AUID;

/**
 * <p>Represents the logical association of the definitions of the extensions used in
 * a file with the structural and descriptive metadata in the file.</p>
 *
 *
 *
 * @see ExtensionScheme
 * @see tv.amwa.maj.model.Preface
 */
public interface Root
	extends MetadataObject {

	/**
	 * <p>Returns the {@linkplain Preface preface} of the file.</p>
	 *
	 * @return Preface of the file.
	 */
	public Preface getRootPreface();

	/**
	 * <p>Sets the {@linkplain Preface preface} of the file.</p>
	 *
	 * @param preface Preface of the file.
	 *
	 * @throws NullPointerException Cannot set the preface of the file using a
	 * <code>null</code> value.
	 */
	public void setRootPreface(
			Preface preface)
		throws NullPointerException;

	/**
	 * <p>Returns the set of {@linkplain ExtensionScheme extension schemes} that are
	 * used for this file. The extension schemes represent {@linkplain MetaDefinition meta definitions}
	 * that are not contained in the {@linkplain tv.amwa.maj.industry.AAFSpecifiedClasses AAF baseline}.</p>
	 *
	 * @return Set of extension schemes that are used for this file.
	 *
	 * @see tv.amwa.maj.model.ApplicationPluginObject#getApplicationScheme()
	 * @see tv.amwa.maj.model.DescriptiveMarker#getDescriptiveMetadataScheme()
	 * @see tv.amwa.maj.model.Preface#getApplicationPlugins()
	 * @see tv.amwa.maj.model.Preface#getDescriptiveSchemes()
	 */
	public Set<ExtensionScheme> getRootExtensions();

	/**
	 * <p>Add an {@linkplain ExtensionScheme extension scheme} to the set of extension
	 * schemes used for this file.</p>
	 *
	 * @param extensionScheme Extension scheme to add.
	 *
	 * @see #getRootExtensions()
	 */
	public void addRootExtension(
			ExtensionScheme extensionScheme);

	/**
	 * <p>Determines if the root contains the given {@linkplain ExtensionScheme extension scheme}, indicating that
	 * the scheme is used in the file.</p>
	 *
	 * @param extensionScheme Extension scheme to check for.
	 * @return Is the given extension scheme contained in the root of the file?
	 *
	 * @throws NullPointerException Cannot check for a root using a <code>null</code>
	 * value.
	 *
	 * @see #containsRootExtension(AUID)
	 * @see #containsRootExtension(String)
	 * @see #getRootExtensions()
	 */
	public boolean containsRootExtension(
			ExtensionScheme extensionScheme)
		throws NullPointerException;

	/**
	 * <p>Determines if the root contains the given {@linkplain ExtensionScheme extension scheme}
	 * {@linkplain ExtensionScheme#getSchemeID() identifier},
	 * indicating that the corresponding scheme is used in the file.</p>
	 *
	 * @param extensionScheme Identifier for the extension scheme to check for.
	 * @return Is the identified extension scheme contained in the root of the file?
	 *
	 * @throws NullPointerException Cannot check for a root using a <code>null</code>
	 * value.
	 *
	 * @see #containsRootExtension(ExtensionScheme)
	 * @see #containsRootExtension(String)
	 * @see #getRootExtensions()
	 * @see ExtensionScheme#getSchemeID()
	 */
	public boolean containsRootExtension(
			AUID extensionSchemeID)
		throws NullPointerException;

	/**
	 * <p>Determines if the root contains the given {@linkplain ExtensionScheme extension scheme} from its
	 * {@linkplain ExtensionScheme#getSchemeURI() scheme URI} or
	 * {@linkplain ExtensionScheme#getPreferredPrefix() preferred prefix}, indicating that the corresponding
	 * scheme is used in the file.</p>
	 *
	 * @param schemeURI Namespace URI or preferred prefix of the extension scheme to check for.
	 * @return Is the identified extension scheme contained in the root of the file?
	 *
	 * @throws NullPointerException Cannot check for a root using a <code>null</code>
	 * value.
	 *
	 * @see #containsRootExtension(ExtensionScheme)
	 * @see #containsRootExtension(AUID)
	 * @see #getRootExtensions()
	 * @see ExtensionScheme#getSchemeURI()
	 * @see ExtensionScheme#getPreferredPrefix()
	 */
	public boolean containsRootExtension(
			String schemeURI)
		throws NullPointerException;

	/**
	 * <p>Clear all the root {@linkplain ExtensionScheme extension schemes} for the file.</p>
	 *
	 * @see #getRootExtensions()
	 */
	public void clearRootExtensions();

	/**
	 * <p>Returns the number of {@linkplain ExtensionScheme extension schemes} attached
	 * to the file.</p>
	 *
	 * @return Number of extension schemes attached to the file.
	 *
	 * @see #getRootExtensions()
	 */
	public int countRootExtensions();

	/**
	 * <p>Remove the given extension scheme from the set of {@linkplain ExtensionScheme extension schemes}
	 * of this file.</p>
	 *
	 * @param extensionScheme Extension scheme to remove from this file.
	 * @return Has the extension scheme been successfully removed from this file? Returns <code>false</code> if
	 * the extension scheme was not contained.
	 *
	 * @throws NullPointerException Cannot remove an extension scheme using a <code>null</code> value.
	 *
	 * @see #removeRootExtension(AUID)
	 * @see #getRootExtensions()
	 */
	public boolean removeRootExtension(
			ExtensionScheme extensionScheme)
		throws NullPointerException;

	/**
	 * <p>Remove the identified extension scheme from the set of {@linkplain ExtensionScheme extension schemes}
	 * of this file.</p>
	 *
	 * @param extensionScheme Identifier for the extension scheme to remove from this file.
	 * @return Has the extension scheme been successfully removed from this file? Returns <code>false</code> if
	 * the extension scheme was not contained.
	 *
	 * @throws NullPointerException Cannot remove an extension scheme using a <code>null</code> value.
	 *
	 * @see #removeRootExtension(ExtensionScheme)
	 * @see #getRootExtensions()
	 */
	public boolean removeRootExtension(
			AUID extensionScheme)
		throws NullPointerException;

	/**
	 * <p>Returns the simple integer version number of meta model. If present, this
	 * optional property shall be 12h or greater.</p>
	 *
	 * @return Simple integer version number of meta model.
	 *
	 * @throws PropertyNotPresentException The optional root format version property is not
	 * present for this root.
	 */
	public @UInt32 int getRootFormatVersion()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the simple integer version number of meta model. If present, this
	 * optional property shall be 12h or greater.</p>
	 *
	 * @param rootFormatVerison Simple integer version number of meta model.
	 *
	 * @throws IllegalArgumentException Cannot set the root format version to a
	 * value less than 12h (18).
	 */
	public void setRootFormatVersion(
			@UInt32 Integer rootFormatVerison)
		throws IllegalArgumentException;

	/**
	 * <p>Create a cloned copy of this root.</p>
	 *
	 * @return Cloned copy of this root.
	 */
	public Root clone();
}
