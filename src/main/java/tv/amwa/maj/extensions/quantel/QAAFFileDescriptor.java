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
