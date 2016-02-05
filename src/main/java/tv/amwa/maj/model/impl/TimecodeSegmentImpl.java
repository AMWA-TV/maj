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
 * $Log: TimecodeSegmentImpl.java,v $
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
 * Revision 1.1  2007/11/13 22:09:54  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.BadSampleOffsetException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.TimecodeNotFoundException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.model.TimecodeSegment;
import tv.amwa.maj.record.TimecodeValue;
import tv.amwa.maj.record.impl.TimecodeValueImpl;


/** 
 * <p>Implements the storage of video tape or audio tape timecode information.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1400,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Timecode",
		  description = "The Timecode class stores videotape or audio tape timecode information.",
		  symbol = "Timecode")
public class TimecodeSegmentImpl
	extends 
		SegmentImpl
	implements 
		TimecodeSegment,
		tv.amwa.maj.extensions.quantel.QTimecode,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 5112267590769722595L;

	private TimecodeValue timecode;

	public TimecodeSegmentImpl() { }

	/**
	 * <p>Creates and initializes a new timecode segment class, which stores videotape or 
	 * audio tape timecode information.</p>
	 *
	 * @param length The length of this component.
	 * @param timecode Timecode value of the new timecode segment.
	 * 
	 * @throws NullPointerException The timecode value is <code>null</code>.
	 * @throws BadLengthException Cannot set the length of a component to a negative value.
	 */
	public TimecodeSegmentImpl(
			long length,
			TimecodeValue timecode)
		throws NullPointerException,
			BadLengthException {
		
		if (timecode == null)
			throw new NullPointerException("Cannot create a timecode segment with a null timecode value.");
		
		// TODO should this be Timecode or LegacyTimecode, or free choice
		setComponentDataDefinition(DataDefinitionImpl.forIdentification(DataDefinitionImpl.Timecode));
		setLengthPresent(true);
		setComponentLength(length);
		setTimecode(timecode);
	}

	public TimecodeValue getTimecode() {

		return timecode.clone();
	}

	public void setTimecode(
			TimecodeValue timecode)
		throws NullPointerException {

		if (timecode == null)
			throw new NullPointerException("Cannot set the timecode value of this timecode segment using a null value.");
		
		this.timecode = timecode.clone();
	}

	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x0105, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "StartTimecode",
			aliases = { "Start", "TimecodeStart" },
			typeName = "PositionType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1501,
			symbol = "StartTimecode")
	public long getStartTimecode() {
		
		return timecode.getStartTimecode();
	}

	@MediaPropertySetter("StartTimecode")
	public void setStartTimecode(
			long startFrame) {
		
		if (timecode == null)
			timecode = new TimecodeValueImpl();
		
		timecode.setStartTimecode(startFrame);
	}
	
	public final static long initializeStartTimecode() {
		
		return 0l;
	}
	
	@MediaProperty(uuid1 = 0x04040101, uuid2 = (short) 0x0206, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FramesPerSecond",
			aliases = { "FPS" },
			typeName = "UInt16", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1502,
			symbol = "FramesPerSecond")
	public short getFramesPerSecond() {
		
		return timecode.getFramesPerSecond();
	}
	
	@MediaPropertySetter("FramesPerSecond")
	public void setFPS(
			short fps) 
		throws IllegalArgumentException {
		
		if (fps < 0)
			throw new IllegalArgumentException("Cannot set the number of frames per second to a negative value.");
		
		if (timecode == null)
			timecode = new TimecodeValueImpl();
		
		timecode.setFramesPerSecond(fps);
	}

	public final static short initializeFramesPerSecond() {
		
		return 1;
	}
	
	@MediaProperty(uuid1 = 0x04040101, uuid2 = (short) 0x0500, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "DropFrame",
			aliases = { "Drop" },
			typeName = "Boolean",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1503,
			symbol = "DropFrame")
	public boolean getDropFrame() {
		
		return timecode.getDropFrame();
	}
	
	@MediaPropertySetter("DropFrame")
	public void setDropFrame(
			boolean dropFrame) {
		
		if (timecode == null)
			timecode = new TimecodeValueImpl();
		
		timecode.setDropFrame(dropFrame);
	}
	
	// Begin - Quantel extensions
	
	private Integer timecodeModifiedFlag = null;
	
    @MediaProperty(uuid1 = 0x6d158b73, uuid2 = (short) 0x0d46, uuid3 = (short) 0x4569,
        uuid4 = { (byte) 0x9d, (byte) 0x58, (byte) 0x08, (byte) 0x5f, (byte) 0x45, (byte) 0xf8, (byte) 0x32, (byte) 0x75 },
        definedName = "Timecode modified flag",
        symbol = "Timecode_modified_flag",
        aliases = { "Timecode_modified_flag" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getTimecodeModifiedFlag()
		throws PropertyNotPresentException {
		
		if (timecodeModifiedFlag == null)
			throw new PropertyNotPresentException("The optional timecode modified flag property is not present for this Quantel timecode segment.");
		
		return timecodeModifiedFlag;
	}
	
	@MediaPropertySetter("Timecode modified flag")
	public void setTimecodeModifiedFlag(
			@Int32 Integer timecodeModifiedFlag) {
		
		this.timecodeModifiedFlag = timecodeModifiedFlag;
	}
	
	// End - Quantel extensions
	
	public final static boolean initializeDropFrame() {
		
		return false;
	}
	
	public TimecodeValue segmentOffsetToTC(
			long offset) {
		
		TimecodeValue offsetTimecode = timecode.clone();
		offsetTimecode.setStartTimecode(offsetTimecode.getStartTimecode() + offset);
		
		return offsetTimecode;
	}

	public long segmentTCToOffset(
			tv.amwa.maj.record.TimecodeValue timecode,
			tv.amwa.maj.record.Rational editrate) 
		throws NullPointerException,
			TimecodeNotFoundException,
			BadSampleOffsetException {
		
		if (timecode == null)
			throw new NullPointerException("Cannot calculate an offset value for this timecode segment using a null timecode value.");
		if (editrate == null)
			throw new NullPointerException("Cannot calculate an offset value for this timecode segment using a null edit rate value.");
		
		try {	
			if (timecode.getStartTimecode() < this.timecode.getStartTimecode())
				throw new BadSampleOffsetException("The given timecode value is outside of lower bound of this timecode segment.");
			
			if (timecode.getStartTimecode() > (this.timecode.getStartTimecode() + getComponentLength()))
				throw new BadSampleOffsetException("The given timecode value is outside of the upper bound of this timecode segment.");
		}
		catch (BadPropertyException bpe) {
			throw new BadSampleOffsetException("This component does not have a length property present, preventing the calculation of the offset value.");
		}
		
		return timecode.getStartTimecode() - this.timecode.getStartTimecode();
	}
	
	public TimecodeSegment clone() {
		
		return (TimecodeSegment) super.clone();
	}
}
