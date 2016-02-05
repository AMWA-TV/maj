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
 * $Log: DescriptiveMarker.java,v $
 * Revision 1.3  2011/10/05 17:14:27  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/02/28 12:50:34  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.3  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2008/01/27 11:07:29  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:30  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.record.AUID;



/** 
 * <p>Specifies descriptive metadata associated with a point in time and the {@linkplain Track tracks} that 
 * the description refers to. A descriptive marker may contain a {@linkplain DescriptiveFramework descriptive framework} 
 * that specifies the metadata.</p>
 * 
 * <p>If a descriptive marker refers to a {@linkplain StaticTrack static track}, the extent of the description 
 * is for the entire {@linkplain Package package}.</p>
 * 
 *
 * 
 * @see DescriptiveFramework
 * @see DescriptiveClip
 */

public interface DescriptiveMarker 
	extends CommentMarker {

	/** <p>Default value for the described tracks parameter. This value is a set that contains all track
	 * indexes from <code>0</code> to <code>Integer.MAX_VALUE</code>.</p> */
	public final static Set<Integer> AllTracksDescribed = new AllTracksDescribed();
	
	/**
	 * <p>No tracks are described by the marker.</p>
	 */
	public final static Set<Integer> NoTracksDescribed = new HashSet<Integer>(0);
	
	/** 
	 * <p>Contrived inner class that represents the set of all positive integers and zero. This represents
	 * the default value for described sets as specified in section&nbsp;7.10 of the 
	 * <a href="http://www.amwa.tv/html/specs/aafobjectspec-v1.1.pdf">AAF object specification</a>.</p>
	 *
	 */
	final static class AllTracksDescribed
		implements java.util.Set<Integer> {

		AllTracksDescribed() { }
		
		public boolean add(
				Integer o) {

			return false;
		}

		public boolean addAll(
				Collection<? extends Integer> c) {

			return false;
		}

		public void clear() {
		}

		public boolean contains(
				Object o) {

			if (!(o instanceof Number)) return false;

			if (((Number) o).intValue() < 0)
				return false;
			else
				return true;
		}

		public boolean containsAll(
				Collection<?> c) {

			for ( Object o : c ) {
				if (!(contains(o))) return false;
			}
			
			return true;
		}

		public boolean isEmpty() {

			return false;
		}

		/** 
		 *
		 * <p>Contrived inner class of an iterator over all positive integers and zero. Any implementation
		 * that uses this may go off on a wild goose chase!</p>
		 *
		 */
		final static class AllIntsIterator
			implements java.util.Iterator<Integer> {

			int value = 0;

			AllIntsIterator() { }

			public boolean hasNext() {

				return (value < Integer.MAX_VALUE);
			}

			public Integer next() 
				throws NoSuchElementException {

				if (value < Integer.MAX_VALUE)
					return value++;
				else
					throw new NoSuchElementException("Reached the end of this iterator over positive integers.");
			}

			public void remove() {

				// Does nothing
			}

		}

		
		public Iterator<Integer> iterator() {

			return new AllIntsIterator();
		}

		public boolean remove(
				Object o) {

			return false;
		}

		public boolean removeAll(
				Collection<?> c) {

			return false;
		}

		public boolean retainAll(
				Collection<?> c) {

			return false;
		}

		public int size() {

			return Integer.MAX_VALUE;
		}

		public Object[] toArray() {

			return null;
		}

		public <T> T[] toArray(
				T[] a) {

			return null;
		}
		
		public int hashCode() {
			
			return Integer.MAX_VALUE;
		}
		
		public boolean equals(Object o) {
			
			if (o == this) return true;
			return false;
		}
	}
	
	/**
	 * <p>Specifies the {@linkplain tv.amwa.maj.misctype.TrackID track ids} in the {@linkplain Package package} to which 
	 * this descriptive marker refers. Set this optional property to <code>null</code> to
	 * omit it, which indicates that all tracks are described. Set to {@link #NoTracksDescribed}
	 * to indicate that the marker does not refer to any tracks.</p>
	 * 
	 * @param describedTrackIDs Track id for the tracks in the associated package that this
	 * descriptive marker describes.
	 * 
	 * @throws IllegalArgumentException One or more of the track ids in the given set is negative.
	 * 
	 * @see #AllTracksDescribed
	 * @see Track
	 */
	public void setDescribedTrackIDs(
			@TrackID Set<Integer> describedTrackIDs)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the set of described track ids that are referenced by this descriptive marker. If the value returned
	 * is equal to {@link #AllTracksDescribed}, this descriptive marker describes all tracks in the 
	 * associated {@linkplain Package package}.</p>
	 * 
	 * @return Track ids in this package that this descriptive marker describes.
	 * 
	 * @throws PropertyNotPresentException The optional described tracks property is not present in this 
	 * descriptive marker.
	 */
	public @TrackID Set<Integer> getDescribedTrackIDs()
		throws PropertyNotPresentException; 
	
	/**
	 * <p>Returns the number of {@linkplain tv.amwa.maj.misctype.TrackID track ids} referred to by this descriptive 
	 * marker.</p.
	 * 
	 * <p>Note that the default value is {@link #AllTracksDescribed}, which has <code>Integer.MAX_VALUE</code>
	 * track ids. Care should be taken not to end up iterating over all of these entries!</p>
	 * 
	 * @return Number of track ids stored.
	 * 
	 * @throws PropertyNotPresentException The optional described tracks property is not present in this 
	 * descriptive marker.
	 */
	public @UInt32 int getDescribedTrackIDsSize()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the {@link DescriptiveFramework descriptive framework} that specifies the metadata for 
	 * this descriptive marker. Set this optional property to <code>null</code> to omit it.</p>
	 *  
	 * @param descriptiveFramework Specifies the descriptive framework.
	 * 
	 * @see DescriptiveFramework
	 */
	public void setDescriptiveFrameworkObject(
			DescriptiveFramework descriptiveFramework);

	/**
	 * <p>Returns the {@link DescriptiveFramework descriptive framework} that specifies the metadata for 
	 * this descriptive marker.</p>
	 * 
	 * @return The associated descriptive framework.
	 * 
	 * @throws PropertyNotPresentException The optional descriptive framework property is not present
	 * in this descriptive marker.
	 * 
	 * @see DescriptiveFramework
	 */
	public DescriptiveFramework getDescriptiveFrameworkObject()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the immutable identifier for this descriptive metadata plugin instance.</p>
	 * 
	 * @return Immutable identifier for this descriptive metadata plugin instance.
	 * 
	 * @throws PropertyNotPresentException The optional descriptive metadata plugin identifier
	 * is not present for this descriptive marker.
	 */
	public AUID getDescriptiveMetadataPluginID()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the immutable identifier for this descriptive metadata plugin instance.</p>
	 * 
	 * @param descriptiveMetadataPluginID Immutable identifier for this descriptive metadata plugin 
	 * instance.
	 */
	public void setDescriptiveMetadataPluginID(
			AUID descriptiveMetadataPluginID);
	
	/**
	 * <p>Returns the descriptive metadata scheme that is referenced by the descriptive framework 
	 * object. A descriptive metadata scheme is represented by an 
	 * {@linkplain tv.amwa.maj.meta.ExtensionScheme extension scheme}. This is an optional property.</p>
	 * 
	 * @return Descriptive metadata scheme that is referenced by the descriptive framework 
	 * object.
	 * 
	 * @throws PropertyNotPresentException The optional descriptive metadata scheme property is
	 * not present for this descriptive marker.
	 * 
	 * @see #getDescriptiveFrameworkObject()
	 * @see tv.amwa.maj.meta.Root#getRootExtensions()
	 */
	public ExtensionScheme getDescriptiveMetadataScheme()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the descriptive metadata scheme that is referenced by the descriptive framework 
	 * object. A descriptive metadata scheme is represented by an 
	 * {@linkplain tv.amwa.maj.meta.ExtensionScheme extension scheme}. Set this optional property
	 * to <code>null</code> to omit it.</p>
	 * 
	 * @param extensionScheme Descriptive metadata scheme that is referenced by the descriptive framework 
	 * object.
	 * 
	 * @see #setDescriptiveFrameworkObject(DescriptiveFramework)
	 */
	public void setDescriptiveMetadataScheme(
			ExtensionScheme extensionScheme);
	
	/**
	 * <p>Returns the application environment identifier, an <a href="http://www.ietf.org/rfc/rfc3986.txt">Uniform 
	 * Resource Identifier (RFC 3986)</a> that identifies the application to which the information in this plugin 
	 * object applies. This is an optional property.</p>
	 * 
	 * @return Application environment identifier.
	 * 
	 * @throws PropertyNotPresentException The optional descriptive metadata application environment identifier
	 * is not present for this descriptive marker.
	 */
	public String getDescriptiveMetadataApplicationEnvironmentID()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the application environment identifier, an <a href="http://www.ietf.org/rfc/rfc3986.txt">Uniform 
	 * Resource Identifier (RFC 3986)</a> that identifies the application to which the information in this plugin 
	 * object applies. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param descriptiveMetadataApplicationEnvironmentID Application environment identifier.
	 */
	public void setDescriptiveMetadataApplicationEnvironmentID(
			String descriptiveMetadataApplicationEnvironmentID);
	
	/**
	 * <p>Create a cloned copy of this descriptive marker.</p>
	 *
	 * @return Cloned copy of this descriptive marker.
	 */
	public DescriptiveMarker clone();
	
}
