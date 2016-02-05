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
 * $Log: TaggedValueImpl.java,v $
 * Revision 1.7  2011/11/04 09:54:16  vizigoth
 * Updates to enable writing AAF files from objects with AMP metadata.
 *
 * Revision 1.6  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.5  2011/01/26 11:49:03  vizigoth
 * Completed common method testing.
 *
 * Revision 1.4  2011/01/25 14:18:28  vizigoth
 * Class instantiation tests with all properties present completed.
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
 * Revision 1.3  2010/05/20 18:52:14  vizigoth
 * Adding support for Avid extensions.
 *
 * Revision 1.2  2010/05/19 22:22:58  vizigoth
 * Adding Avid extensions.
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
 * Revision 1.1  2007/11/13 22:09:07  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import java.nio.ByteBuffer;

import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.avid.AvidConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.model.TaggedValue;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements a user defined tag and value pair.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3f00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TaggedValue",
		  description = "The TaggedValue class specifies a user-defined tag and value.",
		  symbol = "TaggedValue")
public class TaggedValueImpl
	extends 
		InterchangeObjectImpl
	implements 
		TaggedValue,
		tv.amwa.maj.extensions.avid.TaggedValue,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -3605581441449580470L;
	
	private String tag;
	private AUID typeDefinition;
	private Serializable value;
	
	public TaggedValueImpl() { }

	/**
	 * <p>Creates and initializes a new tagged value object, which specifies a 
	 * user-defined tag and value.</p>
	 * 
	 * @param name User defined name (tag) of the tagged value object.
	 * @param typeDefinition Type definition of the following value data.
	 * @param value Value of the given type.
	 * 
	 * @throws NullPointerException One or more of the arguments is null.
	 * @throws ClassCastException The given value cannot be cast to a value of the
	 * given type.
	 */
	public TaggedValueImpl(
			@AAFString String name,
			TypeDefinition typeDefinition,
			Serializable value) 
		throws NullPointerException,
			ClassCastException {
		
		if (name == null)
			throw new NullPointerException("Cannot create a new tagged value with a null name.");
		if (typeDefinition == null)
			throw new NullPointerException("Cannot create a new tagged value with a null type definition.");
		if (value == null)
			throw new NullPointerException("Cannot create a new tagged value with a null value instead of a byte array.");	
	
		setTag(name);
		setTypeDefinition(typeDefinition);
		
		// Test if the object makes a value of the given type.
		typeDefinition.createValue(value);
		this.value = value;
	}
	
	public TaggedValueImpl(
			@AAFString String tag,
			PropertyValue indirectValue) 
		throws NullPointerException {
		
		if (tag == null)
			throw new NullPointerException("Cannot create a new tagged value with a null tag name.");
		if (indirectValue == null)
			throw new NullPointerException("Cannot create a new tagged value with a null indirect value.");
		if (indirectValue.getType() == null)
			throw new NullPointerException("Cannot create a new tagged value with a property value of null type.");
		
		setTag(tag);
		setIndirectValue(indirectValue);
	}

	public TypeDefinition getIndirectTypeDefinition() {

		return Warehouse.lookForType(typeDefinition);
	}

	void setTypeDefinition(
			TypeDefinition indirectType) 
		throws NullPointerException {
		
		if (indirectType == null)
			throw new NullPointerException("Cannot set the type definition for an indirect type value to null.");
		
		this.typeDefinition = indirectType.getAUID().clone();
	}
	
	@MediaProperty(uuid1 = 0x03020102, uuid2 = (short) 0x0901, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Tag",
			aliases = { "Name", "TaggedValueName" },
			typeName = "UTF16String",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x5001,
			symbol = "Tag")
	public String getTag() {

		return tag;
	}

	@MediaPropertySetter("Tag")
	public void setTag(
			String tag) 
		throws NullPointerException {
		
		if (tag == null)
			throw new NullPointerException("Cannot set the tag name of a tagged value with a null value.");
		
		this.tag = tag;
	}

	public final static String initializeTag() {
		
		return "DeafaultTag";
	}
	
	@MediaProperty(uuid1 = 0x03020102, uuid2 = (short) 0x0a01, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "IndirectValue",
			aliases = { "Value", "TaggedValueValue" },
			typeName = "Indirect", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x5003,
			symbol = "IndirectValue")
	public PropertyValue getIndirectValue() {

		return Warehouse.lookForType(typeDefinition).createValue(value);
	}

	@MediaPropertySetter("IndirectValue")
	public void setIndirectValue(
			PropertyValue indirectValue)
		throws NullPointerException {

		if (indirectValue == null)
			throw new NullPointerException("Cannot set the value of this tagged value with a null indirect value.");
		
		if (indirectValue.getType().equals(TypeDefinitions.Indirect))
			indirectValue = (PropertyValue) indirectValue.getValue();
		
		this.typeDefinition = indirectValue.getType().getAUID();
		
		this.value = (Serializable) indirectValue.getValue();
	}

	public final static PropertyValue initializeIndirectValue() {
		
		return TypeDefinitions.Int32.createValue(0);
	}
	
	@Override
	public TaggedValue clone() {
		
		return (TaggedValue) super.clone();
	}
	
	// AVID extension properties - start
	
	private List<TaggedValue> taggedValueAttributeList =
		Collections.synchronizedList(new Vector<TaggedValue>());
	
	@MediaProperty(uuid1 = 0x60958185, uuid2 = (short) 0x47b1, uuid3 = (short) 0x11d4,
			uuid4 = { (byte) 0xa0, 0x1c, 0x00, 0x04, (byte) 0xac, (byte) 0x96, (byte) 0x9f, 0x50 },
			definedName = "TaggedValueAttributeList",
			typeName = "TaggedValueStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "TaggedValueAttributeList",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public List<TaggedValue> getTaggedValueAttributeList() 
		throws PropertyNotPresentException {
		
		if (taggedValueAttributeList.size() == 0)
			throw new PropertyNotPresentException("The optional tagged value attribute list is not present for this tagged value.");
		
		return StrongReferenceVector.getOptionalList(taggedValueAttributeList);
	}
	
	@MediaListAppend("TaggedValueAttributeList")
	public void appendTaggedValueAttributeItem(
			TaggedValue taggedValueAttributeItem) 
		throws NullPointerException {
		
		if (taggedValueAttributeItem == null)
			throw new NullPointerException("Cannot append to the mob attribute list of this package using a null value.");
		
		StrongReferenceVector.append(taggedValueAttributeList, taggedValueAttributeItem);
	}
	
	@MediaListPrepend("TaggedValueAttributeList")
	public void prependTaggedValueAttributeItem(
			TaggedValue taggedValueAttributeItem) 
		throws NullPointerException {
		
		if (taggedValueAttributeItem == null)
			throw new NullPointerException("Cannot prepend to the mob attribute list of this package using a null value.");
	
		StrongReferenceVector.prepend(taggedValueAttributeList, taggedValueAttributeItem);
	}
	
	@MediaListInsertAt("TaggedValueAttributeList")
	public void insertTaggedValueAttributeItem(
			int index,
			TaggedValue taggedValueAttributeItem)
		throws NullPointerException,
			IndexOutOfBoundsException {
			
		if (taggedValueAttributeItem == null)
			throw new NullPointerException("Cannot insert into the mob attribute list of this package using a null value.");
		
		StrongReferenceVector.insert(taggedValueAttributeList, index, taggedValueAttributeItem);
	}
	
	@MediaPropertyCount("TaggedValueAttributeList")
	public int countTaggedValueAttributeList() {
		
		return taggedValueAttributeList.size();
	}
	
	@MediaPropertyClear("TaggedValueAttributeList")
	public void clearTaggedValueAttributeList() {
		
		taggedValueAttributeList.clear();
	}
	
	@MediaListGetAt("TaggedValueAttributeList")
	public TaggedValue getTaggedValueAttributeItemAt(
			int index) 
		throws IndexOutOfBoundsException {
		
		return StrongReferenceVector.getAt(taggedValueAttributeList, index);
	}
	
	@MediaListRemoveAt("TaggedValueAttributeList")
	public void removeTaggedValueAttributeItemAt(
			int index) 
		throws IndexOutOfBoundsException {
		
		StrongReferenceVector.remove(taggedValueAttributeList, index);
	}
	
	private @UInt32 Integer portableObjectClassID = null;
	
//	@MediaProperty(uuid1 = 0x08835f4f, uuid2 = (short) 0x7b28, uuid3 = (short) 0x11d3,
//			uuid4 = { (byte) 0xa0, 0x44, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb },
//			definedName = "PortableObjectClassID",
//			typeName = "UInt32",
//			optional = true,
//			uniqueIdentifier = false,
//			pid = 0,
//			symbol = "PortableObjectClassID",
//			namespace = AvidConstants.AVID_NAMESPACE,
//			prefix = AvidConstants.AVID_PREFIX)
	public @UInt32 int getPortableObjectClassID() 
		throws PropertyNotPresentException {
		
		if (portableObjectClassID == null)
			throw new PropertyNotPresentException("The optional portable object class ID property is not present for this tagged value.");
		
		return portableObjectClassID;
	}
	
//	@MediaPropertySetter("PortableObjectClassID")
	public void setPortableObjectClassID(
			@UInt32 Integer portableObjectClassID) 
		throws IllegalArgumentException {
		
		if (portableObjectClassID == null) {
			this.portableObjectClassID = null;
			return;
		}
		
		if (portableObjectClassID < 0)
			throw new IllegalArgumentException("Cannot set the portable object class ID of this tagged value to a negative value.");
		
		this.portableObjectClassID = portableObjectClassID;
	}
	
	private InterchangeObject portableObject = null;
	
//	@MediaProperty(uuid1 = 0xb6bb5f4e, uuid2 = (short) 0x7b37, uuid3 = (short) 0x11d3,
//			uuid4 = { (byte) 0xa0, 0x44, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb },
//			definedName = "PortableObject",
//			typeName = "AvidStrongReference",
//			optional = true,
//			uniqueIdentifier = false,
//			pid = 0,
//			symbol = "PortableObject",
//			namespace = AvidConstants.AVID_NAMESPACE,
//			prefix = AvidConstants.AVID_PREFIX)
	public InterchangeObject getPortableObject() 
		throws PropertyNotPresentException {
		
		if (portableObject == null)
			throw new PropertyNotPresentException("The optional portable object is not present for this tagged value.");
		
		// TODO use clone here?
		return portableObject;
	}
	
	public void setPortableObject(
			InterchangeObject portableObject) {
		
		// TODO use clone here?
		this.portableObject = portableObject;
	}
	
	// AVID extension properties - end
	
	public byte[] getIndirectValuePersist() 
		throws NotSerializableException, 
			NullPointerException, 
			IllegalPropertyValueException, 
			InsufficientSpaceException {
		
		if ((value == null) || (typeDefinition == null)) return null;
		
		PropertyValue typedValue = getIndirectValue();
		PropertyValue indirectValue = TypeDefinitions.Indirect.createValueFromActualValue(typedValue);
		
		ByteBuffer buffer = ByteBuffer.allocate((int) TypeDefinitions.Indirect.lengthAsBytes(indirectValue));
		TypeDefinitions.Indirect.writeAsBytes(indirectValue, buffer);
		
		buffer.rewind();
		return buffer.array();
	}
	
	public void setIndirectValuePersist(
			byte[] indirectBytes) {
		
		if (indirectBytes == null) {
			value = null;
			typeDefinition = null;
			return;
		}
		
		PropertyValue indirectValue = TypeDefinitions.Indirect.createFromBytes(ByteBuffer.wrap(indirectBytes));
		PropertyValue typedValue = (PropertyValue) indirectValue.getValue();
		
		value = (Serializable) typedValue.getValue();
		typeDefinition = typedValue.getType().getAUID();
	}
}
