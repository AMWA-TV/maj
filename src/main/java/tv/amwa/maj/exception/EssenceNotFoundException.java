/* 
 **********************************************************************
 *
 * $Id: EssenceNotFoundException.java,v 1.4 2011/01/04 10:41:20 vizigoth Exp $
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
 * $Log: EssenceNotFoundException.java,v $
 * Revision 1.4  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:37:44  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:54  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// Reference: ContentStorage, Preface

/** 
 * <p>Thrown when an attempt is made to remove some data from 
 * {@linkplain tv.amwa.maj.model.ContentStorage content storage} that is not
 * contained within that storage.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_ESSENCE_NOT_FOUND 0x80120129</code></p>
 *
 * @see tv.amwa.maj.model.ContentStorage
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class EssenceNotFoundException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -4944010520539058173L;

	/**
	 * <p>Create a new essence not found exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public EssenceNotFoundException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new essence not found exception with no message.</p>
	 */
	public EssenceNotFoundException() {
		super();
	}

}
