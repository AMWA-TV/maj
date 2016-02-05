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
 * $Log: Preface.java,v $
 * Revision 1.6  2011/10/05 17:14:27  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.5  2011/07/27 17:29:40  vizigoth
 * Changed primary package handling to better match the 377-1 specification that declares the primary package as a weak reference 16 byte number.
 *
 * Revision 1.4  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/26 11:47:13  vizigoth
 * Pushed management of file last modified value to the application.
 *
 * Revision 1.2  2011/01/19 21:54:15  vizigoth
 * Added convenience methods for getting packages and essence.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/11/08 15:59:14  vizigoth
 * Exposed clear methods in Preface interface.
 *
 * Revision 1.3  2010/03/18 15:18:36  vizigoth
 * Added omitted getByteOrder method.
 *
 * Revision 1.2  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/01/27 11:07:25  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.4  2007/12/17 17:14:43  vizigoth
 * Removed FileFormat enumeration as it is never used.
 *
 * Revision 1.3  2007/12/13 11:33:24  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.2  2007/12/04 13:04:50  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:52  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;
import java.util.Set;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.enumeration.CriteriaType;
import tv.amwa.maj.enumeration.PackageKind;
import tv.amwa.maj.exception.DuplicatePackageIDException;
import tv.amwa.maj.exception.EssenceNotFoundException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.exception.PackageNotFoundException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.ProductVersion;
import tv.amwa.maj.record.TimeStamp;
import tv.amwa.maj.record.VersionType;
import tv.amwa.maj.union.SearchCriteria;



/**
 * <p>Specifies file-wide information and indexes. An {@linkplain AAFFile AAF file} shall 
 * have exactly one preface object.</p>
 * 
 * <p>When a preface is created, the contained {@linkplain Dictionary dictionary} object is 
 * automatically created.</p>
 * 
 * <p>Note that the <em>preface</em> of an AAF file was preciously known as the 
 * <em>header</em>.</p>
 * 
 *
 */

public interface Preface 
	extends InterchangeObject {

	/** 
	 * <p>Default value for the object model version property of a preface object which is
	 * {@value #OBJECTMODELVERSION_DEFAULT}. This proeprty is automatically maintained and so has
	 * no get or set method.</p> 
	 */
	public final static int OBJECTMODELVERSION_DEFAULT = 1;
	
	/**
	 * <p>Returns the {@linkplain Package package} that matches the given {@linkplain tv.amwa.maj.record.PackageID package id}
	 * stored in the {@linkplain ContentStorage content storage} of this preface.</p>
	 * 
	 * @param packageID The identifier of the package to look up in the content storage of this preface.
	 * @return The package from the storage with the matching identifier. 
	 * 
	 * @throws NullPointerException The given package identifier is <code>null</code>.
	 * @throws PackageNotFoundException A package with the given identifier was not found in the content storage of
	 * this preface.
	 * 
	 * @see #getContentStorageObject()
	 * @see ContentStorage#lookupPackage(PackageID)
	 */
	public Package lookupPackage(
			PackageID packageID) 
		throws NullPointerException,
			PackageNotFoundException;

	/**
	 * <p>Returns the number of matches for the given {@linkplain tv.amwa.maj.enumeration.PackageKind package kind} within
	 * the {@linkplain ContentStorage content storage} of this preface. Use {@link tv.amwa.maj.enumeration.PackageKind#AllPackages}
	 * to count the total number of packages.</p>
	 * 
	 * @param packageKind The kind of packages to count.
	 * @return Total number of packages matching the given package kind in the content storage of this preface.
	 * 
	 * @see #getContentStorageObject()
	 * @see ContentStorage#countPackages(PackageKind)
	 */
	public @UInt32 int countPackages(
			PackageKind packageKind);

	/**
	 * <p>Returns a set of packages stored in the content storage of this preface that match the given 
	 * {@link tv.amwa.maj.union.SearchCriteria search criteria}. If the search criteria is 
	 * <code>null</code>, all packages are returned.</p> 
	 * 
	 * @param searchCriteria Search criteria to use to filter the set of packages
	 * returned.

	 * @return Set of packages matching the search criteria, 
	 * or the set of all packages if the search criteria was <code>null</code>. The 
	 * set does not clone packages it contains.
	 * 
	 * @see #getContentStorageObject()
	 * @see #getPackages()
	 * @see ContentStorage#getPackages(SearchCriteria)
	 * @see ContentStorage#getPackages()
	 * 	 
	 */
	public Set<? extends Package> getPackages(
			SearchCriteria searchCriteria);

	/**
	 * <p>Returns all the packages stored in the {@linkplain ContentStorage content storage}
	 * of this preface.</p>
	 * 
	 * @return Set of all packages stored in the content storage of this preface.
	 * 
	 * @see #getContentStorageObject()
	 * @see #getPackages(SearchCriteria)
	 * @see ContentStorage#getPackages()
	 */
	public Set<? extends Package> getPackages();
	
	/**
	 * <p>Adds a {@linkplain Package package} to the {@linkplain ContentStorage content storage} of this preface.</p>
	 * 
	 * @param packageToAdd Package to add to the content storage of this preface.
	 * 
	 * @throws NullPointerException The given package is <code>null</code>.
	 * @throws DuplicatePackageIDException A package with the same package id is already
	 * contained in the content storage of this preface.
	 * 
	 * @see #getContentStorageObject()
	 * @see ContentStorage#addPackage(Package)
	 */
	public void addPackage(
			Package packageToAdd) 
		throws NullPointerException,
			DuplicatePackageIDException;

	/**
	 * <p>Removes the given {@linkplain Package package} from the {@linkplain ContentStorage content storage} of 
	 * this preface.</p>
	 * 
	 * @param packageToRemove Package to remove from the preface.
	 * 
	 * @throws NullPointerException The given package to remove is <code>null</code>.
	 * @throws PackageNotFoundException The given package is not contained
	 * in the content storage of this preface.
	 * 
	 * @see #getContentStorageObject()
	 * @see ContentStorage#removePackage(Package)
	 */
	public void removePackage(
			Package packageToRemove) 
		throws NullPointerException,
			PackageNotFoundException;

	/**
	 * <p>Returns the total number of {@linkplain EssenceData essence data} items stored in the 
	 * {@linkplain ContentStorage content storage} of this preface.</p>
	 * 
	 * @return Total number of essence data items stored in the content storage of this preface.
	 * 
	 * @see #getContentStorageObject()
	 * @see ContentStorage#countEssenceDataObjects()
	 */
	public @UInt32 int countEssenceData();

	/**
	 * <p>Returns <code>true</code> if {@linkplain EssenceData essence data} identified by the 
	 * given {@linkplain tv.amwa.maj.record.PackageID package id} is present in the content storage of this preface.</p>
	 * 
	 * @param filePackageID Identifier of a file package that may be present in the content storage of this preface.
	 * @return Does the given package id match that of an essence data item stored in thie content storage of this
	 * preface?
	 *  
	 * @throws NullPointerException The given package id is <code>null</code>.
	 * @throws InvalidParameterException The given file package id does not identify a file source package.
	 * 
	 * @see #getContentStorageObject()
	 * @see ContentStorage#isEssenceDataPresent(PackageID)
	 */
	public @Bool boolean isEssenceDataPresent(
			PackageID filePackageID)
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns a set of all {@linkplain EssenceData essence data} stored in the 
	 * {@linkplain ContentStorage content storage} of this preface.</p>
	 * 
	 * @return Shallow copy of the set of all essence data stored in the content storage
	 * of this preface.
	 * 
	 * @see #getEssenceData(CriteriaType)
	 * @see #getContentStorageObject()
	 * @see ContentStorage#enumEssenceDataObjects()
	 */
	public Set<? extends EssenceData> enumEssenceData();

	
	/**
	 * <p>Returns a set of {@linkplain EssenceData essence data} from the {@linkplain ContentStorage content storage} of this 
	 * preface that matches the given {@linkplain tv.amwa.maj.enumeration.CriteriaType media criteria}. 
	 * If the media criteria is {@linkplain tv.amwa.maj.enumeration.CriteriaType#AnyRepresentation} 
	 * then all essence data that is stored in the content storage of this
	 * preface is included, producing the same result as calling {@link #enumEssenceData()}.</p>
	 * 
	 * @param mediaCriteria Criteria for selecting essence data from storage.
	 * @return Set of essence data matching the criteria that is stored in the content storage of this preface. Note that
	 * the essence data items are not cloned.
	 * 
	 * @see #getEssenceData()
	 * @see #enumEssenceData()
	 * @see #getContentStorageObject()
	 * @see ContentStorage#getEssenceDataObjects(CriteriaType)
	 */
	public Set<? extends EssenceData> getEssenceData(
			CriteriaType mediaCriteria) 
		throws NullPointerException;

	/**
	 * <p>Returns all the {@linkplain EssenceData essence data} objects from the 
	 * {@linkplain ContentStorage content storage} of this preface.</p>
	 * 
	 * @return Set of all the essence data objects from the content storage of this
	 * preface.
	 * 
	 * @see #enumEssenceData()
	 * @see #getEssenceData(CriteriaType)
	 * @see ContentStorage#getEssenceDataObjects()
	 */
	public Set<? extends EssenceData> getEssenceData();
	
	/**
	 * <p>Adds an {@linkplain EssenceData essence data} item to the {@linkplain ContentStorage content 
	 * storage} of this preface.</p>
	 * 
	 * @param essenceData Essence data to add to the content storage of this preface.
	 * 
	 * @throws DuplicatePackageIDException Essence data with the same package id is already contained
	 * in the content storage of this preface.
	 * @throws NullPointerException The given essence data item is <code>null</code>.
	 * 
	 * @see #getContentStorageObject()
	 * @see ContentStorage#addEssenceDataObject(EssenceData)
	 */
	public void addEssenceData(
			EssenceData essenceData) 
		throws DuplicatePackageIDException,
			NullPointerException;

	/**
	 * <p>Removes the given {@linkplain EssenceData essence data} item from the {@linkplain ContentStorage content
	 * storage} of this preface.</p>
	 * 
	 * @param essenceData Essence data to remove from the content storage of this preface.
	 * 
	 * @throws NullPointerException The given essence data item is <code>null</code>.
	 * @throws EssenceNotFoundException The given essence data item is not contained in the 
	 * content storage of this preface.
	 * 
	 * @see #getContentStorageObject()
	 * @see ContentStorage#removeEssenceDataObject(EssenceData)
	 */
	public void removeEssenceData(
			EssenceData essenceData) 
		throws NullPointerException, 
			EssenceNotFoundException;

	/**
	 * <p>Looks up and returns the {@linkplain EssenceData essence data} that matches the given 
	 * {@linkplain tv.amwa.maj.record.PackageID package id} from the {@linkplain ContentStorage content storage}
	 * of this preface.</p>
	 * 
	 * @param packageID Identifier for essence data stored in the content storage of this preface.
	 * @return Essence data stored in the content storage of this preface with the given package id.
	 * 
	 * @throws NullPointerException The given package id is <code>null</code>.
	 * @throws PackageNotFoundException Essence data identified with the given package id is not 
	 * stored in the content storage of the preface.
	 * 
	 * @see #getContentStorageObject()
	 * @see ContentStorage#lookupEssenceDataObject(PackageID)
	 */
	public EssenceData lookupEssenceData(
			PackageID packageID) 
		throws NullPointerException,
			PackageNotFoundException;

	/**
	 * <p>Returns the {@linkplain Dictionary dictionary} of this preface, which contains 
	 * all the {@linkplain DefinitionObject definitions} of the file. The dictionary is 
	 * automatically created when the preface object is created.</p>
	 * 
	 * @return AAF dictionary of the preface.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#DictionaryStrongReference
	 */
	public Dictionary getDictionaries();

	/**
	 * <p>Returns the {@linkplain Identification identification} of the last application 
	 * that modified the file.</p>
	 * 
	 * @return Identification of last application that modified the file.
	 */
	public Identification getLastIdentification();

	/**
	 * <p>Returns the {@linkplain Identification identification} that matches the given 
	 * generation identifier from the list of identifications of applications that created
	 * or modified the file of this preface.</p>
	 * 
	 * @param generation Unique generation identifier to retrieve from the list of identifications 
	 * of this preface.
	 * @return Identification with the given identifier from the list of identifications of this
	 * preface.
	 * 
	 * @throws NullPointerException The given generation identifier is <code>null</code>.
 	 * @throws ObjectNotFoundException An identification with the given identifier was not found
 	 * in the list of identifications of this preface.
	 */
	public Identification lookupIdentification(
			AUID generation) 
		throws NullPointerException,
			ObjectNotFoundException;

	/**
	 * <p>Returns the number of {@linkplain Identification identifications} in the preface, which 
	 * each identify an application that created or modified the file.</p>
	 * 
	 * @return Number of identifications in the list of identifications of this preface.
	 */
	public @UInt32 int countIdentifications();

	/**
	 * <p>Returns the list of {@linkplain Identification identifications} contained within
	 * this preface, which each identify an application that created or modified the file.</p>
	 * 
	 * @return Shallow copy of the list of identifications in this preface.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#IdentificationStrongReferenceVector
	 */
	public List<? extends Identification> getIdentifications();

	/**
	 * <p>Appends the given {@linkplain Identification identification} to the list of 
	 * identifications of this preface, which each identify an application that created or 
	 * modified the file. The identification at the end of the list should represent the most
	 * recent application to modify the file. This method does not attempt to identify duplicate 
	 * identifications, so it will succeed even if an identical identification is already contained
	 * in the list.</p>
	 * 
	 * @param identification Identification to append to the list of identifications of this preface.
	 * 
	 * @throws NullPointerException The given identification is <code>null</code>.
	 */
	public void appendIdentification(
			Identification identification) 
		throws NullPointerException;

	/**
	 * <p>Retrieves the {@linkplain Identification identification} at the given index through the 
	 * list of identifications of this preface, which each identify an application that created or 
	 * modified the file.</p>
	 * 
	 * @param index 0-based index of the identification to retrieve from the list of identifications
	 * of this preface.
	 * @return Identification at the given index.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for the 
	 * current list of identifications of this preface. 
	 */
	public Identification getIdentificationAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Return the version of the MAJ API currently
	 * running on this machine, which implements these interfaces.</p>
	 * 
	 * @return MAJ API version.
	 * 
	 * @see #getLastIdentification()
	 * @see Identification#getRefImplVersion()
	 */
	public ProductVersion getMajApiVersion();

	// TODO the next comment - com-api says this stub is not implemented.
	
	/**
	 * <p>Return the file version property of the preface.</p>
	 * 
	 * @return File version.
	 */
	public VersionType getFormatVersion();

	/**
	 * <p>Return the last modified {@linkplain tv.amwa.maj.record.TimeStamp time stamp} of 
	 * this preface, which specifies the time and date that this file was last modified.</p>
	 * 
	 * @return Last modification time stamp.
	 */
	public TimeStamp getFileLastModified();
	
	/**
	 * <p>Set the last modified {@linkplain tv.amwa.maj.record.TimeStamp time stamp} of 
	 * this preface, which specifies the time and date that this file was last modified.
	 * This value should match that of the 
	 * {@link #getLastIdentification() most recent identification}.</p>
	 * 
	 * @param fileLastModified Time stamp for the most recent modification of the file.
	 * 
	 * @throws NullPointerException Cannot set the timestamp using a <code>null</code> value.
	 * 
	 * @see #getLastIdentification()
	 */
	public void setFileLastModified(
			TimeStamp fileLastModified) 
		throws NullPointerException;

	/**
	 * <p>Returns the {@linkplain ContentStorage content storage} of this preface. This
	 * is the <em>Content</em> property of a preface.</p>
	 * 
	 * @return Content storage of this object.
	 */
	public ContentStorage getContentStorageObject();

	/**
	 * <p>Returns the primary package identifier for the file of this preface, which specifies the 
	 * {@linkplain Package package} that an MXF application treats as the default or primary package. 
	 * This is an optional property.</p>
	 * 
	 * @return Primary package identifier for the file of this preface.
	 * 
	 * @throws PropertyNotPresentException The optional primary package identifier is not present
	 * in the preface.
	 * 
	 * @see tv.amwa.maj.record.PackageID#getMaterial()
	 */
	public Package getPrimaryPackage() 
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the primary package identifier for the file of the preface, which specifies the {@linkplain Package package}
	 * that an MXF application treats as the default or primary package. Set this optional property to 
	 * <code>null</code> to omit it.</p>
	 * 
	 * @param primaryPackageID Primary package identifier for the file of this preface.
	 * 
	 * @see tv.amwa.maj.record.PackageID#getMaterial()
	 */
	public void setPrimaryPackage(
			  Package primaryPackageID);

	/**
	 * <p>Returns the identifier of the MXF operational pattern or AAF protocol that 
	 * the file of this preface complies with. This is an optional property.</p>
	 * 
	 * @return Operational pattern that the file of this preface complies with.
	 * 
	 * @throws PropertyNotPresentException Property is not present in the preface.
	 * 
	 * @see tv.amwa.maj.constant.OperationalPatternConstant
	 */
	public AUID getOperationalPattern()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the MXF operational pattern or AAF protocol that the file of this
	 * preface complies with. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param operationalPatternId Operational pattern identifier for the preface.
	 * 
	 * @throws NullPointerException The given operational pattern identifier is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.constant.OperationalPatternConstant
	 */
	public void setOperationalPattern(
			AUID operationalPatternId) 
		throws NullPointerException;

	/**
	 * <p>Ensures that the contents of the essence containers property
	 * is in sync with the file's metadata. If this method succeeds, the essence containers 
	 * property will contain identifiers of all {@linkplain ContainerDefinition container definitions}
	 * referenced by {@link SourcePackage source packages} in the file of the preface.</p>
	 * 
	 * <p>If the property isn't present it will be created. It is recommended that this method
	 * is called before any other of the methods affecting the essence containers
	 * can be called.</p>
	 * 
	 * @see ContainerDefinition
	 */
	public void updateEssenceContainers();

	/**
	 * <p>Returns the total number of {@linkplain ContainerDefinition essence containers} of this 
	 * preface, which identify the internal essence containers used in the file.</p>
	 * 
	 * @return Total number of essence containers present in the file
	 * of the preface.
	 * 
	 * @throws PropertyNotPresentException Essence containers are not present
	 * in the file of the preface.
	 */
	public @UInt32 int countEssenceContainers()
		throws PropertyNotPresentException;

	/**
	 * <p>Remove all essence containers from this preface, omitting this optional property.</p>
	 * 
	 * @see #getEssenceContainers()
	 * @see #countEssenceContainers()
	 */
	public void clearEssenceContainers();
	
	/**
	 * <p>Returns the set of {@linkplain ContainerDefinition essence containers} of this preface, which 
	 * identify the internal essence containers used in the file. This is an
	 * optional property that is automatically maintained.</p>
	 * 
	 * @return Copy of the identifiers for the internal essence containers of this preface. 
	 * The identifiers are cloned.
	 * 
	 * @throws PropertyNotPresentException The optional set of essence containers is not present
	 * for this preface.
	 * 
	 * @see tv.amwa.maj.constant.ContainerConstant
	 * @see ContainerDefinition
	 */
	public Set<AUID> getEssenceContainers()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns <code>true</code> if the {@linkplain ContainerDefinition essence containers} with the given
	 * identifier is present for this preface; otherwise <code>false</code>. The set
	 * of essence containers identify the internal essence containers used in the file.
	 * The essence containers property of a preface is optional.</p>
	 * 
	 * @param essenceContainerId Identifier of the type of essence container to check for.
	 * @return Is the given essence container identifier in this preface?
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.constant.ContainerConstant
	 * @see ContainerDefinition
	 * @see DefinitionObject#getAUID()
	 */
	public @Bool boolean isEssenceContainerPresent(
			AUID essenceContainerId) 
		throws NullPointerException;

	/**
	 * <p>Adds an essence container to the set of essence containers identifiers of this 
	 * preface, which identify the internal essence containers used in the file. The essence
	 * container property is optional. Adding an optional identifier causes the essence containers
	 * property to become present.</p>
	 * 
	 * @param essenceContainerID Essence container identifier to add to the set of essence 
	 * containers of this prefance.
	 * @throws NullPointerException Cannot add a null identifier to the set of essence
	 * containers.
	 * 
	 * @see ContainerDefinition#getAUID()
	 * @see #getEssenceContainers()
	 * @see #isEssenceContainerPresent(AUID)
	 */
	public void addEssenceContainer(
			AUID essenceContainerID)
		throws NullPointerException;
	
	/** 
	 * <p>Returns the total number of descriptive metadata schemes present 
	 * for this preface, which identify the descriptive metadata schemes used in 
	 * the file.</p>
	 * 
	 * @return Total number of descriptive schemes present in the
	 * file of this preface.
	 */
	public @UInt32 int countDescriptiveSchemes();

	/**
	 * <p>Remove all descriptive schemes from this preface, omitting this optional property.</p>
	 * 
	 * @see #getDescriptiveSchemes()
	 * @see #countDescriptiveSchemes()
	 */
	public void clearDescriptiveSchemes();
	
	/**
	 * <p>Returns the set of descriptive schemes of this preface, which identify the 
	 * descriptive metadata schemes used in the file.</p>
	 * 
	 * @return Copy of the identifiers of the descriptive schemes of this preface.
	 * 
	 * @throws PropertyNotPresentException The optional set of descriptive metadata schemes is 
	 * not present in this preface.
	 * 
	 * @see DescriptiveFramework
	 * @see DescriptiveClip
	 * @see tv.amwa.maj.meta.Root#getRootExtensions()
	 * @see DescriptiveMarker#getDescriptiveMetadataScheme()
	 * @see ExtensionScheme
	 */
	public Set<AUID> getDescriptiveSchemes()
	  	throws PropertyNotPresentException;

	/**
	 * <p>Returns <code>true</code> if the descriptive metadata scheme with 
	 * the given identifier is in set of schemes of this preface; otherwise <code>false</code>.</p>
	 * 
	 * @param descriptiveSchemeId Identifier of the descriptive metadata scheme to check for.
	 * @return Does the given identifier match that of a descriptive metadata scheme in this preface?
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * 
	 * @see DescriptiveFramework
	 * @see DescriptiveClip
	 */
	public @Bool boolean isDescriptiveSchemePresent(
			AUID descriptiveSchemeId) 
		throws NullPointerException;

	/**
	 * <p>Adds the given descriptive metadata scheme identifier to the set of
	 * schemes of this preface, which identify the 
	 * descriptive metadata schemes used in the file. If the optional set
	 * of descriptive metadata schemes property is omitted, it will become
	 * present after successful completion of this method.</p>
	 * 
	 * @param descriptiveSchemeID Descriptive metadata scheme identifier to add to the set of
	 * this preface.
	 * 
	 * @throws NullPointerException The given descriptive metadata scheme identifier is <code>null</code>.
	 * 
	 * @see DescriptiveFramework
	 * @see DescriptiveClip
	 */
	public void addDescriptiveScheme(
			AUID descriptiveSchemeID) 
		throws NullPointerException;

	/**
	 * <p>Removes the given descriptive metadata scheme identifier from the
	 * set of schemes of this preface, which identify the 
	 * descriptive metadata schemes used in the file. If the </p>
	 *
	 * @param descriptiveSchemeID Descriptive metadata scheme identifier to remove from the
	 * set of schemes of this preface.
	 * 
	 * @throws NullPointerException The given scheme identifier is <code>null</code>.
	 * @throws InvalidParameterException The given descriptive scheme identifier is 
	 * not present in the list of schemes of this preface.
	 * @throws PropertyNotPresentException The optional set of descriptive metadata schemes property is
	 * not present in this preface.
	 * 
	 * @see DescriptiveFramework
	 * @see DescriptiveClip
	 */
	public void removeDescriptiveScheme(
			AUID descriptiveSchemeID) 
		throws NullPointerException,
			InvalidParameterException,
			PropertyNotPresentException;
	
	/**
	 * <p>Returns the byte order of the file this preface was read from.</p>
	 * 
	 * @return Byte order of the file this preface was read from.
	 * 
	 * @see ByteOrder#getAAFByteOrderCode()
	 */
	public ByteOrder getByteOrder();
	
	/**
	 * <p>Returns the version of the persistent storage format for objects of this preface.</p>
	 * 
	 * @return Version of the persistent storage format for objects.
	 */
	public @UInt32 int getObjectModelVersion();
	
	/**
	 * <p>Returns the set of identifiers for {@linkplain ExtensionScheme application metadata
	 * schemes} used in the file. This is an optional property.</p>
	 * 
	 * @return Set of identifiers for {@linkplain ExtensionScheme application metadata
	 * schemes} used in the file.
	 * 
	 * @throws PropertyNotPresentException The optional application schemes property is not
	 * present in this preface.
	 * 
	 * @see #isApplicationSchemePresent(AUID)
	 * @see #addApplicationScheme(AUID)
	 * @see ExtensionScheme
	 */
	public Set<AUID> getApplicationSchemes()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Adds the identifier of an {@linkplain ExtensionScheme application metadata scheme} to the set of schemes
	 * used in this file. If this optional property is omitted, successful completion of this property
	 * will make the property present.</p>
	 * 
	 * @param applicationSchemeID Application metadata scheme identifier to add.
	 *  
	 * @throws NullPointerException Cannot add a <code>null</code> identifier to the set of application schemes.
	 * 
	 * @see #getApplicationSchemes()
	 * @see #isApplicationSchemePresent(AUID)
	 */
	public void addApplicationScheme(
			AUID applicationSchemeID)
		throws NullPointerException;
	
	/**
	 * <p>Returns <code>true</code> if the given identifier matches one of the 
	 * {@linkplain ExtensionScheme application metadata scheme} stated to be in the file.</p>
	 * 
	 * @param applicationSchemeID Application scheme identifier for check for.
	 * @return  Does the given identifier match that of an application metadata scheme in this preface?
	 * 
	 * @throws PropertyNotPresentException The optional application schemes property is not present in this
	 * preface.
	 * @throws NullPointerException Cannot test for an application scheme using a <code>null</code> pointer.
	 * 
	 * @see #getApplicationSchemes()
	 */
	public boolean isApplicationSchemePresent(
			AUID applicationSchemeID)
		throws PropertyNotPresentException,
			NullPointerException;
	
	/**
	 * <p>Returns the number of {@linkplain ExtensionScheme application metadata schemes} recorded in the 
	 * preface for this file.</p>
	 * 
	 * @return Number of application metadata schemes of this preface.
	 * 
	 * @see #getApplicationSchemes()
	 */
	public int countApplicationSchemes();
	
	/**
	 * <p>Remove all application schemes from this preface, omitting this optional property.</p>
	 * 
	 * @see #getApplicationSchemes()
	 * @see #countApplicationSchemes()
	 */
	public void clearApplicationSchemes();
	
	/**
	 * <p>Remove an {@linkplain ExtensionScheme application metadata scheme} from the set of application
	 * schemes of this file.</p>
	 *  
	 * @param applicationSchemeID Identifier of the application metadata scheme to remove.
	 * 
	 * @throws NullPointerException Cannot remove an application metadata schemes using a <code>null</code> identifier.
	 * @throws InvalidParameterException The given application metadata scheme identifier is not present in
	 * the set of application schemes of this preface.
	 * @throws PropertyNotPresentException The optional application schemes property is not present for this
	 * preface.
	 * 
	 * @see #getApplicationSchemes()
	 * @see #isApplicationSchemePresent(AUID)
	 */
	public void removeApplicationScheme(
			AUID applicationSchemeID)
		throws NullPointerException,
			InvalidParameterException,
			PropertyNotPresentException;
	
	/**
	 * <p>Iterate through the content storage packages and make sure the dictionary contains
	 * all the referenced definitions, adding any missing definitions.</p>
	 * 
	 * @return Does the dictionary now contain all of the required definitions?
	 * 
	 *  @see #getDictionaries()
	 */
	public boolean updateDictionaries();
	
	/**
	 * <p>Create a cloned copy of this preface.</p>
	 *
	 * @return Cloned copy of this preface.
	 */
	public Preface clone();

}



