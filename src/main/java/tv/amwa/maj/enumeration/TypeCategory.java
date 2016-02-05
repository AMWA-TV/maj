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
 * $Log: TypeCategory.java,v $
 * Revision 1.7  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.5  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.3  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/08 17:01:49  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:45  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies a categorisation of an {@linkplain tv.amwa.maj.meta.TypeDefinition AAF data type}. The 
 * enumeration can be used to define the type of a {@linkplain tv.amwa.maj.industry.PropertyValue property value}.</p>
 * 
 * <p>The ordinal value used to represent a type category can be converted into an element of this
 * enumeration type using the {@link #fromOrdinal(int)} method.</p>
 * 
 * <p>Original C name: <code>eTypeCategory_e</code></p>
 * 
 * @see tv.amwa.maj.meta.TypeDefinition
 * @see tv.amwa.maj.industry.PropertyValue
 * 
 *
 */

public enum TypeCategory 
	implements MediaEnumerationValue {

    /** 
     * <p>Data type of the value is unknown. This situation can only occur in damaged files.</p>
     */ 
	Unknown (0),
    /** 
     * <p>Any integral type.</p> 
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionInteger
     */               
	Int (1), 
    /** 
     * <p>Any character type.</p> 
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionCharacter
     */              
	Character (2), 
    /** 
     * <p>Strong object reference.</p>
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionStrongObjectReference
     */         
	StrongObjRef (3), 
    /** 
     * <p>Weak object reference.</p>
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionWeakObjectReference
     */           
	WeakObjRef (4), 
    /** 
     * <p>Renamed type.</p>
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionRename
     */                    
	Rename (5), 
    /** 
     * <p>Enumerated type.</p>
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionEnumeration
     */                  
	Enum (6),
    /** 
     * <p>Fixed-size array.</p> 
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionFixedArray
     */                 
	FixedArray (7), 
    /** 
     * <p>Variably-sized array.</p>
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionVariableArray
     */            
	VariableArray (8),
    /** <p>Set of strong object references or set of weak object references.</p>
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionSet
     */   
	Set (9), 
    /** 
     * <p>A structured type.</p>
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionRecord
     */              
	Record (10),
    /** 
     * <p>Potentially huge amount of data.</p>
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionStream
     */ 
	Stream (11), 
    /** 
     * <p>Null-terminated variably-sized array of characters.</p>
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionString
     */             
	String (12),  
    /** 
     * <p>Extendible enumerated type.</p>
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionExtendibleEnumeration
     */      
	ExtEnum (13), 
    /** 
     * <p>Type must be determined at runtime.</p> 
     * 
     * @see tv.amwa.maj.meta.TypeDefinitionIndirect
     */                         
	Indirect (14),  
    /** 
     * <p>Type can be determined at runtime.</p>
     *
     * @see tv.amwa.maj.meta.TypeDefinitionOpaque
     */                         
	Opaque (15), 
    /** 
     * <p>Type can be determined at runtime but bits are encrypted.</p>
     */  
	Encrypted (16),  
    ;

    private final int value;

    TypeCategory (int value) { this.value = value; }

    @Int64
    public long value() { return (long) value; }
    
    /**
     * <p>Returns the enumeration element representing the type category given by the
     * specified ordinal value.</p>
     * 
     * @param categoryIdentifier Specified ordinal representing a type category.
     * @return Enumeration element representing the AAF data type category, or {@link #Unknown} 
     * if the value is not recognised.
     */
    public final static TypeCategory fromOrdinal(
    		int categoryIdentifier) {
    	
    	switch (categoryIdentifier) {
    	
    	case 1: return Int;
    	case 2: return Character;
    	case 3: return StrongObjRef;
    	case 4: return WeakObjRef;
    	case 5: return Rename;
    	case 6: return Enum;
    	case 7: return FixedArray;
    	case 8: return VariableArray;
    	case 9: return Set;
    	case 10: return Record;
    	case 11: return Stream;
    	case 12: return String;
    	case 13: return ExtEnum;
    	case 14: return Indirect;
    	case 15: return Opaque;
    	
    	case 0: 
    	default: return Unknown;
	
    	}
    }

    public String symbol() { return name(); }

}
