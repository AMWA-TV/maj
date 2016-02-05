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

import java.io.Serializable;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.RandomIndexItem;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.impl.TypeDefinitionRecordImpl;

public class RandomIndexItemImpl
	implements
		XMLSerializable,
		Cloneable,
		Serializable,
		Comparable<RandomIndexItem>,
		RandomIndexItem,
		MXFConstants {

	private static final long serialVersionUID = -6518630576255869590L;

	private @UInt32 int bodySID;
	private @UInt64 long byteOffset;

	static {
		TypeDefinitionRecordImpl.registerInterfaceMapping(RandomIndexItem.class, RandomIndexItemImpl.class);
	}

	public RandomIndexItemImpl(
			@UInt32 int bodySID,
			@UInt64 long byteOffset)
		throws IllegalArgumentException {

		setBodySID(bodySID);
		setByteOffset(byteOffset);
	}

	/**
	 * <p>Create a cloned copy of this RandomIndexPackItemImpl.</p>
	 *
	 * @return Cloned copy of this RandomIndexPackItemImpl.
	 */
	public @UInt32 int getBodySID() {

		return bodySID;
	}

	/**
	 * <p>Create a cloned copy of this RandomIndexPackItemImpl.</p>
	 *
	 * @return Cloned copy of this RandomIndexPackItemImpl.
	 */
	public void setBodySID(
			@UInt32 int bodySID)
		throws IllegalArgumentException {

		if (bodySID < 0)
			throw new IllegalArgumentException("A body stream identifier cannot be a negative value.");

		this.bodySID = bodySID;
	}

	/**
	 * <p>Create a cloned copy of this RandomIndexPackItemImpl.</p>
	 *
	 * @return Cloned copy of this RandomIndexPackItemImpl.
	 */
	public @UInt64 long getByteOffset() {

		return byteOffset;
	}

	/**
	 * <p>Create a cloned copy of this RandomIndexPackItemImpl.</p>
	 *
	 * @return Cloned copy of this RandomIndexPackItemImpl.
	 */
	public void setByteOffset(
			@UInt64 long byteOffset)
		throws IllegalArgumentException {

		if (byteOffset < 0)
			throw new IllegalArgumentException("A byte offset from the begginning of a file cannot be a negative value.");

		this.byteOffset = byteOffset;
	}

	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof RandomIndexItem))
			return false;

		RandomIndexItem compareTo = (RandomIndexItem) o;

		if (compareTo.getBodySID() != bodySID) return false;
		if (compareTo.getByteOffset() != byteOffset) return false;

		return true;
	}

	public int hashCode() {

		return bodySID ^ Long.valueOf(byteOffset).hashCode();
	}

	public String toString() {

		return XMLBuilder.toXMLNonMetadata(this);
	}

	public final static String RANDOMINDEXITEM_TAG = "RandomIndexItem";
	public final static String BODYSID_TAG = "BodySID";
	public final static String BYTEOFFSET_TAG = "ByteOffset";

	public void appendXMLChildren(
			Node parent) {

		Node randomIndexItemNode = null;

		if (parent instanceof DocumentFragment)
			randomIndexItemNode = XMLBuilder.createChild(parent, RP210_NAMESPACE,
					RP210_PREFIX, RANDOMINDEXITEM_TAG);
		else
			randomIndexItemNode = parent;

		XMLBuilder.appendElement(randomIndexItemNode, RP210_NAMESPACE, RP210_PREFIX,
				BODYSID_TAG, bodySID);
		XMLBuilder.appendElement(randomIndexItemNode, RP210_NAMESPACE, RP210_PREFIX,
				BYTEOFFSET_TAG, byteOffset);
	}

	public String getComment() {

		return null;
	}

	public int compareTo(
			RandomIndexItem o) {

		if (byteOffset < o.getByteOffset()) return -1;
		if (byteOffset > o.getByteOffset()) return 1;
		return 0;
	}

	public RandomIndexItem clone() {

		try {
			return (RandomIndexItem) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			throw new InternalError(cnse.getMessage());
		}
	}

}
