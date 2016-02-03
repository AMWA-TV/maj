/* 
 **********************************************************************
 *
 * $Id: KLVData.java,v 1.3 2011/02/14 22:32:49 vizigoth Exp $
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
 * $Log: KLVData.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/01/27 11:07:40  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:42  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.nio.ByteBuffer;

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.TypeDefinitionOpaque;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.record.AUID;


/**
 * <p>Specifies a container for user data specified with a key (SMPTE label), length 
 * and value. SMPTE KLV's are specified in SMPTE&336M and are 16-bytes long.</p>
 * 
 * <p>The byte order of the KLV data value is the same as the byte order of the 
 * {@linkplain java.nio.ByteBuffer byte buffer} passed to initialize or set the
 * value.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see KLVDataDefinition
 * @see Component#getComponentKLVData()
 * @see Package#getPackageKLVData()
 * @see TypeDefinitionOpaque
 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataStrongReferenceVector
 */

public interface KLVData 
	extends InterchangeObject {

	/**
	 * <p>Returns the key of this KLV data.</p>
	 * 
	 * @return Key of the KLV data.
	 */
	public AUID getKey();

	/**
	 * <p>Returns the value of this KLV data, including the key.</p>
	 * 
	 * @return Value of the KLV data.
	 */
	public @DataBuffer ByteBuffer getValue();

	/**
	 * <p>Returns the size of the data buffer for this KLV data, which includes
	 * the key.</p>
	 *  
	 * @return Length of the value of the KLV data.
	 */
	public @UInt32 int getLength();

	/**
	 * <p>Sets the key and data value of the KLV data from the given 
	 * byte array. The key is assumed to be the first 16 bytes of the
	 * value.</p>
	 * 
	 * @param klvDataValue Value to set for the KLV data.
	 * 
	 * @throws NullPointerException The given buffer for the data value is <code>null</code>.
	 * @throws IllegalArgumentException A buffer is too short. It must contain a key of of 16-bytes.
	 */
	public void setValue(
			@DataBuffer ByteBuffer klvDataValue) 
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Create a cloned copy of this KLV data value.</p>
	 *
	 * @return Cloned copy of this KLV data value.
	 */
	public KLVData clone();
}

