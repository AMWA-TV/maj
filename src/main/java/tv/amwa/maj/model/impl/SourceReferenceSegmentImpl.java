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
 * $Log: SourceReferenceSegmentImpl.java,v $
 * Revision 1.4  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
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
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:10  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.model.SourceReferenceSegment;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.impl.PackageIDImpl;


/** 
 * <p>Implements the representation of essence or other data described 
 * by a {@linkplain tv.amwa.maj.model.Track track} in a {@linkplain tv.amwa.maj.model.Package package}.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#SourceReferenceStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#SourceReferenceStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "SourceReference",
		  description = "The SourceReference class represents the essence or other data described by a Track in a Package.",
		  symbol = "SourceReference",
		  isConcrete = false)
public class SourceReferenceSegmentImpl
	extends 
		SegmentImpl
	implements 
		SourceReferenceSegment,
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5589293931478011918L;
	
	private PackageID sourcePackageID = null;
	private int sourceTrackID;
	private int[] channelIds = null;
	private int[] monoSourceTrackIDs = null;
	
	@MediaProperty(uuid1 = 0x06010103, uuid2 = (short) 0x0700, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "ChannelIDs",
			typeName = "UInt32Array",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1103,
			symbol = "ChannelIDs")
	public int[] getChannelIDs()
			throws PropertyNotPresentException {

		if (channelIds == null)
			throw new PropertyNotPresentException("The optional channel ids property is not present in this source reference segment.");
		
		return channelIds.clone();
	}

	@MediaPropertySetter("ChannelIDs")
	public void setChannelIDs(
			int[] channelIDs) 
		throws IllegalArgumentException {

		if (channelIDs == null) {
			this.channelIds = null;
			return;
		}
		
		for ( int x : channelIDs )
			if (x < 0) 
				throw new IllegalArgumentException("The channel id at index " + x + " is negative, which is not allowed for this source reference segment.");
		
		this.channelIds = channelIDs.clone();
	}

	public int getChannelIDsSize() 
		throws PropertyNotPresentException {
		
		if (channelIds == null)
			throw new PropertyNotPresentException("The optional channel ids property is not present in this source reference segment.");

		return channelIds.length;
	}

	@MediaPropertyClear("ChannelIDs")
	public void clearChannelIDs() {
		
		channelIds = null;
	}
	
	@MediaProperty(uuid1 = 0x06010103, uuid2 = (short) 0x0800, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "MonoSourceTrackIDs",
			aliases = { "MonoSourceSlotIDs" },
			typeName = "UInt32Array",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1104,
			symbol = "MonoSourceTrackIDs")
	public int[] getMonoSourceTrackIDs()
			throws PropertyNotPresentException {

		if (monoSourceTrackIDs == null)
			throw new PropertyNotPresentException("The optional mono source track ids property is not present in this source reference.");
		
		return monoSourceTrackIDs.clone();
	}

	@MediaPropertySetter("MonoSourceTrackIDs")
	public void setMonoSourceTrackIDs(
			int[] monoSourceTrackIDs) 
		throws IllegalArgumentException {
		
		if (monoSourceTrackIDs == null) {
			this.monoSourceTrackIDs = null;
			return;
		}
		
		for ( int x : monoSourceTrackIDs )
			if (x < 0) 
				throw new IllegalArgumentException("The source track id at index " + x + " is negative, which is not permitted for this source reference.");
		
		this.monoSourceTrackIDs = monoSourceTrackIDs.clone();
	}
	
	@MediaPropertyCount("MonoSourceTrackIDs")
	public int getMonoSourceTrackIDsSize() 
		throws PropertyNotPresentException {
		
		if (monoSourceTrackIDs == null)
			throw new PropertyNotPresentException("The optional mono source track ids property is not present in this source reference.");

		return monoSourceTrackIDs.length;
	}
	
	@MediaPropertyClear("MonoSourceTrackIDs")
	public void clearMonoSourceTrackIDs() {
		
		monoSourceTrackIDs = null;
	}

	@MediaProperty(uuid1 = 0x06010103, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "SourcePackageID",
			aliases = { "SourceID" , "SourceMobID" },
			typeName = "PackageIDType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1101,
			symbol = "SourcePackageID")
	public PackageID getSourcePackageID() 
		throws PropertyNotPresentException {

		if (sourcePackageID == null)
			throw new PropertyNotPresentException("The optional source id property is not present for this source reference, implying that this is a reference to a track within the same package.");
		
		return sourcePackageID.clone();
	}

	@MediaPropertySetter("SourcePackageID")
	public void setSourcePackageID(
			tv.amwa.maj.record.PackageID sourcePackageID) {
		
		if (sourcePackageID == null) {
			this.sourcePackageID = null;
			return;
		}

		this.sourcePackageID = sourcePackageID.clone();
	}

	@MediaProperty(uuid1 = 0x06010103, uuid2 = (short) 0x0200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "SourceTrackID",
			aliases = { "SourceMobSlotID" },
			typeName = "UInt32", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1102,
			symbol = "SourceTrackID")
	public int getSourceTrackID() {

		return sourceTrackID;
	}

	@MediaPropertySetter("SourceTrackID")
	public void setSourceTrackID(
			int sourceTrackID) 
		throws IllegalArgumentException {

		if (sourceTrackID < 0) 
			throw new IllegalArgumentException("Cannot set the source track id of this source reference to a negative value.");
		this.sourceTrackID = sourceTrackID;
	}

	public final static int initializeSourceTrackID() {
		
		return 0;
	}
	
	public SourceReferenceSegment clone() {
		
		return (SourceReferenceSegment) super.clone();
	}
	
	public String getSourcePackageIDString() {
		
		return PackageIDImpl.toPersistentForm(sourcePackageID);
	}
	
	public void setSourcePackageIDString(
			String sourcePackageID) {
		
		this.sourcePackageID = PackageIDImpl.fromPersistentForm(sourcePackageID);
	}
}
