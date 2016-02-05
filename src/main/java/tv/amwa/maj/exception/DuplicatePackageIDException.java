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
 * $Log: DuplicatePackageIDException.java,v $
 * Revision 1.2  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:37:50  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:33  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: ContentStorage, Preface

/** 
 * <p>Thrown when an attempt is made to add essence or metadata to 
 * {@linkplain tv.amwa.maj.model.ContentStorage content storage} when information
 * with the same {@linkplain tv.amwa.maj.record.PackageID package id} is already stored.</p>
 *
 * <p>Equivalent C result code: <code>AAFRESULT_DUPLICATE_MOBID 0x80120108</code></p>
 *
 * @see tv.amwa.maj.model.ContentStorage
 * @see tv.amwa.maj.model.Preface
 *
 *
 *
 */
public class DuplicatePackageIDException 
	extends DuplicateException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 7582319426223238933L;

	/**
	 * <p>Create a new duplicate package id exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public DuplicatePackageIDException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new duplicate package id exception with no message.</p>
	 */
	public DuplicatePackageIDException() {
		super();
	}
}
