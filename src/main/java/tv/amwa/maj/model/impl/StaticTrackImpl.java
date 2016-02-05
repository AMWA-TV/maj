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
 * $Log: StaticTrackImpl.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:44  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.model.StaticTrack;


/** 
 * <p>Implements a single track of essence data that has no relationship to time, such 
 * as a static image. As a static track has not relationship with time, it does
 * not specify an edit rate.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3a00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "StaticTrack",
		  aliases = { "StaticMobSlot" },
		  description = "The StaticTrack describes essence data that has no relationship to time, such as a static image.",
		  symbol = "StaticTrack")
public class StaticTrackImpl
	extends 
		TrackImpl
	implements 
		StaticTrack,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 8350113256771243987L;
	
	/** Default constructor is not public to avoid unset required fields. */
	public StaticTrackImpl() { }

	/**
	 * <p>Creates and initializes a new static track, which describes essence 
	 * data that has no relationship to time, such as a static image.</p>
	 *
	 * @param trackID An integer that is used to reference the new track.
	 * @param segment Value of the track.
	 * 
	 * @throws NullPointerException The segment argument is <code>null</code>.
	 * @throws IllegalArgumentException Cannot create a new static track with a negative track id.
	 */
	public StaticTrackImpl(
			@UInt32 int trackID,
			Segment segment)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (segment == null)
			throw new NullPointerException("Cannot create a new static track with a null segment.");
		if (trackID < 0)
			throw new IllegalArgumentException("Cannot create a new static track with a negative track id.");
		
		setTrackSegment(segment);
		setTrackID(trackID);
	}
	
	/** 
	 * <p>Set the segment for the static track, ensuring that it does not have a length property
	 * set.</p>
	 * 
	 * @see tv.amwa.maj.model.impl.TrackImpl#setTrackSegment(tv.amwa.maj.model.Segment)
	 */
	@Override
	public void setTrackSegment(
			Segment segment) {
		
		segment.setLengthPresent(false);
		super.setTrackSegment(segment);
	}

	public StaticTrack clone() {
		
		return (StaticTrack) super.clone();
	}
}
