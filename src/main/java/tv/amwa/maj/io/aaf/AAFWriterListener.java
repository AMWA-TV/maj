/* 
 **********************************************************************
 *
 * $Id: AAFWriterListener.java,v 1.6 2011/11/04 09:54:16 vizigoth Exp $
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
 * $Log: AAFWriterListener.java,v $
 * Revision 1.6  2011/11/04 09:54:16  vizigoth
 * Updates to enable writing AAF files from objects with AMP metadata.
 *
 * Revision 1.5  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.4  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/06/14 17:12:16  vizigoth
 * Significant progress towards writing valid AAF files with MAJ.
 *
 * Revision 1.2  2010/04/16 15:23:12  vizigoth
 * New methods to support the writing of referenced properties and index tables to AAF files.
 *
 * Revision 1.1  2010/04/15 16:33:30  vizigoth
 * POIFS writer listener interface extended with additional AAF property storage.
 *
 */

package tv.amwa.maj.io.aaf;

import java.util.SortedMap;

import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSWriterListener;

import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinitionWeakObjectReference;
import tv.amwa.maj.record.AUID;

public interface AAFWriterListener 
	extends POIFSWriterListener {

//	public void addPropertyMap(
//			String path,
//			SortedMap<PropertyDefinition, PropertyValue> propertyMap)
//		throws NullPointerException;
	
	public void addProperty(
			String path,
			PropertyDefinition propertyDefinition,
			PropertyValue propertyValue)
		throws NullPointerException,
			IllegalPropertyValueException;
	
	public int registerWeakType(
			TypeDefinitionWeakObjectReference weakType)
		throws NullPointerException;
	
	public int getReferencedPropertiesSize();
	
	public void addIndexValue(
			String indexPath,
			PropertyValue indexValue) 
		throws NullPointerException;
	
	public void registerLocalID(
			PropertyDefinition property);

	public void registerLocalID(
			AUID propertyID,
			short localID);
	
	public short getLocalID(
			AUID propertyID);

	public void registerStreamDocument(
			DocumentEntry streamDocument,
			Stream stream)
		throws NullPointerException;

	public void setInterchangePath(
			String path)
		throws NullPointerException;
	
}
