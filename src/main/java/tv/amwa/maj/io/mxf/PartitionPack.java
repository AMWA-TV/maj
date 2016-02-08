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
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

// TODO add IllegalArgumentException to all setters of unsigned values

/**
 * <p>Represents a partition pack that describes and starts every {@linkplain Partition partition} of
 * an MXF file. The description includes:</p>
 *
 * <ul>
 *  <li>the size of each of the partitions constituent parts;</li>
 *  <li>the partitions offset in the overall file, measured from the beginning of
 *  the {@linkplain HeaderPartition header partition};</li>
 *  <li>the identities of any index or body streams contained in the partition;</li>
 *  <li>some additional information that may be of use to a decoder, such as operational pattern
 *  and kinds of essence found in the partition.</li>
 * </ul>
 *
 * <p>With the exception of the {@linkplain #getBodyOffset() body offset}, all byte offsets
 * are measured from the start of the {@linkplain HeaderPartition header partition}.
 * If the file has no run-in, this is the first byte of the file.</p>
 *
 * <p>The header partition starts at the end of the partition pack, found when decoding the
 * length of the KLV data set that represents the parition pack. The index table starts
 * at the end of the partition pack plus the {@linkplain #getHeaderByteCount() size of any
 * header metadata}.</p>
 *
 *
 *
 * @see Partition#getPartitionPack()
 */
public interface PartitionPack
	extends
		MetadataObject,
		Cloneable,
		MXFUnit,
		Padded {

	/**
	 * <p>Default value for the {@linkplain #getMajorVersion() major version property},
	 * which is {@value #MAJORVERSION_DEFAULT}.</p>
	 *
	 * @see #getMajorVersion()
	 * @see #setMajorVersion(short)
	 */
	public final static short MAJORVERSION_DEFAULT = (short) 0x001;

	/**
	 * <p>Default value for the {@linkplain #getMinorVersion() minor version property},
	 * which is {@value #MINORVERSION_DEFAULT}.</p>
	 *
	 * @see #getMinorVersion()
	 * @see #setMinorVersion(short)
	 */
	public final static short MINORVERSION_DEFAULT = (short) 0x003;

	/**
	 * <p>Default value for the byte offset from the start of the file to the
	 * beginning of the previous partition, which is {@value #PREVIOUSPARTITION_DEFAULT}.</p>
	 *
	 * @see #getPreviousPartition()
	 * @see #setPreviousPartition(long)
	 */
	public final static long PREVIOUSPARTITION_DEFAULT = 0l;

	/**
	 * <p>Default value for the byte offset to the
	 * beginning of the footer partition, which is {@value #FOOTERPARTITION_DEFAULT}.</p>
	 */
	public final static long FOOTERPARTITION_DEFAULT = 0l;

	/**
	 * <p>Default value for the size of any {@linkplain HeaderMetadata header metadata}
	 * in the partition, which is {@value #HEADERBYTECOUNT_DEFAULT}. A value of zero
	 * indicates that no header metadata is present in this partition.</p>
	 *
	 * @see #getHeaderByteCount()
	 * @see #setHeaderByteCount(long)
	 */
	public final static long HEADERBYTECOUNT_DEFAULT = 0l;

	/**
	 * <p>Default value for the size of any index table data in the partition,
	 * which is {@value #INDEXBYTECOUNT_DEFAULT}.</p>
	 *
	 * @see #getIndexByteCount()
	 * @see #setIndexByteCount(long)
	 */
	public final static long INDEXBYTECOUNT_DEFAULT = 0l;

	/**
	 * <p>Default value for the identity of any stream of index table data
	 * in this partition, which is {@value #INDEXSID_DEFAULT}. A value of zero
	 * indicates that no index table data is present for this partition.</p>
	 *
	 * @see #getIndexSID()
	 * @see #setIndexSID(int)
	 */
	public final static int INDEXSID_DEFAULT = 0;

	/**
	 * <p>Default value for the identity of any stream of essence (body) in
	 * this partition, which is {@value #BODYSID_DEFAULT}. A value of zero
	 * indicates that no essence container data is present for this
	 * partition.</p>
	 *
	 * @see #getBodySID()
	 * @see #setBodySID(int)
	 */
	public final static int BODYSID_DEFAULT = 0;

	/**
	 * <p>Returns the major version number of the MXF byte-level format. A change
	 * in number is a warning that the file may not be backward compatible.</p>
	 *
	 * @return Major version number of the MXF byte-level format.
	 *
	 * @see #MAJORVERSION_DEFAULT
	 * @see #getMinorVersion()
	 */
	public @UInt16 short getMajorVersion() ;

	/**
	 * <p>Sets the major version number of the MXF byte-level format. A change
	 * in number is a warning that the file may not be backward compatible.</p>
	 *
	 * @param majorVersion Major version number of the MXF byte-level format.
	 *
	 * @see #MAJORVERSION_DEFAULT
	 * @see #setMinorVersion(short)
	 */
	public void setMajorVersion(
			@UInt16 short majorVersion);

	/**
	 * <p>Returns the minor version number of the MXF byte-level format. Newer versions
	 * indicated by this number should be backward-compatible with previous versions.</p>
	 *
	 * @return Minor version number of the MXF byte-level format.
	 *
	 * @see #MINORVERSION_DEFAULT
	 * @see #getMajorVersion()
	 */
	public @UInt16 short getMinorVersion();

	/**
	 * <p>Sets the minor version number of the MXF byte-level format. Newer versions
	 * indicated by this number should be backward-compatible with previous versions.</p>
	 *
	 * @param minorVersion Minor version number of the MXF byte-level format.
	 *
	 * @see #MINORVERSION_DEFAULT
	 * @see #setMajorVersion(short)
	 */
	public void setMinorVersion(
			@UInt16 short minorVersion);

	/**
	 * <p>Returns the key alignment grid size for KLV data triplets in this
	 * partition.</p>
	 *
	 * @return Key alignment grid size for KLV data triplets in this
	 * partition.
	 */
	public @UInt32 int getKagSize();

	/**
	 * <p>Sets the key alignment grid size for KLV data triplets in this
	 * partition.</p>
	 *
	 * @param kagSize Key alignment grid size for KLV data triplets in this
	 * partition.
	 */
	public void setKagSize(
			@UInt32 int kagSize);

	/**
	 * <p>Returns the byte offset of this partition in the file.</p>
	 *
	 * @return Byte offset of this partition in the file.
	 *
	 * @see #getPreviousPartition()
	 * @see #getFooterPartition()
	 */
	public @UInt64 long getThisPartition();

	/**
	 * <p>Returns the byte offset of this partition in the file.</p>
	 *
	 * @param thisPartition Byte offset of this partition in the file.
	 *
	 * @see #setPreviousPartition(long)
	 * @see #setFooterPartition(long)
	 */
	public void setThisPartition(
			@UInt64 long thisPartition);

	/**
	 * <p>Returns the byte offset of the previous partition in the file.</p>
	 *
	 * @return Byte offset of the previous partition in the file.
	 *
	 * @see #PREVIOUSPARTITION_DEFAULT
	 * @see #getThisPartition()
	 * @see #getFooterPartition()
	 */
	public @UInt64 long getPreviousPartition();

	/**
	 * <p>Sets the byte offset of the previous partition in the file to this one.</p>
	 *
	 * @param previousPartition byte offset of the previous partition in the file to this one.
	 *
	 * @see #PREVIOUSPARTITION_DEFAULT
	 * @see #setPreviousPartition(long)
	 * @see #setThisPartition(long)
	 */
	public void setPreviousPartition(
			@UInt64 long previousPartition);

	/**
	 * <p>Returns the byte offset of the footer partition in the file.</p>
	 *
	 * @return Byte offset of the footer partition in the file.
	 *
	 * @see #FOOTERPARTITION_DEFAULT
	 * @see #getThisPartition()
	 * @see #getPreviousPartition()
	 *
	 */
	public @UInt64 long getFooterPartition();

	/**
	 * <p>Sets the byte offset of the footer partition in the file.</p>
	 *
	 * @param footerPartition Byte offset of the footer partition in the file.
	 *
	 * @see #FOOTERPARTITION_DEFAULT
	 * @see #setThisPartition(long)
	 * @see #setPreviousPartition(long)
	 */
	public void setFooterPartition(
			@UInt64 long footerPartition);

	/**
	 * <p>Returns the number of bytes used for {@linkplain HeaderMetadata header metadata}
	 * in this partition. The value includes any {@linkplain PrimerPack primer pack},
	 * metadata sets and trailing fill. A value of zero indicates that no header metadata
	 * is present in this partition.</p>
	 *
	 * @return Number of bytes used for {@linkplain HeaderMetadata header metadata}
	 * in this partition.
	 *
	 * @see #HEADERBYTECOUNT_DEFAULT
	 */
	public @UInt64 long getHeaderByteCount();

	/**
	 * <p>Sets the number of bytes used for {@linkplain HeaderMetadata header metadata}
	 * in this partition. The value includes any {@linkplain PrimerPack primer pack},
	 * metadata sets and trailing fill. A value of zero indicates that no header metadata
	 * is present in this partition.</p>
	 *
	 * @param headerByteCount Number of bytes used for {@linkplain HeaderMetadata header metadata}
	 * in this partition.
	 *
	 * @see #HEADERBYTECOUNT_DEFAULT
	 * @see #setIndexByteCount(long)
	 */
	public void setHeaderByteCount(
			@UInt64 long headerByteCount);

	/**
	 * <p>Returns the number of bytes used for {@linkplain IndexTableSegment index table segments}
	 * in this partition. This includes the key for the first index table segment and any
	 * trailing fill.</p>
	 *
	 * <p>Note that the presence of absence of index table data is determined from the
	 * {@linkplain #getIndexSID() index SID value}.</p>
	 *
	 * @return Number of bytes used for {@linkplain IndexTableSegment index table segments}
	 * in this partition.
	 *
	 * @see #INDEXBYTECOUNT_DEFAULT
	 * @see #getHeaderByteCount()
	 * @see #getIndexSID()
	 */
	public @UInt64 long getIndexByteCount();

	/**
	 * <p>Sets the number of bytes used for {@linkplain IndexTableSegment index table segments}
	 * in this partition. This includes the key for the first index table segment and any
	 * trailing fill.</p>
	 *
	 * <p>Note that the presence of absence of index table data is determined from the
	 * {@linkplain #getIndexSID() index SID value}.</p>
	 *
	 * @param indexByteCount Number of bytes used for {@linkplain IndexTableSegment index table segments}
	 * in this partition.
	 *
	 * @see #INDEXBYTECOUNT_DEFAULT
	 * @see #setHeaderByteCount(long)
	 */
	public void setIndexByteCount(
			@UInt64 long indexByteCount);

	/**
	 * <p>Returns the identifier of {@linkplain IndexTableSegment index table segments} in
	 * this partition. A value of zero indicates that no index table data is present for this
	 * partition.</p>
	 *
	 * @return Identifier of {@linkplain IndexTableSegment index table segments} in
	 * this partition.
	 *
	 * @see #INDEXSID_DEFAULT
	 * @see #getIndexByteCount()
	 */
	public @UInt32 int getIndexSID();

	/**
	 * <p>Sets the identifier of {@linkplain IndexTableSegment index table segments} in
	 * this partition. A value of zero indicates that no index table data is present for this
	 * partition.</p>
	 *
	 * @param indexSID Identifier of {@linkplain IndexTableSegment index table segments} in
	 * this partition.
	 *
	 * @see #INDEXSID_DEFAULT
	 * @see #setIndexByteCount(long)
	 */
	public void setIndexSID(
			int indexSID);

	/**
	 * <p>Returns the byte offset to the {@linkplain EssenceContainer essence container} in this partition
	 * from the start of the partition pack.</p>
	 *
	 * @return Byte offset to the {@linkplain EssenceContainer essence container} in this partition
	 * from the start of the partition pack.
	 *
	 * @see #getBodySID()
	 */
	public @UInt64 long getBodyOffset();

	/**
	 * <p>Sets byte offset to the {@linkplain EssenceContainer essence container} in this partition
	 * from the start of the partition pack.</p>
	 *
	 * @param bodyOffset Byte offset to the {@linkplain EssenceContainer essence container} in this partition
	 * from the start of the partition pack.
	 *
	 * @see #setBodySID(int)
	 */
	public void setBodyOffset(
			@UInt64 long bodyOffset);

	/**
	 * <p>Returns the identifier of the {@linkplain EssenceContainer essence container} in this partition.
	 * A value of zero indicates that no essence container data is present for this partition.</p>
	 *
	 * @return Identifier of the {@linkplain EssenceContainer essence container} in this partition.
	 *
	 * @see #BODYSID_DEFAULT
	 * @see #getIndexSID()
	 * @see #getBodyOffset()
	 */
	public @UInt32 int getBodySID();

	/**
	 * <p>Sets the identifier of the {@linkplain EssenceContainer essence container} in this partition.
	 * A value of zero indicates that no essence container data is present for this partition.</p>
	 *
	 * @param bodySID Identifier of the {@linkplain EssenceContainer essence container} in this partition.
	 *
	 * @see #BODYSID_DEFAULT
	 * @see #setIndexSID(int)
	 * @see #setBodyOffset(long)
	 */
	public void setBodySID(
			@UInt32 int bodySID);

	// UL copied from Preface.OperationalPattern
	/**
	 * <p>Returns the operational pattern of the {@linkplain MXFFile MXF file}.</p>
	 *
	 * @return Operational pattern of the {@linkplain MXFFile MXF file}.
	 *
	 * @see tv.amwa.maj.model.Preface#getOperationalPattern()
	 * @see tv.amwa.maj.constant.OperationalPatternConstant
	 */
	public AUID getOperationalPattern();

	/**
	 * <p>Sets the operational pattern of the {@linkplain MXFFile MXF file} containing
	 * this partition.</p>
	 *
	 * @param auid Operational pattern of the {@linkplain MXFFile MXF file}.
	 *
	 * @see tv.amwa.maj.model.Preface#setOperationalPattern(AUID)
	 * @see tv.amwa.maj.constant.OperationalPatternConstant
	 */
	public void setOperationalPattern(
			AUID auid);

	// UL copied from Preface.EssenceContainers
	/**
	 * <p>Returns a set of identifiers for the kinds of essence found in or referenced
	 * by the {@linkplain MXFFile MXF file} containing this partition.</p>
	 *
	 * @return Set of identifiers for the kinds of essence found in or referenced
	 * by the {@linkplain MXFFile MXF file}.
	 *
	 * @see #addEssenceContainer(AUID)
	 * @see tv.amwa.maj.model.Preface#getEssenceContainers()
	 * @see tv.amwa.maj.constant.ContainerConstant
	 * @see tv.amwa.maj.model.ContainerDefinition
	 */
	public Set<AUID> getEssenceContainers();

	/**
	 * <p>Sets a set of identifiers for the kinds of essence found in or referenced
	 * by the {@linkplain MXFFile MXF file} containing this partition.</p>
	 *
	 * @param essenceContainers Set of identifiers for the kinds of essence found in or referenced
	 * by the {@linkplain MXFFile MXF file}.
	 *
	 * @see #addEssenceContainer(AUID)
	 * @see tv.amwa.maj.model.Preface#getEssenceContainers()
	 * @see tv.amwa.maj.constant.ContainerConstant
	 * @see tv.amwa.maj.model.ContainerDefinition
	 */
	public void setEssenceContainers(
			Set<AUID> essenceContainers);

	/**
	 * <p>Add an identifier for the kinds of essence found in or referenced by the
	 * {@linkplain MXFFile MXF file} containing this partition.</p>
	 *
	 * @param essenceContainerID Identifier for a kind of essence found of referenced by the
	 * {@linkplain MXFFile MXF file}.
	 *
	 * @throws NullPointerException Cannot add a <code>null</code> identifier to the set of
	 * container essence types.
	 *
	 * @see #getEssenceContainers()
	 * @see tv.amwa.maj.model.Preface#getEssenceContainers()
	 * @see tv.amwa.maj.constant.ContainerConstant
	 * @see tv.amwa.maj.model.ContainerDefinition
	 */
	public void addEssenceContainer(
			AUID essenceContainerID)
		throws NullPointerException;

	/**
	 * <p>Calculates the number of bytes it will take to encode this partition pack.</p>
	 *
	 * @return Number of bytes required to encode this partition pack.
	 */
	public int getEncodedSize();

	/**
	 * <p>Create a cloned copy of this partition pack.</p>
	 *
	 * @return Cloned copy of this partition pack.
	 */
	public PartitionPack clone();

}
