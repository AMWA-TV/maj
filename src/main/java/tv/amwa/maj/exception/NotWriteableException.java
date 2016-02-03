/* 
 **********************************************************************
 *
 * $Id: NotWriteableException.java,v 1.2 2007/11/27 20:37:53 vizigoth Exp $
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
 * $Log: NotWriteableException.java,v $
 * Revision 1.2  2007/11/27 20:37:53  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:11:05  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: AAFFile, RandomFile, RandomRawStorage, RawStorage - consider removing?

/** 
 * <p>Thrown when it is not possible to write to a file on the filing system.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_NOT_WRITEABLE 0x80120026</code></p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class NotWriteableException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 6139415946987282586L;

	/**
	 * <p>Create a new not writeable exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public NotWriteableException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new not writeable exception with no message.</p>
	 */
	public NotWriteableException() {
		super();
	}
	
}
