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
// * $Log: EssenceSubParser.java,v $
// * Revision 1.5  2010/01/19 14:44:23  vizigoth
// * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
// *
// * Revision 1.4  2009/05/14 16:15:24  vizigoth
// * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
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
//import java.io.File;
//import java.nio.ByteBuffer;
//import java.util.List;
//
//import tv.amwa.maj.embeddable.RationalImpl;
//import tv.amwa.maj.entity.EssenceDescriptorImpl;
//import tv.amwa.maj.integer.Int64;
//import tv.amwa.maj.integer.UInt32;
//import tv.amwa.maj.integer.UInt64;
//import tv.amwa.maj.integer.UInt8;
//import tv.amwa.maj.io.mxf.impl.MXFFileImpl;
//
///**
// * <p>Base class for all essence parsers.</p>
// *
// *
// *
// */
//public abstract class EssenceSubParser {
//
//	// protected:
//
//		//! The wrapping options selected
//
//		// WrappingOptionPtr SelectedWrapping;
//
//	private WrappingOption selectedWrapping = null;
//
//		//! The index manager in use
//
//		// IndexManagerPtr Manager;
//
////	private IndexManager manager = null;
//
//		//! This essence stream's stream ID in the index manager
//
//		// int	ManagedStreamID;
//
//	private int managedStreamID;
//
//	public abstract class SubParserEssenceSource
//		extends EssenceSource {
//
////	protected:
////
////		EssenceSubParserPtr Caller;
//
//		private EssenceSubParser caller;
//
////		FileHandle File;
//
//		private File file;
//
////		UInt32 Stream;
//
//		private @UInt32 int stream;
////
////		UInt64 RequestedCount;
//
//		private @UInt64 long requestedCount;
//
////		IndexTablePtr Index;
//
//		private IndexTable index;
//
////		DataChunkPtr RemainingData;
//
//		private ByteBuffer remainingData;
//
////		bool AtEndOfData;
//
//		private boolean atEndOfData = false;
//
////		bool Started;
////
//		private boolean started = false;
////
////	public:
////
////		//! Construct and initialise for essence parsing/sourcing
////
////		ESP_EssenceSource(EssenceSubParserPtr TheCaller, FileHandle InFile, UInt32 UseStream, UInt64 Count = 1)
////
////		{
////
////			Caller = TheCaller;
////
////			File = InFile;
////
////			Stream = UseStream;
////
////			RequestedCount = Count;
////
////			AtEndOfData = false;
////
////			Started = false;
////
////		};
////
//		public SubParserEssenceSource(
//				EssenceSubParser caller,
//				File file,
//				@UInt32 int stream,
//				@UInt64 long count) {
//
//			this.caller = caller;
//			this.file = file;
//			this.stream = stream;
//			this.requestedCount = count;
//		}
////
////		//! Get the next "installment" of essence data
////
////		/*! This will attempt to return an entire wrapping unit (e.g. a full frame for frame-wrapping) but will return it in
////
////		 *  smaller chunks if this would break the MaxSize limit. If a Size is specified then the chunk returned will end at
////
////		 *  the first wrapping unit end encountered before Size. On no account will portions of two or more different wrapping
////
////		 *  units be returned together. The mechanism for selecting a type of wrapping (e.g. frame, line or clip) is not
////
////		 *  (currently) part of the common EssenceSource interface.
////
////		 *  \return Pointer to a data chunk holding the next data or a NULL pointer when no more remains
////
////		 *	\note If there is more data to come but it is not currently available the return value will be a pointer to an empty data chunk
////
////		 *	\note If Size = 0 the object will decide the size of the chunk to return
////
////		 *	\note On no account will the returned chunk be larger than MaxSize (if MaxSize > 0)
////
////		 */
////
////		virtual DataChunkPtr GetEssenceData(size_t Size = 0, size_t MaxSize = 0) { return BaseGetEssenceData(Size, MaxSize); };
////
//		@Override
//		public ByteBuffer getEssenceData(
//				int size,
//				int maxSize) {
//
//			return baseGetEssenceData(size, maxSize);
//		}
////
////		//! Non-virtual basic version of GetEssenceData() that can be called by derived classes
////
////		/*! DRAGONS: This implementation always reads whole wrapping units, so it NOT SAFE if these could be too large to fit in memory
////
////		 */
////
//
////		DataChunkPtr BaseGetEssenceData(size_t Size = 0, size_t MaxSize = 0)
////
////		{
////
////			// Allow us to differentiate the first call
////
////			if(!Started) Started = true;
////
////
////
////			DataChunkPtr Data;
////
////
////
////			if(RemainingData)
////
////			{
////
////				Data = RemainingData;
////
////				RemainingData = NULL;
////
////			}
////
////			else
////
////			{
////
////				Data = Caller->Read(File, Stream, 1);
////
////			}
////
////			if(Data)
////
////			{
////
////				if(Data->Size == 0) Data = NULL;
////
////				else
////
////				{
////
////					if((MaxSize) && (Data->Size > MaxSize))
////
////					{
////
////						RemainingData = new DataChunk(Data->Size - MaxSize, &Data->Data[MaxSize]);
////
////						Data->Resize((UInt32)MaxSize);
////
////					}
////
////				}
////
////			}
////
////
////
////			// Record when we hit the end of all data
////
////			if(!Data) AtEndOfData = true;
////
////
////
////			return Data;
////
////		}
////
//		public ByteBuffer baseGetEssenceData(
//				int size,
//				int maxSize) {
//
//			if (!started)
//				started = true;
//
//			ByteBuffer data;
//
//			if (remainingData != null) {
//				data = remainingData;
//				remainingData = null;
//			}
//
//			// TODO finish this
//
//			return null;
//
//		}
////
////		//! Did the last call to GetEssenceData() return the end of a wrapping item
////
////		/*! \return true if the last call to GetEssenceData() returned an entire wrapping unit.
////
////		 *  \return true if the last call to GetEssenceData() returned the last chunk of a wrapping unit.
////
////		 *  \return true if the last call to GetEssenceData() returned the end of a clip-wrapped clip.
////
////		 *  \return false if there is more data pending for the current wrapping unit.
////
////		 *  \return false if the source is to be clip-wrapped and there is more data pending for the clip
////
////		 */
////
////		virtual bool EndOfItem(void)
////
////		{
////
////			// If we are clip wrapping then we only end when no more data
////
////			if(Caller->GetWrapType() == WrappingOption::Clip) return AtEndOfData;
////
////
////
////			// Otherwise items end when there is no data remaining from the last read
////
////			return !RemainingData;
////
////		}
////
//		@Override
//		public boolean endOfItem() {
//
//			// If we are clip wrapping then we only end when no more data
//			if (caller.getWrapType() == WrappingOption.WrapType.Clip)
//				return atEndOfData;
//
//			// Otherwise items end when there is no data remaining from the last read
//			// TODO work out what to return here
//			//return !remainingData;
//			return false;
//		}
////
////		//! Is all data exhasted?
////
////		/*! \return true if a call to GetEssenceData() will return some valid essence data
////
////		 */
////
////		virtual bool EndOfData(void) { return AtEndOfData; }
////
//		@Override
//		public boolean endOfData() {
//
//			return atEndOfData;
//		}
////
////		//! Get the GCEssenceType to use when wrapping this essence in a Generic Container
////
////		virtual UInt8 GetGCEssenceType(void) { return Caller->GetGCEssenceType(); }
////
//		@Override
//		public @UInt8 byte getGenericContainerEssenceType() {
//
//			return caller.getGenericContainerEssenceType();
//		}
////
////		//! Get the GCEssenceType to use when wrapping this essence in a Generic Container
////
////		virtual UInt8 GetGCElementType(void) { return Caller->GetGCElementType(); }
////
//		@Override
//		public @UInt8 byte getGenericContainerElementType() {
//
//			return caller.getGenericContainerElementType();
//		}
////
////		//! Is the last data read the start of an edit point?
////
////		virtual bool IsEditPoint(void) { return true; }
////
//		@Override
//		public boolean isEditPoint() {
//
//			return true;
//		}
////
////		//! Get the edit rate of this wrapping of the essence
////
////		/*! \note This may not be the same as the original "native" edit rate of the
////
////		 *        essence if this EssenceSource is wrapping to a different edit rate
////
////		 */
////
////		virtual Rational GetEditRate(void) { return Caller->GetEditRate(); }
////
//		@Override
//		public RationalImpl getEditRate() {
//
//			return caller.getEditRate();
//		}
////
////		//! Get the current position in GetEditRate() sized edit units
////
////		/*! This is relative to the start of the stream, so the first edit unit is always 0.
////
////		 *  This is the same as the number of edit units read so far, so when the essence is
////
////		 *  exhausted the value returned shall be the size of the essence
////
////		 */
////
////		virtual Position GetCurrentPosition(void) { return Caller->GetCurrentPosition(); }
//
//		@Override
//		public @MXFPosition long getCurrentPosition() {
//
//			return caller.getCurrentPosition();
//		}
////
////
////		//! Set a parser specific option
////
////		/*! \return true if the option was successfully set */
////
////		virtual bool SetOption(std::string Option, Int64 Param = 0) { return Caller->SetOption(Option, Param); } ;
////
//		@Override
//		public boolean setOption(
//				String option,
//				@Int64 long parameter) {
//
//			return caller.setOption(option, parameter);
//		}
////
////		//! Get BytesPerEditUnit if Constant, else 0
////
////		/*! \note This value may be useful even if CanIndex() returns false
////
////		 */
////
////		virtual UInt32 GetBytesPerEditUnit(UInt32 KAGSize = 1) { return Caller->GetBytesPerEditUnit(KAGSize); }
//
//		@Override
//		public @UInt32 int getBytesPerEditUnit(
//				@UInt32 int kagSize) {
//
//			return caller.getBytesPerEditUnit(kagSize);
//		}
////
////
////		//! Can this stream provide indexing
////
////		/*! If true then SetIndex Manager can be used to set the index manager that will receive indexing data
////
////		 */
////
////		virtual bool CanIndex() { return Caller->SelectedWrapping->CanIndex; }
////
//		@Override
//		public boolean canIndex() {
//
//			// TODO
//			return false;
//		}
////
////		//! Set the index manager to use for building index tables for this essence
////
////		virtual void SetIndexManager(IndexManagerPtr &Manager, int StreamID)
////
////		{
////
////			// Set the manager in our containing parser
////
////			Caller->SetIndexManager(Manager, StreamID);
////
////		}
////		public void setIndexManager(
////				IndexManager manager,
////				int streamID) {
////
////			caller.setIndexManager(manager, streamID);
////		}
////
////	}
//
////	public:
////
//
////		//! Report the extensions of files this sub-parser is likely to handle
////
////		virtual StringList HandledExtensions(void) { StringList Ret; return Ret; };
////
//	public List<String> handledExtensions() {
//
//		// TODO
//		return null;
//	}
////
////		//! Examine the open file and return a list of essence descriptors
////
////		/*! This function should fail as fast as possible if the essence if not identifyable by this object
////
////		 *	\return A list of EssenceStreamDescriptors where each essence stream identified in the input file has
////
////		 *			an identifier (to allow it to be referenced later) and an MXF File Descriptor
////
////		 */
////
////		virtual EssenceStreamDescriptorList IdentifyEssence(FileHandle InFile)
////
////		{
////
////			EssenceStreamDescriptorList Ret;
////
////			return Ret;
////
////		}
////
//	public List<EssenceStreamDescriptor> identifyEssence(
//			File file) {
//
//		// TODO
//		return null;
//
//	}
////
////		//! Examine the open file and return the wrapping options known by this parser
////
////		/*! \param InFile The open file to examine (if the descriptor does not contain enough info)
////
////		 *	\param Descriptor An essence stream descriptor (as produced by function IdentifyEssence)
////
////		 *		   of the essence stream requiring wrapping
////
////		 *	\note The options should be returned in an order of preference as the caller is likely to use the first that it can support
////
////		 */
////
////		virtual WrappingOptionList IdentifyWrappingOptions(FileHandle InFile, EssenceStreamDescriptor &Descriptor)
////
////		{
////
////			WrappingOptionList Ret;
////
////			return Ret;
////
////		}
////
//	public List<WrappingOption> identifyWrappingOptions(
//			File file,
//			EssenceStreamDescriptor descriptor) {
//
//		// TODO
//		return null;
//	}
////
////		//! Set a wrapping option for future Read and Write calls
////
////		virtual void Use(UInt32 Stream, WrappingOptionPtr &UseWrapping)
////
////		{
////
////			// DRAGONS: Any derived version of Use() must also set SelectedWrapping
////
////			SelectedWrapping = UseWrapping;
////
////		}
////
//	public void useWrappingOption(
//			@UInt32 int stream,
//			WrappingOption wrapping) {
//
//		// TODO why is the stream required?
//		selectedWrapping = wrapping;
//	}
//
////
////		//! Does this essence parser support ReValidate()
////
////		virtual bool CanReValidate(void) { return false; }
////
//	public boolean canReValidate() {
//
//		return false;
//	}
////
////		//! Quickly validate that the given (open) file can be wrapped as specified
////
////		/*! Providing that the given essence descriptor and wrapping option can be used this will leave the parser
////
////		 *  in the state it would have been in after calls to IdentifyEssence(), IdentifyWrappingOptions() and Use().
////
////		 *  This is used when parsing a list of files to check that the second and later files are the same format as the first
////
////		 *  \return true if all OK
////
////		 */
////
////		virtual bool ReValidate(FileHandle Infile, UInt32 Stream, MDObjectPtr &Descriptor, WrappingOptionPtr &UseWrapping)
////
////		{
////
////			return false;
////
////		}
////
//	public boolean reValidate(
//			File file,
//			@UInt32 int stream,
//			EssenceDescriptorImpl descriptor,
//			WrappingOption wrapping) {
//
//		return false;
//	}
////
////		//! Get the wrapping type that has been selected by Use()
////
////		WrappingOption::WrapType GetWrapType(void)
////
////		{
////
////			if(!SelectedWrapping) return WrappingOption::None;
////
////
////
////			return SelectedWrapping->ThisWrapType;
////
////		}
//
//	public WrappingOption.WrapType getWrapType() {
//
//		if (selectedWrapping == null)
//			return WrappingOption.WrapType.None;
//
//		return selectedWrapping.getWrapType();
//	}
////
////
////
////		//! Set a non-native edit rate
////
////		/*! \return true if this rate is acceptable */
////
////		virtual bool SetEditRate(Rational EditRate)
////
////		{
////
////			// Default action is to not allow the edit rate to be changed
////
////			return (EditRate == GetEditRate());
////
////		}
////
//	public boolean setEditRate(
//			RationalImpl editRate) {
//
//		return (editRate == getEditRate());
//	}
////
////		//! Get the current edit rate
////
////		virtual Rational GetEditRate(void) = 0;
////
//	public abstract RationalImpl getEditRate();
////
////		//! Get the preferred edit rate (if one is known)
////
////		/*! \return The prefered edit rate or 0/0 if note known
////
////		 */
////
////		virtual Rational GetPreferredEditRate(void)
////
////		{
////
////			// By default we don't know the preferred rate
////
////			return Rational(0,0);
////
////		}
////
//	public RationalImpl getPreferredEditRate() {
//
//		return new RationalImpl(0, 0);
//	}
////
////		//! Get BytesPerEditUnit, if Constant
////
////		/*! Note that we use KAGSize to prevent compiler warnings (we cannot omit it as it has a default value) */
////
////		virtual UInt32 GetBytesPerEditUnit(UInt32 KAGSize = 1) { return KAGSize * 0; }
////
//	public @UInt32 int getBytesPerEditUnit(
//			@UInt32 int kagSize) {
//
//		// TODO check this!
//		return kagSize * 0;
//	}
//
//	public @UInt32 int getBytesPerEditUnit() {
//
//		return getBytesPerEditUnit(1);
//	}
//
////
////		//! Get the current position in SetEditRate() sized edit units
////
////		/*! This is relative to the start of the stream, so the first edit unit is always 0.
////
////		 *  This is the same as the number of edit units read so far, so when the essence is
////
////		 *  exhausted the value returned shall be the size of the essence
////
////		 */
////
////		virtual Position GetCurrentPosition(void) = 0;
////
//	public abstract @MXFPosition long getCurrentPosition();
////
////		//! Set the IndexManager for this essence stream (and the stream ID if we are not the main stream)
////
////		virtual void SetIndexManager(IndexManagerPtr &TheManager, int StreamID = 0)
////
////		{
////
////			Manager = TheManager;
////
////			ManagedStreamID = StreamID;
////
////		}
////
//	public void setIndexManager(
//			IndexManager manager,
//			int streamID) {
//
//		this.manager = manager;
//		this.managedStreamID = streamID;
//	}
//
//	public void setIndexManager(
//			IndexManager manager) {
//
//		setIndexManager(manager, 0);
//	}
//
////
////		//! Get the IndexManager for this essence stream
////
////		virtual IndexManagerPtr &GetIndexManager(void) { return Manager; };
////
//	public IndexManager getIndexManager() {
//
//		return manager;
//	}
////
////		//! Get the IndexManager StreamID for this essence stream
////
////		virtual int GetIndexStreamID(void) { return ManagedStreamID; };
////
//	public int getIndexStreamID() {
//
//		return managedStreamID;
//	}
////
////		//! Set the stream offset for a specified edit unit into the current index manager
////
////		virtual void SetStreamOffset(Position EditUnit, UInt64 Offset)
////
////		{
////
////			if(Manager) Manager->SetOffset(ManagedStreamID, EditUnit, Offset);
////
////		}
////
//	public void setStreamOffset(
//			@MXFPosition long editUnit,
//			@UInt64 long offset) {
//
//		if (manager != null)
//			manager.setOffset(managedStreamID, editUnit, offset);
//	}
////
////		//! Offer the stream offset for a specified edit unit to the current index manager
////
////		virtual bool OfferStreamOffset(Position EditUnit, UInt64 Offset)
////
////		{
////
////			if(!Manager) return false;
////
////			return Manager->OfferOffset(ManagedStreamID, EditUnit, Offset);
////
////		}
////
//	public boolean offerStreamOffset(
//			@MXFPosition long editUnit,
//			@UInt64 long offset) {
//
//		if (manager == null)
//			return false;
//
//		return manager.offerOffset(managedStreamID, editUnit, offset);
//	}
////
////		//! Instruct index manager to accept the next edit unit
////
////		virtual void IndexNext(void)
////
////		{
////
////			if(Manager) Manager->AcceptNext();
////
////		}
////
//	public void indexNext() {
//
//		if (manager != null)
//			manager.acceptNext();
//	}
////
////		//! Instruct index manager to accept and log the next edit unit
////
////		virtual int IndexLogNext(void)
////
////		{
////
////			if(Manager) return Manager->AcceptLogNext();
////
////			return -1;
////
////		}
////
//	public int indexLogNext() {
//
//		if (manager != null)
//			return manager.acceptLogNext();
//
//		return -1;
//	}
////
////		//! Instruct index manager to log the next edit unit
////
////		virtual int LogNext(void)
////
////		{
////
////			if(Manager) return Manager->LogNext();
////
////			return -1;
////
////		}
////
//	public int logNext() {
//
//		if (manager != null)
//			return manager.logNext();
//
//		return -1;
//	}
////
////		//! Read an edit unit from the index manager's log
////
////		virtual Position ReadLog(int LogID)
////
////		{
////
////			if(Manager) return Manager->ReadLog(LogID);
////
////			return IndexTable::IndexLowest;
////
////		}
////
//	public @MXFPosition long readLog(
//			int logID) {
//
//		if (manager != null)
//			return manager.readLog(logID);
//
//		return IndexTable.indexLowest;
//	}
////
////		//! Instruct index manager to accept provisional entry
////
////		/*! \return The edit unit of the entry accepted - or IndexLowest if none available */
////
////		virtual Position AcceptProvisional(void)
////
////		{
////
////			if(Manager) return Manager->AcceptProvisional();
////
////			return IndexTable::IndexLowest;
////
////		}
////
//	public @MXFPosition long acceptProvisional() {
//
//		if (manager != null)
//			return manager.accecptProvisional();
//
//		return IndexTable.indexLowest;
//	}
////
////		//! Read the edit unit of the last entry added via the index manager (or IndexLowest if none added)
////
////		Position GetLastNewEditUnit(void)
////
////		{
////
////			if(Manager) return Manager->GetLastNewEditUnit();
////
////			return IndexTable::IndexLowest;
////
////		}
////
//	public @MXFPosition long getLastNewEditUnit() {
//
//		if (manager != null)
//			return manager.getLastNewEditUnit();
//
//		return IndexTable.indexLowest;
//	}
////
////		//! Get the GCEssenceType to use when wrapping this essence in a Generic Container
////
////		virtual UInt8 GetGCEssenceType(void) { return SelectedWrapping->GCEssenceType; }
//
//	public @UInt8 byte getGenericContainerEssenceType() {
//
//		// TODO
//		return 0;
//	}
////
////		//! Get the GCEssenceType to use when wrapping this essence in a Generic Container
////
////		virtual UInt8 GetGCElementType(void) { return SelectedWrapping->GCElementType; }
////
//	public @UInt8 byte getGenericContainerElementType() {
//
//		// TODO
//		return 0;
//	}
////
////
////
////		//! Read a number of wrapping items from the specified stream and return them in a data chunk
////
////		/*! If frame or line mapping is used the parameter Count is used to
////
////		 *	determine how many items are read. In frame wrapping it is in
////
////		 *	units of EditRate, as specified in the call to Use(), which may
////
////		 *  not be the frame rate of this essence
////
////		 *	\note This is going to take a lot of memory in clip wrapping!
////
////		 */
////
////		virtual DataChunkPtr Read(FileHandle InFile, UInt32 Stream, UInt64 Count = 1) = 0;
////
//	public abstract ByteBuffer read(
//			File file,
//			@UInt32 int stream,
//			@UInt64 long count);
//
//	public ByteBuffer read(
//			File file,
//			@UInt32 int stream) {
//
//		return read(file, stream, 1);
//	}
////
////		//! Build an EssenceSource to read a number of wrapping items from the specified stream
////
////		virtual EssenceSourcePtr GetEssenceSource(FileHandle InFile, UInt32 Stream, UInt64 Count = 1) = 0;
////
//	public abstract EssenceSource getEssenceSource(
//			File file,
//			@UInt32 int stream,
//			@UInt64 long count);
//
//	public EssenceSource getEssenceSource(
//			File file,
//			@UInt32 int stream) {
//
//		return getEssenceSource(file, stream, 1);
//	}
////
////		//! Write a number of wrapping items from the specified stream to an MXF file
////
////		/*! If frame or line mapping is used the parameter Count is used to
////
////		 *	determine how many items are read. In frame wrapping it is in
////
////		 *	units of EditRate, as specified in the call to Use(), which may
////
////		 *  not be the frame rate of this essence stream
////
////		 *	\note This is the only safe option for clip wrapping
////
////		 *	\return Count of bytes transferred
////
////		 */
////
////		virtual Length Write(FileHandle InFile, UInt32 Stream, MXFFilePtr OutFile, UInt64 Count = 1) = 0;
////
//	public abstract @MXFLength long write(
//			File inFile,
//			@UInt32 int stream,
//			MXFFileImpl outFile,
//			@UInt64 long count);
//
//	public @MXFLength long write(
//			File inFile,
//			@UInt32 int stream,
//			MXFFileImpl outFile) {
//
//		return write(inFile, stream, outFile, 1);
//	}
////
////		//! Set a parser specific option
////
////		/*! \return true if the option was successfully set */
////
////		virtual bool SetOption(std::string Option, Int64 Param = 0) { return false; } ;
////
//	public boolean setOption(
//			String option,
//			@Int64 long param) {
//
//		return false;
//	}
//
//	public boolean setOption(
//			String option) {
//
//		return false;
//	}
//
////
////		//! Get a unique name for this sub-parser
////
////		/*! The name must be all lower case, and must be unique.
////
////		 *  The recommended name is the part of the filename of the parser header after "esp_" and before the ".h".
////
////		 *  If the parser has no name return "" (however this will prevent named wrapping option selection for this sub-parser)
////
////		 */
////
////		virtual std::string GetParserName(void) const { return ""; }
////
//	public String getParserName() {
//
//		return "";
//	}
////
////
////
////		//! Build a new sub-parser of the appropriate type
////
////		/*! \note You must redifine this function in a sub-parser even if it is not going to be its own factory (EssenceSubParserFactory used).
////
////		 *        In this case it is best to call the factory's NewParser() method.
////
////		 */
////
////		virtual EssenceSubParserPtr NewParser(void) const = 0;
////
//	public abstract EssenceSubParser createParser();
//}
