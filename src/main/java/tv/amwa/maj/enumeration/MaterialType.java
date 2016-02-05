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
 * $Log: MaterialType.java,v $
 * Revision 1.8  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.7  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.5  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/08 17:01:52  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.2  2007/11/15 12:52:48  vizigoth
 * Edits to ensure source can make rough and ready javadoc.
 *
 * Revision 1.1  2007/11/13 22:13:55  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;

/**
 * <p>Identifies the material type of content according the the SMPTE UMID specification SMPTE&nbsp;S330M.
 * Elements of the enumeration are taken from table&nbsp;2 in section&nbsp;5.1.1.1.</p>
 * 
 * <p>To find the corresponding byte value of the given material type identifier, call
 * {@link #getMaterialTypeCode()}. The static method {@link #materialTypeFromCode(byte)} converts the
 * byte value code into a value of this enumeration.</p>
 * 
 * @see tv.amwa.maj.record.PackageID
 * @see tv.amwa.maj.misctype.PackageIDType
 *
 *
 */

public enum MaterialType 
	implements MediaEnumerationValue {

	/** 
	 * <p>Picture material (deprecated).</p>
	 * 
	 * @deprecated Use {@link #SinglePicture} or {@link #TwoPicturesOrMore} instead.
	 */
	@Deprecated
	Picture((byte) 0x01),
	/** 
	 * <p>Audio material (deprecated).</p>
	 * 
	 * @deprecated Use {@link #SingleAudio} or {@link #TwoAudiosOrMore} instead.
	 */
	Audio((byte) 0x02),
	/** 
	 * <p>Data material (deprecated).</p>
	 * 
	 *  @deprecated Use {@link #SingleAuxilary} or {@link #TwoAuxilarysOrMore} instead.
	 */
	Data((byte) 0x03),
	/** 
	 * <p>Other material (deprecated).</p>
	 * 
	 * @deprecated Use {@link #NotIdentified} instead.
	 */
	Other((byte) 0x04),
	/** 
	 * <p>Single picture component. For example, Y component.</p>
	 */
	SinglePicture((byte) 0x05),
	/** 
	 * <p>Two or more picture components in a single container. For example, 
	 * interleaved Y, Cb and Cr components.</p>
	 */
	TwoPicturesOrMore((byte) 0x06),
	/** 
	 * <p>Single audio component. For example, mono audio.</p>
	 */
	SingleAudio((byte) 0x08),
	/** 
	 * <p>Two or more audio components in a single container. For example, AES3 audio pair.</p>
	 */
	TwoAudiosOrMore((byte) 0x09),
	/** 
	 * <p>Single auxiliary (or data) component. For example, sub-titles only.</p>
	 */
	SingleAuxilary((byte) 0x0B),
	/** 
	 * <p>Two or more auxiliary (or data) components in a single container. For example, multiple 
	 * sub-titles streams in different languages.</p>
	 */
	TwoAuxilarysOrMore((byte) 0x0C),
	/** 
	 * <p>Mixed group of components in a single container. For example, video &amp; stereo audio pair.</p>
	 */
	MixedGroup((byte) 0x0D),
	/** 
	 * <p>Material type is not identified.</p>
	 */
	NotIdentified((byte) 0x0F);
	
	/** Internal representation of type value. */
	private byte type = (byte) 0;
	
	/**
	 * <p>Create an instance of the enumeration as and when requried.</p>
	 *
	 * @param type Byte value corresponding to enumeration token.
	 */
	private MaterialType(byte type) {
		
		this.type = type;
	}
	
	/**
	 * <p>Returns the byte value of the material type, as defined in table&nbsp;2 of SMPTE&nbsp;S330M.</p>
	 *
	 * @return Byte value associated with the material type.
	 */
	public byte getMaterialTypeCode() {
		
		return type;
	}

	/**
	 * <p>Converts a byte code for a material type into a value of this enumeration, 
	 * as defined in table&nbsp;2 of SMPTE&nbsp;S330M.</p>
	 *
	 * @param code Code to convert to an enumeration value.
	 * @return Enumeration value corresponding to the given code, or <code>null</code> if a match
	 * could not be found.
	 */
	public final static MaterialType materialTypeFromCode(byte code) {
	
		for ( MaterialType materialType : MaterialType.values() )
			if (materialType.getMaterialTypeCode() == code) return materialType;
		
		return null;
	}

	public long value() {

		return (long) type;
	}
	
    public String symbol() { return name(); }
}
