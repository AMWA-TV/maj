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
 * $Log: StaticTrack.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/02/08 11:27:19  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:36  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

/**
 * <p>Specifies a single track of essence data that has no relationship to time, such 
 * as a static image. As a static track has not relationship with time, it does
 * not specify an edit rate.</p>
 * 
 *
 *
 * @see MaterialPackage#createStaticEssence(int, DataDefinition, tv.amwa.maj.record.AUID, tv.amwa.maj.enumeration.CompressEnable, Locator, tv.amwa.maj.record.AUID) MaterialPackage.createStaticEssence()
 * @see tv.amwa.maj.constant.DataDefinitionConstant#Auxiliary
 */

public interface StaticTrack 
	extends Track {

	/**
	 * <p>Create a cloned copy of this static track.</p>
	 *
	 * @return Cloned copy of this static track.
	 */
	public StaticTrack clone();
}
