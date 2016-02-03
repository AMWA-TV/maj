package tv.amwa.maj.extensions.avid;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int32;

public interface TapeDescriptor 
	extends tv.amwa.maj.model.TapeDescriptor {
	
	public @Int32 int getColorFrame()
		throws PropertyNotPresentException;
	
	public void setColorFrame(
			@Int32 Integer colorFrame);

}
