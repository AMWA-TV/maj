/* 
 **********************************************************************
 *
 * $Id: TextLocator.java,v 1.3 2011/02/14 22:32:49 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
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
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
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
