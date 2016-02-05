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
 * $Log: FindSourceInformationImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/02/08 11:31:14  vizigoth
 * Comment linking fix.
 *
 * Revision 1.3  2008/01/14 20:48:52  vizigoth
 * Change of null/nil mob is to be called the zero mob id.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:36  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.record.impl.PackageIDImpl;

/** 
 * <p>Implementation of a class that contains the result returned by the 
 * {@link tv.amwa.maj.model.SearchSource#searchSource(int, long, tv.amwa.maj.enumeration.MobKind, tv.amwa.maj.enumeration.CriteriaType, tv.amwa.maj.enumeration.OperationChoice)}
 * method, containing source information about particular {@linkplain tv.amwa.maj.model.Track tracks}.</p>
 * 
 * <p>The information available in this object will depend on the kind
 * of search carried out. All methods in this interface that return a non-primitive
 * type value could return a <code>null</code> pointer to indicate that a value 
 * is not present.</p>
 *
 *
 */

// TODO work out what to do with this and associated methods

public class FindSourceInformationImpl
	implements tv.amwa.maj.model.FindSourceInformation,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 280671475967906329L;
	
	private tv.amwa.maj.model.Package mob = null;
	// TODO work out what to do with component and operation group properties
	@SuppressWarnings("unused")
	private tv.amwa.maj.model.Component component = null;
	private tv.amwa.maj.record.Rational editRate = null;
	private Integer trackID;
	private Long position = 0l;
	private Long length = 0l;
	@SuppressWarnings("unused")
	private tv.amwa.maj.model.OperationGroup operationGroup = null;

	/** Default constructor is not public to avoid unset required fields. */
	@SuppressWarnings("unused")
	private FindSourceInformationImpl() {
	}

	public FindSourceInformationImpl(
			tv.amwa.maj.model.Package mob,
			Integer trackID,
			Long position,
			tv.amwa.maj.record.Rational editRate,
			Long length,
			tv.amwa.maj.model.Component component) {
		
		this.mob = mob;
		this.trackID = trackID;
		this.position = position;
		this.editRate = editRate;
		this.length = length;
		this.component = component;
	}

	public tv.amwa.maj.record.Rational getEditRate() {

		return editRate;
	}

	public long getLength()
			throws BadPropertyException {

		if (length == null)
			throw new BadPropertyException("A length property is not present in this find source information value.");
		return length;
	}

	public tv.amwa.maj.model.Package getPackage() {

		return mob;
	}

	public SourceReferenceValueImpl getSourceReference() {

		SourceReferenceValueImpl sourceReference = new SourceReferenceValueImpl(PackageIDImpl.getZeroPackageID(), 0, 0l);
		
		if (mob != null) {
			sourceReference.setSourcePackageID(mob.getPackageID());
			sourceReference.setSourceTrackID(trackID);
			sourceReference.setStartPosition(position);
		}
		
		return sourceReference;
	}
}
