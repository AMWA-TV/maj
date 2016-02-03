package tv.amwa.maj.io.mxf;

/**
 * <p>Represents the description of {@linkplain BodyPartition body partition} that is open 
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
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see HeaderOpenCompletePartitionPack
 * @see BodyClosedCompletePartitionPack
 * @see BodyOpenIncompletePartitionPack
 * @see BodyClosedIncompletePartitionPack
 */
public interface BodyOpenCompletePartitionPack 
	extends 
		BodyPartitionPack,
		Cloneable {

	/**
	 * <p>Create a cloned copy of this body open complete partition pack.</p>
	 *
	 * @return Cloned copy of this body open complete partition pack.
	 */
	public BodyOpenCompletePartitionPack clone();
}
