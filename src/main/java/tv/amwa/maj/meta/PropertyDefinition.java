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
 * $Log: PropertyDefinition.java,v $
 * Revision 1.11  2011/10/05 17:14:29  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.10  2011/07/27 17:09:19  vizigoth
 * Tag class removed from MXF IO ... comment updated to reflect the change.
 *
 * Revision 1.9  2011/02/14 22:32:50  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.8  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.7  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.6  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2009/02/24 18:47:54  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/02/08 11:27:18  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:49  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import tv.amwa.maj.exception.IllegalPropertyException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.record.AUID;

/**
 * <p>Specifies the description of a property allowed for a {@linkplain ClassDefinition class}.</p>
 * 
 * <p>A property definition is used to indicate a particular property
 * within a class.  It defines the name and type of a property that is
 * used to set the value of an object of the class.  Property definitions should be created through 
 * {@link ClassDefinition#registerNewPropertyDefinition(AUID, String, String[], String, String, boolean, boolean, Short)
 * registerNewPropertyDefinition()}
 * or {@link ClassDefinition#registerOptionalPropertyDefinition(AUID, String, String[], String, String)
 * registerOptionalPropertyDefinition()}.</p>
 * 
 *
 * 
 * @see ClassDefinition#registerNewPropertyDefinition(AUID, String, String[], String, String, boolean, boolean, Short)
 * @see ClassDefinition#registerOptionalPropertyDefinition(AUID, String, String[], String, String)
 * @see ClassDefinition#getPropertyDefinitions()
 * @see ClassDefinition#getAllPropertyDefinitions()
 * @see tv.amwa.maj.industry.Warehouse#lookForClass(String)
 * @see tv.amwa.maj.industry.MediaProperty
 * @see PropertyValue
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionWeakReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionStrongReferenceSet
 */

public interface PropertyDefinition 
	extends MetaDefinition,
		Comparable<PropertyDefinition> {

	/**
	 * <p>Returns the property type of the defined property.</p>
	 *  
	 * @return Type definition for this property definition.
	 * 
	 * @see #getPropertyType()
	 */
	public TypeDefinition getTypeDefinition();

	/**
	 * <p>Returns the identifier for the type of this property.</p>
	 * 
	 * @return Identifier for the type of this property.
	 * 
	 * @see #getTypeDefinition()
	 */
	public AUID getPropertyType();
	
	/**
	 * <p>Returns <code>true</code> for a property that is optional and
	 * <code>false</code> for a mandatory property. Optional properties can
	 * be omitted from an objects persistent representation.</p>
	 * 
	 * @return Is the property optional?
	 */
	public @Bool boolean getIsOptional();

	/**
	 * <p>Returns <code>true</code> for a property that provides a unique
	 * identifier for its associated object; otherwise <code>false</code>.</p>
	 * 
	 * @return Is this property a unique identifier for an object it is part
	 * of?
	 * 
	 * @throws PropertyNotPresentException The optional is unique identifier property
	 * is not present in this property definition.
	 */
	public @Bool boolean getIsUniqueIdentifier()
		throws PropertyNotPresentException; 
	
	/**
	 * <p>Returns the local identification associated with this property definition,
	 * which is used to identify the property within the scope of its associated class,
	 * as returned by {@link #getMemberOf()}. A value of zero indicates that no specified
	 * value is available and a unique tag should be generated dynamically on serialization.</p>
	 *
	 * @return Local identification associated with this property definition.
	 * 
	 * @see tv.amwa.maj.io.mxf.PrimerPack#lookupLocalTag(AUID)
	 */
	public @UInt16 short getLocalIdentification();
	
	/**
	 * <p>Sets the optional local identification associated with this property definition,
	 * which is used to identify the property within the scope of its associated class,
	 * as returned by {@link #getMemberOf()}.</p>
	 *
	 * @param localIdentification Local identification associated with this property 
	 * definition.
	 */
	/* public void setLocalIdentification(
			Short localIdentification); */
	
	/**
	 * <p>Returns the class definition for the class that this property definition defines
	 * a property for. This property is optional in the MAJ API but is almost always available
	 * in the implementation.</p>
	 *
	 * @return Class definition of the class that this property definition is associated
	 * with.
	 * 
	 * @throws PropertyNotPresentException The optional member of a class property is not present
	 * in this property definition.
	 * 
	 * @see ClassDefinition#getPropertyDefinitions()
	 */
	public ClassDefinition getMemberOf() 
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the class definition of the the class that this property definition is
	 * associated with.</p>
	 *
	 * @param memberOf Class definition of the class that this property definition is associated
	 * with.
	 */
	public void setMemberOf(
			ClassDefinition memberOf);
	
	/**
	 * <p>Returns the requested property value of the given object. The desired 
	 * property data is identified by this property definition.</p>
	 * 
	 * @param metadataObject Object with this property to retrieve the value from.
	 * @return This property value extracted from the object.
	 * 
	 * @throws NullPointerException The given metadata object is <code>null</code>.
	 * @throws IllegalPropertyException The given object does not have this property.
	 * @throws PropertyNotPresentException The given property is optional, but not 
	 * currently present in this instance of the class.
	 * @throws IllegalArgumentException An error occurred when trying to invoke
	 * the underlying class' method.
	 * 
	 * @see #setPropertyValue(MetadataObject, PropertyValue)
	 * @see MediaEngine#getPropertyValue(MetadataObject, String)
	 */
	public PropertyValue getPropertyValue(
			MetadataObject metadataObject) 
		throws NullPointerException,
			IllegalPropertyException,
			PropertyNotPresentException,
			IllegalArgumentException;
	
	/**
	 * <p>Sets the value of this property on the given {@linkplain MetadataObject object}.  
	 * If the selected property is optional but not yet present, this 
	 * method will make the property present before setting its value.</p>
	 * 
	 * @param metadataObject Object to set this property for.
	 * @param propertyValue Value to set for the property.
	 * 
	 * @throws NullPointerException One or both of the arguments is <code>null</code>.
	 * @throws IllegalPropertyException The given property is not defined on the given
	 * metadata object.
	 * @throws IllegalArgumentException An error occurred when trying to invole the
	 * underlying class' method.
	 * 
	 * @see #getPropertyValue(MetadataObject)
	 * @see PropertyDefinition#getTypeDefinition()
	 * @see TypeDefinition#createValue(Object)
	 */
	public void setPropertyValue(
			MetadataObject metadataObject,
			PropertyValue propertyValue) 
		throws NullPointerException,
			IllegalPropertyException,
			IllegalArgumentException;

	/**
	 * <p>Returns <code>true</code> if this is legal and
	 * present for the given {@linkplain MetadataObject object}; otherwise returns 
	 * <code>false</code>.</p>
	 * 
	 * @param metadataObject Object to use to check for the presence of this property.
	 * @return Is the given property present for this interchange object?
	 * 
	 * @throws NullPointerException The given metadata object is <code>null</code>.
	 * @throws IllegalPropertyException The given property is illegal for this 
	 * interchange object's class.
	 * 
	 * @see ClassDefinition#getPropertyDefinitions()
	 * @see PropertyNotPresentException
	 * @see #omitOptionalProperty(MetadataObject)
	 * @see tv.amwa.maj.industry.MediaEngine#isPropertyPresent(MetadataObject, String)
	 */
	public @Bool boolean isPropertyPresent(
			MetadataObject metadataObject) 
		throws NullPointerException,
			IllegalPropertyException;

	/**
	 * <p>Omits this property from the given {@linkplain MetadataObject object} if it is legal,
	 * optional and is present.</p>
	 * 
	 * @param metadataObject Object to request that this property is omitted.
	 * 
	 * @throws NullPointerException The given metadata object is <code>null</code>.
	 * @throws IllegalPropertyException The given metadata object does not have this
	 * property or the property is not optional.
	 * @throws IllegalArgumentException An error occurred when trying to omit the
	 * optional property on the underlying class.
	 * 
	 * @see ClassDefinition#getPropertyDefinitions()
	 * @see ClassDefinition#getProperties(MetadataObject)
	 * @see #isPropertyPresent(MetadataObject)
	 */
	public void omitOptionalProperty(
			MetadataObject metadataObject) 
		throws NullPointerException,
			IllegalPropertyException,
			IllegalArgumentException;
	
	// Create optional property removed ... use setPropertyValue for the same effect.

	/**
	 * <p>Returns the symbol name for this property, which is the same as its tag name
	 * in registered data XML. Symbol names provide a namespace with unique names
	 * for all SMPTE registered class names and property names.</p>
	 * 
	 * @return Symbol name of this class.
	 * 
	 * @see MediaProperty#symbol()
	 */
	public @AAFString String getSymbol();
	
	/**
	 * <p>Returns the relative weight of this property definition, which is used when 
	 * generating a list of properties for a class or ordering elements when serializing
	 * to XML. The lowest weight value appears first. The default value is&nbsp;0.</p>
	 * 
	 * <p>The weight value is MAJ API specific and has been introduced to support
	 * XML formats that require elements to be in a sequential order, such as the
	 * Broadcast Exchange Format (BXF, SMPTE 2021-2008).</p>
	 * 
	 * @return Relative weight for ordering the property in a list.
	 * 
	 * @see MediaProperty#weight()
	 */
	public @Int32 int getWeight();
	
	/**
	 * <p>Determines whether the content of this property should be serialized to XML
	 * as the CDATA content of the {@linkplain #getMemberOf() owning class} rather than
	 * as a separate element.</p>
	 * 
	 * @return Should the value of this property be serialized to XML
	 * as the CDATA content of the {@linkplain #getMemberOf() owning class}?
	 * 
	 * @see MediaProperty#isXMLCDATA()
	 */
	public boolean getIsXMLCDATA();
	
	/**
	 * <p>Determines whether the content of this property should be sereialized to XML
	 * as an XML attribute of the {@linkplain #getMemberOf() owning class} rather than
	 * as a separate element.</p>
	 * 
	 * @return Should the value of this property be sereialized to XML
	 * as an XML attribute of the {@linkplain #getMemberOf() owning class}?
	 * 
	 * @see MediaProperty#isXMLAttribute()
	 */
	public boolean getIsXMLAttribute();
	
	/**
	 * <p>Determines whether an XML serialization of referenced values or collections of 
	 * values should appear as a replacement for this element rather than contained within it.
	 * The replaced elements will have their symbol changed to match the 
	 * {@linkplain MetaDefinition#getSymbol() symbol of this element}.</p>
	 * 
	 * <p>Values are normally serialized to an element as follows:</p>
	 * 
	 * <pre>
	 * &lt;Parent&gt;
	 *   &lt;ThisProperty&gt;
	 *     &lt;Child attribute1="atval1"&gt;value1&lt;/Child&gt;
	 *     &lt;Child&gt;value2&lt;/Child&gt;
	 *     ...
	 *   &lt;/ThisProperty&gt;
	 *   ...
	 * &lt;/Parent&gt;</pre>
	 * 
	 * <p>When this flag is set to <code>true</code>, the values of the children of this
	 * property to be inserted into this property's parent directly with the same symbol as this
	 * property:</p>
	 * 
	 * <pre>
	 * &lt;Parent&gt;
	 *   &lt;ThisProperty attribute1="atval1"&gt;value1&lt;/ThisProperty&gt;
	 *   &lt;ThisProperty&gt;value2&lt;/ThisProperty&gt;
	 *   ...
	 * &lt;/Parent&gt;</pre>
	 * 
	 * <p>Attributes are also carried to the higher level.</p>
	 * 
	 * @return Should an XML serialization of referenced values or collections of 
	 * values should appear as a replacement for this element rather than contained within it?
	 * 
	 * @see MediaProperty#flattenXML()
	 */
	public boolean getFlattenXML();
	
	/**
	 * <p>Create a cloned copy of this property definition.</p>
	 *
	 * @return Cloned copy of this property definition.
	 */
	public PropertyDefinition clone();
		
}
