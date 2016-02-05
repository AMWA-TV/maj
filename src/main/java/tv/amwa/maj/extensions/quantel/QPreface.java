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

public interface QPreface
	extends tv.amwa.maj.model.Preface {

//    <PropertyDefinition>
//    <Identification>urn:uuid:d52527ee-ba9d-4d2b-92be-e93a2a073d66</Identification>
//    <Symbol>Archive_database_type</Symbol>
//    <Name>Archive database type</Name>
//    <Type>Int32</Type>
//    <MemberOf>Preface</MemberOf>
//    <LocalIdentification>0xffdb</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getArchiveDatabaseType()
		throws PropertyNotPresentException;

	public void setArchiveDatabaseType(
			@Int32 Integer archiveDatabaseType);
}
