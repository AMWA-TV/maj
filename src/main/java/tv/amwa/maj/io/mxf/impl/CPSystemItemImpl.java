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

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.io.mxf.CPSystemItem;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.mxf.UnitType;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;

public class CPSystemItemImpl
	implements
		CPSystemItem,
		Cloneable,
		XMLSerializable {

	private byte bitmap;
	private byte rate;
	private byte type;
	private short channelHandle;
	private short continuityCount;
	private UL label = null;
	private byte[] creationDate = null;
	private byte[] userDate = null;

	public final static UL key =
		(UL) Forge.makeAUID(new byte[] {
				0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01,
				0x0d, 0x01, 0x03, 0x01, 0x04, 0x01, 0x01, 0x00 });

	public CPSystemItemImpl() { }

	public final static CPSystemItem make(
			ByteBuffer buffer) {

		CPSystemItem systemItem = new CPSystemItemImpl();

		systemItem.setBitmap(buffer.get());
		systemItem.setRate(buffer.get());
		systemItem.setType(buffer.get());
		systemItem.setChannelHandle(buffer.getShort());
		systemItem.setContinuityCount(buffer.getShort());

		byte[] readSomeBytes = new byte[16];
		buffer.get(readSomeBytes);
		systemItem.setLabel((UL) Forge.makeAUID(readSomeBytes));

		readSomeBytes = new byte[17];
		buffer.get(readSomeBytes);
		systemItem.setCreationDate(readSomeBytes);

		readSomeBytes = new byte[17];
		buffer.get(readSomeBytes);
		systemItem.setUserDate(readSomeBytes);

		return systemItem;
	}

	@Override
	public byte getBitmap() {
		return bitmap;
	}

	@Override
	public void setBitmap(byte bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public byte getRate() {
		return rate;
	}

	@Override
	public void setRate(byte rate) {
		this.rate = rate;
	}

	@Override
	public byte getType() {
		return type;
	}

	@Override
	public void setType(byte type) {
		this.type = type;
	}

	@Override
	public short getChannelHandle() {
		return channelHandle;
	}

	@Override
	public void setChannelHandle(short channelHandle) {
		this.channelHandle = channelHandle;
	}

	@Override
	public short getContinuityCount() {
		return continuityCount;
	}

	@Override
	public void setContinuityCount(
			short continuityCount) {
		this.continuityCount = continuityCount;
	}

	@Override
	public UL getLabel() {
		return label;
	}

	@Override
	public void setLabel(UL label) {
		this.label = label;
	}

	@Override
	public byte[] getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(byte[] creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public byte[] getUserDate() {
		return userDate;
	}

	@Override
	public void setUserDate(byte[] userDate) {
		this.userDate = userDate;
	}

	@Override
	public UnitType getUnitType() {

		return UnitType.ContentPackageEssenceElement;
	}

	public void appendXMLChildren(
			Node parent) {

		Element systemElement =
			XMLBuilder.createChild(parent, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "SystemMetadata");

		XMLBuilder.appendElement(systemElement, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "Bitmap", bitmap);
		XMLBuilder.appendElement(systemElement, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "Rate", rate);
		XMLBuilder.appendElement(systemElement, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "Type", type);
		XMLBuilder.appendElement(systemElement, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "ChannelHandle", channelHandle);
		XMLBuilder.appendElement(systemElement, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "ContinuityCount", continuityCount);
		XMLBuilder.appendElement(systemElement, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "Label", label.toString());
		XMLBuilder.appendElement(systemElement, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "CreationDate", creationDate);
		XMLBuilder.appendElement(systemElement, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "UserDate", userDate);
	}

	public String getComment() {

		return null;
	}

	public String toString() {

		return XMLBuilder.toXMLNonMetadata(this);
	}
}
