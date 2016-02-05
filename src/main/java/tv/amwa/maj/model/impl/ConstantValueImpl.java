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
 * $Log: ConstantValueImpl.java,v $
 * Revision 1.4  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
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
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:42  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;

import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.ConstantValue;
import tv.amwa.maj.model.ControlPoint;
import tv.amwa.maj.model.ParameterDefinition;
import tv.amwa.maj.model.VaryingValue;

/** 
 * <p>Implements a constant data value for an effect control value. Use {@linkplain VaryingValue varying 
 * values} and one or more {@linkplain ControlPoint control points} for parameters which change in value 
 * during the operation group.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3d00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "ConstantValue",
		  description = "The ConstantValue class specifies a constant data value for an effect control value.",
		  symbol = "ConstantValue")
public class ConstantValueImpl
	extends ParameterImpl
	implements ConstantValue,
		Serializable,
		XMLSerializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -3320656372467708785L;

	// private static final TypeDefinitionIndirect indrectType = 
	//	(TypeDefinitionIndirect) TypeDefinitionWarehouse.forName("Indirect");
	
	private Serializable value;
	
	/** Default constructor is not public to avoid unset required fields. */
	public ConstantValueImpl() { }

	/**
	 * <p>Creates and initializes a new constant value object that is identified with the 
	 * given parameter definition and the value of the constant.</p>
	 * 
	 * <p>Note that a data type may allow the value of a property to be <code>null</code>.</p>
	 * 
	 * @param parameterDefinition Parameter definition for this object. This determines 
	 * the type of the constant value.
	 * @param value Buffer containing the constant's value.
	 * 
	 * @throws NullPointerException The parameter definition is <code>null</code>.
	 * @throws ClassCastException The given value cannot be converted to a value compatible
	 * with the {@linkplain #getTypeDefinition() type definition} of the parameter.
	 */
	public ConstantValueImpl(
			ParameterDefinition parameterDefinition,
			Object value) 
		throws NullPointerException,
			ClassCastException {
		
		if (parameterDefinition == null)
			throw new NullPointerException("Cannot create a new constant value from a null parameter definition.");
		
		setParameterDefinition(parameterDefinition);
		setValue(getTypeDefinition().createValue(value));
	}
	
	@MediaProperty(uuid1 = 0x05300507, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Value",
			aliases = { "ConstantValueValue" },
			typeName = "Indirect",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4D01,
			symbol = "Value")
	public PropertyValue getValue() {

		return getTypeDefinition().createValue(value);
	}

	@MediaPropertySetter("Value")
	public void setValue(
			PropertyValue value)
		throws NullPointerException,
			ClassCastException {

		if (value == null)
			throw new NullPointerException("Cannot set the value of this constant value using a null value.");
		
		if (value.getType().equals(TypeDefinitions.Indirect))
			value = (PropertyValue) value.getValue();
		
		try {
			if (!value.getType().equals(getTypeDefinition()))
				throw new ClassCastException("The given value cannot be cast to a value of the given type for this constant value.");
		}
		catch (NullPointerException npe) {
			// Parameter is not yet set ... so just keep going
		}
			
		if (!(value.getValue() instanceof Serializable))
			throw new ClassCastException("The value must implement java.io.Serializable.");
		
		this.value = (Serializable) value.getValue();
	}

	public final static PropertyValue initializeValue() {
		
		return TypeDefinitions.Rational.createValue("0/1");
	}
	
	/* @Lob
	@Basic(optional = false)
	@Column(name = "Value")
	byte[] getValueValue() 
		throws IOException { 
	
		PropertyValue valueAsProperty = getTypeDefinition().createValue(value);
		PropertyValue indirectValue = indrectType.createValue(valueAsProperty);
		return indrectType.getActualData(indirectValue);
	}
	
	void setValueValue(byte[] value) 
		throws BadParameterException {
		
		PropertyValue indirectValue = 
			indrectType.createValueFromActualData(getTypeDefinition(), value);
		PropertyValue valueAsProperty = indrectType.getActualValue(indirectValue);
		this.value = valueAsProperty.getValue();
	} */
	
	@Override
	public ConstantValue clone() {
		
		return (ConstantValue) super.clone();
	}
	
	public byte[] getValuePersist() 
		throws NullPointerException, 
			IllegalPropertyValueException, 
			InsufficientSpaceException {
		
		if (value == null) return null;
		
		PropertyValue valueAsProperty = getTypeDefinition().createValue(value);
		PropertyValue indirectValue = TypeDefinitions.Indirect.createValue(valueAsProperty);
		
		ByteBuffer buffer = ByteBuffer.allocate((int) TypeDefinitions.Indirect.lengthAsBytes(indirectValue));
		TypeDefinitions.Indirect.writeAsBytes(indirectValue, buffer);
		
		buffer.rewind();
		return buffer.array();
	}
	
	public void setValuePersist(
			byte[] value) {
		
		if (value == null) {
			this.value = null;
			return;
		}
		
		PropertyValue indirectValue = TypeDefinitions.Indirect.createFromBytes(ByteBuffer.wrap(value));
		PropertyValue typedValue = (PropertyValue) indirectValue.getValue();
		
		this.value = (Serializable) typedValue.getValue();
	}
}
