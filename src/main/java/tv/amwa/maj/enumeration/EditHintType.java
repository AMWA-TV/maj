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
 * $Log: EditHintType.java,v $
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
 * Revision 1.2  2008/01/08 17:01:52  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:38  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies hints to be used when editing {@linkplain tv.amwa.maj.model.ControlPoint control 
 * points}. The hint is used to describe how to alter a control point if an 
 * {@linkplain tv.amwa.maj.model.OperationGroup operation group} that contains it is itself
 * altered in size.</p>
 * 
 * <p>Original C name: <code>aafEditHint_e</code></p>
 * 
 * @see tv.amwa.maj.model.ControlPoint#getEditHint()
 * @see tv.amwa.maj.industry.TypeDefinitions#EditHintType
 * @see tv.amwa.maj.model.OperationGroup
 * 
 *

*/

public enum EditHintType 
	implements MediaEnumerationValue {

	
    /**
     * <p>No hint on how to update the control point is provided.</p>
     */
    NoEditHint (0), 
    /**
     * <p>The change to the control point value is in linear proportion to the change in
     * the overall operation group size.</p>
     */
    Proportional (1), 
    /**
     * <p>The control point should maintain the same difference between itself and the control
     * point to its left.</p>
     */
    RelativeLeft (2), 
    /**
     * <p>The control point should maintain the same difference between itself and the control
     * point to its right.</p>
     */
    RelativeRight (3), 
    /**
     * <p>The control point should maintain the same relative difference between itself and
     * the control points either side.</p>
     */
    RelativeFixed (4), 
    ;

    private final int value;

    EditHintType (int value) { this.value = value; }

    @Int64 public long value() { return (long) value; }
    
    public String symbol() { return name(); }
}
