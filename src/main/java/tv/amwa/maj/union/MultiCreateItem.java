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
 * $Log: MultiCreateItem.java,v $
 * Revision 1.2  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/01/27 11:11:54  vizigoth
 * Minor spelling correction in comment.
 *
 * Revision 1.4  2008/01/14 20:52:23  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.3  2008/01/10 17:19:47  vizigoth
 * Minor comment improvement.
 *
 * Revision 1.2  2007/12/14 15:01:49  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.integer.Int16;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;

/** 
 * <p>Specifies an element of an array used to create interleaved audio and video essence.</p>
 *
 * <p><em>Interleaved-essence</em>: An essence format that combines two or more channels 
 * of audio or video data into a single essence stream.</p>
 *
 * 
 *
 */

public interface MultiCreateItem { 

	/**
	 * <p>Returns the kind of media represented by this item.</p>
	 *
	 * @return Kind of media represented by this item.
	 */
	public AUID getMediaKind();

	/**
	 * <p>Sets the kind of media represented by this item.</p>
	 *
	 * @param mediaKind Kind of media represented by this item.
	 * 
	 * @throws NullPointerException The given media kind is <code>null</code>.
	 */
	public void setMediaKind(
			AUID mediaKind) 
		throws NullPointerException;

	/**
	 * <p>Returns the sample rate of this item.</p>
	 *
	 * @return Sample rate of this item.
	 */
	public Rational getSampleRate();

	/**
	 * <p>Sets the sample rate of this item.</p>
	 *
	 * @param sampleRate Sample rate of this item.
	 * 
	 * @throws NullPointerException The given sample rate for the item is <code>null</code>.
	 * @throws IllegalArgumentException The given sample rate for this multi-create item has 
	 * a zero denominator or is negative.
	 */
	public void setSampleRate(
			Rational sampleRate) 
		throws NullPointerException,
			IllegalArgumentException;

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.misctype.TrackID track id} of this item within its 
	 * {@linkplain tv.amwa.maj.model.Package package}.</p>
	 *
	 * @return Track id of this item.
	 */
	public @TrackID int getTrackID();

	/**
	 * <p>Sets the {@linkplain tv.amwa.maj.misctype.TrackID track id} of this item within its
	 * {@linkplain tv.amwa.maj.model.Package package}.</p>
	 *
	 * @param trackID Track id of this item.
	 * 
	 * @throws IllegalArgumentException The given track id for this multi-create item is negative.
	 */
	public void setTrackID(
			@TrackID int trackID);

	/**
	 * <p>Returns the sub track number associated with this item, which is the physical output channel.</p>
	 *
	 * @return Sub track number associated with this item.
	 */
	public @Int16 short getSubTrackNum();

	/**
	 * <p>Sets the sub track number associated with this item, which is the physical output channel.</p>
	 *
	 * @param subTrackNum Sub track number associated with this item.
	 */
	public void setSubTrackNum(
			@Int16 short subTrackNum);
}
