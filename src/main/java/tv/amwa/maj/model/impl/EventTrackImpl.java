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
 * $Log: EventTrackImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/06/18 16:47:26  vizigoth
 * Fixed naming issue that caused issues with the media engine setting values.
 *
 * Revision 1.2  2010/02/10 23:57:35  vizigoth
 * Improvements to create and mod time method names in Package to match meta dictionary and other minor fixes.
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
 * Revision 1.1  2007/11/13 22:09:23  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.model.CommentMarker;
import tv.amwa.maj.model.Component;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.DescriptiveClip;
import tv.amwa.maj.model.Event;
import tv.amwa.maj.model.EventTrack;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.model.Sequence;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.RationalImpl;


/** 
 * <p>Implements a container for a {@linkplain tv.amwa.maj.model.Sequence sequence} of 
 * {@linkplain tv.amwa.maj.model.Event events}.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3900,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "EventTrack",
		  aliases = { "EventMobSlot" },
		  description = "The EventTrack class has a Sequence of Events.",
		  symbol = "EventTrack")
public class EventTrackImpl
	extends 
		TrackImpl
	implements 
		EventTrack,
		Serializable,
		Cloneable {

	private Rational eventTrackEditRate;
	@PositionType private Long eventTrackOrigin = null;

	/** <p></p> */
	private static final long serialVersionUID = -2413811904896253033L;
	
	/** Default constructor is not public to avoid unset required fields. */
	public EventTrackImpl() { }

	/**
	 * <p>Creates and initializes a new event track, which holds a sequence of events.</p>
	 *
	 * @param trackId Integer that is used to reference the new track.
	 * @param segment Value of the new track.
	 * @param editRate Units in which the events specify their starting time and duration.
	 * 
	 * @throws NullPointerException The segment or edit rate values are <code>null</code>.
	 * @throws IllegalArgumentException Cannot set the track id for an event track to
	 * a negative value.
	 */
	public EventTrackImpl(
			@UInt32 int trackId,
			Segment segment,
			Rational editRate) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (segment == null)
			throw new NullPointerException("Cannot create a new event track with a null value.");
		if (editRate == null)
			throw new NullPointerException("Cannot create a new event track with a null edit rate value.");
		
		setTrackID(trackId);
		setTrackSegment(segment);
		setEventTrackEditRate(editRate);
	}

	@MediaProperty(uuid1 = 0x05300402, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EventTrackEditRate",
			aliases = { "EditRate", "EventMobSlotEditRate", "EditTrackEditRate" },
			typeName = "Rational",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4901,
			symbol = "EventTrackEditRate")
	public Rational getEventTrackEditRate() {

		return eventTrackEditRate.clone();
	}

	@MediaPropertySetter("EventTrackEditRate")
	public void setEventTrackEditRate(
			tv.amwa.maj.record.Rational eventTrackEditRate)
		throws NullPointerException {

		if (eventTrackEditRate == null)
			throw new NullPointerException("Cannot set the edit rate for this event track with a null value.");
		
		this.eventTrackEditRate = eventTrackEditRate.clone();
	}

	public final static Rational initializeEventTrackEditRate() {
		
		return new RationalImpl(1, 1);
	}
	
	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x010b, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "EventTrackOrigin",
			aliases = { "EventSlotOrigin" },
			typeName = "PositionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4902,
			symbol = "EventTrackOrigin")
	public long getEventTrackOrigin()
		throws PropertyNotPresentException {
		
		if (eventTrackOrigin == null)
			throw new PropertyNotPresentException("The optional event track origin property is not present in this event track.");
		
		return eventTrackOrigin;
	}

	@MediaPropertySetter("EventTrackOrigin")
	public void setEventTrackOrigin(
			Long eventTrackOrigin) {
		
		this.eventTrackOrigin = eventTrackOrigin;
	}
	
	/** 
	 * <p>This method applies the constraints required for a segment that forms part of an event
	 * track. These are:</p>
	 * 
	 * <ul>
	 *  <li>The segment of an event track must either be an {@link tv.amwa.maj.model.Event event}
	 *  or a {@link tv.amwa.maj.model.Sequence sequence}.</li>
	 *  <li>If the segment is a sequence, the following additional constraints apply:
	 *   <ol>
	 *    <li>All elements of the sequence must be events and events of the same kind.</li>
	 *    <li>All elements of the sequence must have the same {@link DataDefinitionImpl data kind}.</li>
	 *    <li>The data kind for each element must be the same as the data kind for the sequence.</li>
	 *    <li>The {@link EventImpl#getEventPosition() positions} for each event must be increasing with the
	 *    order of the sequence.</li>
	 *   </ol></li>
	 * </ul>
	 *  
	 * @see tv.amwa.maj.model.impl.TrackImpl#setTrackSegment(tv.amwa.maj.model.Segment)
	 */
	@Override
	public void setTrackSegment(
			Segment segment) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (segment == null)
			throw new NullPointerException("Cannot set the segment for this package using a null value.");
		
		if ((!(segment instanceof Sequence)) && (!(segment instanceof Event)) && (!(segment instanceof DescriptiveClip)))
			throw new IllegalArgumentException("The segment for an event track must be either a sequence, an event segment or a descriptive clip.");
		
		if ((segment instanceof Event) || (segment instanceof DescriptiveClip)) {
			super.setTrackSegment(segment);
			return;
		}
			
		// Local segment must be a sequence if we get here.
		Sequence sequence = (Sequence) segment;
	
		int numberOfComponents = sequence.countComponentObjects();
		if (numberOfComponents <= 0)
			throw new IllegalArgumentException("An event track sequence must contain at least one element.");
		
		DataDefinition sequenceDataDefinition = sequence.getComponentDataDefinition();
		Component firstComponent = sequence.getComponentObjectAt(0);
		
		if (!(firstComponent.getComponentDataDefinition().doesDataDefConvertTo(sequenceDataDefinition)))
			throw new IllegalArgumentException("Components of a sequence in an event track must have the same data kind as that of the sequence itself and the first component does not.");
		
		if (!(firstComponent instanceof EventImpl))
			throw new IllegalArgumentException("Components of a sequence in an event track must be events and the first component is not.");
		
		Event firstEvent = (Event) firstComponent;
		try {
			long previousPosition = firstEvent.getEventPosition();
			for ( int x = 1 ; x < numberOfComponents ; x++ ) {
				
				Component componentItem = sequence.getComponentObjectAt(x);
				if (!(componentItem.getComponentDataDefinition().doesDataDefConvertTo(sequenceDataDefinition)))
					throw new IllegalArgumentException("Components of a sequence in an event track must have the same data kind as that of the sequence itself and the component at index " + x + " does not.");
				
				if (!(componentItem instanceof Event))
					throw new IllegalArgumentException("Components of a sequence in an event track must have be events and the component at index " + x + " is not.");
				
				Event eventItem = (Event) componentItem;
				
				// TODO test this ... it may be that this should be done by interfaces and not classes.
				if (!(firstEvent.getClass().isInstance(eventItem)))
					throw new IllegalArgumentException("Components of a sequence in an event track must all be for the same kind of event and the element at index " + x + " does not match the first event of the sequence.");
	
				long currentPosition = eventItem.getEventPosition();
				if (currentPosition < previousPosition)
					throw new IllegalArgumentException("Components of a sequence in an event track must be in increasing position order and the component at index " + x + " has an earlier position than its previous component.");
					
				previousPosition = currentPosition;
			}
		}
		catch (BadPropertyException bpe) {
			throw new IllegalArgumentException("Unexpected bad property exception thrown when checking the validity of a sequence for an event track.");
		}
		
		super.setTrackSegment(sequence);
	}

	@Override
	public Segment initializeTrackSegment() {
		
		CommentMarker marker = new CommentMarkerImpl(DataDefinitionImpl.forName("Unknown"));
		Sequence sequence = new SequenceImpl(marker.getComponentDataDefinition());
		try {
			sequence.appendComponentObject(marker);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return sequence;
	}
	
	public EventTrack clone() {
		
		return (EventTrack) super.clone();
	}
	
	public String getEventTrackEditRateString() {
		
		return RationalImpl.toPersistentForm(eventTrackEditRate);
	}
	
	public void setEventTrackEditRateString(
			String eventTrackEditRate) {
		
		this.eventTrackEditRate = RationalImpl.fromPersistentForm(eventTrackEditRate);
	}
			
}
