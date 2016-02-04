package tv.amwa.maj.io.mxf;

/**
 * <p>Represents the description of a {@linkplain BodyPartition body partition}, including its
 * size and what it contains.</p>
 * 
 *
 *
 * @see HeaderPartitionPack
 * @see FooterPartitionPack
 */
public interface BodyPartitionPack 
	extends 
		PartitionPack,
		Cloneable {

	/**
	 * <p>Create a cloned copy of this body partition pack.</p>
	 *
	 * @return Cloned copy of this body partition pack.
	 */
	public BodyPartitionPack clone();
}
