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
 * $Log: BadOpenException.java,v $
 * Revision 1.2  2007/11/27 20:37:50  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:09:55  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: RandomFile - consider removing?

/**
 * <p>Thrown when an attempt is made to perform an operation on a random access file 
 * that is not currently open.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_OPEN_FAILURE 0x80120017</code><br>
 * which is aliased to: <code>AAFRESULT_BADOPEN</code></p>
 *
 *
 *
 */
public class BadOpenException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 6677015644926491405L;

	/**
	 * <p>Create a new bad open exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public BadOpenException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new bad open exception with no message.</p>
	 */
	public BadOpenException() {
		super();
	}

}
