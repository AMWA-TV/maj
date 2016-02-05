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
 * $Log: FillerImpl.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:16  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.Filler;


/** 
 * <p>Implements an unspecified value for the duration of a 
 * {@linkplain tv.amwa.maj.model.Component component}.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0900,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Filler",
		  description = "The Filler class represents an unspecified value for the duration of the object.",
		  symbol = "Filler")
public class FillerImpl
	extends 
		SegmentImpl
	implements 
		Filler,
		Serializable,
		Cloneable {

	/**  */
	private static final long serialVersionUID = -7421278751273286811L;

	// The filler class does not define any additional properties.
	
	/** Default constructor is not public to avoid unset required fields. */
	public FillerImpl() { }
	
	// TODO search out a good description of what a filler does
	/** 
	 * <p>Creates and initializes a filler segment with a length.</p>
	 *
	 * @param dataDefinition Kind of data described by the component.
	 * @param length Duration measured in edit units of the component.
	 * 
	 * @throws NullPointerException Data definition is <code>null</code>.
	 * @throws BadLengthException The length of a filler segment cannot be negative.
	 */
	public FillerImpl(
			DataDefinition dataDefinition,
			@LengthType long length) 
		throws NullPointerException,
			BadLengthException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a new filler with a null data definition.");
		
		if (length < 0l)
			throw new BadLengthException("The length of a filler cannot be negative.");
		
		setComponentDataDefinition(dataDefinition);
		setLengthPresent(true);
		setComponentLength(length);
	}
	
	/**
	 * <p>Creates and initializes a filler segment without a length.</p>
	 *
	 * @param dataDefinition Kind of data described by the component.
	 * 
	 * @throws NullPointerException The data definition argument is <code>null</code>.
	 */
	public FillerImpl(
			DataDefinition dataDefinition)
		throws NullPointerException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a new filler with a null data definition.");

		setComponentDataDefinition(dataDefinition);
		setLengthPresent(false);
	}

	public Filler clone() {

		return (Filler) super.clone();
	}
}
