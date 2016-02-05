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
 * <p>Provides access to stream data represented as a locally accessible file. The file is
 * assumed to be {@linkplain tv.amwa.maj.constant.ContainerConstant#External external}
 * essence that is not wrapped in any way.</p>
 *
 *
 *
 * @see MemoryResidentStream
 * @see StreamResolver
 * @see tv.amwa.maj.model.EssenceData#getEssenceStream()
 */
public class FileStream
	implements Stream,
		Cloneable {

	/**
	 * <p>Create a new file stream by file reference. If the file exists, it will be available
	 * for reading and writing. If the file does not yet exist, it will be created on the first
	 * write operation.</p>
	 *
	 * @param streamReference Identifier for the stream, which is a URL with a file scheme.
	 */
	public FileStream(
			URI streamReference) {

		// TODO
	}

	public void close() {
		// TODO Auto-generated method stub

	}

	public long getLength() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLimit() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getPosition() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public URI getStreamURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public ByteBuffer read(int noOfBytes) throws EndOfDataException,
			IOException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLimit(long limit) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	public void setPosition(long position) throws IllegalArgumentException,
			IOException {
		// TODO Auto-generated method stub

	}

	public int write(ByteBuffer buffer) throws EndOfDataException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public ByteOrder getByteOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public FileStream clone() {

		try {
			return (FileStream) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			throw new InternalError("FileStream is cloneable.");
		}
	}

}
