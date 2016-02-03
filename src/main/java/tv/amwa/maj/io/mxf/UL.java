package tv.amwa.maj.io.mxf;

import tv.amwa.maj.record.AUID;

public interface UL extends AUID {

	public byte[] getUniversalLabel();
	
	public UL clone();
}
