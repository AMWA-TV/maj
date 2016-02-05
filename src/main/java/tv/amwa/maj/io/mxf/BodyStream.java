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
// * $Log: BodyStream.java,v $
// * Revision 1.4  2010/01/19 14:44:24  vizigoth
// * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
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
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.Vector;
//
//import tv.amwa.maj.integer.UInt32;
//import tv.amwa.maj.io.mxf.WrappingOption.WrapType;
//
//// from mxflib essence.h
//
//// Implements a list of essence sources.
///**
// * <p>A stream to be written by a {@linkplain BodyWriter body writer}. Sub-streams can be added via pointers
// * to their {@linkplain EssenceSource essence sources} as this class implements a generic essence source list.
// * Sub-streams will be written in the same generic container as this stream. This stream's essence source will
// * appear as the first <em>child</em> when the essence source list is scanned.</p>
// *
// *
// *
// * @see BodyWriter
// */
//public class BodyStream
//	implements List<EssenceSource> {
//
//	private List<EssenceSource> essenceSources =
//		new Vector<EssenceSource>();
//
////	public:
////
////		//! Define the action required next for this stream
////
////		enum StateType
////
////		{
////
////			BodyStreamStart = 0,									//!< This stream has not yet done anything - state unknown
////
////			BodyStreamHeadIndex,									//!< Next action: Write a "header" index table - if required in an isolated partition following the header
////
////			BodyStreamPreBodyIndex,									//!< Next action: Write an isolated index table before the next body partition
////
////			BodyStreamBodyWithIndex,								//!< Next action: Write a body partition with an index table
////
////			BodyStreamBodyNoIndex,									//!< Next action: Write a body partition without index table
////
////			BodyStreamPostBodyIndex,								//!< Next action: Write an isolated index table after a body partition
////
////			BodyStreamFootIndex,									//!< Next action: Write a "footer" index table - if required in an isolated partition before the footer
////
////			BodyStreamDone											//!< All done - no more actions required
////
////		};
////
//	public enum State {
//
//		Start,
//		HeadIndex,
//		PreBodyIndex,
//		BodyWithIndex,
//		BodyNoIndex,
//		PostBodyIndex,
//		FootIndex,
//		Done;
//	}
////
////		//! The index table type or types of this stream
////
////		enum IndexType
////
////		{
////
////			StreamIndexNone = 0,									//!< No index table will be written
////
////			StreamIndexFullFooter = 1,								//!< A full index table will be written in the footer if possible (or an isolated partition just before the footer if another index is going to use the footer)
////
////			StreamIndexSparseFooter = 2,							//!< A sparse index table will be written in the footer if possible (or an isolated partition just before the footer if another index is going to use the footer)
////
////			StreamIndexSprinkled = 4,								//!< A full index table will be sprinkled through the file, one chunk in each of this essence's body partitions, and one in or just before the footer
////
////			StreamIndexSprinkledIsolated = 8,						//!< A full index table will be sprinkled through the file, one chunk in an isolated partition following each of this essence's body partitions
////
////			StreamIndexCBRHeader = 16,								//!< A CBR index table will be written in the header (or an isolated partition following the header if another index table exists in the header)
////
////			StreamIndexCBRHeaderIsolated = 32,						//!< A CBR index table will be written in an isolated partition following the header
////
////			StreamIndexCBRFooter = 64,								//!< A CBR index table will be written in the footer if possible (or an isolated partition just before the footer if another index is going to use the footer)
////
////			StreamIndexCBRBody = 128,								//!< A CBR index table will be written in each body partition for this stream
////
////			StreamIndexCBRIsolated = 256,							//!< A CBR index table will be written in an isolated body partition following each partition of this stream
////
////			StreamIndexCBRPreIsolated = 512							//!< A CBR index table will be written in an isolated body partition before each partition of this stream
////
////		};
////
//	public enum IndexType {
//
//		None(0),
//		FullFooter(1),
//		SparseFooter(2),
//		Sprinkled(4),
//		SprinkledIsolated(8),
//		CBRHeader(16),
//		CBRHeaderIsolated(32),
//		CBRFooter(64),
//		CBRBody(128),
//		CBRIsolated(256),
//		CBRPreIsolated(512);
//
//		private final int bitFieldValue;
//
//		private IndexType(
//				int bitFieldValue) {
//
//			this.bitFieldValue = bitFieldValue;
//		}
//
//		public int getBitFieldValue() {
//
//			return bitFieldValue;
//		}
//
//		public boolean inStreamIndex(
//				int streamIndex) {
//
//			return ((bitFieldValue & streamIndex) != 0);
//		}
//	}
////
////		//! Wrapping types for streams
////
////		enum WrapType
////
////		{
////
////			StreamWrapOther = 0,									//!< Other non-standard wrapping types - the essence source will supply one KLVs worth at a time (??)
////
////			StreamWrapFrame,										//!< Frame wrapping
////
////			StreamWrapClip											//!< Clip wrapping
////
////		};
////
//	public enum Wrap {
//
//		Other,
//		Frame,
//		Clip;
//	}
////
////	protected:
////
////		EssenceSourcePtr Source;									//!< The essence source for this stream
//
//	private EssenceSource source;
//
////		EssenceSourceList SubStreams;								//!< Sources for each sub-stream
//
//	private List<EssenceSource> subStreams;
//
////		EssenceSourceList::iterator SubStream_it;					//!< Current sub-stream
//
//	private Iterator<EssenceSource> subStreamIterator;
//
////		bool SubStreamRestart;										//!< Flag true when the sub-stream iterator needs moving to the top of the list next time
////
//	private boolean subStreamRestart = true;
////
////		StateType State;											//!< The state of this stream
////
//	private State state = State.Start;
////
////		IndexType StreamIndex;										//!< The index type(s) of this stream
//
//	private int streamIndex = IndexType.None.getBitFieldValue();
//
////		IndexType FooterIndexFlags;									//!< Set of flags for tracking footer index tables
////
//	private IndexType footerIndexFlags = IndexType.None;
////
////		UInt32 BodySID;												//!< BodySID to use for this stream
//
//	private @UInt32 int bodySID;
//
////		UInt32 IndexSID;											//!< IndexSID to use for indexing this stream
////
//	private @UInt32 int indexSID = 0;
////
////		WrapType StreamWrap;										//!< The wrapping type of this stream
////
//	private Wrap streamWrap = Wrap.Other;
////
////		GCWriterPtr StreamWriter;									//!< The writer for this stream
////
//	private GenericContainerWriter streamWriter;
////
////		bool EssencePendingData;									//!< Is there any essence data pending in the writer?
////
//	private boolean essencePendingData = false;
////
////		bool EndOfStream;											//!< No more essence available for this stream
////
//	private boolean endOfStream = false;
////
////		IndexManagerPtr IndexMan;									//!< The index manager for this stream
////
////	private IndexManager indexManager;
////
////		Position NextSprinkled;										//!< The location of the first edit-unit to use for the next sprinkled index segment
////
//	private @MXFPosition long nextSprinkled = 0l;
////
////		bool FreeSpaceIndex;										//!< True if the free space at the end of the essenc eis to be indexed
////
////																	/*!< When an essence stream may be extended during file creation it may be useful to know
////
////																	 *   where the essence currently ends (to allow new essence to be added).
////
////																	 *   /note DRAGONS: This is non-standard and will produce invalid index tables (even if they are later "fixed")
////
////																	 */
////
//	private boolean freeSpaceIndex = false;
////
////		bool ValueRelativeIndexing;									//!< Flag to allow value-relative indexing
////
////																	/*!< \note This is NOT implemented in the IndexManager, but must be handled by the caller */
////
//	private boolean valueRelativeIndexing = false;
////
////		Length PrechargeSize;										//!< The number of edit units of pre-charge remaining to be written
////
////																	/*!< DRAGONS: This is set when the state moves from "start" because it is
////
////																	 *            important to wait for all sub-streams to be set up first
////
////																	 */
////
//	private @MXFLength long prechargeSize = 0l;
////
////		//! KLV Alignment Grid to use for this stream (of zero if default for this body is to be used)
////
////		UInt32 KAG;
////
//	private @UInt32 int kag = 0;
////
////		//! Flag set if BER lengths for this stream should be forced to 4-byte (where possible)
////
////		bool ForceBER4;
////
//	private boolean forceBER4 = false;
////
////		//! Flag set if partitioning is to be done only on edit boundaries
////
////		/*! \note Only the master stream is (currently) edit aligned, not all sub-streams */
////
////		bool EditAlign;
////
//	private boolean editAlign = false;
////
////		//! Prevent NULL construction
////
////		BodyStream();
//
////	public:
////
////			std::list<Position> SparseList;								//!< List of edit units to include in sparse index tables
////
//	private @MXFPosition List<Long> sparseList;
////
//	private BodyStream() { };
////
////		//! Prevent copy construction
////
////		BodyStream(BodyStream &);
////
////
////
////	/* Public properties */
////
////
////	public:
////
////		//! Construct an body stream object with a given essence source
////
////		BodyStream(UInt32 SID, EssenceSourcePtr &EssSource, DataChunkPtr Key = NULL, bool NonGC = false)
////
////		{
////
////			BodySID = SID;
////
////			IndexSID = 0;
////
////			Source = EssSource;
////
////			State = BodyStreamStart;
////
////			StreamIndex = StreamIndexNone;
////
////			FooterIndexFlags = StreamIndexNone;
////
////			StreamWrap = StreamWrapOther;
////
////			SubStreamRestart = true;
////
////			NextSprinkled = 0;
////
////			EssencePendingData = false;
////
////			EndOfStream = false;
////
////			FreeSpaceIndex = false;
////
////			ValueRelativeIndexing = false;
////
////			PrechargeSize = 0;
////
////
////
////			KAG = 0;
////
////			ForceBER4 = false;
////
////			EditAlign = false;
////
////
////
////			// Set the non-standard key if requested
////
////			if(Key) EssSource->SetKey(Key, NonGC);
////
////
////
////			// Set the master stream as one of the essence streams
////
////			push_back(Source);
////
////		}
////
//	public BodyStream(
//			@UInt32 int bodySID,
//			EssenceSource essenceSource,
//			ByteBuffer key,
//			boolean nonGenericContainer) {
//
//		this.bodySID = bodySID;
//		this.source = essenceSource;
//
//		if (key != null)
//			essenceSource.setKey(key, nonGenericContainer);
//
//		essenceSources.add(source);
//	}
//
//	public BodyStream(
//			@UInt32 int bodySID,
//			EssenceSource essenceSource) {
//
//		this.bodySID = bodySID;
//		this.source = essenceSource;
//
//		essenceSources.add(source);
//	}
//
//
////		//! Get the essence source for this stream
////
////		EssenceSourcePtr &GetSource(void) { return Source; }
////
//	public EssenceSource getSource() {
//
//		return source;
//	}
////
////		//! Get the number of sub-streams (includes the master stream)
////
////		size_type SubStreamCount(void) { return size(); }
////
//	public int subStreamCount() {
//
//		return essenceSources.size();
//	}
////
////		//! Add a new sub-stream
////
////		void AddSubStream(EssenceSourcePtr &SubSource, DataChunkPtr Key = NULL, bool NonGC = false);
////
//	public void addSubStream(
//			EssenceSource subSource,
//			ByteBuffer key,
//			boolean nonGenericContainer) {
//
//		// TODO
//	}
//
//	public void addSubStream(
//			EssenceSource subSource) {
//
//		addSubStream(subSource, null, false);
//	}
//
////		//! Get this stream's BodySID
////
////		UInt32 GetBodySID(void) { return BodySID; }
////
//	public @UInt32 int getBodySID() {
//
//		return bodySID;
//	}
////
////		//! Set this stream's IndexSID
////
////		void SetIndexSID(UInt32 SID) { IndexSID = SID; }
////
//	public void setIndexSID(
//			@UInt32 int indexSID) {
//
//		this.indexSID = indexSID;
//	}
////
////		//! Get this stream's IndexSID
////
////		UInt32 GetIndexSID(void) { return IndexSID; }
////
//	public @UInt32 int getIndexSID() {
//
//		return indexSID;
//	}
////
////		//! Set the stream's state
////
////		void SetState(StateType NewState) { State = NewState; }
////
//	public void setState(
//			State state) {
//
//		this.state = state;
//	}
////
////		//! Get the current state
////
////		StateType GetState(void)
////
////		{
////
////			if(State == BodyStreamStart) GetNextState();
////
////			return State;
////
////		}
////
//	public State getState() {
//
//		if (state == State.Start)
//			getNextState();
//
//		return state;
//	}
////
////		//! Get the next state
////
////		/*! Sets State to the next state
////
////		 *  \return The next state (now the current state)
////
////		 */
////
////		StateType GetNextState(void);
////
//	public State getNextState() {
//
//		// TODO
//		return null;
//	}
////
////		//! Add the specified index type(s)
////
////		void AddIndexType(IndexType NewIndexType) { StreamIndex = (IndexType) (StreamIndex | NewIndexType); }
////
//	public void addIndexType(
//			IndexType indexType) {
//
//		streamIndex = streamIndex | indexType.getBitFieldValue();
//	}
////
////		//! Set the index type(s) to the desired value
////
////		/*! \note This sets the complete value, it doesn't just add an option - to add "X" use AddIndexType()
////
////		 */
////
////		void SetIndexType(IndexType NewIndexType) { StreamIndex = NewIndexType; }
////
//	public void setIndexType(
//			IndexType indexType) {
//
//		streamIndex = indexType.getBitFieldValue();
//	}
////
////		//! Get the index type(s)
////
////		IndexType GetIndexType(void) { return StreamIndex; }
////
//	public int getIndexType() {
//
//		return streamIndex;
//	}
////
////		//! Set the footer index flags to the desired value
////
////		/*! \note This sets the complete value, it doesn't just add an option - to add "X" use SetFooterIndex(GetFooterIndex() | "X");
////
////		 */
////
////		void SetFooterIndex(IndexType NewIndexType) { FooterIndexFlags = NewIndexType; }
////
//	public void setFooterIndex(
//			IndexType indexType) {
//
//		footerIndexFlags = indexType;
//	}
////
////		//! Get the footer index flags
////
////		IndexType GetFooterIndex(void) { return FooterIndexFlags; }
////
//	public IndexType getFooIndexType() {
//
//		return footerIndexFlags;
//	}
////
////		//! Set the wrapping type for this stream
////
////		void SetWrapType(WrapType NewWrapType) { StreamWrap = NewWrapType; }
////
//	public void setWrapType(
//			Wrap wrapType) {
//
//		streamWrap = wrapType;
//	}
////
////		//! Set the wrapping type for this stream
////
////		void SetWrapType(WrappingOption::WrapType NewWrapType)
////
////		{
////
////			if(NewWrapType == WrappingOption::Frame) StreamWrap = StreamWrapFrame;
////
////			else if(NewWrapType == WrappingOption::Clip) StreamWrap = StreamWrapClip;
////
////			else StreamWrap = StreamWrapOther;
////
////		}
////
//	public void setWrapType(
//			WrappingOption.WrapType wrapType) {
//
//		if (wrapType == WrappingOption.WrapType.Frame)
//			streamWrap = Wrap.Frame;
//		else if (wrapType == WrappingOption.WrapType.Clip)
//			streamWrap = Wrap.Clip;
//		else
//			streamWrap = Wrap.Other;
//	}
////
////		//! Get the wrapping type of this stream
////
////		WrapType GetWrapType(void) { return StreamWrap; }
//
//	public Wrap getWrapType() {
//
//		return streamWrap;
//	}
////
////		//! Set the current GCWriter
////
////		void SetWriter(GCWriterPtr &Writer);
////
//	public void setWriter(
//			GenericContainerWriter writer) {
//
//		// TODO
//	}
////
////		//! Get the current index manager
////
////		IndexManagerPtr &GetIndexManager(void)
////
////		{
////
////			if(!IndexMan) InitIndexManager();
////
////			return IndexMan;
////
////		}
////
////	public IndexManager getIndexManager() {
////
////		if (indexManager == null)
////			initializeIndexManager();
////
////		return indexManager;
////	}
////
////		//! Get a reference to the current GCWriter
////
////		GCWriterPtr &GetWriter(void) { return StreamWriter; }
////
//	public GenericContainerWriter getWriter() {
//
//		return streamWriter;
//	}
////
////		//! Get the track number associated with this stream
////
////		UInt32 GetTrackNumber(void)
////
////		{
////
////			if(!Source) return 0;
////
////			return StreamWriter->GetTrackNumber(Source->GetStreamID());
////
////		}
////
//	public @UInt32 int getTrackNumber() {
//
//		if (source == null)
//			return 0;
//
//		return streamWriter.getTrackNumber(source.getStreamID());
//	}
//
////		//! Get the track number associated with a specified stream or sub-stream
////
////		Uint32 GetTrackNumber(GCStreamID ID)
////
////		{
////
////			if(!Source) return 0;
////
////			return StreamWriter->GetTrackNumber(ID);
////
////		}
////
//	public @UInt32 int getTrackNumber(
//			@GCStreamID int streamID) {
//
//		if (source == null)
//			return 0;
//
//		return streamWriter.getTrackNumber(streamID);
//	}
//
////
////		//! Set the pending essence data flag
////
////		void SetPendingData(bool Value = true) { EssencePendingData = Value; }
////
//	public void setPendingData(
//			boolean essencePendingData) {
//
//		this.essencePendingData = essencePendingData;
//	}
//
//	public void setPendingData() {
//
//		this.essencePendingData = true;
//	}
//
////
////		//! Find out if there is any essence data stored in the GCWriter pending a write
////
////		bool HasPendingData(void) { return EssencePendingData; }
////
//	public boolean hasPendingData() {
//
//		return essencePendingData;
//	}
////
////		//! Set the EndOfStream flag
////
////		void SetEndOfStream(bool Value = true) { EndOfStream = Value; }
////
//	public void setEndOfStream(
//			boolean endOfStream) {
//
//		this.endOfStream = endOfStream;
//	}
//
//	public void setEndOfStream() {
//
//		this.endOfStream = true;
//	}
//
////
////		//! Find out if there is any essence data remaining for this stream
////
////		bool GetEndOfStream(void) { return EndOfStream; }
////
//	public boolean getEndOfStream() {
//
//		return endOfStream;
//	}
////
////		//! Set the first edit unit for the next sprinkled index segment
////
////		void SetNextSprinkled(Position Sprinkled) { NextSprinkled = Sprinkled; }
////
//	public void setNextSprinkled(
//			@MXFPosition long nextSprinked) {
//
//		this.nextSprinkled = nextSprinked;
//	}
////
////		//! Get the first edit unit for the next sprinkled index segment
////
////		Position GetNextSprinkled(void) { return NextSprinkled; }
////
//	public @MXFPosition long getNextSprinked() {
//
//		return nextSprinkled;
//	}
////
////		//! Set the KLV Alignment Grid
////
////		// FIXME: This will break CBR indexing if changed during writing!
////
////		void SetKAG(UInt32 NewKAG) { KAG = NewKAG; }
////
//	public void setKAG(
//			@UInt32 int kag) {
//
//		this.kag = kag;
//	}
////
////		//! Get the KLV Alignment Grid
////
////		UInt32 GetKAG(void) { return KAG; }
////
//	public @UInt32 int getKAG() {
//
//		return kag;
//	}
////
////		//! Set flag if BER lengths should be forced to 4-byte (where possible)
////
////		void SetForceBER4(bool Force) { ForceBER4 = Force; }
////
//	public void setForceBER4(
//			boolean forceBER4) {
//
//		this.forceBER4 = forceBER4;
//	}
////
////		//! Get flag stating whether BER lengths should be forced to 4-byte (where possible)
////
////		bool GetForceBER4(void) { return ForceBER4; }
////
//	public boolean getForceBER4() {
//
//		return forceBER4;
//	}
////
////		//! Set edit align forced partitioning flag
////
////		void SetEditAlign(bool Align) { EditAlign = Align; }
////
//	public void setEditAlign(
//			boolean editAlign) {
//
//		this.editAlign = editAlign;
//	}
////
////		//! Get edit align forced partitioning flag
////
////		bool GetEditAlign(void) { return EditAlign; }
////
//	public boolean getEditAlign() {
//
//		return editAlign;
//	}
////
////		//! Set the "FreeSpaceIndex" flag
////
////		/*! \note DRAGONS: Setting this flag will cause index tables that are not SMPTE 377M complient to be created */
////
////		void SetFreeSpaceIndex(bool Flag) { FreeSpaceIndex = Flag; }
////
//	public void setFreeSpaceIndex(
//			boolean freeSpaceIndex) {
//
//		this.freeSpaceIndex = freeSpaceIndex;
//	}
////
////		//! Read the "FreeSpaceIndex" flag
////
////		bool GetFreeSpaceIndex(void) { return FreeSpaceIndex; }
////
//	public boolean getFreeSpaceIndex() {
//
//		return freeSpaceIndex;
//	}
////
////		//! Set value-relative indexing flag
////
////		/*! Value-relative indexing will produce index tables that count from the first byte of the KLV
////
////		 *  of clip-wrapped essence rather than the key. These tables can be used internally but must not
////
////		 *  be written to a file as they are not 377M complient */
////
////		void SetValueRelativeIndexing(bool Val)
////
////		{
////
////			ValueRelativeIndexing = Val;
////
////			if(IndexMan) IndexMan->SetValueRelativeIndexing(Val);
////
////		}
////
//	public void setValueRelativeIndexing(
//			boolean valueRelativeIndexing) {
//
//		this.valueRelativeIndexing = valueRelativeIndexing;
//
////		if (indexManager != null)
////			indexManager.setValueRelativeIndexing(valueRelativeIndexing);
//	}
////
////		//! Get value-relative indexing flag
////
////		/*! Value-relative indexing will produce index tables that count from the first byte of the KLV
////
////		 *  of clip-wrapped essence rather than the key. These tables can be used internally but must not
////
////		 *  be written to a file as they are not 377M complient */
////
////		bool GetValueRelativeIndexing(void) { return ValueRelativeIndexing; }
////
//	public boolean getValueRelativeIndexing() {
//
//		return valueRelativeIndexing;
//	}
////
////		//! Read the number of edit units of pre-charge remaining
////
////		Length GetPrechargeSize(void) const { return PrechargeSize; }
////
//	public @MXFLength long getPrechargeSize() {
//
//		return prechargeSize;
//	}
////
////		//! Reduce the precharge count by one
////
////		void DecrementPrecharge(void) { if(PrechargeSize) PrechargeSize--; }
////
//	public void decrementPrecharge() {
//
//		if (prechargeSize > 0) prechargeSize--;
//	}
////
////		//! Initialize an index manager if required
////
////		void InitIndexManager(void);
//
//	public void initializeIndexManager() {
//
//		// TODO
//	}
//
//	public boolean add(EssenceSource o) {
//
//		return essenceSources.add(o);
//	}
//
//	public void add(
//			int index,
//			EssenceSource element) {
//
//		essenceSources.add(index, element);
//	}
//
//	public boolean addAll(
//			Collection<? extends EssenceSource> c) {
//
//		return essenceSources.addAll(c);
//	}
//
//	public boolean addAll(
//			int index,
//			Collection<? extends EssenceSource> c) {
//
//		return essenceSources.addAll(index, c);
//	}
//
//	public void clear() {
//
//		essenceSources.clear();
//	}
//
//	public boolean contains(Object o) {
//
//		return essenceSources.contains(o);
//	}
//
//	public boolean containsAll(
//				Collection<?> c) {
//
//		return essenceSources.containsAll(c);
//	}
//
//	public EssenceSource get(
//			int index) {
//
//		return essenceSources.get(index);
//	}
//
//	public int indexOf(
//			Object o) {
//
//		return essenceSources.indexOf(o);
//	}
//
//	public boolean isEmpty() {
//
//		return essenceSources.isEmpty();
//	}
//
//	public Iterator<EssenceSource> iterator() {
//
//		return essenceSources.iterator();
//	}
//
//	public int lastIndexOf(
//			Object o) {
//
//		return lastIndexOf(o);
//	}
//
//	public ListIterator<EssenceSource> listIterator() {
//
//		return essenceSources.listIterator();
//	}
//
//	public ListIterator<EssenceSource> listIterator(
//			int index) {
//
//		return essenceSources.listIterator(index);
//	}
//
//	public boolean remove(
//			Object o) {
//
//		return essenceSources.remove(o);
//	}
//
//	public EssenceSource remove(
//			int index) {
//
//		return essenceSources.remove(index);
//	}
//
//	public boolean removeAll(
//			Collection<?> c) {
//
//		return essenceSources.removeAll(c);
//	}
//
//	public boolean retainAll(
//			Collection<?> c) {
//
//		return essenceSources.retainAll(c);
//	}
//
//	public EssenceSource set(
//			int index,
//			EssenceSource element) {
//
//		return essenceSources.set(index, element);
//	}
//
//	public int size() {
//
//		return essenceSources.size();
//	}
//
//	public List<EssenceSource> subList(
//			int fromIndex,
//			int toIndex) {
//
//		return essenceSources.subList(fromIndex, toIndex);
//	}
//
//	public Object[] toArray() {
//
//		return essenceSources.toArray();
//	}
//
//	public <T> T[] toArray(
//			T[] a) {
//
//		return essenceSources.toArray(a);
//	}
//
//}
