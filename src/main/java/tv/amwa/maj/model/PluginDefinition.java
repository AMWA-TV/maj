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
 * $Log: PluginDefinition.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/02/08 11:27:24  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.3  2007/12/04 13:04:52  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.2  2007/12/04 09:31:46  vizigoth
 * Minor comment updates.
 *
 * Revision 1.1  2007/11/13 22:08:33  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.misctype.Engine;
import tv.amwa.maj.misctype.HardwarePlatform;
import tv.amwa.maj.misctype.PluginAPI;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.VersionType;

/**
 * <p>Specifies the definition of code objects that provide an implementation for a 
 * {@linkplain DefinitionObject definition}, such as a {@linkplain CodecDefinition codec
 * definition}, or for a {@linkplain MetaDefinition meta definition}, such as a 
 * {@linkplain ClassDefinition class definition}.</p>  
 * 
 * <p>Note that the mechanism of supporting plugins has yet to be determined for the 
 * current implementation of the MAJ API.</p>
 * 
 *
 *
 * @see tv.amwa.maj.constant.PluginIdentifiers
 * @see Dictionary#getPluginDefinitions()
 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionWeakReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionStrongReferenceSet
 */

public interface PluginDefinition 
	extends DefinitionObject {

	/** 
	 * <p>Default value for the software only property, which is {@value #SOFTWAREONLY_DEFAULT}.
	 * The property specifies if the plugin is capable of executing in a software-only
	 * environment.</p> 
	 * 
	 * @see #isSoftwareOnly()
	 * @see #setIsSoftwareOnly(Boolean)
	 */
	public final static boolean SOFTWAREONLY_DEFAULT = false;
	
	/** 
	 * <p>Default value for the accelerator property, which is {@value #ACCELERATOR_DEFAULT}.
	 * The property specifies if the plugin is capable of using hardware to accelerate
	 * essence processing.</p> 
	 * 
	 * @see #isAccelerated()
	 * @see #setIsAccelerated(Boolean)
	 */
	public final static boolean ACCELERATOR_DEFAULT = false;
	
	/** 
	 * <p>Default value for the authentication property, which is {@value #AUTHENTICATION_DEFAULT}.
	 * The property specifies that the plugin implementation supports authentication.</p> 
	 * 
	 * @see #supportsAuthentication()
	 * @see #setSupportsAuthentication(Boolean)
	 */
	public final static boolean AUTHENTICATION_DEFAULT = false;

	/**
	 * <p>Returns the category of the defined plugin. The AAF built in categories
	 * are:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.constant.PluginCategoryType#Effect}</li>
	 *  <li>{@link tv.amwa.maj.constant.PluginCategoryType#Codec}</li>
	 *  <li>{@link tv.amwa.maj.constant.PluginCategoryType#Interpolation}</li>
	 * </ul>
	 * 
	 * @return Kind of plugin defined.
	 * 
	 * @see tv.amwa.maj.constant.PluginCategoryType
     * @see tv.amwa.maj.industry.TypeDefinitions#PluginCategoryType
	 */
	public AUID getCategoryClass();

	/**
	 * <p>Sets the category of the defined plugin. The AAF built in categories
	 * are:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.constant.PluginCategoryType#Effect}</li>
	 *  <li>{@link tv.amwa.maj.constant.PluginCategoryType#Codec}</li>
	 *  <li>{@link tv.amwa.maj.constant.PluginCategoryType#Interpolation}</li>
	 * </ul>
	 * 
	 * @param categoryClass Kind of plugin defined.
	 * 
	 * @throws NullPointerException The given plugin category is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.constant.PluginCategoryType
     * @see tv.amwa.maj.industry.TypeDefinitions#PluginCategoryType
	 */
	public void setCategoryClass(
			AUID categoryClass) 
		throws NullPointerException;

	/**
	 * <p>Returns the plugin version of the defined plugin.</p>
	 * 
	 * @return Plugin version of the defined plugin.
	 */
	public VersionType getPluginVersion();

	/**
	 * <p>Sets the plugin version of the defined plugin.</p>
	 * 
	 * @param pluginVersion Plugin version of the defined plugin.
	 * 
	 * @throws NullPointerException The given version number is <code>null</code>.
	 */
	public void setPluginVersion(
			VersionType pluginVersion) 
		throws NullPointerException;

	/**
	 * <p>Sets the plugin version string of this defined plugin, which specifies 
	 * a string that can be used to identify the plugin version to the user. Set
	 * this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param pluginVersionString Plugin version string of this plugin
	 * definition.
	 */
	public void setPluginVersionString(
			@AAFString String pluginVersionString);

	/**
	 * <p>Returns the plugin version string of the defined plugin, which specifies 
	 * a string that can be used to identify the plugin version to the user.</p>
	 * 
	 * @return Plugin version string of this plugin definition.
	 * 
	 * @throws PropertyNotPresentException The optional version string property is not present in
	 * this plugin definition.
	 */
	public @AAFString String getPluginVersionString() 
		throws PropertyNotPresentException;

	/**
	 * <p>Set the plugin manufacturer name for the defined plugin, which specifies a string 
	 * that can be used to identify the plugin manufacturer to the user. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 * 
	 * @param pluginManufacturerName Plugin manufacturer name for this
	 * plugin definition.
	 */
	public void setDeviceManufacturerName(
			@AAFString String pluginManufacturerName);

	/**
	 * <p>Returns the plugin manufacturer name for the defined plugin, which specifies a string 
	 * that can be used to identify the plugin manufacturer to the user. This is an optional
	 * property.</p>
	 * 
	 * @return Plugin manufacturer name of the plugin definition.
	 * 
	 * @throws PropertyNotPresentException The optional manufacturer's name is not set for the
	 * plugin definition.
	 */
	public @AAFString String getDeviceManufacturerName()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns a {@linkplain NetworkLocator network locator} pointing to
	 * manufacturer information for the defined plugin, which provides a link to
	 * a web page containing information about the manufacturer.</p>
	 * 
	 * @return Link to manufacturer information about the manufacturer of this plugin 
	 * definition.
	 * 
	 * @throws PropertyNotPresentException The optional manufacturer information property is not present
	 * for this plugin definition.
	 */
	public NetworkLocator getManufacturerInfo()
		throws PropertyNotPresentException;

	/**
	 * <p>Set a {@linkplain NetworkLocator network locator} pointing to the
	 * location of manufacturer information for the defined plugin. A 
	 * successful call to this method will result in any existing 
	 * manufacturer information being replaced.</p>
	 * 
	 * @param manufacturerInfo Network locator for pointing to manufacturer
	 * information for the definition.
	 */
	public void setManufacturerInfo(
			NetworkLocator manufacturerInfo);

	/**
	 * <p>Returns an {@linkplain tv.amwa.maj.record.AUID AUID} that identifies the 
	 * manufacturer of the defined plugin. This is an optional property.</p>
	 * 
	 * @return Manufacturer id of the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional manufacturer id property is not 
	 * present in this plugin definition.
	 */
	public AUID getManufacturerID()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the {@linkplain tv.amwa.maj.record.AUID AUID} that identifies the 
	 * manufacturer of the defined plugin. Set this optional property to <code>null</code>
	 * to omit it.</p>
	 * 
	 * @param manufacturerID Manufacturer id for the defined plugin.
	 */
	public void setManufacturerID(
			AUID manufacturerID);

	/**
	 * <p>Returns the hardware platform identifier of the defined plugin, which identifies 
	 * the platform by both its hardware platform and operating system.
	 * This is an optional property.</p>
	 * 
	 * @return Hardware platform identifier for the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional hardware platform property is not present 
	 * in this plugin definition.
	 * 
	 * @see tv.amwa.maj.constant.PluginIdentifiers#Platform_Independent
	 */
	public @HardwarePlatform AUID getPluginPlatform()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the hardware platform identifier of the defined plugin, which identifies 
	 * the platform by both the hardware platform and operating system.
	 * Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param hardwarePlatform Hardware platform identifier for the defined plugin.
	 * 
	 * @see tv.amwa.maj.constant.PluginIdentifiers#Platform_Independent
	 */
	public void setPluginPlatform(
			@HardwarePlatform AUID hardwarePlatform);

	// TODO is property not present appropriate when only a lower limit is defined? For ALL ranges.
	
	/**
	 * <p>Returns the minimum and maximum platform {@linkplain tv.amwa.maj.record.VersionType 
	 * version} properties of the defined plugin, which specify
	 * the minimum and maximum versions of the platform for which 
	 * the plugin will function. The platform version range properties
	 * are optional.</p>
	 * 
	 * <p>The value returned is an array containing two {@linkplain tv.amwa.maj.record.VersionType
	 * version numbers} with the value at index&nbsp;0 being the minimum
	 * version number and the value at index&nbsp;1 being the maximum.</p>
	 * 
	 * @return Array containing the minimum and maximum platform 
	 * versions of the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional platform version range is
	 * not fully specified in this plugin definition.
	 * 
	 * @see #getPlatformMinimumVersion()
	 * @see #getPlatformMaximumVersion()
	 */
	public VersionType[] getPlatformVersionRange()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the minimum acceptable platform version for the defined plugin, which
	 * specifies the minimum version of the platform on which this plugin will
	 * function. This is an optional property.</p>
	 * 
	 * @return Minimum acceptable platform version for the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional platform minimum version property
	 * is not present for this plugin definition.
	 * 
	 * @see #getPlatformMaximumVersion()
	 * @see #getPlatformVersionRange()
	 */
	public VersionType getPlatformMinimumVersion()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the maximum acceptable platform version for the defined plugin, which
	 * specifies the maximum version of the platform on which this plugin will
	 * function. This is an optional property.</p>
	 * 
	 * @return Maximum acceptable platform version for the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional platform maximum version property
	 * is not present for this plugin definition.
	 * 
	 * @see #getPlatformMinimumVersion()
	 * @see #getPlatformVersionRange()
	 */
	public VersionType getPlatformMaximumVersion()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the minimum platform {@linkplain tv.amwa.maj.record.VersionType version} property
	 * of the defined plugin, which specifies the minimum version of the platform
	 * for which the plugin will function. Set this optional property to
	 * <code>null</code> to omit it.</p>
	 * 
	 * @param minVersion Minimum platform version of the defined plugin.
	 */
	public void setPlatformMinimumVersion(
			VersionType minVersion);

	/**
	 * <p>Sets the maximum platform {@linkplain tv.amwa.maj.record.VersionType version} property
	 * of the defined plugin, which specifies the maximum version of the platform
	 * for which the plugin will function.  Set this optional property to
	 * <code>null</code> to omit it.</p>
	 * 
	 * @param maxVersion Maximum platform version of the defined plugin.
	 */
	public void setPlatformMaximumVersion(
			VersionType maxVersion);

	/**
	 * <p>Returns the software engine identifier that identifies the software
	 * subsystem used for essence management and playback for the defined plugin.
	 * This is an optional property.</p>
	 *   
	 * @return Software engine identifier for the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional engine property is not present
	 * in this plugin definition.
	 * 
	 * @see tv.amwa.maj.constant.PluginIdentifiers#Engine_None
	 */
	public @Engine AUID getEngine()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the software engine identifier that identifies the software
	 * subsystem used for essence management and playback for the 
	 * defined plugin. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param engine Software engine identifier for the defined plugin.
	 * 
	 * @see tv.amwa.maj.constant.PluginIdentifiers#Engine_None
	 */
	public void setEngine(
			@Engine AUID engine);

	/**
	 * <p>Returns the minimum and maximum software engine {@linkplain tv.amwa.maj.record.VersionType 
	 * versions} of the defined plugin, which specify the minimum 
	 * and maximum versions of the engine for which this plugin will
	 * function. The engine version range properties are optional.</p>
	 * 
	 * <p>The value returned is an array containing two {@linkplain tv.amwa.maj.record.VersionType
	 * version numbers} with the value at index&nbsp;0 being the minimum
	 * version number and the value at index&nbsp;1 being the maximum.</p>
	 * 
	 * @return Array containing minimum and maximum engine versions
	 * for the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional engine version range property is not fully
	 * specified in this plugin definition.
	 * 
	 * @see #getEngine() 
	 */
	public VersionType[] getEngineVersionRange()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the minimum acceptable software engine version of the defined plugin, which specifies
	 * the minimum version of the engine for which this plugin will function. The minimum engine version 
	 * property is optional.</p>
	 * 
	 * @return Minimum acceptable software engine versions of the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional engine minimum version property is not present
	 * for this plugin definition.
	 * 
	 * @see #getEngine()
	 * @see #getEngineVersionRange()
	 * @see #getEngineMaximumVersion()
	 */
	public VersionType getEngineMinimumVersion()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the maximum acceptable software engine version of the defined plugin, which specifies
	 * the maximum version of the engine for which this plugin will function. The maximum engine version 
	 * property is optional.</p>
	 * 
	 * @return Maximum acceptable software engine versions of the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional engine maximum version property is not present
	 * for this plugin definition.
	 * 
	 * @see #getEngine()
	 * @see #getEngineVersionRange()
	 * @see #getEngineMinimumVersion()
	 */
	public VersionType getEngineMaximumVersion()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the minimum engine {@linkplain tv.amwa.maj.record.VersionType version} property
	 * of the defined plugin, which specifies the minimum version of the engine for which this 
	 * plugin will function. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param minVersion Minimum engine version for the defined plugin.
	 */
	public void setEngineMinimumVersion(
			VersionType minVersion);

	/**
	 * <p>Sets the maximum engine {@linkplain tv.amwa.maj.record.VersionType version} property
	 * of the defined plugin, which specifies the maximum version of the engine for which this 
	 * plugin will function. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param maxVersion Maximum engine version for the defined plugin.
	 */
	public void setEngineMaximumVersion(
			VersionType maxVersion);

	/**
	 * <p>Returns the identifier for the manufacturer's interface that is supported
	 * by the defined plugin. This is an optional property.</p>
	 * 
	 * @return Identifier for the plugin interface of the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional plugin API property is not
	 * present in this plugin definition.
	 * 
	 * @see tv.amwa.maj.constant.PluginIdentifiers#PluginAPI_EssenceAccess
	 */
	public @PluginAPI AUID getPluginAPI()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the identifier for the manufacturer's interface that is supported
	 * by the defined plugin. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param pluginApi Identifier for the plugin interface of the defined plugin.
	 * 
	 * @see tv.amwa.maj.constant.PluginIdentifiers#Engine_None
	 */
	public void setPluginAPI(
			@PluginAPI AUID pluginApi);

	/**
	 * <p>Returns the minimum and maximum plugin API {@linkplain tv.amwa.maj.record.VersionType 
	 * version} properties of the defined plugin, which are the minimum and maximum versions of 
	 * the plugin API for which this plugin will function. The plugin API version range 
	 * properties are optional.<p>
	 * 
	 * <p>The value returned is an array containing two {@linkplain tv.amwa.maj.record.VersionType
	 * version numbers} with the value at index&nbsp;0 being the minimum
	 * version number and the value at index&nbsp;1 being the maximum.</p>
	 * 
	 * @return Array containing plugin API minimum and maximum version
	 * for the defined plugin.
	 * 
	 * @throws PropertyNotPresentException The optional plugin API version range is not present in 
	 * this plugin definition. 
	 * 
	 * @see #getPluginAPI()
	 * @see #getPluginAPIMinimumVersion()
	 * @see #getPluginAPIMaximumVersion()
	 */
	public tv.amwa.maj.record.VersionType[] getPluginAPIVersionRange()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the minimum acceptable plugin API version supporting the defined plugin, 
	 * which is the minimum version of the plugin API for which this plugin will function. 
	 * The minimum plugin API version property is optional.</p>
	 * 
	 * @return Minimum acceptable plugin API version supporting the defined plugin
	 * 
	 * @throws PropertyNotPresentException The optional plugin API minimum version property
	 * is not present for this plugin definition.
	 * 
	 * @see #getPluginAPI()
	 * @see #getPluginAPIVersionRange()
	 * @see #getPlatformMaximumVersion()
	 */
	public VersionType getPluginAPIMinimumVersion()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the maximum acceptable plugin API version supporting the defined plugin, 
	 * which is the maximum version of the plugin API for which this plugin will function. 
	 * The maximum plugin API version property is optional.</p>
	 * 
	 * @return Maximum acceptable plugin API version supporting the defined plugin
	 * 
	 * @throws PropertyNotPresentException The optional plugin API maximum version property
	 * is not present for this plugin definition.
	 * 
	 * @see #getPluginAPI()
	 * @see #getPluginAPIVersionRange()
	 * @see #getPlatformMinimumVersion()
	 */
	public VersionType getPluginAPIMaximumVersion()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the minimum plugin API {@linkplain tv.amwa.maj.record.VersionType version} property 
	 * of the defined plugin, which specifies the minimum version number of the specified plugin
	 * interfaces that the plugin supports. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param minVersion The minimum plugin API version of the defined plugin.
	 */
	public void setPluginAPIMinimumVersion(
			VersionType minVersion);

	/**
	 * <p>Sets the maximum plugin API {@linkplain tv.amwa.maj.record.VersionType version} property 
	 * of the defined plugin, which specifies the maximum version number of the specified plugin
	 * interfaces that the plugin supports. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param maxVersion Maximum plugin API version for the defined plugin.
	 */
	public void setPluginAPIMaximumVersion(
			VersionType maxVersion);

	/**
	 * <p>Returns <code>true</code> if the defined plugin is 
	 * capable of running in a software-only environment;
	 * otherwise <code>false</code>. A value of <code>true</code> indicates
	 * that no additional hardware is required. If the result is 
	 * <code>true</code> then this plugin may also support hardware
	 * acceleration, as long as it also supports a software-only
	 * method of processing data.</p>
	 * 
	 * <p>If this optional property is not present, the default value of 
	 * {@value #SOFTWAREONLY_DEFAULT} is returned.</p>
	 * 
	 * @return Is the define plugin capable of running in a software-only environment?
	 * 
	 * @see #SOFTWAREONLY_DEFAULT
	 * @see #getIsSoftwareOnly()
	 */
	public @Bool boolean isSoftwareOnly();
	
	/**
	 * <p>Returns <code>true</code> if the defined plugin is 
	 * capable of running in a software-only environment;
	 * otherwise <code>false</code>. A value of <code>true</code> indicates
	 * that no additional hardware is required. If the result is 
	 * <code>true</code> then this plugin may also support hardware
	 * acceleration, as long as it also supports a software-only
	 * method of processing data.</p>
	 * 
	 * <p>If this optional property is not present, the default value of 
	 * {@value #SOFTWAREONLY_DEFAULT} is returned.</p>
	 * 
	 * @return Is the define plugin capable of running in a software-only environment?
	 * 
	 * @see #SOFTWAREONLY_DEFAULT
	 * @see #isSoftwareOnly()
	 */
	public boolean getIsSoftwareOnly();

	/**
	 * <p>Set to <code>true</code> if the defined plugin is capable 
	 * of running in a software-only environment;
	 * otherwise <code>false</code>. A value of <code>true</code> indicates
	 * that no additional hardware is required. If the result is 
	 * <code>true</code> then this plugin may also support hardware
	 * acceleration, as long as it also supports a software-only
	 * method of processing data.</p>
	 * 
	 * <p>The default value of this optional property is {@value #SOFTWAREONLY_DEFAULT}. To
	 * omit this optional property, call this method with <code>null</code>.
	 * 
	 * @param isSoftwareOnly Is the defined plugin capable of running in a software-only 
	 * environment?
	 * 
	 * @see #SOFTWAREONLY_DEFAULT
	 */
	public void setIsSoftwareOnly(
			@Bool Boolean isSoftwareOnly);

	/**
	 * <p>Returns <code>true</code> if the defined plugin
	 * is capable of running with a hardware accelerator;
	 * otherwise <code>false</code>. If the result is <code>true</code>
	 * then the plugin may additionally support software decompression.</p>
	 * 
	 * <p>If this optional property is not present, the default value of 
	 * {@value #ACCELERATOR_DEFAULT} is returned.</p>
	 * 
	 * @return Is the defined plugin capable of running with hardware acceleration?
	 * 
	 * @see #ACCELERATOR_DEFAULT
	 * @see #getIsAccelerated()
	 */
	public @Bool boolean isAccelerated();

	/**
	 * <p>Returns <code>true</code> if the defined plugin
	 * is capable of running with a hardware accelerator;
	 * otherwise <code>false</code>. If the result is <code>true</code>
	 * then the plugin may additionally support software decompression.</p>
	 * 
	 * <p>If this optional property is not present, the default value of 
	 * {@value #ACCELERATOR_DEFAULT} is returned.</p>
	 * 
	 * @return Is the defined plugin capable of running with hardware acceleration?
	 * 
	 * @see #ACCELERATOR_DEFAULT
	 * @see #isAccelerated()
	 */
	public boolean getIsAccelerated();
	
	/**
	 * <p>Set to <code>true</code> if the defined plugin is 
	 * capable of running with a hardware accelerator;
	 * otherwise <code>false</code>. If the result is <code>true</code>
	 * then the plugin may additionally support software decompression.</p>
	 * 
	 * <p>The default value for this optional property is {@value #ACCELERATOR_DEFAULT}. To
	 * omit this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param isAccelerated Is the defined plugin capable of running with hardware acceleration?
	 * 
	 * @see #ACCELERATOR_DEFAULT
	 */
	public void setIsAccelerated(
			@Bool Boolean isAccelerated);

	/**
	 * <p>Returns <code>true</code> if the defined plugin is capable of supporting authentication;
	 * otherwise <code>false</code>.</p>
	 * 
	 * <p>If this optional property is not present, the default value of 
	 * {@value #AUTHENTICATION_DEFAULT} is returned.</p>
	 * 
	 * @return Is the defined plugin capable of supporting authentication?
	 * 
	 * @see #AUTHENTICATION_DEFAULT
	 * @see #getSupportsAuthentication()
	 */
	public @Bool boolean supportsAuthentication();
	
	/**
	 * <p>Returns <code>true</code> if the defined plugin is capable of supporting authentication;
	 * otherwise <code>false</code>.</p>
	 * 
	 * <p>If this optional property is not present, the default value of 
	 * {@value #AUTHENTICATION_DEFAULT} is returned.</p>
	 * 
	 * @return Is the defined plugin capable of supporting authentication?
	 * 
	 * @see #AUTHENTICATION_DEFAULT
	 * @see #supportsAuthentication()
	 */
	public boolean getSupportsAuthentication();

	/**
	 * <p>Set to <code>true</code> if the defined plugin defined is capable of supporting authentication;
	 * otherwise <code>false</code>.</p>
	 * 
	 * <p>The default value for this optional property is {@value #AUTHENTICATION_DEFAULT}. To
	 * omit this optional property, call this method with <code>null</code>.</p>
	 * 
	 * @param supportsAuthentication Is the defined plugin capable of supporting authentication?
	 * 
	 * @see #AUTHENTICATION_DEFAULT
	 */
	public void setSupportsAuthentication(
			@Bool Boolean supportsAuthentication);

	/**
	 * <p>Returns the number of locators in the list of plugin locators of this 
	 * plugin definition, which specify an ordered list locations that
	 * provide access to the defined plugin's implementation. The number of locators may be zero if the 
	 * plugin is in the current file, which is also when this optional property is
	 * not present.</p>
	 * 
	 * @return Number of locators of this plugin definition.
	 */
	public @UInt32 int countPluginLocators();
	
	/**
	 * <p>Clear the list of locators to this plugin definition, omitting this optional
	 * property.</p>
	 */
	public void clearPluginLocators();

	/**
	 * <p>Append a {@linkplain Locator locator} to the list of plugin locators of 
	 * this plugin definition, which specify an ordered list of locations 
	 * that provide access to the defined plugin's implementation. Use this method to add a locator to be 
	 * scanned last when searching for the plugin's implementation. The locator list is an optional 
	 * property that will be made present by calling this method if it is currently omitted.</p>
	 * 
	 * @param pluginLocator Locator of the plugin to append to the list of
	 * locators of this plugin definition.
	 * 
	 * @throws NullPointerException The given locator to append is <code>null</code>.
	 */
	public void appendPluginLocator(
			Locator pluginLocator) 
		throws NullPointerException;

	/**
	 * <p>Prepend a {@linkplain Locator locator} to the list of plugin locators
	 * of this plugin definition, which specify an ordered list of locations
	 * that provide access to the defined plugin's implementation. Use this method to add a locator to be scanned 
	 * first when searching for the plugin's implementation. The locator list is an optional 
	 * property that will be made present by calling this method if it is currently omitted.</p>
	 * 
	 * @param pluginLocator Locator to prepend to the list of plugin locators of the
	 * plugin definition.
	 * 
	 * @throws NullPointerException The given locator to prepend is <code>null</code>.
	 */
	public void prependPluginLocator(
			Locator pluginLocator) 
		throws NullPointerException;

	/**
	 * <p>Insert a {@linkplain Locator locator} at the given index into the list of 
	 * locators of this plugin definition, which specify an ordered list of locations 
	 * that provide access to the defined plugin's implementation. Locators already existing 
	 * at the named and higher indices will be moved up to the next higher 
	 * index to accommodate to inserted item. The locator list is an optional 
	 * property that may be made present by calling this method with an index of&nbsp;0.</p>
	 * 
	 * @param index Index where the locator should be inserted.
	 * @param pluginLocator Locator to insert into the list of plugin locators of this
	 * plugin definition.
	 * 
	 * @throws NullPointerException The given locator to insert is <code>null</code>.
	 * @throws IndexOutOfBoundsException The given index is outside the
	 * acceptable range for the current list of locators.
	 */
	public void insertPluginLocatorAt(
			@UInt32 int index,
			Locator pluginLocator) 
		throws NullPointerException,
			IndexOutOfBoundsException;

	/**
	 * <p>Returns the {@linkplain Locator locator} at the given index from the list of 
	 * locators of this plugin descriptor, which specify an ordered list of locations 
	 * that provide access to the defined plugin's implementation.</p>
	 * 
	 * @param index Index of the locator to retrieve from the list of locators.
	 * @return Locator at the given index in the list of locators of the 
	 * plugin descriptor.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the
	 * acceptable range for the current list of locators.
	 */
	public Locator getPluginLocatorAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Remove the {@linkplain Locator locator} at the given index from
	 * the list of locators of the plugin definition, which specify an ordered list of 
	 * locations that provide access to the defined plugin's implementation.</p>
	 *  
	 * @param index Index of locator to remove from the list of locators
	 * of the plugin definition.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside
	 * the acceptable range for the current list of locators.
	 */
	public void removePluginLocatorAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the list of locators of the plugin definition, which specify an ordered list 
	 * of locations that provide access to the defined plugin's implementation.
	 * This is an optional property.</p>
	 * 
	 * @return Shallow copy of the list of the locators for this plugin definition.
	 * 
	 * @throws PropertyNotPresentException No locators for the plugin implementation are
	 * present in this plugin definition.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#LocatorStrongReferenceVector
	 */
	public List<? extends Locator> getPluginLocators()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the identifier of the implemented class of the defined plugin. This property specifies 
	 * the {@linkplain tv.amwa.maj.record.AUID AUID} for the {@linkplain ClassDefinition class definition} 
	 * of the {@linkplain DefinitionObject definition} or {@linkplain MetaDefinition meta definition} that 
	 * the defined plugin provides an implementation for.</p>
	 * 
	 * <p>As of AAF version 1.1, this property must be specified. This was an optional property in
	 * version 1.0.</p>
	 * 
	 * @param implementedClass Implemented class of the defined plugin.
	 * 
	 * @throws NullPointerException The given definition object identifier is <code>null</code>.
	 * 
	 * @see #getCategoryClass()
	 */
	public void setImplementedClass(
			AUID implementedClass) 
		throws NullPointerException;

	/**
	 * <p>Returns the identifier of the implemented class of teh defined plugin.This property specifies 
	 * the {@linkplain tv.amwa.maj.record.AUID AUID} for the {@linkplain ClassDefinition class definition} 
	 * of the {@linkplain DefinitionObject definition} or {@linkplain MetaDefinition meta definition} that 
	 * the defined plugin provides an implementation for.</p>
	 * 
	 * <p>As of AAF version 1.1, this property must be specified. This was an optional property in
	 * version 1.0.</p>
	 * 
	 * @return Implemented class of the defined plugin.
	 * 
	 * @throws PropertyNotPresentException Although the definition object should be specified, this
	 * was not a required property in older versions of the AAF specification and is not present for
	 * this plugin definition.
	 */
	public AUID getImplementedClass()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this plugin definition.</p>
	 *
	 * @return Cloned copy of this plugin definition.
	 */
	public PluginDefinition clone();
}

