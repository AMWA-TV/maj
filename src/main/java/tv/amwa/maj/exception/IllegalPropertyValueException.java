/* 
 **********************************************************************
 *
 * $Id: IllegalPropertyValueException.java,v 1.3 2011/01/04 10:41:20 vizigoth Exp $
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
 * $Log: IllegalPropertyValueException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.1  2007/11/27 20:38:04  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:08  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: All type definitions and digital image descriptor

/** 
 * <p>Thrown when a parameter to a method call of an object is outside the
 * acceptable range or of the wrong type for that object in its current state. 
 * This exception can be thrown
 * for all {@linkplain tv.amwa.maj.meta.PropertyDefinition property definitions}
 * passed to methods of a {@linkplain tv.amwa.maj.meta.TypeDefinition type definition}
 * if the type of the property does not match the defined type.</p>
 *
 * <p>Equivalent C result code: <code>AAFRESULT_ILLEGAL_VALUE 0x8012016A</code></p>
 *
 * @see tv.amwa.maj.model.PictureDescriptor
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class IllegalPropertyValueException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -7511766518884649301L;

	/**
	 * <p>Create a new illegal value exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public IllegalPropertyValueException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new illegal value exception with no message.</p>
	 */
	public IllegalPropertyValueException() {
		super();
	}	
}
