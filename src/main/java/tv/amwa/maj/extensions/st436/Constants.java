package tv.amwa.maj.extensions.st436;

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.record.AUID;

/**
* <p>Constant values, including the XML namespace, for package <code>tv.amwa.maj.extensions.st436</code>.</p>
*
* @author <a href="mailto:Richard.Cartwright@quantel.com">Richard Cartwright</a>
*/
public interface Constants {

    /**
     * <p>Namespace for all symbols of the extension namespace.</p>
     *
     * @see #XML_PREFIX
     */
    public final static String XML_NAMESPACE =
        "http://www.smpte-ra.org/schemas/436/2006";

    /**
     * <p>Prefix for all symbols of the extension namespace.</p>
     *
     * @see #XML_NAMESPACE
     */
    public final static String XML_PREFIX = "st436";

    /**
     * <p>Identification of the extension namespace.</p>
     */
    public final static AUID EXTENSION_ID = Forge.makeAUID(
            0x10b5a52d, (short) 0x5645, (short) 0x427f,
            new byte[] { (byte) 0x97, 0x07, (byte) 0xe2, (byte) 0x86, (byte) 0xe5, 0x1b, (byte) 0xc3, (byte) 0xf7 } );

}
