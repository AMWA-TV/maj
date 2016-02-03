package tv.amwa.maj.extensions.avid;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int64;

public interface CDCIDescriptor 
	extends tv.amwa.maj.model.CDCIDescriptor,
		PictureDescriptor {

	public @Int64 long getOffsetToFrameIndexes64()
		throws PropertyNotPresentException;
	
	public void setOffsetToFrameIndexes64(
			@Int64 Long offsetToFrameIndexes64);
}
