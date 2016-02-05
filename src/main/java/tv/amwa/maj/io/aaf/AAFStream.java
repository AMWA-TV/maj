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

package tv.amwa.maj.io.aaf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.w3c.dom.Node;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.io.xml.XMLSerializable;

public class AAFStream
	implements Stream,
		Cloneable,
		Serializable,
		XMLSerializable {

	private static final long serialVersionUID = 3734599745445480486L;

	private String ssPath;
	private String fileName;
	private ByteOrder byteOrder;
	private long position = 0;
	private long limit = -1;
	// private long capacity = 0;

	private static Map<String, FileInputStream> fileStreams =
		Collections.synchronizedMap(new HashMap<String, FileInputStream>());

	public AAFStream() { }

	public AAFStream(
			String ssPath,
			String canonicalFileName,
			ByteOrder byteOrder)
		throws NullPointerException {

		if (ssPath == null)
			throw new NullPointerException("Cannot create an AAF stream using a null structured storage path.");

		if (canonicalFileName == null)
			throw new NullPointerException("Cannot create an AAF stream using a null file name.");

		if (byteOrder == null)
			throw new NullPointerException("Cannot create an AAF stream without specification of the byte order of the stream.");

		this.ssPath = ssPath;
		this.fileName = canonicalFileName;
		this.byteOrder = byteOrder;
	}

	public AAFStream(
			URI streamReference) {

		// TODO
	}

	public void close() {

		if (fileStreams.containsKey(fileName)) {
			FileInputStream fis = fileStreams.get(fileName);
			if (fis != null) {
				try { fis.close(); }
				catch(IOException ioe) {  }
				fileStreams.remove(fileName);
			}
		}

	}

	public void open()
		throws IOException {

		if (!fileStreams.containsKey(fileName)) {
			FileInputStream fis = new FileInputStream(fileName);
			fileStreams.put(fileName, fis);
		}
	}

	public DocumentEntry getDocumentEntry()
		throws IOException {

		if (!fileStreams.containsKey(fileName))
			open();

		FileInputStream fis = fileStreams.get(fileName);
		if (fis == null) return null;

		POIFSFileSystem poifsfs = new POIFSFileSystem(fis);

		StringTokenizer tokenizer = new StringTokenizer(ssPath, File.separator);

		DirectoryEntry currentDir = poifsfs.getRoot();
		Entry nextEntry = null;
		while (tokenizer.hasMoreElements()) {
			nextEntry = currentDir.getEntry(tokenizer.nextToken());
			if (nextEntry instanceof DirectoryEntry)
				currentDir = (DirectoryEntry) nextEntry;
		}
		if (!(nextEntry instanceof DocumentEntry)) return null;

		return (DocumentEntry) nextEntry;
	}

	public DocumentInputStream getDocumentInputStream()
		throws IOException {

		return new DocumentInputStream((DocumentEntry) getDocumentEntry());
	}

	public long getLength()
		throws IOException {

		// TODO what do we do with longer segments?
		long length = (long) getDocumentEntry().getSize();
		if (limit == -1)
			limit = length;
		return length;
	}

	public long getLimit()
		throws IOException {

		if (limit == -1l)
			this.limit = getLength();
		return limit;
	}

	public long getPosition()
		throws IOException {

		return position;
	}

	public ByteBuffer read(
			int noOfBytes)
		throws EndOfDataException,
			IOException,
			IllegalArgumentException {

		DocumentInputStream dis = getDocumentInputStream();
		long actualSkip = dis.skip(position);

		if (actualSkip < position)
			throw new EndOfDataException("Position plus length is longer than the AAF stream.");
		byte[] bytes = new byte[noOfBytes];
		position += dis.read(bytes);

		return ByteBuffer.wrap(bytes);
	}

	public void setLimit(
			long limit)
		throws IllegalArgumentException {

		if (limit < 0)
			throw new IllegalArgumentException("Cannot set the limit to a negative value.");

		try {
			if (limit > getLength())
				throw new IllegalArgumentException("Cannot set the limit greater than the length.");
		}
		catch (IOException ioe) { }

		this.limit = limit;
	}

	public void setPosition(
			long position)
		throws IllegalArgumentException,
			IOException {

		if (position < 0)
			throw new IllegalArgumentException("Cannot set the position to a negative value.");

		if (position > getLimit())
			throw new IllegalArgumentException("Cannot set the position beyond the limit for this AAF stream.");

		this.position = position;
	}

	public int write(
			ByteBuffer buffer)
		throws EndOfDataException,
			IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public AAFStream clone() {

		try {
			return (AAFStream) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Implements cloneable ... should never happen
			throw new InternalError();
		}
	}

	public void appendXMLChildren(
			Node parent) {
		// TODO Auto-generated method stub

	}

	public String getComment() {

		return "AAFStream: " + ssPath + ", " + fileName + ", " + byteOrder;
	}

	public String toString() {

		return "<!-- " + getComment() + " -->";
	}

	public URI getStreamURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public ByteOrder getByteOrder() {

		return byteOrder;
	}
}
