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
 * $Log: DictionaryImpl.java,v $
 * Revision 1.3  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/06/18 16:46:37  vizigoth
 * Fixed naming issues that caused issues with the media engine setting values.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.3  2008/01/27 11:14:40  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.2  2007/12/04 13:04:47  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:17  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.StrongReferenceSet;
import tv.amwa.maj.io.aaf.AAFConstants;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.impl.TypeDefinitionIndirectImpl.IndirectValue;
import tv.amwa.maj.meta.impl.TypeDefinitionSetImpl.SetValue;
import tv.amwa.maj.meta.impl.TypeDefinitionVariableArrayImpl.VariableArrayValue;
import tv.amwa.maj.model.ApplicationObject;
import tv.amwa.maj.model.CodecDefinition;
import tv.amwa.maj.model.ContainerDefinition;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.DefinitionObject;
import tv.amwa.maj.model.Dictionary;
import tv.amwa.maj.model.InterpolationDefinition;
import tv.amwa.maj.model.KLVDataDefinition;
import tv.amwa.maj.model.OperationDefinition;
import tv.amwa.maj.model.ParameterDefinition;
import tv.amwa.maj.model.PluginDefinition;
import tv.amwa.maj.model.TaggedValueDefinition;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements a container for {@linkplain tv.amwa.maj.model.DefinitionObject definitions}.  The dictionary specified here is both 
 * an AAF <em>Dictionary</em> and an AAF <em>MetaDictionary</em>. Every {@linkplain tv.amwa.maj.model.AAFFile AAF file} has a dictionary 
 * within its {@linkplain tv.amwa.maj.model.Preface preface} and contains a representation of the meta dictionary.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#DictionaryStrongReference
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2200,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Dictionary",
		  description = "The Dictionary class has DefinitionObject objects.",
		  symbol = "Dictionary")
public class DictionaryImpl
	extends 
		InterchangeObjectImpl
	implements 
		Dictionary,
		Serializable,
		XMLSerializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 7981177320341108556L;
	
	/** <p>Maps identities to definitions.</p> */
	private transient Map<AUID, DefinitionObject> idsToDefinitions =
		Collections.synchronizedMap(new HashMap<AUID, DefinitionObject>());
	
	private Set<CodecDefinition> codecDefinitions =
		Collections.synchronizedSet(new HashSet<CodecDefinition>());
	private Set<ContainerDefinition> containerDefinitions =
		Collections.synchronizedSet(new HashSet<ContainerDefinition>());
	private Set<DataDefinition> dataDefinitions =
		Collections.synchronizedSet(new HashSet<DataDefinition>());
	private Set<InterpolationDefinition> interpolationDefinitions =
		Collections.synchronizedSet(new HashSet<InterpolationDefinition>());
	private Set<KLVDataDefinition> kLVDataDefinitions =
		Collections.synchronizedSet(new HashSet<KLVDataDefinition>());
	private Set<OperationDefinition> operationDefinitions =
		Collections.synchronizedSet(new HashSet<OperationDefinition>());
	private Set<ParameterDefinition> parameterDefinitions =
		Collections.synchronizedSet(new HashSet<ParameterDefinition>());
	private Set<PluginDefinition> pluginDefinitions =
		Collections.synchronizedSet(new HashSet<PluginDefinition>());
	private Set<TaggedValueDefinition> taggedValueDefinitions =
		Collections.synchronizedSet(new HashSet<TaggedValueDefinition>());
	
	// Removed KLV data key maps ... just use KLVDataDefinition.getKLVDataType()
	
	/**
	 * <p>Creates and initializes a new dictionary object, which has the definition objects 
	 * of an AAF persistent unit. All the sets of items in the dictionary are initialized to 
	 * empty sets.</p>
	 */
	public DictionaryImpl() {
	}

	@MediaPropertyCount("CodecDefinitions")
	public int countCodecDefinitions() {

		return codecDefinitions.size();
	}

	@MediaPropertyCount("ContainerDefinitions")
	public int countContainerDefinitions() {

		return containerDefinitions.size();
	}

	@MediaPropertyCount("DataDefinitions")
	public int countDataDefinitions() {

		return dataDefinitions.size();
	}

	@MediaPropertyCount("InterpolationDefinitions")
	public int countInterpolationDefinitions() {

		return interpolationDefinitions.size();
	}

	@MediaPropertyCount("KLVDataDefinitions")
	public int countKLVDataDefinitions() {

		return kLVDataDefinitions.size();
	}

	@MediaPropertyCount("OperationDefinitions")
	public int countOperationDefinitions() {

		return operationDefinitions.size();
	}

	@MediaPropertyCount("ParameterDefinitions")
	public int countParameterDefinitions() {

		return parameterDefinitions.size();
	}

	@MediaPropertyCount("PluginDefinitions")
	public int countPluginDefinitions() {

		return pluginDefinitions.size();
	}

	@MediaPropertyCount("TaggedValueDefinitions")
	public int countTaggedValueDefinitions() {

		return taggedValueDefinitions.size();
	}

	@MediaPropertyClear("CodecDefinitions")
	public void clearCodecDefinitions() {

		codecDefinitions = Collections.synchronizedSet(new HashSet<CodecDefinition>());
	}

	@MediaPropertyClear("ContainerDefinitions")
	public void clearContainerDefinitions() {

		containerDefinitions = Collections.synchronizedSet(new HashSet<ContainerDefinition>());
	}

	@MediaPropertyClear("DataDefinitions")
	public void clearDataDefinitions() {

		dataDefinitions = Collections.synchronizedSet(new HashSet<DataDefinition>());
	}

	@MediaPropertyClear("InterpolationDefinitions")
	public void clearInterpolationDefinitions() {

		interpolationDefinitions = Collections.synchronizedSet(new HashSet<InterpolationDefinition>());
	}

	@MediaPropertyClear("KLVDataDefinitions")
	public void clearKLVDataDefinitions() {

		kLVDataDefinitions = Collections.synchronizedSet(new HashSet<KLVDataDefinition>());
	}

	@MediaPropertyClear("OperationDefinitions")
	public void clearOperationDefinitions() {

		operationDefinitions = Collections.synchronizedSet(new HashSet<OperationDefinition>());
	}

	@MediaPropertyClear("ParameterDefinitions")
	public void clearParameterDefinitions() {

		parameterDefinitions = Collections.synchronizedSet(new HashSet<ParameterDefinition>());
	}

	@MediaPropertyClear("PluginDefinitions")
	public void clearPluginDefinitions() {

		pluginDefinitions = Collections.synchronizedSet(new HashSet<PluginDefinition>());
	}

	@MediaPropertyClear("TaggedValueDefinitions")
	public void clearTaggedValueDefinitions() {
		
		taggedValueDefinitions = Collections.synchronizedSet(new HashSet<TaggedValueDefinition>());
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0507, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "CodecDefinitions",
			typeName = "CodecDefinitionStrongReferenceSet", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2607,
			symbol = "CodecDefinitions")
	public Set<CodecDefinition> getCodecDefinitions() 
		throws PropertyNotPresentException {

		return StrongReferenceSet.getOptionalSet(codecDefinitions);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0508, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ContainerDefinitions",
			typeName = "ContainerDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2608,
			symbol = "ContainerDefinitions")
	public Set<ContainerDefinition> getContainerDefinitions() 
		throws PropertyNotPresentException {

		return StrongReferenceSet.getOptionalSet(containerDefinitions);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0505, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "DataDefinitions",
			typeName = "DataDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2605,
			symbol = "DataDefinitions")
	public Set<DataDefinition> getDataDefinitions() 
		throws PropertyNotPresentException {

		return StrongReferenceSet.getOptionalSet(dataDefinitions);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0509, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "InterpolationDefinitions",
			typeName = "InterpolationDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2609,
			symbol = "InterpolationDefinitions")
	public Set<InterpolationDefinition> getInterpolationDefinitions() 
		throws PropertyNotPresentException {

		return StrongReferenceSet.getOptionalSet(interpolationDefinitions);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x050a, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "KLVDataDefinitions",
			typeName = "KLVDataDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x260A,
			symbol = "KLVDataDefinitions")
	public Set<KLVDataDefinition> getKLVDataDefinitions() 
		throws PropertyNotPresentException {

		return StrongReferenceSet.getOptionalSet(kLVDataDefinitions);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0504, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "ParameterDefinitions",
			typeName = "ParameterDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2604,
			symbol = "ParameterDefinitions")
	public Set<ParameterDefinition> getParameterDefinitions() 
		throws PropertyNotPresentException {

		return StrongReferenceSet.getOptionalSet(parameterDefinitions);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0503, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "OperationDefinitions",
			typeName = "OperationDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2603,
			symbol = "OperationDefinitions")
	public Set<OperationDefinition> getOperationDefinitions() 
		throws PropertyNotPresentException {

		return StrongReferenceSet.getOptionalSet(operationDefinitions);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0506, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PluginDefinitions",
			typeName = "PluginDefinitionStrongReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x2606,
			symbol = "PluginDefinitions")
	public Set<PluginDefinition> getPluginDefinitions() 
		throws PropertyNotPresentException {

		return StrongReferenceSet.getOptionalSet(pluginDefinitions);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x050b, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "TaggedValueDefinitions",
			typeName = "TaggedValueDefinitionStrongReferenceSet", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x260B,
			symbol = "TaggedValueDefinitions")
	public Set<TaggedValueDefinition> getTaggedValueDefinitions() 
		throws PropertyNotPresentException {

		return StrongReferenceSet.getOptionalSet(taggedValueDefinitions);
	}

	DefinitionObject lookupDefinition(
			Set<? extends DefinitionObject> lookMeUpAndDown,
			AUID id,
			String aTypeName)
		throws NullPointerException,
			InvalidParameterException {
		
		if (id == null)
			throw new NullPointerException("Cannot lookup " + aTypeName + " with a null value.");
		
		rebuildTables();
		
		if (!(idsToDefinitions.containsKey(id)))
			throw new InvalidParameterException("A definition matching the given id cannot be found in the dictionary.");
		
		DefinitionObject definition = idsToDefinitions.get(id);
		if (!(lookMeUpAndDown.contains(definition)))
			throw new InvalidParameterException("The definition in the dictionary matching the given id is not " + aTypeName + ".");

		return definition;
	}

	public DataDefinition lookupAuxiliaryDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.Auxiliary);
	}

	public CodecDefinition lookupCodecDefinition(
			tv.amwa.maj.record.AUID codecId)
			throws NullPointerException,
				InvalidParameterException {

		return (CodecDefinition) lookupDefinition(codecDefinitions, codecId, "a codec definition");
	}

	public ContainerDefinition lookupContainerDefinition(
			AUID containerId)
		throws NullPointerException,
			InvalidParameterException {

		return (ContainerDefinition) lookupDefinition(
				containerDefinitions, 
				containerId, 
				"a container definition");
	}

	public DataDefinition lookupDataDefinition(
			AUID dataDefinitionId)
		throws NullPointerException,
			InvalidParameterException {

		return (DataDefinition) lookupDefinition(dataDefinitions, dataDefinitionId, "a data definition");
	}

	public DataDefinition lookupDescriptiveMedadataDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.DescriptiveMetadata);
	}

	public DataDefinition lookupEdgecodeDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.Edgecode);
	}

	public InterpolationDefinition lookupInterpoliationDefinition(
			AUID interpolationId)
		throws NullPointerException,
			InvalidParameterException {

		return (InterpolationDefinition) lookupDefinition(
				interpolationDefinitions, 
				interpolationId, 
				"an interpolation definition");
	}

	public KLVDataDefinition lookupKLVDataDefinition(
			AUID klvDataDefinitionId)
			throws NullPointerException,
				InvalidParameterException {

		return (KLVDataDefinition) lookupDefinition(
				kLVDataDefinitions, 
				klvDataDefinitionId, 
				"a KLV data definition");
	}

	@SuppressWarnings("deprecation")
	public DataDefinition lookupLegacyPictureDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.LegacyPicture);
	}

	@SuppressWarnings("deprecation")
	public DataDefinition lookupLegacySoundDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.LegacySound);
	}

	@SuppressWarnings("deprecation")
	public DataDefinition lookupLegacyTimecodeDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.LegacyTimecode);
	}

	public DataDefinition lookupMatteDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.Matte);
	}

	public OperationDefinition lookupOperationDefinition(
			tv.amwa.maj.record.AUID operationId)
		throws NullPointerException,
			InvalidParameterException {

		return (OperationDefinition) lookupDefinition(
				operationDefinitions,
				operationId, 
				"an operation definition");
	}

	public ParameterDefinition lookupParameterDefinition(
			AUID parameterId)
		throws NullPointerException,
			InvalidParameterException {

		return (ParameterDefinition) lookupDefinition(
				parameterDefinitions, 
				parameterId,
				"a parameter definition");
	}

	public DataDefinition lookupPictureDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.Picture);
	}

	public DataDefinition lookupPictureWithMatteDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.PictureWithMatte);
	}

	public PluginDefinition lookupPluginDefinition(
			AUID pluginId)
		throws NullPointerException,
				InvalidParameterException {

		return (PluginDefinition) lookupDefinition(
				pluginDefinitions, 
				pluginId, 
				"a plugin definition");
	}

	public DataDefinition lookupSoundDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.Sound);
	}

	public DataDefinition lookupTimecodeDataDefinition() {

		return DataDefinitionImpl.forIdentification(DataDefinitionImpl.Timecode);
	}

	public TaggedValueDefinition lookupTaggedValueDefinition(
			AUID taggedValueDefinitionId)
		throws NullPointerException,
			InvalidParameterException {

		return (TaggedValueDefinition) lookupDefinition(taggedValueDefinitions, taggedValueDefinitionId, 
				"a tagged value definition");
	}

	void checkRegistration(
			Set<? extends DefinitionObject> setToBeGrown,
			DefinitionObject definition,
			String aTypeName) 
		throws NullPointerException,
			InvalidParameterException {
		
		if (definition == null)
			throw new NullPointerException("Cannot register a null value as " + aTypeName + ".");
		
		if (setToBeGrown.contains(definition))
			throw new InvalidParameterException("The given definition to register as " + aTypeName + " is already registered.");
	}

	/**
	 * <p>Adds the given definition to the mapping of definition names to definitions ids. Names
	 * that are not qualified with their class are guaranteed to be unique in the dictionary and 
	 * so one name may map to one or more definitions identities. To cope with this, an unqualified 
	 * name maps to a list of possible ids.</p>
	 * 
	 * <p>A name can be qualified with its class name using an underscore character 
	 * ("<code>_</code>") separating the type of the definition from its unqualified name. 
	 * For example:</p>
	 * 
	 * <p><center><code>DataDefinition_Picture</code></center></p>
	 * 
	 * <p>A fully qualified name does map to just one id, although this is wrapped in a list
	 * for convenience. Both qualified and unqualified names are added to the map from names
	 * to definitions ids.</p>
	 *
	 * @param definition Definition Definition to add to the map.
	 * @param canonicalName Canonical name of the type of the definition, for example 
	 * "<code>DataDefinition</code>".
	 */
	void addToSharedTables(
			DefinitionObject definition,
			String canonicalName) {
		
		AUID definitionId = definition.getAUID();
		idsToDefinitions.put(definitionId, definition);			
	}

	@MediaSetAdd("CodecDefinitions")
	public void registerCodecDefinition(
			CodecDefinition codecDefinition)
		throws NullPointerException,
			InvalidParameterException {

		checkRegistration(codecDefinitions, codecDefinition, "a codec definition");
		StrongReferenceSet.add(codecDefinitions, codecDefinition);
		addToSharedTables(codecDefinition, "CodecDefinition");
		
	}

	@MediaSetAdd("ContainerDefinitions")
	public void registerContainerDefinition(
			ContainerDefinition containerDefinition)
		throws NullPointerException,
			InvalidParameterException {
		
		checkRegistration(containerDefinitions, containerDefinition, "a container definition");
		StrongReferenceSet.add(containerDefinitions, containerDefinition);
		addToSharedTables(containerDefinition, "ContainerDefinition");
	}

	@MediaSetAdd("DataDefinitions")
	public void registerDataDefinition(
			DataDefinition dataDefinition)
		throws NullPointerException,
			InvalidParameterException {
		
		checkRegistration(dataDefinitions, dataDefinition, "a data definition");
		StrongReferenceSet.add(dataDefinitions, dataDefinition);
		addToSharedTables(dataDefinition, "DataDefinition");
	}

	@MediaSetAdd("InterpolationDefinitions")
	public void registerInterpolationDefinition(
			InterpolationDefinition interpolationDefinition)
			throws NullPointerException,
				InvalidParameterException {
		
		checkRegistration(interpolationDefinitions, interpolationDefinition, "a interpolation definition");
		StrongReferenceSet.add(interpolationDefinitions, interpolationDefinition);
		addToSharedTables(interpolationDefinition, "InterpolationDefinition");
	}

	@MediaSetAdd("KLVDataDefinitions")
	public void registerKLVDataDefinition(
			tv.amwa.maj.model.KLVDataDefinition klvDataDefinition)
		throws NullPointerException,
			InvalidParameterException {

		checkRegistration(kLVDataDefinitions, klvDataDefinition, "a KLV data definition");
		StrongReferenceSet.add(kLVDataDefinitions, klvDataDefinition);
		addToSharedTables(klvDataDefinition, "KLVDataDefinition");
	}

	@MediaSetAdd("OperationDefinitions")
	public void registerOperationDefinition(
			OperationDefinition operationDefinition)
		throws NullPointerException,
			InvalidParameterException {
		
		checkRegistration(operationDefinitions, operationDefinition, "an operation definition");
		StrongReferenceSet.add(operationDefinitions, operationDefinition);
		addToSharedTables(operationDefinition, "OperationDefinition");
	}

	@MediaSetAdd("ParameterDefinitions")
	public void registerParameterDefinition(
			ParameterDefinition parameterDefinition)
		throws NullPointerException,
			InvalidParameterException {

		checkRegistration(parameterDefinitions, parameterDefinition, "a parameter definition");
		StrongReferenceSet.add(parameterDefinitions, parameterDefinition);
		addToSharedTables(parameterDefinition, "ParameterDefinition");
	}

	@MediaSetAdd("PluginDefinitions")
	public void registerPluginDefinition(
			PluginDefinition pluginDefinition)
			throws NullPointerException,
				InvalidParameterException {

		checkRegistration(pluginDefinitions, pluginDefinition, "a plugin definition");
		StrongReferenceSet.add(pluginDefinitions, pluginDefinition);
		addToSharedTables(pluginDefinition, "PluginDefinition");
	}

	@MediaSetAdd("TaggedValueDefinitions")
	public void registerTaggedValueDefinition(
			tv.amwa.maj.model.TaggedValueDefinition taggedValueDefinition)
		throws NullPointerException,
			InvalidParameterException {

		checkRegistration(taggedValueDefinitions, taggedValueDefinition, "a tagged value definition");
		StrongReferenceSet.add(taggedValueDefinitions, taggedValueDefinition);
		addToSharedTables(taggedValueDefinition, "TaggedValueDefinition");
	}
	
	public Dictionary clone() {
		
		return (Dictionary) super.clone();
	}
	
	private void rebuildTables() {
		
		if (idsToDefinitions.size() >= 0) return;
		
		idsToDefinitions.clear();
		
		for (CodecDefinition codecDefinition : codecDefinitions )
			addToSharedTables(codecDefinition, "CodecDefinition");
		for (ContainerDefinition containerDefinition : containerDefinitions )
			addToSharedTables(containerDefinition, "ContainerDefinition");
		for (DataDefinition dataDefinition : dataDefinitions )
			addToSharedTables(dataDefinition, "DataDefinition");
		for (InterpolationDefinition interpolationDefinition : interpolationDefinitions )
			addToSharedTables(interpolationDefinition, "InterpolationDefinition");
		for (KLVDataDefinition klvDataDefinition : kLVDataDefinitions )
			addToSharedTables(klvDataDefinition, "KLVDataDefinition");
		for (OperationDefinition operationDefinition : operationDefinitions )
			addToSharedTables(operationDefinition, "OperationDefinition");
		for (ParameterDefinition parameterDefinition : parameterDefinitions )
			addToSharedTables(parameterDefinition, "ParameterDefinition");
		for (PluginDefinition pluginDefinition : pluginDefinitions )
			addToSharedTables(pluginDefinition, "PluginDefinition");
		for (TaggedValueDefinition taggedValueDefinition : taggedValueDefinitions )
			addToSharedTables(taggedValueDefinition, "TaggedValueDefinition");
	}
	
	public boolean addDefinitions(
			MetadataObject mdObject) {
		
		if (mdObject == this) return true;
		
		boolean success = true;
		
		if (mdObject == null) return false;
		
		if (mdObject instanceof DefinitionObject)
			success &= addDefinition((DefinitionObject) mdObject);
		
		
		SortedMap<? extends PropertyDefinition,? extends PropertyValue> values = null;
		
		if (mdObject instanceof ApplicationObject) {
			values = ((ApplicationObject) mdObject).getProperties();
		}
		else {
			ClassDefinition targetClass = MediaEngine.getClassDefinition(mdObject);
			values = targetClass.getProperties(mdObject);
		}
		
		for ( PropertyDefinition property : values.keySet() ) {
			
			if (property.getAUID().equals(AAFConstants.ObjectClassID)) continue;
			
			TypeDefinition type = property.getTypeDefinition();
			if (type == null) continue;
			PropertyValue value = null;
			
			switch (type.getTypeCategory()) {
			
			case WeakObjRef:
			case StrongObjRef:
				value = values.get(property);
				//if (!(value.getValue() instanceof MetaDefinition))
					success &= addDefinitions((MetadataObject) value.getValue());
				break;
			case VariableArray:
				
				value = values.get(property);
				List<Object> list = ((VariableArrayValue) value).getValue();
				
				for ( Object listItem : list )
					if (listItem instanceof MetadataObject)
						success &= addDefinitions((MetadataObject) listItem);
				break;
			case Set:
				value = values.get(property);
				Set<Object> elements = ((SetValue) value).getValue();
				
				for (Object element : elements )
					if (element instanceof MetadataObject)
						success &= addDefinitions((MetadataObject) element);
				break;
			case FixedArray:
				break;
			case Indirect:
				value = values.get(property);
				PropertyValue indirectValue = ((IndirectValue) value).getValue();
				if (indirectValue.getValue() instanceof MetadataObject)
					success &= addDefinitions((MetadataObject) indirectValue.getValue());
				break;
				// TODO consider rename
			default:
				break;
			
			}
		}
		
		return success;
	}
	
	public boolean addDefinition(
			DefinitionObject definition) {
		
		if (definition == null)
			return false;
		
		try {
			switch (definition.getClass().getSimpleName().charAt(0)) {
			
			case 'C':
				if (definition instanceof CodecDefinition) {
					registerCodecDefinition((CodecDefinition) definition);
					return true;
				}
				if (definition instanceof ContainerDefinition) {
					registerContainerDefinition((ContainerDefinition) definition);
					return true;
				}
				return false;
			case 'D':
				if (definition instanceof DataDefinition) {
					registerDataDefinition((DataDefinition) definition);
					return true;
				}
				return false;
			case 'I':
				if (definition instanceof InterpolationDefinition) {
					registerInterpolationDefinition((InterpolationDefinition) definition);
					return true;
				}
				return false;
			case 'K':
				if (definition instanceof KLVDataDefinition) {
					registerInterpolationDefinition((InterpolationDefinition) definition);
					return true;
				}
				return false;
			case 'O':
				if (definition instanceof OperationDefinition) {
					registerOperationDefinition((OperationDefinition) definition);
					return true;
				}
				return false;
			case 'P':
				if (definition instanceof ParameterDefinition) {
					registerParameterDefinition((ParameterDefinition) definition);
					return true;
				}
				if (definition instanceof PluginDefinition) {
					registerPluginDefinition((PluginDefinition) definition);
					return true;
				}
				return false;
			case 'T':
				if (definition instanceof TaggedValueDefinition) {
					registerTaggedValueDefinition((TaggedValueDefinition) definition);
					return true;
				}
				return false;
			default:
				return false;
			}
		}
		catch (InvalidParameterException ipe) {
			// Definition already contained is good :-)
			return true;
		}
	}
	
	@Override
	public String getComment() {
		
		return "local dictionary persistent id: " + getPersistentID();
	}
}
