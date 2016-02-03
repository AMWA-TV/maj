package tv.amwa.maj.io.mxf;

import tv.amwa.maj.industry.MetadataObject;

/**
 * <p>Represents the description of {@linkplain HeaderPartition header partition} that is closed 
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
 * @see FooterClosedIncompletePartitionPack
 * @see BodyClosedIncompletePartitionPack
 * @see HeaderClosedCompletePartitionPack
 * @see HeaderOpenCompletePartitionPack
 * @see HeaderClosedIncompletePartitionPack
 */
public interface HeaderClosedIncompletePartitionPack 
	extends
		HeaderPartitionPack,
		MetadataObject,
		Cloneable {
	
	/**
	 * <p>Create a cloned copy of this header closed incomplete partition pack.</p>
	 *
	 * @return Cloned copy of this header closed incomplete partition ack.
	 */
	public HeaderClosedIncompletePartitionPack clone();

}
