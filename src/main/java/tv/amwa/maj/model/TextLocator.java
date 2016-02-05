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
 * $Log: TextLocator.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/02/08 11:27:23  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:09:02  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.misctype.AAFString;


/**
 * <p>Specifies information to help find a file containing the essence or to 
 * help find the physical media. A text locator is not intended for applications 
 * to use without user intervention.</p>
 * 
 *
 * 
 */

public interface TextLocator 
	extends Locator {

	/**
	 * <p>Sets the location name of the text locator, which provides a text string containing 
	 * information to help find the file containing the essence or the physical media.</p>
	 * 
	 * @param name Name property of the text locator.
	 * 
	 * @throws NullPointerException The given name is <code>null</code>.
	 * 
	 * @see Locator#setPath(String)
	 */
	public void setLocationName(
			@AAFString String name) 
		throws NullPointerException;

	/**
	 * <p>Returns the location name of the text locator, which provides a text string 
	 * containing information to help find the file containing the essence 
	 * or the physical media.</p>
	 * 
	 * @return Name property of the text locator.
	 * 
	 * @see Locator#getPath()
	 */
	public @AAFString String getLocationName();

	/**
	 * <p>Create a cloned copy of this text locator.</p>
	 *
	 * @return Cloned copy of this text locator.
	 */
	public TextLocator clone();
}
