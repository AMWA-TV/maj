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
// * $Log: FileParser.java,v $
// * Revision 1.5  2010/01/19 14:44:22  vizigoth
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
//import java.util.Map;
//
//import tv.amwa.maj.embeddable.RationalImpl;
//import tv.amwa.maj.entity.EssenceDescriptorImpl;
//import tv.amwa.maj.industry.MetadataObject;
//import tv.amwa.maj.integer.Int64;
//import tv.amwa.maj.integer.UInt32;
//import tv.amwa.maj.integer.UInt8;
//import tv.amwa.maj.io.mxf.EssenceParser.WrappingConfig;
//
///**
// * <p>Parse essence from a list of files.</p>
// *
// *
// *
// */
//public class FileParser
//	extends ListOfFiles {
//
//	class SubStreamInformation {
//
////		UInt32 StreamID;					//!< The ID of this sub-stream
//
//		public @UInt32 int streamID;
//
////					EssenceSourcePtr Source;			//!< The source for the sub-stream data
//
//		public EssenceSource source;
//	}
//
////	protected:
////
////		bool CurrentFileOpen;					//!< True if we have a file open for processing
//
//	private boolean currentFileOpen;
//
////		FileHandle CurrentFile;					//!< The current file being processed
//
//	private File currentFile;
//
////		EssenceSubParserPtr SubParser;			//!< The sub-parser selected for parsing this sourceessence
//
//	private EssenceSubParser subParser;
//
////		UInt32 CurrentStream;					//!< The currently selected stream in the source essence
//
//	private @UInt32 int currentStream;
//
////		MDObjectPtr CurrentDescriptor;			//!< Pointer to the essence descriptor for the currently selected stream
//
//	private EssenceDescriptorImpl currentDescriptor;
//
////		WrappingOptionPtr CurrentWrapping;		//!< The currently selected wrapping options
//
//	private WrappingOption currentWrapping;
//
////		EssenceSourceParent SeqSource;			//!< This parser's sequential source - which perversely owns the parser!
////
//	private EssenceSource sequentialSource;
////
////		DataChunkPtr PendingData;				//!< Any pending data from the main stream held over from a previous file is a sub-stream read caused a change of file
////
//	private ByteBuffer pendingData;
////
//
////
////		SubStreamList SubStreams;				//!< A list of sub-stream sources
////
//	private List<SubStreamInformation> subStreams;
////
////
////
////	public:
////
////		//! Construct a FileParser and optionally set a single source filename pattern
////
////		FileParser(std::string FileName = "") : ListOfFiles(FileName)
////
////		{
////
////			// Let our sequential source know who we are
////
////			SeqSource = new SequentialEssenceSource(this);
////
////
////
////			CurrentFileOpen = false;
////
////		}
////
//	public FileParser(
//			String fileName) {
//
//		super(fileName);
//		sequentialSource = new SequentialEssenceSource(this);
//		currentFileOpen = false;
//	}
//
//	public FileParser() {
//
//		super();
//		sequentialSource = new SequentialEssenceSource(this);
//		currentFileOpen = false;
//
//	}
//
////		//! Identify the essence type in the first file in the set of possible files
////
////		ParserDescriptorListPtr IdentifyEssence(void);
////
//	public Map<EssenceSubParser, List<EssenceStreamDescriptor>> identifyEssence() {
//
//		// TODO
//		return null;
//	}
////
////		//! Produce a list of available wrapping options
////
////		EssenceParser::WrappingConfigList ListWrappingOptions(bool AllowMultiples, ParserDescriptorListPtr PDList, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////		{
////
////			Rational Zero(0,0);
////
////			return ListWrappingOptions(AllowMultiples, PDList, Zero, ForceWrap);
////
////		}
////
//	public List<EssenceParser.WrappingConfig> listWrappingOptions(
//			boolean allowMultiples,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			WrappingOption.WrapType forceWrap) {
//
//		return listWrappingOptions(allowMultiples, parserDescriptorList, new RationalImpl(0, 0), forceWrap);
//	}
//
//	public List<EssenceParser.WrappingConfig> listWrappingOptions(
//			boolean allowMultiples,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList) {
//
//		return listWrappingOptions(allowMultiples, parserDescriptorList, new RationalImpl(0, 0), WrappingOption.WrapType.None);
//	}
//
////
////		//! Produce a list of available wrapping options
////
////		EssenceParser::WrappingConfigList ListWrappingOptions(ParserDescriptorListPtr PDList, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////		{
////
////			Rational Zero(0,0);
////
////			return ListWrappingOptions(false, PDList, Zero, ForceWrap);
////
////		}
////
//	public List<EssenceParser.WrappingConfig> listWrappingOptions(
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			WrappingOption.WrapType forceWrap) {
//
//		return listWrappingOptions(false, parserDescriptorList, new RationalImpl(0, 0), forceWrap);
//	}
//
//	public List<EssenceParser.WrappingConfig> listWrappingOptions(
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList) {
//
//		return listWrappingOptions(false, parserDescriptorList, new RationalImpl(0, 0), WrappingOption.WrapType.None);
//	}
////
////		//! Produce a list of available wrapping options
////
////		EssenceParser::WrappingConfigList ListWrappingOptions(bool AllowMultiples, ParserDescriptorListPtr PDList, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None);
////
//	public List<EssenceParser.WrappingConfig> listWrappingOptions(
//			boolean allowMultiples,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate,
//			WrappingOption.WrapType forceWrap) {
//
//		// TODO
//		return null;
//	}
//
//	public List<EssenceParser.WrappingConfig> listWrappingOptions(
//			boolean allowMultiples,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate) {
//
//		return listWrappingOptions(allowMultiples, parserDescriptorList, forceEditRate, WrappingOption.WrapType.None);
//	}
//
////
////		//! Produce a list of available wrapping options
////
////		EssenceParser::WrappingConfigList ListWrappingOptions(ParserDescriptorListPtr PDList, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////		{
////
////			return ListWrappingOptions(false, PDList, ForceEditRate, ForceWrap);
////
////		}
////
//	public List<EssenceParser.WrappingConfig> listWrappingOptions(
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate,
//			WrappingOption.WrapType forceWrap) {
//
//		return listWrappingOptions(false, parserDescriptorList, forceEditRate, forceWrap);
//	}
//
//	public List<EssenceParser.WrappingConfig> listWrappingOptions(
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate) {
//
//		return listWrappingOptions(false, parserDescriptorList, forceEditRate, WrappingOption.WrapType.None);
//	}
//
////
////		//! Select the best wrapping option without a forced edit rate
////
////		EssenceParser::WrappingConfigPtr SelectWrappingOption(ParserDescriptorListPtr PDList, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////		{
////
////			Rational Zero(0,0);
////
////			return SelectWrappingOption(false, PDList, Zero, ForceWrap);
////
////		}
////
//	public EssenceParser.WrappingConfig selectWrappingOption(
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			WrappingOption.WrapType forceWrap) {
//
//		return selectWrappingOption(false, parserDescriptorList, new RationalImpl(0, 0), forceWrap);
//	}
//
//	public EssenceParser.WrappingConfig selectWrappingOption(
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList) {
//
//		return selectWrappingOption(false, parserDescriptorList, new RationalImpl(0, 0), WrappingOption.WrapType.None);
//	}
//
////
////		//! Select the best wrapping option with a forced edit rate
////
////		EssenceParser::WrappingConfigPtr SelectWrappingOption(ParserDescriptorListPtr PDList, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////		{
////
////			return SelectWrappingOption(false, PDList, ForceEditRate, ForceWrap);
////
////		}
////
//	public EssenceParser.WrappingConfig selectWrappingOption(
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate,
//			WrappingOption.WrapType forceWrap) {
//
//		return selectWrappingOption(false, parserDescriptorList, forceEditRate, forceWrap);
//	}
//
//	public EssenceParser.WrappingConfig selectWrappingOption(
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate) {
//
//		return selectWrappingOption(false, parserDescriptorList, forceEditRate, WrappingOption.WrapType.None);
//	}
//
////
////		//! Select the best wrapping option without a forced edit rate
////
////		EssenceParser::WrappingConfigPtr SelectWrappingOption(bool AllowMultiples, ParserDescriptorListPtr PDList, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////		{
////
////			Rational Zero(0,0);
////
////			return SelectWrappingOption(AllowMultiples, PDList, Zero, ForceWrap);
////
////		}
////
//	public EssenceParser.WrappingConfig selectWrappingOption(
//			boolean allowMultiples,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			WrappingOption.WrapType forceWrap) {
//
//		return selectWrappingOption(allowMultiples, parserDescriptorList, new RationalImpl(0, 0), forceWrap);
//	}
//
//	public EssenceParser.WrappingConfig selectWrappingOption(
//			boolean allowMultiples,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList) {
//
//		return selectWrappingOption(allowMultiples, parserDescriptorList, new RationalImpl(0, 0), WrappingOption.WrapType.None);
//	}
////
////		//! Select the best wrapping option with a forced edit rate
////
////		EssenceParser::WrappingConfigPtr SelectWrappingOption(bool AllowMultiples, ParserDescriptorListPtr PDList, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None);
////
//	public EssenceParser.WrappingConfig selectWrappingOption(
//			boolean allowMultiples,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate,
//			WrappingOption.WrapType forceWrap) {
//
//		// TODO
//		return null;
//	}
//
//	public EssenceParser.WrappingConfig selectWrappingOption(
//			boolean allowMultiples,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate) {
//
//		return selectWrappingOption(allowMultiples, parserDescriptorList, forceEditRate, WrappingOption.WrapType.None);
//	}
//
////
////		//! Select the specified wrapping options
////
////		void SelectWrappingOption(EssenceParser::WrappingConfigPtr Config);
////
//	public void selectWrappingOption(
//			EssenceParser.WrappingConfig wrappingConfig) {
//
//		// TODO
//	}
////
////		//! Set a wrapping option for this essence
////
////		/*! IdentifyEssence() and IdentifyWrappingOptions() must have been called first
////
////		 */
////
////		void Use(UInt32 Stream, WrappingOptionPtr &UseWrapping);
////
//	public void useWrapping(
//			@UInt32 int stream,
//			WrappingOption wrapping) {
//
//		// TODO
//	}
////
////		//! Return the sequential EssenceSource for the main stream (already aquired internally, so no need to use the stream ID)
////
////		EssenceSourcePtr GetEssenceSource(UInt32 Stream);
////
//	public EssenceSource getEssenceSource(
//			@UInt32 int stream) {
//
//		// TODO
//		return null;
//	}
////
////		//! Build an EssenceSource to read from the specified sub-stream
////
////		EssenceSourcePtr GetSubSource(UInt32 Stream);
////
//	public EssenceSource getSubSource(
//			@UInt32 int stream) {
//
//		// TODO
//		return null;
//	}
////
////		//! Open the current file (any new-file handler will already have been called)
////
////		/*! Required for ListOfFiles
////
////		 *  \return true if file open succeeded
////
////		 */
////
////		bool OpenFile(void)
////
////		{
////
////			CurrentFile = FileOpenRead(CurrentFileName.c_str());
////
////			CurrentFileOpen = FileValid(CurrentFile);
////
////			return CurrentFileOpen;
////
////		}
////
//	public boolean openFile() {
//
//		// TODO
//		return false;
//	}
////
////		//! Close the current file
////
////		/*! Required for ListOfFiles */
////
////		void CloseFile(void)
////
////		{
////
////			if(CurrentFileOpen) FileClose(CurrentFile);
////
////			CurrentFileOpen = false;
////
////		}
////
//	public void closeFile() {
//
//		// TODO
//	}
////
////		//! Is the current file open?
////
////		/*! Required for ListOfFiles */
////
////		bool IsFileOpen(void) { return CurrentFileOpen; }
////
//	public boolean isFileOpen() {
//
//		return currentFileOpen;
//	}
////
////	protected:
////
////		//! Set the sequential source to use the EssenceSource from the currently open and identified source file
////
////		/*! \return true if all OK, false if no EssenceSource available
////
////		 */
////
////		bool GetFirstSource(void);
////
//	boolean getFirstSource() {
//
//		// TODO
//		return false;
//	}
////
////		//! Set the sequential source to use an EssenceSource from the next available source file
////
////		/*! \return true if all OK, false if no EssenceSource available
////
////		 */
////
////		bool GetNextSource(void);
////
//	boolean getNextSource() {
//
//		// TODO
//		return false;
//	}
////
////		//! Essence Source that manages a sequence of essence sources from a list of file patterns
//	class SequentialEssenceSource
//		extends EssenceSource {
////
////		protected:
////
////			EssenceSourcePtr CurrentSource;				//!< An EssenceSource for the current source file
//
//		private EssenceSource currentSource;
//
////			FileParserPtr Outer;						//!< The outer file parser which is owned by us to prevent it being released until be are done
//
//		private FileParser outer;
//
////			Length PreviousLength;						//!< The total size of all previously read essence sources for this set
////
//		private @MXFLength long previousLength = 0l;
////
////			//! Option pair for OptionList
////
////			typedef std::pair<std::string, Int64> OptionPair;
////
////
////
////			//! List of all options set for this source
////
////			std::list<OptionPair> OptionList;
////
//		private Map<String, Long> optionList;
////
////			//! Prevent default construction
////
////			SequentialEssenceSource();
////
//		private SequentialEssenceSource() { }
////
////		public:
////
////			//! Construct a SequentialEssenceSource
////
////			SequentialEssenceSource(FileParser *Outer) : Outer(Outer), PreviousLength(0) {}
////
//		public SequentialEssenceSource(
//				FileParser outer) {
//
//			this.outer = outer;
//		}
////
////			//! Set the new source to use
////
////			void SetSource(EssenceSourcePtr NewSource)
////
////			{
////
////				CurrentSource = NewSource;
////
////
////
////				// Set all options
////
////				std::list<OptionPair>::iterator it = OptionList.begin();
////
////				while(it != OptionList.end())
////
////				{
////
////					NewSource->SetOption((*it).first, (*it).second);
////
////					it++;
////
////				}
////
////
////
////				// Set the index manager
////
////				if(IndexMan) NewSource->SetIndexManager(IndexMan, IndexStreamID);
////
////			}
////
//		public void setSource(
//				EssenceSource source) {
//
//			currentSource = source;
//
//			for ( String option : optionList.keySet() )
//				source.setOption(option, optionList.get(option));
//
//			if (indexManager != null)
//				source.setIndexManager(indexManager, indexStreamID);
//		}
////
////			//! Get the size of the essence data in bytes
////
////			virtual size_t GetEssenceDataSize(void)
////			{
////				if(!ValidSource()) return 0;
//
////				// If we have emptied all files then exit now
////				if(Outer->AtEOF) return 0;
//
////				size_t Ret = CurrentSource->GetEssenceDataSize();
//
////				// If no more data move to the next source file
//
////				if(!Ret) {
////
////					// Work out how much was read from this file
////					Length CurrentSize = (Length)CurrentSource->GetCurrentPosition();
//
////					if(Outer->GetNextSource()) {
////
////						// Add this length to the previous lengths
////						PreviousLength += CurrentSize;
//
////						return GetEssenceDataSize();
////					}
////				}
////				// Return the source size
////
////				return Ret;
////			}
////
//		public int getEssenceDataSize() {
//
//			if (!validSource()) return 0;
//
//			// If all files are emptied, exit now
//			if (outer.atEOF()) return 0;
//
//			int essenceDataSize = currentSource.getEssenceDataSize();
//
//			// If no more data, move to the next source file
//			if (essenceDataSize > 0) {
//
//				// Work out how much was read from this file
//				long currentSize =
//					currentSource.getCurrentPosition();
//
//				if (outer.getNextSource()) {
//
//					// Add this length to the previous lengths
//					previousLength += currentSize;
//
//					return getEssenceDataSize();
//				}
//			}
//
//			return essenceDataSize;
//		}
////
////			//! Get the next "installment" of essence data
////
////			virtual DataChunkPtr GetEssenceData(size_t Size = 0, size_t MaxSize = 0);
////
//		public ByteBuffer getEssenceData(
//				int size,
//				int maxSize) {
//
//			// TODO
//			return null;
//		}
////
////			//! Did the last call to GetEssenceData() return the end of a wrapping item
////
////			virtual bool EndOfItem(void) { if(ValidSource()) return CurrentSource->EndOfItem(); else return true; }
////
//		public boolean endOfItem() {
//
//			if (validSource())
//				return currentSource.endOfItem();
//			else
//				return true;
//		}
////
////			//! Is all data exhasted?
////
////			virtual bool EndOfData(void) { if(ValidSource()) return CurrentSource->EndOfItem(); else return true; }
////
//		public boolean endOfData() {
//
//			if (validSource())
//				return currentSource.endOfItem();
//			else
//				return true;
//		}
////
////			//! Get the GCEssenceType to use when wrapping this essence in a Generic Container
////
////			virtual UInt8 GetGCEssenceType(void) { if(ValidSource()) return CurrentSource->GetGCEssenceType(); else return 0; }
////
//		public @UInt8 byte getGenericContainerEssenceType() {
//
//			if (validSource())
//				return currentSource.getGenericContainerEssenceType();
//			else
//				return 0;
//		}
////
////			//! Get the GCEssenceType to use when wrapping this essence in a Generic Container
////
////			virtual UInt8 GetGCElementType(void) { if(ValidSource()) return CurrentSource->GetGCElementType(); else return 0; }
////
//		public @UInt8 byte getGenericContainerElementType() {
//
//			if (validSource())
//				return currentSource.getGenericContainerElementType();
//			else
//				return 0;
//		}
////
////			//! Is the last data read the start of an edit point?
////
////			virtual bool IsEditPoint(void) { if(ValidSource()) return CurrentSource->IsEditPoint(); else return true; }
////
//		@Override
//		public boolean isEditPoint() {
//
//			if (validSource())
//				return currentSource.isEditPoint();
//			else
//				return true;
//		}
////
////			//! Get the edit rate of this wrapping of the essence
////
////			virtual Rational GetEditRate(void) { if(ValidSource()) return CurrentSource->GetEditRate(); else return Rational(0,0); }
////
//		@Override
//		public RationalImpl getEditRate() {
//
//			if (validSource())
//				return currentSource.getEditRate();
//			else
//				return new RationalImpl(0, 0);
//		}
////
////			//! Get the current position in GetEditRate() sized edit units
////
////			virtual Position GetCurrentPosition(void)
////
////			{
////
////				if(!ValidSource()) return 0;
////
////
////
////				return CurrentSource->GetCurrentPosition() + (Position)PreviousLength;
////
////			}
////
//		public @MXFPosition long getCurrentPosition() {
//
//			if (!validSource()) return 0;
//			return currentSource.getCurrentPosition() + previousLength;
//		}
////
////			//! Get the preferred BER length size for essence KLVs written from this source, 0 for auto
////
////			virtual int GetBERSize(void)
////
////			{
////
////				if(!ValidSource()) return 0;
////
////
////
////				return CurrentSource->GetBERSize();
////
////			}
////
//		public int getBERSize() {
//
//			if (!validSource()) return 0;
//
//			return currentSource.getBERSize();
//		}
////
////			//! Set a source type or parser specific option
////
////			virtual bool SetOption(std::string Option, Int64 Param = 0)
////
////			{
////
////				if(!ValidSource()) return false;
////
////
////
////				// Record this option to allow us to reconfigure sources if we switch source
////
////				OptionList.push_back(OptionPair(Option, Param));
////
////
////
////				return CurrentSource->SetOption(Option, Param);
////
////			}
////
//		public boolean setOption(
//				String option,
//				@Int64 long param) {
//
//			if (!validSource()) return false;
//
//			optionList.put(option, param);
//
//			return currentSource.setOption(option, param);
//		}
////
////			//! Get BytesPerEditUnit if Constant, else 0
////
////			virtual UInt32 GetBytesPerEditUnit(UInt32 KAGSize = 1) { if(ValidSource()) return CurrentSource->GetBytesPerEditUnit(KAGSize); else return 0; }
////
//		public @UInt32 int getBytesPerEditUnit(
//				@UInt32 int kagSize) {
//
//			if (validSource())
//				return currentSource.getBytesPerEditUnit(kagSize);
//			else
//				return 0;
//		}
////
////			//! Can this stream provide indexing
////
////			virtual bool CanIndex() { if(ValidSource()) return CurrentSource->CanIndex(); else return false; }
////
//		public boolean canIndex() {
//
//			if (validSource())
//				return currentSource.canIndex();
//			else
//				return false;
//		}
////
////			//! Set the index manager to use for building index tables for this essence
////
////			virtual void SetIndexManager(IndexManagerPtr &Manager, int StreamID)
////
////			{
////
////				IndexMan = Manager;
////
////				IndexStreamID = StreamID;
////
////
////
////				if(ValidSource()) CurrentSource->SetIndexManager(Manager, StreamID);
////
////			}
////
//		public void setIndexManager(
//				IndexManager indexManager,
//				int streamID) {
//
//			this.indexManager = indexManager;
//			this.indexStreamID = streamID;
//		}
////
////			//! Get the index manager
////
////			virtual IndexManagerPtr &GetIndexManager(void) { return IndexMan; }
////
//		public IndexManager getIndexManager() {
//
//			return indexManager;
//		}
////
////			//! Get the index manager sub-stream ID
////
////			virtual int GetIndexStreamID(void) { return IndexStreamID; }
////
//		public int getIndexStreamID() {
//
//			return indexStreamID;
//		}
////
////			//! Get the origin value to use for this essence specifically to take account of pre-charge
////
////			/*! \return Zero if not applicable for this source
////
////			*/
////
////			virtual Length GetPrechargeSize(void)
////
////			{
////
////				if(!ValidSource()) return 0;
////
////
////
////				return CurrentSource->GetPrechargeSize();
////
////			}
////
//		public @MXFLength long getPrechargeSize() {
//
//			if (!validSource())
//					return 0;
//
//			return currentSource.getPrechargeSize();
//		}
////
////			//! Get the range start position
////
////			virtual Position GetRangeStart(void) { return Outer->GetRangeStart(); }
////
//		public @MXFPosition long getRangeStart() {
//
//			return outer.getRangeStart();
//		}
////
////			//! Get the range end position
////
////			virtual Position GetRangeEnd(void) { return Outer->GetRangeEnd(); }
////
//		public @MXFPosition long getRangeEnd() {
//
//			return outer.getRangeEnd();
//		}
////
////			//! Get the range duration
////
////			virtual Length GetRangeDuration(void) { return Outer->GetRangeDuration(); }
////
//		public @MXFPosition long getRangeDuration() {
//
//			return outer.getRangeDuration();
//		}
////
////
////
////		protected:
////
////			//! Ensure that CurrentSource is valid and ready for reading - if not select the next source file
////
////			/*! \return true if all OK, false if no EssenceSource available
////
////			 */
////
////			bool ValidSource(void)
////
////			{
////
////				if(CurrentSource) return true;
////
////
////
////				// If this is the first time through when we will have a file open but no source set to get current not text source
////
////				if(Outer->CurrentFileOpen) return Outer->GetFirstSource();
////
////
////
////				return Outer->GetNextSource();
////
////			}
//
//		boolean validSource() {
//
//			if (currentSource != null)
//				return true;
//
//			if (outer.currentFileOpen)
//				return outer.getFirstSource();
//
//			return outer.getNextSource();
//		}
//
//	} // End SequentialEssenceSource
//
//} // End FileParser
