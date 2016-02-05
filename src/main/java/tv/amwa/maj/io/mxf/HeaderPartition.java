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

/**
 * <p>Represents the header partition of an MXF file, providing access to its constituent parts. A header partition
 * must contain {@linkplain HeaderMetadata header metadata} and may contain
 * {@linkplain IndexTableSegment index table segments} and parts of a {@linkplain BodyStream body stream}.
 * The header partition is always that first partition in an MXF file.</p>
 *
 *
 *
 * @see FooterPartition
 * @see BodyPartition
 * @see MXFFile#getHeaderPartition()
 */
public interface HeaderPartition
	extends
		EssencePartition,
		Cloneable {

	/**
	 * <p>Returns the header partition pack that describes the details of this header partition, including
	 * what it contains and its size.</p>
	 *
	 * @return Header partition pack describing this header partition.
	 */
	public HeaderPartitionPack getPartitionPack();

	/**
	 * <p>Set the header partition pack that describes this header partition, including
	 * what it contains and its size.</p>
	 *
	 * @param partitionPack Header partition pack describing this header partition.
	 *
	 * @throws NullPointerException Cannot set the header partition pack with a <code>null</code>
	 * value.
	 */
	public void setPartitionPack(
			HeaderPartitionPack partitionPack)
		throws NullPointerException;

	public HeaderPartition clone();

}
