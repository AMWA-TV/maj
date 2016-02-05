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
 * $Log: TimelineTrack.java,v $
 * Revision 1.4  2011/07/27 17:30:18  vizigoth
 * Fixed imports to clear warnings.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/02/08 11:27:20  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:55  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.union.MultiCreateItem;


/**
 * <p>Specifies a track that describes time-varying timeline essence.</p>
 * 
 * <p>If a {@linkplain Component component} is in a timeline track, then it shall have a 
 * {@linkplain Component#getComponentLength() length property}. The timeline track specifies the 
 * {@linkplain #getEditRate() edit rate} for the {@linkplain Segment segment} it has. The 
 * {@linkplain Segment segment} specifies its length in the edit rate set by the timeline track.
 * The segment also specifies its own {@linkplain Component#getComponentDataDefinition() data kind}.</p>
 * 
 *
 *
 * @see MaterialPackage#createEssence(int, DataDefinition, AUID, Rational, Rational, tv.amwa.maj.enumeration.CompressEnable, Locator, AUID) MaterialPackage.createEssence()
 * @see MaterialPackage#createMultiEssence(AUID, MultiCreateItem[], tv.amwa.maj.enumeration.CompressEnable, Locator, AUID) MaterialPackage.createMultiEssence()
 * @see TimecodeSegment
 * @see EdgeCodeSegment
 * @see TimecodeStream
 */

public interface TimelineTrack
	extends Track {

	/**
	 * <p>Returns the edit rate for this timeline track, which specifies the units
	 * of time of the track.</p>
	 * 
	 * @return Edit rate for this timeline track.
	 */
	public Rational getEditRate();

	/**
	 * <p>Sets the edit rate for this timeline track, which specifies the units
	 * of time of the track.</p>
	 * 
	 * @param editRate Edit rate for this timeline track.
	 * 
	 * @throws NullPointerException The given edit rate is <code>null</code>.
	 */
	public void setEditRate(
			Rational editRate) 
		throws NullPointerException;

	/**
	 * <p>Returns the origin of this timeline track, which
	 * specifies the offset used to resolve {@linkplain SourceClip source clip}
	 * references in this timeline track. A positive value of origin 
	 * means that the first sample of the essence is earlier than the 
	 * zero position. A negative value of origin means that the zero position 
	 * is earlier than the first sample of the essence.</p>
	 * 
	 * @return Origin of this timeline track.
	 */
	public @PositionType long getOrigin();

	/**
	 * <p>Sets the origin of this timeline track, which
	 * specifies the offset used to resolve {@linkplain SourceClip source clip}
	 * references in this timeline track. A positive value of origin 
	 * means that the first sample of the essence is earlier than the 
	 * zero position. A negative value of origin means that the zero position 
	 * is earlier than the first sample of the essence.</p>
	 * 
	 * @param origin Origin of this timeline track.
	 */
	public void setOrigin(
			@PositionType long origin);

	/**
	 * <p>Returns the mark in property of the timeline track. This 
	 * property specifies the position of the marked in point as a count 
	 * of edit units from the zero position of the timeline track.
	 * This is an optional property.</p> 
	 * 
	 * <p>Note that this property shall not be present for a {@linkplain SourcePackage
	 * source package}.</p>
	 * 
	 * @return Mark in property for the timeline track.
	 * 
	 * @throws PropertyNotPresentException The optional mark in property is not 
	 * present in this timeline track.
	 */
	public @PositionType long getMarkIn()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the mark in property for this timeline track, which
	 * specifies the position of the marked in point as a count 
	 * of edit units from the zero position of this timeline track.
	 * Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * <p>Note that this property shall not be present for a {@linkplain SourcePackage
	 * source package}.</p>
	 * 
	 * @param markIn Mark in property for this timeline track.
	 */
	public void setMarkIn(
			@PositionType Long markIn);
	
	/**
	 * <p>Returns the mark out property of this timeline
	 * track, which specifies the position of the marked out 
	 * point as a count of edit units from the zero position of this
	 * timeline track. This is an optional property.</p>
	 * 
	 * <p>Note that this property shall not be present for a {@linkplain SourcePackage
	 * source package}.</p>
	 * 
	 * @return Mark out property of this timeline track.
	 * 
	 * @throws PropertyNotPresentException The optional mark out property is not 
	 * present in this timeline track.
	 */
	public @PositionType long getMarkOut()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the mark out property of this timeline track, which
	 * specifies the position of the marked out point as a 
	 * count of edit units from the zero position of this timeline 
	 * track. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * <p>Note that this property shall not be present for a {@linkplain SourcePackage
	 * source package}.</p>
	 * 
	 * @param markOut Mark out property of this timeline track.
	 */
	public void setMarkOut(
			@PositionType Long markOut);

	/**
	 * <p>Returns the user position property of the timeline track, which
	 * specifies a user position as a count of edit units from 
	 * the zero position of the timeline track. This is an optional 
	 * property.</p>
	 * 
	 * <p>Note that this property shall not be present for a {@linkplain SourcePackage
	 * source package}.</p>
	 * 
	 * @return The user position property of this timeline track.
	 * 
	 * @throws PropertyNotPresentException The optional user position property is not 
	 * present in this timeline track.
	 */
	public @PositionType long getUserPosition()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the user position property of the timeline track, which
	 * specifies a user position as a count of edit units from 
	 * the zero position of the timeline track. Set this optional property 
	 * to <code>null</code> to omit it.</p>
	 * 
	 * <p>Note that this property shall not be present for a {@linkplain SourcePackage
	 * source package}.</p>
	 * 
	 * @param userPosition User position property of this timeline track.
	 */
	public void setUserPosition(
			@PositionType Long userPosition);
	
	/**
	 * <p>Create a cloned copy of this timeline track.</p>
	 *
	 * @return Cloned copy of this timeline track.
	 */
	public TimelineTrack clone();
}
