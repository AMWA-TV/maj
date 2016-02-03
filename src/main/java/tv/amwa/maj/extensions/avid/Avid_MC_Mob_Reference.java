package tv.amwa.maj.extensions.avid;

import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.record.PackageID;

public interface Avid_MC_Mob_Reference 
	extends InterchangeObject {
	
	public PackageID getMobReferenceMobID();
	
	public void setMobReferenceMobID(
			PackageID mobReferenceMobID) 
		throws NullPointerException;
	
	public @Int64 long getMobReferencePosition();
	
	public void setMobReferencePosition(
			@Int64 long mobReferencePosition);

	public Avid_MC_Mob_Reference clone();
	
}
