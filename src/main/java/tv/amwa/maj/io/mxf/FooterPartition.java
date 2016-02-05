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
 * <p>Represents the footer partition of an MXF file, providing access to its constituent parts. A footer partition may contain
 * {@linkplain HeaderMetadata header metadata} and {@linkplain IndexTableSegment index table segments}. It
 * should not contain any parts of a {@linkplain BodyStream body stream}. If present, the optional footer partition
 * is the last partition in an MXF file.</p>
 *
 *
 *
 * @see BodyPartition
 * @see HeaderPartition
 * @see MXFFile#getFooterPartition()
 */
public interface FooterPartition
	extends
		Partition,
		Cloneable {

	/**
	 * <p>Returns the footer partition pack that describes the details of this footer partition, including
	 * what it contains and its size.</p>
	 *
	 * @return Footer partition pack describing this footer partition.
	 */
	public FooterPartitionPack getPartitionPack();

	/**
	 * <p>Set the footer partition pack that describes this footer partition, including
	 * what it contains and its size.</p>
	 *
	 * @param partitionPack Footer partition pack describing this footer partition.
	 *
	 * @throws NullPointerException Cannot set the footer partition pack with a <code>null</code>
	 * value.
	 */
	public void setPartitionPack(
			FooterPartitionPack partitionPack)
		throws NullPointerException;

	/**
	 * <p>Create a cloned copy of this footer partition.</p>
	 *
	 * @return Cloned copy of this footer partition.
	 */
	public FooterPartition clone();
}
