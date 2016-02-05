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
 * $Log: ControlPoint.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/01/27 11:07:34  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:31  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.EditHintType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.RationalRangeException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionIndirect;
import tv.amwa.maj.record.Rational;


/**
 * <p>Specifies a value and a time point (position) that is used to specify an effect control value. 
 * Control points must be added to a {@linkplain VaryingValue varying value} that is then added 
 * to the {@linkplain OperationGroup operation group} describing the effect.</p>
 * 
 * <p>For {@linkplain Parameter parameters} that are a constant value for an operation group, 
 * use a {@linkplain ConstantValue constant value}.</p>
 * 
 *
 * 
 * @see VaryingValue#getControlPoints()
 * @see tv.amwa.maj.industry.TypeDefinitions#ControlPointStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ControlPointStrongReferenceVector
 */

public interface ControlPoint 
	extends InterchangeObject {

	/**
	 * <p>Returns the time property of this control point, which specifies the time within the 
	 * {@linkplain VaryingValue varying value} segment for which the value is defined. The value
	 * of the time property should be between&nbps;0 and&nbsp;1 inclusive.</p>
	 * 
	 * @return Time property of this control point.
	 */ 
	
	public Rational getTime();

	/**
	 * <p>Returns the edit hint of the control point, which describes how to alter the
	 * position (time) of the {@linkplain OperationGroup operation group} is made longer 
	 * or shorter. This is an optional property.</p>
	 * 
	 * @return A hint to be used if the effect starting time or length is changed during 
	 * editing.
	 * 
	 * @throws PropertyNotPresentException The optional edit hint property is not present
	 * for this control point.
	 */
	public EditHintType getEditHint()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the control point value at the control point's position (time), which
	 * contains the type and value as a {@linkplain PropertyValue property value}.</p>
	 * 
	 * @return Control point value.
	 * 
	 * @see TypeDefinitionIndirect
	 * @see #getTypeDefinition()
	 * @see PropertyValue#getType()
	 * @see PropertyValue#getValue()
	 */
	public PropertyValue getControlPointValue();

	/**
	 * <p>Sets the position of the control point within an operation group, which specifies the time within the 
	 * {@linkplain VaryingValue varying value} segment for which the value is defined. The value is expressed as a 
	 * {@linkplain tv.amwa.maj.record.Rational rational value} between from&nbsp;0 to&nbsp;1.</p>
	 * 
	 * @param controlPointTime Control point time.
	 * 
	 * @throws RationalRangeException The rational value is outside the range&nbsp;0 to&nbsp;1.
	 * @throws NullPointerException The given time value is <code>null</code>.
	 */
	public void setControlPointTime(
			Rational controlPointTime) 
		throws RationalRangeException,
			NullPointerException;

	/**
	 * <p>Sets the control point edit hint value, which describes how to alter the
	 * position (time) if the {@linkplain OperationGroup operation group} is made longer or shorter. 
	 * Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param editHint Specifies a hint to be used if the effect starting time or 
	 * length is changed during editing.
	 */
	public void setEditHint(
			EditHintType editHint);

	/**
	 * <p>Returns the type definition of the value of this control point.</p>
	 * 
	 * @return Type definition associated with the control point data value inside this 
	 * object.
	 * 
	 * @see TypeDefinitionIndirect
	 */
	public TypeDefinition getTypeDefinition();

	/**
	 * <p>Set the data value describing this control point at the control point's position (time).
	 * The value must be a {@linkplain PropertyValue property value} containing the type
	 * and current value for the control point.</p>
	 * 
	 * <p>To create a control value of a specific type, use the 
	 * {@link TypeDefinition#createValue(Object) createValue()} method of a 
	 * {@link TypeDefinition type definition}. To access a type definition, use
	 * the {@link #getTypeDefinition()} method as follows:</p>
	 * 
	 * <pre>
	 *      controlPoint.setControlPointValue(
	 *          controlPoint.getTypeDefinition().createValue(42));
	 * </pre>
	 * 
	 * <p>Alternatively, you can retrieve the value of any other property 
	 * using its {@linkplain PropertyDefinition property definition}. To retrieve the 
	 * property value of a property called <em>foo</em> from an instance called <em>bar</em>:</p>
	 *  
	 * <pre>
	 *      ClassDefinition barClassDef = 
	 *          ClassDefinitionWarehouse.forClass(bar.getClass());
	 *      PropertyDefinition fooDefinition = 
	 *          barClassDef.lookupPropertyDefinition("foo");
	 *      PropertyValue fooValue = 
	 *         fooDefinition.getPropertyValue(bar);
	 * </pre>	 
	 * 
	 * @param controlPointValue The value of this control point at its position (time).
	 * 
	 * @throws NullPointerException The given control point value is <code>null</code>.
	 * @throws ClassCastException Cannot cast the given value to the defined type
	 * for this control point.
	 * 
	 * @see TypeDefinition#createValue(Object)
	 * @see PropertyValue#getValue()
	 * @see ConstantValue#setValue(PropertyValue)
	 */
	public void setControlPointValue(
			PropertyValue controlPointValue) 
		throws NullPointerException,
			ClassCastException;

	/**
	 * <p>Create a cloned copy of this control point.</p>
	 *
	 * @return Cloned copy of this control point.
	 */
	public ControlPoint clone();
}
