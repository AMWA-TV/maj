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

package tv.amwa.maj.io.mxf.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int8;
import tv.amwa.maj.integer.UInt32Array;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.io.mxf.EditUnitFlag;
import tv.amwa.maj.io.mxf.IndexEntry;
import tv.amwa.maj.io.mxf.IndexTableSegment;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.impl.TypeDefinitionRecordImpl;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.RationalImpl;

public class IndexEntryImpl
	implements
		IndexEntry,
		Cloneable,
		Serializable,
		XMLSerializable {

	private static final long serialVersionUID = -3909304731192434103L;

	private @Int8 byte temporalOffset = TEMPORALOFFSET_DEFAULT;
	private @Int8 byte keyFrameOffset = KEYFRAMEOFFSET_DEFAULT;
	private @EditUnitFlag byte flags = FLAGS_DEFAULT;
	private @UInt64 long streamOffset;
	private @UInt32Array int[] sliceOffset = null;
	private Rational[] posTable = null;

	private transient byte[] unresolvedBytes = null;

	static {
		TypeDefinitionRecordImpl.registerInterfaceMapping(IndexEntry.class, IndexEntryImpl.class);
	}

	public IndexEntryImpl() { }

	public IndexEntryImpl(
			@UInt64 long streamOffset)
		throws IllegalArgumentException {

		setStreamOffset(streamOffset);
	}

	public IndexEntryImpl(
			@Int8 byte temporalOffset,
			@Int8 byte keyFrameOffset,
			@EditUnitFlag byte flags,
			@UInt64 long streamOffset)
		throws IllegalArgumentException {

		setTemporalOffset(temporalOffset);
		setKeyFrameOffset(keyFrameOffset);
		setFlags(flags);
		setStreamOffset(streamOffset);
	}

	IndexEntryImpl(
			@Int8 byte temporalOffset,
			@Int8 byte keyFrameOffset,
			@UInt8 byte flags,
			@UInt64 long streamOffset,
			byte[] unresolvedBytes) {

		setTemporalOffset(temporalOffset);
		setKeyFrameOffset(keyFrameOffset);
		setFlags(flags);
		setStreamOffset(streamOffset);
		this.unresolvedBytes = unresolvedBytes;
	}

	public @Int8 byte getTemporalOffset() {

		return temporalOffset;
	}

	public void setTemporalOffset(
			@Int8 byte temporalOffset) {

		this.temporalOffset = temporalOffset;
	}

	public @Int8 byte getKeyFrameOffset() {

		return keyFrameOffset;
	}

	public void setKeyFrameOffset(
			@Int8 byte keyFrameOffset) {

		this.keyFrameOffset = keyFrameOffset;
	}

	public @EditUnitFlag byte getFlags() {

		return flags;
	}

	public void setFlags(
			@EditUnitFlag byte flags) {

		this.flags = flags;
	}

	public @UInt64 long getStreamOffset() {

		return streamOffset;
	}

	public void setStreamOffset(
			@UInt64 long streamOffset)
		throws IllegalArgumentException {

		if (streamOffset < 0)
			throw new IllegalArgumentException("Cannot set the stream offset to a negative value.");

		this.streamOffset = streamOffset;
	}

	public @UInt32Array int[] getSliceOffset() {

		if (sliceOffset == null)
			return new int[0];

		return sliceOffset.clone();
	}

	public void setSliceOffset(
			@UInt32Array int[] sliceOffset)
		throws IllegalArgumentException {

		if ((sliceOffset == null) || (sliceOffset.length == 0))
			this.sliceOffset = null;

		for ( int i : sliceOffset )
			if (i < 0)
				throw new IllegalArgumentException("Cannot set a slice offset to a negative value.");

		this.sliceOffset = sliceOffset.clone();
	}

	public Rational[] getPosTable() {

		if (posTable == null)
			return new Rational[0];

		return cloneRationalTable(posTable);
	}

	public void setPosTable(
			Rational[] posTable)
		throws NullPointerException {

		if ((posTable == null) || (posTable.length == 0))
			this.posTable = null;

		this.posTable = cloneRationalTable(posTable);
	}

	private final static Rational[] cloneRationalTable(
			Rational[] source)
		throws NullPointerException {

		if (source == null) return null;

		Rational[] clonedTable = new Rational[source.length];
		for ( int x = 0 ; x < source.length ; x++ )
			if (source[x] == null)
				throw new NullPointerException("Cannot create a position table with null elements.");
			else
				clonedTable[x] = source[x].clone();

		return clonedTable;
	}

	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof IndexEntryImpl)) return false;

		IndexEntry testEntry = (IndexEntry) o;

		if (streamOffset != testEntry.getStreamOffset()) return false;
		if (temporalOffset != testEntry.getTemporalOffset()) return false;
		if (keyFrameOffset != testEntry.getKeyFrameOffset()) return false;
		if (flags != testEntry.getFlags()) return false;

		int slicesPresent = 0;
		if ((sliceOffset != null) && (sliceOffset.length > 0)) slicesPresent++;
		int[] testSliceOffset = testEntry.getSliceOffset();
		if ((testSliceOffset != null) && (testSliceOffset.length > 0)) slicesPresent++;

		if (slicesPresent == 1) return false;
		if (slicesPresent == 2) {

			if (sliceOffset.length != testSliceOffset.length)
				return false;
			for ( int x = 0 ; x < sliceOffset.length ; x++ )
				if (sliceOffset[x] != testSliceOffset[x]) return false;
		}

		int posTablesPresent = 0;
		if ((posTable != null) && (posTable.length > 0)) posTablesPresent++;
		Rational[] testPosTable = testEntry.getPosTable();
		if ((testPosTable != null) && (testPosTable.length > 0)) posTablesPresent++;

		if (posTablesPresent == 1) return false;
		if (posTablesPresent == 2) {

			if (posTable.length != testPosTable.length)
				return false;
			for ( int x = 0 ; x < testPosTable.length ; x++ )
				if (!posTable[x].equals(testPosTable[x])) return false;
		}

		return true;
	}

	public int hashCode() {

		int hashcode = (temporalOffset << 24) ^ (keyFrameOffset << 16) & (flags << 8);
		hashcode ^= Long.valueOf(streamOffset).hashCode();
		if (sliceOffset != null) hashcode ^= sliceOffset.hashCode();
		if (posTable != null) hashcode ^= posTable.hashCode();

		return hashcode;
	}

	/**
	 * <p>Create a cloned copy of this IndexEntryImpl.</p>
	 *
	 * @return Cloned copy of this IndexEntryImpl.
	 */
	public IndexEntry clone() {

		try {
			return (IndexEntry) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Implements cloneable so should never get here.
			throw new InternalError(cnse.getMessage());
		}
	}

	// TODO parseFactory

	public void resolveBytes(
			IndexTableSegment parentSegment)
		throws NullPointerException,
			EndOfDataException {

		if (unresolvedBytes == null) return;
		if (parentSegment == null)
			throw new NullPointerException("Cannot resolve bytes using a null parent index table segment.");

		ByteBuffer unresolvedBuffer = ByteBuffer.wrap(unresolvedBytes);

		int sliceCount = 0;
		try {
			sliceCount = parentSegment.getSliceCount();
		}
		catch (PropertyNotPresentException pnpe) { }

		int posTableCount = 0;
		try {
			parentSegment.getPosTableCount();
		}
		catch (PropertyNotPresentException pnpe) { }

		if (unresolvedBuffer.remaining() < (sliceCount * 4 + posTableCount * 8))
			throw new EndOfDataException("Insufficient bytes to resolve remaining bytes to a position table entry.");

		if (sliceCount == 0)
			sliceOffset = null;
		else {
			sliceOffset = new int[sliceCount];
			for ( int x = 0 ; x < sliceCount ; x++ )
				sliceOffset[x] = unresolvedBuffer.getInt();
		}

		if ( posTableCount == 0)
			posTable = null;
		else {
			posTable = new Rational[posTableCount];
			for ( int x = 0 ; x < posTableCount ; x++ )
				posTable[x] = new RationalImpl(unresolvedBuffer.getInt(), unresolvedBuffer.getInt());
		}

		unresolvedBytes = null;
	}

	public final static String INDEXENTRY_TAG = "IndexEntry";
	public final static String TEMPORALOFFSET_TAG = "TemporalOffset";
	public final static String KEYFRANEOFFST_TAG = "KeyFrameOffset";
	public final static String FLAGS_TAG = "Flags";
	public final static String STREAMOFFSET_TAG = "StreamOffset";
	public final static String SLICEOFFSET_TAG = "SliceOffset";
	public final static String POSTABLE_TAG = "PosTable";

	public void appendXMLChildren(
			Node parent) {

		Node indexEntryElement;

		if (parent instanceof DocumentFragment)
			indexEntryElement =
				XMLBuilder.createChild(parent, MXFConstants.RP210_NAMESPACE,
						MXFConstants.RP210_PREFIX, INDEXENTRY_TAG);
		else
			indexEntryElement = parent;

		XMLBuilder.appendElement(indexEntryElement, MXFConstants.RP210_NAMESPACE,
				MXFConstants.RP210_PREFIX, TEMPORALOFFSET_TAG, temporalOffset);
		XMLBuilder.appendElement(indexEntryElement, MXFConstants.RP210_NAMESPACE,
				MXFConstants.RP210_PREFIX, KEYFRANEOFFST_TAG, keyFrameOffset);
		XMLBuilder.appendElement(indexEntryElement, MXFConstants.RP210_NAMESPACE,
				MXFConstants.RP210_PREFIX, FLAGS_TAG, flags);
		XMLBuilder.appendElement(indexEntryElement, MXFConstants.RP210_NAMESPACE,
				MXFConstants.RP210_PREFIX, STREAMOFFSET_TAG, streamOffset);

		if ((sliceOffset != null) && (sliceOffset.length > 0)) {
			Element sliceOffsetElement = XMLBuilder.createChild(indexEntryElement,
					MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, SLICEOFFSET_TAG);

			for ( int offset : sliceOffset )
				XMLBuilder.appendElement(sliceOffsetElement, MXFConstants.RP210_NAMESPACE,
						MXFConstants.RP210_PREFIX, "UInt32", offset);
		}

		if ((posTable != null) && (posTable.length > 0)) {
			Element posTableElement = XMLBuilder.createChild(indexEntryElement,
					MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, POSTABLE_TAG);

			for ( Rational pos : posTable )
				XMLBuilder.appendElement(posTableElement, MXFConstants.RP210_NAMESPACE,
						MXFConstants.RP210_PREFIX, "Rational", pos.toString());
		}
	}

	public String getComment() {

		return null;
	}

	public String toString() {

		return XMLBuilder.toXMLNonMetadata(this);
	}

	public final static IndexEntry createFromBuffer(
			ByteBuffer buffer)
		throws NullPointerException,
			EndOfDataException {

		if (buffer == null)
			throw new NullPointerException("Cannot create an index entry from a null value.");

		if (buffer.remaining() < 11)
			throw new EndOfDataException("Insufficient bytes remaining in the buffer to read an index entry.");

		byte temporalOffset = buffer.get();
		byte keyFrameOffset = buffer.get();
		byte flags = buffer.get();
		long streamOffset = buffer.getLong();

		byte[] unresolvedBytes = null;
		if (buffer.hasRemaining()) {
			unresolvedBytes = new byte[buffer.remaining()];
			buffer.get(unresolvedBytes);
		}

		return new IndexEntryImpl(temporalOffset, keyFrameOffset, flags, streamOffset, unresolvedBytes);
	}
	
	public final static void writeToBuffer(
			IndexEntry entry,
			ByteBuffer buffer)
		throws NullPointerException,
			InsufficientSpaceException {
		
		if (entry == null)
			throw new NullPointerException("Cannot write a null index entry to a buffer.");
		if (buffer == null)
			throw new NullPointerException("Cannot write an index entry to a null buffer.");
		
		if (buffer.remaining() < lengthAsBuffer(entry))
			throw new InsufficientSpaceException("Insufficient space in the given buffer to write an index entry value.");
		
		buffer.put(entry.getTemporalOffset());
		buffer.put(entry.getKeyFrameOffset());
		buffer.put(entry.getFlags());
		buffer.putLong(entry.getStreamOffset());
		for (int offset : entry.getSliceOffset()) {
			buffer.putInt(offset);
		}
		for (Rational pos : entry.getPosTable()) {
			buffer.putInt(pos.getNumerator());
			buffer.putInt(pos.getDenominator());
		}
		
	}
	
	public final static long lengthAsBuffer(
			IndexEntry value) {
		
		long length = 11l;
		length += 4*value.getSliceOffset().length;
		length += 8*value.getPosTable().length;
		
		return length;
	}

}
