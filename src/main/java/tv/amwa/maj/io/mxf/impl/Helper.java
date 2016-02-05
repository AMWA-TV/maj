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
 * $Log: Helper.java,v $
 * Revision 1.1  2011/07/27 16:52:59  vizigoth
 * Capable of reading and writing audio essence component files.
 *
 * Revision 1.3  2009/02/13 15:27:46  vizigoth
 * Replaced DataChunk with core Java ByteBuffer.
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

import java.nio.ByteBuffer;

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;

/**
 * <p>Common helper static methods used in MXF operations.</p>
 * 
 *
 *
 */
public class Helper {

//	//! Make a string containing a number
//
//	inline std::string Int2String(int Num, int Digits = 0)
//
//	{
//
//		char Buffer[18];
//
//		if(Digits > 16) Digits = 16;
//
//		sprintf(Buffer, "%0*d", Digits, Num);
//
//		return std::string(Buffer);
//
//	}
//
//
//
//	//! Make a string containing an unsigned number
//
//	inline std::string UInt2String(int Num, int Digits = 0)
//
//	{
//
//		char Buffer[18];
//
//		if(Digits > 16) Digits = 16;
//
//		sprintf(Buffer, "%0*u", Digits, Num);
//
//		return std::string(Buffer);
//
//	}
//
//
//
//	// Support old capitalization of unsigned integers
//
//	inline std::string Uint2String(int Num, int Digits = 0) { return UInt2String(Num, Digits); }
//
//
//
//	//! Make a hex string containing a number
//
//	inline std::string Int2HexString(int Num, int Digits = 0)
//
//	{
//
//		char Buffer[18];
//
//		if(Digits > 16) Digits = 16;
//
//		sprintf(Buffer, "%0*x", Digits, Num);
//
//		return std::string(Buffer);
//
//	}
//
//
//
//	//! Convert a time to an ISO 8601 string
//
//	/*! \note ISO 8601 suggests "T" as a separator between date and time. 
//
//	 *	To get this behaviour set StrictISO to true
//
//	 *	\note ANSI-C doesn't seem to have a way to get milliseconds */
//
//	inline std::string Time2String(full_time Time, bool StrictISO = false)
//
//	{
//
//		char Buffer[32];
//
//		
//
//		if(StrictISO)
//
//			strftime(Buffer, 31, "%Y-%m-%dT%H:%M:%S.", gmtime( &Time.time ));
//
//		else
//
//			strftime(Buffer, 31, "%Y-%m-%d %H:%M:%S.", gmtime( &Time.time ));
//
//
//
//		// Append the milliseconds
//
//		sprintf(&Buffer[strlen(Buffer)], "%03d", Time.msBy4 * 4);
//
//
//
//		return std::string(Buffer);
//
//	}
//
//
//
//	//! Get the current time as an ISO 8601 string
//
//	/*! \note ISO 8601 suggests "T" as a separator between date and time. 
//
//	 *	To get this behaviour set StrictISO to true */
//
//	inline std::string Now2String(bool StrictISO = false)
//
//	{
//
//		full_time now = GetTime();
//
//		
//
//		return Time2String(now, StrictISO);
//
//	}
//
//
//
//	//! Build a BER length
//
//	/*! \param Data		A pointer to the buffer to receive the length
//
//	 *	\param MazSize	The maximum length that can be written to the buffer
//
//	 *	\param Length	The length to be converted to BER
//
//	 *	\param Size		The total number of bytes to use for BER length (or 0 for auto)
//
//	 *	\return The number of bytes written
//
//	 *	\note If the size is specified it will be overridden for lengths that will not fit in Size,
//
//	 *        <b>providing</b> they will fit in MaxSize. However an error message will be produced.
//
//	 */
//
//	UInt32 MakeBER(UInt8 *Data, int MaxSize, UInt64 Length, UInt32 Size = 0);
//
//
//
//
//	//! Build a BER length
//
//	/*! \param Length	The length to be converted to BER
//
//	 *	\param Size		The total number of bytes to use for BER length (or 0 for auto)
//
//	 *	\note If the size is specified it will be overridden for lengths
//
//	 *		  that will not fit. However an error message will be produced.
//
//	 */
//
//	inline DataChunkPtr MakeBER(UInt64 Length, UInt32 Size = 0)
//
//	{
//
//		// Buffer for building BER
//
//		UInt8 Buff[9];
//
//
//
//		UInt32 Bytes = MakeBER(Buff, 9, Length, Size);
//
//
//
//		// Return as a DataChunk
//
//		return new DataChunk(Bytes, Buff);
//
//	}
//
	public final static ByteBuffer makeBER(
			@UInt64 long length,
			@UInt32 int size) {
		
		// TODO
		return null;
	}
//
//	//! Read a BER length
//
//	Length ReadBER(UInt8 **Data, int MaxSize);
//
//
//
//
//
//	//! Encode a UInt64 as a BER OID subid (7 bits per byte)
//
//	//! length > 0: length is maximum length of subid
//
//	//! length == 0: as long as necessary
//
//	//! length < 0: -length is exact length of subid
//
//	//! returns number of bytes used
//
//	int EncodeOID( UInt8* presult, UInt64 subid, int length );
//
//
//
//	//! Build a new UMID
//
//	UMIDPtr MakeUMID(int Type, const UUIDPtr AssetID = NULL);
//
//
//
//	//! Read a "Chunk" from a non-MXF file
//
//	DataChunkPtr FileReadChunk(FileHandle InFile, UInt64 Size);
//
//
//
//
//
//	//! Read an IFF chunk header (from an open file)
//
//	/*! The Chunk ID is read as a big-endian UInt32 and returned as the first
//
//	 *	part of the returned pair. The chunk size is read as a specified-endian
//
//	 *	number and returned as the second part of the returned pair
//
//	 *	\return <0,0> if the header counld't be read
//
//	 */
//
//	U32Pair ReadIFFHeader(FileHandle InFile, bool BigEndian = true);
//
//
//
//
//
//	//! Read a RIFF chunk header (from an open file)
//
//	/*! The Chunk ID is read as a big-endian UInt32 and returned as the first
//
//	 *	part of the returned pair. The chunk size is read as a little-endian
//
//	 *	number and returned as the second part of the returned pair
//
//	 *	\return <0,0> if the header counld't be read
//
//	 */
//
//	inline U32Pair ReadRIFFHeader(FileHandle InFile)
//
//	{
//
//		return ReadIFFHeader(InFile, false);
//
//	}
//
//
//
//
//
//	//! Read a AIFF chunk header (from an open file)
//
//	/*! The Chunk ID is read as a big-endian UInt32 and returned as the first
//
//	 *	part of the returned pair. The chunk size is read as a big-endian
//
//	 *	number and returned as the second part of the returned pair
//
//	 *	\return <0,0> if the header counld't be read
//
//	 */
//
//	inline U32Pair ReadAIFFHeader(FileHandle InFile)
//
//	{
//
//		return ReadIFFHeader(InFile, true);
//
//	}
//
//
//
//
//
//	//! Read a QuickTime Atom header (from an open file)
//
//	/*! The Atom Type ID is read as a big-endian UInt32 and returned as the first
//
//	 *	part of the returned pair. The Atom size is read as a big-endian
//
//	 *	number and returned as the second part of the returned pair.
//
//	 *  Extended sizes are automatically read if used.
//
//	 *  If SkipWide is omitted (or true) any "wide" atoms are read and skipped automatically.
//
//	 *	\return <0,0> if the header counld't be read
//
//	 */
//
//	std::pair<UInt32, Length> ReadAtomHeader(FileHandle InFile, bool SkipWide = true);
//
//
//
//
//
//	//! Set a data chunk from a hex string
//
//	DataChunkPtr Hex2DataChunk(std::string Hex);
//
//
//
//	//! Set the search path to be used for dictionary files
//
//	void SetDictionaryPath(std::string NewPath);
//
//
//
//	//! Set the search path to be used for dictionary files
//
//	inline void SetDictionaryPath(const char *NewPath) { SetDictionaryPath(std::string(NewPath)); }
//
//
//
//	//! Search for a file of a specified name in the current dictionary search path
//
//	/*! If the filname is either absolute, or relative to "." or ".." then the 
//
//	 *  paths are not searched - just the location specified by that filename.
//
//	 *  \return the full path and name of the file, or "" if not found
//
//	 */
//
//	std::string LookupDictionaryPath(const char *Filename);
//
//	
//
//	//! Search for a file of a specified name in the current dictionary search path
//
//	inline std::string LookupDictionaryPath(std::string Filename) { return LookupDictionaryPath(Filename.c_str()); }
//
//
//
//	//! Search a path list for a specified file
//
//	/*! If the filname is either absolute, or relative to "." or ".." then the 
//
//	 *  paths are not searched - just the location specified by that filename.
//
//	 *  \return the full path and name of the file, or "" if not found
//
//	 */
//
//	std::string SearchPath(const char *Path, const char *Filename);
//
//
//
//	//! Search a path list for a specified file
//
//	inline std::string SearchPath(std::string Path, std::string Filename) { return SearchPath(Path.c_str(), Filename.c_str()); }
//
//
//
//
//
//	// File read primitives
//
//
//
//	//! Read 8-bit unsigned integer
//
//	inline UInt8 ReadU8(FileHandle Handle) { unsigned char Buffer[1]; if(FileRead(Handle, Buffer, 1) == 1) return GetU8(Buffer); else return 0; }
//
//
//
//	//! Read 16-bit unsigned integer
//
//	inline UInt16 ReadU16(FileHandle Handle) { unsigned char Buffer[2]; if(FileRead(Handle, Buffer, 2) == 2) return GetU16(Buffer); else return 0; }
//
//
//
//	//! Read 32-bit unsigned integer
//
//	inline UInt32 ReadU32(FileHandle Handle) { unsigned char Buffer[4]; if(FileRead(Handle, Buffer, 4) == 4) return GetU32(Buffer); else return 0; }
//
//
//
//	//! Read 64-bit unsigned integer
//
//	inline UInt64 ReadU64(FileHandle Handle) { unsigned char Buffer[8]; if(FileRead(Handle, Buffer, 8) == 8) return GetU64(Buffer); else return 0; }
//
//
//
//	//! Read 8-bit signed integer (casts from unsigned version)
//
//	inline Int8 ReadI8(FileHandle Handle) { return (Int8)ReadU8(Handle); }
//
//
//
//	//! Read 16-bit signed integer (casts from unsigned version)
//
//	inline Int16 ReadI16(FileHandle Handle) { return (Int16)ReadU16(Handle); }
//
//	
//
//	//! Read 32-bit signed integer (casts from unsigned version)
//
//	inline Int32 ReadI32(FileHandle Handle) { return (Int32)ReadU32(Handle); }
//
//	
//
//	//! Read 64-bit signed integer (casts from unsigned version)
//
//	inline Int64 ReadI64(FileHandle Handle) { return (Int64)ReadU64(Handle); }
//
//
//
//	//! Read 8-bit unsigned integer Little-Endian
//
//	inline UInt8 ReadU8_LE(FileHandle Handle) { unsigned char Buffer[1]; if(FileRead(Handle, Buffer, 1) == 1) return GetU8_LE(Buffer); else return 0; }
//
//
//
//	//! Read 16-bit unsigned integer Little-Endian
//
//	inline UInt16 ReadU16_LE(FileHandle Handle) { unsigned char Buffer[2]; if(FileRead(Handle, Buffer, 2) == 2) return GetU16_LE(Buffer); else return 0; }
//
//
//
//	//! Read 32-bit unsigned integer Little-Endian
//
//	inline UInt32 ReadU32_LE(FileHandle Handle) { unsigned char Buffer[4]; if(FileRead(Handle, Buffer, 4) == 4) return GetU32_LE(Buffer); else return 0; }
//
//
//
//	//! Read 64-bit unsigned integer Little-Endian
//
//	inline UInt64 ReadU64_LE(FileHandle Handle) { unsigned char Buffer[8]; if(FileRead(Handle, Buffer, 8) == 8) return GetU64_LE(Buffer); else return 0; }
//
//
//
//	//! Read 8-bit signed integer Little-Endian (casts from unsigned version)
//
//	inline Int8 ReadI8_LE(FileHandle Handle) { return (Int8)ReadU8_LE(Handle); }
//
//
//
//	//! Read 16-bit signed integer Little-Endian (casts from unsigned version)
//
//	inline Int16 ReadI16_LE(FileHandle Handle) { return (Int16)ReadU16_LE(Handle); }
//
//	
//
//	//! Read 32-bit signed integer Little-Endian (casts from unsigned version)
//
//	inline Int32 ReadI32_LE(FileHandle Handle) { return (Int32)ReadU32_LE(Handle); }
//
//	
//
//	//! Read 64-bit signed integer Little-Endian (casts from unsigned version)
//
//	inline Int64 ReadI64_LE(FileHandle Handle) { return (Int64)ReadU64_LE(Handle); }
//
//
//
//
//
//
//
//	//! Is a given sequence of bytes a partition pack key?
//
//	bool IsPartitionKey(const UInt8 *Key);
//
//
//
//	//! Does a given std::string contain a "wide" string in UTF8?
//
//	/*! \note This currently only checks if any bytes contain >127 so it is only safe to test strings that are either 7-bit ASCII or UTF-8 */
//
//	bool IsWideString(std::string &String);
//
//
//
//	//! Read hex values separated by any of 'Sep'
//
//	/*! \note Modifies the value of Source to point to the following byte
//
//	 *  \return number of values read */
//
//	int ReadHexString(const char **Source, int Max, UInt8 *Dest, const char *Sep);
//
//
//
//	//! Read hex values separated by any of 'Sep'
//
//	/*! \note This version does not modify the value of parameter Source
//
//	 *  \return number of values read */
//
//	inline int ReadHexString(const char *Source, int Max, UInt8 *Dest, const char *Sep)
//
//	{
//
//		const char *p = Source;
//
//		return ReadHexString(&p, Max, Dest, Sep);
//
//	}
//
//
//
//	//! Build a UL from a character string, writing the bytes into a 16-byte buffer
//
//	/*! \return true if a full 16 bytes were read into the buffer, else false
//
//	 */
//
//	bool StringToUL(UInt8 *Data, std::string Val);
//
//	
//
//	//! Build a UL from a character string, returning a smart pointer to it
//
//	/*! \return A pointer to the new UL if a full 16 bytes were read, else NULL
//
//	 */
//
//	inline ULPtr StringToUL(std::string Val)
//
//	{
//
//		UInt8 Data[16];
//
//		if(!StringToUL(Data, Val)) return NULL;
//
//		return new UL(Data);
//
//	}
	
}
