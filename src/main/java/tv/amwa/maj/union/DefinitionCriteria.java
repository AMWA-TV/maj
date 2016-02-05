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
 * $Log: DefinitionCriteria.java,v $
 * Revision 1.4  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2008/01/14 16:07:34  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:11  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;


/** 
 * <p>Specifies a criteria for matching a {@linkplain tv.amwa.maj.model.DefinitionObject definition} as 
 * determined by class, kind, name etc..</p>
 * 
 * @see DefinitionCriteriaType
 * 
 *
 */

public abstract interface DefinitionCriteria {

	/**
	 * <p>Returns the type of definition criteria, which corresponds to the type of value used to 
	 * define the criteria.</p>
	 *
	 * @return Definition criteria type.
	 */
	public DefinitionCriteriaType getDefinitionType();

}
