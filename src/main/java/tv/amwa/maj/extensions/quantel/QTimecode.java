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

public interface QTimecode
	extends tv.amwa.maj.model.TimecodeSegment {

//    <PropertyDefinition>
//    <Identification>urn:uuid:6d158b73-0d46-4569-9d58-085f45f83275</Identification>
//    <Symbol>Timecode_modified_flag</Symbol>
//    <Name>Timecode modified flag</Name>
//    <Type>Int32</Type>
//    <MemberOf>Timecode</MemberOf>
//    <LocalIdentification>0xffde</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getTimecodeModifiedFlag()
		throws PropertyNotPresentException;

	public void setTimecodeModifiedFlag(
			@Int32 Integer timecodeModifiedFlag);
}
