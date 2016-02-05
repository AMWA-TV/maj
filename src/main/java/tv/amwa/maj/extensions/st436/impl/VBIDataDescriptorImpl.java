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

package tv.amwa.maj.extensions.st436.impl;

import java.io.Serializable;

import tv.amwa.maj.extensions.st436.Constants;
import tv.amwa.maj.extensions.st436.VBIDataDescriptor;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.model.impl.DataEssenceDescriptorImpl;

/**
 * <p>Implements the description of VBI frame elements.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x5b00,
    uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
    definedName = "VBIDataDescriptor",
    description = "Specifies that a file source package is associated with VBI data.",
    symbol = "VBIDataDescriptor",
    namespace = Constants.XML_NAMESPACE,
    prefix = Constants.XML_PREFIX)
public class VBIDataDescriptorImpl
    extends DataEssenceDescriptorImpl
    implements VBIDataDescriptor,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 3515238591382159488L;

	@Override
	public VBIDataDescriptor clone() {
		return (VBIDataDescriptor) super.clone();
	}

}
