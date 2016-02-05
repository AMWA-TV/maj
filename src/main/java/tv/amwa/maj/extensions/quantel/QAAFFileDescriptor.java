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

public interface QAAFFileDescriptor
	extends tv.amwa.maj.model.AAFFileDescriptor {

//    <PropertyDefinition>
//    <Identification>urn:uuid:7bea1710-da09-49e3-9e78-fd2dab166ea2</Identification>
//    <Symbol>_4_bit_audio</Symbol>
//    <Name>24 bit audio</Name>
//    <Type>Int32</Type>
//    <MemberOf>FileDescriptor</MemberOf>
//    <LocalIdentification>0xffdc</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getTwentyFourBitAudio()
		throws PropertyNotPresentException;

	public void setTwentyFourBitAudio(
			@Int32 Integer twentyFourBitAudio);

}
