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
 * $Log: LookupTable.java,v $
 * Revision 1.6  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.5  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/02/08 11:50:17  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2007/12/04 09:19:43  vizigoth
 * Minor comment updates.
 *
 * Revision 1.1  2007/12/03 13:11:39  vizigoth
 * Moved over from misctype package, tidied up, added JUnit tests and all types from the AAF spec version 1.1.
 */

package tv.amwa.maj.util;

import java.util.Hashtable;

/**
 * <p>
 * Static table of mappings from AAF specification data type names to MAJ API
 * Java type names and <a href="http://aaf.sourceforge.net/">AAF SDK</a> C type
 * definition names (as defined in file "<code>AAFTypes.h</code>"). The
 * table allows the details of the Java representation of an AAF data type to be
 * looked up by defined name or the name of the equivalent structure used in the
 * C-based AAF SDK. The
 * {@linkplain Strategy strategy used to represent the AAF data in the MAJ API}
 * for each AAF data type is also available, with the exception of <code>PixelRectangle</code>
 * and <code>RationalRectangle</code>.</p>
 * 
 * <p>For example, consider the following information about AAF data type <code>UInt32</code>:</p>
 * 
 * <ul>
 *  <li><code>UInt32</code> is represented by the Java primitive type <code>int</code>, which is 
 *  returned from the method call {@link #lookupMAJType(String) lookupMAJType("UInt32")}.</li>
 *  <li>In the <a href="../iface/package-summary.html">interface definitions</a> of the MAJ API, the Java
 *  primitive type is labelled with annotation {@link tv.amwa.maj.integer.UInt32} wherever it is 
 *  used to represent an AAF <code>UInt32</code>. This is known as the {@linkplain LookupTable.Strategy#Annotation 
 *  annotation strategy} and can be found with the following method call: 
 *  {@link #lookupMAJTypeStrategy(String) lookupMAJTypeStrategy("UInt32")}.</li>
 *  <li>In the C-based AAF SDK, an <code>UInt32</code> value is represented by a type definition
 *  called "<code>aafUInt32</code>". The AAF type name can be discovered by a call to:
 *  {@link #lookupAAFTypeForCType(String) lookupAAFTypeForCType("aafUInt32")}.</li>
 * </ul>
 * 
 * @deprecated Ongoing maintenance of this class is not expected. The table was created in
 * January 2007 from version&nbsp;1.1 of the AAF SDK and is not used by any
 * other part of the MAJ API. Developers may find this utility class useful but
 * are advised to check any results that are produced against the current state
 * of the AAF SDK and MAJ API. All type names in section&nbsp;21 of the <a
 * href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF object
 * specification version&nbsp;1.1</a> are included.
 * 
 *
 * 
 */
@Deprecated
public final class LookupTable {

	/**
	 * <p>
	 * Defines the kind of strategy used to represent a particular AAF data type
	 * in the MAJ API.
	 * </p>
	 * 
	 *
	 * 
	 */
	public enum Strategy {

		/**
		 * <p>
		 * The representation of the AAF data type uses a Java
		 * {@linkplain java.lang.Enum  enumeration}. For example, the AAF
		 * enumeration <code>EditHintType</code> defined in the AAF object
		 * specification is represented by the Java enumeration
		 * {@link tv.amwa.maj.enumeration.EditHintType}. All such enumerations
		 * are defined in the <a href="../enumeration/package-summary.html">enumeration 
		 * package</a>.
		 * </p>
		 */
		Enumeration,

		/**
		 * <p>
		 * The representation of an AAF data type using a Java interface and
		 * implementing class. For example, the AAF data type
		 * <code>Timestamp</code> is represented in Java by the interface
		 * {@link tv.amwa.maj.record.TimeStamp}. The fields of the AAF data
		 * type as defined as a C structure in the AAF SDK have been translated
		 * to get and set methods of the interface.
		 * </p>
		 */
		Class,

		/**
		 * <p>
		 * The representation of an AAF data type using a Java class or
		 * primitive type that should also be labelled with an annotation taken
		 * from the <a href="../integer/package-summary.html">integer</a> or
		 * <a href="../misctype/package-summary.html">miscellaneous type</a> packages. 
		 * For example, the <code>FrameOffset</code> AAF type is represented in Java by the
		 * primitive type <code>long</code> and values should be labelled with
		 * the Java annotation {@link tv.amwa.maj.misctype.FrameOffset}.
		 * </p>
		 */
		Annotation,

		/**
		 * <p>
		 * The representation of a data type in the C-based AAF SDK defined as a
		 * <code>union</code>. This is represented in Java as an abstract
		 * interface that is inherited by sub-interfaces representing the
		 * alternative values of the C union. Examples of interfaces for
		 * abstract union classes can be found in the <a href="../union/package-summary.html">union
		 * package</a>, with implementations in the <a href="../argument/package-summary.html">argument
		 * package</a>.
		 * </p>
		 */
		AbstractUnion;
	}

	/**
	 * <p>
	 * Representation of a single item in a map to and from an AAF SDK C data
	 * type and a MAJ API Java type.
	 * </p>
	 */
	final static class TypeItem {

		/**
		 * <p>
		 * Name used to represent the data type in the AAF specification, or if
		 * not specified a unique identifier for the type used in the MAJ API.
		 * </p>
		 */
		private String specName;
		/**
		 * <p>
		 * Underlying Java type used to represent the AAF data type, which is
		 * either a Java primitive type or a the name of a Java interface.
		 * </p>
		 */
		private String javaBaseType;
		/**
		 * <p>
		 * The kind of mapping from AAF data type to Java type, as described in
		 * the documentation for enumeration {@link Strategy}.
		 * </p>
		 */
		private Strategy kind;
		/**
		 * <p>
		 * List of type definition names used for the AAF data type in the
		 * C-based AAF SDK.
		 * </p>
		 */
		private String[] cNames;

		/**
		 * <p>
		 * Create a new map item linking an AAF SDK data type with a MAJ API
		 * Java type.
		 * </p>
		 * 
		 * @param specName
		 *            Name used to represent the data type in the AAF
		 *            specification, or if not specified a unique identifier for
		 *            the type used in the MAJ API.
		 * @param javaBaseType
		 *            Underlying Java type used to represent the AAF data type,
		 *            which is either a Java primitive type or a the name of a
		 *            Java interface.
		 * @param kind
		 *            The kind of mapping from AAF data type to Java type, as
		 *            described in the documentation for enumeration
		 *            {@link Strategy}.
		 * @param names
		 *            List of type definition names used for the AAF data type
		 *            in the C-based AAF SDK.
		 */
		public TypeItem(String specName, String javaBaseType, Strategy kind,
				String[] names) {

			this.specName = specName;
			this.javaBaseType = javaBaseType;
			this.kind = kind;
			cNames = names;
		}

		public String[] getCNames() {
			return cNames;
		}

		public String getJavaBaseType() {
			return javaBaseType;
		}

		public String getAAFName() {
			return specName;
		}

		public Strategy getKind() {
			return kind;
		}
	}

	/**
	 * <p>
	 * Lookup table for AAF type name to a type item description.
	 */
	final static Hashtable<String, TypeItem> table;
	/**
	 * <p>
	 * Lookup table for AAF SDK C type name to a type item description.
	 */
	final static Hashtable<String, TypeItem> reverseTable;

	static {

		table = new Hashtable<String, TypeItem>();
		reverseTable = new Hashtable<String, TypeItem>();
		table.put("AlphaTransparencyType", new TypeItem(
				"AlphaTransparencyType", "AlphaTransparencyType",
				Strategy.Enumeration, new String[] { "aafAlphaTransparency" }));
		table.put("AppendOption", new TypeItem("AppendOption", "AppendOption",
				Strategy.Enumeration, new String[] { "aafAppendOption" }));
		table.put("ArgIDType", new TypeItem("ArgIDType", "AUID",
				Strategy.Annotation,
				new String[] { "aafArgIDType", "aafArgID" }));
		table.put("AttributeKind", new TypeItem("AttributeKind",
				"AttributeKind", Strategy.Enumeration,
				new String[] { "aafAttributeKind" }));
		table.put("AudioSample", new TypeItem("AudioSample", "AudioSample",
				Strategy.Enumeration, new String[] { "aafAudioSampleType",
						"aafAudioSample" }));
		table.put("Boolean", new TypeItem("Boolean", "boolean",
				Strategy.Annotation, new String[] { "aafBool", "aafBoolean" }));
		table.put("ByteOrder", new TypeItem("ByteOrder", "ByteOrder",
				Strategy.Enumeration, new String[] { "aafByteOrder" }));
		table.put("Char", new TypeItem("Char", "char", Strategy.Annotation,
				new String[] { "aafCharacter", "wchar", "WChar", "wChar" }));
		table.put("ClassID", new TypeItem("ClassID", "AUID",
				Strategy.Annotation, new String[] { "aafClassID" }));
		table.put("CodecID", new TypeItem("CodecID", "AUID",
				Strategy.Annotation, new String[] { "aafCodecID" }));
		table.put("ColorSitingType", new TypeItem("ColorSitingType",
				"ColorSitingType", Strategy.Enumeration,
				new String[] { "aafColorSiting" }));
		table.put("ColorSpace", new TypeItem("ColorSpace", "ColorSpace",
				Strategy.Enumeration, new String[] { "aafColorSpace" }));
		table.put("CompCodeArray", new TypeItem("CompCodeArray",
				"RGBAComponent[]", Strategy.Annotation,
				new String[] { "aafCompArray" }));
		table.put("CompressEnable", new TypeItem("CompressEnable",
				"CompressEnable", Strategy.Enumeration,
				new String[] { "aafCompressEnable" }));
		table.put("CompSizeArray", new TypeItem("CompSizeArray", "byte[]",
				Strategy.Annotation, new String[] { "aafCompSizeArray" }));
		table.put("CriteriaType", new TypeItem("CriteriaType", "CriteriaType",
				Strategy.Enumeration, new String[] { "aafCriteriaType" }));
		table.put("DataBuffer", new TypeItem("DataBuffer", "byte[]",
				Strategy.Annotation, new String[] { "aafDataBuffer" }));
		table.put("DataDefinitionMatch", new TypeItem("DataDefinitionMatch",
				"DataDefinitionMatch", Strategy.Enumeration, new String[] {
						"aafDataDefinitionMatch", "DataDefinitionMatch" }));
		table.put("DataValue", new TypeItem("DataValue", "byte[]",
				Strategy.Annotation, new String[] { "aafDataValue" }));
		table.put("DateStruct", new TypeItem("DateStruct", "DateStruct",
				Strategy.Class, new String[] { "aafDateStruct" }));
		table.put("DefaultFade", new TypeItem("DefaultFade", "DefaultFade",
				Strategy.Class, new String[] { "aafDefaultFade" }));
		table.put("DefinitionCrit", new TypeItem("DefinitionCrit",
				"DefinitionCrit", Strategy.AbstractUnion,
				new String[] { "aafDefinitionCrit" }));
		table
				.put("DefinitionCriteriaType", new TypeItem(
						"DefinitionCriteriaType", "DefinitionCriteriaType",
						Strategy.Enumeration,
						new String[] { "aafDefinitionCritType" }));
		table.put("DefinitionKind", new TypeItem("DefinitionKind",
				"DefinitionKind", Strategy.Enumeration,
				new String[] { "aafDefinitionKind" }));
		table.put("Depend", new TypeItem("Depend", "Depend",
				Strategy.Enumeration, new String[] { "aafDepend" }));
		table.put("DirectionCode", new TypeItem("DirectionCode",
				"DirectionCode", Strategy.Enumeration,
				new String[] { "aafDirectionCode" }));
		table.put("Edgecode", new TypeItem("Edgecode", "EdgecodeValue",
				Strategy.Class, new String[] { "aafEdgecode" }));
		table.put("EdgecodeHeader", new TypeItem("EdgecodeHeader", "byte[]",
				Strategy.Annotation, new String[] { "aafEdgecodeHeader" }));
		table.put("EdgeType", new TypeItem("EdgeType", "EdgeType",
				Strategy.Enumeration, new String[] { "aafEdgeType" }));
		table.put("EditHintType", new TypeItem("EditHintType", "EditHintType",
				Strategy.Enumeration, new String[] { "aafEditHint" }));
		table.put("ElectroSpatialFormulation", new TypeItem(
				"ElectroSpatialFormulation", "ElectroSpatialFormulation",
				Strategy.Enumeration,
				new String[] { "aafElectroSpatialFormulation" }));
		table.put("Engine", new TypeItem("Engine", "AUID", Strategy.Annotation,
				new String[] { "aafEngine" }));
		table.put("FadeType", new TypeItem("FadeType", "FadeType",
				Strategy.Enumeration, new String[] { "aafFadeType" }));
		table.put("FieldDom", new TypeItem("FieldDom", "FieldDom",
				Strategy.Enumeration, new String[] { "aafFieldDom" }));
		table.put("FieldNumber", new TypeItem("FieldNumber", "FieldNumber",
				Strategy.Enumeration, new String[] { "aafFieldNumber" }));
		table.put("FieldTop", new TypeItem("FieldTop", "FieldTop",
				Strategy.Enumeration, new String[] { "aafFieldTop" }));
		table.put("FileAccess", new TypeItem("FileAccess", "FileAccess",
				Strategy.Enumeration, new String[] { "aafFileAccess" }));
		table.put("FileExistence", new TypeItem("FileExistence",
				"FileExistence", Strategy.Enumeration,
				new String[] { "aafFileExistence" }));
		table.put("FileFormat", new TypeItem("FileFormat", "FileFormat",
				Strategy.Enumeration, new String[] { "aafFileFormat" }));
		table.put("FileRev", new TypeItem("FileRev", "FileRev",
				Strategy.Enumeration, new String[] { "aafFileRev" }));
		table.put("FilmType", new TypeItem("FilmType", "FilmType",
				Strategy.Enumeration, new String[] { "aafFilmType" }));
		table.put("FrameLength", new TypeItem("FrameLength", "long",
				Strategy.Annotation, new String[] { "aafFrameLength" }));
		table.put("FrameOffset", new TypeItem("FrameOffset", "long",
				Strategy.Annotation, new String[] { "aafFrameOffset" }));
		table.put("HardwarePlatform", new TypeItem("HardwarePlatform", "AUID",
				Strategy.Annotation, new String[] { "aafHardwarePlatform" }));
		table.put("IdentificationCriteria", new TypeItem(
				"IdentificationCriteria", "IdentificationCriteria",
				Strategy.AbstractUnion,
				new String[] { "aafIdentificationCrit" }));
		table.put("IdentificationCriteriaType", new TypeItem(
				"IdentificationCriteriaType", "IdentificationCriteriaType",
				Strategy.Enumeration,
				new String[] { "aafIdentificationCritType" }));
		table.put("IncludedMedia", new TypeItem("IncludedMedia",
				"IncludedMedia", Strategy.Enumeration,
				new String[] { "aafIncMedia" }));
		table.put("IndexType", new TypeItem("IndexType", "IndexType",
				Strategy.Enumeration, new String[] { "aafIndexType" }));
		table.put("Int16", new TypeItem("Int16", "short", Strategy.Annotation,
				new String[] { "aafInt16" }));
		table.put("Int16Array", new TypeItem("Int16Array", "short[]",
				Strategy.Annotation, new String[] { "aafInt16 *" }));
		table.put("Int32", new TypeItem("Int32", "int", Strategy.Annotation,
				new String[] { "aafInt32" }));
		table.put("Int32Array", new TypeItem("Int32Array", "int[]",
				Strategy.Annotation, new String[] { "aafInt32 *" }));
		table.put("Int64", new TypeItem("Int64", "long", Strategy.Annotation,
				new String[] { "aafInt64" }));
		table.put("Int64Array", new TypeItem("Int64Array", "long[]",
				Strategy.Annotation, new String[] { "aafInt64 *" }));
		table.put("Int8", new TypeItem("Int8", "byte", Strategy.Annotation,
				new String[] { "aafInt8" }));
		table.put("Int8Array", new TypeItem("Int8Array", "byte[]",
				Strategy.Annotation, new String[] { "aafInt8 *" }));
		table.put("InterpolationKind", new TypeItem("InterpolationKind",
				"InterpolationKind", Strategy.Enumeration,
				new String[] { "aafInterpKind" }));
		table.put("JPEGComponent", new TypeItem("JPEGComponent",
				"JPEGComponent", Strategy.Enumeration,
				new String[] { "aafJPEGcomponent" }));
		table.put("JPEGTableIDType", new TypeItem("JPEGTableIDType", "int",
				Strategy.Annotation, new String[] { "aafJPEGTableID" }));
		table.put("LayoutType", new TypeItem("LayoutType", "LayoutType",
				Strategy.Enumeration, new String[] { "aafLayoutType",
						"aafFrameLayout" }));
		table.put("LengthType", new TypeItem("LengthType", "long",
				Strategy.Annotation, new String[] { "aafLength" }));
		table.put("MediaCriteria", new TypeItem("MediaCriteria",
				"MediaCriteria", Strategy.Class,
				new String[] { "aafMediaCriteria" }));
		table.put("MediaOpenMode", new TypeItem("MediaOpenMode",
				"MediaOpenMode", Strategy.Enumeration,
				new String[] { "aafMediaOpenMode" }));
		table.put("MultiCreateItem", new TypeItem("MultiCreateItem",
				"MultiCreateItem", Strategy.Class,
				new String[] { "aafmMultiCreate" }));
		table.put("NumSlots", new TypeItem("NumSlots", "int",
				Strategy.Annotation, new String[] { "aafNumSlots" }));
		table.put("OperationChoice", new TypeItem("OperationChoice",
				"OperationChoice", Strategy.Enumeration,
				new String[] { "aafOperationChoice" }));
		table.put("PhaseFrameType", new TypeItem("PhaseFrameType", "int",
				Strategy.Annotation, new String[] { "aafPhaseFrame" }));
		table.put("PixelFormat", new TypeItem("PixelFormat", "PixelFormat",
				Strategy.Enumeration, new String[] { "aafPixelFormat" }));
		table.put("PluginAPI", new TypeItem("PluginAPI", "AUID",
				Strategy.Annotation, new String[] { "aafPluginAPI" }));
		table.put("PositionType", 
				new TypeItem("PositionType", "long", Strategy.Annotation, 
						new String[] { "aafPosition" }));
		table.put("ProductIdentification", new TypeItem(
				"ProductIdentification", "ProductIdentification",
				Strategy.Class, new String[] { "aafProductIdentification" }));
		table.put("ProductReleaseType", new TypeItem("ProductReleaseType",
				"ProductReleaseType", Strategy.Enumeration,
				new String[] { "aafProductReleaseType" }));
		table.put("ProductVersion", new TypeItem("ProductVersion",
				"ProductVersion", Strategy.Class,
				new String[] { "aafProductVersion" }));
		table.put("PropertyOpt", new TypeItem("PropertyOpt", "PropertyOpt",
				Strategy.Enumeration, new String[] { "aafPropertyOpt" }));
		table.put("PulldownDirectionType", new TypeItem(
				"PulldownDirectionType", "PulldownDirectionType",
				Strategy.Enumeration, new String[] { "aafPulldownDir" }));
		table.put("PulldownKindType", new TypeItem("PulldownKindType",
				"PulldownKindType", Strategy.Enumeration,
				new String[] { "aafPulldownKind" }));
		table.put("Rational", new TypeItem("Rational", "Rational",
				Strategy.Class, new String[] { "aafRational" }));
		table.put("Rectangle", 
				new TypeItem("Rectangle", "Rectangle", Strategy.Class, 
						new String[] { "aafRect" }));
		table.put("ReferenceType", new TypeItem("ReferenceType",
				"ReferenceType", Strategy.Enumeration,
				new String[] { "aafReferenceType" }));
		table.put("RGBAComponent", 
				new TypeItem("RGBAComponent", "RGBAComponent", Strategy.Class,
						new String[] { "aafRGBAComponent" } ));
		table.put("RGBALayout", new TypeItem("RGBALayout",
				"RGBAComponent[]", Strategy.Annotation,
				new String[] { "aafRGBAComponent *" }));
		table.put("RGBAComponentKind", new TypeItem("RGBAComponentKind",
				"RGBAComponentKind", Strategy.Enumeration,
				new String[] { "aafRGBAComponentKind" }));
		table.put("ScanningDirectionType", new TypeItem(
				"ScanningDirectionType", "ScanningDirectionType",
				Strategy.Enumeration, new String[] { "aafScanningDirection" }));
		table.put("SearchDictionaryTag", new TypeItem("SearchDictionaryTag",
				"SearchDictionaryTag", Strategy.Enumeration,
				new String[] { "aafSearchDictionaryTag" }));
		table.put("SearchTag", new TypeItem("SearchTag", "SearchTag",
				Strategy.Enumeration, new String[] { "aafSearchTag" }));
		table.put("SignalStandardType", new TypeItem("SignalStandardType",
				"SignalStandardType", Strategy.Enumeration,
				new String[] { "aafSignalStandard" }));
		table.put("SlotID", new TypeItem("SlotID", "int", Strategy.Annotation,
				new String[] { "aafSlotID" }));
		table.put("SourceReference", new TypeItem("SourceReference",
				"SourceReferenceValue", Strategy.Class,
				new String[] { "aafSourceRef" }));
		table.put("MobIDType", new TypeItem("MobIDType", "MobID",
				Strategy.Class, new String[] { "aafMobID" }));
		table.put("String", new TypeItem("String", "String",
				Strategy.Annotation, new String[] { "aafString" }));
		table.put("SwapNeeded", new TypeItem("SwapNeeded", "SwapNeeded",
				Strategy.Enumeration, new String[] { "aafSwapNeeded" }));
		table.put("TapeCaseType", new TypeItem("TapeCaseType", "TapeCaseType",
				Strategy.Enumeration, new String[] { "aafTapeCaseType" }));
		table.put("TapeFormatType", new TypeItem("TapeFormatType",
				"TapeFormatType", Strategy.Enumeration,
				new String[] { "aafTapeFormatType" }));
		table.put("TCSource", new TypeItem("TCSource", "TCSource",
				Strategy.Enumeration,
				new String[] { "aafTCSource", "aafTimecodeSource" }));
		table.put("Timecode", new TypeItem("Timecode", "TimecodeValue",
				Strategy.Class, new String[] { "aafTimecode" }));
		table.put("TimeStamp", new TypeItem("TimeStamp", "TimeStamp",
				Strategy.Class, new String[] { "aafTimeStamp" }));
		table.put("TimeStruct", new TypeItem("TimeStruct", "TimeStruct",
				Strategy.Class, new String[] { "aafTimeStruct" }));
		table.put("TypeCategory", new TypeItem("TypeCategory", "TypeCategory",
				Strategy.Enumeration, new String[] { "aafTypeCategory" }));
		table.put("AUID", new TypeItem("AUID", "AUID", Strategy.Class,
				new String[] { "aafUID" }));
		table.put("AUIDArray", new TypeItem("AUIDArray", "AUID[]",
				Strategy.Annotation, new String[] { "aafUID_constptr" }));
		table.put("UInt16", new TypeItem("UInt16", "short",
				Strategy.Annotation, new String[] { "aafUInt16" }));
		table.put("UInt16Array", new TypeItem("UInt16Array", "short[]",
				Strategy.Annotation, new String[] { "aafUInt16 *" }));
		table.put("UInt32", new TypeItem("UInt32", "int", Strategy.Annotation,
				new String[] { "aafUInt32" }));
		table.put("UInt32Array", new TypeItem("UInt32Array", "int[]", Strategy.Annotation,
				new String[] { "aafUInt32 *" }));
		table.put("UInt32Set", 
				new TypeItem("UInt32Set", "java.util.Set<Integer>", Strategy.Annotation,
						new String[] { } ));
		table.put("UInt64", new TypeItem("UInt64", "long", Strategy.Annotation,
				new String[] { "aafUInt64" }));
		table.put("UInt64Array", new TypeItem("UInt64Array", "long[]", Strategy.Annotation,
				new String[] { "aafUInt64 *" }));
		table.put("UInt8", new TypeItem("UInt8", "byte[]", Strategy.Annotation,
				new String[] { "aafUInt8" }));
		table.put("UInt8Array", new TypeItem("UInt8Array", "byte", Strategy.Annotation,
				new String[] { "aafUInt8 *" }));
		table.put("VersionType", new TypeItem("VersionType", "VersionType",
				Strategy.Class, new String[] { "aafVersionType" }));
		table.put("VideoLineMap", new TypeItem("VideoLineMap", "int[]",
				Strategy.Annotation, // int array size 2
				new String[] { "aafVideoLineMap" }));
		table.put("VideoSignalType", new TypeItem("VideoSignalType",
				"VideoSignalType", Strategy.Enumeration,
				new String[] { "aafVideoSignalType" }));
		table.put("MultiResultItem", new TypeItem("MultiResultItem",
				"MultiResultItem", Strategy.Class,
				new String[] { "aafmMultiResult" }));
		table.put("MultiXferItem", new TypeItem("MultiXferItem",
				"MultiXferItem", Strategy.Class,
				new String[] { "aafmMultiXfer" }));

		// References
		table.put("StrongReference", 
				new TypeItem("StrongReference", "InterchangeObject", Strategy.Annotation,
						new String[] { } ));
		table.put("StrongReferenceVector", 
				new TypeItem("StrongReferenceVector", "java.util.List<InterchangeObject>", Strategy.Annotation,
						new String[] { } ));
		table.put("StrongReferenceSet", 
				new TypeItem("StrongReferenceSet", "java.util.Set<InterchangeObject>", Strategy.Annotation,
						new String[] { } ));
		table.put("WeakReference", 
				new TypeItem("WeakReference", "InterchangeObject", Strategy.Annotation,
						new String[] { } ));
		table.put("WeakReferenceVector", 
				new TypeItem("WeakReferenceVector", "java.util.List<InterchangeObject>", Strategy.Annotation,
						new String[] { } ));
		table.put("WeakReferenceSet", 
				new TypeItem("WeakReferenceSet", "java.util.Set<InterchangeObject>", Strategy.Annotation,
						new String[] { } ));
		
		// Extensible enumerations
		table.put("CodingEquationsType",
				new TypeItem("CodingEquationsType", "AUID", Strategy.Annotation,
						new String[] { } ));
		table.put("ColorPrimariesType", 
				new TypeItem("ColorPrimariesType", "AUID", Strategy.Annotation,
						new String[] { } ));
		table.put("TransferCharacteristicType", 
				new TypeItem("TransferCharacteristicType", "AUID", Strategy.Annotation,
						new String[] { } ));
		table.put("UsageType", 
				new TypeItem("UsageType", "AUID", Strategy.Annotation,
						new String[] { } ));
		
		makeReverseTable();
	}

	/**
	 * <p>
	 * Make the reverse lookup table for finding out type mapping details from
	 * AAF SDK data type names.
	 * </p>
	 */
	private final static void makeReverseTable() {

		for (String key : table.keySet()) {

			TypeItem item = table.get(key);
			String[] cNames = item.getCNames();
			for (String cName : cNames) {
				reverseTable.put(cName, item);
			}
		}
	}

	/**
	 * <p>
	 * Find the strategy used by the MAJ API for the representation of an AAF
	 * type from a given AAF type name. This method will try both the given name
	 * and the name with "<code>Type</code>" appended to the end.
	 * </p>
	 * 
	 * @param aafName
	 *            Name of AAF data type to check the representation strategy
	 *            for.
	 * @return Strategy used to represent the AAF type, or <code>null</code>
	 *         if the type name is not known in this lookup table.
	 * 
	 * @throws NullPointerException
	 *             The AAF data type name is <code>null</code>.
	 */
	public final static Strategy lookupMAJTypeStrategy(String aafName)
			throws NullPointerException {

		if (aafName == null)
			throw new NullPointerException(
					"Cannot lookup a strategy kind using a null value.");

		TypeItem item = table.get(aafName);

		if (item == null)
			item = table.get(aafName + "Type");

		if (item == null)
			return null;

		return item.getKind();
	}

	/**
	 * <p>
	 * Find the name of the underlying Java type used to represent the given AAF
	 * data type in the MAJ API. his method will try both the given name and the
	 * name with "<code>Type</code>" appended to the end.
	 * </p>
	 * 
	 * @param aafName
	 *            Name of AAF data type to find the underlying Java type of its
	 *            representation for.
	 * @return Name of the Java type, a primitive type or interface name, used
	 *         to represent the given AAF data type, or <code>null</code> if
	 *         the data type could not be found.
	 * 
	 * @throws NullPointerException
	 *             The AAF data type name is <code>null</code>.
	 */
	public final static String lookupMAJType(String aafName)
			throws NullPointerException {

		if (aafName == null)
			throw new NullPointerException(
					"Cannot lookup a MAJ API Java type representation using a null value.");

		TypeItem item = table.get(aafName);

		if (item == null)
			item = table.get(aafName + "Type");

		if (item == null)
			return null;

		return item.getJavaBaseType();
	}

	/**
	 * <p>
	 * Find the name of the AAF data type represented by a C type definition in
	 * the AAF SDK. Type definitions are taken from the file "<code>AAFTypes.h</code>".
	 * Any of the following extensions will be removed from the name before the
	 * lookup operation takes place:
	 * </p>
	 * 
	 * <ul>
	 * <li><code>_t</code></li>
	 * <li><code>_e</code></li>
	 * <li><code>_constref</code></li>
	 * </ul>
	 * 
	 * <p>
	 * Also, the method tries both the given name and the name with "<code>aaf</code>"
	 * prepended to its start.
	 * </p>
	 * 
	 * @param cTypeName
	 *            Name of the C type definition to search for.
	 * @return Name of the AAF data type represented by the given C type name,
	 *         or <code>null</code> if the given name cannot be found.
	 * 
	 * @throws NullPointerException
	 *             The given C type definition name is <code>null</code>.
	 */
	public final static String lookupAAFTypeForCType(String cTypeName)
			throws NullPointerException {

		if (cTypeName == null)
			throw new NullPointerException(
					"Cannot lookup an AAF data type name using a null value.");

		if (cTypeName.endsWith("_e") || cTypeName.endsWith("_t"))
			cTypeName = cTypeName.substring(0, cTypeName.length() - 2);

		if (cTypeName.endsWith("_constref"))
			cTypeName = cTypeName.substring(0, cTypeName.length() - 9);

		TypeItem item = reverseTable.get(cTypeName);

		if (item == null)
			item = reverseTable.get("aaf" + cTypeName);

		if (item == null)
			return null;

		return item.getAAFName();
	}

	/**
	 * <p>
	 * Lookup the underlying Java type representation in the MAJ API for a given
	 * C type definition name in the AAF SDK. Type definitions are taken from
	 * the file "<code>AAFTypes.h</code>". Any of the following extensions
	 * will be removed from the name before the lookup operation takes place:
	 * </p>
	 * 
	 * <ul>
	 * <li><code>_t</code></li>
	 * <li><code>_e</code></li>
	 * <li><code>_constref</code></li>
	 * </ul>
	 * 
	 * <p>
	 * Also, the method tries both the given name and the name with "<code>aaf</code>"
	 * prepended to its start.
	 * </p>
	 * 
	 * <p>
	 * Calling this method is the same as calling
	 * {@link #lookupAAFTypeForCType(String)} followed by
	 * {@link #lookupMAJType(String)}.
	 * </p>
	 * 
	 * @param cTypeName
	 *            Name of the C type definition to search for.
	 * @return Name of the Java type representing by the given C type name in
	 *         the MAJ API, or <code>null</code> if the given name cannot be
	 *         found.
	 * 
	 * @throws NullPointerException
	 *             The given C type definition name is <code>null</code> or
	 *             could not be found in this lookup table.
	 */
	public final static String lookupMAJTypeForCType(String cTypeName)
			throws NullPointerException {

		return lookupMAJType(lookupAAFTypeForCType(cTypeName));
	}

	/**
	 * <p>
	 * This class should never be instantiated as it is intended for static use
	 * only.
	 * </p>
	 */
	private LookupTable() {
	}
}
