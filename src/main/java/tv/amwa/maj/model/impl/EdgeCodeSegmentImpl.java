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
 * $Log: EdgeCodeSegmentImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2008/01/18 16:09:54  vizigoth
 * Changed header property of an edgecode value to optional.
 *
 * Revision 1.1  2007/11/13 22:09:55  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.EdgeType;
import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadSizeException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.model.EdgeCodeSegment;
import tv.amwa.maj.record.EdgeCodeValue;
import tv.amwa.maj.record.impl.EdgeCodeValueImpl;


/** 
 * <p>Implements the storage of film edge code information.</p>
 * 
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0400,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "EdgeCode",
		  description = "The Edgecode class stores film edge code information.",
		  symbol = "EdgeCode")
public class EdgeCodeSegmentImpl
	extends 
		SegmentImpl
	implements
		EdgeCodeSegment,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -7363833585850894685L;

	private FilmType edgeCodeFilmFormat;
	private EdgeType edgeCodeFormat;
	private long edgeCodeStart;
	private byte[] edgeCodeHeader = null;
	
	public EdgeCodeSegmentImpl() { }

	/**
	 * <p>Creates and initializes a new edgecode segment, which stores film edge 
	 * code information.</p>
	 *
	 * @param length Duration in edit units of this component. 
	 * @param edgecode Edgecode value for the new edgecode segment.
	 * 
	 * @throws NullPointerException Edgecode argument is null.
	 * @throws BadLengthException Cannot set the length of a component to a negative value.
	 * @throws BadSizeException The given edgecode value has a header that is longer than 8 bytes.
	 */
	public EdgeCodeSegmentImpl(
			long length,
			EdgeCodeValue edgecode)
		throws NullPointerException,
			BadLengthException,
			BadSizeException {
		
		if (edgecode == null)
			throw new NullPointerException("Cannot create a new edgecode segment from a null edgecode value.");
		
		setComponentDataDefinition(DataDefinitionImpl.forIdentification(DataDefinitionImpl.Edgecode));
		
		setLengthPresent(true);
		setComponentLength(length);
		
		setEdgecode(edgecode);
	}

	public EdgeCodeValue getEdgecode() {

		EdgeCodeValue edgeCodeValue = new EdgeCodeValueImpl(edgeCodeStart, edgeCodeFilmFormat, edgeCodeFormat);
		if (edgeCodeHeader != null)
			edgeCodeValue.setEdgeCodeHeader(edgeCodeHeader);
		return edgeCodeValue;
	}

	/**
	 * <p>Safe mechanism for setting edgecode values from external implementations, ensuring that an edgecode
	 * header is never greater than 8-bytes in length. </p>
	 *
	 * @param edgecode Edgecode value to use to set the underlying value of this edgecode segment.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws BadSizeException The given edgecode value has a header that is longer than 8 bytes.
	 */
	void setEdgecode(
			EdgeCodeValue edgecode) 
		throws NullPointerException,
			BadSizeException {
		
		if (edgecode == null)
			throw new NullPointerException("Cannot set the edgecode value of this edgecode segment using a null value.");
		
		try {
			// Staying consistent with current reference implementation ... no headers longer than 8 bytes.
			if (edgecode.getEdgeCodeHeader().length > 8) 
				throw new BadSizeException("Cannot use an edgecode value with a header larger than 8 bytes.");
			this.edgeCodeHeader = edgecode.getEdgeCodeHeader();
		}
		catch (PropertyNotPresentException pnpe) {
			this.edgeCodeHeader = null;
		}

		this.edgeCodeStart = edgecode.getEdgeCodeStart();
		this.edgeCodeFilmFormat = edgecode.getEdgeCodeFilmFormat();
		this.edgeCodeFormat = edgecode.getEdgeCodeFormat();
	}
	
	/**
	 * <p>Returns the type of film of the underlying edgecode value.</p>
	 *
	 * @return Type of film of the underlying edgecode value.
	 * 
	 * @see tv.amwa.maj.enumeration.FilmType	 
	 */
	@MediaProperty(uuid1 = 0x04100103, uuid2 = (short) 0x0109, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EdgeCodeFilmFormat",
			aliases = { "FilmKind", "FilmFormat" },
			typeName = "FilmType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0402,
			symbol = "EdgeCodeFilmFormat")
	public FilmType getEdgeCodeFilmFormat() {
		
		return edgeCodeFilmFormat;
	}
	
	@MediaPropertySetter("EdgeCodeFilmFormat")
	public void setEdgeCodeFilmFormat(
			FilmType edgeCodeFilmFormat) 
		throws NullPointerException {
		
		if (edgeCodeFilmFormat == null)
			throw new NullPointerException("Cannot set the film kind of this edgecode segment with a null value.");
		
		this.edgeCodeFilmFormat = edgeCodeFilmFormat;
	}
	
	public final static FilmType initializeEdgeCodeFilmFormat() {
		
		return FilmType.FtNull;
	}
	
	/**
	 * <p>Returns the type of edgecode of the underlying edgecode value.</p>
	 *
	 * @return Type of edgecode of the underlying edgecode value.
	 * 
	 * @see tv.amwa.maj.enumeration.EdgeType
	 */
	@MediaProperty(uuid1 = 0x04100103, uuid2 = (short) 0x0102, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "EdgeCodeFormat",
			aliases = { "CodeFormat" },
			typeName = "EdgeType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0403,
			symbol = "EdgeCodeFormat")
	public EdgeType getEdgeCodeFormat() {
		
		return edgeCodeFormat;
	}
	
	@MediaPropertySetter("EdgeCodeFormat")
	public void setEdgeCodeFormat(
			EdgeType edgeCodeFormat) 
		throws NullPointerException {
		
		if (edgeCodeFormat == null)
			throw new NullPointerException("Cannot set the code format of this digital image descriptor to a null value.");
		
		this.edgeCodeFormat = edgeCodeFormat;
	}
	
	public final static EdgeType initializeEdgeCodeFormat() {
		
		return EdgeType.Null;
	}
	
	/**
	 * <p>Returns the header of the underlying edgecode value, or an empty byte array if the
	 * optional property is not set. This implementation limits headers to 8 bytes in length.</p>
	 *
	 * @return Header of the underlying edgecode value, or an empty byte array if the
	 * optional property is not set.
	 */
	@MediaProperty(uuid1 = 0x01030201, uuid2 = (short) 0x0200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EdgeCodeHeader",
			aliases = { "Header" },
			typeName = "DataValue",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0404,
			symbol = "EdgeCodeHeader")
	public byte[] getEdgeCodeHeader() 
		throws PropertyNotPresentException {
		
		if (edgeCodeHeader == null)
			throw new PropertyNotPresentException("The optional edge code header is not present for this edge code segment.");
		
		return edgeCodeHeader.clone();
	}
	
	public String getEdgeCodeHeaderAsString() {
		
		return getEdgecode().getEdgeCodeHeaderAsString();
	}
	
	@MediaPropertySetter("EdgeCodeHeader")
	public void setEdgeCodeHeader(
			byte[] edgeCodeHeader) 
		throws BadSizeException {
		
		if (edgeCodeHeader == null) {
			this.edgeCodeHeader = null;
			return;
		}
		
		if (edgeCodeHeader.length > 8)
			throw new BadSizeException("An edgcode header can only contain a maximum of 8 bytes.");
	
		this.edgeCodeHeader = edgeCodeHeader.clone();
	}
	
	@MediaPropertyClear("EdgeCodeHeader")
	public void clearEdgeCodeHeader() {
		
		edgeCodeHeader = null;
	}
	
	/**
	 * <p>Returns the edgecode at the beginning of the segment.</p>
	 *
	 * @return Edgecode and the beginning of the segment.
	 */
	@MediaProperty(uuid1 = 0x01040901, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "EdgeCodeStart",
			aliases = { "Start" },
			typeName = "PositionType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0401,
			symbol = "EdgeCodeStart")
	public long getEdgeCodeStart() {
		
		return edgeCodeStart;
	}

	@MediaPropertySetter("EdgeCodeStart")
	public void setEdgeCodeStart(
			@PositionType long startFrame) {
		
		this.edgeCodeStart = startFrame;
	}
	
	public final static long initializeEdgeCodeStart() {
		
		return 0l;
	}

	public EdgeCodeSegment clone() {
	
		return (EdgeCodeSegment) super.clone();
	}
}
