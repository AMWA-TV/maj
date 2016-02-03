/* 
 **********************************************************************
 *
 * $Id: EdgeType.java,v 1.7 2011/02/14 22:32:58 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: EdgeType.java,v $
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
 * Revision 1.1  2007/11/13 22:13:48  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies the kind of film edge code.</p>
 * 
 * <p>Original C name: <code>aafEdgeType_e</code></p>
 * 
 * @see tv.amwa.maj.record.EdgeCodeValue#getEdgeCodeFormat()
 * @see tv.amwa.maj.model.EdgeCodeSegment#getEdgeCodeFormat()
 * @see tv.amwa.maj.industry.TypeDefinitions#EdgeType
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */

public enum EdgeType 
	implements MediaEnumerationValue {

    /** 
     * <p>Invalid edge code.</p> 
     */               
	Null (0), 
    /** 
     * <p>Eastman Kodak KEYKODE<sup>TM</sup> format.</p> 
     */ 
	Keycode (1), 
    /** 
     * <p>Edge code format: <code>nnnn+nn</code>.</p> 
     */       
	Edgenum4 (2), 
    /** 
     * <p>Edge code format: <code>nnnnn+nn</code>.</p> 
     */      
	Edgenum5 (3), 
    /** 
     * <p>Header size. Not really an edge code type, just a sneaky constant used to represent the 
     * normal size of an edge code header.</p> 
     */                     
	HeaderSize (8), // Definition of this one is missing in docs.
     ;

    private final int value;

    EdgeType (int value) { this.value = value; }

    @Int64 public long value() { return (long) value; }
    
    public String symbol() { 
    	
    	return "Et" + name(); 
    }
}
