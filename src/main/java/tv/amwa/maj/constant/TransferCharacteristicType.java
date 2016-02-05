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
 * $Log: TransferCharacteristicType.java,v $
 * Revision 1.10  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.9  2011/01/25 14:17:26  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.8  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2010/03/02 17:34:47  vizigoth
 * Extensible enumeration item is really part of the MAJ industry, so moved to the appropriate package.
 *
 * Revision 1.6  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/01/14 21:00:02  vizigoth
 * Added reference to relevant extendible enumeration constants.
 *
 * Revision 1.2  2007/12/12 12:29:55  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:45  vizigoth
 * Public release of MAJ API.
 */


package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;
import tv.amwa.maj.industry.ExtendibleEnumerationItem;

/**
 * <p>Implement this interface to access unique identifiers for kinds of opto-electronic
 * transfer characteristics. This interface is also used to seed the define the extendible enumeration 
 * {@link tv.amwa.maj.industry.TypeDefinitions#TransferCharacteristicType TransferCharacteristicType}. 
 * Values of this type are used to specify {@linkplain tv.amwa.maj.model.PictureDescriptor digital image 
 * descriptors}.</p>
 * 
 * <p>For more details of the parameters of the transfer characteristics, see section 23.1 of
 * the <a href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF object specification v1.1</a>.</p> 
 * 
 * @see tv.amwa.maj.industry.ExtendibleEnumerationItem
 * @see tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration
 * @see tv.amwa.maj.model.PictureDescriptor#getTransferCharacteristic()
 * @see tv.amwa.maj.industry.Warehouse#lookupExtendibleEnumeration(String)
 * @see tv.amwa.maj.industry.TypeDefinitions#TransferCharacteristicType
 *
 *
 *
 */
public interface TransferCharacteristicType {

	/**
	 * <p>Transfer characteristic defined in "Recommendation ITU-R BT.470 PAL Transfer Characteristic"
	 * with an assumed display gamma of&nbsp;2.8.</p> 
	 */
	@ExtendibleEnumerationItem(target = "TransferCharacteristic")
	public final static AUID ITU470_PAL = new tv.amwa.maj.record.impl.AUIDImpl(
			0x04010101, (short) 0x0101, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>Transfer characteristics defined in "Recommendation ITU-R BT.709 Transfer Characteristic".</p>
	 * 
	 * <p>Note that this identifier may also be used to identify SMPTE&nbsp;170M, SMPTE&nbsp;274M and SMPTE&nbsp;296M 
	 * transfer characteristics, which are defined to be the same as ITU-R BT.709.</p>
	 */
	@ExtendibleEnumerationItem(target = "TransferCharacteristic")
	public final static AUID ITU709 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x04010101, (short) 0x0102, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>Transfer characteristics defined in SMPTE&nbsp;240M. Note that this identifier is included
	 * for legacy use only.</p>
	 */
	@ExtendibleEnumerationItem(target = "TransferCharacteristic")
	public final static AUID SMPTE240M = new tv.amwa.maj.record.impl.AUIDImpl(
			0x04010101, (short) 0x0103, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>Transfer characteristics defined in SMPTE&nbsp;274M and SMPTE&nbsp;296M.</p>
	 */
	@ExtendibleEnumerationItem(target = "TransferCharacteristic")
	public final static AUID _274M_296M = new tv.amwa.maj.record.impl.AUIDImpl(
			0x04010101, (short) 0x0104, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>Transfer characteristics defined in "Recommendation ITU-R BT.1361 Transfer Characteristic".</p>
	 */
	@ExtendibleEnumerationItem(target = "TransferCharacteristic")
	public final static AUID ITU1361 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x04010101, (short) 0x0105, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>Linear transfer characteristics.</p>
	 */
	@ExtendibleEnumerationItem(target = "TransferCharacteristic")
	public final static AUID linear = new tv.amwa.maj.record.impl.AUIDImpl(
			0x04010101, (short) 0x0106, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
}
