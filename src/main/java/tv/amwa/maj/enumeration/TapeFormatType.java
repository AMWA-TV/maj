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
 * $Log: TapeFormatType.java,v $
 * Revision 1.8  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.7  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/11/18 10:43:36  vizigoth
 * Fixed enumeration names to better match meta dictionary.
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
 * Revision 1.2  2008/01/08 17:01:52  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:14:07  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Describes the format of the tape.</p>
 * 
 * <p>Original C name: <code>aafTapeFormatType_e</code></p>
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#TapeFormatType
 * @see tv.amwa.maj.model.TapeDescriptor#getTapeFormat()
 * 
 *
 */

public enum TapeFormatType 
	implements MediaEnumerationValue {

    /**
     * <p>Tape format type not specified.</p>
     */
    TapeFormatNull (0), 
    /**
     * <p>Betacam tape format. The betacam format is a half-inch professional video tape format.</p>
     */
    BetacamFormat (1), 
    /**
     * <p>Betacam SP tape format. The SP is short form for <em>Superior Performance</em> offered by
     * improvements over betacam including its increased horizontal resolution.</p>
     */
    BetacamSPFormat (2), 
    /**
     * <p>VHS tape format. The <em>Video Home System</em> (VHS) tape that became the most popular format for consumer
     * viewing and recording.</p> 
     */
    VHSFormat (3), 
    /**
     * <p>S-VHS tape format. <em>Super VHS</em> improves on the horizontal resolution of the VHS standard.</p>
     */
    SVHSFormat (4), 
    /**
     * <p>The small form factor video&nbsp;8 tape format. A narrower version of the betamax format using 8&nbsp;mm rather than
     * 12&nbsp;mm wide tape, popular for use in camcorders.</p>
     * 
     * <p>Note that the name of this element starts with an underscore ('<code>_</code>')
     * character as Java identifiers must start with an alphanumeric character or an underscore character. The
     * specified name of this value is "<code>8mmFormat</code>".</p>
     */
    _8mmFormat (5), 
    /**
     * <p>Hi-band video&nbsp;8 tape format. An improved version of the the video 8 format.</p>
     */
    Hi8Format (6), 
    ;

    private final int value;

    TapeFormatType (int value) { this.value = value; }

    @Int64
    public long value() { return (long) value; }
    
    public String symbol() {
    	
    	switch (this) {
    	
    	case _8mmFormat:
    		return "8mmFormat";
    	default:
    		return name();
    	}
    }

}
