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
 * $Log: AuxiliaryDescriptorImpl.java,v $
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
 * Revision 1.1  2007/11/13 22:09:50  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.AuxiliaryDescriptor;


// TODO consider mime type and char set methods to link to Java core APIs

/** 
 * <p>Implements the description of an auxiliary file essence source. The type
 * of data in the essence is given by a mime type. See the 
 * <a href="http://www.iana.org/assignments/media-types/index.html">IANA register of media
 * types</a>.</p>
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x4e00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "AuxiliaryDescriptor",
		  description = "The AuxiliaryDescriptor class specifies describes an auxiliary file essence source.",
		  symbol = "AuxiliaryDescriptor")
public class AuxiliaryDescriptorImpl
	extends PhysicalDescriptorImpl
	implements AuxiliaryDescriptor,
		Serializable,
		XMLSerializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 6166632371007055060L;

	private String charSet = null;
	private String mimeType;

	public AuxiliaryDescriptorImpl() { }

	/**
	 * <p>Creates and initializes a new auxilary descriptor with the given mime type.</p>
	 *
 	 * @param mimeType Registered mimetype of the data in the auxiliary file, according to 
 	 * <a href="http://www.ietf.org/rfc/rfc2046.txt">RFC 2046 (MIME Media Types)</a> and 
 	 * <a href="http://www.ietf.org/rfc/rfc2048.txt">RFC 2048 (MIME Registration Procedures)</a>.
	 * 
	 * @throws NullPointerException Argument is null.
	 */
	public AuxiliaryDescriptorImpl(
			@AAFString String mimeType)
		throws NullPointerException {
		
		if (mimeType == null)
			throw new NullPointerException("Cannot create an auxiliary descriptor using a null mime type value.");
		
		setMIMEType(mimeType);
	}
	
	@MediaProperty(uuid1 = 0x04090300, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "CharSet",
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4E12,
			symbol = "CharSet")
	public String getCharSet() 
		throws PropertyNotPresentException {

		if (charSet == null)
			throw new PropertyNotPresentException("The optional character set property is not present in this auxiliary descriptor.");

		return charSet;
	}

	@MediaPropertySetter("CharSet")
	public void setCharSet(
			String charSet) {

		this.charSet = charSet;
	}
	
	@MediaProperty(uuid1 = 0x04090201, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "MIMEType",
			aliases = { "MimeType" },
			typeName = "UTF16String",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4E11,
			symbol = "MIMEType")
	public String getMIMEType() {

		return mimeType;
	}

	@MediaPropertySetter("MIMEType")
	public void setMIMEType(
			String mimeType)
		throws NullPointerException {

		if (mimeType == null)
			throw new NullPointerException("Cannot set the value of the mime type of this auxiliary descriptor using a null value.");
		
		this.mimeType = mimeType;
	}
	
	public final static String initializeMIMEType() {
		
		return "application/octet-stream";
	}
	
	public AuxiliaryDescriptor clone() {
		
		return (AuxiliaryDescriptor) super.clone();
	}
}
