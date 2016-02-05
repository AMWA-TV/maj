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

package tv.amwa.maj.io.mxf;

public interface CPSystemItem
	extends EssenceElement {

	public abstract UnitType getUnitType();

	public abstract void setUserDate(byte[] userDate);

	public abstract byte[] getUserDate();

	public abstract void setCreationDate(byte[] creationDate);

	public abstract byte[] getCreationDate();

	public abstract void setLabel(UL label);

	public abstract UL getLabel();

	public abstract void setContinuityCount(short continuityCount);

	public abstract short getContinuityCount();

	public abstract void setChannelHandle(short channelHandle);

	public abstract short getChannelHandle();

	public abstract void setType(byte type);

	public abstract byte getType();

	public abstract void setRate(byte rate);

	public abstract byte getRate();

	public abstract void setBitmap(byte bitmap);

	public abstract byte getBitmap();

}
