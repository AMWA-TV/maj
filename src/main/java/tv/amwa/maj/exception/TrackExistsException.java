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
 * $Log: TrackExistsException.java,v $
 * Revision 1.2  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:37:43  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:09:55  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: MaterialPackage - may remove after new approach to material package methods is designed

/** 
 * <p>Thrown when an attempt is made to add a track to a {@linkplain tv.amwa.maj.model.Package
 * package} where the given {@linkplain tv.amwa.maj.misctype.TrackID track id} already exists.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_SLOT_EXISTS 0x80120105</code></p>
 *
 *
 * 
 * @see tv.amwa.maj.misctype.TrackID
 *
 */
public class TrackExistsException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 793355247886225031L;

	/**
	 * <p>Create a new track exists exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public TrackExistsException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new track exists exception with no message.</p>
	 */
	public TrackExistsException() {
		super();
	}
}
