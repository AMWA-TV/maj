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
 * $Log: ProductReleaseType.java,v $
 * Revision 1.7  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.4  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/08 17:01:49  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:14:11  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies the release type of a product, for example released, debug alpha etc.. Values
 * of this type are used to specify {@linkplain tv.amwa.maj.record.ProductVersion product versions}
 * that consist of both a version number and a release type.</p>
 *
 * <p>Original C name: <code>aafProductReleaseType_e</code></p>
 * 
 * @see tv.amwa.maj.record.ProductVersion
 * @see tv.amwa.maj.model.Identification
 * @see tv.amwa.maj.industry.TypeDefinitions#ProductReleaseType
 * 
 *
 */

public enum ProductReleaseType 
	implements MediaEnumerationValue {

    /** 
     * <p>No additional version information.</p>
     */        
	Unknown (0), 
    /** 
     * <p>Released product.</p>
     */	                     
	Released (1), 
    /** 
     * <p>Development version.</p> */                      
	Debug (2), 
    /** 
     * <p>Released version with patches.</p>
     */            
	Patched (3), 
    /** 
     * <p>Pre-release beta test version.</p>
     */             
	Beta (4), 
    /** 
     * <p>Version not intended for general release.</p> 
     */ 
	PrivateBuild (5), 
    ;

    private final int value;

    ProductReleaseType (int value) { this.value = value; }

    @Int64 
    public long value() { return (long) value; }
    
    public String symbol() { 
    	
    	return "Version" + name(); 
    }

}
