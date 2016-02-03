package tv.amwa.maj.extensions.st436;

import tv.amwa.maj.model.DataEssenceDescriptor;

/** 
 * <p>Descriptor for VBI frame elements. When VBI packets exist in an MXF file, the appropriate descriptor shall be present in the file. 
 * The descriptor shall be associated with a data track using the mechanisms defined in SMPTE 377M.</p>
 *  
 * <p>The type of data carried in the VBI data is determined packet-by-packet in the packet itself, as specified in SMPTE ST 0291
 * and its associated <a href="http://smpte-ra.org/S291/S291_reg.html">register</a>.</p>
 *
 * @author <a href="mailto:Richard.Cartwright@quantel.com">Richard Cartwright</a>
 *
 */
public interface VBIDataDescriptor 
    extends DataEssenceDescriptor {

	/**
	 * <p>Create a cloned copy of this VBI data descriptor.</p>
	 *
	 * @return Cloned copy of this VBI data descriptor.
	 */
	public VBIDataDescriptor clone();
}
