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

package tv.amwa.maj.extensions.quantel.impl;

import java.io.Serializable;
import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.extensions.quantel.PolyFileDescriptor;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.impl.AAFFileDescriptorImpl;

/**
 * <p>PolyFileDescriptor Implementation.</p>
 *
 *
 */
@MediaClass(
    definedName = "PolyFileDescriptor",
    uuid1 = 0xca6ed57f, uuid2 = (short) 0xc7c7, uuid3 = (short) 0x4f9d,
    uuid4 = { (byte) 0xa9, 0x29, 0x6b, 0x40, (byte) 0xdf, 0x7f, 0x24, 0x7b },
    description = "",
    namespace = QConstants.XML_NAMESPACE,
    prefix = QConstants.XML_PREFIX,
    symbol = "PolyFileDescriptor")
public class PolyFileDescriptorImpl
    extends
        AAFFileDescriptorImpl
    implements
        PolyFileDescriptor,
        CommonConstants,
        Serializable,
        XMLSerializable,
        Cloneable {

    private static final long serialVersionUID = -2907156422426336840l;


    public PolyFileDescriptorImpl() { }

    public PolyFileDescriptor clone() {

        return (PolyFileDescriptor) super.clone();
    }

}
