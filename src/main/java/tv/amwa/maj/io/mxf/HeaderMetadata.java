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

import tv.amwa.maj.model.Preface;

/**
 * <p>Represents header metadata in an MXF file that must be present in the mandatory
 * {@linkplain HeaderPartition header partition} of every MXF file. Header metadata may
 * also be present in other partitions, either as an exact repeat (e.g. to provide join-in-progress
 * ability to read the stream) or because the file is grawing and the metadata keeps improving
 * as it goes.</p>
 *
 * <p>Header metadata consists of a
 * {@linkplain PrimerPack primer pack} and a {@linkplain tv.amwa.maj.model.Preface preface}.
 * The primer pack is used as a means of reducing the number of bytes required to
 * serialize the metadata in an MXF file as a local set.</p>
 *
 *
 *
 * @see MXFBuilder#readLocalSet(UL, java.nio.ByteBuffer, PrimerPack, java.util.Map, java.util.List)
 */
public interface HeaderMetadata
	extends Cloneable,
		MXFUnit {

	/**
	 * <p>Returns the primer pack used to encode the header metadata.</p>
	 *
	 * @return Primer pack used to encode the header metadata.
	 */
	public PrimerPack getPrimerPack();

	/**
	 * <p>Returns the preface that contains metadata records of the
	 * MXF file.</p>
	 *
	 * @return Metadata records of the MXF file.
	 */
	public Preface getPreface();

	/**
	 * <p>Create a cloned copy of the header metadata.</p>
	 *
	 * @return Cloned copy of the header metadata.
	 */
	public HeaderMetadata clone();

}
