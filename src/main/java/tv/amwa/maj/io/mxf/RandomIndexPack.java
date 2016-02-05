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

import java.nio.ByteBuffer;

import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>Represents a random index pack at the end of an MXF file that lists
 * the byte offsets of all the {@linkplain Partition partitions}.</p>
 *
 *
 *
 * @see RandomIndexItem
 * @see MXFFile#buildPartitionsTable()
 */
public interface RandomIndexPack
	extends MXFUnit,
		MetadataObject {

	/**
	 * <p>Key defining the start of a random index pack.</p>
	 */
	public final static UL ripKeyValue = new AUIDImpl(
			0x0d010201, (short) 0x0111, (short) 0x0100,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01});

	/**
	 * <p>Returns the array random index items of this random index pack. Each
	 * partition of the MXF file indexed should have an entry in this array.
	 * The array is ordered by the byte offset of the partition.</p>
	 *
	 * @return Array of random index items of this random index pack.
	 *
	 * @see PartitionPack#getThisPartition()
	 * @see TypeDefinitions#RandomIndexItem
	 * @see TypeDefinitions#RandomIndexItemArray
	 */
	public RandomIndexItem[] getPartitionIndex();

	/**
	 * <p>Sets the array of random index items of this random index pack. Each
	 * partition of the MXF file indexed should have an entry in this array.
	 * The items will be sorted into byte offset order.</p>
	 *
	 * @param partitionIndex Array of random index items of this random index pack.
	 *
	 * @throws NullPointerException Cannot set the partition index with a <code>null</code>
	 * array.
	 */
	public void setPartitionIndex(
			RandomIndexItem[] partitionIndex)
		throws NullPointerException;

	/**
	 * <p>Returns the number of partitions indexed by this random index pack.</p>
	 *
	 * @return Number of partitions indexed by this random index pack.
	 */
	public @UInt32 int count();

	/**
	 * <p>Returns an ordered list of the byte offsets for partitions containing
	 * the given body stream identifier. The order of the partitions is the play
	 * order of the essence contained in the essence streams.</p>
	 *
	 * @param bodySID Body stream identifier to query the partition offsets for.
	 *
	 * @return List of byte offsets to partitions containing part of the given
	 * body SID.
	 */
	public @UInt64 long[] lookupPartitionOffsets(
			@UInt32 int bodySID);

	/**
	 * <p>Clears the partition table within this random index pack.</p>
	 */
	public void clear();

	/**
	 * <p>Add a random index item to this pack by its body stream identifier and byte offset.
	 * A random index item describes the location of
	 * a partition and which body stream it contains data for.</p>
	 *
	 * @param bodySID Body stream identifier of the partition.
	 * @param byteOffset Byte offset of the partition in the file, measured from
	 * the beginning of the {@linkplain HeaderPartition header partition}.
	 *
	 * @throws IllegalArgumentException Cannot set the body stream identifier or
	 * the byte offset to a negative value.
	 *
	 * @see #addRandomIndexItem(RandomIndexItem)
	 * @see PartitionPack#getThisPartition()
	 * @see PartitionPack#getBodySID()
	 */
	public void addRandomIndexItem(
			@UInt32 int bodySID,
			@UInt64 long byteOffset)
		throws IllegalArgumentException;

	/**
	 * <p>Add an existing random index item to this pack.
	 * A random index item describes the location of
	 * a partition and which body stream it contains data for.</p>
	 *
	 * @param randomPartitionItem Item to add to this pack.
	 *
	 * @throws NullPointerException Cannot add a <code>null</code> index item.
	 *
	 * @see #addRandomIndexItem(int, long)
	 */
	public void addRandomIndexItem(
			RandomIndexItem randomPartitionItem)
		throws NullPointerException;

	/**
	 * <p>Returns the overall length of the random index pack when encoded as
	 * bytes, including the key and BER length.</p>
	 *
	 * @return Overall length of the random index pack when encoded as
	 * bytes, including the key and BER length.
	 *
	 * @see #writeAsBytes(ByteBuffer)
	 */
	public @UInt32 int getLength();

	/**
	 * <p>Serializes this random index pack into its file-based KLV-encoded version in
	 * the given buffer. The buffer must have at least {@link #getLength()} bytes remaining.</p>
	 *
	 * @param buffer Buffer to write this random index pack into.
	 * @return Number of bytes written.
	 *
	 * @throws NullPointerException Cannot write this random index pack to a null buffer.
	 * @throws InsufficientSpaceException The given buffer does not have sufficient space
	 * remaining.
	 *
	 * @see #getLength()
	 */
	public int writeAsBytes(
			ByteBuffer buffer)
		throws NullPointerException,
			InsufficientSpaceException;

	/**
	 * <p>Creates a cloned copy of this random index pack.</p>
	 *
	 * @return Cloned copy of this random index pack.
	 */
	public RandomIndexPack clone();

}
