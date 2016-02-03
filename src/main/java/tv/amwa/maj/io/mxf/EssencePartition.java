package tv.amwa.maj.io.mxf;

import java.io.IOException;
import java.nio.ByteBuffer;

import tv.amwa.maj.exception.InsufficientSpaceException;

public interface EssencePartition 
	extends 
		Partition, 
		Cloneable {

	
	public EssenceContainer getEssenceContainer();
	
	public EssenceElement readEssenceElement();
	
	public void initializeWritingBody()
		throws NullPointerException,
			InsufficientSpaceException,
			IOException;
	
	public void writeEssenceElementHeader(
			byte itemType,
			byte elementType,
			byte elementCount,
			byte elementItem,
			long length)
		throws IOException;
	
	public void writeEssenceElementHeader(
			int trackNumber,
			long length)
		throws IOException;
	
	public void writeEssenceBlock(
			ByteBuffer essence)
		throws IOException;
	
	public long fillToEnd()
		throws IOException;
	
	public EssencePartition clone();
}
