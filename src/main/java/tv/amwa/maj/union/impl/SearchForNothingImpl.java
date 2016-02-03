/* 
 **********************************************************************
 *
 * $Id: SearchForNothingImpl.java,v 1.1 2011/01/04 10:40:23 vizigoth Exp $
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
 * $Log: SearchForNothingImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:47  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.union.SearchTag;

// TODO tests

/** 
 * <p>Implementation of a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} 
 * that does not match anything.</p> 
 *  
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
public class SearchForNothingImpl 
	extends SearchCriteriaImpl
	implements tv.amwa.maj.union.SearchForNothing,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = -998758444831340804L;

	/** 
	 * <p>Create a search criteria that does not match any packages.</p>
	 *
	 */
	public SearchForNothingImpl () {
		super(SearchTag.NoSearch);
	}

	@Override
	public boolean equals(
			Object o) {
		
		if (o == null) return false;
		if (o == this) return true;
		
		return o instanceof tv.amwa.maj.union.SearchForNothing;
	}

	/**
	 * <p>Create a pseudo-XML representation of this search for nothing. No XML
	 * schema or DTD defines this element. Always returns:</p>
	 * 
	 * <pre>
	 * &lt;SearchForNothing /&gt;
	 * </pre>
	 * 
	 * @return XML representation of this search for nothing.
	 */
	@Override
	public String toString() {

		return "<SearchForNothing />";
	}

	@Override
	public SearchForNothingImpl clone() {

		return this;
	}

	@Override
	public int hashCode() {

		return 0;
	}
}
