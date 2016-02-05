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

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.io.mxf.DeltaEntry;
import tv.amwa.maj.io.mxf.IndexEntry;
import tv.amwa.maj.io.mxf.IndexTable;
import tv.amwa.maj.io.mxf.IndexTableSegment;
import tv.amwa.maj.io.mxf.LocalTagEntry;
import tv.amwa.maj.io.mxf.MXFBuilder;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.Padded;
import tv.amwa.maj.io.mxf.PrimerPack;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.record.impl.RationalImpl;

public class IndexTableImpl
	implements
		IndexTable,
		Cloneable,
		Padded {

	private @UInt32 int indexSID = -1;
	private SortedMap<Long, IndexTableSegment> segments =
		Collections.synchronizedSortedMap(new TreeMap<Long, IndexTableSegment>());

	private long paddingFillSize = 0;

	public IndexTableImpl() { }

	public @UInt32 int getIndexSID() {

		return indexSID;
	}

	void addSegment(
			IndexTableSegment segment)
		throws NullPointerException {

		if (segment == null)
			throw new NullPointerException("Cannot add a null index table segment to the index table.");

		if (indexSID == -1)
			indexSID = segment.getIndexSID();

		segments.put(segment.getIndexStartPosition(), segment);
	}

	public IndexTable clone() {

		try {
			return (IndexTable) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Implements cloneable so should never happen
			throw new InternalError(cnse.getMessage());
		}
	}

	private final static Map<AUIDImpl, MetadataObject> referenceTable = new HashMap<AUIDImpl, MetadataObject>();
	private final static List<ResolutionEntry> resolutions = new Vector<ResolutionEntry>();

	public final static IndexTable createFromBuffer(
			ByteBuffer buffer)
		throws NullPointerException,
			BufferUnderflowException {

		if (buffer == null)
			throw new NullPointerException("Cannot create an index table from a null value.");

		long lastFillLength = 0;

		IndexTableImpl indexTable = new IndexTableImpl();
		int preserveLimit = buffer.limit();

		while (buffer.hasRemaining()) {

			lastFillLength = MXFBuilder.skipKLVFill(buffer);
			if (lastFillLength > 0) continue;
			lastFillLength = -1;

			UL key = MXFBuilder.readKey(buffer);
			long length = MXFBuilder.readBERLength(buffer);
			int limit = (int) (buffer.position() + length);

			if (!key.equals(IndexTableSegment.key)) {
				buffer.position(limit);
				continue;
			}

			buffer.limit(limit);
			try {
				IndexTableSegment nextSegment =
					(IndexTableSegment) MXFBuilder.readLocalSet(key, buffer, indexPrimer, referenceTable, resolutions);
				indexTable.addSegment(nextSegment);
			}
			catch (Exception e) {
				System.err.println(e.getClass().getName() + " thrown when trying to read an index table segment: " + e.getMessage());
			}
			finally {
				buffer.limit(preserveLimit);
				buffer.position(limit);
			}
		}

		if (lastFillLength > 0)
			indexTable.setPaddingFillSize(lastFillLength);

		referenceTable.clear();
		resolutions.clear();

		return indexTable;
	}

	public long getPaddingFillSize() {

		return paddingFillSize;
	}

	public void setPaddingFillSize(
			long paddingFillSize)
		throws IllegalArgumentException {

		if (paddingFillSize < 0)
			throw new IllegalArgumentException("Cannot set the padding size of an index table to a negative value.");

		this.paddingFillSize = paddingFillSize;
	}

	public String toString() {

		StringBuffer tableAsString = new StringBuffer();
		tableAsString.append("Index table made up from ");
		tableAsString.append(segments.size());
		tableAsString.append(" segments:\n");

		for ( Long startIndex : segments.keySet() ) {
			tableAsString.append(segments.get(startIndex).toString());
			tableAsString.append('\n');
		}

		return tableAsString.toString();
	}

	public final static Rational zeroProportion = new RationalImpl(0, 1);
	public final static DeltaEntry[] singleElementDelta = new DeltaEntry[] {
		new DeltaEntryImpl(0)
	};

	public long streamOffset(
			@PositionType long position,
			int subStream)
		throws IllegalArgumentException {

		Long likelyInstance = segments.headMap(position + 1).lastKey();
		IndexTableSegment likelySegment = segments.get(likelyInstance);

		DeltaEntry[] deltas = null;
		try {
			deltas = likelySegment.getDeltaEntryArray();
		}
		catch (PropertyNotPresentException pnpe) {
			deltas = singleElementDelta;
		}
		if ((subStream < 0) || (subStream >= deltas.length))
			throw new IllegalArgumentException("The given substream is outside of the range of the sub stream known to this index table.");
		DeltaEntry elementDelta = deltas[subStream];

		position -= likelySegment.getIndexStartPosition();
		if ((position < 0) || (position >= likelySegment.getIndexDuration()))
			throw new IllegalArgumentException("Stream offset cannot be resolved.");

		IndexEntry[] editUnitIndex = null;
		try {
			editUnitIndex = likelySegment.getIndexEntryArray();
		}
		catch (PropertyNotPresentException pnpe) { }

		byte posTableIndex = elementDelta.getPosTableIndex();
		if (posTableIndex <= DeltaEntry.APPLY_TEMPORAL_REORDERING) {
			if (editUnitIndex == null)
				throw new IllegalArgumentException("Temporal reordering expected but no index entries provided.");
			position += editUnitIndex[(int) position].getTemporalOffset();
		}

		long result = (editUnitIndex != null) ?
				editUnitIndex[(int) position].getStreamOffset() :
				position * likelySegment.getEditUnitByteCount();

		byte slice = elementDelta.getSlice();
		if (slice > 0) {
			if (editUnitIndex == null)
				throw new IllegalArgumentException("Slices in the delta table but no index entries provided.");

			result += editUnitIndex[(int) position].getSliceOffset()[slice - 1];
		}

		result += elementDelta.getElementDelta();

		Rational proportion = ((posTableIndex > 0) && (editUnitIndex != null)) ?
				editUnitIndex[(int) position].getPosTable()[posTableIndex - 1] :
				zeroProportion;

		// FIXME fractional processing
		if (proportion.getNumerator() != 0)
			throw new IllegalArgumentException("Fractional offset processing not supported in this version of MAJ.");

		return result;
	}
}
