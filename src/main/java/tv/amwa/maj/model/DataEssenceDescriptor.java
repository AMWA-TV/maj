/* 
 **********************************************************************
 *
 * $Id: DataEssenceDescriptor.java,v 1.1 2011/01/04 10:39:02 vizigoth Exp $
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
 * $Log: DataEssenceDescriptor.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2008/02/28 12:50:32  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.3  2008/01/29 18:11:22  vizigoth
 * Updated documentation for newly added classes to 1.1.2 and associated fixes.
 *
 * Revision 1.2  2008/01/27 11:07:38  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:26  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.record.AUID;

/** 
 * <p>Specifies the description of a file of data essence and 
 * identifies the data coding of that essence. Data essence includes that specified in MXF
 * mappings for MPEG (SMPTE&nbsp;381M), DV (SMPTE&nbsp;383M), D10 and D11 (SMPTE&nbsp;386M).
 * Data essence often refers to time-varying data, such as subtitles (closed captions).</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public interface DataEssenceDescriptor
	extends AAFFileDescriptor {

	/**
	 * <p>Sets the data essence coding property of this data essence descriptor. Setting
	 * this optional property to <code>null</code> omits the property.</p>
	 *
	 * @param dataEssenceCoding Data essence coding property of this data essence descriptor.
	 */
	public void setDataEssenceCoding(
			AUID dataEssenceCoding);

	/**
	 * <p>Returns the data essence coding property of this data essence descriptor.</p>
	 *
	 * @return Data essence coding property of this data essence descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional data essence coding property
	 * is not present in this data essence descriptor.
	 */
	public AUID getDataEssenceCoding()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this data essence descriptor.</p>
	 *
	 * @return Cloned copy of this data essence descriptor.
	 */
	public DataEssenceDescriptor clone();
}
