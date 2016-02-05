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
 * $Log: TaggedValue.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/02/08 11:27:20  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:58  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionIndirect;
import tv.amwa.maj.meta.TypeDefinitionString;
import tv.amwa.maj.misctype.AAFString;

/**
 * <p>Specifies a user defined tag and value pair.</p>
 * 
 * <p>The value is of {@linkplain TypeDefinitionIndirect indirect type} and the MAJ
 * API provides methods that map values from Java objects to and from indirect type
 * values. These mechanisms are hidden behind the {@link #getIndirectValue()} and
 * {@link #setIndirectValue(PropertyValue)} methods.</p>
 * 
 * <p>A common type for data values is {@linkplain TypeDefinitionString string} and
 * both the {@linkplain Package package} and {@linkplain Component component} interfaces specify
 * shortcut methods to create string comments and attributes. For example, {@link Package#appendPackageUserComment(String, String)}
 * and {@link Component#appendComponentAttribute(String, String)}.</p>
 * 
 *
 * 
 * @see TaggedValueDefinition
 * @see Dictionary#getTaggedValueDefinitions()
 * @see Package#getPackageAttributes()
 * @see Package#getPackageUserComments()
 * @see Component#getComponentAttributes()
 * @see Component#getComponentUserComments()
 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueStrongReferenceVector
 */
public interface TaggedValue 
	extends InterchangeObject {

	/**
	 * <p>Returns the tag name property of this tagged value, which is a user-defined tag.</p>
	 * 
	 * @return Name property of this tagged value.
	 */
	public @AAFString String getTag();

	/**
	 * <p>Returns the {@linkplain TypeDefinition type} of the user-defined indirect value.</p>
	 * 
	 * @return Type definition for this tagged value.
	 * 
	 * @see #getIndirectValue()
	 * @see TypeDefinitionIndirect
	 * @see TypeDefinitionIndirect#getActualValue(PropertyValue)
	 * @see PropertyValue#getType()

	 */
	public TypeDefinition getIndirectTypeDefinition();

	/**
	 * <p>Returns the user-defined value of this tagged value as a {@linkplain PropertyValue
	 * property value}.</p>
	 * 
	 * <p>The type of the returned value can be found be calling {@link PropertyValue#getType()}. 
	 * Methods of the {@linkplain TypeDefinition type definition} returned can be used to 
	 * access the value. Alternatively, you can access the value directly by calling 
	 * {@link PropertyValue#getValue()}.</p>
	 * 
	 * @return Indirect value of this tagged value.
	 * 
	 * @see #getIndirectTypeDefinition()
	 * @see TypeDefinitionIndirect
	 * @see TypeDefinitionIndirect#getActualValue(PropertyValue)
	 * @see PropertyValue#getValue()
	 */
	public PropertyValue getIndirectValue();

	/**
	 * <p>Sets the user-defined indirect value of this tagged value.</p>
	 * 
	 * <p>To create an indirect value, use the {@link TypeDefinition#createValue(Object)
	 * createValue()} method of any {@linkplain TypeDefinition type definition}.
	 * Alternatively, you can retrieve the value of any other property 
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
	 * @param indirectValue Indirect value of this tagged value.
	 * 
 	 * @throws NullPointerException The given user-defined value is <code>null</code>.
 	 * 
 	 * @see TypeDefinitionIndirect
	 * @see TypeDefinition#createValue(Object)
 	 * @see tv.amwa.maj.industry.Warehouse#lookForType(String)
 	 * @see PropertyDefinition#getPropertyValue(MetadataObject)
	 */
	public void setIndirectValue(
			PropertyValue indirectValue) 
		throws NullPointerException;

	/**
	 * <p>Create a cloned copy of this tagged value.</p>
	 *
	 * @return Cloned copy of this tagged value.
	 */
	public TaggedValue clone();
}

