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
 * $Log: DefinitionCriteriaByName.java,v $
 * Revision 1.3  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:15  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

/**
 * <p>Criteria for matching a {@linkplain tv.amwa.maj.model.DefinitionObject definition} by its name.</p>
 * 
 * @see tv.amwa.maj.model.DefinitionObject#getName()
 * 
 *
 */
public interface DefinitionCriteriaByName 
	extends DefinitionCriteria {

	/**
	 * <p>Returns the name defining this definition criteria.</p>
	 *
	 * @return Name defining this definition criteria.
	 */
	public String getName();

	/**
	 * <p>Sets the name defining this definition criteria.</p>
	 *
	 * @param name Name defining this definition criteria.
	 * 
	 * @throws NullPointerException The definition name is <code>null</code>.
	 */
	public void setName(
			String name) 
		throws NullPointerException;
}
