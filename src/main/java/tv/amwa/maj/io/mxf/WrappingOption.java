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
// * $Log: WrappingOption.java,v $
// * Revision 1.5  2010/01/19 14:44:22  vizigoth
// * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
// *
// * Revision 1.4  2009/03/30 09:04:59  vizigoth
// * Refactor to use SMPTE harmonized names and add early KLV file support.
// *
// * Revision 1.3  2009/02/13 14:27:29  vizigoth
// * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
// *
// * Revision 1.2  2009/02/06 17:01:31  vizigoth
// * Conversion of C headers to fields and stubs.
// *
// * Revision 1.1  2009/02/03 16:15:19  vizigoth
// * Initial creation and copy over of header information from mxflib.
// *
// *
// */
//
//package tv.amwa.maj.io.mxf;
//
//import java.util.List;
//
//import tv.amwa.maj.integer.UInt32;
//import tv.amwa.maj.integer.UInt8;
//
///**
// * <p>Description of how essence is wrapped into an MXF file.</p>
// *
// *
// *
// */
//public class WrappingOption {
//
////	public:
////
////		//! Wrapping type
////
////		/*! \note "None" is only for use as a default condition */
////
////		enum WrapType { None, Frame, Clip, Line, Other } ;
////
//	public enum WrapType {
//		None,
//		Frame,
//		Clip,
//		Line,
//		Other;
//	}
////
////		EssenceSubParserParent Handler;			//!< Pointer to the object that can parse this wrapping option - parent pointer because the parser holds a copy of this!
//
//	private EssenceSubParser handler;
//
////		std::string Name;						//!< A short name, unique for this sub-parser, for this wrapping option (or "" if not supported by this handler)
//
//	private String name;
//
////		std::string Description;				//!< Human readable description of this wrapping option (to allow user selection)
//
//	private String description;
//
////		ULPtr	WrappingID;						//!< A UL (or endian swapped UUID) that uniquely identifies this sub-parser/wrapping option combination (or NULL if not suppoered by this handler)
////
////						/*!< This allows an application to specify a desired wrapping, or list of wrappings, for automated selection */
//	private	UL wrappingID;
//
////
////		ULPtr	WrappingUL;						//!< UL for this wrapping
////
//	private UL wrappingUL;
//
////		ULList	RequiredPartners;				//!< List of other items that *MUST* accompany this item to use this wrapping
//	private List<UL> requiredPartners;
//
////		UInt8	GCEssenceType;					//!< The Generic Container essence type, or 0 if not a GC wrapping
//
//	private @UInt8 byte genericContainerEssenceType;
//
////		UInt8	GCElementType;					//!< The Generic Container element value, or 0 if not a GC wrapping
//
//	private @UInt8 byte genericContainerElementType;
//
////		WrapType ThisWrapType;					//!< The type of this wrapping (frame, clip etc.)
//
//	private WrapType wrapType;
//
////		bool	CanSlave;						//!< True if this wrapping can be a "slave" which allows it to be used at a different edit rate than its own
//
//	private boolean canSlave;
//
////		bool	CanIndex;						//!< True if this wrapping can be VBR indexed by the handler (CBR essence may need VBR indexing when interleaved)
//
//	private boolean canIndex;
//
////		bool	CBRIndex;						//!< True if this wrapping will use a CBR index table (and therefore has a non-zero return value from GetBytesPerEditUnit() )
//
//	private boolean cbrIndex;
//
////		UInt8	BERSize;						//!< The BER length size to use for this wrapping (or 0 for any)
//
//	private @UInt8 byte berSize;
//
////		UInt32 BytesPerEditUnit;				//!< set non zero for ConstSamples
//
//	private @UInt32 int bytesPerEditUnit;
//
//	public EssenceSubParser getHandler() {
//		return handler;
//	}
//
//	public void setHandler(
//			EssenceSubParser handler) {
//		this.handler = handler;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(
//			String name) {
//		this.name = name;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(
//			String description) {
//		this.description = description;
//	}
//
//	public UL getWrappingID() {
//
//		return wrappingID;
//	}
//
//	public void setWrappingID(
//			UL wrappingID) {
//		this.wrappingID = wrappingID;
//	}
//
//	public UL getWrappingUL() {
//
//		return wrappingUL;
//	}
//
//	public void setWrappingUL(
//			UL wrappingUL) {
//
//		this.wrappingUL = wrappingUL;
//	}
//
//	public List<UL> getRequiredPartners() {
//
//		return requiredPartners;
//	}
//
//	public void setRequiredPartners(
//			List<UL> requiredPartners) {
//		this.requiredPartners = requiredPartners;
//	}
//
//	public @UInt8 byte getGenericContainerEssenceType() {
//		return genericContainerEssenceType;
//	}
//
//	public void setGenericContainerEssenceType(
//			@UInt8 byte genericContainerEssenceType) {
//
//		this.genericContainerEssenceType = genericContainerEssenceType;
//	}
//
//	public @UInt8 byte getGenericContainerElementType() {
//		return genericContainerElementType;
//	}
//
//	public void setGenericContainerElementType(
//			@UInt8 byte genericContainerElementType) {
//
//		this.genericContainerElementType = genericContainerElementType;
//	}
//
//	public WrapType getWrapType() {
//
//		return wrapType;
//	}
//
//	public void setWrapType(
//			WrapType wrapType) {
//
//		this.wrapType = wrapType;
//	}
//
//	public boolean isCanSlave() {
//
//		return canSlave;
//	}
//
//	public void setCanSlave(
//			boolean canSlave) {
//
//		this.canSlave = canSlave;
//	}
//
//	public boolean isCanIndex() {
//
//		return canIndex;
//	}
//
//	public void setCanIndex(
//			boolean canIndex) {
//
//		this.canIndex = canIndex;
//	}
//
//	public boolean isCbrIndex() {
//
//		return cbrIndex;
//	}
//
//	public void setCbrIndex(
//			boolean cbrIndex) {
//
//		this.cbrIndex = cbrIndex;
//	}
//
//	public @UInt8 byte getBerSize() {
//
//		return berSize;
//	}
//
//	public void setBerSize(
//			@UInt8 byte berSize) {
//
//		this.berSize = berSize;
//	}
//
//	public @UInt32 int getBytesPerEditUnit() {
//
//		return bytesPerEditUnit;
//	}
//
//	public void setBytesPerEditUnit(
//			@UInt32 int bytesPerEditUnit) {
//
//		this.bytesPerEditUnit = bytesPerEditUnit;
//	}
//
//}
