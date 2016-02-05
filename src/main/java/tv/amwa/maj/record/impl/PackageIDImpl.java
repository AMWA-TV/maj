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
 * $Log: PackageIDImpl.java,v $
 * Revision 1.5  2011/07/27 17:25:45  vizigoth
 * Improved handling of the endianess of material number parts of package IDs.
 *
 * Revision 1.4  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/20 17:41:19  vizigoth
 * Fixed up all meta tests to the point where they all pass.
 *
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.10  2010/12/15 18:55:13  vizigoth
 * Moved character to byte parsing methods into common utility functions.
 *
 * Revision 1.9  2010/11/08 15:26:02  vizigoth
 * Update to use dictionary version number in UMID headers that matches that used in SMPTE D-Cinema specs.
 *
 * Revision 1.8  2010/06/16 14:54:51  vizigoth
 * Fix to the string serialization of package IDs so that universal labels are spotted and swapped as appropriate, matching reference implementation behaviour.
 *
 * Revision 1.7  2010/04/16 15:26:41  vizigoth
 * Not specified instance number type fixed to have an all zero instance number.
 *
 * Revision 1.6  2010/04/13 10:10:14  vizigoth
 * Realised no need to swap UL bytes when reading package IDs from little endian files.
 *
 * Revision 1.5  2010/04/13 07:10:57  vizigoth
 * Added support for reading UMIDs from little endian byte streams.
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
 * Revision 1.1  2009/03/30 09:05:03  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/03/07 08:08:12  vizigoth
 * Edited comments to release standard.
 *
 * Revision 1.3  2008/01/14 20:44:04  vizigoth
 * Renamed null/nil mob id to zero mob id. Fits well with isZero() method.
 *
 * Revision 1.2  2007/11/15 12:52:48  vizigoth
 * Edits to ensure source can make rough and ready javadoc.
 *
 * Revision 1.1  2007/11/13 22:14:41  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record.impl;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import tv.amwa.maj.enumeration.MaterialType;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.GenerationMethodNotSupportedException;
import tv.amwa.maj.exception.InstanceOverflowException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.InstanceNumberGeneration;
import tv.amwa.maj.record.MaterialNumberGeneration;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.util.Utilities;

/** 
 * <p>Implementation of a 32&nbsp;byte package id unique identifier that can hold a SMPTE UMID, as specified in
 * SMPTE&nbsp;330M. Material object identifiers represented by this class are 32-byte - yes, <em>byte</em> - 
 * numbers that can be generated at source with a high degree of likelihood that two independently generated 
 * identifiers will not clash. For most systems, package ids are and should be treated as dumb numbers whose 
 * bit pattern structure is of no significance. However, for the purposes of id generation and consistent 
 * representation between systems, a package id can be viewed 
 * as consisting of:</p>
 * 
 * <ul>
 *  <li>a {@linkplain #getUniversalLabel() universal label} defining the representation of the identifier;</li>
 *  <li>a {@linkplain #getLength() length} value;</li> 
 *  <li>a {@linkplain #getMaterial() material number};</li>
 *  <li>an {@linkplain #getInstanceHigh() instance number} representing the version of the material.</li>
 * </ul>
 * 
 * <p>One common form of package id is the SMPTE Unique Material Identifier (UMID), as defined in SMPTE
 * S330M-2004. A number of helper methods are provided that allow the package id to be created and managed
 * as a UMID, including a factory method 
 * ({@link #umidFactory(MaterialType, MaterialNumberGeneration, InstanceNumberGeneration, byte[]) umidFactory()}) and
 * the ability to generate the next UMID instance number for an existing piece of material 
 * ({@link #nextInstance()}). To decrease the likelihood of two UMIDs being the same within a
 * network of systems, the adoption of the same material number generation and instance number
 * generation schemes is advisable.</p>
 * 
 * <p>A package id can be represented as a string in four sections, consisting of the universal label, followed 
 * by the length, then instance number and finally the material number. It is an informal convention that the 
 * string representation of a UMID, which is generated by a call to {@link #toString()}, starts with
 * "<code>urn:x-umid:</code>". For example:</p>
 * 
 * <p><center><code>urn:x-umid:060a2b340101010101010f13-13-0098ef-4dfc94b90124509571af322ec0a801ba</code></center></p>
 * 
 * <p>The value above is structured according to the SMPTE UMID specification as follows:</p>
 * 
 * <p><center><code>urn:x-umid:</code>&lt;<em>label</em>&gt;<code>-</code>&lt;<em>length</em>&gt;<code>-</code>&lt;<em>instance number</em>&gt;<code>-</code>&lt;<em>material number</em>&gt;</center></p>
 * 
 * <p>This class does not represent SMPTE extended UMIDs, which are 64&nbsp;bytes in length.</p>
 * 
 * <p>Package ids can be used as embeddable identifiers for entities that represent material. The internal
 * representation of a package id is a 32&nbsp;byte array, which can be accessed with the {@link #getPackageIDValue()} method
 * and set using the {@link #setPackageIDValue(byte[])} method. A database column
 * defined to hold a package id may be defined as follows:</p>
 * 
 * <pre>
 *     `PackageID` binary(32) NOT NULL
 * </pre>
 *
 * <p>All methods assume network byte order.</p>
 * 
 * <p><em>Package ids</em> were previously known as <em>package ids</em>  and may still be referred to as
 * such in AAF-specific documentation.</p>
 *
 *
 * 
 * @see AUIDImpl
 * @see tv.amwa.maj.industry.TypeDefinitions#PackageIDType
 * @see tv.amwa.maj.misctype.PackageIDType
 */
public final class PackageIDImpl 
	implements PackageID,
		Serializable,
		XMLSerializable,
		Cloneable {

	/**  */
	private static final long serialVersionUID = 8871509282509864625L;

	/**
	 * <p>Returns a base universal label that identifies a package id as compatible with the SMPTE
	 * Unique Material Identifier (UMID) specification, as documented in SMPTE&nbsp;330M. This
	 * default label has:</p>
	 * 
	 * <ul>
	 *  <li>a {@linkplain MaterialType material type} of {@linkplain MaterialType#NotIdentified
	 *  not identified}, which is stored as the 11th byte of the label;</li>
	 *  <li>a {@linkplain MaterialNumberGeneration material number generation type} set to 
	 *  {@linkplain MaterialNumberGeneration#NotDefined not defined}, which is stored as the
	 *  upper nibble of the 12th byte of the label;</li>
	 *  <li>an {@linkplain InstanceNumberGeneration instance number generation type} set to 
	 *  {@linkplain InstanceNumberGeneration#NotDefined not defined}, which is stored as the
	 *  lower nibble of the 12th byte of the label.</li>
	 * </ul>
	 *
	 * @return A universal label that identifies a package id as a SMPTE UMID.
	 * 
	 * @see #getUniversalLabel()
	 * @see #setUniversalLabel(byte[])
	 */
	public final static byte[] getBaseUniversalLabel() {
		return BaseUniversalLabel.clone();
	}
	
	/** <p>The zero package id is used as a flag in a number of places to indicate a special case, such
	 * as in {@linkplain tv.amwa.maj.model.SourceClip source clips}, so safe static access to 
	 * his special value is provided.</p> */
	private final static PackageIDImpl zeroPackageID = new PackageIDImpl(new byte[] {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
	});

	/**
	 * <p>Returns a copy of the zero package id, for which every component byte of the package id is set to&nbsp;0. 
	 * The zero package id is used as a flag in certain properties, such as to indicate that a
	 * {@linkplain tv.amwa.maj.model.SourceClip source clip} is the original source.</p>
	 *
	 * @return Copy of the zero package id.
	 * 
	 * @see #isZero()
	 * @see AUIDImpl#isNil()
	 * @see tv.amwa.maj.union.impl.SourceReferenceValueImpl#isOriginalSource()
	 */
	public final static PackageID getZeroPackageID() {
		
		return zeroPackageID.clone();
	}
	
	/** <p>Template character array to use when formatting UMID values as URN strings.</p> */
	private final static char[] umidTemplate = new char[] {
		'u', 'r', 'n', ':', 's', 'm', 'p', 't', 'e', ':', 'u', 'm', 'i', 'd', ':',
		'*', '*', '*', '*', '*', '*', '*', '*', '.', 
		'*', '*', '*', '*', '*', '*', '*', '*', '.', 
		'*', '*', '*', '*', '*', '*', '*', '*', '.', 
		'*', '*', '*', '*', '*', '*', '*', '*', '.', 
		'*', '*', '*', '*', '*', '*', '*', '*', '.', 
		'*', '*', '*', '*', '*', '*', '*', '*', '.', 
		'*', '*', '*', '*', '*', '*', '*', '*', '.', 
		'*', '*', '*', '*', '*', '*', '*', '*'
	};
	
	/** <p>Byte to character value map used for efficient generation of string versions of UMIDs.</p> */
	private static final char[] hexCharMap = new char[] {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};

	/** <p>Byte to character value map used for efficient generation of string versions of UMIDs.</p> */
	private static final char[] bigHexCharMap = new char[] {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	};

	// For persistent package IDs, order is material number, then instance number, then length, then UL
	// This works better with database indexes
	public final static char[] persistTemplate = new char[] {
		'*', '*', '*', '*', '*', '*', '*', '*', '-', // 0 - 8
		'*', '*', '*', '*', '-', '*', '*', '*', '*', '-', // 9 - 18
		'*', '*', '*', '*', '-', '*', '*', '*', '*',  // 19 - 27
		'*', '*', '*', '*', '*', '*', '*', '*', '-', // 28 - 36
		'*', '*', '*', '*', '*', '*', '-', '*', '*', '-', // 37 - 46
		'*', '*', '*', '*', '*', '*', '*', '*', // 47 - 54
		'*', '*', '*', '*', '*', '*', '*', '*', // 55 - 62
		'*', '*', '*', '*', '*', '*', '*', '*'	// 63 - 70	
	};
	
    /** Secure random number generator used to automatically create AUIDs. */ 
    private static SecureRandom randomMaker = null;

	/** Internal representation of the package id value. The value is stored in a <em>canonical</em> form
	 *  in the manor it would be serialised to an MXF File. In other words, the material number is not
	 *  stored as a byte swapped AUID. Values are a sequence of bytes with no endianess. */
	private byte[] packageIDValue;
	
	/**
	 * <p>Factory that manufactures new package ids according to the given SMPTE UMID generation specification.</p>
	 * 
	 * <p>Some of the generation methods documented in appendix&nbsp;A of SMPTE&nbsp;330M are 
	 * difficult to support in Java as the core API does not support all the required features,
	 * such as access to the ethernet address of a system. This additional data can be provided using
	 * the <em>extra data</em> parameter, as described below. Results produced by this factory are
	 * only as good as the extra data provided and so this factory method should be considered as not 
	 * fully compliant with SMPTE&nbsp;330M.</p>
	 * 
	 * <p>The supported material number generation methods are as follows:</p>
	 * 
	 * <ul>
	 *  
	 *  <li>{@link MaterialNumberGeneration#SMPTE} - Creates a material number from the system clock,
	 *  current date, a two byte random number and a 6&nbsp;byte system identifier. This approach is documented in 
	 *  section&nbsp;A.1 of SMPTE&nbsp;330M and should ensure a high degree of identifier uniqueness. The 
	 *  system identifier must be provided as the first 6&nbsp;bytes of the <em>extra data</em> parameter. Java&nbsp;1.5 does not 
	 *  provide a means to extract the ethernet address of the system on which a virtual machine is running 
	 *  but a locally administered value that is a combination of bytes taken from the system's IP address 
	 *  and domain name should be sufficient. Just such a value is provided by calling 
	 *  {@link tv.amwa.maj.util.Utilities#createLocalHostID(int) Utilities.createLocalHostID(6)}.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#UUID_UL} - Generates a material number from the UUID or universal
	 *  label provided as the 16&nbsp;bytes passed as the extra data parameter. The method assumes that the byte 
	 *  reordering documented in section&nbsp;A.2 of SMPTE&nbsp;330M has already taken place. No validation
	 *  of the UUID or universal label is carried out. In fact, this method is implemented here as a 
	 *  <em>dumb</em> pass through of the given value directly to the 16-bytes of the material number of 
	 *  the new package id.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#Masked} - Generates a material number according to the SMPTE
	 *  method and then uses an MD5 function on the value to mask the details, as described in section A.3
	 *  of SMPTE&nbsp;330M. This masking is normally done for security reasons so as to hide details of creation times 
	 *  and systems identification. Once masked, the time and host id values cannot be easily recovered and
	 *  the masking function ensures the same degree of uniqueness as the 
	 *  {@linkplain MaterialNumberGeneration#SMPTE SMPTE method}.</li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#IEEE1394} - Generates a material number according to the IEEE&nbsp;1394
	 *  network method, as described in section A.4 of SMPTE&nbsp;330M. The first 8&nbsp;bytes are generated 
	 *  from the system clock, current date and a random number. The last 8&nbsp;bytes are the device node identifier that
	 *  must be provided as the <em>extra bytes</em> parameter. The device node of the
	 *  system on which the virtual machine is running is not available in the core Java API version&nbsp;1.5. A locally
	 *  derived host identifier created from the system's IP address and domain name can be generated by calling
	 *  {@link Utilities#createLocalHostID(int) Utilities.createLocalHostID(8)}.</p>  
	 *  </li>
	 *  
	 *  <li>{@link MaterialNumberGeneration#NotDefined} - A pseudo random number is generated for the
	 *  128-bits of data representing the material number, using the {@link java.security.SecureRandom}
	 *  class.</li>
	 *  
	 * </ul>
	 * 
	 * <p>The supported instance generation methods are documented in more detail in {@link #nextInstance()},
	 * with the instance number initialized as follows:</p>
	 * 
	 * <ul>
	 *  <li>{@link InstanceNumberGeneration#LocalRegistration} - All three instance bytes are set to
	 *  zero.</li>
	 *  <li>{@link  InstanceNumberGeneration#CopyAndPseudoRandom16Bit} - The instance high byte is
	 *  set to 0 and the other two bytes are set to random values.</li>
	 *  <li>{@link InstanceNumberGeneration#PseudoRandom24Bit} - The instance high, middle and low 
	 *  values are set using 24 randomly generated bits.</li>
	 *  <li>{@link InstanceNumberGeneration#LiveStream} - Not supported and throws a 
	 *  {@link GenerationMethodNotSupportedException}.</li>
	 *  <li>{@link InstanceNumberGeneration#NotDefined} - Same as PseudoRandom24Bit.</li>
	 * </ul>
	 * 
	 * <p>The chosen type of instance number generated will define the behaviour of subsequent
	 * calls to {@link #nextInstance()} for the new package id.</p>
	 * 
	 * @param materialType Type of material represented by the new package id.
	 * @param materialNumberGeneration Method to use to generate the material number of the package id.
	 * @param instanceNumberGeneration Method to use to generate the first and next instance
	 * numbers for the package id.
	 * @param extraData Additional data required by the material number generation method.
	 * @return Newly manufactured package id, which is a SMPTE UMID according to the given specification.
	 * 
	 * @throws GenerationMethodNotSupportedException The given material or instance number generation
	 * method is not supported.
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and all are
	 * required.
	 */
	public static final PackageIDImpl umidFactory(
			MaterialType materialType,
			MaterialNumberGeneration materialNumberGeneration,
			InstanceNumberGeneration instanceNumberGeneration,
			byte[] extraData)
		throws GenerationMethodNotSupportedException, NullPointerException {
		
		if (materialType == null)
			throw new NullPointerException("Cannot create a package id from a null material type.");
		if (materialNumberGeneration == null)
			throw new NullPointerException("Cannot create a package id from a null material number generation method.");
		if (instanceNumberGeneration == null)
			throw new NullPointerException("Cannot create a package id from a null instance number generation method.");
		
		if (randomMaker == null)
			randomMaker = Utilities.seedRandomMaker();
		
		PackageIDImpl packageID = 
			new PackageIDImpl(BaseUniversalLabel, (byte) 0x13, (byte) 0, (byte) 0, (byte) 0, new AUIDImpl(new byte[16]));
		
		byte[] label = packageID.getUniversalLabel();
		label[10] = materialType.getMaterialTypeCode();
		label[11] = (byte) ((materialNumberGeneration.getMethodCode() << 4) | 
								(instanceNumberGeneration.getMethodCode()));
		packageID.setUniversalLabel(label);
		
		AUIDImpl material = null;
		
		switch (materialNumberGeneration) {
	
		case SMPTE:
			material = materialGenerationSMPTE(extraData);
			packageID.setMaterial(material);
			break;
		case UUID_UL:
			material = materialGenerationUUID_UL(extraData);
			packageID.setMaterial(material);
			break;
		case Masked:
			material = materialGenerationMasked(extraData);
			packageID.setMaterial(material);
			break;
		case IEEE1394:
			material = materialGenerationIEEE1394(extraData);
			packageID.setMaterial(material);
			break;
		case NotDefined: // Entirely random material number is OK.
			material = materialGenerationRandom();
			packageID.setMaterial(material);
			break;
		default:
			break;
		}
		
		switch(instanceNumberGeneration) {
		
		case CopyAndPseudoRandom16Bit:
			// packageID.setInstanceHigh((byte) 0); removed as initialized to zero anyway

			byte[] twoRandomBytes = new byte[2];
			randomMaker.nextBytes(twoRandomBytes);
			packageID.setInstanceMid(twoRandomBytes[0]);
			packageID.setInstanceLow(twoRandomBytes[1]);
			break;
		case LiveStream:
			throw new GenerationMethodNotSupportedException("Generation of instance numbers using live stream data is not supported by this method.");

		case PseudoRandom24Bit:
			byte[] threeRandomBytes = new byte[3];
			randomMaker.nextBytes(threeRandomBytes);
			packageID.setInstanceHigh(threeRandomBytes[0]);
			packageID.setInstanceMid(threeRandomBytes[1]);
			packageID.setInstanceLow(threeRandomBytes[2]);
		case NotDefined:
		case LocalRegistration:
		default:			
			break; // Stays at 0
		}
		
		return packageID;
	}
	
    /**
     * <p>Generates a pseudo random number is generated for the 128-bits of data representing the 
     * material number, using the {@link java.security.SecureRandom} class.</p>
     *
     * @return Generated material number as an AUID.
     */
    static final AUIDImpl materialGenerationRandom() {

    	byte[] randombytes = new byte[16];
    	randomMaker.nextBytes(randombytes);
		return new AUIDImpl(randombytes);
	}

	/**
	 * <p>Generates a material number according to the IEEE&nbsp;1394
	 * network method, as described in section&nbsp;A.4 of SMPTE&nbsp;S330M. The first 8-bytes are generated 
	 * from the system clock, date and a random number. The last 8 bytes are the device node identifier that
	 * must be provided as the extraBytes parameter as an 8 byte array value. The device node is of the
	 * system on which the virtual machine is running is not available in the core Java API. A locally
	 * derived host identifier created from the systems IP address and domain name can be generated by calling
	 * {@link Utilities#createLocalHostID(int) Utilities.createLocalHostID(8)}.</p>
	 * 
	 * <p>Note that generating these hell for leather will generate classhes ... you cannot get enough
	 * randomness in the recipe. Adding a Thread.sleep(1) will solve the problem, or even Thread.sleep(0, 50)
	 * should do it.</p>
	 *
	 * @param extraData Device node ID, which is the last 8 bytes of the generated material number.
	 * @return Generated material number as an AUID.
	 */
	static final AUIDImpl materialGenerationIEEE1394(byte[] extraData) {

		byte[] materialNumber = new byte[16];
		
		Calendar rightNow = Calendar.getInstance();
		byte[] timeStampBits = Utilities.compute7ByteTimeStamp(rightNow);
		System.arraycopy(timeStampBits, 0, materialNumber, 0, 7);

		byte[] oneRandomByte = new byte[1];
		randomMaker.nextBytes(oneRandomByte);
//		System.out.print(oneRandomByte[0] + "/");
		// Set bit 6 (0-based) to 1 for random and bit 8 to 1 to indicate modified julian date.
		materialNumber[7] = (byte) (((((int) oneRandomByte[0]) & 63) << 2) | 3);
//		System.out.print(Integer.toHexString(materialNumber[7]) + " ");
		
		extraData = Utilities.checkBytes(extraData, 8);
		System.arraycopy(extraData, 0, materialNumber, 8, 8);
		
		return new AUIDImpl(materialNumber);
	}

	private final static byte[] localMaskingData = {
		(byte) 'M', (byte) 'A', (byte) 'J', (byte) 'A', (byte) 'P', (byte) 'I', (byte) '*', (byte) '*', 
		(byte) 'l', (byte) 'o', (byte) 'c', (byte) 'a', (byte) 'l', (byte) '*', (byte) '*', (byte) '*',
		(byte) 'r', (byte) 'e', (byte) 'f', (byte) 'e', (byte) 'r', (byte) 'e', (byte) 'n', (byte) 'c', 
		(byte) 'e', (byte) '*', (byte) '*', (byte) '*', (byte) '*', (byte) '*', (byte) '*', (byte) '*',
		(byte) 'm', (byte) 'a', (byte) 's', (byte) 'k', (byte) 'i', (byte) 'n', (byte) 'g', (byte) '*', 
		(byte) 'd', (byte) 'a', (byte) 't', (byte) 'a', (byte) '*', (byte) '*', (byte) '*', (byte) '*',
		(byte) 't', (byte) 'h', (byte) 'a', (byte) 't', (byte) '*', (byte) '*', (byte) '*', (byte) '*', 
		(byte) 'i', (byte) 's', (byte) '*', (byte) '*', (byte) '*', (byte) '*', (byte) '*', (byte) '*',
		(byte) 'b', (byte) 'e', (byte) 't', (byte) 't', (byte) 'e', (byte) 'r', (byte) '*', (byte) '*', 
		(byte) 't', (byte) 'h', (byte) 'a', (byte) 'n', (byte) '*', (byte) '*', (byte) '*', (byte) '*',
		(byte) 'u', (byte) 's', (byte) 'i', (byte) 'n', (byte) 'g', (byte) '*', (byte) '*', (byte) '*', 
		(byte) 'j', (byte) 'u', (byte) 's', (byte) 't', (byte) '*', (byte) '*', (byte) '*', (byte) '*',
		(byte) 'a', (byte) '*', (byte) '*', (byte) '*', (byte) '*', (byte) '*', (byte) '*', (byte) '*', 
		(byte) 'l', (byte) 'o', (byte) 'a', (byte) 'd', (byte) '*', (byte) '*', (byte) '*', (byte) '*',
		(byte) 'o', (byte) 'f', (byte) '*', (byte) '*', (byte) '*', (byte) '*', (byte) '*', (byte) '*', 
		(byte) 'z', (byte) 'e', (byte) 'r', (byte) 'o', (byte) 's', (byte) '*', (byte) '*', (byte) '*'
	};
	
	/**
	 * <p>Generates a material number according to the SMPTE method and then uses an MD5 function on the 
	 * value to mask the details, as described in section&nbsp;A.3 in SMPTE&nbsp;330M. This if normally done 
	 * for security reasons to hide details of creation times and systems identification. Once masked, the 
	 * time and host ID values cannot be easily recovered and the masking function ensures the same degree 
	 * of uniqueness is ensured as for the SMPTE method.</p>
	 *
	 * @param extraData Device node identifier in 6 bytes.
	 * @return Generated material number as an AUID.
	 */
	static final AUIDImpl materialGenerationMasked(byte[] extraData) {

		byte[] unmasked = materialGenerationSMPTE(extraData).getAUIDValue();
	   	MessageDigest md = null;
    	try {
    		md = MessageDigest.getInstance("MD5");
    	}
    	catch (NoSuchAlgorithmException nsae) {
    		System.err.println("Unexpected: MD5 algorithm not available in method tv.amwa.maj.entity.packageID.materialGenerationMasked(). Please investigate.");
    		return new AUIDImpl(unmasked);
    	}

    	md.update(unmasked);
    	md.update(localMaskingData);
    	byte[] md5digest = md.digest();
    	
    	// Set the UUID to look like a random generation
    	md5digest[7] = (byte) ((md5digest[7] & 0x0f) | 0x40);
    	// Set the UUID to be a RFC 4122 variant
    	md5digest[8] = (byte) ((md5digest[8] & 0x3f) | 0x80);
    	
    	return new AUIDImpl(md5digest);
	}

	/**
	 * <p>Generates a material ID from the UUID or universal
	 *  label provided as the 16-bytes passed as the extraData parameter. The method assumes that the byte 
	 *  reordering documented in section&nbsp;A.2 of SMPTE&nbsp;330M has already taken place. No validation
	 *  of the UUID or universal label is carried out. In fact, this method is implemented here as a 
	 *  <em>dumb</em> pass through of the given value to the material number.</p>
	 *
	 * @param extraData Data to pass through to be the value of the material number.
	 * @return Generated material number as an AUID.
	 */
	static final AUIDImpl materialGenerationUUID_UL(byte[] extraData) {
		
		return new AUIDImpl(extraData);
	}

	/**
	 * <p>Creates a material number from the system clock, current date, two byte random number and a 
	 * system identifier. This approach is documented in section&nbsp;A.1 of SMPTE&nbsp;330M and should ensure 
	 * a high degree of uniqueness. The system identifier must be provided as the first 6 bytes of the 
	 * extraBytes parameter. Java does not provide a means to extract the ethernet address of the system 
	 * on which a virtual machine is running but a locally administered value that is a combination of 
	 * bytes taken from the system's IP address and domain name may be sufficient. Just such a value is 
	 * provided by calling {@link Utilities#createLocalHostID(int) Utilities.createLocalHostID(6)}.</p>
	 *
	 * @param extraData Device node identifier in 6 bytes.
	 * @return Generated material number as an AUID.
	 */
	static final AUIDImpl materialGenerationSMPTE(byte[] extraData) {

		byte[] materialNumber = new byte[16];
		
		Calendar rightNow = Calendar.getInstance();
		byte[] timeStampBits = Utilities.compute7ByteTimeStamp(rightNow);
		System.arraycopy(timeStampBits, 0, materialNumber, 0, 7);
		
		int timezoneOffsetInMillis = rightNow.get(Calendar.ZONE_OFFSET) + rightNow.get(Calendar.DST_OFFSET);
		byte timezoneCode = Utilities.timezoneCode(timezoneOffsetInMillis);
		timezoneCode = (byte) ((((int) timezoneCode) << 2) | 1);
		materialNumber[7] = timezoneCode;
		
		byte[] twoRandomBytes = new byte[2];
		randomMaker.nextBytes(twoRandomBytes);
		materialNumber[8] = twoRandomBytes[0];
		materialNumber[9] = twoRandomBytes[1];
		
		extraData = Utilities.checkBytes(extraData, 6);
		System.arraycopy(extraData, 0, materialNumber, 10, 6);
		
		return new AUIDImpl(materialNumber);
	}
	
	/**
	 * <p>Create a new package id from the specified parameters. The label and material values are copied to
	 * ensure that subsequent external changes do not effect the newly created package id.</p>
	 * 
	 * <p>A default universal label to set the package id to be a SMPTE UMID is available by calling 
	 * {@link #getBaseUniversalLabel()}.</p>
	 * 
	 * <p>Note that this method does not generate a new package id, it creates one using the given parameters. 
	 * To generate values, use {@link #PackageID()} or the
	 * {@linkplain #umidFactory(MaterialType, MaterialNumberGeneration, InstanceNumberGeneration, byte[]) 
	 * UMID factory method}.</p>
	 * 
	 * @param label Universal label component of the package id.
	 * @param length Length of the following data.
	 * @param instanceHigh Most significant part of the instance number component of the package id.
	 * @param instanceMid Second most significant part the instance number component of the package id.
	 * @param instanceLow Least significant part of the instance number component of the package id.
	 * @param material Unique identifier for the identified material, the material number component of the
	 * package id.
	 */
	public PackageIDImpl(
			byte[] label, 
			@UInt8 byte length, 
			@UInt8 byte instanceHigh, 
			@UInt8 byte instanceMid, 
			@UInt8 byte instanceLow, 
			AUID material) 
		throws NullPointerException {

		packageIDValue = new byte[32];
		setUniversalLabel(label);
		setLength(length);
		setInstanceLow(instanceLow);
		setInstanceMid(instanceMid);
		setInstanceHigh(instanceHigh);
		setMaterial(material);
	}

	/**
	 * <p>Creates a new package id from an array of bytes. The array should be 32 bytes in length and
	 * prepared to be considered as a package id. If the array is too short or too long, it will
	 * be padded with 0s or truncated to make it 32 bytes. The passed array value is cloned to avoid
	 * external changes to the array affecting this value.</p>
	 *
	 * @param packageID Byte array to use to create a new package id value.
	 * 
	 * @throws NullPointerException The given byte array is <code>null</code>.
	 * 
	 * @see #setPackageIDValue(byte[])
	 * @see #getPackageIDValue()
	 */

	public PackageIDImpl(
			byte[] packageID) 
		throws NullPointerException {
		
		setPackageIDValue(packageID);
	}

	/**
	 * <p>Generate a new package id using a secure random number generator. The value created is a
	 * SMPTE UMID with the following properties:</p>
	 * 
	 * <ul>
	 *  <li>The material type is set to {@linkplain  tv.amwa.maj.enumeration.MaterialType#NotIdentified
	 *  not identified};</li>
	 *  <li>The material number generation method is set to {@linkplain MaterialNumberGeneration#NotDefined
	 *  not defined} and the material number is generated using a 128&nbsp;bit secure random number generator;</li>
	 *  <li>The instance number generation method is set to {@linkplain InstanceNumberGeneration#PseudoRandom24Bit
	 *  pseudo-random 24 bit} and the instance number is generated using a 24&nbsp;bit secure random number
	 *  generator.</li>
	 * </ul>
	 * 
	 * <p>This method is provided as a quick way to generate a package id. Statistically, it is highly unlikely
	 * that package ids generated this way will clash with one another . For more control over generating globally unique
	 * package ids, use the {@linkplain #umidFactory(MaterialType, MaterialNumberGeneration, InstanceNumberGeneration, byte[])
	 * UMID factory method}.</p>
	 *
	 */
	public PackageIDImpl() {
		
		packageIDValue = new byte[32];
		if (randomMaker == null)
			randomMaker = Utilities.seedRandomMaker();

		byte[] randomInstanceLabel = BaseUniversalLabel.clone();
		randomInstanceLabel[11] = 
		 	(byte) (MaterialNumberGeneration.NotDefined.getMethodCode() << 4 | 
			 		InstanceNumberGeneration.PseudoRandom24Bit.getMethodCode());
		setUniversalLabel(randomInstanceLabel);

		setLength((byte) 0x13);
		
		byte[] dumbNumber = new byte[19];
		randomMaker.nextBytes(dumbNumber);
		System.arraycopy(dumbNumber, 0, packageIDValue, 13, 19);
	}
	
	/**
	 * <p>Returns a copy of all 32&nbsp;bytes representing the value of this package id as a byte array. The internal 
	 * representation of a package id in the MAJ API is as a 32-byte array.</p>
	 * 
	 * @return Copy of the 32-byte value of this package id.
	 */
	public final byte[] getPackageIDValue() {
		
		return packageIDValue.clone();
	}
	
	/**
	 * <p>Sets the value of this package id using the given byte array, which should contain 32&nbsp;bytes
	 * of data. If the array is smaller than required, it will be padded with zeros. If the array is
	 * larger than required, it will be truncated. The given array is cloned so that subsequent changes
	 * do not affect the internal representation within this package id.</p>
	 *
	 * @param packageID Data to use to set the value of this package id.
	 * 
	 * @throws NullPointerException The given package id data is <code>null</code>.
	 * 
	 * @see #PackageID(byte[])
	 */
	public final void setPackageIDValue(
			byte[] packageID) 
		throws NullPointerException {
		
		if (packageID == null)
			throw new NullPointerException("Cannot set the value of a PackageID using a null value.");
		
		// Data is always cloned in checkBytes method.
		packageIDValue = Utilities.checkBytes(packageID, 32);
		
		// Detect unswapped universal label
//		if ((packageIDValue[16] == 0x06) && (packageIDValue[17] == 0x0e) && 
//				(packageIDValue[18] == 0x2b) && (packageIDValue[19] == 0x34) &&
//				((packageIDValue[24] & 128) == 0)) {
//			
//			byte[] swappedBytes = new byte[8];
//			System.arraycopy(packageIDValue, 16, swappedBytes, 0, 8);
//			System.arraycopy(packageIDValue, 24, packageIDValue, 16, 8);
//			System.arraycopy(swappedBytes, 0, packageIDValue, 24, 8);
//		}
	}

	/**
	 * @see #getBaseUniversalLabel()
	 */
	public final byte[] getUniversalLabel() {

		byte[] label = new byte[12];
		System.arraycopy(packageIDValue, 0, label, 0, 12);
		return label;
	}

	/**
	 * @see #getBaseUniversalLabel()
	 */
	public final void setUniversalLabel(
			byte[] label)
		throws NullPointerException {

		if (label == null)
			throw new NullPointerException("Cannot set the universal label of a package id from a null value.");
		
		label = Utilities.checkBytes(label, 12);
		System.arraycopy(label, 0, packageIDValue, 0, 12);
	}
		
	public final @UInt8 byte getLength() {
		
		return packageIDValue[12];
	}

	public final void setLength(
			@UInt8 byte length) {
		
		packageIDValue[12] = length;
	}

	public final @UInt32 int getInstanceNumber() {
		
		return ((((int) packageIDValue[13]) & 255) << 16) &
				((((int) packageIDValue[14]) & 255) << 8) &
				(((int) packageIDValue[15]) & 255); 
	}

	public final @UInt8 byte getInstanceHigh() {

		return packageIDValue[13];
	}

	public final void setInstanceHigh(
			@UInt8 byte instanceHigh) {

		packageIDValue[13] = instanceHigh;
	}

	public final @UInt8 byte getInstanceMid() {
		 
		return packageIDValue[14];
	}

	public final void setInstanceMid(
			@UInt8 byte instanceMid) {

		packageIDValue[14] = instanceMid;
	}

	public final @UInt8 byte getInstanceLow() {

		return packageIDValue[15];
	}

	public final void setInstanceLow(
			@UInt8 byte instanceLow) {

		packageIDValue[15] = instanceLow;
	}

	public final AUID getMaterial() {
		
		byte[] material = new byte[16];
		System.arraycopy(packageIDValue, 16, material, 0, 16);
		return new AUIDImpl(material);
	}

	public final void setMaterial(
			AUID material) 
		throws NullPointerException {
		
		if (material == null)
			throw new NullPointerException("Cannot set the material number from a null AUID.");

		System.arraycopy(material.getAUIDValue(), 0, packageIDValue, 16, 16);
	}

	public final boolean isZero() {
		
		for ( byte element : packageIDValue )
			if (element != 0) return false;
		
		return true;
	}

	public final int hashCode() {
		
		int orrdCode = 0;
		
		for ( int x = 0 ; x < 32 ; x += 8 ) {
			orrdCode = orrdCode << 8;
			orrdCode = orrdCode ^
				(packageIDValue[x]     ^ packageIDValue[x + 1] ^ packageIDValue[x + 2] ^ packageIDValue[x + 3] ^
				 packageIDValue[x + 4] ^ packageIDValue[x + 5] ^ packageIDValue[x + 6] ^ packageIDValue[x + 7]);
		}

		return orrdCode;
	}

	/** 
	 * <p>Formats the value of this package id as a URN-style UMID string, starting with 
	 * "<code>urn:smpte:umid:</code>". For example:</p>
	 *
	 * <p><center><code>urn:smpte:umid:060a2b34.01010101.01010f13.1300b347.53b933d9.18245095.ca82322e.c0a801ba</code></center></p>
	 * 
	 * <p>Values from this method can be turned back into package ids using the {@link #parseFactory(String)}
	 * static method. The canonical form of formatted values created by this implementation uses
	 * lower case letters for hexadecimal digits.</p>
	 * 
	 * @return The value of this package id formatted as a URN-style UMID string.
	 * 
	 * @see java.lang.Object#toString()
	 * @see #parseFactory(String)
	 */
	public final String toString() {
		
		char[] umid = umidTemplate.clone();
		
		int umidCount = 15;

		// This code does not match Reg-XML spec. Big Endianess should rule!
//		if (getMaterial().isUniversalLabel()) {
//			for ( int x = 0 ; x < 16 ; x++ ) {
//				umid[umidCount++] = hexCharMap[(packageIDValue[x] >>> 4) & 15];
//				umid[umidCount++] = hexCharMap[packageIDValue[x] & 15];
//				if ((x % 4) == 3) umidCount++;				
//			}
//			for ( int x = 16 ; x < 24 ; x++ ) {
//				umid[umidCount++] = hexCharMap[(packageIDValue[x + 8] >>> 4) & 15];
//				umid[umidCount++] = hexCharMap[packageIDValue[x + 8] & 15];
//				if ((x % 4) == 3) umidCount++;				
//			}
//			for ( int x = 24 ; x < 32 ; x++ ) {
//				umid[umidCount++] = hexCharMap[(packageIDValue[x - 8] >>> 4) & 15];
//				umid[umidCount++] = hexCharMap[packageIDValue[x - 8] & 15];
//				if ((x % 4) == 3) umidCount++;				
//			}
//		}
//		else {
			for ( int x = 0 ; x < 32 ; x++ ) {
				umid[umidCount++] = hexCharMap[(packageIDValue[x] >>> 4) & 15];
				umid[umidCount++] = hexCharMap[packageIDValue[x] & 15];
				if ((x % 4) == 3) umidCount++;
			}
//		}
			
		return new String(umid);
	}

	/**
	 * <p>Parse a package id formatted as a URN-style UMID string and convert it into a newly 
	 * instantiated package id with an equivalent value. The format of the expected value 
	 * is the same as that generated by the {@link #toString()} method, for example:</p>
	 *
	 * <p><center><code>urn:smpte:umid:060a2b34.01010101.01010f13.1300b347.53b933d9.18245095.ca82322e.c0a801ba</code></center></p>
	 *
	 * <p>Hexadecimal digits used to express the UMID value may be a mixture of upper or lower case letters.</p>
	 *
	 * @param packageIdURN Package id value formatted as a URN-style UMID string.
	 * @return Package id created from the given URN-style UMID value.
	 * 
	 * @throws NullPointerException The package id urn argument is <code>null</code>.
	 * @throws ParseException The given URN value is the wrong length, a different kind or
	 * URN, has path separators in the wrong place or contains digits other than hexadecimal digits
	 * where hexadecimal digits are expected.
	 * 
	 * @see #toString()
	 */
	public final static PackageIDImpl parseFactory(
			String packageIdURN) 
		throws NullPointerException,
			ParseException {
		
		if (packageIdURN == null)
			throw new NullPointerException("Cannot create a package ID value from a null string.");
		
		packageIdURN = packageIdURN.trim();
		
		if (packageIdURN.startsWith("urn:x-umid"))
			return parseOldStyleURN(packageIdURN);
		
		if (packageIdURN.length() != umidTemplate.length)
			throw new ParseException("The given string representation contains a different number of characters to that requried for a package id URN.", 0);
		
		if (!(packageIdURN.startsWith("urn:smpte:umid:")))
			throw new ParseException("The given value does not appear to be a URN representing a package identifier.", 0);
		
		char[] urn = packageIdURN.toCharArray();
		byte[] packageId = new byte[32];
		
		// Universal label
		try {
			int urnCount = 15;
			for ( int x = 0 ; x < 32 ; x++ ) {
				packageId[x] = Utilities.twoCharsToByte(urn[urnCount++], urn[urnCount++]);
				if ((x % 4) == 3) urnCount++;
			}
		}
		catch (NumberFormatException nfe) {
			throw new ParseException("Unexpected character when parsing a package ID URN.", 0);
		}
		
		return new PackageIDImpl(packageId);
	}
	
	/**
	 * <p>Support for parsing the older style of SMPTE URNs. For example:</p>
	 * 
	 * <p><center><code>urn:x-umid:060a2b340101010101010f13-13-00b347-53b933d918245095ca82322ec0a801ba</code></center></p>
	 * 
	 * @param packageIdURN Old-style SMPTE UMID.
	 * @return Package ID created from an old style UMID.
	 */
	private final static PackageIDImpl parseOldStyleURN(
			String packageIdURN) 
		throws ParseException {
		
		if (packageIdURN.length() != 78)
			throw new ParseException("An old style package ID URN is not of the correct length (78 characters).", 0);
	
		char[] urn = packageIdURN.toCharArray();
		byte[] packageId = new byte[32];
		
		try {
			int urnCount = 11;
			for ( int x = 0 ; x < 12 ; x++ )
				packageId[x] = Utilities.twoCharsToByte(urn[urnCount++], urn[urnCount++]);
	
			packageId[12] = Utilities.twoCharsToByte(urn[36], urn[37]);
	
			// Instance number
			packageId[13] = Utilities.twoCharsToByte(urn[39], urn[40]);
			packageId[14] = Utilities.twoCharsToByte(urn[41], urn[42]);
			packageId[15] = Utilities.twoCharsToByte(urn[43], urn[44]);
	
			// Material number
			urnCount = 46;
			for ( int x = 16 ; x < 32 ; x++ )
				packageId[x] = Utilities.twoCharsToByte(urn[urnCount++], urn[urnCount++]);
		}
		catch (NumberFormatException nfe) {
			throw new ParseException("Error parsing part of a value for an old-style package identifier.", 0);
		}

		return new PackageIDImpl(packageId);
	}

	public final PackageID clone() {
		
		try {
			PackageIDImpl cloned = (PackageIDImpl) super.clone();
			cloned.setPackageIDValue(packageIDValue);
			return cloned;
		}
		catch (CloneNotSupportedException cnse) {
			cnse.printStackTrace();
			return null;
		}
	}

	public final boolean equals(Object o) {
		
		if (o == null) return false;
		if (!(o instanceof PackageID)) return false;
		
		PackageID testPackageID = 
			(PackageID) o;
		
		if (testPackageID.getLength() != getLength()) return false;
		if (testPackageID.getInstanceHigh() != getInstanceHigh()) return false;
		if (testPackageID.getInstanceMid() != getInstanceMid()) return false;
		if (testPackageID.getInstanceLow() != getInstanceLow()) return false;
		
		if (!(Arrays.equals(testPackageID.getUniversalLabel(), getUniversalLabel()))) return false;
		
		return getMaterial().equals(testPackageID.getMaterial());
	}

	/**
	 * <p>Returns the material number generation method for this package identifier.</p>
	 *
	 * @return Material number generation method for this package identifier.
	 * 
	 * @see tv.amwa.maj.industry.Forge#generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration);
	 * @see tv.amwa.maj.industry.Forge#generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, AUID)
	 * @see tv.amwa.maj.industry.Forge#generatePackageID(MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, byte[])
	 */
	public final MaterialNumberGeneration getMaterialNumberGenerator() {
		
		return MaterialNumberGeneration.generationMethodFromCode((byte) ((((int) packageIDValue[11]) & 255) >>> 4));
	}
	
	public final InstanceNumberGeneration getInstanceGenerator() {
		
		return InstanceNumberGeneration.generationMethodFromCode((byte) (packageIDValue[11] & 15));
	}

	/**
	 * <p>Returns the material type for this package id. This value only makes sense if the package
	 * id is a SMPTE UMID.</p>
	 *
	 * @return Material type for this package id.
	 * 
	 * @see #umidFactory(MaterialType, MaterialNumberGeneration, InstanceNumberGeneration, byte[])
	 */
	public final MaterialType getMaterialType() {
		
		return MaterialType.materialTypeFromCode(packageIDValue[10]);
	}
	
	/**
	 * <p>Returns a new package id with the same material number as this one and a newly created instance
	 * number. The method of generation of the instance number is set as part of the package id's universal
	 * label and can be found by calling {@link #getInstanceGenerator()}.</p>
	 * 
	 * <p>The package ids generated by this method are only unique within the local context and are not thread 
	 * safe where clones exist or safe for use across more than one virtual machine. It is up to a user
	 * of this method to ensure instance uniqueness in their own local context, using techniques such as
	 * transaction management and persistence mapping to a central store.</p>
	 * 
	 * <p>The relationship between the current and new instance number for each type of generator is:</p>
	 * 
	 * <ul>
	 *  <li>{@link InstanceNumberGeneration#LocalRegistration} - The instance high, mid and low bytes 
	 *  are treated as the most significant, middle and least significant bytes of an unsigned 24-bit 
	 *  integer value representing the local registration number of the package id for the given material 
	 *  number. The instance number of the new package id is set to be one greater than current instance 
	 *  number, as returned by {@link #getInstanceNumber()}.</li>
	 *  <li>{@link  InstanceNumberGeneration#CopyAndPseudoRandom16Bit} - The instance high byte is 
	 *  incremented by one to make the instance high byte of the new package id. If the counter exceeds 
	 *  <code>255</code>, an {@link InstanceOverflowException} is thrown, which gives the caller the 
	 *  option of resetting the counter to <code>0</code> or to take their own choice of action. The 
	 *  middle and low instance bytes are set using a random number generator. This implementation
	 *  ensures that any two consecutive current and new instances do not have the same middle and low 
	 *  instance values.</li>
	 *  <li>{@link InstanceNumberGeneration#PseudoRandom24Bit} - The instance high, middle and low 
	 *  values are set using 24 randomly generated bits. The method ensures that any two consecutive
	 *  current and new instance numbers do not have the same values. </li>
	 *  <li>{@link InstanceNumberGeneration#LiveStream} - Not supported by the MAJ API and throws a 
	 *  {@link GenerationMethodNotSupportedException}.</li>
	 *  <li>{@link InstanceNumberGeneration#NotDefined} - Same as {@link InstanceNumberGeneration#PseudoRandom24Bit}.</li>
	 * </ul>
	 * 
	 * @return The next package id instance as defined by the instance generation method for this package id.
	 * 
	 * @throws InstanceOverflowException For methods that increment counters to define the next instance
	 * package id, the counter has overflowed.
	 * @throws GenerationMethodNotSupportedException The instance number generation method is not supported,
	 * which is currently the case for the live stream method.
	 * 
	 * @see #getInstanceGenerator()
	 * @see #getInstanceNumber()
	 * @see #getInstanceHigh()
	 * @see #getInstanceMid()
	 * @see #getInstanceLow()
	 */
	public final synchronized PackageID nextInstance() 
		throws InstanceOverflowException, 
			GenerationMethodNotSupportedException {
		
		PackageID next = clone();

		switch(getInstanceGenerator()) {
		
		// Local registration only works as part of a persistent, transactional architecture.
		case LocalRegistration:
			int currentPlus = (
				((((int) packageIDValue[13]) & 255) << 16) |
				((((int) packageIDValue[14]) & 255) << 8) |
				(((int) packageIDValue[15]) & 255) ) + 1;

			if (currentPlus > 0xffffff)
				throw new InstanceOverflowException(
						"Creating a new instance number using the local registration method causes the 24-bit instance number to overflow.",
						InstanceNumberGeneration.LocalRegistration);

			next.setInstanceHigh((byte) ((currentPlus >>> 16) & 255));
			next.setInstanceMid((byte) ((currentPlus >>> 8) * 255));
			next.setInstanceLow((byte) (currentPlus & 255));
			return next;
			
		case CopyAndPseudoRandom16Bit:
			int currentPlusOne = (((int) packageIDValue[13]) & 255) + 1;
			
			if (currentPlusOne > 255) 
				throw new InstanceOverflowException(
						"Creating a new instance number using a one byte counter causes an instance number overflow. Consider resetting the counter with setInstanceHigh((byte) 0).",
						InstanceNumberGeneration.CopyAndPseudoRandom16Bit);

			next.setInstanceHigh((byte) (currentPlusOne & 255));

			byte[] twoRandomBytes = new byte[2];

			// Ensures different random bytes between two adjacent instances only.
			while ((next.getInstanceMid() == packageIDValue[14]) &&
					(next.getInstanceLow() == packageIDValue[15])) {
					
				randomMaker.nextBytes(twoRandomBytes);
				next.setInstanceMid(twoRandomBytes[0]);
				next.setInstanceLow(twoRandomBytes[1]);
			}
			
			return next;
		
			case LiveStream:
				throw new GenerationMethodNotSupportedException("Generation of instance numbers using live stream data is not supported by this method.");
			
			case PseudoRandom24Bit:
			case NotDefined:
			default:
				byte[] threeRandomBytes = new byte[3];
			
			while ((next.getInstanceHigh() == packageIDValue[13]) &&
					(next.getInstanceMid() == packageIDValue[14]) &&
					(next.getInstanceLow() == packageIDValue[15])) {
				
				// Ensures different random bytes between two adjacent instances only.
				randomMaker.nextBytes(threeRandomBytes);
				next.setInstanceHigh(threeRandomBytes[0]);
				next.setInstanceMid(threeRandomBytes[1]);
				next.setInstanceLow(threeRandomBytes[2]);
			}
			
			return next;
		}
	}

	public final void appendXMLChildren(
			Node parent) {
		
		Document document = parent.getOwnerDocument();
		Text textPackageID = document.createTextNode(toString());
		parent.appendChild(textPackageID);	
	}

	public String getComment() {

		return null;
	}
	
	// TODO documentation on these special methods
	public final static PackageID createFromBuffer(
			ByteBuffer buffer)
		throws NullPointerException,
			EndOfDataException {
		
		if (buffer == null)
			throw new NullPointerException("Cannot create a package id value from a null buffer.");
		
		if (buffer.remaining() < 32)
			throw new EndOfDataException("Not enough bytes remaining in the given buffer to read a package id value.");
		
		if (buffer.order() == ByteOrder.LITTLE_ENDIAN) { // Only happens when reading AAF binary files
			byte[] packageIDBytes = new byte[32];
			buffer.get(packageIDBytes, 0, 16);

			buffer.get(packageIDBytes, 19, 1);
			buffer.get(packageIDBytes, 18, 1);
			buffer.get(packageIDBytes, 17, 1);
			buffer.get(packageIDBytes, 16, 1);
			
			buffer.get(packageIDBytes, 21, 1);
			buffer.get(packageIDBytes, 20, 1);

			buffer.get(packageIDBytes, 23, 1);
			buffer.get(packageIDBytes, 22, 1);

			buffer.get(packageIDBytes, 24, 8);
			
			if ((packageIDBytes[24] == 0x06) && (packageIDBytes[25] == 0x0e) && (packageIDBytes[26] == 0x2b) && (packageIDBytes[27] == 0x34) &&
					((packageIDBytes[16] & 128) == 0)) { // Detected a universal label not yet swapped
			
				byte[] swapBytes = new byte[8];
				System.arraycopy(packageIDBytes, 24, swapBytes, 0, 8);
				System.arraycopy(packageIDBytes, 16, packageIDBytes, 24, 8);
				System.arraycopy(swapBytes, 0, packageIDBytes, 16, 8);
			}

			return new PackageIDImpl(packageIDBytes); 
		}
		
		byte[] packageIdBytes = new byte[buffer.remaining()]; // Could be 64-bit UMID, although not supported by MAJ
		buffer.get(packageIdBytes);
		
		return new PackageIDImpl(packageIdBytes);
	}
	
	public final static long lengthAsBuffer(
			PackageID packageID) 
		throws NullPointerException {
		
		// TODO support for 64-byte UMIDs (?)
		return 32;
	}
	
	public final static void writeToBuffer(
			PackageID packageID,
			ByteBuffer buffer) 
		throws NullPointerException,
			InsufficientSpaceException {
		
		if (packageID == null)
			throw new NullPointerException("Cannot write a null package id to a buffer.");
		if (buffer == null)
			throw new NullPointerException("Cannot write a package id to a null buffer.");
		
		if (buffer.remaining() < 32)
			throw new InsufficientSpaceException("Insufficient space in the given buffer to write a 32-byte identifier.");
		
		if (buffer.order() == ByteOrder.BIG_ENDIAN) {
			if (packageID instanceof PackageIDImpl)
				buffer.put(((PackageIDImpl) packageID).getPackageIDValue());
			else {
				buffer.put(packageID.getUniversalLabel());
				buffer.put(packageID.getLength());
				buffer.put(packageID.getInstanceHigh());
				buffer.put(packageID.getInstanceMid());
				buffer.put(packageID.getInstanceLow());
				buffer.put(packageID.getMaterial().getAUIDValue());
			}
		}
		else { // AAF binary territory
			byte[] packageIDBytes = ((PackageIDImpl) packageID).getPackageIDValue();
			
			buffer.put(packageIDBytes, 0, 16);
			int y = 16;
			
			if ((packageIDBytes[16] == 0x06) && (packageIDBytes[17] == 0x0e) && (packageIDBytes[18] == 0x2b) && (packageIDBytes[19] == 0x34) &&
					((packageIDBytes[24] & 128) == 0)) 
				y = 24;// Detected a universal label - needs swapping
			
			buffer.put(packageIDBytes[y + 3]);
			buffer.put(packageIDBytes[y + 2]);
			buffer.put(packageIDBytes[y + 1]);
			buffer.put(packageIDBytes[y]);
			
			buffer.put(packageIDBytes[y + 5]);
			buffer.put(packageIDBytes[y + 4]);
			
			buffer.put(packageIDBytes[y + 7]);
			buffer.put(packageIDBytes[y + 6]);
			
			if (y == 24) y = 16; else y = 24;
			
			buffer.put(packageIDBytes, y, 8);
		}
	}
	
	public final static void generateEmbeddableORM(
			Node parent,
			String namespace,
			String prefix) {
		
		Element embeddable = XMLBuilder.createChild(parent, namespace, prefix, "embeddable");
		XMLBuilder.setAttribute(embeddable, namespace, prefix, "class", PackageIDImpl.class.getCanonicalName());
		XMLBuilder.setAttribute(embeddable, namespace, prefix, "access", "FIELD");
		
		Element embeddableAttributes = XMLBuilder.createChild(embeddable, namespace, prefix, "attributes");
		
		Element basic = XMLBuilder.createChild(embeddableAttributes, namespace, prefix, "basic");
		XMLBuilder.setAttribute(basic, namespace, prefix, "name", "packageIDValue");
	}
	
	public final static void generateEmbeddedORM(
			Node parent,
			String ownerName,
			String namespace,
			String prefix) {
		
		Element embedded = XMLBuilder.createChild(parent, namespace, prefix, "embedded");
		XMLBuilder.setAttribute(embedded, namespace, prefix, "name", Utilities.lowerFirstLetter(ownerName));
		
		Element attributeOverride = XMLBuilder.createChild(embedded, namespace, prefix, "attribute-override");
		XMLBuilder.setAttribute(attributeOverride, namespace, prefix, "name", "packageIDValue");
		Element column = XMLBuilder.createChild(attributeOverride, namespace, prefix, "column");
		XMLBuilder.setAttribute(column, namespace, prefix, "name", ownerName);
	}
	
	public final static String MYSQL_COLUMN_DEFINITION = 
		"CHAR(71) CHARACTER SET ascii COLLATE ascii_general_ci";

	public final static String toPersistentForm(
			PackageID packageID) {
		
		if (packageID == null) return null;
		
		char[] packageChars = persistTemplate.clone();
		
		byte[] materialBytes = null;
//		if (packageID.getMaterial().isUniversalLabel()) {
//			byte[] toSwap = packageID.getMaterial().getAUIDValue();
//			materialBytes = new byte[16];
//			System.arraycopy(toSwap, 0, materialBytes, 8, 8);
//			System.arraycopy(toSwap, 8, materialBytes, 0, 8);
//		}
//		else
		materialBytes = packageID.getMaterial().getAUIDValue();
		
		packageChars[0] = bigHexCharMap[(materialBytes[0] >>> 4) & 15];
		packageChars[1] = bigHexCharMap[materialBytes[0] & 15];
		packageChars[2] = bigHexCharMap[(materialBytes[1] >>> 4) & 15];
		packageChars[3] = bigHexCharMap[materialBytes[1] & 15];
		packageChars[4] = bigHexCharMap[(materialBytes[2] >>> 4) & 15];
		packageChars[5] = bigHexCharMap[materialBytes[2] & 15];
		packageChars[6] = bigHexCharMap[(materialBytes[3] >>> 4) & 15];
		packageChars[7] = bigHexCharMap[materialBytes[3] & 15];
		
		packageChars[9] = bigHexCharMap[(materialBytes[4] >>> 4) & 15];
		packageChars[10] = bigHexCharMap[materialBytes[4] & 15];
		packageChars[11] = bigHexCharMap[(materialBytes[5] >>> 4) & 15];
		packageChars[12] = bigHexCharMap[materialBytes[5] & 15];

		packageChars[14] = bigHexCharMap[(materialBytes[6] >>> 4) & 15];
		packageChars[15] = bigHexCharMap[materialBytes[6] & 15];
		packageChars[16] = bigHexCharMap[(materialBytes[7] >>> 4) & 15];
		packageChars[17] = bigHexCharMap[materialBytes[7] & 15];

		packageChars[19] = bigHexCharMap[(materialBytes[8] >>> 4) & 15];
		packageChars[20] = bigHexCharMap[materialBytes[8] & 15];
		packageChars[21] = bigHexCharMap[(materialBytes[9] >>> 4) & 15];
		packageChars[22] = bigHexCharMap[materialBytes[9] & 15];
		
		packageChars[24] = bigHexCharMap[(materialBytes[10] >>> 4) & 15];
		packageChars[25] = bigHexCharMap[materialBytes[10] & 15];
		packageChars[26] = bigHexCharMap[(materialBytes[11] >>> 4) & 15];
		packageChars[27] = bigHexCharMap[materialBytes[11] & 15];
		packageChars[28] = bigHexCharMap[(materialBytes[12] >>> 4) & 15];
		packageChars[29] = bigHexCharMap[materialBytes[12] & 15];
		packageChars[30] = bigHexCharMap[(materialBytes[13] >>> 4) & 15];
		packageChars[31] = bigHexCharMap[materialBytes[13] & 15];
		packageChars[32] = bigHexCharMap[(materialBytes[14] >>> 4) & 15];
		packageChars[33] = bigHexCharMap[materialBytes[14] & 15];
		packageChars[34] = bigHexCharMap[(materialBytes[15] >>> 4) & 15];
		packageChars[35] = bigHexCharMap[materialBytes[15] & 15];

		byte instanceHigh = packageID.getInstanceHigh();
		packageChars[37] = bigHexCharMap[(instanceHigh >>> 4) & 15];
		packageChars[38] = bigHexCharMap[instanceHigh & 15];
		
		byte instanceMid = packageID.getInstanceMid();
		packageChars[39] = bigHexCharMap[(instanceMid >>> 4) & 15];
		packageChars[40] = bigHexCharMap[instanceMid & 15];
		
		byte instanceLow = packageID.getInstanceLow();
		packageChars[41] = bigHexCharMap[(instanceLow >>> 4) & 15];
		packageChars[42] = bigHexCharMap[instanceLow & 15];
		
		byte length = packageID.getLength();
		packageChars[44] = bigHexCharMap[(length >>> 4) & 15];
		packageChars[45] = bigHexCharMap[length & 15];
		
		byte[] ul = packageID.getUniversalLabel();
		int y = 47;
		for ( int x = 0 ; x < ul.length ; x++ ) {
			packageChars[y++] = bigHexCharMap[(ul[x] >>> 4) & 15];
			packageChars[y++] = bigHexCharMap[ul[x] & 15];
		}
		
		return new String(packageChars);
	}

	public final static PackageID fromPersistentForm(
			String packageID) {
		
		if (packageID == null) return null;
		
		byte[] packageBytes = new byte[32];
		char[] packageChars = packageID.toCharArray();

//		boolean byteSwapping = (Utilities.byteFromCharacter(packageChars[19]) < 8);
//		int y = (byteSwapping) ? 24 : 16;
		int y = 16;
		
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[0], packageChars[1]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[2], packageChars[3]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[4], packageChars[5]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[6], packageChars[7]);

		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[9], packageChars[10]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[11], packageChars[12]);

		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[14], packageChars[15]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[16], packageChars[17]);

//		y = (byteSwapping) ? 16 : 24;
		y = 24;
		
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[19], packageChars[20]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[21], packageChars[22]);
		
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[24], packageChars[25]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[26], packageChars[27]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[28], packageChars[29]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[30], packageChars[31]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[32], packageChars[33]);
		packageBytes[y++] = Utilities.twoCharsToByte(packageChars[34], packageChars[35]);

		packageBytes[13] = Utilities.twoCharsToByte(packageChars[37], packageChars[38]);
		packageBytes[14] = Utilities.twoCharsToByte(packageChars[39], packageChars[40]);
		packageBytes[15] = Utilities.twoCharsToByte(packageChars[41], packageChars[42]);
		
		packageBytes[12] = Utilities.twoCharsToByte(packageChars[44], packageChars[45]);
		
		y = 47;
		for ( int x = 0 ; x < 12 ; x++ ) {
			packageBytes[x] = Utilities.twoCharsToByte(packageChars[y++], packageChars[y++]);
		}
		
		return new PackageIDImpl(packageBytes);
	}
}

