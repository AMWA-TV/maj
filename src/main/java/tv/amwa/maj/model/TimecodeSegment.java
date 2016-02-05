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
 * $Log: TimecodeSegment.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/02/08 11:27:25  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:40  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.misctype.FrameOffset;

/**
 * <p>Specifies the storage of video tape or audio tape timecode information.</p>
 * 
 * 
 * <p>In contrast to a {@linkplain TimecodeStream timecode stream}, a timecode segment specifies 
 * timecode by specifying the single starting timecode value; other timecode values 
 * are calculated from the starting timecode and the time offset.</p>
 *  
 * <p>See the <a href="package-summary.html#namingConflicts">section on naming conflicts in the package 
 * documentation</a>.</p>
 * 
 *
 *
 * @see tv.amwa.maj.record.TimecodeValue
 */

public interface TimecodeSegment 
	extends Segment {

	/**
	 * <p>Returns the timecode value for this timecode segment, which specifies the start timecode
	 * for the segment, the frames per second of the tape and whether the timecode is drop
	 * or non-drop.</p>
	 * 
	 * @return Timecode value for the timecode segment.
	 * 
	 * @see #getStartTimecode()
	 * @see #getFramesPerSecond()
	 * @see #getDropFrame()
	 */
	public tv.amwa.maj.record.TimecodeValue getTimecode();

	/**
	 * <p>Returns the timecode value by the number of frames offset from
	 * the start of the video or audio.</p>
	 *
	 * @return Timecode offset value.
	 * 
	 * @see #getTimecode()
	 */
	public @FrameOffset long getStartTimecode();
	
	/**
	 * <p>Returns the frames per second of the videotape or audio tape of the timecode.</p>
	 *
	 * @return Frames per second of the videotape or audiotape of the timecode.
	 * 
	 * @see #getTimecode()
	 */
	public @UInt16 short getFramesPerSecond();
	
	/**
	 * <p>Determines whether the timecode is drop (<code>true</code> value) or 
	 * nondrop (<code>false</code> value).</p>
	 * 
	 * <p>If drop is set to <code>true</code>, the real frames-per-second rate represented
	 * by the timecode is 29.97. Calculations of the real time represented by a timecode
	 * or to create a textual representation of the time code drop 108 selected frames
	 * per hour.</p>
	 *
	 * @return Is the timecode a drop value?
	 * 
	 * @see #getTimecode()
	 */
	public boolean getDropFrame();	
	
	/**
	 * <p>Set the timecode value for this timecode segment, which specifies the start timecode
	 * for the segment, the frames per second of the tape and whether the timecode is drop
	 * or non-drop.</p>
	 * 
	 * @param timecode Timecode value for this timecode segment.
	 * 
	 * @throws NullPointerException The given value for the timecode is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.industry.Forge#makeTimecode(long, short, boolean)
	 */
	public void setTimecode(
			tv.amwa.maj.record.TimecodeValue timecode)
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this timecode segment.</p>
	 *
	 * @return Cloned copy of this timecode segment.
	 */
	public TimecodeSegment clone();
}
