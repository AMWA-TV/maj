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
 * $Log: VBISource.java,v $
 * Revision 1.6  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/02/13 15:27:46  vizigoth
 * Replaced DataChunk with core Java ByteBuffer.
 *
 * Revision 1.3  2009/02/13 14:27:29  vizigoth
 * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
 *
 * Revision 1.2  2009/02/10 08:57:24  vizigoth
 * Finished turning C headers to Java method headers.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.record.impl.RationalImpl;

/**
 * <p>VBI data for a frame, supplied as an {@linkplain EssenceSource essence source}.</p>
 * 
 *
 * 
 * @see VBILine
 */
public class VBISource 
	extends EssenceSource {

//	protected:
//
//		EssenceSourceParent MasterSource;	//!< The EssenceSource for the picture essence to which this VBI data relates
//
	private EssenceSource masterSource;
//
//		VBILineMap Lines;					//!< Map of lines for this frame
//
	private Map<Integer, VBILine> lines;
//
//		DataChunkList BufferedData;			//!< List of data items prepared and ready to be supplied in response to GetEssenceData() - next to be supplied is the head
//
	private List<ByteBuffer> bufferedData;
//
//		size_t BufferOffset;				//!< An offset into the current data buffer if we are returning a partial chunk in GetEssenceData()
//
	private int bufferOffset;
//
//		Position CurrentPosition;			//!< Our current position
//
	private @MXFPosition long currentPosition;
//
//	public:
//
//		// Base constructor
//
//		VBISource(EssenceSource *Master) : MasterSource(Master), BufferOffset(0), CurrentPosition(0) {};
//
	public VBISource(
			EssenceSource master) {
		
		this.masterSource = master;
		bufferOffset = 0;
		currentPosition = 0;
	}
//
//		//! Virtual destructor to allow polymorphism
//
//		virtual ~VBISource() {};
//
//
//
//		// Add a line of data
//
//		void AddLine(int LineNumber, VBIWrappingType Wrapping, VBISampleCoding Coding, UInt16 SampleCount, DataChunkPtr &LineData);
//
	public void addLine(
			int lineNumber,
			VBIWrappingType wrappingType,
			VBISampleCoding sampleCoding,
			@UInt16 short sampleCount,
			ByteBuffer lineData) {
		
		// TODO
	}
//
//		// Add a line of data, for an interlaced frame
//
//		void AddLine(int Field, int LineNumber, VBIWrappingType Wrapping, VBISampleCoding Coding, UInt16 SampleCount, DataChunkPtr &LineData)
//
//		{
//
//			if(Field == 2) LineNumber += 0x4000;
//
//			AddLine(LineNumber, Wrapping, Coding, SampleCount, LineData);
//
//		}
//
	public void addLine(
			int field,
			int lineNumber,
			VBIWrappingType wrappingType,
			VBISampleCoding sampleCoding,
			@UInt16 short sampleCount,
			ByteBuffer lineData) {
		
		if (field == 2)
			lineNumber += 0x4000;
		
		addLine(lineNumber, wrappingType, sampleCoding, sampleCount, lineData);
	}

//
//		//! Get the size of the essence data in bytes
//
//		/*! \note There is intentionally no support for an "unknown" response */
//
//		virtual size_t GetEssenceDataSize(void);
//	
	public int getEssenceDataSize() {
		
		// TODO
		return 0;
	}
//
//		//! Get the next "installment" of essence data
//
//		/*! This will attempt to return an entire wrapping unit (e.g. a full frame for frame-wrapping) but will return it in
//
//		 *  smaller chunks if this would break the MaxSize limit. If a Size is specified then the chunk returned will end at
//
//		 *  the first wrapping unit end encountered before Size. On no account will portions of two or more different wrapping
//
//		 *  units be returned together. The mechanism for selecting a type of wrapping (e.g. frame, line or clip) is not 
//
//		 *  (currently) part of the common EssenceSource interface.
//
//		 *  \return Pointer to a data chunk holding the next data or a NULL pointer when no more remains
//
//		 *	\note If there is more data to come but it is not currently available the return value will be a pointer to an empty data chunk
//
//		 *	\note If Size = 0 the object will decide the size of the chunk to return
//
//		 *	\note On no account will the returned chunk be larger than MaxSize (if MaxSize > 0)
//
//		 */
//
//		virtual DataChunkPtr GetEssenceData(size_t Size = 0, size_t MaxSize = 0);
//
	public ByteBuffer getEssenceData(
			int size,
			int maxSize) {
		
		// TODO
		return null;
	}

//
//		//! Did the last call to GetEssenceData() return the end of a wrapping item
//
//		/*! \return true if the last call to GetEssenceData() returned an entire wrapping unit.
//
//		 *  \return true if the last call to GetEssenceData() returned the last chunk of a wrapping unit.
//
//		 *  \return true if the last call to GetEssenceData() returned the end of a clip-wrapped clip.
//
//		 *  \return false if there is more data pending for the current wrapping unit.
//
//		 *  \return false if the source is to be clip-wrapped and there is more data pending for the clip
//
//		 */
//
//		virtual bool EndOfItem(void) { return (BufferOffset == 0); }
//
	public boolean endOfItem() {
		
		return (bufferOffset == 0);
	}
//
//		//! Is all data exhasted?
//
//		/*! \return true if a call to GetEssenceData() will return some valid essence data
//
//		 */
//
//		virtual bool EndOfData(void) 
//
//		{ 
//
//			if(!MasterSource) return true;
//
//			return MasterSource->EndOfData();
//
//		}
//
	public boolean endOfData() {
		
		// TODO
		return false;
	}
//
//		//! Get the GCEssenceType to use when wrapping this essence in a Generic Container
//
//		virtual UInt8 GetGCEssenceType(void) { return 0x17; }
//
	public @UInt8 byte getGenericContainerEssenceType() {
		
		return 0x17;
	}
//
//		//! Get the GCEssenceType to use when wrapping this essence in a Generic Container
//
//		virtual UInt8 GetGCElementType(void) { return 0x01; }
//
	public @UInt8 byte getGenericContainerElementType() {
		
		return 0x01;
	}
//
//		//! Get the edit rate of this wrapping of the essence
//
//		/*! \note This may not be the same as the original "native" edit rate of the
//
//		 *        essence if this EssenceSource is wrapping to a different edit rate 
//
//		 */
//
//		virtual Rational GetEditRate(void)
//
//		{ 
//
//			if(!MasterSource) return Rational(1,1);
//
//			return MasterSource->GetEditRate();
//
//		}
//
	public RationalImpl getEditRate() {
		
		// TODO
		return null;
	}
//
//		//! Get the current position in GetEditRate() sized edit units
//
//		/*! This is relative to the start of the stream, so the first edit unit is always 0.
//
//		 *  This is the same as the number of edit units read so far, so when the essence is 
//
//		 *  exhausted the value returned shall be the size of the essence
//
//		 */
//
//		virtual Position GetCurrentPosition(void)
//
//		{
//
//			return CurrentPosition;
//
//		}
//
	public @MXFPosition long getCurrentPosition() {
		
		return currentPosition;
	}
//
//		//! Get the preferred BER length size for essence KLVs written from this source, 0 for auto
//
//		virtual int GetBERSize(void) { return 4; }
//
	public int getBERSize() {
		
		return 4;
	}
//
//		//! Is this picture essence?
//
//		virtual bool IsPictureEssence(void) { return false; }
//
	public boolean isPictureEssence() {
		
		return false;
	}
//
//		//! Is this sound essence?
//
//		virtual bool IsSoundEssence(void) { return false; }
//
	public boolean isSoundEssence() {
		
		return false;
	}
//
//		//! Is this data essence?
//
//		virtual bool IsDataEssence(void) { return true; }
//
	public boolean isDataEssence() {
		
		return true;
	}
//
//		//! Is this compound essence?
//
//		virtual bool IsCompoundEssence(void) { return false; }
//
	public boolean isCompoundEssence() {
		
		return false;
	}
//
//		//! An indication of the relative write order to use for this stream
//
//		/*! Normally streams in a GC are ordered as follows:
//
//		 *  - All the CP system items (in Scheme ID then Element ID order)
//
//		 *  - All the GC system items (in Scheme ID then Element ID order)
//
//		 *  - All the CP picture items (in Element ID then Element Number order)
//
//		 *  - All the GC picture items (in Element ID then Element Number order)
//
//		 *  - All the CP sound items (in Element ID then Element Number order)
//
//		 *  - All the GC sound items (in Element ID then Element Number order)
//
//		 *  - All the CP data items (in Element ID then Element Number order)
//
//		 *  - All the GC data items (in Element ID then Element Number order)
//
//		 *  - All the GC compound items (in Element ID then Element Number order) (no GC compound)
//
//		 *
//
//		 *  However, sometimes this order needs to be overridden - such as for VBI data preceding picture items.
//
//		 *
//
//		 *  The normal case for ordering of an essence stream is for RelativeWriteOrder to return 0,
//
//		 *  indicating that the default ordering is to be used. Any other value indicates that relative
//
//		 *  ordering is required, and this is used as the Position value for a SetRelativeWriteOrder()
//
//		 *  call. The value of Type for that call is acquired from RelativeWriteOrderType()
//
//		 *
//
//		 * For example: to force a source to be written between the last GC sound item and the first CP data
//
//		 *              item, RelativeWriteOrder() can return any -ve number, with RelativeWriteOrderType()
//
//		 *				returning 0x07 (meaning before CP data). Alternatively RelativeWriteOrder() could
//
//		 *				return a +ve number and RelativeWriteOrderType() return 0x16 (meaning after GC sound)
//
//		 */
//
//		virtual Int32 RelativeWriteOrder(void) 
//
//		{ 
//
//			// We need to be BEFORE the CP picture data
//
//			return -1; 
//
//		}
//
	public @Int32 int relativeWriteOrder() {
		
		return -1;
	}
//
//		//! The type for relative write-order positioning if RelativeWriteOrder() != 0
//
//		/*! This method indicates the essence type to order this data before or after if reletive write-ordering is used 
//
//		 */
//
//		virtual int RelativeWriteOrderType(void) 
//
//		{ 
//
//	// We need to be before the CP PICTURE DATA
//
//			return 0x05; 
//
//		}
//
	public int relativeWriteOrderType() {
		
		// We need to be before the CP PICTURE DATA
		return 0x05;
	}
//
//	protected:
//
//		//! Build the VBI data for this frame in SMPTE-436M format
//
//		DataChunkPtr BuildChunk(void);
//
	ByteBuffer buildChunk() {
		
		// TODO
		return null;
	}
	
}
