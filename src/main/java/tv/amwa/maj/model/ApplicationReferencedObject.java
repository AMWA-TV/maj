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

package tv.amwa.maj.model;

import tv.amwa.maj.record.AUID;


/**
 * <p>Representation of any object connected by strong reference to an
 * {@linkplain ApplicationPluginObject application plugin object}, whether
 * directly or indirectly.</p>
 *
 *
 *
 */
public interface ApplicationReferencedObject
	extends ApplicationObject {

	/**
	 * <p>Return the identifier of the {@linkplain ApplicationPluginObject application plugin object}
	 * that (directly or indirectly) strongly references this application metadata
	 * referenced object.</p>
	 *
	 * @return Identifier of the application plugin object that strongly references this.
	 *
	 * @see ApplicationPluginObject#getApplicationPluginInstanceID()
	 */
	public AUID getLinkedApplicationPluginInstanceID();

	/**
	 * <p>Sets the identifier of the {@linkplain ApplicationPluginObject application plugin object}
	 * that (directly or indirectly) strongly references this application metadata
	 * referenced object.</p>
	 *
	 * @param linkedApplicationPluginInstanceID Identifier of the application plugin object that strongly
	 * references this.
	 *
	 * @throws NullPointerException Cannot set the linked application plugin instance
	 * using a <code>null</code> value.
	 *
	 * @see #setLinkedApplicationPluginInstanceID(ApplicationPluginObject)
	 * @see ApplicationPluginObject#getApplicationPluginInstanceID()
	 */
	public void setLinkedApplicationPluginInstanceID(
			AUID linkedApplicationPluginInstanceID)
		throws NullPointerException;

	/**
	 * <p>Sets the identifier of the {@linkplain ApplicationPluginObject application plugin object}
	 * that (directly or indirectly) strongly references this application metadata
	 * referenced object using the source of the reference.</p>
	 *
	 * @param linkedApplicationPluginInstanceID Application plugin object that strongly
	 * references this.
	 * @throws NullPointerException Cannot set the linked application plugin instance
	 * using a <code>null</code> value.
	 *
	 * @see #setLinkedApplicationPluginInstanceID(AUID)
	 * @see ApplicationPluginObject#getApplicationPluginInstanceID()
	 */
	public void setLinkedApplicationPluginInstanceID(
			ApplicationPluginObject linkedApplicationPluginInstanceID)
		throws NullPointerException;

	/**
	 * <p>Create a cloned copy of this application referenced object.</p>
	 *
	 * @return Cloned copy of this application referenced object.
	 */
	public ApplicationReferencedObject clone();
}
