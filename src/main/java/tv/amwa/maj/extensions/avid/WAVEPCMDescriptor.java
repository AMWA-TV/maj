package tv.amwa.maj.extensions.avid;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int32;

public interface WAVEPCMDescriptor 
	extends tv.amwa.maj.model.WAVEPCMDescriptor {

	public @Int32 int getDataOffset()
	throws PropertyNotPresentException;

	public void setDataOffset(
			@Int32 Integer dataOffset);
}
