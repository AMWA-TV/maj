/* 
 **********************************************************************
 *
 * $Id: InvalidLinkageException.java,v 1.3 2011/01/04 10:41:20 vizigoth Exp $
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
 * $Log: InvalidLinkageException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:53  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:44  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: EssenceGroup - consider removing as to do with searching?

/** 
 * <p>Thrown when traversal of the content in an {@linkplain tv.amwa.maj.model.EssenceGroup
 * essence group} is not possible due to an invalid link.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_INVALID_LINKAGE 0x801200CA</code></p>
 * 
 * @see tv.amwa.maj.model.EssenceGroup
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class InvalidLinkageException
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -3060468530387777291L;

	/**
	 * <p>Create a new invalid linkage exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public InvalidLinkageException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new invalid linkage exception with no message.</p>
	 */
	public InvalidLinkageException() {
		super();
	}
}
