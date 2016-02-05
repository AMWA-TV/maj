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
 * $Log: PluginDefinitionImpl.java,v $
 * Revision 1.5  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.4  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/25 14:18:28  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/06/18 16:51:58  vizigoth
 * Fix to setMaxEngineVersion message to prevent cloning of null values.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2007/12/04 13:04:48  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:29  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.constant.PluginCategoryType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.meta.impl.ClassDefinitionImpl;
import tv.amwa.maj.meta.impl.MetaDefinitionImpl;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.Locator;
import tv.amwa.maj.model.NetworkLocator;
import tv.amwa.maj.model.PluginDefinition;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.VersionType;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.record.impl.VersionTypeImpl;

// TODO consider adding version range checking

/** 
 * <p>Implements the definition of code objects that provide an implementation for a 
 * {@linkplain tv.amwa.maj.model.DefinitionObject definition}, such as a 
 * {@linkplain tv.amwa.maj.model.CodecDefinition codec definition}, or for a 
 * {@linkplain tv.amwa.maj.meta.MetaDefinition meta definition}, such as a 
 * {@linkplain tv.amwa.maj.meta.ClassDefinition class definition}.</p>
 *
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionWeakReferenceSet
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1e00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "PluginDefinition",
		  description = "The PluginDefinition class identifies code objects that provide an implementation for a DefinitionObject.",
		  symbol = "PluginDefinition")
public class PluginDefinitionImpl
	extends 
		DefinitionObjectImpl
	implements
		PluginDefinition,
		Serializable,
		Cloneable {
	
	/** <p></p> */
	private static final long serialVersionUID = -7691293334720278493L;
	
	private AUID pluginCategory;
	private VersionType pluginVersion;
	private String pluginVersionString = null;
	private String deviceManufacturerName = null;
	private NetworkLocator manufacturerInfo = null;
	private AUID manufacturerID = null;
	private AUID pluginPlatform = null;
	private VersionType minPlatformVersion = null;
	private VersionType maxPlatformVersion = null;
	private AUID engine = null;
	private VersionType minEngineVersion = null;
	private VersionType maxEngineVersion = null;
	private AUID pluginAPI = null;
	private VersionType minPluginAPI = null;
	private VersionType maxPluginAPI = null;
	private Boolean softwareOnly = null;
	private Boolean accelerator = null;
	private List<Locator> pluginLocators = 
		Collections.synchronizedList(new Vector<Locator>());
	private Boolean authentication = null;
	private AUID implementedClass = null;

	public PluginDefinitionImpl() {
	}

	/**
	 * <p>Creates and initializes a plugin definition, which is used to identify code 
	 * objects that provide an implementation for a {@linkplain DefinitionObjectImpl definition object} such 
	 * as {@linkplain CodecDefinitionImpl codec definition}, or for a {@link MetaDefinitionImpl meta definition} 
	 * such as a {@linkplain ClassDefinitionImpl class definition}.</p>
	 * 
	 * <p>The plugin category for the plugin can be set using one of the categories defined in the 
	 * toolkit through the static values of interface {@link PluginCategoryType}.</p>
	 * 
	 * @param identification Unique identifier for the new plugin definition.
	 * @param name Display name for the new plugin definition.
	 * @param pluginCategory Kind of plugin.
	 * @param versionNumber Version of the plugin.
	 * @param definitionObject Unique identifier of the class definition or meta definition that this
	 * definition object provides an implementation of.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and all the
	 * arguments are required in this implementation.
	 */
	public PluginDefinitionImpl(
			AUID identification,
			@AAFString String name,
			AUID pluginCategory,
			VersionType versionNumber,
			AUID definitionObject) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a plugin definition with a null id.");
		if (pluginCategory == null)
			throw new NullPointerException("Cannot create a plugin definition with a null plugin category.");
		if (versionNumber == null)
			throw new NullPointerException("Cannot create a plugin definition with a null version number.");
		if (definitionObject == null)
			throw new NullPointerException("Cannot create a plugin definition with a null data definition object.");
		
		setAUID(identification);
		setName(name);
		setCategoryClass(pluginCategory);
		setPluginVersion(versionNumber);
		setImplementedClass(definitionObject);
	}

	@MediaProperty(uuid1 = 0x05200901, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PluginCategory",
			typeName = "PluginCategoryType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x2203,
			symbol = "PluginCategory")
	public AUID getCategoryClass() {

		return pluginCategory.clone();
	}

	@MediaPropertySetter("PluginCategory")
	public void setCategoryClass(
			AUID categoryClass)
		throws NullPointerException {
		
		if (categoryClass == null)
			throw new NullPointerException("Cannot set the category type for this plugin with a null value.");
		
		// TODO consider adding a warning if the AUID is not known in the extendible enumeration
		this.pluginCategory = categoryClass.clone();
	}
	
	public final static AUID initializePluginCategory() {
		
		return PluginCategoryType.Codec;
	}

	@MediaProperty(uuid1 = 0x0520090f, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ImplementedClass",
			aliases = { "DefinitionObject", "PluginDefinitionDefinitionObject" },
			typeName = "AUID", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2216,
			symbol = "ImplementedClass")
	public AUID getImplementedClass() 
		throws PropertyNotPresentException {

		if (implementedClass == null)
			throw new PropertyNotPresentException("The implemented class property is not present for this plugin definition, indicating a plugin definition from AAF version 1.0.");
		
		return implementedClass.clone();
	}

	@MediaPropertySetter("ImplementedClass")
	public void setImplementedClassLegacy(
			AUID implementedClass) { // Allow 1.0 files to set this as optional
		
		if (implementedClass == null)
			this.implementedClass = null;
		else
			setImplementedClass(implementedClass);
	}
	
	public void setImplementedClass(
			AUID implementedClass)
		throws NullPointerException { // As of 1.1, this must be specified. Published interface does not allow null.
		
		if (implementedClass == null)
			throw new NullPointerException("Cannot set the definition object of this plugin definition using a null id.");

		// TODO consider testing for the existence of the definition in the dictionary
		this.implementedClass = implementedClass.clone();
	}

	@MediaProperty(uuid1 = 0x05200905, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Engine",
			typeName = "AUID",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x220C,
			symbol = "Engine")
	public AUID getEngine() 
		throws PropertyNotPresentException {

		if (engine == null)
			throw new PropertyNotPresentException("The optional engine property is not present in this plugin definition.");
		
		return engine.clone();
	}

	@MediaPropertySetter("Engine")
	public void setEngine(
			AUID engine) {
		
		if (engine == null) {
			this.engine = null;
			return;
		}
		
		this.engine = engine.clone();
	}

	public VersionType[] getEngineVersionRange() 
		throws PropertyNotPresentException {
		
		if ((minEngineVersion == null) || (maxEngineVersion == null))
			throw new PropertyNotPresentException("The optional engine version range is not fully specified for this plugin definition.");
		
		return new VersionType[] { minEngineVersion.clone(), maxEngineVersion.clone() };
	}

	@MediaProperty(uuid1 = 0x05200906, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MinEngineVersion",
			typeName = "VersionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x220D,
			symbol = "MinEngineVersion")
	public VersionType getEngineMinimumVersion() 
		throws PropertyNotPresentException {
		
		if (minEngineVersion == null)
			throw new PropertyNotPresentException("The optional engine minimum property is not present in this plugin definition.");
		
		return minEngineVersion.clone();
	}

	@MediaPropertySetter("MinEngineVersion")
	public void setEngineMinimumVersion(
			VersionType minVersion) {
	
		if (minVersion == null) {
			minEngineVersion = null;
			return;
		}
		
		this.minEngineVersion = minVersion.clone();
	}

	@MediaProperty(uuid1 = 0x05200907, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MaxEngineVersion",
			typeName = "VersionType", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x220E,
			symbol = "MaxEngineVersion")
	public VersionType getEngineMaximumVersion() 
		throws PropertyNotPresentException {
		
		if (maxEngineVersion == null)
			throw new PropertyNotPresentException("The optional engine maximum version is not present for this plugin definition.");
		
		return maxEngineVersion.clone();
	}

	@MediaPropertySetter("MaxEngineVersion")
	public void setEngineMaximumVersion(
			tv.amwa.maj.record.VersionType maxEngineVersion) {
		
		if (maxEngineVersion == null) {
			this.maxEngineVersion = null;
			return;
		}
		
		this.maxEngineVersion = maxEngineVersion.clone();
	}

	@MediaProperty(uuid1 = 0x05200902, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PluginPlatform",
			aliases = { "Platform", "PluginDefinitionPlatform" },
			typeName = "AUID",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2209,
			symbol = "PluginPlatform")
	public AUID getPluginPlatform() 
		throws PropertyNotPresentException {

		if (pluginPlatform == null)
			throw new PropertyNotPresentException("The optional platform property is not present for this plugin definition.");

		return pluginPlatform.clone();
	}

	@MediaPropertySetter("PluginPlatform")
	public void setPluginPlatform(
			AUID pluginPlatform) {
		
		if (pluginPlatform == null) {
			this.pluginPlatform = null;
			return;
		}
		
		this.pluginPlatform = pluginPlatform.clone();
	}

	@MediaProperty(uuid1 = 0x0520090d, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PluginLocators",
			aliases = { "Locators" },
			typeName = "LocatorStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2214,
			symbol = "PluginLocators")
	public List<Locator> getPluginLocators() 
		throws PropertyNotPresentException {

		if (pluginLocators.size() == 0)
			throw new PropertyNotPresentException("No optional locators are provided for this plugin definition.");
		
		return StrongReferenceVector.getOptionalList(pluginLocators);
	}

	@MediaListGetAt("PluginLocators")
	public Locator getPluginLocatorAt(
			int index)
		throws IndexOutOfBoundsException {

		return StrongReferenceVector.getAt(pluginLocators, index);
	}

	@MediaListAppend("PluginLocators")
	public void appendPluginLocator(
			Locator pluginLocator)
		throws NullPointerException {

		if (pluginLocator == null)
			throw new NullPointerException("Cannot append a null locator to the list of locators for this plugin definition.");
		
		StrongReferenceVector.append(pluginLocators, pluginLocator);
	}

	@MediaPropertyCount("PluginLocators")
	public int countPluginLocators() {

		return pluginLocators.size();
	}

	@MediaListPrepend("PluginLocators")
	public void prependPluginLocator(
			tv.amwa.maj.model.Locator pluginLocator)
		throws NullPointerException {

		if (pluginLocator == null)
			throw new NullPointerException("Cannot prepend a null locator to the list of locators for this plugin definition.");
		
		StrongReferenceVector.prepend(pluginLocators, pluginLocator);
	}

	@MediaListInsertAt("PluginLocators")
	public void insertPluginLocatorAt(
			int index,
			Locator pluginLocator)
		throws NullPointerException,
			IndexOutOfBoundsException {

		if (pluginLocator == null)
			throw new NullPointerException("Cannot insert a null locator into the list of locators for this plugin definition.");
	
		StrongReferenceVector.insert(pluginLocators, index, pluginLocator);
	}

	@MediaListRemoveAt("PluginLocators")
	public void removePluginLocatorAt(
			int index)
		throws IndexOutOfBoundsException {

		StrongReferenceVector.remove(pluginLocators, index);
	}

	@MediaPropertyClear("PluginLocators")
	public void clearPluginLocators() {
		
		pluginLocators = Collections.synchronizedList(new Vector<Locator>());
	}

	@MediaProperty(uuid1 = 0x010a0101, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ManufacturerID",
			aliases = { "PluginDefinitionManufacturerID" },
			typeName = "AUID",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2208,
			symbol = "ManufacturerID")
	public AUID getManufacturerID() 
		throws PropertyNotPresentException {

		if (manufacturerID == null)
			throw new PropertyNotPresentException("The optional manufacturer id property is not present in this plugin definition.");
		
		return manufacturerID.clone();
	}

	@MediaPropertySetter("ManufacturerID")
	public void setManufacturerID(
			tv.amwa.maj.record.AUID manufacturerId) {

		if (manufacturerId == null) {
			this.manufacturerID = null;
			return;
		}
		
		this.manufacturerID = manufacturerId.clone();
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x020b, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ManufacturerInfo",
			typeName = "NetworkLocatorStrongReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2207,
			symbol = "ManufacturerInfo")
	public NetworkLocator getManufacturerInfo() 
		throws PropertyNotPresentException {

		if (manufacturerInfo == null)
			throw new PropertyNotPresentException("The optional manufacturer information is not present for this plugin definition.");
		
		return manufacturerInfo;
	}

	@MediaPropertySetter("ManufacturerInfo")
	public void setManufacturerInfo(
			NetworkLocator manufacturerInfo)
		throws NullPointerException {

		if (manufacturerInfo == null) {
			this.manufacturerInfo = null;
			return;
		}
			
		this.manufacturerInfo = manufacturerInfo;
	}

	public VersionType[] getPlatformVersionRange() 
		throws PropertyNotPresentException {

		if ((minPlatformVersion == null) || (maxPlatformVersion == null))
			throw new PropertyNotPresentException("The optional platform version range is not fully specified for this plugin definition.");
		
		return new VersionType[] { minPlatformVersion.clone(), maxPlatformVersion.clone() };
	}

	@MediaProperty(uuid1 = 0x05200903, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MinPlatformVersion",
			typeName = "VersionType", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x220A,
			symbol = "MinPlatformVersion")
	public VersionType getPlatformMinimumVersion() 
		throws PropertyNotPresentException {
		
		if (minPlatformVersion == null)
			throw new PropertyNotPresentException("The optional platform minimum version property is not set for this plugin definition.");

		return minPlatformVersion.clone();
	}

	@MediaPropertySetter("MinPlatformVersion")
	public void setPlatformMinimumVersion(
			VersionType minVersion) {

		if (minVersion == null) {
			this.minPlatformVersion = null;
			return;
		}
		
		this.minPlatformVersion = minVersion.clone();
	}

	@MediaProperty(uuid1 = 0x05200904, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MaxPlatformVersion",
			typeName = "VersionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x220B,
			symbol = "MaxPlatformVersion")
	public VersionType getPlatformMaximumVersion() 
		throws PropertyNotPresentException {
		
		if (maxPlatformVersion == null)
			throw new PropertyNotPresentException("The optional platform maximum version property is not set for this plugin definition.");

		return maxPlatformVersion.clone();
	}

	@MediaPropertySetter("MaxPlatformVersion")
	public void setPlatformMaximumVersion(
			VersionType maxVersion) {

		if (maxVersion == null) {
			this.maxPlatformVersion = null;
			return;
		}
		
		this.maxPlatformVersion = maxVersion.clone();
	}

	@MediaProperty(uuid1 = 0x05200908, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PluginAPI",
			typeName = "AUID",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x220F,
			symbol = "PluginAPI")
	public AUID getPluginAPI() 
		throws PropertyNotPresentException {

		if (pluginAPI == null)
			throw new PropertyNotPresentException("The optional plugin API property is not present in this plugin definition.");
		
		return pluginAPI.clone();
	}

	@MediaPropertySetter("PluginAPI")
	public void setPluginAPI(
			AUID pluginAPI) {

		if (pluginAPI == null) {
			this.pluginAPI = null;
			return;
		}

		this.pluginAPI = pluginAPI.clone();
	}

	public VersionType[] getPluginAPIVersionRange() 
		throws PropertyNotPresentException {

		if ((minPluginAPI == null) || (maxPluginAPI == null))
			throw new PropertyNotPresentException("The optional plugin API version range is not fully specified for this plugin definition.");

		return new VersionType[] { minPluginAPI.clone(), maxPluginAPI.clone() };
	}

	@MediaProperty(uuid1 = 0x05200909, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MinPluginAPI",
			typeName = "VersionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2210,
			symbol = "MinPluginAPI")
	public VersionType getPluginAPIMinimumVersion()
		throws PropertyNotPresentException {
		
		if (minPluginAPI == null)
			throw new PropertyNotPresentException("The optional plugin API minimum version property is not set for this plugin definition.");

		return minPluginAPI.clone();
	}
	
	@MediaPropertySetter("MinPluginAPI")
	public void setPluginAPIMinimumVersion(
			VersionType minVersion) {

		if (minVersion == null) {
			this.minPluginAPI = null;
			return;
		}

		this.minPluginAPI = minVersion.clone();
	}
	
	@MediaProperty(uuid1 = 0x0520090a, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MaxPluginAPI",
			typeName = "VersionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2211,
			symbol = "MaxPluginAPI")
	public VersionType getPluginAPIMaximumVersion() 
		throws PropertyNotPresentException {
		
		if (maxPluginAPI == null)
			throw new PropertyNotPresentException("The optional plugin API maximum version is not set for this plugin definition.");
		
		return maxPluginAPI.clone();
	}

	@MediaPropertySetter("MaxPluginAPI")
	public void setPluginAPIMaximumVersion(
			tv.amwa.maj.record.VersionType maxVersion) {

		if (maxVersion == null) {
			this.maxPluginAPI = null;
			return;
		}
		
		this.maxPluginAPI = maxVersion.clone();
	}

	@MediaProperty(uuid1 = 0x010a0101, uuid2 = (short) 0x0101, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "DeviceManufacturerName",
			aliases = { "Manufacturer", "PluginDefinitionManufacturer" },
			typeName = "UTF16String", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2206,
			symbol = "DeviceManufacturerName")
	public String getDeviceManufacturerName() 
		throws PropertyNotPresentException {

		if (deviceManufacturerName == null)
			throw new PropertyNotPresentException("Cannot set the manufacturer name property for this plugin definition with a null value.");

		return deviceManufacturerName;
	}

	@MediaPropertySetter("DeviceManufacturerName")
	public void setDeviceManufacturerName(
			String deviceManufacturerName) {
		
		this.deviceManufacturerName = deviceManufacturerName;
	}

	@MediaProperty(uuid1 = 0x03030301, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PluginVersion",
			aliases = { "VersionNumber" },
			typeName = "VersionType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x2204,
			symbol = "PluginVersion")
	public VersionType getPluginVersion() {

		return pluginVersion.clone();
	}

	@MediaPropertySetter("PluginVersion")
	public void setPluginVersion(
			tv.amwa.maj.record.VersionType version)
		throws NullPointerException {

		if (version == null)
			throw new NullPointerException("Cannot set the plugin version number of this plugin definition using a null value.");

		this.pluginVersion = version.clone();
	}
	
	public final static VersionType initializePluginVersion() {
		
		return new VersionTypeImpl((byte) 0, (byte) 0);
	}

	@MediaProperty(uuid1 = 0x03030301, uuid2 = (short) 0x0201, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PluginVersionString",
			aliases = { "VersionString" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2205,
			symbol = "PluginVersionString")
	public String getPluginVersionString() 
		throws PropertyNotPresentException {

		if (pluginVersionString == null)
			throw new PropertyNotPresentException("The optional plugin version string is not set for this plugin definition.");
		
		return pluginVersionString;
	}

	@MediaPropertySetter("PluginVersionString")
	public void setPluginVersionString(
			String pluginVersionString) {

		this.pluginVersionString = pluginVersionString;
	}

	public boolean isAccelerated() {

		return getIsAccelerated();
	}

	@MediaProperty(uuid1 = 0x0520090c, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Accelerator",
			typeName = "Boolean",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2213,
			symbol = "Accelerator")
	public boolean getIsAccelerated() { 
		
		if (accelerator == null)
			return ACCELERATOR_DEFAULT;
		else
			return accelerator;
	}

	@MediaPropertySetter("Accelerator")
	public void setIsAccelerated(
			Boolean isAccelerated) {

		this.accelerator = isAccelerated;
	}

	public boolean isSoftwareOnly() {

		return getIsSoftwareOnly();
	}

	@MediaProperty(uuid1 = 0x0520090b, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "SoftwareOnly",
			typeName = "Boolean",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2212,
			symbol = "SoftwareOnly")
	public boolean getIsSoftwareOnly() {
		
		if (softwareOnly == null)
			return SOFTWAREONLY_DEFAULT;
		else
			return softwareOnly;
	}

	@MediaPropertySetter("SoftwareOnly")
	public void setIsSoftwareOnly(
			Boolean isSoftwareOnly) {

		this.softwareOnly = isSoftwareOnly;
	}

	public boolean supportsAuthentication() {

		return getSupportsAuthentication();
	}

	@MediaProperty(uuid1 = 0x0520090e, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Authentication",
			typeName = "Boolean",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2215,
			symbol = "Authentication")
	public boolean getSupportsAuthentication() {
		
		if (authentication == null)
			return AUTHENTICATION_DEFAULT;
		else
			return authentication;
	}

	@MediaPropertySetter("Authentication")
	public void setSupportsAuthentication(
			Boolean supportsAuthentication) {

		this.authentication = supportsAuthentication;
	}
	
	public PluginDefinition clone() {
		
		return (PluginDefinition) super.clone();
	}
	
//	private AUID pluginCategory;
	
	public String getPluginCategoryString() {
		
		return AUIDImpl.toPersistentForm(pluginCategory);
	}
	
	public void setPluginCategoryString(
			String pluginCategory) {
		
		this.pluginCategory = AUIDImpl.fromPersistentForm(pluginCategory);
	}
	
//	private VersionType pluginVersion;
	
	public String getPluginVersionPersist() {
		
		return VersionTypeImpl.toPersistentForm(pluginVersion);
	}
	
	public void setPluginVersionPersist(
			String pluginVersion) {
		
		this.pluginVersion = VersionTypeImpl.fromPersistentForm(pluginVersion);
	}

//	private AUID manufacturerID = null;

	public String getManufacturerIDString() {
		
		return AUIDImpl.toPersistentForm(manufacturerID);
	}
	
	public void setManufacturerIDString(
			String manufacturerID) {
		
		this.manufacturerID = AUIDImpl.fromPersistentForm(manufacturerID);
	}
	
	//	private AUID pluginPlatform = null;
	
	public String getPluginPlatformString() {
		
		return AUIDImpl.toPersistentForm(pluginPlatform);
	}
	
	public void setPluginPlatformString(
			String pluginPlatform) {
		
		this.pluginPlatform = AUIDImpl.fromPersistentForm(pluginPlatform);
	}
	
//	private VersionType minPlatformVersion = null;

	public String getMaxPlatformVersionString() {
		
		return VersionTypeImpl.toPersistentForm(maxPlatformVersion);
	}
	
	public void setMaxPlatformVersionString(
			String maxPlatformVersion) {
		
		this.maxPlatformVersion = VersionTypeImpl.fromPersistentForm(maxPlatformVersion);
	}
	
	//	private VersionType maxPlatformVersion = null;
	
	public String getMinPlatformVersionString() {
		
		return VersionTypeImpl.toPersistentForm(minPlatformVersion);
	}
	
	public void setMinPlatformVersionString(
			String minPlatformVersion) {
		
		this.minPlatformVersion = VersionTypeImpl.fromPersistentForm(minPlatformVersion);
	}
	
	//	private AUID engine = null;
	
	public String getEngineString() {
		
		return AUIDImpl.toPersistentForm(engine);
	}
	
	public void setEngineString(
			String engine) {
		
		this.engine = AUIDImpl.fromPersistentForm(engine);
	}
	
	//	private VersionType minEngineVersion = null;
	
	public String getMinEngineVersionString() {
		
		return VersionTypeImpl.toPersistentForm(minEngineVersion);
	}
	
	public void setMinEngineVersionString(
			String minEngineVersionString) {
		
		this.minEngineVersion = VersionTypeImpl.fromPersistentForm(minEngineVersionString);
	}
	
	//	private VersionType maxEngineVersion = null;
	
	public String getMaxEngineVersionString() {
		
		return VersionTypeImpl.toPersistentForm(maxEngineVersion);
	}
	
	public void setMaxEngineVersionString(
			String maxEngineVersion) {
		
		this.maxEngineVersion = VersionTypeImpl.fromPersistentForm(maxEngineVersion);
	}
	
	//	private AUID pluginAPI = null;
	
	public String getPluginAPIString() {
		
		return AUIDImpl.toPersistentForm(pluginAPI);
	}
	
	public void setPluginAPIString(
			String pluginAPI) {
		
		this.pluginAPI = AUIDImpl.fromPersistentForm(pluginAPI);
	}
	
	//	private VersionType minPluginAPI = null;
	
	public String getMinPluginAPIString() {
		
		return VersionTypeImpl.toPersistentForm(minPluginAPI);
	}
	
	public void setMinPluginAPIString(
			String minPluginAPI) {
		
		this.minPluginAPI = VersionTypeImpl.fromPersistentForm(minPluginAPI);
	}
	
	//	private VersionType maxPluginAPI = null;
	
	public String getMaxPluginAPIString() {
		
		return VersionTypeImpl.toPersistentForm(maxPluginAPI);
	}
	
	public void setMaxPluginAPIString(
			String maxPluginAPI) {
		
		this.maxPluginAPI = VersionTypeImpl.fromPersistentForm(maxPluginAPI);
	}
	
	//	private AUID implementedClass = null;
	
	public String getImplementedClassString() {
		
		return AUIDImpl.toPersistentForm(implementedClass);
	}
	
	public void setImplementedClassString(
			String implementedClass) {
		
		this.implementedClass = AUIDImpl.fromPersistentForm(implementedClass);
	}
}
