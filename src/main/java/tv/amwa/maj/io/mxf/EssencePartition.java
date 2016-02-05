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

import java.io.IOException;
import java.nio.ByteBuffer;

import tv.amwa.maj.exception.InsufficientSpaceException;

public interface EssencePartition
	extends
		Partition,
		Cloneable {


	public EssenceContainer getEssenceContainer();

	public EssenceElement readEssenceElement();

	public void initializeWritingBody()
		throws NullPointerException,
			InsufficientSpaceException,
			IOException;

	public void writeEssenceElementHeader(
			byte itemType,
			byte elementType,
			byte elementCount,
			byte elementItem,
			long length)
		throws IOException;

	public void writeEssenceElementHeader(
			int trackNumber,
			long length)
		throws IOException;

	public void writeEssenceBlock(
			ByteBuffer essence)
		throws IOException;

	public long fillToEnd()
		throws IOException;

	public EssencePartition clone();
}
