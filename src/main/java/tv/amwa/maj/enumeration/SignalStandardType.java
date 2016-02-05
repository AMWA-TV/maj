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
 * $Log: SignalStandardType.java,v $
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
 * Revision 1.2  2008/01/08 17:01:50  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:47  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

// No 1080i defined in this enumeration?

/** 
 * <p>Specifies an underlying signal standard used to define the raster.</p>
 * 
 * <p>Original C name: <code>aafSignalStandard_e</code></p>
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#SignalStandardType
 * @see tv.amwa.maj.model.PictureDescriptor#getSignalStandard()
 * 
 *
 */

public enum SignalStandardType 
	implements MediaEnumerationValue {

    /** 
     * <p>No defined signal standard.</p>
     */      
	None (0), 
    /** 
     * <p>525/625 line interlaced raster, as specified in ITU-R&nbsp;BT.601 <em>Studio 
     * encoding parameters of digital television for standard 4:3 and wide screen 16:9 
     * aspect ratios</em>.</p>
     */  
	ITU601 (1), 
    /** 
     * <p>525/625 line progressive raster, as defined in ITU-R&nbsp;BT 1358 <em>Studio 
     * parameters of 625 and 525 line progressive television systems</em>.</p>.
     */ 
	ITU1358 (2), 
    /** 
     * <p>540&nbsp;Mbit/s mappings, as specified in SMPTE&nbsp;247M <em>Television&nbsp;- 
     * 540&nbsp;Mb/s Serial Digital Interface&nbsp;- Source Image Format Mapping</em>.</p>
     */            
	SMPTE347M (3), 
    /** 
     * <p>1125 line raster, as specified in SMPTE&nbsp;247M <em>Television&nbsp;- 
     * 1920&nbsp;x&nbsp;1080 Image Sample Structure, Digital Representation and Digital Timing 
     * Reference Sequences for Multiple Picture Rates</em>.</p> 
     */                
	SMPTE274M (4), 
    /** 
     * <p>720&nbsp;line progressive raster, as specified in SMPTE&nbsp;296M <em>Television&nbsp;- 
     * 1280&nbsp;x&nbsp;720 Progressive Image Sample Structure&nbsp;- Analog and Digital Representation 
     * and Analog Interface</em>.</p> */     
	SMPTE296M (5), 
    /** 
     * <p>1485&nbsp;Mbit/s mappings, as specified in SMPTE&nbsp;349M <em>Television&nbsp;- Transport of 
     * Alternate Source Image Formats Through SMPTE&nbsp;292M</em>.</p>
     */             
	SMPTE349M (6), 
    ;

    private final int value;

    SignalStandardType (int value) { this.value = value; }

    @Int64 public long value() { return value; }
    
    public String symbol() { 
    	
    	return "SignalStandard_" + name(); 
    }
}
