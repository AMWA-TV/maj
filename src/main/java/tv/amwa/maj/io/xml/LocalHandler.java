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
 * $Log: LocalHandler.java,v $
 * Revision 1.4  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2008/10/15 14:16:08  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.2  2008/01/15 12:24:23  vizigoth
 * Fixed error where the end of an elements data matched exactly the end of the character buffer.
 *
 * Revision 1.1  2007/11/13 22:14:56  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.io.xml;

// TODO add tests and documentation for hierarchical support

// FIXME XML parser is not working with entities such as &lt;

// FIXME problem with empty lists returning null ... probably needs fixing in each class

// TODO comments and tests

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * <p>Content handler class that is implemented by all content handlers that can deserialize an
 * XML element and create an object. This abstract class provides implementations for all
 * of the {@link org.xml.sax.ContentHandler} interfaces, requiring the implementor of a handler
 * for a specific element type to override the abstract {@link #createResult()} method.
 * Subclasses of this class can be registered as element content handlers using
 * the {@link #registerHandler(Class)} method.</p>
 * 
 * <p>The following example code shows how an element specific content handler could be
 * implemented:</p>
 * 
 * <pre>
 * public static class MyObjectHandler
 *     extends LocalHandler
 *     implements ContentHandler {
 *     
 *     public final static String elementName = "MyElement";
 *     public final static String[] dataElementNames = new String[] {
 *             "DataElement1", "DataElement2" };
 *     public final static String[] subElementNames = new String[] {
 *             "SubElement1", "SubElement2" };
 *                 
 *     protected Object createResult() 
 *         throws SAXException {
 * 
 *         return new MyObject(
 *             getAttributes().get("myAttribute"),
 *             getElementData("DataElement1"),
 *             Integer.parseInt(getAttributeValue("DataElement1", "dataAttribute1")),
 *             (MySubObject1) getSubElement("SubElement1"),
 *             Integer.parseInt(getElementData("DataElement2")),
 *             (MySubObject2) getSubElement("SubElement2"));
 *     }
 * }
 * </pre>
 * 
 * <p>The above code would successfully parse the following fragment of XML and create
 * an instance of <code>MyObject</code>.</p>
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;!--Throw away comment--&gt;
 * &lt;MyElement myAttribute="my attribute value"&gt;
 *   &lt;DataElement1 dataAttribute1="7"&gt;data element 1 test value&lt;/DataElement1&gt;
 *   &lt;DataElement2&gt;67&lt;/DataElement&gt;
 *   &lt;SubElement1&gt;
 *     &lt;ChildValue&gt;I'm a child&lt;/ChildValue&gt;
 *   &lt;/SubElement1&gt;
 *   &lt;SubElement2 subElement2Attribute="0x6f"/&gt;
 * &lt;/MyElement&gt;
 * </pre>
 * 
 * <p>The rules surrounding the implementation of a content handler that extends this class
 * are as follows:</p>
 * 
 * <ul>
 *  <li>Content handlers must provide the element name they process as the value of a <code>public final
 *  static String</code> value called "<code>elementName</code>". This is extracted from the
 *  handler by the parser and if any elements of this name are encountered during a parse, the
 *  handler associated with this element name will be called.</li>
 *  
 *  <li>Any child elements of the element associated with the content handler that do not themselves
 *  contain sub elements, only attributes and/or text content, are known as <em>data elements</em>. 
 *  Content handlers must provide a list of the names of all data elements that can be expected to 
 *  be encountered on a parse of the associated element's content, whether they are required or 
 *  optional. This list should be a <code>public final static String[]</code>. If the list is empty 
 *  or not present, the element associated with the content handler will be assumed to have no child 
 *  data elements and if one is encountered, a {@link SAXException} will be thrown.</li>
 *  
 *  <li>Any child elements of the element associated with the content handler that themselves contain
 *  child elements as content are known as <em>sub elements</em>. Content handlers must provide a
 *  list of the names of all sub elements that can expected to be encountered on a parse of the 
 *  associated element's content, whether they are required or optional. This list should be a
 *  <code>public final static String[]</code>. If the list is empty or not present, the element associated
 *  with the content handler will be assumed to have not sub elements and if one is encountered, a
 *  {@link SAXException} will be thrown. The sub elements will be parsed with their own content handler,
 *  which must have been registered. If more than one of the same sub element is found, the values 
 *  are appended to a list of content.</li>
 *  
 *  <li>The handler must provide an implementation of the {@link #createResult()} method that
 *  creates an instance of the deserialized XML fragment. This method is called after all available
 *  data elements and sub elements have been parsed and their values are available through calls to
 *  the {@link #getElementData(String)} for data elements, {@link #getSubElement(String)} for 
 *  a sub element, {@link #getSubElementList(String)} for lists of sub elements of the same type and
 *  {@link #getAttributesForElement(String)} for attributes of data elements or sub elements. All of these
 *  methods could return <code>null</code> if the requested value is not available and implementations of
 *  the {@link #createResult()} method must be coded to expect this, throwing an {@link Exception} if 
 *  creation of a result was not possible.</li>
 * </ul>
 * 
 * <p>This content handler hierarchy has been designed to make it easy to extend the core objects 
 * available in this AAF implementation to other classes while providing an efficient event-driven 
 * XML parser.</p>
 *
 *
 */
public abstract class LocalHandler
	extends MasterContentHandler
		implements ContentHandler {

	// Changed from static to local due to thread safe parsing issues
	/** <p>Cache of data element names created with {@link #addDataElementNames()}.</p> */
	private final Set<String> dataElementNameSet = 
		Collections.synchronizedSet(new HashSet<String>());
	/** <p>Cache of sub element names created with {@link #addSubElementNames()}.</p> */
	private final Set<String> subElementNameSet = 
		Collections.synchronizedSet(new HashSet<String>());

	/** <p>Separator used between all element names and their data or sub element names.</p> */
	private final static String nameSeparator = "_ _";
	
	/** <p>Set to the current data element name when inside the scope of a data element.</p> */
	private String currentElementName = null;
	/** <p>Set to the current attributes of a data element when inside the scope of that element.</p> */
	private Attributes currentElementAttributes = null;
	/** <p>Characters call count is used to detect genuine buffer overflows.</p> */
	private int charactersCallCount; 
	/** <p>Set to true between a call to startElement and endElement to indicate data was read right
	 * up to the end of a buffer.</p> */
	boolean bufferOverflow;
	
	/** <p>Map from data element name to data element values that is constructed during parsing and
	 * then accessed using {@link #getElementData(String)}.</p> */
	private Map<String, List<String>> dataElementValues = 
		Collections.synchronizedMap(new HashMap<String, List<String>>());
	/** <p>Map from data or sub element name to attributes defined for that attribute that is 
	 * constructed during parsing and then accessed using {@link #getAttributesForElement(String)}.</p> */
	private Map<String, Map<String, String>> elementAttributes = 
		Collections.synchronizedMap(new HashMap<String, Map<String, String>>());
	/** <p>Map from sub element name to list of sub element values that is constructed during
	 * parsing and then accessed using {@link #getSubElement(String)} and 
	 * {@link #getSubElementList(String)}.</p> */
	private Map<String, List<MasterContentHandler>> subElementHandlers =
		Collections.synchronizedMap(new HashMap<String, List<MasterContentHandler>>());
	
	/**
	 * <p>Extract the data element names from the "<code>dataElementNames</code>" string array
	 * of this local handler and store them in the {@value #dataElementNameSet}. The element
	 * name is stored in the set to indicate that this given element name is now cached and
	 * the each child data element is stored in the set in the form:</p>
	 * 
	 * <p><center>"&lt;<em>elementName</em>&gt;<code>_ _</code>&lt;<em>childDataElementName</em>&gt;"</center></p>
	 *
	 */
	private final void addDataElementNames(
			Class<?> handlerClass) {
		
		if (!(handlerClass.getSuperclass().equals(LocalHandler.class)))
			addDataElementNames(handlerClass.getSuperclass());
		
		try {
			Field elementNamesField = handlerClass.getField("dataElementNames");
			String[] elementNames = (String[]) elementNamesField.get(null);
			
			for ( String name : elementNames )
				dataElementNameSet.add(getElementName() + nameSeparator + name);
		} catch (SecurityException e) {
			// Leave an empty data element set
		} catch (IllegalArgumentException e) {
			// Leave an empty data element set
		} catch (NoSuchFieldException e) {
			// Leave an empty data element set
		} catch (IllegalAccessException e) {
			// Leave an empty data element set
		}
	}
	
	/**
	 * <p>Extract the sub element names from the "<code>subElementNames</code>" string array
	 * of this local handler and store them in the {@value #subElementNameSet}. The element
	 * name is stored in the set to indicate that this given element name is now cached and
	 * the each child data element is stored in the set in the form:</p>
	 * 
	 * <p><center>"&lt;<em>elementName</em>&gt;<code>_ _</code>&lt;<em>childDataElementName</em>&gt;"</center></p>
	 *
	 */
	private final void addSubElementNames(
			Class<?> handlerClass) {
		
		if (!(handlerClass.getSuperclass().equals(LocalHandler.class)))
				addSubElementNames(handlerClass.getSuperclass());
		
		try {
			Field subElementsField = handlerClass.getField("subElementNames");
			String[] elementNames = (String[]) subElementsField.get(null);
			
			for ( String name : elementNames )
				subElementNameSet.add(getElementName() + nameSeparator + name);
		} catch (SecurityException e) {
			// Leave an empty sub element set
		} catch (IllegalArgumentException e) {
			// Leave an empty sub element set
		} catch (NoSuchFieldException e) {
			// Leave an empty sub element set
		} catch (IllegalAccessException e) {
			// Leave an empty sub element set
		}
	}
	
	/**
	 * <p>Tests if the given data element name is expected for the associated element of this content
	 * handler, creating a cache of data element names if one does not yet exist.</p>
	 *
	 * @param name Data element name to test.
	 * @return Is the given element name the name of a data element expected as a child of the 
	 * element associated with this content handler?
	 */
	private final boolean dataElementsContains(
			String name) {
		
		if (!(dataElementNameSet.contains(getElementName()))) {
			addDataElementNames(getClass());
			dataElementNameSet.add(getElementName());
		}
		
		return dataElementNameSet.contains(getElementName() + nameSeparator + name);
	}
	
	/**
	 * <p>Tests if the given sub element name is expected for the associated element of this content
	 * handler, creating a cache of sub element names if one does not yet exist.</p>
	 *
	 * @param name Sub element name to test.
	 * @return Is the given element name the name of a sub element expected as a child of the 
	 * element associated with this content handler?
	 */
	private final boolean subElementsContains(
			String name) {
		
		if (!(subElementNameSet.contains(getElementName()))) {
			subElementNameSet.add(getElementName());
			addSubElementNames(getClass());
		}

		if (subElementNameSet.contains(getElementName() + nameSeparator + name))
			return true;
		
		return checkParentName(getHandlerClassForName(name).getSuperclass());
	}
	
	private boolean checkParentName(
			Class<?> parentClass) {
		
		if (parentClass.equals(LocalHandler.class)) 
			return false;
		
		try {
			Field parentName = parentClass.getField("elementName");
			if (subElementNameSet.contains(getElementName() + nameSeparator + parentName.get(null)))
				return true;
		} 
		catch (IllegalAccessException e) {
			// It wasn't meant to be
			e.printStackTrace();
		}
		catch (SecurityException e) {
			// This secure!
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// No static element name defined, so try higher
		}

		return checkParentName(parentClass.getSuperclass());
	}
	
	private String getParentName(
			Class<?> parentClass) {
	
		if (parentClass.equals(LocalHandler.class))
			return null;
		
		try {
			Field parentNameField = parentClass.getField("elementName");
			String parentName = (String) parentNameField.get(null);
			if (subElementNameSet.contains(getElementName() + nameSeparator + parentName))
				return parentName;
		}
		catch (IllegalAccessException e) {
			// It wasn't meant to be
			e.printStackTrace();
		}
		catch (SecurityException e) {
			// This secure!
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// No static element name defined, so try higher
		}

		return getParentName(parentClass.getSuperclass());
	}
			
	/** 
	 * @see tv.amwa.maj.io.xml.MasterContentHandler#characters(char[], int, int)
	 */
	public void characters(
			char[] ch,
			int start,
			int length)
			throws SAXException {

		charactersCallCount++;
		
		if (currentElementName != null) {
			if (!(dataElementValues.containsKey(currentElementName)))
				dataElementValues.put(currentElementName, new Vector<String>());
			
			List<String> existingElements = dataElementValues.get(currentElementName);
			existingElements.add(new String(ch, start, length));
			
//			if (currentElementName.equals("DefinitionObjectIdentification"))
//				System.out.println("Added: " + new String(ch, start, length) + " start: " + start + " length : " + length);
			
			elementAttributes.put(currentElementName, makeAttributeMap(currentElementAttributes));
		}
		if ((start + length) != ch.length) {
			currentElementName = null;
			currentElementAttributes = null;
		}
		else {
			bufferOverflow = true;
		}
	}


	
	/** 
	 * <p>Called if the end of a document is reached within a local handler, which indicates 
	 * an exceptional condition as only master content handlers should manage document ends. 
	 * This method always throws a {@link SAXException}.</p>
	 * 
	 * @see tv.amwa.maj.io.xml.MasterContentHandler#endDocument()
	 */
	public final void endDocument()
			throws SAXException {

		throw new SAXException("Local handlers must not deal with the end of documents.");
	}

	/** 
	 * <p>Called at the end of any child data element of the element associated with this local
	 * handler or at the end of the element itself. If the call is at the end of the element
	 * itself, the content handler is set back to the {@link #parentHandler} and the handler
	 * generates its result.</p>
	 * 
	 * @see tv.amwa.maj.io.xml.MasterContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public final void endElement(
			String uri,
			String localName,
			String name)
		throws SAXException {

		// System.out.print("name = " + name + "/cc = " + charactersCallCount + " bo = " + bufferOverflow + " ");

		/*
		 * Detects the situation where the SAX parser has reached the end of its buffer size ... for Apache Xerces this
		 * defaults to 2k. The logic is that both the end of a buffer has been detected and the characters method has
		 * been called more than once before reaching here. This code should handle elements made up of multiple 
		 * buffers.
		 */
		
		if (bufferOverflow == true) {
			assert(charactersCallCount > 0);
			if (charactersCallCount == 1) { // End of buffer exactly matches end of element's characters.
			
				currentElementName = null;
				currentElementAttributes = null;
			}
			else { // charactersCallCount > 1 - End of buffer in the middle of the element's characters
				List<String> mergeRequired = dataElementValues.get(name);
				StringBuffer mergeResult = new StringBuffer();
			
				for ( int x = 0 ; x < charactersCallCount ; x++ ) 
					mergeResult.insert(0, mergeRequired.remove(mergeRequired.size() - 1));
			
				mergeRequired.add(mergeResult.toString());
			}
		}
		
		bufferOverflow = false;
		
		try {
			if (name.equals(getElementName())) {
				getXMLReader().setContentHandler(getParentHandler());
				setResult(createResult());
			}
		} catch (Exception e) {
			throw new SAXException(e.getClass().getName() + " thrown while parsing " + getElementName() + " XML element.", e);
		}
	}
	
	/**
	 * <p>Create the resulting object from the deserialization carried out by this content handler. 
	 * This method must be implemented in every subclass of {@link LocalHandler} and will be 
	 * called when data is available from within the methods implementation by calling:</p>
	 * 
	 * <ul>
	 *  <li>{@link #getElementData(String)} for a child data element of the given element name;</li>
	 *  <li>{@link #getSubElement(String)} for a child sub element object of the given element name;</li>
	 *  <li>{@link #getSubElementList(String)} for a list of sub element objects of the given 
	 *  element name;</li>
	 *  <li>{@link #getAttributesForElement(String)} for the attributes of a child element with the
	 *  given name.</li>
	 * </ul>
	 *
	 * @return Object created from the deserialization of an XML fragment carried out by this
	 * content handler.
	 * @throws Exception TODO
	 * @throws Exception Creation of the required result was not possible as the parser
	 * does not have the required data in the correct format. 
	 */
	protected abstract Object createResult()
		throws Exception;

	/** 
	 * <p>Called at the start of any data element or sub element. For data elements, this method
	 * prepares to capture any character data that might be available. For sub elements, this method
	 * finds and instantiates an appropriate content handler.</p> 
	 * 
	 * @see tv.amwa.maj.io.xml.MasterContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public final void startElement(
			String uri,
			String localName,
			String name,
			Attributes atts)
		throws SAXException {

		if (dataElementsContains(name)) {
			currentElementName = name;
			currentElementAttributes = atts;
			charactersCallCount = 0;
			bufferOverflow = false;
			return;
		}
		
		if (subElementsContains(name)) {
			MasterContentHandler subElementHandler = makeHandler(name, uri, this, atts);
			
			String parentName;
			if (subElementNameSet.contains(getElementName() + nameSeparator + name))
				parentName = name;
			else
				parentName = getParentName(getHandlerClassForName(name));
			
			if (!(subElementHandlers.containsKey(parentName))) {
				subElementHandlers.put(parentName, new Vector<MasterContentHandler>());
			}
			List<MasterContentHandler> currentHandlers = subElementHandlers.get(parentName);
			currentHandlers.add(subElementHandler);
			elementAttributes.put(name, makeAttributeMap(atts));
			getXMLReader().setContentHandler(subElementHandler);
			return;
		}
		
		throw new SAXException("Unexpected element " + name + " when processing a " + getClass().getEnclosingClass().getSimpleName() + ".");
	}
	
	/**
	 * <p>Returns the text content of a child data element of the given name, or <code>null</code> if
	 * the data element was not present or if the name does not match a known data element name.</p>
	 *
	 * @param elementName Name of the data element to retrieve the content of.
	 * @return Text content of the data element with the given name, or <code>null</code> if 
	 * associated text content is not available.
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final String getElementData(
			String elementName) 
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot retrieve text content using a null element name.");
		
		if (!(dataElementValues.containsKey(elementName))) return null;
		
		List<String> dataElementsList = dataElementValues.get(elementName);
		if (dataElementsList.size() == 0) return null;
		
		return dataElementsList.get(0);
	}
	
	public final List<String> getElementDataList(
			String elementName) {
		
		if (elementName == null)
			throw new NullPointerException("Cannot retrieve text content using a null element name.");

		return dataElementValues.get(elementName);
	}
	
	/**
	 * <p>Returns the attributes of a child data element or child sub element, or <code>null</code>
	 * if the element name does not match a known element name and/or no attribute values are available
	 * for that element.</p>
	 *
	 * @param elementName Name of the element to retrieve the attributes of.
	 * @return Attributes of the data element with the given name, or <code>null</code> if an
	 * associated mao of attribute values is not available.
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final Map<String, String> getAttributesForElement(
			String elementName) 
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot retrieve attributes using a null element name.");
		
		return elementAttributes.get(elementName);
	}
	
	/**
	 * <p>Returns the value of an attribute of a child data element or child sub element, or 
	 * <code>null</code> if the element or the attribute is not known.</p>
	 *
	 * @param elementName Name of the element to retrieve an attribute value from.
	 * @param attributeName Name of the attribute to retrieve the value of.
	 * @return Value of the attribute from the child element of the given name and the given
	 * attribute name, or <code>null</code> if an appropriate attribute value cannot be found.
	 * @throws NullPointerException
	 */
	public final String getAttributeValueChildElement(
			String elementName,
			String attributeName) 
		throws NullPointerException {
		
		if (attributeName == null)
			throw new NullPointerException("Cannot retrieve an attribute value with a null attribute name.");
		
		Map<String, String> attributeMap = getAttributesForElement(elementName);
		if (attributeMap != null) {
			return attributeMap.get(attributeName);
		}
		
		return null;
	}
	
	/**
	 * <p>Returns the result object created after the deserialization of a sub element of the given
	 * element name, or <code>null</code> if the sub element name does not match the name of a known
	 * sub element or a result is not available. If the sub element occurred more than once in an 
	 * XML element, this method will return the first one encountered. Use 
	 * {@link #isSingleSubElement(String)} to establish if this is the only value with this
	 * sub element name that has been encountered.</p>
	 *
	 * @param elementName Name of the sub element to retrieve the deserialized value of.
	 * @return Deserialized version of the sub element with the given element name, or <code>null</code> 
	 * if an associated deserialized value is not available.
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final Object getSubElement(
			String elementName)
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot retrieve a sub element value using a null element name.");
		
		if (!(subElementHandlers.containsKey(elementName))) return null;
	
		MasterContentHandler handler = subElementHandlers.get(elementName).get(0);
		return handler.getResult();
	}
	
	/**
	 * <p>Returns a list of result objects created after the deserialization of child sub elements
	 * with the given element name in the order in which they were declared in the XML file. This 
	 * method returns <code>null</code> if the sub element name does not match the name of a known
	 * sub element or a result is not available. Use the {@link #isSubElementList(String)} to find
	 * out if a list of sub element values definitely exists for a particular sub element name.</p>
	 *
	 * @param elementName Name of the sub element to retrieve the list of deserialized values for.
	 * @return List of deserialized values from element names matching the given name.
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final List<Object> getSubElementList(
			String elementName) 
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot retrieve a list of sub element values using a null element name.");
		
		if (!(subElementHandlers.containsKey(elementName))) return null;
		
		List<Object> objectList = new Vector<Object>(subElementHandlers.get(elementName).size());
		for ( MasterContentHandler handler : subElementHandlers.get(elementName) ) {
			objectList.add(handler.getResult());
		}
		
		return objectList;
	}
	
	/**
	 * <p>Returns <code>true</code> if the given sub element name is definately linked with a 
	 * list of values, which is the case if more than one has been encountered. If only one was
	 * encountered, the sub element name could correspond to a list or a single value. This parser
	 * does not have access to a DTD or schema to check and so it is up to the caller to decide whether
	 * a list containing a single value represents a list of sub elements or a single sub element.</p>
	 *
	 * @param elementName Sub element name to check if more than one value with that element name has been 
	 * deserialized.
	 * @return Does the given sub element name correspond to more than one deserialized value in this
	 * local handler?
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final boolean isSubElementList(
			String elementName) 
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot check sub element kind with a null element name.");

		if (!(subElementHandlers.containsKey(elementName))) return false;
		
		if (subElementHandlers.get(elementName).size() > 1) return true;
		return false;
	}
	
	/**
	 * <p>Returns <code>true</code> if the given data element name is definately linked with a 
	 * list of values, which is the case if more than one has been encountered. If only one was
	 * encountered, the data element name could correspond to a list or a single value. This parser
	 * does not have access to a DTD or schema to check and so it is up to the caller to decide whether
	 * a list containing a single value represents a list of data elements or a single data element.</p>
	 *
	 * @param elementName Data element name to check if more than one value with that element
	 * name has data available.
	 * @return Does the given data element name correspond to more than one deserialized value in this
	 * local handler?
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final boolean isDataElementList(
			String elementName) 
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot check data element kind with a null element name.");
		
		if (!(dataElementValues.containsKey(elementName))) return false;
		
		if (dataElementValues.get(elementName).size() > 1) return true;
		return false;
	}
	
	/**
	 * <p>Returns <code>true</code> if exactly one deserialized sub element value matching the 
	 * given element name is available within this local handler. If the element name is not known,
	 * corresponds to an empty list or corresponds to a list of more than one value, <code>false</code>
	 * will be returned.</p>
	 *
	 * @param elementName Sub element name to check if exactly one value with that element name has been 
	 * deserialized.
	 * @return Does the given sub element name correspond to exactly one deserialized value in this
	 * local handler?
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final boolean isSingleSubElement(
			String elementName) 
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot check sub element kind with a null element name.");
		
		if (!(subElementHandlers.containsKey(elementName))) return false;
		
		if (subElementHandlers.get(elementName).size() == 1) return true;
		return false;
	}
	
	/**
	 * <p>Returns <code>true</code> if exactly one value for a data element value matching the 
	 * given element name is available within this local handler. If the element name is not known,
	 * corresponds to an empty list or corresponds to a list of more than one value, <code>false</code>
	 * will be returned.</p>
	 *
	 * @param elementName Data element name to check if exactly one value with that element name
	 * has been deserialized.
	 * @return Does the given data element name correspond to exactly one data element value in
	 * this local handler?
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final boolean isSingleDataElement(
			String elementName)
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot check data element kind with a null element name.");
		
		if (!(dataElementValues.containsKey(elementName))) return false;
		
		if (dataElementValues.get(elementName).size() == 1) return true;
		return false;
	}
	
	/**
	 * <p>Check if text content is available for a child data element of the given name.</p>
	 *
	 * @param elementName Name of a child data element to use to check if text content is available.
	 * @return Is text content available for the given element name?
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final boolean isDataElementPresent(
			String elementName) 
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot check if the content of a data element is present using a null string.");
		
		return dataElementValues.containsKey(elementName);
	}
	
	/**
	 * <p>Check if a deserialized sub element value or values is/are available for the given sub element
	 * name.</p>
	 *
	 * @param elementName Name of a child sub element to use to check if a sub element value or sub element
	 * values are available.
	 * @return Is/are sub element value/values available for the given element name?
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final boolean isSubElementPresent(
			String elementName) 
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot check if a sub element value is present using a null string.");
		
		if (subElementHandlers.containsKey(elementName)) 
			if (subElementHandlers.get(elementName).size() > 0) return true;
		
		return false;
	}
	
	/**
	 * <p>Check if attributes are available for the given element name. If attributes are not
	 * available, this could be for two reasons:</p>
	 * 
	 * <ul>
	 *  <li>An element of the given name was not encountered while parsing the children of the 
	 *  element associated with this local handler.</li>
	 *  <li>The element was encountered but defined no attributes.</li>
	 * </ul>
	 *
	 * @param elementName Name of a data element of sub element to use to check for the presences of an
	 * attributes set.
	 * @return Are attributes available for the given element name?
	 * 
	 * @throws NullPointerException The element name argument is <code>null</code>.
	 */
	public final boolean areAttributesPresent(
			String elementName)
		throws NullPointerException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot check if attributes are present for an element using a null string.");
		
		if (elementAttributes.containsKey(elementName)) 
			return (elementAttributes.get(elementName) != null);
		
		return false;
	}

	/**
	 * <p>Return the input stream with the given name from the collection of streams that
	 * has been attached to this content handler.</p>
	 * 
	 * @param streamName Name of the input stream to retrieve.
	 * @return Input stream associated with the given name, or <code>null</code> if not
	 * input streams are available or the given name does not map to an input stream.
	 * 
	 * @see tv.amwa.maj.io.xml.MasterContentHandler#getInputStreamForName(java.lang.String)
	 */
	@Override
	public InputStream getInputStreamForName(
			String streamName) {
		
		return getParentHandler().getInputStreamForName(streamName);
	}

	// FIXME Quick and dirty method ... should use better stream handling
	/**
	 * <p>Find a named stream of data attached to the parent content handler, read it and return it
	 * as a byte array.</p>
	 * 
	 * @param streamName Name of the stream to read.
	 * @return Byte buffer containing the stream of the given name, or <code>null</code> if a stream
	 * of the given name could not be found.
	 * 
	 * @throws IOException An error occurred when trying to read the given input stream.
	 */
	public byte[] getBytesFromStream(
			String streamName) 
		throws IOException {
		
		InputStream stream = getInputStreamForName(streamName);
		
		if (stream == null) return null;
		
		byte[] streamData = new byte[stream.available()];
		stream.read(streamData);
		
		stream.close();
		
		return streamData;
	}
	
}
