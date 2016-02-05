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
 * $Log: EventTrack.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/01/27 11:07:34  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:09:00  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.Rational;


/**
 * <p>Specifies a container for a {@linkplain Sequence sequence} of {@linkplain Event events}.</p>
 * 
 * <p>The specified rules for an event track are as follows:</p>
 * 
 * <ul>
 *  <li>An event track shall have a concrete {@linkplain Segment segment} that is either a 
 *  concrete sub-class of {@link Event} or a {@link Sequence}.</li>
 *  <li>If an event track has a sequence, then the sequence shall conform to the following 
 *  rules:
 *   <ul>
 *    <li>All segments in the sequence shall be events.</li>
 *    <li>All events in the sequence shall belong to the same concrete sub-class of event.</li>
 *    <li>All events in the sequence shall have the same {@linkplain DataDefinition data definition}
 *    as the sequence.</li>
 *    <li>In a sequence, the position of each event shall be greater than or equal to the position 
 *    of the event preceding it in the sequence.</li>
 *   </ul>
 *  </li>
 * </ul>
 * 
 *
 * 
 * @see Segment
 * @see Sequence
 * @see Event
 */

public interface EventTrack 
	extends Track {

	/**
	 * <p>Returns the edit rate for this track, which specifies the units in which the 
	 * events specify their starting time and duration</p>
	 * 
	 * @return Edit rate for this track.
	 */
	public Rational getEventTrackEditRate();

	/**
	 * <p>Sets the edit rate for this track, which specifies the units in which the 
	 * events specify their starting time and duration</p>
	 * 
	 * @param eventTrackEditRate Edit rate for this track.
	 * 
	 * @throws NullPointerException The given edit rate is <code>null</code>
	 */
	public void setEventTrackEditRate(
			Rational eventTrackEditRate)
		throws NullPointerException;
	
	/**
	 * <p>Gets the event track origin of this event track, which specifies the point, in edit 
	 * units, in a track from which relative times are measured. This is an optional property.</p> 
	 * 
	 * @return Event track origin of this event track.
	 * 
	 * @throws PropertyNotPresentException The optional event track origin property
	 * is not present in this event track.
	 */
	@PositionType public long getEventTrackOrigin()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the event track origin of this event track, which specifies the point, 
	 * in edit units, in a track from which relative times are measured. Set this optional property
	 * to <code>null</code> to omit it.
	 * 
	 * @param eventTrackOrigin Event track origin of this event track.
	 */
	public void setEventTrackOrigin(
			@PositionType Long eventTrackOrigin);
	
	/**
	 * <p>Create a cloned copy of this event track.</p>
	 *
	 * @return Cloned copy of this event track.
	 */
	public EventTrack clone();
}

