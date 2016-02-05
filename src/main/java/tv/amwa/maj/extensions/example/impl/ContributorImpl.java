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

package tv.amwa.maj.extensions.example.impl;

import java.io.Serializable;
import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.extensions.example.Constants;
import tv.amwa.maj.extensions.example.Contributor;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.impl.DescriptiveObjectImpl;

/**
 * <p>Entity that creates or contributes to a resource. Implementation.</p>
 *
 *
 */
@MediaClass(
    definedName = "Contributor",
    uuid1 = 0x0f201101, uuid2 = (short) 0x0102, uuid3 = (short) 0x0000,
    uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x53, 0x01, 0x01 },
    description = "Entity that creates or contributes to a resource.",
    namespace = Constants.XML_NAMESPACE,
    prefix = Constants.XML_PREFIX,
    symbol = "Contributor")
public abstract class ContributorImpl
    extends
        DescriptiveObjectImpl
    implements
        Contributor,
        CommonConstants,
        Serializable,
        XMLSerializable,
        Cloneable {

    private static final long serialVersionUID = -7253804019843638163l;

    private String name;

    public ContributorImpl() { }

    @MediaProperty(
        definedName = "Name",
        uuid1 = 0x0f201101, uuid2 = (short) 0x0102, uuid3 = (short) 0x0100,
        uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
        typeName = "UTF16String",
        optional = false,
        uniqueIdentifier = false,
        pid = 0x0000,
        description = "Name of the contributor.",
        symbol = "Name")
    public String getName() {

        return name;
    }

    @MediaPropertySetter("Name")
    public void setName(
            String name)
        throws NullPointerException {

        if (name == null)
            throw new NullPointerException("Cannot set the required name with a null value.");

        this.name = name;
    }

    public Contributor clone() {

        return (Contributor) super.clone();
    }

}
