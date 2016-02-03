/* 
 **********************************************************************
 *
 * $Id: SearchByAUID.java,v 1.7 2011/01/04 10:39:03 vizigoth Exp $
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
 * $Log: SearchByAUID.java,v $
 * Revision 1.7  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:52:02  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/01/14 16:07:39  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.3  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.2  2007/11/15 12:52:48  vizigoth
 * Edits to ensure source can make rough and ready javadoc.
 *
 * Revision 1.1  2007/11/13 22:15:01  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.record.AUID;

/** 
 * <p>Specifies a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} defined by an 
 * {@linkplain tv.amwa.maj.record.AUID AUID} value. The type of search represented by this 
 * criteria must be set using method {@link #setTag(SearchTag)}.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */

public interface SearchByAUID extends SearchCriteria {

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.record.AUID AUID} that defines 
	 * this search criteria.</p>
	 *
	 * @return AUID of this search criteria.
	 */
	public AUID getAUID();

	/**
	 * <p>Sets the {@linkplain tv.amwa.maj.record.AUID AUID} that defines this 
	 * search criteria.</p>
	 *
	 * @param identification AUID of this search criteria.
	 * 
	 * @throws NullPointerException The given identification is <code>null</code>.
	 */
	public void setAUID(
			AUID identification) 
		throws NullPointerException;

	/**
	 * <p>Set the search type of this search criteria that matches items using an AUID. The tag must be 
	 * compatible with matching using AUID values. If this search is not compatible, an 
	 * {@link IllegalArgumentException} is thrown. Acceptable tags are:</p>
	 * 
	 * <ul>
	 *  <li>{@link SearchTag#ByClass}</li>
	 *  <li>{@link SearchTag#ByDataDef}</li>
	 *  <li>{@link SearchTag#ByUsageCode}</li>
	 *  <li>{@link SearchTag#ByCompositionPackageUsageCode}</li>
	 *  <li>{@link SearchTag#ByMaterialPackageUsageCode}</li>
	 *  <li>{@link SearchTag#BySourcePackageUsageCode}</li>
	 * </ul>
	 *  
	 * @param tag Tag that identifies the kind of this search by AUID criteria.
	 * 
	 * @throws NullPointerException The given search tag is <code>null</code>.
	 * @throws IllegalArgumentException The search tag is not compatible with criteria matching using 
	 * AUID values.
	 */
	public void setTag(
			SearchTag tag)
		throws NullPointerException,
			IllegalArgumentException;
}
