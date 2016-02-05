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
 * $Log: BadCloseException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:55  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:11:13  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.exception;

// TODO References: RandomFile - consider removing?

/** 
 * <p>Thrown when an attempt is made to open a file that has not yet been closed.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_BADCLOSE 0x80120018</code></p>
 * 
 * @see tv.amwa.maj.model.RandomFile
 *
 *
 *
 */
public class BadCloseException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -5107563737449721518L;
	
	/**
	 * <p>Create a new bad file close exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public BadCloseException(
			String msg) {
		super(msg);
	}

	/**
	 * <p>Create a new bad file close exception with no message.</p>
	 */
	public BadCloseException() {
		super();
	}
	
}
