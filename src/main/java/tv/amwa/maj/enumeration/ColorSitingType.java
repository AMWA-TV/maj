/* 
 **********************************************************************
 *
 * $Id: ColorSitingType.java,v 1.7 2011/02/14 22:32:58 vizigoth Exp $
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
 * $Log: ColorSitingType.java,v $
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
 * Revision 1.2  2008/01/08 17:01:53  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:14:15  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies color siting that indicates where colored pixels are located with respect 
 * to their associated luminance value.</p>
 * 
 * <p>Original C name: <code>aafColorSiting_e</code><br>
 * 
 * @see tv.amwa.maj.model.CDCIDescriptor#getColorSiting()
 * @see tv.amwa.maj.industry.TypeDefinitions#ColorSitingType
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */

public enum ColorSitingType 
	implements MediaEnumerationValue {

    /**
     * <p>The first luminance value of the image is co-sited with the 
     * first chrominance value.</p> */
	CoSiting (0), 
	/** 
	 * <p>The color pixel is sited at the point horizontally midway between the 
	 * luminance pixels on each line.</p>
	 */
	Averaging (1), 
	/** 
	 * <p>Reserved.</p> 
	 */
	ThreeTap (2), 
	/** 
	 * <p>Color samples are sited at the point midway between two 
	 * adjacent luminance pixels on two adjacent lines, as in MPEG-1 4:2:0.</p>
	 */
    Quincunx (3), 
	/** 
	 * <p>Color samples are known to be sited in accordance with ITU-R BT.601.</p> 
	 */
    Rec601 (4), 
    /** 
     * <p>The siting of the color samples is unknown.</p> 
     */
    UnknownSiting (255), 
    ;

    private final int value;

    ColorSitingType (int value) { this.value = value; }

    @Int64 public long value() { return (long) value; }
    
    public String symbol() { return name(); }
}
