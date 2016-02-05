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
 * $Log: CompositionPackageImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/24 14:01:40  vizigoth
 * Completed annotation and definition auto test generation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/11/18 10:43:02  vizigoth
 * Fixed import headings.
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
 * Revision 1.6  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.5  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.4  2008/01/27 11:14:40  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.3  2008/01/15 12:29:31  vizigoth
 * Updated due to refactoring of element names in FadeType enumeration.
 *
 * Revision 1.2  2008/01/14 21:09:45  vizigoth
 * Update to import due to DefaultFade changing packages.
 *
 * Revision 1.1  2007/11/13 22:09:52  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.enumeration.FadeType;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.CompositionPackage;
import tv.amwa.maj.model.OperationGroup;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.PackageIDImpl;
import tv.amwa.maj.record.impl.RationalImpl;
import tv.amwa.maj.record.impl.TimeStampImpl;
import tv.amwa.maj.union.DefaultFade;


/** 
 * <p>Implements a material object that describes how to combine content data elements 
 * into a sequence, how to modify content data elements, and how to synchronize content
 * data elements.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3500,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "CompositionPackage",
		  aliases = { "CompositionMob" },
		  description = "The CompositionPackage class specifies how to combine essence elements into a sequence, how to modify essence elements, and how to synchronize essence elements.",
		  symbol = "CompositionPackage")
public class CompositionPackageImpl
	extends PackageImpl
	implements 
		CompositionPackage,
		tv.amwa.maj.extensions.quantel.QCompositionPackage,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -4329286298868156956L;

	private long defaultFadeLength = 0l; // Optionality based on fade type
	private FadeType defaultFadeType = null;
	private Rational defaultFadeEditUnit = null;
	private PackageID compositionRendering = null;

    /** Default constructor is not public to avoid unset required fields. */
	public CompositionPackageImpl() { }

	/**
	 * <p>Creates and initializes a composition package, which specifies how to combine 
	 * essence elements into a sequence, how to modify essence elements, and how to synchronize 
	 * essence elements. The required properties of creation time and last modified time will 
	 * be set automatically to the time of calling this method. These timestamp values can be 
	 * set manually by calling 
	 * {@link PackageImpl#setCreationTime(tv.amwa.maj.record.TimeStamp) setCreateTime()} and
	 * and {@link PackageImpl#setPackageLastModified(tv.amwa.maj.record.TimeStamp) setModTime()}.</p>
	 * 
	 * @param packageID Unique material identifier for the new composition package.
	 * @param name Name of the new composition package.
	 * 
	 * @throws NullPointerException Unique material identifier argument is <code>null</code>.
	 */
	public CompositionPackageImpl(
			tv.amwa.maj.record.PackageID packageID,
			@AAFString String name) 
		throws NullPointerException {
		
		if (packageID == null)
			throw new NullPointerException("Cannot create a new composition package with a null package id.");
		if (name == null)
			throw new NullPointerException("Cannot create a new composition package with a null name.");
		
		setPackageID(packageID);
		setPackageName(name);
		
		setPackageLastModified(new TimeStampImpl());
		setCreationTime(new TimeStampImpl());
	}
	
	public DefaultFade getDefaultFade() 
		throws PropertyNotPresentException {
		
		if ((defaultFadeType == null) || (defaultFadeEditUnit == null)) 
			throw new PropertyNotPresentException("The optional default fade properties are not present in this composition package.");
		
		return new tv.amwa.maj.union.impl.DefaultFadeImpl(defaultFadeLength, defaultFadeType, defaultFadeEditUnit);
	}

	public void setDefaultFade(
			long fadeLength,
			FadeType fadeType,
			Rational fadeEditUnit)
		throws BadLengthException {

		if (fadeLength < 0l)
			throw new BadLengthException("Cannot set the length of a fade to a negative value.");
		
		this.defaultFadeLength = fadeLength;
		
		if ((fadeType == null) || (fadeEditUnit == null)) {
			this.defaultFadeType = null;
			this.defaultFadeEditUnit = null;
		}
		else {
			this.defaultFadeType = fadeType;
			this.defaultFadeEditUnit = fadeEditUnit.clone();
		}
	}
	
	public void setDefaultFade(
			DefaultFade defaultFade) {
		
		if (defaultFade == null) {
			this.defaultFadeType = null;
			this.defaultFadeEditUnit = null;
			return;
		}

		this.defaultFadeLength = defaultFade.getFadeLength();
		this.defaultFadeType = defaultFade.getFadeType();
		this.defaultFadeEditUnit = defaultFade.getFadeEditUnit().clone();
	}

	@MediaProperty(uuid1 = 0x07020201, uuid2 = (short) 0x0105, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "DefaultFadeLength",
			aliases = { "DefFadeLength" },
			typeName = "LengthType",
			optional = true,
			uniqueIdentifier = false,
			pid =0x4501,
			symbol = "DefaultFadeLength")
	public long getDefaultFadeLength() 
		throws PropertyNotPresentException {
		
		if ((defaultFadeType == null) || (defaultFadeEditUnit == null)) 
			throw new PropertyNotPresentException("The optional default fade properties are not present in this composition package.");

		return defaultFadeLength;
	}
	
	@MediaPropertySetter("DefaultFadeLength")
	public void setDefaultFadeLength(
			Long defaultFadeLength) {
		
		if (defaultFadeLength == null) {
			defaultFadeType = null;
			return;
		}
		
		this.defaultFadeLength = defaultFadeLength;
	}

	@MediaProperty(uuid1 = 0x05300201, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "DefaultFadeType",
			aliases = { "DefFadeType" },
			typeName = "FadeType", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4502,
			symbol = "DefaultFadeType")
	public FadeType getDefaultFadeType() 
		throws PropertyNotPresentException {
		
		if ((defaultFadeType == null) || (defaultFadeEditUnit == null)) 
			throw new PropertyNotPresentException("The optional default fade properties are not present in this composition package.");

		return defaultFadeType;
	}
	
	@MediaPropertySetter("DefaultFadeType")
	public void setDefaultFadeType(
			FadeType defaultFadeType) {
		
		this.defaultFadeType = defaultFadeType;
	}

	@MediaProperty(uuid1 = 0x05300403, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "DefaultFadeEditUnit",
			aliases = { "DefFadeEditUnit" },
			typeName = "Rational",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4503,
			symbol = "DefaultFadeEditUnit")
	public Rational getDefaultFadeEditUnit() 
		throws PropertyNotPresentException {
		
		if ((defaultFadeType == null) || (defaultFadeEditUnit == null)) 
			throw new PropertyNotPresentException("The optional default fade properties are not present in this composition package.");
		return defaultFadeEditUnit.clone();
	}
	
	@MediaPropertySetter("DefaultFadeEditUnit")
	public void setDefaultFadeEditUnit(
			Rational defaultFadeEditUnit) {
		
		if (defaultFadeEditUnit == null) 
			this.defaultFadeEditUnit = null;
		else
			this.defaultFadeEditUnit = defaultFadeEditUnit.clone();
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x010a, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x08},
			definedName = "CompositionRendering",
			aliases = { "Rendering", "CompositionMobRendering" },
			typeName = "PackageIDType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4504,
			symbol = "CompositionRendering")
	public PackageID getCompositionRendering() 
		throws PropertyNotPresentException {

		if (compositionRendering == null)
			throw new PropertyNotPresentException("The optional rendering property is not present in this composition package.");
		
		return compositionRendering.clone();
	}

	@MediaPropertySetter("CompositionRendering")
	public void setCompositionRendering(
			PackageID packageID) {

		if (packageID == null)
			this.compositionRendering = null;
		else
			this.compositionRendering = packageID.clone();

	}

	// Begin - Quantel extensions
	
	private Integer clipFPS = null;
	private String clipType = null;
	private String clipProject = null;
	private Integer clipModifiedYears = null;
	private Integer clipModifiedMonths = null;
	private Integer clipModifiedDays = null;
	private Integer clipModifiedHours = null;
	private Integer clipModifiedMinutes = null;
	private Integer clipModifiedSeconds = null;
	private Integer clipModifiedMilliseconds = null;
	private Integer clipAudioTracks = null;
	private Integer archiveCompositionType = null;
	private Segment clipKeyRenderSequence = null;
	private List<OperationGroup> clipEffects = Collections.synchronizedList(new Vector<OperationGroup>());
	private Integer clipDropframe = null;
	private Integer clipDuration = null;
	private Integer clipWidth = null;
	private Integer clipHeight = null;
	private Integer clipVideoTracks = null;
	private Stream clipBlob = null;
	private PropertyValue clipColourGreen = null;
	private PropertyValue clipColourRed = null;
	private PropertyValue clipColourBlue = null;
	private Integer clipArchivedYears = null;
	private Integer clipArchivedMonths = null;
	private Integer clipArchivedDays = null;
	private Integer clipArchivedHours = null;
	private Integer clipArchivedMinutes = null;
	private Integer clipArchivedSeconds = null;
	private Integer clipArchivedMilliseconds = null;
	private Integer clipInPoint = null;
	private Integer clipOutPoint = null;
	private Segment clipRenderSequence = null;
	private Integer clipDestTC = null;
	private Integer archiveSetting = null;
	private Integer clipFlag1001 = null;
	private Integer archiveComposition = null;
	private String clipCategory = null;
	private String clipOwner = null;
	
    @MediaProperty(uuid1 = 0xa1bb5c02, uuid2 = (short) 0x0eac, uuid3 = (short) 0x436f,
        uuid4 = { (byte) 0x96, (byte) 0xdf, (byte) 0xf9, (byte) 0x3b, (byte) 0x15, (byte) 0x3c, (byte) 0xf0, (byte) 0x45 },
       definedName = "Clip fps",
        symbol = "Clip_fps",
        aliases = { "Clip_fps" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipFPS() {

        if (clipFPS == null)
            throw new PropertyNotPresentException("The optional clip FPS property is not present for this Quantel composition package.");

        return clipFPS;
    }
	
	@MediaPropertySetter("Clip fps")
    public void setClipFPS(
        @Int32 Integer clipFPS) {

        this.clipFPS = clipFPS;
    }
	
    @MediaProperty(uuid1 = 0xad2eab05, uuid2 = (short) 0xca5f, uuid3 = (short) 0x4af1,
        uuid4 = { (byte) 0x94, (byte) 0x26, (byte) 0xf2, (byte) 0x37, (byte) 0x6e, (byte) 0xd0, (byte) 0xce, (byte) 0x3d },
        definedName = "Clip type",
        symbol = "Clip_type",
        aliases = { "Clip_type" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public  String getClipType() {

        if (clipType == null)
            throw new PropertyNotPresentException("The optional clip type property is not present for this Quantel composition package.");

        return clipType;
    }
	
	@MediaPropertySetter("Clip type")
    public void setClipType(
         String clipType) {

        this.clipType = clipType;
    }
	
    @MediaProperty(uuid1 = 0xa81adc0c, uuid2 = (short) 0x2914, uuid3 = (short) 0x4715,
        uuid4 = { (byte) 0x95, (byte) 0xaf, (byte) 0x1d, (byte) 0xc1, (byte) 0x0b, (byte) 0xe3, (byte) 0x59, (byte) 0x66 },
        definedName = "Clip project",
        symbol = "Clip_project",
        aliases = { "Clip_project" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public  String getClipProject() {

        if (clipProject == null)
            throw new PropertyNotPresentException("The optional clip project property is not present for this Quantel composition package.");

        return clipProject;
    }
	
	@MediaPropertySetter("Clip project")
    public void setClipProject(
         String clipProject) {

        this.clipProject = clipProject;
    }
	
    @MediaProperty(uuid1 = 0x225b4a14, uuid2 = (short) 0x5f24, uuid3 = (short) 0x48c4,
        uuid4 = { (byte) 0xbb, (byte) 0xe6, (byte) 0xa1, (byte) 0x1f, (byte) 0x00, (byte) 0x11, (byte) 0x91, (byte) 0x89 },
        definedName = "Clip modified minutes",
        symbol = "Clip_modified_minutes",
        aliases = { "Clip_modified_minutes" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipModifiedMinutes() {

        if (clipModifiedMinutes == null)
            throw new PropertyNotPresentException("The optional clip modified minutes property is not present for this Quantel composition package.");

        return clipModifiedMinutes;
    }
	
	@MediaPropertySetter("Clip modified minutes")
    public void setClipModifiedMinutes(
        @Int32 Integer clipModifiedMinutes) {

        this.clipModifiedMinutes = clipModifiedMinutes;
    }
	
    @MediaProperty(uuid1 = 0x7989621f, uuid2 = (short) 0x6a2e, uuid3 = (short) 0x46a1,
        uuid4 = { (byte) 0xa3, (byte) 0x65, (byte) 0xa2, (byte) 0xae, (byte) 0x56, (byte) 0x4d, (byte) 0x0c, (byte) 0x0e },
        definedName = "Clip modified months",
        symbol = "Clip_modified_months",
        aliases = { "Clip_modified_months" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipModifiedMonths() {

        if (clipModifiedMonths == null)
            throw new PropertyNotPresentException("The optional clip modified months property is not present for this Quantel composition package.");

        return clipModifiedMonths;
    }
	
	@MediaPropertySetter("Clip modified months")
    public void setClipModifiedMonths(
        @Int32 Integer clipModifiedMonths) {

        this.clipModifiedMonths = clipModifiedMonths;
    }
	
    @MediaProperty(uuid1 = 0x7e984a30, uuid2 = (short) 0xb6e2, uuid3 = (short) 0x4d36,
        uuid4 = { (byte) 0xb8, (byte) 0x34, (byte) 0x53, (byte) 0x68, (byte) 0x77, (byte) 0x74, (byte) 0xa6, (byte) 0xe0 },
        definedName = "Clip audio tracks",
        symbol = "Clip_audio_tracks",
        aliases = { "Clip_audio_tracks" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipAudioTracks() {

        if (clipAudioTracks == null)
            throw new PropertyNotPresentException("The optional clip audio tracks property is not present for this Quantel composition package.");

        return clipAudioTracks;
    }
	
	@MediaPropertySetter("Clip audio tracks")
    public void setClipAudioTracks(
        @Int32 Integer clipAudioTracks) {

        this.clipAudioTracks = clipAudioTracks;
    }
	
    @MediaProperty(uuid1 = 0x5d8a9e31, uuid2 = (short) 0xd7e4, uuid3 = (short) 0x4c2d,
        uuid4 = { (byte) 0x93, (byte) 0xbd, (byte) 0x13, (byte) 0x0f, (byte) 0x82, (byte) 0x0e, (byte) 0x99, (byte) 0x2c },
        definedName = "Archive composition type",
        symbol = "Archive_composition_type",
        aliases = { "Archive_composition_type" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getArchiveCompositionType() {

        if (archiveCompositionType == null)
            throw new PropertyNotPresentException("The optional archive composition type property is not present for this Quantel composition package.");

        return archiveCompositionType;
    }
	
	@MediaPropertySetter("Archive composition type")
    public void setArchiveCompositionType(
        @Int32 Integer archiveCompositionType) {

        this.archiveCompositionType = archiveCompositionType;
    }
	
    @MediaProperty(uuid1 = 0xa98c9c39, uuid2 = (short) 0x4207, uuid3 = (short) 0x4b0c,
        uuid4 = { (byte) 0xb5, (byte) 0x80, (byte) 0x4d, (byte) 0x4e, (byte) 0x6c, (byte) 0xdc, (byte) 0xff, (byte) 0xf5 },
        definedName = "Clip key render sequence",
        symbol = "Clip_key_render_sequence",
        aliases = { "Clip_key_render_sequence" },
        typeName = "SegmentStrongReference",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public  Segment getClipKeyRenderSequence() {

        if (clipKeyRenderSequence == null)
            throw new PropertyNotPresentException("The optional clip key render sequence property is not present for this Quantel composition package.");

        return clipKeyRenderSequence;
    }
	
	@MediaPropertySetter("Clip key render sequence")
    public void setClipKeyRenderSequence(
         Segment clipKeyRenderSequence) {

        this.clipKeyRenderSequence = clipKeyRenderSequence;
    }
	
    @MediaProperty(uuid1 = 0xc33bb43d, uuid2 = (short) 0x12bf, uuid3 = (short) 0x4bc6,
        uuid4 = { (byte) 0xbc, (byte) 0xed, (byte) 0x40, (byte) 0x47, (byte) 0xf6, (byte) 0x1a, (byte) 0x0e, (byte) 0x89 },
        definedName = "Clip effects",
        symbol = "Clip_effects",
        aliases = { "Clip_effects" },
        typeName = "OperationGroupStrongReferenceVariableArray",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public List<OperationGroup> getClipEffects()
		throws PropertyNotPresentException {
    	
    	if (clipEffects.size() == 0)
    		throw new PropertyNotPresentException("The optional clip effects property is not present for this Quantel composition package.");
    	
    	return StrongReferenceVector.getOptionalList(clipEffects);
    }
	
    @MediaListAppend("Clip effects")
	public void appendClipEffect(
			OperationGroup clipEffect) 
    	throws NullPointerException {
		
		if (clipEffect == null)
			throw new NullPointerException("Cannot append a null clip effect to the list of clip effects of this Quantel composition package.");
		
		StrongReferenceVector.append(clipEffects, clipEffect);
	}
	
    @MediaListGetAt("Clip effects")
	public OperationGroup getClipEffectAt(
			int index)
		throws IndexOutOfBoundsException {
    	
    	return StrongReferenceVector.getAt(clipEffects, index);
    }
	
    @MediaPropertyClear("Clip effects")
	public void clearClipEffects() {
    	
    	clipEffects.clear();
    }
	
    @MediaPropertyCount("Clip effects")
	public int countClipEffects() {
    	
    	return clipEffects.size();
    }
	
    @MediaListPrepend("Clip effects")
	public void prependClipEffect(
			OperationGroup clipEffect) {

		if (clipEffect == null)
			throw new NullPointerException("Cannot append a null clip effect to the list of clip effects of this Quantel composition package.");

		StrongReferenceVector.prepend(clipEffects, clipEffect);
    }
	
    @MediaListRemoveAt("ClipEffects")
	public void removeClipEffectAt(
			int index)
		throws IndexOutOfBoundsException {
    	
    	StrongReferenceVector.remove(clipEffects, index);
    }
	
    @MediaProperty(uuid1 = 0x0c77803f, uuid2 = (short) 0xce8d, uuid3 = (short) 0x43b1,
        uuid4 = { (byte) 0x99, (byte) 0xf7, (byte) 0x15, (byte) 0x6e, (byte) 0x91, (byte) 0x03, (byte) 0x79, (byte) 0xbd },
        definedName = "Clip modified hours",
        symbol = "Clip_modified_hours",
        aliases = { "Clip_modified_hours" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipModifiedHours() {

        if (clipModifiedHours == null)
            throw new PropertyNotPresentException("The optional clip modified hours property is not present for this Quantel composition package.");

        return clipModifiedHours;
    }
	
	@MediaPropertySetter("Clip modified hours")
    public void setClipModifiedHours(
        @Int32 Integer clipModifiedHours) {

        this.clipModifiedHours = clipModifiedHours;
    }
	
    @MediaProperty(uuid1 = 0xd1970f4e, uuid2 = (short) 0x5412, uuid3 = (short) 0x4826,
        uuid4 = { (byte) 0xa8, (byte) 0x9a, (byte) 0x35, (byte) 0x03, (byte) 0xbb, (byte) 0x1a, (byte) 0x75, (byte) 0x0d },
        definedName = "Clip dropframe",
        symbol = "Clip_dropframe",
        aliases = { "Clip_dropframe" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipDropframe() {

        if (clipDropframe == null)
            throw new PropertyNotPresentException("The optional clip dropframe property is not present for this Quantel composition package.");

        return clipDropframe;
    }
	
	@MediaPropertySetter("Clip dropframe")
    public void setClipDropframe(
        @Int32 Integer clipDropframe) {

        this.clipDropframe = clipDropframe;
    }
	
    @MediaProperty(uuid1 = 0xdb695c54, uuid2 = (short) 0x3606, uuid3 = (short) 0x48eb,
        uuid4 = { (byte) 0x89, (byte) 0x24, (byte) 0x4d, (byte) 0x82, (byte) 0x15, (byte) 0xe1, (byte) 0x54, (byte) 0xb1 },
        definedName = "Clip duration",
        symbol = "Clip_duration",
        aliases = { "Clip_duration" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipDuration() {

        if (clipDuration == null)
            throw new PropertyNotPresentException("The optional clip duration property is not present for this Quantel composition package.");

        return clipDuration;
    }
	
	@MediaPropertySetter("Clip durationClip duration")
    public void setClipDuration(
        @Int32 Integer clipDuration) {

        this.clipDuration = clipDuration;
    }
	
    @MediaProperty(uuid1 = 0x8406745b, uuid2 = (short) 0x3ac0, uuid3 = (short) 0x43e2,
        uuid4 = { (byte) 0x94, (byte) 0xf3, (byte) 0x5f, (byte) 0xba, (byte) 0xa2, (byte) 0x2d, (byte) 0x08, (byte) 0x21 },
        definedName = "Clip modified years",
        symbol = "Clip_modified_years",
        aliases = { "Clip_modified_years" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipModifiedYears() {

        if (clipModifiedYears == null)
            throw new PropertyNotPresentException("The optional clip modified years property is not present for this Quantel composition package.");

        return clipModifiedYears;
    }
	
	@MediaPropertySetter("Clip modified years")
    public void setClipModifiedYears(
        @Int32 Integer clipModifiedYears) {

        this.clipModifiedYears = clipModifiedYears;
    }
	
    @MediaProperty(uuid1 = 0xbe312d5c, uuid2 = (short) 0x7059, uuid3 = (short) 0x48c0,
        uuid4 = { (byte) 0x8d, (byte) 0x2b, (byte) 0x0d, (byte) 0xd0, (byte) 0x5e, (byte) 0x82, (byte) 0x79, (byte) 0xc3 },
        definedName = "Clip modified milliseconds",
        symbol = "Clip_modified_milliseconds",
        aliases = { "Clip_modified_milliseconds" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipModifiedMilliseconds() {

        if (clipModifiedMilliseconds == null)
            throw new PropertyNotPresentException("The optional clip modified milliseconds property is not present for this Quantel composition package.");

        return clipModifiedMilliseconds;
    }
	
	@MediaPropertySetter("Clip modified milliseconds")
    public void setClipModifiedMillseconds(
        @Int32 Integer clipModifiedMilliseconds) {

        this.clipModifiedMilliseconds = clipModifiedMilliseconds;
    }
	
    @MediaProperty(uuid1 = 0x3db3ec65, uuid2 = (short) 0x3070, uuid3 = (short) 0x4466,
        uuid4 = { (byte) 0x9a, (byte) 0x7f, (byte) 0x8e, (byte) 0x17, (byte) 0x45, (byte) 0xaa, (byte) 0x4a, (byte) 0x6a },
        definedName = "Clip width",
        symbol = "Clip_width",
        aliases = { "Clip_width" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipWidth() {

        if (clipWidth == null)
            throw new PropertyNotPresentException("The optional clip width property is not present for this Quantel composition package.");

        return clipWidth;
    }
	
	@MediaPropertySetter("Clip width")
    public void setClipWidth(
        @Int32 Integer clipWidth) {

        this.clipWidth = clipWidth;
    }
	
    @MediaProperty(uuid1 = 0x1b749f66, uuid2 = (short) 0x5af2, uuid3 = (short) 0x46f0,
        uuid4 = { (byte) 0x82, (byte) 0xd4, (byte) 0x07, (byte) 0xe0, (byte) 0x9f, (byte) 0x38, (byte) 0x4f, (byte) 0x8f },
        definedName = "Clip video tracks",
        symbol = "Clip_video_tracks",
        aliases = { "Clip_video_tracks" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipVideoTracks() {

        if (clipVideoTracks == null)
            throw new PropertyNotPresentException("The optional clip video tracks property is not present for this Quantel composition package.");

        return clipVideoTracks;
    }
	
	@MediaPropertySetter("Clip video tracks")
    public void setClipVideoTracks(
        @Int32 Integer clipVideoTracks) {

        this.clipVideoTracks = clipVideoTracks;
    }
	
    @MediaProperty(uuid1 = 0x7620ea68, uuid2 = (short) 0xeb15, uuid3 = (short) 0x405e,
        uuid4 = { (byte) 0x8e, (byte) 0xbb, (byte) 0xe3, (byte) 0x9c, (byte) 0x88, (byte) 0xe1, (byte) 0xa5, (byte) 0xe6 },
        definedName = "Clip blob",
        symbol = "Clip_blob",
        aliases = { "Clip_blob" },
        typeName = "Stream",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public  Stream getClipBlob() {

        if (clipBlob == null)
            throw new PropertyNotPresentException("The optional clip blob property is not present for this Quantel composition package.");

        return clipBlob;
    }
	
	@MediaPropertySetter("Clip blob")
    public void setClipBlob(
         Stream clipBlob) {

        this.clipBlob = clipBlob;
    }
	
    @MediaProperty(uuid1 = 0x61a06070, uuid2 = (short) 0x67de, uuid3 = (short) 0x468f,
        uuid4 = { (byte) 0x95, (byte) 0xbd, (byte) 0xcb, (byte) 0xec, (byte) 0xf9, (byte) 0xb1, (byte) 0x65, (byte) 0xdd },
        definedName = "Clip colour green",
        symbol = "Clip_colour_green",
        aliases = { "Clip_colour_green" },
        typeName = "Indirect",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public PropertyValue getClipColourGreen() {

        if (clipColourGreen == null)
            throw new PropertyNotPresentException("The optional clip colour green property is not present for this Quantel composition package.");

        return clipColourGreen;
    }
	
	@MediaPropertySetter("Clip colour green")
    public void setClipColourGreen(
         PropertyValue clipColourGreen) {

        this.clipColourGreen = clipColourGreen;
    }
	
    @MediaProperty(uuid1 = 0x1b322074, uuid2 = (short) 0xbbdc, uuid3 = (short) 0x43fc,
        uuid4 = { (byte) 0x99, (byte) 0x05, (byte) 0xab, (byte) 0x80, (byte) 0xc6, (byte) 0xba, (byte) 0x01, (byte) 0xad },
        definedName = "Clip archived years",
        symbol = "Clip_archived_years",
        aliases = { "Clip_archived_years" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipArchivedYears() {

        if (clipArchivedYears == null)
            throw new PropertyNotPresentException("The optional clip archived years property is not present for this Quantel composition package.");

        return clipArchivedYears;
    }
	
	@MediaPropertySetter("Clip archived years")
    public void setClipArchivedYears(
        @Int32 Integer clipArchivedYears) {

        this.clipArchivedYears = clipArchivedYears;
    }
	
    @MediaProperty(uuid1 = 0x09820679, uuid2 = (short) 0xf971, uuid3 = (short) 0x4743,
        uuid4 = { (byte) 0xb1, (byte) 0xb7, (byte) 0xa7, (byte) 0xe6, (byte) 0xc2, (byte) 0x99, (byte) 0xd1, (byte) 0x56 },
        definedName = "Clip height",
        symbol = "Clip_height",
        aliases = { "Clip_height" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipHeight() {

        if (clipHeight == null)
            throw new PropertyNotPresentException("The optional clip height property is not present for this Quantel composition package.");

        return clipHeight;
    }
	
	@MediaPropertySetter("Clip height")
    public void setClipHeight(
        @Int32 Integer clipHeight) {

        this.clipHeight = clipHeight;
    }
	
    @MediaProperty(uuid1 = 0x97770688, uuid2 = (short) 0xcfe0, uuid3 = (short) 0x47a1,
        uuid4 = { (byte) 0xaf, (byte) 0xc1, (byte) 0x7a, (byte) 0x38, (byte) 0x45, (byte) 0x46, (byte) 0x5e, (byte) 0xb4 },
        definedName = "Clip in point",
        symbol = "Clip_in_point",
        aliases = { "Clip_in_point" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipInPoint() {

        if (clipInPoint == null)
            throw new PropertyNotPresentException("The optional clip in point property is not present for this Quantel composition package.");

        return clipInPoint;
    }
	
	@MediaPropertySetter("Clip in point")
    public void setClipInPoint(
        @Int32 Integer clipInPoint) {

        this.clipInPoint = clipInPoint;
    }
	
    @MediaProperty(uuid1 = 0x4b54d899, uuid2 = (short) 0x0120, uuid3 = (short) 0x4b88,
        uuid4 = { (byte) 0x8f, (byte) 0xed, (byte) 0x1f, (byte) 0x5c, (byte) 0x38, (byte) 0x30, (byte) 0x79, (byte) 0x34 },
        definedName = "Clip archived hours",
        symbol = "Clip_archived_hours",
        aliases = { "Clip_archived_hours" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipArchivedHours() {

        if (clipArchivedHours == null)
            throw new PropertyNotPresentException("The optional clip archived hours property is not present for this Quantel composition package.");

        return clipArchivedHours;
    }
	
	@MediaPropertySetter("Clip archived hours")
    public void setClipArchivedHours(
        @Int32 Integer clipArchivedHours) {

        this.clipArchivedHours = clipArchivedHours;
    }
	
    @MediaProperty(uuid1 = 0x667f0aa1, uuid2 = (short) 0x32c7, uuid3 = (short) 0x4591,
        uuid4 = { (byte) 0xb0, (byte) 0xce, (byte) 0xdd, (byte) 0x57, (byte) 0xf2, (byte) 0x03, (byte) 0x48, (byte) 0x58 },
        definedName = "Clip archived months",
        symbol = "Clip_archived_months",
        aliases = { "Clip_archived_months" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipArchivedMonths() {

        if (clipArchivedMonths == null)
            throw new PropertyNotPresentException("The optional clipa archived months property is not present for this Quantel composition package.");

        return clipArchivedMonths;
    }
	
	@MediaPropertySetter("Clip archived months")
    public void setClipArchivedMonths(
        @Int32 Integer clipArchivedMonths) {

        this.clipArchivedMonths = clipArchivedMonths;
    }
	
    @MediaProperty(uuid1 = 0x9c8c50a5, uuid2 = (short) 0x4e74, uuid3 = (short) 0x483a,
        uuid4 = { (byte) 0x8d, (byte) 0x13, (byte) 0x1b, (byte) 0x74, (byte) 0x29, (byte) 0x42, (byte) 0x75, (byte) 0x3f },
        definedName = "Clip render sequence",
        symbol = "Clip_render_sequence",
        aliases = { "Clip_render_sequence" },
        typeName = "SegmentStrongReference",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public Segment getClipRenderSequence() {

        if (clipRenderSequence == null)
            throw new PropertyNotPresentException("The optional clip render sequence property is not present for this Quantel composition package.");

        return clipRenderSequence;
    }
	
	@MediaPropertySetter("Clip render sequence")
    public void setClipRenderSequence(
         Segment clipRenderSequence) {

        this.clipRenderSequence = clipRenderSequence;
    }
	
    @MediaProperty(uuid1 = 0x6ee0bbad, uuid2 = (short) 0x81db, uuid3 = (short) 0x497d,
        uuid4 = { (byte) 0xbf, (byte) 0x3b, (byte) 0x5a, (byte) 0x1d, (byte) 0xca, (byte) 0x9c, (byte) 0x65, (byte) 0xcb },
        definedName = "Clip archived minutes",
        symbol = "Clip_archived_minutes",
        aliases = { "Clip_archived_minutes" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipArchivedMinutes() {

        if (clipArchivedMinutes == null)
            throw new PropertyNotPresentException("The optional clip archived minutes property is not present for this Quantel composition package.");

        return clipArchivedMinutes;
    }
	
	@MediaPropertySetter("Clip archived minutes")
    public void setClipArchivedMinutes(
        @Int32 Integer clipArchivedMinutes) {

        this.clipArchivedMinutes = clipArchivedMinutes;
    }
	
    @MediaProperty(uuid1 = 0xe80a7faf, uuid2 = (short) 0xb4fe, uuid3 = (short) 0x463e,
        uuid4 = { (byte) 0x95, (byte) 0x07, (byte) 0x06, (byte) 0xc7, (byte) 0xbb, (byte) 0xb3, (byte) 0x19, (byte) 0x94 },
        definedName = "Clip colour red",
        symbol = "Clip_colour_red",
        aliases = { "Clip_colour_red" },
        typeName = "Indirect",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public  PropertyValue getClipColourRed() {

        if (clipColourRed == null)
            throw new PropertyNotPresentException("The optional clip colour red property is not present for this Quantel composition package.");

        return clipColourRed;
    }
	
	@MediaPropertySetter("Clip colour red")
    public void setClipColourRed(
         PropertyValue clipColourRed) {

        this.clipColourRed = clipColourRed;
    }
	
    @MediaProperty(uuid1 = 0x903b5bbd, uuid2 = (short) 0x9f4e, uuid3 = (short) 0x4e71,
        uuid4 = { (byte) 0x9a, (byte) 0xbf, (byte) 0x24, (byte) 0x9c, (byte) 0x69, (byte) 0x99, (byte) 0xd4, (byte) 0x75 },
        definedName = "Clip modified days",
        symbol = "Clip_modified_days",
        aliases = { "Clip_modified_days" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipModifiedDays() {

        if (clipModifiedDays == null)
            throw new PropertyNotPresentException("The optional clip modified days property is not present for this Quantel composition package.");

        return clipModifiedDays;
    }
	
	@MediaPropertySetter("Clip modified days")
    public void setClipModifiedDays(
        @Int32 Integer clipModifiedDays) {

        this.clipModifiedDays = clipModifiedDays;
    }
	
    @MediaProperty(uuid1 = 0x06d1dbc9, uuid2 = (short) 0x014a, uuid3 = (short) 0x4ee2,
        uuid4 = { (byte) 0x8f, (byte) 0x20, (byte) 0x4b, (byte) 0x31, (byte) 0x16, (byte) 0xbf, (byte) 0xab, (byte) 0x15 },
        definedName = "Clip modified seconds",
        symbol = "Clip_modified_seconds",
        aliases = { "Clip_modified_seconds" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipModifiedSeconds() {

        if (clipModifiedSeconds == null)
            throw new PropertyNotPresentException("The optional clip modified seconds property is not present for this Quantel composition package.");

        return clipModifiedSeconds;
    }
	
	@MediaPropertySetter("Clip modified seconds")
    public void setClipModifiedSeconds(
        @Int32 Integer clipModifiedSeconds) {

        this.clipModifiedSeconds = clipModifiedSeconds;
    }
	
    @MediaProperty(uuid1 = 0xbe93ebc9, uuid2 = (short) 0xc74f, uuid3 = (short) 0x46b0,
        uuid4 = { (byte) 0x9a, (byte) 0xe1, (byte) 0x5b, (byte) 0x3c, (byte) 0xba, (byte) 0xfd, (byte) 0x19, (byte) 0x67 },
        definedName = "Clip dest tc",
        symbol = "Clip_dest_tc",
        aliases = { "Clip_dest_tc" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipDestTC() {

        if (clipDestTC == null)
            throw new PropertyNotPresentException("The optional clip dest TC property is not present for this Quantel composition package.");

        return clipDestTC;
    }
	
	@MediaPropertySetter("Clip dest tc")
    public void setClipDestTC(
        @Int32 Integer clipDestTC) {

        this.clipDestTC = clipDestTC;
    }
	
    @MediaProperty(uuid1 = 0x270851cc, uuid2 = (short) 0xbd72, uuid3 = (short) 0x4205,
        uuid4 = { (byte) 0x87, (byte) 0x05, (byte) 0xd5, (byte) 0x8d, (byte) 0xeb, (byte) 0xbd, (byte) 0x19, (byte) 0x90 },
        definedName = "Clip archived days",
        symbol = "Clip_archived_days",
        aliases = { "Clip_archived_days" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipArchivedDays() {

        if (clipArchivedDays == null)
            throw new PropertyNotPresentException("The optional clip archived days property is not present for this Quantel composition package.");

        return clipArchivedDays;
    }
	
	@MediaPropertySetter("Clip archived days")
    public void setClipArchivedDays(
        @Int32 Integer clipArchivedDays) {

        this.clipArchivedDays = clipArchivedDays;
    }
	
    @MediaProperty(uuid1 = 0x281b0ddd, uuid2 = (short) 0xfebc, uuid3 = (short) 0x4a19,
        uuid4 = { (byte) 0xbf, (byte) 0xe6, (byte) 0x09, (byte) 0xdc, (byte) 0x47, (byte) 0x54, (byte) 0x34, (byte) 0x90 },
        definedName = "Clip colour blue",
        symbol = "Clip_colour_blue",
        aliases = { "Clip_colour_blue" },
        typeName = "Indirect",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public PropertyValue clipColourBlue()
		throws PropertyNotPresentException {
    	
    	if (clipColourBlue == null)
    		throw new PropertyNotPresentException("The optional clip colour blue property is not present for this Quantel composition package.");
    	
    	return clipColourBlue;
    }
	
	@MediaPropertySetter("Clip colour blue")
    public void setClipColourBlue(
         PropertyValue clipColourBlue) {

        this.clipColourBlue = clipColourBlue;
    }
	
    @MediaProperty(uuid1 = 0x152f1dde, uuid2 = (short) 0x9616, uuid3 = (short) 0x4d42,
        uuid4 = { (byte) 0xb4, (byte) 0x16, (byte) 0x45, (byte) 0x8e, (byte) 0xb4, (byte) 0x39, (byte) 0x66, (byte) 0x13 },
        definedName = "Archive setting",
        symbol = "Archive_setting",
        aliases = { "Archive_setting" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getArchiveSetting() {

        if (archiveSetting == null)
            throw new PropertyNotPresentException("The optional archive setting property is not present for this Quantel composition package.");

        return archiveSetting;
    }
	
	@MediaPropertySetter("Archive setting")
    public void setArchiveSetting(
        @Int32 Integer archiveSetting) {

        this.archiveSetting = archiveSetting;
    }
	
    @MediaProperty(uuid1 = 0xa80511e1, uuid2 = (short) 0xfe68, uuid3 = (short) 0x4438,
        uuid4 = { (byte) 0xbf, (byte) 0xe4, (byte) 0xea, (byte) 0x26, (byte) 0x78, (byte) 0xfe, (byte) 0x32, (byte) 0x3b },
        definedName = "Clip archived seconds",
        symbol = "Clip_archived_seconds",
        aliases = { "Clip_archived_seconds" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipArchivedSeconds() {

        if (clipArchivedSeconds == null)
            throw new PropertyNotPresentException("The optional clip archived seconds property is not present for this Quantel composition package.");

        return clipArchivedSeconds;
    }
	
	@MediaPropertySetter("Clip archived seconds")
    public void setClipArchivedSeconds(
        @Int32 Integer clipArchivedSeconds) {

        this.clipArchivedSeconds = clipArchivedSeconds;
    }
	
    @MediaProperty(uuid1 = 0xe66bcfe4, uuid2 = (short) 0xb1f1, uuid3 = (short) 0x4951,
        uuid4 = { (byte) 0x8e, (byte) 0xfb, (byte) 0x80, (byte) 0x73, (byte) 0x13, (byte) 0xe5, (byte) 0xfe, (byte) 0xbf },
        definedName = "Clip out point",
        symbol = "Clip_out_point",
        aliases = { "Clip_out_point" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipOutPoint() {

        if (clipOutPoint == null)
            throw new PropertyNotPresentException("The optional clip out point property is not present for this Quantel composition package.");

        return clipOutPoint;
    }
	
	@MediaPropertySetter("Clip out point")
    public void setClipOutPoint(
        @Int32 Integer clipOutPoint) {

        this.clipOutPoint = clipOutPoint;
    }
	
    @MediaProperty(uuid1 = 0x89f3b9e7, uuid2 = (short) 0x8f35, uuid3 = (short) 0x47a5,
        uuid4 = { (byte) 0x81, (byte) 0x9a, (byte) 0xd2, (byte) 0x12, (byte) 0x81, (byte) 0x40, (byte) 0xc2, (byte) 0xbb },
        definedName = "Clip flag1001",
        symbol = "Clip_flag1001",
        aliases = { "Clip_flag1001" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipFlag1001() {

        if (clipFlag1001 == null)
            throw new PropertyNotPresentException("The optional clip flag 1001 property is not present for this Quantel composition package.");

        return clipFlag1001;
    }
	
	@MediaPropertySetter("Clip flag1001")
    public void setClipFlag1001(
        @Int32 Integer clipFlag1001) {

        this.clipFlag1001 = clipFlag1001;
    }
	
    @MediaProperty(uuid1 = 0x25b65bf0, uuid2 = (short) 0x89ff, uuid3 = (short) 0x4a23,
        uuid4 = { (byte) 0xb5, (byte) 0xd8, (byte) 0x3c, (byte) 0x82, (byte) 0xe6, (byte) 0xfc, (byte) 0xc2, (byte) 0x3a },
        definedName = "Clip archived milliseconds",
        symbol = "Clip_archived_milliseconds",
        aliases = { "Clip_archived_milliseconds" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getClipArchivedMilliseconds() {

        if (clipArchivedMilliseconds == null)
            throw new PropertyNotPresentException("The optional clip archived milliseconds property is not present for this Quantel composition package.");

        return clipArchivedMilliseconds;
    }
	
	@MediaPropertySetter("Clip archived milliseconds")
    public void setClipArchivedMilliseconds(
        @Int32 Integer clipArchivedMilliseconds) {

        this.clipArchivedMilliseconds = clipArchivedMilliseconds;
    }
	
    @MediaProperty(uuid1 = 0x7343dff5, uuid2 = (short) 0x97c3, uuid3 = (short) 0x46e0,
        uuid4 = { (byte) 0x9c, (byte) 0xcb, (byte) 0x54, (byte) 0x37, (byte) 0x48, (byte) 0x0a, (byte) 0xae, (byte) 0x2a },
        definedName = "Archive composition",
        symbol = "Archive_composition",
        aliases = { "Archive_composition" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public @Int32 int getArchiveComposition() {

        if (archiveComposition == null)
            throw new PropertyNotPresentException("The optional archive composition property is not present for this Quantel composition package.");

        return archiveComposition;
    }
	
	@MediaPropertySetter("Archive composition")
    public void setArchiveComposition(
        @Int32 Integer archiveComposition) {

        this.archiveComposition = archiveComposition;
    }
	
    @MediaProperty(uuid1 = 0x2844a2f6, uuid2 = (short) 0xf65d, uuid3 = (short) 0x4e8d,
        uuid4 = { (byte) 0xb8, (byte) 0xdc, (byte) 0x71, (byte) 0xd7, (byte) 0x2d, (byte) 0x40, (byte) 0x8b, (byte) 0x75 },
        definedName = "Clip category",
        symbol = "Clip_category",
        aliases = { "Clip_category" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public  String getClipCategory() {

        if (clipCategory == null)
            throw new PropertyNotPresentException("The optional clip category property is not present for this Quantel composition package.");

        return clipCategory;
    }
	
	@MediaPropertySetter("Clip category")
    public void setClipCategory(
         String clipCategory) {

        this.clipCategory = clipCategory;
    }	

    @MediaProperty(uuid1 = 0x419da8fc, uuid2 = (short) 0x7b0f, uuid3 = (short) 0x4a8a,
        uuid4 = { (byte) 0xb7, (byte) 0x06, (byte) 0xca, (byte) 0xb4, (byte) 0x4c, (byte) 0x68, (byte) 0xbb, (byte) 0x3e },
        definedName = "Clip owner",
        symbol = "Clip_owner",
        aliases = { "Clip_owner" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)	
	public  String getClipOwner() {

        if (clipOwner == null)
            throw new PropertyNotPresentException("The optional clip owner property is not present for this Quantel composition package.");

        return clipOwner;
    }
	
	@MediaPropertySetter("Clip owner")
    public void setClipOwner(
         String clipOwner) {

        this.clipOwner = clipOwner;
    }
	
	// End - Quantel extensions
	
	public CompositionPackage clone() {
		
		return (CompositionPackage) super.clone();
	}
	
	// TODO Why are the names Def rather than Default ... the spec is ambiguous	

	public String getDefaultFadeEditUnitString() {
		
		return RationalImpl.toPersistentForm(defaultFadeEditUnit);
	}
	
	public void setDefaultFadeEditUnitString(
			String fadeEditUnit) {
		
		this.defaultFadeEditUnit = RationalImpl.fromPersistentForm(fadeEditUnit);
	}
	
	public String getCompositionRenderingString() {
		
		return PackageIDImpl.toPersistentForm(compositionRendering);
	}
	
	public void setCompositionRenderingString(
			String compositionRendering) {
		
		this.compositionRendering = PackageIDImpl.fromPersistentForm(compositionRendering);
	}
}
