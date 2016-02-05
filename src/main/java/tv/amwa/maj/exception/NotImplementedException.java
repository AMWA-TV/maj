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
 * $Log: NotImplementedException.java,v $
 * Revision 1.2  2011/01/21 12:36:03  vizigoth
 * Better as a runtime exception.
 *
 * Revision 1.1  2008/01/21 12:58:02  vizigoth
 * Added new not implemented exception to handle creatInstance() methods that do not fit with the MAJ API.
 *
 *
 */

package tv.amwa.maj.exception;

/**
 * <p>Thrown when a method is called that is not implemented in the MAJ API. The 
 * method will have been provided to help with the porting of applications from the
 * existing reference implementation and the documentation of the method will
 * provide a suitable alternative.</p>
 * 
 *
 *
 */
public class NotImplementedException 
	extends RuntimeException 
	implements MAJException,
		NewForMAJ {

	private static final long serialVersionUID = 6630951533799908358L;

	/**
	 * <p>Create a new not implemented exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public NotImplementedException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new not implemented exception with no message.</p>
	 */
	public NotImplementedException() {
		super();
	}

	
}
