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

package tv.amwa.maj.constant;

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.record.AUID;

/**
 * <p>First half of constants from SMPTE {@linkplain RP224}, the <em>SMPTE Labels Registry</em>.</p>
 *
 * <p>Splitting into two is required due to Java initialiser limits.</p>
 *
 *
 *
 * @see RP224
 */
public interface RP224FirstHalf {

    /**
     * <p>SDTI-CP MPEG-2 Baseline Template.</p>
     */
    public final static AUID SDTICP_MPEG2_Baseline_Template = Forge.makeAUID(
            0x01010101, (short) 0x0101, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SDTI-CP MPEG-2 Extended Template.</p>
     */
    public final static AUID SDTICP_MPEG2_Extended_Template = Forge.makeAUID(
            0x01010101, (short) 0x0101, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>Unknown File Format.</p>
     */
    public final static AUID Unknown_File_Format = Forge.makeAUID(
            0x01010201, (short) 0x0100, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE-12M Timecode Track Inactive User Bits.</p>
     */
    public final static AUID SMPTE12M_Timecode_Track_Inactive_User_Bits = Forge.makeAUID(
            0x01030201, (short) 0x0100, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M Timecode Track Active User Bits.</p>
     */
    public final static AUID SMPTE12M_Timecode_Track_Active_User_Bits = Forge.makeAUID(
            0x01030201, (short) 0x0200, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-309M Timecode Track Datecode User Bits.</p>
     */
    public final static AUID SMPTE309M_Timecode_Track_Datecode_User_Bits = Forge.makeAUID(
            0x01030201, (short) 0x0300, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>Descriptive Metadata Track.</p>
     */
    public final static AUID Descriptive_Metadata_Track = Forge.makeAUID(
            0x01030201, (short) 0x1000, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>Picture Essence Track.</p>
     */
    public final static AUID Picture_Essence_Track = Forge.makeAUID(
            0x01030202, (short) 0x0100, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>Sound Essence Track.</p>
     */
    public final static AUID Sound_Essence_Track = Forge.makeAUID(
            0x01030202, (short) 0x0200, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>Data Essence Track.</p>
     */
    public final static AUID Data_Essence_Track = Forge.makeAUID(
            0x01030202, (short) 0x0300, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>Auxiliary Data Track.</p>
     */
    public final static AUID Auxiliary_Data_Track = Forge.makeAUID(
            0x01030203, (short) 0x0100, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x05 } );

    /**
     * <p>Parsed Text Track.</p>
     */
    public final static AUID Parsed_Text_Track = Forge.makeAUID(
            0x01030203, (short) 0x0200, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>AES-128 CBC Identifier.</p>
     */
    public final static AUID AES128_CBC_Identifier = Forge.makeAUID(
            0x02090201, (short) 0x0100, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>HMAC-SHA1 128-bit Identifier.</p>
     */
    public final static AUID HMACSHA1_128bit_Identifier = Forge.makeAUID(
            0x02090202, (short) 0x0100, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>ITU-R BT470 Transfer Characteristic.</p>
     */
    public final static AUID ITUR_BT470_Transfer_Characteristic = Forge.makeAUID(
            0x04010101, (short) 0x0101, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>ITU-R BT709 Transfer Characteristic.</p>
     */
    public final static AUID ITUR_BT709_Transfer_Characteristic = Forge.makeAUID(
            0x04010101, (short) 0x0102, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE 240M Transfer Characteristic.</p>
     */
    public final static AUID SMPTE_240M_Transfer_Characteristic = Forge.makeAUID(
            0x04010101, (short) 0x0103, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>ITU-R.BT1361 Transfer Characteristic.</p>
     */
    public final static AUID ITURBT1361_Transfer_Characteristic = Forge.makeAUID(
            0x04010101, (short) 0x0105, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x06 } );

    /**
     * <p>Linear Transfer Characteristic.</p>
     */
    public final static AUID Linear_Transfer_Characteristic = Forge.makeAUID(
            0x04010101, (short) 0x0106, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x06 } );

    /**
     * <p>SMPTE-DC28 DCDM Transfer Characteristic.</p>
     */
    public final static AUID SMPTEDC28_DCDM_Transfer_Characteristic = Forge.makeAUID(
            0x04010101, (short) 0x0107, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>ITU-R BT601 Coding Equations.</p>
     */
    public final static AUID ITUR_BT601_Coding_Equations = Forge.makeAUID(
            0x04010101, (short) 0x0201, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>ITU-R BT709 Coding Equations.</p>
     */
    public final static AUID ITUR_BT709_Coding_Equations = Forge.makeAUID(
            0x04010101, (short) 0x0202, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE 240M Coding Equations.</p>
     */
    public final static AUID SMPTE_240M_Coding_Equations = Forge.makeAUID(
            0x04010101, (short) 0x0203, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x06 } );

    /**
     * <p>SMPTE-170M Color Primaries.</p>
     */
    public final static AUID SMPTE170M_Color_Primaries = Forge.makeAUID(
            0x04010101, (short) 0x0301, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x06 } );

    /**
     * <p>ITU-R.BT470 Color Primaries.</p>
     */
    public final static AUID ITURBT470_Color_Primaries = Forge.makeAUID(
            0x04010101, (short) 0x0302, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x06 } );

    /**
     * <p>ITU-R.BT709 Color Primaries.</p>
     */
    public final static AUID ITURBT709_Color_Primaries = Forge.makeAUID(
            0x04010101, (short) 0x0303, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x06 } );

    /**
     * <p>Uncompressed Picture Coding Interleaved 444 CbYCr 8-bit.</p>
     */
    public final static AUID Uncompressed_Picture_Coding_Interleaved_444_CbYCr_8bit = Forge.makeAUID(
            0x04010201, (short) 0x0101, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>Uncompressed Picture Coding Interleaved 422 CbYCrY 8-bit.</p>
     */
    public final static AUID Uncompressed_Picture_Coding_Interleaved_422_CbYCrY_8bit = Forge.makeAUID(
            0x04010201, (short) 0x0102, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>Uncompressed Picture Coding Interleaved 422 YCbYCr 8-bit.</p>
     */
    public final static AUID Uncompressed_Picture_Coding_Interleaved_422_YCbYCr_8bit = Forge.makeAUID(
            0x04010201, (short) 0x0102, (short) 0x0102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>Uncompressed Picture Coding Planar 422 YCbCr 8-bit.</p>
     */
    public final static AUID Uncompressed_Picture_Coding_Planar_422_YCbCr_8bit = Forge.makeAUID(
            0x04010201, (short) 0x0102, (short) 0x0103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>Uncompressed Picture Coding Interleaved 422 CbYCrY 10-bit.</p>
     */
    public final static AUID Uncompressed_Picture_Coding_Interleaved_422_CbYCrY_10bit = Forge.makeAUID(
            0x04010201, (short) 0x0102, (short) 0x0201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>Uncompressed Picture Coding Planar 422 CbYCrY 10-bit.</p>
     */
    public final static AUID Uncompressed_Picture_Coding_Planar_422_CbYCrY_10bit = Forge.makeAUID(
            0x04010201, (short) 0x0102, (short) 0x0202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>Uncompressed Picture Coding Interleaved 422 CbYCrY 12-bit.</p>
     */
    public final static AUID Uncompressed_Picture_Coding_Interleaved_422_CbYCrY_12bit = Forge.makeAUID(
            0x04010201, (short) 0x0102, (short) 0x0301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>Uncompressed Picture Coding Interleaved 422 CbYCrY 16-bit.</p>
     */
    public final static AUID Uncompressed_Picture_Coding_Interleaved_422_CbYCrY_16bit = Forge.makeAUID(
            0x04010201, (short) 0x0102, (short) 0x0401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>Uncompressed Picture Coding Planar 420 YCbCr 8-bit.</p>
     */
    public final static AUID Uncompressed_Picture_Coding_Planar_420_YCbCr_8bit = Forge.makeAUID(
            0x04010201, (short) 0x0103, (short) 0x0102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>Undefined Uncompressed Picture Coding.</p>
     */
    public final static AUID Undefined_Uncompressed_Picture_Coding = Forge.makeAUID(
            0x04010201, (short) 0x7F00, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MPEG-2 MP-ML I-Frame.</p>
     */
    public final static AUID MPEG2_MPML_IFrame = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x1000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 MP-ML Long GOP.</p>
     */
    public final static AUID MPEG2_MPML_Long_GOP = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x1100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 MP-ML No I-Frames.</p>
     */
    public final static AUID MPEG2_MPML_No_IFrames = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x1200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 HDV 480x720 29.97P 4x3.</p>
     */
    public final static AUID MPEG2_HDV_480x720_2997P_4x3 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 480x720 29.97P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_480x720_2997P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 480x720 59.94I 4x3.</p>
     */
    public final static AUID MPEG2_HDV_480x720_5994I_4x3 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2004,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 480x720 59.94I 16x9.</p>
     */
    public final static AUID MPEG2_HDV_480x720_5994I_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 480x720 59.94P 4x3.</p>
     */
    public final static AUID MPEG2_HDV_MPML_480x720_5994P_4x3 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 480x720 59.94P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_MPML_480x720_5994P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 576x720 25P 4x3.</p>
     */
    public final static AUID MPEG2_HDV_576x720_25P_4x3 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2011,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 576x720 25P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_576x720_25P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2012,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 576x720 50I 4x3.</p>
     */
    public final static AUID MPEG2_HDV_576x720_50I_4x3 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2014,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 576x720 50I 16x9.</p>
     */
    public final static AUID MPEG2_HDV_576x720_50I_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2015,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 576x720 50P 4x3.</p>
     */
    public final static AUID MPEG2_HDV_MPML_576x720_50P_4x3 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2016,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 576x720 50P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_MPML_576x720_50P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0101, (short) 0x2017,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>SMPTE D-10 50Mbps 625x50I.</p>
     */
    public final static AUID SMPTE_D10_50Mbps_625x50I = Forge.makeAUID(
            0x04010202, (short) 0x0102, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE D-10 50Mbps 525x59.94I.</p>
     */
    public final static AUID SMPTE_D10_50Mbps_525x5994I = Forge.makeAUID(
            0x04010202, (short) 0x0102, (short) 0x0102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE D-10 40Mbps 625x50I.</p>
     */
    public final static AUID SMPTE_D10_40Mbps_625x50I = Forge.makeAUID(
            0x04010202, (short) 0x0102, (short) 0x0103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE D-10 40Mbps 525x59.94I.</p>
     */
    public final static AUID SMPTE_D10_40Mbps_525x5994I = Forge.makeAUID(
            0x04010202, (short) 0x0102, (short) 0x0104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE D-10 30Mbps 625x50I.</p>
     */
    public final static AUID SMPTE_D10_30Mbps_625x50I = Forge.makeAUID(
            0x04010202, (short) 0x0102, (short) 0x0105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE D-10 30Mbps 525x59.94I.</p>
     */
    public final static AUID SMPTE_D10_30Mbps_525x5994I = Forge.makeAUID(
            0x04010202, (short) 0x0102, (short) 0x0106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MPEG-2 422P-ML I-Frame.</p>
     */
    public final static AUID MPEG2_422PML_IFrame = Forge.makeAUID(
            0x04010202, (short) 0x0102, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 422P-ML Long GOP.</p>
     */
    public final static AUID MPEG2_422PML_Long_GOP = Forge.makeAUID(
            0x04010202, (short) 0x0102, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 422P-ML No I-Frames.</p>
     */
    public final static AUID MPEG2_422PML_No_IFrames = Forge.makeAUID(
            0x04010202, (short) 0x0102, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 MP-HL I-Frame.</p>
     */
    public final static AUID MPEG2_MPHL_IFrame = Forge.makeAUID(
            0x04010202, (short) 0x0103, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 MP-HL Long GOP.</p>
     */
    public final static AUID MPEG2_MPHL_Long_GOP = Forge.makeAUID(
            0x04010202, (short) 0x0103, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 MP-HL No I-Frames.</p>
     */
    public final static AUID MPEG2_MPHL_No_IFrames = Forge.makeAUID(
            0x04010202, (short) 0x0103, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 HDV 720x1280 59.94P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_720x1280_5994P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0103, (short) 0x2001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 720x1280 50P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_MPHL_720x1280_50P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0103, (short) 0x2008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 422P-HL I-Frame.</p>
     */
    public final static AUID MPEG2_422PHL_IFrame = Forge.makeAUID(
            0x04010202, (short) 0x0104, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 422P-HL Long GOP.</p>
     */
    public final static AUID MPEG2_422PHL_Long_GOP = Forge.makeAUID(
            0x04010202, (short) 0x0104, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 422P-HL No I-Frames.</p>
     */
    public final static AUID MPEG2_422PHL_No_IFrames = Forge.makeAUID(
            0x04010202, (short) 0x0104, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-2 MP-H14 I-Frame.</p>
     */
    public final static AUID MPEG2_MPH14_IFrame = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 MP-H14 Long GOP.</p>
     */
    public final static AUID MPEG2_MPH14_Long_GOP = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 MP-H14 No I-Frames.</p>
     */
    public final static AUID MPEG2_MPH14_No_IFrames = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 480x720 59.94P 4x3.</p>
     */
    public final static AUID MPEG2_HDV_MPH14_480x720_5994P_4x3 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 480x720 59.94P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_MPH14_480x720_5994P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 576x720 50P 4x3.</p>
     */
    public final static AUID MPEG2_HDV_MPH14_576x720_50P_4x3 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 576x720 50P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_MPH14_576x720_50P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2009,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 720x1280 29.97P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_720x1280_2997P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2010,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 720x1280 25P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_720x1280_25P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2014,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 720x1280 50P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_MPH14_720x1280_50P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2015,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 1080x1440 59.94I 16x9.</p>
     */
    public final static AUID MPEG2_HDV_1080x1440_5994I_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2020,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 1080x1440 29.97P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_1080x1440_2997P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2021,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 1080x1440 23.98P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_1080x1440_2398P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2022,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 1080x1440 50I 16x9.</p>
     */
    public final static AUID MPEG2_HDV_1080x1440_50I_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2024,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HDV 1080x1440 25P 16x9.</p>
     */
    public final static AUID MPEG2_HDV_1080x1440_25P_16x9 = Forge.makeAUID(
            0x04010202, (short) 0x0105, (short) 0x2025,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 HP-ML I-Frame.</p>
     */
    public final static AUID MPEG2_HPML_IFrame = Forge.makeAUID(
            0x04010202, (short) 0x0106, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MPEG-2 HP-ML Long GOP.</p>
     */
    public final static AUID MPEG2_HPML_Long_GOP = Forge.makeAUID(
            0x04010202, (short) 0x0106, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MPEG-2 HP-ML No I-Frames.</p>
     */
    public final static AUID MPEG2_HPML_No_IFrames = Forge.makeAUID(
            0x04010202, (short) 0x0106, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MPEG-2 HP-HL I-Frame.</p>
     */
    public final static AUID MPEG2_HPHL_IFrame = Forge.makeAUID(
            0x04010202, (short) 0x0107, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MPEG-2 HP-HL Long GOP.</p>
     */
    public final static AUID MPEG2_HPHL_Long_GOP = Forge.makeAUID(
            0x04010202, (short) 0x0107, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MPEG-2 HP-HL No I-Frames.</p>
     */
    public final static AUID MPEG2_HPHL_No_IFrames = Forge.makeAUID(
            0x04010202, (short) 0x0107, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MPEG-2 HP-H14 I-Frame.</p>
     */
    public final static AUID MPEG2_HPH14_IFrame = Forge.makeAUID(
            0x04010202, (short) 0x0108, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MPEG-2 HP-H14 Long GOP.</p>
     */
    public final static AUID MPEG2_HPH14_Long_GOP = Forge.makeAUID(
            0x04010202, (short) 0x0108, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MPEG-2 HP-H14 No I-Frames.</p>
     */
    public final static AUID MPEG2_HPH14_No_IFrames = Forge.makeAUID(
            0x04010202, (short) 0x0108, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>MPEG-1 Constrained Profile.</p>
     */
    public final static AUID MPEG1_Constrained_Profile = Forge.makeAUID(
            0x04010202, (short) 0x0110, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-1 Unconstrained Coding.</p>
     */
    public final static AUID MPEG1_Unconstrained_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0110, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Advanced Real-time Simple Profile Level 1.</p>
     */
    public final static AUID MPEG4_Advanced_Realtime_Simple_Profile_Level_1 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x0201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Advanced Real-time Simple Profile Level 2.</p>
     */
    public final static AUID MPEG4_Advanced_Realtime_Simple_Profile_Level_2 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x0202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Advanced Real-time Simple Profile Level 3.</p>
     */
    public final static AUID MPEG4_Advanced_Realtime_Simple_Profile_Level_3 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x0203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Advanced Real-time Simple Profile Level 4.</p>
     */
    public final static AUID MPEG4_Advanced_Realtime_Simple_Profile_Level_4 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x0204,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Simple Studio Profile Level 1.</p>
     */
    public final static AUID MPEG4_Simple_Studio_Profile_Level_1 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x1001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Simple Studio Profile Level 2.</p>
     */
    public final static AUID MPEG4_Simple_Studio_Profile_Level_2 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x1002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Simple Studio Profile Level 3.</p>
     */
    public final static AUID MPEG4_Simple_Studio_Profile_Level_3 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x1003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Simple Studio Profile Level 4.</p>
     */
    public final static AUID MPEG4_Simple_Studio_Profile_Level_4 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x1004,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Simple Studio Profile Level 5.</p>
     */
    public final static AUID MPEG4_Simple_Studio_Profile_Level_5 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x1005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>MPEG-4 Simple Studio Profile Level 6.</p>
     */
    public final static AUID MPEG4_Simple_Studio_Profile_Level_6 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x1006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>MPEG-4 Core Studio Profile Level 1.</p>
     */
    public final static AUID MPEG4_Core_Studio_Profile_Level_1 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x1101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Core Studio Profile Level 2.</p>
     */
    public final static AUID MPEG4_Core_Studio_Profile_Level_2 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x1102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Core Studio Profile Level 3.</p>
     */
    public final static AUID MPEG4_Core_Studio_Profile_Level_3 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x1103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>MPEG-4 Core Studio Profile Level 4.</p>
     */
    public final static AUID MPEG4_Core_Studio_Profile_Level_4 = Forge.makeAUID(
            0x04010202, (short) 0x0120, (short) 0x1104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>H.264/MPEG-4 AVC High 10 Intra Unconstrained Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_10_Intra_Unconstrained_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x2001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 10 Intra RP2027 Constrained Class 50 1080/59.94i Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_10_Intra_RP2027_Constrained_Class_50_1080_5994i_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x2101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 10 Intra RP2027 Constrained Class 50 1080/50i Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_10_Intra_RP2027_Constrained_Class_50_1080_50i_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x2102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 10 Intra RP2027 Constrained Class 50 1080/29.97p Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_10_Intra_RP2027_Constrained_Class_50_1080_2997p_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x2103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 10 Intra RP2027 Constrained Class 50 1080/25p Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_10_Intra_RP2027_Constrained_Class_50_1080_25p_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x2104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 10 Intra RP2027 Constrained Class 50 720/59.94p Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_10_Intra_RP2027_Constrained_Class_50_720_5994p_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x2108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 10 Intra RP2027 Constrained Class 50 720/50p Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_10_Intra_RP2027_Constrained_Class_50_720_50p_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x2109,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 422 Intra Profile Unconstrained Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_422_Intra_Profile_Unconstrained_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x3001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 422 Intra RP2027 Constrained Class 100 1080/59.94i Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_422_Intra_RP2027_Constrained_Class_100_1080_5994i_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x3101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 422 Intra RP2027 Constrained Class 100 1080/50i Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_422_Intra_RP2027_Constrained_Class_100_1080_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x3102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 422 Intra RP2027 Constrained Class 100 1080/29.97p Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_422_Intra_RP2027_Constrained_Class_100_1080_2997p_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x3103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 422 Intra RP2027 Constrained Class 100 1080/25p Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_422_Intra_RP2027_Constrained_Class_100_1080_25p_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x3104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 422 Intra RP2027 Constrained Class 100 720/59.94p Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_422_Intra_RP2027_Constrained_Class_100_720_5994p_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x3108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>H.264/MPEG-4 AVC High 422 Intra RP2027 Constrained Class 100 720/50p Coding.</p>
     */
    public final static AUID H264_MPEG4_AVC_High_422_Intra_RP2027_Constrained_Class_100_720_50p_Coding = Forge.makeAUID(
            0x04010202, (short) 0x0132, (short) 0x3109,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>IEC-DV Video 25Mbps 525x60I.</p>
     */
    public final static AUID IECDV_Video_25Mbps_525x60I = Forge.makeAUID(
            0x04010202, (short) 0x0201, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>IEC-DV Video 25Mbps 625x50I.</p>
     */
    public final static AUID IECDV_Video_25Mbps_625x50I = Forge.makeAUID(
            0x04010202, (short) 0x0201, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>IEC-DV Video 25Mbps 525x60I SMPTE-305M Type-41h.</p>
     */
    public final static AUID IECDV_Video_25Mbps_525x60I_SMPTE305M_Type41h = Forge.makeAUID(
            0x04010202, (short) 0x0201, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>IEC-DV Video 25Mbps 625x50I SMPTE-305M Type-41h.</p>
     */
    public final static AUID IECDV_Video_25Mbps_625x50I_SMPTE305M_Type41h = Forge.makeAUID(
            0x04010202, (short) 0x0201, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>DV-based Video 25Mbps 525x60I.</p>
     */
    public final static AUID DVbased_Video_25Mbps_525x60I = Forge.makeAUID(
            0x04010202, (short) 0x0202, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>DV-based Video 25Mbps 625x50I.</p>
     */
    public final static AUID DVbased_Video_25Mbps_625x50I = Forge.makeAUID(
            0x04010202, (short) 0x0202, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>DV-based Video 50Mbps 525x60I.</p>
     */
    public final static AUID DVbased_Video_50Mbps_525x60I = Forge.makeAUID(
            0x04010202, (short) 0x0202, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>DV-based Video 50Mbps 625x50I.</p>
     */
    public final static AUID DVbased_Video_50Mbps_625x50I = Forge.makeAUID(
            0x04010202, (short) 0x0202, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>DV-based Video 100Mbps 1080x59.94I.</p>
     */
    public final static AUID DVbased_Video_100Mbps_1080x5994I = Forge.makeAUID(
            0x04010202, (short) 0x0202, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>DV-based Video 100Mbps 1080x50I.</p>
     */
    public final static AUID DVbased_Video_100Mbps_1080x50I = Forge.makeAUID(
            0x04010202, (short) 0x0202, (short) 0x0600,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>DV-based Video 100Mbps 720x59.94P.</p>
     */
    public final static AUID DVbased_Video_100Mbps_720x5994P = Forge.makeAUID(
            0x04010202, (short) 0x0202, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>DV-based Video 100Mbps 720x50P.</p>
     */
    public final static AUID DVbased_Video_100Mbps_720x50P = Forge.makeAUID(
            0x04010202, (short) 0x0202, (short) 0x0800,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>JPEG 2000 Amd-1 2K Digital Cinema Profile.</p>
     */
    public final static AUID JPEG_2000_Amd1_2K_Digital_Cinema_Profile = Forge.makeAUID(
            0x04010202, (short) 0x0301, (short) 0x0103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>JPEG 2000 Amd-1 4K Digital Cinema Profile.</p>
     */
    public final static AUID JPEG_2000_Amd1_4K_Digital_Cinema_Profile = Forge.makeAUID(
            0x04010202, (short) 0x0301, (short) 0x0104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x09 } );

    /**
     * <p>JPEG 2000 Undefined Digital Cinema Profile.</p>
     */
    public final static AUID JPEG_2000_Undefined_Digital_Cinema_Profile = Forge.makeAUID(
            0x04010202, (short) 0x0301, (short) 0x017F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE D-11 1080 23.98PsF.</p>
     */
    public final static AUID SMPTE_D11_1080_2398PsF = Forge.makeAUID(
            0x04010202, (short) 0x7001, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE D-11 1080 24PsF.</p>
     */
    public final static AUID SMPTE_D11_1080_24PsF = Forge.makeAUID(
            0x04010202, (short) 0x7001, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE D-11 1080 25PsF.</p>
     */
    public final static AUID SMPTE_D11_1080_25PsF = Forge.makeAUID(
            0x04010202, (short) 0x7001, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE D-11 1080 29.97PsF.</p>
     */
    public final static AUID SMPTE_D11_1080_2997PsF = Forge.makeAUID(
            0x04010202, (short) 0x7001, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE D-11 1080 50I.</p>
     */
    public final static AUID SMPTE_D11_1080_50I = Forge.makeAUID(
            0x04010202, (short) 0x7001, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE D-11 1080 59.94I.</p>
     */
    public final static AUID SMPTE_D11_1080_5994I = Forge.makeAUID(
            0x04010202, (short) 0x7001, (short) 0x0600,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE VC-3 ID 1235.</p>
     */
    public final static AUID SMPTE_VC3_ID_1235 = Forge.makeAUID(
            0x04010202, (short) 0x7101, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-3 ID 1237.</p>
     */
    public final static AUID SMPTE_VC3_ID_1237 = Forge.makeAUID(
            0x04010202, (short) 0x7103, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-3 ID 1238.</p>
     */
    public final static AUID SMPTE_VC3_ID_1238 = Forge.makeAUID(
            0x04010202, (short) 0x7104, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-3 ID 1241.</p>
     */
    public final static AUID SMPTE_VC3_ID_1241 = Forge.makeAUID(
            0x04010202, (short) 0x7107, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-3 ID 1242.</p>
     */
    public final static AUID SMPTE_VC3_ID_1242 = Forge.makeAUID(
            0x04010202, (short) 0x7108, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-3 ID 1243.</p>
     */
    public final static AUID SMPTE_VC3_ID_1243 = Forge.makeAUID(
            0x04010202, (short) 0x7109, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-3 ID 1250.</p>
     */
    public final static AUID SMPTE_VC3_ID_1250 = Forge.makeAUID(
            0x04010202, (short) 0x7110, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-3 ID 1251.</p>
     */
    public final static AUID SMPTE_VC3_ID_1251 = Forge.makeAUID(
            0x04010202, (short) 0x7111, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-3 ID 1252.</p>
     */
    public final static AUID SMPTE_VC3_ID_1252 = Forge.makeAUID(
            0x04010202, (short) 0x7112, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-3 ID 1253.</p>
     */
    public final static AUID SMPTE_VC3_ID_1253 = Forge.makeAUID(
            0x04010202, (short) 0x7113, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-1 Coding SP@LL.</p>
     */
    public final static AUID SMPTE_VC1_Coding_SPatLL = Forge.makeAUID(
            0x04010202, (short) 0x7201, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-1 Coding SP@ML.</p>
     */
    public final static AUID SMPTE_VC1_Coding_SPatML = Forge.makeAUID(
            0x04010202, (short) 0x7202, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-1 Coding MP@LL.</p>
     */
    public final static AUID SMPTE_VC1_Coding_MPatLL = Forge.makeAUID(
            0x04010202, (short) 0x7203, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-1 Coding MP@ML.</p>
     */
    public final static AUID SMPTE_VC1_Coding_MPatML = Forge.makeAUID(
            0x04010202, (short) 0x7204, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-1 Coding MP@HL.</p>
     */
    public final static AUID SMPTE_VC1_Coding_MPatHL = Forge.makeAUID(
            0x04010202, (short) 0x7205, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-1 Coding AP@L0.</p>
     */
    public final static AUID SMPTE_VC1_Coding_APatL0 = Forge.makeAUID(
            0x04010202, (short) 0x7206, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-1 Coding AP@L1.</p>
     */
    public final static AUID SMPTE_VC1_Coding_APatL1 = Forge.makeAUID(
            0x04010202, (short) 0x7207, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-1 Coding AP@L2.</p>
     */
    public final static AUID SMPTE_VC1_Coding_APatL2 = Forge.makeAUID(
            0x04010202, (short) 0x7208, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-1 Coding AP@L3.</p>
     */
    public final static AUID SMPTE_VC1_Coding_APatL3 = Forge.makeAUID(
            0x04010202, (short) 0x7209, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE VC-1 Coding AP@L4.</p>
     */
    public final static AUID SMPTE_VC1_Coding_APatL4 = Forge.makeAUID(
            0x04010202, (short) 0x720A, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>SMPTE-382M Default Uncompressed Sound Coding.</p>
     */
    public final static AUID SMPTE382M_Default_Uncompressed_Sound_Coding = Forge.makeAUID(
            0x04020201, (short) 0x0100, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0A } );

    /**
     * <p>AIFF Uncompressed Coding.</p>
     */
    public final static AUID AIFF_Uncompressed_Coding = Forge.makeAUID(
            0x04020201, (short) 0x7E00, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x07 } );

    /**
     * <p>Undefined Sound Coding.</p>
     */
    public final static AUID Undefined_Sound_Coding = Forge.makeAUID(
            0x04020201, (short) 0x7F00, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>A-law Coded Audio default.</p>
     */
    public final static AUID Alaw_Coded_Audio_default = Forge.makeAUID(
            0x04020202, (short) 0x0301, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>DV Compressed Audio.</p>
     */
    public final static AUID DV_Compressed_Audio = Forge.makeAUID(
            0x04020202, (short) 0x0301, (short) 0x1000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>ATSC A-52 Compressed Audio.</p>
     */
    public final static AUID ATSC_A52_Compressed_Audio = Forge.makeAUID(
            0x04020202, (short) 0x0302, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MPEG-1 Layer-1 Compressed Audio.</p>
     */
    public final static AUID MPEG1_Layer1_Compressed_Audio = Forge.makeAUID(
            0x04020202, (short) 0x0302, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MPEG-1 Layer-1 or 2 Compressed Audio.</p>
     */
    public final static AUID MPEG1_Layer1_or_2_Compressed_Audio = Forge.makeAUID(
            0x04020202, (short) 0x0302, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MPEG-1 Layer-2 HDV Constrained.</p>
     */
    public final static AUID MPEG1_Layer2_HDV_Constrained = Forge.makeAUID(
            0x04020202, (short) 0x0302, (short) 0x0501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x08 } );

    /**
     * <p>MPEG-2 Layer-1 Compressed Audio.</p>
     */
    public final static AUID MPEG2_Layer1_Compressed_Audio = Forge.makeAUID(
            0x04020202, (short) 0x0302, (short) 0x0600,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>Dolby-E Compressed Audio.</p>
     */
    public final static AUID DolbyE_Compressed_Audio = Forge.makeAUID(
            0x04020202, (short) 0x0302, (short) 0x1C00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MPEG-2 AAC Compressed Audio.</p>
     */
    public final static AUID MPEG2_AAC_Compressed_Audio = Forge.makeAUID(
            0x04020202, (short) 0x0303, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x03 } );

    /**
     * <p>SMPTE-429-2 Channel Configuration 1.</p>
     */
    public final static AUID SMPTE4292_Channel_Configuration_1 = Forge.makeAUID(
            0x04020210, (short) 0x0101, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-429-2 Channel Configuration 2.</p>
     */
    public final static AUID SMPTE4292_Channel_Configuration_2 = Forge.makeAUID(
            0x04020210, (short) 0x0103, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-429-2 Channel Configuration 3.</p>
     */
    public final static AUID SMPTE4292_Channel_Configuration_3 = Forge.makeAUID(
            0x04020210, (short) 0x0104, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program Configurations.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_Configurations = Forge.makeAUID(
            0x04020210, (short) 0x0301, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program 1a.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_1a = Forge.makeAUID(
            0x04020210, (short) 0x0301, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program 1b.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_1b = Forge.makeAUID(
            0x04020210, (short) 0x0301, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program 1c.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_1c = Forge.makeAUID(
            0x04020210, (short) 0x0301, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Program 2a .</p>
     */
    public final static AUID SMPTE2035_Stereo_Program_2a_ = Forge.makeAUID(
            0x04020210, (short) 0x0302, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Program 2b.</p>
     */
    public final static AUID SMPTE2035_Stereo_Program_2b = Forge.makeAUID(
            0x04020210, (short) 0x0302, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Program 2c.</p>
     */
    public final static AUID SMPTE2035_Stereo_Program_2c = Forge.makeAUID(
            0x04020210, (short) 0x0302, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Dual Stereo 3a.</p>
     */
    public final static AUID SMPTE2035_Dual_Stereo_3a = Forge.makeAUID(
            0x04020210, (short) 0x0303, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Dual Stereo 3b.</p>
     */
    public final static AUID SMPTE2035_Dual_Stereo_3b = Forge.makeAUID(
            0x04020210, (short) 0x0303, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Commentary 4a.</p>
     */
    public final static AUID SMPTE2035_Mono_Commentary_4a = Forge.makeAUID(
            0x04020210, (short) 0x0304, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Commentary 4b.</p>
     */
    public final static AUID SMPTE2035_Mono_Commentary_4b = Forge.makeAUID(
            0x04020210, (short) 0x0304, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Commentary 4c.</p>
     */
    public final static AUID SMPTE2035_Mono_Commentary_4c = Forge.makeAUID(
            0x04020210, (short) 0x0304, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo International Sound 5a.</p>
     */
    public final static AUID SMPTE2035_Stereo_International_Sound_5a = Forge.makeAUID(
            0x04020210, (short) 0x0305, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo International Sound 5b.</p>
     */
    public final static AUID SMPTE2035_Stereo_International_Sound_5b = Forge.makeAUID(
            0x04020210, (short) 0x0305, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Program Sound 6a.</p>
     */
    public final static AUID SMPTE2035_Stereo_Program_Sound_6a = Forge.makeAUID(
            0x04020210, (short) 0x0306, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Program Sound 6b.</p>
     */
    public final static AUID SMPTE2035_Stereo_Program_Sound_6b = Forge.makeAUID(
            0x04020210, (short) 0x0306, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program Dialogue 7a.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_Dialogue_7a = Forge.makeAUID(
            0x04020210, (short) 0x0307, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program Dialogue 7b.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_Dialogue_7b = Forge.makeAUID(
            0x04020210, (short) 0x0307, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program Combo 8a.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_Combo_8a = Forge.makeAUID(
            0x04020210, (short) 0x0308, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program Combo 8b.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_Combo_8b = Forge.makeAUID(
            0x04020210, (short) 0x0308, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program Combo 8c.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_Combo_8c = Forge.makeAUID(
            0x04020210, (short) 0x0308, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program Combo 8d.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_Combo_8d = Forge.makeAUID(
            0x04020210, (short) 0x0308, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Program Combo 8e.</p>
     */
    public final static AUID SMPTE2035_Mono_Program_Combo_8e = Forge.makeAUID(
            0x04020210, (short) 0x0308, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Programs Combo 8f.</p>
     */
    public final static AUID SMPTE2035_Mono_Programs_Combo_8f = Forge.makeAUID(
            0x04020210, (short) 0x0308, (short) 0x0600,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Mono Programs Combo 8g.</p>
     */
    public final static AUID SMPTE2035_Mono_Programs_Combo_8g = Forge.makeAUID(
            0x04020210, (short) 0x0308, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Program Combo 9a.</p>
     */
    public final static AUID SMPTE2035_Stereo_Program_Combo_9a = Forge.makeAUID(
            0x04020210, (short) 0x0309, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Program Combo 9b.</p>
     */
    public final static AUID SMPTE2035_Stereo_Program_Combo_9b = Forge.makeAUID(
            0x04020210, (short) 0x0309, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Program Combo 9c.</p>
     */
    public final static AUID SMPTE2035_Stereo_Program_Combo_9c = Forge.makeAUID(
            0x04020210, (short) 0x0309, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Program Combo 9d.</p>
     */
    public final static AUID SMPTE2035_Stereo_Program_Combo_9d = Forge.makeAUID(
            0x04020210, (short) 0x0309, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Program Combo 9e.</p>
     */
    public final static AUID SMPTE2035_Stereo_Program_Combo_9e = Forge.makeAUID(
            0x04020210, (short) 0x0309, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Stereo Programs Combo 9f.</p>
     */
    public final static AUID SMPTE2035_Stereo_Programs_Combo_9f = Forge.makeAUID(
            0x04020210, (short) 0x0309, (short) 0x0600,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Multi-Channel Channel Non-PCM 10a.</p>
     */
    public final static AUID SMPTE2035_MultiChannel_Channel_NonPCM_10a = Forge.makeAUID(
            0x04020210, (short) 0x030A, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Multi-Channel Program Combo 11a.</p>
     */
    public final static AUID SMPTE2035_MultiChannel_Program_Combo_11a = Forge.makeAUID(
            0x04020210, (short) 0x030B, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Multi-Channel Program Combo 11b.</p>
     */
    public final static AUID SMPTE2035_MultiChannel_Program_Combo_11b = Forge.makeAUID(
            0x04020210, (short) 0x030B, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Multi-Channel Program Combo 11c.</p>
     */
    public final static AUID SMPTE2035_MultiChannel_Program_Combo_11c = Forge.makeAUID(
            0x04020210, (short) 0x030B, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Multi-Channel Program Combo 11d.</p>
     */
    public final static AUID SMPTE2035_MultiChannel_Program_Combo_11d = Forge.makeAUID(
            0x04020210, (short) 0x030B, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Multi-Channel Program Combo 11e.</p>
     */
    public final static AUID SMPTE2035_MultiChannel_Program_Combo_11e = Forge.makeAUID(
            0x04020210, (short) 0x030B, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Multi-Channel Program Combo 11f.</p>
     */
    public final static AUID SMPTE2035_MultiChannel_Program_Combo_11f = Forge.makeAUID(
            0x04020210, (short) 0x030B, (short) 0x0600,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Multi-Channel Program Combo 11g.</p>
     */
    public final static AUID SMPTE2035_MultiChannel_Program_Combo_11g = Forge.makeAUID(
            0x04020210, (short) 0x030B, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Multi-Channel Program Combo 11h.</p>
     */
    public final static AUID SMPTE2035_MultiChannel_Program_Combo_11h = Forge.makeAUID(
            0x04020210, (short) 0x030B, (short) 0x0800,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Multi-Channel Program Combo 11i.</p>
     */
    public final static AUID SMPTE2035_MultiChannel_Program_Combo_11i = Forge.makeAUID(
            0x04020210, (short) 0x030B, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 Dual Stereo Multi-Channel 12a.</p>
     */
    public final static AUID SMPTE2035_Dual_Stereo_MultiChannel_12a = Forge.makeAUID(
            0x04020210, (short) 0x030C, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 12-Track Stereo Programs Plus Multi-Channel Program 13a.</p>
     */
    public final static AUID SMPTE2035_12Track_Stereo_Programs_Plus_MultiChannel_Program_13a = Forge.makeAUID(
            0x04020210, (short) 0x030D, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 12-Track Stereo Dual-Language Programs Plus Multi-Channel-Program 13b.</p>
     */
    public final static AUID SMPTE2035_12Track_Stereo_DualLanguage_Programs_Plus_MultiChannelProgram_13b = Forge.makeAUID(
            0x04020210, (short) 0x030D, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-2035 12-Track Stereo Dual-Language Programs Plus Multi-Channel-Coded-Audio 13c.</p>
     */
    public final static AUID SMPTE2035_12Track_Stereo_DualLanguage_Programs_Plus_MultiChannelCodedAudio_13c = Forge.makeAUID(
            0x04020210, (short) 0x030D, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>SMPTE-12M 23.98fps Inactive User Bits Drop Frame Inactive.</p>
     */
    public final static AUID SMPTE12M_2398fps_Inactive_User_Bits_Drop_Frame_Inactive = Forge.makeAUID(
            0x04040102, (short) 0x0101, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 23.98fps Inactive User Bits Drop Frame Active.</p>
     */
    public final static AUID SMPTE12M_2398fps_Inactive_User_Bits_Drop_Frame_Active = Forge.makeAUID(
            0x04040102, (short) 0x0101, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 24fps Inactive User Bits No Drop Frame.</p>
     */
    public final static AUID SMPTE12M_24fps_Inactive_User_Bits_No_Drop_Frame = Forge.makeAUID(
            0x04040102, (short) 0x0101, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 25fps Inactive User Bits No Drop Frame.</p>
     */
    public final static AUID SMPTE12M_25fps_Inactive_User_Bits_No_Drop_Frame = Forge.makeAUID(
            0x04040102, (short) 0x0101, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 29.97fps Inactive User Bits Drop Frame Inactive.</p>
     */
    public final static AUID SMPTE12M_2997fps_Inactive_User_Bits_Drop_Frame_Inactive = Forge.makeAUID(
            0x04040102, (short) 0x0101, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 29.97fps Inactive User Bits Drop Frame Active.</p>
     */
    public final static AUID SMPTE12M_2997fps_Inactive_User_Bits_Drop_Frame_Active = Forge.makeAUID(
            0x04040102, (short) 0x0101, (short) 0x0401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 30fps Inactive User Bits No Drop Frame.</p>
     */
    public final static AUID SMPTE12M_30fps_Inactive_User_Bits_No_Drop_Frame = Forge.makeAUID(
            0x04040102, (short) 0x0101, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 23.98fps Active User Bits Drop Frame Inactive.</p>
     */
    public final static AUID SMPTE12M_2398fps_Active_User_Bits_Drop_Frame_Inactive = Forge.makeAUID(
            0x04040102, (short) 0x0102, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 23.98fps Active User Bits Drop Frame Active.</p>
     */
    public final static AUID SMPTE12M_2398fps_Active_User_Bits_Drop_Frame_Active = Forge.makeAUID(
            0x04040102, (short) 0x0102, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 24fps Active User Bits No Drop Frame.</p>
     */
    public final static AUID SMPTE12M_24fps_Active_User_Bits_No_Drop_Frame = Forge.makeAUID(
            0x04040102, (short) 0x0102, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 25fps Active User Bits No Drop Frame.</p>
     */
    public final static AUID SMPTE12M_25fps_Active_User_Bits_No_Drop_Frame = Forge.makeAUID(
            0x04040102, (short) 0x0102, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 29.97fps Active User Bits Drop Frame Inactive.</p>
     */
    public final static AUID SMPTE12M_2997fps_Active_User_Bits_Drop_Frame_Inactive = Forge.makeAUID(
            0x04040102, (short) 0x0102, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 29.97fps Active User Bits Drop Frame Active.</p>
     */
    public final static AUID SMPTE12M_2997fps_Active_User_Bits_Drop_Frame_Active = Forge.makeAUID(
            0x04040102, (short) 0x0102, (short) 0x0401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 30fps Active User Bits No Drop Frame.</p>
     */
    public final static AUID SMPTE12M_30fps_Active_User_Bits_No_Drop_Frame = Forge.makeAUID(
            0x04040102, (short) 0x0102, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 23.98fps Datecode User Bits Drop Frame Inactive.</p>
     */
    public final static AUID SMPTE12M_2398fps_Datecode_User_Bits_Drop_Frame_Inactive = Forge.makeAUID(
            0x04040102, (short) 0x0103, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 23.98fps Datecode User Bits Drop Frame Active.</p>
     */
    public final static AUID SMPTE12M_2398fps_Datecode_User_Bits_Drop_Frame_Active = Forge.makeAUID(
            0x04040102, (short) 0x0103, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 24fps Datecode User Bits No Drop Frame.</p>
     */
    public final static AUID SMPTE12M_24fps_Datecode_User_Bits_No_Drop_Frame = Forge.makeAUID(
            0x04040102, (short) 0x0103, (short) 0x0200,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 25fps Datecode User Bits No Drop Frame.</p>
     */
    public final static AUID SMPTE12M_25fps_Datecode_User_Bits_No_Drop_Frame = Forge.makeAUID(
            0x04040102, (short) 0x0103, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 29.97fps Datecode User Bits Drop Frame Inactive.</p>
     */
    public final static AUID SMPTE12M_2997fps_Datecode_User_Bits_Drop_Frame_Inactive = Forge.makeAUID(
            0x04040102, (short) 0x0103, (short) 0x0400,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 29.97fps Datecode User Bits Drop Frame Active.</p>
     */
    public final static AUID SMPTE12M_2997fps_Datecode_User_Bits_Drop_Frame_Active = Forge.makeAUID(
            0x04040102, (short) 0x0103, (short) 0x0401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>SMPTE-12M 30fps Datecode User Bits No Drop Frame.</p>
     */
    public final static AUID SMPTE12M_30fps_Datecode_User_Bits_No_Drop_Frame = Forge.makeAUID(
            0x04040102, (short) 0x0103, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>Manual Exposure.</p>
     */
    public final static AUID Manual_Exposure = Forge.makeAUID(
            0x05100101, (short) 0x0101, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>Full Auto Exposure.</p>
     */
    public final static AUID Full_Auto_Exposure = Forge.makeAUID(
            0x05100101, (short) 0x0102, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>Gain Priority Auto Exposure.</p>
     */
    public final static AUID Gain_Priority_Auto_Exposure = Forge.makeAUID(
            0x05100101, (short) 0x0103, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>Iris Priority Auto Exposure.</p>
     */
    public final static AUID Iris_Priority_Auto_Exposure = Forge.makeAUID(
            0x05100101, (short) 0x0104, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>Shutter Priority Auto Exposure.</p>
     */
    public final static AUID Shutter_Priority_Auto_Exposure = Forge.makeAUID(
            0x05100101, (short) 0x0105, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x0B } );

    /**
     * <p>MXF OP1a SingleItem SinglePackage UniTrack Stream Internal.</p>
     */
    public final static AUID MXF_OP1a_SingleItem_SinglePackage_UniTrack_Stream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0101, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1a SingleItem SinglePackage UniTrack Stream External.</p>
     */
    public final static AUID MXF_OP1a_SingleItem_SinglePackage_UniTrack_Stream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0101, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1a SingleItem SinglePackage UniTrack NonStream Internal.</p>
     */
    public final static AUID MXF_OP1a_SingleItem_SinglePackage_UniTrack_NonStream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0101, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1a SingleItem SinglePackage UniTrack NonStream External.</p>
     */
    public final static AUID MXF_OP1a_SingleItem_SinglePackage_UniTrack_NonStream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0101, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1a SingleItem SinglePackage MultiTrack Stream Internal.</p>
     */
    public final static AUID MXF_OP1a_SingleItem_SinglePackage_MultiTrack_Stream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0101, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1a SingleItem SinglePackage MultiTrack Stream External.</p>
     */
    public final static AUID MXF_OP1a_SingleItem_SinglePackage_MultiTrack_Stream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0101, (short) 0x0B00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1a SingleItem SinglePackage MultiTrack NonStream Internal.</p>
     */
    public final static AUID MXF_OP1a_SingleItem_SinglePackage_MultiTrack_NonStream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0101, (short) 0x0D00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1a SingleItem SinglePackage MultiTrack NonStream External.</p>
     */
    public final static AUID MXF_OP1a_SingleItem_SinglePackage_MultiTrack_NonStream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0101, (short) 0x0F00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1b SingleItem GangedPackages UniTrack Stream Internal.</p>
     */
    public final static AUID MXF_OP1b_SingleItem_GangedPackages_UniTrack_Stream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0102, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1b SingleItem GangedPackages UniTrack Stream External.</p>
     */
    public final static AUID MXF_OP1b_SingleItem_GangedPackages_UniTrack_Stream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0102, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1b SingleItem GangedPackages UniTrack NonStream Internal.</p>
     */
    public final static AUID MXF_OP1b_SingleItem_GangedPackages_UniTrack_NonStream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0102, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1b SingleItem GangedPackages UniTrack NonStream External.</p>
     */
    public final static AUID MXF_OP1b_SingleItem_GangedPackages_UniTrack_NonStream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0102, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1b SingleItem GangedPackages MultiTrack Stream Internal.</p>
     */
    public final static AUID MXF_OP1b_SingleItem_GangedPackages_MultiTrack_Stream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0102, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1b SingleItem GangedPackages MultiTrack Stream External.</p>
     */
    public final static AUID MXF_OP1b_SingleItem_GangedPackages_MultiTrack_Stream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0102, (short) 0x0B00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1b SingleItem GangedPackages MultiTrack NonStream Internal.</p>
     */
    public final static AUID MXF_OP1b_SingleItem_GangedPackages_MultiTrack_NonStream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0102, (short) 0x0D00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1b SingleItem GangedPackages MultiTrack NonStream External.</p>
     */
    public final static AUID MXF_OP1b_SingleItem_GangedPackages_MultiTrack_NonStream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0102, (short) 0x0F00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1c SingleItem AlternatePackages UniTrack Stream Internal.</p>
     */
    public final static AUID MXF_OP1c_SingleItem_AlternatePackages_UniTrack_Stream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0103, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1c SingleItem AlternatePackages UniTrack Stream External.</p>
     */
    public final static AUID MXF_OP1c_SingleItem_AlternatePackages_UniTrack_Stream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0103, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1c SingleItem AlternatePackages UniTrack NonStream Internal.</p>
     */
    public final static AUID MXF_OP1c_SingleItem_AlternatePackages_UniTrack_NonStream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0103, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1c SingleItem AlternatePackages UniTrack NonStream External.</p>
     */
    public final static AUID MXF_OP1c_SingleItem_AlternatePackages_UniTrack_NonStream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0103, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1c SingleItem AlternatePackages MultiTrack Stream Internal.</p>
     */
    public final static AUID MXF_OP1c_SingleItem_AlternatePackages_MultiTrack_Stream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0103, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1c SingleItem AlternatePackages MultiTrack Stream External.</p>
     */
    public final static AUID MXF_OP1c_SingleItem_AlternatePackages_MultiTrack_Stream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0103, (short) 0x0B00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1c SingleItem AlternatePackages MultiTrack NonStream Internal.</p>
     */
    public final static AUID MXF_OP1c_SingleItem_AlternatePackages_MultiTrack_NonStream_Internal = Forge.makeAUID(
            0x0D010201, (short) 0x0103, (short) 0x0D00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP1c SingleItem AlternatePackages MultiTrack NonStream External.</p>
     */
    public final static AUID MXF_OP1c_SingleItem_AlternatePackages_MultiTrack_NonStream_External = Forge.makeAUID(
            0x0D010201, (short) 0x0103, (short) 0x0F00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage UniTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_UniTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage UniTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_UniTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0110,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage UniTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_UniTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage UniTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_UniTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0310,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage UniTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_UniTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage UniTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_UniTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0510,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage UniTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_UniTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage UniTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_UniTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0710,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage MultiTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_MultiTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage MultiTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_MultiTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0910,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage MultiTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_MultiTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0B00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage MultiTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_MultiTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0B10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage MultiTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_MultiTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0D00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage MultiTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_MultiTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0D10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage MultiTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_MultiTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0F00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2a PlaylistItems SinglePackage MultiTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP2a_PlaylistItems_SinglePackage_MultiTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0201, (short) 0x0F10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages UniTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_UniTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages UniTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_UniTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0110,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages UniTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_UniTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages UniTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_UniTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0310,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages UniTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_UniTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages UniTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_UniTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0510,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages UniTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_UniTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages UniTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_UniTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0710,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages MultiTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_MultiTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages MultiTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_MultiTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0910,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages MultiTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_MultiTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0B00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages MultiTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_MultiTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0B10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages MultiTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_MultiTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0D00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages MultiTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_MultiTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0D10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages MultiTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_MultiTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0F00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2b PlaylistItems GangedPackages MultiTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP2b_PlaylistItems_GangedPackages_MultiTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0202, (short) 0x0F10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages UniTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_UniTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages UniTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_UniTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0110,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages UniTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_UniTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages UniTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_UniTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0310,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages UniTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_UniTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages UniTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_UniTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0510,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages UniTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_UniTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages UniTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_UniTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0710,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages MultiTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_MultiTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages MultiTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_MultiTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0910,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages MultiTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_MultiTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0B00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages MultiTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_MultiTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0B10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages MultiTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_MultiTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0D00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages MultiTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_MultiTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0D10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages MultiTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_MultiTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0F00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP2c PlaylistItems AlternatePackages MultiTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP2c_PlaylistItems_AlternatePackages_MultiTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0203, (short) 0x0F10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage UniTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_UniTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage UniTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_UniTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0110,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage UniTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_UniTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage UniTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_UniTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0310,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage UniTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_UniTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage UniTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_UniTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0510,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage UniTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_UniTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage UniTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_UniTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0710,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage MultiTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_MultiTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage MultiTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_MultiTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0910,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage MultiTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_MultiTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0B00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage MultiTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_MultiTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0B10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage MultiTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_MultiTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0D00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage MultiTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_MultiTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0D10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage MultiTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_MultiTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0F00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3a EditItems SinglePackage MultiTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP3a_EditItems_SinglePackage_MultiTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0301, (short) 0x0F10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages UniTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_UniTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages UniTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_UniTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0110,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages UniTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_UniTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages UniTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_UniTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0310,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages UniTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_UniTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages UniTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_UniTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0510,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages UniTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_UniTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages UniTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_UniTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0710,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages MultiTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_MultiTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages MultiTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_MultiTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0910,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages MultiTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_MultiTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0B00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages MultiTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_MultiTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0B10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages MultiTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_MultiTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0D00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages MultiTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_MultiTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0D10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages MultiTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_MultiTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0F00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3b EditItems GangedPackages MultiTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP3b_EditItems_GangedPackages_MultiTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0302, (short) 0x0F10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages UniTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_UniTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0100,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages UniTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_UniTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0110,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages UniTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_UniTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0300,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages UniTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_UniTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0310,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages UniTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_UniTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0500,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages UniTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_UniTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0510,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages UniTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_UniTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0700,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages UniTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_UniTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0710,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages MultiTrack Stream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_MultiTrack_Stream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0900,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages MultiTrack Stream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_MultiTrack_Stream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0910,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages MultiTrack Stream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_MultiTrack_Stream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0B00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages MultiTrack Stream External MayProcess.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_MultiTrack_Stream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0B10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages MultiTrack NonStream Internal NoProcessing.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_MultiTrack_NonStream_Internal_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0D00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages MultiTrack NonStream Internal MayProcess.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_MultiTrack_NonStream_Internal_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0D10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages MultiTrack NonStream External NoProcessing.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_MultiTrack_NonStream_External_NoProcessing = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0F00,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF OP3c EditItems AlternatePackages MultiTrack NonStream External MayProcess.</p>
     */
    public final static AUID MXF_OP3c_EditItems_AlternatePackages_MultiTrack_NonStream_External_MayProcess = Forge.makeAUID(
            0x0D010201, (short) 0x0303, (short) 0x0F10,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-OP Atom 1 Track 1 SourceClip.</p>
     */
    public final static AUID MXFOP_Atom_1_Track_1_SourceClip = Forge.makeAUID(
            0x0D010201, (short) 0x1000, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-OP Atom 1 Track N SourceClips.</p>
     */
    public final static AUID MXFOP_Atom_1_Track_N_SourceClips = Forge.makeAUID(
            0x0D010201, (short) 0x1001, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-OP Atom N Tracks 1 SourceClip.</p>
     */
    public final static AUID MXFOP_Atom_N_Tracks_1_SourceClip = Forge.makeAUID(
            0x0D010201, (short) 0x1002, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-OP Atom N Tracks N SourceClips.</p>
     */
    public final static AUID MXFOP_Atom_N_Tracks_N_SourceClips = Forge.makeAUID(
            0x0D010201, (short) 0x1003, (short) 0x0000,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 625x50I 50Mbps DefinedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_50Mbps_DefinedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 625x50I 50Mbps ExtendedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_50Mbps_ExtendedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 625x50I 50Mbps PictureOnly.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_50Mbps_PictureOnly = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x017F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 525x59.94I 50Mbps DefinedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_50Mbps_DefinedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 525x59.94I 50Mbps ExtendedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_50Mbps_ExtendedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 525x59.94I 50Mbps PictureOnly.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_50Mbps_PictureOnly = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x027F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 625x50I 40Mbps DefinedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_40Mbps_DefinedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 625x50I 40Mbps ExtendedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_40Mbps_ExtendedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 625x50I 40Mbps PictureOnly.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_40Mbps_PictureOnly = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x037F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 525x59.94I 40Mbps DefinedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_40Mbps_DefinedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 525x59.94I 40Mbps ExtendedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_40Mbps_ExtendedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 525x59.94I 40Mbps PictureOnly.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_40Mbps_PictureOnly = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x047F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 625x50I 30Mbps DefinedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_30Mbps_DefinedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 625x50I 30Mbps ExtendedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_30Mbps_ExtendedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 625x50I 30Mbps PictureOnly.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_30Mbps_PictureOnly = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x057F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 525x59.94I 30Mbps DefinedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_30Mbps_DefinedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 525x59.94I 30Mbps ExtendedTemplate.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_30Mbps_ExtendedTemplate = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x0602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-10 525x59.94I 30Mbps PictureOnly.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_30Mbps_PictureOnly = Forge.makeAUID(
            0x0D010301, (short) 0x0201, (short) 0x067F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped IEC-DV 525x59.94I 25Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_IECDV_525x5994I_25Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped IEC-DV 525x59.94I 25Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_IECDV_525x5994I_25Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x0102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped IEC-DV 625x50I 25Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_IECDV_625x50I_25Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x0201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped IEC-DV 625x50I 25Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_IECDV_625x50I_25Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x0202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped IEC-DV 525x59.94I 25Mbps SMPTE-322M.</p>
     */
    public final static AUID MXFGC_Framewrapped_IECDV_525x5994I_25Mbps_SMPTE322M = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x0301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped IEC-DV 525x59.94I 25Mbps SMPTE-322M.</p>
     */
    public final static AUID MXFGC_Clipwrapped_IECDV_525x5994I_25Mbps_SMPTE322M = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x0302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped IEC-DV 625x50I 25Mbps SMPTE-322M.</p>
     */
    public final static AUID MXFGC_Framewrapped_IECDV_625x50I_25Mbps_SMPTE322M = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x0401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped IEC-DV 625x50I 25Mbps SMPTE-322M.</p>
     */
    public final static AUID MXFGC_Clipwrapped_IECDV_625x50I_25Mbps_SMPTE322M = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x0402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped IEC-DV UndefinedSource 25Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_IECDV_UndefinedSource_25Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x3F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped IEC-DV UndefinedSource 25Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_IECDV_UndefinedSource_25Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x3F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped DV-based 525x59.94I 25Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_DVbased_525x5994I_25Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x4001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped DV-based 525x59.94I 25Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_DVbased_525x5994I_25Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x4002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped DV-based 625x50I 25Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_DVbased_625x50I_25Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x4101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped DV-based 625x50I 25Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_DVbased_625x50I_25Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x4102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped DV-based 525x59.94I 50Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_DVbased_525x5994I_50Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x5001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped DV-based 525x59.94I 50Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_DVbased_525x5994I_50Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x5002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped DV-based 625x50I 50Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_DVbased_625x50I_50Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x5101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped DV-based 625x50I 50Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_DVbased_625x50I_50Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x5102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped DV-based 1080x59.94I 100Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_DVbased_1080x5994I_100Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x6001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped DV-based 1080x59.94I 100Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_DVbased_1080x5994I_100Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x6002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped DV-based 1080x50I 100Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_DVbased_1080x50I_100Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x6101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped DV-based 1080x50I 100Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_DVbased_1080x50I_100Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x6102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped DV-based 720x59.94P 100Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_DVbased_720x5994P_100Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x6201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped DV-based 720x59.94P 100Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_DVbased_720x5994P_100Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x6202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped DV-based 720x50P 100Mbps.</p>
     */
    public final static AUID MXFGC_Framewrapped_DVbased_720x50P_100Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x6301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped DV-based 720x50P 100Mbps.</p>
     */
    public final static AUID MXFGC_Clipwrapped_DVbased_720x50P_100Mbps = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x6302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped DV-based UndefinedSource.</p>
     */
    public final static AUID MXFGC_Framewrapped_DVbased_UndefinedSource = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x7F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped DV-based UndefinedSource.</p>
     */
    public final static AUID MXFGC_Clipwrapped_DVbased_UndefinedSource = Forge.makeAUID(
            0x0D010301, (short) 0x0202, (short) 0x7F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x23.98PsF Defined Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x2398PsF_Defined_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x23.98PsF Extended Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x2398PsF_Extended_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x23.98PsF Picture Only.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x2398PsF_Picture_Only = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x017F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x24PsF Defined Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x24PsF_Defined_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x24PsF Extended Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x24PsF_Extended_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x24PsF Picture Only.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x24PsF_Picture_Only = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x027F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x25PsF Defined Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x25PsF_Defined_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x25PsF Extended Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x25PsF_Extended_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x25PsF Picture Only.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x25PsF_Picture_Only = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x037F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x29.97PsF Defined Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x2997PsF_Defined_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x29.97PsF Extended Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x2997PsF_Extended_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x29.97PsF Picture Only.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x2997PsF_Picture_Only = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x047F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x50I Defined Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x50I_Defined_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x50I Extended Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x50I_Extended_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x50I Picture Only.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x50I_Picture_Only = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x057F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x59.94I Defined Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x5994I_Defined_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x59.94I Extended Template.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x5994I_Extended_Template = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x0602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped SMPTE D-11 1080x59.94I Picture Only.</p>
     */
    public final static AUID MXFGC_Framewrapped_SMPTE_D11_1080x5994I_Picture_Only = Forge.makeAUID(
            0x0D010301, (short) 0x0203, (short) 0x067F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3C01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3C03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3C04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3C05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3C06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3C07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3C08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES ProgStreamMap SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_ProgStreamMap_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3C7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES PrivateStream-1 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3D01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES PrivateStream-1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES PrivateStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3D03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES PrivateStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3D04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES PrivateStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3D05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES PrivateStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3D06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES PrivateStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3D07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES PrivateStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3D08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES PrivateStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_PrivateStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3D7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3E01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3E03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3E04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3E05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3E06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3E07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3E08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES PaddingStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_PaddingStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3E7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES PrivateStream-2 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES PrivateStream-2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES PrivateStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES PrivateStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3F04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES PrivateStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES PrivateStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES PrivateStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES PrivateStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES PrivateStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_PrivateStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x3F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4004,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x407F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x417F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4204,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4208,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x427F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4304,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4308,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x437F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4403,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4404,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4405,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4406,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4407,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4408,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x447F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4503,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4504,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4505,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4506,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4507,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4508,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x457F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4603,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4604,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4605,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4606,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4607,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4608,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x467F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4701,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4703,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4704,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4705,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4706,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4707,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4708,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x477F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4801,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4803,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4804,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4805,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4806,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Custom ClosedGOP-wrapped MPEG-ES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_Custom_ClosedGOPwrapped_MPEGES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4807,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4808,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x487F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4901,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4903,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4904,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4905,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4906,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4907,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4908,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x497F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4A01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4A03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4A04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4A05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4A06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4A07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4A08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4A7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4B01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4B03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4B04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4B05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4B06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4B07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4B08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4B7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4C01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4C03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4C04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4C05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4C06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4C07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4C08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4C7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4D01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4D03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4D04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4D05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4D06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4D07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4D08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4D7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4E01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4E03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4E04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4E05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4E06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4E07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4E08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4E7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Custom Stripe-wrapped MPEG-ES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_Custom_Stripewrapped_MPEGES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4F04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x4F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5004,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-16 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream16_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x507F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-17 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream17_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x517F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5204,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5208,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-18 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream18_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x527F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5304,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5308,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-19 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream19_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x537F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5403,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5404,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5405,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5406,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5407,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5408,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-20 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream20_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x547F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5503,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5504,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5505,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5506,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5507,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5508,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-21 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream21_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x557F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5603,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5604,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5605,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5606,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5607,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5608,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-22 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream22_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x567F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5701,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5703,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5704,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5705,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5706,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5707,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5708,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-23 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream23_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x577F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5801,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5803,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5804,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5805,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5806,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5807,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5808,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-24 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream24_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x587F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5901,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5903,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5904,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5905,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5906,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5907,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5908,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-25 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream25_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x597F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5A01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5A03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5A04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5A05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5A06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5A07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5A08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-26 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream26_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5A7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5B01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5B03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5B04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5B05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5B06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5B07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5B08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-27 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream27_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5B7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5C01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5C03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5C04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5C05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5C06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5C07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5C08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-28 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream28_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5C7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5D01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5D03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5D04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5D05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5D06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5D07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5D08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-29 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream29_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5D7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5E01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5E03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5E04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5E05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5E06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5E07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5E08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-30 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream30_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5E7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5F04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AudioStream-31 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AudioStream31_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x5F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6004,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-0 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream0_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x607F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-1 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream1_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x617F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6204,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6208,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-2 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream2_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x627F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6304,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6308,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-3 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream3_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x637F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6403,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6404,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6405,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6406,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6407,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6408,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-4 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream4_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x647F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6503,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6504,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6505,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6506,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6507,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6508,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-5 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream5_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x657F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6603,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6604,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6605,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6606,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6607,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6608,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-6 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream6_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x667F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6701,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6703,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6704,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6705,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6706,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6707,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6708,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-7 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream7_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x677F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6801,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6803,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6804,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6805,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6806,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6807,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6808,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-8 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream8_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x687F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6901,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6903,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6904,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6905,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6906,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6907,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6908,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-9 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream9_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x697F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6A01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6A03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6A04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6A05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6A06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6A07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6A08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-10 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream10_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6A7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6B01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6B03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6B04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6B05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6B06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6B07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6B08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-11 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream11_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6B7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6C01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6C02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6C03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6C04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6C05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6C06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6C07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6C08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-12 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream12_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6C7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6D01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6D02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6D03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6D04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6D05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6D06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6D07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6D08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-13 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream13_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6D7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6E01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6E02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6E03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6E04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6E05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6E06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6E07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6E08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-14 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream14_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6E7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6F04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES VideoStream-15 SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_VideoStream15_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x6F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES ECMStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7001,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES ECMStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7002,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7003,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7004,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7005,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7006,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7007,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7008,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES ECMStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_ECMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x707F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES EMMStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES EMMStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7104,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7108,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES EMMStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_EMMStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x717F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7204,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7208,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES DSMCCStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_DSMCCStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x727F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7301,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7302,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7303,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7304,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7305,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7306,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7307,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7308,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES 13522Stream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_13522Stream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x737F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7401,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7402,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7403,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7404,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7405,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7406,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7407,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7408,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES ITURec222-A SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_ITURec222A_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x747F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7501,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7502,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7503,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7504,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7505,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7506,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7507,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7508,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES ITURec222-B SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_ITURec222B_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x757F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7601,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7602,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7603,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7604,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7605,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7606,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7607,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7608,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES ITURec222-C SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_ITURec222C_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x767F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7701,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7702,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7703,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7704,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7705,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7706,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7707,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7708,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES ITURec222-D SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_ITURec222D_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x777F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7801,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7802,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7803,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7804,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7805,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7806,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7807,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7808,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES ITURec222-E SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_ITURec222E_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x787F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES AncStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7901,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES AncStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7902,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7903,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7904,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7905,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7906,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7907,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7908,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES AncStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_AncStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x797F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7A01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7A02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7A03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7A04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7A05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7A06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7A07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7A08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES SLPackStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_SLPackStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7A7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7B01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7B02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7B03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7B04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7B05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7B06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7B07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7B08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES FlexMuxStream SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_FlexMuxStream_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7B7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped MPEG-ES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_Framewrapped_MPEGES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7F01,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Clip-wrapped MPEG-ES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_Clipwrapped_MPEGES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7F02,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomStripe-wrapped MPEG-ES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomStripewrapped_MPEGES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7F03,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomPES-wrapped MPEG-ES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomPESwrapped_MPEGES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7F04,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomFixedAudioSize-wrapped MPEG-ES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomFixedAudioSizewrapped_MPEGES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7F05,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSplice-wrapped MPEG-ES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomSplicewrapped_MPEGES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7F06,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomClosedGOP-wrapped MPEG-ES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7F07,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomSlave-wrapped MPEG-ES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomSlavewrapped_MPEGES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7F08,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC CustomUnconstrained-wrapped MPEG-ES ProgStreamDir SID.</p>
     */
    public final static AUID MXFGC_CustomUnconstrainedwrapped_MPEGES_ProgStreamDir_SID = Forge.makeAUID(
            0x0D010301, (short) 0x0204, (short) 0x7F7F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x02 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 525x59.94I 720 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_525x5994I_720_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0101,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 525x59.94I 720 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_525x5994I_720_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0102,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 525x59.94I 720 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_525x5994I_720_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0103,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 625x50I 720 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_625x50I_720_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0105,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 625x50I 720 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_625x50I_720_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0106,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 625x50I 720 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_625x50I_720_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0107,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 525x59.94I 960 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_525x5994I_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0109,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 525x59.94I 960 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_525x5994I_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x010A,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 525x59.94I 960 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_525x5994I_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x010B,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 625x50I 960 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_625x50I_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x010D,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 625x50I 960 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_625x50I_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x010E,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 625x50I 960 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_625x50I_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x010F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 525x59.94P 960 420.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_525x5994P_960_420 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0111,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 525x59.94P 960 420.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_525x5994P_960_420 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0112,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 525x59.94P 960 420.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_525x5994P_960_420 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0113,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 625x50P 960 420.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_625x50P_960_420 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0115,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 625x50P 960 420.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_625x50P_960_420 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0116,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 625x50P 960 420.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_625x50P_960_420 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0117,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 525x59.94P 960 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_525x5994P_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0119,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 525x59.94P 960 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_525x5994P_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x011A,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 525x59.94P 960 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_525x5994P_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x011B,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 625x50P 960 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_625x50P_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x011D,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 625x50P 960 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_625x50P_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x011E,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 625x50P 960 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_625x50P_960_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x011F,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 525x59.94I 960 4444.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_525x5994I_960_4444 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0121,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 525x59.94I 960 4444.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_525x5994I_960_4444 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0122,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 525x59.94I 960 4444.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_525x5994I_960_4444 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0123,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 625x50I 960 4444.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_625x50I_960_4444 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0125,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 625x50I 960 4444.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_625x50I_960_4444 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0126,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 625x50I 960 4444.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_625x50I_960_4444 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0127,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x23.98P 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x2398P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0201,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x23.98P 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x2398P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0202,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x23.98P 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x2398P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0203,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x23.98PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x2398PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0205,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x23.98PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x2398PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0206,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x23.98PsF 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x2398PsF_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0207,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Frame-wrapped Uncompressed 1080x24P 1920 422.</p>
     */
    public final static AUID MXFGC_Framewrapped_Uncompressed_1080x24P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0211,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Clip-wrapped Uncompressed 1080x24P 1920 422.</p>
     */
    public final static AUID MXFGC_Clipwrapped_Uncompressed_1080x24P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0212,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

    /**
     * <p>MXF-GC Line-wrapped Uncompressed 1080x24P 1920 422.</p>
     */
    public final static AUID MXFGC_Linewrapped_Uncompressed_1080x24P_1920_422 = Forge.makeAUID(
            0x0D010301, (short) 0x0205, (short) 0x0213,
            new byte[] { 0x06, 0x0E, 0x2B, 0x34, 0x04, 0x01, 0x01, 0x01 } );

}
