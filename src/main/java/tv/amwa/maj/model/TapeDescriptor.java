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
 * $Log: TapeDescriptor.java,v $
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
 * Revision 1.2  2008/02/08 11:27:25  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:15  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.TapeCaseType;
import tv.amwa.maj.enumeration.TapeFormatType;
import tv.amwa.maj.enumeration.VideoSignalType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.AAFString;


/**
 * <p>Specifies the description of audio tape or video tape media. A {@linkplain SourcePackage
 * source package} described by a tape descriptor is known as a <em>tape source package</em>.</p>
 * 
 *
 *
 * @see SourcePackage#setEssenceDescriptor(EssenceDescriptor)
 */

public interface TapeDescriptor	
	extends EssenceDescriptor {	

	/**
	 * <p>Sets the tape manufacturer property of the described tape, which 
	 * provides a text string to display to end users that
	 * identifies the manufacturer of the tape. Set this optional property
	 * to <code>null</code> to omit it.</p>
	 * 
	 * @param tapeManufacturer Tape manufacturer property of the described tape.
	 */
	public void setTapeManufacturer(
			@AAFString String tapeManufacturer);

	/**
	 * <p>Returns the tape manufacturer property of the described tape, which 
	 * provides a text string to display to end users that
	 * identifies the manufacturer of the tape. This is an optional property.</p>
	 * 
	 * @return Tape manufacturer property of the described tape.
	 * 
	 * @throws PropertyNotPresentException The optional tape manufacturer property is not
	 * present in this tape descriptor.
	 */
	public @AAFString String getTapeManufacturer()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the tape formulation (model) property of the described tape, which provides a text 
	 * string to display to end users that identifies the manufacturer's brand designation 
	 * of the tape. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param tapeFormulation Tape model of the described tape.
	 */
	public void setTapeFormulation(
			@AAFString String tapeFormulation);

	/**
	 * <p>Returns the tape formulation (model) property of the described tape, which provides
	 * a text string to display to end users that identifies 
	 * the manufacturer's brand designation of the tape. This is an optional
	 * property.</p>
	 * 
	 * @return Tape model of the described tape.
	 * 
	 * @throws PropertyNotPresentException The optional tape model property is not present
	 * in this tape descriptor.
	 */
	public @AAFString String getTapeFormuulation()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the tape form factor (case size) of the described 
	 * tape. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * <p>Typical values include:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeCaseType#VHSVideoTape}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeCaseType#DATCartridge}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeCaseType#ThreeFourthInchVideoTape}</li>
	 * </ul>
	 * 
	 * @param formFactor Tape form factor (case size) of the described 
	 * tape.
	 */
	public void setTapeFormFactor(
			TapeCaseType formFactor);

	/**
	 * <p>Returns the tape form factor (case size) of the described 
	 * tape. This is an optional property.</p>
	 * 
	 * <p>Typical values include:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeCaseType#VHSVideoTape}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeCaseType#DATCartridge}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeCaseType#ThreeFourthInchVideoTape}</li>
	 * </ul>
	 * 
	 * @return Tape form factor of the described tape.
	 * 
	 * @throws PropertyNotPresentException The optional tape form factor property is not present
	 * in this tape descriptor.
	 */
	public TapeCaseType getTapeFormFactor()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the video signal standard recorded on the described tape. Set this
	 * optional property to <code>null</code> to omit it.</p>
	 *
	 * <p>Typical values includes:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.enumeration.VideoSignalType#NTSCSignal}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.VideoSignalType#PALSignal}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.VideoSignalType#SECAMSignal}</li>
	 * </ul>
	 *
	 * @param videoSignal Signal standard recorded on the described tape.
	 */
	public void setSignalType(
			VideoSignalType videoSignal);

	/**
	 * <p>Returns the video signal standard recorded on the described tape. This is
	 * an optional property.</p>
	 *
	 * <p>Typical values includes:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.enumeration.VideoSignalType#NTSCSignal}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.VideoSignalType#PALSignal}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.VideoSignalType#SECAMSignal}</li>
	 * </ul>
	 * 
	 * @return Signal standard recorded on the described tape.
	 * 
	 * @throws PropertyNotPresentException The optional video signal type property is not
	 * present in this tape descriptor.
	 */
	public VideoSignalType getSignalType()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the recording method used for the described tape. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 * 
	 * <p>Typical values include:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeFormatType#BetacamSPFormat}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeFormatType#SVHSFormat}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeFormatType#_8mmFormat}</li>
	 * </ul>
	 * 
	 * @param tapeFormat Recording method used for the described tape.
	 */
	public void setTapeFormat(
			TapeFormatType tapeFormat);

	/**
	 * <p>Returns the recording method used for the described tape, known as
	 * its format. This is an optional property.</p>
	 * 
	 * <p>Typical values include:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeFormatType#BetacamSPFormat}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeFormatType#SVHSFormat}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.TapeFormatType#_8mmFormat}</li>
	 * </ul>
	 * 
	 * @return Recording method used for the described tape.
	 * 
	 * @throws PropertyNotPresentException The optional tape format property is not
	 * present in this tape descriptor.
	 */
	public TapeFormatType getTapeFormat()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the capacity of the described tape, measured in minutes. Set this 
	 * optional property to <code>null</code> to omit it.<p>
	 * 
	 * @param tapeCapacity Capacity of the described tape measured in minutes.
	 * 
	 * @throws IllegalArgumentException The given tape length is negative.
	 */
	public void setTapeCapacity(
			@UInt32 Integer tapeCapacity)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the capacity of the described tape, measured in minutes. This is an
	 * optional property.<p>
	 * 
	 * @return Capacity of the described tape in minutes. 
	 * 
	 * @throws PropertyNotPresentException The optional tape length property is not
	 * present in this tape descriptor.
	 */
	public @UInt32 int getTapeCapacity()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the tape batch number for the described tape. This is an optional
	 * property.</p>
	 *
	 * @return Tape batch number for the described tape.
	 * 
	 * @throws PropertyNotPresentException The optional tape batch number is not
	 * present in this tape descriptor.
	 */
	public String getTapeBatchNumber()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the tape batch number for the described tape.</p>
	 *
	 * @param tapeBatchNumber Tape batch number for the described tape.
	 */
	public void setTapeBatchNumber(
			@AAFString String tapeBatchNumber);
	
	/**
	 * <p>Returns the string identifying the tape stock for the described tape. This 
	 * is an optional property.</p>
	 *
	 * @return String identifying the tape stock for the described tape.
	 * 
	 * @throws PropertyNotPresentException The optional tape stock property is not present
	 * for this tape descriptor.
	 */
	public String getTapeStock()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the string identifying the tape stock for the described tape. Set this
	 * optional property to <code>null</code> to omit it.</p>
	 *
	 * @param tapeStock String identifying the tape stock for the described tape.
	 */
	public void setTapeStock(
			@AAFString String tapeStock);
	
	/**
	 * <p>Create a cloned copy of this tape descriptor.</p>
	 *
	 * @return Cloned copy of this tape descriptor.
	 */
	public TapeDescriptor clone();
	
}
