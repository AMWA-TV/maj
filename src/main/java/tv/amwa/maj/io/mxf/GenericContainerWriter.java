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
//
///*
// * $Log: GenericContainerWriter.java,v $
// * Revision 1.5  2010/01/19 14:44:22  vizigoth
// * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
// *
// * Revision 1.4  2009/03/30 09:05:00  vizigoth
// * Refactor to use SMPTE harmonized names and add early KLV file support.
// *
// * Revision 1.3  2009/02/13 15:27:46  vizigoth
// * Replaced DataChunk with core Java ByteBuffer.
// *
// * Revision 1.2  2009/02/13 14:27:29  vizigoth
// * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
// *
// * Revision 1.1  2009/02/03 16:15:19  vizigoth
// * Intiial creation and copy over of header information from mxflib.
// *
// *
// */
//
//package tv.amwa.maj.io.mxf;
//
//import java.nio.ByteBuffer;
//import java.util.Map;
//
//import tv.amwa.maj.integer.Int32;
//import tv.amwa.maj.integer.Int64;
//import tv.amwa.maj.integer.UInt32;
//import tv.amwa.maj.integer.UInt64;
//import tv.amwa.maj.integer.UInt8;
//import tv.amwa.maj.integer.UInt8Array;
//import tv.amwa.maj.io.mxf.impl.MXFFileImpl;
//
//// Note: CP is an abbreviation for content package
//
///**
// * <p>Manages the writing of generic container essence.</p>
// *
// *
// *
// */
//public class GenericContainerWriter {
//
//	/**
//	 * <p>Describes a stream of data within a generic container.</p>
//	 *
//	 *
//	 *
//	 */
//	class GenericContainerStreamData {
//
////		DataChunkPtr SpecifiedKey;			//!< Non standard key to use, or NULL to use a standard key
//
//		public ByteBuffer specifiedKey;
//
////		bool NonGC;							//!< True if the track number bytes are <b>not</b> to be set automatically
//
//		public boolean nonGenericContainer;
//
////		UInt8 Type;							//!< Item type
//
//		public @UInt8 byte type;
//
////		UInt8 SchemeOrCount;				//!< Scheme if system or element count if essence
//
//		public @UInt8 byte schemeOrCount;
//
////		UInt8 Element;						//!< Element identifier or type
//
//		public @UInt8 byte element;
//
////		UInt8 SubOrNumber;					//!< Sub ID if system or element number if essence
//
//		public @UInt8 byte subOrNumber;
//
////		UInt8 RegDes;						//!< The registry designator if this is a system item
//
//		public @UInt8 byte registryDesignator;
//
////		UInt8 RegVer;						//!< The registry version number for the item key
//
//		public @UInt8 byte registryVersion;
//
////		int LenSize;						//!< The KLV length size to use for this stream (0 for auto)
//
//		public int klvLengthSize;
//
////		IndexManagerPtr IndexMan;			//!< If indexing this stream a pointer to the index manager, else NULL
//
//		public IndexManager indexManager;
//
////		int IndexSubStream;					//!< If indexing this stream the sub stream number, else undefined
//
//		public int indexSubStream;
//
////		bool IndexFiller;					//!< If indexing this stream true if filler <b>preceeding</b> this stream is to be indexed, else undefined
//
//		public boolean indexFiller;
//
////		bool IndexClip;						//!< True if indexing clip-wrapped essence
//
//		public boolean indexClip;
//
////		bool CountFixed;					//!< True once the essence element count has been fixed
////
////											/*!< The count is fixed the first time either a key is written
////
////											 *   or a track number is reported */
//
//		public boolean countFixed;
//
////		UInt32 WriteOrder;					//!< The (default) write order for this stream
////
////											/*!< Elements with a lower WriteOrder are written first when the
////
////											 *   content package is written */
//		public @UInt32 int writeOrder;
//	}
//
//	class WriteBlock {
//
////		UInt64 Size;				//!< Number of bytes of data to write
//
//		public @UInt64 long size;
//
////		UInt8 *Buffer;				//!< Pointer to bytes to write
//
//		public @UInt8Array byte[] buffer;
//
////		EssenceSourcePtr Source;	//!< Smart pointer to an EssenceSource object or NULL
//
//		public EssenceSource source;
//
////		KLVObjectPtr KLVSource;		//!< Pointer to a KLVObject as source - or NULL
//
//		public KLVObject klvSource;
//
////		int LenSize;				//!< The KLV length size to use for this item (0 for auto)
//
//		public int lengthSize;
//
////		IndexManagerPtr IndexMan;	//!< Index manager that wants to know about this data
//
//		public IndexManager indexManager;
//
////		int IndexSubStream;			//!< Sub-stream ID of data for indexing
//
//		public int indexSubStream;
//
////		bool IndexFiller;			//!< If true filler will also be indexed with SubStream -1
//
//		public boolean indexFiller;
//
////		bool IndexClip;				//!< True if indexing clip-wrapped essence
//
//		public boolean indexClip;
//
////		bool WriteEncrypted;		//!< True if the data is to be written as encrypted data (via a KLVEObject)
//
//		public boolean writeEncrypted;
//
////		bool FastClipWrap;			//!< True if this KLV is to be "FastClipWrapped"
//
//		public boolean fastClipWrap;
//	}
//
////	protected:
////
////		MXFFilePtr LinkedFile;				//!< File that will be written to
//
//	private MXFFileImpl linkedFile;
//
////		UInt32 TheBodySID;					//!< Body SID for this Essence Container
////
//	private @UInt32 int bodySID;
////
////		int	StreamTableSize;				//!< Size of StreamTable
//
////	private int streamTableSize;  Using Java array instead.
//
////		int	StreamCount;					//!< Number of entries in use in StreamTable
////
//	private int streamCount;
////
////		int StreamBase;						//!< Base of all stream numbers in keys
////
//	private int streamBase;
////
////		GCStreamData *StreamTable;			//!< Table of data for streams for this GC
////
//	private GenericContainerStreamData[] streamTable;
////
////		UInt32 KAGSize;						//!< KAGSize for this Essence Container
//
//	private @UInt32 int kagSize;
//
////		bool ForceFillerBER4;				//!< True if filler items must have BER lengths forced to 4-byte BER
////
//	private boolean forceFillerBER4;
////
////		Int32 NextWriteOrder;				//!< The "WriteOrder" to use for the next auto "SetWriteOrder()"
////
//	private @Int32 int nextWriteOrder;
////
////		Position IndexEditUnit;				//!< Edit unit of the current CP for use if indexing
////
////											/*!< This property starts at zero and is incremented with each CP written, however the value
////
////											 *   can be changed by calling SetIndexEditUnit() before calling StartNewCP()
////
////											 */
////
//	private @MXFPosition long indexEditUnit;
////
////		UInt64 StreamOffset;				//!< Current stream offset within this essence container
////
//	private @UInt64 long streamOffset;
////
////		//! Map of all used write orders to stream ID - used to ensure no duplicates
////
////		std::map<UInt32, GCStreamID> WriteOrderMap;
////
//	private Map<Integer, Integer> writeOrderMap; // <UInt32, GCStreamID>
////
////	public:
////
////		//! Constructor
////
////		GCWriter(MXFFilePtr File, UInt32 BodySID = 0, int Base = 0);
////
//	public GenericContainerWriter(
//			MXFFileImpl file,
//			int bodySID,
//			int base) {
//
//		// TODO
//	}
//
//	public GenericContainerWriter(
//			MXFFileImpl file) {
//
//		// TODO same as above with bodySID = 0 and base = 0
//	}
//
////
////		//! Set the KAG for this Essence Container
////
////		void SetKAG(UInt32 KAG, bool ForceBER4 = false) { KAGSize = KAG; ForceFillerBER4 = ForceBER4; };
////
//	public void setKAG(
//			@UInt32 int kag,
//			boolean forceFillerBER4)
//		throws IllegalArgumentException {
//
//		if (kag < 0)
//			throw new IllegalArgumentException("Cannot set the KAG size to a negative value.");
//
//		this.kagSize = kag;
//		this.forceFillerBER4 = forceFillerBER4;
//	}
////
////		//! Get the current KAGSize
////
////		UInt32 GetKAG(void) { return KAGSize; }
////
//	public @UInt32 int getKAG() {
//
//		return kagSize;
//	}
////
////		//! Define a new non-CP system element for this container
////
////		GCStreamID AddSystemElement(unsigned int RegistryDesignator, unsigned int SchemeID, unsigned int ElementID, unsigned int SubID = 0)	{ return AddSystemElement(false, RegistryDesignator, SchemeID, ElementID, SubID); }
////
//	public @GCStreamID int addSystemElement(
//			@UInt32 int registryDesignator,
//			@UInt32 int schemeID,
//			@UInt32 int elementID,
//			@UInt32 int subID) {
//
//		return addSystemElement(false, registryDesignator, schemeID, elementID, subID);
//	}
//
//	public @GCStreamID int addSystemElement(
//			@UInt32 int registryDesignator,
//			@UInt32 int schemeID,
//			@UInt32 int elementID) {
//
//		return addSystemElement(false, registryDesignator, schemeID, elementID, 0);
//	}
//
//
////
////		//! Define a new CP-compatible system element for this container
////
////		GCStreamID AddCPSystemElement(unsigned int RegistryDesignator, unsigned int SchemeID, unsigned int ElementID, unsigned int SubID = 0)	{ return AddSystemElement(true, RegistryDesignator, SchemeID, ElementID, SubID); }
////
//	public @GCStreamID int addCPSystemElement(
//			@UInt32 int registryDesignator,
//			@UInt32 int schemeID,
//			@UInt32 int elementID,
//			@UInt32 int subID) {
//
//		return addSystemElement(true, registryDesignator, schemeID, elementID, subID);
//	}
//
//	public @GCStreamID int addCPSystemElement(
//			@UInt32 int registryDesignator,
//			@UInt32 int schemeID,
//			@UInt32 int elementID) {
//
//		return addSystemElement(true, registryDesignator, schemeID, elementID, 0);
//	}
//
////
////		//! Define a new system element for this container
////
////		GCStreamID AddSystemElement(bool CPCompatible, unsigned int RegistryDesignator, unsigned int SchemeID, unsigned int ElementID, unsigned int SubID = 0);
////
//	public @GCStreamID int addSystemElement(
//			boolean cpCompatible,
//			@UInt32 int registryDesignator,
//			@UInt32 int schemeID,
//			@UInt32 int elementID,
//			@UInt32 int subID) {
//
//		// TODO
//		return 0;
//	}
//
//	public @GCStreamID int addSystemElement(
//			boolean cpCompatible,
//			@UInt32 int registryDesignator,
//			@UInt32 int schemeID,
//			@UInt32 int elementID) {
//
//		return addSystemElement(cpCompatible, registryDesignator, schemeID, elementID, 0);
//	}
////
////		//! Define a new non-CP picture element for this container
////
////		GCStreamID AddPictureElement(unsigned int ElementType) { return AddPictureElement(false, ElementType); }
////
//	public @GCStreamID int addPictureELement(
//			@UInt32 int elementType) {
//
//		return addPictureElement(false, elementType);
//	}
////
////		//! Define a new CP-compatible picture element for this container
////
////		GCStreamID AddCPPictureElement(unsigned int ElementType) { return AddPictureElement(true, ElementType); }
////
//	public @GCStreamID int addCPPictureElement(
//			@UInt32 int elementType) {
//
//		return addPictureElement(true, elementType);
//	}
////
////		//! Define a new picture element for this container
////
////		GCStreamID AddPictureElement(bool CPCompatible, unsigned int ElementType) { return AddEssenceElement( CPCompatible ? 0x05 : 0x15, ElementType); }
////
//	public @GCStreamID int addPictureElement(
//			boolean cpCompatible,
//			@UInt32 int elementType) {
//
//		return addEssenceElement(cpCompatible ? 0x05 : 0x15, elementType);
//	}
////
////		//! Define a new non-CP sound element for this container
//// TODO report this error to Matt Beard
////		GCStreamID AddSoundElement(unsigned int ElementType) { return AddPictureElement(false, ElementType); }
////
//	public @GCStreamID int addSoundElement(
//			@UInt32 int elementType) {
//
//		return addSoundElement(false, elementType);
//	}
////
////		//! Define a new CP-compatible sound element for this container
//// TODO report this error to Matt Beard
////		GCStreamID AddCPSoundElement(unsigned int ElementType) { return AddPictureElement(true, ElementType); }
////
//	public @GCStreamID int addCPSoundElement(
//			@UInt32 int elementType) {
//
//		return addSoundElement(true, elementType);
//	}
//
////
////		//! Define a new sound element for this container
////
////		GCStreamID AddSoundElement(bool CPCompatible, unsigned int ElementType) { return AddEssenceElement( CPCompatible ? 0x06 : 0x16, ElementType); }
////
//	public @GCStreamID int addSoundElement(
//			boolean cpCompatible,
//			@UInt32 int elementType) {
//
//		return addEssenceElement(cpCompatible ? 0x06 : 0x16, elementType);
//	}
////
////		//! Define a new non-CP data element for this container
////
////		GCStreamID AddDataElement(unsigned int ElementType) { return AddDataElement(false, ElementType); }
////
//	public @GCStreamID int addDataElement(
//			@UInt32 int elementType) {
//
//		return addDataElement(false, elementType);
//	}
////
////		//! Define a new CP-compatible data element for this container
////
////		GCStreamID AddCPDataElement(unsigned int ElementType) { return AddDataElement(true, ElementType); }
////
//	public @GCStreamID int addContentPDataElement(
//			@UInt32 int elementType) {
//
//		return addDataElement(true, elementType);
//	}
//
////
////		//! Define a new data element for this container
////
////		GCStreamID AddDataElement(bool CPCompatible, unsigned int ElementType) { return AddEssenceElement( CPCompatible ? 0x07 : 0x17, ElementType); }
////
//	public @GCStreamID int addDataElement(
//			boolean cpCompatible,
//			@UInt32 int elementType) {
//
//		return addEssenceElement(cpCompatible ? 0x07 : 0x17, elementType);
//	}
////
////		//! Define a new compound element for this container
////
////		GCStreamID AddCompoundElement(unsigned int ElementType) { return AddEssenceElement( 0x18, ElementType); }
////
//	public @GCStreamID int addCompoundELement(
//			@UInt32 int elementType) {
//
//		return addEssenceElement(0x18, elementType);
//	}
////
////		//! Define a new essence element for this container
////
////		GCStreamID AddEssenceElement(unsigned int EssenceType, unsigned int ElementType, int LenSize = 0);
////
//	public @GCStreamID int addEssenceElement(
//			@UInt32 int essenceType,
//			@UInt32 int elementType,
//			int lengthSize) {
//
//		// TODO
//		return 0;
//	}
//
//	public @GCStreamID int addEssenceElement(
//			@UInt32 int essenceType,
//			@UInt32 int elementType) {
//
//		return addEssenceElement(essenceType, elementType, 0);
//	}
//
//	//
////		//! Define a new essence element for this container, with a specified key
////
////		GCStreamID AddEssenceElement(DataChunkPtr &Key, int LenSize = 0, bool NonGC = false);
////
//	public @GCStreamID int addEssenceElement(
//			ByteBuffer key,
//			int lengthSize,
//			boolean nonGenericContainer) {
//
//		// TODO
//		return 0;
//	}
//
//	public @GCStreamID int addEssenceElement(
//			ByteBuffer key,
//			int lengthSize) {
//
//		return addEssenceElement(key, lengthSize, false);
//	}
//
//	public @GCStreamID int addEssenceElement(
//			ByteBuffer key) {
//
//		return addEssenceElement(key, 0, false);
//	}
//
////
////		//! Define a new essence element for this container, with a specified key
////
////		GCStreamID AddEssenceElement(int KeySize, UInt8 *KeyData, int LenSize = 0, bool NonGC = false)
////
////		{
////
////			DataChunkPtr Key = new DataChunk(KeySize, KeyData);
////
////			return AddEssenceElement(Key, LenSize, NonGC);
////
////		}
////
//	public @GCStreamID int addEssenceElement(
//			@UInt8Array byte[] keyData,
//			int lengthSize,
//			boolean nonGenericContainer) {
//
//		ByteBuffer key = ByteBuffer.wrap(keyData);
//		return addEssenceElement(key, lengthSize, nonGenericContainer);
//	}
//
//	public @GCStreamID int addEssenceElement(
//			@UInt8Array byte[] keyData,
//			int lengthSize) {
//
//		return addEssenceElement(keyData, lengthSize, false);
//	}
//
//	public @GCStreamID int addEssenceElement(
//			@UInt8Array byte[] keyData) {
//
//		return addEssenceElement(keyData, 0, false);
//	}
//
////
////		//! Allow this data stream to be indexed and set the index manager
////
////		void AddStreamIndex(GCStreamID ID, IndexManagerPtr &IndexMan, int IndexSubStream, bool IndexFiller = false, bool IndexClip = false);
////
//	public void addStreamIndex(
//			@GCStreamID int streamID,
//			IndexManager indexManager,
//			boolean indexFiller,
//			boolean indexClip) {
//
//		// TODO
//	}
//
//	public void addStreamIndex(
//			@GCStreamID int streamID,
//			IndexManager indexManager) {
//
//		addStreamIndex(streamID, indexManager, false, false);
//	}
//
////
////		//! Get the track number associated with the specified stream
////
////		UInt32 GetTrackNumber(GCStreamID ID);
////
//	public @UInt32 int getTrackNumber(
//			@GCStreamID int streamID) {
//
//		// TODO
//		return 0;
//	}
////
////		//! Assign an essence container (mapping) UL to the specified stream
////
////		void AssignEssenceUL(GCStreamID ID, ULPtr EssenceUL);
////
//	public void assignEssenceUL(
//			@GCStreamID int streamID,
//			UL essenceUL) {
//
//		// TODO
//	}
////
////		//! Start a new content package (and write out the prevous one if required)
////
////		void StartNewCP(void);
////
//	public void startNewContentPackage() {
//
//		// TODO
//	}
//
////
////		//! Calculate how much data will be written if "Flush" is called now
////
////		UInt64 CalcWriteSize(void);
////
//	public @UInt64 long calculateWriteSize() {
//
//		// TODO
//		return 0l;
//	}
////
////		//! Flush any remaining data
////
////		void Flush(void);
////
//	public void flush() {
//
//		// TODO
//	}
////
////		//! Get the current stream offset
////
////		Int64 GetStreamOffset(void) { return StreamOffset; }
////
//	public @Int64 long getStreamOffset() {
//
//		return streamOffset;
//	}
////
////		//! Set the index position for the current CP
////
////		void SetIndexEditUnit(Position EditUnit) { IndexEditUnit = EditUnit; }
////
//	public void setIndexEditUnit(
//			@MXFPosition long editUnit) {
//
//		this.indexEditUnit = editUnit;
//	}
////
////		//! Get the index position of the current CP
////
////		Position GetIndexEditUnit(void) { return IndexEditUnit; }
////
//	public @MXFPosition long getIndexEditUnit() {
//
//		return indexEditUnit;
//	}
////
////		//! Add system item data to the current CP
////
////		void AddSystemData(GCStreamID ID, UInt64 Size, const UInt8 *Data);
////
//	public void addSystemData(
//			@GCStreamID int streamID,
//			@UInt8Array byte[] data) {
//
//		// TODO is this the right way around?
//	}
////
////		//! Add system item data to the current CP
////
////		void AddSystemData(GCStreamID ID, DataChunkPtr Chunk) { AddSystemData(ID, Chunk->Size, Chunk->Data); }
////
//	public void addSystemData(
//			@GCStreamID int streamID,
//			ByteBuffer chunk) {
//
//		byte[] data = new byte[chunk.capacity()];
//		chunk.get(data);
//		addSystemData(streamID, data);
//	}
////
////		//! Add encrypted system item data to the current CP
////
////		void AddSystemData(GCStreamID ID, UInt64 Size, const UInt8 *Data, UUIDPtr ContextID, Length PlaintextOffset = 0);
////
//	public void addSystemData(
//			@GCStreamID int streamID,
//			@UInt8Array byte[] data,
//			UUID contextID,
//			@MXFLength long plainTextOffset) {
//
//		// TODO
//	}
//
//	public void addSystemData(
//			@GCStreamID int streamID,
//			@UInt8Array byte[] data,
//			UUID contextID) {
//
//		addSystemData(streamID, data, contextID, 0);
//	}
//
////
////		//! Add encrypted system item data to the current CP
////
////		void AddSystemData(GCStreamID ID, DataChunkPtr Chunk, UUIDPtr ContextID, Length PlaintextOffset = 0) { AddSystemData(ID, Chunk->Size, Chunk->Data, ContextID, PlaintextOffset); }
////
//	public void addSystemData(
//			@GCStreamID int streamID,
//			ByteBuffer chunk,
//			UUID contextID,
//			@MXFLength long plainTextOffset) {
//
//		byte[] data = new byte[chunk.capacity()];
//		chunk.get(data);
//
//		addSystemData(streamID, data, contextID, plainTextOffset);
//	}
//
////
////		//! Add essence data to the current CP
////
////		void AddEssenceData(GCStreamID ID, UInt64 Size, const UInt8 *Data);
////
//	public void addEssenceData(
//			@GCStreamID int streamID,
//			@UInt8Array byte[] data) {
//
//		// TODO
//	}
////
////		//! Add essence data to the current CP
////
////		void AddEssenceData(GCStreamID ID, DataChunkPtr Chunk) { AddEssenceData(ID, Chunk->Size, Chunk->Data); }
////
//	public void addEssenceData(
//			@GCStreamID int streamID,
//			ByteBuffer chunk) {
//
//		byte[] data = new byte[chunk.capacity()];
//		chunk.get(data);
//
//		addEssenceData(streamID, data);
//	}
//
////
////		//! Add essence data to the current CP
////
////		void AddEssenceData(GCStreamID ID, EssenceSourcePtr Source, bool FastClipWrap = false);
////
//	public void addEssenceData(
//			@GCStreamID int streamID,
//			EssenceSource source,
//			boolean fastClipWrap) {
//
//		// TODO
//	}
//
//	public void addEssenceData(
//			@GCStreamID int streamID,
//			EssenceSource source) {
//
//		addEssenceData(streamID, source, false);
//	}
//
//	//
//
////
////		//! Add an essence item to the current CP with the essence to be read from a KLVObject
////
////		void AddEssenceData(GCStreamID ID, KLVObjectPtr Source, bool FastClipWrap = false);
////
//	public void addEssenceData(
//			@GCStreamID int streamID,
//			KLVObject source,
//			boolean fastClipWrap) {
//
//		// TODO
//	}
//
//	public void addEssenceData(
//			@GCStreamID int streamID,
//			KLVObject source) {
//
//		addEssenceData(streamID, source, false);
//	}
//
////		//! Calculate how many bytes would be written if the specified object were written with WriteRaw()
////
////		Length CalcRawSize(KLVObjectPtr Object);
////
//	public @MXFLength long calculateRowSize(
//			KLVObject klvObject) {
//
//		// TODO
//		return 0l;
//	}
////
////		//! Write a raw KLVObject to the file - this is written immediately and not buffered in the WriteQueue
////
////		void WriteRaw(KLVObjectPtr Object);
////
//	public void writeRaw(
//			KLVObject klvObject) {
//
//		// TODO
//	}
//
////
////		//! Queue of items for the current content package in write order
////
////		WriteQueueMap WriteQueue;
////
//	private Map<Integer, WriteBlock> writeQueue;
////
////
////
////		//! Set the WriteOrder for the specified stream
////
////		void SetWriteOrder(GCStreamID ID, Int32 WriteOrder = -1, int Type =-1);
////
//	public void setWriteOrder(
//			@GCStreamID int streamID,
//			@Int32 int writeOrder,
//			int type) {
//
//		// TODO
//	}
//
//	public void setWriteOrder(
//			@GCStreamID int streamID) {
//
//		setWriteOrder(streamID, -1, -1);
//	}
//
//
////		//! Set a write-order relative to all items of a specified type
////
////		void SetRelativeWriteOrder(GCStreamID ID, int Type, Int32 Position);
////
//	public void setRelativeWriteOrder(
//			@GCStreamID int streamID,
//			int type,
//			@Int32 int position) {
//
//		// TODO
//	}
////
////		//! Get the WriteOrder for the specified stream
////
////		Int32 GetWriteOrder(GCStreamID ID);
////
//	public @Int32 int getWriteOrder(
//			@GCStreamID int streamID) {
//
//		// TODO
//		return 0;
//	}
////
////		//! Read the count of streams
////
////		int GetStreamCount(void) { return StreamCount; };
////
//	public int getStreamCount() {
//
//		return streamCount;
//	}
//}
