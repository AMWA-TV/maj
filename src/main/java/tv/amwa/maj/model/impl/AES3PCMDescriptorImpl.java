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
 * $Log: AES3PCMDescriptorImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/25 14:18:28  vizigoth
 * Class instantiation tests with all properties present completed.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/11/08 15:52:27  vizigoth
 * Bug fix to prevent crash on zero channel count.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.3  2008/01/27 11:14:40  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.2  2008/01/15 12:28:33  vizigoth
 * Minor update to allow easier testing in the future.
 *
 * Revision 1.1  2007/11/13 22:09:45  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.AuxBitsModeType;
import tv.amwa.maj.enumeration.ChannelStatusModeType;
import tv.amwa.maj.enumeration.EmphasisType;
import tv.amwa.maj.enumeration.UserDataModeType;
import tv.amwa.maj.exception.BadSizeException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.AES3PCMDescriptor;

/** 
 * <p>Implements an AES3 PCM descriptor that describes audio essence in the AES/EBU
 * audio file format, as defined in <a href="http://www.ebu.ch/CMSimages/en/tec_AES-EBU_eg_tcm6-11890.pdf">
 * the EBU/AES digital audio interface specification</a>.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x4700,
		  uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "AES3PCMDescriptor",
		  description = "Describes audio essence in the AES3 audio file format.",
		  symbol = "AES3PCMDescriptor")
public class AES3PCMDescriptorImpl
	extends WAVEPCMDescriptorImpl
	implements AES3PCMDescriptor,
		Serializable,
		XMLSerializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -3658407306887688052L;

	private EmphasisType emphasis = null;
	private Short blockStartOffset = null;
	private AuxBitsModeType auxBitsMode = null;
	private ChannelStatusModeType[] channelStatusMode = null;
	private byte[][] fixedChannelStatusData = null;
	private UserDataModeType[] userDataMode = null; 
	private byte[][] fixedUserData = null;
	
	public AES3PCMDescriptorImpl() { 
		setChannelCount(1);
	}
	
	/**
	 * <p>Creates and initializes a new PCM sound descriptor, which is associated with audio essence 
	 * formatted according to the 
	 * <a href="http://www.ebu.ch/en/technical/publications/userguides/bwf_user_guide.php">BWF File 
	 * Format</a>.</p>
	 *
	 * @param containerFormat Identifies the container mechanism used to store the essence.
	 * @param audioSamplingRate Sampling rate of the audio essence.
	 * @param channels Number of audio channels.
	 * @param quantizationBits Number of quantization bits.
	 * @param blockAlign Sample block alignment.
	 * @param averageBPS Average bytes per second.
	 * 
	 * @throws NullPointerException The container format or sampling rate properties are
	 * <code>null</code>.
	 * @throws IllegalArgumentException Cannot set the channels, quantization bits, block align
	 * or average bits per second values to negative values. 
	 */
	public AES3PCMDescriptorImpl(
			tv.amwa.maj.model.ContainerDefinition containerFormat,
			tv.amwa.maj.record.Rational audioSamplingRate,
			@UInt32 int channels,
			@UInt32 int quantizationBits,
			@UInt16 short blockAlign,
			@UInt32 int averageBPS)
		throws NullPointerException,
			IllegalArgumentException {
		
		super(containerFormat, audioSamplingRate, channels, quantizationBits, blockAlign, averageBPS);
	}
	
	@MediaProperty(uuid1 = 0x04020501, uuid2 = 0x0100, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "AuxBitsMode",
			     typeName = "AuxBitsModeType",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D08,
			     symbol = "AuxBitsMode")
	public AuxBitsModeType getAuxBitsMode() {

		if (auxBitsMode == null)
			return AUXBITSMODE_DEFAULT;
		else
			return auxBitsMode;
	}

	@MediaPropertySetter("AuxBitsMode")
	public void setAuxBitsMode(
			AuxBitsModeType auxBitsMode) {

		if (auxBitsMode == null)
			this.auxBitsMode = null;
		else
			this.auxBitsMode = auxBitsMode;
	}
	
	@MediaProperty(uuid1 = 0x04020302, uuid2 = 0x0300, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "BlockStartOffset",
			     typeName = "UInt16",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D0F,
			     symbol = "BlockStartOffset")
	public short getBlockStartOffset() {

		if (blockStartOffset == null)
			return BLOCKSTARTOFFSET_DEFAULT;
		else
			return blockStartOffset;
	}

	@MediaPropertySetter("BlockStartOffset")
	public void setBlockStartOffset(
			Short blockStartOffset)
		throws IllegalArgumentException {

		if (blockStartOffset == null) {
			this.blockStartOffset = null;
			return;
		}
		
		if (blockStartOffset < 0)
			throw new IllegalArgumentException("Cannot set the block start offset of this AES3 PCM descriptor to a negative value.");

		this.blockStartOffset = blockStartOffset;
	}
	

	@MediaProperty(uuid1 = 0x04020501, uuid2 = 0x0200, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "ChannelStatusMode",
			     typeName = "ChannelStatusModeArray",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D10,
			     symbol = "ChannelStatusMode")
	public ChannelStatusModeType[] getChannelStatusMode() {
		
		if (channelStatusMode == null) {
			ChannelStatusModeType[] defaultStatus = new ChannelStatusModeType[getChannelCount()];
			for ( int x = 0 ; x < defaultStatus.length ; x++ )
				defaultStatus[x] = ChannelStatusModeType.None;
			return defaultStatus;
		}
		
		return channelStatusMode.clone();
	}
	
	@MediaPropertySetter("ChannelStatusMode")
	public void setChannelStatusMode(
			ChannelStatusModeType[] channelStatusMode)
		throws NullPointerException,
			BadSizeException {
		
		if (channelStatusMode == null) {
			this.channelStatusMode = null;
			return;
		}
		
		if (channelStatusMode.length != getChannelCount() ) {
			throw new BadSizeException("The array of channel status mode values must be the same length as the number of channels of this AES3PCMDescriptor.");
		}

		this.channelStatusMode = new ChannelStatusModeType[channelStatusMode.length];
		
		for ( int x = 0 ; x < channelStatusMode.length ; x++ ) {
			if (channelStatusMode[x] == null)
				throw new NullPointerException("Cannot set the channel status mode for channel " + x + " to a null value for this AES3 PCM descriptor.");
			
			this.channelStatusMode[x] = channelStatusMode[x];
		}
	}
	
	@MediaListGetAt("ChannelStatusMode")
	public ChannelStatusModeType getChannelStatusModeAt(
			int index)
		throws IndexOutOfBoundsException {

		if ((index < 0) || (index >= getChannelCount()))
			throw new IndexOutOfBoundsException("The given channel index is outside the acceptable range for a AES3 PCM descriptor describing audio with " + getChannelCount() + " channels.");
		
		if (channelStatusMode == null) 
			return ChannelStatusModeType.None;
		else
			return channelStatusMode[index];
	}
	
	public void setChannelStatusModeAt(
			int index,
			ChannelStatusModeType channelStatusMode)
		throws IndexOutOfBoundsException {

		if (channelStatusMode == null)
			throw new NullPointerException("Cannot set an element of the channel status mode array to null for this AES3 PCM descriptor.");
		
		if ((index < 0) || (index >= getChannelCount()))
			throw new IndexOutOfBoundsException("The given channel index is outside the acceptable range for a AES3 PCM descriptor describing audio with " + getChannelCount() + " channels.");			
		
		if (this.channelStatusMode == null) {
			this.channelStatusMode = new ChannelStatusModeType[getChannelCount()];
			for ( int x = 0 ; x < this.channelStatusMode.length ; x++ ) 
				this.channelStatusMode[x] = ChannelStatusModeType.None;
		}
		
		this.channelStatusMode[index] = channelStatusMode;
	}

	@MediaPropertyCount("ChannelStatusMode")
	public int countChannelStatusMode() {
		
		if (channelStatusMode != null)
			return channelStatusMode.length;
		else
			return 0;
	}
	
	@MediaPropertyClear("ChannelStatusMode")
	public void clearChannelStatusMode() {
		
		channelStatusMode = null;
	}
	
	@MediaProperty(uuid1 = 0x04020501, uuid2 = 0x0600, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "Emphasis",
			     typeName = "EmphasisType",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D0D,
			     symbol = "Emphasis")
	public EmphasisType getEmphasis() {

		if (emphasis == null)
			return EMPHASIS_DEFAULT;
		else
			return emphasis;
	}

	@MediaPropertySetter("Emphasis")
	public void setEmphasis(
			EmphasisType emphasisType) {

		this.emphasis = emphasisType;
	}

	@MediaProperty(uuid1 = 0x04020501, uuid2 = 0x0300, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "FixedChannelStatusData",
			     typeName = "UInt8Array",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D11,
			     symbol = "FixedChannelStatusData")
	public byte[] getFixedChannelStatusData() {
		
		byte[] persistentStatusData = new byte[getChannelCount() * 24];

		if (fixedChannelStatusData == null) {
			for ( int x = 0 ; x < getChannelCount() ; x++ )
				System.arraycopy(AES3Minimum, 0, persistentStatusData, x * 24, 24);
		}
		else {
			for ( int x = 0 ; x < getChannelCount() ; x++ )
				System.arraycopy(fixedChannelStatusData[x], 0, persistentStatusData, x * 24, 24);
		}
		
		return persistentStatusData;
	}

	@MediaPropertySetter("FixedChannelStatusData")
	public void setFixedChannelStatusData(
			byte[] fixedChannelStatusData) 
		throws BadSizeException {
		
		if (fixedChannelStatusData == null) {
			this.fixedChannelStatusData = null;
			return;
		}
		
		if (fixedChannelStatusData.length < (getChannelCount() * 24)) {
			throw new BadSizeException("The fixed channel status data must contain exactly 24-bytes per channel.");
		}
		
		this.fixedChannelStatusData = new byte[getChannelCount()][24];
		
		for ( int x = 0 ; x < getChannelCount() ; x++ )
			System.arraycopy(
					fixedChannelStatusData, 
					24 * x, 
					this.fixedChannelStatusData[x], 
					0, 
					24);
	}

	@MediaPropertyClear("FixedChannelStatusData")
	public void clearFixedChannelStatusData() {
		
		fixedChannelStatusData = null;
	}
	
	public byte[] getFixedChannelStatusDataAt(
			int index)
		throws IndexOutOfBoundsException {
		
		if ((index < 0) || (index >= getChannelCount()))
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for an AES3 PCM descriptor with " + getChannelCount() + " channels.");
		
		if (fixedChannelStatusData == null)
			return AES3Minimum.clone();
		else
			return fixedChannelStatusData[index].clone();
	}

	public void setFixedChannelStatusDataAt(
			int index,
			byte[] fixedChannelStatusData)
		throws NullPointerException, 
			IndexOutOfBoundsException,
			BadSizeException {

		if ((index < 0) || (index >= getChannelCount()))
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for an AES3 PCM descriptor with " + getChannelCount() + " channels.");

		if (fixedChannelStatusData == null) 
			throw new NullPointerException("Cannot set fixed channel status data using a null value for this AES3 PCM Descriptor.");
		
		if (fixedChannelStatusData.length != 24)
			throw new BadSizeException("The given fixed channel status data must be exactly 24-bytes in length.");
		
		if (this.fixedChannelStatusData == null) {
			this.fixedChannelStatusData = new byte[getChannelCount()][24];
			for ( int x = 0 ; x < this.fixedChannelStatusData.length ; x++ )
				this.fixedChannelStatusData[x] = AES3Minimum.clone();
		}
		
		System.arraycopy(fixedChannelStatusData, 0, this.fixedChannelStatusData[index], 0, 24);
	}

	public void omitChannelStatusData() {
		
		fixedChannelStatusData = null;
		channelStatusMode = null;
	}

	@MediaProperty(uuid1 = 0x04020501, uuid2 = 0x0500, uuid3 = 0x0000,
				 uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
				 definedName = "FixedUserData",
				 typeName = "UInt8Array",
				 optional = true,
				 uniqueIdentifier = false,
				 pid = 0x3D13,
				 symbol = "FixedUserData")
	public byte[] getFixedUserData() {
		
		byte[] persistentUserData = new byte[getChannelCount() * 24];
		
		if (fixedUserData == null) {
			for ( int x = 0 ; x < getChannelCount() ; x++ )
				System.arraycopy(AES3DefaultUserData, 0, persistentUserData, x * 24, 24);
		}
		else {	
			for ( int x = 0 ; x < getChannelCount() ; x++ )
				System.arraycopy(fixedUserData[x], 0, persistentUserData, x * 24, 24);
		}
		
		return persistentUserData;
	}

	@MediaPropertySetter("FixedUserData")
	public void setFixedUserData(
			byte[] fixedUserData) 
		throws BadSizeException {
		
		if (fixedUserData == null) {
			this.fixedUserData = null;
			return;
		}
		
		if (getChannelCount() == 0) {
			this.fixedUserData = new byte[1][fixedUserData.length];
			this.fixedUserData[0] = fixedUserData.clone();
			return;
		}
		
		if (fixedUserData.length < (getChannelCount() * 24))
			throw new BadSizeException("The fixed user data byte array must contain exactly 24-bytes per channel.");
		
		this.fixedUserData = new byte[getChannelCount()][24];
		
		for ( int x = 0 ; x < getChannelCount() ; x++ )
			System.arraycopy(fixedUserData, x * 24, this.fixedUserData[x], 0, 24); 
	}

	@MediaPropertyClear("FixedUserData")
	public void clearFixedUserData() {
		
		fixedUserData = null;
	}
	
	public byte[] getFixedUserDataAt(
			int index)
		throws IndexOutOfBoundsException {

		if ((index < 0) || (index >= getChannelCount()))
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for this AES3 PCM descriptor for audio essence with " + getChannelCount() + " channels.");
		
		if (fixedUserData == null) 
			return AES3DefaultUserData.clone();
		else
			return fixedUserData[index].clone();
	}

	public void setFixedUserDataAt(
			int index,
			byte[] fixedUserData)
		throws NullPointerException,
			IndexOutOfBoundsException,
			BadSizeException {

		if ((index < 0) || (index >= getChannelCount()))
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for this AES3 PCM descriptor for audio essence with " + getChannelCount() + " channels.");

		if (fixedUserData == null)
			throw new NullPointerException("Cannot set the fixed user data for a channel using a null value for this AES3 PCM descriptor.");
		
		if (fixedUserData.length != 24)
			throw new BadSizeException("The size of the fixed user data array must be exactly 24-bytes.");

		if (this.fixedUserData == null) {
			this.fixedUserData = new byte[getChannelCount()][24];
			for ( int x = 0 ; x < this.fixedUserData.length ; x++ )
				this.fixedUserData[x] = AES3DefaultUserData.clone();
		}
		
		System.arraycopy(fixedUserData, 0, this.fixedUserData[index], 0, 24);
	}

	@MediaProperty(uuid1 = 0x04020501, uuid2 = 0x0400, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "UserDataMode",
			     typeName = "UserDataModeArray",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3D12,
			     symbol = "UserDataMode")
	public UserDataModeType[] getUserDataMode() {
		
		if (userDataMode == null) {
			UserDataModeType[] defaultMode = new UserDataModeType[getChannelCount()];
			for ( int x = 0 ; x < defaultMode.length ; x++ )
				defaultMode[x] = UserDataModeType.NotDefined;
			return defaultMode;
		}
		
		return userDataMode.clone();
	}

	@MediaPropertySetter("UserDataMode")
	public void setUserDataMode(
			UserDataModeType[] userDataMode) 
		throws NullPointerException, 
			BadSizeException {
		
		if (userDataMode == null) {
			this.userDataMode = null;
			return;
		}
		
		if (userDataMode.length != getChannelCount())
			throw new BadSizeException("The number of elements in the user data mode array must be the same as the number of channels of the described AES3 PCM essence.");
		
		this.userDataMode = new UserDataModeType[userDataMode.length];
		
		for ( int x = 0 ; x < userDataMode.length ; x++ ) {
			if (userDataMode[x] == null)
				throw new NullPointerException("Cannot set the value of the user data mode for channel " + x + " to a null value for this AES3 PCM descriptor.");
			
			this.userDataMode[x] = userDataMode[x];
		}
	}
	
	@MediaPropertyClear("UserDataMode")
	public void clearUserDataMode() {
		
		userDataMode = null;
	}

	public UserDataModeType getUserDataModeAt(
			int index)
		throws IndexOutOfBoundsException {

		if ((index < 0) || (index >= getChannelCount()))
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for an AES3 PCM descriptor for essence with " + getChannelCount() + " channels.");

		if (userDataMode == null) 
			return UserDataModeType.NotDefined;
		
		return userDataMode[index];
	}

	public void setUserDataModeAt(
			int index,
			UserDataModeType userDataMode)
		throws NullPointerException, 
			IndexOutOfBoundsException {

		if ((index < 0) || (index >= getChannelCount()))
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for an AES3 PCM descriptor for essence with " + getChannelCount() + " channels.");

		if (userDataMode == null)
			throw new NullPointerException("Cannot set the user data mode for any channel to null for this AES3 PCM descriptor.");

		if (this.userDataMode == null) {
			this.userDataMode = new UserDataModeType[getChannelCount()];
			for ( int x = 0 ; x < this.userDataMode.length ; x++ ) 
				this.userDataMode[x] = UserDataModeType.NotDefined;
		}
			
		this.userDataMode[index] = userDataMode;
	}

	public void omitUserData() {
		
		userDataMode = null;
		fixedUserData = null;
	}

	@Override
	public AES3PCMDescriptor clone() {
		
		return (AES3PCMDescriptor) super.clone();
	}
	
	// TODO consider if add to dictionary is required
	
	@Override
	public String getComment() {
		
		return "Element yet to be confirmed as valid AAF XML";
	}
}
