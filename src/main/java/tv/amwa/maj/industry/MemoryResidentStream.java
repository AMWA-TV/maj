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

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.io.xml.XMLBuilder;

/**
 * <p>Implementation of a stream as memory-resident byte buffer.</p>
 *
 *
 *
 */
public class MemoryResidentStream
	implements
		Stream,
		Cloneable {

	private ByteBuffer memoryStream;
	private String localStreamID = Forge.randomAUID().toString();

	private long persistentID = 0l;

	public MemoryResidentStream(
			int capacity)
		throws IllegalArgumentException {

		if (capacity < 0)
			throw new IllegalArgumentException("Cannot create a memory-resident stream of negative size.");

		memoryStream = ByteBuffer.allocate(capacity);
		MediaEngine.getStreamResolver().cacheStream(this);
	}

	public MemoryResidentStream(
			ByteBuffer existingBuffer)
		throws NullPointerException {

		if (existingBuffer == null)
			throw new NullPointerException("Cannot create a memory-resident stream using a null byte buffer.");

		memoryStream = existingBuffer.duplicate();
		memoryStream.rewind();
		MediaEngine.getStreamResolver().cacheStream(this);
	}

	public ByteBuffer read(
			int bytes)
		throws EndOfDataException,
			IOException,
			IllegalArgumentException {

		if (bytes < 0)
			throw new IllegalArgumentException("Cannot read a negative number of bytes from a memory-resident stream.");

		if (bytes > memoryStream.remaining())
			throw new EndOfDataException("Insufficient bytes remaining in memory resident stream buffer.");

		ByteBuffer partialBuffer = ByteBuffer.allocate(bytes);
		while (partialBuffer.hasRemaining())
			partialBuffer.put(memoryStream.get());

		return partialBuffer;
	}

	public long getPosition()
		throws IOException {

		return memoryStream.position();
	}

	public void setPosition(
			long position)
		throws IllegalArgumentException,
			IOException {

		if (position < 0)
			throw new IllegalArgumentException("Cannot set a stream position to a negative value.");

		if (position > memoryStream.limit())
			throw new IllegalArgumentException("Cannot set the position beyond the end of the limit for the stream.");

		memoryStream.position((int) position);
	}

	public int write(
			ByteBuffer bytes)
		throws EndOfDataException,
			IOException {

		int remaining = bytes.remaining();
		if (bytes.remaining() > memoryStream.remaining())
				throw new EndOfDataException("Not enough space to write the given buffer into this memory-resident stream at the current position.");

		while (bytes.hasRemaining())
			memoryStream.put(bytes.get());

		return remaining;
	}

	public long getStart() {

		return 0l;
	}

	public long getLimit() {

		return (long) memoryStream.limit();
	}

	public long getLength() {

		return memoryStream.limit();
	}

	public long getPersistentID() {

		return persistentID;
	}

	public void close() {

	}

	public void finalize() {

		close();
	}

	public MemoryResidentStream clone() {

		try {
			return (MemoryResidentStream) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Should never get here
			throw new InternalError(cnse.getMessage());
		}
	}

	public void setLimit(
			long limit)
		throws IllegalArgumentException {

		if (limit < 0l)
			throw new IllegalArgumentException("Cannot set the limit to a position before the start.");

		if (limit > memoryStream.capacity())
			throw new IllegalArgumentException("Cannot set the limit to a position beyond the capacity of the memory-resident buffer.");

		memoryStream.limit((int) limit);
	}

	public int getCapacity() {

		return memoryStream.capacity();
	}

	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof MemoryResidentStream)) return false;

		MemoryResidentStream testValue = (MemoryResidentStream) o;

		if ((getCapacity() == 0) && (testValue.getCapacity() == 0)) return true;
		if (memoryStream.equals(testValue)) return true;

		return false;
	}

	public int hashCode() {

		return memoryStream.hashCode();
	}

	public final static void generateStreamORM(
			Node parent,
			String namespace,
			String prefix) {

		Element entity = XMLBuilder.createChild(parent, namespace, prefix, "entity");
		XMLBuilder.setAttribute(entity, namespace, prefix, "class", MemoryResidentStream.class.getCanonicalName());
		XMLBuilder.setAttribute(entity, namespace, prefix, "access", "FIELD");

		Element attributes = XMLBuilder.createChild(entity, namespace, prefix, "attributes");

		Element fileName = XMLBuilder.createChild(attributes, namespace, prefix, "basic");
		XMLBuilder.setAttribute(fileName, namespace, prefix, "name", "fileName");

		Element fileNameColumn = XMLBuilder.createChild(fileName, namespace, prefix, "column");
		XMLBuilder.setAttribute(fileNameColumn, namespace, prefix, "name", "FileName");
		XMLBuilder.setAttribute(fileNameColumn, namespace, prefix, "nullable", "false");

		Element persistentID = XMLBuilder.createChild(attributes, namespace, prefix, "id");
		XMLBuilder.setAttribute(persistentID, namespace, prefix, "name", "persistentID");

		XMLBuilder.createChild(persistentID, namespace, prefix, "generated-id");

		Element persistentIDColumn = XMLBuilder.createChild(persistentID, namespace, prefix, "column");
		XMLBuilder.setAttribute(persistentIDColumn, namespace, prefix, "name", "PersistentID");

		Element start = XMLBuilder.createChild(attributes, namespace, prefix, "basic");
		XMLBuilder.setAttribute(start, namespace, prefix, "name", "start");

		Element startColumn = XMLBuilder.createChild(start, namespace, prefix, "column");
		XMLBuilder.setAttribute(startColumn, namespace, prefix, "name", "Start");
		XMLBuilder.setAttribute(startColumn, namespace, prefix, "nullable", "false");

		Element limit = XMLBuilder.createChild(attributes, namespace, prefix, "basic");
		XMLBuilder.setAttribute(limit, namespace, prefix, "name", "limit");

		Element limitColumn = XMLBuilder.createChild(start, namespace, prefix, "column");
		XMLBuilder.setAttribute(limitColumn, namespace, prefix, "name", "Limit");
		XMLBuilder.setAttribute(limitColumn, namespace, prefix, "nullable", "false");
	}

	public URI getStreamURI() {

		return URI.create(localStreamID);
	}

	public ByteOrder getByteOrder() {

		if (memoryStream.order() == java.nio.ByteOrder.BIG_ENDIAN)
			return ByteOrder.Big;
		else
			return ByteOrder.Little;
	}

	public void setByteOrder(
			ByteOrder byteOrder) {

		if (byteOrder == ByteOrder.Big)
			memoryStream.order(java.nio.ByteOrder.BIG_ENDIAN);
		else
			memoryStream.order(java.nio.ByteOrder.LITTLE_ENDIAN);

	}
}
