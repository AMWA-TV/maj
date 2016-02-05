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
 * $Log: ControlPointImpl.java,v $
 * Revision 1.7  2011/07/27 17:33:23  vizigoth
 * Fixed imports to clear warnings.
 *
 * Revision 1.6  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.5  2011/01/25 14:18:28  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.4  2011/01/21 12:37:01  vizigoth
 * Final fixes to work with lenient initialization of properties.
 *
 * Revision 1.3  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
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
 * Revision 1.2  2008/01/27 11:14:43  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.1  2007/11/13 22:09:06  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.nio.ByteBuffer;

import tv.amwa.maj.enumeration.EditHintType;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.RationalRangeException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.model.ControlPoint;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.RationalImpl;


/**
 * <p>Implements a value and a time point (position) that is used to specify an effect control value. 
 * Control points must be added to a {@linkplain tv.amwa.maj.model.VaryingValue varying value} that is then added 
 * to the {@linkplain tv.amwa.maj.model.OperationGroup operation group} describing the effect.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#ControlPointStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ControlPointStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1900,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "ControlPoint",
		  description = "The ControlPoint class specifies a value and a time point and is used to specify an effect control value.",
		  symbol = "ControlPoint")
public class ControlPointImpl
	extends 
		InterchangeObjectImpl
	implements 
		ControlPoint,
		Serializable,
		XMLSerializable,
		Cloneable {
	
	/**  */
	private static final long serialVersionUID = -5739560488672782553L;

	// private static final TypeDefinitionIndirect indirectType = 
	//  	(TypeDefinitionIndirect) TypeDefinitionWarehouse.forName("Indirect");
	
	private AUID valueType;
	private Serializable controlPointValue;
	private Rational controlPointTime;
	private EditHintType editHint = null;

	public ControlPointImpl() { }

	/**
	 * <p>Creates and initializes a new control point object, which specifies a value and a 
	 * time point and is used to specify an effect control value.</p>
	 * 
	 * @param varyingValue A varying value. This determines the type of this control point through the 
	 * parameter definition.
	 * @param controlPointTime Specifies the time within the VaryingValue segment for which the value is defined, 
	 * with a value between 0&nbsp;and&nbsp;1.
	 * @param controlPointValue Control point type and value.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and all values 
	 * are required.
	 * @throws RationalRangeException Rational time value is outside the inclusive range 0.0 to 1.0.
	 * @throws ClassCastException Cannot cast the given Java value to the given AAF type definition.
	 */
	public ControlPointImpl(
			tv.amwa.maj.model.VaryingValue varyingValue,
			tv.amwa.maj.record.Rational controlPointTime,
			PropertyValue controlPointValue) 
		throws NullPointerException,
			RationalRangeException {

		if (varyingValue == null)
			throw new NullPointerException("Cannot create a control point with a null varying value.");
		if (controlPointTime == null)
			throw new NullPointerException("Cannot create a control point with a null time value.");
		if (controlPointValue == null)
			throw new NullPointerException("Cannot create a control point with a null value.");
		
		setTypeDefinition(varyingValue.getTypeDefinition());
		setControlPointTime(controlPointTime);
		setControlPointValue(controlPointValue);
	}

	// Added for the safe initialization of VaryingValue.PointList
	public ControlPointImpl(
			tv.amwa.maj.meta.TypeDefinition varyingValueType,
			tv.amwa.maj.record.Rational controlPointTime,
			PropertyValue controlPointValue) {
		
		if (varyingValueType == null)
			throw new NullPointerException("Cannot create a control point with a null varying value type.");
		if (controlPointTime == null)
			throw new NullPointerException("Cannot create a control point with a null time value.");
		if (controlPointValue == null)
			throw new NullPointerException("Cannot create a control point with a null value.");
		
		setTypeDefinition(varyingValueType);
		setControlPointTime(controlPointTime);
		setControlPointValue(controlPointValue);
	}
	
	@MediaProperty(uuid1 = 0x05300508, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EditHint",
			typeName = "EditHintType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1A04,
			symbol = "EditHint")
	public EditHintType getEditHint()
			throws PropertyNotPresentException {

		if (editHint == null)
			throw new PropertyNotPresentException("Edit hint property not yet set for control point.");

		return editHint;
	}

	@MediaPropertySetter("EditHint")
	public void setEditHint(
			EditHintType editHint) {
		
		this.editHint = editHint;
	}

	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x1002, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ControlPointTime",
			aliases = { "Time" },
			typeName = "Rational",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1A03,
			symbol = "ControlPointTime")
	public Rational getTime() {

		return controlPointTime.clone();
	}

	@MediaPropertySetter("ControlPointTime")
	public void setControlPointTime(
			Rational controlPointTime)
		throws RationalRangeException,
			NullPointerException {

		if (controlPointTime == null)
			throw new NullPointerException("Cannot set the time for a control point from a null value.");
		
		double rangeCheckValue = controlPointTime.doubleValue();
		if ((rangeCheckValue < 0.0) || (rangeCheckValue > 1.0))
			throw new RationalRangeException("Time value is outside the acceptable inclusive range of 0.0 to 1.0.");
		
		this.controlPointTime = controlPointTime.clone();
	}
	
	public final static Rational initializeControlPointTime() {
		
		return new RationalImpl(0, 1);
	}

	public TypeDefinition getTypeDefinition() {

		return Warehouse.lookForType(valueType).clone();
	}

	void setTypeDefinition(
			TypeDefinition valueType) {
		
		this.valueType = valueType.getAUID().clone();
	}

	@MediaProperty(uuid1 = 0x0530050d, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ControlPointValue",
			aliases = { "Value" },
			typeName = "Indirect",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1A02,
			symbol = "ControlPointValue")
	public PropertyValue getControlPointValue() {

		return Warehouse.lookForType(valueType).createValue(controlPointValue);
	}

	@MediaPropertySetter("ControlPointValue")
	public void setControlPointValue(
			PropertyValue controlPointValue)
		throws NullPointerException,
			ClassCastException {
		
		if (controlPointValue == null) 
			throw new NullPointerException("Cannot set a control point value to null.");
		
		if (controlPointValue.getType() == null)
			throw new NullPointerException("Cannot set a control point value with a null type in a property value.");
		
		if (controlPointValue.getType().equals(TypeDefinitions.Indirect))
			controlPointValue = (PropertyValue) controlPointValue.getValue();
		
		if (!(controlPointValue.getValue() instanceof Serializable))
			throw new ClassCastException("The value of a control point must implement java.io.Serializable.");
		
		this.valueType = controlPointValue.getType().getAUID().clone();
		this.controlPointValue = (Serializable) controlPointValue.getValue();
	}
	
	public final static PropertyValue initializeControlPointValue() {
		
		return TypeDefinitions.Int32.createValue(0);
	}
	
	@Override
	public ControlPoint clone() {
		
		return (ControlPoint) super.clone();
	}
	
	@Override
	public String getComment() {
		
		return "local control point persistent id: " + getPersistentID();
	}

	public String getControlPointTimeString() {
		
		return RationalImpl.toPersistentForm(controlPointTime);
	}
	
	public void setControlPointTimeString(
			String controlPointTime) {
		
		this.controlPointTime = RationalImpl.fromPersistentForm(controlPointTime);
	}
	
	public byte[] getControlPointValuePersist() 
		throws NotSerializableException, 
			NullPointerException, 
			IllegalPropertyValueException, 
			InsufficientSpaceException {
	
		if ((controlPointValue == null) || (valueType == null)) return null;
	
		PropertyValue typedValue = getControlPointValue();
		PropertyValue indirectValue = TypeDefinitions.Indirect.createValueFromActualValue(typedValue);
	
		ByteBuffer buffer = ByteBuffer.allocate((int) TypeDefinitions.Indirect.lengthAsBytes(indirectValue));
		TypeDefinitions.Indirect.writeAsBytes(indirectValue, buffer);
	
		buffer.rewind();
		return buffer.array();
	}

	public void setControlPointValuePersist(
		byte[] indirectBytes) {
	
		if (indirectBytes == null) {
			controlPointValue = null;
			valueType = null;
			return;
		}
	
		PropertyValue indirectValue = TypeDefinitions.Indirect.createFromBytes(ByteBuffer.wrap(indirectBytes));
		PropertyValue typedValue = (PropertyValue) indirectValue.getValue();
	
		controlPointValue = (Serializable) typedValue.getValue();
		valueType = typedValue.getType().getAUID();
	}
}

