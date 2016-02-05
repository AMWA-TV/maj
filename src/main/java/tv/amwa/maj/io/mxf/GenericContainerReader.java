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
 * $Log: GenericContainerReader.java,v $
 * Revision 1.3  2010/01/19 14:44:23  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
 *
 * Revision 1.2  2009/02/13 14:27:29  vizigoth
 * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf;

import java.util.Map;

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.io.mxf.impl.MXFFileImpl;

/**
 * <p>Reads data from an MXF file.</p>
 * 
 *
 *
 */
public class GenericContainerReader {

	public abstract class GenericContainerReadHandler {
		
//		public:
//
//			//! Handle a "chunk" of data that has been read from the file
//
//			/*! \return true if all OK, false on error 
//
//			 */
//
//			virtual bool HandleData(GCReaderPtr Caller, KLVObjectPtr Object) = 0;
//
		public abstract boolean handleData(
				GenericContainerReader caller,
				KLVObject klvObject);
	}
	
//	protected:
//
//		MXFFilePtr File;								//!< File from which to read

	private MXFFileImpl file;
	
//		Position FileOffset;							//!< The offset of the start of the current (or next) KLV within the file. Current KLV during HandleData() and next at other times.

	private @MXFPosition long fileOffset;
	
//		Position StreamOffset;							//!< The offset of the start of the current KLV within the data stream
//
	private @MXFPosition long streamOffset;
//
//		bool StopNow;									//!< True if no more KLVs should be read - set by StopReading() and ReadFromFile() with SingleKLV=true

	private boolean stopNow;
	
//		bool StopCalled;								//!< True if StopReading() called while processing the current KLV

	private boolean stopCalled;
	
//		bool PushBackRequested;							//!< True if StopReading() called with PushBackKLV = true
//
	private boolean pushBackRequested;
//
//		GCReadHandlerPtr DefaultHandler;				//!< The default handler to receive all KLVs without a specific handler

	private GenericContainerReadHandler defaultHandler;
	
//		GCReadHandlerPtr FillerHandler;					//!< The hanlder to receive all filler KLVs

	private GenericContainerReadHandler fillerHandler;
	
//		GCReadHandlerPtr EncryptionHandler;				//!< The hanlder to receive all encrypted KLVs
//
	private GenericContainerReadHandler encryptionHandler;
//
//		std::map<UInt32, GCReadHandlerPtr> Handlers;	//!< Map of read handlers indexed by track number
//
	private Map<Integer, GenericContainerReadHandler> handlers;
//
//	public:
//
//		//! Create a new GCReader, optionally with a given default item handler and filler handler
//
//		/*! \note The default handler receives all KLVs without a specific handler (except fillers)
//
//		 *        The filler handler receives all filler KLVs
//
//		 */
//
//		GCReader( MXFFilePtr File, GCReadHandlerPtr DefaultHandler = NULL, GCReadHandlerPtr FillerHandler = NULL );
//
	public GenericContainerReader(
			MXFFileImpl file,
			GenericContainerReadHandler defaultHandler,
			GenericContainerReadHandler fillerHandler) {
		
		// TODO
	}

	public GenericContainerReader(
			MXFFileImpl file) {
		
		// TODO same as above with handlers set to null
	}
	
//
//		//! Set the default read handler 
//
//		/*! This handler receives all KLVs without a specific data handler assigned
//
//		 *  including KLVs that do not appear to be standard GC KLVs.  If not default handler
//
//		 *  is set KLVs with no specific handler will be discarded.
//
//		 */
//
//		void SetDefaultHandler(GCReadHandlerPtr DefaultHandler = NULL)
//
//		{
//
//			this->DefaultHandler = DefaultHandler;
//
//		}

	public void setDefaultHandler(
			GenericContainerReadHandler defaultHandler) {
		
		this.defaultHandler = defaultHandler;
	}
//
//
//		//! Set the filler handler
//
//		/*! If no filler handler is set all filler KLVs are discarded
//
//		 *  \note Filler KLVs are <b>never</b> sent to the default handler
//
//		 *        unless it is also set as the filler handler
//
//		 */
//
//		void SetFillerHandler(GCReadHandlerPtr FillerHandler = NULL)
//
//		{
//
//			this->FillerHandler = FillerHandler;
//
//		}
//
	public void setFillerHandler(
			GenericContainerReadHandler fillerHandler) {
		
		this.fillerHandler = fillerHandler;
	}
//
//		//! Set encryption handler
//
//		/*! This handler will receive all encrypted KLVs and after decrypting them will
//
//		 *  resubmit the decrypted version for handling using function HandleData()
//
//		 */
//
//		void SetEncryptionHandler(GCReadHandlerPtr EncryptionHandler = NULL)
//
//		{
//
//			this->EncryptionHandler = EncryptionHandler;
//
//		}
//
	public void setEncryptionHandler(
			GenericContainerReadHandler encryptionHandler) {
		
		this.encryptionHandler = encryptionHandler;
	}
//
//		//! Set data handler for a given track number
//
//		void SetDataHandler(UInt32 TrackNumber, GCReadHandlerPtr DataHandler = NULL)
//
//		{
//
//			if(DataHandler)
//
//			{
//
//				Handlers[TrackNumber] = DataHandler;
//
//			}
//
//			else
//
//			{
//
//				Handlers.erase(TrackNumber);
//
//			}
//
//		}
//
	public void setDataHandler(
			@UInt32 int trackNumber,
			GenericContainerReadHandler dataHandler) {
		
		if (dataHandler != null)
			handlers.put(trackNumber, dataHandler);
		else
			handlers.remove(trackNumber);
	}
//
//		//! Read from file - and specify a start location
//
//		/*! All KLVs are dispatched to handlers
//
//		 *  Stops reading at the next partition pack unless SingleKLV is true when only one KLV is dispatched
//
//		 *  \param FilePos Location within the file to start this read
//
//		 *  \param StreamPos Stream offset of the first KLV to be read
//
//		 *  \param SingleKLV True if only a single KLV is to be read
//
//		 *  \return true if all went well, false end-of-file, an error occured or StopReading() was called
//
//		 */
//
//		bool ReadFromFile(Position FilePos, Position StreamPos, bool SingleKLV = false)
//
//		{
//
//			FileOffset = FilePos;					// Record the file location
//
//			StreamOffset = StreamPos;				// Record the stream location
//
//
//
//			return ReadFromFile(SingleKLV);			// Then do a "continue" read
//
//		}
//
	public boolean readFromFile(
			@MXFPosition long filePosition,
			@MXFPosition long streamPosition,
			boolean singleKLV) {
		
		this.fileOffset = filePosition;
		this.streamOffset = streamPosition;
		
		return readFromFile(singleKLV);
	}

	public boolean readFromFile(
			@MXFPosition long filePosition,
			@MXFPosition long streamPosition) {
		
		return readFromFile(filePosition, streamPosition, false);
	}
	
	
//		//! Read from file - continuing from a previous read
//
//		/*! All KLVs are dispatched to handlers
//
//		 *  Stops reading at the next partition pack unless SingleKLV is true when only one KLV is dispatched
//
//		 *  \return true if all went well, false end-of-file, an error occured or StopReading() was called
//
//		 */
//
//		bool ReadFromFile(bool SingleKLV = false);
//
	public boolean readFromFile(
			boolean singleKLV) {
	
		// TODO
		return false;
	}

	public boolean readFromFile() {
		
		return readFromFile(false);
	}
	
	
//		//! Set the offset of the start of the next KLV within this GC stream
//
//		/*! Generally this will only be called as a result of parsing a partition pack
//
//		 *  \note The offset will start at zero and increment automatically as data is read.
//
//		 *        If a seek is performed the offset will need to be adjusted.
//
//		 */
//
//		void SetStreamOffset(Position NewOffset) 
//
//		{ 
//
//			StreamOffset = NewOffset; 
//
//		};
//
	public void setStreamOffset(
			@MXFPosition long streamOffset) {
		
		this.streamOffset = streamOffset;
	}
//
//		//! Get the file offset of the next read (or the current KLV if inside ReadFromFile)
//
//		/*! \note This is not the correct way to access the raw KLV in the file - that should be done via the KLVObject.
//
//		 *        This function allows the caller to determine where the file pointer ended up after a read.
//
//		 */
//
//		Position GetFileOffset(void) { return FileOffset; }
//
	public @MXFPosition long getFileOffset() {
		
		return fileOffset;
	}
//
//
//
//		/*** Functions for use by read handlers ***/
//
//
//
//		//! Force a KLVObject to be handled
//
//		/*! \note This is not the normal way that the GCReader is used, but allows the encryption handler
//
//		 *        to push the decrypted data back to the GCReader to pass to the appropriate handler
//
//		 *  \return true if all OK, false on error 
//
//		 */
//
//		bool HandleData(KLVObjectPtr Object);
//
	public boolean handleData(
			KLVObject object) {
		
		// TODO
		return false;
	}
//
//		//! Stop reading even though there appears to be valid data remaining
//
//		/*! This function can be called from a handler if it detects that the current KLV is either the last
//
//		 *  KLV in partition, or does not belong in this partition at all.  If the KLV belongs to another
//
//		 *  partition, or handling should be deferred for some reason, PushBackKLV can be set to true
//
//		 */
//
//		void StopReading(bool PushBackKLV = false);
//
	public void stopReading(
			boolean pushBackKLV) {
		
		// TODO
	}

	public void stopReading() {
		
		stopReading(false);
	}

	//
//		//! Get the offset of the start of the current KLV within this GC stream
//
//		Position GetStreamOffset(void) { return StreamOffset; };
//
	public @MXFPosition long getStreamOffset() {
		
		return streamOffset;
	}
}
