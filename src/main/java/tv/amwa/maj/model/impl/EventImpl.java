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
 * $Log: EventImpl.java,v $
 * Revision 1.3  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/02/10 23:57:35  vizigoth
 * Improvements to create and mod time method names in Package to match meta dictionary and other minor fixes.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2007/11/15 12:52:48  vizigoth
 * Edits to ensure source can make rough and ready javadoc.
 *
 * Revision 1.1  2007/11/13 22:09:06  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

// TODO contextual switching of the existence of the position property

import java.io.Serializable;

import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.model.Event;


/** 
 * <p>Implements a text comment, a trigger, or an area in the image that has an associated interactive
 * action.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0600,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Event",
		  description = "The Event class defines a text comment, a trigger, or an area in the image that has an associated interactive action.",
		  symbol = "Event",
		  isConcrete = false)
public class EventImpl
	extends 
		SegmentImpl
	implements
		Event,
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4054403398629042547L;
	
	private long eventPosition = 0l;
	private String eventComment = null;

	// TODO check this default
	private boolean eventPositionPresent = true;
	
	@MediaProperty(uuid1 = 0x05300404, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EventComment",
			aliases = { "Comment" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0602,
			symbol = "EventComment")	
	public String getEventComment() 
		throws PropertyNotPresentException {

		if (eventComment == null)
			throw new PropertyNotPresentException("The optional event comment property is not present in this event.");
				
		return eventComment;
	}

	@MediaPropertySetter("EventComment")
	public void setEventComment(
			String eventComment) {
		
		this.eventComment = eventComment;
	}

	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x0303, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EventPosition",
			aliases = { "Position" },
			typeName = "PositionType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0601,
			symbol = "EventPosition")	
	public long getEventPosition() 
		throws BadPropertyException {

		if (eventPositionPresent == false)
			throw new BadPropertyException("The event position property is not present in the context of this event.");
		
		return eventPosition;
	}

	@MediaPropertySetter("EventPosition")
	public void setEventPosition(
			long position) 
		throws BadPropertyException {

		if (eventPositionPresent == false)
			throw new BadPropertyException("The event position property is not present in the context of this event.");

		this.eventPosition = position;
	}

	public final static long initializeEventPosition() {
		
		return 0l;
	}
	
	/**
	 * <p>Determine whether this event is in a context where the position property is present or not.</p>
	 *
	 * <p>If an Event is in a {@link TimelineTrackImpl timeline track} or a {@link StaticTrackImpl static track}, 
	 * it shall not have a position property. If an event is in an {@link EventTrackImpl event track}, it 
	 * shall have a position property.</p>
	 * 
	 * @return Is this event in a context where the position property is present?
	 */
	public boolean getPositionPresent() {
		
		return eventPositionPresent;
	}

	/**
	 * <p>Sets whether this event is in a context where the position property is present or not.</p>
	 * 
	 * <p>If an Event is in a {@link TimelineTrackImpl timeline track} or a {@link StaticTrackImpl static track}, 
	 * it shall not have a position property. If an event is in an {@link EventTrackImpl event track}, it 
	 * shall have a position property.</p>
	 *
	 * @param positionPresent Is this event in a context where the position property is present?
	 */
	public void setPositionPresent(
			boolean positionPresent) {
		
		this.eventPositionPresent = positionPresent;
	}

	public Event clone() {
		
		return (Event) super.clone();
	}
}
