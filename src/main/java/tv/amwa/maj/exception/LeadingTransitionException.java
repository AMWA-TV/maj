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
 * $Log: LeadingTransitionException.java,v $
 * Revision 1.4  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:37:56  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:01  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: Package, Segment, Sequence, SourcePackage

/** 
 * <p>Thrown when the result of an operation would make the first 
 * {@linkplain tv.amwa.maj.model.Component component} of a 
 * {@linkplain tv.amwa.maj.model.Sequence sequence} a 
 * {@linkplain tv.amwa.maj.model.Transition transition}. It is an illegal state
 * for a {@linkplain tv.amwa.maj.model.Sequence sequence} to start with a 
 * {@linkplain tv.amwa.maj.model.Transition transition}.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_LEADING_TRAN 0x8012011A</code></p>
 * 
 * @see tv.amwa.maj.model.Sequence
 *
 *
 *
 */
public class LeadingTransitionException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 4780143044337485376L;

	/**
	 * <p>Create a new leading transition exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public LeadingTransitionException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new leading transition exception with no message.</p>
	 */
	public LeadingTransitionException() {
		super();
	}
	
}
