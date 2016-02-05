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
 * $Log: Boolean.java,v $
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
 * Revision 1.2  2008/01/08 17:01:54  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:59  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;

/** 
 * <p>Representation of Boolean values as an enumeration, as per the AAF-defined representation.</p>
 * 
 * <p>This enumeration is provided to enable the instantiation
 * of the {@link tv.amwa.maj.industry.TypeDefinitions#Boolean AAF boolean data type as an enumeration} in Java.
 * However, all boolean properties in the MAJ API are actually represented as Java <code>boolean</code> values,
 * often annotated with {@link tv.amwa.maj.misctype.Bool}.</p>
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#Boolean
 * @see tv.amwa.maj.misctype.Bool
 *
 *
 *
 */
public enum Boolean 
	implements MediaEnumerationValue {
	
	False(0),
	True(1);
	
	private int value;
	
	Boolean(int value) {
		
		this.value = value;
	}

	public long value() {

		return (long) value;
	}

	public String symbol() {
		
		return (value == 0) ? "false" : "true";
	}
}
