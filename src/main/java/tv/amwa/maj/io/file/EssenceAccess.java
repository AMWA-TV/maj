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
 * $Log: EssenceAccess.java,v $
 * Revision 1.5  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:39  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:08  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/27 11:14:02  vizigoth
 * Replaced a ListIterator with a more useful List.
 *
 * Revision 1.1  2007/11/13 22:17:24  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.io.file;

import java.io.Serializable;
import java.util.List;

import tv.amwa.maj.enumeration.EssenceType;
import tv.amwa.maj.exception.BadSampleOffsetException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.exception.SingleChannelOpException;
import tv.amwa.maj.exception.StreamFullException;
import tv.amwa.maj.model.MaterialPackage;
// import tv.amwa.maj.meta.AAFClass;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.AUIDImpl;


/** TODO implementation, comments and tests
 * <p></p>
 *
 *
 *
 */

//@AAFClass(uuid1 = 0xaed97eb, uuid2 = 0x2bc8, uuid3 = 0x11d2,
//		  uuid4 = { (byte) 0xbf, (byte) 0xaa, 0x00, 0x60, (byte) 0x97, 0x11, 0x62, 0x12},
//		  definedName = "EssenceAccess",
//		  description = "The EssenceAccess class provides streaming access over multiple channels of essence data.",
//		  symbol = "EssenceAccess") Not an AAF class
// @Entity
public class EssenceAccess
	extends EssenceMultiAccess
		implements
			tv.amwa.maj.model.EssenceAccess,
			Serializable {

	/** <p></p> */
	private static final long serialVersionUID = 7324887070387644171L;

	// TODO private fields here

	/** Default constructor is not public to avoid unset required fields. */
	EssenceAccess() {
	}
	
	public EssenceAccess(
			MaterialPackage masterMob,
			AUIDImpl mediaKind,
			AUIDImpl codecID,
			Rational editRate,
			Rational sampleRate,
			Rational compEnable,
			EssenceType essencetype) {
		
		// TODO
	}

	// Assuming no public constructor. EssenceAccess is manufactured by material package.

	/**
	 * <p>Cast a {@link tv.amwa.maj.model.EssenceAccess EssenceAccess} 
	 * value from the generic interface to this implementation of
	 * the interface. If the given value is not a native implementation, a copy will be
	 * instanciated using get and set methods.</p>
	 *
	 * @param alien A potentially alien implementation of an instance of the EssenceAccess 
	 * interface.
	 * @return Instance of this class that is equal to the given value and that is 
	 * an instance of this concrete implementation.
	 *
	 * @throws NullPointerException Argument is null.
	 */
	public final static EssenceAccess castFromInterface(
			tv.amwa.maj.model.EssenceAccess alien)
			throws NullPointerException {

		if (alien == null)
			throw new NullPointerException(
					"Cannot cast to EssenceAccess from a null value.");

		if (alien instanceof EssenceAccess)
			return (EssenceAccess) alien;

		EssenceAccess castValue = new EssenceAccess();
		// TODO Complete cast

		return castValue;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#completeWrite()
	 */
	public void completeWrite()
			throws StreamFullException {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#countSamples(tv.amwa.maj.model.DataDefinition)
	 */
	public long countSamples(
			tv.amwa.maj.model.DataDefinition dataDef)
			throws NullPointerException {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#getCodecID()
	 */
	public AUIDImpl getCodecID() {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#getCodecName()
	 */
	public String getCodecName() {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#getEmptyFileFormat()
	 */
	public EssenceFormat getEmptyFileFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#getFileFormat(tv.amwa.maj.model.EssenceFormat)
	 */
	public EssenceFormat getFileFormat(
			tv.amwa.maj.model.EssenceFormat opsTemplate)
			throws NullPointerException,
				InvalidParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#getFileFormatParametersList()
	 */
	public EssenceFormat getFileFormatParametersList() {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#getIndexedSampleSize(tv.amwa.maj.model.DataDefinition, long)
	 */
	public long getIndexedSampleSize(
			tv.amwa.maj.model.DataDefinition dataDef,
			long frameNum)
			throws NullPointerException,
				BadSampleOffsetException {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#getLargestSampleSize(tv.amwa.maj.model.DataDefinition)
	 */
	public long getLargestSampleSize(
			tv.amwa.maj.model.DataDefinition dataDef)
			throws NullPointerException {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#getSamplesActuallyRead()
	 */
	public int getSamplesActuallyRead() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#putFileFormat(tv.amwa.maj.model.EssenceFormat)
	 */
	public void putFileFormat(
			tv.amwa.maj.model.EssenceFormat ops)
			throws NullPointerException {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#readSamples(int)
	 */
	public byte[] readSamples(
			int samples)
			throws EndOfDataException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#seek(long)
	 */
	public void seek(
			long sampleFrameNum)
			throws BadSampleOffsetException {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#setEssenceCodecFlavour(tv.amwa.maj.record.AUID)
	 */
	public void setEssenceCodecFlavour(
			tv.amwa.maj.record.AUID flavour)
			throws NullPointerException {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#setTransformParameters(java.util.ListIterator)
	 */
	public void setTransformParameters(
			List<? extends tv.amwa.maj.model.EssenceFormat> op)
			throws NullPointerException {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see tv.amwa.maj.model.EssenceAccess#writeSamples(int, byte[])
	 */
	public int writeSamples(
			int samples,
			byte[] buffer)
			throws NullPointerException,
				SingleChannelOpException {
		// TODO Auto-generated method stub
		return 0;
	}
}
