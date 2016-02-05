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

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/**
 * <p>Picture type and coding type, as used in {@linkplain tv.amwa.maj.model.AVCSubDescriptor AVC sub descriptors}.</p>
 *
 *
 *
 * @see tv.amwa.maj.model.AVCSubDescriptor#getAVCCodedContentKind()
 * @see tv.amwa.maj.model.AVCSubDescriptor#setAVCCodedContentKind(AVCCodedContentKind)
 * @see tv.amwa.maj.industry.TypeDefinitions#AVCCodedContentKind
 */
public enum AVCCodedContentKind
	implements MediaEnumerationValue {

	/** <p>Unknown AVC coded content kind.</p> */
	Unknown (0),
	/** <p>Source image is progressive. Picture type is frame. Coding type is frame coding.</p> */
	ProgressiveFramePicture (1),
	/** <p>Source image is interlaced. Picture type is field. Coding type is field coding.</p> */
	InterlacedFieldPicture (2),
	/** <p>Source image is interlaced. Picture type is	frame. Coding type frame coding, including macroblock
	 *  adaptive frame-field coding.</p> */
	InterlacedFramePicture (3),
	/** <p>Source image is interlaced. Picture type is frame and field. Coding type is picture adaptive frame-field coding.
	 *  Frame coding may use macroblock adaptive frame-field coding.</p> */
	InterlacedFrameAndFieldPicture (4),
	/** <p>Reserved.</p> */
	Reserved (0xf);

    private final int value;

	AVCCodedContentKind(int value) { this.value = value; }

    @Int64 public long value() { return (long) value; }

    public String symbol() { return name(); }
}
