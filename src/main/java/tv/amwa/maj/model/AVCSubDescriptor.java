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

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.AVCCodedContentKind;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;

/**
 * <p>AVC-specific properties that provide additional description of AVC-encoded material over that contained in
 * a {@linkplain RGBADescriptor RGBA descriptor} or a {@linkplain CDCIDescriptor CDCI descriptor}.</p>
 *
 *
 */
public interface AVCSubDescriptor
	extends SubDescriptor {

	/**
	 * <p>Default value for the AVC constant B&nbsp;Picture flag, which
	 * is&nbsp;<code>{@value #AVCCONSTANTBPICTUREFLAG_DEFAULT}</code>.</p>
	 *
	 * @see #getAVCConstantBPictureFlag()
	 * @see #setAVCConstantBPictureFlag(Boolean)
	 */
	public final static boolean AVCCONSTANTBPICTUREFLAG_DEFAULT = false;

	/**
	 * <p>Default value for the picture and coding kind, which is {@value #AVCCODECCONTENTKIND_DEFAULT}.</p>
	 *
	 * @see #getAVCCodedContentKind()
	 * @see #setAVCCodedContentKind(AVCCodedContentKind)
	 */
	public final static AVCCodedContentKind AVCCODECCONTENTKIND_DEFAULT = AVCCodedContentKind.Unknown;

	/**
	 * <p>Default value for the AVC closed GOP indicator, which is {@value #AVCCLOSEDGOPINDICATOR_DEFAULT}.</p>
	 *
	 * @see #getAVCClosedGOPIndicator()
	 * @see #setAVCClosedGOPIndicator(Boolean)
	 */
	public final static boolean AVCCLOSEDGOPINDICATOR_DEFAULT = false;

	/**
	 * <p>Default value for the AVC identical GOP indicator, which us {@value #AVCIDENTICALGOPINDICATOR_DEFAULT}.</p>
	 *
	 * @see #getAVCIdenticalGOPIndicator()
	 * @see #setAVCIdenticalGOPIndicator(Boolean)
	 */
	public final static boolean AVCIDENTICALGOPINDICATOR_DEFAULT = false;

	/**
	 * <p>Default value for the sequence parameter set flag, which is {@value #AVCSEQUENCEPARAMETERSETFLAG_DEFAULT} and means
	 * the the locations of sequence parameter sets are unknown.</p>
	 *
	 * @see #getAVCSequenceParameterSetFlag()
	 * @see #setAVCSequenceParameterSetFlag(Byte)
	 */
	public final static byte AVCSEQUENCEPARAMETERSETFLAG_DEFAULT = 0;

	/**
	 * <p>Default value for the picture parameter set flag, which is {@value #AVCPICTUREPARAMETERSETFLAG_DEFAULT} and means
	 * the locations of picture parameter sets are unknown.</p>
	 *
	 * @see #getAVCPictureParameterSetFlag()
	 * @see #setAVCPictureParameterSetFlag(Byte)
	 */
	public final static byte AVCPICTUREPARAMETERSETFLAG_DEFAULT = 0;

	/**
	 * <p>The constancy of a parameter set is now known.</p>
	 *
	 * @see #CONSTANCYFLAG_CONSTANT
	 * @see #setAVCSequenceParameterSetFlag(Byte)
	 * @see #setAVCPictureParameterSetFlag(Byte)
	 */
	public final static byte CONSTANCYFLAG_UNKNOWN = 0x00;

	/**
	 * <p>The constancy of a parameter set is constant.</p>
	 *
	 * @see #CONSTANCYFLAG_CONSTANT
	 * @see #setAVCSequenceParameterSetFlag(Byte)
	 * @see #setAVCPictureParameterSetFlag(Byte)
	 */
	public final static byte CONSTANCYFLAG_CONSTANT = (byte) 0x80;

	/**
	 * <p>The location of a kind of parameter set within the stream is unknown or has no specific location.</p>
	 *
	 * @see #INBANDLOCATION_FIRSTACCESSUNIT
	 * @see #INBANDLOCATION_EVERYACCESSUNIT
	 * @see #INBANDLOCATION_EVERYGOP
	 * @see #setAVCSequenceParameterSetFlag(Byte)
	 * @see #setAVCPictureParameterSetFlag(Byte)
	 */
	public final static byte INBANDLOCATION_UNKNOWN = (byte) 0x00;

	/**
	 * <p>The location of parameter set within the stream is only as part of the first access unit.</p>
	 *
	 * @see #INBANDLOCATION_UNKNOWN
	 * @see #INBANDLOCATION_EVERYACCESSUNIT
	 * @see #INBANDLOCATION_EVERYGOP
	 * @see #setAVCSequenceParameterSetFlag(Byte)
	 * @see #setAVCPictureParameterSetFlag(Byte)
	 */
	public final static byte INBANDLOCATION_FIRSTACCESSUNIT = (byte) 0x10;

	/**
	 * <p>The location of a kind of parameter set within the stream is as part of every access unit.</p>
	 *
	 * @see #INBANDLOCATION_UNKNOWN
	 * @see #INBANDLOCATION_FIRSTACCESSUNIT
	 * @see #INBANDLOCATION_EVERYGOP
	 * @see #setAVCSequenceParameterSetFlag(Byte)
	 * @see #setAVCPictureParameterSetFlag(Byte)
	 */
	public final static byte INBANDLOCATION_EVERYACCESSUNIT = (byte) 0x20;

	/**
	 * <p>The location of a kind of parameter set within the stream is periodically placed at the first access
	 * unit in each GOP.</p>
	 *
	 * @see #INBANDLOCATION_UNKNOWN
	 * @see #INBANDLOCATION_FIRSTACCESSUNIT
	 * @see #INBANDLOCATION_EVERYACCESSUNIT
	 * @see #setAVCSequenceParameterSetFlag(Byte)
	 * @see #setAVCPictureParameterSetFlag(Byte)
	 */
	public final static byte INBANDLOCATION_EVERYGOP = (byte) 0x30;

	/**
	 * <p>Returns the delay required for decoded pictures in number of access units.
	 * The value is given by the presentation time of the first presented picture in a GOP minus the
	 * decoding time of the first decoded picture in the GOP. The value shall be set to zero if there are
	 * no B Pictures in the essence stream. The value shall be set to FFh if the delay is unknown.</p>
	 *
	 * @return Delay required for decoded pictures in number of access units.
	 */
	public @UInt8 byte getAVCDecodingDelay();

	/**
	 * <p>Sets the delay required for decoded pictures in number of access units.
	 * The value is given by the presentation time of the first presented picture in a GOP minus the
	 * decoding time of the first decoded picture in the GOP. The value shall be set to zero if there are
	 * no B Pictures in the essence stream. The value shall be set to FFh if the delay is unknown.</p>
	 *
	 * @param avcDecodingDelay Delay required for decoded pictures in number of access units.
	 *
	 * @throws IllegalArgumentException The given decoding delay access unit count cannot be a negative number, unless it is 0xff
	 * to indicate an unknown decoding delay.
	 */
	public void setAVCDecodingDelay(
			@UInt8 byte avcDecodingDelay)
		throws IllegalArgumentException;

	/**
	 * <p>Returns whether the number of consecutive B&nbsp;Pictures always constant? Set to false if the number of consecutive
	 * B&nbsp;Pictures is not constant or is unknown. If this optional property is not present, its default value of
	 * {@value #AVCCONSTANTBPICTUREFLAG_DEFAULT} is returned.</p>
	 *
	 * @return <code>true</code> if the number of consecutive B&nbsp;Pictures always constant, otherwise <code>false</code>.
	 *
	 * @see #AVCCONSTANTBPICTUREFLAG_DEFAULT
	 */
	public boolean getAVCConstantBPictureFlag();

	/**
	 * <p>Sets whether the number of consecutive B&nbsp;Pictures always constant? Set to false if the number of consecutive
	 * B&nbsp;Pictures is not constant or is unknown. Set this optional property to <code>null</code> to omit it and cause the
	 * default value of {@value #AVCCONSTANTBPICTUREFLAG_DEFAULT} to be returned in subsequent requests.</p>
	 *
	 * @param avcConstantBPictureFlag <code>true</code> if the number of consecutive B&nbsp;Pictures always constant, otherwise
	 * <code>false</code>.
	 *
	 * @see #AVCCONSTANTBPICTUREFLAG_DEFAULT
	 */
	public void setAVCConstantBPictureFlag(
			Boolean avcConstantBPictureFlag);

	/**
	 * <p>Returns the picture type and coding type. If this optional property is omitted, the default value of
	 * {@value #AVCCODECCONTENTKIND_DEFAULT} is returned.</p>
	 *
	 * @return Picture type and coding type.
	 *
	 * @see #AVCCODECCONTENTKIND_DEFAULT
	 */
	public AVCCodedContentKind getAVCCodedContentKind();

	/**
	 * <p>Sets the picture type and coding type. Set this optional property to <code>null</code> to omit it,
	 * which will cause the default value of {@value #AVCCODECCONTENTKIND_DEFAULT} to be returned going forward.</p>
	 *
	 * @param avcCodedContentKind Picture type and coding type.
	 *
	 * @see #AVCCODECCONTENTKIND_DEFAULT
	 */
	public void setAVCCodedContentKind(
			AVCCodedContentKind avcCodedContentKind);

	/**
	 * <p>Returns whether all GOPs start with an IDR&nbsp;Picture. If this optional property is omitted, the default value
	 * of {@value #AVCCLOSEDGOPINDICATOR_DEFAULT} is returned.</p>
	 *
	 * @return <code>true</code> if all GOPs start with an IDR&nbsp;Picture, otherwise <code>false</code> if the GOP start type is
	 * not necessarily an IDR&nbsp;Picture or is unknown.
	 *
	 * @see #AVCCLOSEDGOPINDICATOR_DEFAULT
	 */
	public boolean getAVCClosedGOPIndicator();

	/**
	 * <p>Sets whether all GOPs start with an IDR&nbsp;Picture. Set this optional property to <code>null</code> null to
	 * omit it, causing the default value of {@value #AVCCLOSEDGOPINDICATOR_DEFAULT} to be returned in subsequent requests.</p>
	 *
	 * @param avcClosedGOPIndicator <code>true</code> if all GOPs start with an IDR&nbsp;Picture, otherwise <code>false</code> if the
	 * GOP start type is not necessarily an IDR&nbsp;Picture or is unknown.
	 *
	 * @see #AVCCLOSEDGOPINDICATOR_DEFAULT
	 */
	public void setAVCClosedGOPIndicator(
			Boolean avcClosedGOPIndicator);

	/**
	 * <p>Returns whether every GOP in the sequence has the same number of pictures and the same type of pictures in the same
	 * order. This is not the case is not all GOPs are known, of there is at least one GOP in the sequence which has any of the
	 * following:</p>
	 *
	 * <ol>
	 *  <li>a different number of pictures;</li>
	 *  <li>the same types of pictures but in a different order;</li>
	 *  <li>different types of pictures.</li>
	 * </ol>
	 *
	 * <p>If this optional property is omitted, the default value of {@value #AVCIDENTICALGOPINDICATOR_DEFAULT} is returned.</p>
	 *
	 * @return <code>true</code> if every GOP in the sequence has the same number of pictures and the same types of pictures in
	 * the same order, otherwise <code>false</code>.
	 *
	 * @see #AVCIDENTICALGOPINDICATOR_DEFAULT
	 */
	public boolean getAVCIdenticalGOPIndicator();

	/**
	 * <p>Sets whether every GOP in the sequence has the same number of pictures and the same type of pictures in the same
	 * order. This is not the case is not all GOPs are known, of there is at least one GOP in the sequence which has any of the
	 * following:</p>
	 *
	 * <ol>
	 *  <li>a different number of pictures;</li>
	 *  <li>the same types of pictures but in a different order;</li>
	 *  <li>different types of pictures.</li>
	 * </ol>
	 *
	 * <p>Set this optional property to <code>null</code> to omit it, which will result in the default value of
	 * {@value #AVCIDENTICALGOPINDICATOR_DEFAULT} being returned in subsequence requests.</p>
	 *
	 * @param avcIdenticalGOPIndicator <code>true</code> if every GOP in the sequence has the same number of pictures and the same types of pictures in
	 * the same order, otherwise <code>false</code>.
	 */
	public void setAVCIdenticalGOPIndicator(
			Boolean avcIdenticalGOPIndicator);

	/**
	 * <p>Returns the maximum occurring spacing between I&nbsp;Pictures. A value of <code>0</code> or the omission of this property indicates
	 * either there is no limit to the maximum GOP or the maximum GOP is unknown. If an encoder does not know the value, this optional property
	 * shall be omitted.</p>
	 *
	 * @return Maximum occurring spacing between I&nbsp;Pictures.
	 * @throws PropertyNotPresentException The optional AVC maximum GOP size property is not present.
	 */
	public @UInt16 short getAVCMaximumGOPSize()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the maximum occurring spacing between I&nbsp;Pictures. A value of <code>0</code> or the omission of this property indicates
	 * either there is no limit to the maximum GOP or the maximum GOP is unknown. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 *
	 * @param avcMaximumGOPSize Maximum occurring spacing between I&nbsp;Pictures.
	 *
	 * @throws IllegalArgumentException The maximum GOP size cannot be a negative number.
	 */
	public void setAVCMaximumGOPSize(
			@UInt16 Short avcMaximumGOPSize)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the maximum number of B&nbspPictures between P or I&nbsp;Pictures. If an encoder does not know the value,
	 * this optional property shall be omitted.</p>
	 *
	 * @return Maximum number of B&nbspPictures between P or I&nbsp;Pictures.
	 *
	 * @throws PropertyNotPresentException The optional maximum B&nbsp;Picture count property is not present.
	 */
	public @UInt16 short getAVCMaximumBPictureCount()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the maximum number of B&nbspPictures between P or I&nbsp;Pictures. If an encoder does not know the value,
	 * this optional property shall be omitted by setting the value to <code>null</code>.</p>
	 *
	 * @param avcMaximumBPictureCount Maximum number of B&nbspPictures between P or I&nbsp;Pictures.
	 *
	 * @throws IllegalArgumentException Cannot set the maximum B&nbsp;Picture count property to a negative value.
	 */
	public void setAVCMaximumBPictureCount(
			@UInt16 Short avcMaximumBPictureCount)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the maximum bit rate of the AVC stream in bit/s as given by <code>bit_rate_scale</code> and
	 * <code>bit_rate_value_minus1</code> in the HRD parameters in the sequence parameter set. The equivalent value is
	 * assigned for this property of the stream even if the stream does not include the HRD parameters. If an encoder
	 * does not know the optional value, this property shall be omitted.</p>
	 *
	 * <p>To calculate the bitrate from the sequence parameter set values, do:</p>
	 *
	 * <center>(<code>bit_rate_value_minus1</code> + 1) * 2<sup>(6 + <code>bit_rate_scale</code>)</sup></center>
	 *
	 * @return Maximum bit rate of the AVC stream in bit/s.
	 *
	 * @throws PropertyNotPresentException The optional maximum bit rate property is not present.
	 */
	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x010b, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCMaximumBitrate",
		     typeName = "UInt32",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCMaximumBitrate")
	public @UInt32 int getAVCMaximumBitrate()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the maximum bit rate of the AVC stream in bit/s as given by <code>bit_rate_scale</code> and
	 * <code>bit_rate_value_minus1</code> in the HRD parameters in the sequence parameter set. The equivalent value is
	 * assigned for this property of the stream even if the stream does not include the HRD parameters. If an encoder
	 * does not know the optional value, set this property to <code>null</code> to omit it.</p>
	 *
	 * <p>To calculate the bitrate from the sequence parameter set values, do:</p>
	 *
	 * <center>(<code>bit_rate_value_minus1</code> + 1) * 2<sup>(6 + <code>bit_rate_scale</code>)</sup></center>
	 *
	 * @param avcMaximumBitrate Maximum bit rate of the AVC stream in bit/s.
	 *
	 * @throws IllegalArgumentException The maximum bit rate of the stream cannot be a negative value.
	 */
	public void setAVCMaximumBitrate(
			@UInt32 Integer avcMaximumBitrate)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the average bit rate of the AVC stream in bit/s over the entire AVC bitstream. If an encoder does not know
	 * the value, this optional property shall be omitted.</p>
	 *
	 * @return Average bit rate of the AVC stream in bit/s over the entire AVC bitstream.
	 *
	 * @throws PropertyNotPresentException The optional average bit rate property is not present.
	 */
	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0114, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCAverageBitrate",
		     typeName = "UInt32",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCAverageBitrate")
	public @UInt32 int getAVCAverageBitrate()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the average bit rate of the AVC stream in bit/s over the entire AVC bitstream. If an encoder does not know
	 * the value, omit this optional property be setting it to <code>null</code>.</p>
	 *
	 * @param avcAverageBitrate Average bit rate of the AVC stream in bit/s over the entire AVC bitstream.
	 *
	 * @throws IllegalArgumentException Cannot set the average bit rate property to a negative value.
	 */
	public void setAVCAverageBitrate(
			@UInt32 Integer avcAverageBitrate)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the AVC video profile. The value is taken from <code>profile_idc</code> in the sequence parameter set.
	 * This is an optional property.</p>
	 *
	 * @return AVC video profile.
	 *
	 * @throws PropertyNotPresentException The optional AVC profile property is not present.
	 */
	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x010a, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCProfile",
		     typeName = "UInt8",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCProfile")
	public @UInt8 byte getAVCProfile()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the AVC video profile. The value is taken from <code>profile_idc</code> in the sequence parameter set.
	 * Set this optional property to <code>null</code> to omit it.</p>
	 *
	 * @param setAVCProfile AVC video profile.
	 */
	public void setAVCProfile(
			@UInt8 Byte avcProfile); // Can be 128 or greater.

	/**
	 * <p>Returns the AVC video profile constraint flags. The value of bits [7..2] is taken from <code>constraint_set0_flag</code>, ...,
	 * <code>constraint_set5_flag</code> in the sequence parameter set. The value of bits [1..0] shall be set to zero. This
	 * is an optional property.</p>
	 *
	 * @return AVC video profile constraint flags.
	 *
	 * @throws PropertyNotPresentException The optional AVC video profile constraint flags are not present.
	 */
	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x010c, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCProfileConstraint",
		     typeName = "UInt8",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCProfileConstraint")
	public @UInt8 byte getAVCProfileConstraint()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the AVC video profile constraint flags. The value of bits [7..2] is taken from <code>constraint_set0_flag</code>, ...,
	 * <code>constraint_set5_flag</code> in the sequence parameter set. The value of bits [1..0] shall be set to zero. Set this
	 * optional property to <code>null</code> to omit it.</p>
	 *
	 * @param avcProfileConstraint AVC video profile constraint flags.
	 *
	 * @see #setAVCProfileContraint(boolean, boolean, boolean, boolean, boolean, boolean)
	 */
	public void setAVCProfileConstraint(
			@UInt8 Byte avcProfileConstraint); // No IllegalArgumentException as top bit represents a constraint flag

	/**
	 * <p>Sets the AVC video profile constraint flags. The value, stored in bits [7..2] of an unsigned byte, is taken
	 * from <code>constraint_set0_flag</code>, ..., <code>constraint_set5_flag</code> in the sequence parameter set.
	 * Use {@link #setAVCProfileConstraint(Byte)} with a <code>null</code> value to omit this optional property.</p>
	 *
	 * @param constraint_set0_flag
	 * @param constraint_set1_flag
	 * @param constraint_set2_flag
	 * @param constraint_set3_flag
	 * @param constraint_set4_flag
	 * @param constraint_set5_flag
	 *
	 * @see #setAVCProfileConstraint(Byte)
	 */
	public void setAVCProfileContraint(
			boolean constraint_set0_flag,
			boolean constraint_set1_flag,
			boolean constraint_set2_flag,
			boolean constraint_set3_flag,
			boolean constraint_set4_flag,
			boolean constraint_set5_flag);

	/**
	 * <p>Returns the level from the <code>level_idc</code> parameter in the sequence parameter set. The level value is
	 * the defined level multiplied by 10, with the exception of a value of 9 that equals special level <em>1b</em>.
	 * This is an optional property.</p>
	 *
	 * @return Level from the <code>level_idc</code> parameter in the sequence parameter set.
	 *
	 * @throws PropertyNotPresentException The optional level property is not present.
	 */
	public @UInt8 byte getAVCLevel()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the level from the <code>level_idc</code> parameter in the sequence parameter set. The level value is
	 * the defined level multiplied by 10, with the exception of a value of 9 that equals special level <em>1b</em>.
	 * This is an optional property.</p>
	 *
	 * @param avcLevel Level from the <code>level_idc</code> parameter in the sequence parameter set.
	 * @throws IllegalArgumentException The level property cannot be negative.
	 */
	public void setAVCLevel(
			@UInt8 Byte avcLevel)
		throws IllegalArgumentException; // Maximum level is 5.1, which is represented by 5.1 x 10 = 51.

	/**
	 * <p>Returns the maximum number of reference frames. The value is the maximum value of
	 * <code>max_num_ref_frames</code> within all sequence parameter sets. This is an optional property.</p>
	 *
	 * @return Maximum number of reference frames.
	 *
	 * @throws PropertyNotPresentException The optional maximum reference frames property is not present.
	 */
	// Note: Elecard Stream Analyzer wrongly labels this property "num_ref_frames".
	public @UInt8 byte getAVCMaximumRefFrames()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets  the maximum number of reference frames. The value is the maximum value of
	 * <code>max_num_ref_frames</code> within all sequence parameter sets. Set this optional property to
	 * <code>null</code> to omit it.</p>
	 *
	 * @param avcMaximumRefFrames Maximum number of reference frames.
	 *
	 * @throws IllegalArgumentException The maximum number of reference frames cannot be negative.
	 */
	public void setAVCMaximumRefFrames(
			@UInt8 Byte avcMaximumRefFrames)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the location and the constancy of sequence parameter sets. For details of the value, see the descriptions of the
	 * constancy flag and in band locations linked to below. This is an optional property where the default value of
	 * {@value #AVCSEQUENCEPARAMETERSETFLAG_DEFAULT} is returned when when the property is omitted.</p>
	 *
	 * @return Location and the constancy of sequence parameter sets.
	 *
	 * @see #AVCSEQUENCEPARAMETERSETFLAG_DEFAULT
	 * @see #CONSTANCYFLAG_UNKNOWN
	 * @see #CONSTANCYFLAG_CONSTANT
	 * @see #INBANDLOCATION_UNKNOWN
	 * @see #INBANDLOCATION_FIRSTACCESSUNIT
	 * @see #INBANDLOCATION_EVERYACCESSUNIT
	 * @see #INBANDLOCATION_EVERYGOP
	 */
	@MediaProperty(uuid1 = 0x04010606, uuid2 = 0x0110, uuid3 = 0x0000,
		     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0e},
		     definedName = "AVCSequenceParameterSetFlag",
		     typeName = "UInt8",
		     optional = true,
		     uniqueIdentifier = false,
		     pid = 0x0000,
		     symbol = "AVCSequenceParameterSetFlag")
	public @UInt8 byte getAVCSequenceParameterSetFlag();

	/**
	 * <p> Sets the location and the constancy of sequence parameter sets. For details of the value, see the descriptions of the
	 * constancy flag and in band locations linked to below. Set this optional property to <code>null</code> to omit
	 * it and have subsequent requests return the default value of {@value #AVCSEQUENCEPARAMETERSETFLAG_DEFAULT}.</p>
	 *
	 * @param avcSequenceParameterSetFlag Location and the constancy of sequence parameter sets.
	 *
	 * @see #AVCSEQUENCEPARAMETERSETFLAG_DEFAULT
	 * @see #CONSTANCYFLAG_UNKNOWN
	 * @see #CONSTANCYFLAG_CONSTANT
	 * @see #INBANDLOCATION_UNKNOWN
	 * @see #INBANDLOCATION_FIRSTACCESSUNIT
	 * @see #INBANDLOCATION_EVERYACCESSUNIT
	 * @see #INBANDLOCATION_EVERYGOP
	 */
	public void setAVCSequenceParameterSetFlag(
			@UInt8 Byte avcSequenceParameterSetFlag); // Value may have bit 7 set, so no IllegalArgumentException

	/**
	 * <p>Returns the location and the constancy of picture parameter sets. For details of the value, see the descriptions of the
	 * constancy flag and in band locations linked to below. This is an optional property where the default value of
	 * {@value #AVCPICTUREPARAMETERSETFLAG_DEFAULT} is returned when when the property is omitted.</p>
	 *
	 * @return Location and the constancy of picture parameter sets.
	 *
	 * @see #AVCPICTUREPARAMETERSETFLAG_DEFAULT
	 * @see #CONSTANCYFLAG_UNKNOWN
	 * @see #CONSTANCYFLAG_CONSTANT
	 * @see #INBANDLOCATION_UNKNOWN
	 * @see #INBANDLOCATION_FIRSTACCESSUNIT
	 * @see #INBANDLOCATION_EVERYACCESSUNIT
	 * @see #INBANDLOCATION_EVERYGOP
	 */
	public @UInt8 byte getAVCPictureParameterSetFlag();

	/**
	 * <p>Sets the location and the constancy of picture parameter sets. For details of the value, see the descriptions of the
	 * constancy flag and in band locations linked to below. Set this optional property to <code>null</code> to omit
	 * it and have subsequent requests return the default value of {@value #AVCPICTUREPARAMETERSETFLAG_DEFAULT}.</p>
	 *
	 * @param avcPictureParaemterSetFlag Location and the constancy of picture parameter sets.
	 *
	 * @see #AVCPICTUREPARAMETERSETFLAG_DEFAULT
	 * @see #CONSTANCYFLAG_UNKNOWN
	 * @see #CONSTANCYFLAG_CONSTANT
	 * @see #INBANDLOCATION_UNKNOWN
	 * @see #INBANDLOCATION_FIRSTACCESSUNIT
	 * @see #INBANDLOCATION_EVERYACCESSUNIT
	 * @see #INBANDLOCATION_EVERYGOP
	 */
	public void setAVCPictureParameterSetFlag(
			@UInt8 Byte avcPictureParaemterSetFlag); // Value may have bit 7 set, so no IllegalArgumentException

	public AVCSubDescriptor clone();
}
