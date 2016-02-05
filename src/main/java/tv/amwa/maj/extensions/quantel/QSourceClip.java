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

package tv.amwa.maj.extensions.quantel;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.UInt32;

public interface QSourceClip
	extends tv.amwa.maj.model.SourceClip {

//    <PropertyDefinition>
//    <Identification>urn:uuid:11b2c317-8928-49a2-af65-666150d442a3</Identification>
//    <Symbol>Rush_channel_mask</Symbol>
//    <Name>Rush channel mask</Name>
//    <Type>UInt32</Type>
//    <MemberOf>SourceClip</MemberOf>
//    <LocalIdentification>0xffe0</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @UInt32 int getRushChannelMask()
		throws PropertyNotPresentException;

	public void setRushChannelMask(
			@UInt32 Integer rushChannelMask)
		throws IllegalArgumentException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:072ceb98-1a33-40ad-9ee7-1f80a47c3867</Identification>
//    <Symbol>Rush_blob</Symbol>
//    <Name>Rush blob</Name>
//    <Type>Stream</Type>
//    <MemberOf>SourceClip</MemberOf>
//    <LocalIdentification>0xffdf</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Stream getRushBlob()
		throws PropertyNotPresentException;

	public void setRushBlob(
			Stream rushBlob);

}
