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
 * $Log: EssenceSource.java,v $
 * Revision 1.7  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/01/19 14:44:21  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
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
 * Revision 1.2  2009/02/10 09:00:14  vizigoth
 * Finished turning C headers to Java method headers.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf;

import java.nio.ByteBuffer;

import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.record.impl.RationalImpl;

/**
 * <p>Objects that supply large quantities of essence data. An essence source is used when 
 * clip-wrapping to prevent large quantities of data being loaded into memory.</p>
 * 
 *
 * 
 * @see WrappingOption
 *
 */
public abstract class EssenceSource {

//	//! Holds the stream ID for this essence stream when added to a GCWriter
//
//	/*! This value is persisted here between calls to a GCWriter via BodyWriter or similar.
//
//	 *  Set to -1 if no stream ID yet set.
//
//	 */
//
//	GCStreamID StreamID;
//	
	private @GCStreamID int streamID = -1;
//
//	//! Index manager to use if we can index the essence
//
//	IndexManagerPtr IndexMan;
//
//	IndexManager indexManager;
//
//	//! Sub-stream ID to use for our index data if we can index the essence
//
//	int IndexStreamID;
//
	int indexStreamID;
//
//	//! If the default essence key has been overridden for this source it is stored here
//
//	DataChunkPtr SpecifiedKey;
//
	private ByteBuffer specifiedKey;
//
//	//! True if the default essence key has been overridden with a key that does not use GC track number mechanism
//
//	bool NonGC;
//	
	private boolean nonGenericContainer;
//
//public:
//
//	// Base constructor
//
//	EssenceSource() : StreamID(-1) {};
//
	public EssenceSource() {
		
	}

//
//	//! Get the size of the essence data in bytes
//
//	/*! \note There is intentionally no support for an "unknown" response */
//
//	virtual size_t GetEssenceDataSize(void) = 0;
//
	public abstract int getEssenceDataSize();
//
//	//! Get the next "installment" of essence data
//
//	/*! This will attempt to return an entire wrapping unit (e.g. a full frame for frame-wrapping) but will return it in
//
//	 *  smaller chunks if this would break the MaxSize limit. If a Size is specified then the chunk returned will end at
//
//	 *  the first wrapping unit end encountered before Size. On no account will portions of two or more different wrapping
//
//	 *  units be returned together. The mechanism for selecting a type of wrapping (e.g. frame, line or clip) is not 
//
//	 *  (currently) part of the common EssenceSource interface.
//
//	 *  \return Pointer to a data chunk holding the next data or a NULL pointer when no more remains
//
//	 *	\note If there is more data to come but it is not currently available the return value will be a pointer to an empty data chunk
//
//	 *	\note If Size = 0 the object will decide the size of the chunk to return
//
//	 *	\note On no account will the returned chunk be larger than MaxSize (if MaxSize > 0)
//
//	 */
//
//	virtual DataChunkPtr GetEssenceData(size_t Size = 0, size_t MaxSize = 0) = 0;
//
	public abstract ByteBuffer getEssenceData(
			int size,
			int maxSize);
	
	public ByteBuffer getEssenceData() {
		
		return getEssenceData(0, 0);
	}
	
//
//	//! Did the last call to GetEssenceData() return the end of a wrapping item
//
//	/*! \return true if the last call to GetEssenceData() returned an entire wrapping unit.
//
//	 *  \return true if the last call to GetEssenceData() returned the last chunk of a wrapping unit.
//
//	 *  \return true if the last call to GetEssenceData() returned the end of a clip-wrapped clip.
//
//	 *  \return false if there is more data pending for the current wrapping unit.
//
//	 *  \return false if the source is to be clip-wrapped and there is more data pending for the clip
//
//	 */
//
//	virtual bool EndOfItem(void) = 0;
//
	public abstract boolean endOfItem();
//
//	//! Is all data exhasted?
//
//	/*! \return true if a call to GetEssenceData() will return some valid essence data
//
//	 */
//
//	virtual bool EndOfData(void) = 0;
//
	public abstract boolean endOfData();
//
//	//! Get the GCEssenceType to use when wrapping this essence in a Generic Container
//
//	virtual UInt8 GetGCEssenceType(void) = 0;
//
	public abstract @UInt8 byte getGenericContainerEssenceType();
//
//	//! Get the GCEssenceType to use when wrapping this essence in a Generic Container
//
//	virtual UInt8 GetGCElementType(void) = 0;
//
	public abstract @UInt8 byte getGenericContainerElementType();
//
//	//! Set the stream ID for this stream or sub-stream
//
//	void SetStreamID(GCStreamID NewID) { StreamID = NewID; }
//	
	public void setStreamID(
			@GCStreamID int streamID) {
		
		this.streamID = streamID;
	}
//
//	//! Get the stream ID for this stream or sub-stream
//
//	GCStreamID GetStreamID(void) { return StreamID; }
//
	public @GCStreamID int getStreamID() {
		
		return streamID;
	}
//
//	//! Is the last data read the start of an edit point?
//
//	virtual bool IsEditPoint(void) { return true; }
//
	public boolean isEditPoint() {
		
		return true;
	}
//
//	//! Get the edit rate of this wrapping of the essence
//
//	/*! \note This may not be the same as the original "native" edit rate of the
//
//	 *        essence if this EssenceSource is wrapping to a different edit rate 
//
//	 */
//
//	virtual Rational GetEditRate(void) = 0;
//
	public abstract RationalImpl getEditRate();
//
//	//! Get the current position in GetEditRate() sized edit units
//
//	/*! This is relative to the start of the stream, so the first edit unit is always 0.
//
//	 *  This is the same as the number of edit units read so far, so when the essence is 
//
//	 *  exhausted the value returned shall be the size of the essence
//
//	 */
//
//	virtual Position GetCurrentPosition(void) = 0;
//
	public abstract @MXFPosition long getCurrentPosition();
//
//	//! Get the preferred BER length size for essence KLVs written from this source, 0 for auto
//
//	virtual int GetBERSize(void) { return 0; }
//
	public int getBERSize() {
		
		return 0;
	}
//
//	//! Set a source type or parser specific option
//
//	/*! \return true if the option was successfully set */
//
//	virtual bool SetOption(std::string Option, Int64 Param = 0) { return false; } ;
//
	public boolean setOption(
			String option,
			@Int64 long param) {
		
		return false;
	}
	
	public boolean setOption(
			String option) {
		
		return setOption(option, 0l);
	}
	
	
//
//	//! Get BytesPerEditUnit if Constant, else 0
//
//	/*! \note This value may be useful even if CanIndex() returns false
//
//	 */
//
//	virtual UInt32 GetBytesPerEditUnit(UInt32 KAGSize = 1) { return 0; }
//
	public @UInt32 int getBytesPerEditUnit(
			@UInt32 int kagSize) {
		
		return 0;
	}

	public @UInt32 int getBytesPerEditUnit() {
		
		return getBytesPerEditUnit(1);
	}
	
	//
//	//! Can this stream provide indexing
//
//	/*! If true then SetIndex Manager can be used to set the index manager that will receive indexing data
//
//	 */
//
//	virtual bool CanIndex() { return false; }
//
	public boolean canIndex() {
		
		return false;
	}
//
//	//! Set the index manager to use for building index tables for this essence
//
//	/*! \note The values are stored even if this stream does not support indexing as a derived stream may do
//
//	 */
//
//	virtual void SetIndexManager(IndexManagerPtr &Manager, int StreamID)
//
//	{
//
//		IndexMan = Manager;
//
//		IndexStreamID = StreamID;
//
//	}
//
//	public void setIndexManager(
//			IndexManager indexManager,
//			int indexStreamID) {
//		
//		this.indexManager = indexManager;
//		this.indexStreamID = indexStreamID;
//	}
//
//	//! Get the index manager
//
//	virtual IndexManagerPtr &GetIndexManager(void) { return IndexMan; }
//
//	public IndexManager getIndexManager() {
//		
//		return indexManager;
//	}
//
//	//! Get the index manager sub-stream ID
//
//	virtual int GetIndexStreamID(void) { return IndexStreamID; }
//
	public int getIndexStreamID() {
		
		return indexStreamID;
	}
//
//	//! Override the default essence key
//
//	virtual void SetKey(DataChunkPtr &Key, bool NonGC = false)
//
//	{
//
//		ASSERT(Key->Size == 16);
//
//
//
//		SpecifiedKey = Key;
//
//		this->NonGC = NonGC;
//
//	}
//
	public void setKey(
			ByteBuffer key,
			boolean nonGenericContainer) 
		throws IllegalArgumentException {
		
		if (key.capacity() != 16)
			throw new IllegalArgumentException("The essence key must contain exactly 16 bytes.");

		specifiedKey = key;
		this.nonGenericContainer = nonGenericContainer;
	}
//
//	//! Get the current overridden essence key
//
//	/*! DRAGONS: If the key has not been overridden NULL will be returned - not the default key
//
//	 *  \note Defined EssenceSource sub-classes may always use a non-standard key, in which case
//
//	 *        they will always return a non-NULL value from this function
//
//	 */
//
//	virtual DataChunkPtr &GetKey(void) { return SpecifiedKey; }
//
	public ByteBuffer getKey() {
		
		return specifiedKey;
	}
//
//	//! Get true if the default essence key has been overriden with  a key that does not use GC track number mechanism
//
//	/*  \note Defined EssenceSource sub-classes may always use a non-GC-type key, in which case
//
//	 *        they will always return true from this function
//
//	 */
//
//	virtual bool GetNonGC(void) { return NonGC; }
//
	public boolean getNonGenericContainer() {
		
		return nonGenericContainer;
	}
//
//	/* Essence type identification */
//
//	/* These functions can be overwridden, or use the base versions to parse GetGCEssenceType() */
//
//
//
//	//! Is this picture essence?
//
//	virtual bool IsPictureEssence(void)
//
//	{
//
//		UInt8 Type = GetGCEssenceType();
//
//		if((Type == 0x05) || (Type == 0x15)) return true;
//
//		return false;
//
//	}
//
	public boolean isPictureEssence() {
		
		int type = getGenericContainerElementType();
		
		if ((type == 0x05) || (type == 0x15))
			return true;
		
		return false;
	}
//
//	//! Is this sound essence?
//
//	virtual bool IsSoundEssence(void)
//
//	{
//
//		UInt8 Type = GetGCEssenceType();
//
//		if((Type == 0x06) || (Type == 0x16)) return true;
//
//		return false;
//
//	}
//
	public boolean isSoundEssence() {
		
		int type = getGenericContainerElementType();
		if ((type == 0x06) || (type == 0x16)) 
			return true;

		return false;
	}
//
//	//! Is this data essence?
//
//	virtual bool IsDataEssence(void)
//
//	{
//
//		UInt8 Type = GetGCEssenceType();
//
//		if((Type == 0x07) || (Type == 0x17)) return true;
//
//		return false;
//
//	}
//
	public boolean isDataEssence() {
		
		int type = getGenericContainerElementType();
		
		if ((type == 0x07) || (type == 0x17)) 
			return true;
		
		return false;
	}
//
//	//! Is this compound essence?
//
//	virtual bool IsCompoundEssence(void)
//
//	{
//
//		return (GetGCEssenceType() == 0x18);
//
//	}

	public boolean isCompoundEssence() {
		
		return (getGenericContainerElementType() == 0x18);
	}
	
	
//
//	//! An indication of the relative write order to use for this stream
//
//	/*! Normally streams in a GC are ordered as follows:
//
//	 *  - All the CP system items (in Scheme ID then Element ID order)
//
//	 *  - All the GC system items (in Scheme ID then Element ID order)
//
//	 *  - All the CP picture items (in Element ID then Element Number order)
//
//	 *  - All the GC picture items (in Element ID then Element Number order)
//
//	 *  - All the CP sound items (in Element ID then Element Number order)
//
//	 *  - All the GC sound items (in Element ID then Element Number order)
//
//	 *  - All the CP data items (in Element ID then Element Number order)
//
//	 *  - All the GC data items (in Element ID then Element Number order)
//
//	 *  - All the GC compound items (in Element ID then Element Number order) (no GC compound)
//
//	 *
//
//	 *  However, sometimes this order needs to be overridden - such as for VBI data preceding picture items.
//
//	 *
//
//	 *  The normal case for ordering of an essence stream is for RelativeWriteOrder to return 0,
//
//	 *  indicating that the default ordering is to be used. Any other value indicates that relative
//
//	 *  ordering is required, and this is used as the Position value for a SetRelativeWriteOrder()
//
//	 *  call. The value of Type for that call is acquired from RelativeWriteOrderType()
//
//	 *
//
//	 * For example: to force a source to be written between the last GC sound item and the first CP data
//
//	 *              item, RelativeWriteOrder() can return any -ve number, with RelativeWriteOrderType()
//
//	 *				returning 0x07 (meaning before CP data). Alternatively RelativeWriteOrder() could
//
//	 *				return a +ve number and RelativeWriteOrderType() return 0x16 (meaning after GC sound)
//
//	 */
//
//	virtual Int32 RelativeWriteOrder(void) { return 0; }
//
	public @Int32 int relativeWriteOrder() { 
		
		return 0;
	}
//
//	//! The type for relative write-order positioning if RelativeWriteOrder() != 0
//
//	/*! This method indicates the essence type to order this data before or after if reletive write-ordering is used 
//
//	 */
//
//	virtual int RelativeWriteOrderType(void) { return 0; }
//
	public int relativeWriteOrderType() {
		
		return 0;
	}
//
//	//! Get the origin value to use for this essence specifically to take account of pre-charge
//
//	/*! \return Zero if not applicable for this source
//
//	 */
//
//	virtual Length GetPrechargeSize(void) { return 0; }
//
	public @MXFLength long getPrechargeSize() {
		
		return 0;
	}
//
//	//! Get the range start position
//
//	/*! \return Zero if not applicable for this source
//
//	 */
//
//	virtual Position GetRangeStart(void) { return 0; }
//
	public @MXFPosition long getRangeStart() {
		
		return 0;
	}
//
//	//! Get the range end position
//
//	/*! \return -1 if not applicable for this source
//
//	 */
//
//	virtual Position GetRangeEnd(void) { return 0; }
//
	public @MXFPosition long getRangeEnd() {
		
		return 0;
	}
//
//	//! Get the range duration
//
//	/*! \return -1 if not applicable for this source
//
//	 */
//
//	virtual Length GetRangeDuration(void) { return 0; }
//
	public @Int64 long getRangeDuration() {
		
		return 0;
	}
	
}
