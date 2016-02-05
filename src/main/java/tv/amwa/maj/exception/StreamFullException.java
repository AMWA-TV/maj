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
 * $Log: StreamFullException.java,v $
 * Revision 1.2  2007/11/27 20:37:54  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:11:06  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: EssenceAccess and RawStorage - consider removing?

/** 
 * <p>Thrown when a stream has insufficient capacity to complete the requested
 * write operation.</p>
 * 
 * <p>No equivalent C result code could be found.</p>
 *
 *
 *
 */
public class StreamFullException 
	extends Exception
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 1799413170524329203L;

	/**
	 * <p>Create a new stream full exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public StreamFullException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new stream full exception with no message.</p>
	 */
	public StreamFullException() {
		super();
	}
	
}
