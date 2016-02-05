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
 * $Log: ByteOrder.java,v $
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
 * Revision 1.1  2007/11/13 22:13:39  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int16;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies the byte order of a file, such as an AAF file. A ByteOrder property records the byte 
 * order of the computer platform on which the file 
 * was created. Subsequent modification of the file may create objects with foreign byte order; the 
 * byte order of individual modified objects shall be properly maintained by the storage format.</p>
 * 
 * <p>In the {@linkplain #Big big-endian byte order}, the most significant byte is stored 
 * first at the address specified, which is the lowest address of the series of bytes that constitute 
 * the value. In the {@linkplain #Little little-endian byte order}, the least-significant byte 
 * is stored first. In both cases, each individual byte is stored with the most-significant bit first.</p>
 * 
 * <p>Note that the <a href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF object 
 * specification v1.1</a> uses an {@link tv.amwa.maj.integer.Int16} value to represent byte order. This
 * value can be retrieved using {@link #getAAFByteOrderCode()} method and converted back to a value of this
 * enumeration using {@link #getByteOrderFromAAFCode(short)}.</p>
 * 
 * <p>Original C name: <code>eByteOrder_e</code></p>
 * 
 * @see tv.amwa.maj.model.Preface
 * @see tv.amwa.maj.meta.TypeDefinitionStream
 * 
 *
 */

public enum ByteOrder 
	implements MediaEnumerationValue {

	/** 
	 * <p>Little-endian byte order, where the least significant byte is stored first.</p> 
	 * 
	 * <p>The code used in AAF file to represent little-endian byte order is 0x6c6c ('<code>ll</code>').</p>
	 */
	Little (0), 
    /** 
     * <p>Big-endian byte order, where the most significant byte is stored first.</p> 
     * 
     * <p>The code used in AAF file to represent big-endian byte order is 0x4d4d ('<code>MM</code>').</p>
     */
	Big (1), 
    ;

    private final int value;

    ByteOrder (int value) { this.value = value; }

    @Int64 public long value() { return (long) value; }
    
    public String symbol() { 
    	
    	return name() + "Endian"; 
    }
    
    /**
     * <p>Returns the code used to represent this byte order in an AAF file.</p>
     * 
     * @return Code used to represent this byte order in an AAF file.
     */
    public @Int16 short getAAFByteOrderCode() {
    	
    	if (value == 1) return (short) 0x4d4d;
    	
    	return (short) 0x4949;
    }

    /**
     * <p>Returns a value of this enumeration matching the given byte order code from an
     * AAF file.</p> 
     * 
     * @param code Code used to represent byte order from an AAF file.
     * @return Element of this enumeration representing the given code.
     * 
     * @throws IllegalArgumentException The given value is not recognised as one of the codes
     * representing byte order in an AAF file.
     */
    public final static ByteOrder getByteOrderFromAAFCode(
    		@Int16 short code)
    	throws IllegalArgumentException {
    	
    	switch (code) {
    	
    	case (short) 0x6c6c:
    	case (short) 0x4c4c:
    	case (short) 0x4949:
    		return Little;

    	case (short) 0x4d4d:
    	case (short) 0x6d6d:
    		return Big;
    	
    	default:
    		throw new IllegalArgumentException("Unknown byte order code. Should be one of 'II' or 'MM'.");
    	}
    }

}
