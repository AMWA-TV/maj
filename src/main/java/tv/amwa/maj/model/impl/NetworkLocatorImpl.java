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
 * $Log: NetworkLocatorImpl.java,v $
 * Revision 1.4  2011/07/27 17:33:23  vizigoth
 * Fixed imports to clear warnings.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/07/14 13:34:35  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
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
 * Revision 1.1  2007/11/13 22:09:13  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.NetworkLocator;


/** 
 * <p>Implements a representation of information to help find a file containing essence, using a 
 * uniform resource identifier (URI). The current URI specification is 
 * <a href="http://tools.ietf.org/html/rfc3986">RFC&nbsp;3986</a>.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3200,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "NetworkLocator",
		  description = "The NetworkLocator class provides information to help find a file containing essence.",
		  symbol = "NetworkLocator")
public class NetworkLocatorImpl
	extends 
		LocatorImpl
	implements 
		NetworkLocator,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -991161509805417107L;

	// No private fields ... uses superclass "path"
	
	public NetworkLocatorImpl() { }

	/**
	 * <p>Creates and initializes a new network locator, which provides information to help find a 
	 * file containing essence.</p>
	 *
	 * @param urlString Absolute Uniform Resource Locator (URL) complying with 
	 * <a href="http://www.ietf.org/rfc/rfc2046.txt">RFC 1738</a> or 
	 * relative Uniform Resource Identifier (URI) complying with 
	 * <a href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a> for file containing the 
	 * essence. If it is a relative URI, the base URI is determined from the URI of the AAF persistent
	 * unit itself.
	 * 
	 * @throws NullPointerException Location URL is <code>null</code>.
	 * @throws IllegalArgumentException The given URI is not syntactically valid.
	 */
	public NetworkLocatorImpl(
			@AAFString String urlString)
		throws NullPointerException,
			IllegalArgumentException {
	
		setURL(urlString);
	}
	
	@MediaProperty(uuid1 = 0x01020101, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "URL",
			aliases = { "URLString" },
			typeName = "UTF16String",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4001,
			symbol = "URL")
	public String getURL() {
		
		return getPath();
	}

	@MediaPropertySetter("URL")
	public void setURL(
			String urlString)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (urlString == null)
			throw new NullPointerException("Cannot create a network locator from a null URL.");
		
		// FIXME turn me into an informational warning
		try {
			@SuppressWarnings("unused")
			URI testURI = new URI(urlString);
		}
		catch (URISyntaxException use) {
			// throw new IllegalArgumentException("Syntax error in URI value \"" + urlString + "\".");
		}
		setPath(urlString);
	}

	public final static String initializeURL() {
		
		return "http://www.amwa.tv/";
	}
	
	public NetworkLocator clone() {
		
		return (NetworkLocator) super.clone();
	}

	public String getURLPersist() {
		
		return getPath();
	}
	
	public void setURLPersist(
			String urlString) {
		
		if (urlString != null)
			setPath(urlString);
	}

	
}
