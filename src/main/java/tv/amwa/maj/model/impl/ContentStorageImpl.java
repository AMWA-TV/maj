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
 * $Log: ContentStorageImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/11/08 15:30:17  vizigoth
 * Changes source package essence descriptor property to more correct essence description property.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.8  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.7  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.6  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.5  2008/01/14 21:11:03  vizigoth
 * Update to match refactoring of SearchByMobID.getMobId() method name.
 *
 * Revision 1.4  2007/12/17 17:14:15  vizigoth
 * Removed FileFormat enumeration as it is never used.
 *
 * Revision 1.3  2007/12/13 11:31:51  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.2  2007/12/04 13:04:46  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:16  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tv.amwa.maj.enumeration.CriteriaType;
import tv.amwa.maj.enumeration.PackageKind;
import tv.amwa.maj.exception.DuplicatePackageIDException;
import tv.amwa.maj.exception.EssenceNotFoundException;
import tv.amwa.maj.exception.PackageNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyContains;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.industry.StrongReferenceSet;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.AAFFileDescriptor;
import tv.amwa.maj.model.ContentStorage;
import tv.amwa.maj.model.EssenceData;
import tv.amwa.maj.model.EssenceDescriptor;
import tv.amwa.maj.model.Package;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.union.impl.SearchByAUIDImpl;
import tv.amwa.maj.union.impl.SearchByMediaCriteriaImpl;
import tv.amwa.maj.union.impl.SearchByNameImpl;
import tv.amwa.maj.union.impl.SearchByPackageIDImpl;
import tv.amwa.maj.union.impl.SearchByPackageKindImpl;


/** 
 * <p>Implements storage for content that has {@linkplain tv.amwa.maj.model.Package package} and 
 * {@linkplain tv.amwa.maj.model.EssenceData essence data} within it. An 
 * {@linkplain tv.amwa.maj.model.AAFFile AAF file} shall have exactly one content storage.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#ContentStorageStrongReference
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1800,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "ContentStorage",
		  description = "The ContentStorage class has the Package and EssenceData objects.",
		  symbol = "ContentStorage")
public class ContentStorageImpl
	extends InterchangeObjectImpl
	implements ContentStorage,
		Serializable,
		XMLSerializable,
		Cloneable {

	/**  */
	private static final long serialVersionUID = 1286145068134374378L;

	private Set<Package> packages = Collections.synchronizedSet(new HashSet<Package>());;
	private Set<EssenceData> essenceDataObjects = Collections.synchronizedSet(new HashSet<EssenceData>());;
	
	public ContentStorageImpl() {
	}

	/**
	 * <p>Creates and initializes a content storage object, which has the {@link PackageImpl package} and 
	 * {@link EssenceData essence data} objects of an AAF persistent unit. An AAF persistent unit 
	 * shall have exactly one content storage object.</p>
	 *
	 * @param packages Set of all packages to place into the new content storage.
	 * @param essenceData Set of all essence data objects to place into the new content storage.
	 * 
	 * @throws NullPointerException One or both of the sets are <code>null</code>. Use empty
	 * sets to indicate no packages or essence data items are in storage.
	 */
	public ContentStorageImpl(
			Set<Package> packages,
			Set<EssenceData> essenceData)
		throws NullPointerException {
	
		if (packages == null)
			throw new NullPointerException("Cannot set the packages of a new content storage using a null set value.");
		if (essenceData == null)
			throw new NullPointerException("Cannot set the essence data of a new content storage using a null set value.");
		
		for ( tv.amwa.maj.model.Package packageItem : packages )
			if (packageItem != null) StrongReferenceSet.add(packages, packageItem);
		
		for ( tv.amwa.maj.model.EssenceData essenceItem : essenceData )
			if (essenceItem != null) StrongReferenceSet.add(essenceData, essenceItem);
	}
	
	@MediaSetAdd("EssenceDataObjects")
	public void addEssenceDataObject(
			EssenceData essenceDataObject)
			throws DuplicatePackageIDException,
				NullPointerException {

		if (essenceDataObject == null)
			throw new NullPointerException("Cannot add essence data to content storage using null value.");
	
		if (this.essenceDataObjects.contains(essenceDataObject))
			throw new DuplicatePackageIDException("Essence data with the same package id as the given value is already in the content storage.");
		
		StrongReferenceSet.add(this.essenceDataObjects, essenceDataObject);
	}

	@MediaSetAdd("Packages")
	public void addPackage(
			tv.amwa.maj.model.Package packageToAdd)
			throws NullPointerException,
				DuplicatePackageIDException {

		if (packageToAdd == null)
			throw new NullPointerException("Cannot add a package to content storage using a null value.");
		
		if (this.packages.contains(packageToAdd))
			throw new DuplicatePackageIDException("A package with the same package id as the given value is already in the content storage.");
		
		StrongReferenceSet.add(this.packages, packageToAdd);
	}

	@MediaPropertyContains("Packages")
	public boolean containsPackage(
			tv.amwa.maj.model.Package packageToCheck) {
		
		return packages.contains(packageToCheck);
	}
	
	@MediaPropertyCount("EssenceDataObjects")
	public int countEssenceDataObjects() {

		return essenceDataObjects.size();
	}

	@MediaPropertyCount("Packages")
	public int countPackages() {
		
		return packages.size();
	}
	
	public int countPackages(
			PackageKind packageKind)
		throws NullPointerException {

		if (packageKind == null)
			throw new NullPointerException("Cannot count packages for a null package kind.");
		
		int packageCount = 0;
		for ( Package packageElement : packages ) {
			switch (packageKind) {
			
			case AllPackages:
				packageCount++;
				break;
			case CompositionPackage:
				if (packageElement instanceof tv.amwa.maj.model.CompositionPackage) packageCount++;
				break;
			case MaterialPackage:
				if (packageElement instanceof tv.amwa.maj.model.MaterialPackage) packageCount++;
				break;
			case FilmPackage: 
				if (packageElement instanceof tv.amwa.maj.model.SourcePackage) {
					if (((SourcePackageImpl) packageElement).getEssenceDescription() instanceof FilmDescriptorImpl) packageCount++;
				}
				break;		
			case PhysicalPackage:
				if (packageElement instanceof tv.amwa.maj.model.SourcePackage) {
					if (((SourcePackageImpl) packageElement).getEssenceDescription() instanceof PhysicalDescriptorImpl) packageCount++;
				}
				break;		
			case PrimaryPackage:
				// TODO check this! What is a primary package?
				if (packageElement instanceof tv.amwa.maj.model.SourcePackage) packageCount++;
				break;		
			case TapePackage:
				if (packageElement instanceof tv.amwa.maj.model.SourcePackage) {
					if (((SourcePackageImpl) packageElement).getEssenceDescription() instanceof TapeDescriptorImpl) packageCount++;
				}
				break;		

			default:
				break;
			}
		}
		
		return packageCount;
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0502, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EssenceDataObjects",
			aliases = { "EssenceData", "ContentStorageEssenceData" },
			typeName = "EssenceDataStrongReferenceSet", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1902,
			symbol = "EssenceDataObjects")
	public Set<EssenceData> enumEssenceDataObjects() 
		throws PropertyNotPresentException {
		
		if (essenceDataObjects.size() == 0)
			throw new PropertyNotPresentException("No essence data objects are present in this content storage.");

		return getEssenceDataObjects(CriteriaType.AnyRepresentation);
	}

	public Set<EssenceData> getEssenceDataObjects() 
		throws PropertyNotPresentException {
		
		return enumEssenceDataObjects();
	}
	
	public Set<EssenceData> getEssenceDataObjects(
			CriteriaType mediaCriteria) 
		throws NullPointerException {

		if ((mediaCriteria == null) || (mediaCriteria == CriteriaType.AnyRepresentation))
			return new HashSet<EssenceData>(essenceDataObjects);
		
		Set<Package> matchedPackages = 
			getPackages(new SearchByMediaCriteriaImpl(mediaCriteria));
		
		Set<EssenceData> matchedData = new HashSet<EssenceData>();
		for ( tv.amwa.maj.model.Package packageItem : matchedPackages ) {
			try {
				matchedData.add(lookupEssenceDataObject(packageItem.getPackageID()));
			}
			catch (PackageNotFoundException mnfe) { /* Just don't add the new value. */ }
		}
			
		return matchedData;
	}

	public EssenceData lookupEssenceDataObject(
			tv.amwa.maj.record.PackageID packageID)
			throws NullPointerException,
				PackageNotFoundException {
		
		if (packageID == null)
			throw new NullPointerException("Cannot find essence data in content storage from a null package id value.");
		
		for ( EssenceData essenceItem : essenceDataObjects )
			if (essenceItem.getLinkedPackageID().equals(packageID))
				return essenceItem;
		
		throw new PackageNotFoundException("Cannot find essence data in storage with the given package id.");
	}


	public Set<Package> enumPackages() {
		
		return getPackages();
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0501, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Packages",
			aliases = { "Mobs" },
			typeName = "PackageStrongReferenceSet",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1901,
			symbol = "Packages")	
	public Set<Package> getPackages() {
		
		return getPackages(new SearchByPackageKindImpl(PackageKind.AllPackages));
	}
	
	public final static Set<Package> initializePackages() {
		
		Set<Package> initialPackages = new HashSet<Package>(1);
		initialPackages.add(new MaterialPackageImpl(MaterialPackageImpl.initializePackageID(), "DefaultPackage"));
		return initialPackages;
	}
	
	public Set<Package> getPackages(
			tv.amwa.maj.union.SearchCriteria searchCriteria) {

		Set<Package> matchedPackages = new HashSet<Package>();
		
		for ( Package packageItem : packages ) {
			
			switch (searchCriteria.getSearchTag()) {
		
			case ByName:
				try {
						if (packageItem.getPackageName().equals(((SearchByNameImpl) searchCriteria).getName()))
								matchedPackages.add(packageItem);
					} 
				catch (PropertyNotPresentException e1) { }
				break;
			case ByPackageID:
				if (packageItem.getPackageID().equals(((SearchByPackageIDImpl) searchCriteria).getPackageID()))
					matchedPackages.add(packageItem);
				return matchedPackages;
			case ByClass:
				if (packageItem.getObjectClass().getAUID().equals(((SearchByAUIDImpl) searchCriteria).getAUID()))
					matchedPackages.add(packageItem);
				break;
			case ByPackageKind:
				PackageKind packageKind = ((SearchByPackageKindImpl) searchCriteria).getKind();
				switch (packageKind) {
				
				case AllPackages:
					return new HashSet<Package>(packages);
				case MaterialPackage:
					if (packageItem instanceof tv.amwa.maj.model.MaterialPackage)
						matchedPackages.add(packageItem);
					break;
				case CompositionPackage:
					if (packageItem instanceof tv.amwa.maj.model.CompositionPackage)
						matchedPackages.add(packageItem);
					break;
				case FilmPackage: 
					if (packageItem instanceof tv.amwa.maj.model.SourcePackage) {
						if (((SourcePackageImpl) packageItem).getEssenceDescription() instanceof FilmDescriptorImpl) 
							matchedPackages.add(packageItem);
					}
					break;		
				case PhysicalPackage:
					if (packageItem instanceof tv.amwa.maj.model.SourcePackage) {
						if (((SourcePackageImpl) packageItem).getEssenceDescription() instanceof PhysicalDescriptorImpl) 
							matchedPackages.add(packageItem);
					}
					break;		
				case PrimaryPackage:
					// TODO check this! What is a primary package?
					if (packageItem instanceof tv.amwa.maj.model.SourcePackage) 
						matchedPackages.add(packageItem);
					break;		
				case TapePackage:
					if (packageItem instanceof tv.amwa.maj.model.SourcePackage) {
						if (((SourcePackageImpl) packageItem).getEssenceDescription() instanceof TapeDescriptorImpl) 
							matchedPackages.add(packageItem);
					}
					break;		

				default:
					break; 
				}
				break; // switch (packageKind)
				
			case ByCompositionPackageUsageCode:
				if (!(packageItem instanceof tv.amwa.maj.model.CompositionPackage)) break;
				try {
						if (packageItem.getPackageUsage().equals(((SearchByAUIDImpl) searchCriteria).getAUID()))
							matchedPackages.add(packageItem);
					} 
				catch (PropertyNotPresentException e) { }
				break;
			case ByMaterialPackageUsageCode:
				if (!(packageItem instanceof tv.amwa.maj.model.MaterialPackage)) break;
				try {
						if (packageItem.getPackageUsage().equals(((SearchByAUIDImpl) searchCriteria).getAUID()))
							matchedPackages.add(packageItem);
					} 
				catch (PropertyNotPresentException e) { }
				break;
			case BySourcePackageUsageCode:
				if (!(packageItem instanceof tv.amwa.maj.model.SourcePackage)) break;
				try {
						if (packageItem.getPackageUsage().equals(((SearchByAUIDImpl) searchCriteria).getAUID()))
							matchedPackages.add(packageItem);
					} 
				catch (PropertyNotPresentException e) { }
				break;
			case ByUsageCode:
				try {
						if (packageItem.getPackageUsage().equals(((SearchByAUIDImpl) searchCriteria).getAUID()))
							matchedPackages.add(packageItem);
					} 
				catch (PropertyNotPresentException e) { }
				break;
			case ByMediaCrit: // FIXME how do you search on criteria type ... metadata?
				return new HashSet<Package>(packages);
			case ByDataDef: // TODO check this is the correct behaviour
			case NoSearch:
			default:
				return matchedPackages;
				
			} // switch (searchCriteria)

		} // for loop over all packages

		return matchedPackages;
	}

	@MediaPropertyContains("EssenceDataObjects")
	public boolean isEssenceDataObjectPresent(
			EssenceData essenceData) {
		
		return isEssenceDataPresent(essenceData.getLinkedPackageID());
	}

	public boolean isEssenceDataPresent(
			PackageID filePackageID)
		throws NullPointerException {

		if (filePackageID == null)
			throw new NullPointerException("Cannot check for the presence of essence data in content storage using null values.");
		
		for ( EssenceData essenceItem : essenceDataObjects ) 
			if (essenceItem.getLinkedPackageID().equals(filePackageID)) return true;
		
		return false;
	}
	
	public Package lookupPackage(
			PackageID packageID)
		throws NullPointerException,
			PackageNotFoundException {

		if (packageID == null)
			throw new NullPointerException("Cannot check for the presence of packages in content storage using null values.");
		
		for ( Package packageElement : packages) 
			if (packageElement.getPackageID().equals(packageID))
				return packageElement;
		
		throw new PackageNotFoundException("A package with the given ID cannot be found.");
	}

	@MediaPropertyRemove("EssenceDataObjects")
	public void removeEssenceDataObject(
			EssenceData essenceDataObject)
		throws NullPointerException,
			EssenceNotFoundException {

		if (essenceDataObject == null)
			throw new NullPointerException("Cannot remove essence data from content storage using a null value");
		
		if (!(this.essenceDataObjects.contains(essenceDataObject)))
			throw new EssenceNotFoundException("Cannot remove essence data from content storage as it is not currently stored.");
	
		StrongReferenceSet.remove(essenceDataObjects, essenceDataObject);
	}

	@MediaPropertyRemove("Packages")
	public void removePackage(
			Package packageToRemove)
		throws NullPointerException,
			PackageNotFoundException {

		if (packageToRemove == null)
			throw new NullPointerException("Cannot remove a package using a null value.");
		
		if (!(packages.contains(packageToRemove))) 
			throw new PackageNotFoundException("Cannot remove package from content storage as it is not currently stored.");
		
		StrongReferenceSet.remove(packages, packageToRemove);
	}

	@MediaPropertyClear("Packages")
	public void clearPackages() {
		
		packages.clear();
	}
	
	@MediaPropertyClear("EssenceDataObjects")
	public void clearEssenceDataObjects() {
		
		essenceDataObjects.clear();
	}
	
	public ContentStorage clone()  {
		
		return (ContentStorage) super.clone();

	}

	void addEssenceContainers(
			Set<AUID> essenceContainers)  {

		for ( Package packageItem : packages ) {
			if (packageItem instanceof SourcePackageImpl) {
				
				EssenceDescriptor descriptor = null;
				descriptor = ((SourcePackageImpl) packageItem).getEssenceDescription();
				
				if (descriptor instanceof AAFFileDescriptor) {
					AAFFileDescriptor fileDescriptor = (AAFFileDescriptor) descriptor;
				
					essenceContainers.add(fileDescriptor.getContainerFormat().getAUID());
				}
			}
		
		}
	}

	@Override
	public String getComment() {
		
		return "local content storage persistent identifier: " + getPersistentID();
	}
}
