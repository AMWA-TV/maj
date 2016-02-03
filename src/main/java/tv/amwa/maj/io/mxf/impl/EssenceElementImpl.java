package tv.amwa.maj.io.mxf.impl;

import java.nio.ByteBuffer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.io.mxf.EssenceElement;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.io.mxf.UnitType;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;

public class EssenceElementImpl 
	implements 
		EssenceElement,
		XMLSerializable {

	private byte itemType;
	private byte elementCount;
	private byte elementType;
	private byte elementNumber;
	private ByteBuffer data;
	
	public ByteBuffer getData() {
		return data;
	}

	public void setData(ByteBuffer data) {
		this.data = data;
	}

	public EssenceElementImpl() { }
	
	public final static boolean isEssenceElement(
			UL key) {
		
		if (key == null) return false;
		
		byte[] keyBytes = key.getUniversalLabel();
		if (keyBytes[0] != 0x06) return false;
		if (keyBytes[1] != 0x0e) return false;
		if (keyBytes[2] != 0x2b) return false;
		if (keyBytes[3] != 0x34) return false;
		if (keyBytes[4] != 0x01) return false;
		if (keyBytes[5] != 0x02) return false;
		if (keyBytes[6] != 0x01) return false;
		
		if (keyBytes[8] != 0x0d) return false;
		if (keyBytes[9] != 0x01) return false;
		if (keyBytes[10] != 0x03) return false;
		if (keyBytes[11] != 0x01) return false;
		
		if ((keyBytes[12] < 0x05) || (keyBytes[12] > 0x18)) return false;
		if ((keyBytes[12] > 0x07) && (keyBytes[12] < 0x15)) return false;
		
		return true;
	}

	public final static EssenceElement make(
			UL elementKey,
			ByteBuffer data) {
		
		EssenceElementImpl element = new EssenceElementImpl();
		byte[] keyBytes = elementKey.getUniversalLabel();
		
		element.setItemType(keyBytes[ITEM_TYPE_INDEX]);
		element.setElementCount(keyBytes[ELEMENT_COUNT_INDEX]);
		element.setElementType(keyBytes[ELEMENT_TYPE_INDEX]);
		element.setElementNumber(keyBytes[ELEMENT_NUMBER_INDEX]);
		
		element.setData(data);
		
		return element;
	}

	public byte getItemType() {
		return itemType;
	}


	public void setItemType(byte itemType) {
		this.itemType = itemType;
	}


	public byte getElementCount() {
		return elementCount;
	}


	public void setElementCount(byte elementCount) {
		this.elementCount = elementCount;
	}


	public byte getElementType() {
		return elementType;
	}


	public void setElementType(byte elementType) {
		this.elementType = elementType;
	}


	public byte getElementNumber() {
		return elementNumber;
	}


	public void setElementNumber(byte elementNumber) {
		this.elementNumber = elementNumber;
	}

	public int getEssenceTrackIdentifier() {
		
		return byteToInt(itemType) << 24 |
			byteToInt(elementCount) << 16 |
			byteToInt(elementType) << 8 |
			byteToInt(elementNumber);
	}
	
	int byteToInt(
			byte inByte) {
		
		return (inByte >= 0) ? ((int) inByte) : (256 + inByte);
	}
	
	public void appendXMLChildren(
			Node parent) {

		String elementName = null;
		switch (itemType) {
		
		case 0x05: elementName = "CPPicture"; break;
		case 0x06: elementName = "CPSound"; break;
		case 0x07: elementName = "CPData"; break;
		case 0x15: elementName = "GCPicture"; break;
		case 0x16: elementName = "GCSound"; break;
		case 0x17: elementName = "GCData"; break;
		case 0x18: elementName = "GCCompound"; break;
		default: elementName = "Unknown"; break;
		}
		
		Element element = XMLBuilder.createChild(parent, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, elementName);
		XMLBuilder.setAttribute(element, "", "", "essenceTrackIdentifier", Integer.toString(getEssenceTrackIdentifier()));
		
		XMLBuilder.appendElement(element, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "ElementCount", elementCount);
		XMLBuilder.appendElement(element, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "ElementType", elementType);
		XMLBuilder.appendElement(element, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "ElementNumber", elementNumber);

		data.rewind();
		byte[] firstBytes = new byte[32];
		data.get(firstBytes);
		data.rewind();
		
		XMLBuilder.appendElement(element, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "InitialData", firstBytes);
		XMLBuilder.appendElement(element, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX, "DataLength", data.limit());
	}

	public String getComment() {

		return null;
	}

	public String toString() {
		
		return XMLBuilder.toXMLNonMetadata(this);
	}

	@Override
	public UnitType getUnitType() {
		
		switch (itemType) {
		
		case 0x05: return UnitType.ContentPackagePicture;
		case 0x06: return UnitType.ContentPackageSound;
		case 0x07: return UnitType.ContentPackageData;
		case 0x15: return UnitType.GenericContainerPicture;
		case 0x16: return UnitType.GenericContainerSound;
		case 0x17: return UnitType.GenericContainerData;
		case 0x18: return UnitType.GenericContainerCompound;
		default: return UnitType.Unknown;
		}
	}
}
