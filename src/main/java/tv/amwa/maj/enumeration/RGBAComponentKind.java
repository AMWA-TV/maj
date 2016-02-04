/*
 * Copyright 2016 Advanced Media Workflow Assocation
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
 * $Log: RGBAComponentKind.java,v $
 * Revision 1.8  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.7  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.5  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/08 11:53:14  vizigoth
 * Minor comment improvement.
 *
 * Revision 1.2  2008/01/08 17:01:54  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:50  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies the color or function of a component within a pixel, for example whether the
 * component a red level, palette index etc..</p>
 * 
 * <p>The AAF object specification specifies these values as single byte characters. To find the
 * specified character, call {@link #charValue()}. To find the enumeration constant corresponding to
 * a character, call {@link #fromSpecifiedCode(char)}.</p>
 * 
 * <p>Original C name: <code>aafRGBAComponentKind_e</code></p>
 * 
 * @see tv.amwa.maj.record.RGBAComponent
 * @see tv.amwa.maj.industry.TypeDefinitions#RGBAComponentKind
 * @see tv.amwa.maj.model.RGBADescriptor#getPixelLayout()
 * @see tv.amwa.maj.model.RGBADescriptor#getPaletteLayout()
 * @see tv.amwa.maj.industry.TypeDefinitions#RGBALayout
 * @see tv.amwa.maj.misctype.CompArray
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
public enum RGBAComponentKind 
	implements MediaEnumerationValue {

    /** 
     * <p>Red component.</p> 
     */     
	Red ('R'), 
    /** 
     * <p>Green component.</p>
     */   
	Green ('G'), 
    /** 
     * <p>Blue component.</p> 
     */    
	Blue ('B'), 
    /** 
     * <p>Alpha component.</p> 
     */   
	Alpha ('A'), 
    /** 
     * <p>Fill component.</p>
     */    
	Fill ('F'), 
    /** 
     * <p>Palette component.</p> 
     */ 
	Palette ('P'), 
    /** 
     * <p>No component.</p> 
     */      
	None ('0'), 
    /** 
     * <p>Terminates the list of components.</p>
     */
    Null ('\u0000'), 
    ;

    private final char value;

    RGBAComponentKind (char value) { this.value = value; }

    /**
     * <p>Value of the enumeration as defined in the AAF object specification.</p>
     *
     * @return Value defined in AAF specification.
     */
    public char charValue() { return value; }
    
    /**
     * <p>Returns the enumeration constant matching the given code that should match
     * that specified in the AAF object specification. For example, calling this method
     * with '<code>R</code>' will return {@link #Red}.</p>
     * 
     * @param code Character specified to represent an RGBA component kind.
     * @return Corresponding enumeration constant, or <code>null</code> if the code is
     * not recognised.
     */
    public final static RGBAComponentKind fromSpecifiedCode(
    		char code) {
    	
    	switch (code) {
    	case 'R': return Red;
    	case 'G': return Green;
    	case 'B': return Blue;
    	case 'A': return Alpha;
    	case 'F': return Fill;
    	case 'P': return Palette;
    	case '0': return None;
    	case '\u0000': return Null;
    	default: return null;
    	}
    	
    }

	@Int64 
	public long value() { return (long) value; }
	
    public String symbol() { 
    	
    	return "Comp" + name(); 
    }
}
