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
 * $Log: LocatorImpl.java,v $
 * Revision 1.3  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:50  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import org.w3c.dom.Node;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.Locator;


/** 
 * <p>Implements the representation of information to help find a file that contains the essence or to help 
 * find the physical media.</p>
 *
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#LocatorStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#LocatorStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = (short) 0x0101, uuid3 = (short) 0x3100,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Locator",
		  description = "The Locator class provides information to help find a file that contains the essence or to help find the physical media.",
		  symbol = "Locator",
		  isConcrete = false)
public class LocatorImpl
	extends 
		InterchangeObjectImpl
	implements 
		Locator,
		tv.amwa.maj.extensions.quantel.QLocator,
		XMLSerializable,
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3305760419864000490L;
	
	private String path;

	public String getPath() {
		
		return path;
	}

	public void setPath(
			String path) 
		throws NullPointerException {
		
		if (path == null)
			throw new NullPointerException("Cannot set the path for a locator with a null value.");
		
		this.path = path;
	}
	
	// Begin - Quantel extensions
	
	private Integer tapeOffset = null;
	private Integer numberOfFiles = null;
	
    @MediaProperty(uuid1 = 0xad2bc3a7, uuid2 = (short) 0x478b, uuid3 = (short) 0x4589,
        uuid4 = { (byte) 0xb3, (byte) 0x92, (byte) 0x6b, (byte) 0xb8, (byte) 0x20, (byte) 0x8c, (byte) 0x45, (byte) 0x28 },
        definedName = "Tape offset",
        symbol = "Tape_offset",
        aliases = { "Tape_offset" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getTapeOffset()
		throws PropertyNotPresentException {
		
		if (tapeOffset == null)
			throw new PropertyNotPresentException("The optional tape offset property is not present for this Quantel locator.");
		
		return tapeOffset;
	}
	
	@MediaPropertySetter("Tape offset")
	public void setTapeOffset(
			@Int32 Integer tapeOffset) {
		
		this.tapeOffset = tapeOffset;
	}
	
  @MediaProperty(uuid1 = 0x1a8c2bae, uuid2 = (short) 0xd052, uuid3 = (short) 0x4c67,
        uuid4 = { (byte) 0xa3, (byte) 0x34, (byte) 0x1d, (byte) 0xfd, (byte) 0x1f, (byte) 0x36, (byte) 0x55, (byte) 0x8c },
        definedName = "Number of files",
        symbol = "Number_of_files",
        aliases = { "Number_of_files" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	
	public @Int32 int getNumberOfFiles()
		throws PropertyNotPresentException {
		
		if (numberOfFiles == null) 
			throw new PropertyNotPresentException("The optional number of files property is not present for this Quantel locator.");
		
		return numberOfFiles;
	}
	
	@MediaPropertySetter("Number of files")
	public void setNumberOfFiles(
			@Int32 Integer numberOfFiles) {
		
		this.numberOfFiles = numberOfFiles;
	}

	// End = Quantel extensions
	
	@Override
	public Locator clone() {
		
		return (Locator) super.clone();
	}
		
	public void appendXMLChildren(
			Node parent) {

	}
	
	public String getComment() {
		
		return "local locator persistent id: " + getPersistentID();
	}
}

// Regexp for schema conversion : //  <PropertyDef.*\n.*urn:uuid:(.{8})-(.{4})-(.{4})-(.{2})(.{2})-(.{2})(.{2})(.{2})(.{2})(.{2})(.{2}).*$
// @MediaProperty(uuid1 = 0x$1, uuid2 = (short) 0x$2, uuid3 = (short) 0x$3,\n        uuid4 = { (byte) 0x$4, (byte) 0x$5, (byte) 0x$6, (byte) 0x$7, (byte) 0x$8, (byte) 0x$9, (byte) 0x$10, (byte) 0x$11 },
// //  <Symbol>(.*)</Symbol>\n//  <Name>(.*)</Name>\n//  <Type>(.*)</Type>.*\n.*\n.*\n.*\n.*$
//         definedName = "$2",\n        symbol = "$1",\n        aliases = { "$1" },\n        typeName = "$3",\n        optional = true,\n        uniqueIdentifier = false,\n        pid = 0,\n        prefix = QConstants.XML_PREFIX,\n        namespace = QConstants.XML_NAMESPACE)
