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

package tv.amwa.maj.io.mxf;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.integer.Int8;
import tv.amwa.maj.integer.UInt32Array;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.record.Rational;

/**
 * <p>Element of an array that provides stream offsets and other description of an
 * edit unit along an incrementing timeline.</p>
 *
 *
 *
 * @see IndexTableSegment#getIndexEntryArray()
 * @see TypeDefinitions#IndexEntry
 * @see TypeDefinitions#IndexEntryArray
 */
public interface IndexEntry
	extends Cloneable {

	/**
	 * <p>Default value for the offset from the display order to the coded order.</p>
	 *
	 * @see #getTemporalOffset()
	 * @see #setTemporalOffset(byte)
	 */
	public final static @Int8 byte TEMPORALOFFSET_DEFAULT = 0;

	/**
	 * <p>Default value for the offset from the indexed frame to the previous key frame.</p>
	 *
	 * @see #getKeyFrameOffset()
	 * @see #setKeyFrameOffset(byte)
	 */
	public final static @Int8 byte KEYFRAMEOFFSET_DEFAULT = 0;

	/**
	 * <p>Default value for the flags stating what kind of frame this is. The value
	 * represents a bona fide random access frame with no forward or backward predicition, e.g.
	 * for MPEG-2 the frame is an I-frame.</p>
	 *
	 * @see #getFlags()
	 * @see #setFlags(byte)
	 * @see #EDITFLAG_RANDOM_ACCESS
	 */
	public final static @UInt8 byte FLAGS_DEFAULT = (byte) 0x80;

	/**
	 * <p>Set this bit on an edit unit flag to indicate that it is a bona fide random
	 * access point in the stream.</p>
	 *
	 * @see #getFlags()
	 * @see #setFlags(byte)
	 * @see #FLAGS_DEFAULT
	 */
	public final byte EDITFLAG_RANDOM_ACCESS = (byte) 0x80;

	/**
	 * <p>Set this bit on an edit unit flag to indicate that the edit unit contains
	 * an MPEG sequence header.</p>
	 *
	 * @see #getFlags()
	 * @see #setFlags(byte)
	 */
	public final byte EDITFLAG_SEQUENCE_HEADER = (byte) 0x40;
	/**
	 * <p>Set this bit on an edit unit flag to indicate forward prediction from the
	 * previous frame is in use.</p>
	 *
	 * @see #getFlags()
	 * @see #setFlags(byte)
	 */
	public final byte EDITFLAG_FORWARD_PREDICTION = (byte) 0x20;
	/**
	 * <p>Set this bit on an edit unit flag to indicate backward prediction from
	 * a future frame is in use.</p>
	 *
	 * @see #getFlags()
	 * @see #setFlags(byte)
	 */
	public final byte EDITFLAG_BACKWARD_PREDICTION = (byte) 0x10;

	/**
	 * <p>Returns the offset measured in edit units from the display order to the coded order
	 * for this index entry.</p>
	 *
	 * @return Offset measured in edit units from the display order to the coded order.
	 *
	 * @see #TEMPORALOFFSET_DEFAULT
	 * @see DeltaEntry#getPosTableIndex()
	 */
	public @Int8 byte getTemporalOffset();

	/**
	 * <p>Sets the offset measured in edit units from the display order to the coded order
	 * for this index entry.</p>
	 *
	 * @param temporalOffset Offset measured in edit units from the display order to the coded order.
	 *
	 * @see #TEMPORALOFFSET_DEFAULT
	 * @see DeltaEntry#setPosTableIndex(byte)
	 */
	public void setTemporalOffset(
			@Int8 byte temporalOffset);

	/**
	 * <p>Returns the offset measured in edit units to the previous key frame for this index entry,
	 * which may be the preceding I-frame for an MPEG stream. The value is zero if this is a key frame.</p>
	 *
	 * @return Offset measured in edit units to the previous key frame.
	 *
	 * @see #KEYFRAMEOFFSET_DEFAULT
	 */
	public @Int8 byte getKeyFrameOffset();

	/**
	 * <p>Sets the offset measured in edit units to the previous key frame for this index entry,
	 * which may be the preceding I-frame for an MPEG stream. The value is zero if this is a key frame.</p>
	 *
	 * @param keyFrameOffset Offset measured in edit units to the previous key frame.
	 *
	 * @see #KEYFRAMEOFFSET_DEFAULT
	 */
	public void setKeyFrameOffset(
			@Int8 byte keyFrameOffset);

	/**
	 * <p>Returns the edit flags that indicate the coding of the edit unit associated with the index
	 * entry.</p>
	 *
	 * <p>Note the following about prediction flags:</p>
	 *
	 * <ul>
	 *  <li>If no forward or backward prediction is set, either this is not an MPEG stream or if it
	 *  is an MPEG stream, the edit unit is an I-frame.</li>
	 *  <li>If <em>only</em> the forward prediction flag is set, the edit unit is a P-frame.</li>
	 *  <li>If the backward prediction is set, the edit unit is a B-frame and this may include
	 *  forward prediction, depending on the state of the forward prediction flag.</li>
	 * </ul>
	 *
	 * @return Edit flags that indicate the coding of the edit unit.
	 *
	 * @see #FLAGS_DEFAULT
	 * @see #EDITFLAG_RANDOM_ACCESS
	 * @see #EDITFLAG_SEQUENCE_HEADER
	 * @see #EDITFLAG_FORWARD_PREDICTION
	 * @see #EDITFLAG_BACKWARD_PREDICTION
	 */
	public @EditUnitFlag byte getFlags();

	/**
	 * <p>Sets the edit flags that indicate the coding of the edit unit associated with the index
	 * entry.</p>
	 *
	 * <p>Note the following about prediction flags:</p>
	 *
	 * <ul>
	 *  <li>If no forward or backward prediction is set, either this is not an MPEG stream or if it
	 *  is an MPEG stream, the edit unit is an I-frame.</li>
	 *  <li>If <em>only</em> the forward prediction flag is set, the edit unit is a P-frame.</li>
	 *  <li>If the backward prediction is set, the edit unit is a B-frame and this may include
	 *  forward prediction, depending on the state of the forward prediction flag.</li>
	 * </ul>
	 *
	 * @param flags Edit flags that indicate the coding of the edit unit.
	 *
	 * @see #FLAGS_DEFAULT
	 * @see #EDITFLAG_RANDOM_ACCESS
	 * @see #EDITFLAG_SEQUENCE_HEADER
	 * @see #EDITFLAG_FORWARD_PREDICTION
	 * @see #EDITFLAG_BACKWARD_PREDICTION
	 */
	public void setFlags(
			@EditUnitFlag byte flags);

	/**
	 * <p>Returns the offset of the edit unit within the container stream relative to the start of
	 * that container stream, measured in bytes.<p>
	 *
	 * @return Offset of the edit unit within the container stream relative to the start of that
	 * container stream, measured in bytes.
	 */
	public @UInt64 long getStreamOffset();

	/**
	 * <p>Sets the offset of the edit unit within the container stream relative to the start of
	 * that container stream, measured in bytes.</p>
	 *
	 * @param streamOffset offset of the edit unit within the container stream relative to the start of
	 * that container stream, measured in bytes.
	 *
	 * @throws IllegalArgumentException Stream offset values cannot be negative.
	 */
	public void setStreamOffset(
			@UInt64 long streamOffset)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the array of byte offsets from the stream offset of the edit unit to each slice. Other
	 * than the first slice always starts at the beginning of the edit unit, a slice offset indicates the
	 * start of a group of sections following a variable bitrate encoded item.</p>
	 *
	 * @return Array of byte offsets from the stream offset of the edit unit to each slice.
	 *
	 * @see DeltaEntry#getSlice()
	 */
	public @UInt32Array int[] getSliceOffset();

	/**
	 * <p>Sets the array of byte offsets from the stream offset of the edit unit to each slice. Other
	 * than the first slice always starts at the beginning of the edit unit, a slice offset indicates the
	 * start of a group of sections following a variable bitrate encoded item.</p>
	 *
	 * @param sliceOffset Array of byte offsets from the stream offset of the edit unit to each slice.
	 * @throws IllegalArgumentException Slice offset values cannot be negative.
	 *
	 * @see DeltaEntry#setSlice(byte)
	 */
	public void setSliceOffset(
			@UInt32Array int[] sliceOffset)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the array of fractional position offsets from the start of the content package to the
	 * synchronized sample in the content package. Fractional offsets are required when a difference exists
	 * between the frame size of content, such as between audio and video.</p>
	 *
	 * <p>For example, the audio may only be synchronized with the video every few frames. In this case
	 * the fraction indicates how much of a fraction differece exists between the start of the audio
	 * frame and the start of the video frame.</p>
	 *
	 * @return Array of fractional position offsets from the start of the content package to the
	 * synchronized sample in the content package.
	 *
	 * @see DeltaEntry#getPosTableIndex()
	 */
	public Rational[] getPosTable();

	/**
	 * <p>Sets the array of fractional position offsets from the start of the content package to the
	 * synchronized sample in the content package. Fractional offsets are required when a difference exists
	 * between the frame size of content, such as between audio and video.</p>
	 *
	 * <p>For example, the audio may only be synchronized with the video every few frames. In this case
	 * the fraction indicates how much of a fraction differece exists between the start of the audio
	 * frame and the start of the video frame.</p>
	 *
	 * @param posTable Array of fractional position offsets from the start of the content package to the
	 * synchronized sample in the content package.
	 *
	 * @throws NullPointerException Cannot create a position table with <code>null</code> elements.
	 *
	 * @see DeltaEntry#setPosTableIndex(byte)
	 */
	public void setPosTable(
			Rational[] posTable)
		throws NullPointerException;

	/**
	 * <p>Create a cloned copy of this index entry.</p>
	 *
	 * @return Cloned copy of this index entry.
	 */
	public IndexEntry clone();

	/**
	 * <p>Resolves any bytes that could not be parsed initially without the context of an {@linkplain IndexTableSegment index
	 * table segment} in the context of the given index table segment. The result of calling this method is to set the
	 * {@linkplain #getSliceOffset() slice offset} and {@linkplain #getPosTable() position table} properties using the
	 * {@linkplain IndexTableSegment#getSliceCount() slice count} and {@linkplain IndexTableSegment#getPosTableCount() position
	 * table count} of the index table segment.</p>
	 *
	 * @param parentSegment Segment that owns the index entry and provides the slice count and position table count values.
	 *
	 * @throws NullPointerException The given parent segment is <code>null</code>.
	 * @throws EndOfDataException Insufficient data in the unresolved bytes of each index entry to be resolved to arrays of
	 * the required size.
	 *
	 * @see IndexTableSegment#getSliceCount()
	 * @see IndexTableSegment#getPosTableCount()
	 */
	public void resolveBytes(
			IndexTableSegment parentSegment)
		throws NullPointerException,
			EndOfDataException;

}
