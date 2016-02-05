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
 * $Log: ObjectAlreadyAttachedException.java,v $
 * Revision 1.2  2007/11/27 20:37:47  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:09:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: OperationDefinition

/** 
 * <p>Thrown when an attempt is made to attach an item to a collection that it
 * already contains.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_OBJECT_ALREADY_ATTACHED 0x80120132</code></p>
 *
 *
 *
 */
public class ObjectAlreadyAttachedException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -286152952571365461L;

	/**
	 * <p>Create a new object already attached exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */	
	public ObjectAlreadyAttachedException(
			String msg) {

		super(msg);
	}
	
	/**
	 * <p>Create a new object already attached exception with no message.</p>
	 */	
	public ObjectAlreadyAttachedException() {
		super();
	}

}
