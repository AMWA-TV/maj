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
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.model.Segment;

public interface QOperationGroup
	extends tv.amwa.maj.model.OperationGroup {

//    <PropertyDefinition>
//    <Identification>urn:uuid:6db9db08-3198-4140-9302-0ab3b01517bf</Identification>
//    <Symbol>Effect_offset</Symbol>
//    <Name>Effect offset</Name>
//    <Type>Int64</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xfff4</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int64 long getEffectOffset()
		throws PropertyNotPresentException;

	public void setEffectOffset(
			@Int64 Long effectOffset);

//  <PropertyDefinition>
//    <Identification>urn:uuid:2d13a614-f8c9-4810-934d-4d9e20006860</Identification>
//    <Symbol>Transition_blob</Symbol>
//    <Name>Transition blob</Name>
//    <Type>Stream</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xfff3</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Stream getTransitionBlob()
		throws PropertyNotPresentException;

	public void setTransitionBlob(
			Stream transitionBlob);

//  <PropertyDefinition>
//    <Identification>urn:uuid:e8388115-b50e-4199-8719-dbac2f3578bf</Identification>
//    <Symbol>Effect_length</Symbol>
//    <Name>Effect length</Name>
//    <Type>Int64</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xfff2</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int64 long getEffectLength()
		throws PropertyNotPresentException;

	public void setEffectLength(
			@Int64 Long effectLength);

//  <PropertyDefinition>
//    <Identification>urn:uuid:9a654522-1455-4026-8fd1-5526ad62df5a</Identification>
//    <Symbol>Effect_name</Symbol>
//    <Name>Effect name</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xfff1</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getEffectName()
		throws PropertyNotPresentException;

	public void setEffectName(
			String effectName);

//  <PropertyDefinition>
//    <Identification>urn:uuid:b5be0e26-3c0c-4f29-a32c-57614c3e6d15</Identification>
//    <Symbol>Effect_render_key</Symbol>
//    <Name>Effect render key</Name>
//    <Type>Int32</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xfff0</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getEffectRenderKey()
		throws PropertyNotPresentException;

	public void setEffectRenderKey(
			@Int32 Integer effectRenderKey);

//  <PropertyDefinition>
//    <Identification>urn:uuid:82f74f3a-2bd0-4636-bef5-ac31736b1f11</Identification>
//    <Symbol>Repeat_finite_tails</Symbol>
//    <Name>Repeat finite tails</Name>
//    <Type>Int32</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffef</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getRepeatFiniteTails()
		throws PropertyNotPresentException;

	public void setRepeatFiniteTails(
			@Int32 Integer repeatFiniteTails);

//  <PropertyDefinition>
//    <Identification>urn:uuid:3d53b350-6591-4cd1-90de-f2d175d2e0b8</Identification>
//    <Symbol>Effect_enabled</Symbol>
//    <Name>Effect enabled</Name>
//    <Type>Int32</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffee</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getEffectEnabled()
		throws PropertyNotPresentException;

	public void setEffectEnabled(
			@Int32 Integer effectEnabled);

//  <PropertyDefinition>
//    <Identification>urn:uuid:64979555-3210-4839-bccf-5347c2ceb4f5</Identification>
//    <Symbol>Repeat_rush_attributes</Symbol>
//    <Name>Repeat rush attributes</Name>
//    <Type>Int32</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffed</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getRepeatRushAttributes()
		throws PropertyNotPresentException;

	public void setRepeatRushAttributes(
			@Int32 Integer repeatRushAttributes);

//  <PropertyDefinition>
//    <Identification>urn:uuid:5b158c66-c1e4-4d09-aded-dd0506012918</Identification>
//    <Symbol>Effect_source_offset</Symbol>
//    <Name>Effect source offset</Name>
//    <Type>Indirect</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffec</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public PropertyValue getEffectSourceOffset()
		throws PropertyNotPresentException;

	public void setEffectSourceOffset(
			PropertyValue effectSourceOffset);

//  <PropertyDefinition>
//    <Identification>urn:uuid:4963c16a-ec04-4275-b0c0-9813b0f8bb74</Identification>
//    <Symbol>Pack_blob</Symbol>
//    <Name>Pack blob</Name>
//    <Type>Stream</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffeb</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Stream getPackBlob()
		throws PropertyNotPresentException;

	public void setPackBlob(
			Stream packBlob);

//  <PropertyDefinition>
//    <Identification>urn:uuid:900a60a0-44d8-45a5-8933-8cfe58108420</Identification>
//    <Symbol>Repeat_rush_id</Symbol>
//    <Name>Repeat rush id</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffea</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getRepeatRushID()
		throws PropertyNotPresentException;

	public void setRepeatRushID(
			String repeatRushID);

//  <PropertyDefinition>
//    <Identification>urn:uuid:53f6cea7-a563-448c-8722-1e8ac1efc5ac</Identification>
//    <Symbol>Render_key</Symbol>
//    <Name>Render key</Name>
//    <Type>SegmentStrongReference</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffe9</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Segment getRenderKey()
		throws PropertyNotPresentException;

	public void setRenderKey(
			Segment renderKey);

//  <PropertyDefinition>
//    <Identification>urn:uuid:5f9074bd-0ddf-45c3-bc0d-5f60afd615b3</Identification>
//    <Symbol>Pack_render_sequence</Symbol>
//    <Name>Pack render sequence</Name>
//    <Type>SegmentStrongReference</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffe8</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Segment getPackRenderSequence()
		throws PropertyNotPresentException;

	public void setPackRenderSequence(
			Segment packRenderSequence);

//  <PropertyDefinition>
//    <Identification>urn:uuid:ab6091cc-c6be-448a-bd69-6e893755367a</Identification>
//    <Symbol>Repeat_rush_offset</Symbol>
//    <Name>Repeat rush offset</Name>
//    <Type>Int64</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffe7</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int64 long getRepeatRushOffset()
		throws PropertyNotPresentException;

	public void setRepeatRushOffset(
			@Int64 Long repeatRushOffset);

//  <PropertyDefinition>
//    <Identification>urn:uuid:ef2cbed2-52d4-4ad8-9816-8fe7e9514768</Identification>
//    <Symbol>Effect_source_attached</Symbol>
//    <Name>Effect source attached</Name>
//    <Type>Int32</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffe6</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getEffectSourceAttached()
		throws PropertyNotPresentException;

	public void setEffectSourceAttached(
			@Int32 Integer effectSourceAttached);

//  <PropertyDefinition>
//    <Identification>urn:uuid:ca976edb-9db0-4e46-a4d2-2d4e4503f020</Identification>
//    <Symbol>Pack_offset</Symbol>
//    <Name>Pack offset</Name>
//    <Type>Int64</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffe5</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int64 long getPackOffset()
		throws PropertyNotPresentException;

	public void setPackOffset(
			@Int64 Long packOffset);

//  <PropertyDefinition>
//    <Identification>urn:uuid:4d6829fe-3339-4070-a17b-83185f0c3d8a</Identification>
//    <Symbol>Effect_source_length</Symbol>
//    <Name>Effect source length</Name>
//    <Type>Indirect</Type>
//    <MemberOf>OperationGroup</MemberOf>
//    <LocalIdentification>0xffe4</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public PropertyValue getEffectSourceLength()
		throws PropertyNotPresentException;

	public void setEffectSourceLength(
			PropertyValue effectSourceLength);
}
