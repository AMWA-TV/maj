/* 
 **********************************************************************
 *
 * $Id: FilmType.java,v 1.7 2011/02/14 22:32:58 vizigoth Exp $
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
 * $Log: FilmType.java,v $
 * Revision 1.7  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.4  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/08 17:01:51  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:14:03  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies the format of a film. The make "<code>35MM</code>" into a valid Java identifier,
 * all elements are all preceded by "<code>Ft</code>" to become "<code>Ft35MM</code>".</p>
 * 
 * <p>Original C name: <code>aafFilmType_e</code></p>
 * 
 * @see tv.amwa.maj.model.FilmDescriptor#getFilmFormat()
 * @see tv.amwa.maj.industry.TypeDefinitions#FilmType
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */

public enum FilmType 
	implements MediaEnumerationValue {

	/** 
	 * <p>Invalid film type.</p> 
	 */   
	FtNull (0), 
	/** 
	 * <p>35 millimetre film.</p>
	 */  
	Ft35MM (1), 
	/** 
	 * <p>16 millimetre film.</p>
	 */  
	Ft16MM (2), 
	/** 
	 * <p>8 millimetre film.</p> 
	 */   
	Ft8MM (3), 
    /** 
     * <p>65 millimetre film.</p>
     */    
	Ft65MM (4), 
    ;

    private final int value;

    FilmType (int value) { this.value = value; }

    @Int64 public long value() { return (long) value; }
    
    public String symbol() { return name(); }

}
