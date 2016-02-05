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
 * $Log: SetFileBits.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/02/08 11:27:22  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:37  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.DataSizeException;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.misctype.DataBuffer;


// TODO work out how to support this in the MAJ API

/**
 * <p>This interface is implemented by the toolkit, and is provided to
 * clients of {@link AAFFile AAF file} to allow them to supply the raw 
 * bits of a file before open.</p>
 * 
 * <p>Note that this and the {@link GetFileBits get file bits} interfaces 
 * are different to the {@link RawStorage raw storage} interfaces in that 
 * those are implemented by clients to provide access to the desired media; 
 * these are provided by the toolkit to provide read OR write (but not both
 * simultaneously) access to the raw bits.</p>
 * 
 *
 *
 */

public interface SetFileBits {
	

	/**
	 * <p>Writes the given data buffer into the file and the given position.
	 * The position is measured in bytes, with a value of 0 indicating the
	 * beginning of the stream.</p>
	 *  
	 * @param buf Buffer from which data is written.
	 * @param position Position in the file at which to write.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws DataSizeException The requested space, determined by the position
	 * and size of the buffer, is not available.
	 */
	public void writeAt(
			@DataBuffer byte[] buf,
			@UInt64 long position) throws NullPointerException,
										  DataSizeException;

	/**
	 * <p>Attempts to reserve the given number of bytes of storage.</p>
	 * 
	 * @param size Number of bytes requested.
	 * 
	 * @throws DataSizeException The requested space is not available.
	 */
	public void setSize(
			@UInt64 long size) throws DataSizeException;
}

