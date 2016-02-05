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

package tv.amwa.maj.extensions.avid.impl;

import java.io.Serializable;

import tv.amwa.maj.extensions.avid.AvidConstants;
import tv.amwa.maj.extensions.avid.Avid_MC_Mob_Reference;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.Int64;
import tv.amwa.maj.model.impl.InterchangeObjectImpl;
import tv.amwa.maj.record.PackageID;

@MediaClass(uuid1 = 0x6619f8e0, uuid2 = (short) 0xfe77, uuid3 = (short) 0x11d3,
		  uuid4 = { (byte) 0xa0, (byte) 0x84, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb },
		  definedName = "Avid MC Mob Reference",
		  description = "Avid extension MC mob reference.",
		  symbol = "Avid_MC_Mob_Reference",
		  namespace = AvidConstants.AVID_NAMESPACE,
		  prefix = AvidConstants.AVID_PREFIX)
public class Avid_MC_Mob_ReferenceImpl
	extends
		InterchangeObjectImpl
	implements
		Avid_MC_Mob_Reference,
		Cloneable,
		Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 5045667697348328203L;

	private PackageID mob_Reference_MobID;
	private @Int64 long mob_Reference_Position;

	public Avid_MC_Mob_ReferenceImpl() { }

	@MediaProperty(uuid1 = 0x81110e9f, uuid2 = (short) 0xfe7c, uuid3 = (short) 0x11d3,
			uuid4 = { (byte) 0xa0, (byte) 0x84, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb },
			definedName = "Mob Reference MobID",
			typeName = "PackageIDType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "Mob_Reference_MobID")
	public PackageID getMobReferenceMobID() {

		return mob_Reference_MobID.clone();
	}

	@MediaPropertySetter("Mob Reference MobID")
	public void setMobReferenceMobID(
			PackageID mobReferenceMobID)
		throws NullPointerException {

		if (mobReferenceMobID == null)
			throw new NullPointerException("Cannot set the mob reference mob ID to a null value.");

		this.mob_Reference_MobID = mobReferenceMobID.clone();
	}

	@MediaProperty(uuid1 = 0x81110ea0, uuid2 = (short) 0xfe7c, uuid3 = (short) 0x11d3,
			uuid4 = { (byte) 0xa0, (byte) 0x84, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb },
			definedName = "Mob Reference Position",
			typeName = "Int64",
			optional = false,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "Mob_Reference_Position")
	public @Int64 long getMobReferencePosition() {

		return mob_Reference_Position;
	}

	@MediaPropertySetter("Mob Reference Position")
	public void setMobReferencePosition(
			@Int64 long mobReferencePosition) {

		this.mob_Reference_Position = mobReferencePosition;
	}

	public Avid_MC_Mob_Reference clone() {

		return (Avid_MC_Mob_Reference) super.clone();
	}
}
