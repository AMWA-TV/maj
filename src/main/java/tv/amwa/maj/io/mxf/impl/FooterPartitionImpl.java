package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.io.mxf.FooterPartition;
import tv.amwa.maj.io.mxf.FooterPartitionPack;

public class FooterPartitionImpl 
	extends 
		PartitionImpl 
	implements
		FooterPartition,
		MetadataObject {

	private FooterPartitionPack footerPartitionPack;
	
	public FooterPartitionImpl() { }
	
	@Override
	public FooterPartitionPack getPartitionPack() {

		return footerPartitionPack;
	}
	
	public void setPartitionPack(
			FooterPartitionPack footerPartitionPack) 
		throws NullPointerException {
		
		if (footerPartitionPack == null)
			throw new NullPointerException("Cannot set the footer partition's partition pack using a null value.");
		
		this.footerPartitionPack = footerPartitionPack.clone();
	}
	
	public void setPartitionPackPadding(
			long paddingFillSize) 
		throws IllegalArgumentException {
		
		this.footerPartitionPack.setPaddingFillSize(paddingFillSize);
	}
	
	public FooterPartition clone() {
		
		return (FooterPartition) super.clone();
	}

}
