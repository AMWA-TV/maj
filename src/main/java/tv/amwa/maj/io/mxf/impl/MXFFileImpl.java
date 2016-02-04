/*
 * Copyright 2016 Advanced Media Workflow Assocation
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
 * $Log: MXFFileImpl.java,v $
 * Revision 1.8  2011/08/19 17:57:12  vizigoth
 * Surpressed no longer required println.
 *
 * Revision 1.7  2011/07/27 16:52:59  vizigoth
 * Capable of reading and writing audio essence component files.
 *
 * Revision 1.6  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.5  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/07/14 13:34:41  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
 *
 * Revision 1.3  2010/04/13 10:20:36  vizigoth
 * Fix to use AUID rather than AUIDImpl when reading primer packs.
 *
 * Revision 1.2  2010/03/19 16:20:55  vizigoth
 * General commit to sync with repository.
 *
 * Revision 1.1  2010/01/19 14:44:24  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
 *
 * Revision 1.7  2009/12/18 17:11:32  vizigoth
 * Test commit - first in a while. Added readPrefaceFromMXF method.
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
 * Initial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.io.mxf.BodyPartition;
import tv.amwa.maj.io.mxf.FooterPartition;
import tv.amwa.maj.io.mxf.HeaderPartition;
import tv.amwa.maj.io.mxf.KLVObject;
import tv.amwa.maj.io.mxf.MXFBuilder;
import tv.amwa.maj.io.mxf.MXFFile;
import tv.amwa.maj.io.mxf.MXFLength;
import tv.amwa.maj.io.mxf.MXFPosition;
import tv.amwa.maj.io.mxf.Partition;
import tv.amwa.maj.io.mxf.PartitionPack;
import tv.amwa.maj.io.mxf.PrimerPack;
import tv.amwa.maj.io.mxf.RandomIndexItem;
import tv.amwa.maj.io.mxf.RandomIndexPack;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.model.impl.PrefaceImpl;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>Description of an MXF file.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class MXFFileImpl 
	implements 
		MXFFile,
		Cloneable {

	/** Size of data chunk to allocate when dealing with memory files. */
	final static int ALLOCATION_SIZE = 65536;
	
	/** Is the file open? */
	private boolean isOpen = false;
	/** Is this a memory resident <em>file</em>? */
	private boolean isMemoryFile = false;
	/** Reference to the actual file. */
	private File fileHandle;
	/** File channel created for reading and writing. */
	private FileChannel fileChannel;
	/** Size of run-in in physical file. */
	private @UInt32 int runInSize = 0;
	/** Lock some or all of the file. */
	private FileLock lock = null;
	
//		DataChunkPtr Buffer;			//!< Memory file buffer pointer
	private ByteBuffer buffer;
//	UInt64 BufferCurrentPos;		//!< Offset of the current position from the start of the memory file
	private @UInt64 long bufferCurrentPosition;

//		UInt64 BufferOffset;			//!< Offset of the start of the buffer from the start of the memory file
	private @UInt64 long bufferOffset = 0;

//
//
//		UInt32 BlockAlign;				//!< Some systems can run more efficiently if the essence and index data start on a block boundary - if used this is the block size
	private @UInt32 int blockAlign;
//		Int32 BlockAlignEssenceOffset;	//!< Fixed distance from the block grid at which to align essence (+ve is after the grid, -ve before)
	private @UInt32 int blockAlignEssenceOffset;
//		Int32 BlockAlignIndexOffset;	//!< Fixed distance from the block grid at which to align index (+ve is after the grid, -ve before)
	private @Int32 int blockAlignIndexOffset;
//
//
//		//DRAGONS: There should probably be a property to say that in-memory values have changed?
//
//		//DRAGONS: Should we have a flush() function
//
//	public:
//
//		RIP FileRIP;
	private RandomIndexPack fileRIP;
	
//		DataChunk RunIn;
	private ByteBuffer runIn;

	private List<Partition> partitions = 
		Collections.synchronizedList(new ArrayList<Partition>());

	private MessageDigest digest = null;
	
	//
//
//		MXFFile() : isOpen(false), isMemoryFile(false), BlockAlign(0) {};
	public MXFFileImpl() { }
//
//		~MXFFile() { if(isOpen) Close(); };

//
//
//		virtual bool Open(std::string FileName, bool ReadOnly = false );

	public @UInt32 int getRunInSize() {
		
		return runInSize;
	}
	
	public boolean hasRunIn() {
		
		return (runInSize > 0);
	}
	
	public boolean open(
			String fileName,
			boolean readOnly) 
		throws IOException {
		
		if (isOpen) close();
		
		isMemoryFile = false;
		
		fileHandle = new File(fileName);
		fileHandle = fileHandle.getAbsoluteFile();
		
		if (!fileHandle.exists())
			System.err.println("File " + fileHandle.toString() + " does not exist!");
		
		try {
			fileChannel = (new RandomAccessFile(fileHandle, readOnly ? "r" : "rw")).getChannel();
		}
		catch (FileNotFoundException fnfe) {
			return false;
		}

		isOpen = true;
		
		return readRunIn();
	}
	
	public boolean open(
			String fileName) 
		throws IOException {
		
		return open(fileName, false);
	}
	
//		virtual bool OpenNew(std::string FileName);

	public boolean openNew(
			String fileName) 
		throws IOException {
		
		fileHandle = new File(fileName);
		
		// Must be opening a new file, not overwriting one that already exists
		if (fileHandle.exists())
			return false;
		
		return open(fileName, false);
	}
	
//		virtual bool OpenMemory(DataChunkPtr Buff = NULL, Position Offset = 0);

	public boolean openMemory(
			ByteBuffer buffer,
			@MXFPosition long offset) {
		
		if (isOpen) close();
		
		isMemoryFile = true;
		
		// Run in not currently supported for memory buffers
		runInSize = 0;
		
		if (buffer == null) {
			this.buffer = ByteBuffer.allocate(ALLOCATION_SIZE);
			this.buffer.limit(0);
		}
		else
			this.buffer = buffer.duplicate();
		
		bufferOffset = offset;
		bufferCurrentPosition = 0l;
		this.buffer.position(0);
		
		isOpen = true;
		
		return true;
	}
	
	public boolean openMemory() {
		
		return openMemory(null, 0l);
	}
	
//		virtual bool Close(void);

	public boolean close() {
		
		buffer = null;
		try {
			if (fileChannel != null)
				fileChannel.close();
		}
		catch (IOException ioe) {
			if (isOpen) {
				System.err.println("Failed to close MXF file " + fileHandle.getName() + 
						" with IO exception: " + ioe.getMessage());
				return false;
			}
		}
		
		isOpen = false;
		isMemoryFile = false;
		return true;
	}
	
	public void finalize() 
		throws Throwable {
		
		try {
			close();
		}
		finally {
			super.finalize();
		}
	}
	
//
//
//		bool ReadRunIn(void);
//
	public boolean readRunIn() 
		throws IOException {
		
		runInSize = 0;
		runIn = null;
		
		// An MXF file must contain at least 16 bytes
		if (fileChannel.size() < 16)
			return false;
		
		fileChannel.position(0l);
		ByteBuffer startKey = ByteBuffer.allocate(16);
		fileChannel.read(startKey);

		ClassDefinition closedHeaderClass =
			Warehouse.lookForClass(HeaderClosedCompletePartitionPackImpl.class);
		ByteBuffer closedHeader = 
			ByteBuffer.wrap(((UL) closedHeaderClass.getAUID()).getUniversalLabel());
		boolean headerKey = true;
		startKey.rewind();
		for ( int u = 0 ; u < 11 ; u++ ) {
			if (startKey.get() != closedHeader.get()) {
				headerKey = false;
				break;
			}
		}
		
		fileChannel.position(0l);
		
		if (headerKey) return true;
			
		ByteBuffer runInSearchZone = ByteBuffer.allocate(0x10000 + 11);
		fileChannel.read(runInSearchZone);
		runInSearchZone.flip();
		
		byte firstByte = closedHeader.get(0);
		for ( int u = 1 ; u < runInSearchZone.limit() ; u++ ) {
			
			if (runInSearchZone.get(u) == firstByte) {
				
				boolean keyFound = true;
				for ( int v = 1 ; v < 11 ; v++)
					if (runInSearchZone.get(u + v) != closedHeader.get(v)) {
						keyFound = false;
						break;
					}
	
				if (keyFound) {
					runInSize = u;
					byte[] runInBytes = new byte[runInSize];
					runInSearchZone.get(runInBytes);
					runIn = ByteBuffer.wrap(runInBytes);
					
					fileChannel.position((long) runInSize);
					return true;
				}
			}
		}
		
		System.err.println("Cannot find a valid key in the first 65536 bytes in file " + getName() + ".");
		fileChannel.position(0);
		return false;
	}
	
//
//
//		// RIP Readers
//
//		bool ReadRIP(void);
	public boolean readRIP() 
		throws IOException {
		
		int ripLength = -1;
		if (isMemoryFile) {
			ripLength = buffer.getInt(buffer.limit() - 4);
		}
		else {
			ByteBuffer lengthBuffer = ByteBuffer.allocate(4);

			fileChannel.read(lengthBuffer, fileChannel.size() - 4);
			lengthBuffer.rewind();
			ripLength = lengthBuffer.getInt();
		}
		
		// A RIP must have a key and a length and is unlikely to be bigger than 64k
		if ((ripLength < 20) || (ripLength > fileChannel.size()))
			return false;
		
		ByteBuffer ripBytes = null;
		if (isMemoryFile) {
			// TODO
		}
		else {
			ripBytes = ByteBuffer.allocate(ripLength);
			fileChannel.read(ripBytes, fileChannel.size() - ripLength);
		}
		
		clearRIP();
		ripBytes.rewind();
		fileRIP = RandomIndexPackImpl.createFromBytes(ripBytes);
		
		return (fileRIP == null) ? false : true;
	}

	public RandomIndexPack getRandomIndexPack() {
		
		if (fileRIP == null)
			return null;
		else
			return fileRIP.clone();
	}
	
	public void clearRIP() {
		
		fileRIP = null;
	}
	
	public boolean buildPartitionsTable()
            throws IOException, EndOfDataException {
		
		if (fileRIP == null)
			readRIP();
			
		partitions.clear();
		
		readRunIn();
		
		if (fileRIP != null) {
			
			for ( RandomIndexItem item : fileRIP.getPartitionIndex() ) {
				
				// System.out.println("Reading RIP item: " + item.toString());
				seek(item.getByteOffset());
				partitions.add(PartitionImpl.partitionFactory(this));
			}
			
			return true;
		}
	
		partitions.add(PartitionImpl.partitionFactory(this));
		
		return buildPartitionsBackwards(getHeaderPartition().getPartitionPack());

	}
	
	public boolean buildPartitionsBackwards(
			PartitionPack headerPartitionPack) throws EndOfDataException {
	
		long footerPosition = headerPartitionPack.getFooterPartition();
		// Deal with the nasty case where the footer partition has not been provided.
		if (footerPosition == 0) 
			return false; 
		
		long lastPosition = footerPosition;
		while (lastPosition > 0) {
		
			seek(lastPosition);
			Partition insertable = PartitionImpl.partitionFactory(this);
			partitions.add(1, insertable);
			
			lastPosition = insertable.getPartitionPack().getPreviousPartition();
		}
		
		return true;
	}
	
	public int countPartitions() {
		
		return partitions.size();
	}
	
	public HeaderPartition addHeaderPartition(
			boolean closed,
			boolean complete,
			long initialDataSize,
			int bodySID,
			int indexSID,
			UL... containerFormats) {
		
		HeaderPartition partition = new HeaderPartitionImpl();
		if (closed) {
			if (complete) 
				partition.setPartitionPack(new HeaderClosedCompletePartitionPackImpl());
			else
				partition.setPartitionPack(new HeaderClosedIncompletePartitionPackImpl());
		}
		else {
			if (complete)
				partition.setPartitionPack(new HeaderOpenCompletePartitionPackImpl());
			else
				partition.setPartitionPack(new HeaderOpenIncompletePartitionPackImpl());
		}
		
		for ( UL format : containerFormats )
			partition.getPartitionPack().addEssenceContainer(format);
		partition.getPartitionPack().setBodySID((bodySID < 0) ? 0 : bodySID);
		partition.getPartitionPack().setIndexSID((indexSID < 0) ? 0 : indexSID);
		((PartitionImpl) partition).setMXFFile(this);
		partition.setInitialDataSize(initialDataSize);
		
		if ((partitions.size() != 0) && (partitions.get(0) instanceof HeaderPartition))
			partitions.remove(0);
		
		partitions.add(0, partition);
	
		return partition;
	}
	
	public void setFileOperationalPattern(
			UL operationalPattern) 
		throws NullPointerException {
		
		if (operationalPattern == null)
			throw new NullPointerException("Cannot set the operational pattern for this whole file to null.");
		
		for ( Partition partition : partitions )
			partition.getPartitionPack().setOperationalPattern(operationalPattern);
	}
	
	public void setFileKAG(
			@UInt32 int kag) 
		throws IllegalArgumentException {
		
		if (kag < 0)
			throw new IllegalArgumentException("Cannot set the KLV alignment grid for the file to a negative value.");
		
		for ( Partition partition : partitions )
			partition.getPartitionPack().setKagSize(kag);
	}
	
	public void setFileMXFByteLevelVersion(
			@UInt16 short major,
			@UInt16 short minor)
		throws IllegalArgumentException {
		
		if (major < 0) 
			throw new IllegalArgumentException("Cannot set the MXF byte level major version number for the file to a negative value.");
		if (minor < 0)
			throw new IllegalArgumentException("Cannot set the MXF byte level minor version number for this file to a negatuve value.");
		
		for ( Partition partition : partitions ) {
			partition.getPartitionPack().setMajorVersion(major);
			partition.getPartitionPack().setMinorVersion(minor);
		}
	}
	
	public FooterPartition addFooterPartition(
			boolean complete,
			long initialSize,
			int indexSID,
			UL... containerFormats) {
		
		FooterPartition partition = new FooterPartitionImpl();
		
		if (complete)
			partition.setPartitionPack(new FooterClosedCompletePartitionPackImpl());
		else
			partition.setPartitionPack(new FooterClosedIncompletePartitionPackImpl());
		
		for ( UL format : containerFormats )
			partition.getPartitionPack().addEssenceContainer(format);
		partition.getPartitionPack().setIndexSID((indexSID < 0) ? 0 : indexSID);
		((PartitionImpl) partition).setMXFFile(this);
		partition.setInitialDataSize(initialSize);
		if (partitions.size() > 0) {
			Partition firstPartition = partitions.get(0);
			AUID likelyOP = firstPartition.getPartitionPack().getOperationalPattern();
			if (likelyOP != null)
				partition.getPartitionPack().setOperationalPattern(likelyOP);
		}

		if (partitions.size() == 0) {
			partitions.add(partition);
			return partition;
		}

		if (partitions.get(partitions.size() - 1) instanceof FooterPartition) 
			partitions.remove(partitions.size() - 1);
		partitions.add(partition);
		
		return partition;
	}
	
	public BodyPartition addBodyPartition(
			boolean closed,
			boolean complete,
			long initialDataSize,
			int bodySID,
			int indexSID,
			UL... containerFormats) {
		
		BodyPartition partition = new BodyPartitionImpl();
		if (closed) {
			if (complete) 
				partition.setPartitionPack(new BodyClosedCompletePartitionPackImpl());
			else
				partition.setPartitionPack(new BodyClosedIncompletePartitionPackImpl());
		}
		else {
			if (complete)
				partition.setPartitionPack(new BodyOpenCompletePartitionPackImpl());
			else
				partition.setPartitionPack(new BodyOpenIncompletePartitionPackImpl());
		}
		
		for ( UL format : containerFormats )
			partition.getPartitionPack().addEssenceContainer(format);
		partition.getPartitionPack().setBodySID((bodySID < 0) ? 0 : bodySID);
		partition.getPartitionPack().setIndexSID((indexSID < 0) ? 0 : indexSID);
		((PartitionImpl) partition).setMXFFile(this);
		partition.setInitialDataSize(initialDataSize);
		if (partitions.size() > 0) {
			Partition firstPartition = partitions.get(0);
			AUID likelyOP = firstPartition.getPartitionPack().getOperationalPattern();
			if (likelyOP != null)
				partition.getPartitionPack().setOperationalPattern(likelyOP);
		}
		
		if (partitions.size() == 0) {
			partitions.add(partition);
			return partition;
		}
		
		if (partitions.get(partitions.size() - 1) instanceof FooterPartition)
			partitions.add(partitions.size() - 1, partition);
		else
			partitions.add(partition);
	
		return partition;
	}
	
	public Partition getPartitionAt(
			int index) 
		throws IndexOutOfBoundsException {
		
		if ((index < 0) || (index >= partitions.size()))
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the current list of partitions.");
		
		return partitions.get(index);
	}
	
	public Partition getNextPartition(
			Partition current) {
		
		int currentIndex = partitions.indexOf(current);
		if ((currentIndex == -1) || (currentIndex == partitions.size() - 1)) return null;
		return partitions.get(currentIndex + 1);
	}
	
	public void updatePackSizes() {
		
		for ( Partition partition : partitions )
			partition.updateSizes();
	
		long previous = 0l;
		long fileOffset = runInSize;
		for ( Partition partition : partitions ) {
			partition.getPartitionPack().setThisPartition(fileOffset);
			partition.getPartitionPack().setPreviousPartition(previous);
			previous = fileOffset;
			fileOffset += partition.getActualSize();
		}
		
		long footer = 0l;
		if ( partitions.get(partitions.size() - 1) instanceof FooterPartition )
			footer = previous;
		
		for ( Partition partition : partitions )
			partition.getPartitionPack().setFooterPartition(footer);
	}
	
	public HeaderPartition getHeaderPartition() {
		
		if (partitions.size() == 0) return null;
		if (!(partitions.get(0) instanceof HeaderPartition)) return null;

		return (HeaderPartition) partitions.get(0);
	}
	
	public FooterPartition getFooterPartition() {
		
		int partitionsLastIndex = partitions.size() - 1;
		if (partitionsLastIndex < 1) return null;
		if (!(partitions.get(partitionsLastIndex) instanceof FooterPartition)) return null;
		
		return (FooterPartition) partitions.get(partitionsLastIndex);
	}
	
//	bool ScanRIP(Length MaxScan = 1024*1024);
	
	public boolean scanRIP(
			@MXFLength long maxScan) {
		
		//TODO
		return false;
	}

	public boolean scanRIP() {
		
		return scanRIP(1024*1024);
	}
	
//		bool BuildRIP(void);

	public boolean buildRIP() {
		
		fileRIP = new RandomIndexPackImpl();
		
		for ( Partition partition : partitions )
			fileRIP.addRandomIndexItem(partition.getPartitionPack().getBodySID(), partition.getPartitionPack().getThisPartition());
	
		return true;
	}
	
//		bool GetRIP(Length MaxScan = 1024*1024);

	public boolean getRIP(
			@MXFLength long maxScan) {
		
		// TODO
		return false;
	}

//	public boolean getRIP() {
//		
//		return getRIP(1024*1024);
//	}
//		
//
//		//! Locate and read a partition containing closed header metadata
//
//		/*! \ret NULL if none found
//
//		 */

	public PartitionImpl readMasterPartition(
			@Int64 long maxScan) {
		
		//TODO
		return null;
	}

	public PartitionImpl readMasterPartition() {
		
		return readMasterPartition(1024*1024);
	}
	
//
//		//! Locate and read the footer partition
//
//		/*! \ret NULL if not found
//
//		 */
//
//		PartitionPtr ReadFooterPartition(Length MaxScan = 1024*1024);
	public PartitionImpl readFooterPartition(
			@MXFLength long maxScan) {
		
		//TODO
		return null;
	}

	public PartitionImpl readFooterPartition() {
		
		return readFooterPartition(1024*1024);
	}
//
//		//! Report the position of the file pointer
//
//		Position Tell(void) 
//
//		{ 
//
//			if(!isOpen) return 0;
//
//			if(isMemoryFile) return BufferCurrentPos-RunInSize;
//
//			return UInt64(mxflib::FileTell(Handle))-RunInSize;
//
//		}
//
	public @MXFPosition long tell() {
		
		if (!isOpen) return 0l;
		
		if (isMemoryFile) return bufferCurrentPosition - runInSize;
		
		try {
			return fileChannel.position() - runInSize; 
		}
		catch (IOException ioe) {
			return 0l;
		}
 	}
//
//		//! Move the file pointer
//
//		/* \return 0 if no error, else non-zero
//
//		 * DRAGONS: This is where we need to insert code to handle file discontinuities
//
//		 *          If a file has one or more chunks mising then we can build a list of
//
//		 *			discontinuities based on where partition packs start compared with
//
//		 *			where in the file they claim to be. This allows us to modify seeks
//
//		 *			so that they find the data originally at that part of the file even
//
//		 *			though they are now in a different position
//
//		 */
//
//		int Seek(Position Pos)
//
//		{ 
//
//			if(!isOpen) return 0;
//
//			if(isMemoryFile)
//
//			{
//
//				BufferCurrentPos = Pos+RunInSize;
//
//				return 0;
//
//			}
//
//
//
//			return mxflib::FileSeek(Handle, Pos+RunInSize);
//
//		}
//
	public int seek(
			@MXFPosition long position) {
		
		if (!isOpen) return 0;
		
		if (isMemoryFile) {
			
			bufferCurrentPosition = position + runInSize;
			return 0;
		}
		
		try {
			fileChannel.position(position + runInSize);
			return 0;
		}
		catch (IOException ioe) {
			return 1;
		}
	}
//
//		int SeekEnd(void)
//
//		{ 
//
//			if(!isOpen) return 0;
//
//			if(isMemoryFile)
//
//			{
//
//				error("MXFFile::SeekEnd() not supported on memory files\n");
//
//
//
//				// Seek to the end of the current buffer
//
//				BufferCurrentPos = BufferOffset + Buffer->Size;
//
//				return (int)Tell();
//
//			}
//
//
//
//			return mxflib::FileSeekEnd(Handle);
//
//		}
//
	public int seekEnd() {
		
		if (!isOpen) return 0;
		
		if (isMemoryFile) {
			
			bufferCurrentPosition = buffer.limit() + bufferOffset;
			return 0;
		}
		
		try {
			fileChannel.position(fileChannel.size());
			return 0;
		}
		catch (IOException ioe) {
			return -1;
		}
	}
//
//
//
//		//! Determine if the file pointer is at the end of the file
//
//		bool Eof(void) 
//
//		{ 
//
//			if(!isOpen) return true;
//
//			if(isMemoryFile)
//
//			{
//
//				error("MXFFile::Eof() not supported on memory files\n");
//
//
//
//				// Return true if at the end of the current buffer
//
//				if((BufferCurrentPos - BufferOffset) <= Buffer->Size) return true; else return false;
//
//			}
//
//		
//
//			return mxflib::FileEof(Handle) ? true : false; 
//
//		};
//
	public boolean atEOF() {
		
		if (!isOpen) return true;

		if (isMemoryFile) {
			return ((bufferCurrentPosition - bufferOffset) >= buffer.limit());
		}
		
		try {
			return (fileChannel.position() >= fileChannel.size());
		}
		catch (IOException ioe) {
			return true;
		}
	}
//
//		DataChunkPtr Read(size_t Size);

	public ByteBuffer read(
			int size) 
		throws IllegalArgumentException {
		
		if (size < 0)
			throw new IllegalArgumentException("Cannot read a negative number of bytes.");
		
		ByteBuffer readBuffer = null;
		
		if (size > 0) {
			
			int countBytesRead;
			
			if (isMemoryFile) {
				byte[] fromMemory = new byte[size];
				countBytesRead = memoryRead(fromMemory);
				readBuffer = ByteBuffer.wrap(fromMemory);
				readBuffer.limit(countBytesRead);
			}
			else {
				try {
					readBuffer = ByteBuffer.allocate(size);
					countBytesRead = fileChannel.read(readBuffer);
					readBuffer.flip();
				}
				catch (IOException ioe) {
					System.err.println("Error reading file " + getName() + " at position 0x" + Long.toHexString(tell()) + ": " + ioe.getMessage());
					countBytesRead = 0;
				}
			}
			
			return readBuffer;
		}
		
		return ByteBuffer.allocate(0);

	}
//		size_t Read(UInt8 *Buffer, size_t Size);
//
	public int read(
			byte[] buffer) {
		
		int countBytesRead;
		
		if (isMemoryFile) {
			countBytesRead = memoryRead(buffer);
		}
		else {
			try {
				ByteBuffer fileBytes = ByteBuffer.wrap(buffer);
				countBytesRead = fileChannel.read(fileBytes);
			}
			catch (IOException ioe) {
				System.err.println("Error reading file " + getName() + " at position 0x" + Long.toHexString(tell()) + ": " + ioe.getMessage());
				countBytesRead = 0;
			}
		}
		
		return (countBytesRead == -1) ? 0 : countBytesRead;
	}
//
////		MDObjectPtr ReadObject(void);
//
	public MetadataObject readObject() 
		throws BadParameterException { // TODO remove this exception cludge
		
		UL key = readKey();
		ClassDefinition classDef = 
			tv.amwa.maj.meta.impl.ClassDefinitionImpl.forAUID(key);
		MetadataObject mdObject = classDef.createInstance();
		
		long length = readBER();
		
		// Read the properties
		while (length > 0) {
			UL propertyKey = readKey();
			long propertyLength = readBER();
			ByteBuffer propertyBytes = read((int) propertyLength); // TODO check this cast
			PropertyDefinition propertyDef = classDef.lookupPropertyDefinition(propertyKey);
			TypeDefinition propertyType = propertyDef.getTypeDefinition();
			
			PropertyValue propertyValue = propertyType.createValue(propertyBytes);
			propertyDef.setPropertyValue(mdObject, propertyValue);
			
			length -= propertyLength;
		}
		return null;
	}
	
////		template<class TP, class T> TP ReadObjectBase(void) { TP x; return x; };
//
////		template<> MDObjectPtr ReadObjectBase<MDObjectPtr, MDObject>(void) { MDObjectPtr x; return x; };
//
//		MDObjectPtr ReadObject(PrimerPtr UsePrimer = NULL) { return MXFFile__ReadObjectBase<MDObjectPtr, MDObject>(this, UsePrimer); };

	public MetadataObject readObject(
			Primer primer) {
		
		// TODO
		return null;
	}
	
//		PartitionPtr ReadPartition(void) { return MXFFile__ReadObjectBase<PartitionPtr, Partition>(this); };
//
	public Partition readPartition() throws EndOfDataException {
		
		// TODO
		Partition partition = PartitionImpl.partitionFactory(this);
		
		if (partition == null) return null;
		
		if (!partitions.contains(partition))
			partitions.add(partition);
		
		return partition;
	}
//
//		//! Read a KLVObject from the file
	
	// Returns null if no valid KLV data is found
//
//		KLVObjectPtr ReadKLV(void);
//
	public KLVObject readKLV() {
		
		KLVObject klv = new KLVObject();
		klv.setSource(this);
		if (klv.readKL() < 17) return null;
		
		return klv;
	}
//
//		//! Write a partition pack to the file
//
//		void WritePartitionPack(PartitionPtr ThisPartition, PrimerPtr UsePrimer = NULL);
//
	public void writePartitionPack(
			PartitionImpl partition,
			Primer primer) {
		
		// TODO
	}
	
	public void writePartitionPack(
			PartitionImpl partition) {
		
		writePartitionPack(partition, null);
	}
//
//		//! Write a partition pack and associated metadata (no index table segments)
//
//		void WritePartition(PartitionPtr ThisPartition, UInt32 Padding = 0, UInt32 MinPartitionSize = 0) { WritePartition(ThisPartition, true, NULL, Padding, MinPartitionSize); };

	public void writePartition(
			PartitionImpl partition,
			@UInt32 int padding,
			@UInt32 int minPartitionSize) {
		
		writePartition(partition, true, null, padding, minPartitionSize);
	}
//

	public void writePartition(
			PartitionImpl partition) {
		
		writePartition(partition, true, null, 0, 0);
	}
	
//		//! Write a partition pack and associated metadata and preformatted index table segments
//
//		/*! \note The value of IndexSID must be set prior to calling WritePartitionWithIndex */
//
//		void WritePartitionWithIndex(PartitionPtr ThisPartition, DataChunkPtr IndexData, UInt32 Padding = 0, UInt32 MinPartitionSize = 0) { WritePartitionWithIndex(ThisPartition, IndexData, true, NULL, Padding, MinPartitionSize); };
//
	public void writePartitionWithIndex(
			PartitionImpl partition,
			ByteBuffer indexData,
			@UInt32 int padding,
			@UInt32 int minPartitionSize) {
		
		writePartitionWithIndex(partition, indexData, true, null, padding, minPartitionSize);
	}
	
	public void writePartitionWithIndex(
			PartitionImpl partition,
			ByteBuffer indexData) {
		
		writePartitionWithIndex(partition, indexData, true, null, 0, 0);
	}
//
//		//! Write a partition pack and associated metadata (no index table segments)
//
//		void WritePartition(PartitionPtr ThisPartition, PrimerPtr UsePrimer, UInt32 Padding = 0, UInt32 MinPartitionSize = 0) { WritePartition(ThisPartition, true, UsePrimer, Padding, MinPartitionSize); };
//

	public void writePartition(
			PartitionImpl partition,
			Primer primer,
			@UInt32 int padding,
			@UInt32 int minPartitionSize) {
		
		writePartition(partition, true, primer, padding, minPartitionSize);
	}
	
	public void writePartition(
			PartitionImpl partition,
			Primer primer) {
		
		writePartition(partition, true, primer, 0, 0);
	}
//
//		//! Write a partition pack and associated metadata and preformatted index table segments
//
//		/*! \note The value of IndexSID must be set prior to calling WritePartitionWithIndex */
//
//		void WritePartitionWithIndex(PartitionPtr ThisPartition, DataChunkPtr IndexData, PrimerPtr UsePrimer, UInt32 Padding = 0, UInt32 MinPartitionSize = 0) { WritePartitionWithIndex(ThisPartition, IndexData, true, UsePrimer, Padding, MinPartitionSize); };
//
	public void writePartitionWithIndex(
			PartitionImpl partition,
			ByteBuffer indexData,
			Primer primer,
			@UInt32 int padding,
			@UInt32 int minPartitionSize) {
		
		writePartitionWithIndex(partition, indexData, true, primer, padding, minPartitionSize);
	}
	
	public void writePartitionWithIndex(
			PartitionImpl partition,
			ByteBuffer indexData,
			Primer primer) {
		
		writePartitionWithIndex(partition, indexData, true, primer, 0, 0);
	}
//
//		//! Write a partition pack and (optionally) associated metadata (no index table segments)
//
//		void WritePartition(PartitionPtr ThisPartition, bool IncludeMetadata, PrimerPtr UsePrimer = NULL, UInt32 Padding = 0, UInt32 MinPartitionSize = 0)
//
//		{
//
//			WritePartitionInternal(false, ThisPartition, IncludeMetadata, NULL, UsePrimer, Padding, MinPartitionSize);
//
//		}
//
	public void writePartition(
			PartitionImpl partition,
			boolean includeMetadata,
			Primer primer,
			@UInt32 int padding,
			@UInt32 int minPartitionSize) {
		
		writePartitionInternal(false, partition, includeMetadata, null, primer, padding, minPartitionSize);
	}
	
	public void writePartition(
			PartitionImpl partition,
			boolean includeMetadata) {
		
		writePartitionInternal(false, partition, includeMetadata, null, null, 0, 0);
	}
//
//		//! Write a partition pack and (optionally) associated metadata and preformatted index table segments
//
//		/*! \note The value of IndexSID must be set prior to calling WritePartitionWithIndex */
//
//		void WritePartitionWithIndex(PartitionPtr ThisPartition, DataChunkPtr IndexData, bool IncludeMetadata, PrimerPtr UsePrimer = NULL, UInt32 Padding = 0, UInt32 MinPartitionSize = 0)
//
//		{
//
//			WritePartitionInternal(false, ThisPartition, IncludeMetadata, IndexData, UsePrimer, Padding, MinPartitionSize);
//
//		}
//
	public void writePartitionWithIndex(
			PartitionImpl partition,
			ByteBuffer indexData,
			boolean includeMetadata,
			Primer primer,
			@UInt32 int padding,
			@UInt32 int minPartitionSize) {
		
		writePartitionInternal(false, partition, includeMetadata, indexData, primer, padding, minPartitionSize);
	}
	
	public void writePartitionWithIndex(
			PartitionImpl partition,
			ByteBuffer indexData,
			boolean includeMetadata) {
		
		writePartitionInternal(false, partition, includeMetadata, indexData, null, 0, 0);
	}

//
//		//! Re-write a partition pack and associated metadata (no index table segments)
//
//		/*! \note Partition properties are updated from the linked metadata
//
//		 *	\return true if re-write was successful, else false
//
//		 */
//
//		bool ReWritePartition(PartitionPtr ThisPartition, PrimerPtr UsePrimer = NULL) 
//
//		{
//
//			return WritePartitionInternal(true, ThisPartition, true, NULL, UsePrimer, 0, 0);
//
//		}
//
	public boolean reWritePartition(
			PartitionImpl partition,
			Primer primer) {
		
		return writePartitionInternal(true, partition, true, null, primer, 0, 0);
	}

	public boolean reWritePartition(
			PartitionImpl partition) {
		
		return writePartitionInternal(true, partition, true, null, null, 0, 0);
	}
	
//		//! Re-write a partition pack and associated metadata and preformatted index table segments
//
//		/*! \note Partition properties are updated from the linked metadata
//
//		 *	\return true if re-write was successful, else false
//
//		 */
//
//		bool ReWritePartitionWithIndex(PartitionPtr ThisPartition, DataChunkPtr IndexData, PrimerPtr UsePrimer = NULL) 
//
//		{
//
//			return WritePartitionInternal(true, ThisPartition, true, IndexData, UsePrimer, 0, 0);
//
//		}
//
	public boolean reWritePartitionWithIndex(
			PartitionImpl partition,
			ByteBuffer indexData,
			Primer primer) {
		
		return writePartitionInternal(true, partition, true, indexData, primer, 0, 0);
	}
	
	public boolean reWritePartitionWithIndex(
			PartitionImpl partition,
			ByteBuffer indexData) {
		
		return writePartitionInternal(true, partition, true, indexData, null, 0, 0);
	}
//
//	protected:
//
//		//! Write or re-write a partition pack and associated metadata (and index table segments?)
//
//		bool WritePartitionInternal(bool ReWrite, PartitionPtr ThisPartition, bool IncludeMetadata, DataChunkPtr IndexData, PrimerPtr UsePrimer, UInt32 Padding, UInt32 MinPartitionSize);
//
	boolean writePartitionInternal(
			boolean reWrite,
			PartitionImpl partition,
			boolean includeMetadata,
			ByteBuffer indexData,
			Primer primer,
			@UInt32 int padding,
			@UInt32 int minPartitionSize) {
		
		// TODO
		return false;
	}
	
//	public:
//
//		//! Write the RIP
//
//		void WriteRIP(void)
//
//		{
//
//			MDObjectPtr RIPObject = new MDObject(RandomIndexMetadata_UL);
//
//			ASSERT(RIPObject);
//
//
//
//			if(RIPObject)
//
//			{
//
//				MDObjectPtr PA = RIPObject->AddChild(PartitionArray_UL);
//
//
//
//				ASSERT(PA);
//
//				if(PA)
//
//				{
//
//					RIP::iterator it = FileRIP.begin();
//
//					while(it != FileRIP.end())
//
//					{
//
//						PA->AddChild(BodySID_UL, false)->SetUInt((*it).second->BodySID);
//
//						PA->AddChild(ByteOffset_UL, false)->SetUInt64((*it).second->ByteOffset);
//
//						it++;
//
//					}
//
//				}
//
//				
//
//				// Calculate the pack length
//
//				RIPObject->SetUInt(Length_UL, 16 + 4 + (static_cast<UInt32>(FileRIP.size()) * 12) + 4);
//
//
//
//				DataChunkPtr Buffer = RIPObject->WriteObject();
//
//
//
//				Write(Buffer->Data, Buffer->Size);
//
//			}
//
//		}
//
	public boolean writeRIP() 
		throws IOException {
		
		long ripPosition;
		int ripLength = -1;
		if (isMemoryFile) {
			ripLength = buffer.getInt(buffer.limit() - 4);
		}
		else {
			ByteBuffer lengthBuffer = ByteBuffer.allocate(4);

			fileChannel.read(lengthBuffer, fileChannel.size() - 4);
			lengthBuffer.rewind();
			ripLength = lengthBuffer.getInt();
		}
		
		// A RIP must have a key and a length and is unlikely to be bigger than 64k
		if ((ripLength < 20) || (ripLength > fileChannel.size())) {
			ripPosition = fileChannel.size();
		}
		else {
			ByteBuffer ripBytes = null;
			if (isMemoryFile) {
				// TODO
			}
			else {
				ripBytes = ByteBuffer.allocate(ripLength);
				fileChannel.read(ripBytes, fileChannel.size() - ripLength);
			}
		
			ripBytes.rewind();
			RandomIndexPack testRIP = RandomIndexPackImpl.createFromBytes(ripBytes);
			
			ripPosition = (testRIP == null) ? fileChannel.size() : fileChannel.size() - ripLength;
		}
		
		
		ByteBuffer ripBytes = ByteBuffer.allocate(fileRIP.getLength());
		try {
			fileRIP.writeAsBytes(ripBytes);
			ripBytes.rewind();
			
			if (digest != null) {
				digest.update(ripBytes);
				ripBytes.rewind();
			}
			fileChannel.write(ripBytes, ripPosition);
		}
		catch (InsufficientSpaceException ise) {
			return false;
		}
		
		return true;
	}

	
	//
//		//! Calculate the size of a filler to align to a specified KAG
//
//		UInt32 FillerSize(UInt64 FillPos, UInt32 KAGSize, UInt32 MinSize = 0) { return FillerSize(false, FillPos, KAGSize, MinSize); };

	public @UInt32 int fillerSize(
			@UInt64 long fillPosition,
			@UInt32 int kagSize,
			@UInt32 int minSize) {
		
		return fillerSize(false, fillPosition, kagSize, minSize);
	}
	
	public @UInt32 int fillerSize(
			@UInt64 long fillPosition,
			@UInt32 int kagSize) {
		
		return fillerSize(false, fillPosition, kagSize, 0);
	}

	
//		UInt32 FillerSize(bool ForceBER4, UInt64 FillPos, UInt32 KAGSize, UInt32 MinSize = 0);
//
	public @UInt32 int fillerSize(
			boolean forceeBER4,
			@UInt64 long fillPosition,
			@UInt32 int kagSize,
			@UInt32 int minSize) {
		
		// TODO
		return 0;
	}
	
	public @UInt32 int fillerSize(
			boolean forceeBER4,
			@UInt64 long fillPosition,
			@UInt32 int kagSize) {
		
		return fillerSize(forceeBER4, fillPosition, kagSize, 0);
	}
	
//
//		//! Write a filler to align to a specified KAG
//
//		UInt64 Align(UInt32 KAGSize, UInt32 MinSize = 0) { return Align(false, KAGSize, MinSize); };

	public @UInt64 long align(
			@UInt32 int kagSize,
			@UInt32 int minSize) {
		
		return align(false, kagSize, minSize);
	}
	
	public @UInt64 long align(
			@UInt32 int kagSize) {
		
		return align(false, kagSize, 0);
	}
	
//		UInt64 Align(bool ForceBER4, UInt32 KAGSize, UInt32 MinSize = 0);
//
	public @UInt64 long align(
			boolean forceBER4,
			@UInt32 int kagSize,
			@UInt32 int minSize) {
		
		// TODO
		return 0l;
	}

	public @UInt64 long align(
			boolean forceBER4,
			@UInt32 int kagSize) {
		
		return align(forceBER4, kagSize, 0);
	}

	//
//		ULPtr ReadKey(void);

	public UL readKey() {
		
		ByteBuffer keyData = read(16);
		if (keyData.limit() < 16) 
			return null;
		
		byte[] keySwap = new byte[16];
		keyData.get(keySwap, 8, 8);
		keyData.get(keySwap, 0, 8);
		
		return new AUIDImpl(keySwap);
	}
	
//		Length ReadBER(void);
//
	public @MXFLength long readBER() {
		
		ByteBuffer lengthBuffer = read(1);
		
		if (lengthBuffer.limit() < 1) {
			System.err.println("Incomplete BER length in MXF file " + getName() + " at 0x" + Long.toHexString(tell()));
			return -1l;
		}
		
		byte first = lengthBuffer.get();
		if (first >= 0) // top bit set not set
			return (long) first;
		
		int berTailLength = (int) (first & 0x7f);
		lengthBuffer = read(berTailLength);
		
		if (lengthBuffer.limit() != berTailLength) {
			System.err.println("Incomplete BER length in MXF file " + getName() + " at 0x" + Long.toHexString(tell()));
			return -1l;			
		}
			
		long lengthValue = 0l;
		byte[] lengthData = new byte[berTailLength];
		lengthBuffer.get(lengthData);
		for ( int u = 0 ; u < lengthData.length ; u++ )
			lengthValue = (lengthValue << 8) + 
				(((lengthData[u]) >= 0) ? lengthData[u] : 256 + lengthData[u]);
		
		return lengthValue;
	}
//
//		//! Write a BER length
//
//		/*! \param Length	The length to be written
//
//		 *	\param Size		The total number of bytes to use for BER length (or 0 for auto)
//
//		 *	\note If the size is specified it will be overridden for lengths
//
//		 *		  that will not fit. However an error message will be produced.
//
//		 */
//
//		UInt32 WriteBER(UInt64 Length, UInt32 Size = 0) { DataChunkPtr BER = MakeBER(Length, Size); Write(*BER); return static_cast<UInt32>(BER->Size); };
//
	public @UInt32 int writeBER(
			@UInt64 long length,
			@UInt32 int size) 
		throws IOException {
		
		ByteBuffer ber = Helper.makeBER(length, size);
		write(ber);
		return ber.capacity();
	}
	
	public @UInt32 int writeBER(
			@UInt64 long length)
		throws IOException {
		
		return writeBER(length, 0);
	}
//
//		//! Write raw data
//
//		size_t Write(const UInt8 *Buffer, size_t Size) 
//
//		{ 
//
//			if(isMemoryFile) return MemoryWrite(Buffer, Size);
//
//
//
//			return FileWrite(Handle, Buffer, Size); 
//
//		};
//
	public int write(
			byte[] buffer) {
		
		if (isMemoryFile) 
			return memoryWrite(buffer);
		else
			// TODO write data into the file
			// return fileWrite(fileHandle, buffer);
			return 0;
	}
//
//		//! Write the contents of a DataChunk by reference
//
//		size_t Write(const DataChunk &Data) 
//
//		{ 
//
//			if(isMemoryFile) return MemoryWrite(Data.Data, Data.Size);
//
//
//
//			return FileWrite(Handle, Data.Data, Data.Size); 
//
//		};
//
	public int write(
			ByteBuffer data) 
		throws NullPointerException,
			IOException {
		
		if (data == null)
			throw new NullPointerException("Cannot write null data to an MXF file.");
		
		if (isMemoryFile) 
			return memoryWrite(data);

		if (fileChannel == null)
			throw new IOException("Cannot write data to an MXF file that is not yet open.");
		
		if (digest != null) {
			data.mark();
			digest.update(data);
			data.reset();
		}
		
		return fileChannel.write(data);
	}
///
//
//
//		//! Write 8-bit unsigned integer
//
//		void WriteU8(UInt8 Val) { unsigned char Buffer[1]; PutU8(Val, Buffer); Write(Buffer, 1); }
//
//
//
//		//! Write 16-bit unsigned integer
//
//		void WriteU16(UInt16 Val) { unsigned char Buffer[2]; PutU16(Val, Buffer); Write(Buffer, 2); }
//
//
//
//		//! Write 32-bit unsigned integer
//
//		void WriteU32(UInt32 Val) { unsigned char Buffer[4]; PutU32(Val, Buffer); Write(Buffer, 4); }
//
//
//
//		//! Write 64-bit unsigned integer
//
//		void WriteU64(UInt64 Val) { unsigned char Buffer[8]; PutU64(Val, Buffer); Write(Buffer, 8); }
//
//
//
//		//! Write 8-bit signed integer
//
//		void WriteI8(Int8 Val) { unsigned char Buffer[1]; PutI8(Val, Buffer); Write(Buffer, 1); }
//
//
//
//		//! Write 16-bit signed integer
//
//		void WriteI16(Int16 Val) { unsigned char Buffer[2]; PutI16(Val, Buffer); Write(Buffer, 2); }
//
//
//
//		//! Write 32-bit signed integer
//
//		void WriteI32(Int32 Val) { unsigned char Buffer[4]; PutI32(Val, Buffer); Write(Buffer, 4); }
//
//
//
//		//! Write 64-bit signed integer
//
//		void WriteI64(Int64 Val) { unsigned char Buffer[8]; PutI64(Val, Buffer); Write(Buffer, 8); }
//
//
//
//		//! Read 8-bit unsigned integer
//
//		UInt8 ReadU8(void) { unsigned char Buffer[1]; if(Read(Buffer, 1) == 1) return GetU8(Buffer); else return 0; }
//
//
//
//		//! Read 16-bit unsigned integer
//
//		UInt16 ReadU16(void) { unsigned char Buffer[2]; if(Read(Buffer, 2) == 2) return GetU16(Buffer); else return 0; }
//
//
//
//		//! Read 32-bit unsigned integer
//
//		UInt32 ReadU32(void) { unsigned char Buffer[4]; if(Read(Buffer, 4) == 4) return GetU32(Buffer); else return 0; }
//
//
//
//		//! Read 64-bit unsigned integer
//
//		UInt64 ReadU64(void) { unsigned char Buffer[8]; if(Read(Buffer, 8) == 8) return GetU64(Buffer); else return 0; }
//
//
//
//		//! Read 8-bit signed integer (casts from unsigned version)
//
//		Int8 ReadI8(void) { return (Int8)ReadU8(); }
//
//
//
//		//! Read 16-bit signed integer (casts from unsigned version)
//
//		Int16 ReadI16(void) { return (Int16)ReadU16(); }
//
//		
//
//		//! Read 32-bit signed integer (casts from unsigned version)
//
//		Int32 ReadI32(void) { return (Int32)ReadU32(); }
//
//		
//
//		//! Read 64-bit signed integer (casts from unsigned version)
//
//		Int64 ReadI64(void) { return (Int64)ReadU64(); }
//
//
//
//		// Set a new buffer into this memory file
//
//		void SetMemoryBuffer(DataChunkPtr Buff, UInt32 Offset)
//
//		{
//
//			if(isMemoryFile)
//
//			{
//
//				Buffer = Buff;
//
//				BufferOffset = Offset;
//
//			}
//
//		}
//
	public void setMemoryBuffer(
				ByteBuffer buffer,
				@UInt32 int offset) {
		
		if (isMemoryFile) {
			this.buffer = buffer;
			this.bufferOffset = offset;
		}
		// and if not?
	}
//
//		//! Set the block alignment block size
//
//		void SetBlockAlign(UInt32 Size, Int32 EssenceOffset = 0, Int32 IndexOffset = 0)
//
//		{
//
//			BlockAlign = Size;
//
//			BlockAlignEssenceOffset = EssenceOffset;
//
//			BlockAlignIndexOffset = IndexOffset;
//
//		}
//
	public void setBlockAlign(
			@UInt32 int size,
			@Int32 int essenceOffset,
			@Int32 int indexOffset) {
		
		blockAlign = size;
		blockAlignEssenceOffset = essenceOffset;
		blockAlignIndexOffset = indexOffset;
	}
	
	public void setBlockAlign(
			@UInt32 int size) {
		
		blockAlign = size;
		blockAlignEssenceOffset = 0;
		blockAlignIndexOffset = 0;
	}
//
//		//! Determine if this file used block alignment
//
//		bool IsBlockAligned(void) { return (BlockAlign != 0); }
//
	public boolean isBlockAligned() {
		
		return (blockAlign != 0);
	}
	
//
//	protected:
//
//		Position ScanRIP_FindFooter(Length MaxScan);
//
	@MXFPosition long scanRIPFindFooter(
			@MXFLength long maxScan) {
		
		// TODO
		return 0l;
	}
//
//		//! Write to memory file buffer
//
//		/*! \note This can be overridden in classes derived from MXFFile to give different memory write behaviour */
//
//		virtual size_t MemoryWrite(UInt8 const *Data, size_t Size);
//
	int memoryWrite(
			byte[] data) {
		
		return memoryWrite(ByteBuffer.wrap(data));
	}
	
	// Remember to set the data's position before calling this method.
	
	int memoryWrite(
			ByteBuffer data) {

		if (bufferCurrentPosition < bufferOffset) {
			System.err.println("Cannot write to a memory file before the buffer start.");
			return 0;
		}
		
		int actualPosition = (int) (bufferCurrentPosition - bufferOffset);
		int countBytesToWrite = data.remaining();
		int minimumBufferLimit = actualPosition + countBytesToWrite;
		buffer.position(actualPosition);
		
		// If the memory buffer does not have enough capacity, make it bigger. This is the
		// expensive option.
		if (minimumBufferLimit > buffer.capacity()) {
			ByteBuffer biggerBuffer = 
				ByteBuffer.allocate(((int) (minimumBufferLimit / ALLOCATION_SIZE) + 1) * ALLOCATION_SIZE);
			biggerBuffer.limit(minimumBufferLimit);
			buffer.flip();
			biggerBuffer.put(buffer);
			buffer = biggerBuffer;
		}
		
		if (minimumBufferLimit > buffer.limit())
			buffer.limit(minimumBufferLimit);
		
		bufferCurrentPosition += countBytesToWrite;
		buffer.put(data);
		
		return countBytesToWrite;
	}

//
//		//! Read from a memory file buffer
//
//		/*! \note This can be overridden in classes derived from MXFFile to give different memory read behaviour */
//
//		virtual size_t MemoryRead(UInt8 *Data, size_t Size);

	int memoryRead(
			byte[] data) {
		
		if (bufferCurrentPosition < bufferOffset) {
			System.err.println("Cannot read from a memory file before the buffer start.");
			return 0;
		}
		
		int actualPosition = (int) (bufferCurrentPosition - bufferOffset);

		if (actualPosition >= buffer.limit()) {
			System.err.println("Cannot read beyond the end of a memory buffer.");
			return 0;
		}
		
		int maxBytes = buffer.limit() - actualPosition;
		buffer.position(actualPosition);
		
		int readSize = (data.length > maxBytes) ? maxBytes : data.length;

		buffer.get(data, 0, readSize);

		bufferCurrentPosition += readSize;
		return readSize;
	}

	// For testing
	ByteBuffer getMemoryBuffer() {
		
		return buffer;
	}
	
	boolean isMemoryFile() {
		
		return isMemoryFile;
	}
	
	public boolean isOpen() {
		
		return isOpen;
	}
	
	long getBufferOffset() {
		
		return bufferOffset;
	}
	
	public String getName() {
		
		if (isMemoryFile)
			return "memory file";
		return fileHandle.getName();
	}
	
	public boolean lock() {
		
		if ((!isOpen) || (isMemoryFile))
			return false;
		
		try {
			if (lock != null) {
				if (!lock.isValid()) lock = null;
				else {
					if (lock.position() != 0l)
						lock.release();
					else
						return true;
				}
			}
			
			lock = fileChannel.tryLock();
		}
		catch (IOException ioe) {
			return false;
		}
		
		return (lock != null);
	}
	
	
	public boolean lockForward() {
		
		if ((!isOpen) || (isMemoryFile))
			return false;
		
		try {
			if (lock != null) {
				if (!lock.isValid()) lock = null;
				else {
					if (lock.position() != fileChannel.position())
						lock.release();
					else
						return true;
				}
			}
			
			lock = fileChannel.tryLock(fileChannel.position(), Long.MAX_VALUE - fileChannel.position(), false);
		}
		catch (IOException ioe) {
			return false;
		}
		
		return (lock != null);
	}
	
	public void unlock() {
		
		try {
			if (lock != null) {
				lock.release();
			}
		}
		catch (IOException ioe) { }
		finally {
			lock = null;
		}
	}
	
	public void startDigest(
			String digestType) 
		throws NoSuchAlgorithmException {
		
		digest = MessageDigest.getInstance(digestType);
	}
	
	public byte[] getDigestValue() {
		
		if (digest == null)
			return null;
		return digest.digest();
	}
	
	public long getFileSize() 
		throws IOException {
		
		return fileChannel.size();
	}
	
	public MXFFile clone() {
		
		try {
			return (MXFFile) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Implements Cloneable so should never happen
			throw new InternalError(cnse.getMessage());
		}
	}
	
	/**
	 * <p>Reads an MXF file specified by filename and returns the preface object as stored in the 
	 * header of the file.</p>
	 * 
	 * <p>Errors from reading the file are currently directed to the system error stream.</p>
	 * 
	 * <p>Warning: This code has yet to be fully tested and you use it at your own risk!</p>
	 * 
	 * @param fileName Name of the MXF file to read.
	 * @return Preface from the header of the file.
	 * 
	 * @throws Exception Any exception thrown when reading the MXF file.
	 * 
	 * @see #main(String[])
	 */
	public final static Preface readPrefaceFromMXF(
			String fileName) 
		throws Exception {
		
		PrefaceImpl preface = null;
		
		MXFFileImpl material = new MXFFileImpl();
		File mxfFileReference = new File(fileName);
		try {
			material.open(mxfFileReference.getAbsolutePath());
			
			material.readRunIn();
			
			PartitionPack partitionPack = PartitionPackImpl.readPartitionPack(material);
			System.err.println(XMLBuilder.toXML(partitionPack));
			
			long headerLimit = material.tell() + partitionPack.getHeaderByteCount();

			long length;
			ByteBuffer buffer;
			UL key = material.readKey();
			
			if (MXFBuilder.isKLVFill(key)) {
				length = material.readBER();
				buffer = material.read((int) length);
			}
			else {
				material.seek(material.tell() - 16);
			}

			PrimerPack primerPack = readPrimerPack(material);
//			System.out.println(XMLBuilder.toXML(primerPack));
//			System.out.println();
			
			key = material.readKey();
			System.out.println(key.toString());
			
			if (MXFBuilder.isKLVFill(key)) {
				length = material.readBER();
				buffer = material.read((int) length);
			}
			else {
				material.seek(material.tell() - 16);
			}

			Map<AUIDImpl, MetadataObject> referenceTable = new HashMap<AUIDImpl, MetadataObject>();
			List<ResolutionEntry> resolutions = new Vector<ResolutionEntry>();
			
			while (material.tell() < headerLimit) {
				
				key = material.readKey();
				length = material.readBER();
				buffer = material.read((int) length);
				buffer.rewind();
				
				if (MXFBuilder.isKLVFill(key))
					continue;
				
				MetadataObject metadataFromFile = 
					MXFBuilder.readLocalSet((AUIDImpl) key, buffer, primerPack, referenceTable, resolutions);
				
				if (metadataFromFile instanceof PrefaceImpl)
					preface = (PrefaceImpl) metadataFromFile;
				
//				if (metadataFromFile != null)
//					System.out.println(XMLBuilder.toXML(metadataFromFile));
			}
			
			// Resolve references
			for ( ResolutionEntry resolutionEntry : resolutions ) {
				resolutionEntry.resolve(referenceTable);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (material != null)
				material.close();
		}
		
		return preface;
	}
	
	/**
	 * <p>Temporary application to test the reading of MXF files with MAJ. Use with caution! The application
	 * reads an MXF file, reports any warnings encountered when reading the file, reports the time taken to 
	 * initialize and read the file and then dumps the preface as AAF XML (SMPTE registered data XML).</p>
	 * 
	 * <p>The first argument is an MXF filename. Following arguments provide the name of classes that 
	 * should be ignored during parsing. This provides a means to read a file with bad data by listing
	 * classes to be skipped.</p>
	 * 
	 * <p>Example usage:</p>
	 * 
	 * <pre>
	 * java -classpath maj.jar tv.amwa.maj.io.mxf.MXFFile MyFile.mxf
	 * </pre>
	 * 
	 * @param args First argument is the filename of an MXF to read. The following arguments provide names of
	 * classes to be ignored.
	 * 
	 * @see #readPrefaceFromMXF(String)
	 */
	public final static void main(String args[]) {
		
		if (args.length == 0) {
			System.exit(1);
		}
		
		if (args.length > 1) {
			for ( int u = 1 ; u < args.length ; u++ )
				MXFBuilder.ignoreProperty(args[u]);
		}
		
		long initStartTime = System.nanoTime();
		MediaEngine.initializeAAF();
		long initEndTime = System.nanoTime();

		try {
			Preface preface = readPrefaceFromMXF(args[0]);
			
			System.out.println("Initializing AAF classes took :" + (initEndTime - initStartTime) + "ns");
			long fileReadEntTime = System.nanoTime();
			
			System.out.println("Time to read file is " + (fileReadEntTime - initEndTime) + "ns.");

			System.out.println("\nFile as XML is:");
			System.out.println(preface.toString());
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}

	}
	
	private final static PrimerPack readPrimerPack(
			MXFFileImpl material) throws EndOfDataException {
		
		Warehouse.lookForClass(LocalTagEntryImpl.class);
		Warehouse.lookForClass(PrimerPackImpl.class);
		
		UL key = material.readKey();
		long length = material.readBER();
		ByteBuffer buffer = material.read((int) length);
		buffer.rewind();
		
		return (PrimerPack) MXFBuilder.readFixedLengthPack((AUID) key, buffer);
	}
	
	
}
