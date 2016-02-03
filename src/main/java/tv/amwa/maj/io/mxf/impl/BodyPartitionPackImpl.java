package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.io.mxf.BodyPartitionPack;
import tv.amwa.maj.io.mxf.MXFConstants;

@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0103, uuid3 = 0x0000,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01 },
		definedName = "BodyPartitionPack",
		description = "A body partition found in the central section of an MXF file.",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX,
		symbol = "BodyPartitionPack")
public abstract class BodyPartitionPackImpl 
	extends 
		PartitionPackImpl
	implements 
		BodyPartitionPack, 
		Cloneable,
		MetadataObject {

	public BodyPartitionPack clone() {
		
		return (BodyPartitionPack) super.clone();
	}

}
