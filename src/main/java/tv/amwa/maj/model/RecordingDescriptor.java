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
 * $Log: RecordingDescriptor.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/02/08 11:27:25  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:28  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

/**
 * <p>Specifies the description of file source material which has no physical source. When no 
 * physical source exists for file source material, such as in the case of live recordings, a recording
 * source may be used to represent the source. A recording source is analogous to a tape source except 
 * that it does not represent a source that physically existed. It is used to provide a timecode reference 
 * to file source material.</p>
 * 
 *
 * 
 */

public interface RecordingDescriptor 
	extends PhysicalDescriptor {

	/**
	 * <p>Create a cloned copy of this recording descriptor.</p>
	 *
	 * @return Cloned copy of this recording descriptor.
	 */
	public RecordingDescriptor clone();
}

