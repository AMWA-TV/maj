package tv.amwa.maj.extensions.st436.impl;

import java.io.Serializable;

import tv.amwa.maj.extensions.st436.ANCDataDescriptor;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.model.impl.DataEssenceDescriptorImpl;
import tv.amwa.maj.extensions.st436.Constants;

/**
 * <p>Implements the description of ANC frame elements.</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x5c00,
    uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
    definedName = "ANCDataDescriptor",
    description = "Specifies that a file source package is associated with ANC data.",
    symbol = "ANCDataDescriptor",
    namespace = Constants.XML_NAMESPACE,
    prefix = Constants.XML_PREFIX)
public class ANCDataDescriptorImpl 
    extends DataEssenceDescriptorImpl 
    implements ANCDataDescriptor, 
		Serializable,
		Cloneable {

	private static final long serialVersionUID = -196267894961231808L;

	@Override
	public ANCDataDescriptor clone() {
		
		return (ANCDataDescriptor) super.clone();
	}
}
