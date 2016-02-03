package tv.amwa.maj.io.mxf;

import tv.amwa.maj.industry.MetadataObject;

/**
 * <p>Represents the description of {@linkplain HeaderPartition header partition} that is open 
 * and incomplete.</p>
 * 
 * <p>An open partition is one in which required {@linkplain HeaderMetadata header metadata} values 
 * have not been finalized and required values may be absent or incorrect. The header partition may not 
 * have the same values as the closed partition(s) and header metadata in open partitions may change 
 * in later repetitions.</p>
 * 
 * <p>An incomplete partition is one where {@linkplain HeaderMetadata header metadata} exists and 
 * some best effort metadata properties have been flagged as unknown, by setting to the appropriate 
 * distinguished value.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see BodyOpenIncompletePartitionPack
 * @see HeaderOpenCompletePartitionPack
 * @see HeaderClosedCompletePartitionPack
 * @see HeaderClosedIncompletePartitionPack
 */
public interface HeaderOpenIncompletePartitionPack 
	extends 
		HeaderPartitionPack,
		MetadataObject,
		Cloneable {
	
	/**
	 * <p>Create a cloned copy of this header open incomplete partition pack.</p>
	 *
	 * @return Cloned copy of this header open incomplete partition pack.
	 */
	public HeaderOpenIncompletePartitionPack clone();
}
