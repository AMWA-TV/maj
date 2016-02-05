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
 * $Log: TCSource.java,v $
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
 * Revision 1.2  2008/01/08 17:01:52  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:55  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies the kind of a {@linkplain tv.amwa.maj.model.TimecodeStream timecode stream}.</p>
 * 
 * <p>Original C name: <code>aafTCSource_t</code></p>
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#TCSource
 * @see tv.amwa.maj.industry.TypeDefinitions#TimecodeSource
 * @see tv.amwa.maj.model.TimecodeStream#getTimecodeSource()
 * @see tv.amwa.maj.record.TimecodeValue
 * 
 *
 */

public enum TCSource 
	implements MediaEnumerationValue { 
	
	/**
	 * <p>Linear (or Longitudinal) Timecode (LTC) encodes SMPTE timecode data as a Manchester-Biphase encoded 
	 * audio signal.</p>
	 */
	TimecodeLTC(0),
	/**
	 * <p>Vertical Interval TimeCode (VITC) is a form of SMPTE timecode 
	 * embedded as a pair of black-and-white bars in a video signal.</p>
	 */
	TimecodeVITC(1);
	
    private final int value;

    TCSource (int value) { this.value = value; }

    @Int64 
    public long value() { return (long) value; }
    
    public String symbol() { return name(); }
    
}

