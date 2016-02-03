/* 
 **********************************************************************
 *
 * $Id: DefinitionKind.java,v 1.6 2011/02/14 22:32:58 vizigoth Exp $
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
 * $Log: DefinitionKind.java,v $
 * Revision 1.6  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.5  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.3  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/08 17:01:54  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:53  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

// TODO the spec says enumeration values cannot be negative and uses an Int64 value

/** 
 * <p>Specifies the kind of a definition, such as {@linkplain tv.amwa.maj.meta.ClassDefinition Class}, 
 * {@linkplain tv.amwa.maj.meta.PropertyDefinition Property}, 
 * {@linkplain tv.amwa.maj.model.OperationDefinition Operation} etc.. Values of this enumeration
 * are used to define search criteria for search operations.</p>
 * 
 * <p>Original C name: <code>aafDefinitionKind_e</code></p>
 * 
 * @see tv.amwa.maj.union.DefinitionCriteriaByKind
 * @see tv.amwa.maj.model.DefinitionObject
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */

public enum DefinitionKind 
	implements MediaEnumerationValue {

	/** 
	 * <p>Matches all definitions.<p>
	 */
	AllDefinitions (-1), 
	/** 
	 * <p>Matches {@linkplain tv.amwa.maj.meta.ClassDefinition class definitions}.</p>
	 */
	ClassDefinition (1), 
	/** 
	 * <p>Matches {@linkplain tv.amwa.maj.meta.PropertyDefinition property definitions}.</p> 
	 */
	PropertyDefinition (2), 
	/** 
	 * <p>Matches {@linkplain tv.amwa.maj.meta.TypeDefinition type definitions}.</p> 
	 */
	TypeDefinition (4), 
	/** 
	 * <p>Matches {@linkplain tv.amwa.maj.model.DataDefinition data definitions}.</p> 
	 */
	DataDefinition (8), 
	/** 
	 * <p>Matches {@linkplain tv.amwa.maj.model.OperationDefinition operation definitions}.</p> 
	 */
    OperationDefinition (16), 
    ;

    private final int value;

    DefinitionKind (int value) { this.value = value; }

    @Int64 public long value() { return (long) value; }
    
    public String symbol() { return name(); }

}
