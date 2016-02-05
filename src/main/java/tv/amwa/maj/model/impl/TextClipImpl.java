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
 * $Log: TextClipImpl.java,v $
 * Revision 1.3  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
 *
 * Revision 1.2  2011/01/26 11:49:03  vizigoth
 * Completed common method testing.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
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
 * Revision 1.1  2007/11/13 22:09:40  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.model.TextClip;

/** 
 * <p>Implements a reference to text essence.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x1200,
		  uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "TextClip",
		  description = "A reference to a track describing text essence data.",
		  symbol = "TextClip",
		  isConcrete = false)
public class TextClipImpl
	extends 
		SourceReferenceSegmentImpl
	implements 
		TextClip,
		Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2030025009937138092L;

	public TextClip clone() {
		
		return (TextClip) super.clone();
	}
}
