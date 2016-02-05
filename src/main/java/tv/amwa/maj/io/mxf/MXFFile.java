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
import java.security.NoSuchAlgorithmException;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;

// TODO rationalize whether these methods are relevant and should be public

/**
 * <p>Represents and provides access to the components of an MXF file, including
 * the {@linkplain Partition partitions} that it contains.</p>
 *
 * <p>To open an MXF file and to start reading it, call static method {@link MXFFactory#readPartitions(String)}
 * and then access the data it contains using the methods of this class.</p>
 *
 *
 *
 * @see MXFFactory#readPartitions(String)
 */
public interface MXFFile
	extends Cloneable {

	/**
	 * <p>Open the MXF file with the given name for read-only or read-write as required.</p>
	 *
	 * @param filename Name of the file to open.
	 * @param readOnly Open the file read-only? Set to <code>true</code> for read-only and
	 * <code>false</code> for read-write.
	 * @return Was the file successfully opened and the beginning of the
	 * {@linkplain HeaderPartition header partition} found?
	 *
	 * @throws IOException A problem occurred when trying to open the specified file.
	 *
	 * @see MXFFactory#readPartitions(String)
	 */
	public boolean open(
			String filename,
			boolean readOnly)
		throws IOException;

	/**
	 * <p>Close this MXF file, preventing further access and releasing any associated
	 * resources. Applications should close MXF files as soon as they are finished reading
	 * or writing them.</p>
	 *
	 * @return Was the file successfully closed?
	 */
	public boolean close();

	/**
	 * <p>Returns the length of any run in found in the file. A run-in is optional
	 * in an MXF file.</p>
	 *
	 * @return Length of any run in found in the file.
	 *
	 * @see #hasRunIn()
	 * @see #readRunIn()
	 */
	public @UInt32 int getRunInSize();

	/**
	 * <p>Determines whether this file has an optional run-in.</p>
	 *
	 * @return Does this file have an optional run-in?
	 *
	 * @see #getRunInSize()
	 * @see #readRunIn()
	 */
	public boolean hasRunIn();

	/**
	 * <p>Read the internal pointer passed any run-in to the beginning of the
	 * {@linkplain HeaderPartition header partition}.</p>
	 *
	 * @return Is the position of the MXF file set at the beginning of the header partition, just
	 * beyond the run-in.
	 *
	 * @throws IOException Problem reading the underlying MXF file.
	 *
	 * @see #getRunInSize()
	 * @see #hasRunIn()
	 */
	public boolean readRunIn()
		throws IOException;

	/**
	 * <p>Determine if the internal file pointer at the end of the file?</p>
	 *
	 * @return Is the internal file pointer at the end of the file?
	 */
	public boolean atEOF();

	/**
	 * <p>Build a partition table from this MXF file containing all the byte offsets
	 * of all the {@linkplain Partition partitions} in this MXF file. It is necessary
	 * to call this method before calling any of the other methods that reference
	 * partitions.</p>
	 *
	 * @return Successful creation of the partition table.
	 *
	 * @throws IOException Problem occurred when trying to read the file.
	 * @throws EndOfDataException Unexpectedly reached the end of the file, for example
	 * when reading through a partition table.
	 */
	public boolean buildPartitionsTable()
    	throws IOException,
            EndOfDataException;

	/**
	 * <p>Count the number of {@linkplain Partition partitions} in this file. A value
	 * of zero may indicate that the partitions table still needs to be built with
	 * {@link #buildPartitionsTable()}.</p>
	 *
	 * @return Number of partitions in the file.
	 *
	 * @see #getPartitionAt(int)
	 * @see #buildPartitionsTable()
	 */
	public int countPartitions();

	/**
	 * <p>Returns the {@linkplain Partition partition} at the given index in the partition table.
	 * The partitions are ordered in the sequence they occur in the file, with the partition
	 * at index zero being the {@linkplain HeaderPartition header partition}.</p>
	 *
	 * @param index Index of the partition table to retrieve.
	 * @return Partition at the given index in the index table.
	 *
	 * @throws IndexOutOfBoundsException No parition at the given index, or the partition table
	 * has not yet been built with {@link #buildPartitionsTable()}.
	 *
	 * @see #buildPartitionsTable()
	 */
	public Partition getPartitionAt(
			int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Get access to the header partition of this file. Ensure that the partition
	 * table has been built first with {@link #buildPartitionsTable()}.</p>
	 *
	 * @return Header partition of the file, or <code>null</code> if the file is invalid or
	 * the partitions table has not yet been built.
	 *
	 * @see #getHeaderPartition()
	 * @see #buildPartitionsTable()
	 */
	public HeaderPartition getHeaderPartition();

	/**
	 * <p>Get access to the footer partition of this file. Ensure that the partition
	 * table has been built first with {@link #buildPartitionsTable()} or a <code>null</code>
	 * may be returned unexpectedly.</p>
	 *
	 * @return Footer partition of the file or <code>null</code> if a footer partition is not
	 * found.
	 *
	 * @see #getFooterPartition()
	 * @see #buildPartitionsTable()
	 */
	public FooterPartition getFooterPartition();

	/**
	 * <p>Returns the random index pack from the end of the file, or <code>null</code> if the
	 * file does not have a random index pack.</p>
	 *
	 * @return Random index pack from the end of the file if it exists, otherwise <code>null</code>.
	 *
	 */
	public RandomIndexPack getRandomIndexPack();

	/**
	 * <p>Determines if this MXF file is open and therefore and ready to be read and written to.</p>
	 *
	 * @return Is this MXF file open?
	 *
	 * @see #close()
	 */
	public boolean isOpen();

	// Initial size is data size excluding the pack
	public HeaderPartition addHeaderPartition(
			boolean closed,
			boolean complete,
			long initialSize,
			int bodySID,
			int indexSID,
			UL... containerFormats);

	public BodyPartition addBodyPartition(
			boolean closed,
			boolean complete,
			long initialSize,
			int bodySID,
			int indexSID,
			UL... containerFormats);

	public FooterPartition addFooterPartition(
			boolean complete,
			long initialSize,
			int indexSID,
			UL... containerFormats);

	public void updatePackSizes();

	public boolean buildRIP();

	public boolean writeRIP()
		throws IOException;

	public void setFileOperationalPattern(
			UL operationalPattern)
		throws NullPointerException;

	public void setFileKAG(
			@UInt32 int kag)
		throws IllegalArgumentException;

	public void setFileMXFByteLevelVersion(
			@UInt16 short major,
			@UInt16 short minor)
		throws IllegalArgumentException;

	public boolean lock();

	public boolean lockForward();

	public void unlock();

	public void startDigest(
			String digestType)
		throws NoSuchAlgorithmException;

	public byte[] getDigestValue();

	public long getFileSize()
		throws IOException;

	/**
	 * <p>Returns a cloned copy of the MXF file representation. Note that the
	 * MXF file itself is not copied.</p>
	 *
	 * @return Cloned copy of the MXF file representation.
	 */
	public MXFFile clone();
}
