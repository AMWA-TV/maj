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
 * $Log: TimelineTrackImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
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
 * Revision 1.1  2007/11/13 22:09:51  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.TraversalNotPossibleException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.model.Component;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.Event;
import tv.amwa.maj.model.Filler;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.model.Sequence;
import tv.amwa.maj.model.TimelineTrack;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.RationalImpl;


/** 
 * <p>Implements the representation of a track that describes time-varying timeline essence.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3b00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TimelineTrack",
		  aliases = { "TimelineMobSlot" },
		  description = "The TimelineTrack class describes time-varying timeline essence.",
		  symbol = "TimelineTrack")
public class TimelineTrackImpl
	extends 
		TrackImpl
	implements 
		TimelineTrack,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 7780080264046280928L;

	private Rational editRate;
	private long origin;
	private Long markIn = null;
	private Long markOut = null;
	private Long userPosition = null;
	
	/** Default constructor is not public to avoid unset required fields. */
	public TimelineTrackImpl() { }

	/**
	 * <p>Creates and initializes a new timeline track, which describes time-varying timeline 
	 * essence.</p>
	 *
	 * @param trackId Integer that is used to reference the {@link TrackImpl track}.
	 * @param segment Value of the new timeline track.
	 * @param editRate Units of time for the new timeline track.
	 * @param origin Offset used to resolve {@link SourceClipImpl source clip} references to the
	 * new timeline track. A positive value of origin means that the first sample of the essence 
	 * is earlier than the zero position. A negative value of origin means that the zero position is 
	 * earlier than the first sample of the essence.
	 * 
	 * @throws NullPointerException The segment and/or edit rate arguments is/are <code>null</code>.
	 * @throws IllegalArgumentException Track id is negative or the given segment is not valid
	 * for a timeline track.
	 */
	public TimelineTrackImpl(
			@UInt32 int trackId,
			Segment segment,
			Rational editRate,
			@PositionType long origin)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (segment == null)
			throw new NullPointerException("Cannot create a new timeline track with a null segment value.");
		if (editRate == null)
			throw new NullPointerException("Cannot create a new timeline track with a null edit rate.");
		if (trackId < 0)
			throw new IllegalArgumentException("Cannot create a new timeline track with a negative track id.");
		
		setTrackID(trackId);
		setTrackSegment(segment);
		setEditRate(editRate);
		setOrigin(origin);
	}
	
	@MediaProperty(uuid1 = 0x05300405, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EditRate",
			aliases = { "TimelineMobSlotEditRate" },
			typeName = "Rational",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4B01,
			symbol = "EditRate")
	public Rational getEditRate() {

		return editRate.clone();
	}

	@MediaPropertySetter("EditRate")
	public void setEditRate(
			Rational editRate)
		throws NullPointerException {

		if (editRate == null)
			throw new NullPointerException("Cannot set the edit rate for this timeline track using a null value.");
		
		this.editRate = editRate.clone();
	}

	public final static Rational initializeEditRate() {
		
		return new RationalImpl(1, 1);
	}
	
	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x010c, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "MarkIn",
			typeName = "PositionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4B03,
			symbol = "MarkIn")
	public long getMarkIn()
			throws PropertyNotPresentException {

		if (markIn == null)
			throw new PropertyNotPresentException("The optional mark in property is not present for this timeline track.");
		
		return markIn;
	}
	
	@MediaPropertySetter("MarkIn")
	public void setMarkIn(
			Long value) {

		this.markIn = value;
	}

	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x0203, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "MarkOut",
			typeName = "PositionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4B04,
			symbol = "MarkOut")
	public long getMarkOut()
			throws PropertyNotPresentException {

		if (markOut == null)
			throw new PropertyNotPresentException("The optional mark out property is not present for this timeline track.");
		
		return markOut;
	}

	@MediaPropertySetter("MarkOut")
	public void setMarkOut(
			Long value) {

		this.markOut = value;
	}

	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x0103, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Origin",
			aliases = { "SlotOrigin" },
			typeName = "PositionType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4B02,
			symbol = "Origin")
	public long getOrigin() {

		return origin;
	}

	@MediaPropertySetter("Origin")
	public void setOrigin(
			long origin) {

		this.origin = origin;
	}
	
	public final static long initializeOrigin() {
		
		return 0l;
	}

	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x010d, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "UserPosition",
			aliases = { "UserPos" },
			typeName = "PositionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4B05,
			symbol = "UserPosition")
	public long getUserPosition()
			throws PropertyNotPresentException {

		if (userPosition == null)
			throw new PropertyNotPresentException("The user position property is not present for this timeline track.");
		
		return userPosition;
	}

	@MediaPropertySetter("UserPosition")
	public void setUserPosition(
			Long userPosition) {

		this.userPosition = userPosition;
	}
	
	/** 
	 * <p>Sets the segment for this timeline track if the given segment satisfies all the required
	 * constraints. These constraints only affect 
	 * {@link tv.amwa.maj.model.TimelineTrack timeline tracks} with a 
	 * {@link tv.amwa.maj.model.Sequence sequence} that contains an 
	 * {@link tv.amwa.maj.model.Event event}. In this case, the constraints are:</p>
	 * 
	 * <ul>
	 *  <li>All segments in the given sequence must be either {@link tv.amwa.maj.model.Event events}
	 *  or {@link tv.amwa.maj.model.Filler fillers}.</li>
	 *  <li>All events in the sequence shall belong to the same concrete sub-class of event.</li>
	 *  <li>All events and fillers in the sequence shall have the same 
	 *  {@link tv.amwa.maj.model.DataDefinition data definition} as the sequence.</li>
	 * </ul>
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws IllegalArgumentException The given segment does not satisfy the contraints of a timeline
	 * track.
	 * 
	 * @see tv.amwa.maj.model.impl.TrackImpl#setTrackSegment(tv.amwa.maj.model.Segment)
	 */
	@Override
	public void setTrackSegment(
			Segment segment) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (!(segment instanceof Sequence)) {
			segment.setLengthPresent(true);
			super.setTrackSegment(segment);
			return;
		}
		
		Sequence sequence = (Sequence) segment;
		EventImpl firstEvent = null;
		for ( tv.amwa.maj.model.Component componentItem : sequence.getComponentObjects() ) 
			if (componentItem instanceof EventImpl) {
				firstEvent = (EventImpl) componentItem;
				break;
			}
		
		if (firstEvent == null) { // No event found.
			sequence.setLengthPresent(true);
			super.setTrackSegment(sequence);
			return;
		}
		
		DataDefinition sequenceDataDefinition = sequence.getComponentDataDefinition();
		Class<?> eventClass = firstEvent.getClass();
		
		for ( Component componentItem : sequence.getComponentObjects() ) {
					
			if ((!(componentItem instanceof Event)) && (!(componentItem instanceof Filler)))
				throw new IllegalArgumentException("If a timeline track contains a sequence with an event, all elements of that sequence must be either events or fillers.");
			
			if (!(componentItem.getComponentDataDefinition().doesDataDefConvertTo(sequenceDataDefinition)))
				throw new IllegalArgumentException("All events and fillers in a timeline track must have the same data definition as the sequence.");
			
			if (componentItem == firstEvent) continue;
			
			if (componentItem instanceof Event) {
				if (!(eventClass.isInstance(componentItem)))
					throw new IllegalArgumentException("All events in the sequence of a timeline track must be of the same concrete event class.");
			}				
		}
		
		sequence.setLengthPresent(true);
		super.setTrackSegment(sequence);
	}
	
	/**
	 * <p>Sets this timeline track to be in the context of a source package. In this context, the
	 * mark in, mark out and user position properties are all not present.</p>
	 *
	 */
	public void sourcePackageContext() {
		
		this.markIn = null;
		this.markOut = null;
		this.userPosition = null;
	}
	
	/** 
	 * @see tv.amwa.maj.model.impl.TrackImpl#findSegment(long)
	 */
	@Override
	FoundSegment findSegment(
			long offset) 
		throws TraversalNotPossibleException {
		
		Rational sourceRate = getEditRate();
		long origin = getOrigin();
		Segment segment = getTrackSegment();
		
		// The origin of a track is normally zero - ie. it is
		// at the first sample position.  If samples are
		// inserted at the start of the track, then the origin
		// shifts to the right and is greater than zero.  If
		// samples are removed from the start of the track the
		// origin shifts to the left and is less than zero.
		//
		// External sample references (by SourceClip's) into
		// the track are always relative to the origin.  This
		// ensures that external references do not have to be
		// adjusted when samples are inserted or removed.
		//
		// For example, if a SourceClip references sample
		// number 10, then someone inserts 5 samples, the
		// origin and the referenced sample shift 5 positions
		// to the right.  When the source clip fetches (what
		// it considers to be) sample 10, it actually gets
		// sample 10+5 = 15.
		// 
		// All that, to explain the following line of code:
		offset += origin;
		
		FoundSegment foundSegment = SequenceImpl.findSubSegment(segment, offset);
		foundSegment.sourceRate = sourceRate;
		
		if (foundSegment == null)
			throw new TraversalNotPossibleException("Could not find a segment at the given offset for this timeline track.");
		
		return foundSegment;
	}

	public TimelineTrack clone() {
		
		return (TimelineTrack) super.clone();
	}
	
	public String getEditRateString() {
		
		return RationalImpl.toPersistentForm(editRate);
	}
	
	public void setEditRateString(
			String editRate) {
		
		this.editRate = RationalImpl.fromPersistentForm(editRate);
	}
}
