/* 
 **********************************************************************
 *
 * $Id: InvalidParameterException.java,v 1.3 2011/01/04 10:41:20 vizigoth Exp $
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
 * $Log: InvalidParameterException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:48  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:09:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: Anywhere that registers items in the dictionary and Dictionary

/** 
 * <p>Thrown whenever a method call parameter contains a value that is invalid in the 
 * current context. For example, when looking up a 
 * {@linkplain tv.amwa.maj.model.DefinitionObject definition} in the 
 * {@linkplain tv.amwa.maj.model.Dictionary dictionary} using an identifier, if 
 * no definition of the appropriate type is available then this 
 * exception is thrown.</p>
 * 
 * <p>Note that this exception if different from {@link java.lang.IllegalArgumentException}
 * as this exception is thrown when a parameter is invalid in the current context, whereas
 * {@link java.lang.IllegalArgumentException IllegalArgumentException} is thrown when a
 * parameter is illegal in any context. For example, looking up a definition that is
 * not in a dictionary is <em>invalid</em> if the definition is not currently in that
 * dictionary. Trying to set a non-negative parameter to a negative value is 
 * <em>illegal</em>.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_INVALID_PARAM 0x8012016C</code></p>
 * 
 * @see tv.amwa.maj.model.Dictionary
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class InvalidParameterException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 2313111434771134011L;

	/**
	 * <p>Create a new invalid parameter exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public InvalidParameterException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new invalid parameter exception with no message.</p>
	 */
	public InvalidParameterException() {
		super();
	}
	
}
