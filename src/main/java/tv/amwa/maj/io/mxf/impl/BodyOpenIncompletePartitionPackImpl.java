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

package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.io.mxf.BodyOpenIncompletePartitionPack;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.UnitType;

@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0103, uuid3 = 0x0100,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01 },
		definedName = "BodyOpenIncompletePartitionPack",
		description = "A body partition found in the central section of an MXF file that is open and incomplete.",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX,
		symbol = "BodyOpenIncompletePartitionPack")
public class BodyOpenIncompletePartitionPackImpl
	extends
		BodyPartitionPackImpl
	implements
		BodyOpenIncompletePartitionPack,
		Cloneable,
		MetadataObject {

	public BodyOpenIncompletePartitionPack clone() {

		return (BodyOpenIncompletePartitionPack) super.clone();
	}

	@Override
	public UnitType getUnitType() {

		return UnitType.BodyOpenIncompletePartitionPack;
	}
}
