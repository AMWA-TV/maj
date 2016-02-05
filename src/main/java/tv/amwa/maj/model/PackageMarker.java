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

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.misctype.TrackID;

/**
 * <p>Provides mark-in and mark-out properties on {@linkplain material packages}.</p>
 *
 *
 *
 * @see MaterialPackage#getPackageMarker()
 */
public interface PackageMarker
	extends InterchangeObject {

	/**
	 * <p>Returns the track identifier of the target {@linkplain Track track} in the
	 * {@linkplain MaterialPackage material package} that provides the edit rate.</p>
	 *
	 * @return Track identifier of the track that provides the edit rate.
	 */
	public @TrackID int getTimebaseReferenceTrackID();

	/**
	 * <p>Sets the track identifier of the target {@linkplain Track track} in the
	 * {@linkplain MaterialPackage material package} that provides the edit rate.</p>
	 *
	 * @param timebaseReferenceTrackID Track identifier of the track that provides the edit rate.
	 *
	 * @throws IllegalArgumentException Cannot set the track identifeir to a negative
	 * value or to the identifier of a non-timeline track.
	 */
	public void setTimebaseReferenceTrackID(
			@TrackID int timebaseReferenceTrackID)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the start of the optional sub-section on the material package's timebase
	 * reference track timeline. This is an optional property that, when omitted, indicates
	 * that the all tracks are to be played from the zero point.</p>
	 *
	 * @return Start of the optional sub-section.
	 *
	 * @throws PropertyNotPresentException The optional package mark-in position property
	 * is not present for this package marker.
	 */
	public @PositionType long getPackageMarkInPosition()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the start of the optional sub-section on the material package's timebase
	 * reference track timeline. Set this optional property to <code>null</code> to omit
	 * it, indicating that the all tracks are to be played from the zero point.</p>
	 *
	 * @param packageMarkInPosition Start of the optional sub-section.
	 */
	public void setPackageMarkInPosition(
			@PositionType Long packageMarkInPosition);

	/**
	 * <p>Returns the stop of the optional sub-section on the material package's timebase
	 * reference track timeline. This is an optional property that, when omitted, indicates
	 * that the stop point is at package duration.</p>
	 *
	 * @return Stop of the optional sub-section.
	 *
	 * @throws PropertyNotPresentException The optional package mark-out position property
	 * is not present for this package marker.
	 */
	public @PositionType long getPackageMarkOutPosition()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the stop of the optional sub-section on the material package's timebase
	 * reference track timeline. Set this optional property to <code>null</code> to omit
	 * it, indicating that the stop point is at package duration.</p>
	 *
	 * @param packageMarkOutPosition Stop of the optional sub-section.
	 */
	public void setPackageMarkOutPosition(
			@PositionType Long packageMarkOutPosition);

	/**
	 * <p>Create a cloned copy of this package marker.</p>
	 *
	 * @return Cloned copy of this package marker.
	 */
	public PackageMarker clone();
}
