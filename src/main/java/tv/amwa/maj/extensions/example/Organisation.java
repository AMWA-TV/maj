package tv.amwa.maj.extensions.example;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaEntity;
import tv.amwa.maj.integer.UInt64;

/**
 * <p>Organisation that makes or contributes a resource.</p>
 *
 * @author Auto generation.
 */
public interface Organisation
    extends
        MediaEntity,
        Cloneable,
        Contributor {

    /**
     * <p>Returns the registered company number for the organisation.</p>
     *
     * @return Registered company number for the organisation.
     */
    public @UInt64 long getCompanyNo();

    /**
     * <p>Sets the registered company number for the organisation.</p>
     *
     * @param companyNo Registered company number for the organisation.
     * @throws IllegalArgumentException Cannot set the company no property with the given
     * value.
     */
    public void setCompanyNo(
            @UInt64 long companyNo)
        throws IllegalArgumentException;

    /**
     * <p>Returns the contact person at the organisation responsible for the contributed
     * resource.</p>
     *
     * <p>This is an optional property.</p>
     *
     * @return Contact person at the organisation responsible for the contributed resource.
     *
     * @throws PropertyNotPresentException The optional contact property is not present
     * for this organisation.
     */
    public Person getContact()
        throws PropertyNotPresentException;

    /**
     * <p>Sets the contact person at the organisation responsible for the contributed
     * resource.</p>
     *
     * <p>Set this optional property to <code>null</code> to omit it.</p>
     *
     * @param contact Contact person at the organisation responsible for the contributed
     * resource.
     */
    public void setContact(
            Person contact);

    /**
     * <p>Create a cloned copy of this organisation.</p>
     *
     * @return Cloned copy of this organisation.
     */
    public Organisation clone();

}
