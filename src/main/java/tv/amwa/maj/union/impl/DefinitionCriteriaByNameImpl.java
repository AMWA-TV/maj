/* 
 **********************************************************************
 *
 * $Id: DefinitionCriteriaByNameImpl.java,v 1.1 2011/01/04 10:40:23 vizigoth Exp $
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
 * $Log: DefinitionCriteriaByNameImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:28  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.union.DefinitionCriteriaType;


/** 
 * <p>Implementation of a criteria for matching a {@linkplain tv.amwa.maj.model.DefinitionObject definition} by its name.</p>
 * 
 * @see tv.amwa.maj.model.DefinitionObject#getName()
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class DefinitionCriteriaByNameImpl 
	extends DefinitionCriteriaImpl
	implements tv.amwa.maj.union.DefinitionCriteriaByName,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = -8644460018195338020L;
	
	/** <p>Name of definition to match.</p> */
	@AAFString private String name;

	/**
	 * <p>Create a criteria for matching a definition by name.</p>
	 * 
	 * @param name Name of definition to match.
	 * 
	 * @throws NullPointerException The given definition name is <code>null</code>.
	 */
	public DefinitionCriteriaByNameImpl(
			String name) 
		throws NullPointerException {
		
		super(DefinitionCriteriaType.ByName);
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(
			String name) 
		throws NullPointerException {

		if (name == null)
			throw new NullPointerException("The given definition name for this definition criteria is null.");
		this.name = name;
	}

	@Override
	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		
		return false;
	}
	
	/**
	 * <p>Creates a pseudo-XML representation of this definition criteria. No XML schema or
	 * DTD is defined. For example:</p>
	 * 
	 * <pre>
	 * &lt;DefinitionCriteria name="Picture"/&gt;
	 * </pre>
	 */
	@Override
	public String toString() {

		return "<DefinitionCriteria name=\"" + name + "\">";
	}

	public DefinitionCriteriaByNameImpl clone() 
		throws CloneNotSupportedException {
		
		return (DefinitionCriteriaByNameImpl) super.clone();
	}
	
	@Override
	public int hashCode() {

		return name.hashCode();
	}
}
