package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.io.mxf.HeaderOpenIncompletePartitionPack;
import tv.amwa.maj.io.mxf.HeaderPartitionPack;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.UnitType;

@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0102, uuid3 = 0x0100,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01 },
		definedName = "HeaderOpenIncompletePartitionPack",
		description = "A header open incomplete partition pack describes a the first partition within an open and incompelte MXF file.",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX,
		symbol = "HeaderOpenIncompletePartitionPack")
public class HeaderOpenIncompletePartitionPackImpl 
	extends 
		HeaderPartitionPackImpl
	implements
		HeaderOpenIncompletePartitionPack,
		Cloneable {

	public HeaderOpenIncompletePartitionPack clone() {
		
		return (HeaderOpenIncompletePartitionPack) super.clone();
	}
	
	@Override
	public UnitType getUnitType() {
		
		return UnitType.HeaderOpenIncompletePartitionPack;
	}


}
