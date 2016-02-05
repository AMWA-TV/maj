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

package tv.amwa.maj.io.mxf;

import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>Constants used by the MXF capability of the MAJ API.</p>
 *
 *
 *
 */
public interface MXFConstants {

	/**
	 * <p>XML namespace representing definitions from the SMPTE RP210 metadictionary available
	 * at the <a href="http://www.smpte-ra.org/">SMPTE registration authority</a>. This namespace
	 * is used for elements required to read and write MXF files beyond those defined for AAF.</p>
	 *
	 * @see #RP210_PREFIX
	 */
	public static final String RP210_NAMESPACE = "http://www.smpte-ra.org/rp210";

	/**
	 * <p>Suggested prefix for the {@linkplain #RP210_NAMESPACE RP210 namespace},
	 * which is {@value #RP210_PREFIX}.</p>
	 *
	 * @see #RP210_NAMESPACE
	 */
	public static final String RP210_PREFIX = "mxf";

	/**
	 * <p>Key for the instance ID property of a local set. The instance ID property is used to represent strong
	 * references between {@linkplain tv.amwa.maj.industry.MetadataObject metadata objects}
	 * in {@linkplain HeaderMetadata header metadata}.</p>
	 *
	 * @see #InstanceTag
	 * @see LocalTagEntry#getLocalTag()
	 */
	public static final AUID InstanceUID = new AUIDImpl(
			0x01011502, (short) 0x0000, (short) 0x0000,
			new byte [] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 });

	/**
	 * <p>Tag for the instance ID property of a local set. The instance ID property is used
	 * to represent strong references between {@linkplain tv.amwa.maj.industry.MetadataObject metadata objects}
	 * in {@linkplain HeaderMetadata header metadata}.</p>
	 *
	 * @see #InstanceUID
	 * @see LocalTagEntry#getUID()
	 */
	public static final short InstanceTag = (short) 0x3c0a;

	/**
	 * <p>Key starting a KLV triplet representing fill data.</p>
	 *
	 * @see #KLVFill
	 */
	public static final AUID KLVFill = new AUIDImpl(
			0x03010210, (short) 0x0100, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02 } );

	/**
	 * <p>An older key for a KLV triplet representing fill data.</p>
	 *
	 * @see #KLVFillOld
	 */
	public static final AUID KLVFillOld = new AUIDImpl(
			0x03010210, (short) 0x0100, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 } );
}
