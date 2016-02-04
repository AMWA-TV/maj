package tv.amwa.maj.extensions.quantel;

import tv.amwa.maj.industry.MediaEntity;
import tv.amwa.maj.model.AAFFileDescriptor;

/**
 * <p>null</p>
 *
 *
 */
public interface PolyFileDescriptor
    extends
        MediaEntity,
        Cloneable,
        AAFFileDescriptor {

    /**
     * <p>Create a cloned copy of this poly file descriptor.</p>
     *
     * @return Cloned copy of this poly file descriptor.
     */
    public PolyFileDescriptor clone();

}
