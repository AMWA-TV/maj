package tv.amwa.maj.io.mxf;

import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.mxf.impl.LocalTagEntryImpl;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionRecord;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.meta.impl.TypeDefinitionRecordImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionSetImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionStrongObjectReferenceImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionVariableArrayImpl;
import tv.amwa.maj.record.impl.AUIDImpl;

// TODO find proper keys for these types

/**
 * <p>Definitions of data types used specifically in the serialization of AAF data to and from
 * KLV format. The additional types represent values for {@linkplain IndexTable index tables}, 
 * {@linkplain PrimerPack primer packs} and {@linkplain RandomIndexPack random index packs}.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public interface TypeDefinitions 
	extends tv.amwa.maj.industry.TypeDefinitions {

	/**
	 * <p>Defines the type for a value that describes the layout of an element stored within an 
	 * edit unit, including byte offset.</p>
	 * 
	 * @see #DeltaEntryArray
	 * @see DeltaEntry
	 * @see IndexTableSegment#getDeltaEntryArray()
	 */
	public final static TypeDefinitionRecord DeltaEntry = new TypeDefinitionRecordImpl(
				new AUIDImpl(), 
				"DeltaEntry", 
				new String[] {
					"PosTableIndex",
					"Slice",
					"ElementDelta"
				}, 
				new TypeDefinition[] {
						Int8,
						UInt8,
						UInt32
				}, 
				DeltaEntry.class);
	
	/**
	 * <p>Defines the type for a value that is an array of {@linkplain #DeltaEntry delta entry} elements.</p>
	 * 
	 * @see #DeltaEntry
	 * @see DeltaEntry
	 * @see IndexTableSegment#getDeltaEntryArray()
	 */
	public final static TypeDefinitionVariableArray DeltaEntryArray = new TypeDefinitionVariableArrayImpl(
			new AUIDImpl(), 
			"DeltaEntryArray", 
			DeltaEntry);

//	public final static TypeDefinitionRename EditUnitFlag = new TypeDefinitionRenameImpl(
//			new AUIDImpl(),
//			"EditUnitFlag",
//			UInt8);
//	
	/**
	 * <p>Defines the type for a value that represents an element of an {@linkplain IndexTable index table}
	 * that provides stream offsets and other information about an edit unit on an incrementing timeline.</p>
	 * 
	 * @see #IndexEntryArray
	 * @see IndexEntry
	 * @see IndexTableSegment#getIndexEntryArray()
	 */
	public final static TypeDefinitionRecord IndexEntry = new TypeDefinitionRecordImpl(
			new AUIDImpl(),
			"IndexEntry",
			new String[] {
				"TemporalOffset",
				"KeyFrameOffset",
				"Flags",
				"StreamOffset" // Missing out SliceOffset and PosTable as not record supported types.
			},
			new TypeDefinition[] {
				Int8,
				Int8,
				UInt8,
				UInt64
			},
			IndexEntry.class);
	
	/**
	 * <p>Defines the type for a value that represents an array of {@linkplain #IndexEntry entries} 
	 * in an {@linkplain IndexTable index table}.</p>
	 * 
	 * @see #IndexEntry
	 * @see IndexEntry
	 * @see IndexTableSegment#getIndexEntryArray()
	 */
	public final static TypeDefinitionVariableArray IndexEntryArray = new TypeDefinitionVariableArrayImpl(
			new AUIDImpl(),
			"IndexEntryArray",
			IndexEntry);
	
	/**
	 * <p>Defines the type for a value that represents an entry in a 
	 * {@linkplain RandomIndexPack random index pack (RIP)} that provides byte offset
	 * details for a partition.</p>
	 * 
	 * @see #RandomIndexItemArray
	 * @see RandomIndexItem
	 * @see RandomIndexPack#getPartitionIndex()
	 */
	public final static TypeDefinitionRecord RandomIndexItem = new TypeDefinitionRecordImpl(
			new AUIDImpl(),
			"RandomIndexItem",
			new String[] {
				"BodySID",
				"ByteOffset"
			},
			new TypeDefinition[] {
				UInt32,
				UInt64
			},
			RandomIndexItem.class);
			
	/**
	 * <p>Defines the type for a value that represents an array of {@linkplain #RandomIndexItem random index items}
	 * that provide a table of byte offsets to partitions for a {@linkplain RandomIndexPack random index pack} 
	 * in an MXF file.</p>
	 * 
	 * @see #RandomIndexItem
	 * @see RandomIndexItem
	 * @see RandomIndexPack#getPartitionIndex()
	 */
	public final static TypeDefinitionVariableArray RandomIndexItemArray = new TypeDefinitionVariableArrayImpl(
			new AUIDImpl(),
			"RandomIndexItemArray",
			RandomIndexItem);
	
	/**
	 * <p>Defines the type of a value that is a reference to a {@linkplain LocalTagEntry local tag
	 * entry} in a {@linkplain PrimerPack primer pack} that contains a single tag-to-key mapping. 
	 * Two-byte to sixteen byte mappings are used to reduce the encoding footprint for a local
	 * set.</p>
	 * 
	 * @see #LocalTagEntryBatch
	 * @see LocalTagEntry
	 * @see PrimerPack#getLocalTagEntryBatch()
	 * @see MXFBuilder#readLocalSet(UL, java.nio.ByteBuffer, PrimerPack, java.util.Map, java.util.List)
	 */
	public final static TypeDefinitionStrongObjectReference LocalTagEntryReference =
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x0f721102, (short) 0x0200, (short) 0x0000, 
						new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 }),
				"StrongReference to LocalTagEntry",
				Warehouse.lookForClass(LocalTagEntryImpl.class));
	
	/**
	 * <p>Defines the type of a value that is a set of references to {@linkplain LocalTagEntry local tag
	 * entries} for a {@linkplain PrimerPack primer pack}, where the set contains the lookup table of
	 * tag-to-key mappings. Two-byte to sixteen byte mappings are used to reduce the encoding footprint for a local
	 * set.</p>
	 * 
	 * @see #LocalTagEntryReference
	 * @see PrimerPack#getLocalTagEntryBatch()
	 * @see MXFBuilder#readLocalSet(UL, java.nio.ByteBuffer, PrimerPack, java.util.Map, java.util.List)
	 */
	public final static TypeDefinitionSet LocalTagEntryBatch = new TypeDefinitionSetImpl(
			new AUIDImpl(0x0f721102, (short) 0x0300, (short) 0x0000, 
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 }),
			"LocalTagEntryBatch",
			LocalTagEntryReference);
	
}
