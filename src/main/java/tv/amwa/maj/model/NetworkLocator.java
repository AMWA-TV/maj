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
 * $Log: NetworkLocator.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2008/02/08 11:27:20  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2008/01/27 11:07:35  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:38  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.misctype.AAFString;

/**
 * <p>Specifies information to help find a file containing essence, using a 
 * uniform resource identifier (URI). The current URI specification is 
 * <a href="http://tools.ietf.org/html/rfc3986">RFC&nbsp;3986</a>.</p>
 * 
 *
 *
 * @see TextLocator
 * @see Locator#getPath()
 * @see EssenceDescriptor#getLocators()
 * @see PluginDefinition#getManufacturerInfo()
 * @see tv.amwa.maj.industry.TypeDefinitions#NetworkLocatorStrongReference
 */
public interface NetworkLocator 
	extends Locator {

	/**
	 * <p>Returns an absolute uniform resource locator (URL) complying with 
	 * <a href="http://www.faqs.org/rfcs/rfc1738.html">RFC 1738</a> 
	 * or relative Uniform Resource Identifier (URI) complying with 
	 * <a href="http://www.faqs.org/rfcs/rfc2396.html">RFC 2396</a> for a file containing the 
	 * essence. If it is a relative URI, the base URI is determined from the URI of the AAF 
	 * file itself.<p>
	 * 
	 * <p>Note that a valid URL or URI uses a constrained character set and uses the '<code>/</code>'
	 * character as the path separator.</p>
	 * 
	 * @return URL property of this locator.
	 * 
	 * @see Locator#getPath()
	 */
	public @AAFString String getURL();
	
	/**
	 * <p>Sets an absolute uniform resource locator (URL) complying with 
	 * <a href="http://www.faqs.org/rfcs/rfc1738.html">RFC 1738</a> 
	 * or relative Uniform Resource Identifier (URI) complying with 
	 * <a href="http://www.faqs.org/rfcs/rfc2396.html">RFC 2396</a> for a file containing the 
	 * essence. If it is a relative URI, the base URI is determined from the URI of the AAF 
	 * file itself.<p>
	 * 
	 * <p>Note that a valid URL or URI uses a constrained character set and uses the '<code>/</code>'
	 * character as the path separator.</p>
	 * 
	 * @param url URL property of this locator.
	 * 
	 * @throws NullPointerException The given locator is <code>null</code> and the property is required.
	 * @throws IllegalArgumentException The given locator did not parse as a valid URL.
	 */
	public void setURL(
			@AAFString String url)
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Create a cloned copy of this network locator.</p>
	 *
	 * @return Cloned copy of this network locator.
	 */
	public NetworkLocator clone();
	
}