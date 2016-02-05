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
 * <p>Represents the description of {@linkplain BodyPartition body partition} that is open
 * and incomplete.</p>
 *
 * <p>An open partition is one in which required {@linkplain HeaderMetadata header metadata} values
 * have not been finalized and required values may be absent or incorrect. The header partition may not
 * have the same values as the closed partition(s) and header metadata in open partitions may change
 * in later repetitions.</p>
 *
 * <p>An incomplete partition is one where {@linkplain HeaderMetadata header metadata} exists and
 * some best effort metadata properties have been flagged as unknown, by setting to the appropriate
 * distinguished value.</p>
 *
 *
 *
 * @see HeaderOpenIncompletePartitionPack
 * @see BodyClosedIncompletePartitionPack
 * @see BodyOpenCompletePartitionPack
 * @see BodyClosedCompletePartitionPack
 */
public interface BodyOpenIncompletePartitionPack
	extends
		BodyPartitionPack,
		Cloneable {

	/**
	 * <p>Create a cloned copy of this body open incomplete partition pack.</p>
	 *
	 * @return Cloned copy of this body open incomplete partition pack.
	 */
	public BodyOpenIncompletePartitionPack clone();

}
