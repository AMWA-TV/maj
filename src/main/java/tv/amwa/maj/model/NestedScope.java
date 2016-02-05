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
 * $Log: NestedScope.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/08 11:27:17  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2007/12/04 13:04:51  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:24  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.integer.UInt32;

/**
 * <p>Specifies a scope and has an ordered set of {@linkplain Segment segments}. 
 * Typically, nested scopes are used within {@linkplain CompositionPackage composition packages}.</p>
 * 
 * <p>The {@linkplain Component#getComponentLength() length}  of each segment contained in the list of segments 
 * must be the same as the length of the nested scope itself.</p>
 * 
 * <p>The value represented by a nested scope is the value specified by the last 
 * segment in the list of segments it contains, which must have a compatible 
 * {@linkplain Component#getComponentDataDefinition() essence type} to that of the nested scope. 
 * Nested scopes are used to encapsulate intermediate results which may be referenced from 
 * more than one place, in a similar way to subexpressions within mathematical 
 * expressions.</p>
 * 
 *
 * 
 * @see ScopeReference
 * @see CompositionPackage
 * @see OperationGroup
 */

public interface NestedScope 
	extends Segment {

	/**
	 * <p>Append an input segment to the list of segments of this nested scope. The
	 * last segment added will be used as the output value of the nested
	 * scope. Typically, the given segment contains operations whose inputs are 
	 * {@linkplain ScopeReference scope references}.</p>
	 * 
	 * @param segment Input segment to append to the list of segments in this nested scope.
	 * 
	 * @throws NullPointerException The given segment is <code>null</code>.
	 * @throws InvalidDataDefinitionException The given segment will become the value of this nested scope
	 * has an incompatible data definition.
	 * @throws BadLengthException The given segment has a different length from that of this nested scope.
	 */
	public void appendSegment(
			Segment segment) 
		throws NullPointerException,
			InvalidDataDefinitionException,
			BadLengthException;

	/**
	 * <p>Prepend an input segment to the list of segments of this nested scope.</p>
	 * 
	 * @param segment Segment to add at the beginning of the list.
	 * 
 	 * @throws NullPointerException The given segment is <code>null</code>.
	 * @throws BadLengthException The given segment has a different length from that of 
	 * this nested scope.
 	 */
	public void prependSegment(
			Segment segment) 
		throws NullPointerException,
			BadLengthException;

	/**
	 * <p>Insert an input segment into the list of segments of this nested scope at the
	 * given index. Segments already existing at the given and higher
	 * indices will be moved up by one index to accommodate.</p>
	 * 
	 * @param index Index at which the segment is to be inserted into the
	 * list of segments of this nested scope.
	 * @param segment Segment to be inserted into the list.
	 * 
	 * @throws NullPointerException The given segment is <code>null</code>.
	 * @throws IndexOutOfBoundsException Index is outside the range
	 * of the indices of the current list.
	 * @throws InvalidDataDefinitionException The given segment will become the value of this nested scope
	 * and has an incompatible data definition.
	 * @throws BadLengthException The given segment has a different length from that of this nested scope.
	 */
	public void insertSegmentAt(
			@UInt32 int index,
			Segment segment) 
		throws NullPointerException,
			IndexOutOfBoundsException,
			InvalidDataDefinitionException,
			BadLengthException;

	/**
	 * <p>Removes the segment at the given index in the list of segments of this
	 * nested scope.  Segments already existing at indices greater than the given index will be 
	 * moved down by one index to accommodate.</p>
	 * 
	 * @param index Index corresponding to segment to be removed from the list of segments
	 * of this nested scope. 
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for the current 
	 * list of segments.
	 * @throws InvalidDataDefinitionException The removal operation for the given index will result in the
	 * value of the nested scope having an incompatible data definition to the nested scope itself.
	 * @throws IllegalArgumentException Removal of a segment will leave an empty list.
	 */
	public void removeSegmentAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException,
			InvalidDataDefinitionException,
			IllegalArgumentException;

	/**
	 * <p>Returns the number of segments in the list of segments of this nested scope.</p>
	 * 
	 * @return Number of segments in the list of segments of this nested scope.
	 */
	public @UInt32 int countSegments();

	/**
	 * <p>Returns the segment at the given index through the list of segments in this nested
	 * scope.</p>
	 * 
	 * @param index 0-based index of the segment to return.
	 * @return Segment at the given index.
	 * 
	 * @throws IndexOutOfBoundsException Index is outside the range acceptable range
	 * for the current list of segments of this nested scope.
	 */
	public Segment getSegmentAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the list of {@linkplain Segment segments} that make up this
	 * nested scope.</p>
	 * 
	 * @return Shallow copy of the list of segments of this nested scope.
	 */
	public List<? extends Segment> getNestedScopeTracks();
	
	/**
	 * <p>Create a cloned copy of this nested scope.</p>
	 *
	 * @return Cloned copy of this nested scope.
	 */
	public NestedScope clone();
}
