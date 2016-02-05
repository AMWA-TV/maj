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
 * $Log: PackageImpl.java,v $
 * Revision 1.6  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.5  2011/07/27 17:33:23  vizigoth
 * Fixed imports to clear warnings.
 *
 * Revision 1.4  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/05/20 18:52:14  vizigoth
 * Adding support for Avid extensions.
 *
 * Revision 1.5  2010/05/19 22:22:58  vizigoth
 * Adding Avid extensions.
 *
 * Revision 1.4  2010/04/16 15:25:52  vizigoth
 * Fix to ensure the auto-initialized package ID has a not specified instance number type.
 *
 * Revision 1.3  2010/02/10 23:56:13  vizigoth
 * Improvements to create and mod time method names in Package to match meta dictionary. Also, added static initialize_PropertyName_ methods for required properties.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.6  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.5  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.4  2008/01/27 11:14:42  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.3  2007/12/13 11:31:51  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.2  2007/12/04 13:04:49  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:15  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

// TODO extra package methods from ImplAAFMob.cpp
// TODO what keeps track ids unique

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.constant.DataDefinitionConstant;
import tv.amwa.maj.enumeration.AppendOption;
import tv.amwa.maj.enumeration.Depend;
import tv.amwa.maj.enumeration.IncludedMedia;
import tv.amwa.maj.exception.AdjacentTransitionException;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.EventSemanticsException;
import tv.amwa.maj.exception.InsufficientTransitionMaterialException;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.LeadingTransitionException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.TimecodeNotFoundException;
import tv.amwa.maj.exception.TrackExistsException;
import tv.amwa.maj.exception.TrackNotFoundException;
import tv.amwa.maj.extensions.avid.AvidConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.model.KLVData;
import tv.amwa.maj.model.Package;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.model.Sequence;
import tv.amwa.maj.model.SourceClip;
import tv.amwa.maj.model.TaggedValue;
import tv.amwa.maj.model.TimelineTrack;
import tv.amwa.maj.model.Track;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.TimeStamp;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.record.impl.PackageIDImpl;
import tv.amwa.maj.record.impl.RationalImpl;
import tv.amwa.maj.record.impl.TimeStampImpl;
import tv.amwa.maj.record.impl.TimecodeValueImpl;


/**
 * <p>Implements a package, which can describe a composition, essence, or
 * physical media. A package has a unique identifier and consists of metadata.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#PackageWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PackageStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PackageStrongReferenceSet
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3400,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Package",
		  aliases = { "Mob" },
		  description = "Specifies a package, which can describe a composition, essence, or physical media.",
		  symbol = "Package",
		  isConcrete = false)
public class PackageImpl
	extends InterchangeObjectImpl
	implements
		Package,
		tv.amwa.maj.extensions.avid.Package,
		Serializable,
		Cloneable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1772171014938118040L;

	private PackageID packageID;
	private String packageName = null;
	private List<Track> packageTracks = Collections.synchronizedList(new Vector<Track>());
	private TimeStamp packageLastModified;
	private TimeStamp creationTime;
	private List<TaggedValue> packageUserComments = Collections.synchronizedList(new Vector<TaggedValue>());
	private List<TaggedValue> packageAttributes = Collections.synchronizedList(new Vector<TaggedValue>());
	private List<KLVData> packageKLVData = Collections.synchronizedList(new Vector<KLVData>());
	private AUID packageUsage = null;

	@MediaProperty(uuid1 = 0x03010210, uuid2 = (short) 0x0700, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "PackageAttributes",
			aliases = { "Attributes", "MobAttributes" },
			typeName = "TaggedValueStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4409,
			symbol = "PackageAttributes")
	public List<TaggedValue> getPackageAttributes()
		throws PropertyNotPresentException {

		if (packageAttributes.size() == 0)
			throw new PropertyNotPresentException("The optional attributes property is not present in this package.");

		return StrongReferenceVector.getOptionalList(packageAttributes);
	}

	public void appendPackageAttribute(
			String name,
			String value)
		throws NullPointerException {

		if (name == null)
			throw new NullPointerException("Cannot create and append a new attribute to the list of attributes for this package with a null name value.");
		if (value == null)
			throw new NullPointerException("Cannot create and append a new attribute to the list of attributes for this package with a null value.");

		TaggedValue taggedValue = new TaggedValueImpl(
				name,
				TypeDefinitions.UTF16String,
				value);
		StrongReferenceVector.appendNoClone(packageAttributes, taggedValue);
	}

	@MediaListAppend("PackageAttributes")
	public void appendPackageAttribute(
			TaggedValue packageAttribute)
		throws NullPointerException {

		if (packageAttribute == null)
			throw new NullPointerException("Cannot append a null attribute to the attributes of this package.");

		StrongReferenceVector.append(packageAttributes, packageAttribute);
	}

	@MediaListPrepend("PackageAttributes")
	public void prependPackageAttribute(
			TaggedValue packageAttribute)
		throws NullPointerException {

		if (packageAttribute == null)
			throw new NullPointerException("Cannot prepend a null attribute to the attributes of this package.");

		StrongReferenceVector.prepend(packageAttributes, packageAttribute);
	}

	@MediaPropertyCount("PackageAttributes")
	public int countPackageAttributes() {

		return packageAttributes.size();
	}

	@MediaPropertyClear("PackageAttributes")
	public void clearPackageAttributes() {

		packageAttributes = Collections.synchronizedList(new Vector<TaggedValue>());
	}

	@MediaPropertyRemove("PackageAttributes")
	public void removePackageAttribute(
			TaggedValue packageAttribute)
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException {

		if (packageAttribute == null)
			throw new NullPointerException("Cannot remove an attribute from the list of attributes of this package using a null value.");

		if (packageAttributes.size() == 0)
			throw new PropertyNotPresentException("The optional attributes property is not present in this package.");

		if (!(packageAttributes.contains(packageAttribute)))
			throw new ObjectNotFoundException("Canoot remove the given attribute from the list of attributes of this package as it is not currently contained.");

		StrongReferenceVector.remove(packageAttributes, packageAttribute);
	}

	@MediaProperty(uuid1 = 0x03020102, uuid2 = (short) 0x0c00, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PackageUserComments",
			aliases = { "UserComments", "MobUserComments" },
			typeName = "TaggedValueStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4406,
			symbol = "PackageUserComments")
	public List<TaggedValue> getPackageUserComments()
		throws PropertyNotPresentException {

		if (packageUserComments.size() == 0)
			throw new PropertyNotPresentException("The optional user comments property is not present in this package.");

		return StrongReferenceVector.getOptionalList(packageUserComments);
	}

	public void appendPackageUserComment(
			String category,
			String comment)
		throws NullPointerException {

		if (category == null)
			throw new NullPointerException("Cannot create and append a new user comment for this package will a null category name value.");
		if (comment == null)
			throw new NullPointerException("Cannot create and append a new user comment for this package with a null comment value.");

		TaggedValue taggedValue = new TaggedValueImpl(
				category,
				TypeDefinitions.UTF16String,
				comment);
		StrongReferenceVector.appendNoClone(packageUserComments, taggedValue);
	}

	@MediaListAppend("PackageUserComments")
	public void appendPackageUserComment(
			TaggedValue packageUserComment)
		throws NullPointerException {

		if (packageUserComment == null)
			throw new NullPointerException("Cannot append a new user comment with a null tagged value to this package.");

		StrongReferenceVector.append(packageUserComments, packageUserComment);
	}

	@MediaListPrepend("PackageUserComments")
	public void prependPackageUserComment(
			TaggedValue comment)
		throws NullPointerException {

		if (comment == null)
			throw new NullPointerException("Cannot prepend a new user comment with a null tagged value to this package.");

		StrongReferenceVector.prepend(packageUserComments, comment);
	}

	@MediaPropertyCount("PackageUserComments")
	public int countPackageUserComments() {

		return packageUserComments.size();
	}

	@MediaPropertyClear("PackageUserComments")
	public void clearPackageUserComments() {

		packageUserComments = Collections.synchronizedList(new Vector<TaggedValue>());
	}

	@MediaPropertyRemove("PackageUserComments")
	public void removePackageUserComment(
			TaggedValue packageUserComment)
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException {

		if (packageUserComment == null)
			throw new NullPointerException("Cannot remove the given comment from the user comments of this package as it is null.");

		if (packageUserComments.size() == 0)
			throw new PropertyNotPresentException("The optional user comments property is not present in this package.");

		if (!(packageUserComments.contains(packageUserComment)))
			throw new ObjectNotFoundException("Cannot remove the given comment for the user comments of this package as it is not currently contained.");

		StrongReferenceVector.remove(packageUserComments, packageUserComment);
	}

	@MediaProperty(uuid1 = 0x03010210, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PackageKLVData",
			aliases = { "MobKLVData", "KLVData" },
			typeName = "KLVDataStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4407,
			symbol = "PackageKLVData")
	public List<KLVData> getPackageKLVData()
		throws PropertyNotPresentException {

		if (packageKLVData.size() == 0)
			throw new PropertyNotPresentException("No KLV data values are present in this package.");

		return StrongReferenceVector.getOptionalList(packageKLVData);
	}

	@MediaListAppend("PackageKLVData")
	public void appendPackageKLVData(
			KLVData packageKLVdata)
		throws NullPointerException {

		if (packageKLVdata == null)
			throw new NullPointerException("Cannot append a null klv data item to the list of klv data items of this package.");

		StrongReferenceVector.append(this.packageKLVData, packageKLVdata);
	}

	@MediaListPrepend("PackageKLVData")
	public void prependPackageKLVData(
			KLVData packageKLVdata)
		throws NullPointerException {

		if (packageKLVdata == null)
			throw new NullPointerException("Cannot prepend a null klv data item to the list of klv data items of this package.");

		StrongReferenceVector.prepend(this.packageKLVData, packageKLVdata);
	}

	@MediaPropertyCount("PackageKLVData")
	public int countPackageKLVData() {

		return packageKLVData.size();
	}

	@MediaPropertyClear("PackageKLVData")
	public void clearPackageKLVData() {

		packageKLVData = Collections.synchronizedList(new Vector<KLVData>());
	}

	@MediaPropertyRemove("PackageKLVData")
	public void removePackageKLVData(
			KLVData packageKLVdata)
		throws NullPointerException,
			ObjectNotFoundException {

		if (packageKLVdata == null)
			throw new NullPointerException("Cannot remove a null value from the list of klv data items of this package.");

		if (!(packageKLVData.contains(packageKLVdata)))
			throw new ObjectNotFoundException("Cannot remove the given klv data item from the list of klv data items of this package as it is not currently contained.");

		StrongReferenceVector.remove(this.packageKLVData, packageKLVdata);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0605, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PackageTracks",
			aliases = { "Slots", "MobSlots" },
			typeName = "TrackStrongReferenceVector",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4403,
			symbol = "PackageTracks")
	public List<Track> getPackageTracks() {

		return StrongReferenceVector.getRequiredList(packageTracks);
	}

	public final static List<Track> initializePackageTracks() {

		List<Track> initialTracks = new ArrayList<Track>(1);
		Segment filler = new FillerImpl(DataDefinitionImpl.forName("Unknown"), 0l);
		TimelineTrack firstTrack = new TimelineTrackImpl(1, filler, new RationalImpl(1, 1), 0l);
		initialTracks.add(firstTrack);
		return initialTracks;
	}

	public EventTrackImpl appendNewEventTrack(
			tv.amwa.maj.record.Rational editRate,
			tv.amwa.maj.model.Segment segment,
			int trackID,
			String trackName) // Removed origin property as not required for event tracks.
		throws NullPointerException,
			IllegalArgumentException,
			TrackExistsException {

		EventTrackImpl createdTrack = new EventTrackImpl(trackID, segment, editRate);
		createdTrack.setTrackName(trackName);

		appendPackageTrack(createdTrack);
		return createdTrack;
	}

	public StaticTrackImpl appendNewStaticTrack(
			tv.amwa.maj.model.Segment segment,
			int trackID,
			String trackName)
		throws NullPointerException,
			IllegalArgumentException,
			TrackExistsException {

		StaticTrackImpl createdTrack = new StaticTrackImpl(trackID, segment);
		createdTrack.setTrackName(trackName);

		appendPackageTrack(createdTrack);
		return createdTrack;
	}

	public TimelineTrackImpl appendNewTimelineTrack(
			tv.amwa.maj.record.Rational editRate,
			tv.amwa.maj.model.Segment segment,
			@TrackID int trackID,
			String trackName,
			long origin)
		throws NullPointerException,
			IllegalArgumentException,
			TrackExistsException {

		TimelineTrackImpl createdTrack = new TimelineTrackImpl(trackID, segment, editRate, origin);
		createdTrack.setTrackName(trackName);

		appendPackageTrack(createdTrack);
		return createdTrack;
	}

	@MediaListAppend("PackageTracks")
	public void appendPackageTrack(
			Track track)
		throws NullPointerException,
			TrackExistsException {

		if (track == null)
			throw new NullPointerException("Cannot append a null track to the list of tracks for this package.");

		// Clear the default value out of the way
		if ((packageTracks.size() == 1) &&
				(packageTracks.get(0) instanceof TimelineTrackImpl) &&
					(((TimelineTrack) packageTracks.get(0)).getDataDefinition().equals(DataDefinitionImpl.forAUID(DataDefinitionConstant.Unknown))) &&
						(((TimelineTrack) packageTracks.get(0)).getEditRate().equals(new RationalImpl(1, 1))))
			clearPackageTracks();

		int givenTrackID = track.getTrackID();
		for ( Track trackItem : packageTracks )
			if (trackItem.getTrackID() == givenTrackID)
				throw new TrackExistsException("A track with track id " + givenTrackID + " is already contained in this package.");

		StrongReferenceVector.append(packageTracks, track);
	}

	@MediaPropertyCount("PackageTracks")
	public int countPackageTracks() {

		return packageTracks.size();
	}

	@MediaListGetAt("PackageTracks")
	public Track getPackageTrackAt(
			int index)
		throws IndexOutOfBoundsException {

		return StrongReferenceVector.getAt(packageTracks, index);
	}

	@MediaListInsertAt("PackageTracks")
	public void insertPackageTrackAt(
			int index,
			Track track)
		throws NullPointerException,
			IndexOutOfBoundsException,
			TrackExistsException {

		if (track == null)
			throw new NullPointerException("Cannot insert a null-valued track into the list of tracks of this package.");

		// Clear the default value out of the way
		if ((packageTracks.size() == 1) &&
				(packageTracks.get(0) instanceof TimelineTrackImpl) &&
					(((TimelineTrack) packageTracks.get(0)).getDataDefinition().equals(DataDefinitionImpl.forAUID(DataDefinitionConstant.Unknown))) &&
						(((TimelineTrack) packageTracks.get(0)).getEditRate().equals(new RationalImpl(1, 1))))
			clearPackageTracks();

		int givenTrackId = track.getTrackID();
		for ( Track trackItem : packageTracks )
			if (trackItem.getTrackID() == givenTrackId)
				throw new TrackExistsException("The given track id of " + givenTrackId + " is already used to identify a track contained in this package.");

		StrongReferenceVector.insert(packageTracks, index, track);
	}

	public Track lookupPackageTrack(
			int trackID)
		throws TrackNotFoundException {

		if (trackID < 0)
			throw new TrackNotFoundException("Cannot lookup a track with a negative track number from the list of tracks of this package.");

		for ( Track track : packageTracks) {
			if (track.getTrackID() == trackID)
				return track;
		}

		throw new TrackNotFoundException("Could not find a track with the given track id in the list of tracks of this package.");
	}

	@MediaListPrepend("PackageTracks")
	public void prependPackageTrack(
			Track track)
		throws NullPointerException,
			TrackExistsException {

		if (track == null)
			throw new NullPointerException("Cannot prepend a null track to the list of tracks for this package.");

		// Clear the default value out of the way
		if ((packageTracks.size() == 1) &&
				(packageTracks.get(0) instanceof TimelineTrackImpl) &&
					(((TimelineTrack) packageTracks.get(0)).getDataDefinition().equals(DataDefinitionImpl.forAUID(DataDefinitionConstant.Unknown))) &&
						(((TimelineTrack) packageTracks.get(0)).getEditRate().equals(new RationalImpl(1, 1))))
			clearPackageTracks();

		int givenTrackId = track.getTrackID();
		for ( Track trackItem : packageTracks )
			if (trackItem.getTrackID() == givenTrackId)
				throw new TrackExistsException("The given track id of " + givenTrackId + " is already used to identify a track contained in this package.");

		StrongReferenceVector.prepend(packageTracks, track);
	}

	@MediaListRemoveAt("PackageTracks")
	public void removePackageTrackAt(
			int index)
		throws IndexOutOfBoundsException {

		StrongReferenceVector.remove(packageTracks, index);
	}

	@MediaPropertyClear("PackageTracks")
	public void clearPackageTracks() {

		packageTracks = Collections.synchronizedList(new Vector<Track>());
	}

	@MediaProperty(uuid1 = 0x07020110, uuid2 = (short) 0x0103, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "CreationTime",
			aliases = { "CreateTime", "CreationDate" },
			typeName = "TimeStamp",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4405,
			symbol = "CreationTime")
	public TimeStamp getCreationTime() {

		return creationTime.clone();
	}

	@MediaPropertySetter("CreationTime")
	public void setCreationTime(
			TimeStamp createTime)
		throws NullPointerException {

		if (createTime == null)
			throw new NullPointerException("Cannot set the required creation time property of this package with a null value.");

		this.creationTime = createTime.clone();
	}

	public final static TimeStamp initializeCreationTime() {

		return new TimeStampImpl();
	}

	@MediaProperty(uuid1 = 0x01011510, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "PackageID",
			aliases = { "MobID" },
			typeName = "PackageIDType",
			optional = false,
			uniqueIdentifier = true,
			pid = 0x4401,
			symbol = "PackageID")
	public PackageID getPackageID() {

		return packageID.clone();
	}

	@MediaPropertySetter("PackageID")
	public void setPackageID(
			PackageID packageID)
		throws NullPointerException {

		if (packageID == null)
			throw new NullPointerException("Canoot set the package id of this package using a null value.");

		this.packageID = packageID.clone();

		// mobCache.put(this.mobId, this);
	}

	public final static PackageID initializePackageID() {

		return PackageIDImpl.umidFactory(
				tv.amwa.maj.enumeration.MaterialType.NotIdentified,
			    tv.amwa.maj.record.MaterialNumberGeneration.UUID_UL,
			    tv.amwa.maj.record.InstanceNumberGeneration.NotDefined,
			    AUIDImpl.randomAUID().getAUIDValue());
	}

	@MediaProperty(uuid1 = 0x07020110, uuid2 = (short) 0x0205, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PackageLastModified",
			aliases = { "LastModified", "MobLastModified", "ModificationDate" },
			typeName = "TimeStamp",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4404,
			symbol = "PackageLastModified")
	public TimeStamp getPackageLastModified() {

		return packageLastModified.clone();
	}

	@MediaPropertySetter("PackageLastModified")
	public void setPackageLastModified(
			tv.amwa.maj.record.TimeStamp modTime)
		throws NullPointerException {

		if (modTime == null)
			throw new NullPointerException("Cannot set the last modificaiton value of this package with a null value.");

		this.packageLastModified = modTime.clone();
	}

	public final static TimeStamp initializePackageLastModified() {

		return new TimeStampImpl();
	}

	@MediaProperty(uuid1 = 0x01030302, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "PackageName",
			aliases = { "Name", "MobName" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4402,
			symbol = "PackageName")
	public String getPackageName()
		throws PropertyNotPresentException {

		if (packageName == null)
			throw new PropertyNotPresentException("The optional name property is not present in this package.");

		return packageName;
	}

	@MediaPropertySetter("PackageName")
	public void setPackageName(
			String packageName) {

		this.packageName = packageName;
	}

	@MediaProperty(uuid1 = 0x05010108, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "PackageUsage",
			aliases = { "UsageCode" },
			typeName = "UsageType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4408,
			symbol = "PackageUsage")
	public AUID getPackageUsage()
		throws PropertyNotPresentException {

		if (packageUsage == null)
			throw new PropertyNotPresentException("The optional package usage property is not present in this package.");

		return packageUsage;
	}

	@MediaPropertySetter("PackageUsage")
	public void setPackageUsage(
			tv.amwa.maj.record.AUID packageUsage) {

		if (packageUsage == null)
			this.packageUsage = null;
		else
			this.packageUsage = packageUsage.clone();
	}

	public TimecodeValueImpl offsetToPackageTimecode(
			tv.amwa.maj.model.Segment tcSeg,
			long offset)
			throws NullPointerException,
				TimecodeNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void changeReference(
			tv.amwa.maj.record.PackageID oldPackageID,
			tv.amwa.maj.record.PackageID newPackageID)
		throws NullPointerException {
		// TODO Auto-generated method stub

	}

	public tv.amwa.maj.model.Package cloneExternal(
			Depend resolveDependencies,
			IncludedMedia includedMedia,
			tv.amwa.maj.model.AAFFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	public tv.amwa.maj.model.Package copy(
			String destMobName)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	void addPhysicalSourceReference(
			AppendOption addType,
			tv.amwa.maj.record.Rational editRate,
			int trackId,
			tv.amwa.maj.model.DataDefinition essenceKind,
			tv.amwa.maj.union.SourceReferenceValue reference,
			long sourceReferenceLength)
		throws NullPointerException,
			InvalidDataDefinitionException,
			BadLengthException,
			TrackExistsException {

		SourceClip sourceClip = new SourceClipImpl(essenceKind, sourceReferenceLength, reference);
		Track track = null;
		try {
			track = lookupPackageTrack(trackId);
		}
		catch (TrackNotFoundException snfe) {
			/* Use mobSlot == null to represent slot not found. */
		}

		if (track != null) {

			switch (addType) {

			case ForceOverwrite:
				track.setTrackSegment(sourceClip);
				break;
			case Append:
				Segment trackSegment = track.getTrackSegment();
				Sequence sequence = trackSegment.generateSequence();
				try {
					sequence.appendComponentObject(sourceClip);
				}
				catch (LeadingTransitionException e) {
					// Appending a segment
				} catch (EventSemanticsException e) {
					// Physical source reference, so not an event
				} catch (BadPropertyException e) {
					// This is unlikely but could happen, so leave the stack trace in
					e.printStackTrace();
				} catch (BadLengthException e) {
					// Very unlikely, but leave stack trace just in case.
					e.printStackTrace();
				} catch (AdjacentTransitionException e) {
					// Appending a segment
				} catch (InsufficientTransitionMaterialException e) {
					// Previous element is not a transition
				}
				track.setTrackSegment(sequence); // TODO this line is missing in ImplAAFMob.cpp
				break;
			default:
				// No other options for enumeration.
				break;
			}
		}
		else {
			appendNewTimelineTrack(editRate, sourceClip, trackId, null, 0l);
		}
	}
//
//	@SuppressWarnings("unused")
//	protected List<tv.amwa.maj.iface.FindSourceInformation> internalSearchSource(
//			int trackID,
//			long offset,
//			PackageKind mobKind,
//			CriteriaType mediaCriteria,
//			OperationChoice operationChoice)
//		throws NullPointerException,
//			InvalidPackageTypeException,
//			TraversalNotPossibleException {
//
//		List<tv.amwa.maj.iface.FindSourceInformation> sourceList =
//			new Vector<tv.amwa.maj.iface.FindSourceInformation>();
//
//		Track track = null;
//		try {
//			track = lookupPackageTrack(trackID);
//			FoundSegment foundSegment = track.findSegment(offset);
//			long componentLength = foundSegment.rootObject.getComponentLength();
//			FindSourceInformation sourceInfo = new FindSourceInformation(
//					null, 0, 0l, null, 0l, foundSegment.rootObject);
//
//			MobFindLeafArguments arguments = new MobFindLeafArguments(
//					track,
//					mediaCriteria,
//					operationChoice,
//					foundSegment.rootObject,
//					offset,
//					componentLength,
//					null,
//					null,
//					null,
//					0l);
//
//			// TODO implementation halted until its relevance has been determined
//		}
//		catch (Exception e) { }
//
//
//		return null;
//	}
//
//	/** TODO implementation, comments and test
//	 *
//	 * <p></p>
//	 *
//	 *
//	 */
//	static class ScopeStack {
//
//		// TODO does this actually exist? Cannot find it in the C version.
//	}
//
//	/** TODO implementation, comments and test
//	 *
//	 * <p></p>
//	 *
//	 *
//	 */
//	static class MobFindLeafArguments
//		implements Cloneable {
//
//		Track track;
//		CriteriaType mediaCriteria;
//		OperationChoice operationChoice;
//		ComponentImpl rootObject;
//		@PositionType long rootPosition;
//		@LengthType long rootLength;
//		ComponentImpl previousObject;
//		ComponentImpl nextObject;
//		ScopeStack scopeStack;
//		@PositionType long currentObjectPosition;
//		ComponentImpl foundObject = null;
//		@LengthType long minimumLength = 0l;
//		boolean foundTransition = false;
//		OperationGroup effectObject = null;
//		@Int32 int nestDepth = 0;
//		@PositionType long differencePosition = 0l;
//		PackageImpl mob = null;
//
//		MobFindLeafArguments(
//				Track track,
//				CriteriaType mediaCriteria,
//				OperationChoice operationChoice,
//				ComponentImpl rootObject,
//				@PositionType long rootPosition,
//				@LengthType long rootLength,
//				ComponentImpl previousObject,
//				ComponentImpl nextObject,
//				ScopeStack scopeStack,
//				@PositionType long currentObjectPosition) {
//
//			this.track = track;
//			this.mediaCriteria = mediaCriteria;
//			this.operationChoice = operationChoice;
//			this.rootObject = rootObject;
//			this.rootPosition = rootPosition;
//			this.rootLength = rootLength;
//			this.previousObject = previousObject;
//			this.nextObject = nextObject;
//			this.scopeStack = scopeStack;
//			this.currentObjectPosition = currentObjectPosition;
//		}
//
//		public MobFindLeafArguments clone() {
//
//			try {
//				return (MobFindLeafArguments) super.clone();
//			}
//			catch (CloneNotSupportedException cnse) {
//				// Implements cloneable so should not happen
//				cnse.printStackTrace();
//				return null;
//			}
//		}
//	}
//
//	void mobFindLeaf(
//			MobFindLeafArguments arguments)
//		throws TraversalNotPossibleException {
//
//		arguments.mob = this;
//
//		arguments.rootObject.getMinimumBounds(arguments);
//	}

	public Package clone() {

		return (Package) super.clone();
	}

	/* public final void forget() {

		mobCache.remove(this.mobId);
	} */

	// AVID extension properties - start

	private Boolean convertFrameRate = null;

	@MediaProperty(uuid1 = 0xd4243bd4, uuid2 = (short) 0x0142, uuid3 = (short) 0x4595,
			uuid4 = { (byte) 0xa8, (byte) 0xf3, (byte) 0xf2, (byte) 0xeb, (byte) 0xa5, (byte) 0x42, (byte) 0x44, (byte) 0xde },
			definedName = "ConvertFrameRate",
			typeName = "Boolean",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "ConvertFrameRate",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public boolean getConvertFrameRate()
		throws PropertyNotPresentException {

		if (convertFrameRate == null)
			throw new PropertyNotPresentException("The optional convert frame rate property is not present for this package.");

		return convertFrameRate;
	}

	@MediaPropertySetter("ConvertFrameRate")
	public void setConvertFrameRate(
			Boolean convertFrameRate) {

		this.convertFrameRate = convertFrameRate;
	}

	private List<TaggedValue> mobAttributeList =
		Collections.synchronizedList(new Vector<TaggedValue>());

	@MediaProperty(uuid1 = 0x60958183, uuid2 = (short) 0x47b1, uuid3 = (short) 0x11d4,
			uuid4 = { (byte) 0xa0, 0x1c, 0x00, 0x04, (byte) 0xac, (byte) 0x96, (byte) 0x9f, 0x50 },
			definedName = "MobAttributeList",
			typeName = "TaggedValueStrongReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "MobAttributeList",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public List<TaggedValue> getMobAttributeList()
		throws PropertyNotPresentException {

		if (mobAttributeList.size() == 0)
			throw new PropertyNotPresentException("The optional mob attribute list property is not present for this package.");

		return StrongReferenceVector.getOptionalList(mobAttributeList);
	}

	@MediaListAppend("MobAttributeList")
	public void appendMobAttributeItem(
			TaggedValue mobAttributeItem)
		throws NullPointerException {

		if (mobAttributeItem == null)
			throw new NullPointerException("Cannot append to the mob attribute list of this package using a null value.");

		StrongReferenceVector.append(mobAttributeList, mobAttributeItem);
	}

	@MediaListPrepend("MobAttributeList")
	public void prependMobAttributeItem(
			TaggedValue mobAttributeItem)
		throws NullPointerException {

		if (mobAttributeItem == null)
			throw new NullPointerException("Cannot prepend to the mob attribute list of this package using a null value.");

		StrongReferenceVector.prepend(mobAttributeList, mobAttributeItem);
	}

	@MediaListInsertAt("MobAttributeList")
	public void insertMobAttributeItem(
			int index,
			TaggedValue mobAttributeItem)
		throws NullPointerException,
			IndexOutOfBoundsException {

		if (mobAttributeItem == null)
			throw new NullPointerException("Cannot insert into the mob attribute list of this package using a null value.");

		StrongReferenceVector.insert(mobAttributeList, index, mobAttributeItem);
	}

	@MediaPropertyCount("MobAttributeList")
	public int countMobAttributeList() {

		return mobAttributeList.size();
	}

	@MediaPropertyClear("MobAttributeList")
	public void clearMobAttributeList() {

		mobAttributeList.clear();
	}

	@MediaListGetAt("MobAttributeList")
	public TaggedValue getMobAttributeItemAt(
			int index)
		throws IndexOutOfBoundsException {

		return StrongReferenceVector.getAt(mobAttributeList, index);
	}

	@MediaListRemoveAt("MobAttributeList")
	public void removeMobAttributeItemAt(
			int index)
		throws IndexOutOfBoundsException {

		StrongReferenceVector.remove(mobAttributeList, index);
	}

	private @Int64 Long subclipFullLength = null;

	@MediaProperty(uuid1 = 0x1262bf7b, uuid2 = (short) 0xfce2, uuid3 = (short) 0x4dfe,
			uuid4 = { (byte) 0xa0, (byte) 0xf6, (byte) 0xce, (byte) 0xec, 0x04, 0x7c, (byte) 0x80, (byte) 0xaa },
			definedName = "SubclipFullLength",
			typeName = "Int64",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "SubclipFullLength1",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public @Int64 long getSubclipFullLength()
		throws PropertyNotPresentException {

		if (subclipFullLength == null)
			throw new PropertyNotPresentException("The optional subclip full length property is not present for this package.");

		return subclipFullLength;
	}

	@MediaPropertySetter("SubclipFullLength")
	public void setSubclipFullLength(
			@Int64 Long subclipFullLength) {

		this.subclipFullLength = subclipFullLength;
	}

	private @Int64 Long subclipBegin= null;

	@MediaProperty(uuid1 = 0xaa24b657, uuid2 = (short) 0xfcbb, uuid3 = (short) 0x4921,
			uuid4 = { (byte) 0x95, 0x1d, 0x3a, 0x20, 0x38, 0x39, 0x67, 0x22 },
			definedName = "SubclipBegin",
			typeName = "Int64",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "SubclipBegin",
			namespace = AvidConstants.AVID_NAMESPACE,
			prefix = AvidConstants.AVID_PREFIX)
	public @Int64 long getSubclipBegin()
		throws PropertyNotPresentException {

		if (subclipBegin== null)
			throw new PropertyNotPresentException("The optional subclip begin property is not present for this package.");

		return subclipBegin;
	}

	@MediaPropertySetter("SubclipBegin")
	public void setSubclipBegin(
			@Int64 Long subclipBegin) {

		this.subclipBegin = subclipBegin;
	}

	// AVID extension properties - end

	public String getPackageUsageString() {

		return AUIDImpl.toPersistentForm(packageUsage);
	}

	public void setPackageUsageString(
			String packageUsage) {

		this.packageUsage = AUIDImpl.fromPersistentForm(packageUsage);
	}

	public String getPackageLastModifiedString() {

		return TimeStampImpl.toPersistentForm(packageLastModified);
	}

	public void setPackageLastModifiedString(
			String packageLastModified) {

		this.packageLastModified = TimeStampImpl.fromPersistentForm(packageLastModified);
	}

	public String getCreationTimeString() {

		return TimeStampImpl.toPersistentForm(creationTime);
	}

	public void setCreationTimeString(
			String creationTime) {

		this.creationTime = TimeStampImpl.fromPersistentForm(creationTime);
	}

	public String getPackageIDString() {

		return PackageIDImpl.toPersistentForm(packageID);
	}

	public void setPackageIDString(
			String packageID) {

		this.packageID = PackageIDImpl.fromPersistentForm(packageID);
	}

	public String getWeakTargetReference(){
		return "PackageWeakReference";
	}

	/** TODO
	 * <p>Is this the right AUID?</p>
	 */
	public AUID getAUID() {
		return this.packageID.getMaterial();
	}
}
