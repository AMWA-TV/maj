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
 * $Log: SearchTag.java,v $
 * Revision 1.7  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/12/18 17:56:04  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.4  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/14 16:07:33  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:18  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies the type of criteria used to match {@linkplain tv.amwa.maj.model.Package packages}
 * in a {@linkplain SearchCriteria search criteria}.</p>
 * 
 * <p>Original C name: <code>aafSearchTag_e</code></p>
 * 
 * @see SearchCriteria
 * 
 *
*/

public enum SearchTag 
	implements MediaEnumerationValue {

    /**
     * <p>Criteria that matches nothing.</p>
     * 
     * @see SearchForNothing
     */
    NoSearch (0), 
    /**
     * <p>Criteria that matches packages by their {@linkplain tv.amwa.maj.record.PackageID package id}.</p>
     * 
     * @see SearchByPackageID
     */
    ByPackageID (1), 
    /**
     * <p>Criteria that matches packages by their {@linkplain tv.amwa.maj.enumeration.PackageKind kind}.</p>
     * 
     * @see SearchByPackageKind
     */
    ByPackageKind (2), 
    /**
     * <p>Criteria that matches packages by their name.</p>
     * 
     * @see SearchByName
     */
    ByName (3), 
    /**
     * <p>Criteria that matches packages by their class id.</p>
     * 
     * @see SearchByAUID
     */
    ByClass (4), 
    /**
     * <p>Criteria that matches packages by their {@linkplain tv.amwa.maj.model.DataDefinition
     * data definition}.</p>
     * 
     * @see SearchByAUID
     */
    ByDataDef (5), 
    /**
     * <p>Criteria that matches packages by their {@linkplain tv.amwa.maj.enumeration.CriteriaType
     * media criteria type}.</p>
     * 
     * @see SearchByMediaCriteria
     */
    ByMediaCrit (6), 
    /**
     * <p>Criteria that matches {@linkplain tv.amwa.maj.model.Package all packages} by their 
     * {@linkplain tv.amwa.maj.constant.UsageType usage code}.</p>
     * 
     * @see SearchByAUID
     * @see tv.amwa.maj.industry.TypeDefinitions#UsageType
     * @see tv.amwa.maj.constant.UsageType
     * @see tv.amwa.maj.model.Package#getPackageUsage()
     */
    ByUsageCode (7), 
    /**
     * <p>Criteria that matches {@linkplain tv.amwa.maj.model.MaterialPackage master packages} by their 
     * {@linkplain tv.amwa.maj.constant.UsageType usage code}.</p>
     * 
     * @see SearchByAUID
     * @see tv.amwa.maj.industry.TypeDefinitions#UsageType
     * @see tv.amwa.maj.constant.UsageType
     * @see tv.amwa.maj.model.Package#getPackageUsage() 
     */
    ByMaterialPackageUsageCode (8), 
    /**
     * <p>Criteria that matches {@linkplain tv.amwa.maj.model.SourcePackage source packages} by their 
     * {@linkplain tv.amwa.maj.constant.UsageType usage code}.</p>
     * 
     * @see SearchByAUID
     * @see tv.amwa.maj.industry.TypeDefinitions#UsageType
     * @see tv.amwa.maj.constant.UsageType
     * @see tv.amwa.maj.model.Package#getPackageUsage() 
     */
    BySourcePackageUsageCode (9), 
    /**
     * <p>Criteria that matches {@linkplain tv.amwa.maj.model.CompositionPackage composition packages} by their 
     * {@linkplain tv.amwa.maj.constant.UsageType usage code}.</p>
     * 
     * @see SearchByAUID
     * @see tv.amwa.maj.industry.TypeDefinitions#UsageType
     * @see tv.amwa.maj.constant.UsageType
     * @see tv.amwa.maj.model.Package#getPackageUsage() 
     */
    ByCompositionPackageUsageCode (10), 
    ;

    private final int value;

    SearchTag (int value) { this.value = value; }

    @Int64 
    public long value() { return (long) value; }

	public String symbol() {

		return name();
	}
}
