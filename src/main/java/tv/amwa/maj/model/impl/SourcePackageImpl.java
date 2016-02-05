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
 * $Log: SourcePackageImpl.java,v $
 * Revision 1.3  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/10/26 08:53:11  vizigoth
 * Deprecating get/setEssenceDescriptor in favour of the more correct get/setEssenceDescription.
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
 * Revision 1.7  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.6  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.5  2008/01/18 16:10:51  vizigoth
 * Changed header property of an edgecode value to optional.
 *
 * Revision 1.4  2008/01/14 21:15:22  vizigoth
 * Update due to refactoring of null/nul mob id to zero mob id.
 *
 * Revision 1.3  2007/12/13 11:31:51  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.2  2007/12/04 13:04:48  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:46  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.List;

import tv.amwa.maj.enumeration.AppendOption;
import tv.amwa.maj.enumeration.CriteriaType;
import tv.amwa.maj.enumeration.EdgeType;
import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.enumeration.PackageKind;
import tv.amwa.maj.enumeration.OperationChoice;
import tv.amwa.maj.enumeration.PulldownDirectionType;
import tv.amwa.maj.enumeration.PulldownKindType;
import tv.amwa.maj.exception.AdjacentTransitionException;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.BadRateException;
import tv.amwa.maj.exception.BadSizeException;
import tv.amwa.maj.exception.EventSemanticsException;
import tv.amwa.maj.exception.FilmDescriptorOnlyException;
import tv.amwa.maj.exception.InsufficientTransitionMaterialException;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.InvalidPackageTypeException;
import tv.amwa.maj.exception.LeadingTransitionException;
import tv.amwa.maj.exception.NotSourceClipException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.PulldownDirectionException;
import tv.amwa.maj.exception.TrackExistsException;
import tv.amwa.maj.exception.TrackNotFoundException;
import tv.amwa.maj.exception.TapeDescriptorOnlyException;
import tv.amwa.maj.exception.TraversalNotPossibleException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.Component;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.EssenceDescriptor;
import tv.amwa.maj.model.Filler;
import tv.amwa.maj.model.Pulldown;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.model.Sequence;
import tv.amwa.maj.model.SourceClip;
import tv.amwa.maj.model.SourcePackage;
import tv.amwa.maj.model.Track;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.TimecodeValue;
import tv.amwa.maj.record.impl.EdgeCodeValueImpl;
import tv.amwa.maj.record.impl.PackageIDImpl;
import tv.amwa.maj.record.impl.TimeStampImpl;
import tv.amwa.maj.record.impl.TimecodeValueImpl;
import tv.amwa.maj.union.SourceReferenceValue;
import tv.amwa.maj.union.impl.TimecodeClipImpl;

/** 
 * <p>Implements the description of essence that is either stored in a digital form in a 
 * file or stored on a physical media, such as tape or film. The description is 
 * provided by an {@linkplain tv.amwa.maj.model.EssenceDescriptor essence descriptor}, which is
 * either a {@linkplain tv.amwa.maj.model.TapeDescriptor tape descriptor}, {@linkplain tv.amwa.maj.model.AAFFileDescriptor
 * file descriptor}, {@linkplain tv.amwa.maj.model.FilmDescriptor film descriptor}, or some
 * extended descriptor.</p>
 * 
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x3700,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "SourcePackage",
		  aliases = { "SourceMob" },
		  description = "The SourcePackage class describes essence that is either stored in a digital form in a file or stored on a physical media, such as tape or film.",
		  symbol = "SourcePackage")
public class SourcePackageImpl
	extends 
		PackageImpl
	implements 
		SourcePackage,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -3857642937486744441L;

	private EssenceDescriptor essenceDescription;

	/** Default constructor is not public to avoid unset required fields. */
	public SourcePackageImpl() { }

	/**
	 * <p>Creates and initializes a new source package, which describes essence that is either stored in 
	 * a digital form in a file or stored on a physical media, such as tape or film. The required 
	 * properties of creation time and last modified time will be set automatically to the time of 
	 * calling this method. These timestamp values can be set manually by calling 
	 * {@link PackageImpl#setCreationTime(tv.amwa.maj.record.TimeStamp) setCreateTime()} and
	 * and {@link PackageImpl#setPackageLastModified(tv.amwa.maj.record.TimeStamp) setModTime()}.</p>
	 *
	 * @param packageID Unique identifier for the package.
	 * @param name Name of the source package.
	 * @param essenceDescription Describes the format of the essence associated with thw new source package.
	 * 
	 * @throws NullPointerException The package id and/or essence description arguments are 
	 * <code>null</code>.
	 */
	public SourcePackageImpl(
			PackageID packageID,
			@AAFString String name,
			EssenceDescriptor essenceDescription) 
		throws NullPointerException {
		
		if (packageID == null)
			throw new NullPointerException("Cannot create a new source package using a null package id value.");
		if (essenceDescription == null)
			throw new NullPointerException("Cannot create a new source package using a null essence description value.");
		
		setPackageID(packageID);
		setPackageName(name);
		setEssenceDescriptor(essenceDescription);
		
		setPackageLastModified(new TimeStampImpl());
		setCreationTime(new TimeStampImpl());
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0203, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EssenceDescription",
			typeName = "EssenceDescriptorStrongReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x4701,
			symbol = "EssenceDescription")
	public EssenceDescriptor getEssenceDescription() {

		return essenceDescription;
	}

	@Deprecated
	public EssenceDescriptor getEssenceDescriptor() {

		return getEssenceDescription();
	}
	
	@MediaPropertySetter("EssenceDescription")
	public void setEssenceDescription(
			EssenceDescriptor essenceDescriptor)
		throws NullPointerException {
		
		if (essenceDescriptor == null)
			throw new NullPointerException("Cannot set the essence descriptor for this source package to a null value.");
		
		this.essenceDescription = essenceDescriptor;
	}

	public final static EssenceDescriptor initializeEssenceDescription() {
		
		return new AuxiliaryDescriptorImpl("application/octet-stream");
	}
	
	@Deprecated
	public void setEssenceDescriptor(
			EssenceDescriptor essenceDescriptor)
		throws NullPointerException {
		
		setEssenceDescription(essenceDescriptor);
	}

	public void addNilReference( // Assumes timeline track
			int trackID,
			long length,
			tv.amwa.maj.model.DataDefinition dataDefinition,
			tv.amwa.maj.record.Rational editRate)
		throws NullPointerException,
			IllegalArgumentException,
			BadRateException,			
			BadLengthException,
			TrackExistsException {

		if (editRate.getDenominator() == 0)
			throw new BadRateException("Cannot set an edit rate with a 0 denominator.");
		
		SourceClip subClip = new SourceClipImpl(
				dataDefinition, 
				length, 
				new tv.amwa.maj.union.impl.SourceReferenceValueImpl(PackageIDImpl.getZeroPackageID(), 0, 0l));
		appendNewTimelineTrack(editRate, subClip, trackID, null, 0l);
	}

	public void addStaticNilReference(
			int trackID,
			tv.amwa.maj.model.DataDefinition dataDefinition)
		throws NullPointerException,
			IllegalArgumentException,
			TrackExistsException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot set the data definition of a new static reference using a null value.");
		
		try {
			SourceClip staticClip = new SourceClipImpl(
					dataDefinition,
					0,
					new tv.amwa.maj.union.impl.SourceReferenceValueImpl(PackageIDImpl.getZeroPackageID(), 0, 0l));
			appendNewStaticTrack(staticClip, trackID, null);
		}
		catch (BadLengthException ble) {
			/* Not worried about lengths for static data. */
		}	
	}

	public void addPulldownReference(
			AppendOption addType,
			Rational editRate,
			int trackID,
			DataDefinition essenceKind,
			SourceReferenceValue reference,
			long sourceReferenceLength,
			PulldownKindType pulldownKind,
			int phaseFrame,
			PulldownDirectionType direction)
		throws NullPointerException,
			PulldownDirectionException,
			BadLengthException,
			NotSourceClipException,
			TrackExistsException {

		if (editRate == null)
			throw new NullPointerException("Cannot add a pulldown reference to this source package using a null edit rate value.");
		if (essenceKind == null)
			throw new NullPointerException("Cannot add a pulldown reference to this source package using a null essence kind.");
		if (reference == null)
			throw new NullPointerException("Cannot add a pulldown reference to this source package using a null source reference value.");
		if (addType == null)
			throw new NullPointerException("Cannot add a pulldown reference to this source package using a null add type.");
		if (pulldownKind == null)
			throw new NullPointerException("Cannot add a pulldown reference to this source package using a null pulldown kind.");
		if (direction == null)
			throw new NullPointerException("Cannot add a pulldown reference to this source package using a null pulldown direction.");
		
		if (!((direction == PulldownDirectionType.FilmToTapeSpeed) || (direction == PulldownDirectionType.TapeToFilmSpeed)))
			throw new PulldownDirectionException("Pulldown direction must be either film to tape speed or tape to film speed.");
		
		if (sourceReferenceLength < 0l)
			throw new BadLengthException("Cannot add a pulldown reference to this source package using a negative length.");
			
		
		Pulldown pulldown = new PulldownImpl(
					essenceKind,
					new FillerImpl(essenceKind, 0l),
					pulldownKind,
					direction,
					phaseFrame);
		
		if (pulldownKind.name().contains("OneToOne"))
			pulldown.setComponentLength(sourceReferenceLength);
		else {
			pulldown.setComponentLength(PulldownImpl.mapOffset(pulldown, sourceReferenceLength, true).numFrames);
		}

		Track track = null;
		SourceClip sourceClip = null;
		try {
			track = lookupPackageTrack(trackID);
		}
		catch (TrackNotFoundException snfe) {
			sourceClip = new SourceClipImpl(essenceKind, sourceReferenceLength, reference);
			pulldown.setInputSegment(sourceClip);
			appendNewTimelineTrack(editRate, pulldown, trackID, null, 0l);
			return;
		}
		
		Segment segment = track.getTrackSegment();
		
		if (segment instanceof Sequence) {
			
			try {
				Sequence sequence = (Sequence) segment;
				int numberOfSegments = sequence.countComponentObjects();
				if (numberOfSegments == 0) {
					sourceClip = new SourceClipImpl(essenceKind, sourceReferenceLength, reference);
					pulldown.setInputSegment(sourceClip);
					sequence.appendComponentObject(pulldown);
				}

				for ( int x = 0 ; x < numberOfSegments ; x++ ) { // Find first non zero component
					Component subSegment = sequence.getComponentObjectAt(0);
					if (!(subSegment instanceof Segment)) continue;
					if (subSegment.getComponentLength() != 0l) {
						SequenceImpl.setNthComponent(sequence, pulldown, x);
						if (subSegment instanceof SourceClipImpl)
							sourceClip = (SourceClipImpl) subSegment;
						else
							sourceClip = null;
						break;
					}
				}
			} catch (InvalidDataDefinitionException e) {
				// Unlikely ... stack trace just in case.
				e.printStackTrace();
				sourceClip = null;
			} catch (LeadingTransitionException e) {
				// Appending a source clip
				sourceClip = null;
			} catch (EventSemanticsException e) {
				// Appending a source clip, not an event
				sourceClip = null;
			} catch (BadPropertyException e) {
				// Only checking length of segments in a sequence that should have lengths
				sourceClip = null;
			} catch (AdjacentTransitionException e) {
				// Appending a segment, not a transition
				sourceClip = null;
			} catch (InsufficientTransitionMaterialException e) {
				// First component of a sequence
				sourceClip = null;
			} catch (IndexOutOfBoundsException e) {
				// Iterating within the range of the same sequence
				sourceClip = null;
			} 
		} // if (segment instanceof Sequence)
		else {
			track.setTrackSegment(pulldown);
			if (segment instanceof SourceClipImpl)
				sourceClip = (SourceClipImpl) segment;
			else
				sourceClip = null;
		}
		
		if (sourceClip == null)
			throw new NotSourceClipException("The existing contents of the given track does not contain a source clip to add a pulldown refernce to.");

		sourceClip.setComponentDataDefinition(essenceKind);
		sourceClip.setComponentLength(sourceReferenceLength);
		sourceClip.setSourceReference(reference);
		
		pulldown.setInputSegment(sourceClip);
	}

	public void appendEdgecodeTrack(
			tv.amwa.maj.record.Rational editRate,
			int trackID,
			long startEC,
			long length,
			FilmType filmKind,
			EdgeType codeFormat,
			byte[] header)
		throws NullPointerException,
			IllegalArgumentException,
			BadLengthException,
			BadSizeException,
			FilmDescriptorOnlyException,
			TrackExistsException {

		if (editRate == null)
			throw new NullPointerException("Cannot append an edgecode track to the tracks of a source package with a null edit rate value.");
		if (trackID < 0)
			throw new IllegalArgumentException("Cannot use a negative track id value when appending an edgecode value to the tracks of a source package.");
		if (filmKind == null)
			throw new NullPointerException("Cannot append an edgecode track to the tracks of a source package with a null film kind.");
		if (codeFormat == null)
			throw new NullPointerException("Cannot append an edgecode track to the tracks of a source package with a null code format value.");
		
		if (!(essenceDescription instanceof FilmDescriptorImpl))
			throw new FilmDescriptorOnlyException("The appendEdgecodeTrack() method of source package is defined for film essence only.");
		
		FillerImpl filler1 = null, filler2 = null;
		try {
			filler1 = new FillerImpl(DataDefinitionImpl.forIdentification(DataDefinitionImpl.Edgecode), 0l);
			filler2 = new FillerImpl(DataDefinitionImpl.forIdentification(DataDefinitionImpl.Edgecode), 0l);
		}
		catch (BadLengthException ble) {
			/* Cannot happen for a null value. */
		}
		
		SequenceImpl sequence = new SequenceImpl(DataDefinitionImpl.forIdentification(DataDefinitionImpl.Edgecode));
		
		EdgeCodeValueImpl edgecodeValue = new EdgeCodeValueImpl(
				startEC, 
				filmKind,
				codeFormat);
		
		if (header != null)
			edgecodeValue.setEdgeCodeHeader(header);
		
		EdgeCodeSegmentImpl edgecodeClip = new EdgeCodeSegmentImpl(length, edgecodeValue);
		
		try {
			sequence.appendComponentObject(filler1);
			sequence.appendComponentObject(edgecodeClip);
			sequence.appendComponentObject(filler2);
		}
		catch (InvalidDataDefinitionException e) {
			/* Data definition is forced to be the correct value. */
		} catch (LeadingTransitionException e) {
			/* Appending segments, not transitions. */
		} catch (EventSemanticsException e) {
			/* Appending edgecode segments, not events. */
		} catch (BadPropertyException e) {
			/* Lengths are always defined for edgecode value. */
		} catch (AdjacentTransitionException e) {
			/* Appending segments, not transitions. */
		} catch (InsufficientTransitionMaterialException e) {
			/* Sequence does not contain a transition. */
		}
		
		appendNewTimelineTrack(editRate, sequence, trackID, null, 0l);
	}

	public void appendPhysicalSourceReference(
			Rational editRate,
			int trackID,
			DataDefinition essenceKind,
			SourceReferenceValue reference,
			long sourceReferenceLength)
		throws NullPointerException,
			BadLengthException,
			InvalidDataDefinitionException,
			TrackExistsException {
		
		if (editRate == null)
			throw new NullPointerException("Cannot append a new physical source reference to the tracks of this source package using a null edit rate.");
		if (essenceKind == null)
			throw new NullPointerException("Cannot append a new physical source reference to the tracks of this source package using a null essence kind.");
		if (reference == null)
			throw new NullPointerException("Cannot append a new physical source reference to the tracks of this source package using a null source reference value.");
		if (trackID < 0)
			throw new IllegalArgumentException("Cannot append a new physical source reference to the tracks of this source package using a negative track id.");
		
		addPhysicalSourceReference(
				AppendOption.Append, 
				editRate, 
				trackID, 
				essenceKind, 
				reference, sourceReferenceLength);
	}

	public void appendTimecodeTrack(
			Rational editRate,
			int trackID,
			TimecodeValue startTC,
			long length)
		throws NullPointerException,
			IllegalArgumentException,
			TapeDescriptorOnlyException,
			TrackExistsException {

		if (editRate == null)
			throw new NullPointerException("Cannot set the edit rate of a new timecode track for a source package with a null value.");
		
		if (startTC == null)
			throw new NullPointerException("Cannot use a null value for the start time code of a new timecode track.");
		
		if (!(essenceDescription instanceof TapeDescriptorImpl))
				throw new TapeDescriptorOnlyException("The appendTimecodeTrack() method in source package is defined for tape essence only.");
		
		if (length == FULL_RANGE) {
			length = new TimecodeValueImpl(
					startTC.getFramesPerSecond(), 
					(short) 24, (short) 0, (short) 0, (short) 0, 
					startTC.getDropFrame()).getStartTimecode();
		}
		else
			if (length < 0l) 
				throw new IllegalArgumentException("Cannot set a negative value for the length of a new timecode track for a source package.");

		SequenceImpl sequence = new SequenceImpl(DataDefinitionImpl.forIdentification(DataDefinitionImpl.Timecode));
		TimecodeSegmentImpl timecode;
		
		try {
			timecode = new TimecodeSegmentImpl(length, startTC);
			sequence.appendComponentObject(timecode);
			
			Track track;
			try {
				track = lookupPackageTrack(trackID);
				track.setTrackSegment(sequence);
			}
			catch (TrackNotFoundException snfe) {
				appendNewTimelineTrack(editRate, sequence, trackID, null, 0l);
			}
		}
		catch (BadLengthException ble) {
			/* Already checked the length value. */
		}
		catch (LeadingTransitionException lte) {
			/* Appending a segment so will not be thrown. */
		} 
		catch (InvalidDataDefinitionException e) {
			/* Set data definitions to match. */
		} 
		catch (EventSemanticsException e) {
			/* Component is not an event. */
		} 
		catch (BadPropertyException e) {
			/* Lengths will pan out as all timecodes have lengths present. */
		} 
		catch (AdjacentTransitionException e) {
			/* Appending a segment so will not be thrown. */
		} 
		catch (InsufficientTransitionMaterialException e) {
			/* Material not of interest with timeline data. */
		}	
	}

	public void newPhysicalSourceReference(
			Rational editRate,
			int trackID,
			DataDefinition essenceKind,
			SourceReferenceValue reference,
			long sourceReferenceLength)
		throws NullPointerException,
			BadLengthException,
			TrackExistsException {

		if (editRate == null)
			throw new NullPointerException("Cannot insert a new physical source reference track into the list of tracks of this source package with a null edit rate.");
		if (essenceKind == null)
			throw new NullPointerException("Cannot insert a new physical source reference track into the list of tracks of this source package with a null essence kind.");
		if (reference == null)
			throw new NullPointerException("Cannot insert a new physical source reference track into the list of tracks of this source package with a null source reference.");
		
		try {
			addPhysicalSourceReference(
					AppendOption.ForceOverwrite,
					editRate, 
					trackID, 
					essenceKind, 
					reference, 
					sourceReferenceLength);
		}
		catch (InvalidDataDefinitionException idde) {
			/* Not thrown for forced overwrite. */
		}
	}

	public void specifyValidCodeRange(
			DataDefinition essenceKind,
			int trackID,
			Rational editRate,
			long startOffset,
			long length)
		throws NullPointerException,
			BadLengthException,
			TrackExistsException {
		
		TimecodeClipImpl timecodeClip = findTimecodeClip(startOffset);
		TimecodeValue timecode = timecodeClip.result.getTimecode();
		
		if (length == FULL_RANGE) {
			length = new TimecodeValueImpl(
					timecode.getFramesPerSecond(), 
					(short) 24, (short) 0, (short) 0, (short) 0,
					timecode.getDropFrame()).getStartTimecode();
		}
		
		long position = startOffset;
		long endFillerLength = timecodeClip.timecodeTrackLength;
		endFillerLength -= position;
		endFillerLength -= length;
		SourceClip sourceClip =  new SourceClipImpl( // TODO check ... no source reference to use
					essenceKind, 
					length, 
					new tv.amwa.maj.union.impl.SourceReferenceValueImpl(PackageIDImpl.getZeroPackageID(), 0, 0l));

		Track track = null;
		try {
			track = lookupPackageTrack(trackID);
		}
		catch (TrackNotFoundException snfe) {
			try {
				Sequence sequence = new SequenceImpl(essenceKind);
				Filler filler1 = new FillerImpl(essenceKind, 0l); // TODO check ... no length set in C code
				Filler filler2 = new FillerImpl(essenceKind, 0l);
				
				sequence.appendComponentObject(filler1);
				sequence.appendComponentObject(sourceClip);
				sequence.appendComponentObject(filler2);
				
				appendNewTimelineTrack(editRate, sequence, trackID, null, 0l);
			}
			catch (BadLengthException e) {
				// Length of fillers forced to 0 so this cannot happen
			} 
			catch (InvalidDataDefinitionException e) {
				// Set the data definitions to all be the same
			} 
			catch (LeadingTransitionException e) {
				// Appending segments
			} 
			catch (EventSemanticsException e) {
				// Appending source clips and fillers.
			} 
			catch (BadPropertyException e) {
				// Lengths are available in source clips and fillers
			} 
			catch (AdjacentTransitionException e) {
				// Appending source clips and fillers.
			} 
			catch (InsufficientTransitionMaterialException e) {
				// New sequence with no transitions.
			}

			return;
		}
		
		Segment segment = track.getTrackSegment();
		Sequence sequence = segment.generateSequence();
		List<? extends Component> segmentSequence = sequence.getComponentObjects();
		int numberOfSegments = segmentSequence.size();
		long sequencePosition = 0l;
		
		for ( int sequenceLoop = 0 ; sequenceLoop < numberOfSegments ; sequenceLoop ++) {
			
			Segment subSegment = (Segment) segmentSequence.get(sequenceLoop);
			long segmentLength = 0l;
			try { segmentLength = subSegment.getComponentLength(); }
			catch (BadPropertyException bpe) { /* Must have a length ... must be Picture or Sound. */ }
			
			if (segmentLength == 0l) continue; // Skips 0 length clips sometimes found in MC files
			
			long beginPosition = sequencePosition;
			long endPosition = sequencePosition + segmentLength;
			
			if ((position < endPosition) && (beginPosition <= position)) {
				
				if ((subSegment instanceof tv.amwa.maj.model.Filler) && 
						(sequenceLoop == (numberOfSegments - 1))) {
					
					try {
						long firstFillerLength = position;
						firstFillerLength -= sequencePosition;
						
						endFillerLength = segmentLength;
						endFillerLength -= length;
						endFillerLength -= firstFillerLength;
						
						subSegment.setComponentLength(firstFillerLength);
						long sequenceLength = sequence.getComponentLength();
						sequenceLength -= segmentLength;
						sequenceLength += firstFillerLength;
						sequence.setComponentLength(sequenceLength);
						
						Filler filler2 = new FillerImpl(essenceKind, endFillerLength);
						sequence.appendComponentObject(sourceClip);
						sequence.appendComponentObject(filler2);
					} 
					catch (BadPropertyException e) {
						// Length should be present for a sequence
					}
					catch (InvalidDataDefinitionException e) {
						// Data definitions set to be the same
					} 
					catch (LeadingTransitionException e) {
						// Adding segments, not transitions
					} 
					catch (EventSemanticsException e) {
						// Segments have length and are not likely to be events
					} 
					catch (AdjacentTransitionException e) {
						// Adding segments, not transitions
					} 
					catch (InsufficientTransitionMaterialException e) {
						// Adding segments, not transitions;
					}
				}
			}
				
			sequencePosition += segmentLength;
		} // for loop
	}

	public List<tv.amwa.maj.model.FindSourceInformation> searchSource(
			int trackID,
			long offset,
			PackageKind packageKind,
			CriteriaType mediaCriteria,
			OperationChoice operationChoice)
		throws NullPointerException,
			InvalidPackageTypeException,
			TraversalNotPossibleException {
		// TODO Implementation halted until the value of this in Java EE is established 
		return null;
	}

	private TimecodeClipImpl findTimecodeClip(
			long position) 
		throws IllegalArgumentException {
		
		int physical = -1;
		boolean found = false;
		Track track = null;
		for ( Track trackItem : getPackageTracks() ) {
			
			if (trackItem.getDataDefinition().isTimecodeKind()) {
				try {
					physical = trackItem.getEssenceTrackNumber();
				}
				catch (PropertyNotPresentException pnpe) {
					throw new IllegalArgumentException("Physical track numbers must be present when searching for timecode clips in the tracks of a source package.");
				}
				if ((physical == 0) || (physical == 1)) {
					found = true;
					track = trackItem;
					break;
				}
			}
		}
		
		if (found == false)
			throw new IllegalArgumentException("This source package is missing its primary timecode track.");
		
		Segment segment = track.getTrackSegment();

		return SegmentImpl.offsetToTimecodeClip(segment, position);
	}

	public SourcePackage clone() {
		
		return (SourcePackage) super.clone();
	}
}
