package tv.amwa.maj.extensions.example;

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.record.AUID;

/**
 * <p>Constant values, including the XML namespace, for package <code>tv.amwa.maj.extensions.example</code>.</p>
 *
 * @author Auto generation
 */
public interface Constants {
    
    /**
     * <p>Namespace for all symbols of the extension namespace.</p>
     *
     * @see #XML_PREFIX
     */
    public final static String XML_NAMESPACE =
        "http://amwa.tv/maj/extensions/example";

    /**
     * <p>Prefix for all symbols of the extension namespace.</p>
     *
     * @see #XML_NAMESPACE
     */
    public final static String XML_PREFIX = "eg";

    /**
     * <p>Identification of the extension namespace.</p>
     */
    public final static AUID EXTENSION_ID = Forge.makeAUID(
            0x0f201101, (short) 0x0000, (short) 0x0000,
            new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 } );

}
