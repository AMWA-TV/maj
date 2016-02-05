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
 * $Log: MaterialPackageImpl.java,v $
 * Revision 1.3  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/07/14 13:34:35  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
 *
 * Revision 1.2  2010/02/10 23:57:35  vizigoth
 * Improvements to create and mod time method names in Package to match meta dictionary and other minor fixes.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.3  2008/01/27 11:14:41  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.2  2007/12/13 11:31:51  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.1  2007/11/13 22:09:09  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.CompressEnable;
import tv.amwa.maj.enumeration.CriteriaType;
import tv.amwa.maj.enumeration.MediaOpenMode;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.NotTapePackageException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.TrackExistsException;
import tv.amwa.maj.exception.TrackNotFoundException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.io.file.EssenceAccess;
import tv.amwa.maj.io.file.EssenceMultiAccess;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.MaterialPackage;
import tv.amwa.maj.model.PackageMarker;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.impl.RationalImpl;
import tv.amwa.maj.record.impl.TimeStampImpl;


/** 
 * <p>Implements a material object that provides access to {@linkplain tv.amwa.maj.model.SourcePackage source packages} 
 * and {@linkplain tv.amwa.maj.model.EssenceData essence data}.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3600,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "MaterialPackage",
		  aliases = { "MasterMob" },
		  description = "The MaterialPackage class provides access to the SourcePackages and EssenceData objects.",
		  symbol = "MaterialPackage")
public class MaterialPackageImpl
	extends 
		PackageImpl
	implements 
		MaterialPackage,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -2143970498731098833L;

  	private PackageMarker packageMarker = null;
	
	public MaterialPackageImpl() { }

	/**
	 * <p>Creates and initializes a material package, which provides access to associated 
	 * {@link SourcePackageImpl source package} and {@link EssenceDataImpl essence data} objects with its 
	 * unique material identifier and name. The required properties of creation time and 
	 * last modified time will be set automatically to the time of calling this method. 
	 * These timestamp values can be set manually by calling 
	 * {@link PackageImpl#setCreationTime(tv.amwa.maj.record.TimeStamp) setCreateTime()} and
	 * and {@link PackageImpl#setPackageLastModified(tv.amwa.maj.record.TimeStamp) setModTime()}.</p>
	 * 
	 * @param packageID Unique material identifier for the package.
	 * @param name Name of the material package.
	 * 
	 * @throws NullPointerException Unique material identifier is <code>null</code>.
	 */
	public MaterialPackageImpl(
			PackageID packageID,
			@AAFString String name)
		throws NullPointerException {
		
		if (packageID == null)
			throw new NullPointerException("Cannot create a new material package with a null package id.");
		if (name == null)
			throw new NullPointerException("Cannot create a new material package with a null name.");
		
		setPackageID(packageID);
		setPackageName(name);
		
		setPackageLastModified(new TimeStampImpl());
		setCreationTime(new TimeStampImpl());
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 020F, uuid3 = (short) 0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C },
			definedName = "PackageMarker",
			aliases = { "PackageMarkerObject" },
			typeName = "PackageMarkerStrongReference",
			optional = true,
			uniqueIdentifier = false,
			symbol = "PackageMarker",
			pid = 0,
			description = "Package marker for this material package, which " + 
					"specifies an optional sub-section that may be played as an alternative to the " + 
					"full material package." )
	public PackageMarker getPackageMarker() 
		throws PropertyNotPresentException {

		if (packageMarker == null)
			throw new PropertyNotPresentException("The optional package marker property is not present for this material package.");
		
		return packageMarker;
	}

	@MediaPropertySetter("PackageMarker")
	public void setPackageMarker(
			PackageMarker packageMarker) {

		this.packageMarker = packageMarker;
	}
	
	public void addMaterialTrack(
			tv.amwa.maj.model.DataDefinition dataDef,
			int sourceTrackID,
			tv.amwa.maj.model.SourcePackage sourcePackage,
			int masterTrackID,
			String trackName)
		throws NullPointerException,
			InvalidDataDefinitionException,
			TrackNotFoundException,
			TrackExistsException {
		// TODO Auto-generated method stub

	}

	public void appendPhysSourceRef(
			tv.amwa.maj.record.Rational editrate,
			int track,
			tv.amwa.maj.model.DataDefinition essenceKind,
			tv.amwa.maj.union.SourceReferenceValue ref,
			long srcRefLength)
		throws NullPointerException {
		// TODO Auto-generated method stub

	}

	public short countChannels(
			int trackID,
			CriteriaType mediaCrit,
			tv.amwa.maj.model.DataDefinition mediaKind)
		throws NullPointerException,
			TrackNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	public EssenceAccess createEssence(
			int masterTrackID,
			tv.amwa.maj.model.DataDefinition mediaKind,
			tv.amwa.maj.record.AUID codecID,
			tv.amwa.maj.record.Rational editRate,
			tv.amwa.maj.record.Rational samplerate,
			CompressEnable enable,
			tv.amwa.maj.model.Locator destination,
			tv.amwa.maj.record.AUID fileFormat)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	public EssenceAccess createEventEssence(
			int masterTrackID,
			tv.amwa.maj.model.DataDefinition mediaKind,
			tv.amwa.maj.record.AUID codecID,
			tv.amwa.maj.record.Rational editRate,
			tv.amwa.maj.record.Rational sampleRate,
			CompressEnable enable,
			tv.amwa.maj.model.Locator destination,
			tv.amwa.maj.record.AUID fileFormat)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	public EssenceMultiAccess createMultiEssence(
			tv.amwa.maj.record.AUID codecID,
			tv.amwa.maj.union.MultiCreateItem[] mediaArray,
			CompressEnable enable,
			tv.amwa.maj.model.Locator destination,
			tv.amwa.maj.record.AUID fileFormat)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}


	@SuppressWarnings("unused")
	public EssenceAccess createStaticEssence(
			int masterTrackID,
			tv.amwa.maj.model.DataDefinition mediaKind,
			tv.amwa.maj.record.AUID codecID,
			CompressEnable enable,
			tv.amwa.maj.model.Locator destination,
			tv.amwa.maj.record.AUID fileFormat)
		throws NullPointerException {

		if (mediaKind == null)
			throw new NullPointerException("Cannot create new static essence for this material package using a null media kind value.");
		if (codecID == null)
			throw new NullPointerException("Cannot create new static essence for this material package using a null codec id value.");
		if (enable == null)
			throw new NullPointerException("Cannot create new static essence for this material package using a null compress enable value.");
		
		RationalImpl editRate = new RationalImpl(1, 1);
		RationalImpl sampleRate = new RationalImpl(1, 1);

		AUID mediaKindId = mediaKind.getAUID();
		// EssenceAccess access = new EssenceAccess();
		
		// TODO once the global reference to a global dictionary has been resolved
		return null;
	}

	public EssenceAccess extendEssence(
			int masterTrackID,
			tv.amwa.maj.model.DataDefinition mediaKind,
			tv.amwa.maj.record.AUID codecID,
			tv.amwa.maj.record.Rational editRate,
			tv.amwa.maj.record.Rational sampleRate,
			CompressEnable enable,
			tv.amwa.maj.model.Locator destintation,
			tv.amwa.maj.record.AUID fileFormat)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	public EssenceMultiAccess extendMultiEssence(
			tv.amwa.maj.record.AUID codecID,
			tv.amwa.maj.union.MultiCreateItem[] mediaArray,
			CompressEnable enable,
			tv.amwa.maj.model.Locator destination,
			tv.amwa.maj.record.AUID fileFormat)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	public SegmentImpl getCriteriaSegment(
			int trackID,
			CriteriaType criteria)
		throws TrackNotFoundException,
			NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumberOfRepresentations(
			int trackID)
		throws TrackNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	public SegmentImpl getRepresentation(
			int trackID,
			int index)
		throws TrackNotFoundException,
			IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTapeName(
			int masterTrackID)
		throws TrackNotFoundException,
			NotTapePackageException {
		
		// TODO implementation when best location of these methods is known
		
		/* MobSlot tapeSlot = lookupSlot(masterSlotId);
		
		tv.amwa.maj.record.MobID tapeMobID;
		
		try {
			tapeMobID = ((tv.amwa.maj.model.SourceReferenceSegment) tapeSlot.getSegment()).getSourceID();
			
			if (MobID.getZeroMobID().equals(tapeMobID))
				throw new NotTapeMobException("Unexpectedly, the given slot is not a tape mob as it is an original source reference.");
			
			SourceMob tapeMob = (SourceMob) findMob(tapeMobID);
			if (!(tapeMob.getEssenceDescriptor() instanceof tv.amwa.maj.model.TapeDescriptor))
				throw new NotTapeMobException("The source mob referenced from the given master slot is not described by a tape descriptor.");
			return tapeMob.getName();
		}
		catch (PropertyNotPresentException pnpe) {
			// Slot references another slot in the same master mob - no recursion check
			return getTapeName(((tv.amwa.maj.model.SourceReferenceSegment) tapeSlot.getSegment()).getSourceMobSlotID());
		}
		catch (ClassCastException cce) {
			throw new NotTapeMobException("The given master track id does not reference a source mob and so does not contain a tape mob.");
		} */
		
		return null;
	}

	public void newPhysSourceRef(
			tv.amwa.maj.record.Rational editrate,
			int track,
			tv.amwa.maj.model.DataDefinition essenceKind,
			tv.amwa.maj.union.SourceReferenceValue ref,
			long srcRefLength)
		throws NullPointerException {
		// TODO Auto-generated method stub

	}

	public EssenceAccess openEssence(
			int trackID,
			CriteriaType mediaCrit,
			MediaOpenMode openMode,
			CompressEnable compEnable)
		throws NullPointerException,
			TrackNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public EssenceMultiAccess openMultiEssence(
			int trackID,
			CriteriaType mediaCrit,
			MediaOpenMode openMode,
			CompressEnable compEnable)
		throws NullPointerException,
			TrackNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public EventTrackImpl appendNewEventTrack(
			RationalImpl editRate,
			tv.amwa.maj.model.Segment segment,
			int trackID,
			String trackName)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	public StaticTrackImpl appendNewStaticTrack(
			tv.amwa.maj.model.Segment segment,
			int trackID,
			String trackName)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	public TimelineTrackImpl appendNewTimelineTrack(
			tv.amwa.maj.record.Rational editRate,
			tv.amwa.maj.model.Segment segment,
			int trackID,
			String trackName,
			long origin)
		throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	// Begin - Quantel extensions
	
	private Integer rushIngestTCDropframe = null;
	private Integer videoRushFirstLineIsF2 = null;
	private Integer videoRushChromaRange = null;
	private Integer videoRushProductionTCUserBits = null;
	private Integer audioRushChannels = null;
	private Integer videoRushProductionTCType = null;
	private Integer videoRushKeycodeUserBits = null;
	private Integer videoRushYAspect = null;
	private Integer videoRushProductionTCTimeBits = null;
	private Integer videoRushDominance = null;
	private Integer videoRushBlackLevel = null;
	private Integer audioRushSampleBits = null;
	private Integer rushIngestTCFPS = null;
	private Integer videoRushPlaneSize = null;
	private Integer videoRushVtrTCType = null;
	private Integer audioRushFrameRateNumerator = null;
	private String rushFilePath = null;
	private Integer rushIsFileSequence = null;
	private Integer videoRushRenderable = null;
	private Integer videoRushHosepipedRight = null;
	private Integer videoRushCompression = null;
	private Stream remoteRushBlob = null;
	private Integer videoRushXSize = null;
	private Integer videoRushVtrTCTimeBits = null;
	private Integer videoRushYSize = null;
	private Integer videoRushColourFormat = null;
	private Integer videoRushKeycodeTimeBits = null;
	private Integer videoRushVtrTCUserBits = null;
	private Integer videoRushFormatFlags = null;
	private Integer videoRushScanType = null;
	private Integer rushFileIsMultipleFrames = null;
	private Integer audioRushFrameRateDenominator = null;
	private Integer videoRushPlanes = null;
	private Integer rushIngestTC = null;
	private Integer videoRushWhiteLevel = null;
	private Integer videoRushKeycodeType = null;
	private Integer videoRushXAspect = null;
	
    @MediaProperty(uuid1 = 0x46081f05, uuid2 = (short) 0xe192, uuid3 = (short) 0x4a3a,
        uuid4 = { (byte) 0x8a, (byte) 0xba, (byte) 0x9e, (byte) 0x07, (byte) 0x6d, (byte) 0x82, (byte) 0x08, (byte) 0x18 },
        definedName = "Rush ingest tc dropframe",
        symbol = "Rush_ingest_tc_dropframe",
        aliases = { "Rush_ingest_tc_dropframe" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getRushIngestTCDropframe() {

        if (rushIngestTCDropframe == null)
            throw new PropertyNotPresentException("The optional rush ingest TC dropframe property is not present for this Quantel material package.");

        return rushIngestTCDropframe;
    }
	
	@MediaPropertySetter("Rush ingest tc dropframe")
    public void setRushIngestTCDropFrame(
        @Int32 Integer rushIngestTCDropframe) {

        this.rushIngestTCDropframe = rushIngestTCDropframe;
    }
	
    @MediaProperty(uuid1 = 0x8e1f2007, uuid2 = (short) 0x8464, uuid3 = (short) 0x4b66,
        uuid4 = { (byte) 0x84, (byte) 0x23, (byte) 0x55, (byte) 0x4e, (byte) 0x3f, (byte) 0xe2, (byte) 0x6f, (byte) 0xa9 },
        definedName = "Video rush firstLineIsF2",
        symbol = "Video_rush_firstLineIsF2",
        aliases = { "Video_rush_firstLineIsF2" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getVideoRushFirstLineIsF2() {

        if (videoRushFirstLineIsF2 == null)
            throw new PropertyNotPresentException("The optional video rush first line is F2 property is not present for this Quantel material package.");

        return videoRushFirstLineIsF2;
    }
	
	@MediaPropertySetter("Video rush firstLineIsF2")
    public void setVideoRushFirstLineIsF2(
        @Int32 Integer videoRushFirstLineIsF2) {

        this.videoRushFirstLineIsF2 = videoRushFirstLineIsF2;
    }

    @MediaProperty(uuid1 = 0x82960d09, uuid2 = (short) 0x7b01, uuid3 = (short) 0x45dc,
        uuid4 = { (byte) 0xb6, (byte) 0xe8, (byte) 0xa6, (byte) 0xf6, (byte) 0x53, (byte) 0x8e, (byte) 0x72, (byte) 0xe9 },
        definedName = "Video rush chroma range",
        symbol = "Video_rush_chroma_range",
        aliases = { "Video_rush_chroma_range" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushChromaRange() {

        if (videoRushChromaRange == null)
            throw new PropertyNotPresentException("The optional video rush chroma range property is not present for this Quantel material package.");

        return videoRushChromaRange;
    }
	
	@MediaPropertySetter("Video rush chroma range")
    public void setVideoRushChromaRange(
        @UInt32 Integer videoRushChromaRange) {

        if (videoRushChromaRange == null) { this.videoRushChromaRange = null; return; }

        if (videoRushChromaRange < 0)
            throw new IllegalArgumentException("Cannot set property video rush chroma range to a negative value.");

        this.videoRushChromaRange = videoRushChromaRange;
    }	
	
    @MediaProperty(uuid1 = 0x991baf10, uuid2 = (short) 0x619b, uuid3 = (short) 0x4396,
        uuid4 = { (byte) 0xb3, (byte) 0x9b, (byte) 0xe5, (byte) 0x32, (byte) 0x13, (byte) 0xc9, (byte) 0xde, (byte) 0x41 },
        definedName = "Video rush production tc user bits",
        symbol = "Video_rush_production_tc_user_bits",
        aliases = { "Video_rush_production_tc_user_bits" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)		
	public @UInt32 int getVideoRushProductionTCUserBits() {

        if (videoRushProductionTCUserBits == null)
            throw new PropertyNotPresentException("The optional video rush production TC user bits property is not present for this Quantel material package.");

        return videoRushProductionTCUserBits;
    }
	
	@MediaPropertySetter("Video rush production tc user bits")
    public void setVideoRushProductionTCUserBits(
        @UInt32 Integer videoRushProductionTCUserBits) {

        if (videoRushProductionTCUserBits == null) { this.videoRushProductionTCUserBits = null; return; }

        if (videoRushProductionTCUserBits < 0)
            throw new IllegalArgumentException("Cannot set property video rush production TC user bits to a negative value.");

        this.videoRushProductionTCUserBits = videoRushProductionTCUserBits;
    }

    @MediaProperty(uuid1 = 0x2333e014, uuid2 = (short) 0x5b1e, uuid3 = (short) 0x4700,
        uuid4 = { (byte) 0x99, (byte) 0x69, (byte) 0xf9, (byte) 0x4c, (byte) 0x15, (byte) 0xf0, (byte) 0x21, (byte) 0x99 },
        definedName = "Audio rush channels",
        symbol = "Audio_rush_channels",
        aliases = { "Audio_rush_channels" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getAudioRushChannels() {

        if (audioRushChannels == null)
            throw new PropertyNotPresentException("The optional audio rush channels property is not present for this Quantel material package.");

        return audioRushChannels;
    }

	@MediaPropertySetter("Audio rush channels")
    public void setAudioRushChannels(
        @UInt32 Integer audioRushChannels) {

        if (audioRushChannels == null) { this.audioRushChannels = null; return; }

        if (audioRushChannels < 0)
            throw new IllegalArgumentException("Cannot set property video rush production TC user bits to a negative value.");

        this.audioRushChannels = audioRushChannels;
    }
	
    @MediaProperty(uuid1 = 0xbf60ff1e, uuid2 = (short) 0xe154, uuid3 = (short) 0x4515,
        uuid4 = { (byte) 0x82, (byte) 0x36, (byte) 0xd6, (byte) 0x54, (byte) 0x0d, (byte) 0x19, (byte) 0x7d, (byte) 0x26 },
        definedName = "Video rush production tc type",
        symbol = "Video_rush_production_tc_type",
        aliases = { "Video_rush_production_tc_type" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushProductionTCType() {

        if (videoRushProductionTCType == null)
            throw new PropertyNotPresentException("The optional video rush production TC type property is not present for this Quantel material package.");

        return videoRushProductionTCType;
    }

	@MediaPropertySetter("Video rush production tc type")
    public void setVideoRushProductionTCType(
        @UInt32 Integer videoRushProductionTCType) {

        if (videoRushProductionTCType == null) { this.videoRushProductionTCType = null; return; }

        if (videoRushProductionTCType < 0)
            throw new IllegalArgumentException("Cannot set property video rush production TC type to a negative value.");

        this.videoRushProductionTCType = videoRushProductionTCType;
    }
	
    @MediaProperty(uuid1 = 0xee205f1f, uuid2 = (short) 0xcca9, uuid3 = (short) 0x46cf,
        uuid4 = { (byte) 0xa5, (byte) 0x9b, (byte) 0x5a, (byte) 0x50, (byte) 0x9b, (byte) 0x87, (byte) 0xdd, (byte) 0x2f },
        definedName = "Video rush keycode user bits",
        symbol = "Video_rush_keycode_user_bits",
        aliases = { "Video_rush_keycode_user_bits" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushKeycodeUserBits() {

        if (videoRushKeycodeUserBits == null)
            throw new PropertyNotPresentException("The optional video rush keycode user bits property is not present for this Quantel material package.");

        return videoRushKeycodeUserBits;
    }

	@MediaPropertySetter("Video rush keycode user bits")
    public void setVideoRushKeycodeUserBits(
        @UInt32 Integer videoRushKeycodeUserBits) {

        if (videoRushKeycodeUserBits == null) { this.videoRushKeycodeUserBits = null; return; }

        if (videoRushKeycodeUserBits < 0)
            throw new IllegalArgumentException("Cannot set property video rush keycode user bits to a negative value.");

        this.videoRushKeycodeUserBits = videoRushKeycodeUserBits;
    }
	
    @MediaProperty(uuid1 = 0xfad8ba26, uuid2 = (short) 0x219d, uuid3 = (short) 0x48c1,
        uuid4 = { (byte) 0x9b, (byte) 0xc0, (byte) 0x98, (byte) 0x49, (byte) 0x3e, (byte) 0xcf, (byte) 0x26, (byte) 0xbc },
        definedName = "Video rush y aspect",
        symbol = "Video_rush_y_aspect",
        aliases = { "Video_rush_y_aspect" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushYAspect() {

        if (videoRushYAspect == null)
            throw new PropertyNotPresentException("The optional video rush Y aspect property is not present for this Quantel material package.");

        return videoRushYAspect;
    }

	@MediaPropertySetter("Video rush y aspect")
    public void setVideoRushYAspect(
        @UInt32 Integer videoRushYAspect) {

        if (videoRushYAspect == null) { this.videoRushYAspect = null; return; }

        if (videoRushYAspect < 0)
            throw new IllegalArgumentException("Cannot set property video Rush Y aspect to a negative value.");

        this.videoRushYAspect = videoRushYAspect;
    }

    @MediaProperty(uuid1 = 0x2cb15b2b, uuid2 = (short) 0x0076, uuid3 = (short) 0x4b3c,
        uuid4 = { (byte) 0x84, (byte) 0xcb, (byte) 0x44, (byte) 0x31, (byte) 0xa2, (byte) 0xd6, (byte) 0xe2, (byte) 0xf4 },
        definedName = "Video rush production tc time bits",
        symbol = "Video_rush_production_tc_time_bits",
        aliases = { "Video_rush_production_tc_time_bits" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushProductionTCTimeBits() {

        if (videoRushProductionTCTimeBits == null)
            throw new PropertyNotPresentException("The optional video rush production TC time bits property is not present for this Quantel material package.");

        return videoRushProductionTCTimeBits;
    }

	@MediaPropertySetter("Video rush production tc time bits")
    public void setVideoRushProductionTCTimeBits(
        @UInt32 Integer videoRushProductionTCTimeBits) {

        if (videoRushProductionTCTimeBits == null) { this.videoRushProductionTCTimeBits = null; return; }

        if (videoRushProductionTCTimeBits < 0)
            throw new IllegalArgumentException("Cannot set property video rush production TC time bits to a negative value.");

        this.videoRushProductionTCTimeBits = videoRushProductionTCTimeBits;
    }
	
    @MediaProperty(uuid1 = 0x7097762e, uuid2 = (short) 0xfb52, uuid3 = (short) 0x47ac,
        uuid4 = { (byte) 0x85, (byte) 0xc6, (byte) 0xa1, (byte) 0x7d, (byte) 0x85, (byte) 0x17, (byte) 0xfa, (byte) 0x8d },
        definedName = "Video rush dominance",
        symbol = "Video_rush_dominance",
        aliases = { "Video_rush_dominance" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getVideoRushDominance() {

        if (videoRushDominance == null)
            throw new PropertyNotPresentException("The optional video rush dominance property is not present for this Quantel material package.");

        return videoRushDominance;
    }
		
	@MediaPropertySetter("Video rush dominance")
    public void setVideoRushDominance(
        @Int32 Integer videoRushDominance) {

        this.videoRushDominance = videoRushDominance;
    }

    @MediaProperty(uuid1 = 0x2a596f42, uuid2 = (short) 0x929c, uuid3 = (short) 0x43a6,
        uuid4 = { (byte) 0xbd, (byte) 0x4a, (byte) 0x26, (byte) 0x65, (byte) 0xab, (byte) 0x20, (byte) 0x4d, (byte) 0x2c },
        definedName = "Video rush black level",
        symbol = "Video_rush_black_level",
        aliases = { "Video_rush_black_level" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushBlackLevel() {

        if (videoRushBlackLevel == null)
            throw new PropertyNotPresentException("The optional video rush black level property is not present for this Quantel material package.");

        return videoRushBlackLevel;
    }

	@MediaPropertySetter("Video rush black level")
    public void setVideoRushBlackLevel(
        @UInt32 Integer videoRushBlackLevel) {

        if (videoRushBlackLevel == null) { this.videoRushBlackLevel = null; return; }

        if (videoRushBlackLevel < 0)
            throw new IllegalArgumentException("Cannot set property video rush black level to a negative value.");

        this.videoRushBlackLevel = videoRushBlackLevel;
    }
	
    @MediaProperty(uuid1 = 0x7e85d75b, uuid2 = (short) 0xfd3e, uuid3 = (short) 0x40b3,
        uuid4 = { (byte) 0xa7, (byte) 0x7e, (byte) 0xd8, (byte) 0xe0, (byte) 0xe0, (byte) 0x17, (byte) 0x0c, (byte) 0xa1 },
        definedName = "Audio rush sample bits",
        symbol = "Audio_rush_sample_bits",
        aliases = { "Audio_rush_sample_bits" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getAudioRushSampleBits() {

        if (audioRushSampleBits == null)
            throw new PropertyNotPresentException("The optional audio rush sample bits property is not present for this Quantel material package.");

        return audioRushSampleBits;
    }

	@MediaPropertySetter("Audio rush sample bits")
    public void setAudioRushSampleBits(
        @UInt32 Integer audioRushSampleBits) {

        if (audioRushSampleBits == null) { this.audioRushSampleBits = null; return; }

        if (audioRushSampleBits < 0)
            throw new IllegalArgumentException("Cannot set property audio rush sample bits to a negative value.");

        this.audioRushSampleBits = audioRushSampleBits;
    }
	
    @MediaProperty(uuid1 = 0x8b3f6a6d, uuid2 = (short) 0xe074, uuid3 = (short) 0x41c5,
        uuid4 = { (byte) 0xb0, (byte) 0x40, (byte) 0x82, (byte) 0xe1, (byte) 0xcc, (byte) 0x2b, (byte) 0xb0, (byte) 0x31 },
        definedName = "Rush ingest tc fps",
        symbol = "Rush_ingest_tc_fps",
        aliases = { "Rush_ingest_tc_fps" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getRushIngestTCFPS() {

        if (rushIngestTCFPS == null)
            throw new PropertyNotPresentException("The optional rush ingest TC FPS property is not present for this Quantel material package.");

        return rushIngestTCFPS;
    }
		
	@MediaPropertySetter("Rush ingest tc fps")
    public void setRushIngestTCFPS(
        @Int32 Integer rushIngestTCFPS) {

        this.rushIngestTCFPS = rushIngestTCFPS;
    }

    @MediaProperty(uuid1 = 0xd0d15771, uuid2 = (short) 0xb85e, uuid3 = (short) 0x4f10,
        uuid4 = { (byte) 0xa5, (byte) 0xbf, (byte) 0xcc, (byte) 0xcf, (byte) 0xc8, (byte) 0x9e, (byte) 0x0e, (byte) 0xc6 },
        definedName = "Video rush plane size",
        symbol = "Video_rush_plane_size",
        aliases = { "Video_rush_plane_size" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushPlaneSize() {

        if (videoRushPlaneSize == null)
            throw new PropertyNotPresentException("The optional video rush plane size property is not present for this Quantel material package.");

        return videoRushPlaneSize;
    }

	@MediaPropertySetter("Video rush plane size")
    public void setVideoRushPlaneSize(
        @UInt32 Integer videoRushPlaneSize) {

        if (videoRushPlaneSize == null) { this.videoRushPlaneSize = null; return; }

        if (videoRushPlaneSize < 0)
            throw new IllegalArgumentException("Cannot set property video rush plane size to a negative value.");

        this.videoRushPlaneSize = videoRushPlaneSize;
    }
		
    @MediaProperty(uuid1 = 0xf2a0b377, uuid2 = (short) 0x725b, uuid3 = (short) 0x4f5b,
        uuid4 = { (byte) 0x8c, (byte) 0xfc, (byte) 0xd5, (byte) 0xde, (byte) 0xe4, (byte) 0xdb, (byte) 0xf7, (byte) 0x89 },
        definedName = "Video rush vtr tc type",
        symbol = "Video_rush_vtr_tc_type",
        aliases = { "Video_rush_vtr_tc_type" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushVtrTCType() {

        if (videoRushVtrTCType == null)
            throw new PropertyNotPresentException("The optional video rush vtr TC type property is not present for this Quantel material package.");

        return videoRushVtrTCType;
    }

	@MediaPropertySetter("Video rush vtr tc type")
    public void setVideoRushVtrTCType(
        @UInt32 Integer videoRushVtrTCType) {

        if (videoRushVtrTCType == null) { this.videoRushVtrTCType = null; return; }

        if (videoRushVtrTCType < 0)
            throw new IllegalArgumentException("Cannot set property video rush vtr TC type to a negative value.");

        this.videoRushVtrTCType = videoRushVtrTCType;
    }

    @MediaProperty(uuid1 = 0xc8778b7c, uuid2 = (short) 0x3fdb, uuid3 = (short) 0x4b00,
        uuid4 = { (byte) 0xb8, (byte) 0x7f, (byte) 0xd1, (byte) 0xec, (byte) 0xa8, (byte) 0xef, (byte) 0xa9, (byte) 0xdb },
        definedName = "Audio rush frame rate numerator",
        symbol = "Audio_rush_frame_rate_numerator",
        aliases = { "Audio_rush_frame_rate_numerator" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getAudioRushFrameRateNumerator() {

        if (audioRushFrameRateNumerator == null)
            throw new PropertyNotPresentException("The optional audio rush frame rate numerator property is not present for this Quantel material package.");

        return audioRushFrameRateNumerator;
    }
		
	@MediaPropertySetter("Audio rush frame rate numerator")
    public void setAudioRushFrameRateNumerator(
        @Int32 Integer audioRushFrameRateNumerator) {

        this.audioRushFrameRateNumerator = audioRushFrameRateNumerator;
    }

    @MediaProperty(uuid1 = 0x94ee4180, uuid2 = (short) 0x8328, uuid3 = (short) 0x48fd,
        uuid4 = { (byte) 0x97, (byte) 0xbc, (byte) 0x95, (byte) 0xe2, (byte) 0xbc, (byte) 0xb3, (byte) 0x0c, (byte) 0xb8 },
        definedName = "Rush file path",
        symbol = "Rush_file_path",
        aliases = { "Rush_file_path" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public String getRushFilePath() {

        if (rushFilePath == null)
            throw new PropertyNotPresentException("The optional rush file path property is not present for this Quantel material package.");

        return rushFilePath;
    }
	
	@MediaPropertySetter("Rush file path")
    public void setRushFilePath(
         String rushFilePath) {

        this.rushFilePath = rushFilePath;
    }
	
    @MediaProperty(uuid1 = 0x7982f980, uuid2 = (short) 0x0cfd, uuid3 = (short) 0x4a15,
        uuid4 = { (byte) 0x9d, (byte) 0x08, (byte) 0x0a, (byte) 0x31, (byte) 0x1e, (byte) 0xb5, (byte) 0x93, (byte) 0x0d },
        definedName = "Rush is file sequence",
        symbol = "Rush_is_file_sequence",
        aliases = { "Rush_is_file_sequence" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getRushIsFileSequence() {

        if (rushIsFileSequence == null)
            throw new PropertyNotPresentException("The optional rush is file sequence property is not present for this Quantel material package.");

        return rushIsFileSequence;
    }
		
	@MediaPropertySetter("Rush is file sequence")
    public void setRushIsFileSequence(
        @Int32 int rushIsFileSequence) {

        this.rushIsFileSequence = rushIsFileSequence;
    }

    @MediaProperty(uuid1 = 0x8ae5d292, uuid2 = (short) 0x866a, uuid3 = (short) 0x45ea,
        uuid4 = { (byte) 0x87, (byte) 0xef, (byte) 0xef, (byte) 0x07, (byte) 0xad, (byte) 0x29, (byte) 0x54, (byte) 0x2b },
        definedName = "Video rush renderable",
        symbol = "Video_rush_renderable",
        aliases = { "Video_rush_renderable" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushRenderable() {

        if (videoRushRenderable == null)
            throw new PropertyNotPresentException("The optional video rush renderable property is not present for this Quantel material package.");

        return videoRushRenderable;
    }

	@MediaPropertySetter("Video rush renderable")
    public void setVideoRushRenderable(
        @UInt32 Integer videoRushRenderable) {

        if (videoRushRenderable == null) { this.videoRushRenderable = null; return; }

        if (videoRushRenderable < 0)
            throw new IllegalArgumentException("Cannot set property video rush renderable to a negative value.");

        this.videoRushRenderable = videoRushRenderable;
    }	
	
    @MediaProperty(uuid1 = 0x5a924e9c, uuid2 = (short) 0xe71d, uuid3 = (short) 0x446c,
        uuid4 = { (byte) 0x80, (byte) 0x83, (byte) 0xfe, (byte) 0x71, (byte) 0x12, (byte) 0x88, (byte) 0xb1, (byte) 0x6d },
        definedName = "Video rush hosepiped right",
        symbol = "Video_rush_hosepiped_right",
        aliases = { "Video_rush_hosepiped_right" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushHosepipedRight() {

        if (videoRushHosepipedRight == null)
            throw new PropertyNotPresentException("The optional video rush hosepiped right property is not present for this Quantel material package.");

        return videoRushHosepipedRight;
    }

	@MediaPropertySetter("Video rush hosepiped right")
    public void setVideoRushHosepipedRight(
        @UInt32 Integer videoRushHosepipedRight) {

        if (videoRushHosepipedRight == null) { this.videoRushHosepipedRight = null; return; }

        if (videoRushHosepipedRight < 0)
            throw new IllegalArgumentException("Cannot set property video rush hosepiped right to a negative value.");

        this.videoRushHosepipedRight = videoRushHosepipedRight;
    }	
	
    @MediaProperty(uuid1 = 0xe12800a0, uuid2 = (short) 0x8be5, uuid3 = (short) 0x410c,
        uuid4 = { (byte) 0x8c, (byte) 0xe9, (byte) 0xb7, (byte) 0xfe, (byte) 0x40, (byte) 0x96, (byte) 0x32, (byte) 0xf4 },
        definedName = "Video rush compression",
        symbol = "Video_rush_compression",
        aliases = { "Video_rush_compression" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushCompression() {

        if (videoRushCompression == null)
            throw new PropertyNotPresentException("The optional video rush compression property is not present for this Quantel material package.");

        return videoRushCompression;
    }

	@MediaPropertySetter("Video rush compression")
    public void setVideoRushCompression(
        @UInt32 Integer videoRushCompression) {

        if (videoRushCompression == null) { this.videoRushCompression = null; return; }

        if (videoRushCompression < 0)
            throw new IllegalArgumentException("Cannot set property video rush compression to a negative value.");

        this.videoRushCompression = videoRushCompression;
    }	
	
    @MediaProperty(uuid1 = 0x798714a5, uuid2 = (short) 0x794e, uuid3 = (short) 0x4357,
        uuid4 = { (byte) 0xbc, (byte) 0xfe, (byte) 0x49, (byte) 0x18, (byte) 0x25, (byte) 0xf9, (byte) 0xc9, (byte) 0xf5 },
        definedName = "Remote rush blob",
        symbol = "Remote_rush_blob",
        aliases = { "Remote_rush_blob" },
        typeName = "Stream",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public Stream getRemoteRushBlob() {

        if (remoteRushBlob == null)
            throw new PropertyNotPresentException("The optional remote rush blob property is not present for this Quantel material package.");

        return remoteRushBlob;
    }
	
	@MediaPropertySetter("Remote rush blob")
    public void setRemoteRushBlob(
         Stream remoteRushBlob) {

        this.remoteRushBlob = remoteRushBlob;
    }
	
    @MediaProperty(uuid1 = 0xc7b9e7af, uuid2 = (short) 0x34dd, uuid3 = (short) 0x47b2,
        uuid4 = { (byte) 0x85, (byte) 0x34, (byte) 0x2e, (byte) 0x0c, (byte) 0x0e, (byte) 0xe3, (byte) 0xed, (byte) 0x5c },
        definedName = "Video rush x size",
        symbol = "Video_rush_x_size",
        aliases = { "Video_rush_x_size" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushXSize() {

        if (videoRushXSize == null)
            throw new PropertyNotPresentException("The optional video rush X size property is not present for this Quantel material package.");

        return videoRushXSize;
    }

	@MediaPropertySetter("Video rush x size")
    public void setVideoRushXSize(
        @UInt32 Integer videoRushXSize) {

        if (videoRushXSize == null) { this.videoRushXSize = null; return; }

        if (videoRushXSize < 0)
            throw new IllegalArgumentException("Cannot set property video rush X size to a negative value.");

        this.videoRushXSize = videoRushXSize;
    }	
	
    @MediaProperty(uuid1 = 0xb424eeaf, uuid2 = (short) 0x7085, uuid3 = (short) 0x4c53,
        uuid4 = { (byte) 0xb9, (byte) 0x6a, (byte) 0xa2, (byte) 0xd1, (byte) 0x80, (byte) 0x30, (byte) 0x31, (byte) 0x9e },
        definedName = "Video rush vtr tc time bits",
        symbol = "Video_rush_vtr_tc_time_bits",
        aliases = { "Video_rush_vtr_tc_time_bits" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushVtrTCTimeBits() {

        if (videoRushVtrTCTimeBits == null)
            throw new PropertyNotPresentException("The optional video rush vtr TC time bits property is not present for this Quantel material package.");

        return videoRushVtrTCTimeBits;
    }

	@MediaPropertySetter("Video rush vtr tc time bits")
    public void setVideoRushVtrTCTimeBits(
        @UInt32 Integer videoRushVtrTCTimeBits) {

        if (videoRushVtrTCTimeBits == null) { this.videoRushVtrTCTimeBits = null; return; }

        if (videoRushVtrTCTimeBits < 0)
            throw new IllegalArgumentException("Cannot set property video rush vtr TC time bits to a negative value.");

        this.videoRushVtrTCTimeBits = videoRushVtrTCTimeBits;
    }
	
    @MediaProperty(uuid1 = 0x64f3f0af, uuid2 = (short) 0x5bbd, uuid3 = (short) 0x49bc,
        uuid4 = { (byte) 0xb3, (byte) 0xb6, (byte) 0x47, (byte) 0x5f, (byte) 0xe2, (byte) 0xad, (byte) 0x74, (byte) 0x95 },
        definedName = "Video rush y size",
        symbol = "Video_rush_y_size",
        aliases = { "Video_rush_y_size" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushYSize() {

        if (videoRushYSize == null)
            throw new PropertyNotPresentException("The optional video rush Y size property is not present for this Quantel material package.");

        return videoRushYSize;
    }

	@MediaPropertySetter("Video rush y size")
    public void setVideoRushYSize(
        @UInt32 Integer videoRushYSize) {

        if (videoRushYSize == null) { this.videoRushYSize = null; return; }

        if (videoRushYSize < 0)
            throw new IllegalArgumentException("Cannot set property video rush Y size to a negative value.");

        this.videoRushYSize = videoRushYSize;
    }
	
    @MediaProperty(uuid1 = 0xa06ee9b3, uuid2 = (short) 0xba38, uuid3 = (short) 0x42d7,
        uuid4 = { (byte) 0x9d, (byte) 0xb6, (byte) 0x9e, (byte) 0x6a, (byte) 0xcb, (byte) 0xdf, (byte) 0xf4, (byte) 0x04 },
        definedName = "Video rush colour format",
        symbol = "Video_rush_colour_format",
        aliases = { "Video_rush_colour_format" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushColourFormat() {

        if (videoRushColourFormat == null)
            throw new PropertyNotPresentException("The optional video rush colour format property is not present for this Quantel material package.");

        return videoRushColourFormat;
    }

	@MediaPropertySetter("Video rush colour format")
    public void setVideoRushColourFormat(
        @UInt32 Integer videoRushColourFormat) {

        if (videoRushColourFormat == null) { this.videoRushColourFormat = null; return; }

        if (videoRushColourFormat < 0)
            throw new IllegalArgumentException("Cannot set property video rush colour format to a negative value.");

        this.videoRushColourFormat = videoRushColourFormat;
    }
	
    @MediaProperty(uuid1 = 0xf42c69b5, uuid2 = (short) 0xc62f, uuid3 = (short) 0x4921,
        uuid4 = { (byte) 0xb1, (byte) 0xd9, (byte) 0x25, (byte) 0x63, (byte) 0x19, (byte) 0x9a, (byte) 0x70, (byte) 0x06 },
        definedName = "Video rush keycode time bits",
        symbol = "Video_rush_keycode_time_bits",
        aliases = { "Video_rush_keycode_time_bits" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushKeycodeTimeBits() {

        if (videoRushKeycodeTimeBits == null)
            throw new PropertyNotPresentException("The optional video rush keycode time bits property is not present for this Quantel material package.");

        return videoRushKeycodeTimeBits;
    }

	@MediaPropertySetter("Video rush keycode time bits")
    public void setVideoRushKeycodeTimeBits(
        @UInt32 Integer videoRushKeycodeTimeBits) {

        if (videoRushKeycodeTimeBits == null) { this.videoRushKeycodeTimeBits = null; return; }

        if (videoRushKeycodeTimeBits < 0)
            throw new IllegalArgumentException("Cannot set property video rush keycode time bits to a negative value.");

        this.videoRushKeycodeTimeBits = videoRushKeycodeTimeBits;
    }	
	
    @MediaProperty(uuid1 = 0x225512b6, uuid2 = (short) 0x85ec, uuid3 = (short) 0x4768,
        uuid4 = { (byte) 0xbf, (byte) 0xd8, (byte) 0x85, (byte) 0x84, (byte) 0x3a, (byte) 0x7c, (byte) 0xca, (byte) 0x41 },
        definedName = "Video rush vtr tc user bits",
        symbol = "Video_rush_vtr_tc_user_bits",
        aliases = { "Video_rush_vtr_tc_user_bits" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushVtrTCUserBits() {

        if (videoRushVtrTCUserBits == null)
            throw new PropertyNotPresentException("The optional video rush vtr TC user bits property is not present for this Quantel material package.");

        return videoRushVtrTCUserBits;
    }

	@MediaPropertySetter("Video rush vtr tc user bits")
    public void setVideoRushVtrTCUserBits(
        @UInt32 Integer videoRushVtrTCUserBits) {

        if (videoRushVtrTCUserBits == null) { this.videoRushVtrTCUserBits = null; return; }

        if (videoRushVtrTCUserBits < 0)
            throw new IllegalArgumentException("Cannot set property video rush vtr TC user bits to a negative value.");

        this.videoRushVtrTCUserBits = videoRushVtrTCUserBits;
    }		
	
    @MediaProperty(uuid1 = 0x3eba82bc, uuid2 = (short) 0xe986, uuid3 = (short) 0x42f6,
        uuid4 = { (byte) 0xa9, (byte) 0xf1, (byte) 0x92, (byte) 0x3d, (byte) 0xaf, (byte) 0xbc, (byte) 0xd4, (byte) 0x9f },
        definedName = "Video rush format flags",
        symbol = "Video_rush_format_flags",
        aliases = { "Video_rush_format_flags" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushFormatFlags() {

        if (videoRushFormatFlags == null)
            throw new PropertyNotPresentException("The optional video rush format flags property is not present for this Quantel material package.");

        return videoRushFormatFlags;
    }

	@MediaPropertySetter("Video rush format flags")
    public void setVideoRushFormatFlags(
        @UInt32 Integer videoRushFormatFlags) {

        if (videoRushFormatFlags == null) { this.videoRushFormatFlags = null; return; }

        if (videoRushFormatFlags < 0)
            throw new IllegalArgumentException("Cannot set property video rush format flags to a negative value.");

        this.videoRushFormatFlags = videoRushFormatFlags;
    }			
	
    @MediaProperty(uuid1 = 0xcbf99dbd, uuid2 = (short) 0xd7dc, uuid3 = (short) 0x4ca6,
        uuid4 = { (byte) 0xb3, (byte) 0xd5, (byte) 0x6f, (byte) 0x4c, (byte) 0xb0, (byte) 0xa5, (byte) 0xb8, (byte) 0x02 },
        definedName = "Video rush scan type",
        symbol = "Video_rush_scan_type",
        aliases = { "Video_rush_scan_type" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getVideoRushScanType() {

        if (videoRushScanType == null)
            throw new PropertyNotPresentException("The optional video rush scan type property is not present for this Quantel material package.");

        return videoRushScanType;
    }
		
	@MediaPropertySetter("Video rush scan type")
    public void setVideoRushScanType(
        @Int32 Integer videoRushScanType) {

        this.videoRushScanType = videoRushScanType;
    }

    @MediaProperty(uuid1 = 0xcbf92aca, uuid2 = (short) 0x84fa, uuid3 = (short) 0x4bc1,
        uuid4 = { (byte) 0x94, (byte) 0xc6, (byte) 0x29, (byte) 0x30, (byte) 0x4b, (byte) 0x09, (byte) 0xa5, (byte) 0xbd },
        definedName = "Rush file is multiple frames",
        symbol = "Rush_file_is_multiple_frames",
        aliases = { "Rush_file_is_multiple_frames" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getRushFileIsMultipleFrames() {

        if (rushFileIsMultipleFrames == null)
            throw new PropertyNotPresentException("The optional rush file is multiple frames property is not present for this Quantel material package.");

        return rushFileIsMultipleFrames;
    }
		
	@MediaPropertySetter("Rush file is multiple frames")
    public void setRushFileIsMultipleFrames(
        @Int32 Integer rushFileIsMultipleFrames) {

        this.rushFileIsMultipleFrames = rushFileIsMultipleFrames;
    }
	
    @MediaProperty(uuid1 = 0xb3eb74ce, uuid2 = (short) 0xd1de, uuid3 = (short) 0x4113,
        uuid4 = { (byte) 0x85, (byte) 0xd9, (byte) 0xfd, (byte) 0x19, (byte) 0xdd, (byte) 0x6c, (byte) 0x6a, (byte) 0x3b },
        definedName = "Audio rush frame rate denominator",
        symbol = "Audio_rush_frame_rate_denominator",
        aliases = { "Audio_rush_frame_rate_denominator" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getAudioRushFrameRateDenominator() {

        if (audioRushFrameRateDenominator == null)
            throw new PropertyNotPresentException("The optional audio rush frame rate denominator property is not present for this Quantel material package.");

        return audioRushFrameRateDenominator;
    }
		
	@MediaPropertySetter("Audio rush frame rate denominator")
    public void setAudioRushFrameRateDenominator(
        @Int32 Integer audioRushFrameRateDenominator) {

        this.audioRushFrameRateDenominator = audioRushFrameRateDenominator;
    }	
	
    @MediaProperty(uuid1 = 0x4d5158d0, uuid2 = (short) 0xe6f7, uuid3 = (short) 0x4970,
        uuid4 = { (byte) 0xbc, (byte) 0x41, (byte) 0x67, (byte) 0x7d, (byte) 0xc6, (byte) 0x15, (byte) 0x03, (byte) 0x97 },
        definedName = "Video rush planes",
        symbol = "Video_rush_planes",
        aliases = { "Video_rush_planes" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushPlanes() {

        if (videoRushPlanes == null)
            throw new PropertyNotPresentException("The optional video rush planes property is not present for this Quantel material package.");

        return videoRushPlanes;
    }

	@MediaPropertySetter("Video rush planes")
    public void setVideoRushPlanes(
        @UInt32 Integer videoRushPlanes) {

        if (videoRushPlanes == null) { this.videoRushPlanes = null; return; }

        if (videoRushPlanes < 0)
            throw new IllegalArgumentException("Cannot set property video rush planes to a negative value.");

        this.videoRushPlanes = videoRushPlanes;
    }				
	
    @MediaProperty(uuid1 = 0xb08e13d4, uuid2 = (short) 0xb879, uuid3 = (short) 0x4d53,
        uuid4 = { (byte) 0x8d, (byte) 0xdb, (byte) 0x34, (byte) 0xbd, (byte) 0x76, (byte) 0x4d, (byte) 0xd2, (byte) 0xb4 },
        definedName = "Rush ingest tc",
        symbol = "Rush_ingest_tc",
        aliases = { "Rush_ingest_tc" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getRushIngestTC() {

        if (rushIngestTC == null)
            throw new PropertyNotPresentException("The optional rush ingest TC property is not present for this Quantel material package.");

        return rushIngestTC;
    }
		
	@MediaPropertySetter("Rush ingest tc")
    public void setRushIngestTC(
        @Int32 Integer audioRushFrameRateDenominator) {

        this.audioRushFrameRateDenominator = audioRushFrameRateDenominator;
    }	

    @MediaProperty(uuid1 = 0x0ea150e6, uuid2 = (short) 0x44e3, uuid3 = (short) 0x4d45,
        uuid4 = { (byte) 0x95, (byte) 0x4d, (byte) 0xbe, (byte) 0xa4, (byte) 0xd3, (byte) 0x78, (byte) 0x6b, (byte) 0x98 },
        definedName = "Video rush white level",
        symbol = "Video_rush_white_level",
        aliases = { "Video_rush_white_level" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushWhiteLevel() {

        if (videoRushWhiteLevel == null)
            throw new PropertyNotPresentException("The optional video rush white level property is not present for this Quantel material package.");

        return videoRushWhiteLevel;
    }

	@MediaPropertySetter("Video rush white level")
    public void setVideoRushWhiteLevel(
        @UInt32 Integer videoRushWhiteLevel) {

        if (videoRushWhiteLevel == null) { this.videoRushWhiteLevel = null; return; }

        if (videoRushWhiteLevel < 0)
            throw new IllegalArgumentException("Cannot set property video rush white level to a negative value.");

        this.videoRushWhiteLevel = videoRushWhiteLevel;
    }				
		
    @MediaProperty(uuid1 = 0x08f3cef1, uuid2 = (short) 0x5faa, uuid3 = (short) 0x4595,
        uuid4 = { (byte) 0x94, (byte) 0x5c, (byte) 0xf4, (byte) 0xb1, (byte) 0x26, (byte) 0x66, (byte) 0xe4, (byte) 0x66 },
        definedName = "Video rush keycode type",
        symbol = "Video_rush_keycode_type",
        aliases = { "Video_rush_keycode_type" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushKeycodeType() {

        if (videoRushKeycodeType == null)
            throw new PropertyNotPresentException("The optional video rush keycode type property is not present for this Quantel material package.");

        return videoRushKeycodeType;
    }

	@MediaPropertySetter("Video rush keycode type")
    public void setVideoRushKeycodeType(
        @UInt32 Integer videoRushKeycodeType) {

        if (videoRushKeycodeType == null) { this.videoRushKeycodeType = null; return; }

        if (videoRushKeycodeType < 0)
            throw new IllegalArgumentException("Cannot set property video rush keycode type to a negative value.");

        this.videoRushKeycodeType = videoRushKeycodeType;
    }				

    @MediaProperty(uuid1 = 0xcf1e27f4, uuid2 = (short) 0xa9d9, uuid3 = (short) 0x4951,
        uuid4 = { (byte) 0xab, (byte) 0x2e, (byte) 0xa7, (byte) 0x32, (byte) 0x82, (byte) 0x15, (byte) 0xed, (byte) 0x60 },
        definedName = "Video rush x aspect",
        symbol = "Video_rush_x_aspect",
        aliases = { "Video_rush_x_aspect" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @UInt32 int getVideoRushXAspect() {

        if (videoRushXAspect == null)
            throw new PropertyNotPresentException("The optional video rush X aspect property is not present for this Quantel material package.");

        return videoRushXAspect;
    }

	@MediaPropertySetter("Video rush x aspect")
    public void setVideoRushXAspect(
        @UInt32 Integer videoRushXAspect) {

        if (videoRushXAspect == null) { this.videoRushXAspect = null; return; }

        if (videoRushXAspect < 0)
            throw new IllegalArgumentException("Cannot set property video rush X aspect to a negative value.");

        this.videoRushXAspect = videoRushXAspect;
    }
	
	// End - Quantel extensions

	public MaterialPackage clone() {
		
		return (MaterialPackage) super.clone();
	}
}


