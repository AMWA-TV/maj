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
 * $Log: SourceReferenceValue.java,v $
 * Revision 1.2  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/02/08 11:34:10  vizigoth
 * Consistent referal to zero rather than null/nil mob id and isOriginal/Contextual methods added to the interface.
 *
 * Revision 1.4  2008/01/14 20:52:23  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.3  2008/01/10 17:18:00  vizigoth
 * Minor comment link changes due to refactoring.
 *
 * Revision 1.2  2007/12/14 15:01:49  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:13:00  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.PackageIDType;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.record.PackageID;

/** 
 * <p>Specifies a reference to a clip of source material, by material identifier, track number
 * and start time offset. Source reference values are used to specify 
 * {@linkplain tv.amwa.maj.model.SourceClip source clip segments}.</p>
 * 
 * <p>The interpretation of a source reference value depends on the context where it is used. The value of 
 * a source reference can be explicit or relative to its context. Source references are often used 
 * to build a chain of references that can be used to trace the original source of some material.</p>
 * 
 * <p>Three different kinds of reference can be identified from the value of the source id property of a 
 * source reference. These are defined as:</p>
 * 
 * <ol>
 *  <li>The source package id property is present and has a non-nil value. An explicit reference to a {@linkplain tv.amwa.maj.model.Package package} 
 *  by its {@linkplain PackageID unique package id} and {@linkplain tv.amwa.maj.misctype.TrackID track id}. An optional 
 *  offset starting frame may also be provided.</li>
 *  <li>Source id property is present and is the {@linkplain tv.amwa.maj.industry.Forge#zeroPackageID() zero package id}. This
 *  specifies that the {@linkplain tv.amwa.maj.model.SourcePackage source package} containing the source reference represents 
 *  the original source material. Such references are the final link in source reference chains.</li>
 *  <li>The source package id property is not present. This specifies that the reference is to another 
 *  {@linkplain tv.amwa.maj.model.Track track} within the same package, the package owning source reference itself.</li>
 * </ol>
 * 
 * <p>Original C name: <code>aafSourceRef_t</code></p>
 * 
 * @see tv.amwa.maj.model.SourceReferenceSegment
 * @see PackageID
 * @see tv.amwa.maj.model.SourceClip
 * 
 *
 */

public interface SourceReferenceValue { 
	
	/**
	 * <p>Returns the identifier of the {@linkplain tv.amwa.maj.model.Package package} that is the target of the reference. If the 
	 * property is the {@linkplain tv.amwa.maj.industry.Forge#zeroPackageID() zero package id}, it means that 
	 * the package owning this source reference describes the original source.</p>
	 *
	 * @return Source package id component of the source reference.
	 * 
	 * @throws PropertyNotPresentException The optional source package id property is not present in 
	 * this source reference, implying that the reference is to a track in the same 
	 * {@linkplain tv.amwa.maj.model.Package package} as the reference itself.
	 */
	public @PackageIDType PackageID getSourcePackageID()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the identity of {@linkplain tv.amwa.maj.model.Package package} that is the target of the reference. If the property is the 
	 * {@linkplain tv.amwa.maj.industry.Forge#zeroPackageID() zero package id}, it means that the 
	 * package owning this source reference describes the original source. Set the source package id to <code>null</code> to indicate 
	 * that the reference is to a track in the package owning this reference.</p>
	 *
	 * @param sourcePackageID Source id component of the source reference.
	 */
	public void setSourcePackageID(
			@PackageIDType PackageID sourcePackageID);

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.misctype.TrackID track id} of a 
	 * {@linkplain tv.amwa.maj.model.Track track} within the referenced 
	 * {@linkplain tv.amwa.maj.model.Package package}. If the source package id of this value is the 
	 * {@linkplain tv.amwa.maj.industry.Forge#zeroPackageID() zero package id} then the source track 
	 * value shall also be set to&nbsp;0.</p>
	 *
	 * @return Track id part of the source reference.
	 */
	public @TrackID int getSourceTrackID();

	/**
	 * <p>Sets the {@linkplain tv.amwa.maj.misctype.TrackID track id} of the referenced {@linkplain tv.amwa.maj.model.Track track} 
	 * within the referenced {@linkplain tv.amwa.maj.model.Package package}. If the source package id of this value is the 
	 * {@linkplain tv.amwa.maj.industry.Forge#zeroPackageID() zero package id} 
	 * then the source track value shall also be set to&nbsp;0.</p>
	 *
	 * @param sourceTrackID Track id part of the source reference.
	 * 
	 * @throws IllegalArgumentException Trying to set a non-zero value when the source id is the zero package id or the
	 * track id is negative.
	 */
	public void setSourceTrackID(
			@TrackID int sourceTrackID)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the position offset from the origin of the referenced {@linkplain tv.amwa.maj.model.Package package's} 
	 * {@linkplain tv.amwa.maj.model.Track track} in edit units determined by the 
	 * {@linkplain tv.amwa.maj.model.SourceClip source clip's} 
	 * context. If the source package id of this value is the 
	 * {@linkplain tv.amwa.maj.industry.Forge#zeroPackageID() zero package id}, then the start position shall 
	 * also be set to&nbsp;0.</p>
	 *
	 * @return Position offset from the origin of the referenced content.
	 * 
	 * @throws PropertyNotPresentException The optional start time property is not present,
	 * indicating that the reference is in the context of a {@linkplain tv.amwa.maj.model.StaticTrack static track}
	 * rather than a {@linkplain tv.amwa.maj.model.TimelineTrack timeline} or 
	 * {@linkplain tv.amwa.maj.model.EventTrack event} track.
	 */
	public @PositionType long getStartPosition()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the position offset from the origin of the referenced {@linkplain tv.amwa.maj.model.Package package's} 
	 * {@linkplain tv.amwa.maj.model.Track track} in edit units determined by the 
	 * {@linkplain tv.amwa.maj.model.SourceClip source clip's} 
	 * context. If the source package id of this value is the 
	 * {@linkplain tv.amwa.maj.industry.Forge#zeroPackageID() zero package id} then the start position 
	 * shall be set to&nbsp;0.</p>
	 * 
	 * <p>Set this optional property to <code>null</code> to omit it, indicating 
	 * that this reference is in the context of a {@linkplain tv.amwa.maj.model.StaticTrack static track}
	 * rather than a {@linkplain tv.amwa.maj.model.TimelineTrack timeline} or 
	 * {@linkplain tv.amwa.maj.model.EventTrack event} track.</p>
	 *
	 * @param startPosition Position offset from the origin of the referenced content.
	 * 
	 * @throws IllegalArgumentException Trying to set a non-zero value when the source id is the zero package id.
	 */
	public void setStartPosition(
			@PositionType Long startPosition)
		throws IllegalArgumentException;
	
	/**
	 * <p>Returns <code>true</code> if this source reference is to the original source material and
	 * <code>false</code> if it requires further resolution.</p>
	 *
	 * @return Is this reference to the original source material?
	 * 
	 * @see tv.amwa.maj.industry.Forge#originalSource()
	 */
	public boolean isOriginalSource();
	
	/**
	 * <p>Returns <code>true</code> if this source reference is to another track within the context
	 * of the same {@linkplain tv.amwa.maj.model.Package package}, or <code>false</code> if it is a reference 
	 * to a track in another {@linkplain tv.amwa.maj.model.Package package}.</p>
	 *
	 * @return Does this reference target another track in the context of the same package?
	 * 
	 * @see #isContextual()
	 * @see tv.amwa.maj.industry.Forge#inContextReference(int)
	 * @see tv.amwa.maj.industry.Forge#inContextReference(int, Long)
	 */
	public boolean isContextual();
}
