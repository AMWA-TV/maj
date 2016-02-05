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

package tv.amwa.maj.industry;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.exception.EndOfDataException;

/**
 * <p>Read and write access to {@linkplain TypeDefinitions#Stream stream type} data.</p>
 *
 *
 *
 * @see tv.amwa.maj.model.EssenceData#getEssenceStream()
 * @see tv.amwa.maj.meta.TypeDefinitionStream
 * @see TypeDefinitions#Stream
 * @see MemoryResidentStream
 */
public interface Stream {

	/**
	 * <p>Returns the current position for reading from or writing to the stream.</p>
	 *
	 * @return Current position for reading from or writing to the stream.
	 *
	 * @throws IOException An IO problem occurred when trying to access the stream.
	 *
	 * @see #setPosition(long)
	 */
	public long getPosition()
		throws IOException;

	/**
	 * <p>Sets the current position for reading from or writing to the stream.</p>
	 *
	 * @param position Current position for reading from or writing to the stream.
	 *
	 * @throws IllegalArgumentException Cannot set a stream position to a negative value or
	 * beyond the limit set for the stream.
	 * @throws IOException An IO problem occurred when trying to access the stream.
	 *
	 * @see #getPosition()
	 * @see #getLimit()
	 */
	public void setPosition(
			long position)
		throws IllegalArgumentException,
			IOException;

	/**
	 * <p>Returns the length of the stream, measured in bytes.</p>
	 *
	 * @return Length of the stream, measured in bytes.
	 *
	 * @throws IOException An IO problem occurred when trying to access the stream.
	 */
	public long getLength()
		throws IOException;

	/**
	 * <p>Gets the limit position beyond which content cannot be read or written.<p>
	 *
	 * @return Limit position beyond which content cannot be read or written.
	 *
	 * @throws IOException An IO problem occurred when trying to access the stream.
	 *
	 * @see #setLimit(long)
	 */
	public long getLimit()
		throws IOException;

	/**
	 * <p>Sets the limit position beyond which content cannot be read or written.</p>
	 *
	 * @param limit Limit position beyond which content cannot be read or written.
	 *
	 * @throws IllegalArgumentException The limit position cannot set beyond the end
	 * of the stream.
	 */
	public void setLimit(
			long limit)
		throws IllegalArgumentException;

	/**
	 * <p>Read the number of bytes requested from the stream into a newly created byte
	 * buffer.</p>
	 *
	 * @param noOfBytes Number of bytes to read.
	 * @return Byte buffer containing the requested number of bytes read from the stream.
	 *
	 * @throws EndOfDataException Attempt to read beyond the limit set for the stream.
	 * @throws IOException An IO problem occurred when trying to read from the stream.
	 * @throws IllegalArgumentException Cannot read a negative number of bytes.
	 *
	 * @see #write(ByteBuffer)
	 * @see #getLimit()
	 * @see #getPosition()
	 */
	public ByteBuffer read(
			int noOfBytes)
		throws EndOfDataException,
			IOException,
			IllegalArgumentException;

	/**
	 * <p>Write the number of bytes requested to the stream from the given byte buffer.</p>
	 *
	 * @param buffer Buffer of data to write. Data is written from the current position to the
	 * limit set for the given buffer.
	 * @return Number of bytes actually written.
	 *
	 * @throws EndOfDataException Attempt to write beyond the limit set for the stream.
	 * @throws IOException An IO problem occurred when trying to write to the stream.
	 *
	 * @see #read(int)
	 * @see #getPosition()
	 * @see #getLimit()
	 */
	public int write(
			ByteBuffer buffer)
		throws EndOfDataException,
			IOException;

	/**
	 * <p>Close the stream and release any associated systems resources.</p>
	 */
	public void close();

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.enumeration.ByteOrder byte order} of the stream.</p>
	 *
	 * @return Byte order of the stream.
	 */
	public ByteOrder getByteOrder();

	/**
	 * <p>Returns the URI that specifies the location of this stream. The URI can be used
	 * to {@linkplain StreamResolver resolve the stream} and includes just enough information
	 * that the stream can be opened and read or written. For example, an
	 * {@linkplain tv.amwa.maj.io.aaf.AAFStream AAF stream} contains the structured storage path within the
	 * file where the stream can be accessed, whereas an {@linkplain tv.amwa.maj.io.mxf.MXFStream MXF stream}
	 * contains the body and index stream identifiers.</p>
	 *
	 * @return URI that specifies the location of the stream.
	 *
	 * @see StreamResolver
	 */
	public URI getStreamURI();

	/**
	 * <p>Create a cloned copy of the underlying stream accessor class.</p>
	 *
	 * @return Cloned copy of the underlying stream accessor class.
	 */
	public Stream clone();
}
