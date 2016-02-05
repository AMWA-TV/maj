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

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.record.AUID;

/**
* <p>Constant values, including the XML namespace, for package <code>tv.amwa.maj.extensions.st436</code>.</p>
*
*
*/
public interface Constants {

    /**
     * <p>Namespace for all symbols of the extension namespace.</p>
     *
     * @see #XML_PREFIX
     */
    public final static String XML_NAMESPACE =
        "http://www.smpte-ra.org/schemas/436/2006";

    /**
     * <p>Prefix for all symbols of the extension namespace.</p>
     *
     * @see #XML_NAMESPACE
     */
    public final static String XML_PREFIX = "st436";

    /**
     * <p>Identification of the extension namespace.</p>
     */
    public final static AUID EXTENSION_ID = Forge.makeAUID(
            0x10b5a52d, (short) 0x5645, (short) 0x427f,
            new byte[] { (byte) 0x97, 0x07, (byte) 0xe2, (byte) 0x86, (byte) 0xe5, 0x1b, (byte) 0xc3, (byte) 0xf7 } );

}
