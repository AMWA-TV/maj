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
 * $Log: ContentStorage.java,v $
 * Revision 1.4  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/19 21:54:15  vizigoth
 * Added convenience methods for getting packages and essence.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.10  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.9  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.8  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.7  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.6  2008/01/27 11:07:32  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.5  2007/12/17 17:14:43  vizigoth
 * Removed FileFormat enumeration as it is never used.
 *
 * Revision 1.4  2007/12/13 11:33:24  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.3  2007/12/04 13:04:51  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.2  2007/11/15 12:52:48  vizigoth
 * Edits to ensure source can make rough and ready javadoc.
 *
 * Revision 1.1  2007/11/13 22:08:44  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.Set;

import tv.amwa.maj.enumeration.CriteriaType;
import tv.amwa.maj.enumeration.PackageKind;
import tv.amwa.maj.exception.DuplicatePackageIDException;
import tv.amwa.maj.exception.EssenceNotFoundException;
import tv.amwa.maj.exception.PackageNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.misctype.NumTracks;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.union.SearchCriteria;



/**
 * <p>Specifies storage for content that has {@linkplain Package package} and 
 * {@linkplain EssenceData essence data} within it. An {@linkplain AAFFile AAF file} shall 
 * have exactly one content storage.</p>
 * 
 * <p>Note that there is no requirement for every package stored in a content storage to
 * have its essence data stored within the same content storage. However, it is likely
 * that if some essence data is stored in this content storage, its associated metadata 
 * file source package is also stored.</p>
 * 
 *
 *
 * @see Preface#getContentStorageObject()
 * @see tv.amwa.maj.industry.TypeDefinitions#ContentStorageStrongReference
 */

public interface ContentStorage 
	extends InterchangeObject {

	/** 
	 * <p>Returns the {@linkplain Package package} that matches the given {@linkplain tv.amwa.maj.record.PackageID package id}
	 * from the set of packages in this content storage.</p>
	 * 
	 * @param packageID The identifier of the package to find in this content storage.
	 * @return Package with the given identifier from this content storage.
	 * 
	 * @throws NullPointerException The given package identifier is <code>null</code>.
	 * @throws PackageNotFoundException The requested package is not stored in this content storage.
	 */
	public Package lookupPackage(
			PackageID packageID) 
		throws NullPointerException,
			PackageNotFoundException;
	
	/** 
	 * <p>Find the number of matches for the given {@linkplain tv.amwa.maj.enumeration.PackageKind package kind} 
	 * stored in this content storage.</p>
	 * 
	 * @param packageKind The kind of package to count.
	 * @return Total number of packages of the given package kind stored in this content storage.
	 * 
	 * @throws NullPointerException The given package kind is <code>null</code>.
	 * 
	 * @see #countPackages()
	 */
	public @NumTracks int countPackages(
			PackageKind packageKind) 
		throws NullPointerException;
	
	/**
	 * <p>Returns the total number of packages in this content storage.</p>
	 * 
	 * @return Total number of packages in this content storage.
	 * 
	 * @see #countPackages(PackageKind)
	 */
	public int countPackages();

	/** 
	 * <p>Returns a set of {@linkplain Package packages} from this content storage according 
	 * to a given {@linkplain tv.amwa.maj.union.SearchCriteria search criteria}. If the
	 * search criteria is <code>null</code> then all of the packages in storage are returned.</p>
	 * 
	 * @param searchCriteria Search criteria to filter the set of stored packages of this content
	 * storage with.
	 * @return Shallow copy of the set of all the packages matching the given search criteria.
	 * 
	 * @see tv.amwa.maj.union.SearchCriteria
	 * @see Preface#getPackages(SearchCriteria)
	 * @see tv.amwa.maj.industry.TypeDefinitions#PackageStrongReferenceSet
	 */
	public Set<? extends Package> getPackages(
			SearchCriteria searchCriteria);

	/**
	 * <p>Returns a set of all packages stored in this content storage.</p>
	 * 
	 * @return Shallow copy of the set of all the packages in this content storage.
	 * 
	 * @see #getPackages(SearchCriteria)
	 * @see Preface#getPackages(SearchCriteria)
	 */
	public Set<? extends Package> getPackages();
	
	/**
	 * <p>Adds the given {@linkplain Package package} to the set of packages in this content storage. </p>
	 * 
	 * @param packageToAdd Package to add to this content storage.
	 * 
	 * @throws NullPointerException The given package is <code>null</code>.
	 * @throws DuplicatePackageIDException The given package is already contained in  this content
	 * storage.
	 */
	public void addPackage(
			Package packageToAdd) 
		throws NullPointerException,
			DuplicatePackageIDException;

	/** 
	 * <p>Removes the given {@linkplain Package package} from the set stored in this content storage.</p>
	 * 
	 * @param packageToRemove Package to remove from this content storage.
	 * 
	 * @throws NullPointerException The given package to remove is <code>null</code>.
	 * @throws PackageNotFoundException The given package is not current contained in this content storage.
	 */
	public void removePackage(
			Package packageToRemove) 
		throws NullPointerException,
			PackageNotFoundException;

	/**
	 * <p>Returns the total number of {@linkplain EssenceData essence data items} 
	 * in this content storage.</p>
	 * 
	 * @return Total number of essence data items.
	 */
	public @UInt32 int countEssenceDataObjects();

	/**
	 * <p>Returns <code>true</code> if {@linkplain EssenceData essence data} identified by the given 
	 * {@linkplain tv.amwa.maj.record.PackageID package id} is contained in this content storage.</p>
	 * 
	 * @param filePackageID Identifier of the essence data to check for within this content storage.
	 * @return True if the essence data is found in this content storage.
	 * 
	 * @throws NullPointerException One or more of the arguments in <code>null</code>.
	 */
	public @Bool boolean isEssenceDataPresent(
			PackageID filePackageID) 
		throws NullPointerException;

	// Method fixed to match textual description.
	/**
	 * <p>Returns the complete set of {@linkplain EssenceData essence data} contained within this content
	 * storage object. Essence data does not have to be carried in the same container as the metadata, 
	 * so this is an optional property.</p>
	 * 
	 * <p>Calling this method produces the same result as calling {@link #getEssenceDataObjects(CriteriaType)
	 * getEssenceData(CriteriaType.AnyRepresentation)}.</p>
	 * 
	 * @return Shallow copy of the set of essence data stored in this content
	 * storage.
	 * 
	 * @throws PropertyNotPresentException No essence data objects are present in this content storage.
	 * 
	 * @see #getEssenceDataObjects()
	 * @see Preface#enumEssenceData()
	 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDataStrongReferenceSet
	 */	
	public Set<? extends EssenceData> enumEssenceDataObjects()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the complete set of {@linkplain EssenceData essence data} contained within this content
	 * storage object. Essence data does not have to be carried in the same container as the metadata, 
	 * so this is an optional property.</p>
	 * 
	 * <p>Calling this method produces the same result as calling {@link #getEssenceDataObjects(CriteriaType)
	 * getEssenceData(CriteriaType.AnyRepresentation)}.</p>
	 * 
	 * @return Shallow copy of the set of essence data stored in this content
	 * storage.
	 * 
	 * @throws PropertyNotPresentException No essence data objects are present in this content storage.
	 * 
	 * @see #getEssenceDataObjects(CriteriaType)
	 * @see #enumEssenceDataObjects()
	 * @see Preface#enumEssenceData()
	 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDataStrongReferenceSet
	 */
	public Set<? extends EssenceData> getEssenceDataObjects()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the set of {@linkplain EssenceData essence data} contained within this content
	 * storage object, filtered according to the {@linkplain tv.amwa.maj.enumeration.CriteriaType 
	 * media criteria} provided.</p>
	 * 
	 * <p>If the mediaCriteria is set to {@link CriteriaType#AnyRepresentation}, all 
	 * essence data is returned.</p>
	 * 
	 * @param mediaCriteria Criteria to use to select the required essence data.
	 * @return Shallow copy of the set of essence stored in the content storage object.
	 * 
	 * @see #enumEssenceDataObjects()
	 * @see Preface#getEssenceData(CriteriaType)
	 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDataStrongReferenceSet
	 */	
	public Set<? extends EssenceData> getEssenceDataObjects(
			CriteriaType mediaCriteria);

	/**
	 * <p>Adds the given {@linkplain EssenceData essence data item} to the set of those stored
	 * in this content storage..</p>
	 * 
	 * @param essenceData Essence data object to add to those stored in this content storage.
	 * 
	 * @throws DuplicatePackageIDException The given package has already been added. 
	 * The validation is done by comparing package ids, which should be unique amongst all essence
	 * data in a content storage unit.
	 * @throws NullPointerException The given essence data item is <code>null</code>.
	 * 
	 * @see EssenceData#getLinkedPackageID()
	 */
	public void addEssenceDataObject(
			EssenceData essenceData) 
		throws DuplicatePackageIDException,
			NullPointerException;

	/** 
	 * <p>Removes the given {@linkplain EssenceData essence data item} from the set stored in
	 * this content storage.</p>
	 * 
	 * @param essenceData Essence data to remove from this content storage.
	 * 
	 * @throws NullPointerException The given essence data item is <code>null</code>.
	 * @throws EssenceNotFoundException The given essence data item is not currently contained in
	 * this content storage.
	 */
	public void removeEssenceDataObject(
			EssenceData essenceData) 
		throws NullPointerException,
			EssenceNotFoundException;

	/**
	 * <p>Looks up and returns an {@linkplain EssenceData essence data} item stored in this content storage 
	 * that matches the given {@linkplain tv.amwa.maj.record.PackageID package id}.</p>
	 * 
	 * @param packageId The package id identifying the essence data stored in this content storage.
	 * @return Essence data stored in this content storage with the given package id.
	 * 
	 * @throws NullPointerException The given essece data package id is <code>null</code>.
	 * @throws PackageNotFoundException The requested essence data was not found stored in this content
	 * storage.
	 */
	public EssenceData lookupEssenceDataObject(
			PackageID packageId) 
		throws NullPointerException,
			PackageNotFoundException;

	/**
	 * <p>Create a cloned copy of this content storage.</p>
	 *
	 * @return Cloned copy of this content storage.
	 */
	public ContentStorage clone();
}
