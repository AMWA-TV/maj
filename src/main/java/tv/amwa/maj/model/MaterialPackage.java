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
 * $Log: MaterialPackage.java,v $
 * Revision 1.3  2011/10/05 17:14:27  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/02/28 12:50:32  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.3  2008/01/27 11:07:33  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/13 11:33:24  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.1  2007/11/13 22:08:25  vizigoth
 * Public release of MAJ API.
 */

/*
 * TODO decouple the logic described here from material package. The logic operates differently
 * in an JEE container than in a standalone application.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.CompressEnable;
import tv.amwa.maj.enumeration.CriteriaType;
import tv.amwa.maj.enumeration.MediaOpenMode;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.NotTapePackageException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.TrackExistsException;
import tv.amwa.maj.exception.TrackNotFoundException;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.NumTracks;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.union.MultiCreateItem;
import tv.amwa.maj.union.MultiXferItem;
import tv.amwa.maj.union.SourceReferenceValue;


/**
 * <p>Specifies a material object that provides access to {@linkplain SourcePackage source packages} 
 * and {@linkplain EssenceData essence data}.</p>
 * 
 * <p>This interface provides a number of methods that are useful for the management 
 * of tracks.</p>
 * 
 *
 */

public interface MaterialPackage 
	extends Package {

	/**
	 * <p>Returns the {@linkplain PackageMarker package marker} for this material package, which
	 * specifies an optional sub-section that may be played as an alternative to the 
	 * full material package. This is an optional property.</p>
	 * 
	 * @return Package marker for this material package.
	 * 
	 * @throws PropertyNotPresentException The optional package marker property is not present for
	 * this material package.
	 */
	public PackageMarker getPackageMarker()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the {@linkplain PackageMarker package marker} for this material package, which
	 * specifies an optional sub-section that may be played as an alternative to the 
	 * full material package. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param packageMarker Package marker for this material package.
	 */
	public void setPackageMarker(
			PackageMarker packageMarker);
	
	/**
	 * <p>Adds a {@linkplain Track track} to this material package that references the specified
	 * track in the specified {@link SourcePackage source package}. The new track in 
	 * the material package contains a {@link SourceClip source clip} that specifies
	 * the {@link SourcePackage source package} in its source reference properties.</p>
	 * 
	 * @param dataDefinition Essence kind of the new track.  Requires an essence kind valid 
	 * for time-varying essence, such as: "{@linkplain tv.amwa.maj.constant.DataDefinitionConstant#Picture Picture}", 
	 * "{@linkplain tv.amwa.maj.constant.DataDefinitionConstant#Sound Sound}".
	 * @param sourceTrackID Track id of the source track to be referenced from this
	 * material package.
	 * @param sourcePackage Source package containing the track to be referenced from this material package.
	 * @param materialTrackID Track id to be assigned to the new material track.
	 * @param trackName Name to assign to new track in the material package. Set this property to <code>null</code>
	 * to omit the optional {@linkplain Track#getTrackName() track name}.
	 * 
	 * @throws NullPointerException One or both of the data definition and/or source package 
	 * parameters is/are <code>null</code>.
	 * @throws InvalidDataDefinitionException The data kind of the source track 
	 * to be added to the material package does not match what is specified in 
	 * <code>dataDefinition</code>.
	 * @throws TrackNotFoundException The given source track was not found.
	 * @throws TrackExistsException The given material track id already identifies a track in
	 * this material package.
	 * @throws IllegalArgumentException The given material track id is is negative.
	 * 
	 * @see SourceClip
	 * @see TimelineTrack
	 * @see Package#getPackageTracks()
	 */
	public void addMaterialTrack(
		    DataDefinition dataDefinition,
		    @TrackID int sourceTrackID,
		    SourcePackage sourcePackage,
		    @TrackID int materialTrackID,
		    @AAFString String trackName) 
		throws NullPointerException,
			InvalidDataDefinitionException,
			TrackNotFoundException,
			TrackExistsException,
			IllegalArgumentException;

	/**
	 * <p>Finds the tape {@linkplain SourcePackage source package} associated with a track
	 * of this material package and returns the name of the tape, which is stored in the
	 * source package's name property. If the property name has not yet been set, a
	 * zero-length string will be returned.</p>
	 * 
	 * <p>A tape {@linkplain SourcePackage source package} is a source package described by
	 * a {@linkplain TapeDescriptor tape descriptor}.</p>
	 * 
	 * @param materialTrackId Track id of this material track to use to find the tape
	 * name.
	 * @return Tape name of the tape source package in the given track.
	 * 
	 * @throws TrackNotFoundException The specified material track was not found.
	 * @throws NotTapePackageException The specified material track does not contain 
	 * a tape package.
	 * 
	 * @see TapeDescriptor
	 * @see SourcePackage#getEssenceDescriptor()
	 */
	public @AAFString String getTapeName(
			@TrackID int materialTrackId) 
		throws TrackNotFoundException,
			NotTapePackageException;

	/**
	 * <p>This function returns the number of media representations
	 * available for the specified track of this material package. Each different representation will
	 * have a {@linkplain tv.amwa.maj.enumeration.CriteriaType media criteria} that can be used to 
	 * select it. Use {@link #getCriteriaSegment(int, CriteriaType)} to handle multiple 
	 * representations.</p>
	 * 
	 * @param trackID Track id the count of the number of representations are required for.
	 * @return Number of different media type representations in the track.
	 * 
	 * @throws TrackNotFoundException The material track specified by track is
	 * was not found for this material package.
	 */
	public @NumTracks int getNumberOfRepresentations(
			@TrackID int trackID) 
		throws TrackNotFoundException;

	/**
	 * <p>This method returns the indexed media representation for this material package,
	 * for this given track id and index through the different representations.  This 
	 * call is designed to work with {@link #getNumberOfRepresentations(int)} so that the
	 * user can iterate through all of the choices.  This method uses 
	 * an integer index, not an iterator.  The method takes an index between&nbsp;0 and 
	 * one less than the {@linkplain #getNumberOfRepresentations(int) number of representations}, 
	 * and returns the {@linkplain SourcePackage source package} at that representation index.</p>
	 * 
	 * @param trackID Track id of this material package to find a representation for.
	 * @param representationIndex Index of media representation in the given track.
	 * @return Matching source clip.
	 * 
	 * @throws TrackNotFoundException The material track specified by track id
	 * was not found in this material package.
	 * @throws IndexOutOfBoundsException No media representation is available at
	 * the specified index.
	 * 
	 * @see tv.amwa.maj.enumeration.CriteriaType
	 */
	public Segment getRepresentation(
			@TrackID int trackID,
			@UInt32 int representationIndex) 
		throws TrackNotFoundException,
			IndexOutOfBoundsException;

	/**
	 * <p>Returns the {@linkplain Segment segment} on the specified track of this
	 * material package that references the {@linkplain SourcePackage source package} that best meets the 
	 * specified criteria. This function will return a result whether multiple media
	 * representations exist or not, as one representation not matching a criteria is considered 
	 * better than no representation for any given media criteria.</p>
	 * 
	 * @param trackID Track id of a track of this material package to return a representation for.
	 * @param criteria Criteria for selecting one of the segments.
	 * @return Matching segment.
	 * 
	 * @throws TrackNotFoundException The material track specified by track id was not found.
	 * @throws NullPointerException One or both of the track id and/or criteria is/are <code>null</code>.
	 */
	public Segment getCriteriaSegment(
			@TrackID int trackID,
			CriteriaType criteria) 
		throws TrackNotFoundException,
			NullPointerException;

	// TODO Tidy comment later and add more exceptions.
	/**
	 * <p>Connects this material package with the physical Source package that
	 * describes the previous generation of essence, appending it to
	 * existing package data. If a physical Source package, such as a File
	 * Source package or tape Source package, references another physical
	 * Source package as its ancestor, with no pulldown, then this
	 * function makes the connection between the two.</p>
	 * 
	 * <p>Functionally, this is a helper method to create a track with an
	 * {@link SourceClip} referencing a particular piece of media.  This
	 * method takes many parameters because the components of an
	 * {@link SourceReferenceValue} have been broken out as separate parameters.</p>
	 * 
	 * <p>The ancestor of a {@link SourcePackage} with an {@link AAFFileDescriptor}
	 * is often a {@link TapeDescriptor} or <code>null</code>.</p>
	 * 
	 * @param editrate Edit rate of track to contain reference.
	 * @param trackID Track id of track to contain reference.
	 * @param essenceKind Data kind of track to contain reference.
	 * Requires a data kind valid for a essence stream.  Valid data 
	 * kinds are: "<code>Picture</code>", "<code>Sound</code>"
	 * @param ref Reference to a Physical Source package.
	 * @param srcRefLength Length of the Source Clip.
	 * 
	 * @throws NullPointerException One or more of the arguments is 
	 * null.
	 */
	public void appendPhysSourceRef (
			Rational editrate,
		    @TrackID int trackID,
		    DataDefinition essenceKind,
		    tv.amwa.maj.union.SourceReferenceValue ref,
		    @LengthType long srcRefLength) 
		throws NullPointerException;

	// TODO improve on this comment
	/**
	 * <p>Connects this Source package with the physical Source package that
	 * describes the previous generation of essence, replacing any
	 * existing package data.  If a physical Source package, such as a File
	 * Source package or tape Source package, references another physical
	 * Source package as its ancestor, with no pulldown, then this
	 * function makes the connection between the two.</p>
	 * 
	 * <p>Functionally, this is a helper method to create a track with a
	 * {@link SourceClip} referencing a particular piece of media. This
	 * function takes many parameters because the components of a
	 * {@link SourceReferenceValue} have been broken out as separate parameters.</p>
	 * 
	 * <p>The ancestor of an source package with an {@link AAFFileDescriptor} 
	 * is often an {@link TapeDescriptor} or <code>null</code>.
	 * 
	 * @param editrate
	 * @param track
	 * @param essenceKind
	 * @param ref
	 * @param srcRefLength
	 * 
	 * @throws NullPointerException
	 */
	public void newPhysSourceRef(
			Rational editrate,
			@TrackID int track,
			DataDefinition essenceKind,
			tv.amwa.maj.union.SourceReferenceValue ref,
			@LengthType long srcRefLength) 
		throws NullPointerException;

		// Urm ... this needs to throw some exceptions!

	/**
	 * <p>Creates a single channel stream of essence in the given track of this
	 * material package. Convenience functions exist to create audio or video essence, and a separate
	 * call ({@link #createMultiEssence(AUID, MultiCreateItem[], CompressEnable, Locator, AUID)}) 
	 * exists to create interleaved audio and video data.</p>
	 * 
	 * <p>The essence handle from this call can be used with
	 * {@link EssenceAccess#writeSamples(int, byte[])}, but 
	 * <strong>not</strong> with 
	 * {@link EssenceMultiAccess#writeMultiSamples(MultiXferItem[])}.</p>
	 * 
	 * <p>For video, the sample rate should be the edit rate of the file source package.
	 * For audio, the sample rate should be the actual number of samples per second.</p>
	 * 
	 * @param materialTrackID Material track id for the new essence.
	 * @param mediaKind Create essence of this type.
	 * @param codecID Create essence according to this codec.
	 * @param editRate Create essence with this edit rate.
	 * @param samplerate Create essence with this sample rate.
	 * @param compressEnable Is compression enabled?
	 * @param destination Create the essence at this location, or
	 * <code>null</code> for the default location.
	 * @param fileFormat Create essence to this file format.
	 * 
	 * @return Access to the created essence.
	 * 
	 * @throws NullPointerException One or more of the required arguments 
	 * is <code>null</code>.
	 */
	public EssenceAccess createEssence(
			@TrackID int materialTrackID,
		    DataDefinition mediaKind,
		    AUID codecID,
		    Rational editRate,
		    Rational samplerate,
		    CompressEnable compressEnable,
		    Locator destination,
		    AUID fileFormat) 
		throws NullPointerException;

	/**
	 * <p>Creates a multi-channel interleaved stream of essence for this
	 * material package.  The
	 * essence handle from this call can be used with 
	 * {@link EssenceAccess#writeSamples(int, byte[])} or 
	 * {@link EssenceMultiAccess#writeMultiSamples(MultiXferItem[])}.</p>
	 * 
	 * @param codecID Create multi-essence using this codec.
	 * @param mediaArray Create multi-essence using this array of definitions.
	 * @param enable Is multi-essence compressed?
	 * @param destination Create the interleaved essence file at this location,
	 * or <code>null</code> for default location.
	 * @param fileFormat If a file is created, use this file format as specified
	 * by the identifier of a {@linkplain ContainerDefinition container definition}.
	 * 
	 * @return Access to the created multi-essence.
	 * 
	 * @throws NullPointerException One or more of the required arguments 
	 * is <code>null</code>.
	 */
	public EssenceMultiAccess createMultiEssence(
			AUID codecID,
			MultiCreateItem[] mediaArray,
			CompressEnable enable,
			Locator destination,
			AUID fileFormat) 
		throws NullPointerException;

	/**
	 * <p>Opens a single channel of a file source package referenced from a track of this
	 * material package.  If the essence is interleaved, then it will be de-interleaved when samples are
	 * read.  This routine follows the locator of the source package's essence descriptor.</p>
	 * 
	 * <p>The essence handle from this call can be used with 
	 * {@link EssenceAccess#readSamples(int)}, but NOT with
	 * {@link EssenceMultiAccess#readMultiSamples(MultiXferItem[])}.</p>
	 * 
	 * @param trackID Open essence on this track.
	 * @param mediaCriteria Select essence using this criteria.
	 * @param openMode Open essence with this mode, either read only or append.
	 * @param compressEnable Should the essence be decompressed?
	 * @return Access to the essence.
	 * 
	 * @throws NullPointerException One or more of the arguments was null.
	 * @throws TrackNotFoundException The specified track does not exist
	 * in the material package.
	 */
	public EssenceAccess openEssence(
			@TrackID int trackID,
		    CriteriaType mediaCriteria,
		    MediaOpenMode openMode,
		    CompressEnable compressEnable) 
		throws NullPointerException,
			TrackNotFoundException;

	// Can throw other exceptions.

	/**
	 * <p>Opens all channels associated with a file {@linkplain SourcePackage source package} referenced 
	 * from this material package.  This routine
	 * follows the {@linkplain EssenceDescriptor#getLocators() locator of the essence descriptor} 
	 * of the source package.</p>
	 * 
	 * <p>The essence handle from this call can be used with 
	 * {@link EssenceMultiAccess#writeMultiSamples(MultiXferItem[])}
	 * but <strong>not</strong> with 
	 * {@link EssenceAccess#writeSamples(int, byte[])}.</p>
	 * 
	 * @param trackID Track id of this material package referencing the essence to open.
	 * @param mediaCriteria Select a kind of representation of the content in the track using this criteria.
	 * @param openMode Open mode for essence access, either {@link tv.amwa.maj.enumeration.MediaOpenMode#ReadOnly} or
	 * {@link tv.amwa.maj.enumeration.MediaOpenMode#Append}.
	 * @param compressEnable Should the essence be decompressed?
	 * @return Access to the multi-channel essence.
	 * 
	 * @throws NullPointerException One or more of the media criteria, open mode or compress enable 
	 * arguments is/are <code>null</code>.
	 * @throws TrackNotFoundException The given track does not exist in this material package.
	 */
	public EssenceMultiAccess openMultiEssence(
			@TrackID int trackID,
			CriteriaType mediaCriteria,
			MediaOpenMode openMode,
			CompressEnable compressEnable) 
		throws NullPointerException,
			TrackNotFoundException;

	/**
	 * <p>Returns the number of interleaved essence channels of a given
	 * type in the essence stream referenced from the given track of this 
	 * material package. This method is structured so that it can be called 
	 * before essence is opened.</p>
	 * 
	 * <p>If the data format is not interleaved, then the answer will
	 * always be zero or one.  This function correctly returns zero for
	 * essence types not handled by a given codec, and handles codecs
	 * which work with multiple essence types.</p>
	 * 
	 * @param trackID Count channels on this track of this material package.
	 * @param mediaCriteria Filter channels using this media criteria.
	 * @param mediaKind Count channels of this kind of essence.
	 * @return Number of channels in the given track, matching the given
	 * media criteria and essence kind.
	 * 
	 * @throws NullPointerException One or both of the media criteria and/or media
	 * kind arguments is/are <code>null</code>.
	 * @throws TrackNotFoundException The specified track does not exist
	 * in this material package.
	 */
	public @UInt16 short countChannels(
			@TrackID int trackID,
			CriteriaType mediaCriteria,
			DataDefinition mediaKind) 
		throws NullPointerException,
			TrackNotFoundException;

	/**
	 * <p>Extends a single stream of essence that was originally created using
	 * {@link #createEssence(int, DataDefinition, AUID, Rational, Rational, CompressEnable, Locator, AUID)
	 * createEssence()}. Extended essence is represented by
	 * a {@linkplain Sequence sequence} of {@linkplain SourceClip source clips}. 
	 * The first call to this method will cause the {@linkplain TimelineTrack timeline 
	 * track's} {@linkplain SourceClip source clip} object to be replaced by a {@linkplain Sequence
	 * sequence}.  The initial {@linkplain SourceClip source clip} becomes the first 
	 * component of the new {@linkplain Sequence sequence}.</p>
	 * 
	 * @param materialTrackID Track id of the essence to be extended on this material package.
	 * @param mediaKind Create the extended essence with this type.
	 * @param codecID Create the extended essence with this codec.
	 * @param editRate Create the extended essence with this edit rate.
	 * @param sampleRate Create the extended essence with this sample rage.
	 * @param enable Should the extended essence by compressed?
	 * @param destintation Optionally create a file of extended essence at this location.
	 * @param fileFormat If a file is created, use this file format, as specified by a
	 * the identifier of a {@linkplain ContainerDefinition container definition}.
	 * @return Essence access to the newly extended essence.
	 * 
	 * @throws NullPointerException One of more of the required arguments is/are <code>null</code>.
	 */
	public EssenceAccess extendEssence(
			@TrackID int materialTrackID,
			DataDefinition mediaKind,
			AUID codecID,
			Rational editRate,
			Rational sampleRate,
			CompressEnable enable,
			Locator destintation,
			AUID fileFormat) 
		throws NullPointerException;

	/**
	 * <p>Extends a multi-channel interleaved stream of essence that was
	 * originally created using 
	 * {@link #createMultiEssence(AUID, MultiCreateItem[], CompressEnable, Locator, AUID)
	 * createMultiEssence()}. Extended essence is represented by a {@linkplain Sequence 
	 * sequence} of {@linkplain SourceClip source clips}. The first call to 
	 * this method will cause the {@linkplain TimelineTrack timeline track's} 
	 * {@linkplain SourceClip source clip} to be replaced by a {@linkplain Sequence sequence}.
	 * The initial {@linkplain SourceClip source clip} becomes the first component of the new 
	 * {@linkplain Sequence sequence}.</p>
	 * 
	 * @param codecId Extend the multi-channel essence using this codec.
	 * @param mediaArray Use these media definitions to extend the essence.
	 * @param compressEnable Should the extension be done with compression enabled?
	 * @param destination Optionally create a file at this location.
	 * @param fileFormat If the file is created, use this format, as specified by 
	 * the identifier of a {@linkplain ContainerDefinition container definition}.
	 * @return Essence access to the extended essence.
	 * 
	 * @throws NullPointerException One or more of the required arguments is/are <code>null</code>.
	 * 
	 * @see MultiCreateItem
	 * @see tv.amwa.maj.constant.ContainerConstant
	 */
	public EssenceMultiAccess extendMultiEssence(
			AUID codecId,
			MultiCreateItem[] mediaArray,
			CompressEnable compressEnable,
			Locator destination,
			AUID fileFormat) 
		throws NullPointerException;

	/**
	 * <p>Creates and initializes static essence as a referenced track of 
	 * this material package, returning access to them.</p>
	 * 
	 * <p>This method is broadly similar to 
	 * {@link #createEssence(int, DataDefinition, AUID, Rational, Rational, CompressEnable, Locator, AUID)
	 * createEssence()} except that the essence is created in a 
	 * {@link StaticTrack static track} in this material package.</p>
	 * 
	 * <p>The essence handle from this call can be used with 
	 * {@link EssenceAccess#writeSamples(int, byte[])} but not with 
	 * {@link EssenceMultiAccess#writeMultiSamples(MultiXferItem[])}.</p>
	 * 
	 * @param materialTrackID Track of this material package in which to create static essence.
	 * @param mediaKind Create essence of this type.
	 * @param codecID Create essence using this codec.
	 * @param compressEnable Compress the created essence?
	 * @param destination Optionally create the essence file at this location.
	 * @param fileFormat If the file is created, use this file format, as specified by a 
	 * {@link ContainerDefinition container definition}.
	 * @return Access to the newly created essence.
	 * 
	 * @throws NullPointerException One or more of the required arguments is/are <code>null</code>.
	 * 
	 * @see tv.amwa.maj.constant.ContainerConstant
	 */
	public EssenceAccess createStaticEssence(
			@TrackID int materialTrackID,
			DataDefinition mediaKind,
			AUID codecID,
			CompressEnable compressEnable,
			Locator destination,
			AUID fileFormat) 
		throws NullPointerException;

	/**
	 * <p>Creates and initializes an {@linkplain EventTrack event track} in a track 
	 * of this material packages that represents a stream of {@linkplain Event events}.</p>
	 * 
	 * <p>This method is broadly similar to 
	 * {@link #createEssence(int, DataDefinition, AUID, Rational, Rational, CompressEnable, Locator, AUID)
	 * createEssence()} except that the essence is created in a 
	 * {@link EventTrack event track} in this material package.</p>
	 * 
	 * <p>The essence handle from this call can be used with 
	 * {@link EssenceAccess#writeSamples(int, byte[])} but not with 
	 * {@link EssenceMultiAccess#writeMultiSamples(MultiXferItem[])}.</p>
	 * 
	 * @param materialTrackID Track of this material package in which to create the event essence.
	 * @param mediaKind Create essence of this type.
	 * @param codecID Create essence with this codec.
	 * @param editRate Create essence with this edit rate.
	 * @param sampleRate Create essence with this sample rate.
	 * @param compressEnable Compress the created essence?
	 * @param destination Optionally create a the essence file at this location.
	 * @param fileFormat If the file is created, use this file format, as specified
	 * by the identifier of a {@linkplain ContainerDefinition container definition}.
	 * @return Access to the newly created essence.
	 * 
	 * @throws NullPointerException One or more of the required arguments is/are <code>null</code>.
	 * 
	 * @see tv.amwa.maj.constant.ContainerConstant
	 * @see tv.amwa.maj.constant.CodecConstant
	 */
	public EssenceAccess createEventEssence(
			@TrackID int materialTrackID,
			DataDefinition mediaKind,
			AUID codecID,
			Rational editRate,
			Rational sampleRate,
			CompressEnable compressEnable,
			Locator destination,
			AUID fileFormat) 
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this material package.</p>
	 *
	 * @return Cloned copy of this material package.
	 */
	public MaterialPackage clone();
}
