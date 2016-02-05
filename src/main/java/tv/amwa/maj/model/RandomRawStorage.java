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
 * $Log: RandomRawStorage.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/02/28 12:50:34  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.1  2007/11/13 22:08:27  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.NotReadableException;
import tv.amwa.maj.exception.NotWriteableException;
import tv.amwa.maj.exception.OffsetSizeException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.misctype.DataBuffer;

//TODO decide how to support this better in Java

/**
 * <p>This interface is used by the toolkit to access storage in which
 * {@link AAFFile AAF files} are to be read and written.  It is typically 
 * implemented by clients of {@link AAFFile AAF file}.  Note that some 
 * implementations are provided as a convenience to {@link AAFFile AAF file}
 * clients.</p>
 * 
 * <p>This method assumes that the storage has at least random access 
 * capability.</p>
 * 
 * <p>Objects that implement RandomRawStorage also implement the following 
 * interfaces:
 * 
 * <ul>
 *  <li>{@link RawStorage}</li>
 * </ul></p>
 * 
 *
 *
 */

public interface RandomRawStorage extends RawStorage {

	/* [
	    object,
	    uuid(B1341782-71FE-11d4-B80D-0000863F2C27),
	    helpstring("IAAFRandomRawStorage Interface"),
	    pointer_default(unique)
	] */

	/**
	 * <p>Attempts to read and return the given number of bytes (<code>bufSize</code>) 
	 * from the storage at the given position. If the end of storage is encountered
	 * before the number of requested bytes can be read, the size of the array
	 * of bytes returned will be smaller than the one requested.</p>
	 * 
	 * <p>A call this method will advance the current position in the stream, as used
	 * by the next read operation, by the requested buffer size.</p>
	 * 
	 * @param position Position in the storage from which data is read.
	 * @param bufSize Number of bytes to read from the storage.
	 * @return Array of bytes read from the storage stream at the given position
	 * and up to the given size.
	 * 
	 * @throws NotReadableException The storage is not open for reading.
	 */
	public @DataBuffer byte[] readAt(
			@UInt64 long position,
			@UInt32 int bufSize) throws NotReadableException;

	/**
	 * <p>Writes the given data buffer into the stream of the raw storage at
	 * the given position.</p>
	 * 
	 * <p>This call may fail if the capacity of this storage cannot be
	 * extended sufficiently to complete the request.  The client can
	 * call {@link #setExtent(long)} to attempt to reserve capacity for 
	 * the storage. If the {@link #setExtent(long)} call succeeds, subsequent 
	 * write calls within requested that capacty are guaranteed to succeed.</p>
	 * 
	 * <p>This call will advance the current position by the length of the
	 * given array of bytes.</p>
	 * 
	 * @param position Position in the storage into which the data is written.
	 * @param buffer Buffer from which the data is written to the storage.
	 * @return Number of bytes actually written.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws OffsetSizeException It is not possible to extend the allocated 
	 * size of this storage.
	 * @throws NotWriteableException This storage is not open for writing.
	 */
	public @UInt32 int writeAt(
			@UInt64 long position,
			@DataBuffer byte[] buffer) throws NullPointerException,
											  OffsetSizeException,
											  NotWriteableException;

	/**
	 * <p>Returns the highest byte position in the storage that has been
	 * written so far.</p>
	 * 
	 * @return Highest byte position in the storage that has been written
	 * so far.
	 */
	public @UInt64 long getSize();

	/**
	 * <p>Returns <code>true</code> if the memory allocated to the storage
	 * can be dynamically extended; otherwise <code>false</code>.</p>
	 * 
	 * @return Can the memory allocated to the storage be dynamically
	 * extended?
	 */
	public @Bool boolean isExtendable();

	/**
	 * <p>Returns the number of bytes currently allocated to the
	 * storage.</p>
	 * 
	 * @return Number of bytes allocated to the storage.
	 */
	public @UInt64 long getExtent();

	/**
	 * <p>Request the given capacity for the storage. If successful,
	 * subequent calls to {@link RawStorage#write(byte[])} and
	 * {@link #writeAt(long, byte[])} within the given capacity are
	 * gauranteed to succeed.</p>
	 * 
	 * @param extent Number of bytes capacity requested for the storage.
	 * 
	 * @throws OffsetSizeException This storage has insufficient capacity 
	 * to perform the write.
	 */
	public void setExtent(
			  @UInt64 long extent) throws OffsetSizeException;

}
