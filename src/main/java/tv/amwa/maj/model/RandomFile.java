/* 
 **********************************************************************
 *
 * $Id: RandomFile.java,v 1.1 2011/01/04 10:39:02 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: RandomFile.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2008/02/28 12:50:32  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2007/12/04 09:32:57  vizigoth
 * Minor comment updates.
 *
 * Revision 1.1  2007/11/13 22:08:35  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.BadCloseException;
import tv.amwa.maj.exception.BadOpenException;
import tv.amwa.maj.exception.NotOpenException;
import tv.amwa.maj.exception.NotReadableException;
import tv.amwa.maj.exception.NotWriteableException;

// TODO decide how to support this better in Java

/**
 * <p>Specialized file interface for use with files which exist on
 * storage media which has random access capability.</p>
 * 
 * <p>Objects that implement RandomFile also implement the following 
 * interfaces:
 * 
 * <ul>
 *  <li>{@link AAFFile}</li>
 * </ul></p>
 * 
 * @see GetFileBits
 * @see SetFileBits
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */

public interface RandomFile extends AAFFile {

	/* [
	    object,
	    uuid(3CC80284-72A8-11D4-B80D-0000863F2C27),
	    helpstring("IAAFRandomFile Interface"),
	    pointer_default(unique)
	] */

	/**
	 * <p>This method obtains file data after a file has been created.
	 * The method succeeds if the {@link AAFFile file} has been {@link AAFFile#open()
	 * Opened} and then {@link AAFFile#close() closed}, or if it has
	 * never previously been opened with write access.</p>
	 * 
	 * @return File data following the creation of a file.
	 * 
	 * @throws BadOpenException The file has never been opened.
	 * @throws NotWriteableException The file has never been opened for
	 * writing of modification.
	 * @throws BadCloseException The file has not been closed having
	 * been opened.
	 */
	public GetFileBits getFileBits()
		throws BadOpenException,
			   NotWriteableException,
			   BadCloseException;

	/**
	 * <p>Reverts this file to the previously saved state.</p>
	 * 
	 */
	public void revert();

	/**
	 * <p>Associates this file with the storage specified the given destination
	 * file. Any objects stored in the destination file will be lost.</p>
	 * 
	 * <p>This method behaves in a similar way to {@link AAFFile#saveCopyAs(AAFFile)},
	 * except that this method will change this object to be associated
	 * with the new file.</p>
	 * 
	 * <p>Special case: if <code>this.saveAs(this)</code> is called then the 
	 * operation will save everything including things which have not changed.</p>
	 * 
	 * @param destFile File to associated with this one for saving.
	 * 
	 * @throws NotOpenException The destination file is not open.
	 * @throws NotWriteableException The destination file cannot be written.
	 */
	public void saveAsFile(
			AAFFile destFile) 
		throws NotOpenException,
			NotWriteableException;

	/**
	 * <p>File data with which the file is to be created. This method only
	 * succeeds if the {@linkplain AAFFile file} has not yet been {@linkplain AAFFile#open()
	 * opened}.</p>
	 *  
	 * @param setFileBits File data with which the file is to be created.
	 * @throws NullPointerException Argument is null.
	 * 
	 */
	public void setFileBits(
			SetFileBits setFileBits) 
		throws NullPointerException,
			BadOpenException,
			NotReadableException;
}

