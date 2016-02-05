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
 * $Log: EndOfDataException.java,v $
 * Revision 1.3  2010/11/08 15:55:44  vizigoth
 * Stream exceptions are now runtime exceptions.
 *
 * Revision 1.2  2007/11/27 20:37:46  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: PCMDescriptor, EssenceAccess

/** 
 * <p>Thrown when an attempt is made to read beyond the end of a data stream.</p>
 *
 * <p>Equivalent C result code: <code>AAFRESULT_END_OF_DATA 0x801200CE</code></p>
 * 
 *
 *
 */
public class EndOfDataException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 6398014762309083511L;

	/**
	 * <p>Create a new end of data exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public EndOfDataException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new end of data exception with no message.</p>
	 */
	public EndOfDataException() {
		super();
	}
}
