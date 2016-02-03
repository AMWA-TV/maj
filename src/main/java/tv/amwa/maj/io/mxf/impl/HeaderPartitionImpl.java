package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.io.mxf.HeaderPartition;
import tv.amwa.maj.io.mxf.HeaderPartitionPack;

public class HeaderPartitionImpl 
	extends 
		EssencePartitionImpl 
	implements
		HeaderPartition,
		MetadataObject {

	private HeaderPartitionPack headerPartitionPack;
	
	public HeaderPartitionImpl() { }
	
	@Override
	public HeaderPartitionPack getPartitionPack() {
		
		return headerPartitionPack;
	}

	public void setPartitionPack(
			HeaderPartitionPack headerPartitionPack) 
		throws NullPointerException {
		
		if (headerPartitionPack == null)
			throw new NullPointerException("Cannot set a header partition's partition pack using a null value.");
		
		this.headerPartitionPack = headerPartitionPack.clone();
	}
	
	public void setPartitionPackPadding(
			long paddingFillSize) 
		throws IllegalArgumentException {
		
		this.headerPartitionPack.setPaddingFillSize(paddingFillSize);
	}
	
	public HeaderPartition clone() {
		
		return (HeaderPartition) super.clone();
	}
}
