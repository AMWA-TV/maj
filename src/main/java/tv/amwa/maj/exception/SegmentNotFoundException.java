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
 * $Log: SegmentNotFoundException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:52  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:11:18  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: Selector

/** 
 * <p>Thrown when a given {@linkplain tv.amwa.maj.model.Segment segment} cannot be found
 * within the segments of a {@linkplain tv.amwa.maj.model.Selector selector}.</p>
 *
 * <p>Equivalent C result code: <code>AAFRESULT_SEGMENT_NOT_FOUND 0x80120128</code></p>
 * 
 * @see tv.amwa.maj.model.Selector
 *
 *
 *
 */
public class SegmentNotFoundException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -3724421275807897325L;

	/**
	 * <p>Create a new segment not found exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public SegmentNotFoundException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new segment not found exception with no message.</p>
	 */
	public SegmentNotFoundException() {
		super();
	}
}
