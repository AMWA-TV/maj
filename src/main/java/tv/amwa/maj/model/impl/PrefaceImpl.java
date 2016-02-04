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
 * $Log: PrefaceImpl.java,v $
 * Revision 1.7  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.6  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.5  2011/07/27 17:33:00  vizigoth
 * Changed primary package handling to better match the 377-1 specification that declares the primary package as a weak reference 16 byte number.
 *
 * Revision 1.4  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/26 11:41:35  vizigoth
 * Pushed management of file last modified value to the application.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/12/15 19:07:51  vizigoth
 * Added MXF primary package property.
 *
 * Revision 1.3  2010/11/08 15:46:05  vizigoth
 * Changes source package essence descriptor property to more correct essence description property.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.6  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.5  2008/01/27 11:14:40  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.4  2007/12/17 17:14:15  vizigoth
 * Removed FileFormat enumeration as it is never used.
 *
 * Revision 1.3  2007/12/13 11:31:51  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.2  2007/12/04 13:04:47  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:42  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.enumeration.CriteriaType;
import tv.amwa.maj.enumeration.PackageKind;
import tv.amwa.maj.exception.DuplicatePackageIDException;
import tv.amwa.maj.exception.EssenceNotFoundException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.exception.ObjectNotAttachedException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.exception.PackageNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyContains;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.model.AAFFileDescriptor;
import tv.amwa.maj.model.ContentStorage;
import tv.amwa.maj.model.Dictionary;
import tv.amwa.maj.model.EssenceData;
import tv.amwa.maj.model.EssenceDescriptor;
import tv.amwa.maj.model.Identification;
import tv.amwa.maj.model.MultipleDescriptor;
import tv.amwa.maj.model.Package;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.ProductVersion;
import tv.amwa.maj.record.TimeStamp;
import tv.amwa.maj.record.VersionType;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.record.impl.TimeStampImpl;
import tv.amwa.maj.record.impl.VersionTypeImpl;

/** 
 * <p>Implements file-wide information and indexes. An {@linkplain tv.amwa.maj.model.AAFFile AAF file} shall 
 * have exactly one header object.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2f00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Preface",
		  aliases = { "Header" },
		  description = "The Preface class provides file-wide information and indexes.",
		  symbol = "Preface")
public class PrefaceImpl
	extends 
		InterchangeObjectImpl
	implements
		Preface, 
		tv.amwa.maj.extensions.quantel.QPreface,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 3343754479545511683L;
	
	private ByteOrder byteOrder;
	private TimeStamp fileLastModified;
	private VersionType formatVersion;
	private Integer objectModelVersion = null; // TODO not sure what this is, but is now persistent
	private AUID operationalPattern = null;
	private Set<AUID> essenceContainers =
		Collections.synchronizedSet(new HashSet<AUID>());
	private Set<AUID> descriptiveSchemes = 
		Collections.synchronizedSet(new HashSet<AUID>());
	private Set<AUID> applicationSchemes =
		Collections.synchronizedSet(new HashSet<AUID>());
	private ContentStorage contentStorageObject;
	private List<Identification> identificationList =
		Collections.synchronizedList(new Vector<Identification>());
	private Dictionary dictionaries;
	private WeakReference<Package> primaryPackage;
	
	/**
	 * <p>Creates and initializes a header object, the root object of any AAF persistent unit such
	 * as a file or database representation.</p>
	 */
	public PrefaceImpl() {
		
		// Java uses big endian by default
		this.byteOrder = ByteOrder.Big; // TODO make sure a little endian file sets this
		
		this.fileLastModified = new TimeStampImpl();
		this.dictionaries = new DictionaryImpl();
		
		this.formatVersion = new VersionTypeImpl((byte) 1, (byte) 0);

		IdentificationImpl madeInP4MsAPI = new IdentificationImpl(
				IdentificationImpl.APICompanyName,
				IdentificationImpl.APIProductName,
				IdentificationImpl.APIProductVersionString,
				IdentificationImpl.APIProductID);
		StrongReferenceVector.append(identificationList, madeInP4MsAPI);
		
		this.contentStorageObject = new ContentStorageImpl();
	}

	@MediaSetAdd("DescriptiveSchemes")
	public void addDescriptiveScheme(
			AUID descriptiveSchemeID)
		throws NullPointerException {

		if (descriptiveSchemeID == null)
			throw new NullPointerException("Cannot add a null value to the set of descriptive schemes.");
		
		descriptiveSchemes.add(descriptiveSchemeID.clone());
		// fileLastModified = new TimeStampImpl();
	}

	public void addEssenceData(
			EssenceData essenceData)
		throws DuplicatePackageIDException,
			NullPointerException {
		
		contentStorageObject.addEssenceDataObject(essenceData);
		// fileLastModified = new TimeStampImpl();
	}

	public void addPackage(
			Package packageToAdd)
		throws NullPointerException,
			DuplicatePackageIDException {

		contentStorageObject.addPackage(packageToAdd);

		// Consider refactoring this as duplicates code in ContentStorage.addEssenceContainers()
		if (packageToAdd instanceof SourcePackageImpl) {

			// try {
				EssenceDescriptor descriptor = ((SourcePackageImpl) packageToAdd).getEssenceDescription();
				if (descriptor instanceof AAFFileDescriptor) {
					
					addContainersForDescriptor((AAFFileDescriptor) descriptor);
				}
			//}
			// catch (NoEssenceDescriptorException nede) { /* Does not matter, so move on. */ }
		}
		// fileLastModified = new TimeStampImpl();
	}

	@MediaListAppend("IdentificationList")
	public void appendIdentification(
			tv.amwa.maj.model.Identification identification)
		throws NullPointerException {

		if (identification == null)
			throw new NullPointerException("Cannot append a null identification to this header.");
		
		removeInitialID();
		
		StrongReferenceVector.append(identificationList, identification);
		// fileLastModified = new TimeStampImpl(); // TODO is this what we want to do?
	}

	void removeInitialID() {
		
		if (identificationList.size() != 1) return;
		Identification isThisTheDefaultIdent = identificationList.get(0);
		if (!(isThisTheDefaultIdent.getApplicationName().equals("DefaultProductName"))) return;
		if (!(isThisTheDefaultIdent.getApplicationVersionString().equals("DefaultProductVersion"))) return;
		if (!(isThisTheDefaultIdent.getApplicationSupplierName().equals("DefaultCompanyName"))) return;
		clearIdentificationList();
	}
	
	@MediaListPrepend("IdentificationList")
	public void prependIdentification(
			Identification ident)
		throws NullPointerException {

		if (ident == null)
			throw new NullPointerException("Cannot prepend a null identification to this header.");
		
		StrongReferenceVector.prepend(identificationList, ident);
		// fileLastModified = new TimeStampImpl(); // TODO is this what we want to do?
	}
	
	@MediaPropertyCount("DescriptiveSchemes")
	public int countDescriptiveSchemes() {

		return descriptiveSchemes.size();
	}

	@MediaPropertyCount("EssenceContainers")
	public int countEssenceContainers()
			throws PropertyNotPresentException {

		return essenceContainers.size();
	}

	public int countEssenceData() {

		return contentStorageObject.countEssenceDataObjects();
	}

	@MediaPropertyCount("IdentificationList")
	public int countIdentifications() {

		return identificationList.size();
	}
	
	@MediaPropertyClear("IdentificationList")
	public void clearIdentificationList() {
		
		identificationList = Collections.synchronizedList(new Vector<Identification>());
	}

	public int countPackages(
			PackageKind packageKind) 
		throws NullPointerException {

		return contentStorageObject.countPackages(packageKind);
	}

	public Set<? extends EssenceData> enumEssenceData() {

		return getEssenceData(CriteriaType.AnyRepresentation);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0201, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ContentStorageObject",
			aliases = { "Content" },
			typeName = "ContentStorageStrongReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3B03,
			symbol = "ContentStorageObject")
	public ContentStorage getContentStorageObject() {

		return contentStorageObject;
	}

	@MediaPropertySetter("ContentStorageObject")
	public void setContentStorageObject(
			ContentStorage contentStorageObject) 
		throws NullPointerException {
		
		if (contentStorageObject == null)
			throw new NullPointerException("Cannot set the content for this header to null.");
		
		this.contentStorageObject = contentStorageObject;
	}
	
	public final static ContentStorage initializeContentStorageObject() {
		
		return new ContentStorageImpl();
	}

	@MediaProperty(uuid1 = 0x01020210, uuid2 = (short) 0x0202, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "DescriptiveSchemes",
			aliases = { "DMSchemes" },
			typeName = "AUIDSet", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3B0B,
			symbol = "DescriptiveSchemes")
	public Set<AUID> getDescriptiveSchemes()
			throws PropertyNotPresentException {
		/*
		if (descriptiveSchemes.size() == 0)
			throw new PropertyNotPresentException("Descriptive schemes not present in this header.");*/
		
		return new HashSet<tv.amwa.maj.record.AUID>(descriptiveSchemes);
	}	
	
	@MediaPropertyClear("DescriptiveSchemes")
	public void clearDescriptiveSchemes() {
		
		descriptiveSchemes = Collections.synchronizedSet(new HashSet<AUID>());
	}
	
	/** 
	 * <p>Returns the set of {@linkplain tv.amwa.maj.model.ContainerDefinition essence containers} of this header, 
	 * which identify the internal essence containers used in the file. This is an
	 * optional property that is automatically maintained.</p>
	 * 
	 * @return Copy of the identifiers for the internal essence containers of this header. 
	 * The identifiers are cloned.
	 */
	@MediaProperty(uuid1 = 0x01020210, uuid2 = (short) 0x0201, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "EssenceContainers",
			typeName = "AUIDSet", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3B0A,
			symbol = "EssenceContainers")
	public Set<AUID> getEssenceContainers()
			throws PropertyNotPresentException {
		
		if (essenceContainers.size() == 0)
			throw new PropertyNotPresentException("The optional essence containers property is not present in this header.");
		
		return new HashSet<AUID>(essenceContainers);
	}

	@MediaSetAdd("EssenceContainers")
	public void addEssenceContainer(
			AUID containerID) 
		throws NullPointerException {
		
		if (containerID == null)
			throw new NullPointerException("Cannot add a null essence container to this header.");
		
		essenceContainers.add(containerID.clone());
	}
	
	@MediaPropertyRemove("EssenceContainers")
	public void removeEssenceContainer(
			AUID essenceContainerID) 
		throws NullPointerException,
			InvalidParameterException,
			PropertyNotPresentException {
		
		if (essenceContainerID == null)
			throw new NullPointerException("Cannot remove a essence container with a null id from this header.");
		if (essenceContainers.size() == 0)
			throw new PropertyNotPresentException("The optional essence containers property is not present in this header.");

		if (!(essenceContainers.contains(essenceContainerID)))
			throw new InvalidParameterException("The given essence container is not present in this header.");
		
		essenceContainers.remove(essenceContainerID);
		
		// fileLastModified = new TimeStampImpl();
	}
	
	@MediaPropertyClear("EssenceContainers")
	public void clearEssenceContainers() {
		
		essenceContainers = Collections.synchronizedSet(new HashSet<AUID>());
	}
	
	public Set<? extends EssenceData> getEssenceData(
			CriteriaType mediaCriteria)
		throws NullPointerException {
		
		if (mediaCriteria == null)
			throw new NullPointerException("The given media criteria for filtering essence data to retrieve from this preface is null.");

		return contentStorageObject.getEssenceDataObjects(mediaCriteria);
	}
	
	public Set<? extends EssenceData> getEssenceData() {
		
		try {
			return contentStorageObject.getEssenceDataObjects();
		}
		catch (PropertyNotPresentException pnpe) {
			return new HashSet<EssenceData>();
		}
	}

	@MediaProperty(uuid1 = 0x03010201, uuid2 = (short) 0x0500, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FormatVersion",
			aliases = { "Version" },
			typeName = "VersionType", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3B05,
			symbol = "FormatVersion")
	public VersionType getFormatVersion() {

		return formatVersion.clone();
	}

	@MediaPropertySetter("FormatVersion")
	public void setFormatVersion(
			tv.amwa.maj.record.VersionType formatVersion) 
		throws NullPointerException {
		
		if (formatVersion == null)
			throw new NullPointerException("Cannot set the version to a null value.");
		
		this.formatVersion = formatVersion.clone();
	}
	
	public final static VersionType initializeFormatVersion() {
		
		return new VersionTypeImpl((byte) 1, (byte) 1);
	}

	@MediaListGetAt("IdentificationList")
	public Identification getIdentificationAt(
			int index)
		throws IndexOutOfBoundsException {

		return StrongReferenceVector.getAt(identificationList, index);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0604, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "IdentificationList",
			typeName = "IdentificationStrongReferenceVector", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3B06,
			symbol = "IdentificationList")	
	public List<Identification> getIdentifications() {

		return StrongReferenceVector.getRequiredList(identificationList);
	}
	
	public final static List<Identification> initializeIdentificationList() {
		
		List<Identification> initialIDs = new ArrayList<Identification>(1);
		initialIDs.add(new IdentificationImpl("DefaultCompanyName", "DefaultProductName", "DefaultProductVersion",
				AUIDImpl.randomAUID()));
		return initialIDs;
	}
	
	void setIdentificationListValue(List<Identification> identificaitonList) {
		
		this.identificationList = Collections.synchronizedList(identificaitonList);
	}
	
	void blankIdentificationsList() {
		
		this.identificationList = Collections.synchronizedList(new Vector<Identification>());
	}

	public Identification getLastIdentification() {

		return identificationList.get(identificationList.size() - 1);
	}

	// Last modified values to be handled automatically.
	@MediaProperty(uuid1 = 0x07020110, uuid2 = (short) 0x0204, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FileLastModified",
			aliases = { "LastModified" },
			typeName = "TimeStamp", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3B02,
			symbol = "FileLastModified")
	public TimeStamp getFileLastModified() {

		return fileLastModified.clone();
	}

	@MediaPropertySetter("FileLastModified")
	public void setFileLastModified(
			tv.amwa.maj.record.TimeStamp fileLastModified) 
		throws NullPointerException {
		
		if (fileLastModified == null)
			throw new NullPointerException("Cannot set the file last modified time of this header to a null value.");
		
		this.fileLastModified = fileLastModified.clone();
	}
	
	public final static TimeStamp initializeFileLastModified() {
		
		return new TimeStampImpl();
	}
	
	public Set<? extends Package> getPackages(
			tv.amwa.maj.union.SearchCriteria searchCriteria) {
		
		return contentStorageObject.getPackages(searchCriteria);
	}
	
	public Set<? extends Package> getPackages() {
		
		return contentStorageObject.getPackages();
	}

	@MediaProperty(uuid1 = 0x01020203, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "OperationalPattern",
			typeName = "AUID",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3B09,
			symbol = "OperationalPattern")
	public AUID getOperationalPattern()
			throws PropertyNotPresentException {

		if (operationalPattern == null)
			throw new PropertyNotPresentException("The operational pattern property is not present in this header.");
		
		return operationalPattern.clone();
	}

	@MediaPropertySetter("OperationalPattern")
	public void setOperationalPattern(
			AUID operationalPatternID) {
	
		if (operationalPatternID == null) {
			this.operationalPattern = null;
			// fileLastModified = new TimeStampImpl(); // TODO do we really want to do this here
			return;
		}
			
		this.operationalPattern = operationalPatternID.clone();
		// fileLastModified = new TimeStampImpl(); // TODO do we really want to do this here
	}

//	public Package getPrimaryPackage()
//			throws PropertyNotPresentException {
//
//		if (primaryPackage == null)
//			throw new PropertyNotPresentException("Primary package property is not present.");
//		
//		try {
//			return contentStorageObject.lookupPackage(primaryPackage);
//		}
//		catch (PackageNotFoundException mnfe) {
//			throw new PropertyNotPresentException("Unexpected mismatch between content storage and primary package definition.");
//		}
//	}
//
//	public void setPrimaryPackage(
//			Package primaryPackage) {
//	
//		try {
//			addPackage(primaryPackage);
//			// fileLastModified = new TimeStampImpl();
//		}
//		catch (DuplicatePackageIDException dmie) { /* That's OK here. */ }
//		catch (NullPointerException npe) { /* That's OK here as well. It resets. */ }
//			
//		this.primaryPackage = primaryPackage.getPackageID().clone();
//	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0108, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04},
			definedName = "PrimaryPackage",
			typeName = "PackageWeakReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3B08,
			symbol = "PrimaryPackage")
	public Package getPrimaryPackage() 
		throws PropertyNotPresentException {
		
		if (this.primaryPackage == null)
			throw new PropertyNotPresentException("The optional primary package property is not present for this preface.");
		
		return this.primaryPackage.getTarget();
	}
	
	@MediaPropertySetter("PrimaryPackage")
	public void setPrimaryPackage(
			Package primaryPackage) {
		
		if (primaryPackage == null) {
			this.primaryPackage = null;
			return;
		}
		this.primaryPackage = new WeakReference<Package>(primaryPackage);
	}
			
			
	public ProductVersion getMajApiVersion() {

		return getLastIdentification().getRefImplVersion();
	}

	@MediaPropertyContains("DescriptiveSchemes")
	public boolean isDescriptiveSchemePresent(
			AUID descriptiveSchemeID)
		throws NullPointerException {

		if (descriptiveSchemeID == null)
			throw new NullPointerException("Cannot check for a descriptive scheme with a null id.");

		return descriptiveSchemes.contains(descriptiveSchemeID);
	}

	@MediaPropertyContains("EssenceContainers")
	public boolean isEssenceContainerPresent(
			AUID essenceContainerID)
		throws NullPointerException {

		if (essenceContainerID == null)
			throw new NullPointerException("Cannot check for the presence of an essence container using a null identifier.");
		
		return essenceContainers.contains(essenceContainerID);
	}

	public boolean isEssenceDataPresent(
			tv.amwa.maj.record.PackageID filPackageID)
			throws NullPointerException,
				InvalidParameterException {

		try {
			Package filePackage = contentStorageObject.lookupPackage(filPackageID);
			if (!(filePackage instanceof SourcePackageImpl))
				throw new InvalidParameterException("The given package id does not correspond to a source package.");
			EssenceDescriptor descriptor = ((SourcePackageImpl) filePackage).getEssenceDescription();
			if (!(descriptor instanceof AAFFileDescriptor))
				throw new InvalidParameterException("The given package id does not correspond to a file source package.");
		}
		catch (PackageNotFoundException mnfe) { return false; }
		/* catch (NoEssenceDescriptorException nede) {
			throw new InvalidParameterException("The given package id has no corresponding essence descriptor.");
		} */
	
		return true;
	}

	public EssenceData lookupEssenceData(
			PackageID packageID)
		throws NullPointerException,
			PackageNotFoundException {
		
		return contentStorageObject.lookupEssenceDataObject(packageID);
	}

	public Identification lookupIdentification(	
			AUID generation)
		throws NullPointerException,
			ObjectNotFoundException {

		if (generation == null)
			throw new NullPointerException("Cannot look up an identification using a null AUID.");
		
		for ( Identification identificationItem : identificationList ) 
			if (identificationItem.getLinkedGenerationID().equals(generation)) 
				return identificationItem;
		
		throw new ObjectNotFoundException("An identification matching the given id could not be found.");
	}

	public Package lookupPackage(
			tv.amwa.maj.record.PackageID packageID)
		throws NullPointerException,
			PackageNotFoundException {

		return contentStorageObject.lookupPackage(packageID);
	}

	@MediaPropertyRemove("DescriptiveSchemes")
	public void removeDescriptiveScheme(
			AUID descriptiveSchemeID)
		throws NullPointerException,
			InvalidParameterException,
			PropertyNotPresentException {

		if (descriptiveSchemeID == null)
			throw new NullPointerException("Cannot remove a descriptive scheme with a null value.");
		if (descriptiveSchemes.size() == 0)
			throw new PropertyNotPresentException("The optional descriptive schemes property is not present in this header.");

		if (!(descriptiveSchemes.contains(descriptiveSchemeID)))
			throw new InvalidParameterException("The given descriptive scheme is not present in this header.");
		
		descriptiveSchemes.remove(descriptiveSchemeID);
		
		// fileLastModified = new TimeStampImpl();
	}

	public void removeEssenceData(
			EssenceData essenceData)
		throws NullPointerException,
			EssenceNotFoundException {
		
		contentStorageObject.removeEssenceDataObject(essenceData);
		// fileLastModified = new TimeStampImpl();
	}

	public void removePackage(
			tv.amwa.maj.model.Package packageToRemove)
			throws NullPointerException,
				PackageNotFoundException {

		if (packageToRemove == null)
			throw new NullPointerException("Cannot remove a null package value.");
		
		contentStorageObject.removePackage(packageToRemove);
		updateEssenceContainers();
		
		if (packageToRemove.getPackageID().equals(primaryPackage.getTarget().getPackageID()))
			primaryPackage = null;
		
		// fileLastModified = new TimeStampImpl();
	}

	public void updateEssenceContainers() {
		
		this.essenceContainers = Collections.synchronizedSet(new HashSet<AUID>());
		
		Set<? extends tv.amwa.maj.model.Package> packages = getPackages(null);
		
		for ( tv.amwa.maj.model.Package packageItem : packages ) {
			if (packageItem instanceof tv.amwa.maj.model.SourcePackage) {
				tv.amwa.maj.model.EssenceDescriptor packageDescriptor =
					((tv.amwa.maj.model.SourcePackage) packageItem).getEssenceDescription();
				if (packageDescriptor instanceof AAFFileDescriptor) 
					addContainersForDescriptor((AAFFileDescriptor) packageDescriptor);
			}
		}
	}

	private void addContainersForDescriptor(
			AAFFileDescriptor descriptor) {
		
		if (descriptor instanceof MultipleDescriptor) {
			for ( AAFFileDescriptor nestedDescriptor : ((MultipleDescriptor) descriptor).getFileDescriptors() ) {
				addContainersForDescriptor(nestedDescriptor);
			}
		}
		else
			essenceContainers.add(descriptor.getContainerFormat().getAUID());
	}
	
	public Identification getGeneration()
			throws InvalidParameterException,
				ObjectNotAttachedException {

		return getLastIdentification();
	}

	public AUID getLinkedGenerationID() {
		
		return getLastIdentification().getLinkedGenerationID();
	}
	
	@MediaProperty(uuid1 = 0x03010201, uuid2 = (short) 0x0200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "ByteOrder",
			typeName = "Int16", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3B01,
			symbol = "ByteOrder")
	public ByteOrder getByteOrder() { 
		
		return byteOrder; 
	}
	
	@MediaPropertySetter("ByteOrder")
	public void setByteOrder(
			Object byteOrder)
		throws NullPointerException { 
		
		if (byteOrder == null)
			throw new NullPointerException("Cannot set the byte order of a header using a null value.");

		if (byteOrder instanceof ByteOrder)
			this.byteOrder = (ByteOrder) byteOrder; 
		
		if (byteOrder instanceof Short)
			this.byteOrder = ByteOrder.getByteOrderFromAAFCode((Short) byteOrder);
	}
	
	public final static ByteOrder initializeByteOrder() {
		
		return ByteOrder.Big;
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0202, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Dictionaries",
			aliases = { "Dictionary", "HeaderDictionary" },
			typeName = "DictionaryStrongReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3B04,
			symbol = "Dictionaries")
	public Dictionary getDictionaries() { 
		
		return dictionaries;
	}
	
	@MediaPropertySetter("Dictionaries")
	public void setDictionaries(
			tv.amwa.maj.model.Dictionary dictionaries) 
		throws NullPointerException { 
		
		if (dictionaries == null)
			throw new NullPointerException("Cannot set the dictionaries for this header to a null value.");
		
		this.dictionaries = dictionaries;
	}
	
	public final static Dictionary initializeDictionaries() {
		
		return new DictionaryImpl();
	}
	
	@MediaProperty(uuid1 = 0x03010201, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ObjectModelVersion",
			typeName = "UInt32", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3B07,
			symbol = "ObjectModelVersion")
	public int getObjectModelVersion() {
		
		if (objectModelVersion == null)
			return OBJECTMODELVERSION_DEFAULT;
		else
			return objectModelVersion;
	}
	
	@MediaPropertySetter("ObjectModelVersion")
	public void setObjectModelVersion(
			Integer objectModelVersion) 
		throws IllegalArgumentException {
		
		if (objectModelVersion == null) {
			this.objectModelVersion = null;
			return;
		}
		
		if (objectModelVersion < 0)
			throw new IllegalArgumentException("Cannot set the object model version of this header to a negative value.");
		
		this.objectModelVersion = objectModelVersion;
	}
	
	@MediaProperty(uuid1 = 0x01020210, uuid2 = (short) 0x0203, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C },
			definedName = "ApplicationSchemes",
			aliases = { "ApplicationMetadataSchemes" , "ApplicationSchemesBatch" },
			typeName = "AUIDSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "ApplicationSchemes")
	public Set<AUID> getApplicationSchemes() 
		throws PropertyNotPresentException {

		if (applicationSchemes.size() == 0)
			throw new PropertyNotPresentException("The optional application schemes property is not present for this preface.");
		
		return new HashSet<AUID>(applicationSchemes);
	}
	
	@MediaSetAdd("ApplicationSchemes")
	public void addApplicationScheme(
			AUID applicationSchemeID)
		throws NullPointerException {
		
		if (applicationSchemeID == null)
			throw new NullPointerException("Cannot add an application metadata scheme using a null identifier.");
		
		applicationSchemes.add(applicationSchemeID.clone());
	}

	@MediaPropertyClear("ApplicationSchemes")
	public void clearApplicationSchemes() {
	
		applicationSchemes.clear();
	}

	@MediaPropertyCount("ApplicationScehemes")
	public int countApplicationSchemes() {
		
		return applicationSchemes.size();
	}

	@MediaPropertyContains("ApplicationSchemes")
	public boolean isApplicationSchemePresent(
			AUID applicationSchemeID)
		throws PropertyNotPresentException, 
			NullPointerException {

		if (applicationSchemeID == null)
			throw new NullPointerException("Cannot check for an application scheme ID using a null value.");
		if (applicationSchemes.size() == 0)
			throw new PropertyNotPresentException("The optional application schemes property is not present for this preface.");
		
		return applicationSchemes.contains(applicationSchemeID);
	}

	@MediaPropertyRemove("ApplicationSchemes")
	public void removeApplicationScheme(
			AUID applicationSchemeID)
		throws NullPointerException, 
			InvalidParameterException,
			PropertyNotPresentException {

		if (applicationSchemeID == null)
			throw new NullPointerException("Cannot remove an application metadata scheme using a null value.");
		if (applicationSchemes.size() == 0)
			throw new PropertyNotPresentException("The optional application schemes property is not present for this preface.");
		if (!applicationSchemes.contains(applicationSchemeID))
			throw new InvalidParameterException("The given application schemes identifier is not contained in the set of this preface.");
		
		applicationSchemes.remove(applicationSchemeID);
	}
	
	public boolean updateDictionaries() {
		
		boolean success = true;
		
		for ( Package checkPackage : getPackages() ) {
			
			success &= dictionaries.addDefinitions(checkPackage);
		}
		
		return success;
	}

	public Preface clone() {
		
		return (Preface) super.clone();
	}
	
//	private TimeStamp fileLastModified;
	
	public String getFileLastModifiedString() {
		
		return TimeStampImpl.toPersistentForm(fileLastModified);
	}
	
	public void setFileLastModifiedString(
			String fileLastModified) {
		
		this.fileLastModified = TimeStampImpl.fromPersistentForm(fileLastModified);
	}
	
//	private VersionType formatVersion;

	public String getFormatVersionString() {
		
		return VersionTypeImpl.toPersistentForm(formatVersion);
	}
	
	public void setFormatVersionString(
			String formatVersion) {
		
		this.formatVersion = VersionTypeImpl.fromPersistentForm(formatVersion);
	}
	
	//	private AUID operationalPattern = null;
	
	public String getOperationalPatternString() {
		
		return AUIDImpl.toPersistentForm(operationalPattern);
	}
	
	public void setOperationPatternString(
			String operationalPattern) {
		
		this.operationalPattern = AUIDImpl.fromPersistentForm(operationalPattern);
	}
	
//	private Set<AUID> essenceContainers =
//		Collections.synchronizedSet(new HashSet<AUID>());
	
	public Set<String> getEssenceContainersStringSet() {
		
		return auidSetToStringSet(essenceContainers);
	}
	
	public void setEssenceContainersStringSet(
			Set<String> essenceContainers) {
		
		this.essenceContainers = stringSetToAUIDSet(essenceContainers);
	}
	
//	private Set<AUID> descriptiveSchemes = 
//		Collections.synchronizedSet(new HashSet<AUID>());

	public Set<String> getDescriptiveSchemesStringSet() {
		
		return auidSetToStringSet(descriptiveSchemes);
	}
	
	public void setDescriptiveSchemesStringSet(
			Set<String> descriptiveSchemes) {
		
		this.descriptiveSchemes = stringSetToAUIDSet(descriptiveSchemes);
	}
	
	private final static Set<String> auidSetToStringSet(
			Set<AUID> auidSet) {
		
		if (auidSet == null) return null;
		Set<String> stringSet = new HashSet<String>(auidSet.size());
		for ( AUID auid : auidSet )
			stringSet.add(AUIDImpl.toPersistentForm(auid));
		return stringSet;
	}
	
	private final static Set<AUID> stringSetToAUIDSet(
			Set<String> stringSet) {
		
		if (stringSet == null) return null;
		
		Set<AUID> auidSet = Collections.synchronizedSet(new HashSet<AUID>(stringSet.size()));
		for ( String auid : stringSet )
			auidSet.add(AUIDImpl.fromPersistentForm(auid));
		return auidSet;
	}

	// Begin - Quantel extensions
	
	private Integer archiveDatabaseType = null;
	
    @MediaProperty(uuid1 = 0xd52527ee, uuid2 = (short) 0xba9d, uuid3 = (short) 0x4d2b,
        uuid4 = { (byte) 0x92, (byte) 0xbe, (byte) 0xe9, (byte) 0x3a, (byte) 0x2a, (byte) 0x07, (byte) 0x3d, (byte) 0x66 },
        definedName = "Archive database type",
        symbol = "Archive_database_type",
        aliases = { "Archive_database_type" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	
	public @Int32 int getArchiveDatabaseType()
		throws PropertyNotPresentException {
		
		if (archiveDatabaseType == null)
			throw new PropertyNotPresentException("The optional archive database type property is not present for this Quantel preface.");
		
		return archiveDatabaseType;
	}
	
	@MediaPropertySetter("Archive database type")
	public void setArchiveDatabaseType(
			@Int32 Integer archiveDatabaseType) {
		
		this.archiveDatabaseType = archiveDatabaseType;
	}
	
	// End = Quantel extensions
	
	//	private PackageID primaryPackageID = null;

//	public String getPrimaryPackageString() {
//		
//		return PackageIDImpl.toPersistentForm(primaryPackage);
//	}
//	
//	public void setPrimaryPackageString(
//			String primaryPackageID) {
//		
//		this.primaryPackage = PackageIDImpl.fromPersistentForm(primaryPackageID);
//	}
	
}
