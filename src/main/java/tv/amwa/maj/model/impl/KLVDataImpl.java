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
 * $Log: KLVDataImpl.java,v $
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
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2008/01/27 11:14:41  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.1  2007/11/13 22:09:25  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;

import tv.amwa.maj.exception.NotValidKeyException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.impl.TypeDefinitionImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionRenameImpl;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.model.KLVData;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;


/** 
 * <p>Implements a container for user data specified with a key (SMPTE label), length 
 * and value. SMPTE KLV's are specified in SMPTE&336M.</p>
 * 
 * <p>The byte order of the KLV data value is the same as the byte order of the 
 * {@linkplain java.nio.ByteBuffer byte buffer} passed to initialize or set the
 * value.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x4000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "KLVData",
		  description = "The KLVData class contains user data specified with a key (SMPTE label), length, and value.",
		  symbol = "KLVData")
public class KLVDataImpl
	extends 
		InterchangeObjectImpl
	implements 
		KLVData,
		Serializable,
		XMLSerializable,
		Cloneable {

	private ByteBuffer klvDataValue;

	/**  */
	private static final long serialVersionUID = -5267669350070637395L;
	
	public KLVDataImpl() { }

	/**
	 * <p>Creates and initializes a new KLV data object, which contains 
	 * user data specified with a key (SMPTE label), length, and value.
	 * The key must have been previously registered using 
	 * {@link DictionaryImpl#registerKLVDataKey(AUIDImpl, TypeDefinitionImpl)}
	 * in order to use the byte swapping functionality of the type model.</p>
	 * 
	 * @param key Key associated with the data value.
	 * @param value Value associated with the key.
	 * 
	 * @throws NullPointerException One or both of the arguments is <code>null</code>.
	 * @throws NotValidKeyException The given key must not resolve to a 
	 * built-in type. For most purposes, use {@link TypeDefinitionRenameImpl}
	 * to rename an existing type.
	 */
	public KLVDataImpl(
			AUID key,
			@DataBuffer ByteBuffer value) 
		throws NullPointerException,
			NotValidKeyException {
		
		if (key == null)
			throw new NullPointerException("Cannot set the value of a KLV data item from a null key.");
		if (value == null)
			throw new NullPointerException("Cannot set the value of a KLV data item from a null value.");
		
		this.klvDataValue = ByteBuffer.allocate(value.limit() + 16);
		this.klvDataValue.put(key.getAUIDValue());
		value.rewind();
		this.klvDataValue.put(value);
		this.klvDataValue.order(value.order());
	}

	public KLVDataImpl(
			@DataBuffer ByteBuffer keyAndValue) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (keyAndValue == null)
			throw new NullPointerException("Cannot set the value of a KLV data item from a null key and value.");
		if (keyAndValue.limit() < 16)
			throw new IllegalArgumentException("A KLV data value must include a 16 byte key and the given array is too short.");
		
		this.klvDataValue = keyAndValue.duplicate();
		this.klvDataValue.rewind();
	}

	public AUID getKey() {

		byte[] keyBytes = new byte[16];
		klvDataValue.rewind();
		klvDataValue.get(keyBytes);
		return new AUIDImpl(keyBytes);
	}
	
	public int getLength() {

		// Includes the length of the key
		return klvDataValue.limit();
	}

	@MediaProperty(uuid1 = 0x03010210, uuid2 = (short) 0x0200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "KLVDataValue",
			aliases = { "Value" },
			typeName = "Opaque",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x5101,
			symbol = "KLVDataValue")
	public ByteBuffer getValue() {

		ByteBuffer resultBuffer = klvDataValue.duplicate();
		resultBuffer.rewind();
		return resultBuffer;
	}

	@MediaPropertySetter("KLVDataValue")
	public void setValue(
			ByteBuffer keyAndValue)
		throws NullPointerException,
			IllegalArgumentException {

		if (keyAndValue == null)
			throw new NullPointerException("Canoot set the value of this KLV data to null.");
		if (keyAndValue.limit() < 16)
			throw new IllegalArgumentException("Cannot set the value of a KLV data item with less than 16-bytes as this is missing a valid key.");
		
		this.klvDataValue = keyAndValue.duplicate();
	}
	
	public final static ByteBuffer initializeKLVDataValue() {
		
		return ByteBuffer.allocate(16);
	}
	
	public KLVData clone() {
		
		return (KLVData) super.clone();
	}

	public String getComment() {
		
		return "local KLV data persistent id: " + getPersistentID();
	}
	
	public byte[] getKLVDataValuePersist() {
		
		if (klvDataValue == null) return null;
		return klvDataValue.array();
	}
	
	public void setKLVDataValuePersist(
			byte[] klvDataValue) {
		
		if (klvDataValue == null) { this.klvDataValue = null; return; }
		
		this.klvDataValue = ByteBuffer.wrap(klvDataValue);
	}
}
