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
 * $Log: MultipleDescriptor.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/29 18:11:22  vizigoth
 * Updated documentation for newly added classes to 1.1.2 and associated fixes.
 *
 * Revision 1.2  2007/12/04 13:04:51  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:31  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.integer.UInt32;


/** 
 * <p>Specifies the description of content data associated with a single file {@linkplain SourcePackage source package} 
 * that contains multiple tracks of essence. Each file descriptor within a multiple descriptor should
 * set the linked track property that links it to the source track that it describes.</p>
 *
 *
 * 
 * @see AAFFileDescriptor#getLinkedTrackID()
 * @see AAFFileDescriptor#setLinkedTrackID(Integer)
 * @see SourcePackage#getEssenceDescriptor()
 */
public interface MultipleDescriptor
	extends AAFFileDescriptor {

	/**
	 * <p>Returns the number of file descriptors of this multiple
	 * descriptor.</p>
	 *
	 * @return Number of file descriptors of this multiple descriptor.
	 */ 
	public int countFileDescriptors();

	/**
	 * <p>Appends a file descriptor to the list of file descriptors of this multiple
	 * descriptor. Use this function to add a {@link AAFFileDescriptor file descriptor} to 
	 * the end of the interleave pattern.</p>
	 *
	 * @param fileDescriptor File descriptor to append to this multiple descriptor.
	 * 
	 * @throws NullPointerException The given file descriptor is <code>null</code>.
	 */
	public void appendFileDescriptor (
			AAFFileDescriptor fileDescriptor)
		throws NullPointerException;

	/**
	 * <p>Prepends a file descriptor to the list of file descriptors of this multiple
	 * essence descriptor. Use this method to add a {@link AAFFileDescriptor file descriptor}
	 * to to the beginning of the interleave pattern.</p>
	 *
	 * @param fileDescriptor File descriptor to prepend to this multiple descriptor.
	 * 
	 * @throws NullPointerException The file descriptor argument is <code>null</code>.
	 */
	public void prependFileDescriptor(
			AAFFileDescriptor fileDescriptor)
		throws NullPointerException;

	/**
	 * <p>Insert a file descriptor into the list of file descriptors of this multiple descriptor
	 * at the given index. Other indices will be adjusted upwards to accommodate the new item.</p>
	 *
	 * @param index Index at which the given file descriptor is to be inserted.
	 * @param fileDescriptor File descriptor to insert.
	 * 
	 * @throws NullPointerException The file descriptor argument is <code>null</code>.
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for
	 * the current list of file descriptors.
	 */
	public void insertFileDescriptorAt(
			@UInt32 int index,
			AAFFileDescriptor fileDescriptor)
		throws NullPointerException,
			IndexOutOfBoundsException;

	/**
	 * <p>Returns the file descriptor at the given index through the list of file descriptors
	 * of this multiple descriptor.</p>
	 *
	 * @param index Index of the file descriptor to retrieve.
	 * @return File descriptor at the given index.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for
	 * the current list of file descriptors.
	 */
	public AAFFileDescriptor getFileDescriptorAt(
			@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Removes the file descriptor at the given index from the list of file descriptors
	 * of this multiple descriptor. Indices of other items in the list will be adjusted
	 * to a lower value to fill the gap left after the removal.</p>
	 *
	 * @param index Index of the file descriptor to remove.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for
	 * the current list of file descriptors.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#FileDescriptorStrongReferenceVector
	 */
	public void removeFileDescriptorAt(
	    	@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns a copy of the list of file descriptors of this multiple descriptor.</p>
	 *
	 * @return Copy of the list of file descriptors of this multiple descriptor.
	 */
	public List<? extends AAFFileDescriptor> getFileDescriptors();
	
	/**
	 * <p>Create a cloned copy of this multiple descriptor.</p>
	 *
	 * @return Cloned copy of this multiple descriptor.
	 */
	public MultipleDescriptor clone();
}
