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
 * $Log: NotReadableException.java,v $
 * Revision 1.2  2007/11/27 20:37:43  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:28  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: RandomFile, RandomRawStorage, RawStorage - consider removing?

/** 
 * <p>Thrown when a raw stream is not open and/or available for reading.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_NOT_READABLE 0x80120027</code></p>
 *
 *
 *
 */
public class NotReadableException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -2733709396503137724L;

	/**
	 * <p>Create a new not readable exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public NotReadableException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new not readable exception with no message.</p>
	 */
	public NotReadableException() {
		super();
	}
}
