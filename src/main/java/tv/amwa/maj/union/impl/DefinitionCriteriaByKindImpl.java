/* 
 **********************************************************************
 *
 * $Id: DefinitionCriteriaByKindImpl.java,v 1.1 2011/01/04 10:40:23 vizigoth Exp $
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
 * $Log: DefinitionCriteriaByKindImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:32  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.DefinitionKind;
import tv.amwa.maj.union.DefinitionCriteriaType;

// FIXME possible mistake here ... definition kind enumeration lots like it can be used to build bit fields.

/** 
 * <p>Implementation of a criteria for matching a {@linkplain tv.amwa.maj.model.DefinitionObject definition} determined 
 * by its {@linkplain tv.amwa.maj.enumeration.DefinitionKind kind}, such as data definition, property 
 * definition etc..</p>
 * 
 * @see tv.amwa.maj.model.DefinitionObject
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */

public class DefinitionCriteriaByKindImpl 
	extends DefinitionCriteriaImpl 
	implements tv.amwa.maj.union.DefinitionCriteriaByKind,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = -8559961281737660409L;
	
	/**
	 * <p>Kind of definition this criteria should match.</p>
	 */
	private DefinitionKind kind;
	
	/** 
	 * <p>Create a definition criteria with the given kind of definition to match.</p>
	 * 
	 * @param definitionKind Kind of definition criteria.
	 */
	public DefinitionCriteriaByKindImpl(
			DefinitionKind definitionKind) 
		throws NullPointerException {
		
		super(DefinitionCriteriaType.ByKind);
		setKind(definitionKind);
	}

	public DefinitionKind getKind() {
		return kind;
	}

	public void setKind(
			DefinitionKind kind) 
		throws NullPointerException {
		
		if (kind == null)
			throw new NullPointerException("The given definition kind for this criteria for matching definitions is null.");
		this.kind = kind;
	}

	@Override
	public boolean equals(Object o) {
		
		if (o == null) return false;
		if (o == this) return true;
		
		if (!(o instanceof tv.amwa.maj.union.DefinitionCriteriaByKind)) return false;
		
		return ((tv.amwa.maj.union.DefinitionCriteriaByKind) o).getKind() == kind;
	}
	
	/**
	 * <p>Creates a pseudo-XML representation of this definition criteria. No XML schema or
	 * DTD is defined. For example:</p>
	 * 
	 * <pre>
	 * &lt;DefinitionCriteria kind="PropertyDefinition"/&gt;
	 * </pre>
	 */
	@Override
	public String toString() {

		return "<DefinitionCriteria kind=\"" + kind.name() + "\"/>";
	}

	@Override
	public DefinitionCriteriaByKindImpl clone() 
		throws CloneNotSupportedException {
		
		return (DefinitionCriteriaByKindImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return kind.hashCode();
	}
}
