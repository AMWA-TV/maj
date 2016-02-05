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
 * $Log: IndexTableSegmentImpl.java,v $
 * Revision 1.4  2011/07/28 18:51:18  vizigoth
 * Changes to better support creating index tables with delta entries.
 *
 * Revision 1.3  2011/07/27 16:52:59  vizigoth
 * Capable of reading and writing audio essence component files.
 *
 * Revision 1.2  2010/01/21 20:51:31  vizigoth
 * Updates to index table support to the point where index table data can be read from MXF files and stream offsets can be calculated.
 *
 * Revision 1.1  2010/01/19 14:44:24  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
 *
 * Revision 1.5  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/02/13 15:27:46  vizigoth
 * Replaced DataChunk with core Java ByteBuffer.
 *
 * Revision 1.3  2009/02/13 14:27:29  vizigoth
 * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
 *
 * Revision 1.2  2009/02/06 17:01:31  vizigoth
 * Conversion of C headers to fields and stubs.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf.impl;


import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import tv.amwa.maj.exception.BadRateException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.io.mxf.DeltaEntry;
import tv.amwa.maj.io.mxf.IndexEntry;
import tv.amwa.maj.io.mxf.IndexTable;
import tv.amwa.maj.io.mxf.IndexTableSegment;
import tv.amwa.maj.io.mxf.MXFBuilder;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.PrimerPack;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.mxf.UnitType;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>Index tables are represented as index table segments, where a complete index table may
 * comprise one or more index table segments.</p>
 * 
 *
 *
 * @see IndexTable
 */
@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0110, uuid3 = 0x0100,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01 },
		definedName = "IndexTableSegment",
		description = "Index tables are represented as one or more index table segments.",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX,
		symbol = "IndexTableSegment")
public class IndexTableSegmentImpl 
	implements 
		MetadataObject, 
		IndexTableSegment,
		Cloneable {

	private Rational indexEditRate;
	private @PositionType long indexStartPosition;
	private @LengthType long indexDuration;
	private @UInt32 int editUnitByteCount = EDITUNITBYTECOUNT_DEFAULT;
	private @UInt32 int indexSID;
	private @UInt32 int bodySID;
	private @UInt8 byte sliceCount = SLICECOUNT_DEFAULT;
	private @UInt8 Byte posTableCount = null;
	private DeltaEntry[] deltaEntryArray = null;
	private IndexEntry[] indexEntryArray = null;
	private @UInt64 Long extStartOffset = null;
	private @UInt64 Long vbeByteCount = null;
	
	public IndexTableSegmentImpl() { }
	
	public IndexTableSegmentImpl(
			Rational indexEditRate,
			@PositionType long indexStartPosition,
			@LengthType long indexDuration,
			@UInt32 int indexSID,
			@UInt32 int bodySID) 
		throws NullPointerException,
			BadRateException,
			IllegalArgumentException {
		
		setIndexEditRate(indexEditRate);
		setIndexStartPosition(indexStartPosition);
		setIndexDuration(indexDuration);
		setIndexSID(indexSID);
		setBodySID(bodySID);
	}
	
	@MediaProperty(uuid1 = 0x05300406, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 },
			definedName = "Index Edit Rate",
			typeName = "Rational",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3f0b,
			symbol = "IndexEditRate")
	public Rational getIndexEditRate() {
		
		return indexEditRate.clone();
	}
	
	@MediaPropertySetter("Index Edit Rate")
	public void setIndexEditRate(
			Rational indexEditRate)
		throws NullPointerException,
			BadRateException {
		
		if (indexEditRate == null)
			throw new NullPointerException("Cannot set the index edit rate using a null value.");
		
		if (indexEditRate.getDenominator() == 0)
			throw new BadRateException("The given edit rate has a zero demonminator that could cause divide by zero errors.");
		
		this.indexEditRate = indexEditRate.clone();
	}
	
	// Called Indexing start position in RP210, Index Start Position in 377M-2004
	@MediaProperty(uuid1 = 0x07020103, uuid2 = 0x010a, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 },
			definedName = "Index Start Position",
			typeName = "PositionType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3f0c,
			symbol = "IndexStartPosition")
	public @PositionType long getIndexStartPosition() {
		
		return indexStartPosition;
	}
	
	@MediaPropertySetter("Index Start Position")
	public void setIndexStartPosition(
			@PositionType long indexStartPosition) {
		
		this.indexStartPosition = indexStartPosition;
	}
	
	@MediaProperty(uuid1 = 0x07020201, uuid2 = 0x0102, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 },
			definedName = "Index Duration",
			typeName = "LengthType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3f0d,
			symbol = "IndexDuration")
	public @LengthType long getIndexDuration() {
		
		return indexDuration;
	}
	
	@MediaPropertySetter("Index Duration")
	public void setIndexDuration(
			@LengthType long indexDuration) {
		
		this.indexDuration = indexDuration;
	}
	
	@MediaProperty(uuid1 = 0x04060201, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "Edit Unit Byte Count",
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3f05,
			symbol = "EditUnitByteCount")
	public @UInt32 int getEditUnitByteCount() {
		
		return editUnitByteCount;
	}
	
	@MediaPropertySetter("Edit Unit Byte Count")
	public void setEditUnitByteCount(
			@UInt32 int editUnitByteCount) 
		throws IllegalArgumentException {
		
		if (editUnitByteCount < 0)
			throw new IllegalArgumentException("The edit unit byte count cannot be a negative value.");
		
		this.editUnitByteCount = editUnitByteCount;
	}
	
	@MediaProperty(uuid1 = 0x01030405, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "IndexSID",
			aliases = { "Index Stream ID", "IndexStreamID" },
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3f06,
			symbol = "IndexSID")
	public @UInt32 int getIndexSID() {
		
		return indexSID;
	}
	
	@MediaPropertySetter("IndexSID")
	public void setIndexSID(
			@UInt32 int indexSID) 
		throws IllegalArgumentException {
		
		if (indexSID < 0)
			throw new IllegalArgumentException("Cannot set the index stream ID to a negative value.");
		
		this.indexSID = indexSID;
	}
	
	@MediaProperty(uuid1 = 0x01030404, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "BodySID",
			aliases = { "Essence Stream ID", "EssenceStreamID" },
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3f07,
			symbol = "BodySID")
	public int getBodySID() {
		
		return bodySID;
	}
	
	@MediaPropertySetter("BodySID")
	public void setBodySID(
			@UInt32 int bodySID) 
		throws IllegalArgumentException {
		
		if (bodySID < 0)
			throw new IllegalArgumentException("Cannot set the body stream ID to a negative value.");
		
		this.bodySID = bodySID;
	}
	
	@MediaProperty(uuid1 = 0x04040401, uuid2 = 0x0100, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "Slice Count",
			typeName = "UInt8",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3f08,
			symbol = "SliceCount")
	public @UInt8 byte getSliceCount() {
		
		return sliceCount;
	}
	
	@MediaPropertySetter("Slice Count")
	public void setSliceCount(
			@UInt8 byte sliceCount) 
		throws IllegalArgumentException {
		
		if (sliceCount < 0)
			throw new IllegalArgumentException("Cannot set the slice count to a negative value.");
	
		this.sliceCount = sliceCount;
	}
	
	@MediaProperty(uuid1 = 0x04040401, uuid2 = 0x0700, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 },
			definedName = "PosTableCount",
			typeName = "UInt8",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3f0e,
			symbol = "PosTableCount")
	public @UInt8 byte getPosTableCount() 
		throws PropertyNotPresentException {
		
		if (posTableCount == null)
			throw new PropertyNotPresentException("The optional position table count property is not present for this index table segment.");
		
		return posTableCount;
	}
	
	@MediaPropertySetter("PosTableCount")
	public void setPosTableCount(
			@UInt8 Byte posTableCount) 
		throws IllegalArgumentException {
		
		if (posTableCount == null) {
			this.posTableCount = null;
			return;
		}
		
		// TODO check the pos table values do not exceed 127.
		if (posTableCount < 0)
			throw new IllegalArgumentException("Cannot set the position table count to a negative value.");
		
		this.posTableCount = posTableCount;
	}
	
	@MediaProperty(uuid1 = 0x04040401, uuid2 = 0x0600, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 },
			definedName = "Delta Entry Array",
			typeName = "DeltaEntryArray",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3f09,
			symbol = "DeltaEntryArray")
	public DeltaEntry[] getDeltaEntryArray() 
		throws PropertyNotPresentException {
		
		if ((deltaEntryArray == null) || (deltaEntryArray.length == 0))
			throw new PropertyNotPresentException("The optional delta entry array property is not presnt for this index table segment.");
		
		return deltaEntryArray.clone();
	}

	@MediaPropertySetter("Delta Entry Array")
	public void setDeltaEntryArray(
			DeltaEntry[] deltaEntryArray) 
		throws NullPointerException {
		
		if (deltaEntryArray == null) {
			this.deltaEntryArray = null;
			return;
		}
		
		DeltaEntry[] copiedArray = new DeltaEntry[deltaEntryArray.length];
		
		for ( int x = 0 ; x < copiedArray.length ; x++ )
			if (deltaEntryArray[x] == null) 
				throw new NullPointerException("Element " + x + " of the given delta entry array is null.");
			else
				copiedArray[x] = deltaEntryArray[x].clone();
		
		this.deltaEntryArray = copiedArray;
	}
	
	public void setDeltaEntries(
			int[] elementDeltas) 
		throws NullPointerException,
			IllegalArgumentException {
		
		DeltaEntry[] entries = DeltaEntryImpl.makeDeltaEntryArray(elementDeltas);
		setDeltaEntryArray(entries);
	}
	
	@MediaProperty(uuid1 = 0x04040402, uuid2 = 0x0500, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 },
			definedName = "Index Entry Array",
			typeName = "IndexEntryArray",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3f0a,
			symbol = "IndexEntryArray")
	public IndexEntry[] getIndexEntryArray() 
		throws PropertyNotPresentException {
		
		if ((indexEntryArray == null) || (indexEntryArray.length == 0))
			throw new PropertyNotPresentException("The optional index entry array property is not present for this index table segment.");
		
		return indexEntryArray.clone();
	}

	@MediaPropertySetter("Index Entry Array")
	public void setIndexEntryArray(
			IndexEntry[] indexEntryArray) 
		throws NullPointerException {
		
		if (indexEntryArray == null) {
			this.indexEntryArray = null;
			return;
		}
		
		for ( IndexEntry indexEntry : indexEntryArray )
			try {
			
				indexEntry.resolveBytes(this);
			}
			catch (EndOfDataException ede) {
				System.err.println("Warning: Error resolving bytes for index table segment for index " + indexSID + ".");
			}
		
		IndexEntry[] copiedArray = new IndexEntry[indexEntryArray.length];
		
		for ( int x = 0 ; x < copiedArray.length ; x++ )
			if (indexEntryArray[x] == null) 
				throw new NullPointerException("Element " + x + " of the given delta entry array is null.");
			else
				copiedArray[x] = indexEntryArray[x].clone();
		
		this.indexEntryArray = copiedArray;
	}

	@MediaProperty(uuid1 = 0x04060204, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0a },
			definedName = "ExtStartOffset",
			typeName = "UInt64",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3f0f,
			symbol = "ExtStartOffset")
	public @UInt64 long getExtStartOffset()
		throws PropertyNotPresentException {
		
		if (extStartOffset == 0l)
			throw new PropertyNotPresentException("The optional external start offset property is not present for this index table segment.");
		
		return extStartOffset;
	}
	
	@MediaPropertySetter("ExtStartOffset")
	public void setExtStartOffset(
			@UInt64 Long extStartOffset)
		throws IllegalArgumentException {
		
		if (extStartOffset == null) {
 			this.extStartOffset = null;
 			return;
		}
		
		if (extStartOffset < 0l)
			throw new IllegalArgumentException("The external start offset property cannot be negative.");
		
		this.extStartOffset = extStartOffset;
	}
	
	@MediaProperty(uuid1 = 0x04060205, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0a },
			definedName = "VBEByteCount",
			typeName = "UInt64",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3f10,
			symbol = "VBEByteCount")
	public @UInt64 long getVBEByteCount()
		throws PropertyNotPresentException {
		
		if (vbeByteCount == null)
			throw new PropertyNotPresentException("The optional VBE byte count property is not present for this index table segment.");
		
		return vbeByteCount;
	}
	
	@MediaPropertySetter("VBEByteCount")
	public void setVBEByteCount(
			@UInt64 Long vbeByteCount)
		throws IllegalArgumentException {
		
		if (vbeByteCount == null) {
			this.vbeByteCount = null;
			return;
		}
		
		if (vbeByteCount < 0l)
			throw new IllegalArgumentException("The optional VBE byte count property cannot be negative.");
		
		this.vbeByteCount = vbeByteCount;
	}
	
	private final static PrimerPack indexPrimer = IndexTable.indexPrimer;
	private final static Map<AUIDImpl, MetadataObject> referenceTable = new HashMap<AUIDImpl, MetadataObject>();
	private final static List<ResolutionEntry> resolutions = new Vector<ResolutionEntry>();
	
	public final static IndexTableSegment createFromBuffer(
			ByteBuffer buffer)
		throws NullPointerException,
			BufferUnderflowException {
		
		if (buffer == null)
			throw new NullPointerException("Cannot create an index table from a null value.");
				
		int preserveLimit = buffer.limit();

		IndexTableSegment nextSegment = null;
		
		MXFBuilder.skipKLVFill(buffer);

		UL key = MXFBuilder.readKey(buffer);
		long length = MXFBuilder.readBERLength(buffer);
		int limit = (int) (buffer.position() + length);
			
		if (!key.equals(IndexTableSegment.key)) {
			buffer.position(limit);
			buffer.limit(preserveLimit);
			// FIXME fell down this hole - do better! Problem here is expecting key and length in the buffer
			return null;
		}
			
		buffer.limit(limit);
		try {
			nextSegment =
				(IndexTableSegment) MXFBuilder.readLocalSet(key, buffer, indexPrimer, referenceTable, resolutions);
		} 
		catch (Exception e) {
			System.err.println(e.getClass().getName() + " thrown when trying to read an index table segment: " + e.getMessage());
		}
		finally {
			buffer.limit(preserveLimit);
			buffer.position(limit);
		}
		
		MXFBuilder.skipKLVFill(buffer);
		
		referenceTable.clear();
		resolutions.clear();
		
		return nextSegment;
	}
	
	public int hashCode() {
		
		return MediaEngine.hashCode(this);
	}

	public String toString() {
		
		return MediaEngine.toString(this);
	}
	
	public boolean equals(
			Object o) {
		
		return MediaEngine.equals(this, o);
	}
	
	public UnitType getUnitType() {
		
		return UnitType.IndexTableSegment;
	}
	
	public IndexTableSegmentImpl clone() {
		
		try {
			return (IndexTableSegmentImpl) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Implements cloneable so should not happen
			throw new InternalError(cnse.getMessage());
		}
	}
}
