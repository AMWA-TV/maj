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
 * $Log: PartitionInformation.java,v $
 * Revision 1.1  2011/07/27 16:52:59  vizigoth
 * Capable of reading and writing audio essence component files.
 *
 * Revision 1.4  2010/01/19 14:44:23  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
 *
 * Revision 1.3  2009/02/13 14:27:29  vizigoth
 * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
 *
 * Revision 1.2  2009/02/06 17:01:31  vizigoth
 * Conversion of C headers to fields and stubs.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.io.mxf.MXFLength;
import tv.amwa.maj.io.mxf.MXFPosition;

/**
 * <p>Holds {@linkplain RandomIndexPackImpl random index pack} data relating to a single
 * {@linkplain PartitionImpl partition}.</p>
 * 
 *
 * 
 * @see RandomIndexPackImpl
 * @see PartitionImpl
 */
public class PartitionInformation 
	implements Comparable<PartitionInformation> {

//	public:
//
//		PartitionPtr ThePartition;	//!< The actual partition
//
//									/*!< \note This is public for compatibility only <b>** use accessors **</b>
//
//									 */
//
	private PartitionImpl partition = null;
//
//
//		Position ByteOffset;		//!< Byte offset into the file for the start of this partition
//
//									/*!< \note This is public for compatibility only <b>** use accessors **</b>
//
//									 *
//
//									 *   \note
//
//									 *	 Version 11 of the MXF spec uses a UInt64 for this
//
//									 *         field but we are using a Position type here as it
//
//									 *         makes more sense, and allows distingushed value -1
//
//									 *   
//
//									 *	 \note 
//
//									 *	 Distinguished value -1 is used where the location
//
//									 *         in the file is not known
//
//									 */
//
	private @MXFPosition long byteOffset = -1l;
//
//		UInt32 BodySID;				//!< Stream ID of any essence in this partition (0 if none)
//
//									/*!< \note This is public for compatibility only <b>** use accessors **</b>
//
//									 *
//
//									 *   \note 0 is also used if the existance of essence is
//
//									 *         has not yet been determined
//
//									 */
//
	private @UInt32 int bodySID = 0;
//
//	protected:
//
//		UInt32 IndexSID;			//!< Index SID of any index table in this partition (0 if none or not known)
//
	private @UInt32 int indexSID = 0;
//
//		bool KnownSIDs;				//!< Set true once we know for sure what the SIDs are, including IndexSID
//
//									/*!< This will be false when we have read a version 1 RIP as all that is known is the SID,
//
//									 *   which could possibly be an index SID for index-only partitions, but will be true
//
//									 *   once we have either parsed the partition pack itself, written one ourselves, or
//
//									 *   read a version 2 RIP (complete with detailed partion layout)
//
//									 */
//
	private boolean knownSIDs = false;
//
//
//		Position StreamOffset;		//!< Stream offset of the first byte of essence, or generic stream, data in the partition
//
//									/*!< Set to -1 if not known
//
//									 */

	private @MXFPosition long streamOffset = -1l;
	
//		Length StreamByteCount;		//!< Count of stream data bytes in the partition
//
//									/*!< Set to -1 if not known
//
//									 */
//
	private @MXFLength long streamByteCount = -1l;
//
//		Position EstStreamOffset;	//!< <b>Estimated</b> Stream offset of the first byte of essence, or generic stream, data in the partition
//
//									/*!< Set to -1 if not known
//
//									 */
	
	private @MXFPosition long estimatedStreamOffset = -1l;
//
//		Length EstStreamByteCount;	//!< <b>Estimated</b> Count of stream data bytes in the partition
//
//									/*!< Set to -1 if not known
//
//									 */
//
	private @MXFLength long estimatedStreamByteCount = -1l;
//
//		Position EssenceStart;		//!< Actual byte offset in the file where the essence starts for this partition, if known, else -1
//
	private @MXFPosition long essenceStart = -1l;
//
//	public:
//
//		PartitionInfo(PartitionPtr Part = NULL, Position Offset = -1, UInt32 SID = 0);
//
	public PartitionInformation(
			PartitionImpl partition,
			@MXFPosition long offset,
			@UInt32 int sid) {
		
		this.partition = partition;
		this.byteOffset = offset;
		this.bodySID = sid;
	}
	
	public PartitionInformation() { }
//
//		//! Comparison function to allow sorting
//
//		bool operator< (PartitionInfo &Other) {	return (ByteOffset < Other.ByteOffset); }
//
	public int compareTo(PartitionInformation o) {
		
		if (byteOffset < o.getByteOffset())
			return -1;
		if (byteOffset == o.getByteOffset())
			return 0;
		return 1;
	}

//
//		//! Get a pointer to the actual partition
//
//		PartitionPtr &GetPartition(void) { return ThePartition; }
//
	public PartitionImpl getPartition() {
		
		return partition;
	}
//
//		//! Set the actual partition
//
//		void SetPartition(PartitionPtr Val) { ThePartition = Val; }
//
	public void setPartition(
			PartitionImpl partition) {
		
		this.partition = partition;
	}
	
//
//		//! Get the BodySID
//
//		UInt32 GetBodySID(void) const { return BodySID; }
//
	public @UInt32 int getBodySID() {
		
		return bodySID;
	}
//
//		//! Set the BodySID
//
//		void SetBodySID(UInt32 Val) { BodySID = Val; }
//
	public void setBodySID(
			@UInt32 int bodySID) 
		throws IllegalArgumentException {
		
		if (bodySID < 0)
			throw new IllegalArgumentException("Cannot set the body SID to a negative value.");
		this.bodySID = bodySID;
	}
//
//		//! Get the IndexSID
//
//		UInt32 GetIndexSID(void) const { return IndexSID; }
//
	public @UInt32 int getIndexSID() {
		
		return indexSID;
	}
//
//		//! Set the IndexSID
//
//		void SetIndexSID(UInt32 Val) { IndexSID = Val; }
//
	public void setIndexSID(
			@UInt32 int indexSID) 
		throws IllegalArgumentException {
		
		if (indexSID < 0)
			throw new IllegalArgumentException("Cannot set the index SID to a negative value.");
		this.indexSID = indexSID;
	}
//
//		//! Get KnownSIDs flag
//
//		bool SIDsKnown(void) const { return KnownSIDs; }
//
	public boolean areSIDsKnown() {
		
		return knownSIDs;
	}
//
//		//! Set KnownSIDs flag
//
//		void SetKnownSIDs(bool Val) { KnownSIDs = Val; }
//
	public void setAreSIDsKnown(
			boolean knownSIDs) {
		
		this.knownSIDs = knownSIDs;
	}
	
//
//		//! Set BodySID and IndexSID, and set KnownSIDs to true
//
//		void SetSIDs(UInt32 NewBodySID, UInt32 NewIndexSID)
//
//		{
//
//			BodySID = NewBodySID;
//
//			IndexSID = NewIndexSID;
//
//			KnownSIDs = true;
//
//		}
//
	public void setSIDs(
			@UInt32 int bodySID,
			@UInt32 int indexSID) 
		throws IllegalArgumentException {
		
		setBodySID(bodySID);
		setIndexSID(indexSID);
		knownSIDs = true;
	}
//
//		//! Get the byte offset of this partition pack in the file (if known), or -1 if not known
//
//		Position GetByteOffset(void) const { return ByteOffset; }
//
	public @MXFPosition long getByteOffset() {
		
		return byteOffset;
	}
//
//		//! Set the byte offset of this partition pack in the file (if known), or -1 if not known
//
//		void SetByteOffset(Position Val)  { ByteOffset = Val; }
//
	public void setByteOffset(
			@MXFPosition long byteOffset) {
		
		this.byteOffset = byteOffset;
	}
//
//		//! Get the stream offset of the first data byte in this partition (if known), or -1 if not known
//
//		Position GetStreamOffset(void) const { return StreamOffset; }
//
	public @MXFPosition long getStreamOffset() {
		
		return streamOffset;
	}
//
//		//! Set the stream offset of the first data byte in this partition (if known), or -1 if not known
//
//		void SetStreamOffset(Position Val)  { StreamOffset = Val; }
//
	public void setStreamOffset(
			@MXFPosition long streamOffset) {
		
		this.streamOffset = streamOffset;
	}
//
//		//! Get the <b>estimated</b> stream offset of the first data byte in this partition (if known), or -1 if no idea
//
//		Position GetEstimatedStreamOffset(void) const { return EstStreamOffset; }
//
	public @MXFPosition long getEstimatedStreamOffset() {
		
		return estimatedStreamOffset;
	}
//
//		//! Set the <b>estimated</b> stream offset of the first data byte in this partition (if known), or -1 if no idea
//
//		void SetEstimatedStreamOffset(Position Val)  { EstStreamOffset = Val; }
//
	public void setEstimatedStreamOffset(
			@MXFPosition long estimatedStreamOffset) {
		
		this.estimatedStreamOffset = estimatedStreamOffset;
	}
//
//		//! Get the essence start as a byte offset in the file (if known), or -1 if not known
//
//		Position GetEssenceStart(void) const { return EssenceStart; }
//
	public @MXFPosition long getEssenceStart() {
		
		return essenceStart;
	}
//
//		//! Set the essence start as a byte offset in the file (if known), or -1 if not known
//
//		void SetEssenceStart(Position Val)  { EssenceStart = Val; }

	public void setEssenceStart(
			@MXFPosition long essenceStart) {
		
		this.essenceStart = essenceStart;
	}
	
}
