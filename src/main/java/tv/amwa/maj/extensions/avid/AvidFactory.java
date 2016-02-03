package tv.amwa.maj.extensions.avid;

import tv.amwa.maj.extensions.avid.impl.Avid_MC_Mob_ReferenceImpl;
import tv.amwa.maj.industry.Warehouse;

public class AvidFactory {

	public final static void registerAvidExtensions() {
		
		Warehouse.registerTypes(TypeDefinitions.class, AvidConstants.AVID_NAMESPACE, AvidConstants.AVID_PREFIX);
		
		Warehouse.lookForClass(Avid_MC_Mob_ReferenceImpl.class);
	}
}
