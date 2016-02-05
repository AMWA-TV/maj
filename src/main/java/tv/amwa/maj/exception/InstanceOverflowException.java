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
 * $Log: InstanceOverflowException.java,v $
 * Revision 1.7  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/05 13:10:58  vizigoth
 * Created new forge for making record and union type values. Requires moving related types into exposed package for documentation.
 *
 * Revision 1.5  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:37:52  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:09:56  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

import tv.amwa.maj.record.InstanceNumberGeneration;

// References: PackageID

/** 
 * <p>Thrown when the instance generator of a {@linkplain tv.amwa.maj.record.PackageID package id}
 * overflows. The exception can be queried to find out what kind of 
 * {@linkplain tv.amwa.maj.record.InstanceNumberGeneration
 * instance number generator} is causing an overflow condition.</p>
 * 
 * <p>No equivalent C result code.</p>
 * 
 * @see tv.amwa.maj.record.PackageID#nextInstance()
 *
 *
 *
 */
public class InstanceOverflowException 
	extends RuntimeException 
	implements MAJException,
		NewForMAJ {
	
	/** <p></p> */
	private static final long serialVersionUID = 1604238923583779583L;
	
	private tv.amwa.maj.record.InstanceNumberGeneration generator;
	
	/**
	 * <p>Create a new instance number generator exception with the given error 
	 * message and containing the kind of generator that has overflowed.</p>
	 * 
	 * @param msg Message describing the exception.
	 * @param generator Kind of instance number generator that has overflowed.
	 */
	public InstanceOverflowException(
			String msg, 
			tv.amwa.maj.record.InstanceNumberGeneration generator) {

		super(msg);
		this.generator = generator;
	}

	/**
	 * <p>Create a new instance number generator exception with no error message.</p>
	 */
	public InstanceOverflowException() {
		super();
	}

	/**
	 * <p>Returns the kind of instance number generator that overflowed and caused
	 * this exception.</p>
	 * 
	 * @return The kind of instance number generator that has overflowed, or {@link
	 * tv.amwa.maj.record.InstanceNumberGeneration#NotDefined} if the kind of generator
	 * is not known.
	 */
	public tv.amwa.maj.record.InstanceNumberGeneration getGenerator() {
		
		if (generator == null)
			return InstanceNumberGeneration.NotDefined;
		return generator;
	}
}
