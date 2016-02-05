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
//
///*
// * $Log: EssenceParser.java,v $
// * Revision 1.6  2010/01/19 14:44:23  vizigoth
// * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
// *
// * Revision 1.5  2009/05/14 16:15:24  vizigoth
// * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
// *
// * Revision 1.4  2009/03/30 09:05:00  vizigoth
// * Refactor to use SMPTE harmonized names and add early KLV file support.
// *
// * Revision 1.3  2009/02/13 14:27:29  vizigoth
// * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
// *
// * Revision 1.2  2009/02/10 09:00:14  vizigoth
// * Finished turning C headers to Java method headers.
// *
// * Revision 1.1  2009/02/03 16:15:19  vizigoth
// * Intiial creation and copy over of header information from mxflib.
// *
// *
// */
//
//package tv.amwa.maj.io.mxf;
//
//import java.io.File;
//import java.util.List;
//import java.util.Map;
//import java.util.Vector;
//
//import tv.amwa.maj.embeddable.RationalImpl;
//import tv.amwa.maj.entity.EssenceDescriptorImpl;
//import tv.amwa.maj.integer.UInt32;
//
//
///**
// * <p>Manages the parsing of essence through a collection of {@linkplain EssenceSubParser essence
// * sub parsers}.</p>
// *
// *
// *
// * @see EssenceSubParser
// *
// */
//public class EssenceParser {
//
//
//	//! Configuration data for an essence parser with a specific wrapping option
//
//	/*! \note No parser may contain one of these that includes a pointer to that parser otherwise it will never be deleted (circular reference)
//
//	 */
//
//	// TODO Probably need to make safely public - see FileParser listWrappingOptions()
//	class WrappingConfig {
//
////		public:
////
////			EssenceSubParserPtr Parser;					//!< The parser that parses this essence - true smart pointer not a parent pointer to keep parser alive
//
////		public EssenceSubParser parser;
//
////			WrappingOptionPtr WrapOpt;					//!< The wrapping options
//
//		public WrappingOption wrappingOption;
//
////			MDObjectPtr EssenceDescriptor;				//!< The essence descriptior for the essence as parsed
//
//		public EssenceDescriptorImpl essenceDescriptor;
//
////			UInt32 Stream;								//!< The stream ID of this stream from the parser
//
//		public @UInt32 int stream;
//
////			Rational EditRate;							//!< The selected edit rate for this wrapping
//
//		public RationalImpl editRate;
//
////			WrappingConfigList SubStreams;				//!< A list of wrapping options available for sub-streams extracted from the same essence source. See \ref SubStreamNotes
//
//		public List<WrappingConfig> subStreams;
//
//	}
//
////	public:
////
////		//! A list of parser factory functions
////
////		typedef std::list<EssenceSubParserFactoryPtr> EssenceSubParserFactoryList;
////
////
////	protected:
////
////		//! List of pointers to known parsers
////
////		/*! Used only for building parsers to parse essence - the parses
////
////		 *  in this list must not themselves be used for essence parsing
////
////		 */
////
////		static EssenceSubParserFactoryList EPList;
////
//	private static List<EssenceSubParserFactory> essenceParserFactories
//		= new Vector<EssenceSubParserFactory>();
////
////		//! Initialization flag for EPList
////
////		static bool Inited;
////
//	private boolean listInitialised = false;
////
////	private:
////
////		//! Prevent instantiation of essence parser - all methods are now static
////
////		EssenceParser();
////
//	private EssenceParser() { }
////
////	public:
////
////		//! Add a new EssenceSubParser type
////
////		/*! This adds a factory to build instances of a new sub parser type if required
////
////		 *  to parse an essence stream
////
////		 */
////
////		static void AddNewSubParserType(EssenceSubParserFactoryPtr Factory)
////
////		{
////
////			EPList.push_back(Factory);
////
////		}
////
//	public static void addNewSubParserType(
//			EssenceSubParserFactory factory) {
//
//		essenceParserFactories.add(factory);
//	}
////
////		//! Add a new EssenceSubParser type
////
////		/*! This adds a factory to build instances of a new sub parser type if required
////
////		 *  to parse an essence stream.
////
////		 *  \note This is the lecacy version to cope with EssenceSubParsers which are thier own factories
////
////		 */
////
////		static void AddNewSubParserType(EssenceSubParserPtr SubParser)
////
////		{
////
////			EssenceSubParserFactoryPtr Factory = new EssenceSubParserSelfFactory(SubParser);
////
////
////
////			EPList.push_back(Factory);
////
////		}
////
//	public static void addNewSubParserType(
//			EssenceSubParser subParser) {
//
//		if (!(subParser instanceof EssenceSubParserFactory))
//			throw new IllegalArgumentException("The given essence sub-parser must be a self-factory by implementing EssenceSubParserFactory.");
//
//		essenceParserFactories.add((EssenceSubParserFactory) subParser);
//	}
//
//	/*
//	 * A ParserDescriptor is defined as a pair of an essence sub parser and the list of essence descriptors that it
//	 * is capable of parsing. A parser descriptor list is a list of these pairs. In Java, I've chosen to
//	 * represent this as Map<EssenceDescriptor, List<EssenceStreamDescriptor>>.
//	 *
//	 */
//
////		//! Build a list of parsers with their descriptors for a given essence file
////
////		static ParserDescriptorListPtr IdentifyEssence(FileHandle InFile);
////
//	public static Map<EssenceSubParser, List<EssenceStreamDescriptor>> identifyEssence(
//			File file) {
//
//		// TODO
//		return null;
//	}
//
////	//! Produce a list of available wrapping options
////
////	static WrappingConfigList ListWrappingOptions(bool AllowMultiples, FileHandle InFile, ParserDescriptorListPtr PDList, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None);
////
//	public List<WrappingConfig> listWrappingOptions(
//			boolean allowMultiples,
//			File file,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate,
//			WrappingOption.WrapType forceWrap) {
//
//		// TODO
//		return null;
//	}
//
//	public List<WrappingConfig> listWrappingOptions(
//			boolean allowMultiples,
//			File file,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate) {
//
//
//		return listWrappingOptions(allowMultiples, file, parserDescriptorList,
//				forceEditRate, WrappingOption.WrapType.None);
//	}
//
////
////	//! Produce a list of available wrapping options
////
////	static WrappingConfigList ListWrappingOptions(FileHandle InFile, ParserDescriptorListPtr PDList, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////	{
////
////		return ListWrappingOptions(false, InFile, PDList, ForceEditRate, ForceWrap);
////
////	}
////
//	public List<WrappingConfig> listWrappingOptions(
//			File file,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate,
//			WrappingOption.WrapType forceWrap) {
//
//		return listWrappingOptions(false, file, parserDescriptorList, forceEditRate, forceWrap);
//	}
//
//	public List<WrappingConfig> listWrappingOptions(
//			File file,
//			Map<EssenceSubParser, List<EssenceStreamDescriptor>> parserDescriptorList,
//			RationalImpl forceEditRate) {
//
//		return listWrappingOptions(false, file, parserDescriptorList, forceEditRate,
//				WrappingOption.WrapType.None);
//	}
//
//
////
////	//! Produce a list of available wrapping options
////
////	static WrappingConfigList ListWrappingOptions(bool AllowMultiples, FileHandle InFile, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None);
////
//	public List<WrappingConfig> listWrappingOptions(
//			boolean allowMultiples,
//			File file,
//			RationalImpl forceEditRate,
//			WrappingOption.WrapType forceWrap) {
//
//		// TODO
//		return null;
//	}
//
//	public List<WrappingConfig> listWrappingOptions(
//			boolean allowMultiples,
//			File file,
//			RationalImpl forceEditRate) {
//
//		return listWrappingOptions(allowMultiples, file, forceEditRate, WrappingOption.WrapType.None);
//	}
//
//
////
////	//! Produce a list of available wrapping options
////
////	static WrappingConfigList ListWrappingOptions(FileHandle InFile, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////	{
////
////		return ListWrappingOptions(false, InFile, ForceEditRate, ForceWrap);
////
////	}
////
//	public List<WrappingConfig> listWrappingOptions(
//			File file,
//			RationalImpl forceEditRate,
//			WrappingOption.WrapType forceWrap) {
//
//		return listWrappingOptions(false, file, forceEditRate, forceWrap);
//	}
//
//	public List<WrappingConfig> listWrappingOptions(
//			File file,
//			RationalImpl forceEditRate) {
//
//		return listWrappingOptions(false, file, forceEditRate, WrappingOption.WrapType.None);
//	}
//
//
////
////	//! Produce a list of available wrapping options
////
////	static WrappingConfigList ListWrappingOptions(bool AllowMultiples, FileHandle InFile, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return ListWrappingOptions(AllowMultiples, InFile, ForceEditRate, ForceWrap);
////
////	}
////
//// TODO really need all of these?
////
////	//! Produce a list of available wrapping options
////
////	static WrappingConfigList ListWrappingOptions(FileHandle InFile, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return ListWrappingOptions(false, InFile, ForceEditRate, ForceWrap);
////
////	}
////
//// TODO really need all of these?
////
////	//! Select the best wrapping option
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, ParserDescriptorListPtr PDList, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////	{
////
////		return SelectWrappingOption(false, InFile, PDList, ForceEditRate, ForceWrap);
////
////	}
////
////
////
////	//! Select the best wrapping option
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, ParserDescriptorListPtr PDList, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return SelectWrappingOption(false, InFile, PDList, ForceEditRate, ForceWrap);
////
////	}
////
////
////
////	//! Select the best wrapping option
////
////	static WrappingConfigPtr SelectWrappingOption(bool AllowMultiples, FileHandle InFile, ParserDescriptorListPtr PDList, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None);
////
////
////
////	//! Select the best wrapping option
////
////	static WrappingConfigPtr SelectWrappingOption(bool AllowMultiples, FileHandle InFile, ParserDescriptorListPtr PDList, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return SelectWrappingOption(AllowMultiples, InFile, PDList, ForceEditRate, ForceWrap);
////
////	}
////
////
////
////	//! Select the best wrapping option
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////	{
////
////		return SelectWrappingOption(false, InFile, ForceEditRate, ForceWrap);
////
////	}
////
////
////
////	//! Select the best wrapping option
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return SelectWrappingOption(false, InFile, ForceEditRate, ForceWrap);
////
////	}
////
////
////
////	//! Select the best wrapping option
////
////	static WrappingConfigPtr SelectWrappingOption(bool AllowMultiples, FileHandle InFile, Rational ForceEditRate, WrappingOption::WrapType ForceWrap = WrappingOption::None);
////
//	public WrappingConfig selectWrappingOption(
//			boolean allowMultiples,
//			File file,
//			RationalImpl forceEditRate,
//			WrappingOption.WrapType forceWrap) {
//
//		// TODO
//		return null;
//	}
//
//	public WrappingConfig selectWrappingOption(
//			boolean allowMultiples,
//			File file,
//			RationalImpl forceEditRate) {
//
//		return selectWrappingOption(allowMultiples, file,
//				forceEditRate, WrappingOption.WrapType.None);
//	}
//
//	//
////	//! Select the best wrapping option
////
////	static WrappingConfigPtr SelectWrappingOption(bool AllowMultiples, FileHandle InFile, WrappingOption::WrapType ForceWrap = WrappingOption::None)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return SelectWrappingOption(AllowMultiples, InFile, ForceEditRate, ForceWrap);
////
////	}
////
////
////
////	//! Select the specified wrapping options
////
////	static void SelectWrappingOption(EssenceParser::WrappingConfigPtr Config);
////
//	public static void selectWrappingOption(
//			EssenceParser.WrappingConfig config) {
//
//		// TODO
//	}
////
////	//! Auto select a  wrapping option (using the default edit rate)
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return SelectWrappingOption(InFile, ForceEditRate);
////
////	}
////
////
////	//! Select a named wrapping option (with a specified edit rate)
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, std::string WrappingName, Rational ForceEditRate);
////
//	public static WrappingConfig selectWrappingOption(
//			File file,
//			String wrappingName,
//			RationalImpl forceEditRate) {
//
//		// TODO
//		return null;
//	}
////
////	//! Select a named wrapping option (using the default edit rate)
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, std::string WrappingName)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return SelectWrappingOption(InFile, WrappingName, ForceEditRate);
////
////	}
////
////
////	//! Select from a list of named wrapping options (with a specified edit rate)
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, std::list<std::string> WrappingNameList, Rational ForceEditRate);
////
//	public static WrappingConfig selectWrappingOption(
//			File file,
//			List<String> wrappingNameList,
//			RationalImpl forceEditRate) {
//
//		// TODO
//		return null;
//	}
////
////	//! Select a named wrapping option (using the default edit rate)
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, std::list<std::string> WrappingNameList)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return SelectWrappingOption(InFile, WrappingNameList, ForceEditRate);
////
////	}
////
////
////
////	//! Select a UL identified wrapping option (with a specified edit rate)
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, ULPtr &WrappingID, Rational ForceEditRate);
////
//	public WrappingConfig selectWrappingOption(
//			File file,
//			UL wrappingID,
//			RationalImpl forceEditRate) {
//
//		// TODO
//		return null;
//	}
////
////	//! Select a UL identified wrapping option (using the default edit rate)
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, ULPtr &WrappingID)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return SelectWrappingOption(InFile, WrappingID, ForceEditRate);
////
////	}
////
////
////
////	//! Select a UL identified wrapping option (with a specified edit rate)
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, ULList &WrappingIDList, Rational ForceEditRate);
////
//
//	/*
//	 * TODO work out how to avoid method signature clash - Lis<UL> vs List<String>
//	 * public static WrappingConfig selectWrappingOption(
//			File file,
//			List<UL> wrappingIDList,
//			Rational forceEditRate) {
//
//		// TODO
//		return null;
//		} */
////
////	//! Select a UL identified wrapping option (using the default edit rate)
////
////	static WrappingConfigPtr SelectWrappingOption(FileHandle InFile, ULList &WrappingIDList)
////
////	{
////
////		Rational ForceEditRate(0,0);
////
////		return SelectWrappingOption(InFile, WrappingIDList, ForceEditRate);
////
////	}
////
////
////
////protected:
////
////	//! Take a list of wrapping options and validate them agains a specified edit rate and wrapping type
////
////	/*! All valid options are built into a WrappingConfig object and added to a specified WrappingConfigList,
////
////	*  which may already contain other items.
////
////	*/
////
////	static void ExtractValidWrappingOptions(WrappingConfigList &Ret, FileHandle InFile, EssenceStreamDescriptorPtr &ESDescriptor, WrappingOptionList &WO, Rational &ForceEditRate, WrappingOption::WrapType ForceWrap);
////
//	static void extractValidWrappingOptions(
//			List<WrappingConfig> ret,
//			File file,
//			EssenceStreamDescriptor essenceStreamDescriptor,
//			List<WrappingOption> wrappingOptions,
//			RationalImpl forceEditRate,
//			WrappingOption.WrapType forceWrap) {
//
//		// TODO
//	}
//
//	static void extractValidWrappingOptions(
//			List<WrappingConfig> ret,
//			File file,
//			EssenceStreamDescriptor essenceStreamDescriptor,
//			List<WrappingOption> wrappingOptions,
//			RationalImpl forceEditRate) {
//
//		extractValidWrappingOptions(ret, file, essenceStreamDescriptor,
//				wrappingOptions, forceEditRate, WrappingOption.WrapType.None);
//	}
//
////
////
////
////protected:
////
////	//! Initialise the sub-parser list
////
////	static void Init(void);
////
//
//	static {
//
//		// TODO initialize the sub-parser list
//	}
//
//}
