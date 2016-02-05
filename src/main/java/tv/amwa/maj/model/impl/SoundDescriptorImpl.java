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
 * $Log: SoundDescriptorImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:53  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.ElectroSpatialFormulation;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.ContainerDefinition;
import tv.amwa.maj.model.SoundDescriptor;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.record.impl.RationalImpl;

/** 
 * <p>Implements the description of a file {@linkplain tv.amwa.maj.model.SourcePackage source package} that 
 * is associated with audio essence.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x4200,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "SoundDescriptor",
		  description = "The SoundDescriptor class specifies that a File SourcePackage is associated with audio essence.",
		  symbol = "SoundDescriptor")
public class SoundDescriptorImpl
	extends 
		AAFFileDescriptorImpl
	implements 
		SoundDescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 6777932901589723444L;

	private Rational audioSampleRate;
	private Boolean locked = null;
	private Byte audioReferenceLevel = null;
	private ElectroSpatialFormulation electrospatialFormulation = null;
	private int channelCount;
	private int quantizationBits;
	private Byte dialNorm = null;
	private AUID soundCompression = null;
	
	/** Default constructor is not public to avoid unset required fields. */
	public SoundDescriptorImpl() { }

	/**
	 * <p>Creates and initializes a new sound descriptor, which specifies that a 
	 * {@link SourcePackageImpl file source package} is associated with audio essence.</p>
	 *
	 * @param containerFormat Identifies the container mechanism used to store the essence.
	 * @param audioSampleRate Sampling rate of the audio essence.
	 * @param channels Number of audio channels.
	 * @param quantizationBits Number of quantization bits.
	 * 
	 * @throws NullPointerException One or both of the container format and/or sampling rate
	 * is <code>null</code>.
	 * @throws IllegalArgumentException The channels and/or quantization bit values are negative.
	 */
	public SoundDescriptorImpl(
			ContainerDefinition containerFormat,
			Rational audioSampleRate,
			@UInt32 int channels,
			@UInt32 int quantizationBits)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (audioSampleRate == null)
			throw new NullPointerException("Cannot create a new sound descriptor using a null audio sampling rate.");
		if (channels < 0)
			throw new IllegalArgumentException("Cannot create a new sound descriptor with a negative number of channels.");
		if (quantizationBits < 0)
			throw new IllegalArgumentException("Cannot create a new sound descriptor with a negative value for quantization bits.");
		
		setContainerFormat(containerFormat);
		setAudioSampleRate(audioSampleRate);
		setChannelCount(channels);
		setQuantizationBits(quantizationBits);
	}
	
	@MediaProperty(uuid1 = 0x04020101, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "AudioReferenceLevel",
			aliases = { "AudioRefLevel" },
			typeName = "Int8",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D04,
			symbol = "AudioReferenceLevel")
	public byte getAudioReferenceLevel()
			throws PropertyNotPresentException {

		if (audioReferenceLevel == null)
			throw new PropertyNotPresentException("The optional audio reference level is not present in this sound descriptor.");
	
		return audioReferenceLevel;
	}

	@MediaPropertySetter("AudioReferenceLevel")
	public void setAudioReferenceLevel(
			Byte audioReferenceLevel) {
		
		this.audioReferenceLevel = audioReferenceLevel;
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0101, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "AudioSampleRate",
			aliases = { "AudioSamplingRate" },
			typeName = "Rational",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3D03,
			symbol = "AudioSampleRate")
	public Rational getAudioSampleRate() {

		return audioSampleRate.clone();
	}

	@MediaPropertySetter("AudioSampleRate")
	public void setAudioSampleRate(
			Rational rate)
		throws NullPointerException {

		if (rate == null)
			throw new NullPointerException("Cannot set the audio sampling rate of this sound descriptor using a null value.");
		
		this.audioSampleRate = rate.clone();
	}
	
	public final static Rational initializeAudioSampleRate() {
		
		return new RationalImpl(1, 1);
	}

	@MediaProperty(uuid1 = 0x04020101, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "ChannelCount",
			aliases = { "Channels" },
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3D07,
			symbol = "ChannelCount")
	public int getChannelCount() {

		return channelCount;
	}

	@MediaPropertySetter("ChannelCount")
	public void setChannelCount(
			int channelCount) 
		throws IllegalArgumentException {

		if (channelCount < 0)
			throw new IllegalArgumentException("Cannot set the channel count for a sound descriptor to a negative value.");
		
		this.channelCount = channelCount;
	}
	
	public final static int initializeChannelCount() {
		
		return 1;
	}

	@MediaProperty(uuid1 = 0x04020402, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "SoundCompression",
			aliases = { "Compression", "SoundDescriptorCompression" },
			typeName = "AUID",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D06,
			symbol = "SoundCompression")
	public AUID getSoundCompression()
			throws PropertyNotPresentException {

		if (soundCompression == null)
			throw new PropertyNotPresentException("The optional compression property is not present in this sound descriptor, indicating that the associated sound data is not compressed.");
		
		return soundCompression.clone();
	}

	@MediaPropertySetter("SoundCompression")
	public void setSoundCompression(
			AUID soundCompression) {

		if (soundCompression == null) {
			this.soundCompression = null;
			return;
		}
		
		this.soundCompression = soundCompression.clone();
	}

	@MediaProperty(uuid1 = 0x04020701, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "DialNorm",
			typeName = "Int8",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D0C,
			symbol = "DialNorm")
	public byte getDialNorm()
			throws PropertyNotPresentException {

		if (dialNorm == null)
			throw new PropertyNotPresentException("The optional dial normalization property is not present in this sound descriptor.");

		return dialNorm;
	}

	@MediaPropertySetter("DialNorm")
	public void setDialNorm(
			Byte dialNorm) {

		this.dialNorm = dialNorm;
	}

	@MediaProperty(uuid1 = 0x04020101, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "ElectrospatialFormulation",
			aliases = { "ElectroSpatial", "ElectroSpatialFormulation" },
			typeName = "ElectroSpatialFormulation",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D05,
			symbol = "ElectrospatialFormulation")
	public ElectroSpatialFormulation getElectrospatialFormulation()
			throws PropertyNotPresentException {

		if (electrospatialFormulation == null)
			throw new PropertyNotPresentException("The optional electro spatial formulation property is not present in this sound descriptor.");

		return electrospatialFormulation;
	}

	@MediaPropertySetter("ElectrospatialFormulation")
	public void setElectrospatialFormulation(
			ElectroSpatialFormulation formulation) {

		this.electrospatialFormulation = formulation;
	}

	@MediaProperty(uuid1 = 0x04020303, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04},
			definedName = "QuantizationBits",
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3D01,
			symbol = "QuantizationBits")
	public int getQuantizationBits() {

		return quantizationBits;
	}

	@MediaPropertySetter("QuantizationBits")
	public void setQuantizationBits(
			int bitsCount) 
		throws IllegalArgumentException {

		if (bitsCount < 0)
			throw new IllegalArgumentException("The number of quantization bits cannot be negative.");
		
		this.quantizationBits = bitsCount;
	}
	
	public final static int initializeQuantizationBits() {
		
		return 0;
	}

	public boolean isLocked()
			throws PropertyNotPresentException {

		if (locked == null)
			throw new PropertyNotPresentException("The optional locked property is not present for this sound descriptor.");

		return locked;
	}

	@MediaProperty(uuid1 = 0x04020301, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04},
			definedName = "Locked",
			typeName = "Boolean",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3D02,
			symbol = "Locked")
	public boolean getLocked() 
		throws PropertyNotPresentException {
		
		return isLocked();
	}

	@MediaPropertySetter("Locked")
	public void setIsLocked(
			Boolean locked) {

		this.locked = locked;
	}

	public SoundDescriptor clone() {
		
		return (SoundDescriptor) super.clone();
	}
	
	public String getAudioSampleRateString() {
		
		return RationalImpl.toPersistentForm(audioSampleRate);
	}
	
	public void setAudioSampleRateString(
			String audioSampleRate) {
		
		this.audioSampleRate = RationalImpl.fromPersistentForm(audioSampleRate);
	}
	
	public String getSoundCompressionString() {
		
		return AUIDImpl.toPersistentForm(soundCompression);
	}
	
	public void setSoundCompressionString(
			String soundCompression) {
		
		this.soundCompression = AUIDImpl.fromPersistentForm(soundCompression);
	}
}
