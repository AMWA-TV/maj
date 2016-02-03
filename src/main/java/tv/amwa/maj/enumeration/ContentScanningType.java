/* 
 **********************************************************************
 *
 * $Id: ContentScanningType.java,v 1.7 2011/02/14 22:32:58 vizigoth Exp $
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
 * $Log: ContentScanningType.java,v $
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
 * Revision 1.1  2007/11/13 22:13:52  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;

/** 
 * <p>For MPEG coded content, specifies if the scanning type of underlying
 * coded content is known and, if it is, what kind of scanning type it is.</p>
 * 
 * @see tv.amwa.maj.model.MPEGVideoDescriptor#getCodedContentScanning()
 * @see tv.amwa.maj.industry.TypeDefinitions#ContentScanningType
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
public enum ContentScanningType 
	implements MediaEnumerationValue {
	
	/**
	 * <p>Scanning type is not known.</p>
	 */
	NotKnown(0),
	/**
	 * <p>Progressive scan. The lines of each video frame are drawn in sequence. See the 
	 * <a href="http://en.wikipedia.org/wiki/Progressive_scan">Wikipedia entry for progressive
	 * scan</a>.</p>
	 */
	Progressive(1),
	/**
	 * <p>Interlaced scan. The lines of each video frame are drawn alternatively, starting with all the odd lines
	 * and then all the even lines. See the 
	 * <a href="http://en.wikipedia.org/wiki/Progressive_scan">Wikipedia entry for interlace</a>.</p>
	 */
	Interlace(2),
	/**
	 * <p>Content contains a mixture of progressive and interlace scanning.</p>
	 */
	Mixed(3);

	private int value;
	
	private ContentScanningType(
			int value) {
		
		this.value = value;
	}
	
	public long value() {

		return (long) value;
	}
	
    public String symbol() { 
    	
    	return "ContentScanning_" + name(); 
    }

}
