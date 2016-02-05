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
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int64;

public interface QSequence
	extends tv.amwa.maj.model.Sequence {

//    <PropertyDefinition>
//    <Identification>urn:uuid:b18a3d40-2ff0-4159-968b-3aba4765899f</Identification>
//    <Symbol>Composite_rush_indicator</Symbol>
//    <Name>Composite rush indicator</Name>
//    <Type>Int32</Type>
//    <MemberOf>Sequence</MemberOf>
//    <LocalIdentification>0xffe3</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getCompositeRushIndicator()
		throws PropertyNotPresentException;

	public void setCompositeRushIndicator(
			@Int32 Integer compositeRushIndicator);

//  <PropertyDefinition>
//    <Identification>urn:uuid:eb84a895-73a3-4cdc-929a-99ebe0921b43</Identification>
//    <Symbol>Composite_rush_id</Symbol>
//    <Name>Composite rush id</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>Sequence</MemberOf>
//    <LocalIdentification>0xffe2</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getCompositeRushID()
		throws PropertyNotPresentException;

	public void setCompositeRushID(
			String compositeRushID);

//  <PropertyDefinition>
//    <Identification>urn:uuid:7eeee9d5-72ba-49b9-9ebc-e397932f89c1</Identification>
//    <Symbol>Composite_rush_offset</Symbol>
//    <Name>Composite rush offset</Name>
//    <Type>Int64</Type>
//    <MemberOf>Sequence</MemberOf>
//    <LocalIdentification>0xffe1</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int64 long getCompositeRushOffset()
		throws PropertyNotPresentException;

	public void setCompositeRushOffset(
			@Int64 Long compositeRushOffset);
}
