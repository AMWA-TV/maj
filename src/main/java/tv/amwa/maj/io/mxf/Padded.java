package tv.amwa.maj.io.mxf;

public interface Padded {

	/**
	 * <p>Returns the number of padding bytes following a significant entry in a {@linkplain Partition partition},
	 * such as a {@linkplain PartitionPack partition pack} or {@linkplain IndexTable index table}.</p>
	 * 
	 * @return Padding bytes following a significant entry in a partition.
	 */
	public long getPaddingFillSize();
	
	/**
	 * <p>Set the amount of padding to include after each significant entry in the partition, 
	 * allowing space for the metadata to expand over time.</p>
	 * 
	 * @param paddingFillSize Size of padding to include after the partition.
	 * 
	 * @throws IllegalArgumentException Cannot set the padding size to a negative value.
	 * 
	 * @see Partition#setPartitionPackPadding(long)
	 */
	public void setPaddingFillSize(
			long paddingFillSize)
		throws IllegalArgumentException;
}
