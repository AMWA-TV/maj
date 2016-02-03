/* 
 **********************************************************************
 *
 * $Id: ChannelStatusModeType.java,v 1.7 2011/02/14 22:32:58 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: ChannelStatusModeType.java,v $
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
 * Revision 1.2  2008/01/08 17:01:53  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:49  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;

/** 
 * <p>Specifies how channel status data is encoded as part of a {@linkplain tv.amwa.maj.model.AES3PCMDescriptor AES3 
 * PCM descriptor}.</p>
 *
 * @see tv.amwa.maj.model.AES3PCMDescriptor#getChannelStatusMode()
 * @see tv.amwa.maj.industry.TypeDefinitions#ChannelStatusModeType
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public enum ChannelStatusModeType 
	implements MediaEnumerationValue {
	
	/**
	 * <p>No channel status mode data is encoded.</p>
	 */
	None(0),
	/**
	 * <p>AES3 minimum encoded channel data (byte 0 bit 1 = '1').</p>
	 */
	Minimum(1),
	/**
	 * <p>AES3 standard encoding of channel status data.</p>
	 */
	Standard(2),
	/**
	 * <p>Fixed 24 bytes of data in the {@linkplain tv.amwa.maj.model.AES3PCMDescriptor#getFixedChannelStatusDataAt(int)
	 * fixed channel status data property}.</p>
	 */
	Fixed(3),
	/**
	 * <p>Stream of data within header metadata.</p>
	 */
	Stream(4),
	/**
	 * <p>Stream of data multiplexed within essence.</p>
	 */
	Essence(5);

	private int value;
	
	private ChannelStatusModeType(int value) {
		
		this.value = value;
	}
			  
	public long value() {

		return (long) value;
	}

	/**
	 * <p>Return the channel status mode element of this enumeration for the given ordinal value.</p>
	 * 
	 * @param channelStatusModeValue Ordinal value corresponding to a value of this enumeration type. 
	 * @return Corresponding enumeration value.
	 */
	public final static ChannelStatusModeType fromOrdinal(
			int channelStatusModeValue) {
		
		switch(channelStatusModeValue) {

		case 0: return None;
		case 1: return Minimum;
		case 2: return Standard;
		case 3: return Fixed;
		case 4: return Stream;
		case 5: return Essence;
		default:
			return null;
		}
	}
	
	public String symbol() {
		
		return "ChannelStatusMode_" + name();
	}
}
