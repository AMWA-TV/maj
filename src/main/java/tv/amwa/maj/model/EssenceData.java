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
 * $Log: EssenceData.java,v $
 * Revision 1.4  2011/07/27 17:28:24  vizigoth
 * Added bodySID and indexSID optional properties and made the essence stream optional for easier working with MXF files.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2010/11/08 15:56:53  vizigoth
 * Version with IO exceptions on stream manipulation methods.
 *
 * Revision 1.6  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2008/01/27 11:07:36  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:18  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.io.IOException;
import java.nio.ByteBuffer;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.NotImplementedException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.PackageID;


// Decided to drop EssenceDataEx in the com-api and use an extended 
// EssenceData instead.

// I've had a go at fixing this interface ... it looks rather like it
// has not been properly tried or tested.

/**
 * <p>Specifies an essence container. The methods can be used to modify 
 * essence data objects that contain the actual essence data (excluding WAVE) when it is contained 
 * within an {@linkplain AAFFile AAF file}.  Normally, the client application 
 * would access the essence through the {@linkplain EssenceAccess essence access}
 * interface, which handles the work of finding and (de)compressing the data.
 * However, in rare cases direct access to the data is required, the {@linkplain #read(int) read}
 * and {@linkplain #write(ByteBuffer) write} methods of this interface are provided.</p>
 * 
 * <p>All methods of this interface use bytes to measure the size of buffers. Access to 
 * sample-sized sections can only be provided with reference to the associated 
 * {@link CodecDefinition codec}, which is provided by an implementation of 
 * {@link EssenceAccess} or {@link EssenceMultiAccess}.</p> 
 * 
 *
 *
 * @see EssenceAccess
 * @see EssenceMultiAccess
 * @see MaterialPackage
 * @see ContentStorage#enumEssenceDataObjects()
 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDataStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDataStrongReferenceSet
 */

public interface EssenceData 
	extends InterchangeObject {


	/**
	 * <p>Write a pre-interleaved data item into the essence stream, starting
	 * at the current position. The number of bytes actually written into the
	 * essence data is returned.</p>
	 * 
	 * @param buffer Buffer containing the data to be written.
	 * @return Buffer containing the data.
	 * 
	 * @throws EndOfDataException Cannot write beyond the end of the bounds of the
	 * underlying stream.
	 * @throws IOException Error occurred when trying to write data to the
	 * underlying stream.
	 */
	public @UInt32 int write(
			@DataBuffer ByteBuffer buffer) 
		throws EndOfDataException, 
			IOException; 

	/**
	 * <p>Read the given number of bytes from the pre-interleaved data of an essence stream 
	 * at the current position.</p>
	 * 
	 * @param bytes Number of bytes to read from the data stream
	 * @return Buffer containing the requested data.
	 * 
	 * @throws IOException Error when trying to read data from the underlying stream.
	 * @throws EndOfDataException Attempting to read beyond the bounds of the data 
	 * stream.
	 */
	public @DataBuffer ByteBuffer read(
			@UInt32 int bytes) 
		throws IOException, 
			EndOfDataException;

	/**
	 * <p>Seek to an absolute position within the stream of essence data.</p>
	 * 
	 * @param offset Offset from the beginning of the essence.
	 * 
	 * @throws IllegalArgumentException Cannot set the position outside the bounds of
	 * the underlying stream.
	 * @throws IOException An error occurred when trying to set the position for
	 * the underlying stream.
	 */
	public void setPosition(
			@PositionType long offset) 
		throws IllegalArgumentException, 
			IOException;

	/**
	 * <p>Get the absolute position within the stream of essence data.</p>
	 * 
	 * @return Absolute position within the essence data.
	 * 
	 * @throws IOException Error trying to determine position from the underlying
	 * stream.
	 */
	public @PositionType long getPosition() 
		throws IOException;

	/**
	 * <p>Returns the total size of the stream of essence data.</p>
	 * 
	 * @return Total size of the essence data.
	 * @throws IOException Error accessing the underlying stream to determine the length.
	 */
	public @LengthType long getSize() 
		throws IOException;

	/**
	 * <p>Writes the given sample index data to the sample index data at
	 * the current sample index stream position. The sample index stream contains an index to 
	 * the samples or frames. The format of the index is determined by the associated 
	 * {@linkplain CodecDefinition codec}. The number of samples actually written is returned
	 * and may be less than those requested at the end of the buffer.</p>
	 * 
	 * <p>The sample index property is optional. It is not possible to write sample index 
	 * data if the property is not present. To write data, call {@link #setSampleIndexSize(long)} 
	 * first with sufficient size to write the required buffer.</p>
	 * 
	 * @param buffer Buffer of sample data to write into the essence data.
	 * @return Number of samples actually written.
	 * 
	 * @throws PropertyNotPresentException The optional sample index data is not present for 
	 * this essence data.
	 * @throws EndOfDataException Cannot write data beyond the bounds of the sample index
	 * stream.
	 * @throws IOException Error occurred when trying to write data to the underlying stream.
	 * 
	 * @see #getSampleIndexPosition()
	 */
	public @UInt32 int writeSampleIndex(
			@DataBuffer ByteBuffer buffer)
		throws PropertyNotPresentException, 
			EndOfDataException, 
			IOException;

//	 TODO why was this in the documentation?
//	 * <p>Call {@link #numberOfSamplesRead()} directly after calling this
//	 * method to find out how many samples were actually returned in the
//	 * buffer.</p> 

	/**
	 * <p>Reads raw data from a sample index stream at the current sample
	 * index position. The sample index stream contains an index to 
	 * the samples or frames. The format of the index is determined by the associated 
	 * {@linkplain CodecDefinition codec} which is why the parameter of this method
	 * is measured in bytes rather than number of samples. The sample index property 
	 * is optional.</p>
	 * 
	 * @param size Read this many bytes from the sample index stream.
	 * @return The buffer containing the required number of samples.
	 * 
	 * @throws PropertyNotPresentException The optional sample index property is
	 * not present for this essence data.
	 * @throws IOException Error trying to read data from the underlying stream.
	 * @throws EndOfDataException Cannot read data beyond the end of the sample
	 * index.
	 */
	public @DataBuffer ByteBuffer readSampleIndex(
			@UInt32 int size)
		throws PropertyNotPresentException, 
			IOException, 
			EndOfDataException;

	/**
	 * <p>Returns the number of bytes returned by the last call
	 * to {@link #readSampleIndex(int)}, which may be different from
	 * the number of bytes requested, for example near the end of the stream.</p>
	 * 
	 * @return Number of samples returned by the last call
	 * to {@link #readSampleIndex(int)}, or&nbsp;0 if the method
	 * has not yet been called.
	 * 
	 * @throws PropertyNotPresentException The optional sample index
	 * property is not present in this essence data.
	 */
	// public @UInt32 int numberOfSamplesRead()
	//	   throws PropertyNotPresentException;

	/**
	 * <p>Seek to an absolute position within the sample index data, measured in bytes
	 * rather than samples. The sample index stream contains an index to the samples or 
	 * frames. The format of the index is determined by the associated {@linkplain CodecDefinition codec}.
	 * If the given offset is beyond the end of the stream, the position is set to
	 * be the end of the stream.</p>
	 * 
	 * @param offset Offset from the beginning of the data measured in
	 * bytes.
	 * @throws PropertyNotPresentException The optional sample index property is not 
	 * present for this essence data.
	 * @throws IllegalArgumentException Cannot set the position outside the bounds of the
	 * underlying stream.
	 * @throws IOException Error trying to set the position for the underlying stream.
	 */
	public void setSampleIndexPosition(
			@PositionType long offset)
		throws PropertyNotPresentException, 
			IllegalArgumentException, 
			IOException;

	/**
	 * <p>Returns the current sample index position, measured in bytes rather than samples. 
	 * The sample index stream contains an index to 
	 * the samples or frames. The format of the index is determined by the associated 
	 * {@linkplain CodecDefinition codec}. The sample index property is optional.</p>
	 * 
	 * @return Current sample index position.
	 * 
	 * @throws PropertyNotPresentException The optional sample index data is not present for this 
	 * essence data.
	 * @throws IOException Error trying to retrieve the position from the underlying stream.
	 */
	public @PositionType long getSampleIndexPosition()
		throws PropertyNotPresentException, IOException;

	/**
	 * <p>Returns the total size of the sample index data, measured in bytes rather than the
	 * total number of samples. The sample index stream contains an index to 
	 * the samples or frames. The format of the index is determined by the associated 
	 * {@linkplain CodecDefinition codec}. The sample index property is optional.</p>
	 * 
	 * @return Size of the sample index data measured in bytes.
	 * 
	 * @throws PropertyNotPresentException The optional sample index data is not present for this
	 * essence data.
	 * @throws IOException Error accessing the underlying stream to determine the stream length.
	 */
	public @LengthType long getSampleIndexSize()
		throws PropertyNotPresentException, 
			IOException;
	
	/**
	 * <p>Set the sample index of this essence data to the given 
	 * {@linkplain tv.amwa.maj.industry.Stream stream}. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 * 
	 * @param sampleIndex Stream of sample index data.
	 */
	public void setSampleIndex(
			Stream sampleIndex);
	
	/**
	 * <p>Sets the size of the sample index. The sample index stream contains an index to 
	 * the samples or frames. The format of the index is determined by the associated 
	 * {@linkplain CodecDefinition codec}. Set this value to&nbsp;0 to omit this optional property.</p>
	 * 
	 * <p>This method will extend an existing buffer but will not truncate
	 * it. To decrease the size of the buffer, copy the data, call this method with&nbsp;0
	 * and then write the copied bytes back into the buffer.</p>
	 * 
	 * @param sampleIndexSize Size for the sample index data.
	 * @throws IllegalArgumentException The given sample index size is negative or smaller
	 * than the sample index already stored.
	 * @throws NotImplementedException Creating a non existent buffer is not supported in
	 * this version of MAJ.
	 */
	public void setSampleIndexSize(
			@LengthType long sampleIndexSize)
		throws IllegalArgumentException, NotImplementedException;
	
	/**
	 * <p>Sets a reference to the file {@linkplain SourcePackage source package} that describes this essence
	 * data. This reference will provide the persistent file package id property for this essence data.</p>
	 * 
	 * @param sourcePackage Reference to a source package that describes this essence data.
	 * 
	 * @throws NullPointerException The given file source package is <code>null</code>.
	 */
	public void setFilePackage(
			SourcePackage sourcePackage) 
		throws NullPointerException;

	/**
	 * <p>Returns a reference to the file {@linkplain SourcePackage source package} that describes this essence
	 * data. This method will return <code>null</code> unless the file package reference has previously
	 * been set using {@link #setFilePackage(SourcePackage)}.</p>
	 * 
	 * @return Reference to a source package.
	 */
	public SourcePackage getFilePackage();

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.record.PackageID oackage id} of the file {@linkplain SourcePackage 
	 * source package} that describes this essence data. The linked source package must exist
	 * in the same file as the essence data.</p>
	 * 
	 * <p>This is a required persistent property of essence data.</p>
	 * 
	 * @return The linked source package identifier associated with the essence.
	 */
	public PackageID getLinkedPackageID();
	
	// TODO come back and review optionality when MXFStream extends Stream is available.
	/**
	 * <p>Returns the underlying essence stream of this essence data. This is an optional
	 * property for MXF applications only, where the {@linkplain #getBodySID() body stream identifier}
	 * is used instead.</p>
	 * 
	 * @return Underlying essence stream of this essence data.
	 * 
	 * @throws PropertyNotPresentException The essence stream data is not (yet) present for this 
	 * essence data.
	 * 	 
	 * @see #read(int)
	 * @see #write(ByteBuffer)
	 * @see #getPosition()
	 * @see #setPosition(long)
	 * @see #getSize()
	 */
	public Stream getEssenceStream()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the underlying essence stream of this essence data. This is an optional
	 * property for MXF applications only, where the {@linkplain #getBodySID() body stream identifier}
	 * is used instead. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param essenceStream Underlying essence stream of this essence data.
	 * 
	 * @see #read(int)
	 * @see #write(ByteBuffer)
	 * @see #getPosition()
	 * @see #setPosition(long)
	 * @see #getSize()
	 */
	public void setEssenceStream(
			Stream essenceStream);
	
	/**
	 * <p>Returns the sample index for the essence data, which contains an index to the 
	 * samples or frames. The format of the index is determined 
	 * by the associated {@linkplain CodecDefinition codec}. This is an optional property.<p>
	 * 
	 * @return Sample index for the essence data.
	 * 
	 * @throws PropertyNotPresentException The optional sample index is not present for
	 * this essence data.
	 */
	public Stream getSampleIndex() 
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the identifier of the index table for the essence container of this essence
	 * data. The index stream identifier is an optional property used in the
	 * encoding of essence streams in MXF files.</p>
	 * 
	 * @return Identifier of the index table for the essence container of this essence data.
	 * 
	 * @throws PropertyNotPresentException The optional index table stream identifier property
	 * is not present for this essence data.
	 * 
	 * @see tv.amwa.maj.io.mxf.IndexTableSegment
	 * @see tv.amwa.maj.io.mxf.PartitionPack#getIndexSID()
	 */
	public @UInt32 int getIndexSID()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the identifier of the index table for the essence container of this essence data.
	 * The index stream identifier is an optional property used in the encoding of essence streams 
	 * in MXF files. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param indexSID Identifier of the index table for the essence container of this essence data.
	 * 
	 * @throws IllegalArgumentException Cannot set the index stream identifier to a negative value.
	 * 
	 * @see tv.amwa.maj.io.mxf.IndexTableSegment
	 * @see tv.amwa.maj.io.mxf.PartitionPack#getIndexSID()
	 */
	public void setIndexSID(
			@UInt32 Integer indexSID)
		throws IllegalArgumentException;
	
	/**
	 * <p>Returns the identifier for the essence container of this this essence data, known as the 
	 * <em>body stream identifier</em>. The body stream identifier is an optional property used in
	 * the encoding of essence streams in MXF files.</p>
	 * 
	 * @return bodySID Identifier for the essence container of this essence data.
	 * 
	 * @throws PropertyNotPresentException The optional body stream identifier property is not 
	 * present for this essence data.
	 * 
	 * @see tv.amwa.maj.io.mxf.EssencePartition
	 * @see tv.amwa.maj.io.mxf.PartitionPack#getBodySID()
	 */
	public @UInt32 int getBodySID()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the identifier for the essence container of this essence data, known as the 
	 * <em>body stream identifier</em>. The body stream identifier is an optional property used in
	 * the encoding of essence streams in MXF files. Set this optional property to <code>null</code> 
	 * to omit it.</p>
	 * 
	 * @param bodySID Identifier for the essence container of this essence data.
	 * 
	 * @throws IllegalArgumentException Cannot set the body stream identifier to a negative value.
	 * 
	 * @see tv.amwa.maj.io.mxf.EssencePartition
	 * @see tv.amwa.maj.io.mxf.PartitionPack#getBodySID()
	 */
	public void setBodySID(
			@UInt32 Integer bodySID)
		throws IllegalArgumentException;
	
	/**
	 * <p>Create a cloned copy of this essence data. The essence data itself is not
	 * cloned, just the references to the essence data.</p>
	 *
	 * @return Cloned copy of this essence data.
	 */
	public EssenceData clone();
}
