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
 * $Log: VBILine.java,v $
 * Revision 1.4  2009/02/13 15:27:46  vizigoth
 * Replaced DataChunk with core Java ByteBuffer.
 *
 * Revision 1.3  2009/02/10 08:57:24  vizigoth
 * Finished turning C headers to Java method headers.
 *
 * Revision 1.2  2009/02/06 17:00:36  vizigoth
 * Partial creation of method stubs and fields.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf;

import java.nio.ByteBuffer;

import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt8Array;

/**
 * <p>A single line of VBI data.</p>
 * 
 *
 * 
 * @see VBISource
 *
 */
public class VBILine {

//	protected:
//
//		DataChunk Data;					//!< The VBI actual data bytes for this line, packed as per SMPTE-436M

	private ByteBuffer data;
	
//		int LineNumber;					//!< The line number of this line in the frame, plus 0x4000 if the line is in field 2 of an interlaced picture
	
	private int lineNumber;
	
//		VBIWrappingType WrappingType;	//!< The VBI Wrapping type for this line
	
	private VBIWrappingType wrappingType;
	
//		VBISampleCoding SampleCoding;	//!< SampleCoding for this line

	private VBISampleCoding sampleCoding;
	
//		UInt16 SampleCount;		

	private @UInt16 short sampleCount;
//
//
//	public:
//
//		//! Construct a VBILine with no data
//
//		VBILine(int LineNumber, VBIWrappingType Wrapping, VBISampleCoding Coding)
//
//			: LineNumber(LineNumber), WrappingType(Wrapping), SampleCoding(Coding) {};
//
	public VBILine(
			int lineNumber,
			VBIWrappingType wrappingType,
			VBISampleCoding sampleCoding) {
		
		this.lineNumber = lineNumber;
		this.wrappingType = wrappingType;
		this.sampleCoding = sampleCoding;
	}
//
//		//! Construct a VBILine with no data, for an interlaced frame
//
//		VBILine(int Field, int LineNumber, VBIWrappingType Wrapping, VBISampleCoding Coding)
//
//			: WrappingType(Wrapping), SampleCoding(Coding) 
//
//		{
//
//			if(Field == 2) this->LineNumber = 0x4000 + LineNumber;
//
//			else this->LineNumber = LineNumber;
//
//		};
//
	public VBILine(
			int field,
			int lineNumber,
			VBIWrappingType wrappingType,
			VBISampleCoding sampleCoding) {
		
		
		// TODO check field range?
		if (field == 2) this.lineNumber = 0x4000 + lineNumber;
		
		else this.lineNumber = lineNumber;
		
		this.wrappingType = wrappingType;
		this.sampleCoding = sampleCoding;
	}
//
//		//! Construct a VBILine with data
//
//		/*! DRAGONS: The LineData chunk will be cleared by this operation */
//
//		VBILine(int LineNumber, VBIWrappingType Wrapping, VBISampleCoding Coding, UInt16 SampleCount, DataChunkPtr &LineData)
//
//			: LineNumber(LineNumber), WrappingType(Wrapping), SampleCoding(Coding), SampleCount(SampleCount)
//
//		{
//
//			// Take the buffer from LineData (and clear LineData)
//
//			Data.TakeBuffer(LineData, true);
//
//		}
//
	public VBILine(
			int lineNumber,
			VBIWrappingType wrappingType,
			VBISampleCoding sampleCoding,
			@UInt16 short sampleCount,
			ByteBuffer lineData) {
		
		this.lineNumber = lineNumber;
		this.wrappingType = wrappingType;
		this.sampleCoding = sampleCoding;
		
		setData(sampleCount, lineData);
	}
//
//		//! Construct a VBILine with data, for an interlaced frame
//
//		/*! DRAGONS: The LineData chunk will be cleared by this operation */
//
//		VBILine(int Field, int LineNumber, VBIWrappingType Wrapping, VBISampleCoding Coding, UInt16 SampleCount, DataChunkPtr &LineData)
//
//			: LineNumber(LineNumber), WrappingType(Wrapping), SampleCoding(Coding), SampleCount(SampleCount)
//
//		{
//
//			if(Field == 2) this->LineNumber = 0x4000 + LineNumber;
//
//			else this->LineNumber = LineNumber;
//
//
//
//			// Take the buffer from LineData (and clear LineData)
//
//			Data.TakeBuffer(LineData, true);
//
//		}
//
	public VBILine(
			int field,
			int lineNumber,
			VBIWrappingType wrappingType,
			VBISampleCoding sampleCoding,
			@UInt16 short sampleCount,
			ByteBuffer lineData) {
		
		if (field == 2)
			this.lineNumber = 0x4000 + lineNumber;
		else
			this.lineNumber = lineNumber;
		
		this.wrappingType = wrappingType;
		this.sampleCoding = sampleCoding;
		
		setData(sampleCount, lineData);
	}
//
//		//! Set (or replace) the current line data
//
//		/*! DRAGONS: The LineData chunk will be cleared by this operation */
//
//		void SetData(UInt16 SampleCount, DataChunkPtr &LineData)
//
//		{
//
//			// Take the buffer from LineData (and clear LineData)
//
//			Data.TakeBuffer(LineData, true);
//
//
//
//			this->SampleCount = SampleCount;
//
//		}
//
	public void setData(
			@UInt16 short sampleCount,
			ByteBuffer lineData) {
		
		// Create a new view of the line data if available, or create a new array if required
		if (lineData.hasArray())
			data = ByteBuffer.wrap(lineData.array());
		else {
			byte[] lineDataBytes = new byte[lineData.capacity()];
			lineData.get(lineDataBytes);
			data = ByteBuffer.wrap(lineDataBytes);
		}

		this.sampleCount = sampleCount;
	}
	
//
//		//! Get the size of the data buffer, excluding the line number, wrapping type, sample coding and sample count bytes
//
//		size_t GetDataSize(void) { return static_cast<size_t>(Data.Size); }
//
	public int getDataSize() {
		
		return data.capacity();
	}
//
//		//! Get the size of the data buffer, including the line number, wrapping type, sample coding and sample count bytes
//
//		size_t GetFullDataSize(void) { return static_cast<size_t>(Data.Size) + 6; }
//	
	public int getFullDataSize() {
		
		return data.capacity() + 6;
	}
//
//		//! Write the line of data into a buffer, including the line number, wrapping type, sample coding and sample count bytes
//
//		/*! \note It is the caller's responsibility to ensure that the buffer has enough space - the number of bytes written <b>will be</b> GetFullDataSize()
//
//		 */
//
//		void WriteData(UInt8 *Buffer);
//
	public void writeData(
			@UInt8Array byte[] buffer) {
		
		// TODO
	}
	
}
