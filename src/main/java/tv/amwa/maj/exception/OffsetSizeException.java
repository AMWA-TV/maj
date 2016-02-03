/* 
 **********************************************************************
 *
 * $Id: OffsetSizeException.java,v 1.2 2007/11/27 20:37:53 vizigoth Exp $
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
 * $Log: OffsetSizeException.java,v $
 * Revision 1.2  2007/11/27 20:37:53  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:21  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: GetFileBits and RandomRawStorage - condider removing?

/** 
 * <p>Thrown when a given offset exceeds the size of a file or the available
 * space to extend storage for a file.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_OFFSET_SIZE 0x8012015F</code></p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class OffsetSizeException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -318441014772177192L;

	/**
	 * <p>Create a new offset size exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public OffsetSizeException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new offset size exception with no message.</p>
	 */
	public OffsetSizeException() {
		super();
	}
	
}
