/* 
 **********************************************************************
 *
 * $Id: HTMLDescriptorImpl.java,v 1.1 2011/01/04 10:39:03 vizigoth Exp $
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
 * $Log: HTMLDescriptorImpl.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:51  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.model.HTMLDescriptor;


/** 
 * <p>Implements a description of essence data that is in HTML text format.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x2A00,
		  uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "HTMLDescriptor",
		  description = "Describes static essence in the HTML file format.",
		  symbol = "HTMLDescriptor")
public class HTMLDescriptorImpl
	extends 
		AAFFileDescriptorImpl
	implements 
		HTMLDescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 8339939983888809431L;

	public HTMLDescriptorImpl() { }
	
	/**
	 * <p>Creates and initializes a new HTML descriptor, which describes static essence in the
	 * HTML file format.</p>
	 *
	 * @param containerFormat Container format of the described HTML essence.
	 * 
	 * @throws NullPointerException The container format argument is <code>null</code>.
	 */
	public HTMLDescriptorImpl(
			tv.amwa.maj.model.ContainerDefinition containerFormat) 
		throws NullPointerException {
				
		setContainerFormat(containerFormat);
	}

	@Override 
	public HTMLDescriptor clone() {
		
		return (HTMLDescriptor) super.clone();
	}
}
