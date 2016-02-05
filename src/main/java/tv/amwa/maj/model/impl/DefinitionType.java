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
 * $Log: DefinitionType.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:08  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import tv.amwa.maj.exception.PropertyNotPresentException;

/**
 * <p>Interface supported by all {@linkplain tv.amwa.maj.model.DefinitionObject definition objects}
 * and {@linkplain tv.amwa.maj.meta.MetaDefinition meta definition objects}. These kinds of objects
 * are identified by a {@linkplain tv.amwa.maj.record.AUID AUID}, have a name and an optional
 * description.</p>
 * 
 *
 *
 */
public interface DefinitionType {

	/**
	 * <p>Returns the identification of the definition. This is normally a registered SMPTE universal
	 * label.</p>
	 * 
	 * @return Identification of the definition.
	 */
	public tv.amwa.maj.record.AUID getAUID();
	
	/**
	 * <p>Returns the name of the definition.</p>
	 * 
	 * @return Name of the definition.
	 */
	public String getName();
	
	/**
	 * <p>Returns the optional description of the definition.</p>
	 * 
	 * @return Description of the definition.
	 * @throws PropertyNotPresentException The optional name property is not present for the
	 * definition.
	 */
	public String getDescription()
		throws PropertyNotPresentException;
	
}
