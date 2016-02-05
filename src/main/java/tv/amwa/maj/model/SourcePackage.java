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
 * $Log: SourcePackage.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/10/26 08:53:56  vizigoth
 * Deprecating get/setEssenceDescriptor in favour of the more correct get/setEssenceDescription.
 *
 * Revision 1.2  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.3  2008/02/08 11:27:18  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2007/12/04 09:37:38  vizigoth
 * Minor comment updates.
 *
 * Revision 1.1  2007/11/13 22:08:50  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.AppendOption;
import tv.amwa.maj.enumeration.EdgeType;
import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.enumeration.PulldownDirectionType;
import tv.amwa.maj.enumeration.PulldownKindType;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadRateException;
import tv.amwa.maj.exception.BadSizeException;
import tv.amwa.maj.exception.FilmDescriptorOnlyException;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.NotSourceClipException;
import tv.amwa.maj.exception.PulldownDirectionException;
import tv.amwa.maj.exception.TapeDescriptorOnlyException;
import tv.amwa.maj.exception.TrackExistsException;
import tv.amwa.maj.misctype.EdgecodeHeader;
import tv.amwa.maj.misctype.FrameLength;
import tv.amwa.maj.misctype.FrameOffset;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.PhaseFrameType;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.TimecodeValue;
import tv.amwa.maj.union.SourceReferenceValue;


/**
 * <p>Specifies the description of essence that is either stored in a digital form in a 
 * file or stored on a physical media, such as tape or film. The description is 
 * provided by an {@linkplain EssenceDescriptor essence descriptor}, which is
 * either a {@linkplain TapeDescriptor tape descriptor}, {@linkplain AAFFileDescriptor
 * file descriptor}, {@linkplain FilmDescriptor film descriptor}, or some
 * extended descriptor.</p>
 * 
 * <p>This described essence is immutable. If the essence changes, 
 * such as if a videotape is redigitized, a new source package must be 
 * created with a new {@linkplain tv.amwa.maj.record.PackageID package id}.</p>
 * 
 * <p>Some methods exist in this interface that are only applicable for 
 * one kind of {@linkplain EssenceDescriptor essence descriptor}.</p>
 * 
 * <ul>
 *  <li>{@link #appendTimecodeTrack(Rational, int, TimecodeValue, long) 
 *  appendTimecodeTrack()} - works only on tape source packages.</li>
 *  <li>{@link #appendEdgecodeTrack(Rational, int, long, long, FilmType, EdgeType, byte[])
 *  appendEdgecodeTrack()} - works only on film source packages.</li>
 * </ul>
 * 
 * <p>These methods will throw an exception if the wrong descriptor is present.</p>
 * 
 * <p>If a source package points to another source package at the same rate
 * (or non-picture), then {@link #appendPhysicalSourceReference(Rational, int, DataDefinition, SourceReferenceValue, long)
 * appendPhysicalSourceReference()} is used to create the relationship.</p>
 * 
 * <p>If a source package points to picture on another source package at a different
 * sample rate, then {@link #addPulldownReference(AppendOption, Rational, int, DataDefinition, SourceReferenceValue, long, PulldownKindType, int, PulldownDirectionType) 
 * addPulldownReference()} is used to create the relationship, and 
 * the {@linkplain Pulldown pulldown} which describes how to map between the two rates.</p>
 * 
 * <p>If a source package is the end of the derivation chain for a particular 
 * track, then {@link #addNilReference(int, long, DataDefinition, Rational)
 * addNilReference()} should be called for that track, to say that that the track 
 * does exist on this source package. For example, a video file package with no 
 * derivation would have a single track of type video, with an original source reference 
 * to show that video exists and was not derived from anything else on 
 * record.</p>
 * 
 *
 * 
 */

public interface SourcePackage 
	extends Package, SearchSource {

	/** 
	 * <p>Represents a length where an actual length value is not known.</p> 
	 * 
	 * @see #appendTimecodeTrack(Rational, int, TimecodeValue, long)
	 */
	public final static long FULL_RANGE = -1l;
	
	/**
	 * <p>Returns the essence descriptor of this source package, which describes the format of the essence 
	 * associated with the source package.</p>
	 * 
	 * @return Essence descriptor of this source package.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDescriptorStrongReference
	 */
	@Deprecated
	public EssenceDescriptor getEssenceDescriptor(); 

	/**
	 * <p>Returns the essence descriptor of this source package, which describes the format of the essence 
	 * associated with the source package.</p>
	 * 
	 * @return Essence descriptor of this source package.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#EssenceDescriptorStrongReference
	 */
	public EssenceDescriptor getEssenceDescription();
	
	/**
	 * <p>Sets the essence descriptor of this source package, which describes the format of the essence 
	 * associated with the source package.</p>
	 * 
	 * @param essence Essence descriptor of this source package.
	 * 
	 * @throws NullPointerException The given essence descriptor is <code>null</code>.
	 */
	@Deprecated
	public void setEssenceDescriptor(
			EssenceDescriptor essence) 
		throws NullPointerException;

	/**
	 * <p>Sets the essence descriptor of this source package, which describes the format of the essence 
	 * associated with the source package.</p>
	 * 
	 * @param essenceDescriptor Essence descriptor of this source package.
	 * 
	 * @throws NullPointerException The given essence descriptor is <code>null</code>.
	 */
	public void setEssenceDescription(
			EssenceDescriptor essenceDescriptor)
		throws NullPointerException;
	
	// TODO are negative lengths allowed ... see the FullLength constant.
	
	/**
	 * <p>Adds a track containing an original source reference to this source package.  
	 * This special source id indicates that the package chain ends here, which indicates 
	 * that no further record exists of where the essence was derived from.  Some 
	 * {@linkplain SourceClip source clip} is still required on the track to indicate 
	 * that the track exists and may be referenced from other {@linkplain Package packages}.</p>
	 * 
	 * <p>Examples of source packages that are not derived from a previous
	 * source of essence are:</p>
	 * 
	 * <ul>
	 *  <li>tape source packages that were not created from film;</li>
	 *  <li>file source packages whose digital essence data was originally 
	 *  generated by computer and was not digitized from videotape.</li>
	 * </ul>
	 * 
	 * @param trackId Track id to be assigned to the new track.
	 * @param length Duration of the source clip to be added to the new track.
	 * @param dataDefinition Data definition for the new track.
	 * @param editRate Edit rate for the new track.
	 * 
	 * @throws NullPointerException The given data definition and/or edit rate is/are 
	 * <code>null</code>.
	 * @throws IllegalArgumentException The given track identifier is <code>null</code>
	 * @throws BadRateException The edit rate is not valid.
	 * @throws BadLengthException The length cannot be a negative value.
	 * @throws TrackExistsException The given track id matches that of a
	 * track already present in this source package.
	 * 
	 * @see tv.amwa.maj.union.SourceReferenceValue#isOriginalSource()
	 */
	public void addNilReference(
			@TrackID int trackId,
			@LengthType long length,
			DataDefinition dataDefinition,
			Rational editRate) 
		throws NullPointerException,
			IllegalArgumentException,
			BadRateException,
			BadLengthException,
			TrackExistsException;

	/**
	 * <p>Creates and adds a timecode track to a tape package represented by this source
	 * package, with the given starting timecode, length and edit rate. It is then necessary to call
	 * {@link #specifyValidCodeRange(DataDefinition, int, Rational, long, long)
	 * specifyValidCodeRange()} to add the {@linkplain Filler filler} to the 
	 * other essence tracks to indicate that the timecode is valid for that 
	 * other tracks of this package.</p>
	 * 
	 * <p>The start timecode parameter is expressed in frames 
	 * since midnight. The length parameter can be the value {@link #FULL_RANGE}, 
	 * in which case the length is 24 hours.</p>
	 * 
	 * @param editRate Edit rate of the timecode track.	
	 * @param trackID Track id for the new timecode track.
	 * @param startTimecode Starting timecode for the timecode track.
	 * @param length Duration of the new timecode track.
	 * 
	 * @throws NullPointerException One or both of the edit rate and/or start timecode arguments 
	 * is/are <code>null</code>.
	 * @throws TapeDescriptorOnlyException This operation is only valid 
	 * for a source package referencing a {@linkplain TapeDescriptor tape descriptor}.
	 * @throws TrackExistsException The given track id matches that of a track already present in this source package.
	 * 
	 * @see #FULL_RANGE
	 * @see tv.amwa.maj.industry.Forge#makeTimecode(long, short, boolean)
	 * @see tv.amwa.maj.record.TimecodeValue TimecodeValue specification
	 */
	public void appendTimecodeTrack(
			Rational editRate,
			@TrackID int trackID,
			TimecodeValue startTimecode,
			@FrameLength long length)
		throws NullPointerException,
			TapeDescriptorOnlyException,
			TrackExistsException;

	/**
	 * <p>Creates and adds an edgecode track to the film package represented by this 
	 * source package, with the given starting edgecode, length and edit rate.
	 * It is also necessary to then add an essence track with 
	 * {@link #specifyValidCodeRange(DataDefinition, int, Rational, long, long)
	 * specifyValidCodeRange()} to make the new edgecode track valid.</p>
	 * 
	 * @param editRate Edit rate for the new edgecode track.
	 * @param trackID Track id for the new track.
	 * @param startEC Starting edgecode of the new edgecode track.
	 * @param length Length of the new edgecode track.
	 * @param filmKind Film kind for the new edgecode track.
	 * @param codeFormat Code for the new edgecode track.
	 * @param header Edgecode 8-byte header for the new edgecode track.
	 * 
	 * @throws NullPointerException One or more of the arguments is null.
	 * @throws FilmDescriptorOnlyException This operation is only valid 
	 * for a source package referencing a {@linkplain FilmDescriptor film descriptor}.
	 * @throws BadSizeException The size of the header is not the required 8 bytes.
	 * @throws BadLengthException The length value is negative.
	 * @throws TrackExistsException The given track id matches that of a track already 
	 * present in this source package.
	 * 
	 * @see tv.amwa.maj.industry.Forge#makeEdgeCode(long, FilmType, EdgeType)
	 * @see tv.amwa.maj.record.EdgeCodeValue EdgecodeValue specification
	 */
	public void appendEdgecodeTrack(
			Rational editRate,
			@TrackID int trackID,
			@FrameOffset long startEC,
			@FrameLength long length,
			FilmType filmKind,
			EdgeType codeFormat,
			@EdgecodeHeader byte[] header) 
		throws NullPointerException,
			IllegalArgumentException,
			FilmDescriptorOnlyException, 
			BadLengthException, 
			BadSizeException,
			TrackExistsException;

	/**
	 * <p>Creates and adds a track containing {@linkplain SourceClip source clips} to the
	 * source package to indicate that a (recently added) timecode track or edgecode track is 
	 * valid for that channel.</p>
	 * 
	 * <p>The essence kind parameter requires a data kind with
	 * valid for an essence stream. Valid data kinds are:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain tv.amwa.maj.constant.DataDefinitionConstant#Picture}</li>
	 *  <li>{@linkplain tv.amwa.maj.constant.DataDefinitionConstant#Sound}</li>
	 * </ul>
	 * 
	 * @param essenceKind Data kind for the track to be added.
	 * @param trackId Track id for the track to be added.
	 * @param editRate Edit rate for the track to be added.
	 * @param startOffset Start offset for the track to be added.
	 * @param length Duration of the source clip in the track.
	 * 
	 * @throws NullPointerException One or both of the edit rate and/or essence kind arguments 
	 * is/are <code>null</code>.
	 * @throws BadLengthException The given length for the new source clip is negative.
	 * @throws TrackExistsException The given track id matches that of a track already present 
	 * in this source package.
	 */
	public void specifyValidCodeRange(
			DataDefinition essenceKind,
			@TrackID int trackId,
			Rational editRate,
			@FrameOffset long startOffset,
			@FrameLength long length) 
		throws NullPointerException,
			BadLengthException,
			TrackExistsException;

	/**
	 * <p>Connects this source package with the physical source package that
	 * describes the previous generation of essence, appending it to the
	 * existing package chain.  If a physical source package, such 
	 * as a file source package or tape source package, references another physical
	 * source package as its ancestor, with no pulldown, then this
	 * function makes the connection between the two.</p>
	 * 
	 * <p>This is a helper method to create a track with a 
	 * {@linkplain SourceClip source clip} referencing a particular piece 
	 * of media.</p>
	 * 
	 * <p>The ancestor of a source package with a {@linkplain AAFFileDescriptor file
	 * descriptor} is often a {@linkplain TapeDescriptor tape descriptor}.</p>
	 * 
	 * <p>The <code>essenceKind</code> parameter requires a data kind
	 * valid for an essence stream.  Valid data kinds are:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain tv.amwa.maj.constant.DataDefinitionConstant#Picture}</li>
	 *  <li>{@linkplain tv.amwa.maj.constant.DataDefinitionConstant#Sound}</li>
	 * </ul>
	 * 
	 * @param editRate Edit rate of the track to contain the reference.
	 * @param trackId Track ID of the track to contain the reference.
	 * @param essenceKind Data kind of the track to contain the reference. 
	 * @param reference Reference to a physical source package.
	 * @param sourceReferenceLength Length of the referenced source clip.
	 * 
	 * @throws NullPointerException One or more of the nullable parameters is/are
	 * <code>null</code> and all are required.
	 * @throws BadLengthException The source reference length is negative.
	 * @throws InvalidDataDefinitionException The given data definition is not
	 * compatible with that of the track this element will be appended to.
	 * @throws TrackExistsException The given track id matches that of a track 
	 * already present in this source package.
	 */
	public void appendPhysicalSourceReference(
			Rational editRate,
			@TrackID int trackId,
			DataDefinition essenceKind,
			SourceReferenceValue reference,
			@LengthType long sourceReferenceLength) 
		throws NullPointerException,
			BadLengthException,
			InvalidDataDefinitionException,
			TrackExistsException;

	/**
	 * <p>Connects this source package with the physical source package that
	 * describes the previous generation of essence, appending it to the
	 * existing package chain.  If a physical source package, such 
	 * as a file source package or tape source package, references another physical
	 * source package as its ancestor, with no pulldown, then this
	 * function makes the 1&nbsp;to&nbsp;1 connection between the two.</p>
	 * 
	 * <p>This is a helper method to create a track with a 
	 * {@linkplain SourceClip source clip} referencing a particular piece 
	 * of media.</p>
	 * 
	 * <p>The ancestor of a source package with a {@linkplain AAFFileDescriptor file
	 * descriptor} is often a {@linkplain TapeDescriptor tape descriptor}.</p>
	 * 
	 * <p>The essence kind parameter requires a data kind
	 * valid for an essence stream.  Valid data kinds are:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain tv.amwa.maj.constant.DataDefinitionConstant#Picture}</li>
	 *  <li>{@linkplain tv.amwa.maj.constant.DataDefinitionConstant#Sound}</li>
	 * </ul>
	 * 
	 * @param editRate Edit rate of the track to contain the reference.
	 * @param trackId Track id of the track to contain the reference.
	 * @param essenceKind Data kind of the track to contain the reference. 
	 * @param sourceReference Reference to a physical source package.
	 * @param sourceReferenceLength Length of the referenced source clip.
	 * 
	 * @throws NullPointerException One or more of the nullable arguments is/are <code>null</code>
	 * and all are required.
	 * @throws BadLengthException The given source reference length is negative.
	 * @throws TrackExistsException The given track id matches that of a package
	 * track already present in this source package.
	 * 
	 * @see SourceClip
	 */
	public void newPhysicalSourceReference(
			Rational editRate,
			@TrackID int trackId,
			DataDefinition essenceKind,
			SourceReferenceValue sourceReference,
			@LengthType long sourceReferenceLength) 
		throws NullPointerException, 
			BadLengthException,
			TrackExistsException;

	/**
	 * <p>Connects the given source package with the physical source package
	 * that describes the previous generation of essence, with an
	 * additional {@linkplain Pulldown pulldown} as part of the reference 
	 * to indicate a non 1&nbsp;to&nbsp;1 relationship between the two.</p>
	 * 
	 * <p>This is a helper method to create a track with a
	 * {@linkplain Pulldown pulldown} object which references a {@linkplain SourceClip
	 * source clip}, which references a particular piece of media.  This 
	 * method takes many parameters because the components of a
	 * {@linkplain Pulldown pulldown} have been broken out as separate parameters.</p>
	 * 
	 * <p>The ancestor of a source package described by a {@linkplain TapeDescriptor tape
	 * descriptor} is often a {@linkplain FilmDescriptor film descriptor}.</p>
	 * 
	 * @param addType Overwrite existing track sequence, or create a new {@linkplain Sequence 
	 * sequence} and append it.
	 * @param editRate Edit rate of track to contain the reference.
	 * @param trackId Track id for the track to contain the reference.
	 * @param essenceKind Data kind of track to contain reference.  Requires a data 
	 * kind valid for an essence stream.  Valid data kinds are 
	 * {@linkplain tv.amwa.maj.constant.DataDefinitionConstant#Picture} and 
	 * {@linkplain tv.amwa.maj.constant.DataDefinitionConstant#Sound}.
	 * @param sourceReference Reference to a physical source package.
	 * @param sourceReferenceLength Length of the referenced physical source package.
	 * @param pulldownKind Method of conversion used between this physical
	 * source package and this source package.
	 * @param phaseFrame Phase of the first frame.
	 * @param direction Direction of the pulldown operation.
	 * 
	 * @throws NullPointerException One or more of the nullable arguments is/are <code>null</code>
	 * and all are required.
	 * @throws PulldownDirectionException The given pullown direction is
	 * invalid in this context, for example 
	 * {@linkplain tv.amwa.maj.enumeration.PulldownDirectionType#TapeToFilmSpeed tape to film speed} 
	 * when this is a tape source package.
	 * @throws BadLengthException The given source reference length cannot is negative.
	 * @throws NotSourceClipException The existing contents of the given track does not 
	 * contain a source clip to add a pulldown reference to.
	 * @throws TrackExistsException The given track id matches that of a package
	 * track already present in this source package.
	 * 
	 * @see SourceClip
	 * @see Pulldown
	 * @see Sequence
	 */
	public void addPulldownReference(
			AppendOption addType,
			Rational editRate,
			@TrackID int trackId,
			DataDefinition essenceKind,
			SourceReferenceValue sourceReference,
			@LengthType long sourceReferenceLength,
			PulldownKindType pulldownKind,
			@PhaseFrameType int phaseFrame,
			PulldownDirectionType direction) 
		throws NullPointerException,
			PulldownDirectionException,
			BadLengthException,
			NotSourceClipException,
			TrackExistsException;
	
	/**
	 * <p>Create a cloned copy of this source package.</p>
	 *
	 * @return Cloned copy of this source package.
	 */
	public SourcePackage clone();
}

