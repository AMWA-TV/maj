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
 * $Log: MultiCreateItemImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/08 11:32:06  vizigoth
 * Minor comment fix.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:44  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.integer.Int16;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;

// TODO tests and property optionality

/** 
 * <p>Implementation of an element of an array used to create interleaved audio and video essence.</p>
 * 
 * <p><em>Interleaved-essence</em>: An essence format that combines two or more channels 
 * of audio or video data into a single essence stream.</p>
 * 
 * @see tv.amwa.maj.model.MaterialPackage#createMultiEssence(AUIDImpl, tv.amwa.maj.union.MultiCreateItem[], tv.amwa.maj.enumeration.CompressEnable, tv.amwa.maj.model.Locator, AUIDImpl)
 * @see tv.amwa.maj.model.MaterialPackage#extendMultiEssence(AUIDImpl, tv.amwa.maj.union.MultiCreateItem[], tv.amwa.maj.enumeration.CompressEnable, tv.amwa.maj.model.Locator, AUIDImpl)
 * @see tv.amwa.maj.model.EssenceData
 *
 *
 */

public class MultiCreateItemImpl 
	implements tv.amwa.maj.union.MultiCreateItem,
		Serializable,
		Cloneable {
	
	private static final long serialVersionUID = 3510149117693668282L;

	/** Kind of media represented by this item. */
	private AUID mediaKind;
	/** Sub track number associated with this item - the physical output 
	 * channel.*/
	@Int16 private short subTrackNum;
	/** TrackID for this item within the essence. */
	@TrackID private int trackID;
	/** Sample rate of this item. */
	private Rational sampleRate;

    /**
     * <p>Create an element of interleaved essence.</p> 
     * 
	 * @param mediaKind Kind of media represented by this item.
	 * @param subTrackNum Sub track number associated with this item - the physical output channel.
	 * @param trackID Track id for this item within the essence.
	 * @param sampleRate Sample rate of this item.
	 * 
	 * @throws NullPointerException One or both of the given media kind or sample rates is
	 * <code>null</code>.
	 * @throws IllegalArgumentException The given sample rate for this multi-create item has 
	 * a zero denominator or is negative.
	 */
	public MultiCreateItemImpl(
			AUID mediaKind, 
			@Int16 short subTrackNum, 
			@TrackID int trackID, 
			Rational sampleRate) 
		throws NullPointerException,
			IllegalArgumentException {
		
		setMediaKind(mediaKind);
		setTrackID(trackID);
		setSampleRate(sampleRate);
		setSubTrackNum(subTrackNum);
	}

	public AUID getMediaKind() {
		
		return mediaKind;
	}


	public void setMediaKind(
			tv.amwa.maj.record.AUID mediaKind) 
		throws NullPointerException {
		
		if (mediaKind == null)
			throw new NullPointerException("The given media kind for this multi-create item is null.");
		
		this.mediaKind = mediaKind.clone();
	}

	public Rational getSampleRate() {

		return sampleRate;
	}

	public void setSampleRate(
			tv.amwa.maj.record.Rational sampleRate) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (sampleRate == null)
			throw new NullPointerException("The given sample rate for this multi-create item is null.");
		if (sampleRate.getDenominator() == 0)
			throw new IllegalArgumentException("The given sample rate for this multi-create item has a zero denominator.");
		if ((sampleRate.getNumerator() < 0) ^ (sampleRate.getDenominator() < 0)) 
			throw new IllegalArgumentException("The given sample rate for this multi-create item is negative.");
		
		this.sampleRate = sampleRate.clone();
	}

	public @TrackID int getTrackID() {
		
		return trackID;
	}

	public void setTrackID(
			@TrackID int trackID) 
		throws IllegalArgumentException {

		if (trackID < 0)
			throw new IllegalArgumentException("The given track id for this multi-create item is negative.");
		
		this.trackID = trackID;
	}


	public @Int16 short getSubTrackNum() {
		return subTrackNum;
	}

	public void setSubTrackNum(
			@Int16 short subTrackNum) {
		this.subTrackNum = subTrackNum;
	}

	@Override
	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof tv.amwa.maj.union.MultiCreateItem)) return false;
		
		tv.amwa.maj.union.MultiCreateItem testItem = (tv.amwa.maj.union.MultiCreateItem) o;
		
		if (testItem.getTrackID() != trackID) return false;
		if (testItem.getSubTrackNum() != subTrackNum) return false;
		if (!(sampleRate.equals(testItem.getSampleRate()))) return false;
		if (!(mediaKind.equals(testItem.getMediaKind()))) return false;
		
		return true;
	}
	
	/**
	 * <p>Pseudo-XML representation of this multi-create item. No associated XML schema or DTD exists.
	 * For example:</p>
	 * 
	 * <pre>
	 * &lt;MultiCreateItem&gt
	 *   &lt;MediaKind&gt;urn:x-ul:060e2b34.0401.0101.01030202.0100.0000&lt;/MediaKind&gt;
	 *   &lt;TrackID&gt;3&lt;/TrackID&gt;
	 *   &lt;SubTrackNum&gt;7&lt;/SubTrackNum&gt;
	 *   &lt;SampleRate&gt;24/60&lt;/SampleRate&gt;
	 * &lt;/MultiCreateItem&gt;
	 * </pre>
	 * 
	 * @return Pseudo-XML representation of this multi-create item.
	 */
	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder(300);
		builder.append("<MultiCreateItem>\n");
		builder.append("  <MediaKind>" + mediaKind.toString() + "</MediaKind>\n");
		builder.append("  <TrackID>" + trackID + "</TrackID>\n");
		builder.append("  <SubTrackNum>" + subTrackNum + "</SubTrackNum>\n");
		builder.append("  <SampleRate>" + sampleRate + "</SampleRate>\n");
		builder.append("</MultiCreateItem>");
		
		return builder.toString();
	}

	@Override
	public MultiCreateItemImpl clone() 
		throws CloneNotSupportedException {

		return (MultiCreateItemImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return trackID ^ ~subTrackNum ^ mediaKind.hashCode() ^ ~sampleRate.hashCode();
	}
}

