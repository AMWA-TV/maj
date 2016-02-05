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
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.example.Constants;
import tv.amwa.maj.extensions.example.Contributor;
import tv.amwa.maj.extensions.example.DCMIType;
import tv.amwa.maj.extensions.example.SimpleDescription;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.impl.DescriptiveFrameworkImpl;
import tv.amwa.maj.record.DateStruct;

/**
 * <p>Very basic description of an item, based on a few Dublin Core terms. Implementation.</p>
 *
 *
 */
@MediaClass(
    definedName = "SimpleDescription",
    uuid1 = 0x0f201101, uuid2 = (short) 0x0101, uuid3 = (short) 0x0000,
    uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x53, 0x01, 0x01 },
    description = "Very basic description of an item, based on a few Dublin Core terms.",
    namespace = Constants.XML_NAMESPACE,
    prefix = Constants.XML_PREFIX,
    symbol = "SimpleDescription")
public class SimpleDescriptionImpl
    extends
        DescriptiveFrameworkImpl
    implements
        SimpleDescription,
        CommonConstants,
        Serializable,
        XMLSerializable,
        Cloneable {

    private static final long serialVersionUID = 1381620862583100288l;

    private String title;
    private Contributor creator;
    private String identifier;
    private DCMIType type;
    private DateStruct dateAccepted;

    public SimpleDescriptionImpl() { }

    @MediaProperty(
        definedName = "Title",
        uuid1 = 0x0f201101, uuid2 = (short) 0x0101, uuid3 = (short) 0x0100,
        uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
        typeName = "UTF16String",
        optional = false,
        uniqueIdentifier = false,
        pid = 0x0000,
        description = "A name given to the resource.",
        symbol = "Title")
    public String getTitle() {

        return title;
    }

    @MediaPropertySetter("Title")
    public void setTitle(
            String title)
        throws NullPointerException {

        if (title == null)
            throw new NullPointerException("Cannot set the required title with a null value.");

        this.title = title;
    }

    @MediaProperty(
        definedName = "Creator",
        uuid1 = 0x0f201101, uuid2 = (short) 0x0101, uuid3 = (short) 0x0500,
        uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
        typeName = "ContributorStrongReference",
        optional = true,
        uniqueIdentifier = false,
        pid = 0x0000,
        description = "An entity primarily responsible for making the resource.",
        symbol = "Creator")
    public Contributor getCreator()
        throws PropertyNotPresentException {

        if (creator == null)
            throw new PropertyNotPresentException("The optional creator property is not present for this simple description.");

        return creator.clone();
    }

    @MediaPropertySetter("Creator")
    public void setCreator(
            Contributor creator) {

        if (creator == null) {
            this.creator = null;
            return;
        }

        this.creator = creator.clone();
    }

    @MediaProperty(
        definedName = "Identifier",
        uuid1 = 0x0f201101, uuid2 = (short) 0x0101, uuid3 = (short) 0x0300,
        uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
        typeName = "UTF16String",
        optional = false,
        uniqueIdentifier = false,
        pid = 0x0000,
        description = "An unambiguous reference to the resource within a given context.",
        symbol = "Identifier")
    public String getIdentifier() {

        return identifier;
    }

    @MediaPropertySetter("Identifier")
    public void setIdentifier(
            String identifier)
        throws NullPointerException {

        if (identifier == null)
            throw new NullPointerException("Cannot set the required identifier with a null value.");

        this.identifier = identifier;
    }

    @MediaProperty(
        definedName = "Type",
        uuid1 = 0x0f201101, uuid2 = (short) 0x0101, uuid3 = (short) 0x0400,
        uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
        typeName = "DCMIType",
        optional = true,
        uniqueIdentifier = false,
        pid = 0x0000,
        description = "Identity of the genre of the resource.",
        symbol = "Type")
    public DCMIType getType()
        throws PropertyNotPresentException {

        if (type == null)
            throw new PropertyNotPresentException("The optional type property is not present for this simple description.");

        return type;
    }

    @MediaPropertySetter("Type")
    public void setType(
            DCMIType type) {

        if (type == null) {
            this.type = null;
            return;
        }

        this.type = type;
    }

    @MediaProperty(
        definedName = "DateAccepted",
        uuid1 = 0x0f201101, uuid2 = (short) 0x0101, uuid3 = (short) 0x0200,
        uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
        typeName = "DateStruct",
        optional = true,
        uniqueIdentifier = false,
        pid = 0x0000,
        description = "Date of acceptance of the resource.",
        symbol = "DateAccepted")
    public DateStruct getDateAccepted()
        throws PropertyNotPresentException {

        if (dateAccepted == null)
            throw new PropertyNotPresentException("The optional date accepted property is not present for this simple description.");

        return dateAccepted.clone();
    }

    @MediaPropertySetter("DateAccepted")
    public void setDateAccepted(
            DateStruct dateAccepted) {

        if (dateAccepted == null) {
            this.dateAccepted = null;
            return;
        }

        this.dateAccepted = dateAccepted.clone();
    }

    public SimpleDescription clone() {

        return (SimpleDescription) super.clone();
    }

}
