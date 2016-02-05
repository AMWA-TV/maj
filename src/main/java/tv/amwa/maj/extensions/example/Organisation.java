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
import tv.amwa.maj.integer.UInt64;

/**
 * <p>Organisation that makes or contributes a resource.</p>
 *
 *
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
