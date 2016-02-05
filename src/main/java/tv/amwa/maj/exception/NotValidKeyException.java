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
 * $Log: NotValidKeyException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:54  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:00  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: AAFFile, RandomFile, RandomRawStorage, RawStorage - consider removing?

/** 
 * <p>Thrown when creating a {@linkplain tv.amwa.maj.model.KLVData KLV data} key that does not 
 * map to a built-in type.</p>
 * 
 * <p>No equivalent C result code could be found.</p>
 *
 *
 *
 */
public class NotValidKeyException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -4640956702209886708L;

	/**
	 * <p>Create a new not a valid key exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public NotValidKeyException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new not a valid key exception with no message.</p>
	 */
	public NotValidKeyException() {
		super();
	}
}
