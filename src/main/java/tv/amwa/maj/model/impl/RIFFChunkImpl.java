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
 * $Log: RIFFChunkImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/11/08 15:47:21  vizigoth
 * Version with IO exceptions on stream manipulation methods.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2008/01/29 18:11:22  vizigoth
 * Updated documentation for newly added classes to 1.1.2 and associated fixes.
 *
 * Revision 1.1  2007/11/13 22:09:42  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.PositionOutOfRangeException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MemoryResidentStream;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.RIFFChunk;

/** 
 * <p>Implements the representation of a chunk of resource interchange file format (RIFF) data, identified by
 * a 4-byte tag and variable size field.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#RIFFChunkStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#RIFFChunkStrongReferenceVector
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x4F00,
		  uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "RIFFChunk",
		  description = "Chunk of data as defined by the Resource Interchange File Format (RIFF).",
		  symbol = "RIFFChunk")
public class RIFFChunkImpl
	extends 
		InterchangeObjectImpl
	implements 
		RIFFChunk,
		Serializable,
		XMLSerializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 1923907225869841814L;
	
	private int chunkID;
	private int chunkLength = 0; // Separated from data to allow length query before full data get
	private Stream chunkData;
	
	public RIFFChunkImpl() { }
	
	/**
	 * <p>Creates and initializes a new RIFF data chunk with the given chunk identifier. The
	 * identifier is used to represent 4 ASCII characters that represent the kind of data
	 * stored inside the chunk.</p>
	 *
	 * @param chunkID Identifier for the new chunk.
	 */
	public RIFFChunkImpl(
			@UInt32 int chunkID) {
		
		setChunkID(chunkID);
	}

	@MediaProperty(uuid1 = 0x04070400, uuid2 = 0x0000, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x08},
			     definedName = "ChunkData",
			     typeName = "Stream",
			     optional = false,
			   	 uniqueIdentifier = false,
			   	 pid = 0x4F03,
			   	 symbol = "ChunkData")
	public Stream getChunkData() {

		return chunkData.clone();
	}

	@MediaPropertySetter("ChunkData")
	public void setChunkData(
			Stream chunkData)
		throws NullPointerException {

		if (chunkData == null)
			throw new NullPointerException("Cannot set the chunk data of RIFF chunk with a null value.");
		
		this.chunkData = chunkData.clone();
	}
	
	public final static Stream initializeChunkData() {
		
		return new MemoryResidentStream(0);
	}

	@MediaProperty(uuid1 = 0x04060802, uuid2 = 0x0000, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x08},
			     definedName = "ChunkID",
			     typeName = "UInt32",
			     optional = false,
			     uniqueIdentifier = false,
			     pid = 0x4F01,
			     symbol = "ChunkID")
	public int getChunkID() {

		return chunkID;
	}

	@MediaPropertySetter("ChunkID")
	public void setChunkID(
			int chunkID) {
	
		this.chunkID = chunkID;
	}

	public final static int initializeChunkID() {
		
		return 0;
	}
	
	@MediaProperty(uuid1 = 0x04060903, uuid2 = 0x0000, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x08},
			     definedName = "ChunkLength",
			     typeName = "UInt32",
			     optional = false,
			     uniqueIdentifier = false,
			     pid = 0x4F02,
			     symbol = "ChunkLength")
	public int getLength() {

		try {
			this.chunkLength = (int) chunkData.getLength();
		}
		catch (IOException ioe) { }
		return chunkLength;
	}
	
	@MediaPropertySetter("ChunkLength")
	public void setChunkLength(
			long chunkLength)
		throws IllegalArgumentException {
		
		if (chunkLength > Integer.MAX_VALUE)
			throw new IllegalArgumentException("The MAJ API does not support RIFF chunks greater than 2Gbs.");
		
		if (chunkLength < 0)
			throw new IllegalArgumentException("Cannot set the chunk length to a negative value.");
		
		this.chunkLength = (int) chunkLength;
	}

	public final static long initializeChunkLength() {
		
		return 0l;
	}
	
	public long getPosition() 
		throws IOException {

		return chunkData.getPosition();
	}

	public void setPosition(
			long offset)
		throws PositionOutOfRangeException, 
			IOException {
	
		if ((offset >= chunkData.getLength()) || (offset < 0))
			throw new PositionOutOfRangeException("Cannot set the position before or beyond the end of the chunk data.");
	
		chunkData.setPosition(offset);
	}

	public ByteBuffer read(
			int numberOfBytes)
		throws IllegalArgumentException, 
			EndOfDataException, 
			IOException {

		if (numberOfBytes < 0) 
			throw new IllegalArgumentException("Cannot read a negative number of bytes from this RIFF chunk.");
		
		return chunkData.read(numberOfBytes);
	}

	public void write(
			ByteBuffer bytes)
		throws NullPointerException,
			EndOfDataException, 
			IOException {

		if (bytes == null)
			throw new NullPointerException("Cannot write to the RIFF chunk data using a null byte array.");
		
		chunkData.write(bytes);
	}

	public RIFFChunk clone() {
		
		return (RIFFChunk) super.clone();
	}
	
	@Override
	public String getComment() {
		
		return "local RIFF chunk persistent id: " + getPersistentID();
	}
}
