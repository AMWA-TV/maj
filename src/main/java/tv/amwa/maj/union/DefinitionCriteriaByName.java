/* 
 **********************************************************************
 *
 * $Id: DefinitionCriteriaByName.java,v 1.3 2011/01/04 10:39:03 vizigoth Exp $
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
 * $Log: DefinitionCriteriaByName.java,v $
 * Revision 1.3  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:15  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

/**
 * <p>Criteria for matching a {@linkplain tv.amwa.maj.model.DefinitionObject definition} by its name.</p>
 * 
 * @see tv.amwa.maj.model.DefinitionObject#getName()
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
public interface DefinitionCriteriaByName 
	extends DefinitionCriteria {

	/**
	 * <p>Returns the name defining this definition criteria.</p>
	 *
	 * @return Name defining this definition criteria.
	 */
	public String getName();

	/**
	 * <p>Sets the name defining this definition criteria.</p>
	 *
	 * @param name Name defining this definition criteria.
	 * 
	 * @throws NullPointerException The definition name is <code>null</code>.
	 */
	public void setName(
			String name) 
		throws NullPointerException;
}
