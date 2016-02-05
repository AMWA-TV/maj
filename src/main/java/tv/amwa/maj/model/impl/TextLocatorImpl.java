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
 * $Log: TextLocatorImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
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
 * Revision 1.1  2007/11/13 22:09:52  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.TextLocator;

/** 
 * <p>Implements a representation of information to help find a file containing the essence or to 
 * help find the physical media. A text locator is not intended for applications 
 * to use without user intervention.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3300,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TextLocator",
		  description = "The TextLocator class provides information to help find a file containing the essence or to help find the physical media.",
		  symbol = "TextLocator")
public class TextLocatorImpl
	extends 
		LocatorImpl
	implements 
		TextLocator,
		Serializable,
		XMLSerializable,
		Cloneable {

	// No private fields ... uses superclass "path" 

	/** <p></p> */
	private static final long serialVersionUID = 7015708619944418258L;
	
	public TextLocatorImpl() { }

	/**
	 * <p>Creates and initializes a new text locator, which provides information to help find 
	 * a file containing the essence or to help find the physical media.</p>
	 *
	 * @param name Text string containing information to help find the file containing the essence 
	 * or the physical media.
	 * 
	 * @throws NullPointerException Location of the media is <code>null</code>.
	 */
	public TextLocatorImpl(
			@AAFString String name)
		throws NullPointerException {
		
		setLocationName(name);
	}
	
	@MediaProperty(uuid1 = 0x01040102, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "LocationName",
			typeName = "UTF16String",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4101,
			symbol = "LocationName",
			aliases = { "TextLocatorName" , "Name"})
	public String getLocationName() {

		return getPath();
	}

	@MediaPropertySetter("LocationName")
	public void setLocationName(
			String name)
			throws NullPointerException {

		if (name == null)
			throw new NullPointerException("Cannot set the name of a text locator with a null value.");
		
		setPath(name);
	}

	public final static String initializeLocationName() {
		
		return "DefaultLocationName";
	}
	
	@Override
	public TextLocator clone() {
		
		return (TextLocator) super.clone();
	}
	
	public String getLocationNamePersist() {
		
		return getPath();
	}
	
	public void setLocationNamePersist(
			String urlString) {
		
		if (urlString != null)
			setPath(urlString);
	}
	
}
