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
 * $Log: PackageKind.java,v $
 * Revision 1.4  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/08 11:54:16  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2008/01/08 17:01:49  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:53  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies the kind of a {@linkplain tv.amwa.maj.model.Package package}. This is used when searching
 * for a package.</p>
 * 
 * @see tv.amwa.maj.model.Package
 * @see tv.amwa.maj.model.ContentStorage#countPackages(PackageKind)
 * 
 *
 */

public enum PackageKind 
	implements MediaEnumerationValue {

    /**
     * <p>Package is a {@linkplain tv.amwa.maj.model.CompositionPackage composition package}.</p>
     */
    CompositionPackage (0), 
    /**
     * <p>Package is a {@linkplain tv.amwa.maj.model.MaterialPackage material package}.</p>
     */
    MaterialPackage (1), 
    /**
     * <p>Package is a {@linkplain tv.amwa.maj.model.SourcePackage source package} described by a 
     * {@linkplain tv.amwa.maj.model.AAFFileDescriptor file descriptor}.</p>
     */
    FilePackage (2), 
    /**
     * <p>Package is a {@linkplain tv.amwa.maj.model.SourcePackage source package} described by a 
     * {@linkplain tv.amwa.maj.model.TapeDescriptor tape descriptor}.</p>
     */
    TapePackage (3), 
    /**
     * <p>Package is a {@linkplain tv.amwa.maj.model.SourcePackage source package} described by a 
     * {@linkplain tv.amwa.maj.model.FilmDescriptor film descriptor}.</p>
     */
    FilmPackage (4), 
    /** TODO add a definition of primary package here
     * <p>Package is a {@linkplain tv.amwa.maj.model.MaterialPackage material package} and the <em>primary
     * package</em> within its package.</p>
     * 
     * @see tv.amwa.maj.model.Preface#getPrimaryPackage()
     * @see tv.amwa.maj.model.Preface#setPrimaryPackage(tv.amwa.maj.model.Package)
     */
    PrimaryPackage (5),
    /**
     * <p>Matches any {@linkplain tv.amwa.maj.model.Package package}.</p>
     */
    AllPackages (6), 
    /**
     * <p>Package is a {@linkplain tv.amwa.maj.model.SourcePackage source package} described by a 
     * {@linkplain tv.amwa.maj.model.PhysicalDescriptor physical descriptor}.</p>
     */
    PhysicalPackage (7), 
    ;

    private final int value;

    PackageKind (int value) { this.value = value; }

    /** 
     * @see tv.amwa.maj.industry.MediaEnumerationValue#value()
     */
    @Int64 public long value() { return (long) value; }
    
    public String symbol() { return name(); }

}
