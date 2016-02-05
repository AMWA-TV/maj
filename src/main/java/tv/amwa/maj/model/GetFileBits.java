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
 * $Log: GetFileBits.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/01/27 11:07:26  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:22  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.OffsetSizeException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.misctype.DataBuffer;

// TODO comments and working out whether to keep this

/**
 * <p>Specifies a mechanism to obtain the raw bits of a file
 * after close.</p>
 * 
 * <p>Note that this and the {@link SetFileBits} interfaces are different
 * than the {@link RawStorage} interfaces in that those are implemented by
 * clients to provide access to the desired media; these are provided
 * by the toolkit to provide read OR write (but not both simultaneously) 
 * access to the raw bits.</p>
 * 
 *
 *
 */

public interface GetFileBits {

	/* [
	    object,
	    uuid(3CC80282-72A8-11D4-B80D-0000863F2C27),
	    helpstring("IAAFGetFileBits Interface"),
	    pointer_default(unique)
	] */

	//***********************************************************
	  //
	  // ReadAt()
	  //
	  /// Attempts to read bufsize bytes from this stream at the given byte
	  /// offset.  Places the data into buf.  Any attempt to read beyond
	  /// the size of this file, as reported by GetSize(), will be
	  /// considered an error.
	  ///
	  /// A value of zero for position indicates the beginning of the
	  /// stream.
	  ///
	  /// Succeeds if:
	  /// - The pNumBytes pointer is valid.
	  /// - position + buSize is not larger than the size of this file, as
	  ///   reported by GetSize().
	  ///
	  /// This method will return the following codes.  If more than one of
	  /// the listed errors is in effect, it will return the first one
	  /// encountered in the order given below:
	  /// 
	  /// AAFRESULT_SUCCESS
	  ///   - succeeded.  (This is the only code indicating success.)
	  ///
	  /// AAFRESULT_NULL_PARAM
	  ///   - buf arg is NULL.
	  ///
	  /// AAFRESULT_OFFSET_SIZE
	  ///   - position + size is larger than the size of this file.
	  /// 
	  /// @param buf [out, size_is(bufSize)] Buffer into which data is read
	  /// @param bufSize [in] Size of buf in bytes
	  /// @param position [in] The position in bytes at which to read
	  ///
	  /* HRESULT ReadAt (
	    [out, size_is(bufSize)] aafMemPtr_t  buf,
	    [in] aafUInt32  bufSize,
	    [in] aafUInt64  position); */
	
	/**
	 * <p>Attempts to read bufsize bytes from this stream at the given byte
	 * offset and returns them as a byte array. Any attempt to read beyond
	 * the size of this file, as reported by {@link #getSize()}, will be
	 * considered an error.</p>
	 * 
	 * <p>A value of zero for position indicates the beginning of the
	 * stream.</p>
	 * 
	 * @param bufSize Size of the buffer to read into.
	 * @param position The position into the file from which to read.
	 * @return Buffer containing bytes read from the file.
	 * 
	 * @throws OffsetSizeException The sum <code>position + bufSize</code>
	 * is larger than the size of the file.
	 */
	public @DataBuffer byte[] readAt(
			@UInt32 int bufSize,
			@UInt64 long position) throws OffsetSizeException;

	  //***********************************************************
	  //
	  // GetSize()
	  //
	  /// Returns the size of this file, in bytes, in *pSize.
	  ///
	  /// Succeeds if:
	  /// - The pSize pointer is valid.
	  ///
	  /// This method will return the following codes.  If more than one of
	  /// the listed errors is in effect, it will return the first one
	  /// encountered in the order given below:
	  /// 
	  /// AAFRESULT_SUCCESS
	  ///   - succeeded.  (This is the only code indicating success.)
	  ///
	  /// AAFRESULT_NULL_PARAM
	  ///   - pSize arg is NULL.
	  /// 
	  /// @param pSize [out] The size of this file in bytes
	  ///
	  /* HRESULT GetSize (
	    [out] aafUInt64 *  pSize); */
	/**
	 * <p>Returns the size of the file in bytes.</p>
	 * 
	 * @return The size of this file in bytes.
	 */
	public @UInt64 long getSize();
}

