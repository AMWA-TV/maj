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
 * $Log: MetadataObjectHandler.java,v $
 * Revision 1.7  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.5  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/03/01 15:18:09  vizigoth
 * Added a generic table for weak reference resolution. Helps with auto generated weak reference targets.
 *
 * Revision 1.3  2009/12/18 17:56:04  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.2  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 */

package tv.amwa.maj.io.xml;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionFixedArray;
import tv.amwa.maj.meta.TypeDefinitionObjectReference;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.meta.TypeDefinitionWeakObjectReference;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.util.Utilities;

// TODO comments
// TODO implementaiton
// TODO test

/**
 * <p>Parse an XML element and its children into {@linkplain tv.amwa.maj.industry.MetadataObject 
 * metadata objects}, which are classes annotated with their XML serialization information.
 * The MAJ API has all the {@linkplain tv.amwa.maj.industry.AAFSpecifiedClasses baseline} classes
 * for AAF and MXF included. Use the {@linkplain tv.amwa.maj.industry.Warehouse
 * warehouse} to add extension objects to be parsed by this method.</p>
 * 
 * <p>THE COMMENTS FOR THIS CLASS ARE INCOMPLETE.</p>
 * 
 * @see tv.amwa.maj.industry.Warehouse#lookForClass(Class)
 * 
 *
 */
public class MetadataObjectHandler 
	extends MasterContentHandler
	implements ContentHandler {

	private StringBuffer characterBuffer = new StringBuffer(65536);
	
	private ClassDefinition definingClass = null;

	private PropertyDefinition scanningValue = null;
	
	private MetadataObject resultInstance;
	
	private String recordEndTag = null;
	
	private MasterContentHandler childHandler = null;
	
	public MetadataObjectHandler() { }
	
	public void characters(
			char[] ch, 
			int start, 
			int length)
		throws SAXException {

		if (scanningValue != null) {
			
			characterBuffer.append(ch, start, length);
		}
		
	}

	public void endElement(
			String uri, 
			String localName, 
			String name)
			throws SAXException {

		if (recordEndTag != null) {
			if (!localName.equals(recordEndTag)) {
				characterBuffer.append("</" + localName + ">\n");
				return;
			}
			recordEndTag = null;
		}
		
		// Reached the end of this metadata object?
		if (uri.equals(getNamespace()) && localName.equals(getElementName())) { // At the end of this object

			if ((definingClass != null) && (resultInstance == null))
				resultInstance = definingClass.createInstance(); // If no instance has been created yet, make an empty one

			if (resultInstance instanceof WeakReferenceTarget)
				WeakReference.registerTarget((WeakReferenceTarget) resultInstance);
			
			setResult(resultInstance);
			
			getXMLReader().setContentHandler(getParentHandler());
			return;
		}
		
		if ((definingClass == null) || (scanningValue == null)) {
			characterBuffer.setLength(0);
			scanningValue = null;
			return; // Try to keep parsing even when the class or property is not known.
		}

		if (resultInstance == null)
			resultInstance = definingClass.createInstance();
		
		// Otherwise, at the end of a property
		TypeDefinition propertyType = scanningValue.getTypeDefinition();
		PropertyValue propertyValue;
		
		try {
			switch (propertyType.getTypeCategory()) {

			// TODO references and structures
			case StrongObjRef:
				TypeDefinitionStrongObjectReference strongType =
					(TypeDefinitionStrongObjectReference) propertyType;
				
				propertyValue = strongType.createValue(childHandler.getResult());
				scanningValue.setPropertyValue(resultInstance, propertyValue);
				break;
			case WeakObjRef:
				if ((uri.equals(CommonConstants.AAF_XML_NAMESPACE)) && (localName.equals("ObjectClass")))
					break;
				TypeDefinitionWeakObjectReference weakType = (TypeDefinitionWeakObjectReference) propertyType;

				Class<?> targetImplementation = weakType.getObjectType().getJavaImplementation();
				String weakReference = characterBuffer.toString().trim();
				
				MetadataObject target = null;
				if (weakReference.startsWith("urn:")) {

					if (ClassDefinition.class.isAssignableFrom(targetImplementation))
						target = Warehouse.lookForClass(Forge.parseAUID(weakReference));
					else if (TypeDefinition.class.isAssignableFrom(targetImplementation))
						target = Warehouse.lookForType(Forge.parseAUID(weakReference));
					else {
//						target = Warehouse.lookup(					
//							(Class<? extends DefinitionObject>) targetImplementation, Forge.parseAUID(weakReference));
						Method referenceResolver = targetImplementation.getMethod("forIdentification", AUID.class);
						target = (MetadataObject) referenceResolver.invoke(
								null, 
								AUIDImpl.parseFactory(weakReference));	
					}
				}
				else {
//					Method referenceResolver = targetImplementation.getMethod("forName", String.class);
//					target = (MetadataObject) referenceResolver.invoke(
//						null, 
//						weakReference);
					if (ClassDefinition.class.isAssignableFrom(targetImplementation))
						target = Warehouse.lookForClass(weakReference);
					else if (TypeDefinition.class.isAssignableFrom(targetImplementation))
						target = Warehouse.lookForType(weakReference);
					else {
//						target = Warehouse.lookup(
//							(Class<? extends DefinitionObject>) targetImplementation, weakReference);
						Method referenceResolver = targetImplementation.getMethod("forName", String.class);
						target = (MetadataObject) referenceResolver.invoke(
							null, 
							weakReference);
					}
				}
				
				propertyValue = weakType.createValue(target);
				scanningValue.setPropertyValue(resultInstance, propertyValue);
				break;
				
			case Indirect:
				String typeName = getAttributeValueThisElement(CommonConstants.AAF_XML_PREFIX + ":actualType");
				TypeDefinition indirectType = Warehouse.lookForType(typeName);
				PropertyValue indirectValue = indirectType.createValue(characterBuffer.toString());
				propertyValue = propertyType.createValue(indirectValue);
				scanningValue.setPropertyValue(resultInstance, propertyValue);
				break;
				
			case Opaque:
				try {
					AUID opaqueTypeID = 
						Forge.parseAUID(getAttributeValueThisElement(CommonConstants.AAF_XML_PREFIX + ":actualType"));
					byte[] valueBytes = Utilities.hexStringToBytes(characterBuffer.toString());
					ByteBuffer opaqueBuffer = ByteBuffer.allocate(16 + valueBytes.length);
					if (getAttributeValueThisElement(
							CommonConstants.AAF_XML_PREFIX + ":byteOrder").equals("BigEndian"))
						opaqueBuffer.order(ByteOrder.BIG_ENDIAN);
					else
						opaqueBuffer.order(ByteOrder.LITTLE_ENDIAN);
					if (opaqueTypeID.isUniversalLabel()) {
						opaqueBuffer.put(opaqueTypeID.getAUIDValue(), 8, 8);
						opaqueBuffer.put(opaqueTypeID.getAUIDValue(), 0, 8);
					}
					else
						opaqueBuffer.put(opaqueTypeID.getAUIDValue());
					opaqueBuffer.put(valueBytes);
					opaqueBuffer.rewind();
					propertyValue = propertyType.createValue(opaqueBuffer);
					scanningValue.setPropertyValue(resultInstance, propertyValue);		
				}
				catch (ParseException pe) { }
				break;
			case FixedArray:
			case VariableArray:
			case Set:
				if (TypeDefinitions.DataValue.equals(propertyType))
					propertyValue = propertyType.createValue(characterBuffer.toString());
				else
					propertyValue = propertyType.createValue(childHandler.getResult());
				scanningValue.setPropertyValue(resultInstance, propertyValue);
				break;
				
			default:
				if (scanningValue.getName().equals("ByteOrder")) {
					if (characterBuffer.toString().equals("BigEndian"))
						propertyValue = TypeDefinitions.UInt16.createValue(
							tv.amwa.maj.enumeration.ByteOrder.Big.getAAFByteOrderCode());
					else
						propertyValue = TypeDefinitions.UInt16.createValue(
								tv.amwa.maj.enumeration.ByteOrder.Little.getAAFByteOrderCode());						
				}
				else
					propertyValue = propertyType.createValue(characterBuffer.toString());
				scanningValue.setPropertyValue(resultInstance, propertyValue);
				break;
			}
		}
		catch (Exception e) {
			System.err.println("When parsing XML, unable to set the value of property " + definingClass.getName() + "." + scanningValue.getName() +
					" because of a " + e.getClass().getName() + ": " + e.getMessage());
		}
		
		scanningValue = null;
		characterBuffer.setLength(0);
	}

	public void startElement(
			String uri, 
			String localName, 
			String name,
			Attributes atts)
		throws SAXException {
		
		if (definingClass == null) return; // Class was not found ... keep going
		
		setAttributes(atts);
		
		if (recordEndTag != null) {
			characterBuffer.append("<" + localName + ">");
			return;
		}
		
		try {
			scanningValue = definingClass.lookupPropertyDefinition("{" + uri + "}" + localName);
		}
		catch (BadParameterException bpe) { 
			scanningValue = null;
		}
		
		try {
			if (scanningValue == null)
				scanningValue = definingClass.lookupPropertyDefinition(localName);
		}
		catch (BadParameterException bpe) {
			System.err.println("Unable to resolve property " + definingClass.getName() + "." + localName + ".");
			return;
		}
		
		characterBuffer.setLength(0);
		
		switch (scanningValue.getTypeDefinition().getTypeCategory()) {
		
		case StrongObjRef:
			childHandler = new ReferenceHandler(this, uri, localName, atts);
			
			getXMLReader().setContentHandler(childHandler);
			break;
		case WeakObjRef:
			break;
		case Set:
			TypeDefinition setElementType =
				((TypeDefinitionSet) scanningValue.getTypeDefinition()).getElementType();
			if (setElementType instanceof TypeDefinitionObjectReference)
				childHandler = new ReferenceListHandler(this, uri, localName, atts);
			else
				childHandler = new ArrayListHandler(this, uri, localName, atts, setElementType);
			
			getXMLReader().setContentHandler(childHandler);	
			break;
		case FixedArray:
			TypeDefinition fixedElementType = 
				((TypeDefinitionFixedArray) scanningValue.getTypeDefinition()).getType();
			childHandler = new ArrayListHandler(this, uri, localName, atts, fixedElementType);
			
			getXMLReader().setContentHandler(childHandler);			
			break;
		case VariableArray:
			if (TypeDefinitions.DataValue.equals(scanningValue.getTypeDefinition()))
				break;
			TypeDefinition arrayElementType = 
				((TypeDefinitionVariableArray) scanningValue.getTypeDefinition()).getType();
			if (arrayElementType instanceof TypeDefinitionObjectReference)
				childHandler = new ReferenceListHandler(this, uri, localName, atts);
			else
				childHandler = new ArrayListHandler(this, uri, localName, atts, arrayElementType);
			
			getXMLReader().setContentHandler(childHandler);
			break;
		case Record:
			recordEndTag = localName;
			break;
		default:
				break;
		}
	}

	@Override
	void setElementName(
			String namespace,
			String elementName) {
		
		super.setElementName(namespace, elementName);
		
		try {
			String fullElementName = getFullElementName();
			if (fullElementName.endsWith("WeakReference"))
				fullElementName = fullElementName.substring(0, fullElementName.length() - 13);
			definingClass = Warehouse.lookForClass(fullElementName);
			if (definingClass != null) return;
		}
		catch (IllegalArgumentException iae) { }	
		
		try {
			definingClass = Warehouse.lookForClass(elementName);
			if (definingClass != null) return;
		}
		catch (IllegalArgumentException iae2) { }	

		System.err.println("Unable to resolve XML reference to " + elementName + ".");
	}
	
	class ReferenceHandler
		extends MasterContentHandler
		implements ContentHandler {
	
		MetadataObject mdObject = null;
		MetadataObjectHandler currentHandler = null;
		
		public ReferenceHandler(
				MasterContentHandler parentHandler,
				String namespace,
				String elementName,
				Attributes attributes) { 
	
			setParentHandler(parentHandler);
			setElementName(namespace, elementName);
			setAttributes(attributes);
		}
		
		public void characters(char[] ch, int start, int length)
				throws SAXException {
	
			// Does nothing .. throw away characters inbetween list entries
		}
	
		public void endElement(
				String uri, 
				String localName, 
				String name)
			throws SAXException {
			
			getXMLReader().setContentHandler(getParentHandler());
			setResult(currentHandler.getResult());
			getParentHandler().endElement(uri, localName, name);
		}
	
		public void startElement(
				String uri, 
				String localName, 
				String name,
				Attributes atts) 
			throws SAXException {
			
			currentHandler = new MetadataObjectHandler();
			currentHandler.setElementName(uri, localName);
			currentHandler.setParentHandler(this);
			currentHandler.setAttributes(atts);
			
			getXMLReader().setContentHandler(currentHandler);
		}
	}

	
	class ReferenceListHandler
		extends MasterContentHandler
		implements ContentHandler {

		List<MetadataObject> list = new Vector<MetadataObject>();
		MetadataObjectHandler currentHandler = null;
		
		public ReferenceListHandler(
				MasterContentHandler parentHandler,
				String namespace,
				String elementName,
				Attributes attributes) { 

			setParentHandler(parentHandler);
			setElementName(namespace, elementName);
			setAttributes(attributes);
		}
		
		public void characters(char[] ch, int start, int length)
				throws SAXException {

			// Does nothing .. throw away characters inbetween list entries
		}

		public void endElement(
				String uri, 
				String localName, 
				String name)
			throws SAXException {

			if (currentHandler != null)
				list.add((MetadataObject) currentHandler.getResult());
			
			getXMLReader().setContentHandler(getParentHandler());
			setResult(list);
			getParentHandler().endElement(uri, localName, name);
		}

		public void startElement(
				String uri, 
				String localName, 
				String name,
				Attributes atts) 
			throws SAXException {

			if (currentHandler != null)
				list.add((MetadataObject) currentHandler.getResult());
			
			currentHandler = new MetadataObjectHandler();
			currentHandler.setElementName(uri, localName);
			currentHandler.setParentHandler(this);
			currentHandler.setAttributes(atts);
			
			getXMLReader().setContentHandler(currentHandler);
		}
	}

	class ArrayListHandler
		extends MasterContentHandler
		implements ContentHandler {

		List<PropertyValue> list = new Vector<PropertyValue>();
		String endTag = null;
		TypeDefinition arrayElementType;

		public ArrayListHandler(
				MasterContentHandler parentHandler,
				String namespace,
				String elementName,
				Attributes attributes,
				TypeDefinition arrayElementType) { 

			setParentHandler(parentHandler);
			setElementName(namespace, elementName);
			setAttributes(attributes);
			this.arrayElementType = arrayElementType;
		}

		public void characters(
				char[] ch, 
				int start, 
				int length)
			throws SAXException {

			if (endTag != null)
				characterBuffer.append(ch, start, length);
		}

		public void endElement(
				String uri, 
				String localName, 
				String name)
		throws SAXException {
			
			if (localName.equals(getElementName())) {

				getXMLReader().setContentHandler(getParentHandler());
				setResult(list);
				getParentHandler().endElement(uri, localName, name);	
				return;
			}
			
			if ((endTag != null) && (localName.equals(endTag))) {
				try {
					PropertyValue propertyValue = arrayElementType.createValue(characterBuffer.toString());
					list.add(propertyValue);
					characterBuffer.setLength(0);
					endTag = null;
				}
				catch (Exception e) {
					System.err.println("Unable to create an array element of type " + arrayElementType.getName() + 
							" for property " + definingClass.getName() + "." + scanningValue.getName() + " because of a " +
							e.getClass().getName() + ": " + e.getMessage());
				}
				return;
			}
			
			characterBuffer.append("</" + name + ">");
		}

		public void startElement(
				String uri, 
				String localName, 
				String name,
				Attributes atts) 
		throws SAXException {

			// Assumes any element is a list item container
			if (endTag == null) {
				endTag = localName;
				characterBuffer.setLength(0);
			}
			else
				characterBuffer.append("<" + name + ">");
		}
	}
}
