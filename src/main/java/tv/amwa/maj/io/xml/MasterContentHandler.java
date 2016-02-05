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
 * $Log: MasterContentHandler.java,v $
 * Revision 1.8  2011/01/26 11:50:34  vizigoth
 * Completed common method testing.
 *
 * Revision 1.7  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/15 14:16:08  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.3  2008/01/14 20:57:43  vizigoth
 * Removed unused ProductIdentification and associated XML handler.
 *
 * Revision 1.2  2007/12/04 09:41:30  vizigoth
 * Minor formating changes.
 *
 * Revision 1.1  2007/11/13 22:14:56  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.io.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import tv.amwa.maj.industry.AAFSpecifiedClasses;

// TODO comments and test

/** 
 *
 * <p>The master content handler is the root of all {@linkplain ContentHandler SAX content handlers}
 * used to deserialize XML documents and fragments into object instances of this AAF implementation.
 * The SAX parser included with the core Java APIs provides an efficient, event-driven approach
 * to parsing XML documents. This class and its subclasses provide content handlers that respond
 * to the parser's generated events and create object instances. This content handler is designed
 * so that every object that can be deserialized from a known XML element name can do so by first
 * registering an appropriate handler.</p>
 * 
 * <p>To make use of the parsing capabilities of a master content handler, call 
 * {@link XMLBuilder#createFromXML(InputSource)}. This will create a master content handler, 
 * parse the input source and return any resulting object.</p>
 * 
 * </p>All classes that provide content handlers are capable of creating instances from XML fragments
 * must register themselves with the static handler registry of this class using 
 * {@link #registerHandler(Class)}. The handler must extend {@link LocalHandler}, 
 * which is in turn a direct subclass of this class. To intiialize all the XML handlers required to
 * parse AAF XML files, call {@link #registerCoreHandlers()}.</p>
 * 
 * <p>One approach that can be taken to providing
 * an element specific handler is to implement a static inner class within the object to
 * be created from the given element. For example:</p>
 * 
 * <pre>
 * public class VersionNumber {
 * 
 *   ...
 *   
 *   public final static class XMLHandler
 *   	extends tv.amwa.maj.io.xml.LocalHandler 
 *   	implements org.xml.sax.ContentHandler.ContentHandler {
 *   
 *      ...
 *    }
 *    ...   
 *  }
 * </pre>
 * 
 * <p>More information on how to do this is provided in the documentation for the {@link LocalHandler}
 * class.</p>
 *
 *
 *
 */
public class MasterContentHandler
	implements ContentHandler,
		AAFSpecifiedClasses {

	/** <p></p> */
	private final Map<String, Class<? extends LocalHandler>> handlers =
		Collections.synchronizedMap(new HashMap<String, Class<? extends LocalHandler>>());
	
	private final static boolean DIAGNOSTICS = false;
	
	/** <p></p> */
	private XMLReader xmlReader = null;
	/** <p></p> */
	private Object result = null;
	/** <p></p> */
	private MasterContentHandler rootHandler;
	/** <p></p> */
	private InputSource inputSource = null;
	/** <p></p> */
	private Map<String, InputStream> streams = null; 
	/** <p>The attributes of the element associated with this content handler.</p> */
	private Map<String, String> attributes;
	/** <p>Content handler for the element that is the parent to the element associated with
	 * this content handler.</p> */
	private MasterContentHandler parentHandler;
	/** <p>Name of the element handled by this content handler.</p> */
	private String elementName;
	/** <p>Namespace of the element.</p> */
	private String namespace = null;

	/**
	 * <p>Create a new master content handler. This method is called by 
	 * {@link #makeHandler(String, MasterContentHandler, Attributes)} to create instances
	 * of content handlers for sub elements.</p>
	 *
	 */
	MasterContentHandler() { 
		registerCoreHandlers();
	}
	
	/**
	 * <p>Create a new master content handler for the given XML input source. The input source
	 * should be a well-formed XML document containing one complete element.</p>
	 *
	 * @param input Input source containing an XML document or fragment to deserialize into a 
	 * Java object.
	 * 
	 * @throws SAXException Exception thrown by the SAX XML parser.
	 * @throws NullPointerException The input source argument is <code>null</code>.
	 */
	MasterContentHandler(
			InputSource input) 
		throws SAXException,
			NullPointerException {
		
		registerCoreHandlers();
		setInputSource(input);
		setUpParser();
	}
	
	MasterContentHandler(
			InputSource input,
			Map<String, InputStream> streams) 
		throws SAXException,
			NullPointerException {
		
		registerCoreHandlers();
		setInputSource(input);
		setUpParser();
		this.streams = streams;
	}
			
	
	final void setUpParser() 
		throws SAXException {
		
		// namespaceContext = Collections.synchronizedMap(new HashMap<String, String>());
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			SAXParser saxParser = factory.newSAXParser();
			xmlReader = saxParser.getXMLReader();
			xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);
			xmlReader.setContentHandler(this);
		} catch (ParserConfigurationException e) {
			// This configuration is known to be valid.
			e.printStackTrace();
		} 
	}
	
	/**
	 * <p>Execute a parse of the given input source that should result in a deserialized version of 
	 * the XML as the result ({@link #getResult()}) from the input source set for this master content 
	 * handler.</p>
	 *
	 * @throws SAXException SAX XML parse exception when reading input source.
	 * @throws IOException An input/output exception occurred when parsing the XML input source.
	 */
	final void parse()
		throws SAXException,
			IOException {

		result = null;
		xmlReader.parse(inputSource);
	}
	
	/**
	 * <p>Sets the input source for this master content handler.</p>
	 * 
	 * <p>This method could be used to create a pool of master content handlers with calls like:</p>
	 * 
	 * <pre>
	 *    MasterContentHandler mch = new MasterContentHandler(inputSource1);
	 *    mch.parse()
	 *    Object object1 = mch.getResult();
	 *    
	 *    mch.setInputSource(inputSource2);
	 *    mch.parse();
	 *    Object object2 = mch.getResult();
	 * </pre>
	 *
	 * @param source New input source for this master content handler.
	 * 
	 * @throws NullPointerException The input source argument is <code>null</code>.
	 */
	final void setInputSource(
			InputSource source) 
		throws NullPointerException {
		
		if (source == null)
			throw new NullPointerException("Cannot parse a null input source.");
		
		this.inputSource = source;
	}
	
	/** 
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(
			char[] ch,
			int start,
			int length)
			throws SAXException {

		if (DIAGNOSTICS)
			System.out.println("INFO: io.xml.MasterContentHandler: characters() called.");
	}

	/** 
	 * <p>Called at the end of the document represented by the input source of this master content
	 * handler. The result of the master content handler parse is the result of its only subelement.</p>
	 * 
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument()
			throws SAXException {

		if (rootHandler == null)
			throw new SAXException("Unexpected end of XML document.");
		result = rootHandler.getResult();
		if (result == null)
			throw new SAXException("Unexpected null result for handler " + getClass().getName() + ".");
	}

	/** 
	 * <p>Called if an end element event is sent by the SAX parser to a master content handler. This
	 * should not happen and so an exception will be thrown.</p>
	 * 
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(
			String uri,
			String localName,
			String name)
			throws SAXException {

		throw new SAXException("Master document should not handle any element ends.");

	}

	/** 
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	public final void endPrefixMapping(
			String prefix)
			throws SAXException {

		if (DIAGNOSTICS)
			System.out.println("INFO: io.xml.MasterContentHandler: endPrefixMapping() called.");
	}

	/** 
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public final void ignorableWhitespace(
			char[] ch,
			int start,
			int length)
			throws SAXException {

		if (DIAGNOSTICS)
			System.out.println("INFO: io.xml.MasterContentHandler: ignorableWhitespace() called.");
	}

	/** 
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
	 */
	public final void processingInstruction(
			String target,
			String data)
			throws SAXException {

		if (DIAGNOSTICS)
			System.out.println("INFO: io.xml.MasterContentHandler: processingInstruction() called.");
	}

	/** 
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	public final void setDocumentLocator(
			Locator locator) {

		if (DIAGNOSTICS)
			System.out.println("INFO: io.xml.MasterContentHandler: setDocumentLocator() called.");
	}

	/** 
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	public final void skippedEntity(
			String name)
			throws SAXException {

		if (DIAGNOSTICS)
			System.out.println("INFO: io.xml.MasterContentHandler: skippedEntity() called.");

	}

	/** 
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public final void startDocument()
			throws SAXException {

		if (DIAGNOSTICS)
			System.out.println("INFO: io.xml.MasterContentHandler: startDocument() called.");
	}

	/**
	 * <p>Called by the SAX parser when the root element of the input source of this master
	 * content handler has been read. A sub element content handler is created from the 
	 * register of handlers and parsing of the document continues using this newly created
	 * handler.</p>
	 *  
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(
			String uri,
			String localName,
			String name,
			Attributes atts)
		throws SAXException {

		rootHandler = makeHandler(uri, localName, this, atts);
		xmlReader.setContentHandler(rootHandler);
	}

	/**
	 * <p>Creates and returns a handler for the element of the given name that is ready to
	 * use to parse the element by calling {@link XMLReader#setContentHandler(ContentHandler)}.
	 * The attributes attached to the element name can be provided to aid in the creation of
	 * deserialized objects. A parent handler must be provided to ensure that parsing can continue 
	 * once the element itself has completed.</p>
	 * 
	 * <p>Handlers are instantiated using the register of handler classes created by calling
	 * {@link #registerHandler(Class)}. Alternatively, all the classes required to parse
	 * AAF XML can be registered using {@link #registerCoreHandlers()}.<p>
	 *
	 * @param namespace Namespace for the element to create a handler for.
	 * @param elementName Name of the element to create a content handler for.
	 * @param parentHandler Content handler for the parent XML element to the element the new 
	 * handler is to parse.
	 * @param attributes Attributes attached to the start element tag that may be required by
	 * the new content handler.
	 * @return Newly created content handler for the given element type.
	 * 
	 * @throws NullPointerException The element name and/or parent handler arguments is/are
	 * <code>null</code>.
	 * @throws SAXException No content handler is registered for the given element name.
	 */
	final MasterContentHandler makeHandler(
			String namespace,
			String elementName,
			MasterContentHandler parentHandler,
			Attributes attributes) 
		throws NullPointerException,
			SAXException {
		
		if (elementName == null)
			throw new NullPointerException("Cannot find a handler in the registry using null element name.");
		if (parentHandler == null)
			throw new NullPointerException("Cannot make a new handler without a parent handler.");
		
		Class<? extends MasterContentHandler> handlerClass = handlers.get(elementName);
		if (handlerClass == null)
			handlerClass = MetadataObjectHandler.class;
		MasterContentHandler handler = null;

		try {
			handler = handlerClass.newInstance();
		} 
		catch (InstantiationException e) {
			if (DIAGNOSTICS) {
				System.err.println("Could not instanciate a handler for " + elementName + ": " + e.getMessage());
				e.printStackTrace();
			}
		} 
		catch (IllegalAccessException e) {
			if (DIAGNOSTICS) {
				System.err.println("Could not instanciate a handler for " + elementName + " due to an illegal access exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		handler.setParentHandler(parentHandler);
		handler.setAttributes(attributes);
		handler.setElementName(namespace, elementName);
		return handler;
	}
	
	/** 
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
	 */
	public final void startPrefixMapping(
			String prefix,
			String uri)
			throws SAXException {
		
		if (DIAGNOSTICS)
			System.out.println("INFO: io.xml.MasterContentHandler: startPrefixMapping() called.");
	}
	
	/**
	 * <p>Retrieve the object resulting from the deserialization of an XML document or fragment
	 * carried out by this handler.</p>
	 *
	 * @return Object resulting from the deserialization of an XML document or fragment
	 * carried out by this handler, or <code>null</code> if the deserialization has not been
	 * successful.
	 */
	final Object getResult() {
		
		return result;
	}

	/**
	 * <p>Set the result of the deseriaization of an XML document or fragment carried out by
	 * this handler as soon as the result is established. All subclasses of this class must
	 * call this method when a result has been instantiated. Typically this is the last call
	 * of an {@link #endElement(String, String, String) endElement()} methods implementation.</p>
	 *
	 * @param result Resulting object following the deserialization of an XML fragment or document. 
	 */
	final void setResult(
			Object result) {
		
		this.result = result;
	}

	/**
	 * <p>Registers an XML handling class for a given element name. The handler must be class
	 * that extends {@link LocalHandler}. If a handler is already registered for the given
	 * element name, it is not replaced. This is to ensure that if {@link #registerCoreHandlers()}
	 * has been called, core handlers are not overridden. If the given handler is successfully
	 * registered, <code>true</code> is returned, otherwise <code>false</code>.<p>
	 * @param handler Content handler for the given element name.
	 *
	 * @return Has the given content handler been registered successfully?
	 * 
	 * @throws NullPointerException The handler argument is <code>null</code>.
	 * @throws IllegalArgumentException The given handler does not contain an accessible
	 * "<code>elementName</code>" static string value.
	 */
	public final boolean registerHandler(
			Class<? extends LocalHandler> handler) 
		throws NullPointerException,
			IllegalArgumentException {

		if (handler == null)
			throw new NullPointerException("Cannot register a content handler using a null value.");
		
		String elementName;
		try {
			Field elementNameField = handler.getField("elementName");
			elementName = (String) elementNameField.get(null);
			
			if (!(handlers.containsKey(elementName))) {
				handlers.put(elementName, handler);
				return true;
			}
		} 
		catch (IllegalArgumentException e) {
			// Element name could not be extracted, so return false
			if (DIAGNOSTICS) {
				System.err.println("Could not register class " + handler.getClass().getName() + " due to an illegal argument exception: " + e.getMessage());
				e.printStackTrace();
			}
		} 
		catch (IllegalAccessException e) {
			// Element name could not be extracted, so return false
			if (DIAGNOSTICS) {
				System.err.println("Could not register class " + handler.getClass().getName() + " due to an illegal access exception: " + e.getMessage());
				e.printStackTrace();
			}
			throw new IllegalArgumentException("The given handler does not contain an accessible elementName static string value.");
		} 
		catch (SecurityException e) {
			// Element name could not be extracted, so return false
			if (DIAGNOSTICS) {
				System.err.println("Could not register class " + handler.getClass().getName() + " due to a security exception: " + e.getMessage());
				e.printStackTrace();
			}
			throw new IllegalArgumentException("The given handler does not contain an accessible elementName static string value.");
		} 
		catch (NoSuchFieldException e) {
			// Element name could not be extracted, so return false
			if (DIAGNOSTICS) {
				System.err.println("Could not register class " + handler.getClass().getName() + " as the element name field is not present.");
				e.printStackTrace();
			}
			throw new IllegalArgumentException("The given handler does not contain an accessible elementName static string value.");
		}

		return false;
	}
	
	/**
	 * @param elementName
	 * @return
	 */
	Class<? extends LocalHandler> getHandlerClassForName(
			String elementName) {
		
		return handlers.get(elementName);
	}
	
	@SuppressWarnings("unchecked")
	public final void registerHandlersForClass(
			Class<?> containsHandler) {
		
		Class<?>[] innerClasses = containsHandler.getClasses();
		for (Class<?> innerClass : innerClasses ) {
			if (Modifier.isAbstract(innerClass.getModifiers())) continue;
			if (LocalHandler.class.isAssignableFrom(innerClass)) {
				registerHandler((Class<? extends LocalHandler>) innerClass);
				// System.out.println(innerClass.getCanonicalName());
			}
		}
	}
	
	/** <p>List of the content handlers not attached to the core classes. This
	 * list is provided to avoid bootstrapping issues of reading XML elements in AAF XML
	 * before the associated class has been loaded.</p> */
	private final static Class<?>[] coreHandlers =
		new Class<?>[] {
			tv.amwa.maj.record.impl.ProductVersionImpl.XMLHandler.class,
			tv.amwa.maj.record.impl.RGBAComponentImpl.class,
			// tv.amwa.maj.io.xml.AAFElement.AAFHandler.class
		};
	
	/**
	 * <p>Registers handlers for the core elements of AAF XML. To extend the range of elements
	 * parsed by this master content handler, call {@link #registerHandler(Class)}.</p>
	 *
	 */
	@SuppressWarnings("unchecked")
	public final void registerCoreHandlers() {
		
		for ( Class<?> handler : coreHandlers ) {
			
			try {
				Class<? extends LocalHandler> localHandler = (Class<? extends LocalHandler>) handler;
				registerHandler(localHandler);
			}
			catch (ClassCastException cce) {
				if (DIAGNOSTICS) {
					System.err.println("Could not register a core class for the master content handler due to a class cast exception: " + cce.getMessage());
					cce.printStackTrace();
				}
			} catch (SecurityException e) {
				if (DIAGNOSTICS) {
					System.err.println("Could not register a core class for the master content handler due to a security exception: " + e.getMessage());
					e.printStackTrace();
				}
			} catch (IllegalArgumentException e) {
				if (DIAGNOSTICS) {
					System.err.println("Could not register a core class for the master content handler due to an illegal argument exception: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}

		for ( Class<?> classWithHandlers : interchangeable )
			registerHandlersForClass(classWithHandlers);
		
		for ( Class<?> classWithHandlers : meta )
			registerHandlersForClass(classWithHandlers);
		
	}
	
	/**
	 * <p>Introduced for testing local handlers.</p>
	 *
	 * @return Root handler of this master content handler.
	 */
	final MasterContentHandler getRootHandler() {
		
		return rootHandler;
	}
	
	/**
	 * <p>Returns the XML reader associated with this master content handler or the parent handler.</p>
	 *
	 * @return XML reader associated with this master content handler, or <code>null</code> if the
	 * parser is not yet set up.
	 */
	final XMLReader getXMLReader() {
		
		if (xmlReader != null)
			return xmlReader;
		if (parentHandler != null)
			return parentHandler.getXMLReader();
		return null;
	}

	/**
	 * <p>Return the input stream with the given name from the collection of streams that
	 * has been attached to this content handler.</p>
	 * 
	 * @param streamName Name of the input stream to retrieve.
	 * @return Input stream associated with the given name, or <code>null</code> if not
	 * input streams are available or the given name does not map to an input stream.
	 */
	InputStream getInputStreamForName(
			String streamName) {
		
		if (streams == null) return null;
		
		return streams.get(streamName);
	}
	
	/**
	 * <p>Set the attributes of the element associated with this content handler.</p>
	 *
	 * @param atts Attributes of the element associated with this content handler.
	 */
	final void setAttributes(
			Attributes atts) {

		if (atts == null) // Introduced for testing
			this.attributes = null;
		else
			this.attributes = makeAttributeMap(atts);
		
	}
	

	/**
	 * <p>Returns the attributes of the element associated with this content handler.</p>
	 *
	 * @return Attributes of the element associated with this content handler.
	 */
	public final Map<String, String> getAttributesForThis() {
		
		return attributes;
	}
	
	/**
	 * <p>Make a map of attribute names to attribute values from the given {@link org.xml.sax.Attributes}
	 * value. The value of attributes is transient and only remains consistent for one SAX event. 
	 * Therefore, it is necessary to copy out the name/value pairs of the attributes for subsequence
	 * access when subsequently deserializing a parent element.</p>
	 *
	 * @param attributes Set of attributes to use to create a name to value map.
	 * @return Name to value map created from the given attributes set.
	 * 
	 * @throws NullPointerException The attributes argument is <code>null</code>.
	 */
	final static Map<String, String> makeAttributeMap(
			Attributes attributes) 
		throws NullPointerException {
		
		if (attributes == null)
			throw new NullPointerException("Cannot create an attribute map using a null attributes set.");
		
		int attributesLength = attributes.getLength();
		// Don't create a map unless one is needed.
		if (attributesLength == 0) return null;		

		Map<String, String> attributeMap = new HashMap<String, String>(attributesLength);
		
		for ( int x = 0 ; x < attributesLength ; x++ )
			attributeMap.put(attributes.getQName(x), attributes.getValue(x));
		
		return attributeMap;
	}
	
	/**
	 * <p>Retrieve the value of an attribute of the element associated with this local handler.
	 * The value of the attribute may not be available for one of two reasons:</p>
	 * 
	 * <ul>
	 *  <li>No attributes are defined for the element associated with this local handler.</li>
	 *  <li>An attribute of the given name was not defined for the element.</li>
	 * </ul>
	 *
	 * @param attributeName Name of an attribute of the element associated with this local handler.
	 * @return Value of the attribute with the given name, or <code>null</code> is the value
	 * of the attribute is not available.
	 * 
	 * @throws NullPointerException The attribute name argument is <code>null</code>.
	 */
	public final String getAttributeValueThisElement(
			String attributeName) 
		throws NullPointerException {
		
		if (attributeName == null)
			throw new NullPointerException("Cannot retrieve an attribute value from a null name.");
		
		if (attributes != null)
			return attributes.get(attributeName);
		
		return null;
	}
	
	final void setParentHandler(
			MasterContentHandler parentHandler) {
		
		this.parentHandler = parentHandler;
	}
	
	final MasterContentHandler getParentHandler() {
		
		return parentHandler;
	}
	
	/**
	 * <p>Sets the element name of the element associated with this content handler. This should
	 * be the same as the "<code>elementName</code>" static value of the content handler.</p>
	 *
	 * @param elementName Element name of the element associated with this content handler.
	 */
	void setElementName(
			String elementName) {
		
		this.elementName = elementName;
		this.namespace = null;
	}
	
	/**
	 * <p>Sets the namespace and element name of the element associated with this content handler. This should
	 * be the same as the "<code>elementName</code>" static value of the content handler.</p>
	 *
	 * @param elementName Element name of the element associated with this content handler.
	 */
	void setElementName(
			String namespace,
			String elementName) {
		
		this.elementName = elementName;
		this.namespace = namespace;
	}

	/**
	 * <p>Returns the element name of the element associated with this content handler. This will
	 * be the same as the "<code>elementName</code>" static value of the content handler.</p>
	 *
	 * @return Element name of the element associated with this content handler.
	 */
	public final String getElementName() {
		
		return elementName;
	}
	
	public final String getNamespace() {
		
		return namespace;
	}
	
	public final String getFullElementName() {
		
		if (namespace == null)
			return elementName;
		return "{" + namespace + "}" + elementName;
	}
}
