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
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.meta.impl.TypeDefinitionVariableArrayImpl;

/**
 * <p>Extension type definitions defined for this package.</p>
 *
 * @see tv.amwa.maj.meta.TypeDefinition
 * @see tv.amwa.maj.industry.Warehouse
 * @see tv.amwa.maj.industry.TypeDefinitions
 */
public interface TypeDefinitions {

    /**
     * <p>Operation group strong reference variable array, a variable array type definition
     * with operation group strong reference elements.</p>
     *
     * @see tv.amwa.maj.industry.StrongReferenceVector
     * @see tv.amwa.maj.industry.WeakReferenceVector
     * @see tv.amwa.maj.industry.TypeDefinitions#OperationGroupStrongReference
     */
    public final static TypeDefinitionVariableArray OperationGroupStrongReferenceVariableArray = new TypeDefinitionVariableArrayImpl(
            Forge.makeAUID(0xe0427274, (short) 0x2630, (short) 0x497b,
                    new byte[] { (byte) 0x94, (byte) 0xa1, (byte) 0xb6, (byte) 0xb2, (byte) 0xe4, 0x70, 0x0d, 0x30 }),
            "OperationGroupStrongReferenceVariableArray",
            tv.amwa.maj.industry.TypeDefinitions.OperationGroupStrongReference);

}
