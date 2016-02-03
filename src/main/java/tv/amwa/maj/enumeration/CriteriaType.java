/* 
 **********************************************************************
 *
 * $Id: CriteriaType.java,v 1.5 2011/01/04 10:40:23 vizigoth Exp $
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
 * $Log: CriteriaType.java,v $
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
 * Revision 1.1  2007/11/13 22:13:40  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>A criteria can be used to select media by the kind of representation for that
 * media. Criteria include the fastest representation, best fidelity or smallest storage space.</p>
 * 
 * @see tv.amwa.maj.model.Preface#getEssenceData(CriteriaType)
 * @see tv.amwa.maj.model.ContentStorage#getEssenceDataObjects(CriteriaType)
 * @see tv.amwa.maj.model.MaterialPackage
 *  
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
*/

public enum CriteriaType 
	implements MediaEnumerationValue {

    /**
     * <p>Any representation of the media is acceptable.</p>
     */
    AnyRepresentation (0), 
    /**
     * <p>A representation of the media that is fastest to process with the associated codec is preferred.</p>
     */
    FastestRepresentation (1), 
    /**
     * <p>A representation of the media with the best fidelity (highest quality) is preferred.</p>
     */
    BestFidelityRepresentation (2), 
    /**
     * <p>The version of the media that takes up the smallest amount of storage space is preferred.</p>
     */
    SmallestRepresentation (3), 
    ;

    private final int value;

    CriteriaType (int value) { this.value = value; }

    @Int64 public long value() { return value; }
    
    public String symbol() { return name(); }

}
