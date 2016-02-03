/* 
 **********************************************************************
 *
 * $Id: NotValidKeyException.java,v 1.3 2011/01/04 10:41:20 vizigoth Exp $
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
 * $Log: NotValidKeyException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:54  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:00  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: AAFFile, RandomFile, RandomRawStorage, RawStorage - consider removing?

/** 
 * <p>Thrown when creating a {@linkplain tv.amwa.maj.model.KLVData KLV data} key that does not 
 * map to a built-in type.</p>
 * 
 * <p>No equivalent C result code could be found.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class NotValidKeyException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -4640956702209886708L;

	/**
	 * <p>Create a new not a valid key exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public NotValidKeyException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new not a valid key exception with no message.</p>
	 */
	public NotValidKeyException() {
		super();
	}
}
