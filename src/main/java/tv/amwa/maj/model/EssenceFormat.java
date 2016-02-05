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
 * $Log: EssenceFormat.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/01/27 11:07:39  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:09:03  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.record.AUID;

// TODO is this code dead wood? (as implied by aafdefuids.h)

/**
 * <p>Specifies a collection of parameters (such as image height/width, audio sample 
 * width) which describes a piece of essence. Each parameter is specified 
 * by an {@linkplain tv.amwa.maj.record.AUID AUID}, and contains a variable length piece of data.</p>
 * 
 * <p>When creating a piece of essence, you should call 
 * {@link #addFormatSpecifier(AUID, byte[])} for each required or known 
 * parameter, and the codec will supply defaults for other optional 
 * parameters.</p>
 * 
 * <p>Note that the current version of the MAJ API has no built-in codec support, large 
 * essence is not supported and the methods of this interface do nothing.</p>
 * 
 *
 *
 * @see EssenceAccess
 */

public interface EssenceFormat {

	/**
	 * <p>Appends a format specifier to the essence format.  If a particular
	 * essence format code has already been added, then this call
	 * replaces the value, otherwise the value is added.</p>
	 * 
	 * @param essenceFormatCode Essence format code (as defined in 
	 * "<code>aafdefuids.h</code>").
	 * @param value Data value for format specifier.
	 * 
	 * @throws NullPointerException One or more of the arguments is null.
	 */
	public void addFormatSpecifier(
			AUID essenceFormatCode,
			@DataBuffer byte[] value) 
		throws NullPointerException;

	/**
	 * <p>Data value representing the essence format of the given
	 * essence format code.</p>
	 * 
	 * @param essenceFormatCode Essence format code (as defined in 
	 * "<code>aafdefuids.h</code>").
	 * @return Corresponding data value.
	 * 
	 * @throws NullPointerException Argument is null.
	 */
	public @DataBuffer byte[] getFormatSpecifier(
			AUID essenceFormatCode) 
		throws NullPointerException;

	/**
	 * <p>Returns the number of specifiers present in this essence 
	 * format.</p>
	 * 
	 * @return Number of specifiers in this essence format.
	 */ 
	public @UInt32 int numSpecifiers();

	/**
	 * <p>Returns the data value at the given index.</p>
	 *  
	 * @param index 0-based index for the format specifier to retrieve.
	 * @return Data value of the format specifier.
	 * 
	 * @throws IndexOutOfBoundsException The specified index is
	 * outside the acceptable range for the current number of specifiers.
	 */
	public @DataBuffer byte[] getIndexedFormatSpecifier(
			@Int32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the essence format code associated with the format
	 * specifier at the given index.</p>
	 *  
	 * @param index 0-based index for the format specifier to retrieve.
	 * @return Corresponding essence format code.
	 * 
	 * @throws IndexOutOfBoundsException The specified index is
	 * outside the acceptable range for the current number of specifiers.
	 */
	public AUID getIndexedEssenceFormatCode(
			@Int32 int index)
		throws IndexOutOfBoundsException;
}


