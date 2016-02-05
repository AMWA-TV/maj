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
 * $Log: GenericContainerElementKind.java,v $
 * Revision 1.4  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2009/02/13 15:27:46  vizigoth
 * Replaced DataChunk with core Java ByteBuffer.
 *
 * Revision 1.2  2009/02/13 14:27:29  vizigoth
 * Completed creation of method stubs from C comments and added MXFPosition and MXFLength labels.
 *
 * Revision 1.1  2009/02/03 16:15:19  vizigoth
 * Intiial creation and copy over of header information from mxflib.
 *
 *
 */

package tv.amwa.maj.io.mxf;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;

/**
 * <p>Information about each stream in a generic container.</p>
 * 
 *
 *
 */
public final class GenericContainerElementKind {

//	struct GCElementKind
//
//	{
//
//		bool	IsValid;					//!< true if this is a GC Element
//
//		UInt8	Item;						//!< Item type - byte 13
//
//		UInt8	Count;						//!< Element count - byte 14
//
//		UInt8	ElementType;				//!< Element type - byte 15
//
//		UInt8	Number;						//!< Element number - byte 16
//
//	};
//
	private boolean isValid;
	
	private @UInt8 byte item;
	
	private @UInt8 byte count;
	
	private @UInt8 byte elementType;
	
	private @UInt8 byte number;
	
	private GenericContainerElementKind() { }
	
	public GenericContainerElementKind(
			boolean isValid,
			@UInt8 byte item,
			@UInt8 byte count,
			@UInt8 byte elementType,
			@UInt8 byte number) {
		
		this.isValid = isValid;
		this.item = item;
		this.count = count;
		this.elementType = elementType;
		this.number = number;
	}
			
	
//
//	//! Register an essence key to be treated as a GC essence key
//
//	/*! This allows private or experimental essence keys to be treated as standard GC keys when reading 
//
//	 *  \note If the key specified is less than 
//
//	 */
//
//	void RegisterGCElementKey(DataChunkPtr &Key);
//
	public final static void registerGenericContainerElementKey(
			ByteBuffer key) {
		
		// TODO
	}
//
//	//! Get a GCElementKind structure from a key
//
//	GCElementKind GetGCElementKind(ULPtr TheUL);
//
	public final static GenericContainerElementKind getGenericContainerElementKind(
			UL universalLabel) {
		
		// TODO
		return null;
	}
//
//	//! Get the track number of this essence key (if it is a GC Key)
//
//	/*! \return 0 if not a valid GC Key
//
//	 */
//
//	UInt32 GetGCTrackNumber(ULPtr TheUL);

	public final static @UInt32 int getGenericContainerTrackNumber(
			UL universalLabel) {
		
		// TODO
		return 0;
	}
	
	private final static byte[] genericContainerEssenceKey = new byte[] {
		0x06, 0x0e, 0x2B, 0x34, 0x01, 0x02, 0x01, 0x00,
		0x0d, 0x01, 0x03, 0x01, 0x00, 0x00, 0x00, 0x00
	};
	
	private final static List<ByteBuffer> genericContainerEssenceKeyAlternatives =
		new Vector<ByteBuffer>();
	
	public boolean isValid() {
		
		return isValid;
	}
	
	public @UInt8 byte getItem() {
		
		return item;
	}
	
	public @UInt8 byte getCount() {
		
		return count;
	}
	public @UInt8 byte getElementType() {
		
		return elementType;
	}
	
	public @UInt8 byte getNumber() {
		
		return number;
	}
	
}
