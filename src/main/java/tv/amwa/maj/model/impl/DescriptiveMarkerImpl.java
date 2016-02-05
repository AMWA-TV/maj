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

/*
 * $Log: DescriptiveMarkerImpl.java,v $
 * Revision 1.4  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.3  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:55:57  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2008/01/27 11:14:40  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.1  2007/11/13 22:09:53  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:17  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.model.DescriptiveFramework;
import tv.amwa.maj.model.DescriptiveMarker;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements descriptive metadata associated with a point in time and the 
 * {@linkplain tv.amwa.maj.model.Track tracks} that the description refers to. A 
 * descriptive marker may contain a {@linkplain tv.amwa.maj.model.DescriptiveFramework descriptive framework} 
 * that specifies the metadata.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x4100,
		uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		definedName = "DescriptiveMarker",
		description = "The DescriptiveMarker class specifies descriptive metadata that is associated with a point in time.",
		symbol = "DescriptiveMarker")
public class DescriptiveMarkerImpl
	extends CommentMarkerImpl
	implements DescriptiveMarker,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -3140208650608144638L;

	private static final Set<Integer> dataBaseAllTracksDescribed = new HashSet<Integer>();

	private Set<Integer> describedTrackIDs = Collections.synchronizedSet(new HashSet<Integer>());
	private DescriptiveFramework descriptiveFrameworkObject = null;

	static {
		dataBaseAllTracksDescribed.add(-1);
	}

	/**
	 * <p>Creates and initializes a descriptive marker. If this event is in an 
	 * {@link EventTrackImpl event track}, the position must also be set using 
	 * {@link EventImpl#setEventPosition(long)}.</p>
	 */
	public DescriptiveMarkerImpl() { 

		setComponentDataDefinition(DataDefinitionImpl.forName("DescriptiveMetadata"));
		setPositionPresent(true);
	}

	@MediaProperty(uuid1 = 0x01070105, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04},
			definedName = "DescribedTrackIDs",
			aliases = { "DescribedSlots", "DescribedSlotIDs", "DescribedTracks"},
			typeName = "UInt32Set",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x6102,
			symbol = "DescribedTrackIDs")
	public Set<Integer> getDescribedTrackIDs()
			throws PropertyNotPresentException {

		if (describedTrackIDs == null)
			throw new PropertyNotPresentException("The optional described tracks property is not present in this descriptive marker.");

		if (describedTrackIDs == AllTracksDescribed) return describedTrackIDs;
		else return new HashSet<Integer>(describedTrackIDs);
	}

	@MediaPropertySetter("DescribedTrackIDs")
	public void setDescribedTrackIDs(
			Set<Integer> describedTrackIDs) 
		throws IllegalArgumentException {

		if (describedTrackIDs == null) {
			this.describedTrackIDs = null;
			return;
		}

		if (describedTrackIDs == AllTracksDescribed) {
			this.describedTrackIDs = AllTracksDescribed;
			return;
		}

		this.describedTrackIDs = Collections.synchronizedSet(new HashSet<Integer>());
		for ( int trackID : describedTrackIDs ) {
			if (trackID >= 0) 
				this.describedTrackIDs.add(trackID);
			else
				throw new IllegalArgumentException("A track id of " + trackID + " cannot be used as it is negative.");
		}
	}

	@MediaSetAdd("DescribedTrackIDs")
	public void addDescribedTrackID(
			@UInt32 int trackID) 
		throws IllegalArgumentException {
		
		if (trackID < 0)
			throw new IllegalArgumentException("The track identifier cannot be negative.");
		
		if (describedTrackIDs == null) {
			describedTrackIDs = Collections.synchronizedSet(new HashSet<Integer>());
		}
		
		if (describedTrackIDs.equals(AllTracksDescribed))
			return;
			
		describedTrackIDs.add(trackID);
	}
	
	public int getDescribedTrackIDsSize() 
		throws PropertyNotPresentException {

		if (describedTrackIDs == null)
			throw new PropertyNotPresentException("The optional described tracks property is not present in this descriptive marker.");

		return describedTrackIDs.size();
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x020c, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "DescriptiveFrameworkObject",
			aliases = { "Description", "DescriptiveMarkerDescription" },
			typeName = "DescriptiveFrameworkStrongReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x6101,
			symbol = "DescriptiveFrameworkObject")
	public DescriptiveFramework getDescriptiveFrameworkObject() 
		throws PropertyNotPresentException {

		if (descriptiveFrameworkObject == null)
			throw new PropertyNotPresentException("The optional descriptive framework property is not present in this descriptive marker.");

		return descriptiveFrameworkObject;
	}

	@MediaPropertySetter("DescriptiveFrameworkObject")
	public void setDescriptiveFrameworkObject(
			tv.amwa.maj.model.DescriptiveFramework descriptiveFrameworkObject) { 

		if (descriptiveFrameworkObject == null) {
			this.descriptiveFrameworkObject = null;
			return;
		}

		this.descriptiveFrameworkObject = descriptiveFrameworkObject;
	}

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x0e00, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C },
			definedName = "DescriptiveMetadataPluginID",
			aliases = { "DescriptiveMetadataPlug-InID" },
			typeName = "AUID",
			optional = true, 
			uniqueIdentifier = false, // TODO it could be considered that this should be true ... but for an optional property?
			pid = 0,
			symbol = "DescriptiveMetadataPluginID",
			description = "Immutable identifier for this descriptive metadata plugin instance.")
	public AUID getDescriptiveMetadataPluginID()
			throws PropertyNotPresentException {
		// TODO Auto-generated method stub
		return null;
	}

	@MediaPropertySetter("DescriptiveMetadataPluginID")
	public void setDescriptiveMetadataPluginID(
			AUID descriptiveMetadataPluginID) {
		// TODO Auto-generated method stub
		
	}

	@MediaProperty(uuid1 = 0x05200701, uuid2 = (short) 0x1000, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C},
			definedName = "DescriptiveMetadataApplicationEnvironmentID",
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "DescriptiveMetadataApplicationEnvironmentID",
			description = "Application environment identifier, an Uniform " + 
					"Resource Identifier (RFC 3986) that identifies the application to which the information in this plugin " + 
					"object applies.")
	public String getDescriptiveMetadataApplicationEnvironmentID()
		throws PropertyNotPresentException {
	
		// TODO Auto-generated method stub
		return null;
	}

	@MediaPropertySetter("DescriptiveMetadataApplicationEnvironmentID")
	public void setDescriptiveMetadataApplicationEnvironmentID(
			String descriptiveMetadataApplicationEnvironmentID) {
		// TODO Auto-generated method stub
		
	}

	@MediaProperty(uuid1 = 0x04060804, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0C },
			definedName = "DescriptiveMetadataScheme",
			typeName = "ExtensionSchemeWeakReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0,
			symbol = "DescriptiveMetadataScheme",
			description = "Descriptive metadata scheme that is referenced by the descriptive framework object.")
	public ExtensionScheme getDescriptiveMetadataScheme()
		throws PropertyNotPresentException {
		
		// TODO Auto-generated method stub
		return null;
	}

	@MediaPropertySetter("DescriptiveMetadataScheme")
	public void setDescriptiveMetadataScheme(
			ExtensionScheme extensionScheme) {
		// TODO Auto-generated method stub
		
	}
	
	public DescriptiveMarker clone() {
		
		return (DescriptiveMarker) super.clone();
	}

}
