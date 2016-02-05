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
import tv.amwa.maj.extensions.example.Organisation;
import tv.amwa.maj.extensions.example.Person;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.io.xml.XMLSerializable;

/**
 * <p>Organisation that makes or contributes a resource. Implementation.</p>
 *
 *
 */
@MediaClass(
    definedName = "Organisation",
    uuid1 = 0x0f201101, uuid2 = (short) 0x0104, uuid3 = (short) 0x0000,
    uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x53, 0x01, 0x01 },
    description = "Organisation that makes or contributes a resource.",
    namespace = Constants.XML_NAMESPACE,
    prefix = Constants.XML_PREFIX,
    symbol = "Organisation")
public class OrganisationImpl
    extends
        ContributorImpl
    implements
        Organisation,
        CommonConstants,
        Serializable,
        XMLSerializable,
        Cloneable {

    private static final long serialVersionUID = -7452986601899923598l;

    private @UInt64 long companyNo;
    private Person contact;

    public OrganisationImpl() { }

    @MediaProperty(
        definedName = "CompanyNo",
        uuid1 = 0x0f201101, uuid2 = (short) 0x0104, uuid3 = (short) 0x0100,
        uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
        typeName = "UInt64",
        optional = false,
        uniqueIdentifier = false,
        pid = 0x0000,
        description = "Registered company number for the organisation.",
        symbol = "CompanyNo")
    public @UInt64 long getCompanyNo() {

        return companyNo;
    }

    @MediaPropertySetter("CompanyNo")
    public void setCompanyNo(
            @UInt64 long companyNo)
        throws IllegalArgumentException {

        this.companyNo = companyNo;
    }

    @MediaProperty(
        definedName = "Contact",
        uuid1 = 0x0f201101, uuid2 = (short) 0x0104, uuid3 = (short) 0x0200,
        uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
        typeName = "PersonStrongReference",
        optional = true,
        uniqueIdentifier = false,
        pid = 0x0000,
        description = "Contact person at the organisation responsible for the contributed resource.",
        symbol = "Contact")
    public Person getContact()
        throws PropertyNotPresentException {

        if (contact == null)
            throw new PropertyNotPresentException("The optional contact property is not present for this organisation.");

        return contact.clone();
    }

    @MediaPropertySetter("Contact")
    public void setContact(
            Person contact) {

        if (contact == null) {
            this.contact = null;
            return;
        }

        this.contact = contact.clone();
    }

    public Organisation clone() {

        return (Organisation) super.clone();
    }

}
