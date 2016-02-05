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
 * $Log: OffsetSizeException.java,v $
 * Revision 1.2  2007/11/27 20:37:53  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:21  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: GetFileBits and RandomRawStorage - condider removing?

/** 
 * <p>Thrown when a given offset exceeds the size of a file or the available
 * space to extend storage for a file.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_OFFSET_SIZE 0x8012015F</code></p>
 *
 *
 *
 */
public class OffsetSizeException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -318441014772177192L;

	/**
	 * <p>Create a new offset size exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public OffsetSizeException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new offset size exception with no message.</p>
	 */
	public OffsetSizeException() {
		super();
	}
	
}
