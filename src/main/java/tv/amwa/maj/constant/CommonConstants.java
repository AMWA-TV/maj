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
 * $Log: CommonConstants.java,v $
 * Revision 1.10  2011/11/04 09:54:16  vizigoth
 * Updates to enable writing AAF files from objects with AMP metadata.
 *
 * Revision 1.9  2011/10/05 17:14:32  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.8  2011/07/27 12:20:00  vizigoth
 * Added RP224 SMPTE label registry leaf-node constants.
 *
 * Revision 1.7  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/12/15 18:54:32  vizigoth
 * Made object class paramater ID more generally visible so that it can be included/excluded as appropriate.
 *
 * Revision 1.5  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.4  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2007/12/12 12:29:54  vizigoth
 * Added to and edited document comments to a release level.
 *
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.industry.EmitXMLClassIDAs;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.misctype.RGBALayout;
import tv.amwa.maj.record.AUID;


/**
 * <p>Implement this interface to access constant values used across the MAJ API.</p>
 * 
 *
 *
 */
public interface CommonConstants {

	/** <p>Specifies the maximum number of {@linkplain tv.amwa.maj.record.RGBAComponent RGBA components} 
	 * in an {@linkplain RGBALayout RGBA layout}.</p> 
	 */
	public final static int MAX_NUM_RGBA_COMPS = 8;
	
	/**
	 * <p>Namespace definition for all types and elements in the set of baseline AAF classes.</p>
	 */
	public final static String AAF_XML_NAMESPACE = "http://www.smpte-ra.org/schemas/2001-2/2007/aaf";
	
	/**
	 * <p>Short prefix name to use in XML file names to reference the {@linkplain #AAF_XML_NAMESPACE AAF
	 * XML baseline namespace}.</p>
	 */
	public final static String AAF_XML_PREFIX = "aaf";
	
	/**
	 * <p>Namespace definition for all metadictionary elements.</p>
	 */
	public final static String AAF_METADICT_NAMESPACE = "http://www.smpte-ra.org/schemas/2001-1-2011/metadict";
	
	/**
	 * <p>Short prefix name to use in XML file names to reference the {@linkplain #AAF_METADICT_NAMESPACE AAF
	 * metadictionary namespace}.</p>
	 */
	public final static String AAF_METADICT_PREFIX = "";
	
	/**
	 * <p>Name for an attribute that is used to emit the identifier for the 
	 * {@linkplain ClassDefinition class} of an object when the style it is to be emitted
	 * as is {@link EmitXMLClassIDAs#Attribute}.</p>
	 * 
	 * @see EmitXMLClassIDAs
	 * @see ClassDefinition#getEmitXMLClassIDAs()
	 */
	public final static String XMLClassIDAsAttributeName = "uid";
	
	public final static AUID ObjectClassID = 
		Forge.makeAUID(0x06010104, (short) 0x0101, (short) 0x0000,
				new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02});
	
	public final static AUID ApplicationPluginObjectID =
		Forge.makeAUID(0x0d010101, (short) 0x0101, (short) 0x6100,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01});
	
	public final static AUID ParentClassID =
		Forge.makeAUID(0x06010107, (short) 0x0100, (short) 0x0000,
			new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02});
	
	public final static AUID MemberOfID =
		Forge.makeAUID(0x06010107, (short) 0x2200, (short) 0x0000,
			new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0d});
	
	public final static AUID ApplicationPluginsID =
		Forge.makeAUID(0x06010104, (short) 0x020e, (short) 0x0000,
			new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C});
	
	public final static AUID PropertiesID =
		Forge.makeAUID(0x06010107, (short) 0x0200, (short) 0x0000,
			new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02});
}
