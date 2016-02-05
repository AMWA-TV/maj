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
 * $Log: TimecodeNotFoundException.java,v $
 * Revision 1.4  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:38:07  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:11  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: package, Pulldown, Segment, TimecodeSegment, TimecodeStream

/** 
 * <p>Thrown when a given timecode or offset is not available within a 
 * {@linkplain tv.amwa.maj.model.Segment segment} or {@linkplain tv.amwa.maj.model.Package
 * package}.</p>
 *
 * <p>Equivalent C result code: <code>AAFRESULT_TIMECODE_NOT_FOUND 0x801200FF</code></p>
 *
 *
 *
 */
public class TimecodeNotFoundException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 1376432604873437294L;

	/**
	 * <p>Create a new timecode not found exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public TimecodeNotFoundException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new timecode not found exception with no message.</p>
	 */	
	public TimecodeNotFoundException() {
		super();
	}
}
