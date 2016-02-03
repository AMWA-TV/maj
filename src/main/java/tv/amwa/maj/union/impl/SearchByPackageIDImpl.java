/* 
 **********************************************************************
 *
 * $Id: SearchByPackageIDImpl.java,v 1.1 2011/01/04 10:40:23 vizigoth Exp $
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
 * $Log: SearchByPackageIDImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:31  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.union.SearchTag;

// TODO tests

/** 
 * <p>Implementation of a search criteria for matching {@linkplain tv.amwa.maj.model.Package packages} 
 * defined by a {@linkplain tv.amwa.maj.record.PackageID package id}.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class SearchByPackageIDImpl 
	extends SearchCriteriaImpl
	implements tv.amwa.maj.union.SearchByPackageID,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 8271136559846688257L;
	
	/** <p>Package id defining this search criteria.</p> */
	private PackageID packageID;
	
	/** 
	 * <p>Create a search criteria defined by a package id. </p>
	 * 
	 * @param packageID Package id to search for.
	 * 
	 * @throws NullPointerException The given package id is <code>null</code>.
	 */
	public SearchByPackageIDImpl (
			PackageID packageID) 
		throws NullPointerException {
		
		super(SearchTag.ByPackageID);
		setPackageID(packageID);
	}

	public PackageID getPackageID() {
		
		return packageID;
	}

	public void setPackageID(
			PackageID packageID) 
		throws NullPointerException {
		
		if (packageID == null)
			throw new NullPointerException("The given package id for this search by package id criteria is null.");
		
		this.packageID = packageID.clone();
	}

	@Override
	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof tv.amwa.maj.union.SearchByPackageID)) return false;
		
		return packageID.equals(((tv.amwa.maj.union.SearchByPackageID) o).getPackageID());
	}
	
	/**
	 * <p>Create a pseudo-XML representation of this search by package id criteria. No XML
	 * schema or DTD defines this element. For example:</p>
	 * 
	 * <pre>
	 * &lt;SearchByPackageID packageID="urn:x-umid:0102030405060708090a0b0c-0d-0e0f10-1112131415161718191a1b1c1d1e1f20"/&gt;
	 * </pre>
	 * 
	 * @return XML representation of this search by package id criteria.
	 */
	@Override
	public String toString() {

		return "<SearchByPackageID packageID=\"" + packageID.toString() + "\"/>";
	}

	@Override
	public SearchByPackageIDImpl clone() 
		throws CloneNotSupportedException {

		return (SearchByPackageIDImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return packageID.hashCode();
	}
}
