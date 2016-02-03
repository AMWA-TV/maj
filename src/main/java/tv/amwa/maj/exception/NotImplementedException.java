/* 
 **********************************************************************
 *
 * $Id: NotImplementedException.java,v 1.2 2011/01/21 12:36:03 vizigoth Exp $
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
 * $Log: NotImplementedException.java,v $
 * Revision 1.2  2011/01/21 12:36:03  vizigoth
 * Better as a runtime exception.
 *
 * Revision 1.1  2008/01/21 12:58:02  vizigoth
 * Added new not implemented exception to handle creatInstance() methods that do not fit with the MAJ API.
 *
 *
 */

package tv.amwa.maj.exception;

/**
 * <p>Thrown when a method is called that is not implemented in the MAJ API. The 
 * method will have been provided to help with the porting of applications from the
 * existing reference implementation and the documentation of the method will
 * provide a suitable alternative.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class NotImplementedException 
	extends RuntimeException 
	implements MAJException,
		NewForMAJ {

	private static final long serialVersionUID = 6630951533799908358L;

	/**
	 * <p>Create a new not implemented exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public NotImplementedException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new not implemented exception with no message.</p>
	 */
	public NotImplementedException() {
		super();
	}

	
}
