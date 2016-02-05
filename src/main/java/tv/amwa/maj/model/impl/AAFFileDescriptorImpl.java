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
 * $Log: AAFFileDescriptorImpl.java,v $
 * Revision 1.5  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.4  2011/07/27 17:33:23  vizigoth
 * Fixed imports to clear warnings.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/12/15 19:04:11  vizigoth
 * Added aliase to be closer to what MXFDump produces.
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
 * Revision 1.1  2007/11/13 22:09:40  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.model.AAFFileDescriptor;
import tv.amwa.maj.model.CodecDefinition;
import tv.amwa.maj.model.ContainerDefinition;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.RationalImpl;

/** 
 * <p>Implements a file descriptor that describes an essence source that is directly 
 * manipulated by an AAF application.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#FileDescriptorStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#FileDescriptorStrongReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2500,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "FileDescriptor",
		  aliases = { "AAFFileDescriptor" },
		  description = "The FileDescriptor class describes an essence source that is directly manipulated by an AAF application.",
		  symbol = "FileDescriptor",
		  isConcrete = false)
public class AAFFileDescriptorImpl
	extends 
		EssenceDescriptorImpl
	implements 
		AAFFileDescriptor,
		tv.amwa.maj.extensions.quantel.QAAFFileDescriptor,
		Serializable,
		Cloneable {
	
	private static final long serialVersionUID = 8332673437313654280L;
	
	private Rational sampleRate = new RationalImpl(-1, -1);
	private Long essenceLength = null;
	private boolean describesStaticEssence = false;
	private WeakReference<CodecDefinition> codec = null;
	private WeakReference<ContainerDefinition> containerFormat = null;
	private Integer linkedTrackID = null; 
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0103, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "Codec",
			aliases = { "CodecDefinition" , "FileDescriptorCodecDefinition" },
			typeName = "CodecDefinitionWeakReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3005,
			symbol = "Codec")
	public CodecDefinition getCodec() 
		throws PropertyNotPresentException {
		
		if (codec == null)
			throw new PropertyNotPresentException("The optional codec definition property is not present for this file descriptor.");
		
		return codec.getTarget();
	}

	@MediaPropertySetter("Codec")
	public void setCodec(
			CodecDefinition codec) {

		if (codec == null) {
			this.codec = null;
			return;
		}
		
		this.codec = new WeakReference<CodecDefinition>(codec);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0102, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			aliases = { "EssenceContainer" },
			definedName = "ContainerFormat",
			typeName = "ContainerDefinitionWeakReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3004,
			symbol = "ContainerFormat")
	public ContainerDefinition getContainerFormat() 
		throws PropertyNotPresentException {

		if (containerFormat == null)
			throw new PropertyNotPresentException("The optional container format property is not present in this file descriptor.");
		
		return containerFormat.getTarget();
	}

	@MediaPropertySetter("ContainerFormat")
	public void setContainerFormat(
			ContainerDefinition format) {

		if (format == null)
			this.containerFormat = null;
		else
			this.containerFormat = new WeakReference<ContainerDefinition>(format);
	}

	@MediaProperty(uuid1 = 0x04060102, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "EssenceLength",
			aliases = { "Length" , "FileDescriptorLength" },
			typeName = "LengthType", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3002,
			symbol = "EssenceLength")
	public long getEssenceLength() 
		throws BadPropertyException,
			PropertyNotPresentException {
		
		if (essenceLength == null)
			throw new PropertyNotPresentException("The optional essence length property is not present for this file descriptor.");
		
		if (describesStaticEssence == true)
			throw new BadPropertyException("The length property is not present for the static essence described by this file descriptor.");
		
		return essenceLength;
	}

	public void setEssenceLength(
			Long essenceLength) 
		throws BadPropertyException,
			BadLengthException {
		
		if (essenceLength == null) {
			this.essenceLength = null;
			return;
		}
		
		if (describesStaticEssence == true)
			throw new BadPropertyException("The length property is not present for the static essence described by this file descriptor.");
		if (essenceLength < 0l)
			throw new BadLengthException("The length of the described material cannot be negative.");
		
		this.essenceLength = essenceLength;
	}

	@MediaPropertySetter("EssenceLength")
	public void setEssenceLengthFromStream(
			long essenceLength) 
		throws BadLengthException {
		
		if (essenceLength < 0l)
			throw new BadLengthException("The length of the described material cannot be negative.");

		describesStaticEssence = false;
		this.essenceLength = essenceLength;
	}
	
	
	public final static long initializeEssenceLength() {
		
		return 0l;
	}

	@MediaProperty(uuid1 = 0x04060101, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "SampleRate",
			aliases = { "FileDescriptorSampleRate" },
			typeName = "Rational", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3001,
			symbol = "SampleRate")
	public Rational getSampleRate() 
		throws BadPropertyException {

		if (describesStaticEssence == true)
			throw new BadPropertyException("The sample rate property is not present as the file descriptor describes static essence.");
		
		return sampleRate.clone();
	}

	public void setSampleRate(
			Rational rate)
		throws NullPointerException,
			BadPropertyException {

		if (describesStaticEssence == true)
			throw new BadPropertyException("Cannot set the sample rate for this file descriptor as it describes static essence.");
		
		if (rate == null)
			throw new NullPointerException("Cannot set the sample rate for this file descriptor using a null value.");
		
		this.sampleRate = rate.clone();	
	}
	
	public final static Rational initializeSampleRate() {
		
		return new RationalImpl(1, 1);
	}
	
	@MediaPropertySetter("SampleRate")
	public void setSampleRateFromStream(
			Rational rate) {
		
		describesStaticEssence = false;
		
		if (rate == null)
			throw new NullPointerException("Cannot set the sample rate for this file descriptor using a null value.");
		
		this.sampleRate = rate.clone();	
	}
	
	/**
	 * <p>Determines whether this file descriptor describes static or time-varying essence. This method
	 * returns <code>true</code> if static essence is described and <code>false</code> if time-varying
	 * essence is described. If the file descriptor describes static essence then the 
	 * {@link #getSampleRate() sample rate} and {@link #getEssenceLength() length} properties are not
	 * present.</p>
	 *
	 * @return Does this file descriptor describe static essence?
	 */
	public boolean describesStaticEssence() {
		
		return describesStaticEssence;
	}
	
	/**
	 * <p>Sets this file descriptor so that it describes static essence.</p>
	 *
	 */
	public void setDescribesStaticEssence() {
	
		this.describesStaticEssence = true;
	}
	
	/**
	 * <p>Sets this file descriptor so that it describes time varying essence. In this state, the
	 * {@link #getSampleRate() sample rate} and {@link #getEssenceLength() length} properties are required
	 * and so must be provided to the call to this method.</p>
	 *
	 * @param sampleRate Sample rate for this file descriptor.
	 * @param length Length of the material represented by this file descriptor.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws BadLengthException The length of the described material is negative.
	 */
	public void setDescribesTimeVaryingEssence(
			tv.amwa.maj.record.Rational sampleRate,
			long length) 
		throws NullPointerException,
			BadLengthException {
		
		this.describesStaticEssence = false;
		
		try {
			setEssenceLength(length);
			setSampleRate(sampleRate);
		}
		catch (BadPropertyException bpe) { /* Not thrown for time varying essence. */ }
	}
	
	@MediaProperty(uuid1 = 0x06010103, uuid2 = 0x0500, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x05},
			     definedName = "LinkedTrackID",
			     aliases = { "LinkedSlotID" },
			     typeName = "UInt32",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3006,
			     symbol = "LinkedTrackID")	
	public int getLinkedTrackID() 
		throws PropertyNotPresentException {
		
		if (linkedTrackID == null)
			throw new PropertyNotPresentException("The optional linked track id property is not present in this file descriptor.");
		
		return linkedTrackID;
	}
		
	@MediaPropertySetter("LinkedTrackID")
	public void setLinkedTrackID(
			Integer linkedTrackID)
		throws IllegalArgumentException {
		
		if (linkedTrackID == null) {
			this.linkedTrackID = null;
			return;
		}
		
		if (linkedTrackID < 0)
			throw new IllegalArgumentException("The linked track id cannot be a negative value.");
		
		this.linkedTrackID = linkedTrackID;
	}
	
	// Begin - Quantel extensions
	
	private Integer twentyFourBitAudio = null; 

    @MediaProperty(uuid1 = 0x7bea1710, uuid2 = (short) 0xda09, uuid3 = (short) 0x49e3,
        uuid4 = { (byte) 0x9e, (byte) 0x78, (byte) 0xfd, (byte) 0x2d, (byte) 0xab, (byte) 0x16, (byte) 0x6e, (byte) 0xa2 },
        definedName = "24 bit audio",
        symbol = "_24_bit_audio",
        aliases = { "_4_bit_audio", "_24_bit_audio", "twentyFourBitAudio" },
        typeName = "Int32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @Int32 int getTwentyFourBitAudio()
		throws PropertyNotPresentException {
		
		if (twentyFourBitAudio == null)
			throw new PropertyNotPresentException("The optional 24 bit audio property is not predent in this Quantel file descriptor.");
		
		return twentyFourBitAudio;
	}
	
	@MediaPropertySetter("24 bit audio")
	public void setTwentyFourBitAudio(
			@Int32 Integer twentyFourBitAudio) {
		
		this.twentyFourBitAudio = twentyFourBitAudio;
	}
	
	// End = Quantel extensions
	
	public AAFFileDescriptor clone() {
		
		return (AAFFileDescriptor) super.clone();
	}
	
	public String getSampleRateString() {
		
		return RationalImpl.toPersistentForm(sampleRate);
	}
	
	public void setSampleRateString(
			String sampleRate) {
		
		this.sampleRate = RationalImpl.fromPersistentForm(sampleRate);
	}
}
