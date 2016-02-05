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
 * $Log: Pulldown.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/02/08 11:27:21  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:21  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.PulldownDirectionType;
import tv.amwa.maj.enumeration.PulldownKindType;
import tv.amwa.maj.misctype.PhaseFrameType;


/**
 * <p>Specifies a conversion between film frame rates and videotape frame rates. Implementations 
 * of this interface provides a mechanism to convert essence to and from video and film rates 
 * and describe the mechanism that was used to convert the essence.</p>
 * 
 * <p>A pulldown is typically used in one of three ways:</p>
 * 
 * <ol>
 *  <li>In a tape {@linkplain SourcePackage source package} to describe how the videotape 
 *  was created from film.</li>
 *  <li>In a file {@linkplain SourcePackage source package} that has digital essence at film 
 *  speed, to describe how the digital essence was created from the videotape.</li>
 *  <li>In a {@linkplain Package package} to create timecode tracks at different edit 
 *  rates.</li>
 * </ol>
 * 
 *
 * 
 */

public interface Pulldown 
	extends Segment {

	/**
	 * <p>Returns the input {@linkplain Segment segment} of this pulldown, which is 
	 * either a {@linkplain SourceClip source clip} or {@linkplain TimecodeSegment timecode}. 
	 * The length of the {@linkplain SourceClip source clip} or {@linkplain TimecodeSegment timecode}
	 * is in the edit units determined by the properties {@linkplain #getPulldownKind() pulldown kind}
	 * and {@linkplain #getPulldownDirection() pulldown direction}.</p>
	 * 
	 * @return Input segment of this pulldown.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#SegmentStrongReference
	 */
	public Segment getInputSegment();

	/**
	 * <p>Sets the input {@linkplain Segment segment} for this pulldown, which is 
	 * either a {@linkplain SourceClip source clip} or {@linkplain TimecodeSegment timecode}.</p>
	 * The length of the {@linkplain SourceClip source clip} or {@linkplain TimecodeSegment timecode}
	 * is in the edit units determined by the properties {@linkplain #getPulldownKind() pulldown kind}
	 * and {@linkplain #getPulldownDirection() pulldown direction}.</p>
	 * 
	 * @param inputSegment Input segment for the pulldown.
	 * 
	 * @throws NullPointerException The given input segment is <code>null</code>.
	 * @throws IllegalArgumentException The given input segment must be either a source clip
	 * or a timecode.
	 */
	public void setInputSegment(
			Segment inputSegment) 
		throws NullPointerException,
			IllegalArgumentException;

	/**
	 * <p>Returns the pulldown kind of this pulldown, which specifies whether the pulldown is 
	 * converting from nominally 30&nbsp;Hz or 25&nbsp;Hz video frame rate and whether frames 
	 * are dropped or the video is played at another speed.</p>
	 * 
	 * @return Pulldown kind of this pulldown.
	 * 
	 * @see tv.amwa.maj.enumeration.PulldownKindType
	 */
	public PulldownKindType getPulldownKind();

	/**
	 * <p>Sets the pulldown kind of this pulldown, which specifies whether the pulldown is 
	 * converting from nominally 30&nbsp;Hz or 25&nbsp;Hz video frame rate and whether frames 
	 * are dropped or the video is played at another speed.</p>
	 * 
	 * @param pulldownKind Pulldown kind of this pulldown.
	 * 
	 * @throws NullPointerException The given pulldown kind is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.enumeration.PulldownKindType
	 */
	public void setPulldownKind(
			PulldownKindType pulldownKind)
		throws NullPointerException;

	/**
	 * <p>Returns the pulldown direction of this pulldown, which specifies whether the pulldown 
	 * is converting from tape to film speed or from film to tape speed.</p>
	 * 
	 * @return Pulldown direction field of the pulldown.</p>
	 * 
	 * @see tv.amwa.maj.enumeration.PulldownDirectionType
	 * @see TapeDescriptor
	 * @see FilmDescriptor
	 */
	public PulldownDirectionType getPulldownDirection();

	/** 
	 * <p>Sets the pulldown direction of the pulldown, which specifies whether the pulldown 
	 * is converting from tape to film speed or from film to tape speed.</p>
	 * 
	 * @param pulldownDirection Pulldown direction of this pulldown.
	 * 
	 * @throws NullPointerException The given pulldown direction is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.enumeration.PulldownDirectionType
	 * @see TapeDescriptor
	 * @see FilmDescriptor
	 */
	public void setPulldownDirection(
			PulldownDirectionType pulldownDirection)
		throws NullPointerException;

	/**
	 * <p>Returns the phase frame property of this pulldown, which specifies the phase within 
	 * the repeating pulldown pattern of the first frame after the pulldown conversion. A value of 
	 * zero specifies that the pulldown object starts at the beginning of the pattern.</p>
	 * 
	 * @return Phase frame property of this pulldown.
	 */
	public @PhaseFrameType int getPhaseFrame();

	/**
	 * <p>Sets the phase frame property of the pulldown, which specifies the phase within 
	 * the repeating pulldown pattern of the first frame after the pulldown conversion. A value of 
	 * zero specifies that the pulldown object starts at the beginning of the pattern.</p>
	 * 
	 * @param phaseFrame Phase frame property for this pulldown.
	 */
	public void setPhaseFrame(
			@PhaseFrameType int phaseFrame);
	
	/**
	 * <p>Create a cloned copy of this pulldown.</p>
	 *
	 * @return Cloned copy of this pulldown.
	 */
	public Pulldown clone();
}

