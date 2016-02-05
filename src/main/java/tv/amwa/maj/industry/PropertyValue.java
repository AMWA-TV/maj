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
 * $Log: PropertyValue.java,v $
 * Revision 1.2  2011/01/26 11:50:57  vizigoth
 * Completed common method testing.
 *
 * Revision 1.1  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2009/02/24 18:47:54  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.3  2008/02/28 12:50:36  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2008/02/08 11:27:26  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.industry;

import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionInteger;

// import tv.amwa.maj.misctype.Bool;

/**
 * <p>Specifies the type and current value of a property. Methods of the subclasses of 
 * {@linkplain TypeDefinition type definition} allow access to the data value held in the property
 * value object.</p>
 *
 *
 * 
 * @see TypeDefinition#createValue(Object)
 * @see PropertyDefinition
 * @see PropertyValue#getValue()
 */
public interface PropertyValue {

	/**
	 * <p>Returns the type definition for this property value.</p>
	 * 
	 * <p>Methods of the returned {@linkplain TypeDefinition type definition} can be 
	 * used to manipulate a property value. For example, for a value of AAF {@link tv.amwa.maj.integer.UInt32} 
	 * type, the following methods are available on {@link TypeDefinitionInteger}:</p>
	 * 
	 * <ul>
	 *  <li>{@link TypeDefinitionInteger#getInteger(PropertyValue) Number getInteger(PropertyValue)}</li>
	 *  <li>{@link TypeDefinitionInteger#createValueFromNumber(Number) PropertyValue createValueFromNumber(Number)}</li>
	 *  <li>{@link TypeDefinitionInteger#setInteger(PropertyValue, Number) void setInteger(PropertyValue, Number)}</li>
	 * </ul>
	 *
	 * @return Type of this property value.
	 * 
	 * @see PropertyDefinition#getTypeDefinition()
	 * @see tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionWeakReference
	 */
	public TypeDefinition getType();
	
	// TODO what does this method really do ... should it be included?
	
	/**
	 * <p>Returns <code>false</code> if this property value's type is not (necessarily) the one which was 
	 * defined for it.  That may be the case if this property value was read from a damaged file where 
	 * type information was not available; in that case {@link #getType()} method will return the raw 
	 * access type for this value.</p>
	 * 
	 * <p>If this property value's type is the one which was defined for it, this method will return 
	 * <code>true</code> and {@link #getType()} will return that defined type for this value.</p>
	 *
	 * @return Is the property value's type the one that was defined for it?
	 */
	// public @Bool boolean isDefinedType();
	
	/**
	 * <p>Returns the value of the property through a generic {@linkplain java.lang.Object Java object} interface. 
	 * Alternative type-specific access methods are provided in each type definition.</p>
	 * 
	 * <p>For example, for a property value of a {@link tv.amwa.maj.integer.UInt32} type, this method
	 * returns an {@link java.lang.Integer}. Values will need to be cast to appropriate types by the
	 * application. The value returned may be <code>null</code>, which may indicate that an optional
	 * property was not present.</p>
	 *
	 * @return Value of this property.
	 * 
	 * @see TypeDefinition#createValue(Object)
	 */
	public Object getValue();
	
	/**
	 * <p>Tests to see if the this property value and the given object are equal. Two property values
	 * are equal if-and-only-if:</p>
	 * 
	 * <ul>
	 *  <li>the given object is not <code>null</code>;</li>
	 *  <li>the given object is another property value;</li>
	 *  <li>the given property value has the same type as this one;</li>
	 *  <li>the values of the given property and this one are equal according to 
	 *  {@link Object#equals(Object)} or both property values represent a <code>null</code> value.</li>
	 * </ul>
	 * 
	 * @param o Property value to test equality with this one.
	 * @return Is this property value equal to the given property value.
	 * 
	 * @see MediaEngine#equals(Object)
	 * @see MediaEngine#deepEquals(MetadataObject, Object)
	 */
	public boolean equals(
			Object o);
	
	/**
	 * <p>Computes a hash code for this property value, based on type and value. This
	 * method follows the {@linkplain Object#hashCode() Java hash code} rules.</p>
	 * 
	 * @return Hashcode computed for this property value.
	 * 
	 * @see MediaEngine#hashCode(MetadataObject)
	 */
	public int hashCode();
	
	/**
	 * <p>Creates a string representation of the value contained in this property value.
	 * The string "null" is returned for a <code>null</code> value. The type of the value
	 * is not included.</p>
	 * 
	 * @return String representation of the value contained in this property value.
	 * 
	 * @see MediaEngine#toString(MetadataObject)
	 */
	public String toString();
	
}
