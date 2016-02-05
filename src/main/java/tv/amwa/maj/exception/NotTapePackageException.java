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
 * $Log: NotTapePackageException.java,v $
 * Revision 1.2  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:38:07  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:09:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: MaterialPackage - consider removing?

/** 
 * <p>Thrown when a request is made to find the name of a tape associated with a 
 * {@linkplain tv.amwa.maj.model.MaterialPackage material package} which is not describing tape-based
 * material</p>
 * 
 * <p>No equivalent C result code has been found.</p>
 * 
 * @see tv.amwa.maj.model.MaterialPackage#getTapeName(int)
 *
 *
 *
 */
public class NotTapePackageException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 6712111922240415032L;

	/**
	 * <p>Create a new not a tape package exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public NotTapePackageException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new not a tape package exception with no message.</p>
	 */
	public NotTapePackageException() {
		super();
	}
}
