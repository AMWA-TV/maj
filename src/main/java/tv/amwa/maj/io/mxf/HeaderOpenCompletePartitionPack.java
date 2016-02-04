package tv.amwa.maj.io.mxf;

import tv.amwa.maj.industry.MetadataObject;

/**
 * <p>Represents the description of {@linkplain HeaderPartition header partition} that is open 
 * and complete.</p>
 * 
 * <p>An open partition is one in which required {@linkplain HeaderMetadata header metadata} values 
 * have not been finalized and required values may be absent or incorrect. The header partition may not 
 * have the same values as the closed partition(s) and header metadata in open partitions may change 
 * in later repetitions.</p>
 * 
 * <p>A complete partition is one where either {@linkplain HeaderMetadata header metadata} is absent 
 * in this partition or where the header metadata exists and all best effort metadata properties have 
 * been correctly completed.</p>
 * 
 *
 * 
 * @see BodyOpenCompletePartitionPack
 * @see HeaderOpenIncompletePartitionPack
 * @see HeaderClosedCompletePartitionPack
 * @see HeaderClosedIncompletePartitionPack
 *
 */
public interface HeaderOpenCompletePartitionPack 
	extends 
		HeaderPartitionPack,
		MetadataObject,
		Cloneable {

	/**
	 * <p>Create a cloned copy of this header open complete partition pack.</p>
	 *
	 * @return Cloned copy of this header open complete partition pack.
	 */
	public HeaderOpenCompletePartitionPack clone();
}
