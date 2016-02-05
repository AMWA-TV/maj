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
 * $Log: PluginCategoryType.java,v $
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
 * Revision 1.5  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/01/14 21:00:02  vizigoth
 * Added reference to relevant extendible enumeration constants.
 *
 * Revision 1.3  2007/12/12 12:29:54  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.2  2007/11/22 14:44:58  vizigoth
 * Changed from class to interface.
 *
 * Revision 1.1  2007/11/13 22:12:35  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;
import tv.amwa.maj.industry.ExtendibleEnumerationItem;

/**
 * <p>Implement this interface to access unique identifiers describing the categories of 
 * {@linkplain tv.amwa.maj.model.PluginDefinition plugin}. 
 * This interface is also used to seed the define the extendible enumeration 
 * {@link tv.amwa.maj.industry.TypeDefinitions#PluginCategoryType PluginCategoryType}.</p>
 * 
 * @see tv.amwa.maj.industry.ExtendibleEnumerationItem
 * @see tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration
 * @see tv.amwa.maj.model.PluginDefinition#getCategoryClass()
 * @see tv.amwa.maj.industry.Warehouse#lookupExtendibleEnumeration(String)
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginCategoryType
 *
 *
 */
public interface PluginCategoryType {
	
	/**
	 * <p>Specifies that a plugin that provides the implementation of an effect, such as the effects
	 * described by the {@linkplain OperationConstant operation identifiers}.</p>
	 */
	@ExtendibleEnumerationItem(target = "PluginCategory")
	public final static AUID Effect = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010102, (short) 0x0101, (short) 0x0200,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>Specifies that a plugin that provides the implementation of a codec, such as the codecs
	 * described by the {@linkplain CodecConstant codec identifiers}.</p>
	 */
	@ExtendibleEnumerationItem(target = "PluginCategory")
	public final static AUID Codec = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010102, (short) 0x0101, (short) 0x0300,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>Specifies that a plugin that provides the implementation of an interpolation function, 
	 * such as the interpolation functions described by the {@linkplain InterpolationConstant interpolation 
	 * identifiers}.</p>
	 */
	@ExtendibleEnumerationItem(target = "PluginCategory")
	public final static AUID Interpolation = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010102, (short) 0x0101, (short) 0x0400,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
}
