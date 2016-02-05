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
 * $Log: BodyReader.java,v $
 * Revision 1.3  2010/01/19 14:44:22  vizigoth
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
 * <p>Reads data from the body of a {@linkplain MXFFileImpl MXF file}.</p>
 * 
 *
 *
 */
public class BodyReader {

//	protected:
//
//		MXFFilePtr File;						//!< File from which to read
//
	private MXFFileImpl file;
	
//		Position CurrentPos;					//!< Current position within file
//
	private @MXFPosition long currentPosition;
//
//		bool NewPos;							//!< The value of CurrentPos has been updated by a seek - therefore reading must be reinitialized!
//
	private boolean newPosition;
	
//		bool SeekInited;						//!< True once the per SID seek system has been initialized

	private boolean seekInitialised;
	
//		bool AtPartition;						//!< Are we (to our knowledge) at the start of a partition pack?

	private boolean atPartition;
	
//		bool AtEOF;								//!< Are we (to our knowledge) at the end of the file?
//
	private boolean atEOF;
//
//		UInt32 CurrentBodySID;					//!< The currentBodySID being processed
//
	private @UInt32 int currentBodySID;
//
//		GCReadHandlerPtr GCRDefaultHandler;		//!< Default handler to use for new GCReaders

	private GenericContainerReader.GenericContainerReadHandler gcReadDefaultHandler;
//		GCReadHandlerPtr GCRFillerHandler;		//!< Filler handler to use for new GCReaders

	private GenericContainerReader.GenericContainerReadHandler gcReadFillerHandler;
	
//		GCReadHandlerPtr GCREncryptionHandler;	//!< Encryption handler to use for new GCReaders
//
	private GenericContainerReader.GenericContainerReadHandler gcReadEncryptionHandler;
//
//		std::map<UInt32, GCReaderPtr> Readers;	//!< Map of GCReaders indexed by BodySID
//
	private Map<Integer, GenericContainerReader> readers;
//
//	public:
//
//		//! Construct a body reader and associate it with an MXF file
//
//		BodyReader(MXFFilePtr File);
//
	public BodyReader(
			MXFFileImpl file) {
		
		// TODO
	}
//
//		//! Seek to a specific point in the file
//
//		/*! \return New location or -1 on seek error
//
//		 */
//
//		Position Seek(Position Pos = 0);
//
	public @MXFPosition long seek(
			@MXFPosition long position) {
		
		// TODO
		return 0;
	}
	
	public @MXFPosition long seek() {
		
		return seek(0l);
	}
	
//
//		//! Tell the current file location
//
//		Position Tell(void) { return CurrentPos; };
//
	public @MXFPosition long tell() {
		
		return currentPosition;
	}
//
//		//! Seek to a specific byte offset in a given stream
//
//		/*! \return New file offset or -1 on seek error
//
//		 */
//
//		Position Seek(UInt32 BodySID, Position Pos);
//
	public @MXFPosition long seek(
			@UInt32 int bodySID,
			@MXFPosition long position) {
		
		// TODO
		return 0l;
	}
//
//		//! Report the byte offset in a given stream
//
//		/*! \return File offset or -1 if not known or stream does not exist
//
//		 */
//
//		Position Tell(UInt32 BodySID);
//
	public @MXFPosition long tell(
			@UInt32 long bodySID) {
		
		//TODO
		return 0l;
	}
//
//		//! Set the default handler for all new GCReaders
//
//		/*! Each time a new GCReader is created this default handler will be used if no other is specified
//
//		 */
//
//		void SetDefaultHandler(GCReadHandlerPtr DefaultHandler = NULL) { GCRDefaultHandler = DefaultHandler; };
//
	public void setDefaultHandler(
			GenericContainerReader.GenericContainerReadHandler defaultHandler) {
		
		this.gcReadDefaultHandler = defaultHandler;
	}
//
//		//! Set the filler handler for all new GCReaders
//
//		/*! Each time a new GCReader is created this filler handler will be used if no other is specified
//
//		 */
//
//		void SetFillerHandler(GCReadHandlerPtr FillerHandler = NULL) { GCRFillerHandler = FillerHandler; };
//
	public void setFillerHandler(
			GenericContainerReader.GenericContainerReadHandler fillerHandler) {
		
		this.gcReadFillerHandler = fillerHandler;
	}
//
//		//! Set the encryption handler for all new GCReaders
//
//		/*! Each time a new GCReader is created this encryption handler will be used
//
//		 */
//
//		void SetEncryptionHandler(GCReadHandlerPtr EncryptionHandler = NULL) { GCREncryptionHandler = EncryptionHandler; };
//
	public void setEncryptionHandler(
			GenericContainerReader.GenericContainerReadHandler encryptionHandler) {
		
		this.gcReadEncryptionHandler = encryptionHandler;
	}
//
//		//! Make a GCReader for the specified BodySID
//
//		/*! \return true on success, false on error (such as there is already a GCReader for this BodySID)
//
//		 */
//
//		bool MakeGCReader(UInt32 BodySID, GCReadHandlerPtr DefaultHandler = NULL, GCReadHandlerPtr FillerHandler = NULL);
//
	public boolean makeGenericContainerReader(
			@UInt32 int bodySID,
			GenericContainerReader.GenericContainerReadHandler defaultHandler,
			GenericContainerReader.GenericContainerReadHandler fillerHandler) {
		
		// TODO
		return false;
	}
	
	public boolean makeGenericContainerReader(
			@UInt32 int bodySID) {
		
		return makeGenericContainerReader(bodySID, null, null);
	}

//
//		//! Get a pointer to the GCReader used for the specified BodySID
//
//		GCReaderPtr GetGCReader(UInt32 BodySID)
//
//		{
//
//			// See if we have a GCReader for this BodySID
//
//			std::map<UInt32, GCReaderPtr>::iterator it = Readers.find(BodySID);
//
//
//
//			// If not found return NULL
//
//			if(it == Readers.end()) return NULL;
//
//
//
//			// Return the pointer
//
//			return (*it).second;
//
//		}
//
	public GenericContainerReader getGenericContainerReader(
			@UInt32 int bodySID) {
		
		return readers.get(bodySID);
	}
//
//		//! Read from file
//
//		/*! All KLVs are dispatched to handlers
//
//		 *  Stops reading at the next partition pack unless SingleKLV is true when only one KLV is dispatched
//
//		 *  \return true if all went well, false end-of-file, an error occured or StopReading() 
//
//		 *          was called on the current GCReader
//
//		 */
//
//		bool ReadFromFile(bool SingleKLV = false);
//
	public boolean readFileFile(
			boolean singleKLV) {
		
		// TODO
		return false;
	}
	
	public boolean readFromFile() {
		
		return readFileFile(false);
	}
//
//		//! Resync after possible loss or corruption of body data
//
//		/*! Searches for the next partition pack and moves file pointer to that point
//
//		 *  \return false if an error (or EOF found)
//
//		 */
//
//		bool ReSync();
//
	public boolean reSync() {
		
		// TODO
		return false;
	}
//
//		//! Are we currently at the start of a partition pack?
//
//		bool IsAtPartition(void);
//
	public boolean isAtPartition() {
		
		// TODO
		return false;
	}
//
//		//! Are we currently at the end of the file?
//
//		bool Eof(void);
//
	public boolean eof() {
		
		// TODO
		return false;
	}
//
//
//		/*** Functions for use by read handlers ***/
//
//
//
//		//! Get the BodySID of the current location (0 if not known)
//
//		UInt32 GetBodySID(void) { return CurrentBodySID; }
//
//
	public @UInt32 int getBodySID() {
		
		return currentBodySID;
	}
//
//
//	protected:
//
//		//! Initialize the per SID seek system
//
//		/*! To allow us to seek to byte offsets within a file we need to initialize 
//
//		 *  various structures - seeking is not always possible!!
//
//		 *  \return False if seeking could not be initialized (perhaps because the file is not seekable)
//
//		 */
//
//		bool InitSeek(void);
//
	boolean initializeSeek() {
		
		// TODO
		return false;
	}
}
