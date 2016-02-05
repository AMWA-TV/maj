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

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;

/**
 * <p>Entry in a random index pack of an MXF file that indexes the
 * byte offset of a {@linkplain Partition partition} in a file. The random
 * index pack is the last data set in an MXF file.</p>
 *
 *
 *
 * @see RandomIndexPack#getPartitionIndex()
 * @see RandomIndexPack#addRandomIndexItem(RandomIndexItem)
 * @see RandomIndexPack#addRandomIndexItem(int, long)
 * @see TypeDefinitions#RandomIndexItem
 * @see TypeDefinitions#RandomIndexItemArray
 */
public interface RandomIndexItem
	extends
		Comparable<RandomIndexItem>,
		Cloneable {

	/**
	 * <p>Returns the body stream identifier for the essence container in the
	 * {@linkplain Partition partition} indexed by this item. A value of zero
	 * indicates that the partition does not contain a body stream.</p>
	 *
	 * @return Body stream identifier within the indexed partition.
	 *
	 * @see PartitionPack#getBodySID()
	 */
	public @UInt32 int getBodySID();

	/**
	 * <p>Sets the body stream identifier for the essence container in the
	 * {@linkplain Partition partition} indexed by this item.</p>
	 *
	 * @param bodySID Body stream identifier within the indexed partition.
	 * @throws IllegalArgumentException Cannot set the body stream identifier
	 * to a negative value.
	 *
	 * @see PartitionPack#setBodySID(int)
	 */
	public void setBodySID(
			@UInt32 int bodySID)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the byte offset of the indexed {@linkplain Partition partition}
	 * in the MXF file. The byte offset is measured from the start of the
	 * {@linkplain HeaderPartition header partition}.</p>
	 *
	 * @return Byte offset of the indexed partition.
	 *
	 * @see PartitionPack#getThisPartition()
	 */
	public @UInt64 long getByteOffset();

	/**
	 * <p>Sets the byte offset of the indexed {@linkplain Partition partition}
	 * in the MXF file. The byte offset is measured from the start of the
	 * {@linkplain HeaderPartition header partition}.</p>
	 *
	 * @param byteOffset Byte offset of the indexed partition.
	 *
	 * @throws IllegalArgumentException Cannot set the byte offset to a
	 * negative value.
	 *
	 * @see PartitionPack#setThisPartition(long)
	 */
	public void setByteOffset(
			@UInt64 long byteOffset)
		throws IllegalArgumentException;

	/**
	 * <p>Compares this item to another random index item by byte offset. This method
	 * returns:</p>
	 *
	 * <ul>
	 *  <li><code>-1</code> if this item indexes a partition before the given item;</li>
	 *  <li><code>0</code> if this item is at the same position as the given item;</li>
	 *  <li><code>1</code> if this item indexes a partition after the given item.</li>
	 * </ul>
	 *
	 * @return If the indexed partition before, at the same position as or after the given
	 * partition's position in the MXF file.
	 */
	public int compareTo(
			RandomIndexItem o);

	/**
	 * <p>Create a cloned copy of this random index item.</p>
	 *
	 * @return Cloned copy of this random index item.
	 */
	public RandomIndexItem clone();

}
