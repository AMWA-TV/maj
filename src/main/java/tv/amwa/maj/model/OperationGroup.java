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
 * $Log: OperationGroup.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/02/08 11:27:23  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.3  2007/12/04 13:04:51  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.2  2007/12/04 09:31:03  vizigoth
 * Fix to replace MissingParameterException with ParameterNotFound exception.
 *
 * Revision 1.1  2007/11/13 22:08:53  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.exception.DuplicateParameterException;
import tv.amwa.maj.exception.ParameterNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.ArgIDType;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.record.AUID;

/**
 * <p>Specifies a container with an ordered set of {@linkplain Segment segments} and an 
 * {@linkplain OperationDefinition operation} that is performed on these segments.</p>
 * 
 * <p>In an operation group that is itself in a {@linkplain Transition transition}, the input segments 
 * are provided by the transition and so the optional input segments property shall be omitted.</p>
 * 
 *
 * 
 * @see OperationDefinition
 * @see CompositionPackage
 * @see Transition#getTransitionOperation()
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationGroupStrongReference
 */

public interface OperationGroup 
	extends Segment {

	/**
	 * <p>Returns the operation definition for this operation group, which identifies the
	 * kind of operation.</p>
	 * 
	 * @return Operation definition for this operation group.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionWeakReference
	 */
	public OperationDefinition getOperationDefinition();

	/**
	 * <p>Sets the operation definition for the operation group, which identifies the
	 * kind of operation.</p>
	 * 
	 * @param operationDefinition Operation definition for the operation group. 

	 * @throws NullPointerException The given operation definition is <code>null</code>.
	 */
	public void setOperationDefinition(
			OperationDefinition operationDefinition) 
		throws NullPointerException;

	/**
	 * <p>Returns the {@linkplain Segment segment} that represents the optional 
	 * rendering of this operation group, which specifies a rendered or precomputed version 
	 * of the operation. Working and final renderings are handled by using an 
	 * {@linkplain EssenceGroup essence group} as the segment.</p> 
	 * 
	 * @return Segment that represents the optional rendering of this operation 
	 * group.
	 * 
	 * @throws PropertyNotPresentException The optional rendering property is not present for 
	 * this operation group.
	 * 
	 * @see SourceClip
	 * @see tv.amwa.maj.industry.TypeDefinitions#SourceReferenceStrongReference
	 */
	public SourceReferenceSegment getRendering()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the {@linkplain Segment segment} that represents the optional 
	 * rendering of this operation group, which specifies a rendered or precomputed version 
	 * of the operation. Multiple renderings may exist if the source clip refers to a 
	 * {@linkplain MaterialPackage material package} that contains an {@linkplain EssenceGroup essence 
	 * group}.</p>
	 * 
	 * @param sourceReference Segment containing a representation of the rendering.
	 * 
	 * @see SourceClip
	 */
	public void setRendering(
			SourceReferenceSegment sourceReference);

	/**
	 * <p>Returns the optional bypass override index of this operation group,
	 * which specifies the array index (1-based) of the input segment which is 
	 * the primary input. This overrides any bypass specified by the 
	 * {@linkplain #getOperationDefinition() operation definition}. This is
	 * an optional property.</p>
	 * 
	 * @return Optional bypass override index of the operation group.
	 * 
	 * @throws PropertyNotPresentException The optional bypass override property
	 * is not set for the operation group.
	 * 
	 * @see OperationDefinition#getBypass()
	 * @see #getOperationDefinition()
	 */
	public @UInt32 int getBypassOverride()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the optional bypass override property on the
	 * given operation group object, which specifies the array index (1-based) of the 
	 * input segment which is the primary input. This overrides any bypass specified 
	 * by the {@linkplain OperationDefinition operation definition}. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 * 
	 * @param bypassOverride Bypass override property of this operation group.
	 * 
	 * @throws IllegalArgumentException The bypass override property must be a 1-based
	 * index value.
	 */
	public void setBypassOverride(
			@UInt32 Integer bypassOverride)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of {@linkplain Parameter parameters} in the collection of parameters of this 
	 * operation group, which specify control values for the operation. This is an optional
	 * property and this method returns&nbsp;0 when the property is not present.</p>
	 * 
	 * @return Number of parameters in the collection of parameters of this operation group.
	 */
	public @UInt32 int countParameters(); 

	/**
	 * <p>Clears the list of {@linkplain Parameter parameters} in the list of parameters of this
	 * operation group, which specify control values for the operation. This has the effect of 
	 * omitting this optional property.</p>
	 */
	public void clearParameters();
	
	/**
	 * <p>Adds a new {@linkplain Parameter parameter} to the collection of parameters of 
	 * this operation group, which specify control values for the operation. This is an 
	 * optional property that becomes present when this method is called.</p> 
	 * 
	 * @param parameter Parameter to add to the collection of parameters of this operation group.
	 * 
	 * @throws DuplicateParameterException The given parameter has the same identifier as one
	 * that is already present in this operation group.
	 * @throws NullPointerException The given parameter is <code>null</code>.
	 */
	public void addParameter(
			Parameter parameter) 
		throws DuplicateParameterException,
			NullPointerException;

	/**
	 * <p>Returns the list of parameters of this operation group, which specify control values 
	 * for the operation. This is an optional property that returns an empty list when not
	 * present.</p>
	 * 
	 * @return Shallow copy of the collection of parameters of this operation group.
	 * 
	 * @throws PropertyNotPresentException No parameters are present in this operation group.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterStrongReferenceVector
	 */
	public List<? extends Parameter> getParameters()
		throws PropertyNotPresentException;

	/**
	 * <p>Given an argument id, returns the corresponding parameter slot from the 
	 * collection of parameters of this operation group.</p>
	 * 
	 * @param argumentId Argument id to look up in the collection of parameters of
	 * this operation group.
	 * @return Matching parameter from the operation group.
	 * 
	 * @throws ParameterNotFoundException The given argument identifier does not match that
	 * of a parameter in the set of parameters for this operation group.
	 * @throws NullPointerException The given argument identifier is <code>null</code>.
	 */
	public Parameter lookupParameter(
			@ArgIDType AUID argumentId) 
		throws ParameterNotFoundException,
			NullPointerException;

	/** 
	 * <p>Returns the number of media sources for this operation group, which is 
	 * the size of the list of input segments for this operation.
	 * The input segments list is an optional property and&nbsp;0 will be returned if the
	 * property is omitted.</p>
	 * 
	 * @return Number of media sources for this operation group.
	 */
	public @UInt32 int countInputSegments();

	/**
	 * <p>Clear the list of input segments for this operation group, omitting this 
	 * optional property.</p>
	 */
	public void clearInputSegments();
	
	/**
	 * <p>Returns the list of input segments of this operation group. This is an
	 * optional property.</p>
	 * 
	 * @return List of input segments of this operation group.
	 * 
	 * @throws PropertyNotPresentException The list of input segments is not present 
	 * for this operation group.
	 */
	public List<Segment> getInputSegments() 
		throws PropertyNotPresentException;
	
	/**
	 * <p>Appends an input segment to the list of input segments of 
	 * this operation group. The list is an optional
	 * property that will become present if a segment is appended.</p>
	 * 
	 * @param segment Segment to append to the list of input segments of this
	 * operation group.
	 * 
	 * @throws NullPointerException The given segment to append is <code>null</code>.
	 */
	public void appendInputSegment(
			Segment segment) throws NullPointerException;

	/**
	 * <p>Prepends an input segment to the list of input segments of this 
	 * operation group. The list is an optional
	 * property that will become present if a segment is prepended.</p>
	 * 
	 * @param segment Input segment to add to the beginning of the list of
	 * input segments.
	 * 
	 * @throws NullPointerException The given segment to prepend is <code>null</code>.
	 */
	public void prependInputSegment(
			Segment segment) 
		throws NullPointerException;

	/**
	 * <p>Inserts an input segment into the list of input segments of this 
	 * operation group at the given index. The existing segments at the given 
	 * and higher indices are moved up by one to make room. This is an optional
	 * property that will become present if a segment is inserted at
	 * index&nbsp;0.</p>
	 * 
	 * @param index 0-based index where input segment is to be inserted.
	 * @param segment Input segment to insert at the given index.
	 * 
	 * @throws IndexOutOfBoundsException The index value is outside the acceptable
	 * range for the list of input segments.
	 * @throws NullPointerException The given segment to insert is <code>null</code>.
	 */
	public void insertInputSegmentAt(
			@UInt32 int index,
			Segment segment) 
		throws IndexOutOfBoundsException,
			NullPointerException;

	/**
	 * <p>Given an index, returns the corresponding input segment from this
	 * list of input segments of this operation group. This is an optional property and this
	 * method will always throw an {@link IndexOutOfBoundsException}
	 * if the property is omitted.</p>
	 * 
	 * @param index 0-based index into the list of input segments of
	 * this operation group.
	 * @return Input segment at the given index.
	 * 
	 * @throws IndexOutOfBoundsException The index is outside the acceptable
	 * range for the list of input segments.
	 */
	public Segment getInputSegmentAt(
			@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Removes the segment at the given index from the list of
	 * input segments of the operation group. Existing segments at
	 * higher indices are moved down by one to accommodate. This is an 
	 * optional property and this method will always throw an {@link IndexOutOfBoundsException}
	 * if the property is omitted. Removing the last input segment from
	 * the list will omit this property.</p>
	 * 
	 * @param index 0-based index into the list of input segments of
	 * this operation group.
	 * 
	 * @throws IndexOutOfBoundsException The index is outside the 
	 * acceptable range for the list of input segments.
	 */
	public void removeInputSegmentAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns <code>true</code> if this operation group can be used in a
	 * {@linkplain Transition transition}; otherwise returns <code>false</code>. An operation group 
	 * can be used in a transition if its operation has exactly&nbsp;2 inputs and
	 * the operation has a <em>level</em> parameter.</p>
	 * 
	 * @return Can the operation group be used in a {@linkplain Transition transition}?
	 * 
	 * @see Transition#setTransitionOperation(OperationGroup)
	 */
	public @Bool boolean isValidTransitionOperation();

	/**
	 * <p>Returns <code>true</code> if an operation group represents a 
	 * time warping effect.</p>
	 * 
	 * @return Does this operation group have an operation that warps
	 * time?
	 * 
	 * @see #getOperationDefinition()
	 * @see OperationDefinition#isTimeWarp()
	 */
	public @Bool boolean isTimeWarp();

	/**
	 * <p>Create a cloned copy of this operation group.</p>
	 *
	 * @return Cloned copy of this operation group.
	 */
	public OperationGroup clone();
}


