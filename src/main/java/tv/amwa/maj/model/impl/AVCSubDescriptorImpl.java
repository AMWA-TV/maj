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

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.AVCCodedContentKind;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.AVCSubDescriptor;

/**
 * <p>Implements an AVC sub descriptor that provides AVC-specific properties that provide additional description of
 * AVC-encoded material over that contained in a {@linkplain tv.amwa.maj.model.RGBADescriptor RGBA descriptor} or a
 * {@linkplain tv.amwa.maj.model.CDCIDescriptor CDCI descriptor}.</p>
 *
 *
 */

@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x6e00,
		uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		definedName = "AVCSubDescriptor",
		description = "AVC-specific properties that provide additional description of AVC-encoded material.",
		symbol = "AVCSubDescriptor")
public class AVCSubDescriptorImpl
  extends SubDescriptorImpl
  implements
		AVCSubDescriptor,
		Serializable,
		XMLSerializable,
		Cloneable {

	private byte avcDecodingDelay;
	private Boolean avcConstantBPictureFlag = null;
	private AVCCodedContentKind avcCodedContentKind = null;
	private Boolean avcClosedGOPIndicator = null;
	private Boolean avcIdenticalGOPIndicator = null;
	private Short avcMaximumGOPSize = null;
	private Short avcMaximumBPictureCount = null;
	private Integer avcMaximumBitrate = null;
	private Integer avcAverageBitrate = null;
	private Byte avcProfile = null;
	private Byte avcProfileConstraint = null;
	private Byte avcLevel = null;
	private Byte avcMaximumRefFrames = null;
	private Byte avcSequenceParameterSetFlag = null;
	private Byte avcPictureParameterSetFlag = null;

	private static final long serialVersionUID = -4823795093498901279L;

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x010e, uuid3 = 0x0000,
    		uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
    		definedName = "AVCDecodingDelay",
    		typeName = "UInt8",
    		optional = false,
    		uniqueIdentifier = false,
    		pid = 0x0000,
    		symbol = "AVCDecodingDelay")
	@Override
	public @UInt8 byte getAVCDecodingDelay() {

		return avcDecodingDelay;
	}

	@MediaPropertySetter("AVCDecodingDelay")
	@Override
	public void setAVCDecodingDelay(
			@UInt8 byte avcDecodingDelay)
		throws IllegalArgumentException {

		if (avcDecodingDelay < 0)
			throw new IllegalArgumentException("Cannot set the AVC decoding delay to a negative value.");

		this.avcDecodingDelay = avcDecodingDelay;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0103, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCConstantBPictureFlag",
		     typeName = "Boolean",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCConstantBPictureFlag")
	@Override
	public boolean getAVCConstantBPictureFlag() {

		if (avcConstantBPictureFlag == null) return AVCCONSTANTBPICTUREFLAG_DEFAULT;
		else return avcConstantBPictureFlag;
	}

	@MediaPropertySetter("AVCConstantBPictureFlag")
	@Override
	public void setAVCConstantBPictureFlag(
			Boolean avcConstantBPictureFlag) {

		this.avcConstantBPictureFlag = avcConstantBPictureFlag;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0104, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCCodedContentKind",
		     typeName = "AVCCodedContentKind",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCCodedContentKind")
	@Override
	public AVCCodedContentKind getAVCCodedContentKind() {

		if (avcCodedContentKind == null) return AVCCODECCONTENTKIND_DEFAULT;
		else return avcCodedContentKind;
	}

	@MediaPropertySetter("AVCCodedContentKind")
	@Override
	public void setAVCCodedContentKind(
			AVCCodedContentKind avcCodedContentKind) {

		this.avcCodedContentKind = avcCodedContentKind;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0106, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCClosedGOPIndicator",
		     typeName = "Boolean",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCClosedGOPIndicator")
	@Override
	public boolean getAVCClosedGOPIndicator() {

		if (avcClosedGOPIndicator == null) return AVCCLOSEDGOPINDICATOR_DEFAULT;
		else return avcClosedGOPIndicator;
	}

	@MediaPropertySetter("AVCClosedGOPIndicator")
	@Override
	public void setAVCClosedGOPIndicator(
			Boolean avcClosedGOPIndicator) {

		this.avcClosedGOPIndicator = avcClosedGOPIndicator;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0107, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCIdenticalGOPIndicator",
		     typeName = "Boolean",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCIdenticalGOPIndicator")
	@Override
	public boolean getAVCIdenticalGOPIndicator() {

		if (avcIdenticalGOPIndicator == null) return AVCIDENTICALGOPINDICATOR_DEFAULT;
		else return avcIdenticalGOPIndicator;
	}

	@MediaPropertySetter("AVCIdenticalGOPIndicator")
	@Override
	public void setAVCIdenticalGOPIndicator(
			Boolean avcIdenticalGOPIndicator) {

		this.avcIdenticalGOPIndicator = avcIdenticalGOPIndicator;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0108, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCMaximumGOPSize",
		     typeName = "UInt16",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCMaximumGOPSize")
	@Override
	public @UInt16 short getAVCMaximumGOPSize()
			throws PropertyNotPresentException {

		if (avcMaximumGOPSize == null)
			throw new PropertyNotPresentException("The optional maximum GOP size property is not present for this AVC sub descriptor.");

		return avcMaximumGOPSize;
	}

	@MediaPropertySetter("AVCMaximumGOPSize")
	@Override
	public void setAVCMaximumGOPSize(
			@UInt16 Short avcMaximumGOPSize)
		throws IllegalArgumentException {

		if (avcMaximumGOPSize == null) {
			this.avcMaximumGOPSize = null;
			return;
		}

		if (avcMaximumGOPSize < 0)
			throw new IllegalArgumentException("Cannot set the maximum GOP size property to a negative value.");
		this.avcMaximumGOPSize = avcMaximumGOPSize;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0109, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCMaximumBPictureCount",
		     typeName = "UInt16",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCMaximumBPictureCount")
	@Override
	public @UInt16 short getAVCMaximumBPictureCount()
			throws PropertyNotPresentException {

		if (avcMaximumBPictureCount == null)
			throw new PropertyNotPresentException("The optional maximum B picture count property is not present.");

		return avcMaximumBPictureCount;
	}

	@MediaPropertySetter("AVCMaximumBPictureCount")
	@Override
	public void setAVCMaximumBPictureCount(
			@UInt16 Short avcMaximumBPictureCount)
		throws IllegalArgumentException {

		if (avcMaximumBPictureCount == null) {
			this.avcMaximumBPictureCount = null;
			return;
		}

		if (avcMaximumBPictureCount < 0)
			throw new IllegalArgumentException("Cannot set the maximum B picture count property to a negative value.");
		this.avcMaximumBPictureCount = avcMaximumBPictureCount;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x010b, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCMaximumBitrate",
		     typeName = "UInt32",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCMaximumBitrate")
	@Override
	public @UInt32 int getAVCMaximumBitrate()
			throws PropertyNotPresentException {

		if (avcMaximumBitrate == null)
			throw new PropertyNotPresentException("The optional maximum bit rate property is not present for this AVC sub descriptor.");
		return avcMaximumBitrate;
	}

	@MediaPropertySetter("AVCMaximumBitrate")
	@Override
	public void setAVCMaximumBitrate(
			@UInt32 Integer avcMaximumBitrate)
		throws IllegalArgumentException {

		if (avcMaximumBitrate == null) {
			this.avcMaximumBitrate = null;
			return;
		}

		if (avcMaximumBitrate < 0)
			throw new IllegalArgumentException("Cannot set the maximum bit rate property to a negative value.");
		this.avcMaximumBitrate = avcMaximumBitrate;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0114, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCAverageBitrate",
		     typeName = "UInt32",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCAverageBitrate")
	@Override
	public @UInt32 int getAVCAverageBitrate()
			throws PropertyNotPresentException {

		if (avcAverageBitrate == null)
			throw new PropertyNotPresentException("The optional average bit rate property is not present for this AVC sub descriptor.");

		return avcAverageBitrate;
	}

	@MediaPropertySetter("AVCAverageBitrate")
	@Override
	public void setAVCAverageBitrate(
			@UInt32 Integer avcAverageBitrate)
		throws IllegalArgumentException {

		if (avcAverageBitrate == null) {
			this.avcAverageBitrate = null;
			return;
		}

		if (avcAverageBitrate < 0)
			throw new IllegalArgumentException("Cannot set the average bitrate property to a negative value.");
		this.avcAverageBitrate = avcAverageBitrate;
	}
	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x010a, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCProfile",
		     typeName = "UInt8",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCProfile")
	@Override
	public @UInt8 byte getAVCProfile()
			throws PropertyNotPresentException {

		if (avcProfile == null)
			throw new PropertyNotPresentException("The optional AVC profile property is not present for this AVC sub descriptor.");

		return avcProfile;
	}

	@MediaPropertySetter("AVCProfile")
	@Override
	public void setAVCProfile(
			@UInt8 Byte avcProfile) {

		this.avcProfile = avcProfile;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x010c, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCProfileConstraint",
		     typeName = "UInt8",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCProfileConstraint")
	@Override
	public @UInt8 byte getAVCProfileConstraint()
			throws PropertyNotPresentException {

		if (avcProfileConstraint == null)
			throw new PropertyNotPresentException("The optional AVC profile constraint property is not present for this AVC sub descriptor.");

		return avcProfileConstraint;
	}

	@MediaPropertySetter("AVCProfileConstraint")
	@Override
	public void setAVCProfileConstraint(
			@UInt8 Byte avcProfileConstraint) {

		this.avcProfileConstraint = avcProfileConstraint;
	}

	@Override
	public void setAVCProfileContraint(
			boolean constraint_set0_flag,
			boolean constraint_set1_flag,
			boolean constraint_set2_flag,
			boolean constraint_set3_flag,
			boolean constraint_set4_flag,
			boolean constraint_set5_flag) {

		this.avcProfileConstraint = (byte) ((constraint_set0_flag ? 0x80 : 0x00) |
				(constraint_set1_flag ? 0x40 : 0x00) |
				(constraint_set2_flag ? 0x20 : 0x00) |
				(constraint_set3_flag ? 0x10 : 0x00) |
				(constraint_set4_flag ? 0x08 : 0x00) |
				(constraint_set5_flag ? 0x04 : 0x00));
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x010d, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCLevel",
		     typeName = "UInt8",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCLevel")
	@Override
	public @UInt8 byte getAVCLevel()
		throws PropertyNotPresentException {

		if (avcLevel == null)
			throw new PropertyNotPresentException("The optional AVC level property is not present for this AVC sub descriptor.");

		return this.avcLevel;
	}

	@MediaPropertySetter("AVCLevel")
	@Override
	public void setAVCLevel(
			@UInt8 Byte avcLevel)
		throws IllegalArgumentException {

		if (avcLevel == null) {
			this.avcLevel = null;
			return;
		}

		if (avcLevel < 0)
			throw new IllegalArgumentException("The AVC level property cannot be set to a negative value.");
		this.avcLevel = avcLevel;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x010f, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCMaximumRefFrames",
		     typeName = "UInt8",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCMaximumRefFrames")
	@Override
	public @UInt8 byte getAVCMaximumRefFrames()
			throws PropertyNotPresentException {

		if (avcMaximumRefFrames == null)
			throw new PropertyNotPresentException("The optional maximum reference frames property is not present for this AVC sub descriptor.");

		return avcMaximumRefFrames;
	}

	@MediaPropertySetter("AVCMaximumRefFrames")
	@Override
	public void setAVCMaximumRefFrames(
			@UInt8 Byte avcMaximumRefFrames)
		throws IllegalArgumentException {

		if (avcMaximumRefFrames == null) {
			this.avcMaximumRefFrames = null;
			return;
		}

		if (avcMaximumRefFrames < 0)
			throw new IllegalArgumentException("The number of maximum reference frames cannot be negative.");
		this.avcMaximumRefFrames = avcMaximumRefFrames;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0110, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCSequenceParameterSetFlag",
		     typeName = "UInt8",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCSequenceParameterSetFlag")
	@Override
	public @UInt8 byte getAVCSequenceParameterSetFlag() {

		if (avcSequenceParameterSetFlag == null) return AVCSEQUENCEPARAMETERSETFLAG_DEFAULT;
		else return avcSequenceParameterSetFlag;
	}

	@MediaPropertySetter("AVCSequenceParameterSetFlag")
	@Override
	public void setAVCSequenceParameterSetFlag(
			@UInt8 Byte avcSequenceParameterSetFlag) {

		this.avcSequenceParameterSetFlag = avcSequenceParameterSetFlag;
	}

	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0111, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCPictureParameterSetFlag",
		     typeName = "UInt8",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCPictureParameterSetFlag")
	@Override
	public @UInt8 byte getAVCPictureParameterSetFlag() {

		if (avcPictureParameterSetFlag == null) return AVCPICTUREPARAMETERSETFLAG_DEFAULT;
		else return avcPictureParameterSetFlag;
	}

	@MediaPropertySetter("AVCPictureParameterSetFlag")
	@Override
	public void setAVCPictureParameterSetFlag(
			@UInt8 Byte avcPictureParaemterSetFlag) {

		this.avcPictureParameterSetFlag = avcPictureParaemterSetFlag;
	}

	public AVCSubDescriptor clone() {

		return (AVCSubDescriptor) super.clone();
	}
}
