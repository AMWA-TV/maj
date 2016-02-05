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
 * $Log: DataDefinition.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/11/18 10:44:35  vizigoth
 * Fixed import headings.
 *
 * Revision 1.5  2010/07/14 13:34:38  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2008/02/28 12:50:32  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2008/01/27 11:07:33  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:54  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.misctype.Bool;

/**
 * <p>Specifies a definition for the kind of data that can be stored in a {@linkplain Component component}.</p>
 * 
 * <p>Section&nbsp;22.1 of the <a href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF object specification
 * v1.1</a> lists the built-in data definitions of the AAF. These can be retrieved by name via the
 * {@link tv.amwa.maj.industry.Warehouse#lookup(Class, String)} method of the MAJ warehouse.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.constant.DataDefinitionConstant
 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
 * @see Dictionary#getDataDefinitions()
 * @see Component#getComponentDataDefinition()
 * @see OperationDefinition#getOperationDataDefinition()
 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReferenceVector
 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionStrongReferenceSet
 */

public interface DataDefinition 
	extends DefinitionObject {

	/**
	 * <p>Returns <code>true</code> is the data definition is a picture only;
	 * otherwise returns <code>false</code>.</p>
	 * 
	 * @return Does the data definition relate to a picture?
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Picture
	 */
	public @Bool boolean isPictureKind();

	/**
	 * <p>Returns <code>true</code> in the data definition is a matte, in 
	 * other words a stream of essence that contains an image of alpha 
	 * values; otherwise <code>false</code>.</p>
	 * 
	 * @return Is the data definition related to a matte?
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Matte
	 */
	public @Bool boolean isMatteKind();

	/**
	 * <p>Returns <code>true</code> if the data definition is for a picture
	 * with a matte; otherwise returns <code>false</code>. Pictures like this 
	 * have both picture data alpha values.</p>
	 * 
	 * @return Does the data definition represent data with both a picture
	 * and a matte?
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#PictureWithMatte
	 */
	public @Bool boolean isPictureWithMatteKind();

	/**
	 * <p>Returns <code>true</code> is a data definition corresponds to
	 * sound data; otherwise returns false.</p>
	 * 
	 * @return Does the data definition represent sound data?
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Sound
	 */
	public @Bool boolean isSoundKind();

	// TODO build a table of data def conversions and check with existing API
	
	/**
	 * <p>Returns <code>true</code> if the data represented by this data 
	 * definition can be converted to the data definition given as an argument. 
	 * Matching takes place passed on the {@linkplain DefinitionObject#getName() name 
	 * property} of the data definition.</p>
	 * 
	 * @param dataDefinition Data definition to compare this data definition with.
	 * @return Can data associated with this data definition be converted 
	 * into data associated by the given data definition?
	 * 
	 * @throws NullPointerException The given data definition is <code>null</code>.
	 */
	public @Bool boolean doesDataDefConvertTo(
			DataDefinition dataDefinition) 
		throws  NullPointerException;

	/**
	 * <p>Returns <code>true</code> is this data definition matches the
	 * data definition provided in the parameter; otherwise <code>false</code>.
	 * Matching takes place using the name property of the definition.</p>
	 * 
	 * @param dataDefinition Data definition to compare with.
	 * @return Does this data definition match the given one?
	 * 
	 * @throws NullPointerException The given data definition is <code>null</code>.
	 */
	public @Bool boolean isDataDefOf(
			DataDefinition dataDefinition) 
		throws NullPointerException;

	/**
	 * <p>Returns <code>true</code> if data represented by this type of
	 * data definition can be created by converting from the type
	 * represented by the given data definition. Matching takes place based
	 * on the {@link DefinitionObject#getName() name property} of the given 
	 * data definition.</p>
	 * 
	 * @param dataDefinition Data definition of the type of data to be converted from.
	 * @return Can data of the type represented by the given data definition
	 * be converted to data of the type represented by this data definition?
	 * 
	 * @throws NullPointerException Argument is null.
	 */
	public @Bool boolean doesDataDefConvertFrom(
			DataDefinition dataDefinition) 
		throws NullPointerException;

	/**
	 * <p>Returns <code>true</code> if the data definition represents 
	 * data that is a stream of film edge code values; otherwise 
	 * <code>false</code>.</p>
	 * 
	 * @return Does this data definition represent data that is a stream
	 * of film edgecode values?
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Edgecode
	 */
	public @Bool boolean isEdgecodeKind();

	/**
	 * <p>Returns <code>true</code> if the data definition represents 
	 * data that is a stream of tape timecode values; otherwise
	 * <code>false</code>.</p>
	 * 
	 * @return Does the data definition represent data that is a stream
	 * of tape timecode values?
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Timecode
	 */
	public @Bool boolean isTimecodeKind();

	/**
	 * <p>Returns <code>true</code> if the data definition represents
	 * Auxiliary data;  otherwise <code>false</code>.</p>
	 * 
	 * @return Does the data definition represent data of an auxiliary
	 * kind?
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Auxiliary
	 */
	public @Bool boolean isAuxilaryKind();

	/**
	 * <p>Returns <code>true</code> if the data definition represents
	 * data that is descriptive metadata; otherwise <code>false</code>.</p>
	 * 
	 * @return Does the data definition represent data that is descriptive
	 * metadata?
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#DescriptiveMetadata
	 */
	public @Bool boolean isDescriptiveMetadataKind();
	
	/**
	 * <p>Create a cloned copy of this data definition.</p>
	 *
	 * @return Cloned copy of this data definition.
	 */
	public DataDefinition clone();
}
