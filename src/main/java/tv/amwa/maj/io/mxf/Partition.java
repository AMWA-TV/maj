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

import java.io.IOException;

import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.model.Preface;

/**
 * <p>Represents a partition of an MXF file, which is the top-level sub-division of an MXF file. An
 * {@linkplain MXFFile MXF file} is made up of a sequence of a {@linkplain HeaderPartition header partition},
 * followed by zero or more {@linkplain BodyPartition body partitions} and an optional
 * {@linkplain FooterPartition footer partition}. All partitions are self-describing with a
 * {@linkplain PartitionPack partition pack} at the start. Depending on their type, partitions
 * may contain {@linkplain HeaderMetadata header metadata}, {@linkplain essence stream data} and
 * {@linkplain IndexTableSegment index table segments}.</p>
 *
 *
 *
 * @see MXFFile#buildPartitionsTable()
 * @see PartitionPack
 */
public interface Partition
	extends Cloneable {

	/**
	 * <p>Size of the partition is not known and will need to be calculated.</p>
	 *
	 * @see #setInitialDataSize(long)
	 */
	public final static long SIZE_UNKNOWN = -1l;

	/**
	 * <p>Returns the partition pack that describes this partition, including its size and what it
	 * contains.</p>
	 *
	 * @return Partition pack that describes this partition.
	 *
	 * @see HeaderPartition#getPartitionPack()
	 * @see FooterPartition#getPartitionPack()
	 * @see BodyPartition#getPartitionPack()
	 */
	public PartitionPack getPartitionPack();

	/**
	 * <p>Set the amount of padding to include after each significant entry in the partition,
	 * allowing space for the metadata to expand over time.</p>
	 *
	 * @param paddingFillSize Size of padding to include after the partition.
	 *
	 * @throws IllegalArgumentException Cannot set the padding size to a negative value.
	 *
	 * @see Padded#getPaddingFillSize()
	 * @see Padded#setPaddingFillSize(long)
	 */
	public void setPartitionPackPadding(
			long paddingFillSize)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the header metadata contained within the partition, if any.</p>
	 *
	 * @return Header metadata of this partition, or <code>null</code> if no header
	 * metadata is present.
	 *
	 * @throws NullPointerException The partition's MXF file is <code>null</code> which may
	 * occur if this partition is being created unattached.
	 *
	 * @see #hasHeaderMetadata()
	 * @see #writeHeaderMetadata(Preface, long)
	 */
	public HeaderMetadata readHeaderMetadata()
		throws NullPointerException;

	/**
	 * <p>Determines whether this partition contains {@linkplain HeaderMetadata header metadata}.
	 * All {@linkplain HeaderPartition header partitions} must contain header metadata. Other
	 * partitions may contain header metadata.</p>
	 *
	 * @return Does this partition have header metadata?
	 *
	 * @see #readHeaderMetadata()
	 * @see #writeHeaderMetadata(Preface, long)
	 */
	public boolean hasHeaderMetadata();

	/**
	 * <p>Write the {@linkplain HeaderMetadata header metadata} of this partition using that provided
	 * in the given preface. Previous header metadata will be overwritten. Note that sufficient space must be
	 * available.</p>
	 *
	 * @param headerMetadata Header metadata to associate with this partition.
	 * @param headerSize Number of bytes the encoded header should fit within, including filler.
	 * @throws NullPointerException Cannot associate <code>null</code> header metadata with this partition.
	 * @throws InsufficientSpaceException Calculations based on partition sizes shows that insufficient space
	 * is available to write this header metadata.
	 * @throws IOException Problem occurred writing to the filesystem.
	 *
	 * @see #readHeaderMetadata()
	 * @see #hasHeaderMetadata()
	 * @see #calculateMinimumHeaderMetadataSize(Preface)
	 */
	public void writeHeaderMetadata(
			Preface preface,
			long headerSize)
		throws NullPointerException,
			InsufficientSpaceException, IOException;

	/**
	 * <p>Reads the {@linkplain IndexTableSegment index table segment} of this partition, if any is
	 * present.</p>
	 *
	 * @return The index data segment of this partition, or <code>null</code> if no index data is
	 * present.
	 *
	 * @throws NullPointerException The partition's MXF file is <code>null</code> which may
	 * occur if this partition is being created unattached.
	 *
	 * @see #hasIndexTable()
	 */
	public IndexTableSegment readIndexTableSegment()
		throws NullPointerException;

	public void writeIndexTableSegment(
			IndexTableSegment indexSegment,
			long segmentLength)
		throws NullPointerException,
			InsufficientSpaceException,
			IOException;

	public void writeSingleIndexSegment(
			IndexTableSegment indexTableSegment)
		throws NullPointerException,
			InsufficientSpaceException,
			IOException;

	/**
	 * <p>Determines whether this partition contains index table data. The partition
	 * may contain a complete index table or just a segment of an index table stream.</p>
	 *
	 * @return Does this partition have index table data?
	 *
	 * @see #readIndexTableSegment()
	 */
	public boolean hasIndexTable();

	/**
	 * <p>Determines whether this partition has an essence container, indicating the
	 * presence of (part of) a body stream.</p>
	 *
	 * @return Does this partition have an essence container?
	 */
	public boolean hasEssenceContainer();

	/**
	 * <p>Returns the initial partition size given for this partition that is used to calculate
	 * the minimum allowed size for the partition. Note that this value does not necessarily reflect
	 * the actual size of the partition.</p>
	 *
	 * @return Initial partition size.
	 *
	 * @see #SIZE_UNKNOWN
	 * @see #getActualSize()
	 */
	public long getInitialSize();

	/**
	 * <p>Sets the initial partition size for this partition that is used to calculate the
	 * minimum allowed size for the partition. Use a value of {@link #SIZE_UNKNOWN} to get MAJ to
	 * calculate the size. Note that the implementation will expand the size
	 * of the partition beyond the minimum given with this method if required. The size is
	 * specified excluding the size of the partition pack.</p>
	 *
	 * @param initialSize Initial partition size.
	 *
	 * @see #SIZE_UNKNOWN
	 * @see #getActualSize()
	 */
	public void setInitialDataSize(
			long initialSize);

	/**
	 * <p>Update the size of partition based on the best information known at this time.</p>
	 *
	 * @see MXFFile#updatePackSizes()
	 */
	public void updateSizes();

	/**
	 * <p>Returns the current size allocated for the partition, including any fill. This size may be
	 * updated during writing.</p>
	 *
	 * @return Current size allocated for the partition.
	 *
	 * @see #getInitialSize()
	 */
	public long getActualSize();

	/**
	 * <p>Create a cloned copy of this partition.</p>
	 *
	 * @return Cloned copy of this partition.
	 */
	public Partition clone();

}
