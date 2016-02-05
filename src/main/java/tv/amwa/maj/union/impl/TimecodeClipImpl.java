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
 * $Log: TimecodeClipImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:33  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import tv.amwa.maj.model.TimecodeSegment;

// TODO question the value of this? If it is, add Object override methods.

/**
 * <p>Implementation of a reference to part of a {@linkplain tv.amwa.maj.model.TimecodeSegment timecode segment}.</p>
 *
 * <p>Note that the value of timecode segment calculations in the MAJ API is still being determined. At the
 * current time, this class is only used by non-public methods.</p>
 *
 *
 * 
 * @see tv.amwa.maj.model.SourcePackage#specifyValidCodeRange(tv.amwa.maj.model.DataDefinition, int, tv.amwa.maj.record.Rational, long, long)
 *
 */
public class TimecodeClipImpl {
	
	/** <p>Timecode segment referenced by this clip.</p> */
	public final TimecodeSegment result;
	/** <p>Start position of reference defined in edit units from the start of the clip.</p> */
	public final long timecodeStartPosition;
	/** <p>Length of the clip in its defined edit units.</p> */
	public final long timecodeTrackLength;
	
	/**
	 * <p>Create a new reference to a clip of a timecode segment.</p>
	 * 
	 * @param result Timecode segment referenced by this clip.
	 * @param timecodeStartPosition Start position of reference defined in edit units from the start of the clip.
	 * @param timecodeTrackLength Length of the clip in its defined edit units.
	 */
	public TimecodeClipImpl(
			TimecodeSegment result,
			long timecodeStartPosition,
			long timecodeTrackLength) {
		
		this.result = result;
		this.timecodeStartPosition = timecodeStartPosition;
		this.timecodeTrackLength = timecodeTrackLength;
	}
}
