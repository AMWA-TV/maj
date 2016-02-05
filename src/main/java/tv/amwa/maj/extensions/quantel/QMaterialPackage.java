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

package tv.amwa.maj.extensions.quantel;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt32;

public interface QMaterialPackage
	extends tv.amwa.maj.model.MaterialPackage {

//	<PropertyDefinition>
//    <Identification>urn:uuid:46081f05-e192-4a3a-8aba-9e076d820818</Identification>
//    <Symbol>Rush_ingest_tc_dropframe</Symbol>
//    <Name>Rush ingest tc dropframe</Name>
//    <Type>Int32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffb1</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getRushIngestTCDropframe()
		throws PropertyNotPresentException;

	public void setRushIngestTCDropFrame(
			@Int32 Integer rushIngestTCDropFrame);

//  <PropertyDefinition>
//    <Identification>urn:uuid:8e1f2007-8464-4b66-8423-554e3fe26fa9</Identification>
//    <Symbol>Video_rush_firstLineIsF2</Symbol>
//    <Name>Video rush firstLineIsF2</Name>
//    <Type>Int32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffb0</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getVideoRushFirstLineIsF2()
		throws PropertyNotPresentException;

	public void setVideoRushFirstLineIsF2(
			@Int32 Integer videoRushFirstLineIsF2);

//  <PropertyDefinition>
//    <Identification>urn:uuid:82960d09-7b01-45dc-b6e8-a6f6538e72e9</Identification>
//    <Symbol>Video_rush_chroma_range</Symbol>
//    <Name>Video rush chroma range</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffaf</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushChromaRange()
		throws PropertyNotPresentException;

	public void setVideoRushChromaRange(
			@UInt32 Integer videoRushChromaRange)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:991baf10-619b-4396-b39b-e53213c9de41</Identification>
//    <Symbol>Video_rush_production_tc_user_bits</Symbol>
//    <Name>Video rush production tc user bits</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffae</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushProductionTCUserBits()
		throws PropertyNotPresentException;

	public void setVideoRushProductionTCUserBits(
			@UInt32 Integer videoRushProductionTCUserBits)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:2333e014-5b1e-4700-9969-f94c15f02199</Identification>
//    <Symbol>Audio_rush_channels</Symbol>
//    <Name>Audio rush channels</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffad</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getAudioRushChannels()
		throws PropertyNotPresentException;

	public void setAudioRushChannels(
			@UInt32 Integer videoRushProductionTCUserBits)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:bf60ff1e-e154-4515-8236-d6540d197d26</Identification>
//    <Symbol>Video_rush_production_tc_type</Symbol>
//    <Name>Video rush production tc type</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffac</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushProductionTCType()
		throws PropertyNotPresentException;

	public void setVideoRushProductionTCType(
			@UInt32 Integer videoRushProductionTCType)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:ee205f1f-cca9-46cf-a59b-5a509b87dd2f</Identification>
//    <Symbol>Video_rush_keycode_user_bits</Symbol>
//    <Name>Video rush keycode user bits</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffab</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushKeycodeUserBits()
		throws PropertyNotPresentException;

	public void setVideoRushKeycodeUserBits(
			@UInt32 Integer videoRushKeycodeUserBits)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:fad8ba26-219d-48c1-9bc0-98493ecf26bc</Identification>
//    <Symbol>Video_rush_y_aspect</Symbol>
//    <Name>Video rush y aspect</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffaa</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushYAspect()
		throws PropertyNotPresentException;

	public void setVideoRushYAspect(
			@UInt32 Integer videoRushYAspect)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:2cb15b2b-0076-4b3c-84cb-4431a2d6e2f4</Identification>
//    <Symbol>Video_rush_production_tc_time_bits</Symbol>
//    <Name>Video rush production tc time bits</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffa9</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushProductionTCTimeBits()
		throws PropertyNotPresentException;

	public void setVideoRushProductionTCTimeBits(
			@UInt32 Integer videoRushProductionTCTimeBits)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:7097762e-fb52-47ac-85c6-a17d8517fa8d</Identification>
//    <Symbol>Video_rush_dominance</Symbol>
//    <Name>Video rush dominance</Name>
//    <Type>Int32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffa8</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getVideoRushDominance()
			throws PropertyNotPresentException;

	public void setVideoRushDominance(
			@Int32 Integer videoRushDominance);

//  <PropertyDefinition>
//    <Identification>urn:uuid:2a596f42-929c-43a6-bd4a-2665ab204d2c</Identification>
//    <Symbol>Video_rush_black_level</Symbol>
//    <Name>Video rush black level</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffa7</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushBlackLevel()
		throws PropertyNotPresentException;

	public void setVideoRushBlackLevel(
			@UInt32 Integer videoRushBlackLevel)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:7e85d75b-fd3e-40b3-a77e-d8e0e0170ca1</Identification>
//    <Symbol>Audio_rush_sample_bits</Symbol>
//    <Name>Audio rush sample bits</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffa6</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getAudioRushSampleBits()
			throws PropertyNotPresentException;

	public void setAudioRushSampleBits(
			@UInt32 Integer audioRushSampleBits)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:8b3f6a6d-e074-41c5-b040-82e1cc2bb031</Identification>
//    <Symbol>Rush_ingest_tc_fps</Symbol>
//    <Name>Rush ingest tc fps</Name>
//    <Type>Int32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffa5</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getRushIngestTCFPS()
			throws PropertyNotPresentException;

	public void setRushIngestTCFPS(
			@Int32 Integer rushIngestTCFPS);

//  <PropertyDefinition>
//    <Identification>urn:uuid:d0d15771-b85e-4f10-a5bf-cccfc89e0ec6</Identification>
//    <Symbol>Video_rush_plane_size</Symbol>
//    <Name>Video rush plane size</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffa4</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushPlaneSize()
			throws PropertyNotPresentException;

	public void setVideoRushPlaneSize(
			@UInt32 Integer videoRushPlaneSize)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:f2a0b377-725b-4f5b-8cfc-d5dee4dbf789</Identification>
//    <Symbol>Video_rush_vtr_tc_type</Symbol>
//    <Name>Video rush vtr tc type</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffa3</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushVtrTCType()
			throws PropertyNotPresentException;

	public void setVideoRushVtrTCType(
			@UInt32 Integer videoRushVtrTCType)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:c8778b7c-3fdb-4b00-b87f-d1eca8efa9db</Identification>
//    <Symbol>Audio_rush_frame_rate_numerator</Symbol>
//    <Name>Audio rush frame rate numerator</Name>
//    <Type>Int32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffa2</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getAudioRushFrameRateNumerator()
			throws PropertyNotPresentException;

	public void setAudioRushFrameRateNumerator(
			@Int32 Integer audioRushFrameRateNumerator);

//  <PropertyDefinition>
//    <Identification>urn:uuid:94ee4180-8328-48fd-97bc-95e2bcb30cb8</Identification>
//    <Symbol>Rush_file_path</Symbol>
//    <Name>Rush file path</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffa1</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getRushFilePath()
		throws PropertyNotPresentException;

	public void setRushFilePath(
			String rushFilePath);

//  <PropertyDefinition>
//    <Identification>urn:uuid:7982f980-0cfd-4a15-9d08-0a311eb5930d</Identification>
//    <Symbol>Rush_is_file_sequence</Symbol>
//    <Name>Rush is file sequence</Name>
//    <Type>Int32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xffa0</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getRushIsFileSequence()
			throws PropertyNotPresentException;

	public void setRushIsFileSequence(
			@Int32 int rushIsFileSequence);

//  <PropertyDefinition>
//    <Identification>urn:uuid:8ae5d292-866a-45ea-87ef-ef07ad29542b</Identification>
//    <Symbol>Video_rush_renderable</Symbol>
//    <Name>Video rush renderable</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff9f</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushRenderable()
			throws PropertyNotPresentException;

	public void setVideoRushRenderable(
			@UInt32 Integer videoRushRenderable)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:5a924e9c-e71d-446c-8083-fe711288b16d</Identification>
//    <Symbol>Video_rush_hosepiped_right</Symbol>
//    <Name>Video rush hosepiped right</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff9e</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushHosepipedRight()
			throws PropertyNotPresentException;

	public void setVideoRushHosepipedRight(
			@UInt32 Integer videoRushHosepipedRight)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:e12800a0-8be5-410c-8ce9-b7fe409632f4</Identification>
//    <Symbol>Video_rush_compression</Symbol>
//    <Name>Video rush compression</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff9d</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushCompression()
			throws PropertyNotPresentException;

	public void setVideoRushCompression(
			@UInt32 Integer videoRushCompression)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:798714a5-794e-4357-bcfe-491825f9c9f5</Identification>
//    <Symbol>Remote_rush_blob</Symbol>
//    <Name>Remote rush blob</Name>
//    <Type>Stream</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff9c</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Stream getRemoteRushBlob()
		throws PropertyNotPresentException;

	public void setRemoteRushBlob(
			Stream remoteRushBlob);

//  <PropertyDefinition>
//    <Identification>urn:uuid:c7b9e7af-34dd-47b2-8534-2e0c0ee3ed5c</Identification>
//    <Symbol>Video_rush_x_size</Symbol>
//    <Name>Video rush x size</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff9b</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushXSize()
			throws PropertyNotPresentException;

	public void setVideoRushXSize(
			@UInt32 Integer videoRushXSize)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:b424eeaf-7085-4c53-b96a-a2d18030319e</Identification>
//    <Symbol>Video_rush_vtr_tc_time_bits</Symbol>
//    <Name>Video rush vtr tc time bits</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff9a</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushVtrTCTimeBits()
			throws PropertyNotPresentException;

	public void setVideoRushVtrTCTimeBits(
			@UInt32 Integer videoRushVtrTCTimeBits)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:64f3f0af-5bbd-49bc-b3b6-475fe2ad7495</Identification>
//    <Symbol>Video_rush_y_size</Symbol>
//    <Name>Video rush y size</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff99</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushYSize()
			throws PropertyNotPresentException;

	public void setVideoRushYSize(
			@UInt32 Integer videoRushYSize)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:a06ee9b3-ba38-42d7-9db6-9e6acbdff404</Identification>
//    <Symbol>Video_rush_colour_format</Symbol>
//    <Name>Video rush colour format</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff98</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushColourFormat()
			throws PropertyNotPresentException;

	public void setVideoRushColourFormat(
			@UInt32 Integer videoRushColourFormat)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:f42c69b5-c62f-4921-b1d9-2563199a7006</Identification>
//    <Symbol>Video_rush_keycode_time_bits</Symbol>
//    <Name>Video rush keycode time bits</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff97</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushKeycodeTimeBits()
			throws PropertyNotPresentException;

	public void setVideoRushKeycodeTimeBits(
			@UInt32 Integer videoRushKeycodeTimeBits)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:225512b6-85ec-4768-bfd8-85843a7cca41</Identification>
//    <Symbol>Video_rush_vtr_tc_user_bits</Symbol>
//    <Name>Video rush vtr tc user bits</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff96</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushVtrTCUserBits()
			throws PropertyNotPresentException;

	public void setVideoRushVtrTCUserBits(
			@UInt32 Integer videoRushVtrTCUserBits)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:3eba82bc-e986-42f6-a9f1-923dafbcd49f</Identification>
//    <Symbol>Video_rush_format_flags</Symbol>
//    <Name>Video rush format flags</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff95</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushFormatFlags()
			throws PropertyNotPresentException;

	public void setVideoRushFormatFlags(
			@UInt32 Integer videoRushFormatFlags)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:cbf99dbd-d7dc-4ca6-b3d5-6f4cb0a5b802</Identification>
//    <Symbol>Video_rush_scan_type</Symbol>
//    <Name>Video rush scan type</Name>
//    <Type>Int32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff94</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getVideoRushScanType()
			throws PropertyNotPresentException;

	public void setVideoRushScanType(
			@Int32 Integer avideoRushScanType);

//  <PropertyDefinition>
//    <Identification>urn:uuid:cbf92aca-84fa-4bc1-94c6-29304b09a5bd</Identification>
//    <Symbol>Rush_file_is_multiple_frames</Symbol>
//    <Name>Rush file is multiple frames</Name>
//    <Type>Int32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff93</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getRushFileIsMultipleFrames()
			throws PropertyNotPresentException;

	public void setRushFileIsMultipleFrames(
			@Int32 Integer rushFileIsMultipleFrames);

//  <PropertyDefinition>
//    <Identification>urn:uuid:b3eb74ce-d1de-4113-85d9-fd19dd6c6a3b</Identification>
//    <Symbol>Audio_rush_frame_rate_denominator</Symbol>
//    <Name>Audio rush frame rate denominator</Name>
//    <Type>Int32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff92</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getAudioRushFrameRateDenominator()
			throws PropertyNotPresentException;

	public void setAudioRushFrameRateDenominator(
			@Int32 Integer audioRushFrameRateDenominator);

//  <PropertyDefinition>
//    <Identification>urn:uuid:4d5158d0-e6f7-4970-bc41-677dc6150397</Identification>
//    <Symbol>Video_rush_planes</Symbol>
//    <Name>Video rush planes</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff91</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushPlanes()
			throws PropertyNotPresentException;

	public void setVideoRushPlanes(
			@UInt32 Integer videoRushPlanes)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:b08e13d4-b879-4d53-8ddb-34bd764dd2b4</Identification>
//    <Symbol>Rush_ingest_tc</Symbol>
//    <Name>Rush ingest tc</Name>
//    <Type>Int32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff90</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getRushIngestTC()
			throws PropertyNotPresentException;

	public void setRushIngestTC(
			@Int32 Integer audioRushFrameRateDenominator);

//  <PropertyDefinition>
//    <Identification>urn:uuid:0ea150e6-44e3-4d45-954d-bea4d3786b98</Identification>
//    <Symbol>Video_rush_white_level</Symbol>
//    <Name>Video rush white level</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff8f</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushWhiteLevel()
			throws PropertyNotPresentException;

	public void setVideoRushWhiteLevel(
			@UInt32 Integer videoRushWhiteLevel)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:08f3cef1-5faa-4595-945c-f4b12666e466</Identification>
//    <Symbol>Video_rush_keycode_type</Symbol>
//    <Name>Video rush keycode type</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff8e</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushKeycodeType()
			throws PropertyNotPresentException;

	public void setVideoRushKeycodeType(
			@UInt32 Integer videoRushKeycodeType)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:cf1e27f4-a9d9-4951-ab2e-a7328215ed60</Identification>
//    <Symbol>Video_rush_x_aspect</Symbol>
//    <Name>Video rush x aspect</Name>
//    <Type>UInt32</Type>
//    <MemberOf>MaterialPackage</MemberOf>
//    <LocalIdentification>0xff8d</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getVideoRushXAspect()
			throws PropertyNotPresentException;

	public void setVideoRushXAspect(
			@UInt32 Integer videoRushXAspect)
		throws IllegalArgumentException;
}
