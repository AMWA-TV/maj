package tv.amwa.maj.io.mxf;

/**
 * <p>Represents the footer partition of an MXF file, providing access to its constituent parts. A footer partition may contain 
 * {@linkplain HeaderMetadata header metadata} and {@linkplain IndexTableSegment index table segments}. It
 * should not contain any parts of a {@linkplain BodyStream body stream}. If present, the optional footer partition
 * is the last partition in an MXF file.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 * @see BodyPartition
 * @see HeaderPartition
 * @see MXFFile#getFooterPartition()
 */
public interface FooterPartition
	extends 
		Partition, 
		Cloneable {
	
	/**
	 * <p>Returns the footer partition pack that describes the details of this footer partition, including
	 * what it contains and its size.</p>
	 * 
	 * @return Footer partition pack describing this footer partition.
	 */
	public FooterPartitionPack getPartitionPack();
	
	/**
	 * <p>Set the footer partition pack that describes this footer partition, including
	 * what it contains and its size.</p>
	 * 
	 * @param partitionPack Footer partition pack describing this footer partition.
	 * 
	 * @throws NullPointerException Cannot set the footer partition pack with a <code>null</code>
	 * value.
	 */
	public void setPartitionPack(
			FooterPartitionPack partitionPack)
		throws NullPointerException;

	/**
	 * <p>Create a cloned copy of this footer partition.</p>
	 *
	 * @return Cloned copy of this footer partition.
	 */
	public FooterPartition clone();
}
