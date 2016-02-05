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
 * $Log: AlreadyOpenException.java,v $
 * Revision 1.2  2007/11/27 20:37:58  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:50  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: AAFFile - consider removing?

/** 
 * <p>Thrown when an attempt is made to open an AAF file that is already open.</p>
 *
 * <p>Equivalent C result code: <code>AAFRESULT_ALREADY_OPEN 0x8012002A</code></p>
 * 
 *
 */
public class AlreadyOpenException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -7116768573512684745L;

}
