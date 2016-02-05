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

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.io.mxf.HeaderMetadata;
import tv.amwa.maj.io.mxf.MXFBuilder;
import tv.amwa.maj.io.mxf.PrimerPack;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.mxf.UnitType;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.model.impl.PrefaceImpl;
import tv.amwa.maj.record.impl.AUIDImpl;

public class HeaderMetadataImpl
	implements
		HeaderMetadata,
		Cloneable {

	private PrimerPack primerPack;
	private Preface preface;
	private long primerPackPadding = 0;
	private long endPadding = 0;

	public HeaderMetadataImpl() { }

	public HeaderMetadataImpl(
			PrimerPack primerPack)
		throws NullPointerException {

		if (primerPack == null)
			throw new NullPointerException("Cannot create new header metadata using a null primer pack.");

		this.primerPack = primerPack;
	}

	public HeaderMetadataImpl(
			PrimerPack primerPack,
			Preface preface)
		throws NullPointerException {

		if (primerPack == null)
			throw new NullPointerException("Cannot create new header metadata using a null primer pack.");
		if (preface == null)
			throw new NullPointerException("Cannot create new header metadata using a null preface.");

		this.primerPack = primerPack;
		this.preface = preface;
	}

	public long getPrimerPackPadding() {

		return primerPackPadding;
	}

	void setPrimerPackPadding(
			long primerPackPadding) {

		if (primerPackPadding < 0)
			throw new IllegalArgumentException("Cannot have a negative number of bytes for padding!");

		this.primerPackPadding = primerPackPadding;
	}

	public long getEndPadding() {

		return endPadding;
	}

	void setEndPadding(
			long endPadding) {

		if (endPadding < 0)
			throw new IllegalArgumentException("Cannot have a negative number of bytes for padding!");

		this.endPadding = endPadding;
	}

	public HeaderMetadata clone() {

		try {
			return (HeaderMetadata) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Implements cloneable so should never happen
			throw new InternalError(cnse.getMessage());
		}
	}

	public Preface getPreface() {

		return preface;
	}

	public PrimerPack getPrimerPack() {

		return primerPack;
	}


	public final static HeaderMetadata createFromBuffer(
			ByteBuffer buffer)
		throws IllegalArgumentException {

		try {
			PrimerPack primerPack = PrimerPackImpl.createFromBytes(buffer);

			if (primerPack == null)
				throw new IllegalArgumentException("Problem reading a primer pack to create header metadata from a buffer.");

			HeaderMetadataImpl headerMetadata = new HeaderMetadataImpl(primerPack);

			headerMetadata.setPrimerPackPadding(MXFBuilder.skipKLVFill(buffer));

			headerMetadata.readPreface(buffer);

			return headerMetadata;
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Unable to create header metadata from the given buffer due to a " +
					e.getClass().getName() + ": " + e.getMessage());
		}
	}

	final Preface readPreface(
			ByteBuffer buffer) {

		Preface preface = null;
		long lastFillSize = -1;

		Map<AUIDImpl, MetadataObject> referenceTable = new HashMap<AUIDImpl, MetadataObject>();
		List<ResolutionEntry> resolutions = new Vector<ResolutionEntry>();

		int preserveLimit = buffer.limit();

		// TODO sort out exception handling
		try {
			while (buffer.hasRemaining()) {

				lastFillSize = MXFBuilder.skipKLVFill(buffer);
				if (lastFillSize > 0) continue;

				UL key = MXFBuilder.readKey(buffer);
				long length = MXFBuilder.readBERLength(buffer);
				buffer.limit((int) (buffer.position() + length));

				MetadataObject metadataFromFile =
					MXFBuilder.readLocalSet((AUIDImpl) key, buffer, primerPack, referenceTable, resolutions);

				buffer.limit(preserveLimit);

				if (metadataFromFile instanceof Preface)
					preface = (PrefaceImpl) metadataFromFile;

//				 if (metadataFromFile != null)
//					System.out.println(XMLBuilder.toXML(metadataFromFile));
			}
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + " thrown when reading a preface: " + e.getMessage());
		}

		setEndPadding(lastFillSize);

		// Resolve references
		for ( ResolutionEntry resolutionEntry : resolutions ) {
			resolutionEntry.resolve(referenceTable);
		}

		this.preface = preface;
		return preface;
	}

	@Override
	public UnitType getUnitType() {

		return UnitType.HeaderMetadata;
	}

}
