package tv.amwa.maj.extensions.quantel;

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.record.AUID;

/**
 * <p>Constant values, including the XML namespace, for package <code>tv.amwa.maj.extensions.quantel</code>.</p>
 *
 *
 */
public interface QConstants {
    
    /**
     * <p>Namespace for all symbols of the extension namespace.</p>
     *
     * @see #XML_PREFIX
     */
    public final static String XML_NAMESPACE =
        "http://www.quantel.com/genQ/extensions";

    /**
     * <p>Prefix for all symbols of the extension namespace.</p>
     *
     * @see #XML_NAMESPACE
     */
    public final static String XML_PREFIX = "q";

    /**
     * <p>Identification of the extension namespace.</p>
     */
    public final static AUID EXTENSION_ID = Forge.makeAUID(
            0x2dc41275, (short) 0xd489, (short) 0x444a,
            new byte[] { (byte) 0xb1, 0x25, 0x62, (byte) 0xe1, 0x56, 0x23, 0x6f, 0x55 } );

}
