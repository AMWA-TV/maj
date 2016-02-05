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
 * $Log: SearchSource.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/08 11:27:25  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2007/12/13 11:33:24  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.1  2007/11/13 22:08:10  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.enumeration.CriteriaType;
import tv.amwa.maj.enumeration.PackageKind;
import tv.amwa.maj.enumeration.OperationChoice;
import tv.amwa.maj.exception.InvalidPackageTypeException;
import tv.amwa.maj.exception.TraversalNotPossibleException;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.misctype.TrackID;

// TODO work out the relevance of this interface for the MAJ API

/**
 * <p>Specifies a search for source information of a {@linkplain Track track}
 * in a {@linkplain MaterialPackage material package} or {@linkplain SourcePackage source package}.</p>
 * 
 *
 *
 */

public interface SearchSource {

	/**
	 * <p>Returns the source information for a track in a {@linkplain MaterialPackage
	 * material package} or {@linkplain SourcePackage source package}. The method follows the 
	 * {@linkplain SourceClip source clip} references in the specified track until 
	 * it encounters the kind of package specified in the package kind 
	 * parameter.</p> 
	 * 
	 * <p>This function cannot be used on a {@linkplain CompositionPackage 
	 * composition package} and is not intended to be called iteratively.</p> 
	 * 
	 * @param trackID Track to find source information for.
	 * @param offset Offset into the track.
	 * @param packageKind Kind of package to search for.
	 * @param mediaCriteria Media criteria to match in the search.
	 * @param operationChoice Operation choice.
	 * 
	 * @return List of source information matching the given 
	 * search criteria.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>.
	 * @throws InvalidPackageTypeException The given package type is not valid.
	 * @throws TraversalNotPossibleException A package of the given kind 
	 * cannot be found.
	 */
	public List<FindSourceInformation> searchSource(
			@TrackID int trackID,
			@PositionType long offset,
			PackageKind packageKind,
			CriteriaType mediaCriteria,
			OperationChoice operationChoice) 
		throws NullPointerException,
			InvalidPackageTypeException,
			TraversalNotPossibleException;
}
