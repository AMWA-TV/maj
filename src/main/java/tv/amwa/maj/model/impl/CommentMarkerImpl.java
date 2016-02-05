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
 * $Log: CommentMarkerImpl.java,v $
 * Revision 1.2  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
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
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:23  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.model.CommentMarker;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.SourceReferenceSegment;


/** 
 * <p>Implements a user comment associated with a point in time. A comment marker may
 * have a {@linkplain SourceReferenceSegment source reference} that specifies a text 
 * or audio annotation.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0800,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "CommentMarker",
		  description = "The CommentMarker class specifies a user comment that is associated with a point in time.",
		  symbol = "CommentMarker")
public class CommentMarkerImpl
	extends EventImpl
	implements CommentMarker,
			Serializable,
			Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 6305838043898648133L;

	private SourceReferenceSegment annotationSource = null;
	
	/** Default constructor is not public to avoid unset required fields. */
	public CommentMarkerImpl() { }

	/**
	 * <p>Creates and initializes a commment marker for the given kind of essence data, which
	 * specifies a user comment that is associated with a point in time. If this event is in an 
	 * {@link EventTrackImpl event track}, the position must also be set using 
	 * {@link EventImpl#setEventPosition(long)}. Call 
	 * {@link CommentMarkerImpl#setAnnotation(SourceReferenceSegment)} to set the optional annotation.</p>
	 *
	 * @param dataDefinition Kind of data represented by this component.
	 * 
	 * @throws NullPointerException Argument is <code>null</code>.
	 */
	public CommentMarkerImpl(
			DataDefinition dataDefinition)
		throws NullPointerException {
		
		// Assuming length is not important for comment markers as they represent a point in time
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a new comment marker with a null data definition.");
		
		setComponentDataDefinition(dataDefinition);
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x020a, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "AnnotationSource",
			aliases = { "Annotation" },
			typeName = "SourceReferenceStrongReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0901,
			symbol = "AnnotationSource")
	public SourceReferenceSegment getAnnotationSource() 
		throws PropertyNotPresentException {

		if (annotationSource == null)
			throw new PropertyNotPresentException("The optional annotation property is not present in this comment marker.");
		
		return annotationSource;
	}

	@MediaPropertySetter("AnnotationSource")
	public void setAnnotation(
			SourceReferenceSegment annotationSource) {

		if (annotationSource == null) {
			this.annotationSource = null;
			return;
		}
		
		this.annotationSource = annotationSource;
	}

	public CommentMarker clone() {

		return (CommentMarker) super.clone();
	}
}
