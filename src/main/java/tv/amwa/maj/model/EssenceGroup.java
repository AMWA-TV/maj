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
 * $Log: EssenceGroup.java,v $
 * Revision 1.4  2011/10/05 17:14:27  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/27 11:07:29  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:52  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;

// TODO find out how selection by media criteria works in the existing API

/**
 * <p>Specifies the description of multiple digital representations of the same original 
 * content source. The essence type and length of all choices must be the same.</p>
 * 
 *
 * 
 */
public interface EssenceGroup 
	extends Segment {

	/**
	 * <p>Sets the still frame property of this essence group, which identifies the essence 
	 * for a single-frame image representation of the group. Call this method with <code>null</code>
	 * to omit this optional property.</p>
	 * 
	 * @param stillFrame Still frame representation for the essence group.
	 * 
	 * @throws InvalidDataDefinitionException The given source clip does not have a compatible data definition that
	 * can convert to the data definition of this essence group.
	 * @throws BadLengthException The given source reference segment does not represent a still frame with a 
	 * length of exactly&nbsp;1.
	 */
	public void setStillFrame(
			SourceReferenceSegment stillFrame) 
		throws InvalidDataDefinitionException,
			BadLengthException;

	/**
	 * <p>Gets the still frame property of this essence group, which identifies the essence 
	 * for a single-frame image representation of the group. This is an optional property.</p>
	 * 
	 * @return Still frame representation of this essence group.
	 * 
	 * @throws PropertyNotPresentException The still frame property is not present within this
	 * essence group.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#SourceReferenceStrongReference
	 */
	public SourceReferenceSegment getStillFrame()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the list of segment choices of this essence group, which 
	 * identify the alternate representations that may be chosen. The order of the items in the 
	 * collection is not necessarily meaningful.</p>
	 * 
	 * @return List of segment choices of this essence group.
	 * 
	 * @see #getChoiceAt(int)
	 */
	public List<Segment> getChoices();

	/**
	 * <p>Append a choice to the collection of segment choices of this essence group, which 
	 * identify the alternate representations that may be chosen. The order of the items in the 
	 * collection is not necessarily meaningful.</p>
	 * 
	 * <p>The segment shall either be a {@linkplain SourceClip source clip} or a {@linkplain Sequence sequence}. 
	 * If the segment is a sequence, it shall contain only source clip and {@linkplain Filler filler} objects.</p>
	 * 
	 * @param choice Segment to add in as a choice to the collection of choices of this essence group.
	 * 
	 * @throws NullPointerException The given segment choice is <code>null</code>.
	 * @throws InvalidDataDefinitionException The data definition of the given segment does not convert to
	 * an acceptable definition for this group.
	 * @throws BadLengthException The length of the given segment is different to the length
	 * of other choices of this group.
	 */
	public void appendChoice(
			Segment choice) 
		throws NullPointerException,
			InvalidDataDefinitionException,
			BadLengthException;

	/**
	 * <p>Prepend a choice to the collection of segment choices of this essence group, which
	 * identify the alternate representations that may be chosen. The order of the items in the 
	 * collection is not necessarily meaningful.</p>
	 * 
	 * <p>The segment shall either be a {@linkplain SourceClip source clip} or a {@linkplain Sequence sequence}. 
	 * If the segment is a sequence, it shall contain only source clip and {@linkplain Filler filler} objects.</p>
	 * 
	 * @param choice Segment to add as a choice to the collection of choices of this essence group.
	 * 
	 * @throws NullPointerException The given choice segment is <code>null</code>.
	 * @throws InvalidDataDefinitionException The data definition of the given segment does not convert to
	 * an acceptable definition for this group.
	 * @throws BadLengthException The length of the given segment is different to the length
	 * of other choices of the group.
	 */
	public void prependChoice(
			Segment choice) 
		throws NullPointerException,
			InvalidDataDefinitionException,
			BadLengthException;

	/**
	 * <p>Insert another choice to the collections of choices of this essence group at the given
	 * index. The choices identify the alternate representations that may be chosen. Choices already 
	 * existing at the given and higher indices will be moved up by one index to accommodate.</p>
	 * 
	 * <p>The segment shall either be a {@linkplain SourceClip source clip} or a {@linkplain Sequence sequence}. 
	 * If the segment is a sequence, it shall contain only source clip and {@linkplain Filler filler} objects.</p>
	 * 
	 * @param index Index at which to insert a new choice into the collection of choices of this essence group.
	 * @param choice Segment to add as a choice.
	 * 
	 * @throws NullPointerException The given choice segment is <code>null</code>.
	 * @throws IndexOutOfBoundsException Index is outside the
	 * acceptable range for this group.
	 * @throws InvalidDataDefinitionException The data definition of the given segment does not convert to
	 * an acceptable definition for this group.
	 * @throws BadLengthException The length of the given segment is different to the length
	 * for other choices of the group.
	 */
	public void insertChoiceAt(
			@UInt32 int index,
			Segment choice) 
		throws NullPointerException,
			IndexOutOfBoundsException,
			InvalidDataDefinitionException,
			BadLengthException;

	/**
	 * <p>Returns the number of choices in the collection of alternates of this essence group,  which
	 * identify the alternate representations that may be chosen. The count does not
	 * include the still frame.</p>
	 * 
	 * @return Number of choices in this essence group
	 */
	public @UInt32 int countChoices();
	
//	/**
//	 * <p>Clear the list of choices for this essence group. As the choices property is a required
//	 * property, a new choice should be inserted into the list soon after calling this method.</p>
//	 */
    // Don't really want to remove choices
	//	public void clearChoices();
	
	/**
	 * <p>Returns the source clip choice at the given index in the collection of choices of this
	 * essence group, which identify the alternate representations that may be chosen.</p>
	 *  
	 * @param index The 0-based index into the collection of choices of this essence group.
	 * @return The corresponding choice at the given index.
	 * 
	 * @throws IndexOutOfBoundsException Index is outside the acceptable range for the collection
	 * of this essence group.
	 */
	public Segment getChoiceAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Removes the choice at the given index in the collection of choices of this essence group,
	 * which identify the alternate representations that may be chosen.  Items already existing at
	 * higher indices will be moved down by one index to accommodate. The essence group should always
	 * contain at least one choice, although the implementation may not check this.</p>
	 * 
	 * @param index The 0-based index into the collection of choices indicating which 
	 * choice to remove.
	 * 
	 * @throws IndexOutOfBoundsException Index is outside the acceptable 
	 * range for this group.
	 */
	public void removeChoiceAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;
	
	/**
	 * <p>Create a cloned copy of this essence group.</p>
	 *
	 * @return Cloned copy of this essence group.
	 */
	public EssenceGroup clone();
}
