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
 * $Log: EssenceStreamDescriptor.java,v $
 * Revision 1.6  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2009/02/13 14:27:29  vizigoth
 * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
 *
 * Revision 1.2  2009/02/10 09:00:14  vizigoth
 * Finished turning C headers to Java method headers.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf;

import java.util.List;

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.impl.EssenceDescriptorImpl;

/**
 * <p>Description of an essence stream.</p>
 * 
 *
 * 
 * @see BodyStream
 */
public class EssenceStreamDescriptor {

//	public:
//
//		UInt32 ID;								//!< ID for this essence stream
	
	private @UInt32 int streamID;
	
//		std::string Description;				//!< Description of this essence stream

	private String description;
	
//		UUID SourceFormat;						//!< A UUID (or byte-swapped UL) identifying the source format

	private UUID sourceFormat;
	
//		MDObjectPtr Descriptor;					//!< Pointer to an actual essence descriptor for this stream

	private EssenceDescriptorImpl descriptor;
	
//		EssenceStreamDescriptorList SubStreams;	//!< A list of sub-streams that can be derived from this stream. See \ref SubStreamNotes

	private List<EssenceStreamDescriptor> subStreams;

	public int getStreamID() {
		return streamID;
	}

	public void setStreamID(
			@UInt32 int streamID) 
		throws IllegalArgumentException {
		
		if (streamID < 0)
			throw new IllegalArgumentException("Cannot set a stream ID to a negative value.");
		
		this.streamID = streamID;
	}

	public String getDescription() {
		
		return description;
	}

	public void setDescription(
			String description) {
		
		this.description = description;
	}

	public UUID getSourceFormat() {
		
		return sourceFormat;
	}

	public void setSourceFormat(
				UUID sourceFormat) {
		
		this.sourceFormat = sourceFormat;
	}

	public EssenceDescriptorImpl getDescriptor() {
		
		return descriptor;
	}

	public void setDescriptor(
			EssenceDescriptorImpl descriptor) {
		
		this.descriptor = descriptor;
	}

	public List<EssenceStreamDescriptor> getSubStreams() {
		
		return subStreams;
	}

	public void setSubStreams(
			List<EssenceStreamDescriptor> subStreams) {
		
		this.subStreams = subStreams;
	}
}
