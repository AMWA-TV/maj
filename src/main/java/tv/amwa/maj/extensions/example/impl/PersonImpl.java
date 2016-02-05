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
import tv.amwa.maj.extensions.example.Person;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.record.DateStruct;

/**
 * <p>Person who makes and/or contributes a resource. Implementation.</p>
 *
 *
 */
@MediaClass(
    definedName = "Person",
    uuid1 = 0x0f201101, uuid2 = (short) 0x0103, uuid3 = (short) 0x0000,
    uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x53, 0x01, 0x01 },
    description = "Person who makes and/or contributes a resource.",
    namespace = Constants.XML_NAMESPACE,
    prefix = Constants.XML_PREFIX,
    symbol = "Person")
public class PersonImpl
    extends
        ContributorImpl
    implements
        Person,
        CommonConstants,
        Serializable,
        XMLSerializable,
        Cloneable {

    private static final long serialVersionUID = -5057133262584192569l;

    private DateStruct dOB;
    private String email;

    public PersonImpl() { }

    @MediaProperty(
        definedName = "DOB",
        uuid1 = 0x0f201101, uuid2 = (short) 0x0103, uuid3 = (short) 0x0200,
        uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
        typeName = "DateStruct",
        optional = true,
        uniqueIdentifier = false,
        pid = 0x0000,
        description = "Date of birth of the person.",
        symbol = "DOB")
    public DateStruct getDOB()
        throws PropertyNotPresentException {

        if (dOB == null)
            throw new PropertyNotPresentException("The optional d o b property is not present for this person.");

        return dOB.clone();
    }

    @MediaPropertySetter("DOB")
    public void setDOB(
            DateStruct dOB) {

        if (dOB == null) {
            this.dOB = null;
            return;
        }

        this.dOB = dOB.clone();
    }

    @MediaProperty(
        definedName = "Email",
        uuid1 = 0x0f201101, uuid2 = (short) 0x0103, uuid3 = (short) 0x0100,
        uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01 },
        typeName = "UTF16String",
        optional = true,
        uniqueIdentifier = false,
        pid = 0x0000,
        description = "Contact e-mail address for the person.",
        symbol = "Email")
    public String getEmail()
        throws PropertyNotPresentException {

        if (email == null)
            throw new PropertyNotPresentException("The optional email property is not present for this person.");

        return email;
    }

    @MediaPropertySetter("Email")
    public void setEmail(
            String email) {

        if (email == null) {
            this.email = null;
            return;
        }

        this.email = email;
    }

    public Person clone() {

        return (Person) super.clone();
    }

}
