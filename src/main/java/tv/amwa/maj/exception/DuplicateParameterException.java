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
 * $Log: DuplicateParameterException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:38:09  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:38  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: OperationGroup

/** 
 * <p>Thrown when an attempt is made to add a parameter to an 
 * {@linkplain tv.amwa.maj.model.OperationGroup operation group} that is already
 * a parameter for that group.</p>
 * 
 * <p>No equivalent C result code.</p>
 * 
 * @see tv.amwa.maj.model.OperationGroup
 *
 *
 *
 */
public class DuplicateParameterException 
	extends DuplicateException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -424037717913876243L;

	/**
	 * <p>Create a new duplicate parameter exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public DuplicateParameterException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new duplicate parameter exception with no message.</p>
	 */
	public DuplicateParameterException() {
		super();
	}
}
