/* 
 **********************************************************************
 *
 * $Id: SearchCriteriaImpl.java,v 1.1 2011/01/04 10:40:23 vizigoth Exp $
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
 * $Log: SearchCriteriaImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/12/18 17:56:01  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:40  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.union.SearchTag;


/**
 * <p>Implementation of a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} 
 * specified in a number of different ways.</p>
 * 
 * @see tv.amwa.maj.model.ContentStorage#getPackages(SearchCriteriaImpl)
 * @see tv.amwa.maj.model.Preface#getPackages(SearchCriteriaImpl)
 * @see SearchTag
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */

public abstract class SearchCriteriaImpl 
	implements tv.amwa.maj.union.SearchCriteria,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6749290822532107160L;
	
	/** Kind of search criteria. */
	private SearchTag tag;
	
	SearchCriteriaImpl() { }
	
	/**
	 * <p>Create a search criteria of the specified type.
	 * 
	 * @param tag Type of search criteria.
	 */
	SearchCriteriaImpl(SearchTag tag) {
		this.tag = tag;
	}

	public SearchTag getSearchTag() {
		return tag;
	}

	void setSearchTag(SearchTag tag) {
		this.tag = tag;
	}
}
