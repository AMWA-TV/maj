package tv.amwa.maj.extensions.example;

import tv.amwa.maj.industry.MediaEntity;
import tv.amwa.maj.model.DescriptiveObject;

/**
 * <p>Entity that creates or contributes to a resource.</p>
 *
 * @author Auto generation.
 */
public interface Contributor
    extends
        MediaEntity,
        Cloneable,
        DescriptiveObject {

    /**
     * <p>Returns the name of the contributor.</p>
     *
     * @return Name of the contributor.
     */
    public String getName();

    /**
     * <p>Sets the name of the contributor.</p>
     *
     * @param name Name of the contributor.
     *
     * @throws NullPointerException Cannot set the required name with a <code>null</code>
     * value.
     */
    public void setName(
            String name)
        throws NullPointerException;

    /**
     * <p>Create a cloned copy of this contributor.</p>
     *
     * @return Cloned copy of this contributor.
     */
    public Contributor clone();

}
