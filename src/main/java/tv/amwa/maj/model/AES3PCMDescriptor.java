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
 * $Log: AES3PCMDescriptor.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/01/29 18:11:22  vizigoth
 * Updated documentation for newly added classes to 1.1.2 and associated fixes.
 *
 * Revision 1.3  2008/01/27 11:07:21  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2008/01/14 21:03:59  vizigoth
 * Minor comment fix.
 *
 * Revision 1.1  2007/11/13 22:08:20  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.AuxBitsModeType;
import tv.amwa.maj.enumeration.ChannelStatusModeType;
import tv.amwa.maj.enumeration.EmphasisType;
import tv.amwa.maj.enumeration.UserDataModeType;
import tv.amwa.maj.exception.BadSizeException;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;

/** 
 * <p>Specifies an AES3 PCM descriptor that describes audio essence in the AES/EBU
 * audio file format, as defined in <a href="http://www.ebu.ch/CMSimages/en/tec_AES-EBU_eg_tcm6-11890.pdf">
 * the EBU/AES digital audio interface specification</a>.</p>
 *
 *
 * 
 */
public interface AES3PCMDescriptor
	extends WAVEPCMDescriptor {

	/** <p>Default value for the emphasis property, which is 
	 * {@link tv.amwa.maj.enumeration.EmphasisType#Unknown}.</p> */
	public final static EmphasisType EMPHASIS_DEFAULT = EmphasisType.Unknown;
	
	/** <p>Default value for the block start offset property, which is 0.</p> */
	public final static short BLOCKSTARTOFFSET_DEFAULT = (short) 0;
	
	/** <p>Default value for the aux bits mode property, which is 
	 * {@link AuxBitsModeType#NotDefined}.</p> */
	public final static AuxBitsModeType AUXBITSMODE_DEFAULT = AuxBitsModeType.NotDefined;
	
	/**
	 * <p>Returns the AES3 emphasis of the associated sound essence. If this optional property is not 
	 * present, the {@linkplain #EMPHASIS_DEFAULT default value} of this property is returned.</p>
	 *
	 * @return AES3 emphasis of the associated sound essence.
	 * 
	 * @see #EMPHASIS_DEFAULT
	 */
	public EmphasisType getEmphasis();

	/**
	 * <p>Sets the AES3 emphasis property of the associated sound essence. Calling
	 * this method with <code>null</code> will omit this optional property.</p>
	 *
	 * @param emphasisType AES3 emphasis property of the associated sound essence.
	 * 
	 * @see #EMPHASIS_DEFAULT
	 */
	public void setEmphasis (
			EmphasisType emphasisType);

	/**
	 * <p>Returns the AES3 position of the first Z preamble in the associated sound
	 * essence stream. If this optional property is not present, the default value 
	 * of&nbsp;0 is returned.</p>
	 *
	 * @return AES3 position of the first Z preamble is the associated sound
	 * essence stream.
	 * 
	 * @see #BLOCKSTARTOFFSET_DEFAULT
	 */
	public @UInt16 short getBlockStartOffset();

	/**
	 * <p>Sets the AES3 position of the first Z preamble in the associated sound
	 * essence stream. The default value of this optional property is&nbsp;0.
	 * To omit this optional property, call this method with <code>null</code>.</p>
	 *
	 * @param blockStartOffset AES3 position of the first Z preamble in the associated sound
	 * essence stream.
	 * 
	 * @throws IllegalArgumentException The block start offset is negative.
	 * 
	 * @see #BLOCKSTARTOFFSET_DEFAULT
	 */
	public void setBlockStartOffset (
			@UInt16 Short blockStartOffset)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the AES3 use of auxiliary bits in the associated audio
	 * essence. If this optional property is not present, the default value of
	 * {@link AuxBitsModeType#NotDefined} is returned.</p>
	 *
	 * @return AES3 use of auxiliary bits in the associated audio essence.
	 * 
	 * @see #AUXBITSMODE_DEFAULT
	 */
	public AuxBitsModeType getAuxBitsMode();

	/**
	 * <p>Sets the AES3 use of auxiliary bits in the associated audio
	 * essence. To omit this optional property, call this method with 
	 * <code>null</code>.</p>
	 *
	 * @param auxBitsMode AES3 use of auxiliary bits in the associated audio 
	 * essence.
	 * 
	 * @see #AUXBITSMODE_DEFAULT
	 */
	public void setAuxBitsMode (
	   	 	AuxBitsModeType auxBitsMode);

	/**
	 * <p>Returns the AES3 enumerated mode of carriage of channel status data for all
	 * channels of the associated audio essence.</p>
	 * 
	 * @return AES3 enumerated mode of carriage of channel status data for all
	 * channels of the associated audio essence.
	 * 
	 * @see #getChannelStatusModeAt(int)
	 */
	public ChannelStatusModeType[] getChannelStatusMode();
	
	/**
	 * <p>Returns the AES3 enumerated mode of carriage of channel status data for the
	 * associated audio essence for the given channel number. If the channel status
	 * mode property is not present, the default value of {@link ChannelStatusModeType#None}
	 * is returned.</p>
	 *
	 * @param index Index into the AES3 channel status mode array.
	 * @return Element of the AES3 channel status mode at the given index.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range
	 * for the current channel status mode array.
	 * 
	 * @see #getChannelStatusMode()
	 * @see SoundDescriptor#getChannelCount()
	 */
	public ChannelStatusModeType getChannelStatusModeAt (
			@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Sets the AES3 enumerated mode of carriage of channel status data for the
	 * associated audio essence at the given channel number.</p>
	 *
	 * <p>If the channel status mode property is not present at the time of calling 
	 * the method, after completion of the method the property becomes
	 * present and properties at other indexes are initialized to 
	 * {@link ChannelStatusModeType#None}.</p>
	 *
	 * @param index Index of the channel status mode array to set the channel status for.
	 * @param channelStatusMode Channel status for the given index.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range
	 * for the current channel status mode array.
	 * @throws NullPointerException Cannot set the channel status for a channel to 
	 * <code>null</code>.
	 * 
	 * @see SoundDescriptor#getChannelCount()
	 */
	public void setChannelStatusModeAt(
	    	@UInt32 int index,
	    	ChannelStatusModeType channelStatusMode)
		throws IndexOutOfBoundsException,
			NullPointerException;

	/** <p>Minimum channel status according to the AES3 specification, specified in section 5.2.1 of 
	 * <a href="http://www.ebu.ch/CMSimages/en/tec_doc_t3250-2004_tcm6-12767.pdf">EBU Tech 3250</a>.
	 * This is the default value returned by {@link #getChannelStatusModeAt(int)}.</p> */
	public final static byte[] AES3Minimum = new byte[] {
			(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
	};
	
	/**
	 * <p>Returns the AES3 fixed channel status data for all channels in the associated audio
	 * essence.</p>
	 * 
	 * @return AES3 fixed channel status data for all channels in the associated audio
	 * essence.
	 * 
	 * @see #getFixedChannelStatusDataAt(int)
	 */
	public byte[] getFixedChannelStatusData();
	
	/** 
	 * <p>Returns the AES3 fixed channel status data for the channel referenced by the given
	 * index in the associated audio essence. The value returned is a 24-byte array.<p>
	 * 
	 * <p>The default value is the {@linkplain #AES3Minimum AES3 minimum}, which codes the most significant 
	 * bit of the first byte as <code>1</code> and all other bits as <code>0</code>. If this
	 * optional property is omitted, the default value will be returned.</p>
	 *
	 * @param index Channel index to retrieve the fixed channel status data for.
	 * @return Fixed channel status data for the given channel in the associated audio essence.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range
	 * for the current range of channels for which fixed status data is represented.
	 * 
	 * @see #getFixedChannelStatusData()
	 * @see SoundDescriptor#getChannelCount()
	 */
	public byte[] getFixedChannelStatusDataAt (
			@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Sets the AES3 fixed channel status data for the channel referenced by the given
	 * index in the associated audio essence. The data must take the form of a 24-byte array.</p>
	 *
	 * @param index Channel number to set the AES3 fixed channel status data for.
	 * @param fixedChannelStatusData Fixed channel status array.
	 * 
	 * @throws NullPointerException The fixed channel status data argument is <code>null</code>.
	 * @throws IndexOutOfBoundsException The given channel index is outside the acceptable
	 * range for the associated audio essence.
	 * @throws BadSizeException The fixed channel status data must be an array of 24-bytes
	 * exactly.
	 * 
	 * @see SoundDescriptor#getChannelCount()
	 */
	public void setFixedChannelStatusDataAt(
			@UInt32 int index,
			byte[] fixedChannelStatusData)
		throws NullPointerException, 
			IndexOutOfBoundsException,
			BadSizeException;
	
	/**
	 * <p>Omits the optional fixed channel status data and channel status mode properties from this
	 * AES3 PCM descriptor. To make them present again, call {@link #setFixedChannelStatusDataAt(int, byte[])}
	 * and {@link #setChannelStatusModeAt(int, ChannelStatusModeType)}.</p>
	 */
	public void omitChannelStatusData();

	/**
	 * <p>Returns the user data mode for all the channels in the associated audio essence.</p>
	 * 
	 * @return User data modes for all channels in the associated essence.
	 * 
	 * @see #getUserDataModeAt(int)
	 */
	public UserDataModeType[] getUserDataMode();
	
	/**
	 * <p>Returns the user data mode for the channel of the associated audio essence 
	 * referenced by the given channel index.<p>
	 * 
	 * <p>The default value for each element of this property is 
	 * {@link UserDataModeType#NotDefined}. If this optional property is omitted, the default
	 * value will be returned.</p>
	 *
	 * @param index Channel index to identify the channel to retrieve the user data mode for.
	 * @return Type of user data for the given channel number in the associated audio
	 * essence.
	 * 
	 * @throws IndexOutOfBoundsException The given channel index is outside the acceptable
	 * range for the associated audio essence.
	 * 
	 * @see #getUserDataMode()
	 * @see SoundDescriptor#getChannelCount()
	 */
	public UserDataModeType getUserDataModeAt(
			@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Sets the user data mode for the channel of the associated audio essence 
	 * referenced by the given channel index.</p>
	 *
	 * @param index Channel index to identify the channel to set the user data mode for.
	 * @param userDataMode Type of user data for the given channel.
	 * 
	 * @throws IndexOutOfBoundsException The given channel index is outside the acceptable
	 * range for the associated audio essence.
	 * @throws NullPointerException Cannot set the user data mode for a channel to 
	 * <code>null</code>.
	 * 
	 * @see SoundDescriptor#getChannelCount()
	 */
	public void setUserDataModeAt(
			@UInt32 int index,
			UserDataModeType userDataMode)
		throws NullPointerException, 
			IndexOutOfBoundsException;

	/** <p>Default AES3 fixed user data value.</p> */
	public final static byte[] AES3DefaultUserData = new byte[] {
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00		
	};
	
	/**
	 * <p>Returns the AES3 fixed user data for all channels in the associated audio
	 * essence. The value is always the number of channels multiplied by 24.</p>
	 * 
	 * @return AES3 fixed user data for all channels.
	 * 
	 * @see #getFixedUserDataAt(int)
	 */
	public byte[] getFixedUserData();
	
	/**
	 * <p>Returns the AES3 fixed user data for the given channel in the associated audio
	 * essence. The value is always a 24-byte array.<p>
	 * 
	 * <p>The default value for fixed user data on a channel is all zero values, as 
	 * represented by {@link #AES3DefaultUserData}. If this optional property is not
	 * present, the default value will be returned.</p>
	 *
	 * @param index Channel index of the fixed user data to retrieve.
	 * @return Retrieved fixed user data for the given channel number from the associated
	 * audio essence.
	 * 
	 * @throws IndexOutOfBoundsException The channel number is outside the acceptable range
	 * for the associated audio essence.
	 * 
	 * @see #getFixedUserData()
	 * @see SoundDescriptor#getChannelCount()
	 */
	public byte[] getFixedUserDataAt (
			@UInt32 int index)
		throws IndexOutOfBoundsException;

	/**
	 * <p>Sets the AES3 fixed user data for the given channel in the associated audio
	 * essence. The value is always a 24-byte array.</p>
	 *
	 * @param index Channel index of the fixed user data to set.
	 * @param fixedUserData Fixed user data for the given channel number of the associated
	 * audio essence.
	 * 
	 * @throws NullPointerException Cannot set fixed user data to <code>null</code>.
	 * @throws IndexOutOfBoundsException The channel number is outside the acceptable range
	 * for the associated audio essence.
	 * @throws BadSizeException The fixed user data value must by a 24-byte array.
	 * 
	 * @see SoundDescriptor#getChannelCount()
	 */
	public void setFixedUserDataAt (
			@UInt32 int index,
			byte[] fixedUserData)
		throws NullPointerException, 
			IndexOutOfBoundsException,
			BadSizeException;
	
	/**
	 * <p>Omit the optional fixed user data and user data mode properties from this AES3 PCM descriptor. To make them 
	 * present again, call {@link #setFixedUserDataAt(int, byte[])} and {@link #setUserDataModeAt(int, UserDataModeType)}.</p>
	 */
	public void omitUserData();
	
	/**
	 * <p>Create a cloned copy of this AES3 PCM descriptor.</p>
	 * 
	 * @return Cloned copy of this AES3 PCM descriptor.
	 */
	public AES3PCMDescriptor clone();
}
