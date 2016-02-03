package tv.amwa.maj.io.mxf;

/**
 * <p>Represents the description of {@linkplain FooterPartition footer partition} that is closed 
 * and complete.</p>
 * 
 * <p>A closed partition is one in which required header metadata values have been finalized 
 * and so all required metadata is present and correct. All closed partitions that contain 
 * {@linkplain HeaderMetadata header metadata} shall have identical header metadata.</p>
 * 
 * <p>A complete partition is one where either {@linkplain HeaderMetadata header metadata} is absent 
 * in this partition or where the header metadata exists and all best effort metadata properties have 
 * been correctly completed.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see HeaderClosedCompletePartitionPack
 * @see BodyClosedCompletePartitionPack
 * @see FooterClosedIncompletePartitionPack
 */
public interface FooterClosedCompletePartitionPack 
	extends 
		FooterPartitionPack,
		Cloneable {
	
	/**
	 * <p>Create a cloned copy of this footer closed complete partition pack.</p>
	 *
	 * @return Cloned copy of this footer closed complete partition pack.
	 */
	public FooterClosedCompletePartitionPack clone();

}
