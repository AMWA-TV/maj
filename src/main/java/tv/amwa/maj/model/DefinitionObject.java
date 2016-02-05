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
 * $Log: DefinitionObject.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/01/27 11:07:25  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:28:27  vizigoth
 * Minor comment updates.
 *
 * Revision 1.1  2007/11/13 22:08:30  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;


/**
 * <p>Specifies a definition to be referenced.</p>
 * 
 *
 * 
 * @see Dictionary
 *
 */
public abstract interface DefinitionObject 
	extends InterchangeObject,
		WeakReferenceTarget {

	/**
	 * <p>Sets the display name of this definition.</p>
	 * 
	 * @param name The display name of the item being defined.
	 * 
	 * @throws NullPointerException The given display name is <code>null</code>.
	 */
	public void setName(
			@AAFString String name) 
		throws NullPointerException;

	/** 
	 * <p>Returns the display name of this definition.</p>
	 * 
	 * @return name The display name of the item being defined.
	 */
	public @AAFString String getName();

	/**
	 * <p>Sets the description of this definition, which provides an explanation 
	 * of the use of the item being defined. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param description An explanation of the use of the item being defined.
	 */
	public void setDescription(
			@AAFString String description);

	/**
	 * <p>Returns the description of this definition, which provides an explanation 
	 * of the use of the item being defined. This is an optional property.</p>
	 * 
	 * @return An explanation of the use of the item being defined.
	 * 
	 * @throws PropertyNotPresentException The optional property is not set
	 * for this definition.
	 */
	public @AAFString String getDescription()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the identifier of this definition, which specifies the unique 
	 * identifier for the item being defined.</p>
	 * 
	 * @return The unique identifier for the item being defined.
	 */
	public AUID getAUID();
	
	/**
	 * <p>Create a cloned copy of this definition object.</p>
	 *
	 * @return Cloned copy of this definition object.
	 */
	public DefinitionObject clone();
}


