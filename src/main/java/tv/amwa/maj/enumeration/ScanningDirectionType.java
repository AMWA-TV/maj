/* 
 **********************************************************************
 *
 * $Id: ScanningDirectionType.java,v 1.7 2011/02/14 22:32:58 vizigoth Exp $
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
 * $Log: ScanningDirectionType.java,v $
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
 * Revision 1.1  2007/11/13 22:13:54  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Describes the scanning direction of an image.</p>
 * 
 * <p>Informative note: The values of scanning direction type match the equivalent type 
 * in SMPTE&nbsp;268M.</p>
 * 
 * <p>Original C name: <code>aafScanningDirection_e</code></p>
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#ScanningDirectionType
 * @see tv.amwa.maj.model.RGBADescriptor#getScanningDirection()
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
public enum ScanningDirectionType 
	implements MediaEnumerationValue {

	/**
	 * <p>Scan from left to right, top to bottom.</p>
	 */
	LeftToRightTopToBottom (0), 
    /**
     * <p>Scan from right to left, top to bottom.</p>
     */
    RightToLeftTopToBottom (1), 
    /**
     * <p>Scan from left to right, bottom to top.</p>
     */
    LeftToRightBottomToTop (2), 
    /**
     * <p>Scan from right to left, bottom to top.</p>
     */
    RightToLeftBottomToTop (3), 
    /**
     * <p>Scan from top to bottom, left to right.</p>
     */
    TopToBottomLeftToRight (4), 
    /**
     * <p>Scan from top to bottom, right to left.</p>
     */
    TopToBottomRightToLeft (5), 
    /**
     * <p>Scan from bottom to top, left to right.</p>
     */
    BottomToTopLeftToRight (6), 
    /**
     * <p>Scan from bottom to top, right to left.</p>
     */
    BottomToTopRightToLeft (7), 
    ;

    private final int value;

    ScanningDirectionType (int value) { this.value = value; }

    @Int64 
    public long value() { return (long) value; }
    
    public String symbol() { 
    	
    	return "ScanningDirection_" + name(); 
    }
}
