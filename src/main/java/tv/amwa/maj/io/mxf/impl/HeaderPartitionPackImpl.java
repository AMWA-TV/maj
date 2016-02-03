package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.io.mxf.HeaderPartitionPack;

@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0102, uuid3 = 0x0000,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01 },
		definedName = "HeaderPartitionPack",
		description = "A header partition pack describes the first partition within an MXF file.",
		namespace = "http://www.smpte-ra.org/rp210",
		prefix = "mxf",
		symbol = "HeaderPartitionPack")
public abstract class HeaderPartitionPackImpl 
	extends 
		PartitionPackImpl 
	implements 
		HeaderPartitionPack,
		Cloneable {
	
	public HeaderPartitionPack clone() {
		
		return (HeaderPartitionPack) super.clone();
	}

}
