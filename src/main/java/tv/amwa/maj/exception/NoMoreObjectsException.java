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
 * $Log: NoMoreObjectsException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:38:08  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:35  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: TaggedValueDefinition
// TODO this really is a bad name - can it be improved?

/** 
 * <p>Thrown when a given parent of a {@linkplain tv.amwa.maj.model.TaggedValueDefinition 
 * tagged value definition} is not known and so cannot be removed from its list of parent
 * properties.</p>
 *
 * <p>Equivalent C result code: <code>AAFRESULT_NO_MORE_OBJECTS 0x80120111</code></p>
 *
 *
 *
 */
public class NoMoreObjectsException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 3242662992565694471L;

	/**
	 * <p>Create a new no more objects exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public NoMoreObjectsException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new no more objects exception with no message.</p>
	 */
	public NoMoreObjectsException() {
		super();
	}
}
