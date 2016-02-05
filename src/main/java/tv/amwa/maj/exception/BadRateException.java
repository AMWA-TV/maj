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
 * $Log: BadRateException.java,v $
 * Revision 1.5  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2007/12/12 12:49:54  vizigoth
 * Minor edit to comment.
 *
 * Revision 1.2  2007/11/27 20:37:46  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:41  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// Reference: SourcePackage

/** 
 * <p>Thrown if an unacceptable edit rate is set for a {@linkplain tv.amwa.maj.model.SourcePackage
 * source package's} track.</p>
 *
 * <p>Equivalent C result code: <code>AAFRESULT_BADRATE 0x801200FD</code></p>
 *
 *
 *
 */
public class BadRateException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 8645002748083539115L;

	/**
	 * <p>Create a new bad rate exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public BadRateException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new bad rate exception with no message.</p>
	 */
	public BadRateException() {
		super();
	}
	
}
