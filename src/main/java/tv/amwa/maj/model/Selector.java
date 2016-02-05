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
 * $Log: Selector.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/08 11:27:22  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2007/12/04 13:04:52  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:39  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.SegmentNotFoundException;
import tv.amwa.maj.integer.Int32;


/**
 * <p>Specifies a selected value of a single {@linkplain Segment segment} while preserving 
 * references to unused alternatives. A selector represents an editing decision, which is in contrast 
 * with an {@linkplain EssenceGroup essence group} that presents a group of alternative versions 
 * of the same essence that the application can choose from, based on the most appropriate or efficient 
 * essence format among the alternatives.</p>
 * 
 * <p>The selected item if provided when the selector is created and by calling {@link #setSelectedSegment(Segment)}.
 * Note that the collection of alternates does not contain the selected item. When the selected segment
 * is changed, the segment that has been replaced is automatically added to the collection of alternates.</p>
 * 
 *
 *
 * @see EssenceGroup
 * @see tv.amwa.maj.industry.TypeDefinitions#SegmentStrongReference
 */

public interface Selector
	extends Segment {

	/**
	 * <p>Returns the selected segment of this selector.</p>
	 * 
	 * @return Selected segment of this selector.
	 */
	public Segment getSelectedSegment();

	/**
	 * <p>Sets the selected segment of this selector. If the selected 
	 * segment currently exists in the set of alternatives, the selected
	 * segment is removed from the set. The segment being replaced is appended
	 * to the set of alternatives.</p>
	 * 
	 * @param selectedSegment Selected segment of this selector.
	 * 
	 * @throws NullPointerException The given selected segment is <code>null</code>.
	 * @throws BadLengthException The given segment does not have a length that matches the
	 * length of this selector.
	 * @throws InvalidDataDefinitionException The given segment does not have a data definition that
	 * is compatible with the essence type of this selector.
	 */
	public void setSelectedSegment(
			Segment selectedSegment) 
		throws NullPointerException,
			BadLengthException,
			InvalidDataDefinitionException;

	/**
	 * <p>Append the given segment to collection of alternative segments of this
	 * selector. This set represents unused segment alternatives. If currently omitted,
	 * the alternates optional property is made present by this method.</p>
	 *  
	 * @param segment Segment to append to the collection of alternates of this
	 * selector.
	 *  
	 * @throws NullPointerException The given alternate segment is <code>null</code>.
	 * @throws BadLengthException The given segment does not have a length that matches the length
	 * of this selector.
	 * @throws InvalidDataDefinitionException The given segment does not have a data definition that
	 * is compatible with the essence type of this selector.
	 */
	public void appendAlternateSegment(
			Segment segment) 
		throws NullPointerException,
			BadLengthException,
			InvalidDataDefinitionException;

	/**
	 * <p>Returns the number of segments in the collection of alternates of this 
	 * selector. If the alternates property is omitted, this method returns&nbsp;0.</p>
	 * 
	 * @return Number of segments in the collection of alternates of this
	 * selector.
	 */
	public @Int32 int countAlternateSegments();

	/**
	 * <p>Returns a collection of all the segments of the collection of alternates of this selector.
	 * If the alternates property is omitted, this method returns an empty list. This is an 
	 * optional property.</p>
	 * 
	 * @return Shallow copy of the collection of alternates of this selector.
	 * 
	 * @throws PropertyNotPresentException No alternates are present for this selector.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#SegmentStrongReferenceVector
	 */
	public List<? extends Segment> getAlternateSegments()
		throws PropertyNotPresentException;

	/**
	 * <p>Removes the given segment from the collection of alternates for this selector.</p>
	 * 
	 * @param segment Segment to remove from the collection of alternates of this selector.
	 * 
	 * @throws NullPointerException The given segment to remove from the list
	 * of alternates is <code>null</code>.
	 * @throws SegmentNotFoundException The given segment is not
	 * contained in the list of alternates for this selector.
	 */
	public void removeAlternateSegment(
			Segment segment) 
		throws NullPointerException,
			SegmentNotFoundException;
	
	/**
	 * <p>Create a cloned copy of this selector.</p>
	 *
	 * @return Cloned copy of this selector.
	 */
	public Selector clone();
}

