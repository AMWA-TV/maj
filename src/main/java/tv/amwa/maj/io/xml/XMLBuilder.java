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
 * $Log: XMLBuilder.java,v $
 * Revision 1.15  2011/10/05 17:14:32  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.14  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.13  2011/01/26 11:50:34  vizigoth
 * Completed common method testing.
 *
 * Revision 1.12  2011/01/19 21:58:19  vizigoth
 * Preparing for change to Stream seialization.
 *
 * Revision 1.11  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.10  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.9  2010/11/08 16:41:39  vizigoth
 * Align better with Reg XML and omit XML 1.0 rather than 1.1.
 *
 * Revision 1.8  2010/06/16 14:56:41  vizigoth
 * Towards better Reg XML support for complete documents ... still work in progress.
 *
 * Revision 1.7  2010/03/19 10:16:41  vizigoth
 * Removed some old test code in the main method and added more generic main method.
 *
 * Revision 1.6  2009/12/18 17:56:04  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.5  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/15 14:16:08  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.1  2007/11/13 22:14:57  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.io.xml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionIndirect;
import tv.amwa.maj.meta.TypeDefinitionInteger;
import tv.amwa.maj.meta.TypeDefinitionObjectReference;
import tv.amwa.maj.meta.TypeDefinitionOpaque;
import tv.amwa.maj.meta.TypeDefinitionRename;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.meta.impl.TypeDefinitionEnumerationImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionExtendibleEnumerationImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionFixedArrayImpl;
import tv.amwa.maj.model.ApplicationPluginObject;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.model.impl.InterchangeObjectImpl;
import tv.amwa.maj.util.Utilities;

// TODO comments
// TODO test

/** 
 *
 * <p>A class of static utility methods to assist with the process of serializing and
 * deserializing {@linkplain tv.amwa.maj.industry.MediaClass AAF classes} to/from XML representations. 
 * This representation is the core human-readable form used by this Java implementation of the 
 * AAF object model. To serialize a {@linkplain tv.amwa.maj.industry.MetadataObject metadata object} or 
 * {@linkplain XMLSerializable XML serializable} object to an XML 
 * document or fragment, call {@link #toXML(MetadataObject)} or {@link #toXMLNonMetadata(XMLSerializable)}. To create an 
 * object instance from an XML input source, call {@link #createFromXML(InputSource)}.</p>
 * 
 * <p>The <em>append</em> and <em>set</em> static methods of this class allow DOM tree XML representations 
 * to be constructed. This is useful when a client class needs to override or add to default serialization behaviour by
 * implementing the {@link XMLSerializable#appendXMLChildren(Node)}.</p>
 * 
 * <p>The documents attached to the fragments created by {@link #createDocumentFragment()} may
 * have stream data attached to them if the {@link #appendStream(Node, String, String, String, String, Stream)}
 * method is called on one of the document's descendants. In this case, the number of streams
 * currently attached to the node is stored as an {@link Integer} value with key 
 * "<code>entityCount</code>" and each stream is stored with the key "<code>stream_</code><em>i</em>"
 * where <em>i</em> is the zero-based index of the stream.</p>
 *
 *
 *
 */
public final class XMLBuilder {
	
	// private final static AUID dataValueTypeId = TypeDefinitionWarehouse.forName("DataValue").getAUID();
	
	private XMLBuilder() { }
	
	/**
	 * <p>Create a new element as a child of the given element and according to the 
	 * AAF name space.</p>
	 *
	 * @param parent Element to create a child element for.
	 * @param namespace Namespace if which the element is defined.
	 * @param prefix Short prefix name for the namespace, or <code>null</code> if no prefix is required.
	 * @param elementName Unqualified name of the new element.
	 * 
	 * @return Newly created element within the given namespace.
	 */
	public final static Element createChild(
			Node parent,
			String namespace,
			String prefix,
			String elementName) {
		
		Document document = parent.getOwnerDocument();
		Element childElement = document.createElementNS(namespace, elementName);
		childElement.setPrefix(prefix);
		
		if (parent instanceof Element)
			((Element) parent).appendChild(childElement);
		if (parent instanceof DocumentFragment)
			((DocumentFragment) parent).appendChild(childElement);
		return childElement;
	}
	
	/**
	 * <p>Append an element with a long value to the given parent node.</p>
	 *
	 * @param parent Parent to append a new child element to.
	 * @param namespace Namespace in which the element is defined.
	 * @param prefix Short prefix name for the namespace, or <code>null</code> if no prefix is required.
	 * @param elementName Name of the element to append.
	 * @param value Value for the new element.
	 */
	public final static Element appendElement(
			Node parent,
			String namespace,
			String prefix,
			String elementName,
			long value) {
		
		Document document = parent.getOwnerDocument();
		
		Element element = document.createElementNS(namespace, elementName);
		element.setPrefix(prefix);
		element.setTextContent(Long.toString(value));
		parent.appendChild(element);
		
		return element;
	}
	
	/**
	 * <p>Append an element with a string value to the given parent node.</p>
	 *
	 * @param parent Parent to append a new child element to.
	 * @param namespace Namespace in which the element is defined.
	 * @param prefix Short prefix name for the namespace, or <code>null</code> if no prefix is required.
	 * @param elementName Name of the element to append.
	 * @param value Value for the new element.
	 * 
	 * @return Newly created child element.
	 */
	public final static Element appendElement(
			Node parent,
			String namespace,
			String prefix,
			String elementName,
			String value) {
		
		Document document = parent.getOwnerDocument();
		
		Element element = document.createElementNS(namespace, elementName);
		element.setPrefix(prefix);
		Text text = document.createTextNode(value);
		element.appendChild(text);
		parent.appendChild(element);
		
		return element;
	}
	
	/**
	 * <p>Create a document fragment that can be used to build a serialized version of an object
	 * with.</p>
	 *
	 * @return XML document fragment.
	 */
	public final static DocumentFragment createDocumentFragment() {
		
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			return document.createDocumentFragment();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * <p>Transform a DOM node into its textual XML representation.</p>
	 *
	 * @param node Node to transform to an XML string.
	 * @return String representation of the XML tree represented by the given DOM node.
	 */
	public final static String transformNodeToString(
			Node node) {
		
		StringWriter writer = new StringWriter();
		
		try {
			node.normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			try {
				transformerFactory.setAttribute("indent-number", 2);
			}
			catch (IllegalArgumentException iae) { /* Required for Xerces Xalan in JBoss */ }
			
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			writer = new StringWriter();
			transformer.transform(new DOMSource(node), new StreamResult(writer));

			return writer.toString();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * <p>Creates and returns an XML fragment by serializing to XML the given object that implements 
	 * {@link XMLSerializable} and is not a core metadata object. This method returns <code>null</code> 
	 * if an exception occurs in the serialization process.</p>
	 *
	 * @param item Item to serialize as an XML fragment.
	 * @return String representation of the given item as an XML fragment.
	 */
	public final static String toXMLNonMetadata(
			XMLSerializable item) {
		
		DocumentFragment fragment = createDocumentFragment();
		
		item.appendXMLChildren(fragment);

		/* String abbreviation;
		if ((prefix != null) && (prefix.length() > 0))
			abbreviation = "xmlns:" + prefix;
		else
			abbreviation = "xmlns";
		
		NodeList nodes = fragment.getChildNodes();
		for ( int x = 0 ; x < nodes.getLength() ; x++ )
			if (nodes.item(x) instanceof Element)
				((Element) nodes.item(x)).setAttributeNS(
						XMLConstants.XMLNS_ATTRIBUTE_NS_URI, 
						abbreviation,
						namespace); */
		
		fragment.normalize();

		return transformNodeToString(fragment);
	}
	
	/**
	 * <p>Create a string representation of a {@linkplain tv.amwa.maj.industry.MetadataObject metadata
	 * object}. The object is expected to be an instance of a class annotated with the 
	 * {@link tv.amwa.maj.industry.MediaClass AAFClass} and {@link tv.amwa.maj.industry.MediaProperty AAFProperty}
	 * annotations. Metadata in these annotations is used to create the XML tags and serialize 
	 * values of annotation-specified types to character data representations.</p>
	 * 
	 * <p>Note that the returned XML representation of the given metadata object will only include
	 * values for optional properties when they are present.</p>
	 * 
	 * @param metadataObject Object to serialize as XML.
	 * @return XML representation of the given metadata object.
	 * 
	 * @throws NullPointerException The metadata object is <code>null</code>.
	 * @throws IllegalArgumentException The given metadata object does not have an associated
	 * {@linkplain tv.amwa.maj.meta.ClassDefinition class definition}.
	 */
	public final static String toXML(
			MetadataObject metadataObject) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (metadataObject == null)
			throw new NullPointerException("Cannot create an XML representation of a null value.");
		
		DocumentFragment fragment = createDocumentFragment();
		
		appendObject(fragment, metadataObject);
		
		/* NodeList nodes = fragment.getChildNodes();
		for ( int x = 0 ; x < nodes.getLength() ; x++ )
			if (nodes.item(x) instanceof Element)
				((Element) nodes.item(x)).setAttributeNS(
						XMLConstants.XMLNS_ATTRIBUTE_NS_URI, 
						"xmlns:aaf",
						AAF_XML_NAMESPACE); */

		
		String asXML = transformNodeToString(fragment);
		
		String docType = makeDoctype(fragment, "toString");
		if (docType != null) {
			StringBuffer withDocType = new StringBuffer(asXML.length() + docType.length() + 1);
			withDocType.append(asXML.substring(0, 39));
			withDocType.append(docType);
			withDocType.append(asXML.substring(39));
			return withDocType.toString();
		}
		else
			return asXML;
	}
	
	public final static void appendObject(
			Node parent,
			MetadataObject metadataObject) {
		
		// 
		if (metadataObject == null) {
			System.err.println("Unable to serialize to XML a child of element " + parent.getNodeName() + " at this time.");
			return;
		}
					
		Document document = parent.getOwnerDocument();
		ClassDefinition metaClass = Warehouse.lookForClass(metadataObject.getClass());
		Map<? extends tv.amwa.maj.meta.PropertyDefinition, ? extends PropertyValue> values =
			metaClass.getProperties(metadataObject);
				
		Element element = null;
		if (metadataObject instanceof InterchangeObject) {
			InterchangeObject interchangeObject = (InterchangeObject) metadataObject;
			if (interchangeObject.countApplicationPlugins() > 0) {
				for ( ApplicationPluginObject plugin : interchangeObject.getApplicationPlugins() ) {
					ClassDefinition pluginObjectClass = plugin.getObjectClass();
					if (!pluginObjectClass.getAUID().equals(CommonConstants.ApplicationPluginObjectID)) {
						ExtensionScheme applicationScheme = plugin.getApplicationScheme();
						element = document.createElementNS(applicationScheme.getSchemeURI(), pluginObjectClass.getSymbol());
						element.setPrefix(applicationScheme.getPreferredPrefix());
						break;
					}
				}
			}
		}
		
		if (element == null) {
			element = document.createElementNS(metaClass.getNamespace(), metaClass.getSymbol());
			element.setPrefix(metaClass.getPrefix());
		}
		
		if ((metadataObject instanceof WeakReferenceTarget) && // Exclude meta definitions
				(!(metadataObject instanceof tv.amwa.maj.meta.MetaDefinition))) {
			setAttribute(
					element, 
					metaClass.getNamespace(),
					metaClass.getPrefix(),
					"uid", 
					((WeakReferenceTarget) metadataObject).getWeakTargetReference());
		}

		if (metadataObject instanceof XMLSerializable) {
			String comment = ((XMLSerializable) metadataObject).getComment();
			if (comment != null)
				appendComment(element, comment);
		}
		
		for ( tv.amwa.maj.meta.PropertyDefinition alienProperty : values.keySet() ) {
			
			PropertyDefinition property = alienProperty;

			PropertyValue value = values.get(property);
			
			if (property.getIsXMLAttribute()) {
				setAttribute(element, property.getNamespace(), property.getPrefix(), property.getSymbol(), value);
				continue;
			}
			
			if (property.getIsXMLCDATA()) {
				
				if (property.getTypeDefinition().getTypeCategory() != TypeCategory.StrongObjRef) {
					String stringValue = propertyValueToString(value);
					if (stringValue != null) {
						Text textNode = document.createTextNode(propertyValueToString(value));
						element.appendChild(textNode);
					}
				}
				else {
					
					appendObject(element, (MetadataObject) value.getValue());
				}
				
				continue;
			}
			
			if (property.getFlattenXML()) {
				
				appendValue(element, property.getNamespace(), property.getPrefix(), property.getSymbol(), value);
				Node lastChild = element.getLastChild();
				NodeList lastChildContent = lastChild.getChildNodes();
				element.removeChild(lastChild);
				
				for ( int u = 0 ; u < lastChildContent.getLength() ; u++) {

					Element childElement = (Element) lastChild.cloneNode(false);
					Node branch = lastChildContent.item(u);
					NodeList leaves = branch.getChildNodes();
					
					for ( int v = 0 ; v < leaves.getLength() ; v++ )
						childElement.appendChild(leaves.item(v));
					
					NamedNodeMap attributeMap = branch.getAttributes();
					if (attributeMap != null)
						for ( int w = 0 ; w < attributeMap.getLength() ; w++ )
							childElement.setAttributeNodeNS((Attr) attributeMap.item(w).cloneNode(false));
					
					element.appendChild(childElement);
				}
				continue;
			}
			
			if (property.getAUID().equals(InterchangeObjectImpl.ObjectClassPropertyID))
				manageObjectClass(element, metaClass, property, value);
			else
				appendValue(element, property.getNamespace(), property.getPrefix(), property.getSymbol(), value);
		} // property-by-property for loop
		
		if (metadataObject instanceof XMLSerializable) 
			((XMLSerializable) metadataObject).appendXMLChildren(element);
		
		parent.appendChild(element);
	}
	
	@SuppressWarnings("unchecked")
	public final static void appendValue(
			Element element,
			String namespace,
			String prefix,
			String symbolName,
			PropertyValue value) {
		
		Document document = element.getOwnerDocument();
		
		switch (value.getType().getTypeCategory()) {
		
		case String:
		case Int:
			if ((symbolName.equals("ByteOrder")) && (element.getNodeName().endsWith("Preface"))) {
				appendElement(element, namespace, prefix, symbolName, 
						ByteOrder.getByteOrderFromAAFCode((Short) value.getValue()).symbol());
			}
			else
				appendElement(element, namespace, prefix, symbolName, value.getValue().toString());
			break;
			
		case Record:
			Object baseValue = value.getValue();
			if (baseValue instanceof XMLSerializable) {
				Element recordChild = document.createElementNS(namespace, symbolName);
				recordChild.setPrefix(prefix);
				((XMLSerializable) baseValue).appendXMLChildren(recordChild);
				element.appendChild(recordChild);
			}
			else
				appendElement(element, namespace, prefix, symbolName, baseValue.toString());
			break;
		
		case WeakObjRef:
			if (value.getValue() instanceof WeakReferenceTarget) {
				appendElement(
						element, 
						namespace,
						prefix,
						symbolName, 
						((WeakReferenceTarget) value.getValue()).getWeakTargetReference());
			}
			break;
			
		case StrongObjRef:
			Element strongElement = document.createElementNS(namespace, symbolName);
			strongElement.setPrefix(prefix);
			appendObject(strongElement, (MetadataObject) value.getValue());
			element.appendChild(strongElement);
			break;
			
		case Rename:
			TypeDefinitionRename renamedType = (TypeDefinitionRename) value.getType();
			appendValue(element, namespace, prefix, symbolName, renamedType.getBaseValue(value));
			break;
			
		case Stream: 
			Stream stream = (Stream) value.getValue(); 
			
			appendStream(element, namespace, prefix, symbolName, "stream", stream);
			// TODO consider the else condition
			break;
			
		case VariableArray:
			if (TypeDefinitions.DataValue.equals(value.getType())) {
				List<Byte> dataValueList = (List<Byte>) value.getValue();
				byte[] dataValueBytes = new byte[dataValueList.size()];
				for ( int x = 0 ; x < dataValueBytes.length ; x++ )
					dataValueBytes[x] = dataValueList.get(x);
				appendElement(element, namespace, prefix, symbolName, dataValueBytes);
				break;
			}
					
			TypeDefinitionVariableArray variableArrayType = (TypeDefinitionVariableArray) value.getType();
			TypeDefinition elementType = variableArrayType.getType();
			Element subElement = document.createElementNS(namespace, symbolName);
			subElement.setPrefix(prefix);
			Object[] listValues = variableArrayType.getArray(value);
			for ( Object listValue : listValues) {
				
				switch (elementType.getTypeCategory()) {
				
				case StrongObjRef:
					appendObject(subElement, (MetadataObject) listValue);
					break;
				case WeakObjRef:
					if (listValue instanceof WeakReferenceTarget) 
						appendElement(
								subElement, 
								namespace,
								prefix,
								((TypeDefinitionObjectReference) elementType).getObjectType().getName() + "WeakReference", 
								((WeakReferenceTarget) listValue).getWeakTargetReference());
					break;
				case Character:
					if (variableArrayType.equals(TypeDefinitions.UTF16StringArray))
						appendElement(subElement, namespace, prefix, "Character", (String) listValue);
					else
						appendValue(subElement, namespace, prefix, 
								elementType.getName(), elementType.createValue(listValue));
					break;
				case Int:
					TypeDefinitionInteger varArrayIntType = (TypeDefinitionInteger) elementType;
					long negativeValue = ((Number) listValue).longValue();
					if ((varArrayIntType.isSigned()) || 
							((!varArrayIntType.isSigned()) && (negativeValue >= 0))) {
						appendValue(subElement, namespace, prefix, 
								elementType.getName(), elementType.createValue(listValue));
						break;
					}
					switch (varArrayIntType.getSize()) {
					case 1: 
						negativeValue += 1<<8;
						break;
					case 2:
						negativeValue += 1<<16;
						break;
					case 3:
						negativeValue += 1<<32;
						break;
					case 4:
						break;
					default: break;
					}
					appendValue(subElement, namespace, prefix, 
							elementType.getName(), TypeDefinitions.UInt64.createValue(negativeValue));
					break;
				default:
					appendValue(subElement, namespace, prefix, 
							elementType.getName(), elementType.createValue(listValue));
				break;
				}
			}
			
			element.appendChild(subElement);
			break;
			
		case FixedArray:
			TypeDefinitionFixedArrayImpl fixedArrayType = (TypeDefinitionFixedArrayImpl) value.getType();
			TypeDefinition elementArrayType = fixedArrayType.getType();
			Element fixedSubElement = document.createElementNS(namespace, symbolName);
			fixedSubElement.setPrefix(prefix);
			
			Object[] fixedElements = fixedArrayType.getArray(value);
			for ( Object fixedElement : fixedElements ) {
				appendValue(fixedSubElement, namespace, prefix,
						elementArrayType.getName(), elementArrayType.createValue(fixedElement));
			}
			
			element.appendChild(fixedSubElement);
			break;
			
		case Enum:
			appendElement(element, namespace, prefix, symbolName, 
					((TypeDefinitionEnumerationImpl) value.getType()).getSymbolFromValue(value));
			break;
			
		case ExtEnum:
			appendElement(element, namespace, prefix, symbolName, 
					((TypeDefinitionExtendibleEnumerationImpl) value.getType()).getSymbolFromValue(value));
			break;
			
		case Set:
			if (value.getType().equals(TypeDefinitions.ApplicationPluginObjectStrongReferenceSet)) {
				Set<ApplicationPluginObject> pluginElements = (Set<ApplicationPluginObject>) value.getValue();
				for ( ApplicationPluginObject plugin : pluginElements )
					plugin.appendXMLChildren(element);
				break;
			}
			
			TypeDefinitionSet setType = (TypeDefinitionSet) value.getType();
			TypeDefinition setElementType = setType.getElementType();
			Element setSubElement = document.createElementNS(namespace, symbolName);
			setSubElement.setPrefix(prefix);
			Set<PropertyValue> elementValues = setType.getElements(value);
			for ( PropertyValue elementValue : elementValues ) {
				
				switch (setElementType.getTypeCategory()) {
				
				case StrongObjRef:
					appendObject(setSubElement, (MetadataObject) elementValue.getValue());
					break;
					
				case WeakObjRef:
					if (elementValue.getValue() instanceof WeakReferenceTarget) 
						appendElement(
								setSubElement, 
								namespace,
								prefix,
								((TypeDefinitionObjectReference) setElementType).getObjectType().getName() + "WeakReference", 
								((WeakReferenceTarget) elementValue.getValue()).getWeakTargetReference());
					break;
				case Int:
					TypeDefinitionInteger setIntType = (TypeDefinitionInteger) setElementType;
					long negativeValue = ((Number) elementValue.getValue()).longValue();
					if ((setIntType.isSigned()) || 
							((!setIntType.isSigned()) && (negativeValue >= 0))) {
						appendValue(setSubElement, namespace, prefix, 
								setElementType.getName(), setElementType.createValue(elementValue.getValue()));
						break;
					}
					switch (setIntType.getSize()) {
					case 1: 
						negativeValue += 1<<8;
						break;
					case 2:
						negativeValue += 1<<16;
						break;
					case 3:
						negativeValue += 1<<32;
						break;
					case 4:
						break;
					}
					appendValue(setSubElement, namespace, prefix, 
							setElementType.getName(), TypeDefinitions.UInt64.createValue(negativeValue));
					break;
					
				default:
					appendValue(setSubElement, namespace, prefix, setElementType.getName(), elementValue);
					break;
				}
			}
			
			element.appendChild(setSubElement);
			break;
			
		case Indirect:
			PropertyValue indirectValue = ((TypeDefinitionIndirect) value.getType()).getActualValue(value);
			appendValue(element, namespace, prefix, symbolName, indirectValue);
			Element indirectElement = (Element) element.getLastChild();
			setAttribute(indirectElement, namespace, prefix, "actualType", indirectValue.getType().getName());
			break;
			
		case Opaque:
			TypeDefinitionOpaque opaqueType = (TypeDefinitionOpaque) value.getType();
			try {
				appendElement(element, namespace, prefix, symbolName, opaqueType.getActualData(value));
			}
			catch (Exception e) {
				System.err.println("Unable to create an opaque type data value due to a " + e.getClass().getName() + 
						" for a property named " + symbolName + ": " + e.getMessage());
			}
			
			Element opaqueElement = (Element) element.getLastChild();

			TypeDefinition actualType = 
				Warehouse.lookForType(opaqueType.getActualTypeID(value));
			if (actualType != null)
				appendComment(element, "Local type name for opaque value is: " + actualType.getName());
			
			setAttribute(opaqueElement, namespace, prefix, "actualType", opaqueType.getActualTypeID(value).toString());
			setAttribute(opaqueElement, namespace, prefix, "byteOrder", 
					(opaqueType.getHandle(value).order() == java.nio.ByteOrder.BIG_ENDIAN) ? "BigEndian" : "LittleEndian");
			break;
			
		default:
			break;
		}
	}
	
	private static void manageObjectClass(
			Element element, 
			ClassDefinition propertyClass,
			PropertyDefinition property,
			PropertyValue value) {
		
		switch (propertyClass.getEmitXMLClassIDAs()) {
		
		case Element:
			if (value.getValue() instanceof WeakReferenceTarget) {
				appendElement(
						element, 
						property.getNamespace(),
						property.getPrefix(),
						property.getSymbol(), 
						((WeakReferenceTarget) value.getValue()).getWeakTargetReference());
			}
			break;
		case Attribute:
			setAttribute(element, property.getNamespace(), property.getPrefix(), 
					CommonConstants.XMLClassIDAsAttributeName, value);
			break;
		case Suppressed:
			break;
		default:
			break;
		}
	}

	/**
	 * <p>Creates and returns an XML fragment by serializing to XML the given object that implements 
	 * {@link XMLSerializable} and generating a <code>DOCTYPE</code> element with referenced stream 
	 * entities if required.</p>
	 *
	 * @param item Item to serialize to an XML fragment.
	 * @param xmlFile File to be used to store the XML data indicating the appropriate relative
	 * reference to stream files.
	 * 
	 * @return The given item serialized to XML with a <code>DOCTYPE</code> header referencing
	 * any stream entities of the item. 
	 */
	public final static String toXML(
			XMLSerializable item,
			File xmlFile) {
		
		DocumentFragment fragment = createDocumentFragment();
		
		item.appendXMLChildren(fragment);

		/* NodeList nodes = fragment.getChildNodes();
		for ( int x = 0 ; x < nodes.getLength() ; x++ )
			if (nodes.item(x) instanceof Element)
				((Element) nodes.item(x)).setAttributeNS(
						XMLConstants.XMLNS_ATTRIBUTE_NS_URI, 
						"xmlns:aaf",
						AAF_XML_NAMESPACE); */
		
		fragment.normalize();

		String asXML = transformNodeToString(fragment);
		
		String docType = makeDoctype(fragment, xmlFile.getName());
		if (docType != null) {
			StringBuffer withDocType = new StringBuffer(asXML.length() + docType.length() + 1);
			withDocType.append(asXML.substring(0, 39));
			withDocType.append(docType);
			withDocType.append(asXML.substring(39));
			return withDocType.toString();
		}
		else
			return asXML;
	}
	
	/**
	 * <p>Append a comment to the given parent node.</p>
	 *
	 * @param parent Parent to append a new child element to.
	 * @param commentText Text to include with the comment.
	 * 
	 * @return Newly created child comment.
	 */
	public static Comment appendComment(
			Node parent,
			String commentText) {

		Document document = parent.getOwnerDocument();
		
		Comment comment = document.createComment(commentText);
		parent.appendChild(comment);
		
		return comment;
	}

	/**
	 * <p>Append an element with an integer value to the given parent node.</p>
	 *
	 * @param parent Parent to append a new child element to.
	 * @param namespace Namespace in which the element is defined.
     * @param prefix Short prefix name for the namespace, or <code>null</code> if no prefix is required.
	 * @param elementName Name of the element to append.
	 * @param value Value for the new element.
	 * 
	 * @return Newly created child element.
	 */
	public static Element appendElement(
			Node parent,
			String namespace,
			String prefix,
			String elementName,
			int value) {

		Document document = parent.getOwnerDocument();
		
		Element element = document.createElementNS(namespace, elementName);
		element.setPrefix(prefix);
		element.setTextContent(Integer.toString(value));
		parent.appendChild(element);
		
		return element;
	}

	/**
	 * <p>Append an element with a byte value to the given parent node.</p>
	 *
	 * @param parent Parent to append a new child element to.
	 * @param namespace Namespace in which the element is defined.
	 * @param prefix Short prefix name for the namespace, or <code>null</code> if no prefix is required.
	 * @param elementName Name of the element to append.
	 * @param value Value for the new element.
	 * 
	 * @return Newly created child element.
	 */
	public static Element appendElement(
			Node parent,
			String namespace,
			String prefix,
			String elementName,
			byte value) {
		
		Document document = parent.getOwnerDocument();

		Element element = document.createElementNS(namespace, elementName);
		element.setPrefix(prefix);
		element.setTextContent(Byte.toString(value));
		parent.appendChild(element);
		
		return element;
	}
	
	/**
	 * <p>Append an element with a boolean value to the given parent node.</p>
	 *
	 * @param parent Parent to append a new child element to.
	 * @param namespace Namespace in which the element is defined.
	 * @param prefix Short prefix name for the namespace, or <code>null</code> if no prefix is required.
	 * @param elementName Name of the element to append.
	 * @param value Value for the new element.
	 * 
	 * @return Newly created child element.
	 */
	public static Element appendElement(
			Node parent,
			String namespace,
			String prefix,
			String elementName,
			boolean value) {

		Document document = parent.getOwnerDocument();
		
		Element element = document.createElementNS(namespace, elementName);
		element.setPrefix(prefix);
		element.setTextContent((value == true) ? "true" : "false");
		parent.appendChild(element);
		
		return element;
	}
	
	/**
	 * <p>Append an element with a byte array value to the given parent node, encoding the
	 * binary data as a sequence of pairs of hexidecimal character values.</p>
	 *
	 * @param parent Parent to append a new child to.
	 * @param namespace Namespace in which the element is defined.
	 * @param prefix Short prefix name for the namespace, or <code>null</code> if no prefix is required.
	 * @param elementName Name of the element to append.
	 * @param value Value for the new element.
	 * 
	 * @return Newly created child element.
	 */
	public static Element appendElement(
			Node parent,
			String namespace,
			String prefix,
			String elementName,
			byte[] value) {
		
		Document document = parent.getOwnerDocument();
		
		Element element = document.createElementNS(namespace, elementName);
		element.setPrefix(prefix);
		element.setTextContent(new String(Utilities.bytesToHexChars(value)));
		parent.appendChild(element);

		return element;
	}

	/**
	 * <p>Append a stream of data to this document, creating an element that references the
	 * data stream. The stream will become an external reference to a URI that indicates where
	 * the stream of data is stored. Calling {@link #makeDoctype(DocumentFragment, String)} will 
	 * create a document type element containing a reference to the stream.</p>
	 * 
	 * <p>This method does not write any data to disk or to a socket. It attaches the 
	 * stream data to the document that owns the parent element in the form of user data. This 
	 * can be accessed when the document is being serialized to XML.</p>
	 *
	 * @param parent Element to append a stream reference element to.
	 * @param namespace Namespace in which the element is defined.
	 * @param prefix Short prefix name for the namespace, or <code>null</code> is no prefix is required.
	 * @param elementName Name of the stream reference element.
	 * @param attributeName Name of the attribute referencing the stream.
	 * @param entityData Data to be written to the stream.
	 * 
	 * @return Newly created stream element.
	 */
	public static Element appendStream(
			Node parent,
			String namespace,
			String prefix,
			String elementName,
			String attributeName,
			Stream entityData) {
		
		Document document = parent.getOwnerDocument();
		
		String streamName;
		synchronized (document) {
			
			Integer entityCount = (Integer) document.getUserData("entityCount");
			if (entityCount == null) 
				entityCount = 0;
		
			streamName = "stream_" + entityCount;
			document.setUserData("entityCount", ++entityCount, null);

			document.setUserData(streamName, entityData, null);
		}
		
		Element streamElement = document.createElementNS(namespace, elementName);
		streamElement.setPrefix(prefix);
		setAttribute(streamElement, namespace, prefix, attributeName, streamName);
		parent.appendChild(streamElement);
		
		return streamElement;
	}
	
	/**
	 * <p>Creates a "<code>DOCTYPE</code>" element for the given document fragment. It is assumed that
	 * the document fragement contains data stream that have been added using the 
	 * {@link #appendStream(Node, String, String, String, String, Stream)} method. </p>
	 *
	 * @param fragment Document fragment to create a DOCTYPE element for.
	 * @param fileName File name to use in entity references.
	 * 
	 * @return XML DOCTYPE element constructed from the stream appended to the given document.
	 */
	public static final String makeDoctype(
			DocumentFragment fragment,
			String fileName) {
		
		Document document = fragment.getOwnerDocument();
		
		Integer entityCount = (Integer) document.getUserData("entityCount");
		
		if (entityCount == null) return null;
		if (entityCount == 0) return null;
		
		StringBuffer docType = new StringBuffer(100 + entityCount * 100); // guess capacity
		
		docType.append("<!DOCTYPE ");
		docType.append(fragment.getFirstChild().getLocalName());
		docType.append(" [\n" +
				"<!NOTATION DataStream_0 SYSTEM \"urn:smpte:ul:060e2b34.01040101.04100200.00000000\">\n");
		
		for ( int x = 0 ; x < entityCount ; x++ ) {
			docType.append("<!ENTITY stream_");
			docType.append(x);
			docType.append(" SYSTEM \"");
			docType.append(fileName);
			docType.append("_streams/stream_");
			docType.append(x);
			docType.append("\" NDATA DataStream_0>\n");
		}

		docType.append("]>\n");
		
		return docType.toString();
	}
	
	// TODO comment
	public final static void writeStreams(
			Document document,
			File associatedAafxFile) 
		throws IOException {
		
		if (document.getUserData("entityCount") == null) return;
		
		// FIXME write out streams ... returned value is now a industry.stream
//		int entityCount = ((Integer) document.getUserData("entityCount")).intValue();
//		if (entityCount == 0) return;
//		
//		if (!(associatedAafxFile.isFile()))
//			throw new IOException("The given AAF XML file is not a regular file when trying to write out its associated streams.");
//		
//		File parentDir = associatedAafxFile.getParentFile();
//		
//		if (!parentDir.exists())
//			throw new IOException("Cannot write to a directory that does not exist.");
//		
//		if (!parentDir.canWrite())
//			throw new IOException("Insufficient priveleges to create a streams directory for the given AAF XML file.");
//		
//		File streamsDir = new File(parentDir, associatedAafxFile.getName() + "_streams");
//		if (!streamsDir.exists())
//			if (streamsDir.mkdir() == false)
//				System.out.println("Unable to create streams directory " + streamsDir.getCanonicalPath());
//		
//		if (!streamsDir.canWrite())
//			throw new IOException("Cannot write to the streams output directory.");
//		
//		for ( int x = 0 ; x < entityCount ; x++ ) {
//			
//			File streamFile = new File(streamsDir, "stream_" + x);
//			FileOutputStream streamForStream = new FileOutputStream(streamFile);
//			streamForStream.write((byte[]) document.getUserData("stream_" + x));
//			streamForStream.close();
//		}
	}
	
	/**
	 * <p>Sets the attribute of the given element of the given name to the given value,
	 * assuming that the attribute is within the AAF namespace. The attribute name will
	 * be prefixed with <code>aaf:</code>. If the attribute does not yet exist for the
	 * given element, it will be created. If the attribute does exist, its value will 
	 * be replaced.</p>
	 *
	 * @param element Element to set the attribute of.
	 * @param namespace Namespace in which the attribute is defined.
	 * @param prefix Short prefix name for the namespace, or <code>null</code> if no prefix is required.
	 * @param attributeName Name of the attribute of the element to set.
	 * @param attributeValue Value to set the attribute to.
	 */
	public static final void setAttribute(
			Element element,
			String namespace,
			String prefix,
			String attributeName,
			String attributeValue) {
		
		Document document = element.getOwnerDocument();
		Attr attribute = null;
		document.createAttributeNS(namespace, attributeName);
		if (prefix == null) {
			attribute = document.createAttribute(attributeName);
			attribute.setNodeValue(attributeValue);
			element.setAttributeNode(attribute);
		}
		else {
			attribute = document.createAttributeNS(namespace, attributeName);
			attribute.setPrefix(prefix);
			attribute.setNodeValue(attributeValue);
			element.setAttributeNodeNS(attribute);
		}
	}
	
	public static final void setAttribute(
			Element element,
			String namespace,
			String prefix,
			String attributeName,
			PropertyValue value) {
		
		String stringValue = propertyValueToString(value);
		
		if (stringValue != null)
			setAttribute(element, namespace, prefix, attributeName, stringValue);
	}
	
	final static String propertyValueToString(
			PropertyValue value) {
		
		switch (value.getType().getTypeCategory()) {
		
		case Int:
		case String:
		case Record: // May generate messy pseudo-XML ... should only really be used for types with simple rep.
		case StrongObjRef: // Assumes a String serialization with toString/parseFactory pairs
			return value.getValue().toString();

		case Enum:
			return ((TypeDefinitionEnumerationImpl) value.getType()).getSymbolFromValue(value);

		case ExtEnum:
			return ((TypeDefinitionExtendibleEnumerationImpl) value.getType()).getSymbolFromValue(value);
			
		case Rename:
			TypeDefinitionRename renamedType = (TypeDefinitionRename) value.getType();
			return propertyValueToString(renamedType.getBaseValue(value));
		
		default:
			return null; // Only certain types are suitable as attributes
		}

	}
	
	/**
	 * <p>Creates a new instance of the object represented in serialized form by the given XML 
	 * fragment. The fragment must be represented as an {@link InputSource}. All the elements
	 * of the XML fragment must have had XML handlers registered with the 
	 * {@link MasterContentHandler#registerHandler(Class)} method previously to
	 * calling this method.</p>
	 *
	 * @param xmlSource XML fragment to deserialize into a corresponding object instance.
	 * @return Object instance that is a deserialized version of the given XML fragment.
	 * 
	 * @throws NullPointerException The XML source argument is <code>null</code>.
	 * @throws SAXException An exception occurred when trying to parse the given XML fragment.
	 * @throws IOException An input/output exception occurred when reading the given XML
	 * input source.
	 */
	public static Object createFromXML(
			InputSource xmlSource) 
		throws NullPointerException, 
			SAXException,
			IOException {
		
		if (xmlSource == null)
			throw new NullPointerException("Cannot create a new object instance from a null input source.");
		
		MasterContentHandler mch = new MasterContentHandler(xmlSource);
		mch.parse();
		return mch.getResult();
	}

	/**
	 * <p>Creates a new instance of the object represented in serialized form by the given XML 
	 * fragment with reference to the given collection of data streams. The fragment must be represented 
	 * as an {@link InputSource}. All the elements of the XML fragment must have had XML handlers registered with the 
	 * {@link MasterContentHandler#registerHandler(Class)} method previously to
	 * calling this method.</p>
	 *
	 * @param xmlSource XML fragment to deserialize into a corresponding object instance.
	 * @param streams Map from stream name to an input stream providing the required stream data.
	 * @return Object instance that is a deserialized version of the given XML fragment.
	 * 
	 * @throws NullPointerException The XML source argument is <code>null</code>.
	 * @throws SAXException An exception occurred when trying to parse the given XML fragment.
	 * @throws IOException An input/output exception occurred when reading the given XML
	 * input source.
	 */
	public static Object createFromXML(
			InputSource xmlSource,
			Map<String, InputStream> streams) 
		throws NullPointerException, 
			SAXException,
			IOException {
		
		if (xmlSource == null)
			throw new NullPointerException("Cannot create a new object instance from a null input source.");
		
		MasterContentHandler mch = new MasterContentHandler(xmlSource, streams);
		mch.parse();
		return mch.getResult();
	}
	
	/**
	 * <p>Wrapper around the {@link #createFromXML(InputSource)} method that creates an 
	 * {@link InputSource input source} from a new {@link StringReader} of the given 
	 * string.</p>
	 *
	 * @param xmlSource String representation of an XML fragment.
	 * @return Object instance that is a deserialized version of the given XML fragment.
	 * 
	 * @throws NullPointerException The XML source argument is <code>null</code>.
	 * @throws SAXException An exception occurred when trying to parse the given XML fragment.
	 */
	public static Object createFromXMLString(
			String xmlSource) 
		throws NullPointerException,
			SAXException {
		
		if (xmlSource == null)
			throw new NullPointerException("Cannot create a new object instance from a null input source.");	
		
		try {
			return createFromXML(new InputSource(new StringReader(xmlSource)));
		} catch (IOException e) {
			// String readers should not throw IO Exceptions ... rethrow as a SAXException just in case
			throw new SAXException("IO exception thrown during the parse of an XML string.", e);
		}
	}

	/**
	 * <p>Wrapper around the {@link #createFromXML(InputSource)} method that creates an 
	 * {@link InputSource input source} from a new {@link StringReader} of the given 
	 * string, with byte arrays the source for data stream inputs.</p>
	 *
	 * @param xmlSource String representation of an XML fragment.
	 * @return Object instance that is a deserialized version of the given XML fragment.
	 * 
	 * @throws NullPointerException The XML source argument is <code>null</code>.
	 * @throws SAXException An exception occurred when trying to parse the given XML fragment.
	 */
	public static Object createFromXMLString(
			String xmlSource,
			byte[][] streams) 
		throws NullPointerException,
			SAXException {
		
		if (xmlSource == null)
			throw new NullPointerException("Cannot create a new object instance from a null input source.");	
		
		Map<String, InputStream> streamMap = new HashMap<String, InputStream>(streams.length);	
		for ( int x = 0 ; x < streams.length ; x++ ) 
			streamMap.put("stream_" + x, new ByteArrayInputStream(streams[x]));
		
		try {
			return createFromXML(new InputSource(new StringReader(xmlSource)), streamMap);
		} catch (IOException e) {
			// String readers should not throw IO Exceptions ... rethrow as a SAXException just in case
			throw new SAXException("IO exception thrown during the parse of an XML string.", e);
		}
	}

	// TODO comment
	public static Map<String, InputStream> parseDocTypeToStreams(
			File aafxFile) 
		throws IOException {

		FileReader reader = new FileReader(aafxFile);
		File parentDir = aafxFile.getParentFile();
		
		Map<String, InputStream> streams = new HashMap<String, InputStream>();
		
		StreamTokenizer tokenizer = new StreamTokenizer(new BufferedReader(reader));
		tokenizer.eolIsSignificant(true);
		tokenizer.ordinaryChar('_');
		boolean enteredDocType = false;
		
		while (true) {
			int token = tokenizer.nextToken();
			if (token == StreamTokenizer.TT_EOF) break;
			if (tokenizer.sval != null) {

				if (tokenizer.sval.equals("DOCTYPE")) {
					enteredDocType = true;
					break;
				}
			}
		}
		
		if (enteredDocType == false) {
			reader.close();
			return streams;
		}
		
		while(true) {
			int token = tokenizer.nextToken();
			switch (token) {
			
			case StreamTokenizer.TT_EOF:
				reader.close();
				return streams;
			case StreamTokenizer.TT_WORD:
				if (tokenizer.sval.equals("ENTITY")) {
					parseEntity(tokenizer, streams, parentDir);
					break;
				}
				if (tokenizer.sval.equals("HeaderDictionary")) {
					reader.close();
					return streams;
				}
				break;
			default:
				break;
			}
		}

	}

	// TOOO comment
	private static void parseEntity(
			StreamTokenizer tokenizer, 
			Map<String, InputStream> streams,
			File parentDir) 
		throws IOException {

		StringBuffer entityName = new StringBuffer();
		StringBuffer entityFileName = new StringBuffer();
		StringBuffer currentBuffer = entityName;
		
		while (true) {
			
			int token = tokenizer.nextToken();
			switch (token) {
			
			case StreamTokenizer.TT_EOF:
				return;
			case StreamTokenizer.TT_NUMBER:
				currentBuffer.append((int) tokenizer.nval);
				break;
			case StreamTokenizer.TT_WORD:
				if (tokenizer.sval.equals("SYSTEM")) {
					currentBuffer = entityFileName;
					break;
				}
				if ((tokenizer.sval.equals("NDATA")) || (tokenizer.sval.equals("ENTITY"))) {
					streams.put(
							entityName.toString(), 
							new FileInputStream(new File(parentDir, entityFileName.toString())));
					if (tokenizer.sval.equals("ENTITY"))
						tokenizer.pushBack();
					return;
				}
				currentBuffer.append(tokenizer.sval);
				break;
			case StreamTokenizer.TT_EOL:
				break;
			case '\"':
				currentBuffer.append(tokenizer.sval);
				break;
			default:
				currentBuffer.append((char) token);
				break;
			}
		}
		
	}

	public final static void validate(
			String schemaPath,
			String document) 
		throws NullPointerException,
			SAXException,
			IOException {
		
		if (schemaPath == null)
			throw new NullPointerException("Cannot validate against a schema with a null name.");
		if (document == null)
			throw new NullPointerException("Cannot validate a null document.");
		
		SchemaFactory schemaFactory =
		      SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Source schemaSource = new StreamSource(new File(schemaPath));
		Schema schema = schemaFactory.newSchema(schemaSource);
		
		Validator validator = schema.newValidator();
		Source xmlSource = new StreamSource(new StringReader(document));
		
//		System.out.println(document);
		validator.validate(xmlSource);
	}
	
	public static final void main(String[] args) 
		throws Exception {
		
		Object fromXML = XMLBuilder.createFromXML(new InputSource(new FileInputStream(args[0])));
		System.out.println(fromXML.toString());
	}
}
