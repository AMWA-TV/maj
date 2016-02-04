package tv.amwa.maj.io.mxf;

import tv.amwa.maj.exception.BadRateException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>An index table segment makes up part or all of an {@linkplain IndexTable index table}.
 * A index segment provides details of stream offsets that map an edit unit to its position
 * within an essence container.</p>
 * 
 *
 *
 * @see IndexEntry
 * @see DeltaEntry
 * @see IndexTable
 * @see Partition#readIndexTableSegment()
 */
public interface IndexTableSegment 
	extends Cloneable,
		MetadataObject,
		MXFUnit {

	/**
	 * <p>Default value for the edit unit byte count value for the index table segment,
	 * which when set to zero as in this default indicates that the 
	 * {@linkplain #getIndexEntryArray() index entry array} elements should be used to 
	 * determine stream offsets.</p>
	 * 
	 * @see #getEditUnitByteCount()
	 * @see #setEditUnitByteCount(int)
	 */
	public final static int EDITUNITBYTECOUNT_DEFAULT = 0;
	
	/**
	 * <p>Default value for the number of slices dividing up the elements of a single edit
	 * unit.</p>
	 * 
	 * @see #getSliceCount()
	 * @see #setSliceCount(byte)
	 * @see IndexEntry#getSliceOffset()
	 */
	public final static int SLICECOUNT_DEFAULT = 0;

	/**
	 * <p>Default value for the external start offset property that gives the the byte offset to 
	 * the first essence data in a CBE (Constant Bytes per Element) essence stream.</p>
	 * 
	 * @see #getExtStartOffset()
	 * @see #setExtStartOffset(java.lang.Long)
	 */
	public final static long EXTSTARTOFFSET_DEFAULT = 0l;
	
	/**
	 * <p>Default value for the VBE byte count property that gives the byte offset to the end of the 
	 * final essence unit in a VBE (Variable Bytes per Element) essence stream.</p>
	 * 
	 * @see #getVBEByteCount()
	 * @see #setVBEByteCount(java.lang.Long)
	 */
	public final static long VBEBYTECOUNT_DEFAULT = 0l;
	
	/**
	 * <p>Universal label identifying an index table segment.</p>
	 */
	public final static UL key = new AUIDImpl(
			0x0d010201, (short) 0x0110, (short) 0x0100,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x53, 0x01, 0x01});
	
	/**
	 * <p>Returns the edit rate of the master track in the indexed essence container, measured in
	 * hertz. If the container has only one track, this is the edit rate of that track. If the
	 * essence container interleaves video and audio, it is standard practice to use the picture
	 * track to provide the index edit rate.</p>
	 * 
	 * @return Edit rate of the master track in the essence container, measured in
	 * hertz.
	 */
	public Rational getIndexEditRate();

	/**
	 * <p>Sets the edit rate of the master track in the indexed essence container, measured in
	 * hertz. If the container has only one track, this is the edit rate of that track. If the
	 * essence container interleaves video and audio, it is standard practice to use the picture
	 * track to provide the index edit rate.</p>
	 * 
	 * @param indexEditRate Edit rate of the master track in the essence container, measured in
	 * hertz.
	 * 
	 * @throws NullPointerException Cannot set the index edit rate with a <code>null</code> value.
	 * @throws BadRateException The given rate is not acceptable because it is negative or has 
	 * a zero denominator.
	 */
	public void setIndexEditRate(
			Rational indexEditRate)
		throws NullPointerException,
			BadRateException;

	// Called Indexing start position in RP210, Index Start Position in 377M-2004
	/**
	 * <p>Returns the first editable unit indexed by this index table segment measured 
	 * in file package edit units.</p>
	 * 
	 * @return First editable unit indexed by this index table segment,
	 */
	public @PositionType long getIndexStartPosition();

	/**
	 * <p>Sets the first editable unit indexed by this index table segment measured 
	 * in file package edit units.</p>
	 * 
	 * @param indexStartPosition First editable unit indexed by this index table segment.
	 */
	public void setIndexStartPosition(
			@PositionType long indexStartPosition);

	/**
	 * <p>Returns the time duration of this table segment measured in number of edit units of 
	 * the referenced package.</p>
	 * 
	 * @return Time duration of this table segment measured in number of edit units.
	 */
	public @LengthType long getIndexDuration();

	/**
	 * <p>Sets the time duration of this table segment measured in number of edit units of 
	 * the referenced package.</p>
	 * 
	 * @param indexDuration Time duration of this table segment measured in number of edit units.
	 */
	public void setIndexDuration(
			@LengthType long indexDuration);

	/**
	 * <p>Returns the byte count of each and every edit unit in the indexed essence container, 
	 * which is greater than zero when this is a fixed size for all edit units. The default value 
	 * of&nbsp;0 defines that the byte count for each edit unit may be variables and shall be 
	 * looked up in the {@linkplain #getIndexEntryArray() index entry array}.</p>
	 * 
	 * @return Byte count of each and every edit unit in the essence container.
	 * 
	 * @see #EDITUNITBYTECOUNT_DEFAULT
	 */
	public @UInt32 int getEditUnitByteCount();

	/**
	 * <p>Sets the byte count of each and every edit unit in the indexed essence container, 
	 * which is greater than zero when this is a fixed size for all edit units. The default value 
	 * of&nbsp;0 defines that the byte count for each edit unit may be variables and shall be 
	 * looked up in the {@linkplain #getIndexEntryArray() index entry array}.</p>
	 * 
	 * @param editUnitByteCount Byte count of each and every edit unit in the essence container.
	 * 
	 * @throws IllegalArgumentException The edit unit byte count cannot be a negative value.
	 * 
	 * @see #EDITUNITBYTECOUNT_DEFAULT
	 */
	public void setEditUnitByteCount(
			@UInt32 int editUnitByteCount)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the stream identifier for this index table. A {@linkplain Partition partition} can
	 * only hold one index table. All index table segments within a partition and making up the same
	 * {@linkplain IndexTable index table} must have the same identifier. The identifier is unique within 
	 * the scope of an MXF file.</p>
	 * 
	 * @return Stream identifier for this index table.
	 */
	public @UInt32 int getIndexSID();

	/**
	 * <p>Sets the stream identifier for this index table. A {@linkplain Partition partition} can
	 * only hold one index table. All index table segments within a partition and making up the same
	 * {@linkplain IndexTable index table} must have the same identifier. The identifier is unique within 
	 * the scope of an MXF file.</p>
	 * 
	 * @param indexSID Stream identifier for this index table.
	 * 
	 * @throws IllegalArgumentException A stream identifier cannot be a negative value.
	 */
	public void setIndexSID(
			@UInt32 int indexSID)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the stream identifier of the indexed essence container. The indexed container may
	 * be in the same {@linkplain Partition partition} as the index or a different one, but the 
	 * identifier is always unique within the scope of an MXF file.</p>
	 * 
	 * @return Stream identifier of the indexed essence container.
	 */
	public int getBodySID();

	/**
	 * <p>Sets the stream identifier of the indexed essence container. The indexed container may
	 * be in the same {@linkplain Partition partition} as the index or a different one, but the 
	 * identifier is always unique within the scope of an MXF file.</p>
	 * 
	 * @param bodySID Stream identifier of the indexed essence container.
	 * 
	 * @throws IllegalArgumentException Cannot set the stream identifier to a negative value.
	 */
	public void setBodySID(
			@UInt32 int bodySID) 
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of slices used to group the elements within an edit unit, minus one as
	 * only slice offsets are recorded in an array. A new
	 * slice is required after any variable bit rate element in an interleaved stream of essence and
	 * the offset to the essence is recorded in the edit unit's {@linkplain IndexEntry index entry}.</p>
	 * 
	 * <p>The default value for this property is {@value #SLICECOUNT_DEFAULT}.</p>
	 * 
	 * <p>Note that the slice count is required to {@linkplain IndexEntry#resolveBytes(IndexTableSegment) resolve 
	 * bytes} to slice offsets for an index entry.</p>
	 * 
	 * @return Number of slices used to group the elements within an edit unit, minus one.
	 * 
	 * @see #SLICECOUNT_DEFAULT
	 * @see IndexEntry#getSliceOffset()
	 * @see IndexEntry#resolveBytes(IndexTableSegment)
	 */
	public @UInt8 byte getSliceCount();

	/**
	 * <p>Sets the number of slices used to group the elements within an edit unit, minus one as
	 * only slice offsets are recorded in an array. A new
	 * slice is required after any variable bit rate element in an interleaved stream of essence and
	 * the offset to the essence is recorded in the edit unit's {@linkplain IndexEntry index entry}.</p>
	 * 
	 * <p>The default value for this property is {@value #SLICECOUNT_DEFAULT}.</p>
	 * 
	 * <p>Note that the slice count is required to {@linkplain IndexEntry#resolveBytes(IndexTableSegment) resolve 
	 * bytes} to slice offsets for an index entry.</p>
	 * 
	 * @param sliceCount Number of slices used to group the elements within an edit unit, minus one.
	 * 
	 * @throws IllegalArgumentException Cannot set the slice count to a negative value.
	 * 
	 * @see #SLICECOUNT_DEFAULT
	 * @see IndexEntry#getSliceOffset()
	 * @see IndexEntry#resolveBytes(IndexTableSegment)
	 */
	public void setSliceCount(
			@UInt8 byte sliceCount)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of entries in the position offsets table per edit unit, minus one as 
	 * only offsets are recorded in the array. Position table offsets record the temporal fractional offset
	 * from the edit unit of the indexes master track, normally the picture track, and the start of
	 * other interleaved elements, such as sound tracks. This is an optional property that is omitted when
	 * all elements are temporally aligned or streams are not interleaved.</p>
	 * 
	 * <p>Note that the slice count is required to {@linkplain IndexEntry#resolveBytes(IndexTableSegment) resolve 
	 * bytes} to slice offsets for an index entry.</p>
	 * 
	 * @return Number of slices used to group the elements within an edit unit, minus one.
	 * 
	 * @throws PropertyNotPresentException The optional position table count property is not present for
	 * this index table segment.
	 * 
	 * @see IndexEntry#getPosTable()
	 * @see IndexEntry#resolveBytes(IndexTableSegment)
	 */
	public @UInt8 byte getPosTableCount() 
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the number of entries in the position offsets table per edit unit, minus one as 
	 * only offsets are recorded in the array. Position table offsets record the temporal fractional offset
	 * from the edit unit of the indexes master track, normally the picture track, and the start of
	 * other interleaved elements, such as sound tracks. This is an optional property that is omitted when
	 * all elements are temporally aligned or streams are not interleaved.</p>
	 * 
	 * <p>Note that the slice count is required to {@linkplain IndexEntry#resolveBytes(IndexTableSegment) resolve 
	 * bytes} to slice offsets for an index entry.</p>
	 * 
	 * @param posTableCount Number of slices used to group the elements within an edit unit, minus one.
	 * 
	 * @throws IllegalArgumentException Cannot set the position table count to a negative value.
	 * 
	 * @see IndexEntry#getPosTable()
	 * @see IndexEntry#resolveBytes(IndexTableSegment)
	 */
	public void setPosTableCount(
			@UInt8 Byte posTableCount)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the array of {@linkplain DeltaEntry delta entries} for this index table segment that describe
	 * the layout of each element stored within an edit unit. This is an optional property and, when omitted,
	 * indicates only one element per edit unit.</p>
	 * 
	 * @return Array of {@linkplain DeltaEntry delta entries} for this index table segment.
	 * 
	 * @throws PropertyNotPresentException The optional delta entry array is not present for this index table
	 * segment.
	 * 
	 * @see #getSliceCount()
	 * @see TypeDefinitions#DeltaEntry
	 * @see TypeDefinitions#DeltaEntryArray
	 */
	public DeltaEntry[] getDeltaEntryArray() 
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the array of {@linkplain DeltaEntry delta entries} for this index table segment that describe
	 * the layout of each element stored within an edit unit. Set this optional property to <code>null</code>
	 * to omit it, indicating that only one element exists per edit unit.</p> 
	 * 
	 * @param deltaEntryArray Array of {@linkplain DeltaEntry delta entries} for this index table segment.
	 * 
	 * @throws NullPointerException One or more of the given delta elements is <code>null</code>.
	 * 
	 * @see #setDeltaEntries(int[])
	 * @see #setSliceCount(byte)
	 * @see MXFFactory#makeDeltaEntry(byte, byte, int)
	 * @see MXFFactory#makeDeltaEntry(int)
	 */
	public void setDeltaEntryArray(
			DeltaEntry[] deltaEntryArray)
		throws NullPointerException;

	/**
	 * <p>Sets the array of {@linkplain DeltaEntry delta entries} for this index table segment using
	 * element delta offsets only. The delta entries describe the layout of each element stored within 
	 * an edit unit. Default values are set for the {@linkplain DeltaEntry#POSTABLEINDEX_DEFAULT position 
	 * table index} and {@linkplain DeltaEntry#SLICE_DEFAULT slice} components of each entry.</p>
	 *   
	 * @param elementDeltas List of element delta offsets for the elements stored within each frame.
	 * 
	 * @throws NullPointerException Cannot set the element deltas using a <code>null</code> array.
	 * @throws IllegalArgumentException Offsets cannot be negative values.
	 * 
	 * @see #setDeltaEntryArray(DeltaEntry[])
	 */
	public void setDeltaEntries(
			int[] elementDeltas) 
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Returns the array of {@linkplain IndexEntry index entries} for this index table segment that describes the
	 * stream offset of each edit unit. The index entry array is required when variable bit rate coding is in use. 
	 * This is an optional property that is omitted when each edit unit can be found using a multiple of the
	 * {@linkplain #getEditUnitByteCount() edit unit byte count}.</p>
	 * 
	 * @return Array of {@linkplain IndexEntry index entries} for this index table segment.
	 * 
	 * @throws PropertyNotPresentException The optional index entry array property is not present for this index
	 * table segment.
	 * 
	 * @see #getEditUnitByteCount()
	 * @see #EDITUNITBYTECOUNT_DEFAULT
	 * @see TypeDefinitions#IndexEntry
	 * @see TypeDefinitions#IndexEntryArray
	 */
	public IndexEntry[] getIndexEntryArray() 
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the array of {@linkplain IndexEntry index entries} for this index table segment that describes the
	 * stream offset of each edit unit. The index entry array is required when variable bit rate coding is in use. 
	 * Set this optional property to <code>null</code> to omit it, which should be the case when each edit unit 
	 * can be found using a multiple of the {@linkplain #getEditUnitByteCount() edit unit byte count}.</p>
	 * 
	 * @param indexEntryArray Array of {@linkplain IndexEntry index entries} for this index table segment.
	 * 
	 * @throws NullPointerException One or more of the given index entries in the given array is <code>null</code>.
	 * 
	 * @see #setEditUnitByteCount(int)
	 * @see #EDITUNITBYTECOUNT_DEFAULT
	 */
	public void setIndexEntryArray(
			IndexEntry[] indexEntryArray)
		throws NullPointerException;
	
	/**
	 * <p>Get the external start offset property of this index table segment that the byte offset to the first
	 * essence data in a CBE (Constant Bytes per Element) essence stream. This is an optional property.</p>
	 * 
	 * @return External start offset of this index table segment.
	 * 
	 * @throw PropertyNotPresentException The optional external start offset property is not present.
	 * 
	 * @see #EXTSTARTOFFSET_DEFAULT
	 */
	public @UInt64 long getExtStartOffset()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the external start offset property of this index table segment that the byte offset to the first
	 * essence data in a CBE (Constant Bytes per Element) essence stream. Set this property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * <p>The default value for this property is {@value #EXTSTARTOFFSET_DEFAULT}.
	 * 
	 * @param extStartOffset External start offset of this index table segment.
	 * 
	 * @throws IllegalArgumentException The external start offset property cannot be negative.
	 * 
	 * @see #EXTSTARTOFFSET_DEFAULT
	 */
	public void setExtStartOffset(
			@UInt64 Long extStartOffset)
		throws IllegalArgumentException;
	
	/**
	 * <p>Get the VBE byte count value that gives the byte offset to the end of the final essence unit in a 
	 * VBE (Variable Bytes per Element) essence stream. This value can be used to calculate the size of the 
	 * final essence unit. This is an optional property.</p>
	 * 
	 * <p>The default value for this property is {@value #VBEBYTECOUNT_DEFAULT}.</p>
	 * 
	 * @return VBE byte count for this index table segment.
	 * 
	 * @throws PropertyNotPresentException The optional VBE byte count property is not present. 
	 * 
	 * @see #VBEBYTECOUNT_DEFAULT
	 */
	public @UInt64 long getVBEByteCount()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the VBE byte count value that gives the byte offset to the end of the final essence unit in a 
	 * VBE (Variable Bytes per Element) essence stream. This value can be used to calculate the size of the 
	 * final essence unit. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * <p>The default value for this property is {@value #VBEBYTECOUNT_DEFAULT}.</p>
	 * 
	 * @param vbeByteCount VBE byte count for this index table segment.
	 * 
	 * @throws IllegalArgumentException The VBE byte count property cannot be negative.
	 * 
	 * @see #VBEBYTECOUNT_DEFAULT
	 */
	public void setVBEByteCount(
			@UInt64 Long vbeByteCount)
		throws IllegalArgumentException;
	
	/**
	 * <p>Creates a cloned copy of this index table segment.</p>
	 * 
	 * @return Cloned copy of this index table segment.
	 */
	public IndexTableSegment clone();
}