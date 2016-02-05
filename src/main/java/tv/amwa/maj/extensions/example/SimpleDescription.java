/*
 * Copyright 2016 Richard Cartwright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tv.amwa.maj.extensions.example;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaEntity;
import tv.amwa.maj.model.DescriptiveFramework;
import tv.amwa.maj.record.DateStruct;

/**
 * <p>Very basic description of an item, based on a few Dublin Core terms.</p>
 *
 *
 */
public interface SimpleDescription
    extends
        MediaEntity,
        Cloneable,
        DescriptiveFramework {

    /**
     * <p>Returns the a name given to the resource.</p>
     *
     * @return A name given to the resource.
     */
    public String getTitle();

    /**
     * <p>Sets the a name given to the resource.</p>
     *
     * @param title A name given to the resource.
     *
     * @throws NullPointerException Cannot set the required title with a <code>null</code>
     * value.
     */
    public void setTitle(
            String title)
        throws NullPointerException;

    /**
     * <p>Returns the an entity primarily responsible for making the resource.</p>
     *
     * <p>This is an optional property.</p>
     *
     * @return An entity primarily responsible for making the resource.
     *
     * @throws PropertyNotPresentException The optional creator property is not present
     * for this simple description.
     */
    public Contributor getCreator()
        throws PropertyNotPresentException;

    /**
     * <p>Sets the an entity primarily responsible for making the resource.</p>
     *
     * <p>Set this optional property to <code>null</code> to omit it.</p>
     *
     * @param creator An entity primarily responsible for making the resource.
     */
    public void setCreator(
            Contributor creator);

    /**
     * <p>Returns the an unambiguous reference to the resource within a given context.</p>
     *
     * @return An unambiguous reference to the resource within a given context.
     */
    public String getIdentifier();

    /**
     * <p>Sets the an unambiguous reference to the resource within a given context.</p>
     *
     * @param identifier An unambiguous reference to the resource within a given context.
     *
     * @throws NullPointerException Cannot set the required identifier with a <code>null</code>
     * value.
     */
    public void setIdentifier(
            String identifier)
        throws NullPointerException;

    /**
     * <p>Returns the identity of the genre of the resource.</p>
     *
     * <p>This is an optional property.</p>
     *
     * @return Identity of the genre of the resource.
     *
     * @throws PropertyNotPresentException The optional type property is not present for
     * this simple description.
     */
    public DCMIType getType()
        throws PropertyNotPresentException;

    /**
     * <p>Sets the identity of the genre of the resource.</p>
     *
     * <p>Set this optional property to <code>null</code> to omit it.</p>
     *
     * @param type Identity of the genre of the resource.
     */
    public void setType(
            DCMIType type);

    /**
     * <p>Returns the date of acceptance of the resource.</p>
     *
     * <p>This is an optional property.</p>
     *
     * @return Date of acceptance of the resource.
     *
     * @throws PropertyNotPresentException The optional date accepted property is not
     * present for this simple description.
     */
    public DateStruct getDateAccepted()
        throws PropertyNotPresentException;

    /**
     * <p>Sets the date of acceptance of the resource.</p>
     *
     * <p>Set this optional property to <code>null</code> to omit it.</p>
     *
     * @param dateAccepted Date of acceptance of the resource.
     */
    public void setDateAccepted(
            DateStruct dateAccepted);

    /**
     * <p>Create a cloned copy of this simple description.</p>
     *
     * @return Cloned copy of this simple description.
     */
    public SimpleDescription clone();

}
