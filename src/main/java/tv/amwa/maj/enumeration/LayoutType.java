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
 * $Log: LayoutType.java,v $
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
 * Revision 1.1  2007/11/13 22:13:40  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies whether all the data for a complete sample is in one frame 
 * or is split into more than one field.</p>
 * 
 * <p>Original C name: <code>aafFrameLayout_e</code></p>
 * 
 * @see tv.amwa.maj.model.PictureDescriptor#getFrameLayout()
 * @see tv.amwa.maj.industry.TypeDefinitions#LayoutType
 * 
 *
*/

public enum LayoutType 
	implements MediaEnumerationValue {

    /** 
     * <p>A progressive lattice from top to bottom, stored in progressive line 
     * order 1,2,3,4,5,6... The duration of a sampled rectangle is a frame.</p> 
     */
	FullFrame (0), 
	/** 
	 * <p>An interlaced lattice divided into two fields, stored as two fields 
	 * 1,3,5,... and 2,4,6...  Field&nbsp;1 scans alternate lines from top to bottom, 
	 * field&nbsp;2 scans the intervening lines. The second field is scanned at a later time 
	 * than the first field (one field later). Examples: NTSC, SMPTE&nbsp;125M. The duration 
	 * of a sampled rectangle is a field.</p>
	 */
	SeparateFields (1), 
	/** 
	 * <p>An interlaced lattice as for {@link #SeparateFields}, except that 
	 * only one field is scanned and retained in the stored data, as 1,3,5,... 
	 * or 2,4,6,... or (1+2),(3+4),(5+6), ... For display, the second field is 
	 * derived by line replication or interpolation. There are no examples of 
	 * one field in broadcast use; however, this type of sub-sampling is often used 
	 * as a simple compression for index frames. The duration of a sampled rectangle 
	 * is a frame.</p> 
	 */
	OneField (2), 
	/** 
	 * <p>An interlaced lattice as for {@link #SeparateFields} above, stored as a single 
	 * matrix of interleaved lines 1,2,3,4,5,6,... It is not common to use mixed fields in 
	 * broadcast; however, intermediate in-memory data structures sometimes use this format. 
	 * The duration of a sampled rectangle is a frame.</p>
	 */
	MixedFields (3), 
	/** 
	 * <p>An interlaced lattice divided into two fields. Field&nbsp;1 scans alternate lines 
	 * from top to bottom, field&nbsp;2 scans the intervening lines. The lines are stored as two fields 
	 * 1,3,5,... 2,4,6,... The two fields are taken from a single scan of the incoming image - i.e., they 
	 * are coincident in time, except for the effects of shutter angle. Example: "1080P24 SF". The duration 
	 * of a sampled rectangle is a field.</p> 
	 */
	SegmentedFrame (4), 
    ;

    private final int value;

    LayoutType (int value) { this.value = value; }

    @Int64 public long value() { return (long) value; }
    
    public String symbol() { return name(); }
}
