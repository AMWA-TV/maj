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
 * $Log: MediaEnumerationValue.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.5  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/14 21:02:15  vizigoth
 * Minor comment addition about the usage of enumerations in the MAJ API.
 *
 * Revision 1.2  2008/01/08 17:01:52  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:57  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.industry;

import tv.amwa.maj.enumeration.ChannelStatusModeType;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Implemented by enumeration specifications that includes an integer value representing an 
 * {@linkplain tv.amwa.maj.meta.TypeDefinitionEnumeration AAF enumeration data type}.
 * Every enumeration constant must provide a unique ordinal value over the set of all 
 * enumeration constants of the same enumeration type and a symbol for XML representation. For AAF data types, this
 * should be the same ordinal value as provided in the 
 * <a href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF object specification</a> and/or
 * current AAF meta dictionary.</p>
 * 
 * <p>During the creation of instances of {@link tv.amwa.maj.meta.TypeDefinitionEnumeration} from the 
 * associated Java enumeration, calling the {@link #value()} method provides the 
 * {@link tv.amwa.maj.meta.TypeDefinitionEnumeration#getElementValue(int) ElementValues} property.
 * The {@link #symbol()} method is used when serializing enumeration values to and from XML
 * representations. The name of the enumeration element is the same as the Java enumeration literal
 * {@linkplain Enum#name() name} and this may be the same as or different from the symbol. Symbols may contain spaces and
 * characters that are illegal as Java identifiers.</p>
 * 
 * <p>All built-in enumerations specified for AAF must implement this interface. Other enumerations
 * may implement this interface to indicate that they associate an ordinal value with each enumeration
 * element.</p> 
 * 
 * @see tv.amwa.maj.meta.TypeDefinitionEnumeration
 * @see tv.amwa.maj.meta.TypeDefinitionEnumeration
 *
 *
 *
 */
public interface MediaEnumerationValue {

	/**
	 * <p>Returns the unique integer value associated with an enumeration value, either according
	 * to the AAF specification and/or the current AAF meta dictionary.</p>
	 *
	 * @return Unique integer value associated with an enumeration constant.
	 */
	public @Int64 long value();
	
	/**
	 * <p>Returns the unique name associated with the enumeration constant. For Java enumerations,
	 * this is established by calling {@link java.lang.Enum#name()}.</p>
	 *
	 * @return Unique name of the enumeration value.
	 */
	public String name();
	
	/**
	 * <p>Provides a name for the enumeration value that can be used in XML serialisation. The
	 * name is unique within the namespace of the owning type.</p>
	 * 
	 * <p>As an example, the {@link ChannelStatusModeType} value {@link ChannelStatusModeType#Fixed}
	 * value has the name "<code>Fixed</code>" and symbol "<code>ChannelStatusMode_Fixed</code>".</p>
	 * 
	 * @return Symbol name for this enumeration value.
	 */
	public String symbol();
}
