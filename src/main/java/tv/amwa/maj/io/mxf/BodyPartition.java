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
 * <p>Represents a body partition of an MXF file, providing access to its constituent parts. A body partition may contain
 * {@linkplain HeaderMetadata header metadata}, an {@linkplain IndexTableSegment index table segment}
 * and part or all of a {@linkplain BodyStream body stream}. A sequence of body partitions make up the
 * middle of an MXF file, between the mandatory {@linkplain HeaderPartition header partition} at the start
 * and optional {@linkplain FooterPartition footer partition} at the end.</p>
 *
 *
 *
 * @see HeaderPartition
 * @see FooterPartition
 */
public interface BodyPartition
	extends
		Cloneable,
		EssencePartition {

	/**
	 * <p>Returns the body partition pack that describes the details of this body partition, including
	 * what it contains and its size.</p>
	 *
	 * @return Body partition pack describing this body partition.
	 */
	public BodyPartitionPack getPartitionPack();

	/**
	 * <p>Set the body partition pack that describes this body partition, including
	 * what it contains and its size.</p>
	 *
	 * @param partitionPack Body partition pack describing this body partition.
	 *
	 * @throws NullPointerException Cannot set the body partition pack with a <code>null</code>
	 * value.
	 */
	public void setPartitionPack(
			BodyPartitionPack partitionPack)
		throws NullPointerException;

	/**
	 * <p>Create a cloned copy of this body partition.</p>
	 *
	 * @return Cloned copy of this body partition.
	 */
	public BodyPartition clone();

}
