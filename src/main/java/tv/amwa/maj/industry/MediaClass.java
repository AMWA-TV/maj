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
 * $Log: MediaClass.java,v $
 * Revision 1.7  2011/10/05 17:14:25  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.6  2011/07/27 12:25:44  vizigoth
 * Fixed import warning messages.
 *
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/02/10 23:54:40  vizigoth
 * Improved support for carrying descriptions in annotations.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.4  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.2  2007/12/12 12:49:05  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.1  2007/11/13 22:13:23  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.industry;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>Labels a Java class as one suitable for use as an AAF specified class, extension class or
 * class from another namespace compatible with SMPTE registration. It must be possible to derive 
 * the {@linkplain tv.amwa.maj.meta.ClassDefinition AAF meta-class definition} from the annotations provided. A class 
 * using the annotation must also have any get methods used to retrieve the value of {@linkplain tv.amwa.maj.meta.PropertyDefinition 
 * properties} annotated with {@link MediaProperty}.</p>
 * 
 * <p>Aspects of the structure of the Java implementation are used to provide the data used to create
 * a {@linkplain tv.amwa.maj.meta.ClassDefinition class definition}. This includes:</p>
 * 
 * <ul>
 *  <li>If the annotated class is a root class in a class hierarchy, the class definition will have a parent
 *  that references itself.
 *  <li>Otherwise, the class definition will have a parent that is the class
 *  definition of its Java parent. As a side effect, the parent Java class must also be labelled
 *  with this annotation.</li>
 *  <li>If the annotated class is abstract, its class definition will also be abstract.</li>
 *  <li>All the {@link MediaProperty} annotations within the annotated Java class become properties
 *  of the class definition.</li>
 * </ul>
 * 
 * <p>This information is collected on the first call to {@link tv.amwa.maj.industry.Warehouse#lookForClass(Class)} for
 * any Java class labelled with this annotation. A lookup table of this data is help statically
 * within the running virtual machine.</p>
 * 
 * <p>A {@linkplain #symbol() symbol name} must be provided which will be used to name an 
 * {@linkplain org.w3c.dom.Element element} when writing the class to an XML file. Normally, the 
 * namespace for the element will be the same for the parent class in the class hierarchy. A 
 * {@linkplain #namespace() namespace} can be specified to override this if
 * required.</p>
 * 
 * @see tv.amwa.maj.meta.ClassDefinition
 * @see tv.amwa.maj.industry.Warehouse#lookForClass(Class)
 * @see tv.amwa.maj.industry.Warehouse#lookForClass(String)
 * @see tv.amwa.maj.model.InterchangeObject#getObjectClass()
 * @see tv.amwa.maj.industry.AAFSpecifiedClasses
 * @see tv.amwa.maj.industry.TypeDefinitions#ClassDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ClassDefinitionStrongReference
 * 
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MediaClass {

	int uuid1();

	// Wanted this to be short but fails JPA generation due to AnnotationTypeMismatchException
	int uuid2();
	
	// Wanted this to be short but fails JPA generation due to AnnotationTypeMismatchException	
	int uuid3();

	byte[] uuid4();
	
	String definedName();
	
	/**
	 * <p>Alternative names that this class may be known by. The namespace for class names is flat
	 * so take care not to overwrite the definition of another class. A warning will be generated 
	 * if an alias overwrites a defined class name.</p>
	 * 
	 * <p>As an example, the root of the main AAF class hierarchy is defined as <em>InterchangeObject</em>
	 * and is known by the alias <em>AAFObject</em> in the C-based reference implementation.</p>
	 * 
	 * @return List of alternative names for this class.
	 */
	String[] aliases() default { };

	/**
	 * <p>Provides a description of the class.</p>
	 * 
	 * @return Description of the class.
	 * 
	 * @see MediaProperty#description()
	 */
	String description() default ""; 
	
	/**
	 * <p>Provides the registered symbol used for this class when serialized to XML.
	 * The symbol should not include a namespace declaration. Symbols must be unique
	 * within a namespace.</p>
	 * 
	 * @return Registered symbol for the class.
	 * 
	 * @see #namespace()
	 */
	String symbol();
	
	/** 
	 * <p>Provides the XML namespace for this class. If none is defined, the one for the parent
	 * {@linkplain MediaClass class} is used. Any member properties or child classes of the annotated class 
	 * will share the same namespace unless this is overwritten. When serialized to XML, the class
	 * will have the following <code>xmlns:</code> attribute included in its element name:</p>
	 * 
	 * <center><code>xmlns:</code>&lt;<em>namespaceAbbreviation</em>&gt;<code>=&quot;</code>&lt;<em>namespace</em>&gt;<code>&quot;</code></center>
	 * 
	 * @return Registered XML namespace for an element representing this class.
	 * 
	 * @see #prefix()
	 * @see #symbol()
	 * @see tv.amwa.maj.meta.ExtensionScheme#getSchemeURI()
	 * @see tv.amwa.maj.meta.MetaDefinition#getNamespace()
	 */
	String namespace() default "";
	
	/** 
	 * <p>Provides the XML namespace prefix abbreviation for this class. If none is defined, the one for the parent
	 * {@linkplain MediaClass class} is used. Any member properties or child classes of the annotated class 
	 * will share the same namespace unless this is overwritten. When serialized to XML, the class
	 * will have the following <code>xmlns:</code> attribute included in its element name:</p>
	 * 
	 * <center><code>xmlns:</code>&lt;<em>namespaceAbbreviation</em>&gt;<code>=&quot;</code>&lt;<em>namespace</em>&gt;<code>&quot;</code></center>
	 * 
	 * @return Registered XML namespace for an element representing this class.
	 * 
	 * @see #namespace()
	 * @see tv.amwa.maj.meta.ExtensionScheme#getPreferredPrefix()
	 * @see tv.amwa.maj.meta.MetaDefinition#getPrefix()
	 */
	String prefix() default "";
	
	/**
	 * <p>Determines how the class identifier of an XML element is serialized. For details, see the 
	 * description of the associated {@link EmitXMLClassIDAs} enumeration.</p>
	 * 
	 * @return How the class identifier of an XML element is serialized.
	 */
	EmitXMLClassIDAs emitXMLClassID() default EmitXMLClassIDAs.Parent;

	/**
	 * <p>Is this class a concrete class? If the Java class is abstract then the class definition record
	 * will be set to <code>false</code>. Otherwise, this annotation field will be used to determine
	 * the status of the class.</p>
	 * 
	 * @return Will be <code>true</code> if the defined class is concrete, otherwise <code>false</code>.
	 */
	boolean isConcrete() default true;
}
