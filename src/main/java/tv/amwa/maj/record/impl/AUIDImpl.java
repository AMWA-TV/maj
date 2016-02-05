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
 * $Log: AUIDImpl.java,v $
 * Revision 1.5  2011/07/27 17:26:45  vizigoth
 * Do not report the nil UUID as a universal label as it is not valid as one.
 *
 * Revision 1.4  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/19 11:29:25  vizigoth
 * Corrected reference to UUID as URN RFC.
 *
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2010/12/15 18:55:13  vizigoth
 * Moved character to byte parsing methods into common utility functions.
 *
 * Revision 1.6  2010/05/14 18:32:02  vizigoth
 * Provided mechanism for a difference between structured storage byte writing and KLV byte writing.
 *
 * Revision 1.5  2010/03/19 09:35:09  vizigoth
 * Added support for reading AUID values from little endian order buffers.
 *
 * Revision 1.4  2010/02/10 23:58:59  vizigoth
 * Added new static methods for serialization of these types to bytes: lengthAsBuffer, writeToBuffer.
 *
 * Revision 1.3  2010/01/19 14:38:35  vizigoth
 * Moved parsing of record type from byte buffers done in a non standard way to creatFromBuffer factory methods in the implementation itself.
 *
 * Revision 1.2  2009/12/18 17:56:03  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:03  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/03/07 08:08:10  vizigoth
 * Edited comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:14:42  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.InstanceNumberGeneration;
import tv.amwa.maj.record.MaterialNumberGeneration;
import tv.amwa.maj.util.Utilities;

/** 
 * <p>Implementation of a 16&nbsp;byte unique identifier whose value is a 
 * SMPTE&nbsp;298M Universal Label or a UUID or GUID. This implementation provides methods
 * for generating AUIDs that are UUIDs and can be embedded within an entity to
 * become its primary key.</p>
 * 
 * <p>This class supports transformation of AUIDs to and from string representations as
 * Unique Resource Names (URNs), as defined in 
 * <a href="http://www.faqs.org/rfcs/rfc2141.html" alt="RFC 4122">RFC&nbsp;4122/a>. Use the
 * {@link #toString()} method and static {@link #parseFactory(String)} methods to do this.</p>
 * 
 * <p>This class makes a best effort to generate unique identifiers from the information
 * provided to it and the features provided by the Java APIs. However, there is no 100%
 * guarantee that it will generate a UUID that is totally unique. This class is provided
 * with out any warranty whatsoever and its use is entirely at the users own risk.</p>
 * 
 * <p>The internal representation of an AUID used by the MAJ API is an array of 16&nbsp;bytes.
 * This array can be manipulated by an application using the {@link #getAUIDValue()}, {@link #setAUIDValue(byte[])}
 * and {@link #AUID(byte[])} methods. The database schema for an AUID value is a column defined as
 * follows:</p>
 * 
 * <pre>
 *     `Identification` binary(16)
 * </pre>
 * 
 * @see AUIDGeneration
 * @see PackageIDImpl
 * @see tv.amwa.maj.model.DefinitionObject#getAUID()
 * @see java.util.UUID
 * @see tv.amwa.maj.industry.TypeDefinitions#AUID
 * @see tv.amwa.maj.misctype.AUIDArray
 * @see tv.amwa.maj.industry.TypeDefinitions#AUIDArray
 * @see tv.amwa.maj.industry.TypeDefinitions#AUIDSet
 * 
 *
 */

public final class AUIDImpl 
	implements
		AUID, 
		UL,
		Serializable,
		XMLSerializable,
		Cloneable,
		Comparable<AUID> { 

    /**
	 * Eclipse-generated serial version UID for this serializable class.
	 */
	private static final long serialVersionUID = 5546784434464118885L;

	/** <p>Template character array used for the generation of all UUID string values from AUIDs.</p> */
	private static final char[] uuidTemplate = new char[] {
		'u', 'r', 'n', ':', 'u', 'u', 'i', 'd', ':',
		'*', '*', '*', '*', '*', '*', '*', '*', '-',
		'*', '*', '*', '*', '-',
		'*', '*', '*', '*', '-',
		'*', '*', '*', '*', '-',
		'*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*'
	};
	
	/** <p>Byte to character value map used for efficient generation of string versions of AUIDs.</p> */
	private static final char[] hexCharMap = new char[] {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};
	
	/** <p>Byte to character value map used for efficient generation of string versions of AUIDs.</p> */
	private static final char[] bigHexCharMap = new char[] {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	};

	
	/** <p>Template character array used for the generation of all UL string values from AUIDs.</p> */
	private static final char[] ulTemplate = new char[] {
		'u', 'r', 'n', ':', 's', 'm', 'p', 't', 'e', ':', 'u', 'l', ':',
		'*', '*', '*', '*', '*', '*', '*', '*', '.',
		'*', '*', '*', '*', '*', '*', '*', '*', '.',
		'*', '*', '*', '*', '*', '*', '*', '*', '.',
		'*', '*', '*', '*', '*', '*', '*', '*'
	};
	
	private static final char[] persistTemplate = new char[] {
		'*', '*', '*', '*', '*', '*', '*', '*', '-',
		'*', '*', '*', '*', '-', '*', '*', '*', '*', '-',
		'*', '*', '*', '*', '-', '*', '*', '*', '*', 
		'*', '*', '*', '*', '*', '*', '*', '*'
	};
	
	/** <p>Offset to use to convert a Java time in milliseconds to the Gregorian-based offset
	 * used in UUID generation.</p> */
	static final long gregorianToJavaOffset = 12219292800000L;
	
	/** <p>Internal representation of the AUID value.</p> */
	private byte[] auidValue;
	
    /** Secure random number generator used to automatically create AUIDs. */ 
    private static SecureRandom randomMaker = null;
    
    /**
     * <p>Create a new AUID from its component parts.</p>
     * 
	 * @param data1 Integer value ... the first 4 bytes, also known as the "<code>time_low</code>"
	 * part.
	 * @param data2 Short value ... the next 2 bytes, also known as the "<code>time_mid</code>"
	 * part.
	 * @param data3 Short value ... the next 2 bytes, also known as the 
	 * "<code>time_mid_and_version</code>" part.
	 * @param data4 Array of 8 bytes ... the final 8 bytes, containing the variant, clock sequence
	 * and node parts. The value will be truncated or padded if more or less than 8 bytes are 
	 * provided respectively.
	 */
	public AUIDImpl(
			@UInt32 int data1, 
			@UInt16 short data2, 
			@UInt16 short data3, 
			@UInt8 byte[] data4) 
		throws NullPointerException {

		if (data4 == null)
			throw new NullPointerException("Cannot create AUID value with null value for data4, the last 8 bytes.");
		
		auidValue = new byte[16];
		setData1(data1);
		setData2(data2);
		setData3(data3);
		setData4(data4);
	}

	/**
	 * <p>Create a new AUID value from a 16-byte array representing its internal value. The given
	 * array is copied to make the internal representation used here safe from subsequent changes to
	 * the passed array. If the array length is less than or greater than 16, the array is padded with 
	 * 0's or truncated to contain exactly 16 bytes.</p>
	 *
	 * <p>If a SMPTE Universal Label is detected, starting with bytes <code>0x06</code>,
	 * <code>0x0e</code>, <code>0x2b</code> and <code>0x34</code>, then the first eight and
	 * last eight bytes are swapped over to make a valid AUID.</p>
	 * 
	 * @param auidValue Byte array to use to set the value of the new AUID.
	 * 
	 * @throws NullPointerException Argument is null.
	 */
	public AUIDImpl(
			byte[] auidValue) 
		throws NullPointerException {
		
		setAUIDValue(auidValue);
	}
	
	/**
	 * <p>Create a new UUID as an AUID using the random generation method. Creating an AUID
	 * with this constructor has the same result as calling the static factory method 
	 * {@link #randomAUID()}.</p>
	 *
	 * @see #randomAUID()
	 */
	public AUIDImpl() {
		
		setAUIDValue(randomAUID().getAUIDValue());
	}
	
	/** <p>A local node id to use in the generation of timebased UUIDs. This value is 
	 * synthesized from locally available data about the current host as the actual host
	 * id of the system cannot be retrieved using the Java 1.5 APIs (fixed in 1.6).</p> */
	private static byte[] localNodeID = Utilities.createLocalHostID(6);
	
	/**
	 * <p>Set the local node ID to use in the generation of timebased UUIDs as AUIDs. This
	 * method is provided to allow the default host id generated in the instantiation of this
	 * class to be overridden. As the actual host id (ethernet address) of a system is not
	 * available through the Java 1.5 APIs, the default value is generated from the IP address 
	 * and domain name of this system. Using this method, the actual host id of the
	 * system can be set, or any other 6-byte unique identifier for the host.</p>
	 *
	 * @param localNodeIDBytes A guaranteed to be unique identifier for the node on which this
	 * class is loaded. The value will be padded or truncated to 6-bytes.
	 * 
	 * @see #timebasedAUID()
	 */
	public static final void setLocalNodeID(
			byte[] localNodeIDBytes) {
		
		localNodeID = Utilities.checkBytes(localNodeIDBytes, 6);
	}
	
	/**
	 * <p>Create and return a new UUID as AUID generated according to the time and location
	 * based method, type&nbsp;1. The node id used for this method can be set by calling 
	 * {@link #setLocalNodeID(byte[])}.</p>
	 * 
	 * <p>Java's time resolution is to the nearest millisecond. Therefore, an extra counter
	 * is implemented in this method to provide an additional sequence of values to simulate
	 * the generation of time values measured in 100-nano second chunks as required by the
	 * UUID specification. The clock sequence part of the value is generated using a secure 
	 * random number generator.</p>
	 *
	 * @return Time and location based UUID as an AUID.
	 * 
	 * @see #setLocalNodeID(byte[])
	 * @see #auidFactory(AUIDGeneration, byte[])
	 */
	public static final AUIDImpl timebasedAUID() {
		
		return timebasedAUID(localNodeID);
	}
	
	/**
	 * <p>Create a new AUID using pseudo-random number generation, type&nbsp;4. This method uses
	 * the <code>java.security.SecureRandom</code> number generator, seeded from values
	 * in the local environment obscured with a mixing function.</p>
	 * 
	 * @return Random generation of a new UUID as an AUID. 
	 * 
	 * @see #auidFactory(AUIDGeneration, byte[])
	 * @see #AUID()
	 * @see tv.amwa.maj.util.Utilities#seedRandomMaker()
	 */
	public static final AUIDImpl randomAUID() {

		if (randomMaker == null)
			randomMaker = Utilities.seedRandomMaker();

		int data1 = randomMaker.nextInt();
		short data2 = (short) randomMaker.nextInt();
		short data3 = (short) (0x4000 | (randomMaker.nextInt() & 0x0fff));

		byte[] data4 = new byte[8];
		randomMaker.nextBytes(data4);

		data4[0] = (byte) (128 | (data4[0] & 63));
		
		return new AUIDImpl(data1, data2, data3, data4);
	}

	/**
	 * <p>Creates a new UUID as an AUID generated with the name-based method, type&nbsp;3. This
	 * method is based on {@link java.util.UUID#nameUUIDFromBytes(byte[])} that uses an
	 * MD5 algorithm.</p>
	 *
	 * @param nameData Name data to use to create a namebased UUID.
	 * @return Namebased UUID as an AUID.
	 * 
	 * @throws NullPointerException The name data argument is <code>null</code>.
	 * 
	 * @see #auidFactory(AUIDGeneration, byte[])
	 * @see java.util.UUID#nameUUIDFromBytes(byte[])
	 */
	public static final AUIDImpl namebasedAUID(
			byte[] nameData) {
		
		if (nameData == null)
			throw new NullPointerException("Cannot create a new name based UUID using null name data.");
		
		UUID uuid = UUID.nameUUIDFromBytes(nameData);
		
		byte[] auidValue = new byte[16];
		
		long msbs = uuid.getMostSignificantBits();
		for ( int x = 7 ; x > -1 ; x-- ) {
			auidValue[x] = (byte) (msbs & 0xff);
			msbs >>>= 8;
		}
		
		long lsbs = uuid.getLeastSignificantBits();
		for ( int x = 15 ; x > 7 ; x-- ) {
			auidValue[x] = (byte) (lsbs & 0xff);
			lsbs >>>= 8;
		}
		
		return new AUIDImpl(auidValue);
	}
	
	/** <p>Internal counter used to generate greater than millisecond precision for 
	 * timebased UUID generation.</p> */
	private static Long type1Counter = 0l;
	
	/**
	 * <p>Generates a new UUID as an AUID from the current time and a location provided
	 * by the given node id. The node id must be a byte array containing at least 6 values.</p>
	 * 
	 * <p>Java's time resolution is to the nearest millisecond. Therefore, an extra counter
	 * is implemented in this method to provide an additional sequence of values to simulate
	 * the generation of time values measured in 100-nano second chunks as required by the
	 * UUID specification. The clock sequence part of the value is generated using a secure 
	 * random number generator.</p>
	 *
	 * @param nodeId Unique identifier for this computational node, which must contain at least
	 * 6&nbsp;bytes.
	 * @return Time and location based UUID as an AUID.
	 * 
	 * @throws NullPointerException The node id byte array argument is <code>null</code>.
	 * @throws IllegalArgumentException The node id must contain at least 6 bytes.
	 * 
	 * @see #timebasedAUID()
	 * @see #auidFactory(AUIDGeneration, byte[])
	 */
	public static final AUIDImpl timebasedAUID(
			byte[] nodeId) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (nodeId == null)
			throw new NullPointerException("Cannot create a new time-based AUID from a null node ID.");
		if (nodeId.length < 6)
			throw new IllegalArgumentException("Cannot create a new time-based AUID from a node id with less than 6 bytes.");
		
		if (randomMaker == null)
			randomMaker = Utilities.seedRandomMaker();
		
		long timeValue;
		
		synchronized (type1Counter) {
			timeValue = new Date().getTime();
			
			timeValue += gregorianToJavaOffset;
			timeValue = (timeValue * 10000) + (type1Counter & 0x1fff);
			
			type1Counter++;
		}
		
		short clockSequence = (short) (0x8000 | (randomMaker.nextInt() & 0x1fff));
		
		int data1 = (int) (timeValue & 0xffffffff);
		short data2 = (short) ((timeValue >>> 32) & 0xffff);
		short data3 = (short) (0x1000 | ((timeValue >>> 48) & 0xfff));
		
		byte[] data4 = new byte[8];
		data4[0] = (byte) ((clockSequence >>> 8) & 0xff);
		data4[1] = (byte) (clockSequence & 0xff);
		
		System.arraycopy(nodeId, 0, data4, 2, 6);
		
		return new AUIDImpl(data1, data2, data3, data4);
	}

	/**
	 * <p>Generates a new UUID as an AUID using the given 
	 * {@link AUIDGeneration generation method}. Three different types are supported
	 * by this factory:</p>
	 * 
	 * <ul>
	 *  <li>{@link AUIDGeneration#IETF_Type1} or {@link AUIDGeneration#Timebased}: 
	 *  Generate a time and location based UUID as an AUID. If the extra data is <code>null</code>
	 *  then this method is equivalent to calling {@link #timebasedAUID()} and if extra data
	 *  is provided, this method is equivalent to calling {@link #timebasedAUID(byte[])}.</li>
	 *  
	 *  <li>{@link AUIDGeneration#IETF_Type3} or {@link AUIDGeneration#Namebased}:
	 *  Generate a name based UUID as an AUID. Extra data must be provided and this method
	 *  is equivalent to calling {@link #namebasedAUID(byte[])}.</li>
	 *  
	 *  <li>{@link AUIDGeneration#IETF_Type4} or {@link AUIDGeneration#Random}:
	 *  Generate a random UUID as an AUID. The value of the extra data argument is ignored
	 *  and this method is equivalent to calling {@link #randomAUID()}.</li>
	 * 
	 * </ul>
	 *
	 * @param type Generator type for a new UUID as an AUID.
	 * @param extraData Additional data that may be required by the given generation method.
	 * @return Newly generated UUID as an AUID.
	 * 
	 * @throws NullPointerException The type and/or extra data arguments is/are <code>null</code>.
	 * 
	 * @see #randomAUID()
	 * @see #timebasedAUID()
	 * @see #namebasedAUID(byte[])
	 * @see PackageIDImpl#umidFactory(tv.amwa.maj.enumeration.MaterialType, MaterialNumberGeneration, InstanceNumberGeneration, byte[]) umidFactory()
	 */
	public static final AUIDImpl auidFactory(
			AUIDGeneration type, 
			byte[] extraData) 
		throws NullPointerException {
		
		if (type == null)
			throw new NullPointerException("Cannot create a new UUID as an AUID using a null generation type.");
		
		switch (type) {
		
		case IETF_Type1:
		case Timebased:
			if (extraData == null)
				return timebasedAUID();
			else
				return timebasedAUID(extraData);
		case IETF_Type3:
		case Namebased:
			if (extraData != null)
				return namebasedAUID(extraData);
			else
				throw new NullPointerException("Cannot create a new name based AUID without name data.");
		case IETF_Type4:
		case Random:
			return randomAUID();
		default:
			throw new NullPointerException("Cannot create a new AUID for an unknown or null generation type.");	
		}
	}
	
	public final int getData1() {
		return ((((int) auidValue[0]) & 255) << 24) |
			((((int) auidValue[1]) & 255) << 16) |
			((((int) auidValue[2]) & 255) << 8) |
			(((int) auidValue[3]) & 255);
	}

	public final void setData1(int data1) {
		
		auidValue[0] = (byte) (data1 >>> 24);
		auidValue[1] = (byte) ((data1 >>> 16) & 255);
		auidValue[2] = (byte) ((data1 >>> 8) & 255);
		auidValue[3] = (byte) (data1 & 255);
 	}

	public final short getData2() {
		return (short) (
				((((int) auidValue[4]) & 255) << 8) | 
				(((int) auidValue[5]) & 255));
	}

	public final void setData2(short data2) {

		auidValue[4] = (byte) (data2 >>> 8);
		auidValue[5] = (byte) (data2 & 255);
	}

	public final short getData3() {
		return (short) (
				((((int) auidValue[6]) & 255) << 8) | 
				(((int) auidValue[7]) & 255));
	}

	public final void setData3(short data3) {
		
		auidValue[6] = (byte) (data3 >>> 8);
		auidValue[7] = (byte) (data3 & 255);
	}

	public final byte[] getData4() {
		
		byte[] data4 = new byte[8];
		System.arraycopy(auidValue, 8, data4, 0, 8);
		return data4;
	}

	/** 
	 * @see tv.amwa.maj.record.AUID#setData4(byte[])
	 */
	public final void setData4(byte[] data4) {
		
		data4 = Utilities.checkBytes(data4, 8);
		System.arraycopy(data4, 0, auidValue, 8, 8);
	}

	/**
	 * <p>Returns a copy of the array of bytes used to represent this AUID internally.</p>
	 *
	 * @return Array of 16 bytes representing this AUID value.
	 */
	public final byte[] getAUIDValue() {
		
		return auidValue.clone();
	}
	
	/**
	 * <p>Sets the array of bytes used to represent this AUID internally. The given
	 * array is copied to make the internal representation used here safe from subsequent changes to
	 * the passed array. If the array length is less than or greater than 16, the array is padded with 
	 * 0's or truncated to contain exactly 16 bytes.</p>
	 * 
	 * <p>If a SMPTE Universal Label is detected, starting with bytes <code>0x06</code>,
	 * <code>0x0e</code>, <code>0x2b</code> and <code>0x34</code>, then the first eight and
	 * last eight bytes are swapped over to make a valid AUID.</p>
	 *
	 * @param auidValue Array of bytes to use to set the value of this AUID.
	 * 
	 * @throws NullPointerException The given value is <code>null</code>.
	 */
	public final void setAUIDValue(
			byte[] auidValue) 
		throws NullPointerException {
		
		if (auidValue == null)
			throw new NullPointerException("Cannot create AUID from a null byte array.");
		
		// Data is always cloned in the check bytes method 
		auidValue = Utilities.checkBytes(auidValue, 16);
		if ((auidValue[0] == 0x06) && (auidValue[1] == 0x0e) && (auidValue[2] == 0x2b) && (auidValue[3] == 0x34) &&
				((auidValue[8] & 128) == 0)) // Detected a universal label not yet swapped
			this.auidValue = swapIDBytes(auidValue);
		else
			this.auidValue = auidValue;
	}
	
	
	private final static byte[] swapIDBytes(
			byte[] idBytes)  {
		
		byte swapByte;
		for ( int u = 0 ; u < 8 ; u++ ) {
			swapByte = idBytes[u];
			idBytes[u] = idBytes[u + 8];
			idBytes[u + 8] = swapByte;
		}

		return idBytes;
	}
	
	/** 
	 * <p>Check to see if all the component values of this AUID are set to&nbsp;0, the only non-unique
	 * value for an AUID.</p>
	 * 
	 * @return True if all the component values of the AUID are&nbsp;0.
	 */
	public final boolean isNil() {

		for ( byte element : auidValue )
			if (element != (byte) 0) return false;
		
		return true;
	}

	/** 
	 * <p>Checks to see if two AUID values are equal.</p>
	 * 
	 * @param o Object to test for equality with this one.
	 * @return Is the given AUID value equal to this AUID?
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @see #auidsEqual(AUIDImpl, AUIDImpl)
	 */
	public final boolean equals(
			Object o) {
		
		if (o == null) return false;
		if (!(o instanceof AUID)) return false;
		
		return auidsEqual(this, (AUID) o);
	}
	
	/**
	 * <p>Compares two AUIDs to see if they represent equal values. Two AUIDs are equal 
	 * if and only if each of their component values are equal. This is always the case
	 * if the two objects that are provided are the same.</p>
	 * 
	 * @param auid1 First AUID to test for equality with.
	 * @param auid2 Second AUID to test for equality with the first.
	 * @return Are the values of the two provided AUIDs equal?
	 */
	public final static boolean auidsEqual(
			AUID auid1, 
			AUID auid2) 
		throws NullPointerException {

		if ((auid1 == null) || (auid2 == null))
			throw new NullPointerException("Cannot check for AUID value equality with null objects.");
		if (auid1 == auid2) return true;
		
		if (auid1.getData1() != auid2.getData1()) return false;
		if (auid1.getData2() != auid2.getData2()) return false;
		if (auid1.getData3() != auid2.getData3()) return false;
		
		for ( int u = 0 ; u < auid1.getData4().length ; u++)
			if (auid1.getData4()[u] != auid2.getData4()[u]) return false;
		
		return true;		
	}

	/** 
	 * <p>Converts this AUID value into a string, formatted to be compatible with the 
	 * registered namespace for AUIDs as defined in section 3 of <a 
	 * href="http://www.faqs.org/rfcs/rfc4122.html" alt="RFC 4122">rfc 4122</a>. The
	 * returned value is an URN. This method does not check to see if this AUID represents
	 * a valid UUID.</p>
	 * 
	 * <p>The canonical form for an AUID in this implementation is to use lowercase
	 * letters for the hexidecimal digits "a" to "f".</p>
	 * 
	 * @return String UUID value of this AUID.
	 * 
	 * @see java.lang.Object#toString()
	 */
	final String toUUIDString() {

		char[] uuid = uuidTemplate.clone();
		
		uuid[9]  = hexCharMap[(auidValue[0] >>> 4) & 15];
		uuid[10] = hexCharMap[auidValue[0] & 15];
		uuid[11] = hexCharMap[(auidValue[1] >>> 4) & 15];
		uuid[12] = hexCharMap[auidValue[1] & 15];
		uuid[13] = hexCharMap[(auidValue[2] >>> 4) & 15];
		uuid[14] = hexCharMap[auidValue[2] & 15];
		uuid[15] = hexCharMap[(auidValue[3] >>> 4) & 15];
		uuid[16] = hexCharMap[auidValue[3] & 15];
		
		uuid[18] = hexCharMap[(auidValue[4] >>> 4) & 15];
		uuid[19] = hexCharMap[auidValue[4] & 15];
		uuid[20] = hexCharMap[(auidValue[5] >>> 4) & 15];
		uuid[21] = hexCharMap[auidValue[5] & 15];
		
		uuid[23] = hexCharMap[(auidValue[6] >>> 4) & 15];
		uuid[24] = hexCharMap[auidValue[6] & 15];
		uuid[25] = hexCharMap[(auidValue[7] >>> 4) & 15];
		uuid[26] = hexCharMap[auidValue[7] & 15];
		
		uuid[28] = hexCharMap[(auidValue[8] >>> 4) & 15];
		uuid[29] = hexCharMap[auidValue[8] & 15];
		uuid[30] = hexCharMap[(auidValue[9] >>> 4) & 15];
		uuid[31] = hexCharMap[auidValue[9] & 15];
		
		uuid[33] = hexCharMap[(auidValue[10] >>> 4) & 15];
		uuid[34] = hexCharMap[auidValue[10] & 15];
		uuid[35] = hexCharMap[(auidValue[11] >>> 4) & 15];
		uuid[36] = hexCharMap[auidValue[11] & 15];
		uuid[37] = hexCharMap[(auidValue[12] >>> 4) & 15];
		uuid[38] = hexCharMap[auidValue[12] & 15];
		
		uuid[39] = hexCharMap[(auidValue[13] >>> 4) & 15];
		uuid[40] = hexCharMap[auidValue[13] & 15];
		uuid[41] = hexCharMap[(auidValue[14] >>> 4) & 15];
		uuid[42] = hexCharMap[auidValue[14] & 15];
		uuid[43] = hexCharMap[(auidValue[15] >>> 4) & 15];
		uuid[44] = hexCharMap[auidValue[15] & 15];
		
		return new String(uuid);
		
	}

	/** 
	 * <p>Converts this AUID value into a string, formatted to be compatible with the 
	 * registered namespace for Universal Labels as defined in SMPTE 298M-1997. The
	 * returned value is an URN. This method does not check to see if this AUID represents
	 * a valid universal label.</p>
	 * 
	 * <p>The canonical form for a Universal Label in this implementation is to use lowercase
	 * letters for the hexidecimal digits "a" to "f".</p>
	 * 
	 * @return String Universal Label value of this AUID.
	 * 
	 * @see java.lang.Object#toString()
	 */
	final String toULString() {
		
		char[] ul = ulTemplate.clone();
		
		ul[13]  = hexCharMap[(auidValue[8] >>> 4) & 15];
		ul[14] = hexCharMap[auidValue[8] & 15];
		ul[15] = hexCharMap[(auidValue[9] >>> 4) & 15];
		ul[16] = hexCharMap[auidValue[9] & 15];
		ul[17] = hexCharMap[(auidValue[10] >>> 4) & 15];
		ul[18] = hexCharMap[auidValue[10] & 15];
		ul[19] = hexCharMap[(auidValue[11] >>> 4) & 15];
		ul[20] = hexCharMap[auidValue[11] & 15];
		
		ul[22] = hexCharMap[(auidValue[12] >>> 4) & 15];
		ul[23] = hexCharMap[auidValue[12] & 15];
		ul[24] = hexCharMap[(auidValue[13] >>> 4) & 15];
		ul[25] = hexCharMap[auidValue[13] & 15];
		ul[26] = hexCharMap[(auidValue[14] >>> 4) & 15];
		ul[27] = hexCharMap[auidValue[14] & 15];
		ul[28] = hexCharMap[(auidValue[15] >>> 4) & 15];
		ul[29] = hexCharMap[auidValue[15] & 15];
		
		ul[31] = hexCharMap[(auidValue[0] >>> 4) & 15];
		ul[32] = hexCharMap[auidValue[0] & 15];
		ul[33] = hexCharMap[(auidValue[1] >>> 4) & 15];
		ul[34] = hexCharMap[auidValue[1] & 15];
		ul[35] = hexCharMap[(auidValue[2] >>> 4) & 15];
		ul[36] = hexCharMap[auidValue[2] & 15];
		ul[37] = hexCharMap[(auidValue[3] >>> 4) & 15];
		ul[38] = hexCharMap[auidValue[3] & 15];
		
		ul[40] = hexCharMap[(auidValue[4] >>> 4) & 15];
		ul[41] = hexCharMap[auidValue[4] & 15];
		ul[42] = hexCharMap[(auidValue[5] >>> 4) & 15];
		ul[43] = hexCharMap[auidValue[5] & 15];
		ul[44] = hexCharMap[(auidValue[6] >>> 4) & 15];
		ul[45] = hexCharMap[auidValue[6] & 15];
		ul[46] = hexCharMap[(auidValue[7] >>> 4) & 15];
		ul[47] = hexCharMap[auidValue[7] & 15];
	
		return new String(ul);
	}
	
	/**
	 * <p>Returns <code>true</code> if this AUID value is a SMPTE Universal Label, as defined 
	 * in SMPTE 298M-1997. An AUID can be either an Universal Label or an UUID, so if this methods
	 * returns <code>false</code> then this is an UUID. All UUIDs have the MSB of their 9th byte
	 * set, with the exception of the nil UUID.</p>
	 *
	 * @return Does this AUID represent a SMPTE Universal Label?
	 */
	public final boolean isUniversalLabel() {
		
		if (isNil()) return false;
		return ((auidValue[8] & 128) == 0);  // MSB of 9th byte is not set
	}
	
	/** 
	 * <p>Create a string representation of this AUID as either an URN for a Universal Label
	 * (starting with "<code>urn:x-ul:</code>") or an URN for an UUID (starting with 
	 * "<code>urn:uuid:</code>"). The kind of URN to be expected can be found by calling
	 * {@link #isUniversalLabel()}. Values created by this method can be parsed using the
	 * {@link #parseFactory(String)} method and will create an AUID of an equivalent value
	 * to this one.</p>
	 * 
	 * <p>The canonical representation of hexadecimal characters used in this implementation
	 * is lower case, eg. '<code>ae0c</code>' rather than '<code>AE0C</code>' or 
	 * '<code>Ae0C</code>'.</p>
	 * 
	 * <p>The following is the string representation of the Universal Label for timecode
	 * type data:</p>
	 * 
	 * <p><center><code>urn:smpte:ul:060e2b34.0401.0101.01030201.01000000</center></code></p>
	 * 
	 * <p>The following is the string representation of the UUID for the edgecode type data:</p>
	 * 
	 * <p><center><code>urn:uuid:d2bb2af0-d234-11d2-89ee-006097116212</center></code></p>
	 * 
	 * @return String representation of this AUID as an URN.
	 * 
	 * @see #parseFactory(String)
	 */
	public final String toString() {
		
		if (isNil())
			return "urn:uuid:00000000-0000-0000-0000-000000000000";
		
		if (isUniversalLabel())
			return toULString();
		else
			return toUUIDString();
	}
	
    /**
     * <p>Create a new AUID from a URN representation, as specified in section&nbsp;3 of
     * <a href="http://www.faqs.org/rfcs/rfc4122.html" alt="RFC 4122">rfc 4122</a> or
     * the commonly used URN representation of Universal Labels (starting with 
     * "<code>urn:x-ul:</code>" or "<code>urn:smpte:ul:</code>"). The method accepts 
     * hexadecimal digits in upper or lower case.</p>
     * 
     * @param auidValue String value to use to create a new AUID.
     * @return Newly created AUID value corresponding to the given string value.
     * 
     * @throws NullPointerException AUID string value argument is <code>null</code>.
     * @throws NumberFormatException The given string is not recognised as an AUID value, 
     * either because it is of the wrong length, has its separators in the wrong place, 
     * uses the wrong separators or contains non-hexadecimal values. 
     * 
     * @see #toString()
     */
    public final static AUIDImpl parseFactory(
    		String auidValue) 
		throws NullPointerException, 
			NumberFormatException {

    	if (auidValue == null)
    		throw new NullPointerException("Cannot create an AUID from a null string.");
    	auidValue = auidValue.trim();
    	
    	if (auidValue.startsWith("urn:smpte:ul:")) 
    		return parseUL(auidValue);
    	if (auidValue.startsWith("urn:uuid:"))
    		return parseUUID(auidValue);
    	if (auidValue.startsWith("urn:x-ul:"))
    		return parseULOld(auidValue);
    	
    	throw new NumberFormatException("The given string does not appear to be a Universal Label or an UUID.");
    }
    
    /**
     * <p>Parse a UUID URN value and create an instance of this AUID class.</p>
     *
     * @param auidValue String representation of an UUID as an URN.
     * @return AUID of an equivalent value to the given UUID.
     * 
     * @throws NumberFormatException At least one of the given characters that was expected to
     * be a hexadecimal character was not.
     */
    final static AUIDImpl parseUUID(
    		String auidValue) 
    	throws NumberFormatException {
    	
    	if (auidValue.length() != 45)
    		throw new NumberFormatException("The given string is the wrong length for a complete AUID value, UL or UUID.");

    	byte[] auid = new byte[16];
    	char[] urn = auidValue.toCharArray();
    	
    	auid[0] = Utilities.twoCharsToByte(urn[9], urn[10]);
    	auid[1] = Utilities.twoCharsToByte(urn[11], urn[12]);
    	auid[2] = Utilities.twoCharsToByte(urn[13], urn[14]);
    	auid[3] = Utilities.twoCharsToByte(urn[15], urn[16]);
    	
    	auid[4] = Utilities.twoCharsToByte(urn[18], urn[19]);
    	auid[5] = Utilities.twoCharsToByte(urn[20], urn[21]);
    	
    	auid[6] = Utilities.twoCharsToByte(urn[23], urn[24]);
    	auid[7] = Utilities.twoCharsToByte(urn[25], urn[26]);
    	
    	auid[8] = Utilities.twoCharsToByte(urn[28], urn[29]);
    	auid[9] = Utilities.twoCharsToByte(urn[30], urn[31]);
    	
    	auid[10] = Utilities.twoCharsToByte(urn[33], urn[34]);
    	auid[11] = Utilities.twoCharsToByte(urn[35], urn[36]);    	
    	auid[12] = Utilities.twoCharsToByte(urn[37], urn[38]);
    	auid[13] = Utilities.twoCharsToByte(urn[39], urn[40]);    	
    	auid[14] = Utilities.twoCharsToByte(urn[41], urn[42]);
    	auid[15] = Utilities.twoCharsToByte(urn[43], urn[44]);
    	
    	return new AUIDImpl(auid);
    } 

    /**
     * <p>Parse a Universal Label URN value starting "<code>urn:x-ul:</code>" 
     * and create an instance of this AUID class.</p>
     *
     * @param auidValue String representation of an Universal Label as an URN.
     * @return AUID of an equivalent value to the given Universal Label.
     * 
     * @throws NumberFormatException At least one of the given characters that was expected to
     * be a hexidecimal character was not.
     */
    final static AUIDImpl parseULOld(
    		String auidValue) 
    	throws NumberFormatException {
    	
    	if (auidValue.length() != 45)
    		throw new NumberFormatException("The given string is the wrong length for a complete AUID value, UL or UUID.");

    	byte[] auid = new byte[16];
    	char[] urn = auidValue.toCharArray();

    	auid[0] = Utilities.twoCharsToByte(urn[28], urn[29]);
    	auid[1] = Utilities.twoCharsToByte(urn[30], urn[31]);
    	auid[2] = Utilities.twoCharsToByte(urn[32], urn[33]);
    	auid[3] = Utilities.twoCharsToByte(urn[34], urn[35]);
    	
    	auid[4] = Utilities.twoCharsToByte(urn[37], urn[38]);
    	auid[5] = Utilities.twoCharsToByte(urn[39], urn[40]);
    	auid[6] = Utilities.twoCharsToByte(urn[41], urn[42]);
    	auid[7] = Utilities.twoCharsToByte(urn[43], urn[44]);
    	
    	auid[8] = Utilities.twoCharsToByte(urn[9], urn[10]);
    	auid[9] = Utilities.twoCharsToByte(urn[11], urn[12]);
    	auid[10] = Utilities.twoCharsToByte(urn[13], urn[14]);
    	auid[11] = Utilities.twoCharsToByte(urn[15], urn[16]);
    	
    	auid[12] = Utilities.twoCharsToByte(urn[18], urn[19]);
    	auid[13] = Utilities.twoCharsToByte(urn[20], urn[21]);
    	
    	auid[14] = Utilities.twoCharsToByte(urn[23], urn[24]);
    	auid[15] = Utilities.twoCharsToByte(urn[25], urn[26]);
     	
    	return new AUIDImpl(auid);
    }
    
    /**
     * <p>Parse a Universal Label URN value starting "<code>urn:smpte:ul:</code>" 
     * and create an instance of this AUID class.</p>
     *
     * @param auidValue String representation of an Universal Label as an URN.
     * @return AUID of an equivalent value to the given Universal Label.
     * 
     * @throws NumberFormatException At least one of the given characters that was expected to
     * be a hexidecimal character was not.
     */
    final static AUIDImpl parseUL(
    		String auidValue) 
    	throws NumberFormatException {
    	
    	if (auidValue.length() != 48)
    		throw new NumberFormatException("The given string is the wrong length for a complete universal label.");

    	byte[] auid = new byte[16];
    	char[] urn = auidValue.toCharArray();

    	auid[0] = Utilities.twoCharsToByte(urn[31], urn[32]);
    	auid[1] = Utilities.twoCharsToByte(urn[33], urn[34]);
    	auid[2] = Utilities.twoCharsToByte(urn[35], urn[36]);
    	auid[3] = Utilities.twoCharsToByte(urn[37], urn[38]);
    	
    	auid[4] = Utilities.twoCharsToByte(urn[40], urn[41]);
    	auid[5] = Utilities.twoCharsToByte(urn[42], urn[43]);
    	auid[6] = Utilities.twoCharsToByte(urn[44], urn[45]);
    	auid[7] = Utilities.twoCharsToByte(urn[46], urn[47]);
    	
    	auid[8] = Utilities.twoCharsToByte(urn[13], urn[14]);
    	auid[9] = Utilities.twoCharsToByte(urn[15], urn[16]);
    	auid[10] = Utilities.twoCharsToByte(urn[17], urn[18]);
    	auid[11] = Utilities.twoCharsToByte(urn[19], urn[20]);
    	
    	auid[12] = Utilities.twoCharsToByte(urn[22], urn[23]);
    	auid[13] = Utilities.twoCharsToByte(urn[24], urn[25]);
    	auid[14] = Utilities.twoCharsToByte(urn[26], urn[27]);
    	auid[15] = Utilities.twoCharsToByte(urn[28], urn[29]);

    	// Defensive code against badly behaving AAF reference implementation
		if (!((auid[0] == 0x06) && (auid[1] == 0x0e) && (auid[2] == 0x2b) && (auid[3] == 0x34) &&
				((auid[8] & 128) == 0))) // This isn't a universal label!!!
			auid = swapIDBytes(auid);

    	return new AUIDImpl(auid);
    }

    public final int hashCode() {
    	
    	return ((auidValue[0] ^ auidValue[4] ^ auidValue[8] ^ auidValue[12]) << 24) ^
			((auidValue[1] ^ auidValue[5] ^ auidValue[9] ^ auidValue[13]) << 16) ^
			((auidValue[2] ^ auidValue[6] ^ auidValue[10] ^ auidValue[14]) << 8) ^
			(auidValue[3] ^ auidValue[7] ^ auidValue[11] ^ auidValue[15]);
    }

    public final UL clone() {
    	
    	try {
    		AUIDImpl cloned = (AUIDImpl) super.clone();
    		cloned.setAUIDValue(auidValue);
    		return cloned;
    	}
    	catch (CloneNotSupportedException cnse) {
    		cnse.printStackTrace();
    		return null;
    	}
    }

	public final void appendXMLChildren(
			Node parent) {
	
		Document document = parent.getOwnerDocument();
		Text idText = document.createTextNode(toString());
		parent.appendChild(idText);
	}

	public byte[] getUniversalLabel() {

		return swapIDBytes(auidValue.clone());
	}

	public String getComment() {

		return null;
	}

	// Provide an ordering in AUIDs so that the identifiers can be used in sorted maps
	public int compareTo(
			AUID o) {

		if (getData1() < o.getData1()) return -1;
		if (getData1() > o.getData1()) return 1;
		
		if (getData2() < o.getData2()) return -1;
		if (getData2() > o.getData2()) return 1;
		
		if (getData3() < o.getData3()) return -1;
		if (getData3() > o.getData3()) return 1;
		
		byte[] localBytes = getData4();
		byte[] comparisonBytes = o.getData4();
		
		for ( int u = 0 ; u < 8 ; u++ ) {
			if (localBytes[u] < comparisonBytes[u]) return -1;
			if (localBytes[u] > comparisonBytes[u]) return 1;
		}
		
		return 0;
	}
	
	// TODO documentation on these special methods
	public final static AUID createFromBuffer(
			ByteBuffer buffer) 
		throws NullPointerException,
			EndOfDataException {
		
		if (buffer == null)
			throw new NullPointerException("Cannot read an AUID value from a null buffer.");
		
		if (buffer.remaining() < 16)
			throw new EndOfDataException("Not enough bytes remaining in the given buffer to read an AUID value.");
		
		if (buffer.order() == ByteOrder.LITTLE_ENDIAN) {
			int data1 = buffer.getInt();
			short data2 = buffer.getShort();
			short data3 = buffer.getShort();
			byte[] data4 = new byte[8];
			buffer.get(data4);
			return new AUIDImpl(data1, data2, data3, data4);
		}
		
		byte[] keyBytes = new byte[16];
		buffer.get(keyBytes);
		
		return new AUIDImpl(keyBytes);
	}
	
	public final static long lengthAsBuffer(
			AUID value) {
		
		return 16l;
	}
	
	public final static void writeToBuffer(
			AUID value,
			ByteBuffer buffer)
		throws NullPointerException,
			InsufficientSpaceException {
		
		if (value == null)
			throw new NullPointerException("Cannot write a null AUID value to a buffer.");
		if (buffer == null)
			throw new NullPointerException("Cannot write an AUID value to a null buffer.");
		
		if (buffer.remaining() < 16)
			throw new InsufficientSpaceException("Insufficient space is available to write a 16 byte value to the given buffer.");
		
		if (value.isUniversalLabel()) 
			buffer.put(((UL) value).getUniversalLabel());
		else
			buffer.put(value.getAUIDValue());
	}
	
	// Version that does not swap universal labels
	public final static void writeToStructuredStorageBuffer(
			AUID value,
			ByteBuffer buffer)
		throws NullPointerException,
			InsufficientSpaceException {
		
		if (value == null)
			throw new NullPointerException("Cannot write a null AUID value to a buffer.");
		if (buffer == null)
			throw new NullPointerException("Cannot write an AUID value to a null buffer.");
		
		if (buffer.remaining() < 16)
			throw new InsufficientSpaceException("Insufficient space is available to write a 16 byte value to the given buffer.");

		buffer.put(value.getAUIDValue());		
	}
	
	public final static void generateEmbeddableORM(
			Node parent,
			String namespace,
			String prefix) {
		
		Element embeddable = XMLBuilder.createChild(parent, namespace, prefix, "embeddable");
		XMLBuilder.setAttribute(embeddable, namespace, prefix, "class", AUIDImpl.class.getCanonicalName());
		XMLBuilder.setAttribute(embeddable, namespace, prefix, "access", "FIELD");
		
		Element embeddableAttributes = XMLBuilder.createChild(embeddable, namespace, prefix, "attributes");
		
		Element basic = XMLBuilder.createChild(embeddableAttributes, namespace, prefix, "basic");
		XMLBuilder.setAttribute(basic, namespace, prefix, "name", "auidValue");
	}
	
	public final static void generateEmbeddedORM(
			Node parent,
			String ownerName,
			String namespace,
			String prefix) {
		
		Element embedded = XMLBuilder.createChild(parent, namespace, prefix, "embedded");
		XMLBuilder.setAttribute(embedded, namespace, prefix, "name", Utilities.lowerFirstLetter(ownerName));
		
		Element attributeOverride = XMLBuilder.createChild(embedded, namespace, prefix, "attribute-override");
		XMLBuilder.setAttribute(attributeOverride, namespace, prefix, "name", "auidValue");
		Element column = XMLBuilder.createChild(attributeOverride, namespace, prefix, "column");
		XMLBuilder.setAttribute(column, namespace, prefix, "name", ownerName);
	}
	
	public final static String MYSQL_COLUMN_DEFINITION = 
		"CHAR(36) CHARACTER SET ascii COLLATE ascii_general_ci";
	
	public final static String toPersistentForm(
			AUID auid) {
		
		if (auid == null) return null;
		
		byte[] auidValue = auid.getAUIDValue();
		char[] persistUUID = persistTemplate.clone();
		
		persistUUID[0] = bigHexCharMap[(auidValue[0] >>> 4) & 15];
		persistUUID[1] = bigHexCharMap[auidValue[0] & 15];
		persistUUID[2] = bigHexCharMap[(auidValue[1] >>> 4) & 15];
		persistUUID[3] = bigHexCharMap[auidValue[1] & 15];
		persistUUID[4] = bigHexCharMap[(auidValue[2] >>> 4) & 15];
		persistUUID[5] = bigHexCharMap[auidValue[2] & 15];
		persistUUID[6] = bigHexCharMap[(auidValue[3] >>> 4) & 15];
		persistUUID[7] = bigHexCharMap[auidValue[3] & 15];
		
		persistUUID[9] = bigHexCharMap[(auidValue[4] >>> 4) & 15];
		persistUUID[10] = bigHexCharMap[auidValue[4] & 15];
		persistUUID[11] = bigHexCharMap[(auidValue[5] >>> 4) & 15];
		persistUUID[12] = bigHexCharMap[auidValue[5] & 15];
		
		persistUUID[14] = bigHexCharMap[(auidValue[6] >>> 4) & 15];
		persistUUID[15] = bigHexCharMap[auidValue[6] & 15];
		persistUUID[16] = bigHexCharMap[(auidValue[7] >>> 4) & 15];
		persistUUID[17] = bigHexCharMap[auidValue[7] & 15];
		
		persistUUID[19] = bigHexCharMap[(auidValue[8] >>> 4) & 15];
		persistUUID[20] = bigHexCharMap[auidValue[8] & 15];
		persistUUID[21] = bigHexCharMap[(auidValue[9] >>> 4) & 15];
		persistUUID[22] = bigHexCharMap[auidValue[9] & 15];
		
		persistUUID[24] = bigHexCharMap[(auidValue[10] >>> 4) & 15];
		persistUUID[25] = bigHexCharMap[auidValue[10] & 15];
		persistUUID[26] = bigHexCharMap[(auidValue[11] >>> 4) & 15];
		persistUUID[27] = bigHexCharMap[auidValue[11] & 15];
		persistUUID[28] = bigHexCharMap[(auidValue[12] >>> 4) & 15];
		persistUUID[29] = bigHexCharMap[auidValue[12] & 15];
		
		persistUUID[30] = bigHexCharMap[(auidValue[13] >>> 4) & 15];
		persistUUID[31] = bigHexCharMap[auidValue[13] & 15];
		persistUUID[32] = bigHexCharMap[(auidValue[14] >>> 4) & 15];
		persistUUID[33] = bigHexCharMap[auidValue[14] & 15];
		persistUUID[34] = bigHexCharMap[(auidValue[15] >>> 4) & 15];
		persistUUID[35] = bigHexCharMap[auidValue[15] & 15];

		return new String(persistUUID);
	}

	public final static AUID fromPersistentForm(
			String persistentForm) {
		
		if (persistentForm == null) return null;
		
    	byte[] auid = new byte[16];
    	char[] urn = persistentForm.toCharArray();
    	
    	auid[0] = Utilities.twoCharsToByte(urn[0], urn[1]);
    	auid[1] = Utilities.twoCharsToByte(urn[2], urn[3]);
    	auid[2] = Utilities.twoCharsToByte(urn[4], urn[5]);
    	auid[3] = Utilities.twoCharsToByte(urn[6], urn[7]);
    	
    	auid[4] = Utilities.twoCharsToByte(urn[9], urn[10]);
    	auid[5] = Utilities.twoCharsToByte(urn[11], urn[12]);
    	
    	auid[6] = Utilities.twoCharsToByte(urn[14], urn[15]);
    	auid[7] = Utilities.twoCharsToByte(urn[16], urn[17]);
    	
    	auid[8] = Utilities.twoCharsToByte(urn[19], urn[20]);
    	auid[9] = Utilities.twoCharsToByte(urn[21], urn[22]);
    	
    	auid[10] = Utilities.twoCharsToByte(urn[24], urn[25]);
    	auid[11] = Utilities.twoCharsToByte(urn[26], urn[27]);    	
    	auid[12] = Utilities.twoCharsToByte(urn[28], urn[29]);
    	auid[13] = Utilities.twoCharsToByte(urn[30], urn[31]);    	
    	auid[14] = Utilities.twoCharsToByte(urn[32], urn[33]);
    	auid[15] = Utilities.twoCharsToByte(urn[34], urn[35]);
    	
    	return new AUIDImpl(auid);

	}
}

