/* 
 **********************************************************************
 *
 * $Id: EssenceFormat.java,v 1.4 2011/01/04 10:40:23 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: EssenceFormat.java,v $
 * Revision 1.4  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:39  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2009/03/30 09:05:08  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.1  2007/11/13 22:17:22  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:32  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.io.file;

import java.io.Serializable;

import tv.amwa.maj.record.impl.AUIDImpl;


/** TODO implementation, comments and tests
 * <p></p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */

//@AAFClass(uuid1 = 0x34C2DC82, uuid2 = (short) 0x904C, uuid3 = 0x11d2,
//		  uuid4 = { (byte) 0x80, (byte) 0x88, 0x00, 0x60, 0x08, 0x14, 0x3E, 0x6F},
//		  definedName = "EssenceFormat",
//		  description = "The EssenceFormat class represents a collection of parameters (such as image height/width, audio sample width) which describes a piece of essence.",
//		  symbol = "EssenceFormat") Not an AAF class?
// @Entity
public class EssenceFormat
		implements
			tv.amwa.maj.model.EssenceFormat,
			Serializable {

	/** <p></p> */
	private static final long serialVersionUID = -445380010515357003L;

	// TODO private fields here

	/**
	 * <p>Creates and initializes an empty essence format list.</p>
	 */
	public EssenceFormat() {
		
		// TODO constructor
	}

	/**
	 * <p>Cast a {@link tv.amwa.maj.model.EssenceFormat EssenceFormat} 
	 * value from the generic interface to this implementation of
	 * the interface. If the given value is not a native implementation, a copy will be
	 * instanciated using get and set methods.</p>
	 *
	 * @param alien A potentially alien implementation of an instance of the EssenceFormat 
	 * interface.
	 * @return Instance of this class that is equal to the given value and that is 
	 * an instance of this concrete implementation.
	 *
	 * @throws NullPointerException Argument is null.
	 */
	public final static EssenceFormat castFromInterface(
			tv.amwa.maj.model.EssenceFormat alien)
			throws NullPointerException {

		if (alien == null)
			throw new NullPointerException(
					"Cannot cast to EssenceFormat from a null value.");

		if (alien instanceof EssenceFormat)
			return (EssenceFormat) alien;

		EssenceFormat castValue = new EssenceFormat();
		// TODO Complete cast

		return castValue;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceFormat#addFormatSpecifier(tv.amwa.maj.record.AUID, byte[])
	 */
	public void addFormatSpecifier(
			tv.amwa.maj.record.AUID essenceFormatCode,
			byte[] value)
			throws NullPointerException {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see tv.amwa.maj.model.EssenceFormat#getFormatSpecifier(tv.amwa.maj.record.AUID)
	 */
	public byte[] getFormatSpecifier(
			tv.amwa.maj.record.AUID essenceFormatCode)
			throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceFormat#getIndexedEssenceFormatCode(int)
	 */
	public AUIDImpl getIndexedEssenceFormatCode(
			int index)
			throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceFormat#getIndexedFormatSpecifier(int)
	 */
	public byte[] getIndexedFormatSpecifier(
			int index)
			throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceFormat#numSpecifiers()
	 */
	public int numSpecifiers() {
		// TODO Auto-generated method stub
		return 0;
	}
}
