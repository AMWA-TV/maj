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
 * $Log: DuplicateEssenceKindException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:58  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:34  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.exception;

// References: CodecDefinition

/** 
 * <p>Thrown when an attempt is made to add an essence kind to that supported by a 
 * {@linkplain tv.amwa.maj.model.CodecDefinition codec definition} that is already
 * listed as supported by that codec.</p>
 * 
 * <p>No equivalent C result code.</p>
 *
 *
 *
 */
public class DuplicateEssenceKindException 
	extends DuplicateException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -1291509081658762914L;

	/**
	 * <p>Create a new duplicate essence kind exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public DuplicateEssenceKindException(
			String msg) {
	
		super(msg);
	}

	/**
	 * <p>Create a new duplicate essence kind exception with no message.</p>
	 */
	public DuplicateEssenceKindException() {
		super();
	}
}
