package tv.amwa.maj.io.mxf;

import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.record.AUID;

/**
 * <p>Represents a single entry of the tag-to-key lookup table of a primer pack, mapping a 
 * 2&nbsp;byte tag to a 16&nbsp;byte key.</p>
 * 
 *
 *
 * @see PrimerPack#addLocalTagEntry(LocalTagEntry)
 * @see PrimerPack#getLocalTagEntryBatch()
 * @see TypeDefinitions#LocalTagEntryReference
 * @see TypeDefinitions#LocalTagEntryBatch
 */
public interface LocalTagEntry 
	extends 
		FixedLengthPack, 
		MetadataObject,
		Cloneable {
	
	/**
	 * <p>Returns the 2&nbsp;byte tag of this local entry. Negative Java
	 * values are treated as their bit-equivalent unsigned integer value, 
	 * e.g. <code>-2<code> is </code>0xfffe</code>.</p>
	 * 
	 * @return 2&nbsp;byte tag of this local entry.
	 */
	public @UInt16 short getLocalTag();
	
	/**
	 * <p>Sets the 2&nbsp;byte tag of this local tag entry. Negative Java
	 * values are treated as their bit-equivalent unsigned integer value,
	 * e.g. <code>-2<code> is </code>0xfffe</code>.</p>
	 * 
	 * @param localTag 2&nbsp;byte tag of this local entry.<p>
	 */
	public void setLocalTag(
			@UInt16 short localTag);
	
	/**
	 * <p>Returns the 16&nbsp;byte key part of the local tag entry.</p>
	 * 
	 * @return 16&nbsp;byte key part of the local tag entry.
	 */
	public AUID getUID();
	
	/**
	 * <p>Sets the 16&nbsp;byte key part of the local tag entry.</p>
	 * 
	 * @param uid 16&nbsp;byte key part of the local tag entry.</p>
	 * 
	 * @throws NullPointerException Cannot set the key part of the local tag
	 * entry with a <code>null</code> value.
	 */
	public void setUID(
			AUID uid)
		throws NullPointerException;

	/**
	 * <p>Create a cloned copy of this local tag entry.</p>
	 * 
	 * @return Cloned copy of this local tag entry.
	 */
	public LocalTagEntry clone();
	
}
