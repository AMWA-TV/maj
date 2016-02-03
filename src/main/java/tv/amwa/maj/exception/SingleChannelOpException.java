/* 
 **********************************************************************
 *
 * $Id: SingleChannelOpException.java,v 1.2 2007/11/27 20:37:44 vizigoth Exp $
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
 * $Log: SingleChannelOpException.java,v $
 * Revision 1.2  2007/11/27 20:37:44  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:11:09  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: EssenceAccess - consider removing?

/** 
 * <p>Thrown when an attempt is made to write samples into an essence stream
 * that contains interleaved data.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_SINGLE_CHANNEL_OP 0x80120081</code></p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class SingleChannelOpException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 7990766263719886525L;

	/**
	 * <p>Create a new single channel operation exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public SingleChannelOpException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new single channel operation exception with no message.</p>
	 */
	public SingleChannelOpException() {
		super();
	}
}
