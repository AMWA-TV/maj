/*
 * Copyright 2016 Advanced Media Workflow Assocation
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
 * $Log: TraversalNotPossibleException.java,v $
 * Revision 1.4  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:37:57  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:48  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: Component, EssenceGroup, Package, Track, Selector, SourcePackage, TimelineTrack
// but really just to do with SearchSounce, so consider removing?

/** 
 * <p>Thrown when a search operation cannot proceed.</p>
 * 
 * <p>An equivalent C result code could not be found.</p>
 * 
 * @see tv.amwa.maj.model.SearchSource
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class TraversalNotPossibleException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 2518206830971688494L;

	/**
	 * <p>Create a new traversal not possible exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public TraversalNotPossibleException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new traversal not possible exception with no message.</p>
	 */
	public TraversalNotPossibleException() {
		super();
	}
}
