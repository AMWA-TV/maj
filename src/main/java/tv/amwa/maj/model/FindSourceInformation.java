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
 * $Log: FindSourceInformation.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/01/27 11:07:36  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2008/01/14 21:04:49  vizigoth
 * Minor comment fix.
 *
 * Revision 1.1  2007/11/13 22:09:01  vizigoth
 * Public release of MAJ API.
 */

// TODO more work on source references

package tv.amwa.maj.model;

import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.union.SourceReferenceValue;


/**
 * <p>Specifies a search result containing source information about particular 
 * tracks.</p>
 * 
 * <p>The information available in this object will depend on the kind
 * of search carried out. All methods in this interface that return a non-primitive
 * type value could return a <code>null</code> pointer to indicate that a value 
 * is not present.</p>
 * 
 *
 *
 * @see SearchSource#searchSource(int, long, tv.amwa.maj.enumeration.PackageKind, tv.amwa.maj.enumeration.CriteriaType, tv.amwa.maj.enumeration.OperationChoice)
 */
public interface FindSourceInformation {

	/**
	 * <p>Returns the {@linkplain Package package} search result.</p>
	 * 
	 * @return The final media object referenced, or <code>null</code>
	 * if no valid package is referenced.
	 */
	public Package getPackage();

	/**
	 * <p>Returns the source reference search result.</p></p>
	 * 
	 * @return Source reference search result, or <code>null</code> if the value is not
	 * available.
	 */
	public SourceReferenceValue getSourceReference();

	/**
	 * <p>Returns the length of this component. The duration is specified in 
	 * edit units of the result.</p>
	 * 
	 * <p>This method deals with an optional property, which will only be
	 * present for time-varying media and some events.</p>
	 * 
	 * @return Length of the component.
	 * 
	 * @throws BadPropertyException The optional length property is not 
	 * present for this object.
	 */
	public @LengthType long getLength() 
		throws BadPropertyException;

	/**
	 * <p>Returns the edit rate for the search result.</p>
	 * 
	 * @return Edit rate for the search result, or <code>null</code>
	 * if this value is not available.
	 */
	public Rational getEditRate();
	
}

