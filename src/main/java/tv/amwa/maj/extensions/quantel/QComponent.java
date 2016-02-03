package tv.amwa.maj.extensions.quantel;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int32;

public interface QComponent 
	extends tv.amwa.maj.model.Component {

//    <PropertyDefinition>
//    <Identification>urn:uuid:45e12b0b-ac1d-43ae-9db4-36f065afc3eb</Identification>
//    <Symbol>Jupiter_ID</Symbol>
//    <Name>Jupiter ID</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>Component</MemberOf>
//    <LocalIdentification>0xffff</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getJupiterID()
		throws PropertyNotPresentException;
	
	public void setJupiterID(
			String jupiterID);
	
//    <PropertyDefinition>
//    <Identification>urn:uuid:9a14eb95-b212-4d11-bc5d-b2f36b37ba2b</Identification>
//    <Symbol>Jupiter_Clip_Offset</Symbol>
//    <Name>Jupiter Clip Offset</Name>
//    <Type>Int32</Type>
//    <MemberOf>Component</MemberOf>
//    <LocalIdentification>0xfffe</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public @Int32 int getJupiterClipOffset()
		throws PropertyNotPresentException;
	
	public void setJupiterClipOffset(
			@Int32 Integer jupiterClipOffset);
	
//    <PropertyDefinition>
//    <Identification>urn:uuid:fedcba98-2267-11d3-8a4c-0050040ef7d2</Identification>
//    <Symbol>EditName</Symbol>
//    <Name>EditName</Name>
//    <Type>UTF16String</Type>
//    <MemberOf>Component</MemberOf>
//    <LocalIdentification>0xfffd</LocalIdentification>
//    <IsOptional>true</IsOptional>
//  </PropertyDefinition>

	public String getEditName()
		throws PropertyNotPresentException;
	
	public void setEditName(
			String editName);
	
	
}
