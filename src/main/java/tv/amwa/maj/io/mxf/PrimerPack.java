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

package tv.amwa.maj.io.mxf;

import java.util.Set;

import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.record.AUID;

/**
 * <p>Represents the local primer pack of {@linkplain HeaderMetadata header metadata} required for the
 * local set encoding of {@linkplain tv.amwa.maj.industry.MetadataObject metadata objects}.
 * Tag-length-value encoding of an objects properties require the resolution of 2-byte tags
 * to full 16-byte keys. The primer pack is the table that allows for that resolution to
 * take place.</p>
 *
 *
 *
 * @see HeaderMetadata#getPrimerPack()
 * @see MXFBuilder#readLocalSet(UL, java.nio.ByteBuffer, PrimerPack, java.util.Map, java.util.List)
 */
public interface PrimerPack
	extends
		MetadataObject,
		FixedLengthPack,
		Cloneable {

	/**
	 * <p>Returns the set of local tag entries that make up the tag-to-key lookup table
	 * of this primer pack.</p>
	 *
	 * @return Set of local tag entries that make up the tag-to-key lookup table
	 * of this primer pack.
	 *
	 * @see TypeDefinitions#LocalTagEntryReference
	 * @see TypeDefinitions#LocalTagEntryBatch
	 */
	public Set<LocalTagEntry> getLocalTagEntryBatch();

	/**
	 * <p>Add a local tag entry to the lookup table that makes up the tag-to-key lookup
	 * table of this primer pack using a {@linkplain LocalTagEntry local tag entry}.</p>
	 *
	 * @param localTagEntry Local tag entry to add to the lookup table.
	 *
	 * @throws NullPointerException Cannot add a <code>null</code> local tag entry.
	 *
	 * @see #addLocalTagEntry(short, AUID)
	 * @see #addLocalTagEntry(PropertyDefinition)
	 */
	public void addLocalTagEntry(
			LocalTagEntry localTagEntry)
		throws NullPointerException;

	/**
	 * <p>Add a local tag entry to the lookup table that makes up the tag-to-key
	 * lookup table of this primer pack with the 2-byte tag and key.</p>
	 *
	 * @param localTag Tag to add to the lookup table.
	 * @param uid Corresponding key to add to the lookup table.
	 *
	 * @throws NullPointerException Cannot use a <code>null</code> key.
	 *
	 * @see #addLocalTagEntry(LocalTagEntry)
	 * @see #addLocalTagEntry(PropertyDefinition)
	 */
	public void addLocalTagEntry(
			@UInt16 short localTag, // Allow negative for dynamic tags > 0x8000
			tv.amwa.maj.record.AUID uid)
		throws NullPointerException;

	/**
	 * <p>Add a local tag entry based on the given {@linkplain PropertyDefinition property definition},
	 * based on its {@linkplain PropertyDefinition#getLocalIdentification() local identification}
	 * and {@linkplain PropertyDefinition#getAUID() globally unique identification}. The two identifications
	 * add an entry to the tag-to-key lookup table that makes up this primer pack.</p>
	 *
	 * @param propertyDefinition Property definition to use to create an entry in the tag-to-key
	 * lookup table.
	 *
	 * @throws NullPointerException Cannot use a <code>null</code> property definition.
	 *
	 * @see #addLocalTagEntry(LocalTagEntry)
	 * @see #addLocalTagEntry(short, AUID)
	 */
	public void addLocalTagEntry(
			PropertyDefinition propertyDefinition)
		throws NullPointerException;

	/**
	 * <p>Count the number of local tag entries that make up the tag-to-key lookup table of
	 * this primer pack.</p>
	 *
	 * @return Number of local tag entries that make up the tag-to-key lookup table of
	 * this primer pack.
	 */
	public int countLocalTagEntries();

	/**
	 * <p>Clear the tag-to-key lookup table of this primer pack.</p>
	 */
	public void clearLocalTagEntries();

	/**
	 * <p>Lookup the given tag in the tag-to-key lookup table of this primer pack, returning
	 * the given key or <code>null</code> if the tag is not known.</p>
	 *
	 * @param localTag Tag to find and resolve to a key in the lookup table.
	 * @return Key corresponding to the tag or <code>null</code> if the tag is not
	 * known.
	 */
	public AUID lookupUID(
			@UInt16 short localTag);

	/**
	 * <p>Lookup the given property identifier and find its local tag in the key-to-tag reverse
	 * lookup table of this primer pack.</p>
	 *
	 * @param uid Identifier to map to a local tag.
	 * @return Local tag that corresponds to the given property identifier in this primer
	 * pack, or <code>null</code> if the identifier is not recognized.
	 *
	 * @throws NullPointerException Cannot find a tag for a <code>null</code> value.
	 *
	 * @see #lookupLocalTag(PropertyDefinition)
	 */
	public Short lookupLocalTag(
			AUID uid)
		throws NullPointerException;

	/**
	 * <p>Lookup the given property's local tag in the key-to-tag reverse lookup table of
	 * this primer pack.</p>
	 *
	 * @param propertyDefinition Definition to find the local tag for.
	 * @return Local tag in this primer pack for the given property, or <code>null</code>
	 * if the property cannot be found.
	 *
	 * @throws NullPointerException Cannot find a tag for a <code>null</code> value.
	 *
	 * @see #addLocalTagEntry(PropertyDefinition)
	 * @see #lookupLocalTag(AUID)
	 */
	public Short lookupLocalTag(
			PropertyDefinition propertyDefinition)
		throws NullPointerException;

	/**
	 * <p>Determines if the given tag is present in this lookup table.</p>
	 *
	 * @param localTagEntry Tag to check for.
	 * @return Is the given tag present in this lookup table?
	 */
	public boolean isLocalTagEntryPresent(
			LocalTagEntry localTagEntry);

	/**
	 * <p>Create a cloned copy of this primer pack.</p>
	 *
	 * @return Cloned copy of this primer pack.
	 */
	public PrimerPack clone();
}
