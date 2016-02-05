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
 * $Log: IdentificationImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/11/18 10:43:02  vizigoth
 * Fixed import headings.
 *
 * Revision 1.2  2010/07/14 13:34:35  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
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
 * Revision 1.2  2008/01/14 21:12:31  vizigoth
 * Update due to refactoring of element names in the ProductReleaseType enumeration.
 *
 * Revision 1.1  2007/11/13 22:09:04  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.ProductReleaseType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.Identification;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.ProductVersion;
import tv.amwa.maj.record.TimeStamp;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.record.impl.ProductVersionImpl;
import tv.amwa.maj.record.impl.TimeStampImpl;

/** 
 * <p>Implements a representation of identity information about the application that created or 
 * modified a file.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#IdentificationStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#IdentificationStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Identification",
		  description = "The Identification class provides information about the application that created or modified the file.",
		  symbol = "Identification")
public class IdentificationImpl
	extends InterchangeObjectImpl
	implements Identification,
		Serializable,
		Cloneable {

	// TODO find somewhere better for these API specific definitions
	public final static String APICompanyName = "portability 4 media";
	public final static String APIProductName = "MAJ API";
	public final static ProductVersion APIProductVersion = new ProductVersionImpl(
			(short) 0, (short) 0, (short) 1, (short) 0, ProductReleaseType.PrivateBuild);
	public final static String APIProductVersionString = "0.0.1 development";
	public final static AUID APIProductID = 
		tv.amwa.maj.record.impl.AUIDImpl.parseFactory("urn:uuid:2234694c-39f5-11dc-8314-0800200c9a66");
	public final static ProductVersion APIToolkitVersion = new ProductVersionImpl(
			(short) 1, (short) 1, (short) 4, (short) 0, ProductReleaseType.Beta);
	// Include platform here?
	
	/** <p></p> */
	private static final long serialVersionUID = 7333971490704276839L;
	
	private String applicationSupplierName;
	private String applicationName;
	private ProductVersion applicationVersion = null;
	private String applicationVersionString;
	private AUID applicationProductID;
	private TimeStamp fileModificationDate;
	private ProductVersion toolkitVersion = null;
	private String applicationPlatform = null;
	private AUID generationID;

	public IdentificationImpl() { }

	/**
	 * <p>Creates and initializes a new identification object, which provides information about 
	 * the application that created or modified the current persistent unit. The mandatory date and 
	 * generation AUID properties of the identification will be created automatically.</p>
	 *
	 * @param companyName Name of the company or organization that created the application.
	 * @param productName Name of the application.
	 * @param productVersionString Version number of the application in string form.
	 * @param productID Unique identifier for the application.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and all
	 * arguments are required.
	 */
	public IdentificationImpl(
			@AAFString String companyName,
			@AAFString String productName,
			@AAFString String productVersionString,
			tv.amwa.maj.record.AUID productID) 
		throws NullPointerException {
		
		if (companyName == null)
			throw new NullPointerException("Cannot create an identification with a null company name.");
		if (productName == null)
			throw new NullPointerException("Cannot create an identification with a null application name.");
		if (productVersionString == null)
			throw new NullPointerException("Cannot create an identification with a null application version string.");
		if (productID == null)
			throw new NullPointerException("Cannot create an identification with a null application identifier (AUID).");
		
		this.applicationSupplierName = companyName;
		this.applicationName = productName;
		this.applicationVersionString = productVersionString;
		this.applicationProductID = productID.clone();
		this.fileModificationDate = new tv.amwa.maj.record.impl.TimeStampImpl();
		this.generationID = tv.amwa.maj.record.impl.AUIDImpl.timebasedAUID();

		this.applicationPlatform = "MAJ API (Java " + System.getProperty("java.version") + ")";
	}

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0201, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ApplicationSupplierName",
			aliases = { "CompanyName" },
			typeName = "UTF16String",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3C01,
			symbol = "ApplicationSupplierName")
	public String getApplicationSupplierName() {

		return applicationSupplierName;
	}

	@MediaPropertySetter("ApplicationSupplierName")
	public void setApplicationSupplierName(
			String applicationSupplierName) 
		throws NullPointerException {
		
		if (applicationSupplierName == null)
			throw new NullPointerException("Cannot set the company name of this identification to a null value.");
		
		this.applicationSupplierName = applicationSupplierName;
	}
	
	public final static String initializeApplicationSupplierName() {
		
		return "DefaultApplicationSupplierName";
	}

	@MediaProperty(uuid1 = 0x07020110, uuid2 = (short) 0x0203, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FileModificationDate",
			aliases = { "Date", "ModificationDate" },
			typeName = "TimeStamp",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3C06,
			symbol = "FileModificationDate")
	public TimeStamp getFileModificationDate() {

		return fileModificationDate.clone();
	}

	@MediaPropertySetter("FileModificationDate")
	public void setFileModificationDate(
			tv.amwa.maj.record.TimeStamp fileModificationDate) 
		throws NullPointerException {
		
		if (fileModificationDate == null)
			throw new NullPointerException("Cannot set the date of this identification to null.");
		
		this.fileModificationDate = fileModificationDate.clone();
	}
	
	public final static TimeStamp initializeFileModificationDate() {
		
		return new TimeStampImpl();
	}
	
	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "GenerationID",
			aliases = { "GenerationAUID" },
			typeName = "AUID",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3C09,
			symbol = "GenerationID")
	public AUID getGenerationID() {

		return generationID.clone();
	}

	@MediaPropertySetter("GenerationID")
	public void setGenerationID(
			tv.amwa.maj.record.AUID generationID) 
		throws NullPointerException {
		
		if (generationID == null)
			throw new NullPointerException("Cannot set the generatuin AUID of this identification to null.");
		
		this.generationID = generationID.clone();
	}

	public final static AUID initializeGenerationID() {
		
		return AUIDImpl.randomAUID();
	}
	
	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0601, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ApplicationPlatform",
			aliases = { "Platform", "IdentificationPlatform" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3C08,
			symbol = "ApplicationPlatform")
	public String getApplicationPlatform() 
		throws PropertyNotPresentException {

		if (applicationPlatform == null)
			throw new PropertyNotPresentException("The optional application platform property is not present for this identification.");
		
		return applicationPlatform;
	}

	@MediaPropertySetter("ApplicationPlatform")
	public void setApplicationPlatform(
			String applicationPlatform) {
		
		this.applicationPlatform = applicationPlatform;
	}

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0700, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ApplicationProductID",
			aliases = { "ProductID" },
			typeName = "AUID", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3C05,
			symbol = "ApplicationProductID")
	public AUID getApplicationProductID() {

		return applicationProductID.clone();
	}

	@MediaPropertySetter("ApplicationProductID")
	public void setApplicationProductID(
			AUID applicationProductID) 
		throws NullPointerException {
		
		if (applicationProductID == null)
			throw new NullPointerException("Cannot set the application product ID of this identification to null.");
		
		this.applicationProductID = applicationProductID.clone();
	}

	public final static AUID initializeApplicationProductID() {
		
		return AUIDImpl.randomAUID();
	}
	
	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0301, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ApplicationName",
			aliases = { "ProductName" },
			typeName = "UTF16String", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3C02,
			symbol = "ApplicationName")
	public String getApplicationName() {

		return applicationName;
	}

	@MediaPropertySetter("ApplicationName")
	public void setApplicationName(
			String applicationName) 
		throws NullPointerException {
		
		if (applicationName == null)
			throw new NullPointerException("Cannot set the application name of this identification to null.");
		
		this.applicationName = applicationName;
	}
	
	public final static String initializeApplicationName() {
		
		return "DefaultApplicationName";
	}
	
	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ApplicationVersion",
			aliases = { "ProductVersion" },
			typeName = "ProductVersionType", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3C03,
			symbol = "ApplicationVersion")
	public ProductVersion getApplicationVersion() 
		throws PropertyNotPresentException {
		
		if (applicationVersion == null)
			throw new PropertyNotPresentException("The optional application version property is not present in this indentification.");

		return applicationVersion.clone();
	}

	@MediaPropertySetter("ApplicationVersion")
	public void setApplicationVersion(
			tv.amwa.maj.record.ProductVersion applicationVersion) {

		if (applicationVersion == null) 
			this.applicationVersion = null;
		else
			this.applicationVersion = applicationVersion.clone();
	}

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0501, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ApplicationVersionString",
			aliases = { "ProductVersionString" },
			typeName = "UTF16String", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3C04,
			symbol = "ApplicationVersionString")
	public String getApplicationVersionString() {

		return applicationVersionString;
	}

	@MediaPropertySetter("ApplicationVersionString")
	public void setApplicationVersionString(
			String applicationVersionString) 
		throws NullPointerException {
		
		if (applicationVersionString == null)
			throw new NullPointerException("Cannot set the application version string of this identification to null.");
		
		this.applicationVersionString = applicationVersionString;
	}
	
	public final static String initializeApplicationVersionString() {
		
		return "DefaultVersionString";
	}
	
	/** 
	 * <p>Returns the reference implementation which created this 
	 * identification object.</p>
	 * 
	 * <p>Same as {@link #getRefImplVersion()} to use the AAF specification name.</p>
	 * 
	 * @see tv.amwa.maj.model.Identification#getRefImplVersion()
	 */
	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0a00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ToolkitVersion",
			typeName = "ProductVersionType", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3C07,
			symbol = "ToolkitVersion")
	public ProductVersion getToolkitVersion() 
		throws PropertyNotPresentException {
		
		if (toolkitVersion == null)
			throw new PropertyNotPresentException("The toolkit version is not present in this identificiation.");
		
		return toolkitVersion.clone();
	}
	
	@MediaPropertySetter("ToolkitVersion")
	public void setToolkitVersion(
			tv.amwa.maj.record.ProductVersion toolkitVersion) {
		
		if (toolkitVersion == null) {
			this.toolkitVersion = null;
			return;
		}
		
		this.toolkitVersion = toolkitVersion.clone();
	}
	
	public ProductVersion getRefImplVersion() 
		throws PropertyNotPresentException {

		return getToolkitVersion();
	}
	
	public Identification getGeneration() {
		
		return this;
	}
	
	public Identification clone() {
		
		return (Identification) super.clone();
	}
	
	//	private ProductVersion applicationVersion = null;
	
	// TODO consider making product versions embeddable in the JPA database
	public String getApplicationVersionPersist() {
		
		return ProductVersionImpl.toPersistentForm(applicationVersion);
	}
	
	public void setApplicationVersionPersist(
			String applicationVersion) {
		
		this.applicationVersion = ProductVersionImpl.fromPersistentForm(applicationVersion);
	}
	
	//	private AUID applicationProductID;
	
	public String getApplicationProductIDString() {
		
		return AUIDImpl.toPersistentForm(applicationProductID);
	}
	
	public void setApplicationProductIDString(
			String applicationProductID) {
		
		this.applicationProductID = AUIDImpl.fromPersistentForm(applicationProductID);
	}
	
	//	private TimeStamp fileModificationDate;

	public String getFileModificationDateString() {
		
		return TimeStampImpl.toPersistentForm(fileModificationDate);
	}
	
	public void setFileModificationDateString(
			String fileModificationDate) {
		
		this.fileModificationDate = TimeStampImpl.fromPersistentForm(fileModificationDate);
	}
	
	//	private ProductVersion toolkitVersion = null;
	
	public String getToolkitVersionString() {
		
		return ProductVersionImpl.toPersistentForm(toolkitVersion);
	}
	
	public void setToolkitVersionString(
			String toolkitVersion) {
		
		this.toolkitVersion = ProductVersionImpl.fromPersistentForm(toolkitVersion);
	}
	
	//	private AUID generationID;
	public String getGenerationIDString() {
		
		return AUIDImpl.toPersistentForm(generationID);
	}
	
	public void setGenerationIDString(
			String generationID) {
		
		this.generationID = AUIDImpl.fromPersistentForm(generationID);
	}

}
