/* 
 **********************************************************************
 *
 * $Id: PropertyNotPresentException.java,v 1.3 2011/01/04 10:41:20 vizigoth Exp $
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
 * $Log: PropertyNotPresentException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:38:15  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:09:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: everywhere!
// TODO add documentation link to discussion of optional property handling in Java (when written!)

/** 
 * <p>Thrown when an attempt is made to read the value of a property that is
 * omitted from an {@linkplain tv.amwa.maj.model.InterchangeObject interchange 
 * object}. An omitted property is one that is not persisted along with the 
 * mandatory properties of an {@linkplain tv.amwa.maj.model.InterchangeObject 
 * interchange object}.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_PROP_NOT_PRESENT 0x801200CF</code></p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class PropertyNotPresentException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -5884904324471488300L;

	/**
	 * <p>Create a new property not present exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public PropertyNotPresentException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new property not present exception with no message.</p>
	 */
	public PropertyNotPresentException() {
		super();
	}
}
