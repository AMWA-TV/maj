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
 * $Log: EssenceDescriptor.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/12/15 19:00:57  vizigoth
 * Added clear locators method, previously missing from interface.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/27 11:07:33  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 13:04:51  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:24  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;

/**
 * <p>Specifies the description of the format of the content data associated with a file 
 * {@linkplain SourcePackage source package} or of the media associated with a physical 
 * {@linkplain SourcePackage source package}.</p>
 * 
 *
 * 
 * @see SourcePackage#getEssenceDescriptor()
 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDescriptorStrongReference
 *
 */

public abstract interface EssenceDescriptor 
	extends InterchangeObject {

	/**
	 * <p>Return the number of locators attached to this essence descriptor,
	 * which each have operating-system-dependent data or text information that 
	 * provide hints for finding files or physical media. The number of locators 
	 * may be zero if the essence is located within the current file.</p>
	 * 
	 * @return Number of locators in this essence descriptor.
	 */
	public @UInt32 int countLocators();

	/**
	 * <p>Clear all the locators attached to this essence descriptor, omitting this
	 * optional property.</p>
	 */
	public void clearLocators();
	
	/**
	 * <p>Append a locator to the list of locators of this essence descriptors, 
	 * with each locator having operating-system-dependent data or text information that 
	 * provide hints for finding files or physical media. Use this 
	 * function to add a locator to be scanned last when searching for
	 * the essence, providing a secondary location for the essence.</p>
	 * 
	 * @param locator Locator to append to the list of locators in this essence descriptor.
	 * 
	 * @throws NullPointerException The given locator is <code>null</code>. 
	 */
	public void appendLocator(
			Locator locator) 
		throws NullPointerException;

	/**
	 * <p>Prepend a locator to the list of locators of this essence descriptor,
	 * with each locator having operating-system-dependent data or text information that 
	 * provide hints for finding files or physical media.  Use this
	 * function to add a locator to be scanned first when searching for
	 * the essence, providing a new primary location for the essence.</p>
	 * 
	 * @param locator Locator to prepend to the list of locators in this essence descriptor.
	 */
	public void prependLocator(
			Locator locator) 
		throws NullPointerException;

	/** 
	 * <p>Inserts a locator into the list of locators of this essence descriptors 
	 * at the given index. Each locator has operating-system-dependent data or text information that 
	 * provide hints for finding files or physical media. The locators already
	 * existing at the given and higher indices will be moved to the
	 * next higher index to accommodate.</p>
	 * 
	 * @param index Index at which locator is to be inserted
	 * @param locator Locator to insert.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for the
	 * current list of essence descriptors.
	 */
	public void insertLocatorAt(
			@UInt32 int index,
			Locator locator) 
		throws NullPointerException,
			IndexOutOfBoundsException;

	/**
	 * <p>Retrieves the locator at the given index, where each locator has operating-system-dependent data 
	 * or text information that provide hints for finding files or physical media.</p>
	 * 
	 * @param index Index of locator to retrieve from the list of locators of the essence descriptor.
	 * @return Locator at given index.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for the
	 * current list of essence descriptors.
	 */
	public Locator getLocatorAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Removes the locator at the given index, where each locator has operating-system-dependent data 
	 * or text information that provide hints for finding files or physical media. Locators already
	 * existing at indices higher than the given index will be moved to
	 * the next lower index to accommodate.</p>
	 * 
	 * @param index Index of locator to remove from the list of locators of this essence descriptor.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for the
	 * current list of essence descriptors.
	 */
	public void removeLocatorAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns a list of all the locators in this essence descriptors, where each locator has operating-system-dependent data 
	 * or text information that provide hints for finding files or physical media. This is an optional property and is omitted when 
	 * the essence is in the current file.</p>
	 * 
	 * @return A shallow copy of the list of locators in the essence descriptor.
	 * 
	 * @throws PropertyNotPresentException No locators are present for this essence descriptor.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#LocatorStrongReferenceVector
	 */
	public List<? extends Locator> getLocators()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of sub descriptors attached to this essence descriptor, which 
	 * specify additional descriptor metadata that is not included in the main 
	 * essence descriptor class hierarchy. This list of sub-descriptors may be empty.</p>
	 *
	 * @return Number of sub descriptors attached to this essence descriptor.
	 */
	public int countSubDescriptors();

	/**
	 * <p>Append a sub descriptor to the list of sub descriptors of this essence descriptor,
	 * which specify additional descriptor metadata that is not included in the main 
	 * essence descriptor class hierarchy.</p>
	 *
	 * @param subDescriptor Sub descriptor to append to the list of sub descriptors for this essence descriptor.
	 * 
	 * @throws NullPointerException The given sub descriptor is <code>null</code>.
	 */
	public void appendSubDescriptor(
			SubDescriptor subDescriptor)
		throws NullPointerException;

	/**
	 * <p>Prepend a sub descriptor to the list of sub descriptors of this essence descriptor,
	 * which specify additional descriptor metadata that is not included in the main 
	 * essence descriptor class hierarchy.</p>
	 *
	 * @param subDescriptor Sub descriptor to prepend to the list of sub descriptors for this essence descriptor.
	 * 
	 * @throws NullPointerException The sub descriptor argument is <code>null</code>.
	 */
	public void prependSubDescriptor(
			SubDescriptor subDescriptor)
		throws NullPointerException;

	/**
	 * <p>Inserts the given sub descriptor into the list of sub descriptors of this 
	 * essence descriptor at the given index. Sub descriptors specify additional descriptor 
	 * metadata that is not included in the main essence descriptor class hierarchy.</p>
	 *
	 * @param index Index at which the sub descriptor should be inserted into the list of sub
	 * descriptors of this essence descriptor.
	 * @param subDescriptor Sub descriptor to insert.
	 * 
	 * @throws NullPointerException The sub descriptor argument is <code>null</code>.
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range 
	 * for the list of sub descriptors of this essence descriptor.
	 */
	public void insertSubDescriptorAt(
			@UInt32 int index,
	    	SubDescriptor subDescriptor)
		throws NullPointerException, 
			IndexOutOfBoundsException;

	/**
	 * <p>Returns the sub descriptor at the given index through the list of sub descriptors
	 * of this essence descriptor, which specify additional descriptor metadata that is not included in the main 
	 * essence descriptor class hierarchy.</p>
	 *
	 * @param index Index of the sub descriptor value to retrieve.
	 * @return Sub descriptor at the given index.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range 
	 * for the current list of sub descriptors.
	 */
	public SubDescriptor getSubDescriptorAt(
	    	@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Removes the sub descriptor at the given index through the list of sub descriptors
	 * of this essence descriptor, which specify additional descriptor metadata that is not 
	 * included in the main essence descriptor class hierarchy. Sub descriptors at higher 
	 * indices will be moved to fill the gap made by the removal.</p>
	 *
	 * @param index Index of the sub descriptor to remove.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range 
	 * for the current list of sub descriptors.
	 */
	public void removeSubDescriptorAt(
	    	@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the list of sub descriptors of this essence descriptor,
	 * which specify additional descriptor metadata that is not included in the main 
	 * essence descriptor class hierarchy. This is an optional property.</p>
	 *
	 * @return Shallow copy of the list of sub descriptors of this essence descriptor.
	 * 
	 * @throws PropertyNotPresentException No sub descriptors are present for this essence
	 * descriptor.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#SubDescriptorStrongReferenceVector
	 */
	public List<? extends SubDescriptor> getSubDescriptors()
		throws PropertyNotPresentException;

	/**
	 * <p>Create a cloned copy of this essence descriptor.</p>
	 * 
	 * @return Cloned copy of this essence descriptor.
	 */
	public EssenceDescriptor clone();
	
}

