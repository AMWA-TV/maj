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

package tv.amwa.maj.io.aaf;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

public interface AAFConstants
	extends CommonConstants {

	public final static AUID ByteOrderPropertyID =
		new AUIDImpl(0x03010201, (short) 0x0200, (short) 0x0000,
				new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01});

	public final static AUID RootPrefaceProperty =
		new AUIDImpl(0x0d010301, (short) 0x0102, (short) 0x0100,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 });

	public final static AUID RootMetaDictionaryProperty =
		new AUIDImpl(0x0d010301, (short) 0x0101, (short) 0x0100,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 });

	public final static AUID LocalIdentificationProperty =
		new AUIDImpl(0x06010107, (short) 0x0500, (short) 0x0000,
			new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02});

	public final static AUID MetaDefinitionIDProperty =
		new AUIDImpl(0x06010107, (short) 0x1300, (short) 0x0000,
				new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02});

	public final static AUID IdentificationGenerationIDProperty =
		new AUIDImpl(0x05200701, (short) 0x0100, (short) 0x0000,
				new byte[] { 0x6, 0xe, 0x2b, 0x34, 0x1, 0x1, 0x1, 0x2 });

	public final static byte[] AAFSignatureSSBinaryBytes = new byte[] {
		0x41, 0x41, 0x46, 0x42, 0x0d, 0x00, 0x4f, 0x4d,
		0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, (byte) 0xff
	};

	public final static AUID AAFSignatureSSBinary =
		new AUIDImpl(0x42464141, (short) 0x000d, (short) 0x4d4f,
				new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, (byte) 0xff});

	public final static byte[] AAFSignatureSSBin4KBytes = new byte[] {
		0x01, 0x02, 0x01, 0x0d, 0x00, 0x02, 0x00, 0x00,
		0x06, 0x0e, 0x2b, 0x34, 0x03, 0x02, 0x01, 0x01
	};

	public final static AUID AAFSignatureSSBin4K =
		new AUIDImpl(0x0d010201, (short) 0x0200, (short) 0x0000,
				new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x03, 0x02, 0x01, 0x01});

	public final static byte[] rootEntryClassID = new byte[] {
		(byte) 0xa5, (byte) 0x98, (byte) 0xb3, (byte) 0xb3, (byte) 0x90, (byte) 0x1c, (byte) 0xd4, 0x11,
		(byte) 0x80, 0x53, 0x08, 0x00, 0x36, 0x21, 0x08, 0x04
	};

	public final static short SF_DATA = 0x0082;
	public final static short SF_DATA_STREAM = 0x0042;
	public final static short SF_STRONG_OBJECT_REFERENCE = 0x0022;
	public final static short SF_STRONG_OBJECT_REFERENCE_VECTOR = 0x0032;
	public final static short SF_STRONG_OBJECT_REFERENCE_SET = 0x003a;
	public final static short SF_WEAK_OBJECT_REFERENCE = 0x0002;
	public final static short SF_WEAK_OBJECT_REFERENCE_VECTOR = 0x0012;
	public final static short SF_WEAK_OBJECT_REFERENCE_SET = 0x001a;

	public final static String META_DICTIONARY_DIRNAME = "MetaDictionary-1";
	public final static String PREFACE_DIRNAME = "Header-2";
	public final static String PROPERTIES_STREAMNAME = "properties";
	public final static String REFERENCED_PROPERTIES_STREAMNAME = "referenced properties";

	/**
	 * <p>Should structured storage directory names be mapped back to pre-unified AAF names on
	 * the writing of AAF files.</p>
	 *
	 * @see #directoryNameAliases
	 */
	public final static boolean MAP_SS_DIRECTORY_NAMES = true;

	public final static String[] directoryNameAliases = {
		"Header",                              "Preface",
		"Annotation",                          "AnnotationSource",
		"Selected",                            "SelectedSegment",
		"Segment",                             "TrackSegment",
		"OperationGroup",                      "TransitionOperation",
		"Description",                         "DescriptiveFrameworkObject",
		"Content",                             "ContentStorageObject",
		"Dictionary",                          "Dictionaries",
		"KLVData",                             "ComponentKLVData",
		"KLVData",                             "PackageKLVData",
		"UserComments",                        "PackageUserComments",
		"UserComments",                        "ComponentUserComments",
		"Attributes",                          "PackageAttributes",
		"Attributes",                          "ComponentAttributes",
		"Slots",                               "NestedScopeTracks",
		"Alternates",                          "AlternateSegments",
		"Components",                          "ComponentObjects",
		"Locators",                            "PluginLocators",
		"Locator",                             "Locators", // EssenceDescriptor
		"Slots",                               "PackageTracks",
		"Mobs",                                "Packages",
		"EssenceData",                         "EssenceDataObjects",
		"ParametersDefined",				   "OperationParametersDefined"

	};

	public final static boolean MAP_SS_PROPERTY_NAMES = true;

	public final static String[] propertyNameAliases = {
		"LinkedGenerationID",					"Generation",
		"TrackID",								"MobSlotID",
		"ObjectClass",							"ObjClass",
		"ComponentDataDefinition",				"DataDefinition",
		"ComponentLength",						"Length",
		"ComponentUserComments",				"UserComments",
		"ComponentKLVData",						"KLVData",
		"ComponentAttributes",					"Attributes",
		"ComponentObjects",						"Components",
		"SourcePackageID",						"SourceID",
		"SourceTrackID",						"SourceMobSlotID",
		"MonoSourceTrackIDs",					"MonoSourceSlotIDs",
		"StartPosition",						"StartTime",
		"DropFrame",							"Drop",
		"FramesPerSecond",						"FPS",
		"StartTimecode",						"Start",
		"Packages",								"Mobs",
		"EssenceDataObjects",					"EssenceData",
		"OperationParametersDefined",			"ParametersDefined",
		"OperationInputCount",					"NumberInputs",
		"OperationDataDefinition",				"DataDefinition",
		"ParameterType",						"Type",
		"ParameterDisplayUnits",				"DisplayUnits",
		"DeviceManufacturerName",				"Manufacturer",
		"PluginVersion",						"VersionNumber",
		"PluginVersionString",					"VersionString",
		"PluginPlatform",						"Platform",
		"PluginLocators",						"Locators",
		"ImplementedClass",						"DefinitionObject",
		"CodecDataDefinitions",					"DataDefinitions",
		"LinkedPackageID",						"MobID",
		"EssenceStream",						"Data",
		"Locators",								"Locator",
		"EssenceLength",						"Length",
		"LinkedTrackID",						"LinkedSlotID",
		"Codec",								"CodecDefinition",
		"PictureCompression",					"Compression",
		"ImageStartOffset",						"FieldStartOffset",
		"ImageEndOffset",						"FieldEndOffset",
		"BlackRefLevel",						"BlackReferenceLevel",
		"WhiteRefLevel",						"WhiteReferenceLevel",
		"AlphaSampleDepth",						"AlphaSamplingWidth",
		"ComponentDepth",						"ComponentWidth",
		"TapeCapacity",							"Length",
		"TapeFormFactor",						"FormFactor",
		"TapeFormulation",						"Model",
		"TapeManufacturer",						"ManufacturerID",
		"FormatVersion",						"Version",
		"ContentStorageObject",					"Content",
		"Dictionaries",							"Dictionary",
		"FileLastModified",						"LastModified",
		"GenerationID",							"GenerationAUID",
		"ApplicationVersion",					"ProductVersion",
		"ApplicationProductID",					"ProductID",
		"ApplicationSupplierName",				"CompanyName",
		"ApplicationName",						"ProductName",
		"ApplicationVersionString",				"ProductVersionString",
		"ApplicationPlatform",					"Platform",
		"FileModificationDate",					"Date",
		"URL",									"URLString",
		"PackageUserComments",					"UserComments",
		"PackageName",							"Name",
		"PackageTracks",						"Slots",
		"PackageUsage",							"UsageCode",
		"PackageLastModified",					"LastModified",
		"PackageKLVData",						"KLVData",
		"PackageAttributes",					"Attributes",
		"PackageID",							"MobID",
		"TrackID",								"SlotID",
		"TrackName",							"SlotName",
		"EssenceTrackNumber",					"PhysicalTrackNumber",
		"TrackSegment",							"Segment",
		"UserPosition",							"UserPos",
		"Tag",									"Name",
		"IndirectValue",						"Value",
		"KLVDataValue",							"Value",
		"ElectrospatialFormulation",			"ElectroSpatial",
		"AudioReferenceLevel",					"AudioRefLevel",
		"ChannelCount",							"Channels",
		"AudioSampleRate",						"AudioSamplingRate",
		"SoundCompression",						"Compression",
		"AverageBytesPerSecond",				"AverageBPS",
		"ExtendibleEnumerationElementValues",	"ElementValues",
		"GenerationID",							"Generation",
		"ExtendibleEnumerationElementNames",	"ElementNames",
		"EdgeCodeHeader",						"Header",
		"EdgeCodeStart",						"Start",
		"EdgeCodeFormat",						"CodeFormat",
		"EdgeCodeFilmFormat",					"FilmKind",
		"EventPosition",						"Position",
		"EventComment",							"Comment",
		"AnnotationSource",						"Annotation",
		"NestedScopeTracks",					"Slots",
		"RelativeTrack",						"RelativeSlot",
		"AlternateSegments",					"Alternates",
		"SelectedSegment",						"Selected",
		"TimecodeStreamData",					"Source",
		"TimecodeStreamSampleRate",				"SampleRate",
		"TimecodeSource",						"SourceType",
		"TransitionOperation",					"OperationGroup",
		"ControlPointTime",						"Time",
		"ControlPointValue",					"Value",
		"AIFCSummary",							"Summary",
		"TIFFSummary",							"Summary",
		"WAVESummary",							"Summary",
		"FilmFormatName",						"FilmGaugeFormat",
		"FilmStockKind",						"Model",
		"FilmStockManufacturer",				"Manufacturer",
		"Generation",							"GenerationAUID",
		"LocationName",							"Name",
		"CompositionRendering",					"Rendering",
		"EventTrackEditRate",					"EditRate",
		"EventTrackOrigin",						"EventSlotOrigin",
		"ParameterDefinitionReference",			"Definition",
		"DescriptiveFrameworkObject",			"Description",
		"DescribedTrackIDs",					"DescribedSlots",
		"DescriptiveClipDescribedTrackIDs",		"DescribedSlotIDs",
		"MIMEType",								"MimeType",
	};

	public final static boolean MAP_SS_CLASS_NAMES = true;

	public final static String[] classNameAliases = {
		"PictureDescriptor",					"DigitalImageDescriptor",
		"Preface",								"Header",
		"Package",								"Mob",
		"CompositionPackage",					"CompositionMob",
		"MaterialPackage",						"MasterMob",
		"SourcePackage",						"SourceMob",
		"Track",								"MobSlot",
		"EventTrack",							"EventMobSlot",
		"StaticTrack",							"StaticMobSlot",
		"TimelineTrack",						"TimelineMobSlot",
		"WAVEPCMDescriptor",					"PCMDescriptor",
	};

	public final static boolean MAP_SS_TYPE_NAMES = true;

	public final static String[] typeNameAliases = {
		"ProductVersionType",					"ProductVersion",
		"PackageIDType",						"MobIDType",
		"TrackStrongReferenceVector",			"MobSlotStrongReferenceVector",
		"TrackStrongReference",					"MobSlotStrongReference",
		"PackageWeakReference",					"MobWeakReference",
		"PackageStrongReferenceSet",			"MobStrongReferenceSet",
		"PackageStrongReference",				"MobStrongReference",
	};

	public static final Object ParametersID = new AUIDImpl(
			0x06010104, (short) 0x060a, (short) 0x0000,
			new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02});

	public static final int STREAM_CHUNK_SIZE = 2048;

}
