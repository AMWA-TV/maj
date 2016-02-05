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

import tv.amwa.maj.industry.MediaEntity;
import tv.amwa.maj.model.DescriptiveObject;

/**
 * <p>Entity that creates or contributes to a resource.</p>
 *
 *
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
