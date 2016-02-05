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
 * $Log: PluginIdentifiers.java,v $
 * Revision 1.4  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.1  2008/02/08 11:35:16  vizigoth
 * Moved useful plugin constants from the implementation into their own interface.
 *
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;

/**
 * <p>Implement this interface to access some standard constants used to define
 * {@linkplain tv.amwa.maj.model.PluginDefinition plugins}.</p>
 * 
 *
 * 
 * @see PluginCategoryType
 * @see tv.amwa.maj.model.PluginDefinition
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#PluginDefinitionWeakReferenceSet
 */
public interface PluginIdentifiers {

	/**
	 * <p>Specifies that a plugin is platform independent.</p>
	 * 
	 * @see tv.amwa.maj.model.PluginDefinition#getPluginPlatform()
	 * @see tv.amwa.maj.model.PluginDefinition#setPluginPlatform(AUID)
	 */
	public final static AUID Platform_Independent = new tv.amwa.maj.record.impl.AUIDImpl(
			0x3d1dd891, (short) 0xe793, (short) 0x11d2,
			new byte[] { (byte) 0x80, (byte) 0x9e, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });
	
	/**
	 * <p>Specifies that a plugin has no associated software engine.</p>
	 * 
	 * @see tv.amwa.maj.model.PluginDefinition#getEngine()
	 * @see tv.amwa.maj.model.PluginDefinition#setEngine(AUID)
	 */
	public final static AUID Engine_None = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9fdef8c1, (short) 0xe847, (short) 0x11d2,
			new byte[] { (byte) 0x80, (byte) 0x9e, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });
	
	/**
	 * <p>Specifies that the API provided by the plugin is compatible with the 
	 * {@linkplain tv.amwa.maj.model.EssenceAccess essence access API}.</p>
	 * 
	 * @see tv.amwa.maj.model.PluginDefinition#getPluginAPI()
	 * @see tv.amwa.maj.model.PluginDefinition#setPluginAPI(AUID)
	 */
	public final static AUID PluginAPI_EssenceAccess = new tv.amwa.maj.record.impl.AUIDImpl(
			0x69c870a1, (short) 0xe793, (short) 0x11d2, 
			new byte[] { (byte) 0x80, (byte) 0x9e, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });
	
	// removed codec as it conflicts with category types
}
