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
 * $Log: Event.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/27 11:07:35  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:26  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.PositionType;


/**
 * <p>Specifies a text comment, a trigger, or an area in the image that has an associated interactive
 * action.</p>
 * 
 *
 *
 * @see EventTrack
 */
public abstract interface Event 
	extends Segment {

	/**
	 * <p>Returns the position of this event, which specifies the starting time of the 
	 * event in an {@linkplain EventTrack event track}.</p>
	 * 
	 * @return Position of this event in an event track.
	 * 
	 * @throws BadPropertyException The position property is not present in the context of this event.
	 */
	public @PositionType long getEventPosition()
		throws BadPropertyException;

	/**
	 * <p>Sets the position of this event, which specifies the starting time of the 
	 * event in an {@linkplain EventTrack event track}.</p>
	 * 
	 * @param eventPosition The position for this event in an event track.
	 * 
	 * @throws BadPropertyException The position property is not present in the context of this event.
	 */
	public void setEventPosition(
			@PositionType long eventPosition)
		throws BadPropertyException;

	/**
	 * <p>Set a comment that specifies the purpose of the event. Set this optional property
	 * to <code>null</code> to omit it.</p>
	 * 
	 * @param eventComment Comment to set for this event.
	 */
	public void setEventComment(
			@AAFString String eventComment);

	/**
	 * <p>Returns the comment that specifies the purpose of this event. This is an optional
	 * property.</p>
	 * 
	 * @return Comment describing this event.
	 * 
	 * @throws PropertyNotPresentException The optional comment property is not present in this event.
	 */
	public @AAFString String getEventComment() 
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this event.</p>
	 *
	 * @return Cloned copy of this event.
	 */
	public Event clone();
}
