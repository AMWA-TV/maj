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
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.model.Segment;

public interface QSegment
	extends tv.amwa.maj.model.Segment {

//    <PropertyDefinition>
//    <Identification>urn:uuid:10068b11-70a2-4d54-afd1-409cb3919ae6</Identification>
//    <Symbol>Segment_blob</Symbol>
//    <Name>Segment blob</Name>
//    <Type>Stream</Type>
//    <MemberOf>Segment</MemberOf>
//    <LocalIdentification>0xfffc</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Stream getSegmentBlob()
		throws PropertyNotPresentException;

	public void setSegmentBlob(
			Stream segmentBlob);

//  <PropertyDefinition>
//    <Identification>urn:uuid:26fbc046-4294-49a0-91bf-31f02de56335</Identification>
//    <Symbol>CutPoint</Symbol>
//    <Name>CutPoint</Name>
//    <Type>Int32</Type>
//    <MemberOf>Segment</MemberOf>
//    <LocalIdentification>0xfffb</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getCutPoint()
		throws PropertyNotPresentException;

	public void setCutPoint(
			@Int32 Integer cutPoint);

//  <PropertyDefinition>
//    <Identification>urn:uuid:dea04170-a146-4a34-8081-74e9b62b953c</Identification>
//    <Symbol>Track_render_sequence</Symbol>
//    <Name>Track render sequence</Name>
//    <Type>SegmentStrongReference</Type>
//    <MemberOf>Segment</MemberOf>
//    <LocalIdentification>0xfffa</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Segment getTrackRenderSequence()
		throws PropertyNotPresentException;

	public void setTrackRenderSequence(
			Segment trackRenderSequence);

//  <PropertyDefinition>
//    <Identification>urn:uuid:7df1077a-4f85-4cbc-988e-9bbcc10e146e</Identification>
//    <Symbol>Pack_layer_key_indicator</Symbol>
//    <Name>Pack layer key indicator</Name>
//    <Type>Int32</Type>
//    <MemberOf>Segment</MemberOf>
//    <LocalIdentification>0xfff9</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getPackLayerKeyIndicator()
		throws PropertyNotPresentException;

	public void setPackLayerKeyIndicator(
			@Int32 Integer packLayerKeyIndicator);

//  <PropertyDefinition>
//    <Identification>urn:uuid:eee91a9d-14ae-4400-9e1f-4885196551d8</Identification>
//    <Symbol>Track_key_render_sequence</Symbol>
//    <Name>Track key render sequence</Name>
//    <Type>SegmentStrongReference</Type>
//    <MemberOf>Segment</MemberOf>
//    <LocalIdentification>0xfff8</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Segment getTrackKeyRenderSequence()
		throws PropertyNotPresentException;

	public void setTrackKeyRenderSequence(
			Segment trackKeyRenderSequence);

//  <PropertyDefinition>
//    <Identification>urn:uuid:3f40f6b3-3d36-49ce-a78b-a19a88f04dcb</Identification>
//    <Symbol>Track_blob</Symbol>
//    <Name>Track blob</Name>
//    <Type>Stream</Type>
//    <MemberOf>Segment</MemberOf>
//    <LocalIdentification>0xfff7</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Stream getTrackBlob()
		throws PropertyNotPresentException;

	public void setTrackBlob(
			Stream trackBlob);

//  <PropertyDefinition>
//    <Identification>urn:uuid:4ca1a9dc-9d3e-48ea-a539-6dcdfb64f0ed</Identification>
//    <Symbol>Key_offset</Symbol>
//    <Name>Key offset</Name>
//    <Type>Int64</Type>
//    <MemberOf>Segment</MemberOf>
//    <LocalIdentification>0xfff6</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int64 long getKeyOffset()
		throws PropertyNotPresentException;

	public void setKeyOffset(
			@Int64 Long keyOffset);

//  <PropertyDefinition>
//    <Identification>urn:uuid:d1e9aaec-49d1-452f-85e9-5983b1bb472d</Identification>
//    <Symbol>TrackName</Symbol>
//    <Name>TrackName</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>Segment</MemberOf>
//    <LocalIdentification>0xfff5</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getTrackName()
		throws PropertyNotPresentException;

	public void setTrackName(
			String trackName);
}
