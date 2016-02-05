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
 * $Log: EssenceDescriptorImpl.java,v $
 * Revision 1.4  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/25 14:18:28  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/12/15 19:05:24  vizigoth
 * In the current clone-everywhere approach, clearing an existing list is not helpful ... creating a new one is.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
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
 * Revision 1.2  2007/12/04 13:04:49  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:40  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

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
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.EssenceDescriptor;
import tv.amwa.maj.model.Locator;
import tv.amwa.maj.model.SubDescriptor;


/** 
 * <p>Implements the description of the format of the content data associated with a file 
 * {@linkplain tv.amwa.maj.model.SourcePackage source package} or of the media associated with a physical 
 * {@linkplain tv.amwa.maj.model.SourcePackage source package}.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDescriptorStrongReference
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2400,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "EssenceDescriptor",
		  description = "The EssenceDescriptor class describes the format of the essence associated with a file SourcePackage or of the media associated with a physical SourcePackage.",
		  symbol = "EssenceDescriptor",
		  isConcrete = false)
public class EssenceDescriptorImpl
	extends InterchangeObjectImpl
	implements EssenceDescriptor,
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9112399211748828585L;
	
	private List<Locator> locators = 
		Collections.synchronizedList(new Vector<Locator>());
	private List<SubDescriptor> subDescriptors = 
		Collections.synchronizedList(new Vector<SubDescriptor>());

	@MediaListAppend("Locators")
	public void appendLocator(
			Locator locator)
		throws NullPointerException {

		if (locator == null)
			throw new NullPointerException("Cannot append a null locator to the list of locators of this essence descriptor.");
		
		StrongReferenceVector.append(locators, locator);
	}

	@MediaPropertyCount("Locators")
	public int countLocators() {

		return locators.size();
	}

	@MediaListGetAt("Locators")
	public Locator getLocatorAt(
			int index)
		throws IndexOutOfBoundsException {

		return StrongReferenceVector.getAt(locators, index);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0603, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Locators",
			aliases = { "Locator", "EssenceDescriptorLocator", "EssenceDescriptorLocators" },
			typeName = "LocatorStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2F01,
			symbol = "Locators")
	public List<Locator> getLocators() 
		throws PropertyNotPresentException {
		
		if (locators.size() == 0)
			throw new PropertyNotPresentException("No locators are present for this essence descriptor.");

		return StrongReferenceVector.getOptionalList(locators);
	}

	@MediaListInsertAt("Locators")
	public void insertLocatorAt(
			int index,
			Locator locator)
			throws NullPointerException,
				IndexOutOfBoundsException {

		if (locator == null)
			throw new NullPointerException("Cannot insert a null locator into the list of locators of this essence descriptor.");
		
		StrongReferenceVector.insert(locators, index, locator);
	}

	@MediaListPrepend("Locators")
	public void prependLocator(
			Locator locator)
		throws NullPointerException {

		if (locator == null)
			throw new NullPointerException("Cannot prepend a null locator to the list of locators of this essence descriptor.");
		
		StrongReferenceVector.prepend(locators, locator);
	}

	@MediaListRemoveAt("Locators")
	public void removeLocatorAt(
			int index)
		throws IndexOutOfBoundsException {

		StrongReferenceVector.remove(locators, index);
	}

	@MediaPropertyClear("Locators")
	public void clearLocators() {
		
		locators = Collections.synchronizedList(new Vector<Locator>());
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = 0x0610, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x09},
			     definedName = "SubDescriptors",
			     typeName = "SubDescriptorStrongReferenceVector",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x2F02,
			     symbol = "SubDescriptors")
	public List<SubDescriptor> getSubDescriptors() 
		throws PropertyNotPresentException {
		
		if (subDescriptors.size() == 0)
			throw new PropertyNotPresentException("No sub descriptors are present for this essence descriptor.");
		
		return StrongReferenceVector.getOptionalList(subDescriptors);
	}

	@MediaPropertyCount("SubDescriptors")
	public int countSubDescriptors() {

		return subDescriptors.size();
	}

	@MediaListAppend("SubDescriptors")
	public void appendSubDescriptor(
			SubDescriptor subDescriptor)
		throws NullPointerException {
		
		// Needed in testing as no sub descriptor implementations are available
		if ((subDescriptor == null) && (subDescriptors.isEmpty())) return;
		
		if (subDescriptor == null) 
			throw new NullPointerException("Cannot append a null sub descriptor to the list of sub descriptors of this essence descriptor.");
			
		StrongReferenceVector.append(subDescriptors, subDescriptor);
	}

	@MediaListGetAt("SubDescriptors")
	public SubDescriptor getSubDescriptorAt(
	    	@UInt32 int index)
		throws IndexOutOfBoundsException {
		
		return StrongReferenceVector.getAt(subDescriptors, index);
	}

	@MediaListPrepend("SubDescriptors")
	public void prependSubDescriptor(
			tv.amwa.maj.model.SubDescriptor subDescriptor) {
		
		if (subDescriptor == null)
			throw new NullPointerException("Cannot prepend a null sub descriptor to the list of sub descriptors of this essence descriptor.");
		
		StrongReferenceVector.prepend(subDescriptors, subDescriptor);
	}

	@MediaListInsertAt("SubDescriptors")
	public void insertSubDescriptorAt(
			@UInt32 int index,
			SubDescriptor subDescriptor)
		throws NullPointerException, 
			IndexOutOfBoundsException {
		
		if (subDescriptor == null)
			throw new NullPointerException("Cannot insert a null sub descriptor to the list of sub descriptors of this essence descriptor.");

		StrongReferenceVector.insert(subDescriptors, index, subDescriptor);
	}

	@MediaListRemoveAt("SubDescriptors")
	public void removeSubDescriptorAt(
	    	@UInt32 int index)
		throws IndexOutOfBoundsException {
		
		StrongReferenceVector.remove(subDescriptors, index);
	}
	
	@MediaPropertyClear("SubDescriptors")
	public void clearSubDescriptors() {
		
		subDescriptors.clear();
	}

	public EssenceDescriptor clone() {
		
		return (EssenceDescriptor) super.clone();
	}

	@Override
	public String getComment() {
		
		return "local essence descriptor persistent id: " + getPersistentID();
	}
}
