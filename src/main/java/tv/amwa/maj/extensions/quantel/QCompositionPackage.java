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

import java.util.List;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.model.OperationGroup;
import tv.amwa.maj.model.Segment;

public interface QCompositionPackage
	extends tv.amwa.maj.model.CompositionPackage {

//    <PropertyDefinition>
//    <Identification>urn:uuid:a1bb5c02-0eac-436f-96df-f93b153cf045</Identification>
//    <Symbol>Clip_fps</Symbol>
//    <Name>Clip fps</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffd8</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipFPS()
		throws PropertyNotPresentException;

	public void setClipFPS(
			@Int32 Integer clipFPS);

//  <PropertyDefinition>
//    <Identification>urn:uuid:ad2eab05-ca5f-4af1-9426-f2376ed0ce3d</Identification>
//    <Symbol>Clip_type</Symbol>
//    <Name>Clip type</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffd7</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getClipType()
		throws PropertyNotPresentException;

	public void setClipType(
			String clipType);

//  <PropertyDefinition>
//    <Identification>urn:uuid:a81adc0c-2914-4715-95af-1dc10be35966</Identification>
//    <Symbol>Clip_project</Symbol>
//    <Name>Clip project</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffd6</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getClipProject()
		throws PropertyNotPresentException;

	public void setClipProject(
			String clipProject);

//  <PropertyDefinition>
//    <Identification>urn:uuid:225b4a14-5f24-48c4-bbe6-a11f00119189</Identification>
//    <Symbol>Clip_modified_minutes</Symbol>
//    <Name>Clip modified minutes</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffd5</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipModifiedMinutes()
		throws PropertyNotPresentException;

	public void setClipModifiedMinutes(
			@Int32 Integer clipModifiedMinutes);

//  <PropertyDefinition>
//    <Identification>urn:uuid:7989621f-6a2e-46a1-a365-a2ae564d0c0e</Identification>
//    <Symbol>Clip_modified_months</Symbol>
//    <Name>Clip modified months</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffd4</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipModifiedMonths()
		throws PropertyNotPresentException;

	public void setClipModifiedMonths(
			@Int32 Integer clipModifiedMonths);

//  <PropertyDefinition>
//    <Identification>urn:uuid:7e984a30-b6e2-4d36-b834-53687774a6e0</Identification>
//    <Symbol>Clip_audio_tracks</Symbol>
//    <Name>Clip audio tracks</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffd3</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipAudioTracks()
		throws PropertyNotPresentException;

	public void setClipAudioTracks(
			@Int32 Integer clipAudioTracks);

//  <PropertyDefinition>
//    <Identification>urn:uuid:5d8a9e31-d7e4-4c2d-93bd-130f820e992c</Identification>
//    <Symbol>Archive_composition_type</Symbol>
//    <Name>Archive composition type</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffd2</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getArchiveCompositionType()
		throws PropertyNotPresentException;

	public void setArchiveCompositionType(
			@Int32 Integer archiveCompositionType);

//  <PropertyDefinition>
//    <Identification>urn:uuid:a98c9c39-4207-4b0c-b580-4d4e6cdcfff5</Identification>
//    <Symbol>Clip_key_render_sequence</Symbol>
//    <Name>Clip key render sequence</Name>
//    <Type>SegmentStrongReference</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffd1</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Segment getClipKeyRenderSequence()
		throws PropertyNotPresentException;

	public void setClipKeyRenderSequence(
			Segment clipKeyRenderSequence);

//  <PropertyDefinition>
//    <Identification>urn:uuid:c33bb43d-12bf-4bc6-bced-4047f61a0e89</Identification>
//    <Symbol>Clip_effects</Symbol>
//    <Name>Clip effects</Name>
//    <Type>Operation_Group_Strong_Reference_Variable_Array</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffd0</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public List<OperationGroup> getClipEffects()
		throws PropertyNotPresentException;

	public void appendClipEffect(
			OperationGroup clipEffect)
		throws NullPointerException;

	public OperationGroup getClipEffectAt(
			int index)
		throws IndexOutOfBoundsException;

	public void clearClipEffects();

	public int countClipEffects();

	public void prependClipEffect(
			OperationGroup clipEffect);

	public void removeClipEffectAt(int index)
		throws IndexOutOfBoundsException;

//  <PropertyDefinition>
//    <Identification>urn:uuid:0c77803f-ce8d-43b1-99f7-156e910379bd</Identification>
//    <Symbol>Clip_modified_hours</Symbol>
//    <Name>Clip modified hours</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffcf</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipModifiedHours()
		throws PropertyNotPresentException;

	public void setClipModifiedHours(
			@Int32 Integer clipModifiedHours);

//  <PropertyDefinition>
//    <Identification>urn:uuid:d1970f4e-5412-4826-a89a-3503bb1a750d</Identification>
//    <Symbol>Clip_dropframe</Symbol>
//    <Name>Clip dropframe</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffce</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipDropframe()
		throws PropertyNotPresentException;

	public void setClipDropframe(
			@Int32 Integer clipDropframe);

//  <PropertyDefinition>
//    <Identification>urn:uuid:db695c54-3606-48eb-8924-4d8215e154b1</Identification>
//    <Symbol>Clip_duration</Symbol>
//    <Name>Clip duration</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffcd</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipDuration()
		throws PropertyNotPresentException;

	public void setClipDuration(
			@Int32 Integer clipDuration);

//  <PropertyDefinition>
//    <Identification>urn:uuid:8406745b-3ac0-43e2-94f3-5fbaa22d0821</Identification>
//    <Symbol>Clip_modified_years</Symbol>
//    <Name>Clip modified years</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffcc</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipModifiedYears()
		throws PropertyNotPresentException;

	public void setClipModifiedYears(
			@Int32 Integer clipModifiedYears);

//  <PropertyDefinition>
//    <Identification>urn:uuid:be312d5c-7059-48c0-8d2b-0dd05e8279c3</Identification>
//    <Symbol>Clip_modified_milliseconds</Symbol>
//    <Name>Clip modified milliseconds</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffcb</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipModifiedMilliseconds()
		throws PropertyNotPresentException;

	public void setClipModifiedMillseconds(
			@Int32 Integer clipModifiedMilliseconds);

//  <PropertyDefinition>
//    <Identification>urn:uuid:3db3ec65-3070-4466-9a7f-8e1745aa4a6a</Identification>
//    <Symbol>Clip_width</Symbol>
//    <Name>Clip width</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffca</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipWidth()
		throws PropertyNotPresentException;

	public void setClipWidth(
			@Int32 Integer clipWidth);

//  <PropertyDefinition>
//    <Identification>urn:uuid:1b749f66-5af2-46f0-82d4-07e09f384f8f</Identification>
//    <Symbol>Clip_video_tracks</Symbol>
//    <Name>Clip video tracks</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffc9</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipVideoTracks()
		throws PropertyNotPresentException;

	public void setClipVideoTracks(
			@Int32 Integer clipVideoTracks);

//  <PropertyDefinition>
//    <Identification>urn:uuid:7620ea68-eb15-405e-8ebb-e39c88e1a5e6</Identification>
//    <Symbol>Clip_blob</Symbol>
//    <Name>Clip blob</Name>
//    <Type>Stream</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffc8</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Stream getClipBlob()
		throws PropertyNotPresentException;

	public void setClipBlob(
			Stream clipBlob);

//  <PropertyDefinition>
//    <Identification>urn:uuid:61a06070-67de-468f-95bd-cbecf9b165dd</Identification>
//    <Symbol>Clip_colour_green</Symbol>
//    <Name>Clip colour green</Name>
//    <Type>Indirect</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffc7</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public PropertyValue getClipColourGreen()
		throws PropertyNotPresentException;

	public void setClipColourGreen(
			PropertyValue clipColourGreen);

//  <PropertyDefinition>
//    <Identification>urn:uuid:1b322074-bbdc-43fc-9905-ab80c6ba01ad</Identification>
//    <Symbol>Clip_archived_years</Symbol>
//    <Name>Clip archived years</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffc6</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipArchivedYears()
		throws PropertyNotPresentException;

	public void setClipArchivedYears(
			@Int32 Integer clipArchiveYears);

//  <PropertyDefinition>
//    <Identification>urn:uuid:09820679-f971-4743-b1b7-a7e6c299d156</Identification>
//    <Symbol>Clip_height</Symbol>
//    <Name>Clip height</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffc5</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipHeight()
		throws PropertyNotPresentException;

	public void setClipHeight(
			@Int32 Integer clipHeight);

//  <PropertyDefinition>
//    <Identification>urn:uuid:97770688-cfe0-47a1-afc1-7a3845465eb4</Identification>
//    <Symbol>Clip_in_point</Symbol>
//    <Name>Clip in point</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffc4</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipInPoint()
		throws PropertyNotPresentException;

	public void setClipInPoint(
			@Int32 Integer clipInPoint);

//  <PropertyDefinition>
//    <Identification>urn:uuid:4b54d899-0120-4b88-8fed-1f5c38307934</Identification>
//    <Symbol>Clip_archived_hours</Symbol>
//    <Name>Clip archived hours</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffc3</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipArchivedHours()
		throws PropertyNotPresentException;

	public void setClipArchivedHours(
			@Int32 Integer clipArchivedHours);

//  <PropertyDefinition>
//    <Identification>urn:uuid:667f0aa1-32c7-4591-b0ce-dd57f2034858</Identification>
//    <Symbol>Clip_archived_months</Symbol>
//    <Name>Clip archived months</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffc2</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipArchivedMonths()
		throws PropertyNotPresentException;

	public void setClipArchivedMonths(
			@Int32 Integer clipArchivedMonths);

//  <PropertyDefinition>
//    <Identification>urn:uuid:9c8c50a5-4e74-483a-8d13-1b742942753f</Identification>
//    <Symbol>Clip_render_sequence</Symbol>
//    <Name>Clip render sequence</Name>
//    <Type>SegmentStrongReference</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffc1</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public Segment getClipRenderSequence()
		throws PropertyNotPresentException;

	public void setClipRenderSequence(
			Segment clipRenderSequence);

//  <PropertyDefinition>
//    <Identification>urn:uuid:6ee0bbad-81db-497d-bf3b-5a1dca9c65cb</Identification>
//    <Symbol>Clip_archived_minutes</Symbol>
//    <Name>Clip archived minutes</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffc0</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipArchivedMinutes()
		throws PropertyNotPresentException;

	public void setClipArchivedMinutes(
			@Int32 Integer clipArchivedMinutes);

//  <PropertyDefinition>
//    <Identification>urn:uuid:e80a7faf-b4fe-463e-9507-06c7bbb31994</Identification>
//    <Symbol>Clip_colour_red</Symbol>
//    <Name>Clip colour red</Name>
//    <Type>Indirect</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffbf</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public PropertyValue getClipColourRed()
		throws PropertyNotPresentException;

	public void setClipColourRed(
			PropertyValue clipColourRef);

//  <PropertyDefinition>
//    <Identification>urn:uuid:903b5bbd-9f4e-4e71-9abf-249c6999d475</Identification>
//    <Symbol>Clip_modified_days</Symbol>
//    <Name>Clip modified days</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffbe</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipModifiedDays()
		throws PropertyNotPresentException;

	public void setClipModifiedDays(
			@Int32 Integer clipModifiedDays);

//  <PropertyDefinition>
//    <Identification>urn:uuid:06d1dbc9-014a-4ee2-8f20-4b3116bfab15</Identification>
//    <Symbol>Clip_modified_seconds</Symbol>
//    <Name>Clip modified seconds</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffbd</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipModifiedSeconds()
		throws PropertyNotPresentException;

	public void setClipModifiedSeconds(
			@Int32 Integer clipModifiedSeconds);

//  <PropertyDefinition>
//    <Identification>urn:uuid:be93ebc9-c74f-46b0-9ae1-5b3cbafd1967</Identification>
//    <Symbol>Clip_dest_tc</Symbol>
//    <Name>Clip dest tc</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffbc</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipDestTC()
		throws PropertyNotPresentException;

	public void setClipDestTC(
			@Int32 Integer clipDestTC);

//  <PropertyDefinition>
//    <Identification>urn:uuid:270851cc-bd72-4205-8705-d58debbd1990</Identification>
//    <Symbol>Clip_archived_days</Symbol>
//    <Name>Clip archived days</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffbb</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipArchivedDays()
		throws PropertyNotPresentException;

	public void setClipArchivedDays(
			@Int32 Integer clipArchivedDays);

//  <PropertyDefinition>
//    <Identification>urn:uuid:281b0ddd-febc-4a19-bfe6-09dc47543490</Identification>
//    <Symbol>Clip_colour_blue</Symbol>
//    <Name>Clip colour blue</Name>
//    <Type>Indirect</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffba</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public PropertyValue clipColourBlue()
		throws PropertyNotPresentException;

	public void setClipColourBlue(
			PropertyValue clipColourBlue);

//  <PropertyDefinition>
//    <Identification>urn:uuid:152f1dde-9616-4d42-b416-458eb4396613</Identification>
//    <Symbol>Archive_setting</Symbol>
//    <Name>Archive setting</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffb9</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getArchiveSetting()
		throws PropertyNotPresentException;

	public void setArchiveSetting(
			@Int32 Integer archiveSetting);

//  <PropertyDefinition>
//    <Identification>urn:uuid:a80511e1-fe68-4438-bfe4-ea2678fe323b</Identification>
//    <Symbol>Clip_archived_seconds</Symbol>
//    <Name>Clip archived seconds</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffb8</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipArchivedSeconds()
		throws PropertyNotPresentException;

	public void setClipArchivedSeconds(
			@Int32 Integer clipArchivedSeconds);

//  <PropertyDefinition>
//    <Identification>urn:uuid:e66bcfe4-b1f1-4951-8efb-807313e5febf</Identification>
//    <Symbol>Clip_out_point</Symbol>
//    <Name>Clip out point</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffb7</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipOutPoint()
		throws PropertyNotPresentException;

	public void setClipOutPoint(
			@Int32 Integer clipOutPoint);

//  <PropertyDefinition>
//    <Identification>urn:uuid:89f3b9e7-8f35-47a5-819a-d2128140c2bb</Identification>
//    <Symbol>Clip_flag1001</Symbol>
//    <Name>Clip flag1001</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffb6</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipFlag1001()
		throws PropertyNotPresentException;

	public void setClipFlag1001(
			@Int32 Integer clipFlag1001);

//  <PropertyDefinition>
//    <Identification>urn:uuid:25b65bf0-89ff-4a23-b5d8-3c82e6fcc23a</Identification>
//    <Symbol>Clip_archived_milliseconds</Symbol>
//    <Name>Clip archived milliseconds</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffb5</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getClipArchivedMilliseconds()
		throws PropertyNotPresentException;

	public void setClipArchivedMilliseconds(
			@Int32 Integer clipArchivedMilliseconds);

//  <PropertyDefinition>
//    <Identification>urn:uuid:7343dff5-97c3-46e0-9ccb-5437480aae2a</Identification>
//    <Symbol>Archive_composition</Symbol>
//    <Name>Archive composition</Name>
//    <Type>Int32</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffb4</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getArchiveComposition()
		throws PropertyNotPresentException;

	public void setArchiveComposition(
			@Int32 Integer clipArchiveComposition);

//  <PropertyDefinition>
//    <Identification>urn:uuid:2844a2f6-f65d-4e8d-b8dc-71d72d408b75</Identification>
//    <Symbol>Clip_category</Symbol>
//    <Name>Clip category</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffb3</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getClipCategory()
		throws PropertyNotPresentException;

	public void setClipCategory(
			String clipCategory);

//  <PropertyDefinition>
//    <Identification>urn:uuid:419da8fc-7b0f-4a8a-b706-cab44c68bb3e</Identification>
//    <Symbol>Clip_owner</Symbol>
//    <Name>Clip owner</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>CompositionPackage</MemberOf>
//    <LocalIdentification>0xffb2</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getClipOwner()
		throws PropertyNotPresentException;

	public void setClipOwner(
			String clipOwner);
}
