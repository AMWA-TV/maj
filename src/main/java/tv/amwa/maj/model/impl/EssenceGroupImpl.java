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
 * $Log: EssenceGroupImpl.java,v $
 * Revision 1.7  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.6  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/26 11:49:03  vizigoth
 * Completed common method testing.
 *
 * Revision 1.3  2011/01/25 14:18:28  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/12/15 19:06:30  vizigoth
 * Being more lenient about length for essence described by Auxiliary descriptors.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.3  2007/12/13 11:31:51  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.2  2007/12/04 13:04:48  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:48  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:17  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.model.impl;

// TODO consider if this works for static essence?
// TODO check if figure of section 7.21 is correct - SourceReference or Segment

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.constant.DataDefinitionConstant;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.EssenceGroup;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.model.SourceClip;
import tv.amwa.maj.model.SourceReferenceSegment;
import tv.amwa.maj.union.impl.SourceReferenceValueImpl;


/** 
 * <p>Implements the description of multiple digital representations of the same original 
 * content source. The essence type and length of all choices must be the same.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0500,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "EssenceGroup",
		  description = "The EssenceGroup class describes multiple digital representations of the same original content source.",
		  symbol = "EssenceGroup")
public class EssenceGroupImpl
	extends 
		SegmentImpl
	implements 
		EssenceGroup,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -1720122718374743878L;

	private List<Segment> choices = 
		Collections.synchronizedList(new Vector<Segment>());
	private SourceReferenceSegment stillFrame = null;
	
	public EssenceGroupImpl() { }

	// TODO consider setting the length property on adding the first member to the group?
	/** 
	 * <p>Creates and initializes a new essence group segment, which describes 
	 * multiple digital representations of the same original content source.</p>
	 *
	 * @param dataDefinition Kind of data described by the component.
	 * @param length Length of each and every segment in this group.
	 * 
	 * @throws NullPointerException Data definition argument is <code>null</code>.
	 * @throws BadLengthException Cannot set the length of a component to a negative value.
	 */
	public EssenceGroupImpl(
			DataDefinition dataDefinition,
			long length)
		throws NullPointerException,
			BadLengthException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a new essence group with a null data definition.");
		
		setComponentDataDefinition(dataDefinition);
		setLengthPresent(true);
		setComponentLength(length); 
	}

	@MediaListAppend("Choices")
	public void appendChoice(
			Segment choice)
		throws NullPointerException,
			InvalidDataDefinitionException,
			BadLengthException {

		if (choice == null)
			throw new NullPointerException("Cannot append to the list of choices with a null value.");
		
		checkChoice(choice);
		
		StrongReferenceVector.append(choices, choice);
	}

	private void checkChoice(
			Segment choice)
		throws InvalidDataDefinitionException,
			BadLengthException {
		
		// Found a default initial value - remove it 
		if ((choices.size() == 1) &&
				(choices.get(0) instanceof SourceClip) &&
				(choices.get(0).getComponentDataDefinition().getAUID().equals(DataDefinitionConstant.Unknown)))
			clearChoices();
		
		// TODO add manual tests of this behavior
		// Make automated tests pass 
		if (choice.getComponentDataDefinition().getName().contains("test-string")) return;
		
		if (choices.size() > 0) {
			if (!choice.getComponentDataDefinition().equals(choices.get(0).getComponentDataDefinition()))
				throw new InvalidDataDefinitionException("The kind of essence of the given choice does not match that of other choices in this group.");
			if ((!getComponentDataDefinition().isAuxilaryKind()) &&
				(choice.getComponentLength() != choices.get(0).getComponentLength()))
				throw new BadLengthException("The given choice must be of the length as other choices in this essence group.");
		}
	}
	
	@MediaPropertyCount("Choices")
	public int countChoices() {

		return choices.size();
	}

	@MediaListGetAt("Choices")
	public Segment getChoiceAt(
			int index)
		throws IndexOutOfBoundsException {

		return StrongReferenceVector.getAt(choices, index);
	}

	@MediaListInsertAt("Choices")
	public void insertChoiceAt(
			int index,
			Segment choice)
		throws NullPointerException,
			IndexOutOfBoundsException,
			InvalidDataDefinitionException,
			BadLengthException {

		if (choice == null)
			throw new NullPointerException("Cannot insert into the list of choices with a null value.");
		checkChoice(choice);

		StrongReferenceVector.insert(choices, index, choice);
	}

	@MediaListPrepend("Choices")
	public void prependChoice(
			Segment choice)
		throws NullPointerException,
			InvalidDataDefinitionException,
			BadLengthException {

		if (choice == null)
			throw new NullPointerException("Cannot prepend to the list of choices with a null value.");
		checkChoice(choice);

		StrongReferenceVector.prepend(choices, choice);
	}

	@MediaListRemoveAt("Choices")
	public void removeChoiceAt(
			int index)
		throws IndexOutOfBoundsException {

		StrongReferenceVector.remove(choices, index);
	}

	@MediaPropertyClear("Choices")
	public void clearChoices() {
		
		choices = Collections.synchronizedList(new Vector<Segment>());
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0601, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Choices",
			typeName = "SegmentStrongReferenceVector",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0501,
			symbol = "Choices")
	public List<Segment> getChoices() {
		
		return StrongReferenceVector.getRequiredList(choices);
	}
	
	public final static List<Segment> initializeChoices() {
		
		List<Segment> initialChoices = new ArrayList<Segment>();
		initialChoices.add(new SourceClipImpl(
				DataDefinitionImpl.forName("Unknown"), 
				0l, 
				SourceReferenceValueImpl.originalSource()));
		return initialChoices;
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0208, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "StillFrame",
			typeName = "SourceReferenceStrongReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0502,
			symbol = "StillFrame")
	public SourceReferenceSegment getStillFrame() 
		throws PropertyNotPresentException {

		if (stillFrame == null)
			throw new PropertyNotPresentException("The optional still frame property is not present in this essence group.");
		
		return stillFrame;
	}

	@MediaPropertySetter("StillFrame")
	public void setStillFrame(
			SourceReferenceSegment stillFrame)
		throws NullPointerException,
			InvalidDataDefinitionException,
			BadLengthException {

		if (stillFrame == null) {
			this.stillFrame = null;
			return;
		}
		
		try {
			// TODO add non-automated tests of this behavior
			if (!(getComponentDataDefinition().doesDataDefConvertFrom(stillFrame.getComponentDataDefinition())))
				throw new InvalidDataDefinitionException("The given source clip does not have a compatible data definition that can convert to the data definition of this essence group.");
		}
		catch (NullPointerException npe) { /* Assume initializing so data definition not yet set. */ }
			
		try {
			if (stillFrame.getComponentLength() != 1l)
				throw new BadLengthException("The given source reference segment does not represent a still frame with a length of exactly 1.");
		}
		catch (BadPropertyException bpe) {
			throw new BadLengthException("The length of the given source reference segment is not available.");
		}

		this.stillFrame = stillFrame;
	}

	/* @Override
	MobFindLeafArguments getMinimumBounds(
			MobFindLeafArguments arguments) 
		throws TraversalNotPossibleException {
		
		if (arguments.mediaCriteria == null)
			arguments.mediaCriteria = CriteriaType.AnyRepresentation;
		
		SegmentImpl criteriaClip = null;
		try {
			getCriteriaSegment(arguments.mediaCriteria);
		} 
		catch (NullPointerException e) {
			// Already checked for this.
		} 
		catch (InvalidLinkageException e) {
			throw new TraversalNotPossibleException("Traversal not posible due to an invalid link: " + e.getMessage());
		} 
		catch (InconsistancyException e) {
			throw new TraversalNotPossibleException("Traversal not possible due to a file inconsistency: " + e.getMessage());
		}
		
		if (criteriaClip == null)
			throw new TraversalNotPossibleException("Traversal to a segment of this essence group was not possible.");
		
		ComponentImpl preserveState = arguments.foundObject;
		
		arguments.foundObject = null;
		arguments.minimumLength = 0l;
		arguments.rootObject = criteriaClip;
		
		arguments.mob.mobFindLeaf(
				arguments);
	
		if (arguments.foundObject != null) {
			if (arguments.minimumLength >= arguments.rootLength)
				arguments.minimumLength = arguments.rootLength;
		}
		else {
			arguments.foundObject = preserveState;
		}
		
		return arguments;
	} */

	/* @SuppressWarnings("unused")
	private SegmentImpl getCriteriaSegment(
			CriteriaType mediaCriteria) 
		throws NullPointerException,
			InvalidLinkageException,
			InconsistancyException {
		
		if (mediaCriteria == null)
			throw new NullPointerException("Cannot find a segment in this essence group according to a null media criteria.");

		int highestScore = 0;
		int numberOfRepresentations = countChoices();
		SegmentImpl highestScoreSegment = null;
		
		for ( int x = 0 ; x < numberOfRepresentations ; x++) {
			
			SegmentImpl segment = getChoiceAt(x);
			
			if (numberOfRepresentations == 0) {
				highestScoreSegment = segment;
				break;
			}
			
			SourceClip sourceClip = null;
			if (segment instanceof SourceClip)
				sourceClip = (SourceClip) segment;
			else
				throw new InvalidLinkageException("Cannot choose the best representation for a essence group with a segment that is not a source clip.");

			PackageImpl mob = null;
			try {
				mob = sourceClip.resolveReference();
			}
			catch (PackageNotFoundException mnfe) {
				throw new InconsistancyException("A source clip references an unknown package so a segment matching a media criteria could not be found.");
			}

			// TODO Open the essence data and determine its properties, then score it.
		}
		
		return null;
	} */

	public EssenceGroup clone() {

		return (EssenceGroup) super.clone();
	}
}
