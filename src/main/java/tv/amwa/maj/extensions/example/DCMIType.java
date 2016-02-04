package tv.amwa.maj.extensions.example;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/**
 * <p>Different genres of resource.</p>
 *
 * @see TypeDefinitions#DCMIType
 *
 *
 */
public enum DCMIType
    implements MediaEnumerationValue {

    /** <p>Moving image element.</p> */
    MovingImage (1l),
    /** <p>Still image element.</p> */
    StillImage (2l),
    /** <p>Sound element.</p> */
    Sound (3l),
    /** <p>Data set element.</p> */
    DataSet (4l),
    ;

    private final long value;

    DCMIType(long value) { this.value = value; }

    @Int64 public long value() { return value; }

    public String symbol() {

        return "DCMIType_" + name();
    }

}
