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

/*
 * $Log: RandomIndexPackImpl.java,v $
 * Revision 1.4  2011/07/27 16:52:59  vizigoth
 * Capable of reading and writing audio essence component files.
 *
 * Revision 1.3  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/04/13 10:23:31  vizigoth
 * Initial adding of write bytes methods.
 *
 * Revision 1.1  2010/01/19 14:44:24  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
 *
 * Revision 1.3  2009/02/13 14:27:29  vizigoth
 * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
 *
 * Revision 1.2  2009/02/06 17:01:31  vizigoth
 * Conversion of C headers to fields and stubs.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf.impl;

import java.io.Serializable;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.integer.UInt64Array;
import tv.amwa.maj.io.mxf.MXFBuilder;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.RandomIndexItem;
import tv.amwa.maj.io.mxf.RandomIndexPack;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.mxf.UnitType;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>Random index pack data for an {@linkplain MXFFileImpl MXF file}.</p>
 * 
 *
 * 
 * @see PartitionImpl
 * @see PartitionInformation
 *
 */
@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0111, uuid3 = 0x0100,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01},
		definedName = "RandomIndexPack",
		description = "A device to help find partitions scattered throughout an MXF file.",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX,
		symbol = "RandomIndexPack")
public class RandomIndexPackImpl 
	implements MetadataObject,
		Serializable,
		Cloneable, 
		RandomIndexPack {

	private static final long serialVersionUID = -4119168629997789616L;

	private SortedSet<RandomIndexItem> partitionOrder =
		new TreeSet<RandomIndexItem>();
	private Map<Integer, SortedSet<RandomIndexItem>> partitionTable =
		 new HashMap<Integer, SortedSet<RandomIndexItem>>();
	
	public RandomIndexPackImpl() {}

	@MediaProperty(
			uuid1 = 0x0f000000 , uuid2 = 0x0000, uuid3 = 0x0073,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00},
			definedName = "PartitionIndex",
			typeName = "RandomIndexItemArray",
			optional = false,
			uniqueIdentifier = false,
			symbol = "PartitionIndex")
	public RandomIndexItem[] getPartitionIndex() {
		
		RandomIndexItem[] currentTable = new RandomIndexItem[partitionOrder.size()];
		
		int x = 0;
		for ( RandomIndexItem item : partitionOrder )
			currentTable[x++] = item.clone();
		
		return currentTable;
	}
	
	@MediaPropertySetter("PartitionIndex")
	public void setPartitionIndex(
			RandomIndexItem[] partitionIndex) 
		throws NullPointerException {
		
		if (partitionIndex == null)
			throw new NullPointerException("Cannot set the partition index witn a null value.");
		
		clear();
		for ( RandomIndexItem item : partitionIndex ) 
			addRandomIndexItem(item);
	}
	
	public @UInt64Array long[] lookupPartitionOffsets(
			@UInt32 int bodySID) {
		
		if (!partitionTable.containsKey(bodySID)) return null;
		
		SortedSet<RandomIndexItem> streamSet = partitionTable.get(bodySID);
		long[] streamPartitions = new long[streamSet.size()];
		int index = 0;
		for ( RandomIndexItem item : streamSet )
			streamPartitions[index++] = item.getByteOffset();
		
		return streamPartitions;
	}

	@MediaPropertyCount("PartitionIndex")
	public int count() {
		
		return partitionOrder.size();
	}
	
	@MediaPropertyClear("PartitionIndex")
	public void clear() {
		
		partitionOrder.clear();
		partitionTable.clear();
	}
	
	public void addRandomIndexItem(
			@UInt32 int bodySID,
			@UInt64 long byteOffset) 
		throws IllegalArgumentException {
		
		addRandomIndexItem(new RandomIndexItemImpl(bodySID, byteOffset));
	}
	
	public void addRandomIndexItem(
			RandomIndexItem randomPartitionItem) 
		throws NullPointerException {

		if (randomPartitionItem == null)
			throw new NullPointerException("Cannot put a null entry into the random index pack table.");
		
		RandomIndexItem clonedItem = randomPartitionItem.clone();
		partitionOrder.add(clonedItem);
		
		SortedSet<RandomIndexItem> streamSet = null;
		if (partitionTable.containsKey(clonedItem.getBodySID()))
			streamSet = partitionTable.get(clonedItem.getBodySID());
		else
			streamSet = new TreeSet<RandomIndexItem>();
		streamSet.add(clonedItem);
		partitionTable.put(clonedItem.getBodySID(), streamSet);
	}
	
	@MediaProperty(
			uuid1 = 0x04061001, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "Length",
			typeName = "UInt32",
			uniqueIdentifier = false,
			optional = false,
			symbol = "Length")
	public @UInt32 int getLength() {
		
		// Assumes BER 4 byte length max length 16M
		return partitionOrder.size() * 12 + 24;
	}
	
	@MediaPropertySetter("Length")
	public void setLength(
			@UInt32 int length)
		throws IllegalArgumentException { 
		
		if (partitionOrder.size() > 0)
			throw new IllegalArgumentException("The length of a random index pack is automnatically maintained.");
	}
	
	public String toString() {
		
		return MediaEngine.toString(this);
	}
	
	public boolean equals(
			Object o) {
		
		return MediaEngine.equals(this, o);
	}
	
	public int hashCode() {
		
		return MediaEngine.hashCode(this);
	}
	
	public RandomIndexPack clone() {
		
		try {
			return (RandomIndexPack) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Is cloneable, so should never get here
			throw new InternalError(cnse.getMessage());
		}
	}

	public int writeAsBytes(
			ByteBuffer buffer) 
		throws NullPointerException,
			InsufficientSpaceException {
		
		if (buffer == null)
			throw new NullPointerException("Cannot write a random index pack into a null buffer.");
		
		if (getLength() > buffer.remaining())
			throw new InsufficientSpaceException("Insufficient space to write this random index pack into the given buffer.");
		
		MXFBuilder.writeKey(ripKeyValue, buffer);
		MXFBuilder.writeBERLength(partitionOrder.size() * 12 + 4, 4, buffer);
		
		for ( RandomIndexItem item : partitionOrder ) {
			buffer.putInt(item.getBodySID());
			buffer.putLong(item.getByteOffset());
		}
		
		buffer.putInt(getLength());
		
		return getLength();
	}
	
	@Override
	public UnitType getUnitType() {
		
		return UnitType.RandomIndexPack;
	}
		
	public static RandomIndexPack createFromBytes(
			ByteBuffer ripBytes) 
		throws BufferUnderflowException {

		UL ripKey = MXFBuilder.readKey(ripBytes);
		if (ripKey == null)
			return null;
		if (!ripKeyValue.equals(ripKey))
			return null;
		
		long ripLength = MXFBuilder.readBERLength(ripBytes);
		int ripItemCount = (int) (ripLength - 4) / 12;
		// If remainder, the random index pack is corrupt in some way.
		if (((ripLength - 4) % 12) != 0) return null;
		
		RandomIndexItem[] itemArray = new RandomIndexItem[ripItemCount];
		
		for ( int x = 0 ; x < ripItemCount ; x++ )
			itemArray[x] = new RandomIndexItemImpl(ripBytes.getInt(), ripBytes.getLong());
			
		RandomIndexPack rip = new RandomIndexPackImpl();
		rip.setPartitionIndex(itemArray);
		
		// Read past length value
		ripBytes.getInt();
		
		return rip;
	}
	
	public final static int initializeLength() {
		
		return 24;
	}
	
	public final static RandomIndexItem[] initializePartitionIndex() {
		
		return new RandomIndexItem[0];
	}
}
