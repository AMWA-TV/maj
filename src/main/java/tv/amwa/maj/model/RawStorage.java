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
 * $Log: RawStorage.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/02/28 12:50:34  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.1  2007/11/13 22:08:18  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.NotReadableException;
import tv.amwa.maj.exception.NotWriteableException;
import tv.amwa.maj.exception.StreamFullException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.misctype.DataBuffer;

//TODO decide how to support this better in Java

/**
 * <p>This interface is used by the toolkit to access storage in which
 * {@link AAFFile AAF files} are to be read and written.  It is typically 
 * implemented by clients of {@link AAFFile AAF file}.  Note that some 
 * implementations are provided as a convenience to {@link AAFFile AAF
 * file} clients.</p>
 * 
 * <p>When writing to a stream, clients should be aware that the
 * stream's capacity may be limited.  To insure that writes will not
 * fail due to insufficient stream capacity, clients must implement
 * {@link RandomRawStorage} and call
 * {@link RandomRawStorage#setExtent(long)} to pre-allocate capacity 
 * in the stream.  Writes done within the size specified to the most 
 * recent successful {@link RandomRawStorage#setExtent(long)} call are 
 * guaranteed to succeed.</p>
 * 
 * <p>This method assumes that the storage has at least sequential access
 * capability.</p>
 * 
 *
 *
 */

public interface RawStorage {

	/* [
	    object,
	    uuid(F45FA9E1-7166-11D4-B80D-0000863F2C27),
	    helpstring("IAAFRawStorage Interface"),
	    pointer_default(unique)
	] */

	/**
	 * <p>Returns <code>true</code> if the storage is readable; otherwise
	 * <code>false</code>.</p>
	 * 
	 * @return Is the storage readable?
	 */
	public @Bool boolean isReadable();

	/**
	 * <p>Reads the given number of bytes from the stream of the raw storage
	 * and returns them as a byte array. If the end of the stream is 
	 * encountered before the given number of bytes can be read, the output
	 * array will contain fewer bytes than specified.</p>
	 * 
	 * <p>A call this method will advance the current position in the stream, 
	 * as used by the next read operation, by the requested buffer size.</p>
	 * 
	 * @param bufSize Number of bytes to read from the raw storage stream.
	 * 
	 * @return Buffer containing bytes read form raw storage stream.
	 * 
	 * @throws NotReadableException The stream is not open for reading.
	 */
	public @DataBuffer byte[] read(
			@UInt32 int bufSize) throws NotReadableException; 

	/**
	 * <p>Returns <code>true</code> if this storage is writeable; otherwise
	 * <code>false</code>.</p>
	 * 
	 * @return Is the raw storage writeable?
	 */
	public @Bool boolean isWriteable();

	/**
	 * <p>Writes the given bytes into the stream of the raw storage.</p>
	 * 
	 * <p>This call may fail if the stream has insufficient capacity to
	 * complete the request.  If this storage supports the {@link RandomRawStorage 
	 * random raw storage} interface, the client can call 
	 * {@link RandomRawStorage#setExtent(long)} to attempt to reserve capacity
	 * in the stream. If the call succeeds, subsequent calls to this method
	 * within that capacity are guaranteed to succeed.</p>
	 * 
	 * <p>This call will advance the current position by the length of the
	 * given array of bytes.</p>
	 * 
	 * @param buffer Bytes to write to the stream of the raw storage.
	 * @return Number of bytes actually written.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws NotWriteableException The stream is not open for writing.
	 * @throws StreamFullException The stream has insufficient capacity
	 * to perform the write.
	 */
	public @UInt32 int write(
			@DataBuffer byte[] buffer) throws NullPointerException,
			                                  NotWriteableException,
			                                  StreamFullException;

	/**
	 * <p>Synchronizes any cached data of the raw storage object with
	 * the underlying storage.</p>
	 * 
	 */
	public void synchronize();
}
