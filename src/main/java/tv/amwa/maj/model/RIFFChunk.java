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
 * $Log: RIFFChunk.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/11/08 16:00:17  vizigoth
 * Version with IO exceptions on stream manipulation methods.
 *
 * Revision 1.3  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/01/29 18:11:22  vizigoth
 * Updated documentation for newly added classes to 1.1.2 and associated fixes.
 *
 * Revision 1.1  2007/11/13 22:08:53  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.io.IOException;
import java.nio.ByteBuffer;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.PositionOutOfRangeException;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.misctype.PositionType;

/** 
 * <p>Specifies a chunk of resource interchange file format (RIFF) data, identified by
 * a 4-byte tag and variable size field.</p>
 * 
 * <p>See the <a href="http://en.wikipedia.org/wiki/RIFF">description of the RIFF file
 * format on Wikipedia</a>.</p>
 *
 *
 * 
 * @see BWFImportDescriptor#getUnknownBWFChunks()
 * @see tv.amwa.maj.industry.TypeDefinitions#RIFFChunkStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#RIFFChunkStrongReferenceVector
 */
public interface RIFFChunk
	extends InterchangeObject {

	/**
	 * <p>Sets the chunk id of this RIFF chunk, which is a 4-byte tag that identifies this tag.</p>
	 * 
	 * <p>Note that in the representation of this tag, care needs to be taken to ensure that the
	 * sign bit is not confused with an 8-bit character code. Negative values are accepted by this
	 * method.</p>
	 *
	 * @param chunkId Chunk identifier for this RIFF chunk.
	 */
	public void setChunkID(
			@UInt32 int chunkId);

	/**
	 * <p>Returns the chunk id of this RIFF chunk, which is a 4-byte tag that identifies this tag.</p>
	 *
	 * <p>Note that in the representation of this tag, care needs to be taken to ensure that the
	 * sign bit is not confused with an 8-bit character code. Negative values are accepted by this
	 * method.</p>
	 *
	 * @return Chunk identifier for this RIFF chunk.
	 */
	public @UInt32 int getChunkID();

	/**
	 * <p>Read the requested number of bytes from the RIFF chunk at the current position.
	 * The position is advanced by the number of bytes read.</p>
	 *
	 * @param numberOfBytes Number of bytes to read from the RIFF chunk.
	 * @return Bytes read from the RIFF chunk at the current position.
	 * 
	 * @throws IllegalArgumentException Cannot read a negative number of bytes.
	 * @throws EndOfDataException The number of bytes requested are not available in the
	 * RIFF chunk relative to the current position.
	 * @throws IOException Error occurred when trying to read from the underlying
	 * stream.
	 * 
	 * @see #getPosition()
	 */
	public @DataBuffer ByteBuffer read(
			@UInt32 int numberOfBytes)
		throws IllegalArgumentException, 
			EndOfDataException, 
			IOException;


	  //***********************************************************
	  //
	  // Write()
	  //
	  /// Write data to this RIFFChunk at the current position. Advances the position
	/// by the number of bytes written. Any data beyond the new position is lost; 
	///that is, the buffer is truncated.
	  /// 
	  /// @param bytes [in] write this many bytes
	  /// @param buffer [out, size_is(bytes)] chunk data to write
	  /// @param pBytesWritten [out,ref] actual number of bytes written
	  ///
	
	// TODO very suspicious behaviour - check
	/** 
	 * <p>Writes the given bytes to this RIFF chunk data at the current position.
	 * The position is advanced by the number of bytes written. Any data beyond the 
	 * new position is lost by truncating the buffer.</p> 
	 *
	 * @param bytes Bytes to write to the RIFF chunk data.
	 * @throws NullPointerException The given array of bytes is <code>null</code>.
	 * @throws EndOfDataException The size of the RIFF chunk is less than the number of bytes
	 * to write relative to the current position.
	 * @throws IOException Error occurred when trying to write to the underlying stream.
	 * 
	 * @see #getPosition()
	 */
	public void write(
	    	@DataBuffer ByteBuffer bytes)
		throws NullPointerException,
			EndOfDataException, IOException;

	/**
	 * <p>Sets the position for reading or writing this RIFF chunk data.</p>
	 *
	 * @param offset Position offset to set for reading or writing RIFF chunk data.
	 * 
	 * @throws PositionOutOfRangeException The given offset is outside the acceptable
	 * range for this RIFF chunk data, which is between 0 and the length of the data.
	 * @throws IOException Error occurred when trying to set the position for the
	 * underlying stream.
	 * 
	 * @see #getLength()
	 */
	public void setPosition (
			@PositionType long offset)
		throws PositionOutOfRangeException, 
			IOException;

	/**
	 * <p>Returns the current position for reading or writing the RIFF chunk data.</p>
	 *
	 * @return Current position for reading or writing the RIFF chunk data.
	 * @throws IOException Error occurred when trying to set the position for the
	 * underlying stream.
	 */
	public @PositionType long getPosition() 
		throws IOException;

	/**
	 * <p>Returns the total size of this RIFF chunk measured in bytes. The length is 
	 * initially&nbsp;0 until {@link #setChunkData(Stream)} has been called.</p>
	 *
	 * @return Total size size of this RIFF chunk measured in bytes.
	 */ 
	public @UInt32 int getLength();
	
	/**
	 * <p>Returns the complete data of this RIFF chunk.</p>
	 *
	 * @return Actual data of this RIFF chunk.
	 */
	public @DataBuffer Stream getChunkData();
	
	/**
	 * <p>Sets the complete data of this RIFF chunk and initializes the position to zero.
	 * The length property of this RIFF chunk will be set to the length of the given chunk
	 * data.</p>
	 *
	 * @param chunkData Actual data for this RIFF chunk.
	 * 
	 * @throws NullPointerException The chunk data argument is <code>null</null>.
	 */
	public void setChunkData(
			Stream chunkData)
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this RIFF chunk.</p>
	 *
	 * @return Cloned copy of this RIFF chunk.
	 */
	public RIFFChunk clone();
}
