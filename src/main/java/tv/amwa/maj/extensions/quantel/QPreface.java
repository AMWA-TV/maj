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
