package tv.amwa.maj.extensions.avid;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt16;

public interface PictureDescriptor 
	extends tv.amwa.maj.model.PictureDescriptor {

	public @Int32 int getDataOffset()
		throws PropertyNotPresentException;
	
	public void setDataOffset(
			@Int32 Integer dataOffset);
	
	public @UInt16 short getFrameIndexByteOrder()
		throws PropertyNotPresentException;
	
	public void setFrameIndexByteOrder(
			@UInt16 Short frameIndexByteOrder)
		throws IllegalArgumentException;
	
	public @Int32 int getFrameSampleSize()
		throws PropertyNotPresentException;

	public void setFrameSampleSize(
			@Int32 Integer frameSampleSize);
	
	public @Int32 int getImageSize()
		throws PropertyNotPresentException;

	public void setImageSize(
			@Int32 Integer imageSize);

	public @Int32 int getResolutionID()
		throws PropertyNotPresentException;

	public void setResolutionID(
				@Int32 Integer resolutionID);
}
