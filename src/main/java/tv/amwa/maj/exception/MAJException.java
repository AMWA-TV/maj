/* 
 **********************************************************************
 *
 * $Id: MAJException.java,v 1.1 2007/11/27 20:38:14 vizigoth Exp $
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
 * $Log: MAJException.java,v $
 * Revision 1.1  2007/11/27 20:38:14  vizigoth
 * Edited javadoc comments to release standard.
 *
 */

package tv.amwa.maj.exception;

import java.io.Serializable;

/**
 * <p>Interface implemented by all exceptions that are specific to the MAJ API.
 * For more in depth discussion, see the <a href="package-summary.html#aafSpecificExceptions">the
 * package description section on design philosophy</a>.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public interface MAJException  
	extends Serializable {

}
