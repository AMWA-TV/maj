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
 * $Log: TrackImpl.java,v $
 * Revision 1.4  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.3  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
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
 * Revision 1.1  2007/11/13 22:09:11  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

// TODO consider implementing extra methods of ImplAAFMobSlot.cpp and subclasses.

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.TraversalNotPossibleException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.Component;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.model.Track;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.RationalImpl;


/** 
 * <p>Implements the representation of a single track in a {@linkplain tv.amwa.maj.model.Package package}. 
 * A track describes the relationship between essence and time. In a {@linkplain tv.amwa.maj.model.MaterialPackage material package}, 
 * tracks describe the desired relationship between stored essence and the output timeline.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#TrackStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#TrackStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3800,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Track",
		  aliases = { "MobSlot" },
		  description = "The Track class represents an individual track in a Package.",
		  symbol = "Track",
		  isConcrete = false)
public class TrackImpl
	extends InterchangeObjectImpl
	implements Track,
		Serializable,
		XMLSerializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2911988693521900535L;
	
	private int trackID;
	private String trackName = null;
	private Integer essenceTrackNumber = null;
	private Segment trackSegment;

	public DataDefinition getDataDefinition() {
		
		return trackSegment.getComponentDataDefinition();
	}

	@MediaProperty(uuid1 = 0x01070102, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TrackName",
			aliases = { "SlotName" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4802,
			symbol = "TrackName")
	public String getTrackName() 
		throws PropertyNotPresentException {

		if (trackName == null)
			throw new PropertyNotPresentException("The optional name property is not present for this track.");
		
		return trackName;
	}

	@MediaPropertySetter("TrackName")
	public void setTrackName(
			String trackName) {

		this.trackName = trackName;
	}

	@MediaProperty(uuid1 = 0x01040103, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EssenceTrackNumber",
			aliases = { "PhysicalTrackNumber", "PhysicalNumber", "TrackNumber" },
			typeName = "UInt32", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4804,
			symbol = "EssenceTrackNumber")
	public int getEssenceTrackNumber()
			throws PropertyNotPresentException {

		if (essenceTrackNumber == null)
			throw new PropertyNotPresentException("The physical track number is not set for this track.");
		
		return essenceTrackNumber;
	}

	@MediaPropertySetter("EssenceTrackNumber")
	public void setEssenceTrackNumber(
			Integer essenceTrackNumber) 
		throws IllegalArgumentException {
		
		if (essenceTrackNumber == null) {
			this.essenceTrackNumber = null;
			return;
		}
		
		if (essenceTrackNumber < 0)
			throw new IllegalArgumentException("Cannot set the essence track number for this track to a negative value.");

		this.essenceTrackNumber = essenceTrackNumber;
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0204, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TrackSegment",
			aliases = { "Segment", "MobSlotSegment" },
			typeName = "SegmentStrongReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4803,
			symbol = "TrackSegment")
	public Segment getTrackSegment() {

		return trackSegment; 
	}

	@MediaPropertySetter("TrackSegment")
	public void setTrackSegment(
			Segment trackSegment)
		throws NullPointerException {

		if (trackSegment == null)
			throw new NullPointerException("Cannot set the segment for this package using a null value.");
		
		this.trackSegment = trackSegment;
	}

	public Segment initializeTrackSegment() {
		
		return new FillerImpl(DataDefinitionImpl.forName("Unknown"), 0l);
	}
	
	@MediaProperty(uuid1 = 0x01070101, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TrackID",
			aliases = { "SlotID" },
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4801,
			symbol = "TrackID")
	public int getTrackID() {

		return trackID;
	}

	@MediaPropertySetter("TrackID")
	public void setTrackID(
			int trackID) 
		throws IllegalArgumentException {

		if (trackID < 0)
			throw new IllegalArgumentException("Cannot set the track id of a track to a negative value.");
		
		this.trackID = trackID;
	}
	
	public final static int initializeTrackID() {
		
		return 0;
	}
	
	// TODO test or remove
	/** 
	 *
	 * <p>Class containing the results of an operation to find a segment in a track from its
	 * given offset position.</p>
	 *
	 *
	 *
	 */
	static class FoundSegment {
		
		/** <p>Object within the slot at the given offset.</p> */
		Component rootObject = null;
		
		/** <p>Edit rate at the given offset.</p> */
		Rational sourceRate = new RationalImpl(0, 1);
		
		/** <p>Difference between the given offset and the origin of the segment at that offset.</p> */
		long differencePosition = 0l;
	} 
	
	/**
	 * <p>Find the segment at the given offset into this track.</p>
	 *
	 * @see SegmentImpl#findSubSegment(long offset)
	 * 
	 * @param offset Offset to use to search for a segment at.
	 * @return Segment found at the given offset.
	 * 
	 * @throws TraversalNotPossibleException Traversal of the track was not possible.
	 */
	FoundSegment findSegment(
			long offset) 
		throws TraversalNotPossibleException {
		
		SegmentImpl segment = (SegmentImpl) getTrackSegment();
		
		FoundSegment foundSegment = SegmentImpl.findSubSegment(segment, offset);
		if (foundSegment == null)
			throw new TraversalNotPossibleException("Cannot find the segment at the given offset in this track.");
		
		return foundSegment;
	}

	public Track clone() {
		
		return (Track) super.clone();
	}
	
	@Override
	public String getComment() {
		
		return "local track persistent id: " + getPersistentID();
	}
}
