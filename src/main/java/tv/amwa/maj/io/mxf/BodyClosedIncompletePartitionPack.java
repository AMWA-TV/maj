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
 * <p>Represents the description of {@linkplain BodyPartition body partition} that is closed
 * and incomplete.</p>
 *
 * <p>A closed partition is one in which required header metadata values have been finalized
 * and so all required metadata is present and correct. All closed partitions that contain
 * {@linkplain HeaderMetadata header metadata} shall have identical header metadata.</p>
 *
 * <p>An incomplete partition is one where {@linkplain HeaderMetadata header metadata} exists and
 * some best effort metadata properties have been flagged as unknown, by setting to the appropriate
 * distinguished value.</p>
 *
 *
 *
 * @see HeaderClosedIncompletePartitionPack
 * @see FooterClosedIncompletePartitionPack
 * @see BodyClosedCompletePartitionPack
 * @see BodyOpenCompletePartitionPack
 * @see BodyOpenIncompletePartitionPack
 *
 */
public interface BodyClosedIncompletePartitionPack
	extends
		BodyPartitionPack,
		Cloneable {

	/**
	 * <p>Create a cloned copy of this body closed incomplete partition pack.</p>
	 *
	 * @return Cloned copy of this body closed incomplete partition pack.
	 */
	public BodyClosedIncompletePartitionPack clone();
}
