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
 * $Log: Segment.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/02/08 11:27:21  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2007/12/04 09:36:09  vizigoth
 * Minor comment updates.
 *
 * Revision 1.1  2007/11/13 22:08:55  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.BadSampleOffsetException;
import tv.amwa.maj.exception.TimecodeNotFoundException;
import tv.amwa.maj.misctype.FrameOffset;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.TimecodeValue;


/**
 * <p>Specifies a {@linkplain Component component} that is independent of any surrounding
 * object. Contrast this to a {@linkplain Transition transition} which is a component that
 * depends on other components to establish its value.</p>
 * 
 *
 *
 * @see Track#getTrackSegment()
 * @see Pulldown#getInputSegment()
 * @see tv.amwa.maj.industry.TypeDefinitions#SegmentStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#SegmentStrongReferenceVector
 */

public abstract interface Segment 
	extends Component {

	/**
	 * <p>Converts the given segment offset to a {@linkplain TimecodeValue timecode} 
	 * value.</p>
	 * 
	 * @param offset Segment offset to be converted to a timecode.
	 * @return Converted timecode value.
	 * 
	 * @throws TimecodeNotFoundException The given offset is not available as
	 * a timecode value in this segment.
	 * 
	 * @see #segmentTCToOffset(TimecodeValue, Rational)
	 */
	public TimecodeValue segmentOffsetToTC(
			@PositionType long offset) 
		throws TimecodeNotFoundException;
	
	/**
	 * <p>Converts the given {@linkplain TimecodeValue timecode} and edit rate
	 * to a segment offset value.</p>
	 * 
	 * @param timecode Timecode to be converted to an offset.
	 * @param editRate Edit rate of the given timecode.
	 * @return Frame offset into the segment.
	 * 
	 * @throws NullPointerException One or both of the given timecode and/or edit rate is/are 
	 * <code>null</code>.
	 * @throws TimecodeNotFoundException The given timecode is not available
	 * in this segment.
	 * @throws BadSampleOffsetException The given timecode is outside the bounds
	 * of the timecode values represented by this segment.
	 * 
	 * @see #segmentOffsetToTC(long)
	 */
	public @FrameOffset long segmentTCToOffset(
			TimecodeValue timecode,
			Rational editRate) 
		throws NullPointerException,
			TimecodeNotFoundException,
			BadSampleOffsetException;
	
	/**
	 * <p>Create a {@linkplain Sequence sequence} containing just this segment.</p>
	 * 
	 * @return Sequence containing just this segment.
	 */
	public Sequence generateSequence();
	
	/**
	 * <p>Create a cloned copy of this segment.</p>
	 *
	 * @return Cloned copy of this segment.
	 */
	public Segment clone();
}
