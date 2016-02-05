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
 * $Log: EssenceMultiAccess.java,v $
 * Revision 1.3  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/03/30 09:05:08  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.1  2007/11/13 22:17:23  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:32  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.io.file;

import java.io.Serializable;

import tv.amwa.maj.exception.InvalidParameterException;
// import tv.amwa.maj.meta.AAFClass;


/** TODO implementation, comments and tests
 * <p></p>
 *
 *
 *
 */

//@AAFClass(uuid1 = 0xd815e2b4, uuid2 = 0x2425, uuid3 = 0x11d3,
//		  uuid4 = { (byte) 0x80, (byte) 0xad, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f},
//		  definedName = "EssenceMultiAccess",
//		  description = "The EssenceMultiAccess class provides provides streaming access over essence data.",
//		  symbol = "EssenceMultiAccess") // Not an AAF class
// @Entity
public class EssenceMultiAccess
		implements
			tv.amwa.maj.model.EssenceMultiAccess,
			Serializable {

	/** <p></p> */
	private static final long serialVersionUID = -5175175196136811893L;

	// TODO private fields here

	/** Default constructor is not public to avoid unset required fields. */
	EssenceMultiAccess() {
	}

	// TODO public constructor here - assuming not publically makeable - use material package as factory

	/**
	 * <p>Cast a {@link tv.amwa.maj.model.EssenceMultiAccess EssenceMultiAccess} 
	 * value from the generic interface to this implementation of
	 * the interface. If the given value is not a native implementation, a copy will be
	 * instanciated using get and set methods.</p>
	 *
	 * @param alien A potentially alien implementation of an instance of the EssenceMultiAccess 
	 * interface.
	 * @return Instance of this class that is equal to the given value and that is 
	 * an instance of this concrete implementation.
	 *
	 * @throws NullPointerException Argument is null.
	 */
	public final static EssenceMultiAccess castFromInterface(
			tv.amwa.maj.model.EssenceMultiAccess alien)
			throws NullPointerException {

		if (alien == null)
			throw new NullPointerException(
					"Cannot cast to EssenceMultiAccess from a null value.");

		if (alien instanceof EssenceMultiAccess)
			return (EssenceMultiAccess) alien;

		EssenceMultiAccess castValue = new EssenceMultiAccess();
		// TODO Complete cast

		return castValue;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceMultiAccess#readMultiSamples(tv.amwa.maj.union.MultiXferItem[])
	 */
	public tv.amwa.maj.union.MultiResultItem[] readMultiSamples(
			tv.amwa.maj.union.MultiXferItem[] xferArray)
			throws NullPointerException,
				InvalidParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceMultiAccess#writeMultiSamples(tv.amwa.maj.union.MultiXferItem[])
	 */
	public tv.amwa.maj.union.MultiResultItem[] writeMultiSamples(
			tv.amwa.maj.union.MultiXferItem[] xferArray)
			throws NullPointerException,
				InvalidParameterException {
		// TODO Auto-generated method stub
		return null;
	}
}
