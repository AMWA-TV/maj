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
 * $Log: DescriptiveClip.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/01/27 11:07:27  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.Set;

import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.misctype.TrackID;


/** 
 * <p>Specifies what is being described in terms of {@linkplain Track tracks} and references 
 * a {@linkplain Package package} to provide that description. This enables a {@linkplain Segment segment}
 * in a descriptive metadata {@linkplain Track track} to reference a section of descriptive
 * metadata in the track of another package. Typically, the referenced track would contain
 * {@linkplain DescriptiveMarker descriptive markers}.</p>
 *
 *
 *
 * @see tv.amwa.maj.misctype.TrackID
 * @see tv.amwa.maj.model.Track
 */
public interface DescriptiveClip
	extends SourceClip {

	/**
	 * <p>Returns the number of described track ids in the set of described track ids
	 * referenced by this descriptive clip, which specify which of the  
	 * {@linkplain Track tracks} in the {@linkplain Package package} are being referenced.</p>
	 *
	 * @return Number of described track ids in the set of described track ids
	 * referenced by this descriptive clip.
	 */
	public @TrackID int countDescribedTrackIDs();
	
	/**
	 * <p>Clears the list of described track ids of this descriptive clip, omitting this
	 * optional property.</p>
	 */
	public void clearDescribedTrackIDs();

	/**
	 * <p>Returns a copy of the set of descriptive track ids referenced by this descriptive
	 * clip, which specify which of the  
	 * {@linkplain Track tracks} in the {@linkplain Package package} are being referenced.</p>
	 *
	 * @return A copy of the set of descriptive track ids referenced by this descriptive
	 * clip.
	 */
	public @TrackID Set<Integer> getDescribedTrackIDs();

	/**
	 * <p>Returns <code>true</code> if the given described track id is present in the
	 * set of described track ids referenced from this descriptive clip, which specify which of the  
	 * {@linkplain Track tracks} in the {@linkplain Package package} are being referenced.</p>
	 *
	 * @param describedTrackID Described track id to check to see if it is contained in the set
	 * of described track ids of this descriptive clip.
	 * 
	 * @return Is the given described track id present in the set of described track ids 
	 * referenced by this descriptive clip?
	 * 
	 * @throws IllegalArgumentException The given track id is negative.
	 */
	public boolean isDescribedTrackIDPresent (
			@TrackID int describedTrackID);

	/**
	 * <p>Adds the given described track id to the set of described track ids referenced by
	 * this descriptive clip, which specify which of the 
	 * {@linkplain Track tracks} in the {@linkplain Package package} are being referenced.</p>
	 *
	 * @param describedTrackID Descriptive track id to add to the set of described track ids
	 * referenced by this descriptive clip.
	 * 
	 * @throws IllegalArgumentException The given track id is negative.
 	 * @throws InvalidParameterException The given described track id is already contained
 	 * in the set of described track ids referenced by this descriptive clip.
	 */
	public void addDescribedTrackID(
			@TrackID int describedTrackID)
		throws IllegalArgumentException,
			InvalidParameterException;

	/**
	 * <p>Removes the given described track id from the list of described track ids
	 * referenced by this descriptive clip, which specify which of the 
	 * {@linkplain Track tracks} in the {@linkplain Package package} are being referenced. 
	 * In effect, optional property empty sets become not present when the last element 
	 * is removed.</p>
	 *
	 * @param describedTrackID Described track id to remove from this descriptive clip.
	 * 
	 * @throws IllegalArgumentException A given track id is negative.
	 * @throws InvalidParameterException The given track id is not in the set of descriptive
	 * track ids of this descriptive clip.
	 */
	public void removeDescribedTrackID (
			@TrackID int describedTrackID)
		throws InvalidParameterException;
	
	/**
	 * <p>Create a cloned copy of this descriptive clip.</p>
	 *
	 * @return Cloned copy of this descriptive clip.
	 */
	public DescriptiveClip clone();
}
