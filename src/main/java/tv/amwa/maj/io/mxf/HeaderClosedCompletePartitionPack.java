package tv.amwa.maj.io.mxf;

import tv.amwa.maj.industry.MetadataObject;

/**
 * <p>Represents the description of {@linkplain HeaderPartition header partition} that is closed 
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
 * @see FooterClosedCompletePartitionPack
 * @see BodyClosedCompletePartitionPack
 * @see HeaderClosedIncompletePartitionPack
 * @see HeaderOpenCompletePartitionPack
 * @see HeaderClosedIncompletePartitionPack
 *
 */
public interface HeaderClosedCompletePartitionPack 
	extends 
		HeaderPartitionPack,
		MetadataObject,
		Cloneable {

	/**
	 * <p>Create a cloned copy of this header closed complete partition pack.</p>
	 *
	 * @return Cloned copy of this headerC closed complete partition pack.
	 */
	public HeaderClosedCompletePartitionPack clone();
}
