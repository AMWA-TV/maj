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
 * $Log: Locator.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/01/27 11:07:33  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:45  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.misctype.AAFString;

// TODO Consider putting specific methods for access to dediciated properties in the sub-interfaces

/**
 * <p>Specifies information to help find a file that contains the essence or to help 
 * find the physical media.</p>
 * 
 *
 *
 * @see EssenceDescriptor#getLocators()
 * @see tv.amwa.maj.industry.TypeDefinitions#LocatorStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#LocatorStrongReferenceVector
 */

public abstract interface Locator 
	extends InterchangeObject {

	/**
	 * <p>Sets the path string property of this locator, which provides access to 
	 * the <em>URLString</em> property of a {@linkplain NetworkLocator network locator} and
	 * the <em>name</em> property of a {@linkplain TextLocator text locator}.</p>
	 * 
	 * @param path Path string property of this locator.
	 * 
	 * @throws NullPointerException The given path is <code>null</code>.
	 */
	public void setPath(
			@AAFString String path) 
		throws NullPointerException;

	/**
	 * <p>Returns the path string property of this locator, which provides access to 
	 * the <em>URL</em> property of a {@linkplain NetworkLocator network locator} and
	 * the <em>name</em> property of a {@linkplain TextLocator text locator}.</p>
	 * 
	 * @return Path string property of this locator.
	 * 
	 * @see NetworkLocator#getURL()
	 */
	public @AAFString String getPath();
	
	/**
	 * <p>Create a cloned copy of this locator.</p>
	 *
	 * @return Cloned copy of this locator.
	 */
	public Locator clone();
}
