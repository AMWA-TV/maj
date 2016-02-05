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
 * $Log: SegmentImpl.java,v $
 * Revision 1.2  2011/10/05 17:30:40  vizigoth
 * Changing class abstraction to metadata only to support application metadata plugin class definition extensions.
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
 * Revision 1.1  2007/11/13 22:09:42  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.AdjacentTransitionException;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.BadSampleOffsetException;
import tv.amwa.maj.exception.EventSemanticsException;
import tv.amwa.maj.exception.InsufficientTransitionMaterialException;
import tv.amwa.maj.exception.InvalidDataDefinitionException;
import tv.amwa.maj.exception.LeadingTransitionException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.TimecodeNotFoundException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.model.Component;
import tv.amwa.maj.model.Segment;
import tv.amwa.maj.model.Sequence;
import tv.amwa.maj.model.TimecodeSegment;
import tv.amwa.maj.model.impl.TrackImpl.FoundSegment;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.TimecodeValue;
import tv.amwa.maj.union.impl.TimecodeClipImpl;


/** 
 * <p>Implements a {@linkplain tv.amwa.maj.model.Component component} that is independent of any surrounding
 * object. Contrast this to a {@linkplain tv.amwa.maj.model.Transition transition} which is a component that
 * depends on other components to establish its value.</p>
 *
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#SegmentStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#SegmentStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0300,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Segment",
		  description = "The Segment class represents a Component that is independent of any surrounding object.",
		  symbol = "Segment",
		  isConcrete = false)
public class SegmentImpl
	extends 
		ComponentImpl
	implements
		Segment,
		tv.amwa.maj.extensions.quantel.QSegment,
		Serializable,
		Cloneable {

	// No private fields
	
	// Begin - Quantel extensions
	
	private Stream segmentBlob = null;
	private Integer cutPoint = null;
	private Segment trackRenderSequence = null;
	private Integer packLayerKeyIndicator = null;
	private Segment trackKeyRenderSequence = null;
	private Stream trackBlob = null;
	private Long keyOffset = null;
	private String trackName = null;
	
	
    @MediaProperty(uuid1 = 0x10068b11, uuid2 = (short) 0x70a2, uuid3 = (short) 0x4d54,
        uuid4 = { (byte) 0xaf, (byte) 0xd1, (byte) 0x40, (byte) 0x9c, (byte) 0xb3, (byte) 0x91, (byte) 0x9a, (byte) 0xe6 },
        definedName = "Segment blob",
        symbol = "Segment_blob",
        aliases = { "Segment_blob" },
        typeName = "Stream",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public Stream getSegmentBlob()
		throws PropertyNotPresentException {
		
		if (segmentBlob == null)
			throw new PropertyNotPresentException("The optional segment blob property is not present for this Quantel segment.");
		
		return segmentBlob;
	}
	
	@MediaPropertySetter("Segment blob")
	public void setSegmentBlob(
			Stream segmentBlob) {
		
		this.segmentBlob = segmentBlob;
	}
	
    @MediaProperty(uuid1 = 0x26fbc046, uuid2 = (short) 0x4294, uuid3 = (short) 0x49a0,
        uuid4 = { (byte) 0x91, (byte) 0xbf, (byte) 0x31, (byte) 0xf0, (byte) 0x2d, (byte) 0xe5, (byte) 0x63, (byte) 0x35 },
        definedName = "CutPoint",
        symbol = "CutPoint",
        aliases = { "CutPoint" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getCutPoint()
		throws PropertyNotPresentException {
		
		if (cutPoint == null)
			throw new PropertyNotPresentException("The optional cut point property is not present for this Quantel segment.");
		
		return cutPoint;
	}

	@MediaPropertySetter("CutPoint")
	public void setCutPoint(
			@Int32 Integer cutPoint) {
		
		this.cutPoint = cutPoint;
	}
	
    @MediaProperty(uuid1 = 0xdea04170, uuid2 = (short) 0xa146, uuid3 = (short) 0x4a34,
        uuid4 = { (byte) 0x80, (byte) 0x81, (byte) 0x74, (byte) 0xe9, (byte) 0xb6, (byte) 0x2b, (byte) 0x95, (byte) 0x3c },
        definedName = "Track render sequence",
        symbol = "Track_render_sequence",
        aliases = { "Track_render_sequence" },
        typeName = "SegmentStrongReference",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public Segment getTrackRenderSequence()
		throws PropertyNotPresentException {
		
		if (trackRenderSequence == null)
			throw new PropertyNotPresentException("The optional track render sequence property is not present for this Quantel segment.");
		
		return trackRenderSequence;
	}
	
	@MediaPropertySetter("Track render sequence")
	public void setTrackRenderSequence(
			Segment trackRenderSequence) {
		
		this.trackRenderSequence = trackRenderSequence;
	}
	
    @MediaProperty(uuid1 = 0x7df1077a, uuid2 = (short) 0x4f85, uuid3 = (short) 0x4cbc,
        uuid4 = { (byte) 0x98, (byte) 0x8e, (byte) 0x9b, (byte) 0xbc, (byte) 0xc1, (byte) 0x0e, (byte) 0x14, (byte) 0x6e },
        definedName = "Pack layer key indicator",
        symbol = "Pack_layer_key_indicator",
        aliases = { "Pack_layer_key_indicator" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getPackLayerKeyIndicator()
		throws PropertyNotPresentException {
		
		if (packLayerKeyIndicator == null)
			throw new PropertyNotPresentException("The optional pack layer key indicator property is not present for this Quantel segment.");
		
		return packLayerKeyIndicator;
	}
		
	@MediaPropertySetter("Pack layer key indicator")
	public void setPackLayerKeyIndicator(
			@Int32 Integer packLayerKeyIndicator) {
		
		this.packLayerKeyIndicator = packLayerKeyIndicator;
	}
	
    @MediaProperty(uuid1 = 0xeee91a9d, uuid2 = (short) 0x14ae, uuid3 = (short) 0x4400,
        uuid4 = { (byte) 0x9e, (byte) 0x1f, (byte) 0x48, (byte) 0x85, (byte) 0x19, (byte) 0x65, (byte) 0x51, (byte) 0xd8 },
        definedName = "Track key render sequence",
        symbol = "Track_key_render_sequence",
        aliases = { "Track_key_render_sequence" },
        typeName = "SegmentStrongReference",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public Segment getTrackKeyRenderSequence()
		throws PropertyNotPresentException {
		
		if (trackKeyRenderSequence == null)
			throw new PropertyNotPresentException("The optional track key render sequence property is not present for this Quantel segment.");
		
		return trackKeyRenderSequence;
	}
	
	@MediaPropertySetter("Track key render sequence")
	public void setTrackKeyRenderSequence(
			Segment trackKeyRenderSequence) {
		
		this.trackKeyRenderSequence = trackKeyRenderSequence;
	}
	
    @MediaProperty(uuid1 = 0x3f40f6b3, uuid2 = (short) 0x3d36, uuid3 = (short) 0x49ce,
        uuid4 = { (byte) 0xa7, (byte) 0x8b, (byte) 0xa1, (byte) 0x9a, (byte) 0x88, (byte) 0xf0, (byte) 0x4d, (byte) 0xcb },
        definedName = "Track blob",
        symbol = "Track_blob",
        aliases = { "Track_blob" },
        typeName = "Stream",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public Stream getTrackBlob()
		throws PropertyNotPresentException {
		
		if (trackBlob == null)
			throw new PropertyNotPresentException("THe optional track blob property is not present for this Quantel segment.");
		
		return trackBlob;
	}
	
	@MediaPropertySetter("Track blob")
	public void setTrackBlob(
			Stream trackBlob) {
		
		this.trackBlob = trackBlob;
	}
	
    @MediaProperty(uuid1 = 0x4ca1a9dc, uuid2 = (short) 0x9d3e, uuid3 = (short) 0x48ea,
        uuid4 = { (byte) 0xa5, (byte) 0x39, (byte) 0x6d, (byte) 0xcd, (byte) 0xfb, (byte) 0x64, (byte) 0xf0, (byte) 0xed },
        definedName = "Key offset",
        symbol = "Key_offset",
        aliases = { "Key_offset" },
        typeName = "Int64",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int64 long getKeyOffset()
		throws PropertyNotPresentException {
		
		if (keyOffset == null)
			throw new PropertyNotPresentException("The optional key offset property is not present for this Quantel segment.");
		
		return keyOffset;
	}
	
	@MediaPropertySetter("Key offset")
	public void setKeyOffset(
			@Int64 Long keyOffset) {
		
		this.keyOffset = keyOffset;
	}
	
    @MediaProperty(uuid1 = 0xd1e9aaec, uuid2 = (short) 0x49d1, uuid3 = (short) 0x452f,
        uuid4 = { (byte) 0x85, (byte) 0xe9, (byte) 0x59, (byte) 0x83, (byte) 0xb1, (byte) 0xbb, (byte) 0x47, (byte) 0x2d },
        definedName = "TrackName",
        symbol = "TrackName",
        aliases = { "TrackName" },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public String getTrackName()
		throws PropertyNotPresentException {
		
		if (trackName == null)
			throw new PropertyNotPresentException("The optional track name property is not present for this Quantel segment.");
		
		return trackName;
	}

	@MediaPropertySetter("TrackName")
	public void setTrackName(
			String trackName) {
		
		this.trackName = trackName;
	}
	
	// End - Quantel extensions

	
	// TODO make sure timecode methods are implemented in appropriate segments

	/**
	 * 
	 */
	private static final long serialVersionUID = -2420966923728552880L;

	public TimecodeValue segmentOffsetToTC(
			long offset)
		throws TimecodeNotFoundException {
		
		throw new TimecodeNotFoundException("Timecode value was not found for this segment.");
	}

	public long segmentTCToOffset(
			TimecodeValue timecode,
			Rational editRate)
		throws NullPointerException,
			TimecodeNotFoundException,
			BadSampleOffsetException {
		
		if (timecode == null)
			throw new NullPointerException("Cannot calculate segment timecode offset using a null timecode value.");
		if (editRate == null)
			throw new NullPointerException("Cannot calculate segment timecode offset using a null edit rate value.");
		
		throw new TimecodeNotFoundException("Timecode value was not found for this segment.");
	}

	public Segment clone() {

		return (Segment) super.clone();
	}

	public Sequence generateSequence() {

		Sequence sequence = new SequenceImpl(getComponentDataDefinition());
		try {
			sequence.appendComponentObject(this);
		}
		catch (InvalidDataDefinitionException e) {
			// Fixed the data definition kind
		} 
		catch (LeadingTransitionException e) {
			// Appending a segment
		} 
		catch (EventSemanticsException e) {
			// Possible if appending an event, so show stack trace just in case
			e.printStackTrace();
		} 
		catch (BadPropertyException e) {
			// Possible although not likely so show stack trace
			e.printStackTrace();
		} 
		catch (BadLengthException e) {
			// Possible, although very unlikely if other exception checking is working
			e.printStackTrace();
		} 
		catch (AdjacentTransitionException e) {
			// Appending a segment
		} 
		catch (InsufficientTransitionMaterialException e) {
			// Not possible when appending the first element
		}
		
		return sequence;
	}
	
	// Used in source package findTimecodeClip() ... overriden in TimecodeSegment
	static TimecodeClipImpl offsetToTimecodeClip(
			Segment relativeTo,
			long offset) {
		
		try {
			return new TimecodeClipImpl(null, relativeTo.getComponentLength(), 0l);
		}
		catch (BadPropertyException bpe) {
			return new TimecodeClipImpl(null, 0l, 0l);
		}
	}

	static TimecodeClipImpl offsetToTimecodeClip(
			TimecodeSegment relativeTo,
			long offset) {
		
		try {
			return new TimecodeClipImpl(relativeTo, relativeTo.getComponentLength(), 0l);
		}
		catch (BadPropertyException bpe) {
			/* Length always present for timecode segments. */
			return null;
		}
	}
	
	/**
	 * <p>Finds the sub segment of this segment at the given offset. For all segments other
	 * than sequences, this is just this. For sequences, a search for the material at the
	 * given offset takes place.</p>
	 * 
	 * <p>If the offset is out of range, <code>null</code> is returned.</p>
	 * 
	 * @see SequenceImpl#findSubSegment(long)
	 *
	 * @param offset Offset at which to search for a segment.
	 * @return This segment if the offset is in range, otherwise <code>null</code>.
	 */
	static FoundSegment findSubSegment(
			Segment toSearch,
			long offset) {
		
		try {
			Segment subSegment = null;
			
			long segmentLength = toSearch.getComponentLength();
			long beginPosition = 0l, endPosition = segmentLength;
			if ((beginPosition <= offset) && (offset < endPosition)) {
				subSegment = toSearch;
			}
			else if ((beginPosition == endPosition) && (offset == 0l)) {
				subSegment = toSearch;
			}
			else 
				return null;
			
			FoundSegment foundSegment = new TrackImpl.FoundSegment();
			foundSegment.rootObject = subSegment;
			foundSegment.differencePosition = offset;
			
			return foundSegment;
		} 
		catch (BadPropertyException e) {
			return null;
		}
	}

	/** 
	 * <p>Search through all the components of this sequence to find the one at the 
	 * given offset.</p>
	 * 
	 * @see tv.amwa.maj.model.impl.SegmentImpl#findSubSegment(long)
	 */
	static FoundSegment findSubSegment(
			Sequence toSearch,
			long offset) {
		
		try {
			Component subSegment = null;
			
			long segmentLength = toSearch.getComponentLength();
			long beginPosition = 0l, endPosition = segmentLength;
			
			if ((beginPosition <= offset) && (offset < endPosition)) {
			
				for (Component component : toSearch.getComponentObjects()) {
					
					segmentLength = component.getComponentLength();
					endPosition += segmentLength;
					if ((beginPosition <= offset) && (offset < endPosition)) {
						subSegment = component;
						break;
					}
					else {
						beginPosition = endPosition;
					}
				}
			}
			else if ((beginPosition == endPosition) && (offset == 0l)) {
				subSegment = toSearch;
			}
			else
				return null;

			FoundSegment foundSegment = new TrackImpl.FoundSegment();
			foundSegment.rootObject = subSegment;
			foundSegment.differencePosition = offset;
			
			return foundSegment;
		} 
		catch (BadPropertyException e) {
			e.printStackTrace();
			return null;
		}
	}
}
