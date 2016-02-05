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

import tv.amwa.maj.extensions.avid.impl.Avid_MC_Mob_ReferenceImpl;
import tv.amwa.maj.industry.Warehouse;

public class AvidFactory {

	public final static void registerAvidExtensions() {

		Warehouse.registerTypes(TypeDefinitions.class, AvidConstants.AVID_NAMESPACE, AvidConstants.AVID_PREFIX);

		Warehouse.lookForClass(Avid_MC_Mob_ReferenceImpl.class);
	}
}
