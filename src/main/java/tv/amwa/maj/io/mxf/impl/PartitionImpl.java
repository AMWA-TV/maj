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
 * $Log: PartitionImpl.java,v $
 * Revision 1.6  2011/08/19 17:56:50  vizigoth
 * Surpressed no longer required println.
 *
 * Revision 1.5  2011/07/27 16:52:59  vizigoth
 * Capable of reading and writing audio essence component files.
 *
 * Revision 1.4  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.3  2010/07/14 13:34:41  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
 *
 * Revision 1.2  2010/01/21 20:51:31  vizigoth
 * Updates to index table support to the point where index table data can be read from MXF files and stream offsets can be calculated.
 *
 * Revision 1.1  2010/01/19 14:44:24  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
 *
 * Revision 1.6  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2009/02/13 15:27:46  vizigoth
 * Replaced DataChunk with core Java ByteBuffer.
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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.io.mxf.BodyPartition;
import tv.amwa.maj.io.mxf.BodyPartitionPack;
import tv.amwa.maj.io.mxf.FixedLengthPack;
import tv.amwa.maj.io.mxf.FooterPartition;
import tv.amwa.maj.io.mxf.FooterPartitionPack;
import tv.amwa.maj.io.mxf.HeaderMetadata;
import tv.amwa.maj.io.mxf.HeaderPartition;
import tv.amwa.maj.io.mxf.HeaderPartitionPack;
import tv.amwa.maj.io.mxf.IndexTable;
import tv.amwa.maj.io.mxf.IndexTableSegment;
import tv.amwa.maj.io.mxf.MXFBuilder;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.Partition;
import tv.amwa.maj.io.mxf.PartitionPack;
import tv.amwa.maj.io.mxf.PrimerPack;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.model.Preface;

/**
 * <p>Data relating to a single MXF partition.</p>
 * 
 *
 *
 */
public abstract class PartitionImpl 
	implements 
		Partition,
		Cloneable {

	private HeaderMetadata headerMetadata = null;
	private IndexTableSegment indexTableSegment = null;
	private long endOfPartitionPack = -1l;
	private MXFFileImpl localMXFFile;
	private long initialSize = -1l;
	private long actualSize = 0l;
	
	public PartitionImpl() { }
	
	public abstract PartitionPack getPartitionPack();
	
	public abstract void setPartitionPackPadding(
			long paddingFillSize)
		throws IllegalArgumentException;
	
	void setEndOfPartitionPack(
			long endOfPartitionPack) {
		
		this.endOfPartitionPack = endOfPartitionPack;
	}
	
	void setMXFFile(
			MXFFileImpl mxfFile) {
		
		this.localMXFFile = mxfFile;
	}
	
	public boolean hasHeaderMetadata() {
		
		return (getPartitionPack().getHeaderByteCount() != 0);
	}
	
	public boolean hasIndexTable() {
		
		return (getPartitionPack().getIndexSID() != 0);
	}
	
	public boolean hasEssenceContainer() {
		
		return (getPartitionPack().getBodySID() != 0);
	}
	
	public Partition clone() {
		
		try {
			return (Partition) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Implements cloneable so should never happen
			throw new InternalError(cnse.getMessage());
		}
	}
	
	public HeaderMetadata readHeaderMetadata() 
		throws NullPointerException {
		
		if (localMXFFile == null)
			throw new NullPointerException("Cannot read a header from a null mxf file.");
			
		if (getPartitionPack().getHeaderByteCount() == 0) {
			this.headerMetadata = null;
			return null;
		}
		
		localMXFFile.seek(endOfPartitionPack);
		
		// TODO consider a limit on buffer size in case of large metadata!
		ByteBuffer primerAndPreface = localMXFFile.read((int) getPartitionPack().getHeaderByteCount());		
		this.headerMetadata = HeaderMetadataImpl.createFromBuffer(primerAndPreface);
		return headerMetadata;		
	}
	
	public void writeHeaderMetadata(
			Preface preface,
			long headerByteCount)
		throws NullPointerException,
			InsufficientSpaceException,
			IOException {
		
		if (localMXFFile == null)
			throw new NullPointerException("Cannot replace the header metadata in a null MXF file.");
		if (preface == null)
			throw new NullPointerException("Cannot replace the header metadata using a null preface.");
		
		long currentHeaderSize = getPartitionPack().getHeaderByteCount();
		if (headerByteCount != currentHeaderSize) {
			// FIXME should allow partitions to expand - this is over cautious
			if (headerByteCount > initialSize)
				throw new InsufficientSpaceException("Not enough room in this partition for the space required by the header metadata.");
			if ((currentHeaderSize == 0) && (!hasEssenceContainer()) && (!hasIndexTable())) {
				getPartitionPack().setHeaderByteCount(headerByteCount);
			}
			else {
				if (headerByteCount > currentHeaderSize) {
					throw new InsufficientSpaceException("Space requested for header metadata is larger than existing header metadata. Please recalculate partition table.");
				}
				headerByteCount = currentHeaderSize;
			}
		}
		
		PrimerPack primerPack = new PrimerPackImpl();
		primerPack.addLocalTagEntry(MXFConstants.InstanceTag, MXFConstants.InstanceUID);
		long lengthOfAllSets = PrimerPackImpl.addPropertiesForObject(primerPack, preface);
		
		if ((lengthOfAllSets + MXFBuilder.lengthOfFixedLengthPack(primerPack)) > headerByteCount)
			throw new InsufficientSpaceException("Not enough space to write the primer pack and header metadata to the file.");

		ByteBuffer primerAndPreface = ByteBuffer.allocate((int) getPartitionPack().getHeaderByteCount());
		
		primerAndPreface.rewind();
		MXFBuilder.writeFixedLengthPack(primerPack, primerAndPreface);
		List<PropertyValue> stillToWrite = new ArrayList<PropertyValue>();
		MXFBuilder.writeLocalSet(preface, primerAndPreface, primerPack, stillToWrite);
		
		while (stillToWrite.size() > 0) {
			PropertyValue headItem = stillToWrite.remove(0);
			MXFBuilder.writeLocalSet(headItem, primerAndPreface, primerPack, stillToWrite);
		}
		
		if (primerAndPreface.remaining() > 24) {
			MXFBuilder.writeKey((UL) MXFConstants.KLVFill, primerAndPreface);
			MXFBuilder.writeBERLength(primerAndPreface.remaining() - 8, 8, primerAndPreface);
		}
		
		while (primerAndPreface.hasRemaining()) {
			primerAndPreface.put((byte) 0);
		}
		
		primerAndPreface.rewind();
		
//		Utilities.hexDump(primerAndPreface);
		
		writePartitionPack();
		
		localMXFFile.seek(endOfPartitionPack);
		localMXFFile.write(primerAndPreface);
	}
	
	private final static PrimerPack indexPrimer = IndexTable.indexPrimer;
	
	public void writeIndexTableSegment(
			IndexTableSegment indexTableSegment,
			long segmentLength) // segment plus fill
		throws NullPointerException,
			InsufficientSpaceException,
			IOException {
		
		if (localMXFFile == null)
			throw new NullPointerException("Cannot replace the header metadata in a null MXF file.");
		if (indexTableSegment == null)
			throw new NullPointerException("Cannot replace the index table metadata using a null index table segment.");
		
		long currentIndexSize = getPartitionPack().getIndexByteCount();
		if (segmentLength != currentIndexSize) {
			// FIXME this is over cautious - should allow partition to expand
			if (segmentLength > initialSize)
				throw new InsufficientSpaceException("Not enough room in this partition for the space required by the index table segment.");
			if ((currentIndexSize == 0) && (!hasHeaderMetadata())) {
				getPartitionPack().setIndexByteCount(segmentLength);
			}
			else {
				if (segmentLength > currentIndexSize) {
					throw new InsufficientSpaceException("Space requested for an index table segment is larger than existing header metadata. Please recalculate partition table.");
				}
				segmentLength = currentIndexSize;
			}
		}

		long indexSegmentSize = MXFBuilder.lengthOfLocalSet(indexTableSegment);
		
		ByteBuffer indexBuffer = ByteBuffer.allocate((int) (indexSegmentSize + 20));
		indexBuffer.rewind();
		MXFBuilder.writeLocalSet(indexTableSegment, indexBuffer, indexPrimer, new ArrayList<PropertyValue>());
		indexBuffer.rewind();
		
//		Utilities.hexDump(indexBuffer);
		
		writePartitionPack();
		
//		System.out.println("Header byte count in index partition is " + getPartitionPack().getHeaderByteCount());
		
		localMXFFile.seek(endOfPartitionPack + getPartitionPack().getHeaderByteCount());
		localMXFFile.write(indexBuffer);		
	}
	
	public void writeSingleIndexSegment(
			IndexTableSegment indexTableSegment) 
		throws NullPointerException,
			InsufficientSpaceException,
			IOException {
		
		if (localMXFFile == null)
			throw new NullPointerException("Cannot replace the header metadata in a null MXF file.");
		if (indexTableSegment == null)
			throw new NullPointerException("Cannot replace the index table metadata using a null index table segment.");

		long indexSegmentSize = MXFBuilder.lengthOfLocalSet(indexTableSegment);
		
		ByteBuffer indexBuffer = ByteBuffer.allocate((int) (indexSegmentSize + 20));
		indexBuffer.rewind();
		MXFBuilder.writeLocalSet(indexTableSegment, indexBuffer, indexPrimer, new ArrayList<PropertyValue>());
		indexBuffer.rewind();

		localMXFFile.write(indexBuffer);				
	}
	
	public void writePartitionPack() 
		throws NullPointerException, 
			InsufficientSpaceException,
			IOException {
		
		ByteBuffer partitionPackBuffer = ByteBuffer.allocate(getPartitionPack().getEncodedSize() + 20);
		
		PartitionPack thePack = getPartitionPack();
		ClassDefinition theClass = MediaEngine.getClassDefinition(thePack);
		MXFBuilder.writeKey((UL) theClass.getAUID(), partitionPackBuffer);
		MXFBuilder.writeBERLength(thePack.getEncodedSize(), 4, partitionPackBuffer);
		
		MXFBuilder.writeFixedLengthPack((FixedLengthPack) getPartitionPack(), partitionPackBuffer);
		
		// Utilities.hexDump(partitionPackBuffer);
		
		partitionPackBuffer.rewind();
		localMXFFile.seek(getPartitionPack().getThisPartition());
		localMXFFile.write(partitionPackBuffer);
		endOfPartitionPack = getPartitionPack().getThisPartition() + getPartitionPack().getEncodedSize() + 20;
	}
	
	public IndexTableSegment readIndexTableSegment() 
		throws NullPointerException {
		
		if (localMXFFile == null)
			throw new NullPointerException("Cannot read a header from a null mxf file.");

		if (!hasIndexTable()) {
			this.indexTableSegment = null;
			return null;
		}
		
		localMXFFile.seek(endOfPartitionPack + getPartitionPack().getHeaderByteCount());
		
		// TODO consider whether reading in an entire index table at one time is sensible?
		ByteBuffer indexBuffer = localMXFFile.read((int) getPartitionPack().getIndexByteCount());
		this.indexTableSegment = IndexTableSegmentImpl.createFromBuffer(indexBuffer);
		return indexTableSegment;
	}
	
	protected MXFFileImpl getMXFFile() {
		
		return localMXFFile;
	}
	
	protected long getEndOfParitionPack() {
		
		return endOfPartitionPack;
	}
	
	public long getInitialSize() {
		
		return initialSize;
	}
	
	public void setInitialDataSize(
			long initialSize) {
		
		this.initialSize = initialSize;
	}
	
	public long getActualSize() {
		
		return actualSize;
	}
	
	public void setActualSize(
			long actualSize) {
		
		this.actualSize = actualSize;
	}
	
	public void updateSizes() {
		
		int packSize = getPartitionPack().getEncodedSize() + 20;
		long maxKnownSize = packSize + getPartitionPack().getHeaderByteCount() + getPartitionPack().getIndexByteCount();
		long initialPlusPack = initialSize + packSize;
		actualSize = (initialPlusPack > maxKnownSize) ? initialPlusPack : maxKnownSize;
	}
	
	public final static Partition partitionFactory(
			MXFFileImpl mxfFile) throws EndOfDataException {
		
		PartitionPack partitionPack = PartitionPackImpl.readPartitionPack(mxfFile);
		Partition createdPartition = null;
		
		if (partitionPack instanceof HeaderPartitionPack) {
			HeaderPartition headerPartition = new HeaderPartitionImpl();
			headerPartition.setPartitionPack((HeaderPartitionPack) partitionPack);
			createdPartition = headerPartition;
		}
		
		if (partitionPack instanceof BodyPartitionPack) {
			BodyPartition bodyPartition = new BodyPartitionImpl();
			bodyPartition.setPartitionPack((BodyPartitionPack) partitionPack);
			createdPartition = bodyPartition;
		}
		
		if (partitionPack instanceof FooterPartitionPack) {
			FooterPartition footerPartition = new FooterPartitionImpl();
			footerPartition.setPartitionPack((FooterPartitionPack) partitionPack);
			createdPartition = footerPartition;
		}
		
		if (createdPartition == null)
			return null;
		
		createdPartition.setPartitionPackPadding(MXFBuilder.skipKLVFill(mxfFile));
		
		((PartitionImpl) createdPartition).setEndOfPartitionPack(mxfFile.tell());
		((PartitionImpl) createdPartition).setMXFFile(mxfFile);
		return createdPartition;
	}
	
	public String toString() {
		
		return "Partition details: \n" + getPartitionPack().toString() + "\n";
	}
}
