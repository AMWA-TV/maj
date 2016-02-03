package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.io.mxf.HeaderOpenCompletePartitionPack;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.UnitType;

@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0102, uuid3 = 0x0300,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01 },
		definedName = "HeaderOpenCompletePartitionPack",
		description = "A header open complete partition pack describes a the first partition within an open and compelte MXF file.",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX,
		symbol = "HeaderOpenCompletePartitionPack")
public class HeaderOpenCompletePartitionPackImpl 
	extends 
		HeaderPartitionPackImpl
	implements
		HeaderOpenCompletePartitionPack,
		Cloneable {
	
	public HeaderOpenCompletePartitionPack clone() {
		
		return (HeaderOpenCompletePartitionPack) super.clone();
	}
	
	@Override
	public UnitType getUnitType() {
		
		return UnitType.HeaderOpenCompletePartitionPack;
	}

}
