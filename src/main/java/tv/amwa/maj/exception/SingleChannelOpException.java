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
 * $Log: SingleChannelOpException.java,v $
 * Revision 1.2  2007/11/27 20:37:44  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:11:09  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: EssenceAccess - consider removing?

/** 
 * <p>Thrown when an attempt is made to write samples into an essence stream
 * that contains interleaved data.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_SINGLE_CHANNEL_OP 0x80120081</code></p>
 *
 *
 *
 */
public class SingleChannelOpException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 7990766263719886525L;

	/**
	 * <p>Create a new single channel operation exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public SingleChannelOpException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new single channel operation exception with no message.</p>
	 */
	public SingleChannelOpException() {
		super();
	}
}
