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
 * $Log: EssenceSink.java,v $
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

import tv.amwa.maj.integer.UInt8;

/**
 * <p>Objects that receive large quantities of essence data.</p>
 * 
 *
 *
 */
public abstract class EssenceSink {

//	public:
//
//		// Base constructor
//
//		EssenceSink() {};
//
	public EssenceSink() { }
//
//
//		//! Receive the next "installment" of essence data
//
//		/*! This will recieve a buffer containing thhe next bytes of essence data
//
//		 *  \param Buffer The data buffer
//
//		 *  \param BufferSize The number of bytes in the data buffer
//
//		 *  \param EndOfItem This buffer is the last in this wrapping item
//
//		 *  \return True if all is OK, else false
//
//		 *  \note The first call may well fail if the sink has not been fully configured.
//
//		 *	\note If false is returned the caller should make no more calls to this function, but the function should be implemented such that it is safe to do so
//
//		 */
//
//		virtual bool PutEssenceData(UInt8 *const Buffer, size_t BufferSize, bool EndOfItem = true) = 0;
//
	public abstract boolean putEssenceData(
			ByteBuffer buffer,
			boolean endOfItem);
	
	public boolean putEssenceData(
			ByteBuffer buffer) {
		
		return putEssenceData(buffer, true);
	}
	
// TODO tidy up the following methods ... are they required?
	
//		//! Receive the next "installment" of essence data from a smart pointer to a DataChunk
//
//		bool PutEssenceData(DataChunkPtr &Buffer, bool EndOfItem = true) { return PutEssenceData(Buffer->Data, Buffer->Size, EndOfItem); }
//
//
//		//! Receive the next "installment" of essence data from a DataChunk
//
//		bool PutEssenceData(DataChunk &Buffer, bool EndOfItem = true) { return PutEssenceData(Buffer.Data, Buffer.Size, EndOfItem); }
//
//
//
//		//! Called once all data exhasted
//
//		/*! \return true if all is OK, else false
//
//		 *  \note This function must also be called from the derived class' destructor in case it is never explicitly called
//
//		 */
//
//		virtual bool EndOfData(void) = 0;
//
	public abstract boolean endOfData();
}
