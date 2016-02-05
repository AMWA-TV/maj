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

package tv.amwa.maj.extensions.example;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/**
 * <p>Different genres of resource.</p>
 *
 * @see TypeDefinitions#DCMIType
 *
 *
 */
public enum DCMIType
    implements MediaEnumerationValue {

    /** <p>Moving image element.</p> */
    MovingImage (1l),
    /** <p>Still image element.</p> */
    StillImage (2l),
    /** <p>Sound element.</p> */
    Sound (3l),
    /** <p>Data set element.</p> */
    DataSet (4l),
    ;

    private final long value;

    DCMIType(long value) { this.value = value; }

    @Int64 public long value() { return value; }

    public String symbol() {

        return "DCMIType_" + name();
    }

}
