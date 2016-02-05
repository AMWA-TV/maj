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

package tv.amwa.maj.extensions.avid;

import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.impl.TypeDefinitionStrongObjectReferenceImpl;
import tv.amwa.maj.model.impl.InterchangeObjectImpl;
import tv.amwa.maj.record.impl.AUIDImpl;

public interface TypeDefinitions {

	public final static TypeDefinitionStrongObjectReference AvidStrongReference =
		new TypeDefinitionStrongObjectReferenceImpl(
				new AUIDImpl(0xf9a74d0a, (short) 0x7b30, (short) 0x11d3,
						new byte[] { (byte) 0xa0, 0x44, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb }),
				"AvidStrongReference",
				Warehouse.lookForClass(InterchangeObjectImpl.class));
}
