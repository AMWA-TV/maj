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
 * $Log: BadParameterException.java,v $
 * Revision 1.2  2007/11/27 20:37:57  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:48  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

/** 
 * <p>Thrown when a lookup operation fails because the given parameter cannot be
 * found in a known collection of values.</p>
 * 
 * <p>Note that this exception can be traced back to AAF SDK documentation only
 * and does not appear to have a result code.</p>
 *
 *
 */
public class BadParameterException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -5759219330793904281L;

	/**
	 * <p>Create a new bad parameter exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public BadParameterException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new bad parameter exception with no message.</p>
	 */
	public BadParameterException() {
		super();
	}
}
