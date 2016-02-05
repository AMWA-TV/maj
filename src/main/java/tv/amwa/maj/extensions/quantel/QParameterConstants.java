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

package tv.amwa.maj.extensions.quantel;

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.model.ParameterDefinition;

public interface QParameterConstants {

//    <ParameterDefinition aaf:uid="urn:uuid:6503c60e-e16c-4900-b0bb-3e21f411a211">
//    <ParameterType>Rational</ParameterType>
//    <DefinitionObjectIdentification>urn:uuid:6503c60e-e16c-4900-b0bb-3e21f411a211</DefinitionObjectIdentification>
//    <DefinitionObjectDescription>This number represents the y position of the wipe centre</DefinitionObjectDescription>
//    <DefinitionObjectName>iQ Wipe Position Y</DefinitionObjectName>
//  </ParameterDefinition>

	public ParameterDefinition wipePositionY = Forge.make(ParameterDefinition.class,
			"DefinitionObjectIdentification", Forge.makeAUID(0x6503c60e, (short) 0xe16c, (short) 0x4900,
					new byte[] { (byte) 0xb0, (byte) 0xbb, 0x3e, 0x21, (byte) 0xf4, 0x11, (byte) 0xa2, 0x11 } ),
			"DefinitionObjectName", "iQ Wipe Position Y",
			"DefinitionObjectDescription", "This number represents the y position of the wipe centre.",
			"ParameterType", Warehouse.lookForType("Rational"));

//  <ParameterDefinition aaf:uid="urn:uuid:0782c20f-09eb-4384-8df6-07cf7948bd66">
//    <ParameterType>Int32</ParameterType>
//    <DefinitionObjectIdentification>urn:uuid:0782c20f-09eb-4384-8df6-07cf7948bd66</DefinitionObjectIdentification>
//    <DefinitionObjectDescription>This number represents the presence of a wipe border</DefinitionObjectDescription>
//    <DefinitionObjectName>iQ Wipe Border</DefinitionObjectName>
//  </ParameterDefinition>

	public ParameterDefinition wipeBorder = Forge.make(ParameterDefinition.class,
			"DefinitionObjectIdentification", Forge.makeAUID(0x782c20f, (short) 0x9eb, (short) 0x4384,
					new byte[] { (byte) 0x8d, (byte) 0xf6, 0x7, (byte) 0xcf, 0x79, 0x48, (byte) 0xbd, 0x66 }),
			"DefinitionObjectName", "iQ Wipe Border",
			"DefinitionObjectDescription", "This number represents the presence of a wipe border.",
			"ParameterType", Warehouse.lookForType("Int32"));

//  <ParameterDefinition aaf:uid="urn:uuid:bb55ba48-0788-4603-a830-50f4a4f4972d">
//    <ParameterType>Rational</ParameterType>
//    <DefinitionObjectIdentification>urn:uuid:bb55ba48-0788-4603-a830-50f4a4f4972d</DefinitionObjectIdentification>
//    <DefinitionObjectDescription>This number represents the width of the wipe border</DefinitionObjectDescription>
//    <DefinitionObjectName>iQ Wipe Border Width</DefinitionObjectName>
//  </ParameterDefinition>

	public ParameterDefinition wipeBorderWidth = Forge.make(ParameterDefinition.class,
			"DefinitionObjectIdentification", Forge.makeAUID(0xbb55ba48, (short) 0x0788, (short) 0x4603,
					new byte[] { (byte) 0xa8, 0x30, 0x50, (byte) 0xf4, (byte) 0xa4, (byte) 0xf4, (byte) 0x97, 0x2d }),
			"DefinitionObjectName", "iQ Wipe Border Width",
			"DefinitionObjectDescription", "This number represents the width of the wipe border.",
			"ParameterType", Warehouse.lookForType("Rational"));

//  <ParameterDefinition aaf:uid="urn:uuid:31346b9a-acbf-4b28-996b-34573a66fc87">
//    <ParameterType>Rational</ParameterType>
//    <DefinitionObjectIdentification>urn:uuid:31346b9a-acbf-4b28-996b-34573a66fc87</DefinitionObjectIdentification>
//    <DefinitionObjectDescription>This number repesents the 'out' softness of wipe</DefinitionObjectDescription>
//    <DefinitionObjectName>iQ Wipe Out Softness</DefinitionObjectName>
//  </ParameterDefinition>

	public ParameterDefinition wipeOutSoftness = Forge.make(ParameterDefinition.class,
			"DefinitionObjectIdentification", Forge.makeAUID(0x31346b9a, (short) 0xacbf, (short) 0x4b28,
					new byte[] { (byte) 0x99, 0x6b, 0x34, 0x57, 0x3a, 0x66, (byte) 0xfc, (byte) 0x87 }),
			"DefinitionObjectName", "iQ Wipe Out Softness",
			"DefinitionObjectDescription", "This number repesents the 'out' softness of wipe.",
			"ParameterType", Warehouse.lookForType("Rational"));


//  <ParameterDefinition aaf:uid="urn:uuid:648a09b6-095e-4691-bb2d-4ecf4e508ee8">
//    <ParameterType>Int64</ParameterType>
//    <DefinitionObjectIdentification>urn:uuid:648a09b6-095e-4691-bb2d-4ecf4e508ee8</DefinitionObjectIdentification>
//    <DefinitionObjectDescription>This number represents the colour of the wipe border</DefinitionObjectDescription>
//    <DefinitionObjectName>iQ Wipe Border Colour</DefinitionObjectName>
//  </ParameterDefinition>

	public ParameterDefinition wipeBorderColour = Forge.make(ParameterDefinition.class,
			"DefinitionObjectIdentification", Forge.makeAUID(0x648a09b6, (short) 0x95e, (short) 0x4691,
					new byte[] { (byte) 0xbb, 0x2d, 0x4e, (byte) 0xcf, 0x4e, 0x50, (byte) 0x8e, (byte) 0xe8 }),
			"DefinitionObjectName", "iQ Wipe Border Colour",
			"DefinitionObjectDescription", "This number represents the colour of the wipe border.",
			"ParameterType", Warehouse.lookForType("Int64"));

//  <ParameterDefinition aaf:uid="urn:uuid:5f90c0bc-d59b-46b0-b8d2-f1694b87f2b8">
//    <ParameterType>Rational</ParameterType>
//    <DefinitionObjectIdentification>urn:uuid:5f90c0bc-d59b-46b0-b8d2-f1694b87f2b8</DefinitionObjectIdentification>
//    <DefinitionObjectDescription>This number represents the x position of the wipe centre</DefinitionObjectDescription>
//    <DefinitionObjectName>iQ Wipe Position X</DefinitionObjectName>
//  </ParameterDefinition>

	public ParameterDefinition wipePositionX = Forge.make(ParameterDefinition.class,
			"DefinitionObjectIdentification", Forge.makeAUID(0x5f90c0bc, (short) 0xd59b, (short) 0x46b0,
					new byte[] { (byte) 0xb8, (byte) 0xd2, (byte) 0xf1, 0x69, 0x4b, (byte) 0x87, (byte) 0xf2, (byte) 0xb8 }),
			"DefinitionObjectName", "iQ Wipe Position X",
			"DefinitionObjectDescription", "This number represents the x position of the wipe centre.",
			"ParameterType", Warehouse.lookForType("Rational"));

//  <ParameterDefinition aaf:uid="urn:uuid:a91cbee2-49cb-416a-9fca-3f4230605d2e">
//    <ParameterType>Rational</ParameterType>
//    <DefinitionObjectIdentification>urn:uuid:a91cbee2-49cb-416a-9fca-3f4230605d2e</DefinitionObjectIdentification>
//    <DefinitionObjectDescription>This number repesents the 'in' softness of wipe</DefinitionObjectDescription>
//    <DefinitionObjectName>iQ Wipe In Softness</DefinitionObjectName>
//  </ParameterDefinition>

	public ParameterDefinition wipeInSoftness = Forge.make(ParameterDefinition.class,
			"DefinitionObjectIdentification", Forge.makeAUID(0xa91cbee2, (short) 0x49cb, (short) 0x416a,
					new byte[] { (byte) 0x9f, (byte) 0xca, 0x3f, 0x42, 0x30, 0x60, 0x5d, 0x2e }),
			"DefinitionObjectName", "iQ Wipe In Softness",
			"DefinitionObjectDescription", "This number repesents the 'in' softness of wipe.",
			"ParameterType", Warehouse.lookForType("Rational"));

//  <ParameterDefinition aaf:uid="urn:uuid:9e23e6e9-7f23-4a4b-b2ee-24242098afe9">
//    <ParameterType>Int32</ParameterType>
//    <DefinitionObjectIdentification>urn:uuid:9e23e6e9-7f23-4a4b-b2ee-24242098afe9</DefinitionObjectIdentification>
//    <DefinitionObjectDescription>This number indicates frame or field rendering</DefinitionObjectDescription>
//    <DefinitionObjectName>iQ Wipe Field Render</DefinitionObjectName>
//  </ParameterDefinition>

	public ParameterDefinition wipeFieldRender = Forge.make(ParameterDefinition.class,
			"DefinitionObjectIdentification", Forge.makeAUID(0x9e23e6e9, (short) 0x7f23, (short) 0x4a4b,
					new byte[] { (byte) 0xb2, (byte) 0xee, 0x24, 0x24, 0x20, (byte) 0x98, (byte) 0xaf, (byte) 0xe9 }),
			"DefinitionObjectName", "iQ Wipe Field Render",
			"DefinitionObjectDescription", "This number indicates frame or field rendering.",
			"ParameterType", Warehouse.lookForType("Int32"));

//  <ParameterDefinition aaf:uid="urn:uuid:e65cbbea-e2a5-424a-9a2d-7c5cebca9f38">
//    <ParameterType>Int32</ParameterType>
//    <DefinitionObjectIdentification>urn:uuid:e65cbbea-e2a5-424a-9a2d-7c5cebca9f38</DefinitionObjectIdentification>
//    <DefinitionObjectDescription>This number represents the presence of a positional wipe</DefinitionObjectDescription>
//    <DefinitionObjectName>iQ Wipe Position</DefinitionObjectName>
//  </ParameterDefinition>

	public ParameterDefinition wipePosition = Forge.make(ParameterDefinition.class,
			"DefinitionObjectIdentification", Forge.makeAUID(0xe65cbbea, (short) 0xe2a5, (short) 0x424a,
					new byte[] { (byte) 0x9a, 0x2d, 0x7c, 0x5c, (byte) 0xeb, (byte) 0xca, (byte) 0x9f, 0x38 }),
			"DefinitionObjectName", "iQ Wipe Position",
			"DefinitionObjectDescription", "This number represents the presence of a positional wipe.",
			"ParameterType", Warehouse.lookForType("Int32"));

	public ParameterDefinition[] allParameters = new ParameterDefinition[] {
			wipeBorder, wipeBorderColour, wipeBorderWidth, wipeFieldRender,
			wipeInSoftness, wipeOutSoftness, wipePosition, wipePositionX, wipePositionY };
}
