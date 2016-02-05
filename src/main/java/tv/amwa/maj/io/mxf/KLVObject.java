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
 * $Log: KLVObject.java,v $
 * Revision 1.6  2010/01/19 14:44:22  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
 *
 * Revision 1.5  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2009/02/13 15:27:46  vizigoth
 * Replaced DataChunk with core Java ByteBuffer.
 *
 * Revision 1.2  2009/02/13 14:27:29  vizigoth
 * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */
package tv.amwa.maj.io.mxf;

import java.nio.ByteBuffer;

import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8Array;
import tv.amwa.maj.io.mxf.impl.MXFFileImpl;

/**
 * <p>Implementation of a KLV triple value.</p>
 * 
 *
 *
 */
public class KLVObject {

	public enum KeyFormat {

		KEY_NONE,
		KEY_1_BYTE,
		KEY_2_BYTE,
		KEY_AUTO,
		KEY_4_BYTE
	};

	public enum LengthFormat {

		LEN_NONE,
		LEN_1_BYTE,
		LEN_2_BYTE,
		LEN_4_BYTE,
		LEN_BER
	};

	class KLVInfo {
		
		/** Source or destination file. */
		MXFFileImpl file;

		/** Offset of the first byte of the <b>key</b> as an offset into the file, or -1 if not available. */
		@Int64 long offset = -1;

		/** The length of the entire readable value space - in basic KLV types this is always valueLength, 
		 * derived types may add some hidden overhead. */
		@MXFLength long outerLength = 0;	

		/** Size of this object's KL in the source or destination file, or -1 if not known. */
		@Int32 int klSize = -1;

		/** Set to true once the data is "set". */
		boolean valid = false;
		
		KLVInfo() { }
	}

	/** Information on the source file. */
	private KLVInfo source = new KLVInfo();						

	/** Information on the destination file. */
	private KLVInfo destination = new KLVInfo();

	/** The universal label for this object, if known. */
	private UL theUL = null;						//!< The UL for this object (if known)

	/** Length value of the field. */
	private @MXFLength long valueLength = 0l;

	/** The raw data for this item, if available. */
	private ByteBuffer data = null;

	/** The offset of the first byte in the DataChunk from the start of the KLV value field. */
	private @Int64 long dataBaseOffset = 0l;


//## DRAGONS: Ensure any new properties are copied by the KLVObject --> KLVEObject copy constructor ##


	/**
	 * <p>Construct a KLV object where nothing is yet known about it.</p>
	 */
	public KLVObject() { }
	
	/**
	 * <p>Construct a KLV object with the given key.</p>
	 * 
	 * @param theUL Key for the new KLV object.
	 */
	public KLVObject(
			UL theUL) {
		
		if (theUL == null)
			theUL = null;
		else
			this.theUL = theUL.clone();
	}
	
	/**
	 * <p>Set the source details when an object has been read from a file, with the option to provide
	 * a byte offset.</p>
	 * 
	 * @param file The source file of this KLV object.
	 * @param location The byte offset of the start of the <em>key</em> of the KLV from the start of the file, or
	 * current position if -1.
	 * 
	 * @throws NullPointerException Cannot set the source of this KLV object to be a <code>null</code> value.
	 */
	public void setSource(
			MXFFileImpl file,
			@Int64 long location) 
		throws NullPointerException {
		
		if (file == null)
			throw new NullPointerException("Cannot set the source of this KLV object to be a null value.");
		
		source.valid = true;
		source.file = file;
		if (location < 0) 
			source.offset = file.tell();
		else
			source.offset = location;
		
		if (!destination.valid)
			destination = source;
	}

	/**
	 * <p>Set the source details when an object has been read from a file using the current position.</p>
	 * 
	 * @param file The source file of this KLV object.
	 * 
	 * @throws NullPointerException Cannot set the source of this KLV object to be a <code>null</code> value.
	 */
	public void setSource(
			MXFFileImpl file) 
		throws NullPointerException {
		
		setSource(file, -1);
	}

	/**
	 * <p>Set the destination details for this object when written to a file, with the option to 
	 * provide the file location.</p>
	 * 
	 * @param file The destination file for the KLV object.
	 * @param location The byte offset of the start of the <b>key</b> of the KLV from the start of the file, 
	 * or if -1 then the current position in that file will be used.
	 * 
	 * @throws NullPointerException Cannot set the destination for this KLV object to be a <code>null</code> value.
	 */
	public void setDestination(
			MXFFileImpl file,
			@Int64 long location) 
		throws NullPointerException {
	
		if (file == null)
			throw new NullPointerException("Cannot set the destination for this KLV object to be a null value.");
		
		// Prevent accidental overwrite of source data
		if (destination == source)
			destination = new KLVInfo();
		
		destination.valid = true;
		destination.file = file;
		
		if (location < 0)
			destination.offset = file.tell();
		else
			destination.offset = location;
	}
	
	/**
	 * <p>Set the destination details for this object when written to a file, using the current file
	 * location.</p>
	 * 
	 * @param file The destination file for the KLV object.
	 * @param location The byte offset of the start of the <b>key</b> of the KLV from the start of the file, 
	 * or if -1 then the current position in that file will be used.
	 * 
	 * @throws NullPointerException Cannot set the destination for this KLV object to be a 
	 * <code>null</code> value.
	 */
	public void setDestination(
			MXFFileImpl file) 
		throws NullPointerException {
		
		setDestination(file, -1);
	}

	/**
	 * <p>Get this KLV objects universal label or <em>key</em>. Note that this could be <code>null</code> 
	 * if not yet set.</p>
	 * 
	 * @return The KLV object's universal label.
	 */
	public UL getUL() {
		
		if (theUL == null)
			return null;
		else
			return theUL.clone();
	}

	/**
	 * <p>Set this KLV objects universal label or <em>key</em>.</p>
	 * 
	 * @param newUL New universal label for this KLV object, which can be <code>null</code>.
	 */
	public void setUL(
			UL newUL) {
		
		if (newUL == null)
			this.theUL = null;
		else
			this.theUL = newUL.clone();;
	}

	
	/**
	 * <p>Get the location of this KLV object within its ultimate parent.</p>
	 * 
	 * @return Location of this KLV object as an offset into the source file.
	 */
	public @Int64 long getLocation() {
		
		return source.offset;
	}

	/**
	 * <p>Get a textual description of where this KLV object came from.</p>
	 * 
	 * @return Description of where this KLV object came from.
	 */
	public String describeSource() {
		
		if (source.file != null) 
			return source.file.getName();
		else
			return "memory buffer";
	}

	/**
	 * <p>Get a textual description of exactly where this item came from, including its location.</p>
	 * 
	 * @return Textual description of exactly where this KLV object came from.
	 */
	public String describeSourceLocation() {
		
		if (source.file == null)
			return "KLVObject created in memory.";
		return "0x" + Long.toHexString(getLocation()) + " in " + describeSource();
	}

	/**
	 * <p>Get the size of the key and length of this KLV object. This does not include the
	 * size of the value.</p>
	 * 
	 * @return Size of the key and length of this KLV object.
	 */
	public @Int32 int getKLSize() {
		
		return (source.klSize >= 0 ? source.klSize : destination.klSize);
	}
	
	/**
	 * <p>Set the size of the key and length of this KLV object. This does not include the
	 * size of the value. Note that you cannot change the source size.</p>
	 * 
	 * @param klSize Size to set for the key and length.
	 */
	public void setKLSize(
			@Int32 int klSize) {
		
		destination.klSize = klSize;
	}

	public GenericContainerElementKind getGCElementKind() {
		
		// TODO
		return null;
		//! Get a GCElementKind structure

		// virtual GCElementKind GetGCElementKind(void) { return mxflib::GetGCElementKind(TheUL); }
	}
	
	public @UInt32 int getGCTrackNumber() {
		
		// TODO
		return 0;
		
		// ! Get the track number of this KLVObject (if it is a GC KLV, else 0)

		//virtual UInt32 GetGCTrackNumber(void) { return mxflib::GetGCTrackNumber(TheUL); };
	}

	//
	//
	////! Get the position of the first byte in the DataChunk as an offset into the file
	//
	///*! \return -1 if the data has not been read from a file (or the offset cannot be determined) 
	//
	// */
	//
	//
	
	public @Int64 long getDataBaseOffset() {
		
		return dataBaseOffset;
	}


//
//
////! Set the position of the first byte in the DataChunk as an offset into the file
//
///*! \note This function must be used with great care as data may will be written to this location
//
// */

	public void setDataBaseOffset(
			@Int64 long dataBaseOffset) {
		
		this.dataBaseOffset = dataBaseOffset;
	}
	
////! Read the key and length for this KLVObject from the current source
//
///*! \return The number of bytes read (i.e. KLSize)
//
// */
//
//virtual Int32 ReadKL(void) { return Base_ReadKL(); }
//

	public @Int32 int readKL() {
		
		return baseReadKL();
	}
	
//
////! Base version: Read the key and length for this KLVObject from the current source
//
///*! \return The number of bytes read (i.e. KLSize)
//
// *
//
// *  DRAGONS: This base function may be called from derived class objects to get base behaviour.
//
// *           It is therefore vital that the function does not call any "virtual" KLVObject
//
// *           functions, directly or indirectly.
//
// */
//
//Int32 Base_ReadKL(void);

	@Int32 int baseReadKL() {
		
		// TODO
		return 0;
	}
	
//
//
////! Read data from the start of the KLV value into the current DataChunk
//
///*! \param Size Number of bytes to read, if -1 all available bytes will be read (which could be billions!)
//
// *  \return The number of bytes read
//
// */
//
//virtual size_t ReadData(size_t Size = static_cast<size_t>(-1)) { return Base_ReadDataFrom(0, Size); }
//

	public @Int32 int readData(
			@Int32 int size) {
		
		// TODO
		return 0;
	}
	
//
////! Read data from a specified position in the KLV value field into the DataChunk
//
///*! \param Offset Offset from the start of the KLV value from which to start reading
//
// *  \param Size Number of bytes to read, if -1 all available bytes will be read (which could be billions!)
//
// *  \return The number of bytes read
//
// */
//
//virtual size_t ReadDataFrom(Position Offset, size_t Size = static_cast<size_t>(-1)) { return Base_ReadDataFrom(Offset, Size); }
//

	public int readDataFrom(
			@MXFPosition long offset,
			int size) {
		
		return baseReadDataFrom(offset, size);
	}
	
	public int readDataFrom(
			@MXFPosition long offset) {
		
		return readDataFrom(offset, -1);
	}
	
//
////! Base verion: Read data from a specified position in the KLV value field into the DataChunk
//
///*! \param Offset Offset from the start of the KLV value from which to start reading
//
// *  \param Size Number of bytes to read, if -1 all available bytes will be read (which could be billions!)
//
// *  \return The number of bytes read
//
// *
//
// *  DRAGONS: This base function may be called from derived class objects to get base behaviour.
//
// *           It is therefore vital that the function does not call any "virtual" KLVObject
//
// *           functions, directly or indirectly.
//
// */
//
//inline size_t Base_ReadDataFrom(Position Offset, size_t Size = static_cast<size_t>(-1)) { return Base_ReadDataFrom(Data, Offset, Size); }
//

	int baseReadDataFrom(
			@MXFPosition long offset,
			int size) {
		
		return baseReadDataFrom(data, offset, size);
	}
	
	int baseReadDataFrom(
			@Int64 long offset) {
		
		return baseReadDataFrom(data, offset, -1);
	}
	
//
////! Base verion: Read data from a specified position in the KLV value field into the DataChunk
//
///*! \param Offset Offset from the start of the KLV value from which to start reading
//
// *  \param Size Number of bytes to read, if -1 all available bytes will be read (which could be billions!)
//
// *  \return The number of bytes read
//
// *
//
// *  \note This function can write to a buffer other than the KLVObject's main buffer if required, 
//
// *        however the file pointer will be updated so care must be used when mixing reads
//
// *
//
// *  DRAGONS: This base function may be called from derived class objects to get base behaviour.
//
// *           It is therefore vital that the function does not call any "virtual" KLVObject
//
// *           functions, directly or indirectly.
//
// */
//
//size_t Base_ReadDataFrom(DataChunk &Buffer, Position Offset, size_t Size = static_cast<size_t>(-1));
//

	@Int32 int baseReadDataFrom(
			ByteBuffer buffer,
			@MXFPosition long position,
			int size) 
		throws NullPointerException {
		
		// TODO
		return 0;
	}
	
	@Int32 int baseReadDataFrom(
			ByteBuffer buffer,
			@MXFPosition long position) {
		
		return baseReadDataFrom(buffer, position, -1);
	}
//
////! Write the key and length of the current DataChunk to the destination file
//
///*! The key and length will be written to the source file as set by SetSource.
//
// *  If LenSize is zero the length will be formatted to match KLSize (if possible!)
//
// */
//
//virtual Int32 WriteKL(Int32 LenSize = 0) { return Base_WriteKL(LenSize); }
//

	public @Int32 int writeKL(
			@Int32 int lengthSize) {
		
		return baseWriteKL(lengthSize);
	}
	
	public @Int32 int writeKL() {
		
		return baseWriteKL(0);
	}
	
//
////! Base version: Write the key and length of the current DataChunk to the destination file
//
///*! The key and length will be written to the source file as set by SetSource.
//
// *  If LenSize is zero the length will be formatted to match KLSize (if possible!).
//
// *  The length written can be overridden by using parameter NewLength
//
// *
//
// *  DRAGONS: This base function may be called from derived class objects to get base behaviour.
//
// *           It is therefore vital that the function does not call any "virtual" KLVObject
//
// *           functions, directly or indirectly.
//
// */
//
//Int32 Base_WriteKL(Int32 LenSize = 0, Length NewLength = -1);
	
	@Int32 int baseWriteKL(
			@Int32 int lengthSize,
			@MXFLength long newLength) {
		
		// TODO
		return 0;
	}
	
	@Int32 int baseWriteKL(
			@Int32 int lengthSize) {
		
		return baseWriteKL(lengthSize, -1l);
	}
//
////! Write (some of) the current data to the same location in the destination file
//
///*! \param Size The number of bytes to write, if -1 all available bytes will be written
//
// *  \return The number of bytes written
//
// */
//
//virtual size_t WriteData(size_t Size = static_cast<size_t>(-1)) { return WriteDataFromTo(0, 0, Size); }
//

	public int writeData(
			int size) {
		
		return writeDataFromTo(0, 0, size);
	}
	
	public int writeData() {
		
		return writeDataFromTo(0, 0, -1);
	}
	
//
////! Write (some of) the current data to the same location in the destination file
//
///*! \param Start The offset within the current DataChunk of the first byte to write
//
// *  \param Size The number of bytes to write, if -1 all available bytes will be written
//
// *  \return The number of bytes written
//
// */
//
//virtual size_t WriteDataFrom(Position Start, size_t Size = static_cast<size_t>(-1)) { return WriteDataFromTo(0, Start, Size); }
//
	public int writeDataFrom(
			@MXFPosition long start,
			int size) {
		
		return writeDataFromTo(0, start, size);
	}
	
	public int writeDataFrom(
			@MXFPosition long start) {
		
		return writeDataFromTo(0, start, -1);
	}
//
////! Write (some of) the current data to a different location in the destination file
//
///*! \param Offset The offset within the KLV value field of the first byte to write
//
// *  \param Size The number of bytes to write, if <= 0 all available bytes will be written
//
// *  \return The number of bytes written
//
// */
//
//virtual size_t WriteDataTo(Position Offset, size_t Size = static_cast<size_t>(-1)) { return WriteDataFromTo(Offset, 0, Size); }
//
	public int writeDataTo(
			@MXFPosition long offset,
			int size) {
		
		return writeDataFromTo(offset, 0, size);
	}
	
	public int writeDataTo(
			@MXFPosition long offset) {
		
		return writeDataFromTo(offset, 0, -1);
	}

//
////! Write (some of) the current data to the same location in the destination file
//
///*! \param Offset The offset within the KLV value field of the first byte to write
//
// *  \param Start The offset within the current DataChunk of the first byte to write
//
// *  \param Size The number of bytes to write, if -1 all available bytes will be written
//
// *  \return The number of bytes written
//
// */
//
//virtual size_t WriteDataFromTo(Position Offset, Position Start, size_t Size = static_cast<size_t>(-1))
//
//{
//
//	// Calculate default number of bytes to write
//
//	Length BytesToWrite = Data.Size - Start;
//
//
//
//	// Write the requested size (if valid)
//
//	if((Size > 0) && (Size < BytesToWrite)) BytesToWrite = Size;
//
//
//
//	// Sanity check the size of this chunk
//
//	if((sizeof(size_t) < 8) && (BytesToWrite > 0xffffffff))
//
//	{
//
//		error("Tried to write > 4GBytes, but this platform can only handle <= 4GByte chunks\n");
//
//		return 0;
//
//	}
//
//
//
//	return Base_WriteDataTo(&Data.Data[Start], Offset, static_cast<size_t>(BytesToWrite));
//
//}
//

	public int writeDataFromTo(
			@MXFPosition long offset,
			@MXFPosition long start,
			int size) {
		
		long bytesToWrite = data.capacity() - start;
		
		if ((size > 0) && (size < bytesToWrite)) bytesToWrite = size;
		
		if (bytesToWrite > Integer.MAX_VALUE) {
			System.err.println("Tried to write > 2GBs and this platform can only handle <= 2Gb chunks.");
			return 0;
		}
		
		if (data.hasArray())
			return baseWriteDataTo(data.array(), start, offset, size);
		
		byte[] dataToWrite = new byte[data.capacity()];
		data.get(dataToWrite);
		return baseWriteDataTo(dataToWrite, start, offset, (int) bytesToWrite);
	}
	
	public int writeDataFromTo(
			@MXFPosition long offset,
			@MXFPosition long start) {
		
		return writeDataFromTo(offset, start, -1);
	}
//
////! Write data from a given buffer to a given location in the destination file
//
///*! \param Buffer Pointer to data to be written
//
// *  \param Offset The offset within the KLV value field of the first byte to write
//
// *  \param Size The number of bytes to write
//
// *  \return The number of bytes written
//
// *  \note As there may be a need for the implementation to know where within the value field
//
// *        this data lives, there is no WriteData(Buffer, Size) function.
//
// */
//
//virtual size_t WriteDataTo(const UInt8 *Buffer, Position Offset, size_t Size) { return Base_WriteDataTo(Buffer, Offset, Size); }
//
	public int writeDataTo(
			@UInt8Array byte[] buffer,
			@MXFPosition long start,
			@MXFPosition long offset,
			int size) {
		
		return baseWriteDataTo(buffer, start, offset, size);
	}
//
////! Base verion: Write data from a given buffer to a given location in the destination file
//
///*! \param Buffer Pointer to data to be written
//
// *  \param Offset The offset within the KLV value field of the first byte to write
//
// *  \param Size The number of bytes to write
//
// *  \return The number of bytes written
//
// *
//
//	 *  DRAGONS: This base function may be called from derived class objects to get base behaviour.
//
// *           It is therefore vital that the function does not call any "virtual" KLVObject
//
// *           functions, directly or indirectly.
//
// */
//
//size_t Base_WriteDataTo(const UInt8 *Buffer, Position Offset, size_t Size);
//
//
	int baseWriteDataTo(
			@UInt8Array byte[] buffer,
			@MXFPosition long start,
			@MXFPosition long offset,
			int size) {
		
		// TODO
		return 0;
	}
			
//
//
////! Set a handler to supply data when a read is performed
//
///*! \note If not set it will be read from the source file (if available) or cause an error message
//
// */
//
//virtual void SetReadHandler(KLVReadHandlerPtr Handler) { ReadHandler = Handler; }
//
//
//
//////! Set a handler to supply data when a write is performed
//
/////*! \note If not set it will be written to destination file (if available) or cause an error message
//
//// */
//
////virtual void SetWriteHandler(KLVWriteHandlerPtr Handler) { WriteHandler = Handler; }
//
//
//
////! Get the length of the value field
//
//virtual Length GetLength(void) { return ValueLength; }
//
	public @MXFLength long getLength() {
		
		return valueLength;
	}
//
////! Set the length of the value field
//
//virtual void SetLength(Length NewLength) { ValueLength = Dest.OuterLength = Source.OuterLength = NewLength; }
//

	public void setLength(
			@MXFLength long length) {
		
		valueLength = length;
		destination.outerLength = length;
		source.outerLength = length;
	}
//
////! Get a reference to the data chunk
//
//virtual DataChunk& GetData(void) { return Data; }
//
	public ByteBuffer getData() {
		
		return data;
	}


}
