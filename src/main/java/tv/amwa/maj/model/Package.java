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
 * $Log: Package.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/02/10 23:43:22  vizigoth
 * Improvements to create and mod time method names to match meta dictionary.
 *
 * Revision 1.2  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.6  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.5  2008/02/28 12:50:35  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.4  2008/02/08 11:27:19  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.3  2008/01/14 21:05:37  vizigoth
 * Minor change due to refactoring of Depend enumeration.
 *
 * Revision 1.2  2007/12/04 13:04:50  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:09  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.enumeration.Depend;
import tv.amwa.maj.enumeration.IncludedMedia;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.exception.TrackExistsException;
import tv.amwa.maj.exception.TrackNotFoundException;
import tv.amwa.maj.exception.TimecodeNotFoundException;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.PackageIDType;
import tv.amwa.maj.misctype.NumTracks;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.TimeStamp;
import tv.amwa.maj.record.TimecodeValue;
import tv.amwa.maj.record.VersionType;



/**
 * <p>Specifies a package, which can describe a composition, essence, or 
 * physical media. A package has a unique identifier and consists of metadata.</p>
 * 
 *
 *
 * @see ContentStorage#getPackages()
 * @see EssenceData#getFilePackage()
 * @see tv.amwa.maj.record.PackageID
 * @see tv.amwa.maj.industry.TypeDefinitions#PackageWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PackageStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PackageStrongReferenceSet
 */

public interface Package 
	extends InterchangeObject, 
	WeakReferenceTarget {

	/**
	 * <p>Returns the unique ({@linkplain tv.amwa.maj.record.PackageID package identifier}) 
	 * for this package.</p>
	 * 
	 * @return Unique package id that identifies this package.
	 * 
	 */
	public @PackageIDType PackageID getPackageID();

	/**
	 * <p>Sets the unique ({@linkplain tv.amwa.maj.record.PackageID package identifier})
	 * for this package.</p>
	 * 
	 * @param packageId New package identifier to set for this package.
	 * 
	 * @throws NullPointerException The given package identifier is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.industry.Forge#makePackageID(byte[], byte, byte, byte, byte, AUID)
	 */
	public void setPackageID(
			@PackageIDType PackageID packageId) 
		throws NullPointerException;

	/**
	 * <p>Sets the name of this package, as displayed to the end user. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 * 
	 * @param name Name of this package.
	 */
	public void setPackageName(
			@AAFString String name);

	/**
	 * <p>Returns the name of this package, as displayed to the end user. This is an optional
	 * property.</p>
	 * 
	 * @return Name of this package.
	 * 
	 * @throws PropertyNotPresentException The optional name property is not present in
	 * this package.
	 */
	public @AAFString String getPackageName()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of {@linkplain Track tracks} contained in this package.</p>
	 * 
	 * @return Number of tracks contained by this package.
	 */
	public @NumTracks int countPackageTracks();

	/**
	 * <p>Appends the given {@linkplain Track tracks} to the list contained in
	 * this package. Note that the index of a package track in the list of package tracks is not 
	 * the same as its {@linkplain tv.amwa.maj.misctype.TrackID track id}.</p>
	 * 
	 * @param packageTrack Track to append to the list of tracks described by this package.
	 * 
	 * @throws NullPointerException The given track is <code>null</code>.
	 * @throws TrackExistsException A track with the same track id as the given track
	 * is already present in this package.
	 */
	public void appendPackageTrack(
			Track packageTrack) 
		throws NullPointerException,
			TrackExistsException;

	/**
	 * <p>Prepends the given {@linkplain Track track} to the list of tracks
	 * contained in this package. Note that the index of a package track in the list of tracks is not 
	 * the same as its {@linkplain tv.amwa.maj.misctype.TrackID track id}.</p>
	 * 
	 * @param packageTrack Track to prepend to the list of tracks described by this package.
	 * 
	 * @throws NullPointerException The given track is <code>null</code>.
	 * @throws TrackExistsException A track with the same track id as the given track
	 * is already present in this package.
	 */
	public void prependPackageTrack(
			Track packageTrack) 
		throws NullPointerException,
			TrackExistsException;

	/**
	 * <p>Inserts the given {@linkplain Track track} into the list of tracks 
	 * contained in this package at the given index. All existing tracks at the 
	 * given and higher index will be moved up by one index to accommodate
	 * the new track. Note that the index of a track in the list of tracks is not 
	 * the same as its {@linkplain tv.amwa.maj.misctype.TrackID track id}.</p>
	 * 
	 * @param index Index where the track is to be inserted.
	 * @param packageTrack Track to be inserted into the list contained in this package.
	 * 
	 * @throws NullPointerException The given track is <code>null</code>.
	 * @throws IndexOutOfBoundsException Index is outside the acceptable range 
	 * for the list of tracks contained in this package.
	 * @throws TrackExistsException A track with the same track id as the given track
	 * is already present in this package. 
	 */
	public void insertPackageTrackAt(
			@UInt32 int index,
			Track packageTrack) 
		throws NullPointerException,
			IndexOutOfBoundsException,
			TrackExistsException;

	/**
	 * <p>Removes the {@linkplain Track track} at the given index from the list
	 * of tracks contained in this package.  All existing tracks at indices higher than the given 
	 * index will be moved on one index to accommodate.</p>
	 * 
	 * @param index Index of the track to be removed from the list of tracks of this package.
	 * 
	 * @throws IndexOutOfBoundsException Index is outside the acceptable range  
	 * for the list of tracks contained in this package.
	 */
	public void removePackageTrackAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the {@linkplain Track track} at the given index through the
	 * list of tracks contained in this package.</p>
	 * 
	 * @param index Index of track to be returned.
	 * @return Track at the specified index through the list of this package.
	 * 
	 * @throws IndexOutOfBoundsException Index is outside the acceptable range  
	 * for the list of tracks contained in this package.
	 */
	public Track getPackageTrackAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Return the list of {@linkplain Track tracks} contained in this
	 * package.</p>
	 * 
	 * @return Shallow copy of the list of tracks contained in this package.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#TrackStrongReferenceVector
	 */
	public List<? extends Track> getPackageTracks();

	/**
	 * <p>Returns the modification time for this package, which is the date and time when this 
	 * package was last modified.</p>
	 * 
	 * @return Modification time for this package.
	 */
	public TimeStamp getPackageLastModified();

	/**
	 * <p>Sets the modification time for this package, which is the date and time when this 
	 * package was last modified. The modification time is initially set to the time that this package
	 * was created.</p>
	 * 
	 * <p>The MAJ API does not maintain the modification time every time that a 
	 * package is updated. Therefore, this method should be called explicitly to change the
	 * modification time for this package.</p>
	 * 
	 * @param lastModified Modification time to set for this package.
	 * 
	 * @throws NullPointerException The given modification time is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.industry.Forge#now()
	 * @see tv.amwa.maj.industry.Forge#makeTimeStamp(java.util.Calendar)
	 */
	public void setPackageLastModified(
			TimeStamp lastModified)
		throws NullPointerException;

	/**
	 * <p>Returns the creation time of this package, which is the date and time when this package 
	 * was originally created.</p>
	 * 
	 * @return Creation time for this package.
	 */
	public TimeStamp getCreationTime();

	/**
	 * <p>Sets the creation time of this package, which is the date and time when this package 
	 * was originally created. The creation time is initially set to the time that this package
	 * was created. Therefore, this method should only be called to explicitly to change the 
	 * creation time.</p>
	 * 
	 * @param creationTime The creation time for this package.
	 * 
	 * @throws NullPointerException The given creation time is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.industry.Forge#now()
	 * @see tv.amwa.maj.industry.Forge#makeTimeStamp(java.util.Calendar)
	 */
	public void setCreationTime(
			TimeStamp creationTime)
		throws NullPointerException;

	/**
	 * <p>Creates a user-defined comment and adds it to the collection of comments
	 * of this package, which specify user comments that are directly classified
	 * and set up by the operator (for example Bin columns).</p>
	 * 
	 * <p>This method creates a new {@linkplain TaggedValue tagged value}, initializes it with the 
	 * specified comment name/value pair, and appends it to the comment collection.</p>
	 * 
	 * @param category The name associated with the new comment to create.
	 * @param comment The corresponding value, or description, of the new comment.
	 * 
	 * @throws NullPointerException One or both of the name and/or value values is/are <code>null</code>.
	 * 
	 */
	public void appendPackageUserComment(
			@AAFString String category,
			@AAFString String comment) 
		throws NullPointerException;

	/**
	 * <p>Append a user comment defined by an existing {@linkplain TaggedValue tagged value}
	 * to the collection of user comments of this package, which specify user comments that are 
	 * directly classified and set up by the operator (for example Bin columns).</p>
	 * 
	 * @param packageUserComment User comment to append to the collection of user comments of this component.
	 * 
	 * @throws NullPointerException The given user comment value is <code>null</code>.
	 * 
	 * @see TaggedValueDefinition
	 */
	public void appendPackageUserComment(
			TaggedValue packageUserComment)
		throws NullPointerException;
	
	/**
	 * <p>Returns the total number of user comments attached to this 
	 * package, which specify user comments that are 
	 * directly classified and set up by the operator (for example Bin columns).</p>
	 * 
	 * @return Total number of comments attached to this package.
	 */
	public @UInt32 int countPackageUserComments();
	
	/**
	 * <p>Clears the list of user comments attached to this package, omitting this
	 * optional property.</p>
	 */
	public void clearPackageUserComments();

	/**
	 * <p>Clears the list of package tracks attached to this package.</p>
	 */
	public void clearPackageTracks();
	/**
	 * <p>Returns the collection of all user comments attached to this package, which specify user comments that are 
	 * directly classified and set up by the operator (for example Bin columns). This is an optional
	 * property.</p>
	 * 
	 * @return Shallow copy of the collection of all comments attached to this package.
	 * 
	 * @throws PropertyNotPresentException No optional user comments are present for this package.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueStrongReferenceVector
	 */
	public List<? extends TaggedValue> getPackageUserComments()
		throws PropertyNotPresentException;

	/**
	 * <p>Removes the given user comment from the collection of user comments of
	 * this package, which specify user comments that are 
	 * directly classified and set up by the operator (for example Bin columns).</p>
	 * 
	 * @param packageUserComment User comment to remove from the collection of this package.
	 * 
	 * @throws NullPointerException The given tagged value user comment is <code>null</code>.
	 * @throws PropertyNotPresentException No user comments are present for this package.
	 * @throws ObjectNotFoundException The given comment is not in the collection of user comments
	 * of this package.
	 */
	public void removePackageUserComment(
			TaggedValue packageUserComment) 
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException;

	/**
	 * <p>Creates and returns a new {@linkplain TimelineTrack timeline track} 
	 * with the given property values, as well as appending it to this package.</p>
	 * 
	 * @param editRate Units of time for the new timeline track.
	 * @param segment Value for the new timeline track.
	 * @param trackId Integer to be used to refer to the track.
	 * @param trackName Name of the new timeline track, or <code>null</code> to
	 * omit this optional property.
	 * @param origin Specifies the offset used to resolve {@linkplain SourceClip
	 * source clip} references to source material in the new timeline track, measured in
	 * according to the edit rate of the new track. A 
	 * positive value means that the first sample of the essence is earlier than the zero 
	 * position. A negative value of origin means that the zero position is 
	 * earlier than the first sample of the essence.
	 * 
	 * @return The newly created timeline tracl, which is also appended to
	 * the list of tracks of this package.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>.
	 * @throws IllegalArgumentException The given track id is negative.
	 * @throws TrackExistsException The given track id is already used to to identify a track
	 * in this package.
	 */
	public TimelineTrack appendNewTimelineTrack(
			Rational editRate,
			Segment segment,
			@TrackID int trackId,
			@AAFString String trackName,
			@PositionType long origin) 
		throws NullPointerException,
			IllegalArgumentException,
			TrackExistsException;

	/**
	 * <p>Calculates the {@linkplain tv.amwa.maj.record.TimecodeValue 
	 * timecode} at the given offset into the given {@linkplain Segment timecode segment}. 
	 * If the given timecode segment is <code>null</code>, the method will search for the track 
	 * containing a timecode segment in this package and will use that instead.</p>
	 * 
	 * @param timecodeSegment Timecode segment to search for, or <code>null</code> to select
	 * the one from this package.
	 * @param offset Offset into the segment, expressed in edit unit's for the
	 * associated track.
	 * @return Associated timecode for the given offset.
	 * 
	 * @throws NullPointerException The given timecode segment is <code>null</code>.
	 * @throws TimecodeNotFoundException A timecode track was not found in this
	 * package.
	 */
	public TimecodeValue offsetToPackageTimecode(
			Segment timecodeSegment,
			@PositionType long offset) 
		throws NullPointerException,
			TimecodeNotFoundException;

	/**
	 * <p>Finds and returns the {@linkplain Track track} with the associated
	 * {@linkplain tv.amwa.maj.misctype.TrackID track id} from the list of tracks for 
	 * this package.</p>
	 * 
	 * @param trackId The requested track id from the list of tracks of this package.
	 * @return Matching track.
	 * 
	 * @throws TrackNotFoundException A track with the given track id was not found in
	 * this package.
	 */
	public Track lookupPackageTrack(
			@TrackID int trackId) 
		throws TrackNotFoundException;

	/**
	 * <p>Finds all {@linkplain SourceClip source clips} in this package that refer 
	 * to the specified old package and changes the references to point to the
	 * newly specified package.</p>
	 * 
	 * <p>This method traverses through all the package reference chains of all of the tracks of 
	 * this package looking for {@linkplain SourceClip source clips}. All source 
	 * clips that have a source id property that matches the old package id,
	 * changes them to the new package id.</p>
	 * 
	 * @param oldPackageId Package id to match against the source id property of a source clip.
	 * @param newPackageId If a match is found, change the package id of the source clip
	 * to the given one.
	 * 
	 * @throws NullPointerException One or both of the given package ids is/are <code>null</code>.
	 * 
	 * @see SourceReferenceSegment#getSourcePackageID()
	 */
	public void changeReference(
			PackageID oldPackageId,
			PackageID newPackageId) 
		throws NullPointerException;

	// TODO support the next method in MAJ? More exceptions?
	
	/**
	 * <p>Clones this package, and optionally all dependent packages, 
	 * to a package that can be used in an external file, keeping the same package 
	 * id.</p>  
	 * 
	 * <p>The method clones the specified this package into a new destination 
	 * package, with the same package id, in the given destination file.  If 
	 * the resolve dependencies flag is set to {@linkplain tv.amwa.maj.enumeration.Depend#Follow follow}, 
	 * the method also clones all packages referenced by this package.</p>
	 * 
	 * <p>If the {@linkplain tv.amwa.maj.enumeration.IncludedMedia#IncludedMedia include media flag} is set, 
	 * the method also copies the {@linkplain EssenceData essence data} associated with this package and 
	 * returns the destination package, also cloning all private data. If the media
	 * data is not in the file, the function does not attempt to find it in 
	 * another file and clone it. Both {@linkplain AAFFile AAF files} must be open 
	 * before you call this function and both must have the same 
	 * {@linkplain VersionType version number}.</p>
	 * 
	 * @param resolveDependencies Sets whether the clone of this package also clones
	 * all of its references.
	 * @param includedMedia Sets whether any media data included with this package 
	 * should be copied into the clone or not.
	 * @param clonedFile File in which to create the cloned package.
	 * 
	 * @return Externally valid cloned version of this package, created according to
	 * the given parameters.
	 * 
	 * @throws NullPointerException One or more of the given arguments is/are <code>null</code>
	 * and all are required.
	 */
	public Package cloneExternal(
			Depend resolveDependencies,
			IncludedMedia includedMedia,
			AAFFile clonedFile)
		throws NullPointerException; 

	/**
	 * <p>Creates and returns a new copy of this package in the same 
	 * {@linkplain AAFFile AAF file}. The method gives the returned new package a new 
	 * {@linkplain tv.amwa.maj.record.PackageID package id} and the given name. The method also 
	 * copies all private data.</p>
	 * 
	 * @param destinationPackageName Name for the newly created, copied version of this package.
	 * @return Newly created copy of this package.
	 * 
	 * @throws NullPointerException The given destination name for the new package is
	 * <code>null</code>.
	 */
	public Package copy(
			@AAFString String destinationPackageName) 
		throws NullPointerException;

	/**
	 * <p>Appends an existing user {@linkplain KLVData KLV data item} to the collection
	 * of KLV data items contained in this package.</p>
	 * 
	 * @param packageKLVData KLV data item to add to the collection of data items contained in 
	 * this package.
	 * 
	 * @throws NullPointerException The given KLV data item is <code>null</code>.
	 * 
	 * @see KLVDataDefinition
	 */
	public void appendPackageKLVData(
			KLVData packageKLVData) 
		throws NullPointerException;

	/**
	 * <p>Returns the total number of {@linkplain KLVData KLV data} items in the
	 * collection of KLV data items contained in this package.</p>
	 * 
	 * @return Total number of KLV data items contained in this package.
	 */
	public @UInt32 int countPackageKLVData();
	
	/**
	 * <p>Clears the list of KLV data items of this package, omitting this optional
	 * property.</p>
	 */
	public void clearPackageKLVData();

	/**
	 * <p>Returns the collection of {@linkplain KLVData KLV data} items contained in this
	 * package. This is an optional property.</p>
	 * 
	 * @return Shallow copy of the collection of KLV data items contained in this package.
	 * 
	 * @throws PropertyNotPresentException No KLV data values are present for this package.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataStrongReferenceVector
	 */
	public List<? extends KLVData> getPackageKLVData()
		throws PropertyNotPresentException;

	/**
	 * <p>Removes the given item of {@linkplain KLVData KLV data} from the 
	 * collection of KLV data items of this package.</p>
	 * 
	 * @param packageKLVData KLV data to remove from the set of KLV data of this package.
	 * 
	 * @throws NullPointerException Argument is <code>null</code>.
	 * @throws PropertyNotPresentException No KLV data is present for this package.
	 * @throws ObjectNotFoundException The given KLV data object could not be
	 * found in the collection of KLV data items for this package.
	 */
	public void removePackageKLVData(
			KLVData packageKLVData) 
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException;

	/**
	 * <p>Create and append a new attribute to the collection of attributes contained in this
	 * package, which specify attributes that are under the control of the application 
	 * (for example filter control).</p>
	 * 
	 * <p>This method creates a new {@linkplain TaggedValue tagged value}, initializes it with the 
	 * specified attribute name/value pair, and appends it to the attribute collection.</p>
	 * 
	 * @param name Name of the new attribute.
	 * @param value Value of the new attribute.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code>.
	 * 
	 * @see #appendPackageAttribute(TaggedValue)
	 */
	public void appendPackageAttribute(
			@AAFString String name,
			@AAFString String value) 
		throws NullPointerException;
	
	/**
	 * <p>Appends an existing attribute to the collection of attributes contained in this
	 * package, which specify attributes that are under the control of the application 
	 * (for example filter control).</p>
	 * 
	 * @param packageAttribute Attribute to append to the collection of attributes contained in this
	 * package.
	 * 
	 * @throws NullPointerException The given attribute is <code>null</code>.
	 * 
	 * @see #appendPackageAttribute(String, String)
	 */
	public void appendPackageAttribute(
			TaggedValue packageAttribute)
		throws NullPointerException;

	/**
	 * <p>Returns the number of attributes in the collection of attributes contained in
	 * this package, which specify attributes that are under the control of the application 
	 * (for example filter control).</p>
	 * 
	 * @return Number of attributes in the collection of attributes contained in this package.
	 */
	public @UInt32 int countPackageAttributes();
	
	/**
	 * <p>Clears the list of package attributes of this package, omitting this optional
	 * property.</p>
	 */
	public void clearPackageAttributes();

	/**
	 * <p>Returns the collection of attributes contained in this package, which specify attributes 
	 * that are under the control of the application (for example filter control).</p>
	 * 
	 * @return Shallow copy of the collection of attributes contained in this package.
	 * 
	 * @throws PropertyNotPresentException No attributes are present for this package.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueStrongReferenceVector
	 */
	public List<? extends TaggedValue> getPackageAttributes()
		throws PropertyNotPresentException;

	/**
	 * <p>Removes the given attribute from the collection of attributes contained in this
	 * package, which specify attributes that are under the control of the application 
	 * (for example filter control).</p>
	 * 
	 * @param packageAttribute Attribute to remove from the collection of attributes contained in  
	 * this package.
	 * 
	 * @throws NullPointerException The given attribute is <code>null</code>.
	 * @throws PropertyNotPresentException No attributes are present for this package.
	 * @throws ObjectNotFoundException The given attribute could not be
	 * found in collection of attributes contained in this package.
	 */
	public void removePackageAttribute(
			TaggedValue packageAttribute) 
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException;

	/**
	 * <p>Sets this package's {@linkplain tv.amwa.maj.constant.UsageType usage}.</p>
	 * 
	 * <p>Possible values include:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.constant.UsageType#AdjustedClip}</li>
	 *  <li>{@link tv.amwa.maj.constant.UsageType#LowerLevel}</li>
	 *  <li>{@link tv.amwa.maj.constant.UsageType#SubClip}</li>
	 *  <li>{@link tv.amwa.maj.constant.UsageType#Template}</li>
	 *  <li>{@link tv.amwa.maj.constant.UsageType#TopLevel}</li>
	 * </ul>
	 * 
	 * <p>Set this optional property to <code>null</code> to omit it, which
	 * is used to specify that a clip references only file, import, tape or
	 * film sources.</p>
	 * 
     * <p>The built-in usage codes are specified in the 
     * <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF edit protocol</a>.</p>
     * 
	 * @param packageUsage This package's usage code.
	 * 
	 * @throws NullPointerException The given usage is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.constant.UsageType
	 * @see tv.amwa.maj.industry.TypeDefinitions#UsageType
	 */
	public void setPackageUsage(
			AUID packageUsage)
		throws NullPointerException;

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.constant.UsageType usage} of this package. This
	 * is an optional property.</p>
     * 
     * <p>The built-in usage codes are specified in the 
     * <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF edit protocol</a>.</p>
	 * 
	 * @return Usage of this package.
	 * 
	 * @throws PropertyNotPresentException The optional usage code property is not
	 * present in this package.
	 * 
	 * @see tv.amwa.maj.constant.UsageType
	 * @see tv.amwa.maj.industry.TypeDefinitions#UsageType
	 */
	public AUID getPackageUsage()
		throws PropertyNotPresentException;

	/**
	 * <p>Creates and returns a new {@linkplain StaticTrack static 
	 * track} with the given property values, as well as appending it to the 
	 * list of tracks of this package.</p>
	 * 
	 * @param segment Segment to use to create a new static track.
	 * @param trackId Track id to assign to the new static track.
	 * @param trackName Name for the new static track, or <code>null</code> to omit
	 * this optional property.
	 * @return Newly created static track, which is also appended to the list of tracks
	 * of this package.
	 * 
	 * @throws NullPointerException The given segment is <code>null</code>.
	 * @throws IllegalArgumentException The given track id is negative.
	 * @throws TrackExistsException A track with the given track id is already present
	 * in this package.
	 * 
	 * @see #appendPackageTrack(Track)
	 */
	public StaticTrack appendNewStaticTrack(
			Segment segment,
			@TrackID int trackId,
			@AAFString String trackName) 
		throws NullPointerException,
			IllegalArgumentException,
			TrackExistsException;

	/**
	 * <p>Creates and returns a new {@linkplain EventTrack event 
	 * track} with the given edit rate, as well as appending it to 
	 * the list of tracks of this package.</p>
	 * 
	 * @param editRate Edit rate property for the new event track.
	 * @param segment Segment for the new event track.
	 * @param trackId Track ID for the new event track.
	 * @param trackName Name for the new event track, or <code>null</code>
	 * to omit this optional property.
	 * @return The newly created event track, which is also appended
	 * as a track to the list of tracks of this package.
	 * 
	 * @throws NullPointerException One or both of the edit rate and/or segment
	 * arguments is <code>null</code>.
	 * @throws IllegalArgumentException The track id is negative or the given segment
	 * is not valid according to the conditional rules for {@linkplain EventTrack event 
	 * track}.
	 * @throws TrackExistsException A track with the given track id is already present
	 * in this package.
	 * 
	 * @see #appendPackageTrack(Track)
	 */
	public EventTrack appendNewEventTrack(
			Rational editRate,
			Segment segment,
			@TrackID int trackId,
			@AAFString String trackName)
		throws NullPointerException,
			IllegalArgumentException,
			TrackExistsException;
	
	/**
	 * <p>Create a cloned copy of this package.</p>
	 *
	 * @return Cloned copy of this package.
	 */
	public Package clone();
}
