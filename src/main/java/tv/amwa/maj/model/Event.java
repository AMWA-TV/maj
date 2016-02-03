/* 
 **********************************************************************
 *
 * $Id: Event.java,v 1.1 2011/01/04 10:39:03 vizigoth Exp $
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
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
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
