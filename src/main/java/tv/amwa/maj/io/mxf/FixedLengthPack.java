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

import tv.amwa.maj.industry.MetadataObject;

/**
 * <p>Labels a {@linkplain MetadataObject metadata object} that can be serialized as a
 * fixed length pack for which the order of the values in the buffer is important. All
 * properties of a fixed length pack must have a fixed length, with the exception of the
 * last property that is either of fixed size or is and array with length encoded as part
 * of the property.</p>
 *
 *
 *
 */
public interface FixedLengthPack
	extends MetadataObject {

	/**
	 * <p>Returns a list of property names in the order they need to be serialized
	 * to a stream.</p>
	 *
	 * @return List of property names in the order they need to be serialized.
	 *
	 * @see MXFBuilder#readFixedLengthPack(tv.amwa.maj.record.AUID, java.nio.ByteBuffer)
	 * @see MXFBuilder#writeFixedLengthPack(FixedLengthPack, java.nio.ByteBuffer)
	 */
	public String[] getPackOrder();
}
