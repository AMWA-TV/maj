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
 * $Log: UserDataModeType.java,v $
 * Revision 1.7  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.4  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/08 17:01:50  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:54  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Represents the kind of the 4&nbsp;bits of channel status that is used for
 * user data related to audio essence stored in the AES/EBU audio file format and described by a 
 * {@linkplain tv.amwa.maj.model.AES3PCMDescriptor AES3 PCM descriptor}.</p>
 * 
 * <p>The values defined here are as specified in section&nbsp;4 of
 * <a href="http://www.ebu.ch/CMSimages/en/tec_doc_t3250-2004_tcm6-12767.pdf">EBU tech. 3250</a>.</p> 
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#UserDataModeType
 * @see tv.amwa.maj.model.AES3PCMDescriptor#getUserDataMode()
 * @see tv.amwa.maj.enumeration.AuxBitsModeType
 *
 *
 */
public enum UserDataModeType 
	implements MediaEnumerationValue {
	
	/**
	 * <p>No user information indicated. This is the default value.</p>
	 */
	NotDefined(0),
	/**
	 * <p>192&nbsp;bit block structure. Preamble "Z" indicates the start of a block.</p>
	 * 
	 * <p>Note that the name of this element starts with an underscore ('<code>_</code>')
     * character as Java identifiers must start with an alphanumeric character or an underscore character. The
     * specified name of this value is "<code>192BitBlockStructure</code>".
	 */
	_192BitBlockStructure(1), 
	/**
	 * <p>Packet system based on HDLC protocol (ISO&nbsp;13239). This system is defined in supplement&nbsp;1 
	 * to EBU Tech.&nbsp;3250: <em>format of the user data channel of the digital audio interface</em>.
	 */
	AES18(2),
	/**
	 * <p>User defined.</p>
	 */
	UserDefined(3),
	/**
	 * <p>User data conforms to the general user data format defined in IEC&nbsp;60958-3.</p>
	 */
	IEC(4),
	/**
	 * <p>Reserved for metadata.</p>
	 */
	Metadata(5),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved0(6),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved1(7),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved2(8),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved3(9),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved4(10),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved5(11),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved6(12),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved7(13),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved8(14),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved9(15);

	private int value;
	
	private UserDataModeType(int value) {
		
		this.value = value;
	}

	@Int64
	public long value() {

		return (long) value;
	}

	/**
	 * <p>Returns the value of this enumerated type associated with the given ordinal.</p>
	 * 
	 * @param userDataModeValue Specified ordinal to use to find the associated value of this type.
	 * @return Value of this type with the given ordinal value.
	 */
	public final static UserDataModeType fromOrdinal(
			int userDataModeValue) {

		switch (userDataModeValue) {
		
		case 0: return NotDefined;
		case 1: return _192BitBlockStructure;
		case 2: return AES18;
		case 3: return UserDefined;
		case 4: return IEC;
		case 5: return Metadata;
		case 6: return Reserved0;
		case 7: return Reserved1;
		case 8: return Reserved2;
		case 9: return Reserved3;
		case 10: return Reserved4;
		case 11: return Reserved5;
		case 12: return Reserved6;
		case 13: return Reserved7;
		case 14: return Reserved8;
		case 15: return Reserved9;
		default:
			return null;
		}
	}

    public String symbol() { 
    	
    	if (this == _192BitBlockStructure)
    		return "UserDataMode_192BitBlockStructure";
    	return "UserDataMode_" + name();
    }
}
