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
 * $Log: Track.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/02/08 11:27:22  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:46  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.TrackID;


/**
 * <p>Specifies a single track in a {@linkplain Package package}. A track describes the relationship
 * between essence and time. In a {@linkplain MaterialPackage material package}, tracks describe the 
 * desired relationship between stored essence and the output timeline.</p>
 * 
 *
 * 
 * @see Package#appendPackageTrack(Track)
 * @see Package#getPackageTracks()
 * @see Package#lookupPackageTrack(int)
 * @see tv.amwa.maj.industry.TypeDefinitions#TrackStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#TrackStrongReferenceVector
*/

public abstract interface Track 
	extends InterchangeObject {

	/**
	 * <p>Sets the name of this track. Set this optional property to <code>null</code>
	 * to omit it.</p>
	 * 
	 * @param name Name of this track.
	 */
	public void setTrackName(
			@AAFString String name);

	/**
	 * <p>Returns the name of this track. This is an optional property.</p>
	 * 
	 * @return Name of this track.
	 * 
	 * @throws PropertyNotPresentException The optional name property is not present
	 * for this track.
	 */
	public @AAFString String getTrackName()
		throws PropertyNotPresentException;

	/** <p>Sets the physical output channel associated with this track. Set this
	 * optional property to <code>null</code> to omit it.</p>
	 * 
	 * <p>The essence track number identifies the physical channel
	 * associated with the media.  For file source packages that describe
	 * stereo audio media, the left channel should have a essence track number
	 * of&nbsp;1 and the right channel should have a essence track number of&nbsp;2.</p>
	 * 
	 * @param essenceTrackNumber The physical output channel associated with this track.
	 * 
	 * @throws IllegalArgumentException The given physical track number is negative.
	 */
	public void setEssenceTrackNumber(
			@UInt32 Integer essenceTrackNumber)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the physical output channel associated with this track. This
	 * is an optional property.</p>
	 * 
	 * <p>The essence track number identifies the physical channel
	 * associated with the media.  For file source packages that describe
	 * stereo audio media, the left channel should have an essence track number
	 * of&nbsp;1 and the right channel should have a essnece track number of&nbsp;2.</p>
	 * 
	 * @return The physical output channel associated with this track.
	 *
	 * @throws PropertyNotPresentException The track is not a physical track.
	 */
	public @UInt32 int getEssenceTrackNumber()
		throws PropertyNotPresentException;

	/**
	 * <p>This method will set the track id of this track, which specifies an integer that 
	 * is used to reference this track.</p>
	 * 
	 * @param trackID Track id for this track.
	 * 
	 * @throws IllegalArgumentException The given track id is negative.
	 * 
	 * @see tv.amwa.maj.exception.TrackExistsException
	 */
	public void setTrackID(
			@TrackID int trackID)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the track id of this track, which specifies an integer that is used to 
	 * reference this track.</p>
	 * 
	 * @return Track id of this track.
	 */
	public @TrackID int getTrackID();

	/**
	 * <p>Sets the segment for this track, which is the value of the track.</p>
	 * 
	 * @param segment Segment representing the value for this track.
	 * 
	 * @throws NullPointerException The given segment is <code>null</code>.
	 */
	public void setTrackSegment(
			Segment segment) 
		throws NullPointerException;

	/**
	 * <p>Returns the segment of this track, which is the value of the track.</p>
	 * 
	 * @return Segment specifying the value of this track.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#SegmentStrongReference
	 */
	public Segment getTrackSegment();

	/**
	 * <p>Returns the {@linkplain DataDefinition data definition} describing the type
	 * of essence represented by the value of this track.</p> 
	 * 
	 * @return Data definition object associated with the segment in the track.
	 * 
	 * @see DataDefinition
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 * @see #getTrackSegment()
	 * @see Component#getComponentDataDefinition()
	 */
	public DataDefinition getDataDefinition();
	
	/**
	 * <p>Create a cloned copy of this track.</p>
	 *
	 * @return Cloned copy of this track.
	 */
	public Track clone();
}

