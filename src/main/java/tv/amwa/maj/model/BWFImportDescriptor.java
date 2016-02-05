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
 * $Log: BWFImportDescriptor.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/02/28 12:50:36  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.3  2008/01/27 11:07:41  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 13:04:52  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:48  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.exception.ObjectAlreadyAttachedException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;


/**
 * <p>Specifies the description of audio essence that is formatted according the the 
 * <a href="http://www.ebu.ch/en/technical/publications/userguides/bwf_user_guide.php">Broadcast Wave Format</a>
 * specifications, which is a file essence source that is not directly manipulated by an AAF application.</p>
 *
 *
 */
public interface BWFImportDescriptor
	extends ImportDescriptor {

	/**
	 * <p>Set the file security code of the quality report of this BWF import descriptor.
	 * This property is optional and can be omitted by setting its value to 
	 * <code>null</code>.</p>
	 *
	 * @param fileSecurityReport File security code of the quality report of this BWF import
	 * descriptor.
	 */
	public void setFileSecurityReport(
			@UInt32 Integer fileSecurityReport);

	/**
	 * <p>Returns the file security code of the quality report of this BWF import 
	 * descriptor. This property is optional.</p>
	 *
	 * @return File security code of the quality report of this BWF import 
	 * descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional file security code value is
	 * not present in this BWF import descriptor.
	 */
	public @UInt32 int getFileSecurityReport()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the file security code of the BWF wave data of this BWF import 
	 * descriptor. This property is optional and can be omitted by setting its value to 
	 * <code>null</code>.</p>
	 *
	 * @param fileSecurityWave File security code of the BWF wave data of this BWF import 
	 * descriptor.
	 */
	public void setFileSecurityWave(
			@UInt32 Integer fileSecurityWave);

	/**
	 * <p>Returns the file security code of the BWF wave data of this BWF import 
	 * descriptor. This property is optional.</p>
	 *
	 * @return File security code of the BWF wave data of this BWF import 
	 * descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional file security wave property
	 * is not present in this BWF import descriptor.
	 */
	public @UInt32 int getFileSecurityWave()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the total number of {@linkplain RIFFChunk unknown BWF chunks} attached
	 * to this BWF import descriptor. </p>
	 *
	 * @return Number of unknown BWF chunks attached to this BWF descriptor.
	 */
	public @UInt32 int countUnknownBWFChunks();
	
	/**
	 * <p>Clear the {@linkplain RIFFChunk unknown BWF chunks} attached to this BWF import
	 * descriptor, omitting this optional property.</p>
	 */
	public void clearUnknownBWFChunks();

	/**
	 * <p>Append a {@linkplain RIFFChunk} to end of the list of unknown BWF chunks of this 
	 * BWF import descriptor.</p>
	 *
	 * @param chunk Chunk to append to the current list.
	 * 
	 * @throws NullPointerException The chunk argument is <code>null</code>.
	 * @throws ObjectAlreadyAttachedException The chunk is already contained within
	 * the list of unknown BWF chunks of this BWF import descriptor.
	 */
	public void appendUnknownBWFChunk(
	    	RIFFChunk chunk)
		throws NullPointerException,
			ObjectAlreadyAttachedException;

	/**
	 * <p>Prepend a {@linkplain RIFFChunk} to the beginning of the list of unknown BWF
	 * chunks of this BWF descriptor.</p>
	 *
	 * @param chunk Chunk to prepend to the current list.
	 * 
	 * @throws NullPointerException The chunk argument is <code>null</code>.
	 * @throws ObjectAlreadyAttachedException The The chunk is already contained within
	 * the list of unknown BWF chunks of this BWF import descriptor.
	 */
	public void prependUnknownBWFChunk (
	   		RIFFChunk chunk)
		throws NullPointerException,
			ObjectAlreadyAttachedException;

	/**
	 * <p>Insert a {@linkplain RIFFChunk} into the list of unknown BWF chunks of this BWF
	 * import descriptor at the given index. Chunks already existing at the given and 
	 * higher indices will be moved to the next higher index to accommodate the new 
	 * chunk.</p>
	 *
	 * @param index Index through the list of unknown BWF chunks at which to insert
	 * the additional chunk.
	 * @param chunk Chunk to insert at the given index.
	 * 
	 * @throws NullPointerException The chunk argument is <code>null</code>.
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable
	 * range for the current list of unknown BWF chunks of this BWF import descriptor.
	 * @throws ObjectAlreadyAttachedException The given chunk is already contained in 
	 * the list of unknown BWF chunks of this BWF import descriptor.
	 */
	public void insertUnknownBWFChunkAt(
			@UInt32 int index,
			RIFFChunk chunk)
		throws NullPointerException,
			IndexOutOfBoundsException,
			ObjectAlreadyAttachedException;

	/**
	 * <p>Returns the {@linkplain RIFFChunk} from the list of unknown BWF chunks of this
	 * BWF import descriptor at the given index.</p>
	 *
	 * @param index Index of the chunk to retrieve.
	 * @return Chunk at the given index into the list of unknown BWF chunks of this
	 * BWF import descriptor.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable
	 * range for the current list of unknown BWF chunks of this BWF import descriptor.
	 */
	public RIFFChunk getUnknownBWFChunkAt(
			@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Removes the {@linkplain RIFFChunk} at the given index through the list of unknown
	 * BWF chunks of this BWF descriptor. Chunks already existing at indices higher than 
	 * the given index will be moved to the next lower index to fill the gap left by the
	 * removal.</p>
	 *
	 * @param index Index of the chunk to remove. 
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable
	 * range for the current list of unknown BWF chunks of this BWF import descriptor.
	 */
	public void removeUnknownBWFChunkAt(
			@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns a copy of the list of unknown BWF chunks of this BWF import descriptor.
	 * This is an optional property.</p>
	 *
	 * @return Shallow copy of the list of unknown BWF chunks of this BWF import descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional list of unknown BWF chunks is not
	 * present for this BWF import descriptor.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#RIFFChunkStrongReferenceVector
	 */
	public List<? extends RIFFChunk> getUnknownBWFChunks()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the coding history property of this BWF import descriptor, which is
	 * taken from the coding history property of the associated &lt;bext&gt; chunk.
	 * This property is optional and setting it to <code>null</code> will omit the 
	 * property.</p>
	 *
	 * @param codingHistory Coding history property of this BWF import descriptor.
	 */
	public void setCodingHistory(
			String codingHistory);

	/**
	 * <p>Returns the coding history property of this BWF import descriptor, which is
	 * taken from the coding history property of the associated &lt;bext&gt; chunk.
	 * This property is optional.</p>
	 *
	 * @return Coding history property of this BWF import descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional coding history property is not
	 * present in this BWF import descriptor.
	 */
	public String getCodingHistory()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the basic data of this BWF import descriptor, which is taken from the
	 * basic data property of the associated &lt;qlty&gt; chunk. This property is optional 
	 * and setting it to <code>null</code> will omit the property.</p>
	 *
	 * @param basicData Basic data of this BWF import descriptor.
	 */
	public void setBasicData(
			String basicData);

	/**
	 * <p>Returns the basic data of this BWF import descriptor, which is taken from the
	 * basic data property of the associated &lt;qlty&gt; chunk. This property is optional.</p>
	 *
	 * @return Basic data of this BWF import descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional basic data property is not present
	 * in this BWF import descriptor.
	 */
	public String getBasicData()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the start of modulation data of this BWF import descriptor, which is taken
	 * from the start modulation data property of the associated &lt;qlty&gt; chunk. This 
	 * property is optional and setting it to <code>null</code> will omit the property.</p>
	 *
	 * @param startOfModulation Start of modulation data of this BWF import descriptor.
	 */
	public void setStartOfModulation(
			String startOfModulation);

	/**
	 * <p>Returns the start of modulation data of this BWF import descriptor, which is taken
	 * from the start modulation data poperty of the associated &lt;qlty&gt; chunk. This property
	 * is optional.</p>
	 *
	 * @return Start of modulation data of this BWF import descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional start of modulation property is not
	 * present in this BWF import descriptor.
	 */
	public String getStartOfModulation()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the quality event data property of this BWF import descriptor, which is taken
	 * from the quality event data property of the associated &lt;qlty&gt; chunk. This 
	 * property is optional and setting it to <code>null</code> will omit the property.</p>
	 *
	 * @param qualityEvent Quality event data property of this BWF import descriptor.
	 */
	public void setQualityEvent(
			String qualityEvent);

	 /**
	 * <p>Returns the quality event data property of this BWF import descriptor, which is taken
	 * from the quality event data property of the associated &lt;qlty&gt; chunk. This property
	 * is optional.</p>
	 *
	 * @return Quality event data property of this BWF import descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional quality event property is not present
	 * in this BWF import descriptor.
	 */
	public String getQualityEvent()
	 	throws PropertyNotPresentException;

	/**
	 * <p>Sets the end of modulation property of this BWF import descriptor, which is taken from
	 * the end modulation data property of the associated &lt;qlty&gt; chunk. This 
	 * property is optional and setting it to <code>null</code> will omit the property.</p>
	 *
	 * @param endOfModulation End of modulation property of this BWF import descriptor.
	 */
	public void setEndOfModulation(
			String endOfModulation);

	 /**
	 * <p>Returns the end of modulation property of this BWF import descriptor, which is 
	 * taken from the end modulation data property of the associated &lt;qlty&gt; chunk. This 
	 * property is optional.</p>
	 *
	 * @return End of modulation property of this BWF import descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional end of modulation property is not
	 * present in this BWF import descriptor. 
	 */
	public String getEndOfModulation()
	 	throws PropertyNotPresentException;

	/**
	 * <p>Sets the quality parameter property of this BWF import descriptor, which is taken
	 * from the quality parameter data property of the associated &lt;qlty&gt; chunk. This 
	 * property is optional and setting it to <code>null</code> will omit the property.</p>
	 *
	 * @param qualityParameter Quality parameter property of this BWF import descriptor.
	 */
	public void setQualityParameter(
			String qualityParameter);

	/**
	 * <p>Returns the quality parameter property of this BWF import descriptor, which is taken
	 * from the quality parameter data property of the associated &lt;qlty&gt; chunk. This 
	 * property is optional.</p>
	 *
	 * @return Quality parameter property of this BWF import descriptor
	 * 
	 * @throws PropertyNotPresentException The optional quality parameter property is not present
	 * in this BWF import descriptor.
	 */
	public String getQualityParameter()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the operator comment property of this BWF import descriptor, which is taken
	 * from the comment of operator property of the associated &lt;qlty&gt; chunk. This 
	 * property is optional and setting it to <code>null</code> will omit the property.</p>
	 *
	 * @param operatorComment Operator comment of this BWF import descriptor.
	 */
	public void setOperatorComment(
			String operatorComment);

	/**
	 * <p>Returns the operator comment property of this BWF import descriptor, which is taken
	 * from the comment of operator property of the associated &lt;qlty&gt; chunk. This 
	 * property is optional.</p>
	 *
	 * @return Operator comment property of this BWF import descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional operator comment property is not
	 * present in this BWF import descriptor.
	 */
	public String getOperatorComment()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the cue sheet property of this BWF import descriptor, which is taken from
	 * the cue sheet data property of the associated &lt;qlty&gt; chunk. This 
	 * property is optional and setting it to <code>null</code> will omit the property.</p>
	 *
	 * @param cueSheet Cue sheet property of this BWF import descriptor.
	 */
	public void setCueSheet(
			String cueSheet);

	/**
	 * <p>Returns the cue sheet property of this BWF import descriptor, which is taken from
	 * the cue sheet data property of the associated &lt;qlty&gt; chunk. This 
	 * property is optional</p>
	 *
	 * @return Cue sheet property of this BWF import descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional cue sheet property is not present
	 * in this BWF import descriptor.
	 */
	public String getCueSheet()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this BWF import descriptor.</p>
	 *
	 * @return Cloned copy of this BWF import descriptor.
	 */
	public BWFImportDescriptor clone();
}
