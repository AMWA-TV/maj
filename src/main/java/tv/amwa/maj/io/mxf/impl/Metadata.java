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

package tv.amwa.maj.io.mxf.impl;

import java.util.List;

import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.mxf.UUID;
import tv.amwa.maj.model.Package;


/**
 * <p>Data relating to a single {@linkplain PartitionImpl partition}.</p>
 *
 *
 *
 */
public class Metadata {

//	public:
//
//		PackageList Packages;						//!< Each package in this metadata
//
	private List<Package> packages;
//
//	protected:
//
//		std::string ModificationTime;				//!< Creation or modification time for this metadata, used for package times
//
	private String modificationTime;
//
//		//! Protected constructor used to create from an existing MDObject
//
//		Metadata(MDObjectPtr BaseObject) : ObjectInterface(BaseObject) {}
//
	Metadata(
			MetadataObject baseObject) {

		// TODO
	}
//
//	public:
//
//	Metadata();
	//
	public Metadata() {

		// TODO
	}

//		Metadata(std::string TimeStamp);

	public Metadata(
			String timeStamp) {

		// TODO
	}

//		void Init(void);
//
// TODO do we need this?
//
//		// Update the modification time for this file and any new packages
//
//		void SetTime(void) { ModificationTime = Now2String(); }

	public void setModificationTime() {

		// TODO
	}

//		void SetTime(std::string TimeStamp) { ModificationTime = TimeStamp; }
//
	public void setModificationTime(
			String timestamp) {

		// TODO
	}
//
//		//! Add a DMScheme to the listed schemes
//
//		void AddDMScheme(ULPtr Scheme)
//
//		{
//
//			DataChunk SchemeValue;
//
//			SchemeValue.Set(16, Scheme->GetValue());
//
//
//
//			// Get a list of current schemes
//
//			MDObjectPtr SchemeList = Object->Child(DMSchemes_UL);
//
//
//
//			// Scan the list to see if we already have this one
//
//			MDObjectULList::iterator it = SchemeList->begin();
//
//			while(it != SchemeList->end())
//
//			{
//
//				if(SchemeValue == *((*it).second->PutData())) return;
//
//				it++;
//
//			}
//
//
//
//			// New scheme, so add it
//
//			Object->Child(DMSchemes_UL)->AddChild()->SetValue(SchemeValue);
//
//		}
//
	public void addDMScheme(
			UL dmScheme) {

		// TODO
	}
//
//		//! Add an essence type UL to the listed essence types
//
//		/*! Only added if it does not already appear in the list */
//
//		void AddEssenceType(ULPtr ECType) { AddEssenceType(*ECType); };
//
	public void addEssenceType(
			UL essenceContainerType) {

		// TODO see next C comment
	}
//
//		//! Add an essence type UL to the listed essence types
//
//		/*! Only added if it does not already appear in the list */
//
//		void AddEssenceType(const UL &ECType)
//
//		{
//
//			DataChunk ECTypeValue;
//
//			ECTypeValue.Set(16, ECType.GetValue());
//
//
//
//			// Get a list of known containers
//
//			MDObjectPtr ECTypeList = Object->Child(EssenceContainers_UL);
//
//
//
//			// Scan the list to see if we already have this type
//
//			MDObjectULList::iterator it = ECTypeList->begin();
//
//			while(it != ECTypeList->end())
//
//			{
//
//				if(ECTypeValue == *((*it).second->PutData())) return;
//
//				it++;
//
//			}
//
//
//
//			// New type, so add it
//
//			Object->Child(EssenceContainers_UL)->AddChild()->SetValue(ECTypeValue);
//
//		}
//
//
//
//		//! Set the operational pattern property of the preface
//
//		void SetOP(const UL &OP)
//
//		{
//
//			MDObjectPtr Ptr = Object->AddChild(OperationalPattern_UL);
//
//			Ptr->ReadValue(OP.GetValue(), 16);
//
//		}
//
	public void setOperationalPattern(
			UL operationalPattern) {

		// TODO
	}
//
//		//! Set the operational pattern property of the preface
//
//		void SetOP(ULPtr OP) { SetOP(*OP); }
//
//
//		// Add a material package to the metadata
//
//		PackagePtr AddMaterialPackage(UMIDPtr PackageUMID) { return AddPackage(MaterialPackage_UL, "", PackageUMID); }

	public Package addMaterialPackage(
			UMID packageUMID) {

		// TODO
		return null;
	}

//		PackagePtr AddMaterialPackage(std::string PackageName = "", UMIDPtr PackageUMID = NULL) { return AddPackage(MaterialPackage_UL, PackageName, PackageUMID); }
//
	public Package addMaterialPackage(
			String packageName,
			UMID pacakageUMID) {

		// TODO
		return null;
	}

	public Package addMaterialPackage() {

		return addMaterialPackage("", null);
	}
//
//		// Add a top-level file package to the metadata
//
//		PackagePtr AddFilePackage(UInt32 BodySID, UMIDPtr PackageUMID) { return AddPackage(SourcePackage_UL, "", PackageUMID, BodySID); }

	public Package addFilePackage(
			@UInt32 int bodySID,
			UMID packageUMID) {

		// TODO
		return null;
	}

//		PackagePtr AddFilePackage(UInt32 BodySID, std::string PackageName = "", UMIDPtr PackageUMID = NULL) { return AddPackage(SourcePackage_UL, PackageName, PackageUMID, BodySID); }
//
	public Package addFilePackage(
			@UInt32 int bodySID,
			String packageName,
			UMID packageUMID) {

		// TODO
		return null;
	}

	public Package addFilePackage(
			@UInt32 int bodySID) {

		return addFilePackage(bodySID, "", null);
	}

//
//		// Add a lower-level source package to the metadata
//
//		PackagePtr AddSourcePackage(UInt32 BodySID, UMIDPtr PackageUMID) { return AddPackage(SourcePackage_UL, "", PackageUMID, BodySID); }

	public Package addSourcePackage(
			@UInt32 int bodySID,
			UMID packageUMID) {

		// TODO
		return null;
	}

//		PackagePtr AddSourcePackage(UInt32 BodySID, std::string PackageName = "", UMIDPtr PackageUMID = NULL) { return AddPackage(SourcePackage_UL, PackageName, PackageUMID, BodySID); }
//
	public Package addSourcePackage(
			@UInt32 int bodySID,
			String packageName,
			UMID packageUMID) {

		// TODO
		return null;
	}

	public Package addSourcePackage(
			@UInt32 int bodySID) {

		return addSourcePackage(bodySID, "", null);
	}


//		//! Add an entry into the essence container data set for a given essence stream
//
//		bool AddEssenceContainerData(UMIDPtr TheUMID, UInt32 BodySID, UInt32 IndexSID = 0);
//
	public boolean addEssenceContainerData(
			UMID umid,
			@UInt32 int bodySID,
			@UInt32 int indexSID) {

		// TODO
		return false;
	}

	public boolean addEssenceContainerData(
			UMID umid,
			@UInt32 int bodySID) {

		return addEssenceContainerData(umid, bodySID, 0);
	}

//		//! Set the primary package property of the preface
//
//		void SetPrimaryPackage(PackagePtr Package) { SetPrimaryPackage(Package->Object); }
//
	public void setPrimaryPackage(
			Package primaryPackage) {

		// TODO
	}
//
//		//! Set the primary package property of the preface
//
//		void SetPrimaryPackage(MDObjectPtr Package)
//
//		{
//
//			MDObjectPtr Ptr = Object->Child(PrimaryPackage_UL);
//
//			if(!Ptr) Ptr = Object->AddChild(PrimaryPackage_UL);
//
//			Ptr->MakeRef(Package);
//
//		}
//
	public void setPrimaryPackage(
			MetadataObject primaryPackage) {

		// TODO
	}
//
//		//! Get a pointer to the primary package
//
//		PackagePtr GetPrimaryPackage(void);
//
	public Package getPrimaryPackage() {

		// TODO
		return null;
	}
//
//		//! Update the Generation UID of all modified sets and add the specified Ident set
//
//		bool UpdateGenerations(MDObjectPtr Ident, std::string UpdateTime = "");
//
	public boolean updateGenerations(
			MetadataObject identifier,
			String updateTime) {

		// TODO
		return false;
	}

	public boolean updateGenerations(
			MetadataObject identifier) {

		return updateGenerations(identifier, "");
	}


//
//		//! Return the containing "Metadata" object for this MDObject
//
//		/*! \return NULL if MDObject is not contained in a Metadata object
//
//		 */
//
//		static MetadataPtr GetMetadata(MDObjectPtr Object);
//
	public final static Metadata getMetadata(
			MetadataObject metadataObject) {

		// TODO
		return null;
	}
//
//		//! Parse an existing MDObject into a Metadata object
//
//		static MetadataPtr Parse(MDObjectPtr BaseObject);
//
	public final static Metadata parse(
			MetadataObject baseObject) {

		// TODO
		return null;
	}
//
//	private:
//
//		//! Add a package of the specified type to the matadata
//
//		PackagePtr AddPackage(const UL &PackageType, std::string PackageName, UMIDPtr PackageUMID, UInt32 BidySID = 0);
//
	private Package addPackage(
			UL packageType,
			String packageName,
			UMID packageUMID,
			@UInt32 int bodySID) {

		// TODO probably call out to the AAF factory
		return null;
	}
//
//		//! Update the Generation UID of a set if modified - then iterate through strongly linked sets
//
//		bool UpdateGenerations_Internal(MDObjectPtr Obj, UUIDPtr ThisGeneration);
//
	private boolean updateGenerationsInternal(
			MetadataObject metadataObject,
			UUID generation) {

		// TODO
		return false;
	}
//
//		//! Clear all modified flags for this set and strongly linked sets - used when adding initial Identification set
//
//		void ClearModified_Internal(MDObjectPtr Obj);

	private void clearModifiedInternal(
			MetadataObject metadataObject) {

		// TODO
	}

//	};
//


}
