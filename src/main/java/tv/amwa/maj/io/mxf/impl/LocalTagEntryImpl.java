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
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.io.mxf.FixedLengthPack;
import tv.amwa.maj.io.mxf.LocalTagEntry;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.record.AUID;


@MediaClass(uuid1 = 0x0f721102, uuid2 = 0x0100, uuid3 = 0x0000,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
		definedName = "LocalTagEntry",
		description = "MAJ API implementation of a local tag lookup value.",
		symbol = "LocalTagEntry",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX)
public class LocalTagEntryImpl
	implements
		LocalTagEntry,
		FixedLengthPack,
		MetadataObject,
		Cloneable,
		MXFConstants {

	private final static String[] packOrder = { "LocalTag", "UID" };

	private short localTag;
	private AUID uid;

	public LocalTagEntryImpl() { }

	public LocalTagEntryImpl(
			@UInt16 short localTag,
			tv.amwa.maj.record.AUID uid)
		throws NullPointerException {

		if (uid == null)
			throw new NullPointerException("Cannot create a primer pack entry with a null uid.");

		setUID(uid);
		setLocalTag(localTag);
	}

	@MediaProperty(uuid1 = 0x01030602, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 },
			definedName = "Local Tag",
			aliases = {"Local Tag Value"},
			typeName = "UInt16",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "LocalTag")
	public @UInt16 short getLocalTag() {

		return localTag;
	}

	@MediaPropertySetter("Local Tag")
	public void setLocalTag(
			@UInt16 short localTag) {

		this.localTag = localTag;
	}

	@MediaProperty(uuid1 = 0x01030603, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 },
			definedName = "UID",
			aliases = {"Local Tag Unique ID"},
			typeName = "AUID",
			optional = false,
			uniqueIdentifier = true,
			pid = 0x0000,
			symbol = "UID")
	public AUID getUID() {

		return uid.clone();
	}

	@MediaPropertySetter("UID")
	public void setUID(
			tv.amwa.maj.record.AUID uid)
		throws NullPointerException {

		if (uid == null)
			throw new NullPointerException("Cannot set the UID of this primer pack entry with a null value.");

		this.uid = uid.clone();
	}

	public String toString() {

		return XMLBuilder.toXML(this);
	}

	public String[] getPackOrder() {

		return packOrder;
	}

	public LocalTagEntry clone() {

		try {
			return (LocalTagEntry) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			throw new InternalError("The cloning of local tag entries is supported.");
		}
	}
}
