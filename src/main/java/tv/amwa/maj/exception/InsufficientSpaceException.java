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
 * $Log: InsufficientSpaceException.java,v $
 * Revision 1.3  2009/12/18 17:56:03  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.2  2007/11/27 20:37:59  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:09:57  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: AAFFile - consider removing?

/** 
 * <p>Thrown when not enough space is available on the filing system or in a given
 * buffer to save data.</p>
 * 
 * <p>No equivalent C result code.</p>
 *
 *
 *
 */
public class InsufficientSpaceException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -1741561437252529119L;

	/**
	 * <p>Create a new insufficient space exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public InsufficientSpaceException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new insufficient space exception with no message.</p>
	 */
	public InsufficientSpaceException() {
		super();
	}
}
