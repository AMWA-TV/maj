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
 * $Log: VaryingValue.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/02/08 11:27:25  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2007/12/04 13:04:52  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:34  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.record.Rational;

/**
 * <p>Specifies a changing data value for an effect control value.</p>
 * 
 *
 *
 * @see OperationGroup#addParameter(Parameter)
 */

public interface VaryingValue 
	extends Parameter {

	/**
	 * <p>Adds a {@linkplain ControlPoint control point} to the  
	 * sequence of control points of this varying value, each of which specifies a value 
	 * and a time point at which the value is defined. The point will be 
	 * sorted by time order, not the order that the points were added in.</p>
	 * 
	 * @param controlPoint Control point to add to the sequence of control
	 * points.
	 * 
	 * @throws NullPointerException The given control point is <code>null</code>.
	 */
	public void appendControlPoint(
			ControlPoint controlPoint) 
		throws NullPointerException;

	/**
	 * <p>Returns the sequence of {@linkplain ControlPoint 
	 * control points} of this varying value, each of which specifies a value 
	 * and a time point at which the value is defined.</p>
	 * 
	 * @return Shallow copy of the sequence of control points of this
	 * varying value.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#ControlPointStrongReferenceVector
	 */
	public List<? extends ControlPoint> getControlPoints();

	/**
	 * <p>Returns the number of {@linkplain ControlPoint control points} in 
	 * the sequence of control points of this varying value, each of which specifies a 
	 * value and a time point at which the value is defined.</p>
	 * 
	 * @return Number of control points in the sequence of control 
	 * points of the varying value.
	 */
	public @UInt32 int countControlPoints();

	/**
	 * <p>Retrieves the input {@linkplain ControlPoint control point}
	 * at the given index in the sequence of control points of this varying 
	 * value, each of which specifies a value and a time point at which the value 
	 * is defined.</p>
	 * 
	 * @param index 0-based index of the control point to retrieve.
	 * 
	 * @return Input control point at the given index in the sequence 
	 * of control points of this varying value.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the 
	 * acceptable range for the current sequence of control points of this
	 * varying value.
	 */
	public ControlPoint getControlPointAt(
			@UInt32 int index) 
		 throws IndexOutOfBoundsException;

	/**
	 * <p>This function removes the input {@linkplain ControlPoint control point}
	 * at the given index in the sequence of control points of this varying
	 * value, each of which specifies a value 
	 * and a time point at which the value is defined. Control points already 
	 * existing at indices higher than the 
	 * given index will be moved to the next lower index to accommodate.</p>
	 * 
	 * @param index Index of control point to remove from the sequence.
	 * 
	 * @throws IndexOutOfBoundsException The index is outside acceptable
	 * range for the current sequence of control points of this varying
	 * value.
	 */
	public void removeControlPointAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the {@linkplain InterpolationDefinition interpolation} property of
	 * this varying value, which specifies the kind of interpolation to be used 
	 * to find the value between {@linkplain ControlPoint control points}.</p>
	 * 
	 * @return Interpolation definition referenced by this varying value.
	 * 
	 * @see #getInterpolatedValue(Rational)
	 * @see tv.amwa.maj.constant.InterpolationConstant
	 * @see tv.amwa.maj.industry.TypeDefinitions#InterpolationDefinitionWeakReference
	 */
	public InterpolationDefinition getInterpolationDefinition();

	/**
	 * <p>Returns the interpolated value of the 
	 * varying value at a given time position, which should be a value
	 * between&nbsp;0 and&nbsp;1 inclusive. The {@linkplain TypeDefinition data type} 
	 * of the value contained in the value returned will be the same as the data
	 * type of the {@linkplain Parameter parameter} this varying value
	 * provides a value for, which is the same as the data type of
	 * all of the {@linkplain ControlPoint control points}.</p>
	 *   
	 * @param inputValue Time position along the operation group to find the
	 * interpolated value for.
	 * 
	 * @return Buffer containing the interpolated value of the 
	 * varying value at a given position.
	 * 
	 * @throws NullPointerException The given input value is <code>null</code>.
	 * 
	 * @see #getInterpolationDefinition()
	 * @see tv.amwa.maj.constant.InterpolationConstant
	 * @see Parameter#getTypeDefinition()
	 * @see ControlPoint#getTypeDefinition()
	 * @see TypeDefinition#createValue(Object)
	 * @see PropertyValue#getValue()
	 */
	public Object getInterpolatedValue(
			Rational inputValue) 
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this varying value.</p>
	 *
	 * @return Cloned copy of this varying value.
	 */
	public VaryingValue clone();
}
