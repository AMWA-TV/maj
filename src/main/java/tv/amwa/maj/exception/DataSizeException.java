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
 * $Log: DataSizeException.java,v $
 * Revision 1.2  2007/11/27 20:37:57  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:05  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO Reference: SetFileBits, TypeDefinitionStream - consider removing?

/** 
 * <p>Thrown when it has not been possible to allocate the requested or necessary
 * amount of space for data storage.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_DATA_SIZE 0x80120169</code></p>
 *
 *
 *
 */
public class DataSizeException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 388681042827815938L;

	/**
	 * <p>Create a new data size exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public DataSizeException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new data size exception with no message.</p>
	 */
	public DataSizeException() {
		super();
	}
}
