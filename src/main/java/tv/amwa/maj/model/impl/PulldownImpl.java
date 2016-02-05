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
 * $Log: PulldownImpl.java,v $
 * Revision 1.3  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/06/18 16:52:45  vizigoth
 * Corrected PulldownDirection setter property name.
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
 * Revision 1.1  2007/11/13 22:09:51  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.PulldownDirectionType;
import tv.amwa.maj.enumeration.PulldownKindType;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.TimecodeNotFoundException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.misctype.PhaseFrameType;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.Pulldown;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.model.TimecodeSegment;
import tv.amwa.maj.record.TimecodeValue;
import tv.amwa.maj.union.impl.SourceReferenceValueImpl;


/** 
 * <p>Implements the representation of a conversion between film frame rates and videotape 
 * frame rates. This class provides a mechanism to convert essence 
 * to and from video and film rates and describe the mechanism that was used to convert the 
 * essence.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0c00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Pulldown",
		  description = "The Pulldown class converts between film frame rates and videotape frame rates.",
		  symbol = "Pulldown")
public class PulldownImpl
	extends 
		SegmentImpl
	implements 
		Pulldown,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 4587590050086371546L;

	private Segment inputSegment;
	private PulldownKindType pulldownKind;
	private PulldownDirectionType pulldownDirection;
	private int phaseFrame;
	
	public PulldownImpl() {
	}

	/** 
	 * <p>Creates and initializes a new pulldown object, which converts between film frame rates 
	 * and videotape frame rates</p>
	 *
	 * @param dataDefinition Kind of data represented by this new component.
	 * @param inputSegment A segment that is either a {@link SourceClipImpl source clip} or 
	 * {@link TimecodeSegmentImpl timecode segment}. The length of input segment object is in the edit 
	 * units determined by the pulldown kind and pulldown direction.
	 * @param pulldownKind Specifies whether the new pulldown object is converting from nominally 30 Hz 
	 * or 25 Hz videoframe rate and whether frames are dropped or the video is played at another speed.
	 * @param pulldownDirection Specifies whether the pulldown object is converting from tape to film 
	 * speed or from film to tape speed.
	 * @param phaseFrame The phase within the repeating pulldown pattern of the first frame after the 
	 * pulldown conversion. A value of <code>0</code> specifies that the pulldown object starts 
	 * at the beginning of the pulldown pattern.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and all arguments
	 * are required.
	 */
	public PulldownImpl(
			DataDefinition dataDefinition,
			Segment inputSegment,
			PulldownKindType pulldownKind,
			PulldownDirectionType pulldownDirection,
			@PhaseFrameType int phaseFrame)
		throws NullPointerException {
		
		// TODO how does the length get set? by calculation from input segment ... but how?
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a new pulldown segment with a null data definition.");
		if (inputSegment == null)
			throw new NullPointerException("Cannot create a new pulldown segment with a null input segment value.");
		if (pulldownKind == null)
			throw new NullPointerException("Cannot create a new pulldown segment with a null pulldown kind value.");
		if (pulldownDirection == null)
			throw new NullPointerException("Cannot create a new pulldown segment with a null pulldown direction value.");
		
		setComponentDataDefinition(dataDefinition);
		setInputSegment(inputSegment);
		setPulldownKind(pulldownKind);
		setPulldownDirection(pulldownDirection);
		setPhaseFrame(phaseFrame);
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0207, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "InputSegment",
			typeName = "SegmentStrongReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0D01,
			symbol = "InputSegment")
	public Segment getInputSegment() {

		return inputSegment;
	}

	@MediaPropertySetter("InputSegment")
	public void setInputSegment(
			Segment inputSegment)
		throws NullPointerException,
			IllegalArgumentException {

		if (inputSegment == null)
			throw new NullPointerException("Cannot set the input segment of this pulldown using a null segment value.");
		
		if (!((inputSegment instanceof tv.amwa.maj.model.SourceClip) || 
				(inputSegment instanceof tv.amwa.maj.model.TimecodeSegment)))
			throw new IllegalArgumentException("The input segment must for a pulldown operation must be either a source clip or a timecode.");
		
		this.inputSegment = inputSegment;
	}
	
	@MediaProperty(uuid1 = 0x05401001, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PhaseFrame",
			typeName = "PhaseFrameType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0D04,
			symbol = "PhaseFrame")
	public int getPhaseFrame() {

		return phaseFrame;
	}
	
	public final static Segment initializeInputSegment() {
		
		return new SourceClipImpl(
				DataDefinitionImpl.forName("Unknown"), 
				0l, 
				SourceReferenceValueImpl.originalSource());
	}

	@MediaPropertySetter("PhaseFrame")
	public void setPhaseFrame(
			int phaseFrame) {

		this.phaseFrame = phaseFrame;
	}
	
	public final static int initializePhaseFrame() {
		
		return 0;
	}

	@MediaProperty(uuid1 = 0x05401001, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PulldownDirection",
			typeName = "PulldownDirectionType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0D03,
			symbol = "PulldownDirection")
	public PulldownDirectionType getPulldownDirection() {

		return pulldownDirection;
	}

	@MediaPropertySetter("PulldownDirection")
	public void setPulldownDirection(
			PulldownDirectionType pulldownDirection) 
		throws NullPointerException {

		if (pulldownDirection == null)
			throw new NullPointerException("Cannot set the pulldown direction for this pulldown segment to null.");
		
		this.pulldownDirection = pulldownDirection;
	}

	public final static PulldownDirectionType initializePulldownDirection() {
		
		return PulldownDirectionType.FilmToTapeSpeed;
	}
	
	@MediaProperty(uuid1 = 0x05401001, uuid2 = (short) 0x0200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PulldownKind",
			typeName = "PulldownKindType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0D02,
			symbol = "PulldownKind")
	public PulldownKindType getPulldownKind() {

		return pulldownKind;
	}

	@MediaPropertySetter("PulldownKind")
	public void setPulldownKind(
			PulldownKindType pulldownKind) 
		throws NullPointerException {
		
		if (pulldownKind == null)
			throw new NullPointerException("Cannot set the pulldown kind for this pulldown segment to null.");

		this.pulldownKind = pulldownKind;
	}

	public final static PulldownKindType initializePulldownKind() {
		
		return PulldownKindType.OneToOnePAL;
	}
	
	public Pulldown clone() {
		
		return (Pulldown) super.clone();
	}
	
	public TimecodeValue segmentOffsetToTC( // TODO test this port from the C code.
			long offset)
	 	throws TimecodeNotFoundException {

		return internalSegmentOffsetToTC(offset);
	}

	public long segmentTCToOffset( // TODO test this port from the C code.
			tv.amwa.maj.record.TimecodeValue timecode,
			tv.amwa.maj.record.Rational editrate) // edit rate not used here
		throws NullPointerException,
			TimecodeNotFoundException {

		if (timecode == null)
			throw new NullPointerException("Cannot compute offset from a null timecode value.");
		
		/* if (editrate == null)
			throw new NullPointerException("Cannot compute offset from a null edit rate."); */
		
		if (!(inputSegment instanceof TimecodeSegmentImpl))
			throw new TimecodeNotFoundException("The input segment of this pulldown segment is not a timecode segment.");
		
		TimecodeSegment timecodeSegment = (TimecodeSegment) inputSegment;
		TimecodeValue startTimecode = timecodeSegment.getTimecode();
		
		long segmentLength = 0l;
		try {
			segmentLength = timecodeSegment.getComponentLength();
		}
		catch (BadPropertyException bpe) {
			throw new NullPointerException("Cannot get the length of the input segment of this pulldown segment.");
		}
		long offset = timecode.getStartTimecode() - startTimecode.getStartTimecode();
		
		if ((offset < 0) || (offset >= segmentLength))
			throw new TimecodeNotFoundException("Calculated offset is out of range.");
		
		Offset mapOffset = mapOffset(this, offset, true);
		
		return mapOffset.numFrames;
	}

	static class Offset {
		long numFrames;
		int srcPhase;
	}

	static Offset mapOffset(
			Pulldown pulldown,
			long offset,
			boolean reverse) {
		
		Offset mapOffset = new Offset();

		int phaseOffset = pulldown.getPhaseFrame();
		boolean drop = (pulldown.getPulldownDirection() == PulldownDirectionType.TapeToFilmSpeed);
		PulldownMask pulldownMask = getPulldownMask(pulldown.getPulldownKind());
		
		if (pulldownMask.isOneToOne == true) {
			mapOffset.numFrames = offset;
		}
		else {
			if (reverse == true) drop = !(drop);
			
			int zeroPos = 0;
			int sign = 0;
			if (offset < zeroPos)
				sign = -1;
			else
				sign = 1;
			
			int maskones = maskGetBits(pulldownMask.mask);
			
			int revolutions, remainder, ones = 0, tmpOnes = 0;
			
			if (drop == true) {
				revolutions = (int) Math.abs(offset) / pulldownMask.length;
				remainder = (int) (offset % pulldownMask.length);
				
				mapOffset.srcPhase = remainder;
				
				ones = revolutions * maskones;

				tmpOnes = getRemFramesDrop(pulldownMask.mask, remainder, phaseOffset, pulldownMask.length);
				ones += tmpOnes;
				ones *= sign;

				mapOffset.numFrames = ones;
			}
			else {
				if (pulldownMask.mask != 0) {

					  revolutions = (int) Math.abs(offset) / maskones;
					  remainder = (int) (offset % maskones);
					  mapOffset.srcPhase = remainder;

					  int full = revolutions * pulldownMask.length;
					  tmpOnes = getRemFramesDouble(pulldownMask.mask, remainder, phaseOffset, pulldownMask.length);

					  full += tmpOnes;
					  full *= sign;
					  mapOffset.numFrames = full;
				}
					
			}
		}
		
		return mapOffset;
	}

	private static int getRemFramesDrop(
			int maskBits, 
			int ones, 
			int phase,
			int masksize) {

		int remMask = maskBits;
		
		for ( int phaseCounter = 0 ; phaseCounter < phase ; phaseCounter++ )
			remMask <<= 1;
		
		int remFramesDrop = 0;
		for ( int maskBitsLeft = masksize ; ones > 0 ; maskBitsLeft-- ) {
			
			if (maskBitsLeft == 0) {
				remMask = maskBits;
				maskBitsLeft = masksize;
			}
			
			if (remMask < 0)
				remFramesDrop++;
			
			ones--;
			remMask <<= 1;
		}

		return remFramesDrop;
	 }



	private static int getRemFramesDouble(
			int maskBits, 
			int ones,
			int phase,
			int masksize) {

		int remMask = maskBits;
		
		for ( int phaseCounter = 0; phaseCounter < phase ; phaseCounter++ ) 
			remMask <<= 1;

		int remFramesDouble = 0;
		
		int phaseCount = phase;
		for ( int maskBitsLeft = masksize ; ones > 0 ; maskBitsLeft--) {
			if (maskBitsLeft == 0) {
				remMask = maskBits;
				phaseCount = 0;
				maskBitsLeft = masksize;
			}
			
			remFramesDouble++;
			
			if (remMask < 0) ones --;
			
			phaseCount++;
			remMask <<= 1;
		}
	
		return remFramesDouble;
	}

	private static int maskGetBits(
			int maskBits) {

		int ones = 0;
		for ( ; maskBits > 0 ; maskBits <<= 1)
			if (maskBits < 0) ones++;
		
		return ones;
	}
		
	/*
	   Iterate over the mask counting all bits and 1-bits 

	  for (tmpMask=maskBits,ones=0; tmpMask; tmpMask<<=1)

		{

		   If high bit is 1 

		  if (tmpMask<0) 

			ones++;

		}



	  if (maskones)

		*maskones = ones;

	  return(AAFRESULT_SUCCESS);
*/

	static class PulldownMask {
		int mask = 0;
		int length = 0;
		boolean isOneToOne = false;
	}

	private static PulldownMask getPulldownMask(
			PulldownKindType pulldown) {

		PulldownMask mask = new PulldownMask();
		
		switch(pulldown) {
		
		/* !! How do we handle "average of fields" mode (it's still even or odd dominant)
		 * 
		 * NTSC pullown pattern is:
		 *     AA BB BC CD DD
		 *     
		 *     Looking at the odd field, we see:
		 *         A B B C D
		 */
		case TwoThreePD:
			mask.mask = 0xd8000000;
			mask.length = 5;
			mask.isOneToOne = false;
			break;
			
		/* 
		 * PAL pullown pattern is:
		 *     AA BB CC DD EE FF GG HH II JJ KK LL MM MN NO OP PQ QR RS ST TU UV VW WX XY YY
		 * 
		 *     Looking at the odd field, we see:
		 *         A B C D E F G H I J K L M M N O P Q R S T U V W X Y
		 */
		case PALPD:
			mask.mask = 0xFFF7FF80;
			mask.length = 25;
			mask.isOneToOne = false;
			break;
			
		case OneToOneNTSC:
		case OneToOnePAL:
			mask.isOneToOne = true;
			break;
			
		default:
			break;
		}	
		
		return mask;
	}

	private TimecodeValue internalSegmentOffsetToTC(
			long offset)
		throws TimecodeNotFoundException {

		if (!(inputSegment instanceof TimecodeSegmentImpl))
			throw new TimecodeNotFoundException("Cannot compute offset as a timecode value cannot be found.");
		
		TimecodeValue timecode = ((TimecodeSegmentImpl) inputSegment).getTimecode().clone();
		
		Offset offsetDetails = mapOffset(this, offset, false);
		timecode.setStartTimecode(timecode.getStartTimecode() + offsetDetails.numFrames);
		
		return timecode;
	}

}
