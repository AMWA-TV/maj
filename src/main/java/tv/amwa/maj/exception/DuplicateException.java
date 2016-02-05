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
 * $Log: DuplicateException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:56  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:46  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.exception;

// References: TypeDefinitionExtendibleEnumeration

/** 
 * <p>Thrown when an attempt is made to add an element to a set that is already
 * contained in that set.</p>
 * 
 * <p>No equivalent C result code.</p>
 *  
 * @see tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration
 *
 *
 *
 */
public class DuplicateException 
	extends RuntimeException 
	implements MAJException,
		NewForMAJ {

	/** <p></p> */
	private static final long serialVersionUID = -5287603287141521522L;

	/**
	 * <p>Create a new duplicate exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public DuplicateException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new duplicate exception with no message.</p>
	 */
	public DuplicateException() {
		super();
	}
}
