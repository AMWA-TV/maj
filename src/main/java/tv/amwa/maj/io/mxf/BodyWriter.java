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
// * $Log: BodyWriter.java,v $
// * Revision 1.4  2010/01/19 14:44:23  vizigoth
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
//import java.util.List;
//
//import tv.amwa.maj.integer.UInt32;
//import tv.amwa.maj.io.mxf.impl.MXFFileImpl;
//import tv.amwa.maj.io.mxf.impl.PartitionImpl;
//
//// from mxflib essence.h
//
///**
// * <p>Manages the multiplexing of essence when writing an MXF file.</p>
// *
// *
// *
// */
//public class BodyWriter {
//
////	protected:
////
////		// States for BodyWriter
////
////		enum BodyState
////
////		{
////
////			BodyStateStart = 0,										//!< The BodyWriter has not yet started writing
////
////			BodyStateHeader,										//!< Writing the header (and/or post header indexes)
////
////			BodyStateBody,											//!< Writing the body essence and indexes
////
////			BodyStateFooter,										//!< Writing the footer (and/or pre-footer indexes or RIP)
////
////			BodyStateDone											//!< All done
////
////		};
////
//	private enum BodyState {
//
//		Start,
//		Header,
//		Body,
//		Footer,
//		Done;
//	}
////
////		//! The state for this writer
////
////		BodyState State;
////
//	private BodyState state = BodyState.Start;
////
////		//! Class for holding info relating to a stream
////
////		/*! This class holds medium-term info about a stream in comparision to BodyStream which holds
////
////		 *  long-term info. This is because odd interleaving may cause a stream to be added and removed
////
////		 *  from the writer during the course of the file.  Data that needs to survive through the whole
////
////		 *  file lives in BodyStream and data relating to this phase lives in StreamInfo.
////
////		 *  \note This class has public internals because it is basically a glorified struct!
////
////		 */
//
//	class StreamInformation {
//
////		class StreamInfo : public RefCount<StreamInfo>
////
////		{
////
////		public:
////
////			bool Active;											//!< True if active - set false once finished
//
//		public boolean active;
//
////			BodyStreamPtr Stream;									//!< The stream in question
//
//		public BodyStream stream;
//
////			Length StopAfter;										//!< Number of edit units to output (or zero for no limit). Decremented each time data is written (unless zero).
////
//		public @MXFLength long stopAfter;
////
////		public:
////
////			//! Construct an "empty" StreamInfo
////
////			StreamInfo() { Active = false; }
////
//		public StreamInformation() {
//
//			active = false;
//		}
////
////			//! Copy constructor
////
////			StreamInfo(const StreamInfo &rhs)
////
////			{
////
////				Active = rhs.Active;
////
////				Stream = rhs.Stream;
////
////				StopAfter = rhs.StopAfter;
////
////			}
////
//			public StreamInformation(
//					StreamInformation original) {
//
//				this.active = original.active;
//				this.stream = original.stream;
//				this.stopAfter = original.stopAfter;
//			}
//	}
////
////		//! Type for list of streams to write
////
////		/*! The list is kept in the order that the BodySIDs are added
////
////		 */
////
////		//! Destination file
////
////		MXFFilePtr File;
////
//	private MXFFileImpl file;
////
////		//! List of streams to write
////
////		StreamInfoList StreamList;
////
//	private List<StreamInformation> streamList;
////
////		//! KLV Alignment Grid to use
////
////		UInt32 KAG;
////
//	private @UInt32 int kag = 0;
////
////		//! Flag set if BER lengths should be forced to 4-byte (where possible)
////
////		bool ForceBER4;
////
//	private boolean forceBER4 = false;
////
////		//! Partition pack to use when one is required
////
////		PartitionPtr BasePartition;
////
//	private PartitionImpl basePartition;
////
////		BodyWriterHandlerPtr PartitionHandler;					//!< The body partition handler
////
//	private BodyWriterHandler partitionHandler;
////
////		UInt32 MinPartitionSize;								//!< The minimum size of the non-essence part of the next partition
//
//	private @UInt32 int minParitionSize = 0;
//
////		UInt32 MinPartitionFiller;								//!< The minimum size of filler before the essence part of the next partition
////
//	private @UInt32 int minPartitionFiller = 0;
////
////		bool IndexSharesWithMetadata;							//!< If true index tables may exist in the same partition as metadata
//
//	private boolean indexSharesWithMetadata = true;
//
////		bool EssenceSharesWithMetadata;							//!< If true essence may exist in the same partition as metadata
////
//	private boolean essenceSharesWithMetadata = false;
////
////		//! The current BodySID, or 0 if not known (will move back to the start of the list)
////
////		UInt32 CurrentBodySID;
////
//	private @UInt32 int currentBodySID = 0;
////
////		//! The current partition is done and must not be continued - any new data must start a new partition
////
////		bool PartitionDone;
////
//	private boolean partitionDone = false;
////
////		//! Iterator for the current (or previous) stream data. Only valid if CurrentBodySID != 0
////
////		StreamInfoList::iterator CurrentStream;
////
//// TODO
////
////
////
////		/* Details about the pending partition, set but not yet written
////
////		 * This is because the value of BodySID depends on whether any
////
////		 * essence is going to be written in this partition which won't
////
////		 * be known for certain until we are about to write the essence
////
////		 */
////
////
////
////		//! Flag set when a partition pack is ready to be written
////
////		bool PartitionWritePending;
////
//	private boolean partitionWritePendiong = false;
////
////		//! Is the pending metadata a header?
////
////		bool PendingHeader;
////
//	private boolean pendingHeader = false;
////
////		//! Is the pending metadata a footer?
////
////		bool PendingFooter;
////
//	private boolean pendingFooter = false;
////
////		//! Is the next partition write going to have metadata?
////
////		bool PendingMetadata;
////
//	private boolean pendingMetadata = false;
////
////		//! Pointer to a chunk of index table data for the pendinf partition or NULL if none is required
////
////		DataChunkPtr PendingIndexData;
////
//	private ByteBuffer pendingIndexData;
////
////		//! BodySID of the essence or index data already written or pending for this partition
////
////		/*! This is used to determine if particular essence can be added to this partition.
////
////		 *  Set to zero if none yet written.
////
////		 */
////
////		UInt32 PartitionBodySID;
////
//	private @UInt32 int partitionBodySID = 0;
////
////		//! Prevent NULL construction
////
////		BodyWriter();
////
//	private BodyWriter() { }
////
////
////	public:
////
////		//! Construct a body writer for a specified file
////
////		BodyWriter(MXFFilePtr &DestFile)
////
////		{
////
////			State = BodyStateStart;
////
////			CurrentBodySID = 0;
////
////			PartitionDone = false;
////
////
////
////			File = DestFile;
////
////
////
////			// By default index tables may share with metadata, but not essence
////
////			IndexSharesWithMetadata = true;
////
////			EssenceSharesWithMetadata = false;
////
////
////
////			KAG = 0;
////
////			ForceBER4 = false;
////
////
////
////			MinPartitionSize = 0;
////
////			MinPartitionFiller = 0;
////
////
////
////			PartitionWritePending = false;
////
////			PendingHeader = 0;
////
////			PendingFooter = 0;
////
////			PendingMetadata = false;
////
////			PartitionBodySID = 0;
////
////		}
////
//	public BodyWriter(
//			MXFFileImpl file) {
//
//		// TODO check for null?
//		this.file = file;
//	}
////
////		//! Clear any stream details ready to call AddStream()
////
////		/*! This allows previously used streams to be removed before a call to WriteBody() or WriteNext()
////
////		 */
////
////		void ClearStreams(void) { StreamList.clear(); CurrentBodySID = 0; }
////
//	public void clearStreams() {
//
//		streamList.clear();
//		currentBodySID = 0;
//	}
////
////		//! Add a stream to the list of those to write
////
////		/*! \param Stream - The stream to write
////
////		 *  \param StopAfter - If > 0 the writer will stop writing this stream at the earliest opportunity after (at least) this number of edit units have been written
////
////		 *  Streams will be written in the order that they were offered and the list is kept in this order.
////
////		 *	\return false if unable to add this stream (for example this BodySID already in use)
////
////		 */
////
////		bool AddStream(BodyStreamPtr &Stream, Length StopAfter = 0);
////
//	public boolean addStream(
//			BodyStream stream,
//			@MXFLength long stopAfter) {
//
//		// TODO
//		return false;
//	}
//
//	public boolean addStream(
//			BodyStream stream) {
//
//		return addStream(stream, 0l);
//	}
//
//
////
////		//! Set the KLV Alignment Grid
////
////		void SetKAG(UInt32 NewKAG)
////
////		{
////
////			// TODO: This is probably not the best way - but is the only way to currently ensure correct CBR indexing!
////
////			if(StreamList.size()) warning("KAG size changed after adding streams - CBR indexing may be incorrect\n");
////
////			KAG = NewKAG;
////
////		}
////
//	public void setKAG(
//			@UInt32 int kag)
//		throws IllegalArgumentException {
//
//		if (kag < 0)
//			throw new IllegalArgumentException("Cannot set the kay alignment grid size to a negative value.");
//
//		if (streamList.size() > 0)
//			System.err.println("KAG size changed after adding streams. CBR indexing may be incorrect as a result.");
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
////		//! Set what sort of data may share with header metadata
////
////		void SetMetadataSharing(bool IndexMayShare = true, bool EssenceMayShare = false)
////
////		{
////
////			IndexSharesWithMetadata = IndexMayShare;
////
////			EssenceSharesWithMetadata = EssenceMayShare;
////
////		}
////
//	public void setMetadataSharing(
//			boolean indexMayShare,
//			boolean essenceMayShare) {
//
//		this.indexSharesWithMetadata = indexMayShare;
//		this.essenceSharesWithMetadata = essenceMayShare;
//	}
//
//	public void setMetadataSharing() {
//
//		setMetadataSharing(true, true);
//	}
//
////
////		//! Set the template partition pack to use when partition packs are required
////
////		/*! The byte counts and SIDs will be updated are required before writing.
////
////		 *  FooterPosition will not be updated so it must either be 0 or the correct value.
////
////		 *  Any associated metadata will be written for the header and if the handler (called just before the write) requests it.
////
////		 *  \note The original object given will be modified - not a copy of it
////
////		 */
////
////		void SetPartition(PartitionPtr &ThePartition) { BasePartition = ThePartition; }
////
//	public void setPartition(
//			PartitionImpl partition) {
//
//		this.basePartition = partition;
//	}
////
////		//! Get a pointer to the current template partition pack
////
////		PartitionPtr GetPartition(void) { return BasePartition; }
////
//	public PartitionImpl getPartition() {
//
//		return basePartition;
//	}
////
////		//! Write the file header
////
////		/*! No essence will be written, but CBR index tables will be written if required.
////
////		 *  The partition will not be "ended" if only the header partition is written
////
////		 *  meaning that essence will be added by the next call to WritePartition()
////
////		 */
////
////		void WriteHeader(bool IsClosed, bool IsComplete);
////
//	public void writeHeader(
//			boolean isClosed,
//			boolean isComplete) {
//
//		// TODO
//	}
////
////		//! End the current partition
////
////		/*! Once "ended" no more essence will be added, even if otherwise valid.
////
////		 *  A new partition will be started by the next call to WritePartition()
////
////		 *  \note This function will also flush any pending partition writes
////
////		 */
////
////		void EndPartition(void);
////
//	public void endPartition() {
//
//		// TODO
//	}
////
////		//! Write stream data
////
////		/*! \param Duration If > 0 the stop writing at the earliest opportunity after (at least) this number of edit units have been written for each stream
////
////		 *  \param MaxPartitionSize If > 0 the writer will attempt to keep the partition no larger than this size in bytes. There is no guarantee that it will succeed
////
////		 *  \note Streams that have finished or hit thier own StopAfter value will be regarded as having written enough when judging whether to stop
////
////		 */
////
////		void WriteBody(Length Duration = 0, Length MaxPartitionSize = 0);
////
//	public void writeBody(
//			@MXFLength long duration,
//			@MXFLength long maxPartitionSize) {
//
//		// TODO
//	}
//
//	public void writeBody(
//			@MXFLength long duration) {
//
//		writeBody(duration, 0l);
//	}
//
//	public void writeBody() {
//
//		writeBody(0l, 0l);
//	}
////
////		//! Write the next partition or continue the current one (if not complete)
////
////		/*! Will stop at the point where the next partition will start, or (if Duration > 0) at the earliest opportunity after (at least) Duration edit units have been written
////
////		 */
////
////		Length WritePartition(Length Duration = 0, Length MaxPartitionSize = 0);
////
//	public @MXFLength long writePartition(
//			@MXFLength long duration,
//			@MXFLength long maxPartitionSize) {
//
//		// TODO
//		return 0l;
//	}
//
//	public @MXFLength long writePartition(
//			@MXFLength long duration) {
//
//		return writePartition(duration, 0l);
//	}
//
//	public @MXFLength long writePartition() {
//
//		return writePartition(0l, 0l);
//	}
//
//
////
////		//! Determine if all body partitions have been written
////
////		/*! Will be false until after the last required WritePartition() call
////
////		 */
////
////		bool BodyDone(void) { return (State == BodyStateFooter) || (State == BodyStateDone); }
////
//	public boolean bodyDone() {
//
//		return ((state == BodyState.Footer) || (state == BodyState.Done));
//	}
////
////		//! Write the file footer
////
////		/*! No essence will be written, but index tables will be written if required.
////
////		 */
////
////		void WriteFooter(bool WriteMetadata = false, bool IsComplete = true);
////
//	public void writeFooter(
//			boolean writeMetadata,
//			boolean isComplete) {
//
//		// TODO
//	}
//
//	public void writeFooter(
//			boolean writeMetadata) {
//
//		writeFooter(writeMetadata, true);
//	}
//
//	public void writeFooter() {
//
//		writeFooter(false, true);
//	}
////
////		//! Set a handler to be called before writing a partition pack within the body
////
////		/*! Will be called before a body partition is written
////
////		 */
////
////		void SetPartitionHandler(BodyWriterHandlerPtr &NewBodyHandler) { PartitionHandler = NewBodyHandler; }
////
//	public void setPartitionHandler(
//			BodyWriterHandler bodyHandler) {
//
//		partitionHandler = bodyHandler;
//	}
//
//
////
////		//! Set the minumum size of the non-essence part of the next partition
////
////		/*! This will cause a filler KLV to be added (if required) after the partition pack, any header metadata and index table segments
////
////		 *  in order to reach the specified size.  This is useful for reserving space for future metadata updates.
////
////		 *  This value is read after calling the partition handlers so this function may safely be used in the handlers.
////
////		 *  \note The size used will be the minimum size that satisfies the following:
////
////		 *  - All required items are included (partition pack, metadata if required, index if required)
////
////		 *  - The total size, excluding essence, is at least as big as the value specified by SetPartitionSize()
////
////		 *  - The filler following the last non-essence item is at least as big as the value specified by SetPartitionFiller()
////
////		 *  - The KAGSize value is obeyed
////
////		 */
////
////		void SetPartitionSize(UInt32 PartitionSize) { MinPartitionSize = PartitionSize; }
////
//	public void setPartitionSize(
//			@UInt32 int partitionSize)
//		throws IllegalArgumentException {
//
//		if (partitionSize < 0)
//			throw new IllegalArgumentException("Cannot set the partition size to a negative value.");
//
//		this.minParitionSize = partitionSize;
//	}
////
////		//! Set the minumum size of filler between the non-essence part of the next partition and any following essence
////
////		/*! If non-zero this will cause a filler KLV to be added after the partition pack, any header metadata and index table segments
////
////		 *  of at least the size specified.  This is useful for reserving space for future metadata updates.
////
////		 *  This value is read after calling the partition handlers so this function may safely be used in the handlers.
////
////		 *  \note The size used will be the minimum size that satisfies the following:
////
////		 *  - All required items are included (partition pack, metadata if required, index if required)
////
////		 *  - The total size, excluding essence, is at least as big as the value specified by SetPartitionSize()
////
////		 *  - The filler following the last non-essence item is at least as big as the value specified by SetPartitionFiller()
////
////		 *  - The KAGSize value is obeyed
////
////		 */
////
////		void SetPartitionFiller(UInt32 PartitionFiller) { MinPartitionFiller = PartitionFiller; }
////
//	public void setPartitionFiller(
//			@UInt32 int partitionFiller)
//		throws IllegalArgumentException {
//
//		if (partitionFiller < 0)
//			throw new IllegalArgumentException("Cannot set the minimum partition filler using a negative value.");
//
//		this.minPartitionFiller = partitionFiller;
//	}
////
////		//! Initialize all required index managers
////
////		void InitIndexManagers(void);
////
//	public void initializeIndexManagers() {
//
//		// TODO
//	}
////
////	protected:
////
////		//! Move to the next active stream (will also advance State as required)
////
////		/*! \note Will set CurrentBodySID to 0 if no more active streams
////
////		 */
////
////		void SetNextStream(void);
////
//	void setNextStream() {
//
//		// TODO
//	}
////
////		//! Write a complete partition's worth of essence
////
////		/*! Will stop if:
////
////		 *    Frame or "other" wrapping and the "StopAfter" reaches zero or "Duration" reaches zero
////
////		 *    Clip wrapping and the entire clip is wrapped
////
////		 */
////
////		Length WriteEssence(StreamInfoPtr &Info, Length Duration = 0, Length MaxPartitionSize = 0);
//
//	@MXFLength long writeEssence(
//			StreamInformation information,
//			@MXFLength long duration,
//			@MXFLength long maxPartitionSize) {
//
//		// TODO
//		return 0l;
//	}
//
//	@MXFLength long writeEssence(
//			StreamInformation information,
//			@MXFLength long duration) {
//
//		return writeEssence(information, duration, 0l);
//	}
//
//	@MXFLength long writeEssence(
//			StreamInformation information) {
//
//		return writeEssence(information, 0l, 0l);
//	}
//
//	public abstract class BodyWriterHandler {
//
////		public:
////
////			//! Virtual destructor to allow polymorphism
////
////			virtual ~BodyWriterHandler();
////
////
////
////			//! Handler called before writing a partition pack
////
////			/*! \param Caller - A pointer to the calling BodyWriter
////
////			 *  \param BodySID - The Stream ID of the essence in this partition (0 if none)
////
////			 *  \param IndexSID - The Stream ID of the index data in this partition (0 if none)
////
////			 *  \note If metadata is to be written the partition type must be set accordingly by the handler - otherwise closed and complete will be used
////
////			 *  \note If metadata is requested but the partition will contain index or essence data that is not permitted to share a partition
////
////			 *        with metadata an extra partition pack will be written with no metadata after writing the metadata
////
////			 *  \return true if metadata should be written with this partition pack
////
////			 */
////
////			virtual bool HandlePartition(BodyWriterPtr Caller, UInt32 BodySID, UInt32 IndexSID) = 0;
////
//		public abstract boolean handlePartition(
//				BodyWriter caller,
//				@UInt32 int bodySID,
//				@UInt32 int indexSID);
//
//	}
//}
