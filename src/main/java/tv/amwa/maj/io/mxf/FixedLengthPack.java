package tv.amwa.maj.io.mxf;

import tv.amwa.maj.industry.MetadataObject;

/**
 * <p>Labels a {@linkplain MetadataObject metadata object} that can be serialized as a 
 * fixed length pack for which the order of the values in the buffer is important. All
 * properties of a fixed length pack must have a fixed length, with the exception of the
 * last property that is either of fixed size or is and array with length encoded as part 
 * of the property.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public interface FixedLengthPack 
	extends MetadataObject {

	/**
	 * <p>Returns a list of property names in the order they need to be serialized
	 * to a stream.</p>
	 * 
	 * @return List of property names in the order they need to be serialized.
	 * 
	 * @see MXFBuilder#readFixedLengthPack(tv.amwa.maj.record.AUID, java.nio.ByteBuffer)
	 * @see MXFBuilder#writeFixedLengthPack(FixedLengthPack, java.nio.ByteBuffer)
	 */
	public String[] getPackOrder();
}
