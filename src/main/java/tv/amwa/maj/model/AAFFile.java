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
 * $Log: AAFFile.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/27 11:07:29  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/11/15 12:52:48  vizigoth
 * Edits to ensure source can make rough and ready javadoc.
 *
 * Revision 1.1  2007/11/13 22:08:38  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.FileRev;
import tv.amwa.maj.exception.AlreadyOpenException;

import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.NoPrefaceException;
import tv.amwa.maj.exception.NotOpenException;
import tv.amwa.maj.exception.NotWriteableException;
import tv.amwa.maj.exception.WrongOpenModeException;


/**
 * <p>Specifies a representation of an AAF file.</p>
 * 
 * <p>Note that in the current version of the MAJ API, no support is provided for AAF structured format or
 * KLV files. The MAJ API implementation of this method does nothing useful. This and related methods are 
 * provided as part of a skeleton implementation of an interface similar to that provided in the existing 
 * C-based reference implementation COM interface definitions (file "<code>AAF.idl</code>").</p>
 * 
 *
 */
public interface AAFFile {

	/**
	 * <p>Associates this AAFFile with the storage to
	 * which it is to be associated.  It will then be possible to access
	 * objects within this file.</p>
	 * 
	 * @throws AlreadyOpenException This object has already been opened.
	 */
	public void open() 
		throws AlreadyOpenException;

	/**
	 * <p>Copies the contents of this file to a given destination file.  
	 * Requires that the destination file be open, writable or modifiable, 
	 * and not yet contain any objects.</p>
	 * 
	 * @param destFile File to receive a copy of the contents of this file.
	 * 
	 * @throws NotOpenException The destination file is not open.
	 * @throws NotWriteableException The destination file cannot be written.
	 * One reason may be that it already contains objects.
	 */
	public void saveCopyAs(
			AAFFile destFile) 
		throws NotOpenException,
			NotWriteableException;

	/**
	 * <p>Returns the {@linkplain Preface preface} associated with this
	 * file. If this object has never been associated with a file, a
	 * new empty {@linkplain Preface preface} will be created and
	 * returned.  Note that the preface is automatically created when the
	 * file object is created.</p>
	 * 
	 * @return Preface of the file.
	 * 
	 * @throws NotOpenException The object is not open.
	 * @throws NoPrefaceException The object contains no preface.
	 */
	public Preface getPreface() 
		throws NotOpenException,
			   NoPrefaceException;

	/**
	 * <p>Returns the revision of the file.</p>
	 * 
	 * @return Revision of the file.
	 * 
	 * @throws NotOpenException The object is not open.
	 */
	public FileRev getRevision() 
		throws NotOpenException;

	/**
	 * <p>If this AAFFile is open, all unsaved changes
	 * made to the contents of this object are saved.</p>
	 * 
	 * @throws NotOpenException The object is not open.
	 * @throws WrongOpenModeException This object is not open for writing 
	 * or modification.
	 * @throws InsufficientSpaceException There is insufficient space in 
	 * the storage to save the contents of this object.
	 */
	public void save()
		throws NotOpenException,
			WrongOpenModeException,
			InsufficientSpaceException;

	/**
	 * <p>Dissociates this AAFFile from its storage.  Any AAF objects which were
	 * contained within this object will then no longer be available to
	 * the client.</p>
	 * 
	 * <p>If the client is maintaining any references to any objects which
	 * are contained in this object, those objects will be marked as
	 * dissociated, and methods on them will fail.</p>
	 * 
	 * <p>Any unsaved changes will be written to the file before the close
	 * operation is complete.</p>
	 * 
	 * @throws NotOpenException This object is not already open.
	 */
	public void close() 
		throws NotOpenException;

	/**
	 * <p>Returns the {@linkplain Dictionary dictionary} that contains all types of 
	 * AAF definition objects for the file. Note that the dictionary
	 * is automatically created when the {@linkplain Preface preface} is created.</p>
	 * 
	 * @return The dictionary of this file.
	 * @see Preface#getDictionaries()
	 */
	public Dictionary getDictionary();
}