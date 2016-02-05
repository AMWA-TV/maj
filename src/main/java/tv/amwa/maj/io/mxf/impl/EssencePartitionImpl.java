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

package tv.amwa.maj.io.mxf.impl;

import java.io.IOException;
import java.nio.ByteBuffer;

// import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

// import sun.security.action.GetBooleanAction;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.io.mxf.EssenceContainer;
import tv.amwa.maj.io.mxf.EssenceElement;
import tv.amwa.maj.io.mxf.EssencePartition;
import tv.amwa.maj.io.mxf.MXFBuilder;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.Partition;
import tv.amwa.maj.io.mxf.PartitionPack;
import tv.amwa.maj.io.mxf.UL;

public abstract class EssencePartitionImpl
	extends
		PartitionImpl
	implements
		EssencePartition,
		MetadataObject {

	private long elementOffset = 0l;
	private EssenceContainer essenceContainer;
	private long essenceBytesWritten = 0l;

	public EssenceContainer getEssenceContainer() {

		// TODO
		return null;
	}

	public EssenceElement readEssenceElement() {

		MXFFileImpl file = getMXFFile();
		PartitionPack partitionPack = getPartitionPack();

		if (partitionPack.getBodySID() == 0) return null;

		long elementPosition = 0;
		Partition nextPartition = file.getNextPartition(this);

		long nextPartitionStart =  nextPartition.getPartitionPack().getThisPartition();
		UL elementKey = (UL) Forge.nilAUID();
		long length = 0;
		ByteBuffer data = null;

		long startOfBody = getEndOfParitionPack() + partitionPack.getBodyOffset();
		elementPosition = startOfBody + elementOffset;
		file.seek(elementPosition);

		while (!isEssenceKey(elementKey)) {

			elementPosition = startOfBody + elementOffset;
			if ((elementPosition + 16) >= nextPartitionStart) {
				file.seek(nextPartitionStart);
				return null;
			}
			elementKey = file.readKey();
			length = file.readBER();
			data = file.read((int) length);
			elementOffset = file.tell() - startOfBody;
		}

		if (elementKey.equals(CPSystemItemImpl.key))
			return CPSystemItemImpl.make(data);

		if (EssenceElementImpl.isEssenceElement(elementKey))
			return EssenceElementImpl.make(elementKey, data);

		return null;
	}

	boolean isEssenceKey(
			UL key) {

		if (key.equals(CPSystemItemImpl.key)) return true;
		if (EssenceElementImpl.isEssenceElement(key)) return true;

		return false;
	}

	public void initializeWritingBody()
		throws NullPointerException,
			InsufficientSpaceException,
			IOException {

		writePartitionPack();
		getMXFFile().seek(getEndOfParitionPack());
		essenceBytesWritten = 0l;
	}

	public void writeEssenceElementHeader(
			byte itemType,
			byte elementType,
			byte elementCount,
			byte elementItem,
			long length)
		throws IOException {

		byte[] keyBytes = ((UL) EssenceElement.essenceElementBase).getUniversalLabel();
		keyBytes[EssenceElement.ITEM_TYPE_INDEX] = itemType;
		keyBytes[EssenceElement.ELEMENT_TYPE_INDEX] = elementType;
		keyBytes[EssenceElement.ELEMENT_COUNT_INDEX] = elementCount;
		keyBytes[EssenceElement.ELEMENT_NUMBER_INDEX] = elementItem;

		ByteBuffer essenceHeaderBytes = ByteBuffer.allocate(24);
		essenceHeaderBytes.rewind();
		essenceHeaderBytes.put(keyBytes);

		MXFBuilder.writeBERLength(length, 8, essenceHeaderBytes);
		essenceHeaderBytes.rewind();

		getMXFFile().write(essenceHeaderBytes);

		essenceBytesWritten += 24;
	}

	public void writeEssenceElementHeader(
			int trackNumber,
			long length)
		throws IOException {

		writeEssenceElementHeader(
				(byte) (trackNumber >>> 24 & 255),
				(byte) (trackNumber >>> 8 & 255),
				(byte) (trackNumber >>> 16 & 255),
				(byte) (trackNumber & 255),
				length);
	}

	public void writeEssenceBlock(
			ByteBuffer essence)
		throws IOException {

		long aboutToWrite = essence.remaining();
		getMXFFile().write(essence);
		essenceBytesWritten += aboutToWrite;
	}

	public long fillToEnd()
		throws IOException {

		long fillMeUpSize = (getEndOfParitionPack() + getInitialSize()) - getMXFFile().tell();
		if (fillMeUpSize > 24) {
			ByteBuffer fillBuffer = ByteBuffer.allocate((int) fillMeUpSize);
			MXFBuilder.writeKey((UL) MXFConstants.KLVFill, fillBuffer);
			MXFBuilder.writeBERLength(fillBuffer.remaining() - 8, 8, fillBuffer);
			fillBuffer.rewind();
			getMXFFile().write(fillBuffer);
		}

		return fillMeUpSize;
	}

	public void rewind() {

		elementOffset = 0l;
	}

	@Override
	public void updateSizes() {

		int packSize = getPartitionPack().getEncodedSize() + 20;
		long maxKnownSize = packSize + getPartitionPack().getIndexByteCount() + getPartitionPack().getHeaderByteCount() + essenceBytesWritten;
		long initialPlusPack = getInitialSize() + packSize;
		setActualSize((initialPlusPack > maxKnownSize) ? initialPlusPack : maxKnownSize);
	}

	public EssencePartition clone() {

		return (EssencePartition) super.clone();
	}

}
