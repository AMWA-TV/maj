/* 
 **********************************************************************
 *
 * $Id: TapeCaseType.java,v 1.7 2011/02/14 22:32:58 vizigoth Exp $
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
 * $Log: TapeCaseType.java,v $
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
 * Revision 1.2  2008/01/08 17:01:54  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:44  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Describes the physical size of a tape.</p> 
 * 
 * <p>Original C name: <code>aafTapeCaseType_e</code></p>
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#TapeCaseType
 * @see tv.amwa.maj.model.TapeDescriptor#getTapeFormFactor()
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */

public enum TapeCaseType 
	implements MediaEnumerationValue {

    /**
     * <p>Tape case type not specified.</p>
     */
    Null (0), 
    /**
     * <p>Three and a quarter inch video tape case.</p>
     */
    ThreeFourthInchVideoTape (1), 
    /**
     * <p>VHS video tape case.</p>
     */
    VHSVideoTape (2), 
    /**
     * <p>8mm video tape case.</p>
     * 
     * <p>Note that the name of this element starts with an underscore ('<code>_</code>')
     * character as Java identifiers must start with an alphanumeric character or an underbar character. The
     * specified name of this value is "<code>8mmVideoTape</code>".</p>
     */
    _8mmVideoTape (3), 
    /**
     * <p>Betacam video tape case.</p>
     */
    BetacamVideoTape (4), 
    /**
     * <p>Compact audio cassette case.</p>
     */
    CompactCassette (5), 
    /**
     * <p>DAT cartridge tape case.</p>
     */
    DATCartridge (6), 
    /**
     * <p>Nagra format reel-to-reel tape case.</p>
     */
    NagraAudioTape (7), 
    ;

    private final int value;

    TapeCaseType (int value) { this.value = value; }

    @Int64 
    public long value() { return (long) value; }
    
    public String symbol() {
    	
    	switch (this) {
    	
    	case Null:
    		return "TapeCaseNull";
    	case _8mmVideoTape:
    		return "8mmVideoTape";
    	default:
    		return name();
    	}
    }
}
