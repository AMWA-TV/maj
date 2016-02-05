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
 * $Log: TextClip.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/01/29 18:11:22  vizigoth
 * Updated documentation for newly added classes to 1.1.2 and associated fixes.
 *
 * Revision 1.1  2007/11/13 22:08:47  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

/** 
 * <p>Specifies a reference to text essence.</p>
 *
 *
 *
 */
public interface TextClip
	extends SourceReferenceSegment {

	/**
	 * <p>Create a cloned copy of this text clip.</p>
	 *
	 * @return Cloned copy of this text clip.
	 */
	public TextClip clone();
}
