/* 
 **********************************************************************
 *
 * $Id: ScopeReference.java,v 1.3 2011/02/14 22:32:49 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: ScopeReference.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/02/08 11:27:21  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:17  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.integer.UInt32;


/**
 * <p>Specifies a reference to a section in the specified {@linkplain Track track}
 * or {@linkplain NestedScope nested scope} track. Scope references are specified 
 * in terms of a relative track offset, and the number of scopes to skip 
 * outward.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see NestedScope
 */

public interface ScopeReference 
	extends Segment {

	/**
	 * <p>Returns the relative scope property of this scope reference, which specifies 
	 * the number of {@linkplain NestedScope nested scopes} to pass to find the nested scope or 
	 * {@linkplain Package package} owning the track.</p>
	 * 
	 * @return Number of nested scopes to pass to find the nested scope track.
	 */
	public @UInt32 int getRelativeScope();
	
	/**
	 * <p>Sets the relative scope property of this scope reference, which specifies 
	 * the number of {@linkplain NestedScope nested scopes} to pass to find the nested scope or 
	 * {@linkplain Package package} owning the track.</p>
	 * 
	 * @param relativeScope Number of nested scopes to pass to find the nested scope track.
	 * 
	 * @throws IllegalArgumentException A relative scope cannot be a negative value.
	 */
	public void setRelativeScope(
			@UInt32 int relativeScope)
		throws IllegalArgumentException;
	
	/**
	 * <p>Returns the relative track property of this scope reference, which specifies the number of 
	 * {@linkplain Track tracks} that precede the track owning the scope reference to pass to find the 
	 * track referenced.</p>
	 * 
	 * @return Number of tracks that precede the track containing the scope reference.
	 */
	public @UInt32 int getRelativeTrack();

	/**
	 * <p>Sets the relative track property of this scope reference, which specifies the number of 
	 * {@linkplain Track tracks} that precede the track owning the scope reference to pass to find the 
	 * track referenced.</p>
	 * 
	 * @param relativeTrack Number of tracks that precede the track containing the scope reference.
	 * 
	 * @throws IllegalArgumentException A relative track cannot be a negative value.
	 */
	public void setRelativeTrack(
			@UInt32 int relativeTrack)
		throws IllegalArgumentException;
	
	/**
	 * <p>Create a cloned copy of this scope reference.</p>
	 *
	 * @return Cloned copy of this scope reference.
	 */
	public ScopeReference clone();
}

