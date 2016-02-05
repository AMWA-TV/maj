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
 * $Log: OperationGroupImpl.java,v $
 * Revision 1.4  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
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
 * Revision 1.3  2007/12/04 13:04:48  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.2  2007/12/04 09:23:57  vizigoth
 * Fix to replace MissingParameterException with ParameterNotFound exception.
 *
 * Revision 1.1  2007/11/13 22:09:43  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.DuplicateParameterException;
import tv.amwa.maj.exception.ParameterNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.OperationDefinition;
import tv.amwa.maj.model.OperationGroup;
import tv.amwa.maj.model.Parameter;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.model.SourceReferenceSegment;
import tv.amwa.maj.record.AUID;


// TODO cardinality is missing in diagram in AAF object spec 7.17

/*
 * The reference implementation uses a set of parameters rather than a list. A list is used here as
 * the hash-based ordering of a java.util.HashSet will lead to a different order of parameters from
 * the enumeration backing used in the C reference implementation.
 */

/** 
 * <p>Implements a container with an ordered set of {@linkplain tv.amwa.maj.model.Segment segments} and an 
 * {@linkplain tv.amwa.maj.model.OperationDefinition operation} that is performed on these segments.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationGroupStrongReference
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0a00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "OperationGroup",
		  description = "The OperationGroup class contains an ordered set of Segments and an operation that is performed on these Segments.",
		  symbol = "OperationGroup")
public class OperationGroupImpl
	extends 
		SegmentImpl
	implements 
		OperationGroup,
		tv.amwa.maj.extensions.quantel.QOperationGroup,
		Serializable,
		Cloneable {
	
	/** <p></p> */
	private static final long serialVersionUID = -919233865311520609L;

	private WeakReference<OperationDefinition> operation;
	private List<Segment> inputSegments = Collections.synchronizedList(new Vector<Segment>());
	private List<Parameter> parameters = Collections.synchronizedList(new Vector<Parameter>());
	private SourceReferenceSegment rendering = null;
	private Integer bypassOverride = null;
	
	public OperationGroupImpl() {
	}

	/**
	 * <p>Creates and initializes a new operation group class, which contains an ordered set of 
	 * {@linkplain tv.amwa.maj.model.Segment segments} and an operation that is performed on these. The lists of segments
	 * and parameters are initialized to empty lists.</p>
	 *
	 * @param dataDefinition Data definition object that specifies the kind of data described by 
	 * the component.
	 * @param length Length of the component represented by this operation group.
	 * @param operation Operation definition that identifies the kind of operation.
	 * 
	 * @throws NullPointerException One or more of the arguments are null.
	 * @throws BadLengthException Cannot set the length of an operation group to a negative value.
	 */
	public OperationGroupImpl(
			DataDefinition dataDefinition,
			long length,
			OperationDefinition operation)
		throws NullPointerException,
			BadLengthException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a new operation group with a null data definition.");
		if (operation == null)
			throw new NullPointerException("Cannot create a new operation group with a null operation definition.");
		if (length < 0)
			throw new BadLengthException("Cannot set the length of an operation group to a negative value.");
		
		setComponentDataDefinition(dataDefinition);
		setLengthPresent(true);
		setComponentLength(length);
		
		setOperationDefinition(operation);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x060a, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Parameters",
			typeName = "ParameterStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0B03,
			symbol = "Parameters")
	public List<Parameter> getParameters() 
		throws PropertyNotPresentException {
		
		if (parameters.size() == 0)
			throw new PropertyNotPresentException("No parameters are present for this operation group.");

		return StrongReferenceVector.getOptionalList(parameters);
	}

	@MediaListAppend("Parameters")
	public void addParameter(
			Parameter parameter)
		throws DuplicateParameterException,
			NullPointerException {

		if (parameter == null)
			throw new NullPointerException("Cannot add a null-valued parameter to the list of parameters of this operation group.");
		
		if (parameters.contains(parameter))
			throw new DuplicateParameterException("Cannot add the given parameter to the operation group as it is already contained.");
		
		StrongReferenceVector.append(parameters, parameter);
	}

	@MediaPropertyCount("Parameters")
	public int countParameters() {

		return parameters.size();
	}

	@MediaPropertyClear("Parameters")
	public void clearParameters() {
		
		parameters = Collections.synchronizedList(new Vector<Parameter>());
	}
	
	public Parameter lookupParameter(
			AUID argID)
		throws ParameterNotFoundException,
			NullPointerException {

		if (argID == null)
			throw new NullPointerException("Cannot lookup a parameter in an operation group using a null AUID value.");
		
		for ( Parameter parameter : parameters) {
			if (parameter.getParameterDefinition().getAUID().equals(argID))
				return parameter;
		}
		
		throw new ParameterNotFoundException("A parameter with the given id was not found in this operation group.");
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0602, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "InputSegments",
			typeName = "SegmentStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0B02,
			symbol = "InputSegments")
	public List<Segment> getInputSegments() 
		throws PropertyNotPresentException {
		
		if (inputSegments.size() == 0)
			throw new PropertyNotPresentException("No input segments are defined for this operation group.");
		
		return StrongReferenceVector.getOptionalList(inputSegments);
	}

	@MediaListGetAt("InputSegments")
	public Segment getInputSegmentAt(
			int index)
		throws IndexOutOfBoundsException {

		return StrongReferenceVector.getAt(inputSegments, index);
	}

	@MediaListAppend("InputSegments")
	public void appendInputSegment(
			Segment segment)
		throws NullPointerException {

		if (segment == null)
			throw new NullPointerException("Cannot append a null valued segment to the list of input segments of this operation group.");
		
		StrongReferenceVector.append(inputSegments, segment);
	}
	
	@MediaPropertyCount("InputSegments")
	public int countInputSegments() {

		return inputSegments.size();
	}

	@MediaListInsertAt("InputSegments")
	public void insertInputSegmentAt(
			int index,
			Segment segment)
		throws NullPointerException, 
			IndexOutOfBoundsException {

		if (segment == null)
			throw new NullPointerException("Cannot insert a null valued segment into the list of segments of this operation group.");
		
		StrongReferenceVector.insert(inputSegments, index, segment);
	}

	@MediaListPrepend("InputSegments")
	public void prependInputSegment(
			Segment segment)
		throws NullPointerException {

		if (segment == null)
			throw new NullPointerException("Cannot prepend a null valued segment to the list of segments of this operation group.");
		
		StrongReferenceVector.prepend(inputSegments, segment);
	}

	@MediaListRemoveAt("InputSegments")
	public void removeInputSegmentAt(
			int index)
		throws IndexOutOfBoundsException {

		StrongReferenceVector.remove(inputSegments, index);	
	}

	@MediaPropertyClear("InputSegments")
	public void clearInputSegments() {
		
		inputSegments = Collections.synchronizedList(new Vector<Segment>());
	}
	
	@MediaProperty(uuid1 = 0x0530050c, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "BypassOverride",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0B04,
			symbol = "BypassOverride")
	public int getBypassOverride()
			throws PropertyNotPresentException {

		if (bypassOverride == null)
			throw new PropertyNotPresentException("The optional bypass override property is not present for this operation group.");
		
		return bypassOverride;
	}

	@MediaPropertySetter("BypassOverride")
	public void setBypassOverride(
			Integer bypassOverride) 
		throws IllegalArgumentException {

		if (bypassOverride == null) {
			this.bypassOverride = null;
			return;
		}

		if (bypassOverride < 1)
			throw new IllegalArgumentException("The bypass override value must be a 1-based index value.");
		
		this.bypassOverride = bypassOverride;
	}

	@MediaProperty(uuid1 = 0x05300506, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Operation",
			aliases = { "OperationDefinition" },
			typeName = "OperationDefinitionWeakReference", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0B01,
			symbol = "Operation")
	public OperationDefinition getOperationDefinition() {

		return operation.getTarget();
	}

	@MediaPropertySetter("Operation")
	public void setOperationDefinition(
			OperationDefinition operationDefinition)
		throws NullPointerException {

		if (operationDefinition == null)
			throw new NullPointerException("Cannot set the operation of this operation group using a null value.");
		
		this.operation = new WeakReference<OperationDefinition>(operationDefinition);
	}
	
	public final static OperationDefinition initializeOperation() {
		
		return OperationDefinitionImpl.forName("Unknown");
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0206, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Rendering",
			aliases = { "OperationGroupRendering" },
			typeName = "SourceReferenceStrongReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0B05,
			symbol = "Rendering")
	public SourceReferenceSegment getRendering()
			throws PropertyNotPresentException {

		if (rendering == null)
			throw new PropertyNotPresentException("The optional rendering property is not present for this operation group.");
		
		return (SourceReferenceSegment) rendering;
	}

	@MediaPropertySetter("Rendering")
	public void setRendering(
			SourceReferenceSegment sourceReference) {

		if (sourceReference == null) {
			this.rendering = null;
			return;
		}

		this.rendering = sourceReference;
	}

	public boolean isTimeWarp() {

		return operation.getTarget().isTimeWarp();
	}

	public boolean isValidTransitionOperation() {

		if (operation.getTarget().getOperationInputCount() == 2)
			return true; // Should also check for a level parameter.
		else
			return false;
	}

	// Begin - Quantel extensions
	
	private Long effectOffset = null;
	private Stream transitionBlob = null;
	private Long effectLength = null;
	private String effectName = null;
	private Integer effectRenderKey = null;
	private Integer repeatFinteTails = null;
	private Integer effectEnabled = null;
	private Integer repeatRushAttributes = null;
	private PropertyValue effectSourceOffset = null;
	private Stream packBlob = null;
	private String repeatRushID = null;
	private Segment renderKey = null;
	private Segment packRenderSequence = null;
	private Long repeatRushOffset = null;
	private Integer effectSourceAttached = null;
	private Long packOffset = null;
	private PropertyValue effectSourceLength = null;
	
    @MediaProperty(uuid1 = 0x6db9db08, uuid2 = (short) 0x3198, uuid3 = (short) 0x4140,
        uuid4 = { (byte) 0x93, (byte) 0x02, (byte) 0x0a, (byte) 0xb3, (byte) 0xb0, (byte) 0x15, (byte) 0x17, (byte) 0xbf },
        definedName = "Effect offset",
        symbol = "Effect_offset",
        aliases = { "Effect_offset" },
        typeName = "Int64",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int64 long getEffectOffset()
		throws PropertyNotPresentException {
		
		if (effectOffset == null)
			throw new PropertyNotPresentException("The optional effect offset property is not present for this Quantel operation group.");
		
		return effectOffset;
	}
	
	@MediaPropertySetter("Effect offset")
	public void setEffectOffset(
			@Int64 Long effectOffset) {
		
		this.effectOffset = effectOffset;
	}
			
    @MediaProperty(uuid1 = 0x2d13a614, uuid2 = (short) 0xf8c9, uuid3 = (short) 0x4810,
        uuid4 = { (byte) 0x93, (byte) 0x4d, (byte) 0x4d, (byte) 0x9e, (byte) 0x20, (byte) 0x00, (byte) 0x68, (byte) 0x60 },
        definedName = "Transition blob",
        symbol = "Transition_blob",
        aliases = { "Transition_blob" },
        typeName = "Stream",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public Stream getTransitionBlob()
		throws PropertyNotPresentException {
			
		if (transitionBlob == null)
			throw new PropertyNotPresentException("The optional transition blob property is not present for this Quantel operation group.");
			
		return transitionBlob;
	}
	
	@MediaPropertySetter("Transition blob")
	public void setTransitionBlob(
			Stream transitionBlob) {
		
		this.transitionBlob = transitionBlob;
	}
	
    @MediaProperty(uuid1 = 0xe8388115, uuid2 = (short) 0xb50e, uuid3 = (short) 0x4199,
        uuid4 = { (byte) 0x87, (byte) 0x19, (byte) 0xdb, (byte) 0xac, (byte) 0x2f, (byte) 0x35, (byte) 0x78, (byte) 0xbf },
        definedName = "Effect length",
        symbol = "Effect_length",
        aliases = { "Effect_length" },
        typeName = "Int64",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int64 long getEffectLength()
		throws PropertyNotPresentException {
			
		if (effectLength == null)
			throw new PropertyNotPresentException("The optional effect length property is not present for this Quantel operation group.");
			
		return effectLength;
	}
	
	@MediaPropertySetter("Effect length")
	public void setEffectLength(
			@Int64 Long effectLength) {
		
		this.effectLength = effectLength;
	}
	
    @MediaProperty(uuid1 = 0x9a654522, uuid2 = (short) 0x1455, uuid3 = (short) 0x4026,
        uuid4 = { (byte) 0x8f, (byte) 0xd1, (byte) 0x55, (byte) 0x26, (byte) 0xad, (byte) 0x62, (byte) 0xdf, (byte) 0x5a },
        definedName = "Effect name",
        symbol = "Effect_name",
        aliases = { "Effect_name" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public String getEffectName()
		throws PropertyNotPresentException {
			
		if (effectName == null)
			throw new PropertyNotPresentException("The optional effect name property is not present for this Quantel operation group.");
			
		return effectName;
	}
	
	@MediaPropertySetter("Effect name")
	public void setEffectName(
			String effectName) {
		
		this.effectName = effectName;
	}
	
    @MediaProperty(uuid1 = 0xb5be0e26, uuid2 = (short) 0x3c0c, uuid3 = (short) 0x4f29,
        uuid4 = { (byte) 0xa3, (byte) 0x2c, (byte) 0x57, (byte) 0x61, (byte) 0x4c, (byte) 0x3e, (byte) 0x6d, (byte) 0x15 },
        definedName = "Effect render key",
        symbol = "Effect_render_key",
        aliases = { "Effect_render_key" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getEffectRenderKey()
		throws PropertyNotPresentException {
			
		if (effectRenderKey == null)
			throw new PropertyNotPresentException("The optional effect render key property is not present for this Quantel operation group.");
			
		return effectRenderKey;
	}
	
	@MediaPropertySetter("Effect render key")
	public void setEffectRenderKey(
			@Int32 Integer effectRenderKey) {
		
		this.effectRenderKey = effectRenderKey;
	}
	
    @MediaProperty(uuid1 = 0x82f74f3a, uuid2 = (short) 0x2bd0, uuid3 = (short) 0x4636,
        uuid4 = { (byte) 0xbe, (byte) 0xf5, (byte) 0xac, (byte) 0x31, (byte) 0x73, (byte) 0x6b, (byte) 0x1f, (byte) 0x11 },
        definedName = "Repeat finite tails",
        symbol = "Repeat_finite_tails",
        aliases = { "Repeat_finite_tails" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getRepeatFiniteTails()
		throws PropertyNotPresentException {
			
		if (repeatFinteTails == null)
			throw new PropertyNotPresentException("The optional repeat finite tails property is not present for this Quantel operation group.");
			
		return repeatFinteTails;
	}
	
	@MediaPropertySetter("Repeat finite tails")
	public void setRepeatFiniteTails(
			@Int32 Integer repeatFiniteTails) {
		
		this.repeatFinteTails = repeatFiniteTails;
	}
	
    @MediaProperty(uuid1 = 0x3d53b350, uuid2 = (short) 0x6591, uuid3 = (short) 0x4cd1,
        uuid4 = { (byte) 0x90, (byte) 0xde, (byte) 0xf2, (byte) 0xd1, (byte) 0x75, (byte) 0xd2, (byte) 0xe0, (byte) 0xb8 },
        definedName = "Effect enabled",
        symbol = "Effect_enabled",
        aliases = { "Effect_enabled" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getEffectEnabled()
		throws PropertyNotPresentException {
			
		if (effectEnabled == null)
			throw new PropertyNotPresentException("The optional effect enabled property is not present for this Quantel operation group.");
			
		return effectEnabled;
	}
	
	@MediaPropertySetter("Effect enabled")
	public void setEffectEnabled(
			@Int32 Integer effectEnabled) {
		
		this.effectEnabled = effectEnabled;
	}
	
    @MediaProperty(uuid1 = 0x64979555, uuid2 = (short) 0x3210, uuid3 = (short) 0x4839,
        uuid4 = { (byte) 0xbc, (byte) 0xcf, (byte) 0x53, (byte) 0x47, (byte) 0xc2, (byte) 0xce, (byte) 0xb4, (byte) 0xf5 },
        definedName = "Repeat rush attributes",
        symbol = "Repeat_rush_attributes",
        aliases = { "Repeat_rush_attributes" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getRepeatRushAttributes()
		throws PropertyNotPresentException {
			
		if (repeatRushAttributes == null)
			throw new PropertyNotPresentException("The optional repeat rush attributes property is not present for this Quantel operation group.");
			
		return repeatRushAttributes;
	}
	
	@MediaPropertySetter("Repeat rush attributes")
	public void setRepeatRushAttributes(
			@Int32 Integer repeatRushAttributes) {
		
		this.repeatRushAttributes = repeatRushAttributes;
	}
	
    @MediaProperty(uuid1 = 0x5b158c66, uuid2 = (short) 0xc1e4, uuid3 = (short) 0x4d09,
        uuid4 = { (byte) 0xad, (byte) 0xed, (byte) 0xdd, (byte) 0x05, (byte) 0x06, (byte) 0x01, (byte) 0x29, (byte) 0x18 },
        definedName = "Effect source offset",
        symbol = "Effect_source_offset",
        aliases = { "Effect_source_offset" },
        typeName = "Indirect",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public PropertyValue getEffectSourceOffset()
		throws PropertyNotPresentException {
			
		if (effectSourceOffset == null)
			throw new PropertyNotPresentException("The optional effect source offset property is not present for this Quantel operation group.");
			
		return effectSourceOffset;
	}
	
	@MediaPropertySetter("Effect source offset")
	public void setEffectSourceOffset(
			PropertyValue effectSourceOffset) {
		
		this.effectSourceOffset = effectSourceOffset;
	}
	
    @MediaProperty(uuid1 = 0x4963c16a, uuid2 = (short) 0xec04, uuid3 = (short) 0x4275,
        uuid4 = { (byte) 0xb0, (byte) 0xc0, (byte) 0x98, (byte) 0x13, (byte) 0xb0, (byte) 0xf8, (byte) 0xbb, (byte) 0x74 },
        definedName = "Pack blob",
        symbol = "Pack_blob",
        aliases = { "Pack_blob" },
        typeName = "Stream",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public Stream getPackBlob()
		throws PropertyNotPresentException {
			
		if (packBlob == null)
			throw new PropertyNotPresentException("The optional pack blob property is not present for this Quantel operation group.");
			
		return packBlob;
	}
	
	@MediaPropertySetter("Pack blob")
	public void setPackBlob(
			Stream packBlob) {
		
		this.packBlob = packBlob;
	}
	
    @MediaProperty(uuid1 = 0x900a60a0, uuid2 = (short) 0x44d8, uuid3 = (short) 0x45a5,
        uuid4 = { (byte) 0x89, (byte) 0x33, (byte) 0x8c, (byte) 0xfe, (byte) 0x58, (byte) 0x10, (byte) 0x84, (byte) 0x20 },
        definedName = "Repeat rush id",
        symbol = "Repeat_rush_id",
        aliases = { "Repeat_rush_id" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public String getRepeatRushID()
		throws PropertyNotPresentException {
			
		if (repeatRushID == null)
			throw new PropertyNotPresentException("The optional repeat rush ID property is not present for this Quantel operation group.");
			
		return repeatRushID;
	}
	
	@MediaPropertySetter("Repeat rush id")
	public void setRepeatRushID(
			String repeatRushID) {
		
		this.repeatRushID = repeatRushID;
	}
	
    @MediaProperty(uuid1 = 0x53f6cea7, uuid2 = (short) 0xa563, uuid3 = (short) 0x448c,
        uuid4 = { (byte) 0x87, (byte) 0x22, (byte) 0x1e, (byte) 0x8a, (byte) 0xc1, (byte) 0xef, (byte) 0xc5, (byte) 0xac },
        definedName = "Render key",
        symbol = "Render_key",
        aliases = { "Render_key" },
        typeName = "SegmentStrongReference",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public Segment getRenderKey()
		throws PropertyNotPresentException {
			
		if (renderKey == null)
			throw new PropertyNotPresentException("The optional render key property is not present for this Quantel operation group.");
			
		return renderKey;
	}
	
	@MediaPropertySetter("Render key")
	public void setRenderKey(
			Segment renderKey) {
		
		this.renderKey = renderKey;
	}
	
    @MediaProperty(uuid1 = 0x5f9074bd, uuid2 = (short) 0x0ddf, uuid3 = (short) 0x45c3,
        uuid4 = { (byte) 0xbc, (byte) 0x0d, (byte) 0x5f, (byte) 0x60, (byte) 0xaf, (byte) 0xd6, (byte) 0x15, (byte) 0xb3 },
        definedName = "Pack render sequence",
        symbol = "Pack_render_sequence",
        aliases = { "Pack_render_sequence" },
        typeName = "SegmentStrongReference",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public Segment getPackRenderSequence()
		throws PropertyNotPresentException {
			
		if (packRenderSequence == null)
			throw new PropertyNotPresentException("The optional pack render sequence property is not present for this Quantel operation group.");
			
		return packRenderSequence;
	}
	
	@MediaPropertySetter("Pack render sequence")
	public void setPackRenderSequence(
			Segment packRenderSequence) {
		
		this.packRenderSequence = packRenderSequence;
	}
	
    @MediaProperty(uuid1 = 0xab6091cc, uuid2 = (short) 0xc6be, uuid3 = (short) 0x448a,
        uuid4 = { (byte) 0xbd, (byte) 0x69, (byte) 0x6e, (byte) 0x89, (byte) 0x37, (byte) 0x55, (byte) 0x36, (byte) 0x7a },
        definedName = "Repeat rush offset",
        symbol = "Repeat_rush_offset",
        aliases = { "Repeat_rush_offset" },
        typeName = "Int64",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int64 long getRepeatRushOffset()
		throws PropertyNotPresentException {
			
		if (repeatRushOffset == null)
			throw new PropertyNotPresentException("The optional repeat rush offset property is not present for this Quantel operation group.");
			
		return repeatRushOffset;
	}
	
	@MediaPropertySetter("Repeat rush offset")
	public void setRepeatRushOffset(
			@Int64 Long repeatRushOffset) {
		
		this.repeatRushOffset = repeatRushOffset;
	}
	
    @MediaProperty(uuid1 = 0xef2cbed2, uuid2 = (short) 0x52d4, uuid3 = (short) 0x4ad8,
        uuid4 = { (byte) 0x98, (byte) 0x16, (byte) 0x8f, (byte) 0xe7, (byte) 0xe9, (byte) 0x51, (byte) 0x47, (byte) 0x68 },
        definedName = "Effect source attached",
        symbol = "Effect_source_attached",
        aliases = { "Effect_source_attached" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getEffectSourceAttached()
		throws PropertyNotPresentException {
			
		if (effectSourceAttached == null)
			throw new PropertyNotPresentException("The optional effect source attached property is not present for this Quantel operation group.");
			
		return effectSourceAttached;
	}
	
	@MediaPropertySetter("Effect source attached")
	public void setEffectSourceAttached(
			@Int32 Integer effectSourceAttached) {
		
		this.effectSourceAttached = effectSourceAttached;
	}
	
    @MediaProperty(uuid1 = 0xca976edb, uuid2 = (short) 0x9db0, uuid3 = (short) 0x4e46,
        uuid4 = { (byte) 0xa4, (byte) 0xd2, (byte) 0x2d, (byte) 0x4e, (byte) 0x45, (byte) 0x03, (byte) 0xf0, (byte) 0x20 },
        definedName = "Pack offset",
        symbol = "Pack_offset",
        aliases = { "Pack_offset" },
        typeName = "Int64",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int64 long getPackOffset()
		throws PropertyNotPresentException {
			
		if (packOffset == null)
			throw new PropertyNotPresentException("The optional pack offset property is not present for this Quantel operation group.");
			
		return packOffset;
	}
	
	@MediaPropertySetter("Pack offset")
	public void setPackOffset(
			@Int64 Long packOffset) {
		
		this.packOffset = packOffset;
	}
	
    @MediaProperty(uuid1 = 0x4d6829fe, uuid2 = (short) 0x3339, uuid3 = (short) 0x4070,
        uuid4 = { (byte) 0xa1, (byte) 0x7b, (byte) 0x83, (byte) 0x18, (byte) 0x5f, (byte) 0x0c, (byte) 0x3d, (byte) 0x8a },
        definedName = "Effect source length",
        symbol = "Effect_source_length",
        aliases = { "Effect_source_length" },
        typeName = "Indirect",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public PropertyValue getEffectSourceLength()
		throws PropertyNotPresentException {
			
		if (effectSourceLength == null)
			throw new PropertyNotPresentException("The optional effect source length property is not present for this Quantel operation group.");
			
		return effectSourceLength;
	}

	@MediaPropertySetter("Effect source length")
	public void setEffectSourceLength(
			PropertyValue effectSourceLength) {
		
		this.effectSourceLength = effectSourceLength;
	}
	
	// End - Quantel extensions
	
	public OperationGroup clone() {
		
		return (OperationGroup) super.clone();
 	}
}
