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
 * $Log: TypeDefinitionInteger.java,v $
 * Revision 1.9  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2010/12/15 18:51:31  vizigoth
 * Moved constant values for integer size into interface.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:40:07  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:38  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import tv.amwa.maj.exception.BadSizeException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.misctype.Bool;

/**
 * <p>Specifies the definition of a property type that is an integer with the specified 
 * number of bytes.</p>
 * 
 * <p>The representation of integers in the MAJ API is explained in the
 * <a href="../integer/package-summary.html">description of the integer package</a>. The 
 * methods of this class cast values of subclasses of {@link java.lang.Number} to values
 * of Java type used to represent an integer value, as described in the 
 * <a href="../integer/package-summary.html#aafIntegerToJavaType">type mapping table</a>.
 * The methods here are specified in such a way that rounding or truncations errors should
 * be avoidable</p>
 * 
 *
 *
 * @see tv.amwa.maj.enumeration.TypeCategory#Int
 * @see tv.amwa.maj.integer.Int32
 * @see tv.amwa.maj.integer.UInt8
 */

public interface TypeDefinitionInteger 
	extends TypeDefinition {

	/** <p>Number of bytes used to represent a Java <code>byte</code> value.</p> */
	public final static int BYTE = 1;
	/** <p>Number of bytes used to represent a Java <code>short</code> value.</p> */
	public final static int SHORT = 2;
	/** <p>Number of bytes used to represent a Java <code>int</code> value.</p> */
	public final static int INT = 4;
	/** <p>Number of bytes used to represent a Java <code>long</code> value.</p> */
	public final static int LONG = 8;
	
	/**
	 * <p>Creates a property value as defined by this integer type
	 * definition using the given number. The given number will
	 * he cast to a value that fits with this integer type definition.</p>
	 * 
	 * <p>It is possible that the given Java value cannot be converted to
	 * a value compatible with this integer type definition. If this is the
	 * case, an {@link IllegalArgumentException} is thrown. Conversion may not be
	 * possible for the following reasons:</p>
	 * 
	 * <ul>
	 *  <li>The given value is a <code>float</code>, <code>double</code> or
	 *  {@link java.math.BigDecimal} that includes decimal part to its value that 
	 *  would require rounding as part of the conversion.</li>
	 *  <li>The given value uses more bytes to represent an integer value
	 *  than the integer type definition, as returned by {@link #getSize()}.
	 *  Conversion will require truncation of the value.</li>
	 *  <li>The given value is negative and the integer type definition
	 *  is not signed.</li>
	 * </ul>
	 * 
	 * <p>To specifically cause truncation or rounding to take place, perform an appropriate
	 * cast before calling this method.</p>
	 * 
	 * <p>A {@link BadSizeException} may also be thrown if the given value is
	 * a {@link java.math.BigInteger} or {@link java.math.BigDecimal} value as an implementation
	 * is not required to support values greater than 8-bytes in length.</p>
	 * 
	 * @param value Value to create a new integer property value from.
	 * @return New integer property value consistent with this integer type 
	 * definition and set to the given value.
	 * 
	 * @throws NullPointerException The given number value is <code>null</code>.
	 * @throws IllegalArgumentException Due to its value, the given value cannot 
	 * be converted to a property value defined by this integer type definition.
	 * 
	 * @see #setInteger(PropertyValue, Number)
	 */
	public PropertyValue createValueFromNumber(
			Number value) 
		throws NullPointerException,
			BadSizeException,
			IllegalArgumentException;

	/**
	 * <p>Returns the value of the given integer property value as
	 * a {@link java.lang.Number}. This value can then be converted
	 * to the required type by calling 
	 * {@link Number#intValue()}, {@link Number#byteValue()} etc..</p>
	 * 
	 * @param integerProperty Property value of integer type.
	 * @return Integer value represented by an appropriate wrapper type
	 * from the <code>java.lang</code> package, e.g. {@link Byte}, 
	 * {@link Integer} etc..
	 * 
	 * @throws NullPointerException The given integer property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given integer property value
	 * is not as defined by this integer type definition.
	 */
	public Number getInteger(
			PropertyValue integerProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Convert the given number value to one appropriate for this integer
	 * type definition and set the value of the given integer property
	 * value to be the converted value. This method can make use of Java's 
	 * autoboxing features.</p>
	 * 
	 * <p>It is possible that the given Java value cannot be converted to
	 * a value compatible with the integer type definition. If this is the
	 * case, an {@link IllegalArgumentException} is thrown. Conversion may not be
	 * possible for the following reasons:</p>
	 * 
	 * <ul>
	 *  <li>The given value is a <code>float</code>, <code>double</code> or
	 *  {@link java.math.BigDecimal} that includes decimal part to its value that 
	 *  would require rounding as part of the conversion.</li>
	 *  <li>The given value uses more bytes to represent an integer value
	 *  than the integer type definition, as returned by {@link #getSize()}.
	 *  Conversion will require truncation of the value.</li>
	 *  <li>The given value is negative and the integer type definition
	 *  is not signed.</li>
	 * </ul>
	 * 
	 * <p>To specifically cause truncation or rounding to take place, perform an appropriate
	 * cast before calling this method.</p>
	 * 
	 * @param integerProperty Property value to set the value of.
	 * @param value Value to set for the given integer property value to.
	 * 
	 * @throws NullPointerException One or both of the integer property value and/or number
	 * value arguments is/are <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not compatible
	 * with this integer type definition.
	 * @throws IllegalArgumentException Due to its value, the given value cannot 
	 * be converted to a property value defined by the integer type definition.
	 * 
	 * @see #createValueFromNumber(Number)
	 */
	public void setInteger(
			PropertyValue integerProperty,
			Number value) 
		throws NullPointerException,
			BadSizeException,
			IllegalPropertyValueException,
			IllegalArgumentException;

	/**
	 * <p>Returns the size of integral value of the integer type definition,
	 * measured in number of bytes.</p>
	 * 
	 * @return Size of integral value of the integer type definition,
	 * measured in number of bytes.
	 */
	public @UInt8 byte getSize();

	/**
	 * <p>Returns <code>true</code> if the integer type definition
	 * defines signed integer values; otherwise <code>false</code>.</p>
	 * 
	 * @return Does the integer type definition define signed integer
	 * values?
	 */
	public @Bool boolean isSigned();
	
	/**
	 * <p>Create a cloned copy of this integer type definition.</p>
	 *
	 * @return Cloned copy of this integer type definition.
	 */
	public TypeDefinitionInteger clone();
}
