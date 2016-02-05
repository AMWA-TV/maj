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
 * $Log: Sequence.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.4  2008/02/08 11:27:18  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.3  2007/12/04 13:04:51  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.2  2007/12/04 09:37:03  vizigoth
 * Minor comment updates.
 *
 * Revision 1.1  2007/11/13 22:08:45  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

// TODO The methods here are inconsistent as to whether they enforce the
// rules of an AAF sequence. Should it be possible to create a sequence
// by appending or prepending transitions? Should you be able to remove
// components without breaking the video/audio relationships?

import java.util.List;

import tv.amwa.maj.exception.AdjacentTransitionException;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.EventSemanticsException;
import tv.amwa.maj.exception.InsufficientTransitionMaterialException;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.LeadingTransitionException;
import tv.amwa.maj.integer.UInt32;

/**
 * <p>Specifies the combination of an ordered list of {@linkplain Segment segments}
 * and {@linkplain Transition transitions}. A sequence is the mechanism for combining sections 
 * of essence to be played in a sequential manner. If a sequence object has one 
 * {@linkplain Segment segment} followed by another {@linkplain Segment segment}, after the 
 * first {@linkplain Segment segment} is played, the following one begins immediately.</p>
 * 
 * <p>To be considered legal, sequences must satisfy a number of rules. The MAJ API supports 
 * the construction of sequences from start to finish, allowing a sequence to temporarily exist in
 * an illegal state, for example with a trailing transition. The application must ensure that
 * the rules for sequences are maintained. These rules are repeated from the AAF specification
 * below:</p>
 * 
 * <ol>
 *  <li>The first and last {@linkplain Component components} in the ordered set shall be 
 *  {@linkplain Segment segments}.</li>
 *  <li>A {@linkplain Transition transition} shall only appear in a {@linkplain Sequence sequence}
 *  between two {@linkplain Segment segments}. The length of each of these {@linkplain Segment segments}
 *  shall be greater than or equal to the length of the {@linkplain Transition transition}.</li>
 *  <li>If a {@linkplain Segment segment} has a {@linkplain Transition transition} before it and after it, 
 *  the sum of the lengths of the surrounding {@linkplain Transition transitions} shall be less than or equal
 *  to the length of the {@linkplain Segment segment} that they surround.</li>
 *  <li>The length of the sequence shall be equal to the sum of the length of all 
 *  {@linkplain Segment segments} directly owned by the sequence minus the sum of the lengths of all 
 *  {@linkplain Transition transitions} directly owned by the sequence.</li>
 *  <li>The {@linkplain DataDefinition data definition} of each {@linkplain Component component} in the sequence
 *  shall be the same as the data definition of the sequence itself.</li>
 *  <li>If a sequence has a {@linkplain Transition transition}, the last section of the {@linkplain Segment segment}
 *  that precedes the {@linkplain Transition transition}, the {@linkplain Transition transition} itself, and the 
 *  first section of the {@linkplain Segment segment} that follows the {@linkplain Transition transition} are overlapped. 
 *  The duration of the {@linkplain Transition transition} determines the duration of the section of the preceding and 
 *  following {@linkplain Segment segments} that are overlapped.</li>
 * </ol>
 * 
 *
 * 
 * @see OperationGroup#isValidTransitionOperation()
 */

public interface Sequence
	extends Segment {
	
	/**
	 * <p>Appends the given {@linkplain Component component} to this sequence.  
	 * The length of the sequence is incremented by the size of the component
	 * or transition.</p>
	 * 
	 * <p>If the component is to be the first in the sequence and is a 
	 * {@linkplain Transition transition}, the method
	 * fails with a {@link LeadingTransitionException} as it is not
	 * acceptable to have a transition as the first element of a sequence.
	 * Note that this method supports the incremental creation of sequences from start to 
	 * finish and so could be used to create a sequence that ends in a transition, 
	 * which is an illegal state.</p>
	 * 
	 * <p>The method checks that the {@linkplain DataDefinition data definition}
	 * of the appended component is compatible with that of the sequence.</p>
	 * 
	 * @param component Component to append to this sequence.
	 * 
	 * @throws NullPointerException The given component is <code>null</code>.
	 * @throws InvalidDataDefinitionException The kind of data of the component
	 * is not compatible with the data definition of the sequence.
	 * @throws LeadingTransitionException A transition cannot be appended to a 
	 * sequence so as to become its first component.
	 * @throws EventSemanticsException The given component is an {@linkplain Event event} that 
	 * either: does not match the other kinds of event in the sequence; does not have the contextual 
	 * position property set; does not follow on from the previous event.
	 * @throws BadPropertyException The given non-event component does not have a
	 * length value set.
	 * @throws BadLengthException The length of the given components is negative.
	 * @throws AdjacentTransitionException Appending the given transition component would
	 * result in two adjacent transitions.
	 * @throws InsufficientTransitionMaterialException Appending the given component
	 * or transition will result in insufficient material for the last transition in
	 * the sequence.
	 * 
	 * @see Event
	 */
	public void appendComponentObject(
			Component component) 
		throws NullPointerException,
			InvalidDataDefinitionException,
			LeadingTransitionException,
			EventSemanticsException,
			BadPropertyException,
			BadLengthException,
			AdjacentTransitionException,
			InsufficientTransitionMaterialException;

	/**
	 * <p>Prepends the given {@linkplain Component component} to this sequence. 
	 * The length of the sequence is incremented by the size of the component.</p>  
	 * 
	 * <p>If the component is a {@linkplain Transition transition}, the method
	 * fails with a {@link LeadingTransitionException} as it is not
	 * acceptable to have a transition as the first element of a sequence.
	 * Use {@link #insertComponentObjectAt(int, Component)} instead.</p>
	 * 
	 * <p>The method checks that the {@link DataDefinition data definition}
	 * of the appended component is compatible with that of the sequence.</p>
	 * 
	 * @param component Component to prepend to this sequence.
	 * 
	 * @throws NullPointerException The given component is <code>null</code>.
	 * @throws InvalidDataDefinitionException The kind of data of the component
	 * is not compatible with the data definition of the sequence.
	 * @throws LeadingTransitionException A transition cannot be appended to a 
	 * sequence so as to become its first or last component.
	 */
	public void prependComponentObject(
			Component component) 
		throws NullPointerException,
			InvalidDataDefinitionException,
			LeadingTransitionException;

	/**
	 * <p>Insert the given component into this sequence at the given index. 
	 * The length of the sequence is incremented by the size of the component, unless the 
	 * component is a {@linkplain Transition transition}. Indices of components higher than
	 * the given index will be increased by one to accommodate the new component.</p>
	 * 
	 * <p>If the component is a {@linkplain Transition transition}, the method checks that it 
	 * is not the first object in the sequence, and that it is not neighbouring another transition.  
	 * It also verifies that there is enough source material on either side of the 
	 * transition. Note that this method supports the incremental creation of sequences from start to 
	 * finish and so could be used to create a sequence that ends in a transition, 
	 * which is an illegal state.</p>  
	 * 
	 * <p>The method checks that the {@link DataDefinition data definition}
	 * of the appended component is compatible with that of the sequence.</p>
	 * 
	 * @param index 0-based index to insert the new component at.
	 * @param component Component to insert into the sequence.
	 * 
	 * @throws NullPointerException The given component to insert is <code>null</code>.
	 * @throws IndexOutOfBoundsException The index is outside the acceptable
	 * range for the sequence.
	 * @throws InvalidDataDefinitionException The kind of data of the component
	 * is not compatible with the data definition of the sequence.
	 * @throws LeadingTransitionException A transition component cannot be
	 * inserted as first element of the sequence.
	 * @throws AdjacentTransitionException Inserting a transition and the 
	 * given index will result in two neighbouring transitions, which is not
	 * permitted.
	 * @throws InsufficientTransitionMaterialException Not enough source material
	 * exists either side of the given transition for the transition to work.
	 */
	public void insertComponentObjectAt(
			@UInt32 int index,
			Component component) 
		throws NullPointerException,
			IndexOutOfBoundsException,
			InvalidDataDefinitionException,
			LeadingTransitionException,
			AdjacentTransitionException,
			InsufficientTransitionMaterialException;

	/**
	 * <p>Returns the {@linkplain Component component} at the given index 
	 * through this sequence.</p>
	 * 
	 * @param index Index of component through this sequence
	 * @return Component at the given index of this sequence.
	 * 
	 * @throws IndexOutOfBoundsException The index is outside the acceptable
	 * range for this sequence.
	 */
	public Component getComponentObjectAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	// TODO Why doesn't this method support sequence validation?

	/**
	 * <p>Removes the {@linkplain Component component} at the given index from 
	 * this sequence. Components already existing at indices higher than the 
	 * given index will be moved to the next lower index to accommodate.</p>
	 * 
	 * <p>Note that removal of a component from a sequence may result in the 
	 * sequence being in an illegal state.</p> 
	 * 
	 * @param index Index of component to be removed.

	 * @throws IndexOutOfBoundsException The index is outside the acceptable
	 * range for the sequence.
	 */
	public void removeComponentObjectAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the number of components in this sequence.</p>
	 * 
	 * @return Number of components in this sequence.
	 */
	public @UInt32 int countComponentObjects();

	/**
	 * <p>Clear all component objects from the sequence.</p>
	 */
	public void clearComponentObjects();
	
	/**
	 * <p>Returns a list of all the components of this sequence.</p>
	 * 
	 * @return Shallow copy of the list containing all of the components of the 
	 * sequence.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#ComponentStrongReferenceVector
	 */
	public List<? extends Component> getComponentObjects();
	
	/**
	 * <p>Create a cloned copy of this sequence.</p>
	 *
	 * @return Cloned copy of this sequence.
	 */
	public Sequence clone();
}
