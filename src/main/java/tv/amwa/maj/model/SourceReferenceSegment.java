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
 * $Log: SourceReferenceSegment.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/02/28 12:50:36  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2008/02/08 11:27:21  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:43  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt32Array;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.union.SourceReferenceValue;


/**
 * <p>Specifies the representation of essence or other data described 
 * by a {@linkplain Track track} in a {@linkplain Package package}.</p>
 * 
 * <p>To reference a single channel of a multi-channel track from a mono 
 * track, the {@linkplain #getChannelIDs() channel ids} property is used with 
 * a single element in the array. To reference multiple channels of a 
 * multi-channel track from a multi-channel track, the 
 * {@linkplain #getChannelIDs() channel ids} property is used with multiple 
 * elements in the array.</p>
 * 
 * <p>To reference multiple mono tracks from a multi-channel track, the 
 * {@linkplain #getMonoSourceTrackIDs() mono source track ids} property is used 
 * with multiple elements in the array.</p>
 * 
 * <p>See the <a href="package-summary.html#namingConflicts">section on naming conflicts in the package 
 * documentation</a>.</p>
 * 
 *
 *
 * @see tv.amwa.maj.union.SourceReferenceValue
 * @see tv.amwa.maj.industry.TypeDefinitions#SourceReferenceStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#SourceReferenceStrongReferenceVector
 */

public interface SourceReferenceSegment
	extends Segment {

	/**
	 * <p>Returns the package id of the {@linkplain Package package} that is the
	 * target of this reference. This is an optional property.</p>
	 * 
	 * <p>If the source package id property is the {@linkplain tv.amwa.maj.record.PackageID#isZero() zero
	 * package id}, the {@linkplain Package package} owning the source reference describes the original source. If this 
	 * property is omitted, the reference is to another track within the same package.</p>
	 * 
	 * @return Source package id that is the target of this source reference.
	 * 
	 * @throws PropertyNotPresentException The optional source package id property is not present 
	 * in this source reference, implying that this is a reference to a track within the 
	 * same package.
	 * 
	 * @see tv.amwa.maj.union.SourceReferenceValue#getSourcePackageID()
	 */
	public PackageID getSourcePackageID()
		throws PropertyNotPresentException;

	/**
	 * <p>Set the source package id of this source reference, which identifies the {@linkplain Package package} that
	 * is the target of this reference.</p>
	 * 
	 * <p>If the property has a value of the {@linkplain tv.amwa.maj.record.PackageID#isZero() 
	 * zero pcakage id}, it means that the {@linkplain Package package} owning this source reference describes the original 
	 * source. Set this optional property to <code>null</code> to omit and indicate that this
	 * reference is to another {@linkplain Track track} in the same {@linkplain Package package}.</p>
	 * 
	 * <p>For {@linkplain SourceClip source clips}, it is recommended that the 
	 * {@link SourceClip#setSourceReference(SourceReferenceValue) setSourceReference()} method
	 * is used to set the source package id property in preference to this method.</p>
	 * 
	 * @param sourcePackageID Source package id of this source reference.
	 * 
	 * @see tv.amwa.maj.union.SourceReferenceValue#setSourcePackageID(PackageID)
	 * @see tv.amwa.maj.industry.Forge#zeroPackageID()
	 * @see SourceClip#setSourceReference(SourceReferenceValue)
	 */
	public void setSourcePackageID(
			PackageID sourcePackageID);

	/**
	 * <p>Returns the track id of this source reference. If the {@linkplain #getSourcePackageID()
	 * source package id} property is the the {@linkplain tv.amwa.maj.record.PackageID#isZero() package package id},
	 * the source track id should be&nbsp;0.</p>
	 * 
	 * @return Track id of this source reference.
	 * 
	 * @see tv.amwa.maj.union.SourceReferenceValue#getSourceTrackID()
	 * @see tv.amwa.maj.misctype.TrackID
	 */
	public @TrackID int getSourceTrackID();

	/**
	 * <p>Sets the track id of this source reference. If the {@linkplain #getSourcePackageID()
	 * source package id} property is the the {@linkplain tv.amwa.maj.record.PackageID#isZero() zero package id},
	 * the source track id should be&nbsp;0.</p>
	 * 
	 * @param trackID Track id of this source reference.
	 * 
	 * @throws IllegalArgumentException The given source track id is negative.
	 * 
	 * @see tv.amwa.maj.union.SourceReferenceValue#setSourceTrackID(int)
	 */
	public void setSourceTrackID(
			@TrackID int trackID)
		throws IllegalArgumentException;

	// TODO need to find some examples of this to tidy up comment and implementation
	
	/**
	 * <p>For references to a multi-channel {@linkplain Track track}, sets the
	 * channel ids property of this source reference, which specifies the channels 
	 * within that {@linkplain Track tracks} that are referenced. Set this
	 * optional property to <code>null</code> to omit it and indicate that all channels in the
	 * referenced track are referenced in the same order.</p>
	 * 
	 * <p>The number of channel ids shall equal the number of 
	 * channels being described the {@link Track track} containing
	 * the source reference, e.g. 1&nbsp;element for a mono audio track, 
	 * 6&nbsp;elements for a 5.1&nbsp;multi-channel audio track.</p>
	 * 
	 * @param channelIDs Array of channel IDs to set for the source 
	 * reference.
	 */
	public void setChannelIDs(
			@UInt32Array int[] channelIDs);

	/**
	 * <p>For references to a multi-channel {@linkplain Track track}, returns the
	 * channel ids property of this source reference, which specifies the channels 
	 * within that {@linkplain Track tracks} that are referenced. This optional
	 * property is omitted to indicate that all channels in the
	 * referenced track are referenced in the same order.</p>
	 * 
	 * <p>The number of channel ids shall equal the number of 
	 * channels being described the {@linkplain Track track} containing
	 * the source reference, e.g. 1&nbsp;element for a mono audio track, 
	 * 6&nbsp;elements for a 5.1&nbsp;multi-channel audio track.</p>
	 * 
	 * @return Array of channel ids for this source reference.
	 * 
	 * @throws PropertyNotPresentException The optional channel ids property is
	 * not present in this source reference.
	 */
	public @UInt32Array int[] getChannelIDs()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of elements of the channel ids array of
	 * this source reference. For references to a multi-channel {@linkplain Track track}, the
	 * channel ids property specifies the channels 
	 * within the {@linkplain Track tracks} that are referenced. This is 
	 * an optional property.</p>
	 * 
	 * @return Number of elements of the channel ids array of the
	 * source reference.
	 * 
	 * @throws PropertyNotPresentException The optional channel ids property is
	 * not present in this source reference.
	 */
	public @UInt32 int getChannelIDsSize()
		throws PropertyNotPresentException;

	/**
	 * <p>For references from a multi-channel {@linkplain Track track} to 
	 * multiple mono {@linkplain Track tracks}, sets the mono source
	 * track ids property of this source reference, which specifies multiple mono 
	 * {@linkplain Track tracks} that are referenced. Set this optional
	 * property to <code>null</code> to omit it and specify that this is 
	 * a single-channel reference.</p>
	 * 
	 * <p>The number of elements in the 
	 * mono source track ids array shall equal the number of channels being 
	 * described by the {@linkplain Track track} containing this 
	 * source reference, e.g. 6&nbsp;elements for a&nbsp;5.1 multi-channel audio 
	 * track.</p>
	 * 
	 * @param monoSourceTrackIDs Array of mono source track ids for this
	 * source reference.
	 */
	public void setMonoSourceTrackIDs(
			@UInt32 int[] monoSourceTrackIDs);

	/**
	 * <p>For references from a multi-channel {@linkplain Track track} to 
	 * multiple mono {@linkplain Track tracks}, returns the mono source
	 * track ids property of this source reference, which specifies multiple mono 
	 * {@linkplain Track tracks} that are referenced. This is an optional
	 * property that is omitted to specify that this is a single-channel reference.</p>
	 * 
	 * <p>The number of elements in the 
	 * mono source track ids array shall equal the number of channels being 
	 * described by the {@linkplain Track track} containing this 
	 * source reference, e.g. 6&nbsp;elements for a&nbsp;5.1 multi-channel audio 
	 * track.</p>
	 * 
	 * @return Array of mono source track ids of this source reference.
	 * 
	 * @throws PropertyNotPresentException This optional mono source track ids property is
	 * not present in this source reference.
	 */
	public @UInt32 int[] getMonoSourceTrackIDs()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the size of the mono source track ids array. For references from a multi-channel 
	 * {@linkplain Track track} to multiple mono {@linkplain Track tracks}, the mono source
	 * track ids property specifies multiple mono {@linkplain Track tracks} that are referenced. 
	 * This is an optional property.</p>
	 * 
	 * @return Size of the mono source track ids array.
	 * 
	 * @throws PropertyNotPresentException The optional mono source track ids property is not
	 * present for this source reference.
	 */
	public @UInt32 int getMonoSourceTrackIDsSize()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this source reference segment.</p>
	 *
	 * @return Cloned copy of this source reference segment.
	 */
	public SourceReferenceSegment clone();
}
