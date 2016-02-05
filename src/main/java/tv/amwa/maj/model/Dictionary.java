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
 * $Log: Dictionary.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.8  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.7  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.6  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.5  2008/02/28 12:50:34  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.4  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.3  2008/01/27 11:07:21  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 13:04:50  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:28  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.Set;

import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.MetaDictionary;
import tv.amwa.maj.record.AUID;



/**
 * <p>Specifies a container for {@linkplain DefinitionObject definitions}. Every {@linkplain AAFFile AAF file} 
 * has a dictionary within its {@linkplain Preface preface} and contains a representation of the 
 * {@linkplain MetaDictionary meta dictionary}.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.meta.MetaDictionary
 * @see DefinitionObject
 * @see tv.amwa.maj.meta.MetaDefinition
 * @see Preface#getDictionaries()
 * @see tv.amwa.maj.industry.TypeDefinitions#DictionaryStrongReference
 */ 
public interface Dictionary 
	extends InterchangeObject {

	/**
	 * <p>Add a data definition to the those contained in this dictionary.</p>
	 * 
	 * @param dataDefinition Data definition to add.
	 * 
	 * @throws NullPointerException The given data definition is <code>null</code>.
	 * @throws InvalidParameterException A data definition with the
	 * same identifier is already contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
	 */
	public void registerDataDefinition(
			DataDefinition dataDefinition) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Return the data definition with the given identifier that is contained in this dictionary.</p>
	 * 
	 * @param dataDefinitionId Unique identifier for a data definition contained in this dictionary.
	 * @return Data definition contained in this dictionary with the given identifier.
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * @throws InvalidParameterException A data definition with the given id could not
	 * be found in this dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReference
	 */
	public DataDefinition lookupDataDefinition(
			AUID dataDefinitionId) 
		throws NullPointerException,
			InvalidParameterException;

	/** 
	 * <p>Returns the set of data definitions contained in this dictionary. This is an
	 * optional property.</p>
	 * 
	 * @return Shallow copy of the set of data definitions contained in this dictionary.
	 * 
	 * @throws PropertyNotPresentException No data definitions are present in this
	 * dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionStrongReferenceSet
	 */
	public Set<? extends DataDefinition> getDataDefinitions()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of data definitions contained in this dictionary.</p>
	 * 
	 * @return Number of data definitions contained in this dictionary.
	 */
	public @UInt32 int countDataDefinitions();

	/**
	 * <p>Add a operation definition to those contained in the dictionary.</p>
	 * 
	 * @param operationDefinition Operation definition to add.
	 * 
	 * @throws NullPointerException The given operation definition is <code>null</code>.
	 * @throws InvalidParameterException An operation definition with the
	 * same identifier is already contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.constant.OperationConstant
	 */
	public void registerOperationDefinition(
			OperationDefinition operationDefinition) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Return the operation definition with the given identifier that is contained in this dictionary.</p>
	 * 
	 * @param operationId Identifier of an operation definition contained in this dictionary.
	 * @return Operation definition with the given id contained in this dictionary.
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * @throws InvalidParameterException The given identifier does not match that of an operation
	 * definition contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionWeakReference
	 */
	public OperationDefinition lookupOperationDefinition(
			AUID operationId) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns the set of operation definitions contained in this dictionary. This is
	 * an optional property.</p>
	 * 
	 * @return Shallow copy of the set of operation definitions contained in this dictionary.
	 * 
	 * @throws PropertyNotPresentException No operation definitions are present in this
	 * dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionStrongReferenceSet
	 */
	public Set<? extends OperationDefinition> getOperationDefinitions()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of operation definitions contained in this dictionary.</p>
	 * 
	 * @return Number of operation definitions contained in this dictionary.
	 */
	public @UInt32 int countOperationDefinitions();
	
	/**
	 * <p>Add a parameter definition to those contained in the dictionary.</p>
	 * 
	 * @param parameterDefinition Parameter definition to add.
	 * 
	 * @throws NullPointerException The given parameter definition is <code>null</code>.
	 * @throws InvalidParameterException A parameter definition with the
	 * same identifier is already contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.constant.ParameterConstant
	 */
	public void registerParameterDefinition(
			ParameterDefinition parameterDefinition) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns the parameter definition with the given identifier that is contained in this dictionary.</p>
	 * 
	 * @param parameterId Identifier for a parameter definition contained in this dictionary.
	 * @return Parameter definition with the given identifier contained in this dictionary.
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * @throws InvalidParameterException The given identifier does not match a
	 * parameter definition contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionWeakReference
	 */
	public ParameterDefinition lookupParameterDefinition(
			AUID parameterId) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns the set of parameter definitions contained in this dictionary. This is
	 * an optional property.</p>
	 * 
	 * @return Shallow copy of the set of parameter definitions contained in this dictionary.
	 * 
	 * @throws PropertyNotPresentException No parameter definitions are present in
	 * this dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionStrongReferenceSet
	 */
	public Set<? extends ParameterDefinition> getParameterDefinitions()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of parameter definitions contained in this dictionary.</p>
	 * 
	 * @return Number of parameter definitions contained in this dictionary.
	 */
	public @UInt32 int countParameterDefinitions();

	/** 
	 * <p>Add a codec definition to those contained in this dictionary.</p>
	 * 
	 * @param codecDefinition Codec definition to add.
	 * 
	 * @throws NullPointerException The given codec definition is <code>null</code>.
	 * @throws InvalidParameterException A codec definition with
	 * the same identifier is already contained in this dictionary. 
	 * 
	 * @see tv.amwa.maj.constant.CodecConstant
	 */
	public void registerCodecDefinition(
			CodecDefinition codecDefinition) 	
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns the codec definition with the given identifier that is contained in this dictionary.</p>
	 * 
	 * @param codecId Identifier for a codec definition contained in this dictionary. 
	 * @return Codec definition with the given identifier contained in this dictionary.
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * @throws InvalidParameterException The given identifier does not match a
	 * codec definition contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionWeakReference
	 */
	public CodecDefinition lookupCodecDefinition(
			AUID codecId) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns a set of all the codec definitions contained in the dictionary.</p>
	 * 
	 * @return Shallow copy of the set of codec definitions contained in this dictionary.
	 * 
	 * @throws PropertyNotPresentException No codec definitions are present in this
	 * dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionStrongReferenceSet
	 */
	public Set<? extends CodecDefinition> getCodecDefinitions()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of codec definitions contained in this dictionary.</p>
	 * 
	 * @return Number of codec definitions contained in this dictionary.
	 */
	public @UInt32 int countCodecDefinitions();

	/**
	 * <p>Add a container definition to those contained in this dictionary.</p>
	 * 
	 * @param containerDefinition Container definition to add.
	 * 
	 * @throws NullPointerException The given container definition is <code>null</code>.
	 * @throws InvalidParameterException A container definition with
	 * the same identifier is already contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.constant.ContainerConstant
	 */
	public void registerContainerDefinition(
			ContainerDefinition containerDefinition) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns the container definition with the given identifier that is contained in this dictionary.</p>
	 * 
	 * @param containerId Identifier for a container definition contained in this dictionary.
	 * @return Container definition contained in this dictionary with the given id.
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * @throws InvalidParameterException The given identifier does not match a
	 * codec definition contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionWeakReference
	 */
	public ContainerDefinition lookupContainerDefinition(
			AUID containerId) 
		throws NullPointerException,
			 InvalidParameterException;

	/**
	 * <p>Returns the set of all container definitions contained in this dictionary.
	 * This is an optional property.</p>
	 * 
	 * @return Shallow copy of the set of container definitions.
	 * 
	 * @throws PropertyNotPresentException No container definitions are present in
	 * this dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionStrongReferenceSet
	 */
	public Set<? extends ContainerDefinition> getContainerDefinitions()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of container definitions contained in this dictionary.</p>
	 * 
	 * @return Number of container definitions contained in this dictionary.
	 */
	public @UInt32 int countContainerDefinitions();

	/**
	 * <p>Add an interpolation definition to those contained in this dictionary.</p>
	 * 
	 * @param interpolationDefinition Interpolation definition to add.
	 * 
	 * @throws NullPointerException The given interpolation definition is <code>null</code>.
	 * @throws InvalidParameterException An interpolation definition with
	 * the same identifier is already contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.constant.InterpolationConstant
	 */
	public void registerInterpolationDefinition(
			InterpolationDefinition interpolationDefinition) 
		throws NullPointerException,
			InvalidParameterException;
	
	/**
	 * <p>Returns the interpolation definition with the given identifier that is contained in this dictionary.</p>
	 * 
	 * @param interpolationId Identifier for an interpolation definition contained in this dictionary.
	 * @return Interpolation definition with the given identifier contained in this dictionary.
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * @throws InvalidParameterException The given identifier does not match
	 * that of an interpolation definition contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#InterpolationDefinitionWeakReference
	 */
	public InterpolationDefinition lookupInterpoliationDefinition(
			AUID interpolationId) 
		throws NullPointerException,
			InvalidParameterException;
	
	/**
	 * <p>Returns the set of interpolation definitions contained in this dictionary. This is
	 * an optional property.</p>
	 * 
	 * @return Shallow copy of the set of interpolation definitions contained in this dictionary.
	 * 
	 * @throws PropertyNotPresentException No interpolation definitions are present in this
	 * dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#InterpolationDefinitionStrongReferenceSet
	 */
	public Set<? extends InterpolationDefinition> getInterpolationDefinitions()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of interpolation definitions contained in 
	 * this dictionary.</p>
	 * 
	 * @return Number of interpolation definitions contained in this dictionary.
	 */
	public @UInt32 int countInterpolationDefinitions();
	
	/**
	 * <p>Add a plugin definition to those contained in this dictionary.</p>
	 * 
	 * @param pluginDefinition Plugin definition to add.
	 * 
	 * @throws NullPointerException The given plugin definition is <code>null</code>.
	 * @throws InvalidParameterException A plugin definition with
	 * the same identifier is already contained in this dictionary.
	 * 
	 */
	public void registerPluginDefinition(
			PluginDefinition pluginDefinition) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Return the plugin definition with the given identifier that is contained in this dictionary.</p>
	 * 
	 * @param pluginId Identifier for a plugin definition contained in this dictionary.
	 * @return Plugin definition with the given identifier contained in this dictionary.
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * @throws InvalidParameterException The given identifier does not match that of
	 * a plugin definition contained in this dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionWeakReference
	 */
	public PluginDefinition lookupPluginDefinition(
			AUID pluginId) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns the set of plugin definitions contained in this dictionary. This is
	 * an optional property.</p>
	 * 
	 * @return Shallow copy of the set of plugin definitions contained in this dictionary.
	 * 
	 * @throws PropertyNotPresentException No plugin definitions are present in this
	 * dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionStrongReferenceSet
	 */
	public Set<? extends PluginDefinition> getPluginDefinitions()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of plugin definitions contained in this dictionary.</p>
	 * 
	 * @return Number of plugin definitions contained in this dictionary.
	 */
	public @UInt32 int countPluginDefinitions();

	/**
	 * <p>Add a KLV data definition to those contained in the dictionary.</p>
	 * 
	 * @param klvDataDefinition KLV data definition to add to the dictionary.
	 * 
	 * @throws NullPointerException The given KLV data definition is <code>null</code>.
	 * @throws InvalidParameterException The given KLV data definition has the same identifier
	 * as one already contained in the dictionary.
	 */
	public void registerKLVDataDefinition(
			KLVDataDefinition klvDataDefinition) 
		throws NullPointerException, 
			InvalidParameterException;

	/**
	 * <p>Returns the KLV data definition with the given identifier that is contained in this dictionary</p>
	 * 
	 * @param klvDataDefinitionId Identifier for a KLV data definition contained in this dictionary.
	 * @return KLV data definition with the given identifier contained in this dictionary.
	 * 
	 * @throws NullPointerException The given KLV data definition is <code>null</code>.
	 * @throws InvalidParameterException The given id does not match that of a KLV data definition 
	 * in the dictionary.
	 */
	public KLVDataDefinition lookupKLVDataDefinition(
			AUID klvDataDefinitionId) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns the set of  KLV data definitions contained in this dictionary. This
	 * is an optional property.</p>
	 * 
	 * @return Shallow copy of the set of KLV data definitions contained in this dictionary.
	 * 
	 * @throws PropertyNotPresentException No KLV data definitions are present in this
	 * dictionary.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataDefinitionStrongReferenceSet
	 */
	public Set<? extends KLVDataDefinition> getKLVDataDefinitions()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of KLV data definitions contained in this dictionary.</p>
	 * 
	 * @return Number of KLV data definitions contained in this dictionary.
	 */
	public @UInt32 int countKLVDataDefinitions();

	/**
	 * <p>Add the tagged value definition to those contained in this dictionary.</p>
	 * 
	 * @param taggedValueDefinition Tagged value definition to add to the dictionary.
	 * 
	 * @throws NullPointerException The given tagged value definition is <code>null</code>.
	 * @throws InvalidParameterException The given tagged value definition has the same
	 * identifier as one already contained in the dictionary.
	 */
	public void registerTaggedValueDefinition(
			TaggedValueDefinition taggedValueDefinition) 
		throws NullPointerException, 
			InvalidParameterException;

	/**
	 * <p>Returns the tagged value definition with the given identifier that is contained in this dictionary.</p>
	 * 
	 * @param taggedValueDefinitionId ID of the tagged value definition to lookup in the 
	 * dictionary.
	 * @return Matching tagged value definition in the dictionary.
	 * 
	 * @throws NullPointerException The given identifier is <code>null</code>.
	 * @throws InvalidParameterException The given identifier does not match that of
	 * a tagged value definition contained in this dictionary.
	 */
	public TaggedValueDefinition lookupTaggedValueDefinition(
			AUID taggedValueDefinitionId) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns a set of all tagged value definitions contained in this dictionary.
	 * This is an optional property.</p>
	 * 
	 * @return Shallow copy of the set the tagged value definitions contained
	 * in this dictionary.
	 * 
	 * @throws PropertyNotPresentException No tagged value definitions are present in
	 * this dictionary.
	 */
	public Set<? extends TaggedValueDefinition> getTaggedValueDefinitions()
		throws PropertyNotPresentException;

	/**
	 * </p>Returns the number of tagged value definitions contained in this dictionary.</p>
	 * 
	 * @return Number of tagged value definitions contained in the dictionary.
	 */
	public @UInt32 int countTaggedValueDefinitions();

	/**
	 * <p>Returns the auxiliary data definition. This is a built-in 
	 * {@linkplain DataDefinition data definition} for auxiliary data.</p>
	 * 
	 * @return Auxiliary data definition.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Auxiliary
	 */
	public DataDefinition lookupAuxiliaryDataDefinition();

	/**
	 * <p>Returns the descriptive metadata data definition. This is a built-in
	 * {@linkplain DataDefinition data definition} for descriptive metadata.</p>
	 * 
	 * @return Descriptive metadata data definition.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#DescriptiveMetadata
	 */
	public DataDefinition lookupDescriptiveMedadataDataDefinition();

	/** 
	 * <p>Returns the edgecode data definition. This is a built-in
	 * {@linkplain DataDefinition data definition} for a stream of film edge 
	 * code values.</p>
	 * 
	 * @return Edgecode data definition.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Edgecode
	 */
	public DataDefinition lookupEdgecodeDataDefinition();

	/**
	 * <p>Returns the legacy picture data definition. This is a 
	 * built-in {@linkplain DataDefinition data definition} for a stream of 
	 * essence that contains image data.</p>
	 * 
	 * @return Legacy picture data definition.
	 * 
	 * @deprecated As of AAF v1.1, the legacy picture data definition is
	 * deprecated. Use the picture data definition, as returned by
	 * {@link #lookupPictureDataDefinition()}, instead.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#LegacyPicture
	 */
	@Deprecated public DataDefinition lookupLegacyPictureDataDefinition();

	/**
	 * <p>Returns the legacy sound data definition. This is a 
	 * built-in {@linkplain DataDefinition data definition} essence that 
	 * contains a single channel of sound.</p>
	 * 
	 * @return Legacy sound data definition.
	 * 
	 * @deprecated As of AAF v1.1, the legacy sound data definition is
	 * deprecated. Use the sound data definition, as returned by
	 * {@link #lookupSoundDataDefinition()}, instead.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#LegacySound
	 */
	@Deprecated public DataDefinition lookupLegacySoundDataDefinition();

	/**
	 * <p>Returns the legacy timecode data definition. This is a 
	 * built-in {@linkplain DataDefinition data definition} for a stream of 
	 * tape timecode values.</p>
	 * 
	 * @return Legacy timecode data definition.
	 * 
	 * @deprecated As of AAF v1.1, the legacy timecode data definition is
	 * deprecated. Use the timecode data definition, as returned by
	 * {@link #lookupTimecodeDataDefinition()}, instead.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#LegacyTimecode
	 */
	@Deprecated public DataDefinition lookupLegacyTimecodeDataDefinition();

	/**
	 * <p>Returns the matte data definition. This is a built-in
	 * {@linkplain DataDefinition data definition} for a stream of essence that
	 * contains alpha values.</p>
	 * 
	 * @return Matte data definition.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Matte
	 */
	public DataDefinition lookupMatteDataDefinition();

	/**
	 * <p>Returns the picture data definition. This is a built-in
	 * {@linkplain DataDefinition data definition} for a stream of essence that 
	 * contains image data.</p>
	 * 
	 * @return Picture data definition.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Picture
	 */
	public DataDefinition lookupPictureDataDefinition();

	/**
	 * <p>Returns the picture with matte data definition. This is a built-in
	 * {@linkplain DataDefinition data definition} for a stream of essence that 
	 * contains image data and a matte.</p>
	 * 
	 * @return Picture with matte data definition.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#PictureWithMatte
	 */
	public DataDefinition lookupPictureWithMatteDataDefinition();

	/**
	 * <p>Returns a sound data definition. This is a built-in
	 * {@linkplain DataDefinition data definition} for a a stream of essence 
	 * that contains a single channel of sound.</p>
	 * 
	 * @return Sound data definition.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Sound
	 */
	public DataDefinition lookupSoundDataDefinition();

	/**
	 * <p>Returns the timecode data definition. This is a built-in
	 * {@link DataDefinition data definition} for a stream of tape 
	 * timecode values.</p>
	 * 
	 * @return Timecode data definition.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Timecode
	 */
	public DataDefinition lookupTimecodeDataDefinition();
	

	/**
	 * <p>Scan the given metadata object to see if it contains and references to 
	 * {@linkplain DefinitionObject definitions} not known in this dictionary, 
	 * adding any missing definitions to this dictionary as it goes.</p>
	 * 
	 * @param metadataObject Metadata object to check for missing definitions.
	 * @return Are all definitions referenced by the given metadata object now
	 * in registered in this dictionary?
	 */
	public boolean addDefinitions(
			MetadataObject metadataObject);

	/**
	 * <p>Add the given {@linkplain DefinitionObject definition} to this dictionary
	 * if it is not already contained. If the definition is already represented, this
	 * methods returns successfully.</p>
	 * 
	 * @param definition Definition to add to this dictionary.
	 * @return Has the given definition been added to this dictionary successfully.
	 */
	public boolean addDefinition(
			DefinitionObject definition);
	
	/**
	 * <p>Create a cloned copy of this dictionary.</p>
	 *
	 * @return Cloned copy of this dictionary.
	 */
	public Dictionary clone();

	
}
