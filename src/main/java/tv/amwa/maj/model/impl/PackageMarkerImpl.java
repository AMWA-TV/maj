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

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.model.PackageMarker;

@MediaClass(uuid1 =0x0d010101 , uuid2 = (short) 0x0101, uuid3 = (short) 0x6000,
		uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01 },
		definedName = "PackageMarker",
		aliases = { "PackageMarkerObject" },
		symbol = "PackageMarker",
		description = "Provides mark-in and mark-out properties on material packages.")
public class PackageMarkerImpl
	extends
		InterchangeObjectImpl
	implements
		PackageMarker,
		Cloneable,
		Serializable {

	private static final long serialVersionUID = 4831780255667423820L;

	private @TrackID int timecaseReferenceTrackID;
	private @PositionType Long packageMarkInPosition = null;
	private @PositionType Long packageMarkOutPosition = null;

	public PackageMarkerImpl() { }

	@MediaProperty(uuid1 = 0x06010103, uuid2 = (short) 0x0E00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C},
			definedName = "TimebaseReferenceTrackID",
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "TimebaseReferenceTrackID",
			description = "Track identifier of the target track in the material package that provides the edit rate.")
	public @TrackID int getTimebaseReferenceTrackID() {

		return timecaseReferenceTrackID;
	}

	@MediaPropertySetter("TimebaseReferenceTrackID")
	public void setTimebaseReferenceTrackID(
			@TrackID int timebaseReferenceTrackID)
		throws IllegalArgumentException {

		if (timebaseReferenceTrackID < 0)
			throw new IllegalArgumentException("Cannot set the timebase referenced track identifeir to a negative value.");

		this.timecaseReferenceTrackID = timebaseReferenceTrackID;
	}

	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x010e, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0A },
			definedName = "PackageMarkInPosition",
			typeName = "PositionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "PackageMarkInPosition",
			description = "Start of the optional sub-section on the material package's timebase reference track timeline.")
	public @PositionType long getPackageMarkInPosition()
		throws PropertyNotPresentException {

		if (packageMarkInPosition == null)
			throw new PropertyNotPresentException("The optional package mark in position property is not present for this package marker.");

		return packageMarkInPosition;
	}

	@MediaPropertySetter("PackageMarkInPosition")
	public void setPackageMarkInPosition(
			@PositionType Long packageMarkInPosition) {

		this.packageMarkInPosition = packageMarkInPosition;
	}

	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x0204, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0A },
			definedName = "PackageMarkOutPosition",
			typeName = "PositionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "PackageMarkOutPosition",
			description = "Stop of the optional sub-section on the material package's timebase reference track timeline.")
	public @PositionType long getPackageMarkOutPosition()
		throws PropertyNotPresentException {

		if (packageMarkOutPosition == null)
			throw new PropertyNotPresentException("The optional package mark out position property is not present for this package marker.");

		return packageMarkOutPosition;
	}

	@MediaPropertySetter("PackageMarkOutPosition")
	public void setPackageMarkOutPosition(
			@PositionType Long packageMarkOutPosition) {

		this.packageMarkOutPosition = packageMarkOutPosition;
	}

	public PackageMarker clone() {

		return (PackageMarker) super.clone();
	}
}
