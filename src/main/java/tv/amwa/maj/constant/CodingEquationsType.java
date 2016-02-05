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
 * $Log: CodingEquationsType.java,v $
 * Revision 1.9  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
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
 * Revision 1.2  2007/12/12 12:29:54  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:32  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;
import tv.amwa.maj.industry.ExtendibleEnumerationItem;

/** 
 * <p>Implement this interface to access unique identifiers for coding equations used to convert RGB image 
 * components to component color difference image components. This interface is also used to seed the define
 * the extendible enumeration 
 * {@link tv.amwa.maj.industry.TypeDefinitions#CodingEquationsType CodingEquationsType}. 
 * See section 23.1 of the <a href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF object specification 
 * v1.1</a> for details of the equations themselves.</p>
 * 
 * @see tv.amwa.maj.industry.ExtendibleEnumerationItem
 * @see tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration
 * @see tv.amwa.maj.model.PictureDescriptor#getCodingEquations()
 * @see tv.amwa.maj.industry.Warehouse#lookupExtendibleEnumeration(String)
 * @see tv.amwa.maj.industry.TypeDefinitions#CodingEquationsType
 *
 *
 *
 */
public interface CodingEquationsType {

	/**
	 * <p>Coding equations from "Recommendation ITU-R BT.601 Coding Equations".</p>
	 */
	@ExtendibleEnumerationItem(target = "CodingEquations")
	public final static AUID ITU601 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x04010101, (short) 0x0201, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>Coding equations from "Recommendation ITU-R BT.709 Coding Equations". Note that this constant
	 * may also be used to identify coding equations from SMPTE&nbsp;274M and SMPTE&nbsp;296M which 
	 * are defined to be the same.</p>
	 */
	@ExtendibleEnumerationItem(target = "CodingEquations")
	public final static AUID ITU709 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x04010101, (short) 0x0202, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>Coding equations from "SMPTE&nbsp;240M Coding Equations". Note that this is included for
	 * legacy use only.</p>
	 */
	@ExtendibleEnumerationItem(target = "CodingEquations")
	public final static AUID SMPTE240M = new tv.amwa.maj.record.impl.AUIDImpl(
			0x04010101, (short) 0x0203, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
}
