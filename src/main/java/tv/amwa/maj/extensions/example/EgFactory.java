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

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.Warehouse;

/**
 * <p>Factory for all types and classes of symbolespace <code>http://amwa.tv/maj/extensions/example</code>.
 * Contains methods to initialize the MAJ media engine and warehouses with the types
 * of this symbol space.</p>
 *
 * <p>A simple example of how to write an extension metadata set.</p>
 *
 *
 *
 * @see TypeDefinitions
 * @see tv.amwa.maj.industry.MediaEngine
 * @see tv.amwa.maj.industry.Warehouse
 */
public class EgFactory{

    private static boolean initialized = false;

    /**
     * <p>List of all the implementing classes defined for this symbol space.</p>
     *
     * @see #initialize()
     */
    public final static Class<?>[] CLASSES = new Class<?>[] {
            tv.amwa.maj.extensions.example.impl.SimpleDescriptionImpl.class,
            tv.amwa.maj.extensions.example.impl.ContributorImpl.class,
            tv.amwa.maj.extensions.example.impl.OrganisationImpl.class,
            tv.amwa.maj.extensions.example.impl.PersonImpl.class,
    };

    /**
     * <p>Initialize all the types of this symbol space and make them available through
     * the MAJ {@linkplain tv.amwa.maj.industry.MediaEngine media engine}. The media engine
     * and associated APIs can then make, serialize and deserialize values to all supported
     * XML formats, binary formats and persistance providers.</p>
     *
     * @see tv.amwa.maj.industry.MediaEngine#initializeAAF()
     * @see #CLASSES
     */
    public final static void initialize() {

        if (initialized) return;

        // Register all of the symbolspace classes
        for ( Class<?> extensionClass : CLASSES )
            Warehouse.lookForClass(extensionClass);

        // Register all of the initial extendible enumeration values
        // Register all of the extension type definitions
        Warehouse.registerTypes(TypeDefinitions.class, Constants.XML_NAMESPACE, Constants.XML_PREFIX);

        initialized = true;
    }

    /**
     * <p>Create a new instance of a class defined in this symbol space from its name
     * and initial property values.</p>
     *
     * @param className Name of the class in this symbol space to create.
     * @param properties List of property identifier and value pairs to use to make a
     * value of the required type.
     * @return Newly created value of the named class.
     *
     * @throws NullPointerException Cannot create a new instance from a <code>null</code>
     * name or <code>null</code> property specifications.
     * @throws IllegalArgumentException Unable to use the given properties to create a
     * value of the name class or the named class does not exist.
     *
     * @see tv.amwa.maj.industry.Forge#makeByName(String, String, Object...)
     */
    @SuppressWarnings("unchecked")
    public final static <T extends MetadataObject> T make(
            String className,
            Object... properties)
        throws NullPointerException,
            IllegalArgumentException {

        return (T) Forge.makeByName(Constants.XML_NAMESPACE, className, properties);
    }

}
