/* 
 **********************************************************************
 *
 * $Id: IdentificationCriteriaByIdProductIDImpl.java,v 1.1 2011/01/04 10:40:23 vizigoth Exp $
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
 * $Log: IdentificationCriteriaByIdProductIDImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:49  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.record.AUID;
import tv.amwa.maj.union.IdentificationCriteriaType;


/** 
 * <p>Implementation of a criteria for matching an {@linkplain tv.amwa.maj.model.Identification identification}
 * by its product id.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
public class IdentificationCriteriaByIdProductIDImpl
	extends IdentificationCriteriaImpl
	implements tv.amwa.maj.union.IdentificationCriteriaByIdProductID,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = -3107880597678440672L;

	private AUID productID;
	
	/** 
	 * <p>Create a criteria for matching an identification defined by the given 
	 * product id.</p>
	 * 
	 * @param productId Product identifier defining the new identification criteria.
	 *
	 * @throws NullPointerException The given product id is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.model.Identification#getApplicationProductID()
	 */
	public IdentificationCriteriaByIdProductIDImpl(
			tv.amwa.maj.record.AUID productId) 
		throws NullPointerException {

		super(IdentificationCriteriaType.ByIdProductID);
		setProductID(productId);
	}

	public AUID getProductID() {

		return productID;
	}

	public void setProductID(
			tv.amwa.maj.record.AUID productId) 
		throws NullPointerException {

		if (productId == null)
			throw new NullPointerException("The given product id for this identification criteria is null.");
		
		this.productID = productId.clone();
	}

	@Override
	public boolean equals(Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof tv.amwa.maj.union.IdentificationCriteriaByIdProductID)) return false;
		
		return productID.equals(((tv.amwa.maj.union.IdentificationCriteriaByIdProductID) o).getProductID());
	}
	
	/**
	 * <p>Pseudo-XML representation of this identification criteria. No corresponding XML schema or DTD is defined.
	 * For example:</p>
	 * 
	 * <pre>
	 * &lt;IdentificationCriteria productId="urn:uuid:7ab65789-dfed-7891-f6b7-a8d67892"/&gt;
	 * </pre>
	 * 
	 * @return String representation of an identification criteria defined by product id.
	 */

	@Override
	public String toString() {

		return "<IdenficationCriteria productId=\"" + productID.toString() + "\"/>";
	}

	@Override
	public IdentificationCriteriaByIdProductIDImpl clone() 
		throws CloneNotSupportedException {
		
		return (IdentificationCriteriaByIdProductIDImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return productID.hashCode();
	}
}
