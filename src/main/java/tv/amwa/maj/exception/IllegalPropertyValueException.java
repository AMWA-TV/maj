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
 * $Log: IllegalPropertyValueException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.1  2007/11/27 20:38:04  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:08  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: All type definitions and digital image descriptor

/** 
 * <p>Thrown when a parameter to a method call of an object is outside the
 * acceptable range or of the wrong type for that object in its current state. 
 * This exception can be thrown
 * for all {@linkplain tv.amwa.maj.meta.PropertyDefinition property definitions}
 * passed to methods of a {@linkplain tv.amwa.maj.meta.TypeDefinition type definition}
 * if the type of the property does not match the defined type.</p>
 *
 * <p>Equivalent C result code: <code>AAFRESULT_ILLEGAL_VALUE 0x8012016A</code></p>
 *
 * @see tv.amwa.maj.model.PictureDescriptor
 *
 *
 *
 */
public class IllegalPropertyValueException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -7511766518884649301L;

	/**
	 * <p>Create a new illegal value exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public IllegalPropertyValueException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new illegal value exception with no message.</p>
	 */
	public IllegalPropertyValueException() {
		super();
	}	
}
