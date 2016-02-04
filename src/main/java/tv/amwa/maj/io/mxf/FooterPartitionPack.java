package tv.amwa.maj.io.mxf;

/**
 * <p>Represents the description of a {@linkplain FooterPartition footer partition}, including its
 * size and what it contains.</p>
 * 
 *
 * 
 * @see BodyPartitionPack
 * @see HeaderPartitionPack
 *
 */
public interface FooterPartitionPack 
	extends 
		PartitionPack,
		Cloneable {
	
	/**
	 * <p>Create a cloned copy of this footer partition pack.</p>
	 *
	 * @return Cloned copy of this footer partition pack.
	 */
	public FooterPartitionPack clone();

}
