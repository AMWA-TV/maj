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
 * $Log: WrongOpenModeException.java,v $
 * Revision 1.2  2007/11/27 20:37:59  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:23  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO Thrown from AAF File ... could consider removing?

/** 
 * <p>Thrown when an attempt is made to save data to a file that was not opened
 * for writing or other modification.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_WRONG_OPENMODE 0x8012001F</code></p>
 *
 *
 *
 */
public class WrongOpenModeException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -6497399823876627501L;

	/**
	 * <p>Create a new wrong open mode exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public WrongOpenModeException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new wrong open mode exception with no message.</p>
	 */
	public WrongOpenModeException() {
		super();
	}
}
