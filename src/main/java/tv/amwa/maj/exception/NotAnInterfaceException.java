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
 * $Log: NotAnInterfaceException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:41  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:40  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: Dictionary - create class methods replaced with the factory?

/**
 * <p>Thrown when an interface, represented by a {@link java.lang.Class} instance, to be implemented 
 * by a new class instance is not a Java interface.</p>
 * 
 * <p>No equivalent C result code has been found.</p>
 * 
 * @see tv.amwa.maj.model.Dictionary
 *
 *
 *
 */
public class NotAnInterfaceException 
	extends IllegalArgumentException 
	implements MAJException {

	/**  */
	private static final long serialVersionUID = 2416963845717604608L;

	/**
	 * <p>Create a new not an interface exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public NotAnInterfaceException(String msg) {
		super(msg);
	}

	/**
	 * <p>Create a new not an interface exception with no message.</p>
	 */
	public NotAnInterfaceException() {
		super();
	}
}
