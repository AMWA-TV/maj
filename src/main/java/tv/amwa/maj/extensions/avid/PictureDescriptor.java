/*
 * Copyright 2016 Richard Cartwright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
