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
 * $Log: VaryingValueImpl.java,v $
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/25 14:18:28  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.3  2011/01/21 12:37:01  vizigoth
 * Final fixes to work with lenient initialization of properties.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
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
 * Revision 1.2  2007/12/04 13:04:47  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:18  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.exception.NotImplementedException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.model.ControlPoint;
import tv.amwa.maj.model.InterpolationDefinition;
import tv.amwa.maj.model.ParameterDefinition;
import tv.amwa.maj.model.VaryingValue;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.RationalImpl;

/** 
 * <p>Implements a changing data value for an effect control value.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3e00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "VaryingValue",
		  description = "The VaryingValue class specifies a changing data value for an effect control value.",
		  symbol = "VaryingValue")
public class VaryingValueImpl
	extends 
		ParameterImpl
	implements 
		VaryingValue,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 9169919354845468382L;

	private WeakReference<InterpolationDefinition> interpolation;
	private List<ControlPoint> pointList =
		Collections.synchronizedList(new Vector<ControlPoint>());
	
	public VaryingValueImpl() { }

	/**
	 * <p>Creates and initializes a new varying value object, which specifies a 
	 * changing data value for an effect control value. The value is identified with the
	 * given the given {@link ParameterDefinitionImpl parameter definition}, and requires an 
	 * {@link InterpolationDefinitionImpl interpolation definition}. Although the list of 
	 * control points should not be empty, varying values are used to create control point.
	 * The process of setting up a varying value is as follows:</p>
	 * 
	 * <ol>
	 *  <li>Create a varying value with its parameter and interpolation definition.</li>
	 *  <li>Use the varying value to create the control points for the varying value.</li>
	 *  <li>Add the control points to the varying value list using 
	 *  {@link #appendControlPoint(ControlPointImpl)}.</li>
	 * </ol>
	 * 
	 * @param parameterDefinition Parameter definition for the varying value, which
	 * determines the type of the varying value.
	 * @param interpolation Specifies the kind of interpolation to be used to 
	 * find the value between control points.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and
	 * all are required.
	 */
	public VaryingValueImpl(
			ParameterDefinition parameterDefinition,
			InterpolationDefinition interpolation) 
		throws NullPointerException {
		
		if (parameterDefinition == null)
			throw new NullPointerException("Cannot create a new varying value with a null parameter definition.");
		if (interpolation == null)
			throw new NullPointerException("Cannot create a new varying value with a null interpolation definition.");
		
		setParameterDefinition(parameterDefinition);
		setInterpolationDefinition(interpolation);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0606, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PointList",
			typeName = "ControlPointStrongReferenceVector",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4E02,
			symbol = "PointList")
	public List<ControlPoint> getControlPoints() {

		return StrongReferenceVector.getRequiredList(pointList);
	}

//	/**
//	 * <p></p>
//	 *
//	 * @param pointList
//	 * @throws NullPointerException
//	 * @throws IllegalArgumentException
//	 */
//	@MediaPropertySetter("PointList")
//	public void setControlPoints(
//			List<ControlPoint> pointList) 
//		throws NullPointerException,
//			IllegalArgumentException {
//		
//		if (pointList == null)
//			throw new NullPointerException("Cannot set the control points of this varying value using a null point list.");
//		if (pointList.size() == 0)
//			throw new IllegalArgumentException("Cannot set the control points of this varying value with an empty point list.");
//		
//		this.pointList.clear();
//		for ( ControlPoint point : pointList )
//			if (point != null)
//				StrongReferenceVector.append(pointList, point);
//	}

	public final List<ControlPoint> initializePointList() {
		
		List<ControlPoint> initialPoints = new ArrayList<ControlPoint>(1);
		setParameterDefinition(ParameterDefinitionImpl.forName("Level"));
		initialPoints.add(new ControlPointImpl(
				TypeDefinitions.Rational, new RationalImpl(0, 1), 
				TypeDefinitions.Rational.createValue("0/1")));
		return initialPoints;
	}
	
	@MediaListAppend("PointList")
	public void appendControlPoint(
			ControlPoint controlPoint)
		throws NullPointerException,
			IllegalArgumentException {

		if (controlPoint == null)
			throw new NullPointerException("Cannot add to the list of control points with a null value.");
		try {
			if (!(getTypeDefinition().equals(controlPoint.getTypeDefinition())))
				throw new IllegalArgumentException("Cannot add the given control point to the list of control points of this varying value as it is of the wrong type.");
		}
		catch (NullPointerException npe) { /* Assume still initializing ... so don't be picky */ }
			
		StrongReferenceVector.append(pointList, controlPoint);
	}

	@MediaPropertyCount("PointList")
	public int countControlPoints() {

		return pointList.size();
	}
	
	@MediaPropertyClear("PointList")
	public void clearControlPoints() {
		
		pointList = Collections.synchronizedList(new Vector<ControlPoint>());
	}

	@MediaListGetAt("PointList")
	public ControlPoint getControlPointAt(
			int index)
		throws IndexOutOfBoundsException {

		return StrongReferenceVector.getAt(pointList, index);
	}

	@MediaListRemoveAt("PointList")
	public void removeControlPointAt(
			int index)
		throws IndexOutOfBoundsException {

		StrongReferenceVector.remove(pointList, index);
	}

	public Object getInterpolatedValue(
			Rational inputValue)
		throws NullPointerException,
			NotImplementedException {
		
		// TODO Consider for MAJ post 1.0 
		// Note sure how to do this yet - involves plugins in the C reference implementation
		return null;
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0105, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Interpolation",
			typeName = "InterpolationDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4E01,
			symbol = "Interpolation")
	public InterpolationDefinition getInterpolationDefinition() {

		return interpolation.getTarget();
	}

	/**
	 * <p></p>
	 *
	 * @param interpolation
	 * @throws NullPointerException
	 */
	@MediaPropertySetter("Interpolation")
	public void setInterpolationDefinition(
			InterpolationDefinition interpolation) 
		throws NullPointerException {
		
		if (interpolation == null)
			throw new NullPointerException("Cannot set the interpolation definition of this varying value with a null value.");
		
		this.interpolation = new WeakReference<InterpolationDefinition>(interpolation);
	}

	public final static InterpolationDefinition initializeInterpolation() {
		
		return InterpolationDefinitionImpl.forName("None");
	}
	
	@Override
	public VaryingValue clone() {
		
		return (VaryingValue) super.clone();
	}
}
