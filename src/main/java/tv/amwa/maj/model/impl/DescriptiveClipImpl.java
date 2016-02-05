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
 * $Log: DescriptiveClipImpl.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
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
 * Revision 1.2  2007/12/04 13:04:48  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:08  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyContains;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.DescriptiveClip;
import tv.amwa.maj.union.SourceReferenceValue;


/** 
 * <p>Implements a means to identify what is being described in terms of {@linkplain tv.amwa.maj.model.Track tracks} 
 * and references a {@linkplain tv.amwa.maj.model.Package package} to provide that description. This enables a 
 * {@linkplain tv.amwa.maj.model.Segment segment} in a descriptive metadata {@linkplain TrackImpl track} to reference a
 * section of descriptive metadata in the track of another package. Typically, the referenced track would contain
 * {@linkplain tv.amwa.maj.model.DescriptiveMarker descriptive markers}.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x4500,
		  uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "DescriptiveClip",
		  description = "A descriptive clip specifies what is being described and references a package to provide that description.",
		  symbol = "DescriptiveClip")
public class DescriptiveClipImpl
	extends SourceClipImpl
	implements 
		DescriptiveClip,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -6668716126007849274L;
	
	private Set<Integer> descriptiveClipDescribedTrackIDs = Collections.synchronizedSet(new HashSet<Integer>());
	
	public DescriptiveClipImpl() { }
	
	/**
	 * <p>Creates and initializes a new descriptive clip, which specifies what is being described and 
	 * references a package to provide that description.</p>
	 *
	 * @param dataDefinition Kind of data represented by this component.
	 * @param length Length of the source clip component.
	 * @param sourceReference Reference to the package providing the description.
	 * 
	 * @throws NullPointerException The data definition and/or source reference arguments are
	 * <code>null</code>.
	 * @throws BadLengthException The length of a descriptive clip cannot be negative.
	 */
	public DescriptiveClipImpl(
			DataDefinition dataDefinition,
			long length,
			SourceReferenceValue sourceReference)
		throws NullPointerException,
			BadLengthException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a new descriptive clip using a null data definition.");
		if (sourceReference == null)
			throw new NullPointerException("Cannot create a new descriptive clip with a null source reference.");
		if (length < 0l)
			throw new BadLengthException("Cannot create a new descriptive clip with a negative length.");

		setComponentDataDefinition(dataDefinition);
		setLengthPresent(true);
		setComponentLength(length);
		setSourceReference(sourceReference);
	}

	@MediaProperty(uuid1 = 0x01070106, uuid2 = 0x0000, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "DescriptiveClipDescribedTrackIDs",
			     aliases = { "DescribedSlotIDs" },
			     typeName = "UInt32Set",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x6103,
			     symbol = "DescriptiveClipDescribedTrackIDs")
	public Set<Integer> getDescribedTrackIDs() {

		return new HashSet<Integer>(descriptiveClipDescribedTrackIDs);
	}

	@MediaSetAdd("DescriptiveClipDescribedTrackIDs")
	public void addDescribedTrackID(
			int describedTrackID)
		throws IllegalArgumentException,
			InvalidParameterException {

		if (describedTrackID < 0)
			throw new IllegalArgumentException("A track id cannot be negative and so cannot be added to the set of descrived track ids of this descriptive clip.");
		if (descriptiveClipDescribedTrackIDs.contains(describedTrackID))
			throw new InvalidParameterException("Cannot add the given described track id to the set of described track ids referenced by this descriptive clip already contains it.");
		
		descriptiveClipDescribedTrackIDs.add(describedTrackID);
	}

	@MediaPropertyCount("DescriptiveClipDescribedTrackIDs")
	public int countDescribedTrackIDs() {

		return descriptiveClipDescribedTrackIDs.size();
	}

	@MediaPropertyContains("DescriptiveClipDescribedTrackIDs")
	public boolean isDescribedTrackIDPresent(
			int describedTrackID) 
		throws IllegalArgumentException {

		if (describedTrackID < 0)
			throw new IllegalArgumentException("Cannot check for a negative track id value in the set of described slots ids referenced by this descriptive clip.");
		
		return descriptiveClipDescribedTrackIDs.contains(describedTrackID);
	}

	@MediaPropertyRemove("DescriptiveClipDescribedTrackIDs")
	public void removeDescribedTrackID(
			int describedTrackID)
		throws IllegalArgumentException, 
			InvalidParameterException {

		if (describedTrackID < 0)
			throw new IllegalArgumentException("Cannot remove an illegal negative track id value from the set of described slots ids referenced by this descriptive clip.");
		if (!(descriptiveClipDescribedTrackIDs.contains(describedTrackID)))
			throw new InvalidParameterException("Cannot remove the given described track id value from the set of described track ids referenced by this descriptive clip as it is not currently contained.");

		descriptiveClipDescribedTrackIDs.remove(describedTrackID);
	}
	
	@MediaPropertyClear("DescriptiveClipDescribedTrackIDs")
	public void clearDescribedTrackIDs() {
		
		descriptiveClipDescribedTrackIDs = Collections.synchronizedSet(new HashSet<Integer>());
	}

	@Override
	public DescriptiveClip clone() {
		
		return (DescriptiveClip) super.clone();
	}

}
