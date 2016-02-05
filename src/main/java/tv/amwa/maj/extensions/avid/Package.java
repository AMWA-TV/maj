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

import java.util.List;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.model.TaggedValue;

public interface Package
	extends tv.amwa.maj.model.Package {

	public boolean getConvertFrameRate();

	public void setConvertFrameRate(
			Boolean convertFrameRate);

	public List<? extends TaggedValue> getMobAttributeList()
		throws PropertyNotPresentException;

	public void appendMobAttributeItem(
			TaggedValue mobAttributeItem)
		throws NullPointerException;

	public void prependMobAttributeItem(
			TaggedValue mobAttributeItem)
		throws NullPointerException;

	public void insertMobAttributeItem(
			int index,
			TaggedValue mobAttributeItem)
		throws NullPointerException,
			IndexOutOfBoundsException;

	public int countMobAttributeList();

	public void clearMobAttributeList();

	public TaggedValue getMobAttributeItemAt(
			int index)
		throws IndexOutOfBoundsException;

	public void removeMobAttributeItemAt(
			int index)
		throws IndexOutOfBoundsException;

	public @Int64 long getSubclipFullLength()
		throws PropertyNotPresentException;

	public void setSubclipFullLength(
			@Int64 Long subclipFullLength);

	public @Int64 long getSubclipBegin()
		throws PropertyNotPresentException;

	public void setSubclipBegin(
			@Int64 Long subclipBegin);

}
