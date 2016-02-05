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
 * $Log: Primer.java,v $
 * Revision 1.1  2011/07/27 16:52:59  vizigoth
 * Capable of reading and writing audio essence component files.
 *
 * Revision 1.5  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2009/02/13 15:27:46  vizigoth
 * Replaced DataChunk with core Java ByteBuffer.
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

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.io.mxf.UL;

/**
 * <p>Mapping of local tags to metadata definitions.</p>
 * 
 *
 *
 */
public class Primer 
	implements Map<Short, UL> {

//
//	protected:
//
//		Tag NextDynamic;						//! Next dynamic tag to try

	private @UInt16 short nextDynamic = (short) 0xffff; 
	
	private Map<Short, UL> ulLookup = new HashMap<Short, UL>();
	
//		std::map<UL, Tag> TagLookup;			//! Reverse lookup for locating a tag for a given UL
//
	private Map<UL, Short> tagLookup = new HashMap<UL, Short>();
	
//
//	public:
//
//		Primer() { NextDynamic = 0xffff; };

	public Primer(
			@UInt16 short nextDynamic) {
		
		this.nextDynamic = nextDynamic;
	}
	
	public Primer() { }

	public void clear() {

		ulLookup.clear();
		tagLookup.clear();
		
	}

	public boolean containsKey(Object key) {

		return ulLookup.containsKey(key);
	}

	public boolean containsValue(Object value) {

		return ulLookup.containsValue(value);
	}

	public Set<java.util.Map.Entry<Short, UL>> entrySet() {
	
		return ulLookup.entrySet();
	}

	public UL get(Object key) {

		return ulLookup.get(key);
	}

	public boolean isEmpty() {

		return ulLookup.isEmpty();
	}

	public Set<Short> keySet() {

		return ulLookup.keySet();
	}

	public UL put(Short key, UL value) {
		
		tagLookup.put(value, key);
		return ulLookup.put(key, value);
	}

	public void putAll(Map<? extends Short, ? extends UL> t) {

		ulLookup.putAll(t);
		
		for ( Short tag : ulLookup.keySet() ) 
			tagLookup.put(ulLookup.get(tag), tag);
		
	}

	public UL remove(Object key) {

		tagLookup.remove(key);
		return ulLookup.remove(key);
	}

	public int size() {

		return ulLookup.size();
	}

	public Collection<UL> values() {

		return ulLookup.values();
	}
	
//		//! Read the primer from the given buffer
//		UInt32 ReadValue(const UInt8 *Buffer, UInt32 Size);
//
	public @UInt32 int readValue(
			byte[] buffer) {
	
		//TODO
		return 0;
	}
//
//		//! Write this primer to a memory buffer
//
//		UInt32 WritePrimer(DataChunkPtr &Buffer);
//
	public @UInt32 int writePrimer(
			ByteBuffer buffer) {
		
		// TODO
		return 0;
	}
//
//		//! Determine the tag to use for a given UL
////! Determine the tag to use for a given UL
//
//	/*! If the UL has not yet been used the correct static or dynamic tag will 
//
//	 *	be determined and added to the primer
//
//	 *	\return The tag to use, or 0 if no more dynamic tags available
//
//	 */
//		Tag Lookup(ULPtr ItemUL, Tag TryTag = 0);
//
	public @UInt16 short lookup(
			UL itemUL,
			@UInt16 short tryTag) {
		
		// TODO
		return 0;
	}
	
	
//
//		//! Determine the tag to use for a given UL - when no primer is availabe
//
//		static Tag StaticLookup(ULPtr ItemUL, Tag TryTag = 0);
//
	public static @UInt16 short staticLookup(
			UL itemUL,
			@UInt16 short tryTag) {
		
		//TODO
		return 0;
	}


}
