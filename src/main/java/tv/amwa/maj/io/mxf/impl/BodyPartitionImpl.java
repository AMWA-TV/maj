package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.io.mxf.BodyPartition;
import tv.amwa.maj.io.mxf.BodyPartitionPack;

public class BodyPartitionImpl 
	extends 
		EssencePartitionImpl
	implements
		MetadataObject,
		BodyPartition {

	private BodyPartitionPack bodyPartitionPack;
	
	public BodyPartitionImpl() { }
	
	@Override
	public BodyPartitionPack getPartitionPack() {
		
		return bodyPartitionPack;
	}

	public void setPartitionPack(
			BodyPartitionPack bodyPartitionPack) 
		throws NullPointerException {
		
		if (bodyPartitionPack == null)
			throw new NullPointerException("Cannot set a body partition's partition pack using a null value.");
		
		this.bodyPartitionPack = bodyPartitionPack.clone();
	}
	
	public void setPartitionPackPadding(
			long paddingFillSize) 
		throws IllegalArgumentException {
		
		this.bodyPartitionPack.setPaddingFillSize(paddingFillSize);
	}
	
	public BodyPartition clone() {
		
		return (BodyPartition) super.clone();
	}
}
