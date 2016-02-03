/* 
 **********************************************************************
 *
 * $Id: InsufficientSpaceException.java,v 1.3 2009/12/18 17:56:03 vizigoth Exp $
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
 * $Log: InsufficientSpaceException.java,v $
 * Revision 1.3  2009/12/18 17:56:03  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.2  2007/11/27 20:37:59  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:09:57  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: AAFFile - consider removing?

/** 
 * <p>Thrown when not enough space is available on the filing system or in a given
 * buffer to save data.</p>
 * 
 * <p>No equivalent C result code.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class InsufficientSpaceException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -1741561437252529119L;

	/**
	 * <p>Create a new insufficient space exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public InsufficientSpaceException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new insufficient space exception with no message.</p>
	 */
	public InsufficientSpaceException() {
		super();
	}
}
