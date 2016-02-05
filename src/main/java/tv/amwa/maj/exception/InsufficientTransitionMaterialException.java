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
 * $Log: InsufficientTransitionMaterialException.java,v $
 * Revision 1.5  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2007/12/12 12:49:54  vizigoth
 * Minor edit to comment.
 *
 * Revision 1.2  2007/11/27 20:37:59  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:11:20  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: package, Segment, Sequence, SourcePackage

/**
 * <p>Thrown when appending or inserting a given {@linkplain tv.amwa.maj.model.Component component}
 * to a {@linkplain tv.amwa.maj.model.Sequence sequence} will result in insufficient material for 
 * the adjacent {@linkplain tv.amwa.maj.model.Transition transition}.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_INSUFF_TRAN_MATERIAL 0x8012011B</code></p>
 * 
 * @see tv.amwa.maj.model.Sequence
 *
 *
 *
 */
public class InsufficientTransitionMaterialException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -7048021275304899831L;

	/**
	 * <p>Create a new insufficient transition material exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public InsufficientTransitionMaterialException(String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new insufficient transition material exception with no message.</p>
	 */
	public InsufficientTransitionMaterialException() {
		super();
	}
}
