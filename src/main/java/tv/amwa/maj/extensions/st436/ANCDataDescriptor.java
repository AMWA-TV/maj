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

package tv.amwa.maj.extensions.st436;

import tv.amwa.maj.model.DataEssenceDescriptor;

/**
 * <p>Descriptor for ANC frame elements. When ANC packets exist in an MXF file, the appropriate descriptor shall be present in the file.
 * The descriptor shall be associated with a data track using the mechanisms defined in SMPTE 377M.</p>
 *
 * <p>The type of data carried in the ANC data is determined packet-by-packet in the packet itself, as specified in SMPTE ST 0291
 * and its associated <a href="http://smpte-ra.org/S291/S291_reg.html">register</a>.</p>
 *
 *
 *
 */
public interface ANCDataDescriptor
	extends DataEssenceDescriptor {

	/**
	 * <p>Create a cloned copy of this ANC data descriptor.</p>
	 *
	 * @return Cloned copy of this ANC data descriptor.
	 */
	public ANCDataDescriptor clone();

}
