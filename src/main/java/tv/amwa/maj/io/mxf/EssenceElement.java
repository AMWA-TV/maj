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

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.record.AUID;

public interface EssenceElement
	extends MXFUnit {

	public static final AUID essenceElementBase = Forge.makeAUID(
			0x0d010301, (short) 0x0000, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x02, 0x01, 0x01 });

	public final static int ITEM_TYPE_INDEX = 12;
	public final static int ELEMENT_COUNT_INDEX = 13;
	public final static int ELEMENT_TYPE_INDEX = 14;
	public final static int ELEMENT_NUMBER_INDEX = 15;

	public final static byte CPPicture = 0x05;
	public final static byte CPSound = 0x06;
	public final static byte CPData = 0x07;
	public final static byte GCPicture = 0x15;
	public final static byte GCSound = 0x16;
	public final static byte GCData = 0x17;
	public final static byte GCCompound = 0x18;
}
