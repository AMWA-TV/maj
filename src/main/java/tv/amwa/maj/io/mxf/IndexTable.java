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

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.io.mxf.impl.IndexPrimerPackImpl;
import tv.amwa.maj.misctype.PositionType;

/**
 * <p>Represents an index table in an MXF file, providing a means to calculate byte
 * offsets for the indexed essence container.</p>
 *
 * <p>In a file, the index table is made up
 * of a sequence of one or more {@linkplain IndexTableSegment index table segments}.
 * In fact, the index table does not exist in the file as a single entity and must
 * be created by joining together all the segments with the same index stream
 * identifier.</p>
 *
 *
 *
 * @see IndexTableSegment
 * @see IndexEntry
 */
public interface IndexTable
	extends Cloneable {

	public final static PrimerPack indexPrimer = new IndexPrimerPackImpl();

	/**
	 * <p>Returns the index stream identifier for this index table within its
	 * MXF file.</p>
	 *
	 * @return Index stream identifier for this index table.
	 */
	public @UInt32 int getIndexSID();

	/**
	 * <p>Calculate the byte offset in the indexed stream of the frame at the
	 * given position.</p>
	 *
	 * @param position Edit unit number counted from the start of body.
	 * @param subStream Sub-stream number to calculate the offset for.
	 * @return
	 *
	 * @throws IllegalArgumentException
	 */
	public @UInt64 long streamOffset(
			@PositionType long position,
			@UInt32 int subStream)
		throws IllegalArgumentException;

	/**
	 * <p>Create a cloned copy of this index table.</p>
	 *
	 * @return Cloned copy of this index table.
	 */
	public IndexTable clone();

}
