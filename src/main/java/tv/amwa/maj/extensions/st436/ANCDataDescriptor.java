package tv.amwa.maj.extensions.st436;

import tv.amwa.maj.model.DataEssenceDescriptor;

/** 
 * <p>Descriptor for ANC frame elements. When ANC packets exist in an MXF file, the appropriate descriptor shall be present in the file. 
 * The descriptor shall be associated with a data track using the mechanisms defined in SMPTE 377M.</p>
 *  
 * <p>The type of data carried in the ANC data is determined packet-by-packet in the packet itself, as specified in SMPTE ST 0291
 * and its associated <a href="http://smpte-ra.org/S291/S291_reg.html">register</a>.</p>
 *
 *
 *
 */
public interface ANCDataDescriptor 
	extends DataEssenceDescriptor {

	/**
	 * <p>Create a cloned copy of this ANC data descriptor.</p>
	 *
	 * @return Cloned copy of this ANC data descriptor.
	 */
	public ANCDataDescriptor clone();
	
}
