/* 
 **********************************************************************
 *
 * $Id: RecordingDescriptorImpl.java,v 1.1 2011/01/04 10:39:03 vizigoth Exp $
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
 * $Log: RecordingDescriptorImpl.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:25  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.model.RecordingDescriptor;

/** 
 * <p>Implements the description of file source material which has no physical source. When no 
 * physical source exists for file source material, such as in the case of live recordings, a recording
 * source may be used to represent the source. A recording source is analogous to a tape source except 
 * that it does not represent a source that physically existed. It is used to provide a timecode reference 
 * to file source material.</p>
 * 
 * <p>THE COMMENTS FOR THIS CLASS ARE INCOMPLETE.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x4b00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "RecordingDescriptor",
		  description = "The RecordingDescriptor class describes the source of file source material which has no physical source.",
		  symbol = "RecordingDescriptor")
public class RecordingDescriptorImpl
	extends 
		PhysicalDescriptorImpl
	implements 
		RecordingDescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 2576996965392632153L;
	
	/**
	 * <p>Creates and initializes a new recording descriptor, which describes the source of file source 
	 * material that has no physical source.</p>
	 * 
	 * <p>When no physical source exists for file source material, such as in the case of live recordings, 
	 * a recording source may be used to represent the source. A recording source is analogous to a tape 
	 * source except that it does not represent a source that physically existed. It is used to provide 
	 * a timecode reference to file source material.</p>
	 */
	public RecordingDescriptorImpl() { }
	
	public RecordingDescriptor clone() {
		
		return (RecordingDescriptor) super.clone();
	}
}
