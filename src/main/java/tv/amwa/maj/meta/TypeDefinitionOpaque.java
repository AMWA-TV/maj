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
 * $Log: TypeDefinitionOpaque.java,v $
 * Revision 1.10  2011/02/14 22:32:50  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.9  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:40:23  vizigoth
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
 * Revision 1.3  2008/02/14 12:55:14  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:40:06  vizigoth
 * Changed name of IllegalValueException to IllegalPropertyValue.
 *
 * Revision 1.1  2007/11/13 22:08:26  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import java.nio.ByteBuffer;

import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.model.Dictionary;
import tv.amwa.maj.model.KLVData;
import tv.amwa.maj.model.KLVDataDefinition;
import tv.amwa.maj.record.AUID;

/**
 * <p>Specifies the definition of property values whose type is specified in
 * each instance, providing access to data opaque to this API and manipulated directly by an application through
 * a handle. The data type of the value may be contained in a key ({@linkplain tv.amwa.maj.record.AUID AUID})
 * that is stored with the data itself. To access the data, its type
 * must be known to the application.</p>
 * 
 * <p>Note that many of the methods of {@linkplain TypeDefinitionIndirect} are overridden
 * by opaque-specific versions in the implementation provided by the MAJ API. In this case,
 * an attempt is made to resolve the type of the opaque data using the assumption that the first
 * 16&nbsp;bytes  represent a key to the data type. </p>
 * 
 *
 * 
 * @see tv.amwa.maj.enumeration.TypeCategory#Opaque
 * @see KLVData
 * @see KLVDataDefinition 
 * @see tv.amwa.maj.industry.TypeDefinitions#Opaque
 *
 */

public interface TypeDefinitionOpaque 
	extends TypeDefinitionIndirect {

	/**
	 * <p>Returns the type identifier of the actual data within the given opaque
	 * property value. This method assumes that the type identifier is stored in 
	 * the first 16&nbsp;bytes of the opaque data.</p>
	 * 
	 * @param opaqueProperty Property value as defined by this opaque type
	 * definition.
	 * @return Type identifier of the actual data within the given opaque
	 * property value.
	 * 
	 * @throws NullPointerException The given opaque property definition is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not defined
	 * by this opaque type definition.
	 * 
	 * @see Dictionary#lookupKLVDataDefinition(AUID)
	 * @see TypeDefinitionIndirect#getActualType(PropertyValue)
	 */
	public AUID getActualTypeID(
			PropertyValue opaqueProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Read and return the given opaque property
	 * value without having to know its contents. The returned buffer of
	 * data can be used later by a call to 
	 * {@link #setHandle(PropertyValue, ByteBuffer)}.</p>
	 * 
	 * @param opaqueProperty Property value containing unknown data to
	 * be returned.
	 * @return Buffer of unknown data as contained within the given
	 * opaque property value.
	 * 
	 * @throws NullPointerException The given opaque property value is <code>null</code>.
	 * @throws IllegalPropertyValueException The given property value is not defined
	 * by this opaque type definition.
	 * 
	 * @see #setHandle(PropertyValue, ByteBuffer)
	 * @see TypeDefinitionIndirect#getActualData(PropertyValue)
	 * @see TypeDefinitionIndirect#getActualValue(PropertyValue)
	 */
	public @DataBuffer ByteBuffer getHandle(
			PropertyValue opaqueProperty) 
		throws NullPointerException,
			IllegalPropertyValueException;

	/**
	 * <p>Sets the value of the given opaque property value to the given unknown
	 * data. This data may have previously been retrieved by a call to 
	 * {@link #getHandle(PropertyValue)}.</p>
	 * 
	 * @param opaqueProperty Property value to set the unknown data value for.
	 * @param handle Data to set as the value of the given opaque property
	 * value. 
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>.
	 * @throws IllegalArgumentException A handle must contain at least 16&nbsp;bytes of
	 * data.
	 * @throws IllegalPropertyValueException The given property value is not defined
	 * by this opaque type definition.
	 * 
	 * @see #getHandle(PropertyValue)
	 * @see #createValueFromHandle(ByteBuffer)
	 */
	public void setHandle(
			PropertyValue opaqueProperty,
			@DataBuffer ByteBuffer handle) 
		throws NullPointerException,
			IllegalArgumentException,
			IllegalPropertyValueException;

	/**
	 * <p>Creates and returns a new opaque property value from the given
	 * block of initial data.</p>
	 * 
	 * @param initialData Buffer containing the handle to use.
	 * @return Newly created opaque property value containing the given
	 * data. 
	 * 
	 * @throws NullPointerException The given initial data handle is <code>null</code>.
	 * @throws IllegalArgumentException A handle must contain at least 
	 * 16&nbsp;bytes of data.
	 * 
	 * @see #setHandle(PropertyValue, ByteBuffer)
	 * @see #getHandle(PropertyValue)
	 * @see TypeDefinitionIndirect#createValueFromActualData(TypeDefinition, byte[])
	 * @see TypeDefinitionIndirect#createValueFromActualValue(PropertyValue)
	 */
	public PropertyValue createValueFromHandle(
			@DataBuffer ByteBuffer initialData) 
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Create a cloned copy of this opaque type definition.</p>
	 *
	 * @return Cloned copy of this opaque type definition.
	 */
	public TypeDefinitionOpaque clone();
}
