/*
 * Copyright 2016 Advanced Media Workflow Assocation
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
 * $Log: InterchangeObject.java,v $
 * Revision 1.4  2011/10/05 17:14:27  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/26 11:49:26  vizigoth
 * Completed common method testing.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.10  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.9  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.8  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.7  2009/02/24 18:47:54  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.6  2009/02/06 17:03:20  vizigoth
 * Added new super-interface "MetadataObject".
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/01/27 11:07:39  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.3  2007/12/04 09:30:31  vizigoth
 * Minor comment updates.
 *
 * Revision 1.2  2007/11/15 12:52:48  vizigoth
 * Edits to ensure source can make rough and ready javadoc.
 *
 * Revision 1.1  2007/11/13 22:08:33  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.Set;

import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.exception.ObjectNotAttachedException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaEntity;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.record.AUID;

/**
 * <p>Specifies the root class for all AAF persistent classes. All classes 
 * defined in an AAF file shall be sub-classes of interchange object with the exception 
 * of the {@linkplain MetaDefinition meta definition} classes.</p>
 * 
 * <p>In addition to methods which all clients can use, it provides methods
 * for direct property access which should not be used unless the
 * client programmer is aware of the liabilities. Note that extending the set
 * of optional properties of a class is not fully supported in the current implementation
 * of the MAJ API.</p>
 * 
 * <p>Note that generation tracking is not automatically supported by the MAJ API.</p>
 * 
 *
 *
 */

public abstract interface InterchangeObject 
	extends MediaEntity {

	/**
	 * <p>Gets the generation of this object, which identifies when this object was created 
	 * or last modified. </p>
	 * 
	 * <p>This method will succeed if generation tracking is enabled for
	 * this object.  Call {@link #enableGenerationTracking()} and
	 * {@link #disableGenerationTracking()} to control generation tracking for
	 * this object.  Call {@link #isGenerationTracked()} to determine if
	 * generation tracking is currently enabled for this object.</p>
	 * 
	 * @return Identification for when this object was last modified.
	 * 
	 * @throws InvalidParameterException Generation tracking is not enabled 
	 * for this object.
	 * @throws ObjectNotAttachedException This object is not attached to a file 
	 * from which generation information can be obtained.
	 */
	public Identification getGeneration() 
		throws InvalidParameterException,
			ObjectNotAttachedException;

	/** 
	 * <p>Shortcut to get the {@linkplain tv.amwa.maj.record.AUID AUID} of the identification
	 * representing this object's generation.</p>
	 * 
	 * <p>This method will succeed if generation tracking is enabled for
	 * this object. Call {@link #enableGenerationTracking()} and
	 * {@link #disableGenerationTracking()} to control generation tracking for
	 * this object.  Call {@link #isGenerationTracked()} to determine if
	 * generation tracking is currently enabled for this object.</p>
	 * 
	 * @return AUID of the identification representing this object's generation.
	 * 
	 * @throws PropertyNotPresent Generation tracking is not enabled for 
	 * this object and so this optional property is omitted.
	 * @throws ObjectNotAttachedException This object is not attached to a file 
	 * from which generation information can be obtained.
	 * 
	 * @see #getGeneration()
	 * @see Identification#getLinkedGenerationID()
	 */
	public AUID getLinkedGenerationID()
		throws PropertyNotPresentException,
			   ObjectNotAttachedException;

	/**
	 * <p>Sets the linked generation identification for this object that represents this
	 * object's generation. In a file, all objects added or modified at the same time have
	 * the same generation identification and that generation is described by an
	 * {@linkplain Identification identification}.</p>
	 * 
	 * <p>Passing a <code>null</code> value into to this method will omit the linked
	 * generation property and disable generation tracking. Passing in a nil value will 
	 * indicate that this object is not attached to a file where its identification can
	 * be resolved. Passing in a non-nil value will enable generation tracking and 
	 * a means to resolve the identification should be provided.</p>
	 * 
	 * @param linkedGenerationID Linked generation identification for this interchangeable
	 * object.
	 * 
	 * @see #getLinkedGenerationID()
	 * @see #getGeneration()
	 * @see Identification#getLinkedGenerationID()
	 * @see Preface#getIdentifications()
	 */
	public void setLinkedGenerationID(
			AUID linkedGenerationID);
	
	/**
	 * <p>Returns the {@linkplain ClassDefinition class definition} that identifies the class 
	 * of this interchange object.</p>
	 * 
	 * @return Class definition that describes this object instance.
	 * 
	 * @see tv.amwa.maj.industry.MediaEngine#getClassDefinition(tv.amwa.maj.industry.MetadataObject)
	 * @see tv.amwa.maj.industry.TypeDefinitions#ClassDefinitionWeakReference
	 */
	public ClassDefinition getObjectClass();

	/**
	 * <p>Sets the {@linkplain ClassDefinition class definition} that identifies the class 
	 * of this interchange object.</p>
	 * 
	 * <p>Setting this property used by {@linkplain ApplicationPluginObject application 
	 * metadata plugins} to indicate the defined class of an extension to this class. In this
	 * case, the {@linkplain ApplicationObject#getBaseClass() base class} property can be used 
	 * to hold the identifier of this class.</p>
	 * 
	 * @param objectClass Class definition that describes this object instance.
	 * 
	 * @see tv.amwa.maj.meta.ExtensionScheme#getMetaDefinitions()
	 * @see ApplicationObject#getBaseClass()
	 */
	public void setObjectClass(
			ClassDefinition objectClass);
	
	// Removed next method as not relevant to the MAJ API ... a dictionary was not used to 
	// create this instance.
	
	/**
	 * <p>Returns the dictionary of the object's instance.</p>
	 * 
	 * <p><strong>Important note</strong>: This is a low-level method 
	 * that allows direct access to
	 * properties.  If such access is done, any semantic checking (such
	 * as that which is performed in all other named property Get/Set
	 * own risk.</p>
	 * 
	 * @return The dictionary of the object's instance.
	 * @throws ObjectNotAttachedException The given object is not currently attached to a file.
	 */
	// public Dictionary getDictionary();

	/**
	 * <p>Calling this method will cause generation tracking to be enabled
	 * for the object. Generation information will then be available
	 * through the {@link #getGeneration()} and {@link #getLinkedGenerationID()}
	 * methods.</p>
	 * 
	 * <p>Calling the {@link #disableGenerationTracking()} method will disable
	 * generation tracking for this object.</p>
	 * 
	 * <p>Generation tracking is disabled by default.</p>
	 */
	public void enableGenerationTracking();

	/**
	 * <p>Calling this method will cause generation tracking to be disabled
	 * for the object. Generation information will then not be
	 * available through the {@link #getGeneration()} and 
	 * {@link #getLinkedGenerationID()} methods.</p>
	 * 
	 * <p>Calling the {@link #enableGenerationTracking()} method will enable
	 * generation tracking for this object.</p>
	 * 
	 * <p>Generation tracking is disabled by default.</p>
	 */
	public void disableGenerationTracking();

	/**
	 * <p>Returns <code>true</code> if generations are being tracked by
	 * the object. A value of <code>true</code> indicates that generation
	 * information will be available through the {@link #getGeneration()}
	 * and {@link #getLinkedGenerationID()} methods.</p>
	 * 
	 * <p>Calling the {@link #enableGenerationTracking()} method will enable
	 * generation tracking for this object; calling the
	 * {@link #disableGenerationTracking()} method will disable generation
	 * tracking for this object.</p>
	 * 
	 * <p>Generation tracking is disabled by default.</p>
	 * 
	 * @see #enableGenerationTracking()
	 * @see #disableGenerationTracking()
	 */
	public @Bool boolean isGenerationTracked();
	
	/**
	 * <p>Returns the set of {@linkplain ApplicationPluginObject application metadata plugins} 
	 * attached to this interchange object, where each attached object contains application-specific 
	 * description. This is an optional property.</p>
	 * 
	 * @return Set of application metadata plugins attached to this object.
	 * 
	 * @throws PropertyNotPresentException The optional application plugins
	 * extension is not present for this interchange object.
	 * 
	 * @see tv.amwa.maj.meta.Root#getRootExtensions()
	 * @see ApplicationPluginObject#getApplicationScheme()
	 */
	public Set<ApplicationPluginObject> getApplicationPlugins()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Add an {@linkplain ApplicationPluginObject application metadata plugin}
	 * so as to this object to enhance its application-specific description.</p>
	 * 
	 * @param applicationPlugin Application plugin object to add to the set
	 * of application plugins.
	 * 
	 * @throws NullPointerException Cannot add a <code>null</code> application plugin
	 * object to the set of plugins.
	 * 
	 * @see #getApplicationPlugins()
	 */
	public void addApplicationPlugin(
			ApplicationPluginObject applicationPlugin)
		throws NullPointerException;
	
	/**
	 * <p>Determines whether the given {@linkplain ApplicationPluginObject application metadata plugin}
	 * instance is attached to this interchange object.</p>
	 * 
	 * @param applicationPlugin Application plugin object to check for.
	 * @return Is the given application plugin object attached to this interchange object?
	 * 
	 * @throws NullPointerException Cannot check for attachment using a <code>null</code> value.
	 * 
	 * @see #containsApplicationPlugin(AUID)
	 * @see #getApplicationPlugins()
	 */
	public boolean containsApplicationPlugin(
			ApplicationPluginObject applicationPlugin)
		throws NullPointerException;
	
	/**
	 * <p>Determines whether the given identifier for a 
	 * {@linkplain ApplicationPluginObject application metadata plugin} instance is attached to 
	 * this interchange object.</p>
	 * 
	 * @param applicationPluginInstanceID Identifier for the application plugin object to check for.
	 * @return Is the given identifier for an application plugin object attached to this interchange object?
	 * 
	 * @throws NullPointerException Cannot check for attachment using a <code>null</code> value for an
	 * identifier.
	 * 
	 * @see #containsApplicationPlugin(ApplicationPluginObject)
	 * @see #getApplicationPlugins()
	 */
	public boolean containsApplicationPlugin(
			AUID applicationPluginInstanceID) 
		throws NullPointerException;
	
	/**
	 * <p>Clear the set of application metadata plugins, omitting the optional property.</p>
	 * 
	 * @see #getApplicationPlugins()
	 */
	public void clearApplicationPlugins();
	
	/**
	 * <p>Returns the number of {@linkplain ApplicationPluginObject application metadata plugins} attached
	 * to this interchange object.</p>
	 * 
	 * @return Number of application metadata plugins attached to this object.
	 * 
	 * @see #getApplicationPlugins()
	 */
	public int countApplicationPlugins();
	
	/**
	 * <p>Remove the given {@linkplain ApplicationPluginObject application metadata plugin} from the set
	 * of application plugins attached to this interchange object.</p>
	 * 
	 * @param applicationPlugin Application plugin object to remove.
	 * @return Was the application plugin object removed successfully? A value of <code>false</code> is
	 * returned if the plugin object was not already attached.
	 * 
	 * @throws NullPointerException Cannot remove an application metadata plugin object using a <code>null</code>
	 * value.
	 * 
	 * @see #getApplicationPlugins()
	 */
	public boolean removeApplicationPlugin(
			ApplicationPluginObject applicationPlugin)
		throws NullPointerException;
	
	/**
	 * <p>Remove the {@linkplain ApplicationPluginObject application metadata plugin} with the given
	 * identifier from the set of application plugins attached to this interchange object.</p>
	 * 
	 * @param applicationPluginInstanceID Identifier of this application plugin object to remove. 
	 * @return Was the application plugin object removed successfully? A value of <code>false</code> is
	 * returned if the plugin object was not already attached.
	 * 
	 * @throws NullPointerException Cannot remove an application metadata plugin object using a <code>null</code>
	 * identifier.
	 * 
	 * @see #getApplicationPlugins()
	 */
	public boolean removeApplicationPlugin(
			AUID applicationPluginInstanceID)
		throws NullPointerException;
	
	/**
	 * <p>Tests if this interchangeable object is equal to the given object by
	 * comparing every property and the properties of any referenced values.
	 * Two objects are equal if and only if the following conditions are all met:</p>
	 * 
	 * <ul>
	 *  <li>the given object is not <code>null</code>;</li>
	 *  <li>they are the same object;</li>
	 *  <li>they are of the same type;</li>
	 *  <li>they have the same properties omitted or present;</li>
	 *  <li>the value of each property non-reference or collection property is the same;</li>
	 *  <li>an array-type property contains the same number of values and a deep equals comparison of each
	 *  element, index-for-index, is the same;</li>
	 *  <li>a set-type property contains the same number of values and the same elements, determined
	 *  by a deep equals comparison;</li>
	 *  <li>a referenced property is the same according to a deep equals comparison.</li>
	 * </ul> 
	 * 
	 * <p>The main difference between this method and {@link #equals(Object)} is that this
	 * method compares every property, even when a unique identifier is provided.</p>
	 *  
	 * @param o Object to test deep equality with this one.
	 * @return Do this object and the given object represent the same value?
	 * 
	 * @see #equals(Object)
	 * @see tv.amwa.maj.industry.MediaEngine#deepEquals(tv.amwa.maj.industry.MetadataObject, Object)
	 */
	public boolean deepEquals(
			Object o);
	
	/**
	 * <p>Provides an XML representation of this value. This value is useful for debugging
	 * and understanding the internal structure of an item of media.</p>
	 * 
	 * <p>Note that this interface does not guarantee to provide valid XML. Use
	 * {@link tv.amwa.maj.io.xml.XMLBuilder#toXML(tv.amwa.maj.industry.MetadataObject)} for this.</p>
	 * 
	 * @return An XML representation of the value of this interchange object.
	 * 
	 * @see tv.amwa.maj.io.xml.XMLBuilder#toXML(tv.amwa.maj.industry.MetadataObject)
	 * @see tv.amwa.maj.industry.MediaEngine#toString(tv.amwa.maj.industry.MetadataObject)
	 */
	public String toString();
	
	/**
	 * <p>Tests if this interchangeable object is equal to the given object by
	 * comparing unique identifiers where available, or property-by-property if not.
	 * Two objects are equal if and only if the following conditions are all met:</p>
	 * 
	 * <ul>
	 *  <li>the given object is not <code>null</code>;</li>
	 *  <li>they are the same object;</li>
	 *  <li>they are of the same type;</li>
	 *  <li>Either:
	 *  <ul>
	 *   <li>they have the same unique identifier, or</li>
	 *   <li>the following conditions are met:
	 *   <ul>
	 *    <li>they have the same properties omitted or present;</li>
	 *    <li>the value of each property non-reference or collection property is the same using
	 *    {@link Object#equals(Object)};</li>
	 *    <li>an array-type property contains the same number of values and an equals comparison of each
	 * 	  element, index-for-index, is the same;</li>
	 *    <li>a set-type property contains the same number of values and the same elements, determined
	 *    by an equals comparison;</li>
	 *    <li>a referenced property is the same according to an equals comparison.</li>
	 *   </ul></li>
	 *  </ul></li>
	 * </ul> 
	 * 
	 * <p>For a property-by-property comparison of each value, use {@link #deepEquals(Object)}.</p>
	 * 
	 * @param o Object to test equality with this one.
	 * @return Do this object and the given object represent the same value?
	 * 
	 * @see #deepEquals(Object)
	 * @see tv.amwa.maj.industry.MediaEngine#equals(tv.amwa.maj.industry.MetadataObject, Object)
	 * @see Object#equals(Object)
	 */
	public boolean equals(
			Object o);
	
	/**
	 * <p>Create a cloned copy of this interchange object.</p>
	 *
	 * @return Cloned copy of this interchange object.
	 */
	public InterchangeObject clone();
}

