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

package tv.amwa.maj.io.mxf;

/**
 * <p>Represents the description of a {@linkplain FooterPartition footer partition}, including its
 * size and what it contains.</p>
 *
 *
 *
 * @see BodyPartitionPack
 * @see HeaderPartitionPack
 *
 */
public interface FooterPartitionPack
	extends
		PartitionPack,
		Cloneable {

	/**
	 * <p>Create a cloned copy of this footer partition pack.</p>
	 *
	 * @return Cloned copy of this footer partition pack.
	 */
	public FooterPartitionPack clone();

}
