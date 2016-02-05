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
 * $Log: PositionOutOfRangeException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:52  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:11:12  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: TimecodeStream, RIFFChunk

/** 
 * <p>Thrown when a given position, specified by frame count or 
 * {@linkplain tv.amwa.maj.record.TimecodeValue timecode}, is outside the 
 * the length of the referenced stream.</p>
 * 
 * <p>No equivalent C result code could be found.</p>
 *
 * @see tv.amwa.maj.misctype.PositionType
 * @see tv.amwa.maj.model.TimecodeStream
 * @see tv.amwa.maj.model.RIFFChunk
 *
 *
 *
 */
public class PositionOutOfRangeException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 70955515534684374L;

	/**
	 * <p>Create a new position out of range exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public PositionOutOfRangeException(
			String msg) {

		super(msg);
	}
	
	/**
	 * <p>Create a new position out of range exception with no message.</p>
	 */
	public PositionOutOfRangeException() {
		super();
	}
}
