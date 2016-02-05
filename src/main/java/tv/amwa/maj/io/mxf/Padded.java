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

public interface Padded {

	/**
	 * <p>Returns the number of padding bytes following a significant entry in a {@linkplain Partition partition},
	 * such as a {@linkplain PartitionPack partition pack} or {@linkplain IndexTable index table}.</p>
	 *
	 * @return Padding bytes following a significant entry in a partition.
	 */
	public long getPaddingFillSize();

	/**
	 * <p>Set the amount of padding to include after each significant entry in the partition,
	 * allowing space for the metadata to expand over time.</p>
	 *
	 * @param paddingFillSize Size of padding to include after the partition.
	 *
	 * @throws IllegalArgumentException Cannot set the padding size to a negative value.
	 *
	 * @see Partition#setPartitionPackPadding(long)
	 */
	public void setPaddingFillSize(
			long paddingFillSize)
		throws IllegalArgumentException;
}
