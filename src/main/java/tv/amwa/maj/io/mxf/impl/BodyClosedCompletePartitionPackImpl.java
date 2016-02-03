package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.io.mxf.BodyClosedCompletePartitionPack;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.UnitType;

@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0103, uuid3 = 0x0400,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01 },
		definedName = "BodyClosedCompletePartitionPack",
		description = "A body partition found in the central section of an MXF file that is closed and complete.",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX,
		symbol = "BodyClosedCompletePartitionPack")
public class BodyClosedCompletePartitionPackImpl 
	extends 
		BodyPartitionPackImpl
	implements 
		BodyClosedCompletePartitionPack, 
		Cloneable, 
		MetadataObject {
	
	public BodyClosedCompletePartitionPack clone() {
		
		return (BodyClosedCompletePartitionPack) super.clone();
	}

	@Override
	public UnitType getUnitType() {
		
		return UnitType.BodyClosedCompletePartitionPack;
	}
}
