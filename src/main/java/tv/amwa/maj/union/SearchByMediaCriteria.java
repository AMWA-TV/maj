/* 
 **********************************************************************
 *
 * $Id: SearchByMediaCriteria.java,v 1.5 2011/01/04 10:39:03 vizigoth Exp $
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
 * $Log: SearchByMediaCriteria.java,v $
 * Revision 1.5  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/14 16:07:35  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:12  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.enumeration.CriteriaType;

/**
 * <p>Specifies a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} defined by media 
 * criteria. This can be used to search for a version of media that is the best fidelity, smallest representation etc. 
 * as defined by a {@linkplain tv.amwa.maj.enumeration.CriteriaType criteria type}.</p>
 * 
 * @see tv.amwa.maj.enumeration.CriteriaType
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
public interface SearchByMediaCriteria 
	extends SearchCriteria {
	
	/**
	 * <p>Returns the media criteria search type.</p>
	 *
	 * @return Media criteria search type.
	 */
	public CriteriaType getMediaCriteria();

	/**
	 * <p>Sets the media criteria search type.</p>
	 *
	 * @param mediaCriteria Media criteria search type.
	 * 
	 * @throws NullPointerException The given media criteria is <code>null</code>.
	 */
	public void setMediaCriteria(
			CriteriaType mediaCriteria) 
		throws NullPointerException;
}
