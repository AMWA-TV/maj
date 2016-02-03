package tv.amwa.maj.io.mxf;

/**
 * <p>Represents the description of {@linkplain FooterPartition footer partition} that is closed 
 * and incomplete.</p>
 * 
 * <p>A closed partition is one in which required header metadata values have been finalized 
 * and so all required metadata is present and correct. All closed partitions that contain 
 * {@linkplain HeaderMetadata header metadata} shall have identical header metadata.</p>
 * 
 * <p>An incomplete partition is one where {@linkplain HeaderMetadata header metadata} exists and 
 * some best effort metadata properties have been flagged as unknown, by setting to the appropriate 
 * distinguished value.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see HeaderClosedIncompletePartitionPack
 * @see BodyClosedIncompletePartitionPack
 * @see FooterClosedCompletePartitionPack
 */
public interface FooterClosedIncompletePartitionPack 
	extends
		FooterPartitionPack, 
		Cloneable {

	/**
	 * <p>Create a cloned copy of this footer closed incomplete partition pack.</p>
	 *
	 * @return Cloned copy of this footer closed incomplete partition pack.
	 */
	public FooterClosedIncompletePartitionPack clone();
}
