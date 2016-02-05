/*
 * Copyright 2016 Richard Cartwright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * $Log: IdentificationCriteriaByIdRefImplVersionImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:49  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.record.ProductVersion;
import tv.amwa.maj.union.IdentificationCriteriaType;

/** 
 * <p>Implementation of a criteria for matching an {@linkplain tv.amwa.maj.model.Identification identification}
 * by its reference implementation version, also known as its toolkit version.<p>
 *
 *
 */
public class IdentificationCriteriaByIdRefImplVersionImpl 
	extends IdentificationCriteriaImpl
	implements tv.amwa.maj.union.IdentificationCriteriaByIdRefImplVersion,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 6276353474768569265L;
	
	/** <p>Reference implementation version defining this identification criteria.</p> */
	private ProductVersion referenceImplementationVersion;
	
	/** 
	 * <p>Create an identification criteria by reference implementation
	 * version.</p>
	 *  
	 * @param referenceImplementationVersion Product version of a reference implementation.
	 * 
	 * @throws NullPointerException The given product version value is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.model.Identification#getRefImplVersion()
	 */
	
	public IdentificationCriteriaByIdRefImplVersionImpl(
			tv.amwa.maj.record.ProductVersion referenceImplementationVersion) 
		throws NullPointerException {
		
		super(IdentificationCriteriaType.ByIdRefImplVersion);
		setReferenceImplementationVersion(referenceImplementationVersion);
	}

	public ProductVersion getReferenceImplementationVersion() {
		
		return referenceImplementationVersion;
	}

	public void setReferenceImplementationVersion(
			ProductVersion referenceImplementationVersion) 
		throws NullPointerException {
		
		if (referenceImplementationVersion == null)
			throw new NullPointerException("The given reference implementation version is null.");
		
		this.referenceImplementationVersion = referenceImplementationVersion.clone();
	}

	@Override
	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof tv.amwa.maj.union.IdentificationCriteriaByIdRefImplVersion)) return false;
		
		return referenceImplementationVersion.equals(
				((tv.amwa.maj.union.IdentificationCriteriaByIdRefImplVersion) o).getReferenceImplementationVersion());
	}
	
	/**
	 * <p>Pseudo-XML representation of this identification criteria. No corresponding XML schema or DTD is defined.
	 * For example:</p>
	 * 
	 * <pre>
	 * &lt;IdentificationCriteria&gt;
	 *   &lt;ProductVersion&gt;
	 *     &lt;major&gt;1&lt;/major&gt;
	 *     &lt;minor&gt;2&lt;/minor&gt;
	 *     &lt;tertiary&gt;3&lt;/tertiary&gt;
	 *     &lt;patchLevel&gt;4&lt;/patchLevel&gt;
	 *     &lt;type&gt;Debug&lt;/type&gt;
	 *   &lt;/ProductVersion&gt;
	 * &lt;/IdentificationCriteria&gt;
	 * </pre>
	 * 
	 * @return String representation of an identification criteria defined by reference implementation version.
	 */
	@Override
	public String toString() {
	
		String productVersionXML = referenceImplementationVersion.toString();
		int firstLineBreak = productVersionXML.indexOf('\n');
		productVersionXML = productVersionXML.substring(
				firstLineBreak + 1,
				productVersionXML.length() - 1);
		return "<IdentificationCriteria>\n  " + 
			productVersionXML.replace("\n", "\n  ") + 
			"\n</IdentificationCriteria>" ;
	}

	@Override
	public IdentificationCriteriaByIdRefImplVersionImpl clone() 
		throws CloneNotSupportedException {

		return (IdentificationCriteriaByIdRefImplVersionImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return referenceImplementationVersion.hashCode();
	}
}
