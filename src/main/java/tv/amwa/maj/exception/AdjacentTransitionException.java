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
 * $Log: AdjacentTransitionException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:49  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:30  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: Sequence and associated

/** 
 * <p>Thrown when an operation on a {@linkplain tv.amwa.maj.model.Sequence sequence} would
 * result in two adjacent transitions within that sequence. This invalidates the constraint
 * that transitions must be preceded and followed by a {@linkplain tv.amwa.maj.model.Segment
 * segment} within a sequence.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_ADJACENT_TRAN 0x80120119</code></p>
 * 
 * @see tv.amwa.maj.model.Sequence
 * @see tv.amwa.maj.model.Transition
 *
 *
 */
public class AdjacentTransitionException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 1761151362058113443L;

	/**
	 * <p>Create a new adjacent transition exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public AdjacentTransitionException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new adjacent transition exception with no message.</p>
	 */
	public AdjacentTransitionException() {
		super();
	}
}
