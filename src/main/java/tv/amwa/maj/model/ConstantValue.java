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
 * $Log: ConstantValue.java,v $
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
 * Revision 1.3  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2008/01/27 11:07:36  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:39  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionIndirect;

/** 
 * <p>Specifies a a constant data value for an effect control value. Use {@linkplain VaryingValue varying 
 * values} and one or more {@linkplain ControlPoint control points} for parameters which change in value 
 * during the operation group.</p>
 * 
 * <p>The value property of a constant value is specified to be of {@linkplain TypeDefinitionIndirect
 * indirect type}, which the MAJ API represents as a {@linkplain java.lang.Object Java object}. This value
 * should be convertible to an {@linkplain PropertyValue AAF property value} using 
 * {@link TypeDefinition#createValue(Object)} for the type specified for this parameter.</p>
 * 
 *
 * 
 * @see ParameterDefinition
 * @see TypeDefinitionIndirect
 * @see PropertyValue
 */

public interface ConstantValue 
	extends Parameter {

	/** <p>Returns the value of this constant value object, which is a 
	 * {@linkplain PropertyValue property value} that
	 * contains the type and current value.</p>
	 *
	 * @return Property value of this constant value.
	 * 
	 * @see PropertyValue#getValue()
	 * @see PropertyValue#getType()
	 */
	public PropertyValue getValue();

	/**
	 * <p>Returns the type definition associated with this constant value, which is the same
	 * as the type definition for the {@linkplain ParameterDefinition parameter definition}
	 * of this {@linkplain Parameter parameter}.</p>
	 * 
	 * @return Type definition of the data value inside of this object.
	 *  
	 * @see Parameter#getTypeDefinition()
	 * @see ParameterDefinition#getParameterType()
	 */
	public TypeDefinition getTypeDefinition();

	/**
	 * <p>Set the {@linkplain PropertyValue value} of this constant value.</p>
	 * 
	 * <p>To create a property value of a specific type, use the 
	 * {@link TypeDefinition#createValue(Object) createValue()} method of a 
	 * {@link TypeDefinition type definition}. To access a type definition, use
	 * the {@link #getTypeDefinition()} method as follows:</p>
	 * 
	 * <pre>
	 *      constantValue.setValue(
	 *          constantValue.getTypeDefinition().createValue(42));
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
	 * @param value Value and type to of this constant value.
	 * 
	 * @throws NullPointerException The given value is <code>null</code>.
	 * 
	 * @see TypeDefinition#createValue(Object)
	 * @see PropertyValue#getValue()
	 * @see tv.amwa.maj.industry.Warehouse#lookForType(String)
	 */
	public void setValue(
			PropertyValue value) 
		throws NullPointerException,
			ClassCastException;
	
	/**
	 * <p>Create a cloned copy of this constant value.</p>
	 *
	 * @return Cloned copy of this constant value.
	 */
	public ConstantValue clone();
}


