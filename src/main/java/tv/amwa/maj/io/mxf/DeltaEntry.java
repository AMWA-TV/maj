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
 * $Log: DeltaEntry.java,v $
 * Revision 1.4  2011/07/28 18:50:57  vizigoth
 * Changes to better support creating index tables with delta entries.
 *
 * Revision 1.3  2011/07/27 16:52:58  vizigoth
 * Capable of reading and writing audio essence component files.
 *
 * Revision 1.2  2010/01/21 20:50:56  vizigoth
 * Updates to index table support to the point where index table data can be read from MXF files and stream offsets can be calculated.
 *
 * Revision 1.1  2010/01/19 14:44:23  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
 *
 *
 */

package tv.amwa.maj.io.mxf;

import tv.amwa.maj.integer.Int8;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;

/**
 * <p>Elements of an array defining byte offset values along an incrementing timeline,
 * mapping indexed elements into slices.</p>
 * 
 *
 * 
 * @see IndexTableSegment#getDeltaEntryArray()
 * @see TypeDefinitions#DeltaEntry
 * @see TypeDefinitions#DeltaEntryArray
 * @see MXFFactory#makeDeltaEntry(byte, byte, int)
 * @see MXFFactory#makeDeltaEntry(int)
 */
public interface DeltaEntry {
	
	/**
	 * <p>Indicates that position table reordering should be applied and that the difference between 
	 * an edit units stored order and display order is stored in the 
	 * {@linkplain IndexEntry#getTemporalOffset() temporal offset property} of an index entry.</p>
	 * 
	 * @see #NO_TEMPORAL_REORDERING
	 * @see #POSTABLEINDEX_DEFAULT
	 * @see #getPosTableIndex()
	 * @see #setPosTableIndex(byte)
	 */
	public final static @Int8 byte APPLY_TEMPORAL_REORDERING = -1;
	
	/**
	 * <p>Indicates that no temporal ordering should be applied and that no different exists between
	 * the stored order and display order.</p>
	 * 
	 *  @see #APPLY_TEMPORAL_REORDERING
	 *  @see #POSTABLEINDEX_DEFAULT
	 *  @see #getPosTableIndex()
	 *  @see #setPosTableIndex(byte)
	 */
	public final static @Int8 byte NO_TEMPORAL_REORDERING = 0;
	
	/**
	 * <p>Default value for the position table index of a delta entry that indicates that no different exists
	 * between the stored order of edit units and the display order.</p>
	 * 
	 * @see #POSTABLEINDEX_DEFAULT
	 * @see #NO_TEMPORAL_REORDERING
	 * @see #getPosTableIndex()
	 * @see #setPosTableIndex(byte)
	 */
	public final static @Int8 byte POSTABLEINDEX_DEFAULT = NO_TEMPORAL_REORDERING;
	
	/**
	 * <p>Default value for the slice containing the delta entry, indicating that the entry is contained in the
	 * first slice.</p> 
	 * 
	 * @see #getSlice()
	 * @see #setSlice(byte)
	 * @see IndexEntry#getSliceOffset()
	 */
	public final static @UInt8 byte SLICE_DEFAULT = 0;

	/**
	 * <p>Returns if and how the element is subject to temporal reordering. Values are as follows:</p>
	 * 
	 * <ul>
	 *  <li>-1 indicates that the {@linkplain IndexEntry#getTemporalOffset() temporal ordering indicated in 
	 *  the index entry} should be used.</li>
	 *  <li>0 indicates no temporal reordering and that no difference exists between stored order and display
	 *  order.</li>
	 *  <li>A positive value of <em>n</em> provides an index into the {@linkplain IndexEntry#getPosTable() fractional 
	 *  temporal offset position table} in the index entry.</li> 
	 * </ul>
	 * 
	 * @return If and how the element is subject to temporal reordering.
	 * 
	 * @see #POSTABLEINDEX_DEFAULT
	 * @see #APPLY_TEMPORAL_REORDERING
	 * @see #NO_TEMPORAL_REORDERING
	 */
	public @Int8 byte getPosTableIndex();

	/**
	 * <p>Sets if and how the element is subject to temporal reordering. Values are as follows:</p>
	 * 
	 * <ul>
	 *  <li>-1 indicates that the {@linkplain IndexEntry#getTemporalOffset() temporal ordering indicated in 
	 *  the index entry} should be used.</li>
	 *  <li>0 indicates no temporal reordering and that no difference exists between stored order and display
	 *  order.</li>
	 *  <li>A positive value of <em>n</em> provides an index into the {@linkplain IndexEntry#getPosTable() fractional 
	 *  temporal offset position table} in the index entry.</li> 
	 * </ul>
	 * 
	 * @param posTableIndex If and how the element is subject to temporal reordering.
	 * @throws IllegalArgumentException The position table index cannot be less than -1.
	 * 
	 * @see #POSTABLEINDEX_DEFAULT
	 * @see #APPLY_TEMPORAL_REORDERING
	 * @see #NO_TEMPORAL_REORDERING
	 */
	public @UInt8 void setPosTableIndex(
			@UInt8 byte posTableIndex)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the slice number for the slice containing this delta entry. For a delta entry following a
	 * constant bit rate element, the slice number is normally the same. An incremented slice number is required for any
	 * delta entry that follows a variable bit rate element.</p>
	 * 
	 * @return Slice number for the slice containing this delta entry.
	 * 
	 * @see #SLICE_DEFAULT
	 * @see IndexEntry#getSliceOffset()
	 */
	public @UInt8 byte getSlice();

	/**
	 * <p>Sets the slice number for the slice containing this delta entry. A new slice is required for any
	 * element that follows a variable bit rate element.</p>
	 * 
	 * @param slice Slice number for the slice containing this delta entry.
	 * 
	 * @throws IllegalArgumentException A slice number cannot be negative.
	 * 
	 * @see #SLICE_DEFAULT
	 * @see IndexEntry#setSliceOffset(int[])
	 */
	public void setSlice(
			@UInt8 byte slice) 
		throws IllegalArgumentException;

	/**
	 * <p>Returns the offset measured in bytes from the start of the start of the indexed element described by
	 * this delta entry and the start of the current slice. For any delta entry at the start of a slice,
	 * this value is zero.</p>
	 * 
	 * @return Offset measured in bytes from the start of the start of the indexed element and the start of the 
	 * current slice.
	 * 
	 * @see #getSlice()
	 */
	public @UInt32 int getElementDelta();

	/**
	 * <p>Sets offset measured in bytes from the start of the start of the indexed element described by
	 * this delta entry and the start of the current slice.  For any delta entry at the start of a slice,
	 * this value is zero.</p>
	 * 
	 * @param elementDelta Offset measured in bytes from the start of the start of the indexed element and the start of the 
	 * current slice.
	 * 
	 * @throws IllegalArgumentException Cannot set the element delta to a negative value.
	 * 
	 * @see #getSlice()
	 */
	public void setElementDelta(
			@UInt32 int elementDelta)
		throws IllegalArgumentException;

	/**
	 * <p>Create a cloned copy of this delta entry.</p>
	 * 
	 * @return Cloned copy of this delta entry.
	 */
	public DeltaEntry clone();
}