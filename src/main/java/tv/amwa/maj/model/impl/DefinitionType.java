/* 
 **********************************************************************
 *
 * $Id: DefinitionType.java,v 1.1 2011/01/04 10:39:03 vizigoth Exp $
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
 * $Log: DefinitionType.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:08  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import tv.amwa.maj.exception.PropertyNotPresentException;

/**
 * <p>Interface supported by all {@linkplain tv.amwa.maj.model.DefinitionObject definition objects}
 * and {@linkplain tv.amwa.maj.meta.MetaDefinition meta definition objects}. These kinds of objects
 * are identified by a {@linkplain tv.amwa.maj.record.AUID AUID}, have a name and an optional
 * description.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public interface DefinitionType {

	/**
	 * <p>Returns the identification of the definition. This is normally a registered SMPTE universal
	 * label.</p>
	 * 
	 * @return Identification of the definition.
	 */
	public tv.amwa.maj.record.AUID getAUID();
	
	/**
	 * <p>Returns the name of the definition.</p>
	 * 
	 * @return Name of the definition.
	 */
	public String getName();
	
	/**
	 * <p>Returns the optional description of the definition.</p>
	 * 
	 * @return Description of the definition.
	 * @throws PropertyNotPresentException The optional name property is not present for the
	 * definition.
	 */
	public String getDescription()
		throws PropertyNotPresentException;
	
}
