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

package tv.amwa.maj.io.mxf.impl;

import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.io.mxf.BodyPartition;
import tv.amwa.maj.io.mxf.BodyPartitionPack;

public class BodyPartitionImpl
	extends
		EssencePartitionImpl
	implements
		MetadataObject,
		BodyPartition {

	private BodyPartitionPack bodyPartitionPack;

	public BodyPartitionImpl() { }

	@Override
	public BodyPartitionPack getPartitionPack() {

		return bodyPartitionPack;
	}

	public void setPartitionPack(
			BodyPartitionPack bodyPartitionPack)
		throws NullPointerException {

		if (bodyPartitionPack == null)
			throw new NullPointerException("Cannot set a body partition's partition pack using a null value.");

		this.bodyPartitionPack = bodyPartitionPack.clone();
	}

	public void setPartitionPackPadding(
			long paddingFillSize)
		throws IllegalArgumentException {

		this.bodyPartitionPack.setPaddingFillSize(paddingFillSize);
	}

	public BodyPartition clone() {

		return (BodyPartition) super.clone();
	}
}
