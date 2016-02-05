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
 * $Log: Transition.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/02/08 11:27:17  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:44  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.misctype.PositionType;


/**
 * <p>Specifies that the two adjacent {@linkplain Segment Segments} should be overlapped when 
 * they are played and that the overlapped sections should be combined using the specified effect.
 * Transitions are used to specify {@linkplain Sequence sequences} according to the specified rules
 * for a sequence. The {@linkplain OperationGroup operation} that they specify must be appropriate
 * for use in a transition, which means that it has two inputs and a <em>level</em> parameter.</p>
 * 
 *
 *
 * @see Sequence
 * @see OperationDefinition
 * @see tv.amwa.maj.constant.OperationConstant
 */

public interface Transition 
	extends Component {

	/**
	 * <p>Returns the point at which a cut would be inserted if this transition
	 * were removed.</p>
	 * 
	 * @return Point at which a cut would be inserted if the transition
	 * were removed.
	 */
	public @PositionType long getCutPoint();

	/**
	 * <p>Sets the point at which a cut would be inserted if this transition
	 * were removed.</p>
	 * 
	 * @param cutPoint Point at which a cut would be inserted if the transition
	 * were removed.
	 */
	public void setCutPoint(
			@PositionType long cutPoint);

	/**
	 * <p>Returns the operation group defining the operation of this transition, which specifies 
	 * the effect to be applied during the transition.</p>
	 * 
	 * @return Operation group associated with the transition.
	 * 
	 * @see OperationGroup#isValidTransitionOperation()
	 * @see tv.amwa.maj.industry.TypeDefinitions#OperationGroupStrongReference
	 */
	public OperationGroup getTransitionOperation();

	/**
	 * <p>Sets the operation group defining the operation of this transition, which specifies 
	 * the effect to be applied during the transition.</p>
	 * 
	 * @param operationGroup Operation group associated with the transition.
	 * 
	 * @throws NullPointerException The given operation group is <code>null</code>.
	 * 
	 * @see OperationGroup#isValidTransitionOperation()
	 */
	public void setTransitionOperation(
			OperationGroup operationGroup) 
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this transition.</p>
	 *
	 * @return Cloned copy of this transition.
	 */
	public Transition clone();
}
