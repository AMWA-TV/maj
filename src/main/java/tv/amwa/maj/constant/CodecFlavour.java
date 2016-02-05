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
 * $Log: CodecFlavour.java,v $
 * Revision 1.4  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2007/12/12 12:29:54  vizigoth
 * Added to and edited document comments to a release level.
 *
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;

/**
 * <p>Implement this interface to access unique identifiers for flavours of codec.
 * These codec flavours can be used to specify a {@linkplain tv.amwa.maj.model.CodecDefinition
 * codec definition} in more detail than just using its {@linkplain CodecConstant codec constant}
 * allows. All the flavours listed here relate to the {@linkplain CodecConstant#CDCI DV family}
 * of codecs (CDCI) using DCT block-based compression.</p>
 * 
 *
 *
 */
public interface CodecFlavour {

	/**
	 * <p>No codec flavour is specified.</p>
	 */
	public final static AUID None = new tv.amwa.maj.record.impl.AUIDImpl(
			0x1b31f3b1, (short) 0x9450, (short) 0x11d2, 
			new byte[] { (byte) 0x80, (byte) 0x89, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });
	
	/**
	 * <p>DV stream representing 525 line video at 60Hz not complaint with standards defined
	 * for other codec flavours in this interface.</p>
	 */
	public final static AUID LegacyDV_625_50 = new tv.amwa.maj.record.impl.AUIDImpl(
			0xaf4de587, (short) 0x23d7, (short) 0x4c7c, 
			new byte[] { (byte) 0xb3, 0x7b, (byte) 0xc1, (byte) 0xc1, 0x38, 0x70, (byte) 0xe7, 0x11});
	
	/**
	 * <p>DV stream representing 625 line video at 50&nbsp;Hz not compliant with standards defined
	 * for other codec flavours in this interface.</p>
	 */
	public final static AUID LegacyDV_525_60 = new tv.amwa.maj.record.impl.AUIDImpl(
			0xaf4de587, (short) 0x23d7, (short) 0x4c7d, 
			new byte[] { (byte) 0xb3, 0x7b, (byte) 0xc1, (byte) 0xc1, 0x38, 0x70, (byte) 0xe7, 0x11});

	/**
	 * <p>DV stream compliant with IEC&nbsp;61834-2 for 625 line video at 50&nbsp;Hz. The video chroma
	 * format is 4:2:0 and this format is used for standards known as DV, miniDV and DVCam.</p>
	 */
	public final static AUID IEC_DV_625_50 = new tv.amwa.maj.record.impl.AUIDImpl(
			0xaf4de587, (short) 0x23d7, (short) 0x4c7e, 
			new byte[] { (byte) 0xb3, 0x7b, (byte) 0xc1, (byte) 0xc1, 0x38, 0x70, (byte) 0xe7, 0x11});
	
	/**
	 * <p>DV stream compliant with IEC&nbsp;61834-2 for 525 line video at 60&nbsp;Hz. The video chroma
	 * format is 4:1:1 and this flavour is used for standards known as DV, miniDV and DVCam.</p>
	 */
	public final static AUID IEC_DV_525_60 = new tv.amwa.maj.record.impl.AUIDImpl(
			0xaf4de587, (short) 0x23d7, (short) 0x4c7f, 
			new byte[] { (byte) 0xb3, 0x7b, (byte) 0xc1, (byte) 0xc1, 0x38, 0x70, (byte) 0xe7, 0x11});

	/**
	 * <p>DV stream compliant with SMPTE&nbsp;314M or SMPTE&nbsp;370M for 525 line video at 60Hz, 
	 * streamed at a rate of 25&nbsp;Mbps. The video chroma format is 4:1:1 and this flavour is used for
	 * standards commonly known as DVC Pro and Digital S.</p>
	 */
	public final static AUID DV_Based_25Mbps_525_60 = new tv.amwa.maj.record.impl.AUIDImpl(
			0xaf4de587, (short) 0x23d7, (short) 0x4c80, 
			new byte[] { (byte) 0xb3, 0x7b, (byte) 0xc1, (byte) 0xc1, 0x38, 0x70, (byte) 0xe7, 0x11});

	/**
	 * <p>DV stream compliant with SMPTE&nbsp;314M or SMPTE&nbsp;370M for 625 line video at 50Hz, 
	 * streamed at a rate of 25&nbsp;Mbps. The video chroma format is 4:1:1 and this flavour is used for
	 * standards commonly known as DVC Pro and Digital S.</p>
	 */
	public final static AUID DV_Based_25Mbps_625_50 = new tv.amwa.maj.record.impl.AUIDImpl(
			0xaf4de587, (short) 0x23d7, (short) 0x4c81, 
			new byte[] { (byte) 0xb3, 0x7b, (byte) 0xc1, (byte) 0xc1, 0x38, 0x70, (byte) 0xe7, 0x11});

	/**
	 * <p>DV stream compliant with SMPTE&nbsp;314M or SMPTE&nbsp;370M for 525 line video at 60Hz, 
	 * streamed at a rate of 50&nbsp;Mbps. The video chroma format is 4:2:2 and this flavour is used for
	 * standards commonly known as DVC Pro and Digital S.</p>
	 */
	public final static AUID DV_Based_50Mbps_525_60 = new tv.amwa.maj.record.impl.AUIDImpl(
			0xaf4de587, (short) 0x23d7, (short) 0x4c82, 
			new byte[] { (byte) 0xb3, 0x7b, (byte) 0xc1, (byte) 0xc1, 0x38, 0x70, (byte) 0xe7, 0x11});

	/**
	 * <p>DV stream compliant with SMPTE&nbsp;314M or SMPTE&nbsp;370M for 625 line video at 50Hz, 
	 * streamed at a rate of 50&nbsp;Mbps. The video chroma format is 4:2:2 and this flavour is used for
	 * standards commonly known as DVC Pro and Digital S.</p>
	 */
	public final static AUID DV_Based_50Mbps_625_50 = new tv.amwa.maj.record.impl.AUIDImpl(
			0xaf4de587, (short) 0x23d7, (short) 0x4c83, 
			new byte[] { (byte) 0xb3, 0x7b, (byte) 0xc1, (byte) 0xc1, 0x38, 0x70, (byte) 0xe7, 0x11});
}
