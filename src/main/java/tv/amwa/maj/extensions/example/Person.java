package tv.amwa.maj.extensions.example;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaEntity;
import tv.amwa.maj.record.DateStruct;

/**
 * <p>Person who makes and/or contributes a resource.</p>
 *
 *
 */
public interface Person
    extends
        MediaEntity,
        Cloneable,
        Contributor {

    /**
     * <p>Returns the date of birth of the person.</p>
     *
     * <p>This is an optional property.</p>
     *
     * @return Date of birth of the person.
     *
     * @throws PropertyNotPresentException The optional d o b property is not present
     * for this person.
     */
    public DateStruct getDOB()
        throws PropertyNotPresentException;

    /**
     * <p>Sets the date of birth of the person.</p>
     *
     * <p>Set this optional property to <code>null</code> to omit it.</p>
     *
     * @param dOB Date of birth of the person.
     */
    public void setDOB(
            DateStruct dOB);

    /**
     * <p>Returns the contact e-mail address for the person.</p>
     *
     * <p>This is an optional property.</p>
     *
     * @return Contact e-mail address for the person.
     *
     * @throws PropertyNotPresentException The optional email property is not present
     * for this person.
     */
    public String getEmail()
        throws PropertyNotPresentException;

    /**
     * <p>Sets the contact e-mail address for the person.</p>
     *
     * <p>Set this optional property to <code>null</code> to omit it.</p>
     *
     * @param email Contact e-mail address for the person.
     */
    public void setEmail(
            String email);

    /**
     * <p>Create a cloned copy of this person.</p>
     *
     * @return Cloned copy of this person.
     */
    public Person clone();

}
