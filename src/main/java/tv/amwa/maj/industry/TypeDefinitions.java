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
 * $Log: TypeDefinitions.java,v $
 * Revision 1.16  2011/10/05 17:14:25  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.15  2011/07/27 12:25:44  vizigoth
 * Fixed import warning messages.
 *
 * Revision 1.14  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.13  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.12  2011/01/05 13:08:07  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.11  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.10  2010/12/15 18:59:20  vizigoth
 * Fixed spelling error in type definition name.
 *
 * Revision 1.9  2010/11/18 10:48:10  vizigoth
 * Fixed spelling errors and improved match with meta dictionary.
 *
 * Revision 1.8  2010/07/20 14:53:34  vizigoth
 * Added target sets for PackageWeakRef and PropertyDefinitionWeakRef generation of AAF files.
 *
 * Revision 1.7  2010/06/18 09:36:45  vizigoth
 * Version was wrongly based on UInt8 instead of Int8. Fixed to match metadictionary.
 *
 * Revision 1.6  2010/06/16 14:53:21  vizigoth
 * Fixes to match meta dictionary ... UTF16String base type is character and PackagID member types.
 *
 * Revision 1.5  2010/04/16 15:23:50  vizigoth
 * Added target sets to weak reference types.
 *
 * Revision 1.4  2010/03/19 09:52:51  vizigoth
 * Fixed product release type key.
 *
 * Revision 1.3  2010/03/01 11:42:49  vizigoth
 * Fixed interaction with ExtendibleEnumerationWareshouse to make this type truly dynamic and avoid replication between two different sets of static collections.
 *
 * Revision 1.2  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 */

package tv.amwa.maj.industry;

import java.util.Calendar;

import tv.amwa.maj.enumeration.AlphaTransparencyType;
import tv.amwa.maj.enumeration.AuxBitsModeType;
import tv.amwa.maj.enumeration.ChannelStatusModeType;
import tv.amwa.maj.enumeration.ColorSitingType;
import tv.amwa.maj.enumeration.ContentScanningType;
import tv.amwa.maj.enumeration.EdgeType;
import tv.amwa.maj.enumeration.EditHintType;
import tv.amwa.maj.enumeration.ElectroSpatialFormulation;
import tv.amwa.maj.enumeration.EmphasisType;
import tv.amwa.maj.enumeration.FadeType;
import tv.amwa.maj.enumeration.FieldNumber;
import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.enumeration.LayoutType;
import tv.amwa.maj.enumeration.MaterialType;
import tv.amwa.maj.enumeration.ProductReleaseType;
import tv.amwa.maj.enumeration.PulldownDirectionType;
import tv.amwa.maj.enumeration.PulldownKindType;
import tv.amwa.maj.enumeration.RGBAComponentKind;
import tv.amwa.maj.enumeration.ReferenceType;
import tv.amwa.maj.enumeration.ScanningDirectionType;
import tv.amwa.maj.enumeration.SignalStandardType;
import tv.amwa.maj.enumeration.TCSource;
import tv.amwa.maj.enumeration.TapeCaseType;
import tv.amwa.maj.enumeration.TapeFormatType;
import tv.amwa.maj.enumeration.UserDataModeType;
import tv.amwa.maj.enumeration.VideoSignalType;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionCharacter;
import tv.amwa.maj.meta.TypeDefinitionEnumeration;
import tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration;
import tv.amwa.maj.meta.TypeDefinitionFixedArray;
import tv.amwa.maj.meta.TypeDefinitionIndirect;
import tv.amwa.maj.meta.TypeDefinitionInteger;
import tv.amwa.maj.meta.TypeDefinitionOpaque;
import tv.amwa.maj.meta.TypeDefinitionRecord;
import tv.amwa.maj.meta.TypeDefinitionRename;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionStream;
import tv.amwa.maj.meta.TypeDefinitionString;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.meta.TypeDefinitionWeakObjectReference;
import tv.amwa.maj.meta.impl.ClassDefinitionImpl;
import tv.amwa.maj.meta.impl.ExtensionSchemeImpl;
import tv.amwa.maj.meta.impl.MetaDefinitionImpl;
import tv.amwa.maj.meta.impl.PropertyDefinitionImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionCharacterImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionEnumerationImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionExtendibleEnumerationImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionFixedArrayImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionIndirectImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionIntegerImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionOpaqueImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionRecordImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionRenameImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionSetImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionStreamImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionStringImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionStrongObjectReferenceImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionVariableArrayImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionWeakObjectReferenceImpl;
import tv.amwa.maj.model.Component;
import tv.amwa.maj.model.DefinitionObject;
import tv.amwa.maj.model.EssenceData;
import tv.amwa.maj.model.impl.AAFFileDescriptorImpl;
import tv.amwa.maj.model.impl.CodecDefinitionImpl;
import tv.amwa.maj.model.impl.ComponentImpl;
import tv.amwa.maj.model.impl.ContainerDefinitionImpl;
import tv.amwa.maj.model.impl.ContentStorageImpl;
import tv.amwa.maj.model.impl.ControlPointImpl;
import tv.amwa.maj.model.impl.DataDefinitionImpl;
import tv.amwa.maj.model.impl.DescriptiveFrameworkImpl;
import tv.amwa.maj.model.impl.DescriptiveObjectImpl;
import tv.amwa.maj.model.impl.DictionaryImpl;
import tv.amwa.maj.model.impl.EssenceDataImpl;
import tv.amwa.maj.model.impl.EssenceDescriptorImpl;
import tv.amwa.maj.model.impl.IdentificationImpl;
import tv.amwa.maj.model.impl.InterpolationDefinitionImpl;
import tv.amwa.maj.model.impl.KLVDataDefinitionImpl;
import tv.amwa.maj.model.impl.KLVDataImpl;
import tv.amwa.maj.model.impl.LocatorImpl;
import tv.amwa.maj.model.impl.NetworkLocatorImpl;
import tv.amwa.maj.model.impl.OperationDefinitionImpl;
import tv.amwa.maj.model.impl.OperationGroupImpl;
import tv.amwa.maj.model.impl.PackageImpl;
import tv.amwa.maj.model.impl.PackageMarkerImpl;
import tv.amwa.maj.model.impl.ParameterDefinitionImpl;
import tv.amwa.maj.model.impl.ParameterImpl;
import tv.amwa.maj.model.impl.PluginDefinitionImpl;
import tv.amwa.maj.model.impl.PrefaceImpl;
import tv.amwa.maj.model.impl.RIFFChunkImpl;
import tv.amwa.maj.model.impl.SegmentImpl;
import tv.amwa.maj.model.impl.SourceClipImpl;
import tv.amwa.maj.model.impl.SourceReferenceSegmentImpl;
import tv.amwa.maj.model.impl.SubDescriptorImpl;
import tv.amwa.maj.model.impl.TaggedValueDefinitionImpl;
import tv.amwa.maj.model.impl.TaggedValueImpl;
import tv.amwa.maj.model.impl.TrackImpl;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.DateStruct;
import tv.amwa.maj.record.InstanceNumberGeneration;
import tv.amwa.maj.record.MaterialNumberGeneration;
import tv.amwa.maj.record.TimeStruct;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>All AAF-specified type definitions as static constants in an interface. Implement or 
 * reference this interface to gain access to the constants that it defines.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.meta.TypeDefinition
 * @see Warehouse#lookForType(String)
 * @see tv.amwa.maj.meta.PropertyDefinition#getTypeDefinition()
 * @see MediaProperty#typeName()
 */
public interface TypeDefinitions {

	/**
	 * <p>Defines the type of a unsigned 8-bit integer value.</p>
	 * 
	 * @see #UInt8Array
	 * @see #UInt8Array12
	 * @see #UInt8Array8
	 * @see tv.amwa.maj.integer.UInt8
	 */
	public final static TypeDefinitionInteger UInt8 = new TypeDefinitionIntegerImpl(
			new AUIDImpl(0x01010100, (short) 0x0000, (short) 0x0000, new byte[] {
					0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }), "UInt8",
			(byte) 1, false);

	/**
	 * <p>Defines the type of an unsigned 16-bit integer value.</p>
	 * 
	 * @see tv.amwa.maj.integer.UInt16
	 */
	public final static TypeDefinitionInteger UInt16 = new TypeDefinitionIntegerImpl(
			new AUIDImpl(0x01010200, (short) 0x0000, (short) 0x0000, new byte[] {
					0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"UInt16", (byte) 2, false);

	/**
	 * <p>Defines the type of an unsigned 32-bit integer value.</p>
	 * 
	 * @see #UInt32Array
	 * @see #UInt32Set
	 * @see tv.amwa.maj.integer.UInt32 
	 */
	public final static TypeDefinitionInteger UInt32 = new TypeDefinitionIntegerImpl(
			new AUIDImpl(0x01010300, (short) 0x0000, (short) 0x0000, new byte[] {
					0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"UInt32", (byte) 4, false);

	/**
	 * <p>Defines the type of an unsigned 64-bit integer value.</p>
	 * 
	 * @see tv.amwa.maj.integer.UInt64
	 */
	public final static TypeDefinitionInteger UInt64 = new TypeDefinitionIntegerImpl(
			new AUIDImpl(0x01010400, (short) 0x0000, (short) 0x0000, new byte[] {
					0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"UInt64", (byte) 8, false);

	/**
	 * <p>Defines the type of an 8-bit 2's complement signed integer value.</p>
	 * 
	 * @see tv.amwa.maj.integer.Int8
	 */
	public final static TypeDefinitionInteger Int8 = new TypeDefinitionIntegerImpl(
			new AUIDImpl(0x01010500, (short) 0x0000, (short) 0x0000, new byte[] {
					0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }), "Int8",
			(byte) 1, true);

	/**
	 * <p>Defines the type of a 16-bit 2's compliment signed integer value.</p>
	 * 
	 * @see tv.amwa.maj.integer.Int16
	 */
	public final static TypeDefinitionInteger Int16 = new TypeDefinitionIntegerImpl(
			new AUIDImpl(0x01010600, (short) 0x0000, (short) 0x0000, new byte[] {
					0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }), "Int16",
			(byte) 2, true);

	/**
	 * <p>Defines the type of a 32-bit 2's compliment signed integer value.</p>
	 * 
	 * @see #Int32Array
	 * @see #JPEGTableIDType
	 * @see #PhaseFrameType
	 * @see tv.amwa.maj.integer.Int32
	 */
	public final static TypeDefinitionInteger Int32 = new TypeDefinitionIntegerImpl(
			new AUIDImpl(0x01010700, (short) 0x0000, (short) 0x0000, new byte[] {
					0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }), "Int32",
			(byte) 4, true);

	/**
	 * <p>Defines the type of a 64-bit 2's compliment signed integer value.</p>
	 * 
	 * @see #Int64Array
	 * @see #PositionType
	 * @see #LengthType
	 * @see tv.amwa.maj.integer.Int64
	 */
	public final static TypeDefinitionInteger Int64 = new TypeDefinitionIntegerImpl(
			new AUIDImpl(0x01010800, (short) 0x0000, (short) 0x0000, new byte[] {
					0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }), "Int64",
			(byte) 8, true);

	/**
	 * <p>Defines the type of Boolean values that are either <code>true</code> or
	 * <code>false</code>.</p>
	 * 
	 * <p>Note that AAF defines Boolean values by an enumeration type whereas MAJ follows
	 * the Java pattern of using a primitive type. All internal MAJ code has been implemented to
	 * expect Java <code>boolean</code> values representing these values. Other applications
	 * may need to be aware of this, especially when walking a tree of AAF data switched by
	 * type category.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.Boolean
	 * @see tv.amwa.maj.misctype.Bool
	 */
	public final static TypeDefinitionEnumeration Boolean = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x01040100, (short) 0x0000, (short) 0x0000, new byte[] {
					0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"Boolean", tv.amwa.maj.enumeration.Boolean.class,
			TypeDefinitions.UInt8);

	/**
	 * <p>Defines the type a single character value. AAF character values are normally represented
	 * by at least two bytes with a Unicode representation encoded by UTF-16.</p>
	 * 
	 * <p>The AAF character type is equivalent to the Java <code>char</code> primitive type and a value
	 * of {@link java.lang.Character}.</p>
	 * 
	 * @see tv.amwa.maj.meta.TypeDefinitionCharacter
	 * @see #UTF16String
	 */
	public final static TypeDefinitionCharacter Character = new TypeDefinitionCharacterImpl(
			new AUIDImpl(0x01100100, (short) 0x0000, (short) 0x0000, 
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"Character");
	
	/**
	 * <p>Defines the type of an enumerated value describing the level of a product release, e.g. debug,
	 * beta, released.</p>
	 * 
	 * @see #ProductVersionType
	 * @see tv.amwa.maj.enumeration.ProductReleaseType
	 */
	public final static TypeDefinitionEnumeration ProductReleaseType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010101, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"ProductReleaseType",
			ProductReleaseType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the format of a tape, e.g. Betacam,
	 * VHS.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.TapeFormatType
	 * @see tv.amwa.maj.model.TapeDescriptor#getTapeFormat()
	 */
	public final static TypeDefinitionEnumeration TapeFormatType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010102, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"TapeFormatType",
			TapeFormatType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the video signal on a tape, e.g.
	 * NTSC, PAL.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.VideoSignalType
	 * @see tv.amwa.maj.model.TapeDescriptor#getSignalType()
	 */
	public final static TypeDefinitionEnumeration VideoSignalType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010103, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"VideoSignalType",
			VideoSignalType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the physical size of a tape, e.g.
	 * compact cassette, 8mm, DAT.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.TapeCaseType
	 * @see tv.amwa.maj.model.TapeDescriptor#getTapeFormFactor()
	 */
	public final static TypeDefinitionEnumeration TapeCaseType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010104, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"TapeCaseType",
			TapeCaseType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes color, indicating where colored pixels 
	 * are located with respect to their associated luminance value. Examples include 
	 * {@linkplain tv.amwa.maj.enumeration.ColorSitingType#Averaging averaging} and 
	 * {@linkplain tv.amwa.maj.enumeration.ColorSitingType#Rec601 rec601}.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.ColorSitingType
	 * @see tv.amwa.maj.model.CDCIDescriptor#getColorSiting()
	 */
	public final static TypeDefinitionEnumeration ColorSitingType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010105, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"ColorSitingType",
			ColorSitingType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describe hints to be used when editing 
	 * {@linkplain tv.amwa.maj.model.ControlPoint control points}. The hint is used to describe 
	 * how to alter a control point if an {@linkplain tv.amwa.maj.model.OperationGroup operation group} 
	 * that contains it is itself altered in size.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.EditHintType
	 * @see tv.amwa.maj.model.ControlPoint#getEditHint()
	 */
	public final static TypeDefinitionEnumeration EditHintType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010106, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"EditHintType",
			EditHintType.class,
			TypeDefinitions.UInt8);

	/**
	 * <p>Defines the type of an enumerated value that describes the kind of an audio fade.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.FadeType
	 * @see tv.amwa.maj.union.Fade#getFadeInType()
	 * @see tv.amwa.maj.union.Fade#getFadeOutType()
	 * @see tv.amwa.maj.union.DefaultFade#getFadeType()
	 * @see tv.amwa.maj.model.CompositionPackage#getDefaultFadeType()
	 */
	public final static TypeDefinitionEnumeration FadeType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010107, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"FadeType",
			FadeType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the kind of a 
	 * {@linkplain tv.amwa.maj.model.TimecodeStream timecode stream}.</p>
	 * 
	 * @see #TimecodeSource
	 * @see tv.amwa.maj.enumeration.TCSource
	 * @see tv.amwa.maj.model.TimecodeStream#getTimecodeSource()
	 */
	public final static TypeDefinitionEnumeration TCSource = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010109, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"TCSource",
			TCSource.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the kind of a 
	 * {@linkplain tv.amwa.maj.model.TimecodeStream timecode stream}.</p>
	 * 
	 * @see #TCSource
	 * @see tv.amwa.maj.enumeration.TCSource
	 * @see tv.amwa.maj.model.TimecodeStream#getTimecodeSource()
	 */
	public final static TypeDefinitionEnumeration TimecodeSource = TCSource;
	
	/**
	 * <p>Defines the type of an enumerated value that describes whether a 
	 * {@linkplain tv.amwa.maj.model.Pulldown pulldown operation} is converting 
     * from tape to film speed or from film to tape speed.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.PulldownDirectionType
	 * @see tv.amwa.maj.model.Pulldown#getPulldownDirection()
	 */
	public final static TypeDefinitionEnumeration PulldownDirectionType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x0201010a, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"PulldownDirectionType",
			PulldownDirectionType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes whether a 
	 * {@linkplain tv.amwa.maj.model.Pulldown pulldown operation} is converting 
	 * from nominally 30&nbsp;Hz or 25&nbsp;Hz video frame rate and whether frames are dropped or the video 
	 * is played at another speed.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.PulldownKindType
	 * @see tv.amwa.maj.model.Pulldown#getPulldownKind()
	 */
	public final static TypeDefinitionEnumeration PulldownKindType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x0201010b, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"PulldownKindType",
			PulldownKindType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the kind of film edge code, e.g.
	 * {@linkplain tv.amwa.maj.enumeration.EdgeType#Keycode keycode}, 
	 * {@linkplain tv.amwa.maj.enumeration.EdgeType#Edgenum4 edgenum4}.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.EdgeType
     * @see tv.amwa.maj.record.EdgeCodeValue#getEdgeCodeFormat()
     * @see tv.amwa.maj.model.EdgeCodeSegment#getEdgeCodeFormat()
     */
	public final static TypeDefinitionEnumeration EdgeType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x0201010c, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"EdgeType",
			EdgeType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the format of a film, e.g. 16mm, 35mm.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.FilmType
	 * @see tv.amwa.maj.model.FilmDescriptor#getFilmFormat()
	 */
	public final static TypeDefinitionEnumeration FilmType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x0201010d, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"FilmType",
			FilmType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the color or function of a 
	 * component within a pixel, for example whether the component a red level, palette index etc..</p>
	 * 
	 * @see #RGBAComponent
	 * @see #RGBALayout
	 * @see tv.amwa.maj.enumeration.RGBAComponentKind
	 * @see tv.amwa.maj.model.RGBADescriptor#getPixelLayout()
	 * @see tv.amwa.maj.model.RGBADescriptor#getPaletteLayout()
	 */
	public final static TypeDefinitionEnumeration RGBAComponentKind = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x0201010e, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"RGBAComponentKind",
			RGBAComponentKind.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes a type of reference.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.ReferenceType
	 */
	// This data type is defined for AAF but is not currently used
	public final static TypeDefinitionEnumeration ReferenceType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x0201010f, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"ReferenceType",
			ReferenceType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes whether the minimum alpha value or 
	 * the maximum alpha value represents transparency.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.AlphaTransparencyType
	 * @see tv.amwa.maj.model.PictureDescriptor#getAlphaTransparency()
	 */
	public final static TypeDefinitionEnumeration AlphaTransparencyType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010120, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"AlphaTransparencyType",
			AlphaTransparencyType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes whether field dominance is 
	 * the first or second frame of an interlaced image.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.FieldNumber
	 * @see tv.amwa.maj.model.PictureDescriptor#getFieldDominance()
	 */
	public final static TypeDefinitionEnumeration FieldNumber = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010121, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"FieldNumber",
			FieldNumber.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the electro-spatial formulation of
	 * sound, e.g. single channel, stereophonic.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.ElectroSpatialFormulation
	 * @see tv.amwa.maj.model.SoundDescriptor#getElectrospatialFormulation()
	 */
	public final static TypeDefinitionEnumeration ElectroSpatialFormulation = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010122, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"ElectroSpatialFormulation",
			ElectroSpatialFormulation.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes an underlying signal standard used 
	 * to define the raster.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.SignalStandardType
	 * @see tv.amwa.maj.model.PictureDescriptor#getSignalStandard()
	 */
	public final static TypeDefinitionEnumeration SignalStandardType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010127, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"SignalStandardType",
			SignalStandardType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the scanning direction of an image, 
	 * e.g. left-to-right top-to-bottom.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.ScanningDirectionType
	 * @see tv.amwa.maj.model.RGBADescriptor#getScanningDirection()
	 */
	public final static TypeDefinitionEnumeration ScanningDirectionType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010128, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"ScanningDirectionType",
			ScanningDirectionType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes whether all the data for a complete 
	 * sample is in one frame or is split into more than one field, e.g. full frame, separate fields.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.LayoutType
	 * @see tv.amwa.maj.model.PictureDescriptor#getFrameLayout()
	 */
	public final static TypeDefinitionEnumeration LayoutType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010108, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"LayoutType",
			LayoutType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the encoded audio signal 
	 * pre-emphasis for sound data.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.EmphasisType
	 * @see tv.amwa.maj.model.AES3PCMDescriptor#getEmphasis()
	 */
	public final static TypeDefinitionEnumeration EmphasisType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010123, (short) 0x0000, (short) 0x0000,
					new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"EmphasisType",
			EmphasisType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the 3&nbsp;bit auxiliary bits 
	 * mode of audio essence stored in the AES/EBU audio file format.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.AuxBitsModeType
	 * @see tv.amwa.maj.model.AES3PCMDescriptor#getAuxBitsMode()
	 */
	public final static TypeDefinitionEnumeration AuxBitsModeType = new TypeDefinitionEnumerationImpl(
			new AUIDImpl(0x02010124, (short) 0x0000, (short) 0x0000,
					new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"AuxBitsModeType",
			AuxBitsModeType.class,
			TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes how channel status data is encoded 
	 * as part of a {@linkplain tv.amwa.maj.model.AES3PCMDescriptor AES3 PCM descriptor}.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.ChannelStatusModeType
	 * @see tv.amwa.maj.model.AES3PCMDescriptor#getChannelStatusMode()
	 */
	public final static TypeDefinitionEnumeration ChannelStatusModeType = 
		new TypeDefinitionEnumerationImpl(
				new AUIDImpl(0x02010125, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ChannelStatusModeType",
				ChannelStatusModeType.class,
				TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that describes the kind of the 4&nbsp;bits of channel 
	 * status that is used for user data related to audio essence stored in a AES/EBU audio file.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.UserDataModeType
	 * @see tv.amwa.maj.model.AES3PCMDescriptor#getUserDataMode()
	 */
	public final static TypeDefinitionEnumeration UserDataModeType =
		new TypeDefinitionEnumerationImpl(
				new AUIDImpl(0x02010126, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"UserDataModeType",
				UserDataModeType.class,
				TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of an enumerated value that, for MPEG coded content, says if the scanning type 
	 * of the underlying coded content is known and, if it is, what kind of scanning type it is.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.ContentScanningType
	 * @see tv.amwa.maj.model.MPEGVideoDescriptor#getCodedContentScanning()
	 */
	public final static TypeDefinitionEnumeration ContentScanningType =
		new TypeDefinitionEnumerationImpl(
				new AUIDImpl(0x0201012A, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ContentScanningType",
				ContentScanningType.class,
				TypeDefinitions.UInt8);

	/**
	 * <p>Defines the type of an enumerated value that describes the picture type and coding type of AVC
	 * material as part of an {@linkplain tv.amwa.maj.model.AVCSubDescriptor AVC sub descriptor}.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.AVCCodedContentKind
	 * @see tv.amwa.maj.model.AVCSubDescriptor#getAVCCodedContentKind()
	 */
	public final static TypeDefinitionEnumeration AVCCodedContentKind = // FIXME Experimental key ... is this an Enum really?
			new TypeDefinitionEnumerationImpl(
					new AUIDImpl(0x0f01012c, (short) 0x0000, (short) 0x0000,
							new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
					"AVCCodedContentKind",
					tv.amwa.maj.enumeration.AVCCodedContentKind.class,
					TypeDefinitions.UInt8);
	
	/**
	 * <p>Defines the type of extendible enumeration values that describe different categories
	 * of {@linkplain tv.amwa.maj.model.OperationDefinition operation}.</p>
	 * 
	 * @see tv.amwa.maj.constant.OperationCategoryType
	 * @see tv.amwa.maj.model.OperationDefinition#getOperationCategory()
	 */
	public final static TypeDefinitionExtendibleEnumeration OperationCategoryType = 
		new TypeDefinitionExtendibleEnumerationImpl(
				new AUIDImpl(0x02020101, (short) 0x0000, (short) 0x0000,
						new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
				"OperationCategoryType",
				"OperationCategory");

	/**
	 * <p>Defines the type of an extendible enumeration value that describe different kinds of opto-electronic
     * transfer characteristics.</p>
	 * 
	 * @see tv.amwa.maj.constant.TransferCharacteristicType
	 * @see tv.amwa.maj.model.PictureDescriptor#getTransferCharacteristic()
	 */
	public final static TypeDefinitionExtendibleEnumeration TransferCharacteristicType = 
		new TypeDefinitionExtendibleEnumerationImpl(
				new AUIDImpl(0x02020102, (short) 0x0000, (short) 0x0000,
						new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
				"TransferCharacteristicType",
				"TransferCharacteristic");

	/**
	 * <p>Defines the type of an extendible enumeration value that describes different categories
	 * of {@linkplain tv.amwa.maj.model.PluginDefinition plugin}, such as codec, effect or
	 * interpolation.</p>
	 * 
	 * @see tv.amwa.maj.constant.PluginCategoryType
	 * @see tv.amwa.maj.model.PluginDefinition#getCategoryClass()
	 */
	public final static TypeDefinitionExtendibleEnumeration PluginCategoryType = 
		new TypeDefinitionExtendibleEnumerationImpl(
				new AUIDImpl(0x02020103, (short) 0x0000, (short) 0x0000,
						new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
				"PluginCategoryType",
				"PluginCategory");

	/**
	 * <p>Defines the type of an extendible enumeration value that describes the way 
	 * that a {@linkplain tv.amwa.maj.model.Package package} is used.</p>
	 * 
	 * @see tv.amwa.maj.constant.UsageType
	 * @see tv.amwa.maj.model.Package#getPackageUsage()
	 */
	public final static TypeDefinitionExtendibleEnumeration UsageType = 
		new TypeDefinitionExtendibleEnumerationImpl(
				new AUIDImpl(0x02020104, (short) 0x0000, (short) 0x0000,
						new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
				"UsageType",
				"Usage");

	/**
	 * <p>Defines the type of an extendible enumeration value that describes the color primaries used in
     * the representation of images.</p>
	 * 
	 * @see tv.amwa.maj.constant.ColorPrimariesType
	 * @see tv.amwa.maj.model.PictureDescriptor#getColorPrimaries()
	 */
	public final static TypeDefinitionExtendibleEnumeration ColorPrimariesType = 
		new TypeDefinitionExtendibleEnumerationImpl(
				new AUIDImpl(0x02020105, (short) 0x0000, (short) 0x0000,
						new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
				"ColorPrimariesType",
				"ColorPrimaries");

	/**
	 * <p>Defines the type of an extendible enumeration value that describes the coding equations used to convert RGB image 
     * components to component color difference image components.</p>
	 * 
	 * @see tv.amwa.maj.constant.CodingEquationsType
	 * @see tv.amwa.maj.model.PictureDescriptor#getCodingEquations()
	 */
	public final static TypeDefinitionExtendibleEnumeration CodingEquationsType = 
		new TypeDefinitionExtendibleEnumerationImpl(
				new AUIDImpl(0x02020106, (short) 0x0000, (short) 0x0000,
						new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
				"CodingEquationsType",
				"CodingEquations");

	/**
	 * <p>Defines the type for a value for which the type of the value is specified as part
	 * of the value.</p>
	 * 
	 * @see tv.amwa.maj.meta.TypeDefinitionIndirect
	 * @see tv.amwa.maj.model.TaggedValue#getIndirectValue()
	 * @see PropertyValue
	 */
	public final static TypeDefinitionIndirect Indirect = new TypeDefinitionIndirectImpl(
			new AUIDImpl(0x04100300, (short) 0x0000, (short) 0x0000,
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"Indirect");

	/**
	 * <p>Defines the type for a value for which the type is specified in
     * each instance, providing access to data opaque to this API and manipulated directly 
     * by an application through a handle.</p>
	 * 
	 * @see tv.amwa.maj.meta.TypeDefinitionOpaque
	 * @see tv.amwa.maj.model.KLVData
	 */
	public final static TypeDefinitionOpaque Opaque = new TypeDefinitionOpaqueImpl(
			new AUIDImpl(0x04100400, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"Opaque");

	// Alias Position
	/**
	 * <p>Describes an {@link #Int64} value that describes an offset into a 
     * {@linkplain tv.amwa.maj.model.Component component}. The value is measured in the
     * edit units of the component.</p>
	 * 
	 * @see #Int64
	 * @see tv.amwa.maj.misctype.PositionType
	 * @see tv.amwa.maj.model.SourceClip#getStartPosition()
	 */
	public final static TypeDefinitionRename PositionType = new TypeDefinitionRenameImpl(
			new AUIDImpl(0x01012001, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"PositionType",
			Int64);

	// Alias Length
	/**
	 * <p>Defines the type for a value that represents the length of a 
	 * {@linkplain tv.amwa.maj.model.Component component}. The length is measured in the
	 * edit units of the component.</p>
	 * 
	 * @see #Int64
	 * @see tv.amwa.maj.misctype.LengthType
	 * @see tv.amwa.maj.model.Component#getComponentLength()
	 */
	public final static TypeDefinitionRename LengthType = new TypeDefinitionRenameImpl(
			new AUIDImpl(0x01012002, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
					"LengthType",
					Int64);

	/**
	 * <p>Defines a value that identifies the JPEG tables used in compressing TIFF data.</p>
	 * 
	 * @see #Int32
	 * @see tv.amwa.maj.misctype.JPEGTableIDType
	 * @see tv.amwa.maj.model.TIFFDescriptor#getJPEGTableID()
	 */
	public final static TypeDefinitionRename JPEGTableIDType = new TypeDefinitionRenameImpl(
			new AUIDImpl(0x01012003, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"JPEGTableIDType",
			Int32);

	// Alias PhaseFrame
	/**
	 * <p>Defines the type for a value that represents the phase within 
	 * the repeating {@linkplain tv.amwa.maj.model.Pulldown pulldown} pattern of the first frame after 
	 * the pulldown conversion. A value of <code>0</code> specifies that the 
	 * {@linkplain tv.amwa.maj.model.Pulldown pulldown} object starts 
	 * at the beginning of the pulldown pattern.</p>
	 * 
	 * @see #Int32
	 * @see tv.amwa.maj.misctype.PhaseFrameType
	 * @see tv.amwa.maj.model.Pulldown#getPhaseFrame()
	 */
	public final static TypeDefinitionRename PhaseFrameType = new TypeDefinitionRenameImpl(
			new AUIDImpl(0x01012300, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
					"PhaseFrameType",
					Int32);

	/**
	 * <p>Defines the type of a value that is stored in a {@linkplain Stream stream} and has 
	 * a value that consists of a varying number of bytes. The order of the bytes is 
	 * meaningful.</p>
	 * 
	 * @see tv.amwa.maj.meta.TypeDefinitionStream
	 * @see tv.amwa.maj.model.EssenceData#getEssenceStream()
	 * @see Stream
	 * @see tv.amwa.maj.misctype.DataBuffer
	 */
	public final static TypeDefinitionStream Stream = new TypeDefinitionStreamImpl(
			new AUIDImpl(0x04100200, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"Stream");

	/**
	 * <p>Defines the type for a value that is a block of data. In the MAJ API, this will
	 * be manipulated as a {@link java.nio.ByteBuffer}.</p>
	 * 
	 * @see #UInt8Array
	 * @see tv.amwa.maj.misctype.DataValue
	 * @see tv.amwa.maj.model.AIFCDescriptor#getAIFCSummary()
	 * @see tv.amwa.maj.model.TIFFDescriptor#getTIFFSummary()
	 * @see tv.amwa.maj.model.WAVEDescriptor#getWAVESummary()
	 */
	public final static TypeDefinitionVariableArray DataValue = new TypeDefinitionVariableArrayImpl(
			new AUIDImpl(0x04100100, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"DataValue",
			UInt8);

	/**
	 * <p>Defines the type for a value that represents a string of characters encoded 
	 * according to the <a href="http://en.wikipedia.org/wiki/UTF16">16-bit Unicode 
	 * Transformation Format (UTF16)</a>.</p>
	 * 
	 * @see #Character
	 * @see tv.amwa.maj.meta.TypeDefinitionString
	 * @see tv.amwa.maj.industry.TypeDefinitions#UTF16StringArray
	 * @see tv.amwa.maj.misctype.AAFString
	 * @see java.lang.String
	 */
	public final static TypeDefinitionString UTF16String = new TypeDefinitionStringImpl(
			new AUIDImpl(0x01100200, (short) 0x0000, (short) 0x0000,
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01} ),
			"UTF16String",
			TypeDefinitions.Character);

	/**
	 * <p>Defines the type for a value that represents a weak reference to a
	 * {@linkplain tv.amwa.maj.meta.ClassDefinition class definition}.</p>
	 * 
	 * @see #ClassDefinitionStrongReference
	 * @see #ClassDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.meta.ClassDefinition
	 * @see tv.amwa.maj.industry.Warehouse#lookForClass(String)
	 * @see tv.amwa.maj.model.InterchangeObject#getObjectClass()
	 * @see tv.amwa.maj.industry.MediaClass
	 */
	public final static TypeDefinitionWeakObjectReference ClassDefinitionWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010100, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ClassDefinitionWeakReference",
				Warehouse.lookForClass(ClassDefinitionImpl.class),
				new AUIDImpl[] { 
					new AUIDImpl(0x0d010301, (short) 0x0101, (short) 0x0100,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010107, (short) 0x0700, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines the type for a value that represents a weak reference to a
	 * {@linkplain tv.amwa.maj.model.ContainerDefinition container definition}. A container
	 * definition is used to describe the mechanism used to store {@linkplain EssenceData essence data}.</p>
	 * 
	 * @see #ContainerDefinitionStrongReference
	 * @see #ContainerDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.model.ContainerDefinition
	 * @see tv.amwa.maj.model.Dictionary#getContainerDefinitions()
	 * @see tv.amwa.maj.model.AAFFileDescriptor#getContainerFormat()
	 * @see tv.amwa.maj.constant.ContainerConstant
	 */
	public final static TypeDefinitionWeakObjectReference ContainerDefinitionWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010200, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ContainerDefinitionWeakReference",
				Warehouse.lookForClass(ContainerDefinitionImpl.class),
				new AUIDImpl[] { 
					new AUIDImpl(0x0d010301, (short) 0x0102, (short) 0x0100, // Preface
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0202, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0508, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines a type for a value that represents a weak reference to a
	 * {@linkplain tv.amwa.maj.model.DataDefinition data definition}. A data definition specifies
	 * the kind of data that can be stored in a {@linkplain Component component}.</p>
	 * 
	 * @see #DataDefinitionStrongReference
	 * @see #DataDefinitionWeakReferenceSet
	 * @see #DataDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.model.DataDefinition
	 * @see tv.amwa.maj.model.Dictionary#getDataDefinitions()
	 * @see tv.amwa.maj.model.Component#getComponentDataDefinition()
	 * @see tv.amwa.maj.model.OperationDefinition#getOperationDataDefinition()
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 */
	public final static TypeDefinitionWeakObjectReference DataDefinitionWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010300, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"DataDefinitionWeakReference",
				Warehouse.lookForClass(DataDefinitionImpl.class),
				new AUIDImpl[] { 
					new AUIDImpl(0x0d010301, (short) 0x0102, (short) 0x0100, 
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0202, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0505, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines the type for a value that represents a weak reference to a
	 * {@linkplain tv.amwa.maj.model.InterpolationDefinition interpolation definition}.
	 * An interpolation definition describes the mechanism used to calculate the values produced by a
	 * {@linkplain tv.amwa.maj.model.VaryingValue varying value} using the specified 
	 * {@linkplain tv.amwa.maj.model.ControlPoint control points}.</p>
	 * 
	 * @see #InterpolationDefinitionStrongReference
	 * @see #InterpolationDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.model.InterpolationDefinition
	 * @see tv.amwa.maj.model.Dictionary#getInterpolationDefinitions()
	 * @see tv.amwa.maj.model.VaryingValue#getInterpolationDefinition()
	 * @see tv.amwa.maj.constant.InterpolationConstant
	 */
	public final static TypeDefinitionWeakObjectReference InterpolationDefinitionWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010500, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"InterpolationDefinitionWeakReference",
				Warehouse.lookForClass(InterpolationDefinitionImpl.class),
				new AUIDImpl[] { 
					new AUIDImpl(0x0d010301, (short) 0x0102, (short) 0x0100, 
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0202, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0509, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines the type for a value that represents a weak reference to a
	 * {@linkplain tv.amwa.maj.model.Package package}.</p>
	 * 
	 * @see #PackageStrongReference
	 * @see #PackageStrongReferenceSet
	 * @see tv.amwa.maj.model.Package
	 */
	// Does not appear to be referenced in baseline metadata
	public final static TypeDefinitionWeakObjectReference PackageWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010600, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"PackageWeakReference",
				Warehouse.lookForClass(PackageImpl.class),
				new AUIDImpl[] {
					new AUIDImpl(0x0d010301, (short) 0x0102, (short) 0x0100, 
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0201, (short) 0x0000,
							new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0501, (short) 0x0000,
							new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines the type for a value that represents a weak reference to an
	 * {@linkplain tv.amwa.maj.model.OperationDefinition operation definition}. 
	 * Operation definitions specify which {@linkplain tv.amwa.maj.model.Parameter parameters}
	 * are possible for an operation.</p>
	 * 
	 * @see #OperationDefinitionStrongReference
	 * @see #OperationDefinitionStrongReferenceSet
	 * @see #OperationDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.model.OperationDefinition
	 * @see tv.amwa.maj.model.Dictionary#getOperationDefinitions()
	 * @see tv.amwa.maj.model.OperationGroup#getOperationDefinition()
	 * @see tv.amwa.maj.constant.OperationConstant
	 */
	public final static TypeDefinitionWeakObjectReference OperationDefinitionWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010700, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"OperationDefinitionWeakReference",
				Warehouse.lookForClass(OperationDefinitionImpl.class),
				new AUIDImpl[] { 
					new AUIDImpl(0x0d010301, (short) 0x0102, (short) 0x0100, 
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0202, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0503, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines the type for a value that represents a weak reference to a
	 * {@linkplain tv.amwa.maj.model.ParameterDefinition parameter definition}. Parameter
	 * definitions define the kinds of parameters required to control an effect.</p>
	 * 
	 * @see #ParameterDefinitionWeakReferenceSet
	 * @see #ParameterDefinitionStrongReference
	 * @see #ParameterDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.model.ParameterDefinition
	 * @see tv.amwa.maj.model.Dictionary#getParameterDefinitions()
	 * @see tv.amwa.maj.model.OperationDefinition#getOperationParametersDefined()
	 * @see tv.amwa.maj.model.Parameter#getParameterDefinition()
	 * @see tv.amwa.maj.constant.ParameterConstant
	 */
	public final static TypeDefinitionWeakObjectReference ParameterDefinitionWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010800, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ParameterDefinitionWeakReference",
				Warehouse.lookForClass(ParameterDefinitionImpl.class),
				new AUIDImpl[] { 
					new AUIDImpl(0x0d010301, (short) 0x0102, (short) 0x0100, 
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0202, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0504, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines the type for a value that represents a weak reference to a
	 * {@linkplain tv.amwa.maj.meta.TypeDefinition type definition}. Type definitions
	 * specify the set of possible values for a property.</p>
	 * 
	 * @see #TypeDefinitionStrongReference
	 * @see #TypeDefinitionWeakReferenceVector
	 * @see #TypeDefinitionStrongReference
	 * @see #TypeDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.meta.TypeDefinition
	 * @see tv.amwa.maj.meta.PropertyDefinition#getTypeDefinition()
	 * @see tv.amwa.maj.industry.PropertyValue#getType()
	 * @see tv.amwa.maj.industry.Warehouse#lookForType(String)
	 * @see tv.amwa.maj.industry.MediaProperty#typeName()
	 */
	public final static TypeDefinitionWeakObjectReference TypeDefinitionWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010900, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"TypeDefinitionWeakReference",
				Warehouse.lookForClass(TypeDefinitionImpl.class),
				new AUIDImpl[] { 
					new AUIDImpl(0x0d010301, (short) 0x0101, (short) 0x0100,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010107, (short) 0x0800, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines the type for a value that represents a weak reference to a
	 * {@linkplain tv.amwa.maj.model.PluginDefinition plugin definition}. Plugin
	 * definitions describe code objects that provide an implementation for a 
     * {@linkplain DefinitionObject definition}.</p>
	 * 
	 * @see #PluginDefinitionStrongReference
	 * @see #PluginDefinitionWeakReferenceSet
	 * @see #PluginDefinitionStrongReference
	 * @see #PluginDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.constant.PluginCategoryType
	 * @see tv.amwa.maj.model.PluginDefinition
	 * @see tv.amwa.maj.model.Dictionary#getPluginDefinitions()
	 * @see tv.amwa.maj.constant.PluginIdentifiers
	 */
	public final static TypeDefinitionWeakObjectReference PluginDefinitionWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010a00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"PluginDefinitionWeakReference",		
				Warehouse.lookForClass(PluginDefinitionImpl.class),
				new AUIDImpl[] { 
					new AUIDImpl(0x0d010301, (short) 0x0102, (short) 0x0100, 
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0202, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0506, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines a type for a value that represents a weak reference to a
	 * {@linkplain tv.amwa.maj.model.CodecDefinition codec definition}. A 
	 * codec definition defines a kind of coding of a data stream, such as
	 * a stream of essence data.</p>
	 * 
	 * @see #CodecDefinitionStrongReference
	 * @see #CodecDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.model.CodecDefinition
	 * @see tv.amwa.maj.model.Dictionary#getCodecDefinitions()
	 * @see tv.amwa.maj.model.AAFFileDescriptor#getCodec()
	 * @see tv.amwa.maj.constant.CodecConstant
	 */
	public final static TypeDefinitionWeakObjectReference CodecDefinitionWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010b00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"CodecDefinitionWeakReference",
				Warehouse.lookForClass(CodecDefinitionImpl.class),
				new AUIDImpl[] { 
					new AUIDImpl(0x0d010301, (short) 0x0102, (short) 0x0100, 
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0202, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010104, (short) 0x0507, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines a type for a value that represents a weak reference to a
	 * {@linkplain tv.amwa.maj.meta.PropertyDefinition property definition}. A
	 * property definition specifies the description of a property allowed for a 
	 * {@linkplain tv.amwa.maj.meta.ClassDefinition class}.</p>
	 * 
	 * @see #PropertyDefinitionWeakReferenceSet
	 * @see #PropertyDefinitionStrongReference
	 * @see #PropertyDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.meta.PropertyDefinition
	 * @see tv.amwa.maj.meta.ClassDefinition#getPropertyDefinitions()
	 * @see tv.amwa.maj.meta.ClassDefinition#getAllPropertyDefinitions()
	 * @see tv.amwa.maj.industry.MediaProperty
	 */	
	public final static TypeDefinitionWeakObjectReference PropertyDefinitionWeakReference = 
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010c00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"PropertyDefinitionWeakReference",
				Warehouse.lookForClass(PropertyDefinitionImpl.class),
				new AUIDImpl[] { 
					new AUIDImpl(0x0d010301, (short) 0x0101, (short) 0x0100,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010107, (short) 0x0700, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 }),
					new AUIDImpl(0x06010107, (short) 0x0200, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 })
				});

	/**
	 * <p>Defines a type for a value that represents a strong reference to
	 * {@linkplain tv.amwa.maj.model.ContentStorage content storage}.</p>
	 * 
	 * @see tv.amwa.maj.model.ContentStorage
	 * @see tv.amwa.maj.model.Preface#getContentStorageObject()
	 */
	public final static TypeDefinitionStrongObjectReference ContentStorageStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020100, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ContentStorageStrongReference",
				Warehouse.lookForClass(ContentStorageImpl.class));

	/**
	 * <p>Defines a type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.Dictionary dictionary}.</p>
	 * 
	 * @see tv.amwa.maj.model.Dictionary
	 * @see tv.amwa.maj.model.Preface#getDictionaries()
	 */
	public final static TypeDefinitionStrongObjectReference DictionaryStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020200, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"DictionaryStrongReference",
				Warehouse.lookForClass(DictionaryImpl.class));

	/**
	 * <p>Defines a type for a value that represents a strong reference to an
	 * {@linkplain tv.amwa.maj.model.EssenceDescriptor essence descriptor}.</p>
	 * 
	 * @see tv.amwa.maj.model.EssenceDescriptor
	 * @see tv.amwa.maj.model.SourcePackage#getEssenceDescriptor()
	 */
	public final static TypeDefinitionStrongObjectReference EssenceDescriptorStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020300, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"EssenceDescriptorStrongReference",
				Warehouse.lookForClass(EssenceDescriptorImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.NetworkLocator network locator}.</p>
	 * 
	 * @see tv.amwa.maj.model.NetworkLocator
	 * @see tv.amwa.maj.model.PluginDefinition#getManufacturerInfo()
	 * @see tv.amwa.maj.model.EssenceDescriptor#getLocators()
	 */
	public final static TypeDefinitionStrongObjectReference NetworkLocatorStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020400, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"NetworkLocatorStrongReference",
				Warehouse.lookForClass(NetworkLocatorImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to an
	 * {@linkplain tv.amwa.maj.model.OperationGroup operation group}.</p>
	 * 
	 * @see tv.amwa.maj.model.OperationGroup
	 * @see tv.amwa.maj.model.Transition#getTransitionOperation()
	 */
	public final static TypeDefinitionStrongObjectReference OperationGroupStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020500, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"OperationGroupStrongReference",
				Warehouse.lookForClass(OperationGroupImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.Segment segment}.</p>
	 * 
	 * @see #SegmentStrongReferenceVector
	 * @see tv.amwa.maj.model.Segment
	 * @see tv.amwa.maj.model.Pulldown#getInputSegment()
	 * @see tv.amwa.maj.model.Selector
	 * @see tv.amwa.maj.model.Track#getTrackSegment()
	 */
	public final static TypeDefinitionStrongObjectReference SegmentStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020600, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"SegmentStrongReference",
				Warehouse.lookForClass(SegmentImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.SourceClip source clip}.</p>
	 * 
	 * @see tv.amwa.maj.model.SourceClip
	 */
	// Note that this does not appear to be used in the baseline classes
	public final static TypeDefinitionStrongObjectReference SourceClipStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020700, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"SourceClipStrongReference",
				Warehouse.lookForClass(SourceClipImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.SourceReferenceSegment source reference}.</p>
	 * 
	 * @see #SourceReferenceStrongReferenceVector
	 * @see tv.amwa.maj.model.SourceReferenceSegment
	 * @see tv.amwa.maj.union.SourceReferenceValue
	 * @see tv.amwa.maj.model.CommentMarker#getAnnotationSource()
	 * @see tv.amwa.maj.model.EssenceGroup#getStillFrame()
	 * @see tv.amwa.maj.model.OperationGroup#getRendering()
	 */
	public final static TypeDefinitionStrongObjectReference SourceReferenceStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020800, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"SourceReferenceStrongReference",
				Warehouse.lookForClass(SourceReferenceSegmentImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.meta.ClassDefinition class definition}.</p>
	 * 
	 * @see #ClassDefinitionStrongReferenceSet
	 * @see #ClassDefinitionWeakReference
	 * @see tv.amwa.maj.meta.ClassDefinition
	 * @see tv.amwa.maj.industry.MediaClass
	 */
	public final static TypeDefinitionStrongObjectReference ClassDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020900, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ClassDefinitionStrongReference",
				Warehouse.lookForClass(ClassDefinitionImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.CodecDefinition codec definition}.</p>
	 * 
	 * @see #CodecDefinitionStrongReferenceSet
	 * @see #CodecDefinitionWeakReference
	 * @see tv.amwa.maj.model.CodecDefinition
	 * @see tv.amwa.maj.constant.CodecConstant
	 */
	public final static TypeDefinitionStrongObjectReference CodecDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020a00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"CodecDefinitionStrongReference",
				Warehouse.lookForClass(CodecDefinitionImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.Component component}.</p>
	 * 
	 * @see #ComponentStrongReferenceVector
	 * @see tv.amwa.maj.model.Component
	 */
	public final static TypeDefinitionStrongObjectReference ComponentStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020b00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ComponentStrongReference",
				Warehouse.lookForClass(ComponentImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.ContainerDefinition container definition}.</p>
	 * 
	 * @see #ContainerDefinitionStrongReferenceSet
	 * @see #ContainerDefinitionWeakReference
	 * @see tv.amwa.maj.model.ContainerDefinition
	 * @see tv.amwa.maj.constant.ContainerConstant
	 */
	public final static TypeDefinitionStrongObjectReference ContainerDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020c00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ContainerDefinitionStrongReference",
				Warehouse.lookForClass(ContainerDefinitionImpl.class));

	/**
	 * <p>Defines the type for a property value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.ControlPoint control point}.</p>
	 * 
	 * @see #ControlPointStrongReferenceVector
	 * @see tv.amwa.maj.model.ControlPoint
	 */
	public final static TypeDefinitionStrongObjectReference ControlPointStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020d00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ControlPointStrongReference",
				Warehouse.lookForClass(ControlPointImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.DataDefinition data definition}.</p>
	 * 
	 * @see #DataDefinitionStrongReferenceSet
	 * @see #DataDefinitionWeakReference
	 * @see #DataDefinitionWeakReferenceSet
	 * @see #DataDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.model.DataDefinition
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 */
	public final static TypeDefinitionStrongObjectReference DataDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020e00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"DataDefinitionStrongReference",
				Warehouse.lookForClass(DataDefinitionImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to 
	 * {@linkplain tv.amwa.maj.model.EssenceData essence data}.</p>
	 * 
	 * @see #EssenceDataStrongReferenceSet
	 * @see tv.amwa.maj.model.EssenceData
	 */
	public final static TypeDefinitionStrongObjectReference EssenceDataStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05020f00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"EssenceDataStrongReference",
				Warehouse.lookForClass(EssenceDataImpl.class));

	/**
	 * <p>Defines the type for a property value that represents a strong reference to an
	 * {@linkplain tv.amwa.maj.model.Identification identification}.</p>
	 * 
	 * @see #IdentificationStrongReferenceVector
	 * @see tv.amwa.maj.model.Identification
	 */
	public final static TypeDefinitionStrongObjectReference IdentificationStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021000, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"IdentificationStrongReference",
				Warehouse.lookForClass(IdentificationImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to an
	 * {@linkplain tv.amwa.maj.model.InterpolationDefinition interpolation definition}.</p>
	 * 
	 * @see #InterpolationDefinitionStrongReferenceSet
	 * @see #InterpolationDefinitionWeakReference
	 * @see tv.amwa.maj.model.InterpolationDefinition
	 * @see tv.amwa.maj.constant.InterpolationConstant
	 */
	public final static TypeDefinitionStrongObjectReference InterpolationDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021100, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"InterpolationDefinitionStrongReference",
				Warehouse.lookForClass(InterpolationDefinitionImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.Locator locator}.</p>
	 * 
	 * @see #LocatorStrongReferenceVector
	 * @see tv.amwa.maj.model.Locator
	 */
	public final static TypeDefinitionStrongObjectReference LocatorStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021200, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"LocatorStrongReference",
				Warehouse.lookForClass(LocatorImpl.class));

	/**
	 * <p>Defines the type for a property value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.Package package}.</p>
	 * 
	 * @see #PackageStrongReferenceSet
	 * @see #PackageWeakReference
	 * @see tv.amwa.maj.model.Package
	 * @see tv.amwa.maj.model.ContentStorage#getPackages()
	 */
	public final static TypeDefinitionStrongObjectReference PackageStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021300, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"PackageStrongReference",
				Warehouse.lookForClass(PackageImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.Track track}.</p>
	 * 
	 * @see #TrackStrongReferenceVector
	 * @see tv.amwa.maj.model.Track
	 */
	public final static TypeDefinitionStrongObjectReference TrackStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021400, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"TrackStrongReference",
				Warehouse.lookForClass(TrackImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to an
	 * {@linkplain tv.amwa.maj.model.OperationDefinition operation definition}.</p>
	 * 
	 * @see #OperationDefinitionStrongReferenceSet
	 * @see #OperationDefinitionWeakReference
	 * @see #OperationDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.model.OperationDefinition
	 * @see tv.amwa.maj.constant.OperationConstant
	 */
	public final static TypeDefinitionStrongObjectReference OperationDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021500, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"OperationDefinitionStrongReference",
				Warehouse.lookForClass(OperationDefinitionImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.Parameter parameter}.</p>
	 * 
	 * @see #ParameterStrongReferenceVector
	 * @see tv.amwa.maj.model.Parameter
	 */
	public final static TypeDefinitionStrongObjectReference ParameterStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021600, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ParameterStrongReference",
				Warehouse.lookForClass(ParameterImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.ParameterDefinition parameter definition}.</p>
	 * 
	 * @see #ParameterDefinitionStrongReferenceSet
	 * @see #ParameterDefinitionWeakReference
	 * @see #ParameterDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.model.ParameterDefinition
	 * @see tv.amwa.maj.constant.ParameterConstant
	 */
	public final static TypeDefinitionStrongObjectReference ParameterDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021700, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ParameterDefinitionStrongReference",
				Warehouse.lookForClass(ParameterDefinitionImpl.class));

	/**
	 * <p>Defines the type for a  value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.PluginDefinition plugin definition}.</p>
	 * 
	 * @see #PluginDefinitionStrongReferenceSet
	 * @see #PluginDefinitionWeakReference
	 * @see #PluginDefinitionWeakReferenceSet
	 * @see tv.amwa.maj.model.PluginDefinition
	 * @see tv.amwa.maj.constant.PluginIdentifiers
	 */
	public final static TypeDefinitionStrongObjectReference PluginDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021800, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"PluginDefinitionStrongReference",
				Warehouse.lookForClass(PluginDefinitionImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.meta.PropertyDefinition property definition}.</p>
	 * 
	 * @see #PropertyDefinitionStrongReferenceSet
	 * @see #PropertyDefinitionWeakReference
	 * @see #PropertyDefinitionWeakReferenceSet
	 * @see tv.amwa.maj.meta.PropertyDefinition
	 * @see tv.amwa.maj.industry.MediaProperty
	 */
	public final static TypeDefinitionStrongObjectReference PropertyDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021900, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"PropertyDefinitionStrongReference",
				Warehouse.lookForClass(PropertyDefinitionImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.TaggedValue tagged value}.</p>
	 * 
	 * @see #TaggedValueStrongReferenceVector
	 * @see tv.amwa.maj.model.TaggedValue
	 */
	public final static TypeDefinitionStrongObjectReference TaggedValueStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021a00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"TaggedValueStrongReference",
				Warehouse.lookForClass(TaggedValueImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.meta.TypeDefinition type definition}.</p>
	 * 
	 * @see #TypeDefinitionStrongReferenceSet
	 * @see #TypeDefinitionWeakReference
	 * @see #TypeDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.meta.TypeDefinition
	 */
	public final static TypeDefinitionStrongObjectReference TypeDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021b00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"TypeDefinitionStrongReference",
				Warehouse.lookForClass(TypeDefinitionImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.KLVData KLV data value}.</p>
	 * 
	 * @see #KLVDataStrongReferenceVector
	 * @see tv.amwa.maj.model.KLVData
	 */
	public final static TypeDefinitionStrongObjectReference KLVDataStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021c00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"KLVDataStrongReference",
				Warehouse.lookForClass(KLVDataImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.DescriptiveFramework descriptive framework}.</p>
	 * 
	 * @see tv.amwa.maj.model.DescriptiveFramework
	 * @see tv.amwa.maj.model.DescriptiveMarker#getDescriptiveFrameworkObject()
	 */
	public final static TypeDefinitionStrongObjectReference DescriptiveFrameworkStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021f00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"DescriptiveFrameworkStrongReference",
				Warehouse.lookForClass(DescriptiveFrameworkImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.KLVDataDefinition KLV data definition}.</p>
	 * 
	 * @see #KLVDataDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.model.KLVDataDefinition
	 */
	public final static TypeDefinitionStrongObjectReference KLVDataDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05022000, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"KLVDataDefinitionStrongReference",
				Warehouse.lookForClass(KLVDataDefinitionImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.TaggedValueDefinition tagged value definition}.</p>
	 * 
	 * @see #TaggedValueDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.model.TaggedValueDefinition
	 */
	public final static TypeDefinitionStrongObjectReference TaggedValueDefinitionStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05022100, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"TaggedValueDefinitionStrongReference",
				Warehouse.lookForClass(TaggedValueDefinitionImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.SubDescriptor sub descriptor}.</p>
	 * 
	 * @see #SubDescriptorStrongReferenceVector
	 * @see tv.amwa.maj.model.SubDescriptor
	 * @see tv.amwa.maj.model.EssenceDescriptor#getSubDescriptors()
	 */
	public final static TypeDefinitionStrongObjectReference SubDescriptorStrongReference = 
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05022600, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"SubDescriptorStrongReference",
				Warehouse.lookForClass(SubDescriptorImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.AAFFileDescriptor file descriptor}.</p>
	 * 
	 * @see #FileDescriptorStrongReferenceVector
	 * @see tv.amwa.maj.model.AAFFileDescriptor
	 */
	public final static TypeDefinitionStrongObjectReference FileDescriptorStrongReference =
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021D00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"FileDescriptorStrongReference",
				Warehouse.lookForClass(AAFFileDescriptorImpl.class));

	/**
	 * <p>Defines the type for a property value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.RIFFChunk RIFF chunk}.</p>
	 * 
	 * @see #RIFFChunkStrongReferenceVector
	 * @see tv.amwa.maj.model.RIFFChunk
	 */
	public static final TypeDefinitionStrongObjectReference RIFFChunkStrongReference =
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05021E00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"RIFFChunkStrongReference",
				Warehouse.lookForClass(RIFFChunkImpl.class));

	/**
	 * <p>Defines the type for a value that represents a strong reference to a
	 * {@linkplain tv.amwa.maj.model.DescriptiveObject descriptive object}.</p>
	 * 
	 * @see #DescriptiveObjectStrongReferenceVector
	 * @see #DescriptiveObjectStrongReferenceSet
	 * @see tv.amwa.maj.model.DescriptiveObject
	 */
	public final static TypeDefinitionStrongObjectReference DescriptiveObjectStrongReference =
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05022200, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"DescriptiveObjectStrongReference",
				Warehouse.lookForClass(DescriptiveObjectImpl.class));	
	
	/**
	 * <p>Defines the type for a value that is an array of 8&nbsp;elements of unsigned 8-bit integer values.</p>
	 * 
	 * @see #AUID
	 * @see #UInt8
	 * @see tv.amwa.maj.integer.UInt8
	 * @see tv.amwa.maj.record.AUID#getData4()
	 */
	public final static TypeDefinitionFixedArray UInt8Array8 = new TypeDefinitionFixedArrayImpl(
				new AUIDImpl(0x04010800, (short) 0x0000, (short) 0x0000,
						new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
				"UInt8Array8",
				UInt8, 8);

	/**
	 * <p>Defines the type for a value that is an array of 12&nbsp;elements of unsigned 8-bit integer values.</p>
	 * 
	 * @see #PackageIDType
	 * @see #UInt8
	 * @see tv.amwa.maj.integer.UInt8
	 * @see tv.amwa.maj.record.PackageID#getUniversalLabel()
	 */
	public final static TypeDefinitionFixedArray UInt8Array12 = new TypeDefinitionFixedArrayImpl(
				new AUIDImpl(0x04010200, (short) 0x0000, (short) 0x0000,
						new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
				"UInt8Array12",
				UInt8, 12);

	/**
	 * <p>Defines the type for a rational number with 32-bit integer numerator and denominator.</p>
	 * 
	 * <p>To make values of this type, use the following methods from the 
	 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
	 * 
	 * <ul>
	 *  <li>From its numerator and denominator : 
	 *  {@link tv.amwa.maj.industry.Forge#makeRational(int, int)};</li>
	 *  <li>A zero <em>default</em> value: {@link tv.amwa.maj.industry.Forge#zeroRational()};
	 *  <li>From a string representation as generated by 
	 *  {@link tv.amwa.maj.record.Rational#toString()}: 
	 *  {@link tv.amwa.maj.industry.Forge#parseRational(String)}.</li>
	 * </ul> 
	 * 
	 * @see tv.amwa.maj.record.Rational
	 */
	public final static TypeDefinitionRecord Rational = new TypeDefinitionRecordImpl(
			new AUIDImpl(0x03010100, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"Rational",
			new String[] { "Numerator", "Denominator" },
			new TypeDefinition[] { Int32, Int32 },
			tv.amwa.maj.record.Rational.class);

	/**
	 * <p>Defines the type of a 16-byte unique identifier whose value is a SMPTE&nbsp;298M Universal Label or 
	 * a UUID or GUID.</p>
	 * 
	 * <p>To make values of this type, use the following methods from the 
	 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
	 * 
	 * <ul>
	 *  <li>From its underlying bytes: {@link tv.amwa.maj.industry.Forge#makeAUID(byte[])};</li>
	 *  <li>From its constituent parts: {@link tv.amwa.maj.industry.Forge#makeAUID(int, short, short, byte[])};</li>
	 *  <li>A time-based UUID: {@link tv.amwa.maj.industry.Forge#timebasedAUID()} and 
	 *  {@link tv.amwa.maj.industry.Forge#timebasedAUID(byte[])};</li>
	 *  <li>A name-based UUID: {@link tv.amwa.maj.industry.Forge#namebasedAUID(byte[])} and 
	 *  {@link tv.amwa.maj.industry.Forge#namebasedAUID(String)};</li>
	 *  <li>A random UUID: {@link tv.amwa.maj.industry.Forge#randomAUID()};</li>
	 *  <li>From a URN string representation as generated by {@link tv.amwa.maj.record.AUID#toString()}: 
	 *  {@link tv.amwa.maj.industry.Forge#parseAUID(String)}.</li>
	 * </ul>
	 * 
	 * @see tv.amwa.maj.record.AUID
	 * @see #AUIDSet
	 * @see #AUIDArray
	 */
	public final static TypeDefinitionRecord AUID = new TypeDefinitionRecordImpl(
			new AUIDImpl(0x01030100, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"AUID",
			new String[] { "data1", "data2", "data3", "data4" },
			new TypeDefinition[] { 
				UInt32, 
				UInt16,
				UInt16,
				UInt8Array8
			},
			tv.amwa.maj.record.AUID.class);

	/**
	 * <p>Defines the type of a value representing a 32&nbsp;byte <em>package identifier</em> unique identifier 
	 * that can hold a SMPTE UMID, as specified in SMPTE&nbsp;330M.</p>
	 * 
	 * <p>To make values of this type, use the following methods from the 
	 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
	 * 
	 * <ul>
	 *  <li>From its underlying bytes: {@link tv.amwa.maj.industry.Forge#makePackageID(byte[])};</li>
	 *  <li>From its constituent parts: 
	 *  {@link tv.amwa.maj.industry.Forge#makePackageID(byte[], byte, byte, byte, byte, AUID)};</li>
	 *  <li>According to a specific generation strategy: 
	 *  {@link tv.amwa.maj.industry.Forge#generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration)},
	 *  {@link tv.amwa.maj.industry.Forge#generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, AUID)} and
	 *  {@link tv.amwa.maj.industry.Forge#generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, byte[])};</li>
	 *  <li>A D-Cinema UMID: {@link tv.amwa.maj.industry.Forge#dCinemaUMID()};</li>
	 *  <li>From a URN string representation as generated by {@link tv.amwa.maj.record.PackageID#toString()}: 
	 *  {@link tv.amwa.maj.industry.Forge#parsePackageID(String)}.</li>
	 * </ul>
	 * 
	 * @see tv.amwa.maj.record.PackageID
	 */
	public final static TypeDefinitionRecord PackageIDType = new TypeDefinitionRecordImpl(
			new AUIDImpl(0x01030200, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
					"PackageIDType",
					new String[] { "SMPTELabel", "length", "instanceHigh", "instanceMid", "instanceLow", "material" },
					new TypeDefinition[] { 
							UInt8Array12, 
							UInt8,
							UInt8,
							UInt8,
							UInt8,
							TypeDefinitions.AUID
			},
			tv.amwa.maj.record.PackageID.class);

	/**
	 * <p>Defines the type for the version number of an application, represented with four release levels
	 * and its {@linkplain tv.amwa.maj.enumeration.ProductReleaseType product release type}. 
	 * The four release levels specify the major, minor, tertiary and patch level of a product.</p>
	 * 
	 * <p>To make values of this type, use the following methods from the 
	 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
	 * 
	 * <ul>
	 *  <li>From its constituent parts: 
	 *  {@link tv.amwa.maj.industry.Forge#makeProductVersion(short, short, short, short, ProductReleaseType)};</li>
	 *  <li>A zero <em>default</em> value: {@link tv.amwa.maj.industry.Forge#zeroProductVersion()};
	 *  <li>From a pseudo-XML string representation as generated by 
	 *  {@link tv.amwa.maj.record.ProductVersion#toString()}: 
	 *  {@link tv.amwa.maj.industry.Forge#parseProductVersion(String)}.</li>
	 * </ul> 
	 * 
	 * @see tv.amwa.maj.record.ProductVersion
	 * @see #VersionType
	 */
	public final static TypeDefinitionRecord ProductVersionType = new TypeDefinitionRecordImpl(
			new AUIDImpl(0x03010200, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"ProductVersionType",
			new String[] { "major", "minor", "tertiary", "patchLevel", "type" },
			new TypeDefinition[] { 
				UInt16, 
				UInt16,
				UInt16,
				UInt16,
				ProductReleaseType
			},
			tv.amwa.maj.record.ProductVersion.class);

	/**
	 * <p>Defines the type for a 2-byte version number, consisting of major and minor components.</p>
	 * 
	 * <p>To make values of this type, use the following methods from the 
	 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
	 * 
	 * <ul>
	 *  <li>From its component parts: 
	 *  {@link tv.amwa.maj.industry.Forge#makeVersion(byte, byte)};</li>
	 *  <li>A zero value: {@link tv.amwa.maj.industry.Forge#zeroVersion()};</li>
	 *  <li>From a string representation as generated by 
	 *  {@link tv.amwa.maj.record.VersionType#toString()}: 
	 *  {@link tv.amwa.maj.industry.Forge#parseVersion(String)}.</li>
	 * </ul>  	 
	 * 
	 * @see tv.amwa.maj.record.VersionType
	 * @see #ProductVersionType
	 */
	public final static TypeDefinitionRecord VersionType = new TypeDefinitionRecordImpl(
			new AUIDImpl(0x03010300, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"VersionType",
			new String[] { "major", "minor" },
			new TypeDefinition[] { 
				Int8, 
				Int8
			},
			tv.amwa.maj.record.VersionType.class);

	public final static TypeDefinitionRecord VersionNumber = VersionType;

	/**
	 * <p>Defines the type for an element of an array representing the order and size of the component 
	 * values within a pixel value as part of an {@linkplain #RGBALayout}. 
	 * The RGB layout type type is a fixed-size 8&nbsp;element array, where each element consists of an 
	 * RGBA component value. Each RGBA component has with the following fields:</p>
	 * 
	 * <ul>
	 * <li><code>code</code> - {@linkplain tv.amwa.maj.enumeration.RGBAComponentKind RGBA component kind enumerated value} 
	 * specifying the component kind.</li>
	 * <li><code>size</code> - Java byte specifying the number of bits.</li>
	 * </ul>
	 * 
	 * <p>To make values of this type, use the following methods from the 
	 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
	 * 
	 * <ul>
	 *  <li>From its code and size: 
	 *  {@link tv.amwa.maj.industry.Forge#makeRGBAComponent(RGBAComponentKind, byte)};</li>
	 *  <li>A zero <em>default</em> value: {@link tv.amwa.maj.industry.Forge#zeroRGBAComponent()};
	 *  <li>From a pseudo-XML string representation as generated by {@link tv.amwa.maj.record.RGBAComponent#toString()}: 
	 *  {@link tv.amwa.maj.industry.Forge#parseRGBAComponent(String)}.</li>
	 * </ul> 	 
	 * 
	 * @see #RGBALayout
	 * @see tv.amwa.maj.record.RGBAComponent
	 */
	public final static TypeDefinitionRecord RGBAComponent = new TypeDefinitionRecordImpl(
			new AUIDImpl(0x03010400, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"RGBAComponent",
			new String[] { "Code", "ComponentSize" },
			new TypeDefinition[] { 
				RGBAComponentKind, 
				UInt8
			},
			tv.amwa.maj.record.RGBAComponent.class);

	/**
	 * <p>Defines the type for an array of {@linkplain #RGBAComponent RGBA components} that describe the
	 * layout of bytes in a picture represented by red, green, blue and alpha values.</p>
	 * 
	 * @see #RGBAComponent
	 * @see tv.amwa.maj.model.RGBADescriptor
	 * @see tv.amwa.maj.model.RGBADescriptor#getPaletteLayout()
	 * @see tv.amwa.maj.model.RGBADescriptor#getPixelLayout()
	 * @see #RGBAComponentKind
	 */
	public final static TypeDefinitionFixedArray RGBALayout = new TypeDefinitionFixedArrayImpl(
			new AUIDImpl(0x04020100, (short) 0x0000, (short) 0x0000, new byte[] {
					0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"RGBALayout", TypeDefinitions.RGBAComponent, 8);

	/**
	 * <p>Defines th type of a value that represents a date, as year, month and day of the month 
	 * components.</p>
	 * 
	 * <p>To make values of this type, use the following methods from the 
	 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
	 * 
	 * <ul>
	 *  <li>From its component values: {@link tv.amwa.maj.industry.Forge#makeDate(byte, byte, short)};</li>
	 *  <li>From an existing Java Calendar value: {@link tv.amwa.maj.industry.Forge#makeDate(Calendar)};</li>
	 *  <li>For today's date: {@link tv.amwa.maj.industry.Forge#todaysDate()};</li>
	 *  <li>From a string representation, similar to an XML schema date type and as generated by 
	 *  {@link tv.amwa.maj.record.DateStruct#toString()}: 
	 *  {@link tv.amwa.maj.industry.Forge#parseDate(String)}.</li>
	 * </ul>
	 * 
	 * @see tv.amwa.maj.record.DateStruct
	 * @see #TimeStruct
	 * @see #TimeStamp
	 */
	public final static TypeDefinitionRecord DateStruct = new TypeDefinitionRecordImpl(
			new AUIDImpl(0x03010500, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"DateStruct",
			new String[] { "year", "month", "day" },
			new TypeDefinition[] { 
				Int16,
				UInt8,
				UInt8
			},
			tv.amwa.maj.record.DateStruct.class);

	/**
	 * <p>Defines the type for a time component of date and time values specified according to 
     * Coordinated Universal Time (UTC), including
     * hour, minute, second and 1/100th of a second. The time structure is itself a component of a 
     * {@link #TimeStamp}.</p>
     * 
	 * <p>To make values of this type, use the following methods from the 
	 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
	 * 
	 * <ul>
	 *  <li>From its component parts: 
	 *  {@link tv.amwa.maj.industry.Forge#makeTime(byte, byte, byte)} and
	 *  {@link tv.amwa.maj.industry.Forge#makeTime(byte, byte, byte, byte)};</li>
	 *  <li>From a {@link java.util.Calendar} value:
	 *  {@link tv.amwa.maj.industry.Forge#makeTime(java.util.Calendar)};</li>
	 *  <li>The time now: {@link tv.amwa.maj.industry.Forge#timeNow()};</li>
	 *  <li>From a string representation as generated by 
	 *  {@link tv.amwa.maj.record.TimeStruct#toString()}: 
	 *  {@link tv.amwa.maj.industry.Forge#parseTime(String)}.</li>
	 * </ul>  	 
	 * 
	 * @see tv.amwa.maj.record.TimeStruct
	 * @see #DateStruct
	 * @see #TimeStamp
	 */
	public final static TypeDefinitionRecord TimeStruct = new TypeDefinitionRecordImpl(
			new AUIDImpl(0x03010600, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"TimeStruct",
			new String[] { "hour", "minute", "second", "fraction" },
			new TypeDefinition[] { 
				UInt8,
				UInt8,
				UInt8,
				UInt8
			},
			tv.amwa.maj.record.TimeStruct.class);

	/**
	 * <p>Defines the type for a date and time in UTC (Coordinated Universal Time). The value is made
     * up of a {@link #DateStruct} and {@link #TimeStruct} parts.</p>
     * 
	 * <p>For more information on UTC, see the <a href="http://en.wikipedia.org/wiki/UTC">entry on
	 * Coordinated Universal Time on Wikipedia.</a></p>
	 * 
	 * <p>To make values of this type, use the following methods from the 
	 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
	 * 
	 * <ul>
	 *  <li>From its component parts: 
	 *  {@link tv.amwa.maj.industry.Forge#makeTimeStamp(DateStruct, TimeStruct)} and
	 *  {@link tv.amwa.maj.industry.Forge#makeTimeStamp(short, byte, byte, byte, byte, byte, byte)};</li>
	 *  <li>From a {@link java.util.Calendar} value:
	 *  {@link tv.amwa.maj.industry.Forge#makeTimeStamp(java.util.Calendar)};</li>
	 *  <li>The date and time now: {@link tv.amwa.maj.industry.Forge#now()};</li>
	 *  <li>From a string representation as generated by {@link tv.amwa.maj.record.TimeStamp#toString()}: 
	 *  {@link tv.amwa.maj.industry.Forge#parseTimeStamp(String)}.</li>
	 * </ul>  	
	 * 
	 * @see tv.amwa.maj.record.TimeStamp
	 * @see #TimeStruct
	 * @see #DateStruct
	 */
	public final static TypeDefinitionRecord TimeStamp = new TypeDefinitionRecordImpl(
			new AUIDImpl(0x03010700, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"TimeStamp",
			new String[] { "date", "time" },
			new TypeDefinition[] { 
				TypeDefinitions.DateStruct,
				TypeDefinitions.TimeStruct
			},
			tv.amwa.maj.record.TimeStamp.class);

	/**
	 * <p>Defines a type for a set of {@linkplain #AUID AUID} values, where each element represents
	 * a 16-byte identifier.</p>
	 * 
	 * @see #AUID
	 * @see tv.amwa.maj.record.AUID
	 * @see #AUIDArray
	 */
	public final static TypeDefinitionSet AUIDSet = new TypeDefinitionSetImpl(
			new AUIDImpl(0x04030100, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"AUIDSet",
			AUID);

	/**
	 * <p>Defines the type for a value made up of a set of unsigned 32-bit integer values.</p>
	 * 
	 * @see #UInt32
	 * @see tv.amwa.maj.integer.UInt32Set
	 * @see #UInt32Array
	 */
	public final static TypeDefinitionSet UInt32Set = new TypeDefinitionSetImpl(
			new AUIDImpl(0x04030200, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"UInt32Set",
			UInt32);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to 
	 * {@linkplain tv.amwa.maj.meta.ClassDefinition class definitions}.</p>
	 * 
	 * @see tv.amwa.maj.meta.ClassDefinition
	 * @see #ClassDefinitionStrongReference
	 * @see #ClassDefinitionWeakReference
	 */
	public final static TypeDefinitionSet ClassDefinitionStrongReferenceSet = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050100, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"ClassDefinitionStrongReferenceSet",
			ClassDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.CodecDefinition codec definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.CodecDefinition
	 * @see #CodecDefinitionStrongReference
	 * @see #CodecDefinitionWeakReference
	 * @see tv.amwa.maj.model.Dictionary#getCodecDefinitions()
	 * @see tv.amwa.maj.constant.CodecConstant
	 */
	public final static TypeDefinitionSet CodecDefinitionStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050200, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"CodecDefinitionStrongReferenceSet",
			CodecDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.ContainerDefinition container definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.ContainerDefinition
	 * @see #ContainerDefinitionStrongReference
	 * @see #ContainerDefinitionWeakReference
	 * @see tv.amwa.maj.model.Dictionary#getContainerDefinitions()
	 * @see tv.amwa.maj.constant.ContainerConstant
	 */
	public final static TypeDefinitionSet ContainerDefinitionStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050300, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"ContainerDefinitionStrongReferenceSet",
			ContainerDefinitionStrongReference);

	/**
	 * <p>Defines the type for a property value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.DataDefinition data definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.DataDefinition
	 * @see #DataDefinitionStrongReference
	 * @see #DataDefinitionWeakReference
	 * @see #DataDefinitionWeakReferenceSet
	 * @see #DataDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.model.Dictionary#getDataDefinitions()
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 */
	public final static TypeDefinitionSet DataDefinitionStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050400, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"DataDefinitionStrongReferenceSet",
			DataDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.EssenceData essence data}.</p>
	 * 
	 * @see tv.amwa.maj.model.EssenceData
	 * @see #EssenceDataStrongReference
	 * @see tv.amwa.maj.model.ContentStorage#enumEssenceDataObjects()
	 * @see tv.amwa.maj.model.ContentStorage#getEssenceDataObjects(tv.amwa.maj.enumeration.CriteriaType)
	 */
	public final static TypeDefinitionSet EssenceDataStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050500, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"EssenceDataStrongReferenceSet",
			EssenceDataStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.InterpolationDefinition interpolation definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.InterpolationDefinition
	 * @see #InterpolationDefinitionStrongReference
	 * @see #InterpolationDefinitionWeakReference
	 * @see tv.amwa.maj.model.Dictionary#getInterpolationDefinitions()
	 * @see tv.amwa.maj.constant.InterpolationConstant
	 */
	public final static TypeDefinitionSet InterpolationDefinitionStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050600, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"InterpolationDefinitionStrongReferenceSet",
			InterpolationDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.Package packages}.</p>
	 * 
	 * @see tv.amwa.maj.model.Package
	 * @see #PackageStrongReference
	 * @see #PackageWeakReference
	 * @see tv.amwa.maj.model.ContentStorage#getPackages()
	 * @see tv.amwa.maj.model.ContentStorage#getPackages(tv.amwa.maj.union.SearchCriteria)
	 */
	public final static TypeDefinitionSet PackageStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050700, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"PackageStrongReferenceSet",
			PackageStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.OperationDefinition operation definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.OperationDefinition
	 * @see #OperationDefinitionStrongReference
	 * @see #OperationDefinitionWeakReference
	 * @see #OperationDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.model.Dictionary#getOperationDefinitions()
	 * @see tv.amwa.maj.constant.OperationConstant
	 */
	public final static TypeDefinitionSet OperationDefinitionStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050800, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"OperationDefinitionStrongReferenceSet",
			OperationDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.ParameterDefinition parameter definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.ParameterDefinition
	 * @see #ParameterDefinitionStrongReference
	 * @see #ParameterDefinitionWeakReference
	 * @see #ParameterDefinitionWeakReferenceSet
	 * @see tv.amwa.maj.model.Dictionary#getParameterDefinitions()
	 * @see tv.amwa.maj.constant.ParameterConstant
	 */
	public final static TypeDefinitionSet ParameterDefinitionStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050900, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"ParameterDefinitionStrongReferenceSet",
			ParameterDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.PluginDefinition plugin definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.PluginDefinition
	 * @see #PluginDefinitionStrongReference
	 * @see #PluginDefinitionWeakReference
	 * @see #PluginDefinitionWeakReferenceSet
	 * @see tv.amwa.maj.model.Dictionary#getPluginDefinitions()
	 * @see tv.amwa.maj.constant.PluginIdentifiers
	 */
	public final static TypeDefinitionSet PluginDefinitionStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050a00, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"PluginDefinitionStrongReferenceSet",
			PluginDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.meta.PropertyDefinition property definitions}.</p>
	 * 
	 * @see tv.amwa.maj.meta.PropertyDefinition
	 * @see #PropertyDefinitionStrongReference
	 * @see #PropertyDefinitionWeakReference
	 * @see #PropertyDefinitionWeakReferenceSet
	 * @see tv.amwa.maj.industry.MediaProperty
	 */
	public final static TypeDefinitionSet PropertyDefinitionStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050b00, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
					"PropertyDefinitionStrongReferenceSet",
					PropertyDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.meta.TypeDefinition type definitions}.</p>
	 * 
	 * @see tv.amwa.maj.meta.TypeDefinition
	 * @see #TypeDefinitionStrongReference
	 * @see #TypeDefinitionWeakReference
	 * @see #TypeDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.industry.Warehouse#lookForType(AUID)
	 * @see tv.amwa.maj.industry.Warehouse#lookForType(String)
	 */
	public final static TypeDefinitionSet TypeDefinitionStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050c00, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"TypeDefinitionStrongReferenceSet",
			TypeDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.KLVDataDefinition KLV data definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.KLVDataDefinition
	 * @see #KLVDataDefinitionStrongReference
	 * @see #KLVDataStrongReferenceVector
	 * @see tv.amwa.maj.model.Dictionary#getKLVDataDefinitions()
	 */
	public final static TypeDefinitionSet KLVDataDefinitionStrongReferenceSet  = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050d00, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"KLVDataDefinitionStrongReferenceSet",
			KLVDataDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.TaggedValueDefinition tagged value definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.TaggedValueDefinition
	 * @see #TaggedValueDefinitionStrongReference
	 * @see tv.amwa.maj.model.Dictionary#getTaggedValueDefinitions()
	 */
	public final static TypeDefinitionSet TaggedValueDefinitionStrongReferenceSet = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050e00, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"TaggedValueDefinitionStrongReferenceSet",
			TaggedValueDefinitionStrongReference);

	/**
	 * <p>Defines the type for a value that represents a set of weak references to
	 * {@linkplain tv.amwa.maj.model.DataDefinition data definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.DataDefinition
	 * @see #DataDefinitionStrongReference
	 * @see #DataDefinitionStrongReferenceSet
	 * @see #DataDefinitionWeakReference
	 * @see #DataDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReferenceVector
	 * @see tv.amwa.maj.model.CodecDefinition#getEssenceKinds()
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 */
	public final static TypeDefinitionSet DataDefinitionWeakReferenceSet = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05030d00, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"DataDefinitionWeakReferenceSet",
			DataDefinitionWeakReference);

	/**
	 * <p>Defines the type for a value that represents a set of weak references to
	 * {@linkplain tv.amwa.maj.model.ParameterDefinition parameter definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.ParameterDefinition
	 * @see #ParameterDefinitionStrongReference
	 * @see #ParameterDefinitionStrongReferenceSet
	 * @see #ParameterDefinitionWeakReference
	 * @see tv.amwa.maj.model.OperationDefinition#getOperationParametersDefined()
	 * @see tv.amwa.maj.constant.ParameterConstant
	 */
	public final static TypeDefinitionSet ParameterDefinitionWeakReferenceSet = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05030e00, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"ParameterDefinitionWeakReferenceSet",
			ParameterDefinitionWeakReference);

	/**
	 * <p>Defines the type for a value that represents a set of weak references to
	 * {@linkplain tv.amwa.maj.model.PluginDefinition plugin definitions}.</p>
	 * 
	 * @see tv.amwa.maj.model.PluginDefinition
	 * @see #PluginDefinitionStrongReference
	 * @see #PluginDefinitionStrongReferenceSet
	 * @see #PluginDefinitionWeakReference
	 * @see tv.amwa.maj.constant.PluginIdentifiers
	 */
	// Does not appear to be used in current baseline
	public final static TypeDefinitionSet PluginDefinitionWeakReferenceSet = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05030f00, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"PluginDefinitionWeakReferenceSet",
			PluginDefinitionWeakReference);

	/**
	 * <p>Defines the type for a value that represents a set of weak references to
	 * {@linkplain tv.amwa.maj.meta.PropertyDefinition property definitions}.</p>
	 * 
	 * @see tv.amwa.maj.meta.PropertyDefinition
	 * @see #PropertyDefinitionStrongReference
	 * @see #PropertyDefinitionStrongReferenceSet
	 * @see #PropertyDefinitionWeakReference
	 * @see tv.amwa.maj.industry.MediaProperty
	 */
	// Does not appear to be used in current baseline
	public final static TypeDefinitionSet PropertyDefinitionWeakReferenceSet = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05031000, (short) 0x0000, (short) 0x0000, 
					new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"PropertyDefinitionWeakReferenceSet",
			PropertyDefinitionWeakReference);

	/**
	 * <p>Defines the type for a value that represents a set of strong references to
	 * {@linkplain tv.amwa.maj.model.DescriptiveObject descriptive objects}.</p>
	 * 
	 * @see tv.amwa.maj.model.DescriptiveObject
	 * @see #DescriptiveObjectStrongReference
	 * @see #DescriptiveObjectStrongReferenceVector
	 * @see tv.amwa.maj.model.DescriptiveMarker#getDescriptiveFrameworkObject()
	 */
	public final static TypeDefinitionSet DescriptiveObjectStrongReferenceSet = new TypeDefinitionSetImpl(
			new AUIDImpl(0x05050F00, (short) 0x0000, (short) 0x0000,
					new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
			"DescriptiveObjectStrongReferenceSet",
			DescriptiveObjectStrongReference);

	/**
	 * <p>Defines the type for a value that represents an array of 
	 * {@linkplain tv.amwa.maj.integer.UInt8 unsigned 8-bit integers (UInt8)} with
	 * a variable number of elements.</p>
	 * 
	 * @see #UInt8
	 * @see tv.amwa.maj.integer.UInt8Array
	 * @see #DataValue
	 */
	public final static TypeDefinitionVariableArray UInt8Array = new TypeDefinitionVariableArrayImpl(
			new AUIDImpl(0x04010100, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"UInt8Array",
			UInt8);

	/**
	 * <p>Defines the type for a value that represents an array of 
	 * {@linkplain tv.amwa.maj.integer.UInt32 unsigned 32-bit integers (UInt32)} with
	 * a variable number of elements.</p>
	 * 
	 * @see #UInt32
	 * @see #UInt32Set
	 * @see tv.amwa.maj.integer.UInt32Array
	 */
	public final static TypeDefinitionVariableArray UInt32Array = new TypeDefinitionVariableArrayImpl(
			new AUIDImpl(0x04010900, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"UInt32Array",
			UInt32);

	/**
	 * <p>Defines the type for a property value that represents an array of 
	 * {@linkplain tv.amwa.maj.integer.Int32 signed 32-bit integers (Int32)} with
	 * a variable number of elements.</p>
	 * 
	 * @see #Int32
	 * @see tv.amwa.maj.integer.Int32Array
	 */
	public final static TypeDefinitionVariableArray Int32Array = new TypeDefinitionVariableArrayImpl(
			new AUIDImpl(0x04010300, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"Int32Array",
			Int32);

	/**
	 * <p>Defines the type for a value that represents an array of 
	 * {@linkplain tv.amwa.maj.integer.Int64 signed 64-bit integers (Int64)} with
	 * a variable number of elements.</p>
	 * 
	 * @see #Int64
	 * @see tv.amwa.maj.integer.Int64Array
	 */
	public final static TypeDefinitionVariableArray Int64Array = new TypeDefinitionVariableArrayImpl(
			new AUIDImpl(0x04010400, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"Int64Array",
			Int64);

	/**
	 * <p>Defines the type for a value that represents an array of 
	 * {@linkplain #UTF16String UTF 16-bit strings} with
	 * a variable number of elements.</p>
	 * 
	 * @see #UTF16String
	 * @see tv.amwa.maj.meta.TypeDefinitionString
	 * @see tv.amwa.maj.meta.TypeDefinitionCharacter
	 * @see java.lang.String
	 */
	public final static TypeDefinitionVariableArray UTF16StringArray = new TypeDefinitionVariableArrayImpl(
			new AUIDImpl(0x04010500, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"UTF16StringArray",
			Character);

	/**
	 * <p>Defines the type for a property value that represents a variable-length array of 
	 * {@linkplain #AUID AUID} values. Each element is a 
	 * {@linkplain tv.amwa.maj.record.AUID 16-byte identifier (AUID)} with
	 * a variable number of elements.</p>
	 * 
	 * @see #AUID
	 * @see #AUIDSet
	 * @see tv.amwa.maj.record.AUID
	 * @see tv.amwa.maj.misctype.AUIDArray
	 */
	public final static TypeDefinitionVariableArray AUIDArray = new TypeDefinitionVariableArrayImpl(
			new AUIDImpl(0x04010600, (short) 0x0000, (short) 0x0000,
					new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
			"AUIDArray",
			AUID);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.Component components} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.Component
	 * @see #ComponentStrongReference
	 * @see tv.amwa.maj.model.Sequence#getComponentObjects()
	 */
	public final static TypeDefinitionVariableArray ComponentStrongReferenceVector =
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060100, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ComponentStrongReferenceVector",
				ComponentStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.ControlPoint control points} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.ControlPoint
	 * @see #ControlPointStrongReference
	 * @see tv.amwa.maj.model.VaryingValue#getControlPoints()
	 */
	public final static TypeDefinitionVariableArray ControlPointStrongReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060200, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ControlPointStrongReferenceVector",
				ControlPointStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.Identification identifications} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.Identification
	 * @see #IdentificationStrongReference
	 * @see tv.amwa.maj.model.Preface#getIdentifications()
	 */
	public final static TypeDefinitionVariableArray IdentificationStrongReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060300, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"IdentificationStrongReferenceVector",
				IdentificationStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.Locator locators} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.Locator
	 * @see #LocatorStrongReference
	 * @see tv.amwa.maj.model.EssenceDescriptor#getLocators()
	 * @see tv.amwa.maj.model.PluginDefinition#getPluginLocators()
	 */
	public final static TypeDefinitionVariableArray LocatorStrongReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060400, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"LocatorStrongReferenceVector",
				LocatorStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.Track tracks} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.Track
	 * @see #TrackStrongReference
	 * @see tv.amwa.maj.model.Package#getPackageTracks()
	 */
	public final static TypeDefinitionVariableArray TrackStrongReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060500, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"TrackStrongReferenceVector",
				TrackStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.Segment segments} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.Segment
	 * @see #SegmentStrongReference
	 * @see tv.amwa.maj.model.Selector#getAlternateSegments()
	 */
	public final static TypeDefinitionVariableArray SegmentStrongReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060600, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"SegmentStrongReferenceVector",
				SegmentStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.SourceReferenceSegment source references}.</p>
	 * 
	 * @see tv.amwa.maj.model.SourceReferenceSegment
	 * @see #SourceReferenceStrongReference
	 * @see tv.amwa.maj.model.EssenceGroup#getChoices()
	 */
	public final static TypeDefinitionVariableArray SourceReferenceStrongReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060700, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"SourceReferenceStrongReferenceVector",
				SourceReferenceStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.TaggedValue tagged values} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.TaggedValue
	 * @see #TaggedValueStrongReference
	 * @see tv.amwa.maj.model.Component#getComponentAttributes()
	 * @see tv.amwa.maj.model.Component#getComponentUserComments()
	 * @see tv.amwa.maj.model.Package#getPackageAttributes()
	 * @see tv.amwa.maj.model.Package#getPackageUserComments()
	 */
	public final static TypeDefinitionVariableArray TaggedValueStrongReferenceVector  = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060800, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"TaggedValueStrongReferenceVector",
				TaggedValueStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.KLVData KLV data values} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.KLVData
	 * @see #KLVDataStrongReference
	 * @see tv.amwa.maj.model.Component#getComponentKLVData()
	 * @see tv.amwa.maj.model.Package#getPackageKLVData()
	 */
	public final static TypeDefinitionVariableArray KLVDataStrongReferenceVector  = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060900, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"KLVDataStrongReferenceVector",
				KLVDataStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.Parameter parameter values} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.Parameter
	 * @see #ParameterStrongReference
	 * @see tv.amwa.maj.model.OperationGroup#getParameters()
	 * @see tv.amwa.maj.constant.ParameterConstant
	 */
	public final static TypeDefinitionVariableArray ParameterStrongReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060a00, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ParameterStrongReferenceVector",
				ParameterStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of weak references to
	 * {@linkplain tv.amwa.maj.model.OperationDefinition operation definitions} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.OperationDefinition
	 * @see #OperationDefinitionStrongReference
	 * @see #OperationDefinitionStrongReferenceSet
	 * @see #OperationDefinitionWeakReference
	 * @see tv.amwa.maj.model.OperationDefinition#getDegradeToOperations()
	 * @see tv.amwa.maj.constant.OperationConstant
	 */
	public final static TypeDefinitionVariableArray OperationDefinitionWeakReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05040100, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"OperationDefinitionWeakReferenceVector",
				OperationDefinitionWeakReference);

	/**
	 * <p>Defines the type for a value that represents a list of weak references to
	 * {@linkplain tv.amwa.maj.meta.TypeDefinition type definitions} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.meta.TypeDefinition
	 * @see #TypeDefinitionWeakReference
	 * @see #TypeDefinitionStrongReferenceSet
	 * @see tv.amwa.maj.meta.TypeDefinitionRecord#getMemberType(int)
	 * @see tv.amwa.maj.industry.Warehouse#lookForType(AUID)
	 * @see tv.amwa.maj.industry.Warehouse#lookForType(String)
	 */
	public final static TypeDefinitionVariableArray TypeDefinitionWeakReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05040200, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"TypeDefinitionWeakReferenceVector",
				TypeDefinitionWeakReference);

	/**
	 * <p>Defines the type for a value that represents a list of weak references to
	 * {@linkplain tv.amwa.maj.model.DataDefinition data definitions} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.DataDefinition
	 * @see #DataDefinitionStrongReference
	 * @see #DataDefinitionStrongReferenceSet
	 * @see #DataDefinitionWeakReference
	 * @see #DataDefinitionWeakReferenceSet
	 * @see tv.amwa.maj.model.CodecDefinition#getEssenceKinds()
	 */
	public final static TypeDefinitionVariableArray DataDefinitionWeakReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05040300, (short) 0x0000, (short) 0x0000, 
						new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"DataDefinitionWeakReferenceVector",
				DataDefinitionWeakReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.SubDescriptor sub descriptors} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.SubDescriptor
	 * @see #SubDescriptorStrongReference
	 * @see tv.amwa.maj.model.EssenceDescriptor#getSubDescriptors()
	 */
	public final static TypeDefinitionVariableArray SubDescriptorStrongReferenceVector =
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060E00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"SubDescriptorStrongReferenceVector",
				SubDescriptorStrongReference);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.AAFFileDescriptor file descriptors} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.AAFFileDescriptor
	 * @see #FileDescriptorStrongReference
	 * @see tv.amwa.maj.model.MultipleDescriptor#getFileDescriptors()
	 */
	public final static TypeDefinitionVariableArray FileDescriptorStrongReferenceVector =
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060B00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"FileDescriptorStrongReferenceVector",
				FileDescriptorStrongReference);

	/**
	 * <p>Defines the type for a value that represents an array of 
	 * {@linkplain tv.amwa.maj.enumeration.ChannelStatusModeType AES3 PCM channel status modes} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.AES3PCMDescriptor
	 * @see tv.amwa.maj.model.AES3PCMDescriptor#getChannelStatusMode()
	 */	
	public final static TypeDefinitionVariableArray ChannelStatusModeArray =
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x04010A00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ChannelStatusModeArray",
				ChannelStatusModeType);

	/**
	 * <p>Defines the type for a property value that represents an array of 
	 * {@linkplain tv.amwa.maj.enumeration.UserDataModeType AES3 PCM user data modes} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.AES3PCMDescriptor
	 * @see tv.amwa.maj.model.AES3PCMDescriptor#getUserDataMode()
	 */	
	public final static TypeDefinitionVariableArray UserDataModeArray =
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x04010B00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"UserDataModeArray",
				UserDataModeType);

	/**
	 * <p>Defines the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.RIFFChunk Broadcast Wave Format (BWF) RIFF chunks} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.RIFFChunk
	 * @see #RIFFChunkStrongReference
	 * @see tv.amwa.maj.model.BWFImportDescriptor#getUnknownBWFChunks()
	 */
	public final static TypeDefinitionVariableArray RIFFChunkStrongReferenceVector =
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060C00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"RIFFChunkStrongReferenceVector",
				RIFFChunkStrongReference);

	/**
	 * <p>Define the type for a value that represents a list of strong references to
	 * {@linkplain tv.amwa.maj.model.DescriptiveObject descriptive object} with
	 * a variable number of elements.</p>
	 * 
	 * @see tv.amwa.maj.model.DescriptiveObject
	 * @see #DescriptiveObjectStrongReference
	 * @see #DescriptiveObjectStrongReferenceSet
	 */
	public final static TypeDefinitionVariableArray DescriptiveObjectStrongReferenceVector = 
		new TypeDefinitionVariableArrayImpl(
				new AUIDImpl(0x05060D00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"DescriptiveObjectStrongReferenceVector",
				DescriptiveObjectStrongReference);
	
	// Additional definitions to support MXF 2011-11 superset baseline
	
	public final static TypeDefinitionStrongObjectReference PrefaceStrongReference =
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05022800, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"PrefaceStrongReference", 
				Warehouse.lookForClass(PrefaceImpl.class));
		
	public final static TypeDefinitionStrongObjectReference MetaDefinitionStrongReference =
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05022900, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"PrefaceStrongReference", 
				Warehouse.lookForClass(MetaDefinitionImpl.class));
	
	public final static TypeDefinitionStrongObjectReference ExtensionSchemeStrongReference =
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05022a00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ExtensionSchemeStrongReference", 
				Warehouse.lookForClass(ExtensionSchemeImpl.class));
	
	public final static TypeDefinitionStrongObjectReference PackageMarkerStrongReference =
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05022b00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}), 
				"PackageMarkerStrongReference", 
				Warehouse.lookForClass(PackageMarkerImpl.class));
	
	public final static TypeDefinitionStrongObjectReference ApplicationPluginObjectStrongReference =
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0x05022c00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}), 
				"ApplicationPluginObjectStrongReference", 
				Warehouse.lookForClass("ApplicationPluginObject"));
	
	public final static TypeDefinitionSet MetaDefinitionStrongReferenceSet =
		new TypeDefinitionSetImpl(
				new AUIDImpl(0x05051100, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"MetaDefinitionStrongReferenceSet",
				MetaDefinitionStrongReference);
	
	public final static TypeDefinitionSet ExtensionSchemeStrongReferenceSet =
		new TypeDefinitionSetImpl(
				new AUIDImpl(0x05051200, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ExtensionSchemeStrongReferenceSet", 
				ExtensionSchemeStrongReference);
	
	public final static TypeDefinitionSet ApplicationPluginObjectStrongReferenceSet =
		new TypeDefinitionSetImpl(
				new AUIDImpl(0x05051300, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ApplicationPluginObjectStrongReferenceSet",
				ApplicationPluginObjectStrongReference);
				
	public final static TypeDefinitionWeakObjectReference ExtensionSchemeWeakReference =
		new TypeDefinitionWeakObjectReferenceImpl(
				new AUIDImpl(0x05010d00, (short) 0x0000, (short) 0x0000,
						new byte[] {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x04, 0x01, 0x01}),
				"ExtensionSchemeWeakReference", 
				Warehouse.lookForClass(ExtensionSchemeImpl.class), 
				new AUIDImpl[] { 
					new AUIDImpl(0x06010107, (short) 0x1a00, (short) 0x0000,
							new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0d })
				} );
}
