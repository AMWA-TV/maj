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
 * $Log: ListOfFiles.java,v $
 * Revision 1.2  2009/02/13 14:27:29  vizigoth
 * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf;

import java.util.List;

/**
 * <p>Handles a list of files.</p>
 * 
 *
 * 
 */
public abstract class ListOfFiles {

	public abstract class NewFileHandler {
		
// public:
//
// virtual ~NewFileHandler() {};
//
//
//
// //! Receive notification of a new file about to be opened
//
// /*! \param FileName - reference to a std::string containing the name of the
// file about to be opened - <b>may be changed by this function if required</b>
//
// */
//
// virtual void NewFile(std::string &FileName) = 0;
		public abstract void newFile(
				String fileName);
//
	}

// protected:
//
// NewFileHandlerPtr Handler; //!< Handler to be informed of new filenames
	
	private NewFileHandler handler;
	
// std::string BaseFileName; //!< Base filename as a printf string

	private String baseFileName;
	
// std::list<std::string> FollowingNames; //!< Names to be processed next

	private List<String> followingNames;
	
// bool FileList; //!< True if this is a multi-file set rather than a single
// file (or if a range is in use)

	private boolean fileList;
	
// int ListOrigin; //!< Start number for filename building

	private int listOrigin;
	
// int ListIncrement; //!< Number to add to ListOrigin for each new file

	private int listIncrement;
	
// int ListNumber; //!< The number of files in the list or -1 for "end when no
// more files"

	private int listNumber;
	
// int ListEnd; //!< The last file number in the list or -1 for "end when no
// more files"

	private int listEnd;
	
// int FileNumber; //!< The file number to use for the <b>next</b> source file
// to open

	private int fileNumber;
	
// int FilesRemaining; //!< The number of files remaining in the list or -1 for
// "end when no more files"

	private int filesRemaining;
	
// bool AtEOF; //!< True once the last file has hit it's end of file

	private boolean atEOF = false;
	
// std::string CurrentFileName; //!< The name of the current file (if open)
//
	private String currentFileName;
//
// Position RangeStart; //!< The requested first edit unit, or -1 if none
// specified

	private @MXFPosition long rangeStart = -1l;
	
// Position RangeEnd; //!< The requested last edit unit, or -1 if using
// RequestedDuration

	private @MXFPosition long rangeEnd = -1l;
	
// Length RangeDuration; //!< The requested duration, or -1 if using
// RequestedEnd
//
	private @MXFLength long rangeDuration = -1l;
//
// public:
//
// //! Construct a ListOfFiles and optionally set a single source filename
// pattern
//
// ListOfFiles(std::string FileName = "") : RangeStart(-1), RangeEnd(-1),
// RangeDuration(-1)
//
// {
//
// AtEOF = false;
//
//
//
// // Set the filename pattern if required
//
// if(FileName.size())
//
// {
//
// ParseFileName(FileName);
//
// }
//
// else
//
// {
//
// BaseFileName = "";
//
// FileList = false;
//
// }
//
// }
//
	public ListOfFiles(String fileName) {
		
		if (fileName == null) fileName = "";
		
		if (fileName.length() > 0)
			parseFileName(fileName);
		else {
			baseFileName = fileName;
			fileList = false;
		}
	}
	
	public ListOfFiles() {
		
			baseFileName = "";
			fileList = false;
	}


// //! Set a single source filename pattern
//
// void SetFileName(std::string &FileName)
//
// {
//
// FollowingNames.clear();
//
// ParseFileName(FileName);
//
// }
//
	public void setFileName(
			String fileName) {
		
		followingNames.clear();
		parseFileName(fileName);
	}
//
// //! Add a source filename pattern
//
// void AddFileName(std::string &FileName)
//
// {
//
// if(BaseFileName.empty())
//
// {
//
// ParseFileName(FileName);
//
// }
//
// else
//
// {
//
// FollowingNames.push_back(FileName);
//
// }
//
// }
//
	public void addFileName(
			String fileName) 
		throws NullPointerException {
		
		if (fileName == null)
			throw new NullPointerException("Cannot add a null file name to a list of files.");
		
		if (baseFileName.length() == 0)
			parseFileName(fileName);
		else
			followingNames.add(fileName);
	}
//
// //! Set a handler to receive notification of all file open actions
//
// void SetNewFileHandler(NewFileHandlerPtr &NewHandler) { Handler = NewHandler;
// }
//
	public void setNewFileHandler(
			NewFileHandler handler) {
		
		this.handler = handler;
	}
	
//
// //! Get the start of any range specified, or -1 if none
//
// Position GetRangeStart(void) const { return RangeStart; }
//
	public @MXFPosition long getRangeStart() {
		
		return rangeStart;
	}
//
// //! Get the end of any range specified, or -1 if none
//
// Position GetRangeEnd(void) const { return RangeEnd; }
//
	public @MXFPosition long getRangeEnd() {
		
		return rangeEnd;
	}
//
// //! Get the duration of any range specified, or -1 if none
//
// Position GetRangeDuration(void) const { return RangeDuration; }
//
	public @MXFPosition long getRangeDuration() {
		
		return rangeDuration;
	}
//
// //! Get the current filename
//
// std::string FileName(void) { return CurrentFileName; }
//
	public String getCurrentFileName() {
		
		return currentFileName;
	}
//
// //! Open the current file (any new-file handler will already have been
// called)
//
// /*! This function must be supplied by the derived class
//
// * \return true if file open succeeded
//
// */
//
// virtual bool OpenFile(void) = 0;
//
	public abstract boolean openFile();
//
// //! Close the current file
//
// /*! This function must be supplied by the derived class */
//
// virtual void CloseFile(void) = 0;
//
	public abstract void closeFile();
//
// //! Is the current file open?
//
// /*! This function must be supplied by the derived class */
//
// virtual bool IsFileOpen(void) = 0;
//
	public abstract boolean isFileOpen();
//
// //! Is the current filename pattern a list rather than a single file?
//
// bool IsFileList(void) { return FileList; }
//
	public  boolean isFileList() {
		
		return fileList;
	}
//
// //! Open the next file in the set of source files
//
// /*! \return true if all OK, false if no file or error
//
// */
//
// bool GetNextFile(void);
//
	public boolean getNextFile() {
		
		// TODO
		return false;
	}
//
// protected:
//
// //! Parse a given multi-file name
//
// void ParseFileName(std::string FileName);
//
	void parseFileName(
			String fileName) {
		
		// TODO
	}

	boolean atEOF() {
		
		return atEOF;
	}
}
