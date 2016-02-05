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
 * $Log: MediaProperty.java,v $
 * Revision 1.6  2011/07/27 12:25:44  vizigoth
 * Fixed import warning messages.
 *
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.3  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/02/10 23:54:40  vizigoth
 * Improved support for carrying descriptions in annotations.
 *
 * Revision 1.1  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.3  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.2  2007/12/12 12:49:05  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.1  2007/11/13 22:13:15  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.industry;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tv.amwa.maj.meta.PropertyDefinition;

/**
 * <p>Labels a get method of a Java class to indicate that it provides access to
 * the {@linkplain PropertyValue property value} of an {@linkplain MediaClass AAF class}.
 * The annotation is used to create a {@linkplain tv.amwa.maj.meta.PropertyDefinition
 * property definition} for the property at runtime, enabling the dynamic extension of
 * a meta dictionary. The {@linkplain tv.amwa.maj.meta.PropertyDefinition#getMemberOf()
 * class membership of a property} is determined from the Java class of the annotated method.</p>
 * 
 * <p>For singleton values and records, this labelled method should return an object 
 * representing that value. For compound values such as variable sized arrays and sets, 
 * a complete collection of values will be returned. For example, here are two methods 
 * labelled as AAF properties:</p>
 * 
 * <ul>
 *  <li>{@link tv.amwa.maj.model.Track#getTrackID() Track.getTrackID()} returns a single integer value 
 *  representing identity of the track for a property of type {@link tv.amwa.maj.integer.UInt32};</li>
 *  <li>{@link tv.amwa.maj.model.Package#getPackageTracks() Package.getPackageTracks()} returns the list of tracks within 
 *  a package for a property of type "StrongReferenceVector of Track".</li>
 * </ul>
 * 
 * <p>A {@linkplain #symbol() symbol name} must be provided which will be used to name an 
 * {@linkplain org.w3c.dom.Element element} when writing or reading the property to an XML representation. Normally, the 
 * namespace for the element will be the same for the {@linkplain MediaClass class} that the property 
 * is a member of. A {@linkplain #namespace() namespace} can be specified to override this if
 * required. Other aspects of serialization to and from XML can be configured with {@link #isXMLCDATA()},
 * {@link #isXMLAttribute()} and {@link #flattenXML()}.</p>
 * 
 * @see tv.amwa.maj.meta.PropertyDefinition
 * @see tv.amwa.maj.meta.ClassDefinition#getProperties(tv.amwa.maj.industry.MetadataObject)
 * @see tv.amwa.maj.industry.PropertyValue
 * @see MediaPropertySetter
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionWeakReferenceSet
 * 
 *
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface MediaProperty {

	/**
	 * <p>Provides the first&nbsp;4 bytes of the unique {@linkplain tv.amwa.maj.record.AUID AUID} identification 
	 * of this property as a UUID. This is bytes 9&nbsp;to&nbsp;12 if the identification is a SMPTE universal label.</p>
	 * 
	 * @return First 4 bytes of the identification of this property.
	 * 
	 * @see tv.amwa.maj.record.AUID#getData1()
	 * @see tv.amwa.maj.meta.MetaDefinition#getAUID()
	 */
	int uuid1();
	
	/**
	 * <p>Provides bytes 5&nbsp;and&nbsp;6 of the unique {@linkplain tv.amwa.maj.record.AUID AUID} identification 
	 * of this property as a UUID. This is bytes 13&nbsp;and&nbsp;14 if the identification is a SMPTE universal label.</p>
	 * 
	 * @return Bytes 5&nbsp;and&nbsp;6 of the identification of this property.
	 * 
	 * @see tv.amwa.maj.record.AUID#getData2()
	 * @see tv.amwa.maj.meta.MetaDefinition#getAUID()
	 */
	// Wanted this to be short but fails JPA generation due to AnnotationTypeMismatchException
	int uuid2();
	
	/**
	 * <p>Provides bytes 7&nbsp;and&nbsp;8 of the unique {@linkplain tv.amwa.maj.record.AUID AUID} identification 
	 * of this property as a UUID. This is bytes 15&nbsp;and&nbsp;16 if the identification is a SMPTE universal label.</p>
	 * 
	 * @return Bytes 7&nbsp;and&nbsp;8 of the identification of this property.
	 * 
	 * @see tv.amwa.maj.record.AUID#getData3()
	 * @see tv.amwa.maj.meta.MetaDefinition#getAUID()
	 */
	// Wanted this to be short but fails JPA generation due to AnnotationTypeMismatchException
	int uuid3();
	
	/**
	 * <p>Provides bytes 9&nbsp;to&nbsp;16 of the unique {@linkplain tv.amwa.maj.record.AUID AUID} identification 
	 * of this property as a UUID. This is bytes 1&nbsp;to&nbsp;8 if the identification is a SMPTE universal label.</p>
	 * 
	 * @return Bytes 9&nbsp;to&nbsp;16 of the identification of this property.
	 * 
	 * @see tv.amwa.maj.record.AUID#getData4()
	 * @see tv.amwa.maj.meta.MetaDefinition#getAUID()
	 */
	byte[] uuid4();
	
	/**
	 * <p>Provides the registered name of this property within its class. The registered
	 * name must be unique within the class.</p>
	 * 
	 * @return Registered name for this property.
	 * 
	 * @see tv.amwa.maj.meta.MetaDefinition#getName()
	 * @see #symbol()
	 * @see tv.amwa.maj.meta.ClassDefinition#lookupPropertyDefinition(String)
	 */
	String definedName();

	/**
	 * <p>Provides alternative names that this property may be known by. The namespace for property names 
	 * is unique within their member class. Take care not to overwrite the definition of another property. 
	 * A warning will be generated if an alias overwrites a defined property name.</p>
	 * 
	 * <p>As an example, the <em>Name</em> property of a {@linkplain tv.amwa.maj.model.TextLocator text locator} 
	 * may be specified with symbols <em>TextLocatorName</em> and <em>LocationName</em>.</p>
	 * 
	 * @return List of alternative names for this property.
	 * 
	 * @see tv.amwa.maj.meta.ClassDefinition#lookupPropertyDefinition(String)
	 */
	String[] aliases() default { };

	/**
	 * <p>Provides the name of the {@linkplain tv.amwa.maj.meta.TypeDefinition type} that
	 * specifies values of this property. In the MAJ API, type names are resolved to using the 
	 * {@link tv.amwa.maj.industry.Warehouse#lookForType(String)} method.</p>
	 * 
	 * @return Type name for the type sepcifying values of this proeprty.
	 * 
	 * @see tv.amwa.maj.industry.Warehouse#lookForType(String)
	 * @see tv.amwa.maj.meta.PropertyDefinition#getTypeDefinition()
	 * @see tv.amwa.maj.industry.TypeDefinitions#TypeDefinitionWeakReference
	 */
	String typeName();

	/**
	 * <p>Indicates whether this property is optional. An optional property can be omitted
	 * from any specification of an instance of its defining class.</p>
	 * 
	 * @return Is the property optional?
	 * 
	 * @see tv.amwa.maj.meta.PropertyDefinition#getIsOptional()
	 * @see tv.amwa.maj.exception.PropertyNotPresentException
	 */
	boolean optional();

	/**
	 * <p>Indicates whether this property is a unique identifier for the class it is contained
	 * within.</p>
	 * 
	 * @return Is this property a unique identifier for its defining class?
	 * 
	 * @see tv.amwa.maj.meta.PropertyDefinition#getIsUniqueIdentifier()
	 */
	boolean uniqueIdentifier();
	
	/**
	 * <p>Prvoides the local identification for the property. This may be used within a serialized value
	 * of the property to identify the property efficiently, as in MXF. If the property is
	 * omitted or set to zero, the local identification must be determined dynamically at
	 * runtime.</p>
	 * 
	 * @return Local identification for this property.
	 * 
	 * @see tv.amwa.maj.meta.PropertyDefinition#getLocalIdentification()
	 */
	// Wanted this to be short but fails JPA generation due to AnnotationTypeMismatchException
	int pid() default 0x0000;
	
	/**
	 * <p>Provides the registered symbol used for this property when serialized to XML.
	 * The symbol should not include a namespace declaration. Symbols must be unique
	 * within a namespace whereas defined names only have to be unique within a class.</p>
	 * 
	 * @return Registered symbol for the property.
	 * 
	 * @see #namespace()
	 */
	String symbol();
	
	/**
	 * <p>Provides a description of the property.</p>
	 * 
	 * @return Description of the property.
	 * 
	 * @see MediaClass#description()
	 */
	String description() default "";
	
	/** 
	 * <p>Provides the XML namespace for this property. If none is defined, the one for the 
	 * {@linkplain MediaClass class} the property is a member of is used. When serialized to XML, the property
	 * will have the following <code>xmlns:</code> attribute included in its element name:</p>
	 * 
	 * <center><code>xmlns:</code>&lt;<em>namespaceAbbreviation</em>&gt;<code>=&quot;</code>&lt;<em>namespace</em>&gt;<code>&quot;</code></center>
	 * 
	 * @return Registered XML namespace for an element representing this property.
	 * 
	 * @see #prefix()
	 * @see MediaClass#namespace()
	 * @see #symbol()
	 */
	String namespace() default "";
	
	/** 
	 * <p>Provides the XML namespace prefix abbreviation for this property. If none is defined, the one for 
	 * the {@linkplain MediaClass class} the property is a member of is used.When serialized to XML, the property
	 * will have the following <code>xmlns:</code> attribute included in its element name:</p>
	 * 
	 * <center><code>xmlns:</code>&lt;<em>namespaceAbbreviation</em>&gt;<code>=&quot;</code>&lt;<em>namespace</em>&gt;<code>&quot;</code></center>
	 * 
	 * @return XML namespace for an element representing this property.
	 * 
	 * @see #namespace()
	 * @see MediaClass#prefix()
	 */
	String prefix() default "";
	
	/**
	 * <p>Determines whether an XML serialization of this value should appear as the CDATA content of the parent
	 * element rather than contained within its own element.</p>
	 * 
	 * <p>Values are normally serialized to an element as follows:</p>
	 * 
	 * <pre>
	 * &lt;Parent&gt;
	 *   &lt;Child1&gt;value1&lt;/Child1&gt;
	 *   &lt;Child2&gt;value2&lt;/Child2&gt;
	 *   ...
	 * &lt;/Parent&gt;</pre>
	 * 
	 * <p>If this property has the symbol <code>Child2</code>, setting this flag to <code>true</code> causes the value of the
	 * property to be inserted as CDATA of element <code>Parent</code>:</p>
	 * 
	 * <pre>
	 * &lt;Parent&gt;
	 *   &lt;Child1&gt;value1&lt;/Child1&gt;
	 *   value2
	 *   ...
	 * &lt;/Parent&gt;</pre>
	 * 
	 * @return Should the value of this property appear as the CDATA content of the parent element?
	 */
	boolean isXMLCDATA() default false;
	
	/**
	 * <p>Determines whether an XML serialization of this value should appear as an attribute of the parent
	 * element rather than contained within its own element. Only elements of the following types are suitable
	 * for use as attributes:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain tv.amwa.maj.meta.TypeDefinitionInteger integer type};</li>
	 *  <li>{@linkplain tv.amwa.maj.meta.TypeDefinitionString string type};</li>
	 *  <li>{@linkplain tv.amwa.maj.meta.TypeDefinitionRename rename type} when it renames another type in this list;</li>
	 *  <li>{@linkplain tv.amwa.maj.meta.TypeDefinitionEnumeration enumeration type};</li>
	 *  <li>{@linkplain tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration extendible enumeration type};</li>
	 *  <li>{@linkplain tv.amwa.maj.meta.TypeDefinitionRecord record type} when the underlying value has a representation as a simple string,
	 *  such as {@linkplain tv.amwa.maj.record.AUID#toString() AUID}.</li>
	 * </ul>
	 * 
	 * <p>Values are normally serialized to an element as follows:</p>
	 * 
	 * <pre>
	 * &lt;Parent&gt;
	 *   &lt;Child1&gt;value1&lt;/Child1&gt;
	 *   &lt;Child2&gt;value2&lt;/Child2&gt;
	 *   ...
	 * &lt;/Parent&gt;</pre>
	 * 
	 * <p>If this property has the symbol <code>Child2</code>, setting this flag to <code>true</code> causes the value of the
	 * property to be set as an attribute of <code>Parent</code>:</p>
	 * 
	 * <pre>
	 * &lt;Parent Child2="value2"&gt;
	 *   &lt;Child1&gt;value1&lt;/Child1&gt;
	 *   ...
	 * &lt;/Parent&gt;</pre>
	 * 
	 * @return Should the value of this property appear as an attribute of the parent element?
	 */
	boolean isXMLAttribute() default false;
	
	/**
	 * <p>Determines whether an XML serialization of referenced values or collections of 
	 * values should appear as a replacement for this element rather than contained within it.</p>
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
	 * <p>Setting this flag to <code>true</code> causes the values of the children of this
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
	 * @return Should the value of this property appear as the CDATA content of the parent element?
	 */
	boolean flattenXML() default false;
	
	/**
	 * <p>Provides a weighting value for ordering property definitions or values when serialized to XML or listed in
	 * a collection. The property with the lowest weight appears first. If this value is omitted, a value is
	 * computed from the order of the property definition within the class hierarchy and defining class.</p>
	 * 
	 * @return Relative weight for ordering the property in a list.
	 * 
	 * @see PropertyDefinition#getWeight()
	 */
	int weight() default 0;
}
