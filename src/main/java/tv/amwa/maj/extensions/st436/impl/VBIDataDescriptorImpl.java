package tv.amwa.maj.extensions.st436.impl;

import java.io.Serializable;

import tv.amwa.maj.extensions.st436.Constants;
import tv.amwa.maj.extensions.st436.VBIDataDescriptor;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.model.impl.DataEssenceDescriptorImpl;

/**
 * <p>Implements the description of VBI frame elements.</p>
 *
 * @author <a href="mailto:Richard.Cartwright@quantel.com">Richard Cartwright</a>
 *
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x5b00,
    uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
    definedName = "VBIDataDescriptor",
    description = "Specifies that a file source package is associated with VBI data.",
    symbol = "VBIDataDescriptor",
    namespace = Constants.XML_NAMESPACE,
    prefix = Constants.XML_PREFIX)
public class VBIDataDescriptorImpl 
    extends DataEssenceDescriptorImpl 
    implements VBIDataDescriptor,
		Serializable, 
		Cloneable {

	private static final long serialVersionUID = 3515238591382159488L;

	@Override
	public VBIDataDescriptor clone() {
		return (VBIDataDescriptor) super.clone();
	}

}
