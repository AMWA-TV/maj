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
 * $Log: SequenceImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2011/01/04 09:56:37  vizigoth
 * Do not attempt to process events in sequences with no length or zero length.
 *
 * Revision 1.3  2010/02/10 23:57:35  vizigoth
 * Improvements to create and mod time method names in Package to match meta dictionary and other minor fixes.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
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
 * Revision 1.2  2007/12/04 13:04:49  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:41  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.exception.AdjacentTransitionException;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.EventSemanticsException;
import tv.amwa.maj.exception.InsufficientTransitionMaterialException;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.LeadingTransitionException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.model.Component;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.Event;
import tv.amwa.maj.model.Filler;
import tv.amwa.maj.model.Sequence;
import tv.amwa.maj.model.Transition;


// TODO Consider whether length present should cascade to all elements?

/** 
 * <p>Implements the representation of the combination of an ordered list of {@linkplain tv.amwa.maj.model.Segment segments}
 * and {@linkplain tv.amwa.maj.model.Transition transitions}. A sequence is the mechanism for combining sections 
 * of essence to be played in a sequential manner. If a sequence object has one 
 * {@linkplain tv.amwa.maj.model.Segment segment} followed by another {@linkplain tv.amwa.maj.model.Segment segment}, after the 
 * first {@linkplain tv.amwa.maj.model.Segment segment} is played, the following one begins immediately.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0f00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Sequence",
		  description = "The Sequence class combines an ordered list of Segments and Transitions.",
		  symbol = "Sequence")
public class SequenceImpl
	extends 
		SegmentImpl
	implements 
		Sequence,
		tv.amwa.maj.extensions.quantel.QSequence,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 2801133940754656994L;

	private List<Component> componentObjects = Collections.synchronizedList(new Vector<Component>());
	
	public SequenceImpl() { }

	/**
	 * <p>Creates and initializes a new sequence, which combines an ordered list of 
	 * {@link SegmentImpl segments} and {@link TransitionImpl transitions}.</p>
	 *
	 * @param dataDefinition Kind of data represented by this component.
	 * 
	 * @throws NullPointerException Data definition is <code>null</code>.
	 */
	public SequenceImpl(
			DataDefinition dataDefinition)
		throws NullPointerException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a new sequence with a null data definition value.");
		
		setComponentDataDefinition(dataDefinition);
		setLengthPresent(true); // Default length is 0
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0609, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ComponentObjects",
			aliases = { "Components" },
			typeName = "ComponentStrongReferenceVector",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1001,
			symbol = "ComponentObjects")
	public List<Component> getComponentObjects() {
	
		return StrongReferenceVector.getRequiredList(componentObjects);
	}
	
	public final static List<Component> initializeComponentObjects() {
		
		List<Component> initialComponents = new ArrayList<Component>();
		initialComponents.add(new FillerImpl(DataDefinitionImpl.forName("Unknown"), 0l));
		return initialComponents;
	}
	
	@MediaListAppend("ComponentObjects")
	public void appendComponentObject(
			Component componentObject)
		throws NullPointerException,
			InvalidDataDefinitionException,
			LeadingTransitionException,
			EventSemanticsException,
			BadPropertyException,
			BadLengthException,
			AdjacentTransitionException,
			InsufficientTransitionMaterialException {

		// FIXME removed check due to serialization order from ref implementation AAF XML
		// if (!(componentObject.getComponentDataDefinition().doesDataDefConvertTo(getComponentDataDefinition())))
			// throw new InvalidDataDefinitionException("Cannot convert the data definition of the given component to the data definition is this sequence.");
		
		// TODO write test for all of this
		/* Three distinct cases to handle here:
		 * 
		 * 1) This is first component to be appended to the sequence.
		 * 2) This is a sequence of events, as determined by the type of
		 * the first entry in the sequence.  Enforce event sequence policies.
		 * 3) This is not a sequence of events, as determined by the type
		 * of the first entry in the sequence.  Enforce !event sequence
		 * policies.
		 */ 
		
		// If this sequence has been created with a default value filler, remove the filler first
		if ((componentObjects.size() == 1) &&
				(componentObjects.get(0) instanceof Filler) &&
					(((Filler) componentObjects.get(0)).getComponentLength() == 0))
			clearComponentObjects();
		
		if (componentObjects.size() == 0) {
			checkFirstComponentSemantics(componentObject);
			
			if (componentObject instanceof Event)
				updateSequenceLength((Event) componentObject);
			else
				updateSequenceLength(componentObject);
		}
		else if (componentObjects.get(0) instanceof Event) {
			Event event;
			try {
				event = (Event) componentObject;
			}
			catch (ClassCastException cce) {
				throw new EventSemanticsException("Cannot append a non-event component to a sequence of events.");
			}
			checkTypeSemantics(event); 
			checkPositionSemantics(event);
			checkLengthSemantics(event);
			updateSequenceLength(event);
		}
		else {
			checkTypeSemantics(componentObject);
			checkPositionSemantics(componentObject);
			checkLengthSemantics(componentObject);
			updateSequenceLength(componentObject);
		}
				
		StrongReferenceVector.append(componentObjects, componentObject);
	}

	/**
	 * <p>Assuming that the given component is to be the first of a sequence, this method checks that
	 * the component is not a transition as this is not allowed.</p>
	 *
	 * @param component Component to test.
	 * 
	 * @throws LeadingTransitionException The first element of a sequence cannot be a transition.
	 */
	private void checkFirstComponentSemantics(
			Component component) 
		throws LeadingTransitionException {
		
		if (component instanceof tv.amwa.maj.model.Transition)
			throw new LeadingTransitionException("The first element of a sequence cannot be a transition.");
	}

	/**
	 * <p></p>
	 *
	 * @param event
	 * @throws EventSemanticsException
	 */
	private void checkTypeSemantics(
			Event event) 
		throws EventSemanticsException {
		
		if (!(event.getClass().equals(componentObjects.get(componentObjects.size() - 1).getClass())))
			throw new EventSemanticsException("The given event does not match the type of events stored in this sequence of events.");
	}
	
	/**
	 * <p></p>
	 *
	 * @param component
	 * @throws AdjacentTransitionException
	 */
	private void checkTypeSemantics(
			Component component) 
		throws AdjacentTransitionException {
		
		if ((component instanceof TransitionImpl) &&
				(componentObjects.get(componentObjects.size() - 1) instanceof TransitionImpl))
			throw new AdjacentTransitionException("Cannot append the given transition to the list of compositions of this sequence as this would result in adjacent transitions.");	
	}
	
	/**
	 * <p></p>
	 *
	 * @param event
	 * @throws EventSemanticsException
	 */
	private void checkPositionSemantics(
			Event event) 
		throws EventSemanticsException {
		
		try {
			long posNext = event.getEventPosition();
			Event lastEvent = (Event) componentObjects.get(componentObjects.size() - 1);
			long posLast = lastEvent.getEventPosition();
			if (posLast > posNext) {
				throw new EventSemanticsException("The position of an event must be greater than or equal to the position of the last event.");
			}
		}
		catch (BadPropertyException bpe) {
			throw new EventSemanticsException("The given event does not have a position set in its current context.");
		}
		catch (ClassCastException cce) {
			throw new EventSemanticsException("The event is being added to a list of components of a sequence that does not appear to contain events.");
		}
	}
	
	/**
	 * <p></p>
	 *
	 * @param component
	 */
	private void checkPositionSemantics(Component component) {
		
		// Placeholder for future tests - nothing to check for components.
	}
	
	/**
	 * <p></p>
	 *
	 * @param event
	 */
	private void checkLengthSemantics(Event event) {
		
		// Event lengths are optional ... do nothing here.
	}
	
	/**
	 * <p></p>
	 *
	 * @param component
	 * @throws InsufficientTransitionMaterialException
	 */
	private void checkLengthSemantics(
			Component component) 
		throws InsufficientTransitionMaterialException,
			BadPropertyException {
		
		try {
			long lengthNext = component.getComponentLength();
			
			Component lastComponent = componentObjects.get(componentObjects.size() - 1);
			long lengthLast = lastComponent.getComponentLength();
			
			if (((component instanceof TransitionImpl) && (lengthLast < lengthNext)) ||
					((lastComponent instanceof TransitionImpl) && (lengthNext < lengthLast)))
				throw new InsufficientTransitionMaterialException("There is not enough material to achieve the transition to be appended or at the end of the list of components of this sequence.");
		}
		catch (BadPropertyException bpe) {
			throw new BadPropertyException("Components in sequences must have the otherwise optional length property set.");
		}
	}
	
	/**
	 * <p></p>
	 *
	 * @param event
	 * @throws BadPropertyException
	 * @throws BadLengthException
	 */
	private void updateSequenceLength(
			Event event) 
		throws BadPropertyException,
			BadLengthException {
		
		/* If this sequence does not have a length property, and event
		 * does not have a length property, then do nothing (i.e. do not set
		 * this sequence's length).
		 * 
		 * If this sequence does have a length property, and event does not
		 * have a length property then assume the length of event is zero and
		 * update length normally.
		 * 
		 * If both this sequence and pEvent have a length properties then update
		 * this sequence normally.
		 */

		if ((getLengthPresent() == false) || (event.getLengthPresent() == false)) return;
		
		long posNext = event.getEventPosition();
		long lengthNext = 0l;
		try {
			lengthNext = event.getComponentLength();
		}
		catch (BadPropertyException bpe) { /* Event has no length set, so assume 0. */ }
		
		if (componentObjects.size() == 0) {
			setLengthPresent(true);
			setComponentLength(lengthNext);
			return;
		}
		
		Event firstEvent = (Event) componentObjects.get(0);
		long posFirst = firstEvent.getEventPosition();
		long seqLength = 0l;
		try {
			seqLength = getComponentLength();
		}
		catch (BadPropertyException bpe) { 
			/* Surprising if we get here. */ 
			bpe.printStackTrace();
			throw bpe;
		}
		
		if (posNext + lengthNext - posFirst > seqLength ) 
			seqLength = posNext + lengthNext - posFirst;

		setComponentLength(seqLength);
	}
	
	/**
	 * <p></p>
	 *
	 * @param component
	 * @throws BadPropertyException
	 */
	private void updateSequenceLength(
			Component component)
		throws BadPropertyException {
		
		long seqLength = 0l;

		for ( Component componentInList : componentObjects) 
			if (componentInList instanceof Transition) 
				seqLength -= componentInList.getComponentLength();
			else 
				seqLength += componentInList.getComponentLength();
		
		if ((component != null) && (component.getLengthPresent())) {
			if (component instanceof Transition)
				seqLength -= component.getComponentLength();
			else
				seqLength += component.getComponentLength();
		}
		
		setLengthPresent(true);
		try { setComponentLength(seqLength); }
		catch (BadLengthException ble) {
			/* If we are here, the maths messed up in this of checkLengthSemantics. */
			ble.printStackTrace();
		}
	}
		
	@MediaPropertyCount("ComponentObjects")
	public int countComponentObjects() {

		return componentObjects.size();
	}

	@MediaPropertyClear("ComponentObjects")
	public void clearComponentObjects() {
		
		componentObjects.clear();
	}
	
	@MediaListGetAt("ComponentObjects")
	public Component getComponentObjectAt(
			int index)
		throws IndexOutOfBoundsException {

		return StrongReferenceVector.getAt(componentObjects, index);
	}

	/**
	 * <p>Set the component at the given index with the given component.</p>
	 * 
	 * <p>SDK internal method used to save on a component removal followed by a component addition.</p>
	 *
	 * @param component Component to replace in the current list of compoents.
	 * @param index Index at which replacement is to take place.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for this sequence.
	 */
	void setNthComponent(
			Component component, 
			int index) 
		throws IndexOutOfBoundsException {
	
		componentObjects.set(index, component.clone());
		component.setPersistentIndex(index);
	}

	static void setNthComponent(
			Sequence sequence,
			Component component,
			int index) 
		throws IndexOutOfBoundsException {
		
		((SequenceImpl) sequence).setNthComponent(component, index);
	}
	
	@MediaListInsertAt("ComponentObjects")
	public void insertComponentObjectAt(
			int index,
			Component component)
		throws NullPointerException,
			IndexOutOfBoundsException,
			InvalidDataDefinitionException,
			LeadingTransitionException,
			AdjacentTransitionException,
			InsufficientTransitionMaterialException {
		// TODO This is not fully implemented in the C version - check if it should be?
		
		StrongReferenceVector.insert(componentObjects, index, component);
	}

	@MediaListPrepend("ComponentObjects")
	public void prependComponentObject(
			Component component)
		throws NullPointerException,
			InvalidDataDefinitionException,
			LeadingTransitionException {
		// TODO This is not fully implemented in the C version - check if it should be?

		StrongReferenceVector.prepend(componentObjects, component);
	}

	@MediaListRemoveAt("ComponentObjects")
	public void removeComponentObjectAt(
			int index)
		throws IndexOutOfBoundsException {
		// TODO This is not fully implemented in the C version - check if it should be?

		StrongReferenceVector.remove(componentObjects, index);
	}
	
	// Begin - Quantel extensions
	
	private Integer compositeRushIndicator = null;
	private String compositionRushID = null;
	private Long compositeRushOffset = null;
	
    @MediaProperty(uuid1 = 0xb18a3d40, uuid2 = (short) 0x2ff0, uuid3 = (short) 0x4159,
        uuid4 = { (byte) 0x96, (byte) 0x8b, (byte) 0x3a, (byte) 0xba, (byte) 0x47, (byte) 0x65, (byte) 0x89, (byte) 0x9f },
        definedName = "Composite rush indicator",
        symbol = "Composite_rush_indicator",
        aliases = { "Composite_rush_indicator" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getCompositeRushIndicator()
		throws PropertyNotPresentException {
		
		if (compositeRushIndicator == null)
			throw new PropertyNotPresentException("The optional composite rush indicator property is not present for this Quantel sequence.");
		
		return compositeRushIndicator;
	}
	
	@MediaPropertySetter("Composite rush indicator")
	public void setCompositeRushIndicator(
			@Int32 Integer compositeRushIndicator) {
		
		this.compositeRushIndicator = compositeRushIndicator;
	}
	
    @MediaProperty(uuid1 = 0xeb84a895, uuid2 = (short) 0x73a3, uuid3 = (short) 0x4cdc,
        uuid4 = { (byte) 0x92, (byte) 0x9a, (byte) 0x99, (byte) 0xeb, (byte) 0xe0, (byte) 0x92, (byte) 0x1b, (byte) 0x43 },
        definedName = "Composite rush id",
        symbol = "Composite_rush_id",
        aliases = { "Composite_rush_id" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public String getCompositeRushID()
		throws PropertyNotPresentException {
		
		if (compositionRushID == null)
			throw new PropertyNotPresentException("The optional composite rush ID property is not present for this Quantel sequence.");
		
		return compositionRushID;
	}
	
	@MediaPropertySetter("Composite rush id")
	public void setCompositeRushID(
			String compositeRushID) {
		
		this.compositionRushID = compositeRushID;
	}
	
    @MediaProperty(uuid1 = 0x7eeee9d5, uuid2 = (short) 0x72ba, uuid3 = (short) 0x49b9,
        uuid4 = { (byte) 0x9e, (byte) 0xbc, (byte) 0xe3, (byte) 0x97, (byte) 0x93, (byte) 0x2f, (byte) 0x89, (byte) 0xc1 },
        definedName = "Composite rush offset",
        symbol = "Composite_rush_offset",
        aliases = { "Composite_rush_offset" },
        typeName = "Int64",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int64 long getCompositeRushOffset()
		throws PropertyNotPresentException {
		
		if (compositeRushOffset == null)
			throw new PropertyNotPresentException("The optional composition rush offset property is not present for this Quantel sequnce.");
	
		return compositeRushOffset;
	}
	
	@MediaPropertySetter("CompositeRushOffset")
	public void setCompositeRushOffset(
			@Int64 Long compositeRushOffset) {
		
		this.compositeRushOffset = compositeRushOffset;
	}
	
	// End - Quantel extensions
	
	/** 
	 * @see tv.amwa.maj.model.impl.SegmentImpl#clone()
	 */
	public Sequence clone() {
		
		return (Sequence) super.clone();
	}
}
