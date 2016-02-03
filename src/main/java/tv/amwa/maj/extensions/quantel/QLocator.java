package tv.amwa.maj.extensions.quantel;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int32;

public interface QLocator 
	extends tv.amwa.maj.model.Locator {

//    <PropertyDefinition>
//    <Identification>urn:uuid:ad2bc3a7-478b-4589-b392-6bb8208c4528</Identification>
//    <Symbol>Tape_offset</Symbol>
//    <Name>Tape offset</Name>
//    <Type>Int32</Type>
//    <MemberOf>Locator</MemberOf>
//    <LocalIdentification>0xffda</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getTapeOffset()
		throws PropertyNotPresentException;
	
	public void setTapeOffset(
			@Int32 Integer tapeOffset);
	
//    <PropertyDefinition>
//    <Identification>urn:uuid:1a8c2bae-d052-4c67-a334-1dfd1f36558c</Identification>
//    <Symbol>Number_of_files</Symbol>
//    <Name>Number of files</Name>
//    <Type>Int32</Type>
//    <MemberOf>Locator</MemberOf>
//    <LocalIdentification>0xffd9</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>
	
	public @Int32 int getNumberOfFiles()
		throws PropertyNotPresentException;
	
	public void setNumberOfFiles(
			@Int32 Integer numberOfFiles);

}
