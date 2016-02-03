/* 
 **********************************************************************
 *
 * $Id: BadParameterException.java,v 1.2 2007/11/27 20:37:57 vizigoth Exp $
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
 * $Log: BadParameterException.java,v $
 * Revision 1.2  2007/11/27 20:37:57  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:48  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

/** 
 * <p>Thrown when a lookup operation fails because the given parameter cannot be
 * found in a known collection of values.</p>
 * 
 * <p>Note that this exception can be traced back to AAF SDK documentation only
 * and does not appear to have a result code.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
public class BadParameterException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -5759219330793904281L;

	/**
	 * <p>Create a new bad parameter exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public BadParameterException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new bad parameter exception with no message.</p>
	 */
	public BadParameterException() {
		super();
	}
}
