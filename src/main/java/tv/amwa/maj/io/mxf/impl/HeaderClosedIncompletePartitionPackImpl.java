package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.io.mxf.HeaderClosedIncompletePartitionPack;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.UnitType;

@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0102, uuid3 = 0x0200,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01 },
		definedName = "HeaderClosedIncompletePartitionPack",
		description = "A header closed incomplete partition pack describes a the first partition within a closed and incompelte MXF file.",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX,
		symbol = "HeaderClosedIncompletePartitionPack")
public class HeaderClosedIncompletePartitionPackImpl 
	extends 
		HeaderPartitionPackImpl
	implements
		HeaderClosedIncompletePartitionPack,
		Cloneable {
	
	public HeaderClosedIncompletePartitionPack clone() {
		
		return (HeaderClosedIncompletePartitionPack) super.clone();
	}
	
	@Override
	public UnitType getUnitType() {
		
		return UnitType.HeaderClosedIncompletePartitionPack;
	}
}
