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
 * $Log: UsageType.java,v $
 * Revision 1.9  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.8  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2010/03/02 17:34:46  vizigoth
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
 * Revision 1.2  2007/12/12 12:29:53  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:33  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;
import tv.amwa.maj.industry.ExtendibleEnumerationItem;

/**
 * <p>Implement this interface to access unique identifiers describing the way that a {@linkplain tv.amwa.maj.model.Package
 * package} is used. This interface is also used to seed the define the extendible enumeration 
 * {@link tv.amwa.maj.industry.TypeDefinitions#UsageType UsageType}.</p>
 * 
 * <p>The built-in usage codes are specified in the 
 * <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF edit protocol</a>.</p>
 *
 * @see tv.amwa.maj.industry.ExtendibleEnumerationItem
 * @see tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration
 * @see tv.amwa.maj.model.Package#getPackageUsage()
 * @see tv.amwa.maj.industry.Warehouse#lookupExtendibleEnumeration(String)
 * @see tv.amwa.maj.industry.TypeDefinitions#UsageType
 * 
 *
 *
 */
public interface UsageType {

	/**
	 * <p>{@linkplain tv.amwa.maj.model.Package Package} is a {@linkplain tv.amwa.maj.model.CompositionPackage composition
	 * package} used as a sub-clip, where a section of a clip is identified and thereafter treated in the user interface 
	 * as an additional clip.</p>
	 */
	@ExtendibleEnumerationItem(target = "Usage")
	public final static AUID SubClip = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010102, (short) 0x0101, (short) 0x0500,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>{@linkplain tv.amwa.maj.model.Package Package} is a {@linkplain tv.amwa.maj.model.CompositionPackage composition
	 * package} used as an adjusted clip, where an effect is applied directly to a clip and applies to all uses of 
	 * that clip, e.g. an audio gain effect.</p>
	 */
	@ExtendibleEnumerationItem(target = "Usage")
	public final static AUID AdjustedClip = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010102, (short) 0x0101, (short) 0x0600,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>{@linkplain tv.amwa.maj.model.Package Package} is a {@linkplain tv.amwa.maj.model.CompositionPackage composition
	 * package} used as a top-level composition, which specifies a composition that is not referenced by another 
	 * composition in a file.</p>
	 */
	@ExtendibleEnumerationItem(target = "Usage")
	public final static AUID TopLevel = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010102, (short) 0x0101, (short) 0x0700,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>{@linkplain tv.amwa.maj.model.Package Package} is a {@linkplain tv.amwa.maj.model.CompositionPackage composition
	 * package} used as a lower-level composition, which specifies a composition that is referenced by another 
	 * composition in a file.</p>
	 */
	@ExtendibleEnumerationItem(target = "Usage")
	public final static AUID LowerLevel = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010102, (short) 0x0101, (short) 0x0800,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>{@linkplain tv.amwa.maj.model.Package Package} is a {@linkplain tv.amwa.maj.model.MaterialPackage material package}
	 * with no sources.</p>
	 */
	@ExtendibleEnumerationItem(target = "Usage")
	public final static AUID Template = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010102, (short) 0x0101, (short) 0x0900,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
}
