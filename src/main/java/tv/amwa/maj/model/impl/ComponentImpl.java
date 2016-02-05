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
 * $Log: ComponentImpl.java,v $
 * Revision 1.4  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/02/10 23:57:35  vizigoth
 * Improvements to create and mod time method names in Package to match meta dictionary and other minor fixes.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.3  2008/01/27 11:14:42  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.2  2007/12/04 13:04:48  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:27  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.model.impl;

/* 
 * In the first attempt at this implementation, all the StrongReferenceVectors were stored as maps.
 * However, as the uniqueness of keys for these maps cannot be guaranteed, these vectors have been
 * turned into lists. Be aware that any attribute, user comment or KLV data key may occur more than
 * once in any one list.
 */

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.model.Component;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.KLVData;
import tv.amwa.maj.model.TaggedValue;

/** 
 * <p>Implements an essence element.</p>
 *
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#ComponentStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ComponentStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0200,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Component",
		  description = "The Component class represents an essence element.",
		  symbol = "Component",
		  isConcrete = false)
public class ComponentImpl
	extends InterchangeObjectImpl
		implements
			Component,
			tv.amwa.maj.extensions.quantel.QComponent,
			Cloneable,
			Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8957903353705107217L;
	
	private WeakReference<DataDefinition> componentDataDefinition;
	private long componentLength = 0;
	private boolean lengthPresent = false;
	private List<KLVData> componentKLVData = Collections.synchronizedList(new Vector<KLVData>());
	private List<TaggedValue> componentUserComments = Collections.synchronizedList(new Vector<TaggedValue>());
	private List<TaggedValue> componentAttributes = Collections.synchronizedList(new Vector<TaggedValue>());
	
	@MediaProperty(uuid1 = 0x03010210, uuid2 = (short) 0x0800, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "ComponentAttributes",
			aliases = { "Attributes" },
			typeName = "TaggedValueStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0205,
			symbol = "ComponentAttributes")
	public List<TaggedValue> getComponentAttributes() 
		throws PropertyNotPresentException {
		
		if (componentAttributes.size() == 0)
			throw new PropertyNotPresentException("No attributes are present for this component.");

		return StrongReferenceVector.getOptionalList(componentAttributes);
	}

	public void appendComponentAttribute(
			String name,
			String value)
		throws NullPointerException {

		if (name == null) 
			throw new NullPointerException("Cannot set the name of a tagged value attribute with a null value.");
		if (value == null)
			throw new NullPointerException("Cannot set the value of a tagged value attribute with a null value.");

		TaggedValue taggedValue = new TaggedValueImpl(
				name, 
				TypeDefinitions.UTF16String, 
				value); 
		StrongReferenceVector.appendNoClone(componentAttributes, taggedValue);

	}

	@MediaListAppend("ComponentAttributes")
	public void appendComponentAttribute(
			TaggedValue componentAttribute)
		throws NullPointerException {
		
		if (componentAttribute == null)
			throw new NullPointerException("Cannot append a null tagged value to the attributes of this component.");
		
		StrongReferenceVector.append(componentAttributes, componentAttribute);
	}

	@MediaPropertyRemove("ComponentAttributes")
	public void removeComponentAttribute(
			TaggedValue componentAttribute)
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException {

		if (componentAttribute == null)
			throw new NullPointerException("Cannot remove a tagged value attribute from a component using a null value.");
		
		if (componentAttributes.size() == 0)
			throw new PropertyNotPresentException("No attributes are present for this component.");
		
		if (!(componentAttributes.contains(componentAttribute)))
				throw new ObjectNotFoundException("Cannot remove the given tagged value as it is not currentlty contained in the list of attributes of this component.");
		
		StrongReferenceVector.remove(componentAttributes, componentAttribute);
	}

	@MediaPropertyCount("ComponentAttributes")
	public int countComponentAttributes() {

		return componentAttributes.size();
	}
	
	@MediaListPrepend("ComponentAttributes")
	public void prependComponentAttribute(
			TaggedValue componentAttribute) 
		throws NullPointerException {
		
		if (componentAttribute == null)
			throw new NullPointerException("Cannot prepend a null attribute to those of this component.");
		
		StrongReferenceVector.prepend(componentAttributes, componentAttribute);
	}
	
	@MediaPropertyClear("ComponentAttributes")
	public void clearComponentAttributes() {
		
		componentAttributes = Collections.synchronizedList(new Vector<TaggedValue>());;
	}

	@MediaProperty(uuid1 = 0x03020102, uuid2 = (short) 0x1600, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "ComponentUserComments",
			aliases = { "UserComments" },
			typeName = "TaggedValueStrongReferenceVector", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0204,
			symbol = "ComponentUserComments")
	public List<TaggedValue> getComponentUserComments() 
		throws PropertyNotPresentException {

		if (componentUserComments.size() == 0)
			throw new PropertyNotPresentException("No user comments are present for this component.");
		
		return StrongReferenceVector.getOptionalList(componentUserComments);
	}

	public void appendComponentUserComment(
			String name,
			String value)
		throws NullPointerException {

		if (name == null)
			throw new NullPointerException("Cannot set the name of a tagged value user comment for this component with a null value.");
		if (value == null)
			throw new NullPointerException("Cannot set the value of a tagged value user comment for this component with a null value.");

		TaggedValue taggedValue = new TaggedValueImpl(
				name, 
				TypeDefinitions.UTF16String, 
				value);
		StrongReferenceVector.appendNoClone(componentUserComments, taggedValue);
	}

	@MediaListAppend("ComponentUserComments")
	public void appendComponentUserComment(
			TaggedValue userComment) {
		
		if (userComment == null)
			throw new NullPointerException("Cannot append a comment using a null value.");
		
		StrongReferenceVector.append(componentUserComments, userComment);
	}

	@MediaPropertyCount("ComponentUserComments")
	public int countComponentUserComments() {

		return componentUserComments.size();
	}

	@MediaPropertyRemove("ComponentUserComments")
	public void removeComponentUserComment(
			TaggedValue userComment)
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException {

		if (userComment == null)
			throw new NullPointerException("Cannot remove a user comment from the set of comments of this component using a null value.");
		
		if (componentUserComments.size() == 0)
			throw new PropertyNotPresentException("No user comments are present for this component.");
		
		if (!(componentUserComments.contains(userComment)))
			throw new ObjectNotFoundException("The given user comment cannot be removed as it is not currently contained in the set of user comments of this component.");
		
		StrongReferenceVector.remove(componentUserComments, userComment);
	}

	@MediaPropertyClear("ComponentUserComments")
	public void clearComponentUserComments() {
		
		componentUserComments = Collections.synchronizedList(new Vector<TaggedValue>());
	}
	
	@MediaListPrepend("ComponentUserComments")
	public void prependComponentUserComment(
			TaggedValue userComment) 
		throws NullPointerException {
		
		if (userComment == null)
			throw new NullPointerException("Cannot add a null user comment to this component.");
		
		StrongReferenceVector.prepend(componentUserComments, userComment);
	}
	
	@MediaProperty(uuid1 = 0x04070100, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ComponentDataDefinition",
			aliases = { "DataDefinition" },
			typeName = "DataDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0201,
			symbol = "ComponentDataDefinition")
	public DataDefinition getComponentDataDefinition() {

		return componentDataDefinition.getTarget();
	}

	@MediaPropertySetter("ComponentDataDefinition")
	public void setComponentDataDefinition(
			DataDefinition componentDataDefinition)
		throws NullPointerException {

		if (componentDataDefinition == null)
			throw new NullPointerException("Cannot set the data definition of this component with a null value.");

		this.componentDataDefinition = new WeakReference<DataDefinition>(componentDataDefinition);
	}

	public final static DataDefinition initializeComponentDataDefinition() {
		
		return DataDefinitionImpl.forName("Unknown");
	}
	
	@MediaProperty(uuid1 = 0x03010210, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ComponentKLVData",
			aliases = { "KLVData" },
			typeName = "KLVDataStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0203,
			symbol = "ComponentKLVData")
	public List<KLVData> getComponentKLVData() 
		throws PropertyNotPresentException {

		if (componentKLVData.size() == 0)
			throw new PropertyNotPresentException("No KLV data values are present for this component.");
		
		return StrongReferenceVector.getOptionalList(componentKLVData);
	}

	@MediaListAppend("ComponentKLVData")
	public void appendComponentKLVData(
			KLVData klvData)
		throws NullPointerException {

		if (klvData == null)
			throw new NullPointerException("Cannot add to the list of KLV data items with a null value.");
		
		StrongReferenceVector.append(componentKLVData, klvData);
	}

	@MediaPropertyCount("ComponentKLVData")
	public int countComponentKLVData() {

		return componentKLVData.size();
	}

	@MediaPropertyRemove("ComponentKLVData")
	public void removeComponentKLVData(
			KLVData klvData)
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException {

		if (klvData == null)
			throw new NullPointerException("The given KLV data item is null and so cannot be removed from the list of KLV data items of the component.");

		if (componentKLVData.size() == 0)
			throw new PropertyNotPresentException("No KLV data values are present for this component.");
		
		if (!(componentKLVData.contains(klvData)))
			throw new ObjectNotFoundException("The given KLV data item cannot be removed from the list of KLV data items of this component as it is not currently contained.");

		StrongReferenceVector.remove(componentKLVData, klvData);
	}
	
	@MediaListPrepend("ComponentKLVData")
	public void prependComponentKLVData(
			KLVData klvData) 
		throws NullPointerException {
		
		if (klvData == null)
			throw new NullPointerException("Cannot prepend a null KLV data value to this component.");
		
		StrongReferenceVector.prepend(componentKLVData, klvData);
	}

	@MediaPropertyClear("ComponentKLVData")
	public void clearComponentKLVData() {
		
		componentKLVData = Collections.synchronizedList(new Vector<KLVData>());
	}
	
	@MediaProperty(uuid1 = 0x07020201, uuid2 = (short) 0x0103, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ComponentLength",
			aliases = { "Length" },
			typeName = "LengthType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0202,
			symbol = "ComponentLength")
	public long getComponentLength()
			throws BadPropertyException {

		if (lengthPresent == false)
			throw new BadPropertyException("The optional length property is not set in the current context of this component.");
		
		return componentLength;
	}

	public void setComponentLength(
			long componentLength)
		throws BadLengthException {

		if (componentLength < -1l) 
			throw new BadLengthException("Cannot set a negative length value for a component except in open MXF headers.");
		
		this.componentLength = componentLength;
	}

	@MediaPropertySetter("ComponentLength")
	public void setComponentLengthFromStream(
			long componentLength)
		throws BadLengthException {

		if (componentLength < -1l) 
			throw new BadLengthException("Cannot set a negative length value for a component except in open MXF headers.");
		
		this.lengthPresent = true;
		this.componentLength = componentLength;
	}

	public boolean getLengthPresent() { 
		
		return lengthPresent; 
	}

	public void setLengthPresent(
			boolean lengthPresent) {
		
		this.lengthPresent = lengthPresent;
	}
	
	// Begin - Quantel extensions
	
	private String jupiterID = null;
	private Integer jupiterClipOffset = null;
	private String editName = null;
	
    @MediaProperty(uuid1 = 0x45e12b0b, uuid2 = (short) 0xac1d, uuid3 = (short) 0x43ae,
        uuid4 = { (byte) 0x9d, (byte) 0xb4, (byte) 0x36, (byte) 0xf0, (byte) 0x65, (byte) 0xaf, (byte) 0xc3, (byte) 0xeb },
        definedName = "Jupiter ID",
        symbol = "Jupiter_ID",
        aliases = { "Jupiter_ID" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public String getJupiterID()
		throws PropertyNotPresentException {
		
		if (jupiterID == null)
			throw new PropertyNotPresentException("The optional Jupiter ID property is not present for this Quantel component.");
		
		return jupiterID;
	}
	
	@MediaPropertySetter("Jupiter ID")
	public void setJupiterID(
			String jupiterID) {
		
		this.jupiterID = jupiterID;
	}
	
    @MediaProperty(uuid1 = 0x9a14eb95, uuid2 = (short) 0xb212, uuid3 = (short) 0x4d11,
        uuid4 = { (byte) 0xbc, (byte) 0x5d, (byte) 0xb2, (byte) 0xf3, (byte) 0x6b, (byte) 0x37, (byte) 0xba, (byte) 0x2b },
        definedName = "Jupiter Clip Offset",
        symbol = "Jupiter_Clip_Offset",
        aliases = { "Jupiter_Clip_Offset" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getJupiterClipOffset()
		throws PropertyNotPresentException {
		
		if (jupiterClipOffset == null)
			throw new PropertyNotPresentException("The optional jupiter clip offset property is not present for this Quantel component.");
		
		return jupiterClipOffset;
	}
	
	@MediaPropertySetter("Jupiter Clip Offset")
	public void setJupiterClipOffset(
			@Int32 Integer jupiterClipOffset) {
		
		this.jupiterClipOffset = jupiterClipOffset;
	}
	
    @MediaProperty(uuid1 = 0xfedcba98, uuid2 = (short) 0x2267, uuid3 = (short) 0x11d3,
        uuid4 = { (byte) 0x8a, (byte) 0x4c, (byte) 0x00, (byte) 0x50, (byte) 0x04, (byte) 0x0e, (byte) 0xf7, (byte) 0xd2 },
        definedName = "EditName",
        symbol = "EditName",
        aliases = { "EditName" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public String getEditName()
		throws PropertyNotPresentException {
		
		if (editName == null)
			throw new PropertyNotPresentException("The optional edit name property is not present for thie Quantel component.");
		
		return editName;
	}
	
	@MediaPropertySetter("EditName")
	public void setEditName(
			String editName) {
		
		this.editName = editName;
	}
	
	// End - Quantel extensions
	
	public String getComment() {
		
		return "local component persistent id: " + getPersistentID();
	}
	
	public Component clone() {
		
		return (Component) super.clone();
	}
} 
