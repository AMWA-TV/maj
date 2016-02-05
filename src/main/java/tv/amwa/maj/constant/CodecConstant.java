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
 * $Log: CodecConstant.java,v $
 * Revision 1.7  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/01/23 14:20:25  vizigoth
 * Minor comment fixes.
 *
 * Revision 1.2  2007/12/12 12:29:54  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:47  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;

/**
 * <p>Implement this interface to access unique identifiers for codecs, such
 * as WAVE or JPEG. A codec is a program or system capable of performing encoding and decoding on a digital
 * data stream, which is often the case for the data streams represented by {@linkplain tv.amwa.maj.model.EssenceData
 * essence data} in the MAJ API. Additional information about each codec appears in the {@linkplain CodecDescription
 * codec description} that annotates each {@linkplain tv.amwa.maj.record.AUID} constant.</p> 
 * 
 * <p>See the <a href="package-summary.html#managingDefinitions">description of managing definitions</a> 
 * in the package summary for more details of how to use these constants and dynamically extend the range 
 * of supported codecs.</p>
 * 
 * @see tv.amwa.maj.constant.CodecDescription
 * @see tv.amwa.maj.model.CodecDefinition
 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionStrongReferenceSet
 * 
 *
 *
 */
public interface CodecConstant {

	/**
	 * <p>Defines a pass-through codec that performs no encoding or decoding on the data
	 * it represents.</p>
	 */
	@CodecDescription(dataDefinitions = { "DataDef_Unknown " }, 
				fileDescriptorClass = "")
	public final static AUID None = new tv.amwa.maj.record.impl.AUIDImpl(
			0x568fb761, (short) 0x9458, (short) 0x11d2, 
			new byte[] { (byte) 0x80, (byte) 0x89, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f } );

	/**
	 * <p>Codec that represents mainly audio data as a sequence of quantised values sampled from an analogue signal. 
	 * For more information, see the description of <a href="http://en.wikipedia.org/wiki/Pulse-code_modulation">pulse-coded 
	 * modulation on Wikipedia</a>. PCM data is typically audio data that is not compressed.</p>
	 * 
	 * @see tv.amwa.maj.model.WAVEPCMDescriptor
	 */
	@CodecDescription(fileDescriptorClass = "WAVEPCMDescriptor",
			dataDefinitions = { "DataDef_Sound", "DataDef_LegacySound" })
	public final static AUID PCM = new tv.amwa.maj.record.impl.AUIDImpl(
			0x90ac17c8, (short) 0xe3e2, (short) 0x4596, 
			new byte[] { (byte) 0x9e, (byte) 0x9e, (byte) 0xa6, (byte) 0xdd, 0x1c, 0x70, (byte) 0xc8, (byte) 0x92} );

	/**
	 * <p>Codec used to encode raw audio data files originating from Microsoft and IBM as the audio file format
	 * for PCs. WAVE is an acronym for <em>Waveform audio format</em>. For more information, see the 
	 * <a href="http://en.wikipedia.org/wiki/WAV">description of WAVE on Wikipedia</a>.</p>
	 * 
	 * @see tv.amwa.maj.model.WAVEDescriptor
	 */
	@CodecDescription(fileDescriptorClass = "WAVEDescriptor",
			dataDefinitions = { "DataDef_Sound", "DataDef_LegacySound" },
			description = "Handles RIFF WAVE data.")
	public final static AUID WAVE = new tv.amwa.maj.record.impl.AUIDImpl(
			0x820f09b1, (short) 0xeb9b, (short) 0x11d2, 
			new byte[] { (byte) 0x80, (byte) 0x9f, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f } );

	/**
	 * <p>The audio interchange file format with compression, where audio data is stored in chunks that
	 * are compressed according to various defined codecs. For more information, see the description of
	 * <a href="http://en.wikipedia.org/wiki/AIFF">AIFF on Wikipedia</a>. 
	 * 
	 * @see tv.amwa.maj.model.AIFCDescriptor
	 */
	@CodecDescription(fileDescriptorClass = "AIFCDescriptor",
			dataDefinitions = { "DataDef_Sound", "DataDef_LegacySound" },
			description = "Handles RIFF AIFC data.")
	public final static AUID AIFC = new tv.amwa.maj.record.impl.AUIDImpl(
			0x4b1c1a45, (short) 0x03f2, (short) 0x11d4, 
			new byte[] { (byte) 0x80, (byte) 0xfb, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f } );

	/**
	 * <p>A commonly used method for the compression of photographic images, names after the Joint
	 * Photographic Experts Group committee responsible for the standard. This particular constant
	 * refers to versions of JPEG that are compatible with Avid implementations. For more information
	 * on the JPEG format, see the <a href="http://en.wikipedia.org/wiki/JPEG">JPEG entry on
	 * Wikipedia</a>.</p>
	 * 
	 * @see tv.amwa.maj.model.CDCIDescriptor
	 */
	@CodecDescription(fileDescriptorClass = "CDCIDescriptor",
			dataDefinitions = { "DataDef_Picture", "DataDef_LegacyPicture" },
			description = "Supports a constrained form of ISO/IEC 10918-1 JPEG images which are compatible with Avid implementations.")
	public final static AUID JPEG = new tv.amwa.maj.record.impl.AUIDImpl(
			0x18634f8c, (short) 0x3bab, (short) 0x11d3, 
			new byte[] { (byte) 0xbf, (byte) 0xd6, 0x00, 0x10, 0x4b, (byte) 0xc9, 0x15, 0x6d} );

	/**
	 * <p>Codec handling either uncompressed YUV/YCbCr video data or compressed IEC 61834 DV family and DV-Based
	 * family video data. The different flavours of coding represented by this codec are defined by constants defined
	 * in the {@link CodecFlavour} interface. For more information about DV formats, see the description on the
	 * <a href="http://en.wikipedia.org/wiki/DV">DV Wikipedia entry</a>.</p>
	 * 
	 * @see tv.amwa.maj.model.CDCIDescriptor
	 */
	@CodecDescription(fileDescriptorClass = "CDCIDescriptor",
			dataDefinitions = { "DataDef_Picture", "DataDef_LegacyPicture" },
			description = "Handles uncompressed YUV and YCbCr and (compressed) IEC 61834 DV family and DV-Based family") 
	public final static AUID CDCI = new tv.amwa.maj.record.impl.AUIDImpl(
			0x4e84045e, (short) 0x0f29, (short) 0x11d4, 
			new byte[] { (byte) 0xa3, 0x59, 0x00, (byte) 0x90, 0x27, (byte) 0xdf, (byte) 0xca, 0x6a} );

	/**
	 * <p>An RGBA codec represents image data as separate red, green, blue and alpha transparency levels for
	 * each pixel, or using a color palette and lookup table. Image generated by computer systems are often
	 * coded using an RGBA color model.</p>
	 * 
	 * @see tv.amwa.maj.model.RGBADescriptor
	 */
	@CodecDescription(fileDescriptorClass = "RGBADescriptor",
			dataDefinitions = { "DataDef_Picture", "DataDef_LegacyPicture" })
	public final static AUID RGBA = new tv.amwa.maj.record.impl.AUIDImpl(
			0x4e84045f, (short) 0x0f29, (short) 0x11d4, 
			new byte[] { (byte) 0xa3, 0x59, 0x00, (byte) 0x90, 0x27, (byte) 0xdf, (byte) 0xca, 0x6a} );

}
