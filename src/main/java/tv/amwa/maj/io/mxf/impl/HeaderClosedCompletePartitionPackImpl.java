package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.io.mxf.HeaderClosedCompletePartitionPack;
import tv.amwa.maj.io.mxf.UnitType;

@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0102, uuid3 = 0x0400,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01 },
		definedName = "HeaderClosedCompletePartitionPack",
		description = "A header closed complete partition pack describes a the first partition within a closed and compelte MXF file.",
		namespace = "http://www.smpte-ra.org/rp210",
		prefix = "mxf",
		symbol = "HeaderClosedCompletePartitionPack")
public class HeaderClosedCompletePartitionPackImpl 
	extends 
		HeaderPartitionPackImpl
	implements
		HeaderClosedCompletePartitionPack,
		Cloneable {
	
	public HeaderClosedCompletePartitionPack clone() {
		
		return (HeaderClosedCompletePartitionPack) super.clone();
	}

	@Override
	public UnitType getUnitType() {
		
		return UnitType.HeaderClosedCompletePartitionPack;
	}

}
