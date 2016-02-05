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
 * $Log: DefinitionCriteriaByClass.java,v $
 * Revision 1.4  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2008/01/14 16:07:27  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:09  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.record.AUID;

/**
 * <p>Specifies a criteria for matching a {@linkplain tv.amwa.maj.model.DefinitionObject definition} determined 
 * by reference to the unique identifier of a {@linkplain tv.amwa.maj.meta.ClassDefinition class definition}.</p>
 * 
 * @see tv.amwa.maj.meta.ClassDefinition
 * @see tv.amwa.maj.meta.MetaDefinition#getAUID()
 * 
 *
 */
public interface DefinitionCriteriaByClass extends DefinitionCriteria {

	/**
	 * <p>Returns the class identifier defining this definition criteria.</p>
	 *
	 * @return Class identifier defining this definition criteria.
	 */
	public AUID getClassId();

	/**
	 * <p>Sets the class identifier defining this definition criteria.</p>
	 *
	 * @param classId Class identifier defining this definition criteria.
	 * 
	 * @throws NullPointerException The class identifier value is <code>null</code>.
	 */
	public void setClassId(
			AUID classId)
		throws NullPointerException;
}
