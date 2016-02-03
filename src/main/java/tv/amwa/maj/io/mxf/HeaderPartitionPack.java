package tv.amwa.maj.io.mxf;

import tv.amwa.maj.industry.MetadataObject;

/**
 * <p>Represents the description of a {@linkplain HeaderPartition header partition}, including its
 * size and what it contains.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see BodyPartitionPack
 * @see FooterPartitionPack
 */
public interface HeaderPartitionPack 
	extends 
		PartitionPack,
		MetadataObject,
		Cloneable {
	
	/**
	 * <p>Create a cloned copy of this header partition pack.</p>
	 *
	 * @return Cloned copy of this header partition pack.
	 */
	public HeaderPartitionPack clone();

}