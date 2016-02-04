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
 * $Log: InterchangeObjectImpl.java,v $
 * Revision 1.6  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.5  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.4  2011/07/27 17:33:23  vizigoth
 * Fixed imports to clear warnings.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/04/13 07:15:23  vizigoth
 * Using Long rather than long for persistent IDs and setting to null as a default.
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
 * Revision 1.3  2008/01/27 11:14:39  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.2  2007/12/04 13:04:46  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:24  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

/* FIXME sort out how to access the generation, generationAUID and other properties */

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.exception.ObjectNotAttachedException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyContains;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.model.ApplicationPluginObject;
import tv.amwa.maj.model.Identification;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>Implements the root class for all AAF persistent classes. All classes 
 * defined in an AAF file shall be sub-classes of interchange object with the exception 
 * of the {@linkplain MetaDefinition meta definition} classes.</p>
 * 
 *
 */
@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0100,
		 uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		 definedName = "InterchangeObject",
		 description = "The InterchangeObject class is a root class. All classes defined in an AAF file shall be sub-classes of InterchangeObject with the exception of the MetaDefinition classes defined by this document.",
		 symbol = "InterchangeObject",
		 namespace = CommonConstants.AAF_XML_NAMESPACE,
		 prefix = CommonConstants.AAF_XML_PREFIX,
		 aliases = { "AAFObject" },
		 isConcrete = false)
public class InterchangeObjectImpl 
	implements InterchangeObject,
		Serializable,
		Cloneable {
	
	private static final long serialVersionUID = 4120720187157154133L;
	
	private AUID linkedGenerationID = null;
	private boolean generationTracking = false;
	private WeakReference<ClassDefinition> objectClass = null;
	private Long persistentID = null;
	private int persistentIndex = 0;
	
	private Map<AUID, ApplicationPluginObject> applicationPlugins = null;
	
	public void disableGenerationTracking() {

		linkedGenerationID = null;
		generationTracking = false;
	}

	public void enableGenerationTracking() {

		linkedGenerationID = new tv.amwa.maj.record.impl.AUIDImpl(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0,
											   0, 0, 0, 0, 0, 0, 0, 0 });
		generationTracking = true;
	}

	public final static AUID ObjectClassPropertyID = 
		new AUIDImpl(0x06010104, (short) 0x0101, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02} );
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0101, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ObjectClass",
			aliases = { "ObjClass" },
			typeName = "ClassDefinitionWeakReference", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0101,
			symbol = "ObjectClass")
	public ClassDefinition getObjectClass()  {

		if (objectClass == null) 
			objectClass = new WeakReference<ClassDefinition>(Warehouse.lookForClass(this.getClass()));
		
		return objectClass.getTarget();
	}

	@MediaPropertySetter("ObjectClass")
	public void setObjectClass(
			ClassDefinition objectClass) {
		
		if (objectClass == null) {
			this.objectClass = null;
			return;
		}
		
		this.objectClass = new WeakReference<ClassDefinition>(objectClass);
	}
	
	public Identification getGeneration() 
		throws InvalidParameterException,
			ObjectNotAttachedException {
		
		// TODO add support for generation tracking

		throw new ObjectNotAttachedException("This object has not been accessed through a preface.");
	}

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0800, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "LinkedGenerationID",
			aliases = { "Generation" },
			typeName = "AUID",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0102,
			symbol = "LinkedGenerationID")
	public AUID getLinkedGenerationID() 
		throws PropertyNotPresentException,
			ObjectNotAttachedException {

		if (!generationTracking)
			throw new PropertyNotPresentException("Generation tracking is not enabled so the optional linked generation property is not present.");
		if (linkedGenerationID.isNil())
			throw new ObjectNotAttachedException("This object is not attached to a file.");
		return linkedGenerationID;
	}

	@MediaPropertySetter("LinkedGenerationID")
	public void setLinkedGenerationID(
			AUID linkedGenerationID) {
		
		if (linkedGenerationID == null) {
			this.linkedGenerationID = null;
			generationTracking = false;
			return;
		}
		
		this.linkedGenerationID = linkedGenerationID.clone();
		if (!linkedGenerationID.isNil())
			generationTracking = true;
	}
	
	void onSave(AUID generationAUID) {
		
		if (generationTracking)
			this.linkedGenerationID = generationAUID;
	}

	public boolean isGenerationTracked() {

		return generationTracking;
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x020e, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C },
			definedName = "ApplicationPlugins",
			aliases = { "ApplicationPlugIns", "ApplicationPlug-InBatch", "ApplicationPlug-In Batch" },
			typeName = "ApplicationPluginObjectStrongReferenceSet",
			pid = 0,
			symbol = "ApplicationPlugins",
			optional = true,
			uniqueIdentifier = false,
			description = "Set of application metadata plugins " + 
					"attached to this interchange object, where each attached object contains application-specific " + 
					"description.")
	public Set<ApplicationPluginObject> getApplicationPlugins()
		throws PropertyNotPresentException {
		
		if (applicationPlugins == null)
			throw new PropertyNotPresentException("The optional application plugins property is not present for this interchange object.");
		
		return new HashSet<ApplicationPluginObject>(applicationPlugins.values());
	}
	
	@MediaSetAdd("ApplicationPlugins")
	public void addApplicationPlugin(
			ApplicationPluginObject applicationPlugin)
		throws NullPointerException {
		
		if (applicationPlugin == null)
			throw new NullPointerException("Cannot add an application plugin using a null value.");
		
		if (applicationPlugins == null)
			applicationPlugins = Collections.synchronizedMap(new HashMap<AUID, ApplicationPluginObject>());
		
		applicationPlugins.put(applicationPlugin.getApplicationPluginInstanceID(), applicationPlugin);
	}
	
	@MediaPropertyContains("ApplicationPlugins")
	public boolean containsApplicationPlugin(
			ApplicationPluginObject applicationPlugin)
		throws NullPointerException {
		
		if (applicationPlugin == null)
			throw new NullPointerException("Cannot check for an application plugin using a null value.");
		
		if (applicationPlugins == null) return false;
		
		return applicationPlugins.containsKey(applicationPlugin.getApplicationPluginInstanceID());
	}
	
	public boolean containsApplicationPlugin(
			AUID applicationPluginInstanceID) 
		throws NullPointerException {
		
		if (applicationPluginInstanceID == null)
			throw new NullPointerException("Cannot check for an application plugin using a null value.");
		
		if (applicationPlugins == null) return false;
		
		return applicationPlugins.containsKey(applicationPluginInstanceID);
	}
	
	@MediaPropertyClear("ApplicationPlugins")
	public void clearApplicationPlugins() {
		
		if (applicationPlugins != null)
			applicationPlugins.clear();
	}
	
	@MediaPropertyCount("ApplicationPlugins")
	public int countApplicationPlugins() {
		
		if (applicationPlugins == null)
			return 0;
		else
			return applicationPlugins.size();
	}
	
	@MediaPropertyRemove("ApplicationPlugins")
	public boolean removeApplicationPlugin(
			ApplicationPluginObject applicationPlugin)
		throws NullPointerException {
		
		if (applicationPlugin == null)
			throw new NullPointerException("Cannot remove an application plugin object using a null value.");
		
		if (applicationPlugins == null)
			return false;
		
		return (applicationPlugins.remove(applicationPlugin.getAUID()) != null);
	}
	
	public boolean removeApplicationPlugin(
			AUID applicationPluginInstanceID)
		throws NullPointerException {
		
		if (applicationPluginInstanceID == null)
			throw new NullPointerException("Cannot remove an application plugin object using a null value.");
		
		if (applicationPlugins == null)
			return false;
		
		return (applicationPlugins.remove(applicationPluginInstanceID) != null);
	}

	@Override
	public final String toString() {
		
		return MediaEngine.toString(this);
	}
			
	@Override
	public int hashCode() {
		
		return MediaEngine.hashCode(this);
	}
	
	@Override
	public boolean equals(
			Object o) {
		
		return MediaEngine.equals(this, o);
	}
	
	public boolean deepEquals(
			Object o) {
		
		return MediaEngine.deepEquals(this, o);
	}
	
	public InterchangeObject clone() {
		
		try {
			// System.out.println("About to clone a " + getClass().getName());
			// (new Exception()).printStackTrace();
			return (InterchangeObject) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Should never get here
			throw new InternalError(cnse.getMessage());
		}
	}
	
	public void appendXMLChildren(
			Node parent) { }
	
	public String getComment() {
		
		return null;
	}

	public Long getPersistentID() {
		
		return persistentID;
	}
	
	public void setPersistentID(
			Long persistentID) {
		
		this.persistentID = persistentID;
	}
	
	public int getPersistentIndex() {
		
		return persistentIndex;
	}
	
	public void setPersistentIndex(
			int index) {
		
		this.persistentIndex = index;
	}
	
	public String getLinkedGenerationIDString() {
	
		return AUIDImpl.toPersistentForm(linkedGenerationID);
	}
	
	public void setLinkedGenerationIDString(
			String linkedGenerationID) {

		this.linkedGenerationID = AUIDImpl.fromPersistentForm(linkedGenerationID);
	}

}
