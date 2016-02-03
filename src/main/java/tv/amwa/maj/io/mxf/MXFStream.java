package tv.amwa.maj.io.mxf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.io.mxf.impl.CPSystemItemImpl;
import tv.amwa.maj.io.mxf.impl.EssenceElementImpl;
import tv.amwa.maj.io.mxf.impl.HeaderMetadataImpl;
import tv.amwa.maj.io.mxf.impl.PrimerPackImpl;
import tv.amwa.maj.io.mxf.impl.RandomIndexPackImpl;
import tv.amwa.maj.io.mxf.impl.ResolutionEntry;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.model.impl.PrefaceImpl;
import tv.amwa.maj.record.impl.AUIDImpl;

public class MXFStream {

	static final int RIP_BYTES_MAX = 262144;
	
	public static final MXFUnit readNextUnit(
			InputStream stream,
			long sizeLimit)
		throws IOException {
		
		KeyAndConsumed kandc = readKey(stream);
		
		if (kandc.getConsumed() > sizeLimit)
			throw new MAJMXFStreamException("Size limit exceeded when reading key for next MXF unit.");
		
		UnitType unitType = UnitType.typeFromKey(kandc.getKey());
		
		switch (unitType) {
	
		case HeaderClosedCompletePartitionPack:
		case HeaderClosedIncompletePartitionPack:
		case HeaderOpenCompletePartitionPack:
		case HeaderOpenIncompletePartitionPack:
		case FooterClosedCompletePartitionPack:
		case FooterClosedIncompletePartitionPack:
		case BodyClosedCompletePartitionPack:
		case BodyClosedIncompletePartitionPack:
		case BodyOpenCompletePartitionPack:
		case BodyOpenIncompletePartitionPack:
			return readPartitionPack(stream, kandc.getKey());
		case HeaderMetadata:
			return readHeaderMetadata(stream, kandc.getKey(), sizeLimit - kandc.getConsumed());
		case ContentPackageEssenceElement:
		case ContentPackagePicture:
		case ContentPackageSound:
		case ContentPackageData:
		case GenericContainerEssenceElement:
		case GenericContainerPicture:
		case GenericContainerSound:
		case GenericContainerData:
		case GenericContainerCompound:
			return readEssenceElement(stream, kandc.getKey());
		case ContentPackageSystemItem:
			return readCPSystemItem(stream, kandc.getKey());
		case IndexTableSegment:
			return readIndexTableSegment(stream, kandc.getKey());
		case RandomIndexPack:
			return readRandomIndexPack(stream, kandc.getKey());
		case GenericContainerSystemItem: // FIXME should have a generic container system item reader
		case Unknown:
		default:
			throw new MAJMXFStreamException("Unexpected element encountered when reading input stream, starting with key '" +
					kandc.getKey().toString() + "'.");
		}
	}

	/**
	 * <p>Read a {@linkplain RandomIndexPack random index pack (RIP)} from the given input stream, using the
	 * given size to efficiently skip to the end of the file to find the RIP. As the RIP is always located at 
	 * the end of the file, the input stream is always closed following this operation.</p>
	 * 
	 * @param stream Stream of MXF data to read and retrieve the RIP from.
	 * @param size Total size of the input stream measured in bytes, which must be known in advance of a call to this method. 
	 * @return Random index pack contained in the given input stream.
	 * 
	 * @throws IOException Problem occurred when reading the file or no RIP is contained within the file.
	 */
	public final static RandomIndexPack readRandomIndexPack(
			InputStream stream,
			long size) 
		throws IOException {
		
		long skipBytes = (size > RIP_BYTES_MAX) ? size - RIP_BYTES_MAX : 0l;
		byte[] lastBytesOfFile = new byte[RIP_BYTES_MAX];
		int bytesRead;
		try {
			skipForward(stream, skipBytes);
			bytesRead = stream.read(lastBytesOfFile);
		}
		finally {
			if (stream != null)
				stream.close();
		}
		
		if (bytesRead < 36)
			throw new MAJMXFStreamException("Input stream does not contain RIP as total bytes read from file is too low.");
		
		ByteBuffer buffer = ByteBuffer.wrap(lastBytesOfFile, 0, bytesRead);
		buffer.position(buffer.limit() - 4);
		int ripOffset = buffer.getInt();
		if (ripOffset > buffer.limit())
			throw new MAJMXFStreamException("Input stream does not contain a valid RIP offset value. File does not contain a readable RIP.");
		
		buffer.position(buffer.limit() - ripOffset);
		RandomIndexPack rip = RandomIndexPackImpl.createFromBytes(buffer);
		
		if (rip == null)
			throw new MAJMXFStreamException("Input stream does not contain a valid RIP.");
		
		return rip;
	}

	public final static HeaderMetadata readHeaderMetadata(
			InputStream stream,
			long headerSize)
		throws IOException {
				
		KeyAndConsumed kandc = readKey(stream);
		return readHeaderMetadata(stream, kandc.getKey(), headerSize - 16);
	}
	
	final static HeaderMetadata readHeaderMetadata(
			InputStream stream,
			UL primerKey,
			long headerSize) 
		throws IOException {
				
		FixedLengthPack candidatePack = null;
		PrimerPack primerPack = null;
		long primerSize = 0l;
		try {
			LengthAndConsumed landc = readBERLength(stream);
			primerSize = landc.getLength();
			ByteBuffer primerBytes = readValue(stream, primerSize);
			headerSize -= landc.getTotal();
			candidatePack = MXFBuilder.readFixedLengthPack((AUIDImpl) primerKey, primerBytes);
			primerPack = (PrimerPack) candidatePack;
		}
		catch (EndOfDataException eode) {
			throw new MAJMXFStreamException("Insufficient bytes in header bytes read from stream to create a primer pack.");
		}
		catch (IllegalArgumentException ioe) {
			throw new MAJMXFStreamException("Insufficient bytes in header bytes read from stream to create a primer pack, " +
					"detected on setting primer pack buffer limit for primer pack size '" + primerSize + "'.");
		}
		catch (BufferUnderflowException bue) {
			throw new MAJMXFStreamException("Insufficient bytes in header bytes read from stream to create a primer length.");
		}
		catch (ClassCastException cce) {
			throw new MAJMXFStreamException("Expected a primer pack in header metadata bytes but found an instance of '" +
					candidatePack.getClass().getCanonicalName() + "'.");
		}
		
		Map<AUIDImpl, MetadataObject> referenceTable = new HashMap<AUIDImpl, MetadataObject>();
		List<ResolutionEntry> resolutions = new Vector<ResolutionEntry>();
		
		Preface preface = null;
		try {
			while (headerSize > 0l) {
			
				UL metadataKey = readSingleKey(stream);	
				headerSize -= 16;
				if (MXFBuilder.isKLVFill(metadataKey)) {
					headerSize -= readPastFill(stream);
					continue;
				}
				
				LengthAndConsumed landc = readBERLength(stream);
				ByteBuffer headerBytes = readValue(stream, landc.getLength());
				headerSize -= landc.getTotal();
				
				MetadataObject metadataFromFile = 
					MXFBuilder.readLocalSet((AUIDImpl) metadataKey, headerBytes, primerPack, referenceTable, resolutions);
				
				if (metadataFromFile instanceof Preface)
					preface = (PrefaceImpl) metadataFromFile;
			
//				 if (metadataFromFile != null)
//					System.out.println(XMLBuilder.toXML(metadataFromFile));
			}
		}
		catch (Exception e) {
			throw new MAJMXFStreamException("Problem reading header metadata from file, caused by a " + e.getClass().getName() + ": " +
					e.getMessage() + ".",
					e);
		}

		if (preface == null)
			throw new MAJMXFStreamException("No preface local set found in MXF header metadata.");
		
		// Resolve references
		for ( ResolutionEntry resolutionEntry : resolutions ) {
			resolutionEntry.resolve(referenceTable);
		}
		
		return new HeaderMetadataImpl(primerPack, preface);
	}
	
	public final static PartitionPack readPartitionPack(
			InputStream stream)
		throws IOException {
		
		KeyAndConsumed kandc = readKey(stream);
		return readPartitionPack(stream, kandc.getKey());
	}
	
	final static PartitionPack readPartitionPack(
			InputStream stream,
			UL key)
		throws IOException {
		
		long size = readBERLength(stream).getLength();
		
		if (size > 4096l)
			throw new MAJMXFStreamException("Extremely unlikely size for a partition pack '" + size + "' when reading a stream.");
		
		ByteBuffer packValue = readValue(stream, size);
		
		FixedLengthPack candidatePack = null;
		try {
			candidatePack = MXFBuilder.readFixedLengthPack(key, packValue);
		}
		catch (EndOfDataException eode) {
			throw new MAJMXFStreamException("Insufficient bytes read from stream to create a valid partition pack.");
		}
		
		try {
			return (PartitionPack) candidatePack;
		}
		catch (ClassCastException cce) {
			throw new MAJMXFStreamException("Value read from stream was not a partition pack as expected, but rather an instance of '" +
					candidatePack.getClass().getCanonicalName() + "'.");
		}
	}
	
	public final static EssenceElement readEssenceElement(
			InputStream stream)
		throws IOException {
		
		KeyAndConsumed kandc = readKey(stream);
		return readEssenceElement(stream, kandc.getKey());
	}
	
	final static EssenceElement readEssenceElement(
			InputStream stream,
			UL essenceKey) 
		throws IOException {
		
		long size = readBERLength(stream).getLength();
		byte[] elementData = new byte[(int) size];
		
		if (stream.read(elementData, 0, (int) size) < size) {
			throw new MAJMXFStreamException("Failed to read complete essence element into memory.");
		}
		
		if (!EssenceElementImpl.isEssenceElement(essenceKey))
			throw new MAJMXFStreamException("Expected essence element key but found '" + essenceKey.toString() + "'.");

		return EssenceElementImpl.make(essenceKey, ByteBuffer.wrap(elementData));
	}
	
	public final static int readEssenceElementWithTrackID(
			InputStream stream,
			byte[] elementData) 
		throws IOException {
		
		KeyAndConsumed kandc = readKey(stream);
		return readEssenceElementWithTrackID(stream, kandc.getKey(), elementData);
	}
	
	final static int readEssenceElementWithTrackID(
			InputStream stream,
			UL essenceElementKey,
			byte[] elementData)
		throws IOException {
		
		long size = readBERLength(stream).getLength();
		
		if (elementData.length < size)
			throw new MAJMXFStreamException("Insufficient space in element data array to read in an essence element.");
		
		// FIXME handle bufferred streams, as for readEssenceElementWithBytesRead
		if (stream.read(elementData, 0, (int) size) < size) {
			throw new MAJMXFStreamException("Failed to read complete essence element into memory.");
		}
		
		if (!EssenceElementImpl.isEssenceElement(essenceElementKey))
			throw new MAJMXFStreamException("Expected essence element key but found '" + essenceElementKey.toString() + "'.");
		
		byte[] keyBytes = essenceElementKey.getUniversalLabel();
		int essenceTrackID = ((keyBytes[12] & 0xff) << 24) | ((keyBytes[13] & 0xff) << 16) |
				((keyBytes[14] & 0xff) << 8) | (keyBytes[15] & 0xff);
		return essenceTrackID;		
	}
	
	public final static int readEssenceElementWithBytesRead(
			InputStream stream,
			byte[] elementData) 
		throws IOException {
		
		KeyAndConsumed kandc = readKey(stream);
		return readEssenceElementWithBytesRead(stream, kandc.getKey(), elementData);
	}
	
	final static int readEssenceElementWithBytesRead(
			InputStream stream,
			UL essenceElementKey,
			byte[] elementData)
		throws IOException {
		
		long size = readBERLength(stream).getLength();
		
		if (elementData.length < size)
			throw new MAJMXFStreamException("Insufficient space in element data array to read in an essence element.");
		
		int totalBytesRead = 0;
		while (totalBytesRead < size) {
			int bytesRead = stream.read(elementData, totalBytesRead, (int) (size - totalBytesRead));
			if (bytesRead <= -0) break;
			totalBytesRead += bytesRead;
		}
		if (totalBytesRead < size) {
			throw new MAJMXFStreamException("Failed to read complete essence element into memory.");
		}
		
		if (!EssenceElementImpl.isEssenceElement(essenceElementKey))
			throw new MAJMXFStreamException("Expected essence element key but found '" + essenceElementKey.toString() + "'.");
		
		return totalBytesRead;		
	}

	public final static void writeEssenceElement(
			OutputStream stream,
			int essenceTrackID,
			byte[] elementData) 
		throws IOException {
	
		byte[] essenceElementBase = ((UL) EssenceElement.essenceElementBase).getUniversalLabel();
		stream.write(essenceElementBase, 0, 12);
		byte[] essenceTrackIDBytes = ByteBuffer.allocate(4).putInt(essenceTrackID).array();
		stream.write(essenceTrackIDBytes);
		
		writeBERLength(stream, elementData.length, 8);
		stream.write(elementData);
	}
	
	public final static void writeEssenceElement(
			OutputStream stream,
			int essenceTrackID,
			byte[] elementData,
			int offset,
			int length) 
		throws IOException {
	
		byte[] essenceElementBase = ((UL) EssenceElement.essenceElementBase).getUniversalLabel();
		stream.write(essenceElementBase, 0, 12);
		byte[] essenceTrackIDBytes = ByteBuffer.allocate(4).putInt(essenceTrackID).array();
		stream.write(essenceTrackIDBytes);
		
		writeBERLength(stream, length, 8);
		stream.write(elementData, offset, length);
	}

	public final static CPSystemItem readCPSystemItem(
			InputStream stream)
		throws IOException {
		
		KeyAndConsumed kandc = readKey(stream);
		return readCPSystemItem(stream, kandc.getKey());
	}
	
	final static CPSystemItem readCPSystemItem(
			InputStream stream,
			UL cpSystemItemKey) 
		throws IOException {
		
		long size = readBERLength(stream).getLength();

		byte[] cpSystemItemBytes = new byte[(int) size];
		
		if (stream.read(cpSystemItemBytes, 0, (int) size) < size) 
			throw new MAJMXFStreamException("Failed to read complete CP system item into memory.");
		
		if (!cpSystemItemKey.equals(CPSystemItemImpl.key))
			throw new MAJMXFStreamException("Expected a CP system item key but found '" + cpSystemItemKey.toString() + "'.");

		CPSystemItem cpSystemItem = CPSystemItemImpl.make(ByteBuffer.wrap(cpSystemItemBytes));
		return cpSystemItem;
	}
	
	public final static IndexTableSegment readIndexTableSegment(
			InputStream stream)
		throws IOException {
		
		KeyAndConsumed kandc = readKey(stream);
		return readIndexTableSegment(stream, kandc.getKey());
	}
	
	final static IndexTableSegment readIndexTableSegment(
			InputStream stream,
			UL key) 
		throws IOException {
		
		// TODO check the key?
		long size = readBERLength(stream).getLength();
		
		ByteBuffer indexValue = readValue(stream, size);
		
		Map<AUIDImpl, MetadataObject> referenceTable = new HashMap<AUIDImpl, MetadataObject>();
		List<ResolutionEntry> resolutions = new Vector<ResolutionEntry>();

		try {
			MetadataObject candidateSegment = 
					MXFBuilder.readLocalSet(key, indexValue, IndexTable.indexPrimer, referenceTable, resolutions);

			return (IndexTableSegment) candidateSegment;
		}
		catch (Exception e) {
			throw new MAJMXFStreamException(e.getClass().getName() + " throw when trying to read an index table segment.", e);
		}
	}
	
	public final static RandomIndexPack readRandomIndexPack(
			InputStream stream)
		throws IOException {
		
		KeyAndConsumed kandc = readKey(stream);
		return readRandomIndexPack(stream, kandc.getKey());
	}
	
	final static RandomIndexPack readRandomIndexPack(
			InputStream stream,
			UL key)
		throws IOException {
		
		// TODO check the key?
		long size = readBERLength(stream).getLength();
		
		ByteBuffer ripBytes = readValue(stream, size);
		
		try {
			RandomIndexPack rip = RandomIndexPackImpl.createFromBytes(ripBytes);
			
			return rip;
		}
		catch (BufferUnderflowException bue) {
			throw new MAJMXFStreamException("Insufficient bytes in buffer to read a complete random index pack.");
		}
	}
			
	public final static HeaderMetadata readFooterHeaderMetadata(
			InputStream inputStream,
			int pag)
		throws IOException {
		
		// TODO
		
		return null;
	}

	public final static HeaderMetadata readFooterHeaderMetadata(
			InputStream inputStream,
			long footerPartitionOffset)
		throws IOException {
		
		// TODO 
		
		return null;
	}
	
	public final static void writePartitionPack(
			OutputStream stream,
			PartitionPack pack)
		throws IOException {
		
		long partitionPackSize = pack.getEncodedSize();
		ByteBuffer partitionBytes = ByteBuffer.allocate((int) partitionPackSize);
		try {
			MXFBuilder.writeFixedLengthPack((FixedLengthPack) pack, partitionBytes);
		} 
		catch (InsufficientSpaceException ise) {
			throw new MAJMXFStreamException("Unexpetedly, insufficient space for partition pack in buffer.", ise);
		}
		
		ClassDefinition theClass = MediaEngine.getClassDefinition(pack);
		writeKey(stream, (UL) theClass.getAUID());
		writeBERLength(stream, partitionPackSize, 4);
		writeValue(stream, partitionBytes);
	}
	
	public final static void writeHeaderMetadata(
			OutputStream stream,
			Preface preface)
		throws IOException {
		
		PrimerPack primerPack = new PrimerPackImpl();
		primerPack.addLocalTagEntry(MXFConstants.InstanceTag, MXFConstants.InstanceUID);
		long lengthOfAllSets = PrimerPackImpl.addPropertiesForObject(primerPack, preface);

		ByteBuffer primerAndPreface = ByteBuffer.allocate((int) (lengthOfAllSets + PrimerPackImpl.lengthAsBytes(primerPack)));
		
		try {
			primerAndPreface.rewind();
			PrimerPackImpl.writeAsBytes(primerPack, primerAndPreface);
			List<PropertyValue> stillToWrite = new ArrayList<PropertyValue>();
			MXFBuilder.writeLocalSet(preface, primerAndPreface, primerPack, stillToWrite);
			
			while (stillToWrite.size() > 0) {
				PropertyValue headItem = stillToWrite.remove(0);
				MXFBuilder.writeLocalSet(headItem, primerAndPreface, primerPack, stillToWrite);
			}
		}
		catch (InsufficientSpaceException ise) {
			throw new MAJMXFStreamException("Unxepectedly, insufficient space to write primer pack and local sets.", ise);
		}
		catch (BufferOverflowException boe) {
			throw new MAJMXFStreamException("Unxepectedly, insufficient space to write primer pack and local sets.", boe);		
		}
		
		writeValue(stream, primerAndPreface);
	}
	
	public final static void writeIndexTableSegment(
			OutputStream stream,
			IndexTableSegment indexTableSegment) 
		throws IOException {
		
		long indexSegmentSize = MXFBuilder.lengthOfLocalSet(indexTableSegment);
		
		ByteBuffer indexBuffer = ByteBuffer.allocate((int) (indexSegmentSize + 20));
		try {
			indexBuffer.rewind();
			MXFBuilder.writeLocalSet(indexTableSegment, indexBuffer, IndexTable.indexPrimer, new ArrayList<PropertyValue>());
			indexBuffer.rewind();
		}
		catch (InsufficientSpaceException ise) {
			throw new MAJMXFStreamException("Unxepectedly, insufficient space to write index table segment local set.", ise);
		}
		catch (BufferOverflowException boe) {
			throw new MAJMXFStreamException("Unxepectedly, insufficient space to write index table segment local set.", boe);		
		}

		writeValue(stream, indexBuffer);
	}
	
	public final static void writeRandomIndexPack(
			OutputStream stream,
			RandomIndexPack rip) 
		throws IOException {
		
		writeKey(stream, RandomIndexPack.ripKeyValue);
		writeBERLength(stream, 12 * rip.count() + 4, 4);
		
		for ( RandomIndexItem item : rip.getPartitionIndex() ) {
			int bodySID = item.getBodySID();
			stream.write(ByteBuffer.allocate(4).putInt(bodySID).array());
			long byteOffset = item.getByteOffset();
			stream.write(ByteBuffer.allocate(8).putLong(byteOffset).array());
		}
		
		stream.write(ByteBuffer.allocate(4).putInt(rip.getLength()).array());
	}
	
	public final static KeyAndConsumed readKey(
			InputStream stream)
		throws IOException {
		
		byte[] keyBytes = new byte[16];
		int bytesRead = stream.read(keyBytes);
		if (bytesRead < 0)
			throw new MAJMXFStreamException("Unexpectedly reached the end of the stream in the middle of reading a key.");
		if (bytesRead < 16)
			throw new MAJMXFStreamException("Insufficient bytes remaing in stream to read a complete key.");
		
		long consumed = 16l;
		while (bytesRead == 16) {
				
			byte[] keySwap = new byte[16];
			for ( int x = 0 ; x < 8 ; x++ ) {
				keySwap[x] = keyBytes[x + 8];
				keySwap[x + 8] = keyBytes[x];
			}
		
			UL readKey = new AUIDImpl(keySwap);
		
			if (!MXFBuilder.isKLVFill(readKey)) {
				return new KeyAndConsumed(readKey, consumed);
			}
			
			consumed += readPastFill(stream);
			
			bytesRead = stream.read(keyBytes);
			consumed += bytesRead;
		}
		
		throw new MAJMXFStreamException("Insufficient bytes remaing in stream to read a complete key.");		
	}
	
	public final static UL readSingleKey(
			InputStream stream)
		throws IOException {
		
		byte[] keyBytes = new byte[16];
		int bytesRead = stream.read(keyBytes);
		if (bytesRead < 0)
			throw new MAJMXFStreamException("Unexpectedly reached the end of the stream in the middle of reading a key.");
		if (bytesRead < 16)
			throw new MAJMXFStreamException("Insufficient bytes remaing in stream to read a complete key.");

		byte[] keySwap = new byte[16];
		for ( int x = 0 ; x < 8 ; x++ ) {
			keySwap[x] = keyBytes[x + 8];
			keySwap[x + 8] = keyBytes[x];
		}
	
		UL readKey = new AUIDImpl(keySwap);
		return readKey;
	}
	
	public static class KeyAndConsumed {
		
		private UL key;
		private long consumed;
		
		public KeyAndConsumed(
				UL key,
				long consumed) {
			
			this.key = key;
			this.consumed = consumed;
		}
		
		public UL getKey() {
			
			 return key;
		}
		
		public long getConsumed() {
			
			return consumed;
		}
	}
	
	public final static LengthAndConsumed readBERLength(
			InputStream stream)
		throws IOException {
		
		int first = stream.read();
		if (first == -1)
			throw new MAJMXFStreamException("Unexpectedly readed the end of the stream when reading a length.");

		if (first < 128) // top bit set not set
			return new LengthAndConsumed((long) first, 1l);
		
		int berTailLength = (int) (first & 0x7f);
		byte[] lengthData = new byte[berTailLength];
		
		int bytesRead = stream.read(lengthData);
		
		while (bytesRead < berTailLength) {
			System.out.println("Bytes read less than BER tail length when reading length, with bytesRead = " + bytesRead + 
					" and berTailLength = " + berTailLength + " and input stream " + stream.getClass().getCanonicalName());
	      if (bytesRead < 0)
		    throw new MAJMXFStreamException("Unexpectedly reached the end of the stream after the first byte when reading a length.");
	      bytesRead += stream.read(lengthData, bytesRead, berTailLength - bytesRead); 
		}
		
//		int bytesRead = stream.read(lengthData);
//		
//		if (bytesRead < 0)
//			throw new MAJMXFStreamException("Unexpectedly reached the end of the stream after the first byte when reading a length.");
//		if (bytesRead < berTailLength)
//			throw new MAJMXFStreamException("Insufficient bytes available in a stream when reading a length.");
		long lengthValue = 0l;
		
		for ( int u = 0 ; u < lengthData.length ; u++ )
			lengthValue = (lengthValue << 8) |
				(((lengthData[u]) >= 0) ? lengthData[u] : 256 + lengthData[u]);
		
		return new LengthAndConsumed(lengthValue, 1l + berTailLength);
	}
	
	public final static class LengthAndConsumed {
		
		private long length;
		private long consumed;
		
		public LengthAndConsumed(
				long length,
				long consumed) {
			
			this.length = length;
			this.consumed = consumed;
		}
		
		public long getLength() {
			return length;
		}
		
		public long getConsumed() {
			return consumed;
		}
		
		public long getTotal() {
			
			return length + consumed;
		}
	}
	
	public final static ByteBuffer readValue(
			InputStream stream,
			long size)
		throws IOException {
		
		if (size > Runtime.getRuntime().freeMemory()) {
			Runtime.getRuntime().gc();
			if (size > Runtime.getRuntime().freeMemory())
				throw new MAJMXFStreamException("Insufficient memory to read stream content value of size '" + size + 
						"' into a byte buffer.");
		}
		
		if (size > Integer.MAX_VALUE) {
			throw new MAJMXFStreamException("MAJ can only work with values up to '" + Integer.MAX_VALUE + "' in length.");			
		}
		
		byte[] valueBytes = new byte[(int) size];
		int bytesRead = stream.read(valueBytes);
		
		while (bytesRead < size) {
			System.out.println("Bytes read less than size on value read, with bytesRead = " + bytesRead + " and size = " + size + ".");
		    if (bytesRead == -1)
			  throw new MAJMXFStreamException("Unexpectedly reached the end of the stream when reading a value.");
		    bytesRead += stream.read(valueBytes, bytesRead, (int) (size - bytesRead));
		}
				
		return ByteBuffer.wrap(valueBytes);
	}
	
	public final static void writeKey(
			OutputStream stream,
			UL key)
		throws IOException {
		
		if (stream == null)
			throw new NullPointerException("Cannot write a key to a null stream.");
		if (key == null)
			throw new NullPointerException("Cannot write a null key to a stream.");
		
		byte[] keyBytes = key.getUniversalLabel();
		
		// Fix up class identifiers
		if ((keyBytes[4] == 0x02) && (keyBytes[5] == 0x06) && (keyBytes[6] == 0x01))
			keyBytes[5] = 0x53;

		stream.write(keyBytes);
	}
	
	public final static void writeBERLength(
			OutputStream stream,
			long length,
			int encodedBytes)
		throws IOException {
		
		if (stream == null)
			throw new NullPointerException("Cannot write a BER length to a null stream.");

		if (length < 0)
			throw new IllegalArgumentException("Cannot write a negative length value.");
		
		if ((encodedBytes < 1) || (encodedBytes > 9))
			throw new IllegalArgumentException("The number of encoded bytes must be between 1 and 9.");
		
		if (encodedBytes == 1) {
			if (length > 127)
				throw new IllegalArgumentException("The number of encoded bytes is 1 but the length is greater than 127.");
			
			stream.write((byte) length);
			return;
		}
		
		encodedBytes--;
		long maxValue = 2l << (encodedBytes * 8);
		if (length >= maxValue)
			throw new IllegalArgumentException("The given length of " + length + " is greater than the maximum value for the given number of bytes to encode at " + maxValue + ".");
		
		// Write the length byte
		stream.write(0x80 + encodedBytes);
		
		for ( int x = (encodedBytes - 1) ; x >= 0 ; x-- ) {
			long mask = 0xff << (x * 8);
			stream.write((int) ((length & mask) >> (x * 8)));
		}		
	}
	
	public final static void writeValue(
			OutputStream stream,
			ByteBuffer buffer)
		throws IOException {
		
		buffer.rewind();
		if (buffer.hasArray()) {
			stream.write(buffer.array());
		}
		else {
			while(buffer.hasRemaining()) {
				stream.write(buffer.get());
			}
			buffer.rewind();
		}
	}
	
	final static byte[] zeroBytes = new byte[4096];
	
	static {
		for ( int x = 0 ; x < zeroBytes.length ; x++ )
			zeroBytes[x] = (byte) 0;
	}
	
	public final static void writeFill(
			OutputStream stream,
			long totalLength) 
		throws IOException {
		
		if (stream == null)
			throw new NullPointerException("Cannot write a KLV fill item into a null stream.");
		if (totalLength < 20l)
			throw new IllegalArgumentException("Cannot write a fill value less than 20 bytes and '" + totalLength + "' is requested.");
		
		writeKey(stream, (UL) MXFConstants.KLVFill);
		int encodedLength = (totalLength < (2 << 24)) ? 4 : 8;
		totalLength -= (16 + encodedLength);
		writeBERLength(stream, totalLength, encodedLength);
		
		long bytesWritten = 0l;
		for ( long x = zeroBytes.length ; x < totalLength ; x += zeroBytes.length ) {
			stream.write(zeroBytes);
			bytesWritten += zeroBytes.length;
		}
	
		stream.write(zeroBytes, 0, (int) (totalLength - bytesWritten));
	}
	
	public final static long readPastFill(
			InputStream stream) 
		throws IOException {
		
		LengthAndConsumed landc = readBERLength(stream);
		skipForward(stream, landc.getLength());
		return landc.getTotal();
	}
	
	public final static long skipForward(
			InputStream sourceStream,
			long bytesToSkip)
		throws IOException {
		
		long bytesSkipped = 0l;
		long bytesSkippedThisTime = 0l;
		while (bytesSkipped < bytesToSkip) {
			bytesSkippedThisTime = sourceStream.skip(bytesToSkip - bytesSkipped);
			if (bytesSkippedThisTime <= 0l)
				break;
			bytesSkipped += bytesSkippedThisTime;
		}
		
		return bytesSkipped;
	}
}
