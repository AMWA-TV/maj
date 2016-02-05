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
 * $Log: CommentMarker.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/27 11:07:40  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:42  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;

/**
 * <p>Specifies a user comment associated with a point in time. A comment marker may
 * have a {@linkplain SourceReferenceSegment source reference} that specifies a text 
 * or audio annotation.</p>
 * 
 *
 * 
 * @see DescriptiveMarker
 */

public interface CommentMarker 
	extends Event {

	/**
	 * <p>Returns the annotation for this comment marker, which specifies a text or
	 * audio annotation. This is an optional property.</p>
	 * 
	 * @return Text or audio annotation.
	 * 
	 * @throws PropertyNotPresentException The optional annotation property is not present in this comment
	 * marker.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#SourceReferenceStrongReference
	 */
	public SourceReferenceSegment getAnnotationSource()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the annotation marker for this comment marker, which specifies a text
	 * or audio annotation. Set this value to <code>null</code> to omit this optional
	 * property.</p>
	 * 
	 * @param annotation Specifies a text or audio annotation.
	 */
	public void setAnnotation(
			SourceReferenceSegment annotation);
	
	/**
	 * <p>Create a cloned copy of this comment marker.</p>
	 *
	 * @return Cloned copy of this comment marker.
	 */
	public CommentMarker clone();
}
