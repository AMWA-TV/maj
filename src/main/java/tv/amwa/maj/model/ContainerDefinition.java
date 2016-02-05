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
 * $Log: ContainerDefinition.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/27 11:07:32  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:10  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.Bool;


/**
 * <p>Specifies a definition for the mechanism used to store {@linkplain EssenceData essence data}. A container can 
 * be either a kind of file, such as an {@linkplain AAFFile AAF file}, or it can be another mechanism for storing essence 
 * data.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.constant.ContainerConstant
 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
 * @see Dictionary#getContainerDefinitions()
 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionStrongReferenceSet
 */

public interface ContainerDefinition 
	extends DefinitionObject {

	/** 
	 * <p>Returns whether the essence of the defined container is identified, which specifies when <code>true</code> that 
	 * the container uses the {@linkplain tv.amwa.maj.record.PackageID package id} to identify the 
	 * {@linkplain EssenceData essence data} and that the container may contain multiple essence 
	 * data objects, each identified by a {@linkplain tv.amwa.maj.record.PackageID package id}. This is an optional
	 * property.</p>
	 * 
	 * @return Is the essence identified?
	 * 
	 * @throws PropertyNotPresentException The optional essence is identified property is not present
	 * in the container definition.
	 * 
	 * @deprecated Use {@link #getEssenceIsIdentified()} instead.
	 */
	@Deprecated public @Bool boolean essenceIsIdentified()
		throws PropertyNotPresentException;

	/** 
	 * <p>Returns whether the essence of the defined container is identified, which specifies when <code>true</code> that 
	 * the container uses the {@linkplain tv.amwa.maj.record.PackageID package id} to identify the 
	 * {@linkplain EssenceData essence data} and that the container may contain multiple essence 
	 * data objects, each identified by a {@linkplain tv.amwa.maj.record.PackageID package id}. This is an
	 * optional property.</p>
	 * 
	 * @return Is the essence identified?
	 * 
	 * @throws PropertyNotPresentException The optional essence is identified property is not present
	 * in the container definition.
	 */
	public @Bool boolean getEssenceIsIdentified()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets whether the essence of the defined container is identified, which specifies when <code>true</code> that 
	 * the container uses the {@linkplain tv.amwa.maj.record.PackageID package id} to identify the 
	 * {@linkplain EssenceData essence data} and that the container may contain multiple essence 
	 * data objects, each identified by a {@linkplain tv.amwa.maj.record.PackageID package id}. To omit
	 * this optional property, set its value to <code>null</code>.</p>
	 * 
	 * @param essenceIsIdentified Is the essence identified?
	 */
	public void setEssenceIsIdentified(
			@Bool Boolean essenceIsIdentified);
	
	/**
	 * <p>Create a cloned copy of this container definition.</p>
	 *
	 * @return Cloned copy of this container definition.
	 */
	public ContainerDefinition clone();
	
}
